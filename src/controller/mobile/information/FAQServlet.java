package controller.mobile.information;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.information.FAQBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.information.FAQServlet")
public class FAQServlet  extends javax.servlet.http.HttpServlet implements Serializable {
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

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        String process = "";

        try {
            res.setContentType("text/html;charset=euc-kr");
            out = res.getWriter();
            box = RequestManager.getBox(req);
            process = box.getStringDefault("process", "faqList");

            System.out.println("process : " + process);

            if (process.equals("faqList")) {
                this.performSelectFAQList(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    @SuppressWarnings("unchecked")
    private void performSelectFAQList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception{
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/mobile/jsp/information/faq.jsp";

            FAQBean bean = new FAQBean();
            ArrayList faqList = bean.selectFAQList(box);

            req.setAttribute("faqList", faqList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectFAQList()\r\n" + ex.getMessage());
        }
        
    }

}
