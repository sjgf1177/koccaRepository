//**********************************************************
//  1. 제      목: 외주관리시스템 로그인 제어 서블릿
//  2. 프로그램명 : CpLoginServlet.java
//  3. 개      요: 로그인 제어 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 11.  14
//  7. 수     정1:
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.credu.cp.CpLoginBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.cp.CpLoginServlet1")
public class CpLoginServlet1 extends javax.servlet.http.HttpServlet implements Serializable {
 
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

			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("login")) {             //in case of login
                this.performLogin(request, response, box, out);
            }
            else if(v_process.equals("loginpage")) {   //in case of login page
                this.performLoginPagegari(request, response, box, out);
            }            
            else if(v_process.equals("logout")) {      //in case of logout
                this.performLogout(request, response, box, out);
            }
            else if(v_process.equals("losspwd")) {     //in case of loss password
                this.performLossPwd(request, response, box, out);
            }
            else if(v_process.equals("sendmail")) {    //in case of send mail
                this.performSendMail(request, response, box, out);
            }
            else if(v_process.equals("sendresult")) {    //in case of send result
                this.performSendResult(request, response, box, out);
            }
			else if (v_process.equals("authChange")) {
            	String v_auth = box.getString("p_auth");
				box.setSession("gadmin",v_auth);
            	this.performLoginPage(request, response, box, out);
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
    public void performLogin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            //String v_url = "/servlet/controller.homepage.MainServlet";
            String v_url = "/cp/index.jsp";
            box.put("p_process", "");
            
            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);
            
            CpLoginBean bean = new CpLoginBean();
            
                int isOk1 = bean.login(box);
                int isOk2 = 0;
                int isOk3 = 0;
            	
                if(isOk1 > 0) {
                    String s_userid = box.getSession("userid");
            
                    // 쿠키 세팅
                    String v_rememberId = box.getString("rememberId");
                    String v_userid = "";
                    if (v_rememberId.equals("1")) {
                        v_userid = URLEncoder.encode(s_userid);
                        Cookie useridCookie = new Cookie("userid_cp",v_userid);
                        useridCookie.setMaxAge(60*60*24*365);
                        response.addCookie(useridCookie);
                    } else {
            
                        Cookie useridCookie = new Cookie("userid_cp","");
                        useridCookie.setMaxAge(0);
                        response.addCookie(useridCookie);                   
                    }
                     
    				//v_msg = "login.ok";
                    //alert.alertOkMessage(out, v_msg, v_url , box);
                    
                    
                }else{
                    //v_url = "/servlet/controller.homepage.LoginServlet?leftmenu=1";
                    box.put("p_process", "loginPage");
                    v_msg = "다시한번 확인후 접속해주십시오.";
                
                    //alert.alertOkMessage(out, v_msg, v_url , box);
                    //alert.alertFailMessage(out, v_msg);
                }
                
				//frmURL이 있으면 해당 URL로 이동한다.
                String v_frmURL = box.getString("p_frmURL");
                //String v_frmURL = request.getParameter("p_frmURL");
                if (v_frmURL.equals("")) {
                	if (isOk1 > 0) {
                		alert.alertOkMessage(out, v_msg, v_url , box);
                	} else {
                		Log.info.println(this, box, v_msg + " on CpLoginServlet");
                		alert.alertOkMessage(out, v_msg, v_url , box);
                	}
                } else {
	                out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>");  
		            out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
		            out.println("<!--                      ");      
		            out.println("	function winonload() {");
		            out.println("		document.form1.submit();");
		            out.println("	}");
		            out.println("//-->");
		            out.println("</SCRIPT>");
		            out.println("<head>");
		            
		            out.println("<body onload='winonload()'>");
		            out.println("<form name = 'form1' method = 'post' action='"+v_frmURL+"'>");                  

	                Enumeration e = request.getParameterNames();
	                while (e.hasMoreElements()) {
	                    String parameterName  = (String)e.nextElement();
	                    String parameterValue = request.getParameter(parameterName);
	                    out.println("<INPUT type='hidden' name='" + parameterName + "' value='" + parameterValue + "'>");
	                    System.out.println("<INPUT type='hidden' name='" + parameterName + "' value='" + parameterValue + "'>");
	                }
	                out.println("</form>");
	                out.println("</body></html>");
	                out.flush();
                }
                	
                
/*          
            } else {
            // 처음 로그인했을 경우
                out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head><body>");  
                out.println("<form name = 'form1' method = 'post'>");           
                out.println("<input type = 'hidden' name = 'p_userid' value = '" + box.getString("p_userid") + "'>");
                out.println("<input type = 'hidden' name = 'p_process' value = ''>");               
                out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
                out.println("<!--                      ");      
                out.println("function firstLogin() {");
                out.println("window.self.name = \"winSelectView\";");               
                out.println("farwindow = window.open(\"\", \"firstLogin\", \"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no, width = 460, height = 380 top=0, left=0\");");
                out.println("document.form1.target = \"firstLogin\"");
                out.println("document.form1.p_process.value = \"firstLoginPage\"");
                out.println("document.form1.action = \"/servlet/controller.homepage.LoginServlet\";");
                out.println("document.form1.submit();");
                out.println("farwindow.window.focus();");
                out.println("document.form1.target = window.self.name;");                             
                out.println("}");
                out.println("firstLogin();");   
                out.println("//-->");
                out.println("</SCRIPT>");       
                out.println("</form>");
                out.println("</body></html>123123");
                out.flush();
            }
            
*/            
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertLogin()\r\n" + ex.getMessage());
        }
    }


    /**
    LOGOUT PROCESS
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performLogout(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = "/cp/index.jsp";
            box.put("p_process", "");

            if(true){
                HttpSession session = box.getHttpSession();
                session.invalidate();
                v_msg = "";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else{
                v_msg = "logout.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLogout()\r\n" + ex.getMessage());
        }
    }


    /**
   로그인 페이지로 이동할때
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performLoginPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);        // 명시적으로 box 객체를 넘겨준다

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/cp/index.jsp");
           rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /cp/user/o_index.jsp");

       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performLoginPage()\r\n" + ex.getMessage());
       }
   }

     /**
   로그인 페이지로 이동할때
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performLoginPagegari(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);        // 명시적으로 box 객체를 넘겨준다

           ServletContext sc = getServletContext();
		   System.out.println("loginpage로 이동하기");
           RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpLogin.jsp");
		    System.out.println("loginpage로 이동하기");
           rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /cp/user/o_index.jsp");

       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performLoginPage()\r\n" + ex.getMessage());
       }
   }
   

     /**
    패스워드 찾기페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performLossPwd(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_LossPwd_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_LossPwd_I.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLossPwd()\r\n" + ex.getMessage());
        }
    }


     /**
    패스워드 메일전송할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSendMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CpLoginBean bean = new CpLoginBean();
            int isOk = bean.sendFormMail(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.LoginServlet";
            box.put("p_process", "sendresult");
            box.put("p_isOk", String.valueOf(isOk));

            AlertManager alert = new AlertManager();
           
            v_msg = "";
            alert.alertOkMessage(out, v_msg, v_url , box);


            if(isOk > 0) {
                v_msg = "send.ok";
//                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "send.fail";
//                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CpLoginServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendMail()\r\n" + ex.getMessage());
        }
    }

     /**
    패스워드 메일전송결과 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSendResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/homepage/zu_LossPwd_R.jsp";

System.out.println("send mail is ok value on finding password ==>"+ box.getString("p_isOk"));               
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_LossPwd_R.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendResult()\r\n" + ex.getMessage());
        }
    }




}