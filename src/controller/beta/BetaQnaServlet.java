/**
*��Ÿ�׽�Ʈ�ý����� Q&A�ڷ�� �����ϴ� ����
*<p>����:BetaQ&AServlet.java</p>
*<p>����:��Ÿ�׽�Ʈ�ý���Q&A�ڷ�Ǽ���</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ������
*@version 1.0
*/
package controller.beta;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BetaQnaBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.beta.BetaQnaServlet")
public class BetaQnaServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;
        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canModify = false;
        boolean v_canDelete = false;
        boolean v_canReply  = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            String path = request.getRequestURI();
            box = BulletinManager.getState(path.substring(path.lastIndexOf(".")+1, path.lastIndexOf("Servlet")), box, out);

            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }



			
			
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply = BulletinManager.isAuthority(box,box.getString("p_canReply"));

            if(v_process.equals("selectView")){                      // Qna �󼼺���� �̵��Ҷ�
                if(v_canRead) this.performSelectView(request, response, box, out);
            } else if(v_process.equals("insertPage")) {              // Qna ����������� �̵��Ҷ�
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
            } else {                                                 // Qna ����Ʈ
                if(v_canRead) this.performSelectList(request, response, box, out);
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
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            BetaQnaBean bean = new BetaQnaBean();

            
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);
      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaQna_R.jsp");
			System.out.println("/beta/user/zu_BetaQna_R.jsp forward�ϱ�");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaQna_R.jsp");

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
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaQna_I.jsp");
			System.out.println("getRequestDispatcher(/beta/user/zu_BetaQna_I.jsp);");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaQna_I.jsp");

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
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BetaQnaBean bean = new BetaQnaBean();

            int isOk = bean.insertQnaQue(box);

            String v_msg = "";
            String v_url = "/servlet/controller.beta.BetaQnaServlet";
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
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            BetaQnaBean bean = new BetaQnaBean();
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaQna_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaQna_U.jsp");

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
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            BetaQnaBean bean = new BetaQnaBean();
			
            int isOk = bean.updateQna(box);
			System.out.println("updateQna(box)�θ���");
            String v_msg = "";
            String v_url = "/servlet/controller.beta.BetaQnaServlet";
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
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           BetaQnaBean bean = new BetaQnaBean();
		   //System.out.println("Types = "+box.getString("p_types"));

            int isOk = bean.deleteQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.beta.BetaQnaServlet";
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

            Log.info.println(this, box, v_msg + " on CpQnaServlet");
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
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            BetaQnaBean bean = new BetaQnaBean();

            ArrayList list = bean.selectListQna(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaQna_L.jsp");
			System.out.println("zu_CpQna_L.jsp�� forward�ϱ�");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaQna_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

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
    Qna �亯�������̵�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	 public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            BetaQnaBean bean = new BetaQnaBean();
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaQna_A.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /beta/user/zu_BetaQna_A.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
	/**
    Qna �亯�ϱ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           BetaQnaBean bean = new BetaQnaBean();

           int isOk = bean.insertQnaAns(box);

           String v_msg = "";
           String v_url = "/servlet/controller.beta.BetaQnaServlet";
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

            Log.info.println(this, box, v_msg + " on CpQnaServlet");
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performInsert()\r\n" + ex.getMessage());
       }
   }
	 

}

