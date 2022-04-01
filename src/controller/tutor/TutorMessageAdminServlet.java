//1. ��      ��: �����޼��� ����
//2. ���α׷��� : MemberSearchServlet.java
//3. ��      ��: �����޼��� ����
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: ������ 2004. 12. 20
//7. ��      ��:
//**********************************************************

package controller.tutor;

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
import com.credu.system.AdminUtil;
import com.credu.tutor.TutorData;
import com.credu.tutor.TutorIntroduceBean;
import com.credu.tutor.TutorMessageAdminBean;

@WebServlet("/servlet/controller.tutor.TutorMessageAdminServlet")
public class TutorMessageAdminServlet extends HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4592534813352467132L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            // String path = request.getRequestURI();

            if (!v_process.equals("introduceList")) {
                if (!AdminUtil.getInstance().checkRWRight("TutorMessageAdminServlet", v_process, out, box)) {
                    return;
                }
            }

            if (v_process.equals("insertPage")) { //  ����������� �̵��Ҷ�
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { //  ����Ҷ�
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) { //  ������������ �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { //  �����Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("delete")) { //  �����Ҷ�
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("select")) { //  �󼼺����Ҷ�
                this.performSelect(request, response, box, out);
            } else if (v_process.equals("listPage")) { //  ��ȸ�Ҷ�
                this.performSelectList(request, response, box, out);
            }
            //å�� ���� �˾� â �׽�Ʈ �κ�-----------------------------------------
            else if (v_process.equals("introduceList")) { //  ��ȸ�Ҷ�
                this.performIntroduceList(request, response, box, out);
            }
            //------------------------------------------------------------------
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    //�׽�Ʈ �ڵ� �� ------------
    public void performIntroduceList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�            
            //            TutorAdminBean bean = new TutorAdminBean();

            TutorIntroduceBean introduceBean = new TutorIntroduceBean();

            ArrayList<DataBox> list1 = introduceBean.selectMessageList(box);
            DataBox dbox = introduceBean.selectTutor(box);
            ArrayList<TutorData> list = introduceBean.selectTutorSubjList(box);

            request.setAttribute("selectMessageList", list1);

            request.setAttribute("tutorSelect", dbox);
            request.setAttribute("tutorSubjList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/ku_TutorIntroduce_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }


    /**
     * ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�.

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @eturn void
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            TutorMessageAdminBean message = new TutorMessageAdminBean();

            int isOk = message.insertMessage(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorMessageAdminServlet";
            box.put("p_process", "listPage");

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
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            TutorMessageAdminBean message = new TutorMessageAdminBean();

            DataBox dbox = message.selectMessage(box);
            request.setAttribute("selectMessage", dbox);

            ArrayList<DataBox> list = message.selectMessageList(box);
            request.setAttribute("selectMessageList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������������ �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        // System.out.println("---- Message Update Page Start ----------");
        try {

            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�
            TutorMessageAdminBean message = new TutorMessageAdminBean();

            DataBox dbox = message.selectMessage(box);
            request.setAttribute("selectMessage", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            TutorMessageAdminBean message = new TutorMessageAdminBean();

            int isOk = message.updateMessage(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorMessageAdminServlet";
            box.put("p_process", "listPage");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            TutorMessageAdminBean message = new TutorMessageAdminBean();

            int isOk = message.deleteMessage(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorMessageAdminServlet";
            box.put("p_process", "listPage");

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
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            TutorMessageAdminBean message = new TutorMessageAdminBean();

            ArrayList<DataBox> list = message.selectMessageList(box);
            request.setAttribute("selectMessageList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMessageAdmin_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void errorPage(RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_process", "");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }

}
