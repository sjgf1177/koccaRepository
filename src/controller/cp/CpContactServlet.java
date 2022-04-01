/**
*���ְ����ý����� ���� ���Ϻ����������ϴ� ����
*<p>����:CPcontactServlet.java</p>
*<p>����:��Ÿ�׽�Ʈ�ý���Contact�ڷ�Ǽ���</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ������
*@version 1.0
*/
package controller.cp;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CpContactBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.cp.CpContactServlet")
public class CpContactServlet extends javax.servlet.http.HttpServlet {
    
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
            
			
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			if(v_process.equals("send")) {                                // ����Ʈ
                this.performMail(request, response, box, out);
            }
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

   
	/**
    ��ڿ��� ���Ǹ��� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            CpContactBean admin = new CpContactBean();
            boolean isMailed = false;
			
      
            isMailed = admin.sendMail(box);
			
			AlertManager alert = new AlertManager();
			String v_msg = "";
			
			
			
			
			if(isMailed == true) {   
				
				v_msg = "������ �����Ͽ����ϴ�";
				System.out.println(v_msg);
				alert.selfClose(out, v_msg);   
			}
			else {
				v_msg = "���� ����!"; 
				System.out.println(v_msg);
				alert.selfClose(out, v_msg); 

			}                        
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

}

