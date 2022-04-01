//**********************************************************
//1. ��      ��: Ȩ������ ���� ���ȸ��������  �����ϴ� ����
//2. ���α׷��� : HomePageSuperiortyMemberServlet
//3. ��      ��: Ȩ������ ���� ���ȸ���� �����Ѵ�.
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2005.12.13 �ϰ���
//7. ��      ��: 2005.12.13 �ϰ���
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomePageSuperiorityMemberBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.HomePageSuperiorityMemberServlet")
public class HomePageSuperiorityMemberServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -3951736996669027964L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getStringDefault("p_process", "selectList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("select")) { //  �󼼺����Ҷ�
                this.performSelect(request, response, box, out);
            } else if (v_process.equals("selectList")) { //  ��ȸ�Ҷ�
                this.performSelectList1(request, response, box, out);
            } else if (v_process.equals("main")) { //  Main �ѷ��ֱ�
                this.performMainListPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMainListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/game/homepage/BestMember.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/homepage/BestMember.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            HomePageSuperiorityMemberBean bean1 = new HomePageSuperiorityMemberBean();

            ArrayList<DataBox> list = bean1.selectBoardList(box);
            request.setAttribute("selectHomePageSuperiorityMemberList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/service/gu_HomePageService_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_HomePageService_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            HomePageSuperiorityMemberBean bean = new HomePageSuperiorityMemberBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectHomePageSuperiorityMember", dbox);

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("selectHomePageSuperiortyMemberList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/service/gu_HomePageService_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_HomePageService_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����������
     * 
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void errorPage(RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            //  Log.sys.println();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}
