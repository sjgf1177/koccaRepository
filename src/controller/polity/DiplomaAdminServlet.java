//*********************************************************
//1. ��      ��: ������ ����
//2. ���α׷���: DiplomaAdminServlet.java
//3. ��      ��: �������� servlet
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: �ϰ��� 2006.01.16
//7. ��      ��:
//**********************************************************
package controller.polity;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.polity.DiplomaAdminBean;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.polity.DiplomaAdminServlet")
public class DiplomaAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getStringDefault("p_process", "selectList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("selectList")) { //  ��ȸȭ��
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("ExcelDown")) { //  ���� ����
                this.performExcelList(request, response, box, out);
            } else if (v_process.equals("DiplomaPrint")) { //  ������ ���.
                this.performDiplomaPrint(request, response, box, out);
            } else if (v_process.equals("DiplomaOffPrint")) { //  ������ ��� off-line.
                this.performDiplomaOffPrint(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����Ʈ(������)
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

            DiplomaAdminBean bean = new DiplomaAdminBean();

            ArrayList list = bean.selectList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_DiplomaAdmin_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_DiplomaAdmin_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * Excel �ٿ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            DiplomaAdminBean bean = new DiplomaAdminBean();

            if (box.getString("p_action").equals("go")) {
                ArrayList list = bean.selectExcelList(box);
                request.setAttribute("selectList", list);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_DiplomaAdmin_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ(������)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDiplomaPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            DiplomaAdminBean bean = new DiplomaAdminBean();

            DataBox dbox = bean.diplomaPrint(box);
            request.setAttribute("diplomaPrint", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_DiplomaAdmin_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������ΰ��� ������ ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDiplomaOffPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            DiplomaAdminBean bean = new DiplomaAdminBean();

            DataBox dbox = bean.diplomaOffPrint(box);
            request.setAttribute("diplomaPrint", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_OffDiplomaAdmin_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
}