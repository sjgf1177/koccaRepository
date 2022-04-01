//**********************************************************
//  1. ��      ��: ��ڿ��Ը� �����ϴ� ����
//  2. ���α׷��� : HomePageContactServlet.java
//  3. ��      ��: ��ڿ��� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.6 �̿���
//  7. ��      ��: 2005.7.6 �̿���
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomePageContactBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageContactServlet")
public class HomePageContactServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            box.put("p_frmURL", request.getRequestURI().toString() + "?p_process=" + v_process);

            if (v_process.equals("insertPage")) {
                if (!AdminUtil.getInstance().checkLogin(out, box)) {
                    return;
                }
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("insertPage")) { //insertPage   // 1:1 ����������� �̵��Ҷ�
                this.performInsertPage(request, response, box, out);

            } else if (v_process.equals("insert")) { // 1:1 ����Ҷ�
                this.performInsert(request, response, box, out);

            } else if (v_process.equals("contactus")) { // Contact US
                this.performContactUsPage(request, response, box, out);

            } else if (v_process.equals("insertContactUs")) { // B2B Contact ����Ҷ�
                this.performInsertContactUs(request, response, box, out);

            } else if (v_process.equals("contactetc")) { // Contact ETC
                this.performContactEtcPage(request, response, box, out);

            } else if (v_process.equals("insertContactEtc")) { // B2B Contact ����Ҷ�
                this.performInsertContactEtc(request, response, box, out);

            } else if (v_process.equals("VocationUs")) { // ��� ����Ҷ�
                this.performVocationPage(request, response, box, out);

            } else if (v_process.equals("insertVocation")) { // B2B Contact ����Ҷ�
                this.performInsertVocation(request, response, box, out);

            } else if (v_process.equals("webReport")) {
                this.performWebReport(request, response, box, out); //�����ٽŰ� ������ �̵�
            } else if (v_process.equals("insertWebReport")) {
                this.performInsertWebReport(request, response, box, out);
            } else {
                this.performInsertPage(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * Qna ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/helpdesk/zu_HomePageContactUs_I.jsp";
            } else {
                v_url = "/learn/user/portal/helpdesk/zu_HomePageContactUs_I.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_HomePageContactUs_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * Qna ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageContactBean bean = new HomePageContactBean();

            int isOk = bean.insertQnaQue(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageContactServlet";
            box.put("p_process", "insertPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * Contact US ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performContactUsPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            String v_url = "";
            //v_url = "/learn/user/2012/portal/helpdesk/zu_ContactUs_I.jsp";
            v_url = "/learn/user/2013/portal/helpdesk/zu_ContactUs_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_ContactUs_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * Contact US ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsertContactUs(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            HomePageContactBean bean = new HomePageContactBean();

            int isOk = bean.insertContactUs(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageContactServlet";
            box.put("p_process", "contactus");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageContactServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��Ÿ ���� ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performContactEtcPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            String v_url = "";
            //v_url = "/learn/user/2012/portal/helpdesk/zu_ContactEtc_I.jsp";
            v_url = "/learn/user/2013/portal/helpdesk/zu_ContactEtc_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/2012/portal/helpdesk/zu_ContactEtc_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * Contact US ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsertContactEtc(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            HomePageContactBean bean = new HomePageContactBean();

            int isOk = bean.insertContactEtc(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageContactServlet";
            box.put("p_process", "contactetc");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageContactServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��� ����� ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performVocationPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            String v_url = "";
            v_url = "/learn/user/2012/portal/helpdesk/zu_Vocation_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_ContactUs_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * Contact US ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsertVocation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            HomePageContactBean bean = new HomePageContactBean();

            int isOk = bean.insertVocation(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageContactServlet";
            box.put("p_process", "VocationUs");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageContactServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����༺�Ű���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performWebReport(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";

            v_url = "/learn/user/2013/portal/helpdesk/zu_webReport_R.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����༺�Ű��� ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsertWebReport(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageContactBean bean = new HomePageContactBean();

            int isOk = bean.insertWebReport(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageContactServlet";
            box.put("p_process", "webReport");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

}
