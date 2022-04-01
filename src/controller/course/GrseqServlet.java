// **********************************************************
// 1. 제 목: 교육차수관리 SERVLET
// 2. 프로그램명: GrseqServlet.java
// 3. 개 요:
// 4. 환 경: JDK 1.3
// 5. 버 젼: 0.1
// 6. 작 성: LeeSuMin 2003. 07. 14
// 7. 수 정:
//
// **********************************************************
package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.GrseqBean;
import com.credu.course.GrseqData;
import com.credu.course.OffSubjLectureData;
import com.credu.course.SubjseqData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.course.GrseqServlet")
public class GrseqServlet extends HttpServlet implements Serializable {

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
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "listPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("GrseqServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("listPage")) { // 교육차수 리스트 조회 화면
                this.performListPage(request, response, box, out);

            } else if (v_process.equals("listDetailPage")) { // 교육차수 세부 리스트 화면
                this.performListDetailPage(request, response, box, out);

            } else if (v_process.equals("insertPage")) { // 교육차수 등록 화면
                this.performInsertPage(request, response, box, out);

            } else if (v_process.equals("insert")) { // 교육차수 등록(저장)
                this.performInsert(request, response, box, out);

            } else if (v_process.equals("updatePage")) { // 교육차수 수정 화면
                this.performUpdatePage(request, response, box, out);

            } else if (v_process.equals("update")) { // 교육차수 수정(저장)
                this.performUpdate(request, response, box, out);

            } else if (v_process.equals("assignPage")) { // 교육차수에 과정/코스 연결
                this.performAssign(request, response, box, out);

            } else if (v_process.equals("assignSave")) { // 교육차수에 과정/코스 연결저장
                this.performAssignSave(request, response, box, out);

            } else if (v_process.equals("updatePageSubjCourse")) { // 교육차수에 과정 일괄 수정페이지
                this.performUpdatePageSubjCourse(request, response, box, out);

            } else if (v_process.equals("updateEdulimit")) { // 일일진도 수정
                this.performUpdateEdulimit(request, response, box, out);

            } else if (v_process.equals("updatePropose")) { // 수강신청기간 수정
                this.performUpdatePropose(request, response, box, out);

            } else if (v_process.equals("updateEdu")) { // 학습기간 수정
                this.performUpdateEdu(request, response, box, out);

            } else if (v_process.equals("delCourse")) { // 지정 코스 삭제
                this.performDelSubjcourse(request, response, box, out);

            } else if (v_process.equals("delSubj")) { // 지정 과정 삭제
                this.performDelSubjcourse(request, response, box, out);

            } else if (v_process.equals("delSubjArr")) { // 지정 과정 삭제(다수 선택)
                this.performDelSubjcourseArr(request, response, box, out);

            } else if (v_process.equals("addCourse")) { // 코스차수 추가
                this.performAddSubjcourse(request, response, box, out);

            } else if (v_process.equals("addSubj")) { // 과정차수 추가
                this.performAddSubjcourse(request, response, box, out);

            } else if (v_process.equals("delGrseq")) { // 교육차수 삭제
                this.performDelGrseq(request, response, box, out);

            } else if (v_process.equals("updateSubjseqPage")) { // 과정차수정보 수정화면
                this.performUpdateSubjseqPage(request, response, box, out);

            } else if (v_process.equals("updateSubjseq")) { // 과정차수정보 수정 저장
                this.performUpdateSubjseq(request, response, box, out);

            } else if (v_process.equals("updatePageSubjScore")) { // 과정수료점수 수정화면
                this.performUpdateSubjScorePage(request, response, box, out);

            } else if (v_process.equals("updateSubjScore")) { // 과정수료점수 수정 저장
                this.performUpdateSubjScore(request, response, box, out);

            } else if (v_process.equals("lectureInsertPage")) { // 강좌 등록 화면
                this.performLectureInsertPage(request, response, box, out);

            } else if (v_process.equals("lectureInsert")) { // 강좌 등록
                this.performLectureInsert(request, response, box, out);

            } else if (v_process.equals("lectureUpdatePage")) { // 강좌 수정 화면
                this.performLectureUpdatePage(request, response, box, out);

            } else if (v_process.equals("lectureUpdate")) { // 강좌 수정
                this.performLectureUpdate(request, response, box, out);

            } else if (v_process.equals("lectureDelete")) { // 강좌 삭제
                this.performLectureDelete(request, response, box, out);

            } else if (v_process.equals("addSubjSeq")) { // 과정차수를 n개만큼 추가
                this.performAddSubjSeq(request, response, box, out);

            } else if (v_process.equals("updateOther")) { // 설문, 수강신청취소기간, 운영담당자 저장
                this.performUpdateOther(request, response, box, out);

            } else if (v_process.equals("edulimit")) { // 1일 최대학습량 일괄변경
                this.performEdulimit(request, response, box, out);
                
            } else if (v_process.equals("excelUpload")) { // 교육차수 일괄등록 페이지(엑셀업로드)
            	this.performExcelUpload(request, response, box, out);
            	
            } else if (v_process.equals("excelInsert")) { // 교육차수 일괄등록(엑셀업로드)
            	this.performExcelInsert(request, response, box, out);
            	
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 교육차수 리스트 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "";
            if (box.getString("p_action").equals("go")) {
                GrseqBean bean = new GrseqBean();
                ArrayList list1 = bean.SelectGrseqScreenList(box);
                request.setAttribute("GrseqList", list1);
            }
            if (box.getString("isExcel").equals("ok")) {
                v_return_url = "/learn/admin/course/za_Grseq_E.jsp";
                box.setSession("isExcel", "");
            } else {
                v_return_url = "/learn/admin/course/za_Grseq_L.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            // Log.info.println(this, box, "Dispatch to /learn/admin/course/za_Grseq_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수 세부 리스트 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performListDetailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_GrseqDetail_L.jsp";
            GrseqBean bean = new GrseqBean();
            ArrayList list = bean.SelectGrseqDetailList(box);
            request.setAttribute("GrseqDetailList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            // Log.info.println(this, box, "Dispatch to /learn/admin/course/za_Grseq_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드 등록 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_Grseq_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            // Log.info.println(this, box, "Dispatch to /learn/admin/course/za_Grseq_I.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();

            int isOk = bean.InsertGrseq(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("ALL"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            // Log.info.println(this, box, v_msg + " on GrseqInsert");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드 수정 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Grseq_U.jsp";

            GrseqBean bean = new GrseqBean();

            GrseqData data = bean.SelectGrseqData(box);
            request.setAttribute("GrseqData", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.UpdateGrseq(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("s_homepageyn", box.getString("p_homepageyn"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/Update");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드에 과정/코스 지정화면
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performAssign(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Grseq_Assign.jsp";

            GrseqBean bean = new GrseqBean();

            ArrayList list = bean.AssignedSubjCourseList(box);
            request.setAttribute("GrseqAssignData", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAssign()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드에 과정/코스 지정- 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performAssignSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.SaveAssign(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/AssignSave");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수에 과정 일괄 수정페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdatePageSubjCourse(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Grseq_Assign_U.jsp";

            GrseqBean bean = new GrseqBean();
            ArrayList list = bean.selectSubjCourseList(box);
            request.setAttribute("SubjCourseList", list);
            
            ArrayList courseList = bean.searchSubjCourseList(box);
            request.setAttribute("courseList", courseList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePageSubjCourse()\r\n" + ex.getMessage());
        }
    }

    /**
     * 일일진도 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdateEdulimit(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.updateEdulimit(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/AssignSave");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateEdulimit()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강신청기간 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdatePropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.updatePropose(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/AssignSave");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePropose()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습기간 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdateEdu(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.updateEdu(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/AssignSave");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateEdu()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지정 과정/코스 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performDelSubjcourse(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.delSubjcourse(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("p_action", "go");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_mgyear"));
            box.put("s_grseq", box.getString("p_mgrseq"));

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/DelSubjcourse");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지정 과정/코스 삭제(다수 선택 삭제)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performDelSubjcourseArr(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.delSubjcourseArr(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("p_action", "go");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_mgyear"));
            box.put("s_grseq", box.getString("p_mgrseq"));

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/DelSubjcourse");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정/코스 추가
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performAddSubjcourse(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.addSubjcourse(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_mgyear"));
            box.put("s_grseq", box.getString("p_mgrseq"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/AddSubjcourse");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelGrseq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.delGrseq(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_gyear", "ALL");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/DelGrseq");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정차수코드 수정 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdateSubjseqPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Subjseq_U.jsp";

            GrseqBean bean = new GrseqBean();
            SubjseqData data = bean.SelectSubjseqData(box);
            request.setAttribute("SubjseqData", data);

            if (box.getString("p_isonoff").equals("OFF")) { // 집합과정인 경우 강좌리스트
                ArrayList list1 = bean.selectLectureList(box);
                request.setAttribute("LectureList", list1);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정차수코드 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdateSubjseq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.UpdateSubjseq(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("p_action", "go");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/UpdateSubjseq");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * LECTURE INSERT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performLectureInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            GrseqBean bean = new GrseqBean();
            ArrayList list1 = bean.selectTutorList(box);
            request.setAttribute("TutorList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_Lecture_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LectureInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * LECTURE INSERT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performLectureInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GrseqBean bean = new GrseqBean();

            int isOk = bean.insertLecture(box);
            String v_msg = "";
            String v_url = "/servlet/controller.course.GrseqServlet";
            box.put("p_process", "updateSubjseqPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/LectureInsert");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LectureInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * LECTURE UPDATE PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performLectureUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            GrseqBean bean = new GrseqBean();
            OffSubjLectureData data = bean.selectLecture(box);
            request.setAttribute("selectLecture", data);

            ArrayList list1 = bean.selectTutorList(box);
            request.setAttribute("TutorList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_Lecture_U.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LectureUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * LECTURE UPDATE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performLectureUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GrseqBean bean = new GrseqBean();

            int isOk = bean.updateLecture(box);
            String v_msg = "";
            String v_url = "/servlet/controller.course.GrseqServlet";
            box.put("p_process", "updateSubjseqPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/LectureUpdate");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LectureUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * LECTURE DELETE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performLectureDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GrseqBean bean = new GrseqBean();

            int isOk = bean.deleteLecture(box);
            String v_msg = "";
            String v_url = "/servlet/controller.course.GrseqServlet";
            box.put("p_process", "updateSubjseqPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/LectureDelete");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("LectureDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육차수코드 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performAddSubjSeq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();

            int isOk = bean.AddSubjSeq(box);

            String v_msg = "";
            box.put("p_process", "listDetailPage");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            // Log.info.println(this, box, v_msg + " on AddSubjSeq");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performAddSubjSeq()\r\n" + ex.getMessage());
        }
    }

    /**
     * 1일최대학습량 일괄변경
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performEdulimit(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.SaveEdulimit(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/performEdulimit");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEdulimit()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설명 : 설문, 수강신청취소기간, 운영담당자 저장
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     * @author swchoi
     */
    @SuppressWarnings("unchecked")
    public void performUpdateOther(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.updateOther(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/AssignSave");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateEdu()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정차수 수료점수 수정 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdateSubjScorePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_SubjScore_U.jsp";

            GrseqBean bean = new GrseqBean();
            ArrayList list = bean.selectSubjCourseList(box);
            request.setAttribute("SubjCourseList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateSubjScorePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정차수 수료점수 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performUpdateSubjScore(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            int isOk = bean.UpdateSubjScore(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
            // Log.info.println(this, box, v_msg + " on Grseq/UpdateSubjseq");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * 교육차수 일괄등록 페이지(엑셀업로드)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performExcelUpload(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box);
    		String v_url = "/learn/admin/course/za_GrseqExcel_L.jsp";
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher(v_url);
    		rd.forward(request, response);
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performExcelUpload()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * 교육차수 일괄등록(엑셀업로드)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performExcelInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		String v_url = "/servlet/controller.course.GrseqServlet";

            GrseqBean bean = new GrseqBean();
            ArrayList list = bean.insertGrseqExcel(box);
            request.setAttribute("list", list);

            String v_msg = "";
            box.put("p_process", 	"excelResult");
            box.put("list", 	list);

            AlertManager alert = new AlertManager();
            if (list != null) {
                //alert.alertOkMessage(out, "", v_url, box, false, false);
            	request.setAttribute("requestbox", box);
            	v_url = "/learn/admin/course/za_GrseqExcel_R.jsp";
            	ServletContext sc = getServletContext();
        		RequestDispatcher rd = sc.getRequestDispatcher(v_url);
        		rd.forward(request, response);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performExcelInsert()\r\n" + ex.getMessage());
    	}
    }
}
