package controller.mobile.openclass;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.openclass.OpenClassBean;

/**
 * �������� �� ȭ�� ��ȸ �� �� ȭ�鿡���� ����� ����Ѵ�.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.openclass.OpenClassViewServlet")
public class OpenClassViewServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * 
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        String process = "";

        try {

            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            process = box.getStringDefault("process", "openClassViewDetail");

            if (process.equals("openClassViewDetail")) { // �׸� ��� ��ȸ
                this.performOpenClassViewDetail(request, response, box, out);

            } else if (process.equals("updateViewCount")) {
                this.performUpdateViewCount(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �������� �� ������ ��ȸ�Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    @SuppressWarnings("unchecked")
    public void performOpenClassViewDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            OpenClassBean bean = new OpenClassBean();
            ArrayList openClassDetail = bean.selectOpenClassDetail(box);
            ArrayList relatedLecutreList = bean.selectRelatedLecutreList(box);
            ArrayList reviewList = bean.selectOpenClassReviewList(box);

            request.setAttribute("openClassDetail", openClassDetail);
            request.setAttribute("relatedLecutreList", relatedLecutreList);
            request.setAttribute("reviewList", reviewList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassViewDetail.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassViewDetail.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassViewDetail()\r\n" + ex.getMessage());
        }

    }

    /**
     * �������� ��ȸ�� ������ �����Ѵ�. ����� �������¸� ��û�� ä���� �� �� ������ �̸� Ȯ���ϱ� ���� �۾��� �ʿ��ϴ�. �ð� ����
     * �����Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    public void performUpdateViewCount(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            OpenClassBean bean = new OpenClassBean();

            bean.updateOpenClassViewCount(box);

            Log.info.println(this, box, "Update OpenClass View Count : " + box.getInt("seq"));

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassViewDetail()\r\n" + ex.getMessage());
        }

    }

}
