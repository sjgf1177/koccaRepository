//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ Q&A �����ϴ� ����
//  2. ���α׷��� : HomePageBoardServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� Q&A �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1 
//  7. ��      ��: 2005.7.1 
//**********************************************************

package controller.community;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.credu.community.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class CommunityQnAServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter      out          = null;
        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            String path = request.getServletPath();

			out       = response.getWriter();
            box       = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process","selectlist");
			
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

			// �α� check ��ƾ VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("selectlist")) {          //  ����Ʈ �������� �̵��Ҷ�
			   this.performListPage(request, response, box, out);
            } else if(v_process.equals("insertPage")) {          //  ��� �������� �̵��Ҷ�
			   this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insertData")) {          //  �űԵ�� 
			   this.performInsertData(request, response, box, out);
            } else if(v_process.equals("viewPage")) {           //  ���� 
			   this.performViewPage(request, response, box, out);
            } else if(v_process.equals("insertMemoData")) {           //  ��۵�� 
			   this.performInsertMemoData(request, response, box, out);
            } else if(v_process.equals("deleteMemoData")) {           //  ��ۻ���
			   this.performDeleteMemoData(request, response, box, out);
            } else if(v_process.equals("deleteData")) {           //  ��ۻ���
			   this.performDeleteData(request, response, box, out);
            } else if(v_process.equals("updatePage")) {           //  ���� �������� �̵��Ҷ�
			   this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("updateData")) {           //  ���� �������� �̵��Ҷ�
			   this.performUpdateData(request, response, box, out);
            } else if(v_process.equals("deleteFileData")) {           //  ���ϻ���
			   this.performDeleteFileData(request, response, box, out);
            } else if(v_process.equals("replyPage")) {               //  �亯��� �������� �̵��Ҷ�
			   this.performReplyPage(request, response, box, out);
            } else if(v_process.equals("replyData")) {          //  �亯��� 
			   this.performReplyData(request, response, box, out);

			} 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
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
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityQnABean bean = new CommunityQnABean();
			         ArrayList list = bean.selectListQna(box); 
            
            request.setAttribute("list", list);
            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuQnA_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuQnA_L.jsp");
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
    public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityQnABean bean = new CommunityQnABean();
            ArrayList list = bean.selectViewQna(box,"VIEW"); 
            
            request.setAttribute("list", list);


            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box);
            request.setAttribute("listAllUser", listbeanAllMem);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuQnA_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuQnA_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performViewPage()\r\n" + ex.getMessage());
        }
    }

    /**
    ��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
//            CommunityCreateBean bean = new CommunityCreateBean();
//			ArrayList list = bean.selectCodeType_L("0052"); //Ŀ�´�Ƽ �з��� �о�´�.
            
//            request.setAttribute("typelist", list);



            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box);
            request.setAttribute("listAllUser", listbeanAllMem);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuQnA_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuQnA_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


    /**
    �亯��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {


            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box);
            request.setAttribute("listAllUser", listbeanAllMem);


            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuQnA_I3.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuQnA_I3.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    ������������ �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            CommunityQnABean bean = new CommunityQnABean();
			         ArrayList list = bean.selectViewQna(box,"UPDATE"); 
            
            request.setAttribute("list", list);



            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box); 
            request.setAttribute("listAllUser", listbeanAllMem);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuQnA_I2.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuQnA_I2.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �űԵ�� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performInsertData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityQnABean bean = new CommunityQnABean();

            int isOk = bean.insertQnA(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityQnAServlet";
            box.put("p_process", "selectlist");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityQnAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }

    /**
    �亯��� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performReplyData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityQnABean bean = new CommunityQnABean();

            int isOk = bean.replyQnA(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityQnAServlet";
            box.put("p_process", "selectlist");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityQnAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }

    /**
    ���� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityQnABean bean = new CommunityQnABean();

            int isOk = bean.updateQnA(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityQnAServlet";
            box.put("p_process", "selectlist");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityQnAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }
    /**
    ��۵�� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performInsertMemoData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityQnABean bean = new CommunityQnABean();

            int isOk = bean.insertQnAMemo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityQnAServlet";
            box.put("p_process", "viewPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityQnAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }


    /**
    ��� ������ ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performDeleteMemoData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityQnABean bean = new CommunityQnABean();

            int isOk = bean.deleteQnAMemo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityQnAServlet";
            box.put("p_process", "viewPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityQnAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }


    /**
    �� ������ ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityQnABean bean = new CommunityQnABean();

            int isOk = bean.deleteQnAData(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityQnAServlet";
            box.put("p_process", "selectlist");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityQnAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }

    /**
    ���� ������ ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performDeleteFileData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityQnABean bean = new CommunityQnABean();

            int isOk = bean.deleteUpFile(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityQnAServlet";
            box.put("p_process", "updatePage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityQnAServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertData()\r\n" + ex.getMessage());
        }
    }
}

