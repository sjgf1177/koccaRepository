//**********************************************************
//  1. ��      ��: ������� �����ϴ� ����
//  2. ���α׷��� : CountServlet.java
//  3. ��      ��: ������� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 7
//  7. ��      ��:
//**********************************************************

package controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.CountBean;
import com.credu.system.CountData;

@WebServlet("/servlet/controller.system.CountServlet")
public class CountServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 6478253266254825646L;

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

            if (!AdminUtil.getInstance().checkRWRight("CountServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("selectMonthDay")) { //  ��������������� �̵��Ҷ�
                this.performSelectMonthDay(request, response, box, out);
            } else if (v_process.equals("selectDayTime")) { //  �Ͻ������������ �̵��Ҷ�
                this.performSelectDayTime(request, response, box, out);
            } else if (v_process.equals("selectMonthTime")) { //  ��������������� �̵��Ҷ�
                this.performSelectMonthTime(request, response, box, out);
            } else if (v_process.equals("selectMonthWeek")) { //  ����������������� �̵��Ҷ�
                this.performSelectMonthWeek(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �������������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectMonthDay(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CountBean bean = new CountBean();

            // �����ī��Ʈ
            int cnt = bean.SelectYearCnt(box);
            request.setAttribute("YearCnt", String.valueOf(cnt));
            // �� ���
            ArrayList<CountData> list1 = bean.SelectMonth(box);
            request.setAttribute("selectList1", list1);
            // ���� ���
            ArrayList<CountData> list2 = bean.SelectMonthDay(box);
            request.setAttribute("selectList2", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CountMonthDay_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CountMonthDay_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * �Ͻ����������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectDayTime(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CountBean bean = new CountBean();

            // �����ī��Ʈ
            int cnt = bean.SelectYearCnt(box);
            request.setAttribute("YearCnt", String.valueOf(cnt));
            // ���� ���
            ArrayList<CountData> list1 = bean.SelectMonthDay(box);
            request.setAttribute("selectList1", list1);
            // �Ͻ� ���
            ArrayList<CountData> list2 = bean.SelectDayTime(box);
            request.setAttribute("selectList2", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CountDayTime_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CountDayTime_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectDayTime()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectMonthTime(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CountBean bean = new CountBean();

            // �����ī��Ʈ
            int cnt = bean.SelectYearCnt(box);
            request.setAttribute("YearCnt", String.valueOf(cnt));
            // �� ���
            ArrayList<CountData> list1 = bean.SelectMonth(box);
            request.setAttribute("selectList1", list1);
            // ���� ���
            ArrayList<CountData> list2 = bean.SelectMonthTime(box);
            request.setAttribute("selectList2", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CountMonthTime_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CountMonthTime_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthTime()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���������������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectMonthWeek(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CountBean bean = new CountBean();

            // �����ī��Ʈ
            int cnt = bean.SelectYearCnt(box);
            request.setAttribute("YearCnt", String.valueOf(cnt));
            // �� ���
            ArrayList<CountData> list1 = bean.SelectMonth(box);
            request.setAttribute("selectList1", list1);
            // ���������
            ArrayList<CountData> list2 = bean.SelectMonthWeek(box);
            request.setAttribute("selectList2", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CountMonthWeek_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CountMonthWeek_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthWeek()\r\n" + ex.getMessage());
        }
    }

}
