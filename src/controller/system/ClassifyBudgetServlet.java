//**********************************************************
//1. ��      ��: ��з� ���꼳�� SERVLET
//2. ���α׷���: ToeicStatServlet.java
//3. ��      ��: ��з� ���꼳�� SERVLET
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: ���� 2005. 02. 22
//7. ��      ��: 
//                 
//**********************************************************
package controller.system;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.ClassifyBudgetBean;

@WebServlet("/servlet/controller.system.ClassifyBudgetServlet")
public class ClassifyBudgetServlet extends HttpServlet implements Serializable {

	/**
	Pass get requests through to PerformTask
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet    
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

	/**
	doPost
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet    
	*/    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox  box = null;
		String v_process = "";
        
		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);            
			v_process = box.getStringDefault("p_process","listPage");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			///////////////////////////////////////////////////////////////////
			// ���� check ��ƾ VER 0.2 - 2003.08.10
			if (!AdminUtil.getInstance().checkRWRight("ClassifyBudgetServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("ListPage") || v_process.equals("")) { 		//���꼳�� �� ��Ȳ ��ȸ
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("selectListPage") || v_process.equals("")) {  //���꼳������
				this.performSelectListPage(request, response, box, out);
			} else if (v_process.equals("saveInfo") || v_process.equals("")) {  //���꼳������
				this.performSaveInfo(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    �������� ���� �� ������û ��Ȳ ��ȸ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/system/za_ClassifyBudget_U.jsp";
            
            ClassifyBudgetBean bean = new ClassifyBudgetBean();
            DataBox dbox = bean.SelectBudgetInfo(box);
            request.setAttribute("BudgetInfo", dbox);
            
            ArrayList list = bean.SelectStudentList(box);
            request.setAttribute("StudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_ClassifyBudget_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    
	/**
    �������� ����Ʈ ��ȸ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/system/za_ClassifyBudget_L.jsp";
            
            ClassifyBudgetBean bean = new ClassifyBudgetBean();
            
            ArrayList list = bean.SelectBudgetList(box);
            request.setAttribute("SelectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_ClassifyBudget_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

	/**
    �������� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performSaveInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.system.ClassifyBudgetServlet";

            ClassifyBudgetBean bean = new ClassifyBudgetBean();

            int isOk = bean.updateBudgetInfo(box);

            String v_msg = "";
			String r_process = box.getString("r_process");
            box.put("p_process", r_process);

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "save.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "save.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on performSaveInfo");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSaveInfo()\r\n" + ex.getMessage());
        }
    }
    
    
	
}
