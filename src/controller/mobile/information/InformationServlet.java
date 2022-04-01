package controller.mobile.information;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.information.InformationBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.information.InformationServlet")
public class InformationServlet extends javax.servlet.http.HttpServlet implements Serializable {
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

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        String process = "";

        try {
            res.setContentType("text/html;charset=euc-kr");
            out = res.getWriter();
            box = RequestManager.getBox(req);
            process = box.getStringDefault("process", "introPage");

            if (process.equals("introPage")) { // 기본페이지
                this.performIntroPage(req, res, box, out);

            } else if (process.equals("registerContactUs")) { // 1:1 문의 등록
                this.performRegisterContactUs(req, res, box, out);

            } else if (process.equals("newsList")) { // 보도 자료 목록조회
                this.performSelectNewsList(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 고객센터 기본 화면으로 이동한다.
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performIntroPage(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/information/intro.jsp";

            InformationBean bean = new InformationBean();
            ArrayList newsList = bean.selectNewsList(box);

            req.setAttribute("newsList", newsList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegisterContactUs()\r\n" + ex.getMessage());
        }

    }

    /**
     * 보도자료 목록을 조회한다.
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performSelectNewsList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/information/newsListAjaxResult.jsp";

            InformationBean bean = new InformationBean();
            ArrayList newsList = bean.selectNewsList(box);

            req.setAttribute("newsList", newsList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegisterContactUs()\r\n" + ex.getMessage());
        }

    }

    /**
     * 1:1 문의 내용을 등록한다.
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performRegisterContactUs(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            JSONObject jsonObj = new JSONObject();
            InformationBean bean = new InformationBean();
            int resultCnt = bean.regisetrContactUs(box);

            jsonObj.put("result", resultCnt);

            out.write(jsonObj.toJSONString());
            out.flush();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegisterContactUs()\r\n" + ex.getMessage());
        }

    }

}
