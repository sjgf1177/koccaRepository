//**********************************************************
//  1. 제      목: 운영메뉴 제어하는 서블릿
//  2. 프로그램명 : MenuAdminServlet.java
//  3. 개      요: 운영메뉴 제어 프로그램
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 8
//  7. 수      정:
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

			if(v_process.equals("selectView")){                      // 메뉴 상세보기로 이동할때
				this.performSelectView(request, response, box, out);
			} else if(v_process.equals("insertPage")) {              // 메뉴 등록페이지로 이동할때
				this.performInsertPage(request, response, box, out);
			} else if(v_process.equals("insert")) {                  // 메뉴 등록할때
				this.performInsert(request, response, box, out);
			} else if(v_process.equals("updatePage")) {              // 메뉴 수정페이지로 이동할때
				this.performUpdatePage(request, response, box, out);
			} else if(v_process.equals("update")) {                  // 메뉴 수정하여 저장할때
				this.performUpdate(request, response, box, out);
			} else if(v_process.equals("delete")) {                  // 메뉴 삭제할때
				this.performDelete(request, response, box, out);
			} else if(v_process.equals("select")) {                 // 메뉴 리스트
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("subMenuSelect")) {                 // 메뉴 리스트
                this.performSubSelectList(request, response, box, out);
            }
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    // 메뉴 삭제할때
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
    메뉴 등록할때
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
    메뉴 등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			/*-------- 하단 리스트 출력 시작 -------------*/
			MenuAdminBean bean = new MenuAdminBean();
			ArrayList List = bean.selectListMenu(box);

			request.setAttribute("selectList", List);
			/*-------- 하단 리스트 출력 시작 -------------*/

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
    메뉴 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

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
    메뉴 상세보기로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

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
    // 메뉴 수정하여 저장할때
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
			//      수정 후 해당 리스트 페이지로 돌아가기 위해

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
    메뉴 수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			/*-------- 하단 리스트 출력 시작 -------------*/
			MenuAdminBean bean = new MenuAdminBean();
			ArrayList list = bean.selectListMenu(box);

			request.setAttribute("selectList", list);
			/*-------- 하단 리스트 출력 끝   -------------*/

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
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

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

