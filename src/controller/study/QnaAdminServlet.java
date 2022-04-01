//**********************************************************
//  1. 제      목: QNA DATA
//  2. 프로그램명: QnaAdminBean.java
//  3. 개      요: 질문 admin bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김수진 2003. 8. 18
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
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.QnaAdminBean;
import com.credu.study.QnaData;
import com.credu.system.AdminUtil;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.study.QnaAdminServlet")
public class QnaAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3898207917984999613L;

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
        // MultipartRequest multi = null;
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

            if (!AdminUtil.getInstance().checkRWRight("QnaAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("QnaSubjList")) { //in case of activity submit list
                this.performQnaSubjList(request, response, box, out);
            } else if (v_process.equals("QnaSubjseqList")) {
                this.performQnaSubjseqList(request, response, box, out);
            } else if (v_process.equals("QnaList")) {
                this.performQnaList(request, response, box, out);
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
            } else if (v_process.equals("QnaAllList")) {
                this.performQnaAllList(request, response, box, out);
            }

            //  강사용 모듈
            else if (v_process.equals("PeriodQnaSubjList")) { //in case of activity submit list
                this.performPeriodQnaSubjList(request, response, box, out);
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
    public void performQnaSubjList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaSubjList(box);

            request.setAttribute("qnaSubjList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_QnaSubj_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("QnaSubjList()\r\n" + ex.getMessage());
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
    public void performQnaSubjseqList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaSubjseqList(box);

            request.setAttribute("qnaSubjseqList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_QnaSubjseq_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("QnaSubjseqList()\r\n" + ex.getMessage());
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
    public void performQnaList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaList(box);

            request.setAttribute("qnaList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Qna_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("QnaList()\r\n" + ex.getMessage());
        }
    }

    /**
     * QNA detail
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Qna_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("QnaDetail()\r\n" + ex.getMessage());
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
            String v_url = "/servlet/controller.study.QnaAdminServlet";
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
            throw new Exception("qnaInsert()\r\n" + ex.getMessage());
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
            String v_url = "/servlet/controller.study.QnaAdminServlet";
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
            throw new Exception("qnaUpdate()\r\n" + ex.getMessage());
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
            String v_url = "/servlet/controller.study.QnaAdminServlet";
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
            throw new Exception("qnaDelete()\r\n" + ex.getMessage());
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Qna_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("QnaInsert()\r\n" + ex.getMessage());
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Qna_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("QnaUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 질의응답전체조회
     * 
     * @return void
     */
    public void performQnaAllList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaAllList(box);

            request.setAttribute("qnaAllList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_QnaAllList_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("QnaAllList()\r\n" + ex.getMessage());
        }
    }

    // 화면수정
    /**
     * 강사용 QNA 화면 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPeriodQnaSubjList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<QnaData> list1 = bean.selectQnaSubjList(box);

            request.setAttribute("qnaSubjList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PeriodQnaSubj_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPeriodQnaSubjList()\r\n" + ex.getMessage());
        }
    }
}