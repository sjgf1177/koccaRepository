package controller.temp;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.temp.TemporaryBean;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.temp.TempServlet")
public class TempServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            this.performTemporaryList(request, response, box, out);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performTemporaryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            TemporaryBean bean = new TemporaryBean();

            ArrayList list = bean.selectTemporaryList(box);
            DataBox dbox = null;
            StringBuffer scriptSrc = new StringBuffer();

            scriptSrc.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>\n");
            scriptSrc.append("<ROOT>\n");

            for (int i = 0; i < list.size(); i++) {
                dbox = (DataBox) list.get(i);
                Enumeration e = dbox.keys();

                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    String value = dbox.getString(key);

                    scriptSrc.append("<").append(key).append(">");
                    scriptSrc.append(value);
                    scriptSrc.append("</").append(key).append(">\n");
                }
            }

            scriptSrc.append("</ROOT>\n");
            // System.out.println(scriptSrc.toString());
            out.write(scriptSrc.toString());
            out.flush();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
}
