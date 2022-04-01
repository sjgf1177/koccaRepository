/**
*베타테스트시스템의 Sw자료실 제어하는 서블릿
*<p>제목:BetaSwServlet.java</p>
*<p>설명:베타테스트시스템Sw자료실서블릿</p>
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

import com.credu.beta.BetaSwBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.beta.BetaSwServlet")
public class BetaSwServlet extends javax.servlet.http.HttpServlet {
    
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
	   
	   v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
	   v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
	   v_canModify = BulletinManager.isAuthority(box, box.getString("p_canModify"));
	   v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
	   	   
            if(v_process.equals("insertPage")) {       //  등록페이지로 이동할때
                if(v_canAppend) this.performInsertPage(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("insert")) {       //  등록할때
                if(v_canAppend) this.performInsert(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("updatePage")) {       //  수정페이지로 이동할때
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
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
    /**
	*리스트
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
	*/
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
			System.out.println("performSelectList()호출");
            request.setAttribute("requestbox", box);            

            BetaSwBean sw = new BetaSwBean();
            
            ArrayList list = sw.selectPdsList(box);
            
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaSw_L.jsp");
			System.out.println("betasw_l.jsp로 forward하기");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    /**
	*상세보기
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
	*/
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            BetaSwBean sw = new BetaSwBean();
            
      
      
            DataBox dbox = sw.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaSw_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaSw_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
    /**
	*등록페이지 이동
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
	*/
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {            
			System.out.println("performInsertPage()호출");
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다      
                
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaSw_I.jsp");
			System.out.println("zu_BetaSw_I.jsp로 forward");
            rd.forward(request, response);

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    /**
	*등록하기
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
	*/
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {       
			System.out.println("insert()호출");
            BetaSwBean sw = new BetaSwBean();
            
            int isOk = sw.insertPds(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.beta.BetaSwServlet";
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
	*수정페이지이동
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
	*/
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
			         
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            BetaSwBean sw = new BetaSwBean();
      
            DataBox dbox = sw.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();     
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaSw_U.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
    /**
	*수정하기
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
	*/
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {       
         try {                       
            BetaSwBean sw = new BetaSwBean();
            
            int isOk = sw.updatePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.beta.BetaSwServlet";
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
	*삭제하기
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
	*/
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
      	   BetaSwBean sw = new BetaSwBean();
            
            int isOk = sw.deletePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.beta.BetaSwServlet";
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
	*에러메세지
	*@param request  encapsulates the request to the servlet
	*@param response encapsulates the response from the servlet
	*@param box      receive from the form object
	*@param out      printwriter object
	*@return void
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

