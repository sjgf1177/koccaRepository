//**********************************************************
//1. 제      목: 우수회원을 관리하는  서블릿
//2. 프로그램명 : SuperiorityMemberAdminServlet.java
//3. 개      요: 우수회원을 관리한다.
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 하경태 2006.01.02
//7. 수      정:
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

          if(v_process.equals("selectList")) {                		// 조회할때
              this.performSelectList(request, response, box, out);
          }
          else if(v_process.equals("SuperiorityMemberExcel")) {   	// 엑셀
              this.performListExcel(request, response, box, out);
          }else if(v_process.equals("memberList")) {   				// 대상자 리스트 PopUp
              this.performMemberList(request, response, box, out);
          }
		  else if(v_process.equals("inputMember")) {   				// 대상자 등록
              this.performApply(request, response, box, out);
          }
      }catch(Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
      }
  }
	
  /**
  리스트
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
  엑셀보기
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
  대상자 리스트
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
  우수 회원 등록 
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
              v_msg = " 등록  되었습니다.";  
              //box.put("s_action","go"); 
              alert.selfClose(out, v_msg);
          }else {                
              //v_msg = "insert.fail";   
              v_msg = "등록에 실패하였습니다.";   
              alert.alertFailMessage(out, v_msg);   
          }                                          
      }catch (Exception ex) {           
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performInsert()\r\n" + ex.getMessage());
      }            
  }
  
  
}