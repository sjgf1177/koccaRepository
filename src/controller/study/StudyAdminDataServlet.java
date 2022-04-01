//**********************************************************

//  1. ��      ��: �����ڷ���� �����ϴ� ����
//  2. ���α׷���: StudyAdminDataServlet.java
//  3. ��      ��: �����ڷ���� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2004.01.30
//  7. ��      ��:
//**********************************************************

package controller.study;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.BoardBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.StudyAdminDataBean;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.study.StudyAdminDataServlet")
public class StudyAdminDataServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -783901674375501111L;

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
        // int fileupstatus = 0;
        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canModify = false;
        boolean v_canDelete = false;
        boolean v_canReply = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            //String path = request.getServletPath();
            String path = request.getRequestURI();

            box = BulletinManager.getState(path.substring(path.lastIndexOf(".") + 1, path.lastIndexOf("Servlet")), box, out);

            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            ///////////////////////////////////////////////////////////////////
            if (!AdminUtil.getInstance().checkRWRight("StudyAdminDataServlet", v_process, out, box)) {
                return;
            }
            ///////////////////////////////////////////////////////////////////
            v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
            v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
            v_canModify = BulletinManager.isAuthority(box, box.getString("p_canModify"));
            v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
            v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
            v_canReply = BulletinManager.isAuthority(box, box.getString("p_canReply"));

            if (v_process.equals("insertPage")) { //  ����������� �̵��Ҷ�
                if (v_canAppend)
                    this.performInsertPage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("insert")) { //  ����Ҷ�
                if (v_canAppend)
                    this.performInsert(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("updatePage")) { //  ������������ �̵��Ҷ�
                if (v_canModify)
                    this.performUpdatePage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("update")) { //  �����Ͽ� �����Ҷ�
                if (v_canModify)
                    this.performUpdate(request, response, box, out);
                else
                    this.errorPage(box, out);
            }
            if (v_process.equals("replyPage")) { //  �亯�������� �̵��Ҷ�
                if (v_canReply)
                    this.performReplyPage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("reply")) { //  �亯�Ҷ�
                if (v_canReply)
                    this.performReply(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("delete")) { //  �����Ҷ�
                if (v_canDelete)
                    this.performDelete(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("select")) { //  �󼼺����Ҷ�
                if (v_canRead)
                    this.performSelect(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("list")) { //  ��ȸ�Ҷ�
                if (v_canRead)
                    this.performSelectList(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("adminList")) { //in case of admin databoard list
                if (v_canRead)
                    this.performSelectAdminList(request, response, box, out);
                else
                    this.errorPage(box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * DATABOARD ADMIN
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectAdminList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            StudyAdminDataBean bean = new StudyAdminDataBean();
            ArrayList<DataBox> list1 = bean.selectAdminList(box);

            request.setAttribute("selectAdminList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudyDataAdmin_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("selectAdminList()\r\n" + ex.getMessage());
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

            // StudyAdminDataBean bean = new StudyAdminDataBean();
            BoardBean bean1 = new BoardBean();

            //ArrayList list = bean.selectDocList(box);
            ArrayList<DataBox> list = bean1.selectBoardList(box);
            request.setAttribute("selectStudyDataList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudyData_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
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

            //            StudyAdminDataBean bean = new StudyAdminDataBean();
            BoardBean bean = new BoardBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectStudyData", dbox);

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("selectStudyDataList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudyData_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
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
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudyData_I.jsp");
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
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            //StudyAdminDataBean bean = new StudyAdminDataBean();
            //int isOk = bean.insertBoard(box);

            StudyAdminDataBean bean = new StudyAdminDataBean();
            int isOk = bean.insertBoard(box);
            //BoardBean bean = new BoardBean();
            //int isOk = bean.insertBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyAdminDataServlet";
            box.put("p_process", "list");

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
     * ������������ �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            //            StudyAdminDataBean bean = new StudyAdminDataBean();
            BoardBean bean = new BoardBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectStudyData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudyData_U.jsp");
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
            StudyAdminDataBean bean = new StudyAdminDataBean();
            //BoardBean bean = new BoardBean();

            int isOk = bean.updateBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyAdminDataServlet";
            box.put("p_process", "list");
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
     * �亯�������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            //            StudyAdminDataBean bean = new StudyAdminDataBean();
            BoardBean bean = new BoardBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectStudyData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudyData_A.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �亯�Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            //           StudyAdminDataBean bean = new StudyAdminDataBean();
            BoardBean bean = new BoardBean();

            int isOk = bean.replyBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyAdminDataServlet";
            box.put("p_process", "list");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "reply.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "reply.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReply()\r\n" + ex.getMessage());
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
            //            StudyAdminDataBean bean = new StudyAdminDataBean();
            BoardBean bean = new BoardBean();

            int isOk = bean.deleteBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyAdminDataServlet";
            box.put("p_process", "list");

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

    @SuppressWarnings("unchecked")
    public void errorPage(RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_process", "list");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            //  Log.sys.println();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}
