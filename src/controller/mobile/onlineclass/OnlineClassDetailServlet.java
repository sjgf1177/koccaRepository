package controller.mobile.onlineclass;

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
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.onlineclass.OnlineClassBean;
import com.credu.mobile.onlineclass.OnlineClassReviewBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.onlineclass.OnlineClassDetailServlet")
public class OnlineClassDetailServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
            process = box.getStringDefault("process", "selectOnlineClassDetail");

            if (process.equals("selectOnlineClassDetail")) { // 목록 화면. 기본페이지
                this.performSelectOnlineClassDetail(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 과정 상세 정보를 조회한다. 과정 기본정보, 차시목록, 후기, 다음과정 등을 조회한다.
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performSelectOnlineClassDetail(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/onlineclass/onlineClassDetail.jsp";

            OnlineClassBean bean = new OnlineClassBean();
            OnlineClassReviewBean reviewBean = new OnlineClassReviewBean();

            DataBox subjectInfo = bean.selectSubjectInfo(box); // 과정 기본정보
            req.setAttribute("subjectInfo", subjectInfo);
            
            ArrayList subjLessonList = bean.selectSubjLessonList(box);
            req.setAttribute("subjLessonList", subjLessonList); // 과정 차시 목록

            ArrayList subjReviewList = reviewBean.selectSubjReviewList(box);
            req.setAttribute("subjReviewList", subjReviewList); // 후기 목록

            ArrayList nextProposeSubjList = bean.selectNextProposeSubjList(box);
            req.setAttribute("nextProposeSubjList", nextProposeSubjList); // 다음과정 목록
            
            Integer.parseInt("001");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectOnlineClassDetail()\r\n" + ex.getMessage());
        }

    }

}
