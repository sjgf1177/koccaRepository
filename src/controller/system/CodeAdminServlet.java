//**********************************************************
//  1. ��      ��: �ڵ���� �����ϴ� ����
//  2. ���α׷��� : CodeAdminServlet.java
//  3. ��      ��: �ڵ���� ���� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 2
//  7. ��     ��1:
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
import com.credu.system.AdminUtil;
import com.credu.system.CodeAdminBean;
import com.credu.system.CodeData;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.system.CodeAdminServlet")
public class CodeAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("CodeAdminServlet", v_process, out, box)) {
				return; 
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
            if(v_process.equals("selectView"))  {                       // ��з��ڵ� �󼼺���� �̵��Ҷ�
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("insertPage")) {                 // ��з��ڵ� ����������� �̵��Ҷ�
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                     // ��з��ڵ� ����Ҷ�
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("updatePage")) {                 // ��з��ڵ� ������������ �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                     // ��з��ڵ� �����Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                     // ��з��ڵ� �����Ҷ�
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("selectSubView")){               // �Һз��ڵ� �󼼺���� �̵��Ҷ�
                this.performSelectSubView(request, response, box, out);
            } else if(v_process.equals("insertSubPage")) {              // �Һз��ڵ� ����������� �̵��Ҷ�
                this.performInsertSubPage(request, response, box, out);
            } else if(v_process.equals("insertSub")) {                  // �Һз��ڵ� ����Ҷ�
                this.performInsertSub(request, response, box, out);
            } else if(v_process.equals("updateSubPage")) {              // �Һз��ڵ� ������������ �̵��Ҷ�
                this.performUpdateSubPage(request, response, box, out);
            } else if(v_process.equals("updateSub")) {                  // �Һз��ڵ� �����Ͽ� �����Ҷ�
                this.performUpdateSub(request, response, box, out);
            } else if(v_process.equals("deleteSub")) {                  // �Һз��ڵ� �����Ҷ�
                this.performDeleteSub(request, response, box, out);
            } else if(v_process.equals("selectSubList")) {              // �Һз��ڵ� ����Ʈ
                this.performSelectSubList(request, response, box, out);
            } else if(v_process.equals("select")) {                     // ��з��ڵ� ����Ʈ
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
 
    /**
    ��з��ڵ� �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            CodeAdminBean bean = new CodeAdminBean();
            CodeData data = bean.selectViewCode(box);

            request.setAttribute("selectCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_R.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Code_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

     /**
    ��з��ڵ� ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            /*-------- �ϴ� ����Ʈ ��� ���� -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectListCode(box);

            request.setAttribute("selectList", list);
            /*-------- �ϴ� ����Ʈ ��� ��   -------------*/

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_I.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Code_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
    ��з��ڵ� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.insertCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

     /**
    ��з��ڵ� ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            /*-------- �ϴ� ����Ʈ ��� ���� -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectListCode(box);

            request.setAttribute("selectList", list);
            /*-------- �ϴ� ����Ʈ ��� ��   -------------*/

            CodeData data = bean.selectViewCode(box);

            request.setAttribute("selectCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    ��з��ڵ� �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.updateCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "select");
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

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    ��з��ڵ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.deleteCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    �Һз��ڵ� �󼼺���� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectSubView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            CodeAdminBean bean = new CodeAdminBean();
            CodeData data = bean.selectSubViewCode(box);

            request.setAttribute("selectSubCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_R.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSubView()\r\n" + ex.getMessage());
        }
    }

     /**
    �Һз��ڵ� ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertSubPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            /*-------- �ϴ� ����Ʈ ��� ���� -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList List = bean.selectSubListCode(box);

            request.setAttribute("selectSubList", List);
            /*-------- �ϴ� ����Ʈ ��� ���� -------------*/

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_I.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertSubPage()\r\n" + ex.getMessage());
        }
    }

     /**
    �Һз��ڵ� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.insertSubCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "selectSubList");

            AlertManager alert = new AlertManager();

            if(isOk == 3){
            	v_msg = "insert.duplication";
                alert.alertFailMessage(out, v_msg);
            }
            else if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertSub()\r\n" + ex.getMessage());
        }
    }


     /**
    �Һз��ڵ� ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateSubPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            /*-------- �ϴ� ����Ʈ ��� ���� -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectSubListCode(box);

            request.setAttribute("selectSubList", list);
            /*-------- �ϴ� ����Ʈ ��� ��   -------------*/

            CodeData data = bean.selectSubViewCode(box);

            request.setAttribute("selectSubCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_U.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_U.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateSubPage()\r\n" + ex.getMessage());
        }
    }

     /**
    // �Һз��ڵ� �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.updateSubCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "selectSubList");
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

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateSub()\r\n" + ex.getMessage());
        }
    }

     /**
    // �Һз��ڵ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDeleteSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.deleteSubCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "selectSubList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteSub()\r\n" + ex.getMessage());
        }
    }

    /**
    �Һз��ڵ� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectSubList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            CodeAdminBean bean = new CodeAdminBean();
            ArrayList List = bean.selectSubListCode(box);

            request.setAttribute("selectSubList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_L.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSubList()\r\n" + ex.getMessage());
        }
    }

    /**
    ��з��ڵ� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectListCode(box);

            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_L.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Code_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}

