//**********************************************************
//1. 제      목:
//2. 프로그램명: CommunityAdminRoomBean.java
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
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityAdminRoomBean {
    private ConfigSet config;
    private static int row=10;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 1;                    //    페이지에 세팅된 파일첨부 갯수


    public void CommunityAdminRoomBean() {
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

    /**
    * 커뮤니티 조회리스트
    * @param box          receive from the form object and session
    * @return ArrayList   커뮤니티 조회리스트
    * @throws Exception
    */
    public ArrayList selectList(RequestBox box) throws Exception {
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
//        String              sql     = "";
//        String              sql1    = "";
//        String              sql2    = "";
        DataBox             dbox    = null;

        String v_searchtext    = box.getString("p_searchtext");
        String v_select        = box.getString("p_select");
        String v_orderby       = box.getStringDefault("p_orderby", "cmuno");

        String v_if_close_fg   = box.getStringDefault("p_if_close_fg","1");
        int    v_pageno        = box.getInt("p_pageno");
        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");
		String v_grtype			= box.getString("p_grtype");
		
		System.out.println(" v_orderby : "+ v_orderby);
		
        try {
            connMgr = new DBConnectionManager();
			// 수정일 : 05.11.10 수정자 : 이나연 _ rownum 수정

            head_sql  = " select a.cmuno           cmuno           , a.cmu_nm        cmu_nm      , a.in_method_fg     in_method_fg   , a.search_fg search_fg " ;
			head_sql += "              , a.data_passwd_fg  data_passwd_fg  , a.display_fg    display_fg  , a.type_l           type_l         ,a.type_m type_m " ;
			head_sql += "             , a.intro           intro           , a.img_path      img_path    , a.layout_fg        layout_fg      , a.html_skin_fg html_skin_fg " ;
			head_sql += "              , a.read_cnt        read_cnt        , a.member_cnt    member_cnt  , a.close_fg         close_fg " ;
			head_sql += "             , a.close_reason    close_reason    , a.close_dte     close_dte   , a.close_userid     close_userid " ; 
			head_sql += "              , a.hold_fg         hold_fg         , a.accept_dte    accept_dte  , a.accept_userid    accept_userid  , a.register_dte  register_dte " ;
			head_sql += "             , a.register_userid register_userid , a.modifier_dte  modifier_dte, a.modifier_userid  modifier_userid " ;
			head_sql += "              , b.kor_name        kor_name        , c.codenm      grade_nm,d.codenm type_l_nm,e.codenm type_m_nm " ;
			body_sql += "    from tz_cmubasemst a " ;
			body_sql += "        ,(select cmuno,userid,kor_name,grade from tz_cmuusermst where grade='01' and close_fg='1') b " ;
			body_sql += "        ,(select cmuno cmuno,grcode grcode,kor_nm codenm from tz_cmugrdcode) c " ;
			body_sql += "        ,(select code code,codenm  codenm from tz_code where gubun='0052' and levels=1)d " ;
			body_sql += "        ,(select code code,codenm  codenm from tz_code where gubun='0052' and levels=2)e " ;
			body_sql += "  where a.cmuno  = b.cmuno " ;
			body_sql += "    and b.cmuno  = c.cmuno " ;
			body_sql += "    and a.type_l = d.code " ;
			body_sql += "    and a.type_m  =  e.code(+) " ;
			body_sql += "    and b.grade  = c.grcode " ;

			if(!"5".equals(v_if_close_fg)){
				body_sql  += "    and a.close_fg  like '"+v_if_close_fg+"' " ;
				
			}
			
			// 05.12.13 이나연 추가 _ 헤더정렬 
			if (v_orderby.equals("cmuno"))     			order_sql += " order by a.cmuno asc";
			else{
	            if (v_orderby.equals("cmu_nm"))     		order_sql += " order by a.cmu_nm asc";
	            if (v_orderby.equals("kor_name"))   		order_sql += " order by b.kor_name asc";
	            if (v_orderby.equals("accept_dte")) 		order_sql += " order by a.accept_dte asc";
				if (v_orderby.equals("close_fg")) 			order_sql += " order by a.close_fg , a.cmu_nm asc";
				if (v_orderby.equals("register_userid")) 	order_sql += " order by a.register_userid , a.cmu_nm asc";
				if (v_orderby.equals("register_dte")) 		order_sql += " order by a.register_dte , a.cmu_nm asc";
				if (v_orderby.equals("member_cnt")) 		order_sql += " order by a.member_cnt desc , a.cmu_nm asc";
				if (v_orderby.equals("hold_fg")) 			order_sql += " order by a.hold_fg desc , a.cmu_nm asc";
				if (v_orderby.equals("close_dte")) 			order_sql += " order by a.close_dte , a.cmu_nm asc";
				if (v_orderby.equals("type_l")) 			order_sql += " order by a.type_l , a.type_m asc";
			}

			
			sql= head_sql+ body_sql +group_sql+ order_sql ; 
            ls = connMgr.executeQuery(sql);
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);                         // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);  // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();    // 전체 페이지 수를 반환한다

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
    * 게시판 조회
    * @param box          receive from the form object and session
    * @return ArrayList   게시판 조회
    * @throws Exception
    */
    public ArrayList selectViewBrd(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
		
		String              sql     = "";
        DataBox             dbox    = null;
        int                 isOK    = 1;

        String v_cmuno	= box.getString("p_cmuno");
		try
		{
			connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
			
			sql = "Select ";
			sql += " 	cb.cmuno, cmu_nm, accept_dte, register_dte, type_l, type_m, display_fg, userid, kor_name, d.codenm as type_l_nm,";
			sql += " 	e.codenm as type_m_nm, cb.intro intro";
			sql += " 	,cb.member_cnt countMember ";
			sql += " 	,(Select count(cmuno) From TZ_CMUMENU Where cmuno = '" + v_cmuno +"') as countBrd ";
			sql += " From ";
			sql += " 	tz_cmubasemst cb, tz_cmuusermst cu , ";
			sql += " 	(select code code,codenm codenm from tz_code where gubun='0052' and levels=1)d , ";
			sql += " 	(select code code,codenm codenm from tz_code where gubun='0052' and levels=2)e ";
			sql += " Where cb.cmuno  =  cu.cmuno(+) and cb.type_l  =  d.code(+) and cb.type_m  =  e.code(+) and cb.cmuno = '" + v_cmuno +"' ";
			sql += " 	and cu.grade = '01'";
			System.out.println("selectViewBrd.sql = " + sql);
			ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
		
		}
		catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	     }
	     finally {
	         if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	         if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
	         if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	     }
		return list;
    }


    /**
    * 커뮤니티 거부 및 폐쇄 /승인처리
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateCommunity(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        int         isOk        = 0;
        int         v_seq       = 0;

        String v_token_cmuno  = box.getString("p_token_cmuno");

        Vector v_cbx_cmuno = new Vector();
        String v_close_fg  = box.getString("p_close_fg");         
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        //v_close_fg 0.신청 1.승인 2.폐쇄 3.거부
        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 

           StringTokenizer stok    = new StringTokenizer(v_token_cmuno, "/");
           String[]        sTokens = new String[stok.countTokens()];
		   
           for (int i = 0; i < stok.countTokens()/*.hasMoreElements()*/;i++){
               sTokens[i] = ((String)stok.nextElement()).trim();
               v_cbx_cmuno.addElement(sTokens[i]);
           }

           for(int i=0;i<v_cbx_cmuno.size();i++){
               String v_tmp_cmuno = (String)v_cbx_cmuno.elementAt(i) ;
               if(!"1".equals(v_close_fg)){
                  sql  =" update tz_cmubasemst set  close_fg           =?   "
                       +"                         , close_dte          = to_char(sysdate, 'YYYYMMDDHH24MISS') "
                       +"                         , close_userid       =?   "
                       +"                where cmuno = ?"
                       ;
                  pstmt = connMgr.prepareStatement(sql);
                  pstmt.setString(1, v_close_fg      );//구분
                  pstmt.setString(2, s_userid        );//처리자
                  pstmt.setString(3, v_tmp_cmuno     );//커뮤니티번호
               } else {
                  sql  =" update tz_cmubasemst set  close_fg           =?   "
                       +"                         , accept_dte         = to_char(sysdate, 'YYYYMMDDHH24MISS') "
                       +"                         , accept_userid      =?   "
                       +"                where cmuno = ?"
                       ;
                  pstmt = connMgr.prepareStatement(sql);
                  pstmt.setString(1, v_close_fg      );//구분
                  pstmt.setString(2, s_userid        );//처리자
                  pstmt.setString(3, v_tmp_cmuno     );//커뮤니티번호

               }
               isOk = pstmt.executeUpdate();
           } 

            if(isOk > 0 ) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        } finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        
        return isOk;
    }




    /**
    * 인기커뮤니티 관리
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateHold(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        int         isOk        = 0;
        int         v_seq       = 0;

        Vector v_cmuno    	  = box.getVector("p_all_cmuno");
        Vector v_hold_fg  	  =	box.getVector("p_hold_fg");         
        String s_userid       = box.getSession("userid");
        String s_name         = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 
           for(int i=0;i<v_cmuno.size();i++){
               String v_tmp_cmuno = (String)v_cmuno.elementAt(i) ;
               String v_tmp_hold  = (String)v_hold_fg.elementAt(i) ;

               sql  =" update tz_cmubasemst set  hold_fg           =?   "
                    +"                where cmuno = ?"
                    ;
               pstmt = connMgr.prepareStatement(sql);
               pstmt.setString(1, v_tmp_hold      );//인기구분
               pstmt.setString(2, v_tmp_cmuno     );//커뮤니티번호
               isOk = pstmt.executeUpdate();
           } 

            if(isOk > 0 ) {
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
           if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }



    /**
    * 일반메일전송
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int sendMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        int         isOk        = 1;
        int         v_seq       = 0;

        String v_token_cmuno  = box.getString("p_token_cmuno");
        String v_cmuno        = box.getString("p_close_fg");
        Vector v_cbx_cmuno    = new Vector();
        String v_title        = box.getString("p_title");
        String v_intro     = StringManager.replace(box.getString("content"),"<br>","\n");

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 
           StringTokenizer stok    = new StringTokenizer(v_token_cmuno, "/");
           String[]        sTokens = new String[stok.countTokens()];
           for (int i = 0; stok.hasMoreElements();i++){
               sTokens[i] = ((String)stok.nextElement()).trim();
               v_cbx_cmuno.addElement(sTokens[i]);
           }

           //발신자 이메일
           String v_tmp_send_email="";
           sql1 = "select email   from tz_member where userid = '"+s_userid+"' ";
           System.out.println(sql1);
           ls = connMgr.executeQuery(sql1);
           while (ls.next()) v_tmp_send_email = ls.getString(1);
if(ls != null) { try { ls.close(); } catch (Exception e) {} }
           for(int i=0;i<v_cbx_cmuno.size();i++){
               String v_tmp_cmuno = (String)v_cbx_cmuno.elementAt(i) ;

               //커뮤니티명구하기
               String v_tmp_cmu_nm="";
               sql1 = "select cmu_nm   from tz_cmubasemst where cmuno = '"+v_tmp_cmuno+"' ";
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_tmp_cmu_nm = ls.getString(1);

if(ls != null) { try { ls.close(); } catch (Exception e) {} }

               //일련번호 구하기
               int v_mailno=0;
               sql1 = "select isnull(max(MAILNO), 0)   from TZ_CMUMAIL ";
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_mailno = ls.getInt(1);

if(ls != null) { try { ls.close(); } catch (Exception e) {} }
               sql  =" insert into TZ_CMUMAIL ( mailno, userid, kor_nm, recv_email"
                    +"                       ,cmuno, cmu_nm, SEND_USERID,send_email, title, content"
                    +"                       ,loc_fg,loc_nm,regster_dte, send_fg)"
                    +"               values  (?,?,?,?"
//                    +"                       ,?,?,?,?,?,empty_clob()"
                    +"                       ,?,?,?,?,?,?"
                    +"                       ,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
                    ;
               pstmt = connMgr.prepareStatement(sql);

int index = 1;

               sql1 = "select a.cmuno,b.cmu_nm,a.userid,a.kor_name,a.email   from tz_cmuusermst a,tz_cmubasemst b where a.cmuno = b.cmuno and a.cmuno = '"+v_tmp_cmuno+"' ";
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) {
                     v_mailno =v_mailno+1;
                     pstmt.setInt   (index++, v_mailno                                );//일련번호
                     pstmt.setString(index++, ls.getString( 3)                         );//수신자아이디
                     pstmt.setString(index++, ls.getString( 4)                         );//수신자명
                     pstmt.setString(index++, ls.getString( 5)                         );//수신자이메일
                     pstmt.setString(index++, v_tmp_cmuno                             );//커뮤니티먼호
                     pstmt.setString(index++, v_tmp_cmu_nm                            );//커뮤니티명
                     pstmt.setString(index++ ,s_userid                                );//발신자아이디
                     pstmt.setString(index++ ,v_tmp_send_email                        );//발신자이메일
                     pstmt.setString(index++ , v_title                                );//제목
pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
//					 pstmt.setString(index++ , v_intro                                );
                     pstmt.setString(index++, "5"                                    );//구분
                     pstmt.setString(index++, "커뮤니티승인관련"                     );//구분명
                     isOk = pstmt.executeUpdate();
//                     sql1 = "select content  from TZ_CMUMAIL where mailno = '"+v_mailno+"'";
//                     connMgr.setOracleCLOB(sql1, v_intro);
               }
           }//end for
                     if(isOk > 0 ) {
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
        return isOk;
    }
	
	 /**
	    *  (관리자) 커뮤니티 폐쇄
	    * @param box      receive from the form object and session
	    * @return isOk    1:insert success,0:insert fail
	    * @throws Exception
	    */

	public int updateFrClose(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet             ls1      = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        int         isOk        = 1;
		
		Vector v_cmuno    = box.getVector("p_cmuno");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false); 
		   for(int i=0;i<v_cmuno.size();i++){
               String v_tmp_cmuno = (String)v_cmuno.elementAt(i) ;

           sql  =" update tz_cmubasemst set  close_fg           =?   "
 			    +"                         , close_reason       = ?  "
                +"                         , close_dte          =to_char(sysdate, 'YYYYMMDDHH24MISS')   "
                +"                         , close_userid       =?   "
                +"                where cmuno = ?"
                ;
           pstmt = connMgr.prepareStatement(sql);
		  
		   pstmt.setString(1, "3"       );	// 폐쇄구분 = '3' 폐쇄
		   pstmt.setString(2, v_tmp_cmuno  ); 	
           pstmt.setString(3, s_userid  );	// 수정자
           pstmt.setString(4, v_tmp_cmuno   );
           isOk = pstmt.executeUpdate();
		   } 

           if(isOk > 0 ) {
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
          if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
         if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
     }
     return isOk;
	}

}
