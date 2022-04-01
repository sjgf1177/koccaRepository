//**********************************************************
//1. ��      ��:  ȸ������
//2. ���α׷���: OpenForumUserServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 0.1
//6. ��      ��: js.kim
//7. ��      ��:
//
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.KnowBoardUserBean;
import com.credu.homepage.OpenForumUserBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@WebServlet("/servlet/controller.homepage.OpenForumUserServlet")
public class OpenForumUserServlet extends HttpServlet implements Serializable {
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

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            
            
			if (v_process.equals("MainPage")) {   							// ��������
				this.performForumMain(request, response, box, out);     				
			}else if (v_process.equals("SubjectList")) {              		// ������������Ʈ
				box.put("p_types", "A");         
				this.performForumSubjectList(request, response, box, out);    
			}else if (v_process.equals("SubjectFreeList")) {              	// ������������Ʈ
				box.put("p_types", "B");         
				this.performForumSubjectList(request, response, box, out);    
			}else if (v_process.equals("SubjectReview")) {              	// ��� ��õ���� ����Ʈ
				box.put("p_types", "C");         
				this.performForumSubjectList(request, response, box, out);    
			}
			
			 else if(v_process.equals("selectView")){                    	// �󼼺���� �̵��Ҷ�
				this.performSelectView(request, response, box, out);	
			}else if(v_process.equals("insertRecommend")){                 	// ��õ�ϱ�
				this.performInsertRecommend(request, response, box, out);
			}else if(v_process.equals("insertReview")){                 	// ��ڸ��� ��õ
				this.performInsertReview(request, response, box, out);
			}
			
			else if(v_process.equals("commentInsert")) {         			// ������ ����Ҷ�
				this.performInsertcomment(request, response, box, out);
			} else if(v_process.equals("deleteComment")) {           		//  ������ �����Ҷ�
				this.performDeleteComment(request, response, box, out);
			} 
			  
			  else if(v_process.equals("InsertPage")) {                    	// ��� ������
				this.performInsertPage(request, response, box, out);	
			} else if(v_process.equals("insert")) {					        // ����Ҷ�
				this.performInsert(request, response, box, out);		
			} else if(v_process.equals("insertThemePage")) {                // ������ ��� ������
				this.performInsertThemePage(request, response, box, out);	
			} else if(v_process.equals("checkTheme")) {                 	// ������ üũ
				this.performCheckTheme(request, response, box, out);	
			} else if(v_process.equals("insertTheme")) {				    // ������ ����Ҷ�
				this.performInsertTheme(request, response, box, out);		
			} else if(v_process.equals("delete")) {                       	//  �����Ҷ�
				this.performDelete(request, response, box, out);
			} 	 

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


	/**
	  ��������
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performForumMain(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
		  try {     
			  request.setAttribute("requestbox", box);            
			  String v_return_url = "/learn/user/kocca/open/ku_ForumMain_L.jsp";
			  
			  OpenForumUserBean bean = new OpenForumUserBean();
				
			  //�ִ� ��ȸ���� ���� �����´�.
			  //��������            
			  ArrayList list1 = bean.SelectSubjectForumMax(box, "A", "1", "N");    
			  request.setAttribute("SubjectMax", list1);
			  
			  //��������
			  ArrayList list2 = bean.SelectSubjectForumMax(box, "B", "1","N");    
			  request.setAttribute("SubjectFreeMax", list2);
						  
             //��õ����
			  ArrayList list3 = bean.SelectSubjectForumReviewMax(box);    
			  request.setAttribute("SubjectReviewMax", list3);

			  ServletContext sc    = getServletContext();
			  RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			  rd.forward(request, response);      
		  }catch (Exception ex) {           
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performForumMain()\r\n" + ex.getMessage());
		  }
	  }
	  



    /**
    ���� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performForumSubjectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {     
            request.setAttribute("requestbox", box);            
            String v_return_url ="/learn/user/kocca/open/ku_ForumList_L.jsp";
			String v_types = box.getString("p_types");
			
			OpenForumUserBean bean = new OpenForumUserBean();
			
            ArrayList list1 = bean.SelectForumSubjectList(box);    
            request.setAttribute("ListPage", list1);

			if( !v_types.equals("C") ){//�ִ� ��ȸ���� ���� �����´�.
				ArrayList list2 = bean.SelectSubjectForumMax(box, v_types, "5", "Y");    
				request.setAttribute("SubjectMax", list2);
				
	            //�ִ� �ڸ�Ʈ��  ���� �����´�.
				ArrayList list3 = bean.SelectSubjectCommentMax(box, v_types, "5");    
				request.setAttribute("SubjectCommentMax", list3);
        	
			
				// �̴��� ����
		        ArrayList list = bean.SelectTheme(box);    
		        request.setAttribute("monthTheme", list);
			}
			
            ServletContext sc    = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);      
			
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performKnowLatestList()\r\n" + ex.getMessage());
        }
    }



	/**
	   �����󼼺���� �̵��Ҷ�
	   @param request  encapsulates the request to the servlet
	   @param response encapsulates the response from the servlet
	   @param box      receive from the form object
	   @param out      printwriter object
	   @return void
	   */
	   public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		   try {
			   request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			OpenForumUserBean bean = new OpenForumUserBean();
			   DataBox dbox = bean.SelectView(box);
			   request.setAttribute("SelectView", dbox);

			   ArrayList list = bean.selectcommentList(box);
			   request.setAttribute("selecCommentList", list);

      
			   ServletContext sc = getServletContext();
			   RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_ForumView_R.jsp");
			   rd.forward(request, response);

		   }catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, out);
			   throw new Exception("performSelectView()\r\n" + ex.getMessage());
		   }
	   }
	   
	   
	/* ��õ�ϱ�
	   @param request  encapsulates the request to the servlet
	   @param response encapsulates the response from the servlet
	   @param box      receive from the form object
	   @param out      printwriter object
	   @return void
	   */
	   public void performInsertRecommend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		   try {
		      OpenForumUserBean bean = new OpenForumUserBean();
			   int isOk = bean.insertRecommend(box);
			   String v_msg = "";
			   String v_url = "/servlet/controller.homepage.OpenForumUserServlet";
			   box.put("p_process", "selectView");

			   AlertManager alert = new AlertManager();

			   if(isOk > 0) {
				   //v_msg = "��õ�Ǿ����ϴ�.";
				   alert.alertOkMessage(out, "��õ�Ǿ����ϴ�.", v_url , box);
			   }
			   else {
				   //v_msg = "��õ�� �����Ͽ����ϴ�.";
				   alert.alertFailMessage(out, "��õ�� �����Ͽ����ϴ�.");
			   }
		   }catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, out);
			   throw new Exception("performInsertRecommend()\r\n" + ex.getMessage());
		   }
	   }
	   
	   /* ��� ������õ�ϱ�
	   @param request  encapsulates the request to the servlet
	   @param response encapsulates the response from the servlet
	   @param box      receive from the form object
	   @param out      printwriter object
	   @return void
	   */
	   public void performInsertReview(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		   try {
		      OpenForumUserBean bean = new OpenForumUserBean();
			  box.put("Review","Y");
			  
			   int isOk = bean.insertRecommend(box);
			   String v_msg = "";
			   String v_url = "/servlet/controller.homepage.OpenForumUserServlet";
			   box.put("p_process", "SubjectReview");

			   AlertManager alert = new AlertManager();

			   if(isOk > 0) {
				   //v_msg = "��õ�Ǿ����ϴ�.";
				   alert.alertOkMessage(out, "��õ�Ǿ����ϴ�.", v_url , box);
			   }
			   else {
				   //v_msg = "��õ�� �����Ͽ����ϴ�.";
				   alert.alertFailMessage(out, "��õ�� �����Ͽ����ϴ�.");
			   }
		   }catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, out);
			   throw new Exception("performInsertRecommend()\r\n" + ex.getMessage());
		   }
	   }
	   
	/**
	  ������ ����Ҷ�
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performInsertcomment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			OpenForumUserBean bean = new OpenForumUserBean();
			
			  int isOk = bean.insertComment(box);

			  String v_msg = "";
			  String v_url = "/servlet/controller.homepage.OpenForumUserServlet";
			  box.put("p_process", "selectView");

			  AlertManager alert = new AlertManager();

			  if(isOk > 0) {
				  v_msg = "insert.ok";
				  alert.alertOkMessage(out, v_msg, v_url , box);
			  }
			  else {
				  v_msg = "insert.fail";
				  alert.alertFailMessage(out, v_msg);
			  }

			  Log.info.println(this, box, v_msg + " on QnaServlet");
		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performInsertcomment()\r\n" + ex.getMessage());
		  }
	  }

	  /** ��� �����Ҷ�
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performDeleteComment(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			OpenForumUserBean bean = new OpenForumUserBean();
			  int isOk = bean.deleteComment(box);

			  String v_msg = "";
			String v_url = "/servlet/controller.homepage.OpenForumUserServlet";
			  box.put("p_process", "selectView");

			  AlertManager alert = new AlertManager();

			  if(isOk > 0) {
				  v_msg = "delete.ok";
				  alert.alertOkMessage(out, v_msg, v_url , box);
			  }
			  else {
				  v_msg = "delete.fail";
				  alert.alertFailMessage(out, v_msg);
			  }
		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performDeleteComment()\r\n" + ex.getMessage());
		  }
	  }
   
   
	/**
	  ���������
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			  request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			  
				// �̴��� ����
			    OpenForumUserBean bean = new OpenForumUserBean();
		        ArrayList list = bean.SelectTheme(box);    
		        request.setAttribute("monthTheme", list);

			  ServletContext sc = getServletContext();
			  RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_ForumEntry_I.jsp");
			  rd.forward(request, response);
		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		  }
	  }


	   /**
	  ����Ҷ�
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			  OpenForumUserBean bean = new OpenForumUserBean();
			  
			    int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
	            if (tabseq == 0) {
	                /*------- �Խ��� �з��� ���� �κ� ���� -----*/
	                box.put("p_type", "FD");
	                box.put("p_grcode", "0000000");
	                box.put("p_subj", "0000000000");
	                box.put("p_year", "0000");
	                box.put("p_subjseq", "0000");
	                /*----------------------------------------*/
	                tabseq = bean.selectTableseq(box);
	                if (tabseq == 0) {
	                    String msg = "�Խ��������� �����ϴ�.";
	                    AlertManager.historyBack(out, msg);
	                }
	                box.put("p_tabseq", String.valueOf(tabseq));
	            }
			  int isOk = bean.insertKnowBoard(box);

			  String v_msg = "";
			  String v_url = "/servlet/controller.homepage.OpenForumUserServlet";
			  String v_types = box.getString("p_types");       
			  
			  if ( "A".equals(v_types)){
				box.put("p_process", "SubjectList");
			  }
			  else
			  {
				box.put("p_process", "SubjectFreeList");
			  }
	
			  AlertManager alert = new AlertManager();

			  if(isOk > 0) {
				  v_msg = "insert.ok";
				  alert.alertOkMessage(out, v_msg, v_url , box);
			  }
			  else {
				  v_msg = "insert.fail";
				  alert.alertFailMessage(out, v_msg);
			  }

		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performInsert()\r\n" + ex.getMessage());
		  }
	  }
	  
	  /**
	   ������ ��� ��������
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performInsertThemePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			  request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			  // ������ ��������
				OpenForumUserBean bean = new OpenForumUserBean();
	            ArrayList list1 = bean.SelectTheme(box);    
	            request.setAttribute("ListPage", list1);

			  ServletContext sc = getServletContext();
			  RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_InsertTheme.jsp");
			  rd.forward(request, response);
		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		  }
	  }
	  
	  /**
	   ������ üũ
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performCheckTheme(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			    request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
				String v_title = box.getString("p_title");
				
			    // ������ ��������
				OpenForumUserBean bean = new OpenForumUserBean();
	            int isOk = bean.checkTheme(box);  
				String v_tmpmonth = box.getString("p_tmpmonth");
	            request.setAttribute("isOk", String.valueOf(isOk));
				
				box.put("isOk",String.valueOf(isOk)); // count �� ���� ������ 
				box.put("p_tmpmonth",v_tmpmonth);
				box.put("p_title",v_title);
				
//				if ( isOk == 1 ) {  // �� ���� ����� ������
//			        ArrayList list = bean.SelectTheme(box);  
//			        request.setAttribute("monthTheme", list);
//				}
				
				request.setAttribute("requestbox", box);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/open/ku_InsertTheme.jsp");
			    rd.forward(request, response);

		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		  }
	  }


	   /**
	  ����Ҷ�
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performInsertTheme(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			  OpenForumUserBean bean = new OpenForumUserBean();
			  int isOk = bean.insertTheme(box);
			  
			  String v_msg = "";
			  String v_url = "/servlet/controller.homepage.OpenForumUserServlet?p_process=insertThemePage";

			  AlertManager alert = new AlertManager();
			  
			  box.put("isOk",String.valueOf(isOk));
			  
			  if(isOk > 0) {
				  box.put("p_insert","Y");
				  alert.alertOkMessage(out, v_msg, v_url , box);
			  }
			  else {
				  v_msg = "insert.fail";
				  alert.alertFailMessage(out, v_msg);
			  }

		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performInsert()\r\n" + ex.getMessage());
		  }
	  }

	  /**�����Ҷ�
	  @param request  encapsulates the request to the servlet
	  @param response encapsulates the response from the servlet
	  @param box      receive from the form object
	  @param out      printwriter object
	  @return void
	  */
	  public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		  try {
			  KnowBoardUserBean bean = new KnowBoardUserBean();
			  int isOk = bean.deleteBoard(box);

			  String v_msg = "";
			  String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
			  box.put("p_process", "ListPage");

			  AlertManager alert = new AlertManager();

			  if(isOk > 0) {
				  v_msg = "delete.ok";
				  alert.alertOkMessage(out, v_msg, v_url , box);
			  }
			  else {
				  v_msg = "delete.fail";
				  alert.alertFailMessage(out, v_msg);
			  }

			  Log.info.println(this, box, v_msg + " on HomePageQNAServlet");
		  }catch (Exception ex) {
			  ErrorManager.getErrorStackTrace(ex, out);
			  throw new Exception("performDelete()\r\n" + ex.getMessage());
		  }
	  }
   
   
    /**
    ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

            KnowBoardUserBean bean = new KnowBoardUserBean();
            DataBox dbox = bean.SelectView(box);
            request.setAttribute("SelectView", dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_KnowBoard_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            KnowBoardUserBean bean = new KnowBoardUserBean();
			
            int isOk = bean.updateKnowBoard(box);
            String v_msg = "";
            String v_url = "/servlet/controller.homepage.KnowBoardUserServlet";
            box.put("p_process", "ListPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }       
    
}