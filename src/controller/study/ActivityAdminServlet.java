//*********************************************************
//  1. ��      ��: ������ ����
//  2. ���α׷���: ActivityAdminServlet.java
//  3. ��      ��: ������ ���� ������ servlet
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: 2009.11.11
//  7. ��      ��:
//**********************************************************
package controller.study;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.ActivityAdminBean;
import com.credu.study.ActivityAdminData;
import com.credu.study.ToronAdminBean;
import com.credu.study.ToronData;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.study.ActivityAdminServlet")
public class ActivityAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;


        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }


            if (!AdminUtil.getInstance().checkRWRight("ActivityAdminServlet", v_process, out, box)) {
                return;
            }

            if(v_process.equals("listPage")){                  // ���������� ����Ʈ
                    this.performListPage(request, response, box, out);
            }else if(v_process.equals("listPageExcel")){                  // �������
                this.performListPageExcel(request, response, box, out);
            } else if(v_process.equals("updatePage")) {              // ������ ������������ �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                  // ������ �����Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("ViewToron")){             // ��� �󼼺���
                this.performViewToron(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ���������� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

            ActivityAdminBean bean = new ActivityAdminBean();
            ArrayList lists = bean.selectActivityList(box);
            request.setAttribute("activitylist", lists);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Activity_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorList()\r\n" + ex.getMessage());
        }
    }

    /**
    ���������� ����Ʈ ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performListPageExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

            ActivityAdminBean bean = new ActivityAdminBean();
            ArrayList lists = bean.selectActivityList(box);
            request.setAttribute("activitylistExcel", lists);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Activity_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("activityList()\r\n" + ex.getMessage());
        }
    }

    /**
    ������ ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            /*-------- �Խ���Ȱ������ ��� ���� -------------*/
            ActivityAdminBean bean = new ActivityAdminBean();
            ArrayList bbsList = bean.selectBbsList(box);
            request.setAttribute("selectBbsList", bbsList);
            /*-------- �Խ���Ȱ������ ��� ��   -------------*/

            /*-------- ����Ƚ������ ��� ���� -------------*/
            ArrayList loginList = bean.selectLoginList(box);
            request.setAttribute("selectLoginList", loginList);
            /*-------- ����Ƚ������ ��� ��   -------------*/

            /*-------- ������/��Ÿ ���� ��� ���� -------------*/
            ActivityAdminData data = bean.selectViewActivity(box);
            request.setAttribute("selectViewActivity", data);
            /*-------- ������/��Ÿ ���� ��� ���� -------------*/

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Activity_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_Activity_U.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    // ������ �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            ActivityAdminBean bean = new ActivityAdminBean();

            int isOk = bean.updateActivity(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.ActivityAdminServlet";
            box.put("p_process", "listPage");
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

            Log.info.println(this, box, v_msg + " on ActivityAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    ��� �󼼺���� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performViewToron(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            ToronAdminBean bean = new ToronAdminBean();
            ToronData data= bean.selectToron(box);

            request.setAttribute("toronSelect", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_ActivityToron_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_ActivityToron_R.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewToron()\r\n" + ex.getMessage());
        }
    }
}