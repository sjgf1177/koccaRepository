//**********************************************************
//1. 제      목: 나의 질문방
//2. 프로그램명: MyQnaServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정은년
//7. 수      정:
//
//**********************************************************

package controller.study;

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
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.MyQnaBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.study.MyQnaServlet")
public class MyQnaServlet extends HttpServlet implements Serializable {
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
		RequestBox  box = null;
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

			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return;
			}

			/*
            if(box.getSession("userid").equals("")){
            	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }
			 */

			if (v_process.equals("MyQnaStudyListPage")) {                            // 나의 질문방 학습관련 리스트
				this.performMyQnaStudyListPage(request, response, box, out);
			} else if (v_process.equals("MyQnaSiteListPage")) {                      // 나의 질문방 사이트관련 리스트
				this.performMyQnaSiteListPage(request, response, box, out);
			} else if (v_process.equals("MyQnaStudyViewPage")) {                      // 나의 질문방 학습관련 상세보기
				this.performMyQnaStudyViewPage(request, response, box, out);
			} else if (v_process.equals("MyQnaSiteViewPage")) {                      // 나의 질문방 사이트관련 상세보기
				this.performMyQnaSiteViewPage(request, response, box, out);
			} else if (v_process.equals("MyQnaCounselListPage")) {                   // 나의 상담내역 리스트
				this.performMyQnaCounselListPage(request, response, box, out);
			} else if (v_process.equals("MyQnaCounselQnaViewPage")) {                // 나의상담내역 과정 QnA 상세보기
				this.performMyQnaCounselQnaViewPage(request, response, box, out);
			} else if (v_process.equals("MyQnaCounselHomeQnaViewPage")) {                // 나의상담내역 홈페이지 QnA 상세보기
				this.performMyQnaCounselHomeQnaViewPage(request, response, box, out);
			}


		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    나의 질문방 학습관련 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performMyQnaStudyListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_url = "/learn/user/game/study/gu_MyQnaStudy_L.jsp";

			MyQnaBean bean = new MyQnaBean();
			ArrayList list1 = bean.SelectMyQnaStudyList(box); // 학습관련
			request.setAttribute("MyQnaStudyListPage", list1);

			ServletContext sc    = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
			Log.info.println(this, box, v_url);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performMyQnaStudyListPage()\r\n" + ex.getMessage());
		}
	}

	/**
    나의 질문방 사이트관련 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performMyQnaSiteListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);

			String v_url = "/learn/user/game/study/gu_MyQnaSite_L.jsp";

			MyQnaBean bean = new MyQnaBean();
			ArrayList list2 = bean.SelectMyQnaSiteList(box);

			request.setAttribute("MyQnaSiteListPage", list2);
			ServletContext sc    = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
			Log.info.println(this, box, v_url);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performMyQnaSiteListPage()\r\n" + ex.getMessage());
		}
	}

	/**
    나의 질문방 학습관련 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performMyQnaStudyViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

			MyQnaBean bean = new MyQnaBean();
			DataBox dbox = bean.selectMyQnaStudy(box);

			request.setAttribute("MyQnaStudyViewPage", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/study/gu_MyQnaStudy_R.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    나의 질문방 사이트관련 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performMyQnaSiteViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

			MyQnaBean bean = new MyQnaBean();
			DataBox dbox = bean.selectMyQnaSite(box);

			request.setAttribute("MyQnaSiteViewPage", dbox);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/study/gu_MyQnaSite_R.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    나의 상담내역 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	@SuppressWarnings("rawtypes")
	public void performMyQnaCounselListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/study/gu_MyQnaCounsel_L.jsp";
            	v_url = "/learn/user/2013/portal/study/gu_MyQnaCounsel_L.jsp";
            } else {
            	v_url = "/learn/user/portal/study/gu_MyQnaCounsel_L.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/study/gu_MyQnaCounsel_L.jsp";
            }
            
			MyQnaBean bean = new MyQnaBean();
			ArrayList list2 = bean.SelectMyQnaCounselList(box);

			request.setAttribute("MyQnaCounselListPage", list2);
			request.setAttribute("requestbox", box);
			ServletContext sc    = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
			Log.info.println(this, box, v_url);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performMyQnaCounselListPage()\r\n" + ex.getMessage());
		}
	}

	/**
    나의 상담내역 과정 QnA 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performMyQnaCounselQnaViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			
			String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/study/gu_MyQnaCounselQna_R.jsp";
            	v_url = "/learn/user/2013/portal/study/gu_MyQnaCounselQna_R.jsp";
            } else {
            	v_url = "/learn/user/portal/study/gu_MyQnaCounselQna_R.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/study/gu_MyQnaCounselQna_R.jsp";
            }

			MyQnaBean bean = new MyQnaBean();
			DataBox dbox = bean.selectMyQnaCounselQna(box);
			request.setAttribute("selectMyQnaCounselQna", dbox);

			ArrayList list = bean.selectMyQnaCounselQnaListA(box);
			request.setAttribute("selectMyQnaCounselQnaListA", list);
			

			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    나의 상담내역 과정 홈페이지 QnA 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	@SuppressWarnings("rawtypes")
	public void performMyQnaCounselHomeQnaViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			String v_url  = "";
			MyQnaBean bean = new MyQnaBean();
			
			DataBox dbox = bean.selectQna(box);
			request.setAttribute("selectQna", dbox);
			
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/study/gu_MyQnaCounselHomeQna_R.jsp";
            	v_url = "/learn/user/2013/portal/study/gu_MyQnaCounselHomeQna_R.jsp";
            	if(dbox.getString("d_okyn1").equals("3")){
            		ArrayList dbox2 = bean.selectHomeAns(box);
    				request.setAttribute("selectAns", dbox2);
    			}
            } else {
            	v_url = "/learn/user/portal/study/gu_MyQnaCounselHomeQna_R.jsp";
            	if(dbox.getString("d_okyn1").equals("3")){
    				DataBox dbox2 = bean.selectAns(box);
    				request.setAttribute("selectAns", dbox2);
    			}
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/study/gu_MyQnaCounselHomeQna_R.jsp";
            }

			//ArrayList list = bean.selectcommentListQna(box);
			//request.setAttribute("selecCommentList", list);

            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
			Log.info.println(this, box, "Dispatch to " + v_url);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    나의 상담내역 과정 홈페이지 QnA 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performMyQnaCounselHomeQnaViewPage2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

			MyQnaBean bean = new MyQnaBean();
			DataBox dbox = bean.selectMyQnaCounselHomeQna(box);
			request.setAttribute("selectMyQnaCounselHomeQna", dbox);

			ArrayList list = bean.selectMyQnaCounselHomeQnaListA(box);
			request.setAttribute("selectMyQnaCounselHomeQnaListA", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/gu_MyQnaCounselHomeQna_R.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

}