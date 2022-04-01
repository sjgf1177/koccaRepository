//**********************************************************
//1. ��      ��: Ȩ������ �������� ���� ����
//2. ���α׷���: HomeNoticeServlet.java
//3. ��      ��: Ȩ������ �������� ���� ����
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2005.12.18 �̳���
//7. ��      ��:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomeNoticeBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomeNoticeServlet")
public class HomeNoticeServlet extends javax.servlet.http.HttpServlet {

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        //MultipartRequest multi        = null;
        RequestBox box = null;
        String v_process = "";
        //int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            //    String path = request.getServletPath();

            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "List");

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            if (ErrorManager.isErrorMessageView())
                box.put("errorout", out);

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            //if (!AdminUtil.getInstance().checkLogin(out, box)) {
            // return;
            //}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if (v_process.equals("List")) { //  ����Ʈ �������� �̵��Ҷ�
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("selectView")) { //  ����
                this.performViewPage(request, response, box, out);
            } else if (v_process.equals("mainList")) { // ���� �ѷ��ֱ�
                this.performMainListPage(request, response, box, out);
            } else if (v_process.equals("moreNotice")) { // more ����
                this.performMoreListPage(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ���� More ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void performMoreListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/user/game/service/gu_Notice_L.jsp";
            box.put("p_process", "moreNotice");

            HomeNoticeBean bean = new HomeNoticeBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- �Խ��� �з��� ���� �κ� ���� -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = bean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            ArrayList list2 = bean.selectDirectList(box);
            request.setAttribute("selectNoticeList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Notice_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� �������� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMainListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�

            String v_userip = request.getRemoteAddr(); // ������ ip
            box.put("p_userip", v_userip);
            box.put("p_process", "mainList");
            /* ===================== �������� ���� ========================= */
            // HomeNoticeBean bean = new HomeNoticeBean();

            String v_url = "";
            v_url = "/learn/user/game/service/Notice.jsp";
            //String v_url = "/index.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to " + v_url);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ�������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("rawtypes")
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                //v_url = "/learn/user/2012/portal/helpdesk/zu_Notice_L.jsp";
                v_url = "/learn/user/2013/portal/helpdesk/zu_Notice_L.jsp";
            } else {
                v_url = "/learn/user/portal/helpdesk/zu_Notice_L.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/helpdesk/zu_Notice_L.jsp";
            }

            HomeNoticeBean bean = new HomeNoticeBean();

            ArrayList list2 = bean.selectDirectList(box);
            request.setAttribute("selectNoticeList", list2);
            
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                //v_url = "/learn/user/2012/portal/helpdesk/zu_Notice_R.jsp";
                v_url = "/learn/user/2013/portal/helpdesk/zu_Notice_R.jsp";
            } else {
                v_url = "/learn/user/portal/helpdesk/zu_Notice_R.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/helpdesk/zu_Notice_R.jsp";
            }

            HomeNoticeBean bean = new HomeNoticeBean();

            DataBox dbox = bean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewPage()\r\n" + ex.getMessage());

            /*
             * /** �������׻󼼺��� (MainServelt)
             * 
             * @param request encapsulates the request to the servlet
             * 
             * @param response encapsulates the response from the servlet
             * 
             * @param box receive from the form object
             * 
             * @param out printwriter object
             * 
             * @return void
             * 
             * public void performSelectNoticeView(HttpServletRequest request,
             * HttpServletResponse response, RequestBox box, PrintWriter out)
             * throws Exception { try { request.setAttribute("requestbox", box);
             * //��������� box ��ü�� �Ѱ��ش�
             * 
             * String v_userip = request.getRemoteAddr(); // ������ ip
             * box.put("p_userip", v_userip); /* ===================== �������� ����
             * ========================= NoticeAdminBean nbean = new
             * NoticeAdminBean();
             * 
             * int tabseq =
             * StringManager.toInt(box.getStringDefault("p_tabseq","")); if
             * (tabseq == 0) { /*------- �Խ��� �з��� ���� �κ� ���� -----
             * box.put("p_type", "HN"); box.put("p_grcode", "0000000");
             * box.put("p_comp", "0000000000"); box.put("p_subj", "0000000000");
             * box.put("p_year", "0000"); box.put("p_subjseq", "0000");
             * /*---------------------------------------- tabseq =
             * nbean.selectTableseq(box); if (tabseq == 0) { String msg =
             * "�Խ��������� �����ϴ�."; AlertManager.historyBack(out, msg); }
             * box.put("p_tabseq", String.valueOf(tabseq)); }
             * 
             * 
             * DataBox dbox = nbean.selectViewNotice(box);
             * request.setAttribute("selectNotice", dbox); String v_url = "";
             * v_url = "/learn/user/portal/helpdesk/zu_Notice_R.jsp"; //String
             * v_url = "/index.jsp";
             * 
             * ServletContext sc = getServletContext(); RequestDispatcher rd =
             * sc.getRequestDispatcher(v_url); rd.forward(request, response);
             * 
             * //Log.info.println(this, box, "Dispatch to " + v_url);
             * 
             * }catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, out);
             * throw new Exception("performMainList()\r\n" + ex.getMessage()); }
             * }
             */
        }
    }
}
