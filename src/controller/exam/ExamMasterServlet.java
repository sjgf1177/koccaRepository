//**********************************************************
//1. 제      목: 
//2. 프로그램명: ExamMasterServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: 
//********************************************************** 
 
package controller.exam;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.exam.ExamMasterBean;
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
@WebServlet("/servlet/controller.exam.ExamMasterServlet")
public class ExamMasterServlet extends HttpServlet implements Serializable {
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
			v_process = box.getStringDefault("p_process","ExamMasterListPage");
			//System.out.println("평가   ExamMasterServlet : "+v_process);			
       
			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			if (!AdminUtil.getInstance().checkRWRight("ExamMasterServlet", v_process, out, box)) {
					return; 
			}	    	
			
			if (v_process.equals("ExamMasterListPage")) {   
				this.performExamMasterListPage(request, response, box, out);
			} else if (v_process.equals("ExamMasterInsertPage")) {   	// 평가 마스터 등록 페이지 1
				this.performExamMasterInsertPage(request, response, box, out);
			} else if (v_process.equals("ExamMasterInsertNextPage")) {   
				this.performExamMasterInsertNextPage(request, response, box, out);
			} else if (v_process.equals("ExamMasterUpdatePage")) {   
				this.performExamMasterUpdatePage(request, response, box, out);
			} else if (v_process.equals("ExamMasterUpdateNextPage")) {   
				this.performExamMasterUpdateNextPage(request, response, box, out);
			} else if (v_process.equals("ExamMasterInsert")) {  		//평가 마스터 등록
				this.performExamMasterInsert(request, response, box, out);
			} else if (v_process.equals("ExamMasterUpdate")) {   
				this.performExamMasterUpdate(request, response, box, out);
			} else if (v_process.equals("ExamMasterDelete")) {   
				this.performExamMasterDelete(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	

	/**
  평가 마스터 리스트 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
  public void performExamMasterListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	  try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamMaster_L.jsp";
                        
		  ExamMasterBean bean = new ExamMasterBean();
		  ArrayList list1 = bean.selectExamMasterList(box);
		  request.setAttribute("ExamMasterList", list1);
            
		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamMasterListPage()\r\n" + ex.getMessage());
	  }
  }

	/** 
  평가 마스터 등록 페이지 1
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
  public void performExamMasterInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	  try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamMaster_I.jsp";

			String ctype = "";
			ExamQuestionBean bean = new ExamQuestionBean();

			ctype = bean.getContentType(box);  
			box.put("p_ctype",ctype);  // 컨텐츠타입을 구한다.
			
		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamMasterInsertPage()\r\n" + ex.getMessage());
	  }
  }


	/**
  평가 마스터 등록 페이지 2
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
  public void performExamMasterInsertNextPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	  try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamMaster_I2.jsp";

          ExamMasterBean bean = new ExamMasterBean();
          ArrayList list = bean.selectExamLevels(box);
          request.setAttribute("ExamLevelsData", list);
					
		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamMasterInsertNextPage()\r\n" + ex.getMessage());
	  }
  }


	/**
  평가 마스터 수정 페이지 1
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
  public void performExamMasterUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	  try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamMaster_U.jsp";
			
		  ExamMasterBean bean = new ExamMasterBean();
		  DataBox dbox = bean.selectExamMasterData(box);
		  request.setAttribute("ExamMasterData", dbox);
		  
		  boolean result = bean.isExamPaper(box);
		  request.setAttribute("isExamPaper", new Boolean(result));

		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamMasterUpdatePage()\r\n" + ex.getMessage());
	  }
  }


	/**
  평가 마스터 수정 페이지 2
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
  public void performExamMasterUpdateNextPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	  try {     
		  request.setAttribute("requestbox", box);            
		  String v_return_url = "/learn/admin/exam/za_ExamMaster_U2.jsp";

          ExamMasterBean bean = new ExamMasterBean();
          ArrayList list = bean.selectExamLevels(box);
          request.setAttribute("ExamLevelsData", list);

          ArrayList list1 = bean.selectMasterLevels(box);
          request.setAttribute("ExamMasterLevels", list1);

		  DataBox dbox = bean.selectExamMasterData(box);
		  request.setAttribute("ExamMasterData", dbox);
					
		  ServletContext sc = getServletContext();
		  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  rd.forward(request, response);
	  }catch (Exception ex) {           
		  ErrorManager.getErrorStackTrace(ex, out);
		  throw new Exception("performExamMasterUpdateNextPage()\r\n" + ex.getMessage());
	  }
  }  


	/**
  평가 마스터 등록 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
  public void performExamMasterInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	  try{                
		  String v_url  = "/servlet/controller.exam.ExamMasterServlet";
           
		  ExamMasterBean bean = new ExamMasterBean();
		  int isOk = bean.insertExamMaster(box);
            
		  String v_msg = "";
		  box.put("p_process", "ExamMasterInsertPage");
		  box.put("p_end", "0");
      
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
		  throw new Exception("performExamMasterInsert()\r\n" + ex.getMessage());
	  }            
  }       


	/**
  평가 마스터 수정 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
  public void performExamMasterUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	  try{                
		  String v_url  = "/servlet/controller.exam.ExamMasterServlet";
           
		  ExamMasterBean bean = new ExamMasterBean();
		  int isOk = bean.updateExamMaster(box);
            
		  String v_msg = "";
		  box.put("p_process", "ExamMasterUpdatePage");
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
		  throw new Exception("performExamMasterUpdate()\r\n" + ex.getMessage());
	  }            
  }       
  

	/**
  평가 마스터 삭제 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/  
  public void performExamMasterDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	  try{                
		  String v_url  = "/servlet/controller.exam.ExamMasterServlet";
           
		  ExamMasterBean bean = new ExamMasterBean();
		  int isOk = bean.deleteExamMaster(box);
            
		  String v_msg = "";
		  box.put("p_process", "ExamMasterInsertPage");
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
		  throw new Exception("performExamMasterDelete()\r\n" + ex.getMessage());
	  }            
  }       

}
