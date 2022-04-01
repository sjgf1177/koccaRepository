//*********************************************************
//  1. 제      목: 오프라인 수강신청 결제
//  2. 프로그램명: OffBillServlet.java
//  3. 개      요: 오프라인 수강신청 결제 servlet
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.1.18
//  7. 수      정:
//**********************************************************
package controller.bill;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.Bill.OffBillBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({"unchecked", "serial"})
public class OffBillServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
			OffBillBean bean = new OffBillBean();
			String v_msg = "";

			//String v_url ="/learn/user/portal/course/gu_OffPayResult_P.jsp";

			int isOk = 0;
			isOk = bean.billStart(box);

			if (isOk > 0) {
		        ArrayList list = bean.selectOffSecurePayInfo(box);
		        request.setAttribute("offsecurepayInfo", list);
			}
			
			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				ServletContext sc = getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_OffSecurePayResult_P.jsp");
				rd.forward(request, response);
				//				v_msg = "propose.ok";
				//				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "propose.fail";
				v_msg = box.getStringDefault("err_msg",v_msg);
				alert.alertFailMessage(out, v_msg);

				ServletContext sc = getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_OffSecurePayError_P.jsp");
				rd.forward(request, response);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSecurepay()\r\n" + ex.getMessage());
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