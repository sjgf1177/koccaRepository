//**********************************************************
//  1. ��      ��: ���İ���
//  2. ���α׷���: KnowBoardServlet.java
//  3. ��      ��: ���İ��� �Խ��� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 9. 1
//  7. ��     ��1:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.KnowBoardAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.KnowBoardServlet")
public class KnowBoardServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
		String v_type    = "";
        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            v_type = box.getString("p_type");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("KnowBoardServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
			
            if(v_process.equals("KonwCodeList")){                      //��� �з��ڵ� ����Ʈ
                this.performKonwCodeList(request, response, box, out);
            } else if (v_process.equals("insertPage")) {        //in case of �з��ڵ� ��� ȭ��
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) {            //in case of �з��ڵ� ���
                this.performInsert(request, response, box, out);                
            } else if (v_process.equals("updatePage")) {        //in case of �з��ڵ� ���� ȭ��
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) {            //in case of �з��ڵ� ����
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("delete")) {            //in case of �з��ڵ� ����
                this.performDelete(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

   /**
    ��� �з��ڵ� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
*/
    public void performKonwCodeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            KnowBoardAdminBean bean = new KnowBoardAdminBean();
            ArrayList list = bean.SelectKonwCodeList(box);
            request.setAttribute("KonwCodeList", list);
 
		    ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_KnowCode_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performKonwCodeList()\r\n" + ex.getMessage());
        }
    }

    /**
    �з� ��� ȭ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/homepage/za_KnowCode_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �з� ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.homepage.KnowBoardServlet";

            KnowBoardAdminBean bean = new KnowBoardAdminBean();
            int isOk = bean.InsertSalesCode(box);
            String v_msg = "";
            box.put("p_process", "insertPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
				box.put("p_end", "0");
				box.put("p_reloadlist", "true");				
				                
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }    

    /**
    �з� ���� ȭ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/homepage/za_KnowCode_U.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    �з� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.homepage.KnowBoardServlet";

            KnowBoardAdminBean bean = new KnowBoardAdminBean();
            int isOk = bean.UpdateSalesCode(box);

            String v_msg = "";
            box.put("p_process", "updatePage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";

				box.put("p_end", "0");
				box.put("p_reloadlist", "true");				
				                
                //alert.alertOkMessage(out, v_msg, v_url , box, true, true);
                alert.alertOkMessage(out, v_msg, v_url , box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    �з� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.homepage.KnowBoardServlet";

            KnowBoardAdminBean bean = new KnowBoardAdminBean();
            int isOk = bean.DeleteSalesCode(box);

            String v_msg = "";
            box.put("p_process", "updatePage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";

				box.put("p_end", "0");
				box.put("p_reloadlist", "true");				
				                
                //alert.alertOkMessage(out, v_msg, v_url , box, true, true);
                alert.alertOkMessage(out, v_msg, v_url , box);
                
            } else if (isOk == -1) {
                v_msg = "�ߺз��� �־� ������ �� �����ϴ�. �ߺз� ���� �� �ٽ� �õ��ϼ���.";
                alert.alertFailMessage(out, v_msg);
            } else if (isOk == -2) {
                v_msg = "�Һз��� �־� ������ �� �����ϴ�. �Һз� ���� �� �ٽ� �õ��ϼ���.";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }    
}

