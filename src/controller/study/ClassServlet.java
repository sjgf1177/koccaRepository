//**********************************************************
//1. 제      목:
//2. 프로그램명: ClassServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성:
//**********************************************************
package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.ClassBean;
import com.credu.study.ClassListData;
import com.credu.study.ClassMemberData;
import com.credu.system.AdminUtil;

/**
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */

@WebServlet("/servlet/controller.study.ClassServlet")
public class ClassServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1626821211575883763L;

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
    @SuppressWarnings("unchecked")
    @Override
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

            if (!AdminUtil.getInstance().checkRWRight("ClassServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("listPage")) { // 단일클래스구성 LIST
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("oneStopMakeClass")) { // 단일클래스 저장
                this.performOneStopMakeClass(request, response, box, out);
            } else if (v_process.equals("subTutorPage")) { // 보조강사 LIST
                this.performSubTutorPage(request, response, box, out);
            } else if (v_process.equals("subTutorInsert")) { // 보조강사 저장
                this.performSubTutorInsert(request, response, box, out);
            } else if (v_process.equals("plurallistPage")) { // 복수클래스구성 LIST
                this.performPluralListPage(request, response, box, out);
            } else if (v_process.equals("subjClasslistPage")) { // 등록된 과정별 클래스 LIST
                this.performSubjClassPage(request, response, box, out);
            } else if (v_process.equals("deleteClass")) { // 클래스 삭제
                this.performDeleteClass(request, response, box, out);
            } else if (v_process.equals("createClass")) { // 클래스 추가 - 개인별
                this.performCreateClass(request, response, box, out);
            } else if (v_process.equals("classInsertPage")) { // 클래스지정 등록페이지 - 개인별
                this.performClassInsertPage(request, response, box, out);
            } else if (v_process.equals("classInsert")) { // 클래스지정 등록 - 개인별
                this.performClassInsert(request, response, box, out);
            } else if (v_process.equals("classUpdatePage")) { // 클래스지정 수정 페이지 - 개인별
                this.performClassUpdatePage(request, response, box, out);
            } else if (v_process.equals("classUpdate")) { // 클래스지정 수정 -개인별
                this.performClassUpdate(request, response, box, out);
            } else if (v_process.equals("classFileToDBPage")) { // 클래스 분반 FILE TO DB 페이지
                this.performClassFileToDBPage(request, response, box, out);
            } else if (v_process.equals("insertFileToDB")) { // 클래스 분반 FILE TO DB  등록
                this.performInsertFileToDB(request, response, box, out);
            } else if (v_process.equals("previewFileToDB")) { // 클래스 분반 FILE TO DB  미리보기
                this.performPreviewFileToDB(request, response, box, out);
            } else if (v_process.equals("classStudentListExcel")) { // 클래스 분반할 과정 및 학생 리스트
                this.performClassStudentListExcel(request, response, box, out);
            } else if (v_process.equals("ClassListExcel")) { // 엑셀보기
                this.performClassListExcel(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 단일클래스구성 LIST
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
            String v_return_url = "/learn/admin/study/za_SingleClass_L.jsp";

            ClassBean bean = new ClassBean();
            ArrayList<ClassListData> list = bean.SelectClassList(box);
            request.setAttribute("ClassList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 단일클래스 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performOneStopMakeClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.study.ClassServlet";

            ClassBean bean = new ClassBean();
            int isOk = bean.OneStopMakeClass(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk >= 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOneStopMakeClass()\r\n" + ex.getMessage());
        }
    }

    /**
     * 보조강사 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubTutorPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_SubTutor_L.jsp";

            ClassBean bean = new ClassBean();

            Hashtable<String, String> subjinfo = bean.getSubjInfo(box);
            request.setAttribute("SubjectInfo", subjinfo);

            ArrayList<DataBox> list = bean.SelectSubtutorList(box);
            request.setAttribute("SubTutorList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubTutorPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 보조강사 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubTutorInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.study.ClassServlet";
            String v_call_url = box.getString("p_call_url");

            ClassBean bean = new ClassBean();
            int isOk = bean.SubTutorInsert(box);

            String v_msg = "";

            if (v_call_url.equals("za_IndividualClass_U")) {
                box.put("p_process", "classUpdatePage");
            } else if (v_call_url.equals("za_IndividualClass_I")) {
                box.put("p_process", "classInsertPage");
            } else {
                box.put("p_process", "listPage");
                box.put("p_action", "go");
            }

            AlertManager alert = new AlertManager();
            if (isOk >= 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubTutorInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 복수클래스구성 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPluralListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_PluralClass_L.jsp";

            ClassBean bean = new ClassBean();
            ArrayList<ClassListData> list = bean.SelectClassList(box);
            request.setAttribute("ClassList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPluralListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록된 과정별 클래스 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjClassPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_SubjClass_L.jsp";

            ClassBean bean = new ClassBean();

            Hashtable<String, String> subjinfo = bean.getSubjInfo(box);
            request.setAttribute("SubjectInfo", subjinfo);

            ArrayList<ClassMemberData> list = bean.SelectMemberList(box);
            request.setAttribute("SelectMemberList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjClassPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDeleteClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.study.ClassServlet";

            ClassBean bean = new ClassBean();
            int isOk = bean.DeleteAllClass(box);

            String v_msg = "";
            box.put("p_process", "plurallistPage");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk >= 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteClass()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스 추가 - 개인별
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCreateClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.study.ClassServlet";

            ClassBean bean = new ClassBean();
            int isOk = bean.CreateClass(box);

            String v_msg = "";
            box.put("p_process", "classInsertPage");

            AlertManager alert = new AlertManager();
            if (isOk >= 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performClassInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스지정 추가페이지 - 개인별
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performClassInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_IndividualClass_I.jsp";

            ClassBean bean = new ClassBean();

            Hashtable<String, String> subjinfo = bean.getSubjInfo(box);
            request.setAttribute("SubjectInfo", subjinfo);

            String v_subpage = box.getStringDefault("p_subpage", "individual_page");
            ArrayList<?> list = null;

            if (v_subpage.equals("asgn_page")) {
                list = bean.SelectGroupStudentList(box);
            } else if (v_subpage.equals("jikun_page")) {
                list = bean.SelectGroupStudentList(box);
            } else if (v_subpage.equals("jikup_page")) {
                list = bean.SelectGroupStudentList(box);
            } else {
                list = bean.SelectClassInsertList(box);
            }

            request.setAttribute("ClassInsertList", list);

            Hashtable<String, String> tutorinfo = bean.getTutorInfo(box);
            box.put("p_mtutor", tutorinfo.get("p_mtutor"));
            box.put("p_stutor", tutorinfo.get("p_stutor"));
            box.put("p_tutorcnt", tutorinfo.get("p_tutorcnt"));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performClassInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스지정 추가 - 개인별
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performClassInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.study.ClassServlet";

            ClassBean bean = new ClassBean();

            String v_subpage = box.getStringDefault("p_subpage", "individual_page");

            int isOk = 0;

            if (v_subpage.equals("asgn_page")) {
                isOk = bean.UpdateGroupClass(box);
            } else if (v_subpage.equals("jikun_page")) {
                isOk = bean.UpdateGroupClass(box);
            } else if (v_subpage.equals("jikup_page")) {
                isOk = bean.UpdateGroupClass(box);
            } else {
                isOk = bean.UpdateStudentClass(box);
            }

            String v_msg = "";
            box.put("p_process", "classInsertPage");

            AlertManager alert = new AlertManager();
            if (isOk >= 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteClass()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스지정 수정페이지 - 개인별
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performClassUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_IndividualClass_U.jsp";

            ClassBean bean = new ClassBean();

            Hashtable<String, String> subjinfo = bean.getSubjInfo(box);
            request.setAttribute("SubjectInfo", subjinfo);

            ArrayList<ClassMemberData> list = bean.SelectClassInsertList(box);
            request.setAttribute("ClassInsertList", list);

            Hashtable<String, String> tutorinfo = bean.getTutorInfo(box);
            box.put("p_mtutor", tutorinfo.get("p_mtutor"));
            box.put("p_mtutorid", tutorinfo.get("p_mtutorid"));
            box.put("p_stutor", tutorinfo.get("p_stutor"));
            box.put("p_tutorcnt", tutorinfo.get("p_tutorcnt"));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performClassUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스지정 수정 - 개인별
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performClassUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.study.ClassServlet";

            ClassBean bean = new ClassBean();
            int isOk = bean.UpdateStudentClass(box);

            String v_msg = "";
            box.put("p_process", "classUpdatePage");

            AlertManager alert = new AlertManager();
            if (isOk >= 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteClass()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스 FileToDB
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performClassFileToDBPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_ClassFilToDB.jsp";

            ClassBean bean = new ClassBean();

            Hashtable<String, String> subjinfo = bean.getSubjInfo(box);
            request.setAttribute("SubjectInfo", subjinfo);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performClassUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스 FileToDB 처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_ClassFilToDB_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스 FileToDB 미리보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_ClassFilToDB_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스 분반할 과정 및 학생 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performClassStudentListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_ClassStudentList_E.jsp";

            ClassBean bean = new ClassBean();
            ArrayList<DataBox> list = bean.ClassStudentList(box);
            request.setAttribute("ClassStudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performClassListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/study/za_SubjClass_E.jsp";

            ClassBean bean = new ClassBean();

            //ArrayList list = bean.SelectMemberListExcel(box);
            ArrayList<ClassListData> list = bean.SelectClassList(box); // list 메소드를 이용한다.
            request.setAttribute("ClassListExcel", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjClassPage()\r\n" + ex.getMessage());
        }
    }
}
