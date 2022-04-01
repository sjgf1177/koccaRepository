//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.infomation;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.dunet.common.util.StringUtil;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * @author Administrator
 */

public class GoldClassBean {

    private ConfigSet config;
    private int row;

    public GoldClassBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 전문가 관리 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 전문가 관리 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectListGoldClass(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        StringBuilder sb = new StringBuilder();

        String v_search = box.getString("p_search");
        String v_searchtext = box.getString("p_searchtext");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 20 : box.getInt("p_pagesize");

        int totalrowcount = 0;
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            sb.append("/* com.credu.infomation.GoldClassBean selectListGoldClass(열린강좌 목록 조회) */");
            sb.append(" SELECT  \n");
            sb.append("         SEQ                         \n");
            sb.append("     ,   LECNM                       \n");
            sb.append("     ,   TUTORNM                     \n");
            sb.append("     ,   USERID                      \n");
            sb.append("     ,   GET_NAME(USERID) AS NAME    \n");
            sb.append("     ,   OPENYN                      \n");
            sb.append("     ,   USEYN                       \n");
            sb.append("     ,   VIEWCNT                     \n");
            sb.append("     ,   INDATE                      \n");
            sb.append("     ,   COUNT(SEQ) OVER() AS CNT    \n");
            sb.append("   FROM  TZ_GOLDCLASS                \n");

            if (!v_searchtext.equals("")) { // 검색어가 있으면                                         
                if (v_search.equals("lecnm")) { // 강의명 검색  
                    sb.append("  WHERE  UPPER(LECNM) LIKE UPPER('%' || '").append(v_searchtext).append("' || '%')   \n");
                } else if (v_search.equals("tutornm")) { // 강사명 검색  
                    sb.append("  WHERE  UPPER(TUTORNM) LIKE UPPER('%' || '").append(v_searchtext).append("' || '%') \n");
                }
            }

            sb.append("  ORDER  BY  SEQ DESC, OPENYN DESC \n");

            ls = connMgr.executeQuery(sb.toString());

            if (ls.next()) {
                totalrowcount = ls.getInt("CNT");
            }

            ls.moveFirst();
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(totalrowcount));
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
     * 열린강좌를 신규 등록한다.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int insertGoldClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        StringBuilder sb = new StringBuilder();
        ResultSet rs = null;

        int v_seq = 0;
        String v_subtitle = StringUtil.removeTag(box.getString("p_subtitle"));	//자막내용

        try {
            connMgr = new DBConnectionManager();

            stmt = connMgr.createStatement();
            sb.append(" SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM TZ_GOLDCLASS ");
            rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
                v_seq = rs.getInt("seq");
            }

            connMgr.setAutoCommit(false);

            sb.setLength(0);
            sb.append(" /* GoldClassBean.insertGoldClass (열린강좌정보 등록) */ \n");
            sb.append(" INSERT  INTO    TZ_GOLDCLASS                    \n");
            sb.append("     (                                           \n");
            sb.append("         SEQ                                     \n");
            sb.append("     ,   LECNM                                   \n");
            sb.append("     ,   TUTORNM                                 \n");
            sb.append("     ,   VODURL                                  \n");
            sb.append("     ,   TUTORCAREER                             \n");
            sb.append("     ,   LECTIME                                 \n");
            sb.append("     ,   GENRE                                   \n");
            sb.append("     ,   CREATOR                                 \n");
            sb.append("     ,   INTRO                                   \n");
            sb.append("     ,   CONTENTS                                \n");
            sb.append("     ,   TUTORAUTHOR                             \n");
            sb.append("     ,   WIDTH_S                                 \n");
            sb.append("     ,   HEIGHT_S                                \n");
            sb.append("     ,   OPENYN                                  \n");
            sb.append("     ,   VIEWCNT                                 \n");
            sb.append("     ,   VODIMG                                  \n");
            sb.append("     ,   TUTORIMG                                \n");
            sb.append("     ,   USERID                                  \n");
            sb.append("     ,   LUSERID                                 \n");
            sb.append("     ,   USEYN                                   \n");
            sb.append("     ,   INDATE                                  \n");
            sb.append("     ,   LDATE                                   \n");
            sb.append("     ,   MOBILE_URL                              \n");
            sb.append("     ,   CREATYEAR                               \n");
            sb.append("     ,   VOD_PATH                                \n");
            sb.append("     ,   LECTURE_TYPE                            \n");
            sb.append("     ,   LECTURE_CLS                             \n");
            sb.append("     ,   AREA                                    \n");
            sb.append("     ,   NEW_YN                                  \n");
            sb.append("     ,   LECTURE_THEME                           \n");
            sb.append("     ,   TAGS                                    \n");
            sb.append("     ,   GOLDCLASSSUMMARY                        \n");
            sb.append("     ,   SUBTITLE		                        \n");
            sb.append("     ) VALUES (                                  \n");
            sb.append("         ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sb.append("     ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     ,   ?                                       \n");
            sb.append("     )                                           \n");
            pstmt = connMgr.prepareStatement(sb.toString());

            int index = 1;

            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, box.getString("p_lecnm"));
            pstmt.setString(index++, box.getString("p_tutornm"));
            pstmt.setString(index++, box.getString("p_vodurl"));
            pstmt.setString(index++, box.getString("p_tutorcareer"));
            pstmt.setString(index++, box.getString("p_lectime"));
            pstmt.setString(index++, box.getString("p_genre"));
            pstmt.setString(index++, box.getString("p_creator"));
            pstmt.setString(index++, box.getString("p_intro"));
            pstmt.setString(index++, StringUtil.removeTag(box.getString("p_contents")));
            pstmt.setString(index++, box.getString("p_tutorauthor"));
            pstmt.setString(index++, box.getString("p_width_s"));
            pstmt.setString(index++, box.getString("p_height_s"));
            pstmt.setString(index++, box.getStringDefault("p_openyn", "N"));
            pstmt.setInt(index++, 0);
            pstmt.setString(index++, box.getNewFileName("p_img_file"));
            pstmt.setString(index++, box.getNewFileName("p_tutor_file"));
            pstmt.setString(index++, box.getSession("userid"));
            pstmt.setString(index++, box.getSession("userid"));
            pstmt.setString(index++, box.getStringDefault("p_useyn", "N"));
            pstmt.setString(index++, box.getSession("p_mobile_url"));
            pstmt.setString(index++, box.getString("p_creatyear"));
            pstmt.setString(index++, box.getString("p_vod_path"));
            pstmt.setString(index++, box.getString("p_lecture_type"));
            pstmt.setString(index++, box.getString("p_lecture_cls"));
            pstmt.setString(index++, box.getString("p_area"));
            pstmt.setString(index++, box.getString("p_new_yn"));
            pstmt.setString(index++, box.getString("p_lecture_theme"));
            pstmt.setString(index++, box.getString("p_tags"));
            pstmt.setString(index++, box.getString("p_goldclasssummary"));
            pstmt.setCharacterStream(index++, new StringReader(v_subtitle), v_subtitle.length());

            pstmt.executeUpdate();

            if (v_seq > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e1) {
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
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return v_seq;
    }

    /**
     * 열린강좌 수정 정보를 저장한다.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateGoldClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer updateSql = new StringBuffer();
        int isOk = 0;

        int v_seq = box.getInt("p_seq");
        
        
        String v_subtitle = StringUtil.removeTag(box.getString("p_subtitle"));

        String v_img_save_file = box.getNewFileName("p_img_file");
        String v_tutor_save_file = box.getNewFileName("p_tutor_file");

        if (v_img_save_file.length() == 0) {
            v_img_save_file = box.getString("p_img_save_file");
        }

        if (v_tutor_save_file.length() == 0) {
            v_tutor_save_file = box.getString("p_tutor_save_file");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            updateSql.append(" /* GoldClassBean.updateGoldClass (열린강좌정보 수정) */ \n");
            updateSql.append(" UPDATE  TZ_GOLDCLASS                                 \n");
            updateSql.append("    SET                                               \n");
            updateSql.append("         LECNM = ?                                    \n");
            updateSql.append("     ,   TUTORNM = ?                                  \n");
            updateSql.append("     ,   VODURL = ?                                   \n");
            updateSql.append("     ,   TUTORCAREER = ?                              \n");
            updateSql.append("     ,   LECTIME = ?                                  \n");
            updateSql.append("     ,   GENRE = ?                                    \n");
            updateSql.append("     ,   CREATOR = ?                                  \n");
            updateSql.append("     ,   INTRO = ?                                    \n");
            updateSql.append("     ,   CONTENTS = ?                                 \n");
            updateSql.append("     ,   TUTORAUTHOR = ?                              \n");
            updateSql.append("     ,   WIDTH_S = ?                                  \n");
            updateSql.append("     ,   HEIGHT_S = ?                                 \n");
            updateSql.append("     ,   OPENYN = ?                                   \n");
            updateSql.append("     ,   LUSERID = ?                                  \n");
            updateSql.append("     ,   VODIMG = ?                                   \n");
            updateSql.append("     ,   TUTORIMG = ?                                 \n");
            updateSql.append("     ,   USEYN = ?                                    \n");
            updateSql.append("     ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n");
            updateSql.append("     ,   MOBILE_URL = ?                               \n");
            updateSql.append("     ,   CREATYEAR = ?                                \n");
            updateSql.append("     ,   LECTURE_TYPE = ?                             \n");
            updateSql.append("     ,   VOD_PATH = ?                                 \n");
            updateSql.append("     ,   LECTURE_CLS = ?                              \n");
            updateSql.append("     ,   AREA = ?                                     \n");
            updateSql.append("     ,   NEW_YN = ?                                   \n");
            updateSql.append("     ,   LECTURE_THEME = ?                            \n");
            updateSql.append("     ,   TAGS = ?                                     \n");
            updateSql.append("     ,   GOLDCLASSSUMMARY = ?                         \n");
            updateSql.append("     ,   SUBTITLE = ?                                 \n");
            updateSql.append("  WHERE  SEQ = ?                                      \n");
            pstmt = connMgr.prepareStatement(updateSql.toString());

            int index = 1;

            pstmt.setString(index++, box.getString("p_lecnm"));
            pstmt.setString(index++, box.getString("p_tutornm"));
            pstmt.setString(index++, box.getString("p_vodurl"));
            pstmt.setString(index++, box.getString("p_tutorcareer"));
            pstmt.setString(index++, box.getString("p_lectime"));
            pstmt.setString(index++, box.getString("p_genre"));
            pstmt.setString(index++, box.getString("p_creator"));
            pstmt.setString(index++, box.getString("p_intro"));
            pstmt.setString(index++, StringUtil.removeTag(box.getString("p_contents")));
            pstmt.setString(index++, box.getString("p_tutorauthor"));
            pstmt.setString(index++, box.getString("p_width_s"));
            pstmt.setString(index++, box.getString("p_height_s"));
            pstmt.setString(index++, box.getStringDefault("p_openyn", "N"));
            pstmt.setString(index++, box.getSession("userid"));
            pstmt.setString(index++, v_img_save_file);
            pstmt.setString(index++, v_tutor_save_file);
            pstmt.setString(index++, box.getStringDefault("p_useyn", "N"));
            pstmt.setString(index++, box.getString("p_mobile_url"));
            pstmt.setString(index++, box.getString("p_creatyear"));
            pstmt.setString(index++, box.getString("p_lecture_type"));
            pstmt.setString(index++, box.getString("p_vod_path"));
            pstmt.setString(index++, box.getString("p_lecture_cls"));
            pstmt.setString(index++, box.getString("p_area"));
            pstmt.setString(index++, box.getString("p_new_yn"));
            pstmt.setString(index++, box.getString("p_lecture_theme"));
            pstmt.setString(index++, box.getString("p_tags"));
            pstmt.setString(index++, box.getString("p_goldclasssummary"));
            pstmt.setCharacterStream(index++, new StringReader(v_subtitle), v_subtitle.length());

            pstmt.setInt(index++, v_seq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
            throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    public int saveGoldClassGroupInfo(RequestBox box) throws Exception {
        int resultCnt = 0;
        int seq = box.getInt("p_seq");
        resultCnt = saveGoldClassGroupInfo(box, seq);
        return resultCnt;
    }

    /**
     * 열린강좌 교육대상그룹 정보를 저장한다. 기존 자료를 먼저 지우고 선택된 그룹정보만 다시 입력한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int saveGoldClassGroupInfo(RequestBox box, int seq) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuilder sb = new StringBuilder();

        String grCode[] = box.getStringArray("grCode");
        String s_userid = box.getSession("userid");

        int insCnt[] = null;
        int delCnt = 0;
        int index = 1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sb.append(" DELETE  /* GoldClassBean.saveGoldClassGroupInfo (열린강좌 교육대상 그룹 정보 삭제) */ \n");
            sb.append("   FROM  TZ_GOLDCLASS_GRMNG  \n");
            sb.append("  WHERE  SEQ = ? \n");

            pstmt = connMgr.prepareStatement(sb.toString());
            pstmt.setInt(1, seq);

            delCnt = pstmt.executeUpdate();

            if (delCnt > 0) {
                connMgr.commit();
            }

            try {
                pstmt.close();
            } catch (Exception e) {
            }

            if (grCode != null) {
                sb.setLength(0);
                sb.append("/* GoldClassBean.saveGoldClassGroupInfo (열린강좌 교육대상 그룹 정보 입력) */\n");
                sb.append(" INSERT  INTO  TZ_GOLDCLASS_GRMNG  \n");
                sb.append("      (              \n");
                sb.append("         SEQ         \n");
                sb.append("      ,  GRCODE      \n");
                sb.append("      ,  INP_DT      \n");
                sb.append("      ,  INP_USERID  \n");
                sb.append("      ,  MOD_DT      \n");
                sb.append("      ,  MOD_USERID  \n");
                sb.append("      )              \n");
                sb.append(" VALUES              \n");
                sb.append("      (              \n");
                sb.append("         ?           \n");
                sb.append("      ,  ?           \n");
                sb.append("      ,  SYSDATE     \n");
                sb.append("      ,  ?           \n");
                sb.append("      ,  SYSDATE     \n");
                sb.append("      ,  ?           \n");
                sb.append("      )              \n");

                pstmt = connMgr.prepareStatement(sb.toString());

                for (int i = 0; i < grCode.length; i++) {
                    index = 1;
                    pstmt.setInt(index++, seq);
                    pstmt.setString(index++, grCode[i]);
                    pstmt.setString(index++, s_userid);
                    pstmt.setString(index++, s_userid);

                    pstmt.addBatch();
                }

                insCnt = pstmt.executeBatch();

                if (insCnt.length > 0) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return (insCnt == null) ? delCnt : insCnt.length;
    }

    /**
     * 화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    public DataBox selectViewGoldClass(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sb = new StringBuilder();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        // String v_process = box.getString("p_process");

        try {
            connMgr = new DBConnectionManager();

            sb.append(" SELECT  /* GoldClassBean.selectViewGoldClass (열린강좌정보 조회) */ \n");
            sb.append("         SEQ             \n");
            sb.append("     ,   LECNM           \n");
            sb.append("     ,   TUTORNM         \n");
            sb.append("     ,   TUTORCAREER     \n");
            sb.append("     ,   LECTIME         \n");
            sb.append("     ,   GENRE           \n");
            sb.append("     ,   CREATOR         \n");
            sb.append("     ,   INTRO           \n");
            sb.append("     ,   CONTENTS        \n");
            sb.append("     ,   TUTORAUTHOR     \n");
            sb.append("     ,   HEIGHT_S        \n");
            sb.append("     ,   WIDTH_S         \n");
            sb.append("     ,   OPENYN          \n");
            sb.append("     ,   VODURL          \n");
            sb.append("     ,   VODIMG          \n");
            sb.append("     ,   TUTORIMG        \n");
            sb.append("     ,   USEYN           \n");
            sb.append("     ,   MOBILE_URL      \n");
            sb.append("     ,   CREATYEAR       \n");
            sb.append("     ,   LECTURE_TYPE    \n");
            sb.append("     ,   VOD_PATH        \n");
            sb.append("     ,   AREA            \n");
            sb.append("     ,   LECTURE_CLS     \n");
            sb.append("     ,   LECTURE_THEME   \n");
            sb.append("     ,   TAGS            \n");
            sb.append("     ,   NEW_YN          \n");
            sb.append("     ,   GOLDCLASSSUMMARY \n");
            sb.append("     ,   SUBTITLE		\n");
            sb.append("   FROM  TZ_GOLDCLASS    \n");
            sb.append("  WHERE  SEQ = ").append(v_seq).append(" \n");

            ls = connMgr.executeQuery(sb.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
            }

            // if (v_process.equals("selectView")) {
            //    sb.setLength(0);
            //     sb.append(" UPDATE  /* GoldClassBean.selectViewGoldClass (process == selectView) */ \n");
            //     sb.append("         TZ_GOLDCLASS            \n");
            //     sb.append("    SET  VIEWCNT = VIEWCNT + 1   \n");
            //     sb.append("  WHERE  SEQ = ").append(v_seq).append("  \n");

            //     connMgr.executeUpdate(sb.toString());
            // }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("Sql = " + sb + "\r\n" + ex.getMessage());
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
     * 열린강좌를 삭제한다. 열린강좌 정보 삭제 이전에 관리 그룹 테이블의 자료를 먼저 삭제한다.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteGoldClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuilder sql = new StringBuilder();
        int resultCnt = 0;

        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();
            sql.setLength(0);
            sql.append(" DELETE  FROM TZ_GOLDCLASS_GRMNG   \n");
            sql.append("  WHERE  SEQ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(1, v_seq);
            resultCnt = pstmt.executeUpdate();

            pstmt.close();

            sql.setLength(0);
            sql.append(" DELETE  FROM TZ_GOLDCLASS   \n");
            sql.append("  WHERE  SEQ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(1, v_seq);
            resultCnt = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString() + "\r\n");
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
        return resultCnt;
    }


    /**
     * 현재 사용 여부가 'Y'인 교육그룹 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육그룹목록
     * @throws Exception
     */
    public ArrayList<DataBox> selectMngGroupList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuilder sb = new StringBuilder();
        PreparedStatement pstmt = null;

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sb.append("SELECT  A.GRCODE                                         \n");
            sb.append("    ,   A.GRCODENM                                       \n");
            sb.append("    ,   DECODE(NVL(B.SEQ, ''), '', 'N', 'Y') AS CHKFLAG  \n");
            sb.append("  FROM  TZ_GRCODE A                                      \n");
            sb.append("  LEFT  OUTER JOIN TZ_GOLDCLASS_GRMNG B                  \n");
            sb.append("        ON  (                                            \n");
            sb.append("                A.GRCODE = B.GRCODE                      \n");
            sb.append("            AND B.SEQ = ?                                \n");
            sb.append("            )                                            \n");
            sb.append(" WHERE  A.USEYN = 'Y'                                    \n");
            sb.append("   AND  A.GRCODE NOT IN ('N000085', 'N000086')           \n");
            sb.append(" ORDER  BY TO_NUMBER(A.CODE_ORDER)                       \n");

            pstmt = connMgr.prepareStatement(sb.toString());
            pstmt.setInt(1, box.getInt("p_seq"));

            // ls = connMgr.executeQuery(sb.toString());
            ls = new ListSet(pstmt);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
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
     * 오픈강좌 전체 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectAllOpenClassList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        // String clsCd = box.getString("clsCd");
        String hit_yn = box.getString("p_hit_yn");
        String recom_yn = box.getString("p_recom_yn");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.infomation.GoldclassBean() selectAllOpenClassList (열린강좌 전체 과정 목록 조회) */ \n");
            sql.append("SELECT  A.SEQ                           \n");
            sql.append("    ,   A.VIEWCNT                       \n");
            sql.append("    ,   A.LECNM                         \n");
            sql.append("    ,   NVL(A.HIT_YN,'N') HIT_YN        \n");
            sql.append("    ,   NVL(A.RECOM_YN,'N') RECOM_YN    \n");
            sql.append("    ,   A.LDATE                         \n");
            sql.append(" FROM   TZ_GOLDCLASS A                  \n");
            sql.append("    ,   TZ_GOLDCLASS_GRMNG B            \n");
            sql.append("WHERE   GRCODE = 'N000001'              \n");
            sql.append(" AND    USEYN='Y'                       \n");

            if ("Y".equals(hit_yn)) {
                sql.append(" AND  NVL(HIT_YN,'N')='N'               \n");
            } else if ("Y".equals(recom_yn)) {
                sql.append(" AND  NVL(RECOM_YN,'N')='N'               \n");
            }

            sql.append(" AND    A.SEQ = B.SEQ                   \n");
            sql.append(" ORDER  BY VIEWCNT DESC                 \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            ls = new ListSet(pstmt);

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
     * 오픈강좌 인기과정 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectHitOpenClassList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        // String useYn = box.getString("useYn");
        String hit_yn = box.getString("p_hit_yn");
        String recom_yn = box.getString("p_recom_yn");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.infomation.GoldClassBean() selectHitOpenClassList (인기/추천 오픈강좌 목록 조회) */ \n");
            sql.append("SELECT  A.SEQ                           \n");
            sql.append("    ,   A.VIEWCNT                       \n");
            sql.append("    ,   A.LECNM                         \n");
            sql.append("    ,   NVL(A.HIT_YN,'N') HIT_YN        \n");
            sql.append("    ,   NVL(A.RECOM_YN,'N') RECOM_YN    \n");
            sql.append("    ,   A.LDATE                         \n");
            sql.append(" FROM   TZ_GOLDCLASS A                  \n");
            sql.append("    ,   TZ_GOLDCLASS_GRMNG B            \n");
            sql.append("WHERE GRCODE = 'N000001'                \n");
            sql.append(" AND  USEYN = 'Y'                       \n");

            if ("Y".equals(hit_yn)) {
                sql.append(" AND  NVL(HIT_YN,'N')='Y'               \n");
            } else if ("Y".equals(recom_yn)) {
                sql.append(" AND  NVL(RECOM_YN,'N')='Y'               \n");
            }
            sql.append(" AND   A.SEQ = B.SEQ                    \n");
            sql.append(" ORDER BY VIEWCNT DESC                  \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            ls = new ListSet(pstmt);

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
     * 오픈강과 인기과정 목록을 수정한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int saveSubjectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();
        // DataBox dbox = null;
        String seqArr[] = box.getStringArray("classifedOpenClass");
        //String seq      = box.getSession("seq");
        String hit_yn = box.getString("p_hit_yn");
        // String recom_yn = box.getString("p_recom_yn");
        int resultCnt[] = null;

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.infomation.GoldClassBean() saveOpenclassHit (인기강좌 업데이트) */ \n");
            sql.append("UPDATE TZ_GOLDCLASS                 \n");

            if ("Y".equals(hit_yn)) {
                sql.append("   SET HIT_YN = 'N'                 \n");
            } else {
                sql.append("   SET RECOM_YN = 'N'                 \n");
            }

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.executeUpdate();

            pstmt.close();
            pstmt = null;

            sql.setLength(0);
            sql.append("UPDATE TZ_GOLDCLASS                 \n");
            if ("Y".equals(hit_yn)) {
                sql.append("   SET HIT_YN = 'Y'                 \n");
            } else {
                sql.append("   SET RECOM_YN = 'Y'                 \n");
            }
            sql.append(" WHERE SEQ = ?                      \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < seqArr.length; i++) {
                pstmt.setString(1, seqArr[i]);
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

}
