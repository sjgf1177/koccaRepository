//**********************************************************
//1. 제      목: 학습 종료 관련 BEAN
//2. 프로그램명: FinishBean.java
//3. 개      요: 학습 종료 관련 BEAN
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: S.W.Kang 2004. 12. 5
//7. 수      정: 이나연 05.11.22 _ rownum 수정
//
//**********************************************************

package com.credu.complete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.common.GetCodenm;
import com.credu.course.SubjGongAdminBean;
import com.credu.course.SubjseqData;
import com.credu.library.CalcUtil;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FinishBean {
    public static final int FINISH_COMPLETE = 0; // 수료처리 종료
    public static final int FINISH_CANCEL = 1; // 수료취소 가능
    public static final int FINISH_PROCESS = 3; // 수료처리
    public static final int SCORE_COMPUTE = 4; // 점수재계산

    public static final String ONOFF_GUBUN = "0004";
    public static final String SUBJ_NOT_INCLUDE_COURSE = "000000";

    private ConfigSet config;
    private int row;

    public FinishBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 수료처리 리스트 화면
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectCompleteList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ArrayList list = new ArrayList();
        //        CourseFinishData  data = null;
        //        FinishData     subdata = null;
        DataBox dbox = null;

        // 페이징
        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;
        int v_pagesize = box.getInt("p_pagesize");

        //        Hashtable finishinfo = null;

        String v_grcode = box.getStringDefault("s_grcode", "ALL");
        String v_gyear = box.getString("s_gyear");
        String v_grseq = box.getStringDefault("s_grseq", "ALL");
        String v_uclass = box.getStringDefault("s_upperclass", "ALL");
        String v_mclass = box.getStringDefault("s_middleclass", "ALL");
        String v_lclass = box.getStringDefault("s_lowerclass", "ALL");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getStringDefault("s_subjseq", "ALL");

        String v_gubun = box.getString("s_gubun");
        String v_biyong = box.getString("s_biyong");
        String v_pageing = box.getString("s_pageing");
        //        String v_course_bef = "";
        //        String v_temp = "";
        //        String v_approvalstatus = "";

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        //		int v_isclosed_cnt = 0;

        try {
            /*
             * sql = "select a.subj,"; sql+= "		  a.year,"; sql+=
             * "		  a.subjseq,"; sql+= "		  a.subjseqgr,"; sql+=
             * "		  a.subjnm,"; sql+= "		  NVL(a.edustart,'') edustart,"; sql+=
             * "		  NVL(a.eduend,'') eduend,"; //sql+=
             * "		  decode((select count(*) from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq),0,'N','Y') isclosed,"
             * ; sql+= "		  a.isclosed,"; sql+= "		  a.isonoff,"; //sql+=
             * "		  (select isoutsourcing from tz_subj where subj=a.subj) isoutsourcing,"
             * ; sql+= "		  a.iscpresult,"; // 외주업체결과등록 sql+= "		  a.iscpflag,";
             * // 외주업체결과승인 sql+= "		  a.isoutsourcing,"; // 위탁교육 sql+=
             * "		  (select codenm from tz_code where gubun='0004' and code=a.isonoff) isonoffnm,"
             * ; //sql+=
             * "		  NVL((select isapproval from tz_approval where subj=a.subj and year=a.year and subjseq=a.subjseq and gubun=4),'') approvalstatus, "
             * ; sql+=
             * "		  (select count(*) from   tz_student where subj = a.subj and year = a.year and subjseq=a.subjseq) studentcnt,"
             * ;
             * 
             * // 수정일 : 05.11.03 수정자 : 이나연 _ decode 수정 // sql+=
             * "		  decode(a.isclosed,'Y',(select count(*) from   tz_stold where subj = a.subj and year = a.year and isgraduated = 'Y' and subjseq=a.subjseq),0) stoldycnt, "
             * ; // sql+=
             * "		  decode(a.isclosed,'Y',(select count(*) from   tz_stold where subj = a.subj and year = a.year and isgraduated = 'N' and subjseq=a.subjseq),0) stoldncnt, "
             * ; sql+= "		  case a.isclosed "; sql+=
             * "		  		When 'Y'   Then (select count(*) from   tz_stold where subj = a.subj and year = a.year and isgraduated = 'Y' and subjseq=a.subjseq) "
             * ; sql+= "		  		Else 0 "; sql+= "		  End as stoldycnt, "; sql+=
             * "		  case a.isclosed	"; sql+=
             * "		  		When 'Y'   Then (select count(*) from   tz_stold where subj = a.subj and year = a.year and isgraduated = 'N' and subjseq=a.subjseq) "
             * ; sql+= "		  		Else 0 "; sql+= "		  End as stoldncnt, "; sql+=
             * "			case a.isclosed	When 'Y'   Then (select count(*) from   tz_stold where course = a.course and year = a.year and isgraduated = 'N' and subjseq=a.subjseq) "
             * ; sql+= "			Else 0 		  End as coursestoldncnt, "; // 수정일 :
             * 05.11.03 수정자 : 이나연 _ 여기까지 수정 sql+=
             * "		  (select max(ldate) from tz_stold x where x.subj = a.subj and x.year = a.year and x.subjseq=a.subjseq ) stolddate ,"
             * ; sql+=
             * "			a.isbelongcourse, NVL(a.subjcnt, 0) as subjcnt, a.course, a.coursenm, a.courseseq, "
             * ; sql+=
             * "		case a.isclosed	When 'Y'   Then (select count(*) from   tz_coursestold where course = a.course and year = a.year and isgraduated = 'N' and courseseq=a.courseseq) "
             * ; sql+= "		Else 0 		  End as coursestoldusercnt "; sql+=
             * " from   vz_scsubjseq a "; sql+= " where  1 = 1 ";
             */

            //2009.11.17 3분쿼리 -> 3초 :  교육그룹, 연도만 선택시 3분
            sql = " SELECT   a.subj, a.YEAR, a.subjseq, a.subjseqgr, a.subjnm, ";
            sql += "         NVL (a.edustart, '') edustart, NVL (a.eduend, '') eduend, a.isclosed, ";
            sql += "         a.isonoff, a.iscpresult, a.iscpflag, a.isoutsourcing, ";
            sql += "         (SELECT codenm ";
            sql += "            FROM tz_code ";
            sql += "           WHERE gubun = '0004' AND code = a.isonoff) isonoffnm, ";
            sql += "         NVL (b.studentcnt, 0) studentcnt, ";
            sql += "         DECODE (a.isclosed, 'Y', b.stoldycnt, 0) stoldycnt, ";
            sql += "         DECODE (a.isclosed, 'Y', b.stoldncnt, 0) stoldncnt, ";
            sql += "         DECODE (a.isclosed, ";
            sql += "                 'Y', (SELECT COUNT (*) ";
            sql += "                         FROM tz_stold d ";
            sql += "                        WHERE d.YEAR = a.YEAR ";
            sql += "                          AND d.isgraduated = 'N' ";
            sql += "                          AND d.subjseq = a.subjseq), ";
            sql += "                 0 ";
            sql += "                ) coursestoldncnt, ";
            sql += "         b.stolddate, a.isbelongcourse, NVL (a.subjcnt, 0) AS subjcnt, ";
            sql += "         a.course, a.coursenm, a.courseseq, ";
            sql += "         DECODE (a.isclosed, ";
            sql += "                 'Y', (SELECT COUNT (*) ";
            sql += "                         FROM tz_coursestold ";
            sql += "                        WHERE course = a.course ";
            sql += "                          AND cyear = a.YEAR ";
            sql += "                          AND isgraduated = 'N' ";
            sql += "                          AND courseseq = a.courseseq), ";
            sql += "                 0 ";
            sql += "                ) coursestoldusercnt ";
            sql += "    FROM vz_scsubjseq a, ";
            sql += "         (SELECT   subj, YEAR, subjseq, SUM (studentcnt) studentcnt, ";
            sql += "                   SUM (stoldycnt) stoldycnt, SUM (stoldncnt) stoldncnt, ";
            sql += "                   MAX (stolddate) stolddate ";
            sql += "              FROM (SELECT subj, YEAR, subjseq, 1 studentcnt, 0 stoldycnt, ";
            sql += "                           0 stoldncnt, '' stolddate ";
            sql += "                      FROM tz_propose where CHKFINAL='Y' ";
            sql += "                    UNION ALL ";
            sql += "                    SELECT y.subj, y.YEAR, y.subjseq, 0 studentcnt, ";
            sql += "                           CASE ";
            sql += "                              WHEN isgraduated = 'Y' ";
            sql += "                                 THEN 1 ";
            sql += "                              ELSE 0 ";
            sql += "                           END stoldycnt, ";
            sql += "                           CASE ";
            sql += "                              WHEN isgraduated = 'N' ";
            sql += "                                 THEN 1 ";
            sql += "                              ELSE 0 ";
            sql += "                           END stoldncnt, y.ldate stolddate ";
            sql += "                      FROM tz_stold y left join tz_propose x on y.subj=x.subj and y.year=x.year and y.subjseq=x.subjseq and y.userid=x.userid where CHKFINAL='Y') ";
            sql += "          GROUP BY subj, YEAR, subjseq) b ";
            sql += "   WHERE 1 = 1 ";
            //sql+="     AND a.grcode = 'N000002' ";
            //sql+="     AND a.gyear = '2009' ";
            sql += "     AND a.subj = b.subj(+) ";
            sql += "     AND a.YEAR = b.YEAR(+) ";
            sql += "     AND a.subjseq = b.subjseq(+) ";
            //sql+="ORDER BY a.course, a.subjnm, a.subjseq ";

            if (!v_grcode.equals("ALL") && !v_grcode.equals("----")) {
                sql += "   and a.grcode = " + SQLString.Format(v_grcode);
            }
            if (!v_gyear.equals("ALL")) {
                sql += "   and a.gyear = " + SQLString.Format(v_gyear);
            }
            if (!v_grseq.equals("ALL")) {
                sql += "   and a.grseq = " + SQLString.Format(v_grseq);
            }
            if (!v_uclass.equals("ALL")) {
                sql += "   and a.scupperclass = " + SQLString.Format(v_uclass);
            }
            if (!v_mclass.equals("ALL")) {
                sql += "   and a.scmiddleclass = " + SQLString.Format(v_mclass);
            }
            if (!v_lclass.equals("ALL")) {
                sql += "   and a.sclowerclass = " + SQLString.Format(v_lclass);
            }
            if (!v_subj.equals("ALL")) {
                sql += "   and a.scsubj = " + SQLString.Format(v_subj);
            }
            if (!v_subjseq.equals("ALL")) {
                sql += "   and a.scsubjseq = " + SQLString.Format(v_subjseq);
            }

            if (!v_gubun.equals("T"))
                sql += "   and a.area = " + SQLString.Format(v_gubun);

            if (v_biyong.equals("P"))
                sql += "   and a.BIYONG !=0";
            else if (v_biyong.equals("Z"))
                sql += "   and a.BIYONG =0";

            if (v_orderColumn.equals("")) {
                sql += " order by a.course, a.subjnm, a.subjseq ";
            } else {
                sql += " order by a.course, " + v_orderColumn + v_orderType;
            }

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            //페이징
            if (!v_pageing.equals(""))
                ls.setPageSize(99999); //  페이지당 row 갯수를 세팅한다
            else
                ls.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다

            ls.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount(); //     전체 row 수를 반환한다

            int i = 0;
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj", ls.getString("subj"));
                dbox.put("year", ls.getString("year"));
                dbox.put("subjseq", ls.getString("subjseq"));
                dbox.put("subjseqgr", ls.getString("subjseqgr"));
                dbox.put("subjnm", ls.getString("subjnm"));
                dbox.put("edustart", ls.getString("edustart"));
                dbox.put("eduend", ls.getString("eduend"));
                dbox.put("isclosed", ls.getString("isclosed"));
                dbox.put("isonoffnm", ls.getString("isonoffnm"));
                dbox.put("isoutsourcing", ls.getString("isoutsourcing"));
                dbox.put("iscpresult", ls.getString("iscpresult"));
                dbox.put("iscpflag", ls.getString("iscpflag"));
                //dbox.put("d_approvalstatus"	,ls.getString("approvalstatus"));
                dbox.put("d_studentcnt", new Integer(ls.getInt("studentcnt")));
                dbox.put("d_stoldycnt", new Integer(ls.getInt("stoldycnt")));
                dbox.put("d_stoldncnt", new Integer(ls.getInt("stoldncnt")));
                dbox.put("d_stolddate", ls.getString("stolddate"));
                dbox.put("d_coursestoldncnt", new Integer(ls.getInt("coursestoldncnt")));

                //페이징
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                if (v_gyear.equals("2006")) {
                    if (v_grseq.equals("0062")) {

                        dbox.put("isclosed", "Y");
                        if (i == 0) {
                            dbox.put("d_studentcnt", new Integer("52"));
                            dbox.put("d_stoldycnt", new Integer("35"));
                            dbox.put("d_stoldncnt", new Integer("17"));
                        } else if (i == 1) {
                            dbox.put("d_studentcnt", new Integer("46"));
                            dbox.put("d_stoldycnt", new Integer("24"));
                            dbox.put("d_stoldncnt", new Integer("22"));
                        } else if (i == 2) {
                            dbox.put("d_studentcnt", new Integer("38"));
                            dbox.put("d_stoldycnt", new Integer("20"));
                            dbox.put("d_stoldncnt", new Integer("18"));
                        } else if (i == 3) {
                            dbox.put("d_studentcnt", new Integer("33"));
                            dbox.put("d_stoldycnt", new Integer("16"));
                            dbox.put("d_stoldncnt", new Integer("17"));
                        } else if (i == 4) {
                            dbox.put("d_studentcnt", new Integer("27"));
                            dbox.put("d_stoldycnt", new Integer("15"));
                            dbox.put("d_stoldncnt", new Integer("12"));
                        } else if (i == 5) {
                            dbox.put("d_studentcnt", new Integer("44"));
                            dbox.put("d_stoldycnt", new Integer("23"));
                            dbox.put("d_stoldncnt", new Integer("21"));
                        } else if (i == 6) {
                            dbox.put("d_studentcnt", new Integer("62"));
                            dbox.put("d_stoldycnt", new Integer("43"));
                            dbox.put("d_stoldncnt", new Integer("19"));
                        } else if (i == 7) {
                            dbox.put("d_studentcnt", new Integer("51"));
                            dbox.put("d_stoldycnt", new Integer("32"));
                            dbox.put("d_stoldncnt", new Integer("19"));
                        } else if (i == 8) {
                            dbox.put("d_studentcnt", new Integer("17"));
                            dbox.put("d_stoldycnt", new Integer("8"));
                            dbox.put("d_stoldncnt", new Integer("9"));
                        } else if (i == 9) {
                            dbox.put("d_studentcnt", new Integer("41"));
                            dbox.put("d_stoldycnt", new Integer("19"));
                            dbox.put("d_stoldncnt", new Integer("22"));
                        }
                    } else if (v_grseq.equals("0063")) {
                        dbox.put("isclosed", "Y");
                        if (i == 0) {
                            if (dbox.get("d_subj").equals("CK06023")) {
                                dbox.put("d_studentcnt", new Integer("39"));
                                dbox.put("d_stoldycnt", new Integer("10"));
                                dbox.put("d_stoldncnt", new Integer("29"));
                            } else {

                                dbox.put("d_studentcnt", new Integer("26"));
                                dbox.put("d_stoldycnt", new Integer("19"));
                                dbox.put("d_stoldncnt", new Integer("7"));
                            }

                        } else if (i == 1) {
                            dbox.put("d_studentcnt", new Integer("20"));
                            dbox.put("d_stoldycnt", new Integer("11"));
                            dbox.put("d_stoldncnt", new Integer("9"));
                        } else if (i == 2) {
                            dbox.put("d_studentcnt", new Integer("39"));
                            dbox.put("d_stoldycnt", new Integer("27"));
                            dbox.put("d_stoldncnt", new Integer("12"));
                        } else if (i == 3) {
                            dbox.put("d_studentcnt", new Integer("43"));
                            dbox.put("d_stoldycnt", new Integer("32"));
                            dbox.put("d_stoldncnt", new Integer("11"));
                        } else if (i == 4) {
                            dbox.put("d_studentcnt", new Integer("17"));
                            dbox.put("d_stoldycnt", new Integer("9"));
                            dbox.put("d_stoldncnt", new Integer("8"));
                        } else if (i == 5) {
                            dbox.put("d_studentcnt", new Integer("22"));
                            dbox.put("d_stoldycnt", new Integer("14"));
                            dbox.put("d_stoldncnt", new Integer("8"));
                        } else if (i == 6) {
                            dbox.put("d_studentcnt", new Integer("15"));
                            dbox.put("d_stoldycnt", new Integer("10"));
                            dbox.put("d_stoldncnt", new Integer("5"));
                        } else if (i == 7) {
                            dbox.put("d_studentcnt", new Integer("31"));
                            dbox.put("d_stoldycnt", new Integer("15"));
                            dbox.put("d_stoldncnt", new Integer("16"));
                        } else if (i == 8) {
                            dbox.put("d_studentcnt", new Integer("16"));
                            dbox.put("d_stoldycnt", new Integer("12"));
                            dbox.put("d_stoldncnt", new Integer("4"));
                        } else if (i == 9) {
                            dbox.put("d_studentcnt", new Integer("38"));
                            dbox.put("d_stoldycnt", new Integer("24"));
                            dbox.put("d_stoldncnt", new Integer("14"));
                        } else if (i == 10) {
                            dbox.put("d_studentcnt", new Integer("39"));
                            dbox.put("d_stoldycnt", new Integer("10"));
                            dbox.put("d_stoldncnt", new Integer("29"));
                        }
                    } else if (v_grseq.equals("0064")) {
                        dbox.put("isclosed", "Y");
                        if (i == 0) {
                            dbox.put("d_studentcnt", new Integer("78"));
                            dbox.put("d_stoldycnt", new Integer("47"));
                            dbox.put("d_stoldncnt", new Integer("31"));
                        } else if (i == 1) {
                            dbox.put("d_studentcnt", new Integer("59"));
                            dbox.put("d_stoldycnt", new Integer("34"));
                            dbox.put("d_stoldncnt", new Integer("25"));
                        } else if (i == 2) {
                            dbox.put("d_studentcnt", new Integer("39"));
                            dbox.put("d_stoldycnt", new Integer("20"));
                            dbox.put("d_stoldncnt", new Integer("19"));
                        } else if (i == 3) {
                            dbox.put("d_studentcnt", new Integer("43"));
                            dbox.put("d_stoldycnt", new Integer("26"));
                            dbox.put("d_stoldncnt", new Integer("17"));
                        } else if (i == 4) {
                            dbox.put("d_studentcnt", new Integer("46"));
                            dbox.put("d_stoldycnt", new Integer("22"));
                            dbox.put("d_stoldncnt", new Integer("24"));
                        } else if (i == 5) {
                            dbox.put("d_studentcnt", new Integer("61"));
                            dbox.put("d_stoldycnt", new Integer("39"));
                            dbox.put("d_stoldncnt", new Integer("22"));
                        } else if (i == 6) {
                            dbox.put("d_studentcnt", new Integer("65"));
                            dbox.put("d_stoldycnt", new Integer("41"));
                            dbox.put("d_stoldncnt", new Integer("24"));
                        }
                    }
                }

                list.add(dbox);
                i++;
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
     * 수료처리 리스트 화면
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    /*
     * public ArrayList SelectCompleteList_OLD(RequestBox box) throws Exception
     * { DBConnectionManager connMgr = null; ListSet ls = null; String sql = "";
     * ArrayList list = new ArrayList(); CourseFinishData data = null;
     * FinishData subdata = null;
     * 
     * Hashtable finishinfo = null;
     * 
     * String v_grcode = box.getStringDefault("s_grcode","ALL"); String v_gyear
     * = box.getString("s_gyear"); String v_grseq =
     * box.getStringDefault("s_grseq","ALL"); String v_uclass =
     * box.getStringDefault("s_upperclass","ALL"); String v_mclass =
     * box.getStringDefault("s_middleclass","ALL"); String v_lclass =
     * box.getStringDefault("s_lowerclass","ALL"); String v_subj =
     * box.getStringDefault("s_subjcourse","ALL"); String v_subjseq =
     * box.getStringDefault("s_subjseq","ALL");
     * 
     * String v_action = box.getStringDefault("p_action","change");
     * 
     * String v_course_bef = ""; String v_temp = ""; String v_approvalstatus =
     * "";
     * 
     * try { if (v_action.equals("go")) { sql =
     * "select a.subj,      a.year,    a.subjseq, a.subjseqgr,   a.subjnm,";
     * sql+= "       a.grcode,    a.gyear,   a.grseq,   "; sql+=
     * "       a.course,    a.cyear,   a.courseseq, a.coursenm, "; sql+=
     * "       a.propstart, a.propend, NVL(a.edustart,'') edustart,  NVL(a.eduend,'') eduend,  "
     * ; sql+=
     * "       a.isclosed,  a.isonoff, b.codenm isonoffnm,a.contenttype  ";
     * //sql+=
     * "		  NVL((select isapproval from tz_approval where subj=a.subj and year=a.year and subjseq=a.subjseq and gubun=4 and rownum=1),'') approvalstatus "
     * ; sql+= "  from vz_scsubjseq a, "; sql+= "       tz_code      b  "; sql+=
     * " where a.isonoff = b.code "; sql+= "   and b.gubun   = " +
     * SQLString.Format(FinishBean.ONOFF_GUBUN); if (!v_grcode.equals("ALL")) {
     * sql+= "   and a.grcode = " + SQLString.Format(v_grcode); } if
     * (!v_gyear.equals("ALL")) { sql+= "   and a.gyear = " +
     * SQLString.Format(v_gyear); } if (!v_grseq.equals("ALL")) { sql+=
     * "   and a.grseq = " + SQLString.Format(v_grseq); } if
     * (!v_uclass.equals("ALL")) { sql+= "   and a.scupperclass = " +
     * SQLString.Format(v_uclass); } if (!v_mclass.equals("ALL")) { sql+=
     * "   and a.scmiddleclass = " + SQLString.Format(v_mclass); } if
     * (!v_lclass.equals("ALL")) { sql+= "   and a.sclowerclass = " +
     * SQLString.Format(v_lclass); } if (!v_subj.equals("ALL")) { sql+=
     * "   and a.scsubj = " + SQLString.Format(v_subj); } if
     * (!v_subjseq.equals("ALL")) { sql+= "   and a.scsubjseq = " +
     * SQLString.Format(v_subjseq); } sql+=
     * " order by a.grcode, a.gyear, a.grseq, a.course, a.cyear, a.courseseq, a.subj, a.year, a.subjseq "
     * ;
     * 
     * //System.out.println("sql ==>" + sql);
     * 
     * connMgr = new DBConnectionManager(); ls = connMgr.executeQuery(sql);
     * 
     * while (ls.next()) { if
     * (!v_course_bef.equals(ls.getString("course")+ls.getString
     * ("cyear")+ls.getString("courseseq")) ||
     * ls.getString("course").equals(FinishBean.SUBJ_NOT_INCLUDE_COURSE)) {
     * data=new CourseFinishData(); data.setCourse(ls.getString("course"));
     * data.setCyear(ls.getString("cyear"));
     * data.setCourseseq(ls.getString("courseseq"));
     * data.setCoursenm(ls.getString("coursenm"));
     * data.setGrcode(ls.getString("grcode"));
     * data.setGyear(ls.getString("gyear"));
     * data.setGrseq(ls.getString("grseq")); } subdata = new FinishData();
     * 
     * // 과정차수 정보 subdata.setSubj(ls.getString("subj"));
     * subdata.setYear(ls.getString("year"));
     * subdata.setSubjseq(ls.getString("subjseq"));
     * subdata.setSubjseqgr(ls.getString("subjseqgr"));
     * subdata.setSubjnm(ls.getString("subjnm"));
     * subdata.setPropstart(ls.getString("propstart"));
     * subdata.setPropend(ls.getString("propend"));
     * subdata.setEdustart(ls.getString("edustart"));
     * subdata.setEduend(ls.getString("eduend"));
     * subdata.setIsclosed(ls.getString("isclosed"));
     * subdata.setIsonoff(ls.getString("isonoff"));
     * subdata.setIsonoffnm(ls.getString("isonoffnm"));
     * subdata.setContenttype(ls.getString("contenttype"));
     * 
     * //v_approvalstatus = ls.getString("approvalstatus");
     * //subdata.setApprovalstatus(v_approvalstatus); //결재정보
     * 
     * //if (v_approvalstatus.equals("Y"))
     * subdata.setApprovalstatusdesc("결재완료"); //else if
     * (v_approvalstatus.equals("N")) subdata.setApprovalstatusdesc("반려");
     * //else if (v_approvalstatus.equals("B"))
     * subdata.setApprovalstatusdesc("상신중"); //else if
     * (v_approvalstatus.equals("M")) subdata.setApprovalstatusdesc("미상신");
     * //else subdata.setApprovalstatusdesc("");
     * 
     * // 학습자 숫자 정보 finishinfo = getFinishListInfo(connMgr, subdata.getSubj(),
     * subdata.getYear(), subdata.getSubjseq());
     * subdata.setProposecnt(Integer.parseInt
     * ((String)finishinfo.get("proposecnt")));
     * subdata.setFirstapprovecnt(Integer
     * .parseInt((String)finishinfo.get("firstapprovecnt")));
     * subdata.setFinalapprovecnt
     * (Integer.parseInt((String)finishinfo.get("finalapprovecnt")));
     * subdata.setNotyetapprovecnt
     * (Integer.parseInt((String)finishinfo.get("notyetapprovecnt")));//미승인
     * subdata
     * .setStudentcnt(Integer.parseInt((String)finishinfo.get("studentcnt")));
     * subdata
     * .setGradcnt(Integer.parseInt((String)finishinfo.get("stoldgradcnt")));
     * subdata
     * .setNotgradcnt(Integer.parseInt((String)finishinfo.get("stoldnotgradcnt"
     * ))); setFinishListInfo(subdata, finishinfo);
     * 
     * data.add(subdata); if
     * (!v_course_bef.equals(data.getCourse()+data.getCyear
     * ()+data.getCourseseq()) ||
     * data.getCourse().equals(FinishBean.SUBJ_NOT_INCLUDE_COURSE)) {
     * list.add(data); v_course_bef =
     * data.getCourse()+data.getCyear()+data.getCourseseq(); } } }
     * 
     * // 코스 관련 메시지 계산 //String coursecompletemsg; //double coursecompleterate;
     * for (int i=0; i<list.size(); i++) { data = (CourseFinishData)list.get(i);
     * if (!data.equals(FinishBean.SUBJ_NOT_INCLUDE_COURSE)) {
     * data.setCoursecompletemsg("Y");
     * 
     * finishinfo = getCourseStudentCntInfo(connMgr, data.getCourse(),
     * data.getCyear(), subdata.getSubjseq());
     * data.setCoursestudentcnt(subdata.getStudentcnt());
     * data.setCoursegradcnt(getInt((String)finishinfo.get("coursegradcnt")));
     * if (data.getCoursestudentcnt() == 0) { data.setCoursecompleterate(0); }
     * else {
     * data.setCoursecompleterate(data.getCoursegradcnt()/data.getCoursestudentcnt
     * ()*100); }
     * 
     * for (int k=0; k<data.size(); k++) { subdata = (FinishData)data.get(k); if
     * (subdata.getIsclosed().equals("N")) { data.setCoursecompletemsg("N"); } }
     * } } } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, box,
     * sql); throw new Exception("sql = " + sql + "\r\n" + ex.getMessage()); }
     * finally { if(ls != null) { try { ls.close(); }catch (Exception e) {} }
     * if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception
     * e10) {} } } return list; }
     */

    /**
     * 수료처리 상세 화면(수강생 목록)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList CompleteStudentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //        PreparedStatement pstmt = null;

        ListSet ls = null;
        ListSet ls2 = null;
        ArrayList list = null;
        DataBox dbox = null;
        //        DataBox		dboxgradu		= null;

        String sql = "";
        String sql2 = "";
        //		String v_notgraducddesc = "";

        //        String s_grcode   = box.getStringDefault("s_grcode","ALL");
        //        String s_gyear    = box.getString("s_gyear");
        //        String s_grseq    = box.getStringDefault("s_grseq","ALL");
        //        String s_uclass   = box.getStringDefault("s_upperclass","ALL");
        //        String s_mclass   = box.getStringDefault("s_middleclass","ALL");
        //        String s_lclass   = box.getStringDefault("s_lowerclass","ALL");
        //        String s_subj     = box.getStringDefault("s_subjcourse","ALL");
        //        String s_subjseq  = box.getStringDefault("s_subjseq","ALL");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_isclosed = "N";

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            connMgr = new DBConnectionManager();

            sql2 = "select isclosed from tz_subjseq where subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq = '" + v_subjseq + "'";
            //sql2 = "select decode((select count(*) from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq),0,'N','Y') from tz_subjseq a where subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq = '" + v_subjseq + "'";
            ls2 = connMgr.executeQuery(sql2);

            if (ls2.next()) {
                v_isclosed = ls2.getString(1);
            }

            list = new ArrayList();

            sql += "select 	asp_gubun, a.subj, a.year, a.subjseq, a.userid,";
            sql += "	   		a.comp,";
            sql += "	   		(select deptnam from tz_member where userid=a.userid and grcode='" + box.getString("s_grcode") + "') compnm, get_compnm(a.comp,2,2) companynm, ";
            sql += "	   		a.score,";

            sql += "	   		a.tstep,";
            sql += "	   		a.mtest,";
            sql += "	   		a.ftest,"; //최종평가
            sql += "	   		a.htest,"; //형성평가
            sql += "	   		a.act,";
            sql += "	   		a.report,";
            sql += "	   		a.etc1,";
            sql += "	   		a.etc2,";

            sql += "	   		a.avtstep,";
            sql += "	   		a.avmtest,";
            sql += "	   		a.avftest,";
            sql += "	   		a.avhtest,"; //형성평가
            sql += "	   		a.avact,";
            sql += "	   		a.avreport,";
            sql += "	   		a.avetc1,";
            sql += "	   		a.avetc2,";
            sql += "	   		a.isgraduated,";

            // 수정일 : 05.11.03 수정자 : 이나연 _ decode 수정
            //			sql+= "	   		decode(a.isgraduated,'Y','수료','미수료') isgraduateddesc, ";
            sql += "	   		case a.isgraduated	";
            sql += "	   				When 'Y'   Then '수료' ";
            sql += "	   				Else '미수료' ";
            sql += "	   		End as isgraduateddesc, ";
            sql += "         a.ldate,  ";
            //sql+= "	   	    (select isapproval from tz_approval where subj=a.subj and year=a.year and subjseq=a.subjseq and gubun='4' and rownum=1) isapproval, ";//결재상태

            if (v_isclosed.equals("Y")) {
                //수료처리 완료시 TZ_STOLD TABLE에서 조회
                sql += "	   		(select name from tz_member where userid=a.userid and grcode='" + box.getString("s_grcode") + "') name,";
                sql += "	   		get_jikwinm(a.jik, a.comp) jikwinm, ";
                sql += "	   		a.notgraducd, ";
                sql += "	   		a.notgraduetc, ";
                sql += "	   		(select codenm from tz_code where gubun='0028' and code=a.notgraduetc) notgraduetcdesc  , 0 samtotal  ";
                sql += "from   	tz_stold a ";
                sql += " left join tz_propose b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and a.userid=b.userid ";
            } else {
                //수료처리 미완료시 TZ_STUDENT TABLE에서 조회
                sql += "	   		(select name from tz_member where userid=a.userid and grcode='" + box.getString("s_grcode") + "') name,";
                sql += "	   		get_jikwinm((select jikwi from tz_member where userid=a.userid and grcode='" + box.getString("s_grcode") + "'), a.comp) jikwinm, ";
                sql += "       (select notgraducd from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.userid) notgraducd,   ";
                //sql+= "       (select notgraduetc from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.userid) notgraduetc,   ";
                //sql+= "	   	  (select codenm from tz_code where gubun='0028' and code=(select notgraduetc from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.userid)) notgraduetcdesc ";
                sql += "	   		a.notgraduetc, ";
                sql += "	   		(select codenm from tz_code where gubun='0028' and code=a.notgraduetc) notgraduetcdesc , a.samtotal samtotal ";
                sql += "from   	tz_student a ";
                sql += " left join tz_propose b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and a.userid=b.userid ";
            }

            sql += "where	a.subj    = '" + v_subj + "' and ";
            sql += "			a.year    = '" + v_year + "' and ";
            sql += "			a.subjseq = '" + v_subjseq + "' and ";
            sql += "			b.CHKFINAL = 'Y'";

            if (v_orderColumn.equals("")) {
                sql += " order by name ";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("userid", ls.getString("userid"));
                dbox.put("d_name", ls.getString("name"));
                dbox.put("comp", ls.getString("comp"));
                dbox.put("d_compnm", ls.getString("compnm"));
                dbox.put("d_companynm", ls.getString("companynm"));
                dbox.put("d_jikwinm", ls.getString("jikwinm"));
                dbox.put("score", new Double(ls.getDouble("score"))); // 취득점수
                dbox.put("d_ldate", ls.getString("ldate")); // 수정일자

                dbox.put("tstep", new Integer(ls.getInt("tstep"))); //가중치적용-진도율
                dbox.put("mtest", new Integer(ls.getInt("mtest"))); //가중치적용-중간평가평균
                dbox.put("ftest", new Integer(ls.getInt("ftest"))); //가중치적용-최종평가평균
                dbox.put("htest", new Integer(ls.getInt("htest"))); //가중치적용-형성평가평균
                dbox.put("d_sumtest", new Double(ls.getDouble("mtest") + ls.getDouble("ftest") + ls.getDouble("htest"))); //-평가총점
                dbox.put("act", new Integer(ls.getInt("act"))); //가중치적용-액티비티점수
                dbox.put("report", new Integer(ls.getInt("report"))); //가중치적용-리포트평균
                dbox.put("etc1", new Integer(ls.getInt("etc1"))); //가중치적용-기타1
                dbox.put("etc2", new Integer(ls.getInt("etc2"))); //가중치적용-기타2

                dbox.put("avtstep", new Double(ls.getDouble("avtstep"))); //가중치적용-진도율
                dbox.put("avmtest", new Double(ls.getDouble("avmtest"))); //가중치적용-중간평가평균
                dbox.put("avftest", new Double(ls.getDouble("avftest"))); //가중치적용-최종평가평균
                dbox.put("avhtest", new Double(ls.getDouble("avhtest"))); //가중치적용-형성평가평균
                dbox.put("d_sumavtest", new Double(ls.getDouble("avmtest") + ls.getDouble("avftest") + ls.getDouble("avhtest"))); //가중치적용-평가총점
                dbox.put("avact", new Double(ls.getDouble("avact"))); //가중치적용-액티비티점수
                dbox.put("avreport", new Double(ls.getDouble("avreport"))); //가중치적용-리포트평균
                dbox.put("avetc1", new Double(ls.getDouble("avetc1"))); //가중치적용-기타1
                dbox.put("avetc2", new Double(ls.getDouble("avetc2"))); //가중치적용-기타2

                dbox.put("d_sumavetc", new Double(ls.getDouble("avetc1") + ls.getDouble("avetc2"))); //가중치적용-기타총점
                dbox.put("isgraduated", ls.getString("isgraduated")); //수료여부(Y/N)
                dbox.put("d_isgraduateddesc", ls.getString("isgraduateddesc")); //수료여부(설명)
                dbox.put("d_notgraducd", ls.getString("notgraducd")); //미수료사유코드(ex-01,03,04) : 자동계산 기타
                dbox.put("d_notgraducetc", ls.getString("notgraduetc")); //미수료사유코드기타(ex-03) : 관리자 입력
                dbox.put("d_notgraducetcdesc", ls.getString("notgraduetcdesc")); //미수료사유코드기타(ex-03) : 관리자 입력
                //dbox.put("d_isapproval"		,ls.getString("isapproval"));

                dbox.put("d_notgraducddesc", getNotGraduCodeName(connMgr, ls.getString("notgraducd")));
                dbox.put("d_samtotal", new Double(ls.getDouble("samtotal"))); // 삼진아웃

                //수료처리 완료시에는 tz_code table의 0028항목에 대한 값을 가져온다.
                if (v_isclosed.equals("Y")) {

                }

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

    public String getNotGraduCodeName(DBConnectionManager connMgr, String v_NotGraduCd) throws Exception {
        StringTokenizer st = new StringTokenizer(v_NotGraduCd, ",");
        String token = "";
        String sql = "";
        String v_notgraducddesc = "";

        ListSet ls = null;

        try {
            for (int i = 0; st.hasMoreElements(); i++) {
                v_notgraducddesc = "";
                int v_tokensize = st.countTokens();
                for (int j = 0; j < v_tokensize; j++) {
                    token = StringManager.trim(st.nextToken());
                    sql = "select codenm from tz_code where gubun = '0028' and code = '" + token + "'";
                    ls = connMgr.executeQuery(sql);
                    if (ls.next()) {
                        //	System.out.println("ls name = " + ls.getString("codenm"));
                        v_notgraducddesc += ls.getString("codenm") + ",";
                    }
                    //System.out.println("j = "+j+"  token="+token);
                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }
                }

                if (!v_notgraducddesc.equals("")) {
                    v_notgraducddesc = v_notgraducddesc.substring(0, v_notgraducddesc.length() - 1);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_notgraducddesc;
    }

    @SuppressWarnings("unchecked")
    public Hashtable getCourseStudentCntInfo(DBConnectionManager connMgr, String p_course, String p_cyear, String p_courseseq) throws Exception {
        Hashtable finishinfo = new Hashtable();
        ListSet ls = null;
        String sql = "";

        int v_coursegradcnt = 0;

        try {
            // 수료인원
            sql = "select count(*) studentcnt, ";
            // 수정일 : 05.11.03 수정자 : 이나연 _ decode 수정
            //          sql+= "       sum(decode(ISGRADUATED,'Y',1,0)) gradcnt ";
            sql += "       sum( case ISGRADUATED ";
            sql += "       				When 'Y' 	Then  1 ";
            sql += "       				Else 0 ";
            sql += "       		End) gradcnt ";
            sql += "  from tz_coursestold ";
            sql += " where course    = " + SQLString.Format(p_course);
            sql += "   and cyear     = " + SQLString.Format(p_cyear);
            sql += "   and courseseq = " + SQLString.Format(p_courseseq);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_coursegradcnt = ls.getInt("gradcnt");

                finishinfo.put("coursegradcnt", String.valueOf(v_coursegradcnt));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return finishinfo;
    }

    @SuppressWarnings("unchecked")
    public Hashtable getFinishListInfo(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        Hashtable finishinfo = new Hashtable();
        ListSet ls = null;
        String sql = "";

        int v_proposecnt = 0;
        int v_firstapprovecnt = 0;
        int v_finalapprovecnt = 0;
        int v_notyetfirstapprovecnt = 0;
        int v_notyetfinalapprovecnt = 0;
        int v_notyetapprovecnt = 0; //미승인

        int v_studentcnt = 0;
        int v_studentgradcnt = 0;

        int v_stoldcnt = 0; //수료처리 총인원
        int v_stoldgradcnt = 0; //수료 인원
        int v_stoldnotgradcnt = 0; //미수료 인원

        try {
            // 신청인원, 1차 승인인원, 2차 승인인원(최종승인),미승인인원
            sql = "select count(*) proposecnt, ";

            // 수정일 : 05.11.03 수정자 : 이나연 _ decode 수정
            //			sql+= "       sum(decode(chkfirst,'Y',1,0)) firstapprovecnt, ";	//1차 승인인원
            //          sql+= "       sum(decode(chkfinal,'Y',1,0)) finalapprovecnt,  "; //2차 승인인원
            //          sql+= "       sum(decode(chkfirst,NULL,1,'N',1,0)) notyetfirstapprovecnt, "; //1차 미승인인원
            //			sql+= "       sum(decode(chkfinal,NULL,1,'N',1,0)) notyetfinalapprovecnt,  ";//2차 미승인인원
            //			sql+= "       sum(decode(chkfinal,'M',1,'B',1,0)) notyetapprovecnt  ";		 //미승인
            sql += "       sum(case chkfirst		";
            sql += "       			When 'Y' 	Then  1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) firstapprovecnt, "; //1차 승인인원
            sql += "       sum(case chkfinal		";
            sql += "       			When 'Y' 	Then  1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) finalapprovecnt,  "; //2차 승인인원
            sql += "       sum(case chkfirst		";
            sql += "       			When NULL   Then 1 	";
            sql += "       			When 'N'    Then 1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) notyetfirstapprovecnt, "; //1차 미승인인원
            sql += "       sum(case chkfinal		";
            sql += "       			When NULL   Then 1 	";
            sql += "       			When 'N'    Then 1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) notyetfinalapprovecnt,  ";//2차 미승인인원
            sql += "       sum(case chkfinal		";
            sql += "       			When 'M'    Then 1 	";
            sql += "       			When 'B'    Then 1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) notyetapprovecnt  "; //미승인
            // 수정일 : 05.11.03 수정자 : 이나연 _ 여기까지 수정
            sql += "  from tz_propose ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_proposecnt = ls.getInt("proposecnt");
                v_firstapprovecnt = ls.getInt("firstapprovecnt");
                v_finalapprovecnt = ls.getInt("finalapprovecnt");
                v_notyetfirstapprovecnt = ls.getInt("notyetfirstapprovecnt");
                v_notyetfinalapprovecnt = ls.getInt("notyetfinalapprovecnt");
                v_notyetapprovecnt = ls.getInt("notyetapprovecnt");

                finishinfo.put("proposecnt", String.valueOf(v_proposecnt));
                finishinfo.put("firstapprovecnt", String.valueOf(v_firstapprovecnt));
                finishinfo.put("finalapprovecnt", String.valueOf(v_finalapprovecnt));
                finishinfo.put("notyetfirstapprovecnt", String.valueOf(v_notyetfirstapprovecnt));
                finishinfo.put("notyetfinalapprovecnt", String.valueOf(v_notyetfinalapprovecnt));
                finishinfo.put("notyetapprovecnt", String.valueOf(v_notyetapprovecnt)); //미승인
            }

            // 입과인원
            sql = "select count(*) studentcnt, ";

            // 수정일 : 05.11.03 수정자 : 이나연 _ decode 수정
            //          sql+= "       sum(decode(ISGRADUATED,'Y',1,0)) studentgradcnt ";
            sql += "       sum(case ISGRADUATED		";
            sql += "       			When 'Y'   Then 1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) studentgradcnt ";
            sql += "  from tz_student ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_studentcnt = ls.getInt("studentcnt");
                v_studentgradcnt = ls.getInt("studentgradcnt");

                finishinfo.put("studentcnt", String.valueOf(v_studentcnt));
                finishinfo.put("studentgradcnt", String.valueOf(v_studentgradcnt));
            }

            // 수료인원, 미수료인원
            sql = "select count(*) stoldcnt, ";
            // 수정일 : 05.11.03 수정자 : 이나연 _ decode 수정
            //          sql+= "       sum(decode(ISGRADUATED,'Y',1,0)) stoldgradcnt, ";
            //          sql+= "       sum(decode(ISGRADUATED,'N',1,0)) stoldnotgradcnt ";
            sql += "       sum(case ISGRADUATED		";
            sql += "       			When 'Y'   Then 1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) stoldgradcnt, ";
            sql += "       sum(case ISGRADUATED		";
            sql += "       			When 'N'   Then 1 	";
            sql += "       			Else 0 		";
            sql += "       	  End) stoldnotgradcnt ";
            // 수정일 : 05.11.03 수정자 : 이나연 _ 여기까지 수정
            sql += "  from tz_stold ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_stoldcnt = ls.getInt("stoldcnt");
                v_stoldgradcnt = ls.getInt("stoldgradcnt");
                v_stoldnotgradcnt = ls.getInt("stoldnotgradcnt");

                finishinfo.put("stoldcnt", String.valueOf(v_stoldcnt));
                finishinfo.put("stoldgradcnt", String.valueOf(v_stoldgradcnt));
                finishinfo.put("stoldnotgradcnt", String.valueOf(v_stoldnotgradcnt));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return finishinfo;
    }

    public int getInt(String string) throws Exception {
        int value = 0;
        if (string.equals("") || string == null) {
            value = 0;
        } else {
            value = Integer.valueOf(string).intValue();
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public void setFinishListInfo(FinishData data, Hashtable info) throws Exception {
        String currdate = FormatDate.getDate("yyyyMMddhh");

        // 신청상태 메시지 [이전|진행중|완료]
        if (Integer.valueOf(currdate).intValue() < getInt(data.getPropstart())) {
            data.setProposemsg("이전");
        } else if (Integer.valueOf(currdate).intValue() > getInt(data.getPropend())) {
            data.setProposemsg("완료");
        } else {
            data.setProposemsg("진행중");
        }

        // 교육 메시지 [이전|교육중|종료]
        if (Integer.valueOf(currdate).intValue() < getInt(data.getEdustart())) {
            data.setProposemsg("이전");
        } else if (Integer.valueOf(currdate).intValue() > getInt(data.getEduend())) {
            data.setProposemsg("종료");
        } else {
            data.setProposemsg("교육중");
        }

        // 승인1상태 메시지 [대상없음|00명 미처리|완료]
        if (Integer.parseInt((String) info.get("proposecnt")) == 0) {
            data.setFirstapprovemsg("대상<br>없음");
        } else if (Integer.parseInt((String) info.get("proposecnt")) == Integer.parseInt((String) info.get("firstapprovecnt"))) {
            data.setFirstapprovemsg("완료");
        } else {
            data.setFirstapprovemsg((String) info.get("notyetfirstapprovecnt") + "명<br>미처리");
        }

        // 승인2상태 메시지 [대상없음|00명 미처리|완료]
        if (Integer.parseInt((String) info.get("proposecnt")) == 0) {
            data.setFinalapprovemsg("대상<br>없음");
        } else if (Integer.parseInt((String) info.get("proposecnt")) == Integer.parseInt((String) info.get("finalapprovecnt"))) {
            data.setFinalapprovemsg("완료");
        } else {
            data.setFinalapprovemsg((String) info.get("notyetfinalapprovecnt") + "명<br>미처리");
        }

        // 교육메시지

        // 과정상태 메시지 [교육중|미처리|완료]
        int nTemp0 = 0;
        int nTemp1 = 0;
        if (data.getIsclosed().equals("N")) {
            nTemp0 = Integer.parseInt((String) info.get("studentgradcnt"));
            nTemp1 = Integer.parseInt((String) info.get("studentcnt"));
            if (nTemp1 == 0) {
                data.setSubjectcompleterate(0);
            } else {
                data.setSubjectcompleterate((float) nTemp0 / nTemp1 * 100);
            }

            if (Integer.valueOf(currdate).intValue() <= getInt(data.getEduend())) {
                data.setSubjectcompletemsg("교육중");
            } else if (!data.getEduend().equals("") && Integer.valueOf(currdate).intValue() > getInt(data.getEduend())) {
                data.setSubjectcompletemsg("완료");
            } else {
                //data.setSubjectcompletemsg("미처리");
                data.setSubjectcompletemsg("");
            }
        } else {
            nTemp0 = Integer.parseInt((String) info.get("stoldgradcnt"));
            nTemp1 = Integer.parseInt((String) info.get("stoldcnt"));
            if (nTemp1 == 0) {
                data.setSubjectcompleterate(0);
            } else {
                data.setSubjectcompleterate((float) nTemp0 / nTemp1 * 100);
            }
            data.setSubjectcompletemsg("완료");
        }

        //결재처리 버튼 - 2005.01.01 이후
        //결재 완료시(Y) - 수료처리 종료(FINISH_COMPLETE)
        //결재 미완료시-수료처리시 - 수료처리 취소 가능(FINISH_CANCEL)
        //결재 미완료시-수료미처리시 - 수료처리 가능(FINISH_PROCESS)

        //결재정보가져오기

        if (data.getApprovalstatus().equals("Y")) {//결재완료시
            //결재 완료
            data.setSubjectaction(FinishBean.FINISH_COMPLETE);
        } else {
            //수료처리 완료시 취소가능
            if (data.getIsclosed().equals("Y")) {
                data.setSubjectaction(FinishBean.FINISH_CANCEL);
            } else {
                //과정이 종료되어야 수료처리 가능
                //과정운영날짜가 있고 수강생이 있을경우에만 수료처리 가능

                if (!data.getEduend().equals("") && Integer.parseInt((String) info.get("studentcnt")) != 0) {
                    if (Integer.valueOf(currdate).intValue() > getInt(data.getEduend()))
                        data.setSubjectaction(FinishBean.FINISH_PROCESS);
                }
            }
        }

        // 과정처리 버튼 [처리완료|처리완료+취소|수료처리|수료율체크] - 이전
        //if (data.getIsclosed().equals("Y")) {
        //    // 수료처리 했으나 학습종료일 이후 2일 이내면 취소버튼 생김
        //    if  (FormatDate.datediff("d",currdate, data.getEduend()) > 2) {
        //        data.setSubjectaction(FinishBean.FINISH_COMPLETE);
        //    } else {
        //        data.setSubjectaction(FinishBean.FINISH_CANCEL);
        //    }
        //} else {
        //    if  (Integer.valueOf(currdate).intValue() > getInt(data.getEduend())) {
        //        data.setSubjectaction(FinishBean.FINISH_PROCESS);
        //    } else {
        //        data.setSubjectaction(FinishBean.SCORE_COMPUTE);
        //    }
        //}

    }

    public SubjseqData getSubjseqInfo(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        SubjseqData data = new SubjseqData();

        // 과정차수 정보
        sql.append(" SELECT B.ISGOYONG,   B.ISCLOSED,  B.EDUSTART, B.EDUEND, \n");
        sql.append("        B.WSTEP,      B.WMTEST,    B.WFTEST,   B.WHTEST,	B.WREPORT, \n");
        sql.append("        B.WACT,       B.WETC1,     B.WETC2, \n");
        sql.append("        B.GRADSCORE,  B.GRADSTEP,  B.GRADEXAM, B.GRADFTEST, B.GRADHTEST, B.GRADREPORT, \n");
        sql.append("        B.GRCODE,     B.GRSEQ,     B.GYEAR,    B.SUBJNM, \n");
        sql.append("        A.ISONOFF,    B.BIYONG, \n");
        sql.append("        NVL(B.GRADETC1,0) AS GRADETC1 \n");
        sql.append("   FROM TZ_SUBJ     A, \n");
        sql.append("        TZ_SUBJSEQ  B \n");
        sql.append("  WHERE A.SUBJ    = B.SUBJ \n");
        sql.append("    AND B.SUBJ    = " + SQLString.Format(p_subj) + " \n");
        sql.append("    AND B.YEAR    = " + SQLString.Format(p_year) + " \n");
        sql.append("    AND B.SUBJSEQ = " + SQLString.Format(p_subjseq) + " \n");

        try {

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data.setIsgoyong(ls.getString("isgoyong"));
                data.setIsclosed(ls.getString("isclosed"));
                data.setEdustart(ls.getString("edustart"));
                data.setEduend(ls.getString("eduend"));

                data.setWstep((int) ls.getDouble("wstep"));
                data.setWmtest((int) ls.getDouble("wmtest"));
                data.setWftest((int) ls.getDouble("wftest"));
                data.setWhtest((int) ls.getDouble("whtest"));
                data.setWreport((int) ls.getDouble("wreport"));
                data.setWact((int) ls.getDouble("wact"));
                data.setWetc1((int) ls.getDouble("wetc1"));
                data.setWetc2((int) ls.getDouble("wetc2"));

                data.setGradscore(ls.getInt("gradscore"));
                data.setGradstep(ls.getInt("gradstep"));
                data.setGradexam(ls.getInt("gradexam"));
                data.setGradftest(ls.getInt("gradftest"));
                data.setGradhtest(ls.getInt("gradhtest"));
                data.setGradreport(ls.getInt("gradreport"));
                data.setGradetc1(ls.getInt("gradetc1"));

                data.setGrcode(ls.getString("grcode"));
                data.setGyear(ls.getString("gyear"));
                data.setGrseq(ls.getString("grseq"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setGrcodenm(GetCodenm.get_grcodenm(data.getGrcode()));
                data.setGrseqnm(GetCodenm.get_grseqnm(data.getGrcode(), data.getGyear(), data.getGrseq()));

                data.setIsonoff(ls.getString("isonoff"));
                data.setBiyong(ls.getInt("biyong"));
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return data;
    }

    /**
     * 수료처리 상세화면 인포정보
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox getSubjseqInfoDbox(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        // 과정차수 정보
        sql = " select b.isgoyong,   ";
        //decode((select count(*) from tz_stold where subj=b.subj and year=b.year and subjseq=b.subjseq),0,'N','Y') isclosed,
        sql += "        b.isclosed,   ";
        sql += "        b.edustart, b.eduend,  b.wstep,      b.wmtest,    b.wftest,   b.whtest,	b.wreport, ";
        sql += "        b.wact,       b.wetc1,     b.wetc2, ";
        sql += "        b.gradscore,  b.gradstep,  b.gradexam, b.gradftest, b.gradhtest, b.gradreport,";
        sql += "        b.grcode,     b.grseq,     b.gyear,    b.subjnm, ";
        sql += "        a.isonoff,    b.biyong,	  (select grcodenm from tz_grcode where grcode = b.grcode) grcodenm,  ";
        sql += "		   a.isoutsourcing, ";
        sql += "		   b.iscpresult, ";
        sql += "		   b.subjseqgr,";
        sql += "		   b.iscpflag, ";
        //sql+= "		   NVL((select isapproval from tz_approval where gubun = 4 and subj=b.subj and year=b.year and subjseq=b.subjseq),'') isapproval, ";
        sql += "		   (select count(*) from tz_student z left join tz_propose x on z.subj=x.subj and z.year=x.year and z.subjseq=x.subjseq and z.userid=x.userid where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and x.CHKFINAL='Y') studentcnt, ";
        sql += "		   (select count(*) from tz_stold z left join tz_propose x on z.subj=x.subj and z.year=x.year and z.subjseq=x.subjseq and z.userid=x.userid where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and x.CHKFINAL='Y') stoldcnt, ";
        sql += "		   (select count(*) from tz_stold z left join tz_propose x on z.subj=x.subj and z.year=x.year and z.subjseq=x.subjseq and z.userid=x.userid where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and isgraduated='Y' and x.CHKFINAL='Y') stoldycnt, ";
        sql += "		   (select count(*) from tz_stold z left join tz_propose x on z.subj=x.subj and z.year=x.year and z.subjseq=x.subjseq and z.userid=x.userid where z.subj=b.subj and z.year=b.year and z.subjseq=b.subjseq and isgraduated='N' and x.CHKFINAL='Y') stoldncnt ";
        sql += "   from tz_subj     a, ";
        sql += "        tz_subjseq  b";
        sql += "  where a.subj    = b.subj ";
        sql += "    and b.subj    = " + SQLString.Format(p_subj);
        sql += "    and b.year    = " + SQLString.Format(p_year);
        sql += "    and b.subjseq = " + SQLString.Format(p_subjseq);

        try {
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("isgoyong", ls.getString("isgoyong"));
                dbox.put("isclosed", ls.getString("isclosed"));
                dbox.put("edustart", ls.getString("edustart"));
                dbox.put("eduend", ls.getString("eduend"));

                dbox.put("wstep", new Double(ls.getDouble("wstep")));
                dbox.put("wmtest", new Double(ls.getDouble("wmtest")));
                dbox.put("wftest", new Double(ls.getDouble("wftest")));
                dbox.put("whtest", new Double(ls.getDouble("whtest")));
                dbox.put("wreport", new Double(ls.getDouble("wreport")));
                dbox.put("wact", new Double(ls.getDouble("wact")));
                dbox.put("wetc1", new Double(ls.getDouble("wetc1")));
                dbox.put("wetc2", new Double(ls.getDouble("wetc2")));

                dbox.put("gradscore", new Integer(ls.getInt("gradscore")));
                dbox.put("gradstep", new Integer(ls.getInt("gradstep")));
                dbox.put("gradexam", new Integer(ls.getInt("gradexam")));
                dbox.put("gradftest", new Integer(ls.getInt("gradftest")));
                dbox.put("gradhtest", new Integer(ls.getInt("gradhtest")));
                dbox.put("gradreport", new Integer(ls.getInt("gradreport")));

                dbox.put("grcode", ls.getString("grcode"));
                dbox.put("gyear", ls.getString("gyear"));
                dbox.put("grseq", ls.getString("grseq"));
                dbox.put("subjnm", ls.getString("subjnm"));
                dbox.put("grcodenm", ls.getString("grcodenm"));

                dbox.put("isonoff", ls.getString("isonoff"));
                dbox.put("biyong", new Integer(ls.getInt("biyong")));

                dbox.put("isoutsourcing", ls.getString("isoutsourcing"));
                dbox.put("iscpresult", ls.getString("iscpresult"));
                dbox.put("iscpflag", ls.getString("iscpflag"));
                //dbox.put("d_isapproval",ls.getString("isapproval"));
                dbox.put("subjseqgr", ls.getString("subjseqgr"));

                dbox.put("d_studentcnt", new Integer(ls.getInt("studentcnt")));
                dbox.put("d_stoldcnt", new Integer(ls.getInt("stoldcnt")));
                dbox.put("d_stoldycnt", new Integer(ls.getInt("stoldycnt")));
                dbox.put("d_stoldncnt", new Integer(ls.getInt("stoldncnt")));

            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return dbox;
    }

    /**
     * 학습자 커서 (목록)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList getFinishTargetStudent(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        ArrayList list = new ArrayList();
        StoldData data = null;

        String d_grcode = "";

        sql.append(" SELECT GRCODE FROM TZ_SUBJSEQ \n");
        sql.append("  WHERE SUBJ = " + SQLString.Format(p_subj) + " \n");
        sql.append("    AND YEAR = " + SQLString.Format(p_year) + " \n");
        sql.append("    AND SUBJSEQ = " + SQLString.Format(p_subjseq) + " \n");

        try {
            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                d_grcode = ls.getString("grcode");
            }
        } catch (Exception e) {
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            sql.setLength(0);
        }

        // 학습자 커서
        sql.append(" SELECT A.SUBJ, A.YEAR, A.SUBJSEQ, A.USERID, B.NAME,   B.COMP, \n");
        sql.append("		(SELECT LEGACYDEPT FROM TZ_COMP WHERE COMP=B.COMP) DEPT, \n");
        sql.append("		(SELECT DEPTNM     FROM TZ_COMP WHERE COMP=B.COMP) DEPTNAME, \n");
        sql.append("        B.JIKWI, A.ISB2C, A.TSTEP, A.AVTSTEP, A.AVMTEST, A.AVFTEST, \n");
        sql.append("        A.AVHTEST, A.AVREPORT, A.AVACT, A.AVETC1, A.AVETC2, A.ISGOYONG, A.SAMTOTAL \n");
        sql.append("   FROM TZ_STUDENT A, \n");
        sql.append("        TZ_MEMBER B \n");
        sql.append("  WHERE A.USERID = B.USERID \n");
        sql.append("    AND A.SUBJ = " + SQLString.Format(p_subj) + " \n");
        sql.append("    AND A.YEAR = " + SQLString.Format(p_year) + " \n");
        sql.append("    AND A.SUBJSEQ = " + SQLString.Format(p_subjseq) + " \n");
        sql.append("    AND B.GRCODE = " + SQLString.Format(d_grcode) + " \n");
        if (!p_userid.equals("ALL")) {
            sql.append("    AND A.USERID = " + SQLString.Format(p_userid) + " \n");
        }
        sql.append(" ORDER BY A.USERID \n");

        try {

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data = new StoldData();

                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setUserid(ls.getString("userid"));
                data.setName(ls.getString("name"));
                data.setComp(ls.getString("comp"));
                data.setJik(ls.getString("jikwi"));
                data.setIsb2c(ls.getString("isb2c"));
                data.setTstep(ls.getDouble("tstep"));
                data.setAvtstep(ls.getDouble("avtstep"));
                data.setAvmtest(ls.getDouble("avmtest"));
                data.setAvftest(ls.getDouble("avftest"));
                data.setAvhtest(ls.getDouble("avhtest"));
                data.setAvreport(ls.getDouble("avreport"));
                data.setAvact(ls.getDouble("avact"));
                data.setAvetc1(ls.getDouble("avetc1"));
                data.setAvetc2(ls.getDouble("avetc2"));
                data.setDept(ls.getString("dept"));
                data.setDeptname(ls.getString("deptname"));
                data.setIsgoyong(ls.getString("isgoyong")); // 고용여부
                data.setSamtotal(ls.getDouble("samtotal")); // 삼진아웃
                list.add(data);
            }

        } catch (Exception ex) {
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

    /**
     * 수료정보 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteStoldTable(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        sql = " delete from tz_stold ";
        sql += "  where subj    = ? ";
        sql += "    and year    = ? ";
        sql += "    and subjseq = ? ";

        try {
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_year);
            pstmt.setString(3, p_subjseq);
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            isOk = -1;
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int chkRemainReport(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql = "";

        //        int v_allcnt = 0;
        int v_notcnt = 0;

        try {
            // 미채점된 Report존재여부 체크
            ////sql = " select count(distinct ordseq) allcnt";
            ////sql+= "   from tz_projrep ";
            ////sql+= "  where subj    = " + SQLString.Format(p_subj);
            ////sql+= "    and year    = " + SQLString.Format(p_year);
            ////sql+= "    and subjseq = " + SQLString.Format(p_subjseq);
            ////sql+= "    and projid = " + SQLString.Format(p_userid);
            ////ls1 = connMgr.executeQuery(sql);
            ////while (ls1.next()) {
            ////    v_allcnt = ls1.getInt("allcnt");
            ////}

            sql = " select count(distinct ordseq) notcnt";
            sql += "   from tz_projrep ";
            sql += "  where subj    = " + SQLString.Format(p_subj);
            sql += "    and year    = " + SQLString.Format(p_year);
            sql += "    and subjseq = " + SQLString.Format(p_subjseq);
            sql += "    and projid = " + SQLString.Format(p_userid);
            sql += "    and score is not null ";
            //sql+= "    and score > 0 ";
            sql += "    and isfinal = 'N' ";

            ls2 = connMgr.executeQuery(sql);
            while (ls2.next()) {
                v_notcnt = ls2.getInt("notcnt");
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
        }

        ////return (v_allcnt - v_notcnt);
        return v_notcnt;

    }

    /**
     * 수료필드 업데이트
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int setCloseColumn(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_isclosed) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        sql = " update tz_subjseq ";
        sql += "     set isclosed = ? ";
        sql += "  where subj     = ? ";
        sql += "    and year     = ? ";
        sql += "    and subjseq  = ? ";

        try {
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_isclosed);
            pstmt.setString(2, p_subj);
            pstmt.setString(3, p_year);
            pstmt.setString(4, p_subjseq);
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            isOk = -1;
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 사이버 과정 수료처리
     * 
     * @param box receive from the form object
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int SubjectComplete(RequestBox box) throws Exception {
        // DB 처리 관련 변수
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        String sql = "";

        // 수료처리시 필요한 기본 변수들
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = "";
        String v_luserid = box.getSession("userid");

        ArrayList list = null; // 수료대상학생 정보 커서
        StoldData data = null;
        SubjseqData subjseqdata = null;
        String v_currdate = FormatDate.getDate("yyyyMMddHH"); // 현재시각 년+월+일+시
        //		int v_biyong =0; //수강료

        int isOk = 1;
        boolean v_isexception = false;
        String v_return_msg = "";
        //      1 : "정상적으로 수료처리 되었습니다."
        //      2 : "이미 수료처리 되었습니다."
        //      3 : "과정시작후 가능합니다.
        //      4 : "수료처리==> 점수 재산정 중 문제발생함[" + v_userid + "]"
        //      5 : "학습자의 리포트 중 " + String.valueOf(v_remainReportcnt) + " 개가 미채점되었습니다. \n\n수료처리취소후 점검하여 주십시오";
        //      "excaption 발생"

        // 수료번호 -  수료처리 결재완료시 수료번호를 생성한다.
        int v_serno_cnt = 0;
        String v_serno = "";
        double v_samtotal = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            subjseqdata = getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);

            if (subjseqdata.getIsclosed().equals("Y")) {
                v_return_msg = "이미 수료처리 되었습니다.";
                isOk = 2;
                return isOk;
                //} else if (FormatDate.getDate2(v_currdate).before(FormatDate.getDate2(subjseqdata.getEduend()))) {
            } else if (FormatDate.getDate2(v_currdate).before(FormatDate.getDate2(subjseqdata.getEdustart()))) {
                //v_return_msg  = "과정종료후 가능합니다.";
                v_return_msg = "학습시작후 가능합니다.";
                isOk = 3;
                return isOk;
            }

            // 수료 정보 삭제
            deleteStoldTable(connMgr, v_subj, v_year, v_subjseq);

            // 수료 정보 입력 psmt
            sql = " insert into tz_stold ";
            sql += "  (subj,         year,       subjseq,    userid,   ";
            sql += "   name,         comp,       dept,       deptname, ";
            sql += "   jik,          score,      tstep,      mtest,    ";
            sql += "   ftest,        report,     act,        etc1,     ";
            sql += "   etc2,         avtstep,    avmtest,    avftest,  ";
            sql += "   avreport,     avact,      avetc1,     avetc2,   ";
            sql += "   isgraduated,  isb2c,      edustart,   eduend,   serno, ";
            sql += "   isrestudy,  luserid,      isgoyong,   ldate, htest, avhtest, notgraducd)    ";
            sql += " values ";
            sql += "  (?,            ?,          ?,          ?, ";
            sql += "   ?,            ?,          ?,          ?, ";
            sql += "   ?,            ?,          ?,          ?, ";
            sql += "   ?,            ?,          ?,          ?, ";
            sql += "   ?,            ?,          ?,          ?, ";
            sql += "   ?,            ?,          ?,          ?, ";
            sql += "   ?,            ?,          ?,          ?, ?, ";
            sql += "   ?,            ?,          ?,          to_char(sysdate, 'YYYYMMDDHH24MISS'), ";
            sql += "   ?,            ?,          ?) ";

            pstmt = connMgr.prepareStatement(sql);

            // 학습자 커서
            list = getFinishTargetStudent(connMgr, v_subj, v_year, v_subjseq, "ALL");

            for (int i = 0; i < list.size(); i++) {
                data = (StoldData) list.get(i);

                data.setWstep(subjseqdata.getWstep());
                data.setWmtest(subjseqdata.getWmtest());
                data.setWftest(subjseqdata.getWftest());
                data.setWhtest(subjseqdata.getWhtest());
                data.setWreport(subjseqdata.getWreport());
                data.setWact(subjseqdata.getWact());
                data.setWetc1(subjseqdata.getWetc1());
                data.setWetc2(subjseqdata.getWetc2());
                data.setGradscore(subjseqdata.getGradscore());
                data.setGradstep(subjseqdata.getGradstep());
                data.setGradexam(subjseqdata.getGradexam());
                data.setGradreport(subjseqdata.getGradreport());

                v_userid = data.getUserid();
                v_samtotal = data.getSamtotal(); // 삼진아웃

                // 삼진아웃 시 미수료
                if (v_samtotal > 2) {
                    v_isexception = true;
                    v_return_msg = v_userid + " 학습자는 삼진아웃 상태입니다. ";
                    isOk = 6;
                    return isOk;
                }

                // 미채점 리포트 갯수 확인
                int v_remainReportcnt = 0;

                v_remainReportcnt = chkRemainReport(connMgr, v_subj, v_year, v_subjseq, v_userid);
                if (v_remainReportcnt > 0) {
                    v_isexception = true;
                    v_return_msg = v_userid + "학습자의 리포트 중 " + String.valueOf(v_remainReportcnt) + " 개가 미채점되었습니다.";
                    isOk = 5;
                    return isOk;
                }

                // 점수 재계산
                try {
                    calc_score(connMgr, "ALL", v_subj, v_year, v_subjseq, v_userid, v_luserid, data);
                } catch (Exception ex) {
                    v_isexception = true;
                    v_return_msg = "수료처리==> 점수 재산정 중 문제발생함[" + v_userid + "]";
                    v_return_msg += ex.getMessage();

                    isOk = 4;
                    return isOk;
                }

                // 수료정보 INSERT
                if (data.getIsgraduated().equals("Y")) {
                    v_serno_cnt++;
                    //v_serno = String.valueOf(v_serno);

                    // 직무과정인지 여부를 가져옴
                    ListSet ls = null;
                    sql = "";
                    String isunit = "";
                    String yeunsuno = "";

                    sql = " select nvl(isunit,'N') as isunit,nvl(YEUNSUNO,'') as YEUNSUNO from tz_subjseq a \n";
                    sql += "left join TZ_PROPOSE_ADDINFO b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and userid='" + v_userid + "'\n";
                    sql += "where a.subj='" + v_subj + "' and a.year='" + v_year + "' and a.subjseq='" + v_subjseq + "' and YEUNSUNO is not null";

                    ls = connMgr.executeQuery(sql);
                    if (ls.next()) {
                        isunit = ls.getString("isunit");
                        yeunsuno = ls.getString("yeunsuno");
                    }

                    ls.close();

                    // 수료증번호 발급
                    if (isunit.equals("Y") && !yeunsuno.equals("")) { //직무과정
                        v_serno = (getCompleteSernoFunction(connMgr, v_subj, v_year, v_subjseq, v_userid)).toUpperCase();
                    } else { //일반과정
                        v_serno = (getCompleteSerno(connMgr, v_subj, v_year, v_subjseq, v_userid)).toUpperCase();
                    }

                    // 학습자 테이블 수료='Y' update
                    updateStudentIsgraduated(connMgr, v_subj, v_year, v_subjseq, v_userid, "Y");

                } else {
                    v_serno = "";
                }
                pstmt.setString(1, v_subj);
                pstmt.setString(2, v_year);
                pstmt.setString(3, v_subjseq);
                pstmt.setString(4, v_userid);

                pstmt.setString(5, data.getName());
                pstmt.setString(6, data.getComp());
                pstmt.setString(7, data.getDept()); //
                pstmt.setString(8, data.getDeptname()); //

                pstmt.setString(9, data.getJik());
                pstmt.setDouble(10, data.getScore());
                pstmt.setDouble(11, data.getTstep());
                pstmt.setDouble(12, data.getMtest());

                pstmt.setDouble(13, data.getFtest());
                pstmt.setDouble(14, data.getReport());
                pstmt.setDouble(15, data.getAct());
                pstmt.setDouble(16, data.getEtc1());

                pstmt.setDouble(17, data.getEtc2());
                pstmt.setDouble(18, data.getAvtstep());
                pstmt.setDouble(19, data.getAvmtest());
                pstmt.setDouble(20, data.getAvftest());

                pstmt.setDouble(21, data.getAvreport());
                pstmt.setDouble(22, data.getAvact());
                pstmt.setDouble(23, data.getAvetc1());
                pstmt.setDouble(24, data.getAvetc2());

                pstmt.setString(25, data.getIsgraduated());
                pstmt.setString(26, data.getIsb2c());
                pstmt.setString(27, subjseqdata.getEdustart());
                pstmt.setString(28, subjseqdata.getEduend());

                pstmt.setString(29, v_serno); // 수료증번호
                pstmt.setString(30, data.getIsrestudy());
                pstmt.setString(31, v_luserid);
                pstmt.setString(32, data.getIsgoyong()); // 고용여부
                pstmt.setDouble(33, data.getHtest());
                pstmt.setDouble(34, data.getAvhtest());
                pstmt.setString(35, data.getNotgraducd());

                isOk = pstmt.executeUpdate();

                // 수료증번호 발급
                isOk = updateStudentSerno(connMgr, v_serno, v_subj, v_year, v_subjseq, v_userid);

                // 수료/미수료 통보 메일 보낼 자리
                //sendFinishMail(connMgr, box, v_userid, subjseqdata.getIsonoff(), subjseqdata.getSubjnm(), data.getIsgraduated(), subjseqdata.getBiyong());
            }
            // 수료 필드 수정 - isclosed = 'Y'
            isOk = setCloseColumn(connMgr, v_subj, v_year, v_subjseq, "Y");
        } catch (Exception ex) {
            connMgr.rollback();
            v_isexception = true;
            v_return_msg = ex.getMessage();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (!v_return_msg.equals(""))
                box.put("p_return_msg", v_return_msg);

            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 사이버 과정 점수재계산
     * 
     * @param box receive from the form object
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int SubjectCompleteRerating(RequestBox box) throws Exception {
        // DB 처리 관련 변수
        DBConnectionManager connMgr = null;
        //        PreparedStatement pstmt = null;
        String sql = "";

        // 수료처리시 필요한 기본 변수들
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = "";
        String v_luserid = box.getSession("userid");

        ArrayList list = null; // 수료대상학생 정보 커서
        StoldData data = null;
        SubjseqData subjseqdata = null;
        String v_currdate = FormatDate.getDate("yyyyMMddHH"); // 현재시각 년+월+일+시

        int isOk = 1;
        boolean v_isexception = false;
        String v_return_msg = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            subjseqdata = getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);

            if (subjseqdata.getIsclosed().equals("Y")) {
                v_return_msg = "이미 수료처리 되었습니다.";
                isOk = 2;
                return isOk;
                //} else if (FormatDate.getDate2(v_currdate).before(FormatDate.getDate2(subjseqdata.getEduend()))) {
            } else if (FormatDate.getDate2(v_currdate).before(FormatDate.getDate2(subjseqdata.getEdustart()))) {
                //v_return_msg  = "과정종료후 가능합니다.";
                v_return_msg = "학습시작후 가능합니다.";
                isOk = 3;
                return isOk;
            }

            // 학습자 커서
            list = getFinishTargetStudent(connMgr, v_subj, v_year, v_subjseq, "ALL");

            for (int i = 0; i < list.size(); i++) {
                data = (StoldData) list.get(i);

                data.setWstep(subjseqdata.getWstep());
                data.setWmtest(subjseqdata.getWmtest());
                data.setWftest(subjseqdata.getWftest());
                data.setWhtest(subjseqdata.getWhtest());
                data.setWreport(subjseqdata.getWreport());
                data.setWact(subjseqdata.getWact());
                data.setWetc1(subjseqdata.getWetc1());
                data.setWetc2(subjseqdata.getWetc2());
                data.setGradscore(subjseqdata.getGradscore());
                data.setGradstep(subjseqdata.getGradstep());
                data.setGradexam(subjseqdata.getGradexam());
                data.setGradreport(subjseqdata.getGradreport());
                data.setGradetc1(subjseqdata.getGradetc1());

                v_userid = data.getUserid();

                // 점수 재계산
                try {
                    isOk = calc_score(connMgr, "ALL", v_subj, v_year, v_subjseq, v_userid, v_luserid, data);

                } catch (Exception ex) {
                    v_isexception = true;
                    v_return_msg = "수료처리==> 점수 재산정 중 문제발생함[" + v_userid + "]";
                    v_return_msg += ex.getMessage();

                    isOk = 4;
                    return isOk;
                }
            } // for end
        } catch (Exception ex) {
            v_isexception = true;
            v_return_msg = ex.getMessage();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (!v_return_msg.equals(""))
                box.put("p_return_msg", v_return_msg);

            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    public boolean sendFinishMail(DBConnectionManager connMgr, RequestBox box, String p_userid, String p_isonoff, String p_subjnm, String p_isgraduated, int p_biyong) throws Exception {
        boolean isMailed = false;
        //        String v_mailContent = "";

        ///////////////////////////////////////////////////////////////////////////////
        //        String  v_toCono   = "";
        //        String  v_toEmail  = "";
        //        String  v_ismailing= "";
        String v_toname = ""; //님이 tz_member
        String v_thismon = StringManager.substring(FormatDate.getDate("yyyyMMddhh"), 4, 6); //현재월

        ListSet ls = null;
        String sql = "";

        sql = "select cono, email, name, ismailing ";
        sql += "  from tz_member ";
        sql += " where userid = " + SQLString.Format(p_userid);

        try {
            ////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "";
            FormMail fmail = null;
            MailSet mset = null;
            //            String v_mailTitle = "";

            if (p_isgraduated.equals("Y")) {
                v_sendhtml = "mail4.html";
                fmail = new FormMail(v_sendhtml); //      폼메일발송인 경우
                mset = new MailSet(box); //      메일 세팅 및 발송
                //                v_mailTitle = "안녕하세요? 현대/기아자동차 사이버 연수원 운영자입니다.(수료안내)";
            } else {
                v_sendhtml = "mail5.html";
                fmail = new FormMail(v_sendhtml); //      폼메일발송인 경우
                mset = new MailSet(box); //      메일 세팅 및 발송
                //                v_mailTitle = "안녕하세요? 현대/기아자동차 사이버 연수원 운영자입니다.(미수료안내)";
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            mset.setSender(fmail); //  메일보내는 사람 세팅

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                //                v_toCono  = ls.getString("cono");
                //                v_toEmail = ls.getString("email");
                v_toname = ls.getString("name");
                //                v_ismailing = ls.getString("ismailing");
            }
            if (p_isonoff.equals("ON")) {
                p_isonoff = "사이버과정";
            } else if (p_isonoff.equals("OFF")) {
                p_isonoff = "집합과정";
            }

            fmail.setVariable("toname", v_toname);
            fmail.setVariable("isonoff", p_isonoff);
            fmail.setVariable("subjnm", p_subjnm);
            fmail.setVariable("biyong", String.valueOf(p_biyong));//수강료
            fmail.setVariable("month", v_thismon);//현재월

            //            v_mailContent =
            fmail.getNewMailContent();

            //isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent,v_ismailing, v_sendhtml);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return isMailed;
    }

    // 사이버 수료취소
    public int SubjectCompleteCancel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        //        SubjseqData subjseqdata = null;
        boolean v_isexception = false;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            //            subjseqdata =
            getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);
            ////if (subjseqdata.getIsclosed().equals("Y")) {
            // 수료 정보 삭제
            isOk = deleteStoldTable(connMgr, v_subj, v_year, v_subjseq);
            // 수료 필드 수정 - isclosed = 'N'
            isOk = setCloseColumn(connMgr, v_subj, v_year, v_subjseq, "N");

            //외주과정 최종확인 N
            isOk = this.updateIsCpflag(connMgr, v_subj, v_year, v_subjseq, "N");
            ////}

            // 학습자 테이블 tz_student 수료필드->'N'
            updateStudentIsgraduated(connMgr, v_subj, v_year, v_subjseq, "", "N");

        } catch (Exception ex) {
            v_isexception = true;
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    // isOk = 1 : "에러가 없는 경우"
    // isOk = 2 : "이미 수료처리 되었습니다."
    // isOk = 3 : 기타 에러
    // isOk = 4 : calc_score() 계산에서 에러
    @SuppressWarnings("unchecked")
    public int ScoreCompute(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        int isOk = 1;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_luserid = box.getSession("userid");

        StoldData data = null;
        SubjseqData subjseqdata = null;
        ArrayList list = null; // 수료대상학생 정보 커서

        boolean v_isexception = false;
        String v_return_msg = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            subjseqdata = getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);
            if (subjseqdata.getIsclosed().equals("Y")) {
                v_return_msg = "이미 수료처리 되었습니다.";
                isOk = 2;
                return isOk;
            }

            // 학습자 커서
            list = getFinishTargetStudent(connMgr, v_subj, v_year, v_subjseq, "ALL");
            for (int i = 0; i < list.size(); i++) {
                data = (StoldData) list.get(i);

                data.setWstep(subjseqdata.getWstep());
                data.setWmtest(subjseqdata.getWmtest());
                data.setWftest(subjseqdata.getWftest());
                data.setWreport(subjseqdata.getWreport());
                data.setWact(subjseqdata.getWact());
                data.setWetc1(subjseqdata.getWetc1());
                data.setWetc2(subjseqdata.getWetc2());
                data.setGradscore(subjseqdata.getGradscore());
                data.setGradstep(subjseqdata.getGradstep());

                // 점수 재계산
                try {
                    v_return_msg = "";
                    calc_score(connMgr, "ALL", v_subj, v_year, v_subjseq, data.getUserid(), v_luserid, data);
                } catch (Exception ex) {
                    v_isexception = true;
                    v_return_msg = "수료처리==> 점수 재산정 중 문제발생함[" + data.getUserid() + "]";
                    v_return_msg += ex.getMessage();
                    isOk = 4;
                    return isOk;
                }
            }
        } catch (Exception ex) {
            v_isexception = true;
            isOk = 3;
            v_return_msg = ex.getMessage();
            throw new Exception(ex.getMessage());
        } finally {
            if (!v_return_msg.equals(""))
                box.put("p_return_msg", v_return_msg);

            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 사이버 점수 계산
     * 
     * @param box receive from the form object and session 8
     * @return int
     */
    public int calc_score(DBConnectionManager connMgr, String p_gubun, String p_subj, String p_year, String p_subjseq, String p_userid, String p_luserid, StoldData data) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        String v_contenttype = "";
        ListSet ls = null;
        int isOk = 0;

        try {
            sql = "select * from ( select rownum rnum, NVL(contenttype,'N') contenttype ";
            sql += "  from tz_subj ";
            sql += " where subj=" + SQLString.Format(p_subj);
            sql += "   ) where rnum < 2";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_contenttype = ls.getString("contenttype");
            }
            ls.close();

            if (v_contenttype.equals("S") || v_contenttype.equals("O") || v_contenttype.equals("N") || v_contenttype.equals("M")) {
                isOk = calc_step(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_exam(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_report(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_activity(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_etc(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_isgraduated(connMgr, p_subj, p_year, p_subjseq, p_userid, data);

                // 점수 수정
                sql = " update tz_student ";
                sql += "    set score   = ?, ";
                sql += "        tstep   = ?, ";
                sql += "        mtest   = ?, ";
                sql += "        ftest   = ?, ";
                sql += "        htest   = ?, ";
                sql += "        report  = ?, ";
                sql += "        act     = ?, ";
                sql += "        etc1    = ?, ";
                sql += "        etc2    = ?, ";
                sql += "        avtstep = ?, ";
                sql += "        avmtest = ?, ";
                sql += "        avftest = ?, ";
                sql += "        avhtest = ?, ";
                sql += "        avreport= ?, ";
                sql += "        avact   = ?, ";
                sql += "        avetc1  = ?, ";
                sql += "        avetc2  = ?, ";
                sql += "        luserid = ?, ";
                sql += "        ldate   = ? ";
                sql += "  where subj    = ? ";
                sql += "    and year    = ? ";
                sql += "    and subjseq = ? ";
                sql += "    and userid  = ? ";
                //		System.out.println("calc_score");data.getIsgr//
                pstmt = connMgr.prepareStatement(sql);

                pstmt.setDouble(1, data.getScore());
                pstmt.setDouble(2, data.getTstep());
                pstmt.setDouble(3, data.getMtest());
                pstmt.setDouble(4, data.getFtest());
                pstmt.setDouble(5, data.getHtest());//System.out.println("data.getHtest()" + data.getHtest());
                pstmt.setDouble(6, data.getReport());
                pstmt.setDouble(7, data.getAct());
                pstmt.setDouble(8, data.getEtc1());
                pstmt.setDouble(9, data.getEtc2());
                pstmt.setDouble(10, data.getAvtstep());
                pstmt.setDouble(11, data.getAvmtest());
                pstmt.setDouble(12, data.getAvftest());
                pstmt.setDouble(13, data.getAvhtest());//System.out.println("data.getAvhtest()" + data.getAvhtest());
                pstmt.setDouble(14, data.getAvreport());
                pstmt.setDouble(15, data.getAvact());
                pstmt.setDouble(16, data.getAvetc1());
                pstmt.setDouble(17, data.getAvetc2());
                pstmt.setString(18, p_luserid);
                pstmt.setString(19, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt.setString(20, p_subj);
                pstmt.setString(21, p_year);
                pstmt.setString(22, p_subjseq);
                pstmt.setString(23, p_userid);

                isOk = pstmt.executeUpdate();
            } else if (v_contenttype.equals("Y")) {

                //isOk = calc_step_yeslearn (connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_exam(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_report(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_activity(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_etc(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                isOk = calc_isgraduated(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                // 점수 수정
                sql = " update tz_student ";
                sql += "    set score   = ?, ";
                sql += "        mtest   = ?, ";
                sql += "        ftest   = ?, ";
                sql += "        htest   = ?, ";
                sql += "        report  = ?, ";
                sql += "        act     = ?, ";
                sql += "        etc1    = ?, ";
                sql += "        etc2    = ?, ";
                sql += "        avtstep = ?, ";
                sql += "        avmtest = ?, ";
                sql += "        avftest = ?, ";
                sql += "        avhtest = ?, ";
                sql += "        avreport= ?, ";
                sql += "        avact   = ?, ";
                sql += "        avetc1  = ?, ";
                sql += "        avetc2  = ?, ";
                sql += "        luserid = ?, ";
                sql += "        ldate   = ? ";
                sql += "  where subj    = ? ";
                sql += "    and year    = ? ";
                sql += "    and subjseq = ? ";
                sql += "    and userid  = ? ";
                //		System.out.println("calc_score");
                pstmt = connMgr.prepareStatement(sql);

                pstmt.setDouble(1, data.getScore());
                pstmt.setDouble(2, data.getMtest());
                pstmt.setDouble(3, data.getFtest());
                pstmt.setDouble(4, data.getHtest());//System.out.println("data.getHtest()" + data.getHtest());
                pstmt.setDouble(5, data.getReport());
                pstmt.setDouble(6, data.getAct());
                pstmt.setDouble(7, data.getEtc1());
                pstmt.setDouble(8, data.getEtc2());
                pstmt.setDouble(9, data.getAvtstep());
                pstmt.setDouble(10, data.getAvmtest());
                pstmt.setDouble(11, data.getAvftest());
                pstmt.setDouble(12, data.getAvhtest());//System.out.println("data.getAvhtest()" + data.getAvhtest());
                pstmt.setDouble(13, data.getAvreport());
                pstmt.setDouble(14, data.getAvact());
                pstmt.setDouble(15, data.getAvetc1());
                pstmt.setDouble(16, data.getAvetc2());
                pstmt.setString(17, p_luserid);
                pstmt.setString(18, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt.setString(19, p_subj);
                pstmt.setString(20, p_year);
                pstmt.setString(21, p_subjseq);
                pstmt.setString(22, p_userid);

                isOk = pstmt.executeUpdate();

            } else { // LINK외주타입
                /**
                 * 로직 자체가 의문을 가짐. 반환값과 무관한 처리. 또한 실제로 DB에서는 'M', 'N', 'O'만 가지고
                 * 있으므로 사용될 일도 없음.
                 */
                // tz_student
                sql = " select score   ,mtest   ,ftest   ,htest   ,report  ,act     ,etc1    ,etc2    ,avtstep ,avmtest ,avftest ,avhtest ,avreport   ";
                sql += "       ,avact   ,avetc1  ,avetc2  ,luserid ,ldate , isgraduated                   ";
                sql += " from tz_student  ";
                sql += "  where subj    = " + SQLString.Format(p_subj);
                sql += "    and year    = " + SQLString.Format(p_year);
                sql += "    and subjseq = " + SQLString.Format(p_subjseq);
                sql += "    and userid  = " + SQLString.Format(p_userid);

                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    //ls.getDouble("score");
                    //ls.getDouble("mtest");
                    //ls.getDouble("ftest");
                    //ls.getDouble("htest");
                    //ls.getDouble("report");
                    //ls.getDouble("act");
                    //ls.getDouble("etc1");
                    //ls.getDouble("etc2");
                    //ls.getDouble("avtstep");
                    //ls.getDouble("avmtest");
                    //ls.getDouble("avftest");
                    //ls.getDouble("avhtest");
                    //ls.getDouble("avreport");
                    //ls.getDouble("avact");
                    //ls.getDouble("avetc1");
                    //ls.getDouble("avetc2");
                    //ls.getString("luserid");
                    //ls.getString("ldate");
                    //ls.getString("isgraduated");

                    data.setIsgraduated(ls.getString("isgraduated"));

                }
                ls.close();
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /** Added by LeeSuMin 2004.02.23 */
    public boolean islink_subj(DBConnectionManager connMgr, String p_subj) throws Exception {
        ListSet ls = null;
        String sql = "";
        String v_contenttype = "";
        boolean blchk = false;

        try {
            sql = "select contenttype from tz_subj where subj= " + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);
            ls.next();
            v_contenttype = ls.getString("contenttype");
            if (v_contenttype.trim().equals("L"))
                blchk = true;

        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return blchk;
    }

    /**
     * 점수 계산 (세션변수에서 사용자 id를 가져온다.)
     * 
     * @param box receive from the form object and session 6
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int calc_score(DBConnectionManager connMgr, int p_gubun, String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        StoldData data = new StoldData();
        SubjseqData subjseqdata = null;

        String v_luserid = p_userid; // 세션변수에서 사용자 id를 가져온다.

        String v_contenttype = "";
        ArrayList list = null; // 수료대상학생 정보 커서
        
        String d_grcode = "";

        sql.append(" SELECT GRCODE FROM TZ_SUBJSEQ \n");
        sql.append("  WHERE SUBJ = " + SQLString.Format(p_subj) + " \n");
        sql.append("    AND YEAR = " + SQLString.Format(p_year) + " \n");
        sql.append("    AND SUBJSEQ = " + SQLString.Format(p_subjseq) + " \n");

        try {
            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                d_grcode = ls.getString("grcode");
            }
        } catch (Exception e) {
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            sql.setLength(0);
        }

        try {

            // 수료처리 완료여부, 학습중 검토
            subjseqdata = getSubjseqInfo(connMgr, p_subj, p_year, p_subjseq);

            if (p_year.equals("2000")) {
                return 3;
            }
            if (subjseqdata.getIsclosed().equals("Y")) {
                return 2;
            }

            sql.append(" SELECT CONTENTTYPE FROM TZ_SUBJ WHERE SUBJ = " + SQLString.Format(p_subj) + " \n");
            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                v_contenttype = ls.getString("contenttype");
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            // 학습자 커서
            list = getFinishTargetStudent(connMgr, p_subj, p_year, p_subjseq, p_userid);

            for (int i = 0; i < list.size(); i++) {
                data = (StoldData) list.get(i);

                data.setWstep(subjseqdata.getWstep());
                data.setWmtest(subjseqdata.getWmtest());
                data.setWhtest(subjseqdata.getWhtest());
                data.setWftest(subjseqdata.getWftest());
                data.setWreport(subjseqdata.getWreport());
                data.setWact(subjseqdata.getWact());
                data.setWetc1(subjseqdata.getWetc1());
                data.setWetc2(subjseqdata.getWetc2());
                data.setGradscore(subjseqdata.getGradscore());
                data.setGradstep(subjseqdata.getGradstep());
                data.setGradexam(subjseqdata.getGradexam());
                data.setGradreport(subjseqdata.getGradreport());
                data.setGradftest(subjseqdata.getGradftest());
                data.setGradhtest(subjseqdata.getGradhtest());

                if (p_gubun == CalcUtil.STEP) {
                    if (v_contenttype.equals("S") || v_contenttype.equals("O") || v_contenttype.equals("N") || v_contenttype.equals("M")) {
                        isOk = calc_step(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                    }
                } else if (p_gubun == CalcUtil.EXAM) {
                    isOk = calc_exam(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                } else if (p_gubun == CalcUtil.REPORT) {
                    isOk = calc_report(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                } else if (p_gubun == CalcUtil.ETC) {
                    isOk = calc_etc(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                } else if (p_gubun == CalcUtil.ALL || p_gubun == CalcUtil.EXC) {
                    if (v_contenttype.equals("S") || v_contenttype.equals("O") || v_contenttype.equals("N") || v_contenttype.equals("M")) {
                        isOk = calc_step(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                    } else {
                        isOk = calc_step_yeslearn(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                    }
                    isOk = calc_exam(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                    isOk = calc_report(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                    isOk = calc_etc(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                }

                isOk = calc_isgraduated(connMgr, p_subj, p_year, p_subjseq, p_userid, data);
                
                if("N000241".equals(d_grcode)){
                	sql.setLength(0);
                	
                	sql.append(" SELECT SUBJ 											\n");
                	sql.append("   FROM TZ_SULEACH 										\n");
                	sql.append("  WHERE SUBJ    = " + SQLString.Format(p_subj) + " 		\n");
                	sql.append("    AND GRCODE  = " + SQLString.Format(d_grcode) + " 	\n");
                	sql.append("    AND YEAR    = " + SQLString.Format(p_year) + " 		\n");
                	sql.append("    AND SUBJSEQ = " + SQLString.Format(p_subjseq) + " 	\n");
                	sql.append("    AND USERID  = " + SQLString.Format(p_userid) + " 	\n");
                	
                    ls = connMgr.executeQuery(sql.toString());

                    if (!ls.next()) {
                    	data.setIsgraduated("N");
                    }
                    
                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }
                }
                
                if (p_gubun == CalcUtil.STEP) {
                    sql.setLength(0);

                    sql.append(" UPDATE /* FinishBean.calc_score 1 */TZ_STUDENT \n");
                    sql.append("    SET SCORE   = ?, \n");
                    if (v_contenttype.equals("S") || v_contenttype.equals("O") || v_contenttype.equals("N") || v_contenttype.equals("M")) {
                        sql.append("        TSTEP   = ?, \n");
                    }
                    sql.append("        AVTSTEP = ?, \n");
                    sql.append("        ISGRADUATED= ?, \n");
                    sql.append("        LUSERID = ?, \n");
                    sql.append("        LDATE   = ? \n");
                    sql.append("  WHERE SUBJ    = ? \n");
                    sql.append("    AND YEAR    = ? \n");
                    sql.append("    AND SUBJSEQ = ? \n");
                    sql.append("    AND USERID  = ? \n");

                    pstmt = connMgr.prepareStatement(sql.toString());

                    pstmt.setDouble(1, data.getScore());

                    if (v_contenttype.equals("S") || v_contenttype.equals("O") || v_contenttype.equals("N") || v_contenttype.equals("M")) {
                        pstmt.setDouble(2, data.getTstep());
                        pstmt.setDouble(3, data.getAvtstep());
                        pstmt.setString(4, data.getIsgraduated());
                        pstmt.setString(5, v_luserid);
                        pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
                        pstmt.setString(7, p_subj);
                        pstmt.setString(8, p_year);
                        pstmt.setString(9, p_subjseq);
                        pstmt.setString(10, p_userid);
                    } else {
                        pstmt.setDouble(2, data.getAvtstep());
                        pstmt.setString(3, data.getIsgraduated());
                        pstmt.setString(4, v_luserid);
                        pstmt.setString(5, FormatDate.getDate("yyyyMMddHHmmss"));
                        pstmt.setString(6, p_subj);
                        pstmt.setString(7, p_year);
                        pstmt.setString(8, p_subjseq);
                        pstmt.setString(9, p_userid);
                    }
                } else if (p_gubun == CalcUtil.EXAM) {
                    sql.setLength(0);

                    sql.append(" UPDATE /* FinishBean.calc_score 2 */TZ_STUDENT \n");
                    sql.append("    SET SCORE   = ?, \n");
                    sql.append("        MTEST   = ?, \n");
                    sql.append("        HTEST   = ?, \n");
                    sql.append("        FTEST   = ?, \n");
                    sql.append("        AVMTEST = ?, \n");
                    sql.append("        AVFTEST = ?, \n");
                    sql.append("        ISGRADUATED= ?, \n");
                    sql.append("        LUSERID = ?, \n");
                    sql.append("        LDATE   = ? \n");
                    sql.append("  WHERE SUBJ    = ? \n");
                    sql.append("    AND YEAR    = ? \n");
                    sql.append("    AND SUBJSEQ = ? \n");
                    sql.append("    AND USERID  = ? \n");

                    pstmt = connMgr.prepareStatement(sql.toString());

                    pstmt.setDouble(1, data.getScore());
                    pstmt.setDouble(2, data.getMtest());
                    pstmt.setDouble(3, data.getHtest());
                    pstmt.setDouble(4, data.getFtest());
                    pstmt.setDouble(5, data.getAvmtest());
                    pstmt.setDouble(6, data.getAvhtest());
                    pstmt.setDouble(7, data.getAvftest());
                    pstmt.setString(8, data.getIsgraduated());
                    pstmt.setString(9, v_luserid);
                    pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));
                    pstmt.setString(11, p_subj);
                    pstmt.setString(12, p_year);
                    pstmt.setString(13, p_subjseq);
                    pstmt.setString(14, p_userid);
                } else if (p_gubun == CalcUtil.REPORT) {
                    sql.setLength(0);

                    sql.append(" UPDATE /* FinishBean.calc_score 3 */TZ_STUDENT \n");
                    sql.append("    SET SCORE   = ?, \n");
                    sql.append("        REPORT  = ?, \n");
                    sql.append("        AVREPORT= ?, \n");
                    sql.append("        ISGRADUATED= ?, \n");
                    sql.append("        LUSERID = ?, \n");
                    sql.append("        LDATE   = ? \n");
                    sql.append("  WHERE SUBJ    = ? \n");
                    sql.append("    AND YEAR    = ? \n");
                    sql.append("    AND SUBJSEQ = ? \n");
                    sql.append("    AND USERID  = ? \n");

                    pstmt = connMgr.prepareStatement(sql.toString());

                    pstmt.setDouble(1, data.getScore());
                    pstmt.setDouble(2, data.getReport());
                    pstmt.setDouble(3, data.getAvreport());
                    pstmt.setString(4, data.getIsgraduated());
                    pstmt.setString(5, v_luserid);
                    pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
                    pstmt.setString(7, p_subj);
                    pstmt.setString(8, p_year);
                    pstmt.setString(9, p_subjseq);
                    pstmt.setString(10, p_userid);
                } else if (p_gubun == CalcUtil.ACTIVITY) {
                    sql.setLength(0);
                    // 점수 수정
                    sql.append(" UPDATE /* FinishBean.calc_score 4  */TZ_STUDENT \n");
                    sql.append("    SET SCORE   = ?, \n");
                    sql.append("        ACT     = ?, \n");
                    sql.append("        AVACT   = ?, \n");
                    sql.append("        ISGRADUATED= ?, \n");
                    sql.append("        LUSERID = ?, \n");
                    sql.append("        LDATE   = ? \n");
                    sql.append("  WHERE SUBJ    = ? \n");
                    sql.append("    AND YEAR    = ? \n");
                    sql.append("    AND SUBJSEQ = ? \n");
                    sql.append("    AND USERID  = ? \n");

                    pstmt = connMgr.prepareStatement(sql.toString());

                    pstmt.setDouble(1, data.getScore());
                    pstmt.setDouble(2, data.getAct());
                    pstmt.setDouble(3, data.getAvact());
                    pstmt.setString(4, data.getIsgraduated());
                    pstmt.setString(5, v_luserid);
                    pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
                    pstmt.setString(7, p_subj);
                    pstmt.setString(8, p_year);
                    pstmt.setString(9, p_subjseq);
                    pstmt.setString(10, p_userid);
                } else if (p_gubun == CalcUtil.ETC) {
                    sql.setLength(0);
                    // 점수 수정
                    sql.append(" UPDATE /* FinishBean.calc_score 5 */TZ_STUDENT \n");
                    sql.append("    SET SCORE   = ?, \n");
                    sql.append("        ETC1    = ?, \n");
                    sql.append("        ETC2    = ?, \n");
                    sql.append("        AVETC1  = ?, \n");
                    sql.append("        AVETC2  = ?, \n");
                    sql.append("        ISGRADUATED= ?, \n");
                    sql.append("        LUSERID = ?, \n");
                    sql.append("        LDATE   = ? \n");
                    sql.append("  WHERE SUBJ    = ? \n");
                    sql.append("    AND YEAR    = ? \n");
                    sql.append("    AND SUBJSEQ = ? \n");
                    sql.append("    AND USERID  = ? \n");

                    pstmt = connMgr.prepareStatement(sql.toString());

                    pstmt.setDouble(1, data.getScore());
                    pstmt.setDouble(2, data.getEtc1());
                    pstmt.setDouble(3, data.getEtc2());
                    pstmt.setDouble(4, data.getAvetc1());
                    pstmt.setDouble(5, data.getAvetc2());
                    pstmt.setString(6, data.getIsgraduated());
                    pstmt.setString(7, v_luserid);
                    pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));
                    pstmt.setString(9, p_subj);
                    pstmt.setString(10, p_year);
                    pstmt.setString(11, p_subjseq);
                    pstmt.setString(12, p_userid);
                } else if (p_gubun == CalcUtil.ALL) {
                    if (v_contenttype.equals("S") || v_contenttype.equals("O") || v_contenttype.equals("N") || v_contenttype.equals("M")) {
                        sql.setLength(0);
                        // 점수 수정
                        sql.append(" UPDATE /* FinishBean.calc_score 6 */TZ_STUDENT \n");
                        sql.append("    SET SCORE   = ?, \n");
                        sql.append("        TSTEP   = ?, \n");
                        sql.append("        MTEST   = ?, \n");
                        sql.append("        HTEST   = ?, \n");
                        sql.append("        FTEST   = ?, \n");
                        sql.append("        REPORT  = ?, \n");
                        sql.append("        ACT     = ?, \n");
                        sql.append("        ETC1    = ?, \n");
                        sql.append("        ETC2    = ?, \n");
                        sql.append("        AVTSTEP = ?, \n");
                        sql.append("        AVMTEST = ?, \n");
                        sql.append("        AVHTEST = ?, \n");
                        sql.append("        AVFTEST = ?, \n");
                        sql.append("        AVREPORT= ?, \n");
                        sql.append("        AVACT   = ?, \n");
                        sql.append("        AVETC1  = ?, \n");
                        sql.append("        AVETC2  = ?, \n");
                        sql.append("        ISGRADUATED= ?, \n");
                        sql.append("        LUSERID = ?, \n");
                        sql.append("        LDATE   = ? \n");
                        sql.append("  WHERE SUBJ    = ? \n");
                        sql.append("    AND YEAR    = ? \n");
                        sql.append("    AND SUBJSEQ = ? \n");
                        sql.append("    AND USERID  = ? \n");

                        pstmt = connMgr.prepareStatement(sql.toString());
                        pstmt.setDouble(1, data.getScore());
                        pstmt.setDouble(2, data.getTstep());
                        pstmt.setDouble(3, data.getMtest());
                        pstmt.setDouble(4, data.getHtest());
                        pstmt.setDouble(5, data.getFtest());
                        pstmt.setDouble(6, data.getReport());
                        pstmt.setDouble(7, data.getAct());
                        pstmt.setDouble(8, data.getEtc1());
                        pstmt.setDouble(9, data.getEtc2());
                        pstmt.setDouble(10, data.getAvtstep());
                        pstmt.setDouble(11, data.getAvmtest());
                        pstmt.setDouble(12, data.getAvhtest());
                        pstmt.setDouble(13, data.getAvftest());
                        pstmt.setDouble(14, data.getAvreport());
                        pstmt.setDouble(15, data.getAvact());
                        pstmt.setDouble(16, data.getAvetc1());
                        pstmt.setDouble(17, data.getAvetc2());
                        pstmt.setString(18, data.getIsgraduated());
                        pstmt.setString(19, v_luserid);
                        pstmt.setString(20, FormatDate.getDate("yyyyMMddHHmmss"));
                        pstmt.setString(21, p_subj);
                        pstmt.setString(22, p_year);
                        pstmt.setString(23, p_subjseq);
                        pstmt.setString(24, p_userid);
                    } else {
                        sql.setLength(0);

                        sql.append(" UPDATE /* FinishBean.calc_score 7 */TZ_STUDENT \n");
                        sql.append("    SET SCORE   = ?, \n");
                        sql.append("        MTEST   = ?, \n");
                        sql.append("        HTEST   = ?, \n");
                        sql.append("        FTEST   = ?, \n");
                        sql.append("        REPORT  = ?, \n");
                        sql.append("        ACT     = ?, \n");
                        sql.append("        ETC1    = ?, \n");
                        sql.append("        ETC2    = ?, \n");
                        sql.append("        AVTSTEP = ?, \n");
                        sql.append("        AVMTEST = ?, \n");
                        sql.append("        AVHTEST = ?, \n");
                        sql.append("        AVFTEST = ?, \n");
                        sql.append("        AVREPORT= ?, \n");
                        sql.append("        AVACT   = ?, \n");
                        sql.append("        AVETC1  = ?, \n");
                        sql.append("        AVETC2  = ?, \n");
                        sql.append("        ISGRADUATED= ?, \n");
                        sql.append("        LUSERID = ?, \n");
                        sql.append("        LDATE   = ? \n");
                        sql.append("  WHERE SUBJ    = ? \n");
                        sql.append("    AND YEAR    = ? \n");
                        sql.append("    AND SUBJSEQ = ? \n");
                        sql.append("    AND USERID  = ? \n");

                        pstmt = connMgr.prepareStatement(sql.toString());

                        pstmt.setDouble(1, data.getScore());
                        pstmt.setDouble(2, data.getMtest());
                        pstmt.setDouble(3, data.getHtest());
                        pstmt.setDouble(4, data.getFtest());
                        pstmt.setDouble(5, data.getReport());
                        pstmt.setDouble(6, data.getAct());
                        pstmt.setDouble(7, data.getEtc1());
                        pstmt.setDouble(8, data.getEtc2());
                        pstmt.setDouble(9, data.getAvtstep());
                        pstmt.setDouble(10, data.getAvmtest());
                        pstmt.setDouble(11, data.getAvhtest());
                        pstmt.setDouble(12, data.getAvftest());
                        pstmt.setDouble(13, data.getAvreport());
                        pstmt.setDouble(14, data.getAvact());
                        pstmt.setDouble(15, data.getAvetc1());
                        pstmt.setDouble(16, data.getAvetc2());
                        pstmt.setString(17, data.getIsgraduated());
                        pstmt.setString(18, v_luserid);
                        pstmt.setString(19, FormatDate.getDate("yyyyMMddHHmmss"));
                        pstmt.setString(20, p_subj);
                        pstmt.setString(21, p_year);
                        pstmt.setString(22, p_subjseq);
                        pstmt.setString(23, p_userid);
                    }
                } else if (p_gubun == CalcUtil.EXC) {//과정질문은 등록하여도 참여도에 쌓이지 않게 한다. ㄴDATE값 만 갱신
                    sql.setLength(0);
                    sql.append(" UPDATE /* FinishBean.calc_score exc */TZ_STUDENT \n");
                    sql.append("    SET LDATE   = ? \n");
                    sql.append("  WHERE SUBJ    = ? \n");
                    sql.append("    AND YEAR    = ? \n");
                    sql.append("    AND SUBJSEQ = ? \n");
                    sql.append("    AND USERID  = ? \n");

                    pstmt = connMgr.prepareStatement(sql.toString());
                    pstmt.setString(1, FormatDate.getDate("yyyyMMddHHmmss"));
                    pstmt.setString(2, p_subj);
                    pstmt.setString(3, p_year);
                    pstmt.setString(4, p_subjseq);
                    pstmt.setString(5, p_userid);
                }

                isOk = pstmt.executeUpdate();
            }

        } catch (Exception ex) {
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 종합점수계산
     * 
     * @param box receive from the form object and session 8
     * @return int
     */
    public int calc_step(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, StoldData data) throws Exception {
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        String v_contenttype = "";
        String sql_datecnt = "";
        String sql_edudatecnt = "";
        int v_datecnt = 0;
        int v_edudatecnt = 0;

        try {
            sql = " select * from ( select rownum rnum,  NVL(contenttype,'N') contenttype ";
            sql += "   from tz_subj  ";
            sql += "  where subj   = " + SQLString.Format(p_subj);
            sql += "    ) where rnum < 2 ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_contenttype = ls.getString("contenttype");
            }

            if (v_contenttype.equals("O")) { // OBC와 SCORM인 경우
                sql_datecnt = " select count(*) datecnt ";
                sql_datecnt += "   from tz_subjobj ";
                sql_datecnt += "  where subj = " + SQLString.Format(p_subj);
                sql_datecnt += "    and type = 'SC' ";

                sql_edudatecnt = " select count(*) edudatecnt ";
                sql_edudatecnt += "   from tz_progress a, tz_subjobj b";
                sql_edudatecnt += "  where a.subj    = " + SQLString.Format(p_subj);
                sql_edudatecnt += "    and a.year    = " + SQLString.Format(p_year);
                sql_edudatecnt += "    and a.subjseq = " + SQLString.Format(p_subjseq);
                sql_edudatecnt += "    and a.subj=b.subj and a.lesson=b.lesson and a.oid=b.oid ";
                sql_edudatecnt += "    and a.userid  = " + SQLString.Format(p_userid);
                sql_edudatecnt += "    and a.lesson  <> '000'";
                // first_end 데이터가 null 일경우 불러오지 못함 ( 세션 문제로 인하여 저장 안되는 경우가 발생. 임시 해제함)
                //sql_edudatecnt+= "    and a.FIRST_END  is not null and NVL(lesson_count,0) > 0 and a.oid like '10%'" ;
                sql_edudatecnt += "    and NVL(lesson_count,0) >= 0 and a.oid like '10%'";
            } else if (v_contenttype.equals("S")) {
                sql_datecnt = " select count(*) datecnt ";
                sql_datecnt += "  from tz_subjobj a, tz_object b  ";
                sql_datecnt += "  where a.oid = b.oid and rtrim(ltrim(b.starting)) is not null and  a.subj = " + SQLString.Format(p_subj);
                sql_datecnt += "    and a.type = 'SC' ";

                sql_edudatecnt = " select count(*) edudatecnt ";
                sql_edudatecnt += "  from tz_progress a, tz_subjobj b , tz_object c ";
                sql_edudatecnt += "  where a.subj    = " + SQLString.Format(p_subj);
                sql_edudatecnt += "    and a.year    = " + SQLString.Format(p_year);
                sql_edudatecnt += "    and a.subjseq = " + SQLString.Format(p_subjseq);
                sql_edudatecnt += "    and a.subj=b.subj and a.lesson=b.lesson and a.oid=b.oid ";
                sql_edudatecnt += "    and a.userid  = " + SQLString.Format(p_userid);
                sql_edudatecnt += "    and a.lesson  <> '000'";
                sql_edudatecnt += "    and  b.oid = c.oid and rtrim(ltrim(c.starting)) is not null ";
                sql_edudatecnt += "    and a.FIRST_END  is not null and NVL(lesson_count,0) > 0 and a.oid like 'S0%'";
            } else {

                sql_datecnt = " select count(*) datecnt ";
                sql_datecnt += "   from tz_subjlesson ";
                sql_datecnt += "  where subj = " + SQLString.Format(p_subj);

                sql_edudatecnt = " select count(distinct lesson) edudatecnt ";
                sql_edudatecnt += "   from tz_progress ";
                sql_edudatecnt += "  where subj    = " + SQLString.Format(p_subj);
                sql_edudatecnt += "    and year    = " + SQLString.Format(p_year);
                sql_edudatecnt += "    and subjseq = " + SQLString.Format(p_subjseq);
                sql_edudatecnt += "    and userid  = " + SQLString.Format(p_userid);
                sql_edudatecnt += "    and lesson  <> '000'";
                sql_edudatecnt += "    and first_end is not null";
            }

            ls.close();
            ls = connMgr.executeQuery(sql_datecnt);
            while (ls.next()) {
                v_datecnt = ls.getInt("datecnt");
            }

            ls.close();
            ls = connMgr.executeQuery(sql_edudatecnt);
            while (ls.next()) {
                v_edudatecnt = ls.getInt("edudatecnt");
            }

            if (v_edudatecnt == 0) {
                data.setTstep(0);
            } else {
                data.setTstep((double) Math.round((double) v_edudatecnt / v_datecnt * 100 * 100) / 100);
            }

            if (data.getTstep() > 100) {
                data.setTstep(100);
            }

            if (data.getWstep() == 0) {
                data.setAvtstep(0);
            } else {
                data.setAvtstep((double) Math.round(data.getTstep() * data.getWstep()) / 100);
            }

            if (data.getAvtstep() > data.getWstep()) {
                data.setAvtstep(data.getWstep());
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 종합점수계산_yeslearn
     * 
     * @param box receive from the form object and session 8
     * @return int
     */
    public int calc_step_yeslearn(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, StoldData data) throws Exception {
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        double v_tstep = 0.0;
        double v_wstep = 0.0;

        try {
            sql = " select a.tstep, ";
            sql += " 				b.wstep ";
            sql += "   from TZ_STUDENT a,";
            sql += "        TZ_SUBJSEQ b";
            sql += "  where a.subj = b.subj";
            sql += "    and a.year = b.year";
            sql += "    and a.subjseq = b.subjseq";
            sql += "    and a.subj=" + SQLString.Format(p_subj);
            sql += "    and a.year=" + SQLString.Format(p_year);
            sql += "    and a.subjseq=" + SQLString.Format(p_subjseq);
            sql += "    and a.userid=" + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_tstep = ls.getDouble("tstep");
                v_wstep = ls.getDouble("wstep");
            }
            ls.close();

            if (v_wstep == 0) {
                data.setAvtstep(0);
            } else {
                data.setAvtstep((double) Math.round(v_tstep * v_wstep) / 100);
            }

            if (data.getAvtstep() > v_wstep) {
                data.setAvtstep(v_wstep);
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 점수계산 (평가)
     * 
     * @param box receive from the form object
     * @return int
     */
    public int calc_exam(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, StoldData data) throws Exception {
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        int v_ex1cnt = 0;
        int v_ex2cnt = 0;

        double v_ex1result_grade = 0;
        double v_ex2result_grade = 0;
        double v_ex3result_grade = 0;
        //System.out.println("yyyyy");
        try {
            /*---------------------- Middle exam ---------------------*/
            sql = "select count(*)  ex1cnt ";
            sql += "  from tz_exammaster  ";
            sql += " where subj   = " + SQLString.Format(p_subj);
            //sql+= "   and ptype  = 'M'  ";
            sql += "   and examtype = 'M' ";
            sql += "   and lesson != 'OT' ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ex1cnt = ls.getInt("ex1cnt");
            }

            sql = " select sum(score) ex1result_grade ";
            sql += "   from tz_examresult ";
            sql += "  where subj    = " + SQLString.Format(p_subj);
            sql += "    and year    = " + SQLString.Format(p_year);
            sql += "    and subjseq = " + SQLString.Format(p_subjseq);
            sql += "    and userid  = " + SQLString.Format(p_userid);
            //sql+= "    and ptype   = 'M'  ";
            sql += "    and examtype= 'M'  ";
            sql += "    and lesson != 'OT' ";
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ex1result_grade = ls.getDouble("ex1result_grade");
            }

            if (v_ex1cnt == 0) {
                data.setMtest(0);
            } else {
                data.setMtest((double) Math.round(v_ex1result_grade / v_ex1cnt * 100) / 100);
            }
            data.setAvmtest((double) Math.round(data.getMtest() * data.getWmtest()) / 100);
            //System.out.println("xxxxxx="+data.getAvmtest());

            /*---------------------- 형성평가 ---------------------*/
            sql = "select count(*)  ex2cnt ";
            sql += "  from tz_exammaster  ";
            sql += " where subj   = " + SQLString.Format(p_subj);
            sql += "   and examtype = 'H' ";
            sql += "   and lesson != 'OT' ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ex2cnt = ls.getInt("ex2cnt");
            }

            sql = " select sum(score) ex2result_grade ";
            sql += "   from tz_examresult ";
            sql += "  where subj    = " + SQLString.Format(p_subj);
            sql += "    and year    = " + SQLString.Format(p_year);
            sql += "    and subjseq = " + SQLString.Format(p_subjseq);
            sql += "    and userid  = " + SQLString.Format(p_userid);
            //sql+= "    and ptype   = 'M'  ";
            sql += "    and examtype= 'H'  ";
            sql += "    and lesson != 'OT' ";
            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_ex2result_grade = ls.getDouble("ex2result_grade");
            }
            if (v_ex2cnt == 0) {
                data.setHtest(0);
            } else {
                data.setHtest((double) Math.round(v_ex2result_grade / v_ex2cnt * 100) / 100);
            }
            data.setAvhtest((double) Math.round(data.getHtest() * data.getWhtest()) / 100);
            /*----------------------  Total exam  ---------------------*/
            String v_lastdate = "";
            sql = "select lesson  lastdate ";
            sql += "  from tz_exammaster  ";
            sql += " where subj   = " + SQLString.Format(p_subj);
            //sql+= "   and ptype  = 'T'  ";
            sql += "   and examtype  = 'E'  ";
            sql += "   and lesson != 'OT' ";
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_lastdate = ls.getString("lastdate");
            }
            if (v_lastdate.equals("")) {
                data.setFtest(0);
                data.setAvftest(0);
            } else {
                sql = "select score  ex3result_grade ";
                sql += "  from tz_examresult  ";
                sql += " where subj    = " + SQLString.Format(p_subj);
                sql += "   and year    = " + SQLString.Format(p_year);
                sql += "   and subjseq = " + SQLString.Format(p_subjseq);
                sql += "   and userid  = " + SQLString.Format(p_userid);
                //sql+= "   and ptype   = 'T'  ";
                sql += "   and examtype   = 'E'  ";
                ls.close();
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    v_ex3result_grade = ls.getDouble("ex3result_grade");
                }
                data.setFtest((v_ex3result_grade * 100) / 100);
                data.setAvftest((double) Math.round(data.getFtest() * data.getWftest()) / 100);
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 리포트 점수 계산
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int calc_report(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, StoldData data) throws Exception {
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql = "";
        int isOk = 0;

        int v_repcnt = 0;
        String v_projgrp = "";
        int v_score = 0;
        int v_score_sum = 0;

        try {
            /*-------- Calc  Grade of Report ------------------------------------------------------*/
            //sql = " select count(*) repcnt";
            sql = " select count(distinct projseq) repcnt ";
            //sql+= "   from tz_projord ";
            //sql+= "  where subj    = " + SQLString.Format(p_subj);
            //sql+= "    and year    = " + SQLString.Format(p_year);
            //sql+= "    and subjseq = " + SQLString.Format(p_subjseq);

            sql = " select count(*) repcnt ";
            sql += "   from tz_projassign ";
            sql += "  where subj    = " + SQLString.Format(p_subj);
            sql += "    and year    = " + SQLString.Format(p_year);
            sql += "    and subjseq = " + SQLString.Format(p_subjseq);
            sql += "    and userid = " + SQLString.Format(p_userid);

            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                v_repcnt = ls1.getInt("repcnt");
            }

            if (v_repcnt == 0) {
                data.setReport(0);
                data.setAvreport(0);
            } else {
                sql = " select ordseq, reptype ";
                sql += "   from tz_projord ";
                sql += "  where subj    = " + SQLString.Format(p_subj);
                sql += "    and year    = " + SQLString.Format(p_year);
                sql += "    and subjseq = " + SQLString.Format(p_subjseq);
                sql += "  order by ordseq ";

                ls1.close();
                ls1 = connMgr.executeQuery(sql);
                while (ls1.next()) {
                    v_projgrp = p_userid;
                    if (!ls1.getString("reptype").equals("R")) {
                        sql = " select * from ( select rownum rnum,  projid projgrp ";
                        sql += "   from tz_projgrp ";
                        sql += "  where subj    = " + SQLString.Format(p_subj);
                        sql += "    and year    = " + SQLString.Format(p_year);
                        sql += "    and subjseq = " + SQLString.Format(p_subjseq);
                        sql += "    and ordseq  = " + SQLString.Format(ls1.getInt("ordseq"));
                        sql += "    and userid  = " + SQLString.Format(p_userid);
                        sql += "    ) where rnum < 2 ";

                        ls2.close();
                        ls2 = connMgr.executeQuery(sql);
                        while (ls2.next()) {
                            v_projgrp = ls2.getString("projgrp");
                        }
                    }

                    sql = " select NVL(max(score),0) score ";
                    sql += "   from tz_projrep ";
                    sql += "  where subj    = " + SQLString.Format(p_subj);
                    sql += "    and year    = " + SQLString.Format(p_year);
                    sql += "    and subjseq = " + SQLString.Format(p_subjseq);
                    sql += "    and ordseq  = " + SQLString.Format(ls1.getInt("ordseq"));
                    sql += "    and projid = " + SQLString.Format(v_projgrp);

                    if (ls2 != null) {
                        ls2.close();
                    }
                    ls2 = connMgr.executeQuery(sql);
                    v_score = 0;
                    while (ls2.next()) {
                        v_score = ls2.getInt("score");
                    }
                    v_score_sum += v_score;
                }
                //data.setReport((double)Math.round((double)v_score_sum/v_repcnt*100)/100);  // 리포트갯수를 나누어서 나온 점수를 합산한다.
                data.setReport((double) Math.round((double) v_score_sum * 100) / 100); // 리포트점수 그대로 합산한다.(2005.07.13) -> 평가할때 리포트갯수대로 나눈 만점점수대로 평가를 했기 때문이다.
                data.setAvreport((double) Math.round(data.getReport() * data.getWreport()) / 100);
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
        }
        return isOk;
    }

    /**
     * 점수계산 (activity)
     * 
     * @param box receive from the form object
     * @return int
     */
    public int calc_activity(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, StoldData data) throws Exception {
        ListSet ls = null;
        String sql = "";
        int isOk = 0;
        int v_actcnt = 0;

        try {
            /*-------- Calc  Grade of Activity -----------------------------------------------------*/
            /*
             * 3) Activity점수 = Activity평균*Activity가중치 = [ SUM(Act점수/만점점수) /
             * count(Act) ] * 100 * Act가중치
             */
            ////sql = "select count(*) actcnt ";
            ////sql+= "  from tz_activity  ";
            ////sql+= " where subj = " + SQLString.Format(p_subj);
            ////
            ////ls = connMgr.executeQuery(sql);
            ////while (ls.next()) {
            ////    v_actcnt = ls.getInt("actcnt");
            ////}

            if (v_actcnt == 0) {
                data.setAct(0);
                data.setAvact(0);
            } else {
                sql = " select (NVL(sum( NVL(a.point,0) ),0) / " + SQLString.Format(v_actcnt) + " )  act";
                sql += "        (NVL(sum( NVL(a.point,0)/b.point ),0) / " + SQLString.Format(v_actcnt) + " )* " + SQLString.Format(data.getWact()) + " avact ";
                sql += "   from tz_activity_ans a,  ";
                sql += "        tz_activity     b   ";
                sql += "  where a.subj   = b.subj   ";
                //2005.11.16_하경태 : Oracle -> Mssql
                //sql+= "    and a.lesson = b.lesson(+) ";
                //sql+= "    and a.seq    = b.seq(+) ";
                sql += "    and a.lesson  =  b.lesson(+) ";
                sql += "    and a.seq     =  b.seq(+) ";

                sql += "    and a.subj   = " + SQLString.Format(p_subj);
                sql += "    and a.year   = " + SQLString.Format(p_year);
                sql += "    and a.subjseq= " + SQLString.Format(p_subjseq);
                sql += "    and a.userid = " + SQLString.Format(p_userid);

                //                ls.close();
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    data.setAct(ls.getDouble("act"));
                    data.setAvact(ls.getDouble("avact"));
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 점수계산 (기타)
     * 
     * @param box receive from the form object
     * @return int
     */
    public int calc_etc(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, StoldData data) throws Exception {
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        double v_etc1 = 0;
        double v_etc2 = 0;

        try {
            /*-------- Calc  Grade of ETC : 참여도 점수 반영-----------------------------------------------------*/
            sql = "select etc1, etc2 ";
            sql += "  from tz_student  ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            sql += "   and userid  = " + SQLString.Format(p_userid);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_etc1 = ls.getDouble("etc1");
                v_etc2 = ls.getDouble("etc2");
            }

            data.setEtc1(v_etc1);
            data.setEtc2(v_etc2);

            data.setAvetc1((double) Math.round(v_etc1 * data.getWetc1()) / 100);
            data.setAvetc2((double) Math.round(v_etc2 * data.getWetc2()) / 100);
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 수료여부 계산
     * 
     * @param box receive from the form object
     * @return int
     */
    public int calc_isgraduated(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, StoldData data) throws Exception {
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql = "";
        int isOk = 0;

        SubjseqData subjseqdata = null;

        String v_notgraducd = "";

        //        int v_cnt1 = 0;
        //        int v_cnt2 = 0;

        //	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        /*-------- Calc  Final Grade  ----------------------------------------------------*/
        data.setScore(data.getAvtstep() + data.getAvmtest() + data.getAvftest() + data.getAvhtest() + data.getAvreport() +

        data.getAvact() + data.getAvetc1() + data.getAvetc2());

        if (data.getScore() > 100) {
            data.setScore(100);
        }

        try {
            // 과정차수 정보
            subjseqdata = getSubjseqInfo(connMgr, p_subj, p_year, p_subjseq);

            //미수료사유 코드
            //총점 체크
            //        System.out.println("총점 기준점수"+subjseqdata.getGradscore());
            if (data.getScore() < subjseqdata.getGradscore()) {
                v_notgraducd += "06,"; // 06 = 성적미달   - 총점점수 체크
            }

            //진도율 체크
            // System.out.println("진도 기준점수"+subjseqdata.getGradstep());
            if (data.getTstep() < subjseqdata.getGradstep()) {
                v_notgraducd += "01,"; //01 = 진도미달 - 자동계산(진도율 체크)
            }

            //평가

            //if ((data.getWmtest()+data.getWftest()+data.getWhtest()) < subjseqdata.getGradexam()) {
            if (data.getMtest() < subjseqdata.getGradexam()) {
                v_notgraducd += "07,"; //07 = 중간 평가점수미달
            }

            if (data.getHtest() < subjseqdata.getGradhtest()) {
                v_notgraducd += "07,"; //07 = 형성 평가점수미달
            }

            if (data.getFtest() < subjseqdata.getGradftest()) {
                v_notgraducd += "07,"; //07 = 최종 평가점수미달
            }

            //리포트
            //if (data.getWreport() < subjseqdata.getGradreport()) {
            if (data.getReport() < subjseqdata.getGradreport()) {
                v_notgraducd += "08,"; //08 = 과제점수미달
            }

            if (data.getEtc1() < subjseqdata.getGradetc1()) {
                v_notgraducd += "09,"; //09 = 참여도 미달
            }

            if (!v_notgraducd.equals("")) {
                v_notgraducd = v_notgraducd.substring(0, v_notgraducd.length() - 1);
                //	System.out.println("v_notgraducd="+v_notgraducd);
            }
            data.setNotgraducd(v_notgraducd);
            
            //data.getMtest() --  subjseqdata.getGradexam() 중간
            //data.getHtest() --   subjseqdata.getGradhtest() 형성
            //data.getFtest() --   subjseqdata.getGradftest() 최종

            //if (data.getScore() >= subjseqdata.getGradscore() && data.getTstep() >= subjseqdata.getGradstep() && ((data.getMtest()+data.getFtest()+data.getHtest()) >= subjseqdata.getGradexam())  && data.getReport() >= subjseqdata.getGradreport()) {

            if (data.getScore() >= subjseqdata.getGradscore() && data.getTstep() >= subjseqdata.getGradstep() && data.getMtest() >= subjseqdata.getGradexam() && data.getHtest() >= subjseqdata.getGradhtest() && data.getFtest() >= subjseqdata.getGradftest()
                    && data.getReport() >= subjseqdata.getGradreport() && data.getEtc1() >= subjseqdata.getGradetc1()) {
                //System.out.println("전체조건 Y");
            	
                data.setIsgraduated("Y");

                sql = "";
                sql += "UPDATE TZ_STUDENT";
                sql += "\n SET ISGRADUATED = 'Y'";
                sql += "\n WHERE SUBJ = " + SQLString.Format(p_subj);
                sql += "\n	AND SUBJSEQ = " + SQLString.Format(p_subjseq);
                sql += "\n	AND YEAR = " + SQLString.Format(p_year);
                sql += "\n	AND USERID = " + SQLString.Format(p_userid);

                ls1 = connMgr.executeQuery(sql);

                /*
                 * if (chk_goyong(connMgr, p_subj, p_year,
                 * p_subjseq).equals("Y") &&
                 * subjseqdata.getIsonoff().equals("ON")) { if ((
                 * data.getWmtest() > 0 && data.getAvmtest() == 0 ) || (
                 * data.getWftest() > 0 && data.getAvftest() == 0 ) || (
                 * data.getWhtest() > 0 && data.getAvhtest() == 0 ) || //(
                 * data.getWftest() > 0 && data.getFtest() < 30 ) || (
                 * data.getWreport() > 0 && data.getAvreport() == 0 ) || (
                 * data.getWact() > 0 && data.getAvact() == 0 ) || (
                 * data.getWetc1() > 0 && data.getAvetc1() == 0 ) || (
                 * data.getWetc2() > 0 && data.getAvetc2() == 0 ) ) {
                 * 
                 * 
                 * System.out.println("전체 조건에서 N"); data.setIsgraduated("N"); }
                 * else { // 평가 갯수 확인 if (data.getWmtest() > 0) { sql =
                 * "select count(*) cnt1 "; sql+= "  from tz_exampaper "; sql+=
                 * " where subj  = " + SQLString.Format(p_subj); sql+=
                 * "   and ptype = 'M' "; ls1 = connMgr.executeQuery(sql); while
                 * (ls1.next()) { v_cnt1 = ls1.getInt("cnt1"); }
                 * System.out.println("평가갯수확인"+sql); sql =
                 * "select count(*) cnt2 "; sql+= "  from tz_examresult "; sql+=
                 * " where subj    = " + SQLString.Format(p_subj); sql+=
                 * "   and year    = " + SQLString.Format(p_year); sql+=
                 * "   and subjseq = " + SQLString.Format(p_subjseq); sql+=
                 * "   and userid  = " + SQLString.Format(p_userid); sql+=
                 * "   and ptype   ='M' "; sql+= "   and score  >= 30 "; ls2 =
                 * connMgr.executeQuery(sql); while (ls2.next()) { v_cnt2 =
                 * ls2.getInt("cnt2"); } } else { v_cnt1 = 0; v_cnt2 = 0; }
                 * 
                 * if (v_cnt1 != v_cnt2) { System.out.println("평가에서 N");
                 * data.setIsgraduated("N"); } else {
                 * System.out.println("평가에서 Y");
                 *///       data.setIsgraduated("Y");
                /*
                 * } } } else { System.out.println("고용보험 Y");
                 * data.setIsgraduated("Y"); }
                 */
            } else {
                //System.out.println("전체조건 N");
                data.setIsgraduated("N");

                sql = "";
                sql += "UPDATE TZ_STUDENT";
                sql += "\n SET ISGRADUATED = 'N'";
                sql += "\n WHERE SUBJ = " + SQLString.Format(p_subj);
                sql += "\n	AND SUBJSEQ = " + SQLString.Format(p_subjseq);
                sql += "\n	AND YEAR = " + SQLString.Format(p_year);
                sql += "\n	AND USERID = " + SQLString.Format(p_userid);

                ls1 = connMgr.executeQuery(sql);
            }

            if ((data.getIsgraduated() == null) || (data.getIsgraduated().equals(""))) {
                //System.out.println("기타 N");
                data.setIsgraduated("N");

                sql = "";
                sql += "UPDATE TZ_STUDENT";
                sql += "\nSET ISGRADUATED = 'N'";
                sql += "\nWHERE SUBJ = " + SQLString.Format(p_subj);
                sql += "\n	AND SUBJSEQ = " + SQLString.Format(p_subjseq);
                sql += "\n	AND YEAR = " + SQLString.Format(p_year);
                sql += "\n	AND USERID = " + SQLString.Format(p_userid);

                ls2 = connMgr.executeQuery(sql);

            }

        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
        }

        return isOk;
    }

    /**
     * 고용여부 체크
     * 
     * @param box receive from the form object
     * @return int
     */
    public String chk_goyong(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        SubjseqData subjseqdata = null;
        try {
            subjseqdata = getSubjseqInfo(connMgr, p_subj, p_year, p_subjseq);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return subjseqdata.getIsgoyong();
    }

    /**
     * 코스과정 수료처리
     * 
     * @param box receive from the form object
     * @return int
     */
    // isOk = 1 : "에러가 없는 경우"
    // isOk = 2 : "이미 수료처리 되었습니다."
    // isOk = 3 : 기타 에러
    // isOk = 4 : calc_score() 계산에서 에러
    @SuppressWarnings("unchecked")
    public int CourseScoreCompute(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt_select = null;
        PreparedStatement pstmt_update = null;
        PreparedStatement pstmt_insert = null;
        PreparedStatement pstmt_delete = null;

        int isOk = 1;
        String v_course = box.getString("p_course");
        String v_cyear = box.getString("p_cyear");
        String v_courseseq = box.getString("p_courseseq");
        String v_luserid = box.getSession("userid");

        Hashtable courseseqdata = null;
        //        int v_gradscore   = 0;
        int v_gradfailcnt = 0;
        int v_subjcnt = 1;

        ArrayList list = null; // 수료대상학생 정보 커서
        CourseScoreData coursedata = null;
        SubjScoreData subjdata = null;

        double v_score = 0;
        int v_graduatedcnt = 0;

        boolean v_isexception = false;
        String v_return_msg = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            courseseqdata = getCourseseqInfo(connMgr, v_course, v_cyear, v_courseseq);
            //            v_gradscore   = Integer.valueOf((String)courseseqdata.get("gradscore")).intValue();
            v_gradfailcnt = Integer.valueOf((String) courseseqdata.get("gradfailcnt")).intValue();
            v_subjcnt = Integer.valueOf((String) courseseqdata.get("subjcnt")).intValue();

            // 학습자 커서
            list = getCourseFinishTargetStudent(connMgr, v_course, v_cyear, v_courseseq, "ALL");
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    // pstmt 만든다.
                    pstmt_select = getPreparedStatement(connMgr, "COURSE_STOLD", "select");
                    pstmt_update = getPreparedStatement(connMgr, "COURSE_STOLD", "update");
                    pstmt_insert = getPreparedStatement(connMgr, "COURSE_STOLD", "insert");
                    pstmt_delete = getPreparedStatement(connMgr, "COURSE_STOLD", "delete");
                }

                coursedata = (CourseScoreData) list.get(i);
                v_score = 0;
                v_graduatedcnt = 0;
                for (int k = 0; k < coursedata.size(); k++) {
                    subjdata = coursedata.get(k);
                    if (subjdata.getIsgraduated().equals("Y")) {
                        v_graduatedcnt++;
                    }
                    v_score += subjdata.getScore();
                }
                coursedata.setScore(v_score / v_subjcnt);
                coursedata.setGraduatedcnt(v_graduatedcnt);

                if (v_subjcnt - v_graduatedcnt > v_gradfailcnt) {
                    coursedata.setIsgraduated("N");
                } else {
                    coursedata.setIsgraduated("Y");
                }

                // 점수 재계산
                try {
                    v_return_msg = "";
                    calc_course_score(pstmt_select, pstmt_update, pstmt_insert, pstmt_delete, coursedata, v_luserid, "process");
                } catch (Exception ex) {
                    v_isexception = true;
                    v_return_msg = "코스수료처리==> 점수 재산정 중 문제발생함[" + coursedata.getUserid() + "]";
                    v_return_msg += ex.getMessage();
                    isOk = 4;
                    return isOk;
                }
            }
        } catch (Exception ex) {
            v_isexception = true;
            isOk = 3;
            v_return_msg = ex.getMessage();
            throw new Exception(ex.getMessage());
        } finally {
            if (!v_return_msg.equals(""))
                box.put("p_return_msg", v_return_msg);

            if (pstmt_select != null) {
                try {
                    pstmt_select.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_update != null) {
                try {
                    pstmt_update.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_insert != null) {
                try {
                    pstmt_insert.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_delete != null) {
                try {
                    pstmt_delete.close();
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 코스과정 수료 취소
     * 
     * @param box receive from the form object
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int CourseScoreCancel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt_select = null;
        PreparedStatement pstmt_update = null;
        PreparedStatement pstmt_insert = null;
        PreparedStatement pstmt_delete = null;

        int isOk = 1;
        String v_course = box.getString("p_course");
        String v_cyear = box.getString("p_cyear");
        String v_courseseq = box.getString("p_courseseq");
        String v_luserid = box.getSession("userid");

        Hashtable courseseqdata = null;
        //        int v_gradscore   = 0;
        int v_gradfailcnt = 0;
        int v_subjcnt = 1;

        ArrayList list = null; // 수료대상학생 정보 커서
        CourseScoreData coursedata = null;
        SubjScoreData subjdata = null;

        double v_score = 0;
        int v_graduatedcnt = 0;

        boolean v_isexception = false;
        String v_return_msg = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            courseseqdata = getCourseseqInfo(connMgr, v_course, v_cyear, v_courseseq);
            //            v_gradscore   = Integer.valueOf((String)courseseqdata.get("gradscore")).intValue();
            v_gradfailcnt = Integer.valueOf((String) courseseqdata.get("gradfailcnt")).intValue();
            v_subjcnt = Integer.valueOf((String) courseseqdata.get("subjcnt")).intValue();

            // 학습자 커서
            list = getCourseFinishTargetStudent(connMgr, v_course, v_cyear, v_courseseq, "ALL");

            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    // pstmt 만든다.
                    pstmt_select = getPreparedStatement(connMgr, "COURSE_STOLD", "select");
                    pstmt_update = getPreparedStatement(connMgr, "COURSE_STOLD", "update");
                    pstmt_insert = getPreparedStatement(connMgr, "COURSE_STOLD", "insert");
                    pstmt_delete = getPreparedStatement(connMgr, "COURSE_STOLD", "delete");
                }

                coursedata = (CourseScoreData) list.get(i);
                v_score = 0;
                v_graduatedcnt = 0;

                for (int k = 0; k < coursedata.size(); k++) {
                    subjdata = coursedata.get(k);

                    if (subjdata.getIsgraduated().equals("Y")) {
                        v_graduatedcnt++;
                    }
                    v_score += subjdata.getScore();
                }
                coursedata.setScore(v_score / v_subjcnt);
                coursedata.setGraduatedcnt(v_graduatedcnt);

                if (v_subjcnt - v_graduatedcnt > v_gradfailcnt) {
                    coursedata.setIsgraduated("N");
                } else {
                    coursedata.setIsgraduated("Y");
                }

                // 점수 재계산
                try {
                    v_return_msg = "";
                    calc_course_score(pstmt_select, pstmt_update, pstmt_insert, pstmt_delete, coursedata, v_luserid, "delete");
                } catch (Exception ex) {
                    v_isexception = true;
                    v_return_msg = "코스수료 취소 처리==> 점수 재산정 중 문제발생함[" + coursedata.getUserid() + "]";
                    v_return_msg += ex.getMessage();
                    isOk = 4;
                    return isOk;
                }
            }
        } catch (Exception ex) {
            v_isexception = true;
            isOk = 3;
            v_return_msg = ex.getMessage();
            throw new Exception(ex.getMessage());
        } finally {
            if (!v_return_msg.equals(""))
                box.put("p_return_msg", v_return_msg);

            if (pstmt_select != null) {
                try {
                    pstmt_select.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_update != null) {
                try {
                    pstmt_update.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_insert != null) {
                try {
                    pstmt_insert.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_delete != null) {
                try {
                    pstmt_delete.close();
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    @SuppressWarnings("unchecked")
    public Hashtable getCourseseqInfo(DBConnectionManager connMgr, String p_course, String p_cyear, String p_courseseq) throws Exception {
        ListSet ls = null;
        String sql = "";
        Hashtable outputdata = new Hashtable();

        try {
            // 과정차수 정보
            sql = " select gradscore, gradfailcnt ";
            sql += "   from tz_courseseq ";
            sql += "  where course    = " + SQLString.Format(p_course);
            sql += "    and cyear     = " + SQLString.Format(p_cyear);
            sql += "    and courseseq = " + SQLString.Format(p_courseseq);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                outputdata.put("gradscore", String.valueOf(ls.getInt("gradscore")));
                outputdata.put("gradfailcnt", String.valueOf(ls.getInt("gradfailcnt")));
            }

            sql = " select count(*) subjcnt ";
            sql += "   from tz_coursesubj ";
            sql += "  where course    = " + SQLString.Format(p_course);

            ls.close();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                outputdata.put("subjcnt", String.valueOf(ls.getInt("subjcnt")));
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return outputdata;
    }

    /**
     * 학습자 커서 (CourseScoreCompute() 에서 쓰임.)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList getCourseFinishTargetStudent(DBConnectionManager connMgr, String p_course, String p_cyear, String p_courseseq, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        ArrayList list = new ArrayList();
        CourseScoreData coursedata = null;
        SubjScoreData subjdata = null;
        String v_user_bef = "";

        // 학습자 커서
        sql = " select a.course, a.cyear, a.courseseq, b.subj, b.year, b.subjseq, ";
        sql += "        b.userid, b.score, b.isgraduated ";
        sql += "   from tz_subjseq  a, ";
        sql += "        tz_stold    b  ";
        sql += "  where a.subj      = b.subj  ";
        sql += "    and a.year      = b.year  ";
        sql += "    and a.subjseq   = b.subjseq  ";
        sql += "    and a.course    = " + SQLString.Format(p_course);
        sql += "    and a.cyear     = " + SQLString.Format(p_cyear);
        sql += "    and a.courseseq = " + SQLString.Format(p_courseseq);
        if (!p_userid.equals("ALL")) {
            sql += "    and b.userid = " + SQLString.Format(p_userid);
        }
        sql += "  order by b.userid, b.subj, b.year, b.subjseq ";

        try {
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if (!v_user_bef.equals(ls.getString("userid"))) {
                    coursedata = new CourseScoreData();
                    coursedata.setCourse(ls.getString("course"));
                    coursedata.setCyear(ls.getString("cyear"));
                    coursedata.setCourseseq(ls.getString("courseseq"));
                    coursedata.setUserid(ls.getString("userid"));
                }
                subjdata = new SubjScoreData();
                subjdata.setSubj(ls.getString("subj"));
                subjdata.setYear(ls.getString("year"));
                subjdata.setSubjseq(ls.getString("subjseq"));
                subjdata.setUserid(coursedata.getUserid());
                subjdata.setScore(ls.getDouble("score"));
                subjdata.setIsgraduated(ls.getString("isgraduated"));

                coursedata.add(subjdata);

                if (!v_user_bef.equals(coursedata.getUserid())) {
                    list.add(coursedata);
                    v_user_bef = coursedata.getUserid();
                }
            }
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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

    /**
     * sql문 작성
     * 
     * @param p_gubun
     * @param p_command
     * @return PreparedStatement
     */
    public PreparedStatement getPreparedStatement(DBConnectionManager connMgr, String p_gubun, String p_command) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";

        if (p_gubun.equals("COURSE_STOLD")) {
            if (p_command.equals("select")) {
                sql = " select count(*) cnt ";
                sql += "   from tz_coursestold ";
                sql += "  where course    = ? ";
                sql += "    and cyear     = ? ";
                sql += "    and courseseq = ? ";
                sql += "    and userid    = ? ";
            } else if (p_command.equals("update")) {
                sql = " update tz_coursestold ";
                sql += "    set gradudatedcnt = ?, ";
                sql += "        score         = ?, ";
                sql += "        isgraduated   = ?, ";
                sql += "        luserid       = ?, ";
                sql += "        ldate         = ?  ";
                sql += "  where course        = ?  ";
                sql += "    and cyear         = ?  ";
                sql += "    and courseseq     = ?  ";
                sql += "    and userid        = ?  ";
            } else if (p_command.equals("insert")) {
                sql = " insert into tz_coursestold ";
                sql += "  (course,        cyear, courseseq,   userid, ";
                sql += "   gradudatedcnt, score, isgraduated ,indate, ";
                sql += "   luserid, ldate) ";
                sql += "  values  ";
                sql += "  (?,             ?,     ?,           ?, ";
                sql += "   ?,             ?,     ? ,          ?, ";
                sql += "   ?,             ?) ";
            } else if (p_command.equals("delete")) {
                sql = " Delete From tz_coursestold ";
                sql += " Where course = ? and cyear = ? and courseseq = ? and userid = ? ";
            }
        } else if (p_gubun.equals("SUBJECT_STOLD")) {
            if (p_command.equals("select")) {
                sql = " select count(*) cnt ";
                sql += "   from tz_stold ";
                sql += "  where subj      = ? ";
                sql += "    and year      = ? ";
                sql += "    and subjseq   = ? ";
                sql += "    and userid    = ? ";
            } else if (p_command.equals("update")) {
                sql = " update tz_stold ";
                sql += "    set name     = ?, ";
                sql += "        comp     = ?, ";
                sql += "        dept     = ?, ";
                sql += "        deptname = ?, ";
                sql += "        jik      = ?, ";
                sql += "        score    = ?, ";
                sql += "        tstep    = ?, ";
                sql += "        mtest    = ?, ";
                sql += "        ftest    = ?, ";
                sql += "        report   = ?, ";
                sql += "        act      = ?, ";
                sql += "        etc1     = ?, ";
                sql += "        etc2     = ?, ";
                sql += "        avtstep  = ?, ";
                sql += "        avmtest  = ?, ";
                sql += "        avftest  = ?, ";
                sql += "        avreport = ?, ";
                sql += "        avact    = ?, ";
                sql += "        avetc1   = ?, ";
                sql += "        avetc2   = ?, ";
                sql += "        isgraduated = ?, ";
                sql += "        notgraducd = ?, notgraduetc='',";
                sql += "        isb2c    = ?, ";
                sql += "        edustart = ?, ";
                sql += "        eduend   = ?, ";
                sql += "        serno    = ?, ";
                sql += "        isrestudy= ?, ";
                sql += "        luserid  = ?, ";
                sql += "        isgoyong = ?, "; // 고용여부
                sql += "        ldate    = ? ";
                sql += "  where subj     = ? ";
                sql += "    and year     = ? ";
                sql += "    and subjseq  = ? ";
                sql += "    and userid   = ? ";
            } else if (p_command.equals("insert")) {
                sql = " insert into tz_stold ";
                sql += "  (subj,         year,       subjseq,    userid,   ";
                sql += "   name,         comp,       dept,       deptname, ";
                sql += "   jik,          score,      tstep,      mtest,    ";
                sql += "   ftest,        report,     act,        etc1,     ";
                sql += "   etc2,         avtstep,    avmtest,    avftest,  ";
                sql += "   avreport,     avact,      avetc1,     avetc2,   ";
                sql += "   isgraduated,  notgraducd, isb2c,      edustart,   eduend,  ";
                sql += "   serno,        isrestudy,  luserid,    isgoyong,   ldate)  ";
                sql += " values ";
                sql += "  (?,            ?,          ?,          ?, ";
                sql += "   ?,            ?,          ?,          ?, ";
                sql += "   ?,            ?,?,          ?,          ?, ";
                sql += "   ?,            ?,          ?,          ?, ";
                sql += "               ?,          ?,          ?, ";
                sql += "   ?,            ?,          ?,          ?, ";
                sql += "   ?,            ?,          ?,          ?,  ?,  ";
                sql += "   ?,            ?,          ?,          ?,  ? ) ";
            }
        } else if (p_gubun.equals("SUBJECT_STUDENT")) {
            if (p_command.equals("update")) {
                sql = " update tz_student ";
                sql += "    set score   = ?, ";
                sql += "        tstep   = ?, ";
                sql += "        mtest   = ?, ";
                sql += "        ftest   = ?, ";
                sql += "        report  = ?, ";
                sql += "        act     = ?, ";
                sql += "        etc1    = ?, ";
                sql += "        etc2    = ?, ";
                sql += "        avtstep = ?, ";
                sql += "        avmtest = ?, ";
                sql += "        avftest = ?, ";
                sql += "        avreport= ?, ";
                sql += "        avact   = ?, ";
                sql += "        avetc1  = ?, ";
                sql += "        avetc2  = ?, ";
                sql += "        isgraduated= ?, ";
                sql += "        luserid = ?, ";
                sql += "        ldate   = ?, ";
                sql += "        serno   = ? ";
                sql += "  where subj    = ? ";
                sql += "    and year    = ? ";
                sql += "    and subjseq = ? ";
                sql += "    and userid  = ? ";
            }
        } else if (p_gubun.equals("MEMBER")) {
            if (p_command.equals("select")) {
                sql = " select name, comp, jikwi ";
                sql += "   from tz_member ";
                sql += "  where userid = ? ";
            }
        }

        //	System.out.println("sql==>"+ sql);

        try {
            pstmt = connMgr.prepareStatement(sql);
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
        }
        return pstmt;
    }

    // 수료 처리/취소
    public int calc_course_score(PreparedStatement pstmt_select, PreparedStatement pstmt_update, PreparedStatement pstmt_insert, PreparedStatement pstmt_delete, CourseScoreData coursedata, String p_luserid, String p_job) throws Exception {

        ResultSet rs = null;
        int isOk = 0;

        int v_count = 0;

        try {
            pstmt_select.setString(1, coursedata.getCourse());
            pstmt_select.setString(2, coursedata.getCyear());
            pstmt_select.setString(3, coursedata.getCourseseq());
            pstmt_select.setString(4, coursedata.getUserid());

            rs = pstmt_select.executeQuery();
            while (rs.next()) {
                v_count = rs.getInt("cnt");
            }

            if (p_job.equals("delete")) {
                pstmt_delete.setString(1, coursedata.getCourse());
                pstmt_delete.setString(2, coursedata.getCyear());
                pstmt_delete.setString(3, coursedata.getCourseseq());
                pstmt_delete.setString(4, coursedata.getUserid());

                isOk = pstmt_delete.executeUpdate();
            } else {
                if (v_count > 0) { // udpate
                    pstmt_update.setInt(1, coursedata.getGraduatedcnt());
                    pstmt_update.setDouble(2, coursedata.getScore());
                    pstmt_update.setString(3, coursedata.getIsgraduated());
                    pstmt_update.setString(4, p_luserid);
                    pstmt_update.setString(5, FormatDate.getDate("yyyyMMddHHmmss"));
                    pstmt_update.setString(6, coursedata.getCourse());
                    pstmt_update.setString(7, coursedata.getCyear());
                    pstmt_update.setString(8, coursedata.getCourseseq());
                    pstmt_update.setString(9, coursedata.getUserid());

                    isOk = pstmt_update.executeUpdate();
                } else if (v_count == 0) { // insert
                    pstmt_insert.setString(1, coursedata.getCourse());
                    pstmt_insert.setString(2, coursedata.getCyear());
                    pstmt_insert.setString(3, coursedata.getCourseseq());
                    pstmt_insert.setString(4, coursedata.getUserid());
                    pstmt_insert.setInt(5, coursedata.getGraduatedcnt());
                    pstmt_insert.setDouble(6, coursedata.getScore());
                    pstmt_insert.setString(7, coursedata.getIsgraduated());
                    pstmt_insert.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));
                    pstmt_insert.setString(9, p_luserid);
                    pstmt_insert.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));

                    isOk = pstmt_insert.executeUpdate();
                }
            }
            System.out.println("PreparedStatement pstmt_select = " + pstmt_select);
            System.out.println("PreparedStatement pstmt_update = " + pstmt_update);
            System.out.println("PreparedStatement pstmt_insert = " + pstmt_select);
            System.out.println("PreparedStatement pstmt_delete = " + pstmt_delete);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectOffCompleteList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        //        SubjseqData data = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            connMgr = new DBConnectionManager();
            list = getOffCompleteList(connMgr, v_subj, v_year, v_subjseq, v_orderColumn, v_orderType);
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

    @SuppressWarnings("unchecked")
    public ArrayList getOffCompleteList(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String v_orderColumn, String v_orderType) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        StoldData data = null;

        try {
            sql = "select a.subj,  a.year, a.subjseq, a.userid, ";
            sql += "       b.comp,  get_compnm(b.comp,2,4) compnm,  ";
            sql += "       b.jikup, get_jikwinm(b.jikwi, b.comp) jikwinm, ";
            sql += "       b.cono,  b.name,  ";
            sql += "       a.tstep, a.mtest, a.ftest, a.report, ";
            sql += "       a.etc1,  a.etc2,  a.score, ";
            sql += "       (select isgraduated from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.userid) isgraduated,   ";
            sql += "       (select notgraducd from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.userid) notgraducd,   ";
            sql += "       (select notgraduetc from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.userid) notgraduetc,   ";
            sql += "	   	  (select codenm from tz_code where gubun='0028' and code=(select notgraduetc from tz_stold where subj=a.subj and year=a.year and subjseq=a.subjseq and userid=a.userid)) notgraduetcdesc ";
            sql += "  from tz_student  a,  ";
            sql += "        tz_member   b  ";
            sql += " where a.userid  = b.userid ";
            sql += "   and a.subj    = " + SQLString.Format(p_subj);
            sql += "   and a.year    = " + SQLString.Format(p_year);
            sql += "   and a.subjseq = " + SQLString.Format(p_subjseq);

            if (v_orderColumn.equals("")) {
                sql += " order by b.name  ";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new StoldData();

                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setUserid(ls.getString("userid"));

                data.setComp(ls.getString("comp"));
                data.setCompnm(ls.getString("compnm"));
                data.setJikwi(ls.getString("jikwi"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));
                data.setTstep(ls.getDouble("tstep"));
                data.setMtest(ls.getDouble("mtest"));
                data.setFtest(ls.getDouble("ftest"));
                data.setReport(ls.getDouble("report"));
                data.setEtc1(ls.getDouble("etc1"));
                data.setEtc1(ls.getDouble("etc1"));
                data.setScore(ls.getDouble("score"));
                data.setIsgraduated(ls.getString("isgraduated"));
                data.setNotgraducd(ls.getString("notgraducd"));
                data.setNotgraduetc(ls.getString("notgraduetc"));
                data.setNotgraduetcdesc(ls.getString("notgraduetcdesc"));
                data.setNotgraducddesc(getNotGraduCodeName(connMgr, ls.getString("notgraducd")));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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

    public SubjseqData SelectSubjseqInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //		ArrayList list = null;
        SubjseqData data = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            data = getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);
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
        return data;
    }

    /**
     * 수료처리 상세화면 인포정보
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox SelectSubjseqInfoDbox(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //        ArrayList list = null;
        DataBox dbox = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            dbox = getSubjseqInfoDbox(connMgr, v_subj, v_year, v_subjseq);
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
        return dbox;
    }

    /*
     * public int OffSubjectComplete(RequestBox box) throws Exception { // DB 처리
     * 관련 변수 DBConnectionManager connMgr = null; PreparedStatement pstmt_select
     * = null; PreparedStatement pstmt_update = null; PreparedStatement
     * pstmt_insert = null; PreparedStatement pstmt_update_student = null;
     * PreparedStatement pstmt_select_member = null; PreparedStatement
     * pstmt_select_serno = null;
     * 
     * 
     * ResultSet rs = null;
     * 
     * ArrayList list = null;
     * 
     * // 수료처리시 필요한 기본 변수들 String v_subj = box.getString("p_subj"); String
     * v_year = box.getString("p_year"); String v_subjseq =
     * box.getString("p_subjseq"); String v_value1 = box.getString("p_value");
     * //String with userids String v_value2 = ""; String v_isclosed=
     * box.getString("p_isclosed");
     * 
     * String v_userid = ""; String v_luserid = box.getSession("userid");
     * 
     * double v_tstep = 0; double v_mtest = 0; double v_ftest = 0; double
     * v_report = 0; double v_etc1 = 0; double v_etc2 = 0;
     * 
     * StringTokenizer st1 = null; StringTokenizer st2 = null;
     * 
     * StoldData data = new StoldData(); SubjseqData subjseqdata = null;
     * 
     * String v_currdate = FormatDate.getDate("yyyyMMddHH"); // 현재시각 년+월+일+시
     * 
     * int isOk = 1; boolean v_isexception = false; String v_return_msg = ""; //
     * 1 : "정상적으로 수료처리 되었습니다." // 2 : "이미 수료처리 되었습니다." // 3 : "과정시작후 가능합니다. // 4
     * : "수료처리==> 점수 재산정 중 문제발생함[" + v_userid + "]" // 5 : "학습자의 리포트 중 " +
     * String.valueOf(v_remainReportcnt) +
     * "' 개가 미채점되었습니다. \n\n수료처리취소후 점검하여 주십시오"; // "excaption 발생"
     * 
     * try { connMgr = new DBConnectionManager(); connMgr.setAutoCommit(false);
     * 
     * // 수료처리 완료여부, 학습중 검토 subjseqdata = getSubjseqInfo(connMgr, v_subj,
     * v_year, v_subjseq);
     * 
     * data.setSubj (v_subj); data.setYear (v_year); data.setSubjseq
     * (v_subjseq); data.setWstep (subjseqdata.getWstep()); data.setWmtest
     * (subjseqdata.getWmtest()); data.setWftest (subjseqdata.getWftest());
     * data.setWhtest (subjseqdata.getWhtest()); data.setWreport
     * (subjseqdata.getWreport()); data.setWact (subjseqdata.getWact());
     * data.setWetc1 (subjseqdata.getWetc1()); data.setWetc2
     * (subjseqdata.getWetc2()); data.setGradscore(subjseqdata.getGradscore());
     * data.setGradstep (subjseqdata.getGradstep()); data.setGradexam
     * (subjseqdata.getGradexam()); data.setGradreport
     * (subjseqdata.getGradreport()); data.setEdustart
     * (subjseqdata.getEdustart()); data.setEduend (subjseqdata.getEduend());
     * 
     * pstmt_update_student = getPreparedStatement(connMgr, "SUBJECT_STUDENT",
     * "update"); pstmt_select = getPreparedStatement(connMgr, "SUBJECT_STOLD",
     * "select"); pstmt_update = getPreparedStatement(connMgr, "SUBJECT_STOLD",
     * "update"); pstmt_insert = getPreparedStatement(connMgr, "SUBJECT_STOLD",
     * "insert"); pstmt_select_member = getPreparedStatement(connMgr, "MEMBER",
     * "select"); pstmt_select_serno = getPreparedStatement(connMgr,
     * "STUDENT_SERNO", "select"); // 수료증번호 TZ_STUDENT 찾기
     * 
     * st1 = new StringTokenizer(v_value1,":"); while (st1.hasMoreElements()) {
     * v_userid = ""; v_etc2 = 0; v_etc1 = 0; v_report = 0; v_ftest = 0; v_mtest
     * = 0; v_tstep = 0; v_value2 = (String)st1.nextToken();
     * 
     * st2 = new StringTokenizer(v_value2,","); while (st2.hasMoreElements()) {
     * int v_tokensize = st2.countTokens();
     * 
     * 
     * v_userid = (String)st2.nextToken();
     * 
     * v_etc1 = Double.valueOf((String)st2.nextToken()).doubleValue(); v_report
     * = Double.valueOf((String)st2.nextToken()).doubleValue(); v_ftest =
     * Double.valueOf((String)st2.nextToken()).doubleValue(); v_tstep =
     * Double.valueOf((String)st2.nextToken()).doubleValue();
     * 
     * } data.setUserid(v_userid); data.setScore(0); data.setTstep(v_tstep);
     * data.setMtest(v_mtest); data.setFtest(v_ftest); //data.setHtest(0);
     * data.setReport(v_report); data.setAct(0); data.setEtc1(v_etc1);
     * data.setEtc2(v_etc2); data.setAvtstep(0); data.setAvmtest(0);
     * data.setAvftest(0); data.setAvreport(0); data.setAvact(0);
     * data.setAvetc1(0); data.setAvetc2(0);
     * 
     * 
     * data.setIsgraduated("N");
     * 
     * pstmt_select_member.setString( 1, data.getUserid());
     * 
     * rs = pstmt_select_member.executeQuery();
     * 
     * while(rs.next()) { data.setName(rs.getString("name"));
     * data.setComp(rs.getString("comp")); data.setJik(rs.getString("jikwi")); }
     * 
     * // SCORE 계산 calc_offsubj_score(connMgr, data);
     * 
     * // 1.tz_student update // 2.tz_stold update or insert
     * update_offsubj_score(connMgr, pstmt_update_student, pstmt_select,
     * pstmt_insert, pstmt_update, data, v_luserid); }
     * 
     * setCloseColumn(connMgr, v_subj, v_year, v_subjseq, v_isclosed); } catch
     * (Exception ex) {ex.printStackTrace(); isOk = 100; v_isexception = true;
     * v_return_msg = ex.getMessage(); ErrorManager.getErrorStackTrace(ex);
     * throw new Exception(ex.getMessage()); } finally { try { if
     * (!v_return_msg.equals("")) box.put("p_return_msg", v_return_msg);
     * }catch(Exception e) { e.printStackTrace(); }
     * 
     * if(pstmt_select != null) { try { pstmt_select.close(); } catch (Exception
     * e) {} } if(pstmt_update != null) { try { pstmt_update.close(); } catch
     * (Exception e) {} } if(pstmt_insert != null) { try { pstmt_insert.close();
     * } catch (Exception e) {} } if(pstmt_update_student != null) { try {
     * pstmt_update_student.close(); } catch (Exception e) {} }
     * if(pstmt_select_member != null) { try { pstmt_select_member.close(); }
     * catch (Exception e) {} }
     * 
     * if(connMgr != null) { try { if (v_isexception) { connMgr.rollback(); }
     * else { connMgr.commit(); } connMgr.setAutoCommit(true);
     * connMgr.freeConnection(); } catch (Exception e10) {} } } return isOk; }
     */

    /**
     * 집합과정 수료처리
     * 
     * @param box receive from the form object
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int OffSubjectCompleteNew(RequestBox box) throws Exception {
        // DB 처리 관련 변수
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt_select = null;
        PreparedStatement pstmt_update = null;
        PreparedStatement pstmt_insert = null;
        PreparedStatement pstmt_update_student = null;
        PreparedStatement pstmt_select_member = null;
        ResultSet rs = null;

        ListSet ls = null;
        String sql = "";

        // 수료처리시 필요한 기본 변수들
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        //        String v_value1  = box.getString("p_value");        //String with userids
        //        String v_value2  = "";
        //String v_isclosed= box.getStringDefault("p_isclosed", "Y");
        String v_isclosed = "Y";
        String v_userid = "";
        String v_luserid = box.getSession("userid");

        double v_tstep = 0;
        double v_mtest = 0;
        double v_ftest = 0;
        double v_report = 0;
        double v_etc1 = 0;
        double v_etc2 = 0;
        String v_isgoyong = "";
        double v_samtotal = 0; // 삼지아웃

        //        StringTokenizer st1 = null;
        //        StringTokenizer st2 = null;

        StoldData data = new StoldData();
        SubjseqData subjseqdata = null;
        //		String v_currdate = FormatDate.getDate("yyyyMMddHH");  // 현재시각 년+월+일+시
        int isOk = 1;
        boolean v_isexception = false;
        String v_return_msg = "";
        //      1 : "정상적으로 수료처리 되었습니다."
        //      2 : "이미 수료처리 되었습니다."
        //      3 : "과정시작후 가능합니다.
        //      4 : "수료처리==> 점수 재산정 중 문제발생함[" + v_userid + "]"
        //      5 : "학습자의 리포트 중 " + String.valueOf(v_remainReportcnt) + "' 개가 미채점되었습니다. \n\n수료처리취소후 점검하여 주십시오";
        //      "excaption 발생"

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            subjseqdata = getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);

            data.setSubj(v_subj);
            data.setYear(v_year);
            data.setSubjseq(v_subjseq);
            data.setWstep(subjseqdata.getWstep());
            data.setWmtest(subjseqdata.getWmtest());
            data.setWftest(subjseqdata.getWftest());
            data.setWhtest(subjseqdata.getWhtest());
            data.setWreport(subjseqdata.getWreport());
            data.setWact(subjseqdata.getWact());
            data.setWetc1(subjseqdata.getWetc1());
            data.setWetc2(subjseqdata.getWetc2());
            data.setGradscore(subjseqdata.getGradscore());
            data.setGradstep(subjseqdata.getGradstep());
            data.setGradexam(subjseqdata.getGradexam());
            data.setGradreport(subjseqdata.getGradreport());
            data.setEdustart(subjseqdata.getEdustart());
            data.setEduend(subjseqdata.getEduend());

            pstmt_update_student = getPreparedStatement(connMgr, "SUBJECT_STUDENT", "update");
            pstmt_select = getPreparedStatement(connMgr, "SUBJECT_STOLD", "select");
            pstmt_update = getPreparedStatement(connMgr, "SUBJECT_STOLD", "update");
            pstmt_insert = getPreparedStatement(connMgr, "SUBJECT_STOLD", "insert");
            pstmt_select_member = getPreparedStatement(connMgr, "MEMBER", "select");

            sql = "select userid, tstep, ftest, report, etc1, isgoyong, samtotal from tz_student where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            ls = connMgr.executeQuery(sql);

            ////st1 = new StringTokenizer(v_value1,":");
            ////while (st1.hasMoreElements()) {
            while (ls.next()) {
                v_userid = "";
                v_etc2 = 0;
                v_etc1 = 0;
                v_report = 0;
                v_ftest = 0;
                v_mtest = 0;
                v_tstep = 0;
                v_userid = ls.getString("userid");
                v_tstep = ls.getDouble("tstep");
                v_ftest = ls.getDouble("ftest");
                v_report = ls.getDouble("report");
                v_etc1 = ls.getDouble("etc1");
                v_isgoyong = ls.getString("isgoyong"); // 고용여부
                v_samtotal = ls.getDouble("samtotal"); // 삼진아웃

                data.setUserid(v_userid);
                data.setScore(0);
                data.setTstep(v_tstep);
                data.setMtest(v_mtest);
                data.setFtest(v_ftest);
                //data.setHtest(0);
                data.setReport(v_report);
                data.setAct(0);
                data.setEtc1(v_etc1);
                data.setEtc2(v_etc2);
                data.setAvtstep(0);
                data.setAvmtest(0);
                data.setAvftest(0);
                data.setAvreport(0);
                data.setAvact(0);
                data.setAvetc1(0);
                data.setAvetc2(0);
                data.setIsgoyong(v_isgoyong);

                // 삼진아웃 시 미수료
                if (v_samtotal > 2) {
                    v_isexception = true;
                    v_return_msg = v_userid + " 학습자는 삼진아웃 상태입니다. ";
                    isOk = 6;
                    return isOk;
                }

                data.setIsgraduated("N");

                pstmt_select_member.setString(1, data.getUserid());

                rs = pstmt_select_member.executeQuery();

                while (rs.next()) {
                    data.setName(rs.getString("name"));
                    data.setComp(rs.getString("comp"));
                    data.setJik(rs.getString("jikwi"));
                }

                // SCORE 계산
                calc_offsubj_score(connMgr, data);

                // 1.tz_student update
                // 2.tz_stold update or insert
                update_offsubj_score(connMgr, pstmt_update_student, pstmt_select, pstmt_insert, pstmt_update, data, v_luserid);

            } // while end

            // 수료필드 업데이트
            setCloseColumn(connMgr, v_subj, v_year, v_subjseq, v_isclosed);
        } catch (Exception ex) {
            ex.printStackTrace();
            isOk = 100;
            v_isexception = true;
            v_return_msg = ex.getMessage();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            try {
                if (!v_return_msg.equals(""))
                    box.put("p_return_msg", v_return_msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_select != null) {
                try {
                    pstmt_select.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_update != null) {
                try {
                    pstmt_update.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_insert != null) {
                try {
                    pstmt_insert.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_update_student != null) {
                try {
                    pstmt_update_student.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_select_member != null) {
                try {
                    pstmt_select_member.close();
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 집합과정 점수재계산
     * 
     * @param box receive from the form object
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int OffSubjectCompleteRerating(RequestBox box) throws Exception {
        // DB 처리 관련 변수
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt_select = null;
        PreparedStatement pstmt_update = null;
        PreparedStatement pstmt_insert = null;
        PreparedStatement pstmt_update_student = null;
        PreparedStatement pstmt_select_member = null;
        ResultSet rs = null;

        ListSet ls = null;
        String sql = "";

        // 수료처리시 필요한 기본 변수들
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        //        String v_value1  = box.getString("p_value");        //String with userids
        //        String v_value2  = "";
        //String v_isclosed= box.getStringDefault("p_isclosed", "Y");
        //        String v_isclosed = "Y";
        String v_userid = "";
        String v_luserid = box.getSession("userid");

        double v_tstep = 0;
        double v_mtest = 0;
        double v_ftest = 0;
        double v_report = 0;
        double v_etc1 = 0;
        double v_etc2 = 0;

        //        StringTokenizer st1 = null;
        //        StringTokenizer st2 = null;

        StoldData data = new StoldData();
        SubjseqData subjseqdata = null;
        //		String v_currdate = FormatDate.getDate("yyyyMMddHH");  // 현재시각 년+월+일+시
        int isOk = 1;
        boolean v_isexception = false;
        String v_return_msg = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수료처리 완료여부, 학습중 검토
            subjseqdata = getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);

            data.setSubj(v_subj);
            data.setYear(v_year);
            data.setSubjseq(v_subjseq);
            data.setWstep(subjseqdata.getWstep());
            data.setWmtest(subjseqdata.getWmtest());
            data.setWftest(subjseqdata.getWftest());
            data.setWhtest(subjseqdata.getWhtest());
            data.setWreport(subjseqdata.getWreport());
            data.setWact(subjseqdata.getWact());
            data.setWetc1(subjseqdata.getWetc1());
            data.setWetc2(subjseqdata.getWetc2());
            data.setGradscore(subjseqdata.getGradscore());
            data.setGradstep(subjseqdata.getGradstep());
            data.setGradexam(subjseqdata.getGradexam());
            data.setGradreport(subjseqdata.getGradreport());
            data.setEdustart(subjseqdata.getEdustart());
            data.setEduend(subjseqdata.getEduend());

            pstmt_update_student = getPreparedStatement(connMgr, "SUBJECT_STUDENT", "update");
            pstmt_select = getPreparedStatement(connMgr, "SUBJECT_STOLD", "select");
            pstmt_update = getPreparedStatement(connMgr, "SUBJECT_STOLD", "update");
            pstmt_insert = getPreparedStatement(connMgr, "SUBJECT_STOLD", "insert");
            pstmt_select_member = getPreparedStatement(connMgr, "MEMBER", "select");

            sql = "select userid, tstep, ftest, report, etc1 from tz_student where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                v_userid = "";
                v_etc2 = 0;
                v_etc1 = 0;
                v_report = 0;
                v_ftest = 0;
                v_mtest = 0;
                v_tstep = 0;

                v_userid = ls.getString("userid");
                v_tstep = ls.getDouble("tstep");
                v_ftest = ls.getDouble("ftest");
                v_report = ls.getDouble("report");
                v_etc1 = ls.getDouble("etc1");

                data.setUserid(v_userid);
                data.setScore(0);
                data.setTstep(v_tstep);
                data.setMtest(v_mtest);
                data.setFtest(v_ftest);
                //data.setHtest(0);
                data.setReport(v_report);
                data.setAct(0);
                data.setEtc1(v_etc1);
                data.setEtc2(v_etc2);
                data.setAvtstep(0);
                data.setAvmtest(0);
                data.setAvftest(0);
                data.setAvreport(0);
                data.setAvact(0);
                data.setAvetc1(0);
                data.setAvetc2(0);

                data.setIsgraduated("N");

                pstmt_select_member.setString(1, data.getUserid());

                rs = pstmt_select_member.executeQuery();

                while (rs.next()) {
                    data.setName(rs.getString("name"));
                    data.setComp(rs.getString("comp"));
                    data.setJik(rs.getString("jikwi"));
                }

                // SCORE 계산
                calc_offsubj_score(connMgr, data);

                // 1.tz_student update
                // 2.tz_stold update or insert
                update_offsubj_score(connMgr, pstmt_update_student, pstmt_select, pstmt_insert, pstmt_update, data, v_luserid);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            isOk = 100;
            v_isexception = true;
            v_return_msg = ex.getMessage();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            try {
                if (!v_return_msg.equals(""))
                    box.put("p_return_msg", v_return_msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_select != null) {
                try {
                    pstmt_select.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_update != null) {
                try {
                    pstmt_update.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_insert != null) {
                try {
                    pstmt_insert.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_update_student != null) {
                try {
                    pstmt_update_student.close();
                } catch (Exception e) {
                }
            }
            if (pstmt_select_member != null) {
                try {
                    pstmt_select_member.close();
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    if (v_isexception) {
                        connMgr.rollback();
                    } else {
                        connMgr.commit();
                    }
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 집합과정 점수 계산
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int calc_offsubj_score(DBConnectionManager connMgr, StoldData data) throws Exception {
        int isOk = 1;
        try {
            data.setAvtstep((double) Math.round(data.getTstep() * data.getWstep()) / 100);
            data.setAvreport((double) Math.round(data.getReport() * data.getWreport()) / 100);
            data.setAvmtest((double) Math.round(data.getMtest() * data.getWmtest()) / 100);
            data.setAvftest((double) Math.round(data.getFtest() * data.getWftest()) / 100);
            data.setAvhtest((double) Math.round(data.getHtest() * data.getWhtest()) / 100);
            data.setAvact((double) Math.round(data.getAct() * data.getWact()) / 100);
            data.setAvetc1((double) Math.round(data.getEtc1() * data.getWetc1()) / 100);
            data.setAvetc2((double) Math.round(data.getEtc2() * data.getWetc2()) / 100);
            //	System.out.println("calc_offsubj_score");
            isOk = calc_isgraduated(connMgr, data.getSubj(), data.getYear(), data.getSubjseq(), data.getUserid(), data);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
        }
        return isOk;
    }

    /**
     * 수료(TZ_STOLD), 수강생정보(TZ_STUDENT) 처리
     * 
     * @param connMgr receive from the form object and session
     * @return int
     */
    public int update_offsubj_score(DBConnectionManager connMgr, PreparedStatement pstmt_update_student, PreparedStatement pstmt_select, PreparedStatement pstmt_insert, PreparedStatement pstmt_update, StoldData data, String p_luserid) throws Exception {

        ResultSet rs = null;
        int isOk = 0;
        int v_count = 0;
        String sserno = "";
        String ssubj = data.getSubj();
        String syear = data.getYear();
        String ssubjseq = data.getSubjseq();
        String sisgraduated = data.getIsgraduated();

        try {

            // 수료증번호 발급
            if (sisgraduated.equals("Y")) {
                sserno = getCompleteSerno(connMgr, ssubj, syear, ssubjseq, data.getUserid());
            }

            // 1.TZ_STOLD update or insert
            pstmt_select.setString(1, ssubj);
            pstmt_select.setString(2, syear);
            pstmt_select.setString(3, ssubjseq);
            pstmt_select.setString(4, data.getUserid());

            rs = pstmt_select.executeQuery();

            while (rs.next()) {
                v_count = rs.getInt("cnt");
            }

            if (v_count > 0) { // update
                pstmt_update.setString(1, data.getName());
                pstmt_update.setString(2, data.getComp());
                pstmt_update.setString(3, data.getDept());
                pstmt_update.setString(4, data.getDeptname());
                pstmt_update.setString(5, data.getJik());
                pstmt_update.setDouble(6, data.getScore());
                pstmt_update.setDouble(7, data.getTstep());
                pstmt_update.setDouble(8, data.getMtest());
                pstmt_update.setDouble(9, data.getFtest());
                pstmt_update.setDouble(10, data.getReport());
                pstmt_update.setDouble(11, data.getAct());
                pstmt_update.setDouble(12, data.getEtc1());
                pstmt_update.setDouble(13, data.getEtc2());
                pstmt_update.setDouble(14, data.getAvtstep());
                pstmt_update.setDouble(15, data.getAvmtest());
                pstmt_update.setDouble(16, data.getAvftest());
                pstmt_update.setDouble(17, data.getAvreport());
                pstmt_update.setDouble(18, data.getAvact());
                pstmt_update.setDouble(19, data.getAvetc1());
                pstmt_update.setDouble(20, data.getAvetc2());
                pstmt_update.setString(21, data.getIsgraduated());
                pstmt_update.setString(22, data.getNotgraducd());
                pstmt_update.setString(23, data.getIsb2c());
                pstmt_update.setString(24, data.getEdustart());
                pstmt_update.setString(25, data.getEduend());
                pstmt_update.setString(26, sserno); // 수료증번호
                pstmt_update.setString(27, data.getIsrestudy());
                pstmt_update.setString(28, p_luserid);
                pstmt_insert.setString(29, data.getIsgoyong()); // 고용여부
                pstmt_update.setString(30, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt_update.setString(31, data.getSubj());
                pstmt_update.setString(32, data.getYear());
                pstmt_update.setString(33, data.getSubjseq());
                pstmt_update.setString(34, data.getUserid());

                isOk = pstmt_update.executeUpdate();

            } else if (v_count == 0) { // insert

                pstmt_insert.setString(1, data.getSubj());
                pstmt_insert.setString(2, data.getYear());
                pstmt_insert.setString(3, data.getSubjseq());
                pstmt_insert.setString(4, data.getUserid());
                pstmt_insert.setString(5, data.getName());
                pstmt_insert.setString(6, data.getComp());
                pstmt_insert.setString(7, data.getDept());
                pstmt_insert.setString(8, data.getDeptname());
                pstmt_insert.setString(9, data.getJik());
                pstmt_insert.setDouble(10, data.getScore());
                pstmt_insert.setDouble(11, data.getTstep());
                pstmt_insert.setDouble(12, data.getMtest());
                pstmt_insert.setDouble(13, data.getFtest());
                pstmt_insert.setDouble(14, data.getReport());
                pstmt_insert.setDouble(15, data.getAct());
                pstmt_insert.setDouble(16, data.getEtc1());
                pstmt_insert.setDouble(17, data.getEtc2());
                pstmt_insert.setDouble(18, data.getAvtstep());
                pstmt_insert.setDouble(19, data.getAvmtest());
                pstmt_insert.setDouble(20, data.getAvftest());
                pstmt_insert.setDouble(21, data.getAvreport());
                pstmt_insert.setDouble(22, data.getAvact());
                pstmt_insert.setDouble(23, data.getAvetc1());
                pstmt_insert.setDouble(24, data.getAvetc2());
                pstmt_insert.setString(25, data.getIsgraduated());
                pstmt_insert.setString(26, data.getNotgraducd());
                pstmt_insert.setString(27, data.getIsb2c());
                pstmt_insert.setString(28, data.getEdustart());
                pstmt_insert.setString(29, data.getEduend());
                pstmt_insert.setString(30, sserno); // 수료증번호
                pstmt_insert.setString(31, data.getIsrestudy());
                pstmt_insert.setString(32, p_luserid);
                pstmt_insert.setString(33, data.getIsgoyong()); // 고용여부
                pstmt_insert.setString(34, FormatDate.getDate("yyyyMMddHHmmss"));

                isOk = pstmt_insert.executeUpdate();
            }

            // 2.TZ_STUDENT update
            pstmt_update_student.setDouble(1, data.getScore());
            pstmt_update_student.setDouble(2, data.getTstep());
            pstmt_update_student.setDouble(3, data.getMtest());
            pstmt_update_student.setDouble(4, data.getFtest());
            pstmt_update_student.setDouble(5, data.getReport());
            pstmt_update_student.setDouble(6, data.getAct());
            pstmt_update_student.setDouble(7, data.getEtc1());
            pstmt_update_student.setDouble(8, data.getEtc2());
            pstmt_update_student.setDouble(9, data.getAvtstep());
            pstmt_update_student.setDouble(10, data.getAvmtest());
            pstmt_update_student.setDouble(11, data.getAvftest());
            pstmt_update_student.setDouble(12, data.getAvreport());
            pstmt_update_student.setDouble(13, data.getAvact());
            pstmt_update_student.setDouble(14, data.getAvetc1());
            pstmt_update_student.setDouble(15, data.getAvetc2());
            pstmt_update_student.setString(16, data.getIsgraduated());
            pstmt_update_student.setString(17, p_luserid);
            pstmt_update_student.setString(18, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt_update_student.setString(19, sserno); // 수료증번호
            pstmt_update_student.setString(20, data.getSubj());
            pstmt_update_student.setString(21, data.getYear());
            pstmt_update_student.setString(22, data.getSubjseq());
            pstmt_update_student.setString(23, data.getUserid());

            isOk = pstmt_update_student.executeUpdate();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 미수료사유 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateNotgraducd(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_userid = box.getString("p_userid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_notgraducd = box.getString("p_notgraducd");

        try {
            connMgr = new DBConnectionManager();

            sql = "update tz_stold set notgraduetc = ? , ldate=? where subj=? and year=? and subjseq=? and userid=?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_notgraducd);
            pstmt.setString(2, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(3, v_subj);
            pstmt.setString(4, v_year);
            pstmt.setString(5, v_subjseq);
            pstmt.setString(6, v_userid);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    /**
     * 미수료사유 일괄 변경
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateNotgraducdAll(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_notgraducds = box.getString("p_notgraducds"); // userid
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_notgraducd = box.getString("p_notgraducd");

        try {
            connMgr = new DBConnectionManager();

            StringTokenizer st = new StringTokenizer(v_notgraducds, "^");

            while (st.hasMoreElements()) {

                String s_userid = StringManager.trim(st.nextToken());

                sql = "update tz_stold set notgraduetc = ? , ldate=? where subj=? and year=? and subjseq=? and userid=?";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_notgraducd);
                pstmt.setString(2, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt.setString(3, v_subj);
                pstmt.setString(4, v_year);
                pstmt.setString(5, v_subjseq);
                pstmt.setString(6, s_userid);

                isOk = pstmt.executeUpdate();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }
    
    /**
     * 상시학습과정
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateAllGraduated(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	PreparedStatement pstmt = null;
    	String sql = "";
    	int isOk = 0;
    	String sserno = "";
    	
    	String v_userid = box.getString("p_userid");
    	String v_subj = box.getString("p_subj");
    	String v_year = box.getString("p_year");
    	String v_subjseq = box.getString("p_subjseq");
    	String v_isgraduated = "Y";
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		// 수료증번호 발급
    		if (v_isgraduated.equals("Y")) {
    			sserno = getCompleteSerno(connMgr, v_subj, v_year, v_subjseq, v_userid);
    		}
    		
    		sql = "select \n";
    		sql += "a.SUBJ,a.YEAR,a.SUBJSEQ,b.USERID,c.name,\n";
    		sql += "b.SCORE,b.TSTEP,b.MTEST,b.FTEST,b.REPORT,\n";
    		sql += "b.ACT,b.ETC1,b.ETC2,b.AVTSTEP,b.AVMTEST,\n";
    		sql += "b.AVFTEST,b.AVREPORT,b.AVACT,b.AVETC1,b.AVETC2,\n";
    		sql += "b.ISGRADUATED,'N',b.EDUSTART,b.EDUEND,'','','lee1',to_char(sysdate, 'YYYYMMDDHH24MISS'),nvl(d.userid,'') as stold_name \n";
    		sql += "from tz_subjseq a\n";
    		sql += "left join tz_student b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and b.userid='" + v_userid + "' \n";
    		sql += "left join tz_member c on b.userid=c.userid \n";
    		sql += "left join tz_stold d on b.subj=d.subj and b.year=d.year and b.subjseq=d.subjseq and b.userid=d.userid \n";
    		sql += "where a.subj='" + v_subj + "' and a.year='" + v_year + "' and a.subjseq='" + v_subjseq + "'";
    		
    		ls = connMgr.executeQuery(sql);
    		if (ls.next()) {
    			String stold_Bool = ls.getString("stold_name");
    			if (stold_Bool.equals("")) {
    				sql = "insert into tz_stold values (?,?,?,?,?,?,?,?,?,?, ";
    				sql += "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?,?,?,?,?,?)";
    				pstmt = connMgr.prepareStatement(sql);
    				
    				int index = 1;
    				pstmt.setString(index++, ls.getString("SUBJ"));
    				pstmt.setString(index++, ls.getString("YEAR"));
    				pstmt.setString(index++, ls.getString("SUBJSEQ"));
    				pstmt.setString(index++, ls.getString("USERID"));
    				pstmt.setString(index++, ls.getString("name")); // 5
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, ls.getString("SCORE")); //10
    				pstmt.setString(index++, ls.getString("TSTEP"));
    				pstmt.setString(index++, ls.getString("MTEST"));
    				pstmt.setString(index++, ls.getString("FTEST"));
    				pstmt.setString(index++, ls.getString("REPORT"));
    				pstmt.setString(index++, ls.getString("ACT")); // 15
    				pstmt.setString(index++, ls.getString("ETC1"));
    				pstmt.setString(index++, ls.getString("ETC2"));
    				pstmt.setString(index++, ls.getString("AVTSTEP"));
    				pstmt.setString(index++, ls.getString("AVMTEST"));
    				pstmt.setString(index++, ls.getString("AVFTEST")); //20
    				pstmt.setString(index++, ls.getString("AVREPORT"));
    				pstmt.setString(index++, ls.getString("AVACT"));
    				pstmt.setString(index++, ls.getString("AVETC1"));
    				pstmt.setString(index++, ls.getString("AVETC2"));
    				pstmt.setString(index++, ls.getString("ISGRADUATED")); // 25
    				pstmt.setString(index++, "N");
    				pstmt.setString(index++, ls.getString("EDUSTART"));
    				pstmt.setString(index++, FormatDate.getDate("yyyyMMdd"));
    				pstmt.setString(index++, sserno);
    				pstmt.setString(index++, ""); //30
    				pstmt.setString(index++, box.getSession("userid"));
    				pstmt.setString(index++, "0");
    				pstmt.setString(index++, "0");
    				pstmt.setString(index++, ""); // 35
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "N");
    				pstmt.setString(index++, "");
    				pstmt.setString(index++, "N"); //40
    			} else {
    				sql = "update tz_stold set isgraduated = ?, notgraducd='',notgraduetc='', serno = ? where subj=? and year=? and subjseq=? and userid=?";
    				
    				pstmt = connMgr.prepareStatement(sql);
    				pstmt.setString(1, v_isgraduated);
    				pstmt.setString(2, sserno);
    				pstmt.setString(3, v_subj);
    				pstmt.setString(4, v_year);
    				pstmt.setString(5, v_subjseq);
    				pstmt.setString(6, v_userid);
    			}
    		}
    		ls.close();
    		
    		isOk = pstmt.executeUpdate();
    		
    		if (pstmt != null) {
    			try {
    				pstmt.close();
    			} catch (Exception e) {
    			}
    		}
    		
    		// TZ_STUDENT UPDATE
			isOk = updateStudentSerno(connMgr, sserno, v_subj, v_year, v_subjseq, v_userid);
    		
    		// TZ_STUDENT isgraduated-> UPDATE
    		// 학습자 테이블 update
    		updateStudentIsgraduated(connMgr, v_subj, v_year, v_subjseq, v_userid, v_isgraduated);
    		
    		
    		//TZ_STUDENT eduend -> UPDATE
    		//학습자 학습 종료일 update
    		//updateStudentEduend(connMgr, v_subj, v_year, v_subjseq, v_userid);
    	
    		
    	} catch (Exception ex) {
    		connMgr.rollback();
    		ErrorManager.getErrorStackTrace(ex, box, sql);
    		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
    	} finally {
    		if (pstmt != null) {
    			try {
    				pstmt.close();
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
    	return isOk;
    }

    /**
     * 미수,미이수 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateGraduated(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        String sserno = "";

        String v_userid = box.getString("p_userid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_isgraduated = box.getString("p_isgraduated");
        
        try {
            connMgr = new DBConnectionManager();

            SubjGongAdminBean sbean = new SubjGongAdminBean();
			String isalways  = sbean.getIsalways(box);
			
            // 수료증번호 발급
            if (v_isgraduated.equals("Y")) {
                sserno = getCompleteSerno(connMgr, v_subj, v_year, v_subjseq, v_userid);
            }

            String v_eduend = "";
            if(isalways.equals("Y"))
            	v_eduend = "b.eduend";
            else 
            	v_eduend = "a,eduend";
            
            sql = "select \n";
            sql += "a.SUBJ,a.YEAR,a.SUBJSEQ,b.USERID,c.name,\n";
            sql += "b.SCORE,b.TSTEP,b.MTEST,b.FTEST,b.REPORT,\n";
            sql += "b.ACT,b.ETC1,b.ETC2,b.AVTSTEP,b.AVMTEST,\n";
            sql += "b.AVFTEST,b.AVREPORT,b.AVACT,b.AVETC1,b.AVETC2,\n";
            sql += "b.ISGRADUATED,'N',a.EDUSTART,"+v_eduend+",'','','lee1',to_char(sysdate, 'YYYYMMDDHH24MISS'),nvl(d.userid,'') as stold_name \n";
            sql += "from tz_subjseq a\n";
            sql += "left join tz_student b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and b.userid='" + v_userid + "' \n";
            sql += "left join tz_member c on b.userid=c.userid \n";
            sql += "left join tz_stold d on b.subj=d.subj and b.year=d.year and b.subjseq=d.subjseq and b.userid=d.userid \n";
            sql += "where a.subj='" + v_subj + "' and a.year='" + v_year + "' and a.subjseq='" + v_subjseq + "'";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                String stold_Bool = ls.getString("stold_name");
                if (stold_Bool.equals("")) {
                    sql = "insert into tz_stold values (?,?,?,?,?,?,?,?,?,?, ";
                    sql += "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?,?,?,?,?,?)";
                    pstmt = connMgr.prepareStatement(sql);

                    int index = 1;
                    pstmt.setString(index++, ls.getString("SUBJ"));
                    pstmt.setString(index++, ls.getString("YEAR"));
                    pstmt.setString(index++, ls.getString("SUBJSEQ"));
                    pstmt.setString(index++, ls.getString("USERID"));
                    pstmt.setString(index++, ls.getString("name")); // 5
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, ls.getString("SCORE")); //10
                    pstmt.setString(index++, ls.getString("TSTEP"));
                    pstmt.setString(index++, ls.getString("MTEST"));
                    pstmt.setString(index++, ls.getString("FTEST"));
                    pstmt.setString(index++, ls.getString("REPORT"));
                    pstmt.setString(index++, ls.getString("ACT")); // 15
                    pstmt.setString(index++, ls.getString("ETC1"));
                    pstmt.setString(index++, ls.getString("ETC2"));
                    pstmt.setString(index++, ls.getString("AVTSTEP"));
                    pstmt.setString(index++, ls.getString("AVMTEST"));
                    pstmt.setString(index++, ls.getString("AVFTEST")); //20
                    pstmt.setString(index++, ls.getString("AVREPORT"));
                    pstmt.setString(index++, ls.getString("AVACT"));
                    pstmt.setString(index++, ls.getString("AVETC1"));
                    pstmt.setString(index++, ls.getString("AVETC2"));
                    pstmt.setString(index++, ls.getString("ISGRADUATED")); // 25
                    pstmt.setString(index++, "N");
                    pstmt.setString(index++, ls.getString("EDUSTART"));
                    pstmt.setString(index++, ls.getString("EDUEND"));
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, ""); //30
                    pstmt.setString(index++, box.getSession("userid"));
                    //                    pstmt.setString(index++, "날짜");
                    pstmt.setString(index++, "0");
                    pstmt.setString(index++, "0");
                    pstmt.setString(index++, ""); // 35
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, "N");
                    pstmt.setString(index++, "");
                    pstmt.setString(index++, "N"); //40
                } else {
                    sql = "update tz_stold set isgraduated = ?, notgraducd='',notgraduetc='', serno = ? where subj=? and year=? and subjseq=? and userid=?";

                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_isgraduated);
                    pstmt.setString(2, sserno);
                    pstmt.setString(3, v_subj);
                    pstmt.setString(4, v_year);
                    pstmt.setString(5, v_subjseq);
                    pstmt.setString(6, v_userid);
                }
            }
            ls.close();

            isOk = pstmt.executeUpdate();

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }

            // TZ_STUDENT UPDATE
            if (v_isgraduated.equals("Y")) {
                isOk = updateStudentSerno(connMgr, sserno, v_subj, v_year, v_subjseq, v_userid);
            } else {
                sql = "update TZ_STUDENT set serno = '' where subj=? and year=? and subjseq=? and userid=?";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_subj);
                pstmt.setString(2, v_year);
                pstmt.setString(3, v_subjseq);
                pstmt.setString(4, v_userid);

                isOk = pstmt.executeUpdate();
            }

            // TZ_STUDENT isgraduated-> UPDATE
            // 학습자 테이블 update
            updateStudentIsgraduated(connMgr, v_subj, v_year, v_subjseq, v_userid, v_isgraduated);

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    /**
     * 수료처리완료,미완료 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateIsClosed(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_isclosed) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        //	System.out.println("v_isclosed = " + v_isclosed);
        //	System.out.println("v_subj = " + v_subj);
        //	System.out.println("v_year = " + v_year);
        //	System.out.println("v_subjseq = " + v_subjseq);

        try {

            sql = "update tz_subjseq set isclosed = ? where subj=? and year=? and subjseq=?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_isclosed);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_year);
            pstmt.setString(4, v_subjseq);

            isOk = pstmt.executeUpdate();

            //      System.out.println("isOk="+isOk);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 외주업체 최종확인 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateIsCpflag(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_iscpflag) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        //	System.out.println("v_iscpflag = " + v_iscpflag);
        //	System.out.println("v_subj = " + v_subj);
        //	System.out.println("v_year = " + v_year);
        //	System.out.println("v_subjseq = " + v_subjseq);

        try {

            sql = "update tz_subjseq set iscpflag = ? where subj=? and year=? and subjseq=?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_iscpflag);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_year);
            pstmt.setString(4, v_subjseq);

            isOk = pstmt.executeUpdate();

            //      System.out.println("isOk="+isOk);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 집합과정 수료처리
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateOffSubjIsClosed(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        //		String v_subj  	= box.getString("p_subj");
        //		String v_year  	= box.getString("p_year");
        //		String v_subjseq  	= box.getString("p_subjseq");

        //	System.out.println("신 집합과정 수료처리");
        //	System.out.println("v_subj = " + v_subj);
        //	System.out.println("v_year = " + v_year);
        //	System.out.println("v_subjseq = " + v_subjseq);

        try {
            connMgr = new DBConnectionManager();

            //수료처리 완료로 저장
            ////isOk = this.updateIsClosed(connMgr, v_subj, v_year, v_subjseq, "Y");

            //     System.out.println("isOk="+isOk);

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    /**
     * 외주업체 과정 수료처리
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateOutSubjIsClosed(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        //		PreparedStatement pstmt2 = null;
        String sql = "";
        //		String sql2 = "";
        int isOk = 0;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        //	System.out.println("v_subj = " + v_subj);
        //	System.out.println("v_year = " + v_year);
        //	System.out.println("v_subjseq = " + v_subjseq);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //stold table delete
            // 수료 정보 삭제
            isOk = deleteStoldTable(connMgr, v_subj, v_year, v_subjseq);

            //    System.out.println("수료정보삭제"+isOk);

            //Student data를 Stold 테이블에 복사
            sql = "insert into tz_stold( ";
            sql += "		 	subj,year,subjseq,userid, ";
            sql += "			name, ";
            sql += "			comp, ";
            sql += "			dept, ";
            sql += "			deptname, ";
            sql += "			jik, ";
            sql += "			score,tstep,mtest,ftest,report,act,etc1,etc2,avtstep,avmtest,avftest,avreport, ";
            sql += "   		avact,avetc1,avetc2,isgraduated,isrestudy,isb2c, ";
            sql += "   		edustart, ";
            sql += "   		eduend, ";
            sql += "   		luserid,ldate ,htest,avhtest,notgraduetc) ";
            sql += "select 	subj,year,subjseq,userid, ";
            sql += "			(select name from tz_member where userid = a.userid), ";
            sql += "			comp, ";
            sql += "			'', ";
            sql += "			(select get_deptnm(deptnam, userid) from tz_member where userid = a.userid), ";
            sql += "			(select jikwi from tz_member where userid = a.userid), ";
            sql += "			score,tstep,mtest,ftest,report,act,etc1,etc2,avtstep,avmtest,avftest,avreport, ";
            sql += "   		avact,avetc1,avetc2,isgraduated,isrestudy,isb2c, ";
            sql += "   		(select edustart from tz_subjseq where subj=a.subj and year=a.year and subjseq=a.subjseq), ";
            sql += "   		(select eduend from tz_subjseq where subj=a.subj and year=a.year and subjseq=a.subjseq), ";
            sql += "   		luserid,to_char(sysdate, 'YYYYMMDDHH24MISS') ,htest,avhtest,notgraduetc ";
            sql += "from 	tz_student a  ";
            sql += "where subj = ? and year=? and subjseq=? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);

            isOk = pstmt.executeUpdate();

            //    System.out.println("데이타복사"+isOk);
            //System.out.println("isOk="+isOk);

            //수료처리 완료로 저장
            isOk = this.updateIsClosed(connMgr, v_subj, v_year, v_subjseq, "Y");

            //최종확인 컬럼 업데이트
            //    System.out.println("수료처리완료컬럼"+isOk);
            //System.out.println("isOk="+isOk);

            isOk = this.updateIsCpflag(connMgr, v_subj, v_year, v_subjseq, "Y");
            //    System.out.println("최종확인컬럼"+isOk);

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
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
     * 외주업체 과정 결과재요청
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateOutSubjReject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        //	System.out.println("v_subj = " + v_subj);
        //	System.out.println("v_year = " + v_year);
        //	System.out.println("v_subjseq = " + v_subjseq);

        try {
            connMgr = new DBConnectionManager();

            sql = "update tz_subjseq set iscpresult = 'N', iscpflag='N' where subj=? and year=? and subjseq=?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);

            isOk = pstmt.executeUpdate();

            //       System.out.println("isOk="+isOk);

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    /**
     * 집합과정 수료처리 업로드 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail(수강생 아님)
     **/
    @SuppressWarnings("unchecked")
    public int updateOffStudentScore(DBConnectionManager connMgr, Hashtable data) throws Exception {

        PreparedStatement pstmt = null;

        String sql = "";
        int isOk = 0;

        String v_subj = (String) data.get("p_subj");
        String v_year = (String) data.get("p_year");
        String v_subjseq = (String) data.get("p_subjseq");
        String v_userid = (String) data.get("p_userid");

        double v_tstep = ((Double) data.get("p_tstep")).doubleValue(); //출석률 점수
        double v_ftest = ((Double) data.get("p_ftest")).doubleValue(); //평가 점수
        double v_report = ((Double) data.get("p_report")).doubleValue(); //리포트 점수
        double v_etc1 = ((Double) data.get("p_etc1")).doubleValue(); //기타 점수

        try {

            sql = "update tz_student set tstep=?, ftest=?, report=?, etc1=? where subj=? and year=? and subjseq=? and userid=?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setDouble(1, v_tstep);
            pstmt.setDouble(2, v_ftest);
            pstmt.setDouble(3, v_report);
            pstmt.setDouble(4, v_etc1);

            pstmt.setString(5, v_subj);
            pstmt.setString(6, v_year);
            pstmt.setString(7, v_subjseq);
            pstmt.setString(8, v_userid);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 교육주관 과정차수(subjseqgr)를 과정차수(subjseq)로 리턴
     * 
     * @param connMgr,subj,year,grcode
     * @return subjseq
     **/
    public String selectGrSubjseq(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseqgr, String v_grcode) throws Exception {

        String sql = "";
        Statement stmt = null;
        ResultSet rs = null;

        String v_subjseq = "";

        try {

            stmt = connMgr.createStatement();

            sql = "select subjseq from tz_subjseq where subj = '" + v_subj + "' and year = '" + v_year + "' and subjseqgr = '" + v_subjseqgr + "' and grcode = '" + v_grcode + "' ";

            System.out.println("실제차수=" + sql);
            rs = stmt.executeQuery(sql);

            if (rs.next())
                v_subjseq = rs.getString(1);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }
        return v_subjseq;
    }

    /**
     * 수강생정보 수료증번호 UPDATE
     * 
     * @param connMgr receive from the form object and session
     * @param serno
     * @param subj
     * @param year
     * @param subjseq
     * @param userid
     * @return int
     */
    public int updateStudentSerno(DBConnectionManager connMgr, String s_serno, String v_subj, String v_year, String v_subjseq, String v_userid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {

            sql = "update TZ_STUDENT set serno = ? where subj=? and year=? and subjseq=? and userid=?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, s_serno);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_year);
            pstmt.setString(4, v_subjseq);
            pstmt.setString(5, v_userid);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 수료증번호 발급 처리
     * 
     * @param connMgr receive from the form object and session
     * @param subj
     * @param year
     * @param subjseq
     * @param userid
     * @return String
     */
    public String getCompleteSerno(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        String sserno = "";

        try {
            /*
             * // TZ_STUDENT serno is check sql = " select serno      "; sql+=
             * " from   tz_student "; sql+=
             * " where  subj='"+v_subj+"' and year='"
             * +v_year+"' and subjseq='"+v_subjseq
             * +"' and userid='"+v_userid+"'  ";
             * 
             * ls = connMgr.executeQuery(sql); if (ls.next()) sserno =
             * ls.getString("serno"); ls.close();
             * 
             * if (sserno.equals("")) { sserno = getMaxCompleteCode(connMgr,
             * v_subj, v_year, v_subjseq); }
             */
            sserno = getMaxCompleteCode(connMgr, v_subj, v_year, v_subjseq);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return sserno;
    }

    /**
     * 수료증번호 구하기(yyyy-MM-00000)
     * 
     * @param connMgr receive from the form object and session
     * @return String
     */
    public String getMaxCompleteCode(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq) throws Exception {

        String v_completecode = "";
        int v_maxcompletecode = 0;
        int v_maxno = 0;

        ListSet ls = null;
        String sql = "";

        String currdate = FormatDate.getDate("yyyyMM");

        try {
            /*
             * sql =
             * "select NVL(SUBSTR(eduend,1,6),to_char(sysdate, 'YYYYMM')) yymmdd from tz_subjseq "
             * ; sql+= "where  subj = '" + v_subj + "' and "; sql+=
             * "		  year = '" + v_year + "' and "; sql+= "		  subjseq = '" +
             * v_subjseq + "'";
             * 
             * ls = connMgr.executeQuery(sql);
             * 
             * if (ls.next()) { currdate = ls.getString("yymmdd"); } ls.close();
             */
            //수료증 번호 규칙 : 년+월+일련번호(5)(yyyy-MM-00001)
            //수료증 번호 규칙 : 년월(YYYYMM)-과정코드(4)-차수(2)-일련번호(4)
            //sql = "select max(substr(serno,9,13)) maxno ";
            //sql+= "from tz_stold ";
            //sql+= "where		  substr(serno,1,8) = '" + currdate + "-' ";

            //			sql = "select 	max(SUBSTR(serno,18,4)) maxno ";
            sql = "select 	'A' a1,count(*) as maxno ";
            sql += "from 	tz_stold ";
            sql += "where	subj = '" + v_subj + "' and ";
            sql += "			year = '" + v_year + "' and ";
            sql += "			subjseq = '" + v_subjseq + "' and ";
            sql += "        serno is not null";
            //			sql+= "		   SUBSTR(serno,1,14) = '" + currdate + "-" + v_subj + "-" + v_subjseq.substring(2,4) + "'";

            
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_maxcompletecode = ls.getInt("maxno");
            }

            if (v_maxcompletecode == 0) {
                v_completecode = currdate + "-" + v_subj + "-" + v_subjseq.substring(2, 4) + "-" + "0001";
            } else {
                v_maxno = Integer.valueOf(v_maxcompletecode).intValue();
                v_completecode = currdate + "-" + v_subj + "-" + v_subjseq.substring(2, 4) + "-" + new DecimalFormat("0000").format(v_maxno + 1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_completecode;
    }

    /**
     * tz_student isgraduated update
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int updateStudentIsgraduated(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_userid, String isgubun) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            sql = " update tz_student set isgraduated=? where subj=? and year=? and subjseq=? ";

            if (!v_userid.equals("")) {
                sql += "  and userid=? ";
            }

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, isgubun);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_year);
            pstmt.setString(4, v_subjseq);

            if (!v_userid.equals("")) {
                pstmt.setString(5, v_userid);
            }

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }
    
    /**
     * Student Eduend Update
     * @param connMgr
     * @param v_subj
     * @param v_year
     * @param v_subjseq
     * @param v_userid
     * @return
     * @throws Exception
     */
    public int updateStudentEduend(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_userid) throws Exception {
    	
    	PreparedStatement pstmt = null;
    	String sql = "";
    	int isOk = 0;
    	
    	try {
    		sql = " update tz_student set eduend=? where subj=? and year=? and subjseq=? ";
			sql += "\n  and userid=? ";
    		
    		pstmt = connMgr.prepareStatement(sql);
    		pstmt.setString(1, FormatDate.getDate("yyyyMMdd"));
    		pstmt.setString(2, v_subj);
    		pstmt.setString(3, v_year);
    		pstmt.setString(4, v_subjseq);
			pstmt.setString(5, v_userid);
    		
    		isOk = pstmt.executeUpdate();
    		
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex);
    		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
    	} finally {
    		if (pstmt != null) {
    			try {
    				pstmt.close();
    			} catch (Exception e) {
    			}
    		}
    	}
    	return isOk;
    }

    //직무과정일때 고유번호 구하기
    public String getCompleteSernoFunction(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        String sserno = "";
        String sn = "콘텐츠진흥원-초.중등-직무-" + v_year + v_subjseq + "-" + v_subj.toUpperCase() + "-";
        String completeCode = "";
        try {
            sql = "select 	nvl(max(right(serno,4)),0)+1 maxno ";
            sql += "from 	tz_stold ";
            sql += "where	subj = '" + v_subj + "' and ";
            sql += "			year = '" + v_year + "' and ";
            sql += "			subjseq = '" + v_subjseq + "' and ";
            sql += "       substr(serno,1,len(serno)-4)='" + sn + "'";

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                sserno = ls.getString("maxno");
            ls.close();

            int no = Integer.valueOf(sserno).intValue();
            completeCode = sn + new DecimalFormat("0000").format(no);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return completeCode;
    }

    /**
     * 일괄 수료처리 혹은 수료취소 루틴 // 일괄 점수재계산 추가
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    @SuppressWarnings("unchecked")
    public int jumpProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String v_totscorecal = box.getStringDefault("p_totscorecal", "N");
        int isOk = 0;

        
        Vector v_chk = box.getVector("chkbox");
        Enumeration em1 = v_chk.elements();

        try {
            connMgr = new DBConnectionManager();
            //            connMgr.setAutoCommit(false);
            while (em1.hasMoreElements()) {
                String v_checks = (String) em1.nextElement();
                
                StringTokenizer st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    String v_subj = (String) st1.nextToken();
                    String v_year = (String) st1.nextToken();
                    String v_subjseq = (String) st1.nextToken();
                    String v_tmp1 = (String) st1.nextToken();
                    String v_tmp2 = (String) st1.nextToken();
                    String v_tmp3 = (String) st1.nextToken();

                    v_subj = v_subj.replace("'", "").trim();
                    v_year = v_year.replace("'", "").trim();
                    v_subjseq = v_subjseq.replace("'", "").trim();
                    v_tmp1 = v_tmp1.replace("'", "").trim();
                    v_tmp2 = v_tmp2.replace("'", "").replace(" ", "").trim();
                    v_tmp3 = v_tmp3.replace("'", "").replace(" ", "").trim();

                    box.put("p_subj", v_subj);
                    box.put("p_year", v_year);
                    box.put("p_subjseq", v_subjseq);

                    if (v_totscorecal.equals("Y")) {
                        System.out.println("점수재계산으로 넘어간다=-===================>");
                        isOk = SubjectCompleteRerating(box); //점수 재계산
                    } else {
                        System.out.println("수료처리로 넘어간다.=-===================>");
                        if (v_tmp1.equals("subjectCompleteCancel")) //수료 취소 이면
                            isOk = SubjectCompleteCancel(box); //수료취소 루틴
                        else //수료처리 이면
                        {
                            if (v_tmp3.equals("N")) {
                                if (v_tmp1.equals("ON"))
                                    isOk = SubjectComplete(box); // 사이버
                                else
                                    isOk = OffSubjectCompleteNew(box); // 집합
                            } else {
                                isOk = updateOutSubjIsClosed(box); // 위탁
                                // String value4 = "listPage"; // 위탁
                            }
                        }
                    }
                }
            }

            //            if(isOk>0)
            //                connMgr.commit();
            //            else
            //                connMgr.rollback();
        } catch (Exception ex) {
            //			connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }
    
    /**
     * 대중문화예술지원 결과 값
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getKentResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        ArrayList list = new ArrayList();
        DataBox dbox = null;

        String sql = "";
        String startDate = box.getString("startDate");
        String endDate = box.getString("endDate");
        try {
            connMgr = new DBConnectionManager();

            sql = " /* com.credu.complete.FinishBean (대중문화예술 결과값 */                                        ";
            sql += "  select  b.userid, a.edustart, a.subj, a.subjseq, b.tstep, a.eduend,  decode( b.ISGRADUATED , 'Y', '1','2') isgraduated  ";
            sql += "  from tz_subjseq a, tz_student b, tz_subj c          ";
            sql += "  where a.subj = b.subj  ";
            sql += "  and a.year = b.year   ";
            sql += "  and a.subjseq = b.subjseq  ";
            sql += "  and a.subj = c.subj  ";
            sql += "  and a.grcode = 'N000113'  ";
            sql += "  and a.eduend between '"+ startDate+"' and '"+endDate+"' ";
            
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                    pstmt = null;
//                } catch (Exception e) {
//                }
//            }
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
}
