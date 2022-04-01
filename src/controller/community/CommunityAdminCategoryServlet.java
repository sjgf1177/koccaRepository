//**********************************************************
//1. ��      ��: Ŀ�´�Ƽ ��ü ���������� �����ϴ� ����
//2. ���α׷���: CommunityAdminNoticeServlet.java
//3. ��      ��: Ŀ�´�Ƽ ��ü ���������� �������� �����Ѵ�
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: mscho 2004. 02. 17
//7. ��      ��:
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
	
	        if(v_process.equals("insertPage")) {          //  ����������� �̵��Ҷ�
				this.performInsertPage(request, response, box, out);
	        }
	        else if(v_process.equals("insert")) {         //  ����Ҷ�
	            this.performInsert(request, response, box, out);
	        }
	        else if(v_process.equals("updatePage")) {     //  ������������ �̵��Ҷ�
	            this.performUpdatePage(request, response, box, out);
	        }
	        else if(v_process.equals("update")) {         //  �����Ͽ� �����Ҷ�
	            this.performUpdate(request, response, box, out);
	        }
	        else if(v_process.equals("delete")) {         //  �����Ҷ�
	            this.performDelete(request, response, box, out);
	        }
	        else if(v_process.equals("select")) {         //  �󼼺����Ҷ�
	            this.performSelect(request, response, box, out);
	        }
	        else if(v_process.equals("selectList")) {     //  ����Ʈ�����Ҷ�
	            this.performSelectList(request, response, box, out);
	        }            
	    }catch(Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	    }
	}
	
	/**
	����Ʈ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
			
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			CommunityCategoryBean bean = new CommunityCategoryBean();
	        ArrayList list = bean.selectCodeType_L("0052", box); //Ŀ�´�Ƽ �з��� �о�´�.
			
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
	�󼼺���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
	        
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
	����������� �̵��Ҷ�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	    throws Exception {
	    try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
	        
			CommunityCategoryBean bean = new CommunityCategoryBean();            
	        //BoardData data = bean.selectBoard(box);
			ArrayList list = bean.selectLevels("1", box.getString("p_grtype"));		// 1�� ��з��� ��������.
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
	����Ҷ�
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
	������������ �̵��Ҷ�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			
			CommunityCategoryBean bean = new CommunityCategoryBean();            
	        //BoardData data = bean.selectBoard(box);
			ArrayList list = bean.selectLevels("1", box.getString("p_grtype"));		// 1�� ��з��� ��������.
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
	�����Ͽ� �����Ҷ�
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
	        //      ���� �� �ش� ����Ʈ �������� ���ư��� ����
	
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
	�����Ҷ�
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
	
	        alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
	        //  Log.sys.println();
	
	    }catch (Exception ex) {           
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("errorPage()\r\n" + ex.getMessage());
	    }
	}
}

