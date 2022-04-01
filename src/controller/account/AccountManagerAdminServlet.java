//*********************************************************
//1. ��      ��: �������� 
//2. ���α׷���: AccountManagerAdminServlet.java
//3. ��      ��: ��������
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: ���� 2006.08.24
//7. ��      ��:
//**********************************************************
package controller.account;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@WebServlet("/servlet/controller.library.AccountManagerAdminServlet")
public class AccountManagerAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3023185572734416624L;

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

            v_process = box.getStringDefault("p_process", "selectList");

            System.out.println("p_process = " + v_process);
            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /*
             * if(v_process.equals("selectList")) { // ��ȸȭ��
             * this.performSelectList(request, response, box, out); }else
             * if(v_process.equals("ExcelDown")) { // ���� ����
             * this.performExcelList(request, response, box, out); }else
             * if(v_process.equals("AccountAdminInsert")) { // ȯ�� ó��
             * this.performAccountAdminInsert(request, response, box, out); }
             */

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

}