//**********************************************************
//  1. 제      목: 과정 운영정보 BEAN
//  2. 프로그램명:  CourseStateAdminBean.java
//  3. 개      요: 과정 운영정보 BEAN
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 13
//  7. 수      정:
//**********************************************************
package com.credu.course;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.research.SulmunAllBean;
import com.credu.research.SulmunExampleData;
import com.credu.research.SulmunQuestionExampleData;

public class CourseStateAdminBean {

    public CourseStateAdminBean() {
    }

    /**
     * 과정운영정보 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정운영정보 리스트
     */
    public ArrayList<DataBox> selectListCourseState(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list2 = null;
        DataBox dbox = null;
        String sql1 = "";
        // String sql2 = "";

        // String v_Bcourse = ""; //이전코스
        // String v_course = ""; //현재코스
        // String v_Bcourseseq = ""; //이전코스차수
        // String v_courseseq = ""; //현재코스차수        
        // int l = 0;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_uclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류(중)
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류(소)		
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서 

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            // list2 = new ArrayList();

            sql1 = " select a.grcode grcode, b.grcodenm grcodenm, a.gyear gyear, a.grseq grseq, a.course course, a.cyear cyear, a.scsubjseq scsubjseq, ";
            sql1 += "        a.courseseq courseseq, a.coursenm coursenm, a.subj subj, a.year year, a.subjseq subjseq,a.subjseqgr, a.subjnm subjnm,      ";
            sql1 += "        a.isonoff isonoff, a.edustart edustart, a.eduend eduend, a.isgoyong isgoyong, a.wstep wstep, a.wmtest wmtest, ";
            sql1 += "        a.wftest wftest,a.whtest whtest, a.wreport wreport, a.wact wact, a.gradstep gradstep, a.sgradscore sgradscore,                ";
            sql1 += "        a.biyong biyong, a.goyongprice goyongprice, a.subjcnt, a.isbelongcourse, ";
            sql1 += "        (select count(*) from TZ_EXAMPAPER                                                                           ";
            sql1 += "          where subj = A.subj and year = A.year and subjseq=A.subjseq and examtype = 'M') as cnt_mexam, 			  ";
            sql1 += "        (select count(*) from TZ_EXAMPAPER                                                                           ";
            sql1 += "          where subj = A.subj and year = A.year and subjseq=A.subjseq and examtype = 'E') as cnt_texam,             ";
            sql1 += "        (select count(*) from TZ_EXAMPAPER                                                                           ";
            sql1 += "          where subj = A.subj and year = A.year and subjseq=A.subjseq and examtype = 'H') as cnt_hexam,             ";
            sql1 += "        (select count(distinct projseq) from tz_projord where subj=a.subj and year=a.year and subjseq=a.subjseq) as cnt_proj,   ";
            sql1 += "        (select count(distinct ordseq) from tz_projord where subj=a.subj and year=a.year and subjseq=a.subjseq) as cnt_ord      ";
            //sql1 += "        (select count(*) from TZ_ACTIVITY where subj = A.subj) as cnt_act                                          ";
            sql1 += "   from VZ_SCSUBJSEQ a, TZ_GRCODE b where a.grcode = b.grcode                                                        ";

            if (!ss_grcode.equals("ALL")) {
                sql1 += " and a.grcode = '" + ss_grcode + "'";
            }
            if (!ss_gyear.equals("ALL")) {
                sql1 += " and a.gyear = '" + ss_gyear + "'";
            }
            if (!ss_grseq.equals("ALL")) {
                sql1 += " and a.grseq = '" + ss_grseq + "'";
            }
            if (!ss_uclass.equals("ALL")) {
                sql1 += " and a.scupperclass = '" + ss_uclass + "'";
            }
            if (!ss_mclass.equals("ALL")) {
                sql1 += " and a.scmiddleclass = '" + ss_mclass + "'";
            }
            if (!ss_lclass.equals("ALL")) {
                sql1 += " and a.sclowerclass = '" + ss_lclass + "'";
            }
            if (!ss_subjcourse.equals("ALL")) {
                sql1 += " and a.scsubj = '" + ss_subjcourse + "'";
            }
            if (!ss_subjseq.equals("ALL")) {
                sql1 += " and a.scsubjseq = '" + ss_subjseq + "'";
            }

            if (v_orderColumn.equals("")) {
                sql1 += " order by a.course, a.cyear, a.courseseq, a.subj, a.year, a.subjseq ";
            } else {
                sql1 += " order by a.course," + v_orderColumn + v_orderType;
            }

            Log.info.println("----------------" + sql1);
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
        return list1;
    }

    /**
     * 통계검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectTotalList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_category = box.getString("param1"); //구분 방송 문화 게임

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "select a.areaname,a.grcode, decode(e.gubun,'01','일반','02','공공','03','기업','04','대학','05','특화고','미분류') gubun,  ";
            sql += " c.grseqnm, b.userid ,decode(sex,'1','남','2','여') sex, d.name, a.subjnm, b.isgraduated ";
            sql += ", (select nvl(x.isgraduated,'N') from tz_stold x where x.userid =b.userid and x.subj = b.subj and x.subjseq = b.subjseq and x.year=b.year) as graduate ";
            sql += "from vz_scsubjseq a, tz_student b, tz_grseq c, tz_member d, tz_grcode e  where e.grcode = c.grcode and  ";
            sql += "d.userid = b.userid  and d.grcode = a.grcode ";
            sql += "and c.gyear = a.year and b.year = c.gyear and c.grseq = a.grseq and c.grcode = a.grcode ";
            sql += "and a.subj = b.subj and a.subjseq =b.subjseq ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";
            // String tmp1 = "";
            // String tmp2 = "";
            // String tmp3 = "";
            // String tmp4 = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                /*
                 * tmp1 = temp.substring(0,4); tmp2 = temp.substring(4,11); tmp3
                 * = temp.substring(11,15); tmp4 = temp.substring(4,15);
                 */
                if (i != 0)
                    sql += " or ";
                sql += "a.scyear||a.grcode||a.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            i = 0;
            StringTokenizer st2 = new StringTokenizer(ss_category, ",");
            sql += " and (";
            while (st2.hasMoreElements()) {

                String temp5 = st2.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += " a.area = '" + temp5 + "'";
                i++;
            }
            sql += " ) ";

            sql += " order by grseqnm, areaname, subjnm ";

            //System.out.println("selectTotalList =========== : selectTotalList: " + sql);
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
     * 분야별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectCategorylList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun           = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun         = null;

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임
        // String	ss_subj			= box.getString("param2");     //과정

        //listGubun = this.selectGubunList(box);
        StringBuilder sb = new StringBuilder();

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sb.append(" SELECT  /* CourseStateAdminBean.selectCategorylList (분야별 통계) */                            \n");
            sb.append("         CATE                                                                                    \n");
            sb.append("     ,   CATENAME                                                                                \n");
            sb.append("     ,   AREA,AREANAME                                                                           \n");
            sb.append("     ,   SUBJ_CNT                                                                                \n");
            sb.append("     ,   USER_CNT                                                                                \n");
            sb.append("     ,   GRAD_CNT                                                                                \n");
            sb.append("     ,   GRAD_RATE                                                                               \n");
            sb.append("     ,   MAN_CNT                                                                                 \n");
            sb.append("     ,   WOMAN_CNT                                                                               \n");
            sb.append("     ,   SUL_RATE                                                                                \n");
            sb.append("     ,   RANK                                                                                    \n");
            sb.append("     ,   CASE    WHEN RANK = 1 THEN COUNT (*) OVER (PARTITION BY CATE)                           \n");
            sb.append("                 ELSE 0                                                                          \n");
            sb.append("         END AS ROWSPAN                                                                          \n");
            sb.append("   FROM  (                                                                                       \n");
            sb.append("         SELECT  CATE                                                                            \n");
            sb.append("             ,   DECODE(CATE, '01', '일반'                                                       \n");
            sb.append("                            , '02', '공공'                                                       \n");
            sb.append("                            , '03', '기업'                                                       \n");
            sb.append("                            , '04', '대학'                                                       \n");
            sb.append("                            , '05', '특화고'                                                     \n");
            sb.append("                            , '06', '자유학기제'                                                 \n");
            sb.append("                     ) AS CATENAME                                                               \n");
            sb.append("             ,   AREA                                                                            \n");
            sb.append("             ,   DECODE(AREA, 'G0', '게임'                                                       \n");
            sb.append("                            , 'K0', '문화'                                                       \n");
            sb.append("                            , 'B0', '방송'                                                       \n");
            sb.append("                            , '기타'                                                             \n");
            sb.append("                     ) AS AREANAME                                                               \n");
            sb.append("             ,   COUNT (DISTINCT A.SUBJ) SUBJ_CNT                                                \n");
            sb.append("             ,   SUM (USER_CNT) USER_CNT                                                         \n");
            sb.append("             ,   SUM (GRAD_CNT) GRAD_CNT                                                         \n");
            sb.append("             ,   SUM (MAN_CNT) MAN_CNT                                                           \n");
            sb.append("             ,   SUM (WOMAN_CNT) WOMAN_CNT                                                       \n");
            sb.append("             ,   ROUND (DECODE (SUM (USER_CNT),  0, 0                                            \n");
            sb.append("                                             ,   SUM (grad_cnt) / SUM (user_cnt) * 100           \n");
            sb.append("                             ), 2                                                                \n");
            sb.append("                     ) AS GRAD_RATE                                                              \n");
            sb.append("             ,   ROUND (AVG (SUL_RATE), 2) AS SUL_RATE                                           \n");
            sb.append("             ,   ROW_NUMBER() OVER(PARTITION BY CATE ORDER BY A.AREA) AS RANK                    \n");
            sb.append("           FROM  (                                                                               \n");
            sb.append("                 SELECT                                                                          \n");
            sb.append("                         E.GUBUN AS CATE                                                         \n");
            sb.append("                     ,   A.AREA                                                                  \n");
            sb.append("                     ,   DECODE (c.USERID, NULL, 0, 1) USER_CNT                                  \n");
            sb.append("                     ,   DECODE (C.ISGRADUATED, 'Y', 1, 0) GRAD_CNT                              \n");
            sb.append("                     ,   DECODE (D.SEX, '1', 1, 0) MAN_CNT                                       \n");
            sb.append("                     ,   DECODE (D.SEX, '2', 1, 0) WOMAN_CNT                                     \n");
            sb.append("                     ,   A.SUBJ                                                                  \n");
            sb.append("                     ,   (                                                                       \n");
            sb.append("                         SELECT                                                                  \n");
            sb.append("                                 ROUND (AVG (DISTCODE1_AVG), 2)                                  \n");
            sb.append("                           FROM  TZ_SULEACH                                                      \n");
            sb.append("                          WHERE  SUBJ = B.SUBJ                                                   \n");
            sb.append("                            AND  GRCODE = B.GRCODE                                               \n");
            sb.append("                            AND  YEAR = B.YEAR                                                   \n");
            sb.append("                            AND  SUBJSEQ = B.SUBJSEQ                                             \n");
            sb.append("                            AND  SULPAPERNUM = B.SULPAPERNUM2                                    \n");
            sb.append("                         ) AS SUL_RATE                                                           \n");
            sb.append("                   FROM  TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B                                       \n");
            sb.append("                         ON A.SUBJ = B.SUBJ                                                      \n");
            sb.append("                   LEFT  OUTER JOIN TZ_STUDENT C                                                 \n");
            sb.append("                         ON B.SUBJ = C.SUBJ                                                      \n");
            sb.append("                         AND B.YEAR = C.YEAR                                                     \n");
            sb.append("                         AND B.SUBJSEQ = C.SUBJSEQ                                               \n");
            sb.append("                   LEFT  OUTER JOIN TZ_MEMBER D                                                  \n");
            sb.append("                         ON D.USERID = C.USERID                                                  \n");
            sb.append("                   LEFT  JOIN TZ_GRCODE E                                                        \n");
            sb.append("                         ON B.GRCODE = E.GRCODE                                                  \n");
            sb.append("                   LEFT  JOIN TZ_PROPOSE F                                                       \n");
            sb.append("                         ON F.USERID = D.USERID                                                  \n");
            sb.append("                         AND B.SUBJ = F.SUBJ                                                     \n");
            sb.append("                         AND B.YEAR = F.YEAR                                                     \n");
            sb.append("                         AND B.SUBJSEQ = F.SUBJSEQ                                               \n");
            sb.append("                  WHERE  (1 = 1)                                                                 \n");

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sb.append("                    AND  (");
            while (st.hasMoreElements()) {
                temp = st.nextToken();

                //if temp.equals("2012")
                if (i != 0) {
                    sb.append("\n                         OR ");
                }

                sb.append(" B.YEAR||B.GRCODE||B.GRSEQ = '").append(temp).append("'");
                i++;
            }

            sb.append(" ) \n");

            if (!ss_part.equals("")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_part, ",");

                sb.append("                    AND  (");
                while (st2.hasMoreElements()) {

                    String temp5 = st2.nextToken();
                    if (i != 0) {
                        sb.append("\n                         OR ");
                    }

                    sb.append(" A.AREA = '").append(temp5).append("'");
                    i++;
                }

                sb.append(" ) \n");
            } else {
                sb.append(" AND A.AREA IN ('K0','B0','G0')  \n");
            }

            sb.append("                    AND  F.CHKFINAL ='Y'                                                         \n");
            sb.append("                    AND  D.GRCODE||D.USERID = B.GRCODE||C.USERID                                 \n");
            sb.append("                  ORDER  BY B.GRCODE, B.GYEAR, B.GRSEQ, C.USERID, B.SUBJ, B.YEAR, B.SUBJSEQ      \n");
            sb.append("                 ) A                                                                             \n");
            sb.append("          GROUP  BY CATE, AREA                                                                   \n");
            sb.append("         )                                                                                       \n");
            sb.append("  ORDER  BY CATE                                                                                 \n");

            ls = connMgr.executeQuery(sb.toString());
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_grad_rate", new Double(ls.getDouble("grad_rate")));
                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
     * 차수별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectSeqList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select  grcode, grseq, gyear,grseqnm,cate, area, areaname, subj_cnt,user_cnt, rank,grad_cnt,grad_rate, sul_rate ";
            sql += " ,case when rank = 1 then count(*) over (partition by  grcode, gyear, grseq) else 0 end as rowspan from ";

            sql += " (SELECT   grcode,  grseq, gyear, ";
            sql += " (select grseqnm from tz_grseq where grcode = x.grcode and gyear = x.gyear and grseq = x.grseq) grseqnm, DECODE(cate,'01','일반','02','공공','03','기업','04','대학','05','특화고') cate, ";
            sql += "area , ";
            sql += " DECODE (area, 'G0', '게임', 'K0', '문화', 'B0', '방송','기타') AS areaname, ";
            sql += "  COUNT (DISTINCT x.subj) subj_cnt,  ";
            sql += "  SUM (user_cnt) user_cnt,";
            sql += "   SUM (grad_cnt) grad_cnt,";
            sql += " RANK () OVER (PARTITION BY grcode, gyear, grseq ORDER BY grcode, gyear, grseq, area) AS RANK,";

            sql += "    ROUND (DECODE (SUM (user_cnt),";
            sql += "                  0, 0,";
            sql += "                  SUM (grad_cnt) / SUM (user_cnt) * 100";
            sql += "                 ),";
            sql += "          1";
            sql += "         ) grad_rate,";
            sql += "          ROUND (AVG (sul_rate), 1) sul_rate, ROUND (AVG (sul_rate), 1) totalsul_rate";

            sql += "   FROM (SELECT   b.grcode, b.grseq, b.gyear,e.gubun cate,a.area,";
            sql += "                 DECODE (c.userid, NULL, 0, 1) user_cnt,";
            sql += "                 DECODE (c.isgraduated, 'Y', 1, 0) grad_cnt,  ";
            sql += "                 a.subj,";
            sql += "                 (SELECT ROUND (AVG (distcode1_avg), 1)";
            sql += "                    FROM tz_suleach";
            sql += "                      WHERE subj = b.subj";
            sql += "                     AND grcode = b.grcode";
            sql += "                     AND YEAR = b.YEAR";
            sql += "                     AND subjseq = b.subjseq";
            sql += "                     AND sulpapernum = b.sulpapernum2) sul_rate";

            sql += "            FROM tz_subj a INNER JOIN tz_subjseq b ON a.subj = b.subj ";
            sql += "                 LEFT OUTER JOIN tz_student c ON b.subj = c.subj  ";
            sql += "                   AND b.YEAR = c.YEAR ";
            sql += "                   AND b.subjseq = c.subjseq ";
            sql += "                 LEFT OUTER JOIN tz_member d ON d.userid = c.userid ";
            sql += "                 LEFT JOIN tz_grcode e ON b.grcode = e.grcode   ";
            sql += "                 LEFT JOIN tz_propose f ON f.userid = d.userid and b.subj = f.subj and b.year = f.year and b.subjseq = f.subjseq   ";
            sql += "           WHERE 1=1  ";
            sql += "              ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_part.equals("")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_part, ",");
                sql += " and (";
                while (st2.hasMoreElements()) {

                    String temp5 = st2.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.area = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            } else {
                sql += "and a.area in ('K0','B0','G0')";
            }
            sql += "  and f.chkfinal ='Y' and d.grcode||d.userid = b.grcode||c.userid  ";
            sql += "  ORDER BY b.grcode, b.gyear, b.grseq, c.userid, b.subj, b.YEAR, b.subjseq) x  ";
            sql += "  GROUP BY grcode, grseq, gyear, area, cate ) ORDER BY grcode, gyear, grseq, rank asc ";

            //System.out.println("StatisticsAdminBean =========== : selectSeqList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_grad_rate", new Double(ls.getDouble("grad_rate")));
                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));
                //dbox.put("d_totalsul_rate"	, new Double(ls.getDouble("totalsul_rate")));

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
     * 과정별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectCourseList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임
        String ss_subj = box.getString("param2"); //과정
        String ss_ordering = box.getString("param3"); //오더링

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            sql = "select area,subj,subjnm,sum(user_cnt) user_cnt,sum(grad_cnt) grad_cnt,sum(ngrad_cnt) ngrad_cnt,avg(grad_rate )grad_rate, avg(SUL_RATE ) SUL_RATE from  ( ";
            sql += " SELECT    decode(AREA,'B0','방송','G0','게임','K0','문화') as AREA, ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM, ";
            sql += "           USER_CNT, ";
            sql += "           GRAD_CNT, ";
            sql += "           ROUND(DECODE(USER_CNT,0,0,GRAD_CNT / USER_CNT * 100),2) GRAD_RATE, ";
            sql += "           NGRAD_CNT, ";
            sql += "           ROUND((DECODE(USER_CNT,0,0,(SELECT avg(distcode1_avg) FROM TZ_SULEACH WHERE SUBJ = A.SUBJ AND YEAR = A.YEAR and SUBJSEQ = A.SUBJSEQ  AND SULPAPERNUM = A.SULPAPERNUM2) )),2) SUL_RATE ";
            sql += "  FROM ( ";
            sql += "         SELECT ";
            sql += "           AREA, YEAR, SUBJSEQ, ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUM(USER_CNT) USER_CNT, ";
            sql += "           SUM(GRAD_CNT) GRAD_CNT, ";
            sql += "           SUM(NGRAD_CNT) NGRAD_CNT ";
            sql += "         FROM ";
            sql += "              ( ";
            sql += "                SELECT ";
            sql += "                    A.AREA, B.SUBJ, ";
            sql += "                    B.YEAR, ";
            sql += "                    B.SUBJNM, ";
            sql += "                    B.SUBJSEQ, ";
            sql += "                    B.EDUSTART, ";
            sql += "                    B.EDUEND, ";
            sql += "                    B.GRCODE, ";
            sql += "                    B.GYEAR, ";
            sql += "                    B.GRSEQ, ";
            sql += "                    DECODE(C.USERID,NULL,0,1) USER_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'Y',1,0) GRAD_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'N',1,0) NGRAD_CNT, ";
            sql += "                    B.SULPAPERNUM2 ";
            sql += "                FROM ";
            sql += "                  TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                  ON A.SUBJ = B.SUBJ  ";
            sql += "                  LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                  ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                  LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "       WHERE (1=1)";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();

                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            i = 0;
            StringTokenizer st2 = new StringTokenizer(ss_part, ",");
            sql += " and (";
            while (st2.hasMoreElements()) {

                String temp5 = st2.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += " a.area = '" + temp5 + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_subj.equals("null")) {

                i = 0;
                StringTokenizer st3 = new StringTokenizer(ss_subj, ",");
                sql += " and (";
                while (st3.hasMoreElements()) {

                    String temp5 = st3.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.subj = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            }

            sql += "   and d.grcode||d.userid = b.grcode||c.userid   ORDER BY AREA ";
            sql += "              )  ";
            sql += "    GROUP BY   ";
            sql += "           AREA,  ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM,  ";
            sql += "           YEAR, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUBJSEQ ";
            sql += " ) A  ";

            sql += " ) group by area,subj,subjnm  ";
            if (ss_ordering.equals("")) {
                sql += " ORDER BY user_cnt desc ";
            } else if (ss_ordering.equals("subjnm")) {
                sql += " ORDER BY subjnm asc ";
            } else {
                sql += " ORDER BY " + ss_ordering + " desc ";
            }

            //System.out.println("StatisticsAdminBean =========== : selectCourseList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_grad_rate", new Double(ls.getDouble("grad_rate")));
                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));
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
     * 만족도 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectSatisfyList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_area = box.getString("param1"); //구분 방송 문화 게임
        String ss_subj = box.getString("param2"); //과정
        String ss_ordering = box.getString("param3"); //오더링

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            sql = "select area,subj,subjnm,sum(user_cnt) user_cnt,sum(SUL_CNT) SUL_CNT,sum(ngrad_cnt) ngrad_cnt,avg(grad_rate)grad_rate, avg(SUL_RATE ) SUL_RATE, avg(TOTALSUL_RATE) TOTALSUL_RATE from  ( ";
            sql += " SELECT    decode(AREA,'B0','방송','G0','게임','K0','문화') as AREA, ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM, ";
            sql += "           USER_CNT, ";
            sql += "           GRAD_CNT, ";
            sql += "           ROUND(DECODE(USER_CNT,0,0,GRAD_CNT / USER_CNT * 100),2) GRAD_RATE, ";
            sql += "           NGRAD_CNT, ";
            sql += "		   (SELECT COUNT(*) FROM TZ_SULEACH WHERE SUBJ = A.SUBJ AND YEAR = A.YEAR AND SUBJSEQ = A.SUBJSEQ AND SULPAPERNUM = A.SULPAPERNUM2) SUL_CNT, ";
            sql += "           ROUND((DECODE(USER_CNT,0,0,(SELECT avg(distcode1_avg) FROM TZ_SULEACH WHERE SUBJ = A.SUBJ AND YEAR = A.YEAR and SUBJSEQ = A.SUBJSEQ  AND SULPAPERNUM = A.SULPAPERNUM2) )),1) SUL_RATE, ";
            sql += "           ROUND((DECODE(USER_CNT,0,0,(SELECT avg(distcode1_avg) FROM TZ_SULEACH WHERE YEAR = A.YEAR and SUBJSEQ = A.SUBJSEQ  AND SULPAPERNUM = A.SULPAPERNUM2))),1) TOTALSUL_RATE ";

            sql += "  FROM ( ";
            sql += "         SELECT ";
            sql += "           AREA, YEAR, SUBJSEQ, ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUM(USER_CNT) USER_CNT, ";
            sql += "           SUM(GRAD_CNT) GRAD_CNT, ";
            sql += "           SUM(NGRAD_CNT) NGRAD_CNT ";
            sql += "         FROM ";
            sql += "              ( ";
            sql += "                SELECT ";
            sql += "                    A.AREA, B.SUBJ, ";
            sql += "                    B.YEAR, ";
            sql += "                    B.SUBJNM, ";
            sql += "                    B.SUBJSEQ, ";
            sql += "                    B.EDUSTART, ";
            sql += "                    B.EDUEND, ";
            sql += "                    B.GRCODE, ";
            sql += "                    B.GYEAR, ";
            sql += "                    B.GRSEQ, ";
            sql += "                    DECODE(C.USERID,NULL,0,1) USER_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'Y',1,0) GRAD_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'N',1,0) NGRAD_CNT, ";
            sql += "                    B.SULPAPERNUM2 ";
            sql += "                FROM ";
            sql += "                  TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                  ON A.SUBJ = B.SUBJ  ";
            sql += "                  LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                  ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                  LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "       WHERE (1=1)";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                /*
                 * tmp1 = temp.substring(0,4); tmp2 = temp.substring(4,11); tmp3
                 * = temp.substring(11,15); tmp4 = temp.substring(4,15);
                 */
                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            i = 0;
            StringTokenizer st2 = new StringTokenizer(ss_area, ",");
            sql += " and (";
            while (st2.hasMoreElements()) {

                String temp5 = st2.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += " a.area = '" + temp5 + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_subj.equals("null")) {

                i = 0;
                StringTokenizer st3 = new StringTokenizer(ss_subj, ",");
                sql += " and (";
                while (st3.hasMoreElements()) {

                    String temp5 = st3.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.subj = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            }

            sql += "                ORDER BY AREA ";
            sql += "              )  ";
            sql += "    GROUP BY   ";
            sql += "           AREA,  ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM,  ";
            sql += "           YEAR, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUBJSEQ ";
            sql += " ) A  ";

            sql += " ) group by area,subj,subjnm  ";
            if (ss_ordering.equals("")) {
                sql += " ORDER BY user_cnt desc ";
            } else if (ss_ordering.equals("subjnm")) {
                sql += " ORDER BY subjnm asc ";
            } else {
                sql += " ORDER BY " + ss_ordering + " desc ";
            }

            //System.out.println("StatisticsAdminBean =========== : selectCourseList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                //dbox.put("d_grad_rate"	, new Double(ls.getDouble("grad_rate")));
                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));
                //dbox.put("d_totalsul_rate"	, new Double(ls.getDouble("totalsul_rate")));

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
     * 연령별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectAgeList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select  grcode, grseq, gyear,grseqnm,cate, area, areaname,";
            //sql = " select  gyear,";
            sql += " RANK,tot,toddler,teens,twenty,thirty,fourty,fifty,sixty,seventy,johndoe,";
            sql += " case when rank = 1 then count(*) over (partition by  grcode, gyear, grseq) else 0 end as rowspan from ";

            sql += " (SELECT   grcode,  grseq, gyear, ";
            sql += " (select grseqnm from tz_grseq where grcode = x.grcode and gyear = x.gyear and grseq = x.grseq) grseqnm, DECODE(cate,'01','일반','02','공공','03','기업','04','대학','05','특화고') cate, ";
            sql += " area , ";

            sql += " sum(tot) tot,sum(toddler) toddler, sum(teens) teens,sum(twenty) twenty,sum(thirty) thirty, ";
            sql += " sum(fourty) fourty,sum(fifty) fifty,sum(sixty) sixty, ";
            sql += " sum(seventy) seventy, sum(johndoe) johndoe,";

            sql += " DECODE (cate,'01', '일반', '02', '공공', '03', '기업','04', '대학','05', '특화고' ) catename, ";
            sql += " DECODE (area, 'G0', '게임', 'K0', '문화', 'B0', '방송','기타') AS areaname, ";

            sql += " RANK () OVER (PARTITION BY grcode, gyear, grseq ORDER BY grcode, gyear, grseq, area) AS RANK";

            sql += "   FROM (SELECT   d.grcode, b.grseq, b.gyear,e.gubun cate,a.area,";
            sql += " decode(c.userid,c.userid,1,0) tot, ";
            sql += " (CASE WHEN memberyear BETWEEN '2004' AND '2013' THEN 1 END) toddler, ";
            sql += " (CASE WHEN memberyear BETWEEN '1994' AND '2003' THEN 1 END) teens, ";
            sql += " (CASE WHEN memberyear BETWEEN '1984' AND '1993' THEN 1 END) twenty, ";
            sql += " (CASE WHEN memberyear BETWEEN '1974' AND '1983' THEN 1 END) thirty, ";
            sql += " (CASE WHEN memberyear BETWEEN '1964' AND '1973' THEN 1 END) fourty,";
            sql += " (CASE WHEN memberyear BETWEEN '1954' AND '1963' THEN 1 END) fifty, ";
            sql += " (CASE WHEN memberyear BETWEEN '1944' AND '1953' THEN 1 END) sixty, ";
            sql += " (CASE WHEN memberyear BETWEEN '1900' AND '1943' THEN 1 END) seventy, ";
            sql += " decode(memberyear,NULL,1) johndoe ";

            sql += "            FROM tz_subj a INNER JOIN tz_subjseq b ON a.subj = b.subj ";
            sql += "                 LEFT OUTER JOIN tz_student c ON b.subj = c.subj  ";
            sql += "                   AND b.YEAR = c.YEAR ";
            sql += "                   AND b.subjseq = c.subjseq ";
            sql += "                 LEFT OUTER JOIN tz_member d ON d.userid = c.userid ";
            sql += "                 LEFT JOIN tz_grcode e ON b.grcode = e.grcode   ";
            sql += "           WHERE 1=1  ";
            sql += "              ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_part.equals("")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_part, ",");
                sql += " and (";
                while (st2.hasMoreElements()) {

                    String temp5 = st2.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.area = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            } else {
                sql += "and a.area in ('K0','B0','G0')";
            }

            sql += "  and d.grcode||d.userid = b.grcode||c.userid  ";
            sql += "  ORDER BY d.grcode, b.gyear, b.grseq, c.userid, b.subj, b.YEAR, b.subjseq) x  ";
            sql += "  GROUP BY grcode, grseq, gyear, area, cate )";
            //sql += "  GROUP BY  gyear)"; 

            //System.out.println("StatisticsAdminBean =========== : selectAgeList: " + sql);
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
     * 성별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectGenderList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select  grcode, grseq, gyear,grseqnm,cate, area, areaname,";
            sql += " RANK,man,woman,johndoe,tot,";
            sql += " case when rank = 1 then count(*) over (partition by  grcode, gyear, grseq) else 0 end as rowspan from ";

            sql += " (SELECT   grcode,  grseq, gyear, ";
            sql += " (select grseqnm from tz_grseq where grcode = x.grcode and gyear = x.gyear and grseq = x.grseq) grseqnm, DECODE(cate,'01','일반','02','공공','03','기업','04','대학','05','특화고') cate, ";
            sql += " area , ";

            sql += "sum(tot) tot, sum(man) man, sum(woman) woman,sum(johndoe) johndoe, ";

            sql += " DECODE (cate,'01', '일반', '02', '공공', '03', '기업','04', '대학','05', '특화고' ) catename, ";
            sql += " DECODE (area, 'G0', '게임', 'K0', '문화', 'B0', '방송','기타') AS areaname, ";

            sql += " RANK () OVER (PARTITION BY grcode, gyear, grseq ORDER BY grcode, gyear, grseq, area) AS RANK";

            sql += "   FROM (SELECT   e.grcode, b.grseq, b.gyear,e.gubun cate,a.area,";
            sql += " decode(c.userid,c.userid,1,0) tot, ";
            sql += " decode(sex,1,1,0) man, ";
            sql += " decode(sex,2,1,0) woman, ";
            sql += "decode(sex,1,0,2,0,1) johndoe ";

            sql += "            FROM tz_subj a INNER JOIN tz_subjseq b ON a.subj = b.subj ";
            sql += "                 LEFT OUTER JOIN tz_student c ON b.subj = c.subj  ";
            sql += "                   AND b.YEAR = c.YEAR ";
            sql += "                   AND b.subjseq = c.subjseq ";
            sql += "                 LEFT OUTER JOIN tz_member d ON d.userid = c.userid ";
            sql += "                 LEFT JOIN tz_grcode e ON b.grcode = e.grcode   ";
            sql += "           WHERE 1=1  ";
            sql += "              ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_part.equals("")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_part, ",");
                sql += " and (";
                while (st2.hasMoreElements()) {

                    String temp5 = st2.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.area = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            } else {
                sql += "and a.area in ('K0','B0','G0')";
            }

            sql += "  and d.grcode||d.userid = b.grcode||c.userid  ";
            sql += "  ORDER BY e.grcode, b.gyear, b.grseq, c.userid, b.subj, b.YEAR, b.subjseq) x  ";
            sql += "  GROUP BY grcode, grseq, gyear, area, cate )";

            //System.out.println("StatisticsAdminBean =========== : selectAgeList: " + sql);
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
     * 지역별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectLocationList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select  grcode, grseq, gyear,grseqnm,cate, area, areaname,";
            sql += " RANK,seoul,kyunggi,busan,";

            sql += " incheon,daegu,daejeon,";
            sql += " kwangju, jeonbook,jeonnam,";
            sql += " chungbook, choongnam,gangwon, ";
            sql += " gyungbook, gyungnam,woolsan, ";
            sql += " jejoo, etc_cnt , tot,  ";

            sql += " case when rank = 1 then count(*) over (partition by  grcode, gyear, grseq) else 0 end as rowspan from ";

            sql += " (SELECT   grcode,  grseq, gyear, ";
            sql += " (select grseqnm from tz_grseq where grcode = x.grcode and gyear = x.gyear and grseq = x.grseq) grseqnm, DECODE(cate,'01','일반','02','공공','03','기업','04','대학','05','특화고') cate, ";
            sql += " area , ";

            sql += "sum(seoul) seoul, sum(kyunggi) kyunggi,sum(busan) busan, ";
            sql += "sum(incheon) incheon, sum(daegu) daegu,sum(daejeon) daejeon, ";
            sql += "sum(kwangju) kwangju, sum(jeonbook) jeonbook,sum(jeonnam) jeonnam, ";

            sql += "sum(chungbook) chungbook, sum(choongnam) choongnam,sum(gangwon) gangwon, ";
            sql += "sum(gyungbook) gyungbook, sum(gyungnam) gyungnam,sum(woolsan) woolsan, ";
            sql += "sum(jejoo) jejoo, sum(etc) etc_cnt ,sum(tot) tot , ";

            sql += " DECODE (cate,'01', '일반', '02', '공공', '03', '기업','04', '대학','05', '특화고' ) catename, ";
            sql += " DECODE (area, 'G0', '게임', 'K0', '문화', 'B0', '방송','기타') AS areaname, ";

            sql += " RANK () OVER (PARTITION BY grcode, gyear, grseq ORDER BY grcode, gyear, grseq, area) AS RANK";

            sql += " FROM (SELECT   e.grcode, b.grseq, b.gyear,e.gubun cate,a.area,";
            sql += " decode(c.userid,c.userid,1,0) tot, ";
            sql += " decode(substr(addr,1,2),'서울',1,0) seoul, ";
            sql += " decode(substr(addr,1,2),'경기',1,0) kyunggi, ";
            sql += " decode(substr(addr,1,2),'부산',1,0) busan, ";
            sql += " decode(substr(addr,1,2),'인천',1,0) incheon, ";
            sql += " decode(substr(addr,1,2),'대구',1,0) daegu, ";
            sql += " decode(substr(addr,1,2),'대전',1,0) daejeon, ";
            sql += " decode(substr(addr,1,2),'광주',1,0) kwangju, ";
            sql += " decode(substr(addr,1,2),'전북',1,0) jeonbook, ";
            sql += " decode(substr(addr,1,2),'전남',1,0) jeonnam, ";
            sql += " decode(substr(addr,1,2),'충북',1,0) chungbook, ";
            sql += " decode(substr(addr,1,2),'충남',1,0) choongnam, ";
            sql += " decode(substr(addr,1,2),'강원',1,0) gangwon, ";
            sql += " decode(substr(addr,1,2),'경북',1,0) gyungbook, ";
            sql += " decode(substr(addr,1,2),'경남',1,0) gyungnam, ";
            sql += " decode(substr(addr,1,2),'울산',1,0) woolsan, ";
            sql += " decode(substr(addr,1,2),'제주',1,0) jejoo, ";
            sql += " decode(substr(addr,1,2),'서울',0,'경기',0,'부산',0,'인천',0,'대구',0 ,'대전',0,'광주',0,'전북',0,'전남',0,'충북',0,'충남',0,'강원',0,'경북',0,'경남',0,'울산',0,'제주',0,1) etc ";
            sql += "            FROM tz_subj a INNER JOIN tz_subjseq b ON a.subj = b.subj ";
            sql += "                 LEFT OUTER JOIN tz_student c ON b.subj = c.subj  ";
            sql += "                   AND b.YEAR = c.YEAR ";
            sql += "                   AND b.subjseq = c.subjseq ";
            sql += "                 LEFT OUTER JOIN tz_member d ON d.userid = c.userid ";
            sql += "                 LEFT JOIN tz_grcode e ON b.grcode = e.grcode   ";
            sql += "           WHERE 1=1  ";
            sql += "              ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_part.equals("")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_part, ",");
                sql += " and (";
                while (st2.hasMoreElements()) {

                    String temp5 = st2.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.area = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            } else {
                sql += "and a.area in ('K0','B0','G0')";
            }

            sql += "  and d.grcode||d.userid = b.grcode||c.userid  ";
            sql += "  ORDER BY e.grcode, b.gyear, b.grseq, c.userid, b.subj, b.YEAR, b.subjseq) x  ";
            sql += "  GROUP BY grcode, grseq, gyear, area, cate ) ORDER BY grcode, gyear, grseq, rank asc";

            //System.out.println("StatisticsAdminBean =========== : selectAgeList: " + sql);
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
     * 직업별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectVocationList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select  grcode, grseq, gyear,grseqnm,cate, area, areaname,";
            sql += " RANK,high,univ,country,";

            sql += " gonggong,freelancer,preoffice,";
            sql += " office, jubu,miltiary,";
            sql += " univoffice, hospital,lawyer, ";
            sql += " broad, jongkyo,entertain, ";
            sql += " sports, proffesor , tot,  ";

            sql += " lecter, owner , etc,  ";

            sql += " case when rank = 1 then count(*) over (partition by  grcode, gyear, grseq) else 0 end as rowspan from ";

            sql += " (SELECT   grcode,  grseq, gyear, ";
            sql += " (select grseqnm from tz_grseq where grcode = x.grcode and gyear = x.gyear and grseq = x.grseq) grseqnm, DECODE(cate,'01','일반','02','공공','03','기업','04','대학','05','특화고') cate, ";
            sql += " area , ";

            sql += "sum(high) high, sum(univ) univ,sum(country) country, ";
            sql += "sum(gonggong) gonggong, sum(freelancer) freelancer, sum(preoffice) preoffice, ";
            sql += "sum(office) office, sum(jubu) jubu, sum(miltiary) miltiary, ";

            sql += "sum(univoffice) univoffice, sum(hospital) hospital,sum(lawyer) lawyer, ";
            sql += "sum(broad) broad, sum(jongkyo) jongkyo,sum(entertain) entertain, ";
            sql += "sum(sports) sports, sum(proffesor) proffesor ,sum(tot) tot , ";

            sql += "sum(lecter) lecter, sum(owner) owner ,sum(etc) etc , ";

            sql += " DECODE (cate,'01', '일반', '02', '공공', '03', '기업','04', '대학','05', '특화고' ) catename, ";
            sql += " DECODE (area, 'G0', '게임', 'K0', '문화', 'B0', '방송','기타') AS areaname, ";

            sql += " RANK () OVER (PARTITION BY grcode, gyear, grseq ORDER BY grcode, gyear, grseq, area) AS RANK";

            sql += " FROM (SELECT   e.grcode, b.grseq, b.gyear,e.gubun cate,a.area,";
            sql += " decode(c.userid,c.userid,1,0) tot, ";
            sql += " decode(jikup,'01',1,0) high, ";
            sql += " decode(jikup,'02',1,0) univ, ";
            sql += " decode(jikup,'03',1,0) country, ";
            sql += " decode(jikup,'04',1,0) gonggong, ";
            sql += " decode(jikup,'05',1,0) freelancer, ";
            sql += " decode(jikup,'06',1,0) preoffice, ";
            sql += " decode(jikup,'07',1,0) office, ";
            sql += " decode(jikup,'08',1,0) jubu, ";
            sql += " decode(jikup,'09',1,0) miltiary, ";
            sql += " decode(jikup,'10',1,0) univoffice, ";
            sql += " decode(jikup,'11',1,0) hospital, ";
            sql += " decode(jikup,'12',1,0) lawyer, ";
            sql += " decode(jikup,'13',1,0) broad, ";
            sql += " decode(jikup,'14',1,0) jongkyo, ";
            sql += " decode(jikup,'15',1,0) entertain, ";
            sql += " decode(jikup,'16',1,0) sports, ";
            sql += " decode(jikup,'17',1,0) proffesor, ";
            sql += " decode(jikup,'18',1,0) lecter, ";
            sql += " decode(jikup,'19',1,0) owner, ";
            sql += " decode(jikup,'99',1,0) etc, ";
            sql += " decode(jikup,'01',0,'02',0,'03',0,'04',0,'05',0,'06',0,'07',0,'08',0,'09',0,'10',0,'11',0,'12',0,'13',0,'14',0,'15',0,'16',0,'17',0,'18',0,'19',0,'99',0,1) johndoe ";

            sql += "            FROM tz_subj a INNER JOIN tz_subjseq b ON a.subj = b.subj ";
            sql += "                 LEFT OUTER JOIN tz_student c ON b.subj = c.subj  ";
            sql += "                   AND b.YEAR = c.YEAR ";
            sql += "                   AND b.subjseq = c.subjseq ";
            sql += "                 LEFT OUTER JOIN tz_member d ON d.userid = c.userid ";
            sql += "                 LEFT JOIN tz_grcode e ON b.grcode = e.grcode   ";
            sql += "           WHERE 1=1  ";
            sql += "              ";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_part.equals("")) {
                i = 0;
                StringTokenizer st2 = new StringTokenizer(ss_part, ",");
                sql += " and (";
                while (st2.hasMoreElements()) {

                    String temp5 = st2.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.area = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            } else {
                sql += "and a.area in ('K0','B0','G0')";
            }

            sql += "  and d.grcode||d.userid = b.grcode||c.userid  ";
            sql += "  ORDER BY e.grcode, b.gyear, b.grseq, c.userid, b.subj, b.YEAR, b.subjseq) x  ";
            sql += "  GROUP BY grcode, grseq, gyear, area, cate )ORDER BY grcode, gyear, grseq, rank asc ";

            //System.out.println("StatisticsAdminBean =========== : selectAgeList: " + sql);
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
     * 과정운영 통계 - 매출현황
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectSaleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "N000001"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "2012"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "SELECT   a.tid, a.userid, a.subj, a.YEAR, a.subjseq, a.subjnm, a.edustart, ";
            sql += "         a.eduend, a.paymethod, a.biyong, a.RESULT, a.pgauthdate, a.pgauthtime, ";
            sql += "         NVL(TRIM(a.inputname),a.name) inputname, a.price, a.RANK, a.name,";
            sql += "         (SELECT gs.grseqnm ";
            sql += "            FROM tz_grseq gs ";
            sql += "           WHERE gs.grcode = a.grcode ";
            sql += "             AND gs.gyear = a.gyear ";
            sql += "             AND gs.grseq = a.grseq) grseqnm, ";
            sql += "         NVL ((SELECT isuseyn ";
            sql += "                 FROM tz_tax ";
            sql += "                WHERE subj = a.subj AND YEAR = a.YEAR AND subjseq = a.subjseq), ";
            sql += "              'N' ";
            sql += "             ) istaxyn, ";
            sql += "         (SELECT sa.classname ";
            sql += "            FROM tz_subjatt sa ";
            sql += "           WHERE sa.upperclass = a.upperclass ";
            sql += "             AND sa.middleclass = a.middleclass ";
            sql += "             AND sa.lowerclass = '000') middleclassname, ";
            sql += "         (SELECT sa.classname ";
            sql += "            FROM tz_subjatt sa ";
            sql += "           WHERE sa.upperclass = a.upperclass ";
            sql += "             AND sa.middleclass = a.middleclass ";
            sql += "             AND sa.lowerclass = a.lowerclass) lowerclassname, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN price ";
            sql += "            ELSE 0 ";
            sql += "         END AS realtotal, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN SUM (biyong) OVER (PARTITION BY tid) ";
            sql += "            ELSE 0 ";
            sql += "         END AS subtotal, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN COUNT (*) OVER (PARTITION BY tid) ";
            sql += "            ELSE 0 ";
            sql += "         END AS rowspan ";
            sql += "    FROM (SELECT   a.tid, b.userid, b.subj, b.YEAR, b.subjseq, c.subjnm, ";
            sql += "                   c.biyong, d.upperclass, d.middleclass, d.lowerclass, ";
            sql += "                   c.edustart, c.eduend, a.paymethod, ";
            sql += "                   c.grcode, c.gyear, c.grseq, ";
            sql += "                   (SELECT max(mb.name) ";
            sql += "                      FROM tz_member mb ";
            sql += "                     WHERE mb.userid = a.userid) name, ";
            sql += "                   CASE ";
            sql += "                      WHEN resultcode = '00' ";
            sql += "                         THEN '성공' ";
            sql += "                      ELSE '실패' ";
            sql += "                   END AS RESULT, a.pgauthdate, a.pgauthtime, a.inputname, a.price, ";
            sql += "                   RANK () OVER (PARTITION BY b.tid ORDER BY b.subj) AS RANK ";
            sql += "              FROM tz_billinfo a INNER JOIN tz_propose b ON a.tid = b.tid ";
            sql += "                   INNER JOIN tz_subjseq c ";
            sql += "                   ON b.subj = c.subj ";
            sql += "                 AND b.YEAR = c.YEAR ";
            sql += "                 AND b.subjseq = c.subjseq ";
            sql += "                   INNER JOIN tz_subj d ON b.subj = d.subj ";

            sql += "                 AND b.chkfinal = 'Y' ";

            if (!ss_grcode.equals("ALL")) {
                sql += " and c.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and c.gyear = " + SQLString.Format(v_year) + "\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and c.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and d.middleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_lclass.equals("ALL"))
                sql += " and d.lowerclass = " + SQLString.Format(ss_lclass) + "\n";
            if (!ss_option2.equals("ALL")) {
                if ("CARD".equals(ss_option2)) { //카드
                    sql += " and a.paymethod IN ('VCard', 'Card')";
                } else if ("DIRECT".equals(ss_option2)) { //계좌이체
                    sql += " and a.paymethod = 'DirectBank'";
                } else if ("VBANK".equals(ss_option2)) { //가상계좌
                    sql += " and a.paymethod = 'VBank'";
                } else if ("BOOK".equals(ss_option2)) { //무통장입금
                    sql += " and a.paymethod = 'BankBook'";
                }
            }

            if (!v_startdate.equals("")) {//
                sql += "  and a.pgauthdate  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and a.pgauthdate <=" + SQLString.Format(v_enddate);
            }

            sql += "          ORDER BY b.tid, b.userid, b.YEAR) a ";
            sql += "ORDER BY YEAR DESC, tid, userid, RANK, pgauthdate DESC ";

            //System.out.println("StatisticsAdminBean =========== : selectStatisticsSaleList: " + sql);
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
     * 입과현황 - 수강생별 - 결제액별 통계 - 매출현황
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterStudentSaleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        // String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "SELECT   a.tid, a.userid, a.subj, a.YEAR, a.subjseq, a.subjnm, a.edustart, ";
            sql += "         a.eduend, a.paymethod, a.biyong, a.RESULT, a.pgauthdate, a.pgauthtime, ";
            sql += "         NVL(TRIM(a.inputname),a.name) inputname, a.price, a.RANK, ";
            sql += "         a.name, a.resno, a.comptext, a.appdate, a.email, a.handphone, ";
            sql += "         (SELECT gs.grseqnm ";
            sql += "            FROM tz_grseq gs ";
            sql += "           WHERE gs.grcode = a.grcode ";
            sql += "             AND gs.gyear = a.gyear ";
            sql += "             AND gs.grseq = a.grseq) grseqnm, ";
            sql += "         NVL ((SELECT isuseyn ";
            sql += "                 FROM tz_tax ";
            sql += "                WHERE subj = a.subj AND YEAR = a.YEAR AND subjseq = a.subjseq), ";
            sql += "              'N' ";
            sql += "             ) istaxyn, ";
            sql += "         (SELECT sa.classname ";
            sql += "            FROM tz_subjatt sa ";
            sql += "           WHERE sa.upperclass = a.upperclass ";
            sql += "             AND sa.middleclass = a.middleclass ";
            sql += "             AND sa.lowerclass = '000') middleclassname, ";
            sql += "         (SELECT sa.classname ";
            sql += "            FROM tz_subjatt sa ";
            sql += "           WHERE sa.upperclass = a.upperclass ";
            sql += "             AND sa.middleclass = a.middleclass ";
            sql += "             AND sa.lowerclass = a.lowerclass) lowerclassname, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN price ";
            sql += "            ELSE 0 ";
            sql += "         END AS realtotal, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN SUM (biyong) OVER (PARTITION BY userid) ";
            sql += "            ELSE 0 ";
            sql += "         END AS subtotal, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN COUNT (*) OVER (PARTITION BY userid) ";
            sql += "            ELSE 0 ";
            sql += "         END AS rowspan ";
            sql += "    FROM (SELECT   a.tid, b.userid, b.subj, b.YEAR, b.subjseq, c.subjnm, ";
            sql += "                   c.biyong, d.upperclass, d.middleclass, d.lowerclass, ";
            sql += "                   c.edustart, c.eduend, a.paymethod, ";
            sql += "                   c.grcode, c.gyear, c.grseq, ";
            sql += "                   e.name, e.resno, e.comptext, b.appdate, e.email, e.handphone, ";
            sql += "                   CASE ";
            sql += "                      WHEN resultcode = '00' ";
            sql += "                         THEN '성공' ";
            sql += "                      ELSE '실패' ";
            sql += "                   END AS RESULT, a.pgauthdate, a.pgauthtime, a.inputname, a.price, ";
            sql += "                   RANK () OVER (PARTITION BY b.tid ORDER BY a.pgauthdate, b.subj,b.appdate) AS RANK ";
            sql += "              FROM tz_billinfo a INNER JOIN tz_propose b ON a.tid = b.tid ";
            sql += "                   INNER JOIN tz_subjseq c ";
            sql += "                   ON b.subj = c.subj ";
            sql += "                 AND b.YEAR = c.YEAR ";
            sql += "                 AND b.subjseq = c.subjseq ";
            sql += "                   INNER JOIN tz_subj d ON b.subj = d.subj ";
            sql += "                   INNER JOIN tz_member e ON b.userid = e.userid ";

            sql += "                 AND b.chkfinal = 'Y' ";

            if (!ss_grcode.equals("ALL")) {
                sql += " and c.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and c.gyear = " + SQLString.Format(v_year) + "\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and c.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and d.middleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_lclass.equals("ALL"))
                sql += " and d.lowerclass = " + SQLString.Format(ss_lclass) + "\n";

            if (!v_startdate.equals("")) {//
                sql += "  and c.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and c.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += "          ORDER BY b.tid, b.userid, b.YEAR) a ";
            sql += "ORDER BY YEAR DESC, tid, userid, RANK, pgauthdate DESC ";

            //System.out.println("StatisticsAdminBean =========== : selectEnterStudentSaleList: " + sql);
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
     * 입과현황 - 수강생별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterStudentGubunList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        // String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        // String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        // String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건
        String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "        gubun, ";

            if ("MEM".equals(ss_option3)) {
                sql += "    (SELECT codenm ";
                sql += "       FROM tz_code ";
                sql += "      WHERE gubun = '0029' AND code = tot.gubun) ";
            } else if ("GEN".equals(ss_option3)) {
                sql += "    (CASE WHEN gubun = '1' THEN '남자' WHEN gubun='2' THEN '여자' ";
                sql += "          ELSE '익명' END) ";
            } else if ("YEA".equals(ss_option3)) {
                sql += "    (CASE WHEN gubun <= 10 THEN '10 대' ";
                sql += "          WHEN gubun = 20 THEN '20 대' ";
                sql += "          WHEN gubun = 30 THEN '30 대' ";
                sql += "          WHEN gubun = 40 THEN '40 대' ";
                sql += "          WHEN gubun = 50 THEN '50 대' ";
                sql += "          ELSE '60 대 이상' ";
                sql += "     END) ";
            } else if ("SCH".equals(ss_option3)) {
                sql += "    DECODE(tot.gubun,'99','기타',(SELECT codenm ";
                sql += "                                                                FROM tz_code ";
                sql += "                                                               WHERE gubun = '0069' AND code = tot.gubun)) ";
            } else if ("CUL".equals(ss_option3)) {
                sql += "    (SELECT codenm ";
                sql += "       FROM tz_code ";
                sql += "      WHERE gubun = '0092' AND code = tot.gubun) ";
            } else if ("NCU".equals(ss_option3)) {
                sql += "    (SELECT codenm ";
                sql += "       FROM tz_code ";
                sql += "      WHERE gubun = '0091' AND code = tot.gubun) ";
            }

            sql += "        gubunnm, ";
            sql += "        cnt, ";
            sql += "       ROUND (ratio_to_report (cnt) OVER (), 2) * 100 rate ";
            sql += "  FROM (SELECT   gubun, SUM (cnt) cnt ";
            sql += "            FROM (";
            sql += "                  SELECT ";

            if ("MEM".equals(ss_option3)) {
                sql += "                     UPPER (NVL (TRIM (b.membergubun), 'P')) ";
            } else if ("GEN".equals(ss_option3)) {
                sql += "                     SEX ";
            } else if ("YEA".equals(ss_option3)) {
                //비정상적 주민등록번호가 존재하여 날짜연산 불가 
                //sql += "                     TRUNC((TO_DATE (TO_CHAR (SYSDATE, 'yyyymmdd')) - "; 
                //sql += "                               TO_date(DECODE (SUBSTR (resno, 7, 1), ";
                //sql += "                                                         '1', '19', ";
                //sql += "                                                         '2', '19', ";
                //sql += "                                                         '20' ";
                //sql += "                                                        ) ";
                //sql += "                                              || SUBSTR (resno, 0, 6),'yyyymmdd')) /365,-1) ";
                sql += "                CASE WHEN (";
                sql += "                     TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, 'yyyy')) - ";
                sql += "                               TO_NUMBER(MEMBERYEAR)),-1) ";
                sql += "                    ) IN (0, 10, 20, 30, 40, 50, 60)";
                sql += "                    THEN (";
                sql += "                     TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, 'yyyy')) - ";
                sql += "                               TO_NUMBER(MEMBERYEAR)),-1) ";
                sql += "                    ) ELSE 60 END";
            } else if ("SCH".equals(ss_option3)) {
                sql += "                     NVL((SELECT code FROM tz_code cd where cd.gubun = '0069' and cd.code = b.degree),'99') ";
            } else if ("CUL".equals(ss_option3)) {
                sql += "                     b.job_culture ";
            } else if ("NCU".equals(ss_option3)) {
                sql += "                     b.job ";
            }

            sql += "                                                                gubun, 1 cnt ";
            sql += "                    FROM tz_student a INNER JOIN tz_member b ";
            sql += "                         ON a.userid = b.userid ";
            sql += "                         INNER JOIN tz_subjseq c ";
            sql += "                         ON a.subj = c.subj ";
            sql += "                       AND a.YEAR = c.YEAR ";
            sql += "                       AND a.subjseq = c.subjseq ";
            sql += "                         INNER JOIN tz_subj d ON c.subj = d.subj ";

            if (!ss_grcode.equals("ALL")) {
                sql += " and c.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //sql+= " and c.gyear = "+SQLString.Format(v_year) +"\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and c.grseq = " + SQLString.Format(ss_grseq);
            /*
             * if (!ss_uclass.equals("ALL")) sql+=
             * " and d.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and d.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and d.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             */
            if (!v_startdate.equals("")) {//
                sql += "  and c.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and c.edustart <=" + SQLString.Format(v_enddate);
            }

            if ("CUL".equals(ss_option3)) {
                sql += "  and b.job_culture IS NOT NULL ";
            } else if ("NCU".equals(ss_option3)) {
                sql += "  and b.job_culture IS NULL and b.job IS NOT NULL ";
            }

            sql += "                         ) a ";
            sql += "        GROUP BY gubun) tot ";

            //System.out.println("StatisticsAdminBean =========== : selectEnterStudentGubunList: " + sql);
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
     * 입과현황 - 분야별 - 일반
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterFieldList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        // String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건
        // String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "   UPPERCLASS, ";
            sql += "   MIDDLECLASS, ";
            sql += "   (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS = A.UPPERCLASS AND MIDDLECLASS = A.MIDDLECLASS AND LOWERCLASS = '000') MIDDLECLASSNAME, ";
            sql += "   LOWERCLASS, ";
            sql += "   (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS = A.UPPERCLASS AND MIDDLECLASS = A.MIDDLECLASS AND LOWERCLASS = A.LOWERCLASS) LOWERCLASSNAME, ";
            sql += "   RANK, ";
            sql += "   SUBJ_CNT, ";
            sql += "   CLASS_CNT, ";
            sql += "   SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS) AS TOTAL, ";
            sql += "   CASE WHEN RANK = 1 THEN COUNT(*) OVER (PARTITION BY UPPERCLASS, MIDDLECLASS) ELSE 0 END AS ROWSPAN, ";
            sql += "   DECODE(SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS),0,0,ROUND((CLASS_CNT / SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS)) * 100,2)) AS LOWERCLASS_RATE, ";
            sql += "   CASE WHEN RANK = 1 THEN SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS, MIDDLECLASS) ELSE 0 END AS MIDDLECLASS_CNT, ";
            sql += "   CASE WHEN RANK = 1 THEN SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS) ELSE 0 END AS TOTAL_CNT,   ";
            sql += "   DECODE(SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS),0,0,CASE WHEN RANK = 1 THEN ROUND((SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS, MIDDLECLASS) / SUM(CLASS_CNT) OVER (PARTITION BY UPPERCLASS)) * 100, 2) ELSE 0 END) AS MIDDLECLASS_RATE ";
            sql += " FROM ";
            sql += " (  ";
            sql += "   SELECT ";
            sql += "     UPPERCLASS, ";
            sql += "     MIDDLECLASS,  ";
            sql += "     LOWERCLASS, ";
            sql += "     COUNT( USERID) CLASS_CNT, ";
            sql += "     COUNT(DISTINCT SUBJ) AS SUBJ_CNT, ";
            sql += "     RANK() OVER (PARTITION BY UPPERCLASS, MIDDLECLASS ORDER BY UPPERCLASS, MIDDLECLASS, LOWERCLASS) AS RANK ";
            sql += "   FROM ";
            sql += "   ( ";
            sql += "     SELECT ";
            sql += "         A.SUBJCLASS, ";
            sql += "         A.UPPERCLASS,  ";
            sql += "         A.MIDDLECLASS,  ";
            sql += "         A.LOWERCLASS,  ";
            sql += "         A.SUBJNM,  ";
            sql += "         B.SUBJ, ";
            sql += "         B.YEAR, ";
            sql += "         B.SUBJSEQ, ";
            sql += "         C.USERID ";
            sql += "     FROM ";
            sql += "       TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "       ON A.SUBJ = B.SUBJ  ";
            sql += "       LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "       ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "       LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";
            sql += "       WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and b.gyear = " + SQLString.Format(v_year) + "\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and a.upperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and a.middleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_lclass.equals("ALL"))
                sql += " and a.lowerclass = " + SQLString.Format(ss_lclass) + "\n";

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += "     ORDER BY SUBJ, YEAR, SUBJSEQ ";
            sql += "   ) ";
            sql += "   GROUP BY UPPERCLASS, MIDDLECLASS, LOWERCLASS ";
            sql += " ) A ";
            sql += " ORDER BY UPPERCLASS, MIDDLECLASS, LOWERCLASS ";

            //System.out.println("StatisticsAdminBean =========== : selectEnterFieldList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_lowerclass_rate", new Double(ls.getDouble("lowerclass_rate")));
                dbox.put("d_middleclass_rate", new Double(ls.getDouble("middleclass_rate")));

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
     * 입과현황 - 분야별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterFieldGubunList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        ArrayList<DataBox> listGubun = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        // String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건
        String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        listGubun = this.selectGubunList(box);

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "   UPPERCLASS, ";
            sql += "   MIDDLECLASS, ";
            sql += "   (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS = A.UPPERCLASS AND MIDDLECLASS = A.MIDDLECLASS AND LOWERCLASS = '000') MIDDLECLASSNAME, ";
            sql += "   LOWERCLASS, ";
            sql += "   (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS = A.UPPERCLASS AND MIDDLECLASS = A.MIDDLECLASS AND LOWERCLASS = A.LOWERCLASS) LOWERCLASSNAME, ";
            sql += "   RANK, ";
            sql += "   SUBJ_CNT, ";

            for (int j = 0; j < listGubun.size(); j++) {
                sql += "   CASE WHEN RANK = 1 THEN SUM(CNT" + (j + 1) + ") OVER (PARTITION BY UPPERCLASS ,MIDDLECLASS) ELSE 0 END AS FIELD_CNT" + (j + 1) + ", ";
                sql += "   SUM(CNT" + (j + 1) + ") OVER (PARTITION BY UPPERCLASS ,MIDDLECLASS, LOWERCLASS) AS CNT" + (j + 1) + ", ";
            }

            sql += "   CASE WHEN RANK = 1 THEN COUNT(*) OVER (PARTITION BY UPPERCLASS, MIDDLECLASS) ELSE 0 END AS ROWSPAN ";
            sql += " FROM ";
            sql += " (  ";
            sql += "   SELECT ";
            sql += "     UPPERCLASS, ";
            sql += "     MIDDLECLASS,  ";
            sql += "     LOWERCLASS, ";
            sql += "     COUNT(DISTINCT SUBJ) AS SUBJ_CNT, ";

            for (int j = 0; j < listGubun.size(); j++) {
                sql += "     SUM(CNT" + (j + 1) + ") CNT" + (j + 1) + ", ";
            }

            sql += "     RANK() OVER (PARTITION BY UPPERCLASS, MIDDLECLASS ORDER BY UPPERCLASS, MIDDLECLASS, LOWERCLASS) AS RANK ";
            sql += "   FROM ";
            sql += "   ( ";
            sql += "     SELECT ";
            sql += "         A.SUBJCLASS, ";
            sql += "         A.UPPERCLASS,  ";
            sql += "         A.MIDDLECLASS,  ";
            sql += "         A.LOWERCLASS,  ";
            sql += "         A.SUBJNM,  ";
            sql += "         B.SUBJ, ";
            sql += "         B.YEAR, ";

            for (int j = 0; j < listGubun.size(); j++) {
                dboxGubun = (DataBox) listGubun.get(j);
                sql += dboxGubun.getString("d_condition") + " CNT" + (j + 1) + ",";
            }
            sql += "         B.SUBJSEQ ";
            sql += "     FROM ";
            sql += "       TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "       ON A.SUBJ = B.SUBJ  ";
            sql += "       LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "       ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "       LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "       WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and b.gyear = " + SQLString.Format(v_year) + "\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and a.upperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and a.middleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_lclass.equals("ALL"))
                sql += " and a.lowerclass = " + SQLString.Format(ss_lclass) + "\n";

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            if ("GEN".equals(ss_option3) || "YEA".equals(ss_option3)) {
                sql += "  and d.resno IS NOT NULL and LEN(d.resno) > 12 and SUBSTR(d.resno,7,1) IN ('1','2','3','4')";
            } else if ("CUL".equals(ss_option3)) {
                sql += "  and d.job_culture IS NOT NULL ";
            } else if ("NCU".equals(ss_option3)) {
                sql += "  and d.job_culture IS NULL and d.job IS NOT NULL ";
            }

            sql += "     ORDER BY SUBJ, YEAR, SUBJSEQ ";
            sql += "   ) ";
            sql += "   GROUP BY UPPERCLASS, MIDDLECLASS, LOWERCLASS ";
            sql += " ) A ";
            sql += " ORDER BY UPPERCLASS, MIDDLECLASS, LOWERCLASS ";

            //System.out.println("StatisticsAdminBean =========== : selectEnterFieldList: " + sql);
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
     * 입과현황 - 과정별 - 일반
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        // String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        // String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        // String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건
        // String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "   SUBJNM,  ";
            sql += "   SUBJ, ";
            sql += "   YEAR, ";
            sql += "   SUBJSEQ, ";
            sql += "   SUM(CNT) CNT ";
            sql += " FROM ";
            sql += " ( ";
            sql += "   SELECT ";
            sql += "       A.SUBJCLASS, ";
            sql += "       A.SUBJNM,  ";
            sql += "       B.SUBJ, ";
            sql += "       B.YEAR, ";
            sql += "       B.SUBJSEQ, ";
            sql += "       DECODE(C.USERID,NULL,0,1) CNT ";
            sql += "   FROM ";
            sql += "     TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "     ON A.SUBJ = B.SUBJ  ";
            sql += "     LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "     ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "     LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";
            sql += "   WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //sql+= " and b.gyear = "+SQLString.Format(v_year) +"\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            /*
             * if (!ss_uclass.equals("ALL")) sql+=
             * " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             */

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += "   ORDER BY CNT DESC ";
            sql += " ) A ";
            sql += " GROUP BY SUBJNM, SUBJ, YEAR, SUBJSEQ ORDER BY CNT DESC ";

            //System.out.println("StatisticsAdminBean =========== : selectEnterCourseList: " + sql);
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
     * 입과현황 - 과정별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterCourseGubunList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        ArrayList<DataBox> listGubun = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        // String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        // String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        // String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건
        String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        listGubun = this.selectGubunList(box);

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "   SUBJNM,  ";

            for (int j = 0; j < listGubun.size(); j++) {
                sql += "   SUM(CNT" + (j + 1) + ") AS CNT" + (j + 1) + ", ";
            }

            sql += "   SUBJ, COUNT(*) AS CCNT ";
            sql += " FROM ";
            sql += " ( ";
            sql += "   SELECT ";
            sql += "       A.SUBJNM,  ";

            for (int j = 0; j < listGubun.size(); j++) {
                dboxGubun = (DataBox) listGubun.get(j);
                sql += dboxGubun.getString("d_condition") + " CNT" + (j + 1) + ",";
            }

            sql += "       B.SUBJ ";
            sql += "   FROM ";
            sql += "     TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "     ON A.SUBJ = B.SUBJ  ";
            sql += "     LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "     ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "     LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "  WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //sql+= " and b.gyear = "+SQLString.Format(v_year) +"\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            /*
             * if (!ss_uclass.equals("ALL")) sql+=
             * " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             */
            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            if ("GEN".equals(ss_option3) || "YEA".equals(ss_option3)) {
                //sql += "  and d.resno IS NOT NULL and LEN(d.resno) > 12 and SUBSTR(d.resno,7,1) IN ('1','2','3','4')";
            } else if ("CUL".equals(ss_option3)) {
                sql += "  and d.job_culture IS NOT NULL ";
            } else if ("NCU".equals(ss_option3)) {
                sql += "  and d.job_culture IS NULL and d.job IS NOT NULL ";
            }

            sql += "   ORDER BY SUBJ, YEAR, SUBJSEQ ";
            sql += " ) A ";
            sql += " GROUP BY SUBJNM, SUBJ ORDER BY CCNT DESC";

            //System.out.println("StatisticsAdminBean =========== : selectEnterCourseGubunList: " + sql);
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
     * 입과현황 - 차수별 - 일반
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterSeqList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        // String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        // String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        // String ss_option1 = box.getStringDefault("s_option1", ""); //통계구분
        // String ss_option2 = box.getStringDefault("s_option2", "ALL"); //통계별 조건
        // String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        // String v_order = box.getString("p_order"); //정렬할 컬럼명
        // String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "SELECT ";
            sql += "       GRCODE, ";
            sql += "       GYEAR, ";
            sql += "       GRSEQ, ";
            sql += "       (SELECT GRSEQNM FROM TZ_GRSEQ WHERE GRCODE = A.GRCODE AND GYEAR = A.GYEAR AND GRSEQ = A.GRSEQ) GRSEQNM, ";
            sql += "       AREA, ";
            sql += "       DECODE(AREA,'G0','게임','K0','문화','B0','방송') as  AREANAME, ";
            sql += "       RANK, ";
            sql += "       SUM(CLASS_CNT) OVER (PARTITION BY AREA) AS TOTAL, ";
            sql += "       CASE WHEN RANK=1 THEN 3 ELSE 0 END  AS ROWSPAN, ";
            sql += "       SUM(class_cnt) OVER(PARTITION BY grcode,gyear,grseq,area)  AS AREA_CNT, ";
            sql += "       DECODE(SUM(CLASS_CNT) OVER (PARTITION BY AREA),0,0, ROUND((SUM(CLASS_CNT) OVER (PARTITION BY GRCODE, GYEAR, GRSEQ, AREA) / SUM(CLASS_CNT) OVER (PARTITION BY GRCODE, GYEAR, GRSEQ) * 100),2)) AREA_RATE ";
            sql += "  FROM ";
            sql += "      (  ";
            sql += "        SELECT ";
            sql += "          GRCODE, ";
            sql += "          GYEAR, ";
            sql += "          GRSEQ, ";
            sql += "          AREA, ";
            sql += "          COUNT(USERID) CLASS_CNT, ";
            sql += "          RANK() OVER (PARTITION BY GRCODE, GYEAR, GRSEQ ORDER BY GRCODE, GYEAR, GRSEQ, AREA) AS RANK ";
            sql += "        FROM ";
            sql += "             ( ";
            sql += "               SELECT ";
            sql += "                   B.GRCODE, ";
            sql += "                   B.GYEAR, ";
            sql += "                   B.GRSEQ, ";
            sql += "                   A.SUBJCLASS, A.AREA,";
            sql += "                   C.USERID ";
            sql += "               FROM ";
            sql += "                 TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                 ON A.SUBJ = B.SUBJ  ";
            sql += "                 LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                 ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                 LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";
            sql += "       WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //sql+= " and b.gyear = "+SQLString.Format(v_year) +"\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);

            /*
             * if (!ss_uclass.equals("ALL")) sql+=
             * " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             */

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += "               ORDER BY SUBJ, YEAR, SUBJSEQ ";
            sql += "             ) ";
            sql += "   GROUP BY GRCODE, GYEAR, GRSEQ, AREA ";
            sql += " ) A ";
            sql += " ORDER BY GRCODE, GYEAR, GRSEQ, AREA ";

            //System.out.println("StatisticsAdminBean =========== : selectEnterSeqList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_area_rate", new Double(ls.getDouble("area_rate")));

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
     * 입과현황 - 차수별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectEnterSeqGubunList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        ArrayList<DataBox> listGubun = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일
        String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        listGubun = this.selectGubunList(box);

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "       GRCODE, ";
            sql += "       GYEAR, ";
            sql += "       GRSEQ, ";
            sql += "       SUBJ, ";
            sql += "       SUBJNM, ";

            for (int j = 0; j < listGubun.size(); j++) {
                sql += "   SUM(CNT" + (j + 1) + ") OVER (PARTITION BY GRCODE, GYEAR, GRSEQ, SUBJ, SUBJNM) AS CNT" + (j + 1) + ", ";
            }

            sql += "       (SELECT GRSEQNM FROM TZ_GRSEQ WHERE GRCODE = A.GRCODE AND GYEAR = A.GYEAR AND GRSEQ = A.GRSEQ) GRSEQNM, ";
            sql += "       RANK, ";
            sql += "       CASE WHEN RANK = 1 THEN COUNT(*) OVER (PARTITION BY GRCODE, GYEAR, GRSEQ) ELSE 0 END AS ROWSPAN ";
            sql += "  FROM ";
            sql += "      (  ";
            sql += "        SELECT ";
            sql += "          GRCODE, ";
            sql += "          GYEAR, ";
            sql += "          GRSEQ, ";
            sql += "          SUBJ, ";
            sql += "          SUBJNM, ";

            for (int j = 0; j < listGubun.size(); j++) {
                sql += "      SUM(CNT" + (j + 1) + ") CNT" + (j + 1) + ", ";
            }

            sql += "          RANK() OVER (PARTITION BY GRCODE, GYEAR, GRSEQ ORDER BY GRCODE, GYEAR, GRSEQ, SUBJ, SUBJNM) AS RANK ";
            sql += "        FROM ";
            sql += "             ( ";
            sql += "               SELECT ";
            sql += "                   B.GRCODE, ";
            sql += "                   B.GYEAR, ";
            sql += "                   B.GRSEQ, ";
            sql += "                   B.SUBJ, ";

            for (int j = 0; j < listGubun.size(); j++) {
                dboxGubun = (DataBox) listGubun.get(j);
                sql += dboxGubun.getString("d_condition") + " CNT" + (j + 1) + ",";
            }

            sql += "                   A.SUBJNM ";
            sql += "               FROM ";
            sql += "                 TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                 ON A.SUBJ = B.SUBJ  ";
            sql += "                 LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                 ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                 LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "  WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //sql+= " and b.gyear = "+SQLString.Format(v_year) +"\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            /*
             * if (!ss_uclass.equals("ALL")) sql+=
             * " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             */

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            if ("GEN".equals(ss_option3) || "YEA".equals(ss_option3)) {
                //sql += "  and d.resno IS NOT NULL and LEN(d.resno) > 12 and SUBSTR(d.resno,7,1) IN ('1','2','3','4')";
            } else if ("CUL".equals(ss_option3)) {
                sql += "  and d.job_culture IS NOT NULL ";
            } else if ("NCU".equals(ss_option3)) {
                sql += "  and d.job_culture IS NULL and d.job IS NOT NULL ";
            }

            sql += "               ORDER BY SUBJ, YEAR, SUBJSEQ ";
            sql += "             ) ";
            sql += "   GROUP BY GRCODE, GYEAR, GRSEQ, SUBJ, SUBJNM ";
            sql += " ) A ";
            sql += " ORDER BY GRCODE, GYEAR, GRSEQ, SUBJ, SUBJNM ";

            //System.out.println("StatisticsAdminBean =========== : selectEnterSeqGubunList: " + sql);
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
     * 수료현황 - 전체
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectPassAllList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_comp = box.getStringDefault("s_comp", "01"); //교육그룹
        String ss_area = box.getStringDefault("s_area", "ALL"); //교육그룹

        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도

        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일

        String v_startdate = ss_startdate.replace("-", "") + "00";
        String v_enddate = ss_enddate.replace("-", "") + "23";

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "SELECT decode(E.COMP,'01','B2C','02','공공','03','기업','04','대학','05','특화고','기타') AS COMP, ";
            sql += "decode(A.AREA,'B0','방송','G0','게임','K0','문화') as AREA, ";
            sql += " SUM(DECODE(C.USERID, NULL, 0, 1)) USER_CNT, ";
            sql += " SUM(DECODE(C.ISGRADUATED, 'Y', 1, 0)) GRAD_CNT, ";

            sql += " SUM(CASE SEX when '1' THEN 1 else 0 END) MAN, ";
            sql += "SUM(CASE SEX when '2' THEN 1 else 0 END) WOMAN ";

            sql += "FROM   TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ON A.SUBJ = B.SUBJ ";
            sql += "    LEFT OUTER JOIN TZ_STUDENT C ON B.SUBJ = C.SUBJ ";
            sql += "    INNER JOIN TZ_GRCODE E ON B.GRCODE = E.GRCODE    ";

            sql += "AND    B.YEAR = C.YEAR ";
            sql += "AND    B.SUBJSEQ = C.SUBJSEQ LEFT JOIN TZ_MEMBER D ON D.USERID = C.USERID ";
            sql += "WHERE  (1=1) ";
            sql += "and    E.COMP = '" + ss_comp + "' ";

            if (!ss_area.equals("ALL")) {
                sql += "and    A.AREA = '" + ss_area + "' ";
            }

            //sql += "and    b.gyear = '2012' ";
            sql += "and    B.EDUSTART >= '" + v_startdate + "' ";
            sql += "AND    B.EDUEND <= '" + v_enddate + "' ";
            sql += " GROUP BY E.COMP, A.AREA";

            /*
             * sql = " SELECT "; sql += "   GRCODE, "; sql += "   GYEAR, "; sql
             * += "   GRSEQ, "; sql += "   EDUSTART, "; sql += "   EDUEND, ";
             * sql +=
             * "   (SELECT GRSEQNM FROM TZ_GRSEQ WHERE GRCODE = A.GRCODE AND GYEAR = A.GYEAR AND GRSEQ = A.GRSEQ) GRSEQNM, "
             * ; sql += "   SUM(USER_CNT) USER_CNT, "; sql +=
             * "   SUM(GRAD_CNT) GRAD_CNT, "; sql +=
             * "   ROUND(DECODE(SUM(USER_CNT),0,0,SUM(GRAD_CNT) / SUM(USER_CNT) * 100),2) GRAD_RATE"
             * ; sql += " FROM "; sql += "      ( "; sql += "        SELECT ";
             * sql += "            B.GRCODE, "; sql += "            B.GYEAR, ";
             * sql += "            B.GRSEQ, "; sql +=
             * "            (SELECT MIN(B.EDUSTART) FROM TZ_SUBJSEQ WHERE SUBJ = B.SUBJ AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ) EDUSTART, "
             * ; sql +=
             * "            (SELECT MAX(B.EDUEND) FROM TZ_SUBJSEQ WHERE SUBJ = B.SUBJ AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ) EDUEND, "
             * ; sql += "            DECODE(D.USERID,NULL,0,1) USER_CNT, "; sql
             * += "            DECODE(C.ISGRADUATED,'Y',1,0) GRAD_CNT, "; sql +=
             * "            A.SUBJNM "; sql += "        FROM "; sql +=
             * "          TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B "; sql +=
             * "          ON A.SUBJ = B.SUBJ  "; sql +=
             * "          LEFT OUTER JOIN TZ_STUDENT C "; sql +=
             * "          ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ "
             * ; sql +=
             * "          LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";
             * 
             * sql += "       WHERE (1=1)";
             * 
             * if (!ss_grcode.equals("ALL")) { sql+=
             * " and b.grcode = "+SQLString.Format(ss_grcode) +"\n"; sql+=
             * " and b.gyear = "+SQLString.Format(v_year) +"\n"; } if
             * (!ss_grseq.equals("ALL")) sql+=
             * " and b.grseq = "+SQLString.Format(ss_grseq); if
             * (!ss_uclass.equals("ALL")) sql+=
             * " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             * 
             * if(!v_startdate.equals("") ){// sql +=
             * "  and b.edustart  >="+SQLString.Format(v_startdate); }
             * 
             * if(!v_enddate.equals("") ){// sql +=
             * "  and b.edustart <="+SQLString.Format(v_enddate); }
             * 
             * sql += "        ORDER BY SUBJ, YEAR, SUBJSEQ "; sql +=
             * "      ) A "; sql +=
             * " GROUP BY GRCODE, GYEAR, GRSEQ, EDUSTART, EDUEND ";
             */

            //System.out.println("StatisticsAdminBean =========== : selectPassAllList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                //dbox.put("d_grad_rate"	, new Double(ls.getDouble("grad_rate")));

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
     * 수료현황 - 과정별
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectPassCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += " SELECT    GRCODE, decode(AREA,'B0','방송','G0','게임','K0','문화') as AREA, ";
            sql += "           GYEAR, ";
            sql += "           GRSEQ, ";
            sql += "           EDUSTART, ";
            sql += "           EDUEND, ";
            sql += "           SUBJ, ";
            sql += "           YEAR, ";
            sql += "           SUBJSEQ, ";
            sql += "           SUBJNM, ";
            sql += "           USER_CNT, ";
            sql += "           GRAD_CNT, ";
            sql += "           ROUND(DECODE(USER_CNT,0,0,GRAD_CNT / USER_CNT * 100),2) GRAD_RATE, ";
            sql += "           NGRAD_CNT, ";
            sql += "           (SELECT COUNT(*) FROM TZ_SULEACH WHERE SUBJ = A.SUBJ AND GRCODE = A.GRCODE AND YEAR = A.YEAR AND SUBJSEQ = A.SUBJSEQ AND SULPAPERNUM = A.SULPAPERNUM2) SUL_CNT2, ";
            sql += "           ROUND((DECODE(USER_CNT,0,0,(SELECT COUNT(*) FROM TZ_SULEACH WHERE SUBJ = A.SUBJ AND GRCODE = A.GRCODE AND YEAR = A.YEAR AND SUBJSEQ = A.SUBJSEQ AND SULPAPERNUM = A.SULPAPERNUM2) / USER_CNT * 100)),2) SUL_RATE ";
            sql += "  FROM ( ";
            sql += "         SELECT ";
            sql += "           GRCODE, AREA, ";
            sql += "           GYEAR, ";
            sql += "           GRSEQ, ";
            sql += "           EDUSTART, ";
            sql += "           EDUEND, ";
            sql += "           SUBJ, ";
            sql += "           YEAR, ";
            sql += "           SUBJSEQ, ";
            sql += "           SUBJNM, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUM(USER_CNT) USER_CNT, ";
            sql += "           SUM(GRAD_CNT) GRAD_CNT, ";
            sql += "           SUM(NGRAD_CNT) NGRAD_CNT ";
            sql += "         FROM ";
            sql += "              ( ";
            sql += "                SELECT ";
            sql += "                    A.AREA, B.SUBJ, ";
            sql += "                    B.YEAR, ";
            sql += "                    B.SUBJNM, ";
            sql += "                    B.SUBJSEQ, ";
            sql += "                    B.EDUSTART, ";
            sql += "                    B.EDUEND, ";
            sql += "                    B.GRCODE, ";
            sql += "                    B.GYEAR, ";
            sql += "                    B.GRSEQ, ";
            sql += "                    DECODE(C.USERID,NULL,0,1) USER_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'Y',1,0) GRAD_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'N',1,0) NGRAD_CNT, ";
            sql += "                    B.SULPAPERNUM2 ";
            sql += "                FROM ";
            sql += "                  TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                  ON A.SUBJ = B.SUBJ  ";
            sql += "                  LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                  ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                  LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "       WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //          		sql+= " and b.gyear = "+SQLString.Format(v_year) +"\n";
            }
            /*
             * if (!ss_grseq.equals("ALL")) sql+=
             * " and b.grseq = "+SQLString.Format(ss_grseq); if
             * (!ss_uclass.equals("ALL")) sql+=
             * " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             */
            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += "                ORDER BY AREA ";
            sql += "              )  ";
            sql += "    GROUP BY   ";
            sql += "           GRCODE, AREA, ";
            sql += "           GYEAR, ";
            sql += "           GRSEQ, ";
            sql += "           EDUSTART, ";
            sql += "           EDUEND, ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM, ";
            sql += "           YEAR, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUBJSEQ ";
            sql += " ) A ORDER BY AREA, USER_CNT DESC ";

            //System.out.println("StatisticsAdminBean =========== : selectPassCourseList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_grad_rate", new Double(ls.getDouble("grad_rate")));
                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));

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
     * 수료현황 - 개인별
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectPassPersonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "     D.USERID, ";
            sql += "     D.NAME, ";
            sql += "     B.SUBJ, ";
            sql += "     B.YEAR, ";
            sql += "     B.EDUSTART, ";
            sql += "     B.EDUEND, ";
            sql += "     B.SUBJNM, ";
            sql += "     B.SUBJSEQ, ";
            sql += "     B.GRCODE, ";
            sql += "     B.GYEAR, ";
            sql += "     B.GRSEQ, ";
            sql += "     C.ISGRADUATED, ";
            sql += "     C.SCORE, ";
            sql += "     C.TSTEP, ";
            sql += "     C.MTEST, ";
            sql += "     C.FTEST, ";
            sql += "     C.HTEST, ";
            sql += "     C.REPORT, ";
            sql += "     C.ETC1, ";
            sql += "     C.ETC2, ";
            sql += "     C.AVTSTEP, ";
            sql += "     C.AVMTEST, ";
            sql += "     C.AVFTEST, ";
            sql += "     C.AVHTEST, ";
            sql += "     C.AVREPORT, ";
            sql += "     C.AVETC1, ";
            sql += "     C.AVETC2, ";
            sql += "     (SELECT COUNT(*) FROM TZ_EXAMRESULT WHERE SUBJ = B.SUBJ AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ AND USERID = C.USERID) EXAM_CNT, ";
            sql += "     (SELECT COUNT(*) FROM TZ_SULEACH WHERE SUBJ = B.SUBJ AND GRCODE = B.GRCODE AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ AND SULPAPERNUM = B.SULPAPERNUM AND USERID = C.USERID) SUL_CNT1, ";
            sql += "     (SELECT COUNT(*) FROM TZ_SULEACH WHERE SUBJ = B.SUBJ AND GRCODE = B.GRCODE AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ AND SULPAPERNUM = B.SULPAPERNUM2 AND USERID = C.USERID) SUL_CNT2, ";
            sql += "     NVL((SELECT DISTCODE1_AVG FROM TZ_SULEACH WHERE SUBJ = B.SUBJ AND GRCODE = B.GRCODE AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ AND USERID = C.USERID AND SULPAPERNUM = B.SULPAPERNUM2),0) SUL_RATE, ";
            sql += "     (SELECT COUNT(*) FROM TZ_QNA WHERE SUBJ = B.SUBJ AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ) QNA_CNT ";
            sql += " FROM ";
            sql += "   TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "   ON A.SUBJ = B.SUBJ  ";
            sql += "   INNER JOIN TZ_STUDENT C ";
            sql += "   ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "   INNER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";
            sql += "       WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //sql+= " and b.gyear = "+SQLString.Format(v_year) +"\n";
            }
            //if (!ss_grseq.equals("ALL"))      sql+= " and b.grseq = "+SQLString.Format(ss_grseq);

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += " ORDER BY SUBJ, YEAR, SUBJSEQ ";

            //System.out.println("StatisticsAdminBean =========== : selectPassPersonList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));

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
     * 학습현황
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectStudyList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT ";
            sql += "         GRCODE, ";
            sql += "         GYEAR, ";
            sql += "         GRSEQ, ";
            sql += "         (SELECT GRSEQNM FROM TZ_GRSEQ WHERE GRCODE = A.GRCODE AND GYEAR = A.GYEAR AND GRSEQ = A.GRSEQ) GRSEQNM, ";
            sql += "         (SELECT MIN(EDUSTART) FROM TZ_SUBJSEQ WHERE GRCODE = A.GRCODE AND GYEAR = A.GYEAR AND GRSEQ = A.GRSEQ) EDUSTART, ";
            sql += "         (SELECT MAX(EDUEND) FROM TZ_SUBJSEQ WHERE GRCODE = A.GRCODE AND GYEAR = A.GYEAR AND GRSEQ = A.GRSEQ) EDUEND, ";
            sql += "         COUNT(DISTINCT SUBJ) SUBJ_CNT, ";
            sql += "         SUM(USER_CNT) USER_CNT, ";
            sql += "         SUM(GRAD_CNT) GRAD_CNT, ";
            sql += "         ROUND(DECODE(SUM(USER_CNT),0,0,SUM(GRAD_CNT) / SUM(USER_CNT) * 100),2) GRAD_RATE, ";
            sql += "         ROUND(AVG(SUL_RATE),2) SUL_RATE, ";
            sql += "         (SELECT COUNT(*) FROM TZ_SULEACH SE, TZ_SUBJSEQ SS WHERE SS.SUBJ = SE.SUBJ AND SS.GRCODE = SE.GRCODE AND SS.YEAR = SE.YEAR AND SS.SUBJSEQ = SE.SUBJSEQ AND SE.SULPAPERNUM = SS.SULPAPERNUM2 AND SS.GRCODE = A.GRCODE AND SS.GYEAR = A. GYEAR AND SS.GRSEQ = A.GRSEQ   ) SUL_CNT2, ";
            sql += "         ROUND(DECODE(SUM(USER_CNT),0,0,((SELECT COUNT(*) FROM TZ_SULEACH SE, TZ_SUBJSEQ SS WHERE SS.SUBJ = SE.SUBJ AND SS.GRCODE = SE.GRCODE AND SS.YEAR = SE.YEAR AND SS.SUBJSEQ = SE.SUBJSEQ AND SE.SULPAPERNUM = SS.SULPAPERNUM2 AND SS.GRCODE = A.GRCODE AND SS.GYEAR = A. GYEAR AND SS.GRSEQ = A.GRSEQ   )) / SUM(USER_CNT) * 100),2) SUL_CNT_RATE ";
            sql += "    FROM ( ";
            sql += "             SELECT ";
            sql += "                    B.GRCODE, ";
            sql += "                    B.GYEAR, ";
            sql += "                    B.GRSEQ,   ";
            sql += "                    DECODE(C.USERID,NULL,0,1) USER_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'Y',1,0) GRAD_CNT, ";
            sql += "                    (SELECT ROUND(AVG(DISTCODE1_AVG),2) FROM TZ_SULEACH WHERE SUBJ = B.SUBJ AND GRCODE = B.GRCODE AND YEAR = B.YEAR AND SUBJSEQ = B.SUBJSEQ AND SULPAPERNUM = B.SULPAPERNUM2) SUL_RATE, ";
            sql += "                    A.SUBJ, ";
            sql += "                    RANK() OVER (PARTITION BY  b.GRCODE, GYEAR, GRSEQ, C.USERID ORDER BY  SUBJ) AS RANK ";
            sql += "                FROM ";
            sql += "                  TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                  ON A.SUBJ = B.SUBJ  ";
            sql += "                  LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                  ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                  LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "       WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                //          		sql+= " and b.gyear = "+SQLString.Format(v_year) +"\n";
            }

            /*
             * if (!ss_grseq.equals("ALL")) sql+=
             * " and b.grseq = "+SQLString.Format(ss_grseq); if
             * (!ss_uclass.equals("ALL")) sql+=
             * " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n"; if
             * (!ss_mclass.equals("ALL")) sql+=
             * " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n"; if
             * (!ss_lclass.equals("ALL")) sql+=
             * " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
             */

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += "                ORDER BY GRCODE, GYEAR, GRSEQ,C.USERID,SUBJ, YEAR, SUBJSEQ ";
            sql += "         ) A ";
            sql += " GROUP BY GRCODE, GYEAR, GRSEQ ";

            //System.out.println("StatisticsAdminBean =========== : selectStudyList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_grad_rate", new Double(ls.getDouble("grad_rate")));
                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));
                dbox.put("d_sul_cnt_rate", new Double(ls.getDouble("sul_cnt_rate")));

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
     * 전체설문 결과분석 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SulmunQuestionExampleData> selectPreSulmunList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<SulmunQuestionExampleData> list = null;

        try {
            connMgr = new DBConnectionManager();
            list = getSulmunResult(connMgr, box);
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
        return list;
    }

    /**
     * 차수별 사전설문
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList<SulmunQuestionExampleData> getSulmunResult(DBConnectionManager connMgr, RequestBox box) throws Exception {
        DataBox dboxSulPaperNum = null;
        ArrayList<DataBox> listSulPaperNum = null;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        String v_year = "";
        if (v_year.equals(""))
            v_year = ss_gyear;

        int v_studentcount = 0;

        Vector<String> v_sulnums = null;
        ArrayList<SulmunQuestionExampleData> QuestionExampleDataList = null;
        Vector<String> v_answers = null;

        listSulPaperNum = this.selectSulPaperNum(box);
        dboxSulPaperNum = (DataBox) listSulPaperNum.get(0);
        int v_sulpapernum = dboxSulPaperNum.getInt("d_sulpapernum");

        try {
            // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            v_sulnums = getSulnums(connMgr, ss_grcode, "ALL", v_sulpapernum);

            // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
            QuestionExampleDataList = getSelnums(connMgr, ss_grcode, "ALL", v_sulnums);

            // 설문지 답변모음을 읽어온다. 벡터(답변1,3,2,3..)
            v_answers = getSulmunAnswers2(connMgr, ss_grcode, ss_gyear, ss_grseq, v_sulpapernum, ss_uclass, ss_mclass, ss_lclass, v_startdate, v_enddate);

            // 리스트((설문번호1, 보기1,2,3..)) + 벡터(답변1,3,2,3..) 이용해서 응답자수 및 비율을 계산한다.
            ComputeCount(QuestionExampleDataList, v_answers);

            // 응답자수
            box.put("p_replycount", String.valueOf(v_answers.size()));

            // 수강자수
            v_studentcount = getStudentCount2(connMgr, ss_grcode, ss_gyear, ss_grseq, v_sulpapernum, ss_uclass, ss_mclass, ss_lclass, v_startdate, v_enddate);

            box.put("p_studentcount", String.valueOf(v_studentcount));

        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return QuestionExampleDataList;
    }

    /**
     * 차수에서사용된 사전 설문지 번호 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectSulPaperNum(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수		
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //실제로 설문지는 과정별로 세팅이 가능하기 때문에 교육차수에 사용된 설문지 번호는 가져올 수 없음
            //강제로 가장 많이 쓰인 설문지 번호를 가져옴
            //시스템에서 현재 사용되는 사전 설문지번호는 6 번임 
            //6으로 하드코딩된 페이지도 있음
            sql = " SELECT SULPAPERNUM ";
            sql += "   FROM ( ";
            sql += " 		SELECT SULPAPERNUM, COUNT(SUBJ) SUBJ_CNT FROM TZ_SUBJSEQ ";
            sql += " 		 WHERE GRCODE = " + SQLString.Format(ss_grcode);
            sql += " 		   AND GYEAR = " + SQLString.Format(ss_gyear);
            sql += " 		   AND GRSEQ = " + SQLString.Format(ss_grseq);
            sql += " 		 GROUP BY SULPAPERNUM ";
            sql += " 		 ORDER BY SUBJ_CNT DESC ";
            sql += " 		) ";
            sql += "  WHERE ROWNUM < 2 ";

            //System.out.println("StatisticsAdminBean =========== : selectSulPaperNum: "+sql);
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

    public Vector<String> getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_sulnums = new Vector<String>();
        String v_tokens = "";
        StringTokenizer st = null;

        try {
            sql = "select sulnums  ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);
            //System.out.println("SulmunAllResultBean 설문번호 getSulnums:" + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            st = new StringTokenizer(v_tokens, SulmunAllBean.SPLIT_COMMA);
            while (st.hasMoreElements()) {
                v_sulnums.add((String) st.nextToken());
            }
        } catch (Exception ex) {
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
        return v_sulnums;
    }

    /**
     * 
     * @param connMgr
     * @param p_grcode
     * @param p_subj
     * @param p_sulnums
     * @return
     * @throws Exception
     */
    public ArrayList<SulmunQuestionExampleData> getSelnums(DBConnectionManager connMgr, String p_grcode, String p_subj, Vector<String> p_sulnums) throws Exception {
        Hashtable<String, SulmunQuestionExampleData> hash = new Hashtable<String, SulmunQuestionExampleData>();
        ArrayList<SulmunQuestionExampleData> list = new ArrayList<SulmunQuestionExampleData>();

        ListSet ls = null;
        String sql = "";
        SulmunQuestionExampleData data = null;
        SulmunExampleData exampledata = null;
        int v_bef_sulnum = 0;

        String v_sulnums = "";
        for (int i = 0; i < p_sulnums.size(); i++) {
            v_sulnums += (String) p_sulnums.get(i);
            if (i < p_sulnums.size() - 1) {
                v_sulnums += ", ";
            }
        }
        if (v_sulnums.equals(""))
            v_sulnums = "-1";

        try {
            sql = "select a.subj,     a.sulnum, ";
            sql += "        a.distcode, c.codenm distcodenm, ";
            sql += "       a.sultype,  d.codenm sultypenm, ";
            sql += "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
            sql += "  from tz_sul     a, ";
            sql += "       tz_sulsel  b, ";
            sql += "       tz_code    c, ";
            sql += "       tz_code    d  ";
            // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
            //            sql+= " where a.subj     = b.subj(+)    ";
            //            sql+= "   and a.sulnum   = b.sulnum(+)  ";
            //            sql+= "   and a.grcode  = b.grcode(+) ";
            sql += " where a.subj      =  b.subj(+)    ";
            sql += "   and a.sulnum    =  b.sulnum(+)  ";
            sql += "   and a.grcode    =  b.grcode(+) ";
            sql += "   and a.distcode = c.code ";
            sql += "   and a.sultype  = d.code ";
            sql += "   and a.subj     = " + SQLString.Format(p_subj);
            sql += "   and a.grcode     = " + SQLString.Format(p_grcode);
            sql += "   and a.sulnum in (" + v_sulnums + ")";
            sql += "   and c.gubun    = " + SQLString.Format(SulmunAllBean.DIST_CODE);
            sql += "   and d.gubun    = " + SQLString.Format(SulmunAllBean.SUL_TYPE);
            sql += " order by a.subj, a.sulnum, b.selnum ";

            //System.out.println("SulmunAllResultBean 문제리스트:" + sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if (v_bef_sulnum != ls.getInt("sulnum")) {
                    data = new SulmunQuestionExampleData();
                    data.setSubj(ls.getString("subj"));
                    data.setSulnum(ls.getInt("sulnum"));
                    data.setSultext(ls.getString("sultext"));
                    data.setSultype(ls.getString("sultype"));
                    data.setSultypenm(ls.getString("sultypenm"));
                    data.setDistcode(ls.getString("distcode"));
                    data.setDistcodenm(ls.getString("distcodenm"));
                }
                exampledata = new SulmunExampleData();
                exampledata.setSubj(data.getSubj());
                exampledata.setSulnum(data.getSulnum());
                exampledata.setSelnum(ls.getInt("selnum"));
                exampledata.setSelpoint(ls.getInt("selpoint"));
                exampledata.setSeltext(ls.getString("seltext"));
                data.add(exampledata);
                if (v_bef_sulnum != data.getSulnum()) {
                    hash.put(String.valueOf(data.getSulnum()), data);
                    v_bef_sulnum = data.getSulnum();
                }
            }
            data = null;
            for (int i = 0; i < p_sulnums.size(); i++) {
                data = (SulmunQuestionExampleData) hash.get((String) p_sulnums.get(i));
                if (data != null) {
                    list.add(data);
                }
            }
        } catch (Exception ex) {
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
        return list;
    }

    @SuppressWarnings("unchecked")
    public void ComputeCount(ArrayList p_list, Vector p_answers) throws Exception {
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;

        SulmunQuestionExampleData data = null;
        Vector subject = new Vector();
        Vector complex = new Vector();
        String v_answers = "";
        String v_answer = "";
        int index = 0;

        try {
            for (int i = 0; i < p_answers.size(); i++) {
                v_answers = (String) p_answers.get(i);
                st1 = new StringTokenizer(v_answers, SulmunAllBean.SPLIT_COMMA);
                index = 0;
                while (st1.hasMoreElements() && index < p_list.size()) {
                    v_answer = (String) st1.nextToken();
                    data = (SulmunQuestionExampleData) p_list.get(index);

                    if (data.getSultype().equals(SulmunAllBean.OBJECT_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunAllBean.MULTI_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunAllBean.SPLIT_COLON);
                        while (st2.hasMoreElements()) {
                            v_answer = (String) st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        }
                    } else if (data.getSultype().equals(SulmunAllBean.SUBJECT_QUESTION)) {
                        subject.add(v_answer);
                    } else if (data.getSultype().equals(SulmunAllBean.COMPLEX_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunAllBean.SPLIT_COLON);
                        v_answer = (String) st2.nextToken();
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        while (st2.hasMoreElements()) {
                            v_answer = (String) st2.nextToken();
                            complex.add(v_answer);
                        }
                    } else if (data.getSultype().equals(SulmunAllBean.FSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunAllBean.SSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    }
                    //System.out.println(data.getComplexAnswer());
                    index++;
                }
            }
            // 응답비율을 계산한다. 리스트((설문번호1, 보기1,2,3..))
            for (int i = 0; i < p_list.size(); i++) {
                data = (SulmunQuestionExampleData) p_list.get(i);
                data.ComputeRate();
                data.setComplexAnswer(complex);
                data.setSubjectAnswer(subject);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    
    public Vector<String> getSulmunAnswers2(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, int p_sulpapernum, String p_uclass, String p_mclass, String p_lclass, String p_startdate, String p_enddate) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_answers = new Vector<String>();

        try {
            sql += " SELECT C.ANSWERS ";
            sql += "   FROM TZ_SUBJ A ";
            sql += "        INNER JOIN TZ_SUBJSEQ B ON A.SUBJ = B.SUBJ ";
            sql += "        INNER JOIN TZ_SULEACH C ON B.SUBJ = C.SUBJ AND B.GRCODE = C.GRCODE AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "        INNER JOIN TZ_MEMBER D ON C.USERID = D.USERID ";
            sql += "  WHERE (1=1) ";
            sql += "    AND C.GUBUN = 'ALL' ";

            if (!p_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(p_grcode) + "\n";
                sql += " and b.gyear = " + SQLString.Format(p_gyear) + "\n";
            }
            if (!p_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(p_grseq);
            if (!p_uclass.equals("ALL"))
                sql += " and a.upperclass = " + SQLString.Format(p_uclass) + "\n";
            if (!p_mclass.equals("ALL"))
                sql += " and a.middleclass = " + SQLString.Format(p_mclass) + "\n";
            if (!p_lclass.equals("ALL"))
                sql += " and a.lowerclass = " + SQLString.Format(p_lclass) + "\n";

            if (!p_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(p_startdate);
            }

            if (!p_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(p_enddate);
            }

            sql += "    AND C.SULPAPERNUM = " + p_sulpapernum;

            //System.out.println("SulmunAllResultBean 설문지 답변모음 :" + sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_answers.add(ls.getString("answers"));
            }
        } catch (Exception ex) {
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
        return v_answers;
    }

    public int getStudentCount2(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, int p_sulpapernum, String p_uclass, String p_mclass, String p_lclass, String p_startdate, String p_enddate) throws Exception {

        ListSet ls = null;
        String sql = "";
        int v_count = 0;

        try {

            sql += " SELECT COUNT (*) CNT ";
            sql += "   FROM TZ_SUBJ A  ";
            sql += "        INNER JOIN TZ_SUBJSEQ B ON A.SUBJ = B.SUBJ ";
            sql += "        INNER JOIN TZ_STUDENT C ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "        INNER JOIN TZ_MEMBER D ON C.USERID = D.USERID ";
            sql += "  WHERE (1=1) ";

            if (!p_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(p_grcode) + "\n";
                sql += " and b.gyear = " + SQLString.Format(p_gyear) + "\n";
            }
            if (!p_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(p_grseq);
            if (!p_uclass.equals("ALL"))
                sql += " and a.upperclass = " + SQLString.Format(p_uclass) + "\n";
            if (!p_mclass.equals("ALL"))
                sql += " and a.middleclass = " + SQLString.Format(p_mclass) + "\n";
            if (!p_lclass.equals("ALL"))
                sql += " and a.lowerclass = " + SQLString.Format(p_lclass) + "\n";

            if (!p_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(p_startdate);
            }

            if (!p_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(p_enddate);
            }

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_count = ls.getInt("cnt");
            }
        } catch (Exception ex) {
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
        return v_count;
    }

    /**
     * 수료현황 - 전체
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectSatiSulmunList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //분야
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //난이도
        String ss_startdate = box.getStringDefault("s_startdate", ""); //결제기간 검색 시작일
        String ss_enddate = box.getStringDefault("s_enddate", ""); //결제기간 검색 종료일

        String v_startdate = ss_startdate.replace("-", "");
        String v_enddate = ss_enddate.replace("-", "");

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += " SELECT ";
            sql += "        GRCODE, ";
            sql += "        GYEAR, ";
            sql += "        GRSEQ, ";
            sql += "        UPPERCLASS, ";
            sql += "        MIDDLECLASS, ";
            sql += "        (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS = A.UPPERCLASS AND MIDDLECLASS = A.MIDDLECLASS AND LOWERCLASS = '000') MIDDLECLASSNAME, ";
            sql += "        SUBJ, ";
            sql += "        SUBJNM, ";
            sql += "        (SELECT GRSEQNM FROM TZ_GRSEQ WHERE GRCODE = A.GRCODE AND GYEAR = A.GYEAR AND GRSEQ = A.GRSEQ) GRSEQNM, ";
            sql += "        ROUND(NVL((SELECT AVG(DISTCODE1_AVG) FROM TZ_SULEACH WHERE SUBJ = A.SUBJ AND GRCODE = A.GRCODE AND YEAR = A.YEAR AND SULPAPERNUM = A.SULPAPERNUM2),0),2) DISTCODE1_AVG, ";
            sql += "        RANK, ";
            sql += "        RANK2, ";
            sql += "        CASE WHEN RANK = 1 THEN COUNT(*) OVER (PARTITION BY GRCODE, GYEAR, GRSEQ) ELSE 0 END AS ROWSPAN, ";
            sql += "        CASE WHEN RANK2 = 1 THEN COUNT(*) OVER (PARTITION BY GRCODE, GYEAR, GRSEQ, UPPERCLASS, MIDDLECLASS) ELSE 0 END AS ROWSPAN2 ";
            sql += "   FROM ";
            sql += "       (  ";
            sql += "         SELECT ";
            sql += "           GRCODE, ";
            sql += "           GYEAR, ";
            sql += "           GRSEQ, ";
            sql += "           UPPERCLASS, ";
            sql += "           MIDDLECLASS, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUBJ, ";
            sql += "           YEAR, ";
            sql += "           SUBJNM, ";
            sql += "           RANK() OVER (PARTITION BY GRCODE, GYEAR, GRSEQ ORDER BY GRCODE, GYEAR, GRSEQ, UPPERCLASS, MIDDLECLASS, SUBJ, SUBJNM) AS RANK, ";
            sql += "           RANK() OVER (PARTITION BY GRCODE, GYEAR, GRSEQ, UPPERCLASS, MIDDLECLASS ORDER BY GRCODE, GYEAR, GRSEQ, UPPERCLASS, MIDDLECLASS, SUBJ, SUBJNM) AS RANK2 ";
            sql += "         FROM ";
            sql += "              ( ";
            sql += "                SELECT ";
            sql += "                    B.GRCODE, ";
            sql += "                    B.GYEAR, ";
            sql += "                    B.GRSEQ, ";
            sql += "                    B.SUBJ, ";
            sql += "                    B.YEAR, ";
            sql += "                    A.UPPERCLASS, ";
            sql += "                    A.MIDDLECLASS, ";
            sql += "                    B.SULPAPERNUM2, ";
            sql += "                    A.SUBJNM ";
            sql += "                FROM ";
            sql += "                  TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                  ON A.SUBJ = B.SUBJ  ";
            sql += "                  LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                  ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                  LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "       WHERE (1=1)";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and b.gyear = " + SQLString.Format(v_year) + "\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and a.upperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and a.middleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_lclass.equals("ALL"))
                sql += " and a.lowerclass = " + SQLString.Format(ss_lclass) + "\n";

            if (!v_startdate.equals("")) {//
                sql += "  and b.edustart  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and b.edustart <=" + SQLString.Format(v_enddate);
            }

            sql += "                ORDER BY SUBJ, YEAR, SUBJSEQ ";
            sql += "              ) ";
            sql += "    GROUP BY GRCODE, GYEAR, GRSEQ, UPPERCLASS, MIDDLECLASS, SUBJ, YEAR, SUBJNM, SULPAPERNUM2 ";
            sql += "  ) A ";
            sql += "  ORDER BY GRCODE, GYEAR, GRSEQ, UPPERCLASS, MIDDLECLASS ";

            //System.out.println("StatisticsAdminBean =========== : selectSatiSulmunList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_distcode1_avg", new Double(ls.getDouble("distcode1_avg")));

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
     * 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 구분 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectGubunList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String ss_option3 = box.getStringDefault("s_option3", "ALL"); //통계별 조건

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if ("MEM".equals(ss_option3)) {
                sql = " SELECT CODE, CODENM, 'DECODE(D.MEMBERGUBUN,''' || CODE || ''',1,0)' CONDITION FROM TZ_CODE WHERE GUBUN = '0029' ORDER BY CODE ";
            } else if ("GEN".equals(ss_option3)) {
                sql = " SELECT '1' CODE, '남자' CODENM, 'DECODE(SEX,''1'',1,0)' CONDITION FROM DUAL ";
                sql += " UNION ALL ";
                sql += " SELECT '2' CODE, '여자' CODENM, 'DECODE(SEX,''2'',1,0)' CONDITION FROM DUAL ";
            } else if ("YEA".equals(ss_option3)) {
                sql = " SELECT '10' CODE, '10대' CODENM, 'DECODE(CASE WHEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) IN (0, 10, 20, 30, 40, 50, 60) THEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) ELSE 60 END,0,1,10,1,0 )' CONDITION FROM DUAL ";
                sql += " UNION ALL ";
                sql += " SELECT '20' CODE, '20대' CODENM, 'DECODE(CASE WHEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) IN (0, 10, 20, 30, 40, 50, 60) THEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) ELSE 60 END,20,1,0 )' CONDITION FROM DUAL ";
                sql += " UNION ALL ";
                sql += " SELECT '30' CODE, '30대' CODENM, 'DECODE(CASE WHEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) IN (0, 10, 20, 30, 40, 50, 60) THEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) ELSE 60 END,30,1,0 )' CONDITION FROM DUAL ";
                sql += " UNION ALL ";
                sql += " SELECT '40' CODE, '40대' CODENM, 'DECODE(CASE WHEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) IN (0, 10, 20, 30, 40, 50, 60) THEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) ELSE 60 END,40,1,0 )' CONDITION FROM DUAL ";
                sql += " UNION ALL ";
                sql += " SELECT '50' CODE, '50대' CODENM, 'DECODE(CASE WHEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) IN (0, 10, 20, 30, 40, 50, 60) THEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) ELSE 60 END,50,1,0 )' CONDITION FROM DUAL ";
                sql += " UNION ALL ";
                sql += " SELECT '60' CODE, '60대 이상' CODENM, 'DECODE(CASE WHEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) IN (0, 10, 20, 30, 40, 50, 60) THEN (TRUNC((TO_NUMBER(TO_CHAR(SYSDATE, ''yyyy'')) - TO_NUMBER(MEMBERYEAR)),-1) ) ELSE 60 END,60,1,0 )' CONDITION FROM DUAL ";
            } else if ("SCH".equals(ss_option3)) {
                sql = " SELECT CODE, CODENM, 'DECODE(D.DEGREE,''' || CODE || ''',1,0)' CONDITION FROM TZ_CODE WHERE GUBUN = '0069' ";
                sql += " UNION ALL ";
                sql += " SELECT '99','기타', 'DECODE(D.DEGREE,''99'',1,0)' FROM DUAL ";
                sql += " ORDER BY CODE ";
            } else if ("CUL".equals(ss_option3)) {
                sql += " SELECT CODE, CODENM, 'DECODE(D.JOB_CULTURE,''' || CODE || ''',1,0)' CONDITION FROM TZ_CODE WHERE GUBUN = '0092' ";
            } else if ("NCU".equals(ss_option3)) {
                sql += " SELECT CODE, CODENM, 'DECODE(D.JOB,''' || CODE || ''',1,0)' CONDITION FROM TZ_CODE WHERE GUBUN = '0091' AND CODE <> '9999'"; //9999:문화산업종사자
            }

            //System.out.println("StatisticsAdminBean =========== : selectGubunList: " + sql);
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
     * 교육그룹별 통계 - New
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectGroupList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        // DataBox dboxGubun = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ArrayList listGubun = null;
        String sql = "";

        String ss_grseq = box.getString("param"); //차수
        String ss_part = box.getString("param1"); //구분 방송 문화 게임
        String ss_subj = box.getString("param2"); //과정
        String ss_ordering = box.getString("param3"); //오더링

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            sql = "select	(select a.grcodenm from tz_grcode a where a.grcode = gcd) grnm, ";
            sql += "      	sum(user_cnt) user_cnt, ";
            sql += "        sum(grad_cnt) grad_cnt, ";
            sql += "        sum(ngrad_cnt) ngrad_cnt, ";
            sql += "        avg(grad_rate )grad_rate, ";
            sql += "        avg(SUL_RATE ) SUL_RATE from  ( ";
            sql += " SELECT    SUBJ, ";
            sql += "           SUBJNM, ";
            sql += "           GRCODE gcd, ";
            sql += "           USER_CNT, ";
            sql += "           GRAD_CNT, ";
            sql += "           ROUND(DECODE(USER_CNT,0,0,GRAD_CNT / USER_CNT * 100),2) GRAD_RATE, ";
            sql += "           NGRAD_CNT, ";
            sql += "           ROUND((DECODE(USER_CNT,0,0,(SELECT avg(distcode1_avg) FROM TZ_SULEACH WHERE SUBJ = A.SUBJ AND YEAR = A.YEAR and SUBJSEQ = A.SUBJSEQ  AND SULPAPERNUM = A.SULPAPERNUM2) )),2) SUL_RATE ";
            sql += "  FROM ( ";
            sql += "         SELECT ";
            sql += "           YEAR, SUBJSEQ, GRCODE, ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUM(USER_CNT) USER_CNT, ";
            sql += "           SUM(GRAD_CNT) GRAD_CNT, ";
            sql += "           SUM(NGRAD_CNT) NGRAD_CNT ";
            sql += "         FROM ";
            sql += "              ( ";
            sql += "                SELECT ";
            sql += "                    B.SUBJ, ";
            sql += "                    B.YEAR, ";
            sql += "                    B.SUBJNM, ";
            sql += "                    B.SUBJSEQ, ";
            sql += "                    B.EDUSTART, ";
            sql += "                    B.EDUEND, ";
            sql += "                    B.GRCODE, ";
            sql += "                    B.GYEAR, ";
            sql += "                    B.GRSEQ, ";
            sql += "                    DECODE(C.USERID,NULL,0,1) USER_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'Y',1,0) GRAD_CNT, ";
            sql += "                    DECODE(C.ISGRADUATED,'N',1,0) NGRAD_CNT, ";
            sql += "                    B.SULPAPERNUM2 ";
            sql += "                FROM ";
            sql += "                  TZ_SUBJ A INNER JOIN TZ_SUBJSEQ B ";
            sql += "                  ON A.SUBJ = B.SUBJ  ";
            sql += "                  LEFT OUTER JOIN TZ_STUDENT C ";
            sql += "                  ON B.SUBJ = C.SUBJ AND B.YEAR = C.YEAR AND B.SUBJSEQ = C.SUBJSEQ ";
            sql += "                  LEFT OUTER JOIN TZ_MEMBER D ON D.USERID = C.USERID ";

            sql += "       WHERE (1=1)";

            int i = 0;
            StringTokenizer st = new StringTokenizer(ss_grseq, ",");
            String temp = "";

            sql += " and (";
            while (st.hasMoreElements()) {
                temp = st.nextToken();

                if (i != 0)
                    sql += " or ";
                sql += "b.year||b.grcode||b.grseq ='" + temp + "'";
                i++;
            }
            sql += " ) ";

            i = 0;
            StringTokenizer st2 = new StringTokenizer(ss_part, ",");
            sql += " and (";
            while (st2.hasMoreElements()) {

                String temp5 = st2.nextToken();
                if (i != 0)
                    sql += " or ";
                sql += " a.area = '" + temp5 + "'";
                i++;
            }
            sql += " ) ";

            if (!ss_subj.equals("null")) {

                i = 0;
                StringTokenizer st3 = new StringTokenizer(ss_subj, ",");
                sql += " and (";
                while (st3.hasMoreElements()) {

                    String temp5 = st3.nextToken();
                    if (i != 0)
                        sql += " or ";
                    sql += " a.subj = '" + temp5 + "'";
                    i++;
                }
                sql += " ) ";
            }

            sql += "   and d.grcode||d.userid = b.grcode||c.userid ";
            sql += "              )  ";
            sql += "    GROUP BY   ";
            sql += "           SUBJ, ";
            sql += "           SUBJNM,  ";
            sql += "           YEAR, ";
            sql += "           GRCODE, ";
            sql += "           SULPAPERNUM2, ";
            sql += "           SUBJSEQ ";
            sql += " ) A  ";

            sql += " ) group by gcd  ";
            if (ss_ordering.equals("")) {
                sql += " ORDER BY user_cnt desc ";
            } else if (ss_ordering.equals("subjnm")) {
                sql += " ORDER BY subjnm asc ";
            } else {
                sql += " ORDER BY " + ss_ordering + " desc ";
            }

            //System.out.println("StatisticsAdminBean =========== : selectGroupList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_grad_rate", new Double(ls.getDouble("grad_rate")));
                dbox.put("d_sul_rate", new Double(ls.getDouble("sul_rate")));
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