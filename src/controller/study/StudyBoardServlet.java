//**********************************************************
//  1. ��      ��: �����Խ����� �����ϴ� ����
//  2. ���α׷���: StudyBoardServlet.java
//  3. ��      ��: �����Խ����� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 9. 6
//  7. ��      ��:
//**********************************************************

package controller.study;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.BoardBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.StudyCountBean;

@WebServlet("/servlet/controller.study.StudyBoardServlet")
public class StudyBoardServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6199932597943298794L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;
        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canModify = false;
        boolean v_canDelete = false;
        // boolean v_canReply  = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            //String path = request.getServletPath();
            String path = request.getRequestURI();

            box = BulletinManager.getState(path.substring(path.lastIndexOf(".") + 1, path.lastIndexOf("Servlet")), box, out);

            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
            v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
            v_canModify = BulletinManager.isAuthority(box, box.getString("p_canModify"));
            v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
            v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
            // v_canReply  = BulletinManager.isAuthority(box,box.getString("p_canReply"));

            if (v_process.equals("insertPage")) { //  ����������� �̵��Ҷ�
                if (v_canAppend)
                    this.performInsertPage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("insert")) { //  ����Ҷ�
                if (v_canAppend)
                    this.performInsert(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("insertContent")) { //  ����Ҷ�
                if (v_canAppend)
                    this.performContentInsert(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("updatePage")) { //  ������������ �̵��Ҷ�
                if (v_canModify)
                    this.performUpdatePage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("update")) { //  �����Ͽ� �����Ҷ�
                if (v_canModify)
                    this.performUpdate(request, response, box, out);
                else
                    this.errorPage(box, out);
            }
            if (v_process.equals("replyPage")) { //  �亯�������� �̵��Ҷ�
                //                if(v_canReply) this.performReplyPage(request, response, box, out);
                this.performReplyPage(request, response, box, out);
                //                else this.errorPage(box, out);
            } else if (v_process.equals("reply")) { //  �亯�Ҷ�
                this.performReply(request, response, box, out);
                //              if(v_canReply) this.performReply(request, response, box, out);
                //              else this.errorPage(box, out);
            } else if (v_process.equals("delete")) { //  �����Ҷ�
                if (v_canDelete)
                    this.performDelete(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("replySubmit")) { //  ���õ��
                if (v_canRead)
                    this.performInsertReply(request, response, box, out);
            } else if (v_process.equals("replyDelete")) { //  ���û���
                if (v_canRead)
                    this.performDeleteReply(request, response, box, out);
            } else if (v_process.equals("select")) { //  �󼼺����Ҷ�
                if (v_canRead)
                    this.performSelect(request, response, box, out);
            } else { //  ��ȸ�Ҷ�
                if (v_canRead)
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

            // ������ �޴� ���� ���� �߰�
            box.put("p_menu", "22");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            BoardBean bean = new BoardBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- �Խ��� �з��� ���� �κ� ���� -----*/
                box.put("p_type", "SB");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", box.getString("p_subj"));
                box.put("p_year", box.getString("p_year"));
                box.put("p_subjseq", box.getString("p_subjseq"));
                /*----------------------------------------*/

                tabseq = bean.selectSBTableseq(box);

                AlertManager alert = new AlertManager();
                if (tabseq == 0) {
                    String v_msg = "�߸��� �Խ����Դϴ�.";
                    alert.selfClose(out, v_msg);
                } else {
                    box.put("p_tabseq", String.valueOf(tabseq));
                }
            }

            ArrayList list = bean.selectBoardList(box);
            request.setAttribute("selectStudyBoardList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_StudyBoard_L.jsp");
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
    @SuppressWarnings("unchecked")
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            BoardBean bean = new BoardBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectStudyBoard", dbox);

            ArrayList list = bean.selectBoardReplyList(box);
            request.setAttribute("selectBoardReplyList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_StudyBoard_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_StudyBoard_I.jsp");
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
            /* ================ ���ϸ��� ��� ���� ============================ */
            //            String v_code   = "00000000000000000001";                      // �Խ��� �� �ø��� ���ϸ����ڵ�
            //            String s_userid = box.getSession("userid");
            //            int isOk3 = MileageManager.insertMileage(v_code, s_userid);    // ���ϸ��� �ۼ�
            /* ================ ���ϸ��� ��� ���� ============================ */

            BoardBean bean = new BoardBean();

            int isOk = bean.insertStudyBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyBoardServlet";
            box.put("p_process", "");

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
     * ���������� ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performContentInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardBean bean = new BoardBean();

            int v_tabseq = bean.selectSBTableseq(box);
            box.put("p_tabseq", v_tabseq);

            int isOk = bean.insertStudyBoard(box);

            if (!(isOk > 0)) {
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

            BoardBean bean = new BoardBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectStudyBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_StudyBoard_U.jsp");
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
            BoardBean bean = new BoardBean();

            int isOk = bean.updateBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyBoardServlet";
            box.put("p_process", "");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

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

            BoardBean bean = new BoardBean();
            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectStudyBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_StudyBoard_A.jsp");
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
            BoardBean bean = new BoardBean();

            int isOk = bean.replyBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyBoardServlet";
            box.put("p_process", "");

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
            BoardBean bean = new BoardBean();

            int isOk = bean.deleteBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyBoardServlet";
            box.put("p_process", "");

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

    /**
     * ���� ���
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performInsertReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            BoardBean bean = new BoardBean();

            int isOk = bean.insertReply(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyBoardServlet";
            box.put("p_process", "select");

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
            throw new Exception("performInsertReply()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ����
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performDeleteReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            BoardBean bean = new BoardBean();

            int isOk = bean.deleteReply(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyBoardServlet";
            box.put("p_process", "select");

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
            throw new Exception("performInsertReply()\r\n" + ex.getMessage());
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
