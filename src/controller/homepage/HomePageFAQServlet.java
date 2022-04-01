//**********************************************************
//  1. 제      목: FAQ를 제어하는 서블릿
//  2. 프로그램명 : FaqServlet.java
//  3. 개      요: FAQ의 페이지을 제어한다(HOMEPAGE)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.26
//  7. 수      정:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomePageFAQBean;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageFAQServlet")
public class HomePageFAQServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {

            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            if (box.getSession("tem_grcode") == "") {
                 box.setSession("tem_grcode","N000001");
            }

            v_process = box.getStringDefault("p_process", "selectList");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

           if(v_process.equals("selectList")) {                                // 리스트
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageFAQBean bean = new HomePageFAQBean();

            String v_grcode = box.getSession("tem_grcode");
            if(v_grcode.equals("")) {
                v_grcode = "N000001";
            }

            String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
                //v_url = "/learn/user/2012/portal/helpdesk/zu_HomePageFaq_L.jsp";
                v_url = "/learn/user/2013/portal/helpdesk/zu_HomePageFaq_L.jsp";
            } else {
                v_url = "/learn/user/portal/helpdesk/zu_HomePageFaq_L.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/helpdesk/zu_HomePageFaq_L.jsp";
            }

            box.put("p_grcode", v_grcode);

            ArrayList list = bean.selectListFaq(box);
            ArrayList cateList = bean.selectFaqCategoryList(box);

            request.setAttribute("selectList", list);
            request.setAttribute("cateList", cateList);

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_HomePageFaq_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}

