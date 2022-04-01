/**
*베타테스트시스템의 서식 자료실 빈
*<p>제목:BetaFormBean.java</p>
*<p>설명:서식자료실 빈</p>
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


public class BetaFormBean {
    private ConfigSet config;
    private int row;
	private String v_type = "CD";
    	
    public BetaFormBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
  
   /**
    * 서식자료실 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   서식 리스트
    */
    public ArrayList selectPdsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
		//2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
        
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

            head_sql = "select a.seq, a.userid, a.name, a.title, count(b.realfile) filecnt, a.indate, a.cnt";
            body_sql += " from tz_board a, tz_boardfile b";
            // 2005.11.15_하경태 :  Oracle -> Mssql
			//sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq = " + v_tabseq;
			body_sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq = " + v_tabseq;
                        
            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
                v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다
                
                if (v_select.equals("name")) {      //    이름으로 검색할때
					body_sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("title")) {     //    제목으로 검색할때
					body_sql += " and a.title like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("content")) {     //    내용으로 검색할때
					body_sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                }                                                                                                                
           
            }
            group_sql += " group by a.seq, a.userid, a.name, a.title, a.indate, a.cnt";
            order_sql += " order by a.seq desc";  		
            
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);             				// 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);  // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       // 전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();    	// 전체 row 수를 반환한다

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
    * 서식자료실 상세보기 
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
            sql = "select a.seq, a.userid, a.name, a.title, a.content, b.fileseq, b.realfile, b.savefile, a.indate, a.cnt";
            sql += " from tz_board a, tz_boardfile b";   
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq = " + v_tabseq + " and a.seq = "+v_seq;;
			
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
            
            connMgr.executeUpdate("update tz_board set cnt = cnt + 1 where tabseq = " + v_tabseq + " and  seq = "+v_seq);        
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
    * 서식자료실 등록하기 
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
			
			//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------
            
            //----------------------   게시판 번호 가져온다 ----------------------------
            sql = "select isnull(max(seq), 0) from tz_board";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into tz_board(tabseq, seq, userid, name, indate, title, cnt, luserid, content, ldate)";
//            sql1 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?,  empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'))";    
            sql1 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?,  ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";    
			int index = 1;
            
            pstmt1 = connMgr.prepareStatement(sql1);
			
            pstmt1.setInt(index++, v_tabseq);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);             
            pstmt1.setInt(index++, 0);
            pstmt1.setString(index++, s_userid);  
			pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			
            isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
            
//			sql2 = "select content from tz_board where tabseq = " + v_tabseq + " and  seq = " + v_seq;
//            connMgr.setOracleCLOB(sql2, v_content);
            
            isOk2 = this.insertUpFile(connMgr, v_seq, box);
            if(isOk1 > 0 && isOk2 > 0) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            }
        }
        catch (Exception ex) { 
        	if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }             
            FileManager.deleteFile(v_newMotionName);		                      
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
    * 서식자료실 수정하기 
    * @param box          receive from the form object and session
    * @return isOk1*isOk2*isOk3	  수정에 성공하면 1을 리턴한다
    */
     public int updatePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
		ListSet ls = null;
        PreparedStatement pstmt1 = null;        
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
        String v_savemotion = box.getString("p_savemotion");        //      이전에 저장되었던 파일명
        String v_userid = box.getString("p_userid"); 

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
            
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------
int index = 1;
            if ( !v_newMotionName.equals("")) {     //      수정할 첨부파일이 있다면..
//                sql1 = "update tz_board set title = ?, userid = ?, name = ?, luserid = ?, content = empty_clob(), indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 = "update tz_board set title = ?, userid = ?, name = ?, luserid = ?, content = ?, indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 += "  where tabseq = " + v_tabseq + " and seq = ?";                   
                
                pstmt1 = connMgr.prepareStatement(sql1);
                
                pstmt1.setString(index++, v_title);
                pstmt1.setString(index++, v_userid);
                pstmt1.setString(index++, s_usernm);
                pstmt1.setString(index++, s_userid);
				pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
                pstmt1.setInt(index++, v_seq);
            }
            else {     //       첨부파일이 없다면..
//                sql1 = "update tz_board set title = ?, userid = ?, name = ?, luserid = ?,  content = empty_clob(), indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 = "update tz_board set title = ?, userid = ?, name = ?, luserid = ?,  content = ?, indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 += "  where tabseq = " + v_tabseq + " and seq = ?";                              
                
                pstmt1 = connMgr.prepareStatement(sql1);
                
                pstmt1.setString(index++, v_title);
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, s_usernm);
                pstmt1.setString(index++, s_userid);
				pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
                pstmt1.setInt(index++, v_seq);
            }
            
            isOk1 = pstmt1.executeUpdate();
	           
//            sql2 = "select content from tz_board where tabseq = " + v_tabseq + " and seq = " + v_seq; 
//            connMgr.setOracleCLOB(sql2, v_content);

            isOk2 = this.insertUpFile(connMgr, v_seq, box);       //      파일첨부했다면 파일table에  insert            
            isOk3 = this.deleteUpFile(connMgr, box);        //     삭제할 파일이 있다면 파일table에서 삭제
			
            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
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
    * 서식자료실 삭제하기 
    * @param box          receive from the form object and session
    * @return isOk1*isOk2 삭제에 성공하면 1을 리턴한다
    */
    public int deletePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		ListSet ls = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;        
        PreparedStatement pstmt2 = null;
		String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;       
        
        int v_seq = box.getInt("p_seq");
        Vector savefile  = box.getVector("p_realfile");
		
        String v_savemotion = box.getString("p_savemotion");		
            
        try {
            connMgr = new DBConnectionManager();           
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------
            
			sql1 = "delete from tz_board where tabseq = " + v_tabseq + " and  seq = ?";
            
            pstmt1 = connMgr.prepareStatement(sql1);
            
            pstmt1.setInt(1, v_seq);
            
            isOk1 = pstmt1.executeUpdate();
            
            for (int i = 0; i < savefile.size() ;i++ ){
				String str = (String)savefile.elementAt(i);
				if (!str.equals("")){
					
					isOk2 = this.deleteUpFile(connMgr, box);
				}
			}
			
            if (isOk1 > 0 && isOk2 > 0) {
				
                if (savefile != null) {
                    FileManager.deleteFile(savefile);         //	 첨부파일 삭제
                }
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion); 
                }
           /* 	Ftp ftp = new Ftp();
            	ftp.delete(v_savemotion);       //      Ftp 서버에서 기존 파일 삭제*/

                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
                Log.info.println(this, box, "delete process to " + v_seq);
            }
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
    
    
///////////////////////////////////////////////////////  파일 테이블   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
     /**
    * 서식자료실 파일 입력하기 
    * @param box          receive from the form object and session
    * @return isOk2 입력에 성공하면 1을 리턴한다
    */
     public int insertUpFile(DBConnectionManager connMgr, int p_seq, RequestBox box) throws Exception {    
        ListSet ls = null;
        PreparedStatement pstmt2 = null;     
        String sql = "";   
        String sql2 = "";
        int isOk2 = 1;        
        
        //----------------------   업로드되는 파일 --------------------------------
        Vector realFileNames = box.getRealFileNames("p_file");  
        Vector newFileNames = box.getNewFileNames("p_file");                
        //----------------------------------------------------------------------------------------------------------------------------
               
        String s_userid = box.getSession("userid");
        
         try {
            if(realFileNames != null) {          
				//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
				sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
				//------------------------------------------------------------------------------------
                 //----------------------   자료 번호 가져온다 ----------------------------
                sql = "select isnull(max(fileseq), 0) from tz_boardfile where tabseq = " + v_tabseq;
                ls = connMgr.executeQuery(sql);
                ls.next();
                int v_fileseq = ls.getInt(1) + 1;
                ls.close();
                //------------------------------------------------------------------------------------
                
                //////////////////////////////////   파일 table 에 입력  ///////////////////////////////////////////////////////////////////
                sql2 =  "insert into tz_boardfile(tabseq, seq, fileseq, realfile, savefile, luserid, ldate)";
                sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                
                pstmt2 = connMgr.prepareStatement(sql2);
                
                for(int i = 0; i < realFileNames.size(); i++) {  
					
		            pstmt2.setInt(1, v_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, (String)realFileNames.elementAt(i));
                    pstmt2.setString(5, (String)newFileNames.elementAt(i));
                    pstmt2.setString(6, s_userid);
                    
                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;      
                }
            }
        }
        catch (Exception ex) {
            FileManager.deleteFile(newFileNames);		//  일반파일, 첨부파일 있으면 삭제..            
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }  
        }
        return isOk2;
    }
    
   /**
    * 서식자료실 파일 삭제하기 
    * @param box          receive from the form object and session
    * @return isOk3 삭제에 성공하면 1을 리턴한다
    */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null;        
        String sql3 = "";
        int isOk3 = 1;
		ListSet ls = null;
		String sql = "";
          
        int v_seq = box.getInt("p_seq");
        Vector v_savefileVector = box.getVector("p_savefile");    
        
        try {     
			//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------
            sql3 = "delete from tz_boardfile where tabseq = " + v_tabseq + " and seq = ? and savefile = ?";
                
            pstmt3 = connMgr.prepareStatement(sql3);
                
            for(int i = 0; i < v_savefileVector.size(); i++) {
                String v_savefile = (String)v_savefileVector.elementAt(i);                     
                                
                pstmt3.setInt(1, v_seq);
                pstmt3.setString(2, v_savefile);
                
                isOk3 = pstmt3.executeUpdate();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
        }
        return isOk3;
    }
}
