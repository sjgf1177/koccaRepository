//**********************************************************
//  1. 제	  목: 템플릿메뉴관리
//  2. 프로그램명 : TempletMenuAdminServlet
//  3. 개	  요: 템플릿관리에서 사용되는 메뉴를 관리한다.
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009.11.02
//  7. 수	 정1:
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

import com.credu.course.TempletMenuAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.course.TempletMenuAdminServlet")
public class TempletMenuAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
			if (!AdminUtil.getInstance().checkRWRight("TempletMenuAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("insert")) {					 // 대분류코드 등록할때
				this.performInsert(request, response, box, out);
			} else if(v_process.equals("update")) {					 // 대분류코드 수정하여 저장할때
				this.performUpdate(request, response, box, out);
			} else if(v_process.equals("delete")) {					 // 대분류코드 삭제할때
				this.performDelete(request, response, box, out);
			} else {					 // 대분류코드 리스트
				this.performSelectList(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	대분류코드 삭제할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box	  receive from the form object
	@param out	  printwriter object
	@return void
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			TempletMenuAdminBean bean = new TempletMenuAdminBean();

			int isOk = bean.deleteMenu(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.TempletMenuAdminServlet";
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

			Log.info.println(this, box, v_msg + " on TempletMenuAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}

	/**
	대분류코드 등록할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box	  receive from the form object
	@param out	  printwriter object
	@return void
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			TempletMenuAdminBean bean = new TempletMenuAdminBean();

			int isOk = bean.insertMenu(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.TempletMenuAdminServlet";
			box.put("s_gubun", "00");
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

			Log.info.println(this, box, v_msg + " on TempletMenuAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}

	/**
	메뉴목록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box	  receive from the form object
	@param out	  printwriter object
	@return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);	//명시적으로 box 객체를 넘겨준다

			TempletMenuAdminBean bean = new TempletMenuAdminBean();
			ArrayList list = bean.selectListMenu(box);

			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_TempletMenu_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/system/za_TempletMenu_L.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
	대분류코드 수정하여 저장할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box	  receive from the form object
	@param out	  printwriter object
	@return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			TempletMenuAdminBean bean = new TempletMenuAdminBean();

			int isOk = bean.updateMenu(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.TempletMenuAdminServlet";
			box.put("p_process", "select");
			//	  수정 후 해당 리스트 페이지로 돌아가기 위해

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "update.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "update.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on TempletMenuAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

}

