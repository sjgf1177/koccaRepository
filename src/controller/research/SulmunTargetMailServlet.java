//**********************************************************
//1. 제      목: 설문 메일 발송 관리
//2. 프로그램명: SulmunTargetMailServlet .java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2004-11-05
//7. 수      정:
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
import com.credu.research.SulmunTargetMailBean;
import com.credu.research.SulmunTargetPaperBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.research.SulmunTargetMailServlet")
public class SulmunTargetMailServlet extends HttpServlet implements Serializable {
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
            v_process = box.getStringDefault("p_process", "SulmunMailListPage");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("SulmunTargetMailServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("SulmunMailListPage")) {                    //설문 메일발송 리스트
                this.performSulmunMailListPage(request, response, box, out);
            } 
            else if (v_process.equals("SulmunMailSendPage")) {             // 설문 메일 입력 페이지
                this.performSulmunMailSendPage(request, response, box, out);
            } 
            else if (v_process.equals("SulmunMailPreviewPage")) {              // 설문 메일 미리보기 페이지
                this.performSulmunMailPreviewPage(request, response, box, out);
            } 
            else if (v_process.equals("SulmunMailResultPage")) {                 // 설문 개인 답변 보기
                this.performSulmunMailResultPage(request, response, box, out);
            } 
            else if (v_process.equals("SulmunMailEncoSendPage")) {                 // 설문 독려메일 입력 페이지
                this.performSulmunMailEncoSendPage(request, response, box, out);
            }
            else if (v_process.equals("SulmunMailEncoPreviewPage")) {               // 설문 독려메일 미리보기 페이지
                this.performSulmunMailEncoPreviewPage(request, response, box, out);
            } 
            else if (v_process.equals("SulmunMailSend")) {                    // 설문 메일 보내기 
                this.performSulmunMailSend(request, response, box, out);
            } 
            else if (v_process.equals("SulmunMailEncoSend")) {                   // 설문 독려메일 보내기
                this.performSulmunMailEncoSend(request, response, box, out);
            }   
            else if (v_process.equals("SulmunMailPage")) {                 //대상자설문 사용자 문제보기 (메일)
                this.performSulmunMailPage(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    설문 메일발송 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunMailListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/research/za_SulmunTargetMail_L.jsp";
            
            SulmunTargetMailBean bean = new SulmunTargetMailBean();
            ArrayList list = bean.selectSulmunMailList(box);
            request.setAttribute("SulmunMailList", list);

			SulmunTargetPaperBean bean1 = new SulmunTargetPaperBean();
			DataBox dbox1 = bean1.getPaperData(box);
			request.setAttribute("SulmunPaperData", dbox1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);   
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 메일 입력 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunMailSendPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/research/za_SulmunTargetMail_I.jsp";

			SulmunTargetPaperBean bean1 = new SulmunTargetPaperBean();
			DataBox dbox1 = bean1.getPaperData(box);
			request.setAttribute("SulmunPaperData", dbox1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailSendPage()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 메일 미리보기 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunMailPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
			request.setAttribute("requestbox", box);            
		    String v_return_url = "/learn/admin/research/za_SulmunTargetMailPreview.jsp";
                        
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
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
            throw new Exception("performSulmunMailPreviewPage()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 메일 보내기 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunMailSend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
//            String v_url  = "/servlet/controller.research.SulmunTargetMailServlet";

            SulmunTargetMailBean bean = new SulmunTargetMailBean();
            boolean isOk1 = bean.insertMailSend(box);

            AlertManager alert = new AlertManager();
            String v_msg = "";

            if(isOk1) {
                v_msg = "메일이 발송되었습니다.";
            }
            else {
                v_msg = "메일발송에 실패했습니다.";
            }
            alert.selfClose(out,v_msg);

		 }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailSend()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 개인 답변 보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunMailResultPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{

		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailResultPage()\r\n" + ex.getMessage());
        }
    }


    /**
    설문 독려메일 입력 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
   public void performSulmunMailEncoSendPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/research/za_SulmunTargetMail_I.jsp";

			SulmunTargetPaperBean bean1 = new SulmunTargetPaperBean();
			DataBox dbox1 = bean1.getPaperData(box);
			request.setAttribute("SulmunPaperData", dbox1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		}catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailEncoSendPage()\r\n" + ex.getMessage());
        }
    }

    /**
    설문 독려메일 미리보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunMailEncoPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
			request.setAttribute("requestbox", box);            
		    String v_return_url = "/learn/admin/research/za_SulmunTargetMailPreview.jsp";
                        
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
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
            throw new Exception("performSulmunMailEncoPreviewPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
    설문 독려메일 보내기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunMailEncoSend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            String v_url  = "/servlet/controller.research.SulmunTargetMailServlet";

            SulmunTargetMailBean bean = new SulmunTargetMailBean();
            boolean isOk1 = bean.insertMailSend(box);

            SulmunTargetMailBean bean1 = new SulmunTargetMailBean();

            AlertManager alert = new AlertManager();
            String v_msg = "";

            if(isOk1) {
                v_msg = "메일이 발송되었습니다.";
            }
            else {
                v_msg = "메일발송에 실패했습니다.";
            }
            alert.selfClose(out,v_msg);
		}catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailEncoSend()\r\n" + ex.getMessage());
        }
    }


    /**
    설문 응시 페이지 (메일)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */	
    public void performSulmunMailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/research/zu_SulmunTargetMailPaper_I.jsp";
            
			SulmunTargetPaperBean bean = new SulmunTargetPaperBean();
			ArrayList list1 = bean.selectPaperQuestionExampleList(box);
			request.setAttribute("PaperQuestionExampleList", list1);
            
			box.put("p_subjsel",box.getString("p_subj"));
			box.put("p_upperclass","ALL");
			
			box.put("p_sulpapernm",box.getString("p_sulpapernm"));
			
			DataBox dbox1 = bean.getPaperData(box);               
			request.setAttribute("SulmunPaperData", dbox1);
			request.setAttribute("SulmunRequestData", box); // 설문 기본 데이타
						
			box.remove("p_subjsel");
			box.remove("p_subjsel");
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunMailPage()\r\n" + ex.getMessage());
        }
    }
}