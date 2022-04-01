//*********************************************************
//  1. 제      목: PROJECT SERVLET
//  2. 프로그램명: ProjectServlet.java
//  3. 개      요: 리포트 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 01
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
import com.credu.study.ProjectBean;
import com.credu.study.ProjectData;
import com.credu.system.StudyCountBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.study.ProjectServlet")
public class ProjectServlet extends javax.servlet.http.HttpServlet implements Serializable {
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

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (v_process.equals("ProjectList")) { //in case of project list
                this.performProjectList(request, response, box, out);
            } else if (v_process.equals("ProjectHandlingPage")) { //레포트 제출
                this.performProjectHandlingPage(request, response, box, out);
            } else if (v_process.equals("ProjectHandling")) { //in case of project handling
                this.performProjectHandling(request, response, box, out);
            } else if (v_process.equals("ProjectJudgePage")) { //in case of project judge page
                this.performProjectJudgePage(request, response, box, out);
            } else if (v_process.equals("ProjectJudge")) { //in case of project judge
                this.performProjectJudge(request, response, box, out);
            } else if (v_process.equals("choicePage")) { //학습자 배정
                this.performChoicePage(request, response, box, out);
            } else if (v_process.equals("ProjectHalfSave")) { //레포트 중간저장
                this.performProjectHalfSave(request, response, box, out);
            } else if (v_process.equals("ProjectListOld")) { //in case of 과거 레포트 리스트(개인)
                this.performProjectListOld(request, response, box, out);
            } else if (v_process.equals("ProjectSelect")) { //in case of 과거 레포트 상세보기
                this.performProjectSelect(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * PROJECT LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();
            ArrayList<ProjectData> list = bean.selectProjectList(box);

            request.setAttribute("projectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Project_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT HANDLING PAGE 레포트 제출
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectHandlingPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();
            ProjectData data1 = bean.selectProjectOrd(box);
            request.setAttribute("selectProjectOrd", data1);
            ProjectData data2 = bean.selectProjectRep(box);
            request.setAttribute("selectProjectRep", data2);

            String v_reptype = box.getString("p_reptype");

            //Log.info.println("v_reptype"+v_reptype);
            if (v_reptype.equals("C")) { //COP의 경우 동료평가점수 리스트
                ArrayList<ProjectData> list = bean.selectCoprepList(box);
                request.setAttribute("selectCoprepList", list);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Project_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProjectHandlingPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT HANDLING
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performProjectHandling(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();

            int isOk = bean.ProjectHandling(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectServlet";
            box.put("p_process", "ProjectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                //v_msg = "update.ok";
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProjectHandling()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT JUDGE PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectJudgePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();
            ArrayList<ProjectData> list1 = bean.selectProjectCopList(box);

            request.setAttribute("projectJudgePage", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_ProjectCOP_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProjectJudgePage()\r\n" + ex.getMessage());
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
    public void performProjectJudge(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();

            int isOk = bean.updateProjectJudge(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectServlet";
            box.put("p_process", "ProjectList");

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
            throw new Exception("ProjectJudge()\r\n" + ex.getMessage());
        }
    }

    /**
     * REPORT AND ACTIVITY CHOICE PAGE 학습자 배정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performChoicePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // 과정별 메뉴 접속 정보 추가
            box.put("p_menu", "34");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            ProjectBean bean = new ProjectBean();

            /**
             * 레포트 제출 화면을 한번이라도 들어왔던 경우 그 이후에 문제가 추가되면 더이상 배정이 안되는 이유이다. TODO :
             * 문제가 추가되었을 경우 배정을 다시 하는 방법이 필요.
             */
            //int projcnt = bean.IsProjectAssign(box);  // 기존 배정여부 --> 2009.11.09 기존 배정여부를 체크하지 않고  학습자 배정시 체크
            //if(projcnt<1) {
            bean.updateProjectAssign(box); // 학습자 배정
            //}

            // int result =
            bean.selectChoicePage(box);

            //if(result == 1) {       //리포트만 있는 경우
            this.performProjectList(request, response, box, out);

            //}else if(result == 2) { //액티비티만 있는 경우
            //    String v_url = "/servlet/controller.study.ActivityServlet";
            //    box.put("p_process","ActivityList");
            //    AlertManager alert = new AlertManager();
            //    alert.alertOkMessage(out, "", v_url , box);
            //
            //}else if(result == 3) { //리포트와 액티비티가 있는 경우
            //    ServletContext sc = getServletContext();
            //    RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_ChoicePage.jsp");
            //    rd.forward(request, response);
            //}else{
            //    String v_msg = "정보가 없습니다.";
            //    AlertManager alert = new AlertManager();
            //    alert.selfClose(out, v_msg);
            //}

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ChoicePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 중간저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performProjectHalfSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();

            int isOk = bean.ProjectHalfSave(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ProjectServlet";
            box.put("p_process", "ProjectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                //v_msg = "update.ok";
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProjectHandling()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과거 레포트 리스트(개인)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectListOld(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();
            ArrayList<DataBox> list = bean.selectProjectListOld(box);

            request.setAttribute("projectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_ProjectOld_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProjectListOld()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT HANDLING PAGE 레포트 제출
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectBean bean = new ProjectBean();
            DataBox dbox = bean.selectProjectOld(box);
            request.setAttribute("selectProject", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_ProjectOld_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProjectSelect()\r\n" + ex.getMessage());
        }
    }
}
