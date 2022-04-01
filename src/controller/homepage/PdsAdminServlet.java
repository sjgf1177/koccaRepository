//**********************************************************
//  1. ��      ��: ����ڷ���� �����ϴ� ����
//  2. ���α׷��� : PdsAdminServlet.java
//  3. ��      ��: ����ڷ���� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 1. 2
//  7. ��      ��:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.PdsAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.homepage.PdsAdminServlet")
public class PdsAdminServlet extends javax.servlet.http.HttpServlet {

	/**
	 * DoGet
	 * Pass get requests through to PerformTask
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";
		boolean v_canRead = false;
		boolean v_canAppend = false;
		boolean v_canModify = false;
		boolean v_canDelete = false;

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();

			box = RequestManager.getBox(request);

			v_process = box.getString("p_process");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
			v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
			v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
			v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));

			if(v_process.equals("insertPage")) {          //  ����������� �̵��Ҷ�
				if(v_canAppend) this.performInsertPage(request, response, box, out);
				else this.errorPage(box, out);
			}
			else if(v_process.equals("insert")) {         //  ����Ҷ�
				if(v_canAppend) this.performInsert(request, response, box, out);
				else this.errorPage(box, out);
			}
			else if(v_process.equals("updatePage")) {     //  ������������ �̵��Ҷ�
				if(v_canModify) this.performUpdatePage(request, response, box, out);
				else this.errorPage(box, out);
			}
			else if(v_process.equals("update")) {         //  �����Ͽ� �����Ҷ�
				if(v_canModify) this.performUpdate(request, response, box, out);
				else this.errorPage(box, out);
			}
			else if(v_process.equals("delete")) {         //  �����Ҷ�
				if(v_canDelete) this.performDelete(request, response, box, out);
				else this.errorPage(box, out);
			}
			else if(v_process.equals("select")) {         //  �󼼺����Ҷ�
				if(v_canRead) this.performSelect(request, response, box, out);
			}
			else {                                        //  ��ȸ�Ҷ�
				if(v_canRead) this.performSelectList(request, response, box, out);
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
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			PdsAdminBean pds = new PdsAdminBean();

			ArrayList list = pds.selectPdsList(box);
			request.setAttribute("selectPdsList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_PdsAdmin_L.jsp");
			rd.forward(request, response);

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
	public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			PdsAdminBean pds = new PdsAdminBean();

			DataBox dbox = pds.selectPds(box);
			request.setAttribute("selectPds", dbox);

			ArrayList list = pds.selectPdsList(box);
			request.setAttribute("selectPdsList", list);


			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_PdsAdmin_R.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelect()\r\n" + ex.getMessage());
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
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_PdsAdmin_I.jsp");
			rd.forward(request, response);

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
			PdsAdminBean pds = new PdsAdminBean();

			int isOk = pds.insertPds(box);

			String v_msg = "";
			String v_url = "/servlet/controller.homepage.PdsAdminServlet";
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}
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

			PdsAdminBean pds = new PdsAdminBean();

			DataBox dbox = pds.selectPds(box);

			request.setAttribute("selectPds", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_PdsAdmin_U.jsp");
			rd.forward(request, response);

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
			PdsAdminBean pds = new PdsAdminBean();

			int isOk = pds.updatePds(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.homepage.PdsAdminServlet";
			box.put("p_process", "");
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
			PdsAdminBean pds = new PdsAdminBean();

			int isOk = pds.deletePds(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.homepage.PdsAdminServlet";
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}


	public void errorPage(RequestBox box, PrintWriter out)
	throws Exception {
		try {
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
			//  Log.sys.println();

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("errorPage()\r\n" + ex.getMessage());
		}
	}
}

