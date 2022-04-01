//**********************************************************
//  1. 제      목: HomePage FAQ 카테고리 제어하는 서블릿
//  2. 프로그램명 : HomePageFaqCategoryAdminServlet.java
//  3. 개      요: HomePage FAQ 카테고리 제어 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 6. 26
//  7. 수      정:
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.EduGroupBean;
import com.credu.course.EduGroupData;
import com.credu.homepage.HomePageFaqCategoryAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageFaqCategoryAdminServlet")
public class HomePageFaqCategoryAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (v_process.equals("")) {
                box.put("v_process", "selectList");
                v_process = "selectList";
            }

            ///////////////////////////////////////////////////////////////////
            if (!AdminUtil.getInstance().checkRWRight("HomePageFaqCategoryAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if (v_process.equals("selectList")) { // faq 카테고리 리스트 조회
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("insertPage")) { // faq 카테고리 등록페이지로 이동
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { // faq 카테고리 등록
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) { // faq 카테고리 수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { //faq 카테고리 수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("delete")) { // faq 카테고리 삭제할때
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("faqList")) { // faq 리스트
                this.performFaqList(request, response, box, out);
            } else if (v_process.equals("insertPage2")) { // faq 등록페이지로 이동
                this.performInsertPage2(request, response, box, out);
            } else if (v_process.equals("insert2")) { // faq 등록
                this.performInsert2(request, response, box, out);
            } else if (v_process.equals("updatePage2")) { // faq 수정페이지로 이동할때
                this.performUpdatePage2(request, response, box, out);
            } else if (v_process.equals("update2")) { //faq 수정하여 저장할때
                this.performUpdate2(request, response, box, out);
            } else if (v_process.equals("delete2")) { // faq 삭제할때
                this.performDelete2(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    //HomePage FAQ 카테고리 리스트 페이지 조회
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // String v_user = box.getSession("userid");

            // 교육그룹코드 - 통합 라이브러리 적용으로 구문 삭제 (2009.10.23)
            //EduGroupBean bean = new EduGroupBean();
            //ArrayList list1 = bean.SelectGrcodeList(box);
            //request.setAttribute("GrcodeList", list1);

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();
            ArrayList<DataBox> list = homepagefc.selectListHomePageFaqCategory(box);
            request.setAttribute("selectListHomePageFaqCategory", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageFaqCategory_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // 교육그룹코드
            EduGroupBean bean = new EduGroupBean();
            ArrayList<EduGroupData> list1 = bean.SelectGrcodeList(box);
            request.setAttribute("GrcodeList", list1);

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();
            DataBox dbox = homepagefc.HomePageFaqCategorySetting(box);

            request.setAttribute("HomePageFaqCategorySetting", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageFaqCategory_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            int isOk = homepagefc.insertHomePageFaqCategory(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "저장하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "저장 에러!";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메뉴 수정페이지로 이동할때
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

            // 교육그룹코드
            EduGroupBean bean = new EduGroupBean();
            ArrayList<EduGroupData> list1 = bean.SelectGrcodeList(box);
            request.setAttribute("GrcodeList", list1);

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();
            DataBox dbox = homepagefc.selectHomePageFaqCategory(box);
            request.setAttribute("selectHomePageFaqCategory", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageFaqCategory_U.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * // 메뉴 수정하여 저장할때
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

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            int isOk = homepagefc.updateHomePageFaqCategory(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "저장하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "저장 에러!";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * // 메뉴 삭제할때
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
            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            int isOk = homepagefc.deleteHomePageFaqCategory(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "삭제하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "삭제 에러";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    //faq 리스트 페이지로 이동
    public void performFaqList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();
            ArrayList<DataBox> list = homepagefc.selectListHomePageFaq(box);
            request.setAttribute("selectListHomePageFaq", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageFaq_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    //faq 등록 페이지로 이동
    public void performInsertPage2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            DataBox dbox = homepagefc.HomePageFaqSetting(box);

            request.setAttribute("HomePageFaqSetting", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageFaq_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void performInsert2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            int isOk = homepagefc.insertHomePageFaq(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
            box.put("p_process", "faqList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "저장하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "저장 에러!";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    public void performUpdatePage2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            DataBox dbox = homepagefc.selectHomePageFaq(box);

            request.setAttribute("selectHomePageFaq", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_HomePageFaq_U.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage2()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void performUpdate2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            int isOk = homepagefc.updateHomePageFaq(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
            box.put("p_process", "faqList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "저장하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "저장 에러!";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * // faq 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelete2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageFaqCategoryAdminBean homepagefc = new HomePageFaqCategoryAdminBean();

            int isOk = homepagefc.deleteHomePageFaq(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
            box.put("p_process", "faqList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "삭제하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "삭제 에러";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

}
