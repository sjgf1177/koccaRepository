//**********************************************************
//  1. 제      목: 검색 제어하는 서블릿
//  2. 프로그램명 : SearchServlet.java
//  3. 개      요: 검색 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 7
//  7. 수      정:
//**********************************************************

package controller.library;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.SearchAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.polity.TaxbillAdminBean;
import com.credu.propose.ProposeWizardBean;
import com.credu.system.MemberData;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.library.SearchServlet")
public class SearchServlet extends javax.servlet.http.HttpServlet {

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

			if(v_process.equals("grcode")) {                      // 그룹검색
				this.performSearchGrcode(request, response, box, out);
			}
			else if(v_process.equals("subj")) {                   // 과정검색
				this.performSearchSubj(request, response, box, out);
			}
			else if(v_process.equals("subjseq")) {                   // 차수검색
				this.performSearchSubjAndSubjseq(request, response, box, out);
			}
            else if(v_process.equals("dept")) {                   // 부서검색
				this.performSearchDept(request, response, box, out);
			}
			else if(v_process.equals("grpcomp")) {                   // 회사검색
				this.performSearchGrpComp(request, response, box, out);
			}
			else if(v_process.equals("grpoutcomp")) {                   // 회사검색
				this.performSearchGrpOutComp(request, response, box, out);
			}
			else if(v_process.equals("member")) {                 // 멤버검색
				this.performSearchMember(request, response, box, out);
			}
			else if(v_process.equals("betacompany")) {			  // 컨텐츠 제작업체 담당자 검색
				this.performSearchBetaCompany (request, response, box, out);
			}
			else if(v_process.equals("company")) {                 // 교육주관처검색
				this.performSearchCompany(request, response, box, out);
			}
			else if(v_process.equals("approval")) {                 // 결재자검색
				this.performSearchApproval(request, response, box, out);
			}
			else if(v_process.equals("memberInfo")) {                 // 회원정보보기
				this.performSelectMemberInfo(request, response, box, out);
			}
			else if(v_process.equals("outMemberInfo")) {                 // 협력업체회원정보보기
				this.performSelectOutMemberInfo(request, response, box, out);
			}
			else if(v_process.equals("appmember")) {                 // 결재상신조회
				this.performSelectAppMemberInfo(request, response, box, out);
			}
			else if(v_process.equals("subjclass")) {                 // 대분류,중분류, 소분류 IFrame
				this.performSearchSubjClass(request, response, box, out);
			}
			else if(v_process.equals("user")) {                         // 회원
				this.performSearchUser(request, response, box, out);
			}
			else if(v_process.equals("taxBill")) {                         // 계산서
				this.performSearchTaxBill(request, response, box, out);
			}
			else if(v_process.equals("tutor")) {                         // 교수, 튜터 검색
				this.performSearchTutor(request, response, box, out);
			}
			else if(v_process.equals("searchComp")) {                 //  회사검색(오프라인 취업관리)
				this.performSearchComp(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
  회사검색(오프라인 취업관리)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchComp(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchComp(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchComp.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
    그룹코드 검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchGrcode(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchGrcode(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchGrcode.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}


	/**
    과정검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchSubj(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchSubj.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

    //'차수 찾기'
    public void performSearchSubjAndSubjseq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchSubjseq(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchSubjseq.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}
	/**
    부서검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchDept(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchDept(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchDept.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}



	/**
    회사검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchGrpComp(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchGrpComp(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchGrpComp.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchGrpComp()\r\n" + ex.getMessage());
		}
	}


	/**
    회사검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchGrpOutComp(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchGrpComp(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchGrpOutComp.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchGrpComp()\r\n" + ex.getMessage());
		}
	}


	/**
    멤버검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchMember(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchMember(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchMember.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchMember()\r\n" + ex.getMessage());
		}
	}

	/**
    멤버검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectAppMemberInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchAppMember(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchAppMember.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchMember()\r\n" + ex.getMessage());
		}
	}

	/**
    멤버검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchBetaCompany(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchBetaCompany(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchBetaCompany.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchMember()\r\n" + ex.getMessage());
		}
	}

	/**
    교육주관처검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchCompany(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchCompany(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchCompany.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchCompany()\r\n" + ex.getMessage());
		}
	}

	/**
    결재자검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchMember(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchApproval.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchApproval()\r\n" + ex.getMessage());
		}
	}


	/**
    회원정보보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectMemberInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			MemberData data = bean.selectPersonalInformation(box);
			request.setAttribute("SelectMemberInfo", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchMemberInfo.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SelectMemberInfo()\r\n" + ex.getMessage());
		}
	}

	/**
    회원정보보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectOutMemberInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			DataBox dbox = bean.selectOutPersonalInformation(box);
			request.setAttribute("SelectMemberInfo", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchOutMemberInfo.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SelectMemberInfo()\r\n" + ex.getMessage());
		}
	}
	/**
    과정 대분류,중분류 보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchSubjClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchSubjClass.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchSubjClass()\r\n" + ex.getMessage());
		}
	}

	/**
    교수/튜터 검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSearchTutor(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SearchAdminBean bean = new SearchAdminBean();

			ArrayList list = bean.searchTutor(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchTutor.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchApproval()\r\n" + ex.getMessage());
		}
	}
	public void performSearchUser(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			ProposeWizardBean bean = new ProposeWizardBean();
			ArrayList list  = bean.SelectMember(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchUser.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchApproval()\r\n" + ex.getMessage());
		}
	}
	public void performSearchTaxBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			TaxbillAdminBean bean = new TaxbillAdminBean();

			ArrayList list = bean.selectSearchTaxBillList(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchTaxBill.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSearchApproval()\r\n" + ex.getMessage());
		}
	}

}

