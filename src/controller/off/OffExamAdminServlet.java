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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffExamBean;
import com.credu.system.AdminUtil;
import com.dunet.common.taglib.KoccaAjaxBean;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffExamAdminServlet")
public class OffExamAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("OffExamAdminServlet", v_process, out, box)) {
				return;
			}
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("select")) {                  //  ����Ʈ
				this.performSelectList(request, response, box, out);
			}
			if(v_process.equals("selectOneTerm")) {                  //  ����Ʈ
				this.performSelectOneTermList(request, response, box, out);
			}
			if(v_process.equals("updateOneTerm")) {                  //  ����Ʈ
				this.performupdateOneTermList(request, response, box, out);
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
			request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			if(box.get("s_action").equals("go")) {
				OffExamBean bean = new OffExamBean();

				//��ü ����Ʈ
				request.setAttribute("resultData", bean.listPage(box));
			}
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_Exam_L.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
	public void performSelectOneTermList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			if(box.get("s_action").equals("go")) {
				OffExamBean bean = new OffExamBean();

				//��ü ����Ʈ
				request.setAttribute("resultData", bean.listPageOneTerm(box));
			}
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_ExamOneTerm_L.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	public void performupdateOneTermList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�
			String[] keyList = {"p_isgraduated", "p_userid"};
			box.put("keyList", keyList);
			box.put("sqlNum","lecture.isgraduated.update");
			KoccaAjaxBean bean = new KoccaAjaxBean();
			boolean isOk = Boolean.parseBoolean(bean.save(box).toString());

			String v_msg = "";
			String v_url = "/servlet/controller.off.OffExamAdminServlet";
			box.put("p_process", "selectOneTerm");

			AlertManager alert = new AlertManager();

			if(isOk) {
				request.setAttribute("requestbox", box);
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

}

