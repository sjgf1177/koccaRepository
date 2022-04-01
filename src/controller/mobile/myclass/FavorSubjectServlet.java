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

/**
 * ���� ���ǽǿ��� ���� ���� ���� ����� �����Ѵ�.
 * 
 * @author saderaser
 * 
 */
@WebServlet("/servlet/controller.mobile.myclass.FavorSubjectServlet")
public class FavorSubjectServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            process = box.getStringDefault("process", "selectFavorSubjectList");

            if (process.equals("selectFavorSubjectList")) { // ���� ���� ��� ��ȸ
                this.performSelectFavorSubjectList(req, res, box, out);

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
    public void performSelectFavorSubjectList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            String reqType = box.getStringDefault("reqType", "normal");
            String dispatcherUrl = "";
            if (reqType.equals("normal")) {
                dispatcherUrl = "/mobile/jsp/myclass/myclassFavorSubjectList.jsp";
            } else if (reqType.equals("ajax")) {
                dispatcherUrl = "/mobile/jsp/myclass/myclassFavorSubjectListAjaxResult.jsp";
            }

            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> favorSubjectList = bean.selectFavorSubjectList(box);

            req.setAttribute("favorSubjectList", favorSubjectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

}