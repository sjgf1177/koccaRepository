//1. 제      목: 교수메세지 서블릿
//2. 프로그램명 : MemberSearchServlet.java
//3. 개      요: 교수메세지 서블릿
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 정상진 2004. 12. 20
//7. 수      정:
//**********************************************************

package controller.tutor;

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
import com.credu.tutor.TutorData;
import com.credu.tutor.TutorIntroduceBean;
import com.credu.tutor.TutorMessageAdminBean;

@WebServlet("/servlet/controller.tutor.TutorMessageAdminServlet")
public class TutorMessageAdminServlet extends HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4592534813352467132L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            // String path = request.getRequestURI();

            if (!v_process.equals("introduceList")) {
                if (!AdminUtil.getInstance().checkRWRight("TutorMessageAdminServlet", v_process, out, box)) {
                    return;
                }
            }

            if (v_process.equals("insertPage")) { //  등록페이지로 이동할때
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { //  등록할때
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) { //  수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { //  수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("delete")) { //  삭제할때
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("select")) { //  상세보기할때
                this.performSelect(request, response, box, out);
            } else if (v_process.equals("listPage")) { //  조회할때
                this.performSelectList(request, response, box, out);
            }
            //책임 교수 팝업 창 테스트 부분-----------------------------------------
            else if (v_process.equals("introduceList")) { //  조회할때
                this.performIntroduceList(request, response, box, out);
            }
            //------------------------------------------------------------------
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    //테스트 코드 임 ------------
    public void performIntroduceList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다            
            //            TutorAdminBean bean = new TutorAdminBean();

            TutorIntroduceBean introduceBean = new TutorIntroduceBean();

            ArrayList<DataBox> list1 = introduceBean.selectMessageList(box);
            DataBox dbox = introduceBean.selectTutor(box);
            ArrayList<TutorData> list = introduceBean.selectTutorSubjList(box);

            request.setAttribute("selectMessageList", list1);

            request.setAttribute("tutorSelect", dbox);
            request.setAttribute("tutorSubjList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/ku_TutorIntroduce_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }


    /**
     * 등록페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다.

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @eturn void
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            TutorMessageAdminBean message = new TutorMessageAdminBean();

            int isOk = message.insertMessage(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorMessageAdminServlet";
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
     * 상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            TutorMessageAdminBean message = new TutorMessageAdminBean();

            DataBox dbox = message.selectMessage(box);
            request.setAttribute("selectMessage", dbox);

            ArrayList<DataBox> list = message.selectMessageList(box);
            request.setAttribute("selectMessageList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수정페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        // System.out.println("---- Message Update Page Start ----------");
        try {

            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다
            TutorMessageAdminBean message = new TutorMessageAdminBean();

            DataBox dbox = message.selectMessage(box);
            request.setAttribute("selectMessage", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수정하여 저장할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            TutorMessageAdminBean message = new TutorMessageAdminBean();

            int isOk = message.updateMessage(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorMessageAdminServlet";
            box.put("p_process", "listPage");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

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
     * 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            TutorMessageAdminBean message = new TutorMessageAdminBean();

            int isOk = message.deleteMessage(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorMessageAdminServlet";
            box.put("p_process", "listPage");

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
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            TutorMessageAdminBean message = new TutorMessageAdminBean();

            ArrayList<DataBox> list = message.selectMessageList(box);
            request.setAttribute("selectMessageList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void errorPage(RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_process", "");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }

}
