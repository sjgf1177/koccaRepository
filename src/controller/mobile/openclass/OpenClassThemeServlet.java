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
 * ���� ���� �׸��� ����� ��ȸ�Ѵ�.
 * @author saderaser
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.openclass.OpenClassThemeServlet")
public class OpenClassThemeServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            process = box.getStringDefault("process", "openClassThemeList");
            
            if ( process.equals("openClassThemeList")) { // �׸� ��� ��ȸ
                this.performOpenClassThemeList(request, response, box, out);
                
            } else if ( process.equals("openClassThemeDetailList")) { // �׸��� �� ��� ��ȸ
                this.performOpenClassThemeDetailList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �������� �׸� ����� ��ȸ�Ѵ�. �׸� ���� ��ϰ� �Ǽ��� ��ȸ�Ѵ�.
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performOpenClassThemeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            request.setAttribute("requestbox", box);
            OpenClassBean bean = new OpenClassBean();
            ArrayList openClassThemeList = bean.selectOpenClassThemeList(box);
            
            request.setAttribute("openClassThemeList", openClassThemeList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassThemeList.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassThemeList.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassThemeList()\r\n" + ex.getMessage());
        }

    }

    /**
     * �������� �׸��� �� ����� ��ȸ�Ѵ�.
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performOpenClassThemeDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        // TODO Auto-generated method stub
        try {
            request.setAttribute("requestbox", box);
            OpenClassBean bean = new OpenClassBean();
            ArrayList openClassThemeDetailList = bean.selectOpenClassThemeDetailList(box);
            
            request.setAttribute("openClassThemeDetailList", openClassThemeDetailList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassThemeDetailList.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassThemeDetailList.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassThemeDetailList()\r\n" + ex.getMessage());
        }

    }

}