
//**********************************************************
//  1. 제      목: 외주관리시스템의 회사별게시판을 제어하는 서블릿
//  2. 프로그램명: CpCompanyServlet.java
//  3. 개      요: 외주관리시스템의 회사별게시판을 제어하는 서블릿
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08. 04
//  7. 수      정: 이연정 2005. 08. 04
//***********************************************************

package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CpCompanyBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.cp.CpCompanyServlet")
public class CpCompanyServlet extends javax.servlet.http.HttpServlet {
    
    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;
//        boolean v_canRead = false;
//        boolean v_canAppend = false;
//        boolean v_canModify = false;
//        boolean v_canDelete = false;
        
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
	   
	  // v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
	  // v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
	  // v_canModify = BulletinManager.isAuthority(box, box.getString("p_canModify"));
	  // v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
	   	   
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
            else if(v_process.equals("replyPage")){   
              this.performReplyPage(request, response, box, out);
            }
            else if(v_process.equals("reply")){   
              this.performReply(request, response, box, out);
            }
            else if(v_process.equals("selectList")){   
              this.performSelectList(request, response, box, out);
            }
			 			else if(v_process.equals("viewsave")) {                  //  보기화면에서 분류지정하고 확인저장할때
              this.performViewSave(request, response, box, out);
			 			}
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
    /**
    회사별게시판 리스트
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

            CpCompanyBean company = new CpCompanyBean();
            String v_type = "AF";
            ArrayList list = company.selectPdsList(box,v_type);
            
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpCompany_L.jsp");
			System.out.println("zu_CpCompany_L.jsp로 forward하기");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    /**
    회사별게시판 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpCompanyBean company = new CpCompanyBean();
      
            DataBox dbox = company.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpCompany_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/admin/zu_CpCompany_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
    /**
    회사별게시판 등록페이지 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {            

            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			CpCompanyBean bean = new CpCompanyBean();
			ArrayList list        = bean.selectCpinfo(box);
            request.setAttribute("selectCpinfo", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpCompany_I.jsp");
			System.out.println("zu_CpCompany_I.jsp로 forward");
            rd.forward(request, response);

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    /**
    회사별게시판 등록
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
            CpCompanyBean company = new CpCompanyBean();
            
            int isOk = company.insertPds(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CpCompanyServlet";
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
    /**
    회사별게시판 수정페이지이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
			         
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpCompanyBean company = new CpCompanyBean();
      
            DataBox dbox = company.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();     
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpCompany_U.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
    /**
    회사별게시판 수정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {       
         try {                       
            CpCompanyBean company = new CpCompanyBean();
            
            int isOk = company.updatePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpCompanyServlet";
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
    
   
    /**
    회사별게시판 삭제
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
      	   CpCompanyBean company = new CpCompanyBean();
            
            int isOk = company.deletePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpCompanyServlet";
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
	/**
    회사별게시판 에러메세지
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

     /**
    // 회사별게시판 뷰화면에서 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performViewSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
			 System.out.println("수정하기 수정하기");
            CpCompanyBean bean = new CpCompanyBean();
			
            int isOk = bean.viewupdate(box);
			System.out.println("updatecpCompany(box)부르기");
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CpCompanyServlet";
            box.put("p_process", "select");
            //      수정 후 해당 뷰 페이지로 돌아가기 위해

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
            throw new Exception("performViewSave()\r\n" + ex.getMessage());
        }
    }
    
    /**
    회사별게시판 페이지이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
			         
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpCompanyBean company = new CpCompanyBean();
            DataBox dbox = company.selectPds(box);
            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();     
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpCompany_A.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    회사별게시판 페이지이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {              
			         
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpCompanyBean company = new CpCompanyBean();
            
            int isOk = company.replyPds(box);
            String v_msg = "";
            
            String v_url = "/servlet/controller.cp.CpCompanyServlet";
            box.put("p_process", "select");
            
            AlertManager alert = new AlertManager();
            v_url = "";

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
            
            //request.setAttribute("selectPds", dbox);
            
            //ServletContext sc = getServletContext();     
            //RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpCompany_R.jsp");
            //rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

}

