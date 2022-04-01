//**********************************************************
//	1. 제      목: 이벤트정보 수정 제어하는 서블릿(사용자)
//	2. 프로그램명 : EventInfoServlet.java
//	3. 개      요: 이벤트 제어 프로그램(사용자)
//	4. 환      경: JDK 1.3
//	5. 버      젼: 1.0
//	6. 작      성: 조재형 2008. 10. 07
//	7. 수     정1:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.EventInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.EventInfoServlet")
public class EventInfoServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
		MultipartRequest multi = null;
		RequestBox box = null;
		String v_process = "";
		int fileupstatus = 0;

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getString("p_process");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			if(v_process.equals("eventReg")){                      // 이벤트 등록
				this.performEventInsertPage(request, response, box, out);
			} else if(v_process.equals("eventChk")){               // 이벤트 체크
				this.performEventChkPage(request, response, box, out);
			}
			
		} catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

    /**
   이벤트 사용자등록
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performEventInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);        // 명시적으로 box 객체를 넘겨준다

           EventInfoBean bean = new EventInfoBean();
           int isOk = bean.insertEventData(box);          

           String v_msg = "";
           String v_url = "/servlet/controller.homepage.MainServlet";
           box.put("p_process", "");

           AlertManager alert = new AlertManager();
 
           if(isOk < 1) {
              v_msg = "이벤트 등록에 실패했습니다.";
            	alert.alertOkMessage(out, v_msg, v_url , box);
           }

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/event/20081105/event_main.jsp");
           rd.forward(request, response);
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performLoginPage()\r\n" + ex.getMessage());
       }
   }

    /**
   이벤트 사용자 체크
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performEventChkPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
       	
           request.setAttribute("requestbox", box);        // 명시적으로 box 객체를 넘겨준다

           EventInfoBean bean = new EventInfoBean();
           int isOk = bean.insertEventData(box);
           
           box.put("p_userCnt", String.valueOf(isOk));

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/member/gu_idEventChk.jsp");
           rd.forward(request, response);
           
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performLoginPage()\r\n" + ex.getMessage());
       }
   }

}