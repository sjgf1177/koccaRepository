//**********************************************************
//1. ��      ��: ȸ�������Ȳ�� �����ϴ� ����
//2. ���α׷��� : UserCountServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��:
//7. ��      ��:
//**********************************************************

package controller.statistics;

import java.io.PrintWriter;
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
import com.credu.statistics.UserCountBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.statistics.UserCountServlet")
public class UserCountServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8284886225471131656L;

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

            ///////////////////////////////////////////////////////////////////
            if (!AdminUtil.getInstance().checkRWRight("UserCountServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if (v_process.equals("selectYearCnt")) { //  �⵵�� ȸ�������Ȳ �������� �̵��� ��
                this.performSelectYearCnt(request, response, box, out);
            } else if (v_process.equals("selectMonthCnt")) { //  ���� ȸ�������Ȳ �������� �̵��� ��
                this.performSelectMonthCnt(request, response, box, out);
            } else if (v_process.equals("selectLocationCnt")) { //������ ȸ�������Ȳ �������� �̵��� ��
                this.performSelectLocationCnt(request, response, box, out);
            } else if (v_process.equals("selectAgeCnt")) { //���ɺ� ȸ�������Ȳ �������� �̵��� ��
                this.performSelectAgeCnt(request, response, box, out);
            } else if (v_process.equals("selectVocationCnt")) { //���ɺ� ȸ�������Ȳ �������� �̵��� ��
                this.performSelectVocationCnt(request, response, box, out);

            } else if (v_process.equals("selectStat")) { //ȸ������ ��� �⺻ȭ��
                this.performSelectStat(request, response, box, out);
            } else if (v_process.equals("selectYearStat")) { //ȸ������ ����  New ���
                this.performSelectYearStat(request, response, box, out);
            } else if (v_process.equals("selectMonthStat")) { // ���� ȸ������ ����  New ���
                this.performSelectMonthStat(request, response, box, out);
            } else if (v_process.equals("selectAgeStat")) { // ���ɺ�  New ���
                this.performSelectAgeStat(request, response, box, out);
            } else if (v_process.equals("selectVocationStat")) { // ������  New ���
                this.performSelectVocationStat(request, response, box, out);
            } else if (v_process.equals("selectLocationStat")) { // ������  New ���
                this.performSelectLocationStat(request, response, box, out);
            } else if (v_process.equals("selectWeekStat")) { // ���Ϻ�  New ���
                this.performSelectWeekStat(request, response, box, out);
            } else if (v_process.equals("selectHourStat")) { // �ð���  New ���
                this.performSelectHourStat(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �⵵�� ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListNew_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * �⵵�� ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectYearStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            //
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberYearStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectYearStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "ȸ������ ����");//���� ����
                box.put("tname", "����|��ü|����|����|��Ȯ��");//�÷���
                box.put("tcode", "d_indate|d_usercnt|d_man|d_woman|d_johndoe");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectMonthStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberMonthStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectMonthStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "����  �ű�ȸ�� ����");//���� ����
                box.put("tname", "������|��ü|����|����|��Ȯ��");//�÷���
                box.put("tcode", "d_indate|d_usercnt|d_man|d_woman|d_johndoe");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���ɺ� ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectAgeStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberAgeStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectAgeStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���ɺ� ��� ");//���� ����
                box.put("tname", "������|��ü|10��|20��|30��|40��|50��|60��|70��|��Ȯ��");//�÷���
                box.put("tcode", "d_indate|d_usercnt|d_teens|d_twenty|d_thirty|d_fourty|d_fifty|d_sixty|d_seventy|d_johndoe");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectVocationStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberVocationStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectVocationStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���ɺ� ��� ");//���� ����
                box.put("tname", "������|��ü|����л�|���л�|������|�������|��������|���������|ȸ���|�ֺ�|����|������|�Ƿ���|������|�����|������|������|ü����|����|����|�ڿ���|��Ÿ|�̵�� ");//�÷���
                box.put("tcode",
                        "d_indate|d_usercnt|d_high|d_univ|d_country|d_gonggong|d_freelancer|d_preoffice|d_office|d_jubu|d_miltiary|d_univoffice|d_hospital|d_lawyer|d_broad|d_jongkyo|d_entertain|d_sports|d_proffesor|d_lecter|d_owner|d_etc|d_johndoe");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectLocationStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_MemberLocationStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectLocationStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���ɺ� ��� ");//���� ����
                box.put("tname", "������|��ü|����|���|�λ�|��õ|�뱸|����|����|����|����|���|�泲|����|���|�泲|���|����|��Ÿ  ");//�÷���
                box.put("tcode", "d_indate|d_usercnt|d_seoul|d_kyunggi|d_busan|d_incheon|d_daegu|d_daejeon|d_kwangju|d_jeonbook|d_jeonnam|d_chungbook|d_choongnam|d_gangwon|d_gyungbook|d_gyungnam|d_woolsan|d_jejoo|d_etc");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���Ϻ� ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectWeekStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberWeekStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectWeekStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���ɺ� ��� ");//���� ����
                box.put("tname", "����|��|��ü|��|ȭ|��|��|��|��|��  ");//�÷���
                box.put("tcode", "d_date_year|d_date_month|d_tot|d_mon|d_tue|d_wed|d_thi|d_fri|d_sat|d_sun");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * �ð��� ȸ�������Ȳ ������ New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectHourStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberHourStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectHourStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���ɺ� ��� ");//���� ����
                box.put("tname", "����|��|��ü|2��|4��|6��|8�� |10�� |12��|14��|16��|18��|20��|22��|24��   ");//�÷���
                box.put("tcode", "d_date_year|d_date_month|d_tot|d_first|d_second|d_third|d_fourth|d_fifth|d_sixth|d_seventh|d_eightth|d_nineth|d_tenth|d_eleventh|d_twelveth");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * �⵵�� ȸ�������Ȳ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectYearCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListNew_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ȸ�������Ȳ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectMonthCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectMonthCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListMonth_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListMonth_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ȸ�������Ȳ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectLocationCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectLocationCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListLocation_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListLocation_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���ɺ� ȸ�������Ȳ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectAgeCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectAgeCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListAge_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ȸ�������Ȳ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectVocationCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectVocationCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListVocation_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

}
