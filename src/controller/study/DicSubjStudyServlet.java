//**********************************************************
//  1. ��      ��: ������ �����ϴ� ����
//  2. ���α׷��� : DicSubjStudyServlet.java
//  3. ��      ��: ������ �������� �����Ѵ� (�н�â)
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 29
//  7. ��      ��:
//**********************************************************

package controller.study;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.DicSubjBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.StudyCountBean;
@WebServlet("/servlet/controller.study.DicSubjStudyServlet")
public class DicSubjStudyServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 5916272555865783251L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        //      MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        //      int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (box.getSession("userid").equals("")) {
                request.setAttribute("tUrl", request.getRequestURI());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("selectList")) { // ����Ʈ ��ȸ��
                this.performSelectList(request, response, box, out);
            } else if (v_process.equals("selectListPre")) { // ����Ʈ ��ȸ��
                this.performSelectListPre(request, response, box, out);
            } else if (v_process.equals("selectListTotal")) { // ����Ʈ ��ȸ��
                this.performSelectTotalList(request, response, box, out);
            } else if (v_process.equals("selectTotalListPre")) { // ���տ����� ����Ʈ ��ȸ��ȭ��
                this.performSelectTotalListPre(request, response, box, out);
            } else if (v_process.equals("wordPopUp")) { // ���տ����� ����Ʈ ��ȸ��ȭ��
                this.performWordPopUP(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ������ ����Ʈ(�˻���) 2005.08
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            DicSubjBean bean = new DicSubjBean();

            ArrayList<DataBox> list = bean.selectListDicSubjStudy(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_DicSubjStudy_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/study/zu_DicSubjStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ����Ʈ(�˻���) 2005.08
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ������ �޴� ���� ���� �߰�
            box.put("p_menu", "35");
            //box.put("p_flagpreview","Y");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_DicSubjStudy_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/study/zu_DicSubjStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectTotalList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            DicSubjBean bean = new DicSubjBean();

            ArrayList<DataBox> list = bean.selectListDicTotal(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            //RequestDispatcher rd = sc.getRequestDispatcher("/portal/user/gold/edu_word.jsp");
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_DicSubjStudy_L.jsp");
            rd.forward(request, response);

            //Log.info.println(this, box, "Dispatch to /learn/user/study/zu_DicSubjStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectTotalListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/portal/user/gold/edu_word.jsp");
            rd.forward(request, response);

            //Log.info.println(this, box, "Dispatch to /learn/user/study/zu_DicSubjStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performWordPopUP(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            DicSubjBean bean = new DicSubjBean();
            DataBox dbox = bean.selectWordContent(box);

            request.setAttribute("seldbox", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/portal/user/gold/edu_words_pop.jsp");
            rd.forward(request, response);

            //Log.info.println(this, box, "Dispatch to /learn/user/study/zu_DicSubjStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

}
