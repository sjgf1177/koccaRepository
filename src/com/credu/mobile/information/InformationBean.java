package com.credu.mobile.information;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 고객센터 관련된 기능을 담당한다.
 * @author saderaser
 *
 */
public class InformationBean {

	/**
     * 1:1 문의 내용을 등록한다. 
     * @param box
     * @return
     * @throws Exception
     */
    public int regisetrContactUs(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        DataBox dbox2 = null;
        StringBuffer sql = null;
        int resultCnt = 0;
        String title = box.getString("title");
        String contents = box.getString("contents");
        String userId = box.getSession("userid");
        
        String nttId = "";

        try {
            connMgr = new DBConnectionManager("unpp");
            connMgr.setAutoCommit(false);
            
            
            //2015.11.13 1:1문의글을 포털로 보냄
            sql = new StringBuffer();
            sql.append("SELECT			\n");
            sql.append("    A.MBER_SE,	\n");
            sql.append("    A.USER_SN,	\n");
            sql.append("    A.USER_NM,	\n");
            sql.append("    A.EMAIL,	\n");
            sql.append("    A.MBTLNUM	\n");
            sql.append("FROM TM_USER A	\n");
            sql.append("WHERE			\n");
            sql.append("    A.USER_ID IN ("+StringManager.makeSQL(userId)+")	\n");
            
            ls = connMgr.executeQuery(sql.toString());

            while(ls.next()){
                dbox = ls.getDataBox();
            }
            
            if(ls != null){
            	ls.close();
            }
            
            if(dbox != null){
            	sql.setLength(0);
            	sql.append("SELECT NVL(MAX(NTT_ID),0)+1 AS NTT_ID FROM TB_BBS_ESTN ");
            	ls = connMgr.executeQuery(sql.toString());
                while(ls.next()){
                    dbox2 = ls.getDataBox();
                }
                nttId = dbox2.getString("d_ntt_id");
                if(ls != null){
                	ls.close();
                }
                
            	sql = new StringBuffer();
            	sql.append("INSERT INTO TB_BBS_ESTN (	\n");
            	sql.append("     NTT_ID,				\n");	//게시글 ID
            	sql.append("     BBS_ID,				\n");	//게시판 ID (문의하기: B0000038)
            	sql.append("     NTT_SJ,				\n");	//제목
            	sql.append("     NTT_CN,				\n");	//내용(CLOB)
            	sql.append("     NTCR_ID,				\n");	//회원일련번호
            	sql.append("     NTCR_NM,				\n");	//회원명
            	sql.append("     INQIRE_CO,				\n");
            	sql.append("     REPLY_AT,				\n");
            	sql.append("     PARNTS,				\n");
            	sql.append("     REPLY_LC,				\n");
            	sql.append("     NTCR_EMAIL,			\n");	//이메일
            	sql.append("     NTCR_TEL,				\n");	//전화번호(휴대폰)
            	sql.append("     FRST_REGISTER_ID,		\n");	//회원일련번호(등록자 USER_SN 값)
            	sql.append("     FRST_REGIST_PNTTM,		\n");	//등록일자
            	sql.append("     LAST_UPDT_PNTTM,		\n");	//수정일자
            	sql.append("     USE_AT,				\n");
            	sql.append("     SECRET_AT,				\n");	//비공개여부(Y, N)
            	sql.append("     DELETE_CODE,			\n");	//삭제코드(1: 삭제, 0: 유지)
            	sql.append("     NTT_TYPE,				\n");
            	sql.append("     HTML_AT				\n");
            	sql.append(")							\n");
            	sql.append("VALUES (					\n");
            	sql.append("   ").append(StringManager.makeSQL(nttId)).append("	\n");
            	sql.append("   ,'B0000038'				\n");
            	sql.append("   ,").append(StringManager.makeSQL(title)).append("	\n");
            	sql.append("   ,").append(StringManager.makeSQL(contents)).append("	\n");
            	sql.append("   ,").append(StringManager.makeSQL(dbox.getString("d_user_sn"))).append("	\n");
            	sql.append("   ,").append(StringManager.makeSQL(dbox.getString("d_user_nm"))).append("	\n");
            	sql.append("   ,0						\n");
            	sql.append("   ,'N'						\n");
            	sql.append("   ,'0'						\n");
            	sql.append("   ,'0'						\n");
            	sql.append("   ,").append(StringManager.makeSQL(dbox.getString("d_email"))).append("	\n");
            	sql.append("   ,").append(StringManager.makeSQL(dbox.getString("d_mbtlnum"))).append("	\n");
            	sql.append("   ,").append(StringManager.makeSQL(dbox.getString("d_user_sn"))).append("	\n");
            	sql.append("   ,SYSDATE					\n");
            	sql.append("   ,SYSDATE					\n");
            	sql.append("   ,'Y'						\n");
            	sql.append("   ,'Y'						\n");
            	sql.append("   ,'0'						\n");
            	sql.append("   ,'2'						\n");
            	sql.append("   ,'N'						\n");
            	sql.append(")							\n");
            	
            	resultCnt = connMgr.executeUpdate(sql.toString());
            	
            }

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
        	connMgr.rollback();
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) { try { ls.close(); ls = null; } catch (Exception e10) { } }
            if (connMgr != null) { try { connMgr.freeConnection(); connMgr.setAutoCommit(true); } catch (Exception e10) { } }

        }
        return resultCnt;
    }

    /**
     * 보도자료 목록을 조회한다.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectNewsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int currListCnt = box.getInt("currListCnt");
        int startNum = currListCnt + 1;
        int endNum = currListCnt + 5;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.information.InformationBean selectNewsList (보도자료 목록 조회) */\n");
            sql.append("SELECT  *                                               \n");
            sql.append("  FROM  (                                               \n");
            sql.append("        SELECT                                          \n");
            sql.append("                A.TABSEQ                                \n");
            sql.append("            ,   A.SEQ                                   \n");
            sql.append("            ,   A.TITLE                                 \n");
            sql.append("            ,   A.URL                                   \n");
            sql.append("            ,   B.SAVEFILE                              \n");
            sql.append("            ,   RANK() OVER(ORDER BY A.SEQ DESC) RNK    \n");
            sql.append("            ,   COUNT(A.SEQ) OVER() AS TOTCNT           \n");
            sql.append("          FROM  TZ_PR_ARTICLES A                        \n");
            sql.append("            ,   TZ_BOARDFILE B                          \n");
            sql.append("         WHERE  A.TABSEQ = '2262'                       \n");
            sql.append("           AND  A.FLAGYN = 'Y'                          \n");
            sql.append("           AND  A.TABSEQ = B.TABSEQ(+)                  \n");
            sql.append("           AND  A.SEQ = B.SEQ(+)                        \n");
            sql.append("           AND  B.FILESEQ  = '1'                        \n");
            sql.append("        )                                               \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY RNK                                          \n");
            
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
        return list;
    }

    /**
     * 보도자료 목록을 조회한다.
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox selectNewsDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int seq = box.getInt("seq");

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.mobile.information.InformationBean selectNewsDetail (보도자료 상세 조회) */\n");
            sql.append("SELECT  *                                                   \n");
            sql.append("  FROM  (                                                   \n");
            sql.append("        SELECT                                              \n");
            sql.append("                A.TABSEQ                                    \n");
            sql.append("            ,   A.SEQ                                       \n");
            sql.append("            ,   A.TITLE                                     \n");
            sql.append("            ,   A.URL                                       \n");
            sql.append("            ,   A.CONTENT                                   \n");
            sql.append("            ,   TO_CHAR(TO_DATE( A.LDATE, 'YYYYMMDDHH24MISS'), 'YYYY-MM-DD') AS LDATE \n");
            sql.append("            ,   B.SAVEFILE                                  \n");
            sql.append("            ,   LAG(A.SEQ) OVER(ORDER BY A.SEQ) PREV_SEQ    \n");
            sql.append("            ,   LAG(A.TITLE) OVER(ORDER BY A.SEQ) PREV_TITLE    \n");
            sql.append("            ,   LEAD(A.SEQ) OVER(ORDER BY A.SEQ) NEXT_SEQ   \n");
            sql.append("            ,   LEAD(A.TITLE) OVER(ORDER BY A.SEQ) NEXT_TITLE   \n");
            sql.append("          FROM  TZ_PR_ARTICLES A                            \n");
            sql.append("            ,   TZ_BOARDFILE B                              \n");
            sql.append("         WHERE  A.TABSEQ = '2262'                           \n");
            sql.append("           AND  A.FLAGYN = 'Y'                              \n");
            sql.append("           AND  A.TABSEQ = B.TABSEQ(+)                      \n");
            sql.append("           AND  A.SEQ = B.SEQ(+)                            \n");
            sql.append("           AND  B.FILESEQ  = '1'                            \n");
            sql.append("        )                                                   \n");
            sql.append(" WHERE SEQ = ").append(seq).append("                        \n");
            
            ls = connMgr.executeQuery(sql.toString());

//            pstmt = connMgr.prepareStatement(sql.toString());
//            pstmt.setInt(1, seq);
//
//            ls = new ListSet(pstmt);

            if (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

}
