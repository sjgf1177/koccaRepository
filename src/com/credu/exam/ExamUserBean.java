//**********************************************************
//1. 제      목: 온라인테스트 사용자
//2. 프로그램명: ExamUserBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//**********************************************************

package com.credu.exam;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.complete.StoldData;
import com.credu.library.CalcUtil;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.EduEtc1Bean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
public class ExamUserBean {

    public ExamUserBean() {
    }

    /**
     * 사용자 해당과정리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */

    public ArrayList<DataBox> SelectUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";

        try {
            String s_userid = box.getSession("userid");
            String v_subj = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select b.subj,   a.year,    a.subjseq,   a.lesson, a.startdt, a.enddt,   \n";
            sql += "       a.examtype,  a.papernum,   a.lessonstart, a.lessonend,  a.examtime, a.exampoint, a.examcnt, a.totalscore,    \n";
            sql += "       a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer,   \n";
            sql += "		  (select eduend from tz_subjseq where subj=a.subj and year=a.year and subjseq=a.subjseq) eduend,  \n";
            sql += "       a.isopenexp,  a.retrycnt, a.progress, b.subjnm,  get_codenm(" + SQLString.Format(ExamBean.PTYPE) + ", nvl(a.examtype, '')) examtypenm,   \n";
            sql += "       nvl((select score from tz_examresult where subj=a.subj    \n";
            sql += "        and year= a.year and subjseq=a.subjseq and lesson=a.lesson and  examtype=a.examtype    \n";
            sql += "        and papernum=a.papernum and  userid='" + s_userid + "'), '') score,                     \n";
            sql += "       (select count(userid) from tz_examresult where subj=a.subj    \n";
            sql += "        and year= a.year and subjseq=a.subjseq and lesson=a.lesson and  examtype=a.examtype    \n";
            sql += "        and papernum=a.papernum and  userid='" + s_userid + "') resultcnt,                     \n";
            sql += "       nvl((select ldate from tz_examresult where subj=a.subj    \n";
            sql += "        and year= a.year and subjseq=a.subjseq and lesson=a.lesson and  examtype=a.examtype    \n";
            sql += "        and papernum=a.papernum and  userid='" + s_userid + "'), '') ldate                     \n";
            sql += "  from tz_exampaper a,  tz_subj       b    \n";
            sql += " where a.subj   = b.subj    \n";
            sql += "   and a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.year   = " + SQLString.Format(v_year);
            sql += "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            sql += " order by a.subj, a.year, a.subjseq, a.lesson, a.examtype    \n";

            ls = connMgr.executeQuery(sql);
            System.out.println("ExamUserBean SelectUserList 해당과정리스트:" + sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                System.out.println("score = " + ls.getString("score"));
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
     * 사용자 해당과정리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */

    public ArrayList<String> SelectUserResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<String> list = null;
        DataBox dbox = null;
        String sql = "";
        int v_result = 0;

        try {
            String s_userid = box.getSession("userid");
            String v_subj = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");

            connMgr = new DBConnectionManager();

            list = new ArrayList<String>();

            sql = "select b.subj,   a.year,    a.subjseq,   a.lesson,  \n";
            sql += "       a.examtype,  a.papernum,   a.lessonstart, a.lessonend,  a.examtime, a.exampoint, a.examcnt, a.totalscore,    \n";
            sql += "       a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer,   \n";
            sql += "       a.isopenexp,  a.retrycnt,  a.progress, b.subjnm,  get_codenm(" + SQLString.Format(ExamBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm,   \n";
            sql += "       (select score from tz_examresult where subj=a.subj    \n";
            sql += "        and year= a.year and subjseq=a.subjseq and lesson=a.lesson and  examtype=a.examtype    \n";
            sql += "        and papernum=a.papernum and  userid='" + s_userid + "') score                              \n";
            sql += "  from tz_exampaper a,   \n";
            sql += "       tz_subj      b    \n";
            sql += " where a.subj   = b.subj    \n";
            sql += "   and a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.year   = " + SQLString.Format(v_year);
            sql += "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            sql += " order by a.subj, a.year, a.subjseq, a.lesson, a.examtype    \n";

            ls = connMgr.executeQuery(sql);
            System.out.println("ExamUserBean SelectUserResultList 사용자 해당과정리스트:" + sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                v_result = SelectUserResultList(connMgr, v_subj, v_year, v_subjseq, s_userid, dbox.getString("d_examtype"), dbox.getInt("d_papernum"));
                list.add(String.valueOf(v_result));
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
     * 학습자 결과 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    public int SelectUserResultList(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, String p_examtype, int p_papernum) throws Exception {

        //        ArrayList QuestionExampleDataList  = null;
        int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select count(score) examcount ";
            sql += "  from tz_examresult  ";
            sql += " where subj = " + SQLString.Format(p_subj);
            sql += " and year = " + SQLString.Format(p_year);
            sql += " and subjseq = " + SQLString.Format(p_subjseq);
            sql += " and examtype = " + SQLString.Format(p_examtype);
            sql += " and userid = " + SQLString.Format(p_userid);
            sql += " and papernum = " + SQLString.Format(p_papernum);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                v_result = dbox.getInt("d_examcount");
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
        return v_result;
    }

    /*
     * public String InsertRetry(RequestBox box) throws Exception {
     * DBConnectionManager connMgr = null; String sql = ""; int isOk = 0;
     * 
     * String v_subj = box.getString("p_subj"); String v_year =
     * box.getString("p_gyear"); String v_subjseq = box.getString("p_subjseq");
     * 
     * String v_lesson = box.getString("p_lesson"); String v_examtype =
     * box.getString("p_examtype"); int v_papernum = box.getInt("p_papernum");
     * String v_userid = box.getString("p_userid"); String v_answer =
     * box.getString("p_answer"); String v_exam = box.getString("p_exam");
     * 
     * String v_started = box.getString("p_started"); String v_ended =
     * box.getString("p_ended"); double v_time = 0;
     * 
     * int v_examcnt = box.getInt("p_examcnt"); int v_exampoint =
     * box.getInt("p_exampoint");
     * 
     * int v_retry = box.getInt("p_retry"); int v_realretry =
     * box.getInt("p_realretry");
     * 
     * int v_score = 0; int v_answercnt = 0;
     * 
     * String v_luserid = box.getSession("userid");
     * 
     * int v_exist = 0;
     * 
     * String v_result = "0,"; Vector v_v = null;
     * 
     * try { connMgr = new DBConnectionManager(); connMgr.setAutoCommit(false);
     * 
     * if (v_retry == 0 || (v_retry - v_realretry) == 1) { v_result =
     * "1,"+String.valueOf(InsertResult(box)); } else{
     * 
     * v_v = getScore(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype,
     * v_papernum, v_userid, v_exam, v_answer, v_examcnt, v_exampoint);
     * 
     * v_score = Integer.parseInt((String)v_v.get(0)); v_answercnt =
     * Integer.parseInt((String)v_v.get(1));
     * 
     * v_time = FormatDate.getSecDifference(v_started, v_ended);
     * 
     * v_result +=(String.valueOf(v_score)) + ","; v_result
     * +=(String.valueOf(v_answercnt)) ;
     * 
     * 
     * } } catch(Exception ex) { connMgr.rollback();
     * ErrorManager.getErrorStackTrace(ex, box, sql); throw new
     * Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally {
     * connMgr.commit(); if(connMgr != null) { try {
     * connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception
     * e10) {} } } return v_result; }
     */

    /**
     * 평가 제출
     * 
     * @param box receive from the form object
     * @return int
     */

    public int InsertResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");

        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");
        String v_answer = box.getString("p_answer");
        String v_exam = box.getString("p_exam");

        String v_started = box.getString("p_started");
        String v_ended = box.getString("p_ended");
        double v_time = 0;

        int v_examcnt = box.getInt("p_examcnt");
        int v_exampoint = box.getInt("p_exampoint");
        int v_userretry = box.getInt("p_userretry");

        int v_score = 0;
        int v_answercnt = 0;

        String v_luserid = box.getSession("userid");

        int v_exist = 0;

        Vector<String> v_result = null;

        String v_corrected = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // Added by LeeSuMin.. 2004.02.24
            // 현재 학습중인 학생신분이 아니므로 평가결과를 저장하지 않으며 결과를 확인하실 수 없습니다.
            if (!EduEtc1Bean.isCurrentStudent(v_subj, v_year, v_subjseq, v_userid).equals("Y"))
                return 97;

            // 평가지,평가자별 카운트
            v_exist = chkResultExist(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid);

            // 평가점수채점
            v_result = getScore(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_exam, v_answer, v_examcnt, v_exampoint);

            v_score = Integer.parseInt((String) v_result.get(0)); // 점수

            v_answercnt = Integer.parseInt((String) v_result.get(1));
            v_corrected = (String) v_result.get(2);

            v_time = FormatDate.getMinDifference(v_started, v_ended);

            StringTokenizer st2 = new StringTokenizer(v_answer, ExamBean.SPLIT_COMMA);

            v_answer = "";
            while (st2.hasMoreElements()) {

                String s = StringManager.trim((String) st2.nextToken());

                if (s.length() == 0)
                    v_answer = v_answer + " " + ",";
                else
                    v_answer = v_answer + s + ",";
            }
            System.out.println("v_userretry>>" + v_userretry);
            System.out.println("획득점수" + v_score);

            if (v_userretry != 0)
                v_userretry = v_userretry - 1;

            if (v_exist == 0) {

                isOk = InsertTZ_examresult(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_exam, v_examcnt, v_exampoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_userretry, v_luserid);

                isOk = CalcUtil.getInstance().calc_score(connMgr, CalcUtil.EXAM + "", v_subj, v_year, v_subjseq, v_userid, v_luserid, new StoldData());

            } else {

                // 기존점수가 높으면 업데이트 안됨.
                int is_score = IsResultScore(connMgr, box);

                System.out.println(" 기존점수" + is_score);
                System.out.println("획득점수" + v_score);

                // 획득점수 > 기존점수
                if (v_score > is_score) {

                    isOk = UpdateTZ_examresult(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_exam, v_examcnt, v_exampoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_userretry, v_luserid);

                    isOk = CalcUtil.getInstance().calc_score(connMgr, CalcUtil.EXAM + "", v_subj, v_year, v_subjseq, v_userid, v_luserid, new StoldData());

                } else {

                    // 재응시횟수 업데이트
                    isOk = UpdateUserUserretry(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_userretry);

                }

            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.commit();
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
     * 평가 결과 등록 처리
     * 
     * @param box receive from the form object
     * @return int
     */
    public int InsertTZ_examresult(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid, String p_exam, int p_examcnt, int p_exampoint, int p_score,
            int p_answercnt, String p_started, String p_ended, double p_time, String p_answer, String p_corrected, int p_userretry, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql = " insert into TZ_EXAMRESULT ";
            sql += " (subj,   year,      subjseq,  lesson, ";
            sql += "  examtype,  papernum, userid, ";
            sql += "  exam, examcnt, exampoint, score, answercnt,   started, ";
            sql += "  ended,  time, answer, corrected, userretry,  luserid,  ldate) ";
            sql += " values ";
            sql += " (?,      ?,         ?,         ?, ";
            sql += "  ?,      ?,         ?,          ";
            sql += "  ?,      ?,         ?,         ?,                   ?,                    ?, ";
            sql += "  ?,      ?,         ?,         ?,        ?,        ?,      ? ) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_year);
            pstmt.setString(3, p_subjseq);
            pstmt.setString(4, p_lesson);
            pstmt.setString(5, p_examtype);
            pstmt.setInt(6, p_papernum);
            pstmt.setString(7, p_userid);
            pstmt.setString(8, p_exam);
            pstmt.setInt(9, p_examcnt);
            pstmt.setInt(10, p_exampoint);
            pstmt.setInt(11, p_score);
            pstmt.setInt(12, p_answercnt);
            pstmt.setString(13, p_started);
            pstmt.setString(14, p_ended);
            pstmt.setDouble(15, p_time);
            pstmt.setString(16, p_answer);
            pstmt.setString(17, p_corrected);
            pstmt.setInt(18, p_userretry);
            pstmt.setString(19, p_luserid);
            pstmt.setString(20, FormatDate.getDate("yyyyMMddHHmmss"));

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
     * 평가 결과 수정 처리
     * 
     * @param box receive from the form object
     * @return int
     */
    public int UpdateTZ_examresult(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid, String p_exam, int p_examcnt, int p_exampoint, int p_score,
            int p_answercnt, String p_started, String p_ended, double p_time, String p_answer, String p_corrected, int p_userretry, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql = " update TZ_EXAMRESULT ";
            sql += "    set exam   = ?, ";
            sql += "        examcnt   = ?, ";
            sql += "        exampoint   = ?, ";
            sql += "        score   = ?, ";
            sql += "        answercnt  = ?, ";
            sql += "        started     = ?, ";
            sql += "        ended    = ?, ";
            sql += "        time    = ?, ";
            sql += "        answer = ?, ";
            sql += "        corrected = ?, ";
            sql += "        userretry = ?, ";
            sql += "        luserid = ?, ";
            sql += "        ldate   = ? ";
            sql += "  where subj    = ? ";
            sql += "    and year    = ? ";
            sql += "    and subjseq = ? ";
            sql += "    and lesson = ? ";
            sql += "    and examtype = ? ";
            sql += "    and papernum = ? ";
            sql += "    and userid  = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_exam);
            pstmt.setInt(2, p_examcnt);
            pstmt.setInt(3, p_exampoint);
            pstmt.setInt(4, p_score);
            pstmt.setInt(5, p_answercnt);
            pstmt.setString(6, p_started);
            pstmt.setString(7, p_ended);
            pstmt.setDouble(8, p_time);
            pstmt.setString(9, p_answer);
            pstmt.setString(10, p_corrected);
            pstmt.setInt(11, p_userretry);
            pstmt.setString(12, p_luserid);
            pstmt.setString(13, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(14, p_subj);
            pstmt.setString(15, p_year);
            pstmt.setString(16, p_subjseq);
            pstmt.setString(17, p_lesson);
            pstmt.setString(18, p_examtype);
            pstmt.setInt(19, p_papernum);
            pstmt.setString(20, p_userid);

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
     * 평가 결과 수정 처리 (재채점)
     * 
     * @param box receive from the form object
     * @return int
     */
    public int UpdateTZ_examresult(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid, int p_score, int p_answercnt, String p_answer, String p_corrected,
            String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql = " update TZ_EXAMRESULT set  ";
            sql += "        score      = ?, ";
            sql += "        answercnt  = ?, ";
            sql += "        answer     = ?, ";
            sql += "        corrected  = ?, ";
            sql += "        luserid = ?, ";
            sql += "        ldate   = ? ";
            sql += "  where subj    = ? ";
            sql += "    and year    = ? ";
            sql += "    and subjseq = ? ";
            sql += "    and lesson = ? ";
            sql += "    and examtype = ? ";
            sql += "    and papernum = ? ";
            sql += "    and userid  = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, p_score);
            pstmt.setInt(2, p_answercnt);
            pstmt.setString(3, p_answer);
            pstmt.setString(4, p_corrected);
            pstmt.setString(5, p_luserid);
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(7, p_subj);
            pstmt.setString(8, p_year);
            pstmt.setString(9, p_subjseq);
            pstmt.setString(10, p_lesson);
            pstmt.setString(11, p_examtype);
            pstmt.setInt(12, p_papernum);
            pstmt.setString(13, p_userid);

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
     * 평가결과 평가지,평가자별 카운트
     * 
     * @param
     * @return int
     */
    public int chkResultExist(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int v_exist = 0;

        try {
            sql = "select count(*)  cnt ";
            sql += "  from tz_examresult  ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            sql += "   and lesson  = " + SQLString.Format(p_lesson);
            sql += "   and examtype   = " + SQLString.Format(p_examtype);
            sql += "   and papernum= " + SQLString.Format(p_papernum);
            sql += "   and userid  = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_exist = ls.getInt("cnt");
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
        return v_exist;
    }

    /**
     * 평가 점수 채점
     * 
     * @param
     * @return Vector
     */

    public Vector<String> getScore(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid, String p_exam, String p_answer, int p_examcnt, int p_exampoint)
            throws Exception {

        Vector<String> v_examnums = new Vector<String>();
        int v_examnum = 0;
        Vector<String> v_answers = new Vector<String>();
        String v_answer = "";

        StringTokenizer st1 = null;
        StringTokenizer st2 = null;

        int v_score = 0;
        int v_answercnt = 0;
        String v_iscorrect = "";
        int v_temp = 0;

        Vector<String> v_result = new Vector<String>();

        try {
            //System.out.println("p_exam " + p_exam);
            //System.out.println("p_answer " + p_answer);
            st1 = new StringTokenizer(p_exam, ExamBean.SPLIT_COMMA);
            while (st1.hasMoreElements()) {
                v_examnums.add(StringManager.trim((String) st1.nextToken()));
            }

            st2 = new StringTokenizer(p_answer, ExamBean.SPLIT_COMMA);

            while (st2.hasMoreElements()) {
                String s = StringManager.trim((String) st2.nextToken());//System.out.println("s :" + s + "a");

                v_answers.add(s);
            }
            System.out.println("평가점수채점 : ");
            System.out.println("v_answers.size() : " + v_answers.size());
            for (int i = 0; i < v_examnums.size(); i++) {

                v_examnum = Integer.parseInt((String) v_examnums.get(i));
                v_answer = (String) v_answers.get(i);

                v_temp = MakeExamResult(connMgr, p_subj, v_examnum, v_answer);

                v_score += (v_temp * p_exampoint);
                v_answercnt += v_temp;
                if (i == v_examnums.size() - 1) {
                    v_iscorrect += String.valueOf(v_temp);
                } else {
                    v_iscorrect += String.valueOf(v_temp) + ",";
                }

            }

            v_result.add(String.valueOf(v_score));
            v_result.add(String.valueOf(v_answercnt));
            v_result.add(v_iscorrect);

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return v_result;
    }

    /**
     * 평가 점수 계산
     * 
     * @param box receive from the form object
     * @return int
     */
    public int MakeExamResult(DBConnectionManager connMgr, String p_subj, int p_examnum, String p_answer) throws Exception {

        int isOk = 0;

        ListSet ls = null;
        String sql = "";
        @SuppressWarnings("unused")
        DataBox dbox = null;

        String v_examtype = "";

        try {
            sql = "select examtype ";
            sql += "  from tz_exam ";
            sql += " where subj      = " + SQLString.Format(p_subj);
            sql += " and examnum        = " + SQLString.Format(p_examnum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_examtype = ls.getString("examtype");
            }

            if (v_examtype.length() > 0) {

                if (v_examtype.equals("2")) { // 주관식

                    sql = "select count(b.selnum) cnt  ";
                    sql += "   from tz_exam a,  ";
                    sql += "   (select subj, examnum, selnum, seltext, isanswer  from tz_examsel where subj    = " + SQLString.Format(p_subj);
                    sql += "   and examnum    = " + SQLString.Format(p_examnum) + "	)  b ";
                    sql += "   where a.subj = b.subj  ";
                    sql += "   and a.examnum = b.examnum  ";
                    sql += "   and b.seltext   like  " + SQLString.Format(p_answer.trim());

                } else { // 객관식, ox식

                    int p_selnum = 0;
                    if (!p_answer.trim().equals("")) {
                        p_selnum = Integer.parseInt(p_answer.trim());
                    }

                    sql = "select count(b.selnum) cnt  ";
                    sql += "   from tz_exam a,  ";
                    sql += "   (select subj, examnum, selnum, isanswer  from tz_examsel where subj    = " + SQLString.Format(p_subj);
                    sql += "   and examnum    = " + SQLString.Format(p_examnum) + "	)  b ";
                    sql += "   where b.isanswer    =   'Y'   ";
                    sql += "   and a.subj = b.subj  ";
                    sql += "   and a.examnum = b.examnum  ";
                    sql += "   and b.selnum    =    " + p_selnum;
                }

                ls.close();
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    if (ls.getInt("cnt") > 0) {
                        isOk = 1;
                    }

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

        return isOk;
    }

    /**
     * 평가자 결과 보기
     * 
     * @param
     * @return ArrayList
     */

    public ArrayList<ArrayList<DataBox>> SelectUserPaperResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<ArrayList<DataBox>> list = null;

        //        String v_action =
        box.getStringDefault("p_action", "change");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list = getPaperResultList(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid);
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
     * 평가자 결과 보기
     * 
     * @param
     * @return ArrayList
     */

    public ArrayList<ArrayList<DataBox>> getPaperResultList(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid) throws Exception {

        ArrayList<ArrayList<DataBox>> QuestionExampleDataList = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select * from ( select rownum rnum, a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected ";
            sql += "  from tz_exampaper  a, ";
            sql += "       tz_examresult b ";
            sql += " where a.subj    = b.subj ";
            sql += "   and a.year    = b.year ";
            sql += "   and a.subjseq = b.subjseq ";
            sql += "   and a.lesson = b.lesson ";
            sql += "   and a.examtype = b.examtype ";
            sql += "   and a.papernum = b.papernum ";
            sql += " and b.subj = " + SQLString.Format(p_subj);
            sql += " and b.year = " + SQLString.Format(p_year);
            sql += " and b.subjseq = " + SQLString.Format(p_subjseq);
            sql += " and b.lesson = " + SQLString.Format(p_lesson);
            sql += " and b.examtype = " + SQLString.Format(p_examtype);
            sql += " and b.papernum = " + SQLString.Format(p_papernum);
            sql += " and b.userid = " + SQLString.Format(p_userid);
            sql += " order by a.subj, a.year, a.subjseq, b.userid ) where rnum < 2";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            if (dbox != null)
                QuestionExampleDataList = getExampleData(connMgr, p_subj, dbox.getString("d_exam"));
            System.out.println("\n\n\n2. not yet err!!");

        } catch (Exception ex) {
            System.out.println(ex + "\n" + sql);
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
        return QuestionExampleDataList;
    }

    /**
     * 평가 결과 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */

    public ArrayList<Object> SelectUserPaperResult2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<Object> list = null;

        // String v_action = box.getStringDefault("p_action",  "change");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list = getPaperResultList2(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid);
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
     * 평가 결과 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<Object> getPaperResultList2(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid) throws Exception {

        ArrayList<Object> list = new ArrayList<Object>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        StringTokenizer st = null;
        Vector<String> v_answer = null;
        StringTokenizer st2 = null;
        Vector<String> v_corrected = null;

        try {
            // 수정일 : 05.11.09 수정자 : 이나연 _ rownum , (+) 수정
            //          sql = "select a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected, ";
            sql = "select * from ( select rownum rnum, a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected, ";
            sql += " b.examcnt, b.answercnt, b.score, b.exampoint, b.started, b.ended,  ";
            sql += " a.year, a.lesson, a.subjseq, c.subjnm,  get_codenm(" + SQLString.Format(ExamBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm  ";
            sql += "  from tz_exampaper  a, ";
            sql += "       tz_examresult b, ";
            sql += "       tz_subj c ";
            //          sql+= " where a.subj(+)        = c.subj  ";
            sql += " where a.subj  = c.subj  ";
            sql += " and a.subj    = b.subj ";
            sql += "   and a.year    = b.year ";
            sql += "   and a.subjseq = b.subjseq ";
            sql += "   and a.lesson = b.lesson ";
            sql += "   and a.examtype = b.examtype ";
            sql += "   and a.papernum = b.papernum ";
            sql += " and b.subj = " + SQLString.Format(p_subj);
            sql += " and b.year = " + SQLString.Format(p_year);
            sql += " and b.subjseq = " + SQLString.Format(p_subjseq);
            sql += " and b.lesson = " + SQLString.Format(p_lesson);
            sql += " and b.examtype = " + SQLString.Format(p_examtype);
            sql += " and b.papernum = " + SQLString.Format(p_papernum);
            sql += " and b.userid = " + SQLString.Format(p_userid);
            //          sql+= "   and rownum <= 1 ";
            sql += " order by a.subj, a.year, a.subjseq, b.userid ) where rnum < 2";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                st = new StringTokenizer(dbox.getString("d_answer"), ",");
                v_answer = new Vector<String>();
                while (st.hasMoreElements()) {
                    v_answer.add(st.nextToken());
                }
                st2 = new StringTokenizer(dbox.getString("d_corrected"), ",");
                v_corrected = new Vector<String>();
                while (st2.hasMoreElements()) {
                    v_corrected.add(st2.nextToken());
                }
            }
            sql = "select count(b.userid) cnt ";
            sql += "     from  tz_examresult b ";
            sql += " where b.subj = " + SQLString.Format(p_subj);
            sql += " and b.year = " + SQLString.Format(p_year);
            sql += " and b.subjseq = " + SQLString.Format(p_subjseq);
            sql += " and b.lesson = " + SQLString.Format(p_lesson);
            sql += " and b.examtype = " + SQLString.Format(p_examtype);
            sql += " and b.papernum = " + SQLString.Format(p_papernum);
            sql += " and b.score >  " + SQLString.Format(dbox.getString("d_score"));

            ls.close();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox.put("d_overman", ls.getString("cnt"));
            }

            list.add(dbox);
            list.add(v_answer);
            list.add(v_corrected);

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

    /**
     * 문제당 문제항목 리스트
     * 
     * @param box receive from the form object and session
     * @return QuestionExampleData 평가문제
     */

    public ArrayList<ArrayList<DataBox>> getExampleData(DBConnectionManager connMgr, String p_subj, String p_examnums) throws Exception {
        //        Hashtable hash = new Hashtable();
        ArrayList<ArrayList<DataBox>> blist = new ArrayList<ArrayList<DataBox>>();
        ArrayList<DataBox> list = null;

        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        StringTokenizer st = null;

        try {

            st = new StringTokenizer(p_examnums, ",");

            while (st.hasMoreElements()) {

                int examnum = Integer.parseInt(st.nextToken());

                sql = "select a.subj,     a.examnum,  a.lesson,  a.examtype, ";
                sql += "       a.examtext,      a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio, ";
                sql += "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
                sql += "       b.selnum,   b.seltext,  b.isanswer,  ";
                sql += "       c.codenm    examtypenm, ";
                sql += "       d.codenm    levelsnm    ";
                sql += "  from tz_exam      a, ";
                sql += "       tz_examsel   b, ";
                sql += "       tz_code      c, ";
                sql += "       tz_code      d  ";
                // 수정일 : 05.11.09 수정자 : 이나연 _ (+) 수정
                //          sql+= " where a.subj     = b.subj(+) ";
                //          sql+= "   and a.examnum  = b.examnum(+) ";
                sql += " where a.subj      =  b.subj(+) ";
                sql += "   and a.examnum   =  b.examnum(+) ";
                sql += "   and a.examtype  = c.code ";
                sql += "   and a.levels    = d.code ";
                sql += "   and a.subj      = " + SQLString.Format(p_subj);
                sql += "   and a.examnum   = " + examnum;
                sql += "   and c.gubun     = " + SQLString.Format(ExamBean.EXAM_TYPE);
                sql += "   and d.gubun     = " + SQLString.Format(ExamBean.EXAM_LEVEL);
                sql += " order by a.examnum, b.selnum ";
                Log.info.println("examtext : " + sql);
                ls = connMgr.executeQuery(sql);
                list = new ArrayList<DataBox>();

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);

                }
                blist.add(list);
            }
        } catch (Exception ex) {
            System.out.println(ex);
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
        return blist;
    }

    /**
     * 평가 결과 문제 데이타
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<String> getUserData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<String> list = null;
        int v_result = 0;

        String v_userid = box.getSession("userid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<String>();
            v_result = getUserData(connMgr, v_subj, v_year, v_subjseq, v_userid, "M");
            list.add(String.valueOf(v_result));
            v_result = getUserData(connMgr, v_subj, v_year, v_subjseq, v_userid, "H");
            list.add(String.valueOf(v_result));
            v_result = getUserData(connMgr, v_subj, v_year, v_subjseq, v_userid, "E");
            list.add(String.valueOf(v_result));
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
     * 평가 결과 문제 데이타
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int getUserData(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, String p_examtype) throws Exception {

        //        ArrayList QuestionExampleDataList  = null;
        int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select count(papernum) examcount ";
            sql += "  from tz_exampaper  ";
            sql += " where subj = " + SQLString.Format(p_subj);
            sql += " and year = " + SQLString.Format(p_year);
            sql += " and subjseq = " + SQLString.Format(p_subjseq);
            sql += " and examtype = " + SQLString.Format(p_examtype);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                v_result = dbox.getInt("d_examcount");
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
        return v_result;
    }

    /**
     * 평가 결과 개인별 문제 데이타
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<String> getUserResultData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<String> list = null;
        int v_result = 0;

        String v_userid = box.getSession("userid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<String>();
            v_result = getUserResultData(connMgr, v_subj, v_year, v_subjseq, v_userid, "M");
            list.add(String.valueOf(v_result));
            v_result = getUserResultData(connMgr, v_subj, v_year, v_subjseq, v_userid, "H");
            list.add(String.valueOf(v_result));
            v_result = getUserResultData(connMgr, v_subj, v_year, v_subjseq, v_userid, "E");
            list.add(String.valueOf(v_result));
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
     * 평가 결과 개인별 문제 데이타
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int getUserResultData(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, String p_examtype) throws Exception {

        //        ArrayList QuestionExampleDataList  = null;
        int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select count(score) examcount ";
            sql += "  from tz_examresult  ";
            sql += " where subj = " + SQLString.Format(p_subj);
            sql += " and year = " + SQLString.Format(p_year);
            sql += " and subjseq = " + SQLString.Format(p_subjseq);
            sql += " and examtype = " + SQLString.Format(p_examtype);
            sql += " and userid = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                v_result = dbox.getInt("d_examcount");
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
        return v_result;
    }

    /**
     * 평가 문제 차시 구함
     * 
     * @param box receive from the form object and session
     * @return String
     */
    public String getProgressData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        String v_research = "";

        try {
            String s_userid = box.getSession("userid");
            String v_subj = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");

            connMgr = new DBConnectionManager();
            // 수정일 : 05.11.09 수정자 : 이나연 _ rownum 수정
            //			sql = "select a.lesson  ";
            sql = "select * from ( select rownum rnum,  a.lesson  ";
            sql += "  from tz_progress a ";
            sql += "   where a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.year   = " + SQLString.Format(v_year);
            sql += "   and a.subjseq   = " + SQLString.Format(v_subjseq);
            sql += "   and a.userid   = " + SQLString.Format(s_userid);
            sql += "   and a.lessonstatus = 'complete' ";
            //          sql+= "   and rownum <= 1 ";
            sql += "   order by a.lesson desc ) where rnum < 2";

            ls = connMgr.executeQuery(sql);
            //System.out.println("ExamUserBean getProgressData 평가 문제 차시:"+sql);

            if (ls.next()) {
                dbox = ls.getDataBox();

                v_research = dbox.getString("d_lesson");
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
        return v_research;
    }

    /**
     * 운영자권한 테이블에 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int UpdateUserRetry(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        String v_papernum = box.getString("p_papernum");
        String v_userid = box.getString("p_userid");
        String v_userretrycnt = box.getStringDefault("p_userretrycnt", "0");

        int isOk = 0;
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //delete TZ_MANAGER table
            sql = "update tz_examresult set userretry = " + v_userretrycnt + " ";
            sql += "where  subj = '" + v_subj + "' and ";
            sql += "	   year = '" + v_year + "' and ";
            sql += "	   subjseq = '" + v_subjseq + "' and ";
            sql += "	   lesson = '" + v_lesson + "' and ";
            sql += "	   examtype = '" + v_examtype + "' and ";
            sql += "	   papernum = '" + v_papernum + "' and ";
            sql += "	   userid = '" + v_userid + "' ";
            Log.info.println("재응시>>>" + sql);
            isOk = connMgr.executeUpdate(sql);

            if (isOk == 1)
                connMgr.commit();
            else
                connMgr.rollback();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * 평가지 전체 보기(재채점 뷰)
     * 
     * @param
     * @return ArrayList
     */

    public ArrayList<ArrayList<DataBox>> SelectPaperResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<ArrayList<DataBox>> QuestionExampleDataList = null;
        //        ArrayList list = new ArrayList();
        ListSet ls = null;
        //        DataBox dbox = null;
        String sql = "";
        String s_exam = ""; // 문제번호

        String v_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();

            sql = " select examnum from TZ_EXAM where subj='" + v_subj + "' order by examnum asc ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                s_exam = s_exam + ls.getInt("examnum") + ",";
            }
            QuestionExampleDataList = getExampleData(connMgr, v_subj, s_exam);

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
        return QuestionExampleDataList;
    }

    /**
     * 평가 id별 재채점
     * 
     * @param box receive from the form object
     * @return int
     */
    public int InsertReRating(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //        PreparedStatement pstmt = null;
        //        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        String v_subj = box.getString("p_subj");
        //        String v_year      = box.getString("p_gyear");
        //        String v_subjseq   = box.getString("p_subjseq");
        //        String v_lesson    = box.getString("p_lesson");
        //        String v_examtype  = box.getString("p_examtype");
        //        int    v_papernum  = box.getInt("p_papernum");
        //        String v_userid    = box.getString("p_userid");
        String v_luserid = box.getSession("userid"); // 최종수정자
        String v_change1 = box.getString("p_change1"); // 재채점정보(객관식)
        String v_change2 = box.getString("p_change2"); // 재채점정보(주관식)

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 새로운객관식정답 update
            if (!v_change1.equals("")) {
                isOk = UpdateNewAnswer(connMgr, v_change1, v_subj, v_luserid);
            }

            // 새로운주관식정답 update
            if (!v_change2.equals("")) {
                isOk = UpdateNewAnswerSubjective(connMgr, v_change2, v_subj, v_luserid);
            }

            // id별 재채점 처리
            isOk = UpdateIdReRating(connMgr, box);

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.commit();
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
     * 평가 과정별 재채점
     * 
     * @param box receive from the form object
     * @return int
     */

    @SuppressWarnings("unchecked")
    public int InsertAllReRating(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        //		Vector v_result = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        //        String v_lesson    = box.getString("p_lesson");
        //        String v_examtype  = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        //        String v_userid    = box.getString("p_userid");
        String v_luserid = box.getSession("userid"); // 최종수정자
        String v_change1 = box.getString("p_change1"); // 재채점정보(객관식)
        String v_change2 = box.getString("p_change2"); // 재채점정보(주관식)

        //        String v_exam      = ""; // 문제조합
        //        int    v_examcnt   = 0;  // 문제수
        //        int    v_exampoint = 0;  // 문제당 배점
        //        String v_started   = ""; // 시작시간
        //        String v_ended     = ""; // 종료시간
        //		double v_time      = 0;  // 소요시간
        //        int    v_userretry = 0;  // 사용자재응시횟수

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 새로운객관식정답 update
            if (!v_change1.equals("")) {
                isOk = UpdateNewAnswer(connMgr, v_change1, v_subj, v_luserid);
            }

            // 새로운주관식정답 update
            if (!v_change2.equals("")) {
                isOk = UpdateNewAnswerSubjective(connMgr, v_change2, v_subj, v_luserid);
            }

            // 조회된 학습자 데이타 select
            sql = " SELECT userid, lesson, examtype FROM TZ_EXAMRESULT  " + " WHERE  subj = " + SQLString.Format(v_subj) + " AND    year = " + SQLString.Format(v_year) + " AND    subjseq =  " + SQLString.Format(v_subjseq) + " AND    papernum = "
                    + SQLString.Format(v_papernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                box.put("p_userid", ls.getString("userid"));
                box.put("p_lesson", ls.getString("lesson"));
                box.put("p_examtype", ls.getString("examtype"));

                // id별 재채점 처리
                isOk = UpdateIdReRating(connMgr, box);
                System.out.println("ls.getString(userid)>>>" + ls.getString("userid") + ">>>" + isOk);
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.commit();
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
     * 평가 id별 재채점 UPDATE
     * 
     * @param box receive from the form object
     * @return int
     */

    public int UpdateIdReRating(DBConnectionManager connMgr, RequestBox box) throws Exception {
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        Vector<String> v_result = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");
        String v_luserid = box.getSession("userid"); // 최종수정자
        // String v_change    = box.getString("p_change");  // 재채점정보

        String v_exam = ""; // 문제조합
        int v_examcnt = 0; // 문제수
        int v_exampoint = 0; // 문제당 배점
        String v_answer = ""; // 평가답안
        // String v_started = ""; // 시작시간
        // String v_ended = ""; // 종료시간
        // double v_time = 0;  // 소요시간
        // int v_userretry = 0;  // 사용자재응시횟수

        try {
            // 기존 데이타 select
            sql = " select exam, examcnt, exampoint, answer, started, ended, time, userretry " + " from TZ_EXAMRESULT " + " where subj    = " + SQLString.Format(v_subj) + "   and year    = " + SQLString.Format(v_year) + "   and subjseq = "
                    + SQLString.Format(v_subjseq) + "   and lesson  = " + SQLString.Format(v_lesson) + "   and examtype   = " + SQLString.Format(v_examtype) + "   and papernum= " + SQLString.Format(v_papernum) + "   and userid  = "
                    + SQLString.Format(v_userid);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_exam = ls.getString("exam"); // 문제조합
                v_examcnt = ls.getInt("examcnt"); // 문제수
                v_exampoint = ls.getInt("exampoint"); // 문제당 배점
                v_answer = ls.getString("answer"); // 평가답안
                //                v_started   = ls.getString("started"); // 시작시간
                //                v_ended     = ls.getString("ended");   // 종료시간
                //                v_time      = ls.getDouble("time");    // 소요시간
                //                v_userretry = ls.getInt("userretry");  // 사용자재응시횟수
            }

            // 평가점수채점
            v_result = getScore(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_exam, v_answer, v_examcnt, v_exampoint);
            int v_score = Integer.parseInt((String) v_result.get(0)); // 획득점수
            int v_answercnt = Integer.parseInt((String) v_result.get(1)); // 정답수
            String v_corrected = (String) v_result.get(2); // 정답유무

            // update
            isOk = UpdateTZ_examresult(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_score, v_answercnt, v_answer, v_corrected, v_luserid);

            // tz_student 점수계산
            isOk = CalcUtil.getInstance().calc_score(connMgr, CalcUtil.EXAM + "", v_subj, v_year, v_subjseq, v_userid, v_luserid, new StoldData());

        } catch (Exception ex) {
            isOk = 0;
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
        return isOk;
    }

    /**
     * 평가문제 새로운 객관식 정답 UPDATE
     * 
     * @param box receive from the form object
     * @return int
     */
    public int UpdateNewAnswer(DBConnectionManager connMgr, String v_change, String v_subj, String v_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {

            StringTokenizer st_exam = new StringTokenizer(v_change, ExamBean.SPLIT_COMMA);
            int s_examnum = 0;
            int s_selnum = 0;
            while (st_exam.hasMoreElements()) {
                String s = StringManager.trim((String) st_exam.nextToken());
                String[] tokens = s.split("\\^");
                s_examnum = Integer.parseInt(tokens[0]); // 문제번호
                s_selnum = Integer.parseInt(tokens[1]); // 문제보기 정답번호

                // 문제항목 정답필드 '' 셋팅
                sql = "  update TZ_EXAMSEL  set isanswer='' , luserid=? , ldate=?  WHERE subj=?  and examnum=?  ";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_luserid);
                pstmt.setString(2, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt.setString(3, v_subj);
                pstmt.setInt(4, s_examnum);
                isOk = pstmt.executeUpdate();

                if (isOk > 0) {
                    //새로운 정답 Y 셋팅
                    sql = "  update TZ_EXAMSEL set isanswer='Y' WHERE subj=? and examnum=? and selnum=?  ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_subj);
                    pstmt.setInt(2, s_examnum);
                    pstmt.setInt(3, s_selnum);
                    isOk = pstmt.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
     * 평가문제 새로운 주관식 정답 UPDATE
     * 
     * @param box receive from the form object
     * @return int
     */
    public int UpdateNewAnswerSubjective(DBConnectionManager connMgr, String v_change, String v_subj, String v_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            StringTokenizer st_exam = new StringTokenizer(v_change, ExamBean.SPLIT_COMMA); // 문제번호^정답^정답,문제번호^정답^정답,...
            int s_examnum = 0;
            String s_selnum = "";
            while (st_exam.hasMoreElements()) {
                String s = StringManager.trim((String) st_exam.nextToken());
                String[] tokens = s.split("\\^");

                s_examnum = Integer.parseInt(tokens[0]); // 문제번호

                // 문제항목 정답필드 delete
                sql = "  delete from tz_examsel where subj=? and examnum=?  ";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_subj);
                pstmt.setInt(2, s_examnum);
                isOk = pstmt.executeUpdate();

                //새로운 정답 insert
                for (int e = 1; e < tokens.length; e++) {
                    s_selnum = tokens[e]; // 문제정답

                    sql = "insert into TZ_EXAMSEL(subj, examnum, selnum, seltext, luserid, ldate) ";
                    sql += " values (?, ?, ?, ?, ?, ?)";

                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_subj);
                    pstmt.setInt(2, s_examnum);
                    pstmt.setInt(3, e); // 정답번호
                    pstmt.setString(4, s_selnum); // 주관식정답
                    pstmt.setString(5, v_luserid);
                    pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));

                    isOk = pstmt.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
     * 평가 제출시 기존 시험이 최고점수면 업데이트 안됨
     * 
     * @param box receive from the form object
     * @return int
     */
    public int IsResultScore(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        //        int isOk = 0;
        int is_score = 0;
        ResultSet rs = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");

        //        String v_lesson    = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");
        //        String v_answer    = box.getString("p_answer");
        //        String v_exam      = box.getString("p_exam");

        //        String v_started   = box.getString("p_started");
        //        String v_ended     = box.getString("p_ended");
        //		    double v_time = 0;

        //        int    v_examcnt  = box.getInt("p_examcnt");
        //        int    v_exampoint  = box.getInt("p_exampoint");
        //        int    v_userretry  = box.getInt("p_userretry");

        //        int v_score     = 0;
        //		    int v_answercnt = 0;

        //        String v_luserid   = box.getSession("userid");

        //        int    v_exist     = 0;

        //				Vector v_result = null;

        //				String v_corrected = "";

        try {

            sql = " select score from tz_examresult where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' and examtype='" + v_examtype + "' and papernum=" + v_papernum + " and userid='" + v_userid + "' ";
            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                is_score = rs.getInt("score");
            }

        } catch (Exception ex) {
            is_score = 0;
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
        }
        return is_score;
    }

    /**
     * 평가 결과 재응시횟수 update(학습창에서..)
     * 
     * @param box receive from the form object
     * @return int
     */
    public int UpdateUserUserretry(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid, int p_userretry) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql = " update TZ_EXAMRESULT ";
            sql += "    set ";
            sql += "        userretry = ?, ";
            sql += "        ldate   = ? ";
            sql += "  where subj    = ? ";
            sql += "    and year    = ? ";
            sql += "    and subjseq = ? ";
            sql += "    and lesson = ? ";
            sql += "    and examtype = ? ";
            sql += "    and papernum = ? ";
            sql += "    and userid  = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, p_userretry);
            pstmt.setString(2, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(3, p_subj);
            pstmt.setString(4, p_year);
            pstmt.setString(5, p_subjseq);
            pstmt.setString(6, p_lesson);
            pstmt.setString(7, p_examtype);
            pstmt.setInt(8, p_papernum);
            pstmt.setString(9, p_userid);

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

    // 평가 제출시 임시테이블에 결과데이타 저장해서 결과보여줌 - 실제 결과데이타와의 구분을 두기 위해 //
    //////TEMP//////////////////////////////////////////2005.10
    /**
     * 평가 제출시 TZ_EXAMRESULTTEMP 테이블에 결과 등록
     * 
     * @param box receive from the form object
     * @return int
     */

    public int InsertResultTemp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;

        //        PreparedStatement pstmt = null;
        //	    	ResultSet rs = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");

        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");
        String v_answer = box.getString("p_answer");
        String v_exam = box.getString("p_exam");

        String v_started = box.getString("p_started");
        String v_ended = box.getString("p_ended");
        double v_time = 0;

        int v_examcnt = box.getInt("p_examcnt");
        int v_exampoint = box.getInt("p_exampoint");
        int v_userretry = box.getInt("p_userretry");

        int v_score = 0;
        int v_answercnt = 0;

        String v_luserid = box.getSession("userid");

        int v_exist = 0;

        Vector<String> v_result = null;

        String v_corrected = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 평가지,평가자별 카운트
            v_exist = chkResultExistTemp(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid);

            // 평가점수채점
            v_result = getScore(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_exam, v_answer, v_examcnt, v_exampoint);

            v_score = Integer.parseInt((String) v_result.get(0)); // 점수

            v_answercnt = Integer.parseInt((String) v_result.get(1));
            v_corrected = (String) v_result.get(2);

            v_time = FormatDate.getMinDifference(v_started, v_ended);

            StringTokenizer st2 = new StringTokenizer(v_answer, ExamBean.SPLIT_COMMA);

            v_answer = "";
            while (st2.hasMoreElements()) {

                String s = StringManager.trim((String) st2.nextToken());

                if (s.length() == 0)
                    v_answer = v_answer + " " + ",";
                else
                    v_answer = v_answer + s + ",";
            }

            if (v_userretry != 0)
                v_userretry = v_userretry - 1;

            if (v_exist == 0) {
                isOk = InsertTZ_examresultTemp(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_exam, v_examcnt, v_exampoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_userretry, v_luserid);
            } else {

                isOk = UpdateTZ_examresultTemp(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid, v_exam, v_examcnt, v_exampoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_userretry, v_luserid);
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.commit();
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
     * 평가 결과 임시테이블 등록 처리
     * 
     * @param box receive from the form object
     * @return int
     */
    public int InsertTZ_examresultTemp(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid, String p_exam, int p_examcnt, int p_exampoint, int p_score,
            int p_answercnt, String p_started, String p_ended, double p_time, String p_answer, String p_corrected, int p_userretry, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql = " insert into TZ_EXAMRESULTTEMP ";
            sql += " (subj,   year,      subjseq,  lesson, ";
            sql += "  examtype,  papernum, userid, ";
            sql += "  exam, examcnt, exampoint, score, answercnt,   started, ";
            sql += "  ended,  time, answer, corrected, userretry,  luserid,  ldate) ";
            sql += " values ";
            sql += " (?,      ?,         ?,         ?, ";
            sql += "  ?,      ?,         ?,          ";
            sql += "  ?,      ?,         ?,         ?,                   ?,                    ?, ";
            sql += "  ?,      ?,         ?,         ?,        ?,        ?,      ? ) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_year);
            pstmt.setString(3, p_subjseq);
            pstmt.setString(4, p_lesson);
            pstmt.setString(5, p_examtype);
            pstmt.setInt(6, p_papernum);
            pstmt.setString(7, p_userid);
            pstmt.setString(8, p_exam);
            pstmt.setInt(9, p_examcnt);
            pstmt.setInt(10, p_exampoint);
            pstmt.setInt(11, p_score);
            pstmt.setInt(12, p_answercnt);
            pstmt.setString(13, p_started);
            pstmt.setString(14, p_ended);
            pstmt.setDouble(15, p_time);
            pstmt.setString(16, p_answer);
            pstmt.setString(17, p_corrected);
            pstmt.setInt(18, p_userretry);
            pstmt.setString(19, p_luserid);
            pstmt.setString(20, FormatDate.getDate("yyyyMMddHHmmss"));

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
     * 평가 결과 임시테이블 수정 처리
     * 
     * @param box receive from the form object
     * @return int
     */
    public int UpdateTZ_examresultTemp(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid, String p_exam, int p_examcnt, int p_exampoint, int p_score,
            int p_answercnt, String p_started, String p_ended, double p_time, String p_answer, String p_corrected, int p_userretry, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql = " update TZ_EXAMRESULTTEMP ";
            sql += "    set exam   = ?, ";
            sql += "        examcnt   = ?, ";
            sql += "        exampoint   = ?, ";
            sql += "        score   = ?, ";
            sql += "        answercnt  = ?, ";
            sql += "        started     = ?, ";
            sql += "        ended    = ?, ";
            sql += "        time    = ?, ";
            sql += "        answer = ?, ";
            sql += "        corrected = ?, ";
            sql += "        userretry = ?, ";
            sql += "        luserid = ?, ";
            sql += "        ldate   = ? ";
            sql += "  where subj    = ? ";
            sql += "    and year    = ? ";
            sql += "    and subjseq = ? ";
            sql += "    and lesson = ? ";
            sql += "    and examtype = ? ";
            sql += "    and papernum = ? ";
            sql += "    and userid  = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_exam);
            pstmt.setInt(2, p_examcnt);
            pstmt.setInt(3, p_exampoint);
            pstmt.setInt(4, p_score);
            pstmt.setInt(5, p_answercnt);
            pstmt.setString(6, p_started);
            pstmt.setString(7, p_ended);
            pstmt.setDouble(8, p_time);
            pstmt.setString(9, p_answer);
            pstmt.setString(10, p_corrected);
            pstmt.setInt(11, p_userretry);
            pstmt.setString(12, p_luserid);
            pstmt.setString(13, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(14, p_subj);
            pstmt.setString(15, p_year);
            pstmt.setString(16, p_subjseq);
            pstmt.setString(17, p_lesson);
            pstmt.setString(18, p_examtype);
            pstmt.setInt(19, p_papernum);
            pstmt.setString(20, p_userid);

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
     * 평가결과 임시테이블 평가지,평가자별 카운트
     * 
     * @param
     * @return int
     */
    public int chkResultExistTemp(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int v_exist = 0;

        try {
            sql = "select count(*)  cnt ";
            sql += "  from tz_examresulttemp  ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            sql += "   and lesson  = " + SQLString.Format(p_lesson);
            sql += "   and examtype   = " + SQLString.Format(p_examtype);
            sql += "   and papernum= " + SQLString.Format(p_papernum);
            sql += "   and userid  = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_exist = ls.getInt("cnt");
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
        return v_exist;
    }

    /**
     * 평가자 결과 보기-제출후 바로 보는 결과
     * 
     * @param
     * @return ArrayList
     */

    public ArrayList<ArrayList<DataBox>> SelectUserPaperResultTemp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<ArrayList<DataBox>> list = null;

        // String v_action = box.getStringDefault("p_action",  "change");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list = getPaperResultListTemp(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid);
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
     * 평가자 결과 보기-제출후 바로 보는 결과
     * 
     * @param
     * @return ArrayList
     */

    public ArrayList<ArrayList<DataBox>> getPaperResultListTemp(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid) throws Exception {

        ArrayList<ArrayList<DataBox>> QuestionExampleDataList = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            // 수정일 : 05.11.09 수정자 : 이나연 _ rownum 수정
            // sql = "select a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected ";
            sql = "select * from ( select rownum rnum, a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected ";
            sql += "  from tz_exampaper  a, ";
            sql += "       tz_examresulttemp b ";
            sql += " where a.subj    = b.subj ";
            sql += "   and a.year    = b.year ";
            sql += "   and a.subjseq = b.subjseq ";
            sql += "   and a.lesson = b.lesson ";
            sql += "   and a.examtype = b.examtype ";
            sql += "   and a.papernum = b.papernum ";
            sql += " and b.subj = " + SQLString.Format(p_subj);
            sql += " and b.year = " + SQLString.Format(p_year);
            sql += " and b.subjseq = " + SQLString.Format(p_subjseq);
            sql += " and b.lesson = " + SQLString.Format(p_lesson);
            sql += " and b.examtype = " + SQLString.Format(p_examtype);
            sql += " and b.papernum = " + SQLString.Format(p_papernum);
            sql += " and b.userid = " + SQLString.Format(p_userid);
            //          sql+= "   and rownum <= 1 ";
            sql += " order by a.subj, a.year, a.subjseq, b.userid) where rnum < 2 ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

            QuestionExampleDataList = getExampleData(connMgr, p_subj, dbox.getString("d_exam"));

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
        return QuestionExampleDataList;
    }

    /**
     * 평가자 결과 보기-제출후 바로 보는 결과
     * 
     * @param
     * @return ArrayList
     */

    public ArrayList<Object> SelectUserPaperResult2Temp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<Object> list = null;

        // String v_action = box.getStringDefault("p_action",  "change");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        String v_examtype = box.getString("p_examtype");
        int v_papernum = box.getInt("p_papernum");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list = getPaperResultList2Temp(connMgr, v_subj, v_year, v_subjseq, v_lesson, v_examtype, v_papernum, v_userid);
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
     * 평가자 결과 보기-제출후 바로 보는 결과
     * 
     * @param
     * @return ArrayList
     */

    public ArrayList<Object> getPaperResultList2Temp(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_examtype, int p_papernum, String p_userid) throws Exception {

        ArrayList<Object> list = new ArrayList<Object>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        StringTokenizer st = null;
        Vector<String> v_answer = null;
        StringTokenizer st2 = null;
        Vector<String> v_corrected = null;

        try {
            // 수정일 : 05.11.09 수정자 : 이나연 _ rownum 수정
            //          sql = "select a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected, ";
            sql = "select * from ( select rownum rnum, a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected, ";
            sql += " b.examcnt, b.answercnt, b.score, b.exampoint, b.started, b.ended,  ";
            sql += " a.year, a.lesson, a.subjseq, c.subjnm,  get_codenm(" + SQLString.Format(ExamBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm  ";
            sql += "  from tz_exampaper  a, ";
            sql += "       tz_examresulttemp b, ";
            sql += "       tz_subj c ";
            // 수정일 : 05.11.09 수정자 : 이나연 _ (+) 수정
            //			sql+= " where a.subj(+)        = c.subj  ";
            sql += " where a.subj  = c.subj  ";
            sql += " and a.subj    = b.subj ";
            sql += "   and a.year    = b.year ";
            sql += "   and a.subjseq = b.subjseq ";
            sql += "   and a.lesson = b.lesson ";
            sql += "   and a.examtype = b.examtype ";
            sql += "   and a.papernum = b.papernum ";
            sql += " and b.subj = " + SQLString.Format(p_subj);
            sql += " and b.year = " + SQLString.Format(p_year);
            sql += " and b.subjseq = " + SQLString.Format(p_subjseq);
            sql += " and b.lesson = " + SQLString.Format(p_lesson);
            sql += " and b.examtype = " + SQLString.Format(p_examtype);
            sql += " and b.papernum = " + SQLString.Format(p_papernum);
            sql += " and b.userid = " + SQLString.Format(p_userid);
            //          sql+= "   and rownum <= 1 ";
            sql += " order by a.subj, a.year, a.subjseq, b.userid ) where rnum < 2";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                st = new StringTokenizer(dbox.getString("d_answer"), ",");
                v_answer = new Vector<String>();
                while (st.hasMoreElements()) {
                    v_answer.add(st.nextToken());
                }
                st2 = new StringTokenizer(dbox.getString("d_corrected"), ",");
                v_corrected = new Vector<String>();
                while (st2.hasMoreElements()) {
                    v_corrected.add(st2.nextToken());
                }
            }

            /*
             * sql = "select count(b.userid) cnt "; sql+=
             * "     from  tz_examresult b "; sql+= " where b.subj = " +
             * SQLString.Format(p_subj); sql+= " and b.year = " +
             * SQLString.Format(p_year); sql+= " and b.subjseq = " +
             * SQLString.Format(p_subjseq); sql+= " and b.lesson = " +
             * SQLString.Format(p_lesson); sql+= " and b.examtype = " +
             * SQLString.Format(p_examtype); sql+= " and b.papernum = " +
             * SQLString.Format(p_papernum); sql+= " and b.score >  " +
             * SQLString.Format(dbox.getString("d_score"));
             * 
             * ls.close(); ls = connMgr.executeQuery(sql);
             * 
             * while (ls.next()) { dbox.put("d_overman", ls.getString("cnt")); }
             */

            list.add(dbox);
            list.add(v_answer);
            list.add(v_corrected);

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
    //////TEMP//////////////////////////////////////////
}