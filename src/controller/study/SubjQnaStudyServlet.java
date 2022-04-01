//**********************************************************
//  1. 제      목: SUBJ QNA SERVLET
//  2. 프로그램명: SubjQnaStudyServlet.java
//  3. 개      요: 과정  QNA SERVLET
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김영만 2003. 9. 7
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
import com.credu.study.QnaData;
import com.credu.study.SubjQnaBean;
import com.credu.system.AdminUtil;
import com.credu.system.StudyCountBean;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.study.SubjQnaStudyServlet")
public class SubjQnaStudyServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7688155867376465561L;

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
    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        //      MultipartRequest multi = null;
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

            if (v_process.equals("SubjQnaSubjList")) { //in case of activity submit list
                this.performSubjQnaSubjList(request, response, box, out);
            } else if (v_process.equals("SubjQnaSubjseqList")) {
                this.performSubjQnaSubjseqList(request, response, box, out);
            } else if (v_process.equals("SubjQnaList")) {
                this.performSubjQnaList(request, response, box, out);
            } else if (v_process.equals("SubjQnaDetail")) {
                this.performSubjQnaDetail(request, response, box, out);
            } else if (v_process.equals("SubjQnaInsert")) { // 등록
                this.performSubjQnaInsert(request, response, box, out);
            } else if (v_process.equals("SubjQnaUpdate")) {
                this.performSubjQnaUpdate(request, response, box, out);
            } else if (v_process.equals("SubjQnaReply")) {
                this.performSubjQnaReply(request, response, box, out);
            } else if (v_process.equals("SubjQnaDelete")) {
                this.performSubjQnaDelete(request, response, box, out);
            } else if (v_process.equals("SubjQnaInsertPage")) {
                this.performSubjQnaInsertPage(request, response, box, out);
            } else if (v_process.equals("SubjQnaUpdatePage")) {
                this.performSubjQnaUpdatePage(request, response, box, out);
            } else if (v_process.equals("SubjQnaReplyPage")) {
                this.performSubjQnaReplyPage(request, response, box, out);
            } else if (v_process.equals("SubjQnaFrame")) { // 질문방 리스트
                //this.performSubjQnaFrame(request, response, box, out);
                this.performSubjQnaList(request, response, box, out);
            } else if (v_process.equals("MySubjQnaList")) { //in case of propose member division
                this.performMySubjQnaList(request, response, box, out);
            } else if (v_process.equals("MySubjQnaView")) { //in case of propose member division
                this.performMySubjQnaView(request, response, box, out);
            } else if (v_process.equals("MkSubjQnaInsert")) { // 마케팅노트 등록
                this.performMkSubjQnaInsert(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * QNA SUBJ LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjQnaSubjList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            ArrayList<QnaData> list1 = bean.selectSubjQnaSubjList(box);

            request.setAttribute("SubjqnaSubjList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQnaSubj_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaSubjList()\r\n" + ex.getMessage());
        }
    }

    /**
     * QNA SUBJSEQ LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjQnaSubjseqList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();

            String v_gadminYn = bean.getGadminYn(box);
            request.setAttribute("GadminYn", v_gadminYn);

            ArrayList<QnaData> list1 = bean.selectSubjQnaSubjseqList(box);
            request.setAttribute("SubjqnaSubjseqList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQnaSubjseq_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaSubjseqList()\r\n" + ex.getMessage());
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
    @SuppressWarnings("unchecked")
    public void performSubjQnaList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            // 과정별 메뉴 접속 정보 추가
            box.put("p_menu", "03");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            ArrayList<DataBox> list1 = bean.selectSubjQnaList(box);
            request.setAttribute("SubjQnaList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQna_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaList()\r\n" + ex.getMessage());
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
    public void performSubjQnaDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            ArrayList<DataBox> list1 = bean.selectSubjQnaDetail(box);

            request.setAttribute("SubjqnaDetail", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQna_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaDetail()\r\n" + ex.getMessage());
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
    public void performSubjQnaInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            int isOk = bean.qnaInsert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjQnaStudyServlet";
            box.put("p_process", "SubjQnaList");

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
            throw new Exception("SubjqnaInsert()\r\n" + ex.getMessage());
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
    public void performSubjQnaUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            int isOk = bean.updateQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjQnaStudyServlet";
            box.put("p_process", "SubjQnaList");

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
            throw new Exception("SubjqnaUpdate()\r\n" + ex.getMessage());
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
    public void performSubjQnaReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            int isOk = bean.replyQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjQnaStudyServlet";
            box.put("p_process", "SubjQnaList");

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
            throw new Exception("SubjqnaReply()\r\n" + ex.getMessage());
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
    public void performSubjQnaDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            int isOk = bean.deleteQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjQnaStudyServlet";
            box.put("p_process", "SubjQnaList");

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
            throw new Exception("SubjqnaDelete()\r\n" + ex.getMessage());
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
    public void performSubjQnaInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQna_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaInsertPage()\r\n" + ex.getMessage());
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
    public void performSubjQnaUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            //ArrayList list1 = bean.selectSubjQnaDetail2(box);

            DataBox dbox = bean.selectSubjQnaDetail2(box);

            request.setAttribute("SubjqnaDetail2", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQna_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaUpdate()\r\n" + ex.getMessage());
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
    public void performSubjQnaReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            //ArrayList data = bean.selectSubjQnaDetail2(box);

            DataBox dbox = bean.selectSubjQnaDetail2(box);

            request.setAttribute("SubjqnaDetail2", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQna_A.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaReply()\r\n" + ex.getMessage());
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
    public void performSubjQnaFrame(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            //RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQna_F.jsp");
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjQna_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjQnaFrame()\r\n" + ex.getMessage());
        }
    }

    /**
     * 나의 학습질문 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMySubjQnaList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            ArrayList<DataBox> list = bean.mySubjQnaList(box);

            request.setAttribute("mySubjQnaList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_MySubjQna_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }
    }

    /**
     * 나의 학습질문 내용보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMySubjQnaView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            ArrayList<DataBox> list = bean.selectSubjQnaDetail(box);

            request.setAttribute("mySubjQnaView", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_MySubjQna_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }
    }

    /**
     * 마케팅 노트 INSERT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMkSubjQnaInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjQnaBean bean = new SubjQnaBean();
            int isOk = bean.MkqnaInsert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjQnaStudyServlet";
            box.put("p_process", "SubjQnaList");

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
            throw new Exception("SubjqnaInsert()\r\n" + ex.getMessage());
        }
    }
}