//**********************************************************
//1. 제      목: 회원가입 제어하는 서블릿
//2. 프로그램명 : KMemberJoinServlet.java
//3. 개      요: 회원가입 제어 프로그램
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 이나연 05.12.16
//7. 수     정1:
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.LoginBean;
import com.credu.homepage.MemberJoinBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.KMemberJoinServlet")
public class KMemberJoinServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
		v_process = box.getStringDefault("p_process","join");

      box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
	  box.setSession("grtype","KOCCA");

      if(v_process.equals("join")) {             
          this.performJoin(request, response, box, out);
      }else if(v_process.equals("checkResno")) {   			// 회원가입 여부
          this.performCheckResno(request, response, box, out);
      }else if(v_process.equals("checkId")) {   				// ID 중복체크
          this.performCheckID(request, response, box, out);
      }else if(v_process.equals("joinOk")) {   				// 회원가입
          this.performJoinOk(request, response, box, out);  
      }
	  
	  else if(v_process.equals("user")) {   				// 이용약관
          this.performUserOk(request, response, box, out);  
      }
	  else if(v_process.equals("personal")) {   				// 개인보호정책
          this.performPersonal(request, response, box, out);  
      }
  }catch(Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
  }
}


/**
LOGIN PROCESS
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performJoin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {
      String v_url = "";
      v_url  = "/learn/user/kocca/member/ku_Member.jsp";
      box.put("p_process", "");

      String v_userip = request.getRemoteAddr();
      box.put("p_userip", v_userip);
		
		request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher(v_url);
      rd.forward(request, response);
             
  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performInsertLogin()\r\n" + ex.getMessage());
  }
}


/**
회원가입 확인여부
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/

public void performCheckResno(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {
	  String v_kor_name = box.getString("p_kor_name");
	  String v_resno = box.getString("p_resno");
      String v_msg = "";

      AlertManager alert = new AlertManager();
      MemberJoinBean bean = new MemberJoinBean();
      int is_Ok = bean.checkResno(box);

	  String isOk = String.valueOf(is_Ok);

	  if(isOk.equals("0")) {
		    box.setSession("p_name",v_kor_name);
			box.setSession("p_resno",v_resno);
		  
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/member/ku_MemberJoin.jsp");
			rd.forward(request, response);
      }
      else {
          v_msg = "이미 가입된 회원입니다";
			alert.alertFailMessage(out, v_msg);
      }

      Log.info.println(this, box, v_msg + " on MemberJoinServlet");
  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performSendMail()\r\n" + ex.getMessage());
  }
}

/**
ID 중복체크 
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/

public void performCheckID(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
 try {
     MemberJoinBean bean = new MemberJoinBean();
     int is_Ok = bean.checkID(box);
	   String v_id = box.getString("p_userid");

       String v_msg = "";
	   String v_url = "/learn/user/kocca/member/ku_idCheck.jsp";
	   
     AlertManager alert = new AlertManager();
    
		    box.put("isOk",String.valueOf(is_Ok));
		   
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	        rd.forward(request, response);

     Log.info.println(this, box, v_msg + " on MemberJoinServlet");
 }catch (Exception ex) {
     ErrorManager.getErrorStackTrace(ex, out);
     throw new Exception("performSendMail()\r\n" + ex.getMessage());
 }
}

/**
회원등록
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performJoinOk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {
		String v_msg ="";
		String v_url  = "/servlet/controller.homepage.MainServlet";
		
      AlertManager alert = new AlertManager();

	  	   MemberJoinBean bean = new MemberJoinBean();
	       int is_Ok = bean.insertMember(box);
		   
		if(is_Ok == 1){
          v_msg = "회원 가입되셨습니다";
			alert.alertOkMessage(out,v_msg,v_url,box);

		}else{
          v_msg = "회원등록에 실패하였습니다";
			alert.alertFailMessage(out, v_msg);
		}

  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performSendResult()\r\n" + ex.getMessage());
  }
}

/**
이용약관
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performUserOk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {
      String v_url  = "/learn/user/kocca/member/ku_UserAgree.jsp";

	  request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher(v_url);
      rd.forward(request, response);

  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performSendResult()\r\n" + ex.getMessage());
  }
}


/**
개인보호정책
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performPersonal(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {
      String v_url  = "/learn/user/kocca/member/ku_Personal.jsp";

	  request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher(v_url);
      rd.forward(request, response);

  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performSendResult()\r\n" + ex.getMessage());
  }
}

//==========================================  새로 만든 메서드  ====================================================================


/**
처음 로긴했을 때 팝업 띄우는 페이지로
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performFirstLoginPagePre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {
      request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

      // 가로460 세로 324입니다
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_FirstLogin_P.jsp");
      rd.forward(request, response);

      Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_FirstLogin_P.jsp");

  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performFirstLoginPagePre()\r\n" + ex.getMessage());
  }
}


/**
처음 로긴했을 때 페이지로
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performFirstLoginPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {
      request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
      LoginBean bean = new LoginBean();
      String v_email = bean.emailOpen(box);
      box.put("p_email",v_email);
      
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_FirstLogin_I.jsp");
      rd.forward(request, response);

      Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_FirstLogin_I.jsp");

  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performFirstLoginPage()\r\n" + ex.getMessage());
  }
}


/**
처음 로긴처리
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performFirstLogin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
  try {


      LoginBean bean = new LoginBean();
      int isOk = bean.firstLogin(box);

      String v_msg = "";
      String v_url = "/servlet/controller.homepage.MemberInfoServlet";
      box.put("p_process", "memberUpdatePage");
      AlertManager alert = new AlertManager();

      if(isOk > 0) {
          v_msg = "정보이용동의서에 동의하셧습니다";
          alert.alertOkMessage(out, v_msg, v_url , box, true, true);
//          alert.selfClose(out, v_msg);
      }
      else {
          v_msg = "실패했습니다.";
//          alert.selfClose(out, v_msg);
          alert.alertFailMessage(out, v_msg);
      }

      Log.info.println(this, box, v_msg + " on LoginServlet");
  }catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, out);
      throw new Exception("performSendMail()\r\n" + ex.getMessage());
  }
}

}