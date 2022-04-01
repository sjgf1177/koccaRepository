package com.credu.mobile.subj;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * 과정 찜하기 관리를 담당한다.
 * 
 * @author saderaser
 * 
 */
public class SubjectBean {

    /**
     * 과정 찜하기 등록
     * 
     * @param box
     * @return
     */
    public int registerSubjFavor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        String grcode = box.getSession("grcode");
        String userid = box.getSession("userid");
        String classType = box.getString("classType");
        String subj = box.getString("subj");
        String subjseq = box.getStringDefault("subjseq", "");
        String year = box.getStringDefault("year", "");

        grcode = (grcode.equals("")) ? box.getSession("tem_grcode") : grcode;

        // int index = 1;
        int resultCnt = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.mobile.subj.SubjectBean.registerSubjFavor (찜한 강좌 30번째 목록 삭제) */   \n");
            sql.append("DELETE  TZ_SUBJ_FAVOR                                                   \n");
            sql.append(" WHERE  USERID = '").append(userid).append("'                           \n");
            sql.append("   AND  CLASS_TYPE= '").append(classType).append("'                     \n");
            sql.append("   AND  SUBJ IN (                                                       \n");
            sql.append("            SELECT  SUBJ                                                \n");
            sql.append("              FROM  (                                                   \n");
            sql.append("                    SELECT  USERID                                      \n");
            sql.append("                        ,   SUBJ                                        \n");
            sql.append("                        ,   RANK() OVER( ORDER BY REG_DT DESC) AS RNK   \n");
            sql.append("                      FROM  TZ_SUBJ_FAVOR                               \n");
            sql.append("                     WHERE  USERID = '").append(userid).append("'       \n");
            sql.append("                       AND  CLASS_TYPE = '").append(classType).append("'    \n");
            sql.append("                    )                                                   \n");
            sql.append("             WHERE  RNK > 29                                            \n");
            sql.append("        )                                                               \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            // pstmt = connMgr.prepareStatement(sql.toString());
            //
            // pstmt.setString(index++, userid);
            // pstmt.setString(index++, classType);
            // pstmt.setString(index++, userid);
            // pstmt.setString(index++, classType);
            //
            // resultCnt = pstmt.executeUpdate();
            // pstmt.close();
            // pstmt = null;

            // index = 1;
            sql.setLength(0);

            sql.append("/* com.credu.mobile.subj.SubjectBean.registerSubjFavor (과정 찜하기 등록) */   \n");
            sql.append("INSERT  INTO  TZ_SUBJ_FAVOR \n");
            sql.append("    (               \n");
            sql.append("        GRCODE      \n");
            sql.append("    ,   USERID      \n");
            sql.append("    ,   CLASS_TYPE  \n");
            sql.append("    ,   SUBJ        \n");
            sql.append("    ,   SUBJSEQ     \n");
            sql.append("    ,   YEAR        \n");
            sql.append("    ,   REG_DT      \n");
            sql.append("    ,   MOD_DT      \n");
            sql.append("    )               \n");
            sql.append(" VALUES             \n");
            sql.append("    (               \n");
            sql.append("        '").append(grcode).append("'    \n");
            sql.append("    ,   '").append(userid).append("'    \n");
            sql.append("    ,   '").append(classType).append("' \n");
            sql.append("    ,   '").append(subj).append("'      \n");
            sql.append("    ,   '").append(subjseq).append("'   \n");
            sql.append("    ,   '").append(year).append("'      \n");
            sql.append("    ,   SYSDATE \n");
            sql.append("    ,   SYSDATE \n");
            sql.append("    )   \n");

            resultCnt += connMgr.executeUpdate(sql.toString());

            // pstmt = connMgr.prepareStatement(sql.toString());
            //
            // pstmt.setString(index++, grcode);
            // pstmt.setString(index++, userid);
            // pstmt.setString(index++, classType);
            // pstmt.setString(index++, subj);
            // pstmt.setString(index++, subjseq);
            // pstmt.setString(index++, year);
            //
            // resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            System.out.println("Exception : " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            // if (pstmt != null) {
            // try {
            // pstmt.close();
            // } catch (Exception e) {
            // }
            // }
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
     * 과정 찜하기 취소
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int cancelSubjFavor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        String grcode = box.getSession("grcode");
        String userid = box.getSession("userid");
        String classType = box.getString("classType");
        String subj = box.getString("subj");
        // String subjseq = box.getString("subjseq");
        // String year = box.getString("year");
            
        // int index = 1;
        int resultCnt = 0;
        grcode = (grcode.equals("")) ? box.getSession("tem_grcode") : grcode;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* SubjFavorBean.cancelSubjFavor (과정 찜하기 취소) */ \n");
            sql.append("DELETE  FROM TZ_SUBJ_FAVOR \n");
            sql.append(" WHERE  GRCODE = '").append(grcode).append("'       \n");
            sql.append("   AND  USERID = '").append(userid).append("'       \n");
            sql.append("   AND  CLASS_TYPE = '").append(classType).append("'\n");
            sql.append("   AND  SUBJ = '").append(subj).append("'           \n");

            // if (classType.equals("01")) { // 정규과정일 경우
            // sql.append("   AND  SUBJSEQ = ?     \n");
            // sql.append("   AND  YEAR = ?        \n");
            // }

            // pstmt = connMgr.prepareStatement(sql.toString());
            //
            // pstmt.setString(index++, grcode);
            // pstmt.setString(index++, userid);
            // pstmt.setString(index++, classType);
            // pstmt.setString(index++, subj);

            // if (classType.equals("01")) { // 정규과정일 경우
            // pstmt.setString(index++, subjseq);
            // pstmt.setString(index++, year);
            // }

            // resultCnt = pstmt.executeUpdate();
            // System.out.println("resultCnt : " + resultCnt);
            resultCnt = connMgr.executeUpdate(sql.toString());

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            // if (pstmt != null) {
            // try {
            // pstmt.close();
            // } catch (Exception e) {
            // }
            // }
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
     * 과정 검색 결과를 조회한다. 정규과정, 열린강좌 모두 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> searchSubjectList(RequestBox box, String keyword) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        StringBuffer sql = new StringBuffer();

        // String keyword = box.getString("keyword");
        String grcode = box.getSession("grcode");
        grcode = (grcode == null || grcode.equals("")) ? "N000001" : grcode;

        String[] keywordArr = null;

        try {
            connMgr = new DBConnectionManager();

            // 검색어 로그 쌓기
            System.out.println("keyword : "+keyword);
            //keyword = java.net.URLDecoder.decode(keyword, "euc-kr");
            box.put("keyword", keyword);
            createSearchWordLog(connMgr, box);

            keywordArr = keyword.split("\\s");

            sql.append("/* com.credu.mobile.subj.SubjectBean searchSubjectList (통합검색) */");
            sql.append(" SELECT  *                                                   \n");
            sql.append("   FROM  (                                                   \n");
            sql.append("         SELECT  COUNT(*) OVER() AS TOT_CNT                  \n");
            sql.append("             ,   SUBJ_FLAG                                   \n");
            sql.append(this.makeRankQuery(keywordArr));
            sql.append("             ,   UPPERCLASS                                \n");
            sql.append("             ,   SUBJ                                        \n");
            sql.append("             ,   YEAR                                        \n");
            sql.append("             ,   SUBJSEQ                                     \n");
            sql.append("             ,   SUBJNM                                      \n");
            sql.append("             ,   REPLACE(SUBJFILENAMENEW, '\', '/') AS SUBJFILENAMENEW   \n");
            sql.append("             ,   REPLACE(SUBJFILENAMEREAL, '\', '/') AS SUBJFILENAMEREAL \n");
            sql.append("             ,   ISUSE                                       \n");
            sql.append("             ,   SPECIALS                                    \n");
            sql.append("             ,   TUTOR_NM                                    \n");
            sql.append("             ,   BIZ_TYPE                                    \n");
            sql.append("             ,   MOBILE_USE_YN                               \n");
            sql.append("             ,   TO_CHAR(TO_DATE(PROPSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPSTART \n");
            sql.append("             ,   TO_CHAR(TO_DATE(PROPEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPEND     \n");
            sql.append("             ,   TO_CHAR(TO_DATE(EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART   \n");
            sql.append("             ,   TO_CHAR(TO_DATE(EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND       \n");
            sql.append("           FROM  VZ_SUBJINFO V                               \n");
            sql.append("          WHERE  GRCODE = '").append(grcode).append("'       \n");
            sql.append("            AND  ( " + this.makeSearhConditionQuery("SUBJNM", keywordArr, keyword));
            sql.append("             OR " + this.makeSearhConditionQuery("INTRO", keywordArr, keyword));
            // sql.append("             OR " +
            // this.makeSearhConditionQuery("EXPLAIN", keywordArr, keyword));
            sql.append("             OR " + this.makeSearhConditionQuery("SEARCH_NM", keywordArr, keyword));
            sql.append("             OR " + this.makeSearhConditionQuery("TUTOR_NM", keywordArr, keyword));
            sql.append("     )                                   \n");
            sql.append("            AND  SUBJ_FLAG <> 'OFFLINE'  \n");
            sql.append("     )                                   \n");
            sql.append("  ORDER  BY SUBJ_FLAG, ( \n");

            for (int i = 0; i < keywordArr.length; i++) {
                if (i < keywordArr.length - 1) {
                    sql.append("  RNK").append((i + 1)).append(" || ");
                } else {
                    sql.append("  RNK").append((i + 1));
                }
            }
            sql.append("  ) ASC, SUBJNM                          \n");

            DataBox dbox = null;

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
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

    private String makeRankQuery(String[] strArr) throws ArrayIndexOutOfBoundsException {
        StringBuffer sql = new StringBuffer();
        try {
            for (int i = 0; i < strArr.length; i++) {
                sql.append("             ,   CASE WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') > 0 THEN 1  \n");
                sql.append("                      WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') = 0 AND INSTR(INTRO, '").append(strArr[i])
                        .append("') > 0  THEN 2  \n");
                sql.append("                      WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') = 0 AND INSTR(INTRO, '").append(strArr[i])
                        .append("') = 0 AND INSTR(EXPLAIN, '").append(strArr[i]).append("') > 0 THEN 3 \n");
                sql.append("                      WHEN INSTR(SUBJNM, '").append(strArr[i]).append("') = 0 AND INSTR(INTRO, '").append(strArr[i])
                        .append("') = 0 AND INSTR(EXPLAIN, '").append(strArr[i]).append("') = 0 AND INSTR(TUTOR_NM, '").append(strArr[i]).append(
                                "') > 0 THEN 4  \n");
                sql.append("                      ELSE 5     \n");
                sql.append("                 END AS RNK").append((i + 1)).append(" \n");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ErrorManager.getErrorStackTrace(ex);
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
        }
        return sql.toString();
    }

    /**
     * 과정 검색시에 띄어쓰기 검색어가 입력될 경우 조건 쿼리문을 생성해서 리턴한다.
     * 
     * @param colName
     * @param strArr
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    private String makeSearhConditionQuery(String colName, String[] strArr, String fullWord) throws ArrayIndexOutOfBoundsException {
        StringBuffer sql = new StringBuffer();
        try {
            colName = colName.toUpperCase();
            if (strArr.length == 1) {
                sql.append(" ( UPPER(").append(colName).append(") LIKE UPPER('%' || '").append(strArr[0]).append("' || '%') )\n");
            } else {
                sql.append("(");
                for (int i = 0; i < strArr.length; i++) {
                    if (i == 0) {
                        sql.append(" UPPER(").append(colName).append(") LIKE UPPER('%' || '").append(strArr[i]).append("' || '%') \n");
                    } else {
                        sql.append(" OR   UPPER(").append(colName).append(") LIKE UPPER('%' || '").append(strArr[i]).append("' || '%') \n");
                    }
                }
                // sql.append(" OR   " + colName + " LIKE '%' || '" + fullWord +
                // "' || '%' \n");
                sql.append(" )\n");
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
            ErrorManager.getErrorStackTrace(ex);
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
        }
        return sql.toString();

    }

    /**
     * 사용자가 입력한 검색어 로그 저장
     * 
     * @param connMgr
     *            DBConnectionManager
     * @param box
     *            RequestBox
     * @return result Int
     * @throws Exception
     */
    public int createSearchWordLog(DBConnectionManager connMgr, RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int resultCnt = 0;
        String keyword = box.getString("keyword");
        String userip = box.getString("userip");
        String userid = box.getSession("userid");
        
        // keyword = java.net.URLDecoder.decode(keyword, "euc-kr");

        try {
            sql.append("INSERT   INTO    TZ_LOG_SEARCH_WORD \n");
            sql.append("         (               \n");
            sql.append("             SEARCH_WORD \n");
            sql.append("         ,   SEARCH_DT   \n");

            if (userid != null && !userid.equals("")) {
                sql.append("         ,   USERID      \n");
            }
            sql.append("         ,   USER_IP     \n");
            sql.append("         ,   PLATFORM    \n");
            sql.append("         )   VALUES  (   \n");
            sql.append("             '").append(keyword).append("'  \n");
            sql.append("         ,   SYSDATE     \n");

            if (userid != null && !userid.equals("")) {
                sql.append("         ,   '").append(userid).append("'   \n");
            }

            sql.append("         ,   '").append(userip).append("'       \n");
            sql.append("         ,   'MOBILE'    \n");
            sql.append("         )               \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            // pstmt = connMgr.prepareStatement(sql.toString());
            // pstmt.setString(++i, box.getString("kyeword"));
            //
            // if (userId != null && !userId.equals("")) {
            // pstmt.setString(++i, userId);
            // }
            //
            // pstmt.setString(++i, box.getString("userip"));
            //
            // result = pstmt.executeUpdate();

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("" + e.getMessage());
        } finally {
        }
        return resultCnt;
    }

}
