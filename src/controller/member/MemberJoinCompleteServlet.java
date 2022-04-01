package controller.member;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MainMemberJoinBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.library.MemberJoinCompleteServlet")
public class MemberJoinCompleteServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8134603527929000841L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     */
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
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            // ȸ�� ������ ����� �� �Ϸ� ȭ������ �̵��Ѵ�.
            this.performRegisterMember(request, response, box, out);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ȸ�� ������ ����� �� �Ϸ� ȭ������ �̵��Ѵ�.
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
    public void performRegisterMember(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/learn/user/2013/portal/member/zu_MemberJoinOK.jsp";

            String process = box.getString("p_process");

            AlertManager alert = new AlertManager();
            if (process == null || process.equals("")) {
                alert.alertOkMessage(out, "�߸��� �����Դϴ�", "/", box);
            } else {

                MainMemberJoinBean bean = new MainMemberJoinBean();
                int is_Ok = bean.insertMember(box);

                if (is_Ok == 1) {
                    request.setAttribute("requestbox", box); // ��������� box ��ü��
                    // �Ѱ��ش�
                    // ServletContext sc = getServletContext();
                    // RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                    // rd.forward(request, response);
                    response.sendRedirect(v_url);

                } else {
                    alert.alertFailMessage(out, "ȸ����Ͽ� �����Ͽ����ϴ�");
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberCeritPage()\r\n" + ex.getMessage());
        }
    }

}