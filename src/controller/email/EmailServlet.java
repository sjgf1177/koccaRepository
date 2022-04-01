package controller.email;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.email.EmailBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;


@WebServlet("/servlet/controller.email.EmailServlet")
public class EmailServlet extends HttpServlet implements Serializable {


		
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
	        RequestBox  box = null;
	        String v_process = "";
	        
	        try {
	            response.setContentType("text/html;charset=euc-kr");
	            out = response.getWriter();
	            box = RequestManager.getBox(request);            
	            v_process = box.getStringDefault("p_process","listMail");
				System.out.println("ClassServlet Ŭ�������� v_process:"+v_process);
	            
	            if(ErrorManager.isErrorMessageView()) {
	                box.put("errorout", out);
	            }

	            //if (!AdminUtil.getInstance().checkRWRight("ClassServlet", v_process, out, box)) {
	            //    return; 
	            //}

	            if (v_process.equals("listMail")) {                                 // �̸��� ����Ʈ
	            	
	            	
	            	this.emailListPage(request, response, box, out);
	            	
	                
	            } else if (v_process.equals("SaveMail")) {                  		// �����ϱ�
	            	System.out.println("�����ϴ°� ");
	                this.inputReturn(request, response, box, out);
	            } 




	        }catch(Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	        }
	    }

	    /**
	    Email LIST
	    
	    */
	    public void emailListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	        try {
	        	request.setAttribute("requestbox", box);
	        	
	            String v_return_url = "/learn/admin/email/za_email_L.jsp";
	           EmailBean bean = new EmailBean();
	           
	           ArrayList list = bean.SelectEmailList(box);	           
	           request.setAttribute("EmailList", list);
	                     
	            ServletContext sc = getServletContext();
	           
	            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);	            
	            rd.forward(request, response);
	            
	        }catch (Exception ex) {           
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performListPage()\r\n" + ex.getMessage());
	        }
	    }
	    
	    
		/**
	    ����Ŭ���� ����
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	   
	    public void inputReturn(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
	    	try {
	    		EmailBean bean = new EmailBean();
	            int isOk = bean.InsertReturn(box);

	            String v_msg = "";
	            String v_url = "";//"/servlet/controller.study.StudyStatusAdminServlet";
//	            box.put("p_process", "PersonalSelect");
//
	            AlertManager alert = new AlertManager();

	            if(isOk > 0) {
	                v_msg = " ��� �Ǿ����ϴ�. ��� Ȯ�� �� �ٷ� ������ �帮�ڽ��ϴ�. �����մϴ�.";
	                alert.selfClose(out, v_msg);
	            }
	            else {
	                v_msg = "��Ͽ� �����Ͽ����ϴ�. �����ڿ��� �����ϼ���.";
	                alert.alertFailMessage(out, v_msg);
	            }

	            //Log.info.println(this, box, v_msg + " on StudyStatusAdminServlet");
	        }catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, out);
	            throw new Exception("performCounselInsert()\r\n" + ex.getMessage());
	        }
	    }
	    	
	}

	
	
	

