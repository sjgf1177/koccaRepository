package controller.mobile.myclass;

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
import com.credu.mobile.myclass.MyClassBean;
@WebServlet("/servlet/controller.mobile.myclass.NextSubjectServlet")
public class NextSubjectServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(req, res);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        String process = "";

        try {
            res.setContentType("text/html;charset=euc-kr");
            out = res.getWriter();
            box = RequestManager.getBox(req);
            process = box.getStringDefault("process", "selectNextSubjectList");

            if (process.equals("selectNextSubjectList")) { // ���� ���� ��� ��ȸ
                this.performSelectNextSubjectList(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ���԰������� ���ſ� ������û�� �Ͽ���, �����Ⱓ�� ����� ���� ����� ��� ��ȸ�Ѵ�. ����/�̼��ῡ ������� ��� ��ȸ�Ѵ�.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectNextSubjectList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            String reqType = box.getStringDefault("reqType", "normal");
            String dispatcherUrl = "";
            if ( reqType.equals("normal")) {
                dispatcherUrl = "/mobile/jsp/myclass/myclassNextSubjectList.jsp";
            } else {
                dispatcherUrl = "/mobile/jsp/myclass/myclassNextSubjectListAjaxResult.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> nextSubjectList = bean.selectNextSubjectList(box);

            req.setAttribute("nextSubjectList", nextSubjectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectNextSubjectList()\r\n" + ex.getMessage());
        }
    }

}