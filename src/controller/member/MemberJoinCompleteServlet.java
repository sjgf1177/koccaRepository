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

            // 회원 정보를 등록한 후 완료 화면으로 이동한다.
            this.performRegisterMember(request, response, box, out);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 회원 정보를 등록한 후 완료 화면으로 이동한다.
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
                alert.alertOkMessage(out, "잘못된 접근입니다", "/", box);
            } else {

                MainMemberJoinBean bean = new MainMemberJoinBean();
                int is_Ok = bean.insertMember(box);

                if (is_Ok == 1) {
                    request.setAttribute("requestbox", box); // 명시적으로 box 객체를
                    // 넘겨준다
                    // ServletContext sc = getServletContext();
                    // RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                    // rd.forward(request, response);
                    response.sendRedirect(v_url);

                } else {
                    alert.alertFailMessage(out, "회원등록에 실패하였습니다");
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberCeritPage()\r\n" + ex.getMessage());
        }
    }

}