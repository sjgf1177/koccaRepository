//**********************************************************
//  1. ��      ��: �뵿�� ���������� �����ϴ� ����
//  2. ���α׷��� : HomePageProcessServlet.java
//  3. ��      ��: �뵿�� �������� �������� �����Ѵ�(HOMEPAGE)
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.26 �̿���
//  7. ��      ��:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.HomePageWorkServlet")
public class HomePageWorkServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 5399088592133843998L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

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

            if (v_process.equals("selectList")) { // ����Ʈ
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("selectListMove01")) {
                this.performSelectListMove01(request, response, box, out);
            } else if (v_process.equals("selectListMove02")) {
                this.performSelectListMove02(request, response, box, out);
            } else if (v_process.equals("selectListMove03")) {
                this.performSelectListMove03(request, response, box, out);
            } else if (v_process.equals("selectListMove04")) {
                this.performSelectListMove04(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_HomePageWork_L.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_HomePageWork_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��Ź�Ʒ� �Ƿ����� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectListMove01(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_HomePageWork_01.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_HomePageWork_01.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectListMove01()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��Ź�Ʒ� �Ƿ����� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectListMove02(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_HomePageWork_02.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_HomePageWork_02.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectListMove02()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��Ź�Ʒ� �Ƿ����� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectListMove03(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_HomePageWork_03.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_HomePageWork_03.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectListMove03()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��Ź�Ʒ� �Ƿ����� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectListMove04(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_HomePageWork_04.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_HomePageWork_04.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectListMove04()\r\n" + ex.getMessage());
        }
    }

}
