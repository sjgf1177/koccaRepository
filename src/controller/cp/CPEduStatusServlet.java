//**********************************************************
//  1. 제      목: 과정관리 서블릿
//  2. 프로그램명 : CPEduStatusServlet.java
//  3. 개      요: 과정관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12.  21
//  7. 수     정1:
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPEduStatusBean;
import com.credu.cp.CPResultBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
*과정관리
*<p>제목:CPEduStatusServlet.java</p>
*<p>설명:과정관리 서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/
@WebServlet("/servlet/controller.cp.CPEduStatusServlet")
public class CPEduStatusServlet extends javax.servlet.http.HttpServlet {
    
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
			if (!AdminUtil.getInstance().checkRWRight("CPEduStatusServlet", v_process, out, box)) {
				return; 
			}
			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("listPage")) {                                
                this.performListPage(request, response, box, out);
            }
            else if(v_process.equals("insertPage")) {
                this.performInsertPage(request, response, box, out);
            }
            else if(v_process.equals("statusUpdate")) {             //엑셀파일 결과업데이트페이지
                this.performInsertFileToDB(request, response, box, out);
            }
            else if(v_process.equals("insertFileToDB")) {             //엑셀파일 결과업데이트페이지
                this.performInsertFileToDB(request, response, box, out);
            }
            else if(v_process.equals("openUpdateList")) {             //학습현황 List
                this.performUpdateList(request, response, box, out);
            }
            
            else if(v_process.equals("statusInsertAllPage")) {   //엑셀파일 결과업데이트페이지
                this.performInsertAllPage(request, response, box, out);
            }
            else if(v_process.equals("statusInsertAll")) {   //엑셀파일 결과업데이트
                this.performInsertAll(request, response, box, out);
            }
            else if(v_process.equals("insertFileToDBALL")) {   //엑셀파일 결과업데이트
                this.performInsertAll(request, response, box, out);
            }
            else if(v_process.equals("ExcelDown")) {   //엑셀파일 과정코드표 down
                this.performExcelDown(request, response, box, out);
            }
            
            
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

	/**
	외주업체 과정리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPEduStatusBean cpCourse = new CPEduStatusBean();
            
            ArrayList list = cpCourse.selectCourseList(box);
            
            request.setAttribute("selectEduStatuslList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_studystatus_L.jsp");
            rd.forward(request, response);
            Log.info.println(this, box, "/servlet/controller.cp.CPEduStatusServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    
	/**
	과정코드 등록 PAGE - 사이버
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
			String v_return_url = "/cp/admin/za_studystatus_i.jsp";
			CPEduStatusBean cpCourse = new CPEduStatusBean();
		    DataBox dbox = cpCourse.recentUpdateStatus(box);
		    request.setAttribute("recentStatus", dbox);
		    

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}            
	}
	
	
	/**
	EXCEL 교육현황등록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/cp/admin/za_studystatusinput.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
		}
	}
	
	
	
	/**
    교육현황 업데이트 일시 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            //if (box.getString("p_action").equals("go")) {
	            CPEduStatusBean cpCourse = new CPEduStatusBean();
	            
	            ArrayList list = cpCourse.selectUpdateList(box);            
	            request.setAttribute("selectUpdateList", list);
            //}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpUpdate_l.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPCourseSeqServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    /**
	외주업체 과정 교육현황 일괄등록 페이지
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertAllPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_studystatusAll_i.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
	과정코드표 EXCEL down
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/cp/admin/za_cpeduResultAll_E.jsp";
			
			CPResultBean cpResult = new CPResultBean();            
	        ArrayList list = cpResult.selectExcelSubjDown(box);            
	        request.setAttribute("selectExcelSubjDown", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
		}
	}
	

	/**
	EXCEL 교육결과등록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/cp/admin/za_cpeduStatusALLinput.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
		}
	}
	
}