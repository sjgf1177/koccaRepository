//**********************************************************
//	1. ��      ��: �̺�Ʈ���� ���� �����ϴ� ����(�����)
//	2. ���α׷��� : EventInfoServlet.java
//	3. ��      ��: �̺�Ʈ ���� ���α׷�(�����)
//	4. ȯ      ��: JDK 1.3
//	5. ��      ��: 1.0
//	6. ��      ��: ������ 2008. 10. 07
//	7. ��     ��1:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.EventInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.EventInfoServlet")
public class EventInfoServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

			if(v_process.equals("eventReg")){                      // �̺�Ʈ ���
				this.performEventInsertPage(request, response, box, out);
			} else if(v_process.equals("eventChk")){               // �̺�Ʈ üũ
				this.performEventChkPage(request, response, box, out);
			}
			
		} catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

    /**
   �̺�Ʈ ����ڵ��
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performEventInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);        // ��������� box ��ü�� �Ѱ��ش�

           EventInfoBean bean = new EventInfoBean();
           int isOk = bean.insertEventData(box);          

           String v_msg = "";
           String v_url = "/servlet/controller.homepage.MainServlet";
           box.put("p_process", "");

           AlertManager alert = new AlertManager();
 
           if(isOk < 1) {
              v_msg = "�̺�Ʈ ��Ͽ� �����߽��ϴ�.";
            	alert.alertOkMessage(out, v_msg, v_url , box);
           }

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/event/20081105/event_main.jsp");
           rd.forward(request, response);
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performLoginPage()\r\n" + ex.getMessage());
       }
   }

    /**
   �̺�Ʈ ����� üũ
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performEventChkPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
       	
           request.setAttribute("requestbox", box);        // ��������� box ��ü�� �Ѱ��ش�

           EventInfoBean bean = new EventInfoBean();
           int isOk = bean.insertEventData(box);
           
           box.put("p_userCnt", String.valueOf(isOk));

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/member/gu_idEventChk.jsp");
           rd.forward(request, response);
           
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performLoginPage()\r\n" + ex.getMessage());
       }
   }

}