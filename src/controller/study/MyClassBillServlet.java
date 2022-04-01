//*********************************************************
//  1. ��      ��: MYCLASS BILL USER SERVLET
//  2. ���α׷���: MyClassBillServlet.java
//  3. ��      ��: ���ǰ��ǽ� ������ ���� ��ȸ/���� servlet
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: 2009.12.24
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
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.polity.SchoolfeeAdminBean;
import com.credu.study.MyClassBillBean;
import com.credu.system.AdminUtil;

/**
 * 
 * @author kocca
 * 
 */
@WebServlet("/servlet/controller.study.MyClassBillServlet")
public class MyClassBillServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 186000416125468734L;

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
        //        MultipartRequest multi = null;
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

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("MyClassBillListPage")) { // on-line ��������� �̷� ������
                this.performMyClassBillListPage(request, response, box, out);
            }
            if (v_process.equals("PayInfoPage")) { // on-line ��������� �̷� ������
                this.performPayInfoPage(request, response, box, out);
            }
            if (v_process.equals("MyOffClassBillListPage")) { // off-line ��������� �̷� ������
                this.performMyOffClassBillListPage(request, response, box, out);
            }
            if (v_process.equals("OffPayInfoPage")) { // off-line ��������� �̷� ������
                this.performOffPayInfoPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelApplyPage")) { // on-line ���� ��û ��� ��û ������
                this.performProposeCancelApplyPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelApply")) { // on-line ���� ��û ��� ��û
                this.performProposeCancelApply(request, response, box, out);
            }
            if (v_process.equals("ProposeOffHistoryPage")) { // off-line ��������� �̷� ������
                this.performProposeOffHistoryPage(request, response, box, out);
            }
            if (v_process.equals("ProposeOffCancelApplyPage")) { // off-line ���� ��û ��� ��û ������
                this.performProposeOffCancelApplyPage(request, response, box, out);
            }
            if (v_process.equals("ProposeOffCancelApply")) { // off-line ���� ��û ��� ��û
                this.performProposeOffCancelApply(request, response, box, out);
            }
            if (v_process.equals("ProposeOffPayApplyPage")) { // off-line ���� ��û ��� ��û ������
                this.performProposeOffPayApplyPage(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ������������� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPayInfoPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            SchoolfeeAdminBean bean = new SchoolfeeAdminBean();

            ArrayList<DataBox> list = bean.selectPayInfo(box);
            request.setAttribute("payInfo", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_PayInfo_P.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/course/gu_PayInfo_P.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPayCancePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * off-line ��������� ��ȸ/���� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performMyOffClassBillListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/course/gu_MyOffClassBill_L.jsp";
            } else {
                v_url = "/learn/user/portal/course/gu_MyOffClassBill_L.jsp";
            }

            MyClassBillBean bean = new MyClassBillBean();
            ArrayList<DataBox> list = bean.selectMyOffClassBillList(box);

            request.setAttribute("MyOffClassBillList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * off-line ������������� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOffPayInfoPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MyClassBillBean bean = new MyClassBillBean();

            ArrayList<DataBox> list = bean.selectOffPayInfo(box);
            request.setAttribute("offpayInfo", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_OffPayInfo_P.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/course/gu_OffPayInfo_P.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOffPayInfoPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������� ��ȸ/���� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMyClassBillListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/course/gu_MyClassBill_L.jsp";
            } else {
                v_url = "/learn/user/portal/course/gu_MyClassBill_L.jsp";
            }

            MyClassBillBean bean = new MyClassBillBean();
            ArrayList<DataBox> list = bean.selectMyClassBillList(box);

            request.setAttribute("MyClassBillList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������� ��ҽ�û ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeCancelApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBillBean bean = new MyClassBillBean();
            ArrayList<DataBox> list = bean.selectSubjnmList(box);

            request.setAttribute("SubjnmList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_ProposeCancelApply_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û ��� ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performProposeOffCancelApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBillBean bean = new MyClassBillBean();

            int isOk = bean.updateProposeOffCancelApply(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassBillServlet";
            box.put("p_process", "ProposeOffHistoryPage");
            //box.put("p_grcode","G01");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "propcancel.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "propcancel.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeOffCancelApply()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������� ��ȸ/���� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeOffHistoryPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBillBean bean = new MyClassBillBean();
            ArrayList<DataBox> list = bean.selectProposeOffHistoryList(box);

            request.setAttribute("ProposeOffHistoryList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_ProposeOffHistory_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeOffHistoryPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������� ��ҽ�û ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeOffCancelApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBillBean bean = new MyClassBillBean();
            ArrayList<DataBox> list = bean.selectOffSubjnmList(box);

            request.setAttribute("SubjnmList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_ProposeOffCancelApply_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeOffCancelApplyPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û ��� ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performProposeCancelApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBillBean bean = new MyClassBillBean();

            int isOk = bean.updateProposeCancelApply(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassBillServlet";
            box.put("p_process", "MyClassBillListPage");
            //box.put("p_grcode","G01");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "propcancel.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "propcancel.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancel()\r\n" + ex.getMessage());
        }
    }

    /**
     * offline ������û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeOffPayApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //  ������Ұ��� �� ���� �� ��� ������û������ TID �� Null������ ������Ʈ�Ѵ�. (2010.10.11)
            // �������� ���´� ������� �� ������û������ �ڵ����� ���� �ʱ� ������ ����� �� ������û������ ����(�Ǵ� TID �ʵ� null ó��) �ؾ� �Ѵ�.
            //============================================================================================
            /*
             * String v_tid = box.getString("p_tid"); String v_cancelyn =
             * box.getString("p_cancelyn");
             * 
             * if (!v_tid.equals("") && v_cancelyn.equals("Y") ) { MyClassBean
             * bean2 = new MyClassBean(); int isOk =
             * bean2.updateCancelOffPropose2(box);
             * 
             * box.put("p_tid_origin", v_tid); box.put("p_cancelyn_origin",
             * v_cancelyn); }
             */
            //============================================================================================

            MyClassBillBean bean = new MyClassBillBean();
            ArrayList<DataBox> list = bean.selectOffPayInfo(box);
            request.setAttribute("offpayInfo", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_OffPayCheck_P.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/course/gu_OffPayCheck_P.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

}