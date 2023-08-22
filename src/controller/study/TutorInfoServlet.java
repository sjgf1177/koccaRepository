//**********************************************************
//  1. ��      ��:  ���� ����Ұ� ����
//  2. ���α׷��� : TutorInfoServlet.java
//  3. ��      ��: ���� ����Ұ� ����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 12. 20
//  7. ��     ��1:
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
            if(v_process.equals("select")) {                  // ���� ����Ʈ
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			// ������ �޴� ���� ���� �߰�
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

