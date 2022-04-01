//**********************************************************
//  1. 제      목: 접속통계 제어하는 서블릿
//  2. 프로그램명 : StudyCountServlet.java
//  3. 개      요: 접속통계 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 7
//  7. 수      정:
//**********************************************************

package controller.system;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.system.*;
import com.credu.library.*;
import com.credu.study.*;
import com.credu.homepage.*;

@WebServlet("/servlet/controller.system.KStudyCountServlet")
public class KStudyCountServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            ///////////////////////////////////////////////////////////////////
            // selectMonthDay : 운영자 화면이므로 체크
/*            if (v_process.equals("selectMonthDay")){
                if (!AdminUtil.getInstance().checkRWRight("StudyCountServlet", v_process, out, box)) {
                    return;
                }
            }*/
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if(v_process.equals("myActivity")) {          //  Myactivity 페이지
                this.performSelectActivity(request, response, box, out);
            }else if(v_process.equals("writeLog")) {          //  학습별접속통계 등록때
                this.performWriteLog(request, response, box, out);
            }
            else if(v_process.equals("myActivityExcel")){          // 출력
                    this.performMyActivityExcel(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    MY Activity 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectActivity(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MyClassBean bean = new MyClassBean();

            ArrayList list1 = bean.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList", list1);


            String v_subj = box.getString("subj");
            String v_year = box.getString("year");
            //String v_subjseq = box.getString("subjseq");
			String v_subjseq = box.getString("grseq");

            if(v_subj.equals("") && v_year.equals("") && v_subjseq.equals("")){ // 최초 화면 로딩시에 첫번째 과정 정보를 입력한다.
                if(list1 != null && list1.size() > 0) {
                    DataBox dbox    = (DataBox)list1.get(0);
                    box.put("subj",     dbox.getString("d_subj"));
                    box.put("year",     dbox.getString("d_year"));
                    box.put("subjseq",  dbox.getString("d_subjseq"));
                }
            }

            StudyCountBean bean2 = new StudyCountBean();

            // 학습별 통계
            ArrayList list2 = bean2.SelectActivityList(box);
            request.setAttribute("selectActivity", list2);

            // 학습시간 정보
            ArrayList list3 = bean2.selectLearningTime(box);
            request.setAttribute("selectLearningTime", list3);

            // 최근학습일
            String date  = bean2.selectStudyDate(box);
            request.setAttribute("studyDate", date);

            // 강의실 접근정보
            String count  = bean2.selectStudyCount(box);
            request.setAttribute("studyCount", count);

            // 강의실 접근정보
            String step  = bean2.selectStudyStep(box);
            request.setAttribute("studyStep", step);


            // 게시판 정보
            ArrayList list4 = bean2.selectBoardCnt(box);
            request.setAttribute("selectBoard", list4);


			// 회원정보
			MemberInfoBean bean3 = new MemberInfoBean();
			DataBox list_member = bean3.memberInfoView(box);
			request.setAttribute("memberView", list_member);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/study/ku_Activity_Study.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectActivity()\r\n" + ex.getMessage());
        }
    }


    /**
    학습별 통계 등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performWriteLog(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            StudyCountBean bean = new StudyCountBean();
            int isOk = bean.writeLog(box);


            String v_msg = "";
            String v_url = "";
//            box.put("p_process", "select");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
//                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            }
            else {
//                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performWriteLog()\r\n" + ex.getMessage());
        }
    }

    /**
    출력
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMyActivityExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {

            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Activity_Study_E.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyActivityExcel()\r\n" + ex.getMessage());
        }
    }
}

