//**********************************************************
//1. 제      목: 척도 관리
//2. 프로그램명: ScaleServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-11-05
//7. 수      정:
//
//**********************************************************

package controller.research;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.ScaleBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.research.ScaleServlet")
public class ScaleServlet extends HttpServlet implements Serializable {
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

			v_process = box.getString("p_process");


            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ScaleServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ScaleListPage")) {                //척도 리스트
                this.performScaleListPage(request, response, box, out);
            } else if (v_process.equals("ScaleInsertPage")) {              //척도 등록 페이지
                this.performScaleInsertPage(request, response, box, out);
            } else if (v_process.equals("ScaleUpdatePage")) {              //척도 수정 페이지
                this.performScaleUpdatePage(request, response, box, out);
            } else if (v_process.equals("ScaleInsert")) {                  //척도 등록할때
                this.performScaleInsert(request, response, box, out);
            } else if (v_process.equals("ScaleUpdate")) {                  //척도 수정하여 저장할때
                this.performScaleUpdate(request, response, box, out);
            } else if (v_process.equals("ScaleDelete")) {                  // 척도 삭제할때
                this.performScaleDelete(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    척도 문제 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performScaleListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_Scale_L.jsp";
                        
			ScaleBean bean = new ScaleBean();
			ArrayList list1 = bean.selectScaleList(box);
			request.setAttribute("ScaleList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    척도 문제 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performScaleInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
  			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_Scale_I.jsp";
                        
		//	ScaleBean bean = new ScaleBean();
		//	ScaleQuestionExampleData ScaleExample = bean.selectScaleExample(box); 
		//	request.setAttribute("ScaleExampleData", ScaleExample);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response); 
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    척도 문제 수정 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performScaleUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_Scale_U.jsp";
                        
			ScaleBean bean = new ScaleBean();
			ArrayList list = bean.selectScaleExample(box); 
			request.setAttribute("ScaleExampleData", list);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);        
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    척도 문제 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performScaleInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.research.ScaleServlet";
           
			ScaleBean bean = new ScaleBean();
			int isOk = bean.insertScale(box);

			String v_msg = "";
			box.put("p_process", "ScaleInsertPage");
      
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "insert.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "insert.fail";   
				alert.alertFailMessage(out, v_msg);   
			}            


		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    척도 문제 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performScaleUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.research.ScaleServlet";
           
			ScaleBean bean = new ScaleBean();
			int isOk = bean.updateScale(box);
            
			String v_msg = "";
			box.put("p_process", "ScaleListPage");
      
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "update.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "update.fail";   
				alert.alertFailMessage(out, v_msg);   
			}        

		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    척도 문제 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performScaleDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.research.ScaleServlet";
           
			ScaleBean bean = new ScaleBean();
			int isOk = bean.deleteScale(box);
            
			String v_msg = "";
			box.put("p_process", "ScaleListPage");
     
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else if(isOk==-2){                
				v_msg = "해당 척도는 사용중입니다.";   
				alert.alertFailMessage(out, v_msg);   				
			}else {                
				v_msg = "delete.fail";   
				alert.alertFailMessage(out, v_msg);   
			}            
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsert()\r\n" + ex.getMessage());
        }
    }
}