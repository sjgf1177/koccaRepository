//**********************************************************
//  1. ��      ��:  �����ϴ� ����
//  2. ���α׷��� : ____Servlet.java
//  3. ��      ��:  ���� ���α׷�
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: __����__ 2009. 10. 19
//  7. ��     ��1:
//**********************************************************
package controller.off;

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
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffSchRegBean;
import com.credu.off.OffStudentJobBean;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffStudentJobAdminServlet")
public class OffStudentJobAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
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

            if (!AdminUtil.getInstance().checkRWRight("OffStudentJobAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("selectList")) { //  ����Ʈ
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("selectListPerPerson")) { //  ���κ� ����Ʈ(�˾�)
                this.performSelectListPerPerson(request, response, box, out);
            } else if (v_process.equals("insertPage")) { //  ����������� �̵��Ҷ�(�˾�)
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("updatePage")) { //  ������������ �̵��Ҷ�(�˾�)
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("insert")) { //  ����Ͽ� �����Ҷ�
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("update")) { //  �����Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("excelDown")) { //  �����ٿ�
                this.performExcelDown(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
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

            OffStudentJobBean bean = new OffStudentJobBean();
            ArrayList selectListPerPerson = bean.selectListPerPerson(box);
            request.setAttribute("selectListPerPerson", selectListPerPerson);

            DataBox selectView = bean.selectView(box);
            request.setAttribute("selectView2", selectView);

            OffSchRegBean beans = new OffSchRegBean();
            DataBox dbox = beans.selectView(box);
            request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_StudentJob_Person_U.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/off/za_off_SchReg_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
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

            OffStudentJobBean bean = new OffStudentJobBean();
            ArrayList selectListPerPerson = bean.selectListPerPerson(box);
            request.setAttribute("selectListPerPerson", selectListPerPerson);

            OffSchRegBean beans = new OffSchRegBean();
            DataBox dbox = beans.selectView(box);
            request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_StudentJob_Person_I.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/off/za_off_SchReg_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * // �����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffStudentJobBean bean = new OffStudentJobBean();

            int isOk = bean.update(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffStudentJobAdminServlet";
            //box.put("p_process", "selectList");
            box.put("p_process", "selectListPerPerson");

            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                //alert.alertOkMessage(out, v_msg, v_url , box, true, true, false);
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on OffSchRegAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * // ����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffStudentJobBean bean = new OffStudentJobBean();

            int isOk = bean.insert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffStudentJobAdminServlet";
            box.put("p_process", "selectListPerPerson");
            //box.put("p_process", "selectList");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                ///alert.alertOkMessage(out, v_msg, v_url , box, true, true, false);
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on OffSchRegAdminServlet");
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
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�

            OffStudentJobBean bean = new OffStudentJobBean();

            ArrayList selectList = bean.selectList(box);
            request.setAttribute("selectList", selectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_StudentJob_L.jsp");
            rd.forward(request, response);

            // Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ(�˾�)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectListPerPerson(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�

            OffStudentJobBean bean = new OffStudentJobBean();
            ArrayList selectListPerPerson = bean.selectListPerPerson(box);
            request.setAttribute("selectListPerPerson", selectListPerPerson);

            OffSchRegBean beans = new OffSchRegBean();
            DataBox dbox = beans.selectView(box);
            request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_StudentJob_Person_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����ٿ�ε�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�

            OffStudentJobBean bean = new OffStudentJobBean();

            ArrayList selectList = bean.selectList(box);
            request.setAttribute("selectList", selectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_StudentJob_E.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}
