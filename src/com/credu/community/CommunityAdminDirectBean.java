//**********************************************************
//1. 제      목:
//2. 프로그램명: CommunityCreateBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityAdminDirectBean {
    private ConfigSet config;
    private static int row=10;
    private static String v_type = "PQ";
    private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 10;                    //    페이지에 세팅된 파일첨부 갯수


    public void CommunityAdminDirectBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
            row = 10; //강제로 지정
            System.out.println("....... row.....:"+row);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getFILE_LIMIT(){
    	return FILE_LIMIT;
    }
    
    public void CommunityAdminDirectBean(String type) {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
            this.v_type = type;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
    * 공지사항 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   공지사항 리스트
    * @throws Exception
    */
    public ArrayList selectDirectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql     = "";
        String count_sql = "";
        String head_sql  = "";
		String body_sql  = "";		
        String group_sql = "";
        String order_sql = "";
        DataBox             dbox    = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select     = box.getString("p_select");
        String v_faq_type   = box.getStringDefault("p_faq_type", "DIRECT");
		//String v_grcode		= box.getString("p_grcode");
		String v_grtype		= box.getString("p_grtype");
        int v_pageno        = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
			// 수정일 : 05.11.10 수정자 : 이나연_ rownum 수정
//            sql  = "\n select a.*,rownum rowseq from ("
			  head_sql  =  " select a.faq_type faq_type,a.faqno faqno,a.title title,a.content content,a.read_cnt read_cnt " ;
			  head_sql += "\n       ,a.add_cnt add_cnt,a.parent parent,a.lv lv,a.position position " ;
			  head_sql += "\n       ,a.register_userid register_userid,a.register_dte register_dte " ;
			  head_sql += "\n       ,a.modifier_userid modifier_userid,a.modifier_dte modifier_dte " ;
			  head_sql += "\n       ,b.userid userid,b.resno resno,b.name name,b.email email,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm " ; 
			  head_sql += "\n      ,isnull(c.cnt,0) cnt " ;
			  body_sql += "\n   from tz_cmufaq a,tz_member b " ;
			  body_sql += "\n       ,(select faq_type faq_type,faqno faqno, count(*) cnt from tz_cmufaqfile where faq_type = '"+v_faq_type+"' group by faq_type,faqno) c " ;
			  body_sql += "\n  where a.register_userid = b.userid " ;  
			  //body_sql += "\n    and a.grtype = '" + v_grtype + "'" ; 
                 // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//                 + "\n    and a.faq_type        = c.faq_type(+)" 
//                 + "\n    and a.faqno           = c.faqno(+)"
			  body_sql += "\n    and a.faq_type        =  c.faq_type(+) " ; 
			  body_sql += "\n    and a.faqno           =  c.faqno(+) " ;
			  body_sql += "\n    and a.faq_type        = '"+v_faq_type+"'" ;
            
            if ( !v_searchtext.equals("")) {      // 검색어가 있으면
                 if (v_select.equals("title"))   body_sql += "\n and lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")";
                 if (v_select.equals("content")) body_sql += "\n and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                 if (v_select.equals("name"))    body_sql += "\n and lower(b.name) like lower (" +  StringManager.makeSQL("%" + v_searchtext + "%")+")";            //   Oracle 9i 용
            }

            order_sql += "\n order by a.root desc,a.position asc" ;
//                 + "\n ) a";
			sql= head_sql+ body_sql+ group_sql+ order_sql;
                 System.out.println(sql);
            ls = connMgr.executeQuery(sql);
			
			count_sql= "select count(*) " + body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);                         // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);// 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();    // 전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();  // 전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount" , new Integer(row));
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
    * 공지사항 조회
    * @param box          receive from the form object and session
    * @return ArrayList   공지사항 조회
    * @throws Exception
    */
    public ArrayList selectViewQna(RequestBox box,String qryFlag) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt   = null;
        ListSet             ls      = null;
        ArrayList           list1   = new ArrayList();
        ArrayList           list2   = new ArrayList();
        ArrayList           list3   = new ArrayList();
        ArrayList           list4   = new ArrayList();
        ArrayList           list    = new ArrayList();
        
        Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

        StringBuffer        sql     = new StringBuffer();
        DataBox             dbox    = null;
        int                 isOK    =1;

        String v_faq_type   = box.getStringDefault("p_faq_type", "KICPA");
        String v_grtype     = box.getStringDefault("p_grtype", "KICPA");
        int    v_faqno      = box.getInt("p_faqno");
        int    v_rowseq     = box.getInt("p_rowseq");
        
        try {
            connMgr = new DBConnectionManager();

            //조회수 증가.
            if("VIEW".equals(qryFlag)){
                sql.append(" update tz_cmufaq set read_cnt =read_cnt+1");
                sql.append("  where faq_type        = '"+v_faq_type+"'");
                sql.append("    and faqno           = "+v_faqno);
                    
                pstmt = connMgr.prepareStatement(sql.toString());
                isOK = pstmt.executeUpdate();
            }  
            
            sql.setLength(0);
            pstmt.close();

            //본문내용읽기
            sql.append(" SELECT  A.FAQ_TYPE, A.FAQNO, A.TITLE, A.CONTENT, A.READ_CNT    \n");
            sql.append("         , A.ADD_CNT ,A.PARENT ,A.LV ,A.POSITION ,A.ROOT        \n");
            sql.append("         , A.REGISTER_USERID, A.REGISTER_DTE, A.MODIFIER_USERID \n");
            sql.append("         , A.MODIFIER_DTE, B.USERID, B.RESNO, B.NAME, B.EMAIL   \n");
            sql.append("         , B.DEPTNAM, B.JIKUPNM, B.JIKWINM                      \n");
            sql.append("         , C.FILENO FILESEQ, C.SAVEFILE, C.REALFILE             \n");
            sql.append("  FROM   TZ_CMUFAQ A ,TZ_MEMBER B, TZ_CMUFAQFILE C              \n");
            sql.append(" WHERE   A.REGISTER_USERID = B.USERID                           \n");
            sql.append("   AND   A.FAQ_TYPE        =  C.FAQ_TYPE(+)                     \n");
            sql.append("   AND   A.FAQNO           =  C.FAQNO(+)                        \n");
            sql.append("   AND   A.FAQ_TYPE        = '"+v_faq_type+"'                   \n");
            sql.append("   AND   A.FAQNO           = '"+v_faqno+"'                      \n");

            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                dbox = ls.getDataBox();
                if( !dbox.getString("d_realfile").equals("")){
					realfileVector.addElement(dbox.getString("d_realfile"));
	                savefileVector.addElement(dbox.getString("d_savefile"));
	                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
				}
                
            }
            
            if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);
			
			list1.add(dbox);
			
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            
            sql.setLength(0);
            pstmt.close();

            sql.append(" SELECT  'PREV' GUBUN, FAQ_TYPE, FAQNO, TITLE         \n");
            sql.append(" FROM    (                                            \n");
            sql.append("         SELECT  A.FAQ_TYPE, A.FAQNO, A.TITLE         \n");
            sql.append("          FROM   TZ_CMUFAQ A                          \n");
            sql.append("         WHERE   A.FAQ_TYPE        = '"+v_faq_type+"' \n");
            //sql.append("           AND   A.GRTYPE          = '"+v_grtype+"'   \n");
            sql.append("           AND   A.FAQNO           < "+v_faqno+"      \n");
            sql.append("         ORDER BY A.FAQNO DESC                        \n");
            sql.append("         )                                            \n");
            sql.append(" WHERE   ROWNUM = 1                                   \n");
            sql.append(" UNION ALL                                            \n");
            sql.append(" SELECT  'NEXT' GUBUN, FAQ_TYPE, FAQNO, TITLE         \n");
            sql.append(" FROM    (                                            \n");
            sql.append("         SELECT  A.FAQ_TYPE, A.FAQNO, A.TITLE         \n");
            sql.append("          FROM   TZ_CMUFAQ A                          \n");
            sql.append("         WHERE   A.FAQ_TYPE        = '"+v_faq_type+"' \n");
            //sql.append("           AND   A.GRTYPE          = '"+v_grtype+"'   \n");
            sql.append("           AND   A.FAQNO           > "+v_faqno+"      \n");
            sql.append("         ORDER BY A.FAQNO ASC                         \n");
            sql.append("         )                                            \n");
            sql.append(" WHERE   ROWNUM = 1                                   \n");

            ls = connMgr.executeQuery(sql.toString());
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list2.add(dbox);
            }

            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            pstmt.close();
        	sql.setLength(0);
        	
            //댓글읽기
        	sql.append(" SELECT  A.FAQ_TYPE, A.FAQNO, A.RPLNO, A.CONTENT, A.USERID \n");
        	sql.append("         , A.REGISTER_DTE, A.MODIFIER_DTE, B.RESNO, B.NAME \n");
        	sql.append("         , B.EMAIL, B.DEPTNAM, B.JIKUPNM, B.JIKWINM        \n");
        	sql.append("   FROM  TZ_CMUFAQREPLAY A, TZ_MEMBER B                    \n");
        	sql.append("  WHERE  A.USERID    = B.USERID                            \n");
        	sql.append("    AND  A.FAQ_TYPE  = '"+v_faq_type+"'                    \n");
        	sql.append("    AND  A.FAQNO     = "+v_faqno+"                         \n");
        	sql.append("  ORDER BY A.RPLNO DESC                                    \n");

            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                dbox = ls.getDataBox();
                list3.add(dbox);
            }

            list.add(list1);
            list.add(list2);
            list.add(list3);
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    * 공지사항등록하기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertQnA(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         v_seq       = 0;
        int         v_faqno     = 0; 

        String v_faq_type	= box.getString("p_faq_type");
        String v_content	= StringManager.replace(box.getString("content"),"<br>","\n");
        String s_userid		= box.getSession("userid");
        String s_name		= box.getSession("name");
        String v_grcode		= box.getString("s_grcode");
		//String v_grtype		= GetCodenm.get_grtype(box,v_grcode);
		String v_grtype		= box.getStringDefault("p_grtype","KOCCA");

        String thisYear="";
        String v_templetfile = "";
        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 

           //공지사항 faqno번호를 추출한다.
           sql = "select isnull(max(faqno), 0) from tz_cmufaq where faq_type = '" +v_faq_type+ "'";
           ls = connMgr.executeQuery(sql);
		   
           while (ls.next()) {
               v_faqno = ls.getInt(1) + 1;
           } 
           int tmp =0;
if(ls != null) { try { ls.close(); }catch (Exception e) {} }
           sql = "select isnull(max(position), 0) from tz_cmufaq where faq_type = '" +v_faq_type+ "'";
		   
           ls = connMgr.executeQuery(sql);
           while (ls.next()) {
               tmp = ls.getInt(1) + 1;
           } 

		   /*********************************************************************************************/
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
		   try {
			   v_content =(String) NamoMime.setNamoContent(v_content);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return 0;
		   }
		   /*********************************************************************************************/       


           sql  =" insert into tz_cmufaq ( faq_type     , faqno          , title       , content   , read_cnt"
                +"                       , add_cnt      , lv          , position  , register_userid"
                +"                       , register_dte , modifier_userid, modifier_dte, del_fg,parent,root, grtype)"
//                +"               values  (?,?,?,empty_clob(),?"
                +"               values  (?,?,?,?,?"
                +"                       ,?,?,?,?"
                +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),?"
                +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N',?,?,?)"
                ;
           int index = 1;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(index++, v_faq_type                         );//faq분류
           pstmt.setInt   (index++, v_faqno                            );//일련번호
           pstmt.setString(index++, box.getString("p_title" )          );//제목
		   pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
//           pstmt.setString(index++, v_content          );//제목
           pstmt.setInt   (index++, 0                                  );//조회수
           pstmt.setInt   (index++, 0                                  );//추천수
           pstmt.setInt   (index++, 1         );//답변레벨
           pstmt.setInt   (index++, tmp         );//답변위치
           pstmt.setString(index++, s_userid);//게시자
           pstmt.setString(index++, s_userid );//수정자
           pstmt.setInt   (index++, v_faqno                             );//부모
           pstmt.setInt   (index++, v_faqno                             );//부모
           pstmt.setString(index++, v_grtype                            );//Grtype
		   
           isOk1 = pstmt.executeUpdate();

            isOk2 = this.insertUpFile(connMgr,v_faq_type, v_faqno, box);
//		   String sql1 = "select content from tz_cmufaq where faqno = '"+v_faqno+"' and faq_type = '"+v_faq_type+"' ";
//		   connMgr.setOracleCLOB(sql1, v_content);
            
            if(isOk1 > 0 && isOk2 > 0){
            	connMgr.commit();	
            } else {
            	connMgr.rollback();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }


    /**
    * Q&A답변등록하기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int replyQnA(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         isOk3       = 1;
        int         v_seq       = 0;
        int         v_thisfaqno =0;


        String v_faq_type  = box.getString("p_faq_type");
        int    v_faqno     = box.getInt("p_faqno");
        int    v_lv        = box.getInt("p_lv");
        int    v_position  = box.getInt("p_position");
        int    v_parent    = box.getInt("p_parent");
        int    v_root      = box.getInt("p_root");
        String v_content   = StringManager.replace(box.getString("content"),"<br>","\n");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        String thisYear="";
        String v_templetfile = "";
        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 



           sql  =" update tz_cmufaq set    position = position+1"
                + "  where faq_type        = ?"
//              +"     and parent          = ?"
                +"     and position        > ?"
                ;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(1, v_faq_type                         );//faq분류
           pstmt.setInt   (2, v_position                            );//답변위치
           pstmt.executeUpdate();
if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }

           //qna faqno번호를 추출한다.
           sql = "select isnull(max(faqno), 0) from tz_cmufaq where faq_type = '" +v_faq_type+ "'";
           ls = connMgr.executeQuery(sql);
           while (ls.next()) {
               v_thisfaqno = ls.getInt(1) + 1;
           } 
           int tmp =0;
           
           if(ls != null) { try { ls.close(); }catch (Exception e) {} }
           
           sql = "select isnull(max(position), 0) from tz_cmufaq where faq_type = '" +v_faq_type+ "' and parent= '"+v_faqno+"' and lv="+(v_lv+1);
           System.out.println(sql);
           ls = connMgr.executeQuery(sql);
           while (ls.next()) {
               tmp = ls.getInt(1) + 1;
           } 
int index = 1;           sql  =" insert into tz_cmufaq ( faq_type     , faqno          , title       , content   , read_cnt"
                +"                       , add_cnt      , lv          , position  , register_userid"
                +"                       , register_dte , modifier_userid, modifier_dte, del_fg,parent,root)"
//                +"               values  (?,?,?,empty_clob(),?"
                +"               values  (?,?,?,?,?"
                +"                       ,?,?,?,?"
                +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),?"
                +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N',?,?)"
                ;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(index++, v_faq_type                         );//faq분류
           pstmt.setInt   (index++, v_thisfaqno                            );//일련번호
           pstmt.setString(index++, box.getString("p_title" )          );//제목
			pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
//           pstmt.setString(index++, v_content          );//내용
           pstmt.setInt   (index++, 0                                  );//조회수
           pstmt.setInt   (index++, 0                                 );//추천수
           pstmt.setInt   (index++, v_lv+1         );//답변레벨
//         pstmt.setInt   (index++, tmp         );//답변위치
           pstmt.setInt   (index++, v_position+1         );//답변위치

           pstmt.setString(index++, s_userid);//게시자
           pstmt.setString(index++, s_userid );//수정자
           pstmt.setInt   (index++, v_faqno                            );//부모
           pstmt.setInt   (index++, v_root                            );//부모
           isOk2 = pstmt.executeUpdate();

//           String sql1 = "select content from tz_cmufaq where faq_type = '" + v_faq_type + "' and faqno ="+v_faqno;
//           connMgr.setOracleCLOB(sql1, v_content);



            isOk3 = this.insertUpFile(connMgr,v_faq_type, v_thisfaqno, box);
System.out.println("isOk1 :"+isOk1);
System.out.println("isOk2 :"+isOk2);
System.out.println("isOk3 :"+isOk3);

            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2*isOk3;
    }

    /**
    * Q&A수정하기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateQnA(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         isOk3       = 1;

        String v_faq_type  = box.getString("p_faq_type");
        int    v_faqno     = box.getInt("p_faqno");
        String v_content   = StringManager.replace(box.getString("content"),"<br>","\n");
        String s_userid    = box.getSession("userid");
        
        int	v_upfilecnt       = box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수
        System.out.println("v_upfilecnt :"+v_upfilecnt);
		Vector v_savefile     =	new	Vector();
		Vector v_filesequence =	new	Vector();

        String thisYear="";
        String v_templetfile = "";
        try {
           connMgr = new DBConnectionManager();
           
           for(int	i =	0; i < v_upfilecnt;	i++) {
 			  if(	!box.getString("p_fileseq" + i).equals(""))	{
 			  	v_savefile.addElement(box.getString("p_savefile" + i));			//		서버에 저장되있는 파일명 중에서	삭제할 파일들
 			  	v_filesequence.addElement(box.getString("p_fileseq"	+ i));		 //		서버에	저장되있는 파일번호	중에서 삭제할 파일들
 			  }
 		    }

//System.out.println(v_content);

		   /*********************************************************************************************/
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
		   try {
			   v_content =(String) NamoMime.setNamoContent(v_content);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return 0;
		   }
		   /*********************************************************************************************/       


           sql  =" update tz_cmufaq set    title   = ?"      
                +"                       , content = ?"
                +"                       , modifier_userid = ?"
                +"                       , modifier_dte = to_char(sysdate, 'YYYYMMDDHH24MISS')"
                + "  where faq_type        = ?"
                + "    and faqno           = ?"
                ;
//System.out.println(box.getString("p_title" ) +" : " + v_content +" : " + s_userid +" : " + v_faq_type +" : " + v_faqno);
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(1, box.getString("p_title" )          );//제목
           pstmt.setString(2, v_content          );//내용
           pstmt.setString(3, s_userid);//게시자
           pstmt.setString(4, v_faq_type                         );//faq분류
           pstmt.setInt   (5, v_faqno                            );//일련번호

           isOk1 = pstmt.executeUpdate();

           isOk3 =	this.deleteUpFile(connMgr, box,	v_filesequence);		//	   삭제할 파일이 있다면	파일table에서 삭제
		   isOk2 =	this.insertUpFile(connMgr, v_faq_type, v_faqno,	box);		//		파일첨부했다면 파일table에	insert

		   System.out.println("isOk1 : "+isOk1);
		   System.out.println("isOk2 : "+isOk2);
		   System.out.println("isOk3 : "+isOk3);
		   
            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                connMgr.commit();
                if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 DB	에서 모든처리가	완료되면 해당 첨부파일 삭제
				}
            } else {
            	connMgr.rollback();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2*isOk3;
    }

    /**
    * QNA 새로운 자료파일 등록
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

     public int insertUpFile(DBConnectionManager connMgr, String faq_type, int faqno, RequestBox    box) throws Exception {
        ListSet           ls      = null;
        PreparedStatement pstmt   = null;
        String            sql     = "";
        String            sql2    = "";
        int               isOk2   = 1;
        int               vfileno = 0;
        //----------------------   업로드되는 파일의 형식을 알고 코딩해야한다  --------------------------------
        String [] v_realFile     = new String [FILE_LIMIT];
        String [] v_saveFile     = new String [FILE_LIMIT];

        for(int i = 0; i < FILE_LIMIT; i++) {
            v_realFile[i] = box.getRealFileName(FILE_TYPE + (i+1));
            v_saveFile[i] = box.getNewFileName (FILE_TYPE + (i+1));
        }
        String s_userid = box.getSession("userid");

        try {
        	
        	//----------------------	자료 번호 가져온다 ----------------------------
			sql	= "select isnull(max(fileno), 0) from tz_cmufaqfile where faq_type = '" +faq_type+ "' and faqno ="+faqno;

			ls = connMgr.executeQuery(sql);
			ls.next();
			vfileno =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------

             sql  =" insert into tz_cmufaqfile ( faq_type, faqno, fileno, realfile, savepath"
                  +"                       , savefile, filesize, register_userid, register_dte, modifier_userid, modifier_dte)"
                  +"               values  (?,?,?,?,''"
                  +"                       ,?,null,?"
                  +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),?"
                  +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'))"
                  ;
             pstmt = connMgr.prepareStatement(sql);
             for(int    i = 0; i < FILE_LIMIT; i++) {
                 if (!v_realFile    [i].equals("")&& "".equals(box.getString("p_fileno"+(i+1))) )   {
                     pstmt.setString(1, faq_type       );//faq분류
                     pstmt.setInt   (2, faqno          );//일련번호
                     pstmt.setInt   (3, vfileno        );//파일일련번호
                     pstmt.setString(4, v_realFile[i]  );//원본파일명
                     pstmt.setString(5, v_saveFile[i]  );//실제파일명
                     pstmt.setString(6, s_userid       );//게시자
                     pstmt.setString(7, s_userid       );//수정자
                     isOk2 = pstmt.executeUpdate();
                     vfileno++;
                 }
              } 
        }
        catch (Exception ex) {
            FileManager.deleteFile(v_saveFile, FILE_LIMIT);     //  일반파일, 첨부파일 있으면 삭제..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
        }
        return isOk2;
    }

    /**
     * 선택된 자료파일 DB에서 삭제
     * @param connMgr           DB Connection Manager
     * @param box               receive from the form object and session
     * @param p_filesequence    선택 파일 갯수
     * @return
     * @throws Exception
     */
    public int deleteUpFile( RequestBox box)    throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet           ls      = null;
        String            sql     = "";
        String            sql2    = "";
        int               isOk2   = 1;


        String s_userid = box.getSession("userid");
        String v_faq_type  = box.getString("p_faq_type");
        int    v_faqno     = box.getInt("p_faqno");
        int    v_fileno    = box.getInt("p_delfileno");
        try {
           connMgr = new DBConnectionManager();

             sql  =" delete from  tz_cmufaqfile "
                 + "  where faq_type        = ?"
                 + "    and faqno           = ?"
                 + "    and fileno          = ?"
              ;
             pstmt = connMgr.prepareStatement(sql);
             pstmt.setString(1, v_faq_type       );//faq분류
             pstmt.setInt   (2, v_faqno          );//일련번호
             pstmt.setInt   (3, v_fileno        );//파일일련번호
             isOk2 = pstmt.executeUpdate();
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql  + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk2;
    }
    
	/**
	 * 선택된 자료파일 DB에서 삭제
	 * @param connMgr			DB Connection Manager
	 * @param box				receive from the form object and session
	 * @param p_filesequence    선택 파일 갯수
	 * @return
	 * @throws Exception
	 */
	public int deleteUpFile(DBConnectionManager	connMgr, RequestBox box, Vector p_filesequence)	throws Exception {
		PreparedStatement pstmt3 = null;
		String sql	= "";
		String sql3	= "";
        ListSet ls = null;
		int	isOk3 =	1;
		
		String v_faq_type  = box.getString("p_faq_type");
        int    v_faqno     = box.getInt("p_faqno");

		try	{

			sql3 = " delete from  tz_cmufaqfile "
                + "  where faq_type        = ?"
                + "    and faqno           = ?"
                + "    and fileno          = ?"
             ;
			pstmt3 = connMgr.prepareStatement(sql3);
System.out.println("size : "+p_filesequence.size());
			for(int	i =	0; i < p_filesequence.size(); i++) {
				System.out.println("delete : "+i);
				int	v_fileseq =	Integer.parseInt((String)p_filesequence.elementAt(i));
				pstmt3.setString(1, v_faq_type);
				pstmt3.setInt(2, v_faqno);
				pstmt3.setInt(3, v_fileseq);
				
				System.out.println("v_faq_type : "+v_faq_type);
				System.out.println("v_faqno : "+v_faqno);
				System.out.println("v_fileseq : "+v_fileseq);
				isOk3 =	pstmt3.executeUpdate();
			}
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			  // ErrorManager.getErrorStackTrace(ex, box,	sql3);
			//throw new Exception("sql = " + sql3	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {}	}
		}
		return isOk3;
	}


    /**
    * Q&A 댓글등록하기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertQnAMemo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         v_seq       = 0;
        int         v_rplno     = 0; 


        String v_faq_type  = box.getString("p_faq_type");
        int    v_faqno     = box.getInt("p_faqno"); 
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        String thisYear="";
        String v_templetfile = "";
        try {
           connMgr = new DBConnectionManager();

           //qna faqno번호를 추출한다.
           sql = "select isnull(max(rplno), 0) from tz_cmufaqreplay where faq_type = '" +v_faq_type+ "' and faqno = "+v_faqno;
           ls = connMgr.executeQuery(sql);
           while (ls.next()) {
               v_rplno = ls.getInt(1) + 1;
           } 
           sql  =" insert into tz_cmufaqreplay ( faq_type     , faqno          , rplno       , content   , userid"
                +"                       , register_dte , modifier_dte, del_fg)"
                +"               values  (?,?,?,?,?"
                +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
                ;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(1, v_faq_type                         );//faq분류
           pstmt.setInt   (2, v_faqno                            );//일련번호
           pstmt.setInt   (3, v_rplno                            );//댓글번호
           pstmt.setString(4, box.getString("p_rep_content" )          );//제목
           pstmt.setString(5, s_userid);//게시자
           isOk1 = pstmt.executeUpdate();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }

    /**
    * Q&A 댓글삭제하기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int deleteQnAMemo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 1;
        int         isOk2       = 1;
        int         v_seq       = 0;


        String v_faq_type  = box.getString("p_faq_type");
        int    v_faqno     = box.getInt("p_faqno"); 
        int    v_rplno     = box.getInt("p_rplno"); 
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        String thisYear="";
        String v_templetfile = "";
        try {
           connMgr = new DBConnectionManager();

           sql = "delete from tz_cmufaqreplay where faq_type = '" +v_faq_type+ "' and faqno = "+v_faqno+" and rplno="+v_rplno;
           pstmt = connMgr.prepareStatement(sql);
           isOk1 = pstmt.executeUpdate();
           System.out.println("isOk1:"+isOk1);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }


    /**
    * Q&A 글삭제하기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int deleteQnAData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt   = null;
        ListSet     ls          = null;
        String      sql         = "";

        int         isOk1       = 0;
        int         isOk2       = 1;
        int         isOk3       = 1;

        int         v_seq       = 0;


        String v_faq_type  = box.getString("p_faq_type");
        int    v_faqno     = box.getInt("p_faqno"); 
        int    v_replcnt    = box.getInt("p_replcnt");
        int	   v_upfilecnt	= box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수		
		Vector v_savefile  = box.getVector("p_savefile");

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        String thisYear="";
        String v_templetfile = "";
        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 
           
           //본문삭제
           sql = "delete from tz_cmufaq where faq_type = ? and faqno = ?";
           pstmt = connMgr.prepareStatement(sql);
           pstmt.setString(1, v_faq_type);
           pstmt.setInt   (2, v_faqno);
           isOk1 = pstmt.executeUpdate();

           //댓글삭제
           if(v_replcnt > 0 ) {
        	   sql = "delete from tz_cmufaqreplay where faq_type = ? and faqno = ?";
               System.out.println(sql);
               pstmt = connMgr.prepareStatement(sql);
               pstmt.setString(1, v_faq_type);
               pstmt.setInt   (2, v_faqno);
               isOk2 = pstmt.executeUpdate();
           }
           

           //파일삭제삭제
           if(v_upfilecnt > 0 ) {
        	   sql = "delete from tz_cmufaqfile where faq_type = ? and faqno = ?";
               pstmt = connMgr.prepareStatement(sql);
               pstmt.setString(1, v_faq_type);
               pstmt.setInt   (2, v_faqno);
               isOk3 = pstmt.executeUpdate();   
           }
           System.out.println("isOk1:"+isOk1);   
           System.out.println("isOk2:"+isOk2);
           System.out.println("isOk3:"+isOk3);

            if(isOk1 > 0&& isOk2 > 0&& isOk3 > 0) {
            	connMgr.commit();
            	if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile);         //     첨부파일 삭제
                }
            } else {
            	connMgr.rollback();
            	isOk1 = 0;
            }
          
        }
        catch (Exception ex) {
        	connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }

    /**
    * 게시판 번호달기
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

     public static String printPageList(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str="";
        if(totalPage > 0) {
            PageList pagelist = new PageList(totalPage,currPage,blockSize);


            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            //str += "    <td width='100%' align='center' valign='middle'>";

            if (pagelist.previous()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></a></td>  ";
            }else{
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></td>";
            }


            for (int i=pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<td align='center' valign='middle'><strong>" + i + "</strong>" + "</td>";
                } else {
                    str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + i + "')\">" + i + "</a></td> ";
                }
            }

            if (pagelist.next()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\"><img src=\"/images/user/button/next.gif\"  border=\"0\" align=\"middle\"></a></td>";
            }else{
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/next.gif\" border=\"0\" align=\"middle\"></td>";
            }

           /* if (str.equals("")) {
                str += "<자료가 없습니다.";
            }
            */
           // str += "    </td>";
           // str += "    <td width='15%' align='center'>";
           // str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }
}
