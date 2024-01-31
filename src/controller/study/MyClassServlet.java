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
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.study.MyClassServlet")
public class MyClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        //        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            box.put("p_frmURL", request.getRequestURI().toString() + "?p_process=" + v_process);
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }
            /*
             * if(box.getSession("userid").equals("")){
             * request.setAttribute("tUrl",request.getRequestURI());
             * RequestDispatcher dispatcher =
             * request.getRequestDispatcher("/login.jsp");
             * dispatcher.forward(request,response); return; }
             */
            if (v_process.equals("EducationSubjectPage")) { // ���� ���ǽ�
                this.performEducationSubjectPage(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingSubjectPage")) { // �������� ���� on-line
                this.performEducationStudyingSubjectPage(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingOffSubjectPage")) { // �������� ���� off-line
                this.performEducationStudyingOffSubjectPage(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingOffScorePopup")) { // �������� ���� off-line �ܱ� �����˾�
                this.performEducationStudyingOffScorePopup(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingOffTermScorePopup")) { // �������� ���� off-line �б⺰ �����˾�
                this.performEducationStudyingOffTermScorePopup(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelPage")) { // ������û��� ������
                this.performProposeCancelPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancel")) { // ���� ��û ���
                this.performProposeCancel(request, response, box, out);
            }
            if (v_process.equals("CancelPropose")) { // ���� ��û ��� 2010.01.26
                this.performCancelPropose(request, response, box, out);
            }
            if (v_process.equals("ProposeHistoryPage")) { // on-line ������û �̷� ������
                this.performProposeHistoryPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelApplyPage")) { // on-line ���� ��û ��� ��û ������
                this.performProposeCancelApplyPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelApply")) { // on-line ���� ��û ��� ��û
                this.performProposeCancelApply(request, response, box, out);
            }
            if (v_process.equals("ProposeOffHistoryPage")) { // off-line ������û �̷� ������
                this.performProposeOffHistoryPage(request, response, box, out);
            }
            if (v_process.equals("ProposeOffCancelApplyPage")) { // off-line ���� ��û ��� ��û ������
                this.performProposeOffCancelApplyPage(request, response, box, out);
            }
            if (v_process.equals("ProposeOffCancelApply")) { // off-line ���� ��û ��� ��û
                this.performProposeOffCancelApply(request, response, box, out);
            }

            if (v_process.equals("CancelOffPropose")) { // off-line ���� ��û ��� 2010.01.27
                this.performCancelOffPropose(request, response, box, out);

            } else if (v_process.equals("StudyHistoryList")) { // ���� ���� �̷� on-line
                this.performStudyHistoryList(request, response, box, out);

            } else if (v_process.equals("StudyHistoryOffList")) { // ���� ���� �̷� off-line
                this.performStudyHistoryOffList(request, response, box, out);

            } else if (v_process.equals("StudyHistoryListSyuro")) { // ������ �¶���
                this.performStudyHistoryListSyuro(request, response, box, out);

            } else if (v_process.equals("StudyHistoryOffListSyuro")) { // ������ ��������
                this.performStudyHistoryOffListSyuro(request, response, box, out);

            } else if (v_process.equals("studyHistoryExcel")) { // ���� ���� �̷� EXCEL
                this.performStudyHistoryExcel(request, response, box, out);

            } else if (v_process.equals("SuryoJeung")) { // ������ ������
                this.performSuryoJeung(request, response, box, out);

            } else if (v_process.equals("SuryoJeungPage")) { // ������ ������
                this.performSuryoJeungPage(request, response, box, out);

            } else if (v_process.equals("ProposeOffCancelFirst")) { //�������� �������(���δϾ� ������)�� ��� ����Ÿ�� ����
                this.performProposeOffCancelFirst(request, response, box, out);

            } else if (v_process.equals("SulmunMove")) { //���� �������� ������ ���� �˾�
                this.performSulmunMove(request, response, box, out);

            } else if (v_process.equals("EduSulmunInsert")) { //���� �������� ������ ���� Insert
                this.performEduSulmunInsert(request, response, box, out);

            } else if ("celp".equals(v_process)) {
                this.performCelpViewPage(request, response, box, out); //�ڱ⿪������

            } else if (v_process.equals("manageFavorSubj")) {
                this.pefromManageFavorSubj(request, response, box, out); // ���� ���ϱ� ����

            } else if (v_process.equals("DashboardPage")) {
                this.performDashboardPage(request, response, box, out); // ���� ���ϱ� ����

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

            //���� ����Ʈ
            /*
             * MyPointBean beanPoint = new MyPointBean(); // ���� ����Ʈ int
             * iGetPoint = beanPoint.selectGetPoint(box); //���� ���� ����Ʈ int
             * iUsePoint = beanPoint.selectUsePoint(box); //���� ��� ����Ʈ int
             * iWaitPoint = beanPoint.selectWaitPoint(box); //���� ��� ����Ʈ
             * 
             * box.put("p_mypoint", String.valueOf(iGetPoint - iUsePoint -
             * iWaitPoint));
             */
            MyClassBean bean = new MyClassBean();

            //���� �����̷� �Ǽ�
            int iStudyHistoryCnt = bean.selectGetStudyHistoryCnt(box);
            box.put("p_studyhistorycnt", String.valueOf(iStudyHistoryCnt));

            //�¶��� ���� �Ǽ�
            int iOnStudyCnt = bean.selectGetOnStudyCnt(box);
            box.put("p_onstudycnt", String.valueOf(iOnStudyCnt));

            //�������� ���� �Ǽ�
            int iOffStudyCnt = bean.selectGetOffStudyCnt(box);
            box.put("p_offstudycnt", String.valueOf(iOffStudyCnt));

            //���
            String sMessage = bean.selectGetMessage(box);
            box.put("p_message", sMessage);

            //�������� ���� ���
            ArrayList lists1 = bean.selectStudyingList(box);
            request.setAttribute("selectStudyingList", lists1);

            /*
             * //��������� ��ȸ ���� ��� ArrayList lists2 = bean.selectBillList(box);
             * request.setAttribute("selectBillList", lists2);
             */

            /*
             * //���ɰ��� ��� ArrayList lists3 = bean.selectInterestList(box);
             * request.setAttribute("selectInterestList", lists3);
             */
            //���������� �Ǽ�
            int iSubjQnaCnt = bean.selectGetSubjQnaCnt(box);
            box.put("p_subjqnacnt", String.valueOf(iSubjQnaCnt));

            //Q&A �Ǽ�
            int iQnaCnt = bean.selectGetQnaCnt(box);
            box.put("p_qnacnt", String.valueOf(iQnaCnt));

            //1vs1 �Ǽ�
            int iCounselCnt = bean.selectGetCounselCnt(box);
            box.put("p_counselcnt", String.valueOf(iCounselCnt));

            /*
             * //���� �̺�Ʈ�Ǽ� int iMyEventCnt = bean.selectGetMyEventCnt(box);
             * box.put("p_myeventcnt", String.valueOf(iMyEventCnt));
             * 
             * //���� ��÷ �̺�Ʈ�Ǽ� int iMyWinEventCnt =
             * bean.selectGetMyWinEventCnt(box); box.put("p_mywineventcnt",
             * String.valueOf(iMyWinEventCnt));
             * 
             * //���� ��ũ���Ǽ� int iMyWorkshopCnt = bean.selectGetMyWorkshopCnt(box);
             * box.put("p_myworkshopcnt", String.valueOf(iMyWorkshopCnt));
             */
            //���� ������û �̷°Ǽ�(�¶���/��������)
            int iProposeHistoryYCnt = bean.selectGetProposeHistoryYCnt(box);
            box.put("p_iProposeHistoryYCnt", String.valueOf(iProposeHistoryYCnt));

            //���� ������û �̷°Ǽ�(�¶���/���δ��)
            int iProposeHistoryBCnt = bean.selectGetProposeHistoryBCnt(box);
            box.put("p_iProposeHistoryBCnt", String.valueOf(iProposeHistoryBCnt));

            //���� ������û �̷°Ǽ�(��������/���δ��)
            int iOffProposeHistoryUCnt = bean.selectGetOffProposeHistoryUCnt(box);
            box.put("p_iOffProposeHistoryUCnt", String.valueOf(iOffProposeHistoryUCnt));

            //���� ������û �̷°Ǽ�(��������/��������)
            int iOffProposeHistoryYCnt = bean.selectGetOffProposeHistoryYCnt(box);
            box.put("p_iOffProposeHistoryYCnt", String.valueOf(iOffProposeHistoryYCnt));

            //this.performEducationSubjectList(request,response,box,out);  // �н����� ����
            //this.performProposeSubjectList(request,response,box,out);    // �н����� ����
            //this.performGraduationSubjectList(request,response,box,out); // �н��Ϸ� ����

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/study/gu_EducationSubject_L.jsp";
            } else {
                v_url = "/learn/user/portal/study/gu_EducationSubject_L.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EducationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ���� on-line
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationStudyingSubjectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            MyClassBean bean = new MyClassBean();
            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                //v_url = "/learn/user/2012/portal/study/gu_EducationStudyingSubject_L.jsp";
                v_url = "/learn/user/2013/portal/study/gu_EducationStudyingSubject_L.jsp";

                ArrayList lists = bean.selectSubjectList3(box, "I");
                request.setAttribute("EducationStudyingSubjectList", lists);

                //ArrayList listp = bean.selectSubjectList3(box, "P");
                //request.setAttribute("EducationProposeSubjectList", listp);

                ArrayList listh = bean.selectStudyHistoryTotList(box);
                request.setAttribute("StudyHistoryList", listh);

                // ���Ѱ��� ���
                ArrayList favorSubjectList = bean.selectFavorSubjectList(box);
                request.setAttribute("favorSubjectList", favorSubjectList);

            } else {
                v_url = "/learn/user/portal/study/gu_EducationStudyingSubject_L.jsp";
                if(box.getSession("tem_type").equals("B")){
                	v_url = "/learn/user/typeB/study/gu_EducationStudyingSubject_L.jsp";
                }
                ArrayList lists = bean.selectEducationSubjectList(box);
                request.setAttribute("EducationStudyingSubjectList", lists);
            }

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EducationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ���� off-line
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationStudyingOffSubjectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                //v_url = "/learn/user/2012/portal/study/gu_EducationStudyingOffSubject_L.jsp";
                v_url = "/learn/user/2013/portal/study/gu_EducationStudyingOffSubject_L.jsp";

                ArrayList list = bean.selectProposeOffHistoryList(box);
                request.setAttribute("ProposeOffHistoryList", list);

                ArrayList list2 = bean.selectStudyHistoryOffTotList(box);
                request.setAttribute("StudyHistoryOffList", list2);
            } else {
                v_url = "/learn/user/portal/study/gu_EducationStudyingOffSubject_L.jsp";
                ArrayList lists = bean.selectEducationOffSubjectList(box);
                request.setAttribute("EducationStudyingOffSubjectList", lists);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EducationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ���� off-line �б⺰ ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationStudyingOffTermScorePopup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MyClassBean bean = new MyClassBean();
            ArrayList lists = bean.selectEducationOffTermScoreList(box);
            request.setAttribute("EducationStudyingOffTermScoreList", lists);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/gu_EducationStudyingOffTermScore_P.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEducationStudyingOffTermScorePopup()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ���� off-line �ܱ�����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationStudyingOffScorePopup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MyClassBean bean = new MyClassBean();

            DataBox dbox = bean.selectEducationOffScoreList(box);
            request.setAttribute("EducationStudyingOffScoreList", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/gu_EducationStudyingOffScore_P.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEducationStudyingOffScorePopup()\r\n" + ex.getMessage());
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
    public void performProposeCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int isOk = bean.updateProposeCancel(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassServlet";
            box.put("p_process", "ProposeCancelPage");
            box.put("p_grcode", "G01");

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
     * ���� ��û ��� 2010.01.26 ������� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCancelPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int isOk = bean.updateCancelPropose(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassServlet";
            //box.put("p_process","ProposeHistoryPage");
            box.put("p_process", "EducationStudyingSubjectPage");

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
     * off-line ���� ��û ��� 2010.01.27 ������� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCancelOffPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int isOk = bean.updateCancelOffPropose(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassServlet";
            box.put("p_process", "EducationStudyingOffSubjectPage");

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
            ArrayList list = bean.selectCancelPossibleList(box);

            request.setAttribute("CancelPossibleList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_ProposeCancel_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û Ȯ��/��� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProposeHistoryPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/course/gu_ProposeHistory_L.jsp";
            } else {
                v_url = "/learn/user/portal/course/gu_ProposeHistory_L.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectProposeHistoryList(box);

            request.setAttribute("ProposeHistoryList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û ��ҽ�û ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProposeCancelApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectSubjnmList(box);

            request.setAttribute("SubjnmList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_ProposeCancelApply_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û ��� ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProposeOffCancelApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int isOk = bean.updateProposeOffCancelApply(box);
            String v_msg = "������û ��Ұ� ó���Ǿ����ϴ�.";
            String v_url = "/servlet/controller.study.MyClassServlet";
            box.put("p_process", "ProposeOffHistoryPage");
            //box.put("p_grcode","G01");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "propcancel.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "propcancel.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeOffCancelApply()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û Ȯ��/��� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProposeOffHistoryPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/course/gu_ProposeOffHistory_L.jsp";
            } else {
                v_url = "/learn/user/portal/course/gu_ProposeOffHistory_L.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectProposeOffHistoryList(box);

            request.setAttribute("ProposeOffHistoryList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeOffHistoryPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������û ��ҽ�û ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProposeOffCancelApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectOffSubjnmList(box);

            request.setAttribute("SubjnmList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/course/gu_ProposeOffCancelApply_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeOffCancelApplyPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ��û ��� ��û
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performProposeCancelApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int isOk = bean.updateProposeCancelApply(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassServlet";
            box.put("p_process", "ProposeHistoryPage");
            //box.put("p_grcode","G01");

            String msg_sw = box.getSession("msg");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                if (msg_sw.equals(""))
                    v_msg = "propcancel.ok";
                else
                    v_msg = msg_sw;
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
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
            ArrayList list1 = bean.selectEducationSubjectList(box);

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
            ArrayList list1 = bean.selectProposeSubjectList(box);

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
            ArrayList list1 = bean.selectGraduationSubjectList(box);

            request.setAttribute("GraduationSubjectList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("GraduationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���� �̷� on-line
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("rawtypes")
	public void performStudyHistoryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/study/gu_StudyHistoryTotal_L.jsp";
            } else {
                v_url = "/learn/user/portal/study/gu_StudyHistoryTotal_L.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/study/gu_StudyHistoryTotal_L.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectStudyHistoryTotList(box);

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
     * ���� ���� �̷� ���� on-line
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void performStudyHistoryListSyuro(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/study/gu_StudyHistoryTotalSyuro_L.jsp";
            } else {
                v_url = "/learn/user/portal/study/gu_StudyHistoryTotalSyuro_L.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectStudyHistoryTotList(box);

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
     * ���� ���� �̷� of-line
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudyHistoryOffList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/study/gu_StudyHistoryOffTotal_L.jsp";
            } else {
                v_url = "/learn/user/portal/study/gu_StudyHistoryOffTotal_L.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectStudyHistoryOffTotList(box);

            request.setAttribute("StudyHistoryOffList", list);
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
     * ���� ���� �̷� of-line
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performStudyHistoryOffListSyuro(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2012/portal/study/gu_StudyHistoryOffTotalSyuro_L.jsp";
            } else {
                v_url = "/learn/user/portal/study/gu_StudyHistoryOffTotal_L.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.selectStudyHistoryOffTotList(box);

            request.setAttribute("StudyHistoryOffList", list);
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
     * �������� �������(�����̳� ������)�� ����Ÿ�� ����
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performProposeOffCancelFirst(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int okcount = bean.deleteOffLinePropose(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage";
            box.put("onOff", 2);
            box.put("p_upperclass", "ALL");

            //box.put("p_process","ProposeOffHistoryPage");
            //box.put("p_grcode","G01");

            //String msg_sw=box.getSession("msg");

            AlertManager alert = new AlertManager();

            if (okcount > 0) {
                v_msg = "���������� ��� �Ǿ����ϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "������û ��� ó���� ���еǾ����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudyHistoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �Ϲ� Ȩ������ ���� ���� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunMove(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            // MyClassBean bean = new MyClassBean();

            //          DataBox dbox  = bean.getSuryoInfo(box);
            //          request.setAttribute("SuryoInfo", dbox);

            ServletContext sc = getServletContext();
            //          RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SuryoJeung_P.jsp");
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/sulmun/zu_OpenSulmun.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeMemberDivision()\r\n" + ex.getMessage());
        }
    }

    /**
     * �Ϲ� Ȩ������ ���� ���� INSERT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduSulmunInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            String v_msg = "";
            //          String v_url  = "/servlet/controller.study.MyClassServlet";

            AlertManager alert = new AlertManager();

            MyClassBean bean = new MyClassBean();
            int is_Ok = bean.insertEduService(box);

            if (is_Ok == 1) {
                v_msg = "������ �Ϸ� �Ǿ����ϴ�.";
                //              alert.alertOkMessage(out,v_msg,v_url,box);
                //              alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                //              alert.alertFailMessage(out,v_msg);
                alert.alertEduServiceSul(out, v_msg);
            } else {
                v_msg = "���� ���忡 �����Ͽ����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }

            //          Log.info.println(this, box, "Dispatch to /learn/user/game/homepage/gu_LossPwd_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEduSulmunInsert()\r\n" + ex.getMessage());
        }

    }

    public void performCelpViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            v_url = "/learn/user/2012/portal/study/gu_StudyCelp_L.jsp";

            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���ϱ� ����� ����ϴ� �޼����̴�. ��� �� ��� ����� �����Ѵ�. ���� ���� / ���� ������ ��� �ش�ȴ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void pefromManageFavorSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            StringBuffer sb = new StringBuffer();

            String jobType = box.getString("jobType"); // �۾� ���� (register: ��� / cancel: ���)

            boolean isLogin = box.getSession("userid").equals("") ? false : true;

            if (isLogin) {

                MyClassBean bean = new MyClassBean();

                int resultCnt = 0;
                if (jobType.equals("register")) {
                    resultCnt = bean.registerSubjFavor(box);
                } else {
                    resultCnt = bean.cancelSubjFavor(box);
                }

                sb.append("{\"isLogin\" : true, \n");
                sb.append(" \"resultCnt\" : ").append(resultCnt).append("}");
            } else {
                sb.append("{\"isLogin\" : false, \n");
                sb.append(" \"resultCnt\" : 0 }");
            }

            out.write(sb.toString());
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " peformB2BLoginPage()\r\n" + ex.getMessage());
        }

    }

    public void performDashboardPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String v_url = "";
            v_url = "/learn/user/typeB/study/gu_DashboardPage_L.jsp";

            MyClassBean bean = new MyClassBean();
            ArrayList list = bean.dashBoardStudyList(box);
            ArrayList cntList = bean.selectDashboardCntList(box);
            ArrayList cateList = bean.selectDashboardCateList(box);

            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�
            request.setAttribute("dashBoardStudyList", list);
            request.setAttribute("dashBoardStudyListCnt", list.size());

            request.setAttribute("cntList", cntList);
            request.setAttribute("cateList", cateList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDashboardPage()\r\n" + ex.getMessage());
        }
    }
}