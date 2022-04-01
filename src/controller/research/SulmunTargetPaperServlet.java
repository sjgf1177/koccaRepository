//**********************************************************
//1. ��      ��: 
//2. ���α׷���: SulmunTargetPaperServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-18
//7. ��      ��: 
//                 
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

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunSubjPaperBean;
import com.credu.research.SulmunTargetPaperBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.research.SulmunTargetPaperServlet")
public class SulmunTargetPaperServlet extends HttpServlet implements Serializable {
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
			v_process = box.getStringDefault("p_process","SulmunPaperListPage");
            
			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
		
			if (!AdminUtil.getInstance().checkRWRight("SulmunTargetPaperServlet", v_process, out, box)) {
					return; 
			}

			if (v_process.equals("SulmunPaperListPage")) {      	
				this.performSulmunPaperListPage(request, response, box, out);
			} else if (v_process.equals("SulmunPaperInsertPage")) {      	
				this.performSulmunPaperInsertPage(request, response, box, out);
			} else if (v_process.equals("SulmunPaperUpdatePage")) {      	
				this.performSulmunPaperUpdatePage(request, response, box, out);
			} else if (v_process.equals("SulmunPaperInsert")) {
				this.performSulmunPaperInsert(request, response, box, out);
			} else if (v_process.equals("SulmunPaperUpdate")) {
				this.performSulmunPaperUpdate(request, response, box, out);
			} else if (v_process.equals("SulmunPaperDelete")) {
				this.performSulmunPaperDelete(request, response, box, out);
			} else if (v_process.equals("SulmunPaperPreviewPage")) {    	
				this.performSulmunPaperPreviewPage(request, response, box, out);
			} 
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	
  /**
  ����ڼ��� ������ ����Ʈ
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */	
	public void performSulmunPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunTargetPaper_L.jsp";
                        
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
			ArrayList list1 = bean.selectPaperList(box);
			request.setAttribute("SulmunPaperListPage", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunPaperListPage()\r\n" + ex.getMessage());
		}
	}

  /**
  ����ڼ��� ������ ��� ������ 
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */	
	public void performSulmunPaperInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunTargetPaper_I.jsp";

			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
			ArrayList list1 = bean.selectQuestionList(box);
			request.setAttribute("SulmunPaperInsertPage", list1);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);	
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunPaperInsertPage()\r\n" + ex.getMessage());
		}
	}

  /**
  ����ڼ��� ������ ���� ������
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */	
	public void performSulmunPaperUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunTargetPaper_U.jsp";
                        
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
			ArrayList list1 = bean.selectQuestionList(box);
			request.setAttribute("SulmunTargetQuestionList", list1);
			
			ArrayList list2 = bean.selectPaperQuestionList(box);
			request.setAttribute("SulmunTargetPaperQuestionList", list2);
			
			DataBox dbox1 = bean.getPaperData(box);
			request.setAttribute("SulmunPaperData", dbox1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);	
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunPaperUpdatePage()\r\n" + ex.getMessage());
		}
	}

  /**
  ����ڼ��� ������ ���
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */	
	public void performSulmunPaperInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{                
			String v_url  = "/servlet/controller.research.SulmunTargetPaperServlet";
           
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
			int isOk = bean.insertPaper(box);
            
			String v_msg = "";
			box.put("p_process", "SulmunPaperInsertPage");
			box.put("p_end",  "0");
      
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
			throw new Exception("performSulmunPaperInsert()\r\n" + ex.getMessage());
		}            
	}


  /**
  ����ڼ��� ������ ����
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */		
	public void performSulmunPaperUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{                
			String v_url  = "/servlet/controller.research.SulmunTargetPaperServlet";
           
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
			int isOk = bean.updatePaper(box);
            
			String v_msg = "";
			box.put("p_process", "SulmunPaperUpdatePage");
			box.put("p_end",  "0");
      
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
			throw new Exception("performSulmunPaperUpdate()\r\n" + ex.getMessage());
		}            
	}


  /**
  ����ڼ��� ������ ����
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */	
	public void performSulmunPaperDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{                
			String v_url  = "/servlet/controller.research.SulmunTargetPaperServlet";
           
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
			int isOk = bean.deletePaper(box);
            
			String v_msg = "";
			box.put("p_process", "SulmunPaperUpdatePage");
			box.put("p_end",  "0");
      
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else if(isOk==-2){                
				v_msg = "�ش� �������� ������Դϴ�.";   
				alert.alertFailMessage(out, v_msg);					
			}else {                
				v_msg = "delete.fail";   
				alert.alertFailMessage(out, v_msg);   
			}  
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunPaperDelete()\r\n" + ex.getMessage());
		}            
	}
	

  /**
  ����ڼ��� ������ �̸����� 
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */		
	public void performSulmunPaperPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
		    String v_return_url = "/learn/admin/research/za_SulmunTargetPaperPreview.jsp";
                        
			SulmunSubjPaperBean bean = new SulmunSubjPaperBean();
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
			throw new Exception("performSulmunPaperPreviewPage()\r\n" + ex.getMessage());
		}
	}
	
}
