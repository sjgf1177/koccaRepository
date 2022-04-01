//**********************************************************
//  1. 제      목: 운영프로세스 제어하는 서블릿
//  2. 프로그램명 : MenuSubProcessAdminServlet.java
//  3. 개      요: 운영프로세스 제어 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 2
//  7. 수     정1:
//**********************************************************
package controller.system;

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
import com.credu.system.MenuSubProcessAdminBean;
import com.credu.system.MenuSubProcessData;

@WebServlet("/servlet/controller.system.MenuSubProcessAdminServlet")
public class MenuSubProcessAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4580060395229580984L;

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
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (v_process.equals("select")) { // 프로세스 리스트
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("selectView")) { // 프로세스 상세보기로 이동할때
                this.performSelectView(request, response, box, out);
            } else if (v_process.equals("insertPage")) { // 프로세스 등록페이지로 이동할때
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { // 프로세스 등록할때
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) { // 프로세스 수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { // 프로세스 수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("delete")) { // 프로세스 삭제할때
                this.performDelete(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 프로세스 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            MenuSubProcessAdminBean bean = new MenuSubProcessAdminBean();
            ArrayList<MenuSubProcessData> List = bean.selectListMenuSubProcess(box);

            request.setAttribute("selectList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSubProcess_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSubProcess_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 프로세스 상세보기로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            MenuSubProcessAdminBean bean = new MenuSubProcessAdminBean();
            MenuSubProcessData data = bean.selectViewMenuSubProcess(box);
            request.setAttribute("selectMenuSubProcess", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSubProcess_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSubProcess_R.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
     * 프로세스 등록페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            /*-------- 하단 리스트 출력 시작 -------------*/
            MenuSubProcessAdminBean bean = new MenuSubProcessAdminBean();
            ArrayList<MenuSubProcessData> List = bean.selectListMenuSubProcess(box);
            request.setAttribute("selectList", List);
            /*-------- 하단 리스트 출력 시작 -------------*/

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSubProcess_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSubProcess_I.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 프로세스 등록할때
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
            MenuSubProcessAdminBean bean = new MenuSubProcessAdminBean();

            int isOk = bean.insertMenuSubProcess(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.MenuSubProcessAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on MenuSubProcessAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 프로세스 수정페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            /*-------- 하단 리스트 출력 시작 -------------*/
            MenuSubProcessAdminBean bean = new MenuSubProcessAdminBean();
            ArrayList<MenuSubProcessData> List = bean.selectListMenuSubProcess(box);
            request.setAttribute("selectList", List);
            /*-------- 하단 리스트 출력 끝   -------------*/

            MenuSubProcessData data = bean.selectViewMenuSubProcess(box);
            request.setAttribute("selectMenuSubProcess", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSubProcess_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSubProcess_U.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 프로세스 수정하여 저장할때
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
            MenuSubProcessAdminBean bean = new MenuSubProcessAdminBean();

            int isOk = bean.updateMenuSubProcess(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.MenuSubProcessAdminServlet";
            box.put("p_process", "select");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on MenuSubProcessAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 프로세스 삭제할때
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
            MenuSubProcessAdminBean bean = new MenuSubProcessAdminBean();

            int isOk = bean.deleteMenuSubProcess(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.MenuSubProcessAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on MenuSubProcessAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

}
