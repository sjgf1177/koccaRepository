//*********************************************************
//  1. 제      목: STUDY STATUS ADMIN SERVLET
//  2. 프로그램명: StudyTotalStatusServlet.java
//  3. 개      요: 학습 현황 관리자 servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
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

import com.credu.common.SearchAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.CounselMailBean;
import com.credu.study.StudentStatusAdminBean;
import com.credu.study.StudyStatusAdminBean;
import com.credu.system.MemberData;


@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.study.StudyTotalStatusServlet")
public class StudyTotalStatusServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
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
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if(v_process.equals("PersonalSearchPage")){                 //in case of 개인별 학습현황
                this.performPersonalSearchPage(request, response, box, out);

            } else if(v_process.equals("PersonalNameSelectList")){        //in case of 동명이인 조회
                this.performPersonalNameSelectList(request, response, box, out);

            } else if(v_process.equals("PersonalSelect")){                //in case of 개인별 학습현황-개인이력 상세조회
                this.performPersonalSelect(request, response, box, out);

            } else if(v_process.equals("PersonalProposeList")){           //in case of 개인별 신청과정 list
                this.performPersonalProposeList(request, response, box, out);

            } else if(v_process.equals("PersonalEducationList")){         //in case of 개인별 수강과정 list
                this.performPersonalEducationList(request, response, box, out);

            } else if(v_process.equals("PersonalGraduationList")){        //in case of 개인별 수료과정 list
                this.performPersonalGraduationList(request, response, box, out);

            } else if(v_process.equals("PersonalScoreCompleteList")){     //in case of 개인별 수료과정 list
                this.performPersonalScoreCompleteList(request, response, box, out);

            } else if(v_process.equals("SubjectLessonList")){             //in case of subject lesson list
                this.performSubjectLessonList(request, response, box, out);

            } else if(v_process.equals("LearningStatusListByClass")){     //in case of learning status list by class
                this.performLearningStatusListByClass(request, response, box, out);

            } else if(v_process.equals("LearningStatusExcelByClass")){     //in case of learning status excel by class
                this.performLearningStatusExcelByClass(request, response, box, out);

            } else if(v_process.equals("StudyTotalList")){     //in case of 종합학습현황
                this.performLearningStatusListByScore(request, response, box, out);

            } else if(v_process.equals("LearningStatusExcelByTotal")){     //in case of learning status excel by score
                this.performLearningStatusExcelByTotal(request, response, box, out);

            } else if(v_process.equals("LearningStatusListByAssignment")){//in case of learning status list by assignment
                this.performLearningStatusListByAssignment(request, response, box, out);

            } else if(v_process.equals("LearningStatusExcelByAssignment")){//in case of learning status excel by assignment
                this.performLearningStatusExcelByAssignment(request, response, box, out);

            } else if(v_process.equals("LearningStatusListByGrcode")){    //in case of learning status list by grcode
                this.performLearningStatusListByGrcode(request, response, box, out);

            } else if(v_process.equals("LearningStatusExcelByGrcode")){    //in case of learning status excel by grcode
                this.performLearningStatusExcelByGrcode(request, response, box, out);

            } else if(v_process.equals("LearningTimeSelectList")){        //in case of learning time select list
                this.performLearningTimeSelectList(request, response, box, out);

            } else if(v_process.equals("LearningTimeSelectExcel")){        //in case of learning time select excel
                this.performLearningTimeSelectExcel(request, response, box, out);

            } else if(v_process.equals("SendFormMail")){                  //in case of send form mail
                this.performSendFormMail(request, response, box, out);

            } else if(v_process.equals("SendFreeMail")){                  //in case of send free mail
                this.performSendFreeMail(request, response, box, out);

            } else if(v_process.equals("SmsListPage")){                  //sms 발송리스트
                this.performMailList(request, response, box, out);

            } else if(v_process.equals("MailListPage")){                  //mail/sms 발송리스트
                this.performMailList(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    개인별 학습현황
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performPersonalSearchPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalSearch_I.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("personalSearchPage()\r\n" + ex.getMessage());
        }
    }

    /**
    동명이인 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performPersonalNameSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectPersonalNameList(box);

            request.setAttribute("PersonalNameSelectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_NamesakeList.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalNameSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    개인이력 상세조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performPersonalSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String  v_selTab        = box.getString("p_selTab");    //탭선택값
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            MemberData data = bean.selectPersonal(box);
            request.setAttribute("PersonalSelect", data);

            //검색 실패시 error 메세지 처리
            String v_msg = "";
            AlertManager alert = new AlertManager();
            if(data.getUserid() == null || data.getUserid().equals("")) {
                v_msg = "search.fail";
                alert.alertFailMessage(out, v_msg);
            }

            //과정조회
            box.put("p_userid", data.getUserid());
            if(v_selTab.equals("propose") || v_selTab.equals("")){                  // 신청과정
                this.performPersonalProposeList(request,response,box,out);
            }else if(v_selTab.equals("education")){                                 // 수강과정
                this.performPersonalEducationList(request,response,box,out);
            }else if(v_selTab.equals("graduation")){                                // 수료과정
                this.performPersonalGraduationList(request,response,box,out);
            }else if(v_selTab.equals("scorecomplete")){                             // 학점이수현황
                this.performPersonalScoreCompleteList(request,response,box,out);
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PersonalSelect_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalSelect()\r\n" + ex.getMessage());
        }
    }

    /**
    개인별 신청과정  LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performPersonalProposeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list1 = bean.selectProposeList(box);

            request.setAttribute("PersonalProposeList", list1);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalProposeList()\r\n" + ex.getMessage());
        }
    }

    /**
    개인별 수강과정 LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performPersonalEducationList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list1 = bean.selectEducationList(box);

            request.setAttribute("PersonalEducationList", list1);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalEducationList()\r\n" + ex.getMessage());
        }
    }

    /**
    개인별 수료과정 LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performPersonalGraduationList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list1 = bean.selectGraduationList(box);

            request.setAttribute("PersonalGraduationList", list1);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalGraduationList()\r\n" + ex.getMessage());
        }
    }


    /**
    개인별 학점이수현황 LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performPersonalScoreCompleteList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list1 = bean.selectScoreCompleteList(box);

            request.setAttribute("PersonalScoreCompleteList", list1);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonalScoreCompleteList()\r\n" + ex.getMessage());
        }
    }


    /**
    SUBJECT LESSON LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSubjectLessonList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            //ArrayList list = bean.selectSubjectLessonList(box);

            //request.setAttribute("SubjectLessonList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_SubjectLesson.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectLessonList()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING STATUS LIST BY CLASS
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusListByClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectClassLearningList(box);

            request.setAttribute("LearningStatusListByClass", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByClass_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByClass()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING STATUS EXCEL BY CLASS
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusExcelByClass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectClassLearningList(box);

            request.setAttribute("LearningStatusExcelByClass", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByClass_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByClass()\r\n" + ex.getMessage());
        }
    }

    /**
    TOTAL STATUS LIST BY SCORE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusListByScore(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectTotalScoreStatusList(box);

            request.setAttribute("StudyTotalList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_StudyTotalStatus_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByScore()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING STATUS EXCEL BY SCORE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusExcelByTotal(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectTotalScoreStatusList(box);

            request.setAttribute("LearningStatusExcelByTotal", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByTotal_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByScore()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING STATUS LIST BY ASSIGNMENT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusListByAssignment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectAssignmentLearningList(box);

            request.setAttribute("LearningStatusListByAssignment", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByAssignment_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByAssignment()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING STATUS EXCEL BY ASSIGNMENT
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusExcelByAssignment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectAssignmentLearningList(box);

            request.setAttribute("LearningStatusExcelByAssignment", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByAssignment_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByAssignment()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING STATUS LIST BY GRCODE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusListByGrcode(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list1 = bean.selectGrcodeLearningList(box);

            request.setAttribute("LearningStatusListByGrcode", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByGrcode_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusListByGrcode()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING STATUS EXCEL BY GRCODE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningStatusExcelByGrcode(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list1 = bean.selectGrcodeLearningList(box);

            request.setAttribute("LearningStatusExcelByGrcode", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningStatusByGrcode_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningStatusExcelByGrcode()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING TIME SELECT LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningTimeSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectLearningTimeList(box);

            request.setAttribute("LearningTimeSelectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningTimeSelect_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningTimeSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    LEARNING TIME SELECT EXCEL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performLearningTimeSelectExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudyStatusAdminBean bean = new StudyStatusAdminBean();
            ArrayList list = bean.selectLearningTimeList(box);

            request.setAttribute("LearningTimeSelectExcel", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_LearningTimeSelect_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LearningTimeSelectExcel()\r\n" + ex.getMessage());
        }
    }

    /**
    SEND FORM MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSendFormMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            StudentStatusAdminBean bean = new StudentStatusAdminBean();

            int isOk            = bean.sendFormMail(box);
            String v_mailcnt    = isOk+"";
            String v_msg = "";
            String v_url = "/servlet/controller.study.StudyTotalStatusServlet";
            box.put("p_process",box.getString("p_rprocess"));
            box.put("p_mailcnt",v_mailcnt);
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "mail.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else {
                v_msg = "mail.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFormMail()\r\n" + ex.getMessage());
        }
    }

    /**
    SEND FREE MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSendFreeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }


    /**
    sms List
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSmsList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PerformSmsList_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSmsList()\r\n" + ex.getMessage());
        }
    }

    /**
    sms List
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performMailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // 개인정보
            SearchAdminBean bean = new SearchAdminBean();
            MemberData data = bean.selectPersonalInformation(box);
            request.setAttribute("SelectMemberInfo", data);

            //메일내역조회
            CounselMailBean bean1 = new CounselMailBean();
            ArrayList list1 = bean1.selectCounselMailList(box);
            request.setAttribute("selectCounsel", list1);


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/study/za_PerformMailList_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("PersonalSelect()\r\n" + ex.getMessage());
        }
    }

}