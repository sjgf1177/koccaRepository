/**
*베타테스트시스템의 공지사항 자료실 빈
*<p>제목:BetaNoticeBean.java</p>
*<p>설명:공지사항자료실 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
package com.credu.beta;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;


public class BetaNoticeBean {
    private ConfigSet config;
    private int row;
    private String v_type = "CF";	
    public BetaNoticeBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
  
   /**
    * 공지사항자료실 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   공지사항 리스트
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
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

            list = new ArrayList();

            head_sql = "select a.seq, a.adname, a.adtitle, count(b.realfile) filecnt, a.addate, a.cnt";
            body_sql += " from tz_Notice a, tz_boardfile b";
            //2005.11.15_하경태 : Oracle -> Mssql
			//body_sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq =" + v_tabseq;
			body_sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq =" + v_tabseq;
                        
            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
                v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다
                
               if (v_select.equals("title")) {     //    제목으로 검색할때
				   body_sql += " and a.adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("content")) {     //    내용으로 검색할때
					body_sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                } 
            }
			
            group_sql += " group by a.seq, a.adname, a.adtitle, a.addate, a.cnt";
            order_sql += " order by a.seq desc";  		
            
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);  //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();    	//     전체 row 수를 반환한다

            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
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
    * 공지사항자료실 상세보기 
    * @param box          receive from the form object and session
    * @return DataBox	  조회한 값을 DataBox에 담아 리턴
    */
   public DataBox selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		
        DataBox dbox = null;
                
        int v_seq = box.getInt("p_seq");
		
        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
		
        try {
            connMgr = new DBConnectionManager();
			//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

            sql = "select a.seq, a.adname, a.adtitle, a.adcontent, b.fileseq, b.realfile, b.savefile, a.addate, a.cnt";
            sql += " from tz_notice a, tz_boardfile b";   
            // 2005.11.15_하경태 : Oracle -> Mssql
			//sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq = " + v_tabseq + "and a.seq = " + v_seq;
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq = " + v_tabseq + "and a.seq = " + v_seq;
			
            ls = connMgr.executeQuery(sql); 
			
            while(ls.next()) {
            //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();

                realfileVector.addElement(ls.getString("realfile"));
                savefileVector.addElement(ls.getString("savefile"));
            }   
            dbox.put("d_realfile", realfileVector);
            dbox.put("d_savefile", savefileVector);
                      
            //------------------------------------------------------------------------------------------------------------------------------------
           
            connMgr.executeUpdate("update tz_notice set cnt = cnt + 1 where seq = " + v_seq );        
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
    * 메인화면 공지사항리스트
    * @param box          receive from the form object and session
    * @return ArrayList   공지사항 리스트
    */
    public ArrayList selectNoticeList(RequestBox box) throws Exception {
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
		
        int v_pageno = box.getInt("p_pageno");

        try {
			
            connMgr = new DBConnectionManager();            
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------

            list = new ArrayList();

            head_sql = "select a.seq, a.adname, a.adtitle, count(b.realfile) filecnt, a.addate, a.cnt";
            body_sql += " from tz_Notice a, tz_boardfile b";
            //2005.11.15_하경태 : Oracle -> Mssql
			//sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq =" + v_tabseq;
			body_sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq =" + v_tabseq; 
            group_sql += " group by a.seq, a.adname, a.adtitle, a.addate, a.cnt";
            order_sql += " order by a.seq desc";  		
            
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); 	//     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();    	//     전체 row 수를 반환한다

            while (ls.next()) 
			{
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();
               
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                
                list.add(dbox);
                if (list.size() ==  5){
					break;
				}
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
}
