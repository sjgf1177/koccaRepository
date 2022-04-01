//**********************************************************
//1. 제      목: 교육그룹관리 SERVLET
//2. 프로그램명: MasterSubjServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 14
//7. 수      정: 
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
            // 권한 check 루틴 VER 0.2 - 2003.08.10
            if (!AdminUtil.getInstance().checkRWRight("MasterSubjServlet", v_process, out, box)) {
                return; 
            }
            ///////////////////////////////////////////////////////////////////
            if (v_process.equals("listPage")) {                             //마스터과정 리스트 조회 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insertPage")) {                    //마스터과정 등록 화면
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) {                        //마스터과정 등록(저장)
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) {                    //마스터과정 수정 화면
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) {                        //마스터과정 수정(저장)
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("assignSubjCourse")) {              //마스터과정에 과정/코스 연결 화면
                this.performAssign(request, response, box, out);
            } else if (v_process.equals("assignSubjCourseSave")) {          //마스터과정에 과정/코스 연결(저장))
                this.performAssignSave(request, response, box, out);
            } else if (v_process.equals("deletemaster")){                   //마스터과정삭제
                this.performDeleteMasterSubj(request, response, box, out);
            } else if (v_process.equals("cancelMasterSubj")){               //마스터 연관 과정/차수 정보삭제
                this.performCancelMasterSubj(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    마스터과정 리스트 조회
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
    마스터과정 등록 PAGE
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
    마스터과정 등록
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
    마스터과정 수정 PAGE
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
    마스터과정 수정
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
    마스터과정에 과정/코스 지정화면
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
    마스터과정에 과정/코스 지정- 저장
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
    마스터과정 삭제
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
    마스터 연관 과정/차수 정보삭제
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