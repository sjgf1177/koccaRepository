//*********************************************************
//1. 제      목: 세금계산서 관리 
//2. 프로그램명: TaxAdminServlet.java
//3. 개      요: 행정서비스 servlet
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 하경태 2006.02.03
//7. 수      정:
//**********************************************************
package controller.polity;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.polity.TaxAdminBean;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.polity.TaxAdminServlet")
public class TaxAdminServlet extends javax.servlet.http.HttpServlet  implements Serializable { 

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
      // MultipartRequest multi = null;
      RequestBox box = null;
      String v_process = "";
    
      try {         
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            // String path = request.getServletPath();
            v_process = box.getStringDefault("p_process","selectList");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            
            if(v_process.equals("selectList")) {                        //  조회화면
                this.performSelectList(request, response, box, out);
            }else if(v_process.equals("ExcelDown")) {                   //  엑셀 저장
                this.performExcelList(request, response, box, out);
            }else if(v_process.equals("Insert")) {                      //  세금 계산서 신청
                this.performTaxInsert(request, response, box, out);
            }else if(v_process.equals("InsertPage")) {                  //  세금 계산서 신청
                this.performTaxInsertPage(request, response, box, out);
            }else if(v_process.equals("Update")) {                      //  세금 계산서 수정.
                this.performTaxUpdate(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    /**
    리스트(관리자)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            TaxAdminBean bean = new TaxAdminBean();

            ArrayList list = bean.selectList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_TaxAdmin_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_TaxAdmin_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    /**
      Excel 다운
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
      */
      @SuppressWarnings("unchecked")
    public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
          try{
              request.setAttribute("requestbox", box);            
              // String v_return_url = "/learn/admin/polity/za_TaxAdmin_E.jsp";
              
              TaxAdminBean bean = new TaxAdminBean();
            
              if (box.getString("p_action").equals("go")) 
              {            
                  ArrayList list = bean.selectExcelList(box);
                  request.setAttribute("selectList", list);
              }
                
              ServletContext sc = getServletContext();
              RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_TaxAdmin_E.jsp");
              rd.forward(request, response);
          }catch (Exception ex) {
              ErrorManager.getErrorStackTrace(ex, out);
              throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
          }
      }
      
    /**
    세금 계산서 처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    @SuppressWarnings("unchecked")
    public void performTaxUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{                
            String v_url  = "/servlet/controller.polity.TaxAdminServlet";
            String v_msg = "";

            TaxAdminBean bean = new TaxAdminBean();
            int isOk = bean.TaxUpdate(box);
            box.put("p_process","selectList");
            
            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {              
                v_msg = "update.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {                
                v_msg = "confirm.fail";   
                alert.alertFailMessage(out, v_msg);
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performApproval()\r\n" + ex.getMessage());
        }             
    }
    
    /**
      신청 페이지
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
      */
      public void performTaxInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
          try{
              request.setAttribute("requestbox", box);  
              String url = "";
              
              if(box.getSession("tem_grcode").equals("N000001"))
              {
                  url = "/learn/user/kocca/study/ku_TaxPage_I.jsp";           
              }
              else
              {
                  url = "/learn/user/game/study/gu_TaxPage_I.jsp";
              }
              
              TaxAdminBean bean = new TaxAdminBean();
                    
              DataBox dbox = bean.selectTax(box);
              request.setAttribute("select", dbox);
                
              ServletContext sc = getServletContext();
              RequestDispatcher rd = sc.getRequestDispatcher(url);
              rd.forward(request, response);
          }catch (Exception ex) {
              ErrorManager.getErrorStackTrace(ex, out);
              throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
          }
      }
    
    /**
    세금 계산서 신청 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    @SuppressWarnings("unchecked")
    public void performTaxInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {   
        try{
            String v_url = "";
            if(box.getSession("tem_grcode").equals("N000001"))
              {
                v_url = "/servlet/controller.study.KMyClassServlet?p_process=EducationSubjectPage";           
              }
              else
              {
                  v_url = "/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage";
              }
            String v_msg = "";

            TaxAdminBean bean = new TaxAdminBean();
            int isOk = bean.TaxInsert(box);
            box.put("p_process","selectList");
            
            AlertManager alert = new AlertManager();                        
            if(isOk > 0) {  
                v_msg = "신청 되었습니다."; 
                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            }else {  
                v_msg = "오프라인 과정 신청에 실패하였습니다.";   
                alert.alertFailMessage(out, v_msg);   
            } 
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performApproval()\r\n" + ex.getMessage());
        }             
    }
}
