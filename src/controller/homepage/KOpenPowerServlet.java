//*********************************************************
//1. ��      ��: USER SERVLET
//2. ���α׷���: KOpenPowerServlet.java
//3. ��      ��: Ư���� servlet
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: lyh
//7. ��      ��:
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.KOpenPowerBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.KOpenPowerServlet")
public class KOpenPowerServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5167565141496714160L;

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
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "OpenPowerList");

            box.put("tem_grcode", box.getSession("tem_grcode"));

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("OpenPowerList")) { // �������� �������ΰ��� ����Ʈ
                this.performOpenPowerList(request, response, box, out);
            } else if (v_process.equals("OpenPowerDetail")) { // �������� �������ΰ��� ����
                this.performOpenPowerDetail(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �Ŀ����ͺ� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOpenPowerList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            KOpenPowerBean bean1 = new KOpenPowerBean();

            ArrayList<DataBox> list = bean1.selectList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_OpenPower_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/open/ku_OpenPower_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPowerList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �Ŀ����ͺ� �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOpenPowerDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            KOpenPowerBean bean = new KOpenPowerBean();

            DataBox dbox = bean.selectView(box);
            request.setAttribute("selectMember", dbox);

            ArrayList<DataBox> list = bean.selectList(box);
            request.setAttribute("selectMemberList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_OpenPower_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/open/ku_OpenPower_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("selectOpenPowerDetail()\r\n" + ex.getMessage());
        }
    }

}