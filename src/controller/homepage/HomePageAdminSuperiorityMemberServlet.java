//**********************************************************
//1. ��      ��: Ȩ������ ���� ���ȸ��������  �����ϴ� ����
//2. ���α׷��� : HomePageSuperiortyMemberServlet
//3. ��      ��: Ȩ������ ���� ���ȸ���� �����Ѵ�.
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2005.12.13 �ϰ���
//7. ��      ��: 2005.12.13 �ϰ���
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomePageSuperiorityMemberBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet")
public class HomePageAdminSuperiorityMemberServlet extends javax.servlet.http.HttpServlet {

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            // String path = request.getServletPath();

            //box = BulletinManager.getState("HomePageSuperiorityMember", box, out);

            v_process = box.getStringDefault("p_process", "selectList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

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
            } else if (v_process.equals("selectMain")) { //  �Խÿ��� ���� �Ҷ�
                this.performMainSelect(request, response, box, out);
            } else { //  ��ȸ�Ҷ�
                this.performSelectList1(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
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
    public void performSelectList1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            HomePageSuperiorityMemberBean bean1 = new HomePageSuperiorityMemberBean();

            ArrayList<DataBox> list = bean1.selectBoardList(box);
            request.setAttribute("selectHomePageSuperiorityMemberList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageSuperiorityMember_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_HomePageSuperiorityMember_L.jsp");

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

            HomePageSuperiorityMemberBean bean = new HomePageSuperiorityMemberBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectHomePageSuperiorityMember", dbox);

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("selectHomePageSuperiortyMemberList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageSuperiorityMember_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_HomePageSuperiorityMember_R.jsp");

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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageSuperiorityMember_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_HomePageSuperiorityMember_I.jsp");

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
            HomePageSuperiorityMemberBean bean = new HomePageSuperiorityMemberBean();

            int isOk = bean.insertBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageAdminSuperiorityMemberServlet");
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

            HomePageSuperiorityMemberBean bean = new HomePageSuperiorityMemberBean();

            DataBox dbox = bean.selectBoard(box);

            request.setAttribute("selectHomePageBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageSuperiorityMember_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_HomePageSuperiorityMember_U.jsp");

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
            HomePageSuperiorityMemberBean bean = new HomePageSuperiorityMemberBean();

            int isOk = bean.updateBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet";
            box.put("p_process", "");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageAdminSuperiorityMemberServlet");
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
            HomePageSuperiorityMemberBean bean = new HomePageSuperiorityMemberBean();

            int isOk = bean.deleteBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageAdminSuperiorityMemberServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� �Խÿ��� �Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMainSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageSuperiorityMemberBean bean = new HomePageSuperiorityMemberBean();

            int isOk = bean.MainSelect(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "�����Ͽ����ϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "�������";
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
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            //  Log.sys.println();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}
