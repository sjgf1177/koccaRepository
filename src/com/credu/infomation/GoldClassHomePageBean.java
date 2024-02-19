//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.infomation;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
public class GoldClassHomePageBean {

    private ConfigSet config;
    private int row;

    public GoldClassHomePageBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * 열린강좌 리스트(랜덤)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 실무강좌 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String countSql = "";
        String sql = "";
        DataBox dbox = null;


        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");
        
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            
            headSql.append("SELECT A.SEQ, A.LECNM, A.LECTURE_CLS, A.VODIMG,								\n");
    		headSql.append("       ROW_NUMBER() OVER(ORDER BY ROUND(DBMS_RANDOM.VALUE(1, 100),0)) AS RN	\n");
    		
            bodySql.append("  FROM TZ_GOLDCLASS A														\n");
            bodySql.append(" INNER JOIN TZ_GOLDCLASS_GRMNG B											\n");
            bodySql.append("    ON A.SEQ = B.SEQ														\n");
            bodySql.append(" WHERE B.GRCODE = " + StringManager.makeSQL(tem_grcode) + "					\n");
            

            sql = headSql.toString() + bodySql.toString();

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
     * 열린강좌 누적 조회수를 갱신한다. TZ_GOLDCLASS 테이블에 트리거가 생성되어 있어, 해당 테이블의 누적 조회 자료를 갱신하면
     * TZ_VIEWCOUNT 테이블 자료도 같이 갱신된다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int updateOpenClassViewCount(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuilder sb = new StringBuilder();
        int seq = box.getInt("seq");
        int resultCnt = 0;
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sb.append(" UPDATE  TZ_GOLDCLASS           \n");
            sb.append("    SET  VIEWCNT = VIEWCNT + 1  \n");
            sb.append("  WHERE  SEQ = ").append(seq);

            pstmt = connMgr.prepareStatement(sb.toString());

            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb + "\r\n" + ex.getMessage());
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
                } catch (Exception e) {
                }
            }
        }

        return resultCnt;
    }

    /**
     * 이달의 골드 클래스 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 이달의 골드 클래스 리스트
     * @throws Exception
     */
    public ArrayList selectMainGoldClassList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        String p_genre = box.getString("p_genre");
        String p_lecture_cls = box.getString("p_lecture_cls");
        String grCode = box.getSession("tem_grcode");
        String p_searchtext = box.getString("p_searchtext");
        String p_cateCode = box.getString("cateCode");

        int pageSize = grCode.equals("N000001") ? 10 : 3;
        
        if(box.getSession("tem_type").equals("B"))
        	pageSize = 10;
        
        int totalRowCount = 0;
        int pageNo = Integer.parseInt(box.getStringDefault("pageNo", "1"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* GoldClassHomePageBean.selectMainGoldClassList (열린강좌 목록 구하기) */\n");
            sql.append("SELECT  A.SEQ                                                   \n");
            sql.append("    ,   A.LECNM                                                 \n");
            sql.append("    ,   (SELECT Z.CODENM                                        \n");
            sql.append("           FROM TZ_CODE Z                                       \n");
            sql.append("          WHERE Z.GUBUN  = '0121'                               \n");
            sql.append("            AND Z.LEVELS = 2                                    \n");
            sql.append("            AND Z.CODE   = (SELECT X.LVCODE                     \n");
            sql.append("                              FROM TZ_GOLDHOMEGUBUN_LEVEL X     \n");
            sql.append("                             WHERE X.SEQ = A.SEQ)) AS LVNM      \n");
            sql.append("    ,   NVL(V.MONTHLY_CNT, 0) AS MONTHLY_CNT                    \n");
            sql.append("    ,   A.VODIMG                                                \n");
            sql.append("    ,   A.TUTORNM                                               \n");
            sql.append("    ,   A.TUTORCAREER                                           \n");
            sql.append("    ,   A.TUTORAUTHOR                                           \n");
            sql.append("    ,   A.LECTIME                                               \n");
            sql.append("    ,   A.CREATOR                                               \n");
            sql.append("    ,   A.INTRO                                                 \n");
            sql.append("    ,   A.BANNERIMG                                             \n");
            sql.append("    ,   A.CONTENTS                                              \n");
            sql.append("    ,   A.WIDTH_S                                               \n");
            sql.append("    ,   A.HEIGHT_S                                              \n");
            sql.append("    ,   A.VIEWCNT                                               \n");
            sql.append("    ,   A.VODURL                                                \n");
            sql.append("    ,   A.GENRE                                                 \n");
            sql.append("    ,   A.TUTORIMG                                              \n");
            sql.append("    ,   A.LECTURE_TYPE                                          \n");
            sql.append("    ,   A.LECTURE_CLS                                           \n");
            sql.append("    ,   A.VOD_PATH                                              \n");
            sql.append("    ,   NVL(A.NEW_YN, 'N') AS NEW_YN                            \n");
            sql.append("    ,   NVL(A.HIT_YN, 'N') AS HIT_YN                            \n");
            sql.append("    ,   NVL(A.RECOM_YN, 'N') AS RECOM_YN                        \n");
            sql.append("    ,   COUNT(A.SEQ) OVER( PARTITION BY B.GRCODE ) AS TOT_CNT   \n");
            sql.append("	,   NVL((SELECT ROUND(SUM(CHECKPOIN) / COUNT(1), 0) FROM TZ_GOLDCLASSREPL WHERE SEQ = A.SEQ), 0) AS CHECKPOIN \n");
            sql.append("  FROM  TZ_GOLDCLASS A                                          \n");
            sql.append("    ,   TZ_GOLDCLASS_GRMNG B                                    \n");
            sql.append("    ,   TZ_GOLDHOMEGUBUN C                                      \n");
            sql.append("    ,   (                                                       \n");
            sql.append("        SELECT  SEQ                                             \n");
            sql.append("            ,   SUM(CNT) AS MONTHLY_CNT                                                             \n");
            sql.append("            ,   RANK() OVER( ORDER BY SUM(CNT) DESC, COUNT(SEQ) DESC, SEQ DESC) AS RNK              \n");
            sql.append("          FROM  TZ_VIEWCOUNT                                                                        \n");
            sql.append("         WHERE  VIEWDATE BETWEEN TO_CHAR(SYSDATE - 7, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD')  \n");
            // sql.append("         WHERE  VIEWDATE BETWEEN ADD_MONTHS(SYSDATE, -1) AND TO_CHAR(SYSDATE, 'YYYYMMDD')   \n");
            sql.append("         GROUP  BY SEQ                      \n");
            sql.append("        ) V                                 \n");
            sql.append(" WHERE  A.USEYN = 'Y'                       \n");
            sql.append("   AND  B.GRCODE = '").append(grCode).append("' \n");
            sql.append("   AND  A.SEQ = B.SEQ(+)                    \n");
            sql.append("   AND  A.SEQ = C.SEQ(+)                    \n");
            sql.append("   AND  C.GUBUN(+) = 'GC'                        \n");
            sql.append("   AND  A.SEQ = V.SEQ(+)                    \n");

            if (!p_genre.equals("")) {
                sql.append("   AND  A.GENRE = '").append(p_genre).append("'\n");
            }

/*            if (!p_lecture_cls.equals("") && !p_lecture_cls.equals("ALL")) {
                sql.append("   AND  A.LECTURE_CLS ='").append(p_lecture_cls).append("'\n");
            }*/

            if (!p_cateCode.equals("")) {
                sql.append("   AND  C.GUBUN_1 ='").append(p_cateCode).append("'\n");
            }
            
            if(!p_searchtext.equals("")){
            	sql.append("   AND  A.LECNM LIKE " + StringManager.makeSQL("%" + p_searchtext + "%") + " \n");
            }

            sql.append(" ORDER  BY V.RNK, A.LDATE DESC, A.SEQ DESC  \n");
            
            // 2014-12-10
            // 모바일 열린강좌 인기순과 동일한 순서로 조회( 현재일을 기준으로 1개월 이전의 누적 합계가 많은 자료부터 조회

            // 가장 최근에 등록한 강좌가 먼저 나오도록 되어 있으나
            // 열린강좌의 갱신 정보가 적다보니 가장 최근 수정 강좌부터 나오도록 변경함
            // sb.append("  ORDER  BY A.SEQ DESC   \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                totalRowCount = ls.getInt("TOT_CNT");
            }
            ls.moveFirst();
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(pageSize); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(pageNo, totalRowCount); // 현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();

                // dbox.put("d_checkpoin", new Double(ls.getDouble("checkpoin")));
                dbox.put("d_totalPage", new Integer(ls.getTotalPage()));
                // dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalRowCount", new Integer(totalRowCount));

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return list;
    }

    /**
     * 지난 골드클래스 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 전문가 관리 리스트
     * @throws Exception
     */
    public ArrayList selectPreGoldClassList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";
        String sql = "";
        DataBox dbox = null;

        String v_process = box.getString("p_process");

        String v_search = box.getString("p_search");
        String v_searchtext = box.getString("p_searchtext");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 6 : box.getInt("p_pagesize");

        if (v_process.equals("mainPage")) { // 골드클래스 메인 페이지에서는 지난강좌 3개만 출력
            v_pagesize = 3;
        } else if (v_process.equals("INFORMATION")) { // 정보광장 메인페이지 지난강좌 5개 출력
            v_pagesize = 5;
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            headSql.append(" SELECT  A.SEQ, A.VODIMG, A.LECNM, A.TUTORNM         \n ");
            headSql.append("         , A.LECTIME, A.CREATOR, A.INTRO, A.VODURL   \n ");
            headSql.append("         , A.WIDTH_S, A.HEIGHT_S, A.VIEWCNT          \n ");
            headSql.append("         , ROUND(B.CHECKPOIN, 1) CHECKPOIN           \n ");

            bodySql.append(" FROM    TZ_GOLDCLASS A                              \n ");
            bodySql.append("         , (                                         \n ");
            bodySql.append("             SELECT  SEQ, AVG(CHECKPOIN) CHECKPOIN   \n ");
            bodySql.append("             FROM    TZ_GOLDCLASSREPL                \n ");
            bodySql.append("             GROUP BY SEQ                            \n ");
            bodySql.append("         ) B                                         \n ");
            bodySql.append(" WHERE   A.SEQ       = B.SEQ(+)                      \n ");
            bodySql.append(" AND     A.OPENYN    = 'N'                           \n ");
            bodySql.append(" AND     A.USEYN     = 'Y'                           \n ");

            if (!v_searchtext.equals("")) { // 검색어가 있으면
                if (v_search.equals("lecnm")) { // 제목으로 검색할때
                    bodySql.append(" AND     A.LECNM LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("tutornm")) { // 강사명으로 검색할때
                    bodySql.append(" AND     A.TUTORNM LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                }
            }
            orderSql = " ORDER BY INDATE DESC ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = "SELECT COUNT(*) " + bodySql.toString();

            int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); // 현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_checkpoin", new Double(ls.getDouble("checkpoin")));
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
     * 강좌 의견 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int insertReply(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        StringBuffer insertSql = new StringBuffer();
        String sql = "";
        ResultSet rs = null;
        int isOk = 0;

        int v_num = 0;
        int v_seq = box.getInt("p_seq");
        int v_point = box.getInt("p_point");
        String v_comment = box.getString("p_comment");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            stmt = connMgr.createStatement();
            sql = "select count(*) cnt from TZ_GOLDCLASSREPL where seq = " + v_seq + " and regid = '" + s_userid + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                v_num = rs.getInt("cnt");
            }

            if (v_num > 0) {
                isOk = 2;
                return isOk;
            }

            stmt = connMgr.createStatement();
            sql = "select nvl(max(num), 0) + 1 num from TZ_GOLDCLASSREPL where seq = " + v_seq;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                v_num = rs.getInt("num");
            }

            connMgr.setAutoCommit(false);

            insertSql.append(" INSERT INTO TZ_GOLDCLASSREPL                   \n ");
            insertSql.append(" (                                              \n ");
            insertSql.append("     SEQ, NUM, CONT, CHECKPOIN, REGID, REGDT    \n ");
            insertSql.append(" )                                              \n ");
            insertSql.append(" VALUES                                         \n ");
            insertSql.append(" (                                              \n ");
            insertSql.append("     ?, ?, ?, ?, ?                              \n ");
            insertSql.append("     , to_char(sysdate, 'YYYYMMDDHH24MISS')     \n ");
            insertSql.append(" )                                              \n ");

            pstmt = connMgr.prepareStatement(insertSql.toString());

            int index = 1;

            pstmt.setInt(index++, v_seq);
            pstmt.setInt(index++, v_num);
            pstmt.setCharacterStream(index++, new StringReader(v_comment), v_comment.length());
            pstmt.setInt(index++, v_point);
            pstmt.setString(index++, s_userid);

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
            ErrorManager.getErrorStackTrace(ex, box, insertSql.toString());
            throw new Exception("sql = " + insertSql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e1) {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 강좌 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    public DataBox selectViewGoldClass(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_process");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            if (v_seq > 0) {
                sql.append(" SELECT  /* GoldClassHomePageBean.selectViewGoldClass(강좌 상세보기) */  \n");
                sql.append("         SEQ                                    \n");
                sql.append("     ,   LECNM                                  \n");
                sql.append("     ,   BANNERIMG                              \n");
                sql.append("     ,   TUTORNM                                \n");
                sql.append("     ,   TUTORCAREER                            \n");
                sql.append("     ,   LECTIME                                \n");
                sql.append("     ,   GENRE                                  \n");
                sql.append("     ,   CREATOR                                \n");
                sql.append("     ,   INTRO                                  \n");
                sql.append("     ,   CONTENTS                               \n");
                sql.append("     ,   TUTORAUTHOR                            \n");
                sql.append("     ,   HEIGHT_S                               \n");
                sql.append("     ,   WIDTH_S                                \n");
                sql.append("     ,   OPENYN                                 \n");
                sql.append("     ,   VODURL                                 \n");
                sql.append("     ,   VODIMG                                 \n");
                sql.append("     ,   VOD_PATH                               \n");
                sql.append("     ,   LECTURE_TYPE                           \n");
                sql.append("     ,   TUTORIMG                               \n");
                sql.append("     ,   CREATYEAR                              \n");
                sql.append("     ,   NVL(HIT_YN, 'N') AS HIT_YN             \n");
                sql.append("     ,   NVL(RECOM_YN, 'N') AS RECOM_YN         \n");
                sql.append("     ,   NVL(NEW_YN, 'N') AS NEW_YN             \n");
                sql.append("     ,   DECODE( NVL(B.SUBJ, ''), '', 'N', 'Y') AS FAVOR_YN       \n");
                sql.append("     ,   VTT_PATH                               \n");
                sql.append("   FROM  TZ_GOLDCLASS A                         \n");
                sql.append("     ,   (SELECT  SUBJ                          \n");
                sql.append("            FROM  TZ_SUBJ_FAVOR B               \n");
                sql.append("           WHERE  CLASS_TYPE = '02'             \n");
                sql.append("             AND  USERID =   '").append(userid).append("' )B      \n");
                sql.append("  WHERE  A.SEQ = ").append(v_seq).append("                      \n");
                sql.append("      AND  TO_CHAR(A.SEQ) = B.SUBJ(+)           \n");
            } else {
                sql.append(" SELECT  /* GoldClassHomePageBean.selectViewGoldClass(강좌 상세보기 - Random) */ \n");
                sql.append("         *                              \n");
                sql.append("   FROM  (                              \n");
                sql.append("         SELECT                         \n");
                sql.append("                 SEQ                    \n");
                sql.append("             ,   BANNERIMG              \n");
                sql.append("             ,   LECNM                  \n");
                sql.append("             ,   TUTORNM                \n");
                sql.append("             ,   TUTORCAREER            \n");
                sql.append("             ,   LECTIME                \n");
                sql.append("             ,   GENRE                  \n");
                sql.append("             ,   CREATOR                \n");
                sql.append("             ,   INTRO                  \n");
                sql.append("             ,   CONTENTS               \n");
                sql.append("             ,   TUTORAUTHOR            \n");
                sql.append("             ,   HEIGHT_S               \n");
                sql.append("             ,   WIDTH_S                \n");
                sql.append("             ,   OPENYN                 \n");
                sql.append("             ,   VODURL                 \n");
                sql.append("             ,   VODIMG                 \n");
                sql.append("             ,   VOD_PATH               \n");
                sql.append("             ,   LECTURE_TYPE           \n");
                sql.append("             ,   TUTORIMG               \n");
                sql.append("             ,   CREATYEAR              \n");
                sql.append("            ,   NVL(HIT_YN, 'N') AS HIT_YN         \n");
                sql.append("            ,   NVL(RECOM_YN, 'N') AS RECOM_YN     \n");
                sql.append("            ,   NVL(NEW_YN, 'N') AS NEW_YN         \n");
                sql.append("           FROM  TZ_GOLDCLASS           \n");
                sql.append("          ORDER  BY DBMS_RANDOM.VALUE   \n");
                sql.append("         )                              \n");
                sql.append("  WHERE  ROWNUM < 2                     \n");
            }

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
            }

            if (v_process.equals("popUpVod")) {
/*                sql.setLength(0);
                sql.append(" UPDATE  TZ_GOLDCLASS           \n");
                sql.append("    SET  VIEWCNT = VIEWCNT + 1  \n");
                sql.append("  WHERE  SEQ = ").append(v_seq);
                connMgr.executeUpdate(sql.toString());*/
            }

            if (v_seq > 0) {
                sql.setLength(0);
                sql.append(" UPDATE  TZ_GOLDCLASS           \n");
                sql.append("    SET  VIEWCNT = VIEWCNT + 1  \n");
                sql.append("  WHERE  SEQ = ").append(v_seq);
                connMgr.executeUpdate(sql.toString());
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
     * 열린강좌 상세 화면에서 연관강좌 목록을 조회한다. 연관강좌란 연관된 강좌로 tags 항목에 등록된 내용을 기반으로 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList selectRelatedLecutreList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        int seq = box.getInt("p_seq");

        String tags = "";
        String[] tagsArr = null;
        String grcode = box.getSession("grcode");
        grcode = grcode.equals("") ? "N000001" : grcode;

        int rnkLimit = 0;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  TAGS            \n");
            sql.append("  FROM  TZ_GOLDCLASS    \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("  \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                tags = ls.getString("tags");
            }

            ls.close();
            ls = null;

            if (tags != null && !tags.equals("")) {

                tagsArr = tags.split(",");
                rnkLimit = (tagsArr.length < 3) ? tagsArr.length - 1 : 2;

                sql.setLength(0);
                sql.append("/* GoldClassHomePageBean.selectRelatedLecutreList (열린강좌 연관 강좌 웹목록 조회) */ \n");
                sql.append("SELECT  *                   \n");
                sql.append("  FROM  (                   \n");
                sql.append("        SELECT  A.SEQ       \n");
                sql.append("            ,   A.LECNM     \n");
                sql.append("            ,   A.VODIMG    \n");
                sql.append("            ,   A.TAGS      \n");
                sql.append("            ,   A.TUTORNM   \n");
                sql.append("            ,   A.INTRO     \n");
                sql.append("            ,   A.CONTENTS  \n");
                sql.append("            ,   A.HIT_YN    \n");
                sql.append("            ,   A.NEW_YN    \n");
                sql.append("            ,   A.RECOM_YN  \n");

                for (int i = 0; i < tagsArr.length; i++) {
                    sql.append("            ,   CASE WHEN INSTR (A.TAGS, '").append(tagsArr[i]).append("') > 0 THEN 1 ELSE 0 END RNK").append(i).append(" \n");
                }
                sql.append("          FROM  TZ_GOLDCLASS A   \n");
                sql.append("            ,   TZ_GOLDCLASS_GRMNG B \n");
                sql.append("         WHERE  A.SEQ <> ").append(seq).append("  \n");
                sql.append("           AND  B.GRCODE = '").append(grcode).append("'     \n");
                sql.append("           AND  A.USEYN = 'Y'   \n");
                sql.append("           AND  A.SEQ = B.SEQ   \n");
                sql.append("           AND  (       \n");
                for (int i = 0; i < tagsArr.length; i++) {
                    if (i == 0) {
                        sql.append("                A.TAGS LIKE '%' || '").append(tagsArr[i]).append("' || '%' \n");
                    } else {
                        sql.append("                OR A.TAGS LIKE '%' || '").append(tagsArr[i]).append("' || '%' \n");
                    }
                }
                sql.append("                )   \n");
                sql.append("        )           \n");
                sql.append(" WHERE ( ");

                for (int i = 0; i < tagsArr.length; i++) {
                    if (i < tagsArr.length - 1) {
                        sql.append("RNK").append(i).append(" + ");
                    } else {
                        sql.append("RNK").append(i);
                    }
                }
                sql.append(" ) > ").append(rnkLimit).append(" \n");

                sql.append(" ORDER  BY  ");
                for (int i = 0; i < tagsArr.length; i++) {
                    if (i == 0) {
                        sql.append(" RNK").append(i);
                    } else {
                        sql.append(" || RNK").append(i);
                    }
                }
                sql.append(" DESC   \n");

                ls = connMgr.executeQuery(sql.toString());

                list = new ArrayList();
                while (ls.next()) {
                    dbox = ls.getDataBox();

                    list.add(dbox);
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return list;
    }

    /**
     * 강좌 의견 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 전문가 관리 리스트
     * @throws Exception
     */
    public ArrayList selectGoldClassReplyList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 5 : box.getInt("p_pagesize");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            headSql.append(" SELECT  A.SEQ, A.NUM, A.REGID, A.CONT               \n ");
            headSql.append("         , A.CHECKPOIN, A.REGDT, B.NAME              \n ");

            bodySql.append(" FROM    TZ_GOLDCLASSREPL A                          \n ");
            bodySql.append("         , TZ_MEMBER B                               \n ");
            bodySql.append(" WHERE   A.REGID     = B.USERID(+)                   \n ");
            bodySql.append(" AND     A.SEQ       = " + v_seq);
            bodySql.append(" AND     B.GRCODE       = " + StringManager.makeSQL(box.getSession("tem_grcode")));

            orderSql = " ORDER BY REGDT DESC ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = "SELECT COUNT(*) " + bodySql.toString();

            int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);
            ls.setPageSize(v_pagesize); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(v_pagesize));
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
     * 강좌 의견 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteReply(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_num = box.getInt("p_num");

        try {
            connMgr = new DBConnectionManager();
            sql = " delete from TZ_GOLDCLASSREPL  ";
            sql += "   where  num = ?  ";
            System.out.println(sql);
            System.out.println(v_num);
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_num);
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
     * 외부 링크를 타고 열린 강좌를 보려 할 경우에 필요한 기본 정보를 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox getOpenClassInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("seq");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT  /* GoldClassHomePageBean.getOpenClassInfo(강좌 상세보기) */  \n");
            sql.append("         SEQ            \n");
            sql.append("     ,   HEIGHT_S       \n");
            sql.append("     ,   WIDTH_S        \n");
            sql.append("     ,   LECTURE_TYPE   \n");
            sql.append("     ,   VODURL         \n");
            sql.append("     ,   VOD_PATH       \n");
            sql.append("   FROM  TZ_GOLDCLASS  \n");
            sql.append("  WHERE  SEQ = ").append(v_seq);

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

    public DataBox selecOpenClassCntInfo(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sb = new StringBuilder();
        DataBox dbox = null;

        String grCode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sb.append(" /* GoldClassHomePageBean. selecOpenClassCntInfo (열린강좌 분류별 카운트) */ \n");
            sb.append(" SELECT                                                                      \n");
            sb.append("         MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'ALL', CLS_CNT) ) AS ALL_CNT   \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC01', CLS_CNT) ) AS GC01_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC02', CLS_CNT) ) AS GC02_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC03', CLS_CNT) ) AS GC03_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC04', CLS_CNT) ) AS GC04_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC05', CLS_CNT) ) AS GC05_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC06', CLS_CNT) ) AS GC06_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC07', CLS_CNT) ) AS GC07_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_CLS, 'ALL'), 'GC08', CLS_CNT) ) AS GC08_CNT \n");
            sb.append("   FROM  (                                       \n");
            sb.append("         SELECT  A.LECTURE_CLS                   \n");
            sb.append("             ,   COUNT( A.SEQ ) AS CLS_CNT       \n");
            sb.append("           FROM  TZ_GOLDCLASS A                  \n");
            sb.append("             ,   TZ_GOLDCLASS_GRMNG B            \n");
            sb.append("          WHERE  A.USEYN = 'Y'                   \n");
            sb.append("            AND  B.GRCODE = '").append(grCode).append("'   \n");
            sb.append("            AND  A.SEQ = B.SEQ                   \n");
            sb.append("          GROUP BY ROLLUP(LECTURE_CLS)           \n");
            sb.append(" )                                               \n");

            ls = connMgr.executeQuery(sb.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
     * 열린강좌 후기 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectReviewList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        String seq = box.getString("p_seq");
        String userid = box.getSession("userid");

        int pageSize = box.getInt("p_pagesize"); // 한 페이지에 표시되는 목록의 개수
        int pageNo = box.getInt("p_pageno"); // 현재의 페이지 번호
        int startNum = 0, endNum = 0; // 조회할 게시물의 rank 시작 번호와 종료 번호
        int totalPage = 0; // 전체 페이지 수
        int totalRowCount = 0; // 전체 목록 수
        int dispNum = 0; // 표시 번호

        int blockSize = 10; // 한 블록에 표시되는 페이지 수. 한 블록당 10개의 페이지 수 표시
        int blockCount = 0; // 페이지블록 개수
        int pageBlock = 0; // 현재 페이지의 블럭 번호
        int startPage = 0; // 블럭내 시작 페이지
        int endPage = 0; // 블럭내 종료 페이지

        pageSize = (pageSize == 0) ? 5 : pageSize;
        pageNo = (pageNo == 0) ? 1 : pageNo;
        startNum = (pageNo - 1) * pageSize + 1;
        endNum = startNum + pageSize - 1;

        try {
            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.infomation.GoldClassHomePageBean selectReviewList (열린강좌후기 목록 조회) */\n");
            sql.append("SELECT  *   \n");
            sql.append("  FROM  (   \n");
            sql.append("        SELECT  A.SEQ       \n");
            sql.append("            ,   A.NUM       \n");
            sql.append("            ,   A.CONT      \n");
            sql.append("            ,   A.CHECKPOIN     \n");
            sql.append("            ,   A.REGID     \n");
            sql.append("            ,   B.NAME      \n");
            sql.append("            ,   TO_CHAR( TO_DATE(A.REGDT, 'YYYYMMDDHH24MISS'), 'YYYY/MM/DD') AS REGDT   \n");
            sql.append("            ,   RANK() OVER(ORDER BY DECODE(A.REGID, '").append(userid).append("', 1, 0) DESC, NUM DESC) AS RNK  \n");
            sql.append("            ,   COUNT(NUM) OVER() AS TOT_CNT                \n");
            sql.append("          FROM  TZ_GOLDCLASSREPL A                          \n");
            sql.append("            ,   TZ_MEMBER B                                 \n");
            sql.append("         WHERE  A.SEQ = '").append(seq).append("'           \n");
            sql.append("           AND  A.REGID = B.USERID                          \n");
            sql.append("        )   \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY RNK, NUM DESC \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                totalRowCount = ls.getInt("tot_cnt");
                ls.moveFirst();
            }

            totalPage = (int) (totalRowCount / pageSize);
            totalPage = (totalRowCount % pageSize == 0) ? totalPage : totalPage + 1;

            blockCount = (int) (totalPage / blockSize);
            blockCount = (totalPage % blockSize == 0) ? blockCount : blockCount + 1;

            pageBlock = (int) ((pageNo - 1) / blockSize) + 1;
            // pageBlock = (pageNo % blockSize == 0) ? pageBlock : pageBlock +
            // 1;
            startPage = (int) (pageBlock - 1) * blockSize + 1;
            endPage = (int) pageBlock * blockSize;
            endPage = (endPage > totalPage) ? totalPage : endPage;

            dispNum = totalRowCount - ((pageNo - 1) * pageSize);

            System.out.println("totalRowCount : " + totalRowCount);
            System.out.println("pageNo : " + pageNo);
            System.out.println("pageSize : " + pageSize);
            System.out.println("totalPage : " + totalPage);
            System.out.println("blockCount : " + blockCount);
            System.out.println("pageBlock : " + pageBlock);
            System.out.println("startPage : " + startPage);
            System.out.println("endPage : " + endPage);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispNum", dispNum--);
                dbox.put("d_pageNo", pageNo);
                dbox.put("d_totalPage", totalPage);
                dbox.put("d_rowCount", row);
                dbox.put("d_totalRowCount", totalRowCount);
                dbox.put("d_blockSize", blockSize);
                dbox.put("d_blockCount", blockCount);
                dbox.put("d_pageBlock", pageBlock);
                dbox.put("d_startPage", startPage);
                dbox.put("d_endPage", endPage);

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
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
        return list;
    }
    
    /**
     * 이달의 골드 클래스 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 이달의 골드 클래스 리스트
     * @throws Exception
     */
    public ArrayList selectMainGoldClassThemeList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        String p_genre = box.getString("p_genre");
        String p_lecture_cls = box.getString("p_lecture_cls");
        String grCode = box.getSession("tem_grcode");

        int pageSize = grCode.equals("N000001") ? 10 : 3;
        int totalRowCount = 0;
        int pageNo = Integer.parseInt(box.getStringDefault("pageNo", "1"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* GoldClassHomePageBean.selectMainGoldClassList (열린강좌 목록 구하기) */\n");
            sql.append("SELECT  A.SEQ       \n");
            sql.append("    ,   A.LECNM     \n");
            sql.append("    ,   NVL(V.MONTHLY_CNT, 0) AS MONTHLY_CNT    \n");
            sql.append("    ,   A.VODIMG        \n");
            sql.append("    ,   A.TUTORNM       \n");
            sql.append("    ,   A.TUTORCAREER   \n");
            sql.append("    ,   A.TUTORAUTHOR   \n");
            sql.append("    ,   A.LECTIME       \n");
            sql.append("    ,   A.CREATOR       \n");
            sql.append("    ,   A.INTRO         \n");
            sql.append("    ,   A.BANNERIMG     \n");
            sql.append("    ,   A.CONTENTS      \n");
            sql.append("    ,   A.WIDTH_S       \n");
            sql.append("    ,   A.HEIGHT_S      \n");
            sql.append("    ,   A.VIEWCNT       \n");
            sql.append("    ,   A.VODURL        \n");
            sql.append("    ,   A.GENRE         \n");
            sql.append("    ,   A.TUTORIMG      \n");
            sql.append("    ,   A.LECTURE_TYPE  \n");
            sql.append("    ,   A.LECTURE_CLS   \n");
            sql.append("    ,   A.VOD_PATH      \n");
            sql.append("    ,   NVL(A.NEW_YN, 'N') AS NEW_YN        \n");
            sql.append("    ,   NVL(A.HIT_YN, 'N') AS HIT_YN        \n");
            sql.append("    ,   NVL(A.RECOM_YN, 'N') AS RECOM_YN    \n");
            sql.append("    ,   COUNT(A.SEQ) OVER( PARTITION BY B.GRCODE ) AS TOT_CNT   \n");
            sql.append("  FROM  TZ_GOLDCLASS A          \n");
            sql.append("    ,   TZ_GOLDCLASS_GRMNG B    \n");
            sql.append("    ,   (                       \n");
            sql.append("        SELECT  SEQ             \n");
            sql.append("            ,   SUM(CNT) AS MONTHLY_CNT                                                     \n");
            sql.append("            ,   RANK() OVER( ORDER BY SUM(CNT) DESC, COUNT(SEQ) DESC, SEQ DESC) AS RNK      \n");
            sql.append("          FROM  TZ_VIEWCOUNT                                                                \n");
            sql.append("         WHERE  VIEWDATE BETWEEN TO_CHAR(SYSDATE - 7, 'YYYYMMDD') AND TO_CHAR(SYSDATE, 'YYYYMMDD')  \n");
            // sql.append("         WHERE  VIEWDATE BETWEEN ADD_MONTHS(SYSDATE, -1) AND TO_CHAR(SYSDATE, 'YYYYMMDD')   \n");
            sql.append("         GROUP  BY SEQ                      \n");
            sql.append("        ) V                                 \n");
            sql.append(" WHERE  A.USEYN = 'Y'                       \n");
            sql.append("   AND  B.GRCODE = '").append(grCode).append("' \n");
            sql.append("   AND  A.SEQ = B.SEQ(+)                    \n");
            sql.append("   AND  A.SEQ = V.SEQ(+)                    \n");

            if (!p_genre.equals("")) {
                sql.append("   AND  A.GENRE = '").append(p_genre).append("'\n");
            }

            if (!p_lecture_cls.equals("") && !p_lecture_cls.equals("ALL")) {
                sql.append("   AND  LECTURE_THEME ='").append(p_lecture_cls).append("'\n");
            }

            sql.append(" ORDER  BY V.RNK, A.LDATE DESC, A.SEQ DESC  \n");
            // 2014-12-10
            // 모바일 열린강좌 인기순과 동일한 순서로 조회( 현재일을 기준으로 1개월 이전의 누적 합계가 많은 자료부터 조회

            // 가장 최근에 등록한 강좌가 먼저 나오도록 되어 있으나
            // 열린강좌의 갱신 정보가 적다보니 가장 최근 수정 강좌부터 나오도록 변경함
            // sb.append("  ORDER  BY A.SEQ DESC   \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                totalRowCount = ls.getInt("TOT_CNT");
            }
            ls.moveFirst();
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(pageSize); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(pageNo, totalRowCount); // 현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();

                // dbox.put("d_checkpoin", new Double(ls.getDouble("checkpoin")));
                dbox.put("d_totalPage", new Integer(ls.getTotalPage()));
                // dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalRowCount", new Integer(totalRowCount));

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return list;
    }    
    
    public DataBox selecOpenClassThemeCntInfo(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sb = new StringBuilder();
        DataBox dbox = null;

        String grCode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sb.append(" /* GoldClassHomePageBean. selecOpenClassCntInfo (열린강좌 분류별 카운트) */ \n");
            sb.append(" SELECT                                                                      \n");
            sb.append("         MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'ALL', CLS_CNT) ) AS ALL_CNT   \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT01', CLS_CNT) ) AS OT01_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT02', CLS_CNT) ) AS OT02_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT03', CLS_CNT) ) AS OT03_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT04', CLS_CNT) ) AS OT04_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT05', CLS_CNT) ) AS OT05_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT06', CLS_CNT) ) AS OT06_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT07', CLS_CNT) ) AS OT07_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT08', CLS_CNT) ) AS OT08_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT09', CLS_CNT) ) AS OT09_CNT \n");
            sb.append("     ,   MAX(DECODE( NVL(LECTURE_THEME, 'ALL'), 'OT10', CLS_CNT) ) AS OT10_CNT \n");
            sb.append("   FROM  (                                       \n");
            sb.append("         SELECT  A.LECTURE_THEME                   \n");
            sb.append("             ,   COUNT( A.SEQ ) AS CLS_CNT       \n");
            sb.append("           FROM  TZ_GOLDCLASS A                  \n");
            sb.append("             ,   TZ_GOLDCLASS_GRMNG B            \n");
            sb.append("          WHERE  A.USEYN = 'Y'                   \n");
            sb.append("            AND  B.GRCODE = '").append(grCode).append("'   \n");
            sb.append("            AND  A.SEQ = B.SEQ                   \n");
            sb.append("          GROUP BY ROLLUP(LECTURE_THEME)           \n");
            sb.append(" )                                               \n");

            ls = connMgr.executeQuery(sb.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
}
