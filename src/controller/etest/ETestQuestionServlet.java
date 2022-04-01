//**********************************************************
//1. 제      목: 온라인테스트 문항관리
//2. 프로그램명: ETestQuestionServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: 
//**********************************************************

package controller.etest;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestQuestionBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

@WebServlet("/servlet/controller.etest.ETestQuestionServlet")
public class ETestQuestionServlet extends HttpServlet implements Serializable {
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
            v_process = box.getStringDefault("p_process", "ETestQuestionListPage");
			System.out.println("E-Test 문제관리 ETestQuestionServlet : "+v_process);			

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ETestQuestionServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ETestQuestionListPage")) {                //온라인테스트 문제 리스트
                this.performETestQuestionListPage(request, response, box, out);
            } else if (v_process.equals("ETestQuestionInsertPage")) {              //온라인테스트 문제 등록 페이지
                this.performETestQuestionInsertPage(request, response, box, out);
            } else if (v_process.equals("ETestQuestionUpdatePage")) {              //온라인테스트 문제 수정 페이지
                this.performETestQuestionUpdatePage(request, response, box, out);
            } else if (v_process.equals("ETestQuestionInsert")) {                  //온라인테스트 문제 등록할때
                this.performETestQuestionInsert(request, response, box, out);
            } else if (v_process.equals("ETestQuestionUpdate")) {                  //온라인테스트 문제 수정하여 저장할때
                this.performETestQuestionUpdate(request, response, box, out);
            } else if (v_process.equals("ETestQuestionDelete")) {                  // 온라인테스트 문제 삭제할때
                this.performETestQuestionDelete(request, response, box, out);
            } else if (v_process.equals("ETestQuestionFileToDB")) {               // 온라인테스트 대상자 FileToDB
                this.performETestQuestionFileToDB(request, response, box, out);
            } else if (v_process.equals("insertFileToDB")) {                    // 온라인테스트 대상자 FileToDB 입력 
                this.performInsertFileToDB(request, response, box, out);
            } else if (v_process.equals("previewFileToDB")) {                   // 온라인테스트 대상자 FileToDB 미리보기
                this.performPreviewFileToDB(request, response, box, out);
            } else if (v_process.equals("ETestQuestionPoolPage")) {              //온라인테스트 문제 풀 페이지
                this.performETestQuestionPoolPage(request, response, box, out);
            } else if (v_process.equals("QuestionPoolListPage")) {              //온라인테스트 문제 풀 페이지
                this.performQuestionPoolListPage(request, response, box, out);
            } else if (v_process.equals("ETestQuestionInsertPool")) {              //온라인테스트 문제 풀 등록
                this.performETestQuestionInsertPool(request, response, box, out);
            } else if (v_process.equals("ETestQuestionPreview")) {              //온라인테스트 문제 미리 보기
                this.performETestQuestionPreview(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    온라인테스트 문제 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestQuestion_L.jsp";

            ETestQuestionBean bean = new ETestQuestionBean();
            ArrayList list1 = bean.selectQuestionList(box);
            request.setAttribute("QuestionList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestQuestion_I.jsp";
            if (box.getString("p_etesttype").equals("2")) v_return_url = "/learn/admin/etest/za_ETestQuestion_I2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    온라인테스트 문제 수정 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestQuestion_U.jsp";
            if (box.getString("p_etesttype").equals("2")) v_return_url = "/learn/admin/etest/za_ETestQuestion_U2.jsp";

            ETestQuestionBean bean = new ETestQuestionBean();
	        ArrayList list1 = bean.selectExampleData(box);
            request.setAttribute("QuestionExampleData", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestQuestionServlet";

            ETestQuestionBean bean = new ETestQuestionBean();
            int isOk = bean.insertQuestion(box);

            String v_msg = "";
            box.put("p_process", "ETestQuestionInsertPage");
            box.put("p_etestnum",  "0");

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
            throw new Exception("performETestQuestionInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestQuestionServlet";

            ETestQuestionBean bean = new ETestQuestionBean();
            int isOk = bean.updateQuestion(box);

            String v_msg = "";
            box.put("p_process", "ETestQuestionUpdatePage");
			box.put("p_end",  "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
						}else if(isOk==-2){                
							v_msg = "해당 문제는 사용중입니다!";   
							alert.alertFailMessage(out, v_msg);	                
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestQuestionServlet";

            ETestQuestionBean bean = new ETestQuestionBean();
            int isOk = bean.deleteQuestion(box);

            String v_msg = "";
            box.put("p_process", "ETestQuestionUpdatePage");
			box.put("p_end",  "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
			}else if(isOk==-2){                
				v_msg = "해당 문제는 사용중입니다!";   
				alert.alertFailMessage(out, v_msg);	                     
            }else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 FileToDB
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestQuestionFileToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionFileToDB()\r\n" + ex.getMessage());
        }
    }
    
    /**
    온라인테스트 문제 FileToDB 입력 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestQuestionFileToDB_P.jsp";
            if (box.getString("p_select").equals("2")) v_return_url = "/learn/admin/etest/za_ETestQuestionFileToDB_P2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);    
		 }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 FileToDB 미리보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestQuestionFileToDB_P.jsp";
            if (box.getString("p_select").equals("2")) v_return_url = "/learn/admin/etest/za_ETestQuestionFilToDB_P2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);    
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 Pool
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionPoolPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/admin/etest/za_ETestQuestionPool_I.jsp";
                        
			ETestQuestionBean bean = new ETestQuestionBean();
			ArrayList list1 = bean.selectQuestionPool(box);
			request.setAttribute("ETestQuestionPool", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionPoolPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 Pool 페이지로 이동 (검색후)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performQuestionPoolListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestQuestionPool_I.jsp";
            
            ETestQuestionBean bean = new ETestQuestionBean();
            
            ArrayList list = bean.selectQuestionPoolList(box);
            request.setAttribute("ETestQuestionPool", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);   
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performQuestionPoolListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionInsertPool(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.etest.ETestQuestionServlet";
           
			ETestQuestionBean bean = new ETestQuestionBean();
			int isOk = bean.insertQuestionPool(box);
            
			String v_msg = "";
			box.put("p_process", "ETestQuestionPoolPage");
			box.put("p_action",  "go");
      
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
            throw new Exception("performETestQuestionInsertPool()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 문제 미리보기 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestQuestionPreview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
		    String v_return_url = "/learn/admin/etest/za_ETestQuestionPreview.jsp";
            if (box.getString("p_etesttype").equals("2")) v_return_url = "/learn/admin/etest/za_ETestQuestionPreview2.jsp";
                        
			ETestQuestionBean bean = new ETestQuestionBean();
			ArrayList list1 = bean.selectExampleData(box); 
			request.setAttribute("QuestionExampleData", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestQuestionPreview()\r\n" + ex.getMessage());
        }
    }
}