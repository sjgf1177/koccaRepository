//**********************************************************
//1. 제      목: 
//2. 프로그램명: SulmunCommonResultServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-18
//7. 수      정: 
//                 
//********************************************************** 
 
package controller.research;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunCommonPaperBean;
import com.credu.research.SulmunCommonResultBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.research.SulmunCommonResultServlet")
public class SulmunCommonResultServlet extends HttpServlet implements Serializable {
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
		RequestBox  box = null;
		String v_process = "";
        
		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);            
			v_process = box.getStringDefault("p_process","SulmunResultPage");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
			
			if (!AdminUtil.getInstance().checkRWRight("SulmunCommonResultServlet", v_process, out, box)) {
					return; 
			}

			if (v_process.equals("SulmunResultPage")) {    	
				this.performSulmunResultPage(request, response, box, out);
			} else if (v_process.equals("SulmunResultExcelPage")) {    	
				this.performSulmunResultExcelPage(request, response, box, out);
			} else if (v_process.equals("SulmunDetailResultExcelPage")) {    	
				this.performSulmunDetailResultExcelPage(request, response, box, out);
			} else if (v_process.equals("SulmunEachResultPage")) {    	
				this.performSulmunEachResultPage(request, response, box, out);
			} else if (v_process.equals("SulmunUserPage")) {    	                                                //설문 응시 페이지
				this.performSulmunUserPage(request, response, box, out);
			} else if (v_process.equals("SulmunUserResultInsert")) {    	
				this.performSulmunUserResultInsert(request, response, box, out);
			} else if (v_process.equals("SulmunUserResultPage")) {    	
				this.performSulmunUserResultPage(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	

	/**
	일반설문 결과 페이지
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/		
	public void performSulmunResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunCommonResult_L.jsp";
                  
			SulmunCommonResultBean bean = new SulmunCommonResultBean();
			ArrayList list1 = bean.SelectObectResultList(box);
			request.setAttribute("SulmunResultList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunResultPage()\r\n" + ex.getMessage());
		}
	}
	
	/**
	일반설문 엑셀 페이지
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/		
	public void performSulmunResultExcelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunCommonResult_E.jsp";
                        
			SulmunCommonResultBean bean = new SulmunCommonResultBean();
			ArrayList list1 = bean.SelectObectResultList(box);
			request.setAttribute("SulmunResultExcelPage", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);		
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunResultExcelPage()\r\n" + ex.getMessage());
		}
	}


	/**
	일반설문 엑셀 페이지
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/	
	public void performSulmunDetailResultExcelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunCommonDetailResult_E.jsp";
 
			SulmunCommonPaperBean bean1 = new SulmunCommonPaperBean();
			DataBox dbox1 = bean1.getPaperData(box);
			request.setAttribute("SulmunPaperData", dbox1);				

			SulmunCommonResultBean bean = new SulmunCommonResultBean();
			ArrayList list1 = bean.selectSulmunDetailResultExcelList(box);
			request.setAttribute("SulmunDetailResultExcelPage", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);		
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunResultExcelPage()\r\n" + ex.getMessage());
		}
	}


	/**
	안쓰임
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/			
	public void performSulmunEachResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
	
			}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunEachResultPage()\r\n" + ex.getMessage());
		}
	}
	

	/**
	안쓰임
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/		
	public void performSulmunUserPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     

		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunUserResultListPage()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	안쓰임
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/	
	public void performSulmunUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{                

			
			}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
		}            
	}
	
	
	/**
	안쓰임
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/		
	public void performSulmunUserResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
	
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunUserResultPage()\r\n" + ex.getMessage());
		}
	}
}
