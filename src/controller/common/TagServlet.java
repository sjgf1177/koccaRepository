//**********************************************************
//1. 제      목: Tag관련 SERVLET


//2. 프로그램명: TagServlet
//3. 개      요:
//4. 환      경: JDK 1.6
//5. 버      젼: 0.1
//6. 작      성: swchoi 2009. 7. 10.
//7. 수      정: 
//                 
//**********************************************************
package controller.common;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.dunet.common.taglib.KoccaSelectBean;
@WebServlet("/servlet/controller.common.TagServlet")
public class TagServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 8236546677697884692L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        
        try {
        	
            String v_return_url = "/script/scriptContent.jsp";
            
            RequestBox  box = RequestManager.getBox(request);
            KoccaSelectBean bean = new KoccaSelectBean();
            request.setAttribute("list", bean.list(box));
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);

            rd.forward(request, response);
        } catch(Exception ex) {
        	System.out.println("Error rrrrr "+ex);
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

}
