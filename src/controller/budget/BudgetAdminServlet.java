//*********************************************************
//  1. ��      ��: BUDGET ADMIN SERVLET
//  2. ���α׷���: BudgetAdminServlet.java
//  3. ��      ��: ������� ������ servlet
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2004. 12. 14
//  7. ��      ��:
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

			if(v_process.equals("SubjList")){                  				//������� ����Ʈ
                    this.performSubjList(request, response, box, out);
            }
			if(v_process.equals("SubjListExcel")){                  				//������� ����Ʈ EXCEL
                    this.performSubjListExcel(request, response, box, out);
            }
    	    else if(v_process.equals("SubjSelect")){           				//�������뺸��
                    this.performSubjSelect(request, response, box, out);
            }
            else if(v_process.equals("SubjInsertPage")){   					//������� ������
                    this.performSubjInsertPage(request, response, box, out);
            }            
            else if(v_process.equals("SubjInsert")){           				//�������
                    this.performSubjInsert(request, response, box, out);
            }
            else if(v_process.equals("SubjUpdatePage")){   					//�������� ������
                this.performSubjUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("SubjUpdate")){           				//��������
                this.performSubjUpdate(request, response, box, out);
            }         
            else if(v_process.equals("SubjDelete")){           				//��������
                this.performSubjDelete(request, response, box, out);
            }
			else if(v_process.equals("SubjStatusList")){           			//�������Ȳ ����Ʈ
                this.performSubjStatusList(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusListExcel")){           			//�������Ȳ ����Ʈ Excel
                this.performSubjStatusListExcel(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusSelect")){           		//��������󼼺��� ������
                this.performSubjStatusSelect(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusInsertPage")){          	//�������� �Է� ������
                this.performSubjStatusInsertPage(request, response, box, out);
            }
            else if(v_process.equals("SubjStatusInsert")){           		//�������� �Է�
                this.performSubjStatusInsert(request, response, box, out);
            }
            else if(v_process.equals("BudgetStatusList")){           		//����������Ȳ ����Ʈ
                this.performBudgetStatusList(request, response, box, out);
            }
			else if(v_process.equals("BudgetStatusListExcel")){           		//����������Ȳ ����Ʈ EXCEL
                this.performBudgetStatusListExcel(request, response, box, out);
            }
            else if(v_process.equals("SubjInputList")){           			//�����Է���Ȳ ����Ʈ
                this.performSubjInputList(request, response, box, out);
            }
			else if(v_process.equals("SubjInputListExcel")){           			//�����Է���Ȳ ����Ʈ Excel
                this.performSubjInputListExcel(request, response, box, out);
            }
            else if(v_process.equals("SubjInputSelect")){           		//�����Է���Ȳ �󼼺��� ������
                this.performSubjInputSelect(request, response, box, out);
            }
            else if(v_process.equals("TutorStatusList")){           		//������Ȳ
                this.performTutorStatusList(request, response, box, out);
            }
			else if(v_process.equals("TutorStatusListExcel")){           		//������Ȳ EXCEL
                this.performTutorStatusListExcel(request, response, box, out);
            }
            else if(v_process.equals("TutorStatusSelect")){           		//������Ȳ �󼼺��� ������
                this.performTutorStatusSelect(request, response, box, out);
            }
			else if(v_process.equals("OutCompStatusSelect")){           	//�뿪������Ȳ �󼼺��� ������
                this.performOutCompStatusSelect(request, response, box, out);
            }
            else if(v_process.equals("searchTutor")) {                   	// �系/���/�뿪��� �˻� ������
                this.performSearchTutor(request, response, box, out);
            }
            else if(v_process.equals("inuserInfo")) {                   	// �����Է��� ����
                this.performInuserInfo(request, response, box, out);
            }
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ������� ����Ʈ
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
    ������� ����Ʈ EXCEL
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
    �������뺸��
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
            //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
            //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
            
            //selectBudgetSubjList���� gubun="A"�̸� �ο��׸�
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList���� gubun="B"�̸� �����׸�            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //���α׷� ����
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
    ������� ������
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
            //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
            //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
			box.put("gubun","A");	//�ο� ����
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);
            
			box.put("gubun","B");	//�����׸� ����
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
    �������
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
            	v_msg = "������ ��ϵǾ����ϴ�.";
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
    �������� ������
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
            //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
            //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
            
            //selectBudgetSubjList���� gubun="A"�̸� �ο��׸�
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList���� gubun="B"�̸� �����׸�            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //���α׷� ����
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
    ��������   
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
    ��������    
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
    �������Ȳ ����Ʈ
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
    �������Ȳ ����Ʈ EXCEL
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
    ��������󼼺��� ������
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
            //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
            //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
            
            //selectBudgetSubjList���� gubun="A"�̸� �ο��׸�
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList���� gubun="B"�̸� �����׸�            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //���α׷� ����
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
    �������� �Է� ������
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
            //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
            //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
            
            //selectBudgetSubjList���� gubun="A"�̸� �ο��׸�
            box.put("gubun","A");
            ArrayList list1 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetmemSubjList", list1);

            //selectBudgetSubjList���� gubun="B"�̸� �����׸�            
            box.put("gubun","B");
            ArrayList list2 = bean.selectBudgetSubjList(box);
            request.setAttribute("budgetSubjList", list2);
            
            //���α׷� ����
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
    �������� �Է�
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
    ����������Ȳ ����Ʈ
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
            
			//selectBudgetSubjList���� gubun="B"�̸� �����׸�(��Ī�� �����)
			String      v_budgetgubun   = box.getStringDefault("p_budgetgubun","B");//B:����,A:�ο�
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
    ����������Ȳ ����Ʈ Excel
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
            
			//selectBudgetSubjList���� gubun="B"�̸� �����׸�(��Ī�� �����)
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
    �����Է���Ȳ ����Ʈ
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
    �����Է���Ȳ ����Ʈ
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
    �����Է���Ȳ �󼼺��� ������
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
    ������Ȳ
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
    ������Ȳ EXCEL
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
    ������Ȳ �󼼺��� ������
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
            //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
            //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
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
    �뿪������Ȳ �󼼺��� ������
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
            //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
            //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
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
    �系����/��ܰ���/�뿪��� �˻�
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
    �����Է�������
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
    //        request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�            
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
    //        request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش� 
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