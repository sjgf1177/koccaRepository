//*********************************************************
//  1. 제      목: 강사 활동관리
//  2. 프로그램명: TutorActionAdminServlet.java
//  3. 개      요: 강사 활동관리 관리자 servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//**********************************************************
package controller.tutor;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.tutor.TutorAdminBean;

@WebServlet("/servlet/controller.tutor.TutorActionAdminServlet")
public class TutorActionAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4279611632751612693L;

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
        // int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            // System.out.println("TutorActionAdminServlet  강사활동관리:"+v_process);

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            //System.out.println("v_process : " + v_process);
            ///////////////////////////////////////////////////////////////////
            if (!AdminUtil.getInstance().checkRWRight("TutorActionAdminServlet", v_process, out, box)) {
                return;
            }

            ///////////////////////////////////////////////////////////////////
            if (v_process.equals("listPage")) { // 강사활동관리 리스트
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("listPage2")) { // 강사활동관리 리스트
                this.performListPage2(request, response, box, out);
            } else if (v_process.equals("ListExcel")) { // 강사활동관리 리스트
                this.performListExcel(request, response, box, out);
            } else if (v_process.equals("SendFreeMail")) { //in case of send free mail
                this.performSendFreeMail(request, response, box, out);
            } else if (v_process.equals("CareerPrint")) { //경력증 출력
                this.performCareerPrint(request, response, box, out);
            } else if (v_process.equals("teachDetail")) { // 튜터 강이이력 조회
                this.performTeachDetail(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 강사활동관리 리스트
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

            if (box.getString("p_onlyonoff_sw").equals("")) {
                TutorAdminBean bean = new TutorAdminBean();
                ArrayList lists = bean.selectTutorActionList(box);
                request.setAttribute("actionlist", lists);
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorAction_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 강사활동관리 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performListPage2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            TutorAdminBean bean = new TutorAdminBean();
            ArrayList lists = bean.selectTutorActionList(box);
            request.setAttribute("actionlist", lists);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorAction_L2.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void performListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            TutorAdminBean bean = new TutorAdminBean();
            ArrayList lists = bean.selectTutorActionList(box);
            request.setAttribute("actionlist", lists);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorAction_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList2()\r\n" + ex.getMessage());
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

    public void performCareerPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            TutorAdminBean bean = new TutorAdminBean();
            DataBox lists = bean.selectCareerPrint(box);
            request.setAttribute("careerprint", lists);

            ArrayList<DataBox> list = bean.selectCareerList(box);
            request.setAttribute("careerlist", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_Career_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }

    public void performTeachDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            TutorAdminBean bean = new TutorAdminBean();

            DataBox dbox = bean.selectTutor(box); //
            request.setAttribute("tutorInfo", dbox);

            ArrayList<DataBox> list1 = bean.selectSubjHistory(box); //
            request.setAttribute("tutorSubjLIst", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/tutor/za_TutorDetailHistory_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDetailPage()\r\n" + ex.getMessage());
        }
    }
}