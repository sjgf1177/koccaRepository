
//**********************************************************
//  1. ��      ��: ���ְ����ý����� �뵿���ڷ���� �����ϴ� ����
//  2. ���α׷���: CpWorkServlet.java
//  3. ��      ��: ���ְ����ý����� �뵿���ڷ���� �����ϴ� ����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: �̿��� 2005. 08. 04
//  7. ��      ��: �̿��� 2005. 08. 04
//***********************************************************

package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CpWorkBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.cp.CpWorkServlet")
public class CpWorkServlet extends javax.servlet.http.HttpServlet {
    
    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;
//        boolean v_canRead = false;
//        boolean v_canAppend = false;
//        boolean v_canModify = false;
//        boolean v_canDelete = false;
        
        try {
			System.out.println("doPost()ȣ��");
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();   
  
            box = RequestManager.getBox(request);       
            
            String path = request.getRequestURI();
            
           // box = BulletinManager.getState(path.substring(path.lastIndexOf(".")+1, path.lastIndexOf("Servlet")), box, out);
             
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            
	   System.out.println("v_process : " + v_process);          
	   
	     	   
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
            else if(v_process.equals("selectList")){   
				System.out.println("//      ��ȸ�Ҷ�");
                 this.performSelectList(request, response, box, out);
            }
			 else if(v_process.equals("viewsave")) {                  //  ����ȭ�鿡�� �з������ϰ� Ȯ�������Ҷ�
                  this.performViewSave(request, response, box, out);
			 }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    
    /**
    ȸ�纰�Խ��� ����Ʈ
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

            CpWorkBean company = new CpWorkBean();
            String v_type = "AD";
            ArrayList list = company.selectPdsList(box,v_type);
            
            request.setAttribute("selectPdsList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpWork_L.jsp");
			System.out.println("zu_CpWork_L.jsp�� forward�ϱ�");
            rd.forward(request, response);
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    /**
    ȸ�纰�Խ��� �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            CpWorkBean company = new CpWorkBean();
      
            DataBox dbox = company.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpWork_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/admin/zu_CpWork_R.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }
    /**
    ȸ�纰�Խ��� ��������� �̵�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {            

            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			CpWorkBean bean = new CpWorkBean();
			ArrayList list        = bean.selectCpinfo(box);
            request.setAttribute("selectCpinfo", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpWork_I.jsp");
			System.out.println("zu_CpWork_I.jsp�� forward");
            rd.forward(request, response);

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    /**
    ȸ�纰�Խ��� ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {       
			System.out.println("insert()ȣ��");
            CpWorkBean company = new CpWorkBean();
            
            int isOk = company.insertPds(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CpWorkServlet";
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
    /**
    ȸ�纰�Խ��� �����������̵�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
			         
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            CpWorkBean company = new CpWorkBean();
      
            DataBox dbox = company.selectPds(box);

            request.setAttribute("selectPds", dbox);
            
            ServletContext sc = getServletContext();     
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/zu_CpWork_U.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
    /**
    ȸ�纰�Խ��� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {       
         try {                       
            CpWorkBean company = new CpWorkBean();
            
            int isOk = company.updatePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpWorkServlet";
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
    
   
    /**
    ȸ�纰�Խ��� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
      	   CpWorkBean company = new CpWorkBean();
            
            int isOk = company.deletePds(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpWorkServlet";
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
	/**
    ȸ�纰�Խ��� �����޼���
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

     /**
    // ȸ�纰�Խ��� ��ȭ�鿡�� �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performViewSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
			 System.out.println("�����ϱ� �����ϱ�");
            CpWorkBean bean = new CpWorkBean();
			
            int isOk = bean.viewupdate(box);
			System.out.println("updatecpCompany(box)�θ���");
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CpWorkServlet";
            box.put("p_process", "select");
            //      ���� �� �ش� �� �������� ���ư��� ����

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
            throw new Exception("performViewSave()\r\n" + ex.getMessage());
        }
    }

}

