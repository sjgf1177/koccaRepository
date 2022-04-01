//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class OffStudentJobBean {

    private ConfigSet config;
    private int row;

    // private static final String FILE_TYPE = "p_file"; //		파일업로드되는 tag name
    // private static final int FILE_LIMIT = 1; //	  페이지에 세팅된 파일첨부 갯수

    public OffStudentJobBean() {
        try {
            config = new ConfigSet();
            //row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
            row = Integer.parseInt(config.getProperty("page.manage.row")); //이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 취업관리리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 취업관리리스트
     * @throws Exception
     */
    public ArrayList selectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dt = new Date();

        String v_searchtext = box.getString("p_searchtext");
        String v_subjsearchkey = box.get("s_subjsearchkey");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String v_orderColumn = box.getString("p_orderColumn");
        String v_orderType = box.getString("p_orderType");

        String v_year = box.getStringDefault("s_year", sdf.format(dt));
        String v_subjcode = box.getStringDefault("s_subjcode", "ALL");
        String v_upperclass = box.getStringDefault("s_upperclass", "ALL");
        String v_middleclass = box.getStringDefault("s_middleclass", "ALL");
        String v_lowerclass = box.getStringDefault("s_lowerclass", "ALL");
        String v_state = box.getStringDefault("p_selState", "ALL");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("SELECT  A.SUBJ      \n");
            sql.append("    ,   F.SUBJNM    \n");
            sql.append("    ,   B.SUBJSEQ   \n");
            sql.append("    ,   C.NAME      \n ");
            sql.append("    ,   A.STUDENTNO \n");
            sql.append("    ,   A.STUSTATUS \n");
            sql.append("    ,   E.CODENM STATENM    \n ");
            sql.append("    ,   A.LDATE         \n");
            sql.append("    ,   A.CONFIRMDATE   \n");
            sql.append("    ,   A.YEAR          \n");
            sql.append("    ,   A.USERID        \n ");
            sql.append("    ,   D.COMPNM        \n");
            sql.append("    ,   D.EMPLOYDATE    \n");
            sql.append("    ,   D.EMPLOYGUBUN   \n");
            sql.append("    ,   D.DEPART        \n");
            sql.append("    ,   DECODE(D.TYPE,1,'정',2,'비') AS TYPE  \n");
            sql.append("    ,   COUNT(*) OVER() AS TOT_CNT \n");
            sql.append("    ,   D.LDATE AS LASTDATE \n ");
            sql.append("  FROM  TZ_OFFSTUDENT A \n");
            sql.append("    ,   TZ_OFFSUBJSEQ B \n");
            sql.append("    ,   TZ_MEMBER C     \n ");
            sql.append("    ,   TZ_CODE E       \n");
            sql.append("    ,   TZ_OFFSUBJ F    \n");
            sql.append("    ,   \n ");
            sql.append("       (   \n");
            sql.append("       SELECT  *   \n");
            sql.append("         FROM  (   \n");
            sql.append("               SELECT  A.* \n");
            sql.append("                   ,   COUNT(*) OVER(PARTITION BY SUBJ,YEAR,SUBJSEQ, USERID) MAX_SEQ   \n ");
            sql.append("                 FROM  TZ_OFFCARRIER A \n");
            sql.append("               )   \n");
            sql.append("        WHERE  SEQ = MAX_SEQ   \n");
            sql.append("       ) D \n ");

            sql.append(" WHERE   A.SUBJSEQ   = B.SUBJSEQ   \n ");
            sql.append("   AND   A.SUBJ = B.SUBJ           \n ");
            sql.append("   AND   A.YEAR = B.YEAR           \n ");
            sql.append("   AND   A.SUBJSEQ = B.SUBJSEQ     \n ");
            sql.append("   AND   A.USERID = C.USERID       \n ");
            sql.append("   AND   A.STUSTATUS = E.CODE      \n ");
            sql.append("   AND   E.GUBUN(+) = '0089'       \n ");
            sql.append("   AND   A.SUBJ = F.SUBJ(+)        \n ");
            sql.append("   AND   B.SEQ = 1                 \n ");
            sql.append("   AND   A.SUBJSEQ = D.SUBJSEQ(+)  \n ");
            sql.append("   AND   A.SUBJ = D.SUBJ(+)        \n ");
            sql.append("   AND   A.YEAR = D.YEAR(+)        \n ");
            sql.append("   AND   A.USERID = D.USERID(+)    \n ");
            sql.append("   AND   A.ISGRADUATED = 'Y'       \n ");
            // 수료자만 보이기 수정 '2013.04.23

            if (!v_searchtext.equals("")) { //    검색어가 있으면                                         
                sql.append("  AND  ( UPPER(C.USERID) LIKE UPPER('%").append(v_searchtext).append("%') \n");
                sql.append("   OR  UPPER(C.NAME) LIKE UPPER('%").append(v_searchtext).append("%') ) \n");
            }

            if (!v_subjsearchkey.equals("")) { //    과정명 검색어가 있으면                                         
                sql.append("  AND  UPPER(F.SUBJNM) LIKE UPPER('%").append(v_subjsearchkey).append("%') \n");
            }

            if (!v_year.equals("ALL")) {
                sql.append("  AND  A.YEAR = ").append(StringManager.makeSQL(v_year)).append(" \n");
            }
            if (!v_subjcode.equals("ALL")) {
                sql.append("  AND  F.SUBJ = ").append(StringManager.makeSQL(v_subjcode)).append(" \n");
            }
            if (!v_upperclass.equals("ALL")) {
                sql.append("  AND  F.UPPERCLASS = ").append(StringManager.makeSQL(v_upperclass)).append(" \n");
            }
            if (!v_middleclass.equals("ALL")) {
                sql.append("  AND  F.MIDDLECLASS = ").append(StringManager.makeSQL(v_middleclass)).append(" \n");
            }
            if (!v_lowerclass.equals("ALL")) {
                sql.append("  AND  F.LOWERCLASS = ").append(StringManager.makeSQL(v_lowerclass)).append(" \n");
            }
            if (!v_state.equals("ALL")) {
                sql.append("  AND  A.STUSTATUS = ").append(StringManager.makeSQL(v_state)).append(" \n");
            }

            if (!v_orderColumn.equals("")) {
                sql.append(" ORDER BY ").append(v_orderColumn).append(" ").append(v_orderType);
            } else {
                sql.append(" ORDER BY A.LDATE DESC\n");
            }

            ls = connMgr.executeQuery(sql.toString());

            // countSql = "SELECT COUNT(*) FROM ( " + sql.toString() + " ) ";
            
            int totalrowcount = 0;
            if ( ls.next() ) {
                totalrowcount = ls.getInt("tot_cnt");
                ls.moveFirst();
            }

            // int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(totalrowcount));

                list.add(dbox);
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
        return list;
    }

    /**
     * 개인별 취업리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 개인별 취업리스트
     * @throws Exception
     */
    public ArrayList selectListPerPerson(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";
        String sql = "";
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            headSql.append(" SELECT  A.SEQ, A.COMPNM,   A.EMPLOYGUBUN, A.DEPART       \n ");
            headSql.append("         , C.CODENM EMPLOYGUBUNNM, A.EMPLOYDATE        \n ");
            headSql.append("         , A.SUBJ, A.YEAR, A.SUBJSEQ, A.USERID         \n ");
            bodySql.append(" FROM    TZ_OFFCARRIER A,  TZ_CODE C    \n ");
            bodySql.append(" WHERE                    \n ");
            bodySql.append(" A.EMPLOYGUBUN   = C.CODE(+)                   \n ");
            bodySql.append(" AND     C.GUBUN         = '0099'                      \n ");
            bodySql.append(" AND     A.SUBJSEQ   = '").append(v_subjseq).append("' \n ");
            bodySql.append(" AND     A.SUBJ      = '").append(v_subj).append("'    \n ");
            bodySql.append(" AND     A.YEAR      = '").append(v_year).append("'    \n ");
            bodySql.append(" AND     A.USERID    = '").append(v_userid).append("'  \n ");

            orderSql = " ORDER BY SEQ DESC";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = "SELECT COUNT(*) " + bodySql.toString();

            int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(totalrowcount));

                list.add(dbox);
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
        return list;
    }

    /**
     * 취업 변경내역 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        StringBuffer seqSql = new StringBuffer();
        StringBuffer insertSql = new StringBuffer();

        int isOk = 0;

        int v_seq = 0;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_compnm = box.getString("p_compnm");
        String v_subjseq = box.getString("p_subjseq");
        String v_remarks = box.getString("p_remarks");
        String v_userid = box.getString("p_userid");
        String v_employdate = box.getString("p_employdate");
        String v_employgubun = box.getString("p_employgubun");
        String v_depart = box.getString("p_depart");
        String v_type = box.getString("p_type");
        String v_position = box.getString("p_position");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            seqSql.append(" SELECT MAX(seq) FROM TZ_OFFCARRIER A                   \n ");
            seqSql.append(" WHERE   A.SUBJSEQ   = '").append(v_subjseq).append("'  \n ");
            seqSql.append(" AND     A.SUBJ      = '").append(v_subj).append("'     \n ");
            seqSql.append(" AND     A.YEAR      = '").append(v_year).append("'     \n ");
            seqSql.append(" AND     A.USERID    = '").append(v_userid).append("'   \n ");
            ls = connMgr.executeQuery(seqSql.toString());
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            insertSql.append(" INSERT INTO TZ_OFFCARRIER                                       \n");
            insertSql.append(" (   SUBJ, YEAR, SUBJSEQ, USERID, SEQ, COMPNM, REMARKS             \n");
            insertSql.append("     , EMPLOYDATE, EMPLOYGUBUN, LUSERID, LDATE, DEPART, TYPE, POSITION                   \n");
            insertSql.append(" )                          									  \n");
            insertSql.append(" VALUES                                                          \n");
            insertSql.append(" (   ?, ?, ?, ? , ? , ?, ? ,?                                    \n");
            insertSql.append("     , ? , ? , to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?   \n");
            insertSql.append(" ) ");

            int index = 1;
            pstmt = connMgr.prepareStatement(insertSql.toString());
            pstmt.setString(index++, v_subj);
            pstmt.setString(index++, v_year);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, v_userid);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_compnm);
            pstmt.setString(index++, v_remarks);
            pstmt.setString(index++, v_employdate);
            pstmt.setString(index++, v_employgubun);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_depart);
            pstmt.setString(index++, v_type);
            pstmt.setString(index++, v_position);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, insertSql.toString());
            throw new Exception("sql ->" + insertSql.toString() + "\r\n" + ex.getMessage());
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

    /**
     * 취업 수정
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer updateSql = new StringBuffer();
        int isOk = 0;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");
        String v_compnm = box.getString("p_compnm");
        String v_remarks = box.getString("p_remarks");
        String v_employdate = box.getString("p_employdate");
        String v_employgubun = box.getString("p_employgubun");

        String v_type = box.getString("p_type");
        String v_depart = box.getString("p_depart");
        String v_position = box.getString("p_position");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            updateSql.append(" UPDATE TZ_OFFCARRIER                                          \n ");
            updateSql.append(" SET                                                           \n ");
            updateSql.append("         COMPNM          = ? , REMARKS     = ? , POSITION = ?, DEPART =?, TYPE = ?     \n ");
            updateSql.append("         , EMPLOYDATE  = ? , EMPLOYGUBUN  = ?                  \n ");
            updateSql.append("         , LUSERID     = ?                                     \n ");
            updateSql.append("         , LDATE       = to_char(sysdate, 'YYYYMMDDHH24MISS')  \n ");
            updateSql.append(" WHERE                                                         \n ");
            updateSql.append("         SUBJ        = ?                                       \n ");
            updateSql.append(" AND     YEAR        = ?                                       \n ");
            updateSql.append(" AND     SUBJSEQ     = ?                                       \n ");
            updateSql.append(" AND     USERID      = ?                                       \n ");
            updateSql.append(" AND     SEQ         = ?                                       \n ");

            pstmt = connMgr.prepareStatement(updateSql.toString());

            int index = 1;

            pstmt.setString(index++, v_compnm);
            pstmt.setString(index++, v_remarks);
            pstmt.setString(index++, v_position);
            pstmt.setString(index++, v_depart);
            pstmt.setString(index++, v_type);
            pstmt.setString(index++, v_employdate);
            pstmt.setString(index++, v_employgubun);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_subj);
            pstmt.setString(index++, v_year);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, v_userid);
            pstmt.setInt(index++, v_seq);

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
     * 취업 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 취업 상세보기
     * @throws Exception
     */
    public DataBox selectView(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT  A.SEQ, A.COMP, A.COMPNM,  A.EMPLOYGUBUN, D.NAME           \n ");
            sql.append("         , A.DEPART, A.TYPE, A.POSITION, A.REMARKS                    \n ");
            sql.append("         , C.CODENM EMPLOYGUBUN, A.EMPLOYDATE                      \n ");
            sql.append("         , A.SUBJ, A.YEAR, A.SUBJSEQ, A.USERID                     \n ");
            sql.append(" FROM    TZ_OFFCARRIER A,  TZ_CODE C, TZ_MEMBER D   \n ");
            //sql.append(" WHERE   A.COMP          = B.COMP(+)                               \n ");
            sql.append(" WHERE     A.EMPLOYGUBUN   = C.CODE(+)                               \n ");
            sql.append(" AND     C.GUBUN(+)      = '0099'                                  \n ");
            sql.append(" AND     A.USERID        = D.USERID                             \n ");
            sql.append(" AND     A.SUBJSEQ   = '").append(v_subjseq).append("'             \n ");
            sql.append(" AND     A.SUBJ      = '").append(v_subj).append("'                \n ");
            sql.append(" AND     A.YEAR      = '").append(v_year).append("'                \n ");
            sql.append(" AND     A.USERID    = '").append(v_userid).append("'              \n ");
            sql.append(" AND     A.SEQ       = ").append(v_seq);

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("Sql = " + sql + "\r\n" + ex.getMessage());
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

    /**
     * 취업관리 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int isOk = 0;
        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql.append(" DELETE FROM TZ_OFFCARRIER  \n ");
            sql.append(" AND     SUBJSEQ   = ?      \n ");
            sql.append(" AND     SUBJ      = ?      \n ");
            sql.append(" AND     YEAR      = ?      \n ");
            sql.append(" AND     USERID    = ?      \n ");
            sql.append(" AND     SEQ       = ?      \n ");
            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setString(1, v_subjseq);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_year);
            pstmt.setString(4, v_userid);
            pstmt.setInt(5, v_seq);
            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
            } else
                connMgr.rollback();
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
}
