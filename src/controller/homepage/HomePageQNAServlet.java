//**********************************************************
//  1. ��      ��: Qna �����ϴ� ����
//  2. ���α׷��� : QnaServlet.java
//  3. ��      ��: Qna ���� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2004.1.26
//  7. ��     ��1:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.KoccaTagBean;
import com.credu.homepage.HomePageQnaBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageQNAServlet")
public class HomePageQNAServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;
        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canModify = false;
        boolean v_canDelete = false;
        boolean v_canReply  = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            
            box = RequestManager.getBox(request);
            
//            String path = request.getRequestURI();
           // box = BulletinManager.getState(path.substring(path.lastIndexOf(".")+1, path.lastIndexOf("Servlet")), box, out);


            v_process = box.getString("p_process");
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			// �α� check ��ƾ VER 0.2 - 2003.09.9
            box.put("p_frmURL", request.getRequestURI().toString() +"?p_process="+ v_process);
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply = BulletinManager.isAuthority(box,box.getString("p_canReply"));

            if(v_process.equals("selectView")){                      // Qna �󼼺���� �̵��Ҷ�
                if(v_canRead) this.performSelectView(request, response, box, out);
            } 
			else if(v_process.equals("insertPage")) {              // Qna ����������� �̵��Ҷ�
                if(v_canAppend) this.performInsertPage(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("insert")) {                  // Qna ����Ҷ�
                if(v_canAppend) this.performInsert(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("replyPage")) {               // Qna �亯�������� �̵��Ҷ�
                if(v_canReply) this.performReplyPage(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("reply")) {                   // Qna �亯����Ҷ�
                if(v_canReply) this.performReply(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("updatePage")) {              // Qna ������������ �̵��Ҷ�
                if(v_canModify) this.performUpdatePage(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("update")) {                  // Qna �����Ͽ� �����Ҷ�
                if(v_canModify) this.performUpdate(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("delete")) {                  // Qna �����Ҷ�
                if(v_canDelete) this.performDelete(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("deleteComment")) {           // Qna ������ �����Ҷ�
				
                if(v_canDelete) this.performDeleteComment(request, response, box, out);
                else this.errorPage(box, out);
				
			} else if(v_process.equals("commentInsertPage")) {         // ������ ����Ҷ�
                if(v_canRead) this.performInsertcomment(request, response, box, out);
            } else {                                                 // Qna ����Ʈ
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }



    /**
    Qna �󼼺���� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	@SuppressWarnings("rawtypes")
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/helpdesk/zu_HomePageQna_R.jsp";
            	v_url = "/learn/user/2013/portal/helpdesk/zu_HomePageQna_R.jsp";
            } else {
            	v_url = "/learn/user/portal/helpdesk/zu_HomePageQna_R.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/helpdesk/zu_HomePageQna_R.jsp";
            }

            HomePageQnaBean bean = new HomePageQnaBean();
            
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);
            
            DataBox upbox = bean.selectQnaUD(box,"U");
            request.setAttribute("selectUpQna", upbox);
            
            DataBox downbox = bean.selectQnaUD(box,"D");
            request.setAttribute("selectDownQna", downbox);

            //ArrayList list = bean.selectcommentListQna(box);
            //request.setAttribute("selecCommentList", list);
            
            if(dbox.getString("d_okyn1").equals("3")){
            	ArrayList list = bean.selectAns(box);
    			request.setAttribute("selectAns", list);
            }
      
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            Log.info.println(this, box, "Dispatch to " + v_url);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

     /**
    Qna ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings({ "static-access", "rawtypes" })
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            
            
            String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/helpdesk/zu_HomePageQna_I.jsp";
            	v_url = "/learn/user/2013/portal/helpdesk/zu_HomePageQna_I.jsp";
            } else {
            	v_url = "/learn/user/portal/helpdesk/zu_HomePageQna_I.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/helpdesk/zu_HomePageQna_I.jsp";
            }
            
            KoccaTagBean bean = new KoccaTagBean();
            ArrayList categoryList = bean.getSelectBoxListByCode("0088");
            request.setAttribute("categoryList", categoryList);

            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + v_url);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
    Qna ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageQnaBean bean = new HomePageQnaBean();
			
            int isOk = bean.insertQnaQue(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageQNAServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }


    
    /**
   Qna �亯 ����Ҷ�
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   


     /**
    Qna ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings({ "static-access", "rawtypes" })
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/helpdesk/zu_HomePageQna_U.jsp";
            	v_url = "/learn/user/2013/portal/helpdesk/zu_HomePageQna_U.jsp";
            } else {
            	v_url = "/learn/user/portal/helpdesk/zu_HomePageQna_U.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/helpdesk/zu_HomePageQna_U.jsp";
            }

            HomePageQnaBean bean = new HomePageQnaBean();
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);
            
            KoccaTagBean cbean = new KoccaTagBean();
            ArrayList categoryList = cbean.getSelectBoxListByCode("0088");
            request.setAttribute("categoryList", categoryList);
            
            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + v_url);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    // Qna �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            HomePageQnaBean bean = new HomePageQnaBean();
			
            int isOk = bean.updateQna(box);
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageQNAServlet";
            box.put("p_process", "");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    // Qna �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           HomePageQnaBean bean = new HomePageQnaBean();

            int isOk = bean.deleteQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageQNAServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageQNAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    Qna ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	@SuppressWarnings("rawtypes")
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageQnaBean bean = new HomePageQnaBean();
            
            String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/helpdesk/zu_HomePageQna_L.jsp";
            	v_url = "/learn/user/2013/portal/helpdesk/zu_HomePageQna_L.jsp";
            } else {
            	v_url = "/learn/user/portal/helpdesk/zu_HomePageQna_L.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/helpdesk/zu_HomePageQna_L.jsp";
            }
            
            ArrayList list = bean.selectListQna(box);
            request.setAttribute("selectList", list);

            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+ v_url);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	public void errorPage(RequestBox box, PrintWriter out)
        throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            //  Log.sys.println();

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
	/**
    Qna �亯
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	 public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            HomePageQnaBean bean = new HomePageQnaBean();
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);

            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/helpdesk/zu_HomePageQna_A.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_HomePageQna_A.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
	@SuppressWarnings("unchecked")
	public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           HomePageQnaBean bean = new HomePageQnaBean();
           int isOk = bean.insertQnaAns(box);

           String v_msg = "";
           String v_url = "/servlet/controller.homepage.HomePageQNAServlet";
           box.put("p_process", "");

           AlertManager alert = new AlertManager();

           if(isOk > 0) {
               v_msg = "insert.ok";
               alert.alertOkMessage(out, v_msg, v_url , box);
           }
           else {
               v_msg = "insert.fail";
               alert.alertFailMessage(out, v_msg);
           }

            Log.info.println(this, box, v_msg + " on HomePageQNAServlet");
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performInsert()\r\n" + ex.getMessage());
       }
   }

    /**
    Qna ������ ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performInsertcomment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageQnaBean bean = new HomePageQnaBean();
			
            int isOk = bean.insertCommentQnaQue(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageQNAServlet";
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertcomment()\r\n" + ex.getMessage());
        }
    }



     /**
    // Qna �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performDeleteComment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           HomePageQnaBean bean = new HomePageQnaBean();

            int isOk = bean.deleteCommentQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageQNAServlet";
            box.put("p_process", "selectView");
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageQNAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteComment()\r\n" + ex.getMessage());
        }
    }

	 

}

