//**********************************************************
//  1. 제      목: 튜터커뮤니티 - 커뮤니티 통계
//  2. 프로그램명 : TutorCommunityStatisServlet.java
//  3. 개      요: 커뮤니티 통계
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 최진규 2013.07.16
//  7. 수      정:
//**********************************************************

package controller.tutorcommunity;

import java.io.PrintWriter;
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
import com.credu.tutorcummunity.TutorCommunityStatisBean;

@WebServlet("/servlet/controller.tutorcommunity.TutorCommunityStatisServlet")
public class TutorCommunityStatisServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6608535634652779551L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
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
            // System.out.println("TutorCommunityStatisServlet : v_process : " + v_process);

            // p_process 값 별 이동될 곳
            if (v_process.equals("listPage")) {
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("detailView")) {
                this.performDetailView(request, response, box, out);
            } else if (v_process.equals("delete")) {
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("answerPage")) {
                this.performAnswerPage(request, response, box, out);
            } else if (v_process.equals("register")) {
                this.performRegister(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    String url_path = "/learn/admin/tutorcommunity/communitystatis/";

    /**
     * 리스트 페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String url = url_path + "communityStatisList.jsp";

            TutorCommunityStatisBean bean = new TutorCommunityStatisBean();

            ArrayList<DataBox> list = bean.selectTutorCommunity(box);
            request.setAttribute("TutorCommunityList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 상세화면 페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performDetailView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String url = url_path + "communityStatisDetailView.jsp";
            // String answercnt = box.getStringDefault("p_answercnt", "0");
            // String commentcnt = box.getStringDefault("p_commentcnt", "0");

            TutorCommunityStatisBean bean = new TutorCommunityStatisBean();

            DataBox dbox = bean.selectTutorCommunityDetailView(box);
            request.setAttribute("SelectView", dbox);

            ArrayList<DataBox> alist = bean.selectAnswerList(box);
            request.setAttribute("AnswerList", alist);

            ArrayList<DataBox> clist = bean.selectCommentList(box);
            request.setAttribute("CommentList", clist);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDetailView()\r\n" + ex.getMessage());
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_msg = "";
            String v_url = "/servlet/controller.tutorcommunity.TutorCommunityStatisServlet";
            box.put("p_process", "listPage");

            TutorCommunityStatisBean bean = new TutorCommunityStatisBean();
            AlertManager alert = new AlertManager();

            int isOk = bean.selectDelete(box);
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
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performAnswerPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String url = url_path + "communityStatisAnswerPage.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAnswerPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performRegister(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_msg = "";
            String v_url = "/servlet/controller.tutorcommunity.TutorCommunityStatisServlet";
            box.put("p_process", "listPage");

            TutorCommunityStatisBean bean = new TutorCommunityStatisBean();
            AlertManager alert = new AlertManager();

            int isOk = bean.answerRegister(box);
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

}
