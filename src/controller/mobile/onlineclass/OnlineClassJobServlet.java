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
 * 정규과정 직업별 전체 목록을 조회한다.
 * @author saderaser
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.onlineclass.OnlineClassJobServlet")
public class OnlineClassJobServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            process = box.getStringDefault("process", "selectJobList");
            
            System.out.println("process : " + process);

            if (process.equals("selectJobList")) { // 목록 화면. 기본페이지
                this.performSelectJobList(req, res, box, out);

            } else if (process.equals("selectJobDetailList")) { // 직업별 과정 세부 목록
                this.performSelectJobDetailList(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 정규과정 직업별 목록 및 등록된 과정수를 조회한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectJobList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassJobList.jsp";

            OnlineClassBean bean = new OnlineClassBean();
            ArrayList categoryList = bean.selectCategoryList("2000");

            req.setAttribute("categoryList", categoryList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 정규과정 직업별 세부(전쳬) 목록을 조회한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectJobDetailList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassJobDetailList.jsp";

            OnlineClassBean bean = new OnlineClassBean();
            ArrayList jobDetailList = bean.selectJobDetailList(box);

            req.setAttribute("jobDetailList", jobDetailList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

}