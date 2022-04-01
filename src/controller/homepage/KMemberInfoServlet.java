//**********************************************************
//1. ��      ��: �������� ���� �����ϴ� ����(�����)
//2. ���α׷��� : KMemberInfoServlet.java
//3. ��      ��: �������� ���� ���α׷�(�����)
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: ������ 2003. 7. 13
//7. ��     ��1:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
//import com.credu.training.*;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.KMemberInfoServlet")
public class KMemberInfoServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
	PrintWriter out = null;
	MultipartRequest multi = null;
	RequestBox box = null;
	String v_process = "";
	int fileupstatus = 0;

	try {
		response.setContentType("text/html;charset=euc-kr");
		out = response.getWriter();
		box = RequestManager.getBox(request);
		v_process = box.getString("p_process");

		if(ErrorManager.isErrorMessageView()) {
			box.put("errorout", out);
		}

        box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

		if(v_process.equals("memberUpdatePage")){                      // ������������ �̵�
			this.performMemberUpdatePage(request, response, box, out);
		} else if(v_process.equals("memberUpdate")) {                  // ����
			this.performMemberUpdate(request, response, box, out);
		} else {
		//	this.performMainList(request, response, box, out);
		}
	}catch(Exception ex) {
		ErrorManager.getErrorStackTrace(ex, out);
	}
}


/**
�������� ���� �������� �̵��Ҷ�
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performMemberUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
   try {
       request.setAttribute("requestbox", box);        // ��������� box ��ü�� �Ѱ��ش�

       MemberInfoBean bean = new MemberInfoBean();     
       //ArrayList list = bean.memberInfoView(box);
       //request.setAttribute("memberView", list);
       
       DataBox dbox = bean.memberInfoView(box);
       request.setAttribute("memberView", dbox);

       ServletContext sc = getServletContext();
       RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/member/ku_MemberInfo.jsp");
       rd.forward(request, response);
   }catch (Exception ex) {
       ErrorManager.getErrorStackTrace(ex, out);
       throw new Exception("performLoginPage()\r\n" + ex.getMessage());
   }
}


 /**
�������� ����ó��
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performMemberUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
        MemberInfoBean bean = new MemberInfoBean();
        
        int isOk = bean.memberInfoUpdate(box);

        String v_msg = "";
//        String v_url = "/servlet/controller.homepage.MemberInfoServlet";
//        box.put("p_process", "memberUpdatePage");
        String v_url = "/servlet/controller.homepage.MainServlet";
        box.put("p_process", "");

        AlertManager alert = new AlertManager();
       
        if(isOk > 0) {
            v_msg = "���������� ����Ǿ����ϴ�.";
        	alert.alertOkMessage(out, v_msg, v_url , box);
        }
        else {
            v_msg = "�������� ���濡 �����߽��ϴ�.";
            alert.alertFailMessage(out, v_msg);
        }

    }catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performMemberUpdate()\r\n" + ex.getMessage());
    }
}

}

