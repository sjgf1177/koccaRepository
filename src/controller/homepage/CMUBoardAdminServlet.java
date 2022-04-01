package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.CMUBoardAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.CMUBoardAdminServlet")
public class CMUBoardAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            request.setAttribute("uploadName", "cmu");
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "list");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("CMUBoardAdminServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("list")) { // 목록화면
                this.performList(request, response, box, out);

            } else if (v_process.equals("insertPage")) { // 등록화면
                this.performInsertPage(request, response, box, out);

            } else if (v_process.equals("insert")) { // 등록 처리
                this.performInsert(request, response, box, out);

            } else if (v_process.equals("view")) { // 내용 보기
                this.performView(request, response, box, out);

            } else if (v_process.equals("delete")) { // 삭제 처리
                this.performDelete(request, response, box, out);

            } else if (v_process.equals("updatePage")) { // 수정화면
                this.performUpdatePage(request, response, box, out);

            } else if (v_process.equals("update")) { // 수정 처리
                this.performUpdate(request, response, box, out);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 공지사항 상세보기로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CMUBoardAdminBean bean = new CMUBoardAdminBean();
            ArrayList<DataBox> cmuBoardList = bean.selectCMUBoardList(box);

            request.setAttribute("cmuBoardList", cmuBoardList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/cmuboard/za_CMUBoard_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록 화면으로 이동
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/cmuboard/za_CMUBoard_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }

    }

    /**
     * 등록 처리
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CMUBoardAdminBean bean = new CMUBoardAdminBean();

            String retUrl = "/servlet/controller.homepage.CMUBoardAdminServlet";
            box.put("p_process", "list");
            String resultMsg = "";
            int resultCnt = bean.insertCMUBoard(box);

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {
                resultMsg = "insert.ok";
                alert.alertOkMessage(out, resultMsg, retUrl, box);

            } else {
                resultMsg = "insert.fail";
                alert.alertFailMessage(out, resultMsg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }

    }

    /**
     * 내용 보기
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CMUBoardAdminBean bean = new CMUBoardAdminBean();
            DataBox cmuBoard = bean.selectCMUBoard(box);

            request.setAttribute("cmuBoard", cmuBoard);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/cmuboard/za_CMUBoard_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performView()\r\n" + ex.getMessage());
        }

    }

    /**
     * 삭제 처리
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

            CMUBoardAdminBean bean = new CMUBoardAdminBean();

            String retUrl = "/servlet/controller.homepage.CMUBoardAdminServlet";
            box.put("p_process", "list");
            String resultMsg = "";
            int resultCnt = bean.deleteCMUBoard(box);

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {
                resultMsg = "delete.ok";
                alert.alertOkMessage(out, resultMsg, retUrl, box);

            } else {
                resultMsg = "delete.fail";
                alert.alertFailMessage(out, resultMsg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }

    }

    /**
     * 수정 화면으로 이동
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CMUBoardAdminBean bean = new CMUBoardAdminBean();
            DataBox cmuBoard = bean.selectCMUBoard(box);

            request.setAttribute("cmuBoard", cmuBoard);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/cmuboard/za_CMUBoard_U.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }

    }

    /**
     * 수정 처리
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CMUBoardAdminBean bean = new CMUBoardAdminBean();

            String retUrl = "/servlet/controller.homepage.CMUBoardAdminServlet";
            box.put("p_process", "list");
            String resultMsg = "";
            int resultCnt = bean.updateCMUBoard(box);

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {
                resultMsg = "update.ok";
                alert.alertOkMessage(out, resultMsg, retUrl, box);

            } else {
                resultMsg = "update.fail";
                alert.alertFailMessage(out, resultMsg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }

    }

}
