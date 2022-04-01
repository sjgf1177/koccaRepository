//*********************************************************
//  1. 제      목: SUBJECT INFORMATION USER SERVLET
//  2. 프로그램명: ProposeCourseServlet.java
//  3. 개      요: 과정안내 사용자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 19
//  7. 수      정:
//**********************************************************
package controller.off;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.ProposeOffBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({"unchecked", "serial"})
@WebServlet("/servlet/controller.off.ProposeOffServlet")
public class ProposeOffServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
			
			if (box.getSession("tem_grcode") == "") {        		
	             box.setSession("tem_grcode","N000001");
	     	}	

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
			if (v_process.equals("SubjectCreduPropose")) {
				String creduuserid = box.getString("p_userid");
				box.setSession("userid", creduuserid);
			}

			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			if(!(v_process.equals("SubjectList")||v_process.equals("SubjectPreviewPage")||v_process.equals("TotalSubjectList"))) {
				if (!AdminUtil.getInstance().checkLogin(out, box)) {
					this.LoginChk(request, response, box, out);
				}
			}

			if(v_process.equals("SubjectList")){                           // 과정리스트
				this.performSubjectList(request, response, box, out);
			}
			else if(v_process.equals("SubjectPreviewPage")){               // 과정 미리보기
				this.performSubjectPreviewPage(request, response, box, out);
			}
			else if(v_process.equals("SubjectEduProposePage")){            // 수강신청
				this.performSubjectEduProposePage(request, response, box, out);
			}
			else if(v_process.equals("SubjectEduPropose")){                // 수강신청
				this.performSubjectEduPropose(request, response, box, out);
			}
			else if(v_process.equals("UpdateSubjectEduProposePage")){                // 수강신청 추가정보 수정 팝업
				this.performUpdateSubjectEduProposePage(request, response, box, out);
			}
			else if(v_process.equals("UpdateSubjectEduPropose")){                // 수강신청 추가정보 수정
				this.performUpdateSubjectEduPropose(request, response, box, out);
			}
			else if(v_process.equals("TotalSubjectList")){                 // 전체과정보기
				this.performTotalSubjectList(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    SUBJECT LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void LoginChk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{

			request.setAttribute("tUrl",request.getRequestURI());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request,response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectList()\r\n" + ex.getMessage());
		}
	}

	/**
    수강 신청
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSubjectEduProposePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			MemberInfoBean bean  = new MemberInfoBean();

			box.put("onOff", 2);
			request.setAttribute("resultbox", bean.memberInfoViewNew(box));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/propose/zu_OffSubject_I.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
		}
	}

	/**
    수강 신청
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			ProposeOffBean bean = new ProposeOffBean();
			String v_msg = "";
			String v_url ="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage";
			box.put("onOff", 2);
			box.put("p_upperclass", "ALL");

			MemberInfoBean mbean = new MemberInfoBean();

			int isOk = mbean.memberInfoUpdateNew(box);
			isOk = bean.insertSubjectEduPropose(box);
			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "offpropose.ok";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			}
			else if(isOk == -2){
				v_msg = "propose.fail";
				v_msg = box.getStringDefault("err_msg",v_msg);
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			}
			else {
				v_msg = "propose.fail";
				v_msg = box.getStringDefault("err_msg",v_msg);
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
		}
	}


    /**
    수강 신청 추가정보 수정 팝업
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdateSubjectEduProposePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			MemberInfoBean bean  = new MemberInfoBean();

			box.put("onOff", 2);
			request.setAttribute("resultbox", bean.memberInfoViewNew(box));
			request.setAttribute("offApplyInfo", bean.selectOffApplyInfo(box));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/propose/zu_OffSubject_U.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("updateSubjectEduProposePage()\r\n" + ex.getMessage());
		}
	}

    /**
    수강 신청 추가정보 수정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdateSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			ProposeOffBean bean = new ProposeOffBean();
			String v_msg = "";
			String v_url ="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage";
			box.put("onOff", 2);

			MemberInfoBean mbean = new MemberInfoBean();

			int isOk = mbean.memberInfoUpdateNew(box);
			isOk = bean.updateSubjectEduPropose(box);
			AlertManager alert = new AlertManager();

			if(isOk >= 0) {
				v_msg = "offpropose.ok";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			}
			else {
				v_msg = "propose.fail";
				v_msg = box.getStringDefault("err_msg",v_msg);
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("updateSubjectEduPropose()\r\n" + ex.getMessage());
		}
	}

	/**
    과정 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{
			request.setAttribute("requestbox", box);
			
			String v_url = "";
			if(box.getSession("tem_grcode").equals("N000001")) {
				//v_url = "/learn/user/2012/portal/propose/zu_OffSubject_L.jsp";
				v_url = "/learn/user/2013/portal/propose/zu_OffSubject_L.jsp";
			} else {
				v_url = "/learn/user/portal/propose/zu_OffSubject_L.jsp";
			}

			ProposeOffBean bean = new ProposeOffBean();
			request.setAttribute("SubjectList", bean.selectSubjectList(box));
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectList()\r\n" + ex.getMessage());
		}
	}

	/**
    과정 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSubjectPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			ProposeOffBean bean  = new ProposeOffBean();
			
			String v_url = "";
			
			if(box.getSession("tem_grcode").equals("N000001")) {
				//v_url = "/learn/user/2012/portal/propose/zu_OffSubject_R.jsp";
				v_url = "/learn/user/2013/portal/propose/zu_OffSubject_R.jsp";
			} else {
				v_url = "/learn/user/portal/propose/zu_OffSubject_R.jsp";
			}
			
			// 과정상세, 차수 상세내용
			DataBox dbox  = bean.selectSubjectPreview(box);
			request.setAttribute("subjectPreview", dbox);

			//request.setAttribute("tutorList", bean.selectTutorList(box));
			
			// 수강신청 여부 확인
			String isCheck = bean.selectCheckedPropose(box);
			box.put("isCheckedPropose", isCheck);

			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);

			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
		}
	}

	/**
    오프라인 전체과정 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performTotalSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{
			request.setAttribute("requestbox", box);
			String v_url = "/learn/user/portal/offline/zu_TotalSubject_L.jsp";

			// 과정리스트
			ProposeOffBean bean = new ProposeOffBean();
			request.setAttribute("totalSubjectList", bean.selectTotalSubjectList(box));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
		}
	}
}