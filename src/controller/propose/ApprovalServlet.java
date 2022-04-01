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
package controller.propose;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ApprovalBean;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.propose.ApprovalServlet")
public class ApprovalServlet extends HttpServlet implements Serializable {

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
    @Override
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
            if (!AdminUtil.getInstance().checkRWRight("ApprovalServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("listPage")) { // 수강신청승인 리스트 조회 화면
                this.performListPage(request, response, box, out);

            } else if (v_process.equals("approvalProcess")) { // Batch 승인처리
                this.performApproval(request, response, box, out);

            } else if (v_process.equals("OffLinelistPage")) { // OffLine 수강신청승인 리스트 조회 화면
                this.performOffLineListPage(request, response, box, out);

            } else if (v_process.equals("OffLineapprovalProcess")) { // OffLine Batch 승인처리

                this.performOffLineApproval(request, response, box, out);
            } else if (v_process.equals("ExcelDown")) { // 엑셀다운로드
                this.performExcelDown(request, response, box, out);

            } else if (v_process.equals("OffLineExcelDown")) { // OffLine 엑셀다운로드
                this.performOffLineExcelDown(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 신청승인 리스트 조회
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
            String v_return_url = "/learn/admin/propose/za_Approval_L.jsp";
            ApprovalBean bean = new ApprovalBean();
            if (box.getString("p_action").equals("go")) {
                ArrayList<DataBox> list1 = bean.SelectApprovalScreenList(box);
                request.setAttribute("ApprovalList", list1);

                DataBox dbox = bean.getSubjInfomat(box);
                request.setAttribute("isManagerStatus", dbox);
            }

            int appauth = bean.getApprovalAuth(box);
            request.setAttribute("appauth", new Integer(appauth));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 집단승인처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.ApprovalServlet";

            ApprovalBean bean = new ApprovalBean();
            int isOk = bean.ApprovalProcess(box);

            String v_msg = "";
            box.put("p_process", "listPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("s_upperclass", box.getString("p_uclass"));
            box.put("s_middleclass", box.getString("p_mclass"));
            box.put("s_lowerclass", box.getString("p_lclass"));
            box.put("s_subjcourse", box.getString("p_subjcourse"));
            box.put("s_subjseq", box.getString("p_subjseq"));
            box.put("s_company", box.getString("p_company"));

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "confirm.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "confirm.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performApproval()\r\n" + ex.getMessage());
        }
    }

    /**
     * 신청승인코드에 과정/코스 지정- 저장
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     * 
     *         public void performAssignSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
     *         try{ String v_url = "/servlet/controller.course.ApprovalServlet";
     * 
     *         ApprovalBean bean = new ApprovalBean(); int isOk = bean.SaveAssign(box);
     * 
     *         String v_msg = ""; box.put("p_process", "listPage");
     * 
     *         AlertManager alert = new AlertManager(); if(isOk > 0) { v_msg = "update.ok"; alert.alertOkMessage(out, v_msg, v_url , box, true, true); }else
     *         { v_msg = "update.fail"; alert.alertFailMessage(out, v_msg); } }catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, out); throw new
     *         Exception("performUpdate()\r\n" + ex.getMessage()); } }
     **/

    /**
     * Excel 다운
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ApprovalBean bean = new ApprovalBean();

            if (box.getString("p_action").equals("go")) {
                ArrayList<DataBox> list1 = bean.SelectApprovalScreenList(box);
                request.setAttribute("ApprovalList", list1);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_Approval_E.jsp");

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * OffLine Excel 다운
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOffLineExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ApprovalBean bean = new ApprovalBean();

            if (box.getString("p_action").equals("go")) {
                ArrayList<DataBox> list1 = bean.SelectOffLineApprovalScreenList(box);
                request.setAttribute("OffLineApprovalList", list1);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/propose/za_OffLineApproval_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
     * 신청승인 리스트 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOffLineListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_OffLineApproval_L.jsp";

            ApprovalBean bean = new ApprovalBean();

            if (box.getString("p_action").equals("go")) {
                ArrayList<DataBox> list1 = bean.SelectOffLineApprovalScreenList(box);
                request.setAttribute("OffLineApprovalList", list1);

                DataBox dbox = bean.getSubjInfomat(box);
                request.setAttribute("isOffLineManagerStatus", dbox);
            }

            int appauth = bean.getApprovalAuth(box);
            request.setAttribute("appauth", new Integer(appauth));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 오프라인 집단승인처리
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    @SuppressWarnings("unchecked")
    public void performOffLineApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.propose.ApprovalServlet";

            ApprovalBean bean = new ApprovalBean();
            int isOk = bean.OffLineApprovalProcess(box);

            String v_msg = "";
            box.put("p_process", "OffLinelistPage");
            box.put("s_grcode", box.getString("p_grcode"));
            box.put("s_gyear", box.getString("p_gyear"));
            box.put("s_grseq", box.getString("p_grseq"));
            box.put("s_upperclass", box.getString("p_uclass"));
            box.put("s_middleclass", box.getString("p_mclass"));
            box.put("s_lowerclass", box.getString("p_lclass"));
            box.put("s_subjcourse", box.getString("p_subjcourse"));
            box.put("s_subjseq", box.getString("p_subjseq"));
            box.put("s_company", box.getString("p_company"));

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "confirm.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "confirm.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performApproval()\r\n" + ex.getMessage());
        }
    }
}
