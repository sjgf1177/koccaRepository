//**********************************************************
//  1. 제      목:  제어하는 서블릿
//  2. 프로그램명 : ____Servlet.java
//  3. 개      요:  제어 프로그램
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수     정1:
//**********************************************************
package controller.infomation;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.infomation.JobServlet")
public class JobServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            
            out.println("<script language='JavaScript' type='text/JavaScript'>  ");
            out.println("window.open('http://www.culturist.co.kr/');            ");
            out.println("</script>                                              ");
            
            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher("/servlet/controller.homepage.SubMenuMainServlet?p_process=INFORMATION");

            rd.include(request, response);

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
}

