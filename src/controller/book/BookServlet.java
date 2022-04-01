//1. ��      ��: ���������� �����ϴ� ����
//2. ���α׷��� : BookServlet.java
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
import com.credu.book.BookSellBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.book.BookServlet")
public class BookServlet extends javax.servlet.http.HttpServlet {
	
        /**
	    * DoGet
	    * Pass get requests through to PerformTask
	    */
	    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
	        this.doPost(request, response);
	    }
	    @SuppressWarnings("unchecked")
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
			PrintWriter      out          = null;
//			MultipartRequest multi        = null;
			RequestBox       box          = null;
			String           v_process    = "";
//			int              fileupstatus = 0;

	        try {
			    response.setContentType("text/html;charset=euc-kr");
//			    String path = request.getServletPath();
			    out       = response.getWriter();
			    box       = RequestManager.getBox(request);
			    v_process = box.getStringDefault("p_process","listPage");

			    if(ErrorManager.isErrorMessageView()) box.put("errorout", out);
				
				///////////////////////////////////////////////////////////////////
				/*if (!AdminUtil.getInstance().checkLogin(out, box)) {
					return; 
				}*/
	            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
				///////////////////////////////////////////////////////////////////

			    if(v_process.equals("listPage")) {                       // �����Ǹ�
			      this.performListPage(request, response, box, out);
			    } else if(v_process.equals("selectPage")) {              // �󼼺��� 
			      this.performSelectPage(request, response, box, out);
			    } else if(v_process.equals("bookBillPage")) {             // �����û (���������� �̵�) 
				      this.performBookBillPage(request, response, box, out);
			    } else if(v_process.equals("bookBillCheckPage")) {        // �����û (����Ȯ�������� �̵�) 
				      this.performBookBillCheckPage(request, response, box, out);								  
			    } else if(v_process.equals("bookPropose")) {             // �����û 
				      this.performBookPropose(request, response, box, out);	
			    } else if(v_process.equals("bookProposeCancel")) {       // �����û ��� (���Ա�)
				      this.performBookProposeCancel(request, response, box, out);
			    } else if(v_process.equals("bookBillCancelPage")) {      // �����û ��� (ȯ�������� �̵�)
				      this.performBookBillCancelPage(request, response, box, out);					  
			    } else if(v_process.equals("bookBillCancel")) {          // �����û ��� (ȯ��)
				      this.performBookBillCancel(request, response, box, out);					  
			    } else if(v_process.equals("proposeListPage")) {         // �����û/��� ����Ʈ
				      this.performProposeListPage(request, response, box, out);
			    } 
	        }catch(Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	        }
	    }

	    /**
	    �����Ǹ�
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    @SuppressWarnings("unchecked")
		public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);
	            
	            String v_url  = "";
	             if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
	             	v_url = "/learn/user/2012/portal/helpdesk/zu_Book_L.jsp";
	             } else {
	             	v_url = "/learn/user/portal/helpdesk/zu_Book_L.jsp";
	             }
				
				BookBean bean = new BookBean();
	            ArrayList list = bean.selectUserBookList(box);
	            request.setAttribute("selectUserBookList", list);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_Book_L.jsp");

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
	            
	            String v_url  = "";
	             if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
	             	v_url = "/learn/user/2012/portal/helpdesk/zu_Book_R.jsp";
	             } else {
	             	v_url = "/learn/user/portal/helpdesk/zu_Book_R.jsp";
	             }

				BookBean bean = new BookBean();
	            DataBox dbox = bean.selectBook(box);
	            request.setAttribute("selectBook", dbox);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_Book_R.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performSelectPage()\r\n" + ex.getMessage());
	        }
	    }

		
	    /**
	    �����û(������������ �̵�)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performBookBillPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);

				BookBean bean = new BookBean();
	            DataBox dbox = bean.selectBook(box);
	            request.setAttribute("selectBook", dbox);
				
	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/book/zu_BookBill_P.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/user/game/book/zu_BookBill_L.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performBookBillPage()\r\n" + ex.getMessage());
	        }
	    }

	    /**
	    �����û(����Ȯ���������� �̵�)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performBookBillCheckPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);
				
	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/book/zu_BookBillCheck_P.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/user/game/book/zu_BookBillCheck_L.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performBookBillCheckPage()\r\n" + ex.getMessage());
	        }
	    }
		
	     /**
	    �����û
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    @SuppressWarnings("unchecked")
	    public void performBookPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	        try {
				BookSellBean bean = new BookSellBean();
	            int isOk = bean.insertBookPropose(box);

	            String v_msg = "";
	            String v_url = "/servlet/controller.book.BookServlet";
	            box.put("p_process", "listPage");

	            AlertManager alert = new AlertManager();

	            if(isOk > 0) {
					v_msg = "insert.ok";
	                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
	            }
	            else {
	                v_msg = "insert.fail";
	                alert.alertFailMessage(out, v_msg);
	            }

	            Log.info.println(this, box, v_msg + " on BookServlet");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performBookPropose()\r\n" + ex.getMessage());
	        }
	    }
		
	     /**
	    �����û ���(���Ա�)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    @SuppressWarnings("unchecked")
	    public void performBookProposeCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	        try {
				BookSellBean bean = new BookSellBean();
	            int isOk = bean.deleteBookPropose(box);

	            String v_msg = "";
	            String v_url = "/servlet/controller.book.BookServlet";
	            box.put("p_process", "proposeListPage");

	            AlertManager alert = new AlertManager();

	            if(isOk > 0) {
					v_msg = "��û��ҵǾ����ϴ�.";
	                alert.alertOkMessage(out, v_msg, v_url , box);
	            }
	            else {
	                v_msg = "��û��ҿ� �����߽��ϴ�.";
	                alert.alertFailMessage(out, v_msg);
	            }

	            Log.info.println(this, box, v_msg + " on BookServlet");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performBookProposeCancel()\r\n" + ex.getMessage());
	        }
	    }
			
	    /**
	    �����û ���(ȯ���������� �̵�)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performBookBillCancelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);

				BookBean bean = new BookBean();
	            DataBox dbox = bean.selectBook(box);
	            request.setAttribute("selectBook", dbox);
				
	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/book/zu_BookBillCancel_P.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/user/game/book/zu_BookBillCancel_P.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performBookBillCancelPage()\r\n" + ex.getMessage());
	        }
	    }
		
	     /**
	    �����û ���(ȯ��)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    @SuppressWarnings("unchecked")
	    public void performBookBillCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	        try {
				BookSellBean bean = new BookSellBean();
	            int isOk = bean.updateBookPropose(box);

	            String v_msg = "";
	            String v_url = "/servlet/controller.book.BookServlet";
	            box.put("p_process", "proposeListPage");

	            AlertManager alert = new AlertManager();

	            if(isOk > 0) {
					v_msg = "ȯ�� ��û�Ǿ����ϴ�.";
	                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
	            }
	            else {
	                v_msg = "ȯ�� ��û�� �����߽��ϴ�.";
	                alert.alertFailMessage(out, v_msg);
	            }

	            Log.info.println(this, box, v_msg + " on BookServlet");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performBookBillCancel()\r\n" + ex.getMessage());
	        }
	    }
		
	    /**
	     �����û/��� ����Ʈ
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    @SuppressWarnings("unchecked")
	    public void performProposeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	        try {
	            request.setAttribute("requestbox", box);
				
				BookSellBean bean = new BookSellBean();
	            ArrayList list = bean.selectUserSellList(box);
	            request.setAttribute("selectUserSellList", list);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/book/zu_BookPropose_L.jsp");
	            rd.forward(request, response);

	            Log.info.println(this, box, "Dispatch to /learn/user/game/book/zu_BookPropose_L.jsp");

	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performProposeListPage()\r\n" + ex.getMessage());
	        }
	    }
}