/**
*베타테스트시스템의 자유 자료실 빈
*<p>제목:BetaFreeBean.java</p>
*<p>설명:자유자료실 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
package com.credu.beta;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;


public class BetaFreeBean {
    private ConfigSet config;
    private int row;
	private String v_type = "CA";
    	
    public BetaFreeBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
  
   /**
    * 자유자료실 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   자유게시판 리스트
    */
    public ArrayList selectPdsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
		//2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");

        try {
			
            connMgr = new DBConnectionManager();            

            // 어떤 게시판인지정보를  가져와 tabseq를 리턴한다.
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            
            list = new ArrayList();
			
            head_sql = "select seq, userid, name, title, indate, cnt";
            body_sql += " from tz_board";
			body_sql += " where a.tabseq = " + v_tabseq;
                        
            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
                v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다
                
                if (v_select.equals("name")) {      //    이름으로 검색할때
					body_sql += " and name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("title")) {     //    제목으로 검색할때
					body_sql += " and title like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }                                                                                                                               
                else if (v_select.equals("content")) {     //    내용으로 검색할때
					body_sql += " and content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                }
            }
            group_sql += " group by seq, userid, name, title, indate, cnt";		
            order_sql += " order by a.seq desc";  		
            
			/*
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성
            */
            
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);//     전체 row 수를 반환한다

            ls.setPageSize(row);             				//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);	//     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();    	//     전체 row 수를 반환한다

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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
	
        return list;
    }  
  
  /**
    * 자유자료실 상세보기 
    * @param box          receive from the form object and session
    * @return DataBox	  조회한 값을 DataBox에 담아 리턴
    */
  
   public DataBox selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
                
        int v_seq = box.getInt("p_seq");
		
        try {
            connMgr = new DBConnectionManager();
			//어떤 게시판인지정보를  가져와 tabseq를 리턴한다.
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            
            sql = "select seq, userid, name, title, content, indate, cnt";
            sql += " from tz_board where tabseq = " + v_tabseq + " and seq = "+ v_seq;            

            ls = connMgr.executeQuery(sql); 
        
            while(ls.next()) {
                dbox = ls.getDataBox();
            }
			
            connMgr.executeUpdate("update tz_board set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = "+v_seq);        
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }   
  
  /**
    * 자유자료실 등록하기 
    * @param box          receive from the form object and session
    * @return isOk1*isOk2	  조회한 값을 DataBox에 담아 리턴
    */
     public int insertPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;        
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;
   
        String v_title = box.getString("p_title");
		
        String v_content =  StringManager.replace(box.getString("content"),"&","&amp;");  		
        
        String v_realMotionName = box.getRealFileName("p_motion");
        String v_newMotionName = box.getNewFileName("p_motion");      
        
        String s_gadmin = box.getSession("gadmin");
		String s_userid = "";
		String s_usernm = "";
		
		if (s_gadmin.equals("A1")){
			s_usernm = "운영자";
		}else{
			s_usernm = box.getSession("name");
		}

		if (s_gadmin.equals("A1")){
			s_userid = "운영자";
		}else{
			s_userid = box.getSession("userid");
		}
		            
        try {
			
            connMgr = new DBConnectionManager();          
            connMgr.setAutoCommit(false);
            
			// 어떤 게시판인지정보를  가져와 tabseq를 리턴한다.
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            
            // 게시판 번호 가져온다.
            sql = "select isnull(max(seq), 0) from tz_board";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();

           // 게시판 table 에 입력
int index = 1;            sql1 =  "insert into TZ_BOARD(TABSEQ,SEQ,TITLE,USERID,NAME,CONTENT,INDATE)";
//            sql1 += " values (?, ?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'))";
            sql1 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(index++,v_tabseq);
            pstmt1.setInt(index++, v_seq);
			pstmt1.setString(index++, v_title);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
			pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());

            isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.

//            sql2 = "select content from tz_board where tabseq = " + v_tabseq + " and  seq = " + v_seq;			
//			connMgr.setOracleCLOB(sql2, v_content);

			if(isOk1 > 0 && isOk2 > 0) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            }

        } catch (Exception ex) {
        	if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            FileManager.deleteFile(v_newMotionName);		                      
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {      
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }            
        }
        return isOk1;
    }
    
    /**
    * 자유자료실 수정하기 
    * @param box          receive from the form object and session
    * @return isOk1*isOk2*isOk3	  수정에 성공하면 1을 리턴한다
    */
     public int updatePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
		ListSet ls = null;
		String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;
        
        int v_seq = box.getInt("p_seq");
        int v_upfilecnt = box.getInt("p_upfilecnt");    //  서버에 저장되있는 파일수 
        String v_title = box.getString("p_title");
        String v_content =  StringManager.replace(box.getString("content"),"&","&amp;");   
        String v_savemotion = box.getString("p_savemotion");
		String v_userid = box.getString("p_userid"); 
        
        String v_realMotionName = box.getRealFileName("p_motion");
        String v_newMotionName = box.getNewFileName("p_motion");      

        String s_gadmin = box.getSession("gadmin");
		String s_userid = "";
		String s_usernm = "";

		if (s_gadmin.equals("A1")) {
			s_usernm = "운영자";
		} else {
			s_usernm = box.getSession("name");
		}

		if (s_gadmin.equals("A1")) {
			s_userid = "운영자";
		} else {
			s_userid = box.getSession("userid");
		}
        
        try {
            connMgr = new DBConnectionManager();     
            connMgr.setAutoCommit(false);
            
			// 어떤 게시판인지정보를  가져와 tabseq를 리턴한다.
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
int index = 1;
//            sql1 = "update tz_board set title = ?, userid = ?, name = ?, luserid = ?, content = empty_clob(), indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 = "update tz_board set title = ?, userid = ?, name = ?, luserid = ?, content = ?, indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 += "  where tabseq = " + v_tabseq + " and seq = ?";                   
            
            pstmt1 = connMgr.prepareStatement(sql1);
            
            pstmt1.setString(index++, v_title);
            pstmt1.setString(index++, v_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, s_userid);
			pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            pstmt1.setInt(index++, v_seq);
            
            isOk1 = pstmt1.executeUpdate();
			
//            sql2 = "select content from tz_board where seq = " + v_seq + "and tabseq = " + v_tabseq;            
//            connMgr.setOracleCLOB(sql2, v_content);
 
            if(isOk1 > 0 ) {
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);         //	 DB 에서 모든처리가 완료되면 해당 첨부파일 삭제
                }
                
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            }
            Log.info.println(this, box, "update process to " + v_seq);
        }
        catch(Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            FileManager.deleteFile(v_newMotionName);		// 첨부파일 있으면 삭제..    
            ErrorManager.getErrorStackTrace(ex, box, sql1);            
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }            
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2*isOk3;
    }	
    
    /**
    * 자유자료실 삭제하기 
    * @param box          receive from the form object and session
    * @return isOk1*isOk2 삭제에 성공하면 1을 리턴한다
    */
    public int deletePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;        
        PreparedStatement pstmt2 = null; 
		String sql = "";
		ListSet ls = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;       
        
        int v_seq = box.getInt("p_seq");
        Vector savefile  = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");                    
            
        try {
            connMgr = new DBConnectionManager();           
            connMgr.setAutoCommit(false);
            
            // 어떤 게시판인지정보를  가져와 tabseq를 리턴한다.
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            sql1 = "delete from tz_board where seq = ? and tabseq = "+ v_tabseq;
            
            pstmt1 = connMgr.prepareStatement(sql1);          
            pstmt1.setInt(1, v_seq);

            isOk1 = pstmt1.executeUpdate();
            
            if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            Log.info.println(this, box, "delete process to " + v_seq);
            
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }            
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }	    
}
