/**
 * ������Ʈ�� : kocca_java
 * ��Ű���� : controller.mobile.member
 * ���ϸ� : LoginServlet.java
 * �ۼ���¥ : 2011. 10. 1.
 * ó������ :
 * �������� :
 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.   
 */

package controller.mobile.member;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.credu.homepage.LoginBean;
import com.credu.library.AlertManager;
import com.credu.library.Base64;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.templet.TempletBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.member.LoginServlet")
public class LoginServlet extends javax.servlet.http.HttpServlet {
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getString("p_process"); // process

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if ("".equals(v_process) || "loginpage".equals(v_process)) {
                this.peformLoginPage(request, response, box, out); //����� �α��� ������

            } else if ("loginproc".equals(v_process)) {
                this.peformloginProc(request, response, box, out); //����� �α���ó�� process

            } else if ("logoutPoc".equals(v_process)) {
                this.performLogoutProc(request, response, box, out); //����� �α׾ƿ�
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �α��� ������
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void peformLoginPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            if ("".equals(box.getSession("tem_grcode"))) {
                box.put("p_servernm", request.getServerName());
                TempletBean bean1 = new TempletBean();
                DataBox listBean = bean1.SelectGrcodeExists(box);

                if (listBean != null && !listBean.get("d_grcode").equals("")) {
                    box.setSession("tem_menu_type", listBean.get("d_menutype")); //�޴� �׺���̼� Ÿ��
                    box.setSession("tem_grcode", listBean.get("d_grcode"));
                    box.setSession("tem_main_type", listBean.get("d_type")); // ���� ȭ�� Ÿ��
                } else {
                    box.setSession("tem_type", "A");
                    box.setSession("tem_grcode", "N000001");
                    box.setSession("tem_menu_type", ""); //�޴� �׺���̼� Ÿ��
                    box.setSession("tem_main_type", ""); // ���� ȭ�� Ÿ��
                }
            }
            box.put("p_menuid", "010010000000");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/member/zu_login_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("peformgetSchoolCodeSearchPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �α��� üũ process
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings( { "unchecked", "static-access" })
    public void peformloginProc(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            String v_msg = "";
            // String v_url  = "/servlet/controller.mobile.main.MainServlet?p_process=mainPage";
            String v_frmURL = box.getString("p_frmURL");

            LoginBean bean = new LoginBean();

            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);

            int isOk1 = bean.login(box);

            AlertManager alert = new AlertManager();

            if (isOk1 == 1) { // �α��� ����
                if (!"".equals(v_frmURL)) {
                    v_frmURL = Base64.decode(v_frmURL);

                    out.println("<html > <head > <meta http-equiv='Content-Type' content='text/html; charset=utf-8'> ");
                    out.println("<SCRIPT LANGUAGE=\"JavaScript\"> ");
                    out.println(" function form_submit() ");
                    out.println(" { ");
                    out.println(" document.form1.action ='" + v_frmURL + "';");
                    out.println(" document.form1.submit(); ");
                    out.println(" } ");
                    out.println("</SCRIPT> ");
                    out.println("<head> ");
                    out.println("<body onload=\"form_submit();\"> ");
                    out.println("<form name = 'form1' method = 'post' action='" + v_frmURL + "'>");

                    Enumeration e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String parameterName = (String) e.nextElement();
                        String parameterValue = request.getParameter(parameterName);
                        out.println("<INPUT type='hidden' name='" + parameterName + "' value='" + parameterValue + "'>");
                    }
                    out.println("</form> ");
                    out.println("</body> </html> ");
                    out.flush();
                } else {
                    alert.alertOkMessage(out, "", "/servlet/controller.mobile.main.MainServlet?p_process=mainPage", box);
                }

            } else {
                box.put("p_loginFail", "Y");
                if (isOk1 == -1) { //ID �Ǵ� ��й�ȣ ����
                    v_msg = "��ϵ� ���̵� �����ϴ�.\\n�ٽ��ѹ� Ȯ���� �ּ���.";
                } else if (isOk1 == -2) {
                    v_msg = "�˼��մϴ�.";
                } else if (isOk1 == -3) {
                    v_msg = "��й�ȣ�� ���� �ʽ��ϴ�.";
                } else {
                    v_msg = "";
                }

                alert.historyBack(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("peformloginProc()\r\n" + ex.getMessage());
        }
    }

    /**
     * �α� �ƿ�
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performLogoutProc(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            HttpSession session = request.getSession();
            session.invalidate();

            box.put("p_process", "mainPage");

            AlertManager alert = new AlertManager();
            alert.alertOkMessage(out, "", "/", box);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLogoutProc()\r\n" + ex.getMessage());
        }
    }

}
