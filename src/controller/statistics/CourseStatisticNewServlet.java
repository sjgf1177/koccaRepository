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
import com.credu.statistics.CourseStatisticsNewBean;
import com.credu.statistics.SpotStudyStatisticsBean;
import com.credu.system.AdminUtil;

/**
 * 정규(상시) 과정별 통계 오류수정ver. by rsg  
 * 
 * @author saderaser
 * @since 2016-06-08
 * 
 */
@WebServlet("/servlet/controller.statistics.CourseStatisticNewServlet")
public class CourseStatisticNewServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("CourseStatisticNewServlet", v_process, out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
 
            if (v_process.equals("selectCourseStatisticList")) { // 정규(상시) 참여기준 통계 리스트 
                this.performCourseStatisticList (request, response, box, out);

            }else if (v_process.equals("selectCourseStatisticExcel")) { // 정규(상시) 참여기준  통계 엑셀다운로드
                this.selectCourseStatisticExcel (request, response, box, out);
                
            }else if (v_process.equals("selectCourseStatisticExcelLowData")) { // 정규(상시) 참여기준  통계 LowData 엑셀다운로드
            	this.selectCourseStatisticExcelLowData (request, response, box, out);
            	
           /* }else if (v_process.equals("selectCourseStatisticView")) { // 상세페이지
            	this.selectCourseStatisticView (request, response, box, out); //za_CourseStatisticsNew_D.jsp
            	*/
            }else if (v_process.equals("selectCourseGraduatedList")) { // 정규(상시) 수료기준+만족도 통계
                this.performCourseGraduatedList (request, response, box, out);
                
            }else if (v_process.equals("selectCourseGraduatedExcel")) { // 정규(상시) 수료기준+만족도 통계 엑셀다운로드
                this.selectCourseGraduatedExcel (request, response, box, out);
                
	        }else if (v_process.equals("selectCourseGraduatedExcelLowData")) { // 정규(상시) 수료기준+만족도 통계 엑셀다운로드
	        	this.selectCourseGraduatedExcelLowData (request, response, box, out);
	        }
            /*
            if (v_process.equals("selectCourseSatisfactionList")) { // 정규(상시) 과정별 만족도 리스트
                this.performCourseSatisfactionList (request, response, box, out);

            }else if (v_process.equals("selectCourseSatisfactionExcel")) { // 정규(상시) 과정별 만족도 엑셀다운로드
                this.selectCourseSatisfactionExcel (request, response, box, out);
            }
            */
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
        	CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
            
        	if("go".equals(box.getString("p_action"))){
        		ArrayList<DataBox> list = bean.selectCourseStatisticList(box);
        		request.setAttribute("selectCourseStatisticList", list);
        	}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStatisticsNew_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch  to /learn/admin/statistics/za_CourseStatisticsNew_L.jsp");
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
        	CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
            
        	if("go".equals(box.getString("p_action"))){
        		ArrayList<DataBox> list = bean.selectCourseStatisticList(box);
        		request.setAttribute("selectCourseStatisticList", list);
        	}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStatisticsNew_E.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStatisticsNew_E.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("selectCourseStatisticExcel()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * 정규(상시) 과정별 통계 엑셀LowData 다운로드
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void selectCourseStatisticExcelLowData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		
    		request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
    		CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
    		
    		if("go".equals(box.getString("p_action"))){
    			ArrayList<DataBox> list = bean.selectCourseStatisticLowDataList(box);
    			request.setAttribute("selectCourseStatisticLowDataList", list);
    		}
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStatisticsNew_LE.jsp");
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStatisticsNew_LE.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("selectCourseStatisticExcelLowData()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * 상세팝업
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void selectCourseStatisticView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
//        	CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
            
//        	if("go".equals(box.getString("p_action"))){
//        		ArrayList<DataBox> list = bean.selectCourseStatisticList(box);
//        		request.setAttribute("selectCourseStatisticList", list);
//        	}
//        	System.out.println("test   a");
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStatisticsNew_D.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStatisticsNew_D.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseStatisticList()\r\n" + ex.getMessage());
        }
    }

    /**
     *  정규(상시) 수료기준+만족도 통계
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performCourseGraduatedList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
        	CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
            
        	if("go".equals(box.getString("p_action"))){
        		ArrayList<DataBox> list = bean.selectCourseGraduatedList(box);
        		request.setAttribute("selectCourseGraduatedList", list);
        	}
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseGraduated_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseGraduated_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseSatisfactionList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 정규(상시) 과정별 수료통계 엑셀 다운로드
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void selectCourseGraduatedExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		
    		request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
    		CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
    		
    		if("go".equals(box.getString("p_action"))){
    			ArrayList<DataBox> list = bean.selectCourseGraduatedList(box);
    			request.setAttribute("selectCourseGraduatedList", list);
    		}
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseGraduated_E.jsp");
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseGraduated_E.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("selectCourseSatisfactionExcel()\r\n" + ex.getMessage());
    	}
    }

    /**
     * 정규(상시) 과정별 수료통계 엑셀LowData 다운로드
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void selectCourseGraduatedExcelLowData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		
    		request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
    		CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
    		
    		if("go".equals(box.getString("p_action"))){
    			ArrayList<DataBox> list = bean.selectCourseGraduatedLowDataList(box);
    			request.setAttribute("selectCourseGraduatedLowDataList", list);
    		}
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseGraduated_LE.jsp");
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseGraduated_LE.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("selectCourseGraduatedExcelLowData()\r\n" + ex.getMessage());
    	}
    }
    /*
    private void performCourseSatisfactionList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

        	request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
        	CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
            
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
    		CourseStatisticsNewBean bean = new CourseStatisticsNewBean();
    		
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
    */
}
