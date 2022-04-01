//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ ȸ�������� �����ϴ� ����
//  2. ���α׷��� : CommunityFrJoinServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� ȸ������ �������� �����Ѵ�
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

public class CommunityFrJoinServlet extends javax.servlet.http.HttpServlet {

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
            v_process = box.getStringDefault("p_process","selectmsmainPage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("movePage")) {                                //  ��� �������� �̵��Ҷ�
              this.performmoTrJoinPage(request, response, box, out);
            } else if(v_process.equals("insertData")) {          //  ȸ�� ���� ó��
              this.performInsertFrJoinData(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
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
    public void performmoTrJoinPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityIndexBean bean = new CommunityIndexBean();
            DataBox userBox = bean.selectTz_Member(box);
            
	        request.setAttribute("userBox", userBox);
	
	        ServletContext    sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrJoin_I.jsp");
	        rd.forward(request, response);

	        Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrJoin_I.jsp");
           
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }



     /**
    ȸ�������� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertFrJoinData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityFrJoinBean bean = new CommunityFrJoinBean();

            String v_rejoinFlag = box.getString("p_rejoinFlag");
            
            int isOk = 0;
            
            if(v_rejoinFlag.equals("Y")) isOk = bean.updateFrJoin(box); // �簡��
            else isOk = bean.insertFrJoin(box);// ����
            
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityIndexServlet";
            box.put("p_process", "selectmyindex");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityFrJoinServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFrJoinData()\r\n" + ex.getMessage());
        }
    }
}

