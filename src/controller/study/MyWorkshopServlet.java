//**********************************************************
//1. ��      ��: ���� ��ũ��
//2. ���α׷���: MyWorkshopServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.5
//5. ��      ��: 1.0
//6. ��      ��: 2009.11.27
//7. ��      ��:
//
//**********************************************************

package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.QnaAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.MyWorkshopBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.study.MyWorkshopServlet")
public class MyWorkshopServlet extends HttpServlet implements Serializable {
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
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }
            
            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }            

            /*
            if(box.getSession("userid").equals("")){
            	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }
            */

            if (v_process.equals("MyWorkshopListPage")) {                            // ���� ��ũ�� ����Ʈ
                this.performMyWorkshopListPage(request, response, box, out);
            } else if (v_process.equals("MyWorkshopViewPage")) {                      // ���� ��ũ��  ��û ��� �󼼺���
                this.performMyWorkshopViewPage(request, response, box, out);
            }                  

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ���� ��ũ�� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performMyWorkshopListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_url = "/learn/user/portal/study/gu_MyWorkshop_L.jsp";
            
            MyWorkshopBean bean = new MyWorkshopBean();
            ArrayList list1 = bean.SelectMyWorkshopList(box);                        
            request.setAttribute("MyWorkshopListPage", list1);

            ServletContext sc    = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
          Log.info.println(this, box, v_url);            
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyWorkshopListPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    ���� ��ũ�� �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMyWorkshopViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            MyWorkshopBean bean = new MyWorkshopBean();
            DataBox dbox = bean.selectMyWorkshop(box);
            
            request.setAttribute("MyWorkshopViewPage", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/gu_MyWorkshopResult_P.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    } 
    
}