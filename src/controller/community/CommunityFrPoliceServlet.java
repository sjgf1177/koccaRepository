//**********************************************************
//  1. 제      목: 커뮤니티 신고 서블릿
//  2. 프로그램명 : CommunityFrPoliceServlet.java
//  3. 개      요: 커뮤니티의 신고 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.7.1 
//  7. 수      정: 2005.7.1 
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityIndexBean;
import com.credu.community.CommunityPoliceBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

public class CommunityFrPoliceServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter      out          = null;
//        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
//        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
//            String path = request.getServletPath();

            out       = response.getWriter();
            box       = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","selectmsmainPage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("insertPage")) {                   //   신고 페이지로 이동할때
              this.performmoFrSingoPage(request, response, box, out);
            } else if(v_process.equals("insert")) {          //  신고처리
              this.performInsertFrPoliceData(request, response, box, out);

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
    public void performmoFrSingoPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPolice_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPolice_I.jsp");
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

    public void performInsertFrPoliceData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityPoliceBean bean = new CommunityPoliceBean();

            int isOk = bean.insertPolice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPoliceServlet";
            box.put("p_process", "insertPage");

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

