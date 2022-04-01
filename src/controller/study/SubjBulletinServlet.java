//**********************************************************
//  1. 제      목: SUBJ 게시판 SERVLET
//  2. 프로그램명: SubjBulletinServlet.java
//  3. 개      요: 과정  게시판 SERVLET
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2006. 05. 16
//  7. 수      정:
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
import com.credu.study.SubjBulletinBean;
import com.credu.system.AdminUtil;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.study.SubjBulletinServlet")
public class SubjBulletinServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5840240969703683226L;

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
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("SubjBulletinList")) {
                this.performSubjBulletinList(request, response, box, out);
            } else if (v_process.equals("SubjBulletinDetail")) {
                this.performSubjBulletinDetail(request, response, box, out);
            } else if (v_process.equals("SubjBulletinInsert")) { // 등록
                this.performSubjBulletinInsert(request, response, box, out);
            } else if (v_process.equals("SubjBulletinUpdate")) {
                this.performSubjBulletinUpdate(request, response, box, out);
            } else if (v_process.equals("SubjBulletinReply")) {
                this.performSubjBulletinReply(request, response, box, out);
            } else if (v_process.equals("SubjBulletinDelete")) {
                this.performSubjBulletinDelete(request, response, box, out);
            } else if (v_process.equals("SubjBulletinInsertPage")) {
                this.performSubjBulletinInsertPage(request, response, box, out);
            } else if (v_process.equals("SubjBulletinUpdatePage")) {
                this.performSubjBulletinUpdatePage(request, response, box, out);
            } else if (v_process.equals("SubjBulletinReplyPage")) {
                this.performSubjBulletinReplyPage(request, response, box, out);
            } else if (v_process.equals("SubjBulletinFrame")) { //리스트
                this.performSubjBulletinList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * QNA LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjBulletinList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            ArrayList<DataBox> list1 = bean.selectSubjBulletinList(box);

            request.setAttribute("SubjBulletinList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBulletin_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjBulletinList()\r\n" + ex.getMessage());
        }
    }

    /**
     * QNA Detail
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjBulletinDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            ArrayList<DataBox> list1 = bean.selectSubjBulletinDetail(box);

            request.setAttribute("SubjBulletinDetail", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBulletin_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjBulletinDetail()\r\n" + ex.getMessage());
        }
    }

    /**
     * Qna INSERT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjBulletinInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            int isOk = bean.qnaInsert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjBulletinServlet";
            box.put("p_process", "SubjBulletinList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjBulletinInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * qna UPDATE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjBulletinUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            int isOk = bean.updateQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjBulletinServlet";
            box.put("p_process", "SubjBulletinList");

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
            throw new Exception("SubjBulletinUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * qna REPLY
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjBulletinReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            int isOk = bean.replyQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjBulletinServlet";
            box.put("p_process", "SubjBulletinList");

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
            throw new Exception("SubjBulletinReply()\r\n" + ex.getMessage());
        }
    }

    /**
     * Qna DELETE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjBulletinDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            int isOk = bean.deleteQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjBulletinServlet";
            box.put("p_process", "SubjBulletinList");

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
            throw new Exception("SubjBulletinDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * QNA 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjBulletinInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBulletin_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjBulletinInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * QNA SUBJ UPDATE PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjBulletinUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            ArrayList<DataBox> list1 = bean.selectSubjBulletinDetail2(box);

            request.setAttribute("SubjBulletinDetail2", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBulletin_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjBulletinUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * QNA SUBJ REPLY PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjBulletinReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjBulletinBean bean = new SubjBulletinBean();
            ArrayList<DataBox> data = bean.selectSubjBulletinDetail2(box);

            request.setAttribute("SubjBulletinDetail2", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBulletin_A.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjBulletinReply()\r\n" + ex.getMessage());
        }
    }

    /**
     * 프레임셋
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjBulletinFrame(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjBulletin_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjBulletinFrame()\r\n" + ex.getMessage());
        }
    }

}