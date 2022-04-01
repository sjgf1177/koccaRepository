//**********************************************************
//  1. 제      목: 모듈 관리
//  2. 프로그램명 : MenuSubProcessAdminBean.java
//  3. 개      요: 메뉴 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  2
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 메뉴 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class MenuSubProcessAdminBean {
    private static final String CONFIG_NAME = "cur_nrm_grcode";

    public MenuSubProcessAdminBean() {
    }

    /**
     * 프로세스화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 모듈 리스트
     */
    public ArrayList<MenuSubProcessData> selectListMenuSubProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<MenuSubProcessData> list = null;
        String sql = "";
        MenuSubProcessData data = null;

        String v_grcode = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_menu = box.getString("p_menu");
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<MenuSubProcessData>();

            sql = " select process, servlettype, method, luserid, ldate    ";
            sql += "   from TZ_MENUSUBPROCESS                               ";
            sql += "  where grcode = " + StringManager.makeSQL(v_grcode);
            sql += "    and menu   = " + StringManager.makeSQL(v_menu);
            sql += "    and seq    = " + v_seq;
            sql += " order by process asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new MenuSubProcessData();

                data.setProcess(ls.getString("process"));
                data.setServlettype(ls.getString("servlettype"));
                data.setMethod(ls.getString("method"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));

                list.add(data);
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
     * 프로세스화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return MenuData 조회한 상세정보
     */
    public MenuSubProcessData selectViewMenuSubProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        MenuSubProcessData data = null;

        String v_grcode = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_menu = box.getString("p_menu");
        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_processvalue");

        try {
            connMgr = new DBConnectionManager();

            sql = " select process, servlettype, method, luserid, ldate    ";
            sql += "   from TZ_MENUSUBPROCESS                               ";
            sql += "  where grcode  = " + StringManager.makeSQL(v_grcode);
            sql += "    and menu    = " + StringManager.makeSQL(v_menu);
            sql += "    and seq     = " + v_seq;
            sql += "    and process = " + StringManager.makeSQL(v_process);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new MenuSubProcessData();
                data.setProcess(ls.getString("process"));
                data.setServlettype(ls.getString("servlettype"));
                data.setMethod(ls.getString("method"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
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
        return data;
    }

    /**
     * 프로세스 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertMenuSubProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_menu = box.getString("p_menu");
        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_processvalue");
        String v_servlettype = box.getString("p_servlettype");
        String v_method = box.getString("p_method");

        System.out.println("--'" + v_menu + "'--");
        System.out.println("--'" + v_servlettype + "'--");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "insert into TZ_MENUSUBPROCESS(grcode, menu, seq, process, servlettype, method, luserid, ldate ) ";
            sql += "            values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))                  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_menu);
            pstmt.setInt(3, v_seq);
            pstmt.setString(4, v_process);
            pstmt.setString(5, v_servlettype);
            pstmt.setString(6, v_method);
            pstmt.setString(7, s_userid);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
     * 프로세스 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateMenuSubProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_menu = box.getString("p_menu");
        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_processvalue");
        String v_servlettype = box.getString("p_servlettype");
        String v_method = box.getString("p_method");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_MENUSUBPROCESS set servlettype = ? , method = ?, luserid= ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where grcode = ? and menu = ? and seq = ? and process = ?                                                           ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_servlettype);
            pstmt.setString(2, v_method);
            pstmt.setString(3, s_userid);
            pstmt.setString(4, v_grcode);
            pstmt.setString(5, v_menu);
            pstmt.setInt(6, v_seq);
            pstmt.setString(7, v_process);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
        return isOk;
    }

    /**
     * 프로세스 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteMenuSubProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = CodeConfigBean.getConfigValue(CONFIG_NAME);
        String v_menu = box.getString("p_menu");
        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_processvalue");

        try {
            connMgr = new DBConnectionManager();

            sql = " delete from TZ_MENUSUBPROCESS                               ";
            sql += "   where grcode = ? and menu = ? and seq = ? and process = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_menu);
            pstmt.setInt(3, v_seq);
            pstmt.setString(4, v_process);
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
        return isOk;
    }

}
