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

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;
import com.dunet.common.util.StringUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityFrJoinBean {
    private ConfigSet config;
    private int row;

    public CommunityFrJoinBean() {
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
    * 회원가입하기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertFrJoin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        String      createCmuno ="";
        int         isOk        = 1;
        int         v_seq       = 0;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_intro     = StringManager.replace(StringUtil.removeTag(box.getString("p_content")),"<br>","\n");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");
        String v_grade     = "";
        String v_in_method_fg="1";
        try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false); 

             CommunityIndexBean bean = new CommunityIndexBean();
              
             //최하위등급구하기
             sql1  = " select max(grcode) grcode from tz_cmugrdcode where cmuno ='"+v_cmuno+"'";
             ls = connMgr.executeQuery(sql1);
             while (ls.next()) {
                   v_grade= ls.getString("grcode");
             }
if(ls != null) { try { ls.close(); } catch (Exception e) {} }
             //회원가입방식
             sql1  = " select  in_method_fg from tz_cmubasemst where cmuno ='"+v_cmuno+"'";
             ls = connMgr.executeQuery(sql1);
             while (ls.next()) {
                   v_in_method_fg= ls.getString("in_method_fg");
             }
if(ls != null) { try { ls.close(); } catch (Exception e) {} }

             if("1".equals(v_in_method_fg)){//신청즉시가입
                sql  =" insert into tz_cmuusermst (cmuno       , userid      , kor_name    , eng_name      , email"
                     +"                          , tel         , mobile      , office_tel  , duty          , wk_area"
                     +"                          , grade       , request_dte , license_dte , license_userid, close_fg"
                     +"                          , close_reason, close_dte   , intro       , recent_dte    , visit_num"
                     +"                          , search_num  , register_num, modifier_dte)"
                     +"             values (       ?           , ?           , ?           ,''             , ?"
                     +"                          , ?           , ?           , ?           ,''             , ?"
                     +"                          , ?           ,to_char(sysdate, 'YYYYMMDDHH24MISS'),to_char(sysdate, 'YYYYMMDDHH24MISS'),?,'1'"
                     +"                          ,? ,''           ,? ,''             ,0"
                     +"                          ,0            ,0            ,to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                pstmt = connMgr.prepareStatement(sql);

                sql1  = " select userid,name,email,hometel,handphone,comptel,work_plcnm from tz_member where userid ='"+s_userid+"'";
                ls = connMgr.executeQuery(sql1);
                while (ls.next()) {
int index = 1;
                     pstmt.setString(index++, v_cmuno            );
                     pstmt.setString(index++, ls.getString("userid")   );
                     pstmt.setString(index++, ls.getString("name") );
                     pstmt.setString(index++, ls.getString("email") );
                     pstmt.setString(index++, ls.getString("hometel") );
                     pstmt.setString(index++, ls.getString("handphone") );
                     pstmt.setString(index++, ls.getString("comptel") );
                     pstmt.setString(index++, ls.getString("work_plcnm") );
                     pstmt.setString(index++, v_grade);
                     pstmt.setString(index++, s_userid );
                     pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
pstmt.setCharacterStream(index++, new StringReader(""), 0);
                     isOk=pstmt.executeUpdate();
                }   

//                sql1 = "select intro from tz_cmuusermst where cmuno = '" + v_cmuno  + "' and userid ='"+s_userid+"'";
//                connMgr.setOracleCLOB(sql1, v_intro);
                
//                sql1 = "select close_reason from tz_cmuusermst where cmuno = '" + v_cmuno + "' and userid ='"+s_userid+"'";
//                connMgr.setOracleCLOB(sql1, "");
                    
                sql1  = " update tz_cmubasemst set MEMBER_CNT = MEMBER_CNT+1 where cmuno ='"+v_cmuno+"'";
                pstmt = connMgr.prepareStatement(sql1);
                pstmt.executeUpdate();

             } else {//승인후 가입

                sql  =" insert into tz_cmuusermst (cmuno       , userid      , kor_name    , eng_name      , email"
                     +"                          , tel         , mobile      , office_tel  , duty          , wk_area"
                     +"                          , grade       , request_dte , license_dte , license_userid, close_fg"
                     +"                          , close_reason, close_dte   , intro       , recent_dte    , visit_num"
                     +"                          , search_num  , register_num, modifier_dte)"
                     +"             values (       ?           , ?           , ?           ,''             , ?"
                     +"                          , ?           , ?           , ?           ,''             , ?"
                     +"                          , ?           ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'','','0'"
                     +"                          ,? ,''           ,? ,''             ,0"
                     +"                          ,0            ,0            ,to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                pstmt = connMgr.prepareStatement(sql);

                sql1  = " select userid,name,email,hometel,handphone,comptel,work_plcnm from tz_member where userid ='"+s_userid+"'";
                ls = connMgr.executeQuery(sql1);
                EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY,Constants.APP_IV);
                
                while (ls.next()) {
int index = 1;
                     pstmt.setString(index++, v_cmuno            );
                     pstmt.setString(index++, ls.getString("userid")   );
                     pstmt.setString(index++, ls.getString("name") );
                     pstmt.setString(index++, encryptUtil.decrypt(ls.getString("email")) );
                     pstmt.setString(index++, encryptUtil.decrypt(ls.getString("hometel")) );
                     pstmt.setString(index++, encryptUtil.decrypt(ls.getString("handphone")) );
                     pstmt.setString(index++, ls.getString("comptel") );
                     pstmt.setString(index++, ls.getString("work_plcnm") );
                     pstmt.setString(index++, v_grade);
pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
pstmt.setCharacterStream(index++, new StringReader(""), 0);
                     isOk=pstmt.executeUpdate();
//                sql1 = "select intro from tz_cmuusermst where cmuno = '" + v_cmuno + "' and userid ='"+s_userid+"'";    
//                connMgr.setOracleCLOB(sql1, v_intro);
                
//                sql1 = "select close_reason from tz_cmuusermst where cmuno = '" + v_cmuno + "' and userid ='"+s_userid+"'";
//                connMgr.setOracleCLOB(sql1, ""); 
                }   
 
    
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
     * 회원가 재입하기
     * @param box      receive from the form object and session
     * @return isOk    1:update success,0:update fail
     * @throws Exception
     */
     public int updateFrJoin(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         PreparedStatement pstmt = null;
         ListSet     ls          = null;
         StringBuffer  sql         = new StringBuffer();
         String      sql1        = "";
         int         isOk        = 1;

         String v_cmuno         = box.getString("p_cmuno");
         String v_intro     = StringManager.replace(StringUtil.removeTag(box.getString("p_content")),"<br>","\n");
         String s_userid    = box.getSession("userid");
         String v_grade     = "";
         String v_in_method_fg="1";
         try {
              connMgr = new DBConnectionManager();
              connMgr.setAutoCommit(false); 

              //최하위등급구하기
              sql1  = " select max(grcode) grcode from tz_cmugrdcode where cmuno ='"+v_cmuno+"'";
              ls = connMgr.executeQuery(sql1);
              while (ls.next()) {
                    v_grade= ls.getString("grcode");
              }
              if(ls != null) { try { ls.close(); } catch (Exception e) {} }
              //회원가입방식
              sql1  = " select  in_method_fg from tz_cmubasemst where cmuno ='"+v_cmuno+"'";
              ls = connMgr.executeQuery(sql1);
              while (ls.next()) {
                    v_in_method_fg= ls.getString("in_method_fg");
              }
             
            	  sql.append(" update tz_cmuusermst set									\n ");
            	  sql.append(" 	close_fg = ?, grade = ? , intro = ?                    \n ");
            	  sql.append("   , modifier_dte = to_char(sysdate, 'YYYYMMDDHH24MISS')  \n ");
            	  sql.append(" where cmuno = ?                                          \n ");
            	  sql.append(" and userid = ?                                           \n ");

            	  pstmt = connMgr.prepareStatement(sql.toString());

            	  int index = 1;
            	  if("1".equals(v_in_method_fg))pstmt.setString(index++, "1" );//신청즉시가입
            	  else pstmt.setString(index++, "0" ); // 신청 승인 후 가입
            	  pstmt.setString(index++, v_grade);
            	  pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
                  pstmt.setString(index++, v_cmuno );
                  pstmt.setString(index++, s_userid );
                  isOk=pstmt.executeUpdate();
                   
                     
                 sql1  = " update tz_cmubasemst set MEMBER_CNT = MEMBER_CNT+1 where cmuno ='"+v_cmuno+"'";
                 pstmt = connMgr.prepareStatement(sql1);
                 pstmt.executeUpdate();

              if(isOk > 0 ) {
                 if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
              }

         }
         catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
    * 회원정보수정
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateFrMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        String      createCmuno ="";
        int         isOk        = 1;
        int         v_seq       = 0;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_intro     = StringManager.replace(StringUtil.removeTag(box.getString("p_content")),"<br>","\n");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");
        String v_grade     = "";
        String v_in_method_fg="1";
        try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false); 

             sql  =" update tz_cmuusermst  set  tel         =?"
                  +"                          , mobile      =?"
                  +"                          , office_tel  =?"
                  +"                          , fax         =?"
                  +"                          , email       =?"
                  +"                          , duty        =?"  
                  +"                          , wk_area     =?"
//                  +"                          , intro       =empty_clob()"
                  +"                          , intro       =?"
                  +"                          , modifier_dte=to_char(sysdate, 'YYYYMMDDHH24MISS')"
                  +"                where  cmuno  =?"
                  +"                  and  userid =?";
             pstmt = connMgr.prepareStatement(sql);
int index = 1;

             pstmt.setString(index++, box.getString("p_tel")            );
             pstmt.setString(index++, box.getString("p_mobile")            );
             pstmt.setString(index++, box.getString("p_office_tel")            );
             pstmt.setString(index++, box.getString("p_fax")            );
             pstmt.setString(index++, box.getString("p_email")            );
             pstmt.setString(index++, box.getString("p_duty")            );
             pstmt.setString(index++, box.getString("p_wk_area")            );
pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
             pstmt.setString(index++, box.getString("p_cmuno")            );
             pstmt.setString(index++, s_userid    );
             isOk=pstmt.executeUpdate();

//			 sql1 = "select intro from tz_cmuusermst where cmuno = '" + v_cmuno + "' and userid ='"+s_userid+"'";
//             connMgr.setOracleCLOB(sql1, v_intro);
             
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
    * 회원정보수정
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateFrEmailMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        String      createCmuno ="";
        int         isOk        = 1;
        int         isOk1        = 1;
        int         v_seq       = 0;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_intro     = StringManager.replace(box.getString("content"),"<br>","\n");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");
        String v_grade     = "";
        String v_in_method_fg="1";
        try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false); 

             sql  =" update tz_member  set  email       =?"
                  +"                where  userid =?";
             pstmt = connMgr.prepareStatement(sql);
             pstmt.setString(1, box.getString("p_email")            );
             pstmt.setString(2, s_userid    );
             isOk=pstmt.executeUpdate();

             if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }

             sql  =" update tz_cmuusermst  set email       =?"
                  +"                          , modifier_dte=to_char(sysdate, 'YYYYMMDDHH24MISS')"
                  +"                where  cmuno  =?"
                  +"                  and  userid =?";
             pstmt = connMgr.prepareStatement(sql);

             pstmt.setString(1, box.getString("p_email")            );
             pstmt.setString(2, box.getString("p_cmuno")            );
             pstmt.setString(3, s_userid    );
             isOk1=pstmt.executeUpdate();
             
             if((isOk *isOk1)> 0 ) {
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
        return isOk *isOk1;
    }



    /**
    * 최근접속일수정
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateFrRecentDte(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        String      createCmuno ="";
        int         isOk        = 1;
        int         isOk1        = 1;
        int         v_seq       = 0;

        String v_static_cmuno  	= box.getString("p_static_cmuno");
        String v_cmuno         	= box.getString("p_cmuno");
        String s_userid    		= box.getSession("userid");
        String s_name      		= box.getSession("name");
        String v_grade     		= "";
        String v_in_method_fg="1";
        try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false); 

             sql  =" update tz_cmuusermst  set  recent_dte=to_char(sysdate, 'YYYYMMDDHH24MISS')"
                  +"                where  cmuno  =?"
                  +"                  and  userid =?";
             pstmt = connMgr.prepareStatement(sql);

             pstmt.setString(1, v_cmuno           );
             pstmt.setString(2, s_userid    );
             isOk1=pstmt.executeUpdate();
             
             if((isOk *isOk1)> 0 ) {
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
        return isOk *isOk1;
    }
}
