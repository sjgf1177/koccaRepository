//**********************************************************
//1. ��      ��: �����׷���� SERVLET
//2. ���α׷���: MasterSubjServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: LeeSuMin 2003. 07. 14
//7. ��      ��: 
//                 
//**********************************************************
package controller.propose;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.MasterWizardBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.propose.MasterSubjServlet")
public class MasterSubjServlet extends HttpServlet implements Serializable {

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
            v_process = box.getStringDefault("p_process","listPage");
            
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            ///////////////////////////////////////////////////////////////////
            // ���� check ��ƾ VER 0.2 - 2003.08.10
            if (!AdminUtil.getInstance().checkRWRight("MasterSubjServlet", v_process, out, box)) {
                return; 
            }
            ///////////////////////////////////////////////////////////////////
            if (v_process.equals("listPage")) {                             //�����Ͱ��� ����Ʈ ��ȸ ȭ��
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insertPage")) {                    //�����Ͱ��� ��� ȭ��
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) {                        //�����Ͱ��� ���(����)
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) {                    //�����Ͱ��� ���� ȭ��
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) {                        //�����Ͱ��� ����(����)
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("assignSubjCourse")) {              //�����Ͱ����� ����/�ڽ� ���� ȭ��
                this.performAssign(request, response, box, out);
            } else if (v_process.equals("assignSubjCourseSave")) {          //�����Ͱ����� ����/�ڽ� ����(����))
                this.performAssignSave(request, response, box, out);
            } else if (v_process.equals("deletemaster")){                   //�����Ͱ�������
                this.performDeleteMasterSubj(request, response, box, out);
            } else if (v_process.equals("cancelMasterSubj")){               //������ ���� ����/���� ��������
                this.performCancelMasterSubj(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �����Ͱ��� ����Ʈ ��ȸ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/propose/za_MasterSubj_L.jsp";
                        
            MasterWizardBean bean = new MasterWizardBean();
            ArrayList list1 = bean.SelectMasterGroupList(box);
            request.setAttribute("MasterSubjList", list1);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �����Ͱ��� ��� PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{                
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/propose/za_MasterSubj_I.jsp";
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url); 
            rd.forward(request, response);                                       
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }            
    }

    /**
    �����Ͱ��� ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{                
            String v_url  = "/servlet/controller.propose.MasterSubjServlet";
           
            MasterWizardBean bean = new MasterWizardBean();
            
            int isOk = bean.InsertMasterSubj(box);
            
            String v_msg = "";
            box.put("p_process", "listPage");
      
            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {              
                v_msg = "insert.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {                
                v_msg = "insert.fail";   
                alert.alertFailMessage(out, v_msg);   
            }                                          
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }            
    }
    
    /**
    �����Ͱ��� ���� PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{                
            request.setAttribute("requestbox", box);            
            String v_url = "/learn/admin/propose/za_MasterMain_U.jsp";
/*
            MasterWizardBean bean = new MasterWizardBean();

            MasterGroupData data = bean.SelectEduGroupData(box);
            request.setAttribute("EduGroupData", data);
*/
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
            rd.forward(request, response);                                       
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }            
    }
    
    /**
    �����Ͱ��� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{                
            String v_url  = "/servlet/controller.propose.MasterSubjServlet";

            MasterWizardBean bean = new MasterWizardBean();
            int isOk = bean.UpdateEduGroup(box);
            
            String v_msg = "";
            box.put("p_process", "listPage");
            
            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {              
                v_msg = "update.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);           
            }else {                
                v_msg = "update.fail";   
                alert.alertFailMessage(out, v_msg);   
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }            
    }
 
    /**
    �����Ͱ����� ����/�ڽ� ����ȭ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performAssign(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{                
            request.setAttribute("requestbox", box);
            String v_url  = "/learn/admin/propose/za_MasterGrSubj.jsp";
           
            MasterWizardBean bean = new MasterWizardBean();
            
            ArrayList list1 = bean.TargetCourseList(box);
            request.setAttribute("TargetCourseList", list1);
            
            ArrayList list2 = bean.TargetSubjectList(box);
            request.setAttribute("TargetSubjectList", list2);

            ArrayList list3 = bean.SelectedList(box);
            request.setAttribute("SelectedList", list3);
            
            //ArrayList list4 = bean.TargetSubjSeqList(box);
            //request.setAttribute("TargetSubjSeqList", list4);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
            rd.forward(request, response);                                       
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAssign()\r\n" + ex.getMessage());
        }            
    }
    /**
    �����Ͱ����� ����/�ڽ� ����- ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performAssignSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{
            String v_url  = "/servlet/controller.propose.MasterSubjServlet";

            MasterWizardBean bean = new MasterWizardBean();
            int isOk = bean.SaveAssign(box);
            
            String v_msg = "";
            box.put("p_process", "assignSubjCourse");
            box.put("p_action", "go");
            
            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {              
                v_msg = "update.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box, false, true);           
            }else {                
                v_msg = "update.fail";   
                alert.alertFailMessage(out, v_msg);   
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }            
    }     

    /**
    �����Ͱ��� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performDeleteMasterSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{
            String v_url  = "/servlet/controller.propose.MasterSubjServlet";
      
            MasterWizardBean bean = new MasterWizardBean();
            int isOk = bean.DeleteMasterSubj(box);

            String v_msg = "";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {              
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);           
            }else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }
   
    /**
    ������ ���� ����/���� ��������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performCancelMasterSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{
            String v_url  = "/servlet/controller.propose.MasterSubjServlet";

            MasterWizardBean bean = new MasterWizardBean();
            int isOk = bean.CancelMasterSubj(box);
            
            String v_msg = "";
            box.put("p_process", "assignSubjCourse");
            box.put("p_action", "go");
            
            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {              
                v_msg = "update.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box, false, true);           
            }else {                
                v_msg = "update.fail";   
                alert.alertFailMessage(out, v_msg);   
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }   
}