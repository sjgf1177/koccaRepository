//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ ��ü ���������� �����ϴ� ����
//  2. ���α׷���: CommunityAdminNoticeServlet.java
//  3. ��      ��: Ŀ�´�Ƽ ��ü ���������� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: mscho 2004. 02. 17
//  7. ��      ��:
//**********************************************************

package controller.community;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.credu.community.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class CommunityAdminNoticeServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
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

            String path = request.getServletPath();

			box = BulletinManager.getState(path.substring(path.lastIndexOf(".")+1, path.lastIndexOf("Servlet")), box, out);            

            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("CommunityAdminNoticeServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////            
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply  = BulletinManager.isAuthority(box,box.getString("p_canReply"));           

            if(v_process.equals("insertPage")) {          //  ����������� �̵��Ҷ�
                if(v_canAppend) this.performInsertPage(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("insert")) {         //  ����Ҷ�
                if(v_canAppend) this.performInsert(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("updatePage")) {     //  ������������ �̵��Ҷ�
                if(v_canModify) this.performUpdatePage(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("update")) {         //  �����Ͽ� �����Ҷ�
                if(v_canModify) this.performUpdate(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("delete")) {         //  �����Ҷ�
                if(v_canDelete) this.performDelete(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("select")) {         //  �󼼺����Ҷ�
                if(v_canRead) this.performSelect(request, response, box, out);
            }
            else if(v_process.equals("selectList")) {     //  ����Ʈ�����Ҷ�
                if(v_canRead) this.performSelectList(request, response, box, out);
            }            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            
            CommunityNoticeBean bean = new CommunityNoticeBean();            
            ArrayList list = bean.selectBoardList(box);
            request.setAttribute("selectCommunityNoticeList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityNotice_L.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            CommunityNoticeBean bean = new CommunityNoticeBean();            
            //BoardData data = bean.selectBoard(box);
			DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectCommunityNotice", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityNotice_R.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

     /**
    ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityNotice_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
    ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            CommunityNoticeBean bean = new CommunityNoticeBean();
            int isOk = bean.insertBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityAdminNoticeServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

     /**
    ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            CommunityNoticeBean bean = new CommunityNoticeBean();
            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectCommunityNotice", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityNotice_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            CommunityNoticeBean bean = new CommunityNoticeBean();
            int isOk = bean.updateBoard(box);

            String v_msg = "";
            String v_url  = "/servlet/controller.community.CommunityAdminNoticeServlet";
            box.put("p_process", "selectList");
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
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
    
     /**
    �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityNoticeBean bean = new CommunityNoticeBean();
            int isOk = bean.deleteBoard(box);

            String v_msg = "";
            String v_url  = "/servlet/controller.community.CommunityAdminNoticeServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }


    public void errorPage(RequestBox box, PrintWriter out) 
        throws Exception {       
        try {                         
            box.put("p_process", "selectList");
            
            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            //  Log.sys.println();

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}

