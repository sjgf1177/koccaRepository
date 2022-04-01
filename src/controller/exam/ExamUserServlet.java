//**********************************************************
//1. ��      ��: �� ����
//2. ���α׷���: ExamUserServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 0.1
//6. ��      ��: 
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
			//System.out.println("��   ExamUserServlet : "+v_process);			

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if(box.getSession("userid").equals("")){
            	request.setAttribute("tUrl",request.getRequestURI());
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }

            if (v_process.equals("ExamUserListPage")) {                      //�򰡻���� ������ ����Ʈ
                this.performExamUserListPage(request, response, box, out);
            } 
            else if (v_process.equals("ExamUserPaperListPage")) {                 //�򰡻���� ��������
                this.performExamUserPaperListPage(request, response, box, out);
            } 
            else if (v_process.equals("ExamRetryListPage")) {                 //�򰡻���� ��������
                this.performExamRetryListPage(request, response, box, out);
            } 
           else if (v_process.equals("ExamUserResultInsert")) {                 //���ڰ� �� ����
                this.performExamUserResultInsert(request, response, box, out);
            }
            else if (v_process.equals("ExamUserPaperResult")) {                 //�򰡰��κ� �򰡰�� ����()
                this.performExamUserPaperResult(request, response, box, out);
            }
            else if (v_process.equals("ExamUserPaperResultTemp")) {                 //�򰡰��κ� �򰡰�� ����(TEMP) (2005.10)
                this.performExamUserPaperResultTemp(request, response, box, out);
            }                        
            else if (v_process.equals("ExamUserPaperClosed")) {                 //�򰡰��κ� �򰡰�� ����
                this.performExamUserPaperClosed(request, response, box, out);
            }
            else if (v_process.equals("ExamUserRetrySetPage")) {                 //�� ������ ��ȸ ���� ������(2005.08.20)
                this.performExamUserRetrySetPage(request, response, box, out);
            }
            else if (v_process.equals("ExamUserRetry")) {                        //�� ������ ��ȸ ���� (2005.08.20)
                this.performExamUserRetrySet(request, response, box, out);
            }            
            else if (v_process.equals("ExamUserReRatingSelect")) {               //��ä�� ��..
                this.performExamUserReRatingSelect(request, response, box, out);
            }            
            else if (v_process.equals("ExamUserReRatingInsert")) {               //ID�� ��ä�� ó��..
                this.performExamUserReRatingInsert(request, response, box, out);
            }
            else if (v_process.equals("ExamUserReRatingAllInsert")) {               //�������� ��ä�� ó��..
                this.performExamUserReRatingAllInsert(request, response, box, out);
            }
            
            

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �򰡻���� ������ ����Ʈ
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
    �н�â����  ���ϱ�
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
    �Ⱦ��� 
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
				v_msg = "�̹� ������ �����Դϴ�.";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 44) {            
				v_msg = "������ ���ؿ� �̴��Ͽ� ������� �ʾҽ��ϴ�. ��뺸�� ȯ�ް����� 30�� �̻� ����Ͽ��� ����˴ϴ�.";       
				alert.alertFailMessage(out, v_msg);
			} else if(isOk == 98) {            
				v_msg = "�н����� ���� Object �Ǵ� ������ �򰡰� �����Ƿ� ������� �ʽ��ϴ�";       
				alert.alertFailMessage(out, v_msg);
			}else if(isOk == 97) {
				v_msg = "���� �н����� �л��ź��� �ƴϹǷ� �򰡰���� �������� ������ ����� Ȯ���Ͻ� �� �����ϴ�.";       
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
    �򰡹����� ����Ҷ�(����)
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
			    bean.InsertResultTemp(box);            // �ӽð�����̺� ����Ÿ ���
			    box.put("p_process", "ExamUserPaperResultTemp");  // �ӽð�����̺� ����Ÿ ����
			}else {
				box.put("p_process", "ExamUserPaperClosed");
				box.put("p_end", "0");
			}
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				v_msg = "����Ǿ����ϴ�.";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 99) {            	
				v_msg = "�̹� ������ �����Դϴ�.";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if(isOk == 44) {            
				v_msg = "������ ���ؿ� �̴��Ͽ� ������� �ʾҽ��ϴ�. ��뺸�� ȯ�ް����� 30�� �̻� ����Ͽ��� ����˴ϴ�.";       
				alert.alertFailMessage(out, v_msg);
			} else if(isOk == 98) {            
				v_msg = "�н����� ���� Object �Ǵ� ������ �򰡰� �����Ƿ� ������� �ʽ��ϴ�";       
				alert.alertFailMessage(out, v_msg);
			}else if(isOk == 97) {
				v_msg = "���� �н����� �л��ź��� �ƴϹǷ� �򰡰���� �������� ������ ����� Ȯ���Ͻ� �� �����ϴ�.";       
				alert.alertFailMessage(out, v_msg);
			}else {                
				v_msg = "���⿡ �����߽��ϴ�.";   
				alert.alertFailMessage(out, v_msg);   
			}         

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserResultInsert()\r\n" + ex.getMessage());
        }
    } 

    /**
    ��ä�� ������ ����Ʈ
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
            
            if(v_userid.equals("")) {     // ���� ��ä��
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
    id�� ��ä��
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
				v_msg = "��ä�� �Ǿ����ϴ�.";       
				box.put("p_end", "0");
				box.put("p_reloadlist", "true");				
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "��ä���� �����߽��ϴ�.";   			
				alert.alertFailMessage(out, v_msg);   
			}         

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserReRatingInsert()\r\n" + ex.getMessage());
        }
    }     
    
    /**
    ������ ��ä��
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
				v_msg = "��ä�� �Ǿ����ϴ�.";       
				box.put("p_end", "0");
				box.put("p_reloadlist", "true");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "��ä���� �����߽��ϴ�.";   			
				alert.alertFailMessage(out, v_msg);   
			}         

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserReRatingAllInsert()\r\n" + ex.getMessage());
        }
    }  
    
    /**
    �򰡻���� ������ ����Ʈ
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
            String v_return_url = "/learn/user/exam/zu_ExamPaperResult_L.jsp"; //����.
            
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
    �򰡻���� ������ ����Ʈ(TEMP)
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
            String v_return_url = "/learn/user/exam/zu_ExamPaperResultTemp_L.jsp"; //����.
            
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
    �� ����� ������ ����Ʈ
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
    �� ������ Ƚ�� ���� ������
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
    �� ������ Ƚ�� ����
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
				v_msg = "���� �Ǿ����ϴ�.";       
				box.put("p_end", "0");
				box.put("p_reloadlist", "true");
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "������ �����߽��ϴ�.";   			
				alert.alertFailMessage(out, v_msg);   
			}   

         }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExamUserResultInsert()\r\n" + ex.getMessage());
        }
    } 
}