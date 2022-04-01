//**********************************************************
//  1. 제      목: 과정관리 서블릿
//  2. 프로그램명 : CPSulServlet.java
//  3. 개      요: 과정관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 9.  10
//  7. 수     정1:
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPSulmunBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunCpUserBean;
import com.credu.research.SulmunCpuserPaperBean;
import com.credu.system.AdminUtil;

/**
*과정관리
*<p>제목:CPSulServlet.java</p>
*<p>설명:과정관리 서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

@WebServlet("/servlet/controller.cp.CPSulServlet")
public class CPSulServlet extends javax.servlet.http.HttpServlet {
    
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
        RequestBox box = null;
        String v_process = "";
        
        try {

            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            System.out.println("v_process====>>>>>"+v_process);

			if(v_process.equals("")){
				box.put("v_process","listPage");
				v_process = "listPage";
			}
			
			if (!AdminUtil.getInstance().checkRWRight("CPSulServlet", v_process, out, box)) {
				return; 
			}
			
			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("listPage")) {   //과정목록조회 
                this.performList(request, response, box, out);
            }
			else if (v_process.equals("SulmunUserPaperListPage")) {                 //설문응시 사용자 문제보기
                this.performSulmunUserPaperListPage(request, response, box, out);
            } 
            
            else if (v_process.equals("SulmunCpResultInsert")) {                 //설문응시결과 저장
                this.performSulmunCpResultInsert(request, response, box, out);
            }
            
            else if (v_process.equals("testpage")) { // text 페이지 출력
                this.performSulmunCpTestPage(request, response, box, out);
            }
            else if (v_process.equals("excelDown")) { //레포팅출력
                this.performSulmunCpReportPage(request, response, box, out);
            }
            
			
			
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

	/**
	외주업체 설문리스트
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            

            CPSulmunBean cpCourse = new CPSulmunBean();
            
            ArrayList list = cpCourse.selectCourseList(box);
            
            request.setAttribute("selectCourseList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_sul_l.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPSulServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    
    

	
    /**
    설문 사용자 문제지 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/cp/admin/za_sulPaper_l.jsp";

            SulmunCpuserPaperBean bean = new SulmunCpuserPaperBean();
            ArrayList list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);      
            
            String v_replycnt = bean.getCpReplaycnt(box);

            box.put("p_subjsel",box.getString("p_subj"));
            box.put("p_upperclass","ALL");
            box.put("p_replycnt", v_replycnt);
            
            DataBox dbox1 = bean.getPaperData(box);               
            request.setAttribute("SulmunPaperData", dbox1);  //2005.08.22 by정은년 jsp에서 다 값 받아옴.
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
    설문 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunCpResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
           String v_url  = "/servlet/controller.cp.CPSulServlet";

            SulmunCpUserBean bean = new SulmunCpUserBean();
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            box.put("p_process", "SulmunUserPaperListPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk == 2) {
              v_msg = "설문에 응답해 주셔서 감사합니다.";
              alert.alertOkMessage(out, v_msg, v_url , box);
            }else if(isOk == 1){
		      v_msg = "설문 기간 이전입니다.";
              alert.alertFailMessage(out, v_msg);
            }else if (isOk == 3){
		      v_msg = "설문 기간이 완료되었습니다.";
              alert.alertFailMessage(out, v_msg);
            }else{
		      v_msg = "이미 해당 설문에 응답하셨습니다.";
              alert.alertFailMessage(out, v_msg);			
			} 
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunCpResultInsert()\r\n" + ex.getMessage());
        }
    } 
    
    
    /**
	테스트페이지 출력
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSulmunCpTestPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_return_url = "/cp/admin/za_cpSulResult_L.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunCpTestPage()\r\n" + ex.getMessage());
		}            
	}
	
	
	
	/**
	레포팅 출력
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSulmunCpReportPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_return_url = "/cp/admin/za_cpSulResult_E.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSulmunCpReportPage()\r\n" + ex.getMessage());
		}            
	}
    
    
    
}