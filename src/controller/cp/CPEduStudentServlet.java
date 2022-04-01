//**********************************************************
//  1. 제      목: 과정입과 인원조회 서블릿
//  2. 프로그램명: CPEduStudentServlet.java
//  3. 개      요: 과정입과인원조회관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 23
//  7. 수      정:
//**********************************************************

package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPEduStudentBean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
*과정입과 인원조회
*<p>제목:CPEduStudentServlet.java</p>
*<p>설명:과정입과 인원조회 서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/
@WebServlet("/servlet/controller.cp.CPEduStudentServlet")
public class CPEduStudentServlet extends javax.servlet.http.HttpServlet {
    
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
			
			if (!AdminUtil.getInstance().checkRWRight("CPEduStudentServlet", v_process, out, box)) {
				return; 
			}
			
			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("cancel")) {             //수강취소자 명단 페이지
                this.performCancelList(request, response, box, out);
            }
            else if(v_process.equals("propose")) {       //수강신청자 명단 페이지
                this.performProposeList(request, response, box, out);
            }
            else if(v_process.equals("approvalList")) {       //수강확정자 명단리스트 페이지
                this.performApprovalUserList(request, response, box, out);
            }
            else if(v_process.equals("cancelList")) {       //수강취소자 명단리스트 페이지
                this.performCancelUserList(request, response, box, out);
            }
            else if(v_process.equals("proposeList")) {       //수강신청자 명단리스트 페이지
                this.performProposeUserList(request, response, box, out);
            }
            else if(v_process.equals("studentExcel")) {       //입과인원 엑셀다운로드 페이지
                this.performStExcel(request, response, box, out);
            }
            else if(v_process.equals("studentExcelDown")) {       //입과인원 엑셀다운로드
                this.performStExcelDown(request, response, box, out);
            }
            else if(v_process.equals("excelDown")) {       //과정별 인원 엑셀다운로드
                this.performExcelDown(request, response, box, out);
            }
            else if(v_process.equals("cancelexcelDown")) {       //과정별 인원 엑셀다운로드
                this.performCancelExcelDown(request, response, box, out);
            }
            else if(v_process.equals("listPage")) {   //수강확정자 명단
                this.performApprovalList(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

	/**
	수강확정자 명단 과정 리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performApprovalList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            if (box.getString("p_action").equals("go")) {            	
	            CPEduStudentBean cpEduSt = new CPEduStudentBean();            
	            ArrayList list = cpEduSt.selectApprovalList(box);            
	            request.setAttribute("selectApprovalList", list);
	        }
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduApproval_l.jsp");
            rd.forward(request, response);
            
            //Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

	/**
	수강확정자 과정별 명단 리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/	
    public void performApprovalUserList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPEduStudentBean cpEduSt = new CPEduStudentBean();
            
            ArrayList list = cpEduSt.selectApprovalUserList(box);
            
            request.setAttribute("selectApprovalUserList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduApprovaluser_l.jsp");
            rd.forward(request, response);
            
            //Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
     
	/**
	수강취소자 명단 과정리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/    
	public void performCancelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPEduStudentBean cpEduSt = new CPEduStudentBean();
            
            ArrayList list = cpEduSt.selectCancelList(box);
            
            request.setAttribute("selectCancelList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduCancel_l.jsp");
            rd.forward(request, response);
         
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

	/**
	수강취소자 과정별 명단 리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performCancelUserList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPEduStudentBean cpEduSt = new CPEduStudentBean();
            
            ArrayList list = cpEduSt.selectCancelUserList(box);
            
            request.setAttribute("selectCancelUserList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduCanceluser_l.jsp");
            rd.forward(request, response);
            
            //Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

	/**
	수강신청자 명단 과정리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
     public void performProposeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPEduStudentBean cpEduSt = new CPEduStudentBean();
            
            ArrayList list = cpEduSt.selectProposeList(box);
            
            request.setAttribute("selectProposeList", list);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduPropose_l.jsp");
            rd.forward(request, response);
         
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
	/**
	수강신청자 과정별 명단 리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performProposeUserList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPEduStudentBean cpEduSt = new CPEduStudentBean();
            
            ArrayList list = cpEduSt.selectProposeUserList(box);
            
            request.setAttribute("selectProposeUserList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduProposeuser_l.jsp");
            rd.forward(request, response);
            
            //Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    

	/**
	입과인원 엑셀 다운로드페이지
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/ 	
    public void performStExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpstudentExcel.jsp");
            rd.forward(request, response);
            
            //Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
	/**
	입과인원 엑셀 다운로드
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/ 	
    public void performStExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);
            
            CPEduStudentBean cpEduSt = new CPEduStudentBean();
            
            ArrayList list = cpEduSt.selectStudentExcel(box);
            
            request.setAttribute("selectStudentExcel", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpstudentExcel_E.jsp");
            rd.forward(request, response);
            
          //  Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
	/**
	과정별 입과인원 엑셀 다운로드
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);
            
            //CPEduStudentBean cpEduSt = new CPEduStudentBean();
            //
            //ArrayList list = cpEduSt.selectExcel(box);
            //
            //request.setAttribute("selectExcel", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduExcel_E.jsp");
            rd.forward(request, response);
            
          //  Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    /**
	과정별 취소인원 엑셀 다운로드
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performCancelExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduCancelExcel_E.jsp");
            rd.forward(request, response);
            
          //  Log.info.println(this, box, "/servlet/controller.cp.CPEduStudentServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
}
        	
        	