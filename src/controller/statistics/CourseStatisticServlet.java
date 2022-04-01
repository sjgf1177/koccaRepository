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
import com.credu.statistics.CourseStatisticsBean;
import com.credu.statistics.SpotStudyStatisticsBean;
import com.credu.system.AdminUtil;

/**
 * 정규(상시) 과정별 통계 자료를 조회한다.
 * 
 * @author saderaser
 * @since 2016-06-08
 * 
 */
@WebServlet("/servlet/controller.statistics.CourseStatisticServlet")
public class CourseStatisticServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("CourseStatisticServlet", v_process, out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
 
            if (v_process.equals("selectCourseStatisticList")) { // 정규(상시) 과정별 통계 리스트
                this.performCourseStatisticList (request, response, box, out);

            }else if (v_process.equals("selectCourseStatisticExcel")) { // 정규(상시) 과정별 통계 엑셀다운로드
                this.selectCourseStatisticExcel (request, response, box, out);

            }if (v_process.equals("selectCourseSatisfactionList")) { // 정규(상시) 과정별 만족도 리스트
                this.performCourseSatisfactionList (request, response, box, out);

            }else if (v_process.equals("selectCourseSatisfactionExcel")) { // 정규(상시) 과정별 만족도 엑셀다운로드
                this.selectCourseSatisfactionExcel (request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 정규(상시) 과정별 통계 리스트
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performCourseStatisticList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
        	CourseStatisticsBean bean = new CourseStatisticsBean();
            
        	if("go".equals(box.getString("p_action"))){
        		ArrayList<DataBox> list = bean.selectCourseStatisticList(box);
        		request.setAttribute("selectCourseStatisticList", list);
        	}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStatistics_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStatistics_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseStatisticList()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * 정규(상시) 과정별 통계 엑셀다운로드
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void selectCourseStatisticExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {

        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
        	CourseStatisticsBean bean = new CourseStatisticsBean();
            
        	if("go".equals(box.getString("p_action"))){
        		ArrayList<DataBox> list = bean.selectCourseStatisticList(box);
        		request.setAttribute("selectCourseStatisticList", list);
        	}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStatistics_E.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStatistics_E.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("selectCourseStatisticExcel()\r\n" + ex.getMessage());
        }
    }

    
    
    private void performCourseSatisfactionList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
        	CourseStatisticsBean bean = new CourseStatisticsBean();
            
        	if("go".equals(box.getString("p_action"))){
        		ArrayList<DataBox> list = bean.selectCourseSatisfactionList(box);
        		request.setAttribute("selectCourseSatisfactionList", list);
        	}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatisfaction_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSatisfaction_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseSatisfactionList()\r\n" + ex.getMessage());
        }
    }
    private void selectCourseSatisfactionExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		
    		request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
    		CourseStatisticsBean bean = new CourseStatisticsBean();
    		
    		if("go".equals(box.getString("p_action"))){
    			ArrayList<DataBox> list = bean.selectCourseSatisfactionList(box);
    			request.setAttribute("selectCourseSatisfactionList", list);
    		}
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatisfaction_E.jsp");
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSatisfaction_E.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("selectCourseSatisfactionExcel()\r\n" + ex.getMessage());
    	}
    }
}
