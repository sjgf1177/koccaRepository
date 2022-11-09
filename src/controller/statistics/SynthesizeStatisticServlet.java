package controller.statistics;

import com.credu.library.*;
import com.credu.statistics.SynthesizeStatisticsBean;
import com.credu.system.*;
import com.credu.system.AdminUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 종합 통계 자료를 조회한다.
 * 
 * @author saderaser
 * @since 2022-09-28
 * 
 */
@WebServlet("/servlet/controller.statistics.SynthesizeStatisticServlet")
public class SynthesizeStatisticServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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

            if (!AdminUtil.getInstance().checkRWRight("SynthesizeStatisticServlet", v_process, out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
 
            if (v_process.equals("selectUsersStatisticList")) { // 회원 통계 리스트
                this.performUsersStatisticList (request, response, box, out);
            } else if (v_process.equals("selectUsersStatisticListExcel")) { // 회원 통계 엑셀 리스트
                this.performUsersStatisticListExcel (request, response, box, out);
            } else if (v_process.equals("selectEduUsersStatisticList")) { // 교육인원 통계 리스트
                this.performEduUsersStatisticList (request, response, box, out);
            } else if (v_process.equals("selectEduUsersStatisticListExcel")) { // 교육인원 통계 엑셀 리스트
                this.performEduUsersStatisticListExcel (request, response, box, out);
            } else if (v_process.equals("selectEduResultStatisticList")) { // 교육성과 통계 리스트
                this.performEduResultStatisticList (request, response, box, out);
            } else if (v_process.equals("selectEduResultStatisticListExcel")) { // 교육인원 통계 엑셀 리스트
                this.performEduResultStatisticListExcel(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 회원 통계 리스트
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performUsersStatisticList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
        	SynthesizeStatisticsBean bean = new SynthesizeStatisticsBean();
            CodeAdminBean cd_bean = new CodeAdminBean();
            
        	if("go".equals(box.getString("p_action"))){
        		ArrayList<DataBox> list = bean.selectUsersStatisticList(box);
        		request.setAttribute("selectUsersStatisticList", list);
        	}

            ArrayList<DataBox> gr_list = bean.selectGrcodeList(box);
            request.setAttribute("selectGrcodeList", gr_list);

            ArrayList<DataBox> cd_list = cd_bean.selectListCode("0125");
            request.setAttribute("selectListCode", cd_list);

            ArrayList<DataBox> job_list = bean.selectCodeList("COM056");
            request.setAttribute("selectJobList", job_list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UsersStatistics_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UsersStatistics_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUsersStatisticList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육인원 통계 리스트
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performEduUsersStatisticList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
            SynthesizeStatisticsBean bean = new SynthesizeStatisticsBean();
            CodeAdminBean cd_bean = new CodeAdminBean();

            if("go".equals(box.getString("p_action"))){
                ArrayList<DataBox> list = bean.selectEduUsersStatisticList(box);
                request.setAttribute("selectEduUsersStatisticList", list);
            }

            ArrayList<DataBox> gr_list = bean.selectGrcodeList(box);
            request.setAttribute("selectGrcodeList", gr_list);

            ArrayList<DataBox> cd_list = cd_bean.selectListCode("0125");
            request.setAttribute("selectListCode", cd_list);

            ArrayList<DataBox> job_list = bean.selectCodeList("COM056");
            request.setAttribute("selectJobList", job_list);

            ArrayList<DataBox> g1_list = bean.selectCodeList2("gubun = '0110' AND levels = 1 ANd code NOT IN('T0', 'A', 'O0') ORDER BY code");
            request.setAttribute("selectG1ListCode", g1_list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_EduUsersStatistics_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_EduUsersStatistics_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEduUsersStatisticList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육성과 통계 리스트
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performEduResultStatisticList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
            SynthesizeStatisticsBean bean = new SynthesizeStatisticsBean();
            CodeAdminBean cd_bean = new CodeAdminBean();

            if("go".equals(box.getString("p_action"))){
                ArrayList<DataBox> list = bean.selectEduResultStatisticList(box);
                request.setAttribute("selectEduResultStatisticList", list);
            }

            ArrayList<DataBox> gr_list = bean.selectGrcodeList(box);
            request.setAttribute("selectGrcodeList", gr_list);

            ArrayList<DataBox> cd_list = cd_bean.selectListCode("0125");
            request.setAttribute("selectListCode", cd_list);

            ArrayList<DataBox> job_list = bean.selectCodeList("COM056");
            request.setAttribute("selectJobList", job_list);

            ArrayList<DataBox> g1_list = bean.selectCodeList2("gubun = '0110' AND levels = 1 ANd code NOT IN('T0', 'A', 'O0') ORDER BY code");
            request.setAttribute("selectG1ListCode", g1_list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_EduResultStatistics_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_EduResultStatistics_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEduResultStatisticList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 종합통계 회원 EXCEL
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUsersStatisticListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SynthesizeStatisticsBean bean = new SynthesizeStatisticsBean();
            ArrayList<DataBox> list = bean.selectUsersStatisticListExcel(box);
            request.setAttribute("selectUsersStatisticListExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UsersStatistics_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("selectUsersStatisticListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 종합통계 교육인원 EXCEL
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduUsersStatisticListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SynthesizeStatisticsBean bean = new SynthesizeStatisticsBean();
            ArrayList<DataBox> list = bean.selectEduUsersStatisticListExcel(box);
            request.setAttribute("selectEduUsersStatisticListExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_EduUsersStatistics_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("selectUsersStatisticListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 종합통계 교육성과 EXCEL
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduResultStatisticListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SynthesizeStatisticsBean bean = new SynthesizeStatisticsBean();
            ArrayList<DataBox> list = bean.selectEduResultStatisticListExcel(box);
            request.setAttribute("selectEduResultStatisticListExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_EduResultStatistics_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("selectEduResultStatisticListExcel()\r\n" + ex.getMessage());
        }
    }

}
