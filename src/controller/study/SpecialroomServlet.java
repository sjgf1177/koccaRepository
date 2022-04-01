//*********************************************************
//  1. 제      목: USER SERVLET
//  2. 프로그램명: SpecialroomServlet.java
//  3. 개      요: 특강실 servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//  7. 수      정:
//**********************************************************
package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.SpecialroomBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.study.SpecialroomServlet")
public class SpecialroomServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2632617617608730456L;

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
            v_process = box.getStringDefault("p_process", "SpecialroomList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 로긴 check 루틴 VER 0.2
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("SpecialroomList")) { // 특강실 리스트 출력

                this.performSpecialroomList(request, response, box, out);
            } else if (v_process.equals("SpecialroomDetail")) { // 특강실 상세

                this.performSpecialroomDetail(request, response, box, out);
            } else if (v_process.equals("SpecialroomSearch")) { // 특강실 검색

                this.performSpecialroomSearch(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 특강실 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSpecialroomList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            SpecialroomBean bean = new SpecialroomBean();

            ArrayList<DataBox> list = bean.selectSpecialroom(box);
            String v_url = "/learn/user/game/study/gu_Specialroom_L.jsp";

            request.setAttribute("SpecialroomList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInerestList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 특강실 상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSpecialroomDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            SpecialroomBean bean = new SpecialroomBean();

            ArrayList<DataBox> list = bean.selectSpecialroomDetail(box); //해당 상세
            ArrayList<DataBox> preList = bean.selectSpecialroomDetailPre(box); //이전글
            ArrayList<DataBox> NextList = bean.selectSpecialroomDetailNext(box); //다음글

            String v_url = "/learn/user/game/study/gu_SpecialroomDetail_L.jsp";

            request.setAttribute("SpecialroomListDetail", list);
            request.setAttribute("SpecialroomListDetailPre", preList);
            request.setAttribute("SpecialroomListDetailNext", NextList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSpecialroomDetail()\r\n" + ex.getMessage());
        }
    }

    /**
     * 특강실 검색
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSpecialroomSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            SpecialroomBean bean = new SpecialroomBean();

            ArrayList<DataBox> list = bean.selectSpecialroomSearch(box); // 검색결과

            String v_url = "/learn/user/game/study/gu_Specialroom_L.jsp";

            request.setAttribute("SpecialroomList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSpecialroomSearch()\r\n" + ex.getMessage());
        }
    }

}