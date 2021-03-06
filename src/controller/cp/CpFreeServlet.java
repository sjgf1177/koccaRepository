
//*********************************************************
//  1. 제      목: 외주관리시스템Free서블릿
//  2. 프로그램명: CpFreeServlet.java
//  3. 개      요: 외주관리시스템의 자유게시판을 제어하는 서블릿
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 2005. 07.19 이연정 
//  7. 수      정:
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CpFreeBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.cp.CpFreeServlet")
public class CpFreeServlet extends javax.servlet.http.HttpServlet {
    
    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;
        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canModify = false;
        boolean v_canDelete = false;
        
        try {
			System.out.println("doPost()호출");
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();   
  
            box = RequestManager.getBox(request);       
            
            String path = request.getRequestURI();
            
            box = BulletinManager.getState(path.substring(path.lastIndexOf(".")+1, path.lastIndexOf("Servlet")), box, out);
             
            v_process = box.getString("p_process");
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            
	   System.out.println("v_process : " + v_process);		       
	   
	   v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));System.out.println("v_canDelete : " + v_canRead);	
	   v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
	   v_canModify = BulletinManager.isAuthority(box, box.getString("p_canModify"));
	   v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));System.out.println("v_canDelete : " + v_canDelete);	
	   	   
            if(v_process.equals("insertPage")) {       //  등록페이지로 이동할때
                if(v_canAppend) this.performInsertPage(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("insert")) {       //  등록할때
                if(v_canAppend) this.performInsert(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("updatePage")) {       //  수정페이지로 이동할때
			   System.out.println("v_canModify : " + v_canModify);	
			   System.out.println("box.getSession : " + box.getSession("gadmin"));
                if(v_canModify) this.performUpdatePage(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("update")) {     //      수정하여 저장할때 
                if(v_canModify) this.performUpdate(request, response, box, out);
                else this.errorPage(box, out);
            }
            
            else if(v_process.equals("delete")) {     //     삭제할때
                if(v_canDelete) this.performDelete(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("select")) {       //      상세보기할때
                if(v_canRead) this.performSelect(request, response, box, out);
            }
            else {   
				System.out.println("//      조회할때");
                if(v_canRead) this.performSelectList(request, response, box, out);
				//else this.errorPage(box, out);
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
	 /**
    자유게시판 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
			System.out.println("performSelectList()호출");
            request.setAttribute("requestbox", box);            

            CpFreeBean free = new CpFreeBean();
            
            ArrayList list = free.selectPdsList(box);
            
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpFree_L.jsp");
			System.out.println("cpfree_l.jsp로 forward하기");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    /**
    자유게시판 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpFreeBean free = new CpFreeBean();
            
      
      
            DataBox dbox = free.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpFree_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/user/zu_CpFree_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
    /**
    자유게시판 등록페이지이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {            
			System.out.println("performInsertPage()호출");
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다      
                
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpFree_I.jsp");
			System.out.println("zu_CpFree_I.jsp로 forward");
            rd.forward(request, response);

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    /**
    자유게시판 등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {       
			System.out.println("insert()호출");
            CpFreeBean free = new CpFreeBean();
            
            int isOk = free.insertPds(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CpFreeServlet";
            box.put("p_process", "");
            
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
    /**
    자유게시판 수정페이지이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
			         
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpFreeBean free = new CpFreeBean();
      
            DataBox dbox = free.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();     
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpFree_U.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
    /**
    자유게시판 수정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {       
         try {                       
            CpFreeBean free = new CpFreeBean();
            
            int isOk = free.updatePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpFreeServlet";
            box.put("p_process", "");
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
    
   
     /**
    자유게시판 삭제
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
      	   CpFreeBean free = new CpFreeBean();
            
            int isOk = free.deletePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpFreeServlet";
            box.put("p_process", "");
            
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
    /**
    자유게시판 에러메세지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
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

