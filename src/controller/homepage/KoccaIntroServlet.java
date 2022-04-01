//**********************************************************
//1. ��      ��: ��ȭ������ ��ī���� �Ұ��� �����ϴ� ����
//2. ���α׷��� : KoccaIntroServlet.java
//3. ��      ��: ��ȭ������ ��ī���� �Ұ� �������� �����Ѵ� (HOMEPAGE)
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: 06.01.18
//7. ��      ��: 
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

       if(v_process.equals("KoccaIntro")) {                                 // ��ī���� �Ұ�
            this.performSelectIntro(request, response, box, out);
        }else if(v_process.equals("KoccaCEO")){								// �����λ縻
            this.performSelectCEO(request, response, box, out);
		}else if(v_process.equals("List")){										// ��ȭ������ �η¾缺 ü�赵
            this.performSelectList(request, response, box, out);
		}
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);  
    }
}

/**
��ī���� �Ұ�
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
�����λ縻
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
��ȭ������ �η¾缺 ü�赵
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

