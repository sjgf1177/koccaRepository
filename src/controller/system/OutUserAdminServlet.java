//**********************************************************
//  1. 제      목: 사외이용자 관련 서블릿
//  2. 프로그램명 : OutUserAdminServlet.java
//  3. 개      요: 사외이용자 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2004. 12. 24
//  7. 수      정: 신선철 2004. 12. 24
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
 *사외이용자 관리
 *<p>제목:OutUserAdminServlet.java</p>
 *<p>설명:사외이용자 관리 서블릿</p>
 *<p>Copyright: Copright(c)2004</p>
 *<p>Company: VLC</p>
 *@author 이창훈
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
			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			System.out.println("============= 123 : " + v_process + " // " + out + " // " + box);
			if (!AdminUtil.getInstance().checkRWRight("OutUserAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("select")) {       //    업체 리스트 조회
				this.performSelect(request, response, box, out);
			}
			else if(v_process.equals("insertPage")) {       //  업체 등록페이지로 이동할때
				this.performInsertPage(request, response, box, out);
			}
			else if(v_process.equals("insert")) {       //  업체 등록할때
				this.performInsert(request, response, box, out);
			}
			else if(v_process.equals("selectPage")) {       //  업체 수정페이지로 이동하여 뿌려줄때
				this.performSelectPage(request, response, box, out);
			}
			else if(v_process.equals("update")) {     //      업체 수정하여 저장할때
				this.performUpdate(request, response, box, out);
			}
			else if(v_process.equals("delete")) {     //    업체 삭제할때
				this.performDelete(request, response, box, out);
			}
			else if(v_process.equals("userList")) {   //회원검색
				this.performUserList(request, response, box, out);
			}
			else if(v_process.equals("usercheck")) {   //아이디 체크
				this.performCheck(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    권한 에러처리
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void errorPage(RequestBox box, PrintWriter out)
	throws Exception {
		try {
			box.put("p_process", "");

			AlertManager alert = new AlertManager();

			alert.alertFailMessage(out, "이 프로세스로 진행할 권한이 없습니다.");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("errorPage()\r\n" + ex.getMessage());
		}
	}


	/**
    외주업체 담당자 가입 사번체크
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
    업체 삭제
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
				v_msg = "삭제되었습니다!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "삭제 에러!";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}


	/**
    업체 등록
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
			box.put("p_process", "select"); //회사등록 후 업체리스트 화면으로 이동

			AlertManager alert = new AlertManager();

			OutUserAdminBean outuser = new OutUserAdminBean();

			int isOk = outuser.insertoutcomp(box);


			if(isOk > 0) {
				v_msg = "저장하였습니다!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "저장 에러!";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}


	/**
    업체 등록페이지로 이동
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
    업체 리스트 조회
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
				v_return_url = "/learn/admin/off/za_excel.jsp";//필수
				box.put("title", "업체관리");//엑셀 제목
				box.put("tname", "회사코드|회사명|대표이름|담당자명|담당자ID|사업자등록번호|대표전화번호|팩스번호|우편번호1|우편번호2|사업장주소1|사업장주소2|홈페이지");//컬럼명
				box.put("tcode", "comp|compnm|coname|name|userid|compresno|telno|faxno|zip1|zip2|compaddr1|compaddr2|homepage");//데이터이름
				//				head_sql  = " select comp, compnm, compresno, coname, telno, faxno,\n";
				//				head_sql += "        addr compaddr1, addr2 compaddr2, zip1, zip2, gubun, homepage,ldate,\n";
				//				head_sql += "        userid, name, resno, pwd, hometel, handphone, comptel,\n";
				//				head_sql += "        email, post1, post2, addr, addr2, comptext\n";
				//				box.put("bgcolumn", "TSTEP|MTEST|HTEST|FTEST|REPORT|ETC1|ETC2");
				box.put("resultListName", "selectoutcomp");//결과 목록
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
    업체 수정페이지 조회
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
    업체 수정
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
			//      수정 후 업체리스트 화면으로 이동
			box.put("p_process", "select");


			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "저장하였습니다!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "저장 에러!";
				alert.alertFailMessage(out, v_msg);
			}

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
    회원검색
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
