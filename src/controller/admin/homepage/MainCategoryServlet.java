/**
 * FileName : MainCategoryServlet.java
 * Comment : 홈페이지 메인 화면 인기/추천 항목 관리 기능을 담당하는 servlet class이다.
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
                // 목록 조회
                this.performRetrieveList(request, response, box, out);

            } else if (process.equals("registerPage")) {
                // 등록 화면으로 이동
                this.performRegisterPage(request, response, box, out);

            } else if (process.equals("register")) {
                // 등록
                this.performRegister(request, response, box, out);

            } else if (process.equals("retrieveInfo")) {
                // 상세 보기
                this.performRetrieveInfo(request, response, box, out);

            } else if (process.equals("delete")) {
                // 삭제
                this.performDelete(request, response, box, out);

            } else if (process.equals("updatePage")) {
                // 수정 화면으로 이동
                this.performUpdatePage(request, response, box, out);

            } else if (process.equals("update")) {
                // 수정
                this.performUpdate(request, response, box, out);

            } else if (process.equals("updateUseYn")) {
                this.performUpdateUseYn(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 메인 화면 관리에서 사용여부를 일괄 적용한다.
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
                // 등록 성공 시 목록 화면으로 이동

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "update.ok", retUrl, box);

            } else {
                // 등록 실패시 이전 화면(등록 화면)으로 history back
                alert.alertFailMessage(out, "update.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateUseYn()\r\n" + ex.getMessage());
        }
    }

    /**
     * 홈페이지 메인 화면 인기/추천 항목 관리 목록을 조회한다.
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
     * 홈페이지 메인 화면 인기/추천 항목 관리 등록 화면으로 이동한다.
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
     * 홈페이지 메인 화면 인기/추천 항목을 등록한다.
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
                // 등록 성공 시 목록 화면으로 이동

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "insert.ok", retUrl, box);

            } else if (resultCount == 99) {
                // 최대 사용가능 개수 초과로 등록 실패. 이전 화면(등록 화면)으로 history back
                alert.alertFailMessage(out, "사용하고 있는 카테고리 항목이 이미 6개입니다.\\\n사용 여부를 미사용으로 선택한 후 메인 목록에서 변경하시기 바랍니다.");
            } else {
                // 등록 실패시 이전 화면(등록 화면)으로 history back
                alert.alertFailMessage(out, "insert.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegister()\r\n" + ex.getMessage());
        }
    }

    /**
     * 홈페이지 메인 화면 인기/추천 항목 상세 내용을 조회한다.
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
     * 홈페이지 메인 화면 인기/추천 항목을 삭제한다.
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
                // 삭제 성공 시 목록 화면으로 이동

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "delete.ok", retUrl, box);

            } else {
                // 삭제 실패시 이전 화면(등록 화면)으로 history back
                alert.alertFailMessage(out, "delete.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 홈페이지 메인 화면 인기/추천 항목 관리 수정 화면으로 이동한다.
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
     * 홈페이지 메인 화면 인기/추천 항목을 수정한다.
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
                // 수정 성공 시 목록 화면으로 이동

                String retUrl = "/servlet/controller.admin.homepage.MainCategoryServlet";
                box.put("process", "retrieveList");

                alert.alertOkMessage(out, "update.ok", retUrl, box);
            } else if (resultCount == 88) {
                // 사용가능 항목 초과로 수정 실패.
                alert.alertFailMessage(out, "사용하고 있는 카테고리 항목이 이미 6개입니다.\\\n사용 여부를 미사용으로 선택한 후 메인 목록에서 변경하시기 바랍니다.");
                
            } else if (resultCount == 99) {
                // 레이아웃을 B type으로 설정한 경우, 기존에 등록된 하위 과정이 4개를 초과한 경우
                alert.alertFailMessage(out, "해당 카테고리에 기 등록된 과정의 사용 가능한 수를 초과하였습니다.\\\n과정 관리에서 과정수 수정 후, 변경하여 주시기 바랍니다.");

            } else {
                // 수정 실패시 이전 화면(등록 화면)으로 history back
                alert.alertFailMessage(out, "update.fail");
            }

            Log.info.println(this, box, "Dispatch to /servlet/controller.admin.homepage.MainCategoryServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

}
