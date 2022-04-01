//**********************************************************
//  1. 제      목: SMS 제어하는 서블릿
//  2. 프로그램명 : SMSServlet.java
//  3. 개      요: SMS 페이지을 제어한다
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 23
//  7. 수      정:
//**********************************************************

package controller.library;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.SmsBean;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.library.SMSServlet")
public class SMSServlet extends javax.servlet.http.HttpServlet {

    /**
     * DoGet Pass get requests through to PerformTask
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @Override
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

            if (!v_process.equals("certicheck")) { //회원가입시 핸드폰 인증
                // 로긴 check 루틴 VER 0.2 - 2003.09.9
                if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
                    return;
                }
            } else {
                v_process = "insert";
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("insertPage")) { // freeMail 작성 폼으로 이동할때
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { // freeMail 작성
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("certicheck")) { // freeMail 작성
                this.performInsertSMS(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * SMS 등록페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/SMSForm.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/libary/SMSForm.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * SMS 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            SmsBean bean = new SmsBean();
            int isOk = bean.sendSms(box);

            if (isOk > 0) {
                v_msg = "SMS가 발송되었습니다.";
            } else {
                v_msg = "SMS 발송에 실패했습니다.";
            }
            alert.selfClose(out, v_msg);

            Log.info.println(this, box, v_msg + " on FreeMailServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * SMS 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertSMS(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // AlertManager alert = new AlertManager();
            String v_msg = "";
            // SmsBean bean = new SmsBean();
            // int isOk = bean.sendSms(box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, v_msg + " on FreeMailServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

}
