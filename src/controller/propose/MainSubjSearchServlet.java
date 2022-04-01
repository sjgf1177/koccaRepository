package controller.propose;

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
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.MainSubjSearchBean;

/**
 * 과정 검색 수행을 담당하는 class 이다.
 *
 * @author saderaser
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.propose.MainSubjSearchServlet")
public class MainSubjSearchServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "SubjSearch");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            /*
             * if (!AdminUtil.getInstance().checkLogin(out, box)) { return; }
             */

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("SubjSearch")) { // 과정검색결과
                this.performSubjSearch(request, response, box, out);

            } else if (v_process.equals("SubjSearchPre")) { // 과정검색전
                this.performSubjSearchPre(request, response, box, out);

            } else if (v_process.equals("SubjNewList")) {
                performSubjNewList(request, response, box, out);

            } else if (v_process.equals("SubjRecomList")) { // 추천과정 리스트
                this.performSubjRecomList(request, response, box, out);

            } else if (v_process.equals("EducationSchedule")) { // 교육일정
                this.performEducationSchedule(request, response, box, out);

            } else if (v_process.equals("Feedback")) { // 교육후기
                this.performFeedback(request, response, box, out);
            }
            /*
             * else if(v_process.equals("SubjNewMain")) { //신규과정(메인 -상위4개) this.performSubjNewMain(request, response, box, out); }
             */
            /*
             * else if(v_process.equals("SubjRecomMain")){ //추천과정(메인 -상위4개) this.performSubjRecomMain(request, response, box, out); }
             */
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 과정검색결과
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "/learn/user/2013/portal/propose/zu_TotalSearch_L.jsp";

            String searchText = box.getString("p_lsearchtext");

            if (!searchText.equals("")) {

                MainSubjSearchBean bean = new MainSubjSearchBean();

                ArrayList subjList = bean.selectSubjList(box);
                ArrayList<DataBox> onlineSubjList = new ArrayList<DataBox>();
                ArrayList<DataBox> offlineSubjList = new ArrayList<DataBox>();
                ArrayList<DataBox> openclassSubjList = new ArrayList<DataBox>();
                DataBox dbox = null;

                String detlSubjFlag = box.get("detlSubjFlag");
                String subjFlag = "";

                if (detlSubjFlag.equals("")) {
                    for (int i = 0; i < subjList.size(); i++) {
                        dbox = ((DataBox) subjList.get(i));
                        subjFlag = dbox.getString("d_subj_flag");

                        if (subjFlag.equals("ONLINE")) {
                            onlineSubjList.add(dbox);
                        } else if (subjFlag.equals("OFFLINE")) {
                            offlineSubjList.add(dbox);
                        } else if (subjFlag.equals("OPENCLASS")) {
                            openclassSubjList.add(dbox);
                        }
                    }

                } else if (detlSubjFlag.equals("ONLINE")) {
                    onlineSubjList = subjList;
                } else if (detlSubjFlag.equals("OFFLINE")) {
                    offlineSubjList = subjList;
                } else if (detlSubjFlag.equals("OPENCLASS")) {
                    openclassSubjList = subjList;
                }
                request.setAttribute("onlineSubjList", onlineSubjList);
                request.setAttribute("offlineSubjList", offlineSubjList);
                request.setAttribute("openclassSubjList", openclassSubjList);

            } else {
                v_url = "/";
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/2013/portal/propose/zu_TotalSearch_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정검색전화면
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjSearchPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ArrayList list = new ArrayList();

            request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_SubjSearch_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/course/gu_SubjSearch_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearchPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 추천과정(메인 -상위4개)
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    /*
     * public void performSubjRecomMain(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception { try{ request.setAttribute("requestbox", box); MainSubjSearchBean bean = new MainSubjSearchBean(); ArrayList list = bean.selectSubjRecomMain(box);
     *
     * request.setAttribute("SubjectList", list); ServletContext sc = getServletContext(); RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_SubjRecomMain_L.jsp"); rd.forward(request, response); }catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, out); throw new Exception("performSubjRecomMain()\r\n" + ex.getMessage()); } }
     */

    /**
     * 추천과정 리스트
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjRecomList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainSubjSearchBean bean = new MainSubjSearchBean();
            ArrayList list = bean.selectSubjRecomList(box);

            request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_SubjRecom_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/course/gu_SubjRecom_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjRecomList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육 일정
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performEducationSchedule(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainSubjSearchBean bean = new MainSubjSearchBean();
            ArrayList list1 = bean.selectEducationList(box);

            request.setAttribute("EducationList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_EducationSchedule_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("educationYearlySchedule()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육 후기
     *
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performFeedback(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainSubjSearchBean bean = new MainSubjSearchBean();
            ArrayList list1 = bean.selectFeedbackList(box);

            request.setAttribute("FeedbackList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_Feedback_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("educationYearlySchedule()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설명 : 디컴파일된 소스 복사한 부분
     *
     * @param httpservletrequest
     * @param httpservletresponse
     * @param requestbox
     * @param printwriter
     * @throws Exception
     * @author swchoi
     */
    @SuppressWarnings("unchecked")
    public void performSubjNewList(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, RequestBox requestbox, PrintWriter printwriter) throws Exception {
        try {
            httpservletrequest.setAttribute("requestbox", requestbox);
            MainSubjSearchBean mainsubjsearchbean = new MainSubjSearchBean();
            ArrayList arraylist = mainsubjsearchbean.selectSubjNewList(requestbox);
            httpservletrequest.setAttribute("SubjectList", arraylist);
            ServletContext servletcontext = getServletContext();
            RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher("/learn/user/game/course/gu_SubjNew_L.jsp");
            requestdispatcher.forward(httpservletrequest, httpservletresponse);
            Log.info.println(this, requestbox, "Dispatch to /learn/user/game/course/gu_SubjNew_L.jsp");
        } catch (Exception exception) {
            ErrorManager.getErrorStackTrace(exception, printwriter);
            throw new Exception("performSubjRecomList()\r\n" + exception.getMessage());
        }
    }

}
