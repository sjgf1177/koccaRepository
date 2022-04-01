//**********************************************************
//1. 제      목: MasterForm(학습창) SERVLET
//2. 프로그램명: BetaEduStart.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: S.W.Kang 2004. 12. 23
//7. 수      정: 
//                 
//**********************************************************
package controller.beta;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BetaEduScoreData;
import com.credu.beta.BetaEduStartBean;
import com.credu.beta.BetaMasterFormBean;
import com.credu.beta.BetaMasterFormData;
import com.credu.course.SubjGongAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.BetaEduStart")
public class BetaEduStart extends HttpServlet {

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
			v_process = box.getStringDefault("p_process","main");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
	
			///////////////////////////////////////////////////////////////////
			// 권한 check 루틴 VER 0.2 - 2003.08.10
			if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("main")) {      			//
				this.framesetPage(request, response, box, out);
			} else if (v_process.equals("fsetsub")) {      	//
				this.fsetSubPage(request, response, box, out);				
			} else if (v_process.equals("fup")) {      	//
				this.fupPage(request, response, box, out);
			} else if (v_process.equals("fmenu")) {      	//
				this.fmenuPage(request, response, box, out);
			} else if (v_process.equals("eduCheck")) {      	//
				this.eduCheck(request, response, box, out);
			} else if (v_process.equals("eduList")) {      	//
				this.eduListPage(request, response, box, out);
			} else if (v_process.equals("exam")) {      	//
				this.fmenuPage(request, response, box, out);
			} else if (v_process.equals("tree")) {      	//
				this.ftreePage(request, response, box, out);
			} else if (v_process.equals("bott")) {      	//
				this.fbottPage(request, response, box, out);				
			} else if (v_process.equals("gong")) {      	//
				this.fmenuPage(request, response, box, out);
			} else if (v_process.equals("sul")) {      	//
				this.fmenuPage(request, response, box, out);
			}


		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
	frameset
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void framesetPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url="";
            
			BetaMasterFormBean bean = new BetaMasterFormBean();

			BetaMasterFormData 	data = bean.SelectBetaMasterFormData(box);	//마스터폼 정보
			request.setAttribute("BetaMasterFormData", data);
			
			if (data.getContenttype().equals("N")){				    //Normal MasterForm (NEW)
				v_url = "/beta/admin/z_BetaEduStart_fset.jsp";
			}else if (data.getContenttype().equals("M")){			//Normal MasterForm (OLD)
				v_url = "/beta/admin/z_BetaEduStart_fset.jsp";				
			}else if (data.getContenttype().equals("O")){			//OBC MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fset_OBC.jsp";
			}else if (data.getContenttype().equals("S")){			//SCORM MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fset_SCORM.jsp";
			}	
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("framesetPage()\r\n" + ex.getMessage());
		}            
	} 
	 /**
	fsetSub
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void fsetSubPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{
			request.setAttribute("requestbox", box);            
			String v_url="";
			box.put("p_subj",box.getSession("s_subj"));	
			BetaMasterFormBean bean = new BetaMasterFormBean();

			BetaMasterFormData 	data = bean.SelectBetaMasterFormData(box);	//마스터폼 정보
			request.setAttribute("BetaMasterFormData", data);
			
            if (data.getContenttype().equals("O")){		                //OBC MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fsetSub_OBC.jsp";
			} else if (data.getContenttype().equals("S")){	            // SCORM MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fsetSub_SCORM.jsp";
			}
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("fsetSubPage()\r\n" + ex.getMessage());
		}            
	}

	/**
	fup
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void fupPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url="";
			
            box.put("p_subj",box.getSession("s_subj"));	
			
			
			BetaEduStartBean	bean = new BetaEduStartBean();

			BetaMasterFormData 	data = bean.SelectBetaMasterFormData(box);	//마스터폼 정보
			request.setAttribute("BetaMasterFormData", data);
			
			ArrayList		data1= bean.SelectMfLessonList(box);	    //Lesson List
			request.setAttribute("MfLessonList", data1);
			
			if (data.getContenttype().equals("N")){					    //Normal MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fup.jsp";
			}else if (data.getContenttype().equals("M")){			    //Normal MasterForm (OLD)
				v_url = "/beta/admin/z_BetaEduStart_fup.jsp";					
			}else if (data.getContenttype().equals("O")){			    //OBC MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fup_OBC.jsp";
			}else if (data.getContenttype().equals("S")){			    //SCORM MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fup_SCORM.jsp";
			}
System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzv_url"+v_url);
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("fupPage()\r\n" + ex.getMessage());
		}            
	} 

	/**
	fmenu
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void fmenuPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url="";
 
            box.put("p_subj",box.getSession("s_subj"));	
                        
			BetaMasterFormBean bean = new BetaMasterFormBean();

			BetaMasterFormData 	data = bean.SelectBetaMasterFormData(box);	//마스터폼 정보
			request.setAttribute("BetaMasterFormData", data);
			
			ArrayList		data1= bean.SelectMfSubjList(box);	        //mfSubj Menu List
			request.setAttribute("MfSubjList", data1);
			
			if (data.getContenttype().equals("N")){				        //Normal MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fmenu.jsp";
			}else if (data.getContenttype().equals("M")){		        //Normal MasterForm (OLD)
				v_url = "/beta/admin/z_BetaEduStart_fmenu.jsp";					
			}else if (data.getContenttype().equals("O")){			    //OBC MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fmenu_OBC.jsp";
			}else if (data.getContenttype().equals("S")){		        //SCORM MasterForm
				v_url = "/beta/admin/z_BetaEduStart_fmenu_SCORM.jsp";
			}
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("fmenuPage()\r\n" + ex.getMessage());
		}            
	} 	
	/**
	ftree
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void ftreePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url="";
			String subjnm="";
 
            box.put("p_subj",box.getSession("s_subj"));	
			box.put("p_year",box.getSession("s_year"));	
			box.put("p_subjseq",box.getSession("s_subjseq"));	
			box.put("p_userid",box.getSession("userid"));
                        
			BetaMasterFormBean bean = new BetaMasterFormBean();

			BetaMasterFormData 	data = bean.SelectBetaMasterFormData(box);	//마스터폼 정보
			request.setAttribute("BetaMasterFormData", data);
			
			BetaEduStartBean	eduBean = new BetaEduStartBean();
			//ArrayList	data1	= eduBean.SelectTreeDataList(box);	    // Tree Datas
			//request.setAttribute("TreeData", data1);			
			
			if (data.getContenttype().equals("N")){		                // Normal MasterForm (NEW)
				ArrayList	data1	= eduBean.SelectTreeDataList(box);	// Tree Datas
				request.setAttribute("TreeData", data1);	

				v_url = "/beta/admin/z_BetaEduStart_ftree.jsp";
			}else if (data.getContenttype().equals("M")){			    //Normal MasterForm (OLD)
				ArrayList	data1	= eduBean.SelectTreeDataList(box);	// Tree Datas
				request.setAttribute("TreeData", data1);	

				v_url = "/beta/admin/z_BetaEduStart_ftree.jsp";
			} else if (data.getContenttype().equals("O")){		                //OBC MasterForm
				ArrayList	data1	= eduBean.SelectTreeDataList(box);	// Tree Datas
				request.setAttribute("TreeData", data1);	

				v_url = "/beta/admin/z_BetaEduStart_ftree_OBC.jsp";
			} else if (data.getContenttype().equals("S")){	// SCORM MasterForm

			    ArrayList	data1	= eduBean.SelectMappingSubjList(box);	// Tree Datas
				request.setAttribute("TreeData", data1);			

				subjnm = eduBean.SelectSubjName(box);
				box.put("subjnm",subjnm);

				v_url = "/beta/admin/z_BetaEduStart_ftree_SCORM.jsp";
			}
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("ftreePage()\r\n" + ex.getMessage());
		}            
	} 
	
	/**
	fbott
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void fbottPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url="";
 
            box.put("p_subj",box.getSession("s_subj"));	
                        
			BetaMasterFormBean bean = new BetaMasterFormBean();

			BetaMasterFormData 	data = bean.SelectBetaMasterFormData(box);	//마스터폼 정보
			request.setAttribute("BetaMasterFormData", data);
			
			v_url = "/beta/admin/z_BetaEduStart_fbott_OBC.jsp";
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("fbottPage()\r\n" + ex.getMessage());
		}            
	}	

	/**
	진도체크
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void eduCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{     
			
System.out.println("box=========================================="+box);            

			String v_url  = "/beta/admin/z_BetaEduChk_after.jsp";

			BetaEduStartBean bean = new BetaEduStartBean();
			String results = bean.EduCheck(box);
			String v_msg = "";
			
			AlertManager alert = new AlertManager();                        
			if(!results.equals("OK")) {            	
				v_msg = "update.fail";   
				//alert.alertFailMessage(out, v_msg);   
				//alert.alertFailMessage(out, results);
				//alert.alertOkMessage(out, v_msg, v_url , box, false, false);
				alert.alertOkMessage(out, results, v_url , box);       
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("eduCheck()\r\n" + ex.getMessage());
		}            
	}			
	/**
	eduList
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void eduListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url="";
            if(box.getString("p_subj").equals(""))
            	box.put("p_subj",box.getSession("s_subj"));	
            
			BetaMasterFormBean mfbean = new BetaMasterFormBean();
			BetaMasterFormData 	data = mfbean.SelectBetaMasterFormData(box);	//마스터폼 정보
			request.setAttribute("BetaMasterFormData", data);
			BetaEduStartBean 	bean = new BetaEduStartBean();

			/* modified by LeeSuMin 2004.02.23. for OBC 			 				
			ArrayList		data1= bean.SelectEduList(box);				//mfSubj Menu List
			request.setAttribute("EduList", data1);
			EduScoreData	data2= bean.SelectEduScore(box);
			request.setAttribute("EduScore", data2);
			
			if (data.getContenttype().equals("N")){					    //Normal MasterForm
				v_url = "/learn/user/contents/z_EduChk_List.jsp";
			}
			*/
			ArrayList		data1= null;
			
			BetaEduScoreData	data2= bean.SelectEduScore(box);
			request.setAttribute("EduScore", data2);
			
			if (data.getContenttype().equals("N")){					      //Normal MasterForm (OLD)
				data1= bean.SelectEduList(box);						      //진도데이터
			
				v_url = "/beta/admin/z_BetaEduChk_List.jsp";
			}else if (data.getContenttype().equals("M")){			      //Normal MasterForm (OLD)
				data1= bean.SelectEduList(box);						      //진도데이터
				
				v_url = "/beta/admin/z_BetaEduChk_List.jsp";		
			}else if (data.getContenttype().equals("O") || data.getContenttype().equals("S")){			//OBC,SCORM MasterForm
				data1= bean.SelectEduListOBC(box);					      //진도데이터

				v_url = "/beta/admin/z_BetaEduChk_List_OBC.jsp";
			}
			request.setAttribute("EduList", data1);


            /* ==========   권장진도율, 자기진도율 시작 ==========*/
            if(box.getString("p_year").equals(""))
            	box.put("p_year",box.getSession("s_year"));	
            if(box.getString("p_subjseq").equals(""))
            	box.put("p_subjseq",box.getSession("s_subjseq"));	

            SubjGongAdminBean sbean = new SubjGongAdminBean();

            if(box.getString("p_subj").equals(""))
            	box.put("p_subj",box.getSession("s_subj"));	
			String promotion  = sbean.getPromotion(box);
			request.setAttribute("promotion", promotion);
			String progress = sbean.getProgress(box);
			request.setAttribute("progress", progress);
            /* ==========   권장진도율, 자기진도율 끝  ==========*/
			
			
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("eduListPage()\r\n" + ex.getMessage());
		}            
	}
}
