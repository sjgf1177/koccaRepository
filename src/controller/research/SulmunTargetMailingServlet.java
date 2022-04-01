//**********************************************************
//1. ��      ��: a-mail ���� ���� �������� ��ũ�� �н��ڰ� ������.
//2. ���α׷���: SulmunTargetMailingServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: 2005.10.
//7. ��      ��:
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
import com.credu.research.SulmunTargetPaperBean;
import com.credu.research.SulmunTargetUserBean;

/**
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
@WebServlet("/servlet/controller.research.SulmunTargetMailingServlet")
public class SulmunTargetMailingServlet extends HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5164439395141645445L;

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
            v_process = box.getStringDefault("p_process", "DamunResultPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (v_process.equals("SulmunMailPage")) { //����ڼ��� ����� �������� (����)
                this.performSulmunMailPage(request, response, box, out);
            } else if (v_process.equals("SulmunUserResultInsert2")) {
                this.performSulmunUserResultInsert2(request, response, box, out);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����ڼ��� ����ڸ���Ʈ ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSulmunMailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/research/zu_SulmunTargetMailPaper_I.jsp";

            SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
            ArrayList<ArrayList<DataBox>> list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");

            box.put("p_sulpapernm", box.getString("p_sulpapernm"));

            DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1);
            request.setAttribute("SulmunRequestData", box); // ���� �⺻ ����Ÿ

            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����ڼ��� ���� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSulmunUserResultInsert2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/research/zu_SulmunTargetUserPaperClose.jsp";

            SulmunTargetUserBean bean = new SulmunTargetUserBean();
            //            int isOk = bean.InsertSulmunUserResult2(box);
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            //box.put("p_process", "SulmunMailPage");
            //box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if (isOk == 2) {
                v_msg = "������ ������ �ּż� �����մϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 1) {
                v_msg = "���� �Ⱓ �����Դϴ�.";
                alert.alertFailMessage(out, v_msg);
            } else if (isOk == 3) {
                v_msg = "���� �Ⱓ�� �Ϸ�Ǿ����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "�̹� �ش� ������ �����ϼ̽��ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
        }
    }
}
