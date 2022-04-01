//*********************************************************
//  1. 제      목: PROJECT ADMIN SERVLET
//  2. 프로그램명: ProjectAdminServlet.java
//  3. 개      요: 리포트 관리자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 25
//  7. 수      정:
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
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.ProjectAdminBean;
import com.credu.study.ProjectBean;
import com.credu.study.ProjectData;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.study.ProjectAdminServlet")
public class ProjectAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
        //        MultipartRequest multi = null;
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

            if (!AdminUtil.getInstance().checkRWRight("ProjectAdminServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("ProjectQuestionsAdmin")) { //in case of project questions admin
                this.performProjectQuestionsAdmin(request, response, box, out);
            } else if (v_process.equals("ProjectQuestionsList")) { //in case of project questions list
                this.performProjectQuestionsList(request, response, box, out);
            } else if (v_process.equals("ProjectQuestionsInsertPage")) {//in case of project questions insert page
                this.performProjectQuestionsInsertPage(request, response, box, out);
            } else if (v_process.equals("ProjectQuestionsCopy")) {//이전차수 리포트 복사
                this.performProjectQuestionsCopy(request, response, box, out);
            } else if (v_process.equals("ProjectQuestionsInsert")) { //in case of project questions insert
                this.performProjectQuestionsInsert(request, response, box, out);
            } else if (v_process.equals("ProjectQuestionsUpdatePage")) {//in case of project questions update page
                this.performProjectQuestionsUpdatePage(request, response, box, out);
            } else if (v_process.equals("ProjectQuestionsUpdate")) { //in case of project questions update
                this.performProjectQuestionsUpdate(request, response, box, out);
            } else if (v_process.equals("ProjectGroupHandlingPage")) { //in case of project group handling page
                this.performProjectGroupHandlingPage(request, response, box, out);
            } else if (v_process.equals("ProjectGroupHandling")) { //in case of project group handling
                this.performProjectGroupHandling(request, response, box, out);
            } else if (v_process.equals("ProjectSubmitAdmin")) { //in case of project submit admin
                this.performProjectSubmitAdmin(request, response, box, out);
            } else if (v_process.equals("ProjectSubmitList")) { //in case of project submit list
                this.performProjectSubmitList(request, response, box, out);
            } else if (v_process.equals("ProjectDetailListAll")) { //전체 과제물 제출 리스트
                this.performProjectDetailListAll(request, response, box, out);
            } else if (v_process.equals("ProjectDetailList")) { //in case of project detail list
                this.performProjectDetailList(request, response, box, out);
            } else if (v_process.equals("ProjectSubmitOpenPage")) { //in case of project submit open page
                this.performProjectSubmitOpenPage(request, response, box, out);
            } else if (v_process.equals("ProjectJudgeAtOpenWin")) { //in case of project judge at open page
                this.performProjectJudgeAtOpenWin(request, response, box, out);
            } else if (v_process.equals("DelUpfile")) { //in case of Upfile Delete
                this.performDelUpfile(request, response, box, out);
            } else if (v_process.equals("ProjectReportDelete")) { //in case of Upfile Delete
                this.performProjectReportDelete(request, response, box, out);
            } else if (v_process.equals("ProjectCopyView")) { // 모사답안 view
                this.performProjectCopyView(request, response, box, out);
            } else if (v_process.equals("ProjectJudgeAtOpenWinCopy")) { // 모사답안 view
                this.performProjectJudgeAtOpenWinCopy(request, response, box, out);
            } else if (v_process.equals("ProjectEndSubmitList")) { // 교육완료 후 재제출 리스트
                this.performProjectEndSubmitList(request, response, box, out);
            } else if (v_process.equals("ProjectEndSubmitOpen")) { // 교육완료 후 재제출 화면
                this.performProjectEndSubmitOpen(request, response, box, out);
            } else if (v_process.equals("ProjectEndSubmit")) { // 교육완료 후 재제출 처리
                this.performProjectEndSubmit(request, response, box, out);
            } else if (v_process.equals("ProjectCopyRegist")) { // 모사답압비교 체크 등록처리
                this.performProjectCopyRegist(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * PROJECT QUESTIONS ADMIN
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectQuestionsAdmin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<DataBox> list1 = bean.selectProjectQuestionsAList(box);

            request.setAttribute("projectQuestionsAList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectQuestionsAdmin_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsAdmin()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT QUESTIONS LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectQuestionsList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<ProjectData> List = bean.selectProjectQuestionsList(box);

            request.setAttribute("projectQuestionsList", List);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectQuestions_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT QUESTIONS INSERT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectQuestionsInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //리포트 차수에 대한 숫자 구하기
            ProjectAdminBean bean = new ProjectAdminBean();
            box.put("v_maxprojseq", new Integer(bean.selectMaxProjectSeq(box)));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectQuestions_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT QUESTIONS COPY
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectQuestionsCopy(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.copyProjectQuestions(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";
            box.put("p_process", "ProjectQuestionsList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else if (isOk == -1) {
                v_msg = "이미 복사가 되었습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT QUESTIONS INSERT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectQuestionsInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.insertProjectQuestions(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";
            box.put("p_process", "ProjectQuestionsList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT QUESTIONS UPDATE PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectQuestionsUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ProjectData data = bean.updateProjectQuestionsPage(box);

            request.setAttribute("projectQuestionsUpdatePage", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectQuestions_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT QUESTIONS UPDATE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectQuestionsUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.updateProjectQuestions(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";
            box.put("p_process", "ProjectQuestionsList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else if (isOk == -1) {
                v_msg = "리포트가 배정된 학습자가 있어 수정할 수 없습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT GROUP HANDLING PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectGroupHandlingPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<ProjectData> list1 = bean.handlingProjectGroupPage(box);

            request.setAttribute("projectGroupHandlingPage", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectGroup_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectGroupHandlingPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT GROUP HANDLING
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectGroupHandling(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.handlingProjectGroup(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";
            box.put("p_process", "ProjectGroupHandlingPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectGroupHandling()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT SUBMIT ADMIN
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectSubmitAdmin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<DataBox> list1 = bean.selectProjectSubmitAList(box);

            request.setAttribute("projectSubmitAList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectSubmitAdmin_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectSubmitAdmin()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT SUBMIT LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectSubmitList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<ProjectData> list = bean.selectProjectSubmitList(box);

            request.setAttribute("projectSubmitList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectSubmit_L1.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectSubmitList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT DETAIL LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<ProjectData> List = bean.selectProjectDetailList(box);

            request.setAttribute("projectDetailList", List);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectDetail_L1.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectDetailList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT DETAIL LIST ALL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectDetailListAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<DataBox> List = bean.selectProjectDetailListAll(box);
            request.setAttribute("projectDetailListAll", List);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectDetailAll_L1.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectDetailList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT SUBMIT OPEN PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectSubmitOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ProjectData data = bean.selectProjectSubmitOpen(box);
            ArrayList<ProjectData> List = bean.selectProjectSubmitListOpen(box);

            request.setAttribute("projectSubmitOpenPage", data);
            request.setAttribute("projectSubmitListOpen", List);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectSubmit.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectSubmitOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT COPY VIEW
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectCopyView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<ProjectData> list = bean.selectProjectCopyView(box);

            request.setAttribute("projectCopyView", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectCopyViewOpen_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectCopyView()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT JUDGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectJudgeAtOpenWin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.updateProjectJudge(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";

            String v_returnprocess = box.getString("p_returnprocess");

            if (v_returnprocess.equals("")) {
                box.put("p_process", "ProjectDetailList");
            } else if (v_returnprocess.equals("ProjectDetailList1")) { //강사화면에서 채점한 후 다시 강사 채점화면으로...
                v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
                box.put("p_process", "ProjectDetailList");
            } else {
                box.put("p_process", v_returnprocess); //리포트전체리스트에서 채점할때 해당화면으로 redir.
            }

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectJudge()\r\n" + ex.getMessage());
        }
    }

    /**
     * 모사답안 반려 처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectJudgeAtOpenWinCopy(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.updateProjectJudgeCopy(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";

            box.put("p_process", "ProjectDetailListAll");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProjectJudgeAtOpenWinCopy()\r\n" + ex.getMessage());
        }
    }

    /**
     * UPFILE DELETE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performDelUpfile(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.delUpfile(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";
            box.put("p_process", "ProjectQuestionsList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectJudge()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT REPORT DELETE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectReportDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.deleteProjectReport(box);
            //int isOk = bean.updateProjectJudge(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";
            box.put("p_process", "ProjectQuestionsList");

            AlertManager alert = new AlertManager();

            if (isOk == 1) {
                v_msg = "리포트가 삭제되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == -1) {
                v_msg = "리포트가 배정된 학습자가 있어 삭제할 수 없습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectJudge()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육완료 후 재제출 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProjectEndSubmitList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<DataBox> list1 = bean.selectProjectEndAssignList(box);
            request.setAttribute("ProjectEndAssignList", list1); // 배정받은 수강생

            ArrayList<DataBox> list2 = bean.selectProjectEndAssignNotList(box);
            request.setAttribute("ProjectEndAssignNotList", list2); // 다른 리포트에도 배정 받지 않은 수강생

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectEndSubmit_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProjectEndSubmitList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육완료 후 재제출 화면 - 배정정보등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectEndSubmitOpen(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // 배정정보가 없는 수강자인 경우..
            if (box.getString("p_assign").equals("1")) {
                ProjectBean bean = new ProjectBean();
                bean.updateProjectAssign(box);
            }

            //AlertManager alert = new AlertManager();
            //if(isOk < 0) {
            //    alert.alertFailMessage(out, "배정정보등록 처리가 되지 않았습니다.");
            //}

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ProjectEndSubmit_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProjectEndSubmitOpen()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육완료 후 재제출 처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectEndSubmit(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.insertProjectEndSubmit(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";

            box.put("p_process", "ProjectEndSubmitList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                //alert.alertOkMessage(out, v_msg, v_url , box, true);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProjectEndSubmit()\r\n" + ex.getMessage());
        }
    }

    /**
     * 모사답안비교 체크 등록 처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performProjectCopyRegist(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();

            int isOk = bean.updateProjectCopyRegist(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectAdminServlet";

            box.put("p_process", "ProjectDetailListAll");

            String v_returnprocess = box.getString("p_returnprocess"); // 강사 체점에서 넘어왔을 경우

            if (v_returnprocess.equals("ProjectDetailList1")) {
                v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
                box.put("p_process", "ProjectDetailList");
            }

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProjectEndSubmit()\r\n" + ex.getMessage());
        }
    }

}
