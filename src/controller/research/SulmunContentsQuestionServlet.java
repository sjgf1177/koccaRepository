//**********************************************************
//1. 제      목: 컨텐츠 평가 문제 관리
//2. 프로그램명: SulmunContentsQuestionServlet.java
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
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.ScaleBean;
import com.credu.research.SulmunContentsQuestionBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.research.SulmunContentsQuestionServlet")
public class SulmunContentsQuestionServlet extends HttpServlet implements Serializable {
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
        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

			v_process = box.getStringDefault("p_process","SulmunQuestionListPage");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("SulmunContentsQuestionServlet", v_process, out, box)) {
                    return;
            }

			if (v_process.equals("SulmunQuestionListPage")) {                //컨텐츠 평가 문제 리스트
                this.performSulmunQuestionListPage(request, response, box, out);
            } else if (v_process.equals("SulmunQuestionInsertPage")) {              //컨텐츠 평가 문제 등록 페이지
                this.performSulmunQuestionInsertPage(request, response, box, out);
            } else if (v_process.equals("SulmunQuestionInsertS")) {              //컨텐츠 평가 문제 등록 페이지
                this.performSulmunQuestionInsertS(request, response, box, out);
            } else if (v_process.equals("SulmunQuestionUpdatePage")) {              //컨텐츠 평가 문제 수정 페이지
                this.performSulmunQuestionUpdatePage(request, response, box, out);
            } else if (v_process.equals("SulmunQuestionUpdateS")) {              //컨텐츠 평가 문제 수정 페이지
                this.performSulmunQuestionUpdateS(request, response, box, out);
            } else if (v_process.equals("SulmunQuestionInsert")) {                  //컨텐츠 평가 문제 등록할때
                this.performSulmunQuestionInsert(request, response, box, out);
            } else if (v_process.equals("SulmunQuestionUpdate")) {                  //컨텐츠 평가 문제 수정하여 저장할때
                this.performSulmunQuestionUpdate(request, response, box, out);
            } else if (v_process.equals("SulmunQuestionDelete")) {                  // 컨텐츠 평가 문제 삭제할때
                this.performSulmunQuestionDelete(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    설문 문제 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performSulmunQuestionListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunContentsQuestion_L.jsp";
                        
			SulmunContentsQuestionBean bean = new SulmunContentsQuestionBean();
			ArrayList list1 = bean.selectQuestionList(box);
			request.setAttribute("SulmunQuestionList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunQuestionListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 문제 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSulmunQuestionInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
  			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunContentsQuestion_I.jsp";
                        
			ScaleBean sbean = new ScaleBean();
			ArrayList list1 = sbean.selectScaleGubunExample(box); 
			request.setAttribute("ScaleQuestionExampleData", list1);
                        
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response); 
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunQuestionInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 문제 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSulmunQuestionInsertS(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
  			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunContentsQuestion_I2.jsp";
                        
			ScaleBean sbean = new ScaleBean();
			ArrayList list1 = sbean.selectScaleGubunExample(box); 
			request.setAttribute("ScaleQuestionExampleData", list1);

 	        DataBox dbox0 = (DataBox)list1.get(0);
	        int v_scalecode = dbox0.getInt("d_scalecode");  		

   			box.put("p_scalecode", Integer.toString(v_scalecode));
						
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response); 
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunQuestionInsertS()\r\n" + ex.getMessage());
        }
    }
    
    /**
    설문 문제 수정 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSulmunQuestionUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunContentsQuestion_U.jsp";
         
			SulmunContentsQuestionBean bean = new SulmunContentsQuestionBean();
			ArrayList list = bean.selectQuestionExample(box); 
			request.setAttribute("SulmunQuestionExampleData", list);

 	        DataBox dbox0 = (DataBox)list.get(0);
	        int v_scalecode = dbox0.getInt("d_scalecode");  		
		
			box.put("p_scalecode", Integer.toString(v_scalecode));

			ScaleBean sbean = new ScaleBean();
			ArrayList list1 = sbean.selectScaleGubunExample(box); 
			request.setAttribute("ScaleQuestionExampleData", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);   
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunQuestionUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 문제 수정 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSulmunQuestionUpdateS(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/research/za_SulmunContentsQuestion_U2.jsp";
   
   			SulmunContentsQuestionBean bean = new SulmunContentsQuestionBean();
						
			ArrayList list = bean.selectQuestionExample(box); 
			request.setAttribute("SulmunQuestionExampleData", list);

			ScaleBean sbean = new ScaleBean();
			ArrayList list1 = sbean.selectScaleGubunExample(box); 
			request.setAttribute("ScaleQuestionExampleData", list1);

 	        DataBox dbox0 = (DataBox)list1.get(0);
	        int v_scalecode = dbox0.getInt("d_scalecode");  		

   			box.put("p_scalecode", Integer.toString(v_scalecode));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);   
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunQuestionUpdateS()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 문제 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performSulmunQuestionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.research.SulmunContentsQuestionServlet";
           
			SulmunContentsQuestionBean bean = new SulmunContentsQuestionBean();
			int isOk = bean.insertQuestion(box);

			String v_msg = "";
			box.put("p_process", "SulmunQuestionInsertPage");
      
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
            throw new Exception("performSulmunQuestionInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 문제 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performSulmunQuestionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
 			String v_url  = "/servlet/controller.research.SulmunContentsQuestionServlet";
           
			SulmunContentsQuestionBean bean = new SulmunContentsQuestionBean();
			int isOk = bean.updateQuestion(box);
            
			String v_msg = "";
			box.put("p_process", "SulmunQuestionUpdatePage");
			box.put("p_end",  "0");
      
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
            throw new Exception("performSulmunQuestionUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 문제 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performSulmunQuestionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.research.SulmunContentsQuestionServlet";
           
			SulmunContentsQuestionBean bean = new SulmunContentsQuestionBean();
			int isOk = bean.deleteQuestion(box);
            
			String v_msg = "";
			box.put("p_process", "SulmunQuestionInsertPage");
			box.put("p_end",  "0");
     
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else if(isOk==-2){                
				v_msg = "해당 문제는 사용중입니다";     //해당 문제는 사용중입니다
				alert.alertFailMessage(out, v_msg);						
			}else {                
				v_msg = "delete.fail";   
				alert.alertFailMessage(out, v_msg);   
			}           
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunQuestionDelete()\r\n" + ex.getMessage());
        }
    }
}