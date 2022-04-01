//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ �˾� ����
//  2. ���α׷��� : HomePageBoardServlet.java
//  3. ��      ��: �ڷ���� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1 �̿���
//  7. ��      ��: 2005.7.1 �̿���
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityCategoryResultBean;
import com.credu.community.CommunityCreateBean;
import com.credu.community.CommunityIndexBean;
import com.credu.community.CommunityMsMangeBean;
import com.credu.community.CommunityMsMenuBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
public class CommunityPopUpServlet extends javax.servlet.http.HttpServlet {

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
//        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
//        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
//            String path = request.getServletPath();

            out    = response.getWriter();
            box    = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","dupchkPage");
            
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("dupchkPage")) {          //  Ŀ�´�Ƽ�� �ߺ�üũ�������� �̵��Ҷ�
               this.performCmuDupChkPage(request, response, box, out);
            } else if(v_process.equals("gomsnoticePop")) {          //  ����Ÿ��ü�˸� �������� �̵��Ҷ�
               this.performMsPopNoticePage(request, response, box, out);
            } else if(v_process.equals("gomsmemberPop")) {          //  Ŀ�´�Ƽ ȸ������ �������� �̵��Ҷ�
               this.performMsPopMemberPage(request, response, box, out);
            } else if(v_process.equals("insertnoticeData")) {          //  ȸ������ �˸� ������ ������.
               this.performInsertnoticeData(request, response, box, out);
            } else if(v_process.equals("gomsmembernonePop")) {          //  Ŀ�´�Ƽ ȸ������ �������� �̵��Ҷ�
               this.performMsPopMemberNonePage(request, response, box, out);
            } else if(v_process.equals("boarddupchkPage")) {          //  Ŀ�´�Ƽ�� �ߺ�üũ�������� �̵��Ҷ�
               this.performMsBoardDupChkPage(request, response, box, out);
            } else if(v_process.equals("menumemPage")) {          //  Ŀ�´�Ƽ ȸ������ �������� �̵��Ҷ�
               this.performMsPopMenuMemPage(request, response, box, out);
            } else if(v_process.equals("menucmufindPage")) {          //  Ŀ�´�Ƽ ��ȸ �������� �̵��Ҷ�
               this.performCmuFindPage(request, response, box, out);
            } else if(v_process.equals("sendmailPage")) {          //  �����Է��������� �̵��Ҷ�
               this.performSendMailPage(request, response, box, out);
            } else if(v_process.equals("sendmailData")) {          // ��������
               this.performSendMailData(request, response, box, out);
            } else if(v_process.equals("hongboPop")) {          // Ŀ�´�Ƽ ȫ�� �˾�
               this.performHongboPop(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
           Ŀ�´�Ƽ ȫ�� �˾�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performHongboPop(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            CommunityIndexBean bean = new CommunityIndexBean();
            DataBox dbox = bean.selectHongbo(box);   
            
            request.setAttribute("selectHongbo", dbox);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuPopHongbo.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuPopHongbo.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }
    /**
    Ŀ�´�Ƽ�� �ߺ�üũ�������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performCmuDupChkPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            CommunityCreateBean bean = new CommunityCreateBean();
            int iRecordCnt = bean.selectCmuNmRowCnt(box);   
            
            request.setAttribute("recordcnt", String.valueOf(iRecordCnt));

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuPopNameCheck.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuPopNameCheck.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }



    /**
    �޴��� �ߺ�üũ�������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsBoardDupChkPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int iRecordCnt = bean.selectCmuNmRowCnt(box);   
            
            request.setAttribute("recordcnt", String.valueOf(iRecordCnt));

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMenuCheck.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMenuCheck.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }

    /**
    Ŀ�´�Ƽ�� �ߺ�üũ�������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopNoticePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsPopNotice_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsPopNotice_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }


    /**
    �����Է��������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSendMailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�


            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean bean = new CommunityIndexBean();
            ArrayList list = bean.selectSendMailData(box); 
            request.setAttribute("list", list);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MailForm_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MailForm_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }


    /**
    Ŀ�´�Ƽ�� ����Ÿ����ȸ������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopMemberPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemberPop_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemberPop_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }

    /**
    Ŀ�´�Ƽ�� ����Ÿ����ȸ������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopMemberNonePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberNoneList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemberPop_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemberPop_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCmuDupChkPage()\r\n" + ex.getMessage());
        }
    }

    /**
    ȸ������ �˸������� ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performInsertnoticeData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMangeBean bean = new CommunityMsMangeBean();

            int isOk = bean.sendMsNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityPopUpServlet";
            box.put("p_process", "gomsnoticePop");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {

                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityCreateServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }


    /**
    Ŀ�´�Ƽ�� ,�޴����濡�� ȸ��ã�� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performMsPopMenuMemPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberNoneList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMenuMemSearch_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMenuMemSearch_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPopMenuMemPage()\r\n" + ex.getMessage());
        }
    }


    /**
    Ŀ�´�Ƽ�� ��ȸ���������̵�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performCmuFindPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�


            CommunityCategoryResultBean bean = new CommunityCategoryResultBean();
            ArrayList list = bean.selectCateGoryList(box); 
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrCmuFindPop_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrCmuFindPop_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPopMenuMemPage()\r\n" + ex.getMessage());
        }
    }



    /**
    �Ϲ� ��������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    @SuppressWarnings("unchecked")
    public void performSendMailData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMangeBean bean = new CommunityMsMangeBean();

            int isOk = bean.sendMail(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityPopUpServlet";
            box.put("p_process", "sendmailPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {

                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityPopUpServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendMailData()\r\n" + ex.getMessage());
        }
    }
}

