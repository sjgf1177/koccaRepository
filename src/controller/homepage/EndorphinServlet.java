//*********************************************************
//  1. 제      목: 엔돌핀 
//  2. 프로그램 명: EndorphinServlet.java
//  3. 개      요: 엔돌핀 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.EndorphinBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.EndorphinServlet")
public class EndorphinServlet extends javax.servlet.http.HttpServlet implements Serializable {    
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
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;
  
        try {
            response.setContentType("text/html;charset=euc-kr");            
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

			if(v_process.equals("")) v_process = "listPage";

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("EndorphinServlet", v_process, out, box)) {
				return; 
			}

			///////////////////////////////////////////////////////////////////
			if(v_process.equals("List")){         //in case of project detail list
                this.performList(request, response, box, out);
			}else if(v_process.equals("Insert")){         //in case of project detail list
                this.performInsert(request, response, box, out);
			}else if(v_process.equals("Update")){         //in case of project detail list
                this.performUpdate(request, response, box, out);
			}else if(v_process.equals("Delete")){         //in case of project detail list
                this.performDelete(request, response, box, out);
			}else if(v_process.equals("selectList")){         //in case of project detail list
                this.performSelectList(request, response, box, out);
			}else if(v_process.equals("selectInsert")){         //in case of project detail list
                this.performSelectInsert(request, response, box, out);
            }   
   
          }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    엔돌핀풀 목록보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
			request.setAttribute("requestbox", box); 
			EndorphinBean bean = new EndorphinBean();
			ArrayList list1 = bean.list(box);
			request.setAttribute("endorphinList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_EndorphinMaster_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }



	/**
	 엔돌핀풀 INSERT
	 @param request  encapsulates the request to the servlet
	 @param response encapsulates the response from the servlet
	 @param box      receive from the form object
	 @param out      printwriter object
	 @return void
	 */
	 public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		 throws Exception {
		 try {
			 request.setAttribute("requestbox", box);   
			 EndorphinBean bean = new EndorphinBean();
                        
			 int isOk = bean.insert(box);
			 String v_msg = "";                     
			 String v_url = "/servlet/controller.homepage.EndorphinServlet";
			 box.put("p_process","List");
            
			 AlertManager alert = new AlertManager();
                        
			 if(isOk > 0) {
				 v_msg = "insert.ok";       
				 alert.alertOkMessage(out, v_msg, v_url , box, false, false);
			 }
			 else {
				 v_msg = "insert.fail";
				 alert.alertFailMessage(out, v_msg); 
			 } 
		 }catch (Exception ex) {           
			 ErrorManager.getErrorStackTrace(ex, out);
			 throw new Exception("performInsert()\r\n" + ex.getMessage());
		 }
	 }     
	 
	/**
	엔돌핀풀 UPDATE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try {
			request.setAttribute("requestbox", box);   
			EndorphinBean bean = new EndorphinBean();
			int isOk = bean.update(box);
            
			String v_msg = "";                     
			String v_url = "/servlet/controller.homepage.EndorphinServlet";
			box.put("p_process","List");
            
			AlertManager alert = new AlertManager();
                        
			if(isOk > 0) {
				v_msg = "update.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "update.fail";   
				alert.alertFailMessage(out, v_msg); 
			}     
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}
	/**
	엔돌핀풀 DELETE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try {
			request.setAttribute("requestbox", box);
			EndorphinBean bean = new EndorphinBean();
			int isOk = bean.delete(box);
            
			String v_msg = "";                     
			String v_url = "/servlet/controller.homepage.EndorphinServlet";
			box.put("p_process","List");
            
			AlertManager alert = new AlertManager();
                        
			if(isOk > 0) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {                
				v_msg = "delete.fail";   
				alert.alertFailMessage(out, v_msg); 
			}     
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}  


    /**
    엔돌핀 목록보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
			request.setAttribute("requestbox", box); 
			EndorphinBean bean = new EndorphinBean();
			ArrayList list1 = bean.selectList(box);
			request.setAttribute("endorphinList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Endorphin_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }


	/**
	 엔돌핀풀 INSERT
	 @param request  encapsulates the request to the servlet
	 @param response encapsulates the response from the servlet
	 @param box      receive from the form object
	 @param out      printwriter object
	 @return void
	 */
	 public void performSelectInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		 throws Exception {
		 try {
			 request.setAttribute("requestbox", box);   
			 EndorphinBean bean = new EndorphinBean();
                        
			 int isOk = bean.selectInsert(box);
			 String v_msg = "";                     
			 String v_url = "/servlet/controller.homepage.EndorphinServlet";
			 box.put("p_process","selectList");
            
			 AlertManager alert = new AlertManager();
                        
			 if(isOk > 0) {
				 v_msg = "insert.ok";       
				 alert.alertOkMessage(out, v_msg, v_url , box, false, false);
			 }
			 else {
				 v_msg = "insert.fail";
				 alert.alertFailMessage(out, v_msg); 
			 } 
		 }catch (Exception ex) {           
			 ErrorManager.getErrorStackTrace(ex, out);
			 throw new Exception("performSelectInsert()\r\n" + ex.getMessage());
		 }
	 }        

}