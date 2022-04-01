/*
 * @(#)SSOBean.java
 *
 * Copyright(c) 2007, Jin-pil Chung
 * All rights reserved.
 */

package com.credu.homepage;

import java.util.HashMap;
import java.util.Map;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * SSO Bean Class
 * 
 * @version 1.0 2007
 * @author Jin-pil Chung
 * 
 */
public class SSOBean {
    /**
     * 수강신청된 학생여부인지를 반환한다
     * 
     * @param box
     * @return boolean
     * @throws Exception
     */
    public DataBox getStudentStatus(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();

        String v_userid = box.getString("id");
        String v_subj = box.getString("subjcp");
        String grcode = box.getString("p_grcode");
        
        // todo 
        v_userid = "kbs_m_10262";
        v_subj = "CB12001";
        grcode = "N000085";
        
        try {
            connMgr = new DBConnectionManager();

            //            sql.append("/* com.credu.homepage.SSOBean() isApprovalStduent(유효한 수강생인지 확인) */   \n");
            //            sql.append("SELECT  A.USERID                                            \n");
            //            sql.append("  FROM  TZ_STUDENT A                                        \n");
            //            sql.append("    ,   TZ_SUBJSEQ B                                        \n");
            //            sql.append(" WHERE  A.SUBJ = '").append(v_subj).append("'               \n");
            //            sql.append("   AND  A.USERID = '").append(v_userid).append("'           \n");
            //            sql.append("   AND  B.GRCODE = '").append(grcode).append("'             \n");
            //            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN B.EDUSTART \n");
            //            sql.append("                                             AND B.EDUEND   \n");
            //            sql.append("   AND  A.SUBJ = B.SUBJ                                     \n");
            //            sql.append("   AND  A.YEAR = B.YEAR                                     \n");
            //            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ                               \n");

            sql.append("SELECT  *                                                   \n");
            sql.append("  FROM  (                                                   \n");
            sql.append("        SELECT  A.USERID                                    \n");
            sql.append("            ,   B.GYEAR                                     \n");
            sql.append("            ,   B.GRSEQ                                     \n");
            sql.append("            ,   B.SUBJSEQ                                   \n");
            sql.append("            ,   B.EDUSTART                                  \n");
            sql.append("            ,   B.EDUEND                                    \n");
            sql.append("            ,   CASE WHEN (TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN B.EDUSTART AND B.EDUEND) THEN 'Y'   \n");
            sql.append("                ELSE 'N'                                    \n");
            sql.append("                END AS STUDY_YN                             \n");
            sql.append("            ,   RANK() OVER( ORDER BY B.GYEAR DESC, B.GRSEQ DESC) AS RNK    \n");
            sql.append("          FROM  TZ_STUDENT A                                \n");
            sql.append("            ,   TZ_SUBJSEQ B                                \n");
            sql.append("         WHERE  A.SUBJ = '").append(v_subj).append("'       \n");
            sql.append("           AND  A.USERID = '").append(v_userid).append("'   \n");
            sql.append("           AND  B.GRCODE = '").append(grcode).append("'     \n");
            sql.append("           AND  A.SUBJ = B.SUBJ                             \n");
            sql.append("           AND  A.YEAR = B.YEAR                             \n");
            sql.append("           AND  A.SUBJSEQ = B.SUBJSEQ                       \n");
            sql.append("        )                                                   \n");
            sql.append(" WHERE  RNK = 1                                             \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
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

        return dbox;
    }

    /**
     * 학습창을 띄우기 위한 기본 Parameter를 반환한다.
     * 
     * @param box
     * @return Map
     * @throws Exception
     */
    public Map<String, String> selectEduStartParameter(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        Map<String, String> param = null;

        StringBuffer sql = new StringBuffer();

        String v_userid = box.getString("id");
        String v_subj = box.getString("subjcp");
        String grcode = box.getString("p_grcode");
// todo
v_subj = "CB12001";
v_userid = "kbs_m_10262";

        try {
            connMgr = new DBConnectionManager();
            param = new HashMap<String, String>();

            sql.append("/* com.credu.homepage.SSOBean() selectEduStartParamter (SSO 로그인 사용자 학습창 정보 조회) */ \n");
            sql.append("SELECT  *                                                                          \n");
            sql.append("  FROM  (                                                                          \n");
            sql.append("        SELECT  A.SUBJ                                                             \n");
            sql.append("            ,   A.YEAR                                                             \n");
            sql.append("            ,   A.SUBJSEQ                                                          \n");
            sql.append("            ,   B.CONTENTTYPE                                                      \n");
            sql.append("            ,   (SELECT NVL(DOMAIN, '') FROM TZ_GRCODE WHERE GRCODE = '").append(grcode).append("') AS DOMAIN \n");
            sql.append("            ,   RANK() OVER(ORDER BY A.YEAR DESC, A.SUBJSEQ DESC) AS RNK           \n");
            sql.append("          FROM  TZ_STUDENT A                                                       \n");
            sql.append("            ,   TZ_SUBJ B                                                          \n");
            sql.append("            ,   TZ_SUBJSEQ C                                                       \n");
            sql.append("         WHERE  A.SUBJ = B.SUBJ                                                    \n");
            sql.append("           AND  A.SUBJ = '").append(v_subj).append("'                              \n");
            sql.append("           AND  A.USERID = '").append(v_userid).append("'                          \n");
            sql.append("           AND  C.GRCODE = '").append(grcode).append("'                            \n");
            // sql.append("           AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN C.EDUSTART AND C.EDUEND   \n");
            sql.append("           AND  A.YEAR = C.YEAR                                                    \n");
            sql.append("           AND  A.SUBJ = C.SUBJ                                                    \n");
            sql.append("           AND  A.SUBJSEQ = C.SUBJSEQ                                              \n");
            sql.append("        )                                                                          \n");
            sql.append(" WHERE  RNK = 1                                                                    \n");

            //            sql.append("SELECT  *                       \n");
            //            sql.append("  FROM  (                       \n");
            //            sql.append("        SELECT  A.SUBJ          \n");
            //            sql.append("            ,   A.YEAR          \n");
            //            sql.append("            ,   A.SUBJSEQ       \n");
            //            sql.append("            ,   B.CONTENTTYPE   \n");
            //            sql.append("            ,   RANK() OVER(ORDER BY A.YEAR DESC, A.SUBJSEQ DESC) AS RNK \n");
            //            sql.append("          FROM  TZ_STUDENT A    \n");
            //            sql.append("            ,   TZ_SUBJ B       \n");
            //            sql.append("         WHERE  A.SUBJ = B.SUBJ \n");
            //            sql.append("           AND  A.SUBJ = '").append(v_subj).append("'       \n");
            //            sql.append("           AND  A.USERID = '").append(v_userid).append("'   \n");
            //            sql.append("        )           \n");
            //            sql.append(" WHERE  RNK = 1     \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                param.put("p_subj", ls.getString("subj"));
                param.put("p_year", ls.getString("year"));
                param.put("p_subjseq", ls.getString("subjseq"));
                param.put("contenttype", ls.getString("contenttype"));
                param.put("domain", ls.getString("domain"));
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

        return param;
    }
}
