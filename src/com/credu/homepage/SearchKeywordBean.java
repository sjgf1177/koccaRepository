package com.credu.homepage;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.scorm.DateUtil;

public class SearchKeywordBean {

    /**
     * 사용자 검색어 로그 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> searchKeywordList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuilder sb = new StringBuilder();

        String searchStartDate = box.getString("searchStartDate");
        String searchEndDate = box.getString("searchEndDate");

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(dt);

        if (searchEndDate == null || searchEndDate.equals("")) {
            searchEndDate = today;
        } else {
            searchEndDate = searchEndDate.replaceAll("/", "");
        }

        if (searchStartDate == null || searchStartDate.equals("")) {
            searchStartDate = DateUtil.addDate(today, -7, "yyyyMMdd", "D");
        } else {
            searchStartDate = searchStartDate.replaceAll("/", "");
        }

        box.put("searchStartDate", searchStartDate);
        box.put("searchEndDate", searchEndDate);

        int totalRowCnt = 0;
        int totalPage = 0;
        int pageSize = box.getInt("pageSize") == 0 ? 10 : box.getInt("pageSize");
        int pageNo = box.getInt("pageNo") == 0 ? 1 : box.getInt("pageNo");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sb.append("SELECT  /* 인기 검색어 순위 조회 */                                              \n");
            sb.append("        RNK                                                                      \n");
            sb.append("    ,   SEARCH_WORD                                                              \n");
            sb.append("    ,   CNT                                                                      \n");
            sb.append("    ,   TOTAL_CNT                                                                \n");
            sb.append("  FROM  (                                                                        \n");
            sb.append("        SELECT  RANK() OVER( ORDER BY COUNT(SEARCH_WORD) DESC) AS RNK            \n");
            sb.append("            ,   SEARCH_WORD                                                      \n");
            sb.append("            ,   COUNT(SEARCH_WORD) AS CNT                                        \n");
            sb.append("            ,   COUNT(SEARCH_WORD) OVER() AS TOTAL_CNT                           \n");
            sb.append("          FROM  TZ_LOG_SEARCH_WORD                                               \n");
            sb.append("         WHERE  TO_CHAR(SEARCH_DT, 'YYYYMMDD') BETWEEN '").append(searchStartDate).append("' \n");
            sb.append("                                                   AND '").append(searchEndDate).append("'   \n");
            sb.append("         GROUP  BY SEARCH_WORD                                                   \n");
            sb.append("        )                                                                        \n");

            ls = connMgr.executeQuery(sb.toString());

            if (ls.next()) {
                totalRowCnt = ls.getInt("TOTAL_CNT");
                ls.moveFirst();
            }

            ls.setPageSize(pageSize); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(pageNo, totalRowCnt); // 현재페이지번호를 세팅한다.
            totalPage = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(totalRowCnt - ls.getRowNum() + 1));
                dbox.put("totalPage", new Integer(totalPage));
                dbox.put("pageSize", new Integer(pageSize));
                dbox.put("totalRowCnt", new Integer(totalRowCnt));
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("DB Query = " + sb.toString() + "\r\n" + ex.getMessage());
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
     * 설정한 키워드 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectKeywordList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuilder sb = new StringBuilder();

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sb.append("SELECT  /* 노출될 키워드 조회 */                                 \n");
            sb.append("        SORT_SEQ                                             \n");
            sb.append("    ,   KEYWORD                                              \n");
            sb.append("    ,   REG_ID                                               \n");
            sb.append("    ,   TO_CHAR(REG_DT, 'YYYY/MM/DD HH24:MI:SS') AS REG_DT   \n");
            sb.append("  FROM  TZ_KEYWORDMANAGER                                    \n");

            ls = connMgr.executeQuery(sb.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("DB Query = " + sb.toString() + "\r\n" + ex.getMessage());
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
     * 오픈강과 인기과정 목록을 수정한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int insertKeyWord(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();
        String keyword[] = box.getStringArray("s_keyword");
        String s_userid = box.getSession("userid");
        int resultCnt[] = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql.append("/* com.credu.homepage.SearchKeywordBean() insertKeyWord(키워드 등록) */ \n");
            sql.append(" DELETE FROM TZ_KEYWORDMANAGER     \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.executeUpdate();

            pstmt.close();
            pstmt = null;
            int index = 0;
            int j = 1;

            sql.setLength(0);
            sql.append(" INSERT  INTO  TZ_KEYWORDMANAGER \n");
            sql.append("      (                          \n");
            sql.append("         SORT_SEQ                \n");
            sql.append("      ,  KEYWORD                 \n");
            sql.append("      ,  REG_ID                  \n");
            sql.append("      ,  REG_DT                  \n");
            sql.append("       )                         \n");
            sql.append(" VALUES                          \n");
            sql.append("      (                          \n");
            sql.append("         ?                       \n");
            sql.append("      ,  ?                       \n");
            sql.append("      ,  ?                       \n");
            sql.append("      ,  SYSDATE                 \n");
            sql.append("      )                          \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < keyword.length; i++) {
                index = 1;
                pstmt.setInt(index++, j++);
                pstmt.setString(index++, keyword[i]);
                pstmt.setString(index++, s_userid);

                pstmt.addBatch();

            }
            resultCnt = pstmt.executeBatch();

            if (resultCnt.length > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return resultCnt.length;
    }

    @SuppressWarnings("unchecked")
    public ArrayList searchKeywrodDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;

        StringBuilder sb = new StringBuilder();
        String searchStartDate = box.getString("searchStartDate");
        String searchEndDate = box.getString("searchEndDate");
        String searchKeyword = box.getString("searchKeyword");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sb.append(" SELECT                                      \n");
            sb.append("         TO_CHAR(SEARCH_DT, 'YYYY-MM-DD')    \n");
            sb.append("     ,   COUNT(SEARCH_WORD) AS CNT           \n");
            sb.append("   FROM  TZ_LOG_SEARCH_WORD                  \n");
            sb.append("  WHERE  TO_CHAR(SEARCH_DT, 'YYYYMMDD') BETWEEN '").append(searchStartDate).append("' AND '").append(searchEndDate).append("' \n");
            sb.append("    AND  SEARCH_WORD = '").append(searchKeyword).append("'   \n");
            sb.append("  GROUP  BY TO_CHAR(SEARCH_DT, 'YYYY-MM-DD') ORDER BY TO_CHAR(SEARCH_DT, 'YYYY-MM-DD') DESC  \n");

            ls = connMgr.executeQuery(sb.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("DB Query = " + sb.toString() + "\r\n" + ex.getMessage());
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

}
