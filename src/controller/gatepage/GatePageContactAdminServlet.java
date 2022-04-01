//**********************************************************
//  1. ��      ��: ��ڿ��� �����ϴ� ����
//  2. ���α׷��� : ContactAdminServlet.java
//  3. ��      ��: ��ڿ��� ���� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 2
//  7. ��     ��1:
//**********************************************************
package controller.gatepage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.gatepage.GatePageContactAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.gatepage.GatePageContactAdminServlet")
public class GatePageContactAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			if(v_process.equals("")){
				box.put("v_process","selectList");
				v_process = "selectList";
			}
			
			System.out.println("v_process : " + v_process);

			///////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("GatePageContactAdminServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			////////////////////////////////////////////////////////////////


            if(v_process.equals("answer")) {                     // ��ڿ��� �亯�������� �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("reply")) {                  // ��ڿ��� �亯�Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                  // ��ڿ��� �����Ҷ�
                this.performDelete(request, response, box, out);
            } else  if(v_process.equals("selectList")) {             // ��ڿ��� ����Ʈ
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


     /**
    ��ڿ��� �亯�������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            GatePageContactAdminBean bean = new GatePageContactAdminBean();

            DataBox dbox = bean.selectViewContact(box);
            request.setAttribute("selectContact", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatepageSystemAdmin_A.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/gatepage/za_gatesysadmin_a.jsp.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    // ��ڿ��� �亯�Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            GatePageContactAdminBean bean = new GatePageContactAdminBean();

            /* ======  ���Ϲ߼� ��� ���� ===================*/
			int isOk1 = bean.sendFormMail(box);
            /* =============================================*/

			int isOk2 = bean.updateContact(box);            
            String v_msg = "";
            String v_url = "/servlet/controller.gatepage.GatePageContactAdminServlet";
            box.put("p_process", "selectList");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if((isOk1 + isOk2) > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on gatepage.GatePageContactAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    // ��ڿ��� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GatePageContactAdminBean bean = new GatePageContactAdminBean();
            int isOk = bean.deleteContact(box);

            String v_msg = "";
            String v_url = "/servlet/controller.gatepage.GatePageContactAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on gatepage.GatePageContactAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    ��ڿ��� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            GatePageContactAdminBean bean = new GatePageContactAdminBean();
            ArrayList List = bean.selectListContact(box);

            request.setAttribute("selectList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatepageSystemAdmin_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/gatepage/za_gatesysadmin_l.jsp.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}

