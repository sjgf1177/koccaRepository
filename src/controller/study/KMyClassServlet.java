//*********************************************************
//  1. ��      ��: MYCLASS USER SERVLET
//  2. ���α׷���: MyClassServlet.java
//  3. ��      ��: �����н��� ����� servlet
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��:
//  7. ��      ��:
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
import com.credu.study.MyClassBean;
import com.credu.study.MyQnaBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.study.KMyClassServlet")
public class KMyClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2193192794809181571L;

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

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("EducationSubjectPage")) { // ���� ���ǽ�
                this.performEducationSubjectPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelPage")) { // ������û��� ������
                this.performProposeCancelPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancel")) { // ���� ��û ���
                this.performProposeCancel(request, response, box, out);
            } else if (v_process.equals("StudyHistoryList")) { // ���� ���� �̷�
                this.performStudyHistoryList(request, response, box, out);
            } else if (v_process.equals("studyHistoryExcel")) { // ���� ���� �̷� EXCEL
                this.performStudyHistoryExcel(request, response, box, out);
            }

            else if (v_process.equals("SuryoJeung")) { // ������ ������
                this.performSuryoJeung(request, response, box, out);
            } else if (v_process.equals("SuryoJeungPage")) { // ������ ������
                this.performSuryoJeungPage(request, response, box, out);

            } else if (v_process.equals("MyConsult")) { // ���� ��㳻��(�н�����)
                this.performMyConsultPage(request, response, box, out);

            } else if (v_process.equals("MyConsultView")) { // ���� ��㳻�� �󼼺���(�н�����)
                this.performMyConsult(request, response, box, out);

            } else if (v_process.equals("MyQnaSiteListPage")) { // ���� ��㳻��(����Ʈ����)
                this.performMyQnaSiteListPage(request, response, box, out);

            } else if (v_process.equals("MyQnaSiteViewPage")) { // ���� ��㳻�� �󼼺���(����Ʈ����)
                this.performMyQnaSiteViewPage(request, response, box, out);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ���� ���ǽ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationSubjectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            this.performEducationSubjectList(request, response, box, out); // �н����� ����
            this.performProposeSubjectList(request, response, box, out); // �н����� ����
            this.performGraduationSubjectList(request, response, box, out); // �н��Ϸ� ����

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/study/ku_EducationSubject_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EducationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performProposeCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int isOk = bean.updateProposeCancel(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.KMyClassServlet";
            box.put("p_process", "ProposeCancelPage");
            box.put("p_grcode", "K01");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "propcancel.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "propcancel.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û��� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProposeCancelPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> list = bean.selectCancelPossibleList(box);

            request.setAttribute("CancelPossibleList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_ProposeCancel_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �н����� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> list1 = bean.selectEducationSubjectList(box);

            request.setAttribute("EducationSubjectList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EducationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �н����� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> list1 = bean.selectProposeSubjectList(box);

            request.setAttribute("ProposeSubjectList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �н��Ϸ� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performGraduationSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> list1 = bean.selectGraduationSubjectList(box);

            request.setAttribute("GraduationSubjectList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("GraduationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���� �̷�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudyHistoryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> list = bean.selectStudyHistoryTotList(box);
            String v_url = "/learn/user/kocca/study/ku_StudyHistoryTotal_L.jsp";

            request.setAttribute("StudyHistoryList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudyHistoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���� �̷� EXCEL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudyHistoryExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/study/zu_StudyHistory_E.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performStudyHistoryExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSuryoJeung(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SuryoJeung_E.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSuryoJeungPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            MyClassBean bean = new MyClassBean();

            DataBox dbox = bean.getSuryoInfo(box);
            request.setAttribute("SuryoInfo", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SuryoJeung_P.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��㳻�� (�н�����)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMyConsultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/kocca/study/ku_MyConsult_L.jsp";

            MyQnaBean bean = new MyQnaBean();
            ArrayList<DataBox> list1 = bean.SelectMyQnaStudyList(box); // �н�����
            request.setAttribute("MyQnaStudyListPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��㳻�� �󼼺��� (�н�����)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMyConsult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/kocca/study/ku_MyConsult_R.jsp";

            MyQnaBean bean = new MyQnaBean();
            DataBox dbox = bean.selectMyQnaStudy(box);

            request.setAttribute("MyQnaStudyViewPage", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��㳻�� (����Ʈ ����)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMyQnaSiteListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/kocca/study/ku_MyQnaSite_L.jsp";

            MyQnaBean bean = new MyQnaBean();
            ArrayList<DataBox> list1 = bean.SelectMyQnaSiteList(box);

            request.setAttribute("MyQnaSiteListPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }

    }

    /**
     * ���� ��㳻�� �󼼺��� (����Ʈ ����)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMyQnaSiteViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/kocca/study/ku_MyQnaSite_R.jsp";

            MyQnaBean bean = new MyQnaBean();
            DataBox dbox = bean.selectMyQnaSite(box);

            request.setAttribute("MyQnaSiteViewPage", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }

    }

}