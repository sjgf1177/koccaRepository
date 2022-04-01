package controller.infomation;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.GoldClassBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * �������¸� �����ϴ� servlet
 * 
 * @author saderaser
 * @since ?
 */
@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.infomation.GoldClassAdminServlet")
public class GoldClassAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("GoldClassAdminServlet", v_process, out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("selectList")) { // �������� ��� ��ȸ
                this.performSelectList(request, response, box, out);

            } else if (v_process.equals("insertPage")) { // �������� ��� ȭ������ �̵�
                this.performInsertPage(request, response, box, out);

            } else if (v_process.equals("insert")) { // �������� ���
                this.performInsert(request, response, box, out);

            } else if (v_process.equals("updatePage")) { // �������� ���� ȭ������ �̵�
                this.performUpdatePage(request, response, box, out);

            } else if (v_process.equals("update")) { //  �������� ���� 
                this.performUpdate(request, response, box, out);

            } else if (v_process.equals("delete")) { //  ����
                this.performDelete(request, response, box, out);

            } else if (v_process.equals("selectView")) { //  �󼼺���
                this.performSelectView(request, response, box, out);

            } else if (v_process.equals("popUpVod")) { //  �����󺸱�
                this.performPopUpVod(request, response, box, out);

            } else if (v_process.equals("ManageOpenClassPage")) { // �αⰭ����ȸ
                this.performManageOpenClassPage(request, response, box, out);

            } else if (v_process.equals("saveOpenClassList")) { // �αⰭ�¼���
                this.performSaveOpenClassList(request, response, box, out);
                
                
            //�������� �ڸ� ���Ϸ� ������Ʈ PAGE
            } else if (v_process.equals("subtitleUpdatePage")) {
                this.performSubtitleUpdatePage(request, response, box, out);
                
            //�������� �ڸ� ���Ϸ� ������Ʈ
            } else if (v_process.equals("subtitleUpdate")) {
            	this.performSubtitleUpdate(request, response, box, out);
                

            }//ManageOpenClassPage
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
     * ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            GoldClassBean bean = new GoldClassBean();
            ArrayList groupList = bean.selectMngGroupList(box);
            request.setAttribute("groupList", groupList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClass_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_GoldClass_I.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * // ����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GoldClassBean bean = new GoldClassBean();

            int seq = bean.insertGoldClass(box);

            if (seq > 0) {
                bean.saveGoldClassGroupInfo(box, seq);
            }

            String v_msg = "";
            String v_url = "/servlet/controller.infomation.GoldClassAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if (seq > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on GoldClassAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������������ �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            GoldClassBean bean = new GoldClassBean();

            DataBox dbox = bean.selectViewGoldClass(box);
            request.setAttribute("selectOffExpert", dbox);

            ArrayList groupList = bean.selectMngGroupList(box);
            request.setAttribute("groupList", groupList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClass_U.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_GoldClass_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * // �����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GoldClassBean bean = new GoldClassBean();

            int isOk = bean.updateGoldClass(box);

            if (isOk > 0) {
                bean.saveGoldClassGroupInfo(box);
            }

            String v_msg = "";
            String v_url = "/servlet/controller.infomation.GoldClassAdminServlet";
            box.put("p_process", "selectList");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on OffExpertAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * // �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            GoldClassBean bean = new GoldClassBean();

            DataBox dbox = bean.selectViewGoldClass(box);
            request.setAttribute("selectOffExpert", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClass_R.jsp");
            rd.forward(request, response);

            // Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_GoldClass_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�

            GoldClassBean bean = new GoldClassBean();

            //�Ϲ� ����Ʈ
            ArrayList selectList = bean.selectListGoldClass(box);
            request.setAttribute("selectList", selectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClass_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * // �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GoldClassBean bean = new GoldClassBean();

            int isOk = bean.deleteGoldClass(box);

            String v_msg = "";
            String v_url = "/servlet/controller.infomation.GoldClassAdminServlet";
            box.put("p_process", "selectList");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on GoldClassAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * // �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPopUpVod(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ��������� box ��ü�� �Ѱ��ش�

            GoldClassBean bean = new GoldClassBean();

            DataBox dbox = bean.selectViewGoldClass(box);
            request.setAttribute("selectOffExpert", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClass_V.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_GoldClass_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���°��� �α�/��õ ���� ȭ������ �̵�
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    public void performManageOpenClassPage(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            //String dispatcherURL = "/learn/admin/course/za_SubjectClassify_M.jsp";
            String dispatcherURL = "/learn/admin/infomation/za_GoldClassHit_M.jsp";

            GoldClassBean bean = new GoldClassBean();

            ArrayList allOpenclassList = bean.selectAllOpenClassList(box);
            ArrayList selectHitOpenClassList = bean.selectHitOpenClassList(box);
            req.setAttribute("allOpenclassList", allOpenclassList);
            req.setAttribute("hitOpenClassList", selectHitOpenClassList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(req, res);
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, out);
            throw new Exception("performManageOpenClassPage" + e.getMessage());
        }
    }

    /**
     * ���°��� �α�/��õ ��� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSaveOpenClassList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {

            GoldClassBean bean = new GoldClassBean();
            int resultCnt = bean.saveSubjectList(box);
            StringBuilder sb = new StringBuilder();

            sb.append("{\"resultCnt\": " + resultCnt + "}");

            out.write(sb.toString());
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSaveOpenClassList()\r\n" + ex.getMessage());
        }
    }
    
    
    /**
     * �������� �ڸ� ���Ϸ� ������Ʈ PAGE
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSubtitleUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception{
        try{

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/infomation/za_subTitleFromExcel_U.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubtitleUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� �ڸ� ���Ϸ� ������Ʈ
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSubtitleUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception{
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/infomation/za_subTitleExcelToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		 }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubtitleUpdate()\r\n" + ex.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
