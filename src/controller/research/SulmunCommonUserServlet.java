//**********************************************************
//1. ��      ��: �Ϲݼ��� ����������
//2. ���α׷���: SulmunCommonUserServlet.java
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
import com.credu.research.SulmunCommonPaperBean;
import com.credu.research.SulmunCommonUserBean;
import com.credu.research.SulmunTargetUserBean;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.research.SulmunCommonUserServlet")
public class SulmunCommonUserServlet extends HttpServlet implements Serializable {
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

            if (v_process.equals("SulmunUserListPage")) {                      //�Ϲݼ��� ����� ������ ����Ʈ
                this.performSulmunUserListPage(request, response, box, out);
            } 
            else if (v_process.equals("SulmunUserPaperListPage")) {                 //�Ϲݼ��� ����� ��������
                this.performSulmunUserPaperListPage(request, response, box, out);
            } 
           else if (v_process.equals("SulmunUserResultInsert")) {                 //�Ϲݼ��� ������ ������
                this.performSulmunUserResultInsert(request, response, box, out);
            }
            else if (v_process.equals("SulmunUserPaperResult")) {                 //�Ϲݼ��� ���κ� �򰡰�� ����
                this.performSulmunUserPaperResult(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �Ϲݼ��� ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/study/zu_SulmunPaper_L.jsp";

            SulmunCommonUserBean bean = new SulmunCommonUserBean();
            ArrayList list = bean.SelectUserList(box);
            request.setAttribute("SulmunCommonUserList", list);

            SulmunTargetUserBean bean1 = new SulmunTargetUserBean();
            ArrayList list1 = bean1.SelectUserList(box);
            request.setAttribute("SulmunTargetUserList", list1);
            
          //  ArrayList list2 = bean.SelectUserResultList(box);
           // request.setAttribute("SulmunUserResultList", list2);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    �Ϲݼ��� ����� ������ ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/research/zu_SulmunCommonUserPaper_I.jsp";
            
			SulmunCommonPaperBean bean = new SulmunCommonPaperBean();
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
    �Ϲݼ��� ������ ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
           String v_url  = "/servlet/controller.research.SulmunSubjUserServlet";

            SulmunCommonUserBean bean = new SulmunCommonUserBean();
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            box.put("p_process", "SulmunNew");
            box.put("p_tab", "2");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk == 2) {
              v_msg = "������ ������ �ּż� �����մϴ�.";
              alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            }else if(isOk == 1){
		      v_msg = "���� �Ⱓ �����Դϴ�.";
              alert.alertFailMessage(out, v_msg);
            }else if (isOk == 3){
		      v_msg = "���� �Ⱓ�� �Ϸ�Ǿ����ϴ�.";
              alert.alertFailMessage(out, v_msg);
            }else{
		      v_msg = "�̹� �ش� ������ �����ϼ̽��ϴ�.";
              alert.alertFailMessage(out, v_msg);			
			} 
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
        }
    } 
    
    /**
    �Ϲݼ��� ����� ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
			request.setAttribute("requestbox", box);            
			String v_return_url = "/learn/user/research/zu_SulmunCommonUserResultList.jsp";

			SulmunCommonUserBean bean = new SulmunCommonUserBean();

			DataBox dbox = bean.SelectUserPaperResult(box);               
			request.setAttribute("UserPaperResult", dbox);

			DataBox dbox1 = bean.selectSulmunUser(box);               
			request.setAttribute("SulmunUserData", dbox1);

			SulmunCommonPaperBean bean1 = new SulmunCommonPaperBean();
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