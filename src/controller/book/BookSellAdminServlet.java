//1. ��      ��: �����Ǹ������� �����ϴ� ����
//2. ���α׷��� : BookAdminServlet.java
//3. ��      ��: �����Ǹ����� �������� �����Ѵ�
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

import com.credu.book.BookSellBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.book.BookSellAdminServlet")
public class BookSellAdminServlet extends javax.servlet.http.HttpServlet {

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
//			    String path = request.getServletPath();
			    out       = response.getWriter();
			    box       = RequestManager.getBox(request);
			    v_process = box.getStringDefault("p_process","listPage");

			    if(ErrorManager.isErrorMessageView()) box.put("errorout", out);
				
				///////////////////////////////////////////////////////////////////
				if (!AdminUtil.getInstance().checkRWRight("BookSellAdminServlet", v_process, out, box)) {
					return; 
				}
	            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
				///////////////////////////////////////////////////////////////////

			    if(v_process.equals("listPage")) {                       // ����Ʈ �������� �̵��Ҷ� (�ǸŸ���Ʈ)
			      this.performListPage(request, response, box, out);
			    } else if(v_process.equals("update")){ 		             // ���� (���� ����)
					this.performUpdate(request, response, box, out);
			    } else if(v_process.equals("cancelPage")){ 		         // ����Ʈ �������� �̵��Ҷ� (��Ҹ���Ʈ)
					this.performCancelPage(request, response, box, out);
			    } else if(v_process.equals("updateCancel")){ 		             // ���� (���� ����)
					this.performUpdateCancel(request, response, box, out);
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
				
				BookSellBean bean = new BookSellBean();
	            ArrayList list = bean.selectBookSellList(box);
	            request.setAttribute("selectBookSellList", list);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/book/za_BookSell_L.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/admin/book/za_BookSell_L.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
				 BookSellBean bean = new BookSellBean();
	            int isOk = bean.updateBookSell(box);

	            String v_msg = "";
	            String v_url  = "/servlet/controller.book.BookSellAdminServlet";
	            box.put("p_process", "listPage");

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
	    ����Ʈ �������� �̵��Ҷ� (��Ҹ���Ʈ)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performCancelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);
				
				BookSellBean bean = new BookSellBean();
	            ArrayList list = bean.selectCancelList(box);
	            request.setAttribute("selectCancelList", list);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/book/za_BookCancel_L.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/admin/book/za_BookCancel_L.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
	    public void performUpdateCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	         try {
				 BookSellBean bean = new BookSellBean();
	            int isOk = bean.updateBookCancel(box);

	            String v_msg = "";
	            String v_url  = "/servlet/controller.book.BookSellAdminServlet";
	            box.put("p_process", "cancelPage");

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
}
