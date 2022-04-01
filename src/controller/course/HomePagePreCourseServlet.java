//**********************************************************
//1. ��      ��: Ȩ������ ������������ ���� ����
//2. ���α׷���: HomePagePreCourseServlet.java
//3. ��      ��: Ȩ������ ������������ ���� ����
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2005.12.13
//7. ��      ��: 
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

      // �α� check ��ƾ VER 0.2 - 2003.09.9
      if (!AdminUtil.getInstance().checkLogin(out, box)) {
        return; 
      }
      box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

      /////////////////////////////////////////////////////////////////////////////
      if(v_process.equals("selectList")) {          //  ����Ʈ �������� �̵��Ҷ�
        this.performListPage(request, response, box, out);
      } else if(v_process.equals("viewPage")) {             //  ���� 
        this.performViewPage(request, response, box, out);
      } else if(v_process.equals("mainList")){ 		// ���� �ѷ��ֱ�
		this.performMainListPage(request, response, box, out);
      }
  }catch(Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
  }
}

/**	
 	���� ����Ʈ
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

	  // 05.12.15 �̳��� _ ����
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
 	����Ʈ�������� �̵��Ҷ�
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
	  /*            //��������
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
 	���������� �̵��Ҷ�
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

