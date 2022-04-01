package controller.mobile.onlineclass;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.onlineclass.OnlineClassReviewBean;

/**
 * 정규과정 리뷰 내용을 관리한다.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet")
public class OnlineClassReviewServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        String process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            process = box.getString("process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
//            System.out.println("\n++++++++++++++++review contents before "+box.getString("contents")+"++++++++++++++++++\n");
            box.put("contents", URLDecoder.decode(box.getString("contents"), "UTF-8")); // rsg 20170711 인코딩 문제 수정
//            System.out.println("\n++++++++++++++++review contents before "+box.getString("contents")+"++++++++++++++++++\n");

            if (process.equals("registerReview")) { // 등록 
                this.performRegisterReview(request, response, box, out);

            } else if (process.equals("modifyReview")) { // 수정
                this.performModifyReview(request, response, box, out);

            } else if (process.equals("deleteReview")) { // 삭제
                this.performDeleteReview(request, response, box, out);

            } else if (process.equals("selectReviewList")) { // 목록조회
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
            OnlineClassReviewBean bean = new OnlineClassReviewBean();
            int resultCnt = bean.registerOnlineClassReview(box);

            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/onlineclass/onlineClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/onlineclass/onlineClassReviewAjaxResult.jsp");

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
            OnlineClassReviewBean bean = new OnlineClassReviewBean();
            int resultCnt = bean.modifyOnlineClassReview(box);
            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/onlineclass/onlineClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/onlineclass/onlineClassReviewAjaxResult.jsp");

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
            OnlineClassReviewBean bean = new OnlineClassReviewBean();

            int resultCnt = bean.deleteOnlineClassReview(box);

            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/onlineclass/onlineClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/onlineclass/onlineClassReviewAjaxResult.jsp");

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
    @SuppressWarnings("unchecked")
    public void performSelectReviewList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            OnlineClassReviewBean bean = new OnlineClassReviewBean();

            ArrayList onlineClassReviewList = bean.selectSubjReviewList(box);

            request.setAttribute("onlineClassReviewList", onlineClassReviewList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/onlineclass/onlineClassReviewListAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/onlineclass/onlineClassReviewListAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectReviewList()\r\n" + ex.getMessage());
        }

    }
}
