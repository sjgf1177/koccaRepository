//**********************************************************
//  1. ��      ��: ������ �����ϴ� ����
//  2. ���α׷��� : ManagerAdminServlet.java
//  3. ��      ��: ������ ���� ���α׷�
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11. 10
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
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.ManagerAdminBean;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.system.ManagerAdminServlet")
public class ManagerAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

			////////////////////////////////////////////////////////////////// ���α׷� ����üũ
			if (!AdminUtil.getInstance().checkRWRight("ManagerAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("selectView")){                      // ������ �󼼺���� �̵��Ҷ�
				this.performSelectView(request, response, box, out);
			} else if(v_process.equals("insertPage")) {              // ������ ����������� �̵��Ҷ�
				this.performInsertPage(request, response, box, out);
			} else if(v_process.equals("insert")) {                  // ������ ����Ҷ�
				this.performInsert(request, response, box, out);
			} else if(v_process.equals("updatePage")) {              // ������ ������������ �̵��Ҷ�
				this.performUpdatePage(request, response, box, out);
			} else if(v_process.equals("update")) {                  // ������ �����Ͽ� �����Ҷ�
				this.performUpdate(request, response, box, out);
			} else if(v_process.equals("delete")) {                  // ������ �����Ҷ�
				this.performDelete(request, response, box, out);
			} else if(v_process.equals("select")) {                  // ������ ����Ʈ
				this.performSelectList(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    // ������ �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {

			ManagerAdminBean bean = new ManagerAdminBean();

			int isOk = bean.deleteManager(box);

			String v_msg = "";
			String v_url = "/servlet/controller.system.ManagerAdminServlet";
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

			Log.info.println(this, box, v_msg + " on ManagerAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}

	/**
    ������ ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			int isOk = 0;//, isOk1 = 0, isOk2 = 0;
			ManagerAdminBean bean = new ManagerAdminBean();

			isOk  = bean.insertManager(box);

			String v_msg = "";
			String v_url = "/servlet/controller.system.ManagerAdminServlet";
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

			Log.info.println(this, box, v_msg + " on ManagerAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}

	/**
    ������ ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Manager_I.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Manager_I.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
    ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			ManagerAdminBean bean = new ManagerAdminBean();
			ArrayList List = bean.selectListManager(box);

			request.setAttribute("selectList", List);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Manager_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Manager_L.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
    ������ �󼼺���� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			ManagerAdminBean bean = new ManagerAdminBean();

			DataBox dbox = bean.selectViewManager(box);
			request.setAttribute("selectManager", dbox);
			//�����ְ�
			ArrayList list1 = bean.selectViewManagerGrcode(box);
			request.setAttribute("selectManagerGrcode", list1);
			//����
			ArrayList list2 = bean.selectViewManagerSubj(box);
			request.setAttribute("selectManagerSubj", list2);
			//ȸ��
			ArrayList list3 = bean.selectViewManagerComp(box);
			request.setAttribute("selectManagerComp", list3);
			//���־�üȸ��
			ArrayList list4 = bean.selectViewManagerOutComp(box);
			request.setAttribute("selectManagerOutComp", list4);
			//�μ�
			ArrayList list5 = bean.selectViewManagerDept(box);
			request.setAttribute("selectManagerDept", list5);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Manager_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Manager_R.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    // ������ �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {

			ManagerAdminBean bean = new ManagerAdminBean();

			int isOk = bean.updateManager(box);

			String v_msg = "";
			String v_url = "/servlet/controller.system.ManagerAdminServlet";
			box.put("p_process", "select");  //      ���� �� �ش� ����Ʈ �������� ���ư��� ���� �ʿ�

			AlertManager alert = new AlertManager();

			if(isOk > 0 ) {
				v_msg = "update.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "update.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on ManagerAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
    ������ ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			ManagerAdminBean bean = new ManagerAdminBean();

			DataBox dbox = bean.selectViewManager(box);
			request.setAttribute("selectManager", dbox);
			//�����ְ�
			ArrayList list1 = bean.selectViewManagerGrcode(box);
			request.setAttribute("selectManagerGrcode", list1);
			//����
			ArrayList list2 = bean.selectViewManagerSubj(box);
			request.setAttribute("selectManagerSubj", list2);
			//ȸ��
			ArrayList list3 = bean.selectViewManagerComp(box);
			request.setAttribute("selectManagerComp", list3);
			//���־�üȸ��
			ArrayList list4 = bean.selectViewManagerOutComp(box);
			request.setAttribute("selectManagerOutComp", list4);
			//�μ�
			ArrayList list5 = bean.selectViewManagerDept(box);
			request.setAttribute("selectManagerDept", list5);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Manager_U.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Manager_U.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}
	}


}

