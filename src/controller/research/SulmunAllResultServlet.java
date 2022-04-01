//**********************************************************
//1. 제      목: 과정설문
//2. 프로그램명: SulmunAllResultServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//7. 수      정: 조재형
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
import com.credu.research.SulmunAllResultBean;
import com.credu.research.SulmunQuestionExampleData;
import com.credu.system.AdminUtil;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.research.SulmunAllResultServlet")
public class SulmunAllResultServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -124567515942386876L;

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

            if (!AdminUtil.getInstance().checkRWRight("SulmunAllResultServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("SulmunResultPage")) {
                this.performSulmunResultPage(request, response, box, out);
            } else if (v_process.equals("SulmunResultExcelPage")) {
                this.performSulmunResultExcelPage(request, response, box, out);
            } else if (v_process.equals("SulmunEachResultPage")) {
                this.performSulmunEachResultPage(request, response, box, out);
            } else if (v_process.equals("SulmunUserPage")) { //설문 응시 페이지
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
     * 전체설문 결과분석 리스트
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
            String v_return_url = "/learn/admin/research/za_SulmunAllResult_L.jsp";

            SulmunAllResultBean bean = new SulmunAllResultBean();
            ArrayList<SulmunQuestionExampleData> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1);

            ArrayList<DataBox> list2 = bean.getSulGrpList(box);
            request.setAttribute("SulmunUserList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunResultPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 전체설문 결과분석 리스트(엑셀)
     * 
     * @return void
     */
    public void performSulmunResultExcelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunAllResult_E.jsp";

            SulmunAllResultBean bean = new SulmunAllResultBean();
            ArrayList<SulmunQuestionExampleData> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1);

            ArrayList<DataBox> list2 = bean.getSulGrpList(box);
            request.setAttribute("SulmunUserList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunResultExcelPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 안쓰임
     * 
     * @return void
     */
    public void performSulmunEachResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunEachResultPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 안쓰임
     * 
     * @return void
     */
    public void performSulmunUserPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 안쓰임
     * 
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
