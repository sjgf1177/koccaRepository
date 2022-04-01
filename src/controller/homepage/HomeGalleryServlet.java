//**********************************************************
//1. 제      목: 홈페이지 오프라인갤러리 제어 서블릿
//2. 프로그램명: HomeGalleryServlet.java
//3. 개      요: 홈페이지 오프라인갤러리 제어 서블릿
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 2005.12.19 이나연
//7. 수      정: 
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomeGalleryBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.HomeGalleryServlet")
public class HomeGalleryServlet extends javax.servlet.http.HttpServlet {

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
	  v_process = box.getString("p_process");
	  
	  box.put("p_grtype","KDGI");							// 게임 
	  if(ErrorManager.isErrorMessageView()) box.put("errorout", out);
	
	  // 로긴 check 루틴 VER 0.2 - 2003.09.9
	  if (!AdminUtil.getInstance().checkLogin(out, box)) {
	    return; 
	  }
	  
	 // box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
	
	  /////////////////////////////////////////////////////////////////////////////
	  if(v_process.equals("List")) {         						//  리스트 페이지로 이동할때
		  	this.performListPage(request, response, box, out);
	  } else if(v_process.equals("selectView")) {   				//  보기 
		  	this.performViewPage(request, response, box, out);
	  } else if(v_process.equals("moreList")){ 						// 메인 More 뿌려주기
			this.performMorePage(request, response, box, out);
	  } else if(v_process.equals("mainList")){ 						// 메인 뿌려주기
			this.performMainPage(request, response, box, out);
	  } else if(v_process.equals("insertPage")){ 					// 등록 페이지로 이동
			this.performInsertPage(request, response, box, out);
	  } else if(v_process.equals("insertGallery")){ 				// 등록
			this.performInsert(request, response, box, out);
	  } else if(v_process.equals("updaetPage")){ 					// 수정 페이지로 이동
			this.performUpdatePage(request, response, box, out);
	  } else if(v_process.equals("updateGallery")){ 				// 수정
			this.performUpdate(request, response, box, out);
	  } else if(v_process.equals("delete")){ 						// 삭제
			this.performDelete(request, response, box, out);
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
public void performMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
        
	        String v_url = "";
	        v_url = "/learn/user/game/homepage/Gallery.jsp";
	        //String v_url = "/index.jsp";
	        
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	        rd.forward(request, response);
		
		    Log.info.println(this, box, "Dispatch to /learn/user/game/homepage/Gallery.jsp");
		}catch (Exception ex) {
		    ErrorManager.getErrorStackTrace(ex, out);
		    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

/**	
메인 More 리스트
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performMorePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
	try {
		request.setAttribute("requestbox", box);

		  String v_return_url = "/learn/user/game/service/gu_Gallery_L.jsp";
		  box.put("p_process","moreGallery");
		        
				HomeGalleryBean bean = new HomeGalleryBean();
				ArrayList list1 = bean.selectList(box);
				
				request.setAttribute("galleryList",list1);
		  
	    ServletContext    sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
	    rd.forward(request, response);
	
	    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_L.jsp");
	}catch (Exception ex) {
	    ErrorManager.getErrorStackTrace(ex, out);
	    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	}
}


/**	
 리스트로 이동 
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
	
		String v_url = "/learn/user/game/service/gu_Gallery_L.jsp";
		box.put("p_process","List");
		
		
		HomeGalleryBean bean = new HomeGalleryBean();
		ArrayList list1 = bean.selectList(box);
		
		request.setAttribute("galleryList",list1);
		
	    ServletContext    sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	    rd.forward(request, response);
	
	    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_L.jsp");
	}catch (Exception ex) {
	    ErrorManager.getErrorStackTrace(ex, out);
	    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	}
}

/**	
 뷰
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
		

		String v_url = "/learn/user/game/service/gu_Gallery_R.jsp";
		box.put("p_process","selectView");
		
		HomeGalleryBean bean = new HomeGalleryBean();
		DataBox dbox = bean.selectView(box);
		
		request.setAttribute("gallerySelect",dbox);
		  
	    ServletContext    sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	    rd.forward(request, response);
	
	    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_R.jsp");
	}catch (Exception ex) {
	    ErrorManager.getErrorStackTrace(ex, out);
	    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	}
}

/**	
 등록 페이지로 
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
	
		String v_url = "/learn/user/game/service/gu_Gallery_I.jsp";
		box.put("p_process","add");
		  
	    ServletContext    sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	    rd.forward(request, response);
	
	    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_I.jsp");
	}catch (Exception ex) {
	    ErrorManager.getErrorStackTrace(ex, out);
	    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	}
}

/**	
 등록
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
	try {
		request.setAttribute("requestbox", box);
		String v_url = "/servlet/controller.homepage.HomeGalleryServlet";
		
        String v_msg = "";

			HomeGalleryBean bean = new HomeGalleryBean(); 
			int isOk = bean.insertGallery(box);
		
        AlertManager alert = new AlertManager();
		
        if(isOk > 0) {
			box.put("p_process","List");
            v_msg = "insert.ok";
            alert.alertOkMessage(out, v_msg, v_url , box);
		}else {
            v_msg = "insert.fail";
            alert.alertFailMessage(out, v_msg);
        }
	
	    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_L.jsp");
	}catch (Exception ex) {
	    ErrorManager.getErrorStackTrace(ex, out);
	    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	}
}

/**	
 수정페이지로 
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
	try {
		request.setAttribute("requestbox", box);
		
			String v_url = "/learn/user/game/service/gu_Gallery_U.jsp";
			box.put("p_process","updatePage");
			
			HomeGalleryBean bean = new HomeGalleryBean();
			ArrayList list1 = bean.selectList(box);
			
			request.setAttribute("gallerySelect",list1);
			  
		    ServletContext    sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(v_url);
		    rd.forward(request, response);
		
		    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_U.jsp");
		}catch (Exception ex) {
		    ErrorManager.getErrorStackTrace(ex, out);
		    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
}

/**	
 수정
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
	try {
		request.setAttribute("requestbox", box);
		String v_url = "/servlet/controller.homepage.HomeGalleryServlet";
		
       String v_msg = "";

			HomeGalleryBean bean = new HomeGalleryBean(); 
			int isOk = bean.updateGallery(box);
		
       AlertManager alert = new AlertManager();
		
       if(isOk > 0) {
		   box.put("p_process","List");
           v_msg = "insert.ok";
           alert.alertOkMessage(out, v_msg, v_url , box);
		}else {
           v_msg = "insert.fail";
           alert.alertFailMessage(out, v_msg);
       }
	
	    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_L.jsp");
	}catch (Exception ex) {
	    ErrorManager.getErrorStackTrace(ex, out);
	    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	}
}

/**	
수정
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
	try {
		request.setAttribute("requestbox", box);
		String v_url = "/servlet/controller.homepage.HomeGalleryServlet";
		
		
		
		String v_msg = "";

			HomeGalleryBean bean = new HomeGalleryBean(); 
			int isOk = bean.deleteGallery(box);
		
      AlertManager alert = new AlertManager();
		
      if(isOk > 0) {
		   box.put("p_process","List");
          v_msg = "delete.ok";
          alert.alertOkMessage(out, v_msg, v_url , box);
		}else {
          v_msg = "delete.fail";
          alert.alertFailMessage(out, v_msg);
      }
	
	    Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Gallery_L.jsp");
	}catch (Exception ex) {
	    ErrorManager.getErrorStackTrace(ex, out);
	    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	}
}
}