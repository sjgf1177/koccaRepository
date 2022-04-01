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
 * 열린 강좌 찜한 강좌 목록 조회를 수행한다.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.openclass.OpenClassFavorServlet")
public class OpenClassFavorServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     * @param request
     * @param response
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * 
     * @param request
     * @param response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        String process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            process = box.getStringDefault("process", "openClassFavorList");

            if (process.equals("openClassFavorList")) {
                this.performOpenClassFavorList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void performOpenClassFavorList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        // TODO Auto-generated method stub
        try {
            request.setAttribute("requestbox", box);
            
            String viewType = box.getStringDefault("viewType", "");

            String dispatcherURL = "";
            if ( viewType.equals("") ) {
                dispatcherURL = "/mobile/jsp/openclass/openClassFavorList.jsp";
            } else if ( viewType.equals("ajax") ) {
                dispatcherURL = "/mobile/jsp/openclass/openClassFavorListAjaxResult.jsp";
            }
            
            OpenClassBean bean = new OpenClassBean();
            
            ArrayList openClassFavorList = bean.selectOpenClassFavorList(box);
            
            request.setAttribute("openClassFavorList", openClassFavorList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassFavorList.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassCategoryList()\r\n" + ex.getMessage());
        }

    }
}
