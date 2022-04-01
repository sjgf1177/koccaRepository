//**********************************************************
//1. 제      목: Object관리 SERVLET
//2. 프로그램명: SCObjectServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: S.W.Kang 2004. 12. 03
//7. 수      정: 
//                 
//**********************************************************
package controller.beta;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BetaSCObjectBean;
import com.credu.beta.BetaSCObjectData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.BetaSCObjectServlet")
public class BetaSCObjectServlet extends HttpServlet implements Serializable {

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
			v_process = box.getStringDefault("p_process","listPage");
            
			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
			///////////////////////////////////////////////////////////////////
			// 권한 check 루틴 VER 0.2 - 2003.08.10
			if (!AdminUtil.getInstance().checkRWRight("BetaSCObjectServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("listPage")) {      			// Object List
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("searchList")) {      	// Object 검색화면
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("insertPage")) {      	// Object 등록화면
				this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("insertSave")) {      		//Object 등록저장
				this.performInsertSave(request, response, box, out);
			} else if (v_process.equals("updatePage")) {      		//Object 수정화면
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("viewPage")) {      		//Object 조회화면
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("updateSave")) {      		//Object 수정저장
				this.performUpdateSave(request, response, box, out);				
			} else if (v_process.equals("deleteSave")) {      		//Object 삭제
				this.performDeleteSave(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	마스터폼 리스트 조회
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/beta/admin/za_BetaSCObject_L.jsp";
            if (box.getString("p_action").equals("go")) {            
				BetaSCObjectBean bean = new BetaSCObjectBean();
				ArrayList list1 = bean.SelectObjectList(box);
				request.setAttribute("ObjectList", list1);
            }
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}


	/**
	PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/admin/za_BetaSCObject_I.jsp";
			box.put("s_subj",box.getString("s_subj"));
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
		}            
	} 

	/**
	Object등록 저장
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performInsertSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSCObjectServlet";

			BetaSCObjectBean bean = new BetaSCObjectBean();
			String results = bean.InsertObject(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("s_subj",box.getString("s_subj"));
			box.put("s_gubun",box.getString("s_gubun"));
			box.put("p_action",box.getString("go"));
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "insert.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}

   
	/**
	PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/admin/za_BetaSCObject_U.jsp";
            box.put("s_subj",box.getString("s_subj"));
            
			BetaSCObjectBean bean = new BetaSCObjectBean();
			
			BetaSCObjectData 	data = bean.SelectObjectData(box);	//Object 정보
			request.setAttribute("BetaSCObjectData", data);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
		}            
	} 

	/**
	Object수정 저장
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performUpdateSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSCObjectServlet";

			BetaSCObjectBean bean = new BetaSCObjectBean();
			String results = bean.UpdateObject(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("s_subj",box.getString("s_subj"));
			box.put("s_gubun",box.getString("s_gubun"));
			box.put("p_action",box.getString("go"));
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "update.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}

	/**
	Object삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performDeleteSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSCObjectServlet";

			BetaSCObjectBean bean = new BetaSCObjectBean();
			String results = bean.UpdateObject(box);
            
			String v_msg = "";
			box.put("p_process", "updatePage");
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}
	
	
}
