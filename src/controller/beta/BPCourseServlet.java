/**
*베타테스트시스템의 운영자에게를 제어하는 서블릿
*<p>제목:BPCourseServlet.java</p>
*<p>설명:베타테스트시스템Admin서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
package controller.beta;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BPCourseBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.beta.BPCourseServlet")
public class BPCourseServlet extends javax.servlet.http.HttpServlet {
    
    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");
            
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            
			
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("listPage")) {   //과정목록조회
                this.performList(request, response, box, out);
            }
			else if(v_process.equals("updatePage")) {   //수정폼 페이지로
                this.performUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("update")) {   //db수정처리
                this.performUpdate(request, response, box, out);
            }
            else if(v_process.equals("BpApproval")) {       //cp업체 과정상신
				this.performBpApproval(request, response, box, out);
			}			
			else if(v_process.equals("BpApproval1")) {       //cp업체 과정상신
				this.performBpApproval1(request, response, box, out);
			}			

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

	/**
	외주업체 과정리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            BPCourseBean cpCourse = new BPCourseBean();            
            ArrayList list = cpCourse.selectCourseList(box);            
            request.setAttribute("selectCourseList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/admin/za_BPCourse_L.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.beta.BPCourseServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    

	/**
	과정정보 수정페이지 - 사이버
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
            
			BPCourseBean cpCourse = new BPCourseBean();
			DataBox dbox = cpCourse.SelectSubjectData(box);
			request.setAttribute("SubjectData", dbox);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/beta/admin/za_BPCourse_U.jsp"); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}            
	}


	/**
	과정정보 수정 - 사이버
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BPCourseServlet";

			BPCourseBean cpCourse = new BPCourseBean();
			int isOk = cpCourse.UpdateSubject(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			
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
	cp업체 과정상신
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBpApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BPCourseServlet";

			BPCourseBean cpCourse = new BPCourseBean();
			int isOk = cpCourse.UpdateCpApproval(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			
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
			throw new Exception("performBpApproval()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	cp업체 과정상신1
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performBpApproval1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BPCourseServlet";

			BPCourseBean cpCourse = new BPCourseBean();
			int isOk = cpCourse.UpdateCpApproval1(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			
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
			throw new Exception("performBpApproval1()\r\n" + ex.getMessage());
		}            
	}
}

