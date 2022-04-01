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
 * 열린 강좌 인기별 목록 조회
 * @author saderaser
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.openclass.OpenClassPopularServlet")
public class OpenClassPopularServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            process = box.getStringDefault("process", "openClassPopularList");
            
            if ( process.equals("openClassPopularList")) {
                this.performOpenClassPopularList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    @SuppressWarnings("unchecked")
    private void performOpenClassPopularList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        // TODO Auto-generated method stub
        try {
            request.setAttribute("requestbox", box);
            OpenClassBean bean = new OpenClassBean();
            ArrayList openClassPopularList = bean.selectOpenClassPopuplarList(box);
            
            request.setAttribute("openClassPopularList", openClassPopularList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/mobile/jsp/openclass/openClassPopularList.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassPopularList.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassPopularList()\r\n" + ex.getMessage());
        }

    }

}
