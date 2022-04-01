//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ ȫ���������� �����ϴ� ����
//  2. ���α׷��� : CommunityFrJoinServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� ȫ�� �������� �����Ѵ�
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

public class CommunityPrServlet extends javax.servlet.http.HttpServlet {

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
            v_process = box.getStringDefault("p_process","movePage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("movePage")) {                                //  ���� �������� �̵��Ҷ�
              this.performViewPage(request, response, box, out);
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
    public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            CommunityCategoryResultBean bean = new CommunityCategoryResultBean();
            ArrayList list = bean.selectHongbo(box); 
            
            request.setAttribute("list", list);

            //������ ȸ�� ���������
            CommunityMsMangeBean beancmUser = new CommunityMsMangeBean();
            DataBox listcmUser = beancmUser.selectMemSingleData(box);
            request.setAttribute("listUser", listcmUser);

            //����Ʈ ȸ����ü�� �α�����ȸ������
            CommunityIndexBean beanAllMem = new CommunityIndexBean();
            DataBox listbeanAllMem = beanAllMem.selectTz_Member(box); 
            request.setAttribute("listAllUser", listbeanAllMem);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrPr_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrPr_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performmoCrResultPage()\r\n" + ex.getMessage());
        }
    }




}

