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
import com.dunet.common.taglib.KoccaAjaxBean;

@SuppressWarnings("unchecked")
@WebServlet("/servlet/controller.common.AjaxServlet")
public class AjaxServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 8236546677697884692L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;

		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=euc-kr");
			String v_return_url = "/script/ajaxContent.jsp";

			RequestBox  box = RequestManager.getBox(request);
			KoccaAjaxBean bean = new KoccaAjaxBean();
			box.put("s_grcode", box.getSession("tem_grcode"));
			box.put("s_userid", box.getSession("userid"));
			request.setAttribute("result", bean.save(box));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);

			rd.forward(request, response);
		} catch(Exception ex) {
			System.out.println("AjaxServlet Error  "+ex);
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

}
