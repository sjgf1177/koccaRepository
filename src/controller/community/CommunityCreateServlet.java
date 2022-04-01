//**********************************************************
//  1. ��      ��: �ڷ���� �����ϴ� ����
//  2. ���α׷��� : HomePageBoardServlet.java
//  3. ��      ��: �ڷ���� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1 �̿���
//  7. ��      ��: 2005.7.1 �̿���
//**********************************************************

package controller.community;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityCreateBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
public class CommunityCreateServlet extends javax.servlet.http.HttpServlet {

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
		PrintWriter      out          = null;
		RequestBox       box          = null;
		String           v_process    = "";

		try {
			response.setContentType("text/html;charset=euc-kr");

			out    = response.getWriter();
			box    = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process","insertPage");

			if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

			// �α� check ��ƾ VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			/////////////////////////////////////////////////////////////////////////////
			if(v_process.equals("insertPage")) {          //  ����������� ù�������� �̵��Ҷ�
				this.performInsertPage(request, response, box, out);
			} else if(v_process.equals("insertPage2")) {          //  ����������� �ι�° �������� �̵��Ҷ�
				this.performInsertStep2Page(request, response, box, out);
			} else if(v_process.equals("insertData")) {          //  ,�����͵��
				this.performInsertData(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
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

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuCreate.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuCreate.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

	/**
    �ι�° ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertStep2Page(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuCreateStep2.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuCreateStep2.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}



	/**
    Ŀ�´�Ƽ��� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */

	public void performInsertData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			CommunityCreateBean bean = new CommunityCreateBean();

			int isOk = bean.insertBaseMst(box);

			String v_msg = "";
			String v_url = "/servlet/controller.community.CommunityIndexServlet";
			box.put("p_process", "selectmain");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on CommunityCreateServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertData()\r\n" + ex.getMessage());
		}
	}

}

