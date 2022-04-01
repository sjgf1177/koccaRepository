/**
*��Ÿ�׽�Ʈ�ý����� ���������� �����ϴ� ����
*<p>����:BetaNoticeServlet.java</p>
*<p>����:��Ÿ�׽�Ʈ�ý���Notice����</p>
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

import com.credu.beta.BetaNoticeBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.beta.BetaNoticeServlet")
public class BetaNoticeServlet extends javax.servlet.http.HttpServlet {
    
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
			System.out.println("NoticedoPost()ȣ��");
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
	   	   
            if(v_process.equals("select")) {       //      �󼼺����Ҷ�
                if(v_canRead) this.performSelect(request, response, box, out);
            }
            else if(v_process.equals("notice")) {     //     ���������� ������
                this.performNotice(request, response, box, out);
            }
            else {   
				System.out.println("//      ��ȸ�Ҷ� " + v_canRead);
                if(v_canRead) this.performSelectList(request, response, box, out);
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
   /**
    �������� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
			System.out.println("performSelectList()ȣ��");
            request.setAttribute("requestbox", box);            

            BetaNoticeBean notice = new BetaNoticeBean();
            
            ArrayList list = notice.selectPdsList(box);
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaNotice_L.jsp");
			System.out.println("zu_BetaNotice_L.jsp�� forward�ϱ�");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	/**
    ����ȭ���� ��������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performNotice(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
			System.out.println("performNotice()ȣ��");
            request.setAttribute("requestbox", box);            

            BetaNoticeBean notice = new BetaNoticeBean();
            
            ArrayList list = notice.selectNoticeList(box);
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/zu_index_i.jsp");
			System.out.println("zu_index_i.jsp�� forward�ϱ�");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    /**
    �������� �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {       
			System.out.println("Notice performSelect()ȣ��");
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            BetaNoticeBean notice = new BetaNoticeBean();
            
      
      
            DataBox dbox = notice.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaNotice_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/user/zu_BetaNotice_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
    
   /**
    �������� �����޼���
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
    
            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");   
            
            //  Log.sys.println();
   
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}

