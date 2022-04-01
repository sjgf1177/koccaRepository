//**********************************************************
//1. ��      ��: �� ���� ����
//2. ���α׷���: BPExamQuestionServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-11-05
//7. ��      ��:
//
//**********************************************************

package controller.beta;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BPExamQuestionBean;
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
@WebServlet("/servlet/controller.beta.BPExamQuestionServlet")
public class BPExamQuestionServlet extends HttpServlet implements Serializable {
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

			v_process = box.getStringDefault("p_process","ExamQuestionListPage");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("BPExamQuestionServlet", v_process, out, box)) {
                    return;
            }

			if (v_process.equals("ExamQuestionListPage")) {                //�� ���� ����Ʈ
                this.performExamQuestionListPage(request, response, box, out);
            } else if (v_process.equals("ExamQuestionInsertPage")) {              //�� ���� ��� ������
                this.performExamQuestionInsertPage(request, response, box, out);
            } else if (v_process.equals("ExamQuestionUpdatePage")) {              //�� ���� ���� ������
                this.performExamQuestionUpdatePage(request, response, box, out);
            } else if (v_process.equals("ExamQuestionInsert")) {                  //�� ���� ����Ҷ�
                this.performExamQuestionInsert(request, response, box, out);
            } else if (v_process.equals("ExamQuestionUpdate")) {                  //�� ���� �����Ͽ� �����Ҷ�
                this.performExamQuestionUpdate(request, response, box, out);
            } else if (v_process.equals("ExamQuestionDelete")) {                  // �� ���� �����Ҷ�
                this.performExamQuestionDelete(request, response, box, out);
            } else if (v_process.equals("ExamQuestionFileToDB")) {               // �� ���� FileToDB
                this.performExamQuestionFileToDB(request, response, box, out);
            } else if (v_process.equals("insertFileToDB")) {                    // �� ���� FileToDB �Է� 
                this.performInsertFileToDB(request, response, box, out);
            } else if (v_process.equals("previewFileToDB")) {                   // �� ���� FileToDB �̸�����
                this.performPreviewFileToDB(request, response, box, out);
            } else if (v_process.equals("ExamQuestionPoolPage")) {              //�� ���� Ǯ ������
                this.performExamQuestionPoolPage(request, response, box, out);
            } else if (v_process.equals("QuestionPoolListPage")) {              //�� ���� Ǯ ������
                this.performQuestionPoolListPage(request, response, box, out);
            } else if (v_process.equals("ExamQuestionInsertPool")) {              //�� ���� Ǯ ���
                this.performExamQuestionInsertPool(request, response, box, out);
            } else if (v_process.equals("ExamQuestionPreview")) {              //�� ���� �̸� ����
                this.performExamQuestionPreview(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �� ���� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/beta/admin/za_ExamQuestion_L.jsp";
                        
			BPExamQuestionBean bean = new BPExamQuestionBean();
			ArrayList list1 = bean.selectQuestionList(box);
			request.setAttribute("ExamQuestionList", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamQuestionListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� ��� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);        
		    String v_return_url = "/beta/admin/za_ExamQuestion_I.jsp";
            if (box.getString("p_examtype").equals("2")) v_return_url = "/beta/admin/za_ExamQuestion_I2.jsp";
            if (box.getString("p_examtype").equals("3")) v_return_url = "/beta/admin/za_ExamQuestion_I3.jsp";            
						
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamQuestionInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� ���� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
		    String v_return_url = "/beta/admin/za_ExamQuestion_U.jsp";
            if (box.getString("p_examtype").equals("2")) v_return_url = "/beta/admin/za_ExamQuestion_U2.jsp";
            if (box.getString("p_examtype").equals("3")) v_return_url = "/beta/admin/za_ExamQuestion_U3.jsp";   
			BPExamQuestionBean bean = new BPExamQuestionBean();
			ArrayList list1 = bean.selectExampleData(box); 
			request.setAttribute("QuestionExampleData", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
			
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamQuestionUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.beta.BPExamQuestionServlet";
           
			BPExamQuestionBean bean = new BPExamQuestionBean();
			int isOk = bean.insertQuestion(box);
            
			String v_msg = "";
			box.put("p_process", "ExamQuestionInsertPage");
			box.put("p_examnum",  "0");
      
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
            throw new Exception("performExamQuestionInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.beta.BPExamQuestionServlet";
           
			BPExamQuestionBean bean = new BPExamQuestionBean();
			int isOk = bean.updateQuestion(box);
            
			String v_msg = "";
			box.put("p_process", "ExamQuestionUpdatePage");
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
            throw new Exception("performExamQuestionUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.beta.BPExamQuestionServlet";
           
			BPExamQuestionBean bean = new BPExamQuestionBean();
			int isOk = bean.deleteQuestion(box);
            
			String v_msg = "";
			box.put("p_process", "ExamQuestionUpdatePage");
			box.put("p_end",  "0");
     
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "delete.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "delete.fail";   
				alert.alertFailMessage(out, v_msg);   
			}       
 		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamQuestionDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� FileToDB
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/beta/admin/za_ExamQuestionFileToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamQuestionFileToDB()\r\n" + ex.getMessage());
        }
    }
    
    /**
    �� ���� FileToDB �Է� 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/beta/admin/za_ExamQuestionFileToDB_P.jsp";
            if (box.getString("p_select").equals("2")) v_return_url = "/beta/admin/za_ExamQuestionFileToDB_P2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);    
		 }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� FileToDB �̸�����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/beta/admin/za_ExamQuestionFileToDB_P.jsp";
            if (box.getString("p_select").equals("2")) v_return_url = "/beta/admin/za_ExamQuestionFileToDB_P2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);    
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� Pool
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionPoolPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
			String v_return_url = "/beta/admin/za_ExamQuestionPool_I.jsp";
                        
			BPExamQuestionBean bean = new BPExamQuestionBean();
			ArrayList list1 = bean.selectQuestionPool(box);
			request.setAttribute("ExamQuestionPool", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamQuestionPoolPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� Pool �������� �̵� (�˻���)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performQuestionPoolListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/beta/admin/za_ExamQuestionPool_I.jsp";
System.out.println("v_return_url===========>>>>>>"+v_return_url);            
            BPExamQuestionBean bean = new BPExamQuestionBean();
            
            ArrayList list = bean.selectQuestionPoolList(box);
            request.setAttribute("ExamQuestionPool", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);   
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performQuestionPoolListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionInsertPool(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.beta.BPExamQuestionServlet";
           
			BPExamQuestionBean bean = new BPExamQuestionBean();
			int isOk = bean.insertQuestionPool(box);
            
			String v_msg = "";
			box.put("p_process", "ExamQuestionPoolPage");
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
            throw new Exception("performExamQuestionInsertPool()\r\n" + ex.getMessage());
        }
    }

    /**
    �� ���� �̸����� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamQuestionPreview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
		    String v_return_url = "/beta/admin/za_ExamQuestionPreview.jsp";
            if (box.getString("p_examtype").equals("2")) v_return_url = "/beta/admin/za_ExamQuestionPreview2.jsp";
                        
			BPExamQuestionBean bean = new BPExamQuestionBean();
			ArrayList list1 = bean.selectExampleData(box); 
			request.setAttribute("QuestionExampleData", list1);
            
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamQuestionPreview()\r\n" + ex.getMessage());
        }
    }
}