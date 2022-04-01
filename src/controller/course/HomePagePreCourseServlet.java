//**********************************************************
//1. 제      목: 홈페이지 예정개설과정 제어 서블릿
//2. 프로그램명: HomePagePreCourseServlet.java
//3. 개      요: 홈페이지 예정개설과정 제어 서블릿
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 2005.12.13
//7. 수      정: 
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.HomePagePreCourseBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.course.HomePagePreCourseServlet")
public class HomePagePreCourseServlet extends javax.servlet.http.HttpServlet {

/**
* DoGet
* Pass get requests through to PerformTask
*/
public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
  this.doPost(request, response);
}
public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
  PrintWriter      out          = null;
  MultipartRequest multi        = null;
  RequestBox       box          = null;
  String           v_process    = "";
  int              fileupstatus = 0;

  try {
      response.setContentType("text/html;charset=euc-kr");
      String path = request.getServletPath();

      out       = response.getWriter();
      box       = RequestManager.getBox(request);
      v_process = box.getStringDefault("p_process","selectList");

      if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

      // 로긴 check 루틴 VER 0.2 - 2003.09.9
      if (!AdminUtil.getInstance().checkLogin(out, box)) {
        return; 
      }
      box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

      /////////////////////////////////////////////////////////////////////////////
      if(v_process.equals("selectList")) {          //  리스트 페이지로 이동할때
        this.performListPage(request, response, box, out);
      } else if(v_process.equals("viewPage")) {             //  보기 
        this.performViewPage(request, response, box, out);
      } else if(v_process.equals("mainList")){ 		// 메인 뿌려주기
		this.performMainListPage(request, response, box, out);
      }
  }catch(Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
  }
}

/**	
 	메인 리스트
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performMainListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
  throws Exception {
  try {
      request.setAttribute("requestbox", box);
	  String v_return_url = "/learn/user/game/homepage/PreCourse.jsp";

	  // 05.12.15 이나연 _ 메인
            HomePagePreCourseBean bean = new HomePagePreCourseBean();
            ArrayList list = bean.selectDirectList(box); 
            request.setAttribute("mainList", list);
	  
      ServletContext    sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
      rd.forward(request, response);

      Log.info.println(this, box, "Dispatch to /learn/user/game/homepage/PreCourse.jsp");
  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performInsertPage()\r\n" + ex.getMessage());
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
	  String v_return_url = "/learn/user/game/course/gu_PreCourse_L.jsp";
	  
	  HomePagePreCourseBean bean = new HomePagePreCourseBean();
      ArrayList list = bean.selectList(box); 
      
      request.setAttribute("list", list);
	  /*            //공지사항
            CommunityFrBoardBean bean5 = new CommunityFrBoardBean();
            ArrayList list5 = bean5.selectRoomIndexListBrd(box,"0"); 
            request.setAttribute("listDirectBrd", list5);*/
	  
      ServletContext    sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
      rd.forward(request, response);

      Log.info.println(this, box, "Dispatch to /learn/user/game/course/gu_PreCourse_L.jsp");
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
		HomePagePreCourseBean bean = new HomePagePreCourseBean();
      DataBox dbox = bean.selectView(box); 
      
      request.setAttribute("list", dbox);



      ServletContext    sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_PreCourse_R.jsp");
      rd.forward(request, response);

      Log.info.println(this, box, "Dispatch to /learn/user/game/course/gu_PreCourse_R.jsp");
  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performViewPage()\r\n" + ex.getMessage());
  }
}
}

