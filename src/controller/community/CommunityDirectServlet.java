//**********************************************************
//  1. 제      목: 커뮤니티 공지사항을 제어하는 서블릿
//  2. 프로그램명 : HomePageBoardServlet.java
//  3. 개      요: 커뮤니티의 공지사항 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.7.1
//  7. 수      정: 2005.7.1
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityQnABean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings({ "unchecked", "serial" })
public class CommunityDirectServlet extends javax.servlet.http.HttpServlet {

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
		PrintWriter      out          = null;
		RequestBox       box          = null;
		String           v_process    = "";

		try {
			response.setContentType("text/html;charset=euc-kr");

			out       = response.getWriter();
			box       = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process","selectlist");

			if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			//if (!AdminUtil.getInstance().checkLogin(out, box)) {
			// return;
			//}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			/////////////////////////////////////////////////////////////////////////////
			if(v_process.equals("selectList")) {          //  리스트 페이지로 이동할때
				this.performListPage(request, response, box, out);
			} else if(v_process.equals("viewPage")) {           //  보기
				this.performViewPage(request, response, box, out);

			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    리스트페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			CommunityQnABean bean = new CommunityQnABean();
			ArrayList list = bean.selectListQna(box);

			request.setAttribute("list", list);



			//사이트 회원전체중 로그인한회원정보
			//CommunityIndexBean beanAllMem = new CommunityIndexBean();
			//ArrayList listbeanAllMem = beanAllMem.selectTz_Member(box);
			//request.setAttribute("listAllUser", listbeanAllMem);


			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuDirect_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuDirect_L.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
    뷰페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			CommunityQnABean bean = new CommunityQnABean();
			ArrayList list = bean.selectViewQna(box,"VIEW");

			request.setAttribute("list", list);

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuDirect_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuDirect_R.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performViewPage()\r\n" + ex.getMessage());
		}
	}

}

