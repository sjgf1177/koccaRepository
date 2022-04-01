//**********************************************************
//  1. ��      ��: ��������
//  2. ���α׷���:  NewsAdminServlet.java
//  3. ��      ��: �����Խ����� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 9. 6
//  7. ��      ��:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.BoardAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.NewsAdminServlet")
public class NewsAdminServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 2959970003402369708L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "selectList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            ///////////////////////////////////////////////////////////////////
            if (!v_process.equals("userSelect")) {
                if (!AdminUtil.getInstance().checkRWRight("NewsAdminServlet", v_process, out, box)) {
                    return;
                }
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if (v_process.equals("insertPage")) { //  ����������� �̵��Ҷ�
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { //  ����Ҷ�
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) { //  ������������ �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { //  �����Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            }
            if (v_process.equals("replyPage")) { //  �亯�������� �̵��Ҷ�
                this.performReplyPage(request, response, box, out);
            } else if (v_process.equals("reply")) { //  �亯�Ҷ�
                this.performReply(request, response, box, out);
            } else if (v_process.equals("delete")) { //  �����Ҷ�
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("select")) { //  �󼼺����Ҷ�
                this.performSelect(request, response, box, out);
            } else if (v_process.equals("userSelect")) { //  ����ڰ� �󼼺����Ҷ�
                this.performUserSelect(request, response, box, out);
            } else { //  ��ȸ�Ҷ�
                this.performSelectList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            BoardAdminBean bean = new BoardAdminBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            String v_type = box.getStringDefault("p_type", "HR");

            /*------- �Խ��� �з��� ���� �κ� ���� -----*/
            box.put("p_type", v_type);
            box.put("p_grcode", "0000000");
            box.put("p_comp", "0000000000");
            box.put("p_subj", "0000000000");
            box.put("p_year", "0000");
            box.put("p_subjseq", "0000");
            /*----------------------------------------*/

            tabseq = bean.selectTableseq(box);

            AlertManager alert = new AlertManager();
            if (tabseq == 0) {
                String v_msg = "�߸��� �Խ����Դϴ�.";
                alert.selfClose(out, v_msg);
            } else {
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("selectBoardList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NewsLetter_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            BoardAdminBean bean = new BoardAdminBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectBoard", dbox);

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("selectBoardList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NewsLetter_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����ڰ� �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUserSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            BoardAdminBean bean = new BoardAdminBean();
            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_NewsLetter_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NewsLetter_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
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
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            BoardAdminBean bean = new BoardAdminBean();
            int isOk = bean.insertBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NewsAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������������ �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            BoardAdminBean bean = new BoardAdminBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NewsLetter_U.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardAdminBean bean = new BoardAdminBean();

            int isOk = bean.updateBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NewsAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * �亯�������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            BoardAdminBean bean = new BoardAdminBean();
            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NewsLetter_A.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �亯�Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardAdminBean bean = new BoardAdminBean();

            int isOk = bean.replyBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NewsAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "reply.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "reply.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReply()\r\n" + ex.getMessage());
        }
    }

    /**
     * �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardAdminBean bean = new BoardAdminBean();

            int isOk = bean.deleteBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NewsAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void errorPage(RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            //  Log.sys.println();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}
