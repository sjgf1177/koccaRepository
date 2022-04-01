//**********************************************************
//1. 제      목: 홈페이지 예정개설과정 제어 서블릿
//2. 프로그램명: HomePagePreCourseAdminServlet.java
//3. 개      요: 홈페이지 예정개설과정 제어 서블릿
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 2005.12.13
//7. 수      정: 
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.HomePagePreCourseAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.course.HomePagePreCourseAdminServlet")
public class HomePagePreCourseAdminServlet extends javax.servlet.http.HttpServlet {

/**
* DoGet
* Pass get requests through to PerformTask
*/
public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    this.doPost(request, response);
}
public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    PrintWriter      out          = null;
    RequestBox       box          = null;
    String           v_process    = "";

    try {
        response.setContentType("text/html;charset=euc-kr");
        String path = request.getServletPath();

        out       = response.getWriter();
        box       = RequestManager.getBox(request);
        v_process = box.getStringDefault("p_process","selectList");

        if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

        // 로긴 check 루틴 VER 0.2 - 2003.09.9
        if (!AdminUtil.getInstance().checkLogin(out, box)) {
         return; 
        }
        box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

        /////////////////////////////////////////////////////////////////////////////
        if(v_process.equals("selectList")) {          //  리스트 페이지로 이동할때
          this.performListPage(request, response, box, out);
        } else if(v_process.equals("insertPage")) {          //  등록 페이지로 이동할때
          this.performInsertPage(request, response, box, out);
        } else if(v_process.equals("insertData")) {          //  신규등록 
          this.performInsertData(request, response, box, out);
        } else if(v_process.equals("viewPage")) {           //  보기 
          this.performViewPage(request, response, box, out);
        } else if(v_process.equals("updatePage")) {           //  수정 페이지로 이동할때
          this.performUpdatePage(request, response, box, out);
        } else if(v_process.equals("updateData")) {           //  수정 페이지로 이동할때
          this.performUpdateData(request, response, box, out);
        } else if(v_process.equals("deleteData")) {           //  글삭제
    	  this.performDeleteData(request, response, box, out);
        } 
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
    }
}


/**
리스트페이지로 이동할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try {
        request.setAttribute("requestbox", box);  
		HomePagePreCourseAdminBean bean = new HomePagePreCourseAdminBean();
        ArrayList list = bean.selectDirectList(box); 
        
        request.setAttribute("list", list);
        ServletContext    sc = getServletContext();

        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_PreCourse_L.jsp");
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/user/course/za_PreCourse_L.jsp");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertPage()\r\n" + ex.getMessage());
    }
}


/**
뷰페이지로 이동할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try {
        request.setAttribute("requestbox", box);  
		HomePagePreCourseAdminBean bean = new HomePagePreCourseAdminBean();
        DataBox dbox = bean.selectView(box); 
        
        request.setAttribute("list", dbox);



        ServletContext    sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_PreCourse_R.jsp");
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/admin/course/za_PreCourse_R.jsp");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performViewPage()\r\n" + ex.getMessage());
    }
}

/**
등록 페이지로 이동할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try {
        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

        ServletContext    sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_PreCourse_I.jsp");
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/admin/course/za_PreCourse_I.jsp");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertPage()\r\n" + ex.getMessage());
    }
}



/**
수정페이지로 페이지로 이동할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try {
        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
		HomePagePreCourseAdminBean bean = new HomePagePreCourseAdminBean();
        DataBox dbox= bean.selectView(box); 
        
        request.setAttribute("list", dbox);



        ServletContext    sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_PreCourse_U.jsp");
        rd.forward(request, response);

        Log.info.println(this, box, "Dispatch to /learn/admin/course/za_PreCourse_U.jsp");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertPage()\r\n" + ex.getMessage());
    }
}

/**
신규등록 할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/

public void performInsertData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
		HomePagePreCourseAdminBean bean = new HomePagePreCourseAdminBean();

        int isOk = bean.insertPreCourse(box);

        String v_msg = "";
        String v_url = "/servlet/controller.course.HomePagePreCourseAdminServlet";
        box.put("p_process", "selectList");

        AlertManager alert = new AlertManager();

        if(isOk > 0) {
            v_msg = "insert.ok";
            alert.alertOkMessage(out, v_msg, v_url , box);
        }
        else {
            v_msg = "insert.fail";
            alert.alertFailMessage(out, v_msg);
        }

        Log.info.println(this, box, v_msg + " on HomePagePreCourseAdminServlet");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertData()\r\n" + ex.getMessage());
    }
}

/**
글 삭제할 때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/

public void performDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
		HomePagePreCourseAdminBean bean = new HomePagePreCourseAdminBean();

        int isOk = bean.deletePreCourse(box);

        String v_msg = "";
        String v_url = "/servlet/controller.course.HomePagePreCourseAdminServlet";
        box.put("p_process", "selectList");

        AlertManager alert = new AlertManager();

        if(isOk > 0) {
            v_msg = "delete.ok";
            alert.alertOkMessage(out, v_msg, v_url , box);
        }
        else {
            v_msg = "delete.fail";
            alert.alertFailMessage(out, v_msg);
        }

        Log.info.println(this, box, v_msg + " on HomePagePreCourseAdminServlet");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertData()\r\n" + ex.getMessage());
    }
}
/**
수정 할때
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/

public void performUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
		HomePagePreCourseAdminBean bean = new HomePagePreCourseAdminBean();

        int isOk = bean.updatePreCourse(box);

        String v_msg = "";
        String v_url = "/servlet/controller.course.HomePagePreCourseAdminServlet";
        box.put("p_process", "selectList");

        AlertManager alert = new AlertManager();

        if(isOk > 0) {
            v_msg = "update.ok";
            alert.alertOkMessage(out, v_msg, v_url , box);
        }
        else {
            v_msg = "update.fail";
            alert.alertFailMessage(out, v_msg);
        }

        Log.info.println(this, box, v_msg + " on HomePagePreCourseAdminServlet");
    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertData()\r\n" + ex.getMessage());
    }
}
}

