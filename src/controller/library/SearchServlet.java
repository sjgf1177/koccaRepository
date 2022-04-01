//**********************************************************
//  1. ��      ��: �˻� �����ϴ� ����
//  2. ���α׷��� : SearchServlet.java
//  3. ��      ��: �˻� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 7
//  7. ��      ��:
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

			if(v_process.equals("grcode")) {                      // �׷�˻�
				this.performSearchGrcode(request, response, box, out);
			}
			else if(v_process.equals("subj")) {                   // �����˻�
				this.performSearchSubj(request, response, box, out);
			}
			else if(v_process.equals("subjseq")) {                   // �����˻�
				this.performSearchSubjAndSubjseq(request, response, box, out);
			}
            else if(v_process.equals("dept")) {                   // �μ��˻�
				this.performSearchDept(request, response, box, out);
			}
			else if(v_process.equals("grpcomp")) {                   // ȸ��˻�
				this.performSearchGrpComp(request, response, box, out);
			}
			else if(v_process.equals("grpoutcomp")) {                   // ȸ��˻�
				this.performSearchGrpOutComp(request, response, box, out);
			}
			else if(v_process.equals("member")) {                 // ����˻�
				this.performSearchMember(request, response, box, out);
			}
			else if(v_process.equals("betacompany")) {			  // ������ ���۾�ü ����� �˻�
				this.performSearchBetaCompany (request, response, box, out);
			}
			else if(v_process.equals("company")) {                 // �����ְ�ó�˻�
				this.performSearchCompany(request, response, box, out);
			}
			else if(v_process.equals("approval")) {                 // �����ڰ˻�
				this.performSearchApproval(request, response, box, out);
			}
			else if(v_process.equals("memberInfo")) {                 // ȸ����������
				this.performSelectMemberInfo(request, response, box, out);
			}
			else if(v_process.equals("outMemberInfo")) {                 // ���¾�üȸ����������
				this.performSelectOutMemberInfo(request, response, box, out);
			}
			else if(v_process.equals("appmember")) {                 // ��������ȸ
				this.performSelectAppMemberInfo(request, response, box, out);
			}
			else if(v_process.equals("subjclass")) {                 // ��з�,�ߺз�, �Һз� IFrame
				this.performSearchSubjClass(request, response, box, out);
			}
			else if(v_process.equals("user")) {                         // ȸ��
				this.performSearchUser(request, response, box, out);
			}
			else if(v_process.equals("taxBill")) {                         // ��꼭
				this.performSearchTaxBill(request, response, box, out);
			}
			else if(v_process.equals("tutor")) {                         // ����, Ʃ�� �˻�
				this.performSearchTutor(request, response, box, out);
			}
			else if(v_process.equals("searchComp")) {                 //  ȸ��˻�(�������� �������)
				this.performSearchComp(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
  ȸ��˻�(�������� �������)
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
    �׷��ڵ� �˻�
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
    �����˻�
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

    //'���� ã��'
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
    �μ��˻�
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
    ȸ��˻�
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
    ȸ��˻�
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
    ����˻�
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
    ����˻�
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
    ����˻�
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
    �����ְ�ó�˻�
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
    �����ڰ˻�
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
    ȸ����������
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
    ȸ����������
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
    ���� ��з�,�ߺз� ����
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
    ����/Ʃ�� �˻�
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

