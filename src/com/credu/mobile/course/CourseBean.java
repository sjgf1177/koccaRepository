package com.credu.mobile.course;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.DatabaseExecute;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 프로젝트명 : kocca_java 패키지명 : com.credu.mobile.course 파일명 : CourseBean.java 작성날짜
 * : 2011. 9. 28. 처리업무 : 수정내용 :
 * 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.
 */

public class CourseBean {
    private StringBuffer strQuery = null;
    private int x = 1;
    private String tem_grcode = "";
    private int row;
    private ConfigSet config;

    public CourseBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 수강신청목록 목록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getCourseList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {

            connMgr = new DatabaseExecute();

            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            String v_upperclass = box.getString("p_upperclass");
            String v_middleclass = box.getString("p_middleclass");

            String v_menuid = box.getString("p_menuid");
            String v_depth1 = StringManager.substring(v_menuid, 0, 3);

            String v_searchText = box.getString("p_searchText");
            if ("020".equals(v_depth1)) {
                strQuery.append("select \n");
                strQuery.append("    'OFF' as cousegubun, a.subj, a.year, a.subjseq, a.propstart, a.propend, a.edustart, a.eduend, \n");
                strQuery.append("    b.subjnm, b.area, get_codenm('0101', b.area) as areanm \n");
                strQuery.append("from \n");
                strQuery.append("    tz_offsubjseq a, tz_offsubj b \n");
                strQuery.append("where \n");
                strQuery.append("    a.subj = b.subj \n");
                strQuery.append("    and a.year = to_char(sysdate, 'yyyy') \n");
                strQuery.append("    and b.isuse = 'Y' \n");
                strQuery.append("    and b.isvisible = 'Y' \n");
                if (!"".equals(v_upperclass))
                    strQuery.append("    and b.upperclass = " + StringManager.makeSQL(v_upperclass) + " \n");
                if (!"".equals(v_middleclass))
                    //	strQuery.append("    and b.middleclass = " + StringManager.makeSQL(v_middleclass ) + " \n");
                    if (!"".equals(v_searchText))
                        strQuery.append("    and upper(b.subjnm) like upper(" + StringManager.makeSQL("%" + v_searchText + "%") + ") \n");
                //strQuery.append("    and to_char(sysdate, 'yyyymm') between substr(a.propstart, 0, 6) and substr(a.propend, 0, 6) \n");
                strQuery.append("order by a.edustart asc \n");
            } else {
                strQuery.append("select \n");
                strQuery.append("    'ON' as cousegubun, a.subj, a.year, a.subjseq, a.propstart, a.propend, a.edustart, a.eduend, \n");
                strQuery.append("    b.subjnm, b.isonoff, b.area, get_codenm('0101', b.area) as areanm, b.mobile_preurl \n");
                strQuery.append("from \n");
                strQuery.append("    tz_subjseq a, tz_subj b \n");
                strQuery.append("where \n");
                strQuery.append("    a.subj = b.subj \n");
                strQuery.append("    and a.grcode = " + StringManager.makeSQL(tem_grcode) + " \n");
                strQuery.append("    and a.year = to_char(sysdate, 'yyyy') \n");
                strQuery.append("    and b.isuse = 'Y' \n");
                strQuery.append("    and b.isvisible = 'Y' and b.mobile_preurl ='Y' \n");
                /*
                 * if(!"".equals(v_upperclass))
                 * strQuery.append("    and b.upperclass = " +
                 * StringManager.makeSQL(v_upperclass) + " \n");
                 * if(!"".equals(v_middleclass))
                 * strQuery.append("    and b.middleclass = " +
                 * StringManager.makeSQL(v_middleclass ) + " \n");
                 */
                if (!"".equals(v_searchText))
                    strQuery.append("    and upper(b.subjnm) like upper(" + StringManager.makeSQL("%" + v_searchText + "%") + ") \n");
                strQuery.append("    and to_char(sysdate, 'yyyymmdd') between substr(a.propstart, 0, 8) and substr(a.propend, 0, 8) \n");
                strQuery.append("order by a.propstart desc \n");
            }

            box.put("p_isPage", new Boolean(true));
            box.put("p_row", new Integer(row));

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 과정 대분류 목록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getUpperSubjattList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {

            connMgr = new DatabaseExecute();

            String v_menuid = box.getString("p_menuid");
            String v_depth1 = StringManager.substring(v_menuid, 0, 3);

            String v_tableName = "";
            if ("020".equals(v_depth1))
                v_tableName = "tz_offsubjatt";
            else
                v_tableName = "tz_subjatt";

            strQuery.append("select  \n");
            strQuery.append("    a.subjclass, a.upperclass, a.middleclass, a.lowerclass, a.classname \n");
            strQuery.append("from \n");
            strQuery.append("    " + v_tableName + " a \n");
            strQuery.append("where \n");
            strQuery.append("    substr(a.upperclass, 0, 1) != 'X' \n");
            if ("010".equals(v_depth1))// 1차분류에서 제외
                strQuery.append("    and a.upperclass not in('C05', 'W01') \n");
            strQuery.append("    and a.middleclass = '000' \n");
            strQuery.append("    and a.lowerclass = '000' \n");
            strQuery.append("order by a.upperclass, a.middleclass \n");

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 중분류 목록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getMiddleSubjattList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {

            connMgr = new DatabaseExecute();

            String v_upperclass = box.getString("p_upperclass");
            String v_menuid = box.getString("p_menuid");
            String v_depth1 = StringManager.substring(v_menuid, 0, 3);

            String v_tableName = "";
            if ("020".equals(v_depth1))
                v_tableName = "tz_offsubjatt";
            else
                v_tableName = "tz_subjatt";

            strQuery.append("select  \n");
            strQuery.append("    a.subjclass, a.upperclass, a.middleclass, a.lowerclass, a.classname \n");
            strQuery.append("from \n");
            strQuery.append("    " + v_tableName + " a \n");
            strQuery.append("where \n");
            strQuery.append("    a.upperclass = " + StringManager.makeSQL(v_upperclass) + " \n");
            strQuery.append("    and a.middleclass != '000' \n");
            strQuery.append("    and a.lowerclass = '000' \n");
            strQuery.append("order by a.upperclass, a.middleclass \n");

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 관심과정 등록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox insertInterest(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox mbox = new DataBox("request");

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            String s_userid = box.getSession("userid");
            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            mbox.put("_result_", new Boolean(false));

            //관심과정 등록 여부
            strQuery.append("select \n");
            strQuery.append("    a.subj \n");
            strQuery.append("from \n");
            strQuery.append("    tz_interest a \n");
            strQuery.append("where \n");
            strQuery.append("    a.grcode = ? \n");
            strQuery.append("    and a.userid = ? \n");
            strQuery.append("    and a.subj = ? \n");
            strQuery.append("    and rownum = 1 \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, tem_grcode);
            pstmt.setString(x++, s_userid);
            pstmt.setString(x++, box.getString("p_subj"));

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next()) {
                mbox.put("_msg_", "이미 관심과정에 등록되었습니다.");
                return mbox;
            }
            ls.close();
            ls = null;

            //관심과정 등록
            strQuery = new StringBuffer();
            strQuery.append("merge into tz_interest a \n");
            strQuery.append("using ( select ? grcode, ? subj, ? userid from dual) b \n");
            strQuery.append("on \n");
            strQuery.append("( \n");
            strQuery.append("    a.grcode = b.grcode \n");
            strQuery.append("    and a.subj = b.subj \n");
            strQuery.append("    and a.userid = b.userid \n");
            strQuery.append(") \n");
            strQuery.append("when matched then \n");
            strQuery.append("    update set \n");
            strQuery.append("        indate = to_char(sysdate, 'yyyymmddhh24') \n");
            strQuery.append("when not matched then \n");
            strQuery.append("    insert (grcode, subj, userid, indate) values (?, ?, ?, to_char(sysdate, 'yyyymmddhh24')) \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, tem_grcode);
            pstmt.setString(x++, box.getString("p_subj"));
            pstmt.setString(x++, s_userid);

            pstmt.setString(x++, tem_grcode);
            pstmt.setString(x++, box.getString("p_subj"));
            pstmt.setString(x++, s_userid);
            pstmt.executeUpdate();

            mbox.put("_result_", new Boolean(true));
        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
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

        return mbox;
    }

    /**
     * 관심과정 삭제
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public boolean removeInterest(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            String s_userid = box.getString("userid");
            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            strQuery.append("delete from tz_interest\n");
            strQuery.append("where \n");
            strQuery.append("    grcode = ? \n");
            strQuery.append("    and userid = ? \n");
            strQuery.append("    and subj = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, tem_grcode);
            pstmt.setString(x++, s_userid);
            pstmt.setString(x++, box.getString("p_subj"));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
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
        return true;
    }

    /**
     * 모바일 과정 상세(온라인)
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getOnCouseData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            strQuery = new StringBuffer();

            // String v_menuid = box.getString("p_menuid");
            // String v_depth1 = StringManager.substring(v_menuid, 0, 3);

            strQuery.append("select \n");
            strQuery.append("    a.subj,  a.biyong,  a.studentlimit, a.subj, a.year, a.subjseq, a.propstart, a.propend, a.edustart, a.eduend, a.propstart as canclestart, \n");
            strQuery.append("    decode(a.propend, null, '', to_char((to_date(substr(a.propend, 0, 8), 'yyyymmdd') + a.canceldays), 'yyyymmdd')||substr(a.propend, 9, 2)) as cancleend, \n");
            strQuery.append("    a.gradscore, \n");
            strQuery.append("    (case when a.gradstep > 0 then a.gradstep || '점 이상' else '-' end) as gradstep_name, \n");//진도율
            strQuery.append("    (case when a.wstep > 0 then a.wstep || '%' else '-' end) as wstep_name, \n");

            strQuery.append("    (case when a.gradhtest > 0 then a.gradhtest || '점 이상' else '-' end) as gradhtest_name, \n"); //진단평가
            strQuery.append("    (case when a.whtest > 0 then a.whtest || '%' else '-' end) as whtest_name, \n");

            strQuery.append("    (case when a.gradexam > 0 then a.gradexam || '점 이상' else '-' end) as gradexam_name, \n"); //중간평가
            strQuery.append("    (case when a.wmtest > 0 then a.wmtest || '%' else '-' end) as wmtest_name, \n");

            strQuery.append("    (case when a.gradftest > 0 then a.gradftest || '점 이상' else '-' end) as gradftest_name, \n"); //최종평가
            strQuery.append("    (case when a.wftest > 0 then a.wftest || '%' else '-' end) as wftest_name, \n");

            strQuery.append("    (case when a.gradreport > 0 then a.gradreport || '점 이상' else '-' end) as gradreport_name, \n"); //과제제출
            strQuery.append("    (case when a.wreport > 0 then a.wreport || '%' else '-' end) as wreport_name, \n");
            strQuery.append("    (case when a.wetc1 > 0 then a.wetc1 || '%' else '-' end) as wetc2_name, \n"); //참여도
            strQuery.append("    (case when a.wetc2 > 0 then a.wetc2 || '%' else '-' end) as wetc1_name, \n"); //기타

            strQuery.append("        ( \n");
            strQuery.append("                select \n");
            strQuery.append("                        count(s1.userid) \n");
            strQuery.append("                from \n");
            strQuery.append("                        tz_propose s1 \n");
            strQuery.append("                where \n");
            strQuery.append("                        s1.subj = a.subj \n");
            strQuery.append("                        and s1.year = a.year \n");
            strQuery.append("                        and s1.subjseq = a.subjseq \n");
            strQuery.append("        ) as proposecnt, b.area, get_codenm('0101', b.area) as areanm, get_codenm('0076', b.subjtype) as subjtypenm,\n");
            strQuery.append("    b.subjnm, b.contenttype, b.edumans, b.isonoff, b.subjtarget, b.isessential, b.conturl, b.introducefilenamereal, b.introducefilenamenew, b.edudays, \n");
            strQuery.append("    b.eduperiod, b.usebook, b.intro, b.explain, b.preurl, b.proposetype, a.canceldays, b.memo, b.epub_file,  \n");
            strQuery.append("    d.classname as uppername, e.classname as middlename, c.classname, f.name as mastername, f.email as masteremail, f.comptel mastercomptel \n");
            strQuery.append("from \n");
            strQuery.append("    tz_subjseq a, tz_subj b, tz_subjatt c, \n");
            strQuery.append("    (select s1.upperclass, s1.classname from tz_subjatt s1 where s1.middleclass = '000' and s1.lowerclass = '000') d, \n");
            strQuery.append("    (select s1.upperclass, s1.middleclass, s1.classname from tz_subjatt s1 where s1.middleclass != '000' and s1.lowerclass = '000') e, tz_member f \n");
            strQuery.append("where \n");
            strQuery.append("    a.subj = b.subj \n");
            strQuery.append("    and b.subjclass = c.subjclass \n");
            strQuery.append("    and b.upperclass = d.upperclass \n");
            strQuery.append("    and b.upperclass = e.upperclass \n");
            strQuery.append("    and b.middleclass = e.middleclass \n");
            strQuery.append("    and a.charger = f.userid(+) \n");
            strQuery.append("    and a.grcode = f.grcode(+) \n");
            strQuery.append("    and a.subj = ? \n");
            strQuery.append("    and a.year = ? \n");
            strQuery.append("    and a.subjseq = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, box.getString("p_subj"));
            pstmt.setString(x++, box.getString("p_year"));
            pstmt.setString(x++, box.getString("p_subjseq"));

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next())
                dbox = ls.getDataBox();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
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

        return dbox;
    }

    /**
     * 모바일 과정 상세(오프라인)
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getOffCouseData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        strQuery = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            strQuery = new StringBuffer();
            strQuery.append("select \n");
            strQuery.append("    a.subj,  a.biyong,  a.studentlimit, a.subj, a.year, a.subjseq, a.propstart, a.propend, a.edustart, a.eduend, \n");
            strQuery.append("    a.gradscore, b.area, get_codenm('0101', b.area) as areanm,\n");
            strQuery.append("    b.subjnm,  b.subjtarget, b.edudays, b.intro, b.explain, b.memo, b.subjtarget\n");
            strQuery.append("from \n");
            strQuery.append("    tz_offsubjseq a, tz_offsubj b\n");
            strQuery.append("where \n");
            strQuery.append("    a.subj = b.subj \n");
            strQuery.append("    and a.subj = ? \n");
            strQuery.append("    and a.year = ? \n");
            strQuery.append("    and a.subjseq = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, box.getString("p_subj"));
            pstmt.setString(x++, box.getString("p_year"));
            pstmt.setString(x++, box.getString("p_subjseq"));

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next())
                dbox = ls.getDataBox();

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
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

        return dbox;
    }

    /**
     * 과정 차시 정보
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getCourseLessonList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {

            connMgr = new DatabaseExecute();

            String v_subj = box.getString("p_subj");

            strQuery.append("select  \n");
            strQuery.append("    a.lesson, a.sdesc, b.fromdate, b.todate, get_name(a.userid) as name, a.mobile_url \n");
            strQuery.append("from \n");
            strQuery.append("    tz_subjlesson a, tz_subjlessondate b \n");
            strQuery.append("where  \n");
            strQuery.append("    a.subj = b.subj(+)   \n");
            strQuery.append("    and a.module = b.module(+)  \n");
            strQuery.append("    and a.lesson = b.lesson(+) \n");
            strQuery.append("    and a.subj = " + StringManager.makeSQL(v_subj) + "  \n");
            strQuery.append("order by a.lesson \n");

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 강사목록
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getCourseTutorList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {

            connMgr = new DatabaseExecute();

            String v_subj = box.getString("p_subj");

            strQuery.append("select    \n");
            strQuery.append("    b.name, b.majorbook, b.intro, b.career, \n");
            strQuery.append("    decode(nvl(b.photo, ''), b.photo, newphoto, '/images/portal/common/nophoto.gif') phototerms, b.comp  \n");
            strQuery.append("from  \n");
            strQuery.append("    tz_subjtutor a, tz_tutor b \n");
            strQuery.append("where  \n");
            strQuery.append("    a.userid = b.userid \n");
            strQuery.append("    and a.subj = " + StringManager.makeSQL(v_subj) + "  \n");

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 과정 전체 검색
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getSearchCourseList(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        ArrayList list = null;
        strQuery = new StringBuffer();

        try {

            connMgr = new DatabaseExecute();

            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            String v_searchText = box.getString("p_searchText");

            strQuery.append("select \n");
            strQuery.append("    'OFF' as cousegubun, a.subj, a.year, a.subjseq, a.propstart, a.propend, a.edustart, a.eduend, \n");
            strQuery.append("    b.subjnm, b.area, get_codenm('0101', b.area) as areanm, '' as mobile_preurl \n");
            strQuery.append("from \n");
            strQuery.append("    tz_offsubjseq a, tz_offsubj b \n");
            strQuery.append("where \n");
            strQuery.append("    a.subj = b.subj \n");
            strQuery.append("    and a.year = to_char(sysdate, 'yyyy') \n");
            strQuery.append("    and b.isuse = 'Y' \n");
            strQuery.append("    and b.isvisible = 'Y' \n");

            if (!"".equals(v_searchText))
                strQuery.append("    and upper(b.subjnm) like upper(" + StringManager.makeSQL("%" + v_searchText + "%") + ") \n");
            strQuery.append("    and to_char(sysdate, 'yyyymm') between substr(a.propstart, 0, 6) and substr(a.propend, 0, 6) \n");
            strQuery.append("union all \n");
            strQuery.append("select \n");
            strQuery.append("    'ON' as cousegubun, a.subj, a.year, a.subjseq, a.propstart, a.propend, a.edustart, a.eduend, \n");
            strQuery.append("    b.subjnm, b.area, get_codenm('0101', b.area) as areanm, b.mobile_preurl \n");
            strQuery.append("from \n");
            strQuery.append("    tz_subjseq a, tz_subj b \n");
            strQuery.append("where \n");
            strQuery.append("    a.subj = b.subj \n");
            strQuery.append("    and a.grcode = " + StringManager.makeSQL(tem_grcode) + " \n");
            strQuery.append("    and a.year = to_char(sysdate, 'yyyy') \n");
            strQuery.append("    and b.isuse = 'Y' \n");
            strQuery.append("    and b.isvisible = 'Y' \n");

            if (!"".equals(v_searchText))
                strQuery.append("    and upper(b.subjnm) like upper(" + StringManager.makeSQL("%" + v_searchText + "%") + ") \n");
            strQuery.append("    and to_char(sysdate, 'yyyymm') between substr(a.propstart, 0, 6) and substr(a.propend, 0, 6) \n");
            strQuery.append("order by propstart, subjnm \n");

            box.put("p_isPage", new Boolean(true));
            box.put("p_row", new Integer(row));

            list = connMgr.executeQueryList(box, strQuery.toString());

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }

        return list;
    }

    /**
     * 수강신청 작업중
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox insertPropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ListSet ls = null;

        DataBox mbox = new DataBox("request");

        strQuery = new StringBuffer();

        String v_chkfinal = "Y";

        int isOk = 0;
        int isOk2 = 0;

        try {
            connMgr = new DBConnectionManager();

            String s_userid = box.getSession("userid");
            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            String v_subj = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");

            mbox.put("_result_", new Boolean(false));

            strQuery.append("select \n");
            strQuery.append("    a.subj \n");
            strQuery.append("from \n");
            strQuery.append("    TZ_STUDENT a \n");
            strQuery.append("where \n");
            strQuery.append("    a.userid = ? \n");
            strQuery.append("    and a.subj = ? \n");
            strQuery.append("    and a.subjseq = ? \n");
            strQuery.append("    and a.year = ? \n");

            x = 1;
            pstmt = connMgr.prepareStatement(strQuery.toString());
            pstmt.setString(x++, s_userid);
            pstmt.setString(x++, box.getString("p_subj"));
            pstmt.setString(x++, box.getString("p_subjseq"));
            pstmt.setString(x++, box.getString("p_year"));

            ls = new ListSet(pstmt);
            ls.executeQuery();

            if (ls.next()) { // 기존수강신청 되어있음

            } else {
                //

                connMgr.setAutoCommit(false);
                strQuery = new StringBuffer();
                strQuery.append("select \n");
                strQuery.append("    a.subj \n");
                strQuery.append("from \n");
                strQuery.append("    TZ_PROPOSE a \n");
                strQuery.append("where \n");
                strQuery.append("    a.asp_gubun = ? \n");
                strQuery.append("    and a.userid = ? \n");
                strQuery.append("    and a.subj = ? \n");
                strQuery.append("    and a.subjseq = ? \n");
                strQuery.append("    and a.year = ? \n");

                x = 1;
                pstmt = connMgr.prepareStatement(strQuery.toString());
                pstmt.setString(x++, tem_grcode);
                pstmt.setString(x++, s_userid);
                pstmt.setString(x++, box.getString("p_subj"));
                pstmt.setString(x++, box.getString("p_subjseq"));
                pstmt.setString(x++, box.getString("p_year"));

                ls = new ListSet(pstmt);
                ls.executeQuery();

                strQuery = new StringBuffer();
                if (ls.next()) { // 기존수강신청 후 취소
                    //strQuery = new StringBuffer();

                    strQuery.append(" update TZ_PROPOSE set chkfirst='Y',chkfinal=?,cancelkind='', \n");
                    strQuery.append(" luserid=?,ldate=to_char(sysdate,'YYYYMMDDHH24MISS') \n");
                    strQuery.append(" where subj=? and year=? and subjseq=? and userid=?  \n");
                    x = 1;
                    pstmt2 = connMgr.prepareStatement(strQuery.toString());
                    //							pstmt3.setString(i++, v_chkfirst);
                    pstmt2.setString(x++, v_chkfinal);
                    pstmt2.setString(x++, s_userid);
                    pstmt2.setString(x++, v_subj);
                    pstmt2.setString(x++, v_year);
                    pstmt2.setString(x++, v_subjseq);
                    pstmt2.setString(x++, s_userid);

                    isOk = pstmt2.executeUpdate();
                } else {//신규신청                       

                    strQuery.append(" insert into TZ_PROPOSE(subj, year, subjseq, userid, appdate, chkfirst, chkfinal, \n");
                    strQuery.append("  luserid, ldate,asp_gubun)\n");
                    strQuery.append("  values (?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),'Y',?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),?) \n");
                    x = 1;
                    pstmt2 = connMgr.prepareStatement(strQuery.toString());
                    pstmt2.setString(x++, v_subj);
                    pstmt2.setString(x++, v_year);
                    pstmt2.setString(x++, v_subjseq);
                    pstmt2.setString(x++, s_userid);
                    //							pstmt3.setString(i++, v_chkfirst);
                    pstmt2.setString(x++, v_chkfinal);
                    pstmt2.setString(x++, s_userid);
                    pstmt2.setString(x++, tem_grcode);

                    isOk = pstmt2.executeUpdate();
                }

                // TZ_STUDENT 추가

                strQuery = new StringBuffer();
                strQuery.append(" insert into TZ_STUDENT ( ");
                strQuery.append(" subj,        year,     subjseq,     userid,    ");
                strQuery.append(" class,       comp,     isdinsert,   score,     ");
                strQuery.append(" tstep,       mtest,    ftest,       report,    ");
                strQuery.append(" act,         etc1,     etc2,        avtstep,   ");
                strQuery.append(" avmtest,     avftest,  avreport,    avact,     ");
                strQuery.append(" avetc1,      avetc2,   isgraduated, isrestudy, ");
                strQuery.append(" isb2c,       edustart, eduend,      branch,    ");
                strQuery.append(" confirmdate, eduno,    luserid,     ldate,     ");
                strQuery.append(" stustatus )  ");
                strQuery.append(" values ( ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?, ?, ?, ?, ");
                strQuery.append(" ?) ");

                pstmt2 = connMgr.prepareStatement(strQuery.toString());

                pstmt2.setString(1, v_subj);
                pstmt2.setString(2, v_year);
                pstmt2.setString(3, v_subjseq);
                pstmt2.setString(4, s_userid);
                pstmt2.setString(5, ""); // v_class
                pstmt2.setString(6, tem_grcode);
                pstmt2.setString(7, "Y"); // v_isdinsert
                pstmt2.setDouble(8, 0); // v_score
                pstmt2.setDouble(9, 0); // v_tstep
                pstmt2.setDouble(10, 0); // v_mtest
                pstmt2.setDouble(11, 0); // v_ftest
                pstmt2.setDouble(12, 0); // v_report
                pstmt2.setDouble(13, 0); // v_act
                pstmt2.setDouble(14, 0); // v_etc1
                pstmt2.setDouble(15, 0); // v_etc2
                pstmt2.setDouble(16, 0); // v_avtstep
                pstmt2.setDouble(17, 0); // v_avmtest
                pstmt2.setDouble(18, 0); // v_avftest
                pstmt2.setDouble(19, 0); // v_avreport
                pstmt2.setDouble(20, 0); // v_avact
                pstmt2.setDouble(21, 0); // v_avetc1
                pstmt2.setDouble(22, 0); // v_avetc2
                pstmt2.setString(23, "N"); // v_isgraduated
                pstmt2.setString(24, "N"); // v_isrestudy)
                pstmt2.setString(25, "N");
                pstmt2.setString(26, ""); //v_edustart
                pstmt2.setString(27, ""); //  v_eduend
                pstmt2.setInt(28, 99); //v_branch
                pstmt2.setString(29, ""); // v_confirmdate
                pstmt2.setInt(30, 0); // v_eduno
                pstmt2.setString(31, "system");
                pstmt2.setString(32, FormatDate.getDate("yyyyMMddHHmmss")); // ldate
                pstmt2.setString(33, "Y"); // stustatus
                isOk2 = pstmt2.executeUpdate();

                mbox.put("_result_", new Boolean(true));

                if (isOk > 0 && isOk2 > 0) {
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
            } // 기존 수강신청 체크 끝

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e, box, strQuery.toString());
            throw new Exception("\n SQL : [\n" + strQuery.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
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

        return mbox;
    }

    /**
     * 차수별 신청 갯수 구해오기 1인 X과정 Limit 제한때문에 사용됨
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int proposeLimitCount(RequestBox box) throws Exception {
        DatabaseExecute connMgr = null;

        int cnt = 0;
        DataBox resultBox = new DataBox("request");
        strQuery = new StringBuffer();

        try {
            connMgr = new DatabaseExecute();

            tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            String v_userid = box.getSession("userid");
            // String v_subj = box.getString("p_subj");
            // String v_subjseq = box.getString("p_subjseq");
            String v_year = box.getString("p_year");

            strQuery.append("SELECT COUNT(1) LIMITCNT \n");
            strQuery.append("FROM TZ_SUBJSEQ A \n");
            strQuery.append("LEFT JOIN TZ_PROPOSE B \n");
            strQuery.append("ON A.SUBJ = B.SUBJ AND A.YEAR = B.YEAR AND A.SUBJSEQ = B.SUBJSEQ AND B.USERID = " + StringManager.makeSQL(v_userid) + " \n");
            strQuery.append("WHERE \n");
            strQuery.append("	NVL(A.BIYONG, 0) = 0 \n");
            strQuery.append("	AND (B.CANCELKIND IS NULL OR B.CANCELKIND = '') \n");
            strQuery.append("	AND A.GRCODE = " + StringManager.makeSQL(tem_grcode) + " \n");
            strQuery.append("	AND B.USERID IS NOT NULL \n");
            strQuery.append("	AND B.YEAR = " + StringManager.makeSQL(v_year) + " \n");
            // strQuery.append("	AND A.GRSEQ = (SELECT GRSEQ FROM TZ_SUBJSEQ WHERE GRCODE = " + StringManager.makeSQL(tem_grcode) + " AND YEAR = " + StringManager.makeSQL(v_year) + " AND SUBJ = " + StringManager.makeSQL(v_subj) + " AND SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + ") \n");
            strQuery.append("   AND  ( TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.PROPSTART AND A.PROPEND \n");
            strQuery.append("         OR  \n");
            strQuery.append("         TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.EDUSTART AND A.EDUEND )  \n");

            resultBox = connMgr.executeQuery(box, strQuery.toString());

            cnt = resultBox.getInt("d_limitcnt");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
            throw new Exception("sql = " + strQuery + "\r\n" + ex.getMessage());
        }
        return cnt;
    }

}
