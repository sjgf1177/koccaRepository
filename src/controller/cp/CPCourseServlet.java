//**********************************************************
//  1. ��      ��: �������� ����
//  2. ���α׷��� : CPCourseServlet.java
//  3. ��      ��: �������� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ��â�� 2004. 12.  21
//  7. ��     ��1:
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPCourseBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
*��������
*<p>����:CPCourseServlet.java</p>
*<p>����:�������� ����</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ��â��
*@version 1.0
*/
@WebServlet("/servlet/controller.cp.CPCourseServlet")
public class CPCourseServlet extends javax.servlet.http.HttpServlet {
    
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
        RequestBox box = null;
        String v_process = "";
        
        try {

            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

			if(v_process.equals("")){
				box.put("v_process","listPage");
				v_process = "listPage";
			}
			
			if (!AdminUtil.getInstance().checkRWRight("CPCourseServlet", v_process, out, box)) {
				return; 
			}
			
			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("insertPage")) {             //���� ����� ��������
                this.performInsertPage(request, response, box, out);
            }
            /*
            else if(v_process.equals("insert")) {             //db���ó��
                this.performInsert(request, response, box, out);
            }
            */
            else if(v_process.equals("updatePage")) {   //������ ��������
                this.performUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("update")) {   //db����ó��
                this.performUpdate(request, response, box, out);
            }
            else if(v_process.equals("listPage")) {   //���������ȸ
                this.performList(request, response, box, out);
            }
            else if(v_process.equals("CpApproval")) {       //cp��ü �������
				this.performCpApproval(request, response, box, out);
			}
			
			else if(v_process.equals("CpApproval1")) {       //cp��ü �������
				this.performCpApproval1(request, response, box, out);
			}
			
// ȭ�麯�� ����
			else if(v_process.equals("insert2")) {                                // 
                this.performInsert2(request, response, box, out);
            }
			else if(v_process.equals("listPage3")) {       //       
				this.performListPage3(request, response, box, out);
			}
			else if(v_process.equals("insertPage3")) {       //       
				this.performInsertPage3(request, response, box, out);
			}
			
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

	/**
	���־�ü ��������Ʈ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPCourseBean cpCourse = new CPCourseBean();
            
            ArrayList list = cpCourse.selectCourseList(box);
            
            request.setAttribute("selectCourseList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpcourse_l.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPCourseServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
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
			String v_return_url = "/cp/admin/za_cpsubject_i.jsp";

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
	/*	
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.cp.CPCourseServlet";
           
			CPCourseBean cpbean = new CPCourseBean();
			int isOk = cpbean.InsertSubject(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			
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
	*/

	/**
	�������� ���������� - ���̹�
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
			String v_url = "/cp/admin/za_cpcourse_u.jsp";
			//String v_url = "/servlet/controller.cp.CPCourseServlet";
            box.put("p_process", "");
            
			CPCourseBean cpCourse = new CPCourseBean();
			DataBox dbox = cpCourse.SelectSubjectData(box);
			request.setAttribute("SubjectData", dbox);
			
			DataBox dbox1 = cpCourse.SelectCpParamData(box);
			request.setAttribute("CpParamData", dbox1);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}            
	}


	/**
	�������� ���� - ���̹�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.cp.CPCourseServlet";

			CPCourseBean cpCourse = new CPCourseBean();
			int isOk = cpCourse.UpdateSubject(box);
            
			String v_msg = "";
			box.put("p_process", "");
			
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
	ȭ�麯��
	�н���Ȳ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performInsert2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

//            CPCourseBean cpCourse = new CPCourseBean();
            
//            ArrayList list = cpCourse.selectCourseList(box);
            
//            request.setAttribute("insert2", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_studystatus_i.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPCourseServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    
	/**
	ȭ�麯��
	�����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performListPage3(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPCourseBean cpCourse = new CPCourseBean();
            
            ArrayList list = cpCourse.selectCourseList(box);
            
            request.setAttribute("listPage3", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_courseop_l.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPCourseServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

	/**
	ȭ�麯��
	�����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performInsertPage3(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

//            CPCourseBean cpCourse = new CPCourseBean();
            
//            ArrayList list = cpCourse.selectCourseList(box);
            
//            request.setAttribute("insertPage3", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_courseop_i.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPCourseServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    
    /**
	cp��ü �������
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCpApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.cp.CPCourseServlet";

			CPCourseBean cpCourse = new CPCourseBean();
			int isOk = cpCourse.UpdateCpApproval(box);
            
			String v_msg = "";
			box.put("p_process", "");
			
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
	cp��ü �������1
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCpApproval1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.cp.CPCourseServlet";

			CPCourseBean cpCourse = new CPCourseBean();
			int isOk = cpCourse.UpdateCpApproval1(box);
            
			String v_msg = "";
			box.put("p_process", "");
			
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
}