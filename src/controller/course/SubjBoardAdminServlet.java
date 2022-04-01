//**********************************************************
//  1. 제      목: 과정 게시판
//  2. 프로그램명 : SubjBoardAdminServlet
//  3. 개      요: 과정 게시판 페이지을 제어한다
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

import com.credu.common.SubjBoardAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.course.SubjBoardAdminServlet")
public class SubjBoardAdminServlet extends javax.servlet.http.HttpServlet {

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

			if(v_process.length()==0) v_process = "selectList";
			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("SubjBoardAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
			if(v_process.equals("insertPage")) {          //  등록페이지로 이동할때
				this.performInsertPage(request, response, box, out);
			}
			else if(v_process.equals("insert")) {         //  등록할때
				this.performInsert(request, response, box, out);
			}
			else if(v_process.equals("updatePage")) {     //  수정페이지로 이동할때
				this.performUpdatePage(request, response, box, out);
			}
			else if(v_process.equals("update")) {         //  수정하여 저장할때
				this.performUpdate(request, response, box, out);
			}
			if(v_process.equals("replyPage")) {          //  답변페이지로 이동할때
				this.performReplyPage(request, response, box, out);
			}
			else if(v_process.equals("reply")) {         //  답변할때
				this.performReply(request, response, box, out);
			}
			else if(v_process.equals("delete")) {         //  삭제할때
				this.performDelete(request, response, box, out);
			}
			else if(v_process.equals("replySubmit")) {    //  리플등록 : OK
				this.performInsertReply(request, response, box, out);
			}
			else if(v_process.equals("replyDelete")) {    //  리플삭제 : OK
				this.performDeleteReply(request, response, box, out);
			}
			else if(v_process.equals("select")) {         //  상세보기할때 : OK
				this.performSelect(request, response, box, out);
			}
			else {                                        //  조회할때 : OK
				this.performSelectList(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			SubjBoardAdminBean bean = new SubjBoardAdminBean();
			/*------- 게시판 분류에 대한 부분 세팅 -----*/
			box.put("s_subjcourse", box.getString("s_subjcourse"));
			box.put("s_gyear", box.getString("s_gyear"));
			box.put("s_grseq", box.getString("s_grseq"));
			/*----------------------------------------*/

			ArrayList list = bean.selectBoardList(box);
			request.setAttribute("selectStudyBoardList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SubjBoardAll_L.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
	public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			DataBox dbox = bean.selectBoard(box);
			request.setAttribute("selectStudyBoard", dbox);

			ArrayList list = bean.selectBoardReplyList(box);
			request.setAttribute("selectBoardReplyList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/za_SubjBoardAll_R.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelect()\r\n" + ex.getMessage());
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

			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			box.sync("p_subj", "s_subjcourse");
			box.sync("p_year", "s_gyear");
			box.sync("p_subjseq", "s_subjseq");
			box.put("p_tabseq", bean.selectSBTableseq(box));
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBoardAll_I.jsp");
			rd.forward(request, response);

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
			/* ================ 마일리지 등록 시작  ============================*/
			//            String v_code   = "00000000000000000001";                      // 게시판 글 올리기 마일리지코드
			//            String s_userid = box.getSession("userid");
			//            int isOk3 = MileageManager.insertMileage(v_code, s_userid);    // 마일리지 작성
			/* ================ 마일리지 등록 시작  ============================*/

			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			int isOk = bean.insertStudyBoard(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.SubjBoardAdminServlet";
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}
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

			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			DataBox dbox = bean.selectBoard(box);
			request.setAttribute("selectStudyBoard", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBoardAll_U.jsp");
			rd.forward(request, response);

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
			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			int isOk = bean.updateBoard(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.SubjBoardAdminServlet";
			box.put("p_process", "");
			//      수정 후 해당 리스트 페이지로 돌아가기 위해

			AlertManager alert = new AlertManager();
			//System.out.println("update"+isOk);
			if(isOk > 0) {
				v_msg = "update.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "update.fail";
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}


	/**
    답변페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			SubjBoardAdminBean bean = new SubjBoardAdminBean();
			DataBox dbox = bean.selectBoard(box);
			request.setAttribute("selectStudyBoard", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBoardAll_A.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performReplyPage()\r\n" + ex.getMessage());
		}
	}

	/**
    답변할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			int isOk = bean.replyBoard(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.SubjBoardAdminServlet";
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "reply.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "reply.fail";
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performReply()\r\n" + ex.getMessage());
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
			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			int isOk = bean.deleteBoard(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.SubjBoardAdminServlet";
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
	 * 리플 등록
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performInsertReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {

			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			int isOk = bean.insertReply(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.SubjBoardAdminServlet";
			box.put("p_process", "select");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertReply()\r\n" + ex.getMessage());
		}
	}

	/**
	 * 리플 삭제
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performDeleteReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {

			SubjBoardAdminBean bean = new SubjBoardAdminBean();

			int isOk = bean.deleteReply(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.SubjBoardAdminServlet";
			box.put("p_process", "select");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else {
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertReply()\r\n" + ex.getMessage());
		}
	}

}

