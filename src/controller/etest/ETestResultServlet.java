//**********************************************************
//1. ��      ��: �� ����ڰ���
//2. ���α׷���: ETestResultServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 0.1
//6. ��      ��: 
//**********************************************************

package controller.etest;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestMemberBean;
import com.credu.etest.ETestResultBean;
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

@WebServlet("/servlet/controller.etest.ETestResultServlet")
public class ETestResultServlet extends HttpServlet implements Serializable {
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
			System.out.println("E-Test  ETestResultServlet : "+v_process);			
			
            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }
			if (!v_process.equals("ETestGubunResult"))
			{
	            if (!AdminUtil.getInstance().checkRWRight("ETestResultServlet", v_process, out, box)) {
					return;
	            }
			}

            if (v_process.equals("ETestResultListPage")) {                    //�� ����� ����Ʈ
                this.performETestResultListPage(request, response, box, out);
            } 
            else if (v_process.equals("ETestUserPaperResult")) {              // �� ������ ���κ� ��� �������� �̵� (�˻���)
                this.performETestUserPaperResult(request, response, box, out);
            } 
            else if (v_process.equals("ETestUserResultHistory")) {              // ���׽�Ʈ ��� TEXT���� DB �Է�
                this.performETestUserResultHistory(request, response, box, out);
            } 
			else if (v_process.equals("ETestGubunResult")) {                 //�з��� ��� ����
                this.performETestGubunResult(request, response, box, out);
            } 
			else if (v_process.equals("ETestResult")) {                 //����м�
                this.performETestResult(request, response, box, out);                
            }      
            else if(v_process.equals("Reporting")) {   
                this.performReporting(request, response, box, out);
            }                      
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    ����м�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestResult_L1.jsp";
            
            //ETestResultBean bean = new ETestResultBean();
      
			//ArrayList list1 = bean.EtestResultList(box); 
			//request.setAttribute("ETestResult", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestResult()\r\n" + ex.getMessage());
        }
    }
    
    
    /**
    ���׽�Ʈ  ��� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestResultListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestResult_L2.jsp";
            
            ETestResultBean bean = new ETestResultBean();
      
			ArrayList list1 = bean.SelectReaultList(box); 
			request.setAttribute("ETestResultList", list1);

			Vector v1 = bean.SelectResultAverage(box);               
			request.setAttribute("ETestResultAverage", v1);  // ������ ���

            ETestMemberBean bean1 = new ETestMemberBean();
            DataBox dbox = bean1.selectETestMasterData(box);
            request.setAttribute("ETestMasterData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestResultListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    ���׽�Ʈ ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
/*            request.setAttribute("requestbox", box);            
            String v_return_url = "/jsp/admin/etest/za_ETestIndividualPaperResult_L.jsp";
            
            ETestUserBean bean = new ETestUserBean();
            ArrayList list = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
  */      }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserPaperResult()\r\n" + ex.getMessage());
        }
    }


    /**
    ��ȸ�� e-test��� �ݿ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestUserResultHistory(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            String v_url  = "/servlet/controller.etest.ETestResultServlet";

            ETestResultBean bean = new ETestResultBean();
            int isOk = bean.insertETestResult(box);

			String v_msg = "";
            box.put("p_process", "ETestResultListPage");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
              v_msg = "insert.ok";
              alert.alertOkMessage(out, v_msg, v_url , box);
            }else if(isOk == 0){
             v_msg = "";
             //alert.alertFailMessage(out, v_msg, v_url , box);
             alert.alertOkMessage(out, v_msg, v_url , box);
            /* 
	             ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	            rd.forward(request, response);
	          */ 
            }else {
              v_msg = "insert.fail";
             alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserResultHistory()\r\n" + ex.getMessage());
        }
    }

    /**
    �з��� �������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestGubunResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestGubunResult_L.jsp";

            //ETestUserBean bean1 = new ETestUserBean();
            //ArrayList list2 = bean1.SelectUserPaperResult2(box);
            //request.setAttribute("UserPaperResult2", list2);  // Ÿ��Ʋ 

            ETestResultBean bean = new ETestResultBean();
            
            ArrayList list = bean.SelectGubunCodenm();
            request.setAttribute("GubunCodenm", list);    // �з��ڵ�
            
			//System.out.println("userid = "+ box.getSession("userid"));
            Vector vlist = bean.SelectGubunResult(box);
            request.setAttribute("GubunResult", vlist);   // ���
     
		  	ServletContext sc = getServletContext();
		  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  	rd.forward(request, response);
			
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestGubunResult()\r\n" + ex.getMessage());
        }
    }     


    /**
    ���׽�Ʈ ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */    
    public void performReporting(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/etest/za_ETestResult_L1.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReporting()\r\n" + ex.getMessage());
        }
    }       
}