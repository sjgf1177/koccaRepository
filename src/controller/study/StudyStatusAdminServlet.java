// *********************************************************
// 1. 제 목: STUDY STATUS ADMIN SERVLET
// 2. 프로그램명: StudyStatusAdminServlet.java
// 3. 개 요: 학습 현황 관리자 servlet
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성:
// **********************************************************
package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MemberInfoBean;
import com.credu.homepage.QnaAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.point.MyPointBean;
import com.credu.study.CounselAdminBean;
import com.credu.study.StudyStatusAdminBean;
import com.credu.study.StudyStatusData;
import com.credu.system.MemberData;
import com.credu.system.SubjCountBean;

/**
 * 
 * @author kocca
 * 
 */
@WebServlet("/servlet/controller.study.StudyStatusAdminServlet")
public class StudyStatusAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1969336958131777748L;

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

            if (v_process.equals("PersonalSearchPage")) { // in case of 개인별 학습현황
                this.performPersonalSearchPage(request, response, box, out);
            } else if (v_process.equals("PersonalNameSelectList")) { // in case of 동명이인 조회 및 이메일로 조회
                this.performPersonalNameSelectList(request, response, box, out);
            } else if (v_process.equals("PersonalSelect")) { // in case of 개인별 학습현황-개인이력 상세조회
                this.performPersonalSelect(request, response, box, out);
            } else if (v_process.equals("PersonalProposeList")) { // in case of 개인별 신청과정 list
                this.performPersonalProposeList(request, response, box, out);
            } else if (v_process.equals("PersonalEducationList")) { // in case of 개인별 수강과정 list
                this.performPersonalEducationList(request, response, box, out);
            } else if (v_process.equals("PersonalGraduationList")) { // in case of 개인별 수료과정 list
                this.performPersonalGraduationList(request, response, box, out);
            } else if (v_process.equals("PersonalScoreCompleteList")) { // in case of 개인별 수료과정 list
                this.performPersonalScoreCompleteList(request, response, box, out);
            } else if (v_process.equals("ChangeUserInfo")) { // 유저정보 변경
                this.performChangeUserInfo(request, response, box, out);
            } else if (v_process.equals("CounselInsertPage")) { // 상담 등록페이지
                this.performCounselInsertPage(request, response, box, out);
            } else if (v_process.equals("CounselInsert")) { // 상담 등록
                this.performCounselInsert(request, response, box, out);
            } else if (v_process.equals("CounselUpdatePage")) { // 상담 수정페이지
                this.performCounselUpdatePage(request, response, box, out);
            } else if (v_process.equals("CounselUpdate")) { // 상담 수정
                this.performCounselUpdate(request, response, box, out);
            } else if (v_process.equals("CounselDelete")) { // 상담 삭제
                this.performCounselDelete(request, response, box, out);
            } else if (v_process.equals("ViewHomeqna")) { // 홈페이지 Q&A 상세보기
                this.performViewHomeqna(request, response, box, out);
            } else if (v_process.equals("ViewQna")) { // 과정 Q&A 상세보기
                this.performViewQna(request, response, box, out);
            } else if (v_process.equals("YeunsunoDelete")) { // 연수 번호 삭제
                this.performYeunsunoDelete(request, response, box, out);
            } else if (v_process.equals("SubjectLessonList")) { // in case of subject lesson list
                this.performSubjectLessonList(request, response, box, out);
            } else if (v_process.equals("LearningStatusListByClass")) { // in case of 클래스별 학습현황
                this.performLearningStatusListByClass(request, response, box, out);
            } else if (v_process.equals("LearningStatusExcelByClass")) { // in case of learning status excel by class
                this.performLearningStatusExcelByClass(request, response, box, out);
            } else if (v_process.equals("LearningStatusListByScore")) { // in case of learning status list by score
                this.performLearningStatusListByScore(request, response, box, out);
            } else if (v_process.equals("LearningStatusExcelByScore")) { // in case of learning status excel by score
                this.performLearningStatusExcelByScore(request, response, box, out);
            } else if (v_process.equals("LearningStatusListByAssignment")) {// in case of learning status list by assignment
                this.performLearningStatusListByAssignment(request, response, box, out);
            } else if (v_process.equals("LearningStatusExcelByAssignment")) {// in case of learning status excel by assignment
                this.performLearningStatusExcelByAssignment(request, response, box, out);
            } else if (v_process.equals("LearningStatusListByGrcode")) { // in case of 교육그룹별 학습현황
                this.performLearningStatusListByGrcode(request, response, box, out);
            } else if (v_process.equals("LearningStatusExcelByGrcode")) { // in case of learning status excel by grcode
                this.performLearningStatusExcelByGrcode(request, response, box, out);
            } else if (v_process.equals("LearningTimeSelectList")) { // in case of 학습시간조회
                this.performLearningTimeSelectList(request, response, box, out);
            } else if (v_process.equals("PersonalTimeList")) { // in case of 개인별 학습창 접속로그
                this.performPersonalTimeList(request, response, box, out);
            } else if (v_process.equals("PersonalProgressList")) { // in case of 개인별 목차 접속로그
                this.performPersonalProgressList(request, response, box, out);
            } else if (v_process.equals("LearningTimeSelectExcel")) { // in case of learning time select excel
                this.performLearningTimeSelectExcel(request, response, box, out);
            } else if (v_process.equals("SendFormMail")) { // in case of send form mail
                this.performSendFormMail(request, response, box, out);
            } else if (v_process.equals("SendFreeMail")) { // in case of send free mail
                this.performSendFreeMail(request, response, box, out);
            } else if (v_process.equals("LearningStatusSendEmailSms")) { // 이메일,sms 발송
                this.performSendEmailSms(request, response, box, out);
            } else if (v_process.equals("EmailSmsSendList")) { // 이메일,sms 발송
                this.performEmailSmsSendList(request, response, box, out);
            } else if (v_process.equals("NoneDupeSmsSendList")) { // 이메일,sms 발송
                this.performNoneDupeSmsSendList(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 개인별 학습현황
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalSearchPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalSearch_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("personalSearchPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 동명이인 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalNameSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<MemberData> list = bean.selectPersonalNameList(box);

            request.setAttribute("PersonalNameSelectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_NamesakeList.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalNameSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인이력 상세조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performPersonalSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_selTab = box.getString("p_selTab"); // 탭선택값
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            MemberData data = bean.selectPersonal(box);
            request.setAttribute("PersonalSelect", data);
            box.put("onOff", "1");

            // 검색 실패시 error 메세지 처리
            String v_msg = "";
            AlertManager alert = new AlertManager();
            if (data.getUserid() == null || data.getUserid().equals("")) {
                v_msg = "회원DB에 존재하지 않습니다. !!!!";
                alert.alertFailMessage(out, v_msg);
            } else {
                // 과정조회
                box.put("p_userid", data.getUserid());
                if (v_selTab.equals("propose") || v_selTab.equals("")) { // 신청과정
                    this.performPersonalProposeList(request, response, box, out);
                } else if (v_selTab.equals("education")) { // 수강과정
                    this.performPersonalEducationList(request, response, box, out);
                } else if (v_selTab.equals("graduation")) { // 수료과정
                    this.performPersonalGraduationList(request, response, box, out);
                } else if (v_selTab.equals("scorecomplete")) { // 학점이수현황
                    this.performPersonalScoreCompleteList(request, response, box, out);
                } else if (v_selTab.equals("offgraduation")) { // 오프라인 수료과정
                    this.performPersonalOffGraduationList(request, response, box, out);
                } else if (v_selTab.equals("offpropose")) { // 오프라인 신청과정
                    this.performPersonalOffProposeList(request, response, box, out);
                }

                // 연수번호 추출
                this.performYeunsunoList(request, response, box, out);

                // 상담내역조회
                CounselAdminBean bean1 = new CounselAdminBean();
                ArrayList<DataBox> list1 = bean1.selectListCounsel(box);
                request.setAttribute("counselList", list1);

                MemberInfoBean mbean = new MemberInfoBean();
                request.setAttribute("addInfo", mbean.memberInfoViewNew(box));

                String loingId = box.getSession("userid");
                box.setSession("userid", box.getString("p_userid"));
                MyPointBean mypointbean = new MyPointBean(); // 나의 포인트
                int iGetPoint = mypointbean.selectGetPoint(box); // 나의 보유 포인트
                int iUsePoint = mypointbean.selectUsePoint(box); // 나의 사용 포인트
                int iWaitPoint = mypointbean.selectWaitPoint(box); // 나의 대기 포인트
                box.put("p_getpoint", String.valueOf(iGetPoint));
                box.put("p_usepoint", String.valueOf(iUsePoint));
                box.put("p_waitpoint", String.valueOf(iWaitPoint));
                request.setAttribute("selectHavePointList", mypointbean.selectHavePointList(box));// 보유포인트
                request.setAttribute("selectStoldPointList", mypointbean.selectStoldPointList(box));// 적립포인트
                request.setAttribute("selectUsePointList", mypointbean.selectUsePointList(box));// 사용포인트
                box.setSession("userid", loingId);

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalSelect_R.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("12412sdaf PersonalSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 연수번호 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performYeunsunoList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list1 = bean.selectYeunsunoList(box);

            request.setAttribute("YeunsunoList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalProposeList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 신청과정 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalProposeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list1 = bean.selectProposeList(box);

            request.setAttribute("PersonalProposeList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalProposeList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 수강과정 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalEducationList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list1 = bean.selectEducationList(box);

            request.setAttribute("PersonalEducationList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalEducationList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 수료과정 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalGraduationList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list1 = bean.selectGraduationList(box);

            request.setAttribute("PersonalGraduationList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalGraduationList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 학점이수현황 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalScoreCompleteList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list1 = bean.selectScoreCompleteList(box);

            request.setAttribute("PersonalScoreCompleteList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonalScoreCompleteList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 오프라인 수료과정 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalOffGraduationList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list1 = bean.selectOffGraduationList(box);

            request.setAttribute("PersonalOffGraduationList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalOffGraduationList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 오프라인 신청과정 LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalOffProposeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list1 = bean.selectOffProposeList(box);

            request.setAttribute("PersonalOffProposeList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalProposeList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 유저 정보 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performChangeUserInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();

            int isOk = bean.ChangeUserInfo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyStatusAdminServlet";
            box.put("p_process", "PersonalSelect");
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performChangeUserInfo()\r\n" + ex.getMessage());
        }
    }

    /**
     * 상담 등록페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCounselInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Counsel_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_Counsel_I.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 상담 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCounselInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            CounselAdminBean bean = new CounselAdminBean();
            int isOk = bean.insertCounsel(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyStatusAdminServlet";
            box.put("p_process", "PersonalSelect");

            box.put("p_subj", "");
            box.put("p_year", "");
            box.put("p_subjseq", "");

            // box.put("v_search", "userid");
            // box.put("p_searchtext", box.getString("p_userid"));
            // box.put("p_selTab", "");
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on StudyStatusAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselInsert()\r\n" + ex.getMessage());
        }

    }

    /**
     * 상담 수정페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCounselUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            CounselAdminBean bean = new CounselAdminBean();
            DataBox dbox = bean.selectViewCounsel(box);
            request.setAttribute("selectCounsel", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_Counsel_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_Counsel_U.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 상담 수정하여 저장할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCounselUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            CounselAdminBean bean = new CounselAdminBean();
            int isOk = bean.updateCounsel(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyStatusAdminServlet";
            box.put("p_process", "PersonalSelect");
            box.put("p_subj", "");
            box.put("p_year", "");
            box.put("p_subjseq", "");
            // 수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on StudyStatusAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselUpdate()\r\n" + ex.getMessage());
        }

    }

    /**
     * 상담 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performCounselDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            CounselAdminBean bean = new CounselAdminBean();
            int isOk = bean.deleteCounsel(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyStatusAdminServlet";
            box.put("p_process", "PersonalSelect");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on StudyStatusAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounselDelete()\r\n" + ex.getMessage());
        }

    }

    /**
     * SUBJECT LESSON LIST
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjectLessonList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // StudyStatusAdminBean bean = new StudyStatusAdminBean();
            // ArrayList list = bean.selectSubjectLessonList(box);

            // request.setAttribute("SubjectLessonList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_SubjectLesson.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectLessonList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 클래스별 학습현황
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusListByClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list = bean.selectClassLearningList(box);

            request.setAttribute("LearningStatusListByClass", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByClass_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByClass()\r\n" + ex.getMessage());
        }
    }

    /**
     * LEARNING STATUS EXCEL BY CLASS
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusExcelByClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list = bean.selectClassLearningList(box);

            request.setAttribute("LearningStatusExcelByClass", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByClass_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByClass()\r\n" + ex.getMessage());
        }
    }

    /**
     * LEARNING STATUS LIST BY SCORE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusListByScore(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list = bean.selectScoreLearningList(box);

            request.setAttribute("LearningStatusListByScore", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByScore_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByScore()\r\n" + ex.getMessage());
        }
    }

    /**
     * LEARNING STATUS EXCEL BY SCORE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusExcelByScore(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list = bean.selectScoreLearningList(box);

            request.setAttribute("LearningStatusExcelByScore", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByScore_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByScore()\r\n" + ex.getMessage());
        }
    }

    /**
     * LEARNING STATUS LIST BY ASSIGNMENT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusListByAssignment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list = bean.selectAssignmentLearningList(box);

            request.setAttribute("LearningStatusListByAssignment", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByAssignment_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByAssignment()\r\n" + ex.getMessage());
        }
    }

    /**
     * LEARNING STATUS EXCEL BY ASSIGNMENT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusExcelByAssignment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list = bean.selectAssignmentLearningList(box);

            request.setAttribute("LearningStatusExcelByAssignment", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByAssignment_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByAssignment()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹별 학습현황
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusListByGrcode(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list1 = bean.selectGrcodeLearningList(box);

            request.setAttribute("LearningStatusListByGrcode", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByGrcode_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByGrcode()\r\n" + ex.getMessage());
        }
    }

    /**
     * LEARNING STATUS EXCEL BY GRCODE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningStatusExcelByGrcode(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<StudyStatusData> list1 = bean.selectGrcodeLearningList(box);

            request.setAttribute("LearningStatusExcelByGrcode", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByGrcode_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByGrcode()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습시간조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningTimeSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list = bean.selectLearningTimeList(box);

            request.setAttribute("LearningTimeSelectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningTimeSelect_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLearningTimeSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 학습창 접속 로그 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalTimeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjCountBean bean = new SubjCountBean();

            ArrayList<DataBox> list = bean.SelectCountLog(box);
            request.setAttribute("PersonalTimeList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalTimeList_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonalTimeList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인별 학습창 목차 로그 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPersonalProgressList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjCountBean bean = new SubjCountBean();

            ArrayList<DataBox> list = bean.SelectProgressLog(box);
            request.setAttribute("PersonalProgressList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalProgressList_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonalTimeList()\r\n" + ex.getMessage());
        }
    }

    /**
     * LEARNING TIME SELECT EXCEL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLearningTimeSelectExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList<DataBox> list = bean.selectLearningTimeList(box);

            request.setAttribute("LearningTimeSelectExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningTimeSelect_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningTimeSelectExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * SEND FORM MAIL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSendFormMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();

            int isOk = bean.sendFormMail(box);
            String v_mailcnt = isOk + "";
            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyStatusAdminServlet";
            box.put("p_process", box.getString("p_rprocess"));
            box.put("p_mailcnt", v_mailcnt);
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "mail.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "mail.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFormMail()\r\n" + ex.getMessage());
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

    /**
     * 화면변경 이메일, sms 보내기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSendEmailSms(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // request.setAttribute("requestbox", box);
            // StudyStatusAdminBean bean = new StudyStatusAdminBean();
            // ArrayList list = bean.selectScoreLearningList(box);

            // request.setAttribute("LearningStatusListByScore", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusSendEmailSms_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByScore()\r\n" + ex.getMessage());
        }
    }

    /**
     * 화면변경 이메일, sms 발송 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performEmailSmsSendList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // request.setAttribute("requestbox", box);
            // StudyStatusAdminBean bean = new StudyStatusAdminBean();
            // ArrayList list = bean.selectScoreLearningList(box);

            // request.setAttribute("LearningStatusListByScore", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_EmailSmsSend_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EmailSmsSendList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 화면변경 중복제거 sms 발송 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performNoneDupeSmsSendList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_SmsSend_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EmailSmsSendList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 홈페이지 Qna 상세보기로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performViewHomeqna(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            QnaAdminBean bean = new QnaAdminBean();

            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);

            ArrayList<DataBox> list = bean.selectQnaListA(box);
            request.setAttribute("selectQnaListA", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_CounselHomeQna_R.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_CounselHomeQna_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
     * Qna 과정질문방 상세보기로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performViewQna(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            QnaAdminBean bean = new QnaAdminBean();

            DataBox dbox = bean.selectQnaCourse(box);
            request.setAttribute("selectQnaCourse", dbox);

            ArrayList<DataBox> list = bean.selectQnaCourseListA(box);
            request.setAttribute("selectQnaCourseListA", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_CounselQna_R.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/study/za_CounselQna_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    // 연수번호 삭제
    @SuppressWarnings("unchecked")
    public void performYeunsunoDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            int isOk = bean.YeunsunoDelete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyStatusAdminServlet";
            box.put("p_process", "PersonalSelect");
            // 수정 후 해당 리스트 페이지로 돌아가기 위해
            box.put("p_subj", "");
            box.put("p_year", "");
            box.put("p_subjseq", "");

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
            throw new Exception("performCounselDelete()\r\n" + ex.getMessage());
        }
    }
}