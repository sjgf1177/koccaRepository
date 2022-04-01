//*********************************************************
//  1. 제      목: STUDENT STATUS ADMIN SERVLET
//  2. 프로그램명: StudentStatusAdminServlet.java
//  3. 개      요: 입과 현황 관리자 servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//**********************************************************
package controller.study;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.StudentStatusAdminBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.study.StudentStatusAdminServlet")
public class StudentStatusAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
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

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("StudentStatusAdminServlet", v_process, out, box)) {
                return;
            }

            if(v_process.equals("StudentMemberList")){                  //in case of 입과명단조회
                this.performStudentMemberList(request, response, box, out);
            }
            if(v_process.equals("StudentMemberExcel")){                 //in case of 입과명단조회 excel
                this.performStudentMemberExcel(request, response, box, out);
            }
            else if(v_process.equals("CompleteMemberList")){        //in case of complete member list
                this.performCompleteMemberList(request, response, box, out);
            }
            else if(v_process.equals("CompleteMemberExcel")){       //in case of complete member excel
                this.performCompleteMemberExcel(request, response, box, out);
            }
            else if(v_process.equals("StudentMemberCountList")){        //in case of student member count list
                this.performStudentMemberCountList(request, response, box, out);
            }
            else if(v_process.equals("StudentMemberCountExcel")){       //in case of student member count excel
                this.performStudentMemberCountExcel(request, response, box, out);
            }

            else if(v_process.equals("SendStudyBeforeMail")){                  //in case of 사전예고메일
                this.performSendStudyBeforeMail(request, response, box, out);
            }
            else if(v_process.equals("SendStudyAfterMail")){                  //in case of 사후통보메일
                this.performSendStudyAfterMail(request, response, box, out);
            }
            else if(v_process.equals("SendFormMail")){                  //in case of send form mail
                this.performSendFormMail(request, response, box, out);
            }
            else if(v_process.equals("SendFreeMail")){                  //in case of send free mail
                this.performSendFreeMail(request, response, box, out);
            }
            else if(v_process.equals("SendSMS")){                  //in case of send free mail
                this.performSendSMS(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    STUDENT MEMBER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performStudentMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();
            ArrayList list = bean.selectStudentMemberList(box);

            request.setAttribute("StudentMemberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudentMember_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberList()\r\n" + ex.getMessage());
        }
    }

    /**
    STUDENT MEMBER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performStudentMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();
            ArrayList list = bean.selectStudentMemberList(box);

            request.setAttribute("StudentMemberExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudentMember_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberExcel()\r\n" + ex.getMessage());
        }
    }

    /**
    COMPLETE MEMBER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performCompleteMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("CompleteMemberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_CompleteMember_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberList()\r\n" + ex.getMessage());
        }
    }

    /**
    COMPLETE MEMBER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performCompleteMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("CompleteMemberExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_CompleteMember_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberExcel()\r\n" + ex.getMessage());
        }
    }


    /**
    STUDENT MEMBER COUNT LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performStudentMemberCountList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();
            ArrayList list = bean.selectStudentMemberCountList(box);

            request.setAttribute("StudentMemberCountList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudentMemberCount_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberCountList()\r\n" + ex.getMessage());
        }
    }

    /**
    STUDENT MEMBER COUNT EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performStudentMemberCountExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();
            ArrayList list = bean.selectStudentMemberCountList(box);

            String v_excelprocess = box.getString("p_excelprocess");
            String v_url = "";

            if(v_excelprocess.equals("StuMemberALLExcel")){
                v_url = "/learn/admin/study/za_SelectedSubjAll_E.jsp";
            }else if(v_excelprocess.equals("StuMemberWaitExcel")){
                v_url = "/learn/admin/study/za_SelectedSubjWait_E.jsp";
            }else if(v_excelprocess.equals("StuMemberProgExcel")){
                v_url = "/learn/admin/study/za_SelectedSubjPorgress_E.jsp";
            }else if(v_excelprocess.equals("StuMemberFinishExcel")){
                v_url = "/learn/admin/study/za_SelectedSubjFinish_E.jsp";
            }

            request.setAttribute("StudentMemberCountExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberCountExcel()\r\n" + ex.getMessage());
        }
    }






    /**
    SEND STUDY BEFORE MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */

    public void performSendStudyBeforeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();

            int isOk            = bean.sendStudyBeforeMail(box);
            String v_mailcnt    = isOk+"";
            String v_msg = "";
            String v_url = "/servlet/controller.study.StudentStatusAdminServlet";
            box.put("p_process",box.getString("p_rprocess"));
            box.put("p_mailcnt",v_mailcnt);
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "mail.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "mail.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFormMail()\r\n" + ex.getMessage());
        }
    }

    /**
    SEND STUDY AFTER MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */

    public void performSendStudyAfterMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();

            int isOk            = bean.sendStudyAfterMail(box);

            String v_mailcnt    = isOk+"";
            String v_msg = "";
            String v_url = "/servlet/controller.study.StudentStatusAdminServlet";
            box.put("p_process",box.getString("p_rprocess"));
            box.put("p_mailcnt",v_mailcnt);
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "mail.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "mail.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFormMail()\r\n" + ex.getMessage());
        }
    }

    /**
    SEND FORM MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSendFormMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();

            int isOk            = bean.sendFormMail(box);
            String v_mailcnt    = isOk+"";
            String v_msg = "";
            String v_url = "/servlet/controller.study.StudentStatusAdminServlet";

            box.put("p_process",box.getString("p_rprocess"));
            box.put("p_mailcnt",v_mailcnt);
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "mail.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "mail.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFormMail()\r\n" + ex.getMessage());
        }
    }


    /**
    SEND FREE MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSendFreeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }

    /**
    SEND SMS
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSendSMS(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/SMSForm.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }


}