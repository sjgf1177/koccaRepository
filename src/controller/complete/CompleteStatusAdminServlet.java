//*********************************************************
//  1. 제      목: COMPLETE STATUS ADMIN SERVLET
//  2. 프로그램명: CompleteStatusAdminServlet.java
//  3. 개      요: 수료 현황 관리자 servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정:
//**********************************************************
package controller.complete;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.complete.CompleteStatusAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.complete.CompleteStatusAdminServlet")
public class CompleteStatusAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {    
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
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
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
			if (!AdminUtil.getInstance().checkRWRight("CompleteStatusAdminServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////     				

			if(v_process.equals("CompleteMemberList")){                  // 과정별 교육이력리스트 
				this.performCompleteMemberList(request, response, box, out);
			}
			else if(v_process.equals("gCompleteMemberList")){                  //in case of goyong complete member list
				this.performgCompleteMemberList(request, response, box, out);
			}			
			else if(v_process.equals("CompleteMemberExcel")){                 //in case of complete member excel
				this.performCompleteMemberExcel(request, response, box, out);
			}
			else if(v_process.equals("gCompleteMemberExcel")){                 //in case of complete member excel
				this.performgCompleteMemberExcel(request, response, box, out);
			}
			else if(v_process.equals("CompleteRosterList")){            //in case of complete roster list
				this.performCompleteRosterList(request, response, box, out);
			}
			else if(v_process.equals("CompleteRosterExcel")){           //in case of complete roster excel
				this.performCompleteRosterExcel(request, response, box, out);
			}
			else if(v_process.equals("NoneCompleteRosterList")){        //in case of none complete roster list
				this.performNoneCompleteRosterList(request, response, box, out);
			}
			else if(v_process.equals("NoneCompleteRosterExcel")){       //in case of none complete roster excel
				this.performNoneCompleteRosterExcel(request, response, box, out);
			}
			else if(v_process.equals("AllCompleteRosterList")){         //in case of all complete roster list
				this.performAllCompleteRosterList(request, response, box, out);
			}
			else if(v_process.equals("AllCompleteRosterExcel")){        //in case of all complete roster excel
				this.performAllCompleteRosterExcel(request, response, box, out);
			}
			else if(v_process.equals("CompleteRateList")){              //in case of complete rate list
				this.performCompleteRateList(request, response, box, out);
			}
			else if(v_process.equals("CompleteRateExcel")){             //in case of complete rate excel
				this.performCompleteRateExcel(request, response, box, out);
			}
			else if(v_process.equals("SendFormMail")){                  //in case of send form mail
				this.performSendFormMail(request, response, box, out);
			}
			else if(v_process.equals("SendFreeMail")){                  //in case of send free mail
				this.performSendFreeMail(request, response, box, out);
			}
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    COMPLETE MEMBER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCompleteMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("CompleteMemberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_CompleteMember_L1.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberList()\r\n" + ex.getMessage());
        }
    }
    
    
     /**
    COMPLETE MEMBER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performgCompleteMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("CompleteMemberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_CompleteMember_L1.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberList()\r\n" + ex.getMessage());
        }
    }
    
    /**
    COMPLETE MEMBER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCompleteMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("CompleteMemberExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_CompleteMember_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberExcel)\r\n" + ex.getMessage());
        }
    }
    
        /**
    COMPLETE MEMBER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performgCompleteMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("CompleteMemberExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_CompleteMember_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberExcel)\r\n" + ex.getMessage());
        }
    }
    
    
    /**
    COMPLETE ROSTER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCompleteRosterList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteRosterList(box);

            request.setAttribute("CompleteRosterList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_CompleteRoster_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteRosterList\r\n" + ex.getMessage());
        }
    }
    
    /**
    COMPLETE ROSTER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCompleteRosterExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteRosterList(box);

            request.setAttribute("CompleteRosterExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_CompleteRoster_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteRosterExcel\r\n" + ex.getMessage());
        }
    }            

    /**
    NONE COMPLETE ROSTER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performNoneCompleteRosterList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectNoneCompleteRosterList(box);

            request.setAttribute("NoneCompleteRosterList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_NoneCompleteRoster_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("NoneCompleteRosterList\r\n" + ex.getMessage());
        }
    }
    
    /**
    NONE COMPLETE ROSTER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performNoneCompleteRosterExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectNoneCompleteRosterList(box);

            request.setAttribute("NoneCompleteRosterExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_NoneCompleteRoster_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("NoneCompleteRosterExcel\r\n" + ex.getMessage());
        }
    }
    
    /**
    ALL COMPLETE ROSTER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performAllCompleteRosterList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("AllCompleteRosterList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_AllCompleteRoster_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("AllCompleteRosterList\r\n" + ex.getMessage());
        }
    }
    
    /**
    ALL COMPLETE ROSTER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performAllCompleteRosterExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteMemberList(box);

            request.setAttribute("AllCompleteRosterExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_AllCompleteRoster_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("AllCompleteRosterExcel\r\n" + ex.getMessage());
        }
    }
    
    /**
    COMPLETE RATE LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCompleteRateList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteRateList(box);

            request.setAttribute("CompleteRateList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_CompleteRate_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteRateList)\r\n" + ex.getMessage());
        }
    }

    /**
    COMPLETE RATE EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCompleteRateExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectCompleteRateExcelList(box);

            request.setAttribute("CompleteRateExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_CompleteRate_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteRateExcel\r\n" + ex.getMessage());
        }
    }
    
    /**
    HIRING INSURANCE RETURNING MEMBER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performHiringInsuranceReturnedList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectHiringInsuranceReturnedList(box);

            request.setAttribute("HiringInsuranceReturnedList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_HiringInsuranceReturned_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("HiringInsuranceReturnedList\r\n" + ex.getMessage());
        }
    }

    /**
    HIRING INSURANCE RETURNING MEMBER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performHiringInsuranceReturnedExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            ArrayList list = bean.selectHiringInsuranceReturnedList(box);

            request.setAttribute("HiringInsuranceReturnedExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_HiringInsuranceReturned_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("HiringInsuranceReturnedExcel\r\n" + ex.getMessage());
        }
    }    
    
    /**
    NONE HIRING INSURANCE RETURNING MEMBER LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performNoneHiringInsuranceReturnedList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            //ArrayList list = bean.selectNoneHiringInsuranceReturnedList(box);

            //request.setAttribute("NoneHiringInsuranceReturnedList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_NoneHiringInsuranceReturned_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("NoneHiringInsuranceReturnedList\r\n" + ex.getMessage());
        }
    }

    /**
    NONE HIRING INSURANCE RETURNING MEMBER EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performNoneHiringInsuranceReturnedExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();
            //ArrayList list = bean.selectNoneHiringInsuranceReturnedList(box);

            //request.setAttribute("NoneHiringInsuranceReturnedExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/complete/za_NoneHiringInsuranceReturned_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("NoneHiringInsuranceReturnedExcel\r\n" + ex.getMessage());
        }
    }
    
    /**
    SEND FORM MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSendFormMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            CompleteStatusAdminBean bean = new CompleteStatusAdminBean();

            int isOk            = bean.sendFormMail(box);
            String v_mailcnt    = isOk+"";  
            String v_msg = "";            
            String v_url = "/servlet/controller.complete.CompleteStatusAdminServlet";            
            box.put("p_process",box.getString("p_rprocess"));
            box.put("p_mailcnt",v_mailcnt);            
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
            	v_msg = "mail.ok";       
            	alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "mail.fail";
                alert.alertFailMessage(out, v_msg); 
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFormMail()\r\n" + ex.getMessage());
        }
    }            
    
    /**
    SEND FREE MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSendFreeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }    
}