//1. ��      ��: ���������� �����ϴ� ����
//2. ���α׷��� : BookAdminServlet.java
//3. ��      ��: �������� �������� �����Ѵ�
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2006.2.9 ������
//7. ��      ��: 
//**********************************************************

package controller.book;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.book.BookBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.book.BookAdminServlet")
public class BookAdminServlet extends javax.servlet.http.HttpServlet {
    /**
	    * DoGet
	    * Pass get requests through to PerformTask
	    */
	    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
	        this.doPost(request, response);
	    }
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
			PrintWriter      out          = null;
			RequestBox       box          = null;
			String           v_process    = "";

	        try {
			    response.setContentType("text/html;charset=euc-kr");
			    //String path = request.getServletPath();
			    out       = response.getWriter();
			    box       = RequestManager.getBox(request);
			    v_process = box.getStringDefault("p_process","selectList");

			    if(ErrorManager.isErrorMessageView()) box.put("errorout", out);
				
				///////////////////////////////////////////////////////////////////
				if (!AdminUtil.getInstance().checkRWRight("BookAdminServlet", v_process, out, box)) {
					return; 
				}
	            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
				///////////////////////////////////////////////////////////////////

			    if(v_process.equals("selectList")) {                       // ����Ʈ �������� �̵��Ҷ�
			      this.performListPage(request, response, box, out);
			    } else if(v_process.equals("selectView")) {              // �󼼺��� 
			      this.performSelectPage(request, response, box, out);
			    } else if(v_process.equals("insertPage")){ 		         // ��� �������� �̵��Ҷ�
					this.performInsertPage(request, response, box, out);
			    } else if(v_process.equals("insert")){ 		             // ���
					this.performInsert(request, response, box, out);
			    }else if(v_process.equals("updatePage")){ 		         // ���� �������� �̵��Ҷ�
					this.performUpdatePage(request, response, box, out);
			    } else if(v_process.equals("update")){ 		             // ����
					this.performUpdate(request, response, box, out);
			    } else if(v_process.equals("delete")){ 		             // ����
					this.performDelete(request, response, box, out);
			    } 
	        }catch(Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	        }
	    }

	    /**
	    ����Ʈ
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);
				
				BookBean bean = new BookBean();
	            ArrayList list = bean.selectBookList(box);
	            request.setAttribute("selectBookList", list);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/book/za_Book_L.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/admin/book/za_Book_L.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performSelectList()\r\n" + ex.getMessage());
	        }
	    }

		   /**
	    �󼼺���
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performSelectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

				BookBean bean = new BookBean();
	            DataBox dbox = bean.selectBook(box);
	            request.setAttribute("selectBook", dbox);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/book/za_Book_R.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/admin/book/za_Book_R.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performSelectPage()\r\n" + ex.getMessage());
	        }
	    }
		
	    /**
	    ����������� �̵��Ҷ�
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	        try {
	            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/book/za_Book_I.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/admin/book/za_Book_I.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	        }
	    }

	     /**
	    ����Ҷ�
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	        try {
				BookBean bean = new BookBean();
	            int isOk = bean.insertBook(box);

	            String v_msg = "";
	            String v_url = "/servlet/controller.book.BookAdminServlet";
	            box.put("p_process", "selectList");

	            AlertManager alert = new AlertManager();

	            if(isOk > 0) {
					v_msg = "insert.ok";
	                alert.alertOkMessage(out, v_msg, v_url , box);
	            }
	            else {
	                v_msg = "insert.fail";
	                alert.alertFailMessage(out, v_msg);
	            }

	            Log.info.println(this, box, v_msg + " on BookAdminServlet");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performInsert()\r\n" + ex.getMessage());
	        }
	    }

	     /**
	    ������������ �̵��Ҷ�
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

				BookBean bean = new BookBean();
	            DataBox dbox = bean.selectBook(box);
	            request.setAttribute("selectBook", dbox);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/book/za_Book_U.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/admin/book/za_Book_U.jsp");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
	        }
	    }

	     /**
	    �����Ͽ� �����Ҷ�
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	         try {
				BookBean bean = new BookBean();
	            int isOk = bean.updateBook(box);

	            String v_msg = "";
	            String v_url  = "/servlet/controller.book.BookAdminServlet";
	            box.put("p_process", "selectList");

	            AlertManager alert = new AlertManager();
	            if(isOk > 0) {
	                v_msg = "update.ok";
	                alert.alertOkMessage(out, v_msg, v_url , box);
	            } else {
	                v_msg = "update.fail";
	                alert.alertFailMessage(out, v_msg);
	            }
	            Log.info.println(this, box, v_msg + " on BookAdminServlet");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performUpdate()\r\n" + ex.getMessage());
	        }
	    }

	     /**
	    �����Ҷ�
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
				BookBean bean = new BookBean();
	            int isOk = bean.deleteBook(box);

	            String v_msg = "";
	            String v_url  = "/servlet/controller.book.BookAdminServlet";
	            box.put("p_process", "selectList");

	            AlertManager alert = new AlertManager();
	            if(isOk > 0) {
	                v_msg = "delete.ok";
	                alert.alertOkMessage(out, v_msg, v_url , box);
	            } else {
	                v_msg = "delete.fail";
	                alert.alertFailMessage(out, v_msg);
	            }

	            Log.info.println(this, box, v_msg + " on BookAdminServlet");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performDelete()\r\n" + ex.getMessage());
	        }
	    }
		

}
