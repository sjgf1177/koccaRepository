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

import com.credu.community.CommunityAdminRoomBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
public class CommunityAdminRoomServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter      out          = null;
        RequestBox       box          = null;
        String           v_process    = "";

        try {
            response.setContentType("text/html;charset=euc-kr");

            out       = response.getWriter();
            box       = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","selectmsmainPage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("listPage")) {                   //   리스트페이지로 이동할때
				this.performListPage(request, response, box, out);
            } else if(v_process.equals("acceptData")) {                   //   승인처리할때
				this.performAcceptData(request, response, box, out);
            } else if(v_process.equals("holdData")) {                   //   인기커뮤니티 저장
				this.performHoldData(request, response, box, out);
			} else if(v_process.equals("frCloseData")) {                   //   폐쇄커뮤니티
				this.performCloseData(request, response, box, out);				
            } else if(v_process.equals("sendmailPage")) {          //  메일입력페이지로 이동할때
				this.performSendMailPage(request, response, box, out);
            } else if(v_process.equals("viewPage")) {          //  상세보기 페이지로 이동할 때
	            this.performDetailPage(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
	
	/**
    리스트 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performDetailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            CommunityAdminRoomBean bean = new CommunityAdminRoomBean();
            ArrayList list = bean.selectViewBrd(box); 
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CmuRoom_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/community/za_CmuRoom_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performmoFrSingoPage()\r\n" + ex.getMessage());
        }
    }	

    /**
    메일입력페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSendMailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다



            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_MailForm_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/community/za_MailForm_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }

    /**
    리스트 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  


            CommunityAdminRoomBean bean = new CommunityAdminRoomBean();
            ArrayList list = bean.selectList(box); 
            request.setAttribute("list", list);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CmuRoom_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/community/za_CmuRoom_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performmoFrSingoPage()\r\n" + ex.getMessage());
        }
    }

    /**
    승인 및 거부 처리 할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
	public void performAcceptData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityAdminRoomBean bean = new CommunityAdminRoomBean();

            int isOk = bean.updateCommunity(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityAdminRoomServlet";
           // box.put("p_process", "sendmailPage");
			 box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                bean.sendMail(box);
                v_msg = "insert.ok";
                v_url +="?okflag=Y";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityAdminRoomServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAcceptData()\r\n" + ex.getMessage());
        }
    }

    /**
    인기커뮤니티 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
	public void performHoldData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityAdminRoomBean bean = new CommunityAdminRoomBean();

            int isOk = bean.updateHold(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityAdminRoomServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityAdminRoomServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAcceptData()\r\n" + ex.getMessage());
        }
    }
	
	 /**
    폐쇄커뮤니티 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
	public void performCloseData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityAdminRoomBean bean = new CommunityAdminRoomBean();

            int isOk = bean.updateFrClose(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityAdminRoomServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityAdminRoomServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAcceptData()\r\n" + ex.getMessage());
        }
    }

}

