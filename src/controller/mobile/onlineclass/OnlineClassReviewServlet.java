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
 * ���԰��� ���� ������ �����Ѵ�.
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
            box.put("contents", URLDecoder.decode(box.getString("contents"), "UTF-8")); // rsg 20170711 ���ڵ� ���� ����
//            System.out.println("\n++++++++++++++++review contents before "+box.getString("contents")+"++++++++++++++++++\n");

            if (process.equals("registerReview")) { // ��� 
                this.performRegisterReview(request, response, box, out);

            } else if (process.equals("modifyReview")) { // ����
                this.performModifyReview(request, response, box, out);

            } else if (process.equals("deleteReview")) { // ����
                this.performDeleteReview(request, response, box, out);

            } else if (process.equals("selectReviewList")) { // �����ȸ
                this.performSelectReviewList(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ���԰��� �ı⸦ ����Ѵ�.
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
     * ���԰��� �ı⸦ �����Ѵ�.
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
     * ���԰��� �ı⸦ �����Ѵ�.
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
     * ���԰��� �ı� ����� ��ȸ�Ѵ�.
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
