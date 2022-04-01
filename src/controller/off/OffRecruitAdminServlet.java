//**********************************************************
//  1. ��     ��:  �������ΰ��� �����ϴ� ����
//  2. ���α׷��� : OffRecruitAdminServlet.java
//  3. ��     ��:  �������ΰ��� ���� ���α׷�
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

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffRecruitBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffRecruitAdminServlet")
public class OffRecruitAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
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

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("OffRecruitAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("listPage")) {                             //in case of ���� ��� ȭ��
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insertPage")) {                    //in case of ���� ��� ȭ��
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) {                        //in case of ���� ���
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) {                    //in case of ���� ���� ȭ��
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) {                        //in case of ���� ����
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {               //  �����Ҷ�
                this.performDelete(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    //  �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffRecruitBean bean = new OffRecruitBean();

            int isOk = bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffRecruitAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffRecruitBean bean = new OffRecruitBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.InsertSubject(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffRecruitAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //    ��������� box ��ü�� �Ѱ��ش�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_Recruit_I.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �����ڵ帮��Ʈ VIEW
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_Recruit_L.jsp";
            box.put("s_subjsearchkey", box.getString("s_subjsearchkey"));
            if(box.getString("s_lowerclass").length()>0) {
                OffRecruitBean bean = new OffRecruitBean();
                request.setAttribute("resultList", bean.listPage(box));
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    //  �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            OffRecruitBean bean = new OffRecruitBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.update(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffRecruitAdminServlet";
            box.put("p_process", "listPage");
            //    ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //    ��������� box ��ü�� �Ѱ��ش�

            OffRecruitBean bean = new OffRecruitBean();

            request.setAttribute("resultData", bean.selectPage(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = null;
            if (!box.getBoolean("isSeq")) {
                rd = sc.getRequestDispatcher("/learn/admin/off/za_off_Recruit_U.jsp");
            }
            else rd = sc.getRequestDispatcher("/learn/admin/off/za_off_grseq_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
}

