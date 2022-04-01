package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

import com.credu.course.EduSystemChartBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;



/**
 * 교육체계도 관리
 */
//@SuppressWarnings({"unchecked", "serial"})
@WebServlet("/servlet/controller.course.EduSystemChartServlet")
public class EduSystemChartServlet extends HttpServlet implements Serializable {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

	
			
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";
		
		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process", "listPage");
			
			if (ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
			
			if (!AdminUtil.getInstance().checkRWRight("EduSystemChartServlet", v_process, out, box)) {
				return;
			}
			
			
			//교육체계도 목록화면
			if(v_process.equals("listPage")) {
				this.performListPage(request, response, box, out);
				
			//교육체계도 분류 등록 화면
			}else if(v_process.equals("insertPage")) {
				this.performInsertPage(request, response, box, out);
				
			//교육체계도 분류 등록
			}else if(v_process.equals("insert")) {
				this.performInsert(request, response, box, out);
				
			//교육체계도 분류 수정 화면
			}else if(v_process.equals("updatePage")) {
				this.performUpdatePage(request, response, box, out);
				
			//교육체계도 분류 수정
			}else if(v_process.equals("update")) {
				this.performUpdate(request, response, box, out);
				
			//교육체계도 분류 삭제
			}else if(v_process.equals("delete")) {
				this.performDelete(request, response, box, out);
				
			//상,하위 교육체계분류코드 목록
			}else if(v_process.equals("uLChartCodeList")) {
				this.performULChartCodeList(request, box, out);
				
			//교육체계도 과정연결 화면
			}else if(v_process.equals("subjcourse")) {
				this.performSubjCoursePage(request, response, box, out);
				
			//교육체계도 과정연결
			}else if(v_process.equals("subjcourseUpdate")) {
				this.performSubjCourseUpdate(request, response, box, out);
				
				
				
			
			}
		
	
	
	
	
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	
	
	/**
	 * 교육체계도 목록화면
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			EduSystemChartBean bean = new EduSystemChartBean();
			
			//교육체계도 목록
			ArrayList resultList = bean.list(box);
			request.setAttribute("resultList", resultList);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_EduSystemChart_L.jsp");
			rd.forward(request, response);
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	 * 교육체계도 분류 등록 화면
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			EduSystemChartBean bean = new EduSystemChartBean();
			
			//최상위 교육체계분류코드 목록
			ArrayList uLChartCodeList = bean.uLChartCodeList(box);
			request.setAttribute("uLChartCodeList", uLChartCodeList);
			
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_EduSystemChart_I.jsp");
			rd.forward(request, response);
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	 * 교육체계도 분류 등록
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception{
		try{

			EduSystemChartBean bean = new EduSystemChartBean();
			AlertManager alert = new AlertManager();
	
			int result = bean.insert(box);
	
			String v_msg = "";
			String v_url = "/servlet/controller.course.EduSystemChartServlet";
			box.put("p_process", "listPage");
	
			if (result > 0 ) { 
				v_msg = "save.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
				
			}else { 
				v_msg = "save.fail";
				alert.alertFailMessage(out, v_msg);
			}

		} catch( Exception ex ){
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace( ex, out );
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}
	
	
	
	/**
	 * 교육체계도 분류 수정 화면
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			EduSystemChartBean bean = new EduSystemChartBean();
			
			//교육체계분류코드 정보
			DataBox data = bean.view(box);
			request.setAttribute("data", data);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_EduSystemChart_U.jsp");
			rd.forward(request, response);
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	 * 교육체계도 분류 수정
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception{
		try{
			
			EduSystemChartBean bean = new EduSystemChartBean();
			AlertManager alert = new AlertManager();
			
			int result = bean.update(box);
			
			String v_msg = "";
			String v_url = "/servlet/controller.course.EduSystemChartServlet";
			box.put("p_process", "updatePage");
			
			if (result > 0 ) { 
				v_msg = "save.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
				
			}else { 
				v_msg = "save.fail";
				alert.alertFailMessage(out, v_msg);
			}
			
		} catch( Exception ex ){
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace( ex, out );
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	 * 교육체계도 분류 삭제
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception{
		try{
			
			EduSystemChartBean bean = new EduSystemChartBean();
			AlertManager alert = new AlertManager();
			
			int result = bean.delete(box);
			
			String v_msg = "";
			String v_url = "/servlet/controller.course.EduSystemChartServlet";
			box.put("p_process", "listPage");
			
			if (result > 0 ) { 
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
				
			}else if(result == -1){
				v_msg = box.getString("system_msg");
				alert.alertFailMessage(out, v_msg);
				
			}else { 
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}
			
		} catch( Exception ex ){
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace( ex, out );
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}

	
	
	/**
	 * 상,하위 교육체계분류코드 목록
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 */
	public void performULChartCodeList(HttpServletRequest request, RequestBox box, PrintWriter out){ 
		try {
			request.setAttribute("requestbox", box);
			EduSystemChartBean bean = new EduSystemChartBean();
			
			//하위 교육체계분류코드 목록
			ArrayList uLChartCodeList = bean.uLChartCodeList(box);
			
			String jsonString = "";
			if(uLChartCodeList != null && uLChartCodeList.size() > 0){
				jsonString = JSONValue.toJSONString(uLChartCodeList);
			}
			out.write(jsonString);
			
		} catch (Exception e) {
			ErrorManager.getErrorStackTrace(e, out);
		}
	}
	
	
	
	/**
	 * 교육체계도 과정연결 화면
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performSubjCoursePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			EduSystemChartBean bean = new EduSystemChartBean();
			
			//B2C 전체과정 목록
			ArrayList subjAll = bean.subjAll(box);
			request.setAttribute("subjAll", subjAll);
			
			//교육체계도와 연결된 과정 목록
			ArrayList subjSelect = bean.subjSelect(box);
			request.setAttribute("subjSelect", subjSelect);
			
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_EduSystemChart_R.jsp");
			rd.forward(request, response);
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjCoursePage()\r\n" + ex.getMessage());
		}
	}
	
	
	/**
	 * 교육체계도 과정연결
	 * @param request
	 * @param response
	 * @param box
	 * @param out
	 * @throws Exception
	 */
	public void performSubjCourseUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception{
		try{
			
			EduSystemChartBean bean = new EduSystemChartBean();
			AlertManager alert = new AlertManager();
			
			int result = bean.subjCourseUpdate(box);
			
			String v_msg = "";
			String v_url = "/servlet/controller.course.EduSystemChartServlet";
			box.put("p_process", "subjcourse");
			
			if (result > 0 ) { 
				v_msg = "save.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
				
			}else if(result == -1){
				v_msg = box.getString("system_msg");
				alert.alertFailMessage(out, v_msg);
				
			}else { 
				v_msg = "save.fail";
				alert.alertFailMessage(out, v_msg);
			}
			
		} catch( Exception ex ){
			ex.printStackTrace();
			ErrorManager.getErrorStackTrace( ex, out );
			throw new Exception("performSubjCourseUpdate()\r\n" + ex.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
