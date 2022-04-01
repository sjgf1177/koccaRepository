// *********************************************************
// 1. 제 목: PROPOSE STATUS ADMIN SERVLET
// 2. 프로그램명: StudentManagerServlet.java
// 3. 개 요: 신청 현황 관리자 servlet
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 2003. 8. 20
// 7. 수 정:
// *********************************************************
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
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ApprovalBean;
import com.credu.propose.ProposeWizardBean;
import com.credu.propose.StudentManagerBean;
import com.credu.system.AdminUtil;
import com.credu.system.CodeConfigBean;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.propose.StudentManagerServlet")
public class StudentManagerServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
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

            if (!AdminUtil.getInstance().checkRWRight("StudentManagerServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("StudentMemberList")) { // in case of propose member list
                this.performStudentMemberList(request, response, box, out);

            } else if (v_process.equals("StudentMemberExcel")) { // in case of propose member excel
                this.performStudentMemberExcel(request, response, box, out);

            } else if (v_process.equals("StudentMemberDelete")) { // 신청자 삭제
                this.performStudentMemberDelete(request, response, box, out);

            } else if (v_process.equals("SendFormMail")) { // in case of send form mail
                this.performSendFormMail(request, response, box, out);

            } else if (v_process.equals("SendFreeMail")) { // in case of send free mail
                this.performSendFreeMail(request, response, box, out);

            } else if (v_process.equals("openchangeseq")) { // in case of openchangeseq
                this.performOpenChangeSeq(request, response, box, out);

            } else if (v_process.equals("savechangeseq")) { // in case of openchangeseq
                this.performSaveChangeSeq(request, response, box, out);

            } else if (v_process.equals("addtargetSearch")) { // in case of 대상자 검색페이지 팝업
                this.performAddTargetSearch(request, response, box, out);

            } else if (v_process.equals("memSearch")) { // in case of 추가대상 검색
                this.performMemSearch(request, response, box, out);

            } else if (v_process.equals("stuedntinsert")) { // in case of 교육생추가
                this.performStudentInsert(request, response, box, out);

            } else if (v_process.equals("rejectedReason")) { // 반려코드 입력 Open창
                this.performRejectedReason(request, response, box, out);

            } else if (v_process.equals("saverejectreason")) { // 반려처리
                this.performSaveRejectReason(request, response, box, out);

            } else if (v_process.equals("studentapproval")) { // 재승인처리
                this.performStudentApproval(request, response, box, out);

            } else if (v_process.equals("studentnogoyong")) { // 고용보험미적용자변경처리
                this.performstudentNogoyong(request, response, box, out);

            } else if (v_process.equals("studentgoyong")) { // 고용보험적용자변경처리
                this.performstudentGoyong(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * PROPOSE MEMBER LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudentMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "/learn/admin/propose/za_StudentMember_L.jsp";
            DataBox dbox = null;

            StudentManagerBean bean = new StudentManagerBean();
            dbox = bean.isClosedInfo(box);
            request.setAttribute("subjinfo", dbox);

            String v_isaddpossible = bean.isOverflowStudent(box);
            box.put("onOff", 1);
            ArrayList list = bean.selectStduentMemberList(box);
            box.put("p_isaddpossible", v_isaddpossible);
            request.setAttribute("StudentMemberList", list);

            ApprovalBean abbean = new ApprovalBean();
            DataBox dbox1 = abbean.getSubjInfomat(box);
            request.setAttribute("isManagerStatus", dbox1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberList()\r\n" + ex.getMessage());
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
    public void performStudentMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentManagerBean bean = new StudentManagerBean();
            ArrayList list = bean.selectStduentMemberList(box);

            request.setAttribute("StudentMemberExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_StudentMember_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 신청자 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudentMemberDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/servlet/controller.propose.StudentManagerServlet";
            box.put("p_process", "StudentMemberList");

            StudentManagerBean bean = new StudentManagerBean();
            int isOk = bean.stduentMemberDelete(box);
            String v_msg = "";
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_return_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performStudentMemberDelete()\r\n" + ex.getMessage());
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
    public void performSendFormMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentManagerBean bean = new StudentManagerBean();

            int isOk = bean.sendFormMail(box);
            String v_mailcnt = isOk + "";
            String v_msg = "";
            String v_url = "/servlet/controller.propose.StudentManagerServlet";
            box.put("p_process", box.getString("p_rprocess"));
            box.put("p_mailcnt", v_mailcnt);
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "mail.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, false);
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

    /**
     * OPEN CHANGE SUBJSEQ LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOpenChangeSeq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "/learn/admin/propose/za_OpenChangeSeq.jsp";

            StudentManagerBean bean = new StudentManagerBean();
            ArrayList list = bean.selectPossibleChangeSeq(box);

            request.setAttribute("ChangeSeqList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenChangeSeq()\r\n" + ex.getMessage());
        }
    }

    /**
     * OPEN CHANGE SUBJSEQ LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSaveChangeSeq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "/servlet/controller.propose.StudentManagerServlet";
            box.put("p_process", "StudentMemberList");
            box.put("s_action", "go");
            String v_msg = "";

            StudentManagerBean bean = new StudentManagerBean();
            int isOk = bean.updateChangeSeq(box);

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "save.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "save.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSaveChangeSeq()\r\n" + ex.getMessage());
        }
    }

    /**
     * 대상자선정 (팝업)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performAddTargetSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String v_return_url = "/learn/admin/propose/za_AddTargetSearch_L.jsp";

            if (box.getString("p_action").equals("go")) {

                ProposeWizardBean bean = new ProposeWizardBean();
                ArrayList list = bean.SelectAcceptTargetMember(box);
                request.setAttribute("selectList", list);
            }

            DataBox dbox = null;
            StudentManagerBean bean1 = new StudentManagerBean();
            dbox = bean1.isClosedInfo(box);
            request.setAttribute("subjinfo", dbox);

            ArrayList list1 = bean1.selectPossibleAddSeq(box);
            request.setAttribute("PossSeqList", list1);

            // ArrayList list2 = bean1.selectPossibleCompany(box);
            // request.setAttribute("PossibleCompany", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
            Log.info.println(this, box, "Dispatch to /learn/admin/propose/za_AddTargetSearch_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenSubjseq()\r\n" + ex.getMessage());
        }
    }

    public void performMemSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_AddTargetSearch_L.jsp";

            if (box.getString("p_action").equals("go")) {
                ProposeWizardBean bean = new ProposeWizardBean();
                ArrayList list = bean.SelectAcceptTargetMember(box);
                request.setAttribute("selectList", list);
            }

            DataBox dbox = null;
            StudentManagerBean bean1 = new StudentManagerBean();

            dbox = bean1.isClosedInfo(box);
            request.setAttribute("subjinfo", dbox);

            ArrayList list1 = bean1.selectPossibleAddSeq(box);
            request.setAttribute("PossSeqList", list1);

            ArrayList list2 = bean1.selectPossibleCompany(box);
            request.setAttribute("PossibleCompany", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 선정대상자 입과처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performStudentInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.StudentManagerServlet";

            StudentManagerBean bean = new StudentManagerBean();
            int isOk = bean.AcceptTargetMember(box);

            String v_msg = "";
            box.put("p_process", "memSearch");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "studentinsert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "studentinsert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 반려시 코드입력 Open창
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRejectedReason(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ArrayList list = CodeConfigBean.getCodeList("reject_kind", "", 1);
            request.setAttribute("reasonlist", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_OpenRejectReason_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 반려처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSaveRejectReason(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.StudentManagerServlet";

            StudentManagerBean bean = new StudentManagerBean();
            int isOk = bean.SaveRejectProcess(box);
            String v_msg = "";
            box.put("p_process", "StudentMemberList");
            box.put("s_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "reject.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "reject.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 승인처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudentApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.StudentManagerServlet";

            StudentManagerBean bean = new StudentManagerBean();
            int isOk = bean.SaveApprovalProcess(box);

            String v_msg = "";
            box.put("p_process", "StudentMemberList");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "studentapproval.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "studentapproval.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 고용보험미적용자변경처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performstudentNogoyong(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.StudentManagerServlet";

            StudentManagerBean bean = new StudentManagerBean();
            int isOk = bean.SaveNogoyongProcess(box);

            String v_msg = "";
            box.put("p_process", "StudentMemberList");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "studentvnogoyong.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "studentvnogoyong.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 고용보험미적용자변경처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performstudentGoyong(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.StudentManagerServlet";

            StudentManagerBean bean = new StudentManagerBean();
            int isOk = bean.SaveGoyongProcess(box);

            String v_msg = "";
            box.put("p_process", "StudentMemberList");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "studentvnogoyong.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "studentvnogoyong.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

}