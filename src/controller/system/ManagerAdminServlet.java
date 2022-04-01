//**********************************************************
//  1. 제      목: 관리자 제어하는 서블릿
//  2. 프로그램명 : ManagerAdminServlet.java
//  3. 개      요: 관리자 제어 프로그램
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
//  7. 수     정1:
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

			////////////////////////////////////////////////////////////////// 프로그램 권한체크
			if (!AdminUtil.getInstance().checkRWRight("ManagerAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("selectView")){                      // 관리자 상세보기로 이동할때
				this.performSelectView(request, response, box, out);
			} else if(v_process.equals("insertPage")) {              // 관리자 등록페이지로 이동할때
				this.performInsertPage(request, response, box, out);
			} else if(v_process.equals("insert")) {                  // 관리자 등록할때
				this.performInsert(request, response, box, out);
			} else if(v_process.equals("updatePage")) {              // 관리자 수정페이지로 이동할때
				this.performUpdatePage(request, response, box, out);
			} else if(v_process.equals("update")) {                  // 관리자 수정하여 저장할때
				this.performUpdate(request, response, box, out);
			} else if(v_process.equals("delete")) {                  // 관리자 삭제할때
				this.performDelete(request, response, box, out);
			} else if(v_process.equals("select")) {                  // 관리자 리스트
				this.performSelectList(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    // 관리자 삭제할때
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
    관리자 등록할때
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
    관리자 등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

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
    관리자 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

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
    관리자 상세보기로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

			ManagerAdminBean bean = new ManagerAdminBean();

			DataBox dbox = bean.selectViewManager(box);
			request.setAttribute("selectManager", dbox);
			//교육주관
			ArrayList list1 = bean.selectViewManagerGrcode(box);
			request.setAttribute("selectManagerGrcode", list1);
			//과정
			ArrayList list2 = bean.selectViewManagerSubj(box);
			request.setAttribute("selectManagerSubj", list2);
			//회사
			ArrayList list3 = bean.selectViewManagerComp(box);
			request.setAttribute("selectManagerComp", list3);
			//외주업체회사
			ArrayList list4 = bean.selectViewManagerOutComp(box);
			request.setAttribute("selectManagerOutComp", list4);
			//부서
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
    // 관리자 수정하여 저장할때
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
			box.put("p_process", "select");  //      수정 후 해당 리스트 페이지로 돌아가기 위해 필요

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
    관리자 수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			ManagerAdminBean bean = new ManagerAdminBean();

			DataBox dbox = bean.selectViewManager(box);
			request.setAttribute("selectManager", dbox);
			//교육주관
			ArrayList list1 = bean.selectViewManagerGrcode(box);
			request.setAttribute("selectManagerGrcode", list1);
			//과정
			ArrayList list2 = bean.selectViewManagerSubj(box);
			request.setAttribute("selectManagerSubj", list2);
			//회사
			ArrayList list3 = bean.selectViewManagerComp(box);
			request.setAttribute("selectManagerComp", list3);
			//외주업체회사
			ArrayList list4 = bean.selectViewManagerOutComp(box);
			request.setAttribute("selectManagerOutComp", list4);
			//부서
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

