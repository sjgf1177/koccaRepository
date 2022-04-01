//**********************************************************
//1. 제      목: 진단테스트 관리
//2. 프로그램명: JindanUserServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 2005.12.21
//7. 수      정:
//
//**********************************************************

package controller.jindan;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.jindan.JindanUserBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.jindan.JindanUserServlet")
public class JindanUserServlet extends HttpServlet implements Serializable {
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

            if (v_process.equals("JindanUserList")) { //진단테스트사용자 문제지 리스트 --
                this.performJindanUserList(request, response, box, out);
            } else if (v_process.equals("JindanUserPaperListPage")) { //진단테스트사용자 문제보기 --
                this.performJindanUserPaperListPage(request, response, box, out);
            } else if (v_process.equals("JindanUserResultInsert")) { //진단테스트자가 진단테스트 제출
                this.performJindanUserResultInsert(request, response, box, out);
            } else if (v_process.equals("JindanUserResultView")) { // 진단테스트 결과보기
                this.performJindanUserResultView(request, response, box, out);
            } else if (v_process.equals("JindanHistoryList")) { //나의 지난 진단이력 조회
                this.performJindanHistoryUserList(request, response, box, out);
            } else if (v_process.equals("CourseIntroMove")) { //나의 지난 진단이력 조회
                this.performCourseIntroMove(request, response, box, out);
            } else if (v_process.equals("JindanListPage")) { //2009.11.30 학습진단 과정 목록
                this.performJindanListPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 진단테스트 사용자 문제지 리스트 -- fix된 리스트페이지로 이동
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performJindanUserList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/portal/jindan/gu_JindanUserList_L.jsp";

            //진단 기 응시여부가져오기
            JindanUserBean bean = new JindanUserBean();
            ArrayList<DataBox> list1 = bean.SelectJindanHistoryCheck(box);
            request.setAttribute("JindanHistoryCheck", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진단테스트에서 문제가져오기 -- #
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/portal/jindan/gu_JindanUserPaper_I.jsp";
            String jindannumCnt = "5"; //과목당 가져올 문제수를 지정

            box.put("p_jindannumCnt", jindannumCnt);
            JindanUserBean bean = new JindanUserBean();

            //진단문제가져오기
            ArrayList<ArrayList<DataBox>> list1 = bean.SelectJindanQuestionList(box);
            request.setAttribute("PaperQuestionJindanList", list1);
            request.setAttribute("p_upperclass", box.getString("class1"));
            request.setAttribute("p_middleclass", box.getString("class2"));
            request.setAttribute("p_lowerclass", box.getString("class3"));
            request.setAttribute("p_classname", box.getString("classname"));
            request.setAttribute("p_jindancnt", jindannumCnt);

            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanUserPaperListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진단테스트문제지 등록할때(제출)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String v_url = "/servlet/controller.jindan.JindanUserServlet";
            String v_msg = "";

            JindanUserBean bean = new JindanUserBean();
            int isOk = bean.InsertResult(box);

            box.put("p_upperclass", box.getString("p_upperclass"));
            box.put("p_middleclass", box.getString("p_middleclass"));
            box.put("p_lowerclass", box.getString("p_lowerclass"));

            // String v_isopenanswer = box.getString("p_isopenanswer");

            box.put("p_process", "JindanUserResultView");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk == 1) {
                v_msg = "제출되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "제출에 실패했습니다.  다시 응시하여주십시요";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanUserResultInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진단테스트 결과바로보기, 진단히스토리 상세팝업
     **/
    public void performJindanUserResultView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/portal/jindan/gu_JindanResult_L.jsp";

            request.setAttribute("p_upperclass", box.getString("p_upperclass"));
            request.setAttribute("p_middleclass", box.getString("p_middleclass"));
            request.setAttribute("p_lowerclass", box.getString("p_lowerclass"));
            request.setAttribute("p_classname", box.getString("p_classname"));
            String jindanDate = box.getString("p_jindanDate");
            if (jindanDate.equals("")) { // 날짜값이 없다면 - 응시후 결과 바로보기시
                request.setAttribute("p_jindanDate", FormatDate.getDate("yyyy.MM.dd")); //진단일
            } else {
                request.setAttribute("p_jindanDate", FormatDate.getFormatDate(jindanDate, "yyyy.MM.dd")); //진단일
            }

            JindanUserBean bean = new JindanUserBean();
            ArrayList<DataBox> list = bean.SelectJindanResultList(box);
            request.setAttribute("JindanUserResult", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanUserResultView()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진단이력조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performJindanHistoryUserList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/portal/jindan/gu_JindanHistory_L.jsp";

            JindanUserBean bean = new JindanUserBean();
            ArrayList<DataBox> list = bean.SelectJindanHistoryList(box);
            request.setAttribute("JindanHistoryList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진단결과에서 과정상세정보로 이동
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCourseIntroMove(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/servlet/controller.course.CourseIntroServlet";

            String p_subj = box.getString("pp_subj");
            String p_tabnum = box.getString("pp_tabnum");
            String p_process = box.getStringDefault("pp_process", "SubjectPreviewPage");
            String p_rprocess = box.getStringDefault("pp_rprocess", "SubjectList");

            box.put("p_subj", p_subj);
            box.put("p_tabnum", p_tabnum);
            box.put("p_process", p_process);
            box.put("p_rprocess", p_rprocess);

            AlertManager alert = new AlertManager();

            String v_msg = "";
            alert.alertOkMessage(out, v_msg, v_url, box);
            //alert.alertOkMessage(out, v_msg, v_url , box, true, true,false );
            //alert.alertOkMessage(out, v_msg, v_url , box);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진단 과정 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/portal/jindan/gu_JindanList_L.jsp";

            JindanUserBean bean = new JindanUserBean();
            ArrayList list1 = bean.SelectJindanList(box);
            request.setAttribute("JindanListPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            Log.info.println(this, box, v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyQnaStudyListPage()\r\n" + ex.getMessage());
        }
    }

}