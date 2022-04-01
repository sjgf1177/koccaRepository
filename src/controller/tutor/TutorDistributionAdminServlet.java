//*********************************************************
//  1. ��      ��: Ʃ�� ��������
//  2. ���α׷���: TutorDistributionAdminServlet.java
//  3. ��      ��: Ʃ�� �� ���� ������ servlet
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 
//  7. ��      ��:
//**********************************************************
package controller.tutor;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.tutor.*;
import com.credu.library.*;
import com.credu.system.*;

@WebServlet("/servlet/controller.tutor.TutorDistributionAdminServlet")
public class TutorDistributionAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {    
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
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;
  
        try {
            response.setContentType("text/html;charset=euc-kr");            
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
			//System.out.println("v_process : " + v_process);
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("TutorDistributionAdminServlet", v_process, out, box)) {
				return; 
			}

			///////////////////////////////////////////////////////////////////
			if(v_process.equals("listPage")){                  // Ʃ���򰡰��� ����Ʈ
                    this.performListPage(request, response, box, out);
            }else if(v_process.equals("updatePage")){                  // Ʃ���򰡰��� ����������
                    this.performUpdatePage(request, response, box, out);
            }else if(v_process.equals("update")){                  // Ʃ���򰡰��� ����
                    this.performUpdate(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    Ʃ���򰡰��� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            
            TutorAdminBean bean = new TutorAdminBean();
            DataBox dbox = (DataBox)bean.getMeasure(box);
            
            request.setAttribute("measure",dbox);
            

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorDistribution_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    Ʃ���򰡰��� ����������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            
            TutorAdminBean bean = new TutorAdminBean();
            DataBox dbox = (DataBox)bean.getMeasure(box);
            
            request.setAttribute("measure",dbox);
            

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorDistribution_U.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }
    /**
    Ʃ���򰡰��� ����������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try{
            request.setAttribute("requestbox", box); 
            
            TutorAdminBean bean = new TutorAdminBean();
            int isOk = bean.updateMeasure(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorDistributionAdminServlet";
            box.put("p_process","listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
            	v_msg = "update.ok";
            	alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }    
}