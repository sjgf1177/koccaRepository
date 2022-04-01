//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ �Ű� ����
//  2. ���α׷��� : CommunityFrPoliceServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� �Ű� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1 
//  7. ��      ��: 2005.7.1 
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityAdminPoliceBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
public class CommunityAdminPoliceServlet extends javax.servlet.http.HttpServlet {

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

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("listPage")) {                   //   ����Ʈ �������� �̵��Ҷ�
              this.performListPage(request, response, box, out);
            } else if(v_process.equals("viewPage")) {          //  �������������̵�
              this.performViewPage(request, response, box, out);

            } else if(v_process.equals("updateData")) {          //  ����ó��
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
    @SuppressWarnings("unchecked")
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityAdminPoliceBean bean = new CommunityAdminPoliceBean();
            ArrayList list = bean.selectList(box); 
            request.setAttribute("list", list);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CmuPolice_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/community/za_CmuPolice_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
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
    @SuppressWarnings("unchecked")
	public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityAdminPoliceBean bean = new CommunityAdminPoliceBean();
            ArrayList list = bean.selectView(box); 
            request.setAttribute("list", list);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CmuPolice_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/community/za_CmuPolice_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    ����ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
	public void performUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityAdminPoliceBean bean = new CommunityAdminPoliceBean();

            int isOk = bean.updatePolice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityAdminPoliceServlet";
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

            Log.info.println(this, box, v_msg + " on CommunityAdminPoliceServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateData()\r\n" + ex.getMessage());
        }
    }

}

