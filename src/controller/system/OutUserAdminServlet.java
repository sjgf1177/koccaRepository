//**********************************************************
//  1. ��      ��: ����̿��� ���� ����
//  2. ���α׷��� : OutUserAdminServlet.java
//  3. ��      ��: ����̿��� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �ż�ö 2004. 12. 24
//  7. ��      ��: �ż�ö 2004. 12. 24
//**********************************************************

package controller.system;

import java.io.PrintWriter;
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
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.OutUserAdminBean;

/**
 *����̿��� ����
 *<p>����:OutUserAdminServlet.java</p>
 *<p>����:����̿��� ���� ����</p>
 *<p>Copyright: Copright(c)2004</p>
 *<p>Company: VLC</p>
 *@author ��â��
 *@version 1.0
 */

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.system.OutUserAdminServlet")
public class OutUserAdminServlet extends javax.servlet.http.HttpServlet {

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
			v_process = box.getStringDefault("p_process","SubjectList");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			///////////////////////////////////////////////////////////////////
			// �α� check ��ƾ VER 0.2 - 2003.09.9
			System.out.println("============= 123 : " + v_process + " // " + out + " // " + box);
			if (!AdminUtil.getInstance().checkRWRight("OutUserAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("select")) {       //    ��ü ����Ʈ ��ȸ
				this.performSelect(request, response, box, out);
			}
			else if(v_process.equals("insertPage")) {       //  ��ü ����������� �̵��Ҷ�
				this.performInsertPage(request, response, box, out);
			}
			else if(v_process.equals("insert")) {       //  ��ü ����Ҷ�
				this.performInsert(request, response, box, out);
			}
			else if(v_process.equals("selectPage")) {       //  ��ü ������������ �̵��Ͽ� �ѷ��ٶ�
				this.performSelectPage(request, response, box, out);
			}
			else if(v_process.equals("update")) {     //      ��ü �����Ͽ� �����Ҷ�
				this.performUpdate(request, response, box, out);
			}
			else if(v_process.equals("delete")) {     //    ��ü �����Ҷ�
				this.performDelete(request, response, box, out);
			}
			else if(v_process.equals("userList")) {   //ȸ���˻�
				this.performUserList(request, response, box, out);
			}
			else if(v_process.equals("usercheck")) {   //���̵� üũ
				this.performCheck(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    ���� ����ó��
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void errorPage(RequestBox box, PrintWriter out)
	throws Exception {
		try {
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("errorPage()\r\n" + ex.getMessage());
		}
	}


	/**
    ���־�ü ����� ���� ���üũ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			OutUserAdminBean outuser = new OutUserAdminBean();

			DataBox dbox = outuser.userCheck(box);

			request.setAttribute("userCheck", dbox);

			box.put("p_process", "usercheck");

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_usercheck.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performList()\r\n" + ex.getMessage());
		}
	}

	/**
    ��ü ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			OutUserAdminBean outuser = new OutUserAdminBean();

			int isOk = outuser.deleteoutcomp(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.system.OutUserAdminServlet";
			box.put("p_process", "select");
			box.put("p_compgubun", "%");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "�����Ǿ����ϴ�!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "���� ����!";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}


	/**
    ��ü ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {

			String v_msg = "";
			String v_url = "/servlet/controller.system.OutUserAdminServlet";
			box.put("p_process", "select"); //ȸ���� �� ��ü����Ʈ ȭ������ �̵�

			AlertManager alert = new AlertManager();

			OutUserAdminBean outuser = new OutUserAdminBean();

			int isOk = outuser.insertoutcomp(box);


			if(isOk > 0) {
				v_msg = "�����Ͽ����ϴ�!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "���� ����!";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}


	/**
    ��ü ����������� �̵�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_OutUser_Comp_I.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
    ��ü ����Ʈ ��ȸ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {

			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/system/za_OutUser_Comp_L.jsp";

			OutUserAdminBean outuser = new OutUserAdminBean();

			ArrayList list = outuser.selectoutcomp(box);

			request.setAttribute("selectoutcomp", list);

			if (box.getBoolean("isExcel")) {
				v_return_url = "/learn/admin/off/za_excel.jsp";//�ʼ�
				box.put("title", "��ü����");//���� ����
				box.put("tname", "ȸ���ڵ�|ȸ���|��ǥ�̸�|����ڸ�|�����ID|����ڵ�Ϲ�ȣ|��ǥ��ȭ��ȣ|�ѽ���ȣ|�����ȣ1|�����ȣ2|������ּ�1|������ּ�2|Ȩ������");//�÷���
				box.put("tcode", "comp|compnm|coname|name|userid|compresno|telno|faxno|zip1|zip2|compaddr1|compaddr2|homepage");//�������̸�
				//				head_sql  = " select comp, compnm, compresno, coname, telno, faxno,\n";
				//				head_sql += "        addr compaddr1, addr2 compaddr2, zip1, zip2, gubun, homepage,ldate,\n";
				//				head_sql += "        userid, name, resno, pwd, hometel, handphone, comptel,\n";
				//				head_sql += "        email, post1, post2, addr, addr2, comptext\n";
				//				box.put("bgcolumn", "TSTEP|MTEST|HTEST|FTEST|REPORT|ETC1|ETC2");
				box.put("resultListName", "selectoutcomp");//��� ���
			}

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelect()\r\n" + ex.getMessage());
		}
	}



	/**
    ��ü ���������� ��ȸ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			OutUserAdminBean outuser = new OutUserAdminBean();

			DataBox dbox = outuser.select2outcomp(box);

			request.setAttribute("select2outcomp", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_OutUser_Comp_U.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectPage()\r\n" + ex.getMessage());
		}
	}

	/**
    ��ü ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			OutUserAdminBean outuser = new OutUserAdminBean();

			int isOk = outuser.updateoutcomp(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.system.OutUserAdminServlet";
			//      ���� �� ��ü����Ʈ ȭ������ �̵�
			box.put("p_process", "select");


			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "�����Ͽ����ϴ�!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "���� ����!";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
    ȸ���˻�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUserList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			OutUserAdminBean outuser = new OutUserAdminBean();
			box.put("p_param1", box.get("p_param1"));
			box.put("p_param2", box.get("p_param2"));

			request.setAttribute("resultList", outuser.userList(box));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_compManagerSearch_L.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUserList()\r\n" + ex.getMessage());
		}
	}


}
