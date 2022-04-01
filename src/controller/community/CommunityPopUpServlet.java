//**********************************************************
//  1. 제      목: 커뮤니티 팝업 서블릿
//  2. 프로그램명 : HomePageBoardServlet.java
//  3. 개      요: 자료실의 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.7.1 이연정
//  7. 수      정: 2005.7.1 이연정
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityCategoryResultBean;
import com.credu.community.CommunityCreateBean;
import com.credu.community.CommunityIndexBean;
import com.credu.community.CommunityMsMangeBean;
import com.credu.community.CommunityMsMenuBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
public class CommunityPopUpServlet extends javax.servlet.http.HttpServlet {

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

            out    = response.getWriter();
            box    = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","dupchkPage");
            
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("dupchkPage")) {          //  커뮤니티명 중복체크페이지로 이동할때
               this.performCmuDupChkPage(request, response, box, out);
            } else if(v_process.equals("gomsnoticePop")) {          //  마스타단체알림 페이지로 이동할때
               this.performMsPopNoticePage(request, response, box, out);
            } else if(v_process.equals("gomsmemberPop")) {          //  커뮤니티 회원선택 페이지로 이동할때
               this.performMsPopMemberPage(request, response, box, out);
            } else if(v_process.equals("insertnoticeData")) {          //  회원에거 알림 메일을 보낸다.
               this.performInsertnoticeData(request, response, box, out);
            } else if(v_process.equals("gomsmembernonePop")) {          //  커뮤니티 회원선택 페이지로 이동할때
               this.performMsPopMemberNonePage(request, response, box, out);
            } else if(v_process.equals("boarddupchkPage")) {          //  커뮤니티명 중복체크페이지로 이동할때
               this.performMsBoardDupChkPage(request, response, box, out);
            } else if(v_process.equals("menumemPage")) {          //  커뮤니티 회원선택 페이지로 이동할때
               this.performMsPopMenuMemPage(request, response, box, out);
            } else if(v_process.equals("menucmufindPage")) {          //  커뮤니티 조회 페이지로 이동할때
               this.performCmuFindPage(request, response, box, out);
            } else if(v_process.equals("sendmailPage")) {          //  메일입력페이지로 이동할때
               this.performSendMailPage(request, response, box, out);
            } else if(v_process.equals("sendmailData")) {          // 메일전송
               this.performSendMailData(request, response, box, out);
            } else if(v_process.equals("hongboPop")) {          // 커뮤니티 홍보 팝업
               this.performHongboPop(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
           커뮤니티 홍보 팝업
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performHongboPop(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            CommunityIndexBean bean = new CommunityIndexBean();
            DataBox dbox = bean.selectHongbo(box);   
            
            request.setAttribute("selectHongbo", dbox);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuPopHongbo.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuPopHongbo.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }
    /**
    커뮤니티명 중복체크페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performCmuDupChkPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            CommunityCreateBean bean = new CommunityCreateBean();
            int iRecordCnt = bean.selectCmuNmRowCnt(box);   
            
            request.setAttribute("recordcnt", String.valueOf(iRecordCnt));

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuPopNameCheck.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuPopNameCheck.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }



    /**
    메뉴명 중복체크페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsBoardDupChkPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int iRecordCnt = bean.selectCmuNmRowCnt(box);   
            
            request.setAttribute("recordcnt", String.valueOf(iRecordCnt));

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMenuCheck.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMenuCheck.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }

    /**
    커뮤니티명 중복체크페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopNoticePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsPopNotice_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsPopNotice_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
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
    @SuppressWarnings("unchecked")
    public void performSendMailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다


            //사이트 회원전체중 로그인한회원정보
            CommunityIndexBean bean = new CommunityIndexBean();
            ArrayList list = bean.selectSendMailData(box); 
            request.setAttribute("list", list);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MailForm_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MailForm_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }


    /**
    커뮤니티명 마스타포함회원선택 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopMemberPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemberPop_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemberPop_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }

    /**
    커뮤니티명 마스타제외회원선택 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopMemberNonePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberNoneList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemberPop_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemberPop_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }

    /**
    회원에게 알림메일을 보낼때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performInsertnoticeData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMangeBean bean = new CommunityMsMangeBean();

            int isOk = bean.sendMsNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityPopUpServlet";
            box.put("p_process", "gomsnoticePop");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {

                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityCreateServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }


    /**
    커뮤니티명 ,메뉴변경에서 회원찾기 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopMenuMemPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberNoneList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMenuMemSearch_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMenuMemSearch_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPopMenuMemPage()\r\n" + ex.getMessage());
        }
    }


    /**
    커뮤니티명 조회페이지로이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performCmuFindPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다


            CommunityCategoryResultBean bean = new CommunityCategoryResultBean();
            ArrayList list = bean.selectCateGoryList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrCmuFindPop_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrCmuFindPop_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPopMenuMemPage()\r\n" + ex.getMessage());
        }
    }



    /**
    일반 메일전송
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performSendMailData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMangeBean bean = new CommunityMsMangeBean();

            int isOk = bean.sendMail(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityPopUpServlet";
            box.put("p_process", "sendmailPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {

                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityPopUpServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendMailData()\r\n" + ex.getMessage());
        }
    }
}

