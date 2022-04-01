// *********************************************************
// 1. �� ��: SUBJECT INFORMATION USER SERVLET
// 2. ���α׷���: ProposeCourseServlet.java
// 3. �� ��: �����ȳ� ����� servlet
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: ������ 2003. 8. 19
// 7. �� ��:
// **********************************************************
package controller.propose;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.SearchAdminBean;
import com.credu.homepage.InstitutionAdminBean;
import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.ConfigSet;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.SmsBean;
import com.credu.library.StringManager;
import com.credu.mobile.subj.SubjectBean;
import com.credu.propose.ProposeCourseBean;
import com.credu.research.SulmunRegistResultBean;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.propose.ProposeCourseServlet")
public class ProposeCourseServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            v_process = box.getString("p_process");
            
            box.put("p_grgubun", "G01");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            if (v_process.equals("SubjectCreduPropose")) {
                String creduuserid = box.getString("p_userid");
                box.setSession("userid", creduuserid);
            }

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            // �����̸� ���� �α��� üũ ���� ( ��õ�������� ���)

            if (!(v_process.equals("SubjectPreviewPopup") || v_process.equals("SubjectList") || v_process.equals("SubjectPreviewPage") || v_process.equals("TotalSubjectList") || v_process.equals("Curriculum") || v_process.equals("EduSystem")
                    || v_process.equals("SubjectListJikmu") || v_process.equals("SubjectListJikup") || v_process.equals("previewSubjDetailPage") || v_process.equals("LiteratureSubjectList") || v_process.equals("selectReviewListForAjax"))) {
                if (!AdminUtil.getInstance().checkLogin(out, box)) {
                    this.LoginChk(request, response, box, out);
                }
            }

            if (v_process.equals("SubjectList")) { // ��������Ʈ
                this.performSubjectList(request, response, box, out);

            } else if (v_process.equals("SubjectListJikmu")) { // ��������Ʈ /����
                this.performSubjectListJikmu(request, response, box, out);

            } else if (v_process.equals("SubjectListJikup")) { // ��������Ʈ /����
                this.performSubjectListJikup(request, response, box, out);

            } else if (v_process.equals("TotalSubjectList")) { // ��ü��������
                this.performTotalSubjectList(request, response, box, out);

            } else if (v_process.equals("SubjectListAll")) { // ��ü�������� (������)
                this.performSubjectListAll(request, response, box, out);

            } else if (v_process.equals("SubjectListBest")) { // �α� ���� ����
                this.performSubjectListBest(request, response, box, out);

            } else if (v_process.equals("SubjectPreviewPage")) { // ���� �̸�����
                this.performSubjectPreviewPage(request, response, box, out);

            } else if (v_process.equals("SubjectPreviewPopup")) { // ���� �̸����� �˾�
                this.performSubjectPreviewPopup(request, response, box, out);

            } else if (v_process.equals("SeqPreviewPage")) { // �������� �̸�����
                this.performSubjSeqPreviewPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposePage")) { // ������û ȭ��
                this.performSubjectEduProposePage(request, response, box, out);

            } else if (v_process.equals("SubjectEduPropose")) { // ������û
                this.performSubjectEduPropose(request, response, box, out);

            } else if (v_process.equals("SpotSubjectEduPropose")) { // ������û(���)
                this.performSpotSubjectEduPropose(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeDelete")) { // ������û����
                this.performSubjectEduProposeDelete(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeListPage")) { // ������û���
                this.performSubjectEduProposeListPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeCheckListPage")) { // ������û ����Ȯ��-������ø
                this.performSubjectEduProposeCheckListPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeChief")) { // in case of subject education propose
                this.performSubjectEduProposeChief(request, response, box, out);

            } else if (v_process.equals("SubjectAppProposeOpenPage")) { // in case of subject approval propose page
                this.performSubjectAppProposeOpenPage(request, response, box, out);

            } else if (v_process.equals("OfficerChoiceOpenPage")) { // in case of officer choice open page
                this.performOfficerChoiceOpenPage(request, response, box, out);

            } else if (v_process.equals("SubjectCreduPropose")) { // in case of subject credu propose
                this.performSubjectCreduPropose(request, response, box, out);

            } else if (v_process.equals("SubjectCreduPropose2")) { // in case of subject credu propose
                this.performSubjectCreduPropose2(request, response, box, out);

            } else if (v_process.equals("EducationYearlySchedule")) { // ������������
                this.performEducationYearlySchedule(request, response, box, out);

            } else if (v_process.equals("EducationMonthlySchedule")) { // ������������
                this.performEducationMonthlySchedule(request, response, box, out);

            } else if (v_process.equals("CourseSubjOpenPage")) { // in case of course subject view open page
                this.performCourseSubjOpenPage(request, response, box, out);

            } else if (v_process.equals("ContentResearch")) { // ������ �������
                this.performContentResearch(request, response, box, out);

            } else if (v_process.equals("ProposeListPage")) { // ��û�����ȸ
                this.performProposeListPage(request, response, box, out);

            } else if (v_process.equals("LectureList")) { // ���Ǹ��� ����Ʈ
                this.performLectureList(request, response, box, out);

            } else if (v_process.equals("insertPreviewLog")) { // ������ �α�
                this.performInsertPreviewLog(request, response, box, out);

            } else if (v_process.equals("OffLineApply")) { // �������� ��û �Ҷ�
                this.performApply(request, response, box, out);

            } else if (v_process.equals("OffLineCancel")) { // �������� ��û ����Ҷ�
                this.performApplyCancel(request, response, box, out);

            } else if (v_process.equals("OffLineSubjPage")) { // �������� ��û/��� ��������
                // �̵�
                this.performApplyPage(request, response, box, out);

            } else if (v_process.equals("OffLineView")) { // �������� ��û/��� �󼼺���
                this.performApplyViewPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduBill")) { // ������û�� ���� ���� ������.
                this.performEduBill(request, response, box, out);

            } else if (v_process.equals("SubjectEduInputBill")) { // �������Ա� ������.
                this.performEduInputBill(request, response, box, out);

            } else if (v_process.equals("BillCheck")) { // �������Ա� ������ (�Ա��� �Է�).
                this.performEduInputCheck(request, response, box, out);

            } else if (v_process.equals("SubjectEduInputCredit")) { // ī�� ���� �Է�
                // ( Link)
                this.performEduInputCredit(request, response, box, out);

            } else if (v_process.equals("SubjectIntro")) { // ������û�ȳ�
                this.performSubjectIntro(request, response, box, out);

            } else if (v_process.equals("CreditCard")) { // ī�� ������ �б�.
                this.performCreditCard(request, response, box, out);

            } else if (v_process.equals("Curriculum")) { // Ŀ��ŧ��.
                this.performCurriculum(request, response, box, out);

            } else if (v_process.equals("EduSystem")) { // �¶��α���ü�赵
                this.performEduSystem(request, response, box, out);

            } else if (v_process.equals("previewSubjDetailPage")) { // ������ ������ ���� ������û ���� ���������� �̵�
                this.performPreviewSubjDetailPage(request, response, box, out);

            } else if (v_process.equals("LiteratureSubjectList")) { // �ι��� ���� ��� ��ȸ
                this.performLiteratureSubjectList(request, response, box, out);

            } else if (v_process.equals("selectReviewListForAjax")) {
                this.performSelectReviewListForAjax(request, response, box, out); // ajax�� �̿��Ͽ� �ı� ��� ��ȸ
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �ι��� ���� ��� ��ȸ
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performLiteratureSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list = bean.selectLiteratureSubjectList(box);

            // ���� ���ڿ� ���� ������ �ι��� ���� �� ����
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            request.setAttribute("literatureSubjectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/2013/portal/propose/zu_LiteratureSubject_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLiteratureSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void LoginChk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("tUrl", request.getRequestURI());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * OffLine ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.ProposeCourseServlet";

            ProposeCourseBean bean = new ProposeCourseBean();
            int isOk = bean.OffLineApplySubject(box);

            String v_msg = "";
            box.put("p_process", "OffLineSubjPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                // v_msg = "insert.ok";
                v_msg = "��û �Ǿ����ϴ�.";
                // box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                // v_msg = "insert.fail";
                v_msg = "�������� ���� ��û�� �����Ͽ����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * OffLine ��û ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApplyCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.ProposeCourseServlet";

            ProposeCourseBean bean = new ProposeCourseBean();
            int isOk = bean.OffLineApplyCancelSubject(box);

            String v_msg = "";
            box.put("p_process", "OffLineSubjPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                // v_msg = "insert.ok";
                v_msg = "��û ��� �Ǿ����ϴ�.";
                // box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                // v_msg = "insert.fail";
                v_msg = " ��û ��ҿ� �����Ͽ����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��ü ���� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/game/course/gu_OffLineSubjApply_L.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ���� �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApplyViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            DataBox dbox = bean.OffLineApplyView(box);

            request.setAttribute("OffLineSubjApply", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_OffLineSubjApply_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ �� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performContentResearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            box.put("p_action", "go");
            SulmunRegistResultBean bean = new SulmunRegistResultBean();
            ArrayList list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1);

            // MainSubjSearchBean bean = new MainSubjSearchBean();
            // ArrayList list = bean.selectSubjSearch(box);

            //
            // request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_ContentRearch_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * COURSE SUBJECT VIEW OPEN PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCourseSubjOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list = bean.selectCourseSubjList(box);

            request.setAttribute("CourseSubjList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_CourseSubjList.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CourseSubjOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û�ȳ� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCreditCard(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            box.put("p_subj", request.getParameter("p_subj"));
            box.put("p_iscourseYn", request.getParameter("p_iscourseYn"));
            box.put("p_course", request.getParameter("p_course"));
            box.put("p_subjseq", request.getParameter("p_subjseq"));
            box.put("p_year", request.getParameter("p_year"));
            box.put("p_grtype", request.getParameter("p_grtype"));
            box.put("p_transaction", request.getParameter("transaction"));
            box.put("p_realpay", request.getParameter("amount"));
            box.put("p_productcode", request.getParameter("productcode"));
            box.put("p_authnumber", request.getParameter("authnumber"));
            box.put("p_payselect", "D");

            if (request.getParameter("respcode").equals("0000")) {
                performSubjectEduPropose(request, response, box, out);
            }

            // ServletContext sc = getServletContext();
            // RequestDispatcher rd =
            // sc.getRequestDispatcher("/learn/user/game/course/gu_ApplyIntro.jsp");
            // rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE Bill
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            DataBox dbox = bean.getSelectBill(box);
            request.setAttribute("selectEduBill", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeBill_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationMonthlySchedule(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = bean.selectEducationMonthlyList(box);

            request.setAttribute("EducationMonthlyList", list1);
            ServletContext sc = getServletContext();
            // RequestDispatcher rd =
            // sc.getRequestDispatcher("/learn/user/propose/zu_EducationMonthlySchedule_L.jsp");
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_EducationMonthlySchedule_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("educationMonthlySchedule()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationYearlySchedule(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = bean.selectEducationYearlyList(box);

            request.setAttribute("EducationYearlyList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_EducationYearlySchedule_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("educationYearlySchedule()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE Bill(�������Ա�)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduInputBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeInputBill_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE Bill(�������Ա�)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduInputCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeInputBill_Check.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ī������� ������ ����.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduInputCredit(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/credit/edacom_credit.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ �α� ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPreviewLog(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_msg = "";

            bean.insertPreviewLog(box);

            AlertManager alert = new AlertManager();
            v_msg = "";

            alert.selfClose(out, v_msg);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���Ǹ��� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLectureList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            if (v_isonoff.equals("ON")) { // ���̹� ������ ��� ��������Ʈ
                ArrayList list1 = bean.selectLessonList(box);
                request.setAttribute("lessonList", list1);
                v_url = "/learn/user/course/zu_LectureListPop.jsp";
            } else if (v_isonoff.equals("OFF")) { // ���� ������ ��� ���¸���Ʈ
                ArrayList list1 = bean.selectLectureList(box);
                request.setAttribute("lectureList", list1);
                v_url = "/learn/user/course/zu_LectureListPop.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * OFFICER CHOICE OPEN PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOfficerChoiceOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SearchAdminBean bean = new SearchAdminBean();
            ArrayList list = bean.searchMember(box);

            request.setAttribute("OfficerChoiceList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_OfficerChoiceList.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("officerChoiceOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��û�����ȸ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list = bean.selectProposeList(box);

            request.setAttribute("ProposeList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_ProposeName_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT APPROVAL PROPOSE PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectAppProposeOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_SubjectAppPropose.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectAppProposeOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * CREDU PROPOSE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectCreduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            // int check =
            bean.setCreduSession(box); // �ӽ� ����
            int isOk = bean.insertSubjectEduPropose(box);
            String v_msg = "";

            String v_url = box.getString("p_purl");
            if (v_url.length() == 0) {
                v_url = "/servlet/controller.propose.ProposeCourseServlet";
                box.put("p_process", "SubjectList");
            }

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                // alert.selfClose(out, v_msg);
            } else {
                v_msg = "insert.fail";

                alert.alertFailMessage(out, v_msg);
                // alert.selfClose(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectCreduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * CREDU PROPOSE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectCreduPropose2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            // int check =
            bean.setCreduSession(box); // �ӽ� ����
            int isOk = bean.insertSubjectEduPropose(box);
            String v_msg = "";

            String v_url = box.getString("p_purl");
            if (v_url.length() == 0) {
                v_url = "/servlet/controller.propose.ProposeCourseServlet";

            }
            box.put("p_process", "SubjectPreviewPage");
            box.put("p_isonoff", box.getString("p_isonoff"));

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                // alert.selfClose(out, v_msg);
            } else {
                v_msg = "insert.fail";

                alert.alertFailMessage(out, v_msg);
                // alert.selfClose(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectCreduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            MemberInfoBean bean = new MemberInfoBean();

            AlertManager alert = new AlertManager();

            String v_msg = "";
            String v_url = "";

            String upperclass = box.getString("p_upperclass");
            // String area = box.getString("p_area");
            
            // ���������� 2������,�������� �̼���
            int isOk = 0;

            isOk = bean.memberSubjLimit(box);

            if (isOk == 1) { // ������ ������� �̼���

                v_msg = "pregrseq.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);

            } else if (isOk == 2) { // �ش����� ���� ���� ������û���Ѽ� �ʰ�

                v_msg = "nowgrseq3.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;
                box.put("openercount", "3");

                alert.alertOkMessage(out, v_msg, v_url, box, true, true, false, false);

            } else if (isOk == 3) { // Ȩ�ؼ��� �ش����� ���� ���� ������û���Ѽ� �ʰ�

                v_msg = "nowgrseq2.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);

            } else if (isOk == 5) { // �������б� ������û���Ѽ� �ʰ�

                v_msg = "nowgrseq3.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);

            } else if (isOk == 4) {
                v_msg = "subjApplyTimeOver.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                box.put("onOff", 1);
                DataBox box1 = bean.memberInfoViewNew(box);
                // ���ı�û, T3, �ѱ���������(KPU), �λ�泲��ǥ���(KNN), ������ȭ���������� ���ᵵ ����� ó��
                // �ϰ� �ݾ��� ȭ�鿡 ǥ�ø� �Ѵ�.
                if (box.getSession("tem_grcode").equals("N000030") || box.getSession("tem_grcode").equals("N000034") || box.getSession("tem_grcode").equals("N000035") || box.getSession("tem_grcode").equals("N000036")
                        || box.getSession("tem_grcode").equals("N000033") || box.getSession("tem_grcode").equals("N000040") || box.getSession("tem_grcode").equals("N000043")) {
                    box1.put("d_biyong1", box1.getString("d_biyong"));
                    box1.put("d_biyong", "0");
                } else
                    box1.put("d_biyong1", box1.getString("d_biyong"));

                request.setAttribute("resultbox", box1);
                
                v_url = "/learn/user/portal/propose/zu_Subject_I.jsp";
                
                if(box.getSession("tem_type").equals("B")){
                	v_url = "/learn/user/typeB/propose/zu_Subject_I.jsp";
                }

                request.setAttribute("requestbox", box);
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            request.setAttribute("resultbox", bean.getSubjectEduProposeList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/propose/zu_SubjectPropose_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û ����Ȯ��-������ø
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeCheckListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            request.setAttribute("resultbox", bean.getSubjectEduProposeCheckList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/propose/zu_SubjectProposeCheck_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            String v_msg = "";
            String v_biyong = box.get("p_biyong");
            String v_favoryn = box.getString("p_favoryn");
            String v_subj = box.getString("p_subj");
            String v_subjnm = box.getString("p_subjnm");
            
            box.put("onOff", 1);
            box.put("subj", v_subj);

            String upperclass = box.getString("p_upperclass");
            String area = box.getString("p_area");

            // String v_url ="/servlet/controller.study.MyClassServlet?p_process=ProposeCancelPage";
            // String v_url ="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

            String v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=" + area + "&p_upperclass=" + upperclass;
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage";
            }

            MemberInfoBean mbean = new MemberInfoBean();
            AlertManager alert = new AlertManager();
            SubjectBean dbean = new SubjectBean();

            int isOk = mbean.memberInfoUpdateNew(box);

            if (isOk > 0) {

                isOk = bean.insertSubjectEduPropose(box);

                if (isOk > 0) {
                    if (v_biyong.equals("0")) {
                        v_msg = "propose.ok.zero";
                    } else {
                        v_msg = "propose.ok";
                    }

                    if (v_favoryn.equals("Y")) {
                        box.put("classType", "01");
                        dbean.cancelSubjFavor(box);//������û�� ����� ȣ��.. 
                    }

                    alert.alertOkMessage(out, v_msg, v_url, box, true, true, false, true);
                    if (box.getSession("tem_grcode").equals("N000210")){
                    	DateFormat df = new SimpleDateFormat("yyyyMMdd");
                        Date dt = new Date();
                        String tran_date = df.format(dt);
                        String tDate = StringManager.substring(tran_date, 4, 6) + "�� " + StringManager.substring(tran_date, 6, 8) + "�� ";
                        String p_msg = "";
                        
                        if (v_subjnm.length() > 13)
                            p_msg = tDate + StringManager.substring(v_subjnm, 0, 12) + ".. ";
                        else
                            p_msg = tDate + v_subjnm;
                        
                        out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
                        out.println("<body>");
                        out.println("<form name = 'form1' method='post'></form></body>");
                        out.println("<script language = 'javascript'>");
                        out.println("document.form1.target = window.opener.name");
                        out.println("window.opener.location.reload()");
       
                        out.println("</script>");
                        out.println("</html>");
                        
                        SmsBean smsBean = new SmsBean();
                        // �߽Ź�ȣ properites ���� ������ ����
                        ConfigSet conf = new ConfigSet();
                        
                        String p_toPhone = bean.getNumber(box); 
                        String p_fromPhone = "02-6310-0770";
                        p_msg = "[EDUKOCCA]\n" + p_msg + " ������û�� �Ϸ�Ǿ����ϴ�.";
                        boolean result = smsBean.sendSMSMsg(p_toPhone, p_fromPhone, p_msg, "");
                        if(result){
                            System.out.println("sms �߼� ����");
                        }
                    }

                } else {
                    v_msg = "propose.fail";
                    v_msg = box.getStringDefault("err_msg", v_msg);
                    alert.alertFailMessage(out, v_msg);
                }
            } else {
                v_msg = "subjApplyTimeOver.no";
                v_msg = box.getStringDefault("err_msg", v_msg);
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    
    /**
     * ���� ��û(���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSpotSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_subj = box.getString("p_subj");
            String v_subjnm = box.getString("p_subjnm");
            
            box.put("onOff", 1);
            box.put("subj", v_subj);

            //String v_url = "/servlet/controller.contents.EduStart?p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
            
            MemberInfoBean mbean = new MemberInfoBean();
            AlertManager alert = new AlertManager();
            SubjectBean dbean = new SubjectBean();
            
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date dt = new Date();
            String tran_date = df.format(dt);
            String tDate = StringManager.substring(tran_date, 4, 6) + "�� " + StringManager.substring(tran_date, 6, 8) + "��";
            String p_msg = "";
            
            if (v_subjnm.length() > 13)
                p_msg = tDate + StringManager.substring(v_subjnm, 0, 12) + "..";
            else
                p_msg = tDate + v_subjnm;
            
            int chkSubj = 0;
            chkSubj = bean.chkSubjDupl(box);
            
            if( chkSubj < 1){
                
                int isOk = mbean.memberInfoUpdateNew(box);
    
                    isOk = bean.insertSpotSubjectEduPropose(box);
    
                    if(isOk > 0){
    
                    	out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
                        out.println("<body>");
                        out.println("<form name = 'form1' method='post'></form></body>");
                        out.println("<script language = 'javascript'>");
                        out.println("document.form1.target = window.opener.name");
                        out.println("window.opener.location.reload()");
       
                        out.println("</script>");
                        out.println("</html>");
                        
                        SmsBean smsBean = new SmsBean();
                        // �߽Ź�ȣ properites ���� ������ ����
                        ConfigSet conf = new ConfigSet();
                        
                        String p_toPhone = bean.getNumber(box); 
                        String p_fromPhone = conf.getProperty("sms.admin.comptel");
                        p_msg = "[EDUKOCCA]\n" + p_msg + " �н��� ���۵Ǿ����ϴ�.";
                        boolean result = smsBean.sendSMSMsg(p_toPhone, p_fromPhone, p_msg, "");
                        if(result){
                            System.out.println("sms �߼� ����");
                        }
                    } else {
                    	out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
                        out.println("<body>");
                        out.println("<form name = 'form1' method='post'></form></body>");
                        out.println("<script language = 'javascript'>");
                        out.println("document.form1.target = window.opener.name");
                        out.println("window.opener.location.reload()");
                        out.println("document.form1.submit()");
                        out.println("self.close()");
                        out.println("</script>");
                        out.println("</html>");
                    }
            }
                
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/2013/portal/propose/zu_callEduRoom_L.jsp");
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * ���� ��û���� - �̿�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            String v_msg = "";
            String v_billYn = box.getString("p_billYn");

            String v_url = "/servlet/controller.study.MyClassServlet?p_process=ProposeCancelPage";

            int isOk = 0;
            isOk = bean.insertSubjectEduPropose(box);

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "propose.ok";
                if (v_billYn.equals("Y")) {
                    alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                } else {
                    alert.alertOkMessage(out, v_msg, v_url, box);
                }
            } else {
                v_msg = "propose.fail";
                v_msg = box.getStringDefault("err_msg", v_msg);
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeChief(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            DataBox dbox = bean.getSelectChief(box);
            request.setAttribute("selectChief", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_ProposeApproval_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û�ȳ� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectIntro(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/game/course/gu_ApplyIntro1.jsp";
            String tab = box.getString("p_tab");
            if (tab.equals("2")) {
                v_url = "/learn/user/game/course/gu_ApplyIntro2.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û�ȳ� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCurriculum(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/2012/portal/propose/zu_Curriculum_L.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = null;
            ArrayList list_1 = null;
            ArrayList list_2 = null;
            ArrayList list_3 = null;
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) {
                v_url = "/learn/user/2013/portal/propose/zu_Subject_L.jsp";

                list_1 = bean.selectSubjhomegubun_1(box, "GS");
                request.setAttribute("Subjhomegubun_1", list_1);

                list_2 = bean.selectSubjhomegubun_2(box, "GS");
                request.setAttribute("Subjhomegubun_2", list_2);

                list_3 = bean.selectSubjhomegubun_3(box, "GS");
                request.setAttribute("Subjhomegubun_3", list_3);

                list1 = bean.selectSubjectListRe(box);
                request.setAttribute("SubjectList", list1);

                // ���� ���ڿ� ���� ������ �ι��� ���� �� ����
                if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                    int literatureSubjectCount = bean.getLiteratureSubjectCount();
                    if (literatureSubjectCount > 0) {
                        // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                        box.setSession("isLiteratureSubjYn", "Y");
                    }
                }

            } else {
                v_url = "/learn/user/portal/propose/zu_Asp_Subject_L.jsp";
                list1 = bean.selectSubjectList(box);
                request.setAttribute("SubjectList", list1);
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/propose/zu_Asp_Subject_L.jsp";
            }

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ����Ʈ - ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListJikmu(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = null;
            ArrayList list_1 = null;
            ArrayList list_2 = null;
            ArrayList list_3 = null;
            String v_url = "";

            v_url = "/learn/user/2013/portal/propose/zu_SubjectJikmu_L.jsp";

            list_1 = bean.selectSubjhomegubun_1(box, "GM");
            request.setAttribute("Subjhomegubun_1", list_1);

            list_2 = bean.selectSubjhomegubun_2(box, "GM");
            request.setAttribute("Subjhomegubun_2", list_2);

            list_3 = bean.selectSubjhomegubun_3(box, "GM");
            request.setAttribute("Subjhomegubun_3", list_3);

            list1 = bean.selectSubjectListReJikmu(box);
            request.setAttribute("SubjectList", list1);

            // ���� ���ڿ� ���� ������ �ι��� ���� �� ����
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ����Ʈ - ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListJikup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = null;
            ArrayList list_1 = null;
            ArrayList list_2 = null;
            ArrayList list_3 = null;
            String v_url = "";

            v_url = "/learn/user/2013/portal/propose/zu_SubjectJikup_L.jsp";

            list_1 = bean.selectSubjhomegubun_1(box, "GJ");
            request.setAttribute("Subjhomegubun_1", list_1);

            list_2 = bean.selectSubjhomegubun_2(box, "GJ");
            request.setAttribute("Subjhomegubun_2", list_2);

            list_3 = bean.selectSubjhomegubun_3(box, "GJ");
            request.setAttribute("Subjhomegubun_3", list_3);

            list1 = bean.selectSubjectListReJikup(box);
            request.setAttribute("SubjectList", list1);

            // ���� ���ڿ� ���� ������ �ι��� ���� �� ����
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ��û/Ȯ�� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/course/zu_Subject_ALL_L.jsp";

            // ��������Ʈ
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = bean.selectSubjectListAll(box);
            request.setAttribute("SubjectList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
        }
    }

    /**
     * �¶��� ��ü �˻�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("rawtypes")
	public void performTotalSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) {
                v_url = "/learn/user/2012/portal/online/zu_TotalSubject_L.jsp";
                // v_url = "/learn/user/portal/propose/zu_Subject_L.jsp";
            } else {
                v_url = "/learn/user/portal/online/zu_TotalSubject_L.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/online/zu_TotalSubject_L.jsp";
            }

            // ��������Ʈ
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList totalSubjectList = bean.selectTotalSubjectList(box);
            request.setAttribute("totalSubjectList", totalSubjectList);

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
        }
    }

    /**
     * �α� ���� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListBest(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/course/zu_SubjectBest_L.jsp";

            // ��������Ʈ
            ProposeCourseBean bean = new ProposeCourseBean();
            // ���� �α� ����
            box.put("p_gubun", "W");
            ArrayList list1 = bean.selectSubjectListBest(box);
            request.setAttribute("SubjectListW", list1);
            // ���� �α� ����
            box.put("p_gubun", "L");
            ArrayList list2 = bean.selectSubjectListBest(box);
            request.setAttribute("SubjectListL", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListBest()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� �̸�����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) {
                // v_url = "/learn/user/2012/portal/propose/zu_Subject_R.jsp";
                v_url = "/learn/user/2013/portal/propose/zu_Subject_R.jsp";
            } else {
                v_url = "/learn/user/portal/propose/zu_Subject_R.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/propose/zu_Subject_R.jsp";
            }

            // ���������� ����Ʈ
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);

            // �������� ����Ʈ
            request.setAttribute("subjseqList", bean.selectSubjSeqList(box));

            // �������� ����Ʈ
            ArrayList list2 = bean.selectSubjChasiList(box);
            request.setAttribute("selectSubjChasiList", list2);

            // �̺�Ʈ�� �����üũ
            // ArrayList eventLst = bean.selectChkUser(box);
            // request.setAttribute("selectChkUser", eventLst);
            // request.setAttribute("tutorList", bean.selectTutorList(box));

            ArrayList list1 = bean.selectLessonList(box);
            request.setAttribute("lessonList", list1);
            // v_url= "/learn/user/game/course/gu_SubjectPreviewON.jsp";

            // ��������(��������) ���
            ArrayList nextSubjList = bean.selectNextProposeSubjList(box);
            request.setAttribute("nextSubjList", nextSubjList);

            // �ı� ��� ��ȸ
            ArrayList reviewList = bean.selectReviewList(box);
            request.setAttribute("reviewList", reviewList);

            // ���� ���ڿ� ���� ������ �ι��� ���� �� ����
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /*
     * 2009.12.08 ���� ���� ��� by swchoi public void
     * performSubjectPreviewPage(HttpServletRequest request, HttpServletResponse
     * response, RequestBox box, PrintWriter out) throws Exception { try {
     * request.setAttribute("requestbox", box); ProposeCourseBean bean = new
     * ProposeCourseBean();
     * 
     * // �������� ����Ʈ //ArrayList list1 = bean.selectListPre(box);
     * //request.setAttribute("subjectPre", list1); // �ļ����� ����Ʈ //ArrayList
     * list2 = bean.selectListNext(box); //request.setAttribute("subjectNext",
     * list2);
     * 
     * // String v_isonoff = box.getString("p_isonoff"); // String v_iscourseYn
     * = box.getString("p_iscourseYn"); String v_url = "";
     * 
     * // ���������� ����Ʈ DataBox dbox = bean.selectSubjectPreview(box);
     * request.setAttribute("subjectPreview", dbox); // String place =
     * dbox.getString("d_place");
     * 
     * // �������� ����Ʈ ArrayList list = bean.selectSubjSeqList(box);
     * request.setAttribute("subjseqList", list);
     * 
     * // �������� ����Ʈ ArrayList list2 = bean.selectSubjChasiList(box);
     * request.setAttribute("selectSubjChasiList", list2);
     * 
     * // System.out.print("dbox=============>"+dbox);
     * 
     * // �̺�Ʈ�� �����üũ ArrayList eventLst = bean.selectChkUser(box);
     * request.setAttribute("selectChkUser", eventLst);
     * 
     * //if(v_isonoff.equals("N")){ //�ڽ� ������ ��� ArrayList list1 =
     * bean.selectLessonList(box); request.setAttribute("lessonList", list1);
     * v_url= "/learn/user/game/course/gu_SubjectPreviewON.jsp"; // }else
     * if(v_isonoff.equals("Y")){ //�ڽ������� ���. // ArrayList list1 =
     * bean.selectLectureList(box); // request.setAttribute("lectureList",
     * list1); // // �ü����� // InstitutionAdminBean bean1 = new
     * InstitutionAdminBean(); // DataBox dbox2 = bean1.getInstitution(place);
     * // request.setAttribute("institution", dbox2); // // v_url=
     * "/learn/user/game/course/gu_SubjectPreviewOFF.jsp"; // }
     * //System.out.print("v_url=============>"+v_url); ServletContext sc =
     * getServletContext(); RequestDispatcher rd =
     * sc.getRequestDispatcher(v_url);
     * 
     * rd.forward(request, response); }catch (Exception ex) {
     * ErrorManager.getErrorStackTrace(ex, out); throw new
     * Exception("SubjectPreviewPage()\r\n" + ex.getMessage()); } }
     */
    /**
     * ���� �̸����� �˾�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectPreviewPopup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            // ���������� ����Ʈ
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);
            String place = dbox.getString("d_place");

            if (v_isonoff.equals("ON")) { // ���̹� ������ ���
                v_url = "/learn/user/game/course/gu_SubjectPreviewON_P.jsp";
                if(box.getSession("tem_type").equals("B")){
                	v_url = "/learn/user/typeB/course/gu_SubjectPreviewON_P.jsp";
                }
            } else if (v_isonoff.equals("OFF")) { // ���� ������ ���
                // �ü�����
                InstitutionAdminBean bean1 = new InstitutionAdminBean();
                DataBox dbox2 = bean1.getInstitution(place);
                request.setAttribute("institution", dbox2);

                v_url = "/learn/user/game/course/gu_SubjectPreviewOFF_P.jsp";
            }

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� �̸�����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjSeqPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            DataBox dbox = bean.selectSubjSeqPreview(box);
            request.setAttribute("subjseqPreview", dbox);

            ArrayList list = bean.selectLectureList(box);
            request.setAttribute("lectureList", list);

            if (v_isonoff.equals("ON")) { // ���̹� ������ ��� ��������Ʈ
                v_url = "/learn/user/course/zu_SubjSeqPreviewON.jsp";
            } else if (v_isonoff.equals("OFF")) { // ���� ������ ��� ���¸���Ʈ
                v_url = "/learn/user/course/zu_SubjSeqPreviewOFF.jsp";
            }
            System.out.println(v_url);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * TUTOR INFORMATION LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTutorInformationList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_selTutor = box.getString("p_selTutor"); // ���缱�ð�
            // ProposeCourseBean bean = new ProposeCourseBean();
            // ArrayList list1 = bean.selectTutorInformationList(box);
            // request.setAttribute("TutorInformationList", list1);

            // ������ȸ
            if (!v_selTutor.equals("")) {
                this.performTutorInformationSelect(request, response, box, out);
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_TutorInformation_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorInformationList()\r\n" + ex.getMessage());
        }
    }

    /**
     * TUTOR INFORMATION SELECT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTutorInformationSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // ProposeCourseBean bean = new ProposeCourseBean();
            // ProposeCourseData data = bean.selectTutorInformation(box);

            // request.setAttribute("TutorInformationSelect", data);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorInformationSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * �¶��� ����ü�赵
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduSystem(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/2013/portal/propose/zu_OnEduSystem_R2.jsp";

            ProposeCourseBean bean = new ProposeCourseBean();

            // ���� ���ڿ� ���� ������ �ι��� ���� �� ����
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� �󼼺���. �����ڵ常 �ְ� �⵵, ���������� ���� ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewSubjDetailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_url = "";

            v_url = "/learn/user/2013/portal/propose/zu_Subject_R.jsp";

            //DataBox infobox = bean.selectSubjInfo(box);
            //box.put("p_subjseq", infobox.get("d_subjseq"));
            //box.put("p_year", infobox.get("d_year"));
            //box.put("p_subjnm", infobox.get("d_subjnm"));
            box.put("p_subjseq", box.get("p_subjseq"));
            box.put("p_year", box.get("p_year"));
            box.put("p_subjnm", box.get("p_subjnm"));

            // ���������� ����Ʈ
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);

            // �������� ����Ʈ
            request.setAttribute("subjseqList", bean.selectSubjSeqList(box));

            // �������� ����Ʈ
            // ArrayList list2 = bean.selectSubjChasiList(box);
            // request.setAttribute("selectSubjChasiList", list2);

            ArrayList list1 = bean.selectLessonList(box);
            request.setAttribute("lessonList", list1);

            // ��������(��������) ���
            ArrayList nextSubjList = bean.selectNextProposeSubjList(box);
            request.setAttribute("nextSubjList", nextSubjList);

            // �ı� ��� ��ȸ
            ArrayList reviewList = bean.selectReviewList(box);
            request.setAttribute("reviewList", reviewList);

            // ���� ���ڿ� ���� ������ �ι��� ���� �� ����
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewSubjDetailPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �ı� ����� ��ȸ�Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSelectReviewListForAjax(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            System.out.println("performSelectReviewListForAjax");
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_url = "/learn/user/2013/portal/propose/zu_Subject_AjaxResult.jsp";

            // �ı� ��� ��ȸ
            ArrayList reviewList = bean.selectReviewList(box);
            request.setAttribute("reviewList", reviewList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectReviewListForAjax()\r\n" + ex.getMessage());
        }
    }

}