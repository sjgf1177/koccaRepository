//**********************************************************
//1. 제      목: r가입경로 설문 결과 Servlet
//2. 프로그램명: SulmunContentsResultServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
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
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunRegistPaperBean;
import com.credu.research.SulmunRegistResultBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.research.SulmunRegistResultServlet")
public class SulmunRegistResultServlet extends HttpServlet implements Serializable {
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

			if (!v_process.equals("SulmunResultPage") && !v_process.equals("SulmunEachResultPage") && !v_process.equals("ContentResearch")) {
				if (!AdminUtil.getInstance().checkRWRight("SulmunRegistResultServlet", v_process, out, box)) {
					return; 
				}
			} else {
            if(box.getSession("userid").equals("")){
            	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }			
			}
			
			if (v_process.equals("SulmunResultPage")) {    	
				this.performSulmunResultPage(request, response, box, out);
			} else if (v_process.equals("SulmunResultExcelPage")) {    	
				this.performSulmunResultExcelPage(request, response, box, out);
			} else if (v_process.equals("SulmunEachResultPage")) {    	
				this.performSulmunEachResultPage(request, response, box, out);
			} else if (v_process.equals("ContentResearch")) {    	
				this.performContentResearch(request, response, box, out);
			} else if (v_process.equals("SulmunUserPage")) {    	                              // 설문 응시 페이지 - 개인별보기
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
    컨텐츠설문  평가결과 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
  */
	public void performSulmunResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
		    
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunRegistResult_L.jsp";
                  
			SulmunRegistResultBean bean = new SulmunRegistResultBean();
			ArrayList list1 = bean.SelectObectResultList(box);
			request.setAttribute("SulmunResultList", list1);  // 통계별

			ArrayList list2 = bean.selectResultMemberList(box);
			request.setAttribute("ResultMemberList", list2);  // 개인별
			            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunResultPage()\r\n" + ex.getMessage());
		}
	}
	
	/**
    컨텐츠설문 엑셀보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
  */	
	public void performSulmunResultExcelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunRegistResult_E.jsp";
                        
			SulmunRegistResultBean bean = new SulmunRegistResultBean();
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
    컨텐츠설문 결과보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
  */		
	public void performSulmunEachResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/user/course/zu_SulmunContents_L.jsp";
                  
			SulmunRegistResultBean bean = new SulmunRegistResultBean();
Log.info.println("aaaa");
            ArrayList list = bean.selectBoardList(box);
            request.setAttribute("SulmunResultList", list);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);		
			}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunEachResultPage()\r\n" + ex.getMessage());
		}
	}

    /**
    컨텐츠 평가 보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performContentResearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);

        	box.put("p_action", "go"); 
			SulmunRegistResultBean bean = new SulmunRegistResultBean();
			ArrayList list1 = bean.SelectObectResultList(box);
			request.setAttribute("SulmunResultList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_SulmunContents_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performContentResearch()\r\n" + ex.getMessage());
        }
    }	

	/**
	컨텐츠설문 개인별 보기
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSulmunUserPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
		    String v_return_url = "/learn/admin/research/za_SulmunRegistResultMember_L.jsp";
                        
			SulmunRegistPaperBean bean = new SulmunRegistPaperBean();
			ArrayList list1 = bean.selectPaperQuestionExampleList(box);
			request.setAttribute("PaperQuestionExampleList", list1);
        
			box.put("p_subjsel",box.getString("p_subj"));
			box.put("p_upperclass","ALL");
			DataBox dbox1 = bean.getPaperData(box);               
			request.setAttribute("SulmunPaperData", dbox1);
			box.remove("p_subjsel");
			box.remove("p_subjsel");

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunUserPage()\r\n" + ex.getMessage());
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
