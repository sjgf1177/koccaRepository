//*********************************************************
//1. ��      ��: ������ ���� ����
//2. ���α׷���: SchoolfeeAdminServlet.java
//3. ��      ��: �������� servlet
//4. ȯ      ��: JDK 1.5
//5. ��      ��: 1.0
//6. ��      ��: 2009.12.17
//7. ��      ��:
//**********************************************************
package controller.polity;

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
import com.credu.polity.SchoolfeeAdminBean;
import com.credu.study.MyClassBean;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.polity.SchoolfeeAdminServlet")
public class SchoolfeeAdminServlet extends javax.servlet.http.HttpServlet  implements Serializable {

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

			v_process = box.getStringDefault("p_process","selectList");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			if(v_process.equals("selectList")) { 						//  ��ȸȭ��
				this.performSelectList(request, response, box, out);
			}else if(v_process.equals("ExcelDown")) {         			//  ���� ����
				this.performExcelList(request, response, box, out);
			}else if(v_process.equals("paycancelPage")) {         			//  ��������� ��� ������
				this.performPayCancePage(request, response, box, out);
			}else if(v_process.equals("paycancelPage2")) {         			//  ��������� ���Ȯ�� ������
				this.performPayCancePage2(request, response, box, out);
			}else if(v_process.equals("SchoolfeeInfo")){   //�����Ἴ������
				this.performSelectSchoolfeeInfo(request, response, box, out);
			}else if(v_process.equals("taxUpdate")){   //��꼭 �������� ����
				this.performUpdate(request, response, box, out);
			}else if(v_process.equals("CancelPropose")){                  // ���� ��û ��� 2010.01.27
				this.performCancelPropose(request, response, box, out);
			}


		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	����Ʈ(������)
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			SchoolfeeAdminBean bean = new SchoolfeeAdminBean();
			box.put("excelchk", "N");
			ArrayList list = bean.selectList(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(box.getBoolean("isSubject") ? "/learn/admin/polity/za_SchoolfeeAdmin_subject_L.jsp" :  "/learn/admin/polity/za_SchoolfeeAdmin_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_SchoolfeeAdmin_L.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
	  Excel �ٿ�
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	 */
	public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{
			request.setAttribute("requestbox", box);
			//			  String v_return_url = "/learn/admin/polity/za_SchoolfeeAdmin_E.jsp";
			SchoolfeeAdminBean bean = new SchoolfeeAdminBean();

			if (box.getString("p_action").equals("go"))
			{
//				ArrayList list = bean.selectExcelList(box);
				box.put("excelchk", "Y");
				ArrayList list = bean.selectList(box);
				request.setAttribute("selectList", list);
			}

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_SchoolfeeAdmin_E.jsp");
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
		}
	}

	/**
	������� ������
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performPayCancePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			SchoolfeeAdminBean bean = new SchoolfeeAdminBean();

			ArrayList list = bean.selectPayInfo(box);
			request.setAttribute("payInfo", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_PayCancel_P.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_PayCancel_P.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performPayCancePage()\r\n" + ex.getMessage());
		}
	}
	public void performPayCancePage2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			SchoolfeeAdminBean bean = new SchoolfeeAdminBean();

			ArrayList list = bean.selectPayInfo(box);
			request.setAttribute("payInfo", list);
			box.put("onlyView", "true");

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_PayCancel_P.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_PayCancel_P2.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performPayCancePage()\r\n" + ex.getMessage());
		}
	}

	/**
	��꼭 ���� ������
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performSelectSchoolfeeInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			SchoolfeeAdminBean bean = new SchoolfeeAdminBean();

			DataBox dbox = bean.selectSchoolfeeInfo(box);
			request.setAttribute("SchoolfeeInfo", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_SchoolfeeAdmin_U.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_SchoolfeeAdmin_U.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performTaxRegistPage()\r\n" + ex.getMessage());
		}
	}

	/**
    ��꼭 �������� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_url  = "/servlet/controller.polity.SchoolfeeAdminServlet";

			SchoolfeeAdminBean bean = new SchoolfeeAdminBean();

			int isOk = bean.update(box);
			box.put("p_process","selectList");

			String v_msg = "";

			AlertManager alert = new AlertManager();
			if(isOk > 0) {
				v_msg = "save.ok";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			}else {
				v_msg = "save.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on performUpdate");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}

	}

	/**
    ���� ��û ��� 2010.01.27 ������� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performCancelPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			MyClassBean bean = new MyClassBean();

			int isOk = bean.updateCancelPropose(box);
			String v_msg = "";
			String v_url = "/servlet/controller.polity.SchoolfeeAdminServlet";
			box.put("p_process","selectList");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "propcancel.ok";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			}
			else {
				v_msg = "propcancel.fail";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("ProposeCancel()\r\n" + ex.getMessage());
		}
	}

}