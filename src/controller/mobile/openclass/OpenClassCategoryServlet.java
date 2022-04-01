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
 * 열린 강좌 분류별 목록 조회
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.openclass.OpenClassCategoryServlet")
public class OpenClassCategoryServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            process = box.getStringDefault("process", "openClassCategoryList");

            if (process.equals("openClassCategoryList")) { // 기본 화면 ( 조회 없음 )
                this.performOpenClassCategoryList(request, response, box, out);

            } else if (process.equals("openClassCategoryDetailList")) {
                this.performOpenClassCategoryDetailList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    @SuppressWarnings("unchecked")
    private void performOpenClassCategoryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        // TODO Auto-generated method stub
        try {
            request.setAttribute("requestbox", box);

            String lectureCls = box.getStringDefault("lectureCls", "");
            String dispatcherURL = "/mobile/jsp/openclass/openClassCategoryList.jsp";

            if (!lectureCls.equals("")) {
                OpenClassBean bean = new OpenClassBean();
                ArrayList openClassCategoryList = bean.selectOpenClassCategoryList(box);
                request.setAttribute("openClassCategoryList", openClassCategoryList);
                dispatcherURL = "/mobile/jsp/openclass/openClassCategoryAjaxResult.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassCategoryAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassCategoryList()\r\n" + ex.getMessage());
        }

    }
    
    @SuppressWarnings("unchecked")
    private void performOpenClassCategoryDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        // TODO Auto-generated method stub
        try {
            request.setAttribute("requestbox", box);

            String dispatcherURL = "/mobile/jsp/openclass/openClassCategoryDetailList.jsp";

            OpenClassBean bean = new OpenClassBean();
            ArrayList openClassCategoryDetailList = bean.selectOpenClassCategoryList(box);
            request.setAttribute("openClassCategoryDetailList", openClassCategoryDetailList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /mobile/jsp/openclass/openClassCategoryAjaxResult.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenClassCategoryList()\r\n" + ex.getMessage());
        }

    }

}
