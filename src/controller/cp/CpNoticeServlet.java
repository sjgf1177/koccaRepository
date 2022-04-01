/**
*외주관리시스템의 공지사항을 제어하는 서블릿
*<p>제목:CpNoticeServlet.java</p>
*<p>설명:외주관리시스템Notice서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CpNoticeBean;
import com.credu.cp.CpSwBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.cp.CpNoticeServlet")
public class CpNoticeServlet extends javax.servlet.http.HttpServlet {
    
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
			
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();   
  
            box = RequestManager.getBox(request);       
            
            String path = request.getRequestURI();
             
            //v_process = box.getStringDefault("p_process","index");
            v_process = box.getStringDefault("p_process","selectList");
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            

	   v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
	   v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
	   v_canModify = BulletinManager.isAuthority(box, box.getString("p_canModify"));
	   v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
	   	   
           if(v_process.equals("select")) {       //      상세보기할때
                if(v_canRead) this.performSelect(request, response, box, out);
            }
            else if(v_process.equals("selectList")) {     //     공지사항을 보낼때
                  if(v_canRead) this.performSelectList(request, response, box, out);
            }
            else if(v_process.equals("index")){
				System.out.println("//      조회할때");
				this.performNotice(request, response, box, out);
               
            }else if(v_process.equals("authChange")){
				String v_auth = box.getString("p_auth");
				box.setSession("gadmin",v_auth);
				this.performNotice(request, response, box, out);
		
			}
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
    /**
    공지사항 리스트
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

            CpNoticeBean notice = new CpNoticeBean();
            
            ArrayList list = notice.selectPdsList(box);
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpNotice_L.jsp");
			System.out.println("zu_CpNotice_L.jsp로 forward하기");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	/**
    메인화면의 공지사항과 S/W 자료실
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performNotice(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
			
            request.setAttribute("requestbox", box);            
            CpNoticeBean notice = new CpNoticeBean();
            ArrayList list = notice.selectNoticeListNum(box);
            request.setAttribute("selectPdsList", list);
System.out.println("1111111111111서블릿");
            CpSwBean software = new CpSwBean();
            ArrayList list1 = software.selectPdsListNum(box);
            request.setAttribute("selectSwList", list1);
System.out.println("2222222222222서블릿");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_Index_L.jsp");
			rd.forward(request, response);
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    /**
    공지사항 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {       
			System.out.println("Notice performSelect()호출");
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpNoticeBean notice = new CpNoticeBean();
      
            DataBox dbox = notice.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpNotice_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/user/zu_CpNotice_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
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

