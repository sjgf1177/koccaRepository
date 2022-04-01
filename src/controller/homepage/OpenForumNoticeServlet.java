//**********************************************************
//1. 제      목: 포럼 공지사항 제어 서블릿
//2. 프로그램명: OpenForumNoticeServlet.java
//3. 개      요: 포럼 공지사항 제어 서블릿
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 2005.12.18 이나연
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

import com.credu.homepage.OpenForumNoticeBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;

@WebServlet("/servlet/controller.homepage.OpenForumNoticeServlet")
public class OpenForumNoticeServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 3659113822667904702L;

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

        try {
            response.setContentType("text/html;charset=euc-kr");
            // String path = request.getServletPath();

            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "List");

            if (ErrorManager.isErrorMessageView())
                box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            //if (!AdminUtil.getInstance().checkLogin(out, box)) {
            // return;
            //}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if (v_process.equals("List")) { //  리스트 페이지로 이동할때
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("selectView")) { //  보기
                this.performViewPage(request, response, box, out);
            } else if (v_process.equals("mainList")) { // 메인 뿌려주기
                this.performMainListPage(request, response, box, out);
            } else if (v_process.equals("moreNotice")) { // more 보기
                this.performMoreListPage(request, response, box, out);
            } else if (v_process.equals("insertNotice")) { // 관리자 - 포럼공지 등록 페이지로
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { // 관리자 - 포럼공지 등록
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) { // 관리자 - 포럼공지 수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { // 관리자 - 포럼공지 수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("delete")) { // 관리자 - 포럼공지 삭제할때
                this.performDelete(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 메인 More 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMoreListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/user/kocca/open/ku_Notice_L.jsp";
            box.put("p_process", "moreNotice");

            OpenForumNoticeBean bean = new OpenForumNoticeBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "FN");
                box.put("p_grcode", "0000000");
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

            ArrayList<DataBox> list2 = bean.selectDirectList(box);
            request.setAttribute("selectNoticeList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/open/ku_Notice_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메인 공지사항 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMainListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            String v_userip = request.getRemoteAddr(); // 접속자 ip
            box.put("p_userip", v_userip);
            /* ===================== 공지사항 시작 ========================= */
            OpenForumNoticeBean bean = new OpenForumNoticeBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "FN");
                box.put("p_grcode", "0000000");
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

            String v_url = "";
            v_url = "/learn/user/kocca/open/Notice.jsp";
            //String v_url = "/index.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to " + v_url);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 리스트페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            // int v_seq = box.getInt("p_seq");
            String v_return_url = "/learn/user/kocca/open/ku_Notice_L.jsp";

            OpenForumNoticeBean bean = new OpenForumNoticeBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "FN");
                box.put("p_grcode", "0000000");
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

            ArrayList<DataBox> list2 = bean.selectDirectList(box);
            request.setAttribute("selectNoticeList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to/learn/user/kocca/open/ku_Notice_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 뷰페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            OpenForumNoticeBean bean = new OpenForumNoticeBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "FN");
                box.put("p_grcode", "0000000");
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

            DataBox dbox = bean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_Notice_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/open/ku_Notice_R.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewPage()\r\n" + ex.getMessage());

        }
    }

    /**
     * 포럼 공지사항 등록 페이지 이동
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
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_Notice_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/open/ku_Notice_I.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 포럼 공지사항 등록하기
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
            request.setAttribute("requestbox", box);
            // int v_seq = box.getInt("p_seq");

            OpenForumNoticeBean bean = new OpenForumNoticeBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "FN");
                box.put("p_grcode", "0000000");
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

            int isOk = bean.insertNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.OpenForumNoticeServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {

                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on OpenForumNoticeServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 포럼 수정페이지로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            OpenForumNoticeBean bean = new OpenForumNoticeBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "FN");
                box.put("p_grcode", "0000000");
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

            DataBox dbox = bean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_Notice_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/kocca/open/ku_Notice_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * // 포럼 수정하여 저장할때
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
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다
            OpenForumNoticeBean bean = new OpenForumNoticeBean();

            int isOk = bean.updateNotice(box);
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.OpenForumNoticeServlet";
            box.put("p_process", "");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on OpenForumNoticeServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * // 포럼 삭제할때
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
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다
            OpenForumNoticeBean bean = new OpenForumNoticeBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "FN");
                box.put("p_grcode", "0000000");
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

            int isOk = bean.deleteNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.OpenForumNoticeServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on OpenForumNoticeServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }
}
