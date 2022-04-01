//*********************************************************
//1. 제      목: SUBJECT INFORMATION USER SERVLET
//2. 프로그램명: KoccaProposeCourseServlet.java
//3. 개      요: 과정안내 사용자 servlet(Kocca)
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 하경태  2005.12.21
//7. 수      정:
//**********************************************************
package controller.propose;
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
import com.credu.propose.KProposeCourseBean;
import com.credu.propose.ProposeCourseBean;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.propose.KProposeCourseServlet")
public class KProposeCourseServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    @SuppressWarnings("unchecked")
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

            box.put("p_grgubun","K01");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if(!v_process.equals("SubjectPreviewPopup")) {

                if (!AdminUtil.getInstance().checkLogin(out, box)) {
                    this.LoginChk(request, response, box, out);
                }
            }

            if(v_process.equals("SubjectList")){                            // 과정리스트
                this.performSubjectList(request, response, box, out);
            }
            else if (v_process.equals("OffLineApply")){                     // 오프라인 신청  할때
                this.performApply(request, response, box, out);
            }
            else if (v_process.equals("OffLineCancel")){                    // 오프라인 신청  취소할때
                this.performApplyCancel(request, response, box, out);
            }
            else if (v_process.equals("OffLineSubjPage")){                  // 오프라인 신청/취소 페이지로 이동
                this.performApplyPage(request, response, box, out);
            }
            else if (v_process.equals("OffLineView")){                      // 오프라인 신청/취소 상세보기
                this.performApplyViewPage(request, response, box, out);
            }
            else if (v_process.equals("SubjectEduBill")){                   // 수강신청전 지불 수단 페이지.
                this.performEduBill(request, response, box, out);
            }
            else if (v_process.equals("SubjectEduInputBill")){              // 무통장입금  페이지.
                this.performEduInputBill(request, response, box, out);
            }
            else if (v_process.equals("BillCheck")){                        // 무통장입금  페이지 (입금자 입력).
                this.performEduInputCheck(request, response, box, out);
            }
            else if(v_process.equals("SubjectEduPropose")){                 // 수강신청
                this.performSubjectEduPropose(request, response, box, out);
            }
            else if(v_process.equals("SubjectPreviewPopup")){               // 과정 미리보기 팝업
                this.performSubjectPreviewPopup(request, response, box, out);
            }
            else if(v_process.equals("SubjectIntro")){                      // 수강신청과정 안내
                this.performSubjectIntro(request, response, box, out);
            }
            else if (v_process.equals("CreditCard")){                   // 카드 결제시 분기.
                this.performCreditCard(request, response, box, out);
            }
        }
        catch(Exception ex) {
         ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    LoginChk
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void LoginChk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("tUrl",request.getRequestURI());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request,response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
    과정 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

            KProposeCourseBean bean = new KProposeCourseBean();
            ArrayList list1 = null;
            String v_url = "";

            v_url = "/learn/user/kocca/course/ku_Subject_L.jsp";
            // 과정리스트
            list1 = bean.selectSubjectList(box);
            request.setAttribute("SubjectList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
    OffLine 전체 과정 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performApplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/kocca/course/ku_OffLineSubjApply_L.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjectListAll()\r\n" + ex.getMessage());
        }
    }

    /**
    OffLine 신청 취소
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performApplyCancel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url = "/servlet/controller.propose.KProposeCourseServlet";

            KProposeCourseBean bean = new KProposeCourseBean();
            int isOk = bean.OffLineApplyCancelSubject(box);

            String v_msg = "";
            box.put("p_process", "OffLineSubjPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                //v_msg = "insert.ok";
                v_msg = "신청 취소 되었습니다.";
                //box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                //v_msg = "insert.fail";
                v_msg = " 신청 취소에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    OffLine 신청
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url = "/servlet/controller.propose.KProposeCourseServlet";

            KProposeCourseBean bean = new KProposeCourseBean();
            int isOk = bean.OffLineApplySubject(box);

            String v_msg = "";
            box.put("p_process", "OffLineSubjPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                //v_msg = "insert.ok";
                v_msg = "신청 되었습니다.";
                //box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                //v_msg = "insert.fail";
                v_msg = "오프라인 과정 신청에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }


    /**
    오프라인 과정 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performApplyViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            KProposeCourseBean bean = new KProposeCourseBean();
            DataBox dbox = bean.OffLineApplyView(box);

            request.setAttribute("OffLineSubjApply", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_OffLineSubjApply_R.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
    SUBJECT EDUCATION PROPOSE Bill
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performEduBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            KProposeCourseBean bean  = new KProposeCourseBean();

            DataBox dbox  = bean.getSelectBill(box);
            request.setAttribute("selectEduBill", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_ProposeBill_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
    SUBJECT EDUCATION PROPOSE Bill(무통장입금)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performEduInputBill(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_ProposeInputBill_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
    SUBJECT EDUCATION PROPOSE Bill(무통장입금)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performEduInputCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/course/ku_ProposeInputBill_Check.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
    수강 신청
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjectEduPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean = new ProposeCourseBean();

//            String v_isonoff = box.getString("p_isonoff");
            String v_msg = "";
            String v_billYn = box.getString("p_billYn");
//            String v_courseYn = box.getString("p_iscourseYn");

            String v_url ="";

            int isOk = 0;
            isOk = bean.insertSubjectEduPropose(box);

// 성능 테스트용
//MyClassBean bean1 = new MyClassBean();
//isOk = bean1.updateProposeCancel(box);

            v_url = "/servlet/controller.study.KMyClassServlet?p_process=ProposeCancelPage";

            //box.put("p_process","ProposeCancelPage");
//            box.put("p_isonoff",box.getString("p_isonoff"));

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "propose.ok";
                if(v_billYn.equals("Y")){
                    alert.alertOkMessage(out, v_msg, v_url , box, true, true);
                }
                else
                {
                    alert.alertOkMessage(out, v_msg, v_url , box);
                }
            }
            else {
                v_msg = "propose.fail";
                v_msg = box.getStringDefault("err_msg",v_msg);
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("subjectEduPropose()\r\n" + ex.getMessage());
        }
    }

    /**
    과정 미리보기 팝업
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSubjectPreviewPopup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean  = new ProposeCourseBean();

            String v_isonoff    = box.getString("p_isonoff");
            String v_url = "";

//           과정상세정보 리스트
            DataBox dbox  = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);
            //String place = dbox.getString("d_place");

            // 과정차수 리스트
            ArrayList list1 = bean.selectSubjSeqList(box);
            request.setAttribute("subjseqList", list1);

            if(v_isonoff.equals("ON")){         //사이버 과정인 경우 일차리스트
                ArrayList list2 = bean.selectLessonList(box);
                request.setAttribute("lessonList", list2);
                v_url= "/learn/user/kocca/course/ku_SubjectPreviewON_P.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
    수강신청 과정 안내
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjectIntro(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url= "/learn/user/kocca/course/ku_SubjectIntro1.jsp";
            String tab = box.getString("p_tab");
            if(tab.equals("2")) v_url = "/learn/user/kocca/course/ku_SubjectIntro2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
    수강신청안내 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performCreditCard(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            box.put("p_subj",request.getParameter("p_subj"));
            box.put("p_iscourseYn",request.getParameter("p_iscourseYn"));
            box.put("p_course",request.getParameter("p_course"));
            box.put("p_subjseq",request.getParameter("p_subjseq"));
            box.put("p_year",request.getParameter("p_year"));
            box.put("p_grtype",request.getParameter("p_grtype"));
            box.put("p_transaction",request.getParameter("transaction"));
            box.put("p_realpay",request.getParameter("amount"));
            box.put("p_productcode",request.getParameter("productcode"));
            box.put("p_authnumber",request.getParameter("authnumber"));
            box.put("p_payselect","D");

            if(request.getParameter("respcode").equals("0000"))
            {
                performSubjectEduPropose(request, response, box, out);
            }

            //ServletContext sc = getServletContext();
            //RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/course/gu_ApplyIntro.jsp");
            //rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }
}