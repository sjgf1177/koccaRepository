//**********************************************************
//  1. 제	  목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개	  요:  관리
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: __누구__ 2009. 10. 19
//  7. 수	  정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.namo.SmeNamoMime;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class OffGrseqBean {

    public OffGrseqBean() {
    }

    /**
     * 과정코드 체크
     * 
     * @param box receive from the form object and session
     * @return String 1:insert success,0:insert fail
     */
    private boolean checkSubjcode(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_seq) throws Exception {
        boolean result = true;
        int cnt = 0;
        ListSet ls = null;
        String sql = "";
        try {
            sql = " select count(*) cnt ";
            sql += "   from TZ_OFFSUBJSEQ	 \n";
            sql += " where subj = " + StringManager.makeSQL(v_subj);
            sql += " and year = " + StringManager.makeSQL(v_year);
            sql += " and subjseq = " + StringManager.makeSQL(v_subjseq);
            sql += " and seq = " + StringManager.makeSQL(v_seq);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            if (cnt == 0) {
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
        }
        return result;
    }

    /**
     * 기존 학기 정보가 있으면 true, 없으면 false
     * 
     * @param connMgr
     * @param box
     * @return
     * @throws Exception
     */
    private boolean checkSubjTerm(DBConnectionManager connMgr, RequestBox box) throws Exception {
        boolean result = true;
        int cnt = 0;
        ListSet ls = null;
        String sql = "";
        try {
            sql = " select count(*) cnt ";
            sql += "   from TZ_OFFTERM	 \n";
            sql += " where SUBJ = ':p_subj' AND SUBJSEQ = ':p_subjseq' AND YEAR = ':p_year'";
            ls = connMgr.executeQuery(sql, box);
            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            if (cnt == 0) {
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
        }
        return result;
    }

    /**
     * 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        ;
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();

            sql.append(" delete from TZ_OFFSUBJseq A	\n");
            sql.append("WHERE\n");
            sql.append("	SUBJ				=':p_subj'\n");
            sql.append("AND	SUBJSEQ	=':u_subjseq'\n");
            sql.append("AND	seq		=':u_seq'\n");
            sql.append("AND	year		=':u_year'\n");
            sql.append("AND	0		=(SELECT COUNT(USERID) FROM TZ_OFFPROPOSE WHERE SUBJ=A.SUBJ AND YEAR=A.YEAR AND SUBJSEQ=A.SUBJSEQ AND SEQ=A.SEQ)\n");

            pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql + "\r\n");
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

    public int teachDetailDelete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_userid = box.getString("p_userid");
        String v_seq = box.getString("p_seq1");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" delete from tz_subjseq_detail A	\n");
            sql.append("WHERE\n");
            sql.append("	 SUBJ				='" + v_subj + "'\n");
            sql.append("AND	 year		='" + v_year + "'\n");
            sql.append("AND	 SUBJSEQ	='" + v_subjseq + "'\n");
            sql.append("AND	 seq		='" + v_seq + "'\n");

            pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql + "\r\n");
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
     * 학기 등록 - 오프라인
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int detailInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            int term = box.getInt("p_termtotal");
            box.put("p_userid", box.getSession("userid"));

            if (!checkSubjTerm(connMgr, box)) {
                sql.append("INSERT INTO TZ_OFFTERM (\n");
                sql.append("\tSUBJ,YEAR,SUBJSEQ,TERM,TERMSTART,TERMEND,LUSERID,LDATE\n");
                sql.append(") VALUES (\n");
                sql.append("\t':p_subj',':p_year',':p_subjseq',?,?,?,':p_userid',TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS')\n");
                sql.append(")\n");

                pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
                int i = 1;
                for (int h = 0; h < term; h++) {
                    pstmt.setString(i++, box.get("p_term" + h));
                    pstmt.setString(i++, box.get("p_termstart" + h));
                    pstmt.setString(i++, box.get("p_termend" + h));
                    isOk = pstmt.executeUpdate();
                    i = 1;
                }
            } else {
                sql.append("UPDATE TZ_OFFTERM SET\n");
                sql.append("\tTERMSTART = ?, TERMEND = ?\n");
                sql.append("\tWHERE SUBJ = ':p_subj' AND SUBJSEQ = ':p_subjseq' AND YEAR = ':p_year' AND TERM = ?\n");

                pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
                int i = 1;
                for (int h = 0; h < term; h++) {
                    pstmt.setString(i++, box.get("p_termstart" + h));
                    pstmt.setString(i++, box.get("p_termend" + h));
                    pstmt.setString(i++, box.get("p_term" + h));
                    isOk = pstmt.executeUpdate();
                    i = 1;
                }
            }

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
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
     * 설명 :
     * 
     * @param box
     * @return
     * @throws Exception
     * @author swchoi
     */
    public DataBox detailInsertPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox result = null;
        String t = null;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT SUBJNM, A.SUBJ,A.YEAR,A.SUBJSEQ,A.TERMTOTAL\n");
            sql.append("FROM TZ_OFFSUBJSEQ A\n");
            sql.append("WHERE A.SEQ = 1\n");
            sql.append("AND :p_subj = A.SUBJ	\n");
            sql.append("AND :u_subjseq = A.SUBJSEQ	\n");
            sql.append("AND :u_year = A.YEAR	\n");
            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);
            if (ls.next()) {
                result = ls.getDataBox();
            }

            sql.delete(0, sql.length());
            sql.append("SELECT A.TERM,A.TERMSTART,A.TERMEND\n");
            sql.append("FROM TZ_OFFTERM A\n");
            sql.append("WHERE :p_subj = A.SUBJ	\n");
            sql.append("AND :u_subjseq = A.SUBJSEQ	\n");
            sql.append("AND :u_year = A.YEAR	\n");
            sql.append("ORDER BY TERM	\n");
            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);

            result.put("resultList", ls.getAllDataList());
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, t);
            throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
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
     * 새로운 차수 추가모집 등록 - 오프라인
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            String v_subj = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");
            String v_seq = box.getString("p_seq");

            // 중복 체크
            if (!checkSubjcode(connMgr, v_subj, v_year, v_subjseq, v_seq)) {
                sql.append("INSERT INTO TZ_OFFSUBJSEQ (\n");
                sql.append(" SUBJ, YEAR, SUBJSEQ, SEQ, PROPSTART, PROPEND\n");
                sql.append(",EDUSTART, EDUEND, ISCLOSED, SUBJNM, STUDENTLIMIT\n");
                sql.append(",BIYONG, ISGOYONG, GRADSCORE, GRADSTEP, GRADREPORT\n");
                sql.append(",GRADEXAM, WSTEP, WMTEST, WFTEST, WHTEST, WREPORT\n");
                sql.append(",WETC1, WETC2, GRADFTEST, GRADHTEST\n");
                sql.append(",PLACE, CHARGER, BILLBEGINDT, BILLENDDT\n");
                sql.append(",STARTCANCELDATE, ENDCANCELDATE, SPECIALS, TUTORID\n");
                sql.append(",TERMTOTAL, ISUSE, ISVISIBLE, ISINTRODUCTION\n");
                sql.append(",EDUDAYS, EDUDAYSTYPE, GOYONGPRICEMAJOR\n");
                sql.append(",GOYONGPRICEMINOR, SUBJTARGET, INTRO, EXPLAIN\n");
                sql.append(",NEEDINPUT, LUSERID, LDATE\n");
                sql.append(") SELECT\n");
                sql.append(" A.SUBJ, ':p_year', ':p_subjseq', ':p_seq', ':p_propstart', ':p_propend'\n");
                sql.append(",':p_edustart', ':p_eduend', A.ISCLOSED, ':p_subjnm', ':p_studentlimit'\n");
                sql.append(",A.BIYONG, A.ISGOYONG, A.GRADSCORE, A.GRADSTEP, A.GRADREPORT\n");
                sql.append(",A.GRADEXAM, A.WSTEP, A.WMTEST, A.WFTEST, A.WHTEST, A.WREPORT\n");
                sql.append(",A.WETC1, A.WETC2, A.GRADFTEST, A.GRADHTEST\n");
                sql.append(",A.PLACE, A.CHARGER, ':p_billbegindt', ':p_billenddt'\n");
                sql.append(",':p_startcanceldate', ':p_endcanceldate', A.SPECIALS, A.TUTORID\n");
                sql.append(",A.TERMTOTAL, A.ISUSE, A.ISVISIBLE, A.ISINTRODUCTION\n");
                sql.append(",A.EDUDAYS, A.EDUDAYSTYPE, A.GOYONGPRICEMAJOR\n");
                sql.append(",A.GOYONGPRICEMINOR, A.SUBJTARGET, A.INTRO, A.EXPLAIN\n");
                sql.append(",A.NEEDINPUT, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS')\n");
                sql.append("FROM TZ_OFFSUBJSEQ A\n");
                sql.append("WHERE A.SUBJ = ':p_subj'\n");
                sql.append("AND (A.YEAR || A.SUBJSEQ || A.SEQ)\n");
                sql.append(" = (SELECT MAX(YEAR || SUBJSEQ || SEQ) FROM TZ_OFFSUBJSEQ\n");
                sql.append("    WHERE SUBJ = A.SUBJ)\n");

                pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
                //				System.out.println(connMgr.replaceParam(sql.toString(), box, true));
                int i = 1;
                pstmt.setString(i++, box.getSession("userid"));

                isOk = pstmt.executeUpdate();

                if (isOk == 1) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
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
     * 새로운 차수 등록 - 오프라인
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int InsertSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            String v_subj = box.getString("p_subj");
            String v_year = box.getString("p_year");
            String v_subjseq = box.getString("p_subjseq");
            String v_seq = box.getString("p_seq");

            /*********************************************************************************************/
            // 나모에디터 본문 처리
            ConfigSet conf = new ConfigSet();
            SmeNamoMime namo = new SmeNamoMime(box.getString("p_explain")); // 객체생성
            boolean result = namo.parse(); // 실제 파싱 수행
            if (!result) { // 파싱 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
                String v_server = conf.getProperty("kocca.url.value");
                String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
                String refUrl = conf.getProperty("url.namo");
                ; // 웹에서 저장된 파일을 접근하기 위한 경로
                String prefix = "offSubj" + v_subj; // 파일명 접두어
                result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
            }
            if (!result) { // 파일저장 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            /*********************************************************************************************/

            // 중복 체크
            if (!checkSubjcode(connMgr, v_subj, v_year, v_subjseq, v_seq)) {
                sql.append("INSERT INTO TZ_OFFSUBJSEQ A (\n");
                sql.append(" A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ, A.PROPSTART, A.PROPEND\n");
                sql.append(",A.EDUSTART, A.EDUEND, A.ISCLOSED, A.SUBJNM, A.STUDENTLIMIT\n");
                sql.append(",A.BIYONG, A.ISGOYONG, A.GRADSCORE, A.GRADSTEP, A.GRADREPORT\n");
                sql.append(",A.GRADEXAM, A.WSTEP, A.WMTEST, A.WFTEST, A.WHTEST, A.WREPORT\n");
                sql.append(",A.WETC1, A.WETC2, A.GRADFTEST, A.GRADHTEST\n");
                sql.append(",A.PLACE, A.CHARGER, A.BILLBEGINDT, A.BILLENDDT\n");
                sql.append(",A.STARTCANCELDATE, A.ENDCANCELDATE, A.SPECIALS, A.TUTORID\n");
                sql.append(",A.TERMTOTAL, A.ISUSE, A.ISVISIBLE, A.ISINTRODUCTION\n");
                sql.append(",A.EDUDAYS, A.EDUDAYSTYPE, A.GOYONGPRICEMAJOR\n");
                sql.append(",A.GOYONGPRICEMINOR, A.SUBJTARGET, A.INTRO, A.EXPLAIN, GRADETC1, GRADETC2\n");
                sql.append(",A.NEEDINPUT, A.LUSERID, A.LDATE\n");
                sql.append(") VALUES (\n");
                sql.append(" ':p_subj', ':p_year', (SELECT lpad(NVL(MAX(SUBJSEQ),0)+1, 4,'0') FROM TZ_OFFSUBJSEQ WHERE SUBJ= ':p_subj' AND YEAR = ':p_year'), 1, ':p_propstart', ':p_propend'\n");
                sql.append(",':p_edustart', ':p_eduend', 'N', ':p_subjnm', ':p_studentlimit'\n");
                sql.append(",':p_biyong', ':p_isgoyong', ':p_gradscore', ':p_gradstep', ':p_gradreport'\n");
                sql.append(",':p_gradexam', ':p_wstep', ':p_wmtest', ':p_wftest', ':p_whtest', ':p_wreport'\n");
                sql.append(",':p_wetc1', ':p_wetc2', ':p_gradftest', ':p_gradhtest'\n");
                sql.append(",':p_place', ':p_charger', ':p_billbegindt', ':p_billenddt'\n");
                sql.append(",':p_startcanceldate', ':p_endcanceldate', ':p_specials', ':p_tutorid'\n");
                sql.append(",':p_term', ':p_isuse', ':p_isvisible', ':p_isintroduction'\n");
                sql.append(",':p_edudays', ':p_edudaystype', ':p_goyongpricemajor'\n");
                sql.append(",':p_goyongpriceminor', ':p_subjtarget', ':p_intro', ?, ':p_gradetc1', ':p_gradetc2'\n");
                sql.append(",':p_needinput', ?, TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS')\n");
                sql.append(")\n");

                pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
                int i = 1;
                pstmt.setCharacterStream(i++, new StringReader(namo.getContent()), namo.getContent().length());
                //pstmt.setString(i++, "");
                pstmt.setString(i++, box.getSession("userid"));

                isOk = pstmt.executeUpdate();

                if (isOk == 1) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
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
     * 설명 :
     * 
     * @param box
     * @return
     * @throws Exception
     * @author swchoi
     */
    public List<DataBox> listPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT\n");
            sql.append("B.SUBJYEAR, A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ, A.PROPSTART, A.PROPEND\n");
            sql.append(",A.EDUSTART, A.EDUEND, A.ISCLOSED, A.SUBJNM, A.STUDENTLIMIT\n");
            sql.append(",A.BIYONG, A.ISGOYONG, A.GRADSCORE, A.GRADSTEP, A.GRADREPORT\n");
            sql.append(",A.GRADEXAM, A.WSTEP, A.WMTEST, A.WFTEST, A.WHTEST, A.WREPORT\n");
            sql.append(",A.WETC1, A.WETC2, A.LUSERID, A.LDATE, A.GRADFTEST, A.GRADHTEST\n");
            sql.append(",A.PLACE, A.CHARGER, A.BILLBEGINDT, A.BILLENDDT\n");
            sql.append(",A.STARTCANCELDATE, A.ENDCANCELDATE, A.SPECIALS, A.TUTORID\n");
            sql.append(",A.TERMTOTAL, A.ISUSE, A.ISVISIBLE, A.ISINTRODUCTION\n");
            sql.append(",A.EDUDAYS, A.EDUDAYSTYPE, A.GOYONGPRICEMAJOR\n");
            sql.append(",A.GOYONGPRICEMINOR, A.SUBJTARGET, A.INTRO, A.EXPLAIN\n");
            sql.append(",A.NEEDINPUT, B.SUBJNM, (SELECT COUNT(USERID) FROM TZ_OFFPROPOSE WHERE SUBJ=A.SUBJ AND YEAR=A.YEAR AND SUBJSEQ=A.SUBJSEQ AND SEQ=A.SEQ) PCNT   \n");
            sql.append("FROM TZ_OFFSUBJSEQ A, TZ_OFFSUBJ B\n");
            sql.append("WHERE 1=1	\n");
            sql.append("	AND A.SUBJ = B.SUBJ\n");
            if (box.get("isTerm").equals("true")) {
                sql.append("	AND A.SEQ = (SELECT MAX(SEQ) FROM TZ_OFFSUBJSEQ WHERE SUBJ=A.SUBJ AND YEAR=A.YEAR AND SUBJSEQ=A.SUBJSEQ)\n");
            }
            sql.append("	AND 1 = DECODE(NVL(:s_subjcode, 0), A.SUBJ, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_subjseq, 0), A.SUBJSEQ, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_year, 0), B.SUBJYEAR, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_upperclass, UPPERCLASS), UPPERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_middleclass, MIDDLECLASS), MIDDLECLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:s_lowerclass, LOWERCLASS), LOWERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND A.SUBJNM LIKE '%' || nvl(:s_subjsearchkey, A.SUBJNM) || '%'\n");

            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);

            result = ls.getAllDataList();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, t);
            throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
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
     * 설명 : 상세 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     * @author swchoi
     */
    public DataBox selectPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox result = null;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT\n");
            sql.append(" SUBJ, YEAR, SUBJSEQ, SEQ,(SELECT ISTERM FROM TZ_OFFSUBJ WHERE SUBJ =A.SUBJ) ISTERM, \n");
            sql.append(" PROPSTART, PROPEND,suryo_hideshow_yn\n");
            sql.append(",EDUSTART, EDUEND, ISCLOSED, SUBJNM, STUDENTLIMIT,NVL(STUDENTWAIT,0) as STUDENTWAIT\n");
            sql.append(",BIYONG, ISGOYONG, GRADSCORE, GRADSTEP, GRADREPORT\n");
            sql.append(",GRADEXAM, WSTEP, WMTEST, WFTEST, WHTEST, WREPORT\n");
            sql.append(",WETC1, WETC2, GRADFTEST, GRADHTEST,edu_sumtime,filedapply\n");
            sql.append(",PLACE, CHARGER, BILLBEGINDT, BILLENDDT\n");
            sql.append(",STARTCANCELDATE, ENDCANCELDATE, SPECIALS, TUTORID\n");
            sql.append(",TERMTOTAL, ISUSE, ISVISIBLE, ISINTRODUCTION\n");
            sql.append(",EDUDAYS, EDUDAYSTYPE, GOYONGPRICEMAJOR\n");
            sql.append(",GOYONGPRICEMINOR, SUBJTARGET, INTRO, EXPLAIN, GRADETC1, GRADETC2\n");
            sql.append(",NEEDINPUT, LUSERID, LDATE\n");
            sql.append("FROM TZ_OFFSUBJSEQ A\n");
            sql.append("WHERE A.SUBJ = :p_subj	\n");
            sql.append("AND (A.YEAR || A.SUBJSEQ || A.SEQ)\n");
            sql.append(" = (SELECT MAX(YEAR || SUBJSEQ || SEQ) FROM TZ_OFFSUBJSEQ\n where subj = a.subj\n");
            sql.append("	AND 1 = DECODE(NVL(:u_year, YEAR), YEAR, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:u_subjseq, SUBJSEQ), SUBJSEQ, 1, 'ALL', 1, 0)	\n");
            sql.append("	AND 1 = DECODE(NVL(:u_seq, SEQ), SEQ, 1, 'ALL', 1, 0))	\n");

            ls = connMgr.executeQuery(sql.toString(), box);

            if (ls.next()) {
                result = ls.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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

    public ArrayList selectsubjseq_detail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox result = null;
        ArrayList list1 = null;

        String v_subj = box.getString("p_subj").trim();
        String v_year = box.getString("u_year").trim();
        String v_subjseq = box.getString("u_subjseq").trim();

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql.append("SELECT\n");
            sql.append(" a.*,b.userid,c.name \n");
            sql.append("FROM tz_subjseq_detail a \n");
            sql.append("left join tz_subjman b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n");
            sql.append("left join tz_member c on b.userid=c.userid \n");
            sql.append("WHERE a.SUBJ ='" + v_subj + "'\n");
            sql.append("and a.year ='" + v_year + "'\n");
            sql.append("and a.subjseq ='" + v_subjseq + "'\n");
            sql.append(" order by sub_dt,sub_start_time\n");
            //            sql.append("WHERE userid ='	"+v_userid+"'\n");

            ls = connMgr.executeQuery(sql.toString(), box);

            while (ls.next()) {
                result = ls.getDataBox();
                list1.add(result);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
     * 과정정보 수정 - 오프라인
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //v_subj = getMaxSubjcode(connMgr, box.getString("p_upperclass"), box.getString("p_middleclass"));
            String v_subj = box.getString("p_subj");

            /*********************************************************************************************/
            // 나모에디터 본문 처리
            ConfigSet conf = new ConfigSet();
            SmeNamoMime namo = new SmeNamoMime(box.getString("p_explain")); // 객체생성
            boolean result = namo.parse(); // 실제 파싱 수행
            if (!result) { // 파싱 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
                String v_server = conf.getProperty("kocca.url.value");
                String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
                String refUrl = conf.getProperty("url.namo");
                ; // 웹에서 저장된 파일을 접근하기 위한 경로
                String prefix = "offSubj" + v_subj; // 파일명 접두어
                result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
            }
            if (!result) { // 파일저장 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            //			box.put("p_explain", namo.getContent()); // 최종 컨텐트 얻기
            /*********************************************************************************************/

            //			System.out.println(box.getString("p_explain").getBytes().length);
            // 중복 체크
            sql.append("UPDATE TZ_OFFSUBJSEQ SET\n");
            sql.append("	SUBJ				=':p_subj',\n");
            sql.append("	YEAR			=':p_year',\n");
            sql.append("	SUBJSEQ		=':p_subjseq',\n");
            sql.append("	SEQ				=':p_seq',\n");
            sql.append("	PROPSTART	=':p_propstart',\n");
            sql.append("	PROPEND		=':p_propend',\n");
            sql.append("	EDUSTART	=':p_edustart',\n");
            sql.append("	EDUEND		=':p_eduend',\n");
            //				sql.append("	ISCLOSED		=':p_isclosed',\n");
            sql.append("	SUBJNM		=':p_subjnm',\n");
            sql.append("	STUDENTLIMIT	=':p_studentlimit',\n");
            sql.append("    STUDENTWAIT=':p_studentwait',\n");
            sql.append("	BIYONG			=':p_biyong',\n");
            sql.append("	ISGOYONG	=':p_isgoyong',\n");
            sql.append("	GRADSCORE	=':p_gradscore',\n");
            sql.append("	GRADSTEP	=':p_gradstep',\n");
            sql.append("	GRADREPORT=':p_gradreport',\n");
            sql.append("	GRADEXAM	=':p_gradexam',\n");
            sql.append("	WSTEP			=':p_wstep',\n");
            sql.append("	WMTEST		=':p_wmtest',\n");
            sql.append("	WFTEST		=':p_wftest',\n");
            sql.append("	WHTEST		=':p_whtest',\n");
            sql.append("	WREPORT		=':p_wreport',\n");
            sql.append("	WETC1			=':p_wetc1',\n");
            sql.append("	WETC2			=':p_wetc2',\n");
            sql.append("	GRADFTEST	=':p_gradftest',\n");
            sql.append("	GRADHTEST	=':p_gradhtest',\n");
            sql.append("	PLACE			=':p_place',\n");
            sql.append("	CHARGER		=':p_charger',\n");
            sql.append("	BILLBEGINDT	=':p_billbegindt',\n");
            sql.append("	BILLENDDT	=':p_billenddt',\n");
            sql.append("	STARTCANCELDATE= ':p_startcanceldate',\n");
            sql.append("	ENDCANCELDATE	=':p_endcanceldate',\n");
            sql.append("	SPECIALS		=':p_specials',\n");
            sql.append("	TUTORID		=':p_tutorid',\n");
            sql.append("	TERMTOTAL	=':p_term',\n");
            sql.append("	ISUSE			=':p_isuse',\n");
            sql.append("	ISVISIBLE		=':p_isvisible',\n");
            sql.append("	ISINTRODUCTION	=':p_isintroduction',\n");
            sql.append("	EDUDAYS		=':p_edudays',\n");
            sql.append("	EDUDAYSTYPE	=':p_edudaystype',\n");
            sql.append("	GOYONGPRICEMAJOR =':p_goyongpricemajor',\n");
            sql.append("	GOYONGPRICEMINOR =':p_goyongpriceminor',\n");
            sql.append("	SUBJTARGET	=':p_subjtarget',\n");
            sql.append("	INTRO			=':p_intro',\n");
            sql.append("	EXPLAIN		=?,\n");
            sql.append("	NEEDINPUT	=':p_needinput',\n");
            sql.append("	GRADETC1	=':p_gradetc1',\n");
            sql.append("	GRADETC2	=':p_gradetc2',\n");
            sql.append("	suryo_hideshow_yn	=':p_suryo_hideshow_yn',\n");
            sql.append("	LUSERID		=	?,\n");
            sql.append("	LDATE			=	TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS')\n");
            sql.append("WHERE\n");
            sql.append("	SUBJ				=':p_subj'\n");
            sql.append("AND	SUBJSEQ	=':p_subjseq'\n");
            sql.append("AND	seq		=':p_seq'\n");
            sql.append("AND	year		=':p_year'\n");

            pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
            int i = 1;
            pstmt.setCharacterStream(i++, new StringReader(namo.getContent()), namo.getContent().length());
            //				pstmt.setString(i++, namo.getContent());
            pstmt.setString(i++, box.getSession("userid"));

            isOk = pstmt.executeUpdate();

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
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

    public DataBox selectTeachDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox result = null;

        String v_subj = box.getString("p_subj").trim();
        String v_year = box.getString("p_year").trim();
        String v_subjseq = box.getString("p_subjseq").trim();
        String v_seq1 = box.getString("p_seq1").trim();

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT\n");
            sql.append(" * \n");
            sql.append("FROM tz_subjseq_detail a \n");
            sql.append("WHERE a.SUBJ ='" + v_subj + "'\n");
            sql.append("and a.year ='" + v_year + "'\n");
            sql.append("and a.subjseq ='" + v_subjseq + "'\n");
            sql.append("and a.seq ='" + v_seq1 + "'\n");

            ls = connMgr.executeQuery(sql.toString(), box);

            if (ls.next()) {
                result = ls.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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

    public ArrayList selectTeachDailyPrint(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox result = null;
        ArrayList list1 = null;

        String v_subj = box.getString("p_subj").trim();
        String v_year = box.getString("p_year").trim();
        String v_subjseq = box.getString("p_subjseq").trim();
        String v_printdate = box.getString("p_printdate").trim();
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql.append("SELECT\n");
            sql.append(" d.subjnm, \n");
            sql.append("(select min(sub_dt) from tz_subjseq_detail z where a.subj=z.subj and a.year=z.year and a.subjseq=d.subjseq) as min_dt, \n");
            sql.append("(select max(sub_dt) from tz_subjseq_detail z where a.subj=z.subj and a.year=z.year and a.subjseq=d.subjseq) as max_dt, \n");
            sql.append("(select count(*) from tz_offpropose z where a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq and CHKFINAL='Y') as total, \n");
            sql.append("a.sub_title,c.name,a.sub_content,substr(a.sub_dt,1,4)||'년 '||substr(a.sub_dt,5,2)||'월 '||substr(a.sub_dt,7,2)||'일 ' || '(' || to_char(to_date(a.sub_dt),'dy') || '요일)' as weekday \n");
            sql.append("FROM tz_subjseq_detail a  \n");
            sql.append("left join tz_subjman b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq   \n");
            sql.append("left join tz_member c on b.userid=c.userid   \n");
            sql.append("left join tz_offsubjseq d on a.subj=d.subj and a.year=d.year and a.subjseq=d.subjseq  \n");
            sql.append("WHERE a.SUBJ ='" + v_subj + "'\n");
            sql.append("and a.year ='" + v_year + "'\n");
            sql.append("and a.subjseq ='" + v_subjseq + "'\n");
            sql.append("and a.sub_dt='" + v_printdate + "' \n");
            //            sql.append("WHERE userid ='	"+v_userid+"'\n");

            ls = connMgr.executeQuery(sql.toString(), box);

            while (ls.next()) {
                result = ls.getDataBox();
                list1.add(result);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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

    public int updateTeachDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_seq1 = box.getString("p_seq1");
        String dt = box.getString("p_input_year") + box.getString("p_input_month") + box.getString("p_input_day");
        String ti = box.getString("p_input_start_hour") + box.getString("p_input_start_min");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            if (v_seq1.equals("")) {
                sql.append("insert into tz_subjseq_detail values (\n");
                sql.append(" ?,	?,?,(select nvl(max(seq),0)+1 from tz_subjseq_detail where subj=? and year=? and subjseq=?),?,?,?,?,?,?) \n");
                pstmt = connMgr.prepareStatement(sql.toString());
                int i = 1;
                pstmt.setString(i++, v_subj);
                pstmt.setString(i++, v_year);
                pstmt.setString(i++, v_subjseq);
                pstmt.setString(i++, v_subj);
                pstmt.setString(i++, v_year);
                pstmt.setString(i++, v_subjseq);
                pstmt.setString(i++, dt);
                pstmt.setString(i++, ti);
                pstmt.setString(i++, box.getString("p_input_sum_time"));
                pstmt.setString(i++, box.getString("p_input_content"));
                pstmt.setString(i++, box.getString("p_input_title"));
                pstmt.setString(i++, box.getString("p_input_target"));

                isOk = pstmt.executeUpdate();
            } else {
                sql.append("update tz_subjseq_detail set sub_dt='" + dt + "',sub_start_time='" + ti + "',sub_title='" + box.getString("p_input_title") + "',sub_content='" + box.getString("p_input_content") + "',sub_time='"
                        + box.getString("p_input_sum_time") + "',sub_target='" + box.getString("p_input_target") + "' \n");
                sql.append("where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' and seq='" + v_seq1 + "'");
                pstmt = connMgr.prepareStatement(sql.toString());
                isOk = pstmt.executeUpdate();
            }

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
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
}
