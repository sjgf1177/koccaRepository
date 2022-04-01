//**********************************************************
//1. ��      ��: E-Test ���� �߼� ����
//2. ���α׷���: ETestMailServlet .java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2004-11-05
//7. ��      ��:
//
//**********************************************************

package controller.etest;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestMailBean;
import com.credu.etest.ETestMemberBean;
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
@WebServlet("/servlet/controller.etest.ETestMailServlet")
public class ETestMailServlet extends HttpServlet implements Serializable {
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
            v_process = box.getStringDefault("p_process", "ETestMailListPage");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ETestMailServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ETestMailListPage")) {                    //E-Test ���Ϲ߼� ����Ʈ
                this.performETestMailListPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailSendPage")) {             // E-Test ���� �Է� ������
                this.performETestMailSendPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailPreviewPage")) {              // E-Test ���� �̸����� ������
                this.performETestMailPreviewPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailResultPage")) {                 // E-Test ���� �亯 ����
                this.performETestMailResultPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailEncoSendPage")) {                 // E-Test �������� �Է� ������
                this.performETestMailEncoSendPage(request, response, box, out);
            }
            else if (v_process.equals("ETestMailEncoPreviewPage")) {               // E-Test �������� �̸����� ������
                this.performETestMailEncoPreviewPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailSend")) {                    // E-Test ���� ������ 
                this.performETestMailSend(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailEncoSend")) {                   // E-Test �������� ������
                this.performETestMailEncoSend(request, response, box, out);
            }   

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    E-Test ���Ϲ߼� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMailListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    E-Test ���� �Է� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMailSendPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestMail_I.jsp";

            ETestMemberBean bean = new ETestMemberBean();
            
            DataBox dbox = bean.selectETestMasterData(box);
            request.setAttribute("ETestMasterData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailSendPage()\r\n" + ex.getMessage());
        }
    }

    /**
    E-Test ���� �̸����� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMailPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
    		request.setAttribute("requestbox", box);            
		    String v_return_url = "/learn/admin/etest/za_ETestMailPreview.jsp";
                        
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
    E-Test ���� ������ 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMailSend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            String v_url  = "/servlet/controller.etest.ETestMailServlet";

            ETestMailBean bean = new ETestMailBean();
            boolean isOk1 = bean.insertMailSend(box);

            AlertManager alert = new AlertManager();
            String v_msg = "";

            if(isOk1) {
                v_msg = "������ �߼۵Ǿ����ϴ�.";
            }
            else {
                v_msg = "���Ϲ߼ۿ� �����߽��ϴ�.";
            }
            alert.selfClose(out,v_msg);
     	 }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailSend()\r\n" + ex.getMessage());
        }
    }

    /**
    E-Test ���� �亯 ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMailResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{

		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailResultPage()\r\n" + ex.getMessage());
        }
    }


    /**
    E-Test �������� �Է� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
   public void performETestMailEncoSendPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestMail_I.jsp";

            ETestMemberBean bean = new ETestMemberBean();
            
            DataBox dbox = bean.selectETestMasterData(box);
            request.setAttribute("ETestMasterData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailEncoSendPage()\r\n" + ex.getMessage());
        }
    }

    /**
    E-Test �������� �̸�����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMailEncoPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
    		request.setAttribute("requestbox", box);            
		    String v_return_url = "/learn/admin/etest/za_ETestMailPreview.jsp";
                        
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailEncoPreviewPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    E-Test �������� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMailEncoSend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            String v_url  = "/servlet/controller.etest.ETestMailServlet";

            ETestMailBean bean = new ETestMailBean();
            boolean isOk1 = bean.insertMailSend(box);

            AlertManager alert = new AlertManager();
            String v_msg = "";

            if(isOk1) {
                v_msg = "������ �߼۵Ǿ����ϴ�.";
            }
            else {
                v_msg = "���Ϲ߼ۿ� �����߽��ϴ�.";
            }
            alert.selfClose(out,v_msg);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailEncoSend()\r\n" + ex.getMessage());
        }
    }
}