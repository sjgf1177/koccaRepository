//*********************************************************
//  1. 제      목: SUBJECT INFORMATION USER SERVLET
//  2. 프로그램명: BillServlet.java
//  3. 개      요: 과정안내 사용자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 19
//  7. 수      정:
//**********************************************************
package controller.bill;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.Bill.BillBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({"unchecked", "serial"})
public class BillServlet extends javax.servlet.http.HttpServlet implements Serializable {
	/**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}
	/**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getString("p_process");

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			// 과정미리 보기 로그인 체크 안함 ( 추천과정에서 사용)

			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				this.LoginChk(request, response, box, out);
			}
			if(v_process.equals("securepay")){                           // 결제저장
				this.performSecurepay(request, response, box, out);
			}
			else if (v_process.equals("SubjectEduInputBill")){				// 무통장입금  페이지.
				this.performEduInputBill(request, response, box, out);
			}
			else if (v_process.equals("BillCheck")){					// 무통장입금  페이지 (입금자 입력).
				this.performEduInputCheck(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    SUBJECT LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void LoginChk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{

			request.setAttribute("tUrl",request.getRequestURI());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request,response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("SubjectList()\r\n" + ex.getMessage());
		}
	}

	/**
    결제
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSecurepay(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			BillBean bean = new BillBean();
			String v_msg = "";

			String v_url ="/learn/user/portal/propose/zu_Subject_Bill_Result.jsp";

			int isOk = 0;
			
			if( Integer.parseInt(box.get("totalPrice")) == 0 ) {
				isOk = bean.billStartZero(box);
			} else if( Integer.parseInt(box.get("totalPrice")) > 0) {
				isOk = bean.billStart(box);
			} else {
				isOk = -1;
			}

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				ServletContext sc = getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher(v_url);
				rd.forward(request, response);
				//				v_msg = "propose.ok";
				//				alert.alertOkMessage(out, v_msg, v_url , box);
			} else {
				v_msg = "propose.fail";
				v_msg = box.getStringDefault("err_msg",v_msg);
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
		}
	}


	/**
    SUBJECT EDUCATION PROPOSE Bill(무통장입금)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performEduInputBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeInputBill_I.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
		}
	}

	/**
    SUBJECT EDUCATION PROPOSE Bill(무통장입금)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performEduInputCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeInputBill_Check.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
		}
	}

}