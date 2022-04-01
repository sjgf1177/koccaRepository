//**********************************************************
//  1. ��      ��: ��޴� �����ϴ� ����
//  2. ���α׷��� : MenuAdminServlet.java
//  3. ��      ��: ��޴� ���� ���α׷�
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11. 8
//  7. ��      ��:
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
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.MenuAdminBean;
import com.credu.system.MenuData;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.system.MenuAdminServlet")
public class MenuAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
		//        MultipartRequest multi = null;
		RequestBox box = null;
		String v_process = "";
		//        int fileupstatus = 0;

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getString("p_process");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}


			if (!AdminUtil.getInstance().checkRWRight("MenuAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			if(v_process.equals("selectView")){                      // �޴� �󼼺���� �̵��Ҷ�
				this.performSelectView(request, response, box, out);
			} else if(v_process.equals("insertPage")) {              // �޴� ����������� �̵��Ҷ�
				this.performInsertPage(request, response, box, out);
			} else if(v_process.equals("insert")) {                  // �޴� ����Ҷ�
				this.performInsert(request, response, box, out);
			} else if(v_process.equals("updatePage")) {              // �޴� ������������ �̵��Ҷ�
				this.performUpdatePage(request, response, box, out);
			} else if(v_process.equals("update")) {                  // �޴� �����Ͽ� �����Ҷ�
				this.performUpdate(request, response, box, out);
			} else if(v_process.equals("delete")) {                  // �޴� �����Ҷ�
				this.performDelete(request, response, box, out);
			} else if(v_process.equals("select")) {                 // �޴� ����Ʈ
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("subMenuSelect")) {                 // �޴� ����Ʈ
                this.performSubSelectList(request, response, box, out);
            }
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    // �޴� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			MenuAdminBean bean = new MenuAdminBean();

			int isOk = bean.deleteMenu(box);

			String v_msg = "";
			String v_url = "/servlet/controller.system.MenuAdminServlet";
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

			Log.info.println(this, box, v_msg + " on MenuAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}

	/**
    �޴� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			MenuAdminBean bean = new MenuAdminBean();

			int isOk = bean.insertMenu(box);

			String v_msg = "";
			String v_url = "/servlet/controller.system.MenuAdminServlet";
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

			Log.info.println(this, box, v_msg + " on MenuAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}

	/**
    �޴� ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			/*-------- �ϴ� ����Ʈ ��� ���� -------------*/
			MenuAdminBean bean = new MenuAdminBean();
			ArrayList List = bean.selectListMenu(box);

			request.setAttribute("selectList", List);
			/*-------- �ϴ� ����Ʈ ��� ���� -------------*/

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Menu_I.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Menu_I.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
    �޴� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			MenuAdminBean bean = new MenuAdminBean();
			ArrayList List = bean.selectListMenu(box);

			request.setAttribute("selectList", List);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Menu_L.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
    �޴� �󼼺���� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			MenuAdminBean bean = new MenuAdminBean();
			MenuData data = bean.selectViewMenu(box);

			request.setAttribute("selectMenu", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Menu_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Menu_R.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    // �޴� �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			MenuAdminBean bean = new MenuAdminBean();

			int isOk = bean.updateMenu(box);

			String v_msg = "";
			String v_url = "/servlet/controller.system.MenuAdminServlet";
			box.put("p_process", "select");
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

			Log.info.println(this, box, v_msg + " on MenuAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
    �޴� ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			/*-------- �ϴ� ����Ʈ ��� ���� -------------*/
			MenuAdminBean bean = new MenuAdminBean();
			ArrayList list = bean.selectListMenu(box);

			request.setAttribute("selectList", list);
			/*-------- �ϴ� ����Ʈ ��� ��   -------------*/

			MenuData data = bean.selectViewMenu(box);

			request.setAttribute("selectMenu", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Menu_U.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Menu_U.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}
	}
	
	public void performSubSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            MenuAdminBean bean = new MenuAdminBean();
            ArrayList List = bean.subSelectList(box);

            request.setAttribute("subMenuList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/kocca/mScreenSubMenuAjaxResult.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}

