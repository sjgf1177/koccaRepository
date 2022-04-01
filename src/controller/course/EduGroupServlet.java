// **********************************************************
// 1. 제 목: 교육그룹관리 SERVLET
// 2. 프로그램명: EduGroupServlet.java
// 3. 개 요:
// 4. 환 경: JDK 1.3
// 5. 버 젼: 0.1
// 6. 작 성: LeeSuMin 2003. 07. 14
// 7. 수 정:
//                 
// **********************************************************
package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.CourseBean;
import com.credu.course.EduGroupBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.course.EduGroupServlet")
public class EduGroupServlet extends HttpServlet implements Serializable {

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "listPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            if (!AdminUtil.getInstance().checkRWRight("EduGroupServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("listPage")) { // 교육그룹 리스트 조회 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insertPage")) { // 교육그룹 등록 화면
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { // 교육그룹 등록(저장)
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) { // 교육그룹 수정 화면
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { // 교육그룹 수정(저장)
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("assignSubjCourse")) { // 교육그룹에 과정/코스 연결
                this.performAssign(request, response, box, out);
            } else if (v_process.equals("assignSubj")) { // 교육그룹에 과정 연결
                this.performAssignSubj(request, response, box, out);
            } else if (v_process.equals("assignSubjCourseSave")) { // 교육그룹에 과정/코스 연결
                this.performAssignSave(request, response, box, out);
            } else if (v_process.equals("assignSubjSave")) { // 교육그룹에 과정 연결저장
                this.performAssignSubjSave(request, response, box, out);
            } else if (v_process.equals("recomSubj")) { // 교육그룹에 추천과정 연결
                this.performRecomSubj(request, response, box, out);
            } else if (v_process.equals("recomSubjCourseSave")) { // 교육그룹에 추천과정 연결
                this.performRecomSave(request, response, box, out);
            } else if (v_process.equals("delete")) { // 교육그룹 삭제
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("updateCodeOrder")) {

                this.performUpdateCodeOrder(request, response, box, out); // 정렬순서 갱신
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 교육그룹의 정렬순서 정보를 갱신한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performUpdateCodeOrder(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_EduGroup_L.jsp";

            EduGroupBean bean = new EduGroupBean();

            int resultCnt = bean.updateCodeOrder(box); // 정렬순서 갱신

            ArrayList list1 = bean.SelectEduGroupList(box); // 정렬순서 갱신 후 목록조회
            request.setAttribute("resultCnt", resultCnt);
            request.setAttribute("EduGroupList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹 리스트 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_EduGroup_L.jsp";

            EduGroupBean bean = new EduGroupBean();
            ArrayList list1 = bean.SelectEduGroupList(box);
            request.setAttribute("EduGroupList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드 등록 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_EduGroup_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.EduGroupServlet";

            EduGroupBean bean = new EduGroupBean();

            int isOk = bean.InsertEduGroup(box);

            String v_msg = "";
            box.put("p_process", "listPage");

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
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드 수정 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_EduGroup_U.jsp";

            EduGroupBean bean = new EduGroupBean();

            DataBox dbox = bean.SelectEduGroupData(box);
            request.setAttribute("EduGroupData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.EduGroupServlet";

            EduGroupBean bean = new EduGroupBean();
            int isOk = bean.UpdateEduGroup(box);

            String v_msg = "";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드에 과정/코스 지정화면
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performAssign(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_EduGroupSubj.jsp";

            EduGroupBean bean = new EduGroupBean();

            ArrayList list1 = bean.TargetCourseList(box);
            request.setAttribute("TargetCourseList", list1);

            ArrayList list2 = bean.TargetSubjectList(box);
            request.setAttribute("TargetSubjectList", list2);

            ArrayList list3 = bean.SelectedList(box);
            request.setAttribute("SelectedList", list3);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAssign()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드리스트 VIEW
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performAssignSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_EduGroupSubjCourse.jsp";

            EduGroupBean bean = new EduGroupBean();
            ArrayList list1 = bean.SelectSubjList(box);
            request.setAttribute("SubjectList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드에 과정/코스 지정- 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performAssignSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.EduGroupServlet";

            EduGroupBean bean = new EduGroupBean();
            int isOk = bean.SaveAssign(box);

            String v_msg = "";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드에 과정/코스 지정- 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performAssignSubjSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.EduGroupServlet";

            EduGroupBean bean = new EduGroupBean();
            int isOk = bean.SaveAssign2(box);

            String v_msg = "";
            box.put("p_process", "assignSubj");

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
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.EduGroupServlet";

            CourseBean bean = new CourseBean();
            int isOk = bean.DeleteCourse(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("p_course", "");
            box.put("p_coursenm", "");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드리스트 VIEW - 추천과정연결용
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRecomSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_EduGroupRecomSubj.jsp";

            EduGroupBean bean = new EduGroupBean();
            ArrayList list1 = bean.SelectSubjList2(box);
            request.setAttribute("SubjectList2", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹코드에 추천과정 지정- 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRecomSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.EduGroupServlet";

            EduGroupBean bean = new EduGroupBean();
            int isOk = bean.SaveRecom(box);

            String v_msg = "";
            box.put("p_process", "recomSubj");

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
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
}
