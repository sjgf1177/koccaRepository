//**********************************************************
//  1. ��      ��: �������� �����ϴ� ����
//  2. ���α׷��� : LetterAdminServlet.java
//  3. ��      ��: �������� ���� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 13
//  7. ��     ��1:
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

import com.credu.common.SelectEduBean;
import com.credu.homepage.LetterAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.LetterAdminServlet")
public class LetterAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
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

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("LetterAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("selectView")){                      // ���� �󼼺���� �̵��Ҷ�
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("insertPage")) {              // ���� ����������� �̵��Ҷ�
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                  // ���� ����Ҷ�
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("updatePage")) {              // ���� ������������ �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                  // ���� �����Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                  // ���� �����Ҷ�
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("select")) {                  // ���� ����Ʈ
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("complist")) {                  // ȸ�� ����Ʈ
                this.performCompList(request, response, box, out);
            } else if(v_process.equals("grcodelist")) {                  // ȸ�� ����Ʈ
                this.performGrcodeList(request, response, box, out);
            } else if(v_process.equals("popupView")) {               //  �˾� �̸�����
                this.performPopupView(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    �������� �󼼺���� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            LetterAdminBean bean = new LetterAdminBean();
            //NoticeData data = bean.selectViewNotice(box);
            DataBox dbox = bean.selectViewNotice(box);

            request.setAttribute("selectNotice", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Letter_R.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Letter_R.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

     /**
    �������� ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            SelectEduBean seBean = new SelectEduBean();
            ArrayList grcodenm = seBean.getGrcode(box);
            request.setAttribute("grcodenm", grcodenm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Letter_I.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Letter_I.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
    �������� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            LetterAdminBean bean = new LetterAdminBean();

            int isOk = bean.insertNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.LetterAdminServlet";
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

            ////Log.info.println(this, box, v_msg + " on LetterAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }


     /**
    �������� ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            LetterAdminBean bean = new LetterAdminBean();
            //NoticeData data = bean.selectViewNotice(box);
            DataBox dbox = bean.selectViewNotice(box);

            request.setAttribute("selectNotice", dbox);

            //ArrayList compnm = bean.selectComp(box);
            //request.setAttribute("compnm", compnm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Letter_U.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Letter_U.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    // �������� �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            LetterAdminBean bean = new LetterAdminBean();

            int isOk = bean.updateNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.LetterAdminServlet";
            box.put("p_process", "select");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on LetterAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    // �������� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           LetterAdminBean bean = new LetterAdminBean();

            int isOk = bean.deleteNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.LetterAdminServlet";
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

            ////Log.info.println(this, box, v_msg + " on LetterAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    �������� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            LetterAdminBean bean = new LetterAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));

            if (tabseq == 0) {
                /*------- �Խ��� �з��� ���� �κ� ���� -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
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

            //�����׷� ���ùڽ�
            ArrayList EduGroup = bean.selectEduGroup(box);
            request.setAttribute("selectGrcode", EduGroup);

            //��ü���� ����Ʈ
            ArrayList List1 = bean.selectListNoticeAll(box);
            request.setAttribute("selectList1", List1);

            //�Ϲݰ��� ����Ʈ
            ArrayList List2 = bean.selectListNotice(box);
            request.setAttribute("selectList2", List2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Letter_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Letter_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }


     /**
    ȸ���������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performCompList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            LetterAdminBean bean = new LetterAdminBean();
            ArrayList compnm = bean.selectComp(box);
            request.setAttribute("compnm", compnm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NoticeComp_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_NoticeComp_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


    /**
      �����׷츮��Ʈ �������� �̵��Ҷ�
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
    */

    @SuppressWarnings("unchecked")
    public void performGrcodeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            SelectEduBean seBean = new SelectEduBean();
            ArrayList grcodenm = seBean.getGrcode(box);
            request.setAttribute("grcodenm", grcodenm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NoticeGrcode_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_NoticeGrcode_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �˾� �̸������ �̵��Ҷ�
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performPopupView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

           LetterAdminBean bean = new LetterAdminBean();
           String content = bean.popupView(box);

           request.setAttribute("content", content);

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Preview.jsp");
           rd.forward(request, response);

           //Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Preview.jsp");

       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performSelectView()\r\n" + ex.getMessage());
       }
   }

}

