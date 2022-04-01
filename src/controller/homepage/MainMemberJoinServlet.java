//**********************************************************
//1. 제      목: 회원가입 제어하는 서블릿
//2. 프로그램명 : MemberJoinServlet.java
//3. 개      요: 회원가입 제어 프로그램
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 이나연 05.12.16
//7. 수     정1:
//**********************************************************
package controller.homepage;

import ipin.Interop;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.credu.common.DomainUtil;
import com.credu.homepage.LoginBean;
import com.credu.homepage.MainMemberJoinBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.MainMemberJoinServlet")
public class MainMemberJoinServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "MemberJoin");

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            // box.setSession("grtype","KGDI");

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            if (v_process.equals("MemberJoin")) {
                this.performMemberJoin(request, response, box, out); // 회원가입

            } else if (v_process.equals("CheckResno")) { // 회원가입 여부
                this.performCheckResno(request, response, box, out);

            } else if (v_process.equals("CheckAgree")) { // 이용약관 동의
                this.performCheckAgree(request, response, box, out);

            } else if (v_process.equals("CheckId")) { // ID 중복체크
                this.performCheckID(request, response, box, out);

            } else if (v_process.equals("JoinOk")) { // 회원가입
                this.performJoinOk(request, response, box, out);

            } else if (v_process.equals("FindIdPwd")) { // 아이디/패스워드 찾기
                this.performFindIdPwd(request, response, box, out);

            } else if (v_process.equals("FindIdResult")) { // in case of 아이디 찾기 결과
                this.performFindIdResult(request, response, box, out);

            } else if (v_process.equals("SendMail")) { // in case of send mail and 패스워드 결과
                this.performSendMail(request, response, box, out);

            } else if (v_process.equals("SendResult")) { // in case of 패스워드 결과
                this.performSendResult(request, response, box, out);

            } else if (v_process.equals("CheckCerti")) { // 핸드폰인증확인
                this.performCheckCerti(request, response, box, out);

            } else if (v_process.equals("ChangePwd")) { // 비밀번호 변경페이지
                this.performChangePwd(request, response, box, out);

            } else if (v_process.equals("ChangePwdOk")) { // 비밀번호 변경
                this.performChangePwdOk(request, response, box, out);

            } else if (v_process.equals("MemberInfoUpdate")) { // 개인정보수정페이지
                this.performMemberInfoUpdate(request, response, box, out);

            } else if (v_process.equals("MemberInfoUpdateOk")) { // 개인정보수정
                this.performMemberInfoUpdateOk(request, response, box, out);

            } else if (v_process.equals("MemberWithdraw")) { // 회원탈퇴페이지
                this.performMemberWithdraw(request, response, box, out);

            } else if (v_process.equals("MemberWithdrawOk")) { // 회원탈퇴저장
                this.performMemberWithdrawOk(request, response, box, out);

            } else if (v_process.equals("UserAgree")) { // 이용약관
                this.performUserAgree(request, response, box, out);

            } else if (v_process.equals("Personal")) { // 개인보호정책
                this.performPersonal(request, response, box, out);

            } else if (v_process.equals("PersonalNew")) { // 개인보호정책(20140519 신규)
                this.performPersonal(request, response, box, out);
            } else if (v_process.equals("memberInfoInsesrt")) {
                this.performMemberInfoInsert(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * LOGIN PROCESS
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMemberJoin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012
                // renewal
                // v_url = "/learn/user/2012/portal/member/zu_MemberAgree.jsp";
                v_url = "/learn/user/2013/portal/member/zu_MemberAgree.jsp";
            } else {
                v_url = "/learn/user/portal/member/zu_MemberAgree.jsp";
            }
            // v_url = "/learn/user/2012/portal/member/zu_MemberAgree.jsp";
            box.put("p_process", "");

            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberJoin()\r\n" + ex.getMessage());
        }
    }

    /**
     * 회원가입 확인여부
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performCheckResno(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String vv_url = "/learn/user/2013/portal/member/zu_MemberInfo_I.jsp";
            // 메인 페이지일때 팝엄 안심실명 도입으로 인해 구버전과 구별..
            // ASP사이트 때문에 기존것 놔둠..
            if (box.getSession("tem_grcode").equals("N000001") && !box.getString("checkradio").equals("14")) { // 14세 이상

                String nc_safeid = box.getString("nc_safeid"); // 안심키값 받아오기
                String[] info = null;
                String dupinfo = ""; // DI
                String conninfo = ""; // CI
                String v_msg = "";
                String flag = "SID"; // SID : 안심키값, JID : 주민등록번호

                AlertManager alert = new AlertManager();

                if (box.getString("i_irtn").equals("1")) {
                    dupinfo = box.getString("i_dupinfo");
                    conninfo = box.getString("i_conninfo");
                } else {
                    Interop interop = new Interop(); // DI,CI 생성을 위해
                    info = interop.Interop(nc_safeid, flag).split(";"); // DI를 가져온다.
                    dupinfo = info[0];
                    conninfo = info[1];
                }
                box.setSession("dupinfo", dupinfo);
                box.setSession("conninfo", conninfo);

                MainMemberJoinBean bean = new MainMemberJoinBean();
                int is_Ok = bean.checkResno(box);

                if (is_Ok == 0) {
                    request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
                    ServletContext sc = getServletContext();
                    RequestDispatcher rd = sc.getRequestDispatcher(vv_url);
                    rd.forward(request, response);
                } else {
                    String v_url = "/servlet/controller.homepage.MainMemberJoinServlet";
                    box.put("p_process", "CheckAgree");
                    v_msg = "이미 가입된 회원입니다";
                    alert.alertOkMessage(out, v_msg, v_url, box);
                }

            } else {

                String v_username = box.getString("p_username");
                String v_resno1 = box.getString("p_resno1");
                String v_resno2 = box.getString("p_resno2");
                String v_resno = v_resno1 + v_resno2;

                String v_msg = "";
                String[] info = null;
                String dupinfo = "";
                String conninfo = "";
                String flag = "JID";

                Interop interop = new Interop();
                info = interop.Interop(v_resno, flag).split(";"); // DI를 가져온다.
                dupinfo = info[0];
                conninfo = info[1];
                box.setSession("dupinfo", dupinfo); // 세션에 저장
                box.setSession("conninfo", conninfo);

                box.setSession("p_name", v_username);
                box.setSession("p_resno", v_resno);
                box.setSession("p_resno1", v_resno1);
                box.setSession("p_resno2", v_resno2);

                MainMemberJoinBean bean = new MainMemberJoinBean();

                int is_Ok = bean.checkResno(box);

                AlertManager alert = new AlertManager();

                if (is_Ok == 0) {
                    request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
                    ServletContext sc = getServletContext();
                    RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/member/zu_MemberInfo_I.jsp");
                    rd.forward(request, response);
                } else {
                    String v_url = "/servlet/controller.homepage.MainMemberJoinServlet";
                    box.put("p_process", "CheckAgree");
                    v_msg = "이미 가입된 회원입니다";

                    alert.alertOkMessage(out, v_msg, v_url, box);
                }

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckResno()\r\n" + ex.getMessage());
        }
    }

    /**
     * 약관동의
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performCheckAgree(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_MemberJoin.jsp";
            } else {
                v_url = "/learn/user/portal/member/zu_MemberJoin.jsp";
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckAgree()\r\n" + ex.getMessage());
        }
    }

    /**
     * ID 중복체크
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performCheckID(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            MainMemberJoinBean bean = new MainMemberJoinBean();
            int is_Ok = bean.checkID(box);

            String v_url = "/learn/user/portal/member/zu_IdCheck.jsp";

            box.put("isOk", String.valueOf(is_Ok));

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckID()\r\n" + ex.getMessage());
        }
    }

    /**
     * 회원등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performJoinOk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_msg = "";

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_MemberJoinOK.jsp";
            } else {
                v_url = "/learn/user/portal/member/zu_MemberJoinOK.jsp";
            }

            AlertManager alert = new AlertManager();

            MainMemberJoinBean bean = new MainMemberJoinBean();

            int is_Ok = bean.insertMember(box);

            if (is_Ok == 1) {
                // v_msg = "회원 가입되셨습니다";
                v_msg = "";
                // alert.alertOkMessage(out,v_msg,v_url,box);

                request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);

            } else {
                v_msg = "회원등록에 실패하였습니다";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJoinOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * 패스워드 찾기페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performFindIdPwd(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_FindIdPwd.jsp";
            } else {
                v_url = "/learn/user/portal/member/zu_FindIdPwd.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFindIdPwd()\r\n" + ex.getMessage());
        }
    }

    /**
     * 아이디 찾기 결과 페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performFindIdResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_FindId_R.jsp";

            } else {
                v_url = "/learn/user/portal/member/zu_FindId_R.jsp";
            }

            MainMemberJoinBean bean = new MainMemberJoinBean();
            ArrayList userid = bean.getUserid(box);
            request.setAttribute("userid", userid);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFindIdResult()\r\n" + ex.getMessage());
        }
    }

    /**
     * 패스워드 메일전송할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSendMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            AlertManager alert = new AlertManager();

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_FindPwd_R.jsp";

            } else {
                v_url = "/learn/user/portal/member/zu_FindPwd_R.jsp";
            }

            // int isOk = 0;
            String v_msg = "";

            LoginBean bean = new LoginBean();

            // 비밀번호 재생성(업데이트)
            String tmp_pwd = bean.selectTempPasswordMail(box);

            if (tmp_pwd.equals("0")) {
                v_msg = "해당하는 정보를 찾을 수 가 없습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                box.put("tmp_pwd", tmp_pwd);

                // 비밀번호 재발급(메일전송)
                /*
                 * if (!tmp_pwd.equals("")) { isOk = bean.sendFormMail(box); }
                 */

                // request.setAttribute("email", email);

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendMail()\r\n" + ex.getMessage());
        }
    }

    /**
     * 패스워드 메일전송결과 페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSendResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/portal/member/zu_FindPwd_R.jsp";

            LoginBean bean = new LoginBean();
            String pwd = bean.getPwd(box);
            request.setAttribute("pwd", pwd);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendResult()\r\n" + ex.getMessage());
        }
    }

    /**
     * 비밀번호변경 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performChangePwd(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_PwdChange.jsp";
            } else {
                v_url = "/learn/user/portal/member/zu_PwdChange.jsp";
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performChangePwd()\r\n" + ex.getMessage());
        }
    }

    /**
     * 비밀번호변경
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out PrintWriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performChangePwdOk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            MainMemberJoinBean bean = new MainMemberJoinBean();
            String strHttp = DomainUtil.getHttpDomain(request.getRequestURL().toString());
            // String strHttps = DomainUtil.getHttpsDomain(request.getRequestURL().toString());

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "ChangePwd");

            v_url = strHttp + v_url;

            int isOk = bean.pwdUpdate(box);

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                // box.put("p_process", "gologout");
                // v_url = strHttp + "/servlet/controller.homepage.LoginServlet";
                HttpSession session = request.getSession();
                session.invalidate();
                v_url = strHttp + "/learn/user/2013/portal/homepage/zu_MainLogin.jsp";

                v_msg = "비밀번호가 변경되었습니다. 다시 로그인 해주십시오!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 0) {
                v_msg = "비밀번호 변경에 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "현재 비밀번호를 잘못 입력했습니다.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performChangePwdOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인정보 수정 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMemberInfoUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_frmURL", request.getRequestURI().toString());
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_MemberInfo_U.jsp";
            } else {
                v_url = "/learn/user/portal/member/zu_MemberInfo_U.jsp";
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            MainMemberJoinBean bean = new MainMemberJoinBean();
            DataBox dbox = bean.memberInfoView(box);

            request.setAttribute("memberView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberInfoUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인정보 수정처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMemberInfoUpdateOk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // SSL 연동
            String strHttp = DomainUtil.getHttpDomain(request.getRequestURL().toString());
            // String strHttps = DomainUtil.getHttpsDomain(request.getRequestURL().toString());

            MainMemberJoinBean bean = new MainMemberJoinBean();

            int isOk = bean.memberInfoUpdate(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");

            v_url = strHttp + v_url; // HTTP 로 보냄

            AlertManager alert = new AlertManager();

            if (box.getString("p_eventgubun").equals("R")) { // 설문 에서 왔다면 로그인 후 개인정보 페이로 이동한다.
                String s_userid = box.getSession("s_userid");
                v_url = "/servlet/controller.community.CommunityRiskServlet?p_gubun_inja=E0001&p_process=login&id=" + s_userid;
            }

            if (isOk > 0) {
                v_msg = "개인정보가 변경되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "개인정보 변경에 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberInfoUpdateOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * 회원탈퇴신청 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMemberWithdraw(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_MemberWithdraw.jsp";

            } else {
                v_url = "/learn/user/portal/member/zu_MemberWithdraw.jsp";
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberWithdraw()\r\n" + ex.getMessage());
        }
    }

    /**
     * 회원탈퇴신청
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMemberWithdrawOk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            MainMemberJoinBean bean = new MainMemberJoinBean();

            int isOk = bean.memberWithdrawUpdate(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                box.put("p_process", "gologout");
                v_url = "/servlet/controller.homepage.LoginServlet";

                v_msg = "회원탈퇴가 처리되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 0) {
                v_msg = "회원탈퇴가 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "비밀번호를 잘못 입력했습니다.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberWithdrawOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * 이용약관
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUserAgree(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/member/zu_UserAgree.jsp";

            } else {
                v_url = "/learn/user/portal/member/zu_UserAgree.jsp";
            }
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserAgree()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인보호정책
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonal(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012
                if (box.get("p_process").equals("Personal")) {
                    v_url = "/learn/user/2013/portal/member/zu_Personal.jsp";
                } else {
                    v_url = "/learn/user/2013/portal/member/zu_Personal_New.jsp";
                }
            } else {
                v_url = "/learn/user/portal/member/zu_Personal.jsp";
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }

    /**
     * 본인인증(핸드폰)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCheckCerti(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String s_userid = box.getString("p_userid_2");
            String s_resno1 = box.getString("p_resno_2_1");
            String s_resno2 = box.getString("p_resno_2_2");
            String s_name = box.getString("p_name_2");
            // String s_email = box.getString("p_email_2");

            // 메인페이지에서 비번찾기 할때 아이핀의 중복DI로 구분하기 위해서
            String s_resno = s_resno1 + s_resno2;
            String s_dupinfo = "";
            String[] info = null;

            String flag = "JID";
            // 입력된 주민등록 번호로 중복 DI 얻기
            Interop interop = new Interop();
            info = interop.Interop(s_resno, flag).split(";");

            s_dupinfo = info[0];

            box.put("p_dupinfo", s_dupinfo);

            String sMessage = "";
            String sEncData = "";

            int isOk = 0;
            LoginBean bean = new LoginBean();

            // ================================================
            // 아이디 & 주민번호 체크
            // ================================================
            box.put("p_resno1", s_resno1);
            box.put("p_resno2", s_resno2);
            box.put("p_name", s_name);
            box.put("p_userid", s_userid);
            // box.put("p_handphone", s_handphone);
            // box.put("p_email", s_email);

            String userid = bean.getUserid(box);

            // ================================================
            // 입력한 아이디가 존재하면 핸드폰인증 처리
            // ================================================
            if (s_userid.equals(userid)) {

                Kisinfo.Check.CPClient kisCrypt = new Kisinfo.Check.CPClient();

                String sSiteCode = "G1091"; // 한신평정보로부터 부여받은 사이트 코드
                String sSitePassword = "OGUNHRYMMD3M"; // 한신평정보로부터 부여받은 사이트 패스워드

                String sRequestNumber = "REQ0000000001"; // 요청 번호, 이는 성공/실패후에 같은
                // 값으로 되돌려주게 되므로
                // 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
                sRequestNumber = kisCrypt.getRequestNO(sSiteCode);
                box.setSession("REQ_SEQ", sRequestNumber); // 해킹등의 방지를 위하여 세션을
                // 쓴다면, 세션에 요청번호를
                // 넣는다.

                String sAuthType = "M"; // 없으면 기본 선택화면, X: 공인인증서, M: 핸드폰, C: 카드,
                // B: 은행 화면
                String sJuminid = s_resno1 + s_resno2; // 사용자 주민등록번호
                // String sJuminid = "7202161790014";
                String sName = s_name; // 사용자 성명

                // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
                // String sReturnUrl =
                // "http://www.test.co.kr/test/checkplus_success.jsp"; // 성공시
                // 이동될 URL
                // String sErrorUrl =
                // "http://www.test.co.kr/test/checkplus_fail.jsp"; // 실패시 이동될
                // URL

                // String sReturnUrl =
                // "http://localhost:7001/certi/checkplus_success.jsp"; // 성공시
                // 이동될 URL
                // String sErrorUrl =
                // "http://localhost:7001/certi/checkplus_fail.jsp"; // 실패시 이동될
                // URL
                String sReturnUrl = "http://edu.kocca.or.kr/certi/checkplus_success.jsp"; // 성공시
                // 이동될
                // URL
                String sErrorUrl = "http://edu.kocca.or.kr/certi/checkplus_fail.jsp"; // 실패시
                // 이동될
                // URL

                // 입력될 plain 데이타를 만든다.
                String sPlainData = "7:JUMINID" + sJuminid.getBytes().length + ":" + sJuminid + "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber + "4:NAME" + sName.getBytes().length + ":" + sName + "8:SITECODE"
                        + sSiteCode.getBytes().length + ":" + sSiteCode + "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType + "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl + "7:ERR_URL" + sErrorUrl.getBytes().length + ":"
                        + sErrorUrl;

                int iReturn = kisCrypt.fnEncode(sSiteCode, sSitePassword, sPlainData);

                if (iReturn == 0) {
                    sEncData = kisCrypt.getCipherData();
                } else if (iReturn == -1) {
                    sMessage = "암호화 시스템 에러입니다.";
                } else if (iReturn == -2) {
                    sMessage = "암호화 처리오류입니다.";
                } else if (iReturn == -3) {
                    sMessage = "암호화 데이터 오류입니다.";
                } else if (iReturn == -9) {
                    sMessage = "입력 데이터 오류입니다.";
                } else {
                    sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
                }

            } else {
                isOk = -1;
                sMessage = "입력하신 회원정보가 존재하지 않습니다.";
            }

            box.put("sUserid", s_userid);
            box.put("sJuminid1", s_resno1);
            box.put("sJuminid2", s_resno2);
            box.put("sName", s_name);
            box.put("sEncData", sEncData);
            box.put("sMessage", sMessage);
            box.put("sIsOk", isOk);

            String v_url = "";
            // v_url = "/learn/user/portal/member/zu_FindIdPwd.jsp";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012
                // renewal
                // v_url = "/learn/user/2012/portal/member/zu_FindIdPwd.jsp";
                v_url = "/learn/user/2013/portal/member/zu_FindIdPwd.jsp";
            } else {
                v_url = "/learn/user/portal/member/zu_FindIdPwd.jsp";
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckCerti()\r\n" + ex.getMessage());
        }
    }

    /**
     * 본인인증(핸드폰)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCheckCertiRenewal(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {

            String s_userid = box.getString("p_userid_2");
            String s_resno = box.getString("p_resno_2_1") + box.getString("p_resno_2_2");
            String s_name = box.getString("p_name_2");
            String v_url = "/learn/user/2012/portal/member/zu_FindIdPwd.jsp";

            AlertManager alert = new AlertManager();
            String sMessage = "";
            String sEncData = "";

            int isOk = 0;
            LoginBean bean = new LoginBean();

            // ================================================
            // 아이디 & 주민번호 체크
            // ================================================
            box.put("p_name", s_name);
            box.put("p_userid", s_userid);
            box.put("p_resno", s_resno);
            String userid = bean.getUseridHandPhone(box);

            // ================================================
            // 입력한 아이디가 존재하면 핸드폰인증 처리
            // ================================================
            if (s_userid.equals(userid)) {

                Kisinfo.Check.CPClient kisCrypt = new Kisinfo.Check.CPClient();

                String sSiteCode = "G1091"; // 한신평정보로부터 부여받은 사이트 코드
                String sSitePassword = "OGUNHRYMMD3M"; // 한신평정보로부터 부여받은 사이트 패스워드

                String sRequestNumber = "REQ0000000001"; // 요청 번호, 이는 성공/실패후에 같은
                // 값으로 되돌려주게 되므로
                // 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
                sRequestNumber = kisCrypt.getRequestNO(sSiteCode);
                box.setSession("REQ_SEQ", sRequestNumber); // 해킹등의 방지를 위하여 세션을
                // 쓴다면, 세션에 요청번호를
                // 넣는다.

                String sAuthType = "M"; // 없으면 기본 선택화면, X: 공인인증서, M: 핸드폰, C: 카드,
                // B: 은행 화면
                String sJuminid = s_resno; // 사용자 주민등록번호
                // String sJuminid = "7202161790014";
                String sName = s_name; // 사용자 성명

                // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
                // String sReturnUrl =
                // "http://www.test.co.kr/test/checkplus_success.jsp"; // 성공시
                // 이동될 URL
                // String sErrorUrl =
                // "http://www.test.co.kr/test/checkplus_fail.jsp"; // 실패시 이동될
                // URL

                // String sReturnUrl =
                // "http://localhost:7001/certi/checkplus_success.jsp"; // 성공시
                // 이동될 URL
                // String sErrorUrl =
                // "http://localhost:7001/certi/checkplus_fail.jsp"; // 실패시 이동될
                // URL
                String sReturnUrl = "http://edu.kocca.or.kr/certi/checkplus_success.jsp"; // 성공시
                // 이동될
                // URL
                String sErrorUrl = "http://edu.kocca.or.kr/certi/checkplus_fail.jsp"; // 실패시
                // 이동될
                // URL

                // 입력될 plain 데이타를 만든다.
                String sPlainData = "7:JUMINID" + sJuminid.getBytes().length + ":" + sJuminid + "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber + "4:NAME" + sName.getBytes().length + ":" + sName + "8:SITECODE"
                        + sSiteCode.getBytes().length + ":" + sSiteCode + "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType + "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl + "7:ERR_URL" + sErrorUrl.getBytes().length + ":"
                        + sErrorUrl;

                int iReturn = kisCrypt.fnEncode(sSiteCode, sSitePassword, sPlainData);

                if (iReturn == 0) {
                    sEncData = kisCrypt.getCipherData();
                } else if (iReturn == -1) {
                    sMessage = "암호화 시스템 에러입니다.";
                } else if (iReturn == -2) {
                    sMessage = "암호화 처리오류입니다.";
                } else if (iReturn == -3) {
                    sMessage = "암호화 데이터 오류입니다.";
                } else if (iReturn == -9) {
                    sMessage = "입력 데이터 오류입니다.";
                } else {
                    sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
                }
                box.put("sUserid", s_userid);
                box.put("sName", s_name);
                box.put("sEncData", sEncData);
                box.put("sMessage", sMessage);
                box.put("sIsOk", isOk);

                // String v_url = "";
                // v_url = "/learn/user/portal/member/zu_FindIdPwd.jsp";
                if (box.getSession("tem_grcode").equals("N000001")) { // B2C
                    // 2012
                    // renewal
                    v_url = "/learn/user/2012/portal/member/zu_FindIdPwd.jsp";
                } else {
                    v_url = "/learn/user/portal/member/zu_FindIdPwd.jsp";
                }

                request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);

                /*
                 * 
                 * String v_tmp_pwd = bean.selectTempPasswordHandphone(box);
                 * 
                 * String v_url = "/servlet/controller.library.SMSServlet";
                 * box.put("p_process", "certicheck");
                 * 
                 * box.put("p_checks", "1"); box.put("p_title",
                 * "[KOCCA] 임시 비밀번호는 "+ v_tmp_pwd
                 * +" 입니다. 로그인 후 신규 비밀번호로 변경해주세요.");
                 * 
                 * String v_msg = "전송 처리중입니다."; alert.alertOkMessage(out, v_msg,
                 * v_url , box);
                 */

            } else {
                isOk = -1;
                box.put("sIsOk", "1");
                sMessage = "입력하신 회원정보가 존재하지 않습니다.";
                // alert.alertFailMessage(out, sMessage);
                alert.alertOkMessage(out, sMessage, v_url, box);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckCerti()\r\n" + ex.getMessage());
        }
    }

    public void performMemberInfoInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String url = "/learn/user/2013/portal/member/zu_MemberInfo_I_new.jsp";
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckCerti()\r\n" + ex.getMessage());
        }
    }
}