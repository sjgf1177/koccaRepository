//**********************************************************
//  1. ��      ��:  ���� �� ����
//  2. ���α׷��� : StudyExamServlet.java
//  3. ��      ��: ���� �� ����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: lyh
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

import com.credu.course.SubjGongAdminBean;
import com.credu.exam.ExamResultBean;
import com.credu.exam.ExamUserBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.StudyCountBean;

@WebServlet("/servlet/controller.study.StudyExamServlet")
public class StudyExamServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9204572078060531210L;

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
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "StudyExamListPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (box.getSession("userid").equals("")) {
                request.setAttribute("tUrl", request.getRequestURI());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
                return;
            }

            if (v_process.equals("StudyExamListPage")) { //���� ����, ������ ��  ����Ʈ
                this.performStudyExamListPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    @SuppressWarnings("unchecked")
    public void performStudyExamListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/study/zu_StudyExam_L.jsp";

            // ������ �޴� ���� ���� �߰�
            box.put("p_menu", "91");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            ExamUserBean bean = new ExamUserBean();
            ArrayList<DataBox> list1 = bean.SelectUserList(box);
            request.setAttribute("ExamUserList", list1);

            ArrayList<String> list2 = bean.SelectUserResultList(box);
            request.setAttribute("ExamUserResultList", list2);

            String data1 = bean.getProgressData(box); //����������
            request.setAttribute("mylesson", data1);

            ExamResultBean bean1 = new ExamResultBean();
            ArrayList<String> data2 = bean1.SelectUserRetryList(box); //������ ����Ƚ��
            request.setAttribute("ExamUserRetryList", data2);

            if (!box.getSession("s_subjseq").equals("0000")) { // ��Ÿ�׽�Ʈ�ϰ��

                /* ========== ����������, �ڱ������� ���� ========== */
                SubjGongAdminBean sbean = new SubjGongAdminBean();

                String promotion = sbean.getPromotion(box);
                request.setAttribute("promotion", promotion);
                String progress = sbean.getProgress(box);
                request.setAttribute("progress", progress);
                /* ========== ����������, �ڱ������� �� ========== */

            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performStudyExamListPage()\r\n" + ex.getMessage());
        }
    }

}
