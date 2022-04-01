//**********************************************************
//  1. ��      ��: FAQ�� �����ϴ� ����
//  2. ���α׷��� : FaqServlet.java
//  3. ��      ��: FAQ�� �������� �����Ѵ�(HOMEPAGE)
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2004.1.26
//  7. ��      ��: 
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
            
			/*/ �α� check ��ƾ VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}*/
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           if(v_process.equals("usmail")) {                                // ����Ʈ
				this.performSelect2(request, response, box, out);
            }
			else if(v_process.equals("userid")) {                                // ����Ʈ
				this.performSelect1(request, response, box, out);
            }
			else if(v_process.equals("send")) {                                // ����Ʈ
                this.performMail(request, response, box, out);
            }
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
     public void performSelect2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {
			System.out.println("userid�� �ֽ��ϴ�");
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
			System.out.println("userid�� �����ϴ�");
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
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            BetaContactBean admin = new BetaContactBean();
            boolean isMailed = false;
			
      
            isMailed = admin.sendMail(box);
			
			
			String v_msg = "";
			String v_url = "/servlet/controller.beta.BetaNoticeServlet";
			box.put("p_process", "notice");
			
			AlertManager alert = new AlertManager();
			
			if(isMailed) {            	
				v_msg = "������ �����Ͽ����ϴ�";
				alert.alertOkMessage(out, v_msg, v_url , box, true, true);   
			}
			else {
				v_msg = "���� ����!";   
				alert.alertOkMessage(out, v_msg, v_url , box, true, true); 
			}                        
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
	
	

}

