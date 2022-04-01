//**********************************************************
//1. 제      목: 교육차수관리 SERVLET
//2. 프로그램명: ChangeSeqApprovalServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 14
//7. 수      정: 
//                 
//**********************************************************
package controller.propose;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ChangeSeqApprovalBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.propose.ChangeSeqApprovalServlet")
public class ChangeSeqApprovalServlet extends HttpServlet implements Serializable {

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
			if (!AdminUtil.getInstance().checkRWRight("ChangeSeqApprovalServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			
			if (v_process.equals("listPage")) {      			//수강신청승인 리스트 조회 화면
				this.performListPage(request, response, box, out);
			} 
			else if (v_process.equals("ChangeApproval")) {   //Batch 승인처리
				this.performChangeApproval(request, response, box, out);
			}

			//else if (v_process.equals("SubjInfo")) {           //결재상신시 과정상세정보
			//	this.performSubjInfo(request, response, box, out);
				
/*			} else if (v_process.equals("assignSave")) {     		//교육차수에 과정/코스 연결저장
				this.performAssignSave(request, response, box, out);
			} else if (v_process.equals("delCourse")) {     		//지정 코스 삭제
				this.performDelSubjcourse(request, response, box, out);
			} else if (v_process.equals("delSubj")) {     			//지정 과정 삭제
				this.performDelSubjcourse(request, response, box, out);
			} else if (v_process.equals("addCourse")) {     		//코스차수 추가
				this.performAddSubjcourse(request, response, box, out);
			} else if (v_process.equals("addSubj")) {     			//과정차수 추가
				this.performAddSubjcourse(request, response, box, out);
			} else if (v_process.equals("delApproval")) {      		//교육차수 삭제
				this.performDelApproval(request, response, box, out);
			}
*/			
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	신청승인 리스트 조회
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			ChangeSeqApprovalBean bean = new ChangeSeqApprovalBean();
            
			ArrayList list1 = bean.selectChangeApprovalList(box);
			request.setAttribute("ApprovalList", list1);
				
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ChangeSeqApproval_L.jsp");
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	신청승인 리스트 조회
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performChangeApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			ChangeSeqApprovalBean bean = new ChangeSeqApprovalBean();
            int isOk = bean.UpdateChangeSeqApproval(box);
            String v_msg = "";
            String v_url = "/learn/admin/propose/za_ChangeSeqApproval_L.jsp";
            box.put("p_process","listPage");
            
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
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
	
}