//**********************************************************
//1. 제      목: 
//2. 프로그램명: AcceptFileToDBServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-07-23
//7. 수      정: 
//                 
//********************************************************** 
 
package controller.propose;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.AcceptFileToDBBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.propose.AcceptFileToDBServlet")
public class AcceptFileToDBServlet extends javax.servlet.http.HttpServlet {
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
			v_process = box.getStringDefault("p_process","individualAcceptPage");
            
			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			if (!AdminUtil.getInstance().checkRWRight("AcceptFileToDBServlet", v_process, out, box)) {
				return; 
			}
			if (v_process.equals("blacklist")) {      		    //in case of 입과FileToDB
				this.performBlackList(request, response, box, out);
			} else if (v_process.equals("blacklistDelete")) {      		            //in case of delete
				this.performBlackListDelete(request, response, box, out);
			} else if (v_process.equals("FileToDB")) {      		    //in case of Black list FileToDB 입력 
				this.performFileToDB(request, response, box, out);
			} else if (v_process.equals("insertFileToDB")) {      		    //in case of Black list FileToDB 입력 
				this.performInsertFileToDB(request, response, box, out);
			} else if (v_process.equals("previewFileToDB")) {      		    //in case of black list FileToDB 입력 
				this.performPreviewFileToDB(request, response, box, out);
			} else if (v_process.equals("compCoditionPage")) {      		            //in case of delete
				this.performCompCoditionPage(request, response, box, out);
			} else if (v_process.equals("compCoditionUpdate")) {      		            //in case of delete
				this.performCompCoditionUpdate(request, response, box, out);
			} else if (v_process.equals("compCoditionDelete")) {      		            //in case of delete
				this.performCompCoditionDelete(request, response, box, out);
			} else if (v_process.equals("compCoditionInsert")) {      		            //in case of delete
				this.performCompCoditionInsert(request, response, box, out);
			} else if (v_process.equals("blackCoditionPage")) {      		            //in case of delete
				this.performBlackCoditionPage(request, response, box, out);
			} else if (v_process.equals("blackCoditionUpdate")) {      		            //in case of delete
				this.performBlackCoditionUpdate(request, response, box, out);
			} else if (v_process.equals("blackCoditionDelete")) {      		            //in case of delete
				this.performBlackCoditionDelete(request, response, box, out);
			} else if (v_process.equals("blackCoditionInsert")) {      		            //in case of delete
				this.performBlackCoditionInsert(request, response, box, out);
			} 			
			
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	
	/**
	블랙리스트 조회화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBlackList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/propose/za_Blacklist_L.jsp";
			AcceptFileToDBBean bean = new AcceptFileToDBBean();				
			
			ArrayList list1 = bean.SelectBlackList(box);
			request.setAttribute("blacklist", list1);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performBlackList()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	블랙리스트 삭제화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBlackListDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			String v_url  = "/servlet/controller.propose.AcceptFileToDBServlet";
           
			AcceptFileToDBBean aftbean = new AcceptFileToDBBean();
			int isOk = aftbean.deleteBlackList(box);
            
			String v_msg = "";
			box.put("p_process", "blacklist");
			
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
			throw new Exception("performInsertConditionPage()\r\n" + ex.getMessage());
		}
	}
	
	
	
	/**
	수강제약 대상자 엑셀등록 화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/propose/za_BlackFilToDB.jsp";
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performProposeFileToDB()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	수강제약 대상자 엑셀등록 화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/propose/za_BlackFilToDB_P.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
		}
	}

	/**
	수강제약 대상자 미리보기 화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/propose/za_BlackFilToDB_P.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performPreviewFileToDB()\r\n" + ex.getMessage());
		}
	}


	/**
	회사별 수강 제약 조회화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCompCoditionPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/propose/za_CompCondition_L.jsp";

			AcceptFileToDBBean aftbean = new AcceptFileToDBBean();
			
			ArrayList list  = aftbean.SelectCompCondition(box);
			request.setAttribute("compconditionlist", list);
						
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performCompCoditionPage()\r\n" + ex.getMessage());
		}
	}

	/**
	회사별 수강 제약 수정화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCompCoditionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {    
			
			String v_url  = "/servlet/controller.propose.AcceptFileToDBServlet";
 			String v_msg = "";
			box.put("p_process", "compCoditionPage");
			box.put("p_action", "go");
          
			AcceptFileToDBBean bean = new AcceptFileToDBBean();
			int isOk = bean.updateCompCodition(box);           
			
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
			throw new Exception("performCompCoditionUpdate()\r\n" + ex.getMessage());
		}
	}


	/**
	회사별 수강 제약 삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCompCoditionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {    
			
			String v_url  = "/servlet/controller.propose.AcceptFileToDBServlet";
 			String v_msg = "";
			box.put("p_process", "compCoditionPage");
			box.put("p_action", "go");
          
			AcceptFileToDBBean bean = new AcceptFileToDBBean();
			int isOk = bean.deleteCompCodition(box);           
			
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
			throw new Exception("performCompCoditionDelete()\r\n" + ex.getMessage());
		}
	}


	/**
	회사별 수강 제약 등록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCompCoditionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {    
			
			String v_url  = "/servlet/controller.propose.AcceptFileToDBServlet";
 			String v_msg = "";
			box.put("p_process", "compCoditionPage");
			box.put("p_action", "go");
          
			AcceptFileToDBBean bean = new AcceptFileToDBBean();
			int isOk = bean.insertCompCodition(box);           
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 99) {            	
				v_msg = "compcondition.fail";       
				alert.alertFailMessage(out, v_msg);
			}else if(isOk > 0) {                
				v_msg = "insert.ok";   
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "insert.fail";   
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertConditionPage()\r\n" + ex.getMessage());
		}
	}


	/**
	수강신청 제약 조회화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBlackCoditionPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/propose/za_BlackCondition_L.jsp";

			AcceptFileToDBBean aftbean = new AcceptFileToDBBean();
			
			ArrayList list  = aftbean.SelectBlackCondition(box);
			request.setAttribute("blackconditionlist", list);
						
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performBlackCoditionPage()\r\n" + ex.getMessage());
		}
	}


	/**
	수강신청 제약 수정
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBlackCoditionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {    
			
			String v_url  = "/servlet/controller.propose.AcceptFileToDBServlet?p_action=go";
 			String v_msg = "";
			box.put("p_process", "blackCoditionPage");
          
			AcceptFileToDBBean bean = new AcceptFileToDBBean();
			int isOk = bean.updateBlackCodition(box);           
			
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
			throw new Exception("performCompCoditionUpdate()\r\n" + ex.getMessage());
		}
	}


	/**
	수강신청 제약
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBlackCoditionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {    
			
			String v_url  = "/servlet/controller.propose.AcceptFileToDBServlet?p_action=go";
 			String v_msg = "";
			box.put("p_process", "blackCoditionPage");
          
			AcceptFileToDBBean bean = new AcceptFileToDBBean();
			int isOk = bean.deleteBlackCodition(box);           
			
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
			throw new Exception("performCompCoditionDelete()\r\n" + ex.getMessage());
		}
	}


	/**
	수강신청 제약 등록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBlackCoditionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {    
			
			String v_url  = "/servlet/controller.propose.AcceptFileToDBServlet?p_action=go";
 			String v_msg = "";
			box.put("p_process", "blackCoditionPage");
          
			AcceptFileToDBBean bean = new AcceptFileToDBBean();
			int isOk = bean.insertBlackCodition(box);           
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 99) {
				v_msg = "blackcondition.fail";       
				alert.alertFailMessage(out, v_msg);
			}else if(isOk > 0) {
				v_msg = "insert.ok";   
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {
				v_msg = "insert.fail";   
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertConditionPage()\r\n" + ex.getMessage());
		}
	}
	

}
