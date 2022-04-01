//**********************************************************
//  1. 제      목: 접속통계
//  2. 프로그램명: CountBean.java
//  3. 개      요: 접속통계
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 7
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class CountBean {

    public CountBean() {
    }

    /**
     * 로그 작성
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : log ok 2 : log fail
     * @throws Exception
     */
    public int writeLog(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int is_Ok = 0;

        try {
            connMgr = new DBConnectionManager();
            is_Ok = writeLog(connMgr, box);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 로그 작성
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : log ok 2 : log fail
     * @throws Exception
     */
    public int writeLog(DBConnectionManager connMgr, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int cnt = 0;
        int is_Ok = 0;

        //        String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");
        String v_year = FormatDate.getDate("yyyy");
        String v_month = FormatDate.getDate("MM");
        String v_day = FormatDate.getDate("dd");
        String v_time = FormatDate.getDate("HH");
        //        String v_week  = FormatDate.getDayOfWeek();
        String v_week = "";

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(v_year), Integer.parseInt(v_month) - 1, Integer.parseInt(v_day));
        v_week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)); //1(일),2(월),3(화),4(수),5(목),6(금),7(토)

        try {
            sql1 = " select count(*) cnt ";
            sql1 += " from TZ_HOMECOUNT                     ";
            sql1 += " where gubun      = " + StringManager.makeSQL(v_gubun);
            sql1 += "   and date_year  = " + StringManager.makeSQL(v_year);
            sql1 += "   and date_month = " + StringManager.makeSQL(v_month);
            sql1 += "   and date_day   = " + StringManager.makeSQL(v_day);
            sql1 += "   and date_time  = " + StringManager.makeSQL(v_time);
            sql1 += "   and date_week  = " + StringManager.makeSQL(v_week);
            sql1 += "   or to_char(sysdate, 'YYYYMMDD') = (select substring(lglast,1,8) from TZ_MEMBER where userid =" + StringManager.makeSQL(v_userid) + "\n and GRCODE = " + StringManager.makeSQL(box.getSession("tem_grcode")) + ")";

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }
            ls.close();

            if (cnt > 0) { // update
                sql2 = " update TZ_HOMECOUNT set cnt = cnt + 1                      ";
                sql2 += " where gubun    = ?  and date_year = ?  and date_month = ?  ";
                sql2 += "   and date_day = ?  and date_time = ?  and date_week  = ?  ";
                sql2 += "   and to_char(sysdate, 'YYYYMMDD') != (select substring(lglast,1,8) from TZ_MEMBER where userid =?  and GRCODE = " + StringManager.makeSQL(box.getSession("tem_grcode")) + ")";

                pstmt = connMgr.prepareStatement(sql2);

                pstmt.setString(1, v_gubun);
                pstmt.setString(2, v_year);
                pstmt.setString(3, v_month);
                pstmt.setString(4, v_day);
                pstmt.setString(5, v_time);
                pstmt.setString(6, v_week);
                pstmt.setString(7, v_userid);
                
 // todo
System.out.println("============================== v_gubun : " + v_gubun);
System.out.println("============================== v_year : " + v_year); 
System.out.println("============================== v_month : " + v_month); 
System.out.println("============================== v_day : " + v_day); 
System.out.println("============================== v_time : " + v_time); 
System.out.println("============================== v_week : " + v_week); 
System.out.println("============================== v_userid : " + v_userid); 

            } else { // insert
                sql3 = " insert into TZ_HOMECOUNT(gubun, date_year, date_month, date_day, date_time, date_week, cnt) ";
                sql3 += "                    values (?, ?, ?, ?, ?, ?, ?)                                             ";
                pstmt = connMgr.prepareStatement(sql3);

                pstmt.setString(1, v_gubun);
                pstmt.setString(2, v_year);
                pstmt.setString(3, v_month);
                pstmt.setString(4, v_day);
                pstmt.setString(5, v_time);
                pstmt.setString(6, v_week);
                pstmt.setInt(7, 1);
            }
            is_Ok = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 년통계 카운트
     * 
     * @param box receive from the form object and session
     * @return result 년통계카운트
     * @throws Exception
     */
    public int SelectYearCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        // CountData data = null;
        String sql = "";
        int result = 0;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            sql = "select date_year, sum(cnt) cnt		      ";
            sql += " from TZ_HOMECOUNT                        ";
            sql += " where date_year  = " + StringManager.makeSQL(v_year);
            if (!v_gubun.equals("ALL"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            sql += " group by date_year                ";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                // data = new CountData();
                result = ls.getInt("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return result;
    }

    /**
     * 월통계 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월통계 리스트
     * @throws Exception
     */
    public ArrayList<CountData> SelectMonth(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, sum(cnt) cnt     ";
            sql += " from TZ_HOMECOUNT                                   ";
            sql += " where date_year  = " + StringManager.makeSQL(v_year);
            if (!v_gubun.equals("ALL"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            sql += " group by date_year, date_month						 ";
            sql += " order by date_month asc                             ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

                //              data.setGubun(ls.getString("gubun"));
                data.setDate_year(ls.getString("date_year"));
                data.setDate_month(ls.getString("date_month"));
                data.setCnt(ls.getInt("cnt"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 월일통계 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월일통계 리스트
     * @throws Exception
     */
    public ArrayList<CountData> SelectMonthDay(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_month", FormatDate.getDate("MM"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, date_day, sum(cnt) cnt ";
            sql += " from TZ_HOMECOUNT                                         ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            if (!v_gubun.equals("ALL"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            sql += " group by date_year, date_month, date_day           ";
            sql += " order by date_day asc                                     ";
            System.out.println("CountBean 월일통계 리스트 SelectMonthDay:" + sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

                //                data.setGubun(ls.getString("gubun"));
                data.setDate_year(ls.getString("date_year"));
                data.setDate_month(ls.getString("date_month"));
                data.setDate_day(ls.getString("date_day"));
                data.setCnt(ls.getInt("cnt"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 일시통계 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 일시통계 리스트
     * @throws Exception
     */
    public ArrayList<CountData> SelectDayTime(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_month", FormatDate.getDate("MM"));
        String v_day = box.getStringDefault("p_date_day", FormatDate.getDate("dd"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, date_day, date_time, cnt ";
            sql += " from TZ_HOMECOUNT                                    ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            sql += "   and date_day    = " + StringManager.makeSQL(v_day);
            if (!v_gubun.equals("ALL"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            sql += " order by date_time asc                               ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

                //                data.setGubun(ls.getString("gubun"));
                data.setDate_year(ls.getString("date_year"));
                data.setDate_month(ls.getString("date_month"));
                data.setDate_day(ls.getString("date_day"));
                data.setDate_time(ls.getString("date_time"));
                data.setCnt(ls.getInt("cnt"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 월시통계 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월시통계 리스트
     * @throws Exception
     */
    public ArrayList<CountData> SelectMonthTime(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_month", FormatDate.getDate("MM"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, date_time, sum(cnt) cnt ";
            sql += " from TZ_HOMECOUNT                                   ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            if (!v_gubun.equals("ALL"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            sql += " group by date_year, date_month, date_time           ";
            sql += " order by date_time asc                              ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

                //                data.setGubun(ls.getString("gubun"));
                data.setDate_year(ls.getString("date_year"));
                data.setDate_month(ls.getString("date_month"));
                data.setDate_time(ls.getString("date_time"));
                data.setCnt(ls.getInt("cnt"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 월요일통계 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월요일통계 리스트
     * @throws Exception
     */
    public ArrayList<CountData> SelectMonthWeek(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_week", FormatDate.getDate("MM"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, date_week, sum(cnt) cnt ";
            sql += " from TZ_HOMECOUNT                                   ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            if (!v_gubun.equals("ALL"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            sql += " group by date_year, date_month, date_week     ";
            sql += " order by date_week asc                               ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

                //                data.setGubun(ls.getString("gubun"));
                data.setDate_year(ls.getString("date_year"));
                data.setDate_month(ls.getString("date_month"));
                data.setDate_week(ls.getString("date_week"));
                data.setCnt(ls.getInt("cnt"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

}