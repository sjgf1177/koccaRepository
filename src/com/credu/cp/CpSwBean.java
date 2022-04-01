
//**********************************************************
//  1. 제      목: 외주관리시스템의 빈
//  2. 프로그램명: CpSwBean.java
//  3. 개      요: 외주관리시스템의 빈
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.08.11 이연정
//  7. 수      정: 05.11.15 이나연
//**********************************************************
package com.credu.cp;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;


public class CpSwBean {
    private ConfigSet config;
    private int row;
	private String v_type = "AC";
	private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 5;					//	  페이지에 세팅된 파일첨부 갯수

    public CpSwBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

	/**
    * SW자료실 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   SW 리스트
    */
    public ArrayList selectPdsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
		
        
        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
System.out.println("v_select:::"+v_select);
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

            sql = "select a.seq, a.userid, a.name, a.title, count(b.realfile) filecnt, a.indate, a.cnt ";
            sql += "from tz_board a, tz_boardfile b" ;
			// 수정일 : 05.11.15 수정자 : 이나연 _(+)  수정
//          sql += " where a.seq = b.seq(+) and a.tabseq = b.tabseq(+) and a.tabseq = " + v_tabseq;
			sql += " where a.seq  =  b.seq(+) and a.tabseq  =  b.tabseq(+) and a.tabseq = " + v_tabseq;
                        
            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
                v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다
                
                if (v_select.equals("name")) {      //    이름으로 검색할때
                    sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("title")) {     //    제목으로 검색할때
                    sql += " and a.title like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_select.equals("content")) {     //    내용으로 검색할때
                    sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                }                                                                                                                     
            
            }
            sql += " group by a.seq, a.userid, a.name, a.title, a.indate, a.cnt";
            sql += " order by a.seq desc";  		

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();    //     전체 row 수를 반환한다

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
    * SW자료실 화면 리스트 3개 뽑기
    * @param box          receive from the form object and session
    * @return ArrayList   SW 리스트
    */
    public ArrayList selectPdsListNum(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";	
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
			// 수정일 : 05.11.15 수정자 : 이나연 _ rownu, 수정
			sql  = " select * from ( select rownum rnum,  seq, userid, name, title, indate, cnt ";
            sql += " from tz_board a " ;
            sql += " where  a.tabseq = " + v_tabseq;
			sql += "  order by seq desc ) where rnum < 4"  ;
			
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();    //     전체 row 수를 반환한다

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
    * SW자료실 상세보기 
    * @param box          receive from the form object and session
    * @return DataBox	  조회한 값을 DataBox에 담아 리턴
    */
   public DataBox selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
		
        
        int v_seq       = box.getInt("p_seq");
        int v_upfilecnt = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector  = new Vector();



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
            sql += " from tz_board a, tz_boardfile b "; 
			// 05.11.15 이나연 _(+)  수정
//          sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.tabseq = " + v_tabseq + " and a.seq = "+ v_seq;
	        sql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.tabseq = " + v_tabseq + " and a.seq = "+ v_seq;
			ls = connMgr.executeQuery(sql); 
			for (int i = 0; ls.next(); i++) {

            //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();

                realfileVector.addElement(ls.getString("realfile"));
                savefileVector.addElement(ls.getString("savefile"));
                fileseqVector.addElement(String.valueOf(ls.getInt("fileseq")));

            }   
			if (realfileVector  != null) dbox.put("d_realfile", realfileVector);
            if (savefileVector  != null) dbox.put("d_savefile", savefileVector);
            if (fileseqVector   != null) dbox.put("d_fileseq", fileseqVector);

           
            //------------------------------------------------------------------------------------------------------------------------------------
            
            connMgr.executeUpdate("update tz_board set cnt = cnt + 1 where tabseq = "+ v_tabseq + "and  seq = "+v_seq);        
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
    * SW자료실 등록하기 
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
            sql = "select NVL(max(seq), 0) from tz_board";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------
                   
            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into tz_board(tabseq, seq, userid, name, indate, title, cnt, luserid, content, ldate)";
//          sql1 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'))";
          sql1 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";
            
int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);
			
            pstmt1.setInt   (index++, v_tabseq);
            pstmt1.setInt   (index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);             
            pstmt1.setInt   (index++, 0);
            pstmt1.setString(index++, s_userid);   
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
//			pstmt1.setString(index++, v_content);           
            
            isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
//            sql2 = "select content from tz_board where tabseq = " + v_tabseq + " and  seq = " + v_seq;            
			
//            connMgr.setOracleCLOB(sql2, v_content);       //      (기타 서버 경우)       
            /* 05.11.15 이나연
            
           // isOk2 = this.insertUpFile(connMgr, v_seq, box);
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);


			*/
            if(isOk1 > 0) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            }
        }
        catch (Exception ex) {
//          if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            FileManager.deleteFile(v_newMotionName);		                      
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
//          if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }            
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }            
        }
        return isOk1*isOk2;
    }
    
   /**
    * SW자료실 수정하기 
    * @param box          receive from the form object and session
    * @return isOk1*isOk2*isOk3	  수정에 성공하면 1을 리턴한다
    */
     public int updatePds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		ListSet ls = null;
        Connection conn = null;
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

        Vector v_savefile     = box.getVector("p_savefile"); //선택삭제파일
        Vector v_filesequence = box.getVector("p_fileseq");  //선택삭제파일 sequence
        Vector v_realfile     = box.getVector("p_file");     //새로 등록 파일
        String v_userid = box.getString("p_userid"); 
        String s_gadmin = box.getSession("gadmin");
		String s_userid = "";
		String s_usernm = "";
System.out.println("11111111111111111111111111111111111111111111111111");
System.out.println("upfilecnt:::::"+v_upfilecnt);


		for(int	i =	0; i < v_upfilecnt;	i++) {
			if(	!box.getString("p_fileseq" + i).equals(""))	{
System.out.println("fileseq::::"+box.getString("p_fileseq" + i));
				v_savefile.addElement(box.getString("p_savefile" + i));			//		서버에 저장되있는 파일명 중에서	삭제할 파일들
				v_filesequence.addElement(box.getString("p_fileseq"	+ i));		 //		서버에	저장되있는 파일번호	중에서 삭제할 파일들

			}
		}


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
            

                sql1 = "update tz_board set title = ?, userid = ?, name = ?, luserid = ?, content = ?, indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql1 += "  where tabseq = " + v_tabseq + " and seq = ?";                   

                pstmt1 = connMgr.prepareStatement(sql1);
                
                pstmt1.setString(1, v_title);
                pstmt1.setString(2, v_userid);
                pstmt1.setString(3, s_usernm);
                pstmt1.setString(4, s_userid);
				pstmt1.setString(5, v_content);
                pstmt1.setInt   (6, v_seq);          
           
            isOk1 = pstmt1.executeUpdate();
            sql2 = "select content from tz_board where tabseq = " + v_tabseq +" and seq = " + v_seq;            

			connMgr.setOracleCLOB(sql2, v_content);       //      (기타 서버 경우)       
			/* 05.11.15 이나연

			isOk2 =	this.insertUpFile(connMgr, v_tabseq, v_seq,	box);		//		파일첨부했다면 파일table에	insert
System.out.println("isOk2::::::::::::"+isOk2);
			isOk3 =	this.deleteUpFile(connMgr,  v_tabseq, v_seq,v_filesequence);		//	   삭제할 파일이 있다면	파일table에서 삭제
System.out.println("isOk3::::::::::::"+isOk3);
			if(isOk1 > 0 &&	isOk2 >	0 && isOk3 > 0)	{
				connMgr.commit();
				if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 DB	에서 모든처리가	완료되면 해당 첨부파일 삭제
				}
			} else connMgr.rollback();

/*

            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);         //	 DB 에서 모든처리가 완료되면 해당 첨부파일 삭제
                }
            }
            Log.info.println(this, box, "update process to " + v_seq);*/
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
        }
        catch(Exception ex) {
//          if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            ErrorManager.getErrorStackTrace(ex, box, sql1);            
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
//          if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }            
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
//      return isOk1*isOk2*isOk3;
		return isOk1;
    }	

    
    /**
    * SW자료실 삭제하기 
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

        Vector v_savefile  = box.getVector("p_savefile");
        int v_upfilecnt = v_savefile.size();    //  서버에 저장되있는 파일수
       
            
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


                if (v_upfilecnt > 0 ) {
                    sql2 = "delete from TZ_BOARDFILE where tabseq = "+ v_tabseq+" and seq = "+v_seq;
                    isOk2 = connMgr.executeUpdate(sql2);
				}


          /*  for (int i = 0; i < savefile.size() ;i++ ){
				String str = (String)savefile.elementAt(i);
				if (!str.equals("")){
			//		isOk2 =	this.deleteUpFile(connMgr, box,	v_filesequence);
				}
			}
            
            if (isOk1 > 0 && isOk2 > 0) {		                
                if (savefile != null) {
                    FileManager.deleteFile(savefile);         //	 첨부파일 삭제
                }
                if (v_savemotion != null) {
                   // FileManager.deleteFile(v_savemotion); 
                }
*/
	
                if (isOk1 > 0 && isOk2 > 0) {
                    connMgr.commit();
                    if (v_upfilecnt>0) {
                        FileManager.deleteFile(v_savefile);         //   첨부파일 삭제
                    }
                } else connMgr.rollback();

            
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
    * SW자료실 파일 입력하기 
    * @param box          receive from the form object and session
    * @return isOk2 입력에 성공하면 1을 리턴한다

     public int insertUpFile(DBConnectionManager connMgr, int p_seq, RequestBox box) throws Exception {    
        ListSet ls = null;
        PreparedStatement pstmt2 = null;     
        String sql = "";   
        String sql2 = "";
        int isOk2 = 1;  
		int v_seq = box.getInt("p_seq");
		
        
        //----------------------   업로드되는 파일 --------------------------------
        Vector realFileNames = box.getRealFileNames("p_file");  
        Vector newFileNames = box.getNewFileNames("p_file");                
        //----------------------------------------------------------------------------------------------------------------------------
               
        String s_userid = box.getSession("userid");
        
         try {
			 //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
				sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
				//------------------------------------------------------------------------------------
            if(realFileNames != null) {          //     
                 //----------------------   자료 번호 가져온다 ----------------------------
                sql = "select NVL(max(fileseq), 0) from tz_boardfile where tabseq = " + v_tabseq;
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
    */


///////////////////////////////////////////////////////  파일 테이블   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
    * 새로운 자료파일 등록
    * @param connMgr  DB Connection Manager
    * @param p_seq    게시물 일련번호
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
     public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox   box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        //----------------------   업로드되는 파일의 형식을 알고 코딩해야한다  --------------------------------
		String [] v_realFileName = new String [FILE_LIMIT];
		String [] v_newFileName	= new String [FILE_LIMIT];

		for(int	i =	0; i < FILE_LIMIT; i++)	{
			v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
			v_newFileName [i] =	box.getNewFileName(FILE_TYPE + (i+1));
		}

        String s_userid = box.getSession("userid");

        try {

            //----------------------   자료 번호 가져온다 ----------------------------
            sql = "select NVL(max(fileseq), 0) from TZ_BOARDFILE where tabseq = " + p_tabseq +" and seq =   " + p_seq;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            //////////////////////////////////   파일 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql2 =  "insert into TZ_BOARDFILE(tabseq, seq, fileseq, realfile, savefile, luserid, ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);

			for(int	i =	0; i < FILE_LIMIT; i++)	{
				if(	!v_realFileName	[i].equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다

					pstmt2.setInt(1, p_tabseq);
					pstmt2.setInt(2, p_seq);
					pstmt2.setInt(3, v_fileseq);
					pstmt2.setString(4, v_realFileName[i]);
					pstmt2.setString(5, v_newFileName[i]);
					pstmt2.setString(6, s_userid);

					isOk2 = pstmt2.executeUpdate();
					v_fileseq++;
				}
			}
        }
		catch (Exception ex) {
			FileManager.deleteFile(v_newFileName, FILE_LIMIT);		//	일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex,	box, sql2);
			throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
		}
		finally	{
		    if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}

    /**
    * SW자료실 파일 삭제하기 
    * @param box          receive from the form object and session
    * @return isOk3 삭제에 성공하면 1을 리턴한다

    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null;        
        String sql3 = "";
        int isOk3 = 1;
        int v_seq = box.getInt("p_seq");
        Vector v_savefileVector = box.getVector("p_savefile"); 
		
        String sql = "";
		ListSet ls = null;
        try { 
			//----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
				sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
				ls = connMgr.executeQuery(sql);
				ls.next();
				int v_tabseq = ls.getInt(1);
				ls.close();
				//------------------------------------------------------------------------------------
            sql3 = "delete from tz_boardfile where tabseq = ? and seq = ? and savefile = ?";
            pstmt3 = connMgr.prepareStatement(sql3);
                
            for(int i = 0; i < v_savefileVector.size(); i++) {
                String v_savefile = (String)v_savefileVector.elementAt(i);                     
                pstmt3.setInt(1, v_tabseq);                
                pstmt3.setInt(2, v_seq);
                pstmt3.setString(3, v_savefile);
                
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
 */
	/*
	 * 선택된 자료파일 DB에서 삭제
	 * @param connMgr			DB Connection Manager
	 * @param box				receive from the form object and session
	 * @param p_filesequence    선택 파일 갯수
	 * @return
	 * @throws Exception

	public int deleteUpFile(DBConnectionManager	connMgr,  RequestBox box, Vector p_filesequence)	throws Exception {
		PreparedStatement pstmt3 = null;
		String sql	= "";
		String sql3	= "";
        ListSet ls = null;
		int	isOk3  =	1;
        int v_tabseq     = box.getInt("p_tabseq");
		int	v_seq        =	box.getInt("p_seq");
System.out.println("v_tabseq:::"+v_tabseq);
System.out.println("v_seq::::"+v_seq);

		try	{

System.out.println("v_tabseq::::"+v_tabseq);

			sql3 = "delete from TZ_BOARDFILE where tabseq = "+ v_tabseq +" and seq =? and fileseq = ?";

			pstmt3 = connMgr.prepareStatement(sql3);
			for(int	i =	0; i < p_filesequence.size(); i++) {
				int	v_fileseq =	Integer.parseInt((String)p_filesequence.elementAt(i));
//ystem.out.println("v_tabseq::::"+v_seq);
//System.out.println("v_fileseq::::"+v_fileseq);
				pstmt3.setInt(1, v_seq);
				pstmt3.setInt(2, v_fileseq);
				isOk3 =	pstmt3.executeUpdate();
//System.out.println("isOK3 fileDelete::::::::::::::"+isOk3);


			}
		}
		catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box,	sql3);
			throw new Exception("sql = " + sql3	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {}	}
		}
		return isOk3;
	}
	
*/
	/*
	 * 선택된 자료파일 DB에서 삭제
	 * @param connMgr			DB Connection Manager
	 * @param box				receive from the form object and session
	 * @param p_filesequence    선택 파일 갯수
	 * @return
	 * @throws Exception
*/
	public int deleteUpFile(DBConnectionManager	connMgr,  int v_tabseq, int v_seq, Vector p_filesequence)	throws Exception {
		PreparedStatement pstmt3 = null;
		String sql	= "";
		String sql3	= "";
        ListSet ls = null;
		int	isOk3  =	1;
        //int v_tabseq     = box.getInt("p_tabseq");
		//int	v_seq        =	box.getInt("p_seq");
//System.out.println("v_tabseq:::"+v_tabseq);
//System.out.println("v_seq::::"+v_seq);

		try	{

//System.out.println("v_tabseq::::"+v_tabseq);

			sql3 = "delete from TZ_BOARDFILE where tabseq = "+ v_tabseq +" and seq =? and fileseq = ?";

			pstmt3 = connMgr.prepareStatement(sql3);
			for(int	i =	0; i < p_filesequence.size(); i++) {
				int	v_fileseq =	Integer.parseInt((String)p_filesequence.elementAt(i));
//ystem.out.println("v_tabseq::::"+v_seq);
//System.out.println("v_fileseq::::"+v_fileseq);
				pstmt3.setInt(1, v_seq);
				pstmt3.setInt(2, v_fileseq);
				isOk3 =	pstmt3.executeUpdate();
//System.out.println("isOK3 fileDelete::::::::::::::"+isOk3);


			}
		}
		catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex);
			throw new Exception("sql = " + sql3	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {}	}
		}
		return isOk3;
	}
	





}
