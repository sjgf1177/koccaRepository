//**********************************************************
//1. 제      목:
//2. 프로그램명: CommunityAdminPoliceBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정: 이나연 05.11.23 _ 불건전 커뮤니티 처리 쿼리수정
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.common.GetCodenm;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.namo.SmeNamoMime;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityAdminPoliceBean {
    private ConfigSet config;
    private int row;

    public CommunityAdminPoliceBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
            row = 10; //강제로 지정
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
    * 불량 커뮤니티 답변
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updatePolice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";

        int         isOk        = 0;

        String v_policeno  = box.getString("p_policeno");
        String v_repmemo   = StringManager.replace(box.getString("content"),"<br>","\n");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 

		   /*********************************************************************************************/
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
		   try {
			   v_repmemo =(String) NamoMime.setNamoContent(v_repmemo);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return 0;
		   }
		   /*********************************************************************************************/ 
int index = 1;
			sql  =" update tz_cmupolice set  str_fg=2"
//                +"                         ,repmemo=empty_clob()"
                +"                         ,repmemo=?"
                +"                         ,str_userid=?"
                +"                         ,str_dte=to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"  where policeno =?";
           pstmt = connMgr.prepareStatement(sql);
				pstmt.setCharacterStream(index++, new StringReader(v_repmemo), v_repmemo.length());
//		   pstmt.setString(index++, v_repmemo);
           pstmt.setString   (index++, s_userid                       );
           pstmt.setString   (index++, v_policeno                       );
           isOk = pstmt.executeUpdate();

//			sql1 = "select repmemo from tz_cmupolice where policeno = '" + v_policeno + "'";
//           connMgr.setOracleCLOB(sql1, v_repmemo);       //      (기타 서버 경우)


               //일련번호 구하기
               int v_mailno=0;
               sql1 = "select NVL(max(MAILNO), 0)   from TZ_CMUMAIL ";
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_mailno = ls.getInt(1);

index = 1;
               sql  =" insert into TZ_CMUMAIL ( mailno, userid, kor_nm, recv_email"
                    +"                       ,cmuno, cmu_nm, SEND_USERID,send_email, title, content"
                    +"                       ,loc_fg,loc_nm,regster_dte, send_fg)"
                    +"               values  (?,?,?,?"
//                    +"                       ,?,?,?,?,?,empty_clob()"
                    +"                       ,?,?,?,?,?,?"
                    +"                       ,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
                    ;
               pstmt = connMgr.prepareStatement(sql);


               //발신자 이메일
               String v_tmp_send_email="";
               sql1 = "select email   from tz_member where userid = '"+s_userid+"' ";
               System.out.println(sql1);
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_tmp_send_email = ls.getString(1);

if(ls != null) { try { ls.close(); } catch (Exception e) {} }
               sql1 = "select a.policeno, a.cmuno  , a.cmu_nm   , a.userid,b.name,b.email "
                     +"  from tz_cmupolice a,tz_member b "
                     +" where a.userid   = b.userid "
                     +"   and a.policeno ='"+v_policeno+"'";
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) {
                     v_mailno =v_mailno+1;
                     pstmt.setInt   (index++, v_mailno                                );//일련번호
                     pstmt.setString(index++, ls.getString(4)                         );//수신자아이디
                     pstmt.setString(index++, ls.getString(5)                         );//수신자명
                     pstmt.setString(index++, ls.getString(6)                         );//수신자이메일
                     pstmt.setString(index++, ls.getString(2)                         );//커뮤니티먼호
                     pstmt.setString(index++, ls.getString(3)                            );//커뮤니티명
                     pstmt.setString(index++ ,s_userid                                );//발신자아이디
                     pstmt.setString(index++ ,v_tmp_send_email                        );//발신자이메일
                     pstmt.setString(index++ , ""                                     );//제목 
					pstmt.setCharacterStream(index++, new StringReader(v_repmemo), v_repmemo.length());
//					 pstmt.setString(index++, v_repmemo                                    );//구분
                     pstmt.setString(index++, "6"                                    );//구분
                     pstmt.setString(index++, "불건전커뮤니티신고"                     );//구분명
                     isOk = pstmt.executeUpdate();
//                     sql1 = "select content  from TZ_CMUMAIL where mailno = '"+v_mailno+"'";
//                     connMgr.setOracleCLOB(sql1, v_repmemo);
                    // if(isOk > 0 ) {
                     //    if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                    // }
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
    * 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   리스트
    * @throws Exception
    */
    public ArrayList selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql     = "";
        String count_sql     = "";
        String head_sql = "";
		String body_sql = "";		
        String group_sql = "";
        String order_sql = "";
        DataBox             dbox    = null;
		
		String s_userid  = box.getSession      ("userid");
        String v_str_fg  = box.getStringDefault("p_str_fg","1");

	    String  s_grcode	 = box.getSession("tem_grcode");
		String  v_grtype	 = GetCodenm.get_grtype(box,s_grcode);
		
        int v_pageno     = box.getInt		   ("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            head_sql  ="\n  select    a.policeno  policeno , a.cmuno   cmuno , a.cmu_nm    cmu_nm  "
                 +"\n           ,a.userid    userid   , a.email   email , a.content   content "
                 +"\n           ,a.singo_dte singo_dte, a.str_fg  str_fg, a.repmemo   repmemo "
//                 +"\n           ,a.str_dte   str_dte  , a.str_userid    , d.cmu_nm    cmu_nm  "
				 +"\n           ,a.str_dte   str_dte  , a.str_userid   "
                 +"\n           ,e.kor_name  room_master,d.member_cnt member_cnt,d.accept_dte accept_dte,d.intro intro"
                 +"\n           ,b.deptnam         bdeptnam                ,b.jikupnm              bjikupnm "     
                 +"\n           ,b.jikwinm         bjikwinm                ,b.name                 bname"          
                 +"\n           ,NVL(c.deptnam,'') cdeptnam                ,NVL(c.jikupnm,'')      cjikupnm "     
                 +"\n           ,NVL(c.jikwinm,'') cjikwinm                ,NVL(c.name,'')         cname " ;          
            body_sql += "\n    from tz_cmupolice a,tz_member b,tz_member c,tz_cmubasemst d"
                 +"\n       ,(select cmuno,userid,kor_name,grade from tz_cmuusermst where grade='01' and close_fg='1') e"
                 +"\n   where a.cmuno            = d.cmuno  "
                 +"\n     and a.userid           = b.userid "  
                 +"\n     and d.cmuno            = e.cmuno  "
                 // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//                 +"\n     and a.str_userid           = c.userid(+) "  
				 +"\n     and a.str_userid       =  c.userid(+) "  
                 +"\n     and a.str_fg           = '"+v_str_fg+"' ";
            	// +"\n     and d.grtype           = '"+v_grtype+"' " ;
            order_sql += "\n  order by a.policeno desc ";
			
			sql= head_sql+ body_sql+ group_sql+ order_sql;
            ls = connMgr.executeQuery(sql);
            
			count_sql= "select count(*) " + body_sql;
			int totalrowcount= BoardPaging.getTotalRow(connMgr, count_sql);
			
            ls.setPageSize(row);                         // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount);  // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();    // 전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();    // 전체 row 수를 반환한다

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
    * 조회
    * @param box          receive from the form object and session
    * @return ArrayList   조회
    * @throws Exception
    */
    public ArrayList selectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        String              sql1    = "";
        DataBox             dbox    = null;
		String s_userid    = box.getSession("userid");
        String v_policeno  = box.getString("p_policeno");
        int v_pageno       = box.getInt   ("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            sql  ="\n  select    a.policeno  policeno , a.cmuno   cmuno , a.cmu_nm    cmu_nm  "
                 +"\n           ,a.userid    userid   , a.email   email , a.content   content "
                 +"\n           ,a.singo_dte singo_dte, a.str_fg  str_fg, a.repmemo   repmemo "
//                 +"\n           ,a.str_dte   str_dte  , a.str_userid    , d.cmu_nm    cmu_nm  "
				 +"\n           ,a.str_dte   str_dte  , a.str_userid    "
                 +"\n           ,e.kor_name  room_master,d.member_cnt member_cnt,d.accept_dte accept_dte,d.intro intro"
                 +"\n           ,b.deptnam         bdeptnam                ,b.jikupnm              bjikupnm "     
                 +"\n           ,b.jikwinm         bjikwinm                ,b.name                 bname"          
                 +"\n           ,NVL(c.deptnam,'') cdeptnam                ,NVL(c.jikupnm,'')      cjikupnm "     
                 +"\n           ,NVL(c.jikwinm,'') cjikwinm                ,NVL(c.name,'')         cname"          
                 +"\n    from tz_cmupolice a,tz_member b,tz_member c,tz_cmubasemst d"
                 +"\n       ,(select cmuno,userid,kor_name,grade from tz_cmuusermst where grade='01' and close_fg='1') e"
                 +"\n   where a.cmuno            = d.cmuno  "
                 +"\n     and a.userid           = b.userid "  
                 +"\n     and d.cmuno            = e.cmuno  "
                 // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//                 +"\n     and a.str_userid           = c.userid(+) "  
				 +"\n     and a.str_userid       =  c.userid(+) "  
                 +"\n     and a.policeno         = '"+v_policeno+"'";

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
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

}
