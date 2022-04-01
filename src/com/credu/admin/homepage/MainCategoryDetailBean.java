/**
 * FileName : MainCategoryBean.java
 * Comment : Ȩ������ ����ȭ�� �α�/��õ �׸� ������ ����ϴ� bean class�̴�.
 * @version : 1.0
 * @author : kocca
 * @date : 2015. 1. 27.
 */
package com.credu.admin.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author kocca
 * 
 */
public class MainCategoryDetailBean {
    /**
     * Ȩ������ ����ȭ�� �α�/��õ �׸� ī�װ� ����� ��ȸ�Ѵ�.
     * 
     * @param box form ��ü���� �Ķ���� ���� ���� ������ ����� �ִ� ��ü
     * @return ArrayList ��ü ��� Ȥ�� �˻� ���ǿ� ���� ��ȸ�� ���
     * @throws Exception ����ó��
     */
    public ArrayList<DataBox> retrieveMainCategoryDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        int categorySeq = box.getInt("categorySeq");
        String searchItemNm = box.getString("searchItemNm");
        String searchUseYn = box.getStringDefault("searchUseYn", "A");

        int pageSize = box.getInt("pageSize"); // �� �������� ǥ�õǴ� ����� ����
        int pageNo = box.getInt("pageNo"); // ������ ������ ��ȣ
        int startNum = 0, endNum = 0; // ��ȸ�� �Խù��� rank ���� ��ȣ�� ���� ��ȣ
        int totalPage = 0; // ��ü ������ ��
        int totalRowCount = 0; // ��ü ��� ��
        int dispNum = 0; // ǥ�� ��ȣ

        pageSize = (pageSize == 0) ? 10 : pageSize;
        pageNo = (pageNo == 0) ? 1 : pageNo;
        startNum = (pageNo - 1) * pageSize + 1;
        endNum = startNum + pageSize - 1;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.admin.homepage.MainCategoryDetailBean (���� ��� ���� ��� ��ȸ) */\n");
            sql.append("SELECT  *                       \n");
            sql.append("  FROM  (                       \n");
            sql.append("        SELECT  SEQ             \n");
            sql.append("            ,   CATEGORY_SEQ    \n");
            sql.append("            ,   POSITION_NUM    \n");
            sql.append("            ,   ITEM_NM         \n");
            sql.append("            ,   ITEM_URL        \n");
            sql.append("            ,   THUMB_FILE_NM   \n");
            sql.append("            ,   USE_YN          \n");
            sql.append("            ,   COUNT(SEQ) OVER() AS TOT_CNT \n");
            sql.append("            ,   RANK() OVER(ORDER BY USE_YN DESC, POSITION_NUM DESC) AS RNK \n");
            sql.append("          FROM  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append("         WHERE  CATEGORY_SEQ = ").append(categorySeq).append("  \n");

            if (searchItemNm != null && !searchItemNm.equals("")) {
                sql.append("           AND  ITEM_NM LIKE '%").append(searchItemNm).append("%'   \n");
            }

            if (!searchUseYn.equals("A")) {
                sql.append("           AND  USE_YN = '").append(searchUseYn).append("'  \n");
            }

            sql.append("        )   \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                totalRowCount = ls.getInt("TOT_CNT");
                totalPage = (int) (totalRowCount / pageSize) + 1;
                ls.moveFirst();
            }
            dispNum = totalRowCount - ((pageNo - 1) * pageSize);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispNum", dispNum--);
                dbox.put("d_pageNo", pageNo);
                dbox.put("d_totalPage", totalPage);
                dbox.put("d_totalRowCount", totalRowCount);

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
     * Ȩ������ ����ȭ�� �α�/��õ �׸� ī�װ� ����� ��ȸ�Ѵ�.
     * 
     * @param box
     * @return
     */
    public int registerMainCategoryDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int resultCount = 0;
        int index = 1;
        int categorySeq = box.getInt("categorySeq");

        String regId = box.getSession("userid");

        String newFileName = box.getNewFileName("p_thumbFileNm");
        // String realFileName = box.getRealFileName("p_thumbFileNm");

        String useYn = box.getString("useYn");
        String layoutType = box.getString("layoutType");
        int maxRegCnt = layoutType.equals("A") ? 5 : 4;
        int useYnCnt = 0;

        try {
            connMgr = new DBConnectionManager();

            if (useYn.equals("Y")) {
                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.MainCategoryDetailBean registerMainCategoryDetail (��� ���� �Ǽ� ��ȸ) */\n");
                sql.append("SELECT  COUNT(CATEGORY_SEQ) AS CNT  \n");
                sql.append("  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
                sql.append(" WHERE  CATEGORY_SEQ = ").append(categorySeq).append("  \n");
                sql.append("   AND  USE_YN = 'Y' \n");

                ls = connMgr.executeQuery(sql.toString());

                if (ls.next()) {
                    useYnCnt = ls.getInt("cnt");
                }
                ls.close();
                ls = null;
            }

            if (useYnCnt < maxRegCnt) {
                connMgr.setAutoCommit(false);

                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.MainCategoryDetailBean registerMainCategoryDetail(�������� ���) */ \n");
                sql.append("INSERT  INTO    TZ_MAIN_CATEGORY_DETAIL (   \n");
                sql.append("        SEQ             \n");
                sql.append("    ,   CATEGORY_SEQ    \n");
                sql.append("    ,   POSITION_NUM    \n");
                sql.append("    ,   ITEM_NM         \n");
                sql.append("    ,   ITEM_URL        \n");
                sql.append("    ,   THUMB_FILE_NM   \n");
                sql.append("    ,   USE_YN          \n");
                sql.append("    ,   REG_DT          \n");
                sql.append("    ,   REG_ID          \n");
                sql.append("    ,   MOD_DT          \n");
                sql.append("    ,   MOD_ID          \n");
                sql.append(") VALUES (              \n");
                sql.append("        SEQ_MAIN_CATEGORY_DETAIL.NEXTVAL    /* SEQ */   \n");
                sql.append("    ,   ?   /* CATEGORY_SEQ */              \n");
                sql.append("    ,   ?   /* POSITION_NUM */              \n");
                sql.append("    ,   ?   /* ITEM_NM */                   \n");
                sql.append("    ,   ?   /* ITEM_URL */                  \n");
                sql.append("    ,   ?   /* THUMB_FILE_NM */             \n");
                sql.append("    ,   ?   /* USE_YN */                    \n");
                sql.append("    ,   SYSDATE /* REG_DT */                \n");
                sql.append("    ,   ?   /* REG_ID */                    \n");
                sql.append("    ,   SYSDATE /* MOD_DT */                \n");
                sql.append("    ,   ?   /* MOD_ID */                    \n");
                sql.append(")                                           \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setInt(index++, box.getInt("categorySeq"));
                pstmt.setInt(index++, box.getInt("positionNum"));
                pstmt.setString(index++, box.getString("itemNm"));
                pstmt.setString(index++, box.getString("itemUrl"));
                pstmt.setString(index++, newFileName);
                pstmt.setString(index++, box.getString("useYn"));
                pstmt.setString(index++, regId);
                pstmt.setString(index++, regId);

                resultCount = pstmt.executeUpdate();

                if (resultCount > 0) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                    FileManager.deleteFile(newFileName);
                }
            } else {
                resultCount = 99;
                FileManager.deleteFile(newFileName);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return resultCount;
    }

    /**
     * ���� ��� ������ �� ������ ��ȸ�Ѵ�.
     * 
     * @param box
     * @return
     */
    public DataBox retrieveMainCategoryDetailInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        int seq = box.getInt("seq");

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.admin.homepage.ManiCategoryDetailBean retrieveMainCategoryDetailInfo (���� ��ϰ��� �� ���� ��ȸ) */\n");
            sql.append("SELECT  SEQ \n");
            sql.append("    ,   CATEGORY_SEQ    \n");
            sql.append("    ,   POSITION_NUM    \n");
            sql.append("    ,   ITEM_NM         \n");
            sql.append("    ,   ITEM_URL        \n");
            sql.append("    ,   THUMB_FILE_NM   \n");
            sql.append("    ,   USE_YN          \n");
            sql.append("  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("  \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;

    }

    /**
     * ���� ��� ������ �����Ѵ�.
     * 
     * @param box
     * @return
     */
    public int deleteMainCategoryDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCount = 0, resultCountDetail = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.admin.homepage.ManiCategoryDetailBean deleteMainCategoryDetail (���� ��ϰ��� ����) */\n");
            sql.append("DELETE  \n");
            sql.append("  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append(" WHERE  SEQ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(1, box.getInt("seq"));

            resultCount = pstmt.executeUpdate();

            if (resultCount > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return resultCount + resultCountDetail;
    }

    /**
     * ���� ��� ���� �� ������ �����Ѵ�.
     * 
     * @param box
     * @return
     */
    public int updateMainCategoryDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int resultCount = 0;
        int index = 1;
        int categorySeq = box.getInt("categorySeq");
        String userid = box.getSession("userid");

        String useYn = box.getString("useYn");
        String layoutType = box.getString("layoutType");
        int maxRegCnt = layoutType.equals("A") ? 5 : 4;
        int useYnCnt = 0;

        String newFileName = box.getNewFileName("p_thumbFileNm");

        try {
            connMgr = new DBConnectionManager();

            if (useYn.equals("Y")) {
                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.MainCategoryDetailBean updateMainCategoryDetail (��� ���� �Ǽ� ��ȸ) */\n");
                sql.append("SELECT  COUNT(CATEGORY_SEQ) AS CNT  \n");
                sql.append("  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
                sql.append(" WHERE  CATEGORY_SEQ = ").append(categorySeq).append("  \n");
                sql.append("   AND  USE_YN = 'Y' \n");

                ls = connMgr.executeQuery(sql.toString());

                if (ls.next()) {
                    useYnCnt = ls.getInt("cnt");
                }
                ls.close();
                ls = null;
            }

            if (useYnCnt <= maxRegCnt) {

                connMgr.setAutoCommit(false);

                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.MainCategoryDetailBean updateMainCategoryDetail (������ϰ��� ����) */\n");
                sql.append("UPDATE  TZ_MAIN_CATEGORY_DETAIL    \n");
                sql.append("   SET  POSITION_NUM = ?    \n");
                sql.append("    ,   ITEM_NM = ?         \n");
                sql.append("    ,   ITEM_URL = ?        \n");

                if (newFileName != null && !newFileName.equals("")) {
                    sql.append("    ,   THUMB_FILE_NM = ?   \n");
                }
                sql.append("    ,   USE_YN = ?          \n");
                sql.append("    ,   MOD_DT = SYSDATE    \n");
                sql.append("    ,   MOD_ID = ?          \n");
                sql.append(" WHERE  SEQ = ?    \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setInt(index++, box.getInt("positionNum"));
                pstmt.setString(index++, box.getString("itemNm"));
                pstmt.setString(index++, box.getString("itemUrl"));
                if (newFileName != null && !newFileName.equals("")) {
                    pstmt.setString(index++, newFileName);
                }
                pstmt.setString(index++, box.getString("useYn"));
                pstmt.setString(index++, userid);
                pstmt.setInt(index++, box.getInt("seq"));

                resultCount = pstmt.executeUpdate();

                if (resultCount > 0) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            } else {
                resultCount = 99;
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
        return resultCount;
    }

    /**
     * ���԰��� / �������� ����� ��ȸ�Ѵ�.
     * 
     * @param box
     * @return
     */
    public ArrayList<DataBox> retriveSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        String categoryType = box.getString("categoryType");
        String searchSubjArea = box.getStringDefault("searchSubjArea", "");
        String searchSubjNm = box.getStringDefault("searchSubjNm", "");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            if (categoryType.equals("C_ONL")) {
                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.ManiCategoryBean retriveSubjList (���԰��� �����ȸ) */\n");
                sql.append("SELECT  GET_CODENM('0101', A.AREA) AS AREANM    \n");
                sql.append("    ,   A.SUBJNM                \n");
                sql.append("    ,   A.SUBJ                  \n");
                sql.append("  FROM  TZ_SUBJ A               \n");
                sql.append("    ,   TZ_GRSUBJ B             \n");
                sql.append(" WHERE  A.ISUSE = 'Y'           \n");
                sql.append("   AND  B.GRCODE = 'N000001'    \n");
                sql.append("   AND  A.SUBJ = B.SUBJCOURSE   \n");

                if (!searchSubjArea.equals("")) {
                    sql.append("   AND  A.AREA = '").append(searchSubjArea).append("'   \n");
                }

                if (!searchSubjNm.equals("")) {
                    sql.append("   AND  A.SUBJNM LIKE '%").append(searchSubjNm).append("%'  \n");
                }

                sql.append(" ORDER  BY AREA, SUBJNM         \n");
            } else if (categoryType.equals("C_OPN")) {
                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.ManiCategoryBean retriveSubjList (�������� �����ȸ) */\n");
                sql.append("SELECT  GET_CODENM('0118', A.LECTURE_CLS) AS AREANM \n");
                sql.append("    ,   A.LECNM AS SUBJNM           \n");
                sql.append("    ,   A.SEQ AS SUBJ               \n");
                sql.append("  FROM  TZ_GOLDCLASS A              \n");
                sql.append("    ,   TZ_GOLDCLASS_GRMNG B        \n");
                sql.append(" WHERE  A.USEYN = 'Y'               \n");
                sql.append("   AND  B.GRCODE = 'N000001'        \n");
                sql.append("   AND  A.SEQ = B.SEQ               \n");

                if (!searchSubjArea.equals("")) {
                    sql.append("   AND  A.LECTURE_CLS = '").append(searchSubjArea).append("'    \n");
                }

                if (!searchSubjNm.equals("")) {
                    sql.append("   AND  A.LECNM LIKE '%").append(searchSubjNm).append("%'   \n");
                }
                sql.append(" ORDER  BY A.LECTURE_CLS, A.LECNM   \n");
            }

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

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
     * ���� ī�װ� ���� ��� ȭ�鿡�� ��� ���θ� �ϰ� �����Ѵ�.
     * 
     * @param box
     * @return
     */
    public int updateUseYn(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCount[] = null;
        int index = 1;
        String userid = box.getSession("userid");
        String[] itemUseYnArr = box.getStringArray("itemUseYn");
        String[] temp = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.admin.homepage.ManiCategoryBean updateUseYn (��뿩�� �ϰ�����) */\n");
            sql.append("UPDATE  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append("   SET  USE_YN = ?          \n");
            sql.append("    ,   MOD_DT = SYSDATE    \n");
            sql.append("    ,   MOD_ID = ?          \n");
            sql.append(" WHERE  SEQ = ?             \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < itemUseYnArr.length; i++) {
                temp = itemUseYnArr[i].split("\\|");
                pstmt.setString(index++, temp[1]);
                pstmt.setString(index++, userid);
                pstmt.setInt(index++, Integer.parseInt(temp[0]));

                pstmt.addBatch();
                index = 1;
            }

            resultCount = pstmt.executeBatch();

            if (resultCount.length > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return resultCount.length;
    }

    /**
     * ���� ��� ���� �� �Խ��� ����(��ī���� �̾߱�/CMU)�� �ش��ϴ� �׸���� �Խ��� ���̺�� ������ ���� �ڷḦ ��ȸ�Ѵ�.
     * 
     * @param box
     * @return list - ArrayList<DataBox>
     * @throws Exception
     */
    public ArrayList<DataBox> retrieveMainCategoryDetailListFromBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        int categorySeq = box.getInt("categorySeq");
        String categoryType = box.getString("categoryType");

        String searchType = box.getString("searchType");
        String searchNm = box.getString("searchNm");
        String searchUseYn = box.getStringDefault("searchUseYn", "A");

        String tableName = (categoryType.equals("B_ACA")) ? "TZ_BOARD_TONG" : "TZ_BOARD_CMU";

        int pageSize = box.getInt("pageSize"); // �� �������� ǥ�õǴ� ����� ����
        int pageNo = box.getInt("pageNo"); // ������ ������ ��ȣ
        int startNum = 0, endNum = 0; // ��ȸ�� �Խù��� rank ���� ��ȣ�� ���� ��ȣ
        int totalPage = 0; // ��ü ������ ��
        int totalRowCount = 0; // ��ü ��� ��
        int dispNum = 0; // ǥ�� ��ȣ

        pageSize = (pageSize == 0) ? 10 : pageSize;
        pageNo = (pageNo == 0) ? 1 : pageNo;
        startNum = (pageNo - 1) * pageSize + 1;
        endNum = startNum + pageSize - 1;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.admin.homepage.MainCategoryDetailBean (���� ��� ���� ��� ��ȸ) */\n");
            sql.append("SELECT  *                                                   \n");
            sql.append("  FROM  (                                                   \n");
            sql.append("        SELECT  A.SEQ                                       \n");
            sql.append("            ,   A.TITLE                                     \n");
            sql.append("            ,   A.CONT                                      \n");
            sql.append("            ,   A.REG_ID                                    \n");
            sql.append("            ,   TO_CHAR(A.REG_DT, 'YYYY/MM/DD') AS REG_DT   \n");
            sql.append("            ,   A.USE_YN                                    \n");
            sql.append("            ,   DECODE(B.SEQ, NULL, 'N', 'Y') AS MAIN_YN    \n");
            sql.append("            ,   COUNT(A.SEQ) OVER() AS TOT_CNT              \n");
            sql.append("            ,   RANK() OVER (ORDER BY A.SEQ DESC) AS RNK    \n");
            sql.append("          FROM  ").append(tableName).append(" A             \n");
            sql.append("            ,   TZ_MAIN_CATEGORY_DETAIL B                   \n");
            sql.append("         WHERE  B.CATEGORY_SEQ(+) = ").append(categorySeq).append(" \n");
            sql.append("           AND  A.SEQ = B.BOARD_SEQ(+)                      \n");

            if (searchNm != null && !searchNm.equals("")) {
                if (searchType.equals("title")) {
                    sql.append("           AND  A.TITLE LIKE '%").append(searchNm).append("%'   \n");

                } else {
                    sql.append("           AND  A.CONT LIKE '%").append(searchNm).append("%'   \n");

                }
            }

            if (!searchUseYn.equals("A")) {
                sql.append("           AND  A.USE_YN = '").append(searchUseYn).append("'  \n");
            }

            sql.append("        )   \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY MAIN_YN DESC, RNK                                \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                totalRowCount = ls.getInt("TOT_CNT");
                totalPage = (int) (totalRowCount / pageSize) + 1;
                ls.moveFirst();
            }
            dispNum = totalRowCount - ((pageNo - 1) * pageSize);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispNum", dispNum--);
                dbox.put("d_pageNo", pageNo);
                dbox.put("d_totalPage", totalPage);
                dbox.put("d_totalRowCount", totalRowCount);

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
     * �Խù� ������ ��ȸ�Ѵ�.
     * 
     * @param box
     * @return
     */
    public DataBox retrieveBoardContents(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();

        int seq = box.getInt("seq");
        String categoryType = box.getString("categoryType");
        String tableName = (categoryType.equals("B_ACA")) ? "TZ_BOARD_TONG" : "TZ_BOARD_CMU";

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.admin.homepage.MainCategoryDetailBean retrieveBoardContents(�Խù� ���� ��ȸ) */\n");
            sql.append("SELECT  TITLE, CONT \n");
            sql.append("  FROM  ").append(tableName).append("   \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("   \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

    /**
     * �Խ��� ������ ī�װ��� ���� �׸� �� ���õ� �Խù����� ���α۷� �����Ѵ�.
     * 
     * @param box
     * @return
     */
    public int updateMainItem(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        StringBuffer sql = new StringBuffer();

        int resultCount[] = null;
        int index = 1;
        int maxLength = 0;
        int categorySeq = box.getInt("categorySeq");
        String userid = box.getSession("userid");
        String[] mainYnArr = box.getStringArray("mainYn");
        String seqList = "";

        String categoryType = box.getString("categoryType");
        String tableName = (categoryType.equals("B_ACA")) ? "TZ_BOARD_TONG" : "TZ_BOARD_CMU";
        String fileTableName = (categoryType.equals("B_ACA")) ? "TZ_BOARDFILE_TONG" : "TZ_BOARDFILE_CMU";
        String itemUrl = "";

        if (categoryType.equals("B_ACA")) {
            itemUrl = "/servlet/controller.homepage.TongBoardServlet?p_process=view&seq=";
        } else {
            itemUrl = "/servlet/controller.homepage.CMUBoardServlet?p_process=view&seq=";
        }

        try {

            for (int i = 0; i < mainYnArr.length; i++) {
                seqList += mainYnArr[i] + ", ";
            }

            seqList = seqList.substring(0, seqList.length() - 2);

            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();
            sql.setLength(0);
            sql.append("/* com.credu.admin.hompeage.MainCategoryDetailBean updateMainItem (�Խù� ��� ��ȸ) */");
            sql.append("SELECT  A.SEQ AS BOARD_SEQ      \n");
            sql.append("    ,   A.CONT AS BOARD_CONT    \n");
            sql.append("    ,   A.TITLE AS ITEM_NM  \n");
            sql.append("    ,   '").append(itemUrl).append("' || A.SEQ AS ITEM_URL  \n");
            sql.append("    ,   B.SAVE_IMG_NM AS THUMB_FILE_NM  \n");
            sql.append("    ,   RANK() OVER(ORDER BY A.SEQ DESC) AS POSITION_NUM    \n");
            sql.append("  FROM  ").append(tableName).append(" A         \n");
            sql.append("    ,   ").append(fileTableName).append(" B     \n");
            sql.append(" WHERE  A.SEQ IN ( ").append(seqList).append(" )    \n");
            sql.append("   AND  A.SEQ = B.SEQ(+)                        \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.admin.homepage.ManiCategoryBean updateUseYn (�ϰ� ���� ������ ���� �ڷ� ����) */\n");
            sql.append("DELETE  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append(" WHERE  CATEGORY_SEQ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(1, categorySeq);
            pstmt.executeUpdate();

            sql.setLength(0);
            sql.append("/* com.credu.admin.homepage.ManiCategoryBean updateUseYn (��뿩�� �ϰ�����) */\n");
            sql.append("INSERT  INTO TZ_MAIN_CATEGORY_DETAIL (  \n");
            sql.append("        SEQ                             \n");
            sql.append("    ,   CATEGORY_SEQ                    \n");
            sql.append("    ,   POSITION_NUM                    \n");
            sql.append("    ,   ITEM_NM                         \n");
            sql.append("    ,   ITEM_URL                        \n");
            sql.append("    ,   THUMB_FILE_NM                   \n");
            sql.append("    ,   USE_YN                          \n");
            sql.append("    ,   REG_DT                          \n");
            sql.append("    ,   REG_ID                          \n");
            sql.append("    ,   MOD_DT                          \n");
            sql.append("    ,   MOD_ID                          \n");
            sql.append("    ,   BOARD_SEQ                       \n");
            sql.append("    ,   BOARD_CONT                      \n");
            sql.append(") VALUES (                              \n");
            sql.append("        SEQ_MAIN_CATEGORY_DETAIL.NEXTVAL    /* SEQ */   \n");
            sql.append("    ,   ?   /* CATEGORY_SEQ */          \n");
            sql.append("    ,   ?   /* POSITION_NUM */          \n");
            sql.append("    ,   ?   /* ITEM_NM */               \n");
            sql.append("    ,   ?   /* ITEM_URL */              \n");
            sql.append("    ,   ?   /* THUMB_FILE_NM */         \n");
            sql.append("    ,   'Y' /* USE_YN */                \n");
            sql.append("    ,   SYSDATE /* REG_DT */            \n");
            sql.append("    ,   ?   /* REG_ID */                \n");
            sql.append("    ,   SYSDATE /* MOD_DT */            \n");
            sql.append("    ,   ?   /* MOD_ID */                \n");
            sql.append("    ,   ?   /* BOARD_SEQ */             \n");
            sql.append("    ,   ?   /* BOARD_CONT */            \n");
            sql.append(")                                       \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            
            String boardCont = "";

            for (int i = 0; i < list.size(); i++) {
                dbox = (DataBox) list.get(i);
                
                boardCont = StringManager.removeHTML( dbox.getString("d_board_cont") );
                System.out.println(boardCont);

                maxLength = boardCont.length() > 400 ? 400 : boardCont.length();

                pstmt.setInt(index++, categorySeq);
                pstmt.setInt(index++, dbox.getInt("d_position_num"));
                pstmt.setString(index++, dbox.getString("d_item_nm"));
                pstmt.setString(index++, dbox.getString("d_item_url"));
                pstmt.setString(index++, dbox.getString("d_thumb_file_nm"));
                pstmt.setString(index++, userid);
                pstmt.setString(index++, userid);
                pstmt.setInt(index++, dbox.getInt("d_board_seq"));
                pstmt.setString(index++, boardCont.substring(0, maxLength));

                pstmt.addBatch();
                index = 1;
            }

            resultCount = pstmt.executeBatch();

            if (resultCount.length > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
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
        return resultCount.length;
    }
}
