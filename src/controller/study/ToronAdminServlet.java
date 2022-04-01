//*********************************************************
//  1. ��      ��: TORON ADMIN SERVLET
//  2. ���α׷���: ToronAdminServlet.java
//  3. ��      ��: ��й� ������ servlet
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 21
//  7. ��      ��:
//**********************************************************
package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.ToronAdminBean;
import com.credu.study.ToronData;

@WebServlet("/servlet/controller.study.ToronAdminServlet")
public class ToronAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7841664264559578226L;

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

            if (v_process.equals("ToronList")) { // ������ ��й� ����Ʈ
                this.performToronList(request, response, box, out);
            } else if (v_process.equals("TopicList")) { // ��й� ������ ����Ʈ
                this.performTopicList(request, response, box, out);
            } else if (v_process.equals("TopicSelect")) { // ��й� ���� �󼼺���
                this.performTopicSelect(request, response, box, out);
            } else if (v_process.equals("TopicInsertPage")) { // ��й� �������� ������
                this.performTopicInsertPage(request, response, box, out);
            } else if (v_process.equals("TopicInsert")) { // ��й� ��������
                this.performTopicInsert(request, response, box, out);
            } else if (v_process.equals("TopicUpdatePage")) { // ��й� �������� ���� ������
                this.performTopicUpdatePage(request, response, box, out);
            } else if (v_process.equals("TopicUpdate")) { // ��й� �������� ����
                this.performTopicUpdate(request, response, box, out);
            } else if (v_process.equals("TopicDelete")) { // ��й� ���� ����
                this.performTopicDelete(request, response, box, out);
            }

            else if (v_process.equals("ToronSelect")) { // ��й� �ǰ� �󼼺���
                this.performToronSelect(request, response, box, out);
            } else if (v_process.equals("ToronInsertPage")) { // ��й� �ǰ� �ۼ� ������
                this.performToronInsertPage(request, response, box, out);
            } else if (v_process.equals("ToronInsert")) { // ��й� �ǰ��ۼ�
                this.performToronInsert(request, response, box, out);
            } else if (v_process.equals("ToronUpdatePage")) { // ��й� �ǰ� ���� ������
                this.performToronUpdatePage(request, response, box, out);
            } else if (v_process.equals("ToronUpdate")) { // ��й� �ǰ� ����
                this.performToronUpdate(request, response, box, out);
            } else if (v_process.equals("ToronDelete")) { // ��й� �ǰ� ����
                this.performToronDelete(request, response, box, out);
            } else if (v_process.equals("ToronReplyPage")) { // ��й� �ǰ� �亯������
                this.performToronReplyPage(request, response, box, out);
            } else if (v_process.equals("ToronReply")) { // ��й� �ǰ� �亯
                this.performToronReply(request, response, box, out);
            } else if (v_process.equals("StudentMemberList")) {
                this.performStudentMemberList(request, response, box, out);
            } else if (v_process.equals("ToronStudentUpdate")) {
                this.performToronStudentUpdate(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ������ ��й� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performToronList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ArrayList<DataBox> list1 = bean.selectSubjToronList(box);

            request.setAttribute("toronList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Toron_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� ������ ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTopicList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ArrayList<ToronData> list = bean.selectTopicList(box);

            request.setAttribute("topicList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Topic_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTopicSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� ���� �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTopicSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ToronData data = bean.selectTopic(box);
            ArrayList<ToronData> list1 = bean.selectToronList(box);

            request.setAttribute("topicSelect", data);
            request.setAttribute("toronList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Topic_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTopicSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �������� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTopicInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Topic_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTopicInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performTopicInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.insertTopic(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "TopicList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTopicInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� ���� ���� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTopicUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ToronData data = bean.selectTopic(box);

            request.setAttribute("topicSelect", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Topic_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTopicUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� ���� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performTopicUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.updateTopic(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "TopicList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTopicUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� ���� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performTopicDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.deleteTopic(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "TopicList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTopicDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performToronSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ToronData data1 = bean.selectTopic(box);
            ToronData data2 = bean.selectToron(box);
            ArrayList<ToronData> list1 = bean.selectToronList(box);

            request.setAttribute("topicSelect", data1);
            request.setAttribute("toronSelect", data2);
            request.setAttribute("toronList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Toron_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� �ۼ� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performToronInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Toron_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ��ۼ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performToronInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.insertToron(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "TopicSelect");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� ���� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performToronUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ToronData data = bean.selectToron(box);

            request.setAttribute("toronSelect", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Toron_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performToronUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.updateToron(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "TopicSelect");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performToronDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.deleteToron(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "TopicSelect");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� �亯������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performToronReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ToronData data = bean.selectToron(box);
            request.setAttribute("selectToron", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Toron_A.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronReplyPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� �亯
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performToronReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.insertToronReply(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "TopicSelect");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronReply()\r\n" + ex.getMessage());
        }
    }

    /**
     * STUDENT MEMBER LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudentMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();
            ArrayList<DataBox> list = bean.selectToronMemberList(box);

            request.setAttribute("StudentMemberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ToronMember_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й� �ǰ� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performToronStudentUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ToronAdminBean bean = new ToronAdminBean();

            int isOk = bean.updateToronStudent(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronAdminServlet";
            box.put("p_process", "StudentMemberList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performToronUpdate()\r\n" + ex.getMessage());
        }
    }

}