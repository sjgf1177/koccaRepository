//**********************************************************
//  1. 제      목
//  2. 프로그램명: MenuDataServlet.java
//  3. 개      요 :
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: LeeSuMin 2003. 7. 14
//  7. 수      정: 
//                 
//**********************************************************
package controller.system;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.MenuBean;

@WebServlet("/servlet/controller.system.MenuDataServlet")
public class MenuDataServlet extends javax.servlet.http.HttpServlet implements Serializable {
    
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
            
            box.setSession("s_gubun", "0");    // 대메뉴 초기화 (대메뉴 이동시 구분값은 param 값으로 처리)
            
            
    	    if (v_process.equals("mScreenApplet")) {      			//in case of 운영본부 애플릿 출력 화면
				this.performmScreenAppletPage(request, response, box, out);
            }
    	    
    	    if(v_process.equals("mainForward")) {   	    	
    	    	
    	    	String v_url = box.getString("url");    	    
    	    	ServletContext sc = getServletContext();

                RequestDispatcher rd = sc.getRequestDispatcher(v_url);

                //rd.include(request, response);
                rd.forward(request, response);
    	    } 

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
         
    /**
    운영본부 애플릿 출력화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performmScreenAppletPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/system/mScreenFmenu.jsp";
                        
            MenuBean bean = new MenuBean();
            ArrayList list1 = bean.SelectList(box);
            request.setAttribute("MenuDataList", list1);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

}
