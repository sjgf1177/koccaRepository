//*********************************************************
//  1. 제      목: USER SERVLET
//  2. 프로그램명: KOpenOfflineServlet.java
//  3. 개      요: 오프라인강좌 servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//  7. 수      정:
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.KOpenOfflineBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.KOpenOfflineServlet")
public class KOpenOfflineServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5953351271250662144L;

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
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "OpenOfflineList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("OpenOfflineList")) { // 열린마당 오프라인강좌 리스트

                this.performOpenOfflineList(request, response, box, out);
            } else if (v_process.equals("OpenOfflineDetail")) { // 열린마당 오프라인강좌 상세보기

                this.performOpenOfflineDetail(request, response, box, out);
            } else if (v_process.equals("OpenOfflineSearch")) { // 열린마당 오프라인강좌 검색

                this.performOpenOfflineSearch(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 오프라인강좌 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOpenOfflineList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KOpenOfflineBean bean = new KOpenOfflineBean();

            ArrayList<DataBox> list = bean.selectOpenOffline(box);
            String v_url = "/learn/user/kocca/open/ku_OpenOffline_L.jsp";

            request.setAttribute("OpenOfflineList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOfflineList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 오프라인강좌 상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOpenOfflineDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KOpenOfflineBean bean = new KOpenOfflineBean();

            ArrayList<DataBox> list = bean.selectOpenOfflineDetail(box); //해당 상세
            ArrayList<DataBox> preList = bean.selectOpenOfflineDetailPre(box); //이전글
            ArrayList<DataBox> NextList = bean.selectOpenOfflineDetailNext(box); //다음글

            String v_url = "/learn/user/kocca/open/ku_OpenOfflineDetail_L.jsp";

            request.setAttribute("OpenOfflineListDetail", list);
            request.setAttribute("OpenOfflineListDetailPre", preList);
            request.setAttribute("OpenOfflineListDetailNext", NextList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenOfflineDetail()\r\n" + ex.getMessage());
        }
    }

    /**
     * 오프라인강좌 검색
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performOpenOfflineSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KOpenOfflineBean bean = new KOpenOfflineBean();

            ArrayList<DataBox> list = bean.selectOpenOfflineSearch(box);

            String v_url = "/learn/user/kocca/open/ku_OpenOffline_L.jsp";

            request.setAttribute("OpenOfflineList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenOfflineSearch()\r\n" + ex.getMessage());
        }
    }

}