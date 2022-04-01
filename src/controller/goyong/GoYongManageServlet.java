//*********************************************************
//  1. 제      목:
//  2. 프로그램명: GoYongManageServlet.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//**********************************************************
package controller.goyong;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.goyong.GoYongManageBean;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.goyong.GoYongManageServlet")
public class GoYongManageServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("GoYongManageServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("HuryunExe")) {
                this.performHuryunExePage(request, response, box, out);
            } else if (v_process.equals("ExcelList")) {
                this.performExcelList(request, response, box, out);
            } else if (v_process.equals("ExcelList2")) {
                this.performExcelList2(request, response, box, out);
            } else if (v_process.equals("PresenceBook")) {
                this.performPresenceBook(request, response, box, out);
            } else if (v_process.equals("PresenceSignList")) {
                this.performPresenceSignList(request, response, box, out);
            } else if (v_process.equals("PresencePostList")) {
                this.performPresencePostList(request, response, box, out);
            } else if (v_process.equals("PresenceProfList")) {
                this.performPresenceProfList(request, response, box, out);
            } else if (v_process.equals("SuryoJeung")) {
                this.performSuryoJeung(request, response, box, out);
            } else if (v_process.equals("SuryoJeungList")) {
                this.performSuryoJeungList(request, response, box, out);
            } else if (v_process.equals("SuryoJeungFrame")) {
                this.performSuryoJeungFrame(request, response, box, out);
            } else if (v_process.equals("SuryoJeungPrint")) {
                this.performSuryoJeungPrint(request, response, box, out);
            } else if (v_process.equals("TxtList")) {
                this.performTxtList(request, response, box, out);
            } else if (v_process.equals("TxtList2")) {
                this.performTxtList2(request, response, box, out);
                // 화면변경(훈련실시신고-전체)
            } else if (v_process.equals("HuryunExe1")) { //사원 상세보기
                this.performHuryunExe1Page(request, response, box, out);
                // 화면변경(고용보험리포팅)
            } else if (v_process.equals("report")) {
                this.performGoyongReport(request, response, box, out);
                // 화면변경(고용보험리포팅)
            } else if (v_process.equals("reportCourseList")) {
                this.performreportCourseList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performHuryunExePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectStudentList(box);
            request.setAttribute("StudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_HuryunExe_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performHuryunExePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 화면변경(훈련실시신고-전체) EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performHuryunExe1Page(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectStudentListView(box);
            request.setAttribute("StudentListView", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_HuryunExe1_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performHuryunExe1Page()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectStudentListView(box);
            request.setAttribute("StudentListView", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_HuryunExe1_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performExcelList2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectSuryoStudentList(box);
            request.setAttribute("StudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_SuRyo_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList2()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performPresenceBook(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectProposeYList(box);
            request.setAttribute("StudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_PresenceBook_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPresenceBook()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPresenceSignList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_PresenceSignFrame.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPresenceSignList()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPresencePostList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_PresencePostFrame.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPresencePostList()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPresenceProfList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_PresenceProfFrame.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPresenceProfList()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSuryoJeung(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectSuryoStudentList(box);
            request.setAttribute("StudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_SuryoJeung_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSuryoJeung()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSuryoJeungFrame(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            //RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_SuryoJeungFrame.jsp");
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_SuryoJeung_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSuryoJeungFrame()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSuryoJeungList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_SuryoFrame.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSuryoJeungFrame()\r\n" + ex.getMessage());
        }
    }

    /**
     * EDUCATION SUBJECT PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSuryoJeungPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectSuryoJeungPrint(box);
            request.setAttribute("StudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_SuryoJeung_P.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSuryoJeung()\r\n" + ex.getMessage());
        }
    }

    /**
     * TxtList PAGE 훈련실시신고
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performTxtList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectStudentList2(box);
            request.setAttribute("StudentList2", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_HuryunExe_T.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * TxtList PAGE 수료증발급대장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performTxtList2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            GoYongManageBean bean = new GoYongManageBean();
            ArrayList list = bean.selectStudentList3(box);
            request.setAttribute("StudentList3", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_SuryoExe_T.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * TxtList PAGE 수료증발급대장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performGoyongReport(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            //            GoYongManageBean bean = new GoYongManageBean();
            //            ArrayList list = bean.selectStudentList3(box);
            //            request.setAttribute("report", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_GoyongReport_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 리포트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performreportCourseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/goyong/za_GoyongReportCourse_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }
}