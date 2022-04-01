//**********************************************************
//1. 제      목: E-Test 메일 발송 관리
//2. 프로그램명: ETestMailServlet .java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2004-11-05
//7. 수      정:
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

            if (v_process.equals("ETestMailListPage")) {                    //E-Test 메일발송 리스트
                this.performETestMailListPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailSendPage")) {             // E-Test 메일 입력 페이지
                this.performETestMailSendPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailPreviewPage")) {              // E-Test 메일 미리보기 페이지
                this.performETestMailPreviewPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailResultPage")) {                 // E-Test 개인 답변 보기
                this.performETestMailResultPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailEncoSendPage")) {                 // E-Test 독려메일 입력 페이지
                this.performETestMailEncoSendPage(request, response, box, out);
            }
            else if (v_process.equals("ETestMailEncoPreviewPage")) {               // E-Test 독려메일 미리보기 페이지
                this.performETestMailEncoPreviewPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailSend")) {                    // E-Test 메일 보내기 
                this.performETestMailSend(request, response, box, out);
            } 
            else if (v_process.equals("ETestMailEncoSend")) {                   // E-Test 독려메일 보내기
                this.performETestMailEncoSend(request, response, box, out);
            }   

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    E-Test 메일발송 리스트
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
    E-Test 메일 입력 페이지
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
    E-Test 메일 미리보기 페이지
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
    E-Test 메일 보내기 
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
                v_msg = "메일이 발송되었습니다.";
            }
            else {
                v_msg = "메일발송에 실패했습니다.";
            }
            alert.selfClose(out,v_msg);
     	 }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailSend()\r\n" + ex.getMessage());
        }
    }

    /**
    E-Test 개인 답변 보기
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
    E-Test 독려메일 입력 페이지
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
    E-Test 독려메일 미리보기
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
    E-Test 독려메일 보내기
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
                v_msg = "메일이 발송되었습니다.";
            }
            else {
                v_msg = "메일발송에 실패했습니다.";
            }
            alert.selfClose(out,v_msg);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMailEncoSend()\r\n" + ex.getMessage());
        }
    }
}