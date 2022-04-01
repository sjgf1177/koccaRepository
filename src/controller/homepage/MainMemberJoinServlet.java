//**********************************************************
//1. ��      ��: ȸ������ �����ϴ� ����
//2. ���α׷��� : MemberJoinServlet.java
//3. ��      ��: ȸ������ ���� ���α׷�
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: �̳��� 05.12.16
//7. ��     ��1:
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
                this.performMemberJoin(request, response, box, out); // ȸ������

            } else if (v_process.equals("CheckResno")) { // ȸ������ ����
                this.performCheckResno(request, response, box, out);

            } else if (v_process.equals("CheckAgree")) { // �̿��� ����
                this.performCheckAgree(request, response, box, out);

            } else if (v_process.equals("CheckId")) { // ID �ߺ�üũ
                this.performCheckID(request, response, box, out);

            } else if (v_process.equals("JoinOk")) { // ȸ������
                this.performJoinOk(request, response, box, out);

            } else if (v_process.equals("FindIdPwd")) { // ���̵�/�н����� ã��
                this.performFindIdPwd(request, response, box, out);

            } else if (v_process.equals("FindIdResult")) { // in case of ���̵� ã�� ���
                this.performFindIdResult(request, response, box, out);

            } else if (v_process.equals("SendMail")) { // in case of send mail and �н����� ���
                this.performSendMail(request, response, box, out);

            } else if (v_process.equals("SendResult")) { // in case of �н����� ���
                this.performSendResult(request, response, box, out);

            } else if (v_process.equals("CheckCerti")) { // �ڵ�������Ȯ��
                this.performCheckCerti(request, response, box, out);

            } else if (v_process.equals("ChangePwd")) { // ��й�ȣ ����������
                this.performChangePwd(request, response, box, out);

            } else if (v_process.equals("ChangePwdOk")) { // ��й�ȣ ����
                this.performChangePwdOk(request, response, box, out);

            } else if (v_process.equals("MemberInfoUpdate")) { // ������������������
                this.performMemberInfoUpdate(request, response, box, out);

            } else if (v_process.equals("MemberInfoUpdateOk")) { // ������������
                this.performMemberInfoUpdateOk(request, response, box, out);

            } else if (v_process.equals("MemberWithdraw")) { // ȸ��Ż��������
                this.performMemberWithdraw(request, response, box, out);

            } else if (v_process.equals("MemberWithdrawOk")) { // ȸ��Ż������
                this.performMemberWithdrawOk(request, response, box, out);

            } else if (v_process.equals("UserAgree")) { // �̿���
                this.performUserAgree(request, response, box, out);

            } else if (v_process.equals("Personal")) { // ���κ�ȣ��å
                this.performPersonal(request, response, box, out);

            } else if (v_process.equals("PersonalNew")) { // ���κ�ȣ��å(20140519 �ű�)
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberJoin()\r\n" + ex.getMessage());
        }
    }

    /**
     * ȸ������ Ȯ�ο���
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
            // ���� �������϶� �˾� �ȽɽǸ� �������� ���� �������� ����..
            // ASP����Ʈ ������ ������ ����..
            if (box.getSession("tem_grcode").equals("N000001") && !box.getString("checkradio").equals("14")) { // 14�� �̻�

                String nc_safeid = box.getString("nc_safeid"); // �Ƚ�Ű�� �޾ƿ���
                String[] info = null;
                String dupinfo = ""; // DI
                String conninfo = ""; // CI
                String v_msg = "";
                String flag = "SID"; // SID : �Ƚ�Ű��, JID : �ֹε�Ϲ�ȣ

                AlertManager alert = new AlertManager();

                if (box.getString("i_irtn").equals("1")) {
                    dupinfo = box.getString("i_dupinfo");
                    conninfo = box.getString("i_conninfo");
                } else {
                    Interop interop = new Interop(); // DI,CI ������ ����
                    info = interop.Interop(nc_safeid, flag).split(";"); // DI�� �����´�.
                    dupinfo = info[0];
                    conninfo = info[1];
                }
                box.setSession("dupinfo", dupinfo);
                box.setSession("conninfo", conninfo);

                MainMemberJoinBean bean = new MainMemberJoinBean();
                int is_Ok = bean.checkResno(box);

                if (is_Ok == 0) {
                    request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
                    ServletContext sc = getServletContext();
                    RequestDispatcher rd = sc.getRequestDispatcher(vv_url);
                    rd.forward(request, response);
                } else {
                    String v_url = "/servlet/controller.homepage.MainMemberJoinServlet";
                    box.put("p_process", "CheckAgree");
                    v_msg = "�̹� ���Ե� ȸ���Դϴ�";
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
                info = interop.Interop(v_resno, flag).split(";"); // DI�� �����´�.
                dupinfo = info[0];
                conninfo = info[1];
                box.setSession("dupinfo", dupinfo); // ���ǿ� ����
                box.setSession("conninfo", conninfo);

                box.setSession("p_name", v_username);
                box.setSession("p_resno", v_resno);
                box.setSession("p_resno1", v_resno1);
                box.setSession("p_resno2", v_resno2);

                MainMemberJoinBean bean = new MainMemberJoinBean();

                int is_Ok = bean.checkResno(box);

                AlertManager alert = new AlertManager();

                if (is_Ok == 0) {
                    request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
                    ServletContext sc = getServletContext();
                    RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/member/zu_MemberInfo_I.jsp");
                    rd.forward(request, response);
                } else {
                    String v_url = "/servlet/controller.homepage.MainMemberJoinServlet";
                    box.put("p_process", "CheckAgree");
                    v_msg = "�̹� ���Ե� ȸ���Դϴ�";

                    alert.alertOkMessage(out, v_msg, v_url, box);
                }

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckResno()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckAgree()\r\n" + ex.getMessage());
        }
    }

    /**
     * ID �ߺ�üũ
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckID()\r\n" + ex.getMessage());
        }
    }

    /**
     * ȸ�����
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
                // v_msg = "ȸ�� ���ԵǼ̽��ϴ�";
                v_msg = "";
                // alert.alertOkMessage(out,v_msg,v_url,box);

                request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);

            } else {
                v_msg = "ȸ����Ͽ� �����Ͽ����ϴ�";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJoinOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * �н����� ã���������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performFindIdPwd(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

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
     * ���̵� ã�� ��� �������� �̵��Ҷ�
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

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
     * �н����� ���������Ҷ�
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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

            // ��й�ȣ �����(������Ʈ)
            String tmp_pwd = bean.selectTempPasswordMail(box);

            if (tmp_pwd.equals("0")) {
                v_msg = "�ش��ϴ� ������ ã�� �� �� �����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            } else {
                box.put("tmp_pwd", tmp_pwd);

                // ��й�ȣ ��߱�(��������)
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
     * �н����� �������۰�� �������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSendResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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
     * ��й�ȣ���� ������
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performChangePwd()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��й�ȣ����
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

                v_msg = "��й�ȣ�� ����Ǿ����ϴ�. �ٽ� �α��� ���ֽʽÿ�!";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 0) {
                v_msg = "��й�ȣ ���濡 �����߽��ϴ�.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "���� ��й�ȣ�� �߸� �Է��߽��ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performChangePwdOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ���� ������
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

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
     * �������� ����ó��
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
            // SSL ����
            String strHttp = DomainUtil.getHttpDomain(request.getRequestURL().toString());
            // String strHttps = DomainUtil.getHttpsDomain(request.getRequestURL().toString());

            MainMemberJoinBean bean = new MainMemberJoinBean();

            int isOk = bean.memberInfoUpdate(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");

            v_url = strHttp + v_url; // HTTP �� ����

            AlertManager alert = new AlertManager();

            if (box.getString("p_eventgubun").equals("R")) { // ���� ���� �Դٸ� �α��� �� �������� ���̷� �̵��Ѵ�.
                String s_userid = box.getSession("s_userid");
                v_url = "/servlet/controller.community.CommunityRiskServlet?p_gubun_inja=E0001&p_process=login&id=" + s_userid;
            }

            if (isOk > 0) {
                v_msg = "���������� ����Ǿ����ϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "�������� ���濡 �����߽��ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberInfoUpdateOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * ȸ��Ż���û ������
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberWithdraw()\r\n" + ex.getMessage());
        }
    }

    /**
     * ȸ��Ż���û
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

                v_msg = "ȸ��Ż�� ó���Ǿ����ϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 0) {
                v_msg = "ȸ��Ż�� �����߽��ϴ�.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "��й�ȣ�� �߸� �Է��߽��ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberWithdrawOk()\r\n" + ex.getMessage());
        }
    }

    /**
     * �̿���
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserAgree()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���κ�ȣ��å
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������(�ڵ���)
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

            // �������������� ���ã�� �Ҷ� �������� �ߺ�DI�� �����ϱ� ���ؼ�
            String s_resno = s_resno1 + s_resno2;
            String s_dupinfo = "";
            String[] info = null;

            String flag = "JID";
            // �Էµ� �ֹε�� ��ȣ�� �ߺ� DI ���
            Interop interop = new Interop();
            info = interop.Interop(s_resno, flag).split(";");

            s_dupinfo = info[0];

            box.put("p_dupinfo", s_dupinfo);

            String sMessage = "";
            String sEncData = "";

            int isOk = 0;
            LoginBean bean = new LoginBean();

            // ================================================
            // ���̵� & �ֹι�ȣ üũ
            // ================================================
            box.put("p_resno1", s_resno1);
            box.put("p_resno2", s_resno2);
            box.put("p_name", s_name);
            box.put("p_userid", s_userid);
            // box.put("p_handphone", s_handphone);
            // box.put("p_email", s_email);

            String userid = bean.getUserid(box);

            // ================================================
            // �Է��� ���̵� �����ϸ� �ڵ������� ó��
            // ================================================
            if (s_userid.equals(userid)) {

                Kisinfo.Check.CPClient kisCrypt = new Kisinfo.Check.CPClient();

                String sSiteCode = "G1091"; // �ѽ��������κ��� �ο����� ����Ʈ �ڵ�
                String sSitePassword = "OGUNHRYMMD3M"; // �ѽ��������κ��� �ο����� ����Ʈ �н�����

                String sRequestNumber = "REQ0000000001"; // ��û ��ȣ, �̴� ����/�����Ŀ� ����
                // ������ �ǵ����ְ� �ǹǷ�
                // ��ü���� �����ϰ� �����Ͽ� ���ų�, �Ʒ��� ���� �����Ѵ�.
                sRequestNumber = kisCrypt.getRequestNO(sSiteCode);
                box.setSession("REQ_SEQ", sRequestNumber); // ��ŷ���� ������ ���Ͽ� ������
                // ���ٸ�, ���ǿ� ��û��ȣ��
                // �ִ´�.

                String sAuthType = "M"; // ������ �⺻ ����ȭ��, X: ����������, M: �ڵ���, C: ī��,
                // B: ���� ȭ��
                String sJuminid = s_resno1 + s_resno2; // ����� �ֹε�Ϲ�ȣ
                // String sJuminid = "7202161790014";
                String sName = s_name; // ����� ����

                // CheckPlus(��������) ó�� ��, ��� ����Ÿ�� ���� �ޱ����� ���������� ���� http���� �Է��մϴ�.
                // String sReturnUrl =
                // "http://www.test.co.kr/test/checkplus_success.jsp"; // ������
                // �̵��� URL
                // String sErrorUrl =
                // "http://www.test.co.kr/test/checkplus_fail.jsp"; // ���н� �̵���
                // URL

                // String sReturnUrl =
                // "http://localhost:7001/certi/checkplus_success.jsp"; // ������
                // �̵��� URL
                // String sErrorUrl =
                // "http://localhost:7001/certi/checkplus_fail.jsp"; // ���н� �̵���
                // URL
                String sReturnUrl = "http://edu.kocca.or.kr/certi/checkplus_success.jsp"; // ������
                // �̵���
                // URL
                String sErrorUrl = "http://edu.kocca.or.kr/certi/checkplus_fail.jsp"; // ���н�
                // �̵���
                // URL

                // �Էµ� plain ����Ÿ�� �����.
                String sPlainData = "7:JUMINID" + sJuminid.getBytes().length + ":" + sJuminid + "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber + "4:NAME" + sName.getBytes().length + ":" + sName + "8:SITECODE"
                        + sSiteCode.getBytes().length + ":" + sSiteCode + "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType + "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl + "7:ERR_URL" + sErrorUrl.getBytes().length + ":"
                        + sErrorUrl;

                int iReturn = kisCrypt.fnEncode(sSiteCode, sSitePassword, sPlainData);

                if (iReturn == 0) {
                    sEncData = kisCrypt.getCipherData();
                } else if (iReturn == -1) {
                    sMessage = "��ȣȭ �ý��� �����Դϴ�.";
                } else if (iReturn == -2) {
                    sMessage = "��ȣȭ ó�������Դϴ�.";
                } else if (iReturn == -3) {
                    sMessage = "��ȣȭ ������ �����Դϴ�.";
                } else if (iReturn == -9) {
                    sMessage = "�Է� ������ �����Դϴ�.";
                } else {
                    sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
                }

            } else {
                isOk = -1;
                sMessage = "�Է��Ͻ� ȸ�������� �������� �ʽ��ϴ�.";
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckCerti()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������(�ڵ���)
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
            // ���̵� & �ֹι�ȣ üũ
            // ================================================
            box.put("p_name", s_name);
            box.put("p_userid", s_userid);
            box.put("p_resno", s_resno);
            String userid = bean.getUseridHandPhone(box);

            // ================================================
            // �Է��� ���̵� �����ϸ� �ڵ������� ó��
            // ================================================
            if (s_userid.equals(userid)) {

                Kisinfo.Check.CPClient kisCrypt = new Kisinfo.Check.CPClient();

                String sSiteCode = "G1091"; // �ѽ��������κ��� �ο����� ����Ʈ �ڵ�
                String sSitePassword = "OGUNHRYMMD3M"; // �ѽ��������κ��� �ο����� ����Ʈ �н�����

                String sRequestNumber = "REQ0000000001"; // ��û ��ȣ, �̴� ����/�����Ŀ� ����
                // ������ �ǵ����ְ� �ǹǷ�
                // ��ü���� �����ϰ� �����Ͽ� ���ų�, �Ʒ��� ���� �����Ѵ�.
                sRequestNumber = kisCrypt.getRequestNO(sSiteCode);
                box.setSession("REQ_SEQ", sRequestNumber); // ��ŷ���� ������ ���Ͽ� ������
                // ���ٸ�, ���ǿ� ��û��ȣ��
                // �ִ´�.

                String sAuthType = "M"; // ������ �⺻ ����ȭ��, X: ����������, M: �ڵ���, C: ī��,
                // B: ���� ȭ��
                String sJuminid = s_resno; // ����� �ֹε�Ϲ�ȣ
                // String sJuminid = "7202161790014";
                String sName = s_name; // ����� ����

                // CheckPlus(��������) ó�� ��, ��� ����Ÿ�� ���� �ޱ����� ���������� ���� http���� �Է��մϴ�.
                // String sReturnUrl =
                // "http://www.test.co.kr/test/checkplus_success.jsp"; // ������
                // �̵��� URL
                // String sErrorUrl =
                // "http://www.test.co.kr/test/checkplus_fail.jsp"; // ���н� �̵���
                // URL

                // String sReturnUrl =
                // "http://localhost:7001/certi/checkplus_success.jsp"; // ������
                // �̵��� URL
                // String sErrorUrl =
                // "http://localhost:7001/certi/checkplus_fail.jsp"; // ���н� �̵���
                // URL
                String sReturnUrl = "http://edu.kocca.or.kr/certi/checkplus_success.jsp"; // ������
                // �̵���
                // URL
                String sErrorUrl = "http://edu.kocca.or.kr/certi/checkplus_fail.jsp"; // ���н�
                // �̵���
                // URL

                // �Էµ� plain ����Ÿ�� �����.
                String sPlainData = "7:JUMINID" + sJuminid.getBytes().length + ":" + sJuminid + "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber + "4:NAME" + sName.getBytes().length + ":" + sName + "8:SITECODE"
                        + sSiteCode.getBytes().length + ":" + sSiteCode + "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType + "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl + "7:ERR_URL" + sErrorUrl.getBytes().length + ":"
                        + sErrorUrl;

                int iReturn = kisCrypt.fnEncode(sSiteCode, sSitePassword, sPlainData);

                if (iReturn == 0) {
                    sEncData = kisCrypt.getCipherData();
                } else if (iReturn == -1) {
                    sMessage = "��ȣȭ �ý��� �����Դϴ�.";
                } else if (iReturn == -2) {
                    sMessage = "��ȣȭ ó�������Դϴ�.";
                } else if (iReturn == -3) {
                    sMessage = "��ȣȭ ������ �����Դϴ�.";
                } else if (iReturn == -9) {
                    sMessage = "�Է� ������ �����Դϴ�.";
                } else {
                    sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
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

                request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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
                 * "[KOCCA] �ӽ� ��й�ȣ�� "+ v_tmp_pwd
                 * +" �Դϴ�. �α��� �� �ű� ��й�ȣ�� �������ּ���.");
                 * 
                 * String v_msg = "���� ó�����Դϴ�."; alert.alertOkMessage(out, v_msg,
                 * v_url , box);
                 */

            } else {
                isOk = -1;
                box.put("sIsOk", "1");
                sMessage = "�Է��Ͻ� ȸ�������� �������� �ʽ��ϴ�.";
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCheckCerti()\r\n" + ex.getMessage());
        }
    }
}