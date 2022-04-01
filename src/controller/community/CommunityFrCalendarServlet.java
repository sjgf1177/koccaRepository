//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ ȸ�������� �����ϴ� ����
//  2. ���α׷��� : CommunityFrCalendarServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� ȸ������ �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1 
//  7. ��      ��: 2005.7.1 
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

public class CommunityFrCalendarServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter      out          = null;
        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            String path = request.getServletPath();

            out       = response.getWriter();
            box       = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","selectmsmainPage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("movewritePage")) {              //  ��� �������� �̵��Ҷ�
              this.performmoFrWritePage(request, response, box, out);
            } else if(v_process.equals("insertData")) {          //  ���ó��
              this.performInsertFrWriteData(request, response, box, out);
//            } else if(v_process.equals("movelistPage")) {          //  ��������Ʈ���������̵�
//              this.performListPage(request, response, box, out);
            } else if(v_process.equals("moveviewPage")) {          //  ��ȸ���������̵�
              this.performViewPage(request, response, box, out);
            } else if(v_process.equals("deleteData")) {          //  ��ȸ���������̵�
              this.performDeleteData(request, response, box, out);


            } else if(v_process.equals("moveupdatePage")) {          //  �������������̵�
              this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("updateData")) {          //  �������������̵�
              this.performUpdateData(request, response, box, out);

            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ����Ʈ �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityFrCalendarBean bean = new CommunityFrCalendarBean();
            ArrayList list = bean.selectList(box); 
            
            request.setAttribute("list", list);


            //������ ȸ�� ���������
            CommunityMsMangeBean beancmUser = new CommunityMsMangeBean();
            DataBox listcmUser = beancmUser.selectMemSingleData(box);
            request.setAttribute("listUser", listcmUser);

            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box);
            request.setAttribute("listAllUser", listbeanAllMem);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrCalendar_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrCalendar_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


    /**
    ��ȸ �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityFrCalendarBean bean = new CommunityFrCalendarBean();
            ArrayList list = bean.selectSingle(box); 
            
            request.setAttribute("list", list);


            //������ ȸ�� ���������
            CommunityMsMangeBean beancmUser = new CommunityMsMangeBean();
            DataBox listcmUser = beancmUser.selectMemSingleData(box);
            request.setAttribute("listUser", listcmUser);

            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box);
            request.setAttribute("listAllUser", listbeanAllMem);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrCalendar_L1.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrCalendar_L1.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewPage()\r\n" + ex.getMessage());
        }
    }


    /**
    ��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performmoFrWritePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityIndexBean bean = new CommunityIndexBean();
            DataBox list = bean.selectTz_Member(box);
            
            request.setAttribute("list", list);


            //������ ȸ�� ���������
            CommunityMsMangeBean beancmUser = new CommunityMsMangeBean();
            DataBox listcmUser = beancmUser.selectMemSingleData(box);
            request.setAttribute("listUser", listcmUser);

            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box);
            request.setAttribute("listAllUser", listbeanAllMem);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrCalendar_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrCalendar_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }



     /**
    ����������Ҷ� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performInsertFrWriteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrCalendarBean bean = new CommunityFrCalendarBean();

            int isOk = bean.insertFrCalendar(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrCalendarServlet";
            box.put("p_process", "movewritePage");

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
            throw new Exception("performInsertFrWriteData()\r\n" + ex.getMessage());
        }
    }




     /**
    �����������Ҷ� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrCalendarBean bean = new CommunityFrCalendarBean();

            int isOk = bean.deleteFrCalendar(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrCalendarServlet";
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

            Log.info.println(this, box, v_msg + " on CommunityFrCalendarServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteData()\r\n" + ex.getMessage());
        }
    }


     /**
    �����������Ҷ� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrCalendarBean bean = new CommunityFrCalendarBean();

            int isOk = bean.updateFrCalendar(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityFrCalendarServlet";
            box.put("p_process", "moveupdatePage");

            ArrayList list = bean.selectSingle(box); 
            
            request.setAttribute("list", list);


            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrCalendarServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateData()\r\n" + ex.getMessage());
        }
    }


    /**
    ���� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityFrCalendarBean bean = new CommunityFrCalendarBean();
            ArrayList list = bean.selectSingle(box); 
            
            request.setAttribute("list", list);


            //������ ȸ�� ���������
            CommunityMsMangeBean beancmUser = new CommunityMsMangeBean();
            DataBox listcmUser = beancmUser.selectMemSingleData(box);
            request.setAttribute("listUser", listcmUser);

            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box); 
            request.setAttribute("listAllUser", listbeanAllMem);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrCalendar_I1.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrCalendar_I1.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }


}

