//**********************************************************
//  1. ��      ��: TORON SERVLET
//  2. ���α׷���: ToronServlet.java
//  3. ��      ��: ��й� servlet
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 9. 02
//  7. ��      ��:
//**********************************************************
package controller.study;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.study.*;
import com.credu.library.*;
import com.credu.system.*;

public class ToronServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 3134426325124831082L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

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

            if (v_process.equals("TopicInsertPage")) { //in case of toron topic insert page
                this.performTopicInsertPage(request, response, box, out);
            } else if (v_process.equals("TopicInsert")) { //in case of toron topic insert
                this.performTopicInsert(request, response, box, out);
            } else if (v_process.equals("TopicUpdatePage")) { //in case of toron topic update page
                this.performTopicUpdatePage(request, response, box, out);
            } else if (v_process.equals("TopicUpdate")) { //in case of toron topic update
                this.performTopicUpdate(request, response, box, out);
            } else if (v_process.equals("TopicDelete")) { //in case of toron topic delete
                this.performTopicDelete(request, response, box, out);
            } else if (v_process.equals("ToronInsertPage")) { //in case of toron insert page
                this.performToronInsertPage(request, response, box, out);
            } else if (v_process.equals("ToronInsert")) { //in case of toron insert
                this.performToronInsert(request, response, box, out);
            } else if (v_process.equals("ToronUpdatePage")) { //in case of toron update page
                this.performToronUpdatePage(request, response, box, out);
            } else if (v_process.equals("ToronUpdate")) { //in case of toron update
                this.performToronUpdate(request, response, box, out);
            } else if (v_process.equals("ToronDelete")) { //in case of toron delete
                this.performToronDelete(request, response, box, out);
            } else if (v_process.equals("ToronReplyPage")) { //in case of toron reply page
                this.performToronReplyPage(request, response, box, out);
            } else if (v_process.equals("ToronReply")) { //in case of toron reply
                this.performToronReply(request, response, box, out);
            } else if (v_process.equals("ToronSelect")) { //in case of toron select
                this.performToronSelect(request, response, box, out);
            } else if (v_process.equals("TopicSelect")) { //in case of topic select
                this.performTopicSelect(request, response, box, out);
            } else { //in case of topic list
                this.performTopicList(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * TOPIC INSERT PAGE
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Topic_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("topicInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * TOPIC INSERT
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
            ToronBean bean = new ToronBean();

            int isOk = bean.insertTopic(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronServlet";
            box.put("p_process", "");
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
            throw new Exception("topicInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * TOPIC UPDATE PAGE
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
            ToronBean bean = new ToronBean();
            ToronData data = bean.selectTopic(box);

            request.setAttribute("topicSelect", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Topic_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("topicUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * TOPIC UPDATE
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
            ToronBean bean = new ToronBean();

            int isOk = bean.updateTopic(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronServlet";
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
            throw new Exception("topicUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * TOPIC DELETE
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
            ToronBean bean = new ToronBean();

            int isOk = bean.deleteTopic(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronServlet";
            box.put("p_process", "");

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
            throw new Exception("topicDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * TORON INSERT PAGE
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Toron_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("toronInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * TORON INSERT
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
            ToronBean bean = new ToronBean();

            int isOk = bean.insertToron(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronServlet";
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
            throw new Exception("toronInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * TORON UPDATE PAGE
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
            ToronBean bean = new ToronBean();
            ToronData data = bean.selectToron(box);

            request.setAttribute("toronSelect", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Toron_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("toronUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * TORON UPDATE
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
            ToronBean bean = new ToronBean();

            int isOk = bean.updateToron(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronServlet";
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
            throw new Exception("toronUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * TORON DELETE
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
            ToronBean bean = new ToronBean();

            int isOk = bean.deleteToron(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronServlet";
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
            throw new Exception("toronDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * TORON REPLY PAGE
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
            ToronBean bean = new ToronBean();
            ToronData data = bean.selectToron(box);
            request.setAttribute("selectToron", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Toron_A.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("toronReplyPage()\r\n" + ex.getMessage());
        }

    }

    /**
     * TORON REPLY
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
            ToronBean bean = new ToronBean();

            int isOk = bean.insertToronReply(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.ToronServlet";
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
            throw new Exception("toronReply()\r\n" + ex.getMessage());
        }
    }

    /**
     * TORON SELECT
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
            ToronBean bean = new ToronBean();
            ToronData data1 = bean.selectTopic(box);
            ToronData data2 = bean.selectToron(box);
            ArrayList<ToronData> list1 = bean.selectToronList(box);

            request.setAttribute("topicSelect", data1);
            request.setAttribute("toronSelect", data2);
            request.setAttribute("toronList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Toron_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("topicSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * TOPIC SELECT
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
            ToronBean bean = new ToronBean();
            ToronData data = bean.selectTopic(box);
            ArrayList<ToronData> list1 = bean.selectToronList(box);

            request.setAttribute("topicSelect", data);
            request.setAttribute("toronList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Topic_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("topicSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * TOPIC LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performTopicList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ������ �޴� ���� ���� �߰�
            box.put("p_menu", "31");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            ToronBean bean = new ToronBean();
            ArrayList<ToronData> list1 = bean.selectTopicList(box);

            request.setAttribute("topicList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Toron_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("topicList()\r\n" + ex.getMessage());
        }
    }
}
