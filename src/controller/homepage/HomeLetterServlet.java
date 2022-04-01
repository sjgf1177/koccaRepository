//**********************************************************
//1. ��      ��: Ȩ������ �������� ���� ����
//2. ���α׷���: HomeLetterServlet.java
//3. ��      ��: Ȩ������ �������� ���� ����
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: 2005.12.18 �̳���
//7. ��      ��:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomeLetterBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomeLetterServlet")
public class HomeLetterServlet extends javax.servlet.http.HttpServlet {

/**
* DoGet
* Pass get requests through to PerformTask
*/
public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
this.doPost(request, response);
}
@SuppressWarnings("unchecked")
public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
PrintWriter      out          = null;
RequestBox       box          = null;
String           v_process    = "";

try {
    response.setContentType("text/html;charset=euc-kr");

    out       = response.getWriter();
    box       = RequestManager.getBox(request);
    v_process = box.getStringDefault("p_process","List");

    if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

    // �α� check ��ƾ VER 0.2 - 2003.09.9
    //if (!AdminUtil.getInstance().checkLogin(out, box)) {
    // return;
    //}
    box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

    /////////////////////////////////////////////////////////////////////////////
    if(v_process.equals("List")) {          //  ����Ʈ �������� �̵��Ҷ�
      this.performListPage(request, response, box, out);
    } else if(v_process.equals("selectView")) {             //  ����
      this.performViewPage(request, response, box, out);
    } else if(v_process.equals("mainList")){        // ���� �ѷ��ֱ�
        this.performMainListPage(request, response, box, out);
    } else if(v_process.equals("moreNotice")){      // more ����
        this.performMoreListPage(request, response, box, out);
    }
}catch(Exception ex) {
    ErrorManager.getErrorStackTrace(ex, out);
}
}

/**
    ���� More ����Ʈ
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
@SuppressWarnings("unchecked")
public void performMoreListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
try {
    request.setAttribute("requestbox", box);

      String v_return_url = "/learn/user/portal/helpdesk/zu_NewsLetter_L.jsp";
      box.put("p_process","moreNotice");

      HomeLetterBean bean = new HomeLetterBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
            if (tabseq == 0) {
                /*------- �Խ��� �з��� ���� �κ� ���� -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = bean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�";  //�Խ��������� �����ϴ�
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            ArrayList list2 = bean.selectDirectList(box);
            request.setAttribute("selectLetterList", list2);

    ServletContext    sc = getServletContext();
    RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
    rd.forward(request, response);

    Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_NewsLetter_L.jsp");
}catch (Exception ex) {
    ErrorManager.getErrorStackTrace(ex, out);
    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
}
}
/**
���� �������� ����Ʈ
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
@SuppressWarnings("unchecked")
public void performMainListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
        request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

        String v_userip = request.getRemoteAddr();  // ������ ip
        box.put("p_userip", v_userip);
        box.put("p_process", "mainList");
        /*===================== �������� ���� =========================*/
        HomeLetterBean bean = new HomeLetterBean();

        int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
        if (tabseq == 0) {
            /*------- �Խ��� �з��� ���� �κ� ���� -----*/
            box.put("p_type", "HN");
            box.put("p_grcode", "0000000");
            box.put("p_subj", "0000000000");
            box.put("p_year", "0000");
            box.put("p_subjseq", "0000");
            /*----------------------------------------*/
            tabseq = bean.selectTableseq(box);
            if (tabseq == 0) {
                String msg = "�Խ��������� �����ϴ�";  //�Խ��������� �����ϴ�
                AlertManager.historyBack(out, msg);
            }
            box.put("p_tabseq", String.valueOf(tabseq));
        }

        String v_url = "";
        v_url = "/learn/user/game/service/Notice.jsp";
        //String v_url = "/index.jsp";

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
        rd.forward(request, response);

        ////Log.info.println(this, box, "Dispatch to " + v_url);

    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performMainList()\r\n" + ex.getMessage());
    }
}

/**
    ����Ʈ�������� �̵��Ҷ�
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
@SuppressWarnings("unchecked")
public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
try {
    request.setAttribute("requestbox", box);
      String v_return_url = "/learn/user/portal/helpdesk/zu_NewsLetter_L.jsp";

      HomeLetterBean bean = new HomeLetterBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
            if (tabseq == 0) {
                /*------- �Խ��� �з��� ���� �κ� ���� -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = bean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�";  //�Խ��������� �����ϴ�
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            ArrayList list2 = bean.selectDirectList(box);
            request.setAttribute("selectLetterList", list2);

    ServletContext    sc = getServletContext();
    RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
    rd.forward(request, response);

    Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_NewsLetter_L.jsp");
}catch (Exception ex) {
    ErrorManager.getErrorStackTrace(ex, out);
    throw new Exception("performInsertPage()\r\n" + ex.getMessage());
}
}


/**
    ���������� �̵��Ҷ�
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
@SuppressWarnings("unchecked")
public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
throws Exception {
try {
    request.setAttribute("requestbox", box);

    HomeLetterBean bean = new HomeLetterBean();

    int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
    if (tabseq == 0) {
        /*------- �Խ��� �з��� ���� �κ� ���� -----*/
        box.put("p_type", "HN");
        box.put("p_grcode", "0000000");
        box.put("p_subj", "0000000000");
        box.put("p_year", "0000");
        box.put("p_subjseq", "0000");
        /*----------------------------------------*/
        tabseq = bean.selectTableseq(box);
        if (tabseq == 0) {
            String msg = "�Խ��������� �����ϴ�";  //�Խ��������� �����ϴ�
            AlertManager.historyBack(out, msg);
        }
        box.put("p_tabseq", String.valueOf(tabseq));
    }

    DataBox dbox = bean.selectViewNotice(box);
    request.setAttribute("selectNotice", dbox);

    ServletContext    sc = getServletContext();
    RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/helpdesk/zu_NewsLetter_R.jsp");
    rd.forward(request, response);

    Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_NewsLetter_R.jsp");
}catch (Exception ex) {
    ErrorManager.getErrorStackTrace(ex, out);
    throw new Exception("performViewPage()\r\n" + ex.getMessage());

    /*/**
    �������׻󼼺��� (MainServelt)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void

    public void performSelectNoticeView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            String v_userip = request.getRemoteAddr();  // ������ ip
            box.put("p_userip", v_userip);
            /*===================== �������� ���� =========================
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
            if (tabseq == 0) {
                /*------- �Խ��� �з��� ���� �κ� ���� -----
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------
                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�.";
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

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }*/
}
}
}

