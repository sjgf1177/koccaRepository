//**********************************************************
//1. 제      목: 접속통계
//2. 프로그램명: CountBean.java
//3. 개      요: 접속통계
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 
//7. 수      정:
//**********************************************************

package com.credu.statistics;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class UserCountBean {

    // private static final String CONFIG_NAME = null;

    public UserCountBean() {
    }

    /**
     * 시간별 회원등록 현황 리스트 New
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectHourStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_year = box.getString("param"); //년도
        String ss_month = box.getString("param1"); //월
        String ss_category = box.getString("param2"); //분류

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select date_year, date_month, sum(cnt) tot,sum(decode(date_time,'01',cnt,'02',cnt,0)) first, sum(decode(date_time,'03',cnt,'04',cnt,0)) second, ";
            sql += " sum(decode(date_time,'05',cnt,'06',cnt,0)) third, sum(decode(date_time,'07',cnt,'08',cnt,0)) fourth, ";
            sql += " sum(decode(date_time,'09',cnt,'10',cnt,0)) fifth, sum(decode(date_time,'11',cnt,'12',cnt,0)) sixth, ";
            sql += " sum(decode(date_time,'13',cnt,'14',cnt,0)) seventh, sum(decode(date_time,'15',cnt,'16',cnt,0)) eightth, ";
            sql += " sum(decode(date_time,'17',cnt,'18',cnt,0)) nineth, sum(decode(date_time,'19',cnt,'20',cnt,0)) tenth, ";
            sql += " sum(decode(date_time,'21',cnt,'22',cnt,0)) eleventh, sum(decode(date_time,'23',cnt,'24',cnt,0)) twelveth ";
            sql += " from TZ_HOMECOUNT                                       ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            sql += " where  (";
            while (st.hasMoreElements()) {
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(ss_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " date_year||date_month ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!ss_category.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_category, ",");
                sql += " and  gubun in (select grcode from tz_grcode where ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " gubun = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }

            sql += " group by date_year, date_month     ";
            sql += " order by date_year, date_month asc                               ";

            System.out.println(sql);
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
     * 요일별 회원등록 현황 리스트 New
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectWeekStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_year = box.getString("param"); //년도
        String ss_month = box.getString("param1"); //월
        String ss_category = box.getString("param2"); //분류

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select date_year, date_month, sum(cnt) tot,sum(decode(date_week,'1',cnt,0)) sun, sum(decode(date_week,'2',cnt,0)) mon, ";
            sql += " sum(decode(date_week,'3',cnt,0)) tue, sum(decode(date_week,'4',cnt,0)) wed, sum(decode(date_week,'5',cnt,0)) thi,  ";
            sql += " sum(decode(date_week,'6',cnt,0)) fri, sum(decode(date_week,'7',cnt,0)) sat, sum(cnt) cnt ";
            sql += " from TZ_HOMECOUNT                                   ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            sql += " where  (";
            while (st.hasMoreElements()) {
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(ss_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " date_year||date_month ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!ss_category.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_category, ",");
                sql += " and  gubun in (select grcode from tz_grcode where ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " gubun = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }

            sql += " group by date_year, date_month     ";
            sql += " order by date_year, date_month asc                               ";

            System.out.println(sql);
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
     * 지역별 회원등록 현황 리스트 New
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectLocationStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_year = box.getString("param"); //년도
        String ss_month = box.getString("param1"); //월
        String ss_category = box.getString("param2"); //분류

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select substr(indate,1,4) inyear, substr(indate,1,6) indate, count(userid) usercnt, ";
            //sql = "  select substr(indate,1,4) inyear,  count(userid) usercnt, ";		
            sql += " sum(decode(substr(addr,1,2),'서울',1,0)) seoul, ";
            sql += " sum(decode(substr(addr,1,2),'경기',1,0)) kyunggi, ";
            sql += " sum(decode(substr(addr,1,2),'부산',1,0)) busan, ";
            sql += " sum(decode(substr(addr,1,2),'인천',1,0)) incheon, ";
            sql += " sum(decode(substr(addr,1,2),'대구',1,0)) daegu, ";
            sql += " sum(decode(substr(addr,1,2),'대전',1,0)) daejeon, ";
            sql += " sum(decode(substr(addr,1,2),'광주',1,0)) kwangju, ";
            sql += " sum(decode(substr(addr,1,2),'전북',1,0)) jeonbook, ";
            sql += " sum(decode(substr(addr,1,2),'전남',1,0)) jeonnam, ";
            sql += " sum(decode(substr(addr,1,2),'충북',1,0)) chungbook, ";
            sql += " sum(decode(substr(addr,1,2),'충남',1,0)) choongnam, ";
            sql += " sum(decode(substr(addr,1,2),'강원',1,0)) gangwon, ";
            sql += " sum(decode(substr(addr,1,2),'경북',1,0)) gyungbook, ";
            sql += " sum(decode(substr(addr,1,2),'경남',1,0)) gyungnam, ";
            sql += " sum(decode(substr(addr,1,2),'울산',1,0)) woolsan, ";
            sql += " sum(decode(substr(addr,1,2),'제주',1,0)) jejoo, ";
            sql += " sum(decode(substr(addr,1,2),'서울',0,'경기',0,'부산',0,'인천',0,'대구',0 ,'대전',0,'광주',0,'전북',0,'전남',0,'충북',0,'충남',0,'강원',0,'경북',0,'경남',0,'울산',0,'제주',0,1)) etc ";

            sql += " from tz_member   ";
            sql += "  where indate is not null  ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(ss_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");
                        System.out.println("i=" + i + "st.nextToken()=" + tk1 + "     st1.nextToken()=" + tk2);

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " substr(indate,1,6) ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!ss_category.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_category, ",");
                sql += " and  grcode in (select grcode from tz_grcode where ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " gubun = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }

            sql += "  group by substr(indate,1,4), substr(indate,1,6) ";
            //sql += "  group by substr(indate,1,4) ";
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
     * 직업별 회원등록 현황 리스트 New
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectVocationStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_year = box.getString("param"); //년도
        String ss_month = box.getString("param1"); //월
        String ss_category = box.getString("param2"); //분류

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select substr(indate,1,4) inyear, substr(indate,1,6) indate, count(userid) usercnt, ";
            //sql = "  select substr(indate,1,4) inyear, count(userid) usercnt, ";		
            sql += " sum(decode(jikup,'01',1,0)) high, ";
            sql += " sum(decode(jikup,'02',1,0)) univ, ";
            sql += " sum(decode(jikup,'03',1,0)) country, ";
            sql += " sum(decode(jikup,'04',1,0)) gonggong, ";
            sql += " sum(decode(jikup,'05',1,0)) freelancer, ";
            sql += " sum(decode(jikup,'06',1,0)) preoffice, ";
            sql += " sum(decode(jikup,'07',1,0)) office, ";
            sql += " sum(decode(jikup,'08',1,0)) jubu, ";
            sql += " sum(decode(jikup,'09',1,0)) miltiary, ";
            sql += " sum(decode(jikup,'10',1,0)) univoffice, ";
            sql += " sum(decode(jikup,'11',1,0)) hospital, ";
            sql += " sum(decode(jikup,'12',1,0)) lawyer, ";
            sql += " sum(decode(jikup,'13',1,0)) broad, ";
            sql += " sum(decode(jikup,'14',1,0)) jongkyo, ";
            sql += " sum(decode(jikup,'15',1,0)) entertain, ";
            sql += " sum(decode(jikup,'16',1,0)) sports, ";
            sql += " sum(decode(jikup,'17',1,0)) proffesor, ";
            sql += " sum(decode(jikup,'18',1,0)) lecter, ";
            sql += " sum(decode(jikup,'19',1,0)) owner, ";
            sql += " sum(decode(jikup,'99',1,0)) etc, ";
            sql += " sum(decode(jikup,'01',0,'02',0,'03',0,'04',0,'05',0,'06',0,'07',0,'08',0,'09',0,'10',0,'11',0,'12',0,'13',0,'14',0,'15',0,'16',0,'17',0,'18',0,'19',0,'99',0,1)) johndoe ";

            sql += " from tz_member   ";
            sql += "  where indate is not null  ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(ss_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");
                        System.out.println("i=" + i + "st.nextToken()=" + tk1 + "     st1.nextToken()=" + tk2);

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " substr(indate,1,6) ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!ss_category.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_category, ",");
                sql += " and  grcode in (select grcode from tz_grcode where ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " gubun = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }

            sql += "  group by substr(indate,1,4), substr(indate,1,6) ";
            //sql += "  group by substr(indate,1,4)";
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
     * 연령별 회원등록 현황 리스트 New
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectAgeStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_year = box.getString("param"); //년도
        String ss_month = box.getString("param1"); //월
        String ss_category = box.getString("param2"); //분류

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select substr(indate,1,4) inyear, substr(indate,1,6) indate, count(userid) usercnt, ";
            //sql = "  select substr(indate,1,4) inyear, count(userid) usercnt, ";	
            sql += " sum(CASE WHEN memberyear BETWEEN '2004' AND '2013' THEN 1 END) toddler, ";
            sql += " sum(CASE WHEN memberyear BETWEEN '1994' AND '2003' THEN 1 END) teens, ";
            sql += " sum(CASE WHEN memberyear BETWEEN '1984' AND '1993' THEN 1 END) twenty, ";
            sql += " sum(CASE WHEN memberyear BETWEEN '1974' AND '1983' THEN 1 END) thirty, ";
            sql += " sum(CASE WHEN memberyear BETWEEN '1964' AND '1973' THEN 1 END) fourty,";
            sql += " sum(CASE WHEN memberyear BETWEEN '1954' AND '1963' THEN 1 END) fifty, ";
            sql += " sum(CASE WHEN memberyear BETWEEN '1944' AND '1953' THEN 1 END) sixty, ";
            sql += " sum(CASE WHEN memberyear BETWEEN '1900' AND '1943' THEN 1 END) seventy, ";
            sql += " sum (decode(memberyear,NULL,1)) johndoe ";
            sql += " from tz_member   ";
            sql += "  where indate is not null  ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(ss_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");
                        System.out.println("i=" + i + "st.nextToken()=" + tk1 + "     st1.nextToken()=" + tk2);

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " substr(indate,1,6) ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!ss_category.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_category, ",");
                sql += " and  grcode in (select grcode from tz_grcode where ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " gubun = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }
            //sql += "  group by substr(indate,1,4) ";
            sql += "  group by substr(indate,1,4), substr(indate,1,6) ";
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
     * 월별 회원등록 현황 리스트 New
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectMonthStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_year = box.getString("param"); //년도
        String ss_month = box.getString("param1"); //월
        String ss_category = box.getString("param2"); //분류

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select substr(indate,1,4) inyear, substr(indate,1,6) indate, count(userid) usercnt, ";
            sql += " sum(decode(sex,'1',1,0)) man, sum(decode(sex,'2',1,0)) woman, ";
            sql += " sum(decode(sex,'1',0,'2',0,1)) johndoe ";
            sql += " from tz_member   ";
            sql += "  where indate is not null  ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(ss_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");
                        System.out.println("i=" + i + "st.nextToken()=" + tk1 + "     st1.nextToken()=" + tk2);

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " substr(indate,1,6) ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!ss_category.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_category, ",");
                sql += " and  grcode in (select grcode from tz_grcode where ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " gubun = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }

            sql += "  group by substr(indate,1,4), substr(indate,1,6) ";
            sql += "  order by indate ";
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
     * 년도별 회원등록 현황 리스트 New
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectYearStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String ss_year = box.getString("param"); //년도
        String ss_category = box.getString("param2"); //분류

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.statistices.UserCountBean SelectYearStat (년도별 회원 가입 현화 조회) */  \n");
            sql.append("SELECT  SUBSTR(INDATE,1,4) AS INDATE    \n");
            sql.append("    ,   COUNT(USERID) AS USERCNT        \n");
            sql.append("    ,   SUM(DECODE(SEX, '1', 1, 0)) AS MAN  \n");
            sql.append("    ,   SUM(DECODE(SEX, '2', 1, 0)) AS WOMAN    \n");
            sql.append("    ,   SUM(DECODE(SEX, '1', 0, '2', 0, 1)) AS JOHNDOE  \n");
            sql.append("  FROM  TZ_MEMBER   \n");
            sql.append(" WHERE  INDATE IS NOT NULL  \n");

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_year, ",");

            String temp = "";
            // String tmp1 = "";
            // String tmp2 = "";
            // String tmp3 = "";
            // String tmp4 = "";

            sql.append("   AND ( ");
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                if (i != 0) {
                    sql.append(" OR ");
                }
                sql.append(" SUBSTR(INDATE, 1, 4) = '").append(temp).append("' ");
                i++;
            }
            sql.append("   )    \n");

            i = 0;
            if (!ss_category.equals("null")) {
                StringTokenizer st2 = new StringTokenizer(ss_category, ",");
                sql.append("   AND  GRCODE IN ( \n");
                sql.append("                    SELECT  GRCODE  \n");
                sql.append("                      FROM  TZ_GRCODE   \n");
                sql.append("                     WHERE ");
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0) {
                        sql.append(" OR");
                    }
                    sql.append(" GUBUN = '").append(temp).append("'   ");
                    i++;
                }
                sql.append("   )    \n");
            }

            sql.append(" GROUP BY SUBSTR(INDATE, 1, 4)  \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
     * 년도별 회원등록 현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectYearCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        //    String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        //	String v_gubun = box.getString("p_grtype");
        //	System.out.println(" v_gubun : "+v_gubun);
        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select substring(indate,1,4) indate, count(userid) usercnt, ";
            sql += " sum(case sex when '1' then 1 else 0 end) man, sum(case sex when '2' then 1 else 0 end) woman ";
            sql += " from tz_member   ";

            if (!v_sindate.equals("") || v_sindate.equals(""))
                sql += "  where substring(indate,1,4) between " + StringManager.makeSQL(v_sindate) + " and " + StringManager.makeSQL(v_eindate);
            sql += "  group by substring(indate,1,4) ";

            //		System.out.println("UserCountBean 년도별 리스트 :"+sql);
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
     * 월별 회원등록 현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectMonthCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        //    String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_gubun = box.getString("p_grtype");
        System.out.println(" v_gubun : " + v_gubun);
        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        //	String v_eindate  = box.getStringDefault("p_project_year_e",  FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select substring(indate,1,4) indate_y, substring(indate,5,2) indate_m, ";
            sql += "  		count(userid) usercnt, sum(case sex when '1' then 1 else 0 end) man, sum(case sex when '2' then 1 else 0 end) woman  ";
            sql += "		from tz_member  ";
            sql += "  where substring(indate,1,4) = " + StringManager.makeSQL(v_sindate);
            sql += "  group by substring(indate,1,4) , substring(indate,5,2)  ";

            //		System.out.println("UserCountBean 월별 리스트 :"+sql);
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
     * 지역별 회원등록 현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectLocationCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select addr, count(*) usercnt, ";
            sql += "  		sum(case sex when '1' then 1 else 0 end) man, sum(case sex when '2' then 1 else 0 end) woman  ";
            sql += "		from tz_member  ";
            sql += "  where length(addr) =2  ";
            if (!v_sindate.equals("") || v_sindate.equals("")) {
                sql += " and  substring(indate,1,4) between " + StringManager.makeSQL(v_sindate) + " and " + StringManager.makeSQL(v_eindate);
            }
            sql += "  group by addr order by count(*) desc ";

            //		System.out.println("UserCountBean 월별 리스트 :"+sql);
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
     * 연령별 회원등록 현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectAgeCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        // String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select " + " case " + "when memberyear between '2004' and '2012' then '0-10' " + "when memberyear between '1994' and '2003' then '10대' " + "when memberyear between '1984' and '1993' then '20대' "
                    + "when memberyear between '1974' and '1983' then '30대' " + "when memberyear between '1964' and '1973' then '40대'  " + "when memberyear between '1954' and '1963' then '50대'  " + "when memberyear between '1944' and '1953' then '60대'  "
                    + "when memberyear between '1900' and '1943' then '60대이상'  " + "else '미등록'  " + "end ages, count(*) usercnt, " + "sum(case sex when '1' then 1 else 0 end) man, sum(case sex when '2' then 1 else 0 end) woman " + "from tz_member     " +

                    "group by case  " + "when memberyear between '2004' and '2012' then '0-10' " + "when memberyear between '1994' and '2003' then '10대' " + "when memberyear between '1984' and '1993' then '20대' "
                    + "when memberyear between '1974' and '1983' then '30대' " + "when memberyear between '1964' and '1973' then '40대'  " + "when memberyear between '1954' and '1963' then '50대'  " + "when memberyear between '1944' and '1953' then '60대'  "
                    + "when memberyear between '1900' and '1943' then '60대이상'  " + "else '미등록' " + "end ";

            //		System.out.println("UserCountBean 월별 리스트 :"+sql);
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
     * 직업별 회원등록 현황 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 월별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectVocationCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        // String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select " + " case " + " when jikup='01' then '고등학생' " + " when jikup='02' then '대학생' " + " when jikup='03' then '공무원' " + " when jikup='04' then '공공기관' " + " when jikup='05' then '프리랜서' " + " when jikup='06' then '예비취업자' "
                    + " when jikup='07' then '회사원' " + " when jikup='08' then '주부' " + " when jikup='09' then '군인' " + " when jikup='10' then '교직원' " + " when jikup='11' then '의료인' " + " when jikup='12' then '법조인' " + " when jikup='13' then '언론인' "
                    + " when jikup='14' then '종교인' " + " when jikup='15' then '연예인' " + " when jikup='16' then '체육인' " + " when jikup='17' then '교수' " + " when jikup='18' then '강사' " + " when jikup='19' then '자영업' " + " when jikup='99' then '기타' " +

                    " else '미등록' " + "end vocation, count(*) usercnt, " + "sum(case sex when '1' then 1 else 0 end) man, sum(case sex when '2' then 1 else 0 end) woman " + "from tz_member     " +

                    "group by case  " + " when jikup='01' then '고등학생' " + " when jikup='02' then '대학생' " + " when jikup='03' then '공무원' " + " when jikup='04' then '공공기관' " + " when jikup='05' then '프리랜서' " + " when jikup='06' then '예비취업자' "
                    + " when jikup='07' then '회사원' " + " when jikup='08' then '주부' " + " when jikup='09' then '군인' " + " when jikup='10' then '교직원' " + " when jikup='11' then '의료인' " + " when jikup='12' then '법조인' " + " when jikup='13' then '언론인' "
                    + " when jikup='14' then '종교인' " + " when jikup='15' then '연예인' " + " when jikup='16' then '체육인' " + " when jikup='17' then '교수' " + " when jikup='18' then '강사' " + " when jikup='19' then '자영업' " + " when jikup='99' then '기타' "
                    + " else '미등록' " + " end " + " order by count(*) desc";

            System.out.println("UserCountBean 직업별 리스트 :" + sql);
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
     * 과정별 QNA
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectSubjQnaCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        //    String v_gubun = CodeConfigBean.getConfigValue(CONFIG_NAME);
        //	String v_gubun = box.getString("p_grtype");
        //	System.out.println(" v_gubun : "+v_gubun);
        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select b.subjnm,sum(decode(kind,'0',1)) as que, sum(decode(kind,'1',1)) as ans from tz_qna a, tz_subj b where a.subj=b.subj";
            sql += " and year between " + StringManager.makeSQL(v_sindate) + " and " + StringManager.makeSQL(v_eindate);
            sql += " group by b.subjnm, a.subj  order by count(*) desc";

            /*
             * 
             * if (!v_sindate.equals("") || v_sindate.equals("")) sql +=
             * "  where substring(indate,1,4) between " +
             * StringManager.makeSQL(v_sindate) +
             * " and "+StringManager.makeSQL(v_eindate); sql +=
             * "  group by substring(indate,1,4) ";
             */

            //		System.out.println("UserCountBean 년도별 리스트 :"+sql);
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
     * 묻고 답하기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectQnaCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select decode(categorycd,'A1','게임개발','B1','방송영상','C1','문화콘텐츠') as cate, ";
            sql += "  sum(decode(substring(indate,5,2),'01',1)) as jan,sum(decode(substring(indate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(indate,5,2),'03',1)) as mar , sum(decode(substring(indate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(indate,5,2),'05',1)) as may, sum(decode(substring(indate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(indate,5,2),'07',1)) as jul, sum(decode(substring(indate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(indate,5,2),'09',1)) as sep, sum(decode(substring(indate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(indate,5,2),'11',1)) as nov, sum(decode(substring(indate,5,2),'12',1)) as dec ";
            sql += "  from tz_homeqna where tabseq ='5' ";
            sql += " and substring(indate,1,4) between " + StringManager.makeSQL(v_sindate) + " and " + StringManager.makeSQL(v_eindate);
            sql += "  group by categorycd ";

            //		System.out.println("UserCountBean 년도별 리스트 :"+sql);
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
     * VOC답하기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectVocCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select  ";
            sql += "  sum(decode(substring(sdate,5,2),'01',1)) as jan,sum(decode(substring(sdate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(sdate,5,2),'03',1)) as mar , sum(decode(substring(sdate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(sdate,5,2),'05',1)) as may, sum(decode(substring(sdate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(sdate,5,2),'07',1)) as jul, sum(decode(substring(sdate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(sdate,5,2),'09',1)) as sep, sum(decode(substring(sdate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(sdate,5,2),'11',1)) as nov, sum(decode(substring(sdate,5,2),'12',1)) as dec ";
            sql += "  from tz_sangdam  ";
            sql += " where substring(sdate,1,4) between " + StringManager.makeSQL(v_sindate) + " and " + StringManager.makeSQL(v_eindate);

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
     * 1:1 문의 건수
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectOneCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select decode(categorycd,'A1','게임개발','B1','방송영상','C1','문화콘텐츠') as cate, ";
            sql += "  sum(decode(substring(indate,5,2),'01',1)) as jan,sum(decode(substring(indate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(indate,5,2),'03',1)) as mar , sum(decode(substring(indate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(indate,5,2),'05',1)) as may, sum(decode(substring(indate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(indate,5,2),'07',1)) as jul, sum(decode(substring(indate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(indate,5,2),'09',1)) as sep, sum(decode(substring(indate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(indate,5,2),'11',1)) as nov, sum(decode(substring(indate,5,2),'12',1)) as dec ";
            sql += "  from tz_homeqna where tabseq ='2232' and types = 0 ";
            sql += "  and substring(indate,1,4) between " + StringManager.makeSQL(v_sindate) + " and " + StringManager.makeSQL(v_eindate);
            sql += "  group by categorycd ";

            /*
             * if (!v_sindate.equals("") || v_sindate.equals("")) sql +=
             * "  where substring(indate,1,4) between " +
             * StringManager.makeSQL(v_sindate) +
             * " and "+StringManager.makeSQL(v_eindate); sql +=
             * "  group by substring(indate,1,4) ";
             */

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
     * 지식팩토리 등록 건수
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectFactoryCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("p_project_year_s", FormatDate.getDate("yyyy"));
        // String v_eindate = box.getStringDefault("p_project_year_e", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select decode(substr(categorycd,0,2),'CB','방송영상','CG','게임개발','CK','문화콘텐츠') as cate, ";
            sql += "  sum(decode(substring(indate,5,2),'01',1)) as jan,sum(decode(substring(indate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(indate,5,2),'03',1)) as mar , sum(decode(substring(indate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(indate,5,2),'05',1)) as may, sum(decode(substring(indate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(indate,5,2),'07',1)) as jul, sum(decode(substring(indate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(indate,5,2),'09',1)) as sep, sum(decode(substring(indate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(indate,5,2),'11',1)) as nov, sum(decode(substring(indate,5,2),'12',1)) as dec ";
            sql += "  from tz_homeqna where tabseq ='7' and types = 0 ";
            sql += " and substring(indate,1,4) = " + StringManager.makeSQL(v_sindate);
            sql += " group by substr(categorycd,0,2) ";

            /*
             * if (!v_sindate.equals("") || v_sindate.equals("")) sql +=
             * "  where substring(indate,1,4) between " +
             * StringManager.makeSQL(v_sindate) +
             * " and "+StringManager.makeSQL(v_eindate); sql +=
             * "  group by substring(indate,1,4) ";
             */

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
     * 1:1 문의 건수
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> Select1to1Stat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("param", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            int i = 0;

            sql = " select gubun2, count(*) tot,";
            sql += "  sum(decode(substring(indate,5,2),'01',1)) as jan,sum(decode(substring(indate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(indate,5,2),'03',1)) as mar , sum(decode(substring(indate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(indate,5,2),'05',1)) as may, sum(decode(substring(indate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(indate,5,2),'07',1)) as jul, sum(decode(substring(indate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(indate,5,2),'09',1)) as sep, sum(decode(substring(indate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(indate,5,2),'11',1)) as nov, sum(decode(substring(indate,5,2),'12',1)) as dec ";
            sql += "  from tz_homeqna where tabseq ='2232' and types = 0 and (";

            i = 0;
            StringTokenizer st2 = new StringTokenizer(v_sindate, ",");

            while (st2.hasMoreTokens()) {
                String temp = st2.nextToken();
                if (i != 0)
                    sql += " or";
                sql += " substring(indate,1,4) = '" + temp + "'";
                i++;
            }
            //sql += "  and substring(indate,1,4) in ( " + StringManager.makeSQL(v_sindate) ;
            sql += " )  group by gubun2 ";
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
     * 홈페이지 묻고 답하기 new
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectQnaStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("param", FormatDate.getDate("yyyy"));
        //String v_month    = box.getString("param1");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select gubun2, count(*) tot,";
            sql += "  sum(decode(substring(indate,5,2),'01',1)) as jan,sum(decode(substring(indate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(indate,5,2),'03',1)) as mar , sum(decode(substring(indate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(indate,5,2),'05',1)) as may, sum(decode(substring(indate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(indate,5,2),'07',1)) as jul, sum(decode(substring(indate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(indate,5,2),'09',1)) as sep, sum(decode(substring(indate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(indate,5,2),'11',1)) as nov, sum(decode(substring(indate,5,2),'12',1)) as dec ";
            sql += "  from tz_homeqna where tabseq ='5' and types = 0 and ( ";

            int i = 0;
            StringTokenizer st2 = new StringTokenizer(v_sindate, ",");

            while (st2.hasMoreTokens()) {
                String temp = st2.nextToken();
                if (i != 0)
                    sql += " or";
                sql += " substring(indate,1,4) = '" + temp + "'";
                i++;
            }
            sql += " )  group by gubun2 ";

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
     * 과정 묻고 답하기 new
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectSubjQnaStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("param", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            /*
             * sql =
             * "select b.subjnm,sum(decode(kind,'0',1)) as que, sum(decode(kind,'1',1)) as ans from tz_qna a, tz_subj b where a.subj=b.subj"
             * ; sql += " and year between " + StringManager.makeSQL(v_sindate)
             * + " and "+StringManager.makeSQL(v_eindate); sql +=
             * " group by b.subjnm, a.subj  order by count(*) desc";
             */

            sql = " select gubun2, count(*) tot,";
            sql += "  sum(decode(substring(indate,5,2),'01',1)) as jan,sum(decode(substring(indate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(indate,5,2),'03',1)) as mar , sum(decode(substring(indate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(indate,5,2),'05',1)) as may, sum(decode(substring(indate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(indate,5,2),'07',1)) as jul, sum(decode(substring(indate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(indate,5,2),'09',1)) as sep, sum(decode(substring(indate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(indate,5,2),'11',1)) as nov, sum(decode(substring(indate,5,2),'12',1)) as dec ";
            sql += "  from tz_qna where kind = '0' and ( ";

            int i = 0;
            StringTokenizer st2 = new StringTokenizer(v_sindate, ",");

            while (st2.hasMoreTokens()) {
                String temp = st2.nextToken();
                if (i != 0)
                    sql += " or";
                sql += " year = '" + temp + "'";
                i++;
            }
            sql += " )  group by gubun2 ";

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
     * 과정별 QNA
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectSubjQnaList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_year = box.getStringDefault("param", FormatDate.getDate("yyyy"));
        String v_month = box.getString("param1");
        String v_part = box.getString("param2");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "SELECT   decode(b.area,'K0','문화','B0','방송','G0','게임') as area ,a.subj,b.subjnm, SUM (DECODE (kind, '0', 1)) AS que," + " SUM (DECODE (kind, '1', 1)) AS ans, c.name, c.userid " + " FROM tz_qna a, tz_subj b, tz_member c "
                    + "  WHERE a.subj = b.subj and b.tutor = c.userid(+) AND ( ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(v_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            while (st.hasMoreTokens()) {
                //String temp = st.nextToken();
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(v_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " substr(a.indate,1,6) ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!v_part.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(v_part, ",");
                sql += " and  ( ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " b.area = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }

            sql += " group by b.subjnm, a.subj, c.name,c.userid, b.area order by count(*) desc";

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
     * 과정별 QNA
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectSubjQnaBbsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        StringBuffer body_sql = new StringBuffer();
        DataBox dbox = null;

        String v_year = box.getStringDefault("param", FormatDate.getDate("yyyy"));
        String v_month = box.getString("param1");
        String v_subj = box.getString("param2");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += " SELECT      A.TITLE, A.GRCODE, B.NAME, A.ISOPEN, A.INUSERID, A.SUBJSEQ, A.LDATE, A.SUBJ, A.YEAR, A.SEQ     \n";
            head_sql += "             , C.SCSUBJNM, A.CATEGORYCD, A.INDATE, B.NAME, C.GRCODE, C.SCSUBJ, C.SUBJSEQGR, D.REPLYSTATE, A.OKYN1   \n";
            body_sql.append(" FROM        (                                                                     \n");
            body_sql.append("               SELECT * FROM TZ_QNA WHERE KIND = '0'                               \n");
            body_sql.append("             ) A                                                                   \n");
            body_sql.append("             , TZ_MEMBER B, VZ_SCSUBJSEQ C                                         \n");
            body_sql.append("             , (                                                                   \n");
            body_sql.append("               SELECT    SUBJ, YEAR, SUBJSEQ, LESSON, SEQ, COUNT(*) REPLYSTATE     \n");
            body_sql.append("                 FROM    TZ_QNA                                                    \n");
            body_sql.append("                WHERE    KIND > '0'                                                \n");
            body_sql.append("               GROUP BY  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ                          \n");
            body_sql.append("             ) D                                                                   \n");
            body_sql.append(" WHERE       A.INUSERID  = B.USERID(+)                                             \n");
            body_sql.append("   AND       A.SUBJ      = C.SUBJ(+)                                               \n");
            body_sql.append("   AND       A.YEAR      = C.YEAR(+)                                               \n");
            body_sql.append("   AND       A.SUBJSEQ   = C.SUBJSEQ(+)                                            \n");
            body_sql.append("   AND       A.SUBJ      = D.SUBJ(+)                                               \n");
            body_sql.append("   AND       A.YEAR      = D.YEAR(+)                                               \n");
            body_sql.append("   AND       A.SUBJSEQ   = D.SUBJSEQ(+)                                            \n");
            body_sql.append("   AND       A.LESSON    = D.LESSON(+)                                             \n");
            body_sql.append("   AND       A.SEQ       = D.SEQ(+)  AND (                                          \n");

            int i = 0;
            StringTokenizer st = new StringTokenizer(v_year, ",");
            String tk1 = "";
            String tk2 = "";
            // String temp = "";
            String tmpOutput = "";

            while (st.hasMoreTokens()) {
                //String temp = st.nextToken();
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(v_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            body_sql.append(" or");
                        body_sql.append(" substr(a.indate,1,6) ='" + tmpOutput + "'");
                        i++;
                    }
                }
            }

            body_sql.append(")");
            body_sql.append("  AND A.SUBJ ='" + v_subj + "'");

            sql = head_sql + body_sql.toString();
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
     * 과정별 QNA 튜터 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectSubjQnaTutorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        // String head_sql = "";
        // StringBuffer body_sql = new StringBuffer();
        DataBox dbox = null;

        // String v_sindate = box.getStringDefault("param", FormatDate.getDate("yyyy"));
        String v_userid = box.getString("param2");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select a.subj,b.subjnm,count(*) cnt from tz_qna a, tz_subj b  ";
            sql += " where a.inuserid ='" + v_userid + "' and year in ('2010','2011','2012','2013') and kind ='1' and a.subj =b.subj ";
            sql += " group by a.subj, b.subjnm ";

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
     * 지식팩토리 통계 new
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectKnowledgeStat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_sindate = box.getStringDefault("param", FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select gubun2, count(*) tot,";
            sql += "  sum(decode(substring(indate,5,2),'01',1)) as jan,sum(decode(substring(indate,5,2),'02',1)) as feb, ";
            sql += "  sum(decode(substring(indate,5,2),'03',1)) as mar , sum(decode(substring(indate,5,2),'04',1)) as apr, ";
            sql += "  sum(decode(substring(indate,5,2),'05',1)) as may, sum(decode(substring(indate,5,2),'06',1)) as jun, ";
            sql += "  sum(decode(substring(indate,5,2),'07',1)) as jul, sum(decode(substring(indate,5,2),'08',1)) as aug,";
            sql += "  sum(decode(substring(indate,5,2),'09',1)) as sep, sum(decode(substring(indate,5,2),'10',1)) as oct, ";
            sql += "  sum(decode(substring(indate,5,2),'11',1)) as nov, sum(decode(substring(indate,5,2),'12',1)) as dec ";
            sql += "  from tz_homeqna where tabseq ='7' and types = 0 and ( ";

            int i = 0;
            StringTokenizer st2 = new StringTokenizer(v_sindate, ",");

            while (st2.hasMoreTokens()) {
                String temp = st2.nextToken();
                if (i != 0)
                    sql += " or";
                sql += " substring(indate,1,4) = '" + temp + "'";
                i++;
            }
            sql += " )  group by gubun2 ";

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
     * 지식팩토리 QNA
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectKnowledgeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_year = box.getStringDefault("param", FormatDate.getDate("yyyy"));
        String v_month = box.getString("param1");
        String v_part = box.getString("param2");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "SELECT   decode(b.area,'K0','문화','B0','방송','G0','게임') as area ,a.categorycd,b.subjnm, SUM (DECODE (types, '0', 1)) AS que," + "  c.name, c.userid " + " FROM tz_homeqna a, tz_subj b, tz_member c "
                    + "  WHERE a.tabseq ='7' and a.categorycd = b.subj and b.tutor = c.userid(+) AND ( ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(v_year, ",");
            String tk1 = "";
            String tk2 = "";
            String temp = "";
            String tmpOutput = "";

            while (st.hasMoreTokens()) {
                //String temp = st.nextToken();
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(v_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            sql += " or";
                        sql += " substr(a.indate,1,6) ='" + tmpOutput + "'";
                        i++;
                    }
                }
            }

            sql += " ) ";

            if (!v_part.equals("null")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(v_part, ",");
                sql += " and  ( ";
                while (st2.hasMoreTokens()) {
                    temp = st2.nextToken();
                    if (i != 0)
                        sql += " or";
                    sql += " b.area = '" + temp + "'";
                    i++;
                }
                sql += " )";
            }

            sql += " group by b.subjnm, a.categorycd, c.name,c.userid, b.area order by count(*) desc";

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
     * 지식팩토리 게시물 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 년도별 회원등록 현황 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> SelectKnowledgeBbsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        StringBuffer body_sql = new StringBuffer();
        DataBox dbox = null;

        String v_year = box.getStringDefault("param", FormatDate.getDate("yyyy"));
        String v_month = box.getString("param1");
        String v_subj = box.getString("param2");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += " SELECT      A.TITLE,  C.NAME,  A.INUSERID,  A.LDATE, A.CATEGORYCD, GUBUN2,     \n";
            head_sql += "  B.SUBJNM  \n";
            body_sql.append(" FROM TZ_HOMEQNA a, TZ_SUBJ B, TZ_MEMBER C                                                     \n");
            body_sql.append(" WHERE       A.CATEGORYCD  = B.SUBJ  AND A.INUSERID = C.USERID                                           \n");
            body_sql.append("   AND       A.TABSEQ ='7' AND A.TYPES=0  AND (                                          \n");

            int i = 0;
            StringTokenizer st = new StringTokenizer(v_year, ",");
            String tk1 = "";
            String tk2 = "";
            // String temp = "";
            String tmpOutput = "";

            while (st.hasMoreTokens()) {
                //String temp = st.nextToken();
                tk1 = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(v_month, ",");
                if (st1.hasMoreTokens()) {
                    while (st1.hasMoreTokens()) {
                        tk2 = st1.nextToken();
                        tk2 = tk2.replaceAll("'", "");

                        tmpOutput = tk1 + tk2;
                        if (i != 0)
                            body_sql.append(" or");
                        body_sql.append(" substr(a.indate,1,6) ='" + tmpOutput + "'");
                        i++;
                    }
                }
            }

            body_sql.append(" ) AND A.CATEGORYCD ='" + v_subj + "'");

            sql = head_sql + body_sql.toString();
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