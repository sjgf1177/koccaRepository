// *********************************************************
// 1. 제 목: SUBJECT INFORMATION USER SERVLET
// 2. 프로그램명: ProposeCourseServlet.java
// 3. 개 요: 과정안내 사용자 servlet
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 박진희 2003. 8. 19
// 7. 수 정:
// **********************************************************
package controller.propose;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.SearchAdminBean;
import com.credu.homepage.InstitutionAdminBean;
import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.ConfigSet;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.SmsBean;
import com.credu.library.StringManager;
import com.credu.mobile.subj.SubjectBean;
import com.credu.propose.ProposeCourseBean;
import com.credu.research.SulmunRegistResultBean;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.propose.ProposeCourseServlet")
public class ProposeCourseServlet extends javax.servlet.http.HttpServlet implements Serializable {
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
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            v_process = box.getString("p_process");
            
            box.put("p_grgubun", "G01");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            if (v_process.equals("SubjectCreduPropose")) {
                String creduuserid = box.getString("p_userid");
                box.setSession("userid", creduuserid);
            }

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            // 과정미리 보기 로그인 체크 안함 ( 추천과정에서 사용)

            if (!(v_process.equals("SubjectPreviewPopup") || v_process.equals("SubjectList") || v_process.equals("SubjectPreviewPage") || v_process.equals("TotalSubjectList") || v_process.equals("Curriculum") || v_process.equals("EduSystem")
                    || v_process.equals("SubjectListJikmu") || v_process.equals("SubjectListJikup") || v_process.equals("previewSubjDetailPage") || v_process.equals("LiteratureSubjectList") || v_process.equals("selectReviewListForAjax"))) {
                if (!AdminUtil.getInstance().checkLogin(out, box)) {
                    this.LoginChk(request, response, box, out);
                }
            }

            if (v_process.equals("SubjectList")) { // 과정리스트
                this.performSubjectList(request, response, box, out);

            } else if (v_process.equals("SubjectListJikmu")) { // 과정리스트 /직무
                this.performSubjectListJikmu(request, response, box, out);

            } else if (v_process.equals("SubjectListJikup")) { // 과정리스트 /직업
                this.performSubjectListJikup(request, response, box, out);

            } else if (v_process.equals("TotalSubjectList")) { // 전체과정보기
                this.performTotalSubjectList(request, response, box, out);

            } else if (v_process.equals("SubjectListAll")) { // 전체과정보기 (사용안함)
                this.performSubjectListAll(request, response, box, out);

            } else if (v_process.equals("SubjectListBest")) { // 인기 과정 보기
                this.performSubjectListBest(request, response, box, out);

            } else if (v_process.equals("SubjectPreviewPage")) { // 과정 미리보기
                this.performSubjectPreviewPage(request, response, box, out);

            } else if (v_process.equals("SubjectPreviewPopup")) { // 과정 미리보기 팝업
                this.performSubjectPreviewPopup(request, response, box, out);

            } else if (v_process.equals("SeqPreviewPage")) { // 차수정보 미리보기
                this.performSubjSeqPreviewPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposePage")) { // 수강신청 화면
                this.performSubjectEduProposePage(request, response, box, out);

            } else if (v_process.equals("SubjectEduPropose")) { // 수강신청
                this.performSubjectEduPropose(request, response, box, out);

            } else if (v_process.equals("SpotSubjectEduPropose")) { // 수강신청(상시)
                this.performSpotSubjectEduPropose(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeDelete")) { // 수강신청삭제
                this.performSubjectEduProposeDelete(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeListPage")) { // 수강신청목록
                this.performSubjectEduProposeListPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeCheckListPage")) { // 수강신청 결제확인-최후통첩
                this.performSubjectEduProposeCheckListPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduProposeChief")) { // in case of subject education propose
                this.performSubjectEduProposeChief(request, response, box, out);

            } else if (v_process.equals("SubjectAppProposeOpenPage")) { // in case of subject approval propose page
                this.performSubjectAppProposeOpenPage(request, response, box, out);

            } else if (v_process.equals("OfficerChoiceOpenPage")) { // in case of officer choice open page
                this.performOfficerChoiceOpenPage(request, response, box, out);

            } else if (v_process.equals("SubjectCreduPropose")) { // in case of subject credu propose
                this.performSubjectCreduPropose(request, response, box, out);

            } else if (v_process.equals("SubjectCreduPropose2")) { // in case of subject credu propose
                this.performSubjectCreduPropose2(request, response, box, out);

            } else if (v_process.equals("EducationYearlySchedule")) { // 연간교육일정
                this.performEducationYearlySchedule(request, response, box, out);

            } else if (v_process.equals("EducationMonthlySchedule")) { // 월간교육일정
                this.performEducationMonthlySchedule(request, response, box, out);

            } else if (v_process.equals("CourseSubjOpenPage")) { // in case of course subject view open page
                this.performCourseSubjOpenPage(request, response, box, out);

            } else if (v_process.equals("ContentResearch")) { // 컨테츠 설문결과
                this.performContentResearch(request, response, box, out);

            } else if (v_process.equals("ProposeListPage")) { // 신청명단조회
                this.performProposeListPage(request, response, box, out);

            } else if (v_process.equals("LectureList")) { // 강의목차 리스트
                this.performLectureList(request, response, box, out);

            } else if (v_process.equals("insertPreviewLog")) { // 맛보기 로그
                this.performInsertPreviewLog(request, response, box, out);

            } else if (v_process.equals("OffLineApply")) { // 오프라인 신청 할때
                this.performApply(request, response, box, out);

            } else if (v_process.equals("OffLineCancel")) { // 오프라인 신청 취소할때
                this.performApplyCancel(request, response, box, out);

            } else if (v_process.equals("OffLineSubjPage")) { // 오프라인 신청/취소 페이지로
                // 이동
                this.performApplyPage(request, response, box, out);

            } else if (v_process.equals("OffLineView")) { // 오프라인 신청/취소 상세보기
                this.performApplyViewPage(request, response, box, out);

            } else if (v_process.equals("SubjectEduBill")) { // 수강신청전 지불 수단 페이지.
                this.performEduBill(request, response, box, out);

            } else if (v_process.equals("SubjectEduInputBill")) { // 무통장입금 페이지.
                this.performEduInputBill(request, response, box, out);

            } else if (v_process.equals("BillCheck")) { // 무통장입금 페이지 (입금자 입력).
                this.performEduInputCheck(request, response, box, out);

            } else if (v_process.equals("SubjectEduInputCredit")) { // 카드 결제 입력
                // ( Link)
                this.performEduInputCredit(request, response, box, out);

            } else if (v_process.equals("SubjectIntro")) { // 수강신청안내
                this.performSubjectIntro(request, response, box, out);

            } else if (v_process.equals("CreditCard")) { // 카드 결제시 분기.
                this.performCreditCard(request, response, box, out);

            } else if (v_process.equals("Curriculum")) { // 커리큘럽.
                this.performCurriculum(request, response, box, out);

            } else if (v_process.equals("EduSystem")) { // 온라인교육체계도
                this.performEduSystem(request, response, box, out);

            } else if (v_process.equals("previewSubjDetailPage")) { // 과정명 만으로 현재 수강신청 과정 상세페이지로 이동
                this.performPreviewSubjDetailPage(request, response, box, out);

            } else if (v_process.equals("LiteratureSubjectList")) { // 인문학 강좌 목록 조회
                this.performLiteratureSubjectList(request, response, box, out);

            } else if (v_process.equals("selectReviewListForAjax")) {
                this.performSelectReviewListForAjax(request, response, box, out); // ajax를 이용하여 후기 목록 조회
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 인문학 과정 목록 조회
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performLiteratureSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list = bean.selectLiteratureSubjectList(box);

            // 현재 일자에 수강 가능한 인문학 강좌 수 설정
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            request.setAttribute("literatureSubjectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/2013/portal/propose/zu_LiteratureSubject_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLiteratureSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void LoginChk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("tUrl", request.getRequestURI());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * OffLine 신청
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.ProposeCourseServlet";

            ProposeCourseBean bean = new ProposeCourseBean();
            int isOk = bean.OffLineApplySubject(box);

            String v_msg = "";
            box.put("p_process", "OffLineSubjPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                // v_msg = "insert.ok";
                v_msg = "신청 되었습니다.";
                // box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                // v_msg = "insert.fail";
                v_msg = "오프라인 과정 신청에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * OffLine 신청 취소
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApplyCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.ProposeCourseServlet";

            ProposeCourseBean bean = new ProposeCourseBean();
            int isOk = bean.OffLineApplyCancelSubject(box);

            String v_msg = "";
            box.put("p_process", "OffLineSubjPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                // v_msg = "insert.ok";
                v_msg = "신청 취소 되었습니다.";
                // box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                // v_msg = "insert.fail";
                v_msg = " 신청 취소에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 전체 과정 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/game/course/gu_OffLineSubjApply_L.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
        }
    }

    /**
     * 오프라인 과정 상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performApplyViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            DataBox dbox = bean.OffLineApplyView(box);

            request.setAttribute("OffLineSubjApply", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_OffLineSubjApply_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 컨텐츠 평가 보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performContentResearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            box.put("p_action", "go");
            SulmunRegistResultBean bean = new SulmunRegistResultBean();
            ArrayList list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1);

            // MainSubjSearchBean bean = new MainSubjSearchBean();
            // ArrayList list = bean.selectSubjSearch(box);

            //
            // request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_ContentRearch_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * COURSE SUBJECT VIEW OPEN PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCourseSubjOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list = bean.selectCourseSubjList(box);

            request.setAttribute("CourseSubjList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_CourseSubjList.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CourseSubjOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강신청안내 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCreditCard(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            box.put("p_subj", request.getParameter("p_subj"));
            box.put("p_iscourseYn", request.getParameter("p_iscourseYn"));
            box.put("p_course", request.getParameter("p_course"));
            box.put("p_subjseq", request.getParameter("p_subjseq"));
            box.put("p_year", request.getParameter("p_year"));
            box.put("p_grtype", request.getParameter("p_grtype"));
            box.put("p_transaction", request.getParameter("transaction"));
            box.put("p_realpay", request.getParameter("amount"));
            box.put("p_productcode", request.getParameter("productcode"));
            box.put("p_authnumber", request.getParameter("authnumber"));
            box.put("p_payselect", "D");

            if (request.getParameter("respcode").equals("0000")) {
                performSubjectEduPropose(request, response, box, out);
            }

            // ServletContext sc = getServletContext();
            // RequestDispatcher rd =
            // sc.getRequestDispatcher("/learn/user/game/course/gu_ApplyIntro.jsp");
            // rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE Bill
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            DataBox dbox = bean.getSelectBill(box);
            request.setAttribute("selectEduBill", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeBill_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 월간 교육 일정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationMonthlySchedule(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = bean.selectEducationMonthlyList(box);

            request.setAttribute("EducationMonthlyList", list1);
            ServletContext sc = getServletContext();
            // RequestDispatcher rd =
            // sc.getRequestDispatcher("/learn/user/propose/zu_EducationMonthlySchedule_L.jsp");
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_EducationMonthlySchedule_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("educationMonthlySchedule()\r\n" + ex.getMessage());
        }
    }

    /**
     * 연간 교육 일정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEducationYearlySchedule(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = bean.selectEducationYearlyList(box);

            request.setAttribute("EducationYearlyList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_EducationYearlySchedule_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("educationYearlySchedule()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE Bill(무통장입금)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduInputBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeInputBill_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE Bill(무통장입금)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduInputCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ProposeInputBill_Check.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 카드결제용 페이지 열기.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduInputCredit(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/credit/edacom_credit.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 맛보기 로그 신청
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPreviewLog(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_msg = "";

            bean.insertPreviewLog(box);

            AlertManager alert = new AlertManager();
            v_msg = "";

            alert.selfClose(out, v_msg);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 강의목차 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLectureList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            if (v_isonoff.equals("ON")) { // 사이버 과정인 경우 일차리스트
                ArrayList list1 = bean.selectLessonList(box);
                request.setAttribute("lessonList", list1);
                v_url = "/learn/user/course/zu_LectureListPop.jsp";
            } else if (v_isonoff.equals("OFF")) { // 집합 과정인 경우 강좌리스트
                ArrayList list1 = bean.selectLectureList(box);
                request.setAttribute("lectureList", list1);
                v_url = "/learn/user/course/zu_LectureListPop.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * OFFICER CHOICE OPEN PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOfficerChoiceOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SearchAdminBean bean = new SearchAdminBean();
            ArrayList list = bean.searchMember(box);

            request.setAttribute("OfficerChoiceList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_OfficerChoiceList.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("officerChoiceOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 신청명단조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performProposeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list = bean.selectProposeList(box);

            request.setAttribute("ProposeList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_ProposeName_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT APPROVAL PROPOSE PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectAppProposeOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_SubjectAppPropose.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectAppProposeOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * CREDU PROPOSE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectCreduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            // int check =
            bean.setCreduSession(box); // 임시 세션
            int isOk = bean.insertSubjectEduPropose(box);
            String v_msg = "";

            String v_url = box.getString("p_purl");
            if (v_url.length() == 0) {
                v_url = "/servlet/controller.propose.ProposeCourseServlet";
                box.put("p_process", "SubjectList");
            }

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                // alert.selfClose(out, v_msg);
            } else {
                v_msg = "insert.fail";

                alert.alertFailMessage(out, v_msg);
                // alert.selfClose(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectCreduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * CREDU PROPOSE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectCreduPropose2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            // int check =
            bean.setCreduSession(box); // 임시 세션
            int isOk = bean.insertSubjectEduPropose(box);
            String v_msg = "";

            String v_url = box.getString("p_purl");
            if (v_url.length() == 0) {
                v_url = "/servlet/controller.propose.ProposeCourseServlet";

            }
            box.put("p_process", "SubjectPreviewPage");
            box.put("p_isonoff", box.getString("p_isonoff"));

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                // alert.selfClose(out, v_msg);
            } else {
                v_msg = "insert.fail";

                alert.alertFailMessage(out, v_msg);
                // alert.selfClose(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectCreduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강 신청
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            MemberInfoBean bean = new MemberInfoBean();

            AlertManager alert = new AlertManager();

            String v_msg = "";
            String v_url = "";

            String upperclass = box.getString("p_upperclass");
            // String area = box.getString("p_area");
            
            // 현재차수에 2개제한,이전차수 미수료
            int isOk = 0;

            isOk = bean.memberSubjLimit(box);

            if (isOk == 1) { // 전차수 무료과정 미수료

                v_msg = "pregrseq.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);

            } else if (isOk == 2) { // 해당차수 무료 과정 수강신청제한수 초과

                v_msg = "nowgrseq3.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;
                box.put("openercount", "3");

                alert.alertOkMessage(out, v_msg, v_url, box, true, true, false, false);

            } else if (isOk == 3) { // 홈앤쇼핑 해당차수 무료 과정 수강신청제한수 초과

                v_msg = "nowgrseq2.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);

            } else if (isOk == 5) { // 상지대학교 수강신청제한수 초과

                v_msg = "nowgrseq3.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);

            } else if (isOk == 4) {
                v_msg = "subjApplyTimeOver.no";
                v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                box.put("onOff", 1);
                DataBox box1 = bean.memberInfoViewNew(box);
                // 송파구청, T3, 한국산업기술대(KPU), 부산경남대표방송(KNN), 전남문화산업진흥원은 유료도 무료로 처리
                // 하고 금액을 화면에 표시만 한다.
                if (box.getSession("tem_grcode").equals("N000030") || box.getSession("tem_grcode").equals("N000034") || box.getSession("tem_grcode").equals("N000035") || box.getSession("tem_grcode").equals("N000036")
                        || box.getSession("tem_grcode").equals("N000033") || box.getSession("tem_grcode").equals("N000040") || box.getSession("tem_grcode").equals("N000043")) {
                    box1.put("d_biyong1", box1.getString("d_biyong"));
                    box1.put("d_biyong", "0");
                } else
                    box1.put("d_biyong1", box1.getString("d_biyong"));

                request.setAttribute("resultbox", box1);
                
                v_url = "/learn/user/portal/propose/zu_Subject_I.jsp";
                
                if(box.getSession("tem_type").equals("B")){
                	v_url = "/learn/user/typeB/propose/zu_Subject_I.jsp";
                }

                request.setAttribute("requestbox", box);
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강 신청목록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            request.setAttribute("resultbox", bean.getSubjectEduProposeList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/propose/zu_SubjectPropose_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강신청 결제확인-최후통첩
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeCheckListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            request.setAttribute("resultbox", bean.getSubjectEduProposeCheckList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/propose/zu_SubjectProposeCheck_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강 신청
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            String v_msg = "";
            String v_biyong = box.get("p_biyong");
            String v_favoryn = box.getString("p_favoryn");
            String v_subj = box.getString("p_subj");
            String v_subjnm = box.getString("p_subjnm");
            
            box.put("onOff", 1);
            box.put("subj", v_subj);

            String upperclass = box.getString("p_upperclass");
            String area = box.getString("p_area");

            // String v_url ="/servlet/controller.study.MyClassServlet?p_process=ProposeCancelPage";
            // String v_url ="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=" + upperclass;

            String v_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=" + area + "&p_upperclass=" + upperclass;
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage";
            }

            MemberInfoBean mbean = new MemberInfoBean();
            AlertManager alert = new AlertManager();
            SubjectBean dbean = new SubjectBean();

            int isOk = mbean.memberInfoUpdateNew(box);

            if (isOk > 0) {

                isOk = bean.insertSubjectEduPropose(box);

                if (isOk > 0) {
                    if (v_biyong.equals("0")) {
                        v_msg = "propose.ok.zero";
                    } else {
                        v_msg = "propose.ok";
                    }

                    if (v_favoryn.equals("Y")) {
                        box.put("classType", "01");
                        dbean.cancelSubjFavor(box);//수강신청시 찜취소 호출.. 
                    }

                    alert.alertOkMessage(out, v_msg, v_url, box, true, true, false, true);
                    if (box.getSession("tem_grcode").equals("N000210")){
                    	DateFormat df = new SimpleDateFormat("yyyyMMdd");
                        Date dt = new Date();
                        String tran_date = df.format(dt);
                        String tDate = StringManager.substring(tran_date, 4, 6) + "월 " + StringManager.substring(tran_date, 6, 8) + "일 ";
                        String p_msg = "";
                        
                        if (v_subjnm.length() > 13)
                            p_msg = tDate + StringManager.substring(v_subjnm, 0, 12) + ".. ";
                        else
                            p_msg = tDate + v_subjnm;
                        
                        out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
                        out.println("<body>");
                        out.println("<form name = 'form1' method='post'></form></body>");
                        out.println("<script language = 'javascript'>");
                        out.println("document.form1.target = window.opener.name");
                        out.println("window.opener.location.reload()");
       
                        out.println("</script>");
                        out.println("</html>");
                        
                        SmsBean smsBean = new SmsBean();
                        // 발신번호 properites 에서 가지고 오기
                        ConfigSet conf = new ConfigSet();
                        
                        String p_toPhone = bean.getNumber(box); 
                        String p_fromPhone = "02-6310-0770";
                        p_msg = "[EDUKOCCA]\n" + p_msg + " 수강신청이 완료되었습니다.";
                        boolean result = smsBean.sendSMSMsg(p_toPhone, p_fromPhone, p_msg, "");
                        if(result){
                            System.out.println("sms 발송 성공");
                        }
                    }

                } else {
                    v_msg = "propose.fail";
                    v_msg = box.getStringDefault("err_msg", v_msg);
                    alert.alertFailMessage(out, v_msg);
                }
            } else {
                v_msg = "subjApplyTimeOver.no";
                v_msg = box.getStringDefault("err_msg", v_msg);
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    
    /**
     * 수강 신청(상시)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSpotSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_subj = box.getString("p_subj");
            String v_subjnm = box.getString("p_subjnm");
            
            box.put("onOff", 1);
            box.put("subj", v_subj);

            //String v_url = "/servlet/controller.contents.EduStart?p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
            
            MemberInfoBean mbean = new MemberInfoBean();
            AlertManager alert = new AlertManager();
            SubjectBean dbean = new SubjectBean();
            
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date dt = new Date();
            String tran_date = df.format(dt);
            String tDate = StringManager.substring(tran_date, 4, 6) + "월 " + StringManager.substring(tran_date, 6, 8) + "일";
            String p_msg = "";
            
            if (v_subjnm.length() > 13)
                p_msg = tDate + StringManager.substring(v_subjnm, 0, 12) + "..";
            else
                p_msg = tDate + v_subjnm;
            
            int chkSubj = 0;
            chkSubj = bean.chkSubjDupl(box);
            
            if( chkSubj < 1){
                
                int isOk = mbean.memberInfoUpdateNew(box);
    
                    isOk = bean.insertSpotSubjectEduPropose(box);
    
                    if(isOk > 0){
    
                    	out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
                        out.println("<body>");
                        out.println("<form name = 'form1' method='post'></form></body>");
                        out.println("<script language = 'javascript'>");
                        out.println("document.form1.target = window.opener.name");
                        out.println("window.opener.location.reload()");
       
                        out.println("</script>");
                        out.println("</html>");
                        
                        SmsBean smsBean = new SmsBean();
                        // 발신번호 properites 에서 가지고 오기
                        ConfigSet conf = new ConfigSet();
                        
                        String p_toPhone = bean.getNumber(box); 
                        String p_fromPhone = conf.getProperty("sms.admin.comptel");
                        p_msg = "[EDUKOCCA]\n" + p_msg + " 학습이 시작되었습니다.";
                        boolean result = smsBean.sendSMSMsg(p_toPhone, p_fromPhone, p_msg, "");
                        if(result){
                            System.out.println("sms 발송 성공");
                        }
                    } else {
                    	out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
                        out.println("<body>");
                        out.println("<form name = 'form1' method='post'></form></body>");
                        out.println("<script language = 'javascript'>");
                        out.println("document.form1.target = window.opener.name");
                        out.println("window.opener.location.reload()");
                        out.println("document.form1.submit()");
                        out.println("self.close()");
                        out.println("</script>");
                        out.println("</html>");
                    }
            }
                
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/2013/portal/propose/zu_callEduRoom_L.jsp");
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * 수강 신청삭제 - 미완
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();
            String v_msg = "";
            String v_billYn = box.getString("p_billYn");

            String v_url = "/servlet/controller.study.MyClassServlet?p_process=ProposeCancelPage";

            int isOk = 0;
            isOk = bean.insertSubjectEduPropose(box);

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "propose.ok";
                if (v_billYn.equals("Y")) {
                    alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                } else {
                    alert.alertOkMessage(out, v_msg, v_url, box);
                }
            } else {
                v_msg = "propose.fail";
                v_msg = box.getStringDefault("err_msg", v_msg);
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJECT EDUCATION PROPOSE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectEduProposeChief(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            DataBox dbox = bean.getSelectChief(box);
            request.setAttribute("selectChief", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/course/zu_ProposeApproval_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강신청안내 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectIntro(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/game/course/gu_ApplyIntro1.jsp";
            String tab = box.getString("p_tab");
            if (tab.equals("2")) {
                v_url = "/learn/user/game/course/gu_ApplyIntro2.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강신청안내 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCurriculum(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/2012/portal/propose/zu_Curriculum_L.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = null;
            ArrayList list_1 = null;
            ArrayList list_2 = null;
            ArrayList list_3 = null;
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) {
                v_url = "/learn/user/2013/portal/propose/zu_Subject_L.jsp";

                list_1 = bean.selectSubjhomegubun_1(box, "GS");
                request.setAttribute("Subjhomegubun_1", list_1);

                list_2 = bean.selectSubjhomegubun_2(box, "GS");
                request.setAttribute("Subjhomegubun_2", list_2);

                list_3 = bean.selectSubjhomegubun_3(box, "GS");
                request.setAttribute("Subjhomegubun_3", list_3);

                list1 = bean.selectSubjectListRe(box);
                request.setAttribute("SubjectList", list1);

                // 현재 일자에 수강 가능한 인문학 강좌 수 설정
                if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                    int literatureSubjectCount = bean.getLiteratureSubjectCount();
                    if (literatureSubjectCount > 0) {
                        // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                        box.setSession("isLiteratureSubjYn", "Y");
                    }
                }

            } else {
                v_url = "/learn/user/portal/propose/zu_Asp_Subject_L.jsp";
                list1 = bean.selectSubjectList(box);
                request.setAttribute("SubjectList", list1);
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/propose/zu_Asp_Subject_L.jsp";
            }

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 리스트 - 직무별
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListJikmu(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = null;
            ArrayList list_1 = null;
            ArrayList list_2 = null;
            ArrayList list_3 = null;
            String v_url = "";

            v_url = "/learn/user/2013/portal/propose/zu_SubjectJikmu_L.jsp";

            list_1 = bean.selectSubjhomegubun_1(box, "GM");
            request.setAttribute("Subjhomegubun_1", list_1);

            list_2 = bean.selectSubjhomegubun_2(box, "GM");
            request.setAttribute("Subjhomegubun_2", list_2);

            list_3 = bean.selectSubjhomegubun_3(box, "GM");
            request.setAttribute("Subjhomegubun_3", list_3);

            list1 = bean.selectSubjectListReJikmu(box);
            request.setAttribute("SubjectList", list1);

            // 현재 일자에 수강 가능한 인문학 강좌 수 설정
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 리스트 - 직업별
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListJikup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = null;
            ArrayList list_1 = null;
            ArrayList list_2 = null;
            ArrayList list_3 = null;
            String v_url = "";

            v_url = "/learn/user/2013/portal/propose/zu_SubjectJikup_L.jsp";

            list_1 = bean.selectSubjhomegubun_1(box, "GJ");
            request.setAttribute("Subjhomegubun_1", list_1);

            list_2 = bean.selectSubjhomegubun_2(box, "GJ");
            request.setAttribute("Subjhomegubun_2", list_2);

            list_3 = bean.selectSubjhomegubun_3(box, "GJ");
            request.setAttribute("Subjhomegubun_3", list_3);

            list1 = bean.selectSubjectListReJikup(box);
            request.setAttribute("SubjectList", list1);

            // 현재 일자에 수강 가능한 인문학 강좌 수 설정
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 오프라인 신청/확인 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListAll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/course/zu_Subject_ALL_L.jsp";

            // 과정리스트
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList list1 = bean.selectSubjectListAll(box);
            request.setAttribute("SubjectList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인 전체 검색
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("rawtypes")
	public void performTotalSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) {
                v_url = "/learn/user/2012/portal/online/zu_TotalSubject_L.jsp";
                // v_url = "/learn/user/portal/propose/zu_Subject_L.jsp";
            } else {
                v_url = "/learn/user/portal/online/zu_TotalSubject_L.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/online/zu_TotalSubject_L.jsp";
            }

            // 과정리스트
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList totalSubjectList = bean.selectTotalSubjectList(box);
            request.setAttribute("totalSubjectList", totalSubjectList);

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
        }
    }

    /**
     * 인기 과정 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectListBest(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/course/zu_SubjectBest_L.jsp";

            // 과정리스트
            ProposeCourseBean bean = new ProposeCourseBean();
            // 직무 인기 과정
            box.put("p_gubun", "W");
            ArrayList list1 = bean.selectSubjectListBest(box);
            request.setAttribute("SubjectListW", list1);
            // 어학 인기 과정
            box.put("p_gubun", "L");
            ArrayList list2 = bean.selectSubjectListBest(box);
            request.setAttribute("SubjectListL", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListBest()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 미리보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) {
                // v_url = "/learn/user/2012/portal/propose/zu_Subject_R.jsp";
                v_url = "/learn/user/2013/portal/propose/zu_Subject_R.jsp";
            } else {
                v_url = "/learn/user/portal/propose/zu_Subject_R.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/propose/zu_Subject_R.jsp";
            }

            // 과정상세정보 리스트
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);

            // 과정차수 리스트
            request.setAttribute("subjseqList", bean.selectSubjSeqList(box));

            // 과정차시 리스트
            ArrayList list2 = bean.selectSubjChasiList(box);
            request.setAttribute("selectSubjChasiList", list2);

            // 이벤트가 사용자체크
            // ArrayList eventLst = bean.selectChkUser(box);
            // request.setAttribute("selectChkUser", eventLst);
            // request.setAttribute("tutorList", bean.selectTutorList(box));

            ArrayList list1 = bean.selectLessonList(box);
            request.setAttribute("lessonList", list1);
            // v_url= "/learn/user/game/course/gu_SubjectPreviewON.jsp";

            // 다음과정(연관과정) 목록
            ArrayList nextSubjList = bean.selectNextProposeSubjList(box);
            request.setAttribute("nextSubjList", nextSubjList);

            // 후기 목록 조회
            ArrayList reviewList = bean.selectReviewList(box);
            request.setAttribute("reviewList", reviewList);

            // 현재 일자에 수강 가능한 인문학 강좌 수 설정
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /*
     * 2009.12.08 이전 내용 백업 by swchoi public void
     * performSubjectPreviewPage(HttpServletRequest request, HttpServletResponse
     * response, RequestBox box, PrintWriter out) throws Exception { try {
     * request.setAttribute("requestbox", box); ProposeCourseBean bean = new
     * ProposeCourseBean();
     * 
     * // 선수과정 리스트 //ArrayList list1 = bean.selectListPre(box);
     * //request.setAttribute("subjectPre", list1); // 후수과정 리스트 //ArrayList
     * list2 = bean.selectListNext(box); //request.setAttribute("subjectNext",
     * list2);
     * 
     * // String v_isonoff = box.getString("p_isonoff"); // String v_iscourseYn
     * = box.getString("p_iscourseYn"); String v_url = "";
     * 
     * // 과정상세정보 리스트 DataBox dbox = bean.selectSubjectPreview(box);
     * request.setAttribute("subjectPreview", dbox); // String place =
     * dbox.getString("d_place");
     * 
     * // 과정차수 리스트 ArrayList list = bean.selectSubjSeqList(box);
     * request.setAttribute("subjseqList", list);
     * 
     * // 과정차시 리스트 ArrayList list2 = bean.selectSubjChasiList(box);
     * request.setAttribute("selectSubjChasiList", list2);
     * 
     * // System.out.print("dbox=============>"+dbox);
     * 
     * // 이벤트가 사용자체크 ArrayList eventLst = bean.selectChkUser(box);
     * request.setAttribute("selectChkUser", eventLst);
     * 
     * //if(v_isonoff.equals("N")){ //코스 과정일 경우 ArrayList list1 =
     * bean.selectLessonList(box); request.setAttribute("lessonList", list1);
     * v_url= "/learn/user/game/course/gu_SubjectPreviewON.jsp"; // }else
     * if(v_isonoff.equals("Y")){ //코스과정일 경우. // ArrayList list1 =
     * bean.selectLectureList(box); // request.setAttribute("lectureList",
     * list1); // // 시설정보 // InstitutionAdminBean bean1 = new
     * InstitutionAdminBean(); // DataBox dbox2 = bean1.getInstitution(place);
     * // request.setAttribute("institution", dbox2); // // v_url=
     * "/learn/user/game/course/gu_SubjectPreviewOFF.jsp"; // }
     * //System.out.print("v_url=============>"+v_url); ServletContext sc =
     * getServletContext(); RequestDispatcher rd =
     * sc.getRequestDispatcher(v_url);
     * 
     * rd.forward(request, response); }catch (Exception ex) {
     * ErrorManager.getErrorStackTrace(ex, out); throw new
     * Exception("SubjectPreviewPage()\r\n" + ex.getMessage()); } }
     */
    /**
     * 과정 미리보기 팝업
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectPreviewPopup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            // 과정상세정보 리스트
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);
            String place = dbox.getString("d_place");

            if (v_isonoff.equals("ON")) { // 사이버 과정인 경우
                v_url = "/learn/user/game/course/gu_SubjectPreviewON_P.jsp";
                if(box.getSession("tem_type").equals("B")){
                	v_url = "/learn/user/typeB/course/gu_SubjectPreviewON_P.jsp";
                }
            } else if (v_isonoff.equals("OFF")) { // 집합 과정인 경우
                // 시설정보
                InstitutionAdminBean bean1 = new InstitutionAdminBean();
                DataBox dbox2 = bean1.getInstitution(place);
                request.setAttribute("institution", dbox2);

                v_url = "/learn/user/game/course/gu_SubjectPreviewOFF_P.jsp";
            }

            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수정보 미리보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjSeqPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            DataBox dbox = bean.selectSubjSeqPreview(box);
            request.setAttribute("subjseqPreview", dbox);

            ArrayList list = bean.selectLectureList(box);
            request.setAttribute("lectureList", list);

            if (v_isonoff.equals("ON")) { // 사이버 과정인 경우 일차리스트
                v_url = "/learn/user/course/zu_SubjSeqPreviewON.jsp";
            } else if (v_isonoff.equals("OFF")) { // 집합 과정인 경우 강좌리스트
                v_url = "/learn/user/course/zu_SubjSeqPreviewOFF.jsp";
            }
            System.out.println(v_url);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * TUTOR INFORMATION LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTutorInformationList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_selTutor = box.getString("p_selTutor"); // 강사선택값
            // ProposeCourseBean bean = new ProposeCourseBean();
            // ArrayList list1 = bean.selectTutorInformationList(box);
            // request.setAttribute("TutorInformationList", list1);

            // 강사조회
            if (!v_selTutor.equals("")) {
                this.performTutorInformationSelect(request, response, box, out);
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/propose/zu_TutorInformation_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorInformationList()\r\n" + ex.getMessage());
        }
    }

    /**
     * TUTOR INFORMATION SELECT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performTutorInformationSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // ProposeCourseBean bean = new ProposeCourseBean();
            // ProposeCourseData data = bean.selectTutorInformation(box);

            // request.setAttribute("TutorInformationSelect", data);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("tutorInformationSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인 교육체계도
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEduSystem(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/2013/portal/propose/zu_OnEduSystem_R2.jsp";

            ProposeCourseBean bean = new ProposeCourseBean();

            // 현재 일자에 수강 가능한 인문학 강좌 수 설정
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 상세보기. 과정코드만 있고 년도, 차수정보가 없는 경우
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewSubjDetailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_url = "";

            v_url = "/learn/user/2013/portal/propose/zu_Subject_R.jsp";

            //DataBox infobox = bean.selectSubjInfo(box);
            //box.put("p_subjseq", infobox.get("d_subjseq"));
            //box.put("p_year", infobox.get("d_year"));
            //box.put("p_subjnm", infobox.get("d_subjnm"));
            box.put("p_subjseq", box.get("p_subjseq"));
            box.put("p_year", box.get("p_year"));
            box.put("p_subjnm", box.get("p_subjnm"));

            // 과정상세정보 리스트
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);

            // 과정차수 리스트
            request.setAttribute("subjseqList", bean.selectSubjSeqList(box));

            // 과정차시 리스트
            // ArrayList list2 = bean.selectSubjChasiList(box);
            // request.setAttribute("selectSubjChasiList", list2);

            ArrayList list1 = bean.selectLessonList(box);
            request.setAttribute("lessonList", list1);

            // 다음과정(연관과정) 목록
            ArrayList nextSubjList = bean.selectNextProposeSubjList(box);
            request.setAttribute("nextSubjList", nextSubjList);

            // 후기 목록 조회
            ArrayList reviewList = bean.selectReviewList(box);
            request.setAttribute("reviewList", reviewList);

            // 현재 일자에 수강 가능한 인문학 강좌 수 설정
            if (!box.getSession("isLiteratureSubjYn").equals("Y")) {
                int literatureSubjectCount = bean.getLiteratureSubjectCount();
                if (literatureSubjectCount > 0) {
                    // request.setAttribute("literatureSubjCnt", literatureSubjectCount);
                    box.setSession("isLiteratureSubjYn", "Y");
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewSubjDetailPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 후기 목록을 조회한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSelectReviewListForAjax(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            System.out.println("performSelectReviewListForAjax");
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_url = "/learn/user/2013/portal/propose/zu_Subject_AjaxResult.jsp";

            // 후기 목록 조회
            ArrayList reviewList = bean.selectReviewList(box);
            request.setAttribute("reviewList", reviewList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectReviewListForAjax()\r\n" + ex.getMessage());
        }
    }

}