//**********************************************************
//  1. 제      목:  과정 강사소개 서블릿
//  2. 프로그램명 : TutorInfoServlet.java
//  3. 개      요: 과정 강사소개 서블릿
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 20
//  7. 수     정1:
//**********************************************************
package controller.study;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.study.*;
import com.credu.library.*;
import com.credu.system.*;

@WebServlet("/servlet/controller.study.TutorInfoServlet")
public class TutorInfoServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = "select";

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            if(v_process.equals("select")) {                  // 권한 리스트
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    관리자 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

			// 과정별 메뉴 접속 정보 추가
			box.put("p_menu","94");
			StudyCountBean scBean = new StudyCountBean();
			scBean.writeLog(box);

            TutorInfoBean bean = new TutorInfoBean();
            ArrayList List = bean.selectView(box);

            request.setAttribute("select", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjTutorInfo_L.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/user/study/zu_SubjTutorInfo_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }


}

