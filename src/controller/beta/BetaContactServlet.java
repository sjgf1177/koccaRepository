//**********************************************************
//  1. 제      목: FAQ를 제어하는 서블릿
//  2. 프로그램명 : FaqServlet.java
//  3. 개      요: FAQ의 페이지을 제어한다(HOMEPAGE)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.26
//  7. 수      정: 
//**********************************************************

package controller.beta;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BetaContactBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.beta.BetaContactServlet")
public class BetaContactServlet extends javax.servlet.http.HttpServlet {
    
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
            
			/*/ 로긴 check 루틴 VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}*/
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           if(v_process.equals("usmail")) {                                // 리스트
				this.performSelect2(request, response, box, out);
            }
			else if(v_process.equals("userid")) {                                // 리스트
				this.performSelect1(request, response, box, out);
            }
			else if(v_process.equals("send")) {                                // 리스트
                this.performMail(request, response, box, out);
            }
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
     public void performSelect2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {
			System.out.println("userid가 있습니다");
			request.setAttribute("requestbox", box);
			String s_userid = box.getSession("userid");
			BetaContactBean contact = new BetaContactBean();
			DataBox dbox = contact.selectMail(box);
			request.setAttribute("selectPds", dbox);
			
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_contactus.jsp");
			rd.forward(request, response);
			
			}catch (Exception ex) {  
				ErrorManager.getErrorStackTrace(ex, out);
				throw new Exception("performSelect()\r\n" + ex.getMessage());
			}
		}

	public void performSelect1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {       
			System.out.println("userid가 없습니다");
			request.setAttribute("requestbox", box);
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_contactusl.jsp");
			rd.forward(request, response);
			
		}catch (Exception ex) {  
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelect()\r\n" + ex.getMessage());
		}
	}
	
	public void performMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            BetaContactBean admin = new BetaContactBean();
            boolean isMailed = false;
			
      
            isMailed = admin.sendMail(box);
			
			
			String v_msg = "";
			String v_url = "/servlet/controller.beta.BetaNoticeServlet";
			box.put("p_process", "notice");
			
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
	
	

}

