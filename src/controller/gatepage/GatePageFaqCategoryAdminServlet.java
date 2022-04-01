//**********************************************************
//  1. 제      목: GatePage FAQ 카테고리 제어하는 서블릿
//  2. 프로그램명 : GatePageFaqCategoryAdminServlet.java
//  3. 개      요: GatePage FAQ 카테고리 제어 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2005. 1. 1
//  7. 수      정:
//**********************************************************
package controller.gatepage;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.credu.gatepage.*;
import com.credu.system.*;
import com.credu.library.*;

@WebServlet("/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet")
public class GatePageFaqCategoryAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
			
			if(v_process.equals("")){
				box.put("v_process","selectList");
				v_process = "selectList";
			}
			
			System.out.println("v_process : " + v_process);

			////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("GatePageFaqCategoryAdminServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			////////////////////////////////////////////////////////////////

			if(v_process.equals("selectList")){                      // faq 카테고리 리스트 조회
                this.performSelectList(request, response, box, out);
			} else if(v_process.equals("insertPage")){                // faq 카테고리 등록페이지로 이동
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                    // faq 카테고리 등록
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("updatePage")) {                // faq 카테고리 수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                    //faq 카테고리 수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                    // faq 카테고리 삭제할때
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("faqList")) {                  // faq 리스트
                this.performFaqList(request, response, box, out);
			} else if(v_process.equals("insertPage2")) {              // faq 등록페이지로 이동
                this.performInsertPage2(request, response, box, out);
			} else if(v_process.equals("insert2")) {                  // faq 등록
                this.performInsert2(request, response, box, out);
			} else if(v_process.equals("updatePage2")) {                // faq 수정페이지로 이동할때
                this.performUpdatePage2(request, response, box, out);
			} else if(v_process.equals("update2")) {                 //faq 수정하여 저장할때
                this.performUpdate2(request, response, box, out);
			} else if(v_process.equals("delete2")) {                    // faq 삭제할때
                this.performDelete2(request, response, box, out);
			}
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

     /**
    GatePage FAQ 카테고리 리스트 페이지 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */	 
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		try {        
			request.setAttribute("requestbox", box);            

			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();
			
			ArrayList list = gatepagefc.selectListGatePageFaqCategory(box);
			
			request.setAttribute("selectListGatePageFaqCategory", list);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatePageFaqCategory_L.jsp");
			rd.forward(request, response);
			
		}catch (Exception ex) {ex.printStackTrace();
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}

     /**
    등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);	
			System.out.println("여기는 서블릿s_gubun************** " + box.getString("p_gubun"));


			//**************카테고리를 선택했는지 확인하는 곳*****************
			String v_msg = "";
			AlertManager alert = new AlertManager();
			String s_gubun = box.getString("p_gubun");
					   
					   
						

						if (s_gubun.equals("00")){
								v_msg = "관리 시스템을 선택해야 합니다";
								alert.historyBack(out, v_msg);	
						}
			//***************카테고리를 선택했는지 확인하는 곳 끝****************
			
			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();
			
			DataBox dbox = gatepagefc.GatePageFaqCategorySetting(box);
			
			request.setAttribute("GatePageFaqCategorySetting", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatePageFaqCategory_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
    등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();

			int isOk = gatepagefc.insertGatePageFaqCategory(box);

			String v_msg = "";
			String v_url = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
			box.put("p_process", "selectList");
			
			AlertManager alert = new AlertManager();
			
			if(isOk > 0) {
				v_msg = "저장하였습니다!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "저장 에러!";
				alert.alertFailMessage(out, v_msg); 
			}                                    

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

     /**
    메뉴 수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);	
			
			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();
			
			DataBox dbox = gatepagefc.selectGatePageFaqCategory(box);
			
			request.setAttribute("selectGatePageFaqCategory", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatePageFaqCategory_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    // 메뉴 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {

           GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();
			System.out.println("여기는 업데이트서블릿 카테고리 ************ " + box.getString("p_gubun"));
			int isOk = gatepagefc.updateGatePageFaqCategory(box);

			String v_msg = "";
			String v_url = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
			box.put("p_process", "selectList");
			
			AlertManager alert = new AlertManager();
			
			if(isOk > 0) {
				v_msg = "저장하였습니다!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "저장 에러!";
				alert.alertFailMessage(out, v_msg); 
			}                  

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    // 메뉴 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();

			int isOk = gatepagefc.deleteGatePageFaqCategory(box);

            String v_msg = "";
            String v_url = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "삭제하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "삭제 에러";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

     /**
    faq 리스트 페이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */	
    public void performFaqList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);	
			
			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();
			
			ArrayList list = gatepagefc.selectListGatePageFaq(box);
			
			request.setAttribute("selectListGatePageFaq", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatePageFaq_L.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    faq 등록 페이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */    
	public void performInsertPage2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);	
			
			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();
			
			DataBox dbox = gatepagefc.GatePageFaqSetting(box);
			
			request.setAttribute("GatePageFaqSetting", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatePageFaq_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


     /**
    등록 할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performInsert2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();

			int isOk = gatepagefc.insertGatePageFaq(box);

			String v_msg = "";
			String v_url = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
			box.put("p_process", "faqList");
			
			AlertManager alert = new AlertManager();
			
			if(isOk > 0) {
				v_msg = "저장하였습니다!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "저장 에러!";
				alert.alertFailMessage(out, v_msg); 
			}                                    

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }
	
	
	  /**
    수정 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	 public void performUpdatePage2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);	
			
			GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();
			
			DataBox dbox = gatepagefc.selectGatePageFaq(box);
			
			request.setAttribute("selectGatePageFaq", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/gatepage/za_GatePageFaq_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage2()\r\n" + ex.getMessage());
        }
    }
	

     /**
    수정할때 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */	
	public void performUpdate2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {

           GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();

			int isOk = gatepagefc.updateGatePageFaq(box);

			String v_msg = "";
			String v_url = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
			box.put("p_process", "faqList");
			
			AlertManager alert = new AlertManager();
			
			if(isOk > 0) {
				v_msg = "저장하였습니다!";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "저장 에러!";
				alert.alertFailMessage(out, v_msg); 
			}                  

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
	
	
	
	  /**
    faq 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            GatePageFaqCategoryAdminBean gatepagefc = new GatePageFaqCategoryAdminBean();

			int isOk = gatepagefc.deleteGatePageFaq(box);

            String v_msg = "";
            String v_url = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
            box.put("p_process", "faqList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "삭제하였습니다!";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "삭제 에러";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

}

