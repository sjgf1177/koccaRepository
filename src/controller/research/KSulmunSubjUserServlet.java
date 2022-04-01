//**********************************************************
//1. 제      목: 과정설문 문제지관리
//2. 프로그램명: SulmunSubjUserServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package controller.research;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.KSulmunContentsUserBean;
import com.credu.research.KSulmunSubjUserBean;
import com.credu.research.SulmunCommonUserBean;
import com.credu.research.SulmunRegistUserBean;
import com.credu.research.SulmunSubjPaperBean;
import com.credu.research.SulmunSubjUserBean;
import com.credu.research.SulmunTargetUserBean;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.research.KSulmunSubjUserServlet")
public class KSulmunSubjUserServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2008130702089496941L;

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

            if (box.getSession("userid").equals("")) {
                request.setAttribute("tUrl", request.getRequestURI());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
                return;
            }

            if (v_process.equals("SulmunUserListPage")) { //과정설문 사용자 문제지 리스트
                this.performSulmunUserListPage(request, response, box, out);
            } else if (v_process.equals("SulmunUserPaperListPage")) { //과정설문 사용자 문제보기
                this.performSulmunUserPaperListPage(request, response, box, out);
            } else if (v_process.equals("SulmunUserResultInsert")) { //과정설문 문제지 등록할때
                this.performSulmunUserResultInsert(request, response, box, out);
            } else if (v_process.equals("SulmunUserPaperResult")) { //과정설문 개인별 평가결과 보기
                this.performSulmunUserPaperResult(request, response, box, out);
            } else if (v_process.equals("SulmunNew")) { // 나의강의실 설문페이지(2005.07.20)
                this.performSulmunNew(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 과정설문 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/study/zu_SulmunSubjPaper_L.jsp";

            KSulmunContentsUserBean bean2 = new KSulmunContentsUserBean();
            KSulmunSubjUserBean bean1 = new KSulmunSubjUserBean();

            ArrayList<DataBox> list1 = bean1.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList1", list1);

            ArrayList<DataBox> list2 = bean2.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList2", list2);

            ArrayList<DataBox> list4 = bean2.selectGraduationSubjectList(box);
            request.setAttribute("GraduationSubjectList2", list4);

            /*
             * KSulmunSubjUserBean bean = new KSulmunSubjUserBean(); ArrayList
             * list1 = bean.SelectUserList(box);
             * request.setAttribute("SulmunUserList", list1);
             * 
             * ArrayList list2 = bean.SelectUserResultList(box);
             * request.setAttribute("SulmunUserResultList", list2);
             */
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 나의 강의실 메뉴 설문 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunNew(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/kocca/study/ku_SulmunNew_L.jsp";

            SulmunSubjUserBean bean1 = new SulmunSubjUserBean(); // 과정
            SulmunCommonUserBean bean3 = new SulmunCommonUserBean(); // 일반
            SulmunRegistUserBean bean4 = new SulmunRegistUserBean(); // 가입경로            
            SulmunTargetUserBean bean5 = new SulmunTargetUserBean(); // 대상자  
            //SulmunContentsUserBean bean2 = new SulmunContentsUserBean();   // 컨텐츠- 사용안함.           

            ArrayList<DataBox> list1 = bean1.selectEducationSubjectList(box); //과정설문
            request.setAttribute("EducationSubjectList1", list1);

            ArrayList<DataBox> list_t = bean5.selectSulmunTargetList(box); // 대상자설문리스트          
            request.setAttribute("SulmunTarget", list_t);

            ArrayList<DataBox> list_cm = bean3.SelectUserList(box); // 일반설문리스트          
            request.setAttribute("SulmunCommon", list_cm);

            ArrayList<DataBox> list_r = bean4.SelectUserList(box); // 가입경로설문리스트          
            request.setAttribute("SulmunRegist", list_r);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunNew()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/kocca/research/ku_SulmunSubjUserPaper_I.jsp";

            SulmunSubjPaperBean bean = new SulmunSubjPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));

            box.put("p_upperclass", "ALL");

            DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1); //2005.08.22 by정은년 jsp에서 다 값 받아옴.

            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문 문제지 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // String v_url = "/servlet/controller.research.KSulmunSubjUserServlet";

            KSulmunSubjUserBean bean = new KSulmunSubjUserBean();
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            //box.put("p_process", "SulmunUserPaperListPage");
            box.put("p_process", "SulmunNew");

            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk == 2) {
                v_msg = "설문에 응답해 주셔서 감사합니다.";
                //alert.alertOkMessage(out, v_msg, v_url , box);
                alert.selfClose(out, v_msg); //나의설문에서 과정설문응시후 이동

            } else if (isOk == 1) {
                v_msg = "설문 기간 이전입니다.";
                alert.alertFailMessage(out, v_msg);
            } else if (isOk == 3) {
                v_msg = "설문 기간이 완료되었습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "이미 해당 설문에 응답하셨습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/kocca/research/zu_SulmunSubjUserResultList.jsp";

            KSulmunSubjUserBean bean = new KSulmunSubjUserBean();

            DataBox dbox = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", dbox);

            DataBox dbox1 = bean.selectSulmunUser(box);
            request.setAttribute("SulmunUserData", dbox1);

            SulmunSubjPaperBean bean1 = new SulmunSubjPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean1.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");
            DataBox dbox2 = bean1.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox2);
            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperResult()\r\n" + ex.getMessage());
        }
    }
}