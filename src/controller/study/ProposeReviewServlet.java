package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.ProposeReviewBean;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.study.ProposeReviewServlet")
public class ProposeReviewServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        String process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            process = box.getStringDefault("p_process", "selectReviewList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("p_frmURL", request.getRequestURI().toString() + "?p_process=" + process);
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (process.equals("registerReview")) { // 등록 
                this.performRegisterReview(request, response, box, out);

            } else if (process.equals("modifyReview")) { // 수정
                this.performModifyReview(request, response, box, out);

            } else if (process.equals("deleteReview")) { // 삭제
                this.performDeleteReview(request, response, box, out);

            } else if (process.equals("selectReviewList") || process.equals("selectReviewListForAjax")) { // 목록조회
                this.performSelectReviewList(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 정규과정 후기를 등록한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void performRegisterReview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeReviewBean bean = new ProposeReviewBean();

            int resultCnt = bean.registerOnlineClassReview(box);

            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/onlineClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/study/onlineClassReviewAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegisterReview()\r\n" + ex.getMessage());
        }

    }

    /**
     * 정규과정 후기를 수정한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void performModifyReview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeReviewBean bean = new ProposeReviewBean();

            int resultCnt = bean.modifyOnlineClassReview(box);
            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/onlineClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/study/onlineClassReviewAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performModifyReview()\r\n" + ex.getMessage());
        }

    }

    /**
     * 정규과정 후기를 삭제한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void performDeleteReview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeReviewBean bean = new ProposeReviewBean();

            int resultCnt = bean.deleteOnlineClassReview(box);

            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/onlineClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/study/onlineClassReviewAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteReview()\r\n" + ex.getMessage());
        }

    }

    /**
     * 정규과정 후기 목록을 조회한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    public void performSelectReviewList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String retUrl = "";
            if (box.getString("p_process").equals("selectReviewList")) {
                retUrl = "/learn/user/2013/portal/study/gu_ProposeReview_L.jsp";

            } else if ( box.getString("p_process").equals("selectReviewListForAjax") ){
                retUrl = "/learn/user/2013/portal/propose/zu_Subject_AjaxResult.jsp";
            }
            ProposeReviewBean bean = new ProposeReviewBean();

            ArrayList<DataBox> reviewList = bean.selectSubjReviewList(box);

            request.setAttribute("reviewList", reviewList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(retUrl);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + retUrl);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectReviewList()\r\n" + ex.getMessage());
        }

    }
}
