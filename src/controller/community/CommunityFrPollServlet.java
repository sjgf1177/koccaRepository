//**********************************************************
//  1. 제      목: 커뮤니티 회원가입을 제어하는 서블릿
//  2. 프로그램명 : CommunityFrPollServlet.java
//  3. 개      요: 커뮤니티의 회원가입 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.7.1 
//  7. 수      정: 이나연 05.12.02 _ 사용않는 서블릿 제거
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityFrBoardBean;
import com.credu.community.CommunityFrPollBean;
import com.credu.community.CommunityIndexBean;
import com.credu.community.CommunityMsMangeBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
public class CommunityFrPollServlet extends javax.servlet.http.HttpServlet {

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
            v_process = box.getStringDefault("p_process","selectmsmainPage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("movelistPage")) {                              //  리스트페이지로이동할때
              this.performFrListPage(request, response, box, out);
            } else if(v_process.equals("movewirtePage")) {                      //  설문등록 페이지로 이동
              this.performFrWritePage(request, response, box, out);
            } else if(v_process.equals("moveupdatePage")) {                     //  설문수정 페이지로 이동
              this.performFrUpdatePage(request, response, box, out);
            } else if(v_process.equals("moveresultviewPage")) {                 //  설문결과 페이지로 이동
              this.performFrResultViewPage(request, response, box, out);
//            } else if(v_process.equals("movesamplePage")) {						//  예시 페이지로 이동
//              this.performFrSamplePage(request, response, box, out);
            } else if(v_process.equals("queryReply")) {                         //  답변 페이지로 이동
              this.performReplyPage(request, response, box, out);
//            } else if(v_process.equals("movesamplePage")) {						//  예시 페이지로 이동
//              this.performFrSamplePage(request, response, box, out);
            } else if(v_process.equals("insertData")) {                         //  설문등록
              this.performInsertData(request, response, box, out);
            } else if(v_process.equals("updateData")) {                         //  설문수정저장
              this.performUpdateData(request, response, box, out);
            } else if(v_process.equals("deleteData")) {                         //  설문삭제
              this.performDeleteData(request, response, box, out);
//            } else if(v_process.equals("deletefieldData")) {                    //  설문문항삭제
//              this.performDeleteFieldData(request, response, box, out);
            } else if(v_process.equals("replyData")) {							//  답변등록
              this.performReplyData(request, response, box, out);
            } else if(v_process.equals("replyIndexData")) {                     // 커뮤니티 홈에서 답변등록
              this.performReplyIndexData(request, response, box, out);
            } else if(v_process.equals("insertMemoData")) {           //  댓글등록 
              this.performInsertMemoData(request, response, box, out);
            } else if(v_process.equals("deleteMemoData")) {           //  댓글삭제
              this.performDeleteMemoData(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    /**
    댓글등록 할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
	public void performInsertMemoData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	CommunityFrPollBean bean = new CommunityFrPollBean();

            int isOk = bean.insertBrdMemo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPollServlet";
            String v_return_process = box.getString("p_return_process");
            box.put("p_process", v_return_process);

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }


    /**
    댓글 삭제할 때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
	public void performDeleteMemoData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	CommunityFrPollBean bean = new CommunityFrPollBean();

            int isOk = bean.deleteBrdMemo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPollServlet";
            String v_return_process = box.getString("p_return_process");
            box.put("p_process", v_return_process);

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }


    /**
    리스트페이지로이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performFrListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            CommunityFrPollBean bean = new CommunityFrPollBean();
            ArrayList list = bean.selectPollList(box); 
            request.setAttribute("list", list);

            request.setAttribute("userpollcnt", String.valueOf(bean.selectPollTicketCnt(box))); 

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPoll_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPoll_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFrListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    설문결과페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performFrResultViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            CommunityFrPollBean bean = new CommunityFrPollBean();
            ArrayList list = bean.selectPollView(box); 
            request.setAttribute("list", list);
            
            ArrayList replyList = bean.selectPollReplyList(box); 
            request.setAttribute("replyList", replyList);
            request.setAttribute("userpollcnt", String.valueOf(bean.selectPollTicketCnt(box))); 

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPoll_I3.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPoll_I3.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFrListPage()\r\n" + ex.getMessage());
        }
    }



    /**
    설문등록페이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performFrWritePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            CommunityFrPollBean bean = new CommunityFrPollBean();
            ArrayList list = bean.selectPollMst(box); 
            request.setAttribute("list", list);

            request.setAttribute("userpollcnt", String.valueOf(bean.selectPollTicketCnt(box))); 

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPoll_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPoll_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFrListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    설페수정문이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performFrUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            CommunityFrPollBean bean = new CommunityFrPollBean();
            ArrayList list = bean.selectPollView(box); 
            request.setAttribute("list", list);

            request.setAttribute("userpollcnt", String.valueOf(bean.selectPollTicketCnt(box))); 

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPoll_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPoll_U.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFrUpdatePage()\r\n" + ex.getMessage());
        }
    }


    /**
    답변페이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            CommunityFrPollBean bean = new CommunityFrPollBean();
            ArrayList list = bean.selectPollView(box); 
            request.setAttribute("list", list);
            
            ArrayList replyList = bean.selectPollReplyList(box); 
            request.setAttribute("replyList", replyList);

            request.setAttribute("userpollcnt", String.valueOf(bean.selectPollTicketCnt(box))); 

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPoll_I1.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPoll_I1.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyPage()\r\n" + ex.getMessage());
        }
    }
    /**
    예시피에지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performFrSamplePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPoll_P.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPoll_P.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFrListPage()\r\n" + ex.getMessage());
        }
    }

     /**
    설문등록할때 할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performInsertData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrPollBean bean = new CommunityFrPollBean();

            int isOk = bean.insertFrPoll(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPollServlet";
            box.put("p_process", "movelistPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrPollServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }

     /**
    설문수정할때 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrPollBean bean = new CommunityFrPollBean();

            int isOk = bean.insertFrPoll(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPollServlet";
            box.put("p_process", "movelistPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            //Log.info.println(this, box, v_msg + " on CommunityFrPollServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }


     /**
    설문문항삭제할때 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performDeleteFieldData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrPollBean bean = new CommunityFrPollBean();

            int isOk = bean.deleteFrPollField(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPollServlet";
            box.put("p_process", "queryReply");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrPollServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteFieldData()\r\n" + ex.getMessage());
        }
    }

     /**
    설문삭제할때 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrPollBean bean = new CommunityFrPollBean();

            //int isOk = bean.deleteFrPollField(box);
            int isOk = bean.deleteFrPoll(box);
            
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPollServlet";
            box.put("p_process", "movelistPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrPollServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteData()\r\n" + ex.getMessage());
        }
    }

     /**
    답변등록 할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performReplyData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrPollBean bean = new CommunityFrPollBean();

            int isOk = bean.replyFrPoll(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrPollServlet";
            box.put("p_process", "movelistPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "설문에 참여해 주셔서 감사합니다.";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrPollServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyData()\r\n" + ex.getMessage());
        }
    }

     /**
    커뮤니티 홈에서 답변등록 할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performReplyIndexData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrPollBean bean = new CommunityFrPollBean();

            int isOk = bean.replyFrPoll(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityIndexServlet";
            box.put("p_process", "selectmyindex");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrPollServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyIndexData()\r\n" + ex.getMessage());
        }
    }
}

