//**********************************************************
//  1. 제      목: 과정체계도 운영자페이지
//  2. 프로그램명: CourseMapAdminBean.java
//  3. 개      요: 과정체계도 운영자페이지관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0 QnA
//  6. 작      성: 이연정 2005. 7.  7
//  7. 수      정: 이나연 05.11.17 _ totalcount 하기위한 쿼리 수정
//**********************************************************
package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CourseMapAdminBean {
    private ConfigSet config;
    private int row;
    public CourseMapAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public CourseMapAdminBean(String type) {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
    * 리스트
    * @param box          receive from the form object and session
    * @return ArrayList    리스트
    * @throws Exception
    */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql     = "";
        String count_sql = "";
        String head_sql  = "";
		String body_sql  = "";		
        String group_sql = "";
        String order_sql = "";
        int v_pageno        = box.getInt("p_pageno");
        DataBox dbox = null;

		try {
            connMgr = new DBConnectionManager();

			list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            head_sql += " select tabseq, realpdf, savepdf, realsfile, savesfile, reallfile, savelfile, luserid, ldate,tabseqname ";     
            body_sql += "   from TZ_COURSEMAP";
            order_sql += " order by tabseq desc ";
			
			sql= head_sql+ body_sql+ group_sql+ order_sql;

            ls = connMgr.executeQuery(sql);
			count_sql= "select count(*) "+ body_sql;
			int total_row_count=BoardPaging.getTotalRow(connMgr, count_sql);	//     전체 row 수를 반환한다
			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다

            ls.setPageSize(row);             				//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);     //     현재페이지번호를 세팅한다.
            	
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list.add(dbox);

            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    * 등록할때
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */

     public int insert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        int isOk1 = 1;
        // int isOk2 = 1;
        // int v_cnt = 0;

		int v_tabseq = 0;
		// String s_usernm    = box.getSession("name");  
		String s_userid    = box.getSession("userid");
		String v_tabseqname= box.getString("p_tabseqname");
        String v_realpdf   = box.getRealFileName("p_pdffile");
		String v_savepdf   = box.getNewFileName("p_pdffile");
        String v_realsfile = box.getRealFileName("p_sfile");
		String v_savesfile = box.getNewFileName("p_sfile");
		String v_reallfile = box.getRealFileName("p_lfile");
		String v_savelfile = box.getNewFileName("p_lfile");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

			//----------------------   게시판 번호 가져온다 ----------------------------
            sql = "select NVL(max(tabseq), 0) from TZ_COURSEMAP";
            ls = connMgr.executeQuery(sql);
            ls.next();
            v_tabseq = ls.getInt(1) + 1;
            ls.close();

            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into TZ_COURSEMAP(tabseq, realpdf, savepdf, realsfile, savesfile, reallfile, savelfile, luserid, ldate,tabseqname)";
            sql1 += "                values (?, ?, ?, ?, ?, ?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),?) ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setString(2, v_realpdf);
            pstmt1.setString(3, v_savepdf);
            pstmt1.setString(4, v_realsfile);
            pstmt1.setString(5, v_savesfile);
            pstmt1.setString(6, v_reallfile);
            pstmt1.setString(7, v_savelfile);
            pstmt1.setString(8, s_userid);
            pstmt1.setString(9, v_tabseqname);
            
            isOk1 = pstmt1.executeUpdate();

            if(isOk1 > 0 ) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }

    /**
    * 수정 검색
    * @param box          receive from the form object and session
    * @return ArrayList    리스트
    * @throws Exception
    */
    public DataBox selectUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls     = null;
        // ArrayList list = null;
        String sql     = "";
        // int v_pageno   = box.getInt("p_pageno");
		int v_tabseq   = box.getInt("p_tabseq");
        DataBox dbox   = null;

		try {
            connMgr = new DBConnectionManager();

			// list = new ArrayList();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            sql += " select tabseq, realpdf, savepdf, realsfile, savesfile, reallfile, savelfile, luserid, ldate,tabseqname ";     
            sql += "   from TZ_COURSEMAP where tabseq = "+ v_tabseq;
            sql += " order by tabseq desc ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    /**
    * 수정하여 저장할때
    * @param box      receive from the form object and session
    * @return isOk    1:update success,0:update fail
    * @throws Exception
    */
     public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        // String sql1 = "";
        // String sql2 = "";
        // String sql3 = "";
        // ListSet ls = null;
        int isOk = 0;
		// String s_usernm    = box.getSession("name");  
		String s_userid    = box.getSession("userid");
		String v_tabseq    = box.getString("p_tabseq");
		String v_tabseqname= box.getString("p_tabseqname");
        String v_realpdf   = box.getString("p_realpdf");
		String v_savepdf   = box.getString("p_savepdf");
        String v_realsfile = box.getString("p_realsfile");
		String v_savesfile = box.getString("p_savesfile");
		String v_reallfile = box.getString("p_reallfile");
		String v_savelfile = box.getString("p_savelfile");
        String s_realpdf   = box.getRealFileName("s_pdffile");
		String s_savepdf   = box.getNewFileName("s_pdffile");
        String s_realsfile = box.getRealFileName("s_sfile");
		String s_savesfile = box.getNewFileName("s_sfile");
		String s_reallfile = box.getRealFileName("s_lfile");
		String s_savelfile = box.getNewFileName("s_lfile");

        try {
            connMgr = new DBConnectionManager();
            //------------------------------------------------------------------------------------
                sql  = " update TZ_COURSEMAP set realpdf=?, savepdf=?, realsfile=?, savesfile=?, reallfile=?, savelfile=?, luserid=?, ldate=to_char(sysdate, 'YYYYMMDDHH24MISS'),tabseqname=?";
                sql += "  where tabseq = ? ";
                pstmt = connMgr.prepareStatement(sql);

			if("".equals(s_realpdf)){
                pstmt.setString(1,  v_realpdf);
                pstmt.setString(2,  v_savepdf);
			} else {
				pstmt.setString(1,  s_realpdf);
                pstmt.setString(2,  s_savepdf);
			}
			if("".equals(s_realsfile)){
                pstmt.setString(3,  v_realsfile);
                pstmt.setString(4,  v_savesfile);
			}else {
                pstmt.setString(3,  s_realsfile);
                pstmt.setString(4,  s_savesfile);
			}
			if("".equals(s_reallfile)){
                pstmt.setString(5,  v_reallfile);
                pstmt.setString(6,  v_savelfile);
			} else {
                pstmt.setString(5,  s_reallfile);
                pstmt.setString(6,  s_savelfile);
			}
                pstmt.setString(7,  s_userid);
                pstmt.setString(8,  v_tabseqname);
                pstmt.setString(9,  v_tabseq);
			    isOk = pstmt.executeUpdate();

			if(isOk > 0 )	{
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
		}catch(Exception	ex)	{
			connMgr.rollback();			
			ErrorManager.getErrorStackTrace(ex,	box, sql);
			throw new Exception("sql = " + sql	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk;
	}

    /**
    * 삭제할때
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // ListSet ls = null;
        // Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1;

        int    v_tabseq     = box.getInt("p_tabseq");

        try {
            connMgr = new DBConnectionManager();
            //------------------------------------------------------------------------------------

			sql  = " delete from TZ_COURSEMAP    ";
            sql += "  where tabseq = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_tabseq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }}

        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }










}



