  
  package com.credu.study;

  import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

  /**
   * @author Administrator
   *
   * To change the template for this generated type comment go to
   * Window>Preferences>Java>Code Generation>Code and Comments
   */
  @SuppressWarnings({ "unchecked", "serial" })
  @WebServlet("/servlet/controller.study.SpotStudySubjectListServlet")
  public class SpotStudySubjectListServlet extends HttpServlet implements Serializable {

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
              v_process = box.getStringDefault("p_process","SpotStudyList");
              
              if(ErrorManager.isErrorMessageView()) {
                  box.put("errorout", out);
              }

              if (!AdminUtil.getInstance().checkRWRight("SpotStudyServlet", v_process, out, box)) {
                  return; 
              }

              if (v_process.equals("SpotStudyList")) {                       //상시과정 과정별리스트
                  this.performSpotStudyBySubjList(request, response, box, out);
              } else if (v_process.equals("SpotStudyListByUser")) {                       //in case of 수료 처리 조회 화면
                  this.performSpotStudyByUserList(request, response, box, out);
              } else if (v_process.equals("SpotStudyListExcel")) {                       //상시과정 과정별리스트
                  this.performSpotStudyBySubjExcelList(request, response, box, out);
              } else if (v_process.equals("SpotStudyListByUserExcel")) {                       //in case of 수료 처리 조회 화면
                  this.performSpotStudyByUserExcelList(request, response, box, out);
              }
          }catch(Exception ex) {
              ErrorManager.getErrorStackTrace(ex, out);
          }

      }
      
      /**
      상시과정조회- 과정별
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
      */
      public void performSpotStudyBySubjList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
          try {     
              request.setAttribute("requestbox", box);            
              String v_return_url = "/learn/admin/study/za_SpotStudyListBySubj_L.jsp";
                        
              SpotStudyBean bean = new SpotStudyBean();
              ArrayList list1 = bean.SelecSubjComplRateList(box);
              request.setAttribute("spotBySubjList", list1);
              
              ServletContext sc = getServletContext();
              RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
              rd.forward(request, response);
          }catch (Exception ex) {           
              ErrorManager.getErrorStackTrace(ex, out);
              throw new Exception("performListPage()\r\n" + ex.getMessage());
          }
      }

      /**
      상시과정조회- 과정별(excel)
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
      */
      public void performSpotStudyBySubjExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
          try {     
              request.setAttribute("requestbox", box);            
              String v_return_url = "/learn/admin/study/za_SpotStudyListBySubj_E.jsp";
                        
              SpotStudyBean bean = new SpotStudyBean();
              ArrayList list1 = bean.SelecSubjComplRateList(box);
              request.setAttribute("spotBySubjList", list1);
              
              ServletContext sc = getServletContext();
              RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
              rd.forward(request, response);
          }catch (Exception ex) {           
              ErrorManager.getErrorStackTrace(ex, out);
              throw new Exception("performListPage()\r\n" + ex.getMessage());
          }
      }
      
      /**
      상시과정조회 - 개인별
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
      */
      public void performSpotStudyByUserList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
          try {     
              request.setAttribute("requestbox", box);            
              String v_return_url = "/learn/admin/study/za_SpotStudyListByUser_L.jsp";
                        
              SpotStudyBean bean = new SpotStudyBean();
              ArrayList list1 = bean.SelectCompleteList(box);
              
              request.setAttribute("completeList", list1);
              
              ServletContext sc = getServletContext();
              RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
              rd.forward(request, response);
          }catch (Exception ex) {           
              ErrorManager.getErrorStackTrace(ex, out);
              throw new Exception("performListPage()\r\n" + ex.getMessage());
          }
      }
      
      /**
      상시과정조회 - 개인별(excel)
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
      */
      public void performSpotStudyByUserExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
          try {     
              request.setAttribute("requestbox", box);            
              String v_return_url = "/learn/admin/study/za_SpotStudyListByUser_E.jsp";
                        
              SpotStudyBean bean = new SpotStudyBean();
              ArrayList list1 = bean.SelectCompleteList(box);
              request.setAttribute("completeList", list1);
              
              ServletContext sc = getServletContext();
              RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
              rd.forward(request, response);
          }catch (Exception ex) {           
              ErrorManager.getErrorStackTrace(ex, out);
              throw new Exception("performListPage()\r\n" + ex.getMessage());
          }
      }                 
}
