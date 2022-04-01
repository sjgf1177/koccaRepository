//**********************************************************
//  1. 제      목: FAQ를 제어하는 서블릿
//  2. 프로그램명 : FaqServlet.java
//  3. 개      요: FAQ의 페이지을 제어한다(HOMEPAGE)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.26
//  7. 수      정:
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

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("adminiList")) { // 리스트
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("adminiListkia")) { // 리스트
                this.performSelectListkia(request, response, box, out);
            } else if (v_process.equals("mail")) { // 리스트
                //this.performmailPage(request, response, box, out);
            } else if (v_process.equals("send")) { // 리스트
                this.performMail(request, response, box, out);
            } else if (v_process.equals("select2")) { //      상세보기할때 (팀원신청화면) - VLC
                this.performSelect2(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 리스트
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
     * Exception { try { request.setAttribute("requestbox", box); // 명시적으로 box
     * 객체를 넘겨준다
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
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            HomePageAdminBean admin = new HomePageAdminBean();
            boolean isMailed = false;

            isMailed = admin.selectPds(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageAdminServlet";
            box.put("p_process", "adminiList");

            AlertManager alert = new AlertManager();

            if (isMailed) {
                v_msg = "메일을 전송하였습니다";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "전송 에러!";
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
