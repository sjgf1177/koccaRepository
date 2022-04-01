// **********************************************************
// 1. 제 목: 코드명 얻기
// 2. 프로그램명: GetCodenm.java
// 3. 개 요: DB로부터 코드값 얻어 Return
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 이수민 2003. 7. 23
// 7. 수 정:
// **********************************************************

package com.credu.common;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * 각종 코드명 관련 라이브러리
 * 
 * @date : 2002. 11
 * @author : LeeSuMin
 * 
 */
public class GetCodenm {

    /**
     * 교육그룹명 Return
     * 
     * @param String 교육그룹코드
     * @return String 교육그룹명
     */
    public static String get_grcodenm(String p_grcode) throws Exception {
        return get_grcodenm(null, p_grcode);
    }

    /**
     * 교육그룹명 Return
     * 
     * @param String 교육그룹코드
     * @param RequestBox RequestBox
     * @return String 교육그룹명
     */
    public static String get_grcodenm(RequestBox box, String p_grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        if (box != null)
            result = box.getString("grcodenm");

        if (result.equals("")) {
            try {
                connMgr = new DBConnectionManager();
                sql = " select  grcodenm  from tz_grcode where grcode=" + SQLString.Format(p_grcode);
                ls = connMgr.executeQuery(sql);
                if (ls.next())
                    result = ls.getString("grcodenm");
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
        }
        return result;
    }

    /**
     * 교육 Type Return
     * 
     * @param String 교육그룹코드
     * @param RequestBox RequestBox
     * @return String 교육그룹명
     */
    public static String get_grtype(RequestBox box, String p_grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        if (box != null)
            result = box.getString("grcodenm");

        if (result.equals("")) {
            try {
                connMgr = new DBConnectionManager();
                sql = " select  grtype from tz_grcode where grcode=" + SQLString.Format(p_grcode);
                ls = connMgr.executeQuery(sql);
                if (ls.next())
                    result = ls.getString("grtype");
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
        }
        return result;
    }

    /**
     * 교육차수명 Return
     * 
     * @param String 교육그룹코드
     * @return String 교육그룹명
     */
    public static String get_grseqnm(String p_grcode, String p_gyear, String p_grseq) throws Exception {
        return get_grseqnm(null, p_grcode, p_gyear, p_grseq);
    }

    /**
     * 교육차수명 Return
     * 
     * @param String 교육그룹코드
     * @param RequestBox RequestBox
     * @return String 교육그룹명
     */
    public static String get_grseqnm(RequestBox box, String p_grcode, String p_gyear, String p_grseq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        if (box != null)
            result = box.getString("grseqnm");

        if (result.equals("")) {
            try {
                connMgr = new DBConnectionManager();
                sql = " select  grseqnm  from tz_grseq where grcode=" + SQLString.Format(p_grcode) + " and grseq=" + SQLString.Format(p_grseq)
                        + " and gyear=" + SQLString.Format(p_gyear);
                ls = connMgr.executeQuery(sql);
                if (ls.next())
                    result = ls.getString("grseqnm");
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
        }
        return result;
    }

    /**
     * 코스명 Return
     * 
     * @param String 코스코드
     * @return String 코스명
     */
    public static String get_coursenm(String p_course) throws Exception {
        return get_coursenm(null, p_course);
    }

    /**
     * 코스명 Return
     * 
     * @param String 코스코드
     * @param RequestBox RequestBox
     * @return String 코스명
     */
    public static String get_coursenm(RequestBox box, String p_course) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        if (box != null)
            result = box.getString("coursenm");

        if (result.equals("")) {
            try {
                connMgr = new DBConnectionManager();
                sql = " select  coursenm  from tz_course where course=" + SQLString.Format(p_course);
                ls = connMgr.executeQuery(sql);
                if (ls.next())
                    result = ls.getString("coursenm");
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
        }
        return result;
    }

    /**
     * 과정분류명 Return
     * 
     * @param subjatt 코스코드
     * @return result 과정분류명
     */
    public static String get_subjattnm(String subjatt) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  classname  from tz_subjatt where upperclass=" + SQLString.Format(subjatt);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("classname");
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

        return result;
    }

    /**
     * 과정명 Return
     * 
     * @param subjatt 코스코드
     * @return result 과정분류명
     */
    public static String get_subjnm(String subj) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  subjnm  from tz_subj where subj=" + SQLString.Format(subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("subjnm");
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

        return result;
    }

    /**
     * 과정명(온라인테스트) Return
     * 
     * @param subjatt 코스코드
     * @return result 과정분류명
     */
    public static String get_subjnmot(String subj) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  subjnm  from tz_otsubj where subj=" + SQLString.Format(subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("subjnm");
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

        return result;
    }

    /**
     * 과정차수명(교육그룹별) Return
     * 
     * @param subjatt 코스코드
     * @return result 과정분류명
     */
    public static String get_subjseqgr(String subj, String grcode, String gyear, String subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  subjseqgr  from tz_subjseq where subj=" + SQLString.Format(subj);
            sql += " and subjseq = " + SQLString.Format(subjseq);
            sql += " and grcode = " + SQLString.Format(grcode);
            sql += " and gyear = " + SQLString.Format(gyear);

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("subjseqgr");
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

        return result;
    }

    /**
     * Like검색을 위한 회사코드 변경 Return
     * 
     * @param String 회사코드
     * @return String 변경회사코드
     */
    public static String get_compval(String p_comp) throws Exception {
        String v_compstr = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_comptype = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  comptype  from tz_comp where comp=" + SQLString.Format(p_comp);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_comptype = ls.getString("comptype");
                v_compstr = p_comp.substring(0, (Integer.parseInt(v_comptype) * 2)) + "%";
            } else
                v_compstr = p_comp;
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

        return v_compstr;
    }

    /**
     * user's 회사code Return
     * 
     * @param String userid
     * @return String 회사코드
     */
    public static String get_comp_userid(String p_userid) throws Exception {
        String v_compstr = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // String v_comptype ="";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  comp  from tz_member where userid=" + SQLString.Format(p_userid);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                // v_comptype = ls.getString("comp");
            } else
                v_compstr = "";
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

        return v_compstr;
    }

    /**
     * tz_config로부터 값 얻기
     * 
     * @param String tz_config.name
     * @return String tz_config.vals
     */
    public static String get_config(String p_name) throws Exception {
        String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();
            sql.append(" SELECT  RTRIM(LTRIM(VALS)) VALS  FROM TZ_CONFIG WHERE NAME = " + SQLString.Format(p_name));
            ls = connMgr.executeQuery(sql.toString());
            if (ls.next())
                v_vals = ls.getString("vals");
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return v_vals;
    }

    /**
     * tz_code로부터 코드명 얻기
     * 
     * @param String tz_config.name
     * @return String tz_config.vals
     */
    public static String get_codenm(String p_gubun, String p_code) throws Exception {
        String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  rtrim(ltrim(codenm)) VALS  from tz_code " + "  where  gubun=" + SQLString.Format(p_gubun) + " and code="
                    + SQLString.Format(p_code);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                v_vals = ls.getString("vals");
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

        return v_vals;
    }

    /**
     * tz_code로부터 멀티코드명 얻기
     * 
     * @param String tz_config.name
     * @return String tz_config.vals
     */
    public static String get_multiCodeNm(String p_gubun, String p_code) throws Exception {
        String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select GET_MULTICODENM(" + SQLString.Format(p_gubun) + ", " + SQLString.Format(p_code) + ") vals from dual";
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                v_vals = ls.getString("vals");
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

        return v_vals;
    }

    /**
     * 차시명(lesson name) 얻기
     * 
     * @param String tz_config.name
     * @return String tz_config.vals
     */
    public static String get_lessonnm(String p_subj, String p_lesson) throws Exception {
        String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  rtrim(ltrim(sdesc)) VALS  from tz_subjlesson " + "  where  subj=" + SQLString.Format(p_subj) + " and lesson="
                    + SQLString.Format(p_lesson);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                v_vals = ls.getString("vals");
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

        return v_vals;
    }

    /**
     * 클래스명 얻기
     * 
     * @param String
     * @return String
     */
    public static String get_classnm(String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        String v_vals = "not-exist";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  classnm from tz_student a, tz_class b " + "  where  a.subj=" + SQLString.Format(p_subj) + " and a.year="
                    + SQLString.Format(p_year) + "    and  a.subjseq=" + SQLString.Format(p_subjseq) + " and userid=" + SQLString.Format(p_userid)
                    + "    and  a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and a.class=b.class";
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                v_vals = ls.getString("classnm");
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

        return v_vals;
    }

    /**
     * 분기명(branch name) 얻기
     * 
     * @param String tz_config.name
     * @return String tz_config.vals select sdesc into v_lesson_brname from tz_subjlesson_branch where subj=p_subj and lesson=v_lesson and
     *         branch=v_curbranch;
     */
    public static String get_branchname(String p_subj, int p_branch) throws Exception {
        String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  rtrim(ltrim(sdesc)) VALS  from tz_branch " + "  where  subj=" + SQLString.Format(p_subj) + " and branch=" + p_branch;
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                v_vals = ls.getString("vals");
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

        return v_vals;
    }

    /**
     * Lesson Branch Name 얻기
     * 
     * @param String tz_config.name
     * @return String tz_config.vals select sdesc into v_lesson_brname from tz_subjlesson_branch where subj=p_subj and lesson=v_lesson and
     *         branch=v_curbranch;
     */
    public static String get_lessonBranchName(String p_subj, String p_lesson, int p_branch) throws Exception {
        String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  rtrim(ltrim(sdesc)) VALS  from tz_subjlesson_branch " + "  where  subj=" + SQLString.Format(p_subj) + "     and  lesson="
                    + SQLString.Format(p_lesson) + "     and  branch=" + p_branch;
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                v_vals = ls.getString("vals");
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

        return v_vals;
    }

    /**
     * Lesson Branch Name 얻기
     * 
     * @param String tz_config.name
     * @return String tz_config.vals select sdesc into v_lesson_brname from tz_subjlesson_branch where subj=p_subj and lesson=v_lesson and
     *         branch=v_curbranch;
     */
    public static String get_objectBranchName(DBConnectionManager connMgr, String p_subj, String p_lesson, int p_ordering, int p_branch) throws Exception {
        String v_vals = "";
        ListSet ls = null;
        String sql = "";

        try {
            sql = " select  rtrim(ltrim(sdesc)) VALS  from tz_subjobj_branch " + "  where  subj=" + SQLString.Format(p_subj) + "     and  lesson="
                    + SQLString.Format(p_lesson) + "     and  branch=" + p_branch + "     and  ordering=" + p_ordering;

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                v_vals = ls.getString("vals");
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

        return v_vals;
    }

    /**
     * 과정관련 정보 변경 가능여부
     * 
     * @param String 과정코드, 변경할 정보구분, 변경할 정보세부
     * @return boolean 가능여부
     */
    public static boolean canChangeSubj(String p_subj, String p_gubun, String p_opt) throws Exception {
        boolean result = true;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            if (p_gubun.equals("CA")) { // 마스터폼-CA정보
                sql = " select  count(*) CNTS from tz_progress where subj=" + SQLString.Format(p_subj);
                ls = connMgr.executeQuery(sql);
                ls.next();
                if (ls.getInt("CNTS") > 0)
                    result = false;
            } else if (p_gubun.equals("ProjOrd")) { // 리포트 출제정보
                sql = " select  count(*) CNTS from tz_projrep where subj=" + SQLString.Format(p_subj) + "   and  lesson="
                        + StringManager.substring(p_opt, 0, 2) + "   and  ordseq=" + Integer.parseInt(StringManager.substring(p_opt, 2));
                ls = connMgr.executeQuery(sql);
                ls.next();
                if (ls.getInt("CNTS") > 0)
                    result = false;
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

        return result;
    }

    /**
     * Conetnttype
     * 
     * @param String 과정코드
     * @return String 고용보험여부
     */
    public static String get_contenttype(String p_subj) throws Exception {
        String result = "N";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  contenttype from tz_subj where subj=" + SQLString.Format(p_subj);

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("contenttype");

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

        return result;
    }

    /**
     * 고용보험 과정여부
     * 
     * @param String 과정코드
     * @return String 고용보험여부
     */
    public static String get_isgoyong(String p_subj) throws Exception {
        String result = "N";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  NVL(isgoyong,'N') VALS from tz_subj where subj=" + SQLString.Format(p_subj);

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("VALS");

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

        return result;
    }

    /**
     * 고용보험 과정여부
     * 
     * @param String Connection, 과정코드
     * @return String 고용보험여부
     */
    public static String get_isgoyong(DBConnectionManager connMgr, String p_subj) throws Exception {
        String result = "N";
        ListSet ls = null;
        String sql = "";

        try {
            sql = " select  NVL(isgoyong,'N') VALS from tz_subj where subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("VALS");

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

        return result;
    }

    /**
     * 고용보험 과정차수여부
     * 
     * @param String 과정,년도,차수
     * @return String 고용보험여부
     */
    public static String get_isgoyong_seq(String p_subj, String p_year, String p_subjseq) throws Exception {
        String result = "N";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  NVL(isgoyong,'N') VALS from tz_subjseq " + "  where subj=" + SQLString.Format(p_subj) + "    and year="
                    + SQLString.Format(p_year) + "    and subjseq=" + SQLString.Format(p_subjseq);

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("VALS");

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

        return result;
    }

    /**
     * 고용보험 과정차수여부
     * 
     * @param String Connection, 과정,년도,차수
     * @return String 고용보험여부
     */
    public static String get_isgoyong_seq(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        String result = "N";
        ListSet ls = null;
        String sql = "";

        try {
            sql = " select  NVL(isgoyong,'N') VALS from tz_subjseq " + "  where subj=" + SQLString.Format(p_subj) + "    and year="
                    + SQLString.Format(p_year) + "    and subjseq=" + SQLString.Format(p_subjseq);

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("VALS");

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

        return result;
    }

    /**
     * 직훈차수명 Return
     * 
     * @param String 직훈차수코드
     * @param RequestBox RequestBox
     * @return String 직훈차수명
     */
    public static String get_eduseqnm(RequestBox box, String p_eduseq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        if (box != null)
            result = box.getString("eduseqnm");

        if (result.equals("")) {
            try {
                connMgr = new DBConnectionManager();
                sql = " select  eduseqnm  from TZ_EDUSEQ where eduseq=" + SQLString.Format(p_eduseq);
                ls = connMgr.executeQuery(sql);
                if (ls.next())
                    result = ls.getString("eduseqnm");
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
        }
        return result;
    }

    /**
     * 과정소유주 Return
     * 
     * @param String 과정코드
     * @return String 과정소유주
     */
    public static String get_owner(String p_subj) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  owner  from tz_subj where subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("owner");
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
     * 과정/코스분류 Return
     * 
     * @param String 과정/코스 코드
     * @param String 과정/코스 년도
     * @param String 과정/코스 차수
     * @return String 과정/코스 분류
     */
    public static String get_upperclass(String p_subj, String p_year, String p_seq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select scupperclass from VZ_SCSUBJSEQ  ";
            sql += "  where scsubj=" + SQLString.Format(p_subj);
            sql += "    and scyear=" + SQLString.Format(p_year);
            sql += "    and scsubjseq=" + SQLString.Format(p_seq);

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getString("scupperclass");
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return result;
    }
}