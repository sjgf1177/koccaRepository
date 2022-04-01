//**********************************************************
//1. 제      목: 사이트맵
//2. 프로그램명 : KHomeSiteMapServlet.java
//3. 개      요: 사이트맵 페이지을 제어한다(HOMEPAGE)
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 
//7. 수      정: 
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.templet.TempletBean;

@WebServlet("/servlet/controller.homepage.KHomeSiteMapServlet")
public class KHomeSiteMapServlet extends javax.servlet.http.HttpServlet {

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

        v_process = box.getStringDefault("p_process","listPage");

        if(ErrorManager.isErrorMessageView()) {
            box.put("errorout", out);
        }
        
        /* 로긴 check 루틴 VER 0.2 - 2003.09.9 */
        if (!AdminUtil.getInstance().checkLogin(out, box)) {
            return; 
        }
        box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

       if(v_process.equals("listPage")) {                                // 리스트
            this.performListPage(request, response, box, out);
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
public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);            

        String tem_grcode      = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        TempletBean templetbean = new TempletBean();

        ArrayList lmenu_list1 = TempletBean.getMenuList(tem_grcode, "1", "C");
        ArrayList lmenu_list2 = TempletBean.getMenuList(tem_grcode, "2", "C");
        ArrayList lmenu_list3 = TempletBean.getMenuList(tem_grcode, "3", "C");
        ArrayList lmenu_list4 = TempletBean.getMenuList(tem_grcode, "4", "C");


        request.setAttribute("lmenu_list1", lmenu_list1);
        request.setAttribute("lmenu_list2", lmenu_list2);
        request.setAttribute("lmenu_list3", lmenu_list3);
        request.setAttribute("lmenu_list4", lmenu_list4);


        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/common/ku_SiteMap.jsp");
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/common/ku_SiteMap.jsp");
    }catch (Exception ex) {            
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performListPage()\r\n" + ex.getMessage());
    }
}

}

