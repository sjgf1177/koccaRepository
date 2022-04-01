//*********************************************************
//1. 제      목: USER SERVLET
//2. 프로그램명: KRecommendServlet.java
//3. 개      요: 맞춤추천강좌 servlet
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: lyh
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
import com.credu.study.KRecommandBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.study.KRecommendServlet")
public class KRecommendServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 898329821802527780L;

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
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 로긴 check 루틴 VER 0.2
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("RecommandList")) { // 맞춤추천과정 리스트 출력
                this.performInerestList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 맞춤추천과정 조회
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInerestList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KRecommandBean bean = new KRecommandBean();
            String v_url = "/learn/user/kocca/study/ku_RecommandList_L.jsp";

            //개인정보 조회
            ArrayList<DataBox> list = bean.selectRecommandList(box);
            request.setAttribute("UserInfoList", list);

            //검색시 조건 select목록에 들어갈 내용
            ArrayList<ArrayList<DataBox>> selectboxList = bean.selectRecommandSelectbox(box);
            request.setAttribute("RecommandSelectbox", selectboxList);

            //추천과정 조회
            ArrayList<DataBox> recommandSubjList = bean.selectRecommandSubj(box);
            request.setAttribute("RecommandSubj", recommandSubjList);

            //검색추천과정
            ArrayList<DataBox> recommandSearchList = bean.selectRecommandSearch(box);
            request.setAttribute("RecommandSearch", recommandSearchList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancel()\r\n" + ex.getMessage());
        }
    }

}