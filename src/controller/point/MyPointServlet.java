//**********************************************************
//  1. ��      ��:  ��������Ʈ �����ϴ� ����
//  2. ���α׷��� : MyPointServlet.java
//  3. ��      ��:  ��������Ʈ ���� ���α׷�
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: 2009. 11. 30
//  7. ��     ��1:
//**********************************************************
package controller.point;

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
import com.credu.point.MyPointBean;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.point.MyPointServlet")
public class MyPointServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        //        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        //        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (v_process.equals("pointPage")) { //  ����Ʈ ���
                this.performPointPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ��������Ʈ ���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPointPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/portal/study/gu_MyPoint_L.jsp";

            MyPointBean bean = new MyPointBean(); // ���� ����Ʈ

            int iGetPoint = bean.selectGetPoint(box); //���� ���� ����Ʈ
            int iUsePoint = bean.selectUsePoint(box); //���� ��� ����Ʈ
            int iWaitPoint = bean.selectWaitPoint(box); //���� ��� ����Ʈ

            box.put("p_getpoint", String.valueOf(iGetPoint));
            box.put("p_usepoint", String.valueOf(iUsePoint));
            box.put("p_waitpoint", String.valueOf(iWaitPoint));

            ArrayList list1 = bean.selectHavePointList(box); //��������Ʈ
            request.setAttribute("selectHavePointList", list1);

            ArrayList list2 = bean.selectStoldPointList(box); //��������Ʈ
            request.setAttribute("selectStoldPointList", list2);

            ArrayList list3 = bean.selectUsePointList(box); //�������Ʈ
            request.setAttribute("selectUsePointList", list3);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPointPage()\r\n" + ex.getMessage());
        }
    }

}
