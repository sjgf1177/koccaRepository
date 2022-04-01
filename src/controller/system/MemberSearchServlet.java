package controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.point.MyPointBean;
import com.credu.system.AdminUtil;
import com.credu.system.MemberSearchBean;

/**
 * 1. �� ��: ȸ���˻� ���� 2. ���α׷��� : MemberSearchServlet.java 3. �� ��: ȸ���˻� ���� 4. ȯ ��:
 * JDK 1.4 5. �� ��: 1.0 6. �� ��: ������ 2004. 12. 20 7. �� ��:
 */

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.system.MemberSearchServlet")
public class MemberSearchServlet extends javax.servlet.http.HttpServlet {

    /**
     * DoGet Pass get requests through to PerformTask
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (!AdminUtil.getInstance().checkRWRight("MemberSearchServlet", v_process, out, box)) {
                return;
            }

            if (v_process.equals("memberSearchPage")) { //  �λ�DB �˻� ������
                this.performMemberSearchPage(request, response, box, out);

            } else if (v_process.equals("selectMember")) { //  �������� �󼼺���
                this.performSelectMember(request, response, box, out);

            } else if (v_process.equals("MemberExcel")) { //���� ���
                this.performMemberExcel(request, response, box, out);

            } else if (v_process.equals("logView")) { //�α׺���
                this.performLogView(request, response, box, out);

            } else if (v_process.equals("MemberModify")) { //ȸ����������
                this.performMemberModify(request, response, box, out);

            } else if (v_process.equals("ResetUserPwd")) { //��й�ȣ �ʱ�ȭ
                this.performResetUserPwd(request, response, box, out);
                
            } else if (v_process.equals("ChangeUserMailing")) { // ���ϼ��ſ��� ����
            	this.performChangeUserMailing(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �λ�DB �˻� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMemberSearchPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            if (box.getString("p_action").equals("go")) {
                MemberSearchBean bean = new MemberSearchBean();

                ArrayList list = bean.searchMemberList(box);
                request.setAttribute("memberList", list);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberSearch_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MemberSearch_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberSearchPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * �������� �󼼺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectMember(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MemberSearchBean bean = new MemberSearchBean();
            
            if(!"".equals(box.getStringDefault("p_reuserid", ""))){
            	box.put("p_userid", box.getString("p_reuserid"));
            }

            DataBox dbox = bean.selectMemberInfo(box);
            request.setAttribute("memberInfo", dbox);
            MemberInfoBean proposeCourseBean = new MemberInfoBean();
            request.setAttribute("addInfo", proposeCourseBean.memberInfoViewNew(box));

            String loingId = box.getSession("userid");
            box.setSession("userid", box.getString("p_userid"));
            MyPointBean mypointbean = new MyPointBean(); // ���� ����Ʈ

            int iGetPoint = mypointbean.selectGetPoint(box); //���� ���� ����Ʈ
            int iUsePoint = mypointbean.selectUsePoint(box); //���� ��� ����Ʈ
            int iWaitPoint = mypointbean.selectWaitPoint(box); //���� ��� ����Ʈ

            box.put("p_getpoint", String.valueOf(iGetPoint));
            box.put("p_usepoint", String.valueOf(iUsePoint));
            box.put("p_waitpoint", String.valueOf(iWaitPoint));

            ArrayList list1 = mypointbean.selectHavePointList(box); //��������Ʈ
            request.setAttribute("selectHavePointList", list1);

            ArrayList list2 = mypointbean.selectStoldPointList(box); //��������Ʈ
            request.setAttribute("selectStoldPointList", list2);

            ArrayList list3 = mypointbean.selectUsePointList(box); //�������Ʈ
            request.setAttribute("selectUsePointList", list3);
            box.setSession("userid", loingId);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberSearchDetail_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/system/za_MemberSearchDetail_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMember()\r\n" + ex.getMessage());
        }
    }

    /**
     * MEMBER EXCEL
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMemberExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MemberSearchBean bean = new MemberSearchBean();
            ArrayList list = bean.searchMemberListExcel(box);

            request.setAttribute("memberList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberSearch_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberExcel)\r\n" + ex.getMessage());
        }
    }

    /**
     * �α׺���
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLogView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            MemberSearchBean bean = new MemberSearchBean();
            ArrayList list = bean.selectLog(box);

            request.setAttribute("logList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_MemberSearchLog_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("CompleteMemberExcel)\r\n" + ex.getMessage());
        }
    }

    /**
     * ȸ������ ���� �� �˻� ������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performMemberModify(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MemberSearchBean bean = new MemberSearchBean();
            int is_Ok = bean.setModify(box);

            String v_msg = "";
            String v_url = "/learn/admin/system/za_MemberSearch_L.jsp";
            AlertManager alert = new AlertManager();

            if (is_Ok > 0) {
                //if(is_Ok == 0) {
                v_msg = "�����Ǿ����ϴ�";
                ArrayList list = bean.searchMemberList(box);
                request.setAttribute("memberList", list);
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "���� �����Ͽ����ϴ�";
                alert.alertFailMessage(out, v_msg);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberSearchPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ���� ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performResetUserPwd(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_msg = "";
            String v_url = "/servlet/controller.system.MemberSearchServlet";
            MemberSearchBean bean = new MemberSearchBean();
            AlertManager alert = new AlertManager();
            int isOk = bean.resetUserPwd(box);

            box.put("p_process", "selectMember");

            request.setAttribute("requestbox", box);

            if (isOk > 0) {
                v_msg = "��й�ȣ�� �ʱ�ȭ �Ͽ����ϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box, false, false);
            } else {
                v_msg = "��й�ȣ �ʱ�ȭ�� ������ �߻��Ͽ����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performChangeUserInfo()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * ���ϼ��ſ��� ����
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performChangeUserMailing(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		String v_msg = "";
    		String v_url = "/servlet/controller.system.MemberSearchServlet";
    		MemberSearchBean bean = new MemberSearchBean();
    		AlertManager alert = new AlertManager();
    		int isOk = bean.changeUserMailing(box);
    		
    		box.put("p_process", "selectMember");
    		box.put("p_reuserid", box.getString("p_userid"));
    		
    		request.setAttribute("requestbox", box);
    		if (isOk > 0) {
    			v_msg = "���ϼ��ſ��θ� ���� �Ͽ����ϴ�.";
    			alert.alertOkMessage(out, v_msg, v_url, box, false, false);
    		} else {
    			v_msg = "���ϼ��ſ��� ����� ������ �߻��Ͽ����ϴ�.";
    			alert.alertFailMessage(out, v_msg);
    		}
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performChangeUserInfo()\r\n" + ex.getMessage());
    	}
    }

}
