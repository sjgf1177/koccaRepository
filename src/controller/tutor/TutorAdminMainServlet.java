//*********************************************************
//  1. 제      목: 강사 메인
//  2. 프로그램명: TutorAdminMainServlet.java
//  3. 개      요: 강사 메인 관리자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//**********************************************************
package controller.tutor;

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
import com.credu.study.ProjectAdminBean;
import com.credu.study.QnaAdminBean;
import com.credu.study.QnaData;
import com.credu.system.AdminUtil;
import com.credu.tutor.CorrectionAdminBean;
import com.credu.tutor.NoticeAdminBean;
import com.credu.tutor.TutorAdminBean;

@WebServlet("/servlet/controller.tutor.TutorAdminMainServlet")
public class TutorAdminMainServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5578072033269435234L;

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

            // System.out.println("강사 메인 : " + v_process);

            if (v_process.equals(""))
                v_process = "listPage";

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("TutorAdminMainServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("listPage")) { //	 강사메인 리스트
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("QnaListPage")) {
                this.performQnaListPage(request, response, box, out);
            } else if (v_process.equals("QnaDetail")) {
                this.performQnaDetail(request, response, box, out);
            } else if (v_process.equals("QnaInsert")) {
                this.performQnaInsert(request, response, box, out);
            } else if (v_process.equals("QnaUpdate")) {
                this.performQnaUpdate(request, response, box, out);
            } else if (v_process.equals("QnaDelete")) {
                this.performQnaDelete(request, response, box, out);
            } else if (v_process.equals("QnaInsertPage")) {
                this.performQnaInsertPage(request, response, box, out);
            } else if (v_process.equals("QnaUpdatePage")) {
                this.performQnaUpdatePage(request, response, box, out);
            } else if (v_process.equals("ProjectDetailList")) { //in case of project detail list
                this.performProjectDetailList(request, response, box, out);
            } else if (v_process.equals("CorrectionList")) { //in case of project detail list
                this.performCorrectionList(request, response, box, out);
            } else if (v_process.equals("CorrectionInsert")) { //in case of project detail list
                this.performCorrectionInsert(request, response, box, out);
            } else if (v_process.equals("CorrectionUpdate")) { //in case of project detail list
                this.performCorrectionUpdate(request, response, box, out);
            } else if (v_process.equals("CorrectionDelete")) { //in case of project detail list
                this.performCorrectionDelete(request, response, box, out);
            } else if (v_process.equals("SendFreeMail")) { //in case of send free mail
                this.performSendFreeMail(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 강사메인 리스트
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

            TutorAdminBean bean = new TutorAdminBean();

            NoticeAdminBean bean2 = new NoticeAdminBean();

            ArrayList<DataBox> list1 = bean.selectTutorQnaList(box);
            request.setAttribute("qalist", list1);

            ArrayList<DataBox> list2 = bean.selectTutorReportList(box);
            request.setAttribute("reportlist", list2);

            // ArrayList list3 = bean.selectTutorPay(box);
            // request.setAttribute("paylist", list3); 

            ArrayList<DataBox> list3 = bean2.selectNoticeMain(box);
            request.setAttribute("noticelist", list3);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorMain_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 강사 리스트 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performQnaListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaList2(box);
            request.setAttribute("qnaList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorQna_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performQnaListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 강사 미응답 Q/A 내용보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performQnaDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaDetail(box);
            request.setAttribute("qnaDetail", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorQna_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
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
    public void performQnaInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.insertQna(box);
            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
            box.put("p_process", "QnaDetail");

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
            throw new Exception("performQnaInsert()\r\n" + ex.getMessage());
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
    public void performQnaUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            int isOk = bean.updateQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
            box.put("p_process", "QnaDetail");

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
            throw new Exception("performQnaUpdate()\r\n" + ex.getMessage());
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
    public void performQnaDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            int isOk = bean.deleteQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
            box.put("p_process", "QnaDetail");

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
            throw new Exception("performQnaDelete()\r\n" + ex.getMessage());
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
    public void performQnaInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaDetail(box);
            request.setAttribute("qnaDetail", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorQna_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performQnaInsertPage()\r\n" + ex.getMessage());
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
    public void performQnaUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaDetail(box);

            request.setAttribute("qnaDetail", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorQna_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performQnaUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * PROJECT DETAIL LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProjectDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProjectAdminBean bean = new ProjectAdminBean();
            ArrayList<DataBox> list1 = bean.selectProjectDetailListTutor(box);

            request.setAttribute("projectDetailList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorProjectDetail_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProjectDetailList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 첨삭 목록보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCorrectionList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CorrectionAdminBean bean = new CorrectionAdminBean();
            ArrayList<DataBox> list1 = bean.selectList(box);
            request.setAttribute("correctionList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorCorrection_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 첨삭 INSERT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCorrectionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CorrectionAdminBean bean = new CorrectionAdminBean();

            int isOk = bean.insert(box);
            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
            box.put("p_process", "CorrectionList");

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
            throw new Exception("performCorrectionInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 첨삭 UPDATE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCorrectionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CorrectionAdminBean bean = new CorrectionAdminBean();
            int isOk = bean.update(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
            box.put("p_process", "CorrectionList");

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
            throw new Exception("performCorrectionUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 첨삭 DELETE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCorrectionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CorrectionAdminBean bean = new CorrectionAdminBean();
            int isOk = bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.tutor.TutorAdminMainServlet";
            box.put("p_process", "CorrectionList");

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
            throw new Exception("performCorrectionDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * SEND FREE MAIL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSendFreeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }
}