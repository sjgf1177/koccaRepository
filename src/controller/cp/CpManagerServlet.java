//**********************************************************
//  1. ��      ��: ���־�ü���� ����
//  2. ���α׷��� : CpManagerServlet.java
//  3. ��      ��: ���־�ü���� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ��â�� 2004. 11.  15
//  7. ��     ��1:
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPCompBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.cp.CpManagerServlet")
public class CpManagerServlet extends javax.servlet.http.HttpServlet {
    
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
        RequestBox box = null;
        String v_process = "";
        
        try {

            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

			if(v_process.equals("")){
				box.put("v_process","listPage");
				v_process = "listPage";
			}
			
			if (!AdminUtil.getInstance().checkRWRight("CpManagerServlet", v_process, out, box)) {
				return; 
			}
			
			String v_gadmin = box.getSession("gadmin");
			
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("insertPage")) {             //����� ��������
                this.performInsertPage(request, response, box, out);
            }
            else if(v_process.equals("insert")) {             //db���ó��
                this.performInsert(request, response, box, out);
            }
            else if(v_process.equals("updatePage")) {   //������ ��������
                this.performUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("update")) {   //db����ó��
                this.performUpdate(request, response, box, out);
            }
            else if(v_process.equals("select")) {   //������ȸ
                this.performSelect(request, response, box, out);
            }
            else if(v_process.equals("delete")) {   //����ó��
                this.performDelete(request, response, box, out);
            }
            else if(v_process.equals("usercheck")) {   //���̵� üũ
            	this.performCheck(request, response, box, out);
            }
            else if(v_process.equals("listPage")) {   //�����ȸ
            	this.performList(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    ���־�ü����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPCompBean CPComp = new CPCompBean();
            
            ArrayList list = CPComp.selectCompList(box);
            
            request.setAttribute("selectCompList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpOutCompManager_L.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/admin/za_cpcomp_L.jsp");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    /**
    ���־�ü ���������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            //CPCompBean CPComp = new CPCompBean();
            
            //ArrayList list = CPComp.selectCompList(box);
            
            //request.setAttribute("selectCompList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpcomp_I.jsp");
            rd.forward(request, response);
         
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    ���־�ü ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
     public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPCompBean CPComp = new CPCompBean();
            
            int isOk = CPComp.insertComp(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.cp.CpManagerServlet";
            box.put("p_process", "");
            
            AlertManager alert = new AlertManager();

            if(isOk > 0) {            	
                v_msg = "insert.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);   
            }
            else {                
                v_msg = "insert.fail";   
                alert.alertFailMessage(out, v_msg); 
            }                                    
            Log.info.println(this, box, v_msg + " on CPComp");  
                    
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    ���־�ü���� ������Ʈ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPCompBean CPComp = new CPCompBean();
			
            DataBox dbox = CPComp.selectComp(box);
            
            request.setAttribute("selectComp", dbox);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpcomp_U.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    ���־�ü���� ������Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
            CPCompBean CPComp = new CPCompBean();
            
            int isOk = CPComp.updateComp(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpManagerServlet";
            box.put("p_process", "");
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
            Log.info.println(this, box, v_msg + " on CPComp"); 
            
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    /**
    ���־�ü����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {        
            request.setAttribute("requestbox", box);            

            CPCompBean CPComp = new CPCompBean();
            
            DataBox dbox = CPComp.selectComp(box);
            
            request.setAttribute("selectComp", dbox);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpcomp_R.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/admin/za_cpcomp_R.jsp");

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    /**
    ���־�ü ����� ���� ���üũ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPCompBean CPComp = new CPCompBean();

            DataBox dbox = CPComp.userCheck(box);
            
            request.setAttribute("userCheck", dbox);

			box.put("p_process", "usercheck");
			  
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpcomp_usercheck.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    /**
    ���־�ü ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
            CPCompBean CPComp = new CPCompBean();          

            int isOk = CPComp.deleteComp(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.cp.CpManagerServlet";
            box.put("p_process", "");
            
            AlertManager alert = new AlertManager();
            
            if(isOk > 0) {            	
                v_msg = "delete.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);     
            }
            else {                
                v_msg = "delete.fail";   
                alert.alertFailMessage(out, v_msg); 
            }                   
            Log.info.println(this, box, v_msg + " on CPComp");   
                                   
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
        
    }
               
}
        	
        	