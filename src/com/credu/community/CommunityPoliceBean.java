//**********************************************************
//1. 제      목:
//2. 프로그램명: CommunityPoliceBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.community;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.community.*;
import com.namo.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityPoliceBean {
    private ConfigSet config;
    private int row;

    public CommunityPoliceBean() {
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
    * 불량 커뮤니티 신고
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertPolice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";

        int         isOk        = 0;
        int         v_policeno       = 0;

        String v_cmuno     = box.getString("p_cmuno");
        String v_cmu_nm    = box.getString("p_cmu_nm");
        String v_email     = box.getString("p_email");
        String v_intro     = StringManager.replace(box.getString("content"),"<br>","\n");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");


        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 

           sql  = "select NVL(max(policeno),0) from tz_cmupolice";
           System.out.println(sql);
           ls = connMgr.executeQuery(sql);
           while (ls.next()) {
               v_policeno = ls.getInt(1) + 1;
           } 

           sql  =" insert into tz_cmupolice (policeno, cmuno, cmu_nm, userid, email, content, singo_dte, str_fg)"
             +"                   values  (?,?,?,?,?"
             //2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거 
//             +"                           ,empty_clob(),to_char(sysdate, 'YYYYMMDDHH24MISS'),'1')"
             +"                           ,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'1')"
             ;
            System.out.println(sql);
int index = 1;
           pstmt = connMgr.prepareStatement(sql);


           pstmt.setInt   (index++, v_policeno                       );//커뮤니티번호
           pstmt.setString(index++, v_cmuno     );//커뮤니티번호
           pstmt.setString(index++, v_cmu_nm    );//커뮤니티명
           pstmt.setString(index++, s_userid    );//신고자
           pstmt.setString(index++, v_email     );//신고자이메일
pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
//		   pstmt.setString(index++, v_intro     );
           isOk = pstmt.executeUpdate();

//           sql1 = "select content from tz_cmupolice where policeno = '" + v_policeno + "'";
//           connMgr.setOracleCLOB(sql1, v_intro);       //      (기타 서버 경우)

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
    * 커뮤니티 정보변경
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateBaseMst(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        int         isOk        = 0;
        int         v_seq       = 0;

        String v_cmuno     = box.getString("p_cmuno");
        String v_adtitle   = box.getString("p_adtitle");
        String v_intro     = StringManager.replace(box.getString("content"),"<br>","\n");

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        String thisYear="";
        String v_templetfile = "";

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 


           sql  =" update tz_cmubasemst set  cmu_nm             =?   "
                +"                         , in_method_fg       =?   "
                +"                         , search_fg          =?   "
                +"                         , data_passwd_fg     =?   "
                +"                         , display_fg         =?   "
                +"                         , type_l             =?   "  
                +"                         , type_m             =?   "
                //2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거 
//                +"                         , intro              =empty_clob()      "
                +"                         , intro              =?      "
                +"                         , img_path           =?   "
                +"                         , layout_fg          =?   "
                +"                         , html_skin_fg       =?   "
                +"                         , modifier_dte       =to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"                         , modifier_userid    =?   "
                +"                where cmuno = ?"
                ;
int index = 1;
           pstmt = connMgr.prepareStatement(sql);

           pstmt.setString(index++, box.getString("p_cmu_nm")          );//커뮤니티명
           pstmt.setString(index++, box.getString("p_in_method_fg")   );//회원가입방식
           pstmt.setString(index++, box.getString("p_search_fg")      );//자료실검색허용ㅇ
           pstmt.setString(index++, box.getStringDefault("p_data_passwd_fg","N") );//자료암호화구분
           pstmt.setString(index++, box.getString("p_display_fg")     );//커뮤니티공개구분
           pstmt.setString(index++, box.getString("p_type_l")         );//대분류
           pstmt.setString(index++, box.getString("p_type_m")         );//중분류
pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
//		   pstmt.setString(index++, v_intro         );
           pstmt.setString(index++, box.getNewFileName("p_img_path")  );//이미지
           pstmt.setString(index++, box.getString("p_layout_fg")     );//레이아웃
           pstmt.setString(index++, box.getString("p_html_skin_fg")  );//화면스킨
           pstmt.setString(index++, s_userid                         );//수정자
           pstmt.setString(index++, v_cmuno                          );//커뮤니티번호
           isOk = pstmt.executeUpdate();

//           sql1 = "select intro from tz_cmubasemst where cmuno = '" + v_cmuno + "'";
//           connMgr.setOracleCLOB(sql1, v_intro);  
           //커뮤니티 설명 저장 
		   /*2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거 
            */

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
    * 기본정보 조회
    * @param box          receive from the form object and session
    * @return ArrayList   기본정보 데이터
    * @throws Exception
    */
    public ArrayList selectBaseMst(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();

        String              sql     = "";
        String              sql1    = "";
        DataBox             dbox    = null;
        int                 isOK    =1;

        String  v_static_cmuno = box.getString("p_static_cmuno"); 
        String  v_cmuno = box.getString("p_cmuno"); 
        String s_userid        = box.getSession("userid");
        String s_name          = box.getSession("name");
		
        try {
            connMgr = new DBConnectionManager();
  
            sql  =  " select cmuno, cmu_nm, in_method_fg, search_fg, data_passwd_fg, display_fg, type_l "
                  + "       , type_m, intro, img_path, layout_fg, html_skin_fg, read_cnt, member_cnt, close_fg"
                  + "       , close_reason, close_dte, close_userid, hold_fg, accept_dte, accept_userid, register_dte "
                  + "       , register_userid, modifier_dte, modifier_userid "
                  + "   from tz_cmubasemst"
                  + "  where cmuno        = '"+v_cmuno+"'"
                  ;
            System.out.println(sql);
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

 /**
  * 선택된 자료파일 DB에서 삭제
  * @param connMgr   DB Connection Manager
  * @param box    receive from the form object and session
  * @return
  * @throws Exception
  */
 public int deleteSingleFile( RequestBox box) throws Exception {
   DBConnectionManager connMgr = null;
   PreparedStatement   pstmt   = null;
   ListSet             ls      = null;
   String              sql     = "";
   String              sql2    = "";
   int                 isOk2   = 1;



  String s_userid = box.getSession("userid");
  String v_cmuno  = box.getString("p_cmuno");
  try {
       connMgr = new DBConnectionManager();
       connMgr.setAutoCommit(false); 

       sql  =" update tz_cmubasemst set img_path='' "
           + "  where cmuno        = ?"
       ;
       pstmt = connMgr.prepareStatement(sql);
       pstmt.setString(1, v_cmuno       );//커뮤니티번호
       isOk2 = pstmt.executeUpdate();
       if(isOk2 > 0) {
          if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
      }
  }
  catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, box, sql);
   throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
  }
  finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
  }
  return isOk2;
 }

}
