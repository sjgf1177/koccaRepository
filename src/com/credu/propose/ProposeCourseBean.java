//**********************************************************
//1. 제	  목: SUBJECT INFORMATION USER BEAN
//2. 프로그램명: ProposeCourseBean.java
//3. 개	  요: 과정안내 사용자 bean
//4. 환	  경: JDK 1.3
//5. 버	  젼: 1.0
//6. 작	  성: 2004.01.14
//7. 수	  정:
//**********************************************************
package com.credu.propose;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.TimeZone;

import com.credu.Bill.BillBean;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.system.SelectionData;

public class ProposeCourseBean {
    private int row;

    /**
     * 오프라인 신청한 과정리스트 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList 과정리스트
     */
    public static ArrayList<DataBox> OffLineApplySelectSubjectList(RequestBox box) throws Exception {
        //		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";
        //		String count_sql  = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        //		String v_seq = box.getString("p_seq");
        String v_userid = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += "Select c.seq, a.subjgubun, b.codenm as gubunNm , subj, subjseq, propStart, ";
            head_sql += "	propEnd, subjNm, tUserid, tName,  dday, startTime, endTime, place, tName, c.status\n";
            body_sql += " From TZ_OFFLINESUBJ a, tz_code b, tz_offlinepropose c\n";
            body_sql += " Where\n";
            body_sql += "	a.subjgubun = b.code and a.seq = c.seq";
            body_sql += "	and b.gubun = '0061' and a.grcode = 'N000002'\n";
            body_sql += "	and userid = '" + v_userid + "'\n";
            body_sql += "	and to_char(sysdate,'YYYYMMDDHH24') <= dday\n";
            body_sql += " Order By a.seq DESC";

            sql = head_sql + body_sql + group_sql + order_sql;
            System.out.println("sql = " + sql);
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
     * 오프라인 신청 가능한 과정리스트 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList 과정리스트
     */
    public static ArrayList<DataBox> OffLineSelectSubjectList(RequestBox box) throws Exception {
        //		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";
        //		String count_sql  = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        //		String v_seq = box.getString("p_seq");
        String v_userid = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += "Select a.seq, a.subjgubun, b.codenm as gubunNm , subj, subjseq, propStart, ";
            head_sql += "	propEnd, subjNm, tUserid, tName,  dday, startTime, endTime, place, tName\n";
            body_sql += " From TZ_OFFLINESUBJ a, tz_code b\n";
            body_sql += " Where\n";
            body_sql += "	a.subjgubun = b.code\n";
            body_sql += "	and b.gubun = '0061'\n";
            body_sql += "	and seq not in (Select p.seq From tz_offlinepropose p join TZ_OFFLINESUBJ s on p.seq = s.seq\n";
            body_sql += " Where userid = '" + v_userid + "' )\n";
            body_sql += "	and a.subjgubun = b.code and a.grcode = 'N000002'\n";
            body_sql += "	and to_char(sysdate,'YYYYMMDDHH24') between a.propStart and a.propEnd\n";
            body_sql += " Order By a.seq DESC";

            sql = head_sql + body_sql + group_sql + order_sql;
            System.out.println("sql = " + sql);
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

    public ProposeCourseBean() {
    }

    public int datediff(String stdt, String eddt) {
        int returnValue = 0;
        long temp = 0;

        int year = Integer.parseInt(stdt.substring(0, 4));
        int month = Integer.parseInt(stdt.substring(4, 6));
        int day = Integer.parseInt(stdt.substring(6, 8));

        int year1 = Integer.parseInt(eddt.substring(0, 4));
        int month1 = Integer.parseInt(eddt.substring(4, 6));
        int day1 = Integer.parseInt(eddt.substring(6, 8));

        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        Calendar cal = Calendar.getInstance(tz);

        cal.set((year - 1900), (month - 1), day);

        Calendar cal2 = Calendar.getInstance(tz);
        cal2.set((year1 - 1900), (month1 - 1), day1);

        java.util.Date temp1 = cal.getTime();

        java.util.Date temp2 = cal2.getTime();

        temp = temp2.getTime() - temp1.getTime();

        if ((temp % 10) < 5) {
            temp = temp - (temp % 10);
        } else {
            temp = temp + (10 - (temp % 10));
        }

        returnValue = (int) (temp / (1000 * 60 * 60 * 24));

        return returnValue;
    }

    /**
     * 대분류 SELECT (ALL 제외)
     *
     * @param box receive from the form object and session
     * @return ArrayList 대분류 리스트
     */
    public ArrayList<DataBox> getMainUpperClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        ConfigSet conf = new ConfigSet();

        try {
            //String isCourse = box.getStringDefault("isCourse", "N");		//	  코스가 있어야 하는지 여부

            //String v_indexclass  = conf.getProperty("index.main.defaultclass");
            String v_upperclass1 = conf.getProperty("main.cyber.upperclass1");
            String v_upperclass2 = conf.getProperty("main.cyber.upperclass2");
            String v_upperclass3 = conf.getProperty("main.cyber.upperclass3");

            //			String isCourse = "N";		//	  코스가 있어야 하는지 여부
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select upperclass, classname";
            sql += " from tz_subjatt";
            sql += " where\n";
            sql += "(upperclass = '" + v_upperclass1 + "' or upperclass = '" + v_upperclass2 + "' or upperclass = '" + v_upperclass3 + "')";
            //sql += "upperclass in("+v_indexclass+")";
            sql += " and middleclass = '000'";
            sql += " and lowerclass = '000'";
            sql += " order by upperclass";

            System.out.println("class_main=" + sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            //dbox = this.setAllSelectBox(ls);
            //list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
     * 대분류 SELECT (ALL 제외)
     *
     * @param box receive from the form object and session
     * @return ArrayList 대분류 리스트
     */
    public ArrayList<DataBox> getOnlyUpperClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        String v_usergubun = box.getSession("usergubun");
        //		String  v_isonoff	 = "";
        ConfigSet conf = new ConfigSet();

        //		if((v_usergubun.equals("RH")||v_usergubun.equals("RK")) && (!v_usergubun.equals(""))){
        //		  v_isonoff = box.getStringDefault("p_isonoff", "OFF");
        //		}else{
        //		  v_isonoff = box.getStringDefault("p_isonoff", "ON");
        //		}

        try {

            String v_upperclass1 = conf.getProperty("main.cyber.upperclass1");
            String v_upperclass2 = conf.getProperty("main.cyber.upperclass2");
            String v_upperclass3 = conf.getProperty("main.cyber.upperclass3");

            String isCourse = "N"; //	  코스가 있어야 하는지 여부
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select upperclass, classname";
            sql += " from tz_subjatt";
            sql += " where\n";

            if ((v_usergubun.equals("RH") || v_usergubun.equals("RK"))) {
                sql += " substring(upperclass,1,1) = 'R'";
            } else {
                sql += "(upperclass = '" + v_upperclass1 + "' or upperclass = '" + v_upperclass2 + "' or upperclass = '" + v_upperclass3 + "')";
            }
            //}else{
            //  sql += " substr(upperclass,0,1) != 'R'";
            //}

            sql += " and middleclass = '000'";
            sql += " and lowerclass = '000'";

            if (isCourse.equals("N")) { //	 코스분류도 없다
                sql += " and upperclass != 'COUR'";
            }

            sql += " order by upperclass";

            System.out.println(sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            //dbox = this.setAllSelectBox(ls);
            //list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
     * 차수정보상세보기
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox getSelectBill(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        String sql = "";
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        //			String  gyear	   = box.getString("gyear");
        //			String  v_course	= box.getString("p_course");
        //			String  v_isonoff   = box.getString("p_isonoff");

        try {
            connMgr = new DBConnectionManager();
            sql = "  Select s.subj, s.subjseq, s.[year], s.propstart, s.propend, s.edustart, s.eduend,\n";
            sql += "	s.subjnm, s.studentlimit, s.biyong, su.muserid  , m.name , s.bookprice\n";
            sql += "	, v.isbelongcourse, v.course, v.courseseq, v.subjcnt, v.coursenm\n";
            sql += " From tz_subjseq s\n";
            sql += "	join tz_subj su on su.subj = s.subj\n";
            sql += "	left outer join tz_member m on m.userid = su.muserid\n";
            sql += "	join vz_scsubjseq v on s.subj = v.subj and s.year = v.year and s.subjseq = v.subjseq\n";
            sql += "  where\n";
            sql += "	s.subj = " + SQLString.Format(v_subj) + "\n";
            sql += "	and s.subjseq = " + SQLString.Format(v_subjseq) + "\n";
            sql += "	and s.year = " + SQLString.Format(v_year) + "\n";

            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                dbox = ls1.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * 수강신청 목록
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    @SuppressWarnings("unchecked")
    public DataBox getSubjectEduProposeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = new DataBox("result");
        ListSet ls1 = null;
        ListSet ls2 = null;
        StringBuffer sql = new StringBuffer();
        box.sync("p_subj");
        box.sync("p_subjseq");
        box.sync("p_year");
        if (box.get("p_userid").length() == 0) {
            box.put("p_userid", box.getSession("userid"));
        }

        try {
            connMgr = new DBConnectionManager();
            /**
             * 기본정보
             */
            sql.append("select nvl(sum(getpoint - nvl(usepoint, 0)), 0) as getpoint   ");
            sql.append("from   tz_pointget   ");
            sql.append("where  userid = ':p_userid'");
            sql.append("and    to_char(sysdate, 'YYYYMMDD') <= nvl(expiredate,TO_CHAR(to_date(substr(getdate,1,8),'yyyymmdd') + 365,'yyyymmdd'))   ");
            ls1 = connMgr.executeQuery(sql.toString(), box);

            if (ls1.next()) {
                dbox.putAll(ls1.getDataBox());
            }
            /**
             * 신청과정목록
             */
            ls1.close();
            sql.delete(0, sql.length());
            sql.append(" SELECT\n");
            sql.append(" 	C.CLASSNAME, B.ISCHKFIRST, B.CHKFIRST, A.SUBJ, A.SUBJSEQ, A.YEAR,\n");
            sql.append(" 	A.SUBJNM, A.PROPSTART, A.PROPEND, A.EDUSTART, A.EDUEND, D.BIYONG OBIYONG, A.BIYONG\n");
            sql.append(" FROM TZ_SUBJSEQ A, TZ_PROPOSE B, TZ_SUBJATT C, TZ_SUBJ D\n");
            sql.append(" WHERE USERID = ':p_userid'\n");
            sql.append(" AND A.SUBJ = B.SUBJ\n");
            sql.append(" AND A.SUBJ = D.SUBJ\n");
            sql.append(" AND A.SUBJSEQ = B.SUBJSEQ\n");
            sql.append(" AND A.YEAR = B.YEAR\n");
            sql.append(" AND B.CHKFINAL = 'B'\n");
            sql.append(" AND B.APPDATE IS NULL\n");
            sql.append(" AND D.UPPERCLASS = C.UPPERCLASS\n");
            sql.append(" AND '000' = C.MIDDLECLASS\n");
            sql.append(" AND '000' = C.LOWERCLASS\n");
            sql.append(" AND SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24') ");

            ls1 = connMgr.executeQuery(sql.toString(), box);

            dbox.put("proposeResultList", ls1.getAllDataList());

            sql.setLength(0);

            /**
             * 할인율 정책 todo : TZ_DISCOUNT에서 읽어와서 값을 매핑해야 함.
             */

            boolean koccaYn = (box.getSession("KoccaYn")).equals("Y") ? true : false;

            String grcode = box.getSession("tem_grcode");

            int v_subjcnt = 0;
            String v_discount = null;

            sql.append(" SELECT NVL(SUBJCNT, '0') SUBJCNT, DISCOUNT FROM TZ_DISCOUNT ");
            sql.append(" WHERE GRCODE = " + SQLString.Format(grcode));

            ls1 = connMgr.executeQuery(sql.toString(), box);

            if (ls1.next()) {

                v_subjcnt = Integer.parseInt(ls1.getString("subjcnt"));
                v_discount = ls1.getString("discount");

                sql.setLength(0);

                if (koccaYn) { // 통합회원
                    // v_subjcnt이상 유료과정 수강했는지 확인
                    int pcnt = 0;

                    sql.append("SELECT COUNT(*) CNT FROM TZ_SUBJSEQ A,TZ_PROPOSE B\n");
                    sql.append("WHERE USERID = ':p_userid'\n");
                    sql.append("AND A.SUBJ = B.SUBJ\n");
                    sql.append("AND A.SUBJSEQ = B.SUBJSEQ\n");
                    sql.append("AND A.YEAR = B.YEAR\n");
                    sql.append("AND B.CANCELKIND IS NULL\n");
                    sql.append("AND B.APPDATE IS NOT NULL\n");
                    sql.append("AND A.BIYONG > 0\n");

                    ls2 = connMgr.executeQuery(sql.toString(), box);

                    if (ls2.next()) {
                        pcnt = ls2.getInt("cnt");

                        if (pcnt >= v_subjcnt) {
                            dbox.put("d_discount", v_discount);
                        }
                    } else {
                        dbox.put("d_discount", 0);
                    }

                    sql.setLength(0);

                    ls2.close();

                } else { // ASP회원
                    dbox.put("d_discount", v_discount);
                }

            } else {
                dbox.put("d_discount", 0);
            }

            ls1.close();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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

        return dbox;
    }

    /**
     * 수강신청 목록
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    @SuppressWarnings("unchecked")
    public DataBox getSubjectEduProposeCheckList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = new DataBox("result");
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        box.sync("p_subj");
        box.sync("p_subjseq");
        box.sync("p_year");
        if (box.get("p_userid").length() == 0)
            box.put("p_userid", box.getSession("userid"));

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            /**
             * 기본정보
             */
            sql.append("SELECT USERID, NAME, crypto.dec('normal',email) EMAIL, crypto.dec('normal',HANDPHONE)  HANDPHONE   ");
            sql.append("FROM TZ_MEMBER A   ");
            sql.append("where  userid = ':p_userid'");
            ls1 = connMgr.executeQuery(sql.toString(), box);

            if (ls1.next()) {
                dbox.putAll(ls1.getDataBox());

                if (box.getSession("tem_grcode").equals("N000001")) {
                    //====================================================
                    // 개인정보 복호화 - HTJ
                    /*
                     * SeedCipher seed = new SeedCipher(); if
                     * (!dbox.getString("d_email").equals(""))
                     * dbox.put("d_email"
                     * ,seed.decryptAsString(Base64.decode(dbox
                     * .getString("d_email")), seed.key.getBytes(), "UTF-8"));
                     * if (!dbox.getString("d_handphone").equals(""))
                     * dbox.put("d_handphone"
                     * ,seed.decryptAsString(Base64.decode(
                     * dbox.getString("d_handphone")), seed.key.getBytes(),
                     * "UTF-8"));
                     *
                     * EncryptUtil encryptUtil = new
                     * EncryptUtil(Constants.APP_KEY,Constants.APP_IV); if
                     * (!dbox.getString("d_email").equals(""))
                     * dbox.put("d_email",
                     * encryptUtil.decrypt(dbox.getString("d_email"))); if
                     * (!dbox.getString("d_handphone").equals(""))
                     * dbox.put("d_handphone",
                     * encryptUtil.decrypt(dbox.getString("d_handphone")));
                     */
                    //====================================================
                }
            }
            /**
             * 신청기간이 지난 신청 목록 초기화. 1. TZ_BILLING
             */
            ls1.close();
            sql.delete(0, sql.length());
            sql.append("DELETE TZ_BILLING A\n");
            sql.append("WHERE (SUBJ, YEAR, SUBJSEQ, USERID) IN ( SELECT SUBJ, YEAR, SUBJSEQ, USERID FROM VZ_EXPIREPROPOSE WHERE USERID=" + SQLString.Format(box.get("p_userid")) + ")\n");
            connMgr.executeUpdate(sql.toString());
            /*
             * 2. TZ_PROPOSE
             */
            sql.delete(0, sql.length());
            sql.append("DELETE TZ_PROPOSE A\n");
            sql.append("WHERE (SUBJ, YEAR, SUBJSEQ, USERID) IN ( SELECT SUBJ, YEAR, SUBJSEQ, USERID FROM VZ_EXPIREPROPOSE WHERE USERID=" + SQLString.Format(box.get("p_userid")) + ")\n");
            connMgr.executeUpdate(sql.toString());
            /**
             * 신청과정목록
             */
            ls1.close();
            sql.delete(0, sql.length());
            sql.append("SELECT\n");
            sql.append("	C.CLASSNAME, B.ISCHKFIRST, B.CHKFIRST, A.SUBJ, A.SUBJSEQ, A.YEAR,\n");
            sql.append("	D.SUBJNM, A.PROPSTART, A.PROPEND, A.EDUSTART, A.EDUEND, D.BIYONG OBIYONG, A.BIYONG\n");
            sql.append("FROM TZ_SUBJSEQ A, TZ_PROPOSE B, TZ_SUBJATT C, TZ_SUBJ D\n");
            sql.append("WHERE USERID = ':p_userid'\n");
            sql.append("AND A.SUBJ = B.SUBJ\n");
            sql.append("AND A.SUBJ = D.SUBJ\n");
            sql.append("AND A.SUBJSEQ = B.SUBJSEQ\n");
            sql.append("AND A.YEAR = B.YEAR\n");
            sql.append("AND B.CHKFINAL = 'B'\n");
            sql.append("AND B.APPDATE IS NULL\n");
            sql.append("AND D.UPPERCLASS = C.UPPERCLASS\n");
            sql.append("AND '000' = C.MIDDLECLASS\n");
            sql.append("AND '000' = C.LOWERCLASS\n");
            sql.append("AND SYSDATE BETWEEN TO_DATE(A.PROPSTART, 'YYYYMMDDHH24') AND TO_DATE(A.PROPEND, 'YYYYMMDDHH24')");
            ls1 = connMgr.executeQuery(sql.toString(), box);
            dbox.put("proposeResultList", ls1.getAllDataList());
            connMgr.commit();
            connMgr.setAutoCommit(true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * 팀장정보 리턴
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox getSelectChief(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        String sql = "";

        String v_comp = box.getSession("comp");

        try {
            connMgr = new DBConnectionManager();
            //sql = "  select deptmresno,deptmname,deptmjikwi from tz_comp\n";
            //sql+= "  where\n";
            sql = "  select email, userid, name, comp, cono from tz_member where  userid in (select deptmresno from tz_comp where comp = " + SQLString.Format(v_comp) + " )\n";
            System.out.println(sql);

            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                dbox = ls1.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * 팀장정보 리턴
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox getSelectChief(String userid) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        String sql = "";
        String v_comp = "";

        Hashtable<String, String> outputData = new Hashtable<String, String>();
        ProposeBean probean = new ProposeBean();

        try {
            connMgr = new DBConnectionManager();

            outputData = probean.getMeberInfo(userid);
            v_comp = (String) outputData.get("comp");

            //sql = "  select deptmresno,deptmname,deptmjikwi from tz_comp\n";
            //sql+= "  where\n";
            sql = "  select email, userid, name, comp, cono from tz_member where  userid in (select deptmresno from tz_comp where comp = " + SQLString.Format(v_comp) + " )\n";
            //System.out.println(sql);

            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                dbox = ls1.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * 맛보기 로그 등록
     *
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertPreviewLog(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        int v_seq = 0;

        String v_subj = box.getString("p_subj");

        String s_userid = box.getSession("userid");

        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = "select NVL(max(seq),0) from TZ_PREVIEW_LOG\n";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            sql1 = " insert into TZ_PREVIEW_LOG(seq, addate, subj, userid , luserid,ldate, grcode )\n";
            sql1 += " values (?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?)\n";

            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt(1, v_seq);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, s_userid);
            pstmt.setString(4, s_userid);
            pstmt.setString(5, tem_grcode);

            isOk = pstmt.executeUpdate();
            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
            //System.out.println("1111111111로그===>"+isOk);
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
        return isOk;
    }

    /**
     * 수강신청 1. 수강신청이 가능한 상태여야 한다. 2. 블랙리스트에 등록되어 있지 아니하여야 한다. 3. 수강신청 취소 후 재
     * 신청시.
     *
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int insertSubjectEduPropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        PreparedStatement pstmt7 = null;

        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        ListSet ls4 = null;

        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        String sql6 = "";
        String sql7 = "";
        String sql0 = "";

        int isOk = 0;
        int isOk2 = 0;
        int isOk5 = 0;

        String v_course = box.getString("p_course");
        String v_courseseq = box.getString("p_courseseq");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_payselect = box.getString("p_payselect");
        String v_grtype = box.getString("p_grtype");
        String v_inputname = box.getString("p_username");

        String v_biyong = box.getString("p_biyong");
        String v_bookprice = box.getString("p_bookprice");
        String v_bookyn = box.getString("p_bookyn");
        String v_realpay = box.getString("p_realpay");
        String v_inputdate = box.getString("p_inputdate");
        String v_usemileage = box.getString("p_usemileage");
        String v_iscourseYn = box.getString("p_iscourseYn");

        String v_transaction = box.getString("p_transaction");
        String v_authnumber = box.getString("p_authnumber");

        String v_user_id = box.getSession("userid");

        String v_chkfinal = "";
        String v_tmp_subj_value = "";
        String v_subj_value = "";
        String v_subjseq_value = "";
        String v_paystat = "W";
        String v_grcode = box.getSession("tem_grcode");
        String[] arr_sul_code = box.getStringArray("p_sul");
        String v_sul_code = "";
        String v_busi_name = box.getString("p_busiNm");
        String v_cate = box.getString("p_cate");
        String v_cate_txt = box.getString("p_cate_txt");

        if(arr_sul_code != null){
            for(int x = 0; x < arr_sul_code.length; x++){
                if(v_sul_code == ""){
                    v_sul_code = arr_sul_code[x];
                }else{
                    v_sul_code += "," + arr_sul_code[x];
                }
            }
        }

        v_chkfinal = "B";

        box.sync("p_militarystatus");
        box.sync("p_militarystart");
        box.sync("p_militaryend");
        box.sync("p_intro");
        box.sync("p_vision");
        box.sync("p_etc");
        box.sync("p_motive");
        box.sync("p_militarymemo");

        int i = 1;
        int v_jeyak = 0; // 제약조건 결과값
        int v_tmprnum = 0;
        int v_discount = box.getInt("p_discount");

        String v_jeyak_msg = "";
        String v_subjnm = "";
        String v_email = "";
        String v_phone = "";

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY,Constants.APP_IV);

            System.out.println("수강신청 시작 ");
            box.put("s_userid", box.getSession("userid"));
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql3 = "";
            /**
             * 1. 수강신청이 가능한가 체크. [1. 신청인원/모집인원, 2. 이미 신청하였는가, 3. 이미 수료한 것인가.]
             */
            v_jeyak = jeyakCheck(connMgr, v_subj, v_year, v_subjseq, v_user_id);

            System.out.println("v_jeyak : " + v_jeyak);

            if (v_jeyak < 0) {
                System.out.println("제약 결과 값이 0보다 작다");
                v_jeyak_msg = jeyakMsg(v_jeyak);
                box.put("err_msg", v_jeyak_msg);
                return v_jeyak;
            } else {
                System.out.println("제약 결과 값이 0보다 크다");
                /**
                 *2. 블랙리스트 등록여부 체크
                 */
                v_jeyak_msg = jeyakBlackcondition(connMgr, v_subj, v_year, v_subjseq, v_user_id);
                System.out.println("블랙리스트 등록여부 체크 결과 : " + v_jeyak_msg);

                if (!v_jeyak_msg.equals("")) {
                    System.out.println("블랙리스트 등록여부 체크 결과값이 공백이 아니다.");
                    v_jeyak = -7;
                    box.put("err_msg", v_jeyak_msg);
                    return v_jeyak;
                } else {
                    System.out.println("블랙리스트 등록여부 체크 결과값이 공백이다.");
                    if (v_iscourseYn.equals("Y")) {
                        System.out.println("v_iscourseYn 값이 Y일 경우.");
                        sql0 = " Select a.subj,a.subjseq, a.subjcnt,a.subjnm,crypto.dec('normal',b.EMAIL) EMAIL, crypto.dec('normal',b.HANDPHONE) HANDPHONE\n";
                        sql0 += " From VZ_SCSUBJSEQ a\n";
                        sql0 += " left join tz_member b on b.userid='" + v_user_id + "' and b.grcode='" + box.getSession("tem_grcode") + "'";
                        sql0 += " where\n";
                        sql0 += "	a.scsubj=" + SQLString.Format(v_course);
                        sql0 += "	and a.scyear=" + SQLString.Format(v_year);
                        sql0 += "	and a.scsubjseq=" + SQLString.Format(v_courseseq);
                    } else {
                        System.out.println("v_iscourseYn 값이 Y가 아닐 경우.");
                        sql0 = " Select a.subj,a.subjseq, a.subjcnt,a.subjnm, crypto.dec('normal',b.EMAIL) EMAIL, crypto.dec('normal',b.HANDPHONE) HANDPHONE\n";
                        sql0 += " From VZ_SCSUBJSEQ a\n";
                        sql0 += " left join tz_member b on b.userid='" + v_user_id + "' and b.grcode='" + box.getSession("tem_grcode") + "'";
                        sql0 += " where\n";
                        sql0 += "	a.subj		= " + SQLString.Format(v_subj);
                        sql0 += "	and a.year	= " + SQLString.Format(v_year);
                        sql0 += "	and a.subjseq = " + SQLString.Format(v_subjseq);
                    }
                    ls4 = connMgr.executeQuery(sql0);

                    while (ls4.next()) {
                        System.out.println("이런 미치 while문을 돌리면 어쩌자는 거여?");

                        v_subj_value = ls4.getString("subj");
                        v_subjseq_value = ls4.getString("subjseq");
                        v_subjnm = ls4.getString("subjnm");

                        v_email = ls4.getString("EMAIL");
                        v_phone = ls4.getString("HANDPHONE");

                        if (v_grcode.equals("N000001")) {
                            System.out.println("B2C 회원일 경우 아무것도 하지 않는다.");
                            //====================================================
                            // 개인정보 복호화 - HTJ
                            /*
                             * SeedCipher seed = new SeedCipher(); if
                             * (!v_email.equals("")) v_email =
                             * seed.decryptAsString(Base64.decode(v_email),
                             * seed.key.getBytes(), "UTF-8"); if
                             * (!v_phone.equals("")) v_phone =
                             * seed.decryptAsString(Base64.decode(v_phone),
                             * seed.key.getBytes(), "UTF-8");
                             *
                             *
                             * if (!v_email.equals("")) v_email =
                             * encryptUtil.decrypt(v_email); if
                             * (!v_phone.equals("")) v_phone =
                             * encryptUtil.decrypt(v_phone);
                             */
                            //====================================================
                        }

                        box.setSession("goodname", v_subjnm);
                        box.setSession("buyeremail", v_email);
                        box.setSession("buyertel", v_phone);

                        System.out.println("수강신청 테이블 조회시작");

                        sql2 = " select subj from TZ_PROPOSE\n";
                        sql2 += " where subj=" + SQLString.Format(v_subj_value) + " and year=" + SQLString.Format(v_year);
                        sql2 += " and subjseq=" + SQLString.Format(v_subjseq_value) + " and userid=" + SQLString.Format(v_user_id);
                        sql2 += " and asp_gubun=" + SQLString.Format(v_grcode);

                        ls2 = connMgr.executeQuery(sql2);
                        System.out.println("수강신청 테이블 조회 끝");

                        /**
                         * 3. 수강신청 취소 후 재 신청시.
                         */
                        if (ls2.next()) { //수강취소후 다시 수강신청한 경우
                            System.out.println("수강신청 테이블 조회 결과 기등록된 정보가 존재하면 updat 시작");
                            sql3 = " update TZ_PROPOSE set chkfirst='Y',chkfinal=?,cancelkind='',luserid=?,ldate=to_char(sysdate,'YYYYMMDDHH24MISS')\n";
                            sql3 += " where subj=? and year=? and subjseq=? and userid=? ";
                            pstmt3 = connMgr.prepareStatement(sql3);
                            //							pstmt3.setString(i++, v_chkfirst);
                            pstmt3.setString(i++, v_chkfinal);
                            pstmt3.setString(i++, v_user_id);
                            pstmt3.setString(i++, v_subj_value);
                            pstmt3.setString(i++, v_year);
                            pstmt3.setString(i++, v_subjseq_value);
                            pstmt3.setString(i++, v_user_id);

                            System.out.println("변수 v_chkfinal : " + v_chkfinal);
                            System.out.println("변수 v_user_id : " + v_user_id);
                            System.out.println("변수 v_subj_value : " + v_subj_value);
                            System.out.println("변수 v_year : " + v_year);
                            System.out.println("변수 v_subjseq_value : " + v_subjseq_value);

                            isOk = pstmt3.executeUpdate();
                        } else {//신규신청
                            System.out.println("수강신청 테이블 조회 결과 기등록된 정보가 존재하지 않으면 insert 시작");
                            sql3 = " insert into TZ_PROPOSE(subj, year, subjseq, userid, appdate, chkfirst, chkfinal, luserid, ldate,asp_gubun, asp_sul_cd, asp_business_name)\n";
                            sql3 += " values (?,?,?,?,'','Y',?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),?,?,?)";

                            pstmt3 = connMgr.prepareStatement(sql3);
                            pstmt3.setString(i++, v_subj_value);
                            pstmt3.setString(i++, v_year);
                            pstmt3.setString(i++, v_subjseq_value);
                            pstmt3.setString(i++, v_user_id);
                            //							pstmt3.setString(i++, v_chkfirst);
                            pstmt3.setString(i++, v_chkfinal);
                            pstmt3.setString(i++, v_user_id);
                            pstmt3.setString(i++, v_grcode);
                            pstmt3.setString(i++, v_sul_code);
                            pstmt3.setString(i++, v_busi_name);

                            System.out.println("변수 v_chkfinal : " + v_chkfinal);
                            System.out.println("변수 v_user_id : " + v_user_id);
                            System.out.println("변수 v_subj_value : " + v_subj_value);
                            System.out.println("변수 v_year : " + v_year);
                            System.out.println("변수 v_subjseq_value : " + v_subjseq_value);

                            isOk = pstmt3.executeUpdate();

                            if("N000210".equals(v_grcode)) {
                                sql7 = " update TZ_MEMBER set cate_field=?, cate_txt=? where userid=? and grcode=?";
                                pstmt7 = connMgr.prepareStatement(sql7);

                                pstmt7.setString(1, v_cate);
                                pstmt7.setString(2, v_cate_txt);
                                pstmt7.setString(3, v_user_id);
                                pstmt7.setString(4, v_grcode);

                                pstmt7.executeUpdate();
                            }
                        }
                    }

                    connMgr.commit();

                    if (v_biyong.equals("0")) { // 무료 과정 수강신청일때만 체크
                        System.out.println("무료과정 신청 할 때. 여긴 전부다 무료다");
                        BillBean bBean = new BillBean();
                        bBean.billStartZero(box);
                    }

                    System.out.println("DB Transaction commit");
                    connMgr.setAutoCommit(false);

                    /**
                     * 4. 결제정보 아님.. 결제 예상 정보임.. 취소명단 조회시 금액 확인용 외에 몇군대서만 사용.
                     */

                    System.out.println("결제 관련 테이블 조회 시작. 이건 아무 의미 없어 보인다.");

                    sql6 = " Select count(*) as bcount From tz_billing\n";
                    sql6 += " where subj=" + SQLString.Format(v_tmp_subj_value) + " and year=" + SQLString.Format(v_year);
                    sql6 += "	and subjseq=" + SQLString.Format(v_subjseq) + " and userid=" + SQLString.Format(v_user_id);
                    sql6 += "	and grtype=" + SQLString.Format(v_grtype);

                    ls3 = connMgr.executeQuery(sql6);
                    while (ls3.next()) {
                        v_tmprnum = ls3.getInt("bcount");
                    }

                    //빌링 테이블 입력.
                    if (!v_realpay.equals("")) { // 유료과정..
                        System.out.println("결제 관련 테이블 갱신 시작.");
                        if (v_tmprnum != 0) {
                            System.out.println("결제 관련 테이블 갱신 시작. 1111111");
                            sql4 = " Update TZ_BILLING	";
                            sql4 += "	Set subjprice=?, bookprice=?, bookyn=?, usepoint=?, discountrate=0,\n";
                            sql4 += "		paymoney=?, paytype=?, accountname=?, paydate=?, paystat=?,\n";
                            sql4 += "		cardno = ?,  CARDAPPROVALNO=?,";
                            sql4 += "		luserid=?, ldate=to_char(sysdate,'YYYYMMDDHH24MISS')\n";
                            sql4 += " Where subj=? and subjseq=? and year=? and userid=? and grtype=?";

                            pstmt4 = connMgr.prepareStatement(sql4);
                            pstmt4.setString(1, v_biyong);
                            pstmt4.setString(2, v_bookprice);
                            pstmt4.setString(3, v_bookyn);
                            pstmt4.setString(4, v_usemileage);
                            pstmt4.setString(5, v_realpay);
                            pstmt4.setString(6, v_payselect);
                            pstmt4.setString(7, v_inputname);
                            pstmt4.setString(8, v_inputdate);
                            pstmt4.setString(9, v_paystat);
                            // 카드관련
                            pstmt4.setString(10, v_transaction); //거래번호(데이콤에서 부여)
                            pstmt4.setString(11, v_authnumber); //승인번호(데이콤에서 부여)
                            // 카드관련
                            pstmt4.setString(12, v_user_id);
                            pstmt4.setString(13, v_tmp_subj_value);
                            pstmt4.setString(14, v_subjseq);
                            pstmt4.setString(15, v_year);
                            pstmt4.setString(16, v_user_id);
                            pstmt4.setString(17, v_grtype);

                            isOk2 = pstmt4.executeUpdate();
                            System.out.println("isOk2 = " + isOk2);
                        } else {
                            System.out.println("결제 관련 테이블 갱신 시작. 22222222");
                            if (v_payselect.equals("C")) {
                                System.out.println("결제 관련 테이블 갱신 시작. 33333333");
                                v_paystat = "W";
                            } else {
                                System.out.println("결제 관련 테이블 갱신 시작. 44444444444");
                                v_paystat = "Y";
                            }

                            sql4 = " Insert Into TZ_BILLING	";
                            sql4 += "	(subj, subjseq, year, userid, grtype, subjprice, bookprice, bookyn, usepoint, discountrate,\n";
                            sql4 += "	paymoney, paytype, accountname, paydate, PAYSTAT, cardno, CARDAPPROVALNO, luserid, ldate)\n";
                            sql4 += " Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,to_char(sysdate,'YYYYMMDDHH24MISS'))";

                            pstmt4 = connMgr.prepareStatement(sql4);
                            pstmt4.setString(1, v_tmp_subj_value);
                            pstmt4.setString(2, v_subjseq);
                            pstmt4.setString(3, v_year);
                            pstmt4.setString(4, v_user_id);
                            pstmt4.setString(5, v_grtype);
                            pstmt4.setString(6, v_biyong);
                            pstmt4.setString(7, v_bookprice);
                            pstmt4.setString(8, v_bookyn);
                            pstmt4.setString(9, v_usemileage);
                            pstmt4.setInt(10, v_discount);

                            pstmt4.setString(11, v_realpay);
                            pstmt4.setString(12, v_payselect);
                            pstmt4.setString(13, v_inputname);
                            pstmt4.setString(14, v_inputdate);
                            pstmt4.setString(15, v_paystat);
                            // 카드관련
                            pstmt4.setString(16, v_transaction); //거래번호(데이콤에서 부여)
                            pstmt4.setString(17, v_authnumber); //승인번호(데이콤에서 부여)
                            // 카드관련
                            pstmt4.setString(18, v_user_id);

                            isOk2 = pstmt4.executeUpdate();
                            System.out.println("isOk2 = " + isOk2);
                        }

                        if (!v_usemileage.equals("0")) {
                            System.out.println("결제 관련 테이블 갱신 시작. 55555555555");
                            v_usemileage = "-" + v_usemileage;

                            sql5 = " Insert into TZ_MEMBER_MILEAGE";
                            sql5 += " (userid, grtype, point, usememo,luserid, ldate)";
                            sql5 += " Values(?,?,?,'수강신청', ?, to_char(sysdate,'YYYYMMDDHH24MISS'))";

                            pstmt5 = connMgr.prepareStatement(sql5);
                            pstmt5.setString(1, v_user_id);
                            pstmt5.setString(2, v_grtype);
                            pstmt5.setString(3, v_usemileage);
                            pstmt5.setString(4, v_user_id);

                            isOk5 = pstmt5.executeUpdate();
                        } else {
                            System.out.println("결제 관련 테이블 갱신 시작. 6666666666666");
                            isOk5 = 1;
                        }
                    } else { // 무료과정일 경우
                        System.out.println("무료 과정");
                        isOk2 = 1;
                        isOk5 = 1;
                    }

                } // 블랙리스트 IF END
            } // 제약조건 IF END

            if (isOk > 0 && isOk2 > 0 && isOk5 > 0) {
                System.out.println("isOk > 0 && isOk2 > 0 && isOk5 > 0");
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (ls4 != null) {
                try {
                    ls4.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt5 != null) {
                try {
                    pstmt5.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk * isOk2 * isOk5;
    }

    @SuppressWarnings("unchecked")
    public int insertSpotSubjectEduPropose(RequestBox box) throws Exception {

    	DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        // int index = 1;
        int resultCnt = 0;

        Date today = new Date();
        SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = d1.format(today);

        String grcode = box.getSession("tem_grcode");
        String userid = box.getSession("userid");
        String year = box.getString("p_year");
        String subj = box.getString("p_subj");
        String subjseq = box.getString("p_subjseq");

    	try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.propose.ProposeCourseBean.insertSpotSubjectEduPropose() (수강신청 정보 등록 - TZ_PROPOSE ) */      \n");
            sql.append("INSERT  INTO    TZ_PROPOSE  (                                   \n");
            sql.append("        SUBJ                                                    \n");
            sql.append("    ,   YEAR                                                    \n");
            sql.append("    ,   SUBJSEQ                                                 \n");
            sql.append("    ,   USERID                                                  \n");
            sql.append("    ,   APPDATE                                                 \n");
            sql.append("    ,   CHKFIRST                                                \n");
            sql.append("    ,   CHKFINAL                                                \n");
            sql.append("    ,   LUSERID                                                 \n");
            sql.append("    ,   LDATE                                                   \n");
            sql.append("    ,   ASP_GUBUN                                               \n");
            sql.append(") VALUES (                                                      \n");
            sql.append("        '").append(subj).append("'                              \n");
            sql.append("    ,   '").append(year).append("'                              \n");
            sql.append("    ,   '").append(subjseq).append("'                           \n");
            sql.append("    ,   '").append(userid).append("'                            \n");
            sql.append("    ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')                     \n");
            sql.append("    ,   'Y'                                                     \n");
            sql.append("    ,   (                                                       \n");
            sql.append("        SELECT  DECODE(NVL(TRIM(AUTOCONFIRM), 'N'), 'Y', 'Y', 'B')    \n");
            sql.append("          FROM  TZ_SUBJSEQ                                      \n");
            sql.append("         WHERE  SUBJ = '").append(subj).append("'               \n");
            sql.append("           AND  YEAR = '").append(year).append("'               \n");
            sql.append("           AND  SUBJSEQ = '").append(subjseq).append("'         \n");
            sql.append("        )                                                       \n");
            sql.append("    ,   '").append(userid).append("'                            \n");
            sql.append("    ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')                     \n");
            sql.append("    ,   '").append(grcode).append("'                            \n");
            sql.append(")                                                               \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            if (resultCnt == 1) {

                sql.setLength(0);
                sql.append("/* com.credu.propose.ProposeCourseBean.insertSpotSubjectEduPropose() (수강신청 정보 등록 - TZ_STUDENT )  */");
                sql.append("INSERT  INTO    TZ_STUDENT  (                   \n");
                sql.append("        SUBJ                                    \n");
                sql.append("    ,   YEAR                                    \n");
                sql.append("    ,   SUBJSEQ                                 \n");
                sql.append("    ,   USERID                                  \n");
                sql.append("    ,   CLASS                                   \n");
                sql.append("    ,   COMP                                    \n");
                sql.append("    ,   ISDINSERT                               \n");
                sql.append("    ,   SCORE                                   \n");
                sql.append("    ,   TSTEP                                   \n");
                sql.append("    ,   MTEST                                   \n");
                sql.append("    ,   FTEST                                   \n");
                sql.append("    ,   REPORT                                  \n");
                sql.append("    ,   ACT                                     \n");
                sql.append("    ,   ETC1                                    \n");
                sql.append("    ,   ETC2                                    \n");
                sql.append("    ,   AVTSTEP                                 \n");
                sql.append("    ,   AVMTEST                                 \n");
                sql.append("    ,   AVFTEST                                 \n");
                sql.append("    ,   AVREPORT                                \n");
                sql.append("    ,   AVACT                                   \n");
                sql.append("    ,   AVETC1                                  \n");
                sql.append("    ,   AVETC2                                  \n");
                sql.append("    ,   ISGRADUATED                             \n");
                sql.append("    ,   ISRESTUDY                               \n");
                sql.append("    ,   ISB2C                                   \n");
                sql.append("    ,   EDUSTART                                \n");
                sql.append("    ,   EDUEND                                  \n");
                sql.append("    ,   BRANCH                                  \n");
                sql.append("    ,   CONFIRMDATE                             \n");
                sql.append("    ,   EDUNO                                   \n");
                sql.append("    ,   LUSERID                                 \n");
                sql.append("    ,   LDATE                                   \n");
                sql.append("    ,   STUSTATUS                               \n");
                sql.append(") VALUES (                                      \n");
                sql.append("        '").append(subj).append("'              \n");
                sql.append("    ,   '").append(year).append("'              \n");
                sql.append("    ,   '").append(subjseq).append("'           \n");
                sql.append("    ,   '").append(userid).append("'            \n");
                sql.append("    ,   ''                                      \n");
                sql.append("    ,   '").append(grcode).append("'            \n");
                sql.append("    ,   'Y'                                     \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   'N'                                     \n");
                sql.append("    ,   'N'                                     \n");
                sql.append("    ,   'N'                                     \n");
                sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDD')    \n");
                sql.append("    ,   add_months(TO_CHAR(SYSDATE, 'YYYYMMDD'), 1 ) \n");
                sql.append("    ,   99                                      \n");
                sql.append("    ,   ''                                      \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   '").append(userid).append("'            \n");
                sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
                sql.append("    ,   'Y'                                     \n");
                sql.append(")                                               \n");

                resultCnt += connMgr.executeUpdate(sql.toString());
            }

            if (resultCnt == 2) { // 2개의 테이블에 갱신하므로 2가 나와야 함.
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
    	} catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return resultCnt;
    }

    /**
     * 블랙리스트 제약설정
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @return result 제약대상 - 메세지
     */
    public String jeyakBlackcondition(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String result = "";

        String v_matchcode = ""; // 현재 신청한 과정의 어학,직무 구분코드 (W:직무, L:어학)
        String v_conditioncode = "";

        try {

            // 신청한 과정의 어학, 직무 구분
            sql1 = " select b.matchcode from VZ_SCSUBJSEQ a, TZ_CLASSFYMATCH b\n";
            sql1 += " where a.scupperclass = b.upperclass\n";
            sql1 += "   and a.subj	= " + SQLString.Format(p_subj);
            sql1 += "   and a.year	= " + SQLString.Format(p_year);
            sql1 += "   and a.subjseq = " + SQLString.Format(p_subjseq);

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                v_matchcode = ls1.getString("matchcode");
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }

            sql2 = " select a.conditioncode\n";
            sql2 += " from tz_blacklist a, vz_scsubjseq b\n";
            sql2 += " where a.grcode  = b.grcode\n";
            sql2 += "   and a.gryear  = b.gyear\n";
            sql2 += "   and a.grseq   = b.grseq\n";
            sql2 += "   and a.gubun  = " + SQLString.Format(v_matchcode);
            sql2 += "   and b.scsubj   = " + SQLString.Format(p_subj);
            sql2 += "   and b.scyear  = " + SQLString.Format(p_year);
            sql2 += "   and b.scsubjseq  = " + SQLString.Format(p_subjseq);

            ls2 = connMgr.executeQuery(sql2);
            // 제약대상이면
            if (ls2.next()) {
                v_conditioncode = ls2.getString("conditioncode");

                // 제약대상 메세지 SELECT
                sql3 = " select a.alertmsg\n";
                sql3 += "   from tz_BlackCondition a, vz_scsubjseq b\n";
                sql3 += " where a.grcode  = b.grcode\n";
                sql3 += "   and a.gryear  = b.gyear\n";
                sql3 += "   and a.grseq   = b.grseq\n";
                sql3 += "   and b.scsubj   = " + SQLString.Format(p_subj);
                sql3 += "   and b.scyear  = " + SQLString.Format(p_year);
                sql3 += "   and b.scsubjseq  = " + SQLString.Format(p_subjseq);
                sql3 += "   and a.conditioncode = " + SQLString.Format(v_conditioncode);

                ls3 = connMgr.executeQuery(sql3);
                if (ls3.next()) {
                    result = ls3.getString("alertmsg");
                    return result;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 제약조건 체크
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @param p_tem_grcode 현재교육그룹코드
     * @return result 제약조건결과 코드
     */
    public int jeyakCheck(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls = null;
        int result = 0;
        //		String msg = "";

        try {
            // 정원초과  - 1
            result = jeyakStudentlimit(connMgr, p_subj, p_year, p_subjseq, p_userid);
            if (result < 0) {
                return result;
            }

            //이미신청한과정  - 2
            result = jeyakPropose(connMgr, p_subj, p_year, p_subjseq, p_userid);
            if (result < 0) {
                return result;
            }

            //수료한과정  - 3
            result = jeyakStold(connMgr, p_subj, p_year, p_subjseq, p_userid);
            if (result < 0) {
                return result;
            }

            System.out.println("result = " + result);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return result;
    }

    /**
     * 회사별 수강신청 제약설정
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @return result 0 : 정상, 6 : 제약대상
     */
    public int jeyakCompcondition(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, String p_comp) throws Exception {
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int result = 0;

        String v_matchcode = ""; // 현재 신청한 과정의 어학,직무 구분코드 (W:직무, L:어학)
        // tz_compcondition에 설정된 값
        int v_duty1 = 0;
        int v_language1 = 0;
        int v_allcondition1 = 0;
        int v_yearduty1 = 0;
        int v_yearlanguage1 = 0;

        // 사용자 값
        int v_duty2 = 0;
        int v_language2 = 0;
        int v_allcondition2 = 0;
        int v_yearduty2 = 0;
        int v_yearlanguage2 = 0;

        try {

            // 신청한 과정의 어학, 직무 구분
            sql1 = " select b.matchcode from VZ_SCSUBJSEQ a, TZ_CLASSFYMATCH b\n";
            sql1 += " where a.scupperclass = b.upperclass\n";
            sql1 += "	and a.subj	= " + SQLString.Format(p_subj);
            sql1 += "	and a.year	= " + SQLString.Format(p_year);
            sql1 += "	and a.subjseq = " + SQLString.Format(p_subjseq);

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                v_matchcode = ls1.getString("matchcode");
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }

            // 제약내용
            sql1 = " select duty, language, allcondition, yearduty, yearlanguage\n";
            sql1 += "   from TZ_COMPCONDITION\n";
            sql1 += " where comp = " + SQLString.Format(p_comp);

            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) {
                v_duty1 = ls1.getInt("duty");
                v_language1 = ls1.getInt("language");
                v_allcondition1 = ls1.getInt("allcondition");
                v_yearduty1 = ls1.getInt("yearduty");
                v_yearlanguage1 = ls1.getInt("yearlanguage");
                if (v_duty1 == 0) {
                    v_duty1 = 10000; // 0일경우 체크를 안함
                } else if (v_language1 == 0) {
                    v_language1 = 10000; // 0일경우 체크를 안함
                } else if (v_allcondition1 == 0) {
                    v_allcondition1 = 10000; // 0일경우 체크를 안함
                } else if (v_yearduty1 == 0) {
                    v_yearduty1 = 10000; // 0일경우 체크를 안함
                } else if (v_yearlanguage1 == 0) {
                    v_yearlanguage1 = 10000; // 0일경우 체크를 안함
                }

                // 현재차수에 신청한 갯수
                sql2 = " select sum(case c.matchcode  when  'W'  then 1   else 0  end) duty,\n";
                sql2 += "		sum(case c.matchcode  when  'L'  then 1   else 0  end) language\n";
                sql2 += "   from tz_propose a, vz_scsubjseq  b, tz_classfymatch c\n";
                sql2 += "\n";
                sql2 += "  where a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq\n";
                sql2 += "	and b.scupperclass = c.upperclass\n";
                // 수정일 : 05.11.17 수정자 : 이나연 _ || 수정
                //		  sql2+= "	and b.grcode||b.gyear||b.grseq = (select grcode||gyear||grseq\n";
                sql2 += "	and b.grcode+b.gyear+b.grseq = (select grcode+gyear+grseq\n";
                sql2 += "		  from vz_scsubjseq\n";
                sql2 += "		 where subj	= " + SQLString.Format(p_subj);
                sql2 += "		   and year	= " + SQLString.Format(p_year);
                sql2 += "		   and subjseq = " + SQLString.Format(p_subjseq) + " )\n";
                sql2 += "	and a.cancelkind is null\n";
                sql2 += "	and a.userid = " + SQLString.Format(p_userid);

                ls2 = connMgr.executeQuery(sql2);
                if (ls2.next()) {
                    v_duty2 = ls2.getInt("duty");
                    v_language2 = ls2.getInt("language");
                } else {
                    v_duty2 = 0;
                    v_language2 = 0;
                }
                v_allcondition2 = v_duty2 + v_language2;

                if (v_allcondition1 <= v_allcondition2) { // 총신청갯수가 같은경우
                    result = -6;
                    return result;
                } else if (v_matchcode.equals("W") && (v_duty1 <= v_duty2)) { // 직무신청갯수가 같은경우
                    result = -6;
                    return result;
                } else if (v_matchcode.equals("L") && (v_language1 <= v_language2)) { // 어학신청갯수가 같은경우
                    result = -6;
                    return result;
                }

                // 현재년도에 신청한 갯수
                sql3 = " select sum(case c.matchcode  when  'W'  then 1   else 0  end) yearduty,\n";
                sql3 += "		sum(case c.matchcode  when  'L'  then 1   else 0  end) yearlanguage\n";
                sql3 += "   from tz_propose a,\n";
                sql3 += "	   (select grcode, gyear, grseq,scupperclass, subj, year, subjseq\n";
                sql3 += "		  from vz_scsubjseq\n";
                sql3 += "		 where subj	= " + SQLString.Format(p_subj);
                sql3 += "		   and year	= " + SQLString.Format(p_year) + " ) b,\n";
                sql3 += "		tz_classfymatch c\n";
                sql3 += "  where a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq\n";
                sql3 += "	and b.scupperclass = c.upperclass\n";
                sql3 += "	and a.cancelkind is null\n";
                sql3 += "	and a.userid =  " + SQLString.Format(p_userid);

                ls3 = connMgr.executeQuery(sql3);
                if (ls3.next()) {
                    v_yearduty2 = ls3.getInt("yearduty");
                    v_yearlanguage2 = ls3.getInt("yearlanguage");
                } else {
                    v_yearduty2 = 0;
                    v_yearlanguage2 = 0;
                }

                if (v_matchcode.equals("W") && (v_yearduty1 <= v_yearduty2)) { // 직무신청갯수가 같은경우
                    result = -6;
                    return result;
                } else if (v_matchcode.equals("L") && (v_yearlanguage1 <= v_yearlanguage2)) { // 어학신청갯수가 같은경우
                    result = -6;
                    return result;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 제약메세지
     *
     * @param p_jeyak 제약코드
     * @return result 메세지
     */
    public String jeyakMsg(int p_jeyak) {
        String msg = "";

        if (p_jeyak == -1) {
            msg = "정원초과입니다"; // 정원초과  - 1
        } else if (p_jeyak == -2) {
            msg = "이미신청한과정입니다"; // 이미신청한과정  - 2
        } else if (p_jeyak == -3) {
            msg = "수료한과정입니다"; // 수료한과정  - 3
        } else if (p_jeyak == -6) {
            msg = "이미 수강신청하신 과정이 있습니다."; //msg ="수강신청 제약입니다";				// 회사별 수강신청 제약설정
        }

        return msg;
    }

    /**
     * 이미신청한과정
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @return result 0 : 정상, 2 : 이미신청한과정
     */
    public int jeyakPropose(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            sql = " select count(*) cnt\n";
            sql += " from TZ_PROPOSE\n";
            sql += " where subj	= " + SQLString.Format(p_subj);
            sql += "   and year	= " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            sql += "   and userid  = " + SQLString.Format(p_userid);
            sql += "   and chkfinal != 'N'";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt("cnt") > 0) {
                    result = -2;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 수료한과정
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @return result 0 : 정상, 3 : 수료한과정
     */
    public int jeyakStold(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            sql = " select count(*) cnt\n";
            sql += " from TZ_STOLD\n";
            sql += " where subj	= " + SQLString.Format(p_subj);
            sql += "   and year	= " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            sql += "   and userid  = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt("cnt") > 0) {
                    result = -3;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 정원초과
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @return result 0 : 정상, 1 : 정원초과
     */
    public int jeyakStudentlimit(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            sql = " select (case a.studentlimit  when 0  then  1000000  else a.studentlimit  end) as limit ,";
            sql += "	(select count(userid) from tz_propose where subj = a.subj and subjseq = a.subjseq and year = a.year and chkfinal != 'N') as usernum\n";
            sql += " from   VZ_SCSUBJSEQ a\n";
            sql += " where a.subj	= " + SQLString.Format(p_subj);
            sql += "   and a.year	= " + SQLString.Format(p_year);
            sql += "   and a.subjseq = " + SQLString.Format(p_subjseq);

            System.out.println("jeyakStudentlimit.sql" + sql);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt("limit") < ls.getInt("usernum")) {
                    result = -1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 오프라인 수강 등록 취소
     *
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int OffLineApplyCancelSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        //		String sql = "";
        String sql1 = "";
        int isOk = 0;

        String v_userid = box.getSession("userid");
        String v_grcode = box.getString("p_grcode");
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql1 = " Delete From tz_offlinepropose\n";
            sql1 += " Where seq=? and grcode ='N000002' and userid=?\n";

            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt(1, v_seq);
            pstmt.setString(2, v_userid);

            isOk = pstmt.executeUpdate();
            System.out.println(v_seq + " : " + v_grcode + " : " + v_userid);
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 오프라인 수강 등록
     *
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int OffLineApplySubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        //		String sql = "";
        String sql1 = "";
        int isOk = 0;

        String v_userid = box.getSession("userid");
        //		String v_grcode = box.getString("p_grcode");
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql1 = " insert into tz_offlinepropose(seq, grcode, userid, ldate, status)";
            sql1 += " values (?, 'N000002', ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), 'W')";

            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt(1, v_seq);
            pstmt.setString(2, v_userid);

            isOk = pstmt.executeUpdate();
            //System.out.println("1111111111로그===>"+isOk);
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * OffLine 과정상세보기
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox OffLineApplyView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        //		String v_upcnt = "Y";
        //		String v_grcode = box.getString("p_grcode");

        int v_seq = box.getInt("p_seq");

        //		Vector realfileVector = new Vector();
        //		Vector savefileVector = new Vector();
        //		Vector fileseqVector  = new Vector();
        try {
            connMgr = new DBConnectionManager();

            sql = "Select\n";
            sql += "	a.seq, a.subjgubun, a.subj, a.subjseq, a.propStart, a.propEnd, a.subjNm, a.tUserid,\n";
            sql += "	a.tName,  a.dday, a.startTime, a.endTime, a.place, b.codenm as gubunNm, a.content\n";
            sql += " From TZ_OFFLINESUBJ a join tz_code b on a.subjgubun = b.code";
            sql += "	left outer join tz_subj c on a.subj = c.subj\n";
            sql += " Where\n";
            sql += "	b.gubun = '0061'\n";
            sql += "	and seq = " + v_seq;
            sql += " Order By seq DESC\n";
            System.out.println("OffLineApplyView.sql = " + sql);
            ls = connMgr.executeQuery(sql);

            for (int i = 0; ls.next(); i++) {

                dbox = ls.getDataBox();

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
        return dbox;
    }

    /**
     * 이벤트 사용자체크
     *
     * @param box receive from the form object and session
     * @return ArrayList 이벤트 사용자체크
     */
    public ArrayList<DataBox> selectChkUser(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        //		 String  v_subj	  = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            String s_userid = box.getSession("userid");

            sql = " SELECT RESNO FROM TZ_MEMBER A\n";
            sql += " WHERE USERID = '" + s_userid + "'\n";
            sql += " AND USERID IN (SELECT USERID FROM TZ_BILLINFO WHERE USERID=A.USERID)\n";

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
     * 교수목록
     *
     * @param box receive from the form object and session
     * @return ArrayList 교수목록
     */
    public ArrayList<DataBox> selectTutorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        String sql = "";
        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT\n	B.NAME,B.majorbook,B.intro, b.career,\n" + "	decode(nvl(b.photo, ''), b.photo, NEWPHOTO, '/images/portal/common/nophoto.gif') phototerms, b.comp " + " FROM TZ_SUBJTUTOR A, TZ_TUTOR B\n";
            sql += " WHERE a.USERID = B.USERID";
            sql += " AND A.SUBJ = ':p_subj'\n";

            ls = connMgr.executeQuery(sql, box);
            list = ls.getAllDataList();
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

    /*
     * ==================================== 과정소개
     * =====================================
     */
    /**
     * 수강과정 리스트 (일반과정)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectCourseIntroList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox1 = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_select = box.getStringDefault("p_select", "ON");
        String v_tabnum = box.getStringDefault("p_tabnum", "design"); // 대분류코드 매칭
        //			String  v_user_id	 = box.getSession("userid");
        String v_gadmin = box.getSession("gadmin");
        String v_grgubun = box.getStringDefault("p_grgubun", "G01");
        String v_gubun = "";
        String v_subjclass = "";

        if (v_tabnum.equals("design")) {
            v_gubun = "1";
        } else if (v_tabnum.equals("program")) {
            v_gubun = "2";
        } else if (v_tabnum.equals("graphic")) {
            v_gubun = "3";
        } else if (v_tabnum.equals("common")) {
            v_gubun = "4";
        }

        ConfigSet conf = new ConfigSet();
        v_subjclass = conf.getProperty("game.subjclass.gubun" + v_gubun);

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        //			String  gyear	= box.getStringDefault("gyear",FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " select a.subj, a.subjnm, a.isonoff, a.upperclass, a.middleclass, a.lowerclass, usebook,\n";
            sql1 += "		substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "		(select classname from tz_subjatt x\n";
            sql1 += "		  where x.upperclass = a.upperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "		(select classname from tz_subjatt x\n";
            sql1 += "		  where x.upperclass = a.upperclass and x.middleclass = a.middleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "		(select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH\n";
            sql1 += "		  where subj = a.subj and grcode = b.grcode) as sul_avg\n";
            sql1 += "   from TZ_SUBJ a, tz_grsubj b\n";
            sql1 += "  where a.subj = b.subjcourse\n";
            sql1 += "	and b.grcode	   = " + SQLString.Format(v_grcode);
            sql1 += "	and a.middleclass in (" + v_subjclass + ") ";
            sql1 += "	and a.isuse		= 'Y'\n";
            sql1 += "	and a.isonoff	  = " + SQLString.Format(v_select);
            sql1 += "	and a.upperclass = " + SQLString.Format(v_grgubun); // 게임,문콘
            if (!v_gadmin.equals("A1")) {
                sql1 += " and isvisible = 'Y'\n";
            }

            sql1 += " order by a.upperclass, a.middleclass, a.subjnm\n";
            System.out.println("........." + sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 수강과정 리스트 (코스과정)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectCourseIntroList2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_grcode = box.getSession("tem_grcode");

        // boolean ispossible = false;

        try {
            list1 = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            sql1 = " select a.course, a.coursenm, a.biyong, a.subjcnt,\n";
            sql1 += "		b.subj, c.subjnm,  c.upperclass, c.middleclass, c.lowerclass,\n";
            sql1 += "		(select classname from tz_subjatt\n";
            sql1 += "		  where upperclass = c.upperclass and middleclass = c.middleclass\n";
            sql1 += "			and lowerclass='000') classname\n";
            sql1 += "   from tz_course a, tz_coursesubj b, tz_subj c, tz_grsubj d\n";
            sql1 += "  where a.course = b.course\n";
            sql1 += "	and b.subj = c.subj\n";
            sql1 += "	and a.course = d.subjcourse\n";
            sql1 += "	and d.grcode = " + SQLString.Format(v_grcode);
            sql1 += " order by a.course, subjnm\n";

            //System.out.println("subject_list11="+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 코스에 해당하는 과정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ProposeCourseData> selectCourseSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<ProposeCourseData> list = null;
        String sql1 = "";
        //		String sql2			 = "";
        ProposeCourseData data1 = null;
        //		ProposeCourseData data2 = null;
        //		String  v_user_id	   = box.getSession("userid");
        String v_course = box.getString("p_course");
        String v_cyear = box.getString("p_cyear");
        String v_courseseq = box.getString("p_courseseq");
        String v_proposetype = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        int v_studentlimit = 0;
        int v_stucnt = 0;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProposeCourseData>();

            //select subj,year,subjseq,subjnm,edustart,eduend,isonoff,
            //proposetype,studentlimit,stucnt
            sql1 = "select subj,year,subjseq,subjnm,isonoff,";
            sql1 += "edustart,eduend,";
            sql1 += "proposetype,studentlimit,\n";
            sql1 += "(select count(subj) from TZ_PROPOSE where subj=A.subj and year=A.year and subjseq=A.subjseq) stucnt\n";
            sql1 += "from VZ_SCSUBJSEQ A\n";
            sql1 += " where course=" + SQLString.Format(v_course);
            sql1 += " and cyear=" + SQLString.Format(v_cyear);
            sql1 += " and courseseq=" + SQLString.Format(v_courseseq);
            sql1 += " and isuse = 'Y'\n";
            sql1 += " order by subj,subjseq,edustart,eduend\n";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                v_subj = ls1.getString("subj");
                v_year = ls1.getString("year");
                v_subjseq = ls1.getString("subjseq");
                v_proposetype = ls1.getString("proposetype");
                v_studentlimit = ls1.getInt("studentlimit");
                v_stucnt = ls1.getInt("stucnt");

                data1 = new ProposeCourseData();
                data1.setSubj(v_subj);
                data1.setYear(v_year);
                data1.setSubjseq(v_subjseq);
                data1.setSubjnm(ls1.getString("subjnm"));
                data1.setEdustart(ls1.getString("edustart"));
                data1.setEduend(ls1.getString("eduend"));
                data1.setIsonoff(ls1.getString("isonoff"));
                data1.setProposetype(v_proposetype);
                data1.setStudentlimit(v_studentlimit);
                data1.setStucnt(v_stucnt);
                list.add(data1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
     * 월간 교육 일정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ProposeCourseData> selectEducationMonthlyList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<ProposeCourseData> list = null;
        String sql1 = "";
        //		String sql2			 = "";
        ProposeCourseData data1 = null;
        //		ProposeCourseData data2 = null;
        //		String  v_gyear		 = box.getStringDefault("gyear",FormatDate.getDate("yyyy"));
        //		String  v_selmonth	  = box.getStringDefault("p_month",FormatDate.getDate("MM"));
        //		String  v_user_id	   = box.getSession("userid");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        String v_select = box.getString("p_select");
        String v_proposetype = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_subjseqgr = "";
        //		String  v_condition	 = v_gyear + v_selmonth;
        int v_propstart = 0;
        int v_propend = 0;
        int v_studentlimit = 0;
        int v_stucnt = 0;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProposeCourseData>();
            /*
             * 2005.11.07_하경태 : Orcle -> Mssql 쿼리 변환. sql1 =
             * "select scupperclass,course,cyear,courseseq,coursenm,subj,year,subjseq,subjnm,isonoff,\n"
             * ; sql1+=
             * "	   edustart, eduend, subjseqgr, proposetype,studentlimit,propstart,propend,\n"
             * ; sql1+=
             * "(select count(subj) from TZ_PROPOSE where subj=A.subj and year=A.year and subjseq=A.subjseq) stucnt\n"
             * ; sql1+= "from VZ_SCSUBJSEQ A\n"; sql1+=
             * " where gyear="+SQLString.Format(v_gyear); sql1+=
             * " and "+SQLString.Format(v_condition)+
             * " between SUBSTR(edustart,0,6) and SUBSTR(eduend,0,6)\n";
             * //개인기준인지 // sql1+=
             * "  where grcode in (select grcode from TZ_GRCOMP where comp like '"
             * +v_comp+"%')\n"; sql1+=
             * "	and (select comp from tz_grseq where grcode  =a.grcode and gyear = a.gyear and grseq = a.grseq) = "
             * + SQLString.Format(v_comp);
             *
             * sql1+= " and isuse = 'Y'\n"; sql1+= " and subjvisible = 'Y'\n";
             * sql1+= " and seqvisible= 'Y'\n"; sql1+=
             * " and length(replace(edustart,chr(32),'')) > 0\n";
             */

            sql1 = " select\n";
            sql1 += "	scupperclass, course, cyear, courseseq, coursenm, subj, year, subjseq,\n";
            sql1 += "	subjnm, isonoff, edustart, eduend, subjseqgr, proposetype,\n";
            sql1 += "	studentlimit, propstart, propend,\n";
            sql1 += "	(select count(subj) from TZ_PROPOSE where subj=A.subj and year=A.year and subjseq=A.subjseq) stucnt\n";
            sql1 += " from VZ_SCSUBJSEQ A\n";
            sql1 += " where gyear='2005'";
            sql1 += "   and grcode =" + SQLString.Format(v_grcode);
            sql1 += "	and isuse = 'Y' and subjvisible = 'Y' and seqvisible= 'Y' and LENGTH(replace(edustart, ' ','')) > 0\n";

            if (v_select.equals("ON") || v_select.equals("OFF")) {
                sql1 += " and A.isonoff = " + SQLString.Format(v_select);
                sql1 += " and INSTR(A.subjnm,'[통신]',1,1) = 0\n";
            } else if (v_select.equals("COURSE")) {
                sql1 += " and A.course != '000000'\n";
                sql1 += " and INSTR(A.subjnm,'[통신]',1,1) = 0\n";
            }
            if (v_select.equals("REP")) {
                sql1 += " and A.subjnm like '[통신]%'\n";
            }

            //		sql1+= " order by scupperclass,course,cyear,courseseq, subj,subjseq,edustart,eduend\n";
            /*---------------------------------- 코스 무시 -----------------------------------------*/
            sql1 += " order by scsubjnm\n";
            System.out.print(sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                v_subj = ls1.getString("subj");
                v_year = ls1.getString("year");
                v_subjseq = ls1.getString("subjseq");
                v_subjseqgr = ls1.getString("subjseqgr");
                v_proposetype = ls1.getString("proposetype");
                v_studentlimit = ls1.getInt("studentlimit");
                v_stucnt = ls1.getInt("stucnt");
                if (ls1.getString("propstart").equals("")) {
                    v_propstart = 0000000000;
                } else {
                    v_propstart = Integer.parseInt(ls1.getString("propstart"));
                }
                if (ls1.getString("propend").equals("")) {
                    v_propend = 0000000000;
                } else {
                    v_propend = Integer.parseInt(ls1.getString("propend"));
                }

                data1 = new ProposeCourseData();
                data1.setCourse(ls1.getString("course"));
                data1.setCyear(ls1.getString("cyear"));
                data1.setCourseseq(ls1.getString("courseseq"));
                data1.setCoursenm(ls1.getString("coursenm"));
                data1.setSubj(v_subj);
                data1.setYear(v_year);
                data1.setSubjseq(v_subjseq);
                data1.setSubjseqgr(v_subjseqgr);
                data1.setSubjnm(ls1.getString("subjnm"));
                data1.setEdustart(ls1.getString("edustart"));
                data1.setEduend(ls1.getString("eduend"));
                data1.setIsonoff(ls1.getString("isonoff"));
                data1.setProposetype(v_proposetype);
                data1.setStudentlimit(v_studentlimit);
                data1.setStucnt(v_stucnt);
                list.add(data1);

                //신청기간 전 여부
                if (v_propstart > Integer.parseInt(FormatDate.getDate("yyyyMMddHH"))) {
                    data1.setProposestart("N");
                } else {
                    data1.setProposestart("Y");
                }
                //신청마감여부
                if ((v_stucnt < v_studentlimit) && (v_propstart <= Integer.parseInt(FormatDate.getDate("yyyyMMddHH")) && v_propend >= Integer.parseInt(FormatDate.getDate("yyyyMMddHH")))) {
                    data1.setProposeend("N");
                } else {
                    data1.setProposeend("Y");
                }
                //수강완료여부

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
     * 연간 교육 일정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ProposeCourseData> selectEducationYearlyList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<ProposeCourseData> list = null;
        String sql1 = "";
        //		String sql2			 = "";
        ProposeCourseData data1 = null;
        //		ProposeCourseData data2 = null;
        String v_gyear = box.getStringDefault("gyear", FormatDate.getDate("yyyy"));
        //		String  v_user_id	   = box.getSession("userid");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        String v_select = box.getString("p_select");
        String v_proposetype = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_subjseqgr = "";
        int v_propstart = 0;
        int v_propend = 0;
        int v_studentlimit = 0;
        int v_stucnt = 0;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProposeCourseData>();

            sql1 = "select scupperclass, course, cyear, courseseq, coursenm, subj, year, subjseq, subjnm, isonoff,\n";
            sql1 += "	   edustart, eduend, subjseqgr, proposetype, studentlimit, propstart, propend,\n";
            sql1 += "	   (select count(subj) from TZ_PROPOSE where subj=A.subj and year=A.year and subjseq=A.subjseq) stucnt\n";
            sql1 += "from VZ_SCSUBJSEQ A\n";
            sql1 += " where gyear=" + SQLString.Format(v_gyear);
            sql1 += "   and grcode =" + SQLString.Format(v_grcode);
            sql1 += "   and isuse = 'Y'\n";
            sql1 += "   and subjvisible = 'Y'\n";
            sql1 += "   and seqvisible= 'Y'\n";
            sql1 += " and LENGTH(replace(edustart,' ','')) > 0\n";

            if (v_select.equals("ON") || v_select.equals("OFF")) {
                sql1 += " and A.isonoff = " + SQLString.Format(v_select);
            }
            /*---------------------------------- 코스 무시 -----------------------------------------*/
            sql1 += " order by scsubjnm\n";
            System.out.println("saf=====================>" + sql1 + "/");

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                v_subj = ls1.getString("subj");
                v_year = ls1.getString("year");
                v_subjseq = ls1.getString("subjseq");
                v_subjseqgr = ls1.getString("subjseqgr");
                v_proposetype = ls1.getString("proposetype");
                v_studentlimit = ls1.getInt("studentlimit");
                v_stucnt = ls1.getInt("stucnt");
                if (ls1.getString("propstart").equals("")) {
                    v_propstart = 0000000000;
                } else {
                    v_propstart = Integer.parseInt(ls1.getString("propstart"));
                }
                if (ls1.getString("propend").equals("")) {
                    v_propend = 0000000000;
                } else {
                    v_propend = Integer.parseInt(ls1.getString("propend"));
                }
                data1 = new ProposeCourseData();
                data1.setCourse(ls1.getString("course"));
                data1.setCyear(ls1.getString("cyear"));
                data1.setCourseseq(ls1.getString("courseseq"));
                data1.setCoursenm(ls1.getString("coursenm"));
                data1.setSubj(v_subj);
                data1.setYear(v_year);
                data1.setSubjseq(v_subjseq);
                data1.setSubjseqgr(v_subjseqgr);
                data1.setSubjnm(ls1.getString("subjnm"));
                data1.setEdustart(ls1.getString("edustart"));
                data1.setEduend(ls1.getString("eduend"));
                data1.setIsonoff(ls1.getString("isonoff"));
                data1.setProposetype(v_proposetype);
                data1.setStudentlimit(v_studentlimit);
                data1.setStucnt(v_stucnt);
                list.add(data1);

                //신청기간 전 여부
                if (v_propstart > Integer.parseInt(FormatDate.getDate("yyyyMMddHH"))) {
                    data1.setProposestart("N");
                } else {
                    data1.setProposestart("Y");
                }
                //신청마감여부
                if ((v_stucnt < v_studentlimit) && (v_propstart <= Integer.parseInt(FormatDate.getDate("yyyyMMddHH")) && v_propend >= Integer.parseInt(FormatDate.getDate("yyyyMMddHH")))) {
                    data1.setProposeend("N");
                } else {
                    data1.setProposeend("Y");
                }

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
     * 교육그룹 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SelectionData> selectGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<SelectionData> list1 = null;
        String sql1 = "";
        SelectionData data1 = null;
        //		String  v_user_id   = box.getSession("userid");
        String v_comp = box.getSession("comp");
        v_comp = v_comp.substring(0, 4);
        //		int	 l		   = 0;
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<SelectionData>();

            //select  code,name
            sql1 = "select distinct a.grcode code, substring(a.grcodenm,4,50) name, a.code_order\n";
            sql1 += "  from TZ_GRCODE a";
            sql1 += "  , tz_grcomp b";
            sql1 += " where a.grcode=b.grcode\n";
            sql1 += "   and b.comp like '" + v_comp + "%'";
            sql1 += " order by a.code_order\n";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new SelectionData();
                data1.setCode(ls1.getString("code"));
                data1.setName(ls1.getString("name"));
                list1.add(data1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /*
     * ====================================
     * 제약조건=====================================
     */

    /**
     * 강좌 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ProposeCourseData> selectLectureList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<ProposeCourseData> list = null;
        String sql = "";
        ProposeCourseData data = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lectdate = "";
        String v_lectsttime = "";

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProposeCourseData>();

            //select lecture,sdesc,lectdate,lectsttime,tutor
            sql = " select lecture,sdesc,lectdate,lectsttime,";
            sql += "		(select name from TZ_TUTOR where userid=A.tutorid) tutor\n";
            sql += "   from TZ_OFFSUBJLECTURE A\n";
            sql += "  where subj=" + SQLString.Format(v_subj);
            sql += "	and year=" + SQLString.Format(v_year);
            sql += "	and subjseq=" + SQLString.Format(v_subjseq);
            sql += "  order by lecture\n";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ProposeCourseData();
                data.setLecture(ls.getString("lecture"));
                data.setSdesc(ls.getString("sdesc"));
                v_lectdate = FormatDate.getFormatDate(ls.getString("lectdate"), "yyyy/MM/dd");
                v_lectsttime = FormatDate.getFormatTime(ls.getString("lectsttime"), "HH:mm");
                data.setLecturedate(v_lectdate + " " + v_lectsttime);
                data.setTutor(ls.getString("tutor"));
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
     * 일차 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectLessonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        //		ProposeCourseData data  = null;
        String v_subj = box.getString("p_subj"); //과정

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //select lesson,sdesc
            sql = "select a.lesson, a.sdesc, NVL(b.fromdate,0) fromdate,  NVL(b.todate,0) todate, (SELECT NAME FROM TZ_MEMBER WHERE USERID = A.USERID) name\n";
            sql += "from TZ_SUBJLESSON a, TZ_SUBJLESSONDATE b\n";
            sql += " where a.subj  =  b.subj(+)  and a.module  =  b.module(+) and a.lesson  =  b.lesson(+)\n";
            sql += "   and a.subj = " + SQLString.Format(v_subj);
            sql += " order by a.lesson\n";

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
     * 후수과정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 후수과정 리스트
     */
    public ArrayList<DataBox> selectListNext(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select a.gubun, b.subjnm from TZ_SUBJRELATE a, tz_subj b\n";
            sql += "  where a.rsubj = b.subj\n";
            sql += "	and gubun   = 'NEXT'\n";
            sql += " and a.subj	 = " + SQLString.Format(v_subj);

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
     * 선수과정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 선수과정 리스트
     */
    public ArrayList<DataBox> selectListPre(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select a.gubun, b.subjnm from TZ_SUBJRELATE a, tz_subj b\n";
            sql += "  where a.rsubj = b.subj\n";
            sql += "	and gubun   = 'PRE'\n";
            sql += "	and a.subj  = " + SQLString.Format(v_subj);

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
     * 신청자 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectProposeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        //		ListSet ls2		 = null;

        ArrayList<DataBox> list1 = null;
        //		ArrayList list2	 = null;
        String sql1 = "";

        String v_subj = box.getString("p_subj");
        String v_comp = box.getSession("comp");
        String gyear = box.getStringDefault("gyear", FormatDate.getDate("yyyy"));
        v_comp = v_comp.substring(0, 4);

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " select c.subjseqgr,b.name, get_deptnm(b.deptnam,'') compnm, a.appdate\n";
            sql1 += "   from tz_propose a, tz_member b ,  vz_scsubjseq c\n";
            sql1 += "  where a.userid  = b.userid\n";
            sql1 += "	and a.subj	= c.scsubj\n";
            sql1 += "	and a.subjseq = c.scsubjseq\n";
            sql1 += "	and a.year	= c.year\n";
            sql1 += "	and a.subj	= '" + v_subj + "'\n";
            sql1 += "	and a.year	= '" + gyear + "'\n";
            sql1 += "	and c.grcode in (select grcode from TZ_GRCOMP where comp like '" + v_comp + "%')";
            sql1 += "	and to_char(sysdate, 'yyyymmddhh') between c.propstart and c.edustart\n";
            sql1 += "  order by c.subjseqgr, b.comp, b.name\n";
            System.out.println("subject_list=" + sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 후수과정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 후수과정 리스트
     */
    public ArrayList<DataBox> selectSubjChasiList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " Select l.subj, l.module, l.lesson, l.sdesc, l.types, l.owner, fromdate, todate  from tz_subjlesson l\n";
            sql += " join tz_subjlessondate d on l.subj = d.subj and l.module = d.module and l.lesson = d.lesson";
            sql += "		Where l.subj = " + SQLString.Format(v_subj);

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
     * 수강과정 리스트 (온라인)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_gadmin = box.getSession("gadmin");

        //String p_type = box.get("type") + "01";
        String v_upperclass = box.get("p_upperclass");

        String pp_area = box.get("p_area");

        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.sync("p_order");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");
        String p_order = box.getString("p_order");

        String v_searchtext = box.getStringDefault("p_searchtext", "ALL");
        String v_search = box.getStringDefault("p_search", "NAME");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            // 수강신청 가능한 과정
            sql1 = "/* com.credu.propse.ProposeCourseBean.selectSubjectList (수강신청리스트) */ \n";
            sql1 += " select distinct a.subj, a.introducefilenamenew, a.subjseq, a.subjnm, a.scyear,a.hitnumber, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, usebook, a.prewidth, a.preheight,a.propstart as ps,a.wj_classkey,a.mobile_preurl, \n";
            sql1 += " (SELECT DECODE(COUNT(SUBJ), 0, 'false', 'true') FROM TZ_PROPOSE WHERE SUBJ=a.subj AND SUBJSEQ =a.subjseq AND YEAR = a.scyear AND USERID = ':s_userid') EXISTPROPOSE,\n";
            sql1 += "		TO_CHAR(TO_DATE(a.propstart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propstart, TO_CHAR(TO_DATE(a.propend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propend, TO_CHAR(TO_DATE(a.edustart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') edustart, TO_CHAR(TO_DATE(a.eduend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') eduend, a.scbiyong, a.biyong, a.preurl, a.isunit, A.STUDENTLIMIT,\n";
            sql1 += "	substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "    (select classname from tz_subjatt x\n";
            sql1 += "    where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.LOWERCLASS = a.SCLOWERCLASS ) lclassnm,\n";
            sql1 += "	a.course, a.courseseq, a.coursenm, a.isbelongcourse, a.subjcnt,a.area, a.areaname,\n";
            sql1 += "	(Select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	Where subj = a.subj and grcode = a.grcode) as sul_avg,\n";
            sql1 += "	case when to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend	then 'Y' else 'N' end propyn\n";
            if (v_grcode.equals("N000134")) {
            	sql1 += "   , case when subj in ('CK10074','CK18005','CK11004') then 1 else 2 end ordersec \n ";
            }
            
            sql1 += "   from (select * from VZ_SCSUBJSEQIMGMOBILE where propstart not in (' ') and to_char(sysdate,'YYYYMM') between replace(substr(propstart,1,6),'.','') and replace(substr(propend,1,6),'.','')) a\n";
            
            //기수별 홈페이지노출여부를 사용하기 위한 Join 문
            sql1 += "		LEFT JOIN TZ_GRSEQ b  ON a.grcode = b.grcode AND a.gyear = b.gyear AND a.grseq = b.grseq \n ";
            //사이트기준인지
            sql1 += "  where a.grcode	   = " + SQLString.Format(v_grcode);
            sql1 += "\n\t and a.isuse		= 'Y'";
            sql1 += "\n\t and a.scupperclass like " + SQLString.Format(v_upperclass) + "||'%'"; // 게임,문콘
            sql1 += "\n\t and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend";

            //기수별 홈페이지노출여부를 확인하여 홈페이지 노출함
            sql1 += "\n\t and b.homepageyn = 'Y'";

            if (!v_gadmin.equals("A1")) {
                sql1 += "\n\tand subjvisible = 'Y'";
            }

            /*
             * 2012년 모두 서비스 if(v_grcode.equals("N000030")) sql1+=
             * "\n\t and area = 'K0'";
             */

            if (!v_searchtext.equals("ALL")) {
                if (v_search.equals("NAME")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                } else if (v_search.equals("CONT")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                }
            }
            /**
             * 검색조건
             *
             * sql1+="\n\t and a.specials LIKE DECODE(nvl(':s_specials', 'ALL'), 'ALL', '%', ':s_specials')"
             * ; sql1+="\n\t and ( (upper(a.subjnm) LIKE '%' || nvl(upper(':s_subjnm'), upper(a.subjnm)) || '%') or (upper(a.search_nm) LIKE '%' || nvl(upper(':s_subjnm'), upper(a.search_nm)) || '%') ) "
             * ;
             *
             * /** 필터링 영역
             */
            sql1 += "\n\t and a.scmiddleclass LIKE DECODE(nvl(':s_mclassnm', 'ALL'), 'ALL', '%', ':s_mclassnm')";
            sql1 += "\n\t and a.sclowerclass LIKE DECODE(nvl(':s_sclowerclass', 'ALL'), 'ALL', '%', ':s_sclowerclass')";

            if (pp_area.equals("W0")) {
                sql1 += "\n\t and NVL(a.area, nvl(':p_area', 'ALL')) LIKE DECODE(nvl(':p_area', 'ALL'), 'ALL', '%', ':p_area')";
            } else {
                sql1 += "\n\t and a.area != 'W0' and NVL(a.area, nvl(':p_area', 'ALL')) LIKE DECODE(nvl(':p_area', 'ALL'), 'ALL', '%', ':p_area')";
            }

            //sql1+= "\n\t and a.scyear = to_char(sysdate,'YYYY')";

            sql1 += "\n order by ";
            if (v_grcode.equals("N000134")) {
            	sql1 += "\n ordersec,";
            }
            sql1 += "\n isnew desc,";

            if (p_order.trim().equals("date"))
                sql1 += " a.propstart,";
            else if (p_order.trim().equals("biyong"))
                sql1 += " a.biyong,";
            else
                sql1 += " a.subjnm,";

            sql1 += " a.scupperclass, a.scmiddleclass";
            //			sql1 += " ishit desc, a.course, a.scupperclass, a.scmiddleclass, a.subjnm";
            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataList(connMgr, sql1, box, true);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 전체 수강과정 리스트 (온라인)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectTotalSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_gadmin = box.getSession("gadmin");

        //String p_type = box.get("type") + "01";
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");
        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_lclassnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");
        String p_order = box.getString("p_order");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            // 수강신청 가능한 과정
            sql1 = " select distinct a.subj, a.subjseq, a.subjnm, a.scyear, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, usebook,\n";
            sql1 += "		TO_CHAR(TO_DATE(a.propstart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propstart, TO_CHAR(TO_DATE(a.propend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propend, TO_CHAR(TO_DATE(a.edustart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') edustart, TO_CHAR(TO_DATE(a.eduend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') eduend, a.scbiyong, a.biyong, a.preurl, a.isunit, A.STUDENTLIMIT,\n";
            sql1 += "	substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "	a.course, a.courseseq, a.coursenm, a.isbelongcourse, a.subjcnt,\n";
            sql1 += "	(Select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	Where subj = a.subj and grcode = a.grcode) as sul_avg, \n";
            sql1 += "	case when to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend    then 'Y' else 'N' end propyn, \n";
            sql1 += "	(SELECT DECODE(COUNT(SUBJ), 0, 'false', 'true') FROM TZ_PROPOSE WHERE SUBJ=a.subj AND SUBJSEQ =a.subjseq AND YEAR = a.scyear AND USERID = '') EXISTPROPOSE, a.subjimg	\n";
            sql1 += "   from VZ_SCSUBJSEQ a \n";
            //사이트기준인지
            sql1 += "  where a.grcode	   = " + SQLString.Format(v_grcode);
            sql1 += "\n\t and a.isuse		= 'Y'";
            if (!v_upperclass.equals("ALL"))
                sql1 += "\n\t and a.scupperclass like " + SQLString.Format(v_upperclass) + "||'%'"; // 게임,문콘

            sql1 += "\n\t and to_char(sysdate,'YYYYMM') between replace(substr(a.propstart,1,6),'.','') and replace(substr(a.propend,1,6),'.','') ";

            if (!v_gadmin.equals("A1")) {
                sql1 += "\n\tand subjvisible = 'Y'";
            }
            /**
             * 검색조건
             */
            sql1 += "\n\t and a.specials LIKE DECODE(nvl(':s_specials', 'ALL'), 'ALL', '%', ':s_specials')";
            sql1 += "\n\t and ( (upper(a.subjnm) LIKE '%' || nvl(upper(':s_subjnm'), upper(a.subjnm)) || '%') or (upper(a.search_nm) LIKE '%' || nvl(upper(':s_subjnm'), upper(a.search_nm)) || '%') )";

            /**
             * 필터링 영역
             */
            sql1 += "\n\t and a.scupperclass LIKE DECODE(nvl(':s_lclassnm', 'ALL'), 'ALL', '%', ':s_lclassnm')";
            sql1 += "\n\t and a.scmiddleclass LIKE DECODE(nvl(':s_mclassnm', 'ALL'), 'ALL', '%', ':s_mclassnm')";
            //sql1+= "\n\t and a.sclowerclass LIKE DECODE(nvl(':s_sclowerclass', 'ALL'), 'ALL', '%', ':s_sclowerclass')";

            sql1 += "\n order by ";

            if (p_order.trim().equals("date"))
                sql1 += " a.propstart";
            else if (p_order.trim().equals("biyong"))
                sql1 += " a.biyong";
            else
                sql1 += " a.subjnm";

            //			sql1 += "\norder by a.course, a.scupperclass, a.scmiddleclass, a.subjnm";
            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataList(connMgr, sql1, box, true);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 전체 과정 리스트 (온라인)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjectListAll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox1 = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        MainSubjSearchBean bean = new MainSubjSearchBean();

        String v_select = "ON"; //온라인 전체과정
        String v_user_id = box.getSession("userid");
        String v_gadmin = box.getSession("gadmin");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        //		String  gyear		  = box.getStringDefault("gyear",FormatDate.getDate("yyyy"));

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            // 전체과정 리스트
            sql1 = " select distinct a.subj, a.year, a.subjseq, a.subjnm, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, a.usebook,\n";
            sql1 += "		a.propstart,  a.propend,  a.edustart,  a.eduend,  a.studentlimit,  a.subjseqgr, a.preurl, contenttype,\n";
            sql1 += "		substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "		(select classname from tz_subjatt x\n";
            sql1 += "		  where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "		(select classname from tz_subjatt x\n";
            sql1 += "		  where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm\n";
            sql1 += "   from VZ_SCSUBJSEQ a\n";
            sql1 += "  where a.grcode	   = " + SQLString.Format(v_grcode);
            sql1 += "	and a.isuse		= 'Y'\n";
            sql1 += "	and a.isonoff	  = " + SQLString.Format(v_select);
            sql1 += "	and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend\n";
            if (!v_gadmin.equals("A1")) {
                sql1 += " and subjvisible = 'Y'\n";
            }

            sql1 += " order by a.scupperclass , a.scmiddleclass, a.subjnm\n";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox1 = ls1.getDataBox();

                dbox1.put("d_ispropose", bean.getPropseStatus(box, ls1.getString("subj"), ls1.getString("subjseq"), ls1.getString("year"), v_user_id, "3"));
                list1.add(dbox1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 인기과정소개
     *
     * @param box receive from the form object and session
     * @return ArrayList 과정 리스트
     */
    public ArrayList<DataBox> selectSubjectListBest(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox1 = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " select\n";
            sql1 += "	 subj, subjnm, isonoff, scupperclass, scmiddleclass,sclowerclass,uclassnm, mclassnm, cnt\n";
            sql1 += " from\n";
            sql1 += "	(\n";
            sql1 += "		select  distinct top 5 a.subj, a.subjnm, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass,\n";
            sql1 += "			(select classname from tz_subjatt x where x.upperclass = a.scupperclass and x.middleclass = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "			(select classname from tz_subjatt x where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm, b.cnt\n";
            sql1 += "		from VZ_SCSUBJSEQ a, (select subj, count(userid) as cnt from tz_propose where cancelkind is null group by subj ) b\n";
            sql1 += "		where\n";
            sql1 += "			a.subj = b.subj and a.grcode = " + SQLString.Format(v_grcode) + " and\n";
            sql1 += "			(select comp from tz_grseq where grcode =a.grcode and gyear = a.gyear and grseq = a.grseq) = '0101000000' and\n";
            sql1 += "			a.isuse = 'Y' and a.isonoff = 'ON' and a.scupperclass in (select upperclass from TZ_CLASSFYMATCH where matchcode = 'W')\n";
            sql1 += "		order by b.cnt ) c";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 수강과정 리스트 (코스과정)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjectOffList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_iscourseYn = box.getStringDefault("p_iscourseYn", "Y");
        String v_user_id = box.getSession("userid");
        String v_gadmin = box.getSession("gadmin");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        String gyear = box.getStringDefault("gyear", FormatDate.getDate("yyyy"));

        try {
            list1 = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            // 수강신청 가능한 과정
            sql1 = " select distinct subj, subjnm, isonoff, scupperclass, scmiddleclass, sclowerclass, usebook,\n";
            sql1 += "		substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "		(select classname from tz_subjatt x\n";
            sql1 += "		  where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "		(select classname from tz_subjatt x\n";
            sql1 += "		  where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "		(select count(subjseq) from  tz_subjseq x\n";
            sql1 += "		  where x.grcode = " + SQLString.Format(v_grcode);
            sql1 += "			and x.subj = a.subj and x.gyear = a.gyear  ) totcnt, a.year, a.subjseq,\n";
            sql1 += "		a.course, a.courseseq, a.coursenm, a.isbelongcourse, a.subjcnt, a.propstart, a.propend, a.edustart, a.eduend, a.scbiyong, a.biyong, a.preurl, a.isunit,\n";
            sql1 += "		 ( Select count(*) From tz_propose p Where subj= a.subj and subjseq = a.subjseq and year = a.year and userid = '" + v_user_id + "') as procnt";
            sql1 += "   from VZ_SCSUBJSEQ a\n";
            sql1 += "  where grcode = " + SQLString.Format(v_grcode);
            sql1 += "	and isuse = 'Y'\n";
            sql1 += "	and A.scyear  = " + SQLString.Format(gyear);
            sql1 += "	and A.isbelongcourse = " + SQLString.Format(v_iscourseYn);
            sql1 += "	and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend\n";
            //총괄관리자의 경우에는 보여준다
            if (!v_gadmin.equals("A1")) {
                sql1 += " and subjvisible = 'Y'";
            }
            sql1 += " order by a.course, scupperclass,scmiddleclass, subjnm\n";
            System.out.println("selectSubjectOffList.sql = " + sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 과정상세보기
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox selectSubjectPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        String v_subj = box.getString("p_subj");
        String year = box.getString("p_year");
        String subjseq = box.getString("p_subjseq");
        String userid = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        System.out.println("v_subj : " + v_subj);
        System.out.println("year : " + year);
        System.out.println("subjseq : " + subjseq);

        try {
            connMgr = new DBConnectionManager();

            if (year.equals("") || subjseq.equals("")) {
                sql.setLength(0);

                sql.append("     select b.subjseq, b.year                                                         \n");
                sql.append("       from tz_subj a , tz_subjseq b                                                  \n");
                sql.append("      WHERE a.subj = b.subj                                                           \n");
                sql.append("        and b.SUBJ = '").append(v_subj).append("' \n");
                sql.append("        and b.year = '2015'                                                           \n");
                sql.append("        and grseq = '0017'              \n");
                /*sql.append("SELECT  *                                                                           \n");
                sql.append("  FROM  (                                                                           \n");
                sql.append("        SELECT  B.SUBJ                                                              \n");
                sql.append("            ,   B.YEAR                                                              \n");
                sql.append("            ,   B.SUBJSEQ                                                           \n");
                sql.append("            ,   RANK() OVER( ORDER BY B.YEAR DESC, B.SUBJSEQ DESC) AS RNK           \n");
                sql.append("          FROM  TZ_GRSEQ A                                                          \n");
                sql.append("            ,   TZ_SUBJSEQ B                                                        \n");
                sql.append("         WHERE  B.SUBJ = 'CK14004'                                                  \n");
                sql.append("           AND  A.GRCODE = 'N000001'                                                \n");
                sql.append("           AND  A.HOMEPAGEYN = 'Y'                                                  \n");
                sql.append("           AND  A.STAT = 'Y'                                                        \n");
                sql.append("           AND  A.GRCODE = B.GRCODE                                                 \n");
                sql.append("           AND  A.GYEAR = B.GYEAR                                                   \n");
                sql.append("           AND  A.GRSEQ = B.GRSEQ                                                   \n");
                sql.append("           AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN B.PROPSTART AND B.PROPEND  \n");
                sql.append("        )                                                                           \n");
                sql.append(" WHERE  RNK = 1                                                                     \n");*/

                ls1 = connMgr.executeQuery(sql.toString());
                if (ls1.next()) {
                    year = ls1.getString("year");
                    subjseq = ls1.getString("subjseq");
                }

                ls1.close();
                ls1 = null;
            }

            sql.setLength(0);
            sql.append("/******************* 과정상세보기 내용 ********************/							\n");
            sql.append(" SELECT DISTINCT\n");
            sql.append("    A.SUBJ, A.SUBJNM, A.CONTURL, B.YEAR, B.SUBJSEQ, A.SUBJTARGET, A.EDUDAYS, A.EDUTIMES, A.PLACE, A.EDUTYPE,A.MIDDLECLASS,\n");
            sql.append("    (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS=A.UPPERCLASS AND MIDDLECLASS=A.MIDDLECLASS AND LOWERCLASS='000') MIDDLECLASSNM,\n");
            sql.append("    (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS=A.UPPERCLASS AND MIDDLECLASS=A.MIDDLECLASS AND LOWERCLASS=A.LOWERCLASS) LOWERCLASSNM,\n");
            sql.append("    (SELECT CODENM FROM TZ_CODE WHERE GUBUN = '0076' AND CODE = A.SUBJTYPE) SUBJTYPENM,\n");
            sql.append("    A.EDUMANS, A.INTRO, A.EXPLAIN, A.MEMO, A.OWNER, A.OWNERMAN, A.OWNERTEL, A.PREURL, A.PREWIDTH, A.PREHEIGHT,\n");
            sql.append("    A.USEBOOK, A.BOOKPRICE, A.BOOKFILENAMEREAL, A.BOOKFILENAMENEW,\n");
            sql.append("    SUBSTRING(A.SPECIALS,1,1) ISNEW, SUBSTRING(A.SPECIALS,2,1) ISHIT, SUBSTRING(A.SPECIALS,3,1) ISRECOM,\n");
            sql.append("    A.MUSERID, A.MUSERTEL, A.ISOUTSOURCING, A.ISGOYONG, A.ISUNIT, A.ISORDINARY, A.ISWORKSHOP,\n");
            sql.append("    A.INTRODUCEFILENAMEREAL, A.INTRODUCEFILENAMENEW, A.CONTENTTYPE, A.ISONOFF, A.EDUPERIOD, B.BIYONG,\n");
            sql.append("    A.GRADSCORE, A.GRADSTEP, A.WSTEP, A.WMTEST, A.WFTEST, A.WREPORT, A.WETC1, A.WETC2,\n");
            sql.append("    T.NAME, T.CAREER, T.BOOK, T.PHOTO, T.NEWPHOTO, ST.CLASSNAME , A.REVIEWDAYS, A.REVIEWTYPE, A.AREA,\n");
            sql.append("    (SELECT CODENM FROM TZ_CODE WHERE GUBUN = '0101' AND CODE = A.AREA) AREANAME,\n");
            sql.append("    (case when substring(PROPSTART,1,8)<=to_char(sysdate,'yyyymmdd') and substring(PROPEND,0,8)>=to_char(sysdate,'yyyymmdd') then 'Y' else 'N' end) as sugang_YN \n");
            sql.append("	,nvl(a.mobile_use_yn,'N') as mobile_use_yn\n");
            sql.append("    ,DECODE( (SELECT COUNT(USERID) FROM TZ_SUBJ_FAVOR WHERE SUBJ = '").append(v_subj).append("' AND USERID = '").append(userid).append("'), 0, 'N', 'Y') AS FAVOR_YN \n");
            sql.append("    ,DECODE( (SELECT COUNT(USERID) FROM TZ_PROPOSE WHERE SUBJ= '").append(v_subj).append("' AND USERID='").append(userid).append("' AND YEAR ='").append(year).append("' AND SUBJSEQ = '").append(subjseq).append(
                    "'), 0, 'N', 'Y') AS PROPOSE_YN \n");
            sql.append("    , (SELECT COMP FROM TZ_MEMBER WHERE USERID = '").append(userid).append("' AND GRCODE ='").append(v_grcode).append("') AS COMPANY \n");
            sql.append(" FROM TZ_SUBJ A\n");
            sql.append("    LEFT OUTER JOIN TZ_SUBJSEQ B ON A.SUBJ = B.SUBJ and b.year = '").append(year).append("' and b.subjseq = '").append(subjseq).append("' \n");
            sql.append("    LEFT OUTER JOIN TZ_TUTOR T ON A.MUSERID = T.USERID\n");
            sql.append("    JOIN TZ_SUBJATT ST ON A.MIDDLECLASS = ST.MIDDLECLASS AND A.UPPERCLASS = ST.UPPERCLASS\n");
            sql.append("        AND A.MIDDLECLASS = ST.MIDDLECLASS AND ST.LOWERCLASS = '000'\n");
            sql.append(" WHERE A.SUBJ = ").append(SQLString.Format(v_subj));

            ls1 = connMgr.executeQuery(sql.toString());

            if (ls1.next()) {
                dbox = ls1.getDataBox();
            }
            //			System.out.println("v_subj = " + v_subj);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * 개설차수 리스트
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public ArrayList<DataBox> selectSubjSeqList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        MainSubjSearchBean bean = new MainSubjSearchBean();

        DataBox dbox = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        StringBuilder sql = new StringBuilder();
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_gyear = box.getString("gyear");

        String v_user_id = box.getSession("userid");
        // String  v_gadmin	= box.getSession("gadmin");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");
        //	boolean isjeyak = true;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql.append("/* com.credu.propose.ProposeCourseBean selectSubjSeqList (개설차수 리스트) */ \n");
            sql.append("SELECT  SUBJ                                                \n");
            sql.append("    ,   SUBJSEQ                                             \n");
            sql.append("    ,   YEAR                                                \n");
            sql.append("    ,   PROPSTART                                           \n");
            sql.append("    ,   PROPEND                                             \n");
            sql.append("    ,   EDUSTART                                            \n");
            sql.append("    ,   EDUEND                                              \n");
            sql.append("    ,   STUDENTLIMIT                                        \n");
            sql.append("    ,   SUBJSEQGR                                           \n");
            sql.append("    ,   ISONOFF                                             \n");
            sql.append("    ,   SCBIYONG                                            \n");
            sql.append("    ,   BIYONG                                              \n");
            sql.append("    ,   PREURL                                              \n");
            sql.append("    ,   (                                                   \n");
            sql.append("        SELECT  DECODE(COUNT(SUBJ), 0, 'false', 'true')     \n");
            sql.append("          FROM  TZ_PROPOSE                                  \n");
            sql.append("         WHERE  SUBJ = A.SUBJ                               \n");
            sql.append("           AND  SUBJSEQ = A.SUBJSEQ                         \n");
            sql.append("           AND  YEAR = A.SCYEAR                             \n");
            sql.append("           AND  USERID = 'lee1'                             \n");
            sql.append("        ) AS EXISTPROPOSE                                   \n");
            sql.append("    ,   MASTERNAME                                          \n");
            sql.append("    ,   MASTEREMAIL                                         \n");
            sql.append("    ,   MASTERCOMPTEL                                       \n");
            sql.append("    ,   GRADSCORE                                           \n");
            sql.append("    ,   GRADSTEP                                            \n");
            sql.append("    ,   WSTEP                                               \n");
            sql.append("    ,   WMTEST                                              \n");
            sql.append("    ,   WFTEST                                              \n");
            sql.append("    ,   WHTEST                                              \n");
            sql.append("    ,   WREPORT                                             \n");
            sql.append("    ,   WETC1                                               \n");
            sql.append("    ,   WETC2                                               \n");
            sql.append("    ,   (                                                   \n");
            sql.append("        SELECT  COUNT(*) AS CNT                             \n");
            sql.append("          FROM  TZ_PROPOSE                                  \n");
            sql.append("         WHERE  SUBJ = A.SUBJ                               \n");
            sql.append("           AND  YEAR = A.YEAR                               \n");
            sql.append("           AND  SUBJSEQ = A.SUBJSEQ                         \n");
            sql.append("           AND  NVL(CANCELKIND,' ') NOT IN ('F','P')        \n");
            sql.append("           AND  USERID = '").append(v_user_id).append("'    \n");
            sql.append("        ) AS PROPCNT                                        \n");
            sql.append("  FROM  VZ_SCSUBJSEQ A                                      \n");
            sql.append(" WHERE  SCSUBJ = '").append(v_subj).append("'               \n");
            sql.append("   AND  GRCODE = '").append(v_grcode).append("'             \n");
            sql.append("   AND  A.SUBJSEQ = '").append(v_subjseq).append("'         \n");
            sql.append("   AND  TO_CHAR(SYSDATE,'YYYYMMDDHH24') <= A.PROPEND        \n");
            sql.append("   AND  GYEAR = NVL('").append(v_gyear).append("', GYEAR)   \n");
            sql.append(" ORDER BY SUBJSEQGR          \n");

            // sql = connMgr.replaceParam(sql, box);
            ls1 = connMgr.executeQuery(sql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_ispropose", bean.getPropseStatus(box, ls1.getString("subj"), ls1.getString("subjseq"), ls1.getString("year"), v_user_id, "3"));
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * 차수정보상세보기
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox selectSubjSeqPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        String sql = "";
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        //		String  gyear	   = box.getString("gyear");
        //		String  v_isonoff   = box.getString("p_isonoff");

        try {
            connMgr = new DBConnectionManager();
            sql = "  select\n";
            sql += "	a.propstart,\n";
            sql += "	a.propend,\n";
            sql += "	a.edustart,\n";
            sql += "	a.eduend,\n";
            sql += "	a.studentlimit,\n";
            sql += "	a.subjseq,\n";
            sql += "	a.subjseqgr,\n";
            sql += "	a.subjnm,\n";
            sql += "	b.edutimes,\n";
            sql += "	a.biyong,\n";
            sql += "	a.gradscore,\n"; /* 이수기준-점수 */
            sql += "	a.gradstep,\n"; /* 이수기준-진도율 */
            sql += "	a.gradreport,\n"; /* 이수기준-리포트 */
            sql += "	a.gradreportyn,\n"; /* 이수기준-리포트제출미제출 */
            sql += "	a.gradexam,\n"; /* 이수기준-평가 */
            sql += "	a.gradftest,\n"; /* 이수기준-평가 */
            sql += "	a.gradhtest,\n"; /* 이수기준-평가 */
            sql += "	a.wstep,\n"; /* 가중치-진도율 */
            sql += "	a.wmtest,\n"; /* 가중치-중간평가 */
            sql += "	a.wftest,\n"; /* 가중치-최종평가 */
            sql += "	a.whtest,\n"; /* 가중치-형성평가 */
            sql += "	a.wreport,\n"; /* 가중치-REPORT */
            sql += "	a.wetc1,\n"; /* 가중치-REPORT */
            sql += "	a.wetc2,\n"; /* 가중치-REPORT */
            sql += "	a.isgoyong,\n";
            sql += "	a.place,\n";
            sql += "	a.placejh,\n";
            sql += "	b.muserid,\n";
            sql += "	m.name,\n";
            sql += "	b.ownertel,\n";
            sql += "	b.bookprice\n";
            sql += "  from\n";
            sql += "	tz_subjseq a, tz_subj b, tz_member m\n";
            sql += "  where\n";
            sql += "	a.subj = b.subj\n and b.muserid = m.userid\n";
            sql += "	and a.subj = " + SQLString.Format(v_subj) + "\n";
            sql += "	and a.subjseq = " + SQLString.Format(v_subjseq) + "\n";
            sql += "	and a.year = " + SQLString.Format(v_year) + "\n";

            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                dbox = ls1.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * 임시 comp, jikup 세션부여
     *
     * @param box receive from the form object and session
     * @return int
     */
    public int setCreduSession(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        int result = 0;
        String v_userid = box.getSession("userid");

        String v_comp = "";
        String v_jik = "";

        try {
            connMgr = new DBConnectionManager();

            //select  code,name
            sql1 = "select comp, jikup\n";
            sql1 += "  from TZ_MEMBER\n";
            sql1 += " where userid ='" + v_userid + "'";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                v_comp = ls1.getString("comp");
                v_jik = ls1.getString("jikup");
                box.setSession("comp", v_comp);
                box.setSession("jikup", v_jik);
                result = 1;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
     * 수강과정 리스트 (온라인 메인)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectMainSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        StringBuffer orderSql = new StringBuffer();
        StringBuffer countSql = new StringBuffer();

        DataBox dbox = null;

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            // 수강신청 가능한 과정
            headSql.append(" SELECT ROWNUM ROWNO, A.SUBJ, A.SUBJSEQ, A.SUBJNM, A.SCYEAR, A.ISONOFF, A.SCUPPERCLASS, A.SCMIDDLECLASS, A.SCLOWERCLASS, USEBOOK,\n");
            headSql
                    .append("		TO_CHAR(TO_DATE(A.PROPSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') PROPSTART, TO_CHAR(TO_DATE(A.PROPEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') PROPEND, TO_CHAR(TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') EDUSTART, TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') EDUEND, A.SCBIYONG, A.BIYONG, A.PREURL, A.ISUNIT, A.STUDENTLIMIT,\n");
            headSql.append("	SUBSTRING(A.SPECIALS,1,1) ISNEW, SUBSTRING(A.SPECIALS,2,1) ISHIT, SUBSTRING(A.SPECIALS,3,1) ISRECOM,\n");
            headSql.append("	(SELECT CLASSNAME FROM TZ_SUBJATT X\n");
            headSql.append("	WHERE X.UPPERCLASS = A.SCUPPERCLASS AND X.MIDDLECLASS  = '000' AND X.LOWERCLASS = '000' ) UCLASSNM,\n");
            headSql.append("	(SELECT CLASSNAME FROM TZ_SUBJATT X\n");
            headSql.append("	WHERE X.UPPERCLASS = A.SCUPPERCLASS AND X.MIDDLECLASS = A.SCMIDDLECLASS AND X.LOWERCLASS = '000' ) MCLASSNM,\n");
            headSql.append("	A.COURSE, A.COURSESEQ, A.COURSENM, A.ISBELONGCOURSE, A.SUBJCNT,\n");
            headSql.append("	(SELECT ROUND( NVL(SUM(DISTCODE1_AVG) / COUNT(*), 0), 1) FROM TZ_SULEACH	WHERE SUBJ = A.SUBJ AND GRCODE = A.GRCODE) AS SUL_AVG\n");
            headSql.append("	,(SELECT INTRODUCEFILENAMENEW FROM TZ_SUBJ WHERE SUBJ = A.SUBJ ) AS INTRODUCEFILENAMENEW\n");

            bodySql.append("   FROM VZ_SCSUBJSEQ A\n");
            bodySql.append("  WHERE A.GRCODE = " + SQLString.Format(v_grcode));
            bodySql.append("\n\t AND A.ISUSE = 'Y'");
            bodySql.append("\n\t AND TO_CHAR(SYSDATE,'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND ");
            bodySql.append("\n\t AND SUBJVISIBLE = 'Y' ");
            bodySql.append("\n\t AND A.SPECIALS NOT IN ('NNN') ");

            orderSql.append("\n ORDER BY A.COURSE, A.SCUPPERCLASS, A.SCMIDDLECLASS, A.SUBJNM ");

            countSql.append(" SELECT COUNT(*)\n " + bodySql.toString());

            int total_row_count = BoardPaging.getTotalRow(connMgr, countSql.toString()); //     전체 row 수를 반환한다

            if (total_row_count > 0) {
                Random ran = new Random();

                StringBuffer bodySql2 = new StringBuffer();
                bodySql2.append("\n\t WHERE ROWNO IN (");

                int iLen = total_row_count > 4 ? 4 : total_row_count;
                int iCnt = 0;

                for (int i = 0; i < iLen; i++) {
                    int ranCnt = 0;
                    if (ran.nextInt(total_row_count) == 0)
                        ranCnt = 1;
                    else
                        ranCnt = ran.nextInt(total_row_count);

                    if (ranCnt != 0) {
                        if (iCnt != 0)
                            bodySql2.append(" , ");
                        bodySql2.append(ranCnt);
                        iCnt++;
                    }
                }
                bodySql2.append(" )");

                sql.append(" SELECT * FROM ( " + headSql.toString() + bodySql.toString() + ") " + bodySql2.toString());

            } else {
                sql.append(headSql.toString() + bodySql.toString());
            }

            ls1 = connMgr.executeQuery(sql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
     * 알림판 인기과정 가져오기 (통합 메인)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectMainPopSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list = null;

        // StringBuffer sql = new StringBuffer();
        StringBuffer headSql = new StringBuffer();

        DataBox dbox = null;

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            headSql.append("select * from (\n");
            headSql.append("select u.*,rownum as a1 from (\n");
            // 수강신청 가능한 과정
            headSql.append(" SELECT A.SUBJ, A.SUBJSEQ, A.GRSEQ, A.SUBJNM, A.SCYEAR, A.ISONOFF, A.SCUPPERCLASS, A.SCMIDDLECLASS, A.SCLOWERCLASS, USEBOOK,\n");
            headSql
                    .append("		TO_CHAR(TO_DATE(A.PROPSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') PROPSTART, TO_CHAR(TO_DATE(A.PROPEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') PROPEND, TO_CHAR(TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') EDUSTART, TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') EDUEND, A.SCBIYONG, A.BIYONG, A.PREURL, A.ISUNIT, A.STUDENTLIMIT,\n");
            headSql.append("	SUBSTRING(A.SPECIALS,1,1) ISNEW, SUBSTRING(A.SPECIALS,2,1) ISHIT, SUBSTRING(A.SPECIALS,3,1) ISRECOM,\n");
            headSql.append("	(SELECT CLASSNAME FROM TZ_SUBJATT X\n");
            headSql.append("	WHERE X.UPPERCLASS = A.SCUPPERCLASS AND X.MIDDLECLASS  = '000' AND X.LOWERCLASS = '000' ) UCLASSNM,\n");
            headSql.append("	(SELECT CLASSNAME FROM TZ_SUBJATT X\n");
            headSql.append("	WHERE X.UPPERCLASS = A.SCUPPERCLASS AND X.MIDDLECLASS = A.SCMIDDLECLASS AND X.LOWERCLASS = '000' ) MCLASSNM,\n");
            headSql.append("	A.COURSE, A.COURSESEQ, A.COURSENM, A.ISBELONGCOURSE, A.SUBJCNT,\n");
            headSql.append("	(SELECT ROUND( NVL(SUM(DISTCODE1_AVG) / COUNT(*), 0), 1) FROM TZ_SULEACH	WHERE SUBJ = A.SUBJ AND GRCODE = A.GRCODE) AS SUL_AVG\n");
            headSql.append("	,(SELECT INTRODUCEFILENAMENEW FROM TZ_SUBJ WHERE SUBJ = A.SUBJ ) AS INTRODUCEFILENAMENEW\n");
            headSql.append("	,(select intro from tz_subj z where z.subj=a.subj) as intro, mobile_preurl \n");
            //            headSql.append("	,(SELECT MAX(GRSEQ) AS GRSEQ FROM TZ_GRSEQ WHERE GRCODE = 'N000001'\n");
            //            headSql.append("	    AND GYEAR = TO_CHAR(SYSDATE, 'YYYY') AND HOMEPAGEYN = 'Y' ) AS PGRSEQ, MOBILE_PREURL \n");
            headSql.append("   FROM VZ_SCSUBJSEQIMGMOBILE A\n");
            headSql.append("  WHERE A.GRCODE = " + SQLString.Format(v_grcode));
            headSql.append("\n\t AND A.ISUSE = 'Y'");
            headSql.append("\n\t AND TO_CHAR(SYSDATE,'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND ");
            //          headSql.append("\n\t AND TO_CHAR(ADD_MONTHS(SYSDATE,-1),'yyyymm')=SUBSTR(PROPSTART,0,6) ");
            headSql.append("\n\t AND SUBJVISIBLE = 'Y' ");
            //			headSql.append("\n\t AND A.SCYEAR=TO_CHAR(SYSDATE,'YYYY') ");
            //			headSql.append("\n\t AND A.SPECIALS LIKE '_Y_' ");
            headSql.append("\n ORDER BY hitnumber desc");
            headSql.append(" )u \n");
            //            headSql.append(" WHERE GRSEQ = PGRSEQ \n");
            headSql.append(" )p where a1<6");

            ls1 = connMgr.executeQuery(headSql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, headSql.toString());
            throw new Exception("sql1 = " + headSql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
     * 게임제작 온라인 - 메인
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectAreaSubjectList(RequestBox box, String area) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        // 사이트의 GRCODE 로 과정 리스트
        // String v_grcode = box.getSession("tem_grcode");

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            // 수강신청 가능한 과정

            sql.append(" SELECT * FROM ( ");
            sql.append(" SELECT * from VZ_SCSUBJSEQIMGMOBILE where  to_char(sysdate,'YYYYMM') between replace(substr(propstart,1,6),'.','') \n");
            sql.append(" and replace(substr(propend,1,6),'.','') ");
            sql.append(" and grcode ='N000001' and isuse ='Y' and area = " + SQLString.Format(area));
            sql.append(" ORDER BY dbms_random.value ) where rownum < 7 ");

            ls1 = connMgr.executeQuery(sql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
     * 오프라인 과정 - 메인
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOffSubjectList(RequestBox box, String area) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            // 수강신청 가능한 과정

            sql.append(" SELECT * FROM ( ");
            sql.append(" select a.subj, a.edustart,a.eduend,a.subjnm, a.subjseq, a.year,a.seq, 'OFF' as isonoff, ");
            sql.append(" CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND THEN 'Y' WHEN A.PROPSTART > TO_CHAR(SYSDATE, 'YYYYMMDDHH24') THEN 'W'  ELSE 'N' END STATUS, ");
            sql.append(" (case when b.area='B0' then '01' when b.area='G0' then '02' else '03' end) as scupperclass  from tz_offsubjseq a, tz_offsubj b \n");
            sql.append(" where a.subj = b.subj  ");
            //sql.append(" where a.subj = b.subj  and to_char(sysdate,'YYYYMM') between substr(a.propstart,1,6) and substr(a.propend,1,6) ");
            sql.append(" and a.isuse = 'Y' and a.isvisible ='Y' and b.middleclass = " + SQLString.Format(area));
            sql.append(" ORDER BY STATUS desc, a.edustart desc ) where rownum < 7 ");

            ls1 = connMgr.executeQuery(sql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
     * 메인에서 수강과정 온라인 목록 5개 가져오는 쿼리
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectMainFiveSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.sync("p_order");

        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            // 수강신청 가능한 과정
            sql1 = " select \n";
            sql1 += "  subj,year,subjseq,subjnm,biyong,studentlimit,intro,area_nm \n";
            sql1 += "from (  \n";
            sql1 += "    select\n";
            sql1 += "            a.subj,a.year,a.subjseq,b.subjnm,a.biyong,a.studentlimit,b.intro,\n";
            sql1 += "            (case when b.area='B0' then '방송' \n";
            sql1 += "                  when b.area='G0' then '게임' else '문콘' end) as area_nm,\n";
            sql1 += "            rownum as a1\n";
            sql1 += "    from tz_subjseq a\n";
            sql1 += "    left join tz_subj b on a.subj=b.subj\n";
            sql1 += "    where substr(a.PROPSTART,0,6)=to_char(sysdate,'yyyymm') and substr(PROPEND,0,6)=to_char(sysdate,'yyyymm')\n";
            sql1 += "      and a.grcode	   = " + SQLString.Format(v_grcode);
            sql1 += "      and b.isuse		= 'Y'";
            sql1 += "    order by b.hitnumber desc \n";
            sql1 += ")x  \n";
            sql1 += "where a1<7  \n";

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataList(connMgr, sql1, box, true);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    public ArrayList<DataBox> selectProfSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list = null;

        // StringBuffer sql = new StringBuffer();
        StringBuffer headSql = new StringBuffer();

        DataBox dbox = null;

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");
        String v_area = box.getString("p_area");
        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            headSql.append(" SELECT A.SUBJ, A.SUBJSEQ, A.SUBJNM, A.SCYEAR, A.ISONOFF, A.SCUPPERCLASS, A.SCMIDDLECLASS, A.SCLOWERCLASS, USEBOOK,\n");
            headSql
                    .append("		TO_CHAR(TO_DATE(A.PROPSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') PROPSTART, TO_CHAR(TO_DATE(A.PROPEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') PROPEND, TO_CHAR(TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') EDUSTART, TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') EDUEND, A.SCBIYONG, A.BIYONG, A.PREURL, A.ISUNIT, A.STUDENTLIMIT,\n");
            headSql.append("	SUBSTRING(A.SPECIALS,1,1) ISNEW, SUBSTRING(A.SPECIALS,2,1) ISHIT, SUBSTRING(A.SPECIALS,3,1) ISRECOM,\n");
            headSql.append("	(SELECT CLASSNAME FROM TZ_SUBJATT X\n");
            headSql.append("	WHERE X.UPPERCLASS = A.SCUPPERCLASS AND X.MIDDLECLASS  = '000' AND X.LOWERCLASS = '000' ) UCLASSNM,\n");
            headSql.append("	(SELECT CLASSNAME FROM TZ_SUBJATT X\n");
            headSql.append("	WHERE X.UPPERCLASS = A.SCUPPERCLASS AND X.MIDDLECLASS = A.SCMIDDLECLASS AND X.LOWERCLASS = '000' ) MCLASSNM,\n");
            headSql.append("	A.COURSE, A.COURSESEQ, A.COURSENM, A.ISBELONGCOURSE, A.SUBJCNT,\n");
            headSql.append("	(SELECT ROUND( NVL(SUM(DISTCODE1_AVG) / COUNT(*), 0), 1) FROM TZ_SULEACH	WHERE SUBJ = A.SUBJ AND GRCODE = A.GRCODE) AS SUL_AVG,\n");
            headSql.append("	(case when a.area='B0' then '방송' when a.area='G0' then '게임' else '문콘' end) as area_nm \n");
            headSql.append("   FROM VZ_SCSUBJSEQ A\n");
            headSql.append("  WHERE A.GRCODE = " + SQLString.Format(v_grcode));
            headSql.append("\n\t AND A.grseq = '0204'");
            headSql.append("\n\t AND A.year = '2008'");
            //                headSql.append("\n\t AND A.ISUSE = 'Y'");
            //                headSql.append("\n\t AND TO_CHAR(SYSDATE,'YYYYMMDDHH24') BETWEEN A.EDUSTART AND A.EDUEND ");
            //                headSql.append("\n\t AND TO_CHAR(ADD_MONTHS(SYSDATE,-1),'yyyymm')=SUBSTR(PROPSTART,0,6) ");
            //                headSql.append("\n\t AND SUBJVISIBLE = 'Y' ");

            if (!v_area.equals("T") && !v_area.equals(""))
                headSql.append("\n\t AND a.area = '" + v_area + "' ");

            ls1 = connMgr.executeQuery(headSql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, headSql.toString());
            throw new Exception("sql1 = " + headSql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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

    public ArrayList<DataBox> selectSubjectList_Asp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        // String v_gadmin = box.getSession("gadmin");

        //String p_type = box.get("type") + "01";
        // String v_upperclass = box.get("p_upperclass");
        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.sync("p_order");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");
        // String p_order = box.getString("p_order");
        String tabid = box.getStringDefault("tabid", "0");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select distinct a.subj, a.subjnm, a.biyong, a.scupperclass, a.isonoff, a.wj_classkey, \n";
            sql1 += "a.subjseq, a.scyear, case when a.subjseq IS NOT NULL then 'Y' else 'N' end propyn, \n";  // 추가
            sql1 += "(select classname from tz_subjatt x  \n";
            sql1 += "where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,  \n";
            sql1 += "(select classname from tz_subjatt x  \n";
            sql1 += "where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,  \n";
            sql1 += "(select classname from tz_subjatt x  \n";
            sql1 += "where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.LOWERCLASS = a.SCLOWERCLASS ) lclassnm,  \n";
            sql1 += "a.area, a.areaname,  \n";
            sql1 += "TO_NUMBER((select max(lesson) from TZ_SUBJLESSON z where a.subj=z.subj)) as chisi \n";
            
            if (v_grcode.equals("N000134")) {
            	sql1 += "   , case when subj in ('CK10074','CK18005','CK11004') then 1 else 2 end ordersec \n ";
            }
            
            sql1 += "from VZ_SCSUBJSEQ a  \n";
            sql1 += "	LEFT JOIN TZ_GRSEQ b ON a.grcode = b.grcode AND a.gyear = b.gyear AND a.grseq = b.grseq \n ";
            sql1 += "where a.grcode = " + SQLString.Format(v_grcode) + "\n";
            sql1 += "  and a.isuse  = 'Y'  \n";
            sql1 += "  and a.subjvisible = 'Y'  \n";
            sql1 += "  and b.homepageyn = 'Y' \n"; // 추가

            if (tabid.equals("3")) {
                sql1 += "  and a.area  = 'K0' \n"; //문화
            } else if (tabid.equals("1")) {
                sql1 += "  and a.area  = 'G0' \n"; //게임
            } else if (tabid.equals("2")) {
                sql1 += "  and a.area  = 'B0' \n"; //방송
            } else if (tabid.equals("4")) {
                sql1 += "  and a.area  = 'W0' \n"; //교양
            }
            
            sql1 += " and to_date(a.SCEDUEND, 'yyyymmddhh24') > sysdate  \n";
            
            if (v_grcode.equals("N000134")) {
            	sql1 += "order by ordersec \n ";
            }

            /*
             * if(v_grcode.equals("N000022")) //문화체육관광부 sql1 +=
             * "  and a.grseq  = '0004' \n";
             */
            //sql1 += "order by scupperclass,scmiddleclass,SCLOWERCLASS";

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);
            DataBox dbox = null;

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 2013 수강과정 리스트 (온라인)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectSubjectListRe(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        // ListSet ls_chasi = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list_chasi = null;
        String sql1 = "";
        // String sql_chasi = "";
        String count_sql = "";
        /* 페이지링 변수 */
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.sync("p_order");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_searchtext = box.getStringDefault("p_searchtext", "ALL");
        String v_search = box.getStringDefault("p_search", "NAME");

        String v_gubun_1 = box.getStringDefault("p_gubun_1", "A");
        String v_gubun_2 = box.getStringDefault("p_gubun_2", "A");
        String v_gubun_3 = box.getStringDefault("p_gubun_3", "A");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            // list_chasi = new ArrayList();

            sql1 = "";
            sql1 += " /*   ProposeCourseBean.selectSubjectListRe-------수강과정 리스트 (온라인)------------*/    \n";

            if (!v_gubun_1.equals("A")) {
                sql1 += "SELECT AA.* FROM (\n";
            }

            // 수강신청 가능한 과정
            sql1 += " select distinct a.subj, a.introducefilenamenew, a.subjseq, a.subjnm, a.scyear,a.hitnumber, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, usebook, a.prewidth, a.preheight,a.propstart as ps,a.wj_classkey,a.mobile_preurl, \n";
            sql1 += " a.mobile_use_yn, (SELECT DECODE(COUNT(SUBJ), 0, 'false', 'true') FROM TZ_PROPOSE WHERE SUBJ=a.subj AND SUBJSEQ =a.subjseq AND YEAR = a.scyear AND USERID = ':s_userid') EXISTPROPOSE,\n";
            sql1 += "		TO_CHAR(TO_DATE(a.propstart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propstart, TO_CHAR(TO_DATE(a.propend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propend, TO_CHAR(TO_DATE(a.edustart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') edustart, TO_CHAR(TO_DATE(a.eduend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') eduend, a.scbiyong, a.biyong, a.preurl, a.isunit, A.STUDENTLIMIT,\n";
            sql1 += "	substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "    (select classname from tz_subjatt x\n";
            sql1 += "    where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.LOWERCLASS = a.SCLOWERCLASS ) lclassnm,\n";
            sql1 += "	a.course, a.courseseq, a.coursenm, a.isbelongcourse, a.subjcnt,a.area, a.areaname,\n";
            sql1 += "	(Select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	Where subj = a.subj and grcode = a.grcode) as sul_avg,\n";
            sql1 += "	case when to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend	then 'Y' else 'N' end propyn, INTRO, EDUURL, ISOUTSOURCING, CPSUBJ, CPSUBJSEQ, CONTENTTYPE  \n";
            sql1 += "  , (SELECT COMP FROM TZ_MEMBER WHERE USERID = " + SQLString.Format(v_userid) + " AND GRCODE = " + SQLString.Format(v_grcode) + " ) AS COMPANY \n";
            sql1 += "   from (select * from VZ_SCSUBJSEQIMGMOBILE where propstart not in (' ') and to_char(sysdate,'YYYYMM') between replace(substr(propstart,1,6),'.','') and replace(substr(propend,1,6),'.','')) a\n";
            //사이트기준인지
            sql1 += "  where a.grcode	   = " + SQLString.Format(v_grcode);
            sql1 += "\n\t and a.isuse		= 'Y'";
            sql1 += "\n\t and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend";
            sql1 += "\n and subj not in ( select subj from tz_subj where subj between 'CK14014' and 'CK14048' OR ( A.SUBJ BETWEEN 'CK15003' AND 'CK15030' ))";

            if (!v_searchtext.equals("ALL")) {
                if (v_search.equals("NAME")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                } else if (v_search.equals("CONT")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                }
            }

            sql1 += "\n order by a.subjnm,";
            sql1 += " a.hitnumber desc,";
            sql1 += " a.scupperclass, a.scmiddleclass";

            if (!v_gubun_1.equals("A")) {
                sql1 += ")AA, \n";
                sql1 += " (SELECT DISTINCT SUBJ FROM TZ_SUBJHOMEGUBUN WHERE GUBUN='GS'\n";
                sql1 += " AND GUBUN_1='" + v_gubun_1 + "'\n";
                if (!v_gubun_2.equals("A")) {
                    sql1 += " AND GUBUN_2='" + v_gubun_2 + "'\n";
                }
                if (!v_gubun_3.equals("A")) {
                    sql1 += " AND GUBUN_3='" + v_gubun_3 + "'\n";
                }
                sql1 += ") BB \n";
                sql1 += " WHERE AA.SUBJ = BB.SUBJ ";
            }
            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            DataBox dbox = null;

            count_sql = "select count(1) from( " + sql1 + ")";

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다
            ls1.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls1.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
                box.put("d_totalcount", new Integer(total_row_count));
                list1.add(dbox);
            }
            //list1 = ls1.getAllDataList(connMgr, sql1, box, true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 2013 수강과정 리스트 (온라인) - 직무별
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectSubjectListReJikmu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        // ListSet ls_chasi = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list_chasi = null;
        String sql1 = "";
        // String sql_chasi = "";
        String count_sql = "";
        /* 페이지링 변수 */
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.sync("p_order");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");

        String v_searchtext = box.getStringDefault("p_searchtext", "ALL");
        String v_search = box.getStringDefault("p_search", "NAME");

        String v_gubun_1 = box.getStringDefault("p_gubun_1", "A");
        String v_gubun_2 = box.getStringDefault("p_gubun_2", "A");
        String v_gubun_3 = box.getStringDefault("p_gubun_3", "A");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            // list_chasi = new ArrayList();

            sql1 = "";
            sql1 += " /*   ProposeCourseBean.selectSubjectListReJikmu-------직무별 수강과정 리스트-------*/    \n";

            if (!v_gubun_1.equals("A")) {
                sql1 += "SELECT AA.* FROM (\n";
            }

            // 수강신청 가능한 과정
            sql1 += " select distinct a.subj, a.introducefilenamenew, a.subjseq, a.subjnm, a.scyear,a.hitnumber, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, usebook, a.prewidth, a.preheight,a.propstart as ps,a.wj_classkey,nvl(a.mobile_use_yn,'N') as mobile_use_yn, \n";
            sql1 += " (SELECT DECODE(COUNT(SUBJ), 0, 'false', 'true') FROM TZ_PROPOSE WHERE SUBJ=a.subj AND SUBJSEQ =a.subjseq AND YEAR = a.scyear AND USERID = ':s_userid') EXISTPROPOSE,\n";
            sql1 += "		TO_CHAR(TO_DATE(a.propstart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propstart, TO_CHAR(TO_DATE(a.propend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propend, TO_CHAR(TO_DATE(a.edustart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') edustart, TO_CHAR(TO_DATE(a.eduend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') eduend, a.scbiyong, a.biyong, a.preurl, a.isunit, A.STUDENTLIMIT,\n";
            sql1 += "	substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "    (select classname from tz_subjatt x\n";
            sql1 += "    where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.LOWERCLASS = a.SCLOWERCLASS ) lclassnm,\n";
            sql1 += "	a.course, a.courseseq, a.coursenm, a.isbelongcourse, a.subjcnt,a.area, a.areaname,\n";
            sql1 += "	(Select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	Where subj = a.subj and grcode = a.grcode) as sul_avg,\n";
            sql1 += "	case when to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend	then 'Y' else 'N' end propyn, INTRO\n";
            sql1 += "   from (select * from VZ_SCSUBJSEQIMGMOBILE where propstart not in (' ') and to_char(sysdate,'YYYYMM') between replace(substr(propstart,1,6),'.','') and replace(substr(propend,1,6),'.','')) a\n";
            //사이트기준인지
            sql1 += "  where a.grcode = " + SQLString.Format(v_grcode);
            sql1 += "    AND A.SUBJ NOT IN (SELECT SUBJ FROM TZ_SUBJ WHERE SUBJ BETWEEN 'CK14014' AND 'CK14048' OR ( A.SUBJ BETWEEN 'CK15003' AND 'CK15030' ))\n";
            sql1 += "\n\t and a.isuse = 'Y'";
            sql1 += "\n\t and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend";

            if (!v_searchtext.equals("ALL")) {
                if (v_search.equals("NAME")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                } else if (v_search.equals("CONT")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                }
            }

            sql1 += "\n order by a.subjnm,";
            sql1 += " a.hitnumber desc,";
            sql1 += " a.scupperclass, a.scmiddleclass";

            if (!v_gubun_1.equals("A")) {
                sql1 += ")AA, \n";
                sql1 += " (SELECT DISTINCT SUBJ FROM TZ_SUBJHOMEGUBUN WHERE GUBUN='GM'\n";
                sql1 += " AND GUBUN_1='" + v_gubun_1 + "'\n";
                if (!v_gubun_2.equals("A")) {
                    sql1 += " AND GUBUN_3='" + v_gubun_2 + "'\n";
                }
                if (!v_gubun_3.equals("A")) {
                    sql1 += " AND GUBUN_4='" + v_gubun_3 + "'\n";
                }
                sql1 += ") BB \n";
                sql1 += " WHERE AA.SUBJ = BB.SUBJ ";
            }
            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            DataBox dbox = null;

            count_sql = "select count(1) from( " + sql1 + ")";

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다
            ls1.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls1.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
                box.put("d_totalcount", new Integer(total_row_count));
                list1.add(dbox);
            }
            //list1 = ls1.getAllDataList(connMgr, sql1, box, true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 2013 수강과정 리스트 (온라인) - 직업별
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectSubjectListReJikup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        // ListSet ls_chasi = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list_chasi = null;
        String sql1 = "";
        // String sql_chasi = "";
        String count_sql = "";
        /* 페이지링 변수 */
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        box.sync("s_subjnm");
        box.sync("s_specials");
        box.sync("s_subjnm");
        box.sync("s_mclassnm");
        box.sync("s_sclowerclass");
        box.sync("p_area");
        box.sync("p_order");

        // 사이트의 GRCODE 로 과정 리스트
        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getSession("userid");

        String v_searchtext = box.getStringDefault("p_searchtext", "ALL");
        String v_search = box.getStringDefault("p_search", "NAME");

        String v_gubun_1 = box.getStringDefault("p_gubun_1", "A");
        String v_gubun_2 = box.getStringDefault("p_gubun_2", "A");
        String v_gubun_3 = box.getStringDefault("p_gubun_3", "A");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            // list_chasi = new ArrayList();

            sql1 = "";
            sql1 += " /*   ProposeCourseBean.selectSubjectListReJikup-------직업별 수강과정 리스트 ()------------*/    \n";

            if (!v_gubun_1.equals("A")) {
                sql1 += "SELECT AA.* FROM (\n";
            }

            // 수강신청 가능한 과정
            sql1 += " select distinct a.subj, a.introducefilenamenew, a.subjseq, a.subjnm, a.scyear,a.hitnumber, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, usebook, a.prewidth, a.preheight,a.propstart as ps,a.wj_classkey,nvl(a.mobile_use_yn,'N') as mobile_use_yn, \n";
            sql1 += " (SELECT DECODE(COUNT(SUBJ), 0, 'false', 'true') FROM TZ_PROPOSE WHERE SUBJ=a.subj AND SUBJSEQ =a.subjseq AND YEAR = a.scyear AND USERID = ':s_userid') EXISTPROPOSE,\n";
            sql1 += "		TO_CHAR(TO_DATE(a.propstart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propstart, TO_CHAR(TO_DATE(a.propend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') propend, TO_CHAR(TO_DATE(a.edustart, 'YYYYMMDDHH24'), 'YYYY.MM.DD') edustart, TO_CHAR(TO_DATE(a.eduend, 'YYYYMMDDHH24'), 'YYYY.MM.DD') eduend, a.scbiyong, a.biyong, a.preurl, a.isunit, A.STUDENTLIMIT,\n";
            sql1 += "	substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,\n";
            sql1 += "	(select classname from tz_subjatt x\n";
            sql1 += "	where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm,\n";
            sql1 += "    (select classname from tz_subjatt x\n";
            sql1 += "    where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.LOWERCLASS = a.SCLOWERCLASS ) lclassnm,\n";
            sql1 += "	a.course, a.courseseq, a.coursenm, a.isbelongcourse, a.subjcnt,a.area, a.areaname,\n";
            sql1 += "	(Select round( NVL(sum(distcode1_avg) / count(*), 0), 1) From TZ_SULEACH	Where subj = a.subj and grcode = a.grcode) as sul_avg,\n";
            sql1 += "	case when to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend	then 'Y' else 'N' end propyn, INTRO, EDUURL, ISOUTSOURCING, CPSUBJ, CPSUBJSEQ, CONTENTTYPE  \n";
            sql1 += "  , (SELECT COMP FROM TZ_MEMBER WHERE USERID = " + SQLString.Format(v_userid) + " AND GRCODE = " + SQLString.Format(v_grcode) + " ) AS COMPANY \n";
            sql1 += "   from (select * from VZ_SCSUBJSEQIMGMOBILE where propstart not in (' ') and to_char(sysdate,'YYYYMM') between replace(substr(propstart,1,6),'.','') and replace(substr(propend,1,6),'.','')) a\n";
            //사이트기준인지
            sql1 += "  where a.grcode = " + SQLString.Format(v_grcode);
            sql1 += "    AND A.SUBJ NOT IN (SELECT SUBJ FROM TZ_SUBJ WHERE SUBJ BETWEEN 'CK14014' AND 'CK14048' OR ( A.SUBJ BETWEEN 'CK15003' AND 'CK15030' )) ";
            sql1 += "\n\t and a.isuse = 'Y'";
            sql1 += "\n\t and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend";

            if (!v_searchtext.equals("ALL")) {
                if (v_search.equals("NAME")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                } else if (v_search.equals("CONT")) {
                    sql1 += " \n and upper(a.subjnm) like upper('%" + v_searchtext + "%')";
                }
            }

            sql1 += "\n order by a.subjnm,";
            sql1 += " a.hitnumber desc,";
            sql1 += " a.scupperclass, a.scmiddleclass";

            if (!v_gubun_1.equals("A")) {
                sql1 += ")AA, \n";
                sql1 += " (SELECT DISTINCT SUBJ FROM TZ_SUBJHOMEGUBUN WHERE GUBUN='GJ'\n";
                sql1 += " AND GUBUN_1||GUBUN_2='" + v_gubun_1 + "'\n";
                if (!v_gubun_2.equals("A")) {
                    sql1 += " AND GUBUN_3='" + v_gubun_2 + "'\n";
                }
                if (!v_gubun_3.equals("A")) {
                    sql1 += " AND GUBUN_4='" + v_gubun_3 + "'\n";
                }
                sql1 += ") BB \n";
                sql1 += " WHERE AA.SUBJ = BB.SUBJ ";
            }
            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            DataBox dbox = null;

            count_sql = "select count(1) from( " + sql1 + ")";

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다
            ls1.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls1.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
                box.put("d_totalcount", new Integer(total_row_count));
                list1.add(dbox);
            }
            //list1 = ls1.getAllDataList(connMgr, sql1, box, true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 구분 코드 가져오기 gubun_1 GS : 0110, GM : 0113
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjhomegubun_1(RequestBox box, String gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            if (!gubun.endsWith("GJ")) {
                sql1 = "SELECT CODE, CODENM FROM TZ_CODE WHERE GUBUN='0110' ORDER BY CODE";
            } else {
                sql1 = "";
                sql1 += "SELECT 'A' AS CODE, '전체' AS CODENM FROM DUAL \n";
                sql1 += "UNION ALL \n";
                sql1 += "SELECT   DISTINCT \n";
                sql1 += " A.GUBUN_1 || A.GUBUN_2 AS CODE, \n";
                sql1 += "    (SELECT   CODENM \n";
                sql1 += "       FROM   TZ_CODE \n";
                sql1 += "      WHERE   GUBUN = '0110' AND CODE = A.GUBUN_1) \n";
                sql1 += " || '_' \n";
                sql1 += " || (SELECT   CODENM \n";
                sql1 += "       FROM   TZ_CODE \n";
                sql1 += "      WHERE   GUBUN = '0111' AND CODE = A.GUBUN_2) \n";
                sql1 += "   AS CODENM \n";
                sql1 += "  FROM   TZ_SUBJHOMEGUBUN A \n";
                sql1 += " WHERE   A.GUBUN = 'GJ' \n";
            }

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataListNoPage(connMgr, sql1, box, true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 구분 코드 가져오기 gubun_2 /코드 - 0111
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjhomegubun_2(RequestBox box, String gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        // String v_gubun = "0111";

        String v_gubun_1 = box.getStringDefault("p_gubun_1", "A");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select * from dual where 1=0";

            if (!v_gubun_1.equals("A")) {
                if (gubun.equals("GS")) {
                    sql1 = "SELECT   CODE, CODENM \n";
                    sql1 += "    FROM   TZ_CODE \n";
                    sql1 += "   WHERE   GUBUN = '0111' \n";
                    sql1 += "           AND CODE IN (SELECT   DISTINCT GUBUN_2 \n";
                    sql1 += "                          FROM   TZ_SUBJHOMEGUBUN \n";
                    sql1 += "                         WHERE   GUBUN = 'GS' AND GUBUN_1 = '" + v_gubun_1 + "') \n";
                    sql1 += "ORDER BY   CODE \n";
                } else if (gubun.equals("GM")) {
                    sql1 = "SELECT   DISTINCT \n";
                    sql1 += " A.GUBUN_3 AS CODE, \n";
                    sql1 += "    (SELECT   CODENM \n";
                    sql1 += "       FROM   TZ_CODE \n";
                    sql1 += "      WHERE   GUBUN = '0111' AND CODE = A.GUBUN_2) \n";
                    sql1 += " || '_' \n";
                    sql1 += " || (SELECT   CODENM \n";
                    sql1 += "      FROM   TZ_CODE \n";
                    sql1 += "      WHERE   GUBUN = '0114' AND CODE = A.GUBUN_3) \n";
                    sql1 += "    AS CODENM \n";
                    sql1 += "FROM   TZ_SUBJHOMEGUBUN A \n";
                    sql1 += "WHERE   A.GUBUN = 'GM' AND GUBUN_1 = '" + v_gubun_1 + "' \n";
                } else if (gubun.equals("GJ")) {
                    sql1 = "SELECT   CODE, CODENM \n";
                    sql1 += "    FROM   TZ_CODE \n";
                    sql1 += "   WHERE   GUBUN = '0116' \n";
                    sql1 += "           AND CODE IN (SELECT   DISTINCT GUBUN_3 \n";
                    sql1 += "                          FROM   TZ_SUBJHOMEGUBUN \n";
                    sql1 += "                         WHERE   GUBUN = 'GJ' AND GUBUN_1||GUBUN_2 = '" + v_gubun_1 + "') \n";
                    sql1 += "ORDER BY   CODE \n";
                }
            }

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataListNoPage(connMgr, sql1, box, true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 구분 코드 가져오기 gubun_3 /코드 - 0112
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjhomegubun_3(RequestBox box, String gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_gubun_1 = box.getStringDefault("p_gubun_1", "A");
        String v_gubun_2 = box.getStringDefault("p_gubun_2", "A");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select * from dual where 1=0";

            if (!v_gubun_1.equals("A") && !v_gubun_2.equals("A")) {
                if (gubun.equals("GS")) {
                    sql1 = "SELECT   CODE, CODENM \n";
                    sql1 += "    FROM   TZ_CODE \n";
                    sql1 += "   WHERE   GUBUN = '0112' \n";
                    sql1 += "           AND CODE IN (SELECT   DISTINCT GUBUN_3 \n";
                    sql1 += "                          FROM   TZ_SUBJHOMEGUBUN \n";
                    sql1 += "                         WHERE   GUBUN = 'GS' AND GUBUN_1 = '" + v_gubun_1 + "' AND GUBUN_2 = '" + v_gubun_2 + "') \n";
                    sql1 += "ORDER BY   CODE \n";
                } else if (gubun.equals("GM")) {
                    sql1 = "SELECT   CODE, CODENM  \n";
                    sql1 += "FROM   TZ_CODE  \n";
                    sql1 += "WHERE   GUBUN = '0115'  \n";
                    sql1 += "       AND CODE IN (SELECT   DISTINCT GUBUN_4  \n";
                    sql1 += "                      FROM   TZ_SUBJHOMEGUBUN  \n";
                    sql1 += "                     WHERE   GUBUN = 'GM' AND GUBUN_1 = '" + v_gubun_1 + "' AND GUBUN_3 = '" + v_gubun_2 + "')  \n";
                    sql1 += "ORDER BY   CODE  \n";
                } else if (gubun.equals("GJ")) {
                    sql1 = "SELECT   CODE, CODENM  \n";
                    sql1 += "FROM   TZ_CODE  \n";
                    sql1 += "WHERE   GUBUN = '0115'  \n";
                    sql1 += "       AND CODE IN (SELECT   DISTINCT GUBUN_4  \n";
                    sql1 += "                      FROM   TZ_SUBJHOMEGUBUN  \n";
                    sql1 += "                     WHERE   GUBUN = 'GJ' AND GUBUN_1||GUBUN_2 = '" + v_gubun_1 + "' AND GUBUN_3 = '" + v_gubun_2 + "')  \n";
                    sql1 += "ORDER BY   CODE  \n";
                }
            }

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataListNoPage(connMgr, sql1, box, true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 구분 코드 가져오기 gubun_4 /코드 - 0113
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjhomegubun_4(RequestBox box, String gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        String v_gubun = "0113";

        String v_gubun_1 = box.getStringDefault("p_gubun_1", "A");
        String v_gubun_2 = box.getStringDefault("p_gubun_2", "A");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select * from dual where 1=0";

            if (!v_gubun_1.equals("A") && !v_gubun_2.equals("A")) {
                sql1 = "SELECT   CODE, CODENM \n";
                sql1 += "    FROM   TZ_CODE \n";
                sql1 += "   WHERE   GUBUN = '" + v_gubun + "' \n";
                sql1 += "           AND CODE IN (SELECT   DISTINCT GUBUN_3 \n";
                sql1 += "                          FROM   TZ_SUBJHOMEGUBUN \n";
                sql1 += "                         WHERE   GUBUN = '" + gubun + "' AND GUBUN_1 = '" + v_gubun_1 + "' AND GUBUN_2 = '" + v_gubun_2 + "') \n";
                sql1 += "ORDER BY   CODE \n";
            }

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            list1 = ls1.getAllDataListNoPage(connMgr, sql1, box, true);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 과정상세보기
     *
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox selectSubjInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        String v_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  SUBJ    \n");
            sql.append("    ,   SUBJNM  \n");
            sql.append("    ,   YEAR    \n");
            sql.append("    ,   SUBJSEQ \n");
            sql.append("    ,   GRCODE  \n");
            sql.append("  FROM  TZ_SUBJSEQ \n");
            sql.append(" WHERE  GRCODE = 'N000001' \n");
            sql.append("   AND  TO_CHAR(SYSDATE,'YYYYMMDDHH') BETWEEN PROPSTART AND PROPEND \n");
            sql.append("   AND  SUBJ = ").append(SQLString.Format(v_subj));

            ls1 = connMgr.executeQuery(sql.toString());

            if (ls1.next()) {
                dbox = ls1.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * 연관 과정 목록을 조회한다. 연관 과정이란 수강 신청을 해 놓은 과정을 의미한다. 20141015
     *
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectNextProposeSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String grcode = box.getSession("grcode");
        String subj = box.getString("subj");
        subj = (subj.equals("")) ? box.getString("p_subj") : subj;
        String searchNm = "";
        String[] searchNmArr = null;
        int rnkLimit = 0;

        grcode = grcode.equals("") ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  SEARCH_NM   \n");
            sql.append("  FROM  TZ_SUBJ     \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'  \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                searchNm = ls.getString("search_nm");
            }

            ls.close();
            ls = null;

            if (searchNm != null && !searchNm.equals("")) {

                searchNmArr = searchNm.split(",");
                rnkLimit = (searchNmArr.length < 3) ? searchNmArr.length - 1 : 2;

                list = new ArrayList<DataBox>();

                sql.setLength(0);
                sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean selectNextProposeSubjList (과정별 연관과정 웹 목록 조회) */\n");
                sql.append("SELECT  *   \n");
                sql.append("  FROM  (   \n");
                sql.append("        SELECT  A.SUBJ      \n");
                sql.append("            ,   A.YEAR      \n");
                sql.append("            ,   A.SUBJSEQ   \n");
                sql.append("            ,   C.SUBJNM    \n");
                sql.append("            ,   C.INTRODUCEFILENAMENEW                                                      \n");
                sql.append("            ,   C.MOBILE_USE_YN                                                             \n");
                sql.append("            ,   C.INTRO                                                                     \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.PROPSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPSTART   \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.PROPEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPEND       \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART     \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND         \n");

                for (int i = 0; i < searchNmArr.length; i++) {
                    sql.append("            ,   CASE WHEN INSTR (C.SEARCH_NM, '").append(searchNmArr[i]).append("') > 0 THEN 1 ELSE 0 END RNK").append(i).append(" \n");
                }

                sql.append("          FROM  TZ_SUBJSEQ A                                                                \n");
                sql.append("            ,   TZ_SUBJ C                                                                   \n");
                sql.append("         WHERE  A.GRCODE = '").append(grcode).append("'                                     \n");
                sql.append("           AND  C.SUBJ <> '").append(subj).append("'                                        \n");
                sql.append("           AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND          \n");
                sql.append("           AND  A.SUBJ = C.SUBJ                                                             \n");

                sql.append("           AND  (       \n");
                for (int i = 0; i < searchNmArr.length; i++) {
                    if (i == 0) {
                        sql.append("                C.SEARCH_NM LIKE '%' || '").append(searchNmArr[i]).append("' || '%' \n");
                    } else {
                        sql.append("                OR C.SEARCH_NM LIKE '%' || '").append(searchNmArr[i]).append("' || '%' \n");
                    }
                }
                sql.append("                )   \n");
                sql.append("    )   \n");
                sql.append(" WHERE  ( ");

                for (int i = 0; i < searchNmArr.length; i++) {
                    if (i < searchNmArr.length - 1) {
                        sql.append("RNK").append(i).append(" + ");
                    } else {
                        sql.append("RNK").append(i);
                    }
                }
                sql.append(" ) > ").append(rnkLimit).append("   \n");

                sql.append(" ORDER  BY  ");
                for (int i = 0; i < searchNmArr.length; i++) {
                    if (i == 0) {
                        sql.append(" RNK").append(i);
                    } else {
                        sql.append(" || RNK").append(i);
                    }
                }
                sql.append(" DESC   \n");

                //            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean selectNextProposeSubjList (과정별 다음과정 목록 조회) */\n");
                //            sql.append("SELECT  A.SUBJ                                                                      \n");
                //            sql.append("    ,   C.SUBJNM                                                                    \n");
                //            sql.append("    ,   C.INTRODUCEFILENAMENEW                                                      \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.PROPSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPSTART   \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.PROPEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPEND       \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART     \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND         \n");
                //            sql.append("  FROM  TZ_SUBJSEQ A                                                                \n");
                //            sql.append("    ,   TZ_PROPOSE B                                                                \n");
                //            sql.append("    ,   TZ_SUBJ C                                                                   \n");
                //            sql.append(" WHERE  B.USERID = '").append(userid).append("'                                     \n");
                //            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND          \n");
                //            sql.append("   AND  A.YEAR = B.YEAR                                                             \n");
                //            sql.append("   AND  A.SUBJ = B.SUBJ                                                             \n");
                //            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ                                                       \n");
                //            sql.append("   AND  A.SUBJ = B.SUBJ                                                             \n");
                //            sql.append("   AND  A.SUBJ = C.SUBJ                                                             \n");

                //            pstmt = connMgr.prepareStatement(sql.toString());
                //            pstmt.setString(1, userid);
                //
                //            ls = new ListSet(pstmt);

                ls = connMgr.executeQuery(sql.toString());

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    list.add(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 2014-10-21 휴넷에서 제공받은 인문학 강좌 메뉴를 보여주기 위해 각 메뉴를 클릭할 때 마다 카운트를 구한 후 리턴한다. 리턴
     * 페이지에서 카운트가 0이면 메뉴가 보이지 않고 0 이상이면 메뉴가 표시된다.
     *
     * @return
     * @throws Exception
     */
    public int getLiteratureSubjectCount() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int subjCnt = 0;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  COUNT(SUBJ) AS SUBJ_CNT                             \n");
            sql.append("  FROM  TZ_SUBJSEQ                                          \n");
            sql.append(" WHERE  ( SUBJ BETWEEN 'CK14014' AND 'CK14048'  OR ( SUBJ BETWEEN 'CK15003' AND 'CK15030' ) )             \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN PROPSTART  \n");
            sql.append("                                             AND PROPEND    \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                subjCnt = ls.getInt("subj_cnt");
            }

            ls.close();
            ls = null;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }

        return subjCnt;
    }

    /**
     * 인문학 강좌 목록을 조회한다.
     *
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectLiteratureSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String grcode = box.getSession("tem_grcode");
        String userid = box.getSession("userid");
        grcode = grcode.equals("") ? "N000001" : grcode;

        int pageSize = box.getInt("p_pagesize");
        int pageNo = box.getInt("p_pageno");
        int startNum = 0, endNum = 0;
        int totalPage = 0;
        int totalRowCount = 0;

        pageSize = (pageSize == 0) ? 10 : pageSize;
        pageNo = (pageNo == 0) ? 1 : pageNo;
        startNum = (pageNo - 1) * pageSize + 1;
        endNum = startNum + pageSize - 1;

        String searchText = box.getString("p_searchtext");

        try {
            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            sql.append("SELECT  *   \n");
            sql.append("  FROM  (   \n");
            sql.append("        SELECT  A.SUBJ      \n");
            sql.append("            ,   A.SUBJNM    \n");
            sql.append("            ,   A.INTRO     \n");
            sql.append("            ,   A.PREURL    \n");
            sql.append("            ,   A.PREWIDTH  \n");
            sql.append("            ,   A.PREHEIGHT \n");
            sql.append("            ,   A.INTRODUCEFILENAMENEW  \n");
            sql.append("            ,   A.ISONOFF       \n");
            sql.append("            ,   A.UPPERCLASS    \n");
            sql.append("            ,   (   \n");
            sql.append("                SELECT  CLASSNAME   \n");
            sql.append("                  FROM  TZ_SUBJATT  \n");
            sql.append("                 WHERE  UPPERCLASS = A.UPPERCLASS   \n");
            sql.append("                   AND  MIDDLECLASS = A.MIDDLECLASS \n");
            sql.append("                   AND  LOWERCLASS = A.LOWERCLASS   \n");
            sql.append("                ) AS UPPERCLASSNM   \n");
            sql.append("            ,   SUBSTRING(A.SPECIALS, 1, 1) AS ISNEW    \n");
            sql.append("            ,   SUBSTRING(A.SPECIALS, 2, 1) AS ISHIT    \n");
            sql.append("            ,   SUBSTRING(A.SPECIALS, 3, 1) AS ISRECOM  \n");
            sql.append("            ,   B.YEAR      \n");
            sql.append("            ,   B.SUBJSEQ   \n");
            sql.append("            ,   TO_CHAR(TO_DATE(B.PROPSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS PROPSTART    \n");
            sql.append("            ,   TO_CHAR(TO_DATE(B.PROPEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS PROPEND        \n");
            sql.append("            ,   TO_CHAR(TO_DATE(B.EDUSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS EDUSTART      \n");
            sql.append("            ,   TO_CHAR(TO_DATE(B.EDUEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS EDUEND          \n");
            sql.append("            ,   B.GYEAR     \n");
            sql.append("            ,   DECODE(NVL(C.USERID, ''), '', 'N', 'Y') AS EXIST_PROP   \n");
            sql.append("            ,   CASE WHEN TO_CHAR(SYSDATE,'YYYYMMDDHH24') BETWEEN B.PROPSTART AND B.PROPEND THEN 'Y'    \n");
            sql.append("                ELSE 'N'        \n");
            sql.append("                END AS PROPYN   \n");
            sql.append("            ,   COUNT(A.SUBJ) OVER() AS TOT_CNT \n");
            sql.append("            ,   RANK() OVER(ORDER BY A.SUBJ DESC ) AS RNK    \n");
            sql.append("            ,   A.EDUURL, A.ISOUTSOURCING, A.CPSUBJ, B.CPSUBJSEQ, A.CONTENTTYPE    \n");
            sql.append("            , (SELECT COMP FROM TZ_MEMBER WHERE USERID = '").append(userid).append("' AND GRCODE ='").append(grcode).append("') AS COMPANY \n");
            sql.append("          FROM  TZ_SUBJ A       \n");
            sql.append("            ,   TZ_SUBJSEQ B    \n");
            sql.append("            ,   TZ_PROPOSE C    \n");
            sql.append("         WHERE  ( A.SUBJ BETWEEN 'CK14014' AND 'CK14048'  OR ( A.SUBJ BETWEEN 'CK15003' AND 'CK15030' ) )  \n");
            sql.append("           AND  B.GRCODE = '").append(grcode).append("' \n");
            sql.append("           AND  A.SUBJ = B.SUBJ \n");
            sql.append("           AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN B.PROPSTART AND B.PROPEND  \n");
            sql.append("           AND  B.SUBJ = C.SUBJ(+)  \n");
            sql.append("           AND  B.YEAR = C.YEAR(+)  \n");
            sql.append("           AND  B.SUBJSEQ = C.SUBJSEQ(+)    \n");
            sql.append("           AND  '").append(userid).append("' = C.USERID(+)    \n");
            if (searchText != null && !searchText.equals("")) {
                sql.append("           AND  A.SUBJNM LIKE '%").append(searchText).append("%'    \n");
            }
            sql.append("         ORDER  BY SUBJ \n");
            sql.append("        )   \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY SUBJ DESC \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                totalRowCount = ls.getInt("tot_cnt");
                totalPage = (int) (totalRowCount / pageSize) + 1;
                ls.moveFirst();
            }

            int dispNum = totalRowCount - ((pageNo - 1) * pageSize);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispNum", dispNum--);
                dbox.put("d_totalPage", totalPage);
                dbox.put("d_rowCount", row);
                dbox.put("d_totalRowCount", totalRowCount);

                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * 정규과정 후기 목록을 조회한다.
     *
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectReviewList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        String subj = box.getString("p_subj");
        String userid = box.getSession("userid");

        int pageSize = box.getInt("p_pagesize"); // 한 페이지에 표시되는 목록의 개수
        int pageNo = box.getInt("p_pageno"); // 현재의 페이지 번호
        int startNum = 0, endNum = 0; // 조회할 게시물의 rank 시작 번호와 종료 번호
        int totalPage = 0; // 전체 페이지 수
        int totalRowCount = 0; // 전체 목록 수
        int dispNum = 0; // 표시 번호

        int blockSize = 10; // 한 블록에 표시되는 페이지 수. 한 블록당 10개의 페이지 수 표시
        int blockCount = 0; // 페이지블록 개수
        int pageBlock = 0; // 현재 페이지의 블럭 번호
        int startPage = 0; // 블럭내 시작 페이지
        int endPage = 0; // 블럭내 종료 페이지

        pageSize = (pageSize == 0) ? 5 : pageSize;
        pageNo = (pageNo == 0) ? 1 : pageNo;
        startNum = (pageNo - 1) * pageSize + 1;
        endNum = startNum + pageSize - 1;

        try {
            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.propose.ProposeCourseBean selectReviewList (정규과정 후기 목록 조회) */\n");
            sql.append("SELECT  *   \n");
            sql.append("  FROM  (   \n");
            sql.append("        SELECT  A.SUBJ      \n");
            sql.append("            ,   A.NUM       \n");
            sql.append("            ,   A.CONTENTS  \n");
            sql.append("            ,   A.POINT     \n");
            sql.append("            ,   A.USERID    \n");
            sql.append("            ,   B.NAME      \n");
            sql.append("            ,   TO_CHAR(A.REG_DT, 'YYYY.MM.DD') AS REG_DT    \n");
            sql.append("            ,   RANK() OVER(ORDER BY DECODE(A.USERID, '").append(userid).append("', 1, 0) DESC, NUM DESC) AS RNK  \n");
            sql.append("            ,   COUNT(NUM) OVER() AS TOT_CNT                 \n");
            sql.append("          FROM  TZ_SUBJ_REVIEW A                             \n");
            sql.append("            ,   TZ_MEMBER B                                  \n");
            sql.append("         WHERE  A.SUBJ = '").append(subj).append("'         \n");
            sql.append("           AND  A.USERID = B.USERID                          \n");
            sql.append("        )   \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY RNK, NUM DESC \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                totalRowCount = ls.getInt("tot_cnt");
                ls.moveFirst();
            }

            totalPage = (int) (totalRowCount / pageSize);
            totalPage = (totalRowCount % pageSize == 0) ? totalPage : totalPage + 1;

            blockCount = (int) (totalPage / blockSize);
            blockCount = (totalPage % blockSize == 0) ? blockCount : blockCount + 1;

            pageBlock = (int) ((pageNo - 1) / blockSize) + 1;
            // pageBlock = (pageNo % blockSize == 0) ? pageBlock : pageBlock + 1;
            startPage = (int) (pageBlock - 1) * blockSize + 1;
            endPage = (int) pageBlock * blockSize;
            endPage = (endPage > totalPage) ? totalPage : endPage;

            dispNum = totalRowCount - ((pageNo - 1) * pageSize);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispNum", dispNum--);
                dbox.put("d_pageNo", pageNo);
                dbox.put("d_totalPage", totalPage);
                dbox.put("d_rowCount", row);
                dbox.put("d_totalRowCount", totalRowCount);
                dbox.put("d_blockSize", blockSize);
                dbox.put("d_blockCount", blockCount);
                dbox.put("d_pageBlock", pageBlock);
                dbox.put("d_startPage", startPage);
                dbox.put("d_endPage", endPage);

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * 전화번호 가져오기
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @return result 제약대상 - 메세지
     */
    public String getNumber(RequestBox box) throws Exception {
        ListSet ls1 = null;
        String sql1 = "";
        String result = "";

        String v_userid = box.getSession("userid");
        String grcode = box.getSession("tem_grcode");
        DBConnectionManager connMgr = null;

        try {
            connMgr = new DBConnectionManager();

            // 신청한 과정의 어학, 직무 구분
            sql1 = " select crypto.dec('normal', handphone) handphone from tz_member     \n";
            sql1 += " where grcode = " + SQLString.Format(grcode);
            sql1 += "   and userid = " + SQLString.Format(v_userid);

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("handphone");
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 전화번호 가져오기
     *
     * @param connMgr DBConnectionManager
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @param p_userid 유저아이디
     * @param p_comp 회사코드
     * @return result 제약대상 - 메세지
     */
    public int chkSubjDupl(RequestBox box) throws Exception {
        ListSet ls1 = null;
        String sql1 = "";
        int resultCnt = 0;

        String userid = box.getSession("userid");
        String subj = box.getString("p_subj");
        String subjseq = box.getString("p_subjseq");
        String year  = box.getString("p_year");

        DBConnectionManager connMgr = null;

        try {
            connMgr = new DBConnectionManager();

            // 신청한 과정의 어학, 직무 구분
            sql1 = " select count(*) cnt from tz_student     \n";
            sql1 += " where subj = " + SQLString.Format(subj);
            sql1 += "   and subjseq = " + SQLString.Format(subjseq);
            sql1 += "   and year = " + SQLString.Format(year);
            sql1 += "   and userid = " + SQLString.Format(userid);

            ls1 = connMgr.executeQuery(sql1.toString(), box);
            if (ls1.next()) {
                resultCnt = ls1.getInt("cnt");
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }
        return resultCnt;
    }
}
