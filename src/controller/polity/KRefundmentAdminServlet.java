//*********************************************************
//1. ��      ��: ��������
//2. ���α׷���: KRefundmentAdminServlet.java
//3. ��      ��: ȯ��ó��  servlet
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: �ϰ��� 2006.01.10
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
import com.credu.polity.RefundmentAdminBean;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.polity.KRefundmentAdminServlet")
public class KRefundmentAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            } else if (v_process.equals("RefundmentInsertPage")) { //  ȯ�� ��û ������
                this.performRefundmentInsertPage(request, response, box, out);
            } else if (v_process.equals("RefundmentInsert")) { //  ȯ�� ��û ������
                this.performRefundmentInsert(request, response, box, out);
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
    @SuppressWarnings("unchecked")
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            RefundmentAdminBean bean = new RefundmentAdminBean();

            ArrayList list = bean.selectBoardList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_RefundmentAdmin_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_RefundmentAdmin_L.jsp");

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
    @SuppressWarnings("unchecked")
    public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // String v_return_url = "/learn/admin/polity/za_PolityAdmin_E.jsp";
            RefundmentAdminBean bean = new RefundmentAdminBean();

            if (box.getString("p_action").equals("go")) {
                ArrayList list = bean.selectExcelBoardList(box);
                request.setAttribute("selectList", list);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_RefundmentAdmin_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
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
    public void performRefundmentInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);

            RefundmentAdminBean bean = new RefundmentAdminBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("select", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_Refundment_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/course/ku_Refundment_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
    public void performRefundmentInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.study.KMyClassServlet";
            String v_msg = "";

            RefundmentAdminBean bean = new RefundmentAdminBean();
            int isOk = bean.RefundmentInsert(box);
            box.put("p_process", "ProposeCancelPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "confirm.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
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