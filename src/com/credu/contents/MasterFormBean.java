//**********************************************************
//  1. ��      ��: �������� ���� OPERATION BEAN
//  2. ���α׷���: MasterFormBean.java
//  3. ��      ��: �������� ���� BEAN
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 0.1
//  6. ��      ��: S.W.Kang 2004. 12. 5
//  7. ��      ��:
//**********************************************************
package com.credu.contents;

import java.io.File;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.common.GetCodenm;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

@SuppressWarnings("unchecked")
public class MasterFormBean {

    private static final String FILE_TYPE = "p_file"; //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 1; //    �������� ���õ� ����÷�� ����

    public MasterFormBean() {
    }

    /**
     * ������������Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������������Ʈ
     */
    public ArrayList SelectMasterFormList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls2 = null;
        ArrayList list1 = null;
        String sql = "";
        MasterFormListData data = null;

        String ss_upperclass = box.getString("s_upperclass");
        String ss_middleclass = box.getString("s_middleclass");
        String ss_lowerclass = box.getString("s_lowerclass");
        String ss_contenttype = box.getString("s_contenttype");
        String ss_area = box.getString("s_area");
        String p_order = box.getString("p_order");
        String ss_inputbox = box.getString("s_inputbox");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select a.subj, a.subjnm, a.iscentered, a.dir, a.isuse, a.contenttype,a.SERVER,a.PORT,a.EDUURL,\n"
                    + "       a.mftype, a.width, a.height, a.eduprocess, a.otbgcolor, a.isvisible, a.isoutsourcing, a.area, \n"
                    + "       (select count(subj) from tz_subjlesson where subj=a.subj) cnt_lesson, b.codenm mftypenm\n"
                    + "  from tz_subj a, tz_code b\n" + " where  a.isonoff='ON' AND B.gubun(+)='0005' and code(+)=a.mftype\n";
            if (!ss_upperclass.equals("ALL") && !ss_upperclass.equals(""))
                sql += "\n and a.upperclass=" + StringManager.makeSQL(ss_upperclass);
            if (!ss_middleclass.equals("ALL") && !ss_middleclass.equals(""))
                sql += "\n and a.middleclass=" + StringManager.makeSQL(ss_middleclass);
            if (!ss_lowerclass.equals("ALL") && !ss_lowerclass.equals(""))
                sql += "\n and a.lowerclass=" + StringManager.makeSQL(ss_lowerclass);
            if (!ss_contenttype.equals("ALL") && !ss_contenttype.equals(""))
                sql += "\n and a.contenttype=" + StringManager.makeSQL(ss_contenttype);
            if (!ss_area.equals("ALL") && !ss_area.equals(""))
                sql += "\n and a.area=" + StringManager.makeSQL(ss_area);
            if (!ss_inputbox.equals("ss_inputbox") && !ss_inputbox.equals(""))
                sql += "\n and a.subjnm like '%" + ss_inputbox + "%'";

            sql += " order by ";
            if (p_order.equals("subj") || p_order.equals(""))
                sql += "subj";
            if (p_order.equals("subjnm"))
                sql += "subjnm";
            if (p_order.equals("dir"))
                sql += "dir";
            if (p_order.equals("branch"))
                sql += "ismfbranch";
            if (p_order.equals("center"))
                sql += "iscentered";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new MasterFormListData();
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setServer(ls.getString("server"));
                data.setPort(ls.getString("port"));
                data.setEduurl(ls.getString("eduurl"));
                data.setIsuse(ls.getString("isuse"));
                data.setIscentered(ls.getString("iscentered"));
                data.setDir(ls.getString("dir"));
                data.setCnt_lesson(ls.getInt("cnt_lesson"));
                data.setContenttype(ls.getString("contenttype"));
                data.setMftype(ls.getString("mftype"));
                data.setWidth(ls.getString("width"));
                data.setHeight(ls.getString("height"));
                data.setEduprocess(ls.getString("eduprocess"));
                data.setOtbgcolor(ls.getString("otbgcolor"));
                data.setIsvisible(ls.getString("isvisible"));
                data.setIsoutsourcing(ls.getString("isoutsourcing"));

                if (data.getContenttype().equals("N"))
                    data.setContenttypenm("Normal(NEW)");
                else if (data.getContenttype().equals("M"))
                    data.setContenttypenm("Normal(OLD)");
                else if (data.getContenttype().equals("O"))
                    data.setContenttypenm("OBC");
                else if (data.getContenttype().equals("S"))
                    data.setContenttypenm("SCORM");
                else if (data.getContenttype().equals("L"))
                    data.setContenttypenm("LINK");

                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
        return list1;
    }

    /**
     * ������������ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return MasterFormData �������� ����
     */
    public MasterFormData SelectMasterFormData(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ListSet ls2 = null;

        ArrayList list1 = null;

        MasterFormData data = null;

        StringBuilder sql = new StringBuilder();

        String unchangableMaxLesson = "001"; //����/���� �Ұ� �ִ�����

        String p_subj = box.getString("p_subj");
        String p_process = box.getString("p_process");

        try {

            connMgr = new DBConnectionManager();

            list1 = new ArrayList();

            // sql.append(" SELECT /* MasterForm,Bean:141 */\n       SUBJ, SUBJNM, ISCENTERED, DIR, ISUSE, MFTYPE, WIDTH, HEIGHT, MFDLIST, \n");
            // sql.append("       OTBGCOLOR, MFGRDATE, ISNULL(CONTENTTYPE,'N') CONTENTTYPE, \n"); //OBC tree����, module��¿���(Normal MsForm)
            // sql.append("       SERVER,PORT,EDUURL,PREURL,VODURL,EDUPROCESS,OTBGCOLOR, ISOUTSOURCING, \n");
            // sql.append("       (SELECT RTRIM(LTRIM(CODENM)) VALS FROM TZ_CODE WHERE GUBUN='0007' AND CODE=A.CONTENTTYPE) CONTENTTYPETXT \n");
            // sql.append("   FROM TZ_SUBJ A \n");
            // sql.append("  WHERE SUBJ = " + StringManager.makeSQL(p_subj) + " \n");

            sql.append(" SELECT /* MasterForm ��ȸ (MasterFormBean.SelectMasterFormData) */   \n");
            sql.append("        SUBJ                                                \n");
            sql.append("    ,   SUBJNM                                              \n");
            sql.append("    ,   ISCENTERED                                          \n");
            sql.append("    ,   DIR                                                 \n");
            sql.append("    ,   ISUSE                                               \n");
            sql.append("    ,   MFTYPE                                              \n");
            sql.append("    ,   WIDTH                                               \n");
            sql.append("    ,   HEIGHT                                              \n");
            sql.append("    ,   MFDLIST                                             \n");
            sql.append("    ,   OTBGCOLOR                                           \n");
            sql.append("    ,   MFGRDATE                                            \n");
            sql.append("    ,   ISNULL(CONTENTTYPE,'N') CONTENTTYPE                 \n");
            sql.append("    ,   SERVER                                              \n");
            sql.append("    ,   PORT                                                \n");
            sql.append("    ,   EDUURL                                              \n");
            sql.append("    ,   PREURL                                              \n");
            sql.append("    ,   VODURL                                              \n");
            sql.append("    ,   EDUPROCESS                                          \n");
            sql.append("    ,   ISOUTSOURCING                                       \n");
            sql.append("    ,   GET_CODENM('0007', A.CONTENTTYPE) AS CONTENTTYPETXT \n");
            sql.append("  FROM  TZ_SUBJ A                                           \n");
            sql.append(" WHERE  SUBJ = '").append(p_subj).append("'                 \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                data = new MasterFormData();

                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setIscentered(ls.getString("iscentered"));
                data.setDir(ls.getString("dir"));
                data.setMftype(ls.getString("mftype"));
                data.setWidth(ls.getInt("width"));
                data.setHeight(ls.getInt("height"));
                data.setMfdlist(ls.getString("mfdlist"));
                data.setOtbgcolor(ls.getString("otbgcolor"));
                data.setMfgrdate(ls.getString("mfgrdate"));
                data.setContenttype(ls.getString("contenttype"));
                data.setServer(ls.getString("server"));
                data.setPort(ls.getString("port"));
                data.setEduurl(ls.getString("eduurl"));
                data.setPreurl(ls.getString("preurl"));
                data.setVodurl(ls.getString("vodurl"));
                data.setEduprocess(ls.getString("eduprocess"));
                data.setOtbgcolor(ls.getString("otbgcolor"));
                data.setIsoutsourcing(ls.getString("isoutsourcing"));
                data.setContenttypetxt(ls.getString("contenttypetxt"));

                if (p_process.equals("updateModulePage")) {
                    sql.setLength(0);
                    sql.append(" SELECT COUNT(MODULE) CNTS  \n");
                    sql.append("   FROM TZ_SUBJMODULE       \n");
                    sql.append("  WHERE SUBJ = '").append(p_subj).append("' \n");
                    
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                    ls2 = connMgr.executeQuery(sql.toString());
                    ls2.next();
                    data.setCnt_module(ls2.getInt("CNTS"));
                }
// todo
System.out.println("============================== COUNT(LESSON) ");
                sql.setLength(0);
                sql.append(" SELECT COUNT(LESSON) CNTS FROM TZ_SUBJLESSON  WHERE  SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
                
                if (ls2 != null) {
                    try {
                        ls2.close();
                    } catch (Exception e) {
                    }
                }
                ls2 = connMgr.executeQuery(sql.toString());
                ls2.next();
                data.setCnt_lesson(ls2.getInt("CNTS"));

                if (p_process.equals("updateLessonPage")) {
                    sql.setLength(0);
                    sql.append(" SELECT MAX(LESSON) LS FROM TZ_PROGRESS  WHERE  SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                    ls2 = connMgr.executeQuery(sql.toString());
                    if (ls2.next() && (!ls2.getString("LS").equals("")))
                        unchangableMaxLesson = ls2.getString("LS");

                    sql.setLength(0);
                    sql.append(" SELECT MAX(LESSON) LS FROM TZ_EXAMMASTER  WHERE  SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
                    sql.append("    AND LESSON > " + StringManager.makeSQL(unchangableMaxLesson) + " \n");
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                    ls2 = connMgr.executeQuery(sql.toString());
                    if (ls2.next() && (!ls2.getString("LS").equals("")))
                        unchangableMaxLesson = ls2.getString("LS");

                    sql.setLength(0);
                    sql.append(" SELECT MAX(LESSON) LS FROM TZ_PROJORD  WHERE  SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
                    sql.append("    AND LESSON > " + StringManager.makeSQL(unchangableMaxLesson) + " \n");
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                    ls2 = connMgr.executeQuery(sql.toString());
                    if (ls2.next() && (!ls2.getString("LS").equals("")))
                        unchangableMaxLesson = ls2.getString("LS");

                }

                data.setUnchangableMaxLesson(unchangableMaxLesson);

                if (p_process.equals("updateBranchPage") && (data.getCnt_branch() > 0)) {
                    sql.setLength(0);
                    sql.append(" SELECT COUNT(BRANCH) LS FROM TZ_BRANCH  WHERE  SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                    ls2 = connMgr.executeQuery(sql.toString());
                    if (ls2.next())
                        data.setCnt_branch(ls2.getInt("LS"));
                }

                list1.add(data);
            }

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }

            //�������ɿ��� üũ
            data.setCanModify(canModify(connMgr, p_subj, "MAIN", ""));

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
            if (ls2 != null) {
                try {
                    ls2.close();
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

        return data;
    }

    /**
     * �������� �����͸޴�����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� �����͸޴�����Ʈ
     */
    public ArrayList SelectMfMenuList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList list1 = null;//, list2=null;
        String sql = "";//, sql2="";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select menu, menunm, isrequired, pgm, pgmtype,pgram1, pgram2, " + "       pgram3, pgram4, pgram5, luserid, ldate "
                    + "  from tz_mfmenu  " + " order by isrequired desc, menu asc ";
            System.out.println(sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
        return list1;
    }

    /**
     * �������� �����޴�����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� �����޴�����Ʈ
     */
    public ArrayList SelectMfSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        ArrayList list1 = null;

        StringBuffer sql = new StringBuffer();

        MfSubjData data = null;

        String p_subj = box.getString("p_subj");

        try {

            connMgr = new DBConnectionManager();

            list1 = new ArrayList();

            sql.append(" SELECT A.MENU, A.MENUNM, B.ISREQUIRED, A.PGM, A.PGMTYPE, A.PGRAM1, A.PGRAM2, \n");
            sql.append("        A.PGRAM3, A.PGRAM4, A.PGRAM5, A.ORDERS, A.LUSERID, A.LDATE \n");
            sql.append("   FROM TZ_MFSUBJ A, TZ_MFMENU B \n");
            sql.append("  WHERE A.MENU=B.MENU AND A.SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
            sql.append("  ORDER BY A.ORDERS \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data = new MfSubjData();

                data.setMenu(ls.getString("menu"));
                data.setMenunm(ls.getString("menunm"));
                data.setIsrequired(ls.getString("isrequired"));
                data.setPgm(ls.getString("pgm"));
                data.setPgmtype(ls.getString("pgmtype"));
                data.setPgram1(ls.getString("pgram1"));
                data.setPgram2(ls.getString("pgram2"));
                data.setPgram3(ls.getString("pgram3"));
                data.setPgram4(ls.getString("pgram4"));
                data.setPgram5(ls.getString("pgram5"));
                data.setOrders(ls.getInt("orders"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));

                list1.add(data);
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

        return list1;
    }

    /**
     * ������������ ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateMasterForm(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        ConfigSet conf = new ConfigSet();
        String sql = "";
        //        String  results     ="";
        int isOk = 0, j = 0;
        int v_cnt = 0;

        String p_subj = box.getString("p_subj");
        //        String p_mftype     = box.getString("p_mftype");
        //        int    p_width      = box.getInt("p_width");
        //        int    p_height     = box.getInt("p_height");
        //        String p_iscentered = box.getString("p_iscentered");
        String p_menus = box.getString("p_menus");
        //        String p_otbgcolor  = box.getString("p_ptbgcolor");

        String v_luserid = box.getSession("userid");
        //String v_luserid      = "detante";

        String siteCategory = "";

        siteCategory = conf.getProperty("dir.home") + "contents/" + p_subj + "/docs/menuimg/";

        File srcDir = new File(siteCategory);

        try {
            connMgr = new DBConnectionManager();

            if (!canModify(connMgr, p_subj, "MAIN", ""))
                return isOk;
            connMgr.setAutoCommit(false);

            //insert TZ_Grseq table
            sql = "update TZ_subj set " + " mftype=?, width=?, height=?, eduprocess=?, iscentered=?,"
                    + " Luserid     =?, LDATE      =to_char(sysdate,'YYYYMMDDHH24MISS'), otbgcolor=? " + " where subj=?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, box.getString("p_mftype"));
            pstmt.setInt(2, box.getInt("p_width"));
            pstmt.setInt(3, box.getInt("p_height"));
            pstmt.setString(4, box.getString("p_eduprocess"));
            pstmt.setString(5, box.getString("p_iscentered"));
            pstmt.setString(6, v_luserid);
            pstmt.setString(7, box.getString("p_otbgcolor"));
            pstmt.setString(8, p_subj);
            isOk = pstmt.executeUpdate();
            connMgr.commit();
            //Logging.
            Log.info.println(this, "update TZ_SUBJ(masterForm) :" + p_subj + ". isOk=" + isOk);

            if (isOk == 1) {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                    }
                }
                sql = "select count(*) from tz_mfsubj where subj='" + p_subj + "'";
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    v_cnt = ls.getInt(1);
                }
                ls.close();
                if (v_cnt > 0) {
                    sql = "delete from tz_mfsubj where subj=?";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, p_subj);
                    isOk = pstmt.executeUpdate();
                }

                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (Exception e) {
                    }
                }
                String v_menu = "";
                sql = "insert into tz_mfsubj (subj, menu, menunm, "
                        + " pgm, pgmtype, pgram1, pgram2, pgram3, pgram4, pgram5, orders, luserid, ldate) "
                        + " select ?,menu,menunm,pgm,pgmtype,pgram1,pgram2,pgram3,pgram4,pgram5,?,"
                        + "        ?, to_char(sysdate,'YYYYMMDDHH24MISS') " + "   from tz_mfmenu where menu=?";
                pstmt = connMgr.prepareStatement(sql);
                for (int i = 0; i < (p_menus.length() / 2); i++) {
                    j++;
                    v_menu = p_menus.substring(i * 2, i * 2 + 2);
                    pstmt.setString(1, p_subj);
                    pstmt.setInt(2, j);
                    pstmt.setString(3, v_luserid);
                    pstmt.setString(4, v_menu);
                    isOk = pstmt.executeUpdate();
                }
                if (srcDir.isDirectory() == false) {
                    srcDir.mkdirs();
                }
                //if (srcDir2.isDirectory() == false) {
                //  srcDir2.mkdirs();
                //}

                //if (srcDir.isDirectory() == false) {
                //  srcDir.mkdirs();
                //FILE MOVE & UNZIP
                //System.out.println("�������Դϴ�.");
                //  results = this.controlObjectFile("insert",p_subj, box);
                //}
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    /**
     * �������� Module����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Module����Ʈ
     */
    public ArrayList SelectMfModuleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql = "";
        DataBox dbox = null;

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select a.module,a.sdesc,a.types , count(b.lesson) cnt_lesson" + " from tz_subjmodule  a, tz_subjlesson b " + "where a.subj="
                    + StringManager.makeSQL(p_subj)
                    //+ "  and a.subj = b.subj(+) and a.module = b.module(+) "
                    + "  and a.subj  =  b.subj(+) and a.module  =  b.module(+) " + "group by a.module, a.sdesc, a.types " + "order by a.module";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {

                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list1;
    }

    /**
     * �������� Module����Ʈ 2009.10.23 �߰�
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���縮��Ʈ
     */
    public ArrayList SelectMfTutorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql = "";
        DataBox dbox = null;

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "SELECT ST.USERID, TT.NAME" + "  FROM TZ_SUBJTUTOR ST, TZ_TUTOR TT" + " WHERE ST.USERID = TT.USERID" + "   AND ST.SUBJ   ="
                    + StringManager.makeSQL(p_subj) + " ORDER BY SEQ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {

                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list1;
    }

    /**
     * �������� Module����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Module����Ʈ
     */
    public ArrayList SelectScoLocateView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql = "";
        SCOData data = null;

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select distinct scotitle , scolocate from tz_object " + " where oid in " + "   ( select oid from tz_subjobj where subj = "
                    + StringManager.makeSQL(p_subj) + ") ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new SCOData();
                data.setScolocate(ls.getString("scolocate"));
                data.setScoTitle(ls.getString("scotitle"));
                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list1;
    }

    /**
     * �������� Module���� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateMasterFormModule(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1, i = 0;

        String p_subj = box.getString("p_subj");
        Vector vc_module = box.getVector("p_module");
        Vector vc_sdesc = box.getVector("p_sdesc");
        String v_module = "";
        String v_sdesc = "";
        String v_maxmodule = "";

        //String v_luserid      = box.getSession("userid");
        String v_luserid = "icarus";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "delete tz_subjmodule  " + "  where  subj=" + StringManager.makeSQL(p_subj);
            isOk = connMgr.executeUpdate(sql);
            /*
             * //insert TZ_Grseq table sql =
             * "select module from tz_subjmodule where subj="
             * +StringManager.makeSQL(p_subj) + " order by module"; ls =
             * connMgr.executeQuery(sql); while(ls.next()){ v_module =
             * (String)vc_module.elementAt(i); v_sdesc =
             * (String)vc_sdesc.elementAt(i); // �Է��� ������ space�̸� ������� ����, �ƴϸ�
             * update if(!v_sdesc.equals("")){ sql =
             * "update tz_subjmodule set luserid="
             * +StringManager.makeSQL(v_luserid) +
             * ", ldate=to_char(sysdate,'YYYYMMDDHH24MISS') " +
             * ", sdesc="+StringManager.makeSQL(v_sdesc) +
             * "  where  subj="+StringManager.makeSQL(p_subj) +
             * "    and  module="+StringManager.makeSQL(v_module); v_maxmodule =
             * v_module; }else{ sql = "delete tz_subjmodule  " +
             * "  where  subj="+StringManager.makeSQL(p_subj) +
             * "    and  module="+StringManager.makeSQL(v_module); } isOk =
             * connMgr.executeUpdate(sql); i++; }
             */
            /**
             * TODO : tz_subjmodule.types�� �뵵 �Ҹ��̸鼭 �׻� 1001�� ����. ���� ����!!!
             * TZ_SUBJLESSON.TYPES�� ������Ÿ���̴�. �׷��� ���� 1001�� ����.
             */
            // if(isOk==1){
            sql = "insert into tz_subjmodule (subj,module,sdesc,types,luserid,ldate) values "
                    + "(?,ltrim(rtrim(?)),ltrim(rtrim(?)),'1001',?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt = connMgr.prepareStatement(sql);

            for (int j = i; j < vc_module.size(); j++) {
                v_sdesc = (String) vc_sdesc.elementAt(j);
                if (!v_sdesc.equals("")) {
                    v_module = (String) vc_module.elementAt(j);
                    v_maxmodule = v_module;

                    pstmt.setString(1, p_subj);
                    pstmt.setString(2, v_module);
                    pstmt.setString(3, v_sdesc);
                    pstmt.setString(4, v_luserid);

                    isOk = pstmt.executeUpdate();
                }
            }
            //}

            //Module ������ lesson�� ����� Module�� �����Ѵ�. (�����ϴ� �ִ� Module�� set)
            sql = "update tz_subjlesson set module=" + StringManager.makeSQL(v_maxmodule)
                    + "   , ldate=to_char(sysdate,'YYYYMMDDHH24MISS'), luserid=" + StringManager.makeSQL(v_luserid) + " where subj="
                    + StringManager.makeSQL(p_subj) + "   and module >" + StringManager.makeSQL(v_maxmodule);
            isOk = connMgr.executeUpdate(sql);
            isOk = 1;
            connMgr.commit();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.setAutoCommit(true);
            if (isOk > 0) {
                connMgr.commit();
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * �������� Lesson����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Lesson����Ʈ
     */
    public ArrayList SelectMfLessonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null;
        ArrayList list1 = null;
        String sql = "";
        MfLessonData data = null;

        String p_subj = box.getString("p_subj");
        String temp_contenttype = box.getString("temp_contenttype");
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select module,lesson,userid,sdesc,types,owner,starting,isbranch,mobile_url,new_mobile_url,subtitle, "
                    + "       (select userid from tz_subjtutor where seq = 1 and subj=" + StringManager.makeSQL(p_subj) + ") repuserid" //2009.10.26 �߰�
                    + "  from tz_subjlesson  " + " where subj=" + StringManager.makeSQL(p_subj) + " order by to_number(lesson)";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new MfLessonData();
                data.setModule(ls.getString("module"));
                data.setLesson(ls.getString("lesson"));
                data.setUserid(ls.getString("userid"));
                data.setSdesc(ls.getString("sdesc"));
                data.setTypes(ls.getString("types"));
                data.setOwner(ls.getString("owner"));
                data.setStarting(ls.getString("starting"));
                data.setIsbranch(ls.getString("isbranch"));
                data.setRepuserid(ls.getString("repuserid"));
                //data.setMobile_url(ls.getString("mobile_url"));
                data.setMobile_url(ls.getString("new_mobile_url"));
                data.setSubTitle(ls.getString("subtitle"));
                /* OBC �̸� Object List ���� */
                if (temp_contenttype.equals("O") || temp_contenttype.equals("S")) {
                    sql = "select distinct a.oid, a.sdesc, starting, a.type, a.types, npage, a.ordering, "
                            + "       (select count(*) from tz_previewobj c where c.oid=a.oid and c.subj="
                            + StringManager.makeSQL(p_subj)
                            + ") cntPreivew, "
                            //                        + "       level as scothelevel, "
                            + "       0 as scothelevel, " + "   a.module , a.lesson " + "  from tz_subjobj a, tz_object b " + " where a.subj="
                            + StringManager.makeSQL(p_subj) + "   and a.lesson=" + StringManager.makeSQL(data.getLesson())
                            + "   and a.oid = b.oid "
                            //                        + "   connect by b.highoid = prior b.oid start with b.highoid is null "
                            + "   order by a.module asc , a.lesson asc , a.ordering asc , a.oid asc ";
                    //+ " order by ordering ";

                    if (ls1 != null) {
                        try {
                            ls1.close();
                        } catch (Exception e) {
                        }
                    }
                    ls1 = connMgr.executeQuery(sql);
                    while (ls1.next()) {
                        MfLessonDataSub ds = new MfLessonDataSub();
                        ds.setNpage(ls1.getInt("npage"));
                        ds.setOrdering(ls1.getInt("ordering"));
                        if (ls1.getInt("cntPreivew") > 0)
                            ds.setIsPreviewed("Y");
                        ds.setOid(ls1.getString("oid"));
                        ds.setSdesc(ls1.getString("sdesc"));
                        ds.setStarting(ls1.getString("starting"));
                        ds.setType(ls1.getString("type"));
                        ds.setTypes(ls1.getString("types"));
                        ds.setScothelevel(ls1.getInt("scothelevel"));
                        data.makeSub(ds);
                    }
                }
                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list1;
    }

    /**
     * �������� Lesson���� ���� 2009.10.23 userid �������� �߰�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateMasterFormLesson(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1, i = 0;

        String p_subj = box.getString("p_subj");
        Vector vc_lesson = box.getVector("p_lesson");
        Vector vc_userid = box.getVector("p_userid"); //����
        Vector vc_sdesc = box.getVector("p_sdesc");
        Vector vc_module = box.getVector("p_module");
        Vector vc_owner = box.getVector("p_owner");
        Vector vc_starting = box.getVector("p_starting");
        Vector vc_isbranch = box.getVector("p_isbranch");
        Vector vc_mobile_url = box.getVector("p_mobile_url");
        Vector vc_subtitle = box.getVector("p_subtitle");

        String v_lesson = "";
        String v_userid = "";
        String v_sdesc = "";
        String v_module = "";
        String v_owner = "";
        String v_starting = "";
        String v_isbranch = "";
        String v_mobile_url = "";
        String v_subtitle = "";
        //        MasterFormData mfd  =
        new MasterFormBean().SelectMasterFormData(box);
        boolean canDelete = true; //�������ɿ���
        String v_luserid = box.getSession("userid");
        
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ����Ұ� �����̸� ����Ұ�ó��
            canDelete = canModify(connMgr, p_subj, "Main", "");

            sql = "delete tz_subjlesson  " + "  where  subj=" + StringManager.makeSQL(p_subj);
            isOk = connMgr.executeUpdate(sql);

            /*
             * //insert TZ_Grseq table sql =
             * "select lesson from tz_subjlesson where subj="
             * +StringManager.makeSQL(p_subj) + " order by lesson"; ls =
             * connMgr.executeQuery(sql); while(ls.next()){ v_lesson =
             * (String)vc_lesson.elementAt(i); v_userid =
             * (String)vc_userid.elementAt(i); v_sdesc =
             * (String)vc_sdesc.elementAt(i); v_module =
             * (String)vc_module.elementAt(i); v_owner =
             * (String)vc_owner.elementAt(i); v_starting =
             * (String)vc_starting.elementAt(i);
             * 
             * if(vc_isbranch==null||vc_isbranch.size()==0) v_isbranch = "N";
             * else v_isbranch = (String)vc_isbranch.elementAt(i);
             * 
             * // �Է��� ������ space�̸� Lesson���� ����, �ƴϸ� update
             * if(!v_sdesc.equals("")){ sql =
             * "update tz_subjlesson set luserid="
             * +StringManager.makeSQL(v_luserid) +
             * ", ldate=to_char(sysdate,'YYYYMMDDHH24MISS') " +
             * ", userid="+StringManager.makeSQL(v_userid) +
             * ", sdesc="+StringManager.makeSQL(v_sdesc) +
             * ", owner="+StringManager.makeSQL(v_owner) +
             * ", starting="+StringManager.makeSQL(v_starting) +
             * ", module="+StringManager.makeSQL(v_module) +
             * ", isbranch="+StringManager.makeSQL(v_isbranch) +
             * "  where  subj="+StringManager.makeSQL(p_subj) +
             * "    and  lesson="+StringManager.makeSQL(v_lesson); //isOk =
             * connMgr.executeUpdate(sql); }else{ if(canDelete){ sql =
             * "delete tz_subjlesson  " +
             * "  where  subj="+StringManager.makeSQL(p_subj) +
             * "    and  lesson="+StringManager.makeSQL(v_lesson); //isOk =
             * connMgr.executeUpdate(sql); } }
             * 
             * isOk = connMgr.executeUpdate(sql); i++; }
             */ 

            //if(vc_lesson.size()>0&&isOk>0){
            sql = "insert into tz_subjlesson (subj,module,lesson,sdesc,types,owner,starting,isbranch,luserid,userid,ldate,new_mobile_url,subtitle) values "
                    + "(?,?,?,ltrim(rtrim(?)),'1001',?,ltrim(rtrim(?)),?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),ltrim(rtrim(?)),?)";

            pstmt = connMgr.prepareStatement(sql); 

            for (int j = i; j < vc_lesson.size(); j++) {
                v_sdesc = (String) vc_sdesc.elementAt(j);
                v_subtitle = StringUtil.removeTag((String)vc_subtitle.elementAt(j));

                if (!v_sdesc.equals("")) {
                    if (vc_isbranch == null || vc_isbranch.size() == 0)
                        v_isbranch = "N";
                    else
                        v_isbranch = (String) vc_isbranch.elementAt(j);

                    pstmt.setString(1, p_subj);
                    pstmt.setString(2, (String) vc_module.elementAt(j));
                    pstmt.setString(3, (String) vc_lesson.elementAt(j));
                    pstmt.setString(4, v_sdesc);
                    pstmt.setString(5, (String) vc_owner.elementAt(j));
                    pstmt.setString(6, (String) vc_starting.elementAt(j));
                    pstmt.setString(7, v_isbranch);
                    pstmt.setString(8, v_luserid);
                    pstmt.setString(9, (String) vc_userid.elementAt(j));
                    pstmt.setString(10, (String) vc_mobile_url.elementAt(j));
                    pstmt.setCharacterStream(11, new StringReader(v_subtitle), v_subtitle.length());
                    //                      isOk = controlLessonBranch(connMgr, p_subj, mfd.getServer(), mfd.getPort(), mfd.getDir(), v_lesson, (String)vc_isbranch.elementAt(j), v_luserid); //������ �б����� ����/����
                    isOk = pstmt.executeUpdate();

                }
            }
            //}
            connMgr.commit();

            //Lesson ������ lesson�� ����� �򰡸�����,������,����,��Ƽ��Ƽ,����Ʈ,������ ������ ����?

        } catch (Exception ex) {
            connMgr.rollback();
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.setAutoCommit(true);
            if (isOk > 0) {
                connMgr.commit();
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * Module Select-List�����
     * 
     * @param String tz_config.name
     * @return String tz_config.vals
     */
    public static String make_moduleSelect(ArrayList modules, String p_module) throws Exception {
        String results = "";
        DataBox dbox1 = null;

        for (int i = 0; i < modules.size(); i++) {
            dbox1 = (DataBox) modules.get(i);
            results += "<option value='" + dbox1.getString("d_module") + "' ";
            if (dbox1.getString("d_module").equals(p_module))
                results += " selected ";
            results += ">" + dbox1.getString("d_sdesc") + "</option>";
        }
        return results;
    }

    /**
     * ���� Select-List�����
     * 
     * @param String tz_config.name
     * @return String tz_config.vals
     */
    public static String make_tutorSelect(ArrayList tutors, String p_tutor) throws Exception {
        String results = "";
        DataBox dbox1 = null;

        for (int i = 0; i < tutors.size(); i++) {
            dbox1 = (DataBox) tutors.get(i);
            results += "<option value='" + dbox1.getString("d_userid") + "' ";
            if (dbox1.getString("d_userid").equals(p_tutor))
                results += " selected ";
            results += ">" + dbox1.getString("d_name") + "</option>";
        }
        return results;
    }

    /**
     * �������� �������� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Lesson����Ʈ
     */
    public ArrayList SelectMasterFormDate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.subj, a.module, a.lesson, a.sdesc,                       ";
            sql += "        isnull(b.fromdate,'') fromdate, isnull(b.todate,'') todate ";
            sql += "   from tz_subjlesson a , tz_subjlessondate b                      ";
            sql += "  where a.subj  =  b.subj(+)                                           ";
            sql += "    and a.module  =  b.module(+)                                       ";
            sql += "    and a.lesson  =  b.lesson(+)                                       ";
            sql += "    and a.subj = " + StringManager.makeSQL(p_subj);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * �������� �������� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateMasterFormDate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;

        String p_subj = box.getString("p_subj");
        Vector vc_module = box.getVector("p_module");
        Vector vc_lesson = box.getVector("p_lesson");
        Vector vc_fromdate = box.getVector("p_fromdate");
        Vector vc_todate = box.getVector("p_todate");

        String v_lesson = "";
        //        String  v_sdesc     = "";
        String v_module = "";
        String v_fromdate = "";
        String v_todate = "";
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //delete tz_subjlessondate
            sql1 = "delete tz_subjlessondate where  subj=" + StringManager.makeSQL(p_subj);
            isOk = connMgr.executeUpdate(sql1);
            //insert tz_subjlessondate
            sql2 = " insert into tz_subjlessondate (subj,module,lesson,fromdate,todate,luserid,ldate)     ";
            sql2 += "                        values (?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt = connMgr.prepareStatement(sql2);

            for (int i = 0; i < vc_lesson.size(); i++) {
                v_module = (String) vc_module.elementAt(i);
                v_lesson = (String) vc_lesson.elementAt(i);
                v_fromdate = (String) vc_fromdate.elementAt(i);
                v_todate = (String) vc_todate.elementAt(i);

                if (v_fromdate.equals(""))
                    v_fromdate = "0";
                if (v_todate.equals(""))
                    v_todate = "0";

                pstmt.setString(1, p_subj);
                pstmt.setString(2, v_module);
                pstmt.setString(3, v_lesson);
                pstmt.setString(4, v_fromdate);
                pstmt.setString(5, v_todate);
                pstmt.setString(6, v_luserid);
                isOk = pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            connMgr.setAutoCommit(true);
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
        return isOk;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////// Added by Leesumin  2004.02.23 Starts (to End of file)
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Lesson�� ����� Object ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object ����Ʈ
     */
    public ArrayList SelectSubjObjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null;
        ArrayList list1 = null;
        String sql = "";
        MfLessonDataSub ds = null;

        String p_subj = box.getString("p_subj");
        String p_lesson = box.getString("p_lesson");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select a.oid, a.sdesc, starting, a.type, a.types, npage, a.ordering, "
                    + "       (select count(*) from tz_previewobj c where c.oid=a.oid and c.subj=" + StringManager.makeSQL(p_subj)
                    + ") cntPreivew " + "  from tz_subjobj a, tz_object b " + " where a.subj=" + StringManager.makeSQL(p_subj) + "   and a.lesson="
                    + StringManager.makeSQL(p_lesson) + "   and a.oid = b.oid " + " order by ordering ";
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                ds = new MfLessonDataSub();
                ds.setNpage(ls1.getInt("npage"));
                ds.setOrdering(ls1.getInt("ordering"));
                if (ls1.getInt("cntPreivew") > 0)
                    ds.setIsPreviewed("Y");
                ds.setOid(ls1.getString("oid"));
                ds.setSdesc(ls1.getString("sdesc"));
                ds.setStarting(ls1.getString("starting"));
                ds.setType(ls1.getString("type"));
                ds.setTypes(ls1.getString("types"));

                list1.add(ds);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * �������� Lesson�� Object�������� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:save success,0:save fail
     **/
    public int SaveSubjObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;//,ls2=null;
        //        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1, i = 0, j = 1;

        String p_subj = box.getString("p_subj");
        String p_module = box.getString("p_module");
        String p_lesson = box.getString("p_lesson");
        String p_objstr = box.getString("p_objstr");
        String v_oid = "";

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(*) CNTS from tz_progress " + " where subj=" + StringManager.makeSQL(p_subj) + "   and lesson="
                    + StringManager.makeSQL(p_lesson);
            ls = connMgr.executeQuery(sql);
            ls.next();
            if (ls.getInt("CNTS") > 0)
                return -99;

            // ���� �������� ����
            sql = "delete from tz_subjobj " + " where subj=" + StringManager.makeSQL(p_subj) + "   and lesson=" + StringManager.makeSQL(p_lesson);
            isOk = connMgr.executeUpdate(sql);

            if (isOk >= 0) {
                while (i < p_objstr.length()) {
                    try {
                        v_oid = p_objstr.substring(i, (i + 10));

                    } catch (Exception se) {
                        break;
                    }
                    sql = "insert into tz_subjobj " + " ( subj,module,lesson,oid,ordering,type,sdesc,types,luserid,ldate ) " + "select "
                            + StringManager.makeSQL(p_subj) + "," + StringManager.makeSQL(p_module) + "," + StringManager.makeSQL(p_lesson)
                            + ", oid, " + j + ", 'SC'," + "sdesc,'1001'," + StringManager.makeSQL(v_luserid)
                            + ",to_char(sysdate,'YYYYMMDDHH24MISS') " + " from tz_object where oid=" + StringManager.makeSQL(v_oid);
                    isOk = connMgr.executeUpdate(sql);
                    j++;
                    i += 10;
                }
            }
            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
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
        return isOk;
    }

    /**
     * �������� Lesson�� Object�������� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:save success,0:save fail
     **/
    public int SavePreviewObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;//,ls2=null;
        PreparedStatement pstmt = null;
        String sql = "";//, sql2 = "";
        int isOk = 1, i = 0;//, j =1;

        String p_subj = box.getString("p_subj");
        String v_oid = "";
        Vector vc_oid = box.getVector("p_previewobj");

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ���� �������� ����
            sql = "delete from tz_previewobj where subj=" + StringManager.makeSQL(p_subj);
            isOk = connMgr.executeUpdate(sql);

            //if(isOk>=0){
            for (i = 0; i < vc_oid.size(); i++) {
                v_oid = vc_oid.elementAt(i).toString();
                //sql = "select count(*) CNTS from tz_previewobj "
                //    + " where subj="+StringManager.makeSQL(p_subj)
                //    + "   and oid  ="+StringManager.makeSQL(v_oid);
                //
                //if(ls != null) { try { ls.close(); }catch (Exception e) {} }
                //ls = connMgr.executeQuery(sql);
                //ls.next();
                //if(ls.getInt("CNTS")==0){
                //    sql = "insert into tz_previewobj "
                //    + " ( subj,oid,oidnm,ordering,luserid,ldate ) "
                //    + "select "
                //    + StringManager.makeSQL(p_subj)+","
                //    + " oid, sdesc, "+ (i+1) +", "
                //    + StringManager.makeSQL(v_luserid)+",to_char(sysdate,'YYYYMMDDHH24MISS') "
                //    + " from tz_object where oid="+ StringManager.makeSQL(v_oid);
                //System.out.println("sql===>" + sql);
                //    isOk=connMgr.executeUpdate(sql);
                //}

                sql = " insert into tz_previewobj                     \n";
                sql += " ( subj,oid,oidnm,ordering,luserid,ldate )             \n";

                sql += " select subj, oid, oidnm, ordering, luserid, ldate from (  \n";
                sql += "  select '" + p_subj + "' subj,                            \n";
                sql += "         oid,                                          \n";
                sql += "         sdesc oidnm,                                 \n";
                sql += "         " + (i + 1) + " ordering,                         \n";
                sql += "         '" + v_luserid + "' luserid,                  \n";
                sql += "         to_char(sysdate,'YYYYMMDDHH24MISS') ldate     \n";
                sql += "  from tz_object where oid='" + v_oid + "'                 \n";
                sql += "  union                                                    \n";
                sql += "  select '" + p_subj + "' subj,                            \n";
                sql += "         oid,                                          \n";
                sql += "         sdesc oidnm,                                 \n";
                sql += "         " + (i + 1) + " ordering,                         \n";
                sql += "         '" + v_luserid + "' luserid,                  \n";
                sql += "         to_char(sysdate,'YYYYMMDDHH24MISS') ldate     \n";
                sql += "  from tz_object where highoid='" + v_oid + "'             \n";
                sql += "  union                                                    \n";
                sql += "  select '" + p_subj + "' subj,                            \n";
                sql += "         oid,                                          \n";
                sql += "         sdesc oidnm,                                 \n";
                sql += "         " + (i + 1) + " ordering,                         \n";
                sql += "         '" + v_luserid + "' luserid,                  \n";
                sql += "         to_char(sysdate,'YYYYMMDDHH24MISS') ldate     \n";
                sql += "  from tz_object where oid = (select highoid from tz_object where oid='" + v_oid + "') ";
                sql += " ) a ";
                sql += " where not exists(select * from tz_previewobj where subj = a.subj and oid = a.oid) ";
                System.out.println("sql===>" + sql);
                isOk = connMgr.executeUpdate(sql);

                //for Oracle
                //sql = "update tz_previewobj set  ordering = rownum where subj = '" + p_subj + "' ";
                //isOk=connMgr.executeUpdate(sql);

            }
            //}
            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
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
                } catch (Exception e10) {
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
     * Lesson�� ����� Object �Ӽ�
     * 
     * @param box receive from the form object and session
     * @return
     */
    public MfLessonDataSub SelectLessonObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1 = null;
        //        ArrayList list1 = null;
        String sql = "";
        MfLessonDataSub ds = null;

        String p_subj = box.getString("p_subj");
        String p_lesson = box.getString("p_lesson");
        String p_oid = box.getString("p_oid");

        try {
            connMgr = new DBConnectionManager();
            //            list1 = new ArrayList();

            // tz_subjobj���� branch=99�� �⺻ Branch Object List�� Query.
            sql = " select oid, sdesc, types " + "   from  tz_subjobj  " + "  where subj=" + StringManager.makeSQL(p_subj) + "    and lesson="
                    + StringManager.makeSQL(p_lesson) + "    and oid =" + StringManager.makeSQL(p_oid);

            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                ds = new MfLessonDataSub();
                ds.setOid(ls1.getString("oid"));
                ds.setSdesc(ls1.getString("sdesc"));
                ds.setTypes(ls1.getString("types"));
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return ds;
    }

    /**
     * ���� : Lesson�� ����� Object �Ӽ� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:save success,0:save fail
     **/
    public int SaveLessonObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;//,ls2=null;
        //        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 1;//, i=0, j =1;

        String p_subj = box.getString("p_subj");
        String p_lesson = box.getString("p_lesson");
        String p_oid = box.getString("p_oid");
        //        String v_luserid      = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            // ���� �������� ����
            sql = "update tz_subjobj set " + "           sdesc= " + StringManager.makeSQL(box.getString("p_sdesc")) + "           ,types= "
                    + StringManager.makeSQL(box.getString("p_types")) + " where subj=" + StringManager.makeSQL(p_subj) + "   and lesson="
                    + StringManager.makeSQL(p_lesson) + "   and oid=" + p_oid;

            isOk = connMgr.executeUpdate(sql);

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
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
        return isOk;
    }

    /**
     * [OBC] Lesson ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:save success,0:save fail
     **/
    public int DeleteOBCLesson(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;//,ls2=null;
        String sql = "";
        int isOk = 1, i = 0;//, j =1;
        int v_cnt = 0;

        String p_subj = box.getString("p_subj");
        String p_lesson = box.getString("p_lesson");
        String v_sqlstr = " subj='" + p_subj + "' and lesson='" + p_lesson + "' ";
        //        String  v_lesson_from   = "";
        //        String  v_lesson_to     = "";

        //        String  v_luserid      = box.getSession("userid");
        String[] va_table = { "tz_subjobj", "tz_subjlesson" };

        if (!GetCodenm.canChangeSubj(p_subj, "CA", null))
            return 0;

        try {
            connMgr = new DBConnectionManager();

            // Lesson-Object-Branch �������� ����
            /*
             * sql = "delete from tz_subjbranch where "+ v_sqlstr; isOk =
             * connMgr.executeUpdate(sql);
             * 
             * // Lesson-Object �������� ���� sql = "delete from tz_subjobj where "+
             * v_sqlstr; isOk = connMgr.executeUpdate(sql);
             * 
             * // Lesson���� ���� sql = "delete from tz_subjlesson where "+
             * v_sqlstr; isOk = connMgr.executeUpdate(sql);
             */

            for (i = 0; i <= 1; i++) {

                sql = "select count(*) from " + va_table[i] + " where " + v_sqlstr;
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    v_cnt = ls.getInt(1);
                }
                if (v_cnt > 0) {
                    sql = "delete from " + va_table[i] + " where " + v_sqlstr;
                    isOk = connMgr.executeUpdate(sql);
                }
                //System.out.println(isOk+"==> " + sql);
            }

            if (isOk > 0) {
                for (i = 0; i <= 1; i++) {
                    //sql = "update " + va_table[i] + " set lesson=to_char((to_number(lesson)-1),'00')"
                    sql = "update " + va_table[i] + " set lesson=to_char((to_number(lesson)-1),'00')" + " where subj="
                            + StringManager.makeSQL(p_subj) + "   and lesson > " + StringManager.makeSQL(p_lesson);
                    isOk = connMgr.executeUpdate(sql);
                    //System.out.println(isOk+"==> " + sql);
                }
                /*
                 * sql = "select count(*) CNTS from tz_subjlesson " +
                 * " where  subj="+StringManager.makeSQL(p_subj) +
                 * "   and  lesson >"+StringManager.makeSQL(p_lesson); ls =
                 * connMgr.executeQuery(sql); ls.next(); if
                 * (ls.getInt("CNTS")>0){ sql =
                 * "select lesson, to_char((to_number(lesson)-1),'00') preLesson"
                 * + "  from tz_subjlesson " +
                 * " where  subj="+StringManager.makeSQL(p_subj) +
                 * "   and  lesson >"+StringManager.makeSQL(p_lesson) +
                 * " order by lesson"; ls2 = connMgr.executeQuery(sql);
                 * while(ls2.next()){ v_lesson_from = ls2.getString("lesson");
                 * v_lesson_to = ls2.getString("preLesson"); sql =
                 * "update tz_subjbranch set lesson="
                 * +StringManager.makeSQL(v_lesson_to) +
                 * " where subj="+StringManager.makeSQL(p_subj) +
                 * "   and lesson="+StringManager.makeSQL(v_lesson_from); isOk =
                 * connMgr.executeUpdate(sql);
                 * 
                 * sql =
                 * "update tz_subjobj set lesson="+StringManager.makeSQL(v_lesson_to
                 * ) + " where subj="+StringManager.makeSQL(p_subj) +
                 * "   and lesson="+StringManager.makeSQL(v_lesson_from); isOk =
                 * connMgr.executeUpdate(sql);
                 * 
                 * sql =
                 * "update tz_subjlesson set lesson="+StringManager.makeSQL
                 * (v_lesson_to) + " where subj="+StringManager.makeSQL(p_subj)
                 * + "   and lesson="+StringManager.makeSQL(v_lesson_from); isOk
                 * = connMgr.executeUpdate(sql); }
                 * 
                 * }
                 */
            }
            connMgr.commit();
            isOk = 1;
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
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
        return isOk;
    }

    public String get_contenttype(DBConnectionManager connMgr, String p_subj) throws Exception {
        ListSet ls = null;
        String sql = "";
        String results = "";

        try {
            sql = "select contenttype from tz_subj where subj=" + StringManager.makeSQL(p_subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                results = ls.getString("contenttype");
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return results;

    }

    /***
     * MasterForm���� ������ �������ɿ��� üũ.******************* p_gubun p_sub example =
     * ============================================================== MAIN
     * LESSON lesson "01" BRANCH branch "3" OBJECT-ASSIGN lesson||oid
     * "011000000236"
     ******************************************************************/
    public boolean canModify(DBConnectionManager connMgr, String p_subj, String p_gubun, String p_sub) throws Exception {

        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        boolean resultb = true;

        int cnts = 0;

        try {
            /*
             * if(p_gubun.equals("MAIN")){ sql.setLength(0);
             * sql.append(" SELECT COUNT(*) CNTS FROM TZ_PROGRESS WHERE SUBJ = "
             * + StringManager.makeSQL(p_subj) ); } else
             * if(p_gubun.equals("LESSON")){ sql.setLength(0);
             * sql.append(" SELECT COUNT(*) CNTS FROM TZ_PROGRESS WHERE SUBJ = "
             * + StringManager.makeSQL(p_subj) + " \n");
             * sql.append("    AND LESSON = " + StringManager.makeSQL(p_sub) +
             * " \n"); } else if(p_gubun.equals("BRANCH")){ sql.setLength(0);
             * sql.append(" SELECT COUNT(*) CNTS FROM TZ_PROGRESS WHERE SUBJ = "
             * + StringManager.makeSQL(p_subj) + " \n");
             * sql.append("    AND BRANCH = " + p_sub + " \n"); } else
             * if(p_gubun.equals("OBJECT-ASSIGN")){ //OBC: Object�������� �������ɿ���
             * sql.setLength(0);
             * sql.append(" SELECT COUNT(*) CNTS FROM TZ_PROGRESS WHERE SUBJ = "
             * +StringManager.makeSQL(p_subj) + " \n");
             * sql.append("    AND LESSON="
             * +StringManager.makeSQL(p_sub.substring(0,2)) + " \n");
             * sql.append(
             * "    AND OID="+StringManager.makeSQL(p_sub.substring(2,12)) +
             * " \n"); }
             * 
             * if(!sql.equals("")){ if(ls != null) { try { ls.close(); }catch
             * (Exception e) {} }
             * 
             * ls = connMgr.executeQuery( sql.toString() );
             * 
             * ls.next();
             * 
             * cnts = ls.getInt("CNTS");
             * 
             * if(cnts>0) resultb = false; }
             */
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        //���� ���� ���� ����
        resultb = true;
        return resultb;

    }

    /***
     * MasterForm���� ������ �������ɿ��� üũ.******************* p_gubun p_sub example =
     * ============================================================== (+) * MAIN
     * LESSON lesson "01" BRANCH branch "3" OBJECT-ASSIGN lesson||oid
     * "011000000236"
     ******************************************************************/
    public boolean canModify(String p_subj, String p_gubun, String p_sub) throws Exception {
        boolean resultb = true;

        DBConnectionManager connMgr = null;
        try {
            connMgr = new DBConnectionManager();

            resultb = canModify(connMgr, p_subj, p_gubun, p_sub);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return resultb;

    }

    /**
     * SCO ����Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */
    public ArrayList SelectObjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;//, list2=null;
        String sql = "";//, sql2="";
        SCOData data = null;

        String p_scolocate = box.getString("p_scolocate");
        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = " SELECT a.OID,  a.sdesc,  a.starting, a.masteryscore,  a.luserid,  a.scotitle,  " + " LEVEL AS scothelevel"
                    + " FROM tz_object a, tz_met_m b"
                    //+ " WHERE a.OID = b.OID(+) AND a.scolocate = '"+ p_scolocate +"'"
                    + " WHERE a.OID  =  b.OID(+) AND a.scolocate = '" + p_scolocate + "'"
                    + " AND a.OID NOT IN (SELECT OID  FROM tz_scosubjobj  WHERE subj =  '" + p_subj + "') "
                    //                 + " CONNECT BY a.highoid = PRIOR a.OID "
                    //                 + " START WITH a.highoid IS NULL"	//2009.10.23 CONNECT BY �� �ּ� ó���Ǿ� �־ �Բ� �ּ�ó����, CONNECT BY �� �ּ�ó���Ǿ� �ִ� ������ ��
                    + " ORDER BY a.oidnumber";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new SCOData();
                data.setOid(ls.getString("oid"));
                data.setSdesc(ls.getString("sdesc"));
                data.setStarting(ls.getString("starting"));
                data.setMasteryscore(ls.getInt("masteryscore"));
                data.setThelevel(ls.getInt("scothelevel"));
                data.setScoTitle(ls.getString("scotitle"));
                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list1;
    }

    /**
     * ������ ��ȸ
     * 
     * @param box receive subjnm
     * @return subjnm ������
     */
    public String SelectSubjNm(String p_subj) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        //        ArrayList list1 = null;
        String sql = "";
        String p_subjnm = "";

        try {
            connMgr = new DBConnectionManager();
            //            list1 = new ArrayList();

            sql = " SELECT  subjnm from tz_subj where subj = '" + p_subj + "'";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                p_subjnm = ls.getString("subjnm");
            }
        } catch (Exception ex) {
            // ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return p_subjnm;
    }

    /**
     * SCO Package ���� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */

    public int SelectSCOObjectPackageList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null, ls2 = null, ls3 = null;
        String sql = "";
        int isOk = 0;
        String p_scolocate = box.getString("p_scolocate");
        String p_subj = box.getString("p_subj");
        String v_luserid = box.getSession("userid");
        int i_ordering = 0;

        Vector vc_oid = new Vector();
        Vector vc_starting = new Vector();
        Vector vc_highoid = new Vector();
        String v_oid = "";
        String v_starting = "";
        String v_highoid = "";
        String v_lesson = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "  select oid from tz_scosubjobj where subj ='" + p_subj + "'  and oid in (select oid from tz_object where scolocate = '"
                    + p_scolocate + "') ";

            ls2 = connMgr.executeQuery(sql);
            if (ls2.next()) {
                isOk = -99;
                return isOk;
            }

            sql = "  select isnull(max(ordering),0) as ordering from tz_scosubjobj where subj ='" + p_subj + "'  ";
            ls3 = connMgr.executeQuery(sql);
            if (ls3.next()) {
                i_ordering = ls3.getInt("ordering");
            }

            sql = " select oid, sdesc, highoid  from tz_object where scolocate = '" + p_scolocate + "' order by oid ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                vc_oid.addElement(ls.getString("oid"));
                vc_starting.addElement(ls.getString("sdesc"));
                vc_highoid.addElement(ls.getString("highoid"));
            }

            sql = " insert into tz_scosubjobj (subj, oid,  type, sdesc, luserid, ldate, ordering, lesson) "
                    + " values (?, ?, 'SC', ?, ?,  to_char(sysdate,'YYYYMMDDHH24MISS'), ?, ? ) ";

            pstmt = connMgr.prepareStatement(sql);

            for (int j = 0; j < vc_oid.size(); j++) {
                v_oid = (String) vc_oid.elementAt(j);
                v_starting = (String) vc_starting.elementAt(j);
                v_highoid = (String) vc_highoid.elementAt(j);

                pstmt.setString(1, p_subj);
                pstmt.setString(2, v_oid);
                pstmt.setString(3, v_starting);
                pstmt.setString(4, v_luserid);

                if (v_highoid.equals("")) { // ������ �����ϱ� ���ؼ�
                    i_ordering++;
                }

                if (i_ordering < 10) {
                    v_lesson = "00" + String.valueOf(i_ordering);
                } else if (i_ordering < 100) {
                    v_lesson = "0" + String.valueOf(i_ordering);
                } else {
                    v_lesson = String.valueOf(i_ordering);
                }

                pstmt.setInt(5, i_ordering);
                pstmt.setString(6, v_lesson);

                isOk = pstmt.executeUpdate();
            }
            if (isOk > 0) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {

            connMgr.setAutoCommit(true);

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * SCO LO ���� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */

    public int SelectSCOObjectLOList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null, ls2 = null, ls3 = null;
        String sql = "";
        int isOk = -1;
        int i_ordering = 0;
        //       String p_scolocate = box.getString("p_scolocate");
        String p_subj = box.getString("p_subj");
        String v_luserid = box.getSession("userid");

        Vector vc_locontent = box.getVector("locontent");
        String v_oid = "";
        String v_starting = "";
        String v_highoid = "";
        String v_lesson = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "  select isnull(max(ordering),0) as ordering from tz_scosubjobj where subj ='" + p_subj + "'  ";
            ls3 = connMgr.executeQuery(sql);
            if (ls3.next()) {
                i_ordering = ls3.getInt("ordering");
            }

            for (int i = 0; i < vc_locontent.size(); i++) {

                sql = " select oid from tz_scosubjobj where oid = '" + vc_locontent.elementAt(i).toString() + "' and subj ='" + p_subj + "' ";

                System.out.println("tz_scosubjobjtz_scosubjobjtz_scosubjobj=" + sql);
                ls2 = connMgr.executeQuery(sql);
                if (ls2.next()) {
                    isOk = -99;
                    return isOk;
                }

                sql = " select distinct oid, sdesc , highoid from tz_object " // + "connect by highoid = prior oid start with highoid = '"+ vc_locontent.elementAt(i).toString() + "' "
                        + " or oid = ( select oid from tz_object where oid = '" + vc_locontent.elementAt(i).toString() + "' ) order by oid";
                ls = connMgr.executeQuery(sql);

                sql = " insert into tz_scosubjobj (subj, oid,  type, sdesc, luserid, ldate, ordering, lesson) "
                        + " values (?, ?, 'SC', ? , ?,  to_char(sysdate,'YYYYMMDDHH24MISS'), ?, ?) ";

                pstmt = connMgr.prepareStatement(sql);

                while (ls.next()) {
                    v_oid = ls.getString("oid");
                    v_starting = ls.getString("sdesc");
                    v_highoid = ls.getString("highoid");

                    if (v_highoid.equals("")) { // ������ �����ϱ� ���ؼ�
                        i_ordering++;
                    }

                    if (i_ordering < 10) {
                        v_lesson = "00" + String.valueOf(i_ordering);
                    } else if (i_ordering < 100) {
                        v_lesson = "0" + String.valueOf(i_ordering);
                    } else {
                        v_lesson = String.valueOf(i_ordering);
                    }

                    pstmt.setString(1, p_subj);
                    pstmt.setString(2, v_oid);
                    pstmt.setString(3, v_starting);
                    pstmt.setString(4, v_luserid);
                    pstmt.setInt(5, i_ordering);
                    pstmt.setString(6, v_lesson);

                    isOk = pstmt.executeUpdate();
                }
            }
            if (isOk > -1) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.setAutoCommit(true);

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * SCO ���� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */
    public ArrayList SelectMappingLOList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList list1 = null;//, list2=null;
        String sql = "";//, sql2="";
        SCOData data = null;

        String p_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select a.oid, a.scotitle, a.scolocate, a.sdesc, level, b.ordering, b.module , b.lesson  from tz_object a , tz_scosubjobj b   "
                    + " where a.oid = b.oid and level = 1  and b.subj = '" + p_subj + "' "
                    //                + " connect by a.highoid = prior a.oid start with a.highoid is null  "
                    + "order by b.ordering asc , a.oid asc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new SCOData();
                data.setOid(ls.getString("oid"));
                data.setSdesc(ls.getString("sdesc"));
                data.setScolocate(ls.getString("scolocate"));
                data.setScoTitle(ls.getString("scotitle"));
                data.setOrdering(ls.getInt("ordering"));
                data.setModule(ls.getString("module"));
                data.setLesson(ls.getString("lesson"));
                list1.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
        return list1;
    }

    /**
     * SCO LO ���� ���� ��ü ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */

    public String DeleteMappingLOList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String results = "";
        String sql = "";
        ListSet ls = null;
        int isOk = -1, isOk1 = -1, isOk2 = -1;

        String p_subj = box.getString("p_subj");
        //       String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " select oid from tz_progress where  subj = '" + p_subj + "' and oid in ( select oid from tz_subjobj where subj = '" + p_subj
                    + "') ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {

                results = "scodeletejindo.fail";
                return results;
            }

            sql = " delete from  tz_subjobj  where subj = '" + p_subj + "'  ";
            System.out.println(sql);
            isOk = connMgr.executeUpdate(sql);

            sql = " delete from  tz_subjlesson  where subj = '" + p_subj + "'  ";
            isOk1 = connMgr.executeUpdate(sql);

            sql = " delete from tz_scosubjobj where subj = '" + p_subj + "' ";
            isOk2 = connMgr.executeUpdate(sql);

            results = "OK";
            if ((isOk > -1) && (isOk1 > -1) && (isOk2 > -1)) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.setAutoCommit(true);
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
        return results;
    }

    /**
     * SCO LO ���� ���� LO ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */

    public String DeleteOneMappingLOList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        ListSet ls = null, ls2 = null;
        int isOk = -1, isOk1 = -1, isOk2 = -1, isOk3 = -1, isOk4 = -1;

        String results = "";
        String p_subj = box.getString("p_subj");
        //       String v_luserid = box.getSession("userid");
        String p_onescoid = box.getString("p_onescoid");
        String p_lesson = box.getString("p_lesson");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " select oid from tz_progress where  subj = '" + p_subj + "' and oid in "
                    + " ( select oid from tz_object connect by highoid = prior oid " + " start with highoid = '" + p_onescoid + "' )   ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {

                results = "scodeletejindo.fail";
                return results;
            }

            sql = " select oid from tz_progress where  oid = '" + p_onescoid + "' ";
            ls2 = connMgr.executeQuery(sql);

            if (ls2.next()) {

                results = "scodeletejindo.fail";
                return results;
            }

            sql = " delete from tz_subjobj where subj = '" + p_subj + "' and "
                    + " oid in ( select oid from tz_object connect by highoid = prior oid " + " start with highoid = '" + p_onescoid + "' )   ";

            isOk = connMgr.executeUpdate(sql);

            sql = " delete from tz_subjobj where subj = '" + p_subj + "' and " + " oid = '" + p_onescoid + "' ";

            isOk3 = connMgr.executeUpdate(sql);

            sql = " delete from tz_subjlesson where  subj = '" + p_subj + "' and lesson = '" + p_lesson + "' ";

            isOk1 = connMgr.executeUpdate(sql);

            sql = " delete from tz_scosubjobj where subj = '" + p_subj + "' and "
                    + " oid in ( select oid from tz_object connect by highoid = prior oid " + " start with highoid = '" + p_onescoid + "' )  ";

            isOk2 = connMgr.executeUpdate(sql);

            sql = " delete from tz_scosubjobj where subj = '" + p_subj + "' and " + " oid = '" + p_onescoid + "' ";

            isOk4 = connMgr.executeUpdate(sql);

            results = "OK";
            if ((isOk > -1) && (isOk1 > -1) && (isOk2 > -1) && (isOk3 > -1) && (isOk4 > -1)) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.setAutoCommit(true);
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
        return results;
    }

    /**
     * SCO ���� ���� �ø���
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */

    public int ChangeOrderHighLOList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = -1, isOk1 = -1, isOk2 = -1;
        String v_lesson = "";
        String v_lesson_1 = "";

        String p_subj = box.getString("p_subj");
        //       String v_luserid = box.getSession("userid");
        String p_ordering = box.getString("p_ordering");
        //       String  p_onescoid      = box.getString("p_onescoid");

        int i_high = 0;
        int i_ordering = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            if (!p_ordering.equals("1")) {

                i_high = Integer.parseInt(p_ordering) - 1;
                i_ordering = Integer.parseInt(p_ordering);

                if (i_high < 10) {
                    v_lesson = "0" + String.valueOf(i_high);
                } else {
                    v_lesson = String.valueOf(i_high);
                }

                if (i_ordering < 10) {
                    v_lesson_1 = "0" + String.valueOf(i_ordering);
                } else {
                    v_lesson_1 = String.valueOf(i_ordering);
                }

                sql = " update tz_scosubjobj set ordering = -1 where subj = '" + p_subj + "' and ordering =" + i_high + " ";
                isOk = connMgr.executeUpdate(sql);

                sql = " update tz_scosubjobj set ordering = " + i_high + ", lesson = '" + v_lesson + "' where subj = '" + p_subj
                        + "' and ordering =" + p_ordering + " ";
                isOk1 = connMgr.executeUpdate(sql);

                sql = " update tz_scosubjobj set ordering = " + p_ordering + ", lesson = '" + v_lesson_1 + "' where subj = '" + p_subj
                        + "' and ordering = -1 ";
                isOk2 = connMgr.executeUpdate(sql);
            }
            if ((isOk > -1) && (isOk1 > -1) && (isOk2 > -1)) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.setAutoCommit(true);

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
     * SCO ���� �Ʒ��� ������
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */

    public int ChangeOrderLowLOList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = -1, isOk1 = -1, isOk2 = -1;
        ListSet ls = null;
        String maxordering = "";
        String v_lesson = "";
        String v_lesson_1 = "";

        String p_subj = box.getString("p_subj");
        //       String v_luserid = box.getSession("userid");
        String p_ordering = box.getString("p_ordering");
        //       String  p_onescoid      = box.getString("p_onescoid");

        int i_high = 0;
        int i_ordering = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "  select isnull(max(ordering),0) as maxordering from tz_scosubjobj where subj ='" + p_subj + "'  ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                maxordering = String.valueOf(ls.getString("maxordering"));
            }

            if (!p_ordering.equals(maxordering)) {

                i_high = Integer.parseInt(p_ordering) + 1;
                i_ordering = Integer.parseInt(p_ordering);

                if (i_high < 10) {
                    v_lesson = "0" + String.valueOf(i_high);
                } else {
                    v_lesson = String.valueOf(i_high);
                }

                if (i_ordering < 10) {
                    v_lesson_1 = "0" + String.valueOf(i_ordering);
                } else {
                    v_lesson_1 = String.valueOf(i_ordering);
                }

                sql = " update tz_scosubjobj set ordering = -1 where subj = '" + p_subj + "' and ordering =" + i_high + " ";
                isOk = connMgr.executeUpdate(sql);

                sql = " update tz_scosubjobj set ordering = " + i_high + " , lesson = '" + v_lesson + "' where subj = '" + p_subj
                        + "' and ordering =" + p_ordering + " ";
                isOk1 = connMgr.executeUpdate(sql);

                sql = " update tz_scosubjobj set ordering = " + p_ordering + ", lesson = '" + v_lesson_1 + "'  where subj = '" + p_subj
                        + "' and ordering = -1 ";
                isOk2 = connMgr.executeUpdate(sql);
            }
            if ((isOk > -1) && (isOk1 > -1) && (isOk2 > -1)) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            connMgr.setAutoCommit(true);

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
        return isOk;
    }

    /**
     * SCO �����ϱ�
     * 
     * @param box receive from the form object and session
     * @return ArrayList Object����Ʈ
     */

    public int SCOSaveConentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null, ls2 = null;
        String sql = "", sql1 = "", sql2 = "", sql3 = "", sql4 = "", sql5 = "";
        int isOk = -1, isOk1 = -1, isOk2 = -1, isOk3 = -1, isOk4 = -1;
        //       ArrayList list1 = null, list2=null;
        //       String maxordering ="";
        String subjnm = "";
        int modulecount = 0;

        String p_subj = box.getString("p_subj");
        String v_luserid = box.getSession("userid");
        //       Vector vc_module = box.getVector("p_module");
        //       Vector vc_scoid  = box.getVector("p_scoid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "  select subjnm from tz_subj where subj ='" + p_subj + "'  ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                subjnm = ls.getString("subjnm");
            }

            sql = " select count(*) as modulecount from tz_subjmodule where subj ='" + p_subj + "'  ";
            ls2 = connMgr.executeQuery(sql);

            if (ls2.next()) {
                modulecount = ls2.getInt("modulecount");
            }

            if (modulecount == 0) { // ����� �⺻������ �ϳ��� �ִ´�.
                sql1 = " insert into tz_subjmodule (subj, module, sdesc,  luserid, ldate)" + " values ( '" + p_subj + "', '01', '" + subjnm
                        + "' , '" + v_luserid + "',  to_char(sysdate,'YYYYMMDDHH24MISS') )";
                isOk = connMgr.executeUpdate(sql1);
            } else {
                isOk = 1;
            }

            sql2 = " delete from  tz_subjobj  where subj = '" + p_subj + "'  ";
            isOk1 = connMgr.executeUpdate(sql2);

            sql3 = " delete from  tz_subjlesson  where subj = '" + p_subj + "'  ";
            isOk2 = connMgr.executeUpdate(sql3);

            sql4 = "insert into tz_subjlesson (subj, module, lesson , sdesc, owner, starting, luserid , ldate )" + " select '" + p_subj
                    + "' as subj, '01' , b.lesson , a.sdesc, '" + p_subj + "' as subj,a.starting, '" + v_luserid
                    + "' , to_char(sysdate,'YYYYMMDDHH24MISS')  " + " from tz_object a , tz_scosubjobj b  "
                    + " where a.oid = b.oid and level = 1  and b.subj ='" + p_subj + "' "
                    + " connect by a.highoid = prior a.oid start with a.highoid is null  order by b.ordering asc , a.oid asc ";

            isOk3 = connMgr.executeUpdate(sql4);

            sql5 = "insert into tz_subjobj(subj, module, lesson, oid , type , ordering,  sdesc, luserid , ldate )" + " select '" + p_subj
                    + "' as subj, '01' , b.lesson , a.oid, 'SC',  b.ordering, b.sdesc, '" + v_luserid + "' , to_char(sysdate,'YYYYMMDDHH24MISS')  "
                    + " from tz_object a , tz_scosubjobj b  " + " where a.oid = b.oid and b.subj = '" + p_subj
                    + "'  order by b.ordering asc , a.oid asc ";

            isOk4 = connMgr.executeUpdate(sql5);

            if ((isOk > -1) && (isOk1 > -1) && (isOk2 > -1) && (isOk3 > -1) && (isOk4 > -1)) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
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
     * SCO Module Select-List�����
     * 
     * @param String tz_config.name
     * @return String tz_config.vals
     */
    public static String make_SCOmoduleSelect(ArrayList modules, String p_module) throws Exception {
        String results = "";
        DataBox dbox1 = null;

        for (int i = 0; i < modules.size(); i++) {
            dbox1 = (DataBox) modules.get(i);
            results += "<option value='" + dbox1.getString("d_module") + "' ";
            if (dbox1.getString("d_module").equals(p_module))
                results += " selected ";
            results += ">" + dbox1.getString("d_sdesc") + "</option>";
        }
        return results;
    }

    /**
     * SCO Package List�����
     * 
     * @param String
     * @return String
     */
    public static String makeSCOPackageSelect(RequestBox box, String p_scolocate) throws Exception {
        DBConnectionManager connMgr = null;
        String s_cpseq = box.getSession("cpseq");
        String s_gadmin = box.getSession("gadmin");
        ListSet ls = null;
        //        ArrayList list1 = null;
        String sql = "";
        String results = "<select name=\"p_scolocate\" ";
        results += ">";

        try {
            connMgr = new DBConnectionManager();
            //            list1 = new ArrayList();

            sql = "select distinct scotitle, ";
            sql += "								scolocate ";
            sql += " 	from tz_object ";
            sql += " where otype ='SCO'  ";
            if (!s_gadmin.equals("A1")) {
                sql += "   and cpseq = " + StringManager.makeSQL(s_cpseq);
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                results += "<option value='" + ls.getString("scolocate") + "' ";
                if (p_scolocate.equals(ls.getString("scolocate")))
                    results += " selected";
                results += " >" + ls.getString("scotitle") + "</option>";
            }
        } catch (Exception ex) {
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
        results += "</select>";

        return results;
    }

    /**
     * Object ZIP ���Ϸ� Directory ����
     * 
     * @param String p_job ���/��������(insert/update) p_oid Object ID box RequestBox
     * @return String resuts ����޼���
     */
    public String controlObjectFile(String p_job, String p_subj, RequestBox box) throws Exception {

        String results = "OK";
        //        String sql = "";
        //        int isOk = 1;

        String v_realPath = "";
        String v_tempPath = "";
        //        boolean insert_success = false;
        boolean move_success = false;
        //        boolean update_success = false;
        boolean extract_success = false;
        boolean allDelete_success = false;
        /*
         * //---------------------- ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�
         * -------------------------------- String v_realFileName =
         * box.getRealFileName("p_file"); //�� ���ϸ� String v_newFileName =
         * box.getNewFileName("p_file"); //���� upload�� ���ϸ�
         * //----------------------
         * ----------------------------------------------
         * --------------------------------------------------------
         */
        //---------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------

        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];
        String v_file1 = "";
        System.out.println("FILE_LIMIT=" + FILE_LIMIT);
        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }

        if (!v_newFileName[0].equals("")) {
            v_file1 = v_newFileName[0];
        }

        //÷�������� ���� ��쿡�� ����.
        if (!v_file1.equals("")) {

            // Object ��������
            ConfigSet conf = new ConfigSet();
            //v_realPath = GetCodenm.get_config("object_locate") + p_oid;   //���� Un-zip�� Dir
            v_realPath = conf.getProperty("dir.menuupload") + p_subj + "/docs/menuimg/"; //���� Un-zip�� Dir
            v_tempPath = conf.getProperty("dir.upload.default"); //upload�� ���� ��ġ

            results = p_job;
            results += "\\n\\n 0. v_realPath=" + v_realPath;
            results += "\\n\\n 0. v_tempPath=" + v_tempPath;

            try {
                if (p_job.equals("insert")) { // Object ��Ͻ�
                    // 1. ���� ����
                    System.out.println("test1������");
                    File newDir = new File(v_realPath);
                    newDir.mkdirs();
                    allDelete_success = true;
                    results += "\\n\\n 1. makeDirecotry OK. ";
                } else if (p_job.equals("update")) { // Object ������
                    //1. ������ ���Ϲ� ���� ��� ����
                    FileDelete fd = new FileDelete();
                    allDelete_success = fd.allDelete(v_realPath);
                    results += "\\n\\n 1. allDelete =  " + (new Boolean(allDelete_success).toString());
                    // 1. ���� ����
                    File newDir = new File(v_realPath);
                    newDir.mkdirs();
                    allDelete_success = true;
                    results += "\\n\\n 1. makeDirecotry OK. ";
                }

                // 2. ���� �̵�
                if (allDelete_success) {
                    System.out.println("test2������2");
                    FileMove fc = new FileMove();
                    move_success = fc.move(v_realPath, v_tempPath, v_file1);
                }
                results += "\\n\\n 2. move to [" + v_realPath + "] =  " + (new Boolean(move_success).toString());

                // 3. ���� Ǯ��
                if (move_success) {
                    System.out.println("test3������3");
                    FileUnzip unzip = new FileUnzip();
                    extract_success = unzip.extract(v_realPath, v_file1);
                    results += "\\n\\n 3. unzip to [" + v_realPath + "] =  " + (new Boolean(extract_success).toString());
                    if (!extract_success) {
                        FileDelete fd = new FileDelete();
                        fd.allDelete(v_realPath);
                    }
                }
                results += "\\n\\n END of controlObjectFile() ";
                results = "OK";
            } catch (Exception ex) {
                FileDelete fd = new FileDelete();
                fd.allDelete(v_realPath);
                throw new Exception("ERROR results=" + results + "\r\n" + ex.getMessage());
            } finally {
                FileManager.deleteFile(v_realPath + "\\" + v_file1);
            }
        }

        return results;
    }

    /**
     * �޴��̹��� zip ���� ���
     * 
     * @param String box RequestBox
     * @return String resuts ����޼���
     */
    public String insertMenuImage(RequestBox box) throws Exception {
        String results = "FAIL";
        String v_realPath = "";
        String v_tempPath = "";
        boolean move_success = false;
        boolean extract_success = false;

        //---------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------
        //        String v_realFileName = box.getRealFileName(FILE_TYPE);
        String v_newFileName = box.getNewFileName(FILE_TYPE);
        String v_subj = box.getString("p_subj");

        //÷�������� ���� ��쿡�� ����.
        if (!v_newFileName.equals("")) {
            // Object ��������
            ConfigSet conf = new ConfigSet();
            v_realPath = conf.getProperty("dir.menuupload") + v_subj + "/docs/menuimg/"; //���� Un-zip�� Dir
            v_tempPath = conf.getProperty("dir.upload.default"); //upload�� ���� ��ġ

            try {
                //1. ���丮 üũ , ������ �����Ѵ�.
                File dir = new File(v_realPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 2. ���� �̵�
                FileMove fc = new FileMove();
                move_success = fc.move(v_realPath, v_tempPath, v_newFileName);

                // 3. ���� Ǯ��
                if (move_success) {
                    FileUnzip unzip = new FileUnzip();
                    extract_success = unzip.extract(v_realPath, v_newFileName);

                    if (!extract_success) {
                        results = "UNZIP ERROR";
                    } else {
                        results = "OK";
                    }
                } else {
                    results = "MOVE ERROR";
                }

            } catch (Exception ex) {
                throw new Exception("ERROR results=" + results + "\r\n" + ex.getMessage());
            } finally {
                FileManager.deleteFile(v_realPath + "\\" + v_newFileName);
            }
        }

        return results;
    }
}
