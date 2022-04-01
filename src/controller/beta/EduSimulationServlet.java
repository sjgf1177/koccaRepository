//**********************************************************
//1. 제      목: 과정모의학습 SERVLET(베타테스트시스템)
//2. 프로그램명: EduSimulationServlet.java
//3. 개      요: 과정코드 SERVLET(베타테스트시스템)
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: S.W.Kang 2004. 12. 26
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

import com.credu.beta.BetaPreviewData;
import com.credu.beta.BetaSubjectBean;
import com.credu.beta.BetaSubjectData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.EduSimulationServlet")
public class EduSimulationServlet extends HttpServlet implements Serializable {

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
			if (!AdminUtil.getInstance().checkRWRight("BetaSubjectServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("listPage")) {      			//in case of 과정 조회 화면
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("insertPage")) {      	//in case of 과정 등록 화면
				this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("insert")) {      		//in case of 과정 등록
				this.performInsert(request, response, box, out);
			} else if (v_process.equals("updatePage")) {      	//in case of 과정 수정 화면
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("update")) {     		//in case of 과정 수정
				this.performUpdate(request, response, box, out);
			} else if (v_process.equals("delete")) {      		//in case of 과정 삭제
				this.performDelete(request, response, box, out);
			} else if (v_process.equals("previewPage")) { //in case of 과정맛보기 조회 화면
				this.performPreviewPage(request, response, box, out);
			} else if (v_process.equals("previewInsertPage")) { //in case of 과정맛보기 등록 화면
				this.performPreviewInsertPage(request, response, box, out);
			} else if (v_process.equals("previewInsert")) { //in case of 과정맛보기 등록
				this.performPreviewInsert(request, response, box, out);
			} else if (v_process.equals("previewUpdatePage")) { //in case of 과정맛보기 수정 화면
				this.performPreviewUpdatePage(request, response, box, out);
			} else if (v_process.equals("previewUpdate")) { //in case of 과정맛보기 수정
				this.performPreviewUpdate(request, response, box, out);
			} else if (v_process.equals("previewDelete")) { //in case of 과정맛보기 삭제
				this.performPreviewDelete(request, response, box, out);
			} else if (v_process.equals("subjseqPage")) { //in case of 과정차수리스트 조회 화면
				this.performSubjseqPage(request, response, box, out);
			}	
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	과정코드리스트 VIEW
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/beta/admin/za_SubjectSimulation_L.jsp";

			BetaSubjectBean bean = new BetaSubjectBean();
			ArrayList list1 = bean.SelectSubjectList(box);
			request.setAttribute("SubjectList", list1);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

	/**
	과정코드 등록 PAGE - 사이버
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
			String v_return_url = "/beta/admin/za_BetaSubject_I.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}            
	}

	/**
	과정코드 등록 - 사이버
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";
           
			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.InsertSubject(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_upperclass", "ALL");
			box.put("p_subj", "ALL");
			
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
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}            
	}
    
	/**
	과정코드 수정 PAGE - 사이버
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
			String v_url = "/beta/admin/za_BetaSubject_U.jsp";

			BetaSubjectBean bean = new BetaSubjectBean();
			BetaSubjectData data = bean.SelectSubjectData(box);
			request.setAttribute("SubjectData", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}            
	}
    
	/**
	과정코드 수정 - 사이버
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";

			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.UpdateSubject(box);
            System.out.println("isOk="+isOk);
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_upperclass", "ALL");
			box.put("p_subj", "ALL");
			
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
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}

	/**
	과정코드 삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";
           
			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.DeleteSubject(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_upperclass", "ALL");
			box.put("p_subj", "ALL");
            
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
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}            
	}
	/**
	과정맛보기 PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/admin/za_BetaPreview_L.jsp";

			BetaSubjectBean bean = new BetaSubjectBean();

			ArrayList list = bean.PreviewGrcodeList(box);
			request.setAttribute("PreviewGrcodeList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
		}            
	}

	/**
	과정맛보기 등록 PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performPreviewInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/admin/za_BetaPreview_I.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
		}            
	}

	/**
	과정맛보기 등록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performPreviewInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";
           
			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.InsertPreview(box);
            
			String v_msg = "";
			box.put("p_process", "previewPage");
			
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
			throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	과정맛보기 수정 PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performPreviewUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/admin/za_BetaPreview_U.jsp";
			
			BetaSubjectBean bean = new BetaSubjectBean();
			BetaPreviewData data = bean.SelectPreviewData(box);
			request.setAttribute("PreviewData", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	과정코드 수정
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performPreviewUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";

			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.UpdatePreview(box);
            
			String v_msg = "";
			box.put("p_process", "previewPage");
			
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
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	과정코드 수정
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performPreviewDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";

			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.DeletePreview(box);
            
			String v_msg = "";
			box.put("p_process", "previewPage");
			
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
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}

///////////////////////////////////////////////////////////////////////////////
	/**
	차수조회 PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSubjseqPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
		}            
	}
}
