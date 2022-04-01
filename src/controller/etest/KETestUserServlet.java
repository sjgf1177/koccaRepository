//**********************************************************
//1. ��      ��: �� ����
//2. ���α׷���: ETestUserServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: lyh
//**********************************************************

package controller.etest;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestPaperBean;
import com.credu.etest.KETestResultBean;
import com.credu.etest.KETestUserBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

/**
* @author Administrator
*
* To change the template for this generated type comment go to
* Window>Preferences>Java>Code Generation>Code and Comments
*/ 
@WebServlet("/servlet/controller.etest.KETestUserServlet")
public class KETestUserServlet extends HttpServlet implements Serializable {
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
		  System.out.println("E-Test KETestUserServlet : "+v_process);		  

          if(ErrorManager.isErrorMessageView()) {
                  box.put("errorout", out);
          }
     //     box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
          
          
          
          if(box.getSession("userid").equals("")){
          	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
              dispatcher.forward(request,response);
              return;
          }
          

          if (v_process.equals("ETestUserListPage")) {                      //�¶����׽�Ʈ ����� ������ ����Ʈ
              this.performETestUserListPage(request, response, box, out);
          } 
          else if (v_process.equals("ETestUserPaperListPage")) {                 //���� ��
              this.performETestUserPaperListPage(request, response, box, out);
          } 
         else if (v_process.equals("ETestUserResultInsert")) {                 //�¶����׽�Ʈ ������ ����Ҷ�
              this.performETestUserResultInsert(request, response, box, out);
          }
          else if (v_process.equals("ETestUserPaperResult")) {                 //�¶����׽�Ʈ ���κ� �򰡰�� ����
              this.performETestUserPaperResult(request, response, box, out);
          }
			else if (v_process.equals("ETestUserPaperResult2")) {                 //�¶����׽�Ʈ ���κ� �򰡰�� ����
              this.performETestUserPaperResult2(request, response, box, out);
          }
			else if (v_process.equals("ETestUserPaperTextResult")) {                 //�¶����׽�Ʈ ���κ� �򰡰�� ����
              this.performETestUserPaperTextResult(request, response, box, out);
          }

      }catch(Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
      }
  }

  /**
  �¶����׽�Ʈ ����Ʈ
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performETestUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
      try {     
          request.setAttribute("requestbox", box);            
          String v_return_url = "/learn/user/kocca/study/ku_ETestPaper_L2.jsp";
          KETestUserBean bean = new KETestUserBean();
          ArrayList list = bean.SelectUserList(box);  // ��
          request.setAttribute("ETestUserList", list);
          ArrayList list1 = bean.SelectUserHistoryList(box);  // ���
          request.setAttribute("ETestUserHistoryList", list1);
          
    //      Log.info.println(this, box, "performETestUserListPage");
          
          ServletContext sc = getServletContext();
          RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
          rd.forward(request, response);
          
     //     Log.info.println(this, box, "performETestUserListPage");
          
          }catch (Exception ex) {           
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performETestUserListPage()\r\n" + ex.getMessage());
      }
  }


  /**
  �¶����׽�Ʈ ���� ��
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performETestUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
      try {                 
  		request.setAttribute("requestbox", box);    
			String v_url  = "/servlet/controller.etest.KETestUserServlet";			
			String v_return_url = "" ;
			String v_msg        = "";
			
			ETestPaperBean bean = new ETestPaperBean();
			v_return_url = bean.getPaperPathData(box); 
			System.out.println("v_return_url:"+v_return_url);
		//	v_return_url = "/upload/lms_data/etestpaper/2005_ET00009/ET00009200510011.html";  // �ӽ�
		        
		//        v_return_url = "/dp/lms_data/etestpaper/2005_ET00001/ET00001200510021.jsp";  // �ӽ�
			AlertManager alert = new AlertManager();                        
//Log.info.println(this, box, "performETestUserPaperListPage");
			if(v_return_url.equals("")) {            	
				v_msg = "�������� �����ϴ�.";       
				//alert.alertOkMessage(out, v_msg, v_url , box);     
				alert.selfClose(out, v_msg);
			}else{
			  	ServletContext sc = getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
				rd.forward(request, response);
			}
		//	Log.info.println(this, box, "performETestUserPaperListPage");
       }catch (Exception ex) {           
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performETestUserPaperListPage()\r\n" + ex.getMessage());
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
  public void performETestUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
      try{

      	String v_url  = "/servlet/controller.etest.KETestUserServlet";
      	//jkh �ӽ�����0224
          //String v_url  = "/learn/user/etest/zu_close.jsp";
			KETestUserBean bean = new KETestUserBean();

          int isOk = bean.WriteETestUserResult(box);  // 

			AlertManager alert = new AlertManager();     
			String v_msg = "";
			box.put("p_process", "ETestUserPaperTextResult");
			//System.out.println(isOk+">>>>>>isOkisOkisOkisOk");			
			if(isOk == 1) {            	
				v_msg = "�����Ͽ����ϴ�.";
				isOk = bean.IncreaseTrycnt(box);       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 99) {            	
				v_msg = "�̹� ������ �����Դϴ�.";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else {                
				v_msg = "���⿡ �����Ͽ����ϴ�.";   
				alert.alertFailMessage(out, v_msg);   
			}

        Log.info.println(this, box, true);
       }catch (Exception ex) {
       	Log.info.println(this, box, true);
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performETestUserResultInsert()\r\n" + ex.getMessage());
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
  public void performETestUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
      try {     
          request.setAttribute("requestbox", box);            
          String v_return_url = "/learn/user/etest/zu_ETestPaperResult_L.jsp";
      
          KETestUserBean bean = new KETestUserBean();
          ArrayList list = bean.SelectUserPaperResult(box);
          request.setAttribute("UserPaperResult", list);

          ArrayList list2 = bean.SelectUserPaperResult2(box);
          request.setAttribute("UserPaperResult2", list2);

          KETestResultBean bean1 = new KETestResultBean();
			Vector v1 = bean1.SelectResultAverage2(box);               
			request.setAttribute("ETestResultAverage", v1);
   
		  	ServletContext sc = getServletContext();
		  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  	rd.forward(request, response);
			
       }catch (Exception ex) {           
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performETestUserPaperResult()\r\n" + ex.getMessage());
      }
  }

  /**
  �¶����׽�Ʈ ����� ������ ����
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performETestUserPaperResult2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
      try {     
          request.setAttribute("requestbox", box);            
          String v_return_url = "/learn/user/etest/zu_ETestPaperResult_L2.jsp";
      
          KETestUserBean bean = new KETestUserBean();
          ArrayList list = bean.SelectUserPaperResult(box); // ��� 
          request.setAttribute("UserPaperResult", list);

          ArrayList list2 = bean.SelectUserPaperResult2(box);
          request.setAttribute("UserPaperResult2", list2);

          KETestResultBean bean1 = new KETestResultBean();
          
			Vector v1 = bean1.SelectResultAverage(box);               
			request.setAttribute("ETestResultAverage", v1);

//			Vector v1 = bean.SelectPersonAverage(box);               
//			request.setAttribute("PersonAverage", v1);
   
		  	ServletContext sc = getServletContext();
		  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  	rd.forward(request, response);
			
       }catch (Exception ex) {           
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performETestUserPaperResult()\r\n" + ex.getMessage());
      }
  }


  /**
  �¶����׽�Ʈ ������ �������
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performETestUserPaperTextResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
      try {     
          request.setAttribute("requestbox", box);            
          String v_return_url = "/learn/user/etest/zu_ETestPaperTextResult_L.jsp";
      
          KETestUserBean bean = new KETestUserBean();
          ArrayList list = bean.SelectUserPaperTextResult(box);
          request.setAttribute("UserPaperResult", list);

          ArrayList list2 = bean.SelectUserPaperTextResult2(box);
          request.setAttribute("UserPaperResult2", list2);

//          KETestResultBean bean1 = new KETestResultBean();
//			Vector v1 = bean1.SelectResultAverage2(box);               
//			request.setAttribute("ETestResultAverage", v1);
   
		  	    ServletContext sc = getServletContext();
		   	   RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		      	rd.forward(request, response);
			
       }catch (Exception ex) {           
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performETestUserPaperTextResult()\r\n" + ex.getMessage());
      }
  }
     
}