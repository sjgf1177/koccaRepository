//**********************************************************
//1. 제      목: 대상선정 마법사 Operation Servlet
//2. 프로그램명: ProposeWizardServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: 2004-11-10
//7. 수      정:
//**********************************************************

package controller.propose;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.MasterWizardBean;
import com.credu.propose.ProposeWizardBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.propose.ProposeWizardServlet")
public class ProposeWizardServlet extends javax.servlet.http.HttpServlet {
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
        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","listPage");
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ProposeWizardServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("listPage")) {                             //in case of 교육대상자 조회
                this.performListPage(request, response, box, out);
            }else if (v_process.equals("insert")) {                         //in case of 선정대상자 입과처리
                this.performInsert(request, response, box, out);
            }else if (v_process.equals("OpenSubjseq")) {                    //in case of 대상자 검색페이지 팝업
                this.performOpenSubjseq(request, response, box, out);
            }else if (v_process.equals("OpenTagetSelect")) {                    //in case of 대상자 검색페이지 팝업
                this.performOpenSubjseq(request, response, box, out);
            }else if (v_process.equals("memSearch")) {                       //in case of 교육대상 마법사 검색 페이지
                this.performMemSearch(request, response, box, out);
            }else if (v_process.equals("delete")) {                           //in case of 교육대상자 삭제
                this.performDelete(request, response, box, out);
            }else if (v_process.equals("UpFile")) {                           //in case of 교육대상자 파일업로
                this.performEduUpFile(request, response, box, out);
            } else if (v_process.equals("EduTargetinsertFileToDB")) {       //in case of 대상자 FileToDB 입력
                this.performEduTargetInsertFileToDB(request, response, box, out);
            }else if (v_process.equals("EduTargetpreviewFileToDB")) {      //in case of 대상자 FileToDB 입력
                this.performEduTargetPreviewFileToDB(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    교육대상 선정 마법사 검색 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_ProposeWizard_L.jsp";

            if (box.getString("p_action").equals("go")) {
                ProposeWizardBean bean = new ProposeWizardBean();
                ArrayList list  = bean.SelectedAcceptTargetMember(box);
                request.setAttribute("selectList", list);

                MasterWizardBean bean1 = new MasterWizardBean();
                ArrayList list1  = bean1.SelectMasterInfo(box);
                request.setAttribute("selectMasterInfo", list1);
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    선정대상자 입과처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.propose.ProposeWizardServlet";

            ProposeWizardBean bean = new ProposeWizardBean();
            int isOk = bean.AcceptTargetMember(box);

            String v_msg = "";
            box.put("p_process", "memSearch");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    OPEN ADD EDUTARGET SEARCH LIST
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performOpenSubjseq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
            String v_return_url = "/learn/admin/propose/za_OpenSubjseq_L.jsp";

            if(box.getString("p_action").equals("go")){
                ProposeWizardBean bean = new ProposeWizardBean();
                ArrayList list  = bean.SelectAcceptTargetMember(box);
                request.setAttribute("selectList", list);
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
            Log.info.println(this, box, "Dispatch to /learn/admin/propose/za_OpenSubjseq_L.jsp");
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenSubjseq()\r\n" + ex.getMessage());
        }
    }

    public void performMemSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_OpenSubjseq_L.jsp";

            if (box.getString("p_action").equals("go")) {
                ProposeWizardBean bean = new ProposeWizardBean();
                ArrayList list  = bean.SelectAcceptTargetMember(box);
                request.setAttribute("selectList", list);
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/servlet/controller.propose.ProposeWizardServlet";

            ProposeWizardBean bean = new ProposeWizardBean();

            int isOk = bean.SelectedDeleteTargetMember(box);

            ArrayList list  = bean.SelectedAcceptTargetMember(box);
            request.setAttribute("selectList", list);

            String v_msg = "";
            box.put("p_process","listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_return_url , box);
            }else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    public void performEduUpFile(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_OpenUpEduTarget_L.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    교육대상선정 파일업로드
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performEduTargetInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_OpenUpEduTarget_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    교육대상선정 미리보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performEduTargetPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_OpenUpEduTarget_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

}