/**
 * FileName : MainCategoryDetailServlet.java
 * Comment : Ȩ������ ���� ȭ�� �α�/��õ �׸� ���� ��� �� ���� ��ϵǴ� ������ ����ϴ� servlet class�̴�.
 * @version : 1.0
 * @author : kocca
 * @date : 2015. 2. 03
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

import com.credu.admin.homepage.MainCategoryDetailBean;
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
@WebServlet("/servlet/controller.admin.homepage.MainCategoryDetailServlet")
public class MainCategoryDetailServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3233812969494723976L;

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

            request.setAttribute("uploadName", "maincategory");

            box = RequestManager.getBox(request);
            process = box.getStringDefault("process", "retrieveList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!process.equals("searchSubj") && !process.equals("viewBoardContents")) {
                if (!AdminUtil.getInstance().checkRWRight("MainCategoryDetailServlet", process, out, box)) {
                    return;
                }
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

            } else if (process.equals("searchSubj")) {
                // ���� ��� ã��
                this.performSearchSubj(request, response, box, out);

            } else if (process.equals("updateUseYn")) {
                this.performUpdateUseYn(request, response, box, out);

            } else if (process.equals("viewBoardContents")) {
                this.performViewBoardContents(request, response, box, out);

            } else if (process.equals("updateMainItem")) {
                this.performUpdateMainItem(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �Խ��� ������ ī�װ��� ���� �׸� �� ���õ� �Խù����� ���α۷� �����Ѵ�.
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    private void performUpdateMainItem(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            int resultCount = bean.updateMainItem(box);

            AlertManager alert = new AlertManager();
            if (resultCount > 0) {
                // ��� ���� �� ��� ȭ������ �̵�

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryDetailServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "update.ok", retUrl, box);

            } else {
                // ��� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "update.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryDetailServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateUseYn()\r\n" + ex.getMessage());
        }
    }

    /**
     * �Խù� ������ ��ȸ�Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    private void performViewBoardContents(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            DataBox result = bean.retrieveBoardContents(box);
            request.setAttribute("result", result);
            request.setAttribute("returnName", "contents");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/common/za_GetAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/common/za_GetListAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSearchSubj()\r\n" + ex.getMessage());
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
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            int resultCount = bean.updateUseYn(box);

            AlertManager alert = new AlertManager();
            if (resultCount > 0) {
                // ��� ���� �� ��� ȭ������ �̵�

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryDetailServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "update.ok", retUrl, box);

            } else {
                // ��� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "update.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryDetailServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateUseYn()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ����� �˻��Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    private void performSearchSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            ArrayList<DataBox> subjList = bean.retriveSubjList(box);
            request.setAttribute("resultList", subjList);
            request.setAttribute("returnName", "subjList");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/common/za_GetListAjaxResult.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/common/za_GetListAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSearchSubj()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ȩ������ ���� ȭ�� �α�/��õ �׸��� ���� ��� ���� ����� ��ȸ�Ѵ�.
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
            String categoryType = box.getString("categoryType");
            String url = "";

            MainCategoryDetailBean bean = new MainCategoryDetailBean();
            ArrayList<DataBox> mainCategoryDetailList = new ArrayList<DataBox>();

            if (categoryType.equals("C_ONL") || categoryType.equals("C_OPN")) {
                mainCategoryDetailList = bean.retrieveMainCategoryDetailList(box);
                url = "/learn/admin/homepage/maincategory/za_RetrieveMainCategoryDetailListA.jsp";

            } else {
                mainCategoryDetailList = bean.retrieveMainCategoryDetailListFromBoard(box);
                url = "/learn/admin/homepage/maincategory/za_RetrieveMainCategoryDetailListB.jsp";

            }

            request.setAttribute("mainCategoryDetailList", mainCategoryDetailList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + url);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRetrieveDetailList()\r\n" + ex.getMessage());
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/maincategory/za_RegisterMainCategoryDetailPage.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/maincategory/za_RegisterMainCategoryDetailPage.jsp");

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
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            String layoutType = box.getString("layoutType");
            int maxRegCnt = layoutType.equals("A") ? 5 : 4;

            int resultCount = bean.registerMainCategoryDetail(box);

            AlertManager alert = new AlertManager();
            if (resultCount > 0 && resultCount != 99) {
                // ��� ���� �� ��� ȭ������ �̵�
                String retUrl = "/servlet/controller.admin.homepage.MainCategoryDetailServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "insert.ok", retUrl, box);
            } else if (resultCount == 99) {
                // ��� ���� �׸� ���� �ʰ�. ��� ����
                alert.alertFailMessage(out, "����ϰ� �ִ� ���� �׸��� �̹� " + maxRegCnt + "���Դϴ�.\\\n��� ���θ� �̻������ ������ �� ��Ͽ��� �����Ͻñ� �ٶ��ϴ�.");

            } else {
                // ��� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "insert.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryDetailServlet");

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
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            DataBox mainCategoryDetailInfo = bean.retrieveMainCategoryDetailInfo(box);

            request.setAttribute("mainCategoryDetailInfo", mainCategoryDetailInfo);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/maincategory/za_RetrieveMainCategoryDetailInfo.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/maincategory/za_RetrieveMainCategoryDetailInfo.jsp");

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
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            int resultCount = bean.deleteMainCategoryDetail(box);

            AlertManager alert = new AlertManager();
            if (resultCount > 0) {
                // ���� ���� �� ��� ȭ������ �̵�
                String retUrl = "/servlet/controller.admin.homepage.MainCategoryDetailServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "delete.ok", retUrl, box);

            } else {
                // ���� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "delete.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryDetailServlet");

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
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            DataBox mainCategoryDetailInfo = bean.retrieveMainCategoryDetailInfo(box);

            request.setAttribute("mainCategoryDetailInfo", mainCategoryDetailInfo);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/maincategory/za_UpdateMainCategoryDetailPage.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/maincategory/za_UpdateMainCategoryDetailPage.jsp");

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
            MainCategoryDetailBean bean = new MainCategoryDetailBean();

            int resultCount = bean.updateMainCategoryDetail(box);
            AlertManager alert = new AlertManager();
            if (resultCount > 0) {
                // ���� ���� �� ��� ȭ������ �̵�

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryDetailServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "update.ok", retUrl, box);

            } else {
                // ���� ���н� ���� ȭ��(��� ȭ��)���� history back
                alert.alertFailMessage(out, "update.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryDetailServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

}
