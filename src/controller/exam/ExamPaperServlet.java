//**********************************************************
//1. 제      목: 
//2. 프로그램명: ExamPaperServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: 
//********************************************************** 
 
package controller.exam;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.exam.ExamMasterBean;
import com.credu.exam.ExamPaperBean;
import com.credu.exam.ExamQuestionBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.exam.ExamPaperServlet")
public class ExamPaperServlet extends HttpServlet implements Serializable {
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
			v_process = box.getStringDefault("p_process","ExamPaperListPage");
			//System.out.println("평가   ExamPaperServlet : "+v_process);			
			
            
			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			if (!AdminUtil.getInstance().checkRWRight("ExamPaperServlet", v_process, out, box)) {
					return; 
			}	    	

            if (v_process.equals("ExamPaperListPage")) {      	
				this.performExamPaperListPage(request, response, box, out);
			} else if (v_process.equals("ExamPaperInsertPage")) {      	
				this.performExamPaperInsertPage(request, response, box, out);
			} else if (v_process.equals("ExamPaperUpdatePage")) {      	
				this.performExamPaperUpdatePage(request, response, box, out);
			} else if (v_process.equals("ExamPaperUpdateNextPage")) {      	
				this.performExamPaperUpdateNextPage(request, response, box, out);
			} else if (v_process.equals("ExamPaperInsert")) {
				this.performExamPaperInsert(request, response, box, out);
			} else if (v_process.equals("ExamPaperUpdate")) {
				this.performExamPaperUpdate(request, response, box, out);
			}else if (v_process.equals("ExamPaperDelete")) {   
				this.performExamPaperDelete(request, response, box, out);
			} else if (v_process.equals("PreviewPage")) {    	
				this.performExamPaperPreviewPage(request, response, box, out);
			} 
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	
   
	/**
  평가 문제지 리스트 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  
	public void performExamPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/exam/za_ExamPaper_L1.jsp";
			
			ExamPaperBean bean = new ExamPaperBean();
			ArrayList list1 = bean.selectPaperList(box);
			request.setAttribute("ExamPaperList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performExamPaperListPage()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
  평가 문제지 등록 페이지 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  	
	public void performExamPaperInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamPaper_I.jsp";

			String ctype = "";
			ExamQuestionBean bean = new ExamQuestionBean();
			ctype = bean.getContentType(box);  
			box.put("p_ctype",ctype);  // 컨텐츠타입을 구한다.
			
		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamPaperInsertPage()\r\n" + ex.getMessage());
	  }
	}

	/**
  평가 문제지 수정 페이지 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  
	public void performExamPaperUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamPaper_U.jsp";
			
		  ExamPaperBean bean = new ExamPaperBean();
		  DataBox dbox = bean.selectExamPaperData(box);
		  request.setAttribute("ExamPaperData", dbox);
		  
		  boolean result = bean.isExamResult(box);
		  request.setAttribute("isExamResult", new Boolean(result));

		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performExamPaperUpdatePage()\r\n" + ex.getMessage());
		}
	}


	/**
  평가 문제지 수정페이지 2
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  
  public void performExamPaperUpdateNextPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	  try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamPaper_U2.jsp";

          ExamMasterBean bean = new ExamMasterBean();
          ArrayList list = bean.selectExamLevels(box);
          request.setAttribute("ExamLevelsData", list);

		  ExamPaperBean bean1 = new ExamPaperBean();

          ArrayList list1 = bean1.selectPaperLevels(box);
          request.setAttribute("ExamMasterLevels", list1);

		  DataBox dbox = bean1.selectExamPaperData(box);
		  request.setAttribute("ExamPaperData", dbox);
					
		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamPaperUpdateNextPage()\r\n" + ex.getMessage());
	  }
  } 


	/**
  평가 문제지 등록 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  
	public void performExamPaperInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{                
		  String v_url  = "/servlet/controller.exam.ExamPaperServlet";
           
		  ExamPaperBean bean1 = new ExamPaperBean();
		  int isOk = bean1.insertExamPaper(box);
            
		  String v_msg = "";
		  box.put("p_process", "ExamPaperListPage");
      
		  AlertManager alert = new AlertManager();                        
		  if(isOk > 0) {            	
			  v_msg = "insert.ok";       
			  alert.alertOkMessage(out, v_msg, v_url , box);
		  }else {                
			  v_msg = "insert.fail";   
			  alert.alertFailMessage(out, v_msg);   
		  }                              
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performExamPaperInsert()\r\n" + ex.getMessage());
		}            
	}


	/**
  평가 문제지 수정 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  
	public void performExamPaperUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{                
		  String v_url  = "/servlet/controller.exam.ExamPaperServlet";
           
		  ExamPaperBean bean = new ExamPaperBean();
		  int isOk = bean.updateExamPaper(box);
            
		  String v_msg = "";
		  box.put("p_process", "ExamPaperUpdatePage");
		  box.put("p_end", "0");
      
		  AlertManager alert = new AlertManager();                        
		  if(isOk > 0) {            	
			  v_msg = "update.ok";       
			  alert.alertOkMessage(out, v_msg, v_url , box);
		  }else {                
			  v_msg = "update.fail";   
			  alert.alertFailMessage(out, v_msg);   
		  }                                            
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performExamPaperUpdate()\r\n" + ex.getMessage());
		}            
	}
	

	/**
  평가 문제지 삭제 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  	
	public void performExamPaperDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	  try{                
		  String v_url  = "/servlet/controller.exam.ExamPaperServlet";
           
		  ExamPaperBean bean = new ExamPaperBean();
		  int isOk = bean.deleteExamPaper(box);
            
		  String v_msg = "";
		  box.put("p_process", "ExamPaperInsertPage");
		  box.put("p_end", "0");
      
		  AlertManager alert = new AlertManager();                        
		  if(isOk > 0) {            		    
			  v_msg = "delete.ok";       
			  alert.alertOkMessage(out, v_msg, v_url , box);
			  
		  }else {                
			  v_msg = "delete.fail";   
			  alert.alertFailMessage(out, v_msg);   
		  }                               
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamPaperDelete()\r\n" + ex.getMessage());
	  }            
 }       

	/**
  평가 문제지 미리 보기 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  
	public void performExamPaperPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	  	try {     
		  	request.setAttribute("requestbox", box);            
		  	String v_return_url = "/learn/admin/exam/za_ExamPreview.jsp";
 
			ExamPaperBean bean = new ExamPaperBean();
			ArrayList list1 = bean.SelectPaperQuestionExampleList(box); 
			request.setAttribute("PaperQuestionExampleList", list1);

			box.put("p_subjsel",box.getString("p_subj"));
			box.put("p_upperclass","ALL");
			DataBox dbox1 = bean.getPaperData(box);               
			request.setAttribute("ExamPaperData", dbox1);
			box.remove("p_subjsel");
			box.remove("p_subjsel");

			Vector v1 = bean.SelectQuestionList(box);               
			request.setAttribute("ExamQuestionData", v1);
        
		  	ServletContext sc = getServletContext();
		  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  	rd.forward(request, response);
	  	}catch (Exception ex) {           
		  	ErrorManager.getErrorStackTrace(ex, out);
		  	throw new Exception("performExamPaperPreviewPage()\r\n" + ex.getMessage());
	  	}
  	}
}
