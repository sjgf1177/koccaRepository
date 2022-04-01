package controller.propose;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ExternalEduBean;
import com.credu.system.AdminUtil;

/**
 * 
 * @author kocca
 * 
 */
@WebServlet("/servlet/controller.propose.ExternalEduServlet")
public class ExternalEduServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6057454062575271864L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
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
    @Override
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
            v_process = box.getStringDefault("process", "registerPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ExternalEduServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("registerPage")) {
                this.performRegisterPage(request, response, box, out);

            } else if (v_process.equals("register")) {
                this.performRegister(request, response, box, out);

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
    @SuppressWarnings("unchecked")
    private void performRegister(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ExternalEduBean bean = new ExternalEduBean();
            DataBox resultBox = bean.registerExternalEdu(box);

            String v_url = "/servlet/controller.propose.ProposeStatusAdminServlet";
            box.put("process", "registerPage");

            AlertManager alert = new AlertManager();

            String resultMessage = resultBox.getString("msg");
            int resultCount = resultBox.getInt("count");
            if (resultMessage.equals("success")) {
                alert.alertOkMessage(out, "총 " + resultCount +"건의 자료를 등록하였습니다.", v_url, box, false, false);
            } else {
                alert.alertFailMessage(out, "insert.fail");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberExcel()\r\n" + ex.getMessage());
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
    private void performRegisterPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_ExternalEduRegiserPage.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudentMemberExcel()\r\n" + ex.getMessage());
        }

    }

}
