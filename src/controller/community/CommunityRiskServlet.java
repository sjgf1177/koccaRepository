package controller.community;

import com.credu.community.CommunityRiskBean;
import com.credu.library.*;
import com.credu.system.AdminUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 7. 8
 * Time: 오후 1:48:09
 * To change this template use File | Settings | File Templates.
 */
public class CommunityRiskServlet extends javax.servlet.http.HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
            this.doPost(request, response);
        }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out          = null;
        RequestBox box          = null;
        String           v_process    = "";

        try {
            response.setContentType("text/html;charset=euc-kr");

            out       = response.getWriter();
            box       = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","select");

            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            if(!v_process.equals("eventInsert") && !v_process.equals("login") && !v_process.equals("eventPresent") && !v_process.equals("reg20100716") && !v_process.equals("regjob") )
            {
                // 로긴 check 루틴 VER 0.2 - 2003.09.9
                if (!AdminUtil.getInstance().checkLogin(out, box))
                 return;

                //권한 체크
                if (!AdminUtil.getInstance().checkInsideGubun(out, box))
                 return;
            }
            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("select")) {                        //  목록
              this.performList(request, response, box, out);
            } else if(v_process.equals("view")) {                     //  뷰페이지
              this.performView(request, response, box, out);
            } else if(v_process.equals("updatePage")) {                //  수정페이지
              this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                     //  수정
              this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                     //  삭제
              this.performDelete(request, response, box, out);
            } else if(v_process.equals("deleteRePly")) {                     //  삭제
              this.performDeleteRePly(request, response, box, out);
            } else if(v_process.equals("insertPage")) {                //  입력페이지
              this.performinsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                     //  저장
              this.performInsert(request, response, box, out);
            } else if(v_process.equals("replySave")) {                     //  리플 저장
              this.performInsertRePly(request, response, box, out);
            } else if(v_process.equals("updateRePly")) {                     //  리플 업데이트장
              this.performUpdateRePly(request, response, box, out);
            } else if(v_process.equals("eventInsert")) {                     //  임시로 쓰는 함수(이벤트용)
              this.performEventInsert(request, response, box, out);
            } else if(v_process.equals("login")) {                     //  임시로 쓰는 로그인 함수(이벤트용)
              this.performLogin(request, response, box, out);
            } else if(v_process.equals("eventPresent")) {                     //  임시로 쓰고 사용자가 어떤 선물을 골랐는지 디비에 넣는 함수(이벤트용)
              this.performeventPresent(request, response, box, out);
            } else if(v_process.equals("reg20100716")) {                     //  이벤트용 성공취업 축하 저장
              this.performReg20100716(request, response, box, out);
            } else if(v_process.equals("regjob")) {                     //  2012 동문주소록 저장
              this.performRegJob(request, response, box, out);
            } 

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            CommunityRiskBean bean = new CommunityRiskBean();
            ArrayList list = bean.selectList(box);

            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_Risk_L.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }

    public void performView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            CommunityRiskBean bean = new CommunityRiskBean();
            DataBox dbox  = bean.view(box);
            request.setAttribute("view", dbox);
            ArrayList list1 = bean.viewRePly(box);
            request.setAttribute("reply", list1);


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_Risk_V.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performView()\r\n" + ex.getMessage());
        }
    }

    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityRiskServlet";
            box.put("p_process", "select");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    public void performDeleteRePly(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.deleteRePly(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityRiskServlet";
            box.put("p_process", "view");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.update(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityRiskServlet";
            box.put("p_process", "select");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    public void performUpdateRePly(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.updateRePly(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityRiskServlet";
            box.put("p_process", "view");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.insert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityRiskServlet";
            box.put("p_process", "select");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    public void performInsertRePly(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.insertRePly(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityRiskServlet";
            box.put("p_process", "view");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    public void performinsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
         try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_Risk_I.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performView()\r\n" + ex.getMessage());
        }
    }

     public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            CommunityRiskBean bean = new CommunityRiskBean();
            DataBox dbox  = bean.view(box);
            request.setAttribute("view", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_Risk_U.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performView()\r\n" + ex.getMessage());
        }
    }

    public void performEventInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.eventInsert(box);

            String v_msg = "";
            String v_url = "http://edu.kocca.or.kr/";

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    public void performLogin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            CommunityRiskBean bean = new CommunityRiskBean();
            DataBox dbox  = bean.login(box);
            request.setAttribute("member", dbox);

            String gubun=box.getString("p_gubun_inja");
            String filename="";

            if(gubun.equals("A0001"))
                filename="/tmp/event201007.jsp";
            else if(gubun.equals("B0001"))
                filename="/tmp/event20100728.jsp";
            else if(gubun.equals("B0002"))
                filename="/tmp/game_20100730.jsp";
            else if(gubun.equals("B0003"))
                filename="/tmp/broad_20100809.jsp";
            else if(gubun.equals("C0001"))
                filename="/tmp/event_201205.jsp";
            else if(gubun.equals("D0001"))
                filename="/tmp/event_201206.jsp";
            else if(gubun.equals("E0001"))
                filename="/tmp/event_201207.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(filename);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performView()\r\n" + ex.getMessage());
        }
    }

    public void performeventPresent(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.eventPresent(box);

            String v_msg = "";
            String v_url = "";
   
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    public void performReg20100716(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.eventReg20100716(box);

            String v_msg = "취업 축하 이벤트에 이미 응모 하셨습니다.";
            String v_url = "http://edu.kocca.or.kr/servlet/controller.homepage.MainServlet";

            AlertManager alert = new AlertManager();
            if(isOk == 1) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            } else if(isOk == 2) {
            	v_msg = "ID가 존재 하지 않습니다.";
                alert.alertFailMessage(out, v_msg);
            } else {
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }
    
    public void performRegJob(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityRiskBean bean = new CommunityRiskBean();
            int isOk = bean.eventRegJob(box);

            String v_msg = "2012년 동문주소록 업데이트를 하셨습니다.";
            String v_url = "http://edu.kocca.or.kr/servlet/controller.homepage.MainServlet";

            AlertManager alert = new AlertManager();
            if(isOk == 1) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }  else {
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }
}
