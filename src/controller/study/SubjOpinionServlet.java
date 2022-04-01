//**********************************************************
//  1. 제      목: SUBJ OPINION SERVLET
//  2. 프로그램명: SubjOpinionServlet.java
//  3. 개      요: 과정 콘텐츠 의견달기 SERVLET
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2011.02.28
//  7. 수      정:
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

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.SubjOpinionBean;
import com.credu.system.AdminUtil;
import com.credu.system.StudyCountBean;

/**
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */

@WebServlet("/servlet/controller.study.SubjOpinionServlet")
public class SubjOpinionServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5890044932037635739L;

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
        //		MultipartRequest multi = null;
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

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("SubjOpinionList")) { //in case of activity submit list
                this.performSubjOpinionList(request, response, box, out);
            } else if (v_process.equals("SubjOpinionDetail")) {
                this.performSubjOpinionDetail(request, response, box, out);
            } else if (v_process.equals("SubjOpinionInsertPage")) {
                this.performSubjOpinionInsertPage(request, response, box, out);
            } else if (v_process.equals("SubjOpinionInsert")) { // 등록
                this.performSubjOpinionInsert(request, response, box, out);
            } else if (v_process.equals("SubjOpinionUpdatePage")) {
                this.performSubjOpinionUpdatePage(request, response, box, out);
            } else if (v_process.equals("SubjOpinionUpdate")) {
                this.performSubjOpinionUpdate(request, response, box, out);
            } else if (v_process.equals("SubjOpinionDelete")) {
                this.performSubjOpinionDelete(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * SubjOpinionList
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjOpinionList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // 과정별 메뉴 접속 정보 추가
            box.put("p_menu", "03");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            request.setAttribute("requestbox", box);
            SubjOpinionBean bean = new SubjOpinionBean();

            // 나의 의견보기 리스트
            ArrayList<DataBox> list1 = bean.selectSubjMyOpinionList(box);
            request.setAttribute("SubjMyOpinionList", list1);

            // 타 학습자 의견보기 리스트
            ArrayList<DataBox> list2 = bean.selectSubjOpinionList(box);
            request.setAttribute("SubjOpinionList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjOpinion_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjQnaSubjList()\r\n" + ex.getMessage());
        }
    }

    /**
     * OpinionDetail
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjOpinionDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjOpinionBean bean = new SubjOpinionBean();
            ArrayList<DataBox> list1 = bean.selectSubjOpinionDetail(box);

            request.setAttribute("SubjOpinionDetail", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjOpinion_R.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjOpinionDetail()\r\n" + ex.getMessage());
        }
    }

    /**
     * Opinion INSERT
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjOpinionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjOpinionBean bean = new SubjOpinionBean();
            int isOk = bean.opinionInsert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjOpinionServlet";
            box.put("p_process", "SubjOpinionList");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else if (isOk == -1) {
                v_msg = "opinioninsert.fail";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjOpinionInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * Opinion UPDATE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjOpinionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjOpinionBean bean = new SubjOpinionBean();
            int isOk = bean.updateOpinion(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjOpinionServlet";
            box.put("p_process", "SubjOpinionList");

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
            throw new Exception("SubjOpinionUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * Opinion DELETE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSubjOpinionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjOpinionBean bean = new SubjOpinionBean();
            int isOk = bean.deleteOpinion(box);

            String v_msg = "";
            String v_url = "/servlet/controller.study.SubjOpinionServlet";
            box.put("p_process", "SubjOpinionList");

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
            throw new Exception("SubjOpinionDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * Opinion 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjOpinionInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjOpinion_I.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjOpinionInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * SUBJ OPINION UPDATE PAGE
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjOpinionUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SubjOpinionBean bean = new SubjOpinionBean();
            //ArrayList list1 = bean.selectSubjQnaDetail2(box);

            DataBox dbox = bean.selectSubjOpinionDetail2(box);

            request.setAttribute("SubjOpinionDetail2", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjOpinion_U.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjOpinionUpdate()\r\n" + ex.getMessage());
        }
    }

}