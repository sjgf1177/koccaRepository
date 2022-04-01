//*********************************************************
//  1. ��      ��: USER SERVLET
//  2. ���α׷���: KInterestServlet.java
//  3. ��      ��: ���ɰ��� servlet
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: lyh
//  7. ��      ��:
//**********************************************************
package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

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
import com.credu.study.KInterestBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.study.KInterestServlet")
public class KInterestServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 9208024505029520699L;

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

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // �α� check ��ƾ VER 0.2
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("InterestList")) { // ���ɰ��� ����Ʈ ���

                this.performInerestList(request, response, box, out);
            } else if (v_process.equals("InterestDelete")) { // ���ɰ��� ����

                this.performInerestDelete(request, response, box, out);
            } else if (v_process.equals("InterestInsert")) { // ���ɰ��� ���

                this.performInerestInsert(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ���ɰ��� ��ȸ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInerestList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KInterestBean bean = new KInterestBean();

            ArrayList<ArrayList<DataBox>> list = bean.selectInterestList(box);
            String v_url = "/learn/user/kocca/study/ku_InterestList_L.jsp";

            request.setAttribute("InterestList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���ɰ��� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInerestDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KInterestBean bean = new KInterestBean();

            int isOk = bean.deleteInterest(box);
            String v_url = "/servlet/controller.study.KInterestServlet";
            String v_msg = "";

            box.put("p_process", "InterestList");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {

                alert.alertOkMessage(out, v_msg, v_url, box, false, false);

            } else {
                v_msg = "�� ������ �ȵǾ����ϴ�. �ٽ� �õ��Ͽ��ֽʽÿ�";
                alert.alertFailMessage(out, v_msg);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("InerestDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���ɰ��� ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInerestInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KInterestBean bean = new KInterestBean();

            int isOk = bean.insertInterest(box);
            String v_msg = "";

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "���ɰ������� ��ϵǾ����ϴ�.";
                alert.alertFailMessage(out, v_msg);

            } else if (isOk == -1) {

                v_msg = "���ɰ������� �̹� ��ϵǾ��ֽ��ϴ�.";
                alert.alertFailMessage(out, v_msg);

            } else {
                v_msg = "���ɰ������� ��� �ȵǾ����ϴ�. �ٽ� �õ��Ͽ��ֽʽÿ�";
                alert.alertFailMessage(out, v_msg);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("InerestDelete()\r\n" + ex.getMessage());
        }
    }

}