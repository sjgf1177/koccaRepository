//**********************************************************
//1. 제      목: 과정소개 SERVLET
//2. 프로그램명: CourseIntroServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: anonymous 2003. 07. 15

//7. 수      정:
//
//**********************************************************
package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeCourseBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.course.CourseIntroServlet")
public class CourseIntroServlet extends HttpServlet implements Serializable {

	/**
	 * Pass get requests through to PerformTask
	 * 
	 * @param request
	 *            encapsulates the request to the servlet
	 * @param response
	 *            encapsulates the response from the servlet
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

	/**
	 * doPost
	 * 
	 * @param request
	 *            encapsulates the request to the servlet
	 * @param response
	 *            encapsulates the response from the servlet
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process", "SubjectList");

			box.put("p_grgubun", "G01");

			if (ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
			// System.out.print("v_process=============>"+v_process);
			// /////////////////////////////////////////////////////////////////
			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			/*
			 * if (!AdminUtil.getInstance().checkLogin(out, box)) { return; }
			 */
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			// /////////////////////////////////////////////////////////////////

			if (v_process.equals("SubjectList")) { // 과정리스트
				this.performSubjectList(request, response, box, out);
			} else if (v_process.equals("SubjectPreviewPage")) { // 과정 미리보기
				this.performSubjectPreviewPage(request, response, box, out);
			} else if (v_process.equals("SubjectPage")) { // 학습위계
				this.performSubjectIntro(request, response, box, out);
			} else if (v_process.equals("KyowonSubPage")) { // 교원 연수안내
				this.performKyowonSub(request, response, box, out);
			}
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	 * 과정 리스트
	 * 
	 * @param request
	 *            encapsulates the request to the servlet
	 * @param response
	 *            encapsulates the response from the servlet
	 * @param box
	 *            receive from the form object
	 * @param out
	 *            printwriter object
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void performSubjectList(HttpServletRequest request,
			HttpServletResponse response, RequestBox box, PrintWriter out)
			throws Exception {
		try {
			request.setAttribute("requestbox", box);

			ProposeCourseBean bean = new ProposeCourseBean();
			ArrayList list1 = null;
			// ArrayList list2 = null;
			String v_url = "";
			String v_iscourseYn = box.getStringDefault("p_iscourseYn", "N");

			// String firststr = "" ;
			// ConfigSet conf = new ConfigSet();

			if (v_iscourseYn.equals("N")) {
				v_url = "/learn/user/game/course/gu_CourseIntro_L.jsp";
				// 과정리스트
				list1 = bean.selectCourseIntroList(box);
				request.setAttribute("SubjectList", list1);
			} else if (v_iscourseYn.equals("Y")) {
				v_url = "/learn/user/game/course/gu_CourseIntroOFF_L.jsp";
				list1 = bean.selectCourseIntroList2(box);
				request.setAttribute("SubjectList", list1);
			}
			System.out.println(v_url);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectList()\r\n" + ex.getMessage());
		}
	}

	/**
	 * 과정 미리보기
	 * 
	 * @param request
	 *            encapsulates the request to the servlet
	 * @param response
	 *            encapsulates the response from the servlet
	 * @param box
	 *            receive from the form object
	 * @param out
	 *            printwriter object
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void performSubjectPreviewPage(HttpServletRequest request,
			HttpServletResponse response, RequestBox box, PrintWriter out)
			throws Exception {
		try {
			request.setAttribute("requestbox", box);
			ProposeCourseBean bean = new ProposeCourseBean();

			// 선수과정 리스트
			// ArrayList list1 = bean.selectListPre(box);
			// request.setAttribute("subjectPre", list1);
			// 후수과정 리스트
			// ArrayList list2 = bean.selectListNext(box);
			// request.setAttribute("subjectNext", list2);

			String v_select = box.getStringDefault("p_select", "ON");
			String v_url = "";

			// 과정상세정보 리스트
			DataBox dbox = bean.selectSubjectPreview(box);
			request.setAttribute("subjectPreview", dbox);
			// String place = dbox.getString("d_place");

			// 과정차수 리스트
			ArrayList list1 = bean.selectSubjSeqList(box);
			request.setAttribute("subjseqList", list1);

			if (v_select.equals("ON")) { // 사이버 과정인 경우 일차리스트
				ArrayList list2 = bean.selectLessonList(box);
				request.setAttribute("lessonList", list2);
				v_url = "/learn/user/game/course/gu_CourseIntroPreviewON.jsp";
			} else if (v_select.equals("OFF")) { // 집합 과정인 경우 강좌리스트
				ArrayList list3 = bean.selectLectureList(box);
				request.setAttribute("lectureList", list3);
				v_url = "/learn/user/game/course/gu_CourseIntroPreviewOFF.jsp";
			}
			// System.out.print("v_url=============>"+v_url);
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);

			rd.forward(request, response);
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
		}
	}

	/**
	 * 학습위계
	 * 
	 * @param request
	 *            encapsulates the request to the servlet
	 * @param response
	 *            encapsulates the response from the servlet
	 * @param box
	 *            receive from the form object
	 * @param out
	 *            printwriter object
	 * @return void
	 */
	public void performSubjectIntro(HttpServletRequest request,
			HttpServletResponse response, RequestBox box, PrintWriter out)
			throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_url = "";

			String v_gubun = box.getStringDefault("p_gubun", "1");

			if (v_gubun.equals("1"))
				v_url = "/learn/user/game/course/gu_subjectIntro1.jsp";
			if (v_gubun.equals("2"))
				v_url = "/learn/user/game/course/gu_subjectIntro2.jsp";
			if (v_gubun.equals("3"))
				v_url = "/learn/user/game/course/gu_subjectIntro3.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
		}
	}

	/**
	 * 교원연수 서브페이지
	 * 
	 * @param request
	 *            encapsulates the request to the servlet
	 * @param response
	 *            encapsulates the response from the servlet
	 * @param box
	 *            receive from the form object
	 * @param out
	 *            printwriter object
	 * @return void
	 */
	public void performKyowonSub(HttpServletRequest request,
			HttpServletResponse response, RequestBox box, PrintWriter out)
			throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_url = "";

			String v_gubun = box.getStringDefault("p_gubun", "1");

			if (v_gubun.equals("1"))
				v_url = "/learn/user/game/kyowon/kyowon_sub1.jsp";
			if (v_gubun.equals("2"))
				v_url = "/learn/user/game/kyowon/kyowon_sub2.jsp";
			if (v_gubun.equals("3"))
				v_url = "/learn/user/game/kyowon/kyowon_sub3.jsp";
			if (v_gubun.equals("4"))
				v_url = "/learn/user/game/kyowon/kyowon_sub4.jsp";
			if (v_gubun.equals("5"))
				v_url = "/learn/user/game/kyowon/kyowon_sub5.jsp";
			if (v_gubun.equals("6"))
				v_url = "/learn/user/game/kyowon/kyowon_sub6.jsp";
			if (v_gubun.equals("7"))
				v_url = "/learn/user/game/kyowon/kyowon_sub7.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
		}
	}

}
