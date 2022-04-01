//**********************************************************
//  1. ��      ��: FAQ�� �����ϴ� ����
//  2. ���α׷��� : FaqServlet.java
//  3. ��      ��: FAQ�� �������� �����Ѵ�(HOMEPAGE)
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2004.1.26
//  7. ��      ��:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomePageAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageAdminServlet")
public class HomePageAdminServlet extends javax.servlet.http.HttpServlet {

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("adminiList")) { // ����Ʈ
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("adminiListkia")) { // ����Ʈ
                this.performSelectListkia(request, response, box, out);
            } else if (v_process.equals("mail")) { // ����Ʈ
                //this.performmailPage(request, response, box, out);
            } else if (v_process.equals("send")) { // ����Ʈ
                this.performMail(request, response, box, out);
            } else if (v_process.equals("select2")) { //      �󼼺����Ҷ� (������ûȭ��) - VLC
                this.performSelect2(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����Ʈ
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
            HomePageAdminBean bean = new HomePageAdminBean();

            ArrayList<DataBox> list = bean.selectListAdminhyundai(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/portal/user/helpdesk/zu_HomePageAdmin_L.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /portal/user/helpdesk/zu_HomePageAdmin_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    public void performSelectListkia(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            HomePageAdminBean bean = new HomePageAdminBean();

            ArrayList<DataBox> list = bean.selectListAdminkia(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/portal/user/helpdesk/zu_HomePageAdmin_LL.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /portal/user/helpdesk/zu_HomePageAdmin_LL.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /*
     * public void performmailPage(HttpServletRequest request,
     * HttpServletResponse response, RequestBox box, PrintWriter out) throws
     * Exception { try { request.setAttribute("requestbox", box); // ��������� box
     * ��ü�� �Ѱ��ش�
     * 
     * ServletContext sc = getServletContext(); RequestDispatcher rd =
     * sc.getRequestDispatcher("/portal/user/homepage/zu_homepagemail.jsp");
     * rd.forward(request, response);
     * 
     * // Log.info.println(this, box,
     * "Dispatch to /learn/user/homepagepage/za_Faq_I.jsp"); }catch (Exception
     * ex) { ErrorManager.getErrorStackTrace(ex, out); throw new
     * Exception("performInsertPage()\r\n" + ex.getMessage()); } }
     */
    @SuppressWarnings("unchecked")
    public void performMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            HomePageAdminBean admin = new HomePageAdminBean();
            boolean isMailed = false;

            isMailed = admin.selectPds(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageAdminServlet";
            box.put("p_process", "adminiList");

            AlertManager alert = new AlertManager();

            if (isMailed) {
                v_msg = "������ �����Ͽ����ϴ�";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "���� ����!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    public void performSelect2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            HomePageAdminBean admin = new HomePageAdminBean();

            ArrayList<DataBox> list = admin.selectMail(box);

            request.setAttribute("selectlmail", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/portal/user/helpdesk/zu_HomePageAdmin_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

}
