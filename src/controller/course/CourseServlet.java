//**********************************************************
//1. ��      ��: �ڽ��ڵ� SERVLET
//2. ���α׷���: CourseServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: anonymous 2003. 07. 07
//7. ��      ��: 2003.10.29.. LeeSuMin.. add courseseq, subjseq control.
//                 
//**********************************************************
package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.CourseBean;
import com.credu.course.CourseData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.course.CourseServlet")
public class CourseServlet extends HttpServlet implements Serializable {

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
	@SuppressWarnings("unchecked")
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

			if (!AdminUtil.getInstance().checkRWRight("CourseServlet", v_process, out, box)) {
				return; 
			}

			if (v_process.equals("listPage")) {      			//in case of �ڽ� ��ȸ ȭ��
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("insertPage")) {      	//in case of �ڽ� ��� ȭ��
				this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("insert")) {      		//in case of �ڽ� ���
				this.performInsert(request, response, box, out);
			} else if (v_process.equals("updatePage")) {      	//in case of �ڽ� ���� ȭ��
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("update")) {     		//in case of �ڽ� ����
				this.performUpdate(request, response, box, out);
			} else if (v_process.equals("delete")) {      		//in case of �ڽ� ����
				this.performDelete(request, response, box, out);
			} else if (v_process.equals("listSeqPage")) {      			//�ڽ���������Ʈ  ��ȸ ȭ��
				this.performCourseseqListPage(request, response, box, out);
			} else if (v_process.equals("subjseqList")) {      			//������������Ʈ  ��ȸ ȭ��
				this.performSubjseqListPage(request, response, box, out);
			} else if (v_process.equals("updateCourseseqPage")) {      			//�ڽ����� ���� ����ȭ��
				this.performUpdateCourseseqPage(request, response, box, out);
			} else if (v_process.equals("updateCourseseq")) {      			//�ڽ����� ���� ���� ����
				this.performUpdateCourseseq(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	�ڽ��ڵ� VIEW
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/course/za_Course_L.jsp";
                        
			CourseBean bean = new CourseBean();
			ArrayList list1 = bean.SelectCourseList(box);
			request.setAttribute("CourseList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
    
	/**
	�ڽ��ڵ� ��� PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/course/za_Course_I.jsp";
            
            CourseBean bean = new CourseBean();
			ArrayList list1 = bean.TargetSubjectList(box);
			request.setAttribute("TargetSubjectList", list1);

			ArrayList list2 = bean.SelectedSubjectList(box);
			request.setAttribute("SelectedSubjectList", list2);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}            
	}

	/**
	�ڽ��ڵ� ���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.course.CourseServlet";
           
			CourseBean bean = new CourseBean();
			int isOk = bean.InsertCourse(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_coursenm",    "");
      
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
	�ڽ��ڵ� ���� PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/learn/admin/course/za_Course_U.jsp";
            
			CourseBean bean = new CourseBean();
			ArrayList list1 = bean.TargetSubjectList(box);
			request.setAttribute("TargetSubjectList", list1);

			ArrayList list2 = bean.SelectedSubjectList(box);
			request.setAttribute("SelectedSubjectList", list2);

			CourseData data = bean.SelectCourseData(box);
			box.put("p_course", data.getCourse());
			box.put("p_coursenm", data.getCoursenm());
			box.put("p_gradscore", String.valueOf(data.getGradscore()));
			box.put("p_gradfailcnt", String.valueOf(data.getGradfailcnt()));
			box.put("p_biyong",  String.valueOf(data.getBiyong()));
			box.put("p_subjcnt", String.valueOf(data.getSubjcnt()));
			box.put("p_upperclass_i", String.valueOf(data.getUpperclass()));			

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}            
	}
    
	/**
	�ڽ��ڵ� ����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.course.CourseServlet";

			CourseBean bean = new CourseBean();
			int isOk = bean.UpdateCourse(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_coursenm",    "");
			
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "update.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {             
				if(isOk==-99)   v_msg = "�ڽ������� �����ϹǷ� ������ �Ұ����մϴ�";
				else			v_msg = "update.fail";   
				
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}
    
	/**
	�ڽ��ڵ� ����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.course.CourseServlet";
           
			CourseBean bean = new CourseBean();
			int isOk = bean.DeleteCourse(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("p_course", "");
			box.put("p_coursenm", "");
            
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				if(isOk==-99)   v_msg = "�ڽ������� �����ϹǷ� ������ �Ұ����մϴ�";
				else			v_msg = "update.fail";   
				
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}            
	}

	/**
	�ڽ����� ���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performCourseseqListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/course/za_Courseseq_L.jsp";
                        
			CourseBean bean = new CourseBean();
			ArrayList list1 = bean.SelectCourseseqList(box);
			request.setAttribute("CourseseqList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performCourseseqListPage()\r\n" + ex.getMessage());
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
	@SuppressWarnings("unchecked")
	public void performSubjseqListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/course/za_Subjseq_L.jsp";
                        
			CourseBean bean = new CourseBean();
			ArrayList list1 = bean.SelectSubjseqList(box);
			request.setAttribute("subjseqList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjseqListPage()\r\n" + ex.getMessage());
		}
	}	
	/**
	�ڽ����� ����ȭ��
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdateCourseseqPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/course/za_Courseseq_U.jsp";
                        
			CourseBean bean = new CourseBean();
			CourseData data = bean.SelectCourseseqData(box);
			request.setAttribute("courseseqData", data);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateCourseseqPage()\r\n" + ex.getMessage());
		}
	}
	/**
	�ڽ��������� ����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performUpdateCourseseq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.course.GrseqServlet";
           
			CourseBean bean = new CourseBean();
			int isOk = bean.UpdateCourseseq(box);
            
			String v_msg = "";
            
            box.put("p_process", "listPage");
            box.put("p_action", "go");
            box.put("s_grcode",box.getString("p_grcode"));
            box.put("s_gyear",box.getString("p_gyear"));
            box.put("s_grseq",box.getString("p_grseq"));
      
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, true, true);				
			}else {                
				v_msg = "update.fail";   
				alert.alertFailMessage(out, v_msg);   
			}                                          
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateCourseseq()\r\n" + ex.getMessage());
		}            
	}			
}



