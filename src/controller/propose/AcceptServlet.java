//**********************************************************
//1. ��      ��:
//2. ���α׷���: AcceptServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-07-23
//7. ��      ��:
//
//**********************************************************

package controller.propose;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.GrseqBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.propose.AcceptServlet")
public class AcceptServlet extends javax.servlet.http.HttpServlet {
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;

        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","individualAcceptPage");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("AcceptServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("individualAcceptPage")) {                 //in case of ���κ� �����԰�ȭ��
                this.performIndividualAcceptPage(request, response, box, out);
            } else if (v_process.equals("individualAccept")) {              //in case of ���κ� �����԰�
                this.performIndividualAccept(request, response, box, out);
            } else if (v_process.equals("proposeFileToDB")) {               //in case of �԰�FileToDB
                this.performProposeFileToDB(request, response, box, out);
            } else if (v_process.equals("insertFileToDB")) {                //in case of �԰�FileToDB �Է�
                this.performInsertFileToDB(request, response, box, out);
            } else if (v_process.equals("previewFileToDB")) {               //in case of �԰�FileToDB �Է�
                this.performPreviewFileToDB(request, response, box, out);
            } else if (v_process.equals("proposeListExcel")) {              //in case of ����/�����ڵ� �ٿ�ε�
                this.performProposeListExcel(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �����԰�ó�� ȭ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performIndividualAcceptPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_IndividualAccept.jsp";
            ProposeBean bean = new ProposeBean();
            ArrayList list  = bean.SelectAcceptTargetMember(box);
            request.setAttribute("IndividualAcceptList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performIndividualAcceptPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �����԰�ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performIndividualAccept(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.propose.AcceptServlet";

            ProposeBean bean = new ProposeBean();
            int isOk = bean.Accept(box);

            String v_msg = "";
            box.put("p_process", "individualAcceptPage");

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
    �ϰ��԰�ó�� ȭ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProposeFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_ProposeFilToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    �ϰ��԰�ó�� ȭ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_ProposeFilToDB_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    �ϰ��԰�ó�� ȭ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_ProposeFilToDB_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }


    /**
    ����/�����ڵ� �ٿ�ε�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performProposeListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/propose/za_ProposeFilToDB_E.jsp";

            GrseqBean bean = new GrseqBean();
            ArrayList list = bean.getSubjCodeList(box);
            request.setAttribute("SubjCodeList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performProposeListExcel()\r\n" + ex.getMessage());
        }
    }

}
