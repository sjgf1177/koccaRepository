//**********************************************************
//1. ��      ��: �¶����׽�Ʈ ����������
//2. ���α׷���: ETestPaperServlet .java
//3. ��      ��:
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 0.1
//6. ��      ��: 
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

import com.credu.etest.ETestPaperBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
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

@WebServlet("/servlet/controller.etest.ETestPaperServlet")
public class ETestPaperServlet extends HttpServlet implements Serializable {
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
            v_process = box.getStringDefault("p_process", "ETestPaperListPage");
			System.out.println("E-Test ���������� ETestPaperServlet : "+v_process);			

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ETestPaperServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ETestPaperListPage")) {                      //�¶����׽�Ʈ ������ ����Ʈ
                this.performETestPaperListPage(request, response, box, out);
            } else if (v_process.equals("ETestPaperInsert")) {
				this.performETestPaperInsert(request, response, box, out);
			} else if (v_process.equals("ETestPaperPreviewPage")) {                 //�¶����׽�Ʈ ������ �̸�����
                this.performETestPaperPreviewPage(request, response, box, out);
            } 

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �¶����׽�Ʈ ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestPaper_L.jsp";

            ETestPaperBean bean = new ETestPaperBean();
            ArrayList list1 = bean.selectETestPaperList(box);
            request.setAttribute("ETestPaperList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestPaperListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    �¶����׽�Ʈ ������ �߰�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performETestPaperInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try{                
		  String v_url  = "/servlet/controller.etest.ETestPaperServlet";
           
		  ETestPaperBean bean1 = new ETestPaperBean();
		  int isOk = bean1.insertETestPaper(box);
            
		  String v_msg = "";
		  box.put("p_process", "ETestPaperListPage");
      
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
			throw new Exception("performETestPaperInsert()\r\n" + ex.getMessage());
		}            
	}


    /**
    ���׽�Ʈ �̸����� 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestPaperPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
		  	request.setAttribute("requestbox", box);            
		  	String v_return_url = "/learn/admin/etest/za_ETestPreview.jsp";
                     
			ETestPaperBean bean = new ETestPaperBean();
			ArrayList list1 = bean.SelectPaperQuestionExampleList(box); 
			request.setAttribute("PaperQuestionExampleList", list1);

			DataBox dbox1 = bean.selectETestPaperData(box);               
			request.setAttribute("ETestPaperData", dbox1);
        
		  	ServletContext sc = getServletContext();
		  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  	rd.forward(request, response);
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestPaperPreviewPage()\r\n" + ex.getMessage());
        }
    }

}