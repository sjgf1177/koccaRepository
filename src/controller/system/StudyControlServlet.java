//**********************************************************
//1. 제      목: 학습제약조건 SERVLET
//2. 프로그램명: StudyControlServlet.java
//3. 개      요: 학습제약조건 SERVLET
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: S.W.Kang 2005. 03. 02
//7. 수      정:
//
//**********************************************************
package controller.system;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

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
import com.credu.system.AdminUtil;
import com.credu.system.StudyControlBean;

@WebServlet("/servlet/controller.system.StudyControlServlet")
public class StudyControlServlet extends HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5606334113467057315L;

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
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "ListPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            ///////////////////////////////////////////////////////////////////
            // 권한 check 루틴 VER 0.2 - 2003.08.10
            if (!AdminUtil.getInstance().checkRWRight("StudyControlServlet", v_process, out, box)) {
                return;
            }
            ///////////////////////////////////////////////////////////////////
            if (v_process.equals("ListPage") || v_process.equals("")) { // 학습제약조건 리스트
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("Insert") || v_process.equals("")) { // 학습제약조건 등록
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("Update") || v_process.equals("")) { // 학습제약조건 수정
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("Delete") || v_process.equals("")) { // 학습제약조건 삭제
                this.performDelete(request, response, box, out);
            }
            // else if (v_process.equals("ListPage2") || v_process.equals("")) {        // 학습제약조건 삭제
            //     this.performListPage2(request, response, box, out);
            // }
            else if (v_process.equals("ExpListPage") || v_process.equals("")) { // 학습제약조건 삭제
                this.performExpListPage(request, response, box, out);
            } else if (v_process.equals("ExpInsert") || v_process.equals("")) { // 학습제약조건 삭제
                this.performExpInsert(request, response, box, out);
            } else if (v_process.equals("ExpDelete") || v_process.equals("")) { // 학습제약조건 삭제
                this.performExpDelete(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 학습제약 조건 설정 현황 리스트
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
            String v_return_url = "/learn/admin/system/za_StudyControl_L.jsp";

            StudyControlBean bean = new StudyControlBean();

            ArrayList<DataBox> list = bean.SelectStudyControl(box, "N");
            request.setAttribute("StudyControl1", list);

            ArrayList<DataBox> list2 = bean.SelectStudyControl(box, "Y");
            request.setAttribute("StudyControl2", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습제약조건 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.system.StudyControlServlet";

            StudyControlBean bean = new StudyControlBean();

            int isOk = bean.insertStudyControl(box);

            String v_msg = "";
            box.put("p_process", "ListPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "save.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "save.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습제약조건 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response form the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.system.StudyControlServlet";

            StudyControlBean bean = new StudyControlBean();

            int isOk = bean.updateStudyControl(box);
            String v_msg = "";
            box.put("p_process", "ListPage");

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
     * 학습제약조건 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response form the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.system.StudyControlServlet";

            StudyControlBean bean = new StudyControlBean();

            int isOk = bean.deleteStudyControl(box);
            String v_msg = "";
            box.put("p_process", "ListPage");

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
     * 학습제약 조건 예외자 현황 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performExpListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/system/za_StudyControlExp_L.jsp";

            StudyControlBean bean = new StudyControlBean();

            ArrayList<DataBox> list = bean.SelectStudyControlExp(box);
            request.setAttribute("StudyControlExp", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExpListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습제약조건 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performExpInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.system.StudyControlServlet";

            StudyControlBean bean = new StudyControlBean();

            int isOk = bean.insertStudyControlExp(box);
            String v_msg = "";
            box.put("p_process", "ExpListPage");

            AlertManager alert = new AlertManager();

            if (isOk == 99) {
                v_msg = "중복된 아이디입니다.";
                alert.alertFailMessage(out, v_msg);
            } else if (isOk > 0) {
                v_msg = "save.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "save.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExpInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습제약조건 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response form the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     ***/
    @SuppressWarnings("unchecked")
    public void performExpDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.system.StudyControlServlet";

            StudyControlBean bean = new StudyControlBean();

            int isOk = bean.deleteStudyControlExp(box);
            String v_msg = "";
            box.put("p_process", "ExpListPage");

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

}