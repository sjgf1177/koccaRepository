//**********************************************************
//  1. ��      ��: �������� �����ϴ� ����
//  2. ���α׷��� : ��������Servlet.java
//  3. ��      ��: �������� ���� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2004.1.26
//  7. ��     ��1:
//**********************************************************
package controller.infomation;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.OpenBean;
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
@WebServlet("/servlet/controller.infomation.OpenServlet")
public class OpenServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        //MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        //int fileupstatus = 0;
        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canModify = false;
        boolean v_canDelete = false;
        boolean v_canReply  = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            
            box = RequestManager.getBox(request);
            
            //String path = request.getRequestURI();
           // box = BulletinManager.getState(path.substring(path.lastIndexOf(".")+1, path.lastIndexOf("Servlet")), box, out);
       

            v_process = box.getString("p_process");
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			// �α� check ��ƾ VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply = BulletinManager.isAuthority(box,box.getString("p_canReply"));

            if(v_process.equals("courseList")){                      // ���簭�� ��� ����
                if(v_canRead) this.performCourseList(request, response, box, out);
            } else if(v_process.equals("coursePage")) {              // ���簭�� ��
                if(v_canAppend) this.performCoursePage(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("replyList")) {               // ���簭�� �ǰ� ���/�Է�
                if(v_canReply) this.performReplyList(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("reply")) {                   // ���簭�� �ǰ� ����
                if(v_canReply) this.performReply(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("replyDelete")) {             // ���簭�� �ǰ� ����
                if(v_canModify) this.performReplyDelete(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("practicalList")) {           // �ǹ����� ��� ���
                if(v_canModify) this.performPracticalList(request, response, box, out);
                else this.errorPage(box, out);
            } else if(v_process.equals("practicalPage")) {           // �ǹ����� ��
                if(v_canDelete) this.performPracticalPage(request, response, box, out);
                else this.errorPage(box, out);
            } else {                                                 // ���簭�� ��� ����
            	if(v_canRead) this.performCourseList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

   

	/**
          ���簭�� ��� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performCourseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            OpenBean bean = new OpenBean();

            ArrayList list = bean.selectCourseList(box);
            request.setAttribute("selecCommentList", list);
      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/common/information/zu_OpenCourse_L.jsp");
            rd.forward(request, response);
            Log.info.println(this, box, "Dispatch to /learn/user/common/information/zu_OpenCourse_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

     /**
    �������� ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCoursePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/helpdesk/gu_HomePageQna_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/helpdesk/gu_HomePageQna_I.jsp");

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
	public void performReplyList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OpenBean bean = new OpenBean();
			
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
    Qna ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performReplyDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            OpenBean bean = new OpenBean();
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/helpdesk/gu_HomePageQna_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/helpdesk/gu_HomePageQna_U.jsp");

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
	public void performPracticalList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            OpenBean bean = new OpenBean();
			
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
	public void performPracticalPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           OpenBean bean = new OpenBean();

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
	@SuppressWarnings("unchecked")
	public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           OpenBean bean = new OpenBean();
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
}

