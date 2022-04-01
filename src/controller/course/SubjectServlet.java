//**********************************************************
//1. 제	  목: 과정코드 SERVLET
//2. 프로그램명: SubjectServlet.java
//3. 개	  요:
//4. 환	  경: JDK 1.3
//5. 버	  젼: 0.1
//6. 작	  성: anonymous 2003. 07. 15

//7. 수	  정: 
//				 
//**********************************************************
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

import com.credu.course.CourseBean;
import com.credu.course.PreviewData;
import com.credu.course.SubjectBean;
import com.credu.course.SubjectData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.course.SubjectServlet")
public class SubjectServlet extends HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("SubjectServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("listPage")) { //in case of 과정 조회 화면
                this.performListPage(request, response, box, out);

            } else if (v_process.equals("insertPage")) { //in case of 과정 등록 화면
                this.performInsertPage(request, response, box, out);

            } else if (v_process.equals("insert")) { //in case of 과정 등록
                this.performInsert(request, response, box, out);

            } else if (v_process.equals("updatePage")) { //in case of 과정 수정 화면
                this.performUpdatePage(request, response, box, out);

            } else if (v_process.equals("update")) { //in case of 과정 수정
                this.performUpdate(request, response, box, out);

            } else if (v_process.equals("insertOffPage")) { //in case of 과정 등록 화면
                this.performInsertOffPage(request, response, box, out);

            } else if (v_process.equals("insertOff")) { //in case of 과정 등록
                this.performInsertOff(request, response, box, out);

            } else if (v_process.equals("updateOffPage")) { //in case of 과정 수정 화면
                this.performUpdateOffPage(request, response, box, out);

            } else if (v_process.equals("updateOff")) { //in case of 과정 수정
                this.performUpdateOff(request, response, box, out);

            } else if (v_process.equals("delete")) { //in case of 과정 삭제
                this.performDelete(request, response, box, out);

            } else if (v_process.equals("relatedGrcodePage")) { //in case of 과정별 교육그룹별 조회 화면
                this.performRelatedGrcodePage(request, response, box, out);

            } else if (v_process.equals("relatedGrcodeInsert")) { //in case of 과정별 교육그룹별 등록
                this.performRelatedGrcodeInsert(request, response, box, out);

            } else if (v_process.equals("previewPage")) { //in case of 과정맛보기 조회 화면
                this.performPreviewPage(request, response, box, out);
            } else if (v_process.equals("previewInsertPage")) { //in case of 과정맛보기 등록 화면
                this.performPreviewInsertPage(request, response, box, out);
            } else if (v_process.equals("previewInsert")) { //in case of 과정맛보기 등록
                this.performPreviewInsert(request, response, box, out);
            } else if (v_process.equals("previewUpdatePage")) { //in case of 과정맛보기 수정 화면
                this.performPreviewUpdatePage(request, response, box, out);
            } else if (v_process.equals("previewUpdate")) { //in case of 과정맛보기 수정
                this.performPreviewUpdate(request, response, box, out);
            } else if (v_process.equals("previewDelete")) { //in case of 과정맛보기 삭제
                this.performPreviewDelete(request, response, box, out);
            } else if (v_process.equals("relatedSubjPage")) { //in case of 연관과정 조회 화면
                this.performRelatedSubjPage(request, response, box, out);
            } else if (v_process.equals("relatedSubjInsertPage")) { //in case of 연관과정 등록 화면
                this.performRelatedSubjInsertPage(request, response, box, out);
            } else if (v_process.equals("relatedSubjInsert")) { //in case of 연관과정 등록
                this.performRelatedSubjInsert(request, response, box, out);
            } else if (v_process.equals("subjseqPage")) { //in case of 과정차수리스트 조회 화면
                this.performSubjseqPage(request, response, box, out);
            } else if (v_process.equals("buyBagPreviewPage")) { //in case of 과정차수리스트 조회 화면
                this.performBuyBagPreviewPage(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 과정코드리스트 VIEW
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_Subject_L.jsp";

            SubjectBean bean = new SubjectBean();
            ArrayList list1 = bean.SelectSubjectList(box);
            request.setAttribute("SubjectList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 등록 PAGE - 사이버
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
            String v_return_url = "/learn/admin/course/za_Subject_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 등록 - 사이버
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.InsertSubject(box);

            String v_msg = "";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                //v_msg = "insert.ok";	
                v_msg = "과정이 개설되었습니다.";
                //box.put("s_action","go"); 
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                //v_msg = "insert.fail";   
                v_msg = "과정개설에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 수정 PAGE - 사이버
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Subject_U.jsp";

            SubjectBean bean = new SubjectBean();
            SubjectData data = bean.SelectSubjectData(box);
            request.setAttribute("SubjectData", data);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 수정 - 사이버
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.UpdateSubject(box);

            String v_msg = "";
            if (box.getString("p_pagegubun").equals("Approval")) {
                box.put("p_process", "approvalListPage");
            } else {
                box.put("p_process", "listPage");
            }

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                //v_msg = "update.ok";
                v_msg = "과정정보가 수정되었습니다.";
                //box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                //v_msg = "update.fail";   
                v_msg = "과정정보 저장에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 등록 PAGE - 집합
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertOffPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/course/za_Subject_Off_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 등록 - 집합
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertOff(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.InsertOffSubject(box);

            String v_msg = "";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                //v_msg = "insert.ok";	   
                v_msg = "과정이 개설되었습니다.";
                //box.put("s_action","go");
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                //v_msg = "insert.fail";   
                v_msg = "과정개설에 실패하였습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 수정 PAGE - 집합
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdateOffPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Subject_Off_U.jsp";

            SubjectBean bean = new SubjectBean();
            SubjectData data = bean.SelectSubjectData(box);
            request.setAttribute("SubjectData", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 수정 - 집합
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdateOff(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.UpdateOffSubject(box);

            String v_msg = "";
            if (box.getString("p_pagegubun").equals("Approval")) {
                box.put("p_process", "approvalListPage");
            } else {
                box.put("p_process", "listPage");
            }

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                //v_msg = "update.ok";	   
                v_msg = "과정정보가 수정되었습니다.";
                box.put("s_action", "go");
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 삭제
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.DeleteSubject(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("p_upperclass", "ALL");
            box.put("p_subj", "ALL");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                //v_msg = "delete.ok";	   
                v_msg = "과정정보가 삭제되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                //v_msg = "delete.fail";   
                v_msg = "과정정보 삭제에 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹연결 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRelatedGrcodePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_SubjectGrcode_L.jsp";

            SubjectBean bean = new SubjectBean();

            ArrayList list1 = bean.TargetGrcodeList(box);
            request.setAttribute("TargetGrcodeList", list1);

            ArrayList list2 = bean.SelectedGrcodeList(box);
            request.setAttribute("SelectedGrcodeList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 교육그룹연결 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRelatedGrcodeInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.RelatedGrcodeInsert(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            //box.put("p_upperclass", "ALL");
            //box.put("p_subj", "ALL");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRelatedGrcodeInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정맛보기 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Preview_L.jsp";

            SubjectBean bean = new SubjectBean();

            ArrayList list = bean.PreviewGrcodeList(box);
            request.setAttribute("PreviewGrcodeList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정맛보기 등록 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Preview_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정맛보기 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.InsertPreview(box);

            String v_msg = "";
            box.put("p_process", "previewPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정맛보기 수정 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_Preview_U.jsp";

            SubjectBean bean = new SubjectBean();
            PreviewData data = bean.SelectPreviewData(box);
            request.setAttribute("PreviewData", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.UpdatePreview(box);

            String v_msg = "";
            box.put("p_process", "previewPage");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정코드 수정
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            SubjectBean bean = new SubjectBean();
            int isOk = bean.DeletePreview(box);

            String v_msg = "";
            box.put("p_process", "previewPage");

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
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 연관과정리스트 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRelatedSubjPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_RelatedSubject_L.jsp";

            SubjectBean bean = new SubjectBean();

            box.put("p_subjgubun", "PRE");
            ArrayList list1 = bean.RelatedSubjList(box);
            request.setAttribute("PreRelatedSubjList", list1);

            box.put("p_subjgubun", "NEXT");
            ArrayList list2 = bean.RelatedSubjList(box);
            request.setAttribute("NextRelatedSubjList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 연관등록 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRelatedSubjInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/course/za_RelatedSubject_I.jsp";

            CourseBean bean0 = new CourseBean();
            ArrayList list1 = bean0.TargetSubjectList(box);
            request.setAttribute("TargetRelatedSubjList", list1);

            SubjectBean bean = new SubjectBean();
            ArrayList list2 = bean.SelectedRelatedSubjList(box);
            request.setAttribute("SelectedRelatedSubjList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 연관과정 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRelatedSubjInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.course.SubjectServlet";

            String v_grcode = box.getString("p_grcode");
            String v_subj = box.getString("p_subj");
            String v_subjnm = box.getString("p_subjnm");

            SubjectBean bean = new SubjectBean();
            int isOk = bean.RelatedSubjInsert(box);

            String v_msg = "";
            box.clear();
            box.put("p_process", "relatedSubjPage");
            box.put("p_grcode", v_grcode);
            box.put("p_subj", v_subj);
            box.put("p_subjnm", v_subjnm);

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRelatedGrcodeInsert()\r\n" + ex.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    /**
     * 차수조회 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjseqPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강신청 입력양식 미리보기 PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performBuyBagPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/course/zu_buyBagSubject_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRelatedGrcodePage()\r\n" + ex.getMessage());
        }
    }

}
