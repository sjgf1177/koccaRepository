//**********************************************************
//1. ��      ��: ���ȸ���� �����ϴ�  ����
//2. ���α׷��� : SuperiorityMemberAdminServlet.java
//3. ��      ��: ���ȸ���� �����Ѵ�.
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: �ϰ��� 2006.01.02
//7. ��      ��:
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.SuperiorityMemberAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.course.SuperiorityMemberAdminServlet")
public class SuperiorityMemberAdminServlet extends javax.servlet.http.HttpServlet {

  /**
  * DoGet
  * Pass get requests through to PerformTask
  */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      this.doPost(request, response);
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      PrintWriter out = null;
//      MultipartRequest multi = null;
      RequestBox box = null;
      String v_process = "";
//      int fileupstatus = 0;

      try {
          response.setContentType("text/html;charset=euc-kr");
          out = response.getWriter();

          box = RequestManager.getBox(request);

          v_process = box.getString("p_process");

          if(ErrorManager.isErrorMessageView()) {
              box.put("errorout", out);
          }

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("SuperiorityMemberServlet", v_process, out, box)) {
				return; 
			}
          box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

          if(v_process.equals("selectList")) {                		// ��ȸ�Ҷ�
              this.performSelectList(request, response, box, out);
          }
          else if(v_process.equals("SuperiorityMemberExcel")) {   	// ����
              this.performListExcel(request, response, box, out);
          }else if(v_process.equals("memberList")) {   				// ����� ����Ʈ PopUp
              this.performMemberList(request, response, box, out);
          }
		  else if(v_process.equals("inputMember")) {   				// ����� ���
              this.performApply(request, response, box, out);
          }
      }catch(Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
      }
  }
	
  /**
  ����Ʈ
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
      try {
          request.setAttribute("requestbox", box);
		  SuperiorityMemberAdminBean bean = new SuperiorityMemberAdminBean();

          ArrayList list = bean.selectList(box);
          request.setAttribute("selectList", list);

          ServletContext sc = getServletContext();
          RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SuperiorityMember_L.jsp");
          rd.forward(request, response);

          Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SuperiorityMember_L.jsp");

      }catch (Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performSelectList()\r\n" + ex.getMessage());
      }
  }
	
	/**
  ��������
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
      try {
          request.setAttribute("requestbox", box);
		  
		  SuperiorityMemberAdminBean bean = new SuperiorityMemberAdminBean();

          ArrayList list = bean.selectExcelList(box);
          request.setAttribute("selectList", list);
			
          ServletContext sc = getServletContext();
          RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SuperiorityMember_E.jsp");
          rd.forward(request, response);
      }catch (Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performSelectPre()\r\n" + ex.getMessage());
      }
  }
  
  /**
  ����� ����Ʈ
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performMemberList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
      try {
          request.setAttribute("requestbox", box);
		  SuperiorityMemberAdminBean bean = new SuperiorityMemberAdminBean();

          ArrayList list = bean.selectMemberList(box);
          request.setAttribute("selectList", list);

          ServletContext sc = getServletContext();
          RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_SuperiorityMember_P.jsp");
          rd.forward(request, response);

          Log.info.println(this, box, "Dispatch to /learn/admin/course/za_SuperiorityMember_P.jsp");

      }catch (Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performSelectList()\r\n" + ex.getMessage());
      }
  }
  
  /**
  ��� ȸ�� ��� 
  @param request  encapsulates the request to the servlet
  @param response encapsulates the response from the servlet
  @param box      receive from the form object
  @param out      printwriter object
  @return void
  */
  public void performApply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
      throws Exception {   
      try{                
//          String v_url = "/servlet/controller.course.SuperiorityMemberAdminServlet";
         
		  SuperiorityMemberAdminBean bean = new SuperiorityMemberAdminBean();
          int isOk = bean.inputMember(box);
          
          String v_msg = "";
          box.put("p_process", "memberList");
          
          AlertManager alert = new AlertManager();                        
          if(isOk > 0) {              
              //v_msg = "insert.ok";    
              v_msg = " ���  �Ǿ����ϴ�.";  
              //box.put("s_action","go"); 
              alert.selfClose(out, v_msg);
          }else {                
              //v_msg = "insert.fail";   
              v_msg = "��Ͽ� �����Ͽ����ϴ�.";   
              alert.alertFailMessage(out, v_msg);   
          }                                          
      }catch (Exception ex) {           
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performInsert()\r\n" + ex.getMessage());
      }            
  }
  
  
}