//*********************************************************
//  1. 제      목: BUDGET ADMIN SERVLET
//  2. 프로그램명: BudgetAdminServlet.java
//  3. 개      요: 예산관리 관리자 servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 12. 14
//  7. 수      정:
//**********************************************************
package controller.budget;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.budget.*;
import com.credu.library.*;
import com.credu.system.*;
@WebServlet("/servlet/controller.budget.BudgetAdminServlet")
public class BudgetAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {    
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
        int fileupstatus = 0;
  
        try {
            response.setContentType("text/html;charset=euc-kr");            
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			if (!AdminUtil.getInstance().checkRWRight("BudgetAdminServlet", v_process, out, box)) {
				return; 
			}

			if(v_process.equals("SubjList")){                  				//과정등록 리스트
                    this.performSubjList(request, response, box, out);
            }
			if(v_process.equals("SubjListExcel")){                  				//과정등록 리스트 EXCEL
                    this.performSubjListExcel(request, response, box, out);
            }
    	    else if(v_process.equals("SubjSelect")){           				//과정내용보기
                    this.performSubjSelect(request, response, box, out);
            }
            else if(v_process.equals("SubjInsertPage")){   					//과정등록 페이지
                    this.performSubjInsertPage(request, response, box, out);
            }            
            else if(v_process.equals("SubjInsert")){           				//과정등록
                    this.performSubjInsert(request, response, box, out);
            }
            else if(v_process.equals("SubjUpdatePage")){   					//과정수정 페이지
                this.performSubjUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("SubjUpdate")){           				//과정수정
                this.performSubjUpdate(request, response, box, out);
            }         
            else if(v_process.equals("SubjDelete")){           				//과정삭제
                this.performSubjDelete(request, response, box, out);
            }
			else if(v_process.equals("SubjStatusList")){           			//교육운영현황 리스트
                this.performSubjStatusList(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusListExcel")){           			//교육운영현황 리스트 Excel
                this.performSubjStatusListExcel(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusSelect")){           		//과정집행상세보기 페이지
                this.performSubjStatusSelect(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusInsertPage")){          	//과정집행 입력 페이지
                this.performSubjStatusInsertPage(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusInsert")){           		//과정집행 입력
                this.performSubjStatusInsert(request, response, box, out);
            }
            else if(v_process.equals("BudgetStatusList")){           		//예산집행현황 리스트
                this.performBudgetStatusList(request, response, box, out);
            }
			else if(v_process.equals("BudgetStatusListExcel")){           		//예산집행현황 리스트 EXCEL
                this.performBudgetStatusListExcel(request, response, box, out);
            }
            else if(v_process.equals("SubjInputList")){           			//과정입력현황 리스트
                this.performSubjInputList(request, response, box, out);
            }
			else if(v_process.equals("SubjInputListExcel")){           			//과정입력현황 리스트 Excel
                this.performSubjInputListExcel(request, response, box, out);
            }
            else if(v_process.equals("SubjInputSelect")){           		//과정입력현황 상세보기 페이지
                this.performSubjInputSelect(request, response, box, out);
            }
            else if(v_process.equals("TutorStatusList")){           		//강사운영현황
                this.performTutorStatusList(request, response, box, out);
            }
			else if(v_process.equals("TutorStatusListExcel")){           		//강사운영현황 EXCEL
                this.performTutorStatusListExcel(request, response, box, out);
            }
            else if(v_process.equals("TutorStatusSelect")){           		//강사운영현황 상세보기 페이지
                this.performTutorStatusSelect(request, response, box, out);
            }
			else if(v_process.equals("OutCompStatusSelect")){           	//용역기관운영현황 상세보기 페이지
                this.performOutCompStatusSelect(request, response, box, out);
            }
            else if(v_process.equals("searchTutor")) {                   	// 사내/사외/용역기관 검색 페이지
                this.performSearchTutor(request, response, box, out);
            }
            else if(v_process.equals("inuserInfo")) {                   	// 과정입력자 정보
                this.performInuserInfo(request, response, box, out);
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    과정등록 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectSubjList(box);
            
            request.setAttribute("budgetSubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubject_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjList()\r\n" + ex.getMessage());
        }
    }
	
	/**
    과정등록 리스트 EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectSubjList(box);
            
            request.setAttribute("budgetSubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubject_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjList()\r\n" + ex.getMessage());
        }
    }
    
	
    
    /**
    과정내용보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
            DataBox dbox = bean.selectSubject(box);
            request.setAttribute("subjectSelect", dbox);
            //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
            //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
            
            //selectBudgetSubjList에서 gubun="A"이면 인원항목
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList에서 gubun="B"이면 예산항목            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //프로그램 정보
            ArrayList list3 = bean.selectSubjProgramList(box);
            request.setAttribute("SubjProgramList", list3);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubject_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjSelect()\r\n" + ex.getMessage());
        }
    }

    /**
    과정등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            
            BudgetAdminBean bean = new BudgetAdminBean();
            //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
            //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
			box.put("gubun","A");	//인원 정보
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);
            
			box.put("gubun","B");	//예산항목 정보
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubject_I.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    과정등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
                        
            int isOk = bean.insertSubject(box);
            String v_msg = "";                     
            String v_url = "/servlet/controller.budget.BudgetAdminServlet";
            box.put("p_process","SubjList");
            //box.put("p_process","SubjInsertPage");
            
            
            AlertManager alert = new AlertManager();
                        
            if(isOk > 0) {
            	v_msg = "과정이 등록되었습니다.";
           		alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg); 
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjInsert()\r\n" + ex.getMessage());
        }
    }        

    /**
    과정수정 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
            DataBox dbox = bean.selectSubject(box);
            request.setAttribute("subjectSelect", dbox);
            //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
            //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
            
            //selectBudgetSubjList에서 gubun="A"이면 인원항목
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList에서 gubun="B"이면 예산항목            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //프로그램 정보
            ArrayList list3 = bean.selectSubjProgramList(box);
            request.setAttribute("SubjProgramList", list3);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubject_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjUpdatePage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    과정수정   
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();    
            int isOk = bean.updateSubject(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.budget.BudgetAdminServlet";
            box.put("p_process","SubjList");
            
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
            throw new Exception("subjUpdate()\r\n" + ex.getMessage());
        }
    }

    
    /**
    과정삭제    
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            BudgetAdminBean bean = new BudgetAdminBean();    
            int isOk = bean.deleteSubject(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.budget.BudgetAdminServlet";
            box.put("p_process","SubjList");
            
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
            throw new Exception("subjDelete()\r\n" + ex.getMessage());
        }
    }                    


	/**
    교육운영현황 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjStatusList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectSubjStatusList(box);
            
            request.setAttribute("subjStatusList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubjectStatus_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjStatusList()\r\n" + ex.getMessage());
        }
    }
	
	/**
    교육운영현황 리스트 EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjStatusListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectSubjStatusList(box);
            
            request.setAttribute("subjStatusList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubjectStatus_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjStatusList()\r\n" + ex.getMessage());
        }
    }
    
	/**
    과정집행상세보기 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjStatusSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
            DataBox dbox = bean.selectSubject(box);
            request.setAttribute("subjectSelect", dbox);
            //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
            //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
            
            //selectBudgetSubjList에서 gubun="A"이면 인원항목
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList에서 gubun="B"이면 예산항목            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //프로그램 정보
            ArrayList list3 = bean.selectSubjProgramList(box);
            request.setAttribute("SubjProgramList", list3);            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubjectStatus_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjSelect()\r\n" + ex.getMessage());
        }
    }
    
    /**
    과정집행 입력 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjStatusInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
            DataBox dbox = bean.selectSubject(box);
            request.setAttribute("subjectSelect", dbox);
            //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
            //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
            
            //selectBudgetSubjList에서 gubun="A"이면 인원항목
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList에서 gubun="B"이면 예산항목            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //프로그램 정보
            ArrayList list3 = bean.selectSubjProgramList(box);
            request.setAttribute("SubjProgramList", list3);  
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubjectStatus_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjInsertPage()\r\n" + ex.getMessage());
        }
    }

    
    /**
    과정집행 입력
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjStatusInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
                        
            int isOk = bean.insertSubjStatus(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.budget.BudgetAdminServlet";
            box.put("p_process","SubjStatusList");
            
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
            throw new Exception("subjStatusInsert()\r\n" + ex.getMessage());
        }
    }        
    
    /**
    예산집행현황 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performBudgetStatusList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectBudgetStatusList(box);
            request.setAttribute("BudgetStatusList", list);
            
			//selectBudgetSubjList에서 gubun="B"이면 예산항목(명칭만 사용함)
			String      v_budgetgubun   = box.getStringDefault("p_budgetgubun","B");//B:예산,A:인원
            box.put("gubun",v_budgetgubun);
            ArrayList listbudget = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetItemList", listbudget);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetStatus_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("BudgetStatusList()\r\n" + ex.getMessage());
        }
    }
	
	/**
    예산집행현황 리스트 Excel
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performBudgetStatusListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectBudgetStatusList(box);
            request.setAttribute("BudgetStatusList", list);
            
			//selectBudgetSubjList에서 gubun="B"이면 예산항목(명칭만 사용함)
            box.put("gubun","B");
            ArrayList listbudget = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetItemList", listbudget);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetStatus_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("BudgetStatusList()\r\n" + ex.getMessage());
        }
    }
    
    /**
    과정입력현황 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjInputList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectSubjInputList(box);
            
            request.setAttribute("subjInputList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubjectInput_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjStatusList()\r\n" + ex.getMessage());
        }
    }
	
	/**
    과정입력현황 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjInputListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectSubjInputList(box);
            
            request.setAttribute("subjInputList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubjectInput_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjStatusList()\r\n" + ex.getMessage());
        }
    }
    
    
    /**
    과정입력현황 상세보기 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjInputSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selectSubjInputStatusList(box);
            
            request.setAttribute("SubjInputStatusList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_BudgetSubjectInput_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjSelect()\r\n" + ex.getMessage());
        }
    }
    
    /**
    강사운영현황
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performTutorStatusList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selecttutorStatusList(box);
            
            request.setAttribute("tutorStatusList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_TutorStatus_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjStatusList()\r\n" + ex.getMessage());
        }
    }
	
	/**
    강사운영현황 EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performTutorStatusListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            ArrayList list = bean.selecttutorStatusList(box);
            
            request.setAttribute("tutorStatusList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_TutorStatus_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjStatusList()\r\n" + ex.getMessage());
        }
    }
    
    
    
    /**
    강사운영현황 상세보기 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performTutorStatusSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
            DataBox dbox = bean.selectSubject(box);
            //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
            //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
            ArrayList list = bean.selectBudgetSubjList(box);
            
            request.setAttribute("subjectSelect", dbox);
            request.setAttribute("budgetSubjList", list);            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_Tutor_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjSelect()\r\n" + ex.getMessage());
        }
    }
    
    
    
    
    
    /**
    용역기관운영현황 상세보기 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performOutCompStatusSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            BudgetAdminBean bean = new BudgetAdminBean();
            DataBox dbox = bean.selectSubject(box);
            //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
            //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
            ArrayList list = bean.selectBudgetSubjList(box);
            
            request.setAttribute("subjectSelect", dbox);
            request.setAttribute("budgetSubjList", list);            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_OutComp_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjSelect()\r\n" + ex.getMessage());
        }
    }
    
    
    /**
    사내강사/사외강사/용역기관 검색
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSearchTutor(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
			BudgetAdminBean bean = new BudgetAdminBean();

            ArrayList list = bean.searchTutor(box);
            request.setAttribute("searchTutorList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_searchTutor.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSearchGrpComp()\r\n" + ex.getMessage());
        }
    }
    
    /**
    과정입력자정보
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInuserInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            BudgetAdminBean bean = new BudgetAdminBean();
            DataBox dbox = bean.selectInuserInfo(box);
            
            request.setAttribute("inuserinfo", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/budget/za_InuserInfo_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjStatusList()\r\n" + ex.getMessage());
        }
    }
    
    
    
	/**
    SUBJECT SEARCH PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performSearchSubjOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //	throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다            
    //           
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SearchSubj.jsp"); 
    //        rd.forward(request, response);  
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("searchSubjOpenPage()\r\n" + ex.getMessage());
    //    }
    //}
    
    /**
    SUBJECT SEARCH  @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performSearchSubjAtOpenWin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //	throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);   
    //        TutorAdminBean bean = new TutorAdminBean();
    //        ArrayList list = bean.selectSearchSubj(box);
    //
    //        request.setAttribute("searchSubj", list);
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SearchSubj.jsp");
    //        rd.forward(request, response);
    //                
    //    }catch (Exception ex) {           
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("searchSubjAtOpenWin()\r\n" + ex.getMessage());
    //    }
    //}
    
    /**
    TUTOR SEARCH PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performSearchTutorOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //	throws Exception {
    //    try {                        
    //        request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다 
    //        TutorAdminBean bean = new TutorAdminBean();
    //        ArrayList list = bean.selectSearchTutor(box);
    //
    //        request.setAttribute("searchTutor", list);
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SearchTutor.jsp"); 
    //        rd.forward(request, response);
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("searchTutorOpenPage()\r\n" + ex.getMessage());
    //    }
    //}
    
    /**
    TUTOR HISTORY LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performTutorHistoryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //    throws Exception {
    //    try{
    //        request.setAttribute("requestbox", box); 
    //        TutorAdminBean bean = new TutorAdminBean();
    //        ArrayList list = bean.selectTutorHistoryList(box);
    //        
    //        request.setAttribute("tutorHistoryList", list);
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorHistory_L.jsp");
    //        rd.forward(request, response);
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("tutorHistoryList()\r\n" + ex.getMessage());
    //    }
    //}

    /**
    TUTOR HISTORY EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performTutorHistoryExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //    throws Exception {
    //    try{
    //        request.setAttribute("requestbox", box); 
    //        TutorAdminBean bean = new TutorAdminBean();
    //        ArrayList list = bean.selectTutorHistoryList(box);
    //        
    //        request.setAttribute("tutorHistoryList", list);
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorHistory_E.jsp");
    //        rd.forward(request, response);
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("tutorHistoryList()\r\n" + ex.getMessage());
    //    }
    //}
    
    /**
    TUTOR HISTORY SELECT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performTutorHistorySelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //	throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);   
    //        TutorAdminBean bean = new TutorAdminBean();
    //        TutorData data = bean.selectTutor(box);
    //        ArrayList list1 = bean.selectTutorSubjList(box);
    //        ArrayList list2 = bean.selectTutorLectureList(box);              
    //                    
    //        request.setAttribute("tutorSelect", data);
    //        request.setAttribute("tutorSubjList", list1);  
    //        request.setAttribute("tutorLectureList", list2);            
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorHistory_R.jsp");
    //        rd.forward(request, response);
    //    }catch (Exception ex) {           
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("tutorHistorySelect()\r\n" + ex.getMessage());
    //    }
    //}    
        
    /**
    TUTOR JUDGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    //public void performTutorJudge(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    //	throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);   
    //        TutorAdminBean bean = new TutorAdminBean();
    //                    
    //        int isOk = bean.updateTutorScore(box);
    //        String v_msg = "";                     
    //        String v_url = "/servlet/controller.tutor.TutorAdminServlet";
    //        box.put("p_process","TutorHistoryList");
    //        
    //        AlertManager alert = new AlertManager();
    //                    
    //        if(isOk > 0) {
    //        	v_msg = "update.ok";       
    //        	alert.alertOkMessage(out, v_msg, v_url , box, false, false);
    //        }
    //        else {
    //            v_msg = "update.fail";
    //            alert.alertFailMessage(out, v_msg); 
    //        } 
    //    }catch (Exception ex) {           
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("TutorJudge()\r\n" + ex.getMessage());
    //    }
    //}
}