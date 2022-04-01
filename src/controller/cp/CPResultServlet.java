//**********************************************************
//  1. 제      목: 과정교육결과 서블릿
//  2. 프로그램명 : CPResultServlet.java
//  3. 개      요: 과정교육결과 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12.  27
//  7. 수     정1:
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPResultBean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
*과정교육결과리스트
*<p>제목:CPResultServlet.java</p>
*<p>설명:과정교육결과 서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

@WebServlet("/servlet/controller.cp.CPResultServlet")
public class CPResultServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
			
			if (!AdminUtil.getInstance().checkRWRight("CPResultServlet", v_process, out, box)) {
				return; 
			}
			
			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("resultInsertPage")) {             //등록폼 페이지로
                this.performInsertPage(request, response, box, out);
            }            
            else if(v_process.equals("resultInsert")) {             //엑셀파일 결과등록
                this.performInsertFileToDB(request, response, box, out);
            }                        
            else if(v_process.equals("resultUpdatePage")) {             //엑셀파일 결과업데이트페이지
                this.performInsertPage(request, response, box, out);
            }
            else if(v_process.equals("insertFileToDB")) {             //교육결과 db등록
                this.performInsertFileToDB(request, response, box, out);
            }
            else if(v_process.equals("listPage")) {   //과정목록조회
                this.performList(request, response, box, out);
            }
            else if(v_process.equals("resultInsertAllPage")) {   //엑셀파일 결과업데이트페이지
                this.performInsertAllPage(request, response, box, out);
            }
            else if(v_process.equals("resultInsertAll")) {   //엑셀파일 결과업데이트
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
	외주업체 과정 리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            if (box.getString("p_action").equals("go")) {        
	            CPResultBean cpResult = new CPResultBean();            
	            ArrayList list = cpResult.selectResultList(box);            
	            request.setAttribute("selectResultList", list);
	        }
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduresult_l.jsp");
            rd.forward(request, response);
            
            //Log.info.println(this, box, "/servlet/controller.cp.CPResultServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

	/**
	외주업체 과정 교육결과 페이지
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);
                      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduResult_i.jsp");
            rd.forward(request, response);
         
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
	외주업체 과정 교육결과 페이지
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
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_cpeduResultAll_i.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
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
	public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/cp/admin/za_cpeduResultinput.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
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
			String v_return_url = "/cp/admin/za_cpeduResultALLinput.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
		}
	}
}
        	
        	