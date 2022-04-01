// **********************************************************
// 1. 제 목: 인사DB 검색 서블릿
// 2. 프로그램명 : MemberAdminServlet.java
// 3. 개 요: 인사DB 검색 서블릿
// 4. 환 경: JDK 1.4
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2004. 12. 20
// 7. 수 정:
// **********************************************************

package controller.system;

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
import com.credu.system.AdminUtil;
import com.credu.system.MemberAdminBean;

@WebServlet("/servlet/controller.system.MemberAdminServlet")
public class MemberAdminServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -2260089149786362433L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (!AdminUtil.getInstance().checkRWRight("MemberAdminServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("memberInsertPage")) { // 인사DB 검색 페이지
                this.performMemberInsertPage(request, response, box, out);

            } else if (v_process.equals("insertMember")) { // 개인정보 상세보기
                this.performInsertMember(request, response, box, out);

            } else if (v_process.equals("previewFileToDB")) { // 엑셀 출력
                this.performInsertMember(request, response, box, out);

            } else if (v_process.equals("updateMemberPassword")) { // 엑셀 출력
                this.performUpdateMemberPassword(request, response, box, out);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 사용자 비밀번호 갱신
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     */
    private void performUpdateMemberPassword(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        MemberAdminBean bean = new MemberAdminBean();
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        try {
            request.setAttribute("requestbox", box);

            list = bean.updateMemberPassword(box);

            request.setAttribute("memberPasswordUpdateList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberUpdatePassword_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMemberPassword()\r\n" + ex.getMessage());
        }

    }

    /**
     * 인사DB 검색 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMemberInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberFilToDB.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberAdminPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 회원 등록
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertMember(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberFilToDB_P.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertMember()\r\n" + ex.getMessage());
        }
    }

    /**
     * MEMBER EXCEL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // MemberAdminBean bean = new MemberAdminBean();
            // ArrayList list = bean.searchMemberListExcel(box);

            // request.setAttribute("memberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberAdmin_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberExcel)\r\n" + ex.getMessage());
        }
    }

}
