//**********************************************************
//1. 제      목: 대상자설문 문제지관리
//2. 프로그램명: SulmunTargetUserServlet.java
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
import com.credu.research.SulmunTargetPaperBean;
import com.credu.research.SulmunTargetUserBean;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.research.SulmunTargetUserServlet")
public class SulmunTargetUserServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1161071554415773669L;

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

            if (!v_process.equals("SulmunUserResultInsert2")) {
                if (box.getSession("userid").equals("")) {
                    request.setAttribute("tUrl", request.getRequestURI());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }

            if (v_process.equals("SulmunUserListPage")) { //대상자설문 사용자 문제지 리스트
                this.performSulmunUserListPage(request, response, box, out);
            } else if (v_process.equals("SulmunUserPaperListPage")) { //대상자설문 사용자 문제보기
                this.performSulmunUserPaperListPage(request, response, box, out);
            } else if (v_process.equals("SulmunMailPage")) { //대상자설문 사용자 문제보기 (메일)
                this.performSulmunMailPage(request, response, box, out);
            } else if (v_process.equals("SulmunUserResultInsert")) { //대상자설문 문제지 삭제할때
                this.performSulmunUserResultInsert(request, response, box, out);
            } else if (v_process.equals("SulmunUserResultInsert2")) { //대상자설문 문제지 삭제할때
                this.performSulmunUserResultInsert2(request, response, box, out);
            } else if (v_process.equals("SulmunUserPaperResult")) { //대상자설문 개인별 평가결과 보기
                this.performSulmunUserPaperResult(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 대상자설문 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            /**
             * request.setAttribute("requestbox", box); String v_return_url =
             * "/learn/user/research/za_SulmunTargetUserList_L.jsp";
             * 
             * SulmunTargetUserBean bean = new SulmunTargetUserBean(); ArrayList
             * list1 = bean.SelectUserList(box);
             * request.setAttribute("SulmunUserList", list1);
             * 
             * ArrayList list2 = bean.SelectUserResultList(box);
             * request.setAttribute("SulmunUserResultList", list2);
             * 
             * ServletContext sc = getServletContext(); RequestDispatcher rd =
             * sc.getRequestDispatcher(v_return_url); rd.forward(request,
             * response);
             */
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 대상자설문 응시 페이지
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
            String v_return_url = "/learn/user/research/zu_SulmunTargetUserPaper_I.jsp";

            SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");

            box.put("p_sulpapernm", box.getString("p_sulpapernm"));

            DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1);
            request.setAttribute("SulmunRequestData", box); // 설문 기본 데이타

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
     * 대상자설문 설문 응시 페이지 (메일용)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunMailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/research/zu_SulmunTargetMailPaper_I.jsp";

            SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");

            box.put("p_sulpapernm", box.getString("p_sulpapernm"));

            DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1);
            request.setAttribute("SulmunRequestData", box); // 설문 기본 데이타

            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 대상자설문 등록할때
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
            String v_url = "/servlet/controller.research.SulmunTargetUserServlet";

            SulmunTargetUserBean bean = new SulmunTargetUserBean();
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            box.put("p_process", "SulmunUserPaperListPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk == 2) {
                v_msg = "설문에 응답해 주셔서 감사합니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
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
     * 대상자설문 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserResultInsert2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/index.jsp";

            SulmunTargetUserBean bean = new SulmunTargetUserBean();
            //            int isOk = bean.InsertSulmunUserResult2(box);
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            box.put("p_process", "SulmunUserPaperListPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk == 2) {
                v_msg = "설문에 응답해 주셔서 감사합니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
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
     * 대상자설문 사용자 문제지 리스트
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
            String v_return_url = "/learn/user/research/zu_SulmunTargetUserResultList.jsp";

            SulmunTargetUserBean bean = new SulmunTargetUserBean();

            DataBox dbox = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", dbox);

            DataBox dbox1 = bean.selectSulmunUser(box);
            request.setAttribute("SulmunUserData", dbox1);

            SulmunTargetPaperBean bean1 = new SulmunTargetPaperBean();
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