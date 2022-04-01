//*********************************************************
//  1. ��      ��: Counsel ADMIN SERVLET
//  2. ���α׷���: CounselAdminServlet.java
//  3. ��      ��: ��� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 7. 7
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

import com.credu.common.SearchAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.CounselAdminBean;
import com.credu.system.AdminUtil;
import com.credu.system.MemberData;
@WebServlet("/servlet/controller.study.CounselAdminServlet")
public class CounselAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -365838120411671368L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
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

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("CounselAdminServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("CounselListPage")) { //  ������� ����Ʈ
                this.performCounselListPage(request, response, box, out);
            } else if (v_process.equals("CounselInsertPage")) { // ��� ���������
                this.performCounselInsertPage(request, response, box, out);
            } else if (v_process.equals("CounselInsert")) { // ��� ���
                this.performCounselInsert(request, response, box, out);
            } else if (v_process.equals("CounselUpdatePage")) { // ��� ����������
                this.performCounselUpdatePage(request, response, box, out);
            } else if (v_process.equals("CounselUpdate")) { // ��� ����
                this.performCounselUpdate(request, response, box, out);
            } else if (v_process.equals("CounselDelete")) { // ��� ����
                this.performCounselDelete(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ������� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCounselListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ��������
            SearchAdminBean bean = new SearchAdminBean();
            MemberData data = bean.selectPersonalInformation(box);
            request.setAttribute("SelectMemberInfo", data);

            //��㳻����ȸ
            CounselAdminBean bean1 = new CounselAdminBean();
            ArrayList<DataBox> list1 = bean1.selectListCounselSubj(box);
            request.setAttribute("counselList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalCounsel_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��� ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCounselInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            // ��������
            SearchAdminBean bean = new SearchAdminBean();
            MemberData data = bean.selectPersonalInformation(box);
            request.setAttribute("SelectMemberInfo", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalCounsel_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_PersonalCounsel_I.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��� ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCounselInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            CounselAdminBean bean = new CounselAdminBean();
            int isOk = bean.insertCounsel(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.CounselAdminServlet";
            box.put("p_process", "CounselListPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CounselAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselInsert()\r\n" + ex.getMessage());
        }

    }

    /**
     * ��� ������������ �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCounselUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            // ��������
            SearchAdminBean bean = new SearchAdminBean();
            MemberData data = bean.selectPersonalInformation(box);
            request.setAttribute("SelectMemberInfo", data);

            CounselAdminBean bean1 = new CounselAdminBean();
            DataBox dbox = bean1.selectViewCounsel(box);
            request.setAttribute("selectCounsel", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalCounsel_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_PersonalCounsel_U.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��� �����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCounselUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            CounselAdminBean bean = new CounselAdminBean();
            int isOk = bean.updateCounsel(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.CounselAdminServlet";
            box.put("p_process", "CounselListPage");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CounselAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselUpdate()\r\n" + ex.getMessage());
        }

    }

    /**
     * ��� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCounselDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            CounselAdminBean bean = new CounselAdminBean();
            int isOk = bean.deleteCounsel(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.CounselAdminServlet";
            box.put("p_process", "CounselListPage");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CounselAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselDelete()\r\n" + ex.getMessage());
        }

    }

}