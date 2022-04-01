//*********************************************************
//1. ��      ��: ������ �Ա� ����
//2. ���α׷���: AccountAdminServlet.java
//3. ��      ��: �������� servlet
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: �ϰ��� 2006.01.16
//7. ��      ��:
//**********************************************************
package controller.polity;

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
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.polity.AccountAdminBean;

@WebServlet("/servlet/controller.polity.AccountAdminServlet")
//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
public class AccountAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1076563469048122637L;

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

            // String path = request.getServletPath();
            v_process = box.getStringDefault("p_process", "selectList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("selectList")) { //  ��ȸȭ��
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("ExcelDown")) { //  ���� ����
                this.performExcelList(request, response, box, out);
            } else if (v_process.equals("AccountAdminInsert")) { //  ȯ�� ó��
                this.performAccountAdminInsert(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����Ʈ(������)
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

            AccountAdminBean bean = new AccountAdminBean();

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_AccountAdmin_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_AccountAdmin_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * Excel �ٿ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // String v_return_url = "/learn/admin/polity/za_AccountAdmin_E.jsp";
            AccountAdminBean bean = new AccountAdminBean();

            if (box.getString("p_action").equals("go")) {
                ArrayList<DataBox> list = bean.selectExcelBoardList(box);
                request.setAttribute("selectList", list);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_AccountAdmin_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * ȯ�� ��û ó��
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performAccountAdminInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.polity.AccountAdminServlet";
            String v_msg = "";

            AccountAdminBean bean = new AccountAdminBean();
            int isOk = bean.AccountAdminInsert(box);
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "confirm.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "confirm.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performApproval()\r\n" + ex.getMessage());
        }
    }
}