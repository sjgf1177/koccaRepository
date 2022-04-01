//*********************************************************
//  1. 제      목: 외부강의실
//  2. 프로그램명: OutClassServlet.java
//  3. 개      요: 외부강의실 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 19
//  7. 수      정:
//**********************************************************
package controller.outclass;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestPaperBean;
import com.credu.etest.ETestResultBean;
import com.credu.etest.ETestUserBean;
import com.credu.homepage.InstitutionAdminBean;
import com.credu.homepage.LoginBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeCourseBean;
import com.credu.propose.ProposeCourseData;
import com.credu.research.SulmunCommonUserBean;
import com.credu.research.SulmunQuestionExampleData;
import com.credu.research.SulmunRegistResultBean;
import com.credu.research.SulmunRegistUserBean;
import com.credu.research.SulmunSubjPaperBean;
import com.credu.research.SulmunSubjUserBean;
import com.credu.research.SulmunTargetUserBean;
import com.credu.study.MyClassBean;

@WebServlet("/servlet/controller.outclass.OutClassServlet")
public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 822799673690436129L;

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
            response.setHeader("P3P", "CP='CAO PSA CONi OTR OUR DEM ONL'");
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            String v_grcode = ""; // 교육그룹
            String v_comp = ""; // 회사
            String v_site = ""; // 이미지폴더이름
            String v_resno = ""; // 주민번호
            int isOk1 = 0;
            AlertManager alert = new AlertManager();

            if (box.getSession("userid").equals("")) {

                v_grcode = box.getStringDefault("grcode", "N000001"); // 교육그룹
                v_comp = box.getStringDefault("comp", "0101000000"); // 회사
                v_site = box.getStringDefault("site", "auto"); // 이미지폴더이름
                v_resno = box.getString("resno"); // 주민번호

                box.setSession("tem_grcode", v_grcode);
                box.setSession("comp", v_comp);
                box.setSession("resno", v_resno);
                box.setSession("site", v_site);

                if (ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
                }

                // 로그인 체크(세션세팅)
                LoginBean lgbean = new LoginBean();
                isOk1 = lgbean.ssologin2(box);
            } else {
                isOk1 = 1;
            }

            if (isOk1 != 1) {
                String v_msg = "유저 정보가 없습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {

                if (v_process.equals("SubjectList")) { // 과정리스트
                    this.performSubjectList(request, response, box, out);
                } else if (v_process.equals("SubjectListAll")) { // 전체과정보기
                    this.performSubjectListAll(request, response, box, out);
                } else if (v_process.equals("SubjectListBest")) { // 인기 과정 보기
                    this.performSubjectListBest(request, response, box, out);
                } else if (v_process.equals("SubjectPreviewPage")) { // 과정 미리보기
                    this.performSubjectPreviewPage(request, response, box, out);
                } else if (v_process.equals("SubjectPreviewPopup")) { // 과정 미리보기 팝업
                    this.performSubjectPreviewPopup(request, response, box, out);
                } else if (v_process.equals("SeqPreviewPage")) { // 차수정보 미리보기
                    this.performSubjSeqPreviewPage(request, response, box, out);
                } else if (v_process.equals("SubjectEduPropose")) { // 수강신청
                    this.performSubjectEduPropose(request, response, box, out);
                } else if (v_process.equals("ContentResearch")) { // 컨테츠 설문결과
                    this.performContentResearch(request, response, box, out);
                } else if (v_process.equals("ProposeListPage")) { // 신청명단조회
                    this.performProposeListPage(request, response, box, out);
                } else if (v_process.equals("LectureList")) { // 강의목차 리스트
                    this.performLectureList(request, response, box, out);
                } else if (v_process.equals("EducationSubjectPage")) { // 나의 강의실
                    this.performEducationSubjectPage(request, response, box, out);
                } else if (v_process.equals("ProposeCancelPage")) { // 수강신청취소 페이지
                    this.performProposeCancelPage(request, response, box, out);
                } else if (v_process.equals("ProposeCancel")) { // 수강 신청 취소
                    this.performProposeCancel(request, response, box, out);
                } else if (v_process.equals("StudyHistoryList")) { // 개인 교육 이력
                    this.performStudyHistoryList(request, response, box, out);
                }

                // e-test
                if (v_process.equals("ETestUserListPage")) { //온라인테스트 사용자 문제지 리스트
                    this.performETestUserListPage(request, response, box, out);
                } else if (v_process.equals("ETestUserPaperListPage")) { //응시 뷰
                    this.performETestUserPaperListPage(request, response, box, out);
                } else if (v_process.equals("ETestRetryListPage")) { //온라인테스트 사용자 문제보기
                    this.performETestRetryListPage(request, response, box, out);
                } else if (v_process.equals("ETestUserResultInsert")) { //온라인테스트 문제지 등록할때
                    this.performETestUserResultInsert(request, response, box, out);
                } else if (v_process.equals("ETestUserPaperResult")) { //온라인테스트 개인별 평가결과 보기
                    this.performETestUserPaperResult(request, response, box, out);
                } else if (v_process.equals("ETestUserPaperResult2")) { //온라인테스트 개인별 평가결과 보기
                    this.performETestUserPaperResult2(request, response, box, out);
                } else if (v_process.equals("ETestUserPaperTextResult")) { //온라인테스트 개인별 평가결과 보기
                    this.performETestUserPaperTextResult(request, response, box, out);
                }

                // 설문
                if (v_process.equals("SulmunUserListPage")) { //온라인테스트 사용자 문제지 리스트
                    this.performSulmunUserListPage(request, response, box, out);
                } else if (v_process.equals("SulmunUserPaperListPage")) { //온라인테스트 사용자 문제보기
                    this.performSulmunUserPaperListPage(request, response, box, out);
                } else if (v_process.equals("SulmunUserResultInsert")) { //온라인테스트 문제지 등록할때
                    this.performSulmunUserResultInsert(request, response, box, out);
                } else if (v_process.equals("SulmunUserPaperResult")) { //온라인테스트 개인별 평가결과 보기
                    this.performSulmunUserPaperResult(request, response, box, out);
                } else if (v_process.equals("SulmunNew")) { // 나의강의실 설문페이지(2005.07.20)
                    this.performSulmunNew(request, response, box, out);
                }

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
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

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
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
            request.setAttribute("requestbox", box);

            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList<DataBox> list1 = null;
            // ArrayList list2 = null;
            String v_url = "";
            String v_isonoff = box.getStringDefault("p_isonoff", "ON");

            // String firststr = "";
            // ConfigSet conf = new ConfigSet();

            if (v_isonoff.equals("ON")) {
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_Subject_L.jsp";
                // 과정리스트
                list1 = bean.selectSubjectList(box);
                request.setAttribute("SubjectList", list1);
            } else if (v_isonoff.equals("OFF")) {
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjectOFF_L.jsp";
                list1 = bean.selectSubjectOffList(box);
                request.setAttribute("SubjectList", list1);
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
     * 전체 과정 리스트
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
            String v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_Subject_ALL_L.jsp";

            // 과정리스트
            ProposeCourseBean bean = new ProposeCourseBean();
            ArrayList<DataBox> list1 = bean.selectSubjectListAll(box);
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
     * 인기 과정 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjectListBest(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjectBest_L.jsp";

            // 과정리스트
            ProposeCourseBean bean = new ProposeCourseBean();
            // 직무 인기 과정
            box.put("p_gubun", "W");
            ArrayList<DataBox> list1 = bean.selectSubjectListBest(box);
            request.setAttribute("SubjectListW", list1);
            // 어학 인기 과정
            box.put("p_gubun", "L");
            ArrayList<DataBox> list2 = bean.selectSubjectListBest(box);
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
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            // 선수과정 리스트
            //ArrayList list1 = bean.selectListPre(box);
            //request.setAttribute("subjectPre", list1);
            // 후수과정 리스트
            //ArrayList list2 = bean.selectListNext(box);
            //request.setAttribute("subjectNext", list2);

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            // 과정상세정보 리스트
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);
            String place = dbox.getString("d_place");

            // 과정차수 리스트
            ArrayList<DataBox> list = bean.selectSubjSeqList(box);
            request.setAttribute("subjseqList", list);

            //            System.out.print("dbox=============>"+dbox);

            if (v_isonoff.equals("ON")) { //사이버 과정인 경우 일차리스트
                ArrayList<DataBox> list1 = bean.selectLessonList(box);
                request.setAttribute("lessonList", list1);
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjectPreviewON.jsp";
            } else if (v_isonoff.equals("OFF")) { //집합 과정인 경우 강좌리스트
                ArrayList<ProposeCourseData> list1 = bean.selectLectureList(box);
                request.setAttribute("lectureList", list1);
                // 시설정보
                InstitutionAdminBean bean1 = new InstitutionAdminBean();
                DataBox dbox2 = bean1.getInstitution(place);
                request.setAttribute("institution", dbox2);

                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjectPreviewOFF.jsp";
            }
            //System.out.print("v_url=============>"+v_url);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

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
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            String v_isonoff = box.getString("p_isonoff");
            String v_url = "";

            // 과정상세정보 리스트
            DataBox dbox = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);
            String place = dbox.getString("d_place");

            if (v_isonoff.equals("ON")) { //사이버 과정인 경우
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjectPreviewON_P.jsp";
            } else if (v_isonoff.equals("OFF")) { //집합 과정인 경우
                // 시설정보
                InstitutionAdminBean bean1 = new InstitutionAdminBean();
                DataBox dbox2 = bean1.getInstitution(place);
                request.setAttribute("institution", dbox2);

                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjectPreviewOFF_P.jsp";
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

            if (v_isonoff.equals("ON")) { //사이버 과정인 경우 일차리스트
                ArrayList<DataBox> list1 = bean.selectLessonList(box);
                request.setAttribute("lessonList", list1);
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_LectureListPop.jsp";
            } else if (v_isonoff.equals("OFF")) { //집합 과정인 경우 강좌리스트
                ArrayList<ProposeCourseData> list1 = bean.selectLectureList(box);
                request.setAttribute("lectureList", list1);
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_LectureListPop.jsp";
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

            ArrayList<ProposeCourseData> list = bean.selectLectureList(box);
            request.setAttribute("lectureList", list);

            if (v_isonoff.equals("ON")) { //사이버 과정인 경우 일차리스트
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjSeqPreviewON.jsp";
            } else if (v_isonoff.equals("OFF")) { //집합 과정인 경우 강좌리스트
                v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SubjSeqPreviewOFF.jsp";
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
     * 수강 신청
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

            // String v_isonoff = box.getString("p_isonoff");
            String v_msg = "";

            String v_url = "";

            int isOk = 0;
            isOk = bean.insertSubjectEduPropose(box);

            v_url = "/servlet/controller.propose.OutClassServlet";

            box.put("p_process", "SubjectPreviewPage");
            box.put("p_isonoff", box.getString("p_isonoff"));

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "propose.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
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
     * 컨텐츠 평가 보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performContentResearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            box.put("p_action", "go");
            //SulmunContentsResultBean bean = new SulmunContentsResultBean();
            SulmunRegistResultBean bean = new SulmunRegistResultBean();
            ArrayList<SulmunQuestionExampleData> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1);

            //MainSubjSearchBean bean = new MainSubjSearchBean();
            //ArrayList list = bean.selectSubjSearch(box);

            //
            //request.setAttribute("SubjectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/outclass/" + box.getSession("site") + "/zu_ContentRearch_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
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
            ArrayList<DataBox> list = bean.selectProposeList(box);

            request.setAttribute("ProposeList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/outclass/" + box.getSession("site") + "/zu_ProposeName_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
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

            this.performEducationSubjectList(request, response, box, out); // 학습중인 과정
            this.performProposeSubjectList(request, response, box, out); // 학습예정 과정
            this.performGraduationSubjectList(request, response, box, out); // 학습완료 과정

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/outclass/" + box.getSession("site") + "/zu_EducationSubject_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("EducationSubjectList()\r\n" + ex.getMessage());
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
    @SuppressWarnings("unchecked")
    public void performProposeCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MyClassBean bean = new MyClassBean();

            int isOk = bean.updateProposeCancel(box);
            String v_msg = "";
            String v_url = "/servlet/controller.study.MyClassServlet";
            box.put("p_process", "ProposeCancelPage");

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
            ArrayList<DataBox> list = bean.selectCancelPossibleList(box);

            request.setAttribute("CancelPossibleList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/outclass/" + box.getSession("site") + "/zu_ProposeCancel_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancelList()\r\n" + ex.getMessage());
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
            ArrayList<DataBox> list1 = bean.selectEducationSubjectList(box);

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
            ArrayList<DataBox> list1 = bean.selectProposeSubjectList(box);

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
            ArrayList<DataBox> list1 = bean.selectGraduationSubjectList(box);

            request.setAttribute("GraduationSubjectList", list1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("GraduationSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 개인 교육 이력
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
            String v_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_StudyHistoryTotal_L.jsp";

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

    //////////////////////// 온라인테스트 ////////////////////////////////////////////////////////////////////////
    /**
     * 온라인테스트 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performETestUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_ETestPaper_L2.jsp";
            ETestUserBean bean = new ETestUserBean();
            ArrayList<DataBox> list = bean.SelectUserList(box); // 평가
            request.setAttribute("ETestUserList", list);
            ArrayList<DataBox> list1 = bean.SelectUserHistoryList(box); // 결과
            request.setAttribute("ETestUserHistoryList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 응시 뷰
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performETestUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // String v_url = "/servlet/controller.outclass.OutClassServlet";
            String v_return_url = "";
            String v_msg = "";

            ETestPaperBean bean = new ETestPaperBean();
            v_return_url = bean.getPaperPathData(box);

            AlertManager alert = new AlertManager();

            if (v_return_url.equals("")) {
                v_msg = "문제지가 없습니다.";
                //alert.alertOkMessage(out, v_msg, v_url , box);
                alert.selfClose(out, v_msg);
            } else {
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserPaperListPage()\r\n" + ex.getMessage());
        }
    }

    public void performETestRetryListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            /*
             * request.setAttribute("requestbox", box); String v_return_url =
             * "/learn/user/exam/zu_ETestUserPaperRetry_L.jsp"; String v_url =
             * "/servlet/controller.exam.ETestUserServlet";
             * 
             * ETestUserBean bean = new ETestUserBean(); String v_return =
             * bean.InsertRetry(box);
             * 
             * StringTokenizer st = new StringTokenizer(v_return, ",");
             * 
             * int retry = Integer.parseInt(st.nextToken());
             * 
             * if (retry == 0){
             * 
             * ServletContext sc = getServletContext(); RequestDispatcher rd =
             * sc.getRequestDispatcher(v_return_url); rd.forward(request,
             * response);
             * 
             * } else if (retry == 1){
             * 
             * int isOk = Integer.parseInt(st.nextToken());
             * 
             * String v_msg = ""; AlertManager alert = new AlertManager();
             * if(isOk == 1) { v_msg = "insert.ok"; alert.alertOkMessage(out,
             * v_msg, v_url , box); } else if(isOk == 99) { v_msg =
             * "이미 응시한 시험입니다."; alert.alertOkMessage(out, v_msg, v_url , box); }
             * else if(isOk == 44) { v_msg =
             * "점수가 기준에 미달하여 저장되지 않았습니다. 고용보험 환급과정은 30점 이상 취득하여야 저장됩니다.";
             * alert.alertFailMessage(out, v_msg); } else if(isOk == 98) { v_msg
             * = "학습하지 않은 Object 또는 미응시 평가가 있으므로 저장되지 않습니다";
             * alert.alertFailMessage(out, v_msg); }else if(isOk == 97) { v_msg
             * = "현재 학습중인 학생신분이 아니므로 평가결과를 저장하지 않으며 결과를 확인하실 수 없습니다.";
             * alert.alertFailMessage(out, v_msg); }else { v_msg =
             * "insert.fail"; alert.alertFailMessage(out, v_msg); }
             * 
             * }
             */} catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestRetryListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 문제지 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performETestUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String v_url = "/servlet/controller.outclass.OutClassServlet";
            //jkh 임시적용0224
            //String v_url  = "/learn/user/etest/zu_close.jsp";
            ETestUserBean bean = new ETestUserBean();

            int isOk = bean.WriteETestUserResult(box); //

            AlertManager alert = new AlertManager();
            String v_msg = "";
            box.put("p_process", "ETestUserPaperTextResult");

            if (isOk == 1) {
                v_msg = "제출하였습니다.";
                isOk = bean.IncreaseTrycnt(box);
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 99) {
                v_msg = "이미 응시한 시험입니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "제출에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }

            /*
             * int isOk = bean.InsertResult(box);
             * 
             * String v_msg = ""; box.put("p_process", "ETestUserPaperResult");
             * 
             * AlertManager alert = new AlertManager(); if(isOk == 1) { v_msg =
             * "insert.ok"; alert.alertOkMessage(out, v_msg, v_url , box); }
             * else if(isOk == 99) { v_msg = "이미 응시한 시험입니다.";
             * alert.alertOkMessage(out, v_msg, v_url , box); } else if(isOk ==
             * 44) { v_msg =
             * "점수가 기준에 미달하여 저장되지 않았습니다. 고용보험 환급과정은 30점 이상 취득하여야 저장됩니다.";
             * alert.alertFailMessage(out, v_msg); } else if(isOk == 98) { v_msg
             * = "학습하지 않은 Object 또는 미응시 평가가 있으므로 저장되지 않습니다";
             * alert.alertFailMessage(out, v_msg); }else if(isOk == 97) { v_msg
             * = "현재 학습중인 학생신분이 아니므로 평가결과를 저장하지 않으며 결과를 확인하실 수 없습니다.";
             * alert.alertFailMessage(out, v_msg); }else { v_msg =
             * "insert.fail"; alert.alertFailMessage(out, v_msg); }
             */
            Log.info.println(this, box, true);
        } catch (Exception ex) {
            Log.info.println(this, box, true);
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserResultInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performETestUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/etest/zu_ETestPaperResult_L.jsp";

            ETestUserBean bean = new ETestUserBean();
            ArrayList<ArrayList<DataBox>> list = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", list);

            ArrayList<Object> list2 = bean.SelectUserPaperResult2(box);
            request.setAttribute("UserPaperResult2", list2);

            ETestResultBean bean1 = new ETestResultBean();
            Vector<String> v1 = bean1.SelectResultAverage2(box);
            request.setAttribute("ETestResultAverage", v1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserPaperResult()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 사용자 문제지 보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performETestUserPaperResult2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/etest/zu_ETestPaperResult_L2.jsp";

            ETestUserBean bean = new ETestUserBean();
            ArrayList<ArrayList<DataBox>> list = bean.SelectUserPaperResult(box); // 결과
            request.setAttribute("UserPaperResult", list);

            ArrayList<Object> list2 = bean.SelectUserPaperResult2(box);
            request.setAttribute("UserPaperResult2", list2);

            ETestResultBean bean1 = new ETestResultBean();

            Vector<String> v1 = bean1.SelectResultAverage(box);
            request.setAttribute("ETestResultAverage", v1);

            //          Vector v1 = bean.SelectPersonAverage(box);
            //          request.setAttribute("PersonAverage", v1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserPaperResult()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 제출후 결과보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performETestUserPaperTextResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/etest/zu_ETestPaperTextResult_L.jsp";

            ETestUserBean bean = new ETestUserBean();
            ArrayList<ArrayList<DataBox>> list = bean.SelectUserPaperTextResult(box);
            request.setAttribute("UserPaperResult", list);

            ArrayList<Object> list2 = bean.SelectUserPaperTextResult2(box);
            request.setAttribute("UserPaperResult2", list2);

            // ETestResultBean bean1 = new ETestResultBean();
            // Vector v1 = bean1.SelectResultAverage2(box);
            // request.setAttribute("ETestResultAverage", v1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestUserPaperTextResult()\r\n" + ex.getMessage());
        }
    }

    //////////////// 설문 ///////////////////////////////////////////
    /**
     * 온라인테스트 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/research/zu_SulmunSubjPaper_L.jsp";

            //SulmunContentsUserBean bean2 = new SulmunContentsUserBean();
            SulmunRegistUserBean bean2 = new SulmunRegistUserBean();
            SulmunSubjUserBean bean1 = new SulmunSubjUserBean();

            ArrayList<DataBox> list1 = bean1.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList1", list1);

            ArrayList<DataBox> list2 = bean2.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList2", list2);

            ArrayList<DataBox> list4 = bean2.selectGraduationSubjectList(box);
            request.setAttribute("GraduationSubjectList2", list4);

            /*
             * SulmunSubjUserBean bean = new SulmunSubjUserBean(); ArrayList
             * list1 = bean.SelectUserList(box);
             * request.setAttribute("SulmunUserList", list1);
             * 
             * ArrayList list2 = bean.SelectUserResultList(box);
             * request.setAttribute("SulmunUserResultList", list2);
             */
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 - 나의 강의실
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunNew(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/outclass/" + box.getSession("site") + "/zu_SulmunNew_L.jsp";

            SulmunSubjUserBean bean1 = new SulmunSubjUserBean(); // 과정
            //SulmunContentsUserBean bean2 = new SulmunContentsUserBean();   // 컨텐츠
            SulmunRegistUserBean bean2 = new SulmunRegistUserBean(); // 가입경로
            SulmunCommonUserBean bean3 = new SulmunCommonUserBean(); // 일반
            SulmunTargetUserBean bean5 = new SulmunTargetUserBean(); // 대상자

            ArrayList<DataBox> list1 = bean1.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList1", list1);

            ArrayList<DataBox> list2 = bean2.selectEducationSubjectList(box); // 교육중 과정
            request.setAttribute("EducationSubjectList2", list2);

            ArrayList<DataBox> list4 = bean2.selectGraduationSubjectList(box);
            request.setAttribute("GraduationSubjectList2", list4);

            ArrayList<DataBox> list_c = bean2.selectSulmunContentsList(box); // 컨텐츠
            request.setAttribute("SulmunContents", list_c);

            ArrayList<DataBox> list_t = bean5.selectSulmunTargetList(box); // 대상자
            request.setAttribute("SulmunTarget", list_t);

            ArrayList<DataBox> list_cm = bean3.SelectUserList(box); // 일반
            request.setAttribute("SulmunCommon", list_cm);

            /*
             * SulmunSubjUserBean bean = new SulmunSubjUserBean(); ArrayList
             * list1 = bean.SelectUserList(box);
             * request.setAttribute("SulmunUserList", list1);
             * 
             * ArrayList list2 = bean.SelectUserResultList(box);
             * request.setAttribute("SulmunUserResultList", list2);
             */
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunNew()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/research/zu_SulmunSubjUserPaper_I.jsp";

            SulmunSubjPaperBean bean = new SulmunSubjPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));

            box.put("p_upperclass", "ALL");

            DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1); //2005.08.22 by정은년 jsp에서 다 값 받아옴.

            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 문제지 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.outclass.OutClassServlet";

            SulmunSubjUserBean bean = new SulmunSubjUserBean();
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            box.put("p_process", "SulmunUserPaperListPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk == 2) {
                v_msg = "설문에 응답해 주셔서 감사합니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 1) {
                v_msg = "설문 기간 이전입니다.";
                alert.alertFailMessage(out, v_msg);
            } else if (isOk == 3) {
                v_msg = "설문 기간이 완료되었습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "이미 해당 설문에 응답하셨습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 온라인테스트 사용자 문제지 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/research/zu_SulmunSubjUserResultList.jsp";

            SulmunSubjUserBean bean = new SulmunSubjUserBean();

            DataBox dbox = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", dbox);

            DataBox dbox1 = bean.selectSulmunUser(box);
            request.setAttribute("SulmunUserData", dbox1);

            SulmunSubjPaperBean bean1 = new SulmunSubjPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean1.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");
            DataBox dbox2 = bean1.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox2);
            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperResult()\r\n" + ex.getMessage());
        }
    }

}