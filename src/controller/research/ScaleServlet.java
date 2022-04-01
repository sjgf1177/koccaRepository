//**********************************************************
//1. ��      ��: ô�� ����
//2. ���α׷���: ScaleServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-11-05
//7. ��      ��:
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

            if (v_process.equals("ScaleListPage")) {                //ô�� ����Ʈ
                this.performScaleListPage(request, response, box, out);
            } else if (v_process.equals("ScaleInsertPage")) {              //ô�� ��� ������
                this.performScaleInsertPage(request, response, box, out);
            } else if (v_process.equals("ScaleUpdatePage")) {              //ô�� ���� ������
                this.performScaleUpdatePage(request, response, box, out);
            } else if (v_process.equals("ScaleInsert")) {                  //ô�� ����Ҷ�
                this.performScaleInsert(request, response, box, out);
            } else if (v_process.equals("ScaleUpdate")) {                  //ô�� �����Ͽ� �����Ҷ�
                this.performScaleUpdate(request, response, box, out);
            } else if (v_process.equals("ScaleDelete")) {                  // ô�� �����Ҷ�
                this.performScaleDelete(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    ô�� ���� ����Ʈ
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
    ô�� ���� ��� ������
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
    ô�� ���� ���� ������
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
    ô�� ���� ����Ҷ�
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
    ô�� ���� �����Ͽ� �����Ҷ�
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
    ô�� ���� �����Ҷ�
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
				v_msg = "�ش� ô���� ������Դϴ�.";   
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