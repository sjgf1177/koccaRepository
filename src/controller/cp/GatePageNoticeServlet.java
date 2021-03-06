//**********************************************************
//  1. 제      목: 공지사항을 제어하는 서블릿
//  2. 프로그램명: GatePageMateriaServlet.java
//  3. 개      요: 자료실의 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08. 09
//  7. 수      정: 이연정 2005. 08. 09
//**********************************************************

package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.GatePageNoticeAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.cp.GatePageNoticeServlet")
public class GatePageNoticeServlet extends javax.servlet.http.HttpServlet {
    
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
			
			if(v_process.equals("")){
				box.put("v_process","selectList");
				v_process = "selectList";
			}
			
			System.out.println("v_process : " + v_process);

			/*///////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("GatePageNoticeServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			////////////////////////////////////////////////////////////////*/
	   	   
            if(v_process.equals("insertPage")) {       //  등록페이지로 이동할때
               this.performInsertPage(request, response, box, out);
               
            }
            else if(v_process.equals("insert")) {       //  등록할때
               this.performInsert(request, response, box, out);
               
            }
            else if(v_process.equals("updatePage")) {       //  수정페이지로 이동할때
               this.performUpdatePage(request, response, box, out);
               
            }
            else if(v_process.equals("update")) {     //      수정하여 저장할때 
               this.performUpdate(request, response, box, out);
               
            }
            else if(v_process.equals("delete")) {     //     삭제할때
               this.performDelete(request, response, box, out);
               
            }
            else if(v_process.equals("select")) {       //      상세보기할때
               this.performSelect(request, response, box, out);
            }
            else if(v_process.equals("selectList")) {    
				
               this.performSelectList(request, response, box, out);
				
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
    /**********************************************************
    * Process incoming requests for information
    * 
    * @param request encapsulates the request to the servlet
    * @param response encapsulates the response from the servlet
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
			System.out.println("performSelectList()호출");
            request.setAttribute("requestbox", box);            

            GatePageNoticeAdminBean notice = new GatePageNoticeAdminBean();
            
            ArrayList list = notice.selectPdsList(box);
            
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_GatepageNotice_L.jsp");
			System.out.println("gatepagenotice_l.jsp로 forward하기");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            GatePageNoticeAdminBean notice = new GatePageNoticeAdminBean();
            
      
      
            DataBox dbox = notice.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_GatepageNotice_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /gatepage/admin/za_GatepageNotice_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
    
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {            
			System.out.println("performInsertPage()호출");
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다      
                
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_GatepageNotice_I.jsp");
			System.out.println("zu_gatepagenotice_i.jsp로 forward");
            rd.forward(request, response);

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {       
			System.out.println("insert()호출");
            GatePageNoticeAdminBean notice = new GatePageNoticeAdminBean();
            
            int isOk = notice.insertPds(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.gatepage.GatePageNoticeServlet";
            box.put("p_process", "selectList");
            
            AlertManager alert = new AlertManager();

            if(isOk > 0) {            	
                v_msg = "insert.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);   
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
    
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
			         
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            GatePageNoticeAdminBean notice = new GatePageNoticeAdminBean();
      
            DataBox dbox = notice.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();     
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_GatepageNotice_U.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
    
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {       
         try {                       
            GatePageNoticeAdminBean notice = new GatePageNoticeAdminBean();
            
            int isOk = notice.updatePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.gatepage.GatePageNoticeServlet";
            box.put("p_process", "selectList");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해
            
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
    
   
    
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
      	   GatePageNoticeAdminBean notice = new GatePageNoticeAdminBean();
            
            int isOk = notice.deletePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.gatepage.GatePageNoticeServlet";
            box.put("p_process", "selectList");
            
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
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
   
    public void errorPage(RequestBox box, PrintWriter out) 
        throws Exception {       
        try {                         
            box.put("p_process", "");
            
            AlertManager alert = new AlertManager();
    
            alert.alertFailMessage(out, "이 프로세스로 진행할 권한이 없습니다.");   
            
            //  Log.sys.println();
   
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}

