//**********************************************************
//1. 제      목: 온라인테스트 대상자관리
//2. 프로그램명: ETestMemberServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//**********************************************************

package controller.etest;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestMemberBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
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

@WebServlet("/servlet/controller.etest.ETestMemberServlet")
public class ETestMemberServlet extends HttpServlet implements Serializable {
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
            v_process = box.getStringDefault("p_process", "ETestMemberListPage");
			//System.out.println("E-Test 대상자관리 ETestMemberServlet : "+v_process);			

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ETestMemberServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ETestMemberListPage")) {                    //온라인테스트 대상자 리스트
                this.performETestMemberListPage(request, response, box, out);
            } else if (v_process.equals("ETestMemberInsertPage")) {             // 온라인테스트 응시자 개인별 등록 페이지로 이동 (검색전)
                this.performETestMemberInsertPage(request, response, box, out);
            } else if (v_process.equals("MemberTargetListPage")) {              // 온라인테스트 응시자 개인별 등록 페이지로 이동 (검색후)
                this.performtMemberTargetListPage(request, response, box, out);
            } else if (v_process.equals("ETestMemberInsert")) {                 // 온라인테스트 응시자 개인별 등록할때
                this.performETestMemberInsert(request, response, box, out);
            } else if (v_process.equals("ETestMemberDelete")) {                 // 온라인테스트 대상자 삭제할때
                this.performETestMemberDelete(request, response, box, out);
            } else if (v_process.equals("ETestMemberFileToDB")) {               // 온라인테스트 대상자 FileToDB
                this.performETestMemberFileToDB(request, response, box, out);
            } else if (v_process.equals("insertFileToDB")) {                    // 온라인테스트 대상자 FileToDB 입력 
                this.performInsertFileToDB(request, response, box, out);
            } else if (v_process.equals("previewFileToDB")) {                   // 온라인테스트 대상자 FileToDB 미리보기
                this.performPreviewFileToDB(request, response, box, out);
            } else if (v_process.equals("ETestSubjMemberInsertPage")) {             // 온라인테스트 응시자 과정별 등록 페이지로 이동 (검색전)
                this.performETestSubjMemberInsertPage(request, response, box, out);
            } else if (v_process.equals("SubjMemberTargetListPage")) {              // 온라인테스트 응시자 과정별 등록 페이지로 이동 (검색후)
                this.performtSubjMemberTargetListPage(request, response, box, out);
            } else if(v_process.equals("SendFreeMail")){                            //in case of send free mail
                this.performSendFreeMail(request, response, box, out);
			}
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    온라인테스트 대상자 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMemberListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestMember_L.jsp";
            
            ETestMemberBean bean = new ETestMemberBean();
            
            DataBox dbox = bean.selectETestMasterData(box);
            request.setAttribute("ETestMasterData", dbox);
            
            ArrayList list = bean.selectETestMemberList(box);
            request.setAttribute("ETestMemberList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMemberListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 응시자 개인별 등록 페이지로 이동 (검색전)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMemberInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestMember_I.jsp";
      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMemberInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 응시자 개인별 등록 페이지로 이동 (검색후)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performtMemberTargetListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestMember_I.jsp";
            
            ETestMemberBean bean = new ETestMemberBean();
            
            ArrayList list = bean.selectMemberTargetList(box);
            request.setAttribute("MemberTargetList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performtMemberTargetListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 응시자 개인별 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMemberInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestMemberServlet";

            ETestMemberBean bean = new ETestMemberBean();
            int isOk = bean.insertETestMember(box);

            String v_msg = "";
            box.put("p_process", "ETestMemberListPage");
            box.put("p_action", "go");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
              v_msg = "insert.ok";
              alert.alertOkMessage(out, v_msg, v_url , box, true, true);     
                //  public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed);  
                //  isOpenWin  openwindow 여부, 
                //  isClosed  openwindow 가 닫혀야하는지 여부
            }else if(isOk == -1){
              v_msg = "insert.fail";
              alert.alertFailMessage(out, v_msg);
            }
            else if(isOk == 0){
              v_msg = "이미 대상자로 등록되어 있습니다.";
              alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMemberInsert()\r\n" + ex.getMessage());
        }
    }


    /**
    온라인테스트 응시자 개인별 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
   public void performETestMemberDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestMemberServlet";

            ETestMemberBean bean = new ETestMemberBean();
            int isOk = bean.deleteETestMember(box);

            String v_msg = "";
            box.put("p_process", "ETestMemberListPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
              v_msg = "delete.ok";
              alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
              v_msg = "delete.fail";
              alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMasterDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 대상자 FileToDB
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMemberFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestMemberFileToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMemberFileToDB()\r\n" + ex.getMessage());
        }
    }
    
    /**
    온라인테스트 대상자 FileToDB 입력 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestMemberFileToDB_P.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 대상자 FileToDB 미리보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPreviewFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestMemberFileToDB_P.jsp";

            ETestMemberBean bean = new ETestMemberBean();

            DataBox dbox = bean.selectETestMasterData(box);
            request.setAttribute("ETestMasterData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 응시자 과정별 등록 페이지로 이동 (검색전)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestSubjMemberInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/etest/za_ETestSubjMember_I.jsp";
      
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestSubjMemberInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 응시자 과정별 등록 페이지로 이동 (검색후)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performtSubjMemberTargetListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestSubjMember_I.jsp";
            
            ETestMemberBean bean = new ETestMemberBean();
            
            ArrayList list = bean.selectSubjMemberTargetList(box);
            request.setAttribute("SubjMemberTargetList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performtSubjMemberTargetListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    SEND FREE MAIL
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSendFreeMail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SendFreeMail()\r\n" + ex.getMessage());
        }
    }	
}