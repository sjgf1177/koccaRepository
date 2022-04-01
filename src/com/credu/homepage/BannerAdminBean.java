package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class BannerAdminBean {
    /**
     * 메인 배너리스트 조회
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectBannerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.BannerAdminBean selectBannerList (배너리스트 조회) */ \n");
            sql.append("SELECT  SEQ                                         \n");
            sql.append("    ,   TITLE                                       \n");
            sql.append("    ,   ONOFF_FLAG                                  \n");
            sql.append("    ,   USE_YN                                      \n");
            sql.append("    ,   SORT_ORDER                                  \n");
            sql.append("    ,   SAVE_IMG_NM                                 \n");
            sql.append("    ,   IMG_SIZE                                    \n");
            sql.append("    ,   URL                                         \n");
            sql.append("    ,   URL_TARGET                                  \n");
            sql.append("    ,   EXPLAIN                                     \n");
            sql.append("    ,   START_DT                                    \n");
            sql.append("    ,   END_DT                                      \n");
            sql.append("    ,   TO_CHAR(REG_DT, 'YYYY/MM/DD') AS REG_DT     \n");
            sql.append("    ,   REG_ID                                      \n");
            sql.append("    ,   FIXED_FLAG                                  \n");
            sql.append("    ,   CASE WHEN (TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN START_DT AND END_DT) THEN 'Y' ELSE 'N' END AS PERIOD_YN   \n");
            sql.append("  FROM  TZ_BANNER                                   \n");
            sql.append(" ORDER BY SORT_ORDER                                \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
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
     * 사용여부/정렬순서 수정 처리
     * 
     * @param box receive from the form object and session
     * @return resultCnt
     * @throws Exception
     */
    public int updateBannerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuilder sql = new StringBuilder();
        int[] resultCnt = null;
        int index = 0;

        String[] seq = box.getStringArray("seq");
        String[] use_yn = box.getStringArray("p_use_yn");
        String[] sort_order = box.getStringArray("p_sort_order");
        String fixed_flag = box.getString("fixed_flag");

        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.homepage.BannerAdminBean updateBanner ( 메인 배너 수정)*/ \n");
            sql.append("UPDATE  TZ_BANNER                   \n");
            sql.append("   SET  USE_YN = ?                  \n");
            sql.append("    ,   SORT_ORDER = ?              \n");
            sql.append("    ,   FIXED_FLAG = ?              \n");
            sql.append("    ,   MOD_DT = SYSDATE            \n");
            sql.append("    ,   MOD_ID = ?                  \n");
            sql.append(" WHERE  SEQ = ?                     \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < seq.length; i++) {

                index = 1;
                pstmt.setString(index++, use_yn[i]);
                pstmt.setString(index++, sort_order[i]);
                if (fixed_flag.equals(seq[i])) {
                    pstmt.setString(index++, "Y");
                } else {
                    pstmt.setString(index++, "N");
                }
                pstmt.setString(index++, userid);
                pstmt.setString(index++, seq[i]);
                pstmt.addBatch();
            }

            resultCnt = pstmt.executeBatch();

            if (resultCnt.length > 0) {
                connMgr.commit();

            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString() + "\r\n");
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {

            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt.length;
    }

    /**
     * 메인 배너 팝업보기
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> viewBannerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String[] v_seq = box.getStringArray("v_seq");
        // String[] use_yn = box.getStringArray("p_use_yn");

        StringBuffer sql = new StringBuffer();

        // System.out.println(box+ "----" + use_yn.length+ "__________________");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.BannerAdminBean viewBannerList (미리보기 조회) */ \n");
            sql.append("SELECT  SEQ                                         \n");
            sql.append("    ,   TITLE                                       \n");
            sql.append("    ,   SORT_ORDER                                  \n");
            sql.append("    ,   START_DT                                    \n");
            sql.append("    ,   END_DT                                      \n");
            sql.append("    ,   IMG_NM                                      \n");
            sql.append("    ,   SAVE_IMG_NM                                 \n");
            sql.append("    ,   IMG_SIZE                                    \n");
            sql.append("    ,   URL                                         \n");
            sql.append("    ,   URL_TARGET                                  \n");
            sql.append("    ,   EXPLAIN                                     \n");
            sql.append("    ,   FIXED_FLAG                                  \n");
            sql.append("    ,   CASE WHEN (TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN START_DT AND END_DT) THEN 'Y' ELSE 'N' END AS PERIOD_YN   \n");
            sql.append("  FROM  TZ_BANNER                                   \n");
            sql.append(" WHERE  ( TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN START_DT AND END_DT \n");

            if (v_seq.length > 0) {

                sql.append("  AND SEQ IN ( ");
                for (int i = 0; i < v_seq.length; i++) {
                    sql.append(v_seq[i]);
                    if (i != v_seq.length - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
            } else {
                System.out.println(v_seq.length + "0000000000000");
            }
            sql.append("\n )    \n");
            sql.append("    OR  FIXED_FLAG = 'Y' \n");
            sql.append(" ORDER BY SORT_ORDER                                \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
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
     * 등록 처리
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertBanner(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        String title = box.getString("title");
        String onoffFlag = box.getString("onoffFlag");
        String explain = box.getString("explain");
        String postStartDate = box.getString("postStartDate").replaceAll("/", "");
        ;
        String postEndDate = box.getString("postEndDate").replaceAll("/", "");
        ;
        String imgSize = box.getString("imgSize");
        String linkUrl = box.getString("linkUrl");
        String target = box.getString("target");
        int sortOrder = 1;

        String realFileNm = box.getRealFileName("imgFile");
        String saveFileNm = box.getNewFileName("imgFile");

        String userid = box.getSession("userid");

        int seq = 0;
        int resultCnt = 0;
        int index = 1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            connMgr.executeUpdate("UPDATE  TZ_BANNER  SET   SORT_ORDER = SORT_ORDER +1      \n");

            sql.setLength(0);
            sql.append("SELECT  NVL(MAX(SEQ), 0) + 1 AS SEQ     \n");
            sql.append("FROM  TZ_BANNER                         \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                seq = ls.getInt("seq");
            }
            ls.close();
            ls = null;

            sql.setLength(0);
            sql.append("/* com.sredu.homepage.BannerAdminBean insertBanner (메인배너 등록)*/\n");
            sql.append("INSERT    INTO    TZ_BANNER (   \n");
            sql.append("          SEQ                 \n");
            sql.append("      ,   TITLE               \n");
            sql.append("      ,   ONOFF_FLAG          \n");
            sql.append("      ,   IMG_NM              \n");
            sql.append("      ,   SAVE_IMG_NM         \n");
            sql.append("      ,   IMG_SIZE            \n");
            sql.append("      ,   EXPLAIN             \n");
            sql.append("      ,   URL                 \n");
            sql.append("      ,   URL_TARGET          \n");
            sql.append("      ,   SORT_ORDER          \n");
            sql.append("      ,   START_DT            \n");
            sql.append("      ,   END_DT              \n");
            sql.append("      ,   REG_DT              \n");
            sql.append("      ,   REG_ID              \n");
            sql.append("      ,   MOD_DT              \n");
            sql.append("      ,   MOD_ID              \n");
            sql.append(" ) VALUES   (                   \n");
            sql.append("            ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     SYSDATE             \n");
            sql.append("      ,     ?                   \n");
            sql.append("      ,     SYSDATE             \n");
            sql.append("      ,     ?                  )\n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(index++, seq);
            pstmt.setString(index++, title);
            pstmt.setString(index++, onoffFlag);
            pstmt.setString(index++, realFileNm);
            pstmt.setString(index++, saveFileNm);
            pstmt.setString(index++, imgSize);
            pstmt.setString(index++, explain);
            pstmt.setString(index++, linkUrl);
            pstmt.setString(index++, target);
            pstmt.setInt(index++, sortOrder);
            pstmt.setString(index++, postStartDate);
            pstmt.setString(index++, postEndDate);

            pstmt.setString(index++, userid);
            pstmt.setString(index++, userid);

            // resultCnt = connMgr.executeUpdate(sql.toString());
            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
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
        return resultCnt;
    }

    /**
     * 내용 보기 조회
     * 
     * @param box
     * @return
     */
    public DataBox selectBanner(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        int seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql.setLength(0);
            sql.append("/* com.sredu.homepage.BannerAdminBean selectBanner (메인배너 수정보기) */ \n");
            sql.append("SELECT  SEQ                     \n");
            sql.append("    ,   ONOFF_FLAG              \n");
            sql.append("    ,   TITLE                   \n");
            sql.append("    ,   IMG_NM                  \n");
            sql.append("    ,   SAVE_IMG_NM             \n");
            sql.append("    ,   IMG_SIZE                \n");
            sql.append("    ,   EXPLAIN                 \n");
            sql.append("    ,   URL                     \n");
            sql.append("    ,   URL_TARGET              \n");
            sql.append("    ,   SORT_ORDER              \n");
            sql.append("    ,   START_DT                \n");
            sql.append("    ,   END_DT                    \n");
            sql.append("    ,   TO_CHAR(REG_DT,'YYYY/MM/DD')REG_DT                    \n");
            sql.append("    ,   REG_ID                                                \n");
            sql.append("    ,   TO_CHAR(MOD_DT,'YYYY/MM/DD')MOD_DT                    \n");
            sql.append("    ,   MOD_ID                  \n");
            sql.append("  FROM  TZ_BANNER               \n");
            sql.append(" WHERE  SEQ =   ").append(seq).append("                     \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                dbox = ls.getDataBox();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
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

        return dbox;
    }

    /**
     * 수정 처리
     * 
     * @param box receive from the form object and session
     * @return resultCnt
     * @throws Exception
     */
    public int updateBanner(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();
        int resultCnt = 0;
        int index = 1;

        int seq = box.getInt("p_seq");
        String title = box.getString("title");
        String onoffFlag = box.getString("onoffFlag");
        String explain = box.getString("explain");
        String postStartDate = box.getString("postStartDate").replaceAll("/", "");
        String postEndDate = box.getString("postEndDate").replaceAll("/", "");
        ;
        String imgSize = box.getString("imgSize");
        String linkUrl = box.getString("linkUrl");
        String target = box.getString("target");

        String fileNm = box.getRealFileName("imgFile");
        String saveFileNm = box.getNewFileName("imgFile");
        // String orgSaveImgNm = box.getString("orgSaveImgNm");

        String userid = box.getSession("userid");

        System.out.println(postStartDate + "----" + postEndDate);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // title = title.replaceAll("\'", "\'\'");

            sql.setLength(0);
            sql.append("/* com.credu.homepage.BannerAdminBean updateBanner ( 메인 배너 수정)*/ \n");
            sql.append("UPDATE  TZ_BANNER                   \n");
            sql.append("   SET  TITLE = ?                   \n");
            sql.append("    ,   ONOFF_FLAG =?               \n");
            sql.append("    ,   EXPLAIN =?                  \n");
            sql.append("    ,   START_DT =?                 \n");
            sql.append("    ,   END_DT =?                   \n");
            sql.append("    ,   IMG_SIZE= ?                 \n");
            sql.append("    ,   URL = ?                     \n");
            sql.append("    ,   URL_TARGET = ?              \n");

            if (!saveFileNm.equals("")) { // 신규 파일이 있는 경우
                sql.append("    ,   IMG_NM = ?                  \n");
                sql.append("    ,   SAVE_IMG_NM = ?             \n");
            }
            sql.append("    ,   MOD_DT = SYSDATE            \n");
            sql.append("    ,   MOD_ID = ?                  \n");
            sql.append(" WHERE  SEQ = ?                     \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, title);
            pstmt.setString(index++, onoffFlag);
            pstmt.setString(index++, explain);
            pstmt.setString(index++, postStartDate);
            pstmt.setString(index++, postEndDate);
            pstmt.setString(index++, imgSize);
            pstmt.setString(index++, linkUrl);
            pstmt.setString(index++, target);

            if (!saveFileNm.equals("")) { // 신규 파일이 있는 경우
                pstmt.setString(index++, fileNm);
                pstmt.setString(index++, saveFileNm);
            }
            pstmt.setString(index++, userid);
            pstmt.setInt(index++, seq);

            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();

                // if ((saveFileNm != null && !saveFileNm.equals("")) && (orgSaveImgNm != null && !orgSaveImgNm.equals(""))) {
                // FileManager.deleteFile(orgSaveImgNm); // 첨부파일 삭제
                // }
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString() + "\r\n");
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {

            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt;
    }

}
