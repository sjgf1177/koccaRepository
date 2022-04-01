// **********************************************************
// 1. 제 목: 학습창 START BEAN
// 2. 프로그램명: EduStartBean.java
// 3. 개 요: 학습창 START BEAN
// 4. 환 경: JDK 1.4
// 5. 버 젼: 0.1
// 6. 작 성: S.W.Kang 2004. 12. 5
// 7. 수 정:
// **********************************************************
package com.credu.beta;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.common.GetCodenm;
import com.credu.library.CalcUtil;
import com.credu.library.DBConnectionManager;
import com.credu.library.EduEtc1Bean;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class BetaEduStartBean {

    public BetaEduStartBean() {
    }

    /**
     * 마스터폼정보 조회
     * 
     * @param box receive from the form object and session
     * @return BetaMasterFormData 마스터폼 정보
     */
    public BetaMasterFormData SelectBetaMasterFormData(RequestBox box) throws Exception {
        BetaMasterFormBean bean = new BetaMasterFormBean();
        return bean.SelectBetaMasterFormData(box);
    }

    /**
     * 마스터폼 Lesson리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList Lesson리스트
     */
    public ArrayList<BetaMfLessonData> SelectMfLessonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null;
        ArrayList<BetaMfLessonData> list1 = null;
        String sql = "";
        BetaMfLessonData data = null;

        String s_subj = box.getSession("s_subj");
        String s_year = box.getSession("s_year");
        String s_subjseq = box.getSession("s_subjseq");
        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<BetaMfLessonData>();

            sql = "select module,lesson,sdesc,types,owner,starting " + "  from tz_subjlesson  " + " where subj=" + StringManager.makeSQL(s_subj)
                    + " order by lesson";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new BetaMfLessonData();
                data.setModule(ls.getString("module"));
                data.setLesson(ls.getString("lesson"));
                data.setSdesc(ls.getString("sdesc"));
                data.setTypes(ls.getString("types"));
                data.setOwner(ls.getString("owner"));
                data.setStarting(ls.getString("starting"));
                // data.setIsbranch (ls.getString("isbranch")) ;
                // 학습여부 Set
                // 2005.11.15_하경태 : Oralce -> Mssql
                // sql = "select decode(count(userid),0,'N','Y') f_exist from tz_betaprogress"
                sql = "select case count(userid) When 0 Then 'N' Else 'Y' End f_exist from tz_betaprogress" + " where subj="
                        + StringManager.makeSQL(s_subj) + "   and year=" + StringManager.makeSQL(s_year) + "   and subjseq="
                        + StringManager.makeSQL(s_subjseq) + "   and lesson=" + StringManager.makeSQL(data.getLesson()) + "   and userid="
                        + StringManager.makeSQL(s_userid);
                if (ls1 != null) {
                    try {
                        ls1.close();
                    } catch (Exception e) {
                    }
                }
                ls1 = connMgr.executeQuery(sql);
                ls1.next();
                data.setIseducated(ls1.getString("f_exist"));
                // 응시할 평가존재여부 Set
                // 2005.11.15_하경태 : Oralce -> Mssql
                // sql = "select decode(count(lesson),0,'N','Y') f_exist from tz_exammaster"
                sql = "select case count(lesson) When 0 Then 'N' Else 'Y' End  f_exist from tz_exammaster" + " where subj=" + StringManager.makeSQL(s_subj)
                // + "   and year='TEST'"
                        // + "   and subjseq='TEST'"
                        + "   and lesson=" + StringManager.makeSQL(data.getLesson());

                if (ls1 != null) {
                    try {
                        ls1.close();
                    } catch (Exception e) {
                    }
                }
                ls1 = connMgr.executeQuery(sql);
                ls1.next();
                data.setIsexam(ls1.getString("f_exist"));

                list1.add(data);
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
     * 진도체크
     * 
     * @param box receive from the form object and session (p_gubun: START-진도시작, END-진도종료
     * @return int 진도체크 결과
     * 
     *         Process 1. 정상 학습자 체크 : 정상 학습자 아니면 진도데이터 미생성 2. 기 체크여부 체크 : 기체크되었으면 tz_betaprogress update 3. 진도제한 체크 3.1. 진도제한차시수 체크 3.2. 고용보험과정이면 진도제한 차시수와
     *         비교, 큰 넘으로 진도제한 설정 4. 고용보험과정이면 이전차시 학습/평가미실시건 존재여부 체크 5. 진도데이터 생성
     */
    public String EduCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null, ls1 = null;
        int isOk = 1, v_edulimit = 0, v_myeducnt = 0, v_noteducnt = 0;
        // boolean isMake = true;
        String sql = "";
        String v_isgoyong, v_contenttype = "", results = "";
        String s_subj = box.getSession("s_subj");
        String s_year = box.getSession("s_year");
        String s_subjseq = box.getSession("s_subjseq");
        String s_userid = box.getSession("userid");
        String s_eduauth = box.getSession("s_eduauth");
        String p_lesson = box.getString("p_lesson");
        String p_oid = box.getString("p_oid");
        if (p_oid.equals(""))
            p_oid = "1"; // oid 없으면 디폴트 OID값 셋팅("1")
        String p_gubun = box.getString("p_gubun");
        if (p_gubun.equals(""))
            p_gubun = "END"; // 구분값 없으면 진도체크

        String v_session_time = "", v_total_time = "", v_ldate = "", v_sysdate = "";
        // System.out.println("Start EduCheck: s_eduauth="+s_eduauth+".oid="+p_oid);

        try {
            connMgr = new DBConnectionManager();
            // 1.정상 학습자여부 체크

            if (!s_eduauth.equals("Y"))
                return "OK";
            if (!EduEtc1Bean.isCurrentStudent(s_subj, s_year, s_subjseq, s_userid).equals("Y"))
                return "OK";

            // 2. 기 진도체크여부
            sql = "select total_time, ldate, to_char(sysdate,'YYYYMMDDHH24MISS') nowtime  " + "  from tz_betaprogress  " + " where subj="
                    + StringManager.makeSQL(s_subj) + "   and year=" + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq)
                    + "   and userid=" + StringManager.makeSQL(s_userid) + "   and lesson=" + StringManager.makeSQL(p_lesson)
                    + "   and first_end is not null and rownum=1 ";

            if (!p_oid.equals("1"))
                sql += " and oid=" + StringManager.makeSQL(p_oid);
            ls = connMgr.executeQuery(sql);

            // 진도체크정보 기존재시
            if (ls.next()) {

                // 진도종료체크시 세션타임,총학습시간,횟수 update
                if (p_gubun.equals("END")) {
                    v_total_time = ls.getString("total_time");
                    v_ldate = ls.getString("ldate");
                    v_sysdate = ls.getString("nowtime");
                    v_session_time = EduEtc1Bean.get_duringtime(v_ldate, v_sysdate);
                    v_total_time = EduEtc1Bean.add_duringtime(v_total_time, v_session_time);

                    sql = "update tz_betaprogress set " + "		session_time=" + StringManager.makeSQL(v_session_time) + "		,total_time  ="
                            + StringManager.makeSQL(v_total_time) + "		,ldate=to_char(sysdate,'YYYYMMDDHH24MISS') " + "		,lesson_count=lesson_count+1			"
                            + " where subj=" + StringManager.makeSQL(s_subj) + "   and year=" + StringManager.makeSQL(s_year) + "   and subjseq="
                            + StringManager.makeSQL(s_subjseq) + "   and userid=" + StringManager.makeSQL(s_userid) + "   and lesson="
                            + StringManager.makeSQL(p_lesson);
                } else {
                    // 진도시작체크시 ldate만 update
                    sql = "update tz_betaprogress set " + "		ldate=to_char(sysdate,'YYYYMMDDHH24MISS') " + " where subj=" + StringManager.makeSQL(s_subj)
                            + "   and year=" + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq) + "   and userid="
                            + StringManager.makeSQL(s_userid) + "   and lesson=" + StringManager.makeSQL(p_lesson);
                }
                if (!p_oid.equals("1"))
                    sql += " and oid=" + StringManager.makeSQL(p_oid);
                isOk = connMgr.executeUpdate(sql);
                return "OK";
            }

            // 3. 진도제한 체크
            sql = "select mftype,contenttype  from tz_subj  " + " where subj=" + StringManager.makeSQL(s_subj);
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            ls.next();
            // v_mftype = ls.getString("mftype");
            v_contenttype = ls.getString("contenttype");

            sql = "select NVL(edulimit,0) limit, isgoyong " + "  from tz_subjseq  " + " where subj=" + StringManager.makeSQL(s_subj) + "   and year="
                    + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq);
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            ls.next();
            v_edulimit = ls.getInt("limit");
            v_isgoyong = ls.getString("isgoyong");
            // 차수상의 진도제한이 7보다 작으면 고용보험기준으로 설정(7차시)
            if (v_isgoyong.equals("Y") && v_edulimit < 7)
                v_edulimit = 7;

            if (v_edulimit != 0) {

                sql = "select count(distinct lesson) CNTS from tz_betaprogress" + " where subj=" + StringManager.makeSQL(s_subj) + "   and year="
                        + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq) + "   and userid="
                        + StringManager.makeSQL(s_userid) + "   and lesson !=" + StringManager.makeSQL(p_lesson)
                        + "   and substring(first_end,1,8) = to_char(sysdate, 'YYYYMMDD') ";
                if (!p_oid.equals("1"))
                    sql += " and oid=" + StringManager.makeSQL(p_oid);
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = connMgr.executeQuery(sql);
                ls.next();
                v_myeducnt = ls.getInt("CNTS");
                // System.out.println(sql);System.out.println("v_myeducnt : " + v_myeducnt);
                if (v_myeducnt >= v_edulimit) {
                    return "금일 학습할 수 있는 제한을 넘었으므로 진도체크되지 않습니다.";
                }

                // 4. 고용보험과정이면 이전차시 학습/평가미실시건 존재여부 체크
                if (v_isgoyong.equals("Y")) {
                    v_noteducnt = EduEtc1Bean.get_noteducnt(s_subj, s_year, s_subjseq, s_userid, v_contenttype, p_lesson, p_oid);
                    if (v_noteducnt != 0) {
                        return "학습하지 않은 차시 또는 미응시 평가가 있으므로 진도체크되지 않습니다";
                    }
                }
            }

            // 진도정보 생성 (기존재 Update, 없으면 Insert)
            /*
             * 2005.11.15_하경태 : Oracle -> Mssql sql =
             * "select ldate, first_edu, first_end, to_char(sysdate,'YYYYMMDDHH24MISS') nowtime  from tz_betaprogress  " + " where subj=" +
             * StringManager.makeSQL(s_subj) + "   and year=" + StringManager.makeSQL(s_year) + "   and subjseq=" + StringManager.makeSQL(s_subjseq) +
             * "   and userid=" + StringManager.makeSQL(s_userid) + "   and lesson=" + StringManager.makeSQL(p_lesson) + "   and rownum=1";
             */
            sql = "select * from ( select rownum rnum,  ldate, first_edu, first_end, to_char(sysdate,'YYYYMMDDHH24MISS') nowtime  from tz_betaprogress  "
                    + " where subj=" + StringManager.makeSQL(s_subj) + "   and year=" + StringManager.makeSQL(s_year) + "   and subjseq="
                    + StringManager.makeSQL(s_subjseq) + "   and userid=" + StringManager.makeSQL(s_userid) + "   and lesson="
                    + StringManager.makeSQL(p_lesson) + "   ) where rnum < 2";

            if (!p_oid.equals("1"))
                sql += " and oid=" + StringManager.makeSQL(p_oid);

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            if (ls.next()) { // update (first_end가 없는 경우이므로 total_time=session_time)
                v_ldate = ls.getString("ldate");
                v_sysdate = ls.getString("nowtime");
                sql = "update tz_betaprogress set " + "		ldate=to_char(sysdate,'YYYYMMDDHH24MISS') ";
                if (p_gubun.equals("END")) {
                    v_session_time = EduEtc1Bean.get_duringtime(v_ldate, v_sysdate);
                    sql += "	,session_time=" + StringManager.makeSQL(v_session_time) + "	,total_time  =" + StringManager.makeSQL(v_session_time)
                            + "	,first_end=to_char(sysdate,'YYYYMMDDHH24MISS') " + "	,lesson_count=lesson_count+1			";
                }
                sql += " where subj=? and year=?  and subjseq=? and userid=?  and lesson=? ";
                if (!p_oid.equals("1"))
                    sql += " and oid=" + StringManager.makeSQL(p_oid);
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, s_subj);
                pstmt.setString(2, s_year);
                pstmt.setString(3, s_subjseq);
                pstmt.setString(4, s_userid);
                pstmt.setString(5, p_lesson);
                isOk = pstmt.executeUpdate();
                if (isOk == 1)
                    results = "OK";
                else
                    results = "진도체크(" + p_gubun + ") 문제가 발생하였습니다. 운영자에게 문의바랍니다.";
                // 점수 조정
                // com.credu.complete.FinishBean fb = new com.credu.complete.FinishBean();
                // isOk = fb.ScoreCompute(box);

                isOk = CalcUtil.getInstance().calc_score(connMgr, CalcUtil.STEP, s_subj, s_year, s_subjseq, s_userid);

                if (isOk == 1)
                    results = "OK";
                else
                    results = "진도데이터 생성후 점수조정에 문제가 발생하였습니다.운영자에게 문의바랍니다.";
            } else { // insert (진도시작체크)
                sql = "insert into tz_betaprogress " + "(subj, year, subjseq, userid, lesson , oid, session_time, total_time"
                        + " ,first_edu, first_end, lesson_count, ldate) VALUES " + "(?, ?, ?, ?, ?, ?, '00:00:00.00', '00:00:00.00' "
                        + " ,to_char(sysdate,'YYYYMMDDHH24MISS'),";
                // SET first_end :종료체크 아니면 null, SET 학습카운트: 종료이면 1, 아니면 0
                if (p_gubun.equals("END"))
                    sql += " to_char(sysdate,'YYYYMMDDHH24MISS'), 1";
                else
                    sql += " null, 0";
                sql += ", to_char(sysdate,'YYYYMMDDHH24MISS'))";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, s_subj);
                pstmt.setString(2, s_year);
                pstmt.setString(3, s_subjseq);
                pstmt.setString(4, s_userid);
                pstmt.setString(5, p_lesson);
                pstmt.setString(6, p_oid);
                isOk = pstmt.executeUpdate();
                if (isOk == 1)
                    results = "OK";
                else
                    results = "진도시작체크(" + p_gubun + ") 문제가 발생하였습니다. 운영자에게 문의바랍니다.";

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
        return results;
    }

    /**
     * 진도/목차 리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 진도/목차리스트
     */
    public ArrayList<BetaEduListData> SelectEduList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null, ls2 = null;
        ArrayList<BetaEduListData> list = null;
        String sql = "";
        BetaEduListData x = null;

        String p_subj, p_year, p_subjseq, p_userid;

        String p_isFromLMS = box.getString("p_isFromLMS");
        if (p_isFromLMS.equals("Y")) {
            p_subj = box.getString("p_subj");
            p_year = box.getString("p_year");
            p_subjseq = box.getString("p_subjseq");
            p_userid = box.getString("p_userid");
        } else {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();

            // 점수 재계산 (ALL)
            // int isOk = 0;
            CalcUtil.getInstance().calc_score(connMgr, CalcUtil.ALL, p_subj, p_year, p_subjseq, p_userid);

            list = new ArrayList<BetaEduListData>();
            String v_str2 = " year=" + StringManager.makeSQL(p_year) + " and subjseq=" + StringManager.makeSQL(p_subjseq) + " ";
            String v_str1 = v_str2 + " and userid=" + StringManager.makeSQL(p_userid) + " ";
            sql = "select a.lesson, a.sdesc, a.module, a.isbranch,a.starting,																									"
                    + "       (select sdesc from tz_subjmodule where module=a.module and subj=a.subj) modulenm,                                                         "
                    + "       (select first_edu from tz_betaprogress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson) first_edu,                                      "
                    + "       (select first_end from tz_betaprogress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson) first_end,                                      "
                    + "       (select session_time from tz_betaprogress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson) session_time,                                "
                    + "       (select total_time from tz_betaprogress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson) total_time,                                    "
                    + "       (select lesson_count from tz_betaprogress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson) lesson_count,                                "
                    + "       (select count(ordseq) from tz_projord where subj=a.subj and "
                    + v_str2
                    + " and lesson=a.lesson) cntReport,                                    "
                    + "       (select count(seq) from tz_activity where subj=a.subj and lesson=a.lesson) cntAct,                                                        "
                    + "       (select count(distinct seq) from tz_activity_ans where subj=a.subj and  "
                    + v_str1
                    + " and lesson=a.lesson) cntMyAct                          "
                    + "  from tz_subjlesson a   "
                    + " where a.subj="
                    + StringManager.makeSQL(p_subj) + " order by a.lesson ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                x = new BetaEduListData();
                x.setRecordType("STEP");
                x.setLesson(ls.getString("lesson"));
                x.setSdesc(ls.getString("sdesc"));
                x.setStarting(ls.getString("starting"));
                x.setModule(ls.getString("module"));
                x.setModulenm(ls.getString("modulenm"));
                x.setIsbranch(ls.getString("isbranch"));
                x.setFirst_edu(ls.getString("first_edu"));
                x.setFirst_end(ls.getString("first_end"));
                if (x.getStarting() == null || x.getStarting().equals("")) {
                    x.setIsEducated("P");
                } else if (x.getFirst_end() != null && !x.getFirst_end().equals("")) {
                    x.setIsEducated("Y");
                }
                x.setSession_time(ls.getString("session_time"));
                x.setTotal_time(ls.getString("total_time"));
                x.setLesson_count(ls.getInt("lesson_count"));
                x.setCntReport(ls.getInt("cntReport"));
                if (x.getCntReport() > 0) {
                    x.setCntMyReport(get_repCnt(connMgr, p_subj, p_year, p_subjseq, x.getLesson(), p_userid));
                }
                x.setCntAct(ls.getInt("cntAct"));
                x.setCntMyAct(ls.getInt("cntMyAct"));

                list.add(x);

                // 평가
                sql = "select ptype from tz_exammaster where subj=" + SQLString.Format(p_subj) + "   and year='TEST' and subjseq='TEST' and lesson="
                        + SQLString.Format(x.getLesson()) + " order by ptype";

                if (ls1 != null) {
                    try {
                        ls1.close();
                    } catch (Exception e) {
                    }
                }
                ls1 = connMgr.executeQuery(sql);
                while (ls1.next()) {
                    x = new BetaEduListData();
                    x.setRecordType("EXAM");
                    x.setLesson(ls.getString("lesson"));
                    x.setSdesc(ls.getString("sdesc"));
                    x.setModule(ls.getString("module"));
                    x.setModulenm(ls.getString("modulenm"));
                    x.setPtype(ls1.getString("ptype"));

                    sql = "select score,started,ended from tz_examresult  where subj=" + SQLString.Format(p_subj) + "   and year="
                            + SQLString.Format(p_year) + " and subjseq=" + SQLString.Format(p_subjseq) + "   and ptype='" + ls1.getString("ptype")
                            + "' and userid=" + SQLString.Format(p_userid) + " 	  and lesson=" + SQLString.Format(x.getLesson());

                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                    ls2 = connMgr.executeQuery(sql);
                    if (ls2.next()) {
                        x.setFirst_edu(ls2.getString("started"));
                        x.setFirst_end(ls2.getString("ended"));
                        x.setScore(ls2.getDouble("score"));
                        x.setIsEducated("Y");
                    }
                    list.add(x);
                }
            }

            // set row-span
            String v_module = "";
            for (int i = 0; i < list.size(); i++) {
                x = (BetaEduListData) list.get(i);
                if (!v_module.equals(x.getModule())) {
                    v_module = x.getModule();
                    x.setRowspan(get_count(list, v_module));
                    list.set(i, x);
                }
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
     * 진도/목차 리스트 조회-OBC
     * 
     * @param box receive from the form object and session
     * @return ArrayList OBC-진도/목차리스트
     */
    public ArrayList<BetaEduListData> SelectEduListOBC(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null, ls2 = null;
        ArrayList<BetaEduListData> list = null;
        String sql = "";
        BetaEduListData x = null;

        String p_subj, p_year, p_subjseq, p_userid;
        // set row-span
        String v_module = "01", v_lesson = "00";

        // for Branching
        String v_sdesc = "", v_isbranch = "N";

        /*
         * if (p_isFromLMS.equals("Y")){ p_subj = box.getSession("s_subj"); p_year = box.getSession("s_year"); p_subjseq = box.getSession("s_subjseq");
         * p_userid = box.getSession("userid"); }else{ p_subj = box.getString("p_subj"); p_year = box.getString("p_year"); p_subjseq =
         * box.getString("p_subjseq"); p_userid = box.getString("p_userid"); }
         */

        p_subj = box.getString("p_subj");
        p_year = box.getString("p_year");
        p_subjseq = box.getString("p_subjseq");
        p_userid = box.getSession("userid");

        if (p_subj.equals("") || p_year.equals("") || p_subjseq.equals("")) {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        // get my Branch
        int v_mybranch = EduEtc1Bean.get_mybranch(p_subj, p_year, p_subjseq, p_userid);
        if (v_mybranch == 0 || v_mybranch == 99)
            v_mybranch = 1;

        try {
            connMgr = new DBConnectionManager();

            // 점수 재계산 (ALL)
            // isOk = CalcUtil.getInstance().calc_score(connMgr,CalcUtil.ALL,p_subj,p_year,p_subjseq,p_userid);

            list = new ArrayList<BetaEduListData>();
            String v_str2 = " year=" + StringManager.makeSQL(p_year) + " and subjseq=" + StringManager.makeSQL(p_subjseq) + " ";
            String v_str1 = v_str2 + " and userid=" + StringManager.makeSQL(p_userid) + " ";

            sql = "select a.module, b.sdesc modulenm, a.lesson, c.sdesc , c.isbranch, a.oid, a.sdesc oidnm, a.ordering, d.starting, "
                    + "       (select first_edu from tz_progress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson and oid=a.oid) first_edu,        "
                    + "       (select first_end from tz_progress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson and oid=a.oid) first_end,        "
                    + "       (select session_time from tz_progress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson and oid=a.oid) session_time,  "
                    + "       (select total_time from tz_progress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson and oid=a.oid) total_time,      "
                    + "       (select lesson_count from tz_progress where subj=a.subj and "
                    + v_str1
                    + " and lesson=a.lesson and oid=a.oid) lesson_count,  "
                    + "       (select count(ordseq) from tz_projord where subj=a.subj and "
                    + v_str2
                    + " and lesson=a.lesson) cntReport                    "
                    // + "       (select count(seq) from tz_activity where subj=a.subj and lesson=a.lesson) cntAct,                                     "
                    // + "       (select count(distinct seq) from tz_activity_ans where subj=a.subj and  "+v_str1+" and lesson=a.lesson) cntMyAct       "
                    + "  from tz_subjobj a, tz_subjmodule b, tz_subjlesson c,tz_object d	"
                    + " where a.subj=b.subj and a.subj=c.subj                   "
                    + "   and a.module = b.module and a.module = c.module       "
                    + "   and a.lesson = c.lesson                               "
                    + "   and a.oid = d.oid 									" + "   and a.subj=" + StringManager.makeSQL(p_subj)
                    // + "   and length(d.starting) > 1	    					"
                    + " order by a.module, a.lesson, a.ordering, a.oid          ";
            ls = connMgr.executeQuery(sql);

            // System.out.println("sql=================\n"+sql);
            while (ls.next()) {
                if (!ls.getString("lesson").equals(v_lesson)) {
                    // 평가
                    /*
                     * sql = "select examtype from tz_exampaper where subj="+SQLString.Format(p_subj) +
                     * "   and year='TEST' and subjseq='TEST' and lesson="+SQLString.Format(v_lesson) + " order by ptype"; if(ls1 != null) { try {
                     * ls1.close(); }catch (Exception e) {} } ls1 = connMgr.executeQuery(sql); while(ls1.next()){ x = new BetaEduListData(); x.setRecordType
                     * ("EXAM"); x.setLesson (v_lesson); x.setSdesc (ls.getString("sdesc")); x.setModule (ls.getString("module")); x.setModulenm
                     * (ls.getString("modulenm")); x.setPtype(ls1.getString("ptype")); sql =
                     * "select score,started,ended from tz_examresult  where subj="+SQLString.Format(p_subj) +
                     * "   and year="+SQLString.Format(p_year)+" and subjseq="+SQLString.Format(p_subjseq) +
                     * "   and ptype='"+ls1.getString("ptype")+"' and userid="+SQLString.Format(p_userid) +" 	  and lesson="+SQLString.Format(v_lesson);
                     * if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} } ls2 = connMgr.executeQuery(sql); if(ls2.next()){ x.setFirst_edu
                     * (ls2.getString("started")); x.setFirst_end (ls2.getString("ended")); x.setScore (ls2.getDouble("score")); x.setIsEducated("Y"); }
                     * list.add(x); }
                     */
                    // System.out.println("HERE") ;
                    list = getExamObject(connMgr, list, p_subj, p_year, p_subjseq, p_userid, v_module, v_lesson);
                    v_lesson = ls.getString("lesson");
                    v_module = ls.getString("module");

                    v_isbranch = ls.getString("isbranch");
                    v_sdesc = ls.getString("sdesc");

                }

                x = new BetaEduListData();
                x.setRecordType("STEP");
                x.setModule(ls.getString("module"));
                x.setModulenm(ls.getString("modulenm"));
                x.setLesson(ls.getString("lesson"));
                // x.setSdesc (ls.getString("sdesc"));
                x.setSdesc(v_sdesc);
                x.setOid(ls.getString("oid"));
                if (v_isbranch.equals("Y")) {
                    x.setOidnm(GetCodenm.get_objectBranchName(connMgr, p_subj, x.getLesson(), ls.getInt("ordering"), v_mybranch));
                } else {
                    x.setOidnm(ls.getString("oidnm"));
                }
                x.setIsbranch(ls.getString("isbranch"));
                x.setFirst_edu(ls.getString("first_edu"));
                x.setFirst_end(ls.getString("first_end"));
                x.setStarting(ls.getString("starting"));

                String str = x.getStarting();
                String str2 = x.getFirst_end();

                if (str.length() <= 1) {
                    x.setIsEducated("P");
                } else if (str2.length() > 0) {
                    x.setIsEducated("Y");
                } else {
                    x.setIsEducated("N");
                }
                x.setSession_time(ls.getString("session_time"));
                x.setTotal_time(ls.getString("total_time"));
                x.setLesson_count(ls.getInt("lesson_count"));
                x.setCntReport(ls.getInt("cntReport"));

                if (x.getCntReport() > 0) {
                    x.setCntMyReport(get_repCnt(connMgr, p_subj, p_year, p_subjseq, x.getLesson(), p_userid));
                }

                list.add(x);
            }
            // 마지막 Lesson의 평가Object 얻기
            list = getExamObject(connMgr, list, p_subj, p_year, p_subjseq, p_userid, v_module, v_lesson);

            // set row-span
            v_module = "";
            v_lesson = "";
            for (int i = 0; i < list.size(); i++) {
                x = (BetaEduListData) list.get(i);
                if (!v_module.equals(x.getModule())) {
                    v_module = x.getModule();
                    x.setRowspan(get_count(list, v_module));
                    list.set(i, x);
                }
                if (!v_lesson.equals(x.getLesson())) {
                    v_lesson = x.getLesson();
                    x.setRowspan_lesson(get_count_lesson(list, x.getModule(), x.getLesson()));
                    list.set(i, x);
                }
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

    /** 평가 Object 얻기 **/
    public ArrayList<BetaEduListData> getExamObject(DBConnectionManager connMgr, ArrayList<BetaEduListData> list, String p_subj, String p_year, String p_subjseq, String p_userid,
            String p_module, String p_lesson) {
        ListSet ls = null, ls1 = null, ls2 = null;
        ArrayList<BetaEduListData> list2 = list;
        String sql = "";
        BetaEduListData x = null;

        try {
            sql = "select ptype from tz_exammaster where subj=" + SQLString.Format(p_subj) + "   and year='TEST' and subjseq='TEST' and lesson="
                    + SQLString.Format(p_lesson) + " order by ptype";
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                x = new BetaEduListData();
                x.setRecordType("EXAM");
                x.setLesson(p_lesson);
                x.setSdesc(p_lesson);
                x.setModule(p_module);
                x.setModulenm(p_module);
                x.setPtype(ls1.getString("ptype"));

                sql = "select score,started,ended from tz_examresult  where subj=" + SQLString.Format(p_subj) + "   and year=" + SQLString.Format(p_year)
                        + " and subjseq=" + SQLString.Format(p_subjseq) + "   and ptype='" + ls1.getString("ptype") + "' and userid="
                        + SQLString.Format(p_userid) + " 	  and lesson=" + SQLString.Format(p_lesson);
                if (ls2 != null) {
                    try {
                        ls2.close();
                    } catch (Exception e) {
                    }
                }
                ls2 = connMgr.executeQuery(sql);
                if (ls2.next()) {
                    x.setFirst_edu(ls2.getString("started"));
                    x.setFirst_end(ls2.getString("ended"));
                    x.setScore(ls2.getDouble("score"));
                    x.setIsEducated("Y");
                }
                list2.add(x);
            }
        } catch (Exception ex) {
            System.out.println("ERROR=" + ex.getMessage());
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
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
        }
        return list2;
    }

    /** 모듈 Rowspan 얻기 **/
    public int get_count(ArrayList<BetaEduListData> list, String p_module) {
        int v_cnt = 0;
        BetaEduListData x;
        for (int i = 0; i < list.size(); i++) {
            x = (BetaEduListData) list.get(i);
            if (x.getModule().equals(p_module))
                v_cnt++;
        }
        return v_cnt;
    }

    /** Lesson Rowspan 얻기 **/
    public int get_count_lesson(ArrayList<BetaEduListData> list, String p_module, String p_lesson) {
        int v_cnt = 0;
        BetaEduListData x;
        for (int i = 0; i < list.size(); i++) {
            x = (BetaEduListData) list.get(i);
            if (x.getModule().equals(p_module) && x.getLesson().equals(p_lesson))
                v_cnt++;
        }
        return v_cnt;
    }

    /**
     * 리포트 제출건수 Return
     * 
     * @param DBConnectionManager
     * @param String 과정,년도,차수,레슨,id
     * @return String 제출한 리포트건수 p_lesson : 'ALL'이면 과정차수전체 리포트제출건수(중복제출 무시) 'ALL'아니면 해당 레슨의 리포트제출건수(중복제출 무시)
     */
    public static int get_repCnt(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_userid)
            throws Exception {

        ListSet ls = null, ls1 = null;
        String sql = "", v_reptype, v_projgrp = p_userid;
        int resulti = 0, v_ordseq = 0;

        try {
            sql = " select  lesson,ordseq,reptype  from tz_projord where subj=" + SQLString.Format(p_subj);
            if (!p_lesson.equals("ALL"))
                sql += " and lesson=" + SQLString.Format(p_lesson);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_reptype = ls.getString("reptype");
                v_ordseq = ls.getInt("ordseq");
                if (v_reptype.equals("P")) { // Project이면 tz_projgrp에서 get projgrp
                    sql = "select projgrp from tz_projgrp where subj=" + SQLString.Format(p_subj) + "   and year=" + SQLString.Format(p_subj)
                            + " and subjseq=" + SQLString.Format(p_subjseq) + "   and ordseq=" + v_ordseq + " and userid=" + SQLString.Format(p_userid);
                    if (ls1 != null) {
                        try {
                            ls1.close();
                        } catch (Exception e) {
                        }
                    }
                    ls1 = connMgr.executeQuery(sql);
                    if (ls1.next())
                        v_projgrp = ls1.getString("projgrp");
                    else
                        return 0;
                }
                sql = "select count(distinct ordseq) CNTS from tz_projrep where subj=" + SQLString.Format(p_subj) + "   and year="
                        + SQLString.Format(p_subj) + " and subjseq=" + SQLString.Format(p_subjseq) + "   and ordseq=" + v_ordseq + " and projid="
                        + SQLString.Format(v_projgrp);
                if (ls1 != null) {
                    try {
                        ls1.close();
                    } catch (Exception e) {
                    }
                }
                ls1 = connMgr.executeQuery(sql);
                ls1.next();
                return ls1.getInt("CNTS");
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
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }
        return resulti;
    }

    /**
     * 학습점수정보 Return
     * 
     * @param DBConnectionManager
     * @param String 과정,년도,차수,id
     * @return String
     */
    public BetaEduScoreData SelectEduScore(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null, ls2 = null;
        // ArrayList list = null;
        String sql = "";
        BetaEduScoreData x = null;

        String p_subj, p_year, p_subjseq, p_userid;

        String p_isFromLMS = box.getString("p_isFromLMS");
        if (p_isFromLMS.equals("Y")) {
            p_subj = box.getString("p_subj");
            p_year = box.getString("p_year");
            p_subjseq = box.getString("p_subjseq");
            p_userid = box.getString("p_userid");
        } else {
            p_subj = box.getSession("s_subj");
            p_year = box.getSession("s_year");
            p_subjseq = box.getSession("s_subjseq");
            p_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();
            sql = "select a.score score,tstep,htest,mtest,ftest,report,act,etc1,etc2 "
                    + "		  ,avtstep,avhtest,avmtest,avftest,avreport,avact,avetc1,avetc2" + " 	  ,gradscore,gradstep,gradexam,wstep,wmtest"
                    + "       ,wftest,whtest,wreport,wact,wetc1,wetc2,isgraduated" + "       ,b.edustart edustart,b.eduend eduend"
                    + "  from tz_student a, tz_subjseq b " + " where a.subj=" + StringManager.makeSQL(p_subj) + "   and a.year="
                    + StringManager.makeSQL(p_year) + "   and a.subjseq=" + StringManager.makeSQL(p_subjseq) + "   and a.userid="
                    + StringManager.makeSQL(p_userid) + "   and a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                x = new BetaEduScoreData();
                x.setScore(ls.getDouble("score"));
                x.setTstep(ls.getDouble("tstep"));
                x.setHtest(ls.getDouble("htest"));
                x.setMtest(ls.getDouble("mtest"));
                x.setFtest(ls.getDouble("ftest"));
                x.setReport(ls.getDouble("report"));
                x.setAct(ls.getDouble("act"));
                x.setEtc1(ls.getDouble("etc1"));
                x.setEtc2(ls.getDouble("etc2"));
                x.setAvtstep(ls.getDouble("avtstep"));
                x.setAvhtest(ls.getDouble("avhtest"));
                x.setAvmtest(ls.getDouble("avmtest"));
                x.setAvftest(ls.getDouble("avftest"));
                x.setAvreport(ls.getDouble("avreport"));
                x.setAvact(ls.getDouble("avact"));
                x.setAvetc1(ls.getDouble("avetc1"));
                x.setAvetc2(ls.getDouble("avetc2"));
                x.setGradscore(ls.getDouble("gradscore"));
                x.setGradstep(ls.getDouble("gradstep"));
                x.setGradexam(ls.getDouble("gradexam"));
                x.setWstep(ls.getDouble("wstep"));
                x.setWmtest(ls.getDouble("wmtest"));
                x.setWftest(ls.getDouble("wftest"));
                x.setWhtest(ls.getDouble("whtest"));
                x.setWreport(ls.getDouble("wreport"));
                x.setWact(ls.getDouble("wact"));
                x.setWetc1(ls.getDouble("wetc1"));
                x.setWetc2(ls.getDouble("wetc2"));
                x.setIsgraduated(ls.getString("isgraduated"));
                x.setEdustart(ls.getString("edustart"));
                x.setEduend(ls.getString("eduend"));
                x.makeScoreList(); // 가중치별 점수 HashTable생성
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
        return x;
    }

    /**
     * [OBC] 마스터폼 Tree Data 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList OBCTreeData리스트
     */
    public ArrayList<BetaOBCTreeData> SelectTreeDataList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null, ls2 = null, ls3 = null, ls4 = null, ls5 = null;

        ArrayList<BetaOBCTreeData> list1 = null;
        String sql = "";
        BetaOBCTreeData d = null;
        int j = 1;

        String s_subj = box.getSession("s_subj");
        String s_userid = box.getSession("userid");
        // String p_tmp1 = box.getString("p_tmp1");
        // int p_branch = 0;
        // p_branch = box.getInt("p_branch");

        // String f_branchsubj = "", v_lesson_brname = "";
        // int v_curbranch = 99;

        String v_module = "", v_lesson = "", v_oid = "";

        String f_exist = "N", v_types = "1001";
        String v_extype = "", v_tmp = "", v_ptypenm = "";
        boolean f_go = true;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<BetaOBCTreeData>();

            /* 공지사항-- Applet 최초 노드를 빈 0-Level로 할 수 없으므로 별도 아이콘으로 처리한다. */
            /* 맛보기 */
            /*
             * ORACLE sql = " select a.module module, a.sdesc modulenm, b.lesson lesson, b.sdesc lessonnm, 	" +
             * " 		NVL(b.types,'1001') TYPES, NVL(a.types,'1001') MTYPES                " +
             * "   from tz_subjmodule a, tz_subjlesson b                                      " + "  where a.subj="+StringManager.makeSQL(s_subj) +
             * "    and b.module(+)= a.module                                                 " +
             * "    and b.subj(+)=a.subj                                                      " +
             * "  order by a.module, b.lesson                                                 ";
             */
            sql = " select a.module module, a.sdesc modulenm, b.lesson lesson, b.sdesc lessonnm, "
                    + " 	NVL(b.types,'1001') TYPES, NVL(a.types,'1001') MTYPES, " + "     NVL(b.isbranch,'N') ISBRANCH "
                    + "   from  tz_betasubjmodule a , tz_betasubjlesson b                    " + "  where a.subj=" + StringManager.makeSQL(s_subj)
                    // 2005.11.15_하경태 : Oracle -> Mssql
                    // + "   		and	a.module = b.module(+)	and a.subj=b.subj(+)    "
                    + "   		and	a.module  =  b.module(+) and a.subj  =  b.subj(+)    " + "  order by a.module, b.lesson                                ";
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) { // ##1

                if (!ls.getString("module").equals(v_module)) {
                    v_module = ls.getString("module");
                    d = new BetaOBCTreeData();
                    d.setAp((j - 1) + "||" + v_module + "||" + ls.getString("lesson") + "||MODULE1234||MO||SC||MODULE||0||" + ls.getString("MTYPES")
                            + "||N||javascript:whenObj(" + (j - 1) + ")||" + ls.getString("modulenm") + "||N");
                    d.setAh(v_module + "," + ls.getString("lesson") + ",MODULE1234,MO,SC,MODULE,0,N,XXX");
                    list1.add(d);
                    j++;
                }

                f_go = true; // Tree 출력 허용

                if (!ls.getString("lesson").equals(v_lesson)) {
                    v_lesson = ls.getString("lesson");

                    d = new BetaOBCTreeData();
                    d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||LESSON1234||LE||SC||LESSON||0||" + ls.getString("TYPES")
                            + "||N||javascript:whenObj(" + (j - 1) + ")||" + ls.getString("lessonnm") + "||N");
                    d.setAh(v_module + "," + v_lesson + ",LESSON1234,LE,SC,LESSON,0,N,XXX");
                    list1.add(d);
                    j++;
                }

                if (f_go) {

                    sql = "select a.type TYPE, a.oid OID, a.sdesc SDESC, a.ordering ORDERING," + "		  b.starting STARTING,                    "
                            + "       NVL(a.types,'1001') TYPES, b.filetype FILETYPE, b.npage NPAGE               "
                            + "  from tz_subjobj a, tz_object b                                                   " + " where a.subj="
                            + StringManager.makeSQL(s_subj) + "   and a.oid = b.oid " + "   and a.module=" + StringManager.makeSQL(v_module)
                            + "   and lesson=" + StringManager.makeSQL(v_lesson)
                            + "   and type in ('SC','TM','TT')                                                    " + " order by ordering asc ";
                    if (ls1 != null) {
                        try {
                            ls1.close();
                        } catch (Exception e) {
                        }
                    }
                    ls1 = connMgr.executeQuery(sql);

                    while (ls1.next()) {
                        v_oid = ls1.getString("OID");
                        v_types = ls1.getString("TYPES");

                        sql = "	select count(*) CNTS from tz_betaprogress " + "  where subj=" + StringManager.makeSQL(s_subj) + "    and userid="
                                + StringManager.makeSQL(s_userid) + "    and lesson=" + StringManager.makeSQL(v_lesson) + "    and oid="
                                + StringManager.makeSQL(v_oid);

                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }
                        ls2 = connMgr.executeQuery(sql);
                        ls2.next();
                        if (ls2.getInt("CNTS") > 0)
                            f_exist = "Y";
                        else
                            f_exist = "N";

                        d = new BetaOBCTreeData();
                        d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||" + v_oid + "||SC||SC||" + ls1.getString("FILETYPE") + "||"
                                + ls1.getInt("NPAGE") + "||" + v_types + "||" + f_exist + "||javascript:whenObj(" + (j - 1) + ")||"
                                + ls1.getString("SDESC") + "||N");
                        d.setAh(v_module + "," + v_lesson + "," + v_oid + ",SC,SC," + ls1.getString("FILETYPE") + "," + ls1.getInt("NPAGE") + "," + f_exist
                                + "," + ls1.getString("STARTING") + ",NOT");
                        list1.add(d);
                        j++;

                    }

                    // 평가 Object
                    sql = " select count(*) CNTS  from tz_exammaster " + "  where subj=" + StringManager.makeSQL(s_subj)
                    // + "    and year='TEST' "
                            // + "    and subjseq='TEST' "
                            + "    and lesson=" + StringManager.makeSQL(v_lesson);
                    if (ls4 != null) {
                        try {
                            ls4.close();
                        } catch (Exception e) {
                        }
                    }
                    ls4 = connMgr.executeQuery(sql);
                    ls4.next();
                    if (ls4.getInt("CNTS") > 0) {
                        /*
                         * 2005.11.15_하경태 : Oracle -> Mssql sql = " select  SUBJ,YEAR,SUBJSEQ,LESSON,PTYPE,CNTTOTAL,PARTSTART,PARTEND,	" +
                         * " 		 CNTLEVEL1,CNTLEVEL2,CNTLEVEL3,TESTDATE,TESTTIME,JOINTOTAL, " +
                         * " 		 F_EXPURL,F_MULTIEX,LRESNO,LDATE,F_USEHTML,F_OPEN,          " +
                         * " 		 decode(ptype,'Q',1,'M',2,3) ORD                            " +
                         * "   from tz_exammaster                                                " + "   where subj  ="+StringManager.makeSQL(s_subj) +
                         * "     and lesson="+StringManager.makeSQL(v_lesson) + "     and year='TEST' and subjseq='TEST' order by ORD";
                         */

                        sql = " select  SUBJ,YEAR,SUBJSEQ,LESSON,PTYPE,CNTTOTAL,PARTSTART,PARTEND,	"
                                + " 		 CNTLEVEL1,CNTLEVEL2,CNTLEVEL3,TESTDATE,TESTTIME,JOINTOTAL, "
                                + " 		 F_EXPURL,F_MULTIEX,Luserid,LDATE,F_USEHTML,          "
                                + " 		 case ptype When 'Q' Then 1 When 'M' Then 2 Else 3 End  ORD  "
                                + "   from tz_exammaster                                                " + "   where subj  ="
                                + StringManager.makeSQL(s_subj) + "     and lesson=" + StringManager.makeSQL(v_lesson)
                                + "     and year='TEST' and subjseq='TEST' order by ORD";

                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }
                        ls2 = connMgr.executeQuery(sql);

                        while (ls2.next()) {
                            v_extype = "T" + ls2.getString("PTYPE");
                            v_tmp = v_extype + "000000" + ls2.getString("LESSON");

                            sql = " select count(*) CNTS from tz_examresult " + "  where subj=" + StringManager.makeSQL(s_subj) + "    and userid="
                                    + StringManager.makeSQL(s_userid) + "    and lesson=" + StringManager.makeSQL(v_lesson) + "    and ptype="
                                    + StringManager.makeSQL(ls2.getString("PTYPE"));

                            if (ls3 != null) {
                                try {
                                    ls3.close();
                                } catch (Exception e) {
                                }
                            }
                            ls3 = connMgr.executeQuery(sql);
                            ls3.next();
                            if (ls3.getInt("CNTS") > 0)
                                f_exist = "Y";
                            else
                                f_exist = "N";

                            if (ls2.getString("PTYPE").equals("M"))
                                v_ptypenm = "Middle";
                            else if (ls2.getString("PTYPE").equals("Q"))
                                v_ptypenm = "QUIZ";
                            else
                                v_ptypenm = "Final";

                            d = new BetaOBCTreeData();
                            d.setAp((j - 1) + "||" + v_module + "||" + v_lesson + "||" + v_tmp + "||SC||" + v_extype + "||TEST||0||" + "0001||" + f_exist
                                    + "||javascript:whenObj(" + (j - 1) + ")||" + v_ptypenm + " Test||N");
                            d.setAh(v_module + "," + v_lesson + "," + v_tmp + ",SC," + v_extype + ",TEST,0," + f_exist + ",zedu_gong.exjumps");
                            list1.add(d);
                            j++;
                        }
                    }
                } // end if f_go==true
                // i++;
            } // End while of ##1

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
            if (ls5 != null) {
                try {
                    ls5.close();
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
     * SCO 매핑 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    public ArrayList<BetaSCOData> SelectMappingSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList<BetaSCOData> list1 = null;
        String sql = "";
        BetaSCOData data = null;

        String p_subj = box.getString("p_subj");
        String p_year = box.getString("p_year");
        String p_subjseq = box.getString("p_subjseq");
        String p_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<BetaSCOData>();

            sql = "select distinct a.oid, a.scotitle, a.scolocate, a.sdesc , a.starting, a.highoid, "
                    + " a.parameterstring, a.datafromlms, a.prerequisites, a.masteryscore,  "
                    + " a.maxtimeallowed, a.timelimitaction, b.ordering, b.module , b.lesson , level as scothelevel, "
                    + " (SELECT lessonstatus FROM tz_betaprogress WHERE subj = '"
                    + p_subj
                    + "'  AND YEAR = '"
                    + p_year
                    + "' "
                    + "  AND subjseq = '"
                    + p_subjseq
                    + "'  AND userid = '"
                    + p_userid
                    + "'  AND OID = b.OID  AND lesson = b.lesson) AS jindostatus "
                    + "from tz_object a , tz_subjobj b   "
                    + " where a.oid = b.oid and  b.subj = '"
                    + p_subj
                    + "' "
                    + " connect by a.highoid = prior a.oid start with a.highoid is null  order by b.module asc , b.lesson asc , b.ordering asc , a.oid asc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new BetaSCOData();
                data.setOid(ls.getString("oid"));
                data.setSdesc(ls.getString("sdesc"));
                data.setScolocate(ls.getString("scolocate"));
                data.setScoTitle(ls.getString("scotitle"));
                data.setOrdering(ls.getInt("ordering"));
                data.setModule(ls.getString("module"));
                data.setLesson(ls.getString("lesson"));
                data.setStarting(ls.getString("starting"));
                data.setParameterstring(ls.getString("parameterstring"));
                data.setDatafromlms(ls.getString("datafromlms"));
                data.setPrerequisites(ls.getString("prerequisites"));
                data.setMasteryscore(ls.getInt("masteryscore"));
                data.setMaxtimeallowed(ls.getString("maxtimeallowed"));
                data.setTimelimitaction(ls.getString("timelimitaction"));
                data.setHighoid(ls.getString("highoid"));
                data.setThelevel(ls.getInt("scothelevel"));
                data.setJindoStatus(ls.getString("jindostatus"));
                list1.add(data);
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
     * 과정명 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object리스트
     */
    public String SelectSubjName(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // BetaSCOData data = null;
        String subjnm = "";

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();

            sql = "select subjnm  " + "from tz_subj    " + " where subj = '" + p_subj + "' ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                subjnm = ls.getString("subjnm");
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
        return subjnm;
    }

}
