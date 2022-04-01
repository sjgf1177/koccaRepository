//**********************************************************
//  1. ��     ��:  �������ν�û���� : ��û���� �����ϴ� ����
//  2. ���α׷��� : OffApprovalAdminServlet.java
//  3. ��     ��:  �������ν�û���� : ��û���� ���� ���α׷�
//  4. ȯ     ��: JDK 1.5
//  5. ��     ��: 1.0
//  6. ��     ��: swchoi 2009. 11. 12
//  7. ��    ��1:
//**********************************************************
package controller.off;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffApprovalBean;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffApprovalAdminServlet")
public class OffApprovalAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        //      MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        //      int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("OffApprovalAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("listPage")) { //in case of ��û���� ��� ȭ��
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insert")) { //in case of ��û���� ���
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("delete")) { //  �����Ҷ�
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("viewPropose")) { // ���� ������û���� popup
                this.performViewPropose(request, response, box, out);
            } else if (v_process.equals("personalSelectPrint")) { // ������û�ڷ� ���
                this.performViewPrint(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * // �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffApprovalBean bean = new OffApprovalBean();

            int isOk = bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffApprovalAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffApprovalBean bean = new OffApprovalBean();

            int isOk = bean.runAccept(box);
            String s_subjcode = box.getVector("p_subj").elementAt(0).toString();
            String s_year = box.getVector("p_year").elementAt(0).toString();
            String s_subjseq = box.getVector("p_subjseq").elementAt(0).toString();

            box.put("s_subjcode", s_subjcode);
            box.put("s_year", s_year);
            box.put("s_subjseq", s_subjseq);
            box.put("s_action", "go");
            box.sync("s_lowerclass", "p_lowerclass");
            box.sync("s_middleclass", "p_middleclass");
            box.sync("s_upperclass", "p_upperclass");

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffApprovalAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��û�����ڵ帮��Ʈ VIEW
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_Approval_L.jsp";
            box.sync("s_subjsearchkey");
            
            String subj = box.getString("ss_subj");
            String year = box.getString("ss_year");
            String subjseq = box.getString("ss_subjseq");
            
            String nameStr = "ID|����|����|�������|�����|�ҼӺμ�|��å��|����ó|E-Mail|��û��|�Ѱ�³��|�Ѱ�°���|�ڱ�Ұ�|��������|�������� �̸�| �������� | �Ҽ� �� ����";
            String codeStr = "d_userid|d_name|d_sex|d_memberreg|d_comptext|d_deptnam|d_jikchaeknm|d_handphone|d_email|d_appdate|d_tcareeryear|d_tcareermonth|d_intro|d_motive|d_apply_name|d_apply_session|d_apply_belong_title";
            
            OffApprovalBean bean = new OffApprovalBean();
            
            if ( subj != null && !subj.equals("")) {
                DataBox dbox = bean.selectNeedInput(subj, year, subjseq); 
                char[]token = dbox.getString("d_needinput").toCharArray();
                
                if ( token[10] == '1') {
                    nameStr += "|�г���|���ο¶���ä��";
                    codeStr += "|d_nickname|d_private_sns";
                }
            }
            
            if (box.getString("s_lowerclass").length() > 0 && !box.getBoolean("isAllExcel")) {
                request.setAttribute("resultList", bean.listPage(box));
                request.setAttribute("acceptResultList", bean.acceptResultList(box));
            }

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/off/za_excel.jsp";//�ʼ�
                box.put("title", "Off ��û���");//���� ����

                box.put("tname", nameStr);//�÷���
                box.put("tcode", codeStr);//�������̸�
                box.put("resultListName", "resultList");//��� ���
            }

            if (box.getBoolean("isAllExcel")) {
                request.setAttribute("resultList", bean.listAllPage(box));
                v_return_url = "/learn/admin/off/za_excel.jsp";//�ʼ�
                box.put("title", box.get("s_year") + "�⵵ ��ü Off ��û���");//���� ����

                box.put("tname", "������|����|ID|����|����|�������|�����|�ҼӺμ�|��å��|����ó|E-Mail|�������|������|�����ð�|��������|������|���λ���|��û��|�����|��ҿ�û��|�Ѱ�³��|�Ѱ�°���");//�÷���
                box.put("tcode", "d_subjnm|d_subjseq|d_userid|d_name|d_memberreg|d_sex|d_comptext|d_deptnam|d_jikchaeknm|d_handphone|d_email|d_paymethod|d_pgauthdate|d_pgauthtime|d_resultcode|d_price|chk_name|d_appdate|d_refunddate|d_canceldate|d_tcareeryear|d_tcareermonth");//�������̸�
                box.put("resultListName", "resultList");//��� ���
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    public void performViewPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {

            request.setAttribute("requestbox", box);

            // ��������
            MemberInfoBean bean = new MemberInfoBean();
            box.put("onOff", 2);
            request.setAttribute("resultbox", bean.memberInfoViewNew(box));
            request.setAttribute("offApplyInfo", bean.selectOffApplyInfo(box));

            // ���� ������û �̷� ����
            OffApprovalBean bean2 = new OffApprovalBean();
            request.setAttribute("resultList", bean2.listPageMember(box));
            request.setAttribute("acceptResultList", bean2.acceptResultList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_PersonalSelect_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    public void performViewPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ��������
            MemberInfoBean bean = new MemberInfoBean();
            box.put("onOff", 2);
            request.setAttribute("resultbox", bean.memberInfoViewNew(box));

            // ���� ������û �̷� ����
            OffApprovalBean bean2 = new OffApprovalBean();
            request.setAttribute("resultList", bean2.listPageMember(box));
            request.setAttribute("acceptResultList", bean2.acceptResultList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_personalSelect_P.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("personalSelectPrint()\r\n" + ex.getMessage());
        }
    }
}
