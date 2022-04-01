//**********************************************************
//1. 제      목:
//2. 프로그램명: AdminUtil.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-07
//7. 수      정:
//
//**********************************************************

package com.credu.system;

import java.io.PrintWriter;

import com.credu.library.AlertManager;
import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AdminUtil {
    public static int NOADMIN = 1;
    public static int READ = 2;
    public static int WRITE = 4;

    public static int ONLY_READ_PAGE = 1;
    public static int READPAGE_WITH_WRITE = 2;
    public static int WRITE_ACTION = 4;

    public static int RETURN_ACTION = 1;
    public static int CONTINUE_ACTION = 2;
    public static int READ_BUT_WARINING = 4;

    private boolean check = false; //true; ==> 테스트를 위하여 임시 설정.(권한체크안함)

    private AdminUtil(boolean pcheck) {
        check = pcheck; // 초기화 파일에서 읽어올 예정 false 면 권한체크 하지 않는다.
    }

    public static AdminUtil getInstance() throws Exception {
        AdminUtil instance = null;
        instance = new AdminUtil(true);

        return instance;
    }

    public int getServletRight(String p_servlet, String p_process, String p_gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        int control = AdminUtil.NOADMIN;
        int servlettype = AdminUtil.ONLY_READ_PAGE;
        int admin_check = AdminUtil.RETURN_ACTION;
        String v_temp = "";

        try {
            sql = "select b.servlettype, c.control ";
            sql += "  from tz_menusub          a, ";
            sql += "	      tz_menusubprocess   b, ";
            sql += "	      tz_menuauth         c  ";
            sql += " where a.grcode = b.grcode  ";
            sql += "   and a.menu   = b.menu    ";
            sql += "   and a.seq    = b.seq     ";
            sql += "   and a.grcode = c.grcode  ";
            sql += "   and a.menu   = c.menu    ";
            sql += "   and a.seq    = c.menusubseq ";
            sql += "   and a.servlet = " + SQLString.Format(p_servlet);
            sql += "   and b.process = " + SQLString.Format(p_process);
            sql += "   and c.gadmin  = " + SQLString.Format(p_gadmin);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_temp = ls.getString("control");
                if (v_temp.equals("r")) {
                    control = AdminUtil.READ;
                } else if (v_temp.equals("rw")) {
                    control = AdminUtil.WRITE;
                }
                v_temp = StringManager.trim(ls.getString("servlettype"));
                servlettype = Integer.parseInt(v_temp);//       Integer.valueOf(v_temp).intValue();

            }

            if (control == AdminUtil.NOADMIN) {
                admin_check = AdminUtil.RETURN_ACTION;
            } else if (control == AdminUtil.READ) {
                if (servlettype == AdminUtil.ONLY_READ_PAGE) {
                    admin_check = AdminUtil.CONTINUE_ACTION;
                } else if (servlettype == AdminUtil.READPAGE_WITH_WRITE) {
                    admin_check = AdminUtil.READ_BUT_WARINING;
                } else if (servlettype == AdminUtil.WRITE_ACTION) {
                    admin_check = AdminUtil.RETURN_ACTION;
                }
            } else if (control == AdminUtil.WRITE) {
                admin_check = AdminUtil.CONTINUE_ACTION;
            }
            //            System.out.println("admin_check="+admin_check);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
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
        return admin_check;
    }

    /**
     * 
     * @param p_servlet
     * @param p_process
     * @param out
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public boolean checkRWRight(String p_servlet, String p_process, PrintWriter out, RequestBox box) throws Exception {
        boolean v_check = false;
        String v_gadmin = "";

        try {
            if (check == false) {
                v_check = true;
            } else {
                v_check = checkLoginPopup(out, box);
                v_gadmin = box.getSession("gadmin");
                if (v_check) {
                    int v_servletright = getServletRight(p_servlet, p_process, v_gadmin);
                    //System.out.println(v_servletright + " : " + AdminUtil.RETURN_ACTION);
                    if (v_servletright == AdminUtil.RETURN_ACTION) {
                        v_check = false;
                        AlertManager.historyBack(out, "실행권한이 없습니다.");
                    } else if (v_servletright == AdminUtil.READ_BUT_WARINING) {
                        v_check = true;
                        box.put("p_warnmsg", "<font color='red'>조회는 가능하지만 쓰기버튼을 사용할수 없습니다.</font>");
                    } else if (v_servletright == AdminUtil.CONTINUE_ACTION) {
                        v_check = true;
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return v_check;
    }

    @SuppressWarnings("unchecked")
    public boolean checkLogin(PrintWriter out, RequestBox box) throws Exception {
        boolean v_check = true;
        String v_userid = "";
        String v_url = "";
        String v_msg = "";
        v_userid = box.getSession("userid");
        AlertManager alert = new AlertManager();

        if (v_userid.equals("")) {

            v_check = false;
            //v_url  = "/servlet/controller.homepage.MainServlet";p_frmURL            

            if (box.getSession("tem_grcode").equals("N000001")) {
                box.put("p_frmURL", box.getString("p_frmURL"));
                //v_url = "/learn/user/2012/portal/homepage/zu_MainLogin.jsp";
                v_url = "/learn/user/2013/portal/homepage/zu_MainLogin.jsp";
                v_msg = "로그인 후  이용해주세요";
                //alert.alertFailMessage(out, v_msg);
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                box.put("p_process", "main");
                //box.put("p_process","ProposeCancelPage");
                v_url = "/servlet/controller.homepage.MainServlet";
                v_msg = "로그인 후  이용해주세요";
                alert.alertFailMessage(out, v_msg);
            }
            /*
             * v_msg = "로그인 후  이용해주세요"; //alert.alertFailMessage(out, v_msg);
             * alert.alertOkMessage(out, v_msg, v_url , box);
             */
        }
        return v_check;
    }

    public boolean checkLoginPopup(PrintWriter out, RequestBox box) throws Exception {
        boolean v_check = true;
        String v_userid = "";
        String v_url = "";
        String v_msg = "";
        v_userid = box.getSession("userid");
        AlertManager alert = new AlertManager();

        if (v_userid.equals("")) {

            v_check = false;
            v_url = "/servlet/controller.homepage.MainServlet";
            v_msg = "로그인 후 이용해주세요";
            alert.alertOkMessage(out, v_msg, v_url, box, true, true, true);
        }
        return v_check;
    }

    public boolean checkLoginHomePopup(PrintWriter out, RequestBox box) throws Exception {
        boolean v_check = true;
        String v_userid = "";
        String v_url = "";
        String v_msg = "";
        v_userid = box.getSession("userid");
        AlertManager alert = new AlertManager();

        if (v_userid.equals("")) {

            v_check = false;
            v_url = "/servlet/controller.homepage.MainServlet";
            v_msg = "로그인 후 이용해주세요";
            alert.alertOkMessage(out, v_msg, v_url, box, true, true);
        }
        return v_check;
    }

    //클라이언트 커뮤니티 하위 실시간위험관리 내부직인지 여부 판단
    public boolean checkInsideGubun(PrintWriter out, RequestBox box) throws Exception {
        boolean v_check = false;
        String v_userid = "";
        String v_url = "";
        String v_msg = "";
        String sql = "";
        String v_temp = "";
        v_userid = box.getSession("userid");
        AlertManager alert = new AlertManager();
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        sql = "select insidegubun ";
        sql += "  from tz_member ";
        sql += " where userid= " + SQLString.Format(v_userid);

        connMgr = new DBConnectionManager();
        ls = connMgr.executeQuery(sql);
        while (ls.next()) {
            v_temp = ls.getString("insidegubun");
            if (v_temp.equals("A"))
                v_check = true;
        }
        ls.close();
        connMgr.freeConnection();

        if (!v_check) {
            v_url = "/servlet/controller.homepage.MainServlet";
            v_msg = "내부직원만 사용 할수 있는 메뉴 입니다.";
            alert.alertOkMessage(out, v_msg, v_url, box);
        }
        return v_check;
    }
}
