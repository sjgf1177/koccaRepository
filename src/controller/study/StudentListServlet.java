//**********************************************************
//  1. 제      목:  과정 수강생 현황 서블릿
//  2. 프로그램명 : StudentListServlet.java
//  3. 개      요: 과정 수강생의 리스트를 조회하는 서블릿
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 20
//  7. 수     정1:
//**********************************************************
package controller.study;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.study.*;
import com.credu.library.*;
import com.credu.system.*;

@WebServlet("/servlet/controller.study.StudentListServlet")
public class StudentListServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 46058704433719336L;

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
            //v_process = box.getString("p_process");
            v_process = "select";

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // if(v_process.equals("selectView")){                      //권한 상세보기로 이동할때
            //     this.performSelectView(request, response, box, out);
            // } else if(v_process.equals("insertPage")) {              //권한 등록페이지로 이동할때
            //     this.performInsertPage(request, response, box, out);
            // } else if(v_process.equals("insert")) {                  //권한 등록할때
            //     this.performInsert(request, response, box, out);
            // } else if(v_process.equals("updatePage")) {              //권한 수정페이지로 이동할때
            //     this.performUpdatePage(request, response, box, out);
            // } else if(v_process.equals("update")) {                  //권한 수정하여 저장할때
            //     this.performUpdate(request, response, box, out);
            // } else if(v_process.equals("delete")) {                  //권한 삭제할때
            //     this.performDelete(request, response, box, out);
            // } else if(v_process.equals("select")) {                  //권한 리스트
            //     this.performSelectList(request, response, box, out);
            if (v_process.equals("select")) { // 권한 리스트
                this.performSelectList(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 관리자 상세보기로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    //public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
    //
    //        PermissionBean bean = new PermissionBean();
    //
    //        PermissionData data = bean.selectViewPermission(box);
    //        request.setAttribute("selectManager", data);
    //
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Permission_R.jsp");
    //        rd.forward(request, response);
    //
    //       Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Permission_R.jsp");
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("performSelectView()\r\n" + ex.getMessage());
    //    }
    //}

    /**
     * 관리자 등록페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    //public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
    //
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Permission_I.jsp");
    //        rd.forward(request, response);
    //
    //       Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Permission_I.jsp");
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("performInsertPage()\r\n" + ex.getMessage());
    //    }
    //}

    /**
     * 관리자 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    //public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    //    try {
    //
    //        PermissionBean bean = new PermissionBean();
    //
    //        int isOk  = bean.insertManager(box);
    //        int isOk1 = bean.insertManagerSub(box);
    //        int isOk2 = bean.insertMenuAuth(box);
    //
    //
    //
    //
    //        String v_msg = "";
    //        String v_url = "/servlet/controller.system.PermissionServlet";
    //        box.put("p_process", "select");
    //
    //        AlertManager alert = new AlertManager();
    //
    //        if(isOk > 0 && isOk1 > 0 &  isOk2 >0) {
    //            v_msg = "insert.ok";
    //            alert.alertOkMessage(out, v_msg, v_url , box);
    //        }
    //        else {
    //            v_msg = "insert.fail";
    //            alert.alertFailMessage(out, v_msg);
    //        }
    //
    //        Log.info.println(this, box, v_msg + " on PermissionServlet");
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("performInsert()\r\n" + ex.getMessage());
    //    }
    //}

    /**
     * 관리자 수정페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    //public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    //    try {
    //        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
    //
    //        PermissionBean bean = new PermissionBean();
    //
    //        PermissionData data = bean.selectViewPermission(box);
    //        request.setAttribute("selectManager", data);
    //
    //        ServletContext sc = getServletContext();
    //        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Permission_U.jsp");
    //        rd.forward(request, response);
    //
    //       Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Permission_U.jsp");
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
    //    }
    //}

    /**
     * // 관리자 수정하여 저장할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    //public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    //    throws Exception {
    //     try {
    //
    //        PermissionBean bean = new PermissionBean();
    //
    //        int isOk = bean.updateManager(box);
    //        int isOk1 = bean.updateManagerSub(box);
    //
    //        String v_msg = "";
    //        String v_url = "/servlet/controller.system.PermissionServlet";
    //        box.put("p_process", "select");
    //        //      수정 후 해당 리스트 페이지로 돌아가기 위해
    //
    //        AlertManager alert = new AlertManager();
    //
    //        if(isOk > 0 && isOk1 > 0) {
    //            v_msg = "update.ok";
    //            alert.alertOkMessage(out, v_msg, v_url , box);
    //        }
    //        else {
    //            v_msg = "update.fail";
    //            alert.alertFailMessage(out, v_msg);
    //        }
    //
    //        Log.info.println(this, box, v_msg + " on PermissionServlet");
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("performUpdate()\r\n" + ex.getMessage());
    //    }
    //}

    /**
     * // 관리자 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    //public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    //    try {
    //
    //       PermissionBean bean = new PermissionBean();
    //
    //        int isOk = bean.deleteManager(box);
    //        int isOk2 = bean.deleteMenu(box);
    //
    //        String v_msg = "";
    //        String v_url = "/servlet/controller.system.PermissionServlet";
    //        box.put("p_process", "select");
    //
    //        AlertManager alert = new AlertManager();
    //
    //        if(isOk > 0 && isOk > 0) {
    //            v_msg = "delete.ok";
    //            alert.alertOkMessage(out, v_msg, v_url , box);
    //        }
    //        else {
    //            v_msg = "delete.fail";
    //            alert.alertFailMessage(out, v_msg);
    //        }
    //
    //        Log.info.println(this, box, v_msg + " on PermissionServlet");
    //    }catch (Exception ex) {
    //        ErrorManager.getErrorStackTrace(ex, out);
    //        throw new Exception("performDelete()\r\n" + ex.getMessage());
    //    }
    //}

    /**
     * 관리자 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            // 과정별 메뉴 접속 정보 추가
            box.put("p_menu", "93");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            StudentListBean bean = new StudentListBean();
            ArrayList<DataBox> List = bean.selectListStudent(box);

            request.setAttribute("select", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/za_SubjStudentList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/study/za_SubjStudentList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}
