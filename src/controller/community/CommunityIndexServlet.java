//**********************************************************
//  1. 제      목: 커뮤니티 메인화면을 제어하는 서블릿
//  2. 프로그램명 : HomePageBoardServlet.java
//  3. 개      요: 커뮤니티의 Q&A 페이지을 제어한다
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityFrBoardBean;
import com.credu.community.CommunityFrJoinBean;
import com.credu.community.CommunityFrPollBean;
import com.credu.community.CommunityIndexBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.community.CommunityIndexServlet")
public class CommunityIndexServlet extends javax.servlet.http.HttpServlet {

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
//        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
//        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
//            String path = request.getServletPath();

            out       = response.getWriter();
                     box       = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","selectmain");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
        	if (!v_process.equals("selectmain") && !AdminUtil.getInstance().checkLoginPopup(out, box)) {
        		return; 
        	}
            
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("selectmain")) {                    //  메인 페이지로 이동할때
              this.performMainIndexPage(request, response, box, out);
            } else if(v_process.equals("selectmyindex")) {          //  서브메인 페이지로 이동할때
              this.performMyIndexPage(request, response, box, out);
            } else if(v_process.equals("movememout")) {             //  회원탈퇴 페이지로 이동할때
              this.performMemOutPage(request, response, box, out);

            } else if(v_process.equals("updatememout")) {           //  회원탈퇴
              this.performUpdateMemOutData(request, response, box, out);

            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    메인인덱스 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performMainIndexPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityIndexBean bean = new CommunityIndexBean();
            ArrayList list = bean.selectMainIndex(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuMainIndex.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuMainIndex.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


    /**
    회원탈퇴 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performMemOutPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {

            request.setAttribute("requestbox", box);  
            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrOut_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrOut_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    커뮤니서 서브메인 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performMyIndexPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            
            //box.setSession("s_tabMenu", "");
            //box.put("tabMenu", "");
            if(!box.getString("actionflag").equals("changeTab")){
	            box.setSession("s_tabMenu", "");
	            box.put("tabMenu", "");
	            box.setSession("s_menuno", "");
	            box.put("menuno", "");
	            box.setSession("s_masterno", "");
	            box.put("p_masterno", "");
            }
            
            CommunityFrJoinBean beandte = new CommunityFrJoinBean();
            beandte.updateFrRecentDte(box);

            //공지사항
            CommunityFrBoardBean bean5 = new CommunityFrBoardBean();
            ArrayList list5 = bean5.selectRoomIndexListBrd(box,"0"); 
            request.setAttribute("listDirectBrd", list5);

            //게시글
            ArrayList list6 = bean5.selectRoomIndexListBrd(box,"1"); 
            request.setAttribute("listBrd", list6);


            System.out.println("#########  v_pollno :"+box.getString("p_pollno"));
            CommunityFrPollBean bean7 = new CommunityFrPollBean();
            ArrayList list7 = bean7.selectPollIndexView(box); 
            request.setAttribute("listPoll", list7);

            CommunityFrPollBean bean8 = new CommunityFrPollBean();
            int v_icnt = bean8.selectPollTicketCnt(box); 
            request.setAttribute("userpollcnt", String.valueOf(v_icnt)); 


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MyIndex.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MyIndex.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


     /**
    커뮤니티 회원탈퇴
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
	public void performUpdateMemOutData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityIndexBean bean = new CommunityIndexBean();
            int isOk = bean.updateCmuUserCloseFg(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityIndexServlet";
            box.put("p_process", "selectmain");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                //alert.alertOkMessage(out, v_msg, v_url , box);
                out.println("<script language=\"javascript\">");
                out.println("alert(\"회원탈퇴 되었습니다.\r\n팝업창이 닫힙니다.\");");
                out.println("self.close();");
                out.println("</script>");
            }
            else {

                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityCloseData()\r\n" + ex.getMessage());
        }
    }
    

}

