package controller.mobile.information;

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
import com.credu.mobile.information.NoticeBean;

/**
 * 공지사항 목록 조회 및 조회 수 정보를 갱신한다.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.information.NoticeServlet")
public class NoticeServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
            process = box.getStringDefault("process", "noticeList");

            if (process.equals("noticeList")) { // 목록 화면. 기본페이지
                this.performSelectnoticeList(req, res, box, out);

            } else if (process.equals("updateViewCount")) {
                this.performUpdateViewCount(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 공지사항 조회 수 정보를 갱신한다.
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     */
    private void performUpdateViewCount(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            NoticeBean bean = new NoticeBean();
            bean.updateNoticeViewCount(box.getInt("seq"));

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectnoticeList()\r\n" + ex.getMessage());
        }

    }

    /**
     * 공지사항 목록을 조회한다.
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performSelectnoticeList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            
            String reqType = box.getStringDefault("reqType", "normal");
            String dispatcherUrl = "";
            if ( reqType.equals("normal") ) {
                dispatcherUrl = "/mobile/jsp/information/notice.jsp";
            } else if (reqType.equals("ajax") ) {
                dispatcherUrl = "/mobile/jsp/information/noticeListAjaxResult.jsp";
            }

            NoticeBean bean = new NoticeBean();
            ArrayList noticeList = bean.selectNoticeList(box);

            req.setAttribute("noticeList", noticeList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectnoticeList()\r\n" + ex.getMessage());
        }

    }

}
