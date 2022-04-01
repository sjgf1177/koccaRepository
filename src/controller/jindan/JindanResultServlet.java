//**********************************************************
//1. 제      목: 진단평가 대상자관리
//2. 프로그램명: JindanResultServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: 
//**********************************************************

package controller.jindan;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.jindan.JindanResultBean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

@WebServlet("/servlet/controller.jindan.JindanResultServlet")
public class JindanResultServlet extends HttpServlet implements Serializable {
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
			if (!v_process.equals("JindanGubunResult"))
			{
	            if (!AdminUtil.getInstance().checkRWRight("JindanResultServlet", v_process, out, box)) {
					return;
	            }
			}

            if (v_process.equals("JindanResultListPage")) {                    // 진단테스트 결과분석
                this.performJindanResultListPage(request, response, box, out);
            
			}else if(v_process.equals("JindanResultDetail")){						// 진단테스트 결과보기
	            this.performJindanResultDetail(request, response, box, out);	
			}
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    
    /**
    진단테스트  결과분석리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performJindanResultListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/jindan/za_JindanResult_L.jsp";
            
			JindanResultBean bean = new JindanResultBean();
      
			ArrayList list1 = bean.SelectReaultList(box); 
			request.setAttribute("JindanResultAdminList", list1);

 

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
			
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanResultListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    진단테스트 결과분석 상세팝업창 
    **/	
    public void performJindanResultDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/jindan/za_JindanResult_R.jsp";
			
            request.setAttribute("p_upperclass", box.getString("p_upperclass"));
            request.setAttribute("p_middleclass", box.getString("p_middleclass"));
            request.setAttribute("p_lowerclass", box.getString("p_lowerclass"));
			request.setAttribute("p_jindanDate", FormatDate.getFormatDate(box.getString("p_jindanDate"),"yyyy.MM.dd")  );	//진단일				
			
			JindanResultBean bean = new JindanResultBean();
			ArrayList list = bean.SelectJindanResultDetail(box);
            request.setAttribute("JindanResultAdminDetail", list);

			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
			
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJindanUserResultView()\r\n" + ex.getMessage());
        }
    }	
       
}