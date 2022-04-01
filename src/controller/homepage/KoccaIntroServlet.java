//**********************************************************
//1. 제      목: 문화콘텐츠 아카데미 소개를 제어하는 서블릿
//2. 프로그램명 : KoccaIntroServlet.java
//3. 개      요: 문화콘텐츠 아카데미 소개 페이지을 제어한다 (HOMEPAGE)
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

@WebServlet("/servlet/controller.homepage.KoccaIntroServlet")
public class KoccaIntroServlet extends javax.servlet.http.HttpServlet {

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

       if(v_process.equals("KoccaIntro")) {                                 // 아카데미 소개
            this.performSelectIntro(request, response, box, out);
        }else if(v_process.equals("KoccaCEO")){								// 원장인사말
            this.performSelectCEO(request, response, box, out);
		}else if(v_process.equals("List")){										// 문화콘텐츠 인력양성 체계도
            this.performSelectList(request, response, box, out);
		}
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);  
    }
}

/**
아카데미 소개
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectIntro(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/aboutus/ku_AcademyIntro.jsp");
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/aboutus/ku_AcademyIntro.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}

/**
원장인사말
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectCEO(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            
	
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/aboutus/ku_AcademyCeo.jsp");
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/aboutus/ku_AcademyCeo.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}

/**
문화콘텐츠 인력양성 체계도
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
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/aboutus/ku_Academy.jsp");
		
		rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/aboutus/ku_Academy.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectList()\r\n" + ex.getMessage());
    }
}



}

