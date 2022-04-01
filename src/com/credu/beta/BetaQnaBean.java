/**
*베타테스트시스템의 Q&A빈
*<p>제목:BetamolabBean.java</p>
*<p>설명:Q&A 빈</p>
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
import com.credu.common.*;

public class BetaQnaBean {
    private ConfigSet config;
    private int row;
	private String v_type = "CG";
    public BetaQnaBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

   

    /**
    * Q&A 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   QNA 리스트
    */
    public ArrayList selectListQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		String sql1 = "";
        DataBox dbox = null;
		
		String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
		
		
        int v_pageno        = box.getInt("p_pageno");
        //int v_tabseq = box.getInt("p_tabseq");

        try {
            connMgr = new DBConnectionManager();
			//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
				sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
				ls = connMgr.executeQuery(sql1);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
			//------------------------------------------------------------------------------------
            list = new ArrayList();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            head_sql = " select a.seq , a.types, a.title, a.contents, a.indate, a.inuserid, ";
			head_sql += "        a.upfile, a.isopen, a.luserid, a.ldate, b.name,a.cnt,";
			head_sql += "		(select count(realfile) from tz_boardfile where tabseq = " + v_tabseq + ") filecnt,";
			head_sql += "        (select count(*) from TZ_HOMEQNA where tabseq ="+v_tabseq+" and seq = seq and types > 0) replystate ";
            body_sql += "   from TZ_HOMEQNA a, tz_member b";
			body_sql += "  where a.inuserid  =  b.userid(+)";
			body_sql += "  and tabseq = " + v_tabseq;
			
			
            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
                v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다
               
			   if (v_select.equals("title")) {     //    제목으로 검색할때
				   body_sql += " and title like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("contents")) {     //    내용으로 검색할때
					body_sql += " and contents like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                }
            }
            order_sql += " order by seq desc, types asc ";
			
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); 	//     현재페이지번호를 세팅한다.
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
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    

    /**
    * QNA 등록할때(질문)
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */   
     public int insertQnaQue(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;        
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;
   
        String v_title = box.getString("p_title");
        String v_contents =  StringManager.replace(box.getString("content"),"&","&amp;");   
        
        String v_realMotionName = box.getRealFileName("p_motion");
        String v_newMotionName = box.getNewFileName("p_motion");      
        String v_types   = "0";
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
		String v_isopen  = "Y";
		Vector newFileNames = box.getNewFileNames("p_file"); 
		
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
            sql = "select isnull(max(seq), 0) from TZ_HOMEQNA";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------
            if (newFileNames.size() != 0){
	            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
	            sql1 =  "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, upfile)                      ";
	            sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?) ";
	            
	            pstmt1 = connMgr.prepareStatement(sql1);
				pstmt1.setInt(1, v_tabseq);
				pstmt1.setInt(2, v_seq);
				pstmt1.setString(3, v_types);
				pstmt1.setString(4, v_title);
				pstmt1.setCharacterStream(5,  new StringReader(v_contents), v_contents.length());
				pstmt1.setString(6,  s_userid);
				pstmt1.setString(7,  v_isopen);
				pstmt1.setString(8, s_userid);
				pstmt1.setString(9, (String)newFileNames.elementAt(0));
			}else{
				//////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
	            sql1 =  "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate)                      ";
	            sql1 += "	values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
	            
	            pstmt1 = connMgr.prepareStatement(sql1);
				pstmt1.setInt(1, v_tabseq);
				pstmt1.setInt(2, v_seq);
				pstmt1.setString(3, v_types);
				pstmt1.setString(4, v_title);
				pstmt1.setCharacterStream(5,  new StringReader(v_contents), v_contents.length());
				pstmt1.setString(6,  s_userid);
				pstmt1.setString(7,  v_isopen);
				pstmt1.setString(8, s_userid);
			}
           
            isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.

            sql2 = "select content from tz_board where tabseq = " + v_tabseq + " and  seq = " + v_seq;              
            isOk2 = this.insertUpFile(connMgr, v_seq, box);
                        
            if(isOk1 > 0 && isOk2 > 0) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  


				// 질물 등록시 총괄관리자에게 메신져보내기
	
				MChannelConnect mchannel = null;
				try {
					mchannel = new MChannelConnect();
					String message = "";
					String sender_name = "";
					String sender_id = "";
					String target_id = "";
					String target_name = "";
					String sql3 = "";
					String url = "";
					int isOk112 = 0;
					ArrayList list = null;
					DataBox dbox = null;
	
					sql3 = "SELECT TZ_MANAGER.GADMIN,TZ_MEMBER.CONO,TZ_MEMBER.NAME FROM TZ_MEMBER,TZ_MANAGER";
					sql3 += " WHERE (TZ_MEMBER.USERID = TZ_MANAGER.USERID) and tz_manager.gadmin = 'A1'";
					list = new ArrayList();
					ls = connMgr.executeQuery(sql3);
					while (ls.next()) {
						 dbox = ls.getDataBox();
						 list.add(dbox);
					}
					
					for (int i = 0; i < list.size() ;i++ ){
						dbox = (DataBox)list.get(i);
						target_id = dbox.getString("d_cono");
						target_name= dbox.getString("d_name");
						
						//message += v_contents;
						sender_name = box.getSession("name");
						sender_id = box.getSession("userid");
	
						url = "http://beta.hkhrd.com/";
						message = "베타테스트시스템 Q&A에 "+sender_name+"님이 아래와 같은 제목으로 질문을 남겼습니다.\n\r\n\r";
						message += v_title;
					 
						isOk112 = mchannel.sendMessage(sender_id, sender_name, target_id, target_name, message,url );

					}
					
					ls.close();
					
				}catch(Exception ex) {ex.printStackTrace();
				}finally {
					if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
					if(ls != null) { try { ls.close(); } catch (Exception e) {} }
					if(mchannel != null) { try { mchannel.freeConnection(); }catch (Exception e10) {} }       
					if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } 
				}

            }
            
            connMgr.commit();
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            FileManager.deleteFile(v_newMotionName);		                      
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }            
        }
        return isOk1*isOk2;
    }


    /**
    * QNA 등록할때(답변)
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertQnaAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql  = "";
        String sql1 = "";
        int isOk  = 0;
		int isOk2  = 0;
		int isOk3  = 0;

        int    v_seq     = box.getInt("p_seq");
        String v_types   = "";
        String v_title   = box.getString("p_title");
        String v_contents =  StringManager.replace(box.getString("content"),"&","&amp;");
        String v_isopen  = "Y";
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
		Vector newFileNames = box.getNewFileNames("p_file"); 

        try {
        	connMgr = new DBConnectionManager();
        	//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
			sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql1);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			//------------------------------------------------------------------------------------
			sql  = " select max(to_number(types)) from TZ_HOMEQNA  ";
			sql += "  where tabseq = " + v_tabseq + " and seq = " + v_seq;
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
		       v_types = String.valueOf((ls.getInt(1) + 1));
			} else {
		        v_types = "1";
			}
			
			if (newFileNames.size() != 0){
				////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
	            sql1 =  "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, upfile) ";
	            sql1 += "	values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?) ";
	            
	            pstmt = connMgr.prepareStatement(sql1);
				pstmt.setInt(1, v_tabseq);
				pstmt.setInt(2, v_seq);
				pstmt.setString(3, v_types);
				pstmt.setString(4, v_title);
				pstmt.setCharacterStream(5,  new StringReader(v_contents), v_contents.length());
				pstmt.setString(6,  s_userid);
				pstmt.setString(7,  v_isopen);
				pstmt.setString(8, s_userid);
				pstmt.setString(9, (String)newFileNames.elementAt(0));
			}else{
				//////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
	            sql1 =  "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate) ";
	            sql1 += " 	 values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
	            
	            pstmt = connMgr.prepareStatement(sql1);
				pstmt.setInt(1, v_tabseq);
				pstmt.setInt(2, v_seq);
				pstmt.setString(3, v_types);
				pstmt.setString(4, v_title);
				pstmt.setCharacterStream(5,  new StringReader(v_contents), v_contents.length());
				pstmt.setString(6,  s_userid);
				pstmt.setString(7,  v_isopen);
				pstmt.setString(8, s_userid);
			}

           isOk = pstmt.executeUpdate();
		   isOk2 = this.insertUpFile(connMgr, v_seq, box);       //      파일첨부했다면 파일table에  insert            
           isOk3 = this.deleteUpFile(connMgr, box);  
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    * QNA 수정하여 저장할때
    * @param box      receive from the form object and session
    * @return isOk    1:update success,0:update fail
    * @throws Exception
    */
     public int updateQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
		String sql1 = "";
		ListSet ls = null;
        int isOk = 0;
		int isOk2 = 0;
		int isOk3 = 0;
		
        int v_seq          = box.getInt("p_seq");
		
        String v_types     = box.getString("p_types");
		
        String v_title   = box.getString("p_title");
		
        String v_contents =  StringManager.replace(box.getString("content"),"&","&amp;");   
		
        String v_isopen  = "Y";
		String v_savemotion = box.getString("p_savemotion");  
		Vector newFileNames = box.getNewFileNames("p_file"); 
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
			sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
			ls = connMgr.executeQuery(sql1);
			ls.next();
			int v_tabseq = ls.getInt(1);
			ls.close();
			
           if (newFileNames.size() == 0){
				sql  = " update TZ_HOMEQNA set title = ? , contents = ?, isopen = ? , ";
				sql += "  	luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
				sql += "  where tabseq = ? and seq = ? and types = ?                                      ";

				pstmt = connMgr.prepareStatement(sql);

				pstmt.setString(1,  v_title);
	//          pstmt.setString(2,  v_contents);
				pstmt.setCharacterStream(2,  new StringReader(v_contents), v_contents.length());
				pstmt.setString(3,  v_isopen);
				pstmt.setString(4,  s_userid);
				pstmt.setInt(5,  v_tabseq);
				pstmt.setInt(6,  v_seq);
				pstmt.setString(7,  v_types);

				isOk = pstmt.executeUpdate();
           }else{
				sql  = " update TZ_HOMEQNA set title = ? , contents = ?, isopen = ? , ";
				sql += "   luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'), upfile = ? ";
				sql += "  where tabseq = ? and seq = ? and types = ?  ";

				pstmt = connMgr.prepareStatement(sql);

				pstmt.setString(1,  v_title);
				pstmt.setCharacterStream(2,  new StringReader(v_contents), v_contents.length());
				pstmt.setString(3,  v_isopen);
				pstmt.setString(4,  s_userid);
				pstmt.setString(5, (String)newFileNames.elementAt(0));
				pstmt.setInt(6,  v_tabseq);
				pstmt.setInt(7,  v_seq);
				pstmt.setString(8,  v_types);

				isOk = pstmt.executeUpdate();
		   
		   }

			isOk2 = this.insertUpFile(connMgr, v_seq, box);       //      파일첨부했다면 파일table에  insert            
            isOk3 = this.deleteUpFile(connMgr, box);        //     삭제할 파일이 있다면 파일table에서 삭제
 
            if(isOk > 0 && isOk2 > 0 && isOk3 > 0) {
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);         //	 DB 에서 모든처리가 완료되면 해당 첨부파일 삭제
                }
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            }
            Log.info.println(this, box, "update process to " + v_seq);
        }
        catch(Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    * QNA 삭제할때
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int deleteQna(RequestBox box) throws Exception {
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
		
        int    v_seq     = box.getInt("p_seq");
		String v_types   = box.getString("p_types");
		
		Vector savefile  = box.getVector("p_savefile");
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
            if (v_types.equals("0")) {                 // 질문삭제시 답변동시삭제
                sql1  = " delete from TZ_HOMEQNA    ";
                sql1 += "  where tabseq = ? and seq = ?";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);

								
            } else {
                sql1  = " delete from TZ_HOMEQNA";
                sql1 += "  where tabseq = ? and seq = ? and types = ?  ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);
                pstmt1.setString(3, v_types);
           }
			
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
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }            
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1 * isOk2;
    }	    

///////////////////////////////////////////////////////  파일 테이블   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
     /**
    * Q&A 파일 입력하기 
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
        
        try 
        {
			
        	if(realFileNames != null) {          //  
				// 어떤 게시판인지정보를  가져와 tabseq를 리턴한다.
				sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
	
	            // 자료 번호 가져온다.
				sql = "select isnull(max(fileseq), 0) from tz_boardfile where tabseq = " + v_tabseq ;
				ls = connMgr.executeQuery(sql);
				ls.next();
	
				int v_fileseq = ls.getInt(1) + 1;
					
				ls.close();
	                
	            // 파일 table 에 입력.
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
        } catch (Exception ex) {
            FileManager.deleteFile(newFileNames);		//  일반파일, 첨부파일 있으면 삭제..            
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
        	if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }  
        }
        
        return isOk2;
    }
    
   /**
    * Q&A 파일 삭제하기 
    * @param box          receive from the form object and session
    * @return isOk3 삭제에 성공하면 1을 리턴한다
    */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null; 
		String sql = "";
        String sql3 = "";
        int isOk3 = 1;
        ListSet ls = null;
        int v_seq = box.getInt("p_seq");
        Vector v_savefileVector = box.getVector("p_savefile");  
		String v_types   = box.getString("p_types");
        
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

			if (v_types.equals("0")){
				for(int i = 0; i < v_savefileVector.size(); i++) {
					
						String v_savefile = (String)v_savefileVector.elementAt(i);                     
										
						pstmt3.setInt(1, v_seq);
						
						pstmt3.setString(2, v_savefile);
						
						isOk3 = pstmt3.executeUpdate();
				}
			}else{
                for(int i = 0; i < v_savefileVector.size(); i++) {
					if (v_savefileVector.size() == Integer.parseInt(v_types)){
						String v_savefile = (String)v_savefileVector.elementAt(i);                     
										
						pstmt3.setInt(1, v_seq);
						
						pstmt3.setString(2, v_savefile);
						
						isOk3 = pstmt3.executeUpdate();
					}
				}	
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
	
	
	/**
    * Q&A 상세보기 
    * @param box          receive from the form object and session
    * @return DataBox	  조회한 값을 DataBox에 담아 리턴
    */
   public DataBox selectQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
                
        int v_seq = box.getInt("p_seq");
		
        String v_types = box.getString("p_types");
		
		String v_fileseq = box.getString("p_fileseq");
   
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
            sql = "select a.types, a.seq, a.inuserid, a.title, a.contents, b.realfile, b.savefile, a.indate ,c.name";
            sql += " from tz_homeqna a, tz_boardfile b, tz_member c";
			/* 2005.11.15_하경태 : Oracle -> Mssql
            sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) ";
			sql += " and a.inuserid = c.userid(+)";
			sql += " and a.upfile = b.savefile(+) ";
			sql += " and a.tabseq = " + v_tabseq + " and a.seq = "+v_seq+" and types = " + v_types;
			*/
			sql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) ";
			sql += " and a.inuserid  =  c.userid(+) ";
			sql += " and a.upfile  =  b.savefile(+) ";
			sql += " and a.tabseq = " + v_tabseq + " and a.seq = "+v_seq+" and types = " + v_types;
           
            ls = connMgr.executeQuery(sql); 
        
            while(ls.next()) {
            //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();

                realfileVector.addElement(ls.getString("realfile"));
                savefileVector.addElement(ls.getString("savefile"));
            }   
            connMgr.executeUpdate("update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = "+v_seq + "and types = "+ v_types);
            dbox.put("d_realfile", realfileVector);
            dbox.put("d_savefile", savefileVector);
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

}
