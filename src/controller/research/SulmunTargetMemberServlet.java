//**********************************************************
//1. 제      목: 설문 대상자관리
//2. 프로그램명: SulmunTargetMemberServlet .java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2004-11-05
//7. 수      정:
//
//**********************************************************

package controller.research;

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
import com.credu.research.SulmunTargetMemberBean;
import com.credu.research.SulmunTargetPaperBean;
import com.credu.system.AdminUtil;

/**
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.research.SulmunTargetMemberServlet")
public class SulmunTargetMemberServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3973782005909494898L;

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
            v_process = box.getStringDefault("p_process", "SulmunMemberListPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("SulmunTargetMemberServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("SulmunMemberListPage")) { //설문 대상자 리스트
                this.performSulmunMemberListPage(request, response, box, out);
            } else if (v_process.equals("SulmunMemberInsertPage")) { // 설문 대상자 개인별 등록 페이지로 이동 (검색전)
                this.performSulmunMemberInsertPage(request, response, box, out);
            } else if (v_process.equals("MemberTargetListPage")) { // 설문 대상자 개인별 등록 페이지로 이동 (검색후)
                this.performtMemberTargetListPage(request, response, box, out);
            } else if (v_process.equals("SulmunMemberInsert")) { // 설문 대상자 개인별 등록할때
                this.performSulmunMemberInsert(request, response, box, out);
            } else if (v_process.equals("SulmunMemberDelete")) { // 설문 대상자 삭제할때
                this.performSulmunMemberDelete(request, response, box, out);
            } else if (v_process.equals("SulmunMemberFileToDB")) { // 설문 대상자 FileToDB
                this.performSulmunMemberFileToDB(request, response, box, out);
            } else if (v_process.equals("insertFileToDB")) { // 설문 대상자 FileToDB 입력
                this.performInsertFileToDB(request, response, box, out);
            } else if (v_process.equals("previewFileToDB")) { // 설문 대상자 FileToDB 미리보기
                this.performPreviewFileToDB(request, response, box, out);
            } else if (v_process.equals("SulmunMail")) { // 메일정보입력 처리
                this.performSulmunMailInsert(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 설문 대상자 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunMemberListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunTargetMember_L2.jsp";

            SulmunTargetMemberBean bean = new SulmunTargetMemberBean();
            ArrayList<DataBox> list = bean.selectSulmunMemberList(box);
            request.setAttribute("SulmunMemberList", list);

            SulmunTargetPaperBean bean1 = new SulmunTargetPaperBean();
            DataBox dbox1 = bean1.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMemberListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 대상자 개인별 등록 페이지로 이동 (검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunMemberInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunTargetMember_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMemberInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 대상자 개인별 등록 페이지로 이동 (검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performtMemberTargetListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunTargetMember_I.jsp";

            SulmunTargetMemberBean bean = new SulmunTargetMemberBean();

            ArrayList<DataBox> list = bean.selectMemberTargetList(box);
            request.setAttribute("MemberTargetList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performtMemberTargetListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 대상자 개인별 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunMemberInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.research.SulmunTargetMemberServlet";

            SulmunTargetMemberBean bean = new SulmunTargetMemberBean();
            int isOk = bean.insertSulmunMember(box);

            String v_msg = "";
            box.put("p_process", "SulmunMemberListPage");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, true, true);
                //  public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed);
                //  isOpenWin  openwindow 여부,
                //  isClosed  openwindow 가 닫혀야하는지 여부
            } else if (isOk == -1) {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            } else if (isOk == 0) {
                v_msg = "이미 대상자로 등록되어 있습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMemberInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 대상자 개인별 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunMemberDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.research.SulmunTargetMemberServlet";

            SulmunTargetMemberBean bean = new SulmunTargetMemberBean();
            int isOk = bean.deleteSulmunMember(box);

            String v_msg = "";
            box.put("p_process", "SulmunMemberListPage");

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
            throw new Exception("performSulmunMasterDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 대상자 FileToDB
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunMemberFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunTargetMemberFileToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMemberFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 대상자 FileToDB 입력
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunTargetMemberFileToDB_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * 설문 대상자 FileToDB 미리보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunTargetMemberFileToDB_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메일정보 입력
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunMailInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/servlet/controller.research.SulmunTargetMemberServlet";

            SulmunTargetMemberBean bean = new SulmunTargetMemberBean();
            int isOk = bean.insertSulmunMailing(box);

            String v_msg = "";
            box.put("p_process", "SulmunMemberListPage");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
                //  public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed);
                //  isOpenWin  openwindow 여부,
                //  isClosed  openwindow 가 닫혀야하는지 여부
            } else {
                v_msg = "설문메일발송 대기중입니다.";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMemberInsert()\r\n" + ex.getMessage());
        }
    }
}