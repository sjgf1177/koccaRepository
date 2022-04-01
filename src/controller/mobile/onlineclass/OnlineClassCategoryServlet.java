package controller.mobile.onlineclass;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.onlineclass.OnlineClassBean;

/**
 * 정규과정 분야별 전체 목록을 조회한다.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.onlineclass.OnlineClassCategoryServlet")
public class OnlineClassCategoryServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            process = box.getStringDefault("process", "selectCategoryList");

            if (process.equals("selectCategoryList")) { // 목록 화면. 기본페이지
                this.performSelectCategoryList(req, res, box, out);

            } else if (process.equals("selectCategorySubjectList")) { // 분야별 과정 목록
                this.performSelectCategorySubjectList(req, res, box, out);

            } else if (process.equals("selectCategoryDetailList")) { // 분야별 과정 세부 목록
                this.performSelectCategoryDetailList(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 정규과정 분야별 전체 목록을 조회한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectCategoryList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassCategoryList.jsp";

            OnlineClassBean bean = new OnlineClassBean();
            ArrayList categoryList = bean.selectCategoryList("1000");

            req.setAttribute("categoryList", categoryList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 정규과정 분류별 과정 목록을 조회한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectCategorySubjectList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassCategorySubjectAjaxResult.jsp";

            OnlineClassBean bean = new OnlineClassBean();
            ArrayList categorySubjectList = bean.selectClassifySubjectList(box);

            req.setAttribute("categorySubjectList", categorySubjectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategorySubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 정규과정 분야별 상세(전체) 목록을 조회한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectCategoryDetailList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassCategoryDetailList.jsp";

            OnlineClassBean bean = new OnlineClassBean();
            ArrayList categoryDetailList = bean.selectCategoryDetailList(box);

            req.setAttribute("categoryDetailList", categoryDetailList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryDetailList()\r\n" + ex.getMessage());
        }
    }
 
}