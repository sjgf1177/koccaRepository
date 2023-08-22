//**********************************************************
//  1. ��      ��: ������� �����ϴ� ����
//  2. ���α׷��� : MenuCountServlet.java
//  3. ��      ��: ������� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 7
//  7. ��      ��:
//**********************************************************

package controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.MenuCountBean;
import com.credu.templet.TempletBean;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.system.MenuCountServlet")
public class MenuCountServlet extends javax.servlet.http.HttpServlet {

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            System.out.println("============================ MenuCountServlet.java_v_process : " + v_process);
            // /////////////////////////////////////////////////////////////////
            // selectMonthDay : ��� ȭ���̹Ƿ� üũ

            if (v_process.equals("selectMonthDay")) {
                if (!AdminUtil.getInstance().checkRWRight("MenuCountServlet", v_process, out, box)) {
                    return;
                }
            } else if (v_process.equals("myActivity")) {
                if (!AdminUtil.getInstance().checkLogin(out, box)) {
                    return;
                }
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            // /////////////////////////////////////////////////////////////////

            if (v_process.equals("selectMonthDay")) { // �޴������������������ �̵��Ҷ�
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("myActivity")) { // Myactivity ������
                this.performSelectActivity(request, response, box, out);

            } else if (v_process.equals("writeLog")) { // �޴���������� ��϶�
                this.performWriteLog(request, response, box, out);

            } else if (v_process.equals("myActivityExcel")) { // ���
                this.performMyActivityExcel(request, response, box, out);
            } else if (v_process.equals("selectPreviewCount")) { // ������ī��Ʈ
                this.performPreviewCount(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �޴������������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MenuCountBean bean = new MenuCountBean();

            // �޴��� ���
            ArrayList list1 = bean.SelectMenuList(box);
            request.setAttribute("selectList1", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuCount_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * MY Activity ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectActivity(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // �޴��� ���
            MenuCountBean bean = new MenuCountBean();
            ArrayList list1 = bean.SelectActivityList(box);
            request.setAttribute("selectActivity", list1);

            // ȸ������
            MemberInfoBean bean2 = new MemberInfoBean();
            // ArrayList list2 = bean2.memberInfoView(box);
            // request.setAttribute("memberView", list2);

            DataBox dbox = bean2.memberInfoView(box);
            request.setAttribute("memberView", dbox);

            // �Խ�������
            ArrayList list3 = bean.selectBoardCnt(box);
            request.setAttribute("boardCnt", list3);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/gu_Activity_Site.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * �޴��� ��� ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performWriteLog(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            MenuCountBean bean = new MenuCountBean();
            TempletBean bean2 = new TempletBean();
            int isOk = bean.writeLog(box);

            String v_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            String v_msg = "";
            String v_url = "";

            if("bora".equals(box.getString("p_mode"))){
                box.setSession("tem_grcode", "N000210");
                v_grcode = "N000210";
            }

            AlertManager alert = new AlertManager();

            if (v_grcode.equals("")) {
                v_msg = "���������� �����Դϴ�.";
                v_url = "/";
                alert.alertOkMessage(out, v_msg, v_url, box);

            } else if (isOk > 0) {

                v_url = bean2.getMenuUrl(box);
                System.out.println("v_url = " + v_url);

                if (v_url.equals("")) {
                    v_msg = "�����Ͻ� �޴��� ���� �����׷쿡 ��ϵ��� �ʾҽ��ϴ�.";
                    alert.alertFailMessage(out, v_msg);
                }

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                // rd.include(request, response);
                rd.forward(request, response);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            // System.out.println("err menuid = " + box.getString("leftmenu"));
            throw new Exception("performWriteLog()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMyActivityExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Activity_Site_E.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyActivityExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �޴��� ������ī��Ʈ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performPreviewCount(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MenuCountBean bean = new MenuCountBean();

            // �޴��� ���
            ArrayList list1 = bean.selectPreviewCount(box);
            request.setAttribute("selectList1", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CountPreview_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }
}
