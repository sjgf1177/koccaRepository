//*******************************************************c***n_se
//  1. 제      목: 템플릿 관리
//  2. 프로그램명: TempletBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 정상진 2005.7.8
//  7. 수      정:
//**********************************************************
package com.credu.templet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileMove;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

@SuppressWarnings("unchecked")
public class TempletBean {

    private static Map mainMenuList;
    private static Map subMenuList;
    private static Map detailMenuInfoList;
    private static Map detailMenuInfo;

    private ConfigSet config;

    public static void resetMenuList(String grcode) {
        mainMenuList.put(grcode, null);
        //subMenuList = null;
        subMenuList.put(grcode, null);
        detailMenuInfoList.put(grcode, null);
        detailMenuInfo.put(grcode, null);
    }

    public DataBox getSubMain(RequestBox box) throws Exception {
        DataBox dbox = null;

        String v_gubun = box.getSession("s_gubun").equals("") ? "1" : box.getSession("s_gubun");
        String temp_gubun = "";
        String v_grcode = box.getSession("tem_grcode");

        //      String ImgUrl = "";
        List tempList = (List) mainMenuList.get(v_grcode);

        for (int i = 0; i < tempList.size(); i++) {
            dbox = (DataBox) tempList.get(i);
            temp_gubun = dbox.getString("d_gubun");
            if (temp_gubun.equals(v_gubun)) {
                break;
            }
        }

        config = new ConfigSet();

        dbox.put("d_imgpath", (config.getProperty("dir.templet.submenu")) + v_gubun + "\\");

        return dbox;
    }

    public String getMenuUrl(RequestBox box) throws Exception {
        DataBox dbox = null;
        String menuUrl = "";

        String v_menuid = box.getString("menuid");
        String v_gubun = box.getString("gubun");
        String v_grcode = box.getSession("tem_grcode");
        String temp_gubun = "";
        String temp_menuid = "";

        List tempList = this.getSubMenuList(v_grcode);

        for (int i = 0; i < tempList.size(); i++) {
            dbox = (DataBox) tempList.get(i);
            temp_gubun = dbox.getString("d_gubun");
            temp_menuid = dbox.getString("d_menuid");

            if (temp_gubun.equals(v_gubun) && temp_menuid.equals(v_menuid)) {
                menuUrl = dbox.getString("d_menuurl");
                break;
            }

        }
        return menuUrl;
    }

    public TempletBean() {
        if (mainMenuList == null)
            mainMenuList = new HashMap();
        if (subMenuList == null)
            subMenuList = new HashMap();
        if (detailMenuInfoList == null)
            detailMenuInfoList = new HashMap();
        if (detailMenuInfo == null)
            detailMenuInfo = new HashMap();
    }

    public List getMainMenuList(String v_grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        List list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        // 존재시 반환 후 종료
        //if( mainMenuList.get(v_grcode) != null && ((List)mainMenuList.get(v_grcode)).size() != 0) {
        //  return (List)mainMenuList.get(v_grcode);
        //}

        try {
            list = (List) mainMenuList.get(v_grcode);
            if (list == null || list.size() == 0) {

                connMgr = new DBConnectionManager();
                list = new ArrayList();

                sql.append(" SELECT /*TempletBean.getMainMenuList : 123*/\n GRCODE, GUBUN, MENUID, KIND \n");
                sql.append("        , POSITION, ORDERS, MENUNAME, MENUURL, MENUIMG\n");
                sql.append("        , MENUOVERIMG, SUBIMG, SUBOVERIMG\n");
                sql.append("        , MENUHOMEIMG, FLASHFILENAME ,MENUXPOSITION,LDATE\n");
                sql.append(" FROM   TZ_HOMEMENU\n");
                sql.append(" WHERE  MENUID = '00'\n");
                sql.append(" AND    GRCODE  = ").append(StringManager.makeSQL(v_grcode));
                sql.append(" ORDER BY GRCODE, GUBUN ASC, POSITION, ORDERS");

                ls = connMgr.executeQuery(sql.toString());

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
                mainMenuList.put(v_grcode, list);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
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

    public List getSubMenuList(String v_grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        List list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        //if( subMenuList.get(v_grcode) != null && ((List)subMenuList.get(v_grcode)).size() != 0) {
        //  return (List)subMenuList.get(v_grcode);
        //}

        try {
            if (subMenuList.get(v_grcode) == null || ((List) subMenuList.get(v_grcode)).size() == 0) {
                connMgr = new DBConnectionManager();
                list = new ArrayList();

                sql.append("/* TempletBean.getSubMenuList (그룹코드별 메뉴 목록 조회) */    \n");
                sql.append(" SELECT  GUBUN      \n");
                sql.append("     ,   MENUID     \n");
                sql.append("     ,   KIND       \n");
                sql.append("     ,   POSITION   \n");
                sql.append("     ,   MENUNAME   \n");
                sql.append("     ,   MENUURL    \n");
                sql.append("     ,   MENUIMG    \n");
                sql.append("     ,   MENUOVERIMG \n");
                sql.append("     ,   SUBIMG     \n");
                sql.append("     ,   SUBOVERIMG \n");
                sql.append("   FROM  TZ_HOMEMENU    \n ");
                sql.append("  WHERE  GRCODE = '").append(v_grcode).append("'    \n");
                sql.append("    AND  MENUID <>  '00'    \n");
                sql.append("  ORDER  BY GUBUN ASC, ORDERS ASC   \n");

                ls = connMgr.executeQuery(sql.toString());

                config = new ConfigSet();
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    dbox.put("d_imgpath", (config.getProperty("dir.templet.submenu")) + dbox.getString("d_gubun") + "\\");
                    list.add(dbox);
                }

                subMenuList.put(v_grcode, list);
            } else {
                list = (List) subMenuList.get(v_grcode);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
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
     * 교육그룹리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육그룹리스트
     */
    public ArrayList SelectEduGroupList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        //      String v_grtype = box.getStringDefault("p_grtype",box.getSession("grtype"));

        try {
            list = new ArrayList();

            sql.append(" SELECT A.GRCODE, A.GRCODENM, B.TYPE ");
            sql.append("   FROM TZ_GRCODE A, TZ_TEMPLET B ");
            sql.append("  WHERE A.GRCODE = B.GRCODE(+) AND A.USEYN='Y' ");
            sql.append("  ORDER BY A.GRCODE ASC ");

            connMgr = new DBConnectionManager();

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
     * 교육그룹리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육그룹리스트
     */
    public DataBox getGroupInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        String v_servernm = box.getString("p_servernm");

        try {

            sql.append(" SELECT A.GRCODE, A.GRCODENM, B.TYPE  ");
            sql.append("   FROM TZ_GRCODE A, TZ_TEMPLET B ");
            sql.append("  WHERE A.GRCODE = B.GRCODE(+) ");
            sql.append("    AND A.USEYN = 'Y' ");
            sql.append("    AND DOMAIN = ").append(StringManager.makeSQL(v_servernm));

            connMgr = new DBConnectionManager();

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
     * 템플릿 타입
     * 
     * @param box receive from the form object and session
     * @return String 템플릿 타입
     */
    public String getGrcodeType(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        String result = "";
        String v_grcode = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT TYPE FROM TZ_TEMPLET   ");
            sql.append(" WHERE GRCODE = ").append(StringManager.makeSQL(v_grcode));

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                result = ls.getString("type");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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

    /**
     * 교육그룹별 타입 지정
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int InsertGrcodeType(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        StringBuffer sql1 = new StringBuffer();
        StringBuffer sql2 = new StringBuffer();
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_type = box.getString("p_type");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();
            // tz_templet initialize
            sql1.append("DELETE FROM TZ_TEMPLET WHERE GRCODE = ? ");
            pstmt1 = connMgr.prepareStatement(sql1.toString());
            pstmt1.setString(1, v_grcode);
            isOk = pstmt1.executeUpdate();

            // tz_templet insert (TZ_TEMPLET_MASTER 마스터 정보 COPY)
            sql2.append(" /* TempletBean.InsertGrcodeType (TZ_TEMPLET_MASTER 마스터 정보 COPY) */ \n");
            sql2.append(" INSERT  INTO    TZ_TEMPLET (                              \n");
            sql2.append("                 GRCODE                                    \n");
            sql2.append("             ,   TYPE                                      \n");
            sql2.append("             ,   IMGPATH                                   \n");
            sql2.append("             ,   MAINFLASH                                 \n");
            sql2.append("             ,   MENUFLASH                                 \n");
            sql2.append("             ,   MAINBG                                    \n");
            sql2.append("             ,   TOPLOGO                                   \n");
            sql2.append("             ,   TOPBG                                     \n");
            sql2.append("             ,   FOOTTOPIMG                                \n");
            sql2.append("             ,   FOOTBG                                    \n");
            sql2.append("             ,   FOOTBGCOLOR1                              \n");
            sql2.append("             ,   FOOTBGCOLOR2                              \n");
            sql2.append("             ,   FOOTCOPYRIGHT                             \n");
            sql2.append("             ,   FOOTLEFTIMG                               \n");
            sql2.append("             ,   SUBBG1                                    \n");
            sql2.append("             ,   SUBTOPIMG1                                \n");
            sql2.append("             ,   SUBBG2                                    \n");
            sql2.append("             ,   SUBTOPIMG2                                \n");
            sql2.append("             ,   SUBBG3                                    \n");
            sql2.append("             ,   SUBTOPIMG3                                \n");
            sql2.append("             ,   SUBBG4                                    \n");
            sql2.append("             ,   SUBTOPIMG4                                \n");
            sql2.append("             ,   SUBBG5                                    \n");
            sql2.append("             ,   SUBTOPIMG5                                \n");
            sql2.append("             ,   SUBBG6                                    \n");
            sql2.append("             ,   SUBTOPIMG6                                \n");
            sql2.append("             ,   SUBBG7                                    \n");
            sql2.append("             ,   SUBTOPIMG7                                \n");
            sql2.append("             ,   SUBBG8                                    \n");
            sql2.append("             ,   SUBTOPIMG8                                \n");
            sql2.append("             ,   LDATE                                     \n");
            sql2.append("             ,   LUSERID                                   \n");
            sql2.append("         )                                                 \n");
            sql2.append("             SELECT                                        \n");
            sql2.append("                     ?                                     \n");
            sql2.append("                 ,   TYPE                                  \n");
            sql2.append("                 ,   IMGPATH                               \n");
            sql2.append("                 ,   MAINFLASH                             \n");
            sql2.append("                 ,   MENUFLASH                             \n");
            sql2.append("                 ,   MAINBG                                \n");
            sql2.append("                 ,   TOPLOGO                               \n");
            sql2.append("                 ,   TOPBG                                 \n");
            sql2.append("                 ,   FOOTTOPIMG                            \n");
            sql2.append("                 ,   FOOTBG                                \n");
            sql2.append("                 ,   FOOTBGCOLOR1                          \n");
            sql2.append("                 ,   FOOTBGCOLOR2                          \n");
            sql2.append("                 ,   FOOTCOPYRIGHT                         \n");
            sql2.append("                 ,   FOOTLEFTIMG                           \n");
            sql2.append("                 ,   SUBBG1                                \n");
            sql2.append("                 ,   SUBTOPIMG1                            \n");
            sql2.append("                 ,   SUBBG2                                \n");
            sql2.append("                 ,   SUBTOPIMG2                            \n");
            sql2.append("                 ,   SUBBG3                                \n");
            sql2.append("                 ,   SUBTOPIMG3                            \n");
            sql2.append("                 ,   SUBBG4                                \n");
            sql2.append("                 ,   SUBTOPIMG4                            \n");
            sql2.append("                 ,   SUBBG5                                \n");
            sql2.append("                 ,   SUBTOPIMG5                            \n");
            sql2.append("                 ,   SUBBG6                                \n");
            sql2.append("                 ,   SUBTOPIMG6                            \n");
            sql2.append("                 ,   SUBBG7                                \n");
            sql2.append("                 ,   SUBTOPIMG7                            \n");
            sql2.append("                 ,   SUBBG8                                \n");
            sql2.append("                 ,   SUBTOPIMG8                            \n");
            sql2.append("                 ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')   \n");
            sql2.append("                 ,   ?                                     \n");
            sql2.append("               FROM  TZ_TEMPLET_MASTER                     \n");
            sql2.append("              WHERE  TYPE = ?                              \n");
            pstmt2 = connMgr.prepareStatement(sql2.toString());

            pstmt2.setString(1, v_grcode);
            pstmt2.setString(2, v_luserid);
            pstmt2.setString(3, v_type);
            isOk = pstmt2.executeUpdate();

            //파일 생성- 기존파일 삭제
            if (isOk > 0) {
                createIndex(v_grcode, v_type);
            }

        } catch (Exception ex) {
            isOk = 0;
            ErrorManager.getErrorStackTrace(ex, box, sql2.toString());
            throw new Exception("sql2 = " + sql2.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
        return isOk;
    }

    /**
     * 교육그룹별 INDEX 파일 생성 (index+교육그룹코드.jsp)
     * 
     * @param grcode 그룹 코드
     * @param type 템플릿 타입
     **/
    public void createIndex(String grcode, String type) throws Exception {
        File file = null;
        ConfigSet conf = new ConfigSet();
        String home = conf.getProperty("dir.home"); // 생성될 위치
        String filename = "index" + grcode + ".jsp";
        String path = home + filename;
        String contents = "";

        contents = "<%@ page import = \"com.credu.library.*\" %>                                  \n";
        contents += "<%                                                                            \n";
        contents += "    RequestBox box = (RequestBox)request.getAttribute(\"requestbox\");        \n";
        contents += "    if (box == null) box = RequestManager.getBox(request);                    \n";
        contents += "    box.setSession(\"tem_type\",\"" + type + "\");                                \n";
        contents += "    box.setSession(\"tem_grcode\",\"" + grcode + "\");                            \n";
        contents += "%>                                                                            \n";
        contents += "<html>                                                                        \n";
        contents += "<head>                                                                        \n";
        contents += "<Script Language='JavaScript'>                                                \n";
        contents += "<!--                                                                          \n";
        contents += "    document.location.replace(\"/servlet/controller.homepage.MainServlet\");  \n";
        contents += "//-->                                                                         \n";
        contents += "</Script>                                                                     \n";
        contents += "</head>                                                                       \n";
        contents += "<body>                                                                        \n";
        contents += "</body>                                                                       \n";
        contents += "</html>                                                                       \n";

        try {
            // 삭제
            delete(path);
            // 생성
            file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter owriter = new BufferedWriter(fw);
            owriter.write(contents);
            owriter.flush();
            owriter.close();
            fw.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 실제 파일을 삭제한다
     * 
     * @param path 삭제할 파일의 패스+파일명
     * @throws Exception
     */
    public void delete(String path) throws Exception {
        try {
            File file = new File(path);
            file.delete();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 템플릿 상세정보
     * 
     * @param box receive from the form object and session
     * @return DataBox
     */
    public DataBox selectTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_grcode = box.getString("p_grcode");

        sql
                .append(" SELECT /* TempletBean.selectTemplet : 467 */      GRCODE ,TYPE , IMGPATH ,MAINFLASH ,MENUFLASH ,MAINBG ,TOPLOGO ,TOPBG , FOOTTOPIMG\n");
        sql.append("        ,FOOTBG ,FOOTBGCOLOR1 ,FOOTBGCOLOR2 ,FOOTCOPYRIGHT, FOOTLEFTIMG ,SUBBG1 ,SUBTOPIMG1\n");
        sql.append("        ,SUBBG2 ,SUBTOPIMG2 ,SUBBG3 ,SUBTOPIMG3 ,SUBBG4 ,SUBTOPIMG4 ,SUBBG5 ,SUBTOPIMG5\n");
        sql.append("        ,SUBBG6 ,SUBTOPIMG6 ,SUBBG7 ,SUBTOPIMG7 ,SUBBG8 ,SUBTOPIMG8 ,LDATE ,LUSERID\n");
        sql.append(" FROM TZ_TEMPLET\n");
        sql.append(" WHERE GRCODE =  ").append(SQLString.Format(v_grcode));

        try {
            connMgr = new DBConnectionManager();
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
     * 마스터 템플릿 리스트
     * 
     * @param box
     * @return DataBox
     * @throws Exception
     */
    public ArrayList selectMasterTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        ArrayList list = null;

        String v_grcode = box.getString("p_grcode");

        sql.append(" SELECT GUBUN,MENUNAME                  ");
        sql.append(" FROM TZ_HOMEMENU                       ");
        sql.append(" WHERE MENUID = '00' AND KIND = 'SB'    ");
        sql.append("   AND GRCODE = ").append(StringManager.makeSQL(v_grcode));
        sql.append(" ORDER BY GUBUN                     ");

        try {
            list = new ArrayList();

            connMgr = new DBConnectionManager();
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
     * 템플릿 메인 수정할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateTempletMain(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;
        int i = 1;
        String v_grcode = box.getString("p_grcode");
        String v_type = box.getString("p_type");
        String v_imgpath = box.getString("p_imgpath");
        String s_userid = box.getSession("userid");

        String v_footbgcolor1 = box.getString("p_footbgcolor1");
        String v_footbgcolor2 = box.getString("p_footbgcolor2");

        // 업로드된 파일명
        String v_mainbg_old = box.getNewFileName("p_mainbg");
        String v_mainflash_old = box.getNewFileName("p_mainflash");
        String v_menuflash_old = box.getNewFileName("p_menuflash");
        String v_toplogo_old = box.getNewFileName("p_toplogo");
        String v_topbg_old = box.getNewFileName("p_topbg");
        String v_foottopimg_old = box.getNewFileName("p_foottopimg");
        String v_footbg_old = box.getNewFileName("p_footbg");
        String v_footcopyright_old = box.getNewFileName("p_footcopyright");
        String v_footleftimg_old = box.getNewFileName("p_footleftimg");

        // 변경될 파일명
        String v_mainbg_new = v_grcode + "_mainbg.gif";
        String v_mainflash_new = v_grcode + "_mainflash.swf";
        String v_menuflash_new = v_grcode + "_menuflash.swf";
        String v_toplogo_new = v_grcode + "_logo.gif";
        String v_topbg_new = v_grcode + "_topbg.gif";
        String v_foottopimg_new = v_grcode + "_foottopimg.gif";
        String v_footbg_new = v_grcode + "_footbg.gif";
        String v_footcopyright_new = v_grcode + "_footcopyright.gif";
        String v_footleftimg_new = v_grcode + "_footleftimg.gif";

        // 파일 이동
        ConfigSet conf = new ConfigSet();
        String v_thisPath = conf.getProperty("dir.upload.default"); //  이동하기전 경로
        String v_thatPath = conf.getProperty("dir.templet." + v_type); // 이동할 경로

        FileMove filemove = new FileMove();
        if (!v_mainbg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_mainbg_old, v_mainbg_new);
        }
        if (!v_mainflash_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_mainflash_old, v_mainflash_new);
        }
        if (!v_menuflash_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_menuflash_old, v_menuflash_new);
        }
        if (!v_toplogo_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_toplogo_old, v_toplogo_new);
        }
        if (!v_topbg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_topbg_old, v_topbg_new);
        }
        if (!v_foottopimg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_foottopimg_old, v_foottopimg_new);
        }
        if (!v_footbg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_footbg_old, v_footbg_new);
        }
        if (!v_footcopyright_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_footcopyright_old, v_footcopyright_new);
        }
        if (!v_footleftimg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_footleftimg_old, v_footleftimg_new);
        }

        try {
            connMgr = new DBConnectionManager();

            sql.append(" UPDATE TZ_TEMPLET SET  IMGPATH = ? ");

            if (!v_mainbg_old.equals("")) {
                sql.append(" ,MAINBG        =  ? ");
            }
            if (!v_mainflash_old.equals("")) {
                sql.append(" ,MAINFLASH     =  ? ");
            }
            if (!v_menuflash_old.equals("")) {
                sql.append(" ,MENUFLASH     =  ? ");
            }
            if (!v_toplogo_old.equals("")) {
                sql.append(" ,TOPLOGO       =  ? ");
            }
            if (!v_topbg_old.equals("")) {
                sql.append(" ,TOPBG         =  ? ");
            }
            if (!v_foottopimg_old.equals("")) {
                sql.append(" ,FOOTTOPIMG    =  ? ");
            }
            if (!v_footbg_old.equals("")) {
                sql.append(" ,FOOTBG        =  ? ");
            }
            if (!v_footcopyright_old.equals("")) {
                sql.append(" ,FOOTCOPYRIGHT =  ? ");
            }
            if (!v_footleftimg_old.equals("")) {
                sql.append(" ,FOOTLEFTIMG   =  ? ");
            }

            sql.append(" ,FOOTBGCOLOR1 = ?  ,FOOTBGCOLOR2 = ?                           ");
            sql.append(" ,LDATE= TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ,LUSERID = ?       ");
            sql.append(" WHERE GRCODE = ?                                               ");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(i++, v_imgpath);
            if (!v_mainbg_old.equals("")) {
                pstmt.setString(i++, v_mainbg_new);
            }
            if (!v_mainflash_old.equals("")) {
                pstmt.setString(i++, v_mainflash_new);
            }
            if (!v_menuflash_old.equals("")) {
                pstmt.setString(i++, v_menuflash_new);
            }
            if (!v_toplogo_old.equals("")) {
                pstmt.setString(i++, v_toplogo_new);
            }
            if (!v_topbg_old.equals("")) {
                pstmt.setString(i++, v_topbg_new);
            }
            if (!v_foottopimg_old.equals("")) {
                pstmt.setString(i++, v_foottopimg_new);
            }
            if (!v_footbg_old.equals("")) {
                pstmt.setString(i++, v_footbg_new);
            }
            if (!v_footcopyright_old.equals("")) {
                pstmt.setString(i++, v_footcopyright_new);
            }
            if (!v_footleftimg_old.equals("")) {
                pstmt.setString(i++, v_footleftimg_new);
            }
            pstmt.setString(i++, v_footbgcolor1);
            pstmt.setString(i++, v_footbgcolor2);
            pstmt.setString(i++, s_userid);
            pstmt.setString(i++, v_grcode);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * @param box
     * @return
     * @throws Exception
     */
    public int updateSubTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        ListSet ls = null;
        int isOk = 0;
        int i = 1;

        String v_grcode = box.getString("p_grtype");
        //String v_type = box.getString("p_menuid");
        String v_gubun = box.getString("p_gubun");
        String v_orders = box.getString("p_orders");
        String v_menuname = box.getString("p_menuname");
        String v_menuurl = box.getString("p_menuurl");
        String v_menuxposition = box.getString("p_menuxposition");
        //String v_useyn = box.getString("p_useyn");

        // 업로드된 파일명
        String v_menuimg_old = box.getNewFileName("p_menuimg");
        String v_menuoverimg_old = box.getNewFileName("p_menuoverimg");
        String v_subimg_old = box.getNewFileName("p_subimg");
        String v_suboverimg_old = box.getNewFileName("p_suboverimg");
        String v_flashfilename_old = box.getNewFileName("p_flashfilename");
        String v_menuhomeimg_old = box.getNewFileName("p_menuhomeimg");

        //변경될 파일명
        String v_menuimg_new = v_grcode + "_menuimg_off.gif";
        String v_menuoverimg_new = v_grcode + "_menuimg_on.gif";
        String v_subimg_new = v_grcode + "_subimg_off.gif";
        String v_suboverimg_new = v_grcode + "_subimg_on.gif";
        String v_flashfilename_new = v_grcode + "_flash.swf";
        String v_menuhomeimg_new = v_grcode + "_menuhomeimg.gif";

        // 파일 이동
        ConfigSet conf = new ConfigSet();
        String v_thisPath = conf.getProperty("dir.home"); //  이동하기전 경로
        String v_thatPath = v_thisPath + conf.getProperty("dir.templet.submenu1") + v_gubun + "\\"; // 이동할 경로

        FileMove filemove = new FileMove();
        if (!v_menuimg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_menuimg_old, v_menuimg_new);
        }
        if (!v_menuoverimg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_menuoverimg_old, v_menuoverimg_new);
        }
        if (!v_subimg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_subimg_old, v_subimg_new);
        }
        if (!v_suboverimg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_suboverimg_old, v_suboverimg_new);
        }
        if (!v_flashfilename_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_flashfilename_old, v_flashfilename_new);
        }
        if (!v_menuhomeimg_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_menuhomeimg_old, v_menuhomeimg_new);
        }

        try {

            connMgr = new DBConnectionManager();

            sql.append(" SELECT GRCODE FROM TZ_HOMEMENU ");
            sql.append(" WHERE GRCODE = " + SQLString.Format(v_grcode));
            sql.append(" AND GUBUN = " + SQLString.Format(v_gubun));
            sql.append(" AND MENUID = '00' ");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) { // update
                sql.setLength(0);
                sql.append("UPDATE TZ_HOMEMENU ");
                sql.append("SET ORDERS = ? ");
                sql.append(",MENUNAME = ? ");
                sql.append(",MENUURL = ? ");
                sql.append(",LDATE = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ");
                if (!v_menuxposition.equals("")) {
                    sql.append(", MENUXPOSITION = ? ");
                }
                if (!v_menuimg_old.equals("")) {
                    sql.append(",MENUIMG = ? ");
                }
                if (!v_menuoverimg_old.equals("")) {
                    sql.append(",MENUOVERIMG = ? ");
                }
                if (!v_subimg_old.equals("")) {
                    sql.append(",SUBIMG = ? ");
                }
                if (!v_suboverimg_old.equals("")) {
                    sql.append(",SUBOVERIMG = ? ");
                }
                if (!v_flashfilename_old.equals("")) {
                    sql.append(",FLASHFILENAME = ? ");
                }
                if (!v_menuhomeimg_old.equals("")) {
                    sql.append(",MENUHOMEIMG = ? ");
                }
                sql.append(" WHERE GRCODE = ? ");
                sql.append(" AND GUBUN = ? ");
                sql.append(" AND MENUID = '00' ");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(i++, v_orders);
                pstmt.setString(i++, v_menuname);
                pstmt.setString(i++, v_menuurl);
                if (!v_menuxposition.equals("")) {
                    pstmt.setString(i++, v_menuxposition);
                }
                if (!v_menuimg_old.equals("")) {
                    pstmt.setString(i++, v_menuimg_new);
                }
                if (!v_menuoverimg_old.equals("")) {
                    pstmt.setString(i++, v_menuoverimg_new);
                }
                if (!v_subimg_old.equals("")) {
                    pstmt.setString(i++, v_subimg_new);
                }
                if (!v_suboverimg_old.equals("")) {
                    pstmt.setString(i++, v_suboverimg_new);
                }
                if (!v_flashfilename_old.equals("")) {
                    pstmt.setString(i++, v_flashfilename_new);
                }
                if (!v_menuhomeimg_old.equals("")) {
                    pstmt.setString(i++, v_menuhomeimg_new);
                }
                pstmt.setString(i++, v_grcode);
                pstmt.setString(i++, v_gubun);

                isOk = pstmt.executeUpdate();
            } else { // insert
                sql.setLength(0);
                sql.append(" INSERT INTO TZ_HOMEMENU (\n");
                sql.append(" GRCODE,GUBUN,MENUID,KIND,POSITION,ORDERS,MENUNAME,MENUURL,\n");
                sql.append("MENUIMG,MENUOVERIMG,LDATE,LUSERID,SUBIMG,SUBOVERIMG,FLASHFILENAME,\n");
                sql.append("MENUHOMEIMG,MENUXPOSITION)\n");
                sql.append(" VALUES( \n");
                sql.append(" ?,?,'00','SB','',?,?,?,?,?,TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),'',?,?,?,?,? )");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_gubun);
                pstmt.setString(3, v_orders);
                pstmt.setString(4, v_menuname);
                pstmt.setString(5, v_menuurl);
                if (!v_menuimg_old.equals("")) {
                    pstmt.setString(6, v_menuimg_new);
                } else {
                    pstmt.setString(6, "");
                }
                if (!v_menuoverimg_old.equals("")) {
                    pstmt.setString(7, v_menuoverimg_new);
                } else {
                    pstmt.setString(7, "");
                }
                if (!v_subimg_old.equals("")) {
                    pstmt.setString(8, v_subimg_new);
                } else {
                    pstmt.setString(8, "");
                }
                if (!v_suboverimg_old.equals("")) {
                    pstmt.setString(9, v_suboverimg_new);
                } else {
                    pstmt.setString(9, "");
                }
                if (!v_flashfilename_old.equals("")) {
                    pstmt.setString(10, v_flashfilename_new);
                } else {
                    pstmt.setString(10, "");
                }
                if (!v_menuhomeimg_old.equals("")) {
                    pstmt.setString(11, v_menuhomeimg_new);
                } else {
                    pstmt.setString(11, "");
                }
                if (!v_menuxposition.equals("")) {
                    pstmt.setString(12, v_menuxposition);
                } else {
                    pstmt.setString(12, "");
                }

                isOk = pstmt.executeUpdate();
                mainMenuList.clear();
                subMenuList.clear();
                detailMenuInfoList.clear();
                detailMenuInfo.clear();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return isOk;
    }

    /**
     * 템플릿 서브 수정할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateTempletSub(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;
        int i = 1;
        String v_grcode = box.getString("p_grcode");
        String v_type = box.getString("p_type");
        String v_imgpath = box.getString("p_imgpath");
        String s_userid = box.getSession("userid");

        // 업로드된 파일명
        String v_subbg1_old = box.getNewFileName("p_subbg1");
        String v_subtopimg1_old = box.getNewFileName("p_subtopimg1");
        String v_subbg2_old = box.getNewFileName("p_subbg2");
        String v_subtopimg2_old = box.getNewFileName("p_subtopimg2");
        String v_subbg3_old = box.getNewFileName("p_subbg3");
        String v_subtopimg3_old = box.getNewFileName("p_subtopimg3");
        String v_subbg4_old = box.getNewFileName("p_subbg4");
        String v_subtopimg4_old = box.getNewFileName("p_subtopimg4");
        String v_subbg5_old = box.getNewFileName("p_subbg5");
        String v_subtopimg5_old = box.getNewFileName("p_subtopimg5");
        String v_subbg6_old = box.getNewFileName("p_subbg6");
        String v_subtopimg6_old = box.getNewFileName("p_subtopimg6");
        String v_subbg7_old = box.getNewFileName("p_subbg7");
        String v_subtopimg7_old = box.getNewFileName("p_subtopimg7");
        String v_subbg8_old = box.getNewFileName("p_subbg8");
        String v_subtopimg8_old = box.getNewFileName("p_subtopimg8");

        // 변경될 파일명
        String v_subbg1_new = v_grcode + "_subbg1.gif";
        String v_subtopimg1_new = v_grcode + "_subtopimg1.jpg";
        String v_subbg2_new = v_grcode + "_subbg2.gif";
        String v_subtopimg2_new = v_grcode + "_subtopimg2.jpg";
        String v_subbg3_new = v_grcode + "_subbg3.gif";
        String v_subtopimg3_new = v_grcode + "_subtopimg3.jpg";
        String v_subbg4_new = v_grcode + "_subbg4.gif";
        String v_subtopimg4_new = v_grcode + "_subtopimg4.jpg";
        String v_subbg5_new = v_grcode + "_subbg5.gif";
        String v_subtopimg5_new = v_grcode + "_subtopimg5.jpg";
        String v_subbg6_new = v_grcode + "_subbg6.gif";
        String v_subtopimg6_new = v_grcode + "_subtopimg6.jpg";
        String v_subbg7_new = v_grcode + "_subbg7.gif";
        String v_subtopimg7_new = v_grcode + "_subtopimg7.jpg";
        String v_subbg8_new = v_grcode + "_subbg8.gif";
        String v_subtopimg8_new = v_grcode + "_subtopimg8.jpg";

        // 파일 이동
        ConfigSet conf = new ConfigSet();
        //      String v_thisPath = conf.getProperty("dir.home");     //  이동하기전 경로
        //      String v_thatPath = v_thisPath+conf.getProperty("dir.templet.submenu")+gubun + "\\";   // 이동할 경로
        String v_thisPath = conf.getProperty("dir.upload.default"); //  이동하기전 경로
        String v_thatPath = conf.getProperty("dir.templet." + v_type); // 이동할 경로

        FileMove filemove = new FileMove();
        if (!v_subbg1_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_subbg1_old, v_subbg1_new);
        }
        if (!v_subtopimg1_old.equals("")) {
            filemove.move(v_thatPath, v_thisPath, v_subtopimg1_old, v_subtopimg1_new);
        }

        try {
            connMgr = new DBConnectionManager();

            sql.append(" UPDATE TZ_TEMPLET SET  IMGPATH = ? ");

            if (!v_subbg1_old.equals("")) {
                sql.append(" ,SUBBG1     =  ? ");
            }
            if (!v_subtopimg1_old.equals("")) {
                sql.append(" ,SUBTOPIMG1 =  ? ");
            }
            if (!v_subbg2_old.equals("")) {
                sql.append(" ,SUBBG2     =  ? ");
            }
            if (!v_subtopimg2_old.equals("")) {
                sql.append(" ,SUBTOPIMG2 =  ? ");
            }
            if (!v_subbg3_old.equals("")) {
                sql.append(" ,SUBBG3     =  ? ");
            }
            if (!v_subtopimg3_old.equals("")) {
                sql.append(" ,SUBTOPIMG3 =  ? ");
            }
            if (!v_subbg4_old.equals("")) {
                sql.append(" ,SUBBG4     =  ? ");
            }
            if (!v_subtopimg4_old.equals("")) {
                sql.append(" ,SUBTOPIMG4 =  ? ");
            }
            if (!v_subbg5_old.equals("")) {
                sql.append(" ,SUBBG5     =  ? ");
            }
            if (!v_subtopimg5_old.equals("")) {
                sql.append(" ,SUBTOPIMG5 =  ? ");
            }
            if (!v_subbg6_old.equals("")) {
                sql.append(" ,SUBBG6     =  ? ");
            }
            if (!v_subtopimg6_old.equals("")) {
                sql.append(" ,SUBTOPIMG6 =  ? ");
            }
            if (!v_subbg7_old.equals("")) {
                sql.append(" ,SUBBG7     =  ? ");
            }
            if (!v_subtopimg7_old.equals("")) {
                sql.append(" ,SUBTOPIMG7 =  ? ");
            }
            if (!v_subbg8_old.equals("")) {
                sql.append(" ,SUBBG8     =  ? ");
            }
            if (!v_subtopimg8_old.equals("")) {
                sql.append(" ,SUBTOPIMG8 =  ? ");
            }

            sql.append(" ,LDATE= TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ,LUSERID = ? ");
            sql.append(" WHERE GRCODE = ? ");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(i++, v_imgpath);
            if (!v_subbg1_old.equals("")) {
                pstmt.setString(i++, v_subbg1_new);
            }
            if (!v_subtopimg1_old.equals("")) {
                pstmt.setString(i++, v_subtopimg1_new);
            }
            if (!v_subbg2_old.equals("")) {
                pstmt.setString(i++, v_subbg2_new);
            }
            if (!v_subtopimg2_old.equals("")) {
                pstmt.setString(i++, v_subtopimg2_new);
            }
            if (!v_subbg3_old.equals("")) {
                pstmt.setString(i++, v_subbg3_new);
            }
            if (!v_subtopimg3_old.equals("")) {
                pstmt.setString(i++, v_subtopimg3_new);
            }
            if (!v_subbg4_old.equals("")) {
                pstmt.setString(i++, v_subbg4_new);
            }
            if (!v_subtopimg4_old.equals("")) {
                pstmt.setString(i++, v_subtopimg4_new);
            }
            if (!v_subbg5_old.equals("")) {
                pstmt.setString(i++, v_subbg5_new);
            }
            if (!v_subtopimg5_old.equals("")) {
                pstmt.setString(i++, v_subtopimg5_new);
            }
            if (!v_subbg6_old.equals("")) {
                pstmt.setString(i++, v_subbg6_new);
            }
            if (!v_subtopimg6_old.equals("")) {
                pstmt.setString(i++, v_subtopimg6_new);
            }
            if (!v_subbg7_old.equals("")) {
                pstmt.setString(i++, v_subbg7_new);
            }
            if (!v_subtopimg7_old.equals("")) {
                pstmt.setString(i++, v_subtopimg7_new);
            }
            if (!v_subbg8_old.equals("")) {
                pstmt.setString(i++, v_subbg8_new);
            }
            if (!v_subtopimg8_old.equals("")) {
                pstmt.setString(i++, v_subtopimg8_new);
            }
            pstmt.setString(i++, s_userid);
            pstmt.setString(i++, v_grcode);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /* ================ 컨텐츠/메뉴 관련 =================== */

    /**
     * 마스터 메뉴리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 마스터 메뉴리스트
     */
    public ArrayList SelectMenuMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_gubun = box.getString("p_gubun");
        String v_position = box.getString("p_position");
        // String v_grtype = box.getStringDefault("p_grtype",box.getSession("grtype"));

        try {
            list = new ArrayList();

            sql.append(" SELECT GUBUN, MENUID, KIND, POSITION, ORDERS, MENUNAME, MENUURL, MENUIMG,MENUOVERIMG ");
            sql.append("   FROM TZ_HOMEMENU_MASTER         ");
            sql.append("  WHERE USEYN='Y' AND MENUID != '00' AND GUBUN = ").append(SQLString.Format(v_gubun));
            sql.append("    AND POSITION =  ").append(SQLString.Format(v_position));
            //          sql.append("    AND GRTYPE   =  ").append(SQLString.Format(v_grtype));
            sql.append("    AND GRTYPE   =  'ALL' ");
            sql.append("  ORDER BY ORDERS ASC              ");
            connMgr = new DBConnectionManager();

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
     * 선택된 메뉴리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 선택된 메뉴리스트
     */
    public ArrayList SelectMenuList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_grcode = box.getString("p_grcode");
        String v_gubun = box.getString("p_gubun");
        String v_position = box.getString("p_position");

        try {
            list = new ArrayList();

            sql.append(" SELECT GUBUN, MENUID, KIND, POSITION, ORDERS, MENUNAME, MENUURL, MENUIMG,MENUOVERIMG ");
            sql.append("   FROM TZ_HOMEMENU ");
            sql.append("  WHERE GRCODE = ").append(SQLString.Format(v_grcode));
            sql.append("    AND GUBUN  = ").append(SQLString.Format(v_gubun));
            sql.append("    AND POSITION = ").append(SQLString.Format(v_position));
            sql.append("    AND MENUID <> '00' ");
            sql.append("  ORDER BY ORDERS ASC ");

            connMgr = new DBConnectionManager();

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

    public DataBox selectSubMainTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_grcode = box.getString("p_grcode");
        String v_gubun = box.getString("p_gubun");

        try {
            //          if(detailMenuInfoList == null || detailMenuInfoList.size()==0||detailMenuInfoList.get(v_grcode+ "||" + v_gubun)==null) {
            sql
                    .append(" SELECT /* TempletBean.selectSubMainTemplet : 1209 */\n    MENUNAME, MENUURL, MENUIMG, MENUOVERIMG, SUBIMG, SUBOVERIMG, FLASHFILENAME, MENUHOMEIMG,MENUXPOSITION\n");
            sql.append(" FROM TZ_HOMEMENU\n");
            sql.append(" WHERE GRCODE = " + SQLString.Format(v_grcode));
            sql.append("\n   AND GUBUN  = " + SQLString.Format(v_gubun));
            sql.append("\n   AND MENUID = '00'");
            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                dbox = ls.getDataBox();
            }
            detailMenuInfoList.put(v_grcode + "||" + v_gubun, dbox);
            //          }
            //          else dbox=(DataBox)detailMenuInfoList.get(v_grcode+ "||" + v_gubun);
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

    public DataBox selectMasterOneTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_gubun = box.getString("p_gubun");

        try {

            sql.append(" SELECT MENUNAME,MENUURL FROM TZ_HOMEMENU_MASTER ");
            sql.append(" WHERE GUBUN = " + SQLString.Format(v_gubun));
            sql.append(" AND MENUID = '00'");

            connMgr = new DBConnectionManager();

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
     * 선택된 메뉴리스트 조회
     * 
     * @param grcode 교육그룹 코드
     * @param gubun 구분 (MB : MAIN, SB : SUB )
     * @param position 위치
     * @return ArrayList 선택된 메뉴리스트
     */
    public ArrayList SelectMenuList(String grcode, String gubun, String position) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        try {
            list = new ArrayList();

            sql.append(" SELECT GUBUN, MENUID, KIND, POSITION, ORDERS, MENUNAME, MENUURL, MENUIMG,MENUOVERIMG ");
            sql.append("   FROM TZ_HOMEMENU ");
            sql.append("  WHERE GRCODE = ").append(SQLString.Format(grcode));
            sql.append("    AND GUBUN = ").append(SQLString.Format(gubun));
            sql.append("    AND POSITION = ").append(SQLString.Format(position));
            sql.append("  ORDER BY ORDERS ASC ");

            connMgr = new DBConnectionManager();

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
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
     * 교육그룹별 메뉴등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int InsertMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        StringBuffer sql1 = new StringBuffer();
        StringBuffer sql2 = new StringBuffer();
        int isOk = 1;
        int isOk_check = 0;

        String v_grcode = box.getString("p_grcode");
        String v_gubun = box.getString("p_gubun");
        String v_position = box.getString("p_position");
        //      String v_grtype   = box.getStringDefault("p_grtype",box.getSession("grtype"));
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        String v_order = box.getString("p_order");
        String v_menuid = "";
        int v_orders = 0;
        StringTokenizer st = new StringTokenizer(v_order, "|");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // tz_templet initialize
            sql1.append(" DELETE FROM TZ_HOMEMENU WHERE GRCODE=? AND GUBUN = ? AND POSITION = ? AND MENUID <> '00' ");

            pstmt1 = connMgr.prepareStatement(sql1.toString());

            pstmt1.setString(1, v_grcode);
            pstmt1.setString(2, v_gubun);
            pstmt1.setString(3, v_position);

            isOk = pstmt1.executeUpdate();
            isOk = 1;

            // TZ_HOMEMENU insert (TZ_HOMEMENU_MASTER 마스터 정보 COPY)
            sql2.append(" INSERT INTO TZ_HOMEMENU( GRCODE      ,GUBUN        ,MENUID      ,KIND         ,POSITION  ,ORDERS ");
            sql2.append("                         ,MENUNAME    ,MENUURL      ,MENUIMG     ,MENUOVERIMG ");
            sql2.append("                         ,SUBIMG      ,SUBOVERIMG   ,LDATE       ,LUSERID      ) ");
            sql2.append("                 SELECT   ?           ,GUBUN        ,?           ,KIND         ,POSITION  ,ORDERS ");
            sql2
                    .append("                         ,MENUNAME    ,MENUURL      ,MENUIMG     ,MENUOVERIMG  ,SUBIMG    ,SUBOVERIMG  ,TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ,? ");
            sql2.append("                   FROM  TZ_HOMEMENU_MASTER ");
            sql2.append("                  WHERE  GRTYPE = ?  AND GUBUN = ? AND POSITION = ? AND MENUID = ?");

            pstmt2 = connMgr.prepareStatement(sql2.toString());

            int index = 1;
            while (st.hasMoreTokens()) {
                index = 1;
                v_menuid = st.nextToken();
                v_orders++;

                pstmt2.setString(index++, v_grcode);
                pstmt2.setString(index++, v_menuid);
                //pstmt2.setString(index++, String.valueOf(v_orders));
                pstmt2.setString(index++, v_luserid);
                pstmt2.setString(index++, "ALL");
                pstmt2.setString(index++, v_gubun);
                pstmt2.setString(index++, v_position);
                pstmt2.setString(index++, v_menuid);

                isOk_check = pstmt2.executeUpdate();

                if (isOk_check == 0) {
                    isOk = 0;
                }
                mainMenuList.clear();
                subMenuList.clear();
                detailMenuInfoList.clear();
                detailMenuInfo.clear();
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2.toString());
            throw new Exception("sql2 = " + sql2.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
            connMgr.setAutoCommit(true);
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        resetMenuList(v_grcode);
        return isOk;
    }

    /* ================ 홈페이지 관련 =================== */

    /**
     * 템플릿 상세정보
     * 
     * @param p_grcode 교육그룹코드
     * @return DataBox 템플릿 정보
     */
    public static DataBox getTemplet(String p_grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        try {
            if (detailMenuInfo == null || detailMenuInfo.size() == 0 || detailMenuInfo.get(p_grcode) == null) {
                sql
                        .append(" SELECT /* TempletBean.getTemplet : 1427 */\n      GRCODE ,TYPE , IMGPATH ,MAINFLASH ,MENUFLASH ,MAINBG ,TOPLOGO ,TOPBG , FOOTTOPIMG\n");
                sql.append("        ,FOOTBG ,FOOTBGCOLOR1 ,FOOTBGCOLOR2 ,FOOTCOPYRIGHT, FOOTLEFTIMG ,SUBBG1 ,SUBTOPIMG1\n");
                sql.append("        ,SUBBG2 ,SUBTOPIMG2 ,SUBBG3 ,SUBTOPIMG3 ,SUBBG4 ,SUBTOPIMG4 ,SUBBG5 ,SUBTOPIMG5\n");
                sql.append("        ,SUBBG6 ,SUBTOPIMG6 ,SUBBG7 ,SUBTOPIMG7 ,SUBBG8 ,SUBTOPIMG8 ,LDATE ,LUSERID\n");
                sql.append("   FROM TZ_TEMPLET\n");
                sql.append("  WHERE GRCODE =  ").append(SQLString.Format(p_grcode));
                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql.toString());

                if (ls.next()) {
                    dbox = ls.getDataBox();
                }
                if (detailMenuInfo == null)
                    detailMenuInfo = new HashMap();
                detailMenuInfo.put(p_grcode, dbox);
            } else
                dbox = (DataBox) detailMenuInfo.get(p_grcode);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
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
     * 서브화면 메뉴 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 메뉴 리스트
     */
    public static ArrayList getMenuList(String grcode, String gubun, String position) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql.append(" SELECT GUBUN, MENUID, KIND, POSITION, ORDERS, MENUNAME, MENUURL, MENUIMG, MENUOVERIMG,MENUXPOSITION ");
            sql.append("   FROM TZ_HOMEMENU              ");
            sql.append("  WHERE GRCODE   = ").append(SQLString.Format(grcode));
            sql.append("    AND GUBUN    = ").append(SQLString.Format(gubun));
            sql.append("    AND POSITION = ").append(SQLString.Format(position));
            sql.append("  ORDER BY ORDERS ASC              ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
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
     * 마스터 메뉴리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 마스터 메뉴리스트
     */
    public ArrayList SelectMainMenuMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        //String v_position = box.getString("p_position");

        try {
            list = new ArrayList();

            sql.append(" SELECT GUBUN, MENUID, KIND, POSITION, ORDERS, MENUNAME, MENUURL, MENUIMG,MENUOVERIMG ");
            sql.append("   FROM TZ_HOMEMENU_MASTER ");
            sql.append("  WHERE USEYN='Y' AND MENUID = '00' ");
            sql.append("    AND GUBUN <> '0' ");
            //          sql.append("    AND POSITION =  ").append(SQLString.Format(v_position));
            sql.append("    AND GRTYPE   =  'ALL' ");
            sql.append("  ORDER BY ORDERS ASC ");
            connMgr = new DBConnectionManager();

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
     * 선택된 메뉴리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 선택된 메뉴리스트
     */
    public ArrayList SelectMainMenuList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_grcode = box.getString("p_grcode");

        try {
            list = new ArrayList();

            sql.append(" SELECT GUBUN, MENUID, KIND, POSITION, ORDERS, MENUNAME, MENUURL, MENUIMG,MENUOVERIMG ");
            sql.append("   FROM TZ_HOMEMENU ");
            sql.append("  WHERE GRCODE = ").append(SQLString.Format(v_grcode));
            sql.append("    AND MENUID = '00' ");
            sql.append("  ORDER BY ORDERS ASC ");

            connMgr = new DBConnectionManager();

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
     * 교육그룹별 메뉴등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int InsertMainMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        StringBuffer sql1 = new StringBuffer();
        StringBuffer sql2 = new StringBuffer();
        int isOk = 1;
        int isOk_check = 0;

        String v_grcode = box.getString("p_grcode");
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        String v_order = box.getString("p_order");
        // String v_menuid   = "";
        String v_gubun = "";
        StringTokenizer st = new StringTokenizer(v_order, "|");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // tz_templet initialize
            sql1.append(" DELETE FROM TZ_HOMEMENU WHERE GRCODE=? AND MENUID = '00' ");
            pstmt1 = connMgr.prepareStatement(sql1.toString());
            pstmt1.setString(1, v_grcode);
            isOk = pstmt1.executeUpdate();

            isOk = 1;

            // TZ_HOMEMENU insert (TZ_HOMEMENU_MASTER 마스터 정보 COPY)
            sql2.append(" INSERT INTO TZ_HOMEMENU( GRCODE      ,GUBUN        ,MENUID        ,KIND         ,POSITION ");
            sql2.append("                         ,MENUNAME    ,MENUURL      ,MENUIMG       ,MENUOVERIMG ");
            sql2.append("                         ,SUBIMG      ,SUBOVERIMG   ,FLASHFILENAME ,MENUHOMEIMG  ,LDATE     ,LUSERID      ) ");
            sql2.append("                  SELECT  ?           ,GUBUN        ,?             ,KIND         ,POSITION ");
            sql2
                    .append("                         ,MENUNAME    ,MENUURL      ,MENUIMG       ,MENUOVERIMG  ,SUBIMG    ,SUBOVERIMG    ,FLASHFILENAME  ,MENUHOMEIMG  ,TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')  ,?  ");
            sql2.append("                    FROM TZ_HOMEMENU_MASTER ");
            sql2.append("                   WHERE GRTYPE = ?   AND MENUID = ? AND GUBUN <> '0' AND GUBUN = ? ");

            pstmt2 = connMgr.prepareStatement(sql2.toString());

            while (st.hasMoreTokens()) {
                v_gubun = st.nextToken();

                pstmt2.setString(1, v_grcode);
                pstmt2.setString(2, "00");
                pstmt2.setString(3, v_luserid);
                //pstmt2.setString(5, v_grtype);
                pstmt2.setString(4, "ALL");
                pstmt2.setString(5, "00");
                pstmt2.setString(6, v_gubun);

                isOk_check = pstmt2.executeUpdate();

                if (isOk_check == 0) {
                    isOk = 0;
                }
            }
            mainMenuList.clear();

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2.toString());
            throw new Exception("sql2 = " + sql2.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
            connMgr.setAutoCommit(true);
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        resetMenuList(v_grcode);
        return isOk;
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox SelectGrcodeExists(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        String p_servernm = box.get("p_servernm");
        String grcode = box.getSession("tem_grcode");

        try {
            sql.append(" /* TempletBean.SelectGrcodeExists (교육그룹 유무 확인) */ \n");
            sql.append(" SELECT \n");
            sql.append("        A.GRCODE    \n");
            sql.append("    ,   B.TYPE      \n");
            sql.append("    ,   A.MENUTYPE  \n");
            sql.append("  FROM  TZ_GRCODE A LEFT JOIN TZ_TEMPLET B  \n");
            sql.append("    ON  A.GRCODE = B.GRCODE \n");
            sql.append("  WHERE USEYN = 'Y' \n");

            if (grcode.equals("")) {
                sql.append("  AND DOMAIN LIKE '%").append(p_servernm).append("%'  \n");
                sql.append("   OR B2BDOMAIN LIKE  '%").append(p_servernm).append("%'    \n");
            } else {
                sql.append("  AND A.GRCODE='").append(grcode).append("' \n");
            }

            connMgr = new DBConnectionManager();

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
     * 방송통신심의위원회에서 userid를 받아와 pwd를 가져온다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox getKocscSsoPwd(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        String v_userid = box.getString("p_userid");

        try {

            sql.append(" SELECT GRCODE, USERID, NAME, PWD  ");
            sql.append(" FROM TZ_MEMBER ");
            sql.append(" WHERE USERID = ").append(StringManager.makeSQL(v_userid));

            connMgr = new DBConnectionManager();

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
     * 사내연수원에서 userid를 받아와 pwd를 가져온다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox getEdu1SsoPwd(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        String v_userid = box.getString("p_userid");

        try {

            sql.append(" SELECT GRCODE, USERID, NAME, PWD  ");
            sql.append(" FROM TZ_MEMBER ");
            sql.append(" WHERE USERID = ").append(StringManager.makeSQL(v_userid));

            connMgr = new DBConnectionManager();

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
     * 홈앤쇼핑에서 userid를 확인하여 없으면 가입시키고 있으면 pwd가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox checkHnsUserid(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        // StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();
        PreparedStatement pstmt = null;

        String v_userid = box.getString("hns_userid");

        try {

            sql.append(" SELECT GRCODE, USERID, NAME, PWD  ");
            sql.append(" FROM TZ_MEMBER ");
            sql.append(" WHERE USERID = ").append(StringManager.makeSQL(v_userid));

            connMgr = new DBConnectionManager();

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            } else {

                String v_name = box.getString("hns_name");
                String v_birthday = box.getString("hns_birthday");
                String v_memberyear = v_birthday.substring(0, 4);
                String v_membermonth = v_birthday.substring(4, 6);
                String v_memberday = v_birthday.substring(6, 8);
                String v_sex = box.getString("hns_sex");
                String v_pwd = box.getString("hns_pwd");
                String v_tel = box.getString("hns_tel");
                String v_handphone = box.getString("hns_handphone");
                String v_email = box.getString("hns_email");

                // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
                // v_pwd = encryptUtil.encrypt(v_pwd);
                v_pwd = HashCipher.createHash(v_pwd);
                // v_email = encryptUtil.encrypt(v_email);
                // v_tel = encryptUtil.encrypt(v_tel);
                // v_handphone = encryptUtil.encrypt(v_handphone);

                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);
                sql.append("INSERT INTO TZ_MEMBER (    \n");
                sql.append("        NAME        \n");
                sql.append("    ,   MEMBERYEAR  \n");
                sql.append("    ,   MEMBERMONTH \n");
                sql.append("    ,   MEMBERDAY   \n");
                sql.append("    ,   SEX         \n");
                sql.append("    ,   USERID      \n");
                sql.append("    ,   PWD         \n");
                sql.append("    ,   HOMETEL     \n");
                sql.append("    ,   HANDPHONE   \n");
                sql.append("    ,   EMAIL       \n");
                sql.append("    ,   INDATE      \n");
                sql.append("    ,   LDATE       \n");
                sql.append("    ,   GRCODE      \n");
                sql.append("    )   \n");
                sql.append("    VALUES (    \n");
                sql.append("        ?   \n");
                sql.append("    ,   ?   \n");
                sql.append("    ,   ?   \n");
                sql.append("    ,   ?   \n");
                sql.append("    ,   ?   \n");
                sql.append("    ,   ?   \n");
                sql.append("    ,   ?   \n");
                sql.append("    ,   crypto.enc('normal', ?) \n");
                sql.append("    ,   crypto.enc('normal', ?) \n");
                sql.append("    ,   crypto.enc('normal', ?) \n");
                sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
                sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
                sql.append("    ,   ? ) \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(1, v_name);
                pstmt.setString(2, v_memberyear);
                pstmt.setString(3, v_membermonth);
                pstmt.setString(4, v_memberday);
                pstmt.setString(5, v_sex);
                pstmt.setString(6, v_userid);
                pstmt.setString(7, v_pwd);
                pstmt.setString(8, v_tel);
                pstmt.setString(9, v_handphone);
                pstmt.setString(10, v_email);
                pstmt.setString(11, "N000055");

                int isOk = pstmt.executeUpdate();

                if (isOk != 0) {
                    box.put("isOk", String.valueOf(isOk));
                    box.setSession("userid", v_userid);
                    box.setSession("name", v_name);
                }

                if (isOk == 1) {
                    connMgr.commit();

                    sql.setLength(0);

                    sql.append(" SELECT GRCODE, USERID, NAME, PWD  ");
                    sql.append(" FROM TZ_MEMBER ");
                    sql.append(" WHERE USERID = ").append(StringManager.makeSQL(v_userid));

                    connMgr = new DBConnectionManager();

                    ls = connMgr.executeQuery(sql.toString());

                    if (ls.next()) {
                        dbox = ls.getDataBox();
                    }
                } else {
                    connMgr.rollback();
                }
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
     * 한국문화콘텐츠고등학교에서 userid를 확인하여 없으면 가입시키고 있으면 pwd가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox checkKocucohi(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        String sql1 = "";
        PreparedStatement pstmt = null;

        String v_userid = "kocuco_" + box.getString("kocuco_userid");

        try {
            sql.append(" SELECT GRCODE, USERID, NAME, PWD  ");
            sql.append(" FROM TZ_MEMBER ");
            sql.append(" WHERE USERID = ").append(StringManager.makeSQL(v_userid));
            sql.append("       AND GRCODE = 'N000083'");

            connMgr = new DBConnectionManager();

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                dbox = ls.getDataBox();
            } else {
                String v_name = box.getString("kocuco_name");
                String v_id = box.getString("kocuco_id").substring(0, 1);
                String v_memberyear = "";
                if (v_id.equals("1")) {
                    v_memberyear = "16";
                } else if (v_id.equals("2")) {
                    v_memberyear = "17";
                } else if (v_id.equals("3")) {
                    v_memberyear = "18";
                } else {
                    v_memberyear = "13";
                }
                String v_membermonth = "01";
                String v_memberday = "01";
                String v_sex = "2"; // 문화콘텐츠고등학교 => 여고임 전부 여성
                String v_pwd = box.getString("kocuco_userid");
                String v_handphone = "";
                String v_hometel = "";
                String v_email = box.getStringDefault("kocuco_email", "@");

                String tem_tel = box.getStringDefault("kocuco_tel", "--").trim().substring(0, 2);
                if (!tem_tel.equals("--") && !tem_tel.equals("")) {
                    if (tem_tel.equals("01")) {
                        v_handphone = box.getString("kocuco_tel");
                        v_hometel = "02-0000-0000";
                    } else {
                        v_handphone = "010-0000-0000";
                        v_hometel = box.getString("kocuco_tel");
                    }
                } else {
                    v_handphone = "010-0000-0000";
                    v_hometel = "02-0000-0000";
                }

                // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
                // v_pwd = encryptUtil.encrypt(v_pwd);
                v_pwd = HashCipher.createHash(v_pwd);

                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);
                sql1 = " insert into TZ_MEMBER( \n";
                sql1 += " userid,                               name,                   pwd,                        memberyear, \n";
                sql1 += " membermonth,                          memberday,              handphone,                  hometel, \n";
                sql1 += " comptel,                              email,                  sex,                        indate, \n";
                sql1 += " ldate,                                    grcode,                 state,                      ismailing) \n";
                sql1 += " values ( \n";
                sql1 += " ?,                                        ?,                      ?,                          to_char(sysdate,'YYYY')-?, \n";
                sql1 += " ?,                                        ?,                      crypto.enc('normal',?),     crypto.enc('normal',?), \n";
                sql1 += " ?,                                        crypto.enc('normal',?), ?,                          to_char(sysdate, 'YYYYMMDDHH24MISS'), \n";
                sql1 += " to_char(sysdate,'YYYYMMDDHH24MISS'),  'N000083',              'Y',                        'Y' \n";
                sql1 += " )";

                pstmt = connMgr.prepareStatement(sql1);

                pstmt.setString(1, v_userid);
                pstmt.setString(2, v_name);
                pstmt.setString(3, v_pwd);
                pstmt.setString(4, v_memberyear);
                pstmt.setString(5, v_membermonth);
                pstmt.setString(6, v_memberday);
                pstmt.setString(7, v_handphone);
                pstmt.setString(8, v_hometel);
                pstmt.setString(9, v_hometel);
                pstmt.setString(10, v_email);
                pstmt.setString(11, v_sex);

                int isOk = pstmt.executeUpdate();

                if (isOk != 0) {
                    box.put("isOk", String.valueOf(isOk));
                    box.setSession("userid", v_userid);
                    box.setSession("name", v_name);
                }

                if (isOk == 1) {
                    connMgr.commit();

                    sql = new StringBuffer();
                    sql.append(" SELECT GRCODE, USERID, NAME, PWD  ");
                    sql.append(" FROM TZ_MEMBER ");
                    sql.append(" WHERE USERID = ").append(StringManager.makeSQL(v_userid));
                    sql.append("       AND GRCODE = 'N000083'");

                    connMgr = new DBConnectionManager();

                    ls = connMgr.executeQuery(sql.toString());

                    if (ls.next()) {
                        dbox = ls.getDataBox();
                    }
                } else {
                    connMgr.rollback();
                }
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

}