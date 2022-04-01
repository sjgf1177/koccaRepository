//**********************************************************
//1. 제      목: 학습환경도우미를 제어하는 서블릿
//2. 프로그램명 : KHomePageHelpServlet.java
//3. 개      요: 학습환경도우미 페이지을 제어한다(HOMEPAGE)
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 06.01.18
//7. 수      정: 
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.KHomePageHelpServlet")
public class KHomePageHelpServlet extends javax.servlet.http.HttpServlet {

/**
* DoGet
* Pass get requests through to PerformTask
*/
public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    this.doPost(request, response);
}
public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    PrintWriter out = null;
    MultipartRequest multi = null;
    RequestBox box = null;
    String v_process = "";
    int fileupstatus = 0;
    
    try {
        response.setContentType("text/html;charset=euc-kr");
        out = response.getWriter();

        box = RequestManager.getBox(request);

        v_process = box.getString("p_process");

        if(ErrorManager.isErrorMessageView()) {
            box.put("errorout", out);
        }

       if(v_process.equals("selectHelp")) {                                 // 학습환경도우미 ( 인터넷 ) 
            this.performSelectList(request, response, box, out);
        }else if(v_process.equals("selectHelpMove")){						// 학습환경도우미 ( 동영상 )
            this.performSelectListMove(request, response, box, out);
		}else if(v_process.equals("selectHelpSWDown")){						// SW 다운로드
            this.performSelectListDown(request, response, box, out);
		}else if(v_process.equals("Help")){									// 온라인 메뉴얼
            this.performSelectHelp(request, response, box, out);
		}
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);  
    }
}

/**
학습환경도우미 ( 인터넷 )
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_system.jsp");
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_system.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}

/**
학습환경도우미 ( 동영상 )
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectListMove(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            
	
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_systemMedia.jsp");
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_systemMedia.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}

/**
SW 다운로드
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectListDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            
	
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_swDown.jsp");
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_swDown.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}

/**
온라인 메뉴얼
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectHelp(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            
		String v_url = "";
		
		String v_tab = box.getString("p_tab");
		
		//if(v_tab.equals("1"))
		if(v_tab.equals("2")) 		v_url = "/learn/user/kocca/customer/ku_menual2.jsp";
		else if(v_tab.equals("3"))  v_url = "/learn/user/kocca/customer/ku_menual3.jsp";
		else if(v_tab.equals("4"))  v_url = "/learn/user/kocca/customer/ku_menual4.jsp";
		else  						v_url = "/learn/user/kocca/customer/ku_menual.jsp";
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_menual.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}



}

