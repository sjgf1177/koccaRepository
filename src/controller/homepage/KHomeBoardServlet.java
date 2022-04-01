//**********************************************************
//1. 제      목: 자료실을 제어하는 서블릿
//2. 프로그램명: KHomeBoardServlet.java
//3. 개      요: 자료실의 페이지을 제어한다
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성:
//7. 수      정:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.BoardBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.KHomeBoardServlet")
public class KHomeBoardServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 6303109068486389002L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;
        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canModify = false;
        boolean v_canDelete = false;
        boolean v_canReply = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            // String path = request.getServletPath();

            v_process = box.getStringDefault("p_process", "selectList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
            v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
            v_canModify = BulletinManager.isAuthority(box, box.getString("p_canModify"));
            v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
            v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
            v_canReply = BulletinManager.isAuthority(box, box.getString("p_canReply"));

            if (v_process.equals("insertPage")) { //  등록페이지로 이동할때
                if (v_canAppend)
                    this.performInsertPage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("insert")) { //  등록할때
                if (v_canAppend)
                    this.performInsert(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("updatePage")) { //  수정페이지로 이동할때
                if (v_canModify)
                    this.performUpdatePage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("update")) { //  수정하여 저장할때
                if (v_canModify)
                    this.performUpdate(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("replyPage")) { //  답변페이지로 이동할때
                if (v_canReply)
                    this.performReplyPage(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("reply")) { //  답변할때
                if (v_canReply)
                    this.performReply(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("delete")) { //  삭제할때
                if (v_canDelete)
                    this.performDelete(request, response, box, out);
                else
                    this.errorPage(box, out);
            } else if (v_process.equals("select")) { //  상세보기할때
                if (v_canRead)
                    this.performSelect(request, response, box, out);
            } else if (v_process.equals("selectList")) { //  조회할때
                if (v_canRead)
                    this.performSelectList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String tem_grcode = box.getSession("tem_grcode");
            BoardBean bean = new BoardBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "HD");
                box.put("p_grcode", tem_grcode);
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/

                tabseq = bean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            ArrayList list = bean.selectBoardList(box);
            request.setAttribute("selectHomePageBoardList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_pds_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_pds_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            BoardBean bean = new BoardBean();

            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectHomePageBoard", dbox);

            ArrayList<DataBox> list = bean.selectBoardList(box);
            request.setAttribute("selectHomePageBoardList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_pds_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_pds_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록페이지로 이동할때
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_pds_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_pds_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardBean bean = new BoardBean();

            int isOk = bean.insertBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KHomeBoardServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {

                //            /* ================ 마일리지 등록 시작  ============================*/
                //            String v_code   = "00000000000000000004";                      // 자료실등록 마일리지코드
                //            String s_userid = box.getSession("userid");
                //            int isOk3 = MileageManager.insertMileage(v_code, s_userid);    // 마일리지 작성
                //            /* ================ 마일리지 등록 시작  ============================*/

                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on KHomeBoardServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수정페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            BoardBean bean = new BoardBean();

            DataBox dbox = bean.selectBoard(box);

            request.setAttribute("selectHomePageBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/customer/ku_pds_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/customer/ku_pds_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수정하여 저장할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardBean bean = new BoardBean();

            int isOk = bean.updateBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KHomeBoardServlet";
            box.put("p_process", "selectList");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on KHomeBoardServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 답변페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            BoardBean bean = new BoardBean();
            DataBox dbox = bean.selectBoard(box);
            request.setAttribute("selectHomePageBoard", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_HomePageBoard_A.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_HomePageBoard_A.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReplyPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 답변할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardBean bean = new BoardBean();

            int isOk = bean.replyBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KHomeBoardServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "reply.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "reply.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on KHomeBoardServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReply()\r\n" + ex.getMessage());
        }
    }

    /**
     * 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            BoardBean bean = new BoardBean();

            int isOk = bean.deleteBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KHomeBoardServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on KHomeBoardServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void errorPage(RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "이 프로세스로 진행할 권한이 없습니다.");
            //  Log.sys.println();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
}
