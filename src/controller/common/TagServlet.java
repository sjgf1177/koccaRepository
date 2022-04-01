//**********************************************************
//1. ��      ��: Tag���� SERVLET


//2. ���α׷���: TagServlet
//3. ��      ��:
//4. ȯ      ��: JDK 1.6
//5. ��      ��: 0.1
//6. ��      ��: swchoi 2009. 7. 10.
//7. ��      ��: 
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
