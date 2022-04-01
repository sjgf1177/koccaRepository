//*********************************************************
//  1. 제      목: PROJECT ADMIN SERVLET
//  2. 프로그램명: BPProjectAdminServlet.java
//  3. 개      요: 리포트 관리자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 25
//  7. 수      정:
//**********************************************************
package controller.beta;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BPProjectAdminBean;
import com.credu.beta.BPProjectData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.BPProjectAdminServlet")
public class BPProjectAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {    
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
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
  
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
			//System.out.println("v_process : " + v_process);
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("BPProjectAdminServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////  
			if(v_process.equals("ProjectQuestionsAdmin")){          //in case of project questions admin
                    this.performProjectQuestionsAdmin(request, response, box, out);
            }
    	    else if(v_process.equals("ProjectQuestionsList")){      //in case of project questions list
                    this.performProjectQuestionsList(request, response, box, out);
            }
            else if(v_process.equals("ProjectQuestionsInsertPage")){//in case of project questions insert page
                this.performProjectQuestionsInsertPage(request, response, box, out);
            }            
            else if(v_process.equals("ProjectQuestionsInsert")){    //in case of project questions insert
                    this.performProjectQuestionsInsert(request, response, box, out);
            }                
            else if(v_process.equals("ProjectQuestionsUpdatePage")){//in case of project questions update page
                this.performProjectQuestionsUpdatePage(request, response, box, out);
            }  
            else if(v_process.equals("ProjectQuestionsUpdate")){    //in case of project questions update
                this.performProjectQuestionsUpdate(request, response, box, out);
            }    
            else if(v_process.equals("DelUpfile")){     //in case of Upfile Delete
                this.performDelUpfile(request, response, box, out);
            }
			else if(v_process.equals("ProjectReportDelete")){     //in case of Upfile Delete
                this.performProjectReportDelete(request, response, box, out);
			}			
                            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    PROJECT QUESTIONS ADMIN
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProjectQuestionsAdmin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BPProjectAdminBean bean = new BPProjectAdminBean();
            ArrayList list1 = bean.selectProjectQuestionsAList(box);
            
            request.setAttribute("projectQuestionsAList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/admin/za_ProjectQuestionsAdmin_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsAdmin()\r\n" + ex.getMessage());
        }
    }
   
    /**
    PROJECT QUESTIONS LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProjectQuestionsList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BPProjectAdminBean bean = new BPProjectAdminBean();
            ArrayList List = bean.selectProjectQuestionsList(box);
            
            
            
            request.setAttribute("projectQuestionsList", List);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/admin/za_ProjectQuestions_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsList()\r\n" + ex.getMessage());
        }
    }   
   
    /**
    PROJECT QUESTIONS INSERT PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProjectQuestionsInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box); 
            
            //리포트 차수에 대한 숫자 구하기
            BPProjectAdminBean bean = new BPProjectAdminBean();
            box.put("v_maxprojseq",new Integer( bean.selectMaxProjectSeq(box)));
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/admin/za_ProjectQuestions_I.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsInsertPage()\r\n" + ex.getMessage());
        }
    }   
	
   
   
    /**
    PROJECT QUESTIONS INSERT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProjectQuestionsInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BPProjectAdminBean bean = new BPProjectAdminBean();

            int isOk = bean.insertProjectQuestions(box);
            String v_msg = "";                     
            String v_url = "/servlet/controller.beta.BPProjectAdminServlet";
            box.put("p_process","ProjectQuestionsList");
            
            AlertManager alert = new AlertManager();
              
            if(isOk > 0) {
            	v_msg = "insert.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg); 
            } 
            
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsInsert()\r\n" + ex.getMessage());
        }
    }
    
    /**
    PROJECT QUESTIONS UPDATE PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProjectQuestionsUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box); 
            BPProjectAdminBean bean = new BPProjectAdminBean();
            BPProjectData data = bean.updateProjectQuestionsPage(box);
           
            request.setAttribute("projectQuestionsUpdatePage", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/admin/za_ProjectQuestions_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsUpdatePage()\r\n" + ex.getMessage());
        }
    }   
   
   
    /**
    PROJECT QUESTIONS UPDATE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProjectQuestionsUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BPProjectAdminBean bean = new BPProjectAdminBean();

            int isOk = bean.updateProjectQuestions(box);
            String v_msg = "";                     
            String v_url = "/servlet/controller.beta.BPProjectAdminServlet";
            box.put("p_process","ProjectQuestionsList");
            
            AlertManager alert = new AlertManager();
              
            if(isOk > 0) {
            	v_msg = "update.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg); 
            } 
            
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectQuestionsUpdate()\r\n" + ex.getMessage());
        }
    }    

	
    /**
    UPFILE DELETE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelUpfile(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BPProjectAdminBean bean = new BPProjectAdminBean();

            int isOk = bean.delUpfile(box);
            String v_msg = "";                     
            String v_url = "/servlet/controller.beta.BPProjectAdminServlet";
            box.put("p_process","ProjectQuestionsList");
            
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
            	v_msg = "update.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg); 
            } 

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectJudge()\r\n" + ex.getMessage());
        }
    }          
    
    /**
    PROJECT REPORT DELETE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProjectReportDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
        	request.setAttribute("requestbox", box);   
            BPProjectAdminBean bean = new BPProjectAdminBean();

            int isOk = bean.deleteProjectReport(box);
            //int isOk = bean.updateProjectJudge(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.beta.BPProjectAdminServlet";
            box.put("p_process","ProjectQuestionsList");
            
            AlertManager alert = new AlertManager();

            if(isOk == 1) {
            	v_msg = "리포트가 삭제되었습니다.";       
            	alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else if (isOk == -1) {
            	v_msg = "리포트가 배정된 학습자가 있어 삭제할 수 없습니다.";
                alert.alertFailMessage(out, v_msg); 
            } else {
            	v_msg = "update.fail";
            	alert.alertFailMessage(out, v_msg); 
            }
            

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("projectJudge()\r\n" + ex.getMessage());
        }
    }        



}
