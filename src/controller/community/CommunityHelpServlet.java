//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ Q&A �����ϴ� ����
//  2. ���α׷��� : HomePageBoardServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� Q&A �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1 
//  7. ��      ��: 2005.7.1 
//**********************************************************

package controller.community;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.credu.community.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class CommunityHelpServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter      out          = null;
        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            String path = request.getServletPath();

						out       = response.getWriter();
            box       = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process","selectlist");
			
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("pop_guide")) {          	//  Ŀ�´�Ƽ ���� ����
					   this.performListPage(request, response, box, out);
            } else if(v_process.equals("pop_pr")) {			// 
            	this.performPrPage(request, response, box, out);
            } else if(v_process.equals("pop_make")) {			// 
            	this.performMakePage(request, response, box, out);
          	} else if(v_process.equals("pop_use")) {			// 
          		this.performUsePage(request, response, box, out);
          	}
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    ����Ʈ�������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/pop_guide.htm");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    Ŀ�´�Ƽ ȫ�� ���� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPrPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/pop_guide_pr.htm");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    Ŀ�´�Ƽ ����� ���� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMakePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/pop_guide_make.htm");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    Ŀ�´�Ƽ ��� ���� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUsePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/pop_guide_use.htm");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
}

