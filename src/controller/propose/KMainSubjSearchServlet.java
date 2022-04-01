//*********************************************************
//  1. 제      목: 과정검색,신규과정,추천과정
//  2. 프로그램명:  KMainSubjSearchServlet.java
//  3. 개      요: HOMEPAGE 과정관련 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 19
//  7. 수      정:
//**********************************************************
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

@WebServlet("/servlet/controller.propose.KMainSubjSearchServlet")
public class KMainSubjSearchServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -9214589468784611932L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("SubjSearch")) { // 과정검색결과
                this.performSubjSearch(request, response, box, out);
            } else if (v_process.equals("SubjSearchPre")) { // 과정검색전
                this.performSubjSearchPre(request, response, box, out);
            }

            else if (v_process.equals("SubjRecomList")) { //추천과정 리스트
                this.performSubjRecomList(request, response, box, out);
            } else if (v_process.equals("EducationSchedule")) { // 교육일정
                this.performEducationSchedule(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 과정검색결과
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainSubjSearchBean bean = new MainSubjSearchBean();
            ArrayList<DataBox> list = bean.selectSubjSearchK(box);

            request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_SubjSearch_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/course/ku_SubjSearch_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정검색전화면
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjSearchPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ArrayList<DataBox> list = new ArrayList<DataBox>();

            request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_SubjSearch_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/course/ku_SubjSearch_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearchPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 추천과정(메인 -상위4개)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    /*
     * public void performSubjRecomMain(HttpServletRequest request,
     * HttpServletResponse response, RequestBox box, PrintWriter out) throws
     * Exception { try{ request.setAttribute("requestbox", box);
     * MainSubjSearchBean bean = new MainSubjSearchBean(); ArrayList list =
     * bean.selectSubjRecomMain(box);
     * 
     * request.setAttribute("SubjectList", list); ServletContext sc =
     * getServletContext(); RequestDispatcher rd =
     * sc.getRequestDispatcher("/learn/user/propose/zu_SubjRecomMain_L.jsp");
     * rd.forward(request, response); }catch (Exception ex) {
     * ErrorManager.getErrorStackTrace(ex, out); throw new
     * Exception("performSubjRecomMain()\r\n" + ex.getMessage()); } }
     */

    /**
     * 추천과정 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjRecomList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainSubjSearchBean bean = new MainSubjSearchBean();
            ArrayList<DataBox> list = bean.selectSubjRecomList(box);

            request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_SubjRecom_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/course/ku_SubjRecom_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjRecomList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육 일정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationSchedule(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainSubjSearchBean bean = new MainSubjSearchBean();
            ArrayList<DataBox> list1 = bean.selectEducationListK(box);

            request.setAttribute("EducationList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_EducationSchedule_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("educationYearlySchedule()\r\n" + ex.getMessage());
        }
    }

}