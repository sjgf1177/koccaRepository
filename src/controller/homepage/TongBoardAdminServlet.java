//**********************************************************
//  1. 제      목: 아카데미 이야기 게시물 관리
//  2. 프로그램명 : TongBoardAdminServlet.Java
//  3. 개      요: 아카데미 이야기 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0공지사항
//  6. 작      성: 
//  7. 수      정: 
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.TongBoardAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.TongBoardAdminServlet")
public class TongBoardAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            request.setAttribute("uploadName", "tong");

            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "list");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("TongBoardAdminServlet", v_process, out, box)) {
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
     * 아카데미 이야기 목록보기
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

            TongBoardAdminBean bean = new TongBoardAdminBean();
            ArrayList<DataBox> tongBoardList = bean.selectTongBoardList(box);

            request.setAttribute("tongBoardList", tongBoardList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = null;

            rd = sc.getRequestDispatcher("/learn/admin/homepage/tongboard/za_TongBoard_L.jsp");

            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 게시물 등록 화면으로 이동
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
            RequestDispatcher rd = null;

            rd = sc.getRequestDispatcher("/learn/admin/homepage/tongboard/za_TongBoard_I.jsp");

            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }

    }

    /**
     * 게시물 등록 처리
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

            TongBoardAdminBean bean = new TongBoardAdminBean();

            String retUrl = "/servlet/controller.homepage.TongBoardAdminServlet";
            box.put("p_process", "list");
            String resultMsg = "";
            int resultCnt = bean.insertTongBoard(box);

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
     * 게시물 내용 보기
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
            
            TongBoardAdminBean bean = new TongBoardAdminBean();
            DataBox tongboard = bean.selectTongBoard(box);

            request.setAttribute("tongboard", tongboard);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = null;

            rd = sc.getRequestDispatcher("/learn/admin/homepage/tongboard/za_TongBoard_R.jsp");

            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performView()\r\n" + ex.getMessage());
        }

    }

    /**
     * 게시물 삭제 처리
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

            TongBoardAdminBean bean = new TongBoardAdminBean();

            String retUrl = "/servlet/controller.homepage.TongBoardAdminServlet";
            box.put("p_process", "list");
            String resultMsg = "";
            int resultCnt = bean.deleteTongBoard(box);

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
     * 게시물 수정 화면으로 이동
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

            TongBoardAdminBean bean = new TongBoardAdminBean();
            DataBox tongboard = bean.selectTongBoard(box);

            request.setAttribute("tongboard", tongboard);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = null;
            
            rd = sc.getRequestDispatcher("/learn/admin/homepage/tongboard/za_TongBoard_U.jsp");

            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }

    }

    /**
     * 게시물 수정 처리
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

            TongBoardAdminBean bean = new TongBoardAdminBean();

            String retUrl = "/servlet/controller.homepage.TongBoardAdminServlet";
            box.put("p_process", "list");
            String resultMsg = "";
            int resultCnt = bean.updateTongBoard(box);

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
