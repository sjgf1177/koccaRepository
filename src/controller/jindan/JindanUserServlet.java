//**********************************************************
//1. ��      ��: �����׽�Ʈ ����
//2. ���α׷���: JindanUserServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: 2005.12.21
//7. ��      ��:
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

            if (v_process.equals("JindanUserList")) { //�����׽�Ʈ����� ������ ����Ʈ --
                this.performJindanUserList(request, response, box, out);
            } else if (v_process.equals("JindanUserPaperListPage")) { //�����׽�Ʈ����� �������� --
                this.performJindanUserPaperListPage(request, response, box, out);
            } else if (v_process.equals("JindanUserResultInsert")) { //�����׽�Ʈ�ڰ� �����׽�Ʈ ����
                this.performJindanUserResultInsert(request, response, box, out);
            } else if (v_process.equals("JindanUserResultView")) { // �����׽�Ʈ �������
                this.performJindanUserResultView(request, response, box, out);
            } else if (v_process.equals("JindanHistoryList")) { //���� ���� �����̷� ��ȸ
                this.performJindanHistoryUserList(request, response, box, out);
            } else if (v_process.equals("CourseIntroMove")) { //���� ���� �����̷� ��ȸ
                this.performCourseIntroMove(request, response, box, out);
            } else if (v_process.equals("JindanListPage")) { //2009.11.30 �н����� ���� ���
                this.performJindanListPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �����׽�Ʈ ����� ������ ����Ʈ -- fix�� ����Ʈ�������� �̵�
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

            //���� �� ���ÿ��ΰ�������
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
     * �����׽�Ʈ���� ������������ -- #
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
            String jindannumCnt = "5"; //����� ������ �������� ����

            box.put("p_jindannumCnt", jindannumCnt);
            JindanUserBean bean = new JindanUserBean();

            //���ܹ�����������
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
     * �����׽�Ʈ������ ����Ҷ�(����)
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
                v_msg = "����Ǿ����ϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "���⿡ �����߽��ϴ�.  �ٽ� �����Ͽ��ֽʽÿ�";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanUserResultInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����׽�Ʈ ����ٷκ���, ���������丮 ���˾�
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
            if (jindanDate.equals("")) { // ��¥���� ���ٸ� - ������ ��� �ٷκ����
                request.setAttribute("p_jindanDate", FormatDate.getDate("yyyy.MM.dd")); //������
            } else {
                request.setAttribute("p_jindanDate", FormatDate.getFormatDate(jindanDate, "yyyy.MM.dd")); //������
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
     * �����̷���ȸ
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
     * ���ܰ������ ������������ �̵�
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
     * ���� ���� ����Ʈ
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