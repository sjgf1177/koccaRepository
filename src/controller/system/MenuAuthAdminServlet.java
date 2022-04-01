//**********************************************************
//  1. ��      ��: �޴����Ѽ��� �����ϴ� ����
//  2. ���α׷��� : MenuAuthAdminServlet.java
//  3. ��      ��: �޴����Ѽ��� ���� ���α׷�
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11. 8
//  7. ��      ��:
//**********************************************************
package controller.system;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.MenuAuthAdminBean;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.system.MenuAuthAdminServlet")
public class MenuAuthAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("update")) {                         // �޴����Ѽ��� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("updatePage")) {              // �޴����� ����Ʈ
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    �޴����� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            MenuAuthAdminBean bean = new MenuAuthAdminBean();

            // ���� ����
            int gadmincnt   = bean.selectCountGadmin(box);
            box.put("p_gadmincnt", String.valueOf(gadmincnt));

            // ���� ����Ʈ
            ArrayList List1 = bean.selectListGadmin(box);
            request.setAttribute("selectList1", List1);

            // �޴� ���� ����Ʈ
            ArrayList List2 = bean.selectListMenuAuth(box);
            request.setAttribute("selectList2", List2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuAuth_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }


    /**
    // �޴����Ѽ����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {

            MenuAuthAdminBean bean = new MenuAuthAdminBean();

            int isOk = bean.updateMenuAuth(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.MenuAuthAdminServlet";
            box.put("p_process", "updatePage");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on MenuAuthAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }


}

