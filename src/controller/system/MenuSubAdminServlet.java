//**********************************************************
//  1. 제      목: 운영모듈 제어하는 서블릿
//  2. 프로그램명 : MenuSubAdminServlet.java
//  3. 개      요: 운영모듈 제어 프로그램
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
import com.credu.system.MenuAuthAdminBean;
import com.credu.system.MenuSubAdminBean;
import com.credu.system.MenuSubData;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.system.MenuSubAdminServlet")
public class MenuSubAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if(v_process.equals("selectMenu")) {                     // 메뉴 리스트
                this.performSelectListMenu(request, response, box, out);
            } else if(v_process.equals("select")) {                  // 모듈 리스트
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("selectView")){               // 모듈 상세보기로 이동할때
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("insertPage")) {              // 모듀 등록페이지로 이동할때
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                  // 모듈 등록할때
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("updatePage")) {              // 모듈 수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                  // 모듈 수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                  // 모듈 삭제할때
                this.performDelete(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    // 모듈 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            MenuSubAdminBean bean = new MenuSubAdminBean();

            int isOk = bean.deleteMenuSub(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.MenuSubAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on MenuSubAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }


    /**
    모듈 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            MenuSubAdminBean bean = new MenuSubAdminBean();

            int isOk = bean.insertMenuSub(box);
            int isOk2 = bean.insertMenuAuth(box);
            String v_msg = "";
            String v_url = "/servlet/controller.system.MenuSubAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk*isOk2 > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on MenuSubAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }


    /**
    모듈 등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            /*-------- 하단 리스트 출력 시작 -------------*/
            MenuSubAdminBean bean = new MenuSubAdminBean();
            ArrayList list = bean.selectListMenuSub(box);
            request.setAttribute("selectList", list);
            /*-------- 하단 리스트 출력 시작 -------------*/

            MenuAuthAdminBean gadminbean = new MenuAuthAdminBean();
            ArrayList list2 = gadminbean.selectListGadmin(box);
            request.setAttribute("selectGadmin", list2);


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSub_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSub_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    모듈 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            MenuSubAdminBean bean = new MenuSubAdminBean();
            ArrayList list = bean.selectListMenuSub(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSub_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSub_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    메뉴 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectListMenu(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            MenuSubAdminBean bean = new MenuSubAdminBean();
            ArrayList List = bean.selectListMenu(box);

            request.setAttribute("selectList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSubTop_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectListMenu()\r\n" + ex.getMessage());
        }
    }


    /**
    모듈 상세보기로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            MenuSubAdminBean bean = new MenuSubAdminBean();
            MenuSubData data1 = bean.selectViewMenuSub(box);
            request.setAttribute("selectMenuSub", data1);

            ArrayList list = bean.selectListMenuAuth(box);
            request.setAttribute("selectMenuAuth", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSub_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSub_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
    // 모듈 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            MenuSubAdminBean bean = new MenuSubAdminBean();

            int isOk = bean.updateMenuSub(box);
            int isOk2 = bean.insertMenuAuth(box);
            String v_msg = "";
            String v_url = "/servlet/controller.system.MenuSubAdminServlet";
            box.put("p_process", "select");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk*isOk2 > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on MenuSubAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    모듈 수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            /*-------- 하단 리스트 출력 시작 -------------*/
            MenuSubAdminBean bean = new MenuSubAdminBean();
            ArrayList list = bean.selectListMenuSub(box);
            request.setAttribute("selectList", list);

            /*-------- 하단 리스트 출력 끝   -------------*/

            MenuSubData data1 = bean.selectViewMenuSub(box);
            request.setAttribute("selectMenuSub", data1);

            ArrayList list2 = bean.selectListMenuAuth(box);
            request.setAttribute("selectMenuAuth", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MenuSub_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MenuSub_U.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }


}

