//**********************************************************
//  1. 제      목: 통합QnA를 제어하는 서블릿
//  2. 프로그램명 : QnaAdminServlet.java
//  3. 개      요: 통합Qna 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 7
//  7. 수     정1:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.LoginBean;
import com.credu.homepage.QnaAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.SmsBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.homepage.QnaAdminServlet")
public class QnaAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("QnaAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("selectView")){                                     //Qna 상세보기로 이동할때
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("delete")) {                                 // Qna 삭제할때
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("repdelete")) {                              // Qna 답변삭제할때
                this.performRepDelete(request, response, box, out);
            } else if(v_process.equals("deleteCourse")) {                           // 과정질문방 삭제할때
                this.performDeleteCourse(request, response, box, out);
            } else if(v_process.equals("selectListCourse")) {                       // 과정질문방 리스트 보여질때
                this.performSelectListCourse(request, response, box, out);
            } else if(v_process.equals("selectViewCourse")) {                       // 과정질문방 뷰페이지로 이동할때
                this.performSelectViewCourse(request, response, box, out);
            } else if(v_process.equals("reply")) {                                  // Qna 답변등록할때
                this.performReply(request, response, box, out);
            } else if(v_process.equals("replyCourse")) {                            // 과정질문방 답변등록할때
                this.performReplyCourse(request, response, box, out);
            } else if(v_process.equals("replyPage")) {                              // 답변페이지로 이동할때
                this.performReplyPage(request, response, box, out);
            } else if(v_process.equals("replyUpdate")) {                            // 답변
                this.performReplyUpdate(request, response, box, out);
            } else if(v_process.equals("repupdate")) {                              // 답변페이지로 이동할때
                this.performReplyUpdatePage(request, response, box, out);
            } else if(v_process.equals("replyCoursePage")) {                        // 과정질문방 답변페이지로 이동할때
                this.performReplyCoursePage(request, response, box, out);
            } else if(v_process.equals("viewsave")) {                               //  보기화면에서 분류지정하고 확인저장할때
                this.performViewSave(request, response, box, out);
            } else if(v_process.equals("viewCoursesave")) {                         //  과정질문방보기화면에서 분류지정하고 확인저장할때
                this.performViewCourseSave(request, response, box, out);
            } else if(v_process.equals("selectList")) {                             //  리스트
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("repcourseupdatepage")) {                    // Qna 답변페이지
                this.performRepCourseUpdatePage(request, response, box, out);
            } else if(v_process.equals("repcourseupdate")) {                        // Qna 답변수정할때
                this.performRepCourseUpdate(request, response, box, out);
            } else if(v_process.equals("repcoursedelete")) {                        // Qna 답변삭제
                this.performRepCourseDelete(request, response, box, out);
            } else if(v_process.equals("updateRepStatus")) {                        // Qna 답변 상태 수정 (사용안함)
                this.performUpdateRepStatus(request, response, box, out);
            } else if(v_process.equals("updateCourseRepStatus")) {                  // 과정질문방 답변상태 수정(사용안함)
                this.performUpdateCourseRepStatus(request, response, box, out);
            } else if(v_process.equals("excelList")) {                           //통합Q&A 엑셀 받기
                this.performExcelList(request, response, box, out);
            }else if(v_process.equals("insertPage")){
                this.performInsertPage(request, response, box, out);
            }else if(v_process.equals("insert")) {         //  등록할때
                 this.performInsert(request, response, box, out);
            } else{
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            //SubjBoardAdminBean bean = new SubjBoardAdminBean();

            //box.sync("p_subj", "1111");
            //box.put("p_tabseq", bean.selectSBTableseq(box));
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/zu_Qna_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

      /**
    등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            //AdminQnaBean qna = new AdminQnaBean();

            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.insertQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }


    /**
    QnA리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            QnaAdminBean bean = new QnaAdminBean();
            ArrayList list = bean.selectListQna(box);
            request.setAttribute("selectList", list);
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Qna_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Qna_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    QnA 과정질문방 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectListCourse(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            QnaAdminBean bean = new QnaAdminBean();
            ArrayList list = bean.selectListQnaCourse(box);
            request.setAttribute("selectListView", list);
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_QnaCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_QnaCourse_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectListCourse()\r\n" + ex.getMessage());
        }
    }

    /**
    Qna 상세보기로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            QnaAdminBean bean = new QnaAdminBean();
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);
            ArrayList list = bean.selectQnaListA(box);
            request.setAttribute("selectQnaListA", list);


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Qna_R.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Qna_R.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
    Qna 과정질문방 상세보기로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectViewCourse(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            QnaAdminBean bean = new QnaAdminBean();

            DataBox dbox = bean.selectQnaCourse(box);
            request.setAttribute("selectQnaCourse", dbox);
            bean.viewQnAUpdate(box);

            ArrayList list = bean.selectQnaCourseListA(box);
            request.setAttribute("selectQnaCourseListA", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_QnaCourse_R.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_QnaCourse_R.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
    // Qna 뷰화면에서 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performViewSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.viewupdate(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectView");
            //      수정 후 해당 뷰 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewSave()\r\n" + ex.getMessage());
        }
    }



    /**
    // Qna 뷰화면에서 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performReplyUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.viewReplayUpdate(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectView");

            //      수정 후 해당 뷰 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewSave()\r\n" + ex.getMessage());
        }
    }


    /**
    // Qna 과정질문방 뷰화면에서 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performViewCourseSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.viewCourseupdate(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectViewCourse");
            //      수정 후 해당 뷰 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewCourseSave()\r\n" + ex.getMessage());
        }
    }


    /**
    Qna 답변
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            QnaAdminBean bean = new QnaAdminBean();
            DataBox dbox = bean.selectQna(box);
            request.setAttribute("selectQna", dbox);

            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Qna_A.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Qna_A.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


    /**
    Qna 답변수정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performReplyUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            QnaAdminBean bean = new QnaAdminBean();
            DataBox dbox = bean.selectRepQna(box);
            request.setAttribute("selectQna", dbox);

            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_QnaRep_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_QnaRep_U.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    Qna 과정질문방 답변
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performReplyCoursePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            QnaAdminBean bean = new QnaAdminBean();
            DataBox dbox = bean.selectQnaCourse(box);
            request.setAttribute("selectQnaCourse", dbox);

            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_QnaCourse_A.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_QnaCourse_A.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyCoursePage()\r\n" + ex.getMessage());
        }
    }



    /**
     * QNA 답변글 저장할때
     * @param box      receive from the form object and session
     * @return isOk    1:update success,0:update fail
     * @throws Exception
     */
    public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();
            int isOk = bean.insertQnaAns(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {

                String p_userid = box.getString("p_userid");  // 입력자 아아디
                /*String p_title = box.getString("p_title");    //제목
                String p_content = box.getString("contents");   // 내용*/
                LoginBean lgbean = new LoginBean();
                box.put("p_userid", p_userid);
                String p_handphone = "";
                //String p_email = "";
                p_handphone = lgbean.getHandphone(box);
                //p_email = lgbean.getEmail(box);

                if (!p_handphone.equals("")) {
                    SmsBean smbean = new SmsBean();
                    smbean.sendSMSMsg(p_handphone,"02-3219-5483","[KOCCA] 회원님의 질문에 답변이  등록되었습니다. **나의강의실->상담내역 확인**");
                }
                /*
                if( !p_email.equals("")){
                    box.put("email", p_email);
                    box.put("name", "운영자");
                    MailSet mailset = new MailSet(box);
                    mailset.sendMail("", "test@naver.com", p_title, p_content, "1", "");
                }
                */
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReply()\r\n" + ex.getMessage());
        }
    }



    /**
     * QNA 과정질문방 답변글 저장할때
     * @param box      receive from the form object and session
     * @return isOk    1:update success,0:update fail
     * @throws Exception
     */
    public void performReplyCourse(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();
            int isOk = bean.insertQnaCourseAns(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectViewCourse");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyCourse()\r\n" + ex.getMessage());
        }
    }


    /**
    // Qna 삭제할때(답변까지 함께 삭제)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            QnaAdminBean bean = new QnaAdminBean();


            int isOk = bean.deleteQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            String v_types = box.getString("p_types");

            if(v_types.equals("0")){
                box.put("p_process", "selectList");
            }else{
                box.put("p_process", "selectView");
            }

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }


    /**
    // Qna 삭제할때(답변 삭제)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performRepDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.deleteRepQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";

            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }


    /**
    // Qna 과정질문방 삭제할때(답변까지 함께 삭제)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performDeleteCourse(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.deleteCourseQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteCourse()\r\n" + ex.getMessage());
        }
    }

    /**
    Qna 답변수정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performRepCourseUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            QnaAdminBean bean = new QnaAdminBean();
            DataBox dbox = bean.selectRepCourseQna(box);
            request.setAttribute("selectQna", dbox);

            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_QnaCousrseRep_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_QnaRep_U.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


    /**
    // Qna 뷰화면에서 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performRepCourseUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.viewReplayCourseUpdate(box);
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectViewCourse");

            //      수정 후 해당 뷰 페이지로 돌아가기 위해
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewSave()\r\n" + ex.getMessage());
        }
    }


    /**
    // Qna 삭제할때(답변 삭제)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performRepCourseDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.deleteRepCourseQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";

            box.put("p_process", "selectViewCourse");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    // Qna 뷰 화면에서 답변 상태 변경
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performUpdateRepStatus(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.updateRepStatus(box);
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectView");

            //      수정 후 해당 뷰 페이지로 돌아가기 위해
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewSave()\r\n" + ex.getMessage());
        }
    }

    /**
    // Qna 뷰화면에서 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performUpdateCourseRepStatus(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            QnaAdminBean bean = new QnaAdminBean();

            int isOk = bean.updateCourseRepStatus(box);
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.QnaAdminServlet";
            box.put("p_process", "selectViewCourse");

            //      수정 후 해당 뷰 페이지로 돌아가기 위해
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewSave()\r\n" + ex.getMessage());
        }
    }

    //엑셀받기
    public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
            try {
                request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

                QnaAdminBean bean = new QnaAdminBean();
                ArrayList list = bean.excelListQna(box);
                request.setAttribute("selectList", list);
                request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Qna_L_Excel.jsp");
                rd.forward(request, response);
            }catch (Exception ex) {
                ErrorManager.getErrorStackTrace(ex, out);
                throw new Exception("performSelectList()\r\n" + ex.getMessage());
            }
        }

}

