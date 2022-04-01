//**********************************************************
//1. 제      목: 커뮤니티 전체 공지사항을 제어하는 서블릿
//2. 프로그램명: CommunityAdminNoticeServlet.java
//3. 개      요: 커뮤니티 전체 공지사항의 페이지을 제어한다
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: mscho 2004. 02. 17
//7. 수      정:
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityCategoryBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.community.CommunityAdminCategoryServlet")
public class CommunityAdminCategoryServlet extends javax.servlet.http.HttpServlet {

	/**
	* DoGet
	* Pass get requests through to PerformTask
	*/
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
	
	        String path = request.getServletPath();
	
	        v_process = box.getString("p_process");
	
	        if(ErrorManager.isErrorMessageView()) {
	            box.put("errorout", out);
	        }
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////            
	        box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
	
	        if(v_process.equals("insertPage")) {          //  등록페이지로 이동할때
				this.performInsertPage(request, response, box, out);
	        }
	        else if(v_process.equals("insert")) {         //  등록할때
	            this.performInsert(request, response, box, out);
	        }
	        else if(v_process.equals("updatePage")) {     //  수정페이지로 이동할때
	            this.performUpdatePage(request, response, box, out);
	        }
	        else if(v_process.equals("update")) {         //  수정하여 저장할때
	            this.performUpdate(request, response, box, out);
	        }
	        else if(v_process.equals("delete")) {         //  삭제할때
	            this.performDelete(request, response, box, out);
	        }
	        else if(v_process.equals("select")) {         //  상세보기할때
	            this.performSelect(request, response, box, out);
	        }
	        else if(v_process.equals("selectList")) {     //  리스트보기할때
	            this.performSelectList(request, response, box, out);
	        }            
	    }catch(Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	    }
	}
	
	/**
	리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
			
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
			CommunityCategoryBean bean = new CommunityCategoryBean();
	        ArrayList list = bean.selectCodeType_L("0052", box); //커뮤니티 분류를 읽어온다.
			
	        request.setAttribute("selectCommunityCategoryList", list);
	
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityCategory_L.jsp");
	        rd.forward(request, response);
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
	public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
	        
			CommunityCategoryBean bean = new CommunityCategoryBean();            
	        //BoardData data = bean.selectBoard(box);
			DataBox dbox = bean.selectBoard(box);
	        request.setAttribute("selectCommunityCategoryView", dbox);
			        
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityCategory_R.jsp");
	        rd.forward(request, response);
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelect()\r\n" + ex.getMessage());
	    }
	}
	
	 /**
	등록페이지로 이동할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	    throws Exception {
	    try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
	        
			CommunityCategoryBean bean = new CommunityCategoryBean();            
	        //BoardData data = bean.selectBoard(box);
			ArrayList list = bean.selectLevels("1", box.getString("p_grtype"));		// 1차 대분류만 가져오기.
	        request.setAttribute("selectCommunityCategoryLevels", list);
			      
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityCategory_I.jsp");
	        rd.forward(request, response);
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performInsertPage()\r\n" + ex.getMessage());
	    }
	}
	
	 /**
	등록할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	    throws Exception {
	    try {
			CommunityCategoryBean bean = new CommunityCategoryBean();
	        int isOk = bean.insertBoard(box);
	
	        String v_msg = "";
	        String v_url = "/servlet/controller.community.CommunityAdminCategoryServlet";
	        box.put("p_process", "selectList");
	
	        AlertManager alert = new AlertManager();
	
	        if(isOk > 0) {
	            v_msg = "insert.ok";
	            alert.alertOkMessage(out, v_msg, v_url , box);
	        }
	        else {
	            v_msg = "insert.fail";
	            alert.alertFailMessage(out, v_msg);
	        }
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performInsert()\r\n" + ex.getMessage());
	    }
	}
	
	 /**
	수정페이지로 이동할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
			
			CommunityCategoryBean bean = new CommunityCategoryBean();            
	        //BoardData data = bean.selectBoard(box);
			ArrayList list = bean.selectLevels("1", box.getString("p_grtype"));		// 1차 대분류만 가져오기.
	        request.setAttribute("selectCommunityCategoryLevels", list);
			
	
			CommunityCategoryBean bean1 = new CommunityCategoryBean();
	        DataBox dbox1 = bean.selectBoard(box);
	        request.setAttribute("selectCommunityCategoryView", dbox1);
	
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/community/za_CommunityCategory_U.jsp");
	        rd.forward(request, response);
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
	    }
	}
	
	 /**
	수정하여 저장할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	    throws Exception {
	     try {
			 CommunityCategoryBean bean = new CommunityCategoryBean();
	        int isOk = bean.updateBoard(box);
	
	        String v_msg = "";
	        String v_url  = "/servlet/controller.community.CommunityAdminCategoryServlet";
	        box.put("p_process", "selectList");
	        //      수정 후 해당 리스트 페이지로 돌아가기 위해
	
	        AlertManager alert = new AlertManager();
	        if(isOk > 0) {
	            v_msg = "update.ok";
	            alert.alertOkMessage(out, v_msg, v_url , box);
	        }
	        else {
	            v_msg = "update.fail";
	            alert.alertFailMessage(out, v_msg);
	        }
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performUpdate()\r\n" + ex.getMessage());
	    }
	}
	
	 /**
	삭제할때
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        CommunityCategoryBean bean = new CommunityCategoryBean();
	        int isOk = bean.deleteBoard(box);
	
	        String v_msg = "";
	        String v_url  = "/servlet/controller.community.CommunityAdminCategoryServlet";
	        box.put("p_process", "selectList");
	
	        AlertManager alert = new AlertManager();
	
	        if(isOk > 0) {
	            v_msg = "delete.ok";
	            alert.alertOkMessage(out, v_msg, v_url , box);
	        }
	        else {
	            v_msg = "delete.fail";
	            alert.alertFailMessage(out, v_msg);
	        }
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performUpdate()\r\n" + ex.getMessage());
	    }
	}
	
	
	public void errorPage(RequestBox box, PrintWriter out) 
	    throws Exception {       
	    try {                         
	        box.put("p_process", "selectList");
	        
	        AlertManager alert = new AlertManager();
	
	        alert.alertFailMessage(out, "이 프로세스로 진행할 권한이 없습니다.");
	        //  Log.sys.println();
	
	    }catch (Exception ex) {           
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("errorPage()\r\n" + ex.getMessage());
	    }
	}
}

