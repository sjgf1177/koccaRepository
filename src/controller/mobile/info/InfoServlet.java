/**
 * 프로젝트명 : kocca_java
 * 패키지명 : controller.mobile.info
 * 파일명 : InfoServlet.java
 * 작성날짜 : 2011. 10. 15.
 * 처리업무 :
 * 수정내용 :
 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.   
 */

package controller.mobile.info;

import java.io.PrintWriter;
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
import com.credu.mobile.info.GoldClassBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.info.InfoServlet")
public class InfoServlet extends javax.servlet.http.HttpServlet {
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getString("p_process"); // process

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if ("".equals(v_process) || "goldClassListPage".equals(v_process)) {
                this.performGoldClassListPage(request, response, box, out); //골드클레스 목록 페이지
            } else if ("goldClassViewPage".equals(v_process)) {
                this.performGoldClassViewPage(request, response, box, out); //골드클레스 상세 페이지
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 이달의 골드클레스 목록 페이지
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performGoldClassListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            GoldClassBean bean = new GoldClassBean();

            request.setAttribute("_LIST_", bean.getGoldClassList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/info/zu_goldClass_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performGoldClassListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 골드상세페이지 이동
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performGoldClassViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            GoldClassBean bean = new GoldClassBean();

            request.setAttribute("_VIEW_", bean.getGoldClassData(box));

            ArrayList<DataBox> list1 = bean.getGoldClassMobileList(box);

            request.setAttribute("_MOBILELIST_", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/info/zu_goldClass_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performGoldClassViewPage()\r\n" + ex.getMessage());
        }
    }
}
