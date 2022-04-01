//*********************************************************
//1. 제      목: 매출현황을 엑셀 출력하는 서블릿
//2. 프로그램명: StatisticsServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성:
//7. 수      정:
//**********************************************************
package controller.statistics;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.statistics.SubjYnAdminBean;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.statistics.SubjYnAdminServlet")
public class SubjYnAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
@SuppressWarnings("unchecked")
public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    PrintWriter out = null;
    RequestBox box = null;
    String v_process = "";


    try {
        response.setContentType("text/html;charset=euc-kr");
        out = response.getWriter();
        box = RequestManager.getBox(request);
        v_process = box.getString("p_process");

        if(ErrorManager.isErrorMessageView()) {
            box.put("errorout", out);
        }

        // 권한 check 루틴 VER 0.2 - 2003.08.10
        if (!AdminUtil.getInstance().checkRWRight("SubjYnAdminServlet", v_process, out, box)) {
            return;
        }

        // int admin_check = 0;
        if(v_process.equals("SubjYnExe")){
       this.performSubjYnExePage(request, response, box, out);
        } else if(v_process.equals("ExcelDown")){
       this.performSubjYnExcel(request, response, box, out);
        }

        }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
    }
}

/**
STATISTICS SUBJECT PAGE
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
@SuppressWarnings("unchecked")
public void performSubjYnExePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try{
        request.setAttribute("requestbox", box);
        SubjYnAdminBean bean = new SubjYnAdminBean();
        ArrayList list = bean.selectSubjYnList(box);
        request.setAttribute("SubjYnList", list);

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SubjYnSearch_L.jsp");
        rd.forward(request, response);
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSubjYnExePage()\r\n" + ex.getMessage());
    }
}

/**
STATISTICS SUBJECT EXCEL
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
@SuppressWarnings("unchecked")
public void performSubjYnExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try{
        request.setAttribute("requestbox", box);
        SubjYnAdminBean bean = new SubjYnAdminBean();
        ArrayList list = bean.selectSubjYnList(box);
        request.setAttribute("SubjYnList", list);

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SubjYnSearch_E.jsp");
        rd.forward(request, response);
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSubjYnExePage()\r\n" + ex.getMessage());
    }
}

}
