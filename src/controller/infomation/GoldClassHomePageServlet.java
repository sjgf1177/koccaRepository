package controller.infomation;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.GoldClassHomePageBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.infomation.GoldClassHomePageServlet")
public class GoldClassHomePageServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        boolean v_canRead = false;
        boolean v_canAppend = false;
        boolean v_canDelete = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "mainPage");

            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            /*
             * if( !v_process.equals("mainPage") ) {
             * if(v_process.indexOf("Reply") > -1 ||
             * v_process.equals("popUpVod")) { if
             * (!AdminUtil.getInstance().checkLoginPopup(out, box)) { return; }
             * } else { if (!AdminUtil.getInstance().checkLogin(out, box)) {
             * return; } } }
             */
            
            if (box.getSession("tem_grcode").equals("N000111")) {
                box.put("p_frmURL", request.getRequestURI().toString() + "?p_process=" + v_process);
                if (!AdminUtil.getInstance().checkLogin(out, box)) {
                    return;
                }
            }
            
            if (v_process.equals("insertReplyPage")) {
                if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
                    return;
                }
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            v_canRead = BulletinManager.isAuthority(box, box.getString("p_canRead"));
            v_canAppend = BulletinManager.isAuthority(box, box.getString("p_canAppend"));
            // v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
            v_canDelete = BulletinManager.isAuthority(box, box.getString("p_canDelete"));
            // v_canReply = BulletinManager.isAuthority(box,box.getString("p_canReply"));

            if (v_process.equals("mainPage")) { // ���Ŭ��������������
                if (v_canRead) {
                    this.performMainPage(request, response, box, out);
                }

            } else if (v_process.equals("themeMainPage")) { // ���Ŭ��������������
                if (v_canRead) {
                    this.performThemeMainPage(request, response, box, out);
                }

            } else if (v_process.equals("selectPreGoldClassList")) { // ���� ��帮��Ʈ
                if (v_canRead) {
                    this.performSelectPreGoldClassList(request, response, box, out);
                }

            } else if (v_process.equals("insertReplyPage")) { // ���� �ǰ� ��� ������ �̵�
                if (v_canAppend) {
                    this.performInsertReplyPage(request, response, box, out);
                } else {
                    this.errorPage(box, out);
                }
            } else if (v_process.equals("insertReply")) { // ���� �ǰ� ���
                if (v_canAppend) {
                    this.performInsertReply(request, response, box, out);
                } else {
                    this.errorPage(box, out);
                }
            } else if (v_process.equals("deleteReply")) { // ���� �ǰ� ����
                if (v_canDelete) {
                    this.performDeleteReply(request, response, box, out);
                } else {
                    this.errorPage(box, out);
                }
            } else if (v_process.equals("selectView")) { // ���� �󼼺���
                if (v_canRead) {
                    this.performSelectView(request, response, box, out);
                }

            } else if (v_process.equals("popUpVod")) { // �����󺸱� (popup)
                if (v_canRead) {
                    this.performPopUpVod(request, response, box, out);
                }

            } else if (v_process.equals("updateViewCount")) { // �������� ������ ��ȸ�� ����
                if (v_canRead) {
                    this.performUpdateViewCount(request, response, box, out);
                }
            } else if (v_process.equals("getOpenClassInfo")) {
                this.performGetOpenClassInfo(request, response, box, out);
            } else if (v_process.equals("selectReviewListForAjax")) {
                this.performSelectReviewListForAjax(request, response, box, out); // ajax�� �̿��Ͽ� �ı� ��� ��ȸ
            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �ܺ� ��ũ�� Ÿ�� ���� ���¸� ���� �� ��쿡 �ʿ��� �⺻ ������ ��ȸ�Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performGetOpenClassInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            GoldClassHomePageBean bean = new GoldClassHomePageBean();

            DataBox dbox = bean.getOpenClassInfo(box);

            StringBuffer scriptSrc = new StringBuffer();
            java.util.Enumeration e = dbox.keys();
            scriptSrc.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>\n");
            scriptSrc.append("<ROOT>\n");

            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = dbox.getString(key);

                scriptSrc.append("<").append(key).append(">");
                scriptSrc.append(value);
                scriptSrc.append("</").append(key).append(">\n");
            }

            scriptSrc.append("</ROOT>\n");
            out.write(scriptSrc.toString());
            out.flush();

            Log.info.println(this, box, "Dispatch to /common/ajaxContent.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� ��ȸ ������ �����Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performUpdateViewCount(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            GoldClassHomePageBean bean = new GoldClassHomePageBean();

            int resultCnt = bean.updateOpenClassViewCount(box);
            request.setAttribute("result", resultCnt);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/common/ajaxContent.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /common/ajaxContent.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }

    }

    /**
     * ���� �ǰ� ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("rawtypes")
	public void performInsertReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	String v_url = "/learn/user/portal/information/zu_GoldClassReply_I.jsp";
        	if(box.getSession("tem_type").equals("B")){
        		v_url = "/learn/user/typeB/information/zu_GoldClassReply_I.jsp";
        	}
            GoldClassHomePageBean bean = new GoldClassHomePageBean();
            List replyList = bean.selectGoldClassReplyList(box);
            request.setAttribute("replyList", replyList);
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� �ǰ� ����Ͽ� �����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GoldClassHomePageBean bean = new GoldClassHomePageBean();

            int isOk = bean.insertReply(box);

            String v_msg = "";
            String v_url = "/servlet/controller.infomation.GoldClassHomePageServlet";
            box.put("p_process", "insertReplyPage");
            // ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk == 1) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else if (isOk == 2) {
                v_msg = "�� �� �̻��� �ǰ��� ����ϽǼ� �����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on GoldClassHomePageServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���Ŭ���� ���� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            if (box.getSession("tem_grcode").equals("N000001")) {
                // v_url = "/learn/user/2012/portal/information/zu_GoldClass_M.jsp";
                v_url = "/learn/user/2013/portal/information/zu_GoldClass_L.jsp";
            } else {
                v_url = "/learn/user/portal/information/zu_GoldClass_M.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/information/zu_GoldClass_M.jsp";
            }

            String dirFlag = box.getString("p_dir_flag");
            String seq = box.getString("p_seq");
            String lectureCls = box.getString("p_lecture_cls");

            if (dirFlag != null && !dirFlag.equals("") && seq != null && !seq.equals("")) {
            	request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
                StringBuffer sb = new StringBuffer();
                sb.append("<script>");
                sb.append("    location.href = '/servlet/controller.infomation.GoldClassHomePageServlet");
                sb.append("?p_process=selectView");
                sb.append("&p_lecture_cls=").append(lectureCls);
                sb.append("&p_seq=").append(seq);
                sb.append("&p_dir_flag=").append(dirFlag);
                sb.append("';");
                sb.append("</script>");
                out.write(sb.toString());
                out.flush();
            } else {

                GoldClassHomePageBean bean = new GoldClassHomePageBean();

                List GoldClassList = bean.selectMainGoldClassList(box);
                DataBox openClassCnt = bean.selecOpenClassCntInfo(box);
                request.setAttribute("openClassCnt", openClassCnt);
                request.setAttribute("openClassList", GoldClassList);

                /*
                 * DataBox dbox = bean.selectViewGoldClass(box);
                 * request.setAttribute("selectOffExpert", dbox);
                 */
                request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);
            }

            // //Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_GoldClass_U.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * ���Ŭ���� �׸��� ���� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performThemeMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            String v_url = "";
            if (box.getSession("tem_grcode").equals("N000001")) {
                v_url = "/learn/user/2013/portal/information/zu_GoldClassTheme_L.jsp";
            } else {
                v_url = "/learn/user/portal/information/zu_GoldClass_M.jsp";
            }

            String dirFlag = box.getString("p_dir_flag");
            String seq = box.getString("p_seq");
            String lectureCls = box.getString("p_lecture_cls");

            if (dirFlag != null && !dirFlag.equals("") && seq != null && !seq.equals("")) {
                StringBuffer sb = new StringBuffer();
                sb.append("<script>");
                sb.append("    location.href = '/servlet/controller.infomation.GoldClassHomePageServlet");
                sb.append("?p_process=selectView");
                sb.append("&p_lecture_cls=").append(lectureCls);
                sb.append("&p_seq=").append(seq);
                sb.append("&p_dir_flag=").append(dirFlag);
                sb.append("';");
                sb.append("</script>");
                out.write(sb.toString());
                out.flush();
            } else {

                GoldClassHomePageBean bean = new GoldClassHomePageBean();

                List GoldClassThemeList = bean.selectMainGoldClassThemeList(box);
                DataBox openClassThemeCnt = bean.selecOpenClassThemeCntInfo(box);
                
                request.setAttribute("openClassThemeCnt", openClassThemeCnt);
                request.setAttribute("openClassThemeList", GoldClassThemeList);

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("rawtypes")
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String gubun = box.getString("p_lecture_cls").substring(0,2);
            
            String retURL = "";

            String ua = request.getHeader("User-Agent").toLowerCase();

            if (ua.indexOf("iphone") > -1 || ua.indexOf("ipad") > -1 || ua.indexOf("android") > -1) {
                String seq = box.getString("p_seq");
                retURL = "http://m.edu.kocca.kr/servlet/controller.mobile.openclass.OpenClassViewServlet?process=openClassViewDetail&seq=" + seq + "&prevPage=category";
                
                request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
                response.sendRedirect(retURL);
            } else {

                GoldClassHomePageBean bean = new GoldClassHomePageBean();

                DataBox dbox = bean.selectViewGoldClass(box);
                //com.credu.mobile.openclass
                request.setAttribute("selectOffExpert", dbox);

                //���º� ���¼�
                DataBox openClassCnt = null;
                if(gubun.equals("GC")){
                    openClassCnt = bean.selecOpenClassCntInfo(box);
                } else if(gubun.equals("OT")){
                    openClassCnt = bean.selecOpenClassThemeCntInfo(box);
                }
                request.setAttribute("openClassCnt", openClassCnt);
                
                // �������� ����Ʈ
                ArrayList nextSubjList = bean.selectRelatedLecutreList(box); 
                request.setAttribute("nextSubjList", nextSubjList);
                
                // �ı� ��� ��ȸ
                ArrayList reviewList = bean.selectReviewList(box);
                request.setAttribute("reviewList", reviewList);

                if (box.getSession("tem_grcode").equals("N000001")) {
                    retURL = "/learn/user/2013/portal/information/zu_GoldClass_V.jsp";
                } else {
                    retURL = "/learn/user/portal/information/zu_GoldClass_R.jsp";
                }
                
                if(box.getSession("tem_type").equals("B")){
                	retURL = "/learn/user/typeB/information/zu_GoldClass_R.jsp";
                }
                
                request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(retURL);
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���Ŭ���� ����Ʈ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreGoldClassList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            GoldClassHomePageBean bean = new GoldClassHomePageBean();

            // �Ϲ� ����Ʈ
            ArrayList selectPreGoldClassList = bean.selectPreGoldClassList(box);
            request.setAttribute("selectPreGoldClassList", selectPreGoldClassList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/information/zu_GoldClassPre_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_GoldClassPre_L.jsp");
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
    public void performDeleteReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GoldClassHomePageBean bean = new GoldClassHomePageBean();

            int isOk = bean.deleteReply(box);

            String v_msg = "";
            String v_url = "/servlet/controller.infomation.GoldClassHomePageServlet";
            box.put("p_process", "insertReplyPage");
            // ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on GoldClassHomePageServlet");
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            GoldClassHomePageBean bean = new GoldClassHomePageBean();

            DataBox dbox = bean.selectViewGoldClass(box);
            request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/information/zu_GoldClass_V.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_GoldClass_V.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    public void errorPage(RequestBox box, PrintWriter out) throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            // Log.sys.println();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �ı� ����� ��ȸ�Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSelectReviewListForAjax(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            System.out.println("performSelectReviewListForAjax");
            request.setAttribute("requestbox", box);
            GoldClassHomePageBean bean = new GoldClassHomePageBean();

            String v_url = "/learn/user/2013/portal/information/zu_GoldClass_AjaxResult.jsp";

            // �ı� ��� ��ȸ
            ArrayList reviewList = bean.selectReviewList(box);
            request.setAttribute("reviewList", reviewList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectReviewListForAjax()\r\n" + ex.getMessage());
        }
    }
}
