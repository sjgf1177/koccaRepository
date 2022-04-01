//**********************************************************
//1. 제      목: 온라인테스트 그룹관리
//2. 프로그램명: ETestGroupServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package controller.etest;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestGroupBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.etest.ETestGroupServlet")
public class ETestGroupServlet extends HttpServlet implements Serializable {
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
            v_process = box.getStringDefault("p_process", "ETestGroupListPage");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ETestGroupServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ETestGroupListPage")) {                            //온라인테스트 그룹 리스트 
                    this.performETestGroupListPage(request, response, box, out);
            } else if (v_process.equals("ETestGroupInsertPage")) {                   //온라인테스트 그룹 등록페이지로 이동할때
                    this.performETestGroupInsertPage(request, response, box, out);
            } else if (v_process.equals("ETestGroupUpdatePage")) {                   //온라인테스트 그룹 수정페이지로 이동할때
                    this.performETestGroupUpdatePage(request, response, box, out);
            } else if (v_process.equals("ETestGroupInsert")) {                       //온라인테스트 그룹 등록할때
                    this.performETestGroupInsert(request, response, box, out);
            } else if (v_process.equals("ETestGroupUpdate")) {                      //온라인테스트 그룹 수정하여 저장할때
                    this.performETestGroupUpdate(request, response, box, out);
            } else if (v_process.equals("ETestGroupDelete")) {                      // 온라인테스트 그룹 삭제할때
                    this.performETestGroupDelete(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    온라인테스트 그룹 리스트 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestGroupListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestGroup_L.jsp";

	        ETestGroupBean bean = new ETestGroupBean();
            ArrayList list1 = bean.selectETestGroupList(box);
            request.setAttribute("ETestGroupList", list1);
//ErrorManager.systemOutPrintln(box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestGroupListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 그룹 등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestGroupInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestGroup_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestGroupInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 그룹 수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestGroupUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestGroup_U.jsp";

            ETestGroupBean bean = new ETestGroupBean();
            DataBox dbox = bean.selectETestGroupData(box);
            request.setAttribute("ETestGroupData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response); 
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestGroupUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 그룹 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestGroupInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try{
            String v_url  = "/servlet/controller.etest.ETestGroupServlet";

	        ETestGroupBean bean = new ETestGroupBean();
            int isOk = bean.insertETestGroup(box);

            String v_msg = "";
            box.put("p_process", "ETestGroupInsertPage");
		    box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                    v_msg = "insert.ok";
                    alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                    v_msg = "insert.fail";
                    alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestGroupInsert()\r\n" + ex.getMessage());
        }
    }

     /**
    온라인테스트 그룹 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestGroupUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestGroupServlet";

	        ETestGroupBean bean = new ETestGroupBean();
            int isOk = bean.updateETestGroup(box);

            String v_msg = "";
            box.put("p_process", "ETestGroupInsertPage");
		    box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                    v_msg = "update.ok";
                    alert.alertOkMessage(out, v_msg, v_url , box);
						}else if(isOk==-2){                
							v_msg = "해당 그룹은 사용중입니다.";   
							alert.alertFailMessage(out, v_msg);	    				                    
            }else {
                    v_msg = "update.fail";
                    alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestGroupUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 그룹 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestGroupDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestGroupServlet";

	        ETestGroupBean bean = new ETestGroupBean();
            int isOk = bean.deleteETestGroup(box);

            String v_msg = "";
            box.put("p_process", "ETestGroupInsertPage");
		    box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                    v_msg = "delete.ok";
                    alert.alertOkMessage(out, v_msg, v_url , box);
			}else if(isOk==-2){                
				v_msg = "해당 그룹은 사용중입니다.";   
				alert.alertFailMessage(out, v_msg);	                    
            }else {
                    v_msg = "delete.fail";
                    alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestGroupDelete()\r\n" + ex.getMessage());
        }
    }

}