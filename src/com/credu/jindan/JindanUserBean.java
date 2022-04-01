//**********************************************************
//1. 제      목: 진단테스트 사용자
//2. 프로그램명: JindanUserBean.java
//3. 개      요: 진단테스트 사용자화면
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh 
//**********************************************************

package com.credu.jindan;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JindanUserBean {
    private ConfigSet config;
    private int row;

    public JindanUserBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 사용자 - 진단테스트 기 응시여부(개인 전체현황)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> SelectJindanHistoryCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        String v_userid = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select  upperclass, middleclass, lowerclass, userid , count(userid) cnt \n";
            sql += " from tz_jindanresult  a \n";
            sql += " where userid =  " + SQLString.Format(v_userid);
            sql += " group by upperclass, middleclass, lowerclass, userid  \n";
            ;

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();

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
     * 사용자 - 진단테스트에서 문제가져오기 ------------------------------#
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ArrayList<DataBox>> SelectJindanQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Vector v_jindannums = null;
        ArrayList<ArrayList<DataBox>> QuestionJindanDataList = null;

        try {

            connMgr = new DBConnectionManager();

            //---------------------------------------------------
            // 진단문제 가져오기 
            QuestionJindanDataList = getJindanData(connMgr, box);

            if (QuestionJindanDataList == null) {
                QuestionJindanDataList = new ArrayList<ArrayList<DataBox>>();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        return QuestionJindanDataList;
    }

    /**
     * 진단번호에 해당하는 문제리스트 가져오기 ------------------#2
     * 
     * @param box receive from the form object and session
     * @return QuestionExampleData
     */
    public ArrayList<ArrayList<DataBox>> getJindanData(DBConnectionManager connMgr, RequestBox box) throws Exception {

        ArrayList<DataBox> list = null; //하나의 문제,보기
        ArrayList<ArrayList<DataBox>> bList = new ArrayList<ArrayList<DataBox>>(); //하나의 문제,보기
        ArrayList<String> subjList = new ArrayList<String>();
        // Vector v_realnums = new Vector();
        Vector<String> v_jindannums = new Vector<String>();
        // Vector v_realrkeys = new Vector();
        // Vector v = null; 

        int jindannumCnt = box.getInt("p_jindannumCnt");
        String sql = "";
        String sql2 = "";
        String sql3 = ""; //해당과정의 진단문제 쿼리
        // String[] jindannums = null;
        String v_class1 = box.getStringDefault("class1", "");
        String v_class2 = box.getStringDefault("class2", "");
        String v_class3 = box.getStringDefault("class3", "");
        ListSet ls1 = null;
        ListSet ls2 = null;
        DataBox dbox = null;

        try {

            //------------------------------------------------------------------------
            //진단문제테이블서 5문제이상인 해당과목class 해당과정.
            sql2 += " select  a.subj, count(a.subj)  \n";
            sql2 += " from tz_jindan a, tz_subjatt b, tz_subj  c       \n";
            sql2 += " where 1=1  \n";
            sql2 += " and b.subjclass = c.subjclass \n";
            sql2 += " and a.subj = c.subj \n";
            sql2 += " and c.isonoff = 'ON' \n";

            if (!v_class1.equals("")) {
                sql2 += " and b.upperclass = " + SQLString.Format(v_class1);
            }
            if (!v_class2.equals("")) {
                sql2 += " and b.middleclass = " + SQLString.Format(v_class2);
            }
            if (!v_class3.equals("")) {
                sql2 += " and b.lowerclass =  " + SQLString.Format(v_class3);
            }
            sql2 += "group by  a.subj \n";
            sql2 += "having  count(a.subj) >= " + jindannumCnt + " \n";

            //System.out.print(" 5문제이상인 해당과목:"+sql2);

            ls1 = connMgr.executeQuery(sql2);

            while (ls1.next()) {
                subjList.add(ls1.getString(1));
            }
            ls1.close();

            //------------------------------------------------------------------------
            //  속한 과정마다 돌면서 5문제씩 랜덤으로 문제 가져오기
            String subjNo = "";
            // v_realnums = new Vector();
            // v_realrkeys = new Vector();
            String numandrkey = "";
            String getJindannum = "";
            String getJindantype = "";
            StringTokenizer st1 = null;

            for (int i = 0; i < subjList.size(); i++) {

                subjNo = (String) subjList.get(i);

                //해당과정에 5문제				
                v_jindannums = getQuestionList(connMgr, subjNo, jindannumCnt);

                for (int p = 0; p < v_jindannums.size(); p++) {

                    numandrkey = (String) v_jindannums.get(p);
                    st1 = new StringTokenizer(numandrkey, ",");

                    getJindannum = st1.nextToken();
                    getJindantype = st1.nextToken();

                    sql3 = " select a.subj,     a.jindannum,   a.jindantype,   \n";
                    sql3 += "       a.jindantext,      a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio, \n";
                    sql3 += "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, \n";
                    sql3 += "       b.selnum,   b.seltext,  b.isanswer,  \n";
                    sql3 += "       c.codenm    jindantypenm, \n";
                    sql3 += "       d.codenm    levelsnm    \n";
                    sql3 += "  from tz_jindan      a, \n";
                    sql3 += "       tz_jindansel   b, \n";
                    sql3 += "       tz_code      c, \n";
                    sql3 += "       tz_code      d  \n";
                    sql3 += " where a.subj      =  b.subj(+) \n";
                    sql3 += "	and a.jindannum   =  b.jindannum(+) \n";
                    sql3 += "	and a.jindantype  = c.code \n";
                    sql3 += "	and a.levels    = d.code \n";
                    sql3 += "	and a.subj      = " + SQLString.Format(subjNo);
                    sql3 += "	and a.jindannum  = " + getJindannum + " \n";
                    sql3 += "	and a.levels = " + SQLString.Format(getJindantype) + " \n";
                    sql3 += "	and c.gubun     = " + SQLString.Format(JindanBean.JINDAN_TYPE);
                    sql3 += "	and d.gubun     = " + SQLString.Format(JindanBean.JINDAN_TYPE);
                    sql3 += " order by a.jindannum, b.selnum \n";

                    //System.out.println("진단문제 가죠오기:"+sql3);

                    ls2 = connMgr.executeQuery(sql3);
                    list = new ArrayList<DataBox>();

                    while (ls2.next()) {
                        dbox = ls2.getDataBox();
                        list.add(dbox);
                    }
                    ls2.close();

                    bList.add(list);
                }

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
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
        }

        return bList;
    }

    /**
     * 진단테스트 문제리스트
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public Vector<String> getQuestionList(DBConnectionManager connMgr, String p_subj, int getCnt) throws Exception {
        ListSet ls = null;
        String sql = "";
        //String[] jindannums = null;
        DataBox dbox = null;
        Vector<String> v_jindannums = new Vector<String>();

        try {

            sql = " select * from (select rownum rnum,   a.subj,   a.jindannum, a.jindantype, \n";
            sql += "     a.jindantext,    a.exptext,      \n";
            sql += "     a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,  \n";
            sql += " 	a.realaudio, a.realmovie, a.realflash, DBMS_RANDOM.RANDOM rkey  \n";
            sql += " from tz_jindan   a  \n";
            sql += " where a.subj     = " + SQLString.Format(p_subj);
            sql += " order by rkey  \n ) where rnum < " + getCnt;

            //System.out.println("진단테스트 문제리스트:"+sql);
            ls = connMgr.executeQuery(sql);
            //int i = 1;	
            while (ls.next()) {
                dbox = ls.getDataBox();
                v_jindannums.add(dbox.getString("d_jindannum") + "," + dbox.getString("d_levels"));

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
        return v_jindannums;
    }

    /**
     * 진단테스트 제출 -------------------------------##
     * 
     * @param box receive from the form object
     * @return int
     */
    public int InsertResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;
        String v_upperclass = box.getString("p_upperclass");
        String v_middleclass = box.getString("p_middleclass");
        String v_lowerclass = box.getString("p_lowerclass");

        String v_userid = box.getString("p_userid");
        String v_subj = box.getString("p_subj");
        String v_answer = box.getString("p_answer"); //진단응답보기번호
        String v_jindan = box.getString("p_jindan"); //진단문제번호

        String v_started = box.getString("p_started");
        String v_ended = box.getString("p_ended");
        // double v_time = 0;

        int v_jindancnt = box.getInt("p_jindancnt"); //
        int v_jindanpoint = box.getInt("p_jindanpoint"); //배점

        //System.out.println("v_answer:"+v_answer);
        //System.out.println("v_jindan:"+v_jindan);
        //System.out.println("v_jindanpoint:"+v_jindanpoint);
        //System.out.println("v_jindancnt:"+v_jindancnt);
        //System.out.println("v_subj:"+v_subj);

        int v_score = 0;
        int v_answercnt = 0;
        String v_luserid = box.getSession("userid");
        // int v_exist = 0;
        Vector<String> v_result = null;
        String v_corrected = "";

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            StringTokenizer st1 = new StringTokenizer(v_subj, JindanBean.SPLIT_COMMA);
            StringTokenizer st2 = new StringTokenizer(v_jindan, JindanBean.SPLIT_COMMA);
            StringTokenizer st3 = new StringTokenizer(v_answer, JindanBean.SPLIT_COMMA);

            String s = "";
            String s2 = "";
            String s3 = "";
            String s_subj = "";
            String s_jindan = "";
            String s_answer = "";
            int st1_count = st1.countTokens();

            //기존의 내역을 지운다.
            SelectJindanHistoryEachCheck(connMgr, v_luserid, v_upperclass, v_middleclass, v_lowerclass);

            for (int i = 0; i < st1_count; i = i + v_jindancnt) {

                for (int j = 0; j < v_jindancnt; j++) { //과정당 과목수만큼 돌아서 result에과정별로 insert 
                    s = StringManager.trim((String) st1.nextToken());
                    s_subj = s;
                    s2 = StringManager.trim((String) st2.nextToken());
                    s_jindan += s2 + ",";
                    // 문제번호 앞의 과목코드_ 로 넘어온부분을 없앰
                    s_jindan = s_jindan.replaceAll((s_subj + "_"), "");
                    s3 = StringManager.trim((String) st3.nextToken());
                    s_answer += s3 + ",";

                }

                // 진단점수채점
                v_result = getScore(connMgr, s_subj, v_userid, s_jindan, v_answer, v_jindancnt, v_jindanpoint);

                v_score = Integer.parseInt((String) v_result.get(0)); // 점수
                v_answercnt = Integer.parseInt((String) v_result.get(1));
                v_corrected = (String) v_result.get(2);

                isOk = Inserttz_jindanresult(connMgr, s_subj, v_upperclass, v_middleclass, v_lowerclass, v_userid, s_jindan, v_jindancnt, v_jindanpoint, v_score, v_answercnt, v_started, v_ended, s_answer, v_corrected, v_userid);
            }
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * 사용자 - 진단테스트 기존내역삭제
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public void SelectJindanHistoryEachCheck(DBConnectionManager connMgr, String v_userid, String v_upperclass, String v_middleclass, String v_lowerclass) throws Exception {
        String sql = "";

        try {

            sql = " delete from tz_jindanresult  \n";
            sql += " where userid =  " + SQLString.Format(v_userid);
            sql += " and upperclass =  " + SQLString.Format(v_upperclass);
            sql += " and middleclass =  " + SQLString.Format(v_middleclass);
            sql += " and lowerclass =  " + SQLString.Format(v_lowerclass);

            connMgr.executeUpdate(sql);

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {

        }
        return;
    }

    /**
     * 진단평가 결과 등록 처리
     * 
     * @param box receive from the form object
     * @return int
     */
    public int Inserttz_jindanresult(DBConnectionManager connMgr, String p_subj, String p_upperclass, String p_middleclass, String p_lowerclass, String p_userid, String p_jindan, int p_jindancnt, int p_jindanpoint, int p_score, int p_answercnt,
            String p_started, String p_ended, String p_answer, String p_corrected, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {

            sql = " insert into tz_jindanresult ";
            sql += " ( subj, upperclass, middleclass,  lowerclass,   userid,  jindan, ";
            sql += "  jindancnt, jindanpoint, score, answercnt,   started, ";
            sql += "  ended,  answer, corrected,  luserid,  ldate) ";
            sql += " values ";
            sql += " (?,      ?,         ?,         ?,		?, ";
            sql += "  ?,      ?,         ?,         ?,		?, ";
            sql += "  ?,      ?,         ?,         ?,		?,		?)";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_upperclass);
            pstmt.setString(3, p_middleclass);
            pstmt.setString(4, p_lowerclass);
            pstmt.setString(5, p_userid);
            pstmt.setString(6, p_jindan);
            pstmt.setInt(7, p_jindancnt);
            pstmt.setInt(8, p_jindanpoint);
            pstmt.setInt(9, p_score);
            pstmt.setInt(10, p_answercnt);
            pstmt.setString(11, p_started);
            pstmt.setString(12, p_ended);
            pstmt.setString(13, p_answer);
            pstmt.setString(14, p_corrected);
            pstmt.setString(15, p_luserid);
            pstmt.setString(16, FormatDate.getDate("yyyyMMdd"));

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
     * 진단테스트 점수 채점
     * ---------------------------------------------------------------#3
     * 
     * @param
     * @return Vector
     */
    public Vector<String> getScore(DBConnectionManager connMgr, String v_subj, String p_userid, String p_jindan, String p_answer, int p_jindancnt, int p_jindanpoint) throws Exception {

        Vector<String> v_jindannums = new Vector<String>();
        int v_jindannum = 0;
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

            st1 = new StringTokenizer(p_jindan, JindanBean.SPLIT_COMMA);
            while (st1.hasMoreElements()) {
                v_jindannums.add(StringManager.trim((String) st1.nextToken()));
            }

            st2 = new StringTokenizer(p_answer, JindanBean.SPLIT_COMMA);

            while (st2.hasMoreElements()) {
                String s = StringManager.trim((String) st2.nextToken());//System.out.println("s :" + s + "a");
                v_answers.add(s);
            }

            for (int i = 0; i < v_jindannums.size(); i++) {

                v_jindannum = Integer.parseInt((String) v_jindannums.get(i));
                v_answer = (String) v_answers.get(i);

                //정답여부 정답이면 1리턴 
                v_temp = MakeExamResult(connMgr, v_subj, v_jindannum, v_answer);
                //System.out.println("정답여부 정답이면 1리턴 v_temp:"+v_temp);

                v_score += (v_temp * p_jindanpoint);
                v_answercnt += v_temp;
                if (i == v_jindannums.size() - 1) {
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
     * 평가결과 평가지,평가자별 카운트 ------------------------------------------------- #2
     * 
     * @param
     * @return int
     */
    public int chkResultExist(DBConnectionManager connMgr, String v_upperclass, String v_middleclass, String v_lowerclass, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int v_exist = 0;

        try {
            sql = "select count(*)  cnt ";
            sql += "  from tz_jindanresult  ";
            sql += " where 1=1 ";
            sql += " and upperclass  = " + SQLString.Format(v_upperclass);
            sql += " and middleclass  = " + SQLString.Format(v_middleclass);
            sql += " and lowerclass  = " + SQLString.Format(v_lowerclass);
            sql += " and userid  = " + SQLString.Format(p_userid);
            //System.out.println("진단테스트 기 응시체크:"+sql);

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
     * 진단평가 점수계산
     * 
     * @param box receive from the form object
     * @return int
     */
    public int MakeExamResult(DBConnectionManager connMgr, String p_subj, int p_jindannum, String p_answer) throws Exception {

        int isOk = 0;

        ListSet ls = null;
        String sql = "";
        // DataBox dbox = null;

        String v_jindantype = "";

        try {
            sql = "select jindantype ";
            sql += "  from tz_jindan ";
            sql += " where subj      = " + SQLString.Format(p_subj);
            sql += " and jindannum        = " + SQLString.Format(p_jindannum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_jindantype = ls.getString("jindantype");
            }
            ls.close();

            if (v_jindantype.length() > 0) {

                if (v_jindantype.equals("2")) { // 주관식

                    sql = "select count(b.selnum) cnt  \n";
                    sql += "   from tz_jindan a,   \n";
                    sql += "   (select subj, examnum, selnum, seltext, isanswer   \n";
                    sql += "	from tz_jindanexamsel  \n";
                    sql += "	where subj    = " + SQLString.Format(p_subj);
                    sql += "	and jindannum    = " + SQLString.Format(p_jindannum) + "	)  b  \n";
                    sql += "where a.subj = b.subj   \n";
                    sql += "and a.jindannum = b.jindannum   \n";
                    sql += "and b.seltext   like  " + SQLString.Format(p_answer.trim());
                    //System.out.println("주관식:"+sql);

                } else { // 객관식, ox식

                    int p_selnum = 0;
                    if (!p_answer.trim().equals("")) {
                        p_selnum = Integer.parseInt(p_answer.trim());
                    }

                    sql = "select count(b.selnum) cnt   \n";
                    sql += "   from tz_jindan a,   \n";
                    sql += "   (select subj, jindannum, selnum, isanswer   \n";
                    sql += "		from tz_jindansel  \n";
                    sql += "		where subj    = " + SQLString.Format(p_subj);
                    sql += "   and jindannum    = " + SQLString.Format(p_jindannum) + "	)  b  \n";
                    sql += "   where b.isanswer    =   'Y'    \n";
                    sql += "   and a.subj = b.subj   \n";
                    sql += "   and a.jindannum = b.jindannum   \n";
                    sql += "   and b.selnum    =    " + p_selnum;
                    //System.out.println("객관식, ox식:"+sql);					
                }

                ls.close();
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    // dbox = ls.getDataBox();

                    if (ls.getInt("cnt") > 0) {
                        isOk = 1;
                    }

                }
                ls.close();

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
     * 진단평가 후 결과 바로보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    public ArrayList<DataBox> SelectJindanResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        // int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");
        String v_upperclass = box.getString("p_upperclass");
        String v_middleclass = box.getString("p_middleclass");
        String v_lowerclass = box.getString("p_lowerclass");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            sql = " select a.subj , b.subjnm, a.middleclass, a.lowerclass, a.userid, a.score, a.ldate \n";
            sql += " from tz_jindanresult  a , tz_subj b   \n";
            sql += " where 1=1 \n";
            sql += " and a.subj = b.subj \n";
            sql += " and a.upperclass = " + SQLString.Format(v_upperclass);
            sql += " and a.middleclass = " + SQLString.Format(v_middleclass);
            sql += " and a.lowerclass = " + SQLString.Format(v_lowerclass);
            sql += " and a.userid= " + SQLString.Format(v_userid);
            sql += " order by a.score \n";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();

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
        return list;
    }

    /**
     * 진단평가이력 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 해당과정리스트
     */
    public ArrayList<DataBox> SelectJindanHistoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        ListSet ls = null;
        DataBox dbox = null;
        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select  a.upperclass, a.middleclass, a.lowerclass,  \n";
            sql += " (select distinct classname  from  tz_subjatt where upperclass = a.upperclass and middleclass='000' and lowerclass='000'  ) upperclassnm, \n";
            sql += " (select distinct classname  from  tz_subjatt where upperclass = a.upperclass and middleclass= a.middleclass and lowerclass='000'  ) middleclassnm, \n";
            sql += " (select distinct classname  from  tz_subjatt where upperclass = a.upperclass and middleclass=a.middleclass and  lowerclass= a.lowerclass  ) lowerclassnm, \n";
            sql += " to_char(sysdate, 'YYYYMMDD')  ldate ,  sum(a.score) score , count(a.userid) cnt   \n";
            sql += " from tz_jindanresult a  \n";
            sql += " where 1=1  \n";
            sql += " and  a.userid = " + SQLString.Format(v_userid);
            sql += " group by  upperclass, middleclass,  lowerclass, ldate  \n";
            sql += " order by a.upperclass,  a.middleclass, lowerclass  \n";
            //System.out.println("진단 히스토리 :"+sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();

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
        return list;
    }

    /**
     * 평가 결과 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<Object> getPaperResultList2(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_jindantype, int p_papernum, String p_userid) throws Exception {

        ArrayList<Object> list = new ArrayList<Object>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        StringTokenizer st = null;
        Vector<String> v_answer = null;
        StringTokenizer st2 = null;
        Vector<String> v_corrected = null;

        try {

            sql = "select * from (select rownum rnum,  a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected, ";
            sql += " b.jindancnt, b.answercnt, b.score, b.jindanpoint, b.started, b.ended,  ";
            sql += " a.year, a.lesson, a.subjseq, c.subjnm,  get_codenm(" + SQLString.Format(JindanBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm  ";
            sql += "  from tz_exampaper  a, ";
            sql += "       tz_jindanresult b, ";
            sql += "       tz_subj c ";
            sql += " where a.subj  =* c.subj  ";
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
            sql += " and b.examtype = " + SQLString.Format(p_jindantype);
            sql += " and b.papernum = " + SQLString.Format(p_papernum);
            sql += " and b.userid = " + SQLString.Format(p_userid);
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
            ls.close();

            sql = "select count(b.userid) cnt ";
            sql += "     from  tz_jindanresult b ";
            sql += " where b.subj = " + SQLString.Format(p_subj);
            sql += " and b.year = " + SQLString.Format(p_year);
            sql += " and b.subjseq = " + SQLString.Format(p_subjseq);
            sql += " and b.lesson = " + SQLString.Format(p_lesson);
            sql += " and b.examtype = " + SQLString.Format(p_jindantype);
            sql += " and b.papernum = " + SQLString.Format(p_papernum);
            sql += " and b.score >  " + SQLString.Format(dbox.getString("d_score"));

            ls.close();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox.put("d_overman", ls.getString("cnt"));
            }
            ls.close();

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
     * 평가자 결과 보기-제출후 바로 보는 결과
     * 
     * @param
     * @return ArrayList
     */
    public ArrayList<Object> getPaperResultList2Temp(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_lesson, String p_jindantype, int p_papernum, String p_userid) throws Exception {

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
            sql = "select * from (select rownum rnum,  a.isopenanswer, a.isopenexp, b.exam, b.answer, b.corrected, ";
            sql += " b.jindancnt, b.answercnt, b.score, b.jindanpoint, b.started, b.ended,  ";
            sql += " a.year, a.lesson, a.subjseq, c.subjnm,  get_codenm(" + SQLString.Format(JindanBean.PTYPE) + ", isnull(a.examtype, '')) examtypenm  ";
            sql += "  from tz_exampaper  a, ";
            sql += "       tz_jindanresulttemp b, ";
            sql += "       tz_subj c ";
            // 수정일 : 05.11.09 수정자 : 이나연 _ (+) 수정
            //			sql+= " where a.subj(+)        = c.subj  ";
            sql += " where a.subj  =* c.subj  ";
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
            sql += " and b.examtype = " + SQLString.Format(p_jindantype);
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
            ls.close();

            /*
             * sql = "select count(b.userid) cnt "; sql+=
             * "     from  tz_jindanresult b "; sql+= " where b.subj = " +
             * SQLString.Format(p_subj); sql+= " and b.year = " +
             * SQLString.Format(p_year); sql+= " and b.subjseq = " +
             * SQLString.Format(p_subjseq); sql+= " and b.lesson = " +
             * SQLString.Format(p_lesson); sql+= " and b.examtype = " +
             * SQLString.Format(p_jindantype); sql+= " and b.papernum = " +
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

    /**
     * 진단과정 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public ArrayList<DataBox> SelectJindanList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        // String group_sql1 = "";
        String count_sql1 = "";
        // String wsql = "";
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");
        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            head_sql1 = " SELECT   cls.upperclass, cls.middleclass, ";
            head_sql1 += "         (SELECT classname ";
            head_sql1 += "            FROM tz_subjatt ";
            head_sql1 += "           WHERE upperclass = cls.upperclass ";
            head_sql1 += "             AND middleclass = cls.middleclass ";
            head_sql1 += "             AND lowerclass = '000') middleclassnm, ";
            head_sql1 += "         cls.lowerclass, ";
            head_sql1 += "         (SELECT classname ";
            head_sql1 += "            FROM tz_subjatt ";
            head_sql1 += "           WHERE upperclass = cls.upperclass ";
            head_sql1 += "             AND middleclass = cls.middleclass ";
            head_sql1 += "             AND lowerclass = cls.lowerclass) lowerclassnm, ";
            head_sql1 += "         CASE ";
            head_sql1 += "            WHEN usr.userid IS NULL ";
            head_sql1 += "               THEN 'N' ";
            head_sql1 += "            ELSE 'Y' ";
            head_sql1 += "         END isjindan ";
            body_sql1 += "    FROM (SELECT   a.upperclass, a.middleclass, a.lowerclass ";
            body_sql1 += "              FROM tz_subj a, tz_jindan b ";
            body_sql1 += "             WHERE a.subj = b.subj AND a.isuse = 'Y' ";
            body_sql1 += "          GROUP BY a.upperclass, a.middleclass, a.lowerclass) cls ";
            body_sql1 += "         LEFT OUTER JOIN ";
            body_sql1 += "         (SELECT   upperclass, middleclass, lowerclass, userid ";
            body_sql1 += "              FROM tz_jindanresult a ";
            body_sql1 += "             WHERE userid = " + SQLString.Format(v_userid);
            body_sql1 += "          GROUP BY upperclass, middleclass, lowerclass, userid) usr ";
            body_sql1 += "         ON cls.upperclass = usr.upperclass ";
            body_sql1 += "        AND cls.middleclass = usr.middleclass ";
            body_sql1 += "        AND cls.lowerclass = usr.lowerclass ";
            order_sql1 += "  ORDER BY upperclass, middleclass, lowerclass ";

            sql1 = head_sql1 + body_sql1 + order_sql1;
            //System.out.println("SelectMyQnaCounselList" + sql1);
            ls = connMgr.executeQuery(sql1);

            count_sql1 = "select count(*) " + body_sql1;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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

}