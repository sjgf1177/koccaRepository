package controller.mobile.user;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.common.CodeBean;
import com.credu.mobile.user.LoginBean;

@WebServlet("/servlet/controller.mobile.user.LoginServlet")
public class LoginServlet extends javax.servlet.http.HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 4940444538339870581L;

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest , javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest , javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String process = "";

        try {
            // request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            process = box.getStringDefault("process", "loginPage"); // process

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if ("loginPage".equals(process)) {
                this.peformLoginPage(request, response, box, out); // 모바일 개인회원 로그인 페이지

            } else if ("B2BLoginPage".equals(process)) {
                this.peformB2BLoginPage(request, response, box, out); // 모바일 교육그룹 회원 로그인 페이지

            } else if ("loginProc".equals(process)) {
                this.peformloginProc(request, response, box, out); // 모바일 로그인처리 process

            } else if ("logoutProc".equals(process)) {
                this.performLogoutProc(request, response, box, out); // 모바일 로그아웃

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * B2C 로그인 페이지
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void peformLoginPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/user/B2CLoginPage.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " peformgetSchoolCodeSearchPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * B2B 로그인 페이지
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void peformB2BLoginPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            CodeBean bean = new CodeBean();
            ArrayList<DataBox> commonCodeList = bean.selectCommonCodeList(box);

            request.setAttribute("commonCodeList", commonCodeList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/user/B2BLoginPage.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " peformB2BLoginPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 로그인 체크 process
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void peformloginProc(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            LoginBean bean = new LoginBean();

            DataBox dbox = bean.loginUser(box);

            request.setAttribute("loginResult", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/user/loginOkAjaxResult.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " peformloginProc()\r\n" + ex.getMessage());
        }
    }

    /**
     * 로그 아웃
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performLogoutProc(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            System.out.println("log out in servlet");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/user/logout.jsp");
            rd.forward(request, response);

            // alert.alertOkMessage(out, "로그아웃 되었습니다.", "/servlet/controller.mobile.openclass.OpenClassPopularServlet", box);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " performLogoutProc()\r\n" + ex.getMessage());
        }
    }

}
