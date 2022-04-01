//**********************************************************
//  1. 제      목: 커뮤니티 회원가입을 제어하는 서블릿
//  2. 프로그램명 : CommunityFrJoinServlet.java
//  3. 개      요: 커뮤니티의 회원가입 페이지을 제어한다
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

import com.credu.community.CommunityCategoryResultBean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings({ "unchecked", "serial" })
public class CommunityCategoryResultServlet extends javax.servlet.http.HttpServlet {

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
			v_process = box.getStringDefault("p_process","movePage");

			if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			//if (!AdminUtil.getInstance().checkLogin(out, box)) {
			// return;
			//}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			/////////////////////////////////////////////////////////////////////////////
			if(v_process.equals("movePage")) {                                //  등록 페이지로 이동할때
				this.performmoCrResultPage(request, response, box, out);
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
	public void performmoCrResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			CommunityCategoryResultBean bean = new CommunityCategoryResultBean();
			ArrayList selectList = bean.selectCateGoryList(box);

			request.setAttribute("selectList", selectList);

			ArrayList groupList = bean.selectCateGoryGroup(box);

			request.setAttribute("groupList", groupList);



			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuCategoryResult_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuCategoryResult_L.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performmoCrResultPage()\r\n" + ex.getMessage());
		}
	}




}

