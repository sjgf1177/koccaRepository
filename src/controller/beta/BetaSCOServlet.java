//**********************************************************
//  1. 제      목: SCO Object Operation Servlet
//  2. 프로그램명: SCOServlet.java
//  3. 개      요: SCO관리에 관련된 Servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 11.11
//  7. 수      정: 김기수 2004. 11.11
//**********************************************************

package controller.beta;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BetaSCOBean;
import com.credu.beta.BetaSCOData;
import com.credu.library.AlertManager;
import com.credu.library.ConfigSet;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.scorm.FileUtilAdd;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.BetaSCOServlet")
public class BetaSCOServlet extends HttpServlet implements Serializable {

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
			if (!AdminUtil.getInstance().checkRWRight("BetaSCOServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("listPage")) {      			// Scorm List
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("searchList")) {      	// Scorm 검색화면
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("insertsco")) {      	//  Scorm 폴더 업데이트 
				this.performInsertSco(request, response, box, out);
			} else if (v_process.equals("insertPage")) {      	// Scorm 등록화면
				this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("insertSave")) {      		//Scorm 등록저장
				this.performInsertSave(request, response, box, out);
			} else if (v_process.equals("updatePage")) {      		//Scorm 수정화면
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("viewPage")) {      		//Scorm 조회화면
				this.performUpdateViewPage(request, response, box, out);			
			} else if (v_process.equals("updateSave")) {      		//Scorm 수정저장
				this.performUpdateSave(request, response, box, out);				
			} else if (v_process.equals("deleteSave")) {      		//물리적인 Scorm 삭제
				this.performDeleteSave(request, response, box, out);
			} else if (v_process.equals("folderdelete")) {      		//Scorm 삭제
				this.performDeletefolder(request, response, box, out);
			} else if (v_process.equals("deletesco")) {      		//논리적인 Scorm 삭제
				this.performDeleteSco(request, response, box, out);
			} else if (v_process.equals("deletescocontent")) {      		//논리적인 Scorm 삭제
				this.performDeleteScoContent(request, response, box, out);
			} else if (v_process.equals("ContentlistPage")) {      		//Scorm 패키지 리스트
				this.performScoPackageList(request, response, box, out);
			} else if (v_process.equals("deletepackage")) {      		//Scorm 패키지 삭제
				this.performDeletePackage(request, response, box, out);
			} else if (v_process.equals("PackageContentList")) {      		//패키지 리스트 화면
				this.performContentList(request, response, box, out);			
			}
			

		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	마스터폼 리스트 조회
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/beta/admin/za_BetaSCO_L.jsp";
          		if (box.getString("p_action").equals("go")) {            
				BetaSCOBean bean = new BetaSCOBean();
				ArrayList list1 = bean.SelectObjectList(box);
				request.setAttribute("ObjectList", list1);
				}
				
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

	/**
	스콤 저장
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void performInsertSco(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     

			request.setAttribute("requestbox", box);            
			String v_return_url = "/beta/admin/za_BetaSCOIN_L.jsp";	
						
			String	ScoLocateNo	= box.getString("ScoLocateNo");
		    String	v_manifestfilename		= box.getString("v_manifestfilename");
		    
			BetaSCOBean Bean = new BetaSCOBean();
            Bean.InsertScormContent(ScoLocateNo, v_manifestfilename, box );

			ArrayList list1 = Bean.SelectScoInData(box);
			request.setAttribute("ScoObjectList", list1);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

	/**
	PAGE
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
			String v_url = "/beta/admin/za_BetaSCO_I.jsp";
			box.put("s_subj",box.getString("s_subj"));	

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
		}            
	} 

	/**
	Object등록 저장
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performInsertSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSCOServlet";

			BetaSCOBean bean = new BetaSCOBean();
			String results = bean.InsertObject(box);
            
			String v_msg = "";
			box.put("p_process", "listPage");
			box.put("s_subj",box.getString("s_subj"));
			box.put("s_gubun",box.getString("s_gubun"));
			box.put("p_action",box.getString("go"));
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "insert.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}
   
	/**
	PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{
			
			String v_url="";
			request.setAttribute("requestbox", box);

		    v_url = "/beta/admin/za_BetaSCO_U.jsp";			    
			            
			BetaSCOBean bean = new BetaSCOBean();
			BetaSCOData data = bean.SelectObjectData(box);	//Object 정보
			request.setAttribute("SCOData", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
		}            
	} 

	/**
	SCO Contents View PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void performUpdateViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{
			
			String v_url="";
			request.setAttribute("requestbox", box);
			box.put("p_content", "2");

		    v_url = "/beta/admin/za_BetaSCO_Contents_L.jsp";			    
			            
			BetaSCOBean bean = new BetaSCOBean();
			BetaSCOData data = bean.SelectObjectData(box);	//Object 정보
			request.setAttribute("SCOData", data);
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
		}            
	} 

	/**
	Object수정 저장
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performUpdateSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.beta.BetaSCOServlet";

			BetaSCOBean bean = new BetaSCOBean();
			String results = bean.UpdateObject(box);
            
			String v_msg = "";
			box.put("p_process", "PackageContentList");
			box.put("s_subj",box.getString("s_subj"));
			box.put("s_gubun",box.getString("s_gubun"));
			box.put("p_action",box.getString("go"));
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "update.ok";       
				alert.alertOkMessage(out, v_msg, v_url, box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}

	/**
	Object삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performDeleteSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{                
			String v_url  = "/servlet/controller.beta.BetaSCOServlet";

			BetaSCOBean bean = new BetaSCOBean();
			String results = bean.UpdateObject(box);
            
			String v_msg = "";
			box.put("p_process", "updatePage");
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}

	/**
	물리적인 Scorm 폴더 삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performDeletefolder(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{ 
			
			ConfigSet conf = new ConfigSet();
			String savePath = conf.getProperty("dir.scoobjectpath");
			String	p_scolocate	= box.getString("p_scolocate");


			File dir = new File(savePath + p_scolocate);

			if (dir.exists()) {	// 만들려는 폴더가 존재하면 해당 폴더 먼저 지워버림
					FileUtilAdd fua = new FileUtilAdd();
					fua.deleteDir(dir);
			}

            box.put("v_unzip_flag", "0");
			box.put("v_manifestfilename", "");
			request.setAttribute("requestbox", box);            
			String v_url = "/beta/za_BetaSCO_I.jsp";			

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);        
			
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDeletefolder()\r\n" + ex.getMessage());
		}            
	}

	/**
	논리적인 Sco 삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performDeleteSco(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{					
			request.setAttribute("requestbox", box);  
			
			BetaSCOBean Bean = new BetaSCOBean();				
			String results = Bean.DeleteScoOid(box);			

			String v_url  = "/servlet/controller.beta.BetaSCOServlet";
            
			String v_msg = "";
			box.put("p_process", "PackageContentList");
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "update.ok";       
				alert.alertOkMessage(out, v_msg, v_url, box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			}  

			
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDeletefolder()\r\n" + ex.getMessage());
		}            
	}

	/**
	논리적인 Sco 삭제
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performDeleteScoContent(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{					
			request.setAttribute("requestbox", box);  
			
			BetaSCOBean Bean = new BetaSCOBean();							
			String results =  Bean.DeleteScoOid(box);

			String v_url  = "/servlet/controller.beta.BetaSCOServlet";
            
			String v_msg = "";
			box.put("p_process", "PackageContentList");
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url, box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			}  
	
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDeletefolder()\r\n" + ex.getMessage());
		}            
	}

	/**
	Scorm Package List
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performScoPackageList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{					
			request.setAttribute("requestbox", box); 
						
			BetaSCOBean bean = new BetaSCOBean();	
			ArrayList list1 = bean.performScoPackageList(box);

			String v_url = "/beta/admin/za_BetaSCOPackage_L.jsp";	
			request.setAttribute("ScoPackageList", list1);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response); 

		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performScoPackageList()\r\n" + ex.getMessage());
		}            
	}

	/**
	Scorm Package Delete
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	public void performDeletePackage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{					
			request.setAttribute("requestbox", box); 
			String v_url = "/beta/admin/za_BetaSCOPackage_L.jsp";	
			
			String v_msg = "";
			box.put("p_process", "PackageContentList");
            
			BetaSCOBean bean = new BetaSCOBean();			
			String results =  bean.DeleteScoPackage(box);  // DB 삭제
			
			AlertManager alert = new AlertManager();                        
			if(results.equals("OK")) { 
				
				ConfigSet conf = new ConfigSet();
				String savePath = conf.getProperty("dir.scoobjectpath");
				String scolocate	= box.getString("p_scolocate");

				File dir = new File(savePath + scolocate);

				if (dir.exists()) {	// 물리적인 파일 삭제
						FileUtilAdd fua = new FileUtilAdd();
						fua.deleteDir(dir);
				}

				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);           
			}else {                
				v_msg = results;   
				alert.alertFailMessage(out, v_msg);   
			} 

		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performScoPackageList()\r\n" + ex.getMessage());
		}            
	}

	/**
	스콤 Content List
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/

	public void performContentList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     

			request.setAttribute("requestbox", box);            
			String v_return_url ="";
			
			BetaSCOBean Bean = new BetaSCOBean();
           
			ArrayList list1 = Bean.SelectScoInData(box);
		
			if (list1.size() > 0 ) {
				v_return_url = "/beta/admin/za_BetaSCOContent_L.jsp";				
				request.setAttribute("ScoObjectList", list1);
			} else {
			    v_return_url = "/beta/admin/za_BetaSCO_L.jsp";	
				box.put("p_process", "listPage");
			} 

			request.setAttribute("ScoObjectList", list1);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

	
}