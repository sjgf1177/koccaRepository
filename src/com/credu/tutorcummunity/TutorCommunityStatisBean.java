//**********************************************************
//	1. 제	   목: 강사 질문방
//	2. 프로그램명: AdminQnaBean.java
//	3. 개	   요: 강사 질문방
//	4. 환	   경: JDK 1.4
//	5. 버	   젼: 1.0
//	6. 작	   성: 강성욱 2005.	9. 20
//	7. 수	   정:
//**********************************************************

package com.credu.tutorcummunity;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

public class TutorCommunityStatisBean {
    // private static final String FILE_TYPE = "p_file"; //		파일업로드되는 tag name
    private static final int FILE_LIMIT = 10; //	  페이지에 세팅된 파일첨부 갯수

    public static int getFILE_LIMIT() {
        return FILE_LIMIT;
    }

    private ConfigSet config;
    private int row;

    public TutorCommunityStatisBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DataBox> selectTutorCommunity(RequestBox box) throws Exception {
        Calendar calendar = Calendar.getInstance();
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String toDate = new java.text.SimpleDateFormat("yyyyMMdd").format(calendar.getTime());

        /* sql 작성 변수 */
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String where_sql = "";
        String order_sql = "";
        String count_sql = "";

        /* 페이지링 변수 */
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        /* 검색 조건 변수 */
        String v_tgubun = box.getStringDefault("s_tgubun", "ALL");
        String v_sdate = box.getStringDefault("s_sdate", toDate).replace("-", "") + "000000";
        String v_edate = box.getStringDefault("s_edate", toDate).replace("-", "") + "235959";
        String v_area = box.getStringDefault("s_area", "ALL");
        String v_subj = box.getStringDefault("s_subj", "ALL");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += "SELECT \n";
            head_sql += "    A.TGUBUN, \n";
            head_sql += "    A.TABSEQ, \n";
            head_sql += "    A.SEQ, \n";
            head_sql += "    A.TYPES, \n";
            head_sql += "    A.TITLE, \n";
            head_sql += "    A.INDATE, \n";
            head_sql += "    A.INUSERID, \n";
            head_sql += "    A.CNT, \n";
            head_sql += "    M.NAME, \n";
            head_sql += "    A.SUBJ, \n";
            head_sql += "    A.YEAR, \n";
            head_sql += "    A.SUBJSEQ, \n";
            head_sql += "    A.LESSON, \n";
            head_sql += "    S.SUBJNM, \n";
            head_sql += "    NVL(S.AREA,'C0') AS AREA, \n";
            head_sql += "    S.UPPERCLASS, \n";
            head_sql += "    S.MIDDLECLASS, \n";
            head_sql += "    S.LOWERCLASS, \n";
            head_sql += "    A.ANSWERCNT, \n";
            head_sql += "    A.COMMENTCNT \n";
            body_sql += "FROM \n";
            body_sql += "    TZ_MEMBER M, \n";
            body_sql += "    ( \n";
            body_sql += "    SELECT 'C' AS TGUBUN, TABSEQ, SEQ, TYPES, TITLE, INDATE, INUSERID, CNT, '' AS SUBJ, '' AS YEAR, '' AS SUBJSEQ, '' AS LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_HOMEQNA WHERE TABSEQ = '5' AND TYPES != '0' AND SEQ = T.SEQ) AS ANSWERCNT,(SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '5' AND SEQ = T.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_HOMEQNA T WHERE TABSEQ = '5' AND TYPES = '0' \n";

            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'I' AS TGUBUN, TABSEQ, SEQ, TYPES, TITLE, INDATE, INUSERID, CNT, CATEGORYCD AS SUBJ, '' AS YEAR, '' AS SUBJSEQ, '' AS LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_HOMEQNA WHERE TABSEQ = '7' AND TYPES != '0' AND SEQ = T.SEQ) AS ANSWERCNT,(SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '7' AND SEQ = T.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_HOMEQNA T WHERE TABSEQ = '7' AND TYPES = '0' \n";

            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'Q' AS TGUBUN, 0 AS TABSEQ, SEQ, KIND AS TYPES, TITLE, INDATE, INUSERID, CNT, SUBJ, YEAR, SUBJSEQ, LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_QNA WHERE SUBJ = T.SUBJ AND SUBJSEQ = T.SUBJSEQ AND YEAR = T.YEAR AND LESSON = T.LESSON AND KIND != '0' AND SEQ = T.SEQ) AS ANSWERCNT, (SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '0' AND SEQ = T.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_QNA T WHERE KIND = '0' \n";

            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'D' AS TGUBUN, SA.TABSEQ, SA.SEQ, '0' AS TYPES, SA.TITLE, SA.INDATE, SA.USERID AS INUSERID, SA.CNT, SB.SUBJ, SB.YEAR, SB.SUBJSEQ, '' AS LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_BOARD WHERE TABSEQ = SA.TABSEQ AND LEVELS != '1' AND SEQ = SA.SEQ) AS ANSWERCNT, (SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = SA.TABSEQ AND SEQ = SA.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_BOARD SA, TZ_BDS SB WHERE SB.TYPE = 'SD' AND SA.TABSEQ = SB.TABSEQ AND SA.LEVELS = '1' \n";

            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'C' AS TGUBUN, SA.TABSEQ, SA.SEQ, '0' AS TYPES, SA.TITLE, SA.INDATE, SA.USERID AS INUSERID, SA.CNT, SB.SUBJ, SB.YEAR, SB.SUBJSEQ, '' AS LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_BOARD WHERE TABSEQ = SA.TABSEQ AND LEVELS != '1' AND SEQ = SA.SEQ) AS ANSWERCNT, (SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = SA.TABSEQ AND SEQ = SA.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_BOARD SA, TZ_BDS SB WHERE SB.TYPE = 'SB' AND SA.TABSEQ = SB.TABSEQ AND SA.LEVELS = '1' \n";

            body_sql += "    ) A, \n";
            body_sql += "    TZ_SUBJ S \n";
            where_sql += "WHERE \n";
            where_sql += "	M.USERID = A.INUSERID \n";
            where_sql += "	AND A.INDATE >= " + StringManager.makeSQL(v_sdate) + "\n";
            where_sql += "	AND A.INDATE <= " + StringManager.makeSQL(v_edate) + "\n";
            where_sql += "	AND A.SUBJ = S.SUBJ(+) \n";
            if (!v_tgubun.equals("ALL")) {
                where_sql += "	AND TGUBUN = " + StringManager.makeSQL(v_tgubun) + "\n";
            }
            if (!v_area.equals("ALL")) {
                where_sql += "	AND NVL(AREA,'C0') = " + StringManager.makeSQL(v_area) + "\n";
            }
            if (!v_subj.equals("ALL")) {
                where_sql += "	AND A.SUBJ = " + StringManager.makeSQL(v_subj) + "\n";
            }

            order_sql += "ORDER BY A.INDATE DESC \n";

            sql = head_sql + body_sql + where_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(1) " + body_sql + where_sql;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다
            ls.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
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
     * 선택된 게시물 상세내용
     */
    public DataBox selectTutorCommunityDetailView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_tgubun = box.getString("p_tgubun");
        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();
            if (v_tabseq == 7 || v_tabseq == 5) { /*
                                                   * TGUBUN 이 정보(I) 일 경우
                                                   * TZ_HOMEQNA - TABSEQ = "7"
                                                   */
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ,\n";
                sql += "	A.SEQ, \n";
                sql += "	A.TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_HOMEQNA A, \n";
                sql += "	TZ_HOMEFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.TYPES = " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.TYPES = B.TYPES(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
            } else if (v_tabseq == 0) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	'0' AS TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.KIND AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	A.SUBJ, \n";
                sql += "	A.SUBJSEQ, \n";
                sql += "	A.YEAR, \n";
                sql += "	A.LESSON, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_QNA A, \n";
                sql += "	TZ_QNAFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.SUBJ = " + StringManager.makeSQL(v_subj) + " \n";
                sql += "	AND A.SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n";
                sql += "	AND A.YEAR = " + StringManager.makeSQL(v_year) + " \n";
                sql += "	AND A.LESSON = " + StringManager.makeSQL(v_lesson) + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.KIND = " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.SUBJ = B.SUBJ(+) \n";
                sql += "	AND A.YEAR = B.YEAR(+) \n";
                sql += "	AND A.SUBJSEQ = B.SUBJSEQ(+) \n";
                sql += "	AND A.LESSON = B.LESSON(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.KIND = B.KIND(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
                sql += "	AND M.GRCODE = 'N000001'";
            } else {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.LEVELS AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENT AS CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.USERID AS INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_BOARD A, \n";
                sql += "	TZ_BOARDFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.LEVELS = '1' \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.USERID = M.USERID \n";
                sql += "	AND M.GRCODE = 'N000001'";
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }
            }
            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);
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
        return dbox;
    }

    public int selectDelete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;
        // int isOk2 = 0;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        int v_types = box.getInt("p_types");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        try {
            connMgr = new DBConnectionManager();
            if (v_tabseq == 5 || v_tabseq == 7) {
                if (v_types == 0) {
                    sql1 += "DELETE FROM TZ_HOMEQNA \n";
                    sql1 += "WHERE TABSEQ = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                } else {
                    sql1 += "DELETE FROM TZ_HOMEQNA \n";
                    sql1 += "WHERE TABSEQ = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    sql1 += "	AND TYPES = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                    pstmt1.setInt(2, v_types);
                }
            } else if (v_tabseq == 0) {
                if (v_types == 0) {
                    sql1 += "DELETE FROM TZ_QNA \n";
                    sql1 += "WHERE SUBJ = ? \n";
                    sql1 += "	AND SUBJSEQ = ? \n";
                    sql1 += "	AND YEAR = ? \n";
                    sql1 += "	AND LESSON = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setString(1, v_subj);
                    pstmt1.setString(2, v_subjseq);
                    pstmt1.setString(3, v_year);
                    pstmt1.setString(4, v_lesson);
                    pstmt1.setInt(5, v_seq);
                } else {
                    sql1 += "DELETE FROM TZ_QNA \n";
                    sql1 += "WHERE SUBJ = ? \n";
                    sql1 += "	AND SUBJSEQ = ? \n";
                    sql1 += "	AND YEAR = ? \n";
                    sql1 += "	AND LESSON = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    sql1 += "	AND KIND = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setString(1, v_subj);
                    pstmt1.setString(2, v_subjseq);
                    pstmt1.setString(3, v_year);
                    pstmt1.setString(4, v_lesson);
                    pstmt1.setInt(5, v_seq);
                    pstmt1.setInt(5, v_types);
                }
            } else {
                if (v_types == 1) {
                    sql1 = "DELETE FROM TZ_BOARD ";
                    sql1 += "WHERE TABSEQ = ? ";
                    sql1 += "	AND SEQ = ? ";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                } else {
                    sql1 = "DELETE FROM TZ_BOARD ";
                    sql1 += "WHERE TABSEQ = ? ";
                    sql1 += "	AND SEQ = ? ";
                    sql1 += "	AND LEVELS = ? ";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                    pstmt1.setInt(2, v_types);
                }
            }

            sql2 += "DELETE FROM TZ_COMMENTQNA \n";
            sql2 += "WHERE TABSEQ = ? \n";
            sql2 += "	AND SEQ = ? \n";
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setInt(1, v_tabseq);
            pstmt2.setInt(2, v_seq);

            isOk1 = pstmt1.executeUpdate();
            pstmt2.executeUpdate();
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
        return isOk1;
    }

    public ArrayList<DataBox> selectAnswerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        String v_tgubun = box.getString("p_tgubun");
        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        /* sql 작성 변수 */
        String sql = "";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            if (v_tabseq == 7 || v_tabseq == 5) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ,\n";
                sql += "	A.SEQ, \n";
                sql += "	A.TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_HOMEQNA A, \n";
                sql += "	TZ_HOMEFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.TYPES != " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.TYPES = B.TYPES(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
            } else if (v_tabseq == 0) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	'0' AS TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.KIND AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	A.SUBJ, \n";
                sql += "	A.SUBJSEQ, \n";
                sql += "	A.YEAR, \n";
                sql += "	A.LESSON, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_QNA A, \n";
                sql += "	TZ_QNAFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.SUBJ = " + StringManager.makeSQL(v_subj) + " \n";
                sql += "	AND A.SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n";
                sql += "	AND A.YEAR = " + StringManager.makeSQL(v_year) + " \n";
                sql += "	AND A.LESSON = " + StringManager.makeSQL(v_lesson) + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.KIND != " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.SUBJ = B.SUBJ(+) \n";
                sql += "	AND A.YEAR = B.YEAR(+) \n";
                sql += "	AND A.SUBJSEQ = B.SUBJSEQ(+) \n";
                sql += "	AND A.LESSON = B.LESSON(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.KIND = B.KIND(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
            } else {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.LEVELS AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENT AS CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.USERID AS INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_BOARD A, \n";
                sql += "	TZ_BOARDFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.REFSEQ = " + v_seq + " \n";
                sql += "	AND A.LEVELS != '1' \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.USERID = M.USERID \n";
            }

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }
                if (realfileVector != null)
                    dbox.put("d_realfile", realfileVector);
                if (savefileVector != null)
                    dbox.put("d_savefile", savefileVector);
                if (fileseqVector != null)
                    dbox.put("d_fileseq", fileseqVector);

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

    public ArrayList<DataBox> selectCommentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");

        String sql = "";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql += "SELECT A.*, B.NAME \n";
            sql += "FROM TZ_COMMENTQNA A, TZ_MEMBER B \n";
            sql += "WHERE \n";
            sql += "	A.TABSEQ = " + v_tabseq + " \n";
            sql += "	AND A.SEQ = " + v_seq + " \n";
            sql += "	AND A.INUSERID = B.USERID(+) \n";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
                System.out.println(dbox);
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
     * 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int answerRegister(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls2 = null;
        PreparedStatement pstmt = null;
        String typesSql = "";
        String levelSql = "";
        StringBuffer sql = new StringBuffer();

        int isOk = 0;
        int t_types = 0;
        String v_types = "";
        int v_levels = 0;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));

        String v_userid = box.getSession("userid");
        String v_name = box.getSession("name");
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            /*********************************************************************************************/
            // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.                                                         
            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            //types, kind, seq 값 구하기
            if (v_tabseq == 5 || v_tabseq == 7) {
                typesSql += "SELECT MAX(TYPES) FROM TZ_HOMEQNA WHERE TABSEQ=" + v_tabseq + " AND SEQ=" + v_seq;
            } else if (v_tabseq == 0) {
                typesSql += "SELECT MAX(KIND) FROM TZ_QNA WHERE SUBJ='" + v_subj + "' AND SUBJSEQ='" + v_subjseq + "' AND YEAR='" + v_year + "' AND LESSON='" + v_lesson + "' AND SEQ=" + v_seq;
            } else {
                typesSql += "SELECT MAX(SEQ) FROM TZ_BOARD WHERE TABSEQ=" + v_tabseq;
                levelSql = "CHECK";
            }
            ls = connMgr.executeQuery(typesSql);
            if (ls.next()) {
                t_types = ls.getInt(1) + 1;
            } else {
                t_types = 1;
            }
            v_types = t_types + "";

            //levels 값 구하기
            if (levelSql.equals("CHECK")) {
                levelSql = "SELECT MAX(LEVELS) FROM TZ_HOMEQNA WHERE TABSEQ=" + v_tabseq + " AND REFSEQ=" + v_seq;
                ls2 = connMgr.executeQuery(levelSql);
                if (ls.next()) {
                    v_levels = ls.getInt(1) + 1;
                } else {
                    v_levels = 2;
                }
            }

            if (v_tabseq == 5 || v_tabseq == 7) {
                sql.append("INSERT INTO TZ_HOMEQNA \n");
                sql.append("	(TABSEQ,	SEQ,		TYPES,		TITLE,		CONTENTS	\n");
                sql.append("	,INDATE,	INUSERID,	ISOPEN,		LUSERID,	LDATE		\n");
                sql.append("	,CNT,		GRCODE,		CATEGORYCD)							\n");
                sql.append("VALUES														\n");
                sql.append("	(?,	?, ?, ?, ? \n");
                sql.append("	,to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, 'Y', ?,to_char(sysdate, 'YYYYMMDDHH24MISS') \n");
                sql.append("	,0,	?, ?) \n");

                int index = 1;
                pstmt = connMgr.prepareStatement(sql.toString());
                pstmt.setInt(index++, v_tabseq);
                pstmt.setInt(index++, v_seq);
                pstmt.setString(index++, v_types);
                pstmt.setString(index++, v_title);
                pstmt.setString(index++, v_content);
                pstmt.setString(index++, v_userid);
                pstmt.setString(index++, v_userid);
                pstmt.setString(index++, v_grcode);
                pstmt.setString(index++, v_subj);
            } else if (v_tabseq == 0) {
                sql.append("INSERT INTO TZ_QNA	\n");
                sql.append("	(SUBJ,		YEAR,		SUBJSEQ,	LESSON,			SEQ			\n");
                sql.append("	,KIND,		TITLE,		INDATE,		INUSERID,		ISOPEN		\n");
                sql.append("	,LUSERID,	LDATE,		GRCODE,		CATEGORYCD,		CONTENTS	\n");
                sql.append("	,CNT)															\n");
                sql.append("VALUES															\n");
                sql.append("	(?, ?, ?, ?, ?													\n");
                sql.append("	,?, ?,to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, 'Y'			 	\n");
                sql.append("	,?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, '00', ?			\n");
                sql.append("	,0)	\n");

                int index = 1;
                pstmt = connMgr.prepareStatement(sql.toString());
                pstmt.setString(index++, v_subj);
                pstmt.setString(index++, v_year);
                pstmt.setString(index++, v_subjseq);
                pstmt.setString(index++, v_lesson);
                pstmt.setInt(index++, v_seq);
                pstmt.setString(index++, v_types);
                pstmt.setString(index++, v_title);
                pstmt.setString(index++, v_userid);
                pstmt.setString(index++, v_userid);
                pstmt.setString(index++, v_grcode);
                pstmt.setString(index++, v_content);
            } else {
                sql.append("INSERT INTO TZ_BOARD											\n");
                sql.append("	(TABSEQ, SEQ, TITLE, USERID, NAME							\n");
                sql.append("	,CONTENT, INDATE, REFSEQ, LEVELS, POSITION					\n");
                sql.append("	,CNT, LUSERID, LDATE) 										\n");
                sql.append("VALUES														\n");
                sql.append("	(?, ?, ?, ?, ?												\n");
                sql.append("	,?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?			\n");
                sql.append("	,0, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))				 \n");

                int index = 1;
                pstmt = connMgr.prepareStatement(sql.toString());
                pstmt.setInt(index++, v_tabseq);
                pstmt.setInt(index++, t_types);
                pstmt.setString(index++, v_title);
                pstmt.setString(index++, v_userid);
                pstmt.setString(index++, v_name);
                pstmt.setString(index++, v_content);
                pstmt.setInt(index++, v_seq);
                pstmt.setInt(index++, v_levels);
                pstmt.setInt(index++, v_levels);
                pstmt.setString(index++, v_userid);
            }

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
            Log.err.println("isOk==> " + isOk);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
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
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

}
