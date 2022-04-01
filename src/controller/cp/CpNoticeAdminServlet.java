//**********************************************************
//  1. ��      ��: ���������� �����ϴ� ����
//  2. ���α׷���: GatePageMateriaServlet.java
//  3. ��      ��: �ڷ���� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �̿��� 2005. 08. 09
//  7. ��      ��: �̿��� 2005. 08. 09
//**********************************************************

package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CpNoticeAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.cp.CpNoticeAdminServlet")
public class CpNoticeAdminServlet extends javax.servlet.http.HttpServlet {
    
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
			
			if(v_process.equals("")){
				box.put("v_process","selectList");
				v_process = "selectList";
			}
			
			System.out.println("v_process : " + v_process);

			/*///////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("CpNoticeAdminServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			////////////////////////////////////////////////////////////////*/
	   	   
            if(v_process.equals("insertPage")) {       //  ����������� �̵��Ҷ�
               this.performInsertPage(request, response, box, out);
               
            }
            else if(v_process.equals("insert")) {       //  ����Ҷ�
               this.performInsert(request, response, box, out);
               
            }
            else if(v_process.equals("updatePage")) {       //  ������������ �̵��Ҷ�
               this.performUpdatePage(request, response, box, out);
               
            }
            else if(v_process.equals("update")) {     //      �����Ͽ� �����Ҷ� 
               this.performUpdate(request, response, box, out);
               
            }
            else if(v_process.equals("delete")) {     //     �����Ҷ�
               this.performDelete(request, response, box, out);
               
            }
            else if(v_process.equals("select")) {       //      �󼼺����Ҷ�
               this.performSelect(request, response, box, out);
            }
            else if(v_process.equals("selectList")) {    
				
               this.performSelectList(request, response, box, out);
				
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
    /**********************************************************
    * Process incoming requests for information
    * 
    * @param request encapsulates the request to the servlet
    * @param response encapsulates the response from the servlet
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
			System.out.println("performSelectList()ȣ��");
            request.setAttribute("requestbox", box);            

            CpNoticeAdminBean notice = new CpNoticeAdminBean();
            
            ArrayList list = notice.selectPdsList(box);
            
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpNotice_L.jsp");
			System.out.println("CpNotice_l.jsp�� forward�ϱ�");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            CpNoticeAdminBean notice = new CpNoticeAdminBean();
            
      
      
            DataBox dbox = notice.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpNotice_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /gatepage/admin/zu_CpNotice_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
    
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {            
			System.out.println("performInsertPage()ȣ��");
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�      
			CpNoticeAdminBean bean = new CpNoticeAdminBean();
			ArrayList list         = bean.selectCpinfo(box);
            request.setAttribute("selectCpinfo", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpNotice_I.jsp");
			System.out.println("zu_CpNotice_i.jsp�� forward");
            rd.forward(request, response);

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {       
			System.out.println("insert()ȣ��");
            CpNoticeAdminBean notice = new CpNoticeAdminBean();
            
            int isOk = notice.insertPds(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CpNoticeAdminServlet";
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
    
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
			         
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			CpNoticeAdminBean bean = new CpNoticeAdminBean();
			ArrayList list         = bean.selectCpinfo(box);
            request.setAttribute("selectCpinfo", list);

            CpNoticeAdminBean notice = new CpNoticeAdminBean();
            DataBox dbox = notice.selectPds(box);
            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();     
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpNotice_U.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
    
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {       
         try {                       
            CpNoticeAdminBean notice = new CpNoticeAdminBean();
            
            int isOk = notice.updatePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpNoticeAdminServlet";
            box.put("p_process", "selectList");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����
            
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
    
   
    
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
      	   CpNoticeAdminBean notice = new CpNoticeAdminBean();
            
            int isOk = notice.deletePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpNoticeAdminServlet";
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

