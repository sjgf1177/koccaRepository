/**
*��Ÿ�׽�Ʈ�ý����� ��ڿ��Ը� �����ϴ� ����
*<p>����:BetaAdminServlet.java</p>
*<p>����:��Ÿ�׽�Ʈ�ý���Admin����</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ������
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

import com.credu.beta.BetaAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.beta.BetaAdminServlet")
public class BetaAdminServlet extends javax.servlet.http.HttpServlet {
    
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

           if(v_process.equals("adminiList")) {                                // hyundai����Ʈ
                this.performSelectList(request, response, box, out);
            }
			else if(v_process.equals("adminiListkia")) {                                // kia����Ʈ
                this.performSelectListkia(request, response, box, out);
            }
			
			else if(v_process.equals("send")) {                                // ���Ϻ�����
                this.performMail(request, response, box, out);
            }
			else if(v_process.equals("select2")) {       //      �󼼺����Ҷ� 
				this.performSelect2(request, response, box, out);
			}
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    ��ڿ��� hyundai ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            BetaAdminBean bean = new BetaAdminBean();

            ArrayList list = bean.selectListAdminhyundai(box);
            request.setAttribute("selectList", list);
			

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaAdmin_L.jsp");
			System.out.println("zu_betaadmin_l.jps�� forward�ϱ�");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaAdmin_L.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	/**
    ��ڿ��� kia ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performSelectListkia(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            BetaAdminBean bean = new BetaAdminBean();

            ArrayList list = bean.selectListAdminkia(box);
            request.setAttribute("selectList", list);
			

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaAdmin_LL.jsp");
			System.out.println("zu_betaadmin_ll.jps�� forward�ϱ�");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaAdmin_LL.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	/**
    ��ڿ��� ���Ϻ�����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            BetaAdminBean admin = new BetaAdminBean();
            boolean isMailed = false;
			
      
            isMailed = admin.selectPds(box);
			
			
			String v_msg = "";
			String v_url = "/servlet/controller.beta.BetaAdminServlet";
			box.put("p_process", "adminiList");

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

	/**
    ��������� ������ jsp�� �ѱ�� 
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
			BetaAdminBean admin = new BetaAdminBean();
			
			ArrayList list = admin.selectMail(box);
						
			request.setAttribute("selectlmail", list);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaMail.jsp");
			rd.forward(request, response);
			
		}catch (Exception ex) {  
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelect()\r\n" + ex.getMessage());
		}
	}

}

