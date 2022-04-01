//**********************************************************
//  1. 제      목: 과정공지를 제어하는 서블릿
//  2. 프로그램명 : SubjGongAdminServlet.java
//  3. 개      요: 과정공지 페이지을 제어한다
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 20
//  7. 수      정: 조재형
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.SubjGongAdminBean;
import com.credu.course.SubjGongData;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.CodeConfigBean;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.course.SubjGongAdminServlet")
public class SubjGongAdminServlet extends javax.servlet.http.HttpServlet {

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
			if (!AdminUtil.getInstance().checkRWRight("SubjGongAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("selectView")) {                      // 상세보기할때
				this.performSelectView(request, response, box, out);
			}
			else if(v_process.equals("insertPageAll")) {            // 선택된 과정에 대한 동시 등록페이지로 이동할때
				this.performInsertPageAll(request, response, box, out);
			}
            else if(v_process.equals("allEditPageAll")) {            // 전체공지 수정시
				this.performAllEditPageAll(request, response, box, out);
			}
			else if(v_process.equals("insertAll")) {                // 선택된 과정에 대한 동시 등록할때
				this.performInsertAll(request, response, box, out);
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
			else if(v_process.equals("select")) {                // 해당과정에 대한 조회할때
				this.performSelectList(request, response, box, out);
			}
			else if(v_process.equals("selectAll")) {                // 조회할때
				this.performSelectListAll(request, response, box, out);
			}
			else if(v_process.equals("deletePageAll")) {                // 선택 삭제할때
				this.performDeleteListAll(request, response, box, out);
			}
			else if(v_process.equals("selectPre")) {             // 조건 검색 전
				this.performSelectPre(request, response, box, out);
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
			SubjGongAdminBean bean = new SubjGongAdminBean();

			//SubjGongData data = bean.selectViewGong(box);
			//request.setAttribute("selectGong", data);

			DataBox dbox = bean.selectViewGongNewAdmin(box);
			request.setAttribute("selectGong", dbox);

			ServletContext sc    = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGong_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SubjGong_R.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    선택된 과정에 대한 동시 등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPageAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			SubjGongAdminBean bean = new SubjGongAdminBean();
			SubjGongData data = bean.selectGongSample(box);
			request.setAttribute("selectGong", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGongAll_I.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SubjGongAll_I.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

    //전체 공지 수정시
    public void performAllEditPageAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			SubjGongAdminBean bean = new SubjGongAdminBean();
			SubjGongData data = bean.selectGongSample(box);
			request.setAttribute("selectGong", data);

            if(box.get("p_action").equals("titleContent"))       //공지구분 선택시 제목이랑 내용을 가져옴
                bean.selectGongJiTitleAndContent(box);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGongAllEdit.jsp");
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

	/**
    선택된 과정에 대한 동시 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.insertGongAll(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.SubjGongAdminServlet";
			box.put("p_process", "selectAll");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on SubjGongAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
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
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			SubjGongAdminBean bean = new SubjGongAdminBean();
			SubjGongData data = bean.selectGongSample(box);
			request.setAttribute("selectGong", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGong_I.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SubjGong_I.jsp");

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
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.insertGong(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.SubjGongAdminServlet";
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

			Log.info.println(this, box, v_msg + " on SubjGongAdminServlet");
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

			SubjGongAdminBean bean = new SubjGongAdminBean();
			//SubjGongData data = bean.selectViewGong(box);
			//request.setAttribute("selectGong", data);

			DataBox dbox = bean.selectViewGongNewAdmin(box);
			request.setAttribute("selectGong", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGong_U.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SubjGong_U.jsp");

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
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.updateGong(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.SubjGongAdminServlet";
			box.put("p_process", "selectAll");
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

			Log.info.println(this, box, v_msg + " on SubjGongAdminServlet");
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
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.deleteGong(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.SubjGongAdminServlet";
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

			Log.info.println(this, box, v_msg + " on SubjGongAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}

	/**
    해당과정에 대한 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SubjGongAdminBean bean = new SubjGongAdminBean();
			box.put("p_admin", "1");  // 운영자페이지
			ArrayList list = bean.selectListGong(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGong_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SubjGong_L.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
	public void performSelectListAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SubjGongAdminBean bean = new SubjGongAdminBean();

			int typescnt = CodeConfigBean.getCodeCnt("noticeCategory","",1);
			box.put("p_typescnt", String.valueOf(typescnt));

			ArrayList list1 = CodeConfigBean.getCodeList("noticeCategory","",1);
			request.setAttribute("selectList1", list1);

			ArrayList list2 = bean.selectListGongAll(box);
			request.setAttribute("selectList2", list2);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGongAll_L2.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SubjGongAll_L2.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
    선택 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performDeleteListAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.deleteAllGong(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.SubjGongAdminServlet";
			box.put("p_process", "selectAll");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on SubjGongAdminServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
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

			int typescnt = CodeConfigBean.getCodeCnt("noticeCategory","",1);
			box.put("p_typescnt", String.valueOf(typescnt));

			ArrayList list1 = CodeConfigBean.getCodeList("noticeCategory","",1);
			request.setAttribute("selectList1", list1);

			ArrayList list2 = new ArrayList();
			
			
			/** 조회되는 소스없어서 추가함 왜없었는지 이유 모르겠음 2016.05.27 **/
			if(box.getString("p_action").equals("go")){
				SubjGongAdminBean bean = new SubjGongAdminBean();
				list2 = bean.selectListGongAll(box);
			}
			/** 조회되는 소스없어서 추가함 왜없었는지 이유 모르겠음 2016.05.27 **/
			
			
			request.setAttribute("selectList2", list2);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjGongAll_L2.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SubjGongAll_L2.jsp");

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectPre()\r\n" + ex.getMessage());
		}
	}

}

