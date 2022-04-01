//**********************************************************
//1. ��      ��: Ȩ������ ������������ ���� ����
//2. ���α׷���: HomePagePreCourseAdminServlet.java
//3. ��      ��: Ȩ������ ������������ ���� ����
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2005.12.13
//7. ��      ��: 
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

        // �α� check ��ƾ VER 0.2 - 2003.09.9
        if (!AdminUtil.getInstance().checkLogin(out, box)) {
         return; 
        }
        box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

        /////////////////////////////////////////////////////////////////////////////
        if(v_process.equals("selectList")) {          //  ����Ʈ �������� �̵��Ҷ�
          this.performListPage(request, response, box, out);
        } else if(v_process.equals("insertPage")) {          //  ��� �������� �̵��Ҷ�
          this.performInsertPage(request, response, box, out);
        } else if(v_process.equals("insertData")) {          //  �űԵ�� 
          this.performInsertData(request, response, box, out);
        } else if(v_process.equals("viewPage")) {           //  ���� 
          this.performViewPage(request, response, box, out);
        } else if(v_process.equals("updatePage")) {           //  ���� �������� �̵��Ҷ�
          this.performUpdatePage(request, response, box, out);
        } else if(v_process.equals("updateData")) {           //  ���� �������� �̵��Ҷ�
          this.performUpdateData(request, response, box, out);
        } else if(v_process.equals("deleteData")) {           //  �ۻ���
    	  this.performDeleteData(request, response, box, out);
        } 
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
    }
}


/**
����Ʈ�������� �̵��Ҷ�
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
���������� �̵��Ҷ�
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
��� �������� �̵��Ҷ�
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try {
        request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

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
������������ �������� �̵��Ҷ�
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    try {
        request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
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
�űԵ�� �Ҷ�
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
�� ������ ��
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
���� �Ҷ�
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

