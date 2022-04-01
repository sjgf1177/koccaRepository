package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MemberInfoManageBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.MemberInfoAdminServlet")
public class MemberInfoAdminServlet extends HttpServlet implements Serializable {

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
        RequestBox box = null;
        String process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            process = box.getStringDefault("process", "mainPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 권한 check
            if (!AdminUtil.getInstance().checkRWRight("MemberInfoAdminServlet", process, out, box)) {
                return;
            }

            if (process.equals("mainPage")) {
                this.performMainPage(request, response, box, out);
            } else if ( process.equals("updateB2BMemberPassword")) {
                this.performUpdateB2BMemberPassword(request, response, box, out);
            }

        } catch (Exception ex) {
            Log.err.println(this.getClass().getName() + ".doPost() " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * B2B 회원 비밀번호 일괄 갱신을 위하여 회원 목록을 조회하는 화면으로 이동한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/homepage/za_B2BMemberPwd_U.jsp";

            String grCode = box.getStringDefault("grCode", "");
            

            if (!grCode.equals("")) {
                MemberInfoManageBean bean = new MemberInfoManageBean();
                ArrayList<DataBox> b2bMemberList = bean.selectB2BMemberList(grCode);
                request.setAttribute("b2bMemberList", b2bMemberList);
            } else {
                request.setAttribute("b2bMemberList", null);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            Log.err.println(this.getClass().getName() + ".performMainPage : " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainPage()\r\n" + ex.getMessage());
        }
    }

    
    /**
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performUpdateB2BMemberPassword(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/homepage/za_B2BMemberPwd_U.jsp";

            String grCode = box.getStringDefault("grCode", "");
            int resultCnt = 0;

            if (!grCode.equals("")) {
                MemberInfoManageBean bean = new MemberInfoManageBean();
                resultCnt = bean.updateB2BMemberPassword(box);

                if ( resultCnt > 0 ) {
                    ArrayList b2bMemberList = bean.selectB2BMemberList(grCode);
                    
                    request.setAttribute("result", resultCnt);
                    request.setAttribute("b2bMemberList", b2bMemberList);
                } else {
                    AlertManager alert = new AlertManager();
                    alert.alertFailMessage(out, "비밀번호 일괄 변경작업이 실패하였습니다.");
                }
            } else {
                request.setAttribute("b2bMemberList", null);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            Log.err.println(this.getClass().getName() + ".performUpdateB2BMemberPassword : " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateB2BMemberPassword()\r\n" + ex.getMessage());
        }
    }

}
