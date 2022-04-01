//**********************************************************
//1. 제      목: 
//2. 프로그램명: FinishServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-11
//7. 수      정: 
//                 
//********************************************************** 
 
package controller.complete;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.complete.FinishBean;
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
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.complete.FinishServlet")
public class FinishServlet extends HttpServlet implements Serializable {

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

			if (!AdminUtil.getInstance().checkRWRight("FinishServlet", v_process, out, box)) {
				return; 
			}

			if (v_process.equals("listPage")) {      					//in case of 수료 처리 조회 화면
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("subjectSelect")) {      		//in case of 수료 처리 상세 화면
				this.performSubjectSelect(request, response, box, out);
			} else if (v_process.equals("subjectComplete")) {      		//in case of 과정 수료 처리
				this.performSubjectComplete(request, response, box, out);
			} else if (v_process.equals("subjectCompleteCancel")) {  	//in case of 과정 수료 취소
				this.performSubjectCompleteCancel(request, response, box, out);
			} else if (v_process.equals("scoreCompute")) {      		//in case of 점수 계산
				this.performScoreCompute(request, response, box, out);
			} else if (v_process.equals("courseScoreCompute")) {      		//in case of 코스 수료 계산
				this.performCourseScoreCompute(request, response, box, out);
			} else if (v_process.equals("courseScoreCancel")) {      		//in case of 코스 수료 취소 
				this.performCourseScoreCancel(request, response, box, out);
			} else if (v_process.equals("offSubjectInputPage")) {      		//in case of 집합과정 입력
				this.performOffSubjectInputPage(request, response, box, out);
			} else if (v_process.equals("offSubjectCompleteNew")) {      		//in case of 집합과정 수료처리
				this.performOffSubjectCompleteNew(request, response, box, out);
			} else if (v_process.equals("offSubjectCompleteCancel")) {      //in case of 집합과정 수료처리 취소
				this.performOffSubjectCompleteCancel(request, response, box, out);
			} else if (v_process.equals("changeNotgraduPage")) {      		//미수료사유 변경 페이지
				this.performChangeNotgraduPage(request, response, box, out);
			} else if (v_process.equals("changeNotgraduSave")) {      		//미수료사유 변경
				this.performChangeNotgraduSave(request, response, box, out);
			} else if (v_process.equals("changeGraduated")) {      			//수료,미수료 변경
				this.performChangeGraduated(request, response, box, out);
			} else if (v_process.equals("offSubjIsClosed")) {      			//집합과정 수료처리
				this.performOffSubjIsClosed(request, response, box, out);
			} else if (v_process.equals("outSubjIsClosed")) {      			//외주업체 과정 수료처리
				this.performOutSubjIsClosed(request, response, box, out);
			} else if (v_process.equals("outSubjReject")) {      			//외주업체 과정 결과재요청
				this.performOutSubjReject(request, response, box, out);
			} else if (v_process.equals("OffStudentExcel")) {      		    //집합과정 수강생명단 EXCEL 출력
				this.performOffStudentExcel(request, response, box, out);
			} else if (v_process.equals("subjectCompleteRerating")) {      	//사이버 점수재계산
				this.performSubjectCompleteRerating(request, response, box, out);
			} else if (v_process.equals("offSubjectCompleteRerating")) {      	//집합 점수재계산
				this.performOffSubjectCompleteRerating(request, response, box, out);
			} else if (v_process.equals("FinishStudentList")) {      	// 상세화면 엑셀보기
				this.performFinishStudentList(request, response, box, out);
            } else if (v_process.equals("totalSoryo")) {      	// 선택한 과정을 단체로 수료처리 혹은 수료 취소
				this.performTotalSoryo(request, response, box, out);
			} else if (v_process.equals("ListExcel")) {      	// 리스트 엑셀보기
				this.performListExcel(request, response, box, out);
			} else if (v_process.equals("totalsocrecal")) {		// 일관 재계산 버튼
				this.performTotalScoreCal(request, response, box, out);
			}
			
			//else if (v_process.equals("requestApprovalPage")) {      		//결재요청 페이지
			//	this.performRequestApprovalPage(request, response, box, out);
			//} 
			//else if (v_process.equals("requestApprovalSave")) {      		//결재요청
			//	this.performrequestApprovalSave(request, response, box, out);
			//} 
			//else if (v_process.equals("requestApprovalCancel")) {      		//결재요청취소
			//	this.performrequestApprovalCancel(request, response, box, out);
			//}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	
	/**
	수료처리 리스트 VIEW
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/complete/za_Finish_L1.jsp";
                      
			FinishBean bean = new FinishBean();
			ArrayList list1 = bean.SelectCompleteList(box);
			request.setAttribute("CompleteList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
	
	/**
	수료처리 상세화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSubjectSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/complete/za_FinishStudent_L1.jsp";

			FinishBean bean = new FinishBean();
			ArrayList list1 = bean.CompleteStudentList(box);
			request.setAttribute("CompleteStudentList", list1);
            
            DataBox dbox = bean.SelectSubjseqInfoDbox(box);
			request.setAttribute("SubjseqData", dbox);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjectSelect()\r\n" + ex.getMessage());
		}
	}
    
	/**
	사이버 과정수료 처리
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSubjectComplete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk = bean.SubjectComplete(box);
			String v_msg = box.getString("p_return_msg");
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				//v_msg = "insert.ok";       
				v_msg = "수료처리 되었습니다.";       
				//box.put("p_process", "listPage");
				box.put("p_process", "listPage");
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjectComplete()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	사이버 과정 점수재계산
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSubjectCompleteRerating(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk = bean.SubjectCompleteRerating(box);
			String v_msg = box.getString("p_return_msg");
			String p_studentlist = box.getString("p_studentlist"); // 상세화면

			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				//v_msg = "insert.ok";       
				v_msg = "점수재계산 되었습니다.";       
				
				if(!p_studentlist.equals("")){
    				box.put("p_process", "subjectSelect");				
    			}else{
    				box.put("p_process", "listPage");				
    		    }
				
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjectCompleteRerating()\r\n" + ex.getMessage());
		}            
	}	
	
	
	
	
	/**
	수료 처리취소 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSubjectCompleteCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_msg = box.getString("p_return_msg");
			String v_process = box.getString("p_rprocess");
			String v_url = box.getString("p_returnurl");
           
			FinishBean bean = new FinishBean();
			int isOk = bean.SubjectCompleteCancel(box);
			//String v_msg = box.getString("p_return_msg");
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				//v_msg = "insert.ok";       
				v_msg = "수료처리가 취소되었습니다.";       
				//box.put("p_process", "listPage");
				box.put("p_process", v_process);
				
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjectCompleteCancel()\r\n" + ex.getMessage());
		}            
	}

	/**
	과정 점수계산
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performScoreCompute(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk = bean.ScoreCompute(box);
			String v_msg = box.getString("p_return_msg");
			
			AlertManager alert = new AlertManager();
			if(isOk == 1) {            	
				v_msg = "insert.ok";       
				box.put("p_process", "listPage");
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performScoreCompute()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	코스 점수계산
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCourseScoreCompute(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk = bean.CourseScoreCompute(box);
			String v_msg = "[" + String.valueOf(isOk) + "]" + box.getString("p_return_msg");
			
			AlertManager alert = new AlertManager();
			if(isOk == 1) {            	
				v_msg = "insert.ok";       
				box.put("p_process", "listPage");
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performCourseScoreCompute()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	코스 수료 취소.
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCourseScoreCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk = bean.CourseScoreCancel(box);
			String v_msg = "[" + String.valueOf(isOk) + "]" + box.getString("p_return_msg");
			
			AlertManager alert = new AlertManager();
			if(isOk == 1) {            	
				v_msg = "scomodify.ok";       
				box.put("p_process", "listPage");
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performCourseScoreCompute()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	수료처리 리스트 VIEW
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOffSubjectInputPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/complete/za_OffSubjFinish_L.jsp";
                        
			FinishBean bean = new FinishBean();
			ArrayList list1 = bean.SelectOffCompleteList(box);
			request.setAttribute("OffCompleteList", list1);
            
			DataBox dbox = bean.SelectSubjseqInfoDbox(box);
			request.setAttribute("SubjseqData", dbox);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
	
	/**
	수료처리 리스트 VIEW
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOffStudentExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/complete/za_OffSubjFinish_E.jsp";
                        
			FinishBean bean = new FinishBean();
			ArrayList list1 = bean.SelectOffCompleteList(box);
			request.setAttribute("OffCompleteList", list1);
            
			DataBox dbox = bean.SelectSubjseqInfoDbox(box);
			request.setAttribute("SubjseqData", dbox);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
	
		
	/**
	집합과정 수료 처리
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	//public void performOffSubjectComplete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
	//	throws Exception {   
	//	try{
	//		
	//		//String v_url  = "/servlet/controller.complete.FinishServlet";
	//		FinishBean bean = new FinishBean();
	//		int isOk = bean.OffSubjectComplete(box);
	//		String v_msg = box.getString("p_return_msg");
	//		String v_process = box.getString("p_rprocess");
	//		String v_url = box.getString("p_returnurl");
    //
	//		AlertManager alert = new AlertManager();                        
	//		if(isOk == 1) {            	
	//			v_msg = "수료처리가 완료되었습니다.";
	//			box.put("p_process", v_process);
	//			//box.put("p_process", "listpage");
	//			//box.put("p_process", "subjectSelect");
	//			box.put("p_action",  "go");
	//			
	//			alert.alertOkMessage(out, v_msg, v_url , box);
	//		}else {                
	//			alert.alertFailMessage(out, v_msg);   
	//		}                                          
	//	}catch (Exception ex) {           
	//		ErrorManager.getErrorStackTrace(ex, out);
	//		throw new Exception("performOffSubjectComplete()\r\n" + ex.getMessage());
	//	}            
	//}
	
	
	
	/**
	집합과정 수료 처리
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOffSubjectCompleteNew(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_msg = box.getString("p_return_msg");
			String v_process = box.getString("p_rprocess");
			String v_url = box.getString("p_returnurl");
           
			FinishBean bean = new FinishBean();
			int isOk = bean.OffSubjectCompleteNew(box);
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				v_msg = "수료처리가 완료되었습니다.";
				box.put("p_process", v_process);
				//box.put("p_process", "subjectSelect");
				box.put("p_action",  "go");
				
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performOffSubjectComplete()\r\n" + ex.getMessage());
		}            
	}

	/**
	집합과정 점수재계산
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOffSubjectCompleteRerating(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_msg = box.getString("p_return_msg");
			String v_process = box.getString("p_rprocess");
			String v_url = box.getString("p_returnurl");
           
			FinishBean bean = new FinishBean();
			int isOk = bean.OffSubjectCompleteRerating(box);
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				v_msg = "점수재계산 되었습니다.";
				box.put("p_process", v_process);
				//box.put("p_process", "subjectSelect");
				box.put("p_action",  "go");
				
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performOffSubjectCompleteRerating()\r\n" + ex.getMessage());
		}            
	}
	
		
	/**
	수료 처리취소 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOffSubjectCompleteCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_msg = box.getString("p_return_msg");
			String v_process = box.getString("p_rprocess");
			String v_url = box.getString("p_returnurl");
           
			FinishBean bean = new FinishBean();
			int isOk = bean.SubjectCompleteCancel(box);
			
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				//v_msg = "insert.ok";
				v_msg = "수료처리가 취소 되었습니다.";
				//box.put("p_process", "offSubjectInputPage");
				box.put("p_process", v_process);
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performOffSubjectCompleteCancel()\r\n" + ex.getMessage());
		}            
	}
	
	/**
    결재요청 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performRequestApprovalPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //    throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);  
    //        ProposeCourseBean bean  = new ProposeCourseBean();
    //
    //        DataBox dbox  = bean.getSelectChief(box);
    //        request.setAttribute("selectChief", dbox);
    //        
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_CompleteApproval_I.jsp"); 
    //        rd.forward(request, response);
    //        
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
    //    }
    //}
    
	
	/**
    미수료 사유변경 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performChangeNotgraduPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
//            FinishBean bean  = new FinishBean();

            ServletContext sc = getServletContext();
            //RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_ChangeNotgradu_U.jsp"); 
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_ChangeNotgraduAll_U.jsp");  // 2005.8.18 update by 정은년
            rd.forward(request, response);
            
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performChangeNotgraduPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
	미이수사유 저장 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performChangeNotgraduSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			//int isOk = bean.updateNotgraducd(box);
			int isOk = bean.updateNotgraducdAll(box);  // 2005.8.18 update by 정은년
			String v_msg = "";

			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				v_msg = "save.ok"; 
				box.put("p_action",  "go");
				if (box.getString("p_frompage").equals("off")) {
					//집합과정 수료처리 팝업에서 호출한 경우
					box.put("p_process", "offSubjectInputPage");
				} else {
					box.put("p_process", "subjectSelect");
				}
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performChangeNotgraduSave()\r\n" + ex.getMessage());
		}            
	}

	/**
	이수,미이수 저장 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performChangeGraduated(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk = bean.updateGraduated(box);
			String v_msg = "";

			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				//v_msg = "insert.ok"; 
				v_msg = "변경 되었습니다."; 
				
				if (box.getString("p_frompage").equals("off")) {
					//집합과정 수료처리 팝업에서 호출한 경우
					box.put("p_process", "offSubjectInputPage");
				} else {
					box.put("p_process", "subjectSelect");
				}
				
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performChangeNotgraduSave()\r\n" + ex.getMessage());
		}            
	}
	
	
	/**
	집합과정 수료처리 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOffSubjIsClosed(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk1 = bean.updateOffSubjIsClosed(box);
			String v_msg = "";
    
			System.out.println("New 집합 수료처리 결과 = " + isOk1);
			AlertManager alert = new AlertManager();                        
			if(isOk1 == 1) {            	
				//v_msg = "insert.ok"; 
				v_msg = "수료처리가 완료되었습니다."; 
				box.put("p_process", "subjectSelect");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performChangeNotgraduSave()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	외주과정 수료처리 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOutSubjIsClosed(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
			String v_rprocess = box.getString("p_rprocess");
			box.put("p_process", v_rprocess);
			System.out.println(v_rprocess);
           
			FinishBean bean = new FinishBean();
			int isOk = bean.updateOutSubjIsClosed(box);
			String v_msg = "";
    
		
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				//v_msg = "insert.ok"; 
				v_msg = "수료처리가 완료되었습니다."; 
				box.put("p_process", "subjectSelect");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performChangeNotgraduSave()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	외주과정 결과재요청 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performOutSubjReject(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.complete.FinishServlet";
           
			FinishBean bean = new FinishBean();
			int isOk = bean.updateOutSubjReject(box);
			String v_msg = "";
    
		
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				//v_msg = "insert.ok"; 
				v_msg = "결과입력을 재요청하였습니다."; 
				box.put("p_process", "subjectSelect");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performChangeNotgraduSave()\r\n" + ex.getMessage());
		}            
	}


	/**
	수료처리자 명단 엑셀출력 화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performFinishStudentList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/complete/za_FinishStudent_E.jsp";
                        
			FinishBean bean = new FinishBean();
			ArrayList list1 = bean.CompleteStudentList(box);
			request.setAttribute("FinishStudentList", list1);
            
            DataBox dbox = bean.SelectSubjseqInfoDbox(box);
			request.setAttribute("SubjseqData", dbox);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performFinishStudentList()\r\n" + ex.getMessage());
		}
	}


	/**
	수료처리 리스트 엑셀출력화면
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/complete/za_Finish_E.jsp";
                      
			FinishBean bean = new FinishBean();
			ArrayList list1 = bean.SelectCompleteList(box);
			request.setAttribute("CompleteListExcel", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {          
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListExcel()\r\n" + ex.getMessage());
		}
	}	
		
	/**
	수료처리 완료/미완료 저장 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	//public void performChangeIsClosed(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
	//	throws Exception {   
	//	try{                
	//		String v_url  = "/servlet/controller.complete.FinishServlet";
    //       
	//		FinishBean bean = new FinishBean();
	//		int isOk = bean.updateIsClosed(box);
	//		String v_msg = "";
    //
	//	
	//		AlertManager alert = new AlertManager();                        
	//		if(isOk == 1) {            	
	//			//v_msg = "insert.ok"; 
	//			v_msg = ""; 
	//			box.put("p_process", "subjectSelect");
	//			alert.alertOkMessage(out, v_msg, v_url , box, true, true);
	//		}else {                
	//			alert.alertFailMessage(out, v_msg);   
	//		}                                          
	//	}catch (Exception ex) {           
	//		ErrorManager.getErrorStackTrace(ex, out);
	//		throw new Exception("performChangeNotgraduSave()\r\n" + ex.getMessage());
	//	}            
	//}

    //체크된 과정만 수료를 일괄적으로 처리
	public void performTotalSoryo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{
            request.setAttribute("requestbox", box);
//			String v_url  = "/servlet/controller.complete.FinishServlet";

			FinishBean bean = new FinishBean();
			int isOk = bean.jumpProcess(box);

            /*
            String v_msg = box.getString("p_return_msg");
			AlertManager alert = new AlertManager();
			if(isOk == 1) {
				v_msg = "수료처리 되었습니다.";
				box.put("p_process", "listPage");
				box.put("p_action",  "go");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {
				alert.alertFailMessage(out, v_msg);
			} */

            performListPage(request,response,box,out);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performTotalSoryo()\r\n" + ex.getMessage());
		}
	}
	
	//체크된 과정만 일괄 재계산 처리
	public void performTotalScoreCal(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{
            request.setAttribute("requestbox", box);

			FinishBean bean = new FinishBean();
			box.put("p_totscorecal", "Y");
			int isOk = bean.jumpProcess(box);
			
			AlertManager alert = new AlertManager();
			
			if(isOk ==1){
				box.put("p_process","listPage");
				box.put("p_action", "go");
				alert.alertOkMessage(out, "일괄 재계산 처리가 완료되었습니다", "/servlet/controller.complete.FinishServlet", box);
			}else{
				alert.alertFailMessage(out, "실패하였습니다.");
			}
			
            performListPage(request,response,box,out);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performTotalSoryo()\r\n" + ex.getMessage());
		}
	}
}
