//**********************************************************
//  1. 제      목: 아카데미이야기 관리
//  2. 프로그램명 : TongBoardAdminBean.java
//  3. 개      요: 아카데미 이야기 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0공지사항
//  6. 작      성: 
//  7. 수      정: 
//**********************************************************
package com.credu.homepage;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * @author Administrator
 */

public class TongBoardAdminBean {

    private ConfigSet config;
    private int row;

    public TongBoardAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 아카데미이야기 게시판 목록 조회
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectTongBoardList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        String searchType = box.getStringDefault("searchType", "");
        String searchKeyword = box.getStringDefault("searchKeyword", "");

        String useYn = box.getString("p_useyn");
        int pageSize = box.getInt("p_pagesize");
        int pageNo = box.getInt("p_pageno");
        int startNum = 0, endNum = 0;
        int totalPage = 0;
        int totalRowCount = 0;

        pageSize = (pageSize == 0) ? row : pageSize;
        pageNo = (pageNo == 0) ? 1 : pageNo;
        startNum = (pageNo - 1) * pageSize + 1;
        endNum = startNum + pageSize - 1;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.TongBoardAdminBean listTongBoard (통기타 게시판 목록 조회) */ \n");
            sql.append("SELECT *                                                \n");
            sql.append("  FROM (                                                \n");
            sql.append("        SELECT                                          \n");
            sql.append("                A.SEQ                                   \n");
            sql.append("            ,   A.TITLE                                 \n");
            sql.append("            ,   A.USE_YN                                \n");
            sql.append("            ,   TO_CHAR(A.REG_DT, 'YYYY-MM-DD HH24:MI') AS REG_DT   \n");
            sql.append("            ,   TO_CHAR(A.REG_DT, 'YYYY.MM.DD') AS REG_DATE   \n");
            sql.append("            ,   CASE WHEN (SYSDATE - REG_DT) < 3 THEN 'Y' ELSE 'N' END AS NEW_YN \n");
            sql.append("            ,   A.VIEW_COUNT                            \n");
            sql.append("            ,   NVL( B.NAME, A.REG_ID) AS REG_NM        \n");
            sql.append("            ,   RANK() OVER(ORDER BY SEQ DESC) AS RNK   \n");
            sql.append("            ,   COUNT(A.SEQ) OVER() AS TOT_CNT          \n");
            sql.append("          FROM  TZ_BOARD_TONG A                          \n");
            sql.append("            ,   TZ_MEMBER B                             \n");
            sql.append("         WHERE  A.REG_ID = B.USERID(+)                  \n");
            
            if (!searchKeyword.equals("")) {
                if (searchType.equals("title")) {
                    sql.append("           AND  A.TITLE LIKE '%' || '").append(searchKeyword).append("' || '%'  \n");

                } else if (searchType.equals("cont")) {
                    sql.append("           AND  A.CONT LIKE '%' || '").append(searchKeyword).append("' || '%'   \n");

                }
            }

            if (useYn != null && !useYn.equals("")) {
                sql.append("           AND  A.USE_YN = '").append(useYn).append("' \n");
            }
            sql.append("        )                                                \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append("\n");
            sql.append(" ORDER  BY RNK                                           \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                totalRowCount = ls.getInt("tot_cnt");
                totalPage = (int) (totalRowCount / pageSize) + 1;
                System.out.println("totalPage : " + totalPage);
                ls.moveFirst();
            }

            int dispNum = totalRowCount - ((pageNo - 1) * pageSize);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispNum", dispNum--);
                dbox.put("d_totalPage", totalPage);
                dbox.put("d_rowCount", row);
                dbox.put("d_totalRowCount", totalRowCount);
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
     * 게시물 등록 처리
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertTongBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtForFile = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        String title = box.getString("title");
        String cont = StringUtil.removeTag(box.getString("cont"));
        String useYn = box.getString("useYn");
        String userid = box.getSession("userid");

        String newFileName = box.getNewFileName("p_file");
        String realFileName = box.getRealFileName("p_file");
        String newImgName = box.getNewFileName("p_imgFile");
        String realImgName = box.getRealFileName("p_imgFile");


        int seq = 0;
        int resultCnt = 0;
        int index = 1;

        File f = null;
        //File img_file = null;
        ConfigSet conf = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("SELECT  NVL(MAX(SEQ), 0) + 1 AS SEQ \n");
            sql.append("  FROM  TZ_BOARD_TONG                \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                seq = ls.getInt("seq");

            }
            ls.close();
            ls = null;

            conf = new ConfigSet();
            String upDir = conf.getProperty("dir.home");
            f = new File(upDir + newFileName);
            System.out.println("file size in Insert process : " + upDir + newFileName);
            long fileSize = f.length();

            title = title.replaceAll("\'", "\'\'");
            /*********************************************************************************************/
            // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.
            try {
                cont =(String) NamoMime.setNamoContent(cont);
            } catch(Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/
            sql.setLength(0);
            sql.append("/* TongBoardAdminBean insertTongBoard ( 통기타 게시물 등록)*/\n");
            sql.append("INSERT  INTO  TZ_BOARD_TONG  (   \n");
            sql.append("        SEQ     \n");
            sql.append("    ,   TITLE   \n");
            sql.append("    ,   CONT    \n");
            sql.append("    ,   USE_YN  \n");
            sql.append("    ,   VIEW_COUNT  \n");
            sql.append("    ,   REG_ID  \n");
            sql.append("    ,   REG_DT  \n");
            sql.append("    ,   MOD_ID  \n");
            sql.append("    ,   MOD_DT  \n");
            sql.append(") VALUES (      \n");
            sql.append("        ? \n");
            sql.append("    ,   ? \n");
            sql.append("    ,   ? \n");
            sql.append("    ,   ? \n");
            sql.append("    ,   0 \n");
            sql.append("    ,   ? \n");
            sql.append("    ,   SYSDATE \n");
            sql.append("    ,   ? \n");
            sql.append("    ,   SYSDATE \n");
            sql.append(")               \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(index++, seq);
            pstmt.setString(index++, title);
            pstmt.setString(index++, cont);
            pstmt.setString(index++, useYn);
            pstmt.setString(index++, userid);
            pstmt.setString(index++, userid);
            // resultCnt = connMgr.executeUpdate(sql.toString());
            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0 && (!newFileName.equals("") || (!newImgName.equals("")))) {
                index = 1;
                
                System.out.println("seq : " + seq);
                System.out.println("realFileName : " + realFileName);
                System.out.println("newFileName : " + newFileName);
                System.out.println("fileSize : " + fileSize);

                sql.setLength(0);
                sql.append("/* TongBoardAdminBean insertTongBoard ( 통기타 게시물 파일 등록)*/\n");
                sql.append("INSERT  INTO  TZ_BOARDFILE_TONG  (   \n");
                sql.append("        SEQ             \n");
                sql.append("    ,   FILE_NUM        \n");
                sql.append("    ,   FILE_NM         \n");
                sql.append("    ,   SAVE_FILE_NM    \n");
                sql.append("    ,   FILE_SIZE       \n");
                sql.append("    ,   IMG_NM         \n");
                sql.append("    ,   SAVE_IMG_NM    \n");
                sql.append("    ,   REG_ID  \n");
                sql.append("    ,   REG_DT  \n");
                sql.append("    ,   MOD_ID  \n");
                sql.append("    ,   MOD_DT  \n");
                sql.append(") VALUES (      \n");
                sql.append("        ? \n");
                sql.append("    ,   (SELECT NVL(MAX(FILE_NUM), 0) + 1 AS FILE_NUM FROM TZ_BOARDFILE_TONG WHERE SEQ = ?) \n");
                sql.append("    ,   ?       \n");
                sql.append("    ,   ?       \n");
                sql.append("    ,   ?       \n");
                sql.append("    ,   ?       \n");
                sql.append("    ,   ?       \n");
                sql.append("    ,   ?       \n");
                sql.append("    ,   SYSDATE \n");
                sql.append("    ,   ?       \n");
                sql.append("    ,   SYSDATE \n");
                sql.append(")               \n");

                pstmtForFile = connMgr.prepareStatement(sql.toString());

                pstmtForFile.setInt(index++, seq);
                pstmtForFile.setInt(index++, seq);
                pstmtForFile.setString(index++, realFileName);
                pstmtForFile.setString(index++, newFileName);
                pstmtForFile.setLong(index++, fileSize);
                pstmtForFile.setString(index++, realImgName);
                pstmtForFile.setString(index++, newImgName);
                pstmtForFile.setString(index++, userid);
                pstmtForFile.setString(index++, userid);

                resultCnt += pstmtForFile.executeUpdate();
                // resultCnt += connMgr.executeUpdate(sql.toString());
            }

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

            if (pstmtForFile != null) {
                try {
                    pstmtForFile.close();
                    pstmtForFile = null;
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
     * 게시물 내용 보기 조회
     * 
     * @param box
     * @return
     */
    public DataBox selectTongBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        int seq = box.getInt("seq");

        try {
            connMgr = new DBConnectionManager();

            sql.setLength(0);
            sql.append("/* TongBoardAdminBean selectTongBoard (통기타 게시물 내용 조회) */ \n");
            sql.append("SELECT  A.TITLE                                             \n");
            sql.append("    ,   A.CONT                                              \n");
            sql.append("    ,   A.VIEW_COUNT                                        \n");
            sql.append("    ,   A.USE_YN                                            \n");
            sql.append("    ,   TO_CHAR(A.REG_DT, 'YYYY-MM-DD HH24:MI') AS REG_DT   \n");
            sql.append("    ,   TO_CHAR(A.REG_DT, 'YYYY.MM.DD') AS REG_FMT_DT       \n");
            sql.append("    ,   A.REG_ID                                            \n");
            sql.append("    ,   GET_NAME(A.REG_ID) AS REG_NM                        \n");
            sql.append("    ,   B.FILE_NM                                           \n");
            sql.append("    ,   B.SAVE_FILE_NM                                      \n");
            sql.append("    ,   B.FILE_SIZE                                         \n");
            sql.append("    ,   B.IMG_NM                                           \n");
            sql.append("    ,   B.SAVE_IMG_NM                                      \n");
            sql.append("  FROM  TZ_BOARD_TONG A                                      \n");
            sql.append("    ,   TZ_BOARDFILE_TONG B                                  \n");
            sql.append(" WHERE  A.SEQ = ").append(seq).append("                     \n");
            sql.append("   AND  A.SEQ = B.SEQ(+)                                    \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                dbox = ls.getDataBox();
            }

            this.updateTongBoardViewCount(box);

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
     * 게시물 삭제 처리
     * 
     * @param box receive from the form object and session
     * @return resultCnt
     * @throws Exception
     */
    public int deleteTongBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtForFile = null;
        StringBuffer sql = new StringBuffer();
        int resultCnt = 0;

        int seq = box.getInt("seq");
        String saveFileNm = box.getString("saveFileNm");
        String saveImgNm = box.getString("saveImgNm");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            if (!saveFileNm.equals("") || !saveImgNm.equals("")) {

                sql.setLength(0);
                sql.append("/* deleteTongBoard (통기타 첨부파일 정보 삭제)*/ \n");
                sql.append("DELETE      \n");
                sql.append("  FROM  TZ_BOARDFILE_TONG\n");
                sql.append(" WHERE  SEQ = ? \n");

                pstmtForFile = connMgr.prepareStatement(sql.toString());
                pstmtForFile.setInt(1, seq);

                resultCnt = pstmtForFile.executeUpdate();

                // resultCnt = connMgr.executeUpdate(sql.toString());

            }

            sql.setLength(0);
            sql.append("/* deleteTongBoard (통기타 게시물 삭제)*/ \n");
            sql.append("DELETE      \n");
            sql.append("  FROM  TZ_BOARD_TONG    \n");
            sql.append(" WHERE  SEQ = ? \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(1, seq);

            resultCnt = pstmt.executeUpdate();

            // resultCnt += connMgr.executeUpdate(sql.toString());

            if (resultCnt > 0) {
                connMgr.commit();

                if (saveFileNm != null) {
                    FileManager.deleteFile(saveFileNm); // 첨부파일 삭제
                }if (saveImgNm != null) {
                    FileManager.deleteFile(saveImgNm); // 썸네일 삭제
                }
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString() + "\r\n");
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {

            if (pstmtForFile != null) {
                try {
                    pstmtForFile.close();
                    pstmtForFile = null;
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
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt;
    }

    /**
     * 게시물 수정 처리
     * 
     * @param box receive from the form object and session
     * @return resultCnt
     * @throws Exception
     */
    public int updateTongBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtForFile = null;

        StringBuffer sql = new StringBuffer();
        int resultCnt = 0;
        int index = 1;

        int seq = box.getInt("seq");
        String title = box.getString("title");
        String cont = StringUtil.removeTag(box.getString("cont"));
        String useYn = box.getString("useYn");
        String userid = box.getSession("userid");
        String orgSaveFileNm = box.getString("orgSaveFileNm");
        String orgSaveImgNm = box.getString("orgSaveImgNm");

        String newFileName = box.getNewFileName("p_file");
        String realFileName = box.getRealFileName("p_file");
        String newImgName = box.getNewFileName("p_imgFile");
        String realImgName = box.getRealFileName("p_imgFile");
        
        System.out.println("newFileName : "+newFileName+"\n");
        System.out.println("realFileName : "+realFileName+"\n");
        System.out.println("newImgName : "+newImgName+"\n");
        System.out.println("realImgName : "+realImgName+"\n");

        File f = null;
        ConfigSet conf = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            title = title.replaceAll("\'", "\'\'");
            
            /*********************************************************************************************/
            // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.
            try {
                cont =(String) NamoMime.setNamoContent(cont);
            } catch(Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/
            sql.setLength(0);
            sql.append("/* updateTongBoard (통기타 게시물 수정)*/ \n");
            sql.append("UPDATE  TZ_BOARD_TONG    \n");
            sql.append("   SET  TITLE = ?   \n");
            sql.append("    ,   CONT = ?    \n");
            sql.append("    ,   USE_YN = ?  \n");
            sql.append("    ,   MOD_ID = ?  \n");
            sql.append("    ,   MOD_DT = SYSDATE    \n");
            sql.append(" WHERE  SEQ = ? \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, title);
            pstmt.setString(index++, cont);
            pstmt.setString(index++, useYn);
            pstmt.setString(index++, userid);
            pstmt.setInt(index++, seq);
            
            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) { // 정보 갱신 성공
                
                if (!newFileName.equals("") || !newImgName.equals("")) { // 신규 파일이 있는 경우

                    conf = new ConfigSet();
                    String upDir = conf.getProperty("dir.home");
                    f = new File(upDir + newFileName);
                    long fileSize = f.length();

                    index = 1;

                    if((orgSaveFileNm != null && !orgSaveFileNm.equals("")) || (orgSaveImgNm != null && !orgSaveImgNm.equals(""))){

                        if(!newFileName.equals("") && !newImgName.equals("")){

                            sql.setLength(0);
                            sql.append("/* updateTongBoard (통기타 첨부파일/썸네일 정보 수정)*/ \n");
                            sql.append("UPDATE  TZ_BOARDFILE_TONG    \n");
                            sql.append("   SET  FILE_NM = ?         \n");
                            sql.append("    ,   SAVE_FILE_NM = ?    \n");
                            sql.append("    ,   FILE_SIZE = ?   \n");
                            sql.append("    ,   IMG_NM = ?         \n");
                            sql.append("    ,   SAVE_IMG_NM = ?    \n");
                            sql.append(" WHERE  SEQ = ? \n");
    
                            pstmtForFile = connMgr.prepareStatement(sql.toString());
    
                            pstmtForFile.setString(index++, realFileName);
                            pstmtForFile.setString(index++, newFileName);
                            pstmtForFile.setLong(index++, fileSize);
                            pstmtForFile.setString(index++, realImgName);
                            pstmtForFile.setString(index++, newImgName);
                            pstmtForFile.setInt(index++, seq);
        
                        } else if(!newFileName.equals("") ){

                            sql.setLength(0);
                            sql.append("/* updateTongBoard (통기타 첨부파일 정보 수정)*/ \n");
                            sql.append("UPDATE  TZ_BOARDFILE_TONG    \n");
                            sql.append("   SET  FILE_NM = ?         \n");
                            sql.append("    ,   SAVE_FILE_NM = ?    \n");
                            sql.append("    ,   FILE_SIZE = ?   \n");
                            sql.append(" WHERE  SEQ = ? \n");
    
                            pstmtForFile = connMgr.prepareStatement(sql.toString());
    
                            pstmtForFile.setString(index++, realFileName);
                            pstmtForFile.setString(index++, newFileName);
                            pstmtForFile.setLong(index++, fileSize);
                            pstmtForFile.setInt(index++, seq);
        
                        } else if ( !newImgName.equals("") ){

                            sql.setLength(0);
                            sql.append("/* updateTongBoard (통기타 썸네일 정보 수정)*/ \n");
                            sql.append("UPDATE  TZ_BOARDFILE_TONG    \n");
                            sql.append("   SET  IMG_NM = ?          \n");
                            sql.append("    ,   SAVE_IMG_NM = ?     \n");
                            sql.append(" WHERE  SEQ = ? \n");
    
                            pstmtForFile = connMgr.prepareStatement(sql.toString());
    
                            pstmtForFile.setString(index++, realImgName);
                            pstmtForFile.setString(index++, newImgName);
                            pstmtForFile.setInt(index++, seq);
                            
                        }
                    } else {

                        sql.setLength(0);
                        sql.append("/* updateTongBoard (통기타 새로운 첨부파일/썸네일 등록)*/ \n");
                        sql.append("INSERT  INTO  TZ_BOARDFILE_TONG  (   \n");
                        sql.append("        SEQ             \n");
                        sql.append("    ,   FILE_NUM        \n");
                        sql.append("    ,   FILE_NM         \n");
                        sql.append("    ,   SAVE_FILE_NM    \n");
                        sql.append("    ,   FILE_SIZE       \n");
                        sql.append("    ,   IMG_NM          \n");
                        sql.append("    ,   SAVE_IMG_NM     \n");
                        sql.append("    ,   REG_ID  \n");
                        sql.append("    ,   REG_DT  \n");
                        sql.append("    ,   MOD_ID  \n");
                        sql.append("    ,   MOD_DT  \n");
                        sql.append(") VALUES (      \n");
                        sql.append("        ? \n");
                        sql.append("    ,   (SELECT NVL(MAX(FILE_NM), 0) + 1 AS FILE_NUM FROM TZ_BOARDFILE_TONG WHERE SEQ = ? ) \n");
                        sql.append("    ,   ?   \n");
                        sql.append("    ,   ?   \n");
                        sql.append("    ,   ?   \n");
                        sql.append("    ,   ?   \n");
                        sql.append("    ,   ?   \n");
                        sql.append("    ,   ?   \n");
                        sql.append("    ,   SYSDATE \n");
                        sql.append("    ,   ?   \n");
                        sql.append("    ,   SYSDATE \n");
                        sql.append(")               \n");

                        pstmtForFile = connMgr.prepareStatement(sql.toString());

                        pstmtForFile.setInt(index++, seq);
                        pstmtForFile.setInt(index++, seq);
                        pstmtForFile.setString(index++, realFileName);
                        pstmtForFile.setString(index++, newFileName);
                        pstmtForFile.setLong(index++, fileSize);
                        pstmtForFile.setString(index++, realImgName);
                        pstmtForFile.setString(index++, newImgName);
                        pstmtForFile.setString(index++, userid);
                        pstmtForFile.setString(index++, userid);
                    }

                    // resultCnt += connMgr.executeUpdate(sql.toString());
                    resultCnt += pstmtForFile.executeUpdate();

                }

            }

            if (resultCnt > 0) {
                connMgr.commit();

                if ((newFileName != null && !newFileName.equals("")) && (orgSaveFileNm != null && !orgSaveFileNm.equals(""))) {
                    FileManager.deleteFile(orgSaveFileNm); // 첨부파일 삭제
                }
                if ((newImgName != null && !newImgName.equals("")) && (orgSaveImgNm != null && !orgSaveImgNm.equals(""))) {
                    FileManager.deleteFile(orgSaveImgNm); // 첨부파일 삭제
                }
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString() + "\r\n");
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {

            if (pstmtForFile != null) {
                try {
                    pstmtForFile.close();
                    pstmtForFile = null;
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
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt;
    }

    /**
     * 게시물 조회 수 갱신처리
     * 
     * @param box receive from the form object and session
     * @return resultCnt
     * @throws Exception
     */
    public void updateTongBoardViewCount(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int resultCnt = 0;

        int seq = box.getInt("seq");
        String userAuthority = box.getSession("gadmin");

        try {
            if (userAuthority.equals("") || userAuthority.equals("ZZ")) {
                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);

                sql.setLength(0);
                sql.append("/* updateTongBoard (통기타 게시물 조회수 갱신)*/ \n");
                sql.append("UPDATE  TZ_BOARD_TONG    \n");
                sql.append("   SET  VIEW_COUNT = VIEW_COUNT + 1 \n");
                sql.append(" WHERE  SEQ = ? \n");
                
                pstmt = connMgr.prepareStatement(sql.toString());
                pstmt.setInt(1, seq);
                
                resultCnt = pstmt.executeUpdate();

                // resultCnt = connMgr.executeUpdate(sql.toString());

                if (resultCnt > 0) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
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

    }

}
