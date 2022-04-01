//**********************************************************
//1. 제      목: 공지사항 제어하는 서블릿(사용자)
//2. 프로그램명 : MainServlet.java
//3. 개      요: 공지사항 제어 프로그램(사용자)
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 정상진 2005. 7. 13
//7. 수     정1:
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.LoginBean;
import com.credu.homepage.NoticeAdminBean;
import com.credu.homepage.TutorLoginBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.templet.TempletBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.MainServlet_new")
public class MainServlet_new extends javax.servlet.http.HttpServlet implements Serializable {

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
        // int fileupstatus = 0;
        String hostname = "";
        String s_grcode = "";
        String v_grcode = "";
        String v_url = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            s_grcode = box.getSession("tem_grcode");

            LoginBean bean = new LoginBean();

            hostname = request.getHeader("Host");
            box.put("p_hostname", hostname);
            if (s_grcode.equals("")) {
                if (hostname.equals("autoever.eibis.co.kr")) {
                    v_url = "indexN000001.jsp";
                } else {
                    v_grcode = LoginBean.getCompanyUrl(box);
                    v_url = "/index" + v_grcode + ".jsp";
                }
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);
            }

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("authChange")) { // 권한 변경햇을때
                // 권한세션 변경
                String v_auth = box.getString("p_auth");
                box.setSession("gadmin", v_auth);
                box.setSession("grtype", bean.getGrtype(box));

                String v_serno = box.getSession("serno"); // 강사 접속 로그 일련번호
                int v_serno1 = 0; // 강사 접속 로그 일련번호

                if (v_auth.equals("P1") && v_serno.equals("")) { // 권한이 강사일 경우 강사 로그인 정보에 입력한다.
                    TutorLoginBean tbean = new TutorLoginBean();
                    v_serno1 = tbean.tutorLogin(box);
                    box.setSession("serno", v_serno1);
                }

                this.performMainList(request, response, box, out);
            } else if (v_process.equals("popupview")) {
                this.performPopupView(request, response, box, out);
            } else if (v_process.equals("selectNoticeList")) {
                this.performSelectNoticeList(request, response, box, out);
            } else if (v_process.equals("selectNoticeView")) {
                this.performSelectNoticeView(request, response, box, out);
            } else if (v_process.equals("usermail")) { // 사용자 메일작성폼
                this.performUsermail(request, response, box, out);
            } else if (v_process.equals("usermailsend")) { // 사용자 메일작성
                this.performUsermailSend(request, response, box, out);
            } else { // 메인
                this.performMainList(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 메인
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    /*
     * public void performAuthChange(HttpServletRequest request,
     * HttpServletResponse response, RequestBox box, PrintWriter out) throws
     * Exception { try { request.setAttribute("requestbox", box); //명시적으로 box
     * 객체를 넘겨준다
     * 
     * ServletContext sc = getServletContext(); RequestDispatcher rd =
     * sc.getRequestDispatcher("/learn/user/homepage/zu_Main.jsp");
     * rd.forward(request, response); }catch (Exception ex) {
     * ErrorManager.getErrorStackTrace(ex, out); throw new
     * Exception("performAuthChange()\r\n" + ex.getMessage()); } }
     */

    /**
     * 메인
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMainList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            String v_userip = request.getRemoteAddr(); // 접속자 ip
            box.put("p_userip", v_userip);

            /* ===================== 공지사항 시작 ========================= */
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {

                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/

                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            //팝업공지 리스트
            ArrayList pnlist = nbean.selectListNoticePopupHome(box);
            request.setAttribute("noticePopup", pnlist);

            //전체공지 리스트 (최근3개)
            ArrayList tnlist = nbean.selectListNoticeTop(box);
            request.setAttribute("noticeListTop", tnlist);

            //일반공지 리스트 (최근5개)
            //ArrayList list = nbean.selectListNoticeMain(box);
            //request.setAttribute("noticeList", list);
            /* ===================== 공지사항 끝 ========================= */

            /* ===================== 추천과정 시작 ================== */
            //MainSubjSearchBean sbean = new MainSubjSearchBean();
            //ArrayList srlist = sbean.selectSubjRecomMain(box);
            //request.setAttribute("SubjectRecomList", srlist);
            /* ===================== 추천과정 끝 ================== */

            /* ===================== 미확인메모 시작 ================== */
            //MemoBean memo = new MemoBean();
            //      int unconfirmed = memo.getUnconfirmedMemo(box);
            //      box.put("unconfirmed", String.valueOf(unconfirmed));
            /* ===================== 미확인메모 끝 ================== */

            String v_url = "";
            //String v_url = "/index.jsp";
            String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            String tem_type = box.getStringDefault("tem_type", box.getSession("tem_type"));

            TempletBean templetbean = new TempletBean();
            ArrayList mainl_list = templetbean.SelectMenuList(tem_grcode, "0", "L"); // LEFT MENU LIST
            ArrayList mainr_list = templetbean.SelectMenuList(tem_grcode, "0", "R"); // RIGHT MENU LIST
            ArrayList mainc_list = templetbean.SelectMenuList(tem_grcode, "0", "C"); // CENTER MENU LIST

            request.setAttribute("mainl_list", mainl_list);
            request.setAttribute("mainr_list", mainr_list);
            request.setAttribute("mainc_list", mainc_list);

            if (tem_type.equals("GA")) {
                v_url = "/learn/user/game/homepage/gu_MainA.jsp";
            } else if (tem_type.equals("GB")) {
                v_url = "/learn/user/game/homepage/gu_MainB.jsp";
            } else if (tem_type.equals("KA")) {
                v_url = "/learn/user/kocca/homepage/ku_MainA.jsp";
            } else if (tem_type.equals("KB")) {
                v_url = "/learn/user/kocca/homepage/ku_MainB.jsp";
            } else {
                v_url = "/";
            }

            // 개인정보 점검 ( 이메일 없을 경우 개인정보 페이지로 이동 )
            //            String v_userid = box.getSession("userid");
            //            String v_email = "";
            //            String v_deptnam = "";
            //            String v_work_plcnm = "";
            //            String v_comptel = "";
            //            String v_hometel = "";
            //            String v_addr = "";
            //            String v_pwd = "";
            //            int v_validation = 0;

            //        MemberInfoBean memberbean = new MemberInfoBean();
            //        DataBox dbox = memberbean.getMemberInfo(box);
            //        if ( dbox != null ) {
            //          v_validation = dbox.getInt   ("d_validation");
            //            v_email      = dbox.getString("d_email");
            //            v_deptnam    = dbox.getString("d_deptnam");
            //            v_work_plcnm = dbox.getString("d_work_plcnm");
            //            v_comptel    = dbox.getString("d_comptel");
            //            v_hometel    = dbox.getString("d_hometel");
            //            v_addr       = dbox.getString("d_addr");
            //            v_pwd        = dbox.getString("d_pwd");
            //
            //          if ( v_validation == 0 ) {
            //
            //              AlertManager alert = new AlertManager();
            //
            //              if (tem_type.equals("GA")) {
            //                  v_url = "/servlet/controller.homepage.MemberInfoServlet?p_process=memberUpdatePage";
            //              }else if (tem_type.equals("KA")) {
            //                  v_url = "/servlet/controller.homepage.KMemberInfoServlet?p_process=memberUpdatePage";
            //              }
            //              box.put("p_process","validation");
            //
            //              String v_msg = "실명인증이 필요합니다.";
            //              alert.alertOkMessage(out, v_msg, v_url , box);
            //          }
            //        }

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
     * 팝업상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performPopupView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            String v_userip = request.getRemoteAddr(); // 접속자 ip
            box.put("p_userip", v_userip);
            /* ===================== 공지사항 시작 ========================= */
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            DataBox dbox = nbean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);
            String v_url = "";
            if (box.getString("p_useframe").equals("Y")) {
                v_url = "/learn/user/homepage/zu_Notice_popOnlycontY.jsp";
            } else {
                v_url = "/learn/user/homepage/zu_Notice_popOnlycontN.jsp";
            }
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
     * 공지사항리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectNoticeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            String v_userip = request.getRemoteAddr(); // 접속자 ip
            box.put("p_userip", v_userip);
            /* ===================== 공지사항 시작 ========================= */
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            ArrayList list1 = nbean.selectListNoticeAllHome(box);
            request.setAttribute("selectNoticeListAll", list1);

            ArrayList list2 = nbean.selectListNoticeHome(box);
            request.setAttribute("selectNoticeList", list2);

            String v_url = "";
            v_url = "/learn/user/homepage/zu_Notice_L.jsp";
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
     * 공지사항상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectNoticeView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            String v_userip = request.getRemoteAddr(); // 접속자 ip
            box.put("p_userip", v_userip);
            /* ===================== 공지사항 시작 ========================= */
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            DataBox dbox = nbean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);
            String v_url = "";
            v_url = "/learn/user/homepage/zu_Notice_R.jsp";
            //String v_url = "/index.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            //Log.info.println(this, box, "Dispatch to " + v_url);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메일 보내기폼
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUsermail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/homepage/zu_Usermail.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            //Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메일 전송
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUsermailSend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            LoginBean bean = new LoginBean();
            int isOk = bean.insertUserMail(box);

            String v_msg = "";
            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "전송되었습니다.";
                alert.selfClose(out, v_msg);
            } else {
                v_msg = "전송에 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUsermailSend()\r\n" + ex.getMessage());
        }
    }
}
