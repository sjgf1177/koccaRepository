//**********************************************************
//  1. 제      목: 로그인 제어하는 서블릿
//  2. 프로그램명: LoginServlet.java
//  3. 개      요: 로그인 제어 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 2
//  7. 수     정1:
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.credu.common.DomainUtil;
import com.credu.homepage.LoginBean;
import com.credu.homepage.TutorLoginBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.CountBean;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.homepage.LoginServlet")
public class LoginServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
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
            v_process = box.getStringDefault("p_process", "login");
            // v_process = box.getString("p_process");

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("login")) { // in case of login
                this.performLogin(request, response, box, out);

            } else if (v_process.equals("loginPage")) { // in case of login page
                this.performLoginPage(request, response, box, out);

            } else if (v_process.equals("logout")) { // in case of logout
                this.performLogout(request, response, box, out);

            } else if (v_process.equals("gologout")) { // in case of gameuser
                                                       // logout
                this.performGoLogout(request, response, box, out);

            } else if (v_process.equals("tutorlogout")) { // in case of logout
                this.performTutorLogout(request, response, box, out);

            } else if (v_process.equals("losspwd")) { // in case of 패스워드 분실
                this.performLossPwd(request, response, box, out);

            } else if (v_process.equals("golosspwd")) { // in case of logout
                                                        // 패스워드 분실
                this.performGoLossPwd(request, response, box, out);

            } else if (v_process.equals("sendmail")) { // in case of send mail
                this.performSendMail(request, response, box, out);

            } else if (v_process.equals("sendresult")) { // in case of 패스워드 결과
                this.performSendResult(request, response, box, out);

            } else if (v_process.equals("lossidresult")) { // in case of 아이디 찾기
                                                           // 결과
                this.performLossIdResult(request, response, box, out);

            } else if (v_process.equals("firstLoginPagePre")) { // in case of
                                                                // qualification
                                                                // page open
                this.performFirstLoginPagePre(request, response, box, out);

            } else if (v_process.equals("firstLoginPage")) { // in case of
                                                             // qualification
                                                             // page
                this.performFirstLoginPage(request, response, box, out);

            } else if (v_process.equals("firstLogin")) { // in case of
                                                         // qualification
                this.performFirstLogin(request, response, box, out);

            } else if (v_process.equals("cubicnetLogin")) { // in case of 큐빅넷 로긴
                this.performCubicnetLogin(request, response, box, out);

            } else if (v_process.equals("cubicnetApproval")) { // in case of 큐빅넷
                                                               // 결재
                this.performCubicnetApproval(request, response, box, out);

            } else if (v_process.equals("SSO")) { // SSO 로그인 연동
                this.performLoginSSO(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * LOGIN PROCESS
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performLogin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = "";

            String strHttp = DomainUtil.getHttpDomain(request.getRequestURL().toString());
            // String strHttps =
            // DomainUtil.getHttpsDomain(request.getRequestURL().toString());

            if (box.getString("p_eventgubun").equals("R")) { // 설문
                v_url = "/servlet/controller.homepage.MainMemberJoinServlet";
                box.put("p_process", "MemberInfoUpdate"); // 취업 이벤트
                box.setSession("s_userid", box.getString("p_userid"));

            } else if (box.getString("p_eventgubun").equals("S")) { // 취업이벤트에서
                                                                    // 왔다면 로그인 후
                                                                    // 개인정보 페이로
                                                                    // 이동
                v_url = "/servlet/controller.community.CommunityRiskServlet?p_gubun_inja=D0001&p_process=login";
                box.put("p_process", "MemberInfoUpdate"); // 취업 이벤트
                box.setSession("s_userid", box.getString("p_userid"));

            } else if (box.getString("p_eventgubun").equals("W")) { // 웅진과정 신청
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=W0&p_action=go&p_pageno=1&gubun=1";

                // } else if (!box.getString("p_auth").equals("") ||
                // box.getString("p_auth")!= null) {
            } else if (box.getString("p_isAdmin").equals("Y")) {
                v_url = "/learn/admin/kocca/mScreenFset.jsp";

            } else {
                v_url = strHttp + "/servlet/controller.homepage.MainServlet";
                box.put("p_process", ""); // 이벤트 완료 후 주석 제거
                // v_url = "/servlet/controller.homepage.MainServlet";
                // box.put("p_process", "MemberInfoUpdateCheck"); // 이벤트 진행 후 주석
                // v_url = "/index" + v_grcode + ".jsp"; // 템플릿 적용 후 주석 제거
            }

            // String v_userip = request.getRemoteAddr();
            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.length() == 0 || v_userip.equals("") || v_userip.equals("unknown")) {
                v_userip = request.getRemoteAddr();
            }
            box.put("p_userip", v_userip);

            LoginBean bean = new LoginBean();
            int isOk1 = 0;
            int isFail = 0;

            String v_auth = "";
            v_auth = box.getString("p_auth");

            if (!v_auth.equals("")) {
                /*System.out.println("+++++++++++++++++++++++++++++++ IP체크 : v_auth" + v_auth);*/

                /** 관리자 ip체크 **/
                if (v_auth.equals("A1")) { // A1 일때 아이피 체크
                    box.put("p_userip", v_userip);
                    TutorLoginBean tbean = new TutorLoginBean();
                    if (tbean.adminCheck(box) <= 0) {
                        alert.alertFailMessage(out, "접근이 불가능한 IP입니다.");
                        // alert.alertFailMessage(out, v_msg);
                        box.setSession("gadmin", "ZZ");
                        System.out.println("==============>> : " + box.getSession("gadmin"));
                        return;
                    }
                }

                System.out.println("==================================== p_auth : " + box.getString("p_auth"));

                isFail = bean.getLgFail(box);

                if(isFail < 5) {
                    isOk1 = bean.adminLogin(box);
                    isFail = bean.getLgFail(box);
                } else {
                    isOk1 = -3;
                }

            } else {
                isOk1 = bean.login(box);
            }
            /*System.out.println("+++++++++++++++++++++++++++++++ p_auth : " + box.getString("p_auth"));*/

            /*
             * 
             * //교육서비스 설문 응시 여부 체크 시작.. int isOk2 =
             * bean.eduServiceSulCheck(box);
             * 
             * if(isOk2 == 0){ box.put("p_eduServiceSulCheck", "Y"); }else {
             * box.put("p_eduServiceSulCheck", "N"); } //교육서비스 설문 응시 여부 체크 끝..
             */

            if (isOk1 == 1) {

                // LoginBean 으로 이동했음..
                // CountBean bean1 = new CountBean();
                // isOk2 = bean1.writeLog(box); // 로그 작성

                box.setSession("cmu_grtype", "KGDI");
                // isOk3 = MileageManager.insertMileage(v_code, s_userid); //
                // 마일리지 작성

                // 쿠키 세팅
                // String v_rememberId = box.getString("rememberId");
                // String v_userid = "";
                // if (v_rememberId.equals("1")) {
                // v_userid = URLEncoder.encode(s_userid);
                // Cookie useridCookie = new Cookie("userid",v_userid);
                // useridCookie.setMaxAge(60*60*24*365);
                // response.addCookie(useridCookie);
                // } else {
                //
                // Cookie useridCookie = new Cookie("userid","");
                // useridCookie.setMaxAge(0);
                // response.addCookie(useridCookie);
                // }

                // v_msg = "login.ok";
                // alert.alertOkMessage(out, v_msg, v_url , box);

            } else {
                if (isOk1 == -1) { // ID 또는 비밀번호 없음
                    v_msg = "등록된 아이디가 없습니다.\\n다시한번 확인해 주세요.";
                } else if (isOk1 == -2) {
                    v_msg = "죄송합니다. 사용할 수 없는 아이디입니다.";
                } else if (isOk1 == -3) {
                    if(isFail < 5) {
                        v_msg = "비밀번호가 맞지 않습니다. (" + isFail + " / 5)";
                    } else {
                        v_msg = "비밀번호를 5회 잘못 입력하였습니다.\\n5회 입력 오류로 사용이 제한되었습니다.\\n문의 : 02-6310-0770";
                    }
                } else {
                    v_msg = "";
                }

            }

            // frmURL이 있으면 해당 URL로 이동한다.
            String v_frmURL = box.getString("p_frmURL");
            if (!v_frmURL.equals("")) {
                System.out.println("strHttp : " + strHttp);
                v_frmURL = strHttp + v_frmURL;
                System.out.println("v_frmURL : " + v_frmURL);
            }

            if (isOk1 == 1) {

                if (v_frmURL.equals("")) {
                    alert.alertOkMessage(out, v_msg, v_url, box);
                } else {
                    out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>");
                    out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
                    out.println("<!--                      ");
                    out.println("   function winonload() {");
                    out.println("       document.form1.submit();");
                    out.println("   }");
                    out.println("//-->");
                    out.println("</SCRIPT>");
                    out.println("<head>");

                    out.println("<body onload='winonload()'>");
                    out.println("<form name = 'form1' method = 'post' action='" + v_frmURL + "'>");

                    Enumeration e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String parameterName = (String) e.nextElement();
                        String parameterValue = request.getParameter(parameterName);
                        if (!parameterName.equals("p_pwd") && !parameterName.equals("p_topuserid") && !parameterName.equals("p_userid")
                                && !parameterName.equals("p_toppwd")) {
                            out.println("<INPUT type='hidden' name='" + parameterName + "' value='" + parameterValue + "'>");
                        }
                        // System.out.println("<INPUT type='hidden' name='" +
                        // parameterName + "' value='" + parameterValue + "'>");
                    }
                    out.println("</form>");
                    out.println("</body></html>");
                    out.flush();
                }
            } else {
                alert.alertFailMessage(out, v_msg);
                Log.info.println(this, box, v_msg + " on LoginServlet");
            }

            /*
             * } else { // 처음 로그인했을 경우out.println(
             * "<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head><body>"
             * ); out.println("<form name = 'form1' method = 'post'>");
             * out.println("<input type = 'hidden' name = 'p_userid' value = '"
             * + box.getString("p_userid") + "'>");
             * out.println("<input type = 'hidden' name = 'p_process' value = ''>"
             * ); out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
             * out.println("<!--                      ");
             * out.println("function firstLogin() {");
             * out.println("window.self.name = \"winSelectView\";");
             * out.println(
             * "farwindow = window.open(\"\", \"firstLogin\", \"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no, width = 460, height = 380 top=0, left=0\");"
             * ); out.println("document.form1.target = \"firstLogin\"");
             * out.println
             * ("document.form1.p_process.value = \"firstLoginPage\"");
             * out.println(
             * "document.form1.action = \"/servlet/controller.homepage.LoginServlet\";"
             * ); out.println("document.form1.submit();");
             * out.println("farwindow.window.focus();");
             * out.println("document.form1.target = window.self.name;");
             * out.println("}"); out.println("firstLogin();");
             * out.println("//-->"); out.println("</SCRIPT>");
             * out.println("</form>"); out.println("</body></html>123123");
             * out.flush(); }
             */
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertLogin()\r\n" + ex.getMessage());
        }
    }

    /**
     * LOGOUT PROCESS
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performLogout(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "login");
            // String v_url = "/";

            if (true) {

                String v_auth = box.getSession("gadmin");

                if (v_auth.equals("P1")) { // 권한이 강사일 경우 강사 로그아웃 정보를 입력한다.
                    TutorLoginBean tbean = new TutorLoginBean();
                    tbean.tutorLogout(box);
                }
                HttpSession session = box.getHttpSession();
                session.invalidate();
                v_msg = "";

                alert.alertOkMessage(out, v_msg, v_url, box);
            }/*
              * else{ v_msg = "logout.fail"; alert.alertFailMessage(out, v_msg);
              * }
              */
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLogout()\r\n" + ex.getMessage());
        }
    }

    // 05.12.14 이나연 추가
    public void performGoLogout(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = null;
            box.put("p_process", "");
            // String v_url = "/";
            if (true) {

                HttpSession session = box.getHttpSession();
                // session.invalidate();

                String p_originalLoginUserid = box.getSession("p_originalLoginUserid");
                if (p_originalLoginUserid.length() > 0) {
                    LoginBean bean = new LoginBean();
                    box.put("p_userid", p_originalLoginUserid);
                    v_url = "/learn/admin/system/mScreenFset.jsp";
                    bean.login(box);
                } else {
                    v_url = "/servlet/controller.homepage.MainServlet";
                    session.removeAttribute("userid");
                    session.removeAttribute("gadmin");
                    session.removeAttribute("isSubjAll");

                    session.removeAttribute("name");
                }
                v_msg = "";

                alert.alertOkMessage(out, v_msg, v_url, box);
            }/*
              * else{ v_msg = "logout.fail"; alert.alertFailMessage(out, v_msg);
              * }//
              */
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLogout()\r\n" + ex.getMessage());
        }
    }

    /**
     * TUTOR LOGOUT PROCESS
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performTutorLogout(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String v_auth = box.getSession("gadmin");

            System.out.println("================ performTutorLogout ================ gadmin : " + v_auth);

            if (v_auth.equals("P1")) { // 권한이 강사일 경우 강사 로그아웃 정보를 입력한다.
                TutorLoginBean tbean = new TutorLoginBean();
                tbean.tutorLogout(box);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTutorLogout()\r\n" + ex.getMessage());
        }
    }

    /**
     * 로그인 페이지로 이동할때
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performLoginPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_login.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_login.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLoginPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 패스워드 찾기페이지로 이동할때
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performLossPwd(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_LossPwd_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_LossPwd_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLossPwd()\r\n" + ex.getMessage());
        }
    }

    public void performGoLossPwd(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/member/gu_IdpwSearch.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/member/gu_IdpwSearch.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLossPwd()\r\n" + ex.getMessage());
        }
    }

    /**
     * 패스워드 메일전송할때
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performSendMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            LoginBean bean = new LoginBean();
            int isOk = bean.sendFormMail(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.LoginServlet";
            box.put("p_process", "sendresult");
            box.put("p_isOk", String.valueOf(isOk));

            AlertManager alert = new AlertManager();

            v_msg = "";
            alert.alertOkMessage(out, v_msg, v_url, box);

            if (isOk > 0) {
                v_msg = "send.ok";
                // alert.alertOkMessage(out, v_msg, v_url , box);
            } else {
                v_msg = "send.fail";
                // alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on LoginServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendMail()\r\n" + ex.getMessage());
        }
    }

    /**
     * 패스워드 메일전송결과 페이지로 이동할때
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performSendResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/game/member/gu_LossPwd_R.jsp";

            LoginBean bean = new LoginBean();
            String pwd = bean.getPwd(box);
            request.setAttribute("pwd", pwd);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/member/gu_LossPwd_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendResult()\r\n" + ex.getMessage());
        }
    }

    /**
     * 아이디 찾기 결과 페이지로 이동할때
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performLossIdResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/game/member/gu_LossId_R.jsp";

            LoginBean bean = new LoginBean();
            String userid = bean.getUserid(box);
            request.setAttribute("userid", userid);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/member/gu_LossId_R.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLossIdResult()\r\n" + ex.getMessage());
        }
    }

    /**
     * 처음 로긴했을 때 팝업 띄우는 페이지로
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performFirstLoginPagePre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            // 가로460 세로 324입니다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_FirstLogin_P.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_FirstLogin_P.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFirstLoginPagePre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 처음 로긴했을 때 페이지로
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performFirstLoginPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            LoginBean bean = new LoginBean();
            String v_email = bean.emailOpen(box);
            box.put("p_email", v_email);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_FirstLogin_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_FirstLogin_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFirstLoginPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 처음 로긴처리
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performFirstLogin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            LoginBean bean = new LoginBean();
            int isOk = bean.firstLogin(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MemberInfoServlet";
            box.put("p_process", "memberUpdatePage");
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "정보이용동의서에 동의하셧습니다";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                // alert.selfClose(out, v_msg);
            } else {
                v_msg = "실패했습니다.";
                // alert.selfClose(out, v_msg);
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on LoginServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendMail()\r\n" + ex.getMessage());
        }
    }

    /**
     * 큐빅넷 로긴
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performCubicnetLogin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");

            String v_userid = box.getString("p_userid");
            v_userid = StringManager.BASE64Decode(v_userid);
            box.put("p_userid", v_userid);
            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);

            LoginBean bean = new LoginBean();

            int isOk1 = bean.ssologin(box);

            if (isOk1 > 0) {
                CountBean bean1 = new CountBean();
                bean1.writeLog(box); // 로그 작성
                //
                // v_msg = "login.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_url = "/servlet/controller.homepage.LoginServlet?leftmenu=1";
                box.put("p_process", "loginPage");
                v_msg = "";
                alert.alertOkMessage(out, v_msg, v_url, box);
                // alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on LoginServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCubicnetLogin()\r\n" + ex.getMessage());
        }
    }

    /**
     * 큐빅넷 결제
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performCubicnetApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = "/servlet/controller.propose.CubicApprovalServlet";
            box.put("p_process", "listPage");

            String v_userid = box.getString("p_userid");
            v_userid = StringManager.BASE64Decode(v_userid);

            box.put("p_userid", v_userid);

            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);

            LoginBean bean = new LoginBean();

            int isOk1 = bean.ssologin(box);

            if (isOk1 > 0) {
                CountBean bean1 = new CountBean();
                bean1.writeLog(box); // 로그 작성

                // v_msg = "login.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_url = "/servlet/controller.homepage.LoginServlet?leftmenu=1";
                box.put("p_process", "loginPage");
                v_msg = "";
                alert.alertOkMessage(out, v_msg, v_url, box);
                // alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on LoginServlet");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCubicnetApproval()\r\n" + ex.getMessage());
        }
    }

    /**
     * LOGIN SSO PROCESS
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performLoginSSO(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");

            LoginBean bean = new LoginBean();

            int isOk1 = bean.loginSSO(box);

            if (isOk1 == 1) {

                // 쿠키 세팅
                // String v_rememberId = box.getString("rememberId");
                // String v_userid = "";
                // if (v_rememberId.equals("1")) {
                // v_userid = URLEncoder.encode(s_userid);
                // Cookie useridCookie = new Cookie("userid",v_userid);
                // useridCookie.setMaxAge(60*60*24*365);
                // response.addCookie(useridCookie);
                // } else {

                // Cookie useridCookie = new Cookie("userid","");
                // useridCookie.setMaxAge(0);
                // response.addCookie(useridCookie);
                // }

            } else {
                v_url = "/index_new.jsp"; // v_url = "/index.jsp";
                box.put("p_process", "loginPage");
                if (isOk1 == -1) { // ID 또는 비밀번호 없음
                    v_msg = "죄송합니다.\\n등록된 아이디를 찾을수가 없습니다.";
                } else {
                    v_msg = "";
                }

            }

            // frmURL이 있으면 해당 URL로 이동한다.
            String v_frmURL = box.getString("p_frmURL");
            // String v_frmURL = request.getParameter("p_frmURL");

            if (isOk1 == 1) {

                if (v_frmURL.equals("")) {
                    alert.alertOkMessage(out, v_msg, v_url, box);
                } else {

                    out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>");
                    out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
                    out.println("<!--                      ");
                    out.println("   function winonload() {");
                    out.println("       document.form1.submit();");
                    out.println("   }");
                    out.println("//-->");
                    out.println("</SCRIPT>");
                    out.println("<head>");

                    out.println("<body onload='winonload()'>");
                    out.println("<form name = 'form1' method = 'post' action='" + v_frmURL + "'>");

                    Enumeration e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String parameterName = (String) e.nextElement();
                        String parameterValue = request.getParameter(parameterName);
                        out.println("<INPUT type='hidden' name='" + parameterName + "' value='" + parameterValue + "'>");
                        // System.out.println("<INPUT type='hidden' name='" +
                        // parameterName + "' value='" + parameterValue + "'>");
                    }
                    out.println("</form>");
                    out.println("</body></html>");
                    out.flush();
                }
            } else {
                alert.alertOkMessage(out, v_msg, v_url, box);
                // alert.alertFailMessage(out, v_msg);
                Log.info.println(this, box, v_msg + " on LoginServlet");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertLoginSSO()\r\n" + ex.getMessage());
        }
    }

}