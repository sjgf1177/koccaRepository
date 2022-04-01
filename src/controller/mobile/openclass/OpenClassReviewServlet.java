package controller.mobile.openclass;

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
import com.credu.mobile.openclass.OpenClassBean;

/**
 * �������� �ı� ��� �� ���� ����� ����Ѵ�.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.openclass.OpenClassReviewServlet")
public class OpenClassReviewServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            v_process = box.getString("process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
//          System.out.println("\n++++++++++++++++review before "+box.getString("cont")+"++++++++++++++++++\n");
            box.put("cont", URLDecoder.decode(box.getString("cont"), "UTF-8")); // rsg 20170711 ���ڵ� ���� ����
//          System.out.println("\n++++++++++++++++review after "+box.getString("cont")+"++++++++++++++++++\n");
            
            if (v_process.equals("registerReview")) { // ��� 
                this.performRegisterReview(request, response, box, out);

            } else if (v_process.equals("modifyReview")) { // ����
                this.performModifyReview(request, response, box, out);

            } else if (v_process.equals("deleteReview")) { // ����
                this.performDeleteReview(request, response, box, out);

            } else if (v_process.equals("selectReviewList")) { // �����ȸ
                this.performSelectReviewList(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �������� �ı⸦ ����Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void performRegisterReview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OpenClassBean bean = new OpenClassBean();
            int resultCnt = bean.registerOpenClassReview(box);

            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassReviewAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassViewDetail()\r\n" + ex.getMessage());
        }

    }

    /**
     * �������� �ı⸦ �����Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void performModifyReview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OpenClassBean bean = new OpenClassBean();
            int resultCnt = bean.modifyOpenClassReview(box);

            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassReviewAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassViewDetail()\r\n" + ex.getMessage());
        }

    }

    /**
     * �������� �ı⸦ �����Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void performDeleteReview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OpenClassBean bean = new OpenClassBean();

            int resultCnt = bean.deleteOpenClassReview(box);

            box.put("resultCnt", resultCnt);
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassReviewAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassReviewAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassViewDetail()\r\n" + ex.getMessage());
        }

    }
    
    /**
     * �������� �ı� ����� ��ȸ�Ѵ�.
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
            OpenClassBean bean = new OpenClassBean();

            ArrayList openClassReviewList = bean.selectOpenClassReviewList(box);

            request.setAttribute("openClassReviewList", openClassReviewList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassReviewListAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassReviewListAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassViewDetail()\r\n" + ex.getMessage());
        }

    }
}
