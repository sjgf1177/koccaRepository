/**
*외주관리시스템의 운영자에게를 제어하는 서블릿
*<p>제목:CpAdminServlet.java</p>
*<p>설명:외주관리시스템Admin서블릿</p>
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

import com.credu.cp.CpAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.cp.CpAdminServlet")
public class CpAdminServlet extends javax.servlet.http.HttpServlet {
    
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
        
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");
            
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            
			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           if(v_process.equals("adminiList")) {                                // 리스트
                this.performSelectList(request, response, box, out);
            }
			else if(v_process.equals("adminiListkia")) {                                // 리스트
                this.performSelectListkia(request, response, box, out);
            }
			
			else if(v_process.equals("send")) {                                // 메일보내기
                this.performMail(request, response, box, out);
            }
			else if(v_process.equals("select2")) {       //      상세보기할때 
				this.performSelect2(request, response, box, out);
			}
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    운영자에게 hyundai 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            CpAdminBean bean = new CpAdminBean();

            ArrayList list = bean.selectListAdminhyundai(box);
            request.setAttribute("selectList", list);
			

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpAdmin_L.jsp");
			System.out.println("zu_cpadmin_l.jps로 forward하기");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /cp/user/zu_CpAdmin_L.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	/**
    운영자에게 kia 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performSelectListkia(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            CpAdminBean bean = new CpAdminBean();

            ArrayList list = bean.selectListAdminkia(box);
            request.setAttribute("selectList", list);
			

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpAdmin_LL.jsp");
			System.out.println("zu_cpadmin_ll.jps로 forward하기");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /cp/user/zu_CpAdmin_LL.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	/**
    운영자에게 메일보내기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpAdminBean admin = new CpAdminBean();
            boolean isMailed = false;
			
      
            isMailed = admin.selectPds(box);
			
			
			String v_msg = "";
			String v_url = "/servlet/controller.cp.CpAdminServlet";
			box.put("p_process", "adminiList");
			
			AlertManager alert = new AlertManager();
			
			if(isMailed) {            	
				v_msg = "메일을 전송하였습니다";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);   
			}
			else {
				v_msg = "전송 에러!";   
				alert.alertOkMessage(out, v_msg, v_url , box, true, true); 
			}                        
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
	/**
    운영자정보를 가져와 jsp로 넘긴다 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performSelect2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {       
			request.setAttribute("requestbox", box);
			System.out.println("p_touserid ======= " + box.getString("p_touserid"));
			System.out.println("p_formuserid ======= " + box.getString("p_formuserid"));
			CpAdminBean admin = new CpAdminBean();
			
			ArrayList list = admin.selectMail(box);
			System.out.println("selectMail()에서 list값 받아 jsp로 넘기기");
			
			
			
			
			request.setAttribute("selectlmail", list);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpMail.jsp");
			rd.forward(request, response);
			
		}catch (Exception ex) {  
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelect()\r\n" + ex.getMessage());
		}
	}

}

