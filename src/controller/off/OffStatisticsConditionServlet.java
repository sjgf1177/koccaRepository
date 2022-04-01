package controller.off;

import java.io.PrintWriter;
import java.io.Serializable;
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
import com.credu.off.OffStatisticsConditionBean;
import com.credu.system.AdminUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 6. 17
 * Time: 오전 9:33:17
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffStatisticsConditionServlet")
public class OffStatisticsConditionServlet extends javax.servlet.http.HttpServlet implements Serializable {
    //@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}
    //@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getString("p_process");
			//v_process = box.getString("p_process").trim();
			
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("OffStatisticsConditionServlet", v_process, out, box)) {
			return; 
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
			
			if (v_process.equals("listPage")) {                            //in case of 차수 목록 화면
				this.performListPage(request, response, box, out);
			} else if(v_process.equals("jqueryList")) { //실제적으로 목록을 뿌리는 곳
                this.performJqueryList(request, response, box, out);
            }else if (v_process.equals("listPage1")) {                            //in case of 차수 목록 화면
				this.performListPage1(request, response, box, out);

			// 추가 	
            }else if(v_process.equals("selectCourseStat")) {   // 수강생 현황
	             this.performSelectCourseStat(request, response, box, out);
	        }else if(v_process.equals("selectAgeStat")) {   // 연령 현황
	             this.performSelectAgeStat(request, response, box, out);
	        }else if(v_process.equals("selectDetail")) {   // 상세보기
	             this.performSelectDetailStat(request, response, box, out);
	        }
			
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

    //목록 페이지 호출
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/off/za_off_StaticsNew_L.jsp";
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
    
  //목록 페이지 호출
    public void performListPage1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/off/za_off_Statics_L.jsp";
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

    //데이타 불러옴
    public void performJqueryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/off/za_jquery_statics.jsp";

            OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
            request.setAttribute("resultList", bean.listPage(box));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
    
    /**
	수강생 현황
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectCourseStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);
	        String v_return_url ="/learn/admin/off/za_CourseStatAjax_L.jsp";
	        
	        OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
	        ArrayList list = bean.selectOffCourseStatList(box);
	        request.setAttribute("selectList", list);
	        
	        /*if (box.getBoolean("isExcel")) {
				v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
				box.put("title", "과정별 검색");//엑셀 제목
                box.put("tname", "분야|과정명|교육인원|수료|미수료|만족도");//컬럼명
				box.put("tcode", "d_area|d_subjnm|d_user_cnt|d_grad_cnt|d_ngrad_cnt|d_sul_rate");//데이터이름
				box.put("resultListName", "selectList");//결과 목록
			}*/
	
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
	        rd.forward(request, response);
	
	        Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CousreStatAjax_L.jsp");
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
	    }
	}
	/**
	연령별 리스트(검색후)
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectAgeStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);
	        
	        String v_return_url = "/learn/admin/off/za_AgeStatAjax_L.jsp";
	        
	        OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
	        ArrayList list = bean.selectAgeList(box);
	        request.setAttribute("selectList", list);
	        
	        if (box.getBoolean("isExcel")) {
				v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
				box.put("title", "연령별 검색");//엑셀 제목
                box.put("tname", "차수명|분류|분야|전체|10대|20대|30대|40대|50대|60대|70대|미확인");//컬럼명
				box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_teens|d_twenty|d_thirty|d_fourty|d_fifty|d_sixty|d_seventy|d_johndoe");//데이터이름
				box.put("resultListName", "selectList");//결과 목록
			}
	
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
	        rd.forward(request, response);
	
	        Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_AgeStatAjax_L.jsp");
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
	    }
	}
	/**
	상세보기
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectDetailStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);
	        
	        
	        OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
	        ArrayList list = bean.selectDetailList(box);
	        request.setAttribute("selectList", list);
	        
	        String v_return_url = "/learn/admin/off/za_DetailStatAjax_L.jsp";
	        
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
	        rd.forward(request, response);
	
	        Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_AgeStatAjax_L.jsp");
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
	    }
	}
	
	
}
