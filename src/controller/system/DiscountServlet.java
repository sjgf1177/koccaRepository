//**********************************************************
//1. 제      목: 할인률 제어하는 서블릿
//2. 프로그램명 : DiscountServlet.java
//3. 개      요: 할인률 제어하는 서블릿
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 
//7. 수      정:
//**********************************************************

package controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.DiscountBean;

@WebServlet("/servlet/controller.system.DiscountServlet")
public class DiscountServlet extends javax.servlet.http.HttpServlet {

/**
* DoGet
* Pass get requests through to PerformTask
*/
public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    this.doPost(request, response);
}
public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    PrintWriter out = null;
    RequestBox box = null;
    String v_process = "";

    try {
        response.setContentType("text/html;charset=euc-kr");
        out = response.getWriter();
        box = RequestManager.getBox(request);
        v_process = box.getString("p_process");

		box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
		///////////////////////////////////////////////////////////////////

        if(v_process.equals("selectList")) {          //  리스트
//          this.performSelectPage(request, response, box, out);
            this.performSelect(request, response, box, out);
        }
        else if(v_process.equals("insert")) {         //  내용보기
            this.performInsert(request, response, box, out);
        }
        else if(v_process.equals("select")) {         //  내용보기
            this.performSelect(request, response, box, out);
        }


    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
    }
}

/** 할인률페이지로 
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
        request.setAttribute("requestbox", box);
		String v_url = "/learn/admin/system/za_Discount.jsp";

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Discount.jsp");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
    }
}

/**
할인률 내용보기
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
        request.setAttribute("requestbox", box);
		String v_url = "/learn/admin/system/za_Discount.jsp";
		
		DiscountBean bean = new DiscountBean(); 
		ArrayList list = bean.searchDiscount(box);
		
		request.setAttribute("DiscountList", list);

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Discount.jsp");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelect()\r\n" + ex.getMessage());
    }
}
/**
할인률 수정
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
        request.setAttribute("requestbox", box);
        String v_url  = "/servlet/controller.system.DiscountServlet";

		DiscountBean bean = new DiscountBean(); 

        int isOk = bean.update(box);	
		box.put("p_process","select");

        String v_msg = "";

        AlertManager alert = new AlertManager();
        if(isOk > 0) {
            v_msg = "save.ok";
            alert.alertOkMessage(out, v_msg, v_url , box);
        }else {
            v_msg = "save.fail";
            alert.alertFailMessage(out, v_msg);
        }

        Log.info.println(this, box, v_msg + " on performInsert");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsert()\r\n" + ex.getMessage());
    }

}
}

