//*********************************************************
//1. ��      ��: ��������
//2. ���α׷���: AccountManagerServlet.java
//3. ��      ��: ��������
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: ���� 2006.08.24
//7. ��      ��:
//**********************************************************
package controller.account;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.account.AccountManagerBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeCourseBean;
import com.credu.system.AdminUtil;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.library.AccountManagerServlet")
public class AccountManagerServlet extends javax.servlet.http.HttpServlet  implements Serializable { 

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
    RequestBox box = null;
    String v_process = "";

    try {
			
			response.setContentType("text/html;charset=euc-kr");
	        out = response.getWriter();

	        box = RequestManager.getBox(request);

//	        String path = request.getServletPath();
			v_process = box.getStringDefault("p_process","selectList");

			System.out.println("p_process = " + v_process);
	        if(ErrorManager.isErrorMessageView()) {
	            box.put("errorout", out);
	        }
	        
            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }	        
			
	        box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			
			if(v_process.equals("BasketList")) { 								//��ٱ��� ��ȸ
	            this.performBasketList(request, response, box, out);
	        }else if(v_process.equals("InsertBasketSubj")) {         			//��ٱ��� ��� - �Ϲݰ���
	            this.performInsertBasketSubj(request, response, box, out);
	        }else if(v_process.equals("InsertBasketPackage")) {         		//��ٱ��� ��� - ��Ű��
	            this.performInsertBasketPackage(request, response, box, out);
	        }else if(v_process.equals("InsertBasketBook")) {         			//��ٱ��� ��� - ����
	            this.performInsertBasketBook(request, response, box, out);
	        }else if(v_process.equals("UpdateBookUnit")) {         			//��ٱ��� �������� - ����
	            this.performUpdateBookUnit(request, response, box, out);
	        }else if(v_process.equals("DeleteBasket")) {         				//��ٱ��� ����
	            this.performDeleteBasket(request, response, box, out);
	        }else if(v_process.equals("MyBillList")) {         					//���� ����Ʈ
	            this.performMyBillList(request, response, box, out);
	        }else if(v_process.equals("MyBillDetail")) {         					//���� ������
	            this.performMyBillDetail(request, response, box, out);
	        }else if(v_process.equals("InsertProposeFree")) {         					//������� ������û
	            this.performInsertProposeFree(request, response, box, out);
	        }
	        

	    }catch(Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	    }
	}
		
	/**
	����Ʈ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performBasketList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);

			AccountManagerBean bean = new AccountManagerBean();
			
			ProposeCourseBean bean2  = new ProposeCourseBean();

	        ArrayList list1 = bean.selectBasketSubj(box);
	        request.setAttribute("SubjList", list1);
	        
	        ArrayList list2 = bean.selectBasketPackage(box);
	        request.setAttribute("PackageList", list2);
	        
	        ArrayList list3 = bean.selectBasketBook(box);
	        request.setAttribute("BookList", list3);

			 			// �̺�Ʈ�� �����üũ
            ArrayList eventLst = bean2.selectChkUser(box);
            request.setAttribute("selectChkUser", eventLst);

	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/account/gu_MyShoppingBasket_L.jsp");
	        rd.forward(request, response);

	        Log.info.println(this, box, "Dispatch to /learn/user/game/account/gu_MyShoppingBasket_L.jsp");

	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectList()\r\n" + ex.getMessage());
	    }
	}
	
	
	/**
	��ٱ��� ��� - �Ϲݰ���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	@SuppressWarnings("unchecked")
	public void performInsertBasketSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.account.AccountManagerServlet";
			String v_msg = "";

			AccountManagerBean bean = new AccountManagerBean();
			int isOk = bean.InsertBasketSubj(box);
			box.put("p_process","BasketList");
			
			AlertManager alert = new AlertManager();                        
			if(isOk == 1) {            	
				v_msg = "confirm.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			} else if (isOk == -1) {
				v_msg = "�̹� ��û�Ǿ����ϴ�";     //�̹� ��û�Ǿ����ϴ�
				alert.alertFailMessage(out, v_msg);
			} else if (isOk == -2) {
				v_msg = "5���� �ʰ��� �� �����ϴ�";     //5���� �ʰ��� �� �����ϴ�
				alert.alertFailMessage(out, v_msg);
			} else {                
				v_msg = "confirm.fail";   
				alert.alertFailMessage(out, v_msg);
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performApproval()\r\n" + ex.getMessage());
		}             
	}
	
	/**
	��ٱ��� ��� - ��Ű������
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	@SuppressWarnings("unchecked")
	public void performInsertBasketPackage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.account.AccountManagerServlet";
			String v_msg = "";

			AccountManagerBean bean = new AccountManagerBean();
			int isOk = bean.InsertBasketPackage(box);
			box.put("p_process","BasketList");
			
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "confirm.ok";       
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
	��ٱ��� ��� - ����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	@SuppressWarnings("unchecked")
	public void performInsertBasketBook(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.account.AccountManagerServlet";
			String v_msg = "";

			AccountManagerBean bean = new AccountManagerBean();
			int isOk = bean.InsertBasketBook(box);
			box.put("p_process","BasketList");
			
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "confirm.ok";       
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
	��ٱ��� ����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	@SuppressWarnings("unchecked")
	public void performUpdateBookUnit(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.account.AccountManagerServlet";
			String v_msg = "";

			AccountManagerBean bean = new AccountManagerBean();
			int isOk = bean.UpdateBookUnit(box);
			box.put("p_process","BasketList");
			
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "update.ok";       
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "update.fail";   
				alert.alertFailMessage(out, v_msg);
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performApproval()\r\n" + ex.getMessage());
		}             
	}
	
	/**
	��ٱ��� ����
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	@SuppressWarnings("unchecked")
	public void performDeleteBasket(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.account.AccountManagerServlet";
			String v_msg = "";

			AccountManagerBean bean = new AccountManagerBean();
			int isOk = bean.DeleteBasket(box);
			box.put("p_process","BasketList");
			
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
			throw new Exception("performApproval()\r\n" + ex.getMessage());
		}             
	}
	
	/**
	������Ȳ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performMyBillList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);

			AccountManagerBean bean = new AccountManagerBean();

	        ArrayList list = bean.selectMyBillList(box);
	        request.setAttribute("MyBillList", list);
	        
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/account/gu_MyBillList_L.jsp");
	        rd.forward(request, response);

	        Log.info.println(this, box, "Dispatch to /learn/user/game/account/gu_MyBillList_L.jsp");

	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectList()\r\n" + ex.getMessage());
	    }
	}
	
	/**
	�����󼼺���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	@SuppressWarnings("unchecked")
	public void performMyBillDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);

			AccountManagerBean bean = new AccountManagerBean();

			DataBox dbox = bean.selectMyBillInfo(box);
	        request.setAttribute("MyBillInfo", dbox);

	        ArrayList list = bean.selectMyBillList(box);
	        request.setAttribute("MyBillList", list);
	        
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/account/gu_MyBillDetail_R.jsp");
	        rd.forward(request, response);

	        Log.info.println(this, box, "Dispatch to /learn/user/game/account/gu_MyBillDetail_R.jsp");

	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectList()\r\n" + ex.getMessage());
	    }
	}
	
	
	/**
	������� ��û
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	**/
	
	@SuppressWarnings("unchecked")
	public void performInsertProposeFree(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.account.AccountManagerServlet";
			String v_msg = "";

			AccountManagerBean bean = new AccountManagerBean();
			int isOk = bean.InsertProposeFree(box);
			box.put("p_process","BasketList");
			
			AlertManager alert = new AlertManager();                        
			if(isOk > 0) {            	
				v_msg = "��û�Ǿ����ϴ�";         //��û�Ǿ����ϴ�
				alert.alertOkMessage(out, v_msg, v_url , box);
			}else {                
				v_msg = "��û�� ������ �߻��Ͽ����ϴ�";     //��û�� ������ �߻��Ͽ����ϴ�
				alert.alertFailMessage(out, v_msg);
			} 
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performApproval()\r\n" + ex.getMessage());
		}             
	}

	
}