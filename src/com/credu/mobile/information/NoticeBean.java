package com.credu.mobile.information;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;


public class NoticeBean {
    public NoticeBean() { }

    /**
     * 모바일 공지사항 목록
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectNoticeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        StringBuffer sql = new StringBuffer();
        ArrayList list = null;
        ListSet ls = null;

        String grcode = box.getSession("grcode");
        grcode = (grcode == null || grcode.equals("")) ? "N000001" : grcode;
        
        int currListCnt = 0;
        currListCnt = box.getInt("currListCnt");
        int startNum = 0;
        int endNum = 0;

        try {
            connMgr = new DBConnectionManager("unpp");
            list = new ArrayList();

            startNum = currListCnt + 1;
            endNum = currListCnt + 10;
            
            //2015.11.13 포털 공지사항을 보여주도록 함
            sql = new StringBuffer();
            sql.append("SELECT * FROM (																		\n");
            sql.append("	SELECT																			\n");
            sql.append("		A.NTT_ID																	\n");
            sql.append("		, A.BBS_ID																	\n");
            sql.append("		, A.NTT_SJ																	\n");
            sql.append("		, A.NTT_CN																	\n");
            sql.append("		, TO_CHAR(A.FRST_REGIST_PNTTM, 'YYYY/MM/DD') AS FRST_REGISTER_PNTTM			\n");
            sql.append("		, CASE																		\n");
            sql.append("			WHEN A.NTT_TYPE = '1'													\n");
            sql.append("			AND REPLACE(A.NTCE_BGNDE,'-','') <= TO_CHAR(SYSDATE, 'YYYYMMDD')		\n");
            sql.append("			AND REPLACE(A.NTCE_ENDDE,'-','') >= TO_CHAR(SYSDATE, 'YYYYMMDD')		\n");
            sql.append("			THEN '1'																\n");
            sql.append("			ELSE '2'																\n");
            sql.append("		END AS NTT_TYPE																\n");
            sql.append("		, RANK() OVER( ORDER BY A.NTT_TYPE ASC, A.FRST_REGIST_PNTTM DESC ) AS RNK	\n");
            sql.append("		, COUNT(A.NTT_ID) OVER() AS TOTCNT											\n");
            sql.append("	FROM																			\n");
            sql.append("		TB_BBS_ESTN a																\n");
            sql.append("	WHERE																			\n");
            sql.append("		A.BBS_ID = 'B0000011' AND A.PARNTS = 0										\n");
            sql.append("		AND A.DELETE_CODE = '0'														\n");
            sql.append(")																					\n");
            sql.append("WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append("		\n");
	        sql.append("ORDER  BY RNK																		\n");

            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                list.add(ls.getDataBox());
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
            
        } finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) { } }
            if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) { } }
        }
        return list;
    }

    /**
     * 공지사항 조회 수를 갱신한다.
     * 
     * @param seq
     * @return
     * @throws Exception
     */
    public int updateNoticeViewCount(int seq) throws Exception {
        DBConnectionManager connMgr = null;
        StringBuilder sql = new StringBuilder();
        int resultCnt = 0;
        try {
            connMgr = new DBConnectionManager();
//            connMgr = new DBConnectionManager("unpp");
            
            sql = new StringBuilder();
            
            //2015.11.13 포털 공지사항 조회수 증가 쿼리
//            sql.append("UPDATE TB_BBS_ESTN SET                 \n");
//            sql.append("    INQIRE_CO = NVL(INQIRE_CO, 0) + 1 \n");
//            sql.append("WHERE                                  \n");
//            sql.append("    BBS_ID IN ('B0000011')             \n");
//            sql.append("    AND A.PARNTS IN ('0')              \n");
//            sql.append("    AND A.DELETE_CODE IN ('0')         \n");
//            sql.append("    AND NTT_ID IN ("+seq+")                 \n");

            
            sql.setLength(0);
            sql.append("UPDATE  TZ_NOTICE       \n");
            sql.append("   SET  CNT = NVL(CNT, 0) + 1   \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("   \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setInt(1, seq);
            //            
            //            resultCnt = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
//        	if (pstmt != null) { try { pstmt.close(); } catch (Exception e) { } }
            if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) { } }
        }
        return resultCnt;
    }
}
