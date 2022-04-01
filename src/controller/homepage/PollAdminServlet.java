//**********************************************************
//  1. ��      ��: Poll�� �����ϴ� ����
//  2. ���α׷��� : PollAdminServlet.java
//  3. ��      ��: Poll�� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 13
//  7. ��      ��: 
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.PollAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.PollAdminServlet")
public class PollAdminServlet extends javax.servlet.http.HttpServlet {
    
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

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("PollAdminServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
			
           if(v_process.equals("selectView")) {                  // �󼼺����Ҷ�
                this.performSelectView(request, response, box, out);
            }
            else if(v_process.equals("selectPre")) {            // �ڵ弿��Ʈ ���� ����
                this.performSelectPre(request, response, box, out);
            }
            else if(v_process.equals("insertPage")) {            // ����������� �̵��Ҷ�
                this.performInsertPage(request, response, box, out);
            }
            else if(v_process.equals("insert")) {                // ����Ҷ�
                this.performInsert(request, response, box, out);
            }
            else if(v_process.equals("updatePage")) {            // ������������ �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("update")) {                // �����Ͽ� �����Ҷ� 
                this.performUpdate(request, response, box, out);
            }
            else if(v_process.equals("delete")) {                // �����Ҷ�
                this.performDelete(request, response, box, out);
            }
            else if(v_process.equals("select")) {                // ��ȸ�Ҷ�
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    

    /**
    �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            PollAdminBean bean = new PollAdminBean();

            //PollData data1 = bean.selectViewPoll(box);
            DataBox dbox = bean.selectViewPoll(box);
            request.setAttribute("selectPoll", dbox);

            // ������ (���� ��ǥ ��� ������ ��)
            ArrayList list = bean.selectResultPoll(box);
            request.setAttribute("selectResultPoll", list);

            ServletContext sc    = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Poll_R.jsp");
            rd.forward(request, response);
                    
            Log.info.println(this, box, "Dispatch to /learn/user/homepage/za_Poll_R.jsp");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    /**
    �ڵ弿��Ʈ ���� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            PollAdminBean bean = new PollAdminBean();

            //PollData data = bean.selectViewPoll(box);
            DataBox dbox = bean.selectViewPoll(box);
            request.setAttribute("selectPoll", dbox);
            // ������ (���� ��ǥ ��� ������ ���� ����)
            ArrayList list = bean.selectResultPrePoll(box);
            request.setAttribute("selectResultPoll", list);

            ServletContext sc    = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Poll_R.jsp");
            rd.forward(request, response);
                    
            Log.info.println(this, box, "Dispatch to /learn/user/homepage/za_Poll_R.jsp");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }


     /**
    ����������� �̵��Ҷ�
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

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Poll_I.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /learn/user/homepage/za_Poll_I.jsp");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
    ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {             
            PollAdminBean bean = new PollAdminBean();
            int isOk = bean.insertPoll(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.PollAdminServlet";
            box.put("p_process", "select");
            
            AlertManager alert = new AlertManager();

            if(isOk > 0) {              
                v_msg = "insert.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);   
            }
            else {                
                v_msg = "insert.fail";   
                alert.alertFailMessage(out, v_msg); 
            }                                          

            Log.info.println(this, box, v_msg + " on PollAdminServlet");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

     /**
    ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {              
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            PollAdminBean bean = new PollAdminBean();
            //PollData data = bean.selectViewPoll(box);
            DataBox dbox = bean.selectViewPoll(box);
            request.setAttribute("selectPoll", dbox);
            // ������ (���� ��ǥ ��� ������ ���� ����)
            ArrayList list = bean.selectResultPrePoll(box);
            request.setAttribute("selectResultPoll", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Poll_U.jsp");
            rd.forward(request, response);
                    
            Log.info.println(this, box, "Dispatch to /learn/user/homepage/za_Poll_U.jsp");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
         try {                       
            PollAdminBean bean = new PollAdminBean();
            int isOk = bean.updatePoll(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.homepage.PollAdminServlet";
            box.put("p_process", "select");
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

            Log.info.println(this, box, v_msg + " on PollAdminServlet");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {
           PollAdminBean bean = new PollAdminBean();
           int isOk = bean.deletePoll(box);
            
            String v_msg = "";
            String v_url  = "/servlet/controller.homepage.PollAdminServlet";
            box.put("p_process", "select");
            
            AlertManager alert = new AlertManager();
            
            if(isOk > 0) {              
                v_msg = "delete.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);     
            }
            else {                
                v_msg = "delete.fail";   
                alert.alertFailMessage(out, v_msg); 
            }                                          

            Log.info.println(this, box, v_msg + " on PollAdminServlet");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
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
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            PollAdminBean bean = new PollAdminBean();

            ArrayList list = bean.selectListPoll(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Poll_L.jsp");
            rd.forward(request, response);
                    
            Log.info.println(this, box, "Dispatch to /learn/user/homepage/za_Poll_L.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}

