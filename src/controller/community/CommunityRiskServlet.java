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
 * Time: ���� 1:48:09
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
                // �α� check ��ƾ VER 0.2 - 2003.09.9
                if (!AdminUtil.getInstance().checkLogin(out, box))
                 return;

                //���� üũ
                if (!AdminUtil.getInstance().checkInsideGubun(out, box))
                 return;
            }
            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("select")) {                        //  ���
              this.performList(request, response, box, out);
            } else if(v_process.equals("view")) {                     //  ��������
              this.performView(request, response, box, out);
            } else if(v_process.equals("updatePage")) {                //  ����������
              this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                     //  ����
              this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                     //  ����
              this.performDelete(request, response, box, out);
            } else if(v_process.equals("deleteRePly")) {                     //  ����
              this.performDeleteRePly(request, response, box, out);
            } else if(v_process.equals("insertPage")) {                //  �Է�������
              this.performinsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                     //  ����
              this.performInsert(request, response, box, out);
            } else if(v_process.equals("replySave")) {                     //  ���� ����
              this.performInsertRePly(request, response, box, out);
            } else if(v_process.equals("updateRePly")) {                     //  ���� ������Ʈ��
              this.performUpdateRePly(request, response, box, out);
            } else if(v_process.equals("eventInsert")) {                     //  �ӽ÷� ���� �Լ�(�̺�Ʈ��)
              this.performEventInsert(request, response, box, out);
            } else if(v_process.equals("login")) {                     //  �ӽ÷� ���� �α��� �Լ�(�̺�Ʈ��)
              this.performLogin(request, response, box, out);
            } else if(v_process.equals("eventPresent")) {                     //  �ӽ÷� ���� ����ڰ� � ������ ������� ��� �ִ� �Լ�(�̺�Ʈ��)
              this.performeventPresent(request, response, box, out);
            } else if(v_process.equals("reg20100716")) {                     //  �̺�Ʈ�� ������� ���� ����
              this.performReg20100716(request, response, box, out);
            } else if(v_process.equals("regjob")) {                     //  2012 �����ּҷ� ����
              this.performRegJob(request, response, box, out);
            } 

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
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
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
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
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
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
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
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
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
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

            String v_msg = "��� ���� �̺�Ʈ�� �̹� ���� �ϼ̽��ϴ�.";
            String v_url = "http://edu.kocca.or.kr/servlet/controller.homepage.MainServlet";

            AlertManager alert = new AlertManager();
            if(isOk == 1) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            } else if(isOk == 2) {
            	v_msg = "ID�� ���� ���� �ʽ��ϴ�.";
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

            String v_msg = "2012�� �����ּҷ� ������Ʈ�� �ϼ̽��ϴ�.";
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
