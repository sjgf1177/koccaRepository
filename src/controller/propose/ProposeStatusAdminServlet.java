//*********************************************************
//  1. 제      목: PROPOSE STATUS ADMIN SERVLET
//  2. 프로그램명: ProposeStatusAdminServlet.java
//  3. 개      요: 신청 현황 관리자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2003. 8. 20
//  7. 수      정:
//**********************************************************
package controller.propose;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeStatusAdminBean;
import com.credu.propose.ProposeStatusData;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.propose.ProposeStatusAdminServlet")
public class ProposeStatusAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4034080643701573495L;

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

            if (!AdminUtil.getInstance().checkRWRight("ProposeStatusAdminServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("ProposeMemberList")) { //in case of 신청명단조회
                this.performProposeMemberList(request, response, box, out);
            }
            if (v_process.equals("ProposeMemberExcel")) { //in case of propose member excel
                this.performProposeMemberExcel(request, response, box, out);
            } else if (v_process.equals("ProposeCancelMemberList")) { //in case of propose cancel member list
                this.performProposeCancelMemberList(request, response, box, out);
            } else if (v_process.equals("ProposeCancelMemberExcel")) { //in case of 반려/취소 명단 조회
                this.performProposeCancelMemberExcel(request, response, box, out);
            } else if (v_process.equals("ProposeMemberCountList")) { //in case of propose member count list
                this.performProposeMemberCountList(request, response, box, out);
            } else if (v_process.equals("ProposeMemberCountExcel")) { //in case of propose member count excel
                this.performProposeMemberCountExcel(request, response, box, out);
            } else if (v_process.equals("SendFormMail")) { //in case of send form mail
                this.performSendFormMail(request, response, box, out);
            } else if (v_process.equals("SendFreeMail")) { //in case of send free mail
                this.performSendFreeMail(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 신청명단조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeStatusAdminBean bean = new ProposeStatusAdminBean();
            ArrayList<DataBox> list = bean.selectProposeMemberList(box);

            request.setAttribute("ProposeMemberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ProposeMember_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROPOSE MEMBER EXCEL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeStatusAdminBean bean = new ProposeStatusAdminBean();
            ArrayList<DataBox> list = bean.selectProposeExcelMemberList(box);

            request.setAttribute("ProposeMemberExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ProposeMember_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 반려/취소 명단 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeCancelMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeStatusAdminBean bean = new ProposeStatusAdminBean();
            ArrayList<DataBox> list1 = bean.selectProposeCancelMemberList(box);
            request.setAttribute("ProposeCancelMember", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ProposeCancelMember_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelMemberList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROPOSE CANCEL MEMBER EXCEL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeCancelMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeStatusAdminBean bean = new ProposeStatusAdminBean();
            ArrayList<DataBox> list = bean.selectProposeCancelMemberList(box);

            request.setAttribute("ProposeCancelMemberExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ProposeCancelMember_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelMemberExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROPOSE MEMBER COUNT LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeMemberCountList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeStatusAdminBean bean = new ProposeStatusAdminBean();
            ArrayList<ProposeStatusData> list = bean.selectProposeMemberCountList(box);

            request.setAttribute("ProposeMemberCountList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ProposeMemberCount_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberCountList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROPOSE MEMBER COUNT EXCEL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeMemberCountExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeStatusAdminBean bean = new ProposeStatusAdminBean();
            ArrayList<ProposeStatusData> list = bean.selectProposeMemberCountList(box);

            request.setAttribute("ProposeMemberCountExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ProposeMemberCount_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberCountExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * SEND FORM MAIL(독료메일)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSendFormMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeStatusAdminBean bean = new ProposeStatusAdminBean();

            int isOk = bean.sendFormMail(box);
            String v_mailcnt = isOk + "";
            String v_msg = "";
            String v_url = "/servlet/controller.propose.ProposeStatusAdminServlet";
            box.put("p_process", box.getString("p_rprocess"));
            box.put("p_mailcnt", v_mailcnt);
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "mail.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "mail.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFormMail()\r\n" + ex.getMessage());
        }
    }

    /**
     * SEND FREE MAIL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSendFreeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }
}