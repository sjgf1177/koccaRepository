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

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffAttendBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffAttendAdminServlet")
public class OffAttendAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
			if (!AdminUtil.getInstance().checkRWRight("OffAttendAdminServlet", v_process, out, box)) {
				return;
			}
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("select")) {                  //  ����Ʈ
				this.performSelectList(request, response, box, out);
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
				OffAttendBean bean = new OffAttendBean();

				//��ü ����Ʈ
				request.setAttribute("resultData", bean.listPage(box));
			}
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_Attend_L.jsp");
			rd.forward(request, response);

			////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_L.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

}

