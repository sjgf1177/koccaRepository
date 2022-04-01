//**********************************************************
//  1. ��      ��: �����з��ڵ� SERVLET
//  2. ���α׷���: ClassifySubjectServlet.java
//  3. ��      ��:
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 0.1
//  6. ��      ��: anonymous 2003. 6. 30
//  7. ��      ��:
//
//**********************************************************
package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.ClassifySubjectBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.course.ClassifySubjectServlet")
public class ClassifySubjectServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
			v_process = box.getStringDefault("p_process","listPage");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			if (!AdminUtil.getInstance().checkRWRight("ClassifySubjectServlet", v_process, out, box)) {
				return;
			}

			if (v_process.equals("listPage")) {      			//in case of �����з� ��ȸ ȭ��
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("insertPage")) {      	//in case of �����з� ��� ȭ��
				this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("insert")) {      		//in case of �����з� ���
				this.performInsert(request, response, box, out);
			} else if (v_process.equals("updatePage")) {      	//in case of �����з� ���� ȭ��
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("update")) {     		//in case of �����з� ����
				this.performUpdate(request, response, box, out);
			} else if (v_process.equals("delete")) {      		//in case of �����з� ����
				this.performDelete(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    SUBJECT CLASSIFICATION VIEW
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/course/za_ClassifySubject_L.jsp";

			ClassifySubjectBean bean = new ClassifySubjectBean();
			ArrayList list1 = bean.SelectList(box);
			request.setAttribute("SubjectClassificationList", list1);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

	/**
    NEW SUBJECT CLASSIFICATION CREATE PAGE
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
			String v_return_url = "/learn/admin/course/za_ClassifySubject_I.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

	/**
    NEW SUBJECT CLASSIFICATION CREATE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{
			String v_url  = "/servlet/controller.course.ClassifySubjectServlet";

			ClassifySubjectBean bean = new ClassifySubjectBean();
			int isOk = bean.InsertSubjectClassification(box);

			String v_msg = "";
			box.put("p_process", "listPage");

			AlertManager alert = new AlertManager();
			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			}else if(isOk == -1) {
				v_msg = "insert.failDupe";
				alert.alertFailMessage(out, v_msg);
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
    SUBJECT CLASSIFICATION UPDATE PAGE
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
			String v_url = "/learn/admin/course/za_ClassifySubject_U.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}
	}

	/**
    SUBJECT CLASSIFICATION UPDATE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{
			String v_url  = "/servlet/controller.course.ClassifySubjectServlet";

			ClassifySubjectBean bean = new ClassifySubjectBean();
			int isOk = bean.UpdateSubjectClassification(box);

			String v_msg = "";
			box.put("p_process", "listPage");

			AlertManager alert = new AlertManager();
			if(isOk > 0) {
				v_msg = "update.ok";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			} else {
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
    SUBJECT CLASSIFICATION DELETE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{
			String v_url  = "/servlet/controller.course.ClassifySubjectServlet";

			ClassifySubjectBean bean = new ClassifySubjectBean();
			int isOk = bean.DeleteSubjectClassification(box);

			String v_msg = "";
			box.put("p_process", "listPage");

			AlertManager alert = new AlertManager();
			if(isOk > 0) {
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			} else if (isOk == -1) {
				v_msg = "�ߺз��� �־� ������ �� �����ϴ�. �ߺз� ���� �� �ٽ� �õ��ϼ���.";
				alert.alertFailMessage(out, v_msg);
			} else if (isOk == -2) {
				v_msg = "�з��� ���������� �ֽ��ϴ�. ������������ �з� ���� �� �ٽ� �õ��ϼ���.";
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}
}