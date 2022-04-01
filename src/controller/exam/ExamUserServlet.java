//**********************************************************
//1. 제      목: 평가 관리
//2. 프로그램명: ExamUserServlet.java
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

import com.credu.exam.ExamPaperBean;
import com.credu.exam.ExamResultBean;
import com.credu.exam.ExamUserBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.exam.ExamUserServlet")
public class ExamUserServlet extends HttpServlet implements Serializable {
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
        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
			//System.out.println("평가   ExamUserServlet : "+v_process);			

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if(box.getSession("userid").equals("")){
            	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }

            if (v_process.equals("ExamUserListPage")) {                      //평가사용자 문제지 리스트
                this.performExamUserListPage(request, response, box, out);
            } 
            else if (v_process.equals("ExamUserPaperListPage")) {                 //평가사용자 문제보기
                this.performExamUserPaperListPage(request, response, box, out);
            } 
            else if (v_process.equals("ExamRetryListPage")) {                 //평가사용자 문제보기
                this.performExamRetryListPage(request, response, box, out);
            } 
           else if (v_process.equals("ExamUserResultInsert")) {                 //평가자가 평가 제출
                this.performExamUserResultInsert(request, response, box, out);
            }
            else if (v_process.equals("ExamUserPaperResult")) {                 //평가개인별 평가결과 보기()
                this.performExamUserPaperResult(request, response, box, out);
            }
            else if (v_process.equals("ExamUserPaperResultTemp")) {                 //평가개인별 평가결과 보기(TEMP) (2005.10)
                this.performExamUserPaperResultTemp(request, response, box, out);
            }                        
            else if (v_process.equals("ExamUserPaperClosed")) {                 //평가개인별 평가결과 보기
                this.performExamUserPaperClosed(request, response, box, out);
            }
            else if (v_process.equals("ExamUserRetrySetPage")) {                 //평가 재응시 기회 수정 페이지(2005.08.20)
                this.performExamUserRetrySetPage(request, response, box, out);
            }
            else if (v_process.equals("ExamUserRetry")) {                        //평가 재응시 기회 수정 (2005.08.20)
                this.performExamUserRetrySet(request, response, box, out);
            }            
            else if (v_process.equals("ExamUserReRatingSelect")) {               //재채점 뷰..
                this.performExamUserReRatingSelect(request, response, box, out);
            }            
            else if (v_process.equals("ExamUserReRatingInsert")) {               //ID별 재채점 처리..
                this.performExamUserReRatingInsert(request, response, box, out);
            }
            else if (v_process.equals("ExamUserReRatingAllInsert")) {               //과정별별 재채점 처리..
                this.performExamUserReRatingAllInsert(request, response, box, out);
            }
            
            

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    평가사용자 문제지 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
/**            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/exam/zu_ExamUserList_L.jsp";
            
            ExamUserBean bean = new ExamUserBean();
            ArrayList list1 = bean.SelectUserList(box);
            request.setAttribute("ExamUserList", list1);
            
            ArrayList list2 = bean.SelectUserResultList(box);
            request.setAttribute("ExamUserResultList", list2);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
    */    }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUserListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    학습창에서  평가하기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
    		request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/exam/zu_ExamUserPaper_I.jsp";
                     
			ExamPaperBean bean = new ExamPaperBean();
			ArrayList list1 = bean.SelectPaperQuestionExampleList(box); 

			request.setAttribute("PaperQuestionExampleList", list1);

			box.put("p_subjsel",box.getString("p_subj"));
			box.put("p_upperclass","ALL");
			DataBox dbox1 = bean.getPaperData(box);               
			request.setAttribute("ExamPaperData", dbox1);
			box.remove("p_subjsel");
			box.remove("p_subjsel");

			Vector v1 = bean.SelectQuestionList(box);               
			request.setAttribute("ExamQuestionData", v1);

		  	ServletContext sc = getServletContext();
		  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  	rd.forward(request, response);

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserPaperListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    안쓰임 
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */    
    public void performExamRetryListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {    
   /*         
    		request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/exam/zu_ExamUserPaperRetry_L.jsp";
			String v_url  = "/servlet/controller.exam.ExamUserServlet";

			ExamUserBean bean = new ExamUserBean();
			String v_return = bean.InsertRetry(box);

			StringTokenizer st = new StringTokenizer(v_return, ",");
			
			int retry = Integer.parseInt(st.nextToken());

			if (retry == 0){
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
			
			} else if (retry == 1){
           
			int isOk = Integer.parseInt(st.nextToken());

			String v_msg = "";			
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				v_msg = "insert.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 99) {            	
				v_msg = "이미 응시한 시험입니다.";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 44) {            
				v_msg = "점수가 기준에 미달하여 저장되지 않았습니다. 고용보험 환급과정은 30점 이상 취득하여야 저장됩니다.";       
				alert.alertFailMessage(out, v_msg);
			} else if(isOk == 98) {            
				v_msg = "학습하지 않은 Object 또는 미응시 평가가 있으므로 저장되지 않습니다";       
				alert.alertFailMessage(out, v_msg);
			}else if(isOk == 97) {
				v_msg = "현재 학습중인 학생신분이 아니므로 평가결과를 저장하지 않으며 결과를 확인하실 수 없습니다.";       
				alert.alertFailMessage(out, v_msg);
			}else {                
				v_msg = "insert.fail";   
				alert.alertFailMessage(out, v_msg);   
			}         

			}
 */                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamRetryListPage()\r\n" + ex.getMessage());
        }
    }

     /**
    평가문제지 등록할때(제출)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{

			String v_url  = "/servlet/controller.exam.ExamUserServlet";
           
			ExamUserBean bean = new ExamUserBean();
			int isOk = bean.InsertResult(box);
			String v_msg = "";
			
			String v_isopenanswer = box.getString("p_isopenanswer");
			String v_userretry = box.getString("p_userretry");
			
			if(v_isopenanswer.equals("Y") && ("1".equals(v_userretry) || "0".equals(v_userretry))) {
			    //box.put("p_process", "ExamUserPaperResult");
			    bean.InsertResultTemp(box);            // 임시결과테이블 데이타 등록
			    box.put("p_process", "ExamUserPaperResultTemp");  // 임시결과테이블 데이타 보기
			}else {
				box.put("p_process", "ExamUserPaperClosed");
				box.put("p_end", "0");
			}
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				v_msg = "제출되었습니다.";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 99) {            	
				v_msg = "이미 응시한 시험입니다.";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 44) {            
				v_msg = "점수가 기준에 미달하여 저장되지 않았습니다. 고용보험 환급과정은 30점 이상 취득하여야 저장됩니다.";       
				alert.alertFailMessage(out, v_msg);
			} else if(isOk == 98) {            
				v_msg = "학습하지 않은 Object 또는 미응시 평가가 있으므로 저장되지 않습니다";       
				alert.alertFailMessage(out, v_msg);
			}else if(isOk == 97) {
				v_msg = "현재 학습중인 학생신분이 아니므로 평가결과를 저장하지 않으며 결과를 확인하실 수 없습니다.";       
				alert.alertFailMessage(out, v_msg);
			}else {                
				v_msg = "제출에 실패했습니다.";   
				alert.alertFailMessage(out, v_msg);   
			}         

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserResultInsert()\r\n" + ex.getMessage());
        }
    } 

    /**
    재채점 문제지 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserReRatingSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);   
            String v_return_url = "";
            String v_userid = box.getString("p_userid");    
            
            if(v_userid.equals("")) {     // 과정 재채점
                ExamUserBean bean = new ExamUserBean();
                ArrayList list = bean.SelectPaperResult(box);
                request.setAttribute("PaperResult", list);
    			
                v_return_url = "/learn/admin/exam/za_ExamResultReRatingAll_L.jsp";
            }else{
                ExamUserBean bean = new ExamUserBean();
                ArrayList list = bean.SelectUserPaperResult(box);
                request.setAttribute("UserPaperResult", list);
    
                ArrayList list2 = bean.SelectUserPaperResult2(box);
                request.setAttribute("UserPaperResult2", list2);
    
                ExamResultBean bean1 = new ExamResultBean();
    			Vector v1 = bean1.SelectResultAverage2(box);               
    			request.setAttribute("ExamResultAverage", v1);
			            
                v_return_url = "/learn/admin/exam/za_ExamResultReRating_L.jsp";
            }
             
		  	ServletContext sc = getServletContext();
		  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
		  	rd.forward(request, response);
			
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserReRatingSelect()\r\n" + ex.getMessage());
        }
    }
    
    
    /**
    id별 재채점
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserReRatingInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.exam.ExamUserServlet";
			String v_msg = "";
			AlertManager alert = new AlertManager();   			
			ExamUserBean bean = new ExamUserBean();
			
			int isOk = bean.InsertReRating(box);
			box.put("p_process", "ExamUserReRatingSelect");          
			           
			if(isOk == 1) {            	
				v_msg = "재채점 되었습니다.";       
				box.put("p_end", "0");
				box.put("p_reloadlist", "true");				
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "재채점에 실패했습니다.";   			
				alert.alertFailMessage(out, v_msg);   
			}         

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserReRatingInsert()\r\n" + ex.getMessage());
        }
    }     
    
    /**
    과정별 재채점
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserReRatingAllInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
			String v_url  = "/servlet/controller.exam.ExamUserServlet";
			String v_msg = "";
			AlertManager alert = new AlertManager();   			
			ExamUserBean bean = new ExamUserBean();
			
			int isOk = bean.InsertAllReRating(box);
			box.put("p_process", "ExamUserReRatingSelect");          
			           
			if(isOk == 1) {            	
				v_msg = "재채점 되었습니다.";       
				box.put("p_end", "0");
				box.put("p_reloadlist", "true");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "재채점에 실패했습니다.";   			
				alert.alertFailMessage(out, v_msg);   
			}         

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserReRatingAllInsert()\r\n" + ex.getMessage());
        }
    }  
    
    /**
    평가사용자 문제지 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/exam/zu_ExamPaperResult_L.jsp"; //보임.
            
						if(box.getInt("p_flag")==1){
						    v_return_url = "/learn/user/exam/zu_ExamPaperResult_L2.jsp";
						}

            ExamUserBean bean = new ExamUserBean();
            ArrayList list = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", list);

            ArrayList list2 = bean.SelectUserPaperResult2(box);
            request.setAttribute("UserPaperResult2", list2);

            ExamResultBean bean1 = new ExamResultBean();
						Vector v1 = bean1.SelectResultAverage2(box);               
						request.setAttribute("ExamResultAverage", v1);
		     
				  	ServletContext sc = getServletContext();
				  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
				  	rd.forward(request, response);
			
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserPaperResult()\r\n" + ex.getMessage());
        }
    }


    /**
    평가사용자 문제지 리스트(TEMP)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserPaperResultTemp(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/exam/zu_ExamPaperResultTemp_L.jsp"; //보임.
            
            ExamUserBean bean = new ExamUserBean();
            ArrayList list = bean.SelectUserPaperResultTemp(box);
            request.setAttribute("UserPaperResult", list);

            ArrayList list2 = bean.SelectUserPaperResult2Temp(box);
            request.setAttribute("UserPaperResult2", list2);

            //ExamResultBean bean1 = new ExamResultBean();
						//Vector v1 = bean1.SelectResultAverage2(box);               
						//request.setAttribute("ExamResultAverage", v1);
		     
				  	ServletContext sc = getServletContext();
				  	RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
				  	rd.forward(request, response);
			
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserPaperResultTemp()\r\n" + ex.getMessage());
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
    public void performExamUserPaperClosed(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/user/exam/zu_ExamUserPaper_I2.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
			
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserPaperClosed()\r\n" + ex.getMessage());
        }
    }
    
    /**
    평가 재응시 횟수 변경 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performExamUserRetrySetPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url = "/learn/admin/exam/za_ExamResultRetry_U.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
			
         }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserRetrySetPage()\r\n" + ex.getMessage());
        }
    }
    
     /**
    평가 재응시 횟수 변경
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performExamUserRetrySet(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{

			String v_url  = "/servlet/controller.exam.ExamUserServlet";
			String v_msg = "";
			AlertManager alert = new AlertManager();   			
			ExamUserBean bean = new ExamUserBean();
			
			int isOk = bean.UpdateUserRetry(box);
			box.put("p_process", "ExamUserRetrySetPage");          
			           
			if(isOk == 1) {            	
				v_msg = "수정 되었습니다.";       
				box.put("p_end", "0");
				box.put("p_reloadlist", "true");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "수정에 실패했습니다.";   			
				alert.alertFailMessage(out, v_msg);   
			}   

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserResultInsert()\r\n" + ex.getMessage());
        }
    } 
}