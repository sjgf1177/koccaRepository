//*********************************************************
//  1. 제      목: MYCLASS USER SERVLET
//  2. 프로그램명: MyClassServlet.java
//  3. 개      요: 나의학습실 사용자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
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

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
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
            if (v_process.equals("EducationSubjectPage")) { // 나의 강의실
                this.performEducationSubjectPage(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingSubjectPage")) { // 수강중인 과정 on-line
                this.performEducationStudyingSubjectPage(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingOffSubjectPage")) { // 수강중인 과정 off-line
                this.performEducationStudyingOffSubjectPage(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingOffScorePopup")) { // 수강중인 과정 off-line 단기 성적팝업
                this.performEducationStudyingOffScorePopup(request, response, box, out);
            }
            if (v_process.equals("EducationStudyingOffTermScorePopup")) { // 수강중인 과정 off-line 학기별 성적팝업
                this.performEducationStudyingOffTermScorePopup(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelPage")) { // 수강신청취소 페이지
                this.performProposeCancelPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancel")) { // 수강 신청 취소
                this.performProposeCancel(request, response, box, out);
            }
            if (v_process.equals("CancelPropose")) { // 수강 신청 취소 2010.01.26
                this.performCancelPropose(request, response, box, out);
            }
            if (v_process.equals("ProposeHistoryPage")) { // on-line 수강신청 이력 페이지
                this.performProposeHistoryPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelApplyPage")) { // on-line 수강 신청 취소 신청 페이지
                this.performProposeCancelApplyPage(request, response, box, out);
            }
            if (v_process.equals("ProposeCancelApply")) { // on-line 수강 신청 취소 신청
                this.performProposeCancelApply(request, response, box, out);
            }
            if (v_process.equals("ProposeOffHistoryPage")) { // off-line 수강신청 이력 페이지
                this.performProposeOffHistoryPage(request, response, box, out);
            }
            if (v_process.equals("ProposeOffCancelApplyPage")) { // off-line 수강 신청 취소 신청 페이지
                this.performProposeOffCancelApplyPage(request, response, box, out);
            }
            if (v_process.equals("ProposeOffCancelApply")) { // off-line 수강 신청 취소 신청
                this.performProposeOffCancelApply(request, response, box, out);
            }

            if (v_process.equals("CancelOffPropose")) { // off-line 수강 신청 취소 2010.01.27
                this.performCancelOffPropose(request, response, box, out);

            } else if (v_process.equals("StudyHistoryList")) { // 개인 교육 이력 on-line
                this.performStudyHistoryList(request, response, box, out);

            } else if (v_process.equals("StudyHistoryOffList")) { // 개인 교육 이력 off-line
                this.performStudyHistoryOffList(request, response, box, out);

            } else if (v_process.equals("StudyHistoryListSyuro")) { // 수료증 온라인
                this.performStudyHistoryListSyuro(request, response, box, out);

            } else if (v_process.equals("StudyHistoryOffListSyuro")) { // 수료증 오프라인
                this.performStudyHistoryOffListSyuro(request, response, box, out);

            } else if (v_process.equals("studyHistoryExcel")) { // 개인 교육 이력 EXCEL
                this.performStudyHistoryExcel(request, response, box, out);

            } else if (v_process.equals("SuryoJeung")) { // 수료증 프레임
                this.performSuryoJeung(request, response, box, out);

            } else if (v_process.equals("SuryoJeungPage")) { // 수료증 페이지
                this.performSuryoJeungPage(request, response, box, out);

            } else if (v_process.equals("ProposeOffCancelFirst")) { //오프라인 수강취소(승인니아 결재전)할 경우 데이타만 삭제
                this.performProposeOffCancelFirst(request, response, box, out);

            } else if (v_process.equals("SulmunMove")) { //메인 교육서비스 만족도 설문 팝업
                this.performSulmunMove(request, response, box, out);

            } else if (v_process.equals("EduSulmunInsert")) { //메인 교육서비스 만족도 설문 Insert
                this.performEduSulmunInsert(request, response, box, out);

            } else if ("celp".equals(v_process)) {
                this.performCelpViewPage(request, response, box, out); //자기역량진단

            } else if (v_process.equals("manageFavorSubj")) {
                this.pefromManageFavorSubj(request, response, box, out); // 과정 찜하기 관리

            } else if (v_process.equals("DashboardPage")) {
                this.performDashboardPage(request, response, box, out); // 과정 찜하기 관리

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 나의 강의실
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

            //나의 포인트
            /*
             * MyPointBean beanPoint = new MyPointBean(); // 나의 포인트 int
             * iGetPoint = beanPoint.selectGetPoint(box); //나의 보유 포인트 int
             * iUsePoint = beanPoint.selectUsePoint(box); //나의 사용 포인트 int
             * iWaitPoint = beanPoint.selectWaitPoint(box); //나의 대기 포인트
             * 
             * box.put("p_mypoint", String.valueOf(iGetPoint - iUsePoint -
             * iWaitPoint));
             */
            MyClassBean bean = new MyClassBean();

            //나의 교육이력 건수
            int iStudyHistoryCnt = bean.selectGetStudyHistoryCnt(box);
            box.put("p_studyhistorycnt", String.valueOf(iStudyHistoryCnt));

            //온라인 수강 건수
            int iOnStudyCnt = bean.selectGetOnStudyCnt(box);
            box.put("p_onstudycnt", String.valueOf(iOnStudyCnt));

            //오프라인 수강 건수
            int iOffStudyCnt = bean.selectGetOffStudyCnt(box);
            box.put("p_offstudycnt", String.valueOf(iOffStudyCnt));

            //명언
            String sMessage = bean.selectGetMessage(box);
            box.put("p_message", sMessage);

            //수강중인 과정 목록
            ArrayList lists1 = bean.selectStudyingList(box);
            request.setAttribute("selectStudyingList", lists1);

            /*
             * //수강료결제 조회 납부 목록 ArrayList lists2 = bean.selectBillList(box);
             * request.setAttribute("selectBillList", lists2);
             */

            /*
             * //관심과정 목록 ArrayList lists3 = bean.selectInterestList(box);
             * request.setAttribute("selectInterestList", lists3);
             */
            //과정질문방 건수
            int iSubjQnaCnt = bean.selectGetSubjQnaCnt(box);
            box.put("p_subjqnacnt", String.valueOf(iSubjQnaCnt));

            //Q&A 건수
            int iQnaCnt = bean.selectGetQnaCnt(box);
            box.put("p_qnacnt", String.valueOf(iQnaCnt));

            //1vs1 건수
            int iCounselCnt = bean.selectGetCounselCnt(box);
            box.put("p_counselcnt", String.valueOf(iCounselCnt));

            /*
             * //나의 이벤트건수 int iMyEventCnt = bean.selectGetMyEventCnt(box);
             * box.put("p_myeventcnt", String.valueOf(iMyEventCnt));
             * 
             * //나의 당첨 이벤트건수 int iMyWinEventCnt =
             * bean.selectGetMyWinEventCnt(box); box.put("p_mywineventcnt",
             * String.valueOf(iMyWinEventCnt));
             * 
             * //나의 워크샵건수 int iMyWorkshopCnt = bean.selectGetMyWorkshopCnt(box);
             * box.put("p_myworkshopcnt", String.valueOf(iMyWorkshopCnt));
             */
            //나의 수강신청 이력건수(온라인/최종승인)
            int iProposeHistoryYCnt = bean.selectGetProposeHistoryYCnt(box);
            box.put("p_iProposeHistoryYCnt", String.valueOf(iProposeHistoryYCnt));

            //나의 수강신청 이력건수(온라인/승인대기)
            int iProposeHistoryBCnt = bean.selectGetProposeHistoryBCnt(box);
            box.put("p_iProposeHistoryBCnt", String.valueOf(iProposeHistoryBCnt));

            //나의 수강신청 이력건수(오프라인/승인대기)
            int iOffProposeHistoryUCnt = bean.selectGetOffProposeHistoryUCnt(box);
            box.put("p_iOffProposeHistoryUCnt", String.valueOf(iOffProposeHistoryUCnt));

            //나의 수강신청 이력건수(오프라인/최종승인)
            int iOffProposeHistoryYCnt = bean.selectGetOffProposeHistoryYCnt(box);
            box.put("p_iOffProposeHistoryYCnt", String.valueOf(iOffProposeHistoryYCnt));

            //this.performEducationSubjectList(request,response,box,out);  // 학습중인 과정
            //this.performProposeSubjectList(request,response,box,out);    // 학습예정 과정
            //this.performGraduationSubjectList(request,response,box,out); // 학습완료 과정

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
     * 수강중인 과정 on-line
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

                // 찜한과정 목록
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
     * 수강중인 과정 off-line
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
     * 수강중인 과정 off-line 학기별 점수
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
     * 수강중인 과정 off-line 단기점수
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
     * 수강 신청 취소
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
     * 수강 신청 취소 2010.01.26 결제취소 포함
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
     * off-line 수강 신청 취소 2010.01.27 결제취소 포함
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
     * 수강신청취소 페이지
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
     * 수강신청 확인/취소 페이지
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
     * 수강신청 취소신청 페이지
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
     * 수강 신청 취소 신청
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
            String v_msg = "수강신청 취소가 처리되었습니다.";
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
     * 수강신청 확인/취소 페이지
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
     * 수강신청 취소신청 페이지
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
     * 수강 신청 취소 신청
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
     * 학습중인 과정
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
     * 학습예정 과정
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
     * 학습완료 과정
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
     * 개인 교육 이력 on-line
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
     * 개인 교육 이력 수료 on-line
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
     * 개인 교육 이력 of-line
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
     * 개인 교육 이력 of-line
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
     * 개인 교육 이력 EXCEL
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
     * 수료증 프레임
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
     * 수료증 페이지
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
     * 오프라인 수강취소(승인이나 결재전)로 데이타만 삭제
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
                v_msg = "정상적으로 취소 되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "수강신청 취소 처리가 실패되었습니다.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("StudyHistoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 일반 홈페이지 메인 설문 띄우기
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
     * 일반 홈페이지 메인 설문 INSERT
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
                v_msg = "설문이 완료 되었습니다.";
                //              alert.alertOkMessage(out,v_msg,v_url,box);
                //              alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                //              alert.alertFailMessage(out,v_msg);
                alert.alertEduServiceSul(out, v_msg);
            } else {
                v_msg = "설문 저장에 실패하였습니다.";
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

            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 찜하기 기능을 담당하는 메서드이다. 등록 및 취소 기능을 수행한다. 열린 강좌 / 정규 과정을 모두 해당된다.
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

            String jobType = box.getString("jobType"); // 작업 유형 (register: 등록 / cancel: 취소)

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

            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다
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