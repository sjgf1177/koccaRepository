//**********************************************************
//  1. ��      ��: �����ȣ �˻� �����ϴ� ����
//  2. ���α׷��� : PostSearchServlet.java
//  3. ��      ��: �����ȣ �˻� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 7
//  7. ��      ��:
//**********************************************************

package controller.library;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.PostSearchBean;
import com.credu.library.ErrorManager;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.library.PostSearchServlet")
public class PostSearchServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
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

            if(v_process.equals("SearchPostOpenPage")){        //in case of search postcode open page
                    this.performSearchPostOpenPage(request, response, box, out);
            }            
            else if(v_process.equals("SearchPostAtOpenWin")){       //in case of search postcode at openwin
                    this.performSearchPostAtOpenWin(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }



    /**
    SEARCH POSTCODE OPEN PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSearchPostOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {
        try {
            request.setAttribute("requestbox", box); 
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchPost.jsp"); 
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SearchPostOpenPage()\r\n" + ex.getMessage());
        }
    }
        
    /**
    SEARCH POSTCODE AT OPENWIN
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSearchPostAtOpenWin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    	throws Exception {
        try {
            request.setAttribute("requestbox", box);
            PostSearchBean bean = new PostSearchBean();
            ArrayList list = bean.selectPostcodeList(box);
            
            request.setAttribute("SearchPost", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/searchPost.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SearchPost()\r\n" + ex.getMessage());
        }
    }

}