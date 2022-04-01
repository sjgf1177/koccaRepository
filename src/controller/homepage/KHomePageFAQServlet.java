//**********************************************************
//1. ��      ��: FAQ�� �����ϴ� ����
//2. ���α׷��� : FaqServlet.java
//3. ��      ��: FAQ�� �������� �����Ѵ�(HOMEPAGE)
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2004.1.26
//7. ��      ��:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomePageFAQBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.KHomePageFAQServlet")
public class KHomePageFAQServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 3717293020596808284L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");

            // String v_test = box.getString("p_faqcategory");
            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (v_process.equals("selectList")) { // ����Ʈ
                this.performSelectList(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            HomePageFAQBean bean = new HomePageFAQBean();
            //if (box.getString("p_faqcategory").equals("")){
            box.put("p_grcode", "N000001");
            //}

            ArrayList<DataBox> list = bean.selectListFaq(box);

            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_homePageFaq_L.jsp");

            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_homePageFaq_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}
