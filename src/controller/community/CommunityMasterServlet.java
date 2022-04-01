//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ ����Ÿȭ���� �����ϴ� ����
//  2. ���α׷��� : HomePageBoardServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� ����Ÿȭ�� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1 
//  7. ��      ��: 2005.7.1 
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityCreateBean;
import com.credu.community.CommunityIndexBean;
import com.credu.community.CommunityMsCommunityCloseBean;
import com.credu.community.CommunityMsGradeBean;
import com.credu.community.CommunityMsGradeNmBean;
import com.credu.community.CommunityMsMangeBean;
import com.credu.community.CommunityMsMasterChangeBean;
import com.credu.community.CommunityMsMemberJoinBean;
import com.credu.community.CommunityMsMenuBean;
import com.credu.community.CommunityMsPrBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
public class CommunityMasterServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter      out          = null;
//        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
//        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
//            String path = request.getServletPath();

            out       = response.getWriter();
            box       = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","selectmsmainPage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("selectmsmainPage")) {                        //  ����Ÿ���� �������� �̵��Ҷ�
              this.performMsMainPage(request, response, box, out);
            } else if(v_process.equals("moveroomPage")) {                     //  �⺻�������� �������� �̵��Ҷ�
              this.performMsRoomPage(request, response, box, out);
            } else if(v_process.equals("updatemsroomData")) {                 //  �⺻���������Ҷ�
              this.performMsRoomUpdateData(request, response, box, out);
            } else if(v_process.equals("deletemsroomfileData")) {             // Ŀ�´�Ƽ �̹��� ������ �����Ҷ�
              this.performMsRoomFileDeleteData(request, response, box, out);
            } else if(v_process.equals("movemsmemberjoinPage")) {             //  ȸ������ �������� �̵��Ҷ�
              this.performMsMemberJoinPage(request, response, box, out);
            } else if(v_process.equals("insertmsmemberjoinData")) {           //  ȸ������ó��
              this.performMsMemberJoinData(request, response, box, out);
            } else if(v_process.equals("movemsmembergradePage")) {            //  ��޺��� �������� �̵��Ҷ�
              this.performMsMemberGradePage(request, response, box, out);
            } else if(v_process.equals("insertmsmembergradeData")) {          //  ��޺���ó��
              this.performMsMemberGradeData(request, response, box, out);
            } else if(v_process.equals("updatememberoutData")) {              //  ȸ������ó��
              this.performMsMemberOutData(request, response, box, out);

            } else if(v_process.equals("movemsmembergradenmPage")) {          //  ��޸��� �������� �̵��Ҷ�
              this.performMsMemberGradeNmPage(request, response, box, out);
            } else if(v_process.equals("insertmsmembergradenmData")) {        //  ��޸���ó��
              this.performMsMemberGradeNmData(request, response, box, out);

            } else if(v_process.equals("movemasterchagnePage")) {             //  ����Ÿ���� �������� �̵��Ҷ�
              this.performMsMasterChangePage(request, response, box, out);
            } else if(v_process.equals("updatemasterchangeData")) {           //  ����Ÿ����ó��
              this.performMsMasterChangeData(request, response, box, out);

            } else if(v_process.equals("movecommunityclosePage")) {           // ����Ʈ ��� �������� �̵�
               this.performMsCommunityClosePage(request, response, box, out);
            } else if(v_process.equals("movecommunitycloseData")) {           // ����Ʈ ���
               this.performMsCommunityCloseData(request, response, box, out);


            } else if(v_process.equals("movemenuPage")) {           // �޴���� �������� �̵�
               this.performMsMenuPage(request, response, box, out);
            } else if(v_process.equals("insertmenuData")) {           // �޴��߰����
               this.performMsMenuData(request, response, box, out);
            } else if(v_process.equals("updatemenuData")) {           // �޴�����
               this.performMsMenuUpdateData(request, response, box, out);
            } else if(v_process.equals("deletemenuData")) {           // �޴�����
               this.performMsMenuDeleteData(request, response, box, out);

            } else if(v_process.equals("movememberactivityPage")) {              //  ȸ��Ȱ����Ȳ
              this.performMsMemberActivityPage(request, response, box, out);

            
            } else if(v_process.equals("moveprPage")) {             // ȫ�����������̵� �������� �̵�
               this.performMsPrPage(request, response, box, out);
            } else if(v_process.equals("insertprData")) {           // ȫ�����
               this.performMsPrData(request, response, box, out);
            } else if(v_process.equals("deleteprfileData")) {           // ȫ���̹�������
               this.performMsPrDeleteFileData(request, response, box, out);
            } else if(v_process.equals("deleteprData")) {           // ȫ������
               this.performMsPrDeleteData(request, response, box, out);

            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    �����ε��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMain_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMain_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

   /**
    �⺻�������� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsRoomPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsRoom_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsRoom_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }



     /**
    Ŀ�´�Ƽ��� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsRoomUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityCreateBean bean = new CommunityCreateBean();
            int isOk = bean.updateBaseMst(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveroomPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityCreateServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomUpdateData()\r\n" + ex.getMessage());
        }
    }


     /**
    Ŀ�´�Ƽ ��� �̹����� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsRoomFileDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityCreateBean bean = new CommunityCreateBean();
            int isOk = bean.deleteSingleFile(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveroomPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomFileDeleteData()\r\n" + ex.getMessage());
        }
    }



    /**
    Ŀ�´�Ƽ�� ȸ������ �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMemberJoinPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            ArrayList list = bean.selectMemberList(box,"0"); //0.���Խ�û 1.���� 2.���ΰźι� ����
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemJoin_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemJoin_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberJoinPage()\r\n" + ex.getMessage());
        }
    }

     /**
    Ŀ�´�Ƽ ȸ������ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberJoinData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            int isOk = bean.updateCmuUserCloseFg(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmemberjoinPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomFileDeleteData()\r\n" + ex.getMessage());
        }
    }




    /**
    Ŀ�´�Ƽ�� ��޺��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMemberGradePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            //����ڵ���ȸ
            CommunityIndexBean bean1 = new CommunityIndexBean();
            ArrayList gradeList = bean1.selectTz_Cmugrdcode(box); 
            
            request.setAttribute("gradeList", gradeList);


            //����Ʈ��ȸ
            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            ArrayList list = bean.selectMemberList(box,"1"); //0.���Խ�û 1.���� 2.���ΰźι� ����
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemGrade_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemGrade_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberJoinPage()\r\n" + ex.getMessage());
        }
    }

     /**
    Ŀ�´�Ƽ ȸ�� ��޺���ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberGradeData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsGradeBean bean = new CommunityMsGradeBean();
            int isOk = bean.updateUsermstGrade(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmembergradePage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeData()\r\n" + ex.getMessage());
        }
    }


     /**
    Ŀ�´�Ƽ ȸ������ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberOutData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            int isOk = bean.updateCmuUserCloseFg(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmembergradePage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomFileDeleteData()\r\n" + ex.getMessage());
        }
    }



    /**
    Ŀ�´�Ƽ�� ��޸��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMemberGradeNmPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            //Ŀ�´�Ƽ����ڵ���ȸ
            CommunityIndexBean bean1 = new CommunityIndexBean();
            ArrayList gradeList = bean1.selectTz_Cmugrdcode(box); 
            
            request.setAttribute("gradeList", gradeList);


            //����Ʈ��ȸ
            CommunityCreateBean bean = new CommunityCreateBean();
            ArrayList list = bean.selectCodeType_L("0053");
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemGradeNm_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemGradeNm_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeNmPage()\r\n" + ex.getMessage());
        }
    }

     /**
    Ŀ�´�Ƽ ȸ�� ��޸���ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberGradeNmData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsGradeNmBean bean = new CommunityMsGradeNmBean();
            int isOk = bean.updateCommunityGradeNm(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmembergradenmPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeNmData()\r\n" + ex.getMessage());
        }
    }




    /**
    Ŀ�´�Ƽ�� ����Ÿ���� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMasterChangePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberSingleData(box,"01"); 
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMasterChange_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMasterChange_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeNmPage()\r\n" + ex.getMessage());
        }
    }

     /**
    Ŀ�´�Ƽ ȸ�� ��޸���ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMasterChangeData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMasterChangeBean bean = new CommunityMsMasterChangeBean();
            int isOk = bean.updateMasterChange(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityIndexServlet";
            box.put("p_process", "selectmyindex");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMasterChangeData()\r\n" + ex.getMessage());
        }
    }



    /**
    Ŀ�´�Ƽ�� ��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsCommunityClosePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsPopCmuClose_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsPopCmuClose_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityClosePage()\r\n" + ex.getMessage());
        }
    }


     /**
    Ŀ�´�Ƽ ����Ʈ ���ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsCommunityCloseData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsCommunityCloseBean bean = new CommunityMsCommunityCloseBean();
            int isOk = bean.updateCommunityClose(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movecommunityclosePage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityCloseData()\r\n" + ex.getMessage());
        }
    }
    


    /**
    Ŀ�´�Ƽ�� �޴���� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMenuPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            //����ڵ���ȸ
            CommunityIndexBean bean1 = new CommunityIndexBean();
            ArrayList gradeList = bean1.selectTz_Cmugrdcode(box); 
            
            request.setAttribute("gradeList", gradeList);

            //����ڵ���ȸ
            CommunityMsMenuBean bean  = new CommunityMsMenuBean();
            ArrayList list = bean.selectleftList(box); 
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMenu_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMenu_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityClosePage()\r\n" + ex.getMessage());
        }
    }



     /**
    Ŀ�´�Ƽ �޴����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMenuData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int isOk = bean.insertMenu(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemenuPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityCloseData()\r\n" + ex.getMessage());
        }
    }
    

     /**
    �޴� ����ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMenuUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int isOk = bean.updateMenu(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemenuPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMenuUpdateData()\r\n" + ex.getMessage());
        }
    }
    

     /**
    �޴� ����ó��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMenuDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int isOk = bean.deleteMenu(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemenuPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMenuDeleteData()\r\n" + ex.getMessage());
        }
    }

     /**
    Ŀ�´�Ƽ ȸ��Ȱ����Ȳ �������� �̵�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberActivityPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�


            //����Ʈ��ȸ
            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberList(box); //0.���Խ�û 1.���� 2.���ΰźι� ����
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemActivity.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemActivity.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberActivityPage()\r\n" + ex.getMessage());
        }
    }






    /**
    Ŀ�´�Ƽ�� ȫ����� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsPrPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            //����ڵ���ȸ
            CommunityMsPrBean bean  = new CommunityMsPrBean();
            ArrayList list = bean.selectQuery(box); 
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsPr.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsPr.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPrPage()\r\n" + ex.getMessage());
        }
    }



     /**
    Ŀ�´�Ƽ ȫ�����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsPrData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsPrBean bean = new CommunityMsPrBean();
            int isOk = bean.insertHongbo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveprPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPrData()\r\n" + ex.getMessage());
        }
    }
    

     /**
    ȫ�� ���ϻ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsPrDeleteFileData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsPrBean bean = new CommunityMsPrBean();
            int isOk = bean.deleteSingleFile(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveprPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPrDeleteFileData()\r\n" + ex.getMessage());
        }
    }
     /**
    ȫ�� ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsPrDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsPrBean bean = new CommunityMsPrBean();
            int isOk = bean.deleteHongbo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveprPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMenuDeleteData()\r\n" + ex.getMessage());
        }
    }

}
