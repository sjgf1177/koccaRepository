//**********************************************************
//1. 제      목: 과정설문 결과분석
//2. 프로그램명: SulmunSubjResultServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-18
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

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunAllPaperBean;
import com.credu.research.SulmunQuestionExampleData;
import com.credu.research.SulmunSubjPaperBean;
import com.credu.research.SulmunSubjResultBean;
import com.credu.system.AdminUtil;

/**
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.research.SulmunSubjResultServlet")
public class SulmunSubjResultServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8046365936912782180L;

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
            v_process = box.getStringDefault("p_process", "SulmunResultPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!v_process.equals("SulmunEachResultPage") && !v_process.equals("SubjResearch")) {
                if (!AdminUtil.getInstance().checkRWRight("SulmunSubjResultServlet", v_process, out, box)) {
                    return;
                }
            } else {
                if (box.getSession("userid").equals("")) {
                    request.setAttribute("tUrl", request.getRequestURI());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }

            if (v_process.equals("SulmunResultPage")) {
                this.performSulmunResultPage(request, response, box, out); // 과정설문 결과
            } else if (v_process.equals("SulmunResultExcelPage")) {
                this.performSulmunResultExcelPage(request, response, box, out);
            } else if (v_process.equals("SulmunDetailResultExcelPage")) {
                this.performSulmunDetailResultExcelPage(request, response, box, out);
            } else if (v_process.equals("SulmunEachResultPage")) {
                this.performSulmunEachResultPage(request, response, box, out);
            } else if (v_process.equals("SubjResearch")) {
                this.performSubjResearch(request, response, box, out);
            } else if (v_process.equals("SulmunUserPage")) { // 설문 응시 페이지 - 개인별보기
                this.performSulmunUserPage(request, response, box, out);
            } else if (v_process.equals("SulmunUserResultInsert")) {
                this.performSulmunUserResultInsert(request, response, box, out);
            } else if (v_process.equals("SulmunUserResultPage")) {
                this.performSulmunUserResultPage(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 과정설문 결과 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunSubjResult_L.jsp";

            SulmunSubjResultBean bean = new SulmunSubjResultBean();
            ArrayList<DataBox> list2 = bean.selectResultMemberList(box);
            request.setAttribute("ResultMemberList", list2); // 개인별

            ArrayList<SulmunQuestionExampleData> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunResultPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문 개인별 보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunSubjResultMember_L.jsp";

            SulmunAllPaperBean bean = new SulmunAllPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");
            DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1);
            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunResultExcelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunSubjResult_E.jsp";

            SulmunSubjResultBean bean = new SulmunSubjResultBean();
            ArrayList<SulmunQuestionExampleData> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultExcelPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunResultExcelPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunDetailResultExcelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunSubjDetailResult_E.jsp";

            SulmunSubjPaperBean bean1 = new SulmunSubjPaperBean();
            DataBox dbox1 = bean1.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1);

            SulmunSubjResultBean bean = new SulmunSubjResultBean();
            ArrayList<DataBox> list1 = bean.selectSulmunDetailResultExcelList(box);
            request.setAttribute("SulmunDetailResultExcelPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunResultExcelPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunEachResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/course/zu_SulmunSubj_L.jsp";

            SulmunSubjResultBean bean = new SulmunSubjResultBean();

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("SulmunResultList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunEachResultPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정설문 결과
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjResearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            box.put("p_action", "go");
            SulmunSubjResultBean bean = new SulmunSubjResultBean();
            ArrayList<SulmunQuestionExampleData> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_SulmunSubj_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjResearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 안쓰임
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 안쓰임
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunUserResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultPage()\r\n" + ex.getMessage());
        }
    }
}
