//**********************************************************
//1. 제      목: 과정모의학습 SERVLET(베타테스트시스템)
//2. 프로그램명: SubjectSimulationServlet.java
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
import com.credu.beta.SubjectSimulationBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.SubjectSimulationServlet")
public class SubjectSimulationServlet extends HttpServlet implements Serializable {

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
			if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("listPage")) {      			//in case of 과정 조회 화면
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("resultinsert")) {     		//1차 상신, 최종 상신
				this.performResultInsert(request, response, box, out);
			} else if (v_process.equals("resultupdate")) {     		//상신 결재
				this.performResultUpdate(request, response, box, out);
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
			} else if (v_process.equals("progressDelete")) { //in case of 과정맛보기 삭제
				this.performProgressDelete(request, response, box, out);
			}	

		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	과정모의학습리스트 VIEW
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

			SubjectSimulationBean bean = new SubjectSimulationBean();
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
	1차상신 , 최종상신 등록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
		throws Exception {
		try{
			String v_url  = "/servlet/controller.beta.SubjectSimulationServlet";

			SubjectSimulationBean bean = new SubjectSimulationBean();
			int isOk = bean.ResultInsert(box);

			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_upperclass", "ALL");
			//box.put("p_subj", "ALL");

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
	1차상신 , 최종상신 결재
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performResultUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
		throws Exception {
		try{
			String v_url  = "/servlet/controller.beta.SubjectSimulationServlet";

			SubjectSimulationBean bean = new SubjectSimulationBean();
			int isOk = bean.ResultUpdate(box);

			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_upperclass", "ALL");
			//box.put("p_subj", "ALL");

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
			throw new Exception("performPreviewInsertPage()\r\n" + ex.getMessage());
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
			throw new Exception("performPreviewInsert()\r\n" + ex.getMessage());
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
			throw new Exception("performPreviewUpdatePage()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	과정맛보기 수정
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
			throw new Exception("performPreviewUpdate()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	과정맛보기 삭제
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
			throw new Exception("performPreviewDelete()\r\n" + ex.getMessage());
		}            
	}

	/**
	진도 삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performProgressDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.SubjectSimulationServlet";

			SubjectSimulationBean bean = new SubjectSimulationBean();
			int isOk = bean.DeleteProgress(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = isOk+"건의 진도가 삭제되었습니다.";       
//				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				v_msg = "삭제할 진도가 없습니다.";   
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performProgressDelete()\r\n" + ex.getMessage());
		}            
	}


}
