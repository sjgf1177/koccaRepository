//**********************************************************
//  1. 제      목: 용어사전 제어하는 서블릿
//  2. 프로그램명: DicSubjAdminServlet.java
//  3. 개      요: 용어사전 페이지을 제어한다 (과정)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.DicSubjAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.course.DicSubjAdminServlet")
public class DicSubjAdminServlet extends javax.servlet.http.HttpServlet {

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
			if (!AdminUtil.getInstance().checkRWRight("DicSubjAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("selectView")) {                 // 상세보기할때
				this.performSelectView(request, response, box, out);
			}
			else if(v_process.equals("insertPage")) {            // 등록페이지로 이동할때
				this.performInsertPage(request, response, box, out);
			}
			else if(v_process.equals("insert")) {                // 등록할때
				this.performInsert(request, response, box, out);
			}
			else if(v_process.equals("updatePage")) {            // 수정페이지로 이동할때
				this.performUpdatePage(request, response, box, out);
			}
			else if(v_process.equals("update")) {                // 수정하여 저장할때
				this.performUpdate(request, response, box, out);
			}
			else if(v_process.equals("delete")) {                // 삭제할때
				this.performDelete(request, response, box, out);
			}
			else if(v_process.equals("select")) {                // 조회할때
				this.performSelectList(request, response, box, out);
			}
			else if(v_process.equals("selectPre")) {            // 조건 검색 전
				this.performSelectPre(request, response, box, out);
			}
			else if(v_process.equals("ExcelUploadPage")) {            // Excel Upload Page
				this.performExcelUploadPage(request, response, box, out);
			}
			else if(v_process.equals("ExcelUploadSave")) {            // Excel Upload SAVE
				this.performExcelUploadSave(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	상세보기
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
			DicSubjAdminBean bean = new DicSubjAdminBean();

			DataBox data = bean.selectViewDic(box);
			request.setAttribute("selectDic", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_DicSubj_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn//admin/course/za_DicSubj_R.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
	등록페이지로 이동할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        // 명시적으로 box 객체를 넘겨준다

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn//admin/course/za_DicSubj_I.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn//admin/course/za_DicSubj_I.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
	등록할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			DicSubjAdminBean bean = new DicSubjAdminBean();
			int isOk = bean.insertDic(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.DicSubjAdminServlet";
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

			Log.info.println(this, box, v_msg + " on DicSubjAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}

	/**
	수정페이지로 이동할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			DicSubjAdminBean bean = new DicSubjAdminBean();
			DataBox data = bean.selectViewDic(box);
			request.setAttribute("selectDic", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn//admin/course/za_DicSubj_U.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn//admin/course/za_DicSubj_U.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}
	}

	/**
	수정하여 저장할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			DicSubjAdminBean bean = new DicSubjAdminBean();
			int isOk = bean.updateDic(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.DicSubjAdminServlet";
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

			Log.info.println(this, box, v_msg + " on DicSubjAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
	삭제할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			DicSubjAdminBean bean = new DicSubjAdminBean();
			int isOk = bean.deleteDic(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.DicSubjAdminServlet";
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

			Log.info.println(this, box, v_msg + " on DicSubjAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}

	/**
	리스트(검색후)
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			DicSubjAdminBean bean = new DicSubjAdminBean();

			ArrayList list = bean.selectListDic(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn//admin/course/za_DicSubj_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn//admin/course/za_DicSubj_L.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
	리스트(검색전)
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performSelectPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			ArrayList list = new ArrayList();
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn//admin/course/za_DicSubj_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn//admin/course/za_DicSubj_L.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
	Excel Upload Page
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performExcelUploadPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/course/za_DicSubjInput_L.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

	/**
	Excel Upload Save
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performExcelUploadSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/course/za_DicSubjInput_P.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
		}
	}
}

