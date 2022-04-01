// **********************************************************
// 1. �� ��: ��� �����ϴ� ����
// 2. ���α׷��� : MainStatusticServlet
// 3. �� ��:
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��:
// 7. �� ��:
// **********************************************************

package controller.statistics;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.EduGroupBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.statistics.MainStatusticBean;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "serial", "unused" })
@WebServlet("/servlet/controller.statistics.MainStatusticServlet")
public class MainStatusticServlet extends javax.servlet.http.HttpServlet {

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

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("selectStat")) {
                this.performSelectStat(request, response, box, out);

            } else if (v_process.equals("selectStat2")) { // ��������
                this.performSelectGoldclassStat(request, response, box, out);

            } else if (v_process.equals("selectStat3")) { // Ʃ�� ���
                this.performSelectTutorStat(request, response, box, out);

            } else if (v_process.equals("setStatList")) {
                this.performSetStatList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MainStatusticBean bean = new MainStatusticBean();

            ArrayList<DataBox> selectList = bean.selectList(box);
            request.setAttribute("selectList", selectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/main/za_ajax_Stat.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/main/za_ajax_Stat.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectGoldclassStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";
            MainStatusticBean bean = new MainStatusticBean();

            ArrayList<DataBox> selectList = bean.selectViewCountTotListGoldClass(box);
            request.setAttribute("selectList", selectList);
            v_url = "/learn/admin/statistics/main/za_ajax_GoldclassStat.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * Ʃ��
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectTutorStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";
            MainStatusticBean bean = new MainStatusticBean();

            ArrayList<DataBox> selectList = bean.selectTutorStatus(box);
            request.setAttribute("selectList", selectList);
            v_url = "/learn/admin/statistics/main/za_ajax_TutorStat.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSetStatList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";
            MainStatusticBean bean = new MainStatusticBean();

            ArrayList<DataBox> selectList = bean.selectSetStatList(box);
            request.setAttribute("selectList", selectList);
            v_url = "/learn/admin/statistics/main/za_ajax_SettingStat.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }
}
