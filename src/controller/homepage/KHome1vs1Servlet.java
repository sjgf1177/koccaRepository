//**********************************************************
//1. 제      목: 1:1상담
//2. 프로그램명 : KHome1vs1Servlet.java
//3. 개      요: 1:1상담
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

import com.credu.homepage.KHome1vs1Bean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.KHome1vs1Servlet")
public class KHome1vs1Servlet extends javax.servlet.http.HttpServlet {

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
        v_process = box.getString("p_process");

        if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

        // 로긴 check 루틴 VER 0.2 - 2003.09.9
        if (!AdminUtil.getInstance().checkLogin(out, box)) {
         return; 
        }
        box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

        /////////////////////////////////////////////////////////////////////////////
        if(v_process.equals("movePage")) {                   //   1:1 페이지로 이동할때
			this.performPage(request, response, box, out);
        } else if(v_process.equals("insertData")) {          //  저장
			this.performInsertData(request, response, box, out);
        } 
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
    }
}


/**
신고 페이지로 이동할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try {
        request.setAttribute("requestbox", box);  

        //사이트 회원전체중 로그인한회원정보
		KHome1vs1Bean beanAllMem = new KHome1vs1Bean();
        ArrayList listbeanAllMem = beanAllMem.selectTz_Member(box); 
        request.setAttribute("listAllUser", listbeanAllMem);


        ServletContext    sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_1vs1.jsp");
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_1vs1.jsp");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performmoFrSingoPage()\r\n" + ex.getMessage());
    }
}

 /**
신고처리를할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/

public void performInsertData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
        KHome1vs1Bean bean = new KHome1vs1Bean();

        int isOk = bean.insert(box);

        String v_msg = "";
        String v_url = "/servlet/controller.homepage.MainServlet";

        AlertManager alert = new AlertManager();

        if(isOk > 0) {
            v_msg = "insert.ok";
            alert.alertOkMessage(out, v_msg, v_url , box);
        }
        else {
            v_msg = "insert.fail";
            alert.alertFailMessage(out, v_msg);
        }

        Log.info.println(this, box, v_msg + " on CommunityFrJoinServlet");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertFrPoliceData()\r\n" + ex.getMessage());
    }
}


}

