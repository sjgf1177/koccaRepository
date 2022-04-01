//**********************************************************
//  1. 제      목: 접속통계
//  2. 프로그램명: SubjCountBean.java
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
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class SubjCountBean {
    // private static final String CONFIG_NAME = "cur_nrm_grcode";

    public SubjCountBean() {
    }

    /**
     * 로그 작성
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : log ok 2 : log fail
     * @throws Exception
     */
    public int writeLog(RequestBox box, String v_subj, String v_gyear, String v_subjseq) throws Exception {

        DBConnectionManager connMgr = null;

        ListSet ls = null;

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;

        StringBuffer sql = new StringBuffer();

        int cnt = 0;
        int is_Ok = 0;

        String v_gubun = ""; //교육주관코드
        String v_year = FormatDate.getDate("yyyy");
        String v_month = FormatDate.getDate("MM");
        String v_day = FormatDate.getDate("dd");
        String v_time = FormatDate.getDate("HH");
        String v_week = "";

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(v_year), Integer.parseInt(v_month) - 1, Integer.parseInt(v_day));

        v_week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)); //1(일),2(월),3(화),4(수),5(목),6(금),7(토)

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            v_gubun = box.getSession("tem_grcode");
            if (v_gubun.equals("")) {
                box.setSession("tem_grcode", "N000001");
                v_gubun = "N000001";
            }

            sql.append(" SELECT COUNT(CNT) CNT \n");
            sql.append(" FROM TZ_SUBJCOUNT \n");
            sql.append(" WHERE GUBUN      = " + StringManager.makeSQL(v_gubun) + " \n"); //교육주관
            sql.append("   AND SUBJ       = " + StringManager.makeSQL(v_subj) + " \n"); //과정코드
            sql.append("   AND SUBJSEQ    = " + StringManager.makeSQL(v_subjseq) + " \n"); //과정차수
            sql.append("   AND DATE_YEAR  = " + StringManager.makeSQL(v_gyear) + " \n");
            sql.append("   AND DATE_MONTH = " + StringManager.makeSQL(v_month) + " \n");
            sql.append("   AND DATE_DAY   = " + StringManager.makeSQL(v_day) + " \n");
            sql.append("   AND DATE_TIME  = " + StringManager.makeSQL(v_time) + " \n");
            sql.append("   AND DATE_WEEK  = " + StringManager.makeSQL(v_week) + " \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            ls.close();

            if (cnt > 0) { // update
                sql.setLength(0);

                sql.append(" UPDATE TZ_SUBJCOUNT SET CNT = CNT+1 \n");
                sql.append(" WHERE GUBUN    = ?  AND SUBJ = ? AND SUBJSEQ = ? \n");
                sql.append("   AND DATE_YEAR = ?  AND DATE_MONTH = ? \n");
                sql.append("   AND DATE_DAY = ?  AND DATE_TIME = ?  AND DATE_WEEK  = ? \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(1, v_gubun);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, v_subjseq);

                pstmt.setString(4, v_gyear);
                pstmt.setString(5, v_month);
                pstmt.setString(6, v_day);
                pstmt.setString(7, v_time);
                pstmt.setString(8, v_week);

                is_Ok = pstmt.executeUpdate();
            } else { // insert
                sql.setLength(0);

                sql.append(" INSERT INTO TZ_SUBJCOUNT(GUBUN, DATE_YEAR, DATE_MONTH, DATE_DAY, DATE_TIME, DATE_WEEK, CNT, SUBJ,  SUBJSEQ) \n");
                sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");

                pstmt2 = connMgr.prepareStatement(sql.toString());

                pstmt2.setString(1, v_gubun);
                pstmt2.setString(2, v_gyear);
                pstmt2.setString(3, v_month);
                pstmt2.setString(4, v_day);
                pstmt2.setString(5, v_time);
                pstmt2.setString(6, v_week);
                pstmt2.setInt(7, 1);
                pstmt2.setString(8, v_subj);
                pstmt2.setString(9, v_subjseq);

                is_Ok = pstmt2.executeUpdate();

            }

            //학습창 진입시 로그남기기
            String v_userip = box.getString("p_userip");
            String v_userid = box.getSession("userid");

            sql.setLength(0);

            sql.append(" INSERT INTO TZ_SUBJLOGINID(SUBJ, YEAR, SUBJSEQ, USERID, LGIP, LDATE ) \n");
            sql.append("                    VALUES (?, ?, ?, ?, ?,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ) \n");

            pstmt3 = connMgr.prepareStatement(sql.toString());

            pstmt3.setString(1, v_subj);
            pstmt3.setString(2, v_gyear);
            pstmt3.setString(3, v_subjseq);
            pstmt3.setString(4, v_userid);
            pstmt3.setString(5, v_userip);
            pstmt3.executeUpdate();

            if (is_Ok == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
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
     * 년통계 카운트
     * 
     * @param box receive from the form object and session
     * @return result 년통계카운트
     * @throws Exception
     */
    public int SelectYearCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getString("s_subjseq");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));

        if (v_gubun.equals(""))
            v_gubun = "N000001";
        if (v_subj.equals(""))
            v_subj = "ALL";
        if (v_subjseq.equals(""))
            v_subjseq = "ALL";

        try {
            connMgr = new DBConnectionManager();

            sql = "select date_year, sum(cnt) cnt             ";
            sql += " from TZ_SUBJCOUNT                        ";
            sql += " where date_year  = " + StringManager.makeSQL(v_year);
            if (!v_gubun.equals("----"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            if (!v_subj.equals("ALL"))
                sql += "  and subj      = " + StringManager.makeSQL(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += "  and subjseq      = " + StringManager.makeSQL(v_subjseq);
            sql += " group by date_year                ";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
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
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getString("s_subjseq");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));

        if (v_gubun.equals(""))
            v_gubun = "N000001";
        if (v_subj.equals(""))
            v_subj = "ALL";
        if (v_subjseq.equals(""))
            v_subjseq = "ALL";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, sum(cnt) cnt     ";
            sql += " from TZ_SUBJCOUNT                                   ";
            sql += " where date_year  = " + StringManager.makeSQL(v_year);
            if (!v_gubun.equals("----"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            if (!v_subj.equals("ALL"))
                sql += "  and subj      = " + StringManager.makeSQL(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += "  and subjseq      = " + StringManager.makeSQL(v_subjseq);
            sql += " group by date_year, date_month                      ";
            sql += " order by date_month asc                             ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

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
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getString("s_subjseq");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_month", FormatDate.getDate("MM"));
        // String ss_action = box.getString("s_action");

        if (v_gubun.equals(""))
            v_gubun = "N000001";
        if (v_subj.equals(""))
            v_subj = "ALL";
        if (v_subjseq.equals(""))
            v_subjseq = "ALL";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            //if(ss_action.equals("go")){
            sql = "select date_year, date_month, date_day, sum(cnt) cnt         ";
            sql += " from TZ_SUBJCOUNT                                          ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            if (!v_gubun.equals("----"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            if (!v_subj.equals("ALL"))
                sql += "  and subj      = " + StringManager.makeSQL(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += "  and subjseq      = " + StringManager.makeSQL(v_subjseq);
            sql += " group by date_year, date_month, date_day                   ";
            sql += " order by date_day asc                                      ";
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
            //}
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
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getString("s_subjseq");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_month", FormatDate.getDate("MM"));
        String v_day = box.getStringDefault("p_date_day", FormatDate.getDate("dd"));

        if (v_gubun.equals(""))
            v_gubun = "N000001";
        if (v_subj.equals(""))
            v_subj = "ALL";
        if (v_subjseq.equals(""))
            v_subjseq = "ALL";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, date_day, date_time, cnt ";
            sql += " from TZ_SUBJCOUNT                                    ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            sql += "   and date_day    = " + StringManager.makeSQL(v_day);
            if (!v_gubun.equals("----"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            if (!v_subj.equals("ALL"))
                sql += "  and subj      = " + StringManager.makeSQL(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += "  and subjseq      = " + StringManager.makeSQL(v_subjseq);
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
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getString("s_subjseq");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_month", FormatDate.getDate("MM"));

        if (v_gubun.equals(""))
            v_gubun = "N000001";
        if (v_subj.equals(""))
            v_subj = "ALL";
        if (v_subjseq.equals(""))
            v_subjseq = "ALL";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, date_time, sum(cnt) cnt ";
            sql += " from TZ_SUBJCOUNT                                   ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            if (!v_gubun.equals("----"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            if (!v_subj.equals("ALL"))
                sql += "  and subj      = " + StringManager.makeSQL(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += "  and subjseq      = " + StringManager.makeSQL(v_subjseq);
            sql += " group by date_year, date_month, date_time           ";
            sql += " order by date_time asc                              ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

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
        ListSet ls = null;
        ArrayList<CountData> list = null;
        String sql = "";
        CountData data = null;

        //String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getString("s_subjseq");
        String v_year = box.getStringDefault("p_date_year", FormatDate.getDate("yyyy"));
        String v_month = box.getStringDefault("p_date_week", FormatDate.getDate("MM"));

        if (v_gubun.equals(""))
            v_gubun = "N000001";
        if (v_subj.equals(""))
            v_subj = "ALL";
        if (v_subjseq.equals(""))
            v_subjseq = "ALL";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<CountData>();

            sql = "select date_year, date_month, date_week, sum(cnt) cnt ";
            sql += " from TZ_SUBJCOUNT                                   ";
            sql += " where date_year   = " + StringManager.makeSQL(v_year);
            sql += "   and date_month  = " + StringManager.makeSQL(v_month);
            if (!v_gubun.equals("----"))
                sql += "  and gubun      = " + StringManager.makeSQL(v_gubun);
            if (!v_subj.equals("ALL"))
                sql += "  and subj      = " + StringManager.makeSQL(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += "  and subjseq      = " + StringManager.makeSQL(v_subjseq);
            sql += " group by date_year, date_month, date_week     ";
            sql += " order by date_week asc                               ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CountData();

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

    /**
     * 과정, 개인별 접속 로그 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 접속 로그 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectCountLog(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_subj = box.getStringDefault("p_subj", "ALL");
        String v_year = box.getStringDefault("p_year", FormatDate.getDate("yyyy"));
        String v_subjseq = box.getStringDefault("p_subjseq", "ALL");
        String v_userid = box.getStringDefault("p_userid", "ALL");

        try {
            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            sql = "select subj, year, subjseq, userid, lgip, ldate ";
            sql += " from TZ_SUBJLOGINID                           ";
            sql += " where year   = " + StringManager.makeSQL(v_year);
            if (!v_subj.equals("ALL"))
                sql += " and subj    = " + StringManager.makeSQL(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += " and subjseq = " + StringManager.makeSQL(v_subjseq);
            if (!v_userid.equals("ALL"))
                sql += " and userid  = " + StringManager.makeSQL(v_userid);
            sql += " order by ldate desc                           ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
     * 과정, 개인별 목차 로그 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 목차 로그 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectProgressLog(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_subj = box.getStringDefault("p_subj", "");
        String v_year = box.getStringDefault("p_year", "");
        String v_subjseq = box.getStringDefault("p_subjseq", "");
        String v_userid = box.getStringDefault("p_userid", "");

        try {
            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            sql = "SELECT h.*, l.* ";
            sql += " FROM tz_progress_history h ";
            sql += "     LEFT OUTER JOIN tz_subjlesson l ";
            sql += "     ON h.subj=l.subj AND h.lesson=l.lesson ";
            sql += " WHERE h.subj='" + v_subj + "' AND h.year='" + v_year + "' AND h.subjseq='" + v_subjseq + "' AND h.userid='" + v_userid + "' ";
            sql += " ORDER BY h.first_edu DESC ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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