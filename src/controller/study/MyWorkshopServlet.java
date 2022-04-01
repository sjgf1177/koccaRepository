//**********************************************************
//1. 제      목: 나의 워크샵
//2. 프로그램명: MyWorkshopServlet.java
//3. 개      요:
//4. 환      경: JDK 1.5
//5. 버      젼: 1.0
//6. 작      성: 2009.11.27
//7. 수      정:
//
//**********************************************************

package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.QnaAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.MyWorkshopBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.study.MyWorkshopServlet")
public class MyWorkshopServlet extends HttpServlet implements Serializable {
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
            
            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }            

            /*
            if(box.getSession("userid").equals("")){
            	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }
            */

            if (v_process.equals("MyWorkshopListPage")) {                            // 나의 워크샵 리스트
                this.performMyWorkshopListPage(request, response, box, out);
            } else if (v_process.equals("MyWorkshopViewPage")) {                      // 나의 워크샵  신청 결과 상세보기
                this.performMyWorkshopViewPage(request, response, box, out);
            }                  

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    나의 워크샵 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performMyWorkshopListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_url = "/learn/user/portal/study/gu_MyWorkshop_L.jsp";
            
            MyWorkshopBean bean = new MyWorkshopBean();
            ArrayList list1 = bean.SelectMyWorkshopList(box);                        
            request.setAttribute("MyWorkshopListPage", list1);

            ServletContext sc    = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
          Log.info.println(this, box, v_url);            
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyWorkshopListPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    나의 워크샵 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMyWorkshopViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            MyWorkshopBean bean = new MyWorkshopBean();
            DataBox dbox = bean.selectMyWorkshop(box);
            
            request.setAttribute("MyWorkshopViewPage", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/gu_MyWorkshopResult_P.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    } 
    
}