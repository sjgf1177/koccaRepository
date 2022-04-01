package com.credu.mobile.onlineclass;

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
public class OnlineClassReviewBean {

    /**
     * 과정별 후기 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjReviewList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String subj = box.getString("subj");
        String userid = box.getSession("userid");
        String step = box.getStringDefault("step", "step1");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassReviewBean selectSubjReviewList (과정별 후기 목록 조회) */\n");
            sql.append("SELECT  *                                           \n");
            sql.append("  FROM  (                                           \n");
            sql.append("        SELECT  A.SUBJ                              \n");
            sql.append("            ,   A.NUM                               \n");
            sql.append("            ,   A.CONTENTS                          \n");
            sql.append("            ,   A.POINT                             \n");
            sql.append("            ,   A.USERID                            \n");
            sql.append("            ,   B.NAME                              \n");
            sql.append("            ,   TO_CHAR(A.REG_DT, 'YYYY/MM/DD') AS REG_DT   \n");
            sql.append("            ,   DECODE(A.USERID, '").append(userid).append("', 1, 0) AS USER_RNK   \n");
            sql.append("            ,   RANK() OVER(ORDER BY NUM DESC) AS RNK   \n");
            sql.append("            ,   COUNT(NUM) OVER() AS TOT_CNT        \n");
            sql.append("          FROM  TZ_SUBJ_REVIEW A                    \n");
            sql.append("            ,   TZ_MEMBER B                         \n");
            sql.append("         WHERE  A.SUBJ = '").append(subj).append("' \n");
            sql.append("           AND  A.USERID = B.USERID                 \n");
            sql.append("        )                                           \n");

            if (step.equals("step1")) {
                sql.append(" WHERE  RNK < 6 \n");
            } else if (step.equals("step2")) {
                sql.append(" WHERE  RNK < 11 \n");
            }

            sql.append(" ORDER  BY USER_RNK DESC, NUM DESC                       \n");

            // pstmt = connMgr.prepareStatement(sql.toString());
            // pstmt.setString(1, userid);
            // pstmt.setString(2, subj);

            // ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

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
        
        System.out.println("point : " + point);
        System.out.println("subj : " + subj);
        System.out.println("contents : " + contents);
        System.out.println("userid : " + userid);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassReviewBean registerOnlineClassReview (정규과정 후기 등록) */\n");
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

            System.out.println(sql.toString());
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

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassReviewBean modifyOnlineClassReview (정규과정 후기 수정) */\n");
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

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassReviewBean deleteOnlineClassReview (정규과정 후기 삭제) */\n");
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
