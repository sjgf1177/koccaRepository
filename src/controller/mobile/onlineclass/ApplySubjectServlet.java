package controller.mobile.onlineclass;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.mobile.subj.SubjectBean;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.onlineclass.ApplyOnlineClassBean;

/**
 * 수강신청 기능을 담당한다.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.onlineclass.ApplySubjectServlet")
public class ApplySubjectServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(req, res);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        String process = "";

        try {
            res.setContentType("text/html;charset=euc-kr");
            out = res.getWriter();
            box = RequestManager.getBox(req);
            process = box.getString("process");

            if (process.equals("applyOnllineClass")) { // 수강신청
                this.performApplyOnlineClass(req, res, box, out);

            } else if (process.equals("cancelApplyOnllineClass")) { // 수강신청 취소
                this.performCancelApplyOnlineClass(req, res, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 정규과정 수강신청을 한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performApplyOnlineClass(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassApplyAjaxResult.jsp";

            ApplyOnlineClassBean bean = new ApplyOnlineClassBean();
            String resultMsg = bean.applyOnlineClass(box);

            req.setAttribute("resultMsg", resultMsg);
            
            if ( resultMsg.equals("apply.ok") ){
                // 찜한 과정 목록에서 삭제
                SubjectBean subjBean = new SubjectBean();
                box.put("jobType", "cancel");
                box.put("classType", "01");
                subjBean.cancelSubjFavor(box);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 정규과정 수강신청을 취소한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCancelApplyOnlineClass(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassApplyAjaxResult.jsp";

            ApplyOnlineClassBean bean = new ApplyOnlineClassBean();
            String resultMsg = bean.deleteApplyInfo(box);

            req.setAttribute("resultMsg", resultMsg);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }
}