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
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * @author kocca
 * 
 */
public class MainCategoryBean {
    /**
     * Ȩ������ ����ȭ�� �α�/��õ �׸� ī�װ� ����� ��ȸ�Ѵ�.
     * 
     * @param box form ��ü���� �Ķ���� ���� ���� ������ ����� �ִ� ��ü
     * @return ArrayList ��ü ��� Ȥ�� �˻� ���ǿ� ���� ��ȸ�� ���
     * @throws Exception ����ó��
     */
    public ArrayList<DataBox> retrieveMainCategoyList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String searchCategoryNm = box.getString("searchCategoryNm");
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

            sql.append("/* com.credu.admin.homepage.MainCategoryBean retrieveMainCategoyList (����ī�װ� ��� ��ȸ) */\n");
            sql.append("SELECT  *   \n");
            sql.append("  FROM  (   \n");
            sql.append("        SELECT  A.CATEGORY_SEQ  \n");
            sql.append("            ,   A.CATEGORY_NM   \n");
            sql.append("            ,   A.CATEGORY_TYPE \n");
            sql.append("            ,   GET_CODENM('0120', A.CATEGORY_TYPE) AS CATEGORY_TYPE_NM \n");
            sql.append("            ,   A.LAYOUT_TYPE   \n");
            sql.append("            ,   A.SORT_ORDER    \n");
            sql.append("            ,   A.USE_YN        \n");
            sql.append("            ,   A.MAX_ITEM_CNT  \n");
            sql.append("            ,   (   \n");
            sql.append("                SELECT  COUNT(CATEGORY_SEQ) \n");
            sql.append("                  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append("                 WHERE  CATEGORY_SEQ = A.CATEGORY_SEQ   \n");
            sql.append("                   AND  USE_YN = 'Y' \n");
            sql.append("                ) AS REG_ITEM_CNT   \n");
            sql.append("            ,   COUNT(A.CATEGORY_SEQ) OVER() AS TOT_CNT \n");
            sql.append("            ,   RANK() OVER(ORDER BY A.USE_YN DESC, A.SORT_ORDER) AS RNK    \n");
            sql.append("          FROM  TZ_MAIN_CATEGORY A  \n");
            sql.append("         WHERE  1 = 1   \n");
            if (searchCategoryNm != null && !searchCategoryNm.equals("")) {
                sql.append("           AND  A.CATEGORY_NM LIKE '%").append(searchCategoryNm).append("%'   \n");
            }

            if (!searchUseYn.equals("A")) {
                sql.append("           AND  A.USE_YN = '").append(searchUseYn).append("'  \n");
            }
            sql.append("        )   \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY RNK  \n");

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
    public int registerMainCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int resultCount = 0;
        int index = 1;
        int useYnCnt = 0;

        String regId = box.getSession("userid");
        String layoutType = box.getString("layoutType");
        String useYn = box.getString("useYn");
        int maxRegCnt = layoutType.equals("A") ? 5 : 4;

        try {
            connMgr = new DBConnectionManager();

            if (useYn.equals("Y")) {
                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.ManiCategoryBean registerMainCategory (��� ���� ���� Ȯ��) */\n");
                sql.append("SELECT  COUNT(CATEGORY_SEQ) AS CNT  \n");
                sql.append("  FROM  TZ_MAIN_CATEGORY            \n");
                sql.append(" WHERE  USE_YN = 'Y' \n");

                ls = connMgr.executeQuery(sql.toString());

                if (ls.next()) {
                    useYnCnt = ls.getInt("cnt");
                }
                ls.close();
                ls = null;
            }

            if (useYnCnt < 6) {
                connMgr.setAutoCommit(false);

                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.ManiCategoryBean registerMainCategory (����ī�װ� �׸� ���) */\n");
                sql.append("INSERT  INTO    TZ_MAIN_CATEGORY (  \n");
                sql.append("        CATEGORY_SEQ                \n");
                sql.append("    ,   CATEGORY_NM                 \n");
                sql.append("    ,   CATEGORY_TYPE               \n");
                sql.append("    ,   LAYOUT_TYPE                 \n");
                sql.append("    ,   SORT_ORDER                  \n");
                sql.append("    ,   USE_YN                      \n");
                sql.append("    ,   MAX_ITEM_CNT                \n");
                sql.append("    ,   REG_DT                      \n");
                sql.append("    ,   REG_ID                      \n");
                sql.append("    ,   MOD_DT                      \n");
                sql.append("    ,   MOD_ID                      \n");
                sql.append(") VALUES (                          \n");
                sql.append("        (SELECT  NVL( MAX (CATEGORY_SEQ) , 1) + 1 FROM TZ_MAIN_CATEGORY) /* CATEGORY_SEQ */  \n");
                sql.append("    ,   ?   /* CATEGORY_NM */       \n");
                sql.append("    ,   ?   /* CATEGORY_TYPE */     \n");
                sql.append("    ,   ?   /* LAYOUT_TYPE */       \n");
                sql.append("    ,   ?   /* SORT_ORDER */        \n");
                sql.append("    ,   ?   /* USE_YN */            \n");
                sql.append("    ,   ?   /* MAX_ITEM_CNT */      \n");
                sql.append("    ,   SYSDATE /* REG_DT */        \n");
                sql.append("    ,   ?   /* REG_ID */            \n");
                sql.append("    ,   SYSDATE /* MOD_DT */        \n");
                sql.append("    ,   ?   /* MOD_ID */            \n");
                sql.append(")                                   \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(index++, box.getString("categoryNm"));
                pstmt.setString(index++, box.getString("categoryType"));
                pstmt.setString(index++, layoutType);
                pstmt.setString(index++, box.getString("sortOrder"));
                pstmt.setString(index++, box.getString("useYn"));
                pstmt.setInt(index++, maxRegCnt);
                pstmt.setString(index++, regId);
                pstmt.setString(index++, regId);

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
     * Ȩ������ ����ȭ�� �α�/��õ �׸� ī�װ� ������ ��ȸ�Ѵ�.
     * 
     * @param box
     * @return
     */
    public DataBox retrieveMainCategoryInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        int categorySeq = box.getInt("categorySeq");

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.admin.homepage.ManiCategoryBean retrieveMainCategoryInfo (����ȭ�� ī�װ� �� ���� ��ȸ) */\n");
            sql.append("SELECT  A.CATEGORY_SEQ  \n");
            sql.append("    ,   A.CATEGORY_NM   \n");
            sql.append("    ,   A.CATEGORY_TYPE \n");
            sql.append("    ,   GET_CODENM('0120', A.CATEGORY_TYPE) AS CATEGORY_TYPE_NM \n");
            sql.append("    ,   A.LAYOUT_TYPE   \n");
            sql.append("    ,   A.SORT_ORDER    \n");
            sql.append("    ,   A.USE_YN        \n");
            sql.append("    ,   A.MAX_ITEM_CNT  \n");
            sql.append("    ,   (   \n");
            sql.append("        SELECT  COUNT(CATEGORY_SEQ) \n");
            sql.append("          FROM  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append("         WHERE  CATEGORY_SEQ = A.CATEGORY_SEQ   \n");
            sql.append("        ) AS REG_ITEM_CNT   \n");
            sql.append("  FROM  TZ_MAIN_CATEGORY A \n");
            sql.append(" WHERE  A.CATEGORY_SEQ = ").append(categorySeq).append("  \n");

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
     * Ȩ������ ����ȭ�� �α�/��õ �׸� ī�װ��� ��ϵ� ���� ����� ��ȸ�Ѵ�.
     * 
     * @return
     */
    public ArrayList<DataBox> retrieveMainCategoryDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        int categorySeq = box.getInt("categorySeq");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.admin.homepage.ManiCategoryBean retrieveMainCategoryInfo (����ȭ�� ī�װ� �� ���� ��ȸ) */\n");
            sql.append("SELECT  CATEGORY_SEQ            \n");
            sql.append("    ,   POSITION_NUM            \n");
            sql.append("    ,   ITEM_NM                 \n");
            sql.append("    ,   ITEM_URL                \n");
            sql.append("    ,   THUMB_FILE_NM           \n");
            sql.append("    ,   USE_YN                  \n");
            sql.append("  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
            sql.append(" WHERE  CATEGORY_SEQ = ").append(categorySeq).append("  \n");

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
     * Ȩ������ ����ȭ�� �α�/��õ �׸� ī�װ� �׸� �� ���� ��� ������ �����Ѵ�.
     * 
     * @param box
     * @return
     */
    public int deleteMainCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCount = 0, resultCountDetail = 0;

        int regItemCnt = box.getInt("regItemCnt");
        System.out.println("regItemCnt : " + regItemCnt);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            if (regItemCnt > 0) {
                sql.append("/* com.credu.admin.homepage.ManiCategoryBean deleteMainCategory (����ȭ�� ī�װ� ���� ��� ���� ���� ) */\n");
                sql.append("DELETE  \n");
                sql.append("  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
                sql.append(" WHERE  CATEGORY_SEQ = ?        \n");

                pstmt = connMgr.prepareStatement(sql.toString());
                pstmt.setInt(1, box.getInt("categorySeq"));

                resultCountDetail = pstmt.executeUpdate();

                pstmt.close();
                pstmt = null;
            }

            sql.setLength(0);
            sql.append("/* com.credu.admin.homepage.ManiCategoryBean deleteMainCategory (����ȭ�� ī�װ� ����) */\n");
            sql.append("DELETE  \n");
            sql.append("  FROM  TZ_MAIN_CATEGORY    \n");
            sql.append(" WHERE  CATEGORY_SEQ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(1, box.getInt("categorySeq"));

            resultCount = pstmt.executeUpdate();

            if ((regItemCnt > 0 && resultCount > 0 && resultCountDetail > 0) || (regItemCnt == 0 && resultCount > 0)) {
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
     * ����ī�װ� �׸��� �����Ѵ�.
     * 
     * @param box
     * @return
     */
    public int updateMainCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int resultCount = 0;
        int index = 1;
        int mainCategoryUseYnCnt = 0;
        int itemUseYnCnt = 0;

        int categorySeq = box.getInt("categorySeq");

        String userid = box.getSession("userid");
        String useYn = box.getString("useYn");
        String layoutType = box.getString("layoutType");
        int maxRegCnt = layoutType.equals("A") ? 5 : 4;

        try {
            connMgr = new DBConnectionManager();

            if (useYn.equals("Y")) {
                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.ManiCategoryBean updateMainCategory (�����׸� ��밡�� ���� ��ȸ) */\n");
                sql.append("SELECT  COUNT(CATEGORY_SEQ) AS CNT  \n");
                sql.append("  FROM  TZ_MAIN_CATEGORY            \n");
                sql.append(" WHERE  USE_YN = 'Y' \n");
                sql.append("   AND  CATEGORY_SEQ <> ").append(categorySeq).append("  \n");

                ls = connMgr.executeQuery(sql.toString());

                if (ls.next()) {
                    mainCategoryUseYnCnt = ls.getInt("cnt");
                }
                ls.close();
                ls = null;
            }

            if (mainCategoryUseYnCnt < 6) {

                sql.setLength(0);
                sql.append("/* com.credu.admin.homepage.ManiCategoryBean updateMainCategory (�ش� ī�װ� �����׸� ��밡�� ���� ��ȸ) */\n");
                sql.append("SELECT  COUNT(SEQ) AS CNT   \n");
                sql.append("  FROM  TZ_MAIN_CATEGORY_DETAIL \n");
                sql.append(" WHERE  USE_YN = 'Y'    \n");
                sql.append("   AND  CATEGORY_SEQ = ").append(categorySeq).append("  \n");

                ls = connMgr.executeQuery(sql.toString());

                if (ls.next()) {
                    itemUseYnCnt = ls.getInt("cnt");
                }
                ls.close();
                ls = null;

                if (itemUseYnCnt <= maxRegCnt) {

                    connMgr.setAutoCommit(false);

                    sql.setLength(0);
                    sql.append("/* com.credu.admin.homepage.ManiCategoryBean updateMainCategory (����ȭ�� ī�װ� ����) */\n");
                    sql.append("UPDATE  TZ_MAIN_CATEGORY    \n");
                    sql.append("   SET  CATEGORY_NM = ?     \n");
                    sql.append("    ,   CATEGORY_TYPE = ?   \n");
                    sql.append("    ,   LAYOUT_TYPE = ?     \n");
                    sql.append("    ,   SORT_ORDER = ?      \n");
                    sql.append("    ,   USE_YN = ?          \n");
                    sql.append("    ,   MAX_ITEM_CNT = ?    \n");
                    sql.append("    ,   MOD_DT = SYSDATE    \n");
                    sql.append("    ,   MOD_ID = ?          \n");
                    sql.append(" WHERE  CATEGORY_SEQ = ?    \n");

                    pstmt = connMgr.prepareStatement(sql.toString());
                    pstmt.setString(index++, box.getString("categoryNm"));
                    pstmt.setString(index++, box.getString("categoryType"));
                    pstmt.setString(index++, layoutType);
                    pstmt.setInt(index++, box.getInt("sortOrder"));
                    pstmt.setString(index++, box.getString("useYn"));
                    pstmt.setInt(index++, maxRegCnt);
                    pstmt.setString(index++, userid);
                    pstmt.setInt(index++, categorySeq);

                    resultCount = pstmt.executeUpdate();

                    if (resultCount > 0) {
                        connMgr.commit();
                    } else {
                        connMgr.rollback();
                    }
                } else {
                    resultCount = 99;
                }
            } else {
                resultCount = 88;
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
        String[] categoryUseYnArr = box.getStringArray("categoryUseYn");
        String[] temp = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.admin.homepage.ManiCategoryBean updateUseYn (��뿩�� �ϰ�����) */\n");
            sql.append("UPDATE  TZ_MAIN_CATEGORY    \n");
            sql.append("   SET  USE_YN = ?          \n");
            sql.append("    ,   MOD_DT = SYSDATE    \n");
            sql.append("    ,   MOD_ID = ?          \n");
            sql.append(" WHERE  CATEGORY_SEQ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < categoryUseYnArr.length; i++) {
                temp = categoryUseYnArr[i].split("\\|");
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
     * ��� ���� ���θ� Ȯ���Ѵ�.
     * 
     * @param box
     * @return
     */
    public String checkPossibleUse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        StringBuilder sql = new StringBuilder();
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.admin.homepage.ManiCategoryBean checkSortOrder (��� ���� ���� Ȯ��) */\n");
            sql.append("SELECT  COUNT(CATEGORY_SEQ) AS CNT  \n");
            sql.append("  FROM  TZ_MAIN_CATEGORY            \n");
            sql.append(" WHERE  USE_YN = 'Y' \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                int cnt = ls.getInt("cnt");
                result = (cnt < 3) ? "Y" : "N";
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
        return result;
    }
}
