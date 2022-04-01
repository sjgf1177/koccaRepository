// **********************************************************
// 1. 제 목: 나의 질문방
// 2. 프로그램명: KnowBoardUserServlet.java
// 3. 개 요:
// 4. 환 경: JDK 1.3
// 5. 버 젼: 0.1
// 6. 작 성: 정은년
// 7. 수 정:
//
// **********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.KnowBoardUserBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.KnowBoardUserServlet")
public class KnowBoardUserServlet extends HttpServlet implements Serializable {
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
            v_process = box.getString("p_process");
            box.put("p_knowborad", "ok");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            box.put("p_frmURL", request.getRequestURI().toString() + "?p_process=" + v_process);

            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("ListPage")) { // 최근지식 리스트
                this.performKnowLatestList(request, response, box, out);
            } else if (v_process.equals("MenuCategoryPage")) { // 메뉴 카테고리 리스트
                this.performMenuCategoryPage(request, response, box, out);
            } else if (v_process.equals("OpenCategoryPage")) { // 등록할때 카테고리 선택
                this.performOpenCategoryPage(request, response, box, out);
            } else if (v_process.equals("InsertPage")) { // 등록 페이지
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { // 등록할때s
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("selectView")) { // 상세보기로 이동할때
                this.performSelectView(request, response, box, out);
            } else if (v_process.equals("delete")) { // 삭제할때
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("insertRecommend")) { // 추천하기
                this.performInsertRecommend(request, response, box, out);
            } else if (v_process.equals("commentInsert")) { // 꼬릿말 등록할때
                this.performInsertcomment(request, response, box, out);
            } else if (v_process.equals("deleteComment")) { // 꼬릿말 삭제할때
                this.performDeleteComment(request, response, box, out);
            } else if (v_process.equals("updateComment")) { // 댓글 수정
                this.performUpdateComment(request, response, box, out);
            } else if (v_process.equals("PopListPage")) { // 인기지식 리스트 - 최다조회
                this.performPopListPage(request, response, box, out);
            } else if (v_process.equals("RecListPage")) { // 인기지식 리스트 - 최다추천
                this.performRecListPage(request, response, box, out);
            } else if (v_process.equals("ComListPage")) { // 인기지식 리스트 - 최다댓글
                this.performComListPage(request, response, box, out);
            } else if (v_process.equals("replyPage")) { // 답변페이지로 이동할때
                this.performReplyPage(request, response, box, out);
            } else if (v_process.equals("reply")) { // 답변등록할때
                this.performReply(request, response, box, out);
            } else if (v_process.equals("updatePage")) { // 수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) { // 수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if (v_process.equals("selectPage")) { // 카테고리별 보기
                this.performSelectPage(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    String url_path = "/learn/user/2013/portal/tutorcommunity/";

    /**
     * 지식 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performKnowLatestList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = url_path + "gu_KnowFactory_L.jsp";

            String area = box.getString("p_area");
            if (!area.equals("MINE")) {
                box.put("p_area", "MINE");
            }

            box.put("p_listgubun", "");
            KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList list = bean.selectList(box);
            request.setAttribute("selectLastList", list);

            /*
             * 2012 지식 팩토리 String v_return_url = "/learn/user/2012/portal/knowledge/gu_KnowFactory_L.jsp"; box.put("p_listgubun", ""); KnowBoardUserBean
             * bean = new KnowBoardUserBean(); ArrayList list = bean.selectList(box); request.setAttribute("selectLastList", list);
             */

            /*
             * 구 지식게시판 String v_return_url = "/learn/user/study/gu_KnowFactory_L.jsp"; ArrayList tnlist = bean.selectListRecTop(box);
             * request.setAttribute("selectListRecTop", tnlist); ArrayList tnlist2 = bean.selectListReplyTop(box);
             * request.setAttribute("selectListReplyTop", tnlist2);
             */
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performKnowLatestList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 인기지식 리스트 (최다조회)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performPopListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/game/study/gu_KnowPop_L.jsp";
            box.put("p_listgubun", "popcnt_list");
            KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList list1 = bean.selectList(box);
            request.setAttribute("PopListPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPopListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 인기지식 리스트 (최다추천)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performRecListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/game/study/gu_KnowRec_L.jsp";
            box.put("p_listgubun", "poprec_list");
            KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList list1 = bean.selectList(box);
            request.setAttribute("RecListPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRecListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 인기지식 리스트 (최다댓글)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performComListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/game/study/gu_KnowCom_L.jsp";
            box.put("p_listgubun", "popcom_list");
            KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList list1 = bean.selectList(box);
            request.setAttribute("ComListPage", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performComListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메뉴 카테고리 보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performMenuCategoryPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList list1 = KnowBoardUserBean.SelectMenuCategoryTreeList(box);
            request.setAttribute("MenuCategoryList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/study/gu_KnowMenuCategory_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMenuCategoryPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 카테고리 트리 보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performOpenCategoryPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList list1 = bean.SelectCategoryTreeList(box);
            request.setAttribute("CategoryList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/study/gu_KnowCategory_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOpenCategoryPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            // String v_url = "/learn/user/study/gu_KnowFactory_I.jsp";
            // String v_url = "/learn/user/2012/portal/knowledge/gu_KnowFactory_I.jsp";
            // String v_url = "/learn/user/2012/portal/tutorcommunity/gu_KnowFactory_I.jsp";
            String v_url = "/learn/user/2013/portal/tutorcommunity/gu_KnowFactory_I.jsp";

            ServletContext sc = getServletContext();

            KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList tnlist = bean.selectListRecTop(box);
            request.setAttribute("selectListRecTop", tnlist);

            ArrayList tnlist2 = bean.selectListReplyTop(box);
            request.setAttribute("selectListReplyTop", tnlist2);

            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
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
            KnowBoardUserBean bean = new KnowBoardUserBean();

            int isOk = bean.insertKnowBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            box.put("p_process", "ListPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
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
            KnowBoardUserBean bean = new KnowBoardUserBean();
            int isOk = bean.deleteBoard(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            box.put("p_process", "ListPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageQNAServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 상세보기로 이동할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            // String v_url = "/learn/user/2012/portal/tutorcommunity/gu_KnowFactory_R.jsp";
            String v_url = "/learn/user/2013/portal/tutorcommunity/gu_KnowFactory_R.jsp";
            KnowBoardUserBean bean = new KnowBoardUserBean();
            DataBox dbox = bean.SelectView(box);
            request.setAttribute("SelectView", dbox);

            ArrayList alist = bean.selectAnswerList(box);
            request.setAttribute("selectAns", alist);

            ArrayList list = bean.selectcommentList(box);
            request.setAttribute("selectCommentList", list);

            /*
             * if(dbox.getString("d_okyn1").equals("3") && box.getString("p_types").equals("B")){ DataBox dbox2 = bean.selectAns(box);
             * request.setAttribute("selectAns", dbox2); } else if (dbox.getString("d_okyn1").equals("3") && box.getString("p_types").equals("C")) { DataBox
             * dbox2 = bean.selectAns1(box); request.setAttribute("selectAns", dbox2); } ArrayList tnlist = bean.selectListRecTop(box);
             * request.setAttribute("selectListRecTop", tnlist); ArrayList tnlist2 = bean.selectListReplyTop(box);
             * request.setAttribute("selectListReplyTop", tnlist2);
             */

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /*
     * 추천하기
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsertRecommend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            KnowBoardUserBean bean = new KnowBoardUserBean();
            int isOk = bean.insertRecommend(box);
            // String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                // v_msg = "추천되었습니다.";
                alert.alertOkMessage(out, "추천되었습니다.", v_url, box);
            } else {
                // v_msg = "추천에 실패하였습니다.";
                alert.alertFailMessage(out, "추천에 실패하였습니다.");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertRecommend()\r\n" + ex.getMessage());
        }
    }

    /**
     * 꼬릿말 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performInsertcomment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            KnowBoardUserBean bean = new KnowBoardUserBean();

            int isOk = bean.insertComment(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            // box.put("p_process", "ListPage");
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertcomment()\r\n" + ex.getMessage());
        }
    }

    /**
     * 댓글 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performDeleteComment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            KnowBoardUserBean bean = new KnowBoardUserBean();
            int isOk = bean.deleteComment(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            // box.put("p_process", "ListPage");
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteComment()\r\n" + ex.getMessage());
        }
    }

    /**
     * 댓글 수정할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdateComment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            KnowBoardUserBean bean = new KnowBoardUserBean();
            int isOk = bean.updateComment(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            // box.put("p_process", "ListPage");
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteComment()\r\n" + ex.getMessage());
        }
    }

    /**
     * 답변페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            KnowBoardUserBean bean = new KnowBoardUserBean();
            DataBox dbox = bean.SelectView(box);
            request.setAttribute("SelectView", dbox);

            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_KnowBoard_A.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 답변처리
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
            KnowBoardUserBean bean = new KnowBoardUserBean();
            int isOk = bean.insertKnowBoardAns(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            box.put("p_process", "ListPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

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
    @SuppressWarnings("unchecked")
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            // String v_url = "/learn/user/2012/portal/tutorcommunity/gu_KnowFactory_U.jsp";
            String v_url = "/learn/user/2013/portal/tutorcommunity/gu_KnowFactory_U.jsp";

            KnowBoardUserBean bean = new KnowBoardUserBean();
            DataBox dbox = bean.SelectView(box);
            request.setAttribute("SelectView", dbox);

            ArrayList tnlist = bean.selectListRecTop(box);
            request.setAttribute("selectListRecTop", tnlist);

            ArrayList tnlist2 = bean.selectListReplyTop(box);
            request.setAttribute("selectListReplyTop", tnlist2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수정할때
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
            KnowBoardUserBean bean = new KnowBoardUserBean();

            int isOk = bean.updateKnowBoard(box);
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            box.put("p_process", "ListPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 카테고리별 보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String v_url = null;

            KnowBoardUserBean bean = new KnowBoardUserBean();
            ArrayList list1 = bean.selectListCate(box);
            request.setAttribute("SelectListCate", list1);

            String v_gubun = box.getString("p_gubun");

            if (v_gubun.equals("1")) {
                v_url = "/learn/user/game/study/gu_KnowPop_L.jsp";
            } else if (v_gubun.equals("2")) {
                v_url = "/learn/user/game/study/gu_KnowRec_L.jsp";
            } else if (v_gubun.equals("3")) {
                v_url = "/learn/user/game/study/gu_KnowCom_L.jsp";
            } else {
                v_url = "/learn/user/game/study/gu_KnowPop_L.jsp";
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
}