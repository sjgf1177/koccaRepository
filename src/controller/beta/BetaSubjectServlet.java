//**********************************************************
//1. ��      ��: �����ڵ� SERVLET(��Ÿ�׽�Ʈ�ý���)
//2. ���α׷���: BetaSubjectServlet.java
//3. ��      ��: �����ڵ� SERVLET(��Ÿ�׽�Ʈ�ý���)
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 0.1
//6. ��      ��: anonymous 2004. 12. 26
//7. ��      ��: 
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
import com.credu.course.CourseBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.BetaSubjectServlet")
public class BetaSubjectServlet extends HttpServlet implements Serializable {

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

			System.out.println("v_gadmin : " + box.getSession("gadmin"));
			System.out.println("v_userid : " + box.getSession("userid"));
			System.out.println("v_name : " + box.getSession("name"));

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			///////////////////////////////////////////////////////////////////
			// ���� check ��ƾ VER 0.2 - 2003.08.10
			if (!AdminUtil.getInstance().checkRWRight("BetaSubjectServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("listPage")) {      			//in case of ���� ��ȸ ȭ��
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("insertPage")) {      	//in case of ���� ��� ȭ��
				this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("insert")) {      		//in case of ���� ���
				this.performInsert(request, response, box, out);
			} else if (v_process.equals("updatePage")) {      	//in case of ���� ���� ȭ��
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("update")) {     		//in case of ���� ����
				this.performUpdate(request, response, box, out);
			} else if (v_process.equals("delete")) {      		//in case of ���� ����
				this.performDelete(request, response, box, out);
			} else if (v_process.equals("relatedGrcodePage")) { //in case of ������ �����׷캰 ��ȸ ȭ��
				this.performRelatedGrcodePage(request, response, box, out);
			} else if (v_process.equals("relatedGrcodeInsert")) { //in case of ������ �����׷캰 ���
				this.performRelatedGrcodeInsert(request, response, box, out);
			} else if (v_process.equals("previewPage")) { //in case of ���������� ��ȸ ȭ��
				this.performPreviewPage(request, response, box, out);
			} else if (v_process.equals("previewInsertPage")) { //in case of ���������� ��� ȭ��
				this.performPreviewInsertPage(request, response, box, out);
			} else if (v_process.equals("previewInsert")) { //in case of ���������� ���
				this.performPreviewInsert(request, response, box, out);
			} else if (v_process.equals("previewUpdatePage")) { //in case of ���������� ���� ȭ��
				this.performPreviewUpdatePage(request, response, box, out);
			} else if (v_process.equals("previewUpdate")) { //in case of ���������� ����
				this.performPreviewUpdate(request, response, box, out);
			} else if (v_process.equals("previewDelete")) { //in case of ���������� ����
				this.performPreviewDelete(request, response, box, out);
			} else if (v_process.equals("relatedSubjPage")) { //in case of �������� ��ȸ ȭ��
				this.performRelatedSubjPage(request, response, box, out);
			} else if (v_process.equals("relatedSubjInsertPage")) { //in case of �������� ��� ȭ��
				this.performRelatedSubjInsertPage(request, response, box, out);
			} else if (v_process.equals("relatedSubjInsert")) { //in case of �������� ���
				this.performRelatedSubjInsert(request, response, box, out);
			} else if (v_process.equals("subjseqPage")) { //in case of ������������Ʈ ��ȸ ȭ��
				this.performSubjseqPage(request, response, box, out);
			}	
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	�����ڵ帮��Ʈ VIEW
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/beta/admin/za_BetaSubject_L.jsp";

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
	�����ڵ� ��� PAGE - ���̹�
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
	�����ڵ� ��� - ���̹�
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
	�����ڵ� ���� PAGE - ���̹�
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
	�����ڵ� ���� - ���̹�
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
	�����ڵ� ����
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
	�����׷쿬�� PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performRelatedGrcodePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/admin/za_BetaSubjectGrcode_L.jsp";

			BetaSubjectBean bean = new BetaSubjectBean();

			ArrayList list1 = bean.TargetGrcodeList(box);
			request.setAttribute("TargetGrcodeList", list1);

			ArrayList list2 = bean.SelectedGrcodeList(box);
			request.setAttribute("SelectedGrcodeList", list2);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
		}            
	}
    
	/**
	�����׷쿬�� ���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performRelatedGrcodeInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";

			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.RelatedGrcodeInsert(box);
            
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
			throw new Exception("performRelatedGrcodeInsert()\r\n" + ex.getMessage());
		}            
	}

	/**
	���������� PAGE
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
	���������� ��� PAGE
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
	���������� ���
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
	���������� ���� PAGE
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
	�����ڵ� ����
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
	�����ڵ� ����
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

	/**
	������������Ʈ PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performRelatedSubjPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/admin/za_BetaRelatedSubject_L.jsp";

			BetaSubjectBean bean = new BetaSubjectBean();
			
			box.put("p_subjgubun","PRE");
			ArrayList list1 = bean.RelatedSubjList(box);
			request.setAttribute("PreRelatedSubjList", list1);

			box.put("p_subjgubun","NEXT");
			ArrayList list2 = bean.RelatedSubjList(box);
			request.setAttribute("NextRelatedSubjList", list2);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
		}            
	}

	/**
	������� PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performRelatedSubjInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/learn/admin/course/za_RelatedSubject_I.jsp";

			CourseBean bean0 = new CourseBean();
			ArrayList list1 = bean0.TargetSubjectList(box);
			request.setAttribute("TargetRelatedSubjList", list1);

			BetaSubjectBean bean = new BetaSubjectBean();
			ArrayList list2 = bean.SelectedRelatedSubjList(box);
			request.setAttribute("SelectedRelatedSubjList", list2);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
		}            
	}

	/**
	�������� ���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performRelatedSubjInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSubjectServlet";

			String v_grcode = box.getString("p_grcode");
			String v_subj   = box.getString("p_subj");
			String v_subjnm = box.getString("p_subjnm");

			BetaSubjectBean bean = new BetaSubjectBean();
			int isOk = bean.RelatedSubjInsert(box);
            
			String v_msg = "";
			box.clear();
			box.put("p_process", "relatedSubjPage");
			box.put("p_grcode", v_grcode);
			box.put("p_subj", v_subj);
			box.put("p_subjnm", v_subjnm);
			
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
			throw new Exception("performRelatedGrcodeInsert()\r\n" + ex.getMessage());
		}            
	}
///////////////////////////////////////////////////////////////////////////////
	/**
	������ȸ PAGE
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
