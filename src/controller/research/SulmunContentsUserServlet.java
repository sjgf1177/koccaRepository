//**********************************************************
//1. ��      ��: �¶����׽�Ʈ ����������
//2. ���α׷���: SulmunContentsUserServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package controller.research;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunContentsPaperBean;
import com.credu.research.SulmunContentsUserBean;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.research.SulmunContentsUserServlet")
public class SulmunContentsUserServlet extends HttpServlet implements Serializable {
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
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if(box.getSession("userid").equals("")){
            	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }

            if (v_process.equals("SulmunUserListPage")) {                      //�¶����׽�Ʈ ����� ������ ����Ʈ
                this.performSulmunUserListPage(request, response, box, out);
            } 
            else if (v_process.equals("SulmunUserPaperListPage")) {                 //�¶����׽�Ʈ ����� ��������
                this.performSulmunUserPaperListPage(request, response, box, out);
            } 
           else if (v_process.equals("SulmunUserResultInsert")) {                 //�¶����׽�Ʈ ������ ����Ҷ�
                this.performSulmunUserResultInsert(request, response, box, out);
            }
            else if (v_process.equals("SulmunUserPaperResult")) {                 //�¶����׽�Ʈ ���κ� �򰡰�� ����
                this.performSulmunUserPaperResult(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �¶����׽�Ʈ ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
/**            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/research/zu_SulmunContentsUserList_L.jsp";
            
            SulmunContentsUserBean bean = new SulmunContentsUserBean();
            ArrayList list1 = bean.SelectUserList(box);
            request.setAttribute("SulmunUserList", list1);
            
            ArrayList list2 = bean.SelectUserResultList(box);
            request.setAttribute("SulmunUserResultList", list2);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
    */    }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    �¶����׽�Ʈ ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSulmunUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/research/zu_SulmunContentsUserPaper_I.jsp";
            
			SulmunContentsPaperBean bean = new SulmunContentsPaperBean();
			ArrayList list1 = bean.selectPaperQuestionExampleList(box);
			request.setAttribute("PaperQuestionExampleList", list1);
        
			box.put("p_subjsel",box.getString("p_subj"));
			box.put("p_upperclass","ALL");
			DataBox dbox1 = bean.getPaperData(box);               
			request.setAttribute("SulmunPaperData", dbox1);
			box.remove("p_subjsel");
			box.remove("p_subjsel");
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperListPage()\r\n" + ex.getMessage());
        }
    }
    
     /**
    �¶����׽�Ʈ ������ ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSulmunUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
           String v_url  = "/servlet/controller.research.SulmunContentsUserServlet";

            SulmunContentsUserBean bean = new SulmunContentsUserBean();
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            box.put("p_process", "SulmunUserPaperListPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk == 2) {
              v_msg = "������ ������ �ּż� �����մϴ�";  //������ ������ �ּż� �����մϴ�
              alert.alertOkMessage(out, v_msg, v_url , box);
            }else if(isOk == 1){
		      v_msg = "���� �Ⱓ �����Դϴ�";  //���� �Ⱓ �����Դϴ�
              alert.alertFailMessage(out, v_msg);
            }else if (isOk == 3){
		      v_msg = "���� �Ⱓ�� �Ϸ�Ǿ����ϴ�";  //���� �Ⱓ�� �Ϸ�Ǿ����ϴ�
              alert.alertFailMessage(out, v_msg);
            }else{
		      v_msg = "�̹� �ش� ������ �����ϼ̽��ϴ�";  //�̹� �ش� ������ �����ϼ̽��ϴ�
              alert.alertFailMessage(out, v_msg);			
			} 
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
        }
    } 
    
    /**
    �¶����׽�Ʈ ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSulmunUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/user/research/zu_SulmunContentsUserResultList.jsp";

			SulmunContentsUserBean bean = new SulmunContentsUserBean();

			DataBox dbox = bean.SelectUserPaperResult(box);               
			request.setAttribute("UserPaperResult", dbox);

			DataBox dbox1 = bean.selectSulmunUser(box);               
			request.setAttribute("SulmunUserData", dbox1);

			SulmunContentsPaperBean bean1 = new SulmunContentsPaperBean();
			ArrayList list1 = bean1.selectPaperQuestionExampleList(box);
			request.setAttribute("PaperQuestionExampleList", list1);
            
			box.put("p_subjsel",box.getString("p_subj"));
			box.put("p_upperclass","ALL");
			DataBox dbox2 = bean1.getPaperData(box);               
			request.setAttribute("SulmunPaperData", dbox2);
			box.remove("p_subjsel");
			box.remove("p_subjsel");

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperResult()\r\n" + ex.getMessage());
        }
    }
}