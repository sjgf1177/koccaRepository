//**********************************************************
//1. 제      목: 평가 결과조회
//2. 프로그램명: ETestResultBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 
//**********************************************************

package com.credu.etest;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.scorm.Box;
import com.credu.system.*;
import com.credu.common.*;
import java.text.*;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ETestResultBean {

    private ConfigSet conf;

    public ETestResultBean() {
        try {
            conf = new ConfigSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * E-TEST 결과분석 대상자 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 결과분석 대상
     */
    public ArrayList SelectReaultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_action = box.getStringDefault("p_action", "change");

        String v_grcode = box.getString("s_grcode");
        String v_gyear = box.getString("s_gyear");
        String v_upperclass = box.getStringDefault("s_upperclass", "ALL");

        String v_etestsubj = box.getString("s_etestsubj");
        String v_etestcode = box.getString("s_etestcode");

        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getResultList(connMgr, v_grcode, v_gyear, v_etestsubj, v_etestcode, s_userid, s_gadmin, box);

            } else {
                list = new ArrayList();
            }
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
     * E-TEST 결과분석 대상자 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 결과분석 대상
     */
    public ArrayList getResultList(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_etestsubj, String p_etestcode, String p_userid, String p_gadmin, RequestBox box) throws Exception {

        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        ETestResultData data = null;
        ManagerAdminBean bean = null;
        String v_sql_add = "";

        try {
            sql = "select d.jikwi,      get_jikwinm(d.jikwi, d.comp) jikwinm, d.userid, d.cono,   d.name, \n";
            sql += "       d.comp asgn,  get_compnm(d.comp,2,2) companynm, d.deptnam asgnnm,  \n";
            sql += "       b.etestsubj,  b.year,   b.etestcode, b.etesttext,   \n";
            sql += "       c.etest,c.etestnum, c.etestcnt, c.etestpoint, c.score, c.answercnt, c.started, c.ended,  c.time, c.answer, c.corrected   \n";
            sql += "  from  (select a.etestsubj, a.year, a.etestcode, b.etesttext, a.userid  \n";
            sql += "          from tz_etestmember    a,  \n";
            sql += "               tz_etestmaster  b   \n";
            sql += "         where a.etestsubj = b.etestsubj   \n";
            sql += "           and a.year = b.year    \n";
            sql += "           and a.etestcode = b.etestcode ) b,   \n";
            sql += "       tz_etestresult c,  \n";
            sql += "       tz_member     d,  \n";
            sql += "       tz_comp       e   \n";
            sql += "   where b.etestsubj    = c.etestsubj  \n";
            sql += "   and b.year        = c.year  \n";
            sql += "   and b.etestcode   = c.etestcode  \n";
            sql += "   and b.userid      = c.userid  \n";
            sql += "   and b.userid  = d.userid  \n";
            sql += "   and d.comp    = e.comp  \n";
            sql += "   and b.etestsubj = " + SQLString.Format(p_etestsubj);
            sql += "   and b.year      = " + SQLString.Format(p_gyear);
            sql += "   and b.etestcode = " + SQLString.Format(p_etestcode);
            //     sql+= " order by c.etestsubj, c.year, c.etestcode, b.userid ";

            if (box.getString("p_orderColumn").equals("userid")) {
                sql += " order by d.userid " + box.getString("p_flag");
            } else if (box.getString("p_orderColumn").equals("name")) {
                sql += " order by d.name " + box.getString("p_flag");
            } else if (box.getString("p_orderColumn").equals("score")) {
                sql += " order by isnull(c.score, 0) " + box.getString("p_flag");
            } else if (box.getString("p_orderColumn").equals("issubmit")) {
                sql += " order by c.answer " + box.getString("p_flag");
            } else if (box.getString("p_orderColumn").equals("")) {
                sql += " order by  companynm ";
            } else {
                sql += " order by " + box.getString("p_orderColumn") + " " + box.getString("p_flag");
            }

            //System.out.println("ETestResultBean E-Test 결과리스트:"+sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new ETestResultData();

                data.setSubjnm(ls.getString("etesttext"));
                data.setSubj(ls.getString("etestsubj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("etestcode"));
                data.setUserid(ls.getString("userid"));
                data.setETest(ls.getString("etest"));
                data.setPapernum(ls.getInt("etestnum"));
                data.setETestcnt(ls.getInt("etestcnt"));
                data.setETestpoint(ls.getInt("etestpoint"));
                data.setScore(ls.getInt("score"));
                data.setAnswercnt(ls.getInt("answercnt"));
                data.setStarted(ls.getString("started"));
                data.setEnded(ls.getString("ended"));
                data.setTime(ls.getDouble("time"));
                data.setAnswer(ls.getString("answer"));
                data.setCorrected(ls.getString("corrected"));
                data.setCompanynm(ls.getString("companynm"));
                data.setAsgnnm(ls.getString("asgnnm"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));

                if (data.getAnswer() == null || data.getAnswer().equals("")) {
                    data.setStatus("미응시");
                } else {
                    data.setStatus("완료");
                }
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

    /**
     * E-TEST 결과분석 점수별 통계
     * 
     * @param box receive from the form object and session
     * @return Vector 결과분석
     */
    public Vector<String> SelectResultAverage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Vector<String> v_average = null;

        // String v_grcode = box.getString("s_grcode");
        String v_gyear = box.getString("s_gyear");
        // String v_upperclass = box.getStringDefault("s_upperclass", "ALL");

        String v_etestsubj = box.getString("s_etestsubj");
        String v_etestcode = box.getString("s_etestcode");

        // String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            v_average = getAverage(connMgr, v_etestsubj, v_gyear, v_etestcode);

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
        return v_average;
    }

    public Vector<String> SelectResultAverage2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Vector<String> v_average = null;

        // String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        // String v_upperclass = box.getStringDefault("s_upperclass", "ALL");

        String v_etestsubj = box.getString("p_etestsubj");
        String v_etestcode = box.getString("p_etestcode");

        // String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            v_average = getAverage(connMgr, v_etestsubj, v_gyear, v_etestcode);

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
        return v_average;
    }

    /**
     * E-TEST 결과분석 점수별 통계
     * 
     * @param box receive from the form object and session
     * @return Vector 결과분석
     */
    public Vector<String> getAverage(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_etestcode) throws Exception {

        Vector<String> v_average = null;
        String sql = "";
        DataBox dbox = null;
        ListSet ls = null;

        int totalscore = 0;
        int etestcnt = 0;
        int usercnt = 0;
        double averscore = 0;
        int maxscore = 0;
        int minscore = 100;
        int usercnt1 = 0;
        int usercnt2 = 0;
        int usercnt3 = 0;
        int usercnt4 = 0;
        int usercnt5 = 0;
        int usercnt6 = 0;
        int usercnt7 = 0;
        int usercnt8 = 0;
        int usercnt9 = 0;
        int usercnt10 = 0;

        try {
            sql += "   select  k.etestcnt, k.score  \n";
            sql += "    from   tz_etestresult k  \n";
            sql += "    where k.etestsubj = " + SQLString.Format(p_etestsubj);
            sql += "    and k.year = " + SQLString.Format(p_gyear);
            sql += "    and k.etestcode = " + SQLString.Format(p_etestcode);
            sql += " order by k.etestsubj, k.year, k.etestcode, k.userid  \n";

            //System.out.println(" E-TEST 결과분석 점수별 통계:"+sql);
            ls = connMgr.executeQuery(sql);
            v_average = new Vector<String>();

            while (ls.next()) {

                dbox = ls.getDataBox();
                totalscore += dbox.getInt("d_score");
                etestcnt = dbox.getInt("d_etestcnt");
                usercnt++;
                if (dbox.getInt("d_score") > maxscore) {
                    maxscore = dbox.getInt("d_score");
                }
                if (dbox.getInt("d_score") < minscore) {
                    minscore = dbox.getInt("d_score");
                }

                if (dbox.getInt("d_score") <= 10) {
                    usercnt1++;
                } else if (dbox.getInt("d_score") > 10 && dbox.getInt("d_score") <= 20) {
                    usercnt2++;
                } else if (dbox.getInt("d_score") > 20 && dbox.getInt("d_score") <= 30) {
                    usercnt3++;
                } else if (dbox.getInt("d_score") > 30 && dbox.getInt("d_score") <= 40) {
                    usercnt4++;
                } else if (dbox.getInt("d_score") > 40 && dbox.getInt("d_score") <= 50) {
                    usercnt5++;
                } else if (dbox.getInt("d_score") > 50 && dbox.getInt("d_score") <= 60) {
                    usercnt6++;
                } else if (dbox.getInt("d_score") > 60 && dbox.getInt("d_score") <= 70) {
                    usercnt7++;
                } else if (dbox.getInt("d_score") > 70 && dbox.getInt("d_score") <= 80) {
                    usercnt8++;
                } else if (dbox.getInt("d_score") > 80 && dbox.getInt("d_score") <= 90) {
                    usercnt9++;
                } else if (dbox.getInt("d_score") > 90 && dbox.getInt("d_score") <= 100) {
                    usercnt10++;
                }
            }

            if (usercnt > 0) {
                averscore = totalscore / usercnt;

                v_average.add(String.valueOf(etestcnt));
                v_average.add(String.valueOf(usercnt));
                v_average.add(String.valueOf(averscore));
                v_average.add(String.valueOf(maxscore));
                v_average.add(String.valueOf(minscore));
                v_average.add(String.valueOf(usercnt1));
                v_average.add(String.valueOf(usercnt2));
                v_average.add(String.valueOf(usercnt3));
                v_average.add(String.valueOf(usercnt4));
                v_average.add(String.valueOf(usercnt5));
                v_average.add(String.valueOf(usercnt6));
                v_average.add(String.valueOf(usercnt7));
                v_average.add(String.valueOf(usercnt8));
                v_average.add(String.valueOf(usercnt9));
                v_average.add(String.valueOf(usercnt10));
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
        return v_average;
    }

    /**
     * e-test결과 반영
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertETestResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        int isOk = 0;
        String str = "";
        ETestMemberBean bean = new ETestMemberBean();
        ArrayList list = null;

        int cnt = 0;
        int next = 0;

        int v_etestnum = 0;
        String v_userid = "";
        String v_etest = "";
        int v_etestcnt = 0;
        int v_etestpoint = 0;
        int v_score = 0;
        int v_answercnt = 0;
        String v_started = "";
        String v_ended = "";
        double v_time = 0;
        String v_answer = "";
        String v_corrected = "";

        int tmp_etestnum = 0;

        int index = 0;

        int isExist = 0;

        String sql = "";

        String v_etestsubj = box.getString("s_etestsubj");
        String v_year = box.getString("s_gyear");
        String v_etestcode = box.getString("s_etestcode");

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            list = bean.selectETestMemberList(box);
            System.out.println("학습자 명수 >>>>>" + list.size());
            for (int i = 0; i < list.size(); i++) {

                dbox = (DataBox) list.get(i);
                String userid = dbox.getString("d_userid");

                //String userDir = conf.getProperty("dir.etest.userresult");          
                String userDir = conf.getProperty("dir.etest.resultpaper");
                File userDirectory = new java.io.File(userDir + File.separator + v_year + "_" + v_etestsubj + "_" + v_etestcode); //      '연도_과정그룹_이테스트코드' 디렉토리 생성
                String v_filename = v_etestsubj + v_year + v_etestcode + "." + userid + ".txt";

                System.out.println("학습자 v_filename>>>>" + v_filename);
                File txtFile = new File(userDirectory, v_filename);

                if (txtFile.exists()) {

                    BufferedReader in = new BufferedReader(new FileReader(txtFile));

                    v_etestnum = 0;
                    v_userid = "";
                    v_etest = "";
                    v_etestcnt = 0;
                    v_etestpoint = 0;
                    v_score = 0;
                    v_answercnt = 0;
                    v_started = "";
                    v_ended = "";
                    v_time = 0;
                    v_answer = "";
                    v_corrected = "";

                    index = 0;
                    while ((str = in.readLine()) != null) {
                        index++;
                        //System.out.println("for pre");	
                        if (index == 4) {
                            v_etestnum = Integer.parseInt(str);
                        } else if (index == 5) {
                            v_userid = str;
                        } else if (index == 6) {
                            v_etest = str;
                        } else if (index == 7) {
                            v_etestcnt = Integer.parseInt(str);
                        } else if (index == 8) {
                            v_etestpoint = Integer.parseInt(str);
                        } else if (index == 9) {
                            v_score = Integer.parseInt(str);
                        } else if (index == 10) {
                            v_answercnt = Integer.parseInt(str);
                        } else if (index == 11) {
                            v_started = str;
                        } else if (index == 12) {
                            v_ended = str;
                        } else if (index == 13) {
                            v_time = Double.parseDouble(str);
                            v_time = 10.0;
                        } else if (index == 14) {
                            v_answer = str;
                        } else if (index == 15) {
                            v_corrected = str;
                        }
                    }
                    in.close();

                    sql = "   select  k.etestnum  ";
                    sql += "    from   tz_etestresult k ";
                    sql += "    where k.etestsubj = " + SQLString.Format(v_etestsubj);
                    sql += "    and k.year = " + SQLString.Format(v_year);
                    sql += "    and k.etestcode = " + SQLString.Format(v_etestcode);
                    //sql+= "    and k.etestnum = " + SQLString.Format(v_etestnum);
                    sql += "    and k.userid = " + SQLString.Format(v_userid);
                    //System.out.println("sql ="+	sql);
                    ls = connMgr.executeQuery(sql);
                    isExist = 0;
                    tmp_etestnum = 0;
                    if (ls.next()) {
                        isExist = 1;
                        dbox = ls.getDataBox();
                        tmp_etestnum = dbox.getInt("d_etestnum");
                    }
                    //System.out.println("isExist"+isExist);
                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }
                    if (isExist < 1) {
                        /*
                         * System.out.println("InsertTZ_examresult pre");
                         * System.out.println("v_etestsubj=="+v_etestsubj);
                         * System.out.println("v_year=="+v_year);
                         * System.out.println("v_year=="+v_etestcode);
                         * System.out.println("v_year=="+v_etestnum);
                         * System.out.println("v_userid=="+v_userid);
                         * System.out.println("v_etest=="+v_etest);
                         * System.out.println("v_etestcnt=="+v_etestcnt);
                         * System.out.println("v_etestpoint=="+v_etestpoint);
                         * System.out.println("v_score=="+v_score);
                         * System.out.println("v_answercnt=="+v_answercnt);
                         * System.out.println("v_started=="+v_started);
                         * System.out.println("v_ended=="+v_ended);
                         * System.out.println("v_time=="+v_time);
                         * System.out.println("v_answer=="+v_answer);
                         * System.out.println("v_corrected=="+v_corrected);
                         * System.out.println("v_luserid=="+v_luserid);
                         */
                        isOk = InsertTZ_examresult(connMgr, v_etestsubj, v_year, v_etestcode, v_etestnum, v_userid, v_etest, v_etestcnt, v_etestpoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_luserid);
                        //System.out.println("InsertTZ_examresult isOk=="+isOk);
                    } else {
                        if (tmp_etestnum == v_etestnum) {
                            isOk = UpdateTZ_examresult(connMgr, v_etestsubj, v_year, v_etestcode, v_etestnum, v_userid, v_etest, v_etestcnt, v_etestpoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_luserid);
                        } else {
                            isOk = DeleteTZ_examresult(connMgr, v_etestsubj, v_year, v_etestcode, tmp_etestnum, v_userid, v_etest, v_etestcnt, v_etestpoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_luserid);
                            if (isOk > 0) {
                                isOk = InsertTZ_examresult(connMgr, v_etestsubj, v_year, v_etestcode, v_etestnum, v_userid, v_etest, v_etestcnt, v_etestpoint, v_score, v_answercnt, v_started, v_ended, v_time, v_answer, v_corrected, v_luserid);
                            }
                        }
                    }
                    if (isOk > 0) {
                        connMgr.commit();
                        if (txtFile.exists())
                            txtFile.delete();
                    } else {
                        connMgr.rollback();
                    }
                    cnt += isOk;
                    next++;

                }
                //if(txtFile.exists()) txtFile.delete();

            }
            if (next == cnt) {
                connMgr.commit();
                isOk = cnt;
            } else {
                connMgr.rollback();
                isOk = -1;
            }
        } catch (Exception ex) {
            isOk = 0;
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
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
     * E-TEST 결과분석 조회후 결과반영
     * 
     * @return int
     */
    public int InsertTZ_examresult(DBConnectionManager connMgr, String p_etestsubj, String p_year, String p_etestcode, int p_etestnum, String p_userid, String p_etest, int p_etestcnt, int p_etestpoint, int p_score, int p_answercnt, String p_started,
            String p_ended, double p_time, String p_answer, String p_corrected, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        //jkh 0312
        ListSet ls = null;
        DataBox dbox = null;
        int isExist = 0;

        String v_subj = "";
        String v_subjseq = "";
        double v_wftest = 0;
        double v_ftest = 0;
        double v_avftest = 0;
        PreparedStatement pstmt2 = null;

        try {

            //insert TZ_EXAMRESULT table
            sql = " insert into TZ_ETESTRESULT ";
            sql += " (etestsubj,   year,      etestcode,  etestnum, ";
            sql += "  userid,  etest, etestcnt, ";
            sql += "  etestpoint,  score, answercnt, started, ";
            sql += "  ended,  time, answer, corrected,  luserid,  ldate) ";
            sql += " values ";
            sql += " (?,      ?,         ?,         ?, ";
            sql += "  ?,      ?,         ?,          ";
            sql += "  ?,      ?,         ?,         ?,      ";
            sql += "  ?,      ?,         ?,         ?,        ?,             ? ) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_etestsubj);
            pstmt.setString(2, p_year);
            pstmt.setString(3, p_etestcode);
            pstmt.setInt(4, p_etestnum);
            pstmt.setString(5, p_userid);
            pstmt.setString(6, p_etest);
            pstmt.setInt(7, p_etestcnt);
            pstmt.setInt(8, p_etestpoint);
            pstmt.setInt(9, p_score);
            pstmt.setInt(10, p_answercnt);
            pstmt.setString(11, p_started);
            pstmt.setString(12, p_ended);
            pstmt.setDouble(13, p_time);
            pstmt.setString(14, p_answer);
            pstmt.setString(15, p_corrected);
            pstmt.setString(16, p_luserid);
            pstmt.setString(17, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();

            //jkh 0312 과정연결
            if (isOk > 0) {

                sql = "   select  a.subj,a.subjseq,b.wftest  ";
                sql += "    from   tz_etestmember a, tz_subjseq b ";
                sql += "    where a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq ";
                sql += "    and a.etestsubj = " + SQLString.Format(p_etestsubj);
                sql += "    and a.year = " + SQLString.Format(p_year);
                sql += "    and a.etestcode = " + SQLString.Format(p_etestcode);
                sql += "    and a.userid = " + SQLString.Format(p_userid);
                //System.out.println("sql ="+	sql);
                ls = connMgr.executeQuery(sql);
                isExist = 0;
                if (ls.next()) {
                    isExist = 1;
                    dbox = ls.getDataBox();
                    v_subj = dbox.getString("d_subj");
                    v_subjseq = dbox.getString("d_subjseq");
                    v_wftest = dbox.getDouble("d_wftest");
                    v_ftest = (double) (p_score * 100) / 100;
                    v_avftest = (double) Math.round(v_ftest * v_wftest) / 100;
                }
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                if (isExist > 0) {
                    sql = " update tz_student ";
                    sql += "    set ftest   = ?, ";
                    sql += "        avftest   = ? ";
                    sql += "  where subj    = ? ";
                    sql += "    and year    = ? ";
                    sql += "    and subjseq = ? ";
                    sql += "    and userid  = ? ";

                    pstmt2 = connMgr.prepareStatement(sql);

                    pstmt2.setDouble(1, v_ftest);
                    pstmt2.setDouble(2, v_avftest);
                    pstmt2.setString(3, v_subj);
                    pstmt2.setString(4, p_year);
                    pstmt2.setString(5, v_subjseq);
                    pstmt2.setString(6, p_userid);

                    isOk = pstmt2.executeUpdate();
                }

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            System.out.println("sql = " + sql + "\r\n" + ex.getMessage());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int UpdateTZ_examresult(DBConnectionManager connMgr, String p_etestsubj, String p_year, String p_etestcode, int p_etestnum, String p_userid, String p_etest, int p_etestcnt, int p_etestpoint, int p_score, int p_answercnt, String p_started,
            String p_ended, double p_time, String p_answer, String p_corrected, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        //jkh 0312
        ListSet ls = null;
        DataBox dbox = null;
        int isExist = 0;

        String v_subj = "";
        String v_subjseq = "";
        double v_wftest = 0;
        double v_ftest = 0;
        double v_avftest = 0;
        PreparedStatement pstmt2 = null;

        try {
            //insert TZ_EXAMRESULT table
            sql = " update TZ_ETESTRESULT ";
            sql += "  set  etest = ?, etestcnt = ?, ";
            sql += "  etestpoint = ?,  score = ?, answercnt = ?, started = ?, ";
            sql += "  ended = ?,  time = ?, answer = ?, corrected = ?,  luserid = ? ";
            //sql+=  "  ,  ldate = ? ";
            sql += " where  etestsubj = ? and   year = ? and      etestcode = ? and  etestnum = ? and userid = ?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_etest);
            pstmt.setInt(2, p_etestcnt);
            pstmt.setInt(3, p_etestpoint);
            pstmt.setInt(4, p_score);
            pstmt.setInt(5, p_answercnt);
            pstmt.setString(6, p_started);
            pstmt.setString(7, p_ended);
            pstmt.setDouble(8, p_time);
            pstmt.setString(9, p_answer);
            pstmt.setString(10, p_corrected);
            pstmt.setString(11, p_luserid);
            //pstmt.setString(17, FormatDate.getDate("yyyyMMddHHmmss"));

            pstmt.setString(12, p_etestsubj);
            pstmt.setString(13, p_year);
            pstmt.setString(14, p_etestcode);
            pstmt.setInt(15, p_etestnum);
            pstmt.setString(16, p_userid);

            isOk = pstmt.executeUpdate();

            //jkh 0312 과정연결
            if (isOk > 0) {

                sql = "   select  a.subj,a.subjseq,b.wftest  ";
                sql += "    from   tz_etestmember a, tz_subjseq b ";
                sql += "    where a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq ";
                sql += "    and a.etestsubj = " + SQLString.Format(p_etestsubj);
                sql += "    and a.year = " + SQLString.Format(p_year);
                sql += "    and a.etestcode = " + SQLString.Format(p_etestcode);
                sql += "    and a.userid = " + SQLString.Format(p_userid);
                //System.out.println("sql ="+	sql);
                ls = connMgr.executeQuery(sql);
                isExist = 0;
                if (ls.next()) {
                    isExist = 1;
                    dbox = ls.getDataBox();
                    v_subj = dbox.getString("d_subj");
                    v_subjseq = dbox.getString("d_subjseq");
                    v_wftest = dbox.getDouble("d_wftest");
                    v_ftest = (double) (p_score * 100) / 100;
                    v_avftest = (double) Math.round(v_ftest * v_wftest) / 100;
                }
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                if (isExist > 0) {
                    sql = " update tz_student ";
                    sql += "    set ftest   = ?, ";
                    sql += "        avftest   = ? ";
                    sql += "  where subj    = ? ";
                    sql += "    and year    = ? ";
                    sql += "    and subjseq = ? ";
                    sql += "    and userid  = ? ";

                    pstmt2 = connMgr.prepareStatement(sql);

                    pstmt2.setDouble(1, v_ftest);
                    pstmt2.setDouble(2, v_avftest);
                    pstmt2.setString(3, v_subj);
                    pstmt2.setString(4, p_year);
                    pstmt2.setString(5, v_subjseq);
                    pstmt2.setString(6, p_userid);

                    isOk = pstmt2.executeUpdate();
                }

            }
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
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int DeleteTZ_examresult(DBConnectionManager connMgr, String p_etestsubj, String p_year, String p_etestcode, int p_etestnum, String p_userid, String p_etest, int p_etestcnt, int p_etestpoint, int p_score, int p_answercnt, String p_started,
            String p_ended, double p_time, String p_answer, String p_corrected, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMRESULT table
            sql = " delete from TZ_ETESTRESULT ";
            sql += " where  etestsubj = ? and   year = ? and      etestcode = ? and  etestnum = ? and userid = ?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_etestsubj);
            pstmt.setString(2, p_year);
            pstmt.setString(3, p_etestcode);
            pstmt.setInt(4, p_etestnum);
            pstmt.setString(5, p_userid);

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
     * 개인의 분류별 결과 보기
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public ArrayList getGubunResult(DBConnectionManager connMgr, String p_etestsubj, String p_year, String p_etestcode, String p_userid) throws Exception {

        ArrayList alist = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        Vector v_etestgubun = new Vector();
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;
        String s_etestgubun = ""; // 문제분류 
        String s_etest = "";
        String s_corrected = "";
        int etestpoint = 0;
        int gubuncnt1 = 0;
        int gubuncnt2 = 0;
        int gubuncnt3 = 0;
        int gubuncnt4 = 0;
        int gubuncnt5 = 0;
        int gubuncnt6 = 0;
        int gubuncnt7 = 0;
        int gubuncnt8 = 0;
        int gubuncnt9 = 0;

        try {

            // 문제당배점, 문제조합(번호), 정답여부
            sql = "select * from ( select rownum rnum,  a.etestpoint, b.etest,  b.corrected ";
            sql += "  from tz_etestpaper  a, ";
            sql += "       tz_etestresult b ";
            sql += " where a.etestsubj    = b.etestsubj ";
            sql += "   and a.year    = b.year ";
            sql += "   and a.etestcode = b.etestcode ";
            sql += " and b.etestsubj = " + SQLString.Format(p_etestsubj);
            sql += " and b.year      = " + SQLString.Format(p_year);
            sql += " and b.etestcode = " + SQLString.Format(p_etestcode);
            sql += " and b.userid    = " + SQLString.Format(p_userid);
            //sql+= "   and rownum <= 1 ";
            sql += " order by a.etestsubj, a.year, a.etestcode, b.userid ) where rnum < 2";
            System.out.println("문제당배점>>" + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                etestpoint = ls.getInt("etestpoint");
                s_etest = ls.getString("etest");
                s_corrected = ls.getString("corrected");
            }

            // 문제번호
            ArrayList arr_etestnum = new ArrayList();
            st1 = new StringTokenizer(s_etest, ETestBean.SPLIT_COMMA);
            while (st1.hasMoreElements()) {
                arr_etestnum.add((String) st1.nextToken());
            }

            // 정답여부     
            ArrayList arr_answers = new ArrayList();
            st2 = new StringTokenizer(s_corrected, ETestBean.SPLIT_COMMA);
            while (st2.hasMoreElements()) {
                arr_answers.add((String) st2.nextToken());
            }

            for (int i = 0; i < arr_etestnum.size(); i++) {

                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }

                if (arr_answers.get(i).equals("1")) { //정답이면..

                    sql = " select etestgubun from tz_etest where etestsubj = " + SQLString.Format(p_etestsubj) + "  and etestnum = " + arr_etestnum.get(i) + " ";
                    ls = connMgr.executeQuery(sql);
                    System.out.println("sql2 = " + sql);

                    if (ls.next()) {
                        s_etestgubun = ls.getString("etestgubun");
                    }

                    if (s_etestgubun.equals("1")) {
                        gubuncnt1++;
                    } else if (s_etestgubun.equals("2")) {
                        gubuncnt2++;
                    } else if (s_etestgubun.equals("3")) {
                        gubuncnt3++;
                    } else if (s_etestgubun.equals("4")) {
                        gubuncnt4++;
                    } else if (s_etestgubun.equals("5")) {
                        gubuncnt5++;
                    } else if (s_etestgubun.equals("6")) {
                        gubuncnt6++;
                    } else if (s_etestgubun.equals("7")) {
                        gubuncnt7++;
                    } else if (s_etestgubun.equals("8")) {
                        gubuncnt8++;
                    } else if (s_etestgubun.equals("9")) {
                        gubuncnt9++;
                    }
                }
            }

            alist.add(String.valueOf(gubuncnt1 * etestpoint));
            alist.add(String.valueOf(gubuncnt2 * etestpoint));
            alist.add(String.valueOf(gubuncnt3 * etestpoint));
            alist.add(String.valueOf(gubuncnt4 * etestpoint));
            alist.add(String.valueOf(gubuncnt5 * etestpoint));
            alist.add(String.valueOf(gubuncnt6 * etestpoint));
            alist.add(String.valueOf(gubuncnt7 * etestpoint));
            alist.add(String.valueOf(gubuncnt8 * etestpoint));
            alist.add(String.valueOf(gubuncnt9 * etestpoint));

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
        return alist;
    }

    /**
     * 개인의 분류별 결과 보기
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public Vector SelectGubunResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Vector v_etestgubun = new Vector();
        ArrayList a_list = new ArrayList();
        ListSet ls = null;
        String sql = "";

        String v_etestsubj = box.getString("p_etestsubj");
        String v_year = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");
        String v_userid = box.getSession("userid");
        String p_print = box.getString("p_print"); // 개인일때..  

        try {
            connMgr = new DBConnectionManager();

            if (p_print.equals("1")) { // 개인일때..       
                v_etestgubun.add(box.getSession("name")); // 평가자 이름
                a_list = getGubunResult(connMgr, v_etestsubj, v_year, v_etestcode, box.getSession("userid"));
                v_etestgubun.add(a_list);

            } else {
                sql = " select userid,(select name from tz_member where userid=tz_etestresult.userid) name " + " from tz_etestresult where etestsubj = '" + v_etestsubj + "' and year = '" + v_year + "' and etestcode = '" + v_etestcode + "' ";

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    v_etestgubun.add(ls.getString("name")); // 평가자 이름 

                    a_list = getGubunResult(connMgr, v_etestsubj, v_year, v_etestcode, ls.getString("userid"));

                    v_etestgubun.add(a_list);
                }
            }

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

        return v_etestgubun;
    }

    /**
     * 분류별 결과 보기 - 분류코드명 가져오기
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public ArrayList SelectGubunCodenm() throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList a_list = new ArrayList();
        ListSet ls = null;
        String sql = "";

        try {
            connMgr = new DBConnectionManager();

            // 분류항목
            sql = " select codenm from tz_code where gubun=" + SQLString.Format(ETestBean.ETEST_GUBUN) + " and levels='1' order by code ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                a_list.add(ls.getString("codenm"));
            }
            System.out.println(sql);
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

        return a_list;
    }

    //////////////////////////////////////////////////// 결과분석 ///////////////////////////////////////////////////////
    /**
     * 테스트 1,2,3차테스트들 가져오기
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public String[] getEtestmasterCode(String etestsubj) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String[] etest = new String[3];
        int cnt = 0;
        try {
            connMgr = new DBConnectionManager();

            sql = " select etestcode from TZ_ETESTMASTER " + " where etestsubj in (select etestsubj from TZ_ETESTMASTER group by etestsubj having count(etestcode) < 4) and etestsubj='" + etestsubj + "' ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                etest[cnt] = ls.getString("etestcode");
                cnt++;
            }

            for (int i = cnt; i < 3; i++) {
                etest[cnt] = "";
            }

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
        return etest;
    }

    /**
     * 결과보기
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public ArrayList EtestResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList a_list = new ArrayList();
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        String v_userid = "";
        String v_gyear = box.getString("s_gyear");
        String v_etestsubj = box.getString("s_etestsubj");
        String v_action = box.getString("p_action");
        String[] etestcode = new String[3];
        // 테스트명 코드
        etestcode = getEtestmasterCode(v_etestsubj);

        try {

            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();

                sql = " select a.userid, b.name, get_compnm(b.comp,2,2) companynm, ";
                sql += "    get_deptnm(b.deptnam, b.userid) compnm, get_jikwinm(b.jikwi,b.comp) jikwinm, max(score) maxscore ";
                sql += " from tz_etestresult a, tz_member b, tz_etestsubj c  ";
                sql += " where a.userid=b.userid and a.etestsubj=c.etestsubj ";
                sql += "   and a.etestsubj in (select etestsubj from TZ_ETESTMASTER group by etestsubj having count(etestcode) < 4) ";
                sql += "   and a.etestsubj='" + v_etestsubj + "' ";
                sql += " group by c.etestsubjnm, a.etestsubj, a.userid, b.name, get_deptnm(b.deptnam, b.userid), get_jikwinm(b.jikwi,b.comp), get_compnm(b.comp,2,2) ";
                System.out.println(sql);
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();

                    v_userid = dbox.getString("d_userid");

                    //테스트별 결과 가져오기     
                    if (etestcode[0].equals("")) {
                        dbox.put("d_one", "-");
                    } else {
                        String iscore1 = getEtestCode(connMgr, v_etestsubj, etestcode[0], v_gyear, v_userid);
                        dbox.put("d_one", iscore1);
                    }

                    if (etestcode[1].equals("")) {
                        dbox.put("d_two", "-");
                    } else {
                        String iscore2 = getEtestCode(connMgr, v_etestsubj, etestcode[1], v_gyear, v_userid);
                        dbox.put("d_two", iscore2);
                    }

                    if (etestcode[2].equals("")) {
                        dbox.put("d_three", "-");
                    } else {
                        String iscore3 = getEtestCode(connMgr, v_etestsubj, etestcode[2], v_gyear, v_userid);
                        dbox.put("d_three", iscore3);
                    }

                    a_list.add(dbox);
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return a_list;
    }

    /**
     * 테스트 별 결과
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public String getEtestCode(DBConnectionManager connMgr, String p_etestsubj, String p_etestcode, String p_year, String p_userid) throws Exception {
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        int isOk = 0;
        int score = 0;
        String result = "미응시";

        try {
            sql = " select score  from tz_etestresult where etestsubj='" + p_etestsubj + "' and etestcode='" + p_etestcode + "' and year='" + p_year + "' and userid='" + p_userid + "' ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                dbox = ls.getDataBox();
                score = dbox.getInt("d_score");
                result = String.valueOf(score);
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
        return result;
    }

    ////////////////////////리포트/////////////////////////////////////////////////////////////////
    /**
     * 개인별 통계
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public Vector ReportMemberResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ResultSet rs = null;
        DataBox dbox = null;
        String sql = "";

        StringTokenizer st = null;
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;
        Vector vect_etestnum = null; // 문제번호만.. 		      
        Vector vect_etesttext = null;
        Vector vect_answer = null;
        Vector vect_corrected = null;
        ArrayList a_list = new ArrayList();
        ArrayList b_list = new ArrayList();
        ArrayList c_list = new ArrayList();
        ArrayList d_list = new ArrayList();
        ArrayList e_list = new ArrayList(); // 문제번호만.. 		      

        String v_etestsubj = box.getString("p_etestsubj");
        String v_year = box.getString("p_year");
        String v_action = box.getString("p_action");

        Vector vResult = new Vector();
        String s_etesttype = "";
        int vEtestSize = 0;

        // 테스트명 코드
        // etestcode = getEtestmasterCode(v_etestsubj);

        try {

            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();

                sql = "  select etestcode, (select etesttext from tz_etestmaster where etestsubj='" + v_etestsubj + "' and year='" + v_year + "' and etestcode=a.etestcode) etesttext, ";
                sql += "   etestnum, a.userid, b.name, get_jikwinm(b.jikwi,b.comp) jikwinm, score, etest, answer, corrected  ";
                sql += "  from tz_etestresult a, tz_member b                                                                 ";
                sql += "  where a.userid=b.userid and etestsubj='" + v_etestsubj + "' and year='" + v_year + "' order by etestcode, etestnum, userid ";

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    // 데이타                    
                    dbox = ls.getDataBox();
                    a_list.add(dbox);// 1.데이타  

                    // 문제번호
                    st = new StringTokenizer(dbox.getString("d_etest"), ",");
                    vect_etestnum = new Vector();
                    vect_etesttext = new Vector();
                    while (st.hasMoreElements()) {
                        // 수정일 : 05.11.17 수정자 : 이나연 _ || 수정
                        //                      sql = " select etestnum, '('||etestnum||')'||etesttext etext  from tz_etest where etestsubj=? and etestnum=? ";
                        sql = " select etestnum, '('||etestnum||')'||etesttext etext  from tz_etest where etestsubj=? and etestnum=? ";
                        pstmt = connMgr.prepareStatement(sql);
                        pstmt.setString(1, v_etestsubj);
                        pstmt.setInt(2, Integer.parseInt(st.nextToken()));
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            vect_etestnum.add(rs.getString("etestnum"));
                            vect_etesttext.add(rs.getString("etext"));
                        }

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
                    b_list.add(vect_etesttext); // 2.문제번호+내용

                    vEtestSize = vect_etestnum.size();

                    // 본인답
                    st1 = new StringTokenizer(dbox.getString("d_answer"), ",");
                    vect_answer = new Vector();
                    String s_seltext = "";
                    int i_st = 0;
                    while (st1.hasMoreElements()) {

                        // 객관식,주관식 타입구분//////////////////////////////                     
                        sql = " select etesttype from tz_etest where etestsubj=? and etestnum=? ";
                        pstmt = connMgr.prepareStatement(sql);
                        pstmt.setString(1, v_etestsubj);
                        pstmt.setInt(2, Integer.parseInt((String) vect_etestnum.get(i_st)));
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            s_etesttype = rs.getString("etesttype");
                        }
                        ;

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
                        // 객관식,주관식 타입구분////////////////////////////                                

                        if (s_etesttype.equals("1")) { // 객관식
                            // 수정일 : 05.11.17 수정자 : 이나연 _ || 수정
                            //                          sql = " select a.selnum||'-'||a.seltext seltext from tz_etestsel a, tz_etest b ";
                            sql = " select a.selnum||'-'||a.seltext seltext from tz_etestsel a, tz_etest b ";
                            sql += " where a.etestsubj=b.etestsubj and a.etestnum=b.etestnum   ";
                            sql += " and a.etestsubj=? and a.etestnum=? and a.selnum=? and b.etesttype='1'   "; // 문항내용 보여주기(객관식일때만 가능) 

                            pstmt = connMgr.prepareStatement(sql);
                            pstmt.setString(1, v_etestsubj);
                            pstmt.setInt(2, Integer.parseInt((String) vect_etestnum.get(i_st))); // 문제번호
                            pstmt.setInt(3, Integer.parseInt(st1.nextToken())); // 보기번호       				
                            rs = pstmt.executeQuery();
                            if (rs.next()) {
                                s_seltext = rs.getString("seltext");
                            }

                        } else {
                            s_seltext = st1.nextToken();
                        }

                        vect_answer.add(s_seltext);
                        i_st++;
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
                    c_list.add(vect_answer); // 3.본인답 

                    // 정답여부
                    st2 = new StringTokenizer(dbox.getString("d_corrected"), ",");
                    vect_corrected = new Vector();
                    String s_seltext2 = "";
                    int i_st2 = 0;
                    while (st2.hasMoreElements()) {

                        // 객관식,주관식 타입구분//////////////////////////////                    
                        sql = " select etesttype from tz_etest where etestsubj=? and etestnum=? ";
                        pstmt = connMgr.prepareStatement(sql);
                        pstmt.setString(1, v_etestsubj);
                        pstmt.setInt(2, Integer.parseInt((String) vect_etestnum.get(i_st2)));
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            s_etesttype = rs.getString("etesttype");
                        }
                        ;

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
                        // 객관식,주관식 타입구분////////////////////////////        

                        if (s_etesttype.equals("1")) { // 객관식
                            // 수정일 : 05.11.17 수정자 : 이나연 _ || 수정
                            //                          sql = " select selnum||'-'||seltext seltext from tz_etestsel where etestsubj=? and etestnum=? and isanswer='Y' ";
                            sql = " select selnum||'-'||seltext seltext from tz_etestsel where etestsubj=? and etestnum=? and isanswer='Y' ";

                            pstmt = connMgr.prepareStatement(sql);
                            pstmt.setString(1, v_etestsubj);
                            pstmt.setInt(2, Integer.parseInt((String) vect_etestnum.get(i_st2))); // 문제번호      				
                            rs = pstmt.executeQuery();
                            if (rs.next()) {
                                s_seltext2 = rs.getString("seltext");
                            }

                            if (st2.nextToken().equals("1")) {
                                s_seltext2 = s_seltext2 + "(○)";
                            } else {
                                s_seltext2 = s_seltext2 + "(X)";
                            }
                        } else {
                            sql = " select seltext seltext from tz_etestsel where etestsubj=? and etestnum=? ";

                            pstmt = connMgr.prepareStatement(sql);
                            pstmt.setString(1, v_etestsubj);
                            pstmt.setInt(2, Integer.parseInt((String) vect_etestnum.get(i_st2))); // 문제번호      				
                            rs = pstmt.executeQuery();
                            s_seltext2 = "";
                            while (rs.next()) {
                                s_seltext2 += rs.getString("seltext") + ",";
                            }

                            if (st2.nextToken().equals("1")) {
                                s_seltext2 = s_seltext2 + "(○)";
                            } else {
                                s_seltext2 = s_seltext2 + "(X)";
                            }
                        }

                        vect_corrected.add(s_seltext2);
                        i_st2++;
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
                    d_list.add(vect_corrected); // 4.정답여부     				

                } // end while 

                vResult.addElement("" + vEtestSize);
                vResult.addElement(a_list);
                vResult.addElement(b_list);
                vResult.addElement(c_list);
                vResult.addElement(d_list);

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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return vResult;
    }
}