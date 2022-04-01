//**********************************************************
//1. 제      목: 학습환경도우미를 제어하는 서블릿
//2. 프로그램명 : SubjLoadMapServlet.java
//3. 개      요: 학습환경도우미 페이지을 제어한다(HOMEPAGE)
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 2005.7.6 이연정
//7. 수      정: 
//**********************************************************

package controller.course;

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

@WebServlet("/servlet/controller.course.SubjLoadMapServlet")
public class SubjLoadMapServlet extends javax.servlet.http.HttpServlet {

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
		System.out.println("v_process:"+v_process);

        if(ErrorManager.isErrorMessageView()) {
            box.put("errorout", out);
        }

       if(v_process.equals("select")) {                                		 // 과정로드맵
            this.performSelect(request, response, box, out);
        } else if(v_process.equals("select2")) {                                 // 과정로드맵
           this.performSelect2(request, response, box, out);
       }	   
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);  
    }
}

/**
과정로드맵
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            
		String v_url = "";
		String v_tab = box.getString("p_tab");
		
		if( v_tab.equals("2") ) v_url = "/learn/user/kocca/homepage/pop_Loadmap_2.jsp";
		else if( v_tab.equals("3") ) v_url = "/learn/user/kocca/homepage/pop_Loadmap_3.jsp";
		else v_url = "/learn/user/kocca/homepage/pop_Loadmap_1.jsp";

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/homepage/pop_Loadmap_1.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}


/**
과정로드맵2
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelect2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            
		String v_url = "";
		String v_tab = box.getString("p_tab");
		
		if( v_tab.equals("2") ) v_url = "/learn/user/kocca/homepage/pop2_Loadmap_2.jsp";
		else if( v_tab.equals("3") ) v_url = "/learn/user/kocca/homepage/pop2_Loadmap_3.jsp";
		else v_url = "/learn/user/kocca/homepage/pop2_Loadmap_1.jsp";

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/homepage/pop_Loadmap_1.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}



}

