//*********************************************************
//  1. ��      ��: PROJECT SERVLET
//  2. ���α׷���: ProjectServlet.java
//  3. ��      ��: ����Ʈ servlet
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 9. 01
//  7. ��      ��:
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
            } else if (v_process.equals("ProjectHandlingPage")) { //����Ʈ ����
                this.performProjectHandlingPage(request, response, box, out);
            } else if (v_process.equals("ProjectHandling")) { //in case of project handling
                this.performProjectHandling(request, response, box, out);
            } else if (v_process.equals("ProjectJudgePage")) { //in case of project judge page
                this.performProjectJudgePage(request, response, box, out);
            } else if (v_process.equals("ProjectJudge")) { //in case of project judge
                this.performProjectJudge(request, response, box, out);
            } else if (v_process.equals("choicePage")) { //�н��� ����
                this.performChoicePage(request, response, box, out);
            } else if (v_process.equals("ProjectHalfSave")) { //����Ʈ �߰�����
                this.performProjectHalfSave(request, response, box, out);
            } else if (v_process.equals("ProjectListOld")) { //in case of ���� ����Ʈ ����Ʈ(����)
                this.performProjectListOld(request, response, box, out);
            } else if (v_process.equals("ProjectSelect")) { //in case of ���� ����Ʈ �󼼺���
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
     * PROJECT HANDLING PAGE ����Ʈ ����
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
            if (v_reptype.equals("C")) { //COP�� ��� ���������� ����Ʈ
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
     * REPORT AND ACTIVITY CHOICE PAGE �н��� ����
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

            // ������ �޴� ���� ���� �߰�
            box.put("p_menu", "34");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            ProjectBean bean = new ProjectBean();

            /**
             * ����Ʈ ���� ȭ���� �ѹ��̶� ���Դ� ��� �� ���Ŀ� ������ �߰��Ǹ� ���̻� ������ �ȵǴ� �����̴�. TODO :
             * ������ �߰��Ǿ��� ��� ������ �ٽ� �ϴ� ����� �ʿ�.
             */
            //int projcnt = bean.IsProjectAssign(box);  // ���� �������� --> 2009.11.09 ���� �������θ� üũ���� �ʰ�  �н��� ������ üũ
            //if(projcnt<1) {
            bean.updateProjectAssign(box); // �н��� ����
            //}

            // int result =
            bean.selectChoicePage(box);

            //if(result == 1) {       //����Ʈ�� �ִ� ���
            this.performProjectList(request, response, box, out);

            //}else if(result == 2) { //��Ƽ��Ƽ�� �ִ� ���
            //    String v_url = "/servlet/controller.study.ActivityServlet";
            //    box.put("p_process","ActivityList");
            //    AlertManager alert = new AlertManager();
            //    alert.alertOkMessage(out, "", v_url , box);
            //
            //}else if(result == 3) { //����Ʈ�� ��Ƽ��Ƽ�� �ִ� ���
            //    ServletContext sc = getServletContext();
            //    RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_ChoicePage.jsp");
            //    rd.forward(request, response);
            //}else{
            //    String v_msg = "������ �����ϴ�.";
            //    AlertManager alert = new AlertManager();
            //    alert.selfClose(out, v_msg);
            //}

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ChoicePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �߰�����
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
     * ���� ����Ʈ ����Ʈ(����)
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
     * PROJECT HANDLING PAGE ����Ʈ ����
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
