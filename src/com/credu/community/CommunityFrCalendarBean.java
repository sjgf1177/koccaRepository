//**********************************************************
//1. 제      목:
//2. 프로그램명: CommunityFrCalendarBean.java
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
import java.util.Calendar;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.dunet.common.util.StringUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityFrCalendarBean {
    private ConfigSet config;
    private int row;

    public CommunityFrCalendarBean() {
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
    * 왼쪽에문자붙이기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public String LPAD(String str, int len, char pad) {
        String result = str;
        int templen = len - result.getBytes().length;

        for (int i = 0; i < templen; i++)
            result = pad + result;

        return result;
    }

    /**
    * 일정등록
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertFrCalendar(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        String      createCmuno ="";
        int         isOk        = 1;
        int         v_calno     = 0;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        String v_calday        = box.getString("p_calday_year")+LPAD(box.getString("p_calday_month"),2,'0')+LPAD(box.getString("p_calday_day"),2,'0');
        String v_caltime       = "0000";
        String v_title         = StringUtil.removeTag(box.getString("p_title"));
        String v_security_fg   = box.getStringDefault("p_security_fg","Y");
        String v_ftime         = LPAD(box.getString("p_ftime_hh"),2,'0')+LPAD(box.getString("p_ftime_mm"),2,'0');
        String v_ttime         = LPAD(box.getString("p_ttime_hh"),2,'0')+LPAD(box.getString("p_ttime_mm"),2,'0');
        String v_day_fg        = box.getStringDefault("p_day_fg","N");
        String v_cal_area      = box.getString("p_cal_area");
        String v_content       = StringUtil.removeTag(box.getString("p_content"));
        String v_cycle_time    = box.getStringDefault("p_cycle_time","0");
        String v_cycle_cnt     = box.getStringDefault("p_cycle_cnt","0");
        String v_limit_day_year= box.getString("p_limit_day_year")+LPAD(box.getString("p_limit_day_month"),2,'0')+LPAD(box.getString("p_limit_day_day"),2,'0');
        String v_loop_fg       = box.getStringDefault("p_loop_fg","N");



        try {
             connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

             //일련번호구하기
             sql1  = " select max(calno) calno from tz_cmucalendar where cmuno ='"+v_cmuno+"'";
             ls = connMgr.executeQuery(sql1);
             while (ls.next()) v_calno= ls.getInt(1);
             v_calno++;

             sql  =" insert into tz_cmucalendar ( cmuno          , calno       , calday    , caltime   , title    "
                  +"                           , security_fg    , ftime       , ttime     , day_fg    , cal_area "
                  +"                           , content        , cycle_time  , cycle_cnt , limit_day , loop_fg  "
                  +"                           , all_fg                                                          "
                  +"                           , register_userid, register_dte                                   "
                  +"                           , modifier_userid, modifier_dte                                   "
                  +"                           , del_fg)                                                         "
                  +"             values (       ?               , ?           , ?         ,?          , ?"
                  +"                          , ?               , ?           , ?         ,?          , ?"
//                  +"                          , empty_clob()    , ?           , ?         ,?          , ?"
                  +"                          , ?    , ?           , ?         ,?          , ?"
                  +"                          , ?                                                         "
                  +"                          , ?           ,to_char(sysdate, 'YYYYMMDDHH24MISS')"
                  +"                          , ?           ,to_char(sysdate, 'YYYYMMDDHH24MISS')"
                  +"                          ,'N' )" ;
int index = 1;
             pstmt = connMgr.prepareStatement(sql);
             pstmt.setString(index++ , v_cmuno            );
             pstmt.setInt   (index++ , v_calno            );
             pstmt.setString(index++ , v_calday           );
             pstmt.setString(index++ , v_caltime          );
             pstmt.setString(index++ , v_title            );
             pstmt.setString(index++ , v_security_fg      );
             pstmt.setString(index++ , v_ftime            );
             pstmt.setString(index++ , v_ttime            );
             pstmt.setString(index++ , v_day_fg           );
			 pstmt.setString(index++, v_cal_area         );
pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
             pstmt.setString(index++, v_cycle_time       );
             pstmt.setString(index++, v_cycle_cnt        );
             pstmt.setString(index++, v_limit_day_year   );
             pstmt.setString(index++, v_loop_fg          );
             pstmt.setString(index++, "1"                );
             pstmt.setString(index++, s_userid           );
             pstmt.setString(index++, s_userid           );

             isOk=pstmt.executeUpdate();

//             sql1 = "select content from tz_cmucalendar where cmuno = '" + v_cmuno  + "' and calno ="+v_calno;
//             connMgr.setOracleCLOB(sql1, v_content);

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
    * 일정수정
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updateFrCalendar(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        String      createCmuno ="";
        int         isOk        = 1;


        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String  v_calno         = box.getString("p_calno");

        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        String v_calday        = box.getString("p_calday_year")+LPAD(box.getString("p_calday_month"),2,'0')+LPAD(box.getString("p_calday_day"),2,'0');
        String v_caltime       = "0000";
        String v_title         = StringUtil.removeTag(box.getString("p_title"));
        String v_security_fg   = box.getStringDefault("p_security_fg","Y");
        String v_ftime         = LPAD(box.getString("p_ftime_hh"),2,'0')+LPAD(box.getString("p_ftime_mm"),2,'0');
        String v_ttime         = LPAD(box.getString("p_ttime_hh"),2,'0')+LPAD(box.getString("p_ttime_mm"),2,'0');
        String v_day_fg        = box.getStringDefault("p_day_fg","N");
        String v_cal_area      = box.getString("p_cal_area");
        String v_content       = StringUtil.removeTag(box.getString("p_content"));
        String v_cycle_time    = box.getStringDefault("p_cycle_time","0");
        String v_cycle_cnt     = box.getStringDefault("p_cycle_cnt","0");
        String v_limit_day_year= box.getString("p_limit_day_year")+LPAD(box.getString("p_limit_day_month"),2,'0')+LPAD(box.getString("p_limit_day_day"),2,'0');
        String v_loop_fg       = box.getStringDefault("p_loop_fg","N");



        try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false);

             sql  =" update tz_cmucalendar set calday           =?"
                  +"                         , title            =?"
                  +"                         , security_fg      =?"
                  +"                         , ftime            =?"
                  +"                         , ttime            =?"
                  +"                         , day_fg           =?"
                  +"                         , cal_area         =?"
//                  +"                         , content          =empty_clob()"
                  +"                         , content          =?"
                  +"                         , cycle_time       =?"
                  +"                         , cycle_cnt        =?"
                  +"                         , limit_day        =?"
                  +"                         , loop_fg          =?"
                  +"                         , modifier_userid  =?"
                  +"                         , modifier_dte     =to_char(sysdate, 'YYYYMMDDHH24MISS')"
                  +"   where cmuno            =?"
                  +"     and calno            =?";
int index = 1;

             pstmt = connMgr.prepareStatement(sql);
             pstmt.setString(index++ , v_calday           );
             pstmt.setString(index++ , v_title            );
             pstmt.setString(index++ , v_security_fg      );
             pstmt.setString(index++ , v_ftime            );
             pstmt.setString(index++ , v_ttime            );
             pstmt.setString(index++ , v_day_fg           );
             pstmt.setString(index++, v_cal_area         );
pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			 pstmt.setString(index++, v_cycle_time       );
             pstmt.setString(index++, v_cycle_cnt        );
             pstmt.setString(index++, v_limit_day_year   );
             pstmt.setString(index++, v_loop_fg          );
             pstmt.setString(index++, s_userid           );
             pstmt.setString(index++, v_cmuno            );
             pstmt.setString(index++,v_calno            );

             isOk=pstmt.executeUpdate();

//             sql1 = "select content from tz_cmucalendar where cmuno = '" + v_cmuno  + "' and calno ="+v_calno;
//             connMgr.setOracleCLOB(sql1, v_content);

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
    * 일정삭제
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int deleteFrCalendar(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";
        String      createCmuno ="";
        int         isOk        = 1;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String  v_calno         = box.getString("p_calno");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false);
             sql  ="\n  delete from tz_cmucalendar "
                  +"\n   where cmuno            =?"
                  +"\n     and calno            =?";

             pstmt = connMgr.prepareStatement(sql);
             pstmt.setString(1 , v_cmuno            );
             pstmt.setString   (2 , v_calno            );
             isOk=pstmt.executeUpdate();

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
        String              sql     = "";
        String              sql1    = "";
        DataBox             dbox    = null;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");

        String v_if_flag       = box.getStringDefault("p_if_flag","MM");
        String v_if_mm      = box.getString("p_if_mm");
        String v_if_ww      = box.getString("p_if_ww");
        String v_if_dd      = box.getString("p_if_dd");

        String s_userid     = box.getSession("userid");

        String v_if_fdte    ="";
        String v_if_tdte    ="";

        try {
            connMgr = new DBConnectionManager();

            if("MM".equals(v_if_flag)){//월별
               if(v_if_mm.length() <1)v_if_mm=FormatDate.getDate("yyyyMMddHHmmssSSS").substring(0,6);
            } else if("WW".equals(v_if_flag)){//주별

            } else {
            }
            sql1 ="\n  select    a.cmuno            cmuno           , a.calno       calno     , a.calday    calday   , a.caltime   caltime  , a.title    title   "
                 +"\n          , a.security_fg     security_fg    , a.ftime       ftime     , a.ttime     ttime    , a.day_fg    day_fg   , a.cal_area cal_area"
                 +"\n          , a.content         content        , a.cycle_time  cycle_time, a.cycle_cnt cycle_cnt, a.limit_day limit_day, a.loop_fg  loop_fg "
                 +"\n          , a.all_fg          all_fg                                                       "
                 +"\n          , a.register_userid register_userid, a.register_dte register_dte                                  "
                 +"\n          , a.modifier_userid modifier_userid, a.modifier_dte modifier_dte                                  "
                 +"\n    from tz_cmucalendar a,tz_member b,tz_cmuusermst c "
                 +"\n   where a.register_userid  = b.userid "
                 +"\n     and a.cmuno            = c.cmuno "
                 +"\n     and a.register_userid  = c.userid "
                 +"\n     and a.cmuno            = '"+v_cmuno+"'"
                 +"\n     and a.del_fg           = 'N'";

            if("MM".equals(v_if_flag )){
                sql = "select a.* from ("
                     +sql1
                     +"\n     and b.userid          != '"+s_userid+"'"
                     +"\n     and a.security_fg      = 'Y'"
                     +"\n     and a.loop_fg          = 'Y'"
                     +"\n     and a.calday           <= '"+v_if_mm+"31'"
                     +"\n  union all"
                     +sql1
                     +"\n     and b.userid          != '"+s_userid+"'"
                     +"\n     and a.security_fg      = 'Y'"
                     +"\n     and a.loop_fg          = 'N'"
                     +"\n     and a.calday           <= '"+v_if_mm+"31'"
                     +"\n     and a.limit_day           >= '"+v_if_mm+"01'"
                     +"\n  union all"
                     +sql1
                     +"\n     and b.userid          = '"+s_userid+"'"
                     +"\n     and a.loop_fg          = 'Y'"
                     +"\n     and a.calday           <= '"+v_if_mm+"31'"
                     +"\n  union all"
                     +sql1
                     +"\n     and b.userid           = '"+s_userid+"'"
                     +"\n     and a.loop_fg          = 'N'"
                     +"\n     and a.calday           <= '"+v_if_mm+"31'"
                     +"\n     and a.limit_day           >= '"+v_if_mm+"01'"
                     +"\n ) a"
                     +"\n order by a.calday asc,a.ftime asc"
                     ;
            } else if("WW".equals(v_if_flag )){
              Calendar cal = Calendar.getInstance();
              cal.set(Calendar.YEAR, Integer.parseInt(v_if_ww.substring(0,4)));
              cal.set(Calendar.MONTH, Integer.parseInt(v_if_ww.substring(4,6)) - 1);

              cal.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(v_if_ww.substring(6)));

              cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
              int startDay = cal.get(Calendar.DAY_OF_MONTH);
              String sfDay =  String.valueOf(cal.get(Calendar.YEAR))+LPAD(String.valueOf(cal.get(Calendar.MONTH)+1),2,'0')+LPAD(String.valueOf(startDay),2,'0');

              cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
              int endDay = cal.get(Calendar.DAY_OF_MONTH);
               String stDay =  String.valueOf(cal.get(Calendar.YEAR))+LPAD(String.valueOf(cal.get(Calendar.MONTH)+1),2,'0')+LPAD(String.valueOf(endDay),2,'0');

              if (Integer.parseInt(v_if_ww.substring(6)) == cal.getMaximum(Calendar.WEEK_OF_MONTH) - 1 && endDay <= 7) {
                 endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
              }
              System.out.println("sfDay    ;;;;"+sfDay);
              sql = "select a.* from ("
                   +sql1
                   +"\n     and b.userid          != '"+s_userid+"'"
                   +"\n     and a.security_fg      = 'Y'"
                   +"\n     and a.loop_fg          = 'Y'"
                   +"\n     and (a.calday         <= '"+sfDay+"' or a.limit_day >= '"+endDay+"')"
                   +"\n  union all"
                   +sql1
                   +"\n     and b.userid          != '"+s_userid+"'"
                   +"\n     and a.security_fg      = 'Y'"
                   +"\n     and a.loop_fg          = 'N'"
                   +"\n     and (a.calday         <= '"+sfDay+"' or a.limit_day >= '"+endDay+"')"
//                   +"\n     and a.limit_day         between '"+sfDay+"' and '"+stDay+"')"
                   +"\n  union all"
                   +sql1
                   +"\n     and b.userid          = '"+s_userid+"'"
                   +"\n     and a.loop_fg          = 'Y'"
                   +"\n     and (a.calday         <= '"+sfDay+"' or a.limit_day >= '"+endDay+"')"
                   +"\n  union all"
                   +sql1
                   +"\n     and b.userid           = '"+s_userid+"'"
                   +"\n     and a.loop_fg          = 'N'"
                   +"\n     and (a.calday         <= '"+sfDay+"' or a.limit_day >= '"+endDay+"')"
//                   +"\n      or a.limit_day         between '"+sfDay+"' and '"+stDay+"')"
                   +"\n ) a"
                   +"\n order by a.calday asc,a.ftime asc" ;
            } else {
                sql = "select a.* from ("
                     +sql1
                     +"\n     and b.userid          != '"+s_userid+"'"
                     +"\n     and a.security_fg      = 'Y'"
                     +"\n     and a.loop_fg          = 'Y'"
                     +"\n     and a.calday           <= '"+v_if_dd+"'"
                     +"\n  union all"
                     +sql1
                     +"\n     and b.userid          != '"+s_userid+"'"
                     +"\n     and a.security_fg      = 'Y'"
                     +"\n     and a.loop_fg          = 'N'"
                     +"\n     and a.calday           <= '"+v_if_dd+"'"
                     +"\n     and a.limit_day           >= '"+v_if_dd+"'"
                     +"\n  union all"
                     +sql1
                     +"\n     and b.userid          = '"+s_userid+"'"
                     +"\n     and a.loop_fg          = 'Y'"
                     +"\n     and a.calday           <= '"+v_if_dd+"'"
                     +"\n  union all"
                     +sql1
                     +"\n     and b.userid           = '"+s_userid+"'"
                     +"\n     and a.loop_fg          = 'N'"
                     +"\n     and a.calday           <= '"+v_if_dd+"'"
                     +"\n     and a.limit_day           >= '"+v_if_dd+"'"
                     +"\n ) a"
                     +"\n order by a.calday asc,a.ftime asc" ;

            }
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
    * 조회
    * @param box          receive from the form object and session
    * @return ArrayList   조회
    * @throws Exception
    */
    public ArrayList selectSingle(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        String              sql1    = "";
        DataBox             dbox    = null;

        String v_static_cmuno  = box.getString("p_static_cmuno");
        String v_cmuno         = box.getString("p_cmuno");
        String v_calno         = box.getString("p_calno");

        String s_userid             = box.getSession("userid");

        String v_if_fdte       ="";
        String v_if_tdte       ="";

        try {
            connMgr = new DBConnectionManager();


            sql ="\n  select    a.cmuno            cmuno           , a.calno       calno     , a.calday    calday   , a.caltime   caltime  , a.title    title   "
                 +"\n          , a.security_fg     security_fg    , a.ftime       ftime     , a.ttime     ttime    , a.day_fg    day_fg   , a.cal_area cal_area"
                 +"\n          , a.content         content        , a.cycle_time  cycle_time, a.cycle_cnt cycle_cnt, a.limit_day limit_day, a.loop_fg  loop_fg "
                 +"\n          , a.all_fg          all_fg                                                       "
                 +"\n          , a.register_userid register_userid, a.register_dte register_dte                                  "
                 +"\n          , a.modifier_userid modifier_userid, a.modifier_dte modifier_dte                                  "
                 +"\n          , c.userid          userid        "
                 +"\n          , c.kor_name        kor_name      , c.eng_name        eng_name      "
                 +"\n          , c.email           email         , c.tel             tel           "
                 +"\n          , c.mobile          mobile        , c.office_tel      office_tel    "
                 +"\n          , c.duty            duty          , c.wk_area         wk_area       "
                 +"\n          , c.grade           grade         , c.request_dte     request_dte   "
                 +"\n          , c.license_dte     license_dte   , c.license_userid  license_userid"
                 //2005.11.21_하경태 : Underlying input stream returned zero bytes(Text 형이 null or 0 byte 일 경우에러나므로 변경.
                 //+"\n          , c.close_fg        close_fg      , c.close_reason    close_reason  "
				 //+"\n          , c.close_dte       close_dte     , c.intro           intro         "
				 +"\n          , c.close_fg as close_fg  , c.close_reason as close_reason  "
				 +"\n          , c.close_dte close_dte, c.intro as intro "

                 +"\n          , c.recent_dte      recent_dte    , c.visit_num       visit_num     "
                 +"\n          , d.kor_nm          grade_kor_nm   "
                 +"\n          , b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm"
                 +"\n    from tz_cmucalendar a,tz_member b,tz_cmuusermst c ,tz_cmugrdcode d "
                 +"\n   where a.register_userid  = b.userid "
                 +"\n     and a.cmuno            = c.cmuno "
                 +"\n     and a.register_userid  = c.userid "
                 +"\n     and c.cmuno            = d.cmuno"
                 +"\n     and c.grade            = d.grcode"
                 +"\n     and a.cmuno            = '"+v_cmuno+"'"
                 +"\n     and a.calno            = '"+v_calno+"'"
                 +"\n     and a.del_fg           = 'N'";

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
    public boolean dateValid(String src) {
        int year = 0;
        int month = 0;
        int day = 0;

        if (src.length() != 8) return false;

        try {
            year = Integer.parseInt(src.substring(0,4));
            month = Integer.parseInt(src.substring(4,6));
            day = Integer.parseInt(src.substring(6));
        } catch (Exception e) { return false; }

        // 윤년 Flag
        boolean flag = false;

        // 입력받은 년도 윤년인지 체크
        if (year % 4 == 0) {
            flag = true;
            if (year % 100 == 0) {
                flag = false;
                if (year % 400 == 0) {
                    flag = true;
                }
            }
        }

        int dayArray[] = new int[12];
        {
            dayArray[0] = 31;
            dayArray[1] = (flag) ? 29 : 28;
            dayArray[2] = 31;
            dayArray[3] = 30;
            dayArray[4] = 31;
            dayArray[5] = 30;
            dayArray[6] = 31;
            dayArray[7] = 31;
            dayArray[8] = 30;
            dayArray[9] = 31;
            dayArray[10] = 30;
            dayArray[11] = 31;
        }
        if (month < 1 || month > 12)
            return false;
        if (day < 1 || day > dayArray[month-1])
            return false;

        return true;
    }
}
