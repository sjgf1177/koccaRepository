//**********************************************************
//  1. ��      ��: ���������������Ȳ �����ϴ� ����
//  2. ���α׷��� : CourseProgressAdminServlet.java
//  3. ��      ��: ���������������Ȳ �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 8. 13
//  7. ��      ��:
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.CourseProgressAdminBean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.course.CourseProgressAdminServlet")
public class CourseProgressAdminServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("CourseProgressAdminServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

            if(v_process.equals("select")) {                // ��ȸ�Ҷ�
                this.performSelectList(request, response, box, out);
            }
            else if(v_process.equals("selectPre")) {    // ���� �˻� ��
                this.performSelectPre(request, response, box, out);
            }
            else if(v_process.equals("CourseProgressExcel")) {    // ����
                this.performCourseProgressExcel(request, response, box, out);
            }
            


            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ����Ʈ(�˻���)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseProgressAdminBean bean = new CourseProgressAdminBean();

            ArrayList list = bean.selectListCourseProgress(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseProgress_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/course/za_CourseProgress_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    ����Ʈ(�˻���)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ArrayList list = new ArrayList();
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseProgress_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/course/za_CourseProgress_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

    /**
    ��������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCourseProgressExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
			CourseProgressAdminBean bean = new CourseProgressAdminBean();

            ArrayList list = bean.selectListCourseProgress(box);
            request.setAttribute("selectList", list);
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseProgress_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }    
}

