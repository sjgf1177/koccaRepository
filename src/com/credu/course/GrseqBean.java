//**********************************************************
//1. 제      목: 교육차수 OPERATION BEAN
//2. 프로그램명: GrseqBean.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 노희성 2004. 11. 14
//7. 수      정: 조재형 2008. 11. 18
//**********************************************************
package com.credu.course;

import java.io.File;
import java.sql.PreparedStatement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.common.GetCodenm;
import com.credu.exam.ExamPaperBean;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

import jxl.Sheet;
import jxl.Workbook;

public class GrseqBean {
    private boolean isTest = false;

    public GrseqBean() {
    }

    /**
     * 교육차수 구성리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육차수 구성데이터 (과정차수 및 코스차수정보) 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectGrseqScreenList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList list1 = null;
        String sql = "";//, sql2="";
        StringBuffer sql1 = new StringBuffer();
        DataBox dbox = null;
        String v_grcode = box.getString("s_grcode");
        String p_gyear = box.getString("s_gyear");
        String p_grseq = box.getString("s_grseq");

        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //과정&코스
        String ss_area = box.getStringDefault("p_area", "ALL"); //교육구분
        String ss_biyong = box.getStringDefault("pp_biyong", "ALL"); //유/무료

        box.put("v_area", ss_area);
        box.put("v_biyong", ss_biyong);

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            //지정된 과정/코스가 없는 교육차수Record Add
            sql = "\nselect gyear, grseq, grseqnm  from tz_grseq" + "\n where grcode=" + SQLString.Format(v_grcode) + "\n   and ( gyear + grseq not in (select distinct gyear + grseq from tz_subjseq where grcode=" + SQLString.Format(v_grcode) + ")"
                    + "\n )";
            if (!p_gyear.equals("ALL"))
                sql += "\n and gyear='" + p_gyear + "' ";
            if (!p_grseq.equals("ALL"))
                sql += "\n and grseq='" + p_grseq + "' ";
            sql += "\n order by gyear, grseq desc";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_grcode", v_grcode);
                dbox.put("d_grseqnm", GetCodenm.get_grseqnm(v_grcode, dbox.getString("d_gyear"), dbox.getString("d_grseq")));
                list1.add(dbox);
            }

            sql1.append(" select  a.grseqnm, a.gyear, a.grseq, b.subj, b.subjnm, b.year, b.subjseqgr                   ");
            sql1.append("         , b.subjseq, b.subjseqgr, b.propstart, b.propend, b.edustart                         ");
            sql1.append("         , b.eduend, b.studentlimit, b.course, c.coursenm, c.cyear                            ");
            sql1.append("         , c.courseseq, c.subjcnt, d.isonoff, b.biyong                                        ");
            sql1.append("         , case d.isonoff  When 'ON' Then '사이버' Else '집합'  End  as isonoffnm             ");
            sql1.append("         , ( select  count(*) CNTS from tz_subjseq                                            ");
            sql1.append("             where   grcode  = ").append(SQLString.Format(v_grcode)).append("                 ");
            sql1.append("             and     gyear   = b.gyear                                                        ");
            sql1.append("             and     grseq   = b.grseq                                                        ");
            if (!ss_subjcourse.equals("ALL")) {
                sql1.append("             and     subj    = ").append(SQLString.Format(ss_subjcourse)).append(" and isbelongcourse='N'  ");
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    sql1.append("             and     subj in ( select subj from TZ_SUBJ where 1=1                             ");
                    if (!ss_upperclass.equals("ALL"))
                        sql1.append(" and     upperclass  = ").append(SQLString.Format(ss_upperclass)).append(" ");
                    if (!ss_middleclass.equals("ALL"))
                        sql1.append(" and     middleclass = ").append(SQLString.Format(ss_middleclass)).append(" ");
                    if (!ss_lowerclass.equals("ALL"))
                        sql1.append(" and     lowerclass  = ").append(SQLString.Format(ss_lowerclass)).append(" ");
                    sql1.append(" ) ");
                }
            }
            sql1.append("         ) rowspan_grseq                                                                      ");
            sql1.append("         , ( select  count(*) CNTS                                                            ");
            sql1.append("             from    tz_propose                                                               ");
            sql1.append("             where   subj    = b.subj                                                         ");
            sql1.append("             and     year    = b.year                                                         ");
            sql1.append("             and     subjseq = b.subjseq                                                      ");
            sql1.append("             and     cancelkind not in ('F','P')                                              ");
            sql1.append("         ) cnt_propose                                                                        ");
            sql1.append("         , ( select  count(*) CNTS                                                            ");
            sql1.append("             from    tz_student                                                               ");
            sql1.append("             where   subj=b.subj                                                              ");
            sql1.append("             and     year=b.year                                                              ");
            sql1.append("             and     subjseq=b.subjseq                                                        ");
            sql1.append("         ) cnt_student                                                                        ");
            sql1.append("         , ( select  count(*) CNTS                                                            ");
            sql1.append("             from    tz_stold                                                                 ");
            sql1.append("             where   subj    = b.subj                                                         ");
            sql1.append("             and     year    = b.year                                                         ");
            sql1.append("             and     subjseq = b.subjseq                                                      ");
            sql1.append("         ) cnt_stold                                                                          ");
            sql1.append("         , b.autoconfirm, b.reviewdays                                                                      ");
            sql1.append(" from    tz_grseq a, tz_subjseq b, tz_courseseq c, tz_subj d                                  ");
            sql1.append(" where   a.grcode    = b.grcode(+) and a.gyear   = b.gyear(+) and a.grseq    = b.grseq(+)     ");
            sql1.append(" and     b.course    = c.course(+) and b.cyear   = c.cyear(+) and b.courseseq= c.courseseq(+) ");
            sql1.append(" and     b.grcode    = c.grcode(+) and b.gyear   = c.gyear(+) and b.grseq    = c.grseq(+)     ");
            sql1.append(" and     a.grcode    = ").append(SQLString.Format(v_grcode)).append("                         ");
            sql1.append(" and     b.subj      = d.subj(+)                                                              ");

            if (!p_gyear.equals("ALL"))
                sql1.append(" and     a.gyear    = ").append(SQLString.Format(p_gyear)).append("                           ");

            if (!p_grseq.equals("ALL"))
                sql1.append(" and     a.grseq    = ").append(SQLString.Format(p_grseq)).append("                           ");

            if (!ss_area.equals("ALL"))
                sql1.append(" and     d.area     = ").append(SQLString.Format(ss_area)).append("                           ");

            if (!ss_biyong.equals("ALL"))
                if (ss_biyong.equals("Y")) {
                    sql1.append(" and     b.biyong   > 0                                                                   ");
                } else {
                    sql1.append(" and     b.biyong   = 0                                                                   ");
                }

            if (!ss_subjcourse.equals("ALL")) {
                sql1.append(" and     d.subj  = ").append(SQLString.Format(ss_subjcourse)).append(" and isbelongcourse='N' ");

            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL"))
                        sql1.append(" and d.upperclass  = ").append(SQLString.Format(ss_upperclass)).append(" ");
                    if (!ss_middleclass.equals("ALL"))
                        sql1.append(" and d.middleclass = ").append(SQLString.Format(ss_middleclass)).append(" ");
                    if (!ss_lowerclass.equals("ALL"))
                        sql1.append(" and d.lowerclass  = ").append(SQLString.Format(ss_lowerclass)).append(" ");
                }
            }

            sql1.append(" order by b.gyear, b.grseq, c.courseseq desc, c.coursenm asc, c.course, b.subjnm, b.subj, b.edustart desc ");

            ls2 = connMgr.executeQuery(sql1.toString());

            while (ls2.next()) {
                dbox = ls2.getDataBox();
                dbox.put("d_grcode", v_grcode);

                list1.add(dbox);
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
        return list1;
    }

    /**
     * 교육차수 세부 구성리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차수 구성데이터 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectGrseqDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육주관
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //교육주관

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_grseq = box.getString("p_mgrseq");
        String v_subj = box.getString("p_subj");

        try {
            sql = " select distinct ";
            sql += "        a.subj, a.subjnm, a.subjseq, a.subjseqgr, a.year, a.propstart, a.propend, ";
            sql += "        a.edustart, a.eduend, a.studentlimit, ";
            sql += "        (select isonoff from tz_subj where subj=a.subj) isonoff, ";
            sql += "        (select count(*) from tz_propose where subj = a.subj and year = a.year and subjseq=a.subjseq) proposecnt,";
            sql += "        (select count(*) from tz_cancel  where subj = a.subj and year = a.year and subjseq=a.subjseq) cancelcnt,";
            sql += "        (select count(*) from tz_student where subj = a.subj and year = a.year and subjseq=a.subjseq) studentcnt,";
            sql += "        (select count(*) from tz_stold   where subj = a.subj and year = a.year and subjseq=a.subjseq and isgraduated = 'Y' ) stoldcnt ";
            sql += "   from tz_subjseq a  ";
            sql += "  where a.grcode = '" + ss_grcode + "' ";
            sql += "    and a.subj   = '" + v_subj + "' ";
            sql += "    and a.year   = '" + ss_gyear + "' ";
            sql += "    and a.grseq  = '" + v_grseq + "' ";

            if (v_orderColumn.equals("")) {
                sql += " order by 1,4";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);
            System.out.println("sql = " + sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj", ls.getString("subj"));
                dbox.put("subjnm", ls.getString("subjnm"));
                dbox.put("subjseq", ls.getString("subjseq"));
                dbox.put("subjseqgr", ls.getString("subjseqgr"));
                dbox.put("year", ls.getString("year"));
                dbox.put("propstart", ls.getString("propstart"));
                dbox.put("propend", ls.getString("propend"));
                dbox.put("edustart", ls.getString("edustart"));
                dbox.put("eduend", ls.getString("eduend"));
                dbox.put("isonoff", ls.getString("isonoff"));
                dbox.put("studentlimit", new Integer(ls.getInt("studentlimit")));
                dbox.put("d_proposecnt", new Integer(ls.getInt("proposecnt")));
                dbox.put("d_cancelcnt", new Integer(ls.getInt("cancelcnt")));
                dbox.put("d_studentcnt", new Integer(ls.getInt("studentcnt")));
                dbox.put("d_stoldcnt", new Integer(ls.getInt("stoldcnt")));

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
     * 교육차수데이타 조회
     * 
     * @param box receive from the form object and session
     * @return GrseqData
     **/
    public GrseqData SelectGrseqData(RequestBox box) throws Exception {
        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = box.getString("p_grseq");
        GrseqData data = null;

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            sql = "select grseqnm, props, prope, luserid, ldate, homepageyn " + "  from tz_grseq where grcode=" + SQLString.Format(v_grcode) + " and grseq=" + SQLString.Format(v_grseq) + " and gyear=" + SQLString.Format(v_gyear);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GrseqData();
                data.setGrcode(v_grcode);
                data.setGyear(v_gyear);
                data.setGrseq(v_grseq);
                data.setGrseqnm(ls.getString("grseqnm"));
                data.setProps(ls.getString("props"));
                data.setPrope(ls.getString("prope"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
                data.setHomepageyn(ls.getString("homepageyn"));
                break;
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
        return data;
    }

    /**
     * 새로운 교육차수 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int InsertGrseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        checkTest(box);

        PreparedStatement pstmt = null;
        ListSet ls = null;
        // String sql = "";
        StringBuilder sql = new StringBuilder();
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String s_grcode = box.getString("s_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = "";
        String v_makeOption = box.getString("p_makeoption");
        String v_luserid = box.getSession("userid");
        String v_homepageyn = box.getString("p_homepageyn"); //홈페이지 노출 여부
        String v_isCourse = "";
        String v_subjcourse = "";

        int v_sulpaper = box.getInt("p_sulpaper");
        String v_propstart = box.getString("p_propstart");
        String v_propend = box.getString("p_propend");
        String v_edustart = box.getString("p_edustart");
        String v_eduend = box.getString("p_eduend");
        int v_canceldays = box.getInt("p_canceldays");
        String v_startcanceldate = box.getString("p_startcanceldate");
        String v_endcanceldate = box.getString("p_endcanceldate");

        String v_sulpaper1 = box.getString("p_sulpaper1");
        String v_sulstartdate1 = box.getString("p_sulstartdate1");
        String v_sulenddate1 = box.getString("p_sulenddate1");
        String v_sulpaper2 = box.getString("p_sulpaper2");
        String v_sulstartdate2 = box.getString("p_sulstartdate2");
        String v_sulenddate2 = box.getString("p_sulenddate2");

        String v_copy_gyear = box.getString("p_copy_gyear");
        String v_copy_grseq = box.getString("p_copy_grseq");
        String v_charger = box.getString("p_charger");

        String v_isAlways = box.getString("p_isalways");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("SELECT  NVL(LTRIM(RTRIM(TO_CHAR(TO_NUMBER(MAX(GRSEQ))+1,'0000'))),'0001') AS GRS ");
            sql.append("  FROM  TZ_GRSEQ ");
            sql.append(" WHERE  GRCODE = '").append(v_grcode).append("' ");
            sql.append("   AND  GYEAR = '").append(v_gyear).append("' ");

            ls = connMgr.executeQuery(sql.toString());
            sql.setLength(0);

            if (ls.next()) {
                v_grseq = ls.getString("GRS");
            } else {
                v_grseq = "0001";
            }

            //insert TZ_Grseq table
            sql.append("INSERT  INTO    TZ_GRSEQ (  ");
            sql.append("        GRCODE  ");
            sql.append("    ,   GYEAR   ");
            sql.append("    ,   GRSEQ   ");
            sql.append("    ,   GRSEQNM ");
            sql.append("    ,   LUSERID ");
            sql.append("    ,   HOMEPAGEYN  ");
            sql.append("    ,   LDATE   ");
            sql.append("    ,   ISALWAYS ");
            sql.append(") VALUES (  ");
            sql.append("        ?   ");
            sql.append("    ,   ?   ");
            sql.append("    ,   ?   ");
            sql.append("    ,   ?   ");
            sql.append("    ,   ?   ");
            sql.append("    ,   ?   ");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')");
            sql.append("    ,   ?  ) ");

            pstmt = connMgr.prepareStatement(sql.toString());
            sql.setLength(0);

            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_gyear);
            pstmt.setString(3, v_grseq);
            pstmt.setString(4, box.getString("p_grseqnm"));
            pstmt.setString(5, v_luserid);
            pstmt.setString(6, v_homepageyn);
            pstmt.setString(7, v_isAlways);

            isOk = pstmt.executeUpdate();

            if (!v_makeOption.equals("MANUAL")) {
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }

                // 교육그룹에 등록된 과정 모두 일괄생성
                if (v_makeOption.equals("MAKE_ALL")) {
                    sql.append("SELECT  SUBJCOURSE  ");
                    sql.append("    ,   ISCOURSE    ");
                    sql.append("  FROM  TZ_GRSUBJ   ");
                    sql.append(" WHERE  GRCODE = '").append(v_grcode).append("' ");
                    ls = connMgr.executeQuery(sql.toString());

                    // 지정차수에 등록된 과정 모두 일괄생성
                } else if (v_makeOption.equals("SELECT_ALL")) {
                    sql.append("(   ");
                    sql.append("SELECT  SUBJ AS SUBJCOURSE  ");
                    sql.append("    ,   'N' AS ISCOURSE     ");
                    sql.append("  FROM  TZ_SUBJSEQ          ");
                    //sql += "  where grcode = " + SQLString.Format(v_grcode);	-- 2009/06/04 이전
                    sql.append(" WHERE  GRCODE = '").append(s_grcode).append("' ");
                    sql.append("   AND  GYEAR = '").append(v_copy_gyear).append("' ");
                    sql.append("   AND  GRSEQ = '").append(v_copy_grseq).append("' ");
                    sql.append("   AND  ISBELONGCOURSE = 'N'    ");
                    sql.append(")   ");
                    sql.append("UNION   ");
                    sql.append("(                                                       ");
                    sql.append("SELECT  COURSE AS SUBJCOURSE    ");
                    sql.append("    ,   'Y' AS ISCOURSE         ");
                    sql.append("  FROM  TZ_COURSESEQ            ");
                    //sql += "  where grcode = " + SQLString.Format(v_grcode);	-- 2009/06/04 이전
                    sql.append(" WHERE  GRCODE = '").append(s_grcode).append("' ");
                    sql.append("   AND  GYEAR = '").append(v_copy_gyear).append("' ");
                    sql.append("   AND  GRSEQ = '").append(v_copy_grseq).append("' ");
                    sql.append(")   ");

                    //Log.info.println("this sql -> " + sql);

                    ls = connMgr.executeQuery(sql.toString());
                }

                while (ls.next()) {
                    v_isCourse = ls.getString("iscourse");
                    v_subjcourse = ls.getString("subjcourse");
                    if (v_isCourse.equals("Y")) {
                        //CourseSeq Record 생성 (subjseq까지 생성)
                        isOk = makeCourseseq(v_grcode, v_gyear, v_grseq, v_subjcourse, v_luserid, v_sulpaper, v_propstart, v_propend, v_edustart, v_eduend, v_canceldays, v_copy_gyear, v_copy_grseq, connMgr, v_startcanceldate, v_endcanceldate, v_sulpaper1,
                                v_sulstartdate1, v_sulenddate1, v_sulpaper2, v_sulstartdate2, v_sulenddate2, v_charger);
                    } else {
                        //Subjseq Record 생성
                        isOk = makeSubjseq(v_grcode, v_gyear, v_grseq, "000000", "0000", "0000", v_subjcourse, v_luserid, v_sulpaper, v_propstart, v_propend, v_edustart, v_eduend, v_canceldays, v_copy_gyear, v_copy_grseq, connMgr, v_startcanceldate,
                                v_endcanceldate, v_sulpaper1, v_sulstartdate1, v_sulenddate1, v_sulpaper2, v_sulstartdate2, v_sulenddate2, v_charger);
                    }
                    if (isOk == 0) {
                        throw new Exception();
                    }
                }
            }

            if (isOk > 0) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
     * 선택된 교육차수코드 수정
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateGrseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = box.getString("p_grseq");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.
        String v_homepageyn = box.getString("p_homepageyn");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //insert TZ_Grseq table
            sql = "update TZ_GRSEQ set "
            	+ " 	GRSEQNM		= ?,"
            	+ " 	PROPS		= ?,"
            	+ " 	PROPE		= ?, "
            	+ " 	Luserid		= ?,"
            	+ " 	LDATE		= to_char(sysdate,'YYYYMMDDHH24MISS'),"
            	+ " 	HOMEPAGEYN	= ?"
            	+ " where"
            	+ "		grcode = ?"
            	+ "		and gyear=?"
            	+ "		and grseq=?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, box.getString("p_grseqnm"));
            pstmt.setString(2, box.getString("p_props"));
            pstmt.setString(3, box.getString("p_prope"));
            pstmt.setString(4, v_luserid);
            pstmt.setString(5, v_homepageyn);
            pstmt.setString(6, v_grcode);
            pstmt.setString(7, v_gyear);
            pstmt.setString(8, v_grseq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
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
     * 과정/코스 지정 화면
     * 
     * @param box receive from the form object and session
     * @return ArrayList 선택한 과정/코스 리스트
     **/
    @SuppressWarnings("unchecked")
    public ArrayList AssignedSubjCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null;
        String sql = "";
        ArrayList list1 = new ArrayList();
        DataBox dbox = null;

        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = box.getString("p_grseq");

        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //과정&코스

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            connMgr = new DBConnectionManager();

            // get CourseData
            sql = "\n (                                                                                                        ";
            sql += "\n select b.subjcourse, a.subjnm subjcoursenm, a.middleclass, b.iscourse, substring(a.ldate,1,4) ldateyear, ";
            sql += "\n        (select classname from tz_subjatt                                                                 ";
            sql += "\n          where upperclass=a.upperclass and middleclass=a.middleclass and lowerclass='000') classname     ";
            sql += "\n   from tz_subj a, tz_grsubj b                                                                            ";
            sql += "\n  where a.subj=b.subjcourse and grcode=" + SQLString.Format(v_grcode);
            if (!ss_subjcourse.equals("ALL")) {
                sql += "\n   and a.subj = " + SQLString.Format(ss_subjcourse);
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL"))
                        sql += "\n and a.upperclass = " + SQLString.Format(ss_upperclass);
                    if (!ss_middleclass.equals("ALL"))
                        sql += "\n and a.middleclass = " + SQLString.Format(ss_middleclass);
                    if (!ss_lowerclass.equals("ALL"))
                        sql += "\n and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
            }

            sql += "\n )                                                                                                          ";
            sql += "\n union                                                                                                      ";
            sql += "\n (                                                                                                          ";
            sql += "\n select b.subjcourse, a.coursenm subjcoursenm, a.middleclass, b.iscourse, substring(a.ldate,1,4) ldateyear, ";
            sql += "\n        (select classname from tz_subjatt                                                                   ";
            sql += "\n          where upperclass=a.upperclass and middleclass=a.middleclass and lowerclass='000') classname       ";
            sql += "\n   from tz_course a, tz_grsubj b                                                                            ";
            sql += "\n  where a.course = b.subjcourse and grcode=" + SQLString.Format(v_grcode);
            if (!ss_subjcourse.equals("ALL")) {
                sql += "\n   and a.subj = " + SQLString.Format(ss_subjcourse);
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL"))
                        sql += "\n and a.upperclass = " + SQLString.Format(ss_upperclass);
                    if (!ss_middleclass.equals("ALL"))
                        sql += "\n and a.middleclass = " + SQLString.Format(ss_middleclass);
                    if (!ss_lowerclass.equals("ALL"))
                        sql += "\n and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
            }

            sql += "\n )                                                                                                    ";

            if (v_orderColumn.equals("")) {
                sql += "\n order by middleclass, subjcoursenm ";
            } else {
                sql += "\n order by " + v_orderColumn + v_orderType;
            }

            System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_grcode", v_grcode);
                dbox.put("d_gyear", v_gyear);
                dbox.put("d_grseq", v_grseq);

                sql = "\nselect count(*) CNTS ";
                if (dbox.getString("d_iscourse").equals("Y"))
                    sql += "\n from tz_courseseq ";
                else
                    sql += "\n from tz_subjseq ";

                sql += "\n where grcode=" + SQLString.Format(v_grcode);
                sql += "\n   and grseq=" + SQLString.Format(v_grseq);
                sql += "\n   and gyear=" + SQLString.Format(v_gyear);

                if (dbox.getString("d_iscourse").equals("Y"))
                    sql += "\n and course=";
                else
                    sql += "\n and course = '000000' and subj=";

                sql += SQLString.Format(dbox.getString("d_subjcourse"));
                //System.out.println(sql);
                ls1 = connMgr.executeQuery(sql);
                if (ls1.next())
                    dbox.put("d_cnt_using", new Integer(ls1.getInt("CNTS")));

                list1.add(dbox);
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
     * 과정/코스 지정정보 저장(과정매핑시 기존 정보를 상속 받는다)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    @SuppressWarnings("unchecked")
    public int SaveAssign(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        checkTest(box);

        String sql = "";
        int isOk = 1;

        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = box.getString("p_grseq");
        Vector vec_chk = box.getVector("p_chk");
        String v_chk = "";
        String v_isCourse = "";
        String v_subjcourse = "";

        int v_sulpaper = box.getInt("p_sulpaper");
        String v_propstart = box.getString("p_propstart");
        String v_propend = box.getString("p_propend");
        String v_edustart = box.getString("p_edustart");
        String v_eduend = box.getString("p_eduend");
        int v_canceldays = box.getInt("p_canceldays");
        String v_startcanceldate = box.getString("p_startcanceldate");
        String v_endcanceldate = box.getString("p_endcanceldate");

        String v_sulpaper1 = box.getString("p_sulpaper1");
        String v_sulstartdate1 = box.getString("p_sulstartdate1");
        String v_sulenddate1 = box.getString("p_sulenddate1");
        String v_sulpaper2 = box.getString("p_sulpaper2");
        String v_sulstartdate2 = box.getString("p_sulstartdate2");
        String v_sulenddate2 = box.getString("p_sulenddate2");
        String v_charger = box.getString("p_charger");

        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            for (int i = 0; i < vec_chk.size(); i++) {
                v_chk = vec_chk.elementAt(i).toString();
                StringTokenizer st = new StringTokenizer(v_chk, "|");
                v_subjcourse = st.nextToken();
                v_isCourse = st.nextToken();

                if (v_isCourse.equals("Y")) {
                    //CourseSeq Record 생성 (subjseq까지 생성)
                    isOk = makeCourseseq(v_grcode, v_gyear, v_grseq, v_subjcourse, v_luserid, v_sulpaper, v_propstart, v_propend, v_edustart, v_eduend, v_canceldays, "", "", connMgr, v_startcanceldate, v_endcanceldate, v_sulpaper1, v_sulstartdate1,
                            v_sulenddate1, v_sulpaper2, v_sulstartdate2, v_sulenddate2, v_charger);
                } else {
                    //Subjseq Record 생성
                    isOk = makeSubjseq(v_grcode, v_gyear, v_grseq, "000000", "0000", "0000", v_subjcourse, v_luserid, v_sulpaper, v_propstart, v_propend, v_edustart, v_eduend, v_canceldays, "", "", connMgr, v_startcanceldate, v_endcanceldate, v_sulpaper1,
                            v_sulstartdate1, v_sulenddate1, v_sulpaper2, v_sulstartdate2, v_sulenddate2, v_charger);
                }
                System.out.println("과정차수 생성 : " + v_subjcourse);
                if (isOk == 0) {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
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
     * 과정 일괄 수정 화면
     * 
     * @param box receive from the form object and session
     * @return ArrayList 선택한 과정/코스 리스트
     **/
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ArrayList list1 = null;
        list1 = new ArrayList();
        DataBox dbox = null;

        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = box.getString("p_grseq");

        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //과정&코스

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            connMgr = new DBConnectionManager();

            // get CourseData
            sql = "\n select b.subjcourse, a.year scyear, a.subjseq scsubjseq, a.subjnm subjcoursenm, c.middleclass,        ";
            sql += "\n        a.propstart, a.propend, a.edustart, a.eduend, a.edulimit, a.isclosed,                          ";
            sql += "\n        (select classname from tz_subjatt                                                              ";
            sql += "\n          where upperclass=c.upperclass and middleclass=c.middleclass and lowerclass='000') classname  ";
            sql += "\n   from tz_subjseq a, tz_grsubj b, tz_subj c                                                           ";
            sql += "\n  where a.subj=b.subjcourse and a.subj = c.subj and isbelongcourse = 'N'                               ";
            sql += "\n    and a.grcode =" + SQLString.Format(v_grcode);
            sql += "\n    and a.gyear  =" + SQLString.Format(v_gyear);
            sql += "\n    and a.grseq  =" + SQLString.Format(v_grseq);

            if (!ss_subjcourse.equals("ALL")) {
                sql += "\n   and a.subj = " + SQLString.Format(ss_subjcourse);
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL"))
                        sql += "\n and c.upperclass = " + SQLString.Format(ss_upperclass);
                    if (!ss_middleclass.equals("ALL"))
                        sql += "\n and c.middleclass = " + SQLString.Format(ss_middleclass);
                    if (!ss_lowerclass.equals("ALL"))
                        sql += "\n and c.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
            }

            sql += "\n union                                                                                                 ";
            sql += "\n select b.subjcourse, a.cyear scyear, a.courseseq scsubjseq, a.coursenm subjcoursenm, c.middleclass,   ";
            sql += "\n        a.propstart, a.propend, a.edustart, a.eduend, 0 edulimit, a.isclosed,                          ";
            sql += "\n        (select classname from tz_subjatt                                                              ";
            sql += "\n          where upperclass=c.upperclass and middleclass=c.middleclass and lowerclass='000') classname  ";
            sql += "\n   from tz_courseseq a, tz_grsubj b, tz_course c                                                       ";
            sql += "\n  where a.course = b.subjcourse and a.course = c.course ";
            sql += "\n    and a.grcode=" + SQLString.Format(v_grcode);
            sql += "\n    and a.gyear  =" + SQLString.Format(v_gyear);
            sql += "\n    and a.grseq  =" + SQLString.Format(v_grseq);
            if (!ss_subjcourse.equals("ALL")) {
                sql += "\n   and a.course = " + SQLString.Format(ss_subjcourse);
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL"))
                        sql += "\n and c.upperclass = " + SQLString.Format(ss_upperclass);
                    if (!ss_middleclass.equals("ALL"))
                        sql += "\n and c.middleclass = " + SQLString.Format(ss_middleclass);
                    if (!ss_lowerclass.equals("ALL"))
                        sql += "\n and c.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
            }
            if (v_orderColumn.equals("")) {
                sql += "\n order by middleclass, subjcoursenm ";
            } else {
                sql += "\n order by " + v_orderColumn + v_orderType;
            }

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
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
        return list1;
    }
    
    /**
     * 교육차수 과정리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 선택한 과정/코스 리스트
     **/
    @SuppressWarnings("unchecked")
    public ArrayList searchSubjCourseList(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	String sql = "";
    	ArrayList list1 = new ArrayList();
    	DataBox dbox = null;
    	
    	String v_grcode = box.getString("p_grcode");
    	String v_gyear = box.getString("p_gyear");
    	String v_grseq = box.getString("p_grseq");
    	
    	String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
    	String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
    	String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
    	
    	String ss_subjsearchkey = box.getStringDefault("s_subjsearchkey", "");
    	
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		// get CourseData
    		sql = "\n select b.subjcourse, a.year scyear, a.subjseq scsubjseq, a.subjnm subjcoursenm        ";
    		sql += "\n   from tz_subjseq a, tz_grsubj b, tz_subj c                                                           ";
    		sql += "\n  where a.subj=b.subjcourse and a.subj = c.subj and isbelongcourse = 'N'                               ";
    		sql += "\n    and a.grcode =" + SQLString.Format(v_grcode);
    		sql += "\n    and a.gyear  =" + SQLString.Format(v_gyear);
    		sql += "\n    and a.grseq  =" + SQLString.Format(v_grseq);
    		if(!ss_subjsearchkey.equals("")){
    			sql += "\n and (   upper(b.subjcourse) like '%' || upper(" + SQLString.Format(ss_subjsearchkey) + ") || '%'";
    			sql += "\n      or a.subjnm like '%' || upper(" + SQLString.Format(ss_subjsearchkey) + ") || '%' )";
    		}
			if (!ss_upperclass.equals("ALL")){
				sql += "\n and c.upperclass = " + SQLString.Format(ss_upperclass);
			}
			if (!ss_middleclass.equals("ALL")){
				sql += "\n and c.middleclass = " + SQLString.Format(ss_middleclass);
			}
			if (!ss_lowerclass.equals("ALL")){
				sql += "\n and c.lowerclass = " + SQLString.Format(ss_lowerclass);
			}
    		
    		sql += "\n union                                                                                                 ";
    		sql += "\n select b.subjcourse, a.cyear scyear, a.courseseq scsubjseq, a.coursenm subjcoursenm   ";
    		sql += "\n   from tz_courseseq a, tz_grsubj b, tz_course c                                                       ";
    		sql += "\n  where a.course = b.subjcourse and a.course = c.course ";
    		sql += "\n    and a.grcode=" + SQLString.Format(v_grcode);
    		sql += "\n    and a.gyear  =" + SQLString.Format(v_gyear);
    		sql += "\n    and a.grseq  =" + SQLString.Format(v_grseq);
    		
    		if(!ss_subjsearchkey.equals("")){
    			sql += "\n and (   upper(b.subjcourse) like '%' || upper(" + SQLString.Format(ss_subjsearchkey) + ") || '%'";
    			sql += "\n      or upper(a.coursenm) like '%' || upper(" + SQLString.Format(ss_subjsearchkey) + ")|| '%' )";
    		}
    		
    		if (!ss_upperclass.equals("ALL")){
				sql += "\n and c.upperclass = " + SQLString.Format(ss_upperclass);
			}
			if (!ss_middleclass.equals("ALL")){
				sql += "\n and c.middleclass = " + SQLString.Format(ss_middleclass);
			}
			if (!ss_lowerclass.equals("ALL")){
				sql += "\n and c.lowerclass = " + SQLString.Format(ss_lowerclass);
			}
			sql += "\n order by subjcoursenm ";
    		
    		ls = connMgr.executeQuery(sql);
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			list1.add(dbox);
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
    	return list1;
    }

    /**
     * 지정된 과정차수에 일괄적으로 1일최대 학습량을 변경한다.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    @SuppressWarnings("unchecked")
    public int updateEdulimit(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1;

        Vector vec_chk = box.getVector("p_chk");
        String chk = "";

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_edulimit = box.getString("p_edulimit");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_SUBJSEQ set edulimit = ?, luserid = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS') ";
            sql += "\n  where subj = ? and year = ? and subjseq = ?";
            pstmt = connMgr.prepareStatement(sql);

            for (int i = 0; i < vec_chk.size(); i++) {
                chk = vec_chk.elementAt(i).toString();
                StringTokenizer st = new StringTokenizer(chk, ",");
                v_subj = st.nextToken();
                v_year = st.nextToken();
                v_subjseq = st.nextToken();

                // TZ_SUBJSEQ 수정
                pstmt.setString(1, v_edulimit);
                pstmt.setString(2, v_luserid);
                pstmt.setString(3, v_subj);
                pstmt.setString(4, v_year);
                pstmt.setString(5, v_subjseq);
                isOk = pstmt.executeUpdate();

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * 지정된 과정차수에 일괄적으로 수강신청기간을 변경한다.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    @SuppressWarnings("unchecked")
    public int updatePropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1;

        Vector vec_chk = box.getVector("p_chk");
        String chk = "";

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_propstart = box.getString("p_propstart");
        String v_propend = box.getString("p_propend");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_SUBJSEQ set propstart = ?, propend= ?, luserid = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS') ";
            sql += "\n  where subj = ? and year = ? and subjseq = ?";
            pstmt = connMgr.prepareStatement(sql);

            for (int i = 0; i < vec_chk.size(); i++) {
                chk = vec_chk.elementAt(i).toString();
                StringTokenizer st = new StringTokenizer(chk, ",");
                v_subj = st.nextToken();
                v_year = st.nextToken();
                v_subjseq = st.nextToken();

                // TZ_SUBJSEQ 수정
                pstmt.setString(1, v_propstart);
                pstmt.setString(2, v_propend);
                pstmt.setString(3, v_luserid);
                pstmt.setString(4, v_subj);
                pstmt.setString(5, v_year);
                pstmt.setString(6, v_subjseq);
                isOk = pstmt.executeUpdate();

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * 지정된 과정차수에 일괄적으로 학습기간을을 변경한다.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    @SuppressWarnings("unchecked")
    public int updateEdu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1;

        Vector vec_chk = box.getVector("p_chk");
        String chk = "";

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_edustart = box.getString("p_edustart");
        String v_eduend = box.getString("p_eduend");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.
        String v_grcode = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_SUBJSEQ set edustart = ?, eduend = ?, luserid = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS') ";
            sql += "\n  where subj = ? and year = ? and subjseq = ?";
            pstmt = connMgr.prepareStatement(sql);

            /*
             * if (v_edustart.equals("")) { v_edustart_value = "sysdate"; } else
             * { v_edustart_value = v_edustart.substring(0,8); v_edustart_value
             * = "to_date('" + v_edustart_value + "', 'YYYYMMDD')"; } if
             * (v_eduend.equals("")) { v_eduend_value = "sysdate"; } else {
             * v_eduend_value = v_eduend.substring(0,8); v_eduend_value =
             * "to_date('" + v_eduend_value + "', 'YYYYMMDD')"; }
             */

            for (int i = 0; i < vec_chk.size(); i++) {
                chk = vec_chk.elementAt(i).toString();
                StringTokenizer st = new StringTokenizer(chk, ",");
                v_subj = st.nextToken();
                v_year = st.nextToken();
                v_subjseq = st.nextToken();

                // TZ_SUBJSEQ 수정
                pstmt.setString(1, v_edustart);
                pstmt.setString(2, v_eduend);
                pstmt.setString(3, v_luserid);
                pstmt.setString(4, v_subj);
                pstmt.setString(5, v_year);
                pstmt.setString(6, v_subjseq);
                isOk = pstmt.executeUpdate();
                /**
                 * 자동메일 발송부분 시작 1. 과정 시작안내 2. 과정 종료안내 3. 과정 수료안내
                 */
                checkTest(box);
                setMail(v_subj, v_year, v_subjseq, v_grcode, v_edustart, v_eduend, connMgr);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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

    private void checkTest(RequestBox box) throws Exception {
        String connectedUrl = box.get("request.serverName");
        ConfigSet cs = new ConfigSet();
        String[] testBillList = cs.getProperty("inipay.testPay.list").split("|");
        for (String url : testBillList) {//2010.02.16 현재 localhost
            if (connectedUrl.indexOf(url) != -1)
                isTest = true;
        }
    }

    /**
     * 설명 : 설문, 수강신청취소기간, 운영담당자 저장
     * 
     * @param box : p_savetype에 따라 분기
     * @return
     * @throws Exception
     * @author swchoi
     */
    @SuppressWarnings("unchecked")
    public int updateOther(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1;

        Vector vec_chk = box.getVector("p_chk");
        String chk = "";

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_savetype = box.getString("p_savetype");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_SUBJSEQ set" + " sulpapernum= decode(?, '1', ?,sulpapernum),sulstartdate1= decode(?, '1', ?,sulstartdate1),sulenddate1= decode(?, '1', ?,sulenddate1),"
                    + " sulpapernum2= decode(?, '1', ?,sulpapernum2),sulstartdate2= decode(?, '1', ?,sulstartdate2),sulenddate2= decode(?, '1', ?,sulenddate2),"
                    + " startcanceldate= decode(?, '2', ?,startcanceldate), endcanceldate = decode(?, '2', ?,endcanceldate)," + " AUTOCONFIRM = decode(?, '3', ?,AUTOCONFIRM), " + " charger   = decode(?, '4', ?,charger), "
		            + " studentlimit= decode(?, '5', ?, studentlimit) ";
            sql += ", luserid = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS')\n  where subj = ? and year = ? and subjseq = ?";
            pstmt = connMgr.prepareStatement(sql);

            int index = 1;
            for (int i = 0; i < vec_chk.size(); i++) {
                chk = vec_chk.elementAt(i).toString();
                StringTokenizer st = new StringTokenizer(chk, ",");
                v_subj = st.nextToken();
                v_year = st.nextToken();
                v_subjseq = st.nextToken();

                index = 1;
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_sulpaper1")); // 수강신청취소시작일
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_sulstartdate1")); // 수강신청취소시작일
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_sulenddate1")); // 수강신청취소시작일
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_sulpaper2")); // 수강신청취소시작일
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_sulstartdate2")); // 수강신청취소시작일
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_sulenddate2")); // 수강신청취소시작일

                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_startcanceldate")); // 수강신청취소시작일
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_endcanceldate")); // 수강신청취소종료일
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_autoconfirm")); // 자동승인여부
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_charger")); // 운영자
                pstmt.setString(index++, v_savetype);
                pstmt.setString(index++, box.getString("p_studentlimit")); // 정원
                // TZ_SUBJSEQ 수정
                pstmt.setString(index++, v_luserid);
                pstmt.setString(index++, v_subj);
                pstmt.setString(index++, v_year);
                pstmt.setString(index++, v_subjseq);
                isOk = pstmt.executeUpdate();

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * 과정/코스 삭제 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int delSubjcourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 1;

        String v_process = box.getString("p_process");
        String v_subjcourse = box.getString("p_msubjcourse");
        String v_myear = box.getString("p_myear");
        String v_mseq = box.getString("p_mseq");
        String v_luserid = "session"; // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            if (v_process.equals("delCourse")) {
                isOk = delCourseseq(v_subjcourse, v_myear, v_mseq, v_luserid, connMgr);
            } else {
                isOk = delSubjseq(v_subjcourse, v_myear, v_mseq, v_luserid, connMgr);
            }
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            //if (isOk > 0) {connMgr.commit();}
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
        return 1;
    }

    /**
     * 과정/코스 삭제 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    @SuppressWarnings("unchecked")
    public int delSubjcourseArr(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 1;

        String v_luserid = "session"; // 세션변수에서 사용자 id를 가져온다.

        Vector v_check = new Vector();
        v_check = box.getVector("p_checks");
        Enumeration em = v_check.elements();

        StringTokenizer st1 = null;
        String v_checks = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            while (em.hasMoreElements()) {

                v_checks = (String) em.nextElement();

                st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    @SuppressWarnings("unused")
                    String f_grcode = st1.nextToken();
                    @SuppressWarnings("unused")
                    String f_gyear = st1.nextToken();
                    @SuppressWarnings("unused")
                    String f_grseq = st1.nextToken();
                    String f_subj = st1.nextToken();
                    String f_year = st1.nextToken();
                    String f_subjseq = st1.nextToken();
                    @SuppressWarnings("unused")
                    String f_isonoff = st1.nextToken();
                    isOk = delSubjseq(f_subj, f_year, f_subjseq, v_luserid, connMgr);
                    if (isOk < 1) {
                        break;
                    }
                    break;
                }
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            //if (isOk > 0) {connMgr.commit();}
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
        return 1;
    }

    /**
     * 과정/코스 차수 추가 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int addSubjcourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        checkTest(box);

        String sql = "";
        int isOk = 1;

        String v_process = box.getString("p_process");
        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_mgyear");
        String v_grseq = box.getString("p_mgrseq");
        String v_subjcourse = box.getString("p_msubjcourse");
        String v_luserid = "session"; // 세션변수에서 사용자 id를 가져온다.
        String v_startcanceldate = box.getString("p_startcanceldate");
        String v_endcanceldate = box.getString("p_endcanceldate");
        String v_charger = box.getString("p_charger");

        String v_sulpaper1 = box.getString("p_sulpaper1");
        String v_sulstartdate1 = box.getString("p_sulstartdate1");
        String v_sulenddate1 = box.getString("p_sulenddate1");
        String v_sulpaper2 = box.getString("p_sulpaper2");
        String v_sulstartdate2 = box.getString("p_sulstartdate2");
        String v_sulenddate2 = box.getString("p_sulenddate2");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            if (v_process.equals("addCourse")) {
                isOk = makeCourseseq(v_grcode, v_gyear, v_grseq, v_subjcourse, v_luserid, 0, "", "", "", "", 0, "", "", connMgr, v_startcanceldate, v_endcanceldate, v_sulpaper1, v_sulstartdate1, v_sulenddate1, v_sulpaper2, v_sulstartdate2, v_sulenddate2,
                        v_charger);
            } else {
                isOk = makeSubjseq(v_grcode, v_gyear, v_grseq, "000000", "0000", "0000", v_subjcourse, v_luserid, 0, "", "", "", "", 0, "", "", connMgr, v_startcanceldate, v_endcanceldate, v_sulpaper1, v_sulstartdate1, v_sulenddate1, v_sulpaper2,
                        v_sulstartdate2, v_sulenddate2, v_charger);
            }

            if (isOk == 0) {
                throw new Exception();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
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
     * 과정/코스 차수 추가 저장 - n개만큼 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int AddSubjSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        checkTest(box);

        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("s_grcode");
        String v_gyear = box.getString("s_gyear");

        String v_grseq = box.getString("p_mgrseq");
        String v_subj = box.getString("p_subj");
        int v_seqcnt = box.getInt("p_seqcnt"); //등록될 차수 개수
        String v_startcanceldate = box.getString("p_startcanceldate");
        String v_endcanceldate = box.getString("p_endcanceldate");

        String v_sulpaper1 = box.getString("p_sulpaper1");
        String v_sulstartdate1 = box.getString("p_sulstartdate1");
        String v_sulenddate1 = box.getString("p_sulenddate1");
        String v_sulpaper2 = box.getString("p_sulpaper2");
        String v_sulstartdate2 = box.getString("p_sulstartdate2");
        String v_sulenddate2 = box.getString("p_sulenddate2");
        String v_charger = box.getString("p_charger");

        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        System.out.println("v_grcode = " + v_grcode);
        System.out.println("v_gyear = " + v_gyear);
        System.out.println("v_subj = " + v_subj);
        System.out.println("v_seqcnt = " + v_seqcnt);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //v_grcode, v_gyear, v_grseq
            for (int i = 0; i < v_seqcnt; i++) {
                isOk += makeSubjseq(v_grcode, v_gyear, v_grseq, "000000", "0000", "0000", v_subj, v_luserid, 0, "", "", "", "", 0, "", "", connMgr, v_startcanceldate, v_endcanceldate, v_sulpaper1, v_sulstartdate1, v_sulenddate1, v_sulpaper2,
                        v_sulstartdate2, v_sulenddate2, v_charger);
            }

            System.out.println("iiiiiiiiiiiiiis ok = " + isOk);

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
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
     * 교육차수 삭제 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int delGrseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 1;
        ListSet ls = null;

        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = box.getString("p_grseq");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "select course, cyear, courseseq from tz_courseseq " + " where grcode=" + SQLString.Format(v_grcode) + "   and gyear=" + SQLString.Format(v_gyear) + "   and grseq=" + SQLString.Format(v_grseq);
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                isOk = delCourseseq(ls.getString("course"), ls.getString("cyear"), ls.getString("courseseq"), v_luserid, connMgr);
            }

            sql = "select subj, year, subjseq from tz_subjseq " + " where grcode=" + SQLString.Format(v_grcode) + "   and gyear=" + SQLString.Format(v_gyear) + "   and grseq=" + SQLString.Format(v_grseq);
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                isOk = delSubjseq(ls.getString("subj"), ls.getString("year"), ls.getString("subjseq"), v_luserid, connMgr);
            }

            sql = "delete from tz_grseq " + " where grcode=" + SQLString.Format(v_grcode) + "   and gyear=" + SQLString.Format(v_gyear) + "   and grseq=" + SQLString.Format(v_grseq);

            isOk = connMgr.executeUpdate(sql);

            if (isOk == 0) {
                throw new Exception();
            }

            if (isOk > 0) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
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
     * 과정차수데이터 조회
     * 
     * @param box receive from the form object and session
     * @return SubjseqData
     **/
    public SubjseqData SelectSubjseqData(RequestBox box) throws Exception {
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        SubjseqData data = null;

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls2 = null;
        String sql = "";

        try {
            sql = "select subj           ,year          ,subjseq       ,subjseqgr,grcode        ,gyear         ," + "       grseq          ,isbelongcourse,course        ,cyear         ,courseseq     ,"
                    + "       propstart      ,propend       ,edustart      ,eduend        ,isclosed      ," + "       isgoyong       ,ismultipaper  ,subjnm        ,luserid       ,ldate         ,"
                    + "       studentlimit   ,point         ,biyong        ,edulimit      ,warndays      ," + "       stopdays       ,gradscore     ,gradstep      ,wstep         ,wmtest        ,"
                    + "       wftest         ,wreport       ,wact          ,wetc1         ,wetc2         ," + "       endfirst       ,endfinal      ,proposetype, FILEDAPPLY,EDU_SUMTIME,BIGCOIN, PRIORITYCOIN,  "
                    + "       whtest         ,score         ,gradreport     ,gradexam      , " + "       isablereview   ,tsubjbudget   ,isusebudget    ,isessential   , "
                    + "       gradftest,gradhtest,isvisible,place,placejh, bookname, bookprice, sulpapernum, sulpapernum2, sulstartdate1, sulstartdate2, sulenddate1, sulenddate2, "
                    + "       canceldays, startcanceldate,endcanceldate,  isordinary, isworkshop, isunit, reviewdays, reviewtype, autoconfirm, charger, " + "		  gradetc1 " + "  from tz_subjseq where subj=" + SQLString.Format(v_subj) + " and year="
                    + SQLString.Format(v_year) + " and subjseq=" + SQLString.Format(v_subjseq);
            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                data = new SubjseqData();
                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setSubjseqgr(ls.getString("subjseqgr"));
                data.setGrcode(ls.getString("grcode"));
                data.setGyear(ls.getString("gyear"));
                data.setGrseq(ls.getString("grseq"));
                data.setIsbelongcourse(ls.getString("isbelongcourse"));
                data.setCourse(ls.getString("course"));
                data.setCyear(ls.getString("cyear"));
                data.setCourseseq(ls.getString("courseseq"));
                data.setPropstart(ls.getString("propstart"));
                data.setPropend(ls.getString("propend"));
                data.setEdustart(ls.getString("edustart"));
                data.setEduend(ls.getString("eduend"));
                data.setEndfirst(ls.getString("endfirst"));
                data.setEndfinal(ls.getString("endfinal"));
                data.setIsclosed(ls.getString("isclosed"));
                data.setIsgoyong(ls.getString("isgoyong"));
                data.setIsmultipaper(ls.getString("ismultipaper"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
                data.setStudentlimit(ls.getInt("studentlimit"));
                data.setPoint(ls.getInt("point"));
                data.setBiyong(ls.getInt("biyong"));
                data.setEdulimit(ls.getInt("edulimit"));
                data.setWarndays(ls.getInt("warndays"));
                data.setStopdays(ls.getInt("stopdays"));
                data.setGradscore(ls.getInt("gradscore"));
                data.setGradstep(ls.getInt("gradstep"));
                data.setWstep(ls.getDouble("wstep"));
                data.setWmtest(ls.getDouble("wmtest"));
                data.setWftest(ls.getDouble("wftest"));
                data.setWreport(ls.getDouble("wreport"));
                data.setWact(ls.getDouble("wact"));
                data.setWetc1(ls.getDouble("wetc1"));
                data.setWetc2(ls.getDouble("wetc2"));
                data.setProposetype(ls.getString("proposetype"));
                data.setGrcodenm(GetCodenm.get_grcodenm(data.getGrcode()));
                data.setGrseqnm(GetCodenm.get_grseqnm(data.getGrcode(), data.getGyear(), data.getGrseq()));

                data.setGradreport(ls.getInt("gradreport"));
                data.setGradexam(ls.getInt("gradexam"));
                data.setGradftest(ls.getInt("gradftest"));
                data.setGradhtest(ls.getInt("gradhtest"));
                data.setGradetc1(ls.getInt("gradetc1"));
                data.setWhtest(ls.getDouble("whtest"));
                data.setScore(ls.getInt("score"));

                data.setIsablereview(ls.getString("isablereview"));
                data.setTsubjbudget(ls.getInt("tsubjbudget"));
                data.setIsusebudget(ls.getString("isusebudget"));
                data.setIsessential(ls.getString("isessential"));
                data.setIsvisible(ls.getString("isvisible"));

                data.setPlace(ls.getString("place"));
                data.setPlacejh(ls.getString("placejh"));
                data.setBookname(ls.getString("bookname"));
                data.setBookprice(ls.getInt("bookprice"));
                data.setSulpapernum(ls.getInt("sulpapernum"));
                data.setSulpapernum2(ls.getInt("sulpapernum2"));

                data.setSulStartDate1(ls.getString("sulstartdate1"));
                data.setSulEndDate1(ls.getString("sulenddate1"));
                data.setSulStartDate2(ls.getString("sulstartdate2"));
                data.setSulEndDate2(ls.getString("sulenddate2"));

                data.setCanceldays(ls.getInt("canceldays"));
                data.setIsordinary(ls.getString("isordinary"));
                data.setIsworkshop(ls.getString("isworkshop"));
                data.setIsunit(ls.getString("isunit"));

                data.setReviewdays(ls.getInt("reviewdays"));
                data.setReviewtype(ls.getString("reviewtype"));
                data.setStartcanceldate(ls.getString("startcanceldate"));
                data.setEndcanceldate(ls.getString("endcanceldate"));
                data.setCharger(ls.getString("charger"));
                data.setAutoconfirm(ls.getString("autoconfirm"));
            }

            sql = "select isgoyong from tz_subj where subj=" + SQLString.Format(v_subj);
            ls2 = connMgr.executeQuery(sql);
            if (ls2.next()) {
                data.setSubj_isgoyong("N");
            } else {
                data.setSubj_isgoyong("N");
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
        return data;
    }

    /**
     * 과정차수정보 수정
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateSubjseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        checkTest(box);
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_subj = box.getString("p_subj"); //과정코드
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_propstart = box.getString("p_propstart"); //
        String v_propend = box.getString("p_propend"); //
        String v_edustart = box.getString("p_edustart"); //
        String v_eduend = box.getString("p_eduend"); //
        String v_endfirst = box.getString("p_endfirst"); //
        String v_endfinal = box.getString("p_endfinal"); //
        if (v_propstart.length() != 10)
            v_propstart = "";
        if (v_propend.length() != 10)
            v_propend = "";
        if (v_edustart.length() != 10)
            v_edustart = "";
        if (v_eduend.length() != 10)
            v_eduend = "";
        if (v_endfirst.length() != 10)
            v_endfirst = "";
        if (v_endfinal.length() != 10)
            v_endfinal = "";

        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();

            //update TZ_Grseq table
            sql = "update   tz_subjseq " + "   set   propstart   = ?," + "         propend     = ?," + "         edustart    = ?," + "         eduend      = ?," + "         endfirst    = ?," + "         endfinal    = ?," + "         isgoyong    = ?,"
                    + "         ismultipaper= ?," + "         subjnm      = ?," + "         luserid     = ?," + "         ldate       = to_char(sysdate,'YYYYMMDDHH24MISS')," + "         studentlimit= ?," + "         point       = ?,"
                    + "         biyong      = ?," + "         edulimit    = ?," + "         warndays    = ?," + "         stopdays    = ?," + "         gradscore   = ?," + "         gradstep    = ?," + "         wstep       = ?,"
                    + "         wmtest      = ?," + "         wftest      = ?," + "         wreport     = ?," + "         wact        = ?," + "         wetc1       = ?," + "         wetc2       = ?," + "         proposetype = ?,"
                    + "         gradexam    = ?," + "         gradreport  = ?," + "         whtest      = ?," + "         isablereview = ?, " + "         score        = ?, " + "         tsubjbudget  = ?, " + "         isessential  = ?, "
                    + "         gradftest    = ?, " + "         gradhtest    = ?, " + "         isvisible    = ?, " + "         place        = ?, " + "         placejh      = ?, " + "         sulpapernum  = ?, " + "         sulpapernum2  = ?, "
                    + "         sulstartdate1  = ?, " + "         sulenddate1  = ?, " + "         sulstartdate2  = ?, " + "         sulenddate2  = ?, " + "         canceldays   = ?, " + "         isordinary   = ?, " + "         isworkshop   = ?, "
                    + "         isunit       = ?,  " + "         reviewdays   = ?, " + "         reviewtype   = ?," + "         startcanceldate= ?, endcanceldate = ?,AUTOCONFIRM = ?, charger   = ?, " + "			gradetc1	= ?  " + "where    subj        = ?  "
                    + "  and    year        = ?  " + "  and    subjseq     = ?  ";
            System.out.println("sql" + sql);
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_propstart);
            pstmt.setString(2, v_propend);
            pstmt.setString(3, v_edustart);
            pstmt.setString(4, v_eduend);
            pstmt.setString(5, v_endfirst);
            pstmt.setString(6, v_endfinal);
            pstmt.setString(7, box.getString("p_isgoyong"));
            pstmt.setString(8, box.getStringDefault("p_ismultipaper", "N"));
            pstmt.setString(9, box.getString("p_subjnm"));
            pstmt.setString(10, v_luserid);
            pstmt.setInt(11, box.getInt("p_studentlimit"));
            pstmt.setInt(12, box.getInt("p_point"));
            pstmt.setInt(13, box.getInt("p_biyong"));
            pstmt.setInt(14, box.getInt("p_edulimit"));
            pstmt.setInt(15, box.getInt("p_warndays"));
            pstmt.setInt(16, box.getInt("p_stopdays"));
            pstmt.setInt(17, box.getInt("p_gradscore"));
            pstmt.setInt(18, box.getInt("p_gradstep"));
            pstmt.setInt(19, box.getInt("p_wstep"));
            pstmt.setInt(20, box.getInt("p_wmtest"));
            pstmt.setInt(21, box.getInt("p_wftest"));
            pstmt.setInt(22, box.getInt("p_wreport"));
            pstmt.setInt(23, box.getInt("p_wact"));
            pstmt.setInt(24, box.getInt("p_wetc1"));
            pstmt.setInt(25, box.getInt("p_wetc2"));
            pstmt.setInt(26, box.getInt("p_proposetype"));
            //------------------------------------------------------------------------------//
            pstmt.setInt(27, box.getInt("p_gradexam")); //이수기준(평가)
            pstmt.setInt(28, box.getInt("p_gradreport")); //이수기준(리포트)
            pstmt.setInt(29, box.getInt("p_whtest")); //가중치(형성평가)
            pstmt.setString(30, box.getString("p_isablereview")); //복습가능여부
            pstmt.setInt(31, box.getInt("p_score")); //학점배점
            pstmt.setInt(32, box.getInt("p_tsubjbudget")); //과정예산
            pstmt.setString(33, box.getStringDefault("p_isessential", "0")); //
            pstmt.setInt(34, box.getInt("p_gradftest")); //
            pstmt.setInt(35, box.getInt("p_gradhtest")); //
            pstmt.setString(36, box.getStringDefault("p_isvisible", "Y")); //학습자에게 보여주기
            pstmt.setString(37, box.getString("p_place")); //교육장소
            pstmt.setString(38, box.getString("p_placejh")); //집합장소
            pstmt.setInt(39, box.getInt("p_sulpaper")); // 설문지 번호
            pstmt.setInt(40, box.getInt("p_sulpaper2")); // 설문지 번호2

            pstmt.setString(41, box.getString("p_sulstartdate1")); // 설문시작일1
            pstmt.setString(42, box.getString("p_sulenddate1")); // 설문종료일1
            pstmt.setString(43, box.getString("p_sulstartdate2")); // 설문시작일2
            pstmt.setString(44, box.getString("p_sulenddate2")); // 설문종료일2

            pstmt.setInt(45, box.getInt("p_canceldays")); //취소날자
            pstmt.setString(46, box.getString("p_isordinary")); // 상시(Y) / 수시(N)
            pstmt.setString(47, box.getString("p_isworkshop")); // 워크샵(Y)
            pstmt.setString(48, box.getString("p_isunit")); // 학점인증(Y)
            pstmt.setString(49, box.getString("p_reviewdays")); // 복습기간
            pstmt.setString(50, box.getString("p_reviewtype")); // 학점인증(Y)

            pstmt.setString(51, box.getString("p_startcanceldate")); // 수강신청취소시작일
            pstmt.setString(52, box.getString("p_endcanceldate")); // 수강신청취소종료일
            pstmt.setString(53, box.getString("p_autoconfirm")); // 자동승인여부
            pstmt.setString(54, box.getString("p_charger")); // 운영자

            pstmt.setString(55, box.getString("p_gradetc1")); // 이수기준 : 참여도

            pstmt.setString(56, v_subj);
            pstmt.setString(57, v_year);
            pstmt.setString(58, v_subjseq);

            isOk = pstmt.executeUpdate();

            setMail(v_subj, v_year, v_subjseq, box.getString("s_grcode"), v_edustart, v_eduend, connMgr);
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
     * 코스차수 생성
     * 
     * @param String p_grcode 교육그룹 String p_gyear 교육연도 String p_grseq 교육차수
     *        String p_course 코스코드 DBConnectionManager conn DB Connection
     *        Manager
     * @return isOk 1:make success,0:make fail
     **/
    public int makeCourseseq(String p_grcode, String p_gyear, String p_grseq, String p_course, String p_userid, int p_sulpaper, String p_propstart, String p_propend, String p_edustart, String p_eduend, int p_canceldays, String p_copy_gyear,
            String p_copy_grseq, DBConnectionManager conn, String v_startcanceldate, String v_endcanceldate, String v_sulpaper1, String v_sulstartdate1, String v_sulenddate1, String v_sulpaper2, String v_sulstartdate2, String v_sulenddate2,
            String v_charger) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;
        String v_courseseq = "", v_subj = "";

        if (p_propstart.length() != 10)
            p_propstart = "";
        if (p_propend.length() != 10)
            p_propend = "";
        if (p_edustart.length() != 10)
            p_edustart = "";
        if (p_eduend.length() != 10)
            p_eduend = "";

        try {

            sql = "select ltrim(rtrim(to_char(to_number(isnull(max(courseseq),'0000'))+1, '0000'))) GRS " + "  from tz_courseseq " + " where course=" + SQLString.Format(p_course) + "   and cyear=" + SQLString.Format(p_gyear);

            ls = conn.executeQuery(sql);
            if (ls.next())
                v_courseseq = ls.getString("GRS");
            else
                v_courseseq = "0001";
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            if (p_copy_gyear.equals("") || p_copy_grseq.equals("")) {
                //최근의 코스차수 정보를 가져오고
                sql = " select   *          ";
                sql += "\n   from tz_courseseq ";
                sql += "\n  where course='" + p_course + "' and grcode = '" + p_grcode + "' and gyear = '" + p_gyear + "'  ";
                sql += "\n    and courseseq = (select max(courseseq) from tz_courseseq where course='" + p_course + "' and cyear = '" + p_gyear + "')";
            } else {
                //지정된 코스차수 정보를 가져오고
                sql = " select * ";
                sql += "\n   from tz_courseseq ";
                sql += "\n  where course='" + p_course + "' and grcode = '" + p_grcode + "' and gyear = '" + p_copy_gyear + "' ";
                sql += "\n    and grseq = '" + p_copy_grseq + "'";
            }

            ls = conn.executeQuery(sql);
            if (ls.next()) {

            } else {
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }

                //없으면 코스정보에서 상속받는다.
                sql = "select * from tz_course where course=" + SQLString.Format(p_course);
                ls = conn.executeQuery(sql);
                ls.next();
            }

            sql = " insert into tz_courseseq (" + " course     , cyear      , courseseq  , grcode     , gyear," + " grseq      , coursenm   , gradscore  , gradfailcnt, biyong," + " subjcnt    , propstart  , propend    , edustart   , eduend, "
                    + " indate, startcanceldate, endcanceldate     ,luserid    , ldate )  values (  " + " ?,?,?,?,?,  ?,?,?,?,?, ?,?,?,?,?, to_char(sysdate,'YYYYMMDDHH24MISS')," + " ?,?,?, to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p_course);
            pstmt.setString(2, p_gyear);
            pstmt.setString(3, v_courseseq);
            pstmt.setString(4, p_grcode);
            pstmt.setString(5, p_gyear);
            pstmt.setString(6, p_grseq);
            pstmt.setString(7, ls.getString("coursenm"));
            pstmt.setInt(8, ls.getInt("gradscore"));
            pstmt.setInt(9, ls.getInt("gradfailcnt"));
            pstmt.setInt(10, ls.getInt("biyong"));
            pstmt.setInt(11, ls.getInt("subjcnt"));
            pstmt.setString(12, p_propstart);
            pstmt.setString(13, p_propend);
            pstmt.setString(14, p_edustart);
            pstmt.setString(15, p_eduend);
            pstmt.setString(16, v_startcanceldate);
            pstmt.setString(17, v_endcanceldate);
            pstmt.setString(18, p_userid);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                sql = "select subj from tz_coursesubj where course=" + SQLString.Format(p_course);
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = conn.executeQuery(sql);
                while (ls.next()) {
                    v_subj = ls.getString("subj");
                    isOk = makeSubjseq(p_grcode, p_gyear, p_grseq, p_course, p_gyear, v_courseseq, v_subj, p_userid, p_sulpaper, p_propstart, p_propend, p_edustart, p_eduend, p_canceldays, p_copy_gyear, p_copy_grseq, conn, v_startcanceldate,
                            v_endcanceldate, v_sulpaper1, v_sulstartdate1, v_sulenddate1, v_sulpaper2, v_sulstartdate2, v_sulenddate2, v_charger);
                    if (isOk == 0)
                        throw new Exception();
                }
            }

        } catch (Exception ex) {
            isOk = 0;
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
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 과정차수 생성 - 과정정보를 상속 받는다.
     * 
     * @param String p_grcode 교육그룹 String p_gyear 교육연도 String p_grseq 교육차수
     *        String p_course 코스코드 DBConnectionManager conn DB Connection
     *        Manager
     * @return isOk 1:make success,0:make fail
     **/
    public int makeSubjseq(String p_grcode, String p_gyear, String p_grseq, String p_course, String p_cyear, String p_courseseq, String p_subj, String p_userid, int p_sulpaper, String p_propstart, String p_propend, String p_edustart, String p_eduend,
            int p_canceldays, String p_copy_gyear, String p_copy_grseq, DBConnectionManager conn, String v_startcanceldate, String v_endcanceldate, String v_sulpaper1, String v_sulstartdate1, String v_sulenddate1, String v_sulpaper2,
            String v_sulstartdate2, String v_sulenddate2, String v_charger) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;
        String v_subjseq = "", v_isBelongCourse = "Y";
        String v_subjseqgr = ""; //교육그룹에 속한 과정차수
        String v_expiredate = "";

        PreparedStatement cp_pstmt = null;
        PreparedStatement cp_pstmt2 = null;
        // String cp_sql = "";
        // zint cp_isOk = 0;

        if (p_propstart.length() != 10){
        	if(!isDate(p_propstart)){
        		p_propstart = "";
            }else{
            	p_propstart += "00";
            }
        }
            
        if (p_propend.length() != 10){
        	if(!isDate(p_propend)){
        		p_propend = "";
            }else{
            	p_propend += "23";
            }        	
        }
        
        if (p_edustart.length() != 10){
        	if(!isDate(p_edustart)){
        		p_edustart = "";
            }else{
            	p_edustart += "00";
            }         	
        }
        
        if (p_eduend.length() != 10){
        	if(!isDate(p_eduend)){
        		p_eduend = "";
            }else{
            	p_eduend += "23";
            }           	
        }

        if (p_course.equals("000000"))
            v_isBelongCourse = "N";

        try {

            // 년도별 과정차수를 가지고 온다.
            sql = "select ltrim(rtrim(to_char(to_number(isnull(max(subjseq),'0000'))+1, '0000'))) GRS " + "  from tz_subjseq " + " where subj='" + p_subj + "' and year='" + p_gyear + "' ";

            ls = conn.executeQuery(sql);
            if (ls.next())
                v_subjseq = ls.getString("GRS");
            else
                v_subjseq = "0001";
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            //년도별 과정,교육그룹에 따른 과정차수를 가져온다.
            sql = "select ltrim(rtrim(to_char(to_number(isnull(max(subjseqgr),'0000'))+1, '0000'))) GRS " + "  from tz_subjseq " + " where subj=" + SQLString.Format(p_subj) + " and year=" + SQLString.Format(p_gyear) + " and grcode="
                    + SQLString.Format(p_grcode);

            ls = conn.executeQuery(sql);
            if (ls.next())
                v_subjseqgr = ls.getString("GRS");
            else
                v_subjseqgr = "0001";

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            if (p_copy_gyear.equals("") || p_copy_grseq.equals("")) {
                //최근의 과정차수 정보를 가져오고
                sql = " select    a.*       ";
                sql += "\n   from tz_subjseq a ";
                sql += "\n  where subj='" + p_subj + "' and grcode = '" + p_grcode + "' and gyear = '" + p_gyear + "'  ";
                sql += "\n    and subjseq = (select max(subjseq) from tz_subjseq where subj='" + p_subj + "' and year = '" + p_gyear + "')";
            } else {
                //지정된 과정차수 정보를 가져오고
                sql = " select   a.* ";
                sql += "\n   from     tz_subjseq a ";
                sql += "\n  where    subj='" + p_subj + "' and grcode = '" + p_grcode + "' and gyear = '" + p_copy_gyear + "'  ";
                sql += "\n    and    grseq = '" + p_copy_grseq + "'";
            }

            ls = conn.executeQuery(sql);
            if (ls.next()) {

            } else {
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }

                //없으면 과정정보에서 상속받는다.
                System.out.println("과정 정보에서 상속받는다.");
                sql = "select * from tz_subj where subj=" + SQLString.Format(p_subj);
                ls = conn.executeQuery(sql);
                ls.next();
            }

            sql = " insert into tz_subjseq (" + " SUBJ          , YEAR          , SUBJSEQ       , GRCODE        , GYEAR,      " + " GRSEQ         , ISBELONGCOURSE, COURSE        , CYEAR         , COURSESEQ,  "
                    + " ISCLOSED      , SUBJNM        , STUDENTLIMIT  , POINT         , BIYONG,     " + " ISGOYONG      , EDULIMIT      , ISMULTIPAPER  , WARNDAYS      , STOPDAYS,   "
                    + " GRADSCORE     , GRADSTEP      , WSTEP         , WMTEST        , WFTEST,     " + " WREPORT       , WACT          , WETC1         , WETC2         , LUSERID,    " + " LDATE         , PROPOSETYPE   , SUBJSEQGR ,"
                    + " score, isablereview," + " gradexam,gradreport,whtest,isessential,gradftest,gradhtest, place, placejh, bookname,bookprice, "
                    + " sulpapernum, propstart,propend, edustart, eduend, canceldays, isoutsourcing, reviewdays, reviewtype, startcanceldate, endcanceldate "
                    + " ,sulstartdate1,sulenddate1,sulpapernum2,sulstartdate2,sulenddate2, charger,AUTOCONFIRM) values ( " + " ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  " + " to_char(sysdate,'YYYYMMDDHH24MISS'),?,?,"
                    + " ?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_gyear);
            pstmt.setString(3, v_subjseq);
            pstmt.setString(4, p_grcode);
            pstmt.setString(5, p_gyear);
            pstmt.setString(6, p_grseq);
            pstmt.setString(7, v_isBelongCourse);
            pstmt.setString(8, p_course);
            pstmt.setString(9, p_cyear);
            pstmt.setString(10, p_courseseq);
            pstmt.setString(11, "N");
            pstmt.setString(12, ls.getString("subjnm"));
            pstmt.setInt(13, ls.getInt("studentlimit"));
            pstmt.setInt(14, ls.getInt("point"));
            pstmt.setInt(15, ls.getInt("biyong"));
            pstmt.setString(16, ls.getString("isgoyong"));
            pstmt.setInt(17, ls.getInt("edulimit"));
            pstmt.setString(18, "N");
            pstmt.setInt(19, ls.getInt("warndays"));
            pstmt.setInt(20, ls.getInt("stopdays"));
            pstmt.setInt(21, ls.getInt("gradscore"));
            pstmt.setInt(22, ls.getInt("gradstep"));
            pstmt.setInt(23, ls.getInt("wstep"));
            pstmt.setInt(24, ls.getInt("wmtest"));
            pstmt.setInt(25, ls.getInt("wftest"));
            pstmt.setInt(26, ls.getInt("wreport"));
            pstmt.setInt(27, ls.getInt("wact"));
            pstmt.setInt(28, ls.getInt("wetc1"));
            pstmt.setInt(29, ls.getInt("wetc2"));
            pstmt.setString(30, p_userid);
            pstmt.setString(31, ls.getString("proposetype"));
            pstmt.setString(32, v_subjseqgr);

            pstmt.setInt(33, ls.getInt("score"));
            pstmt.setString(34, ls.getString("isablereview"));

            pstmt.setInt(35, ls.getInt("gradexam"));
            pstmt.setInt(36, ls.getInt("gradreport"));
            pstmt.setInt(37, ls.getInt("whtest"));
            pstmt.setString(38, ls.getString("isessential"));
            pstmt.setInt(39, ls.getInt("gradftest"));
            pstmt.setInt(40, ls.getInt("gradhtest"));
            pstmt.setString(41, ls.getString("place"));
            pstmt.setString(42, ls.getString("placejh"));
            pstmt.setString(43, ls.getString("bookname"));
            pstmt.setString(44, ls.getString("bookprice"));

            pstmt.setString(45, v_sulpaper1);//p_sulpaper);
            pstmt.setString(46, p_propstart);
            pstmt.setString(47, p_propend);
            pstmt.setString(48, p_edustart);
            pstmt.setString(49, p_eduend);
            pstmt.setInt(50, p_canceldays);
            pstmt.setString(51, ls.getString("isoutsourcing"));
            pstmt.setInt(52, ls.getInt("reviewdays"));
            pstmt.setString(53, ls.getString("reviewtype"));
            pstmt.setString(54, v_startcanceldate);
            pstmt.setString(55, v_endcanceldate);
            //        pstmt.setString(56 , v_sulpaper1);
            pstmt.setString(56, v_sulstartdate1);
            pstmt.setString(57, v_sulenddate1);
            pstmt.setString(58, v_sulpaper2);
            pstmt.setString(59, v_sulstartdate2);
            pstmt.setString(60, v_sulenddate2);
            pstmt.setString(61, v_charger);
            pstmt.setString(62, ls.getString("autoconfirm"));
            //System.out.println(" isoutsourcing" + ls.getString("isoutsourcing"));

            isOk = pstmt.executeUpdate();
            //System.out.println("차수등록 : " + isOk );

            /**
             * 자동메일 발송부분 시작 1. 과정 시작안내 2. 과정 종료안내 3. 과정 수료안내
             */
            //setMail(p_subj, p_gyear, v_subjseq, p_grcode, p_edustart, p_eduend, conn);

            /**
             * 자동메일 발송부분 종료
             */

            // 2008.09.23 리포트 출제기간 설정
            String v_deadlinesdate = "";
            String v_deadlineedate = "";

            // 학습종료일이 있을경우 레포트마감일시를 종료일로 세팅
            if (p_eduend.length() >= 8) {
                v_expiredate = StringManager.substring(p_eduend, 0, 8);

                v_deadlinesdate = StringManager.substring(p_edustart, 0, 8);
                v_deadlineedate = StringManager.substring(p_eduend, 0, 8);
            } else {
                v_expiredate = "";
            }

            if (isOk > 0) {
                System.out.println("Report 복사 : " + isOk);
                // Report 출제정보 Copy;
                sql = " insert into tz_projord (                                        " + " SUBJ       ,YEAR       ,SUBJSEQ    ,PROJSEQ, ORDSEQ     ,lesson      ,    " + " REPTYPE    ,ISOPEN     ,ISOPENscore,TITLE      ,CONTENTS   ,    "
                        + " score      ,EXPIREDATE ,deadlinesdate, deadlineedate, UPFILE     ,UPFILE2    ,LUSERID    ,    " + " LDATE      ,realfile, realfile2, upfilezise, upfilesize2, ansyn, useyn  ) "
                        + " select                                                          " + "     SUBJ       ," + SQLString.Format(p_gyear) + "," + SQLString.Format(v_subjseq) + ",PROJSEQ,ORDSEQ,lesson,"
                        + "     REPTYPE    ,ISOPEN     ,ISOPENscore,TITLE      ,CONTENTS   ," + "     score      , '" + v_expiredate + "', '" + v_deadlinesdate + "', '" + v_deadlineedate + "' ,UPFILE     ,UPFILE2    ," + SQLString.Format(p_userid) + ","
                        + "     to_char(sysdate,'YYYYMMDDHH24MISS')                         " + "     ,realfile, realfile2, upfilezise, upfilesize2, ansyn, useyn " + " from tz_projord                                                 " + " where subj="
                        + SQLString.Format(p_subj);
                if (p_copy_gyear.equals("") || p_copy_grseq.equals("")) {

                    //최근의 과정차수 정보를 가져오고
                    // 수정일 : 05.11.16 수정자 : 이나연 _ || 수정
                    //              sql += "\n   and year||subjseq=(select max(year||subjseq) from tz_projord where subj="+SQLString.Format(p_subj)+")";
                    
                    sql += "\n   and year+subjseq=(select max(year+subjseq) from tz_projord where subj=" + SQLString.Format(p_subj) + ") and year = "+ SQLString.Format(p_gyear) +" ";
                } else {
                    //지정된 과정차수 정보를 가져오고
                    sql += "\n   and year = '" + p_copy_gyear + "' and subjseq = '" + p_copy_grseq + "' ";
                }
                /*
                 * + "   and year="+SQLString.Format(p_gyear) +
                 * "   and subjseq=( select  max(subjseq) " +
                 * "                 from    tz_subjseq " +
                 * "                 where   subj="+SQLString.Format(p_subj) +
                 * "                         and year="
                 * +SQLString.Format(p_gyear) +
                 * "                         and grcode="
                 * +SQLString.Format(p_grcode) +
                 * "                         and subjseq != " +
                 * SQLString.Format(v_subjseq) + "  )";
                 */
                isOk = conn.executeUpdate(sql);
                System.out.println("리포트" + sql);
                isOk = 1;

                // 평가문제지 Copy;
                ExamPaperBean exambean = new ExamPaperBean();
                isOk = exambean.insertExamPaper(p_subj, p_gyear, v_subjseq, p_userid);
                isOk = 1;
                System.out.println("평가문제지" + sql);
                // 집합강좌 Copy;
                sql = " insert into tz_offsubjlecture (                                     " + " SUBJ       ,YEAR       ,SUBJSEQ    ,lecture, lectdate,lectsttime,    " + " lecttime    ,sdesc,tutorid,lectscore,lectlevel,    " + " luserid      ,ldate )"
                        + " select                                                          " + "     SUBJ       ,"
                        + SQLString.Format(p_gyear)
                        + ","
                        + SQLString.Format(v_subjseq)
                        + "     ,lecture, lectdate,lectsttime,    "
                        + " lecttime    ,sdesc,tutorid,lectscore,lectlevel,"
                        + SQLString.Format(p_userid)
                        + ","
                        + "     to_char(sysdate,'YYYYMMDDHH24MISS')                         "
                        + " from tz_offsubjlecture                                              "
                        + " where subj="
                        + SQLString.Format(p_subj)
                        + "   and year="
                        + SQLString.Format(p_gyear)
                        + "   and subjseq=( select  max(subjseq) "
                        + "                 from    tz_subjseq "
                        + "                 where   subj="
                        + SQLString.Format(p_subj)
                        + "                         and year="
                        + SQLString.Format(p_gyear) + "                         and grcode=" + SQLString.Format(p_grcode) + "                         and subjseq != " + SQLString.Format(v_subjseq) + "  )";

                isOk = conn.executeUpdate(sql);
                System.out.println("집합강좌" + sql);
                isOk = 1;
            }

        } catch (Exception ex) {
            isOk = 0;
            ex.printStackTrace();
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
                } catch (Exception e) {
                }
            }
            if (cp_pstmt != null) {
                try {
                    cp_pstmt.close();
                } catch (Exception e) {
                }
            }
            if (cp_pstmt2 != null) {
                try {
                    cp_pstmt2.close();
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 1. 과정 시작안내 2. 과정 종료안내 3. 과정 수료안내
     * 
     * @param String p_course 코스코드 String p_cyear 코스연도 String p_courseseq 코스차수
     *        String p_userid ID DBConnectionManager conn DB Connection Manager
     * @return isOk 1:make success,0:make fail
     **/
    private int setMail(String p_subj, String p_gyear, String v_subjseq, String p_grcode, String p_edustart, String p_eduend, DBConnectionManager conn) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;
        //테스트 접속일 때는 이메일 발송을 하지 않는다.
        if (isTest) {
            return 1;
        }

        try {
            int index = 1;
            /**
             * 자동메일 발송부분 시작 0. 기존 메일 모두 제거
             */
            sql = "DELETE WISEU.NVAUTOMAIL WHERE GRCODE = ? AND SUBJ = ? AND YEAR = ? AND SUBJSEQ = ? AND ECARE_NO IN ('1', '2', '11')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(index++, p_grcode);
            pstmt.setString(index++, p_subj);
            pstmt.setString(index++, p_gyear);
            pstmt.setString(index++, v_subjseq);
            isOk = pstmt.executeUpdate();
            pstmt.close();

            /**
             * 1. 과정 시작안내
             */
            index = 1;
            sql = "INSERT INTO WISEU.NVAUTOMAIL (\n	GRCODE, SUBJ, YEAR, SUBJSEQ, ECARE_NO,SEND_CD, STARTEND_DT, DEGREE, TID, SEND_NM, SEND_DT, SEND_STS\n) \n"
                    + "VALUES (\n   ?, \n   ?, \n   ?, \n   ?, \n   '1' AS ECARE_NO,   'A01' AS SEND_CD, \n   ? AS STARTEND_DT, \n   '0' AS DEGREE, \n   NULL AS TID, \n   '학습시작' AS SEND_NM, \n   ? -3 AS SEND_DT, \n   'R' AS SEND_STS\n)\n";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(index++, p_grcode);
            pstmt.setString(index++, p_subj);
            pstmt.setString(index++, p_gyear);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, p_edustart);
            pstmt.setString(index++, p_edustart);
            isOk = pstmt.executeUpdate();
            pstmt.close();
            /**
             * 2. 과정 종료안내
             */
            index = 1;
            sql = "INSERT INTO WISEU.NVAUTOMAIL (\n	GRCODE, SUBJ, YEAR, SUBJSEQ, ECARE_NO,SEND_CD, STARTEND_DT, DEGREE, TID, SEND_NM, SEND_DT, SEND_STS\n) \n"
                    + "VALUES (\n   ?, \n   ?, \n   ?, \n   ?, \n   '2' AS ECARE_NO,\n   'A02' AS SEND_CD, \n   ? AS STARTEND_DT,  /* 과정종료일 : YYYYMMDD	*/\n   '0' AS DEGREE, \n   NULL AS TID, \n   '학습종료' AS SEND_NM, \n   ? AS SEND_DT,    /* 학습종료일 : YYYYMMDD */\n   'R' AS SEND_STS\n)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(index++, p_grcode);
            pstmt.setString(index++, p_subj);
            pstmt.setString(index++, p_gyear);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, p_eduend);
            pstmt.setString(index++, p_eduend);
            isOk = pstmt.executeUpdate();
            pstmt.close();
            /**
             * 3. 과정 수료안내
             */
            index = 1;
            sql = "INSERT INTO WISEU.NVAUTOMAIL (\n	GRCODE, SUBJ, YEAR, SUBJSEQ, ECARE_NO,SEND_CD, STARTEND_DT, DEGREE, TID, SEND_NM, SEND_DT, SEND_STS\n) \n"
                    + "VALUES (\n   ?, \n   ?, \n   ?, \n   ?, \n   '11' AS ECARE_NO,\n   'A11' AS SEND_CD, \n   ? AS STARTEND_DT,  /* 과정종료일 : YYYYMMDD	*/\n   '0' AS DEGREE, \n   NULL AS TID, \n   '학습종료' AS SEND_NM, \n   ? +7 AS SEND_DT,    /* 학습종료일 : YYYYMMDD */\n   'R' AS SEND_STS\n)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(index++, p_grcode);
            pstmt.setString(index++, p_subj);
            pstmt.setString(index++, p_gyear);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, p_eduend);
            pstmt.setString(index++, p_eduend);
            isOk = pstmt.executeUpdate();
            pstmt.close();

            if (isOk == 0)
                throw new Exception();

        } catch (Exception ex) {
            isOk = 0;
            ex.printStackTrace();
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
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 코스차수 삭제
     * 
     * @param String p_course 코스코드 String p_cyear 코스연도 String p_courseseq 코스차수
     *        String p_userid ID DBConnectionManager conn DB Connection Manager
     * @return isOk 1:make success,0:make fail
     **/
    public int delCourseseq(String p_course, String p_cyear, String p_courseseq, String p_userid, DBConnectionManager conn) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        try {
            sql = "select a.subj, b.year, b.subjseq, isrequired from tz_coursesubj a, tz_subjseq b " + " where a.subj=b.subj and a.course=b.course and a.course=" + SQLString.Format(p_course) + "   and b.cyear=" + SQLString.Format(p_cyear)
                    + " and b.courseseq=" + SQLString.Format(p_courseseq);
            ls = conn.executeQuery(sql);
            while (ls.next()) {
                isOk = delSubjseq(ls.getString("subj"), ls.getString("year"), ls.getString("subjseq"), p_userid, conn);
                if (isOk == 0)
                    throw new Exception();
            }

            if (isOk > 0) {
                sql = "delete tz_courseseq where course=? and cyear=? and courseseq=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, p_course);
                pstmt.setString(2, p_cyear);
                pstmt.setString(3, p_courseseq);
                isOk = pstmt.executeUpdate();
            }

            if (isOk == 0)
                throw new Exception();

        } catch (Exception ex) {
            isOk = 0;
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
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }

    /**
     * 과정차수 삭제
     * 
     * @param String p_subj 과정코드 String p_year 연도 String p_subjseq 과정차수 String
     *        p_userid ID DBConnectionManager conn DB Connection Manager
     * @return isOk 1:make success,0:make fail
     * 
     *         Process: delete tz_propose, tz_student, tz_stold (==> must be
     *         Backup) delete tz_progress, tz_progressobj,
     *         tz_progress_sco,tz_vodprogress, tz_projord, tz_projrep,
     *         tz_examresult, tz_exampaper, tz_activity_ans, tz_opin, tz_expect,
     *         tz_gong, tz_toron, tz_torontp, tz_qna, tz_objqna, tz_class,
     *         tz_classtutor, tz_sulresult, tz_sulresultp, tz_suleach,
     *         tz_bookmark,
     **/
    @SuppressWarnings("unchecked")
    public int delSubjseq(String p_subj, String p_year, String p_subjseq, String p_userid, DBConnectionManager conn) throws Exception {

        String sql = "";
        int isOk = 0;
        ArrayList sqlArr = new ArrayList();

        sqlArr.add("tz_progress");
        //sqlArr.add("tz_progressobj");
        //sqlArr.add("tz_progress_sco");
        //sqlArr.add("tz_vodprogress");
        sqlArr.add("tz_projord");
        sqlArr.add("tz_projrep");
        sqlArr.add("tz_examresult");
        sqlArr.add("tz_exampaper");
        //sqlArr.add("tz_activity_ans");
        //sqlArr.add("tz_opin");
        //sqlArr.add("tz_expect");
        sqlArr.add("tz_gong");
        sqlArr.add("tz_toron");
        sqlArr.add("tz_torontp");
        //sqlArr.add("tz_qna");
        //sqlArr.add("tz_objqna");
        sqlArr.add("tz_class");
        sqlArr.add("tz_classtutor");
        //sqlArr.add("tz_sulresult");
        //sqlArr.add("tz_sulresultp");
        sqlArr.add("tz_suleach");
        //sqlArr.add("tz_bookmark");
        sqlArr.add("tz_propose");
        sqlArr.add("tz_student");
        sqlArr.add("tz_stold");
        //    sqlArr.add("tz_subjseqtemp");
        sqlArr.add("tz_subjseq");
        sqlArr.add("tz_offsubjlecture");
        if (isTest)
            sqlArr.add("WISEU.NVAUTOMAIL");

        //  BackUp 정책에 따라 Backup 및 Logging Process추가할 것.

        try {

            // 관련 테이블 삭제
            for (int i = 0; i < sqlArr.size(); i++) {
                sql = "delete  " + (String) sqlArr.get(i) + " where subj=" + StringManager.makeSQL(p_subj) + "   and year=" + StringManager.makeSQL(p_year) + "   and subjseq=" + StringManager.makeSQL(p_subjseq);

                isOk += conn.executeUpdate(sql);
            }
        } catch (Exception ex) {
            isOk = 0;
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }

        return isOk;
    }

    /**
     * 집합과정 강좌 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectLectureList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        String sql1 = "";
        OffSubjLectureData data1 = null;
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            //select lecture,sdesc,lectdate,lectsttime,tutorid,tutorname
            sql1 = "select lecture,sdesc,lectdate,lectsttime,tutorid, ";
            sql1 += "\n(select name from TZ_TUTOR where userid = A.tutorid) as tutorname ";
            sql1 += "\n from TZ_OFFSUBJLECTURE A ";
            sql1 += "\nwhere subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
            sql1 += "\n and subjseq=" + SQLString.Format(v_subjseq);
            sql1 += "\n order by lecture";
            //        System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new OffSubjLectureData();
                data1.setLecture(ls1.getString("lecture"));
                data1.setSdesc(ls1.getString("sdesc"));
                data1.setLectdate(ls1.getString("lectdate"));
                data1.setLectsttime(ls1.getString("lectsttime"));
                data1.setTutorid(ls1.getString("tutorid"));
                data1.setTutorname(ls1.getString("tutorname"));
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

    /**
     * 강사 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectTutorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        String sql1 = "";
        OffSubjLectureData data1 = null;
        String v_subj = box.getString("p_subj"); //과정
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            //select userid,name
            sql1 = " select A.userid,A.name ";
            sql1 += "\n from TZ_TUTOR A,TZ_SUBJMAN B ";
            sql1 += "\n where B.subj=" + SQLString.Format(v_subj) + " and A.userid=B.userid and B.gadmin='P1' ";
            sql1 += "\n order by A.name";
            //        System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new OffSubjLectureData();
                data1.setTutorid(ls1.getString("userid"));
                data1.setTutorname(ls1.getString("name"));
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

    /**
     * 강좌 등록
     * 
     * @param box receive from the form object and session
     * @return int 1:insert success,0:insert fail
     */
    public int insertLecture(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        ListSet ls1 = null;
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_lectdate = box.getString("p_lectdate");
        String v_lectsttime1 = box.getString("p_lectsttime1");
        String v_lectsttime2 = box.getString("p_lectsttime2");
        String v_lectsttime = v_lectsttime1 + v_lectsttime2;
        String v_lecttime = box.getString("p_lecttime");
        String v_sdesc = box.getString("p_sdesc");
        String v_tutorid = box.getString("p_tutorid");
        int v_lecture = 0;
        try {
            connMgr = new DBConnectionManager();

            //select max(lecture)
            sql1 = "select isnull(max(lecture), 0) from TZ_OFFSUBJLECTURE ";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                v_lecture = ls1.getInt(1) + 1;
            }

            //insert TZ_OFFSUBJLECTURE table
            sql2 = "insert into TZ_OFFSUBJLECTURE(subj,year,subjseq,lecture,lectdate,lectsttime,lecttime,sdesc,tutorid,luserid,ldate) ";
            sql2 += "values (?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setInt(4, v_lecture);
            pstmt2.setString(5, v_lectdate);
            pstmt2.setString(6, v_lectsttime);
            pstmt2.setString(7, v_lecttime);
            pstmt2.setString(8, v_sdesc);
            pstmt2.setString(9, v_tutorid);
            pstmt2.setString(10, v_user_id);
            isOk = pstmt2.executeUpdate();
            Log.info.println(this, box, "insert TZ_OFFSUBJLECTURE subj=" + v_subj + ",year=" + v_year + ",subjseq" + v_subjseq + ",lecture" + v_lecture);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
     * 집합과정 강좌 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public OffSubjLectureData selectLecture(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        OffSubjLectureData data1 = null;
        String v_year = box.getString("p_year"); //년도
        String v_subj = box.getString("p_subj"); //과정
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_lecture = box.getString("p_lecture"); //과정 차수
        try {
            connMgr = new DBConnectionManager();
            //select lecture,lectdate,lectsttime,lecttime,sdesc,tutorid,tutorname
            sql1 = "select lecture,lectdate,lectsttime,lecttime,sdesc,tutorid, ";
            sql1 += "\n(select name from TZ_TUTOR where userid = A.tutorid) as tutorname ";
            sql1 += "\n from TZ_OFFSUBJLECTURE A ";
            sql1 += "\nwhere subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
            sql1 += "\n and subjseq=" + SQLString.Format(v_subjseq) + " and lecture=" + SQLString.Format(v_lecture);
            sql1 += "\n order by lecture";
            //        System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new OffSubjLectureData();
                data1.setLecture(ls1.getString("lecture"));
                data1.setLectdate(ls1.getString("lectdate"));
                data1.setLectsttime(ls1.getString("lectsttime"));
                data1.setLecttime(ls1.getString("lecttime"));
                data1.setSdesc(ls1.getString("sdesc"));
                data1.setTutorid(ls1.getString("tutorid"));
                data1.setTutorname(ls1.getString("tutorname"));
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
        return data1;
    }

    /**
     * 집합과정 강좌 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateLecture(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        String v_lectdate = box.getString("p_lectdate");
        String v_lectsttime1 = box.getString("p_lectsttime1");
        String v_lectsttime2 = box.getString("p_lectsttime2");
        String v_lectsttime = v_lectsttime1 + v_lectsttime2;
        String v_lecttime = box.getString("p_lecttime");
        String v_sdesc = box.getString("p_sdesc");
        String v_tutorid = box.getString("p_tutorid");
        int v_lecture = box.getInt("p_lecture");
        try {
            connMgr = new DBConnectionManager();

            //update TZ_OFFSUBJLECTURE table
            sql1 = "update TZ_OFFSUBJLECTURE set lectdate=?,lectsttime=?,lecttime=?,sdesc=?,tutorid=?,luserid=?,ldate=to_char(sysdate,'YYYYMMDDHH24MISS') ";
            sql1 += "where subj=? and year=? and subjseq=? and lecture=?";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_lectdate);
            pstmt1.setString(2, v_lectsttime);
            pstmt1.setString(3, v_lecttime);
            pstmt1.setString(4, v_sdesc);
            pstmt1.setString(5, v_tutorid);
            pstmt1.setString(6, v_user_id);
            pstmt1.setString(7, v_subj);
            pstmt1.setString(8, v_year);
            pstmt1.setString(9, v_subjseq);
            pstmt1.setInt(10, v_lecture);

            isOk = pstmt1.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
     * 집합과정 강좌 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteLecture(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk = 0;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정 차수
        int v_lecture = box.getInt("p_lecture");
        try {
            connMgr = new DBConnectionManager();

            //delete TZ_OFFSUBJLECTURE table
            sql1 = "delete from TZ_OFFSUBJLECTURE ";
            sql1 += "where subj=? and year=? and subjseq=? and lecture=?";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_subj);
            pstmt1.setString(2, v_year);
            pstmt1.setString(3, v_subjseq);
            pstmt1.setInt(4, v_lecture);

            isOk = pstmt1.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
     * 지정된 교육차수에 일괄적으로 1일최대 학습량을 변경한다.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int SaveEdulimit(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls1 = null;
        int isOk = 1;

        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_grseq = box.getString("p_grseq");

        String v_edulimit = box.getString("p_edulimit");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_SUBJSEQ set edulimit = ?, luserid = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS') ";
            sql += "\n  where grcode = ? and gyear = ? and grseq = ?";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_edulimit);
            pstmt.setString(2, v_luserid);
            pstmt.setString(3, v_grcode);
            pstmt.setString(4, v_gyear);
            pstmt.setString(5, v_grseq);

            isOk = pstmt.executeUpdate();

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
     * 과정차수정보(학습창에서 사용)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox getSubjInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox = null;
        String v_year = box.getString("FIELD2"); //년도
        String v_subj = box.getString("FIELD3"); //과정
        String v_subjseq = box.getString("FIELD4"); //과정 차수

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select subj, year, subjseq, subjnm, eduurl, isoutsourcing,   ";
            sql1 += "\n       cpsubj, cpsubjseq, contenttype                        ";
            sql1 += "\n from VZ_SCSUBJSEQ                                           ";
            sql1 += "\n where subj    = " + SQLString.Format(v_subj);
            sql1 += "\n   and year    = " + SQLString.Format(v_year);
            sql1 += "\n   and subjseq = " + SQLString.Format(v_subjseq);
            System.out.println("sql1============>" + sql1);
            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) {
                dbox = ls1.getDataBox();
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
        return dbox;
    }

    /**
     * 설문 리스트 SELECT BOX 구성
     * 
     * @param name SELECT BOX NAME
     * @param selected 선택된 설문
     * @param allcheck 처음안내문구 여부 : 0 -선택없음, 1 - 선택있음
     * @return String SELECT BOX 구성 문자
     **/
    public String selectSulPaper(String name, String selected, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = "";
        String sql = "";
        String code = "";
        String codenm = "";

        result = "  <SELECT name=" + name + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>======= 설문지를 선택하세요 ======</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select sulpapernum, sulpapernm from tz_sulpaper ";
            // sql += "\n  where subj = 'ALL' and grcode = 'ALL'          ";
            sql += "\n  where subj = 'ALL' and grcode = '" + selected + "' ";
            sql += "\n order by sulpapernum desc                        ";
            //System.out.println("sql==>"+ sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                code = ls.getString("sulpapernum");
                codenm = ls.getString("sulpapernm");

                result += " <option value='" + code + "'";
                if (selected.equals(code)) {
                    result += " selected ";
                }

                result += ">" + codenm + "</option> \n";
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 설문 리스트 SELECT BOX 구성(GRCODE 인자추가) 2005.12.30
     * 
     * @param name SELECT BOX NAME
     * @param selected 선택된 설문
     * @param allcheck 처음안내문구 여부 : 0 -선택없음, 1 - 선택있음
     * @return String SELECT BOX 구성 문자
     **/
    public String selectSulPaper(String name, String grcode, String selected, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = "";
        String sql = "";
        String code = "";
        String codenm = "";

        result = "  <SELECT name=" + name + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>======= 설문지를 선택하세요 ======</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select sulpapernum, sulpapernm from tz_sulpaper  \n";
            sql += "\n  where subj = 'ALL'                              \n";
            sql += "\n  and grcode = '" + grcode + "'                       \n";
            sql += "\n order by sulpapernum desc                         \n";

            //System.out.println("sql==>"+ sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                code = ls.getString("sulpapernum");
                codenm = ls.getString("sulpapernm");

                result += " <option value='" + code + "'";
                if (selected.equals(code)) {
                    result += " selected ";
                }

                result += ">" + codenm + "</option> \n";
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 복사대상 교육차수용 교육년도
     * 
     * @param grcode 교유그룹
     * @param name SELECT BOX NAME
     * @param selected 선택된 년도
     * @param change change시 자바함수
     * @param allcheck 처음안내문구 여부 : 0 -선택없음, 1 - 선택있음
     * @return String SELECT BOX 구성 문자
     **/
    public String selectGyear(String grcode, String name, String selected, String change, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = "";
        String sql = "";
        String code = "";
        String codenm = "";

        result = "  <SELECT name=" + name + " " + change + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>==선택==</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select distinct gyear ";
            sql += "\n   from tz_grseq       ";
            sql += "\n   where grcode = " + SQLString.Format(grcode);
            sql += "\n  order by gyear desc  ";
            //System.out.println("sql==>"+ sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                code = ls.getString("gyear");
                codenm = ls.getString("gyear");

                result += " <option value='" + code + "'";
                if (selected.equals(code)) {
                    result += " selected ";
                }

                result += ">" + codenm + "</option> \n";
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 복사대상 교육차수용 교육년도
     * 
     * @param grcode 교육그룹
     * @param gyear 교육년도
     * @param name SELECT BOX NAME
     * @param selected 선택된 년도
     * @param change change시 자바함수
     * @param allcheck 처음안내문구 여부 : 0 -선택없음, 1 - 선택있음
     * @return String SELECT BOX 구성 문자
     **/
    public String selectGrseq(String grcode, String gyear, String name, String selected, String change, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = "";
        String sql = "";
        String code = "";
        String codenm = "";

        result = "  <SELECT name=" + name + " " + change + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=========선택=========</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select grseq, grseqnm ";
            sql += "\n from tz_grseq       ";
            //        if(!gyear.equals("")){
            sql += "\n where gyear = " + SQLString.Format(gyear);
            //        }else{
            //          sql += "\n where gyear = (select max(gyear) from tz_grseq where grcode = " + SQLString.Format(grcode)+")";
            //        }
            sql += "\n and grcode = " + SQLString.Format(grcode);
            sql += "\n order by grseq";

            //System.out.println("sql==>"+ sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                code = ls.getString("grseq");
                codenm = ls.getString("grseqnm");

                result += " <option value='" + code + "'";
                if (selected.equals(code)) {
                    result += " selected ";
                }

                result += ">" + codenm + "</option> \n";
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 과정/차수 코드 리스트 (입과일괄처리에서 다운로드)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차수 코드 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList getSubjCodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //교육년도

        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류

        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        try {
            sql = " select subj, subjnm, subjseq, subjseqgr, year ";
            sql += "\n   from vz_scsubjseq   ";
            sql += "\n  where 1=1          ";
            if (!ss_grcode.equals("ALL"))
                sql += "\n and grcode = " + SQLString.Format(ss_grcode);
            if (!ss_grseq.equals("ALL"))
                sql += "\n and grseq = " + SQLString.Format(ss_grseq);
            if (!ss_gyear.equals("ALL"))
                sql += "\n and year = " + SQLString.Format(ss_gyear);
            if (!ss_uclass.equals("ALL"))
                sql += "\n and scupperclass = " + SQLString.Format(ss_uclass);
            if (!ss_mclass.equals("ALL"))
                sql += "\n and scmiddleclass = " + SQLString.Format(ss_mclass);
            if (!ss_lclass.equals("ALL"))
                sql += "\n and sclowerclass = " + SQLString.Format(ss_lclass);
            if (!ss_subjcourse.equals("ALL"))
                sql += "\n and scsubj = " + SQLString.Format(ss_subjcourse);
            if (!ss_subjseq.equals("ALL"))
                sql += "\n and scsubjseq = " + SQLString.Format(ss_subjseq);

            sql += "\n order by subj,subjseqgr";

            connMgr = new DBConnectionManager();
            list = new ArrayList();
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
     * 과정차수정보 수정
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateSubjScore(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer updateSql = new StringBuffer();
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            updateSql.append("UPDATE   TZ_SUBJSEQ	\n ");
            updateSql.append("SET	LDATE       = to_char(sysdate,'YYYYMMDDHH24MISS'),	\n ");
            updateSql.append("	GRADSCORE   = ?,	\n ");
            updateSql.append("	GRADSTEP    = ?,	\n ");
            updateSql.append("	GRADEXAM    = ?,	\n ");
            updateSql.append("	GRADFTEST	= ?,	\n ");
            updateSql.append("	GRADHTEST	= ?,	\n ");
            updateSql.append("	GRADREPORT  = ?,	\n ");
            updateSql.append("	GRADETC1	= ?,	\n ");
            updateSql.append("	WSTEP       = ?,	\n ");
            updateSql.append("	WMTEST      = ?,	\n ");
            updateSql.append("	WFTEST      = ?,	\n ");
            updateSql.append("	WHTEST      = ?,	\n ");
            updateSql.append("	WREPORT     = ?,	\n ");
            updateSql.append("	WETC1       = ? 	\n ");
            updateSql.append("WHERE	GRCODE      = ?	\n ");
            updateSql.append("		AND GYEAR       = ?	\n ");
            updateSql.append("		AND GRSEQ	    = ?	\n ");

            pstmt = connMgr.prepareStatement(updateSql.toString());

            pstmt.setString(1, box.getString("p_gradscore"));
            pstmt.setString(2, box.getString("p_gradstep"));
            pstmt.setString(3, box.getString("p_gradexam"));
            pstmt.setString(4, box.getString("p_gradftest"));
            pstmt.setString(5, box.getString("p_gradhtest"));
            pstmt.setString(6, box.getString("p_gradreport"));
            pstmt.setString(7, box.getString("p_gradetc1"));
            pstmt.setString(8, box.getString("p_wstep"));
            pstmt.setString(9, box.getString("p_wmtest"));
            pstmt.setString(10, box.getString("p_wftest"));
            pstmt.setString(11, box.getString("p_whtest"));
            pstmt.setString(12, box.getString("p_wreport"));
            pstmt.setString(13, box.getString("p_wetc1"));
            pstmt.setString(14, box.getString("p_grcode"));
            pstmt.setString(15, box.getString("p_gyear"));
            pstmt.setString(16, box.getString("p_grseq"));
            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
            throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    
    /**
     * 교육차수 일괄등록 (엑셀업로드)
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList insertGrseqExcel(RequestBox box) throws Exception {
        Sheet 				sheet 	 = null;
        Workbook 			workbook = null;
        ListSet 			ls 		 = null;
        ConfigSet 			conf 	 = null;
        ArrayList 			list 	 = null;
        StringBuffer 		query 	 = null;
        DBConnectionManager connMgr  = null;
        PreparedStatement	pstmt	 = null;
        DataBox				dbox	 = null;
    	
    	String v_newFileName  = box.getNewFileName("p_file");
    	
    	String v_grcode 	= box.getString("p_grcode"); 	//교육그룹
        String v_gyear 		= box.getString("p_gyear"); 	//연도
        String v_isAlways 	= box.getString("p_isalways"); 	//상시or정규
        String v_type		= box.getString("p_type");		//등록구분
        
        String v_grseq 		= "0001"; //교육차시
        String v_luserid 	= box.getSession("userid");
        
        int isOk = 0;
        int failCnt = 0;
        int celIndex = 0;
        int checkCount = 0;
        int pstmtIndex = 1;
        
		String v_subj			= "";
		String v_propstart 		= "";
		String v_propend 		= "";
		String v_edustart		= "";
		String v_eduend			= "";
		String v_startcanceldate= "";
		String v_endcanceldate	= "";
    	
    	try{
    		conf = new ConfigSet();
    		list = new ArrayList();
    		query = new StringBuffer();
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
            String fullName=conf.getProperty("dir.home")+v_newFileName;
            
            workbook = Workbook.getWorkbook(new File(fullName));            
            sheet = workbook.getSheet(0);
            
            query.append("\n SELECT LTRIM(TO_CHAR(NVL(MAX(GRSEQ), 0) + 1, '0000')) AS GRS	");
            query.append("\n   FROM TZ_GRSEQ                                       			");
            query.append("\n  WHERE GRCODE = ").append(SQLString.Format(v_grcode));
            query.append("\n    AND GYEAR  = ").append(SQLString.Format(v_gyear));
            ls = connMgr.executeQuery(query.toString());
            while(ls.next()){
            	v_grseq = ls.getString("GRS");
            }
            if(ls != null){
            	ls.close();
            }
            
            //insert TZ_Grseq table
            query.setLength(0);
            query.append("\n INSERT INTO  TZ_GRSEQ (	");
            query.append("\n         GRCODE  			");
            query.append("\n     ,   GYEAR   			");
            query.append("\n     ,   GRSEQ   			");
            query.append("\n     ,   GRSEQNM 			");
            query.append("\n     ,   LUSERID 			");
            query.append("\n     ,   HOMEPAGEYN  		");
            query.append("\n     ,   LDATE   			");
            query.append("\n     ,   ISALWAYS 			");
            query.append("\n ) VALUES (  				");
            query.append("\n         ?   				");
            query.append("\n     ,   ?   				");
            query.append("\n     ,   ?   				");
            query.append("\n     ,   ?   				");
            query.append("\n     ,   ?   				");
            query.append("\n     ,   'Y'   				");
            query.append("\n     ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')");
            query.append("\n     ,   ?  ) 				");

            pstmt = connMgr.prepareStatement(query.toString());

            pstmt.setString(pstmtIndex++, v_grcode);
            pstmt.setString(pstmtIndex++, v_gyear);
            pstmt.setString(pstmtIndex++, v_grseq);
            pstmt.setString(pstmtIndex++, box.getString("p_grseqnm"));
            pstmt.setString(pstmtIndex++, v_luserid);
            pstmt.setString(pstmtIndex++, v_isAlways);
            
            pstmt.executeUpdate();
            
            for (int i=1; i < sheet.getRows(); i++ ) {
            	celIndex = 0;
				v_subj				= sheet.getCell(celIndex++,i).getContents();
				v_propstart 		= sheet.getCell(celIndex++,i).getContents();
				v_propend 			= sheet.getCell(celIndex++,i).getContents();
				v_edustart			= sheet.getCell(celIndex++,i).getContents();
				v_eduend			= sheet.getCell(celIndex++,i).getContents();
				v_startcanceldate	= sheet.getCell(celIndex++,i).getContents();
				v_endcanceldate		= sheet.getCell(celIndex++,i).getContents();
                
				dbox = new DataBox(null);
				dbox.put("d_line", 		i+1);
				dbox.put("d_subj", 		v_subj);
				dbox.put("d_propstart", v_propstart);
				dbox.put("d_propend", 	v_propend);
				dbox.put("d_edustart", 	v_edustart);
				dbox.put("d_eduend", 	v_eduend);
				
				
				query.setLength(0);
	            query.append("\n SELECT COUNT(0) AS CNT ");
	            query.append("\n   FROM TZ_SUBJ			");
	            query.append("\n  WHERE SUBJ = ").append(SQLString.Format(v_subj));
	            ls = connMgr.executeQuery(query.toString());
	            while(ls.next()){
	            	checkCount = ls.getInt("CNT");
	            }
				
	            if(checkCount == 0 || "".equals(v_subj)){
	            	dbox.put("d_msg",	"ERROR01");
	            	list.add(dbox);
	            	continue;
	            }
	            
	            if(!isDate(v_propstart) || !isDate(v_propend)){
	            	dbox.put("d_msg",	"ERROR02");
	            	list.add(dbox);
	            	continue;
	            }else{
	            	if(Integer.valueOf(v_propstart) > Integer.valueOf(v_propend)){
	            		dbox.put("d_msg",	"ERROR03");
	            		list.add(dbox);
	            		continue;
	            	}
	            }
	            
	            if(!isDate(v_edustart) || !isDate(v_eduend)){
	            	dbox.put("d_msg",	"ERROR04");
	            	list.add(dbox);
	            	continue;
	            }else{
	            	if(Integer.valueOf(v_edustart) > Integer.valueOf(v_eduend)){
	            		dbox.put("d_msg",	"ERROR05");
	            		list.add(dbox);
	            		continue;
	            	}
	            }
	            	
	            	
            	isOk = makeSubjseq(v_grcode, v_gyear, v_grseq, "000000", "0000", "0000", v_subj, v_luserid, 0, v_propstart, v_propend, v_edustart, v_eduend, 0, "", "", connMgr, v_startcanceldate,
            			v_endcanceldate, "", "", "", "", "", "", "");
            	
            	if( isOk == 1){
            		dbox.put("d_msg",	"SUCCESS");
            	}else{
            		failCnt++;
            		dbox.put("d_msg",	"FAIL");
            	}
				list.add(dbox);
            }
            
            if(v_type.equals("Ins") && failCnt == 0){
            	connMgr.commit();
            }else{
            	connMgr.rollback();
            }
    	}catch(Exception ex){
    		connMgr.rollback();
    		ex.printStackTrace();
    		ErrorManager.getErrorStackTrace(ex, box, query.toString());
            throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
    	}finally{
    		if(v_newFileName != null && !v_newFileName.equals("")){
    			try{
    				FileManager.deleteFile(v_newFileName);
    			}catch(Exception e){}
    		}
    		if(pstmt != null){
    			try{
    				pstmt.close();
    			}catch(Exception e){}
    		}
    		if(ls != null){
    			try{
    				ls.close();
    			}catch(Exception e){}
    		}
    		if(workbook != null){
    			try{
    				workbook.close();
    			}catch(Exception e){}
    		}
    		if(connMgr != null){
    			try{
    				connMgr.setAutoCommit(true);
    				connMgr.freeConnection();
    			}catch(Exception e){}
    		}
    	}
    	return list;
    }
    
    public static boolean isDate(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setLenient(false);
		return format.parse(date, new ParsePosition(0)) != null;
	}
}