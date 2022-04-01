//*********************************************************
//  1. 제      목: Counsel ADMIN SERVLET
//  2. 프로그램명: CounselAdminServlet.java
//  3. 개      요: 상담 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 7. 7
//  7. 수      정:
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

            if (v_process.equals("CounselListPage")) { //  상담정보 리스트
                this.performCounselListPage(request, response, box, out);
            } else if (v_process.equals("CounselInsertPage")) { // 상담 등록페이지
                this.performCounselInsertPage(request, response, box, out);
            } else if (v_process.equals("CounselInsert")) { // 상담 등록
                this.performCounselInsert(request, response, box, out);
            } else if (v_process.equals("CounselUpdatePage")) { // 상담 수정페이지
                this.performCounselUpdatePage(request, response, box, out);
            } else if (v_process.equals("CounselUpdate")) { // 상담 수정
                this.performCounselUpdate(request, response, box, out);
            } else if (v_process.equals("CounselDelete")) { // 상담 삭제
                this.performCounselDelete(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 상담정보 리스트
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

            // 개인정보
            SearchAdminBean bean = new SearchAdminBean();
            MemberData data = bean.selectPersonalInformation(box);
            request.setAttribute("SelectMemberInfo", data);

            //상담내역조회
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
     * 상담 등록페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCounselInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            // 개인정보
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
     * 상담 등록할때
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
     * 상담 수정페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCounselUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            // 개인정보
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
     * 상담 수정하여 저장할때
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
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

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
     * 상담 삭제할때
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
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

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