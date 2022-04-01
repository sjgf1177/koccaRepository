package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.EventHomePageBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.EventHomePageServlet")
public class EventHomePageServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

        boolean v_canRead = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (v_process.equals("insertRequest") && !AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
            if (v_process.equals("selectList")) { //  이벤트 리스트
                if (v_canRead)
                    this.performSelectList(request, response, box, out);
            } else if (v_process.equals("selectView")) { //  이벤트 상세
                if (v_canRead)
                    this.performSelectView(request, response, box, out);
            } else if (v_process.equals("selectViewPass")) { //  이벤트 선정자 보기
                if (v_canRead)
                    this.performSelectViewPass(request, response, box, out);
            } else if (v_process.equals("deleteComment")) { //   신청내용 삭제
                if (v_canRead)
                    this.performDeleteComment(request, response, box, out);
            } else if (v_process.equals("updateCommentPage")) { //   신청내용 수정
                if (v_canRead)
                    this.performUpdateCommentPage(request, response, box, out);
            } else if (v_process.equals("updateApplyComment")) { //   신청내용 수정
                if (v_canRead)
                    this.performUpdateComment(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 이벤트 선정자
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectViewPass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            EventHomePageBean bean = new EventHomePageBean();

            DataBox dbox = bean.selectView(box);
            request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/information/zu_EventPass_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_EventPass_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
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
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/event/zu_Event_R.jsp";
            } else {
                v_url = "/learn/user/portal/information/zu_Event_R.jsp";
            }

            EventHomePageBean bean = new EventHomePageBean();

            DataBox dbox = bean.selectView(box);
            //             ArrayList list = bean.selectMemoList(box);             

            request.setAttribute("selectView", dbox);
            //             request.setAttribute("selectEventMemoList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_Event_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
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
            EventHomePageBean bean = new EventHomePageBean();
            int isOk = bean.deleteComment(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.EventHomePageServlet";
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "응모글이 삭제되었습니다.";
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
     * 상세보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdateCommentPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            String v_url = "";
            v_url = "/learn/user/2012/portal/information/zu_Event_Commnet_R.jsp";

            EventHomePageBean bean = new EventHomePageBean();

            DataBox dbox = bean.selectView(box);
            ArrayList<DataBox> list = bean.selectMemoList(box);

            //box.put("p_indate",box.getInt("p_tabseq"););

            request.setAttribute("selectView", dbox);
            request.setAttribute("selectEventMemoList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_Event_R.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
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
    public void performUpdateComment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            EventHomePageBean bean = new EventHomePageBean();
            int isOk = bean.updateComment(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.EventHomePageServlet";
            box.put("p_process", "selectView");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "응모글이 수정되었습니다.";
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
     * 이벤트 리스트
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //명시적으로 box 객체를 넘겨준다

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) { // B2C 2012 renewal
                v_url = "/learn/user/2013/portal/event/zu_Event_L.jsp";

                EventHomePageBean bean = new EventHomePageBean();

                //일반 리스트
                ArrayList<DataBox> selectList = bean.selectListNew(box);
                request.setAttribute("selectList", selectList);

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);

            } else {
                v_url = "/learn/user/portal/information/zu_Event_L.jsp";

                EventHomePageBean bean = new EventHomePageBean();

                //일반 리스트
                ArrayList<DataBox> selectList = bean.selectList(box);
                request.setAttribute("selectList", selectList);

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);

            }

            Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_Event_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 
     * @param box
     * @param out
     * @throws Exception
     */
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
