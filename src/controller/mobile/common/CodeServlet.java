package controller.mobile.common;

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

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.common.CodeServlet")
public class CodeServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * 
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

            process = box.getStringDefault("process", "commonCodeList"); // process

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if ("commonCodeList".equals(process)) {
                this.pefromSelectCommonCodeList(request, response, box, out);

            } else if ("groupCodeList".equals(process)) {
                this.performSelectGroupCodeList(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
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
    private void performSelectGroupCodeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CodeBean bean = new CodeBean();
            ArrayList<DataBox> groupCodeList = bean.selectGroupCodeList(box);

            request.setAttribute("groupCodeList", groupCodeList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/user/groupCodeListAjaxResult.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " peformB2BLoginPage()\r\n" + ex.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    private void pefromSelectCommonCodeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CodeBean bean = new CodeBean();
            ArrayList commonCodeList = bean.selectCommonCodeList(box);

            request.setAttribute("commonCodeList", commonCodeList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/user/B2BLoginPage.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " peformB2BLoginPage()\r\n" + ex.getMessage());
        }

    }

}