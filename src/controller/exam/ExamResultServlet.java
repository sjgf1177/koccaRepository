//**********************************************************
//1. 제      목: 평가 대상자관리
//2. 프로그램명: ExamResultServlet.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: 
//**********************************************************

package controller.exam;

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

import com.credu.exam.ExamResultBean;
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
@WebServlet("/servlet/controller.exam.ExamResultServlet")
public class ExamResultServlet extends HttpServlet implements Serializable {
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
			//System.out.println("평가   ExamResultServlet : "+v_process);			

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ExamResultServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ExamResultListPage")) {                    //평가 대상자 리스트
                this.performExamResultListPage(request, response, box, out);
            } 
            else if(v_process.equals("ResultListExcel")){  
	              this.performResultListExcel(request, response, box, out);
            }
            else if (v_process.equals("ExamUserPaperResult")) {              // 평가 응시자 개인별 등록 페이지로 이동 (검색후)
                this.performExamUserPaperResult(request, response, box, out);
            } 
            
            
            
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    평가 대상자 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamResultListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/exam/za_ExamResult_L.jsp";

            ExamResultBean bean = new ExamResultBean();
            
						ArrayList list1 = bean.SelectReaultList(box); 
						request.setAttribute("ExamResultList", list1);
						
						Vector v1 = bean.SelectResultAverage(box);               
						request.setAttribute("ExamResultAverage", v1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamResultListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    평가 대상자 엑셀보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
		public void performResultListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            //String v_return_url = "/learn/admin/exam/za_ResultList_E.jsp";
            String v_return_url = "/learn/admin/exam/za_ExamResult_E.jsp";
            
            ExamResultBean bean = new ExamResultBean();            

            ArrayList list1 = bean.SelectReaultList(box); 
			request.setAttribute("ExamResultList", list1);
			
			Vector v1 = bean.SelectResultAverage(box);               
			request.setAttribute("ExamResultAverage", v1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamResultListPage()\r\n" + ex.getMessage());
        }
    }
    
    
    /**
    평가 사용자 문제지 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
/*            request.setAttribute("requestbox", box);            
            String v_return_url = "/jsp/admin/etest/za_ExamIndividualPaperResult_L.jsp";
            
            ExamUserBean bean = new ExamUserBean();
            ArrayList list = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
  */      }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserPaperResult()\r\n" + ex.getMessage());
        }
    }

}