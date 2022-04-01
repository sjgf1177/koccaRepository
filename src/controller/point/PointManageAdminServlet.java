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

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.point.PointManageBean;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.point.PointManageAdminServlet")
public class PointManageAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (v_process.equals("selectList")) { //  포인트 목록
                this.performPointPage(request, response, box, out);
            } else if (v_process.equals("insertPoint")) { //  포인트 목록
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("insertPointPage")) { //  포인트 목록
                this.performInsertPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 포인트 등록 페이지(팝업)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/point/za_pointManage_I.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/point/za_pointManage_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 관리자 포인트 적립
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            PointManageBean bean = new PointManageBean();

            int isOk = bean.insert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NoticeAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
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
            String v_return_url = "";

            PointManageBean bean = new PointManageBean(); // 나의 포인트

            int iGetPoint = bean.selectGetPoint(box); //나의 보유 포인트
            int iUsePoint = bean.selectUsePoint(box); //나의 사용 포인트
            int iWaitPoint = bean.selectWaitPoint(box); //나의 대기 포인트

            box.put("p_getpoint", String.valueOf(iGetPoint));
            box.put("p_usepoint", String.valueOf(iUsePoint));
            box.put("p_waitpoint", String.valueOf(iWaitPoint));

            int v_tabseq = box.getInt("p_tabseq");

            ArrayList selectList;

            if (v_tabseq == 2) {
                selectList = bean.selectStoldPointList(box); //적립포인트
                request.setAttribute("selectList", selectList);
                v_return_url = "/learn/admin/point/za_pointManage_L2.jsp";
            } else if (v_tabseq == 3) {
                selectList = bean.selectUsePointList(box); //사용포인트
                request.setAttribute("selectList", selectList);
                v_return_url = "/learn/admin/point/za_pointManage_L3.jsp";
            } else {
                selectList = bean.selectHavePointList(box); //보유포인트
                request.setAttribute("selectList", selectList);
                v_return_url = "/learn/admin/point/za_pointManage_L1.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPointPage()\r\n" + ex.getMessage());
        }
    }

}
