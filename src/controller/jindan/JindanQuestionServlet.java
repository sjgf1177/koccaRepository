//**********************************************************
//1. ��      ��: �����׽�Ʈ ���� ����
//2. ���α׷���: ExamQuestionServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: lyh
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

import com.credu.jindan.JindanQuestionBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

@WebServlet("/servlet/controller.jindan.JindanQuestionServlet")
public class JindanQuestionServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6912688018078923615L;

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

            v_process = box.getStringDefault("p_process", "JindanQuestionListPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("JindanQuestionServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("JindanQuestionListPage")) { //�����׽�Ʈ ���� ����Ʈ
                this.performJindanQuestionListPage(request, response, box, out);
            } else if (v_process.equals("JindanQuestionInsertPage")) { //�����׽�Ʈ ���� ��� ������
                this.performJindanQuestionInsertPage(request, response, box, out);
            } else if (v_process.equals("JindanQuestionUpdatePage")) { //�����׽�Ʈ ���� ���� ������
                this.performJindanQuestionUpdatePage(request, response, box, out);
            } else if (v_process.equals("JindanQuestionInsert")) { //�����׽�Ʈ ���� ����Ҷ�
                this.performJindanQuestionInsert(request, response, box, out);
            } else if (v_process.equals("JindanQuestionUpdate")) { //�����׽�Ʈ ���� �����Ͽ� �����Ҷ�
                this.performJindanQuestionUpdate(request, response, box, out);
            } else if (v_process.equals("JindanQuestionDelete")) { // �����׽�Ʈ ���� �����Ҷ�
                this.performJindanQuestionDelete(request, response, box, out);
            } else if (v_process.equals("JindanQuestionFileToDB")) { // �����׽�Ʈ ���� FileToDB
                this.performJindanQuestionFileToDB(request, response, box, out);
            } else if (v_process.equals("insertFileToDB")) { // �����׽�Ʈ ���� FileToDB �Է�
                this.performInsertFileToDB(request, response, box, out);
            } else if (v_process.equals("previewFileToDB")) { // �����׽�Ʈ FileToDB �̸�����
                this.performPreviewFileToDB(request, response, box, out);
            } else if (v_process.equals("JindanQuestionPreview")) { //�����׽�Ʈ ���� �̸� ����
                this.performJindanQuestionPreview(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �� ���� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performJindanQuestionListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestion_L.jsp";

            JindanQuestionBean bean = new JindanQuestionBean();
            ArrayList<DataBox> list1 = bean.selectQuestionList(box);
            request.setAttribute("ExamQuestionList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �� ���� ��� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanQuestionInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestion_I.jsp";
            if (box.getString("p_examtype").equals("2"))
                v_return_url = "/learn/admin/jindan/za_JindanQuestion_I2.jsp";
            if (box.getString("p_examtype").equals("3"))
                v_return_url = "/learn/admin/jindan/za_JindanQuestion_I3.jsp";

            String ctype = "";
            JindanQuestionBean bean = new JindanQuestionBean();
            ctype = bean.getContentType(box);
            box.put("p_ctype", ctype); // ������Ÿ���� ���Ѵ�.

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �� ���� ���� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanQuestionUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestion_U.jsp";
            if (box.getString("p_jindantype").equals("2"))
                v_return_url = "/learn/admin/jindan/za_JindanQuestion_U2.jsp";
            if (box.getString("p_jindantype").equals("3"))
                v_return_url = "/learn/admin/jindan/za_JindanQuestion_U3.jsp";

            JindanQuestionBean bean = new JindanQuestionBean();
            ArrayList<DataBox> list1 = bean.selectExampleData(box);
            request.setAttribute("QuestionJindanData", list1);

            String ctype = "";
            ctype = bean.getContentType(box);
            box.put("p_ctype", ctype); // ������Ÿ���� ���Ѵ�.

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �� ���� ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanQuestionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.jindan.JindanQuestionServlet";

            JindanQuestionBean bean = new JindanQuestionBean();
            int isOk = bean.insertQuestion(box);

            String v_msg = "";
            box.put("p_process", "JindanQuestionInsertPage");
            box.put("p_examnum", "0");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * �� ���� �����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanQuestionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.jindan.JindanQuestionServlet";

            JindanQuestionBean bean = new JindanQuestionBean();
            int isOk = bean.updateQuestion(box);

            String v_msg = "";
            box.put("p_process", "JindanQuestionUpdatePage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == -2) {
                v_msg = "�ش� ������ ������Դϴ�!";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * �� ���� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performJindanQuestionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.jindan.JindanQuestionServlet";

            JindanQuestionBean bean = new JindanQuestionBean();
            int isOk = bean.deleteQuestion(box);

            String v_msg = "";
            box.put("p_process", "JindanQuestionUpdatePage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == -2) {
                v_msg = "�ش� ������ ������Դϴ�!";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����׽�Ʈ ���� FileToDB
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performJindanQuestionFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestionFileToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * �� ���� FileToDB �Է�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestionFileToDB_P.jsp";
            if (box.getString("p_select").equals("2"))
                v_return_url = "/learn/admin/jindan/za_JindanQuestionFileToDB_P2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * �� ���� FileToDB �̸�����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestionFileToDB_P.jsp";
            if (box.getString("p_select").equals("2"))
                v_return_url = "/learn/admin/jindan/za_JindanQuestionFileToDB_P2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����׽�Ʈ ���� Pool
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performJindanQuestionPoolPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestionPool_I.jsp";

            JindanQuestionBean bean = new JindanQuestionBean();
            ArrayList<DataBox> list1 = bean.selectQuestionPool(box);
            request.setAttribute("ExamQuestionPool", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionPoolPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����׽�Ʈ ���� Pool �������� �̵� (�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performQuestionPoolListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestionPool_I.jsp";

            JindanQuestionBean bean = new JindanQuestionBean();

            ArrayList<DataBox> list = bean.selectQuestionPoolList(box);
            request.setAttribute("ExamQuestionPool", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performQuestionPoolListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����׽�Ʈ ���� �̸����� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performJindanQuestionPreview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanQuestionPreview.jsp";
            if (box.getString("p_examtype").equals("2"))
                v_return_url = "/learn/admin/jindan/za_JindanQuestionPreview2.jsp";

            JindanQuestionBean bean = new JindanQuestionBean();
            ArrayList<DataBox> list1 = bean.selectExampleData(box);
            request.setAttribute("QuestionExampleData", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanQuestionPreview()\r\n" + ex.getMessage());
        }
    }
}