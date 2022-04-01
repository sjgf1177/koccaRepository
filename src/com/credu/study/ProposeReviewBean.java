package com.credu.study;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * 
 * @author saderaser
 * 
 */
public class ProposeReviewBean {

    /**
     * 과정별 후기 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectSubjReviewList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

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

        pageSize = (pageSize == 0) ? 10 : pageSize;
        pageNo = (pageNo == 0) ? 1 : pageNo;
        startNum = (pageNo - 1) * pageSize + 1;
        endNum = startNum + pageSize - 1;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.study.ProposeReviewBean selectSubjReviewList (내가 쓴 후기 목록 조회) */\n");
            sql.append("SELECT  *                                                                     \n");
            sql.append("  FROM  (                                                                     \n");
            sql.append("        SELECT  V.SUBJ                                                        \n");
            sql.append("            ,   V.SUBJNM                                                      \n");
            sql.append("            ,   V.USERID                                                      \n");
            sql.append("            ,   V.APPDATE                                                     \n");
            sql.append("            ,   C.POINT                                                       \n");
            sql.append("            ,   C.CONTENTS                                                    \n");
            sql.append("            ,   C.NUM                                                         \n");
            sql.append("            ,   D.AREA                                                        \n");
            sql.append("            ,   GET_CODENM('0101',D.AREA) AS CODENM                           \n");
            sql.append("            ,   DECODE(C.NUM, NULL, 'N', 'Y') AS REVIEW_YN                    \n");
            sql.append("            ,   COUNT(V.SUBJ) OVER() AS TOT_CNT                               \n");
            sql.append("            ,   RANK() OVER(ORDER BY V.APPDATE DESC, C.REG_DT DESC ) AS RNK   \n");
            sql.append("          FROM  (                                                             \n");
            sql.append("                SELECT  A.SUBJ                                                \n");
            sql.append("                    ,   A.SUBJNM                                              \n");
            sql.append("                    ,   B.USERID                                              \n");
            sql.append("                    ,   MAX(B.APPDATE) AS APPDATE                             \n");
            sql.append("                  FROM  TZ_SUBJSEQ A                                          \n");
            sql.append("                    ,   TZ_PROPOSE B                                          \n");
            sql.append("                 WHERE  A.GRCODE = 'N000001'                                  \n");
            sql.append("                   AND  A.YEAR = B.YEAR                                       \n");
            sql.append("                   AND  A.SUBJ = B.SUBJ                                       \n");
            sql.append("                   AND  A.SUBJSEQ = B.SUBJSEQ                                 \n");
            sql.append("                   AND  B.USERID = '").append(userid).append("'               \n");
            sql.append("                 GROUP  BY A.SUBJ                                             \n");
            sql.append("                    ,   A.SUBJNM                                              \n");
            sql.append("                    ,   B.USERID                                              \n");
            sql.append("                ) V                                                           \n");
            sql.append("            ,   TZ_SUBJ_REVIEW C                                              \n");
            sql.append("            ,   TZ_SUBJ D                                                     \n");
            sql.append("         WHERE  V.SUBJ = C.SUBJ(+)                                            \n");
            sql.append("           AND  V.USERID = C.USERID(+)                                        \n");
            sql.append("           AND  D.SUBJ = V.SUBJ                                               \n");
            sql.append("        )                                                                     \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY RNK                                                                \n");

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
            // pageBlock = (pageNo % blockSize == 0) ? pageBlock : pageBlock + 1;
            startPage = (int) (pageBlock - 1) * blockSize + 1;
            endPage = (int) pageBlock * blockSize;
            endPage = (endPage > totalPage) ? totalPage : endPage;

            dispNum = totalRowCount - ((pageNo - 1) * pageSize);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispNum", dispNum--);
                dbox.put("d_pageNo", pageNo);
                dbox.put("d_totalPage", totalPage);
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
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int registerOnlineClassReview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;

        int resultCnt = 0;
        StringBuilder sql = new StringBuilder();

        // int index = 1;
        int point = box.getInt("point");
        String subj = box.getString("subj");
        String contents = box.getString("contents");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.study.ProposeReviewBean registerOnlineClassReview (정규과정 후기 등록) */\n");
            sql.append("INSERT  INTO    TZ_SUBJ_REVIEW (        \n");
            sql.append("        SUBJ                            \n");
            sql.append("    ,   NUM                             \n");
            sql.append("    ,   CONTENTS                        \n");
            sql.append("    ,   POINT                           \n");
            sql.append("    ,   USERID                          \n");
            sql.append("    ,   REG_DT                          \n");
            sql.append("    ,   MOD_DT                          \n");
            sql.append(") VALUES (                              \n");
            sql.append("        '").append(subj).append("'      \n");
            sql.append("    ,   (SELECT NVL(MAX(NUM), 0) + 1 FROM TZ_SUBJ_REVIEW WHERE SUBJ = '").append(subj).append("' ) /* NUM */ \n");
            sql.append("    ,   '").append(contents).append("'  \n");
            sql.append("    ,   ").append(point).append("       \n");
            sql.append("    ,   '").append(userid).append("'    \n");
            sql.append("    ,   SYSDATE                         \n");
            sql.append("    ,   SYSDATE                         \n");
            sql.append(")                                       \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //
            //            pstmt.setString(index++, subj);
            //            pstmt.setString(index++, subj);
            //            pstmt.setString(index++, contents);
            //            pstmt.setInt(index++, point);
            //            pstmt.setString(index++, box.getSession("userid"));
            //
            //            resultCnt = pstmt.executeUpdate();

            resultCnt = connMgr.executeUpdate(sql.toString());

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                    pstmt = null;
            //                } catch (Exception e10) {
            //                }
            //            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }

        }
        return resultCnt;
    }

    /**
     * 정규과정 후기 내용을 수정한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int modifyOnlineClassReview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;

        int resultCnt = 0;
        StringBuilder sql = new StringBuilder();

        // int index = 1;
        int point = box.getInt("point");
        int num = box.getInt("num");
        String subj = box.getString("subj");
        String contents = box.getString("contents");
        String userid = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.study.ProposeReviewBean modifyOnlineClassReview (정규과정 후기 수정) */\n");
            sql.append("UPDATE  TZ_SUBJ_REVIEW      \n");
            sql.append("   SET  CONTENTS = '").append(contents).append("'   \n");
            sql.append("    ,   POINT = ").append(point).append("           \n");
            sql.append("    ,   MOD_DT = SYSDATE    \n");
            sql.append(" WHERE  USERID = '").append(userid).append("'   \n");
            sql.append("   AND  SUBJ = '").append(subj).append("'       \n");
            sql.append("   AND  NUM = ").append(num).append("           \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //
            //            pstmt.setString(index++, contents);
            //            pstmt.setInt(index++, point);
            //            pstmt.setString(index++, box.getSession("userid"));
            //            pstmt.setString(index++, subj);
            //            pstmt.setInt(index++, num);
            //
            //            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                    pstmt = null;
            //                } catch (Exception e10) {
            //                }
            //            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }

        }
        return resultCnt;
    }

    /**
     * 정규과정 후기 내용을 삭제한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int deleteOnlineClassReview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;

        int resultCnt = 0;
        StringBuilder sql = new StringBuilder();

        // int index = 1;
        int num = box.getInt("num");
        String subj = box.getString("subj");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.study.ProposeReviewBean deleteOnlineClassReview (정규과정 후기 삭제) */\n");
            sql.append("DELETE                  \n");
            sql.append("  FROM  TZ_SUBJ_REVIEW  \n");
            sql.append(" WHERE  USERID = '").append(userid).append("'   \n");
            sql.append("   AND  SUBJ = '").append(subj).append("'       \n");
            sql.append("   AND  NUM = ").append(num).append("           \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //
            //            pstmt.setString(index++, box.getSession("userid"));
            //            pstmt.setString(index++, subj);
            //            pstmt.setInt(index++, num);
            //
            //            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                    pstmt = null;
            //                } catch (Exception e10) {
            //                }
            //            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }

        }
        return resultCnt;
    }

}
