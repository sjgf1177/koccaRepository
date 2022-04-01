package controller.mobile.myclass;

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
import com.credu.mobile.myclass.MyClassBean;

/**
 * 지난 강의 관리를 수행한다. 지난 강의라 함은 과거에 수강 신청을 하였고, 교육기간이 만료된 과정들을 말한다. 수료/미수료 여부 관계 없이
 * 모든 목록을 조회한다.
 * 
 * @author saderaser
 * 
 */
@WebServlet("/servlet/controller.mobile.myclass.LastSubjectServlet")
public class LastSubjectServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
            process = box.getStringDefault("process", "selectLastSubjectList");

            if (process.equals("selectLastSubjectList")) { // 지난 강의 목록 조회
                this.performSelectLastSubjectList(req, res, box, out);

            } else if (process.equals("reviewSubject")) { // 복습하기
                this.performReviewSubject(req, res, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 정규과정에서 과거에 수강신청을 하였고, 교육기간이 만료된 과정 목록을 모두 조회한다. 수료/미수료에 관계없이 모두 조회한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectLastSubjectList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            String reqType = box.getStringDefault("reqType", "normal");
            String dispatcherUrl = "";
            if (reqType.equals("normal")) {
                dispatcherUrl = "/mobile/jsp/myclass/myclassLastSubjectList.jsp";
            } else if (reqType.equals("ajax")) {
                dispatcherUrl = "/mobile/jsp/myclass/myclassLastSubjectListAjaxResult.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> lastSubjectList = bean.selectLastSubjectList(box);

            req.setAttribute("lastSubjectList", lastSubjectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 복습하기 화면으로 이동한다.
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    public void performReviewSubject(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            String dispatcherUrl = "/mobile/jsp/myclass/myclassReviewSubject.jsp";

            MyClassBean bean = new MyClassBean();

            DataBox reviewSubjctInfo = bean.selectReviewSubjectInfo(box);
            ArrayList<DataBox> reviewSubjctLessonList = bean.selectLessonList(box);

            req.setAttribute("reviewSubjctInfo", reviewSubjctInfo);
            req.setAttribute("reviewSubjctLessonList", reviewSubjctLessonList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

}