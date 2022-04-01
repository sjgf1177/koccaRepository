package controller.mobile.main;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.NoticeAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.mobile.helpdesk.HelpDeskBean;
import com.credu.templet.TempletBean;

/**
 * ������Ʈ�� : kocca_java ��Ű���� : controller.mobile.main ���ϸ� : MainServlet.java �ۼ���¥
 * : 2011. 9. 24. ó������ : �������� :
 * 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.
 */

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.main.MainServlet")
public class MainServlet extends javax.servlet.http.HttpServlet {
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getString("p_process"); // process

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if ("".equals(v_process) || "mainPage".equals(v_process)) {
                this.performMainPage(request, response, box, out); //����� ���� ������
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����� ���� ������
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            box.put("p_servernm", request.getServerName());
            TempletBean bean1 = new TempletBean();
            DataBox listBean = bean1.SelectGrcodeExists(box);

            if (listBean != null && !listBean.get("d_grcode").equals("")) {
                box.setSession("tem_menu_type", listBean.get("d_menutype")); //�޴� �׺���̼� Ÿ��
                box.setSession("tem_grcode", listBean.get("d_grcode"));
                box.setSession("tem_main_type", listBean.get("d_type")); // ���� ȭ�� Ÿ��
            } else {
                box.setSession("tem_type", "A");
                box.setSession("tem_grcode", "N000001");
                box.setSession("tem_menu_type", ""); //�޴� �׺���̼� Ÿ��
                box.setSession("tem_main_type", ""); // ���� ȭ�� Ÿ��
            }

            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {

                /*------- �Խ��� �з��� ���� �κ� ���� -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/

                tabseq = nbean.selectTableseq(box);

                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�.";
                    AlertManager.historyBack(out, msg);
                }

                box.put("p_tabseq", String.valueOf(tabseq));
            }
            HelpDeskBean bean = new HelpDeskBean();

            //��������
            request.setAttribute("_NOTICVIEW_", bean.getMainNoticeData(box));

            //�̺�Ʈ����
            request.setAttribute("_EVENTVIEW_", bean.getMainEventData(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/main/zu_Main.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainPage()\r\n" + ex.getMessage());
        }
    }
}
