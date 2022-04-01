/**
 * FileName : MainCategoryServlet.java
 * Comment : Ȩ������ ���� ȭ�� �α�/��õ �׸� ���� ����� ����ϴ� servlet class�̴�.
 * @version : 1.0
 * @author : kocca
 * @date : 2015. 1. 27
 */
package controller.admin.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.admin.homepage.MainCategoryBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author kocca
 * 
 */
@WebServlet("/servlet/controller.admin.homepage.MainCategoryServlet")
public class MainCategoryServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 370073368704246165L;

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
        RequestBox box = null;
        String process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            request.setAttribute("uploadName", "mainCategoy");

            box = RequestManager.getBox(request);
            process = box.getStringDefault("process", "retrieveList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("MainCategoryServlet", process, out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (process.equals("retrieveList")) {
                // ��� ��ȸ
                this.performRetrieveList(request, response, box, out);

            } else if (process.equals("registerPage")) {
                // ��� ȭ������ �̵�
                this.performRegisterPage(request, response, box, out);

            } else if (process.equals("register")) {
                // ���
                this.performRegister(request, response, box, out);

            } else if (process.equals("retrieveInfo")) {
                // �� ����
                this.performRetrieveInfo(request, response, box, out);

            } else if (process.equals("delete")) {
                // ����
                this.performDelete(request, response, box, out);

            } else if (process.equals("updatePage")) {
                // ���� ȭ������ �̵�
                this.performUpdatePage(request, response, box, out);

            } else if (process.equals("update")) {
                // ����
                this.performUpdate(request, response, box, out);

            } else if (process.equals("updateUseYn")) {
                this.performUpdateUseYn(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ���� ȭ�� �������� ��뿩�θ� �ϰ� �����Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    private void performUpdateUseYn(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryBean bean = new MainCategoryBean();

            int resultCount = bean.updateUseYn(box);

            AlertManager alert = new AlertManager();
            if (resultCount > 0) {
                // ��� ���� �� ��� ȭ������ �̵�

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "update.ok", retUrl, box);

            } else {
                // ��� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "update.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateUseYn()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸� ���� ����� ��ȸ�Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRetrieveList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryBean bean = new MainCategoryBean();

            ArrayList<DataBox> mainCategoryList = bean.retrieveMainCategoyList(box);
            request.setAttribute("mainCategoryList", mainCategoryList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/maincategory/za_RetrieveMainCategoryList.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/maincategory/za_RetrieveMainCategoryList.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRetriveList()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸� ���� ��� ȭ������ �̵��Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRegisterPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/maincategory/za_RegisterMainCategoryPage.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/maincategory/za_RegisterMainCategoryPage.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegisterPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸��� ����Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performRegister(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryBean bean = new MainCategoryBean();

            int resultCount = bean.registerMainCategory(box);

            System.out.println("resultCount : " + resultCount);

            AlertManager alert = new AlertManager();
            if (resultCount > 0 && resultCount != 99) {
                // ��� ���� �� ��� ȭ������ �̵�

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "insert.ok", retUrl, box);

            } else if (resultCount == 99) {
                // �ִ� ��밡�� ���� �ʰ��� ��� ����. ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "����ϰ� �ִ� ī�װ� �׸��� �̹� 6���Դϴ�.\\\n��� ���θ� �̻������ ������ �� ���� ��Ͽ��� �����Ͻñ� �ٶ��ϴ�.");
            } else {
                // ��� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "insert.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegister()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸� �� ������ ��ȸ�Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRetrieveInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryBean bean = new MainCategoryBean();

            DataBox mainCategoryInfo = bean.retrieveMainCategoryInfo(box);
            ArrayList<DataBox> mainCategoryDetailList = bean.retrieveMainCategoryDetailList(box);

            request.setAttribute("mainCategoryInfo", mainCategoryInfo);
            request.setAttribute("mainCategoryDetailList", mainCategoryDetailList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/maincategory/za_RetrieveMainCategoryInfo.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/maincategory/za_RetrieveMainCategoryInfo.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRetrieveInfo()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸��� �����Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryBean bean = new MainCategoryBean();

            int resultCount = bean.deleteMainCategory(box);

            AlertManager alert = new AlertManager();
            if (resultCount > 0) {
                // ���� ���� �� ��� ȭ������ �̵�

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "delete.ok", retUrl, box);

            } else {
                // ���� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "delete.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸� ���� ���� ȭ������ �̵��Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryBean bean = new MainCategoryBean();

            DataBox mainCategoryInfo = bean.retrieveMainCategoryInfo(box);

            request.setAttribute("mainCategoryInfo", mainCategoryInfo);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/maincategory/za_UpdateMainCategoryPage.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/maincategory/za_UpdateMainCategoryPage.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸��� �����Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryBean bean = new MainCategoryBean();

            int resultCount = bean.updateMainCategory(box);

            AlertManager alert = new AlertManager();
            if (resultCount > 0 && resultCount != 99) {
                // ���� ���� �� ��� ȭ������ �̵�

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "update.ok", retUrl, box);
            } else if (resultCount == 88) {
                // ��밡�� �׸� �ʰ��� ���� ����.
                alert.alertFailMessage(out, "����ϰ� �ִ� ī�װ� �׸��� �̹� 6���Դϴ�.\\\n��� ���θ� �̻������ ������ �� ���� ��Ͽ��� �����Ͻñ� �ٶ��ϴ�.");
                
            } else if (resultCount == 99) {
                // ���̾ƿ��� B type���� ������ ���, ������ ��ϵ� ���� ������ 4���� �ʰ��� ���
                alert.alertFailMessage(out, "�ش� ī�װ��� �� ��ϵ� ������ ��� ������ ���� �ʰ��Ͽ����ϴ�.\\\n���� �������� ������ ���� ��, �����Ͽ� �ֽñ� �ٶ��ϴ�.");

            } else {
                // ���� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "update.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

}
