package controller.statistics;

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
import com.credu.statistics.SpotStudyStatisticsBean;
import com.credu.system.AdminUtil;

/**
 * 상시과정 자료를 조회한다.
 * 
 * @author saderaser
 * @since 2016-02-19
 * 
 */
@WebServlet("/servlet/controller.statistics.SpotStudyStatisticServlet")
public class SpotStudyStatisticServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("SpotStudyAdminServlet", v_process, out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
 
            if (v_process.equals("selectSpotStudyStatisticList")) { // 통계자료 조회
                this.performSpotStudyStatisticList(request, response, box, out);

            }//ManageOpenClassPage
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    private void performSpotStudyStatisticList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
        	SpotStudyStatisticsBean bean = new SpotStudyStatisticsBean();
            
            ArrayList<DataBox> selectAppplyCountList = bean.selectAppplyCountList(box);
            request.setAttribute("selectAppplyCountList", selectAppplyCountList);
            
            ArrayList<DataBox> selectGraduatedCountList = bean.selectGraduatedCountList(box);
            request.setAttribute("selectGraduatedCountList", selectGraduatedCountList);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SpotStudyStatistics_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_SpotStudyStatistics_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectOpenClassStatisticsList()\r\n" + ex.getMessage());
        }
    }

}
