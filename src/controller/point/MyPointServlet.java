//**********************************************************
//  1. 제      목:  마이포인트 제어하는 서블릿
//  2. 프로그램명 : MyPointServlet.java
//  3. 개      요:  마이포인트 제어 프로그램
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009. 11. 30
//  7. 수     정1:
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

            if (v_process.equals("pointPage")) { //  포인트 목록
                this.performPointPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 나의포인트 목록
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

            MyPointBean bean = new MyPointBean(); // 나의 포인트

            int iGetPoint = bean.selectGetPoint(box); //나의 보유 포인트
            int iUsePoint = bean.selectUsePoint(box); //나의 사용 포인트
            int iWaitPoint = bean.selectWaitPoint(box); //나의 대기 포인트

            box.put("p_getpoint", String.valueOf(iGetPoint));
            box.put("p_usepoint", String.valueOf(iUsePoint));
            box.put("p_waitpoint", String.valueOf(iWaitPoint));

            ArrayList list1 = bean.selectHavePointList(box); //보유포인트
            request.setAttribute("selectHavePointList", list1);

            ArrayList list2 = bean.selectStoldPointList(box); //적립포인트
            request.setAttribute("selectStoldPointList", list2);

            ArrayList list3 = bean.selectUsePointList(box); //사용포인트
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
