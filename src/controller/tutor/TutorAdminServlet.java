//*********************************************************
//  1. 제      목: TUTOR  ADMIN SERVLET
//  2. 프로그램명: TutorAdminServlet.java
//  3. 개      요: 강사관리 관리자 servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 12. 08
//  7. 수      정:
//**********************************************************
package controller.tutor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.tutor.TutorAdminBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.tutor.TutorAdminServlet")
public class TutorAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {    
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
//			System.out.println("---- Tutor Notice Process :  "+v_process);

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
			//System.out.println("v_process : " + v_process);
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("TutorAdminServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if(v_process.equals("TutorList")){                  //강사리스트
                    this.performTutorList(request, response, box, out);
            }
			if(v_process.equals("TutorListExcel")){                  //강사리스트 EXCEL
                    this.performTutorListExcel(request, response, box, out);
            }
			if(v_process.equals("OutCompList")){                  //용역기관 리스트
                    this.performOutCompList(request, response, box, out);
            }
			if(v_process.equals("OutCompListExcel")){                  //용역기관 리스트 EXCEL
                    this.performOutCompListExcel(request, response, box, out);
            }
    	    else if(v_process.equals("TutorSelect")){           //강사상세 정보
                    this.performTutorSelect(request, response, box, out);
            }
            else if(v_process.equals("TutorSelectExcel")){           		//강사상세정보 EXCEL
                this.performTutorSelectExcel(request, response, box, out);
            }

            else if(v_process.equals("SaneTutorInsertPage")){   //in case of tutor insert page
                    this.performSaneTutorInsertPage(request, response, box, out);
            }
            else if(v_process.equals("SaoiTutorInsertPage")){   //in case of tutor insert page
                    this.performSaoiTutorInsertPage(request, response, box, out);
            }
            else if(v_process.equals("TutorInsert")){           //in case of tutor insert
                    this.performTutorInsert(request, response, box, out);
            }
            else if(v_process.equals("SaneTutorUpdatePage")){   //in case of tutor update page
                this.performSaneTutorUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("SaoiTutorUpdatePage")){   //in case of tutor update page
                this.performSaoiTutorUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("TutorUpdate")){           //in case of tutor update
                this.performTutorUpdate(request, response, box, out);
//				System.out.println("---- TutorUpdate -------------");
            }
			/*
			else if(v_process.equals("TutorUpdatePage")){           //in case of tutor update
                this.performTutorUpdatePage(request, response, box, out);
				System.out.println("---- TutorUpdate -------------");
            } 
            */ 
            else if(v_process.equals("SearchSubjOpenPage")){    //in case of subject search open page
                this.performSearchSubjOpenPage(request, response, box, out);
            }
            else if(v_process.equals("SearchSubjAtOpenWin")){   //in case of subject search
                this.performSearchSubjAtOpenWin(request, response, box, out);
            } 
            else if(v_process.equals("TutorDelete")){           //in case of tutor delete
                this.performTutorDelete(request, response, box, out);
            }                        
            else if(v_process.equals("SearchTutorOpenPage")){   //in case of tutor search open page
                this.performSearchTutorOpenPage(request, response, box, out);
            }
            else if(v_process.equals("TutorHistoryList")){       //in case of tutor history list
                    this.performTutorHistoryList(request, response, box, out);
            }
            else if(v_process.equals("TutorHistoryExcel")){       //in case of tutor history excel
                    this.performTutorHistoryExcel(request, response, box, out);
            }            
    	    else if(v_process.equals("TutorHistorySelect")){    //in case of tutor history select
                    this.performTutorHistorySelect(request, response, box, out);
            }  
            else if(v_process.equals("TutorJudge")){            //in case of tutor Judge
                    this.performTutorJudge(request, response, box, out);
            }  
    	    else if(v_process.equals("TutorClassList")){			//강사분반현황
                    this.performTutorClassList(request, response, box, out);
            }
    	    else if(v_process.equals("TutorSubjList")){				//강사입과현황
                    this.performTutorSubjList(request, response, box, out);
            }
    	    else if(v_process.equals("TutorClassStudentList")){		//과정입과자목록
                    this.performTutorClassStudentList(request, response, box, out);
            }

    	    else if(v_process.equals("OutCompSelect")){           //in case of tutor select
                    this.performOutCompSelect(request, response, box, out);
            }			
            else if(v_process.equals("OutCompInsertPage")){			//용역기관 등록 Page
                    this.performOutCompInsertPage(request, response, box, out);
            }            
            else if(v_process.equals("OutCompInsert")){				//용역기관 등록
                    this.performOutCompInsert(request, response, box, out);
            }
            else if(v_process.equals("OutCompUpdatePage")){			//용역기관 수정 Page
                this.performOutCompUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("OutCompUpdate")){				//용역기관 수정
                this.performOutCompUpdate(request, response, box, out);
            }                     
            else if(v_process.equals("OutCompDelete")){				//용역기관 삭제
                this.performOutCompDelete(request, response, box, out);
            }

            else if(v_process.equals("StudentList")){				//체점리스트 리포트
                this.performTutorStudentList(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    TUTOR LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorList(box);
            
            request.setAttribute("tutorList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_Tutor_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorList()\r\n" + ex.getMessage());
        }
    }
	
	/**
    TUTOR LIST EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performTutorListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorList(box);
            
            request.setAttribute("tutorList", list); 
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_Tutor_LE.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorList()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorStudentList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
			
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorSubj(box);
            request.setAttribute("TutorSubjList", list);

			ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorStudent_LE.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorList()\r\n" + ex.getMessage());
        }
    }


    /**
    TUTOR SELECT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performTutorSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
            DataBox dbox = bean.selectTutor(box);
            ArrayList list = bean.selectTutorSubjList(box);
			
			
			String v_tutorid = box.getString("p_userid");
			String v_busino = box.getString("p_busino");
			
			String v_userid = "";
			
			if (!v_tutorid.equals("")) v_userid = v_tutorid;
			if (!v_busino.equals("")) v_userid = v_busino;
			
			
			ArrayList list2 = bean.selectTutorSubjHistoryList(box, v_userid); //강사강의정보 리스트 
            
            request.setAttribute("tutorSelect", dbox);
            request.setAttribute("tutorSubjList", list);  
            
			request.setAttribute("tutorSubjHistoryList", list2); 
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_Tutor_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorSelect()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR SELECT EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performTutorSelectExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
            DataBox dbox = bean.selectTutor(box);
            ArrayList list = bean.selectTutorSubjList(box);
			
			
			String v_tutorid = box.getString("p_userid");
			String v_busino = box.getString("p_busino");
			
			String v_userid = "";
			
			if (!v_tutorid.equals("")) v_userid = v_tutorid;
			if (!v_busino.equals("")) v_userid = v_busino;
			
			
			ArrayList list2 = bean.selectTutorSubjHistoryList(box, v_userid); //강사강의정보 리스트 
            
            request.setAttribute("tutorSelect", dbox);
            request.setAttribute("tutorSubjList", list);  
            
			request.setAttribute("tutorSubjHistoryList", list2); 
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_Tutor_RE.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorSelect()\r\n" + ex.getMessage());
        }
    }
    
    /**
    SANE TUTOR INSERT PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSaneTutorInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box); 
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SaneTutor_I.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("saneTutorInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    SAOI TUTOR INSERT PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSaoiTutorInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SaoiTutor_I.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("saoiTutorInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR INSERT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performTutorInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
                       
            int isOk = bean.insertTutor(box);
            String v_msg = "";                     
            String v_url = "/servlet/controller.tutor.TutorAdminServlet";
            box.put("p_process","TutorList");
            box.put("p_subjclass","");
            
            AlertManager alert = new AlertManager();

			if(isOk == 99){
                alert.alertFailMessage(out, "이미 강사에 등록되어 있습니다."); 
			}			
            else if(isOk > 0) {
            	v_msg = "insert.ok";       
            	if (box.getString("p_pagegubun").equals("popup")) {
         			v_url = "/servlet/controller.budget.BudgetAdminServlet";
            		box.put("p_process","searchTutor");
            
            		alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            	} else {
	            	alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            	}
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg); 
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTutorInsert()\r\n" + ex.getMessage());
        }
    }        
    
    /**
    SANE TUTOR UPDATE PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSaneTutorUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다            
            TutorAdminBean bean = new TutorAdminBean();
            DataBox dbox = bean.selectTutor(box);
            ArrayList list = bean.selectTutorSubjList(box);
            
            request.setAttribute("tutorSelect", dbox);
            request.setAttribute("tutorSubjList", list);          
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SaneTutor_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("saneTutorUpdatePage()\r\n" + ex.getMessage());
        }
    }
	
	/**
    TUTOR UPDATE PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performTutorUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
//		System.out.println("---- Notice Update Page Start ----------");
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다            
            TutorAdminBean bean = new TutorAdminBean();
            DataBox dbox = bean.selectTutor(box);
            ArrayList list = bean.selectTutorSubjList(box);
            
            request.setAttribute("tutorSelect", dbox);
            request.setAttribute("tutorSubjList", list);          
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_NoticeAdmin_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTutorUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    SAOI TUTOR UPDATE PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSaoiTutorUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다            
            
            //DataBox dbox = null; 
            
            TutorAdminBean bean = new TutorAdminBean();

            //TutorData data = bean.selectTutor(box);
            DataBox dbox = bean.selectTutor(box);
            
            ArrayList list = bean.selectTutorSubjList(box);
            
            //request.setAttribute("tutorSelect", data);
            request.setAttribute("tutorSelect", dbox);
            request.setAttribute("tutorSubjList", list);          
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SaoiTutor_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("saoiTutorUpdatePage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR UPDATE   @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performTutorUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
            int isOk = bean.updateTutor(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.tutor.TutorAdminServlet";
            box.put("p_process","TutorList");
            box.put("p_subjclass","");
            
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
            throw new Exception("tutorUpdate()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR UPDATE   @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performOutCompUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
            int isOk = bean.updateOutComp(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.tutor.TutorAdminServlet";
            box.put("p_process","OutCompList");
            box.put("p_subjclass","");
            
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
            throw new Exception("tutorUpdate()\r\n" + ex.getMessage());
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
    public void performSearchSubjOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다            
               
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SearchSubj.jsp"); 
            rd.forward(request, response);  
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("searchSubjOpenPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    SUBJECT SEARCH  @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSearchSubjAtOpenWin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectSearchSubj(box);

            request.setAttribute("searchSubj", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SearchSubj.jsp");
            rd.forward(request, response);
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("searchSubjAtOpenWin()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR DELETE    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            TutorAdminBean bean = new TutorAdminBean();
            int isOk = bean.deleteTutor(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.tutor.TutorAdminServlet";
            box.put("p_process","TutorList");
            box.put("p_subjclass","");
            
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
            throw new Exception("tutorDelete()\r\n" + ex.getMessage());
        }
    }                    

    /**
    TUTOR SEARCH PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSearchTutorOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {                        
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectSearchTutor(box);

            request.setAttribute("searchTutor", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_SearchTutor.jsp"); 
            rd.forward(request, response);
        }catch (Exception ex) {
            // System.out.println("==== performSearchTutorOpenPage Exception ====");
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("searchTutorOpenPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR HISTORY LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorHistoryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorHistoryList(box);
            
            request.setAttribute("tutorHistoryList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorHistory_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorHistoryList()\r\n" + ex.getMessage());
        }
    }

    /**
    TUTOR HISTORY EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performTutorHistoryExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorHistoryList(box);
            
            request.setAttribute("tutorHistoryList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorHistory_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorHistoryList()\r\n" + ex.getMessage());
        }
    }
    
    /**
    TUTOR HISTORY SELECT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorHistorySelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();

            //TutorData data = bean.selectTutor(box);
            DataBox dbox = bean.selectTutor(box);
            
            ArrayList list1 = bean.selectTutorSubjList(box);
            ArrayList list2 = bean.selectTutorLectureList(box);              
                        
            //request.setAttribute("tutorSelect", data);
            request.setAttribute("tutorSelect", dbox);
            
            request.setAttribute("tutorSubjList", list1);  
            request.setAttribute("tutorLectureList", list2);            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorHistory_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorHistorySelect()\r\n" + ex.getMessage());
        }
    }    
        
    /**
    TUTOR JUDGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorJudge(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
                        
            int isOk = bean.updateTutorScore(box);
            String v_msg = "";                     
            String v_url = "/servlet/controller.tutor.TutorAdminServlet";
            box.put("p_process","TutorHistoryList");
            
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
            throw new Exception("TutorJudge()\r\n" + ex.getMessage());
        }
    }


    /**
    TUTOR 분반현황 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorClassList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {                        
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorClass(box);

            request.setAttribute("TutorClassList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorClass_L.jsp"); 
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTutorClassListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    TUTOR 입과현황 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorSubjList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {                        
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorSubj(box);

            request.setAttribute("TutorSubjList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorSubj_L.jsp"); 
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTutorSubjList()\r\n" + ex.getMessage());
        }
    }

    /**
    과정 입과학생 목록 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performTutorClassStudentList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {                        
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectTutorClassStudent(box);

            request.setAttribute("TutorClassStudentList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorClassStudent_L.jsp"); 
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTutorClassStudentList()\r\n" + ex.getMessage());
        }
    }

    
    /**
    용역기관 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performOutCompList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectOutCompList(box);
            
            request.setAttribute("outcompList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_OutComp_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("outcompList()\r\n" + ex.getMessage());
        }
    }
	
	/**
    용역기관 리스트 EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performOutCompListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            TutorAdminBean bean = new TutorAdminBean();
            ArrayList list = bean.selectOutCompList(box);
            
            request.setAttribute("outcompList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_OutComp_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("outcompList()\r\n" + ex.getMessage());
        }
    }

    /**
    용역기관 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performOutCompSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
            DataBox dbox = bean.selectOutComp(box);
            request.setAttribute("outcompSelect", dbox);
			
			//box.getString("p_busino")
            ArrayList list = bean.selectTutorSubjList(box);
            request.setAttribute("outcompSubjList", list);	

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_OutComp_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorSelect()\r\n" + ex.getMessage());
        }
    }
    
    /**
    용역기관 등록 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performOutCompInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_OutComp_I.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("saoiTutorInsertPage()\r\n" + ex.getMessage());
        }
     }
     
    /**
    용역기관 INSERT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performOutCompInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);   
            TutorAdminBean bean = new TutorAdminBean();
                        
            int isOk = bean.insertOutComp(box);
            String v_msg = "";                     
            String v_url = "/servlet/controller.tutor.TutorAdminServlet";
            box.put("p_process","OutCompList");
            box.put("p_subjclass","");
            
            AlertManager alert = new AlertManager();
                        
            if(isOk > 0) {
            	v_msg = "insert.ok";     
            	if (box.getString("p_pagegubun").equals("popup")) {
         			v_url = "/servlet/controller.budget.BudgetAdminServlet";
            		box.put("p_process","searchTutor");
            
            		alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            	} else {
	            	alert.alertOkMessage(out, v_msg, v_url , box, false, false);
	            }
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg); 
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("OutCompInsert()\r\n" + ex.getMessage());
        }
    }  
    
    /**
    용역기관 UPDATE PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performOutCompUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다            
            
            TutorAdminBean bean = new TutorAdminBean();

            DataBox dbox = bean.selectOutComp(box);
            
            box.put("p_subjclass","");
            
            request.setAttribute("outcompSelect", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_OutComp_U.jsp"); 
            rd.forward(request, response);                                              
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("saoiTutorUpdatePage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    용역기관 DELETE    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performOutCompDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            TutorAdminBean bean = new TutorAdminBean();
            int isOk = bean.deleteOutComp(box);
            
            String v_msg = "";                     
            String v_url = "/servlet/controller.tutor.TutorAdminServlet";
            box.put("p_process","OutCompList");
            
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
            throw new Exception("outcompDelete()\r\n" + ex.getMessage());
        }
    }
}