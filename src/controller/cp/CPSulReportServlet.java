//**********************************************************
//  1. ��      ��: �������� ����
//  2. ���α׷��� : CPSulReportServlet.java
//  3. ��      ��: �������� ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ��â�� 2005. 9.  10
//  7. ��      ��: �̳��� 05.11.19 _ ����� �޼ҵ� ����
//**********************************************************
package controller.cp;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CPSulmunReportBean;
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
*��������
*<p>����:CPSulReportServlet.java</p>
*<p>����:�������� ����</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ��â��
*@version 1.0
*/

@WebServlet("/servlet/controller.cp.CPSulReportServlet")
public class CPSulReportServlet extends javax.servlet.http.HttpServlet {
    
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
				box.put("v_process","listPageReport");
				v_process = "listPageReport";
			}
			
			if (!AdminUtil.getInstance().checkRWRight("CPSulReportServlet", v_process, out, box)) {
				return; 
			}
			
			System.out.println("v_process : " + v_process);
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
                       
            if(ErrorManager.isErrorMessageView()) {     
                box.put("errorout", out);
            }

            if(v_process.equals("insertPage")) {             //���� ����� ��������
                this.performInsertPage(request, response, box, out);
            }

            else if(v_process.equals("updatePage")) {   //������ ��������
                this.performUpdatePage(request, response, box, out);
            }
            else if(v_process.equals("update")) {   //db����ó��
                this.performUpdate(request, response, box, out);
            }
            else if(v_process.equals("CpApproval")) {       //cp��ü �������
				this.performCpApproval(request, response, box, out);
			}
			
			else if(v_process.equals("CpApproval1")) {       //cp��ü �������
				this.performCpApproval1(request, response, box, out);
			}
			
			else if (v_process.equals("SulmunUserPaperListPage")) {                 //�������� ����� ��������
                this.performSulmunUserPaperListPage(request, response, box, out);
            } 
            
            else if (v_process.equals("SulmunCpResultInsert")) {                 
                this.performSulmunCpResultInsert(request, response, box, out);
            }
            
            else if(v_process.equals("listPageReport")) {   //���������ȸ
                this.performList(request, response, box, out);
            }			
			
			
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

	/**
	���־�ü ��������Ʈ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {        
            request.setAttribute("requestbox", box);            
            
            CPSulmunReportBean csrbean = new CPSulmunReportBean();
            
            //ArrayList list = csrbean.selectCourseList(box);
            
            //request.setAttribute("selectCourseList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/admin/za_sulReport_l.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "/servlet/controller.cp.CPSulReportServlet");
                    
        }catch (Exception ex) {            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    
	/**
	�����ڵ� ��� PAGE - ���̹�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_return_url = "/cp/admin/za_cpsubject_i.jsp";

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}            
	}
	
    
	/**
	�������� ���������� - ���̹�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			request.setAttribute("requestbox", box);            
			String v_url = "/cp/admin/za_cpcourse_u.jsp";
			//String v_url = "/servlet/controller.cp.CPSulReportServlet";
            box.put("p_process", "");
            
			// ������ : 05.11.19 ������ : �̳��� 
//			CPSulmunBean cpCourse = new CPSulmunBean();
			CPSulmunReportBean cpCourse = new CPSulmunReportBean();
			DataBox dbox = cpCourse.SelectSubjectData(box);
			request.setAttribute("SubjectData", dbox);
			
			DataBox dbox1 = cpCourse.SelectCpParamData(box);
			request.setAttribute("CpParamData", dbox1);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);                                       
		}catch (Exception ex) {           
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}            
	}


	/**
	�������� ���� - ���̹�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.cp.CPSulReportServlet";
			// ������ : 05.11.19 ������ : �̳��� 
//			CPSulmunBean cpCourse = new CPSulmunBean();
			CPSulmunReportBean cpCourse = new CPSulmunReportBean();
			int isOk = cpCourse.UpdateSubject(box);
            
			String v_msg = "";
			box.put("p_process", "");
			
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
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}

    /**
	cp��ü �������
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCpApproval(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.cp.CPSulReportServlet";
			// ������ : 05.11.19 ������ : �̳��� 
//			CPSulmunBean cpCourse = new CPSulmunBean();
			CPSulmunReportBean cpCourse = new CPSulmunReportBean();
			int isOk = cpCourse.UpdateCpApproval(box);
            
			String v_msg = "";
			box.put("p_process", "");
			
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
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}
	
	/**
	cp��ü �������1
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performCpApproval1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {   
		try{                
			String v_url  = "/servlet/controller.cp.CPSulReportServlet";
			// ������ : 05.11.19 ������ : �̳��� 
//			CPSulmunBean cpCourse = new CPSulmunBean();
			CPSulmunReportBean cpCourse = new CPSulmunReportBean();
			int isOk = cpCourse.UpdateCpApproval1(box);
            
			String v_msg = "";
			box.put("p_process", "");
			
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
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}            
	}
	
	
    /**
    ���� ����� ������ ����Ʈ
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
            request.setAttribute("SulmunPaperData", dbox1);  //2005.08.22 by������ jsp���� �� �� �޾ƿ�.
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
    �¶����׽�Ʈ ������ ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSulmunCpResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
           String v_url  = "/servlet/controller.cp.CPSulReportServlet";

            SulmunCpUserBean bean = new SulmunCpUserBean();
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";
            box.put("p_process", "SulmunUserPaperListPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk == 2) {
              v_msg = "������ ������ �ּż� �����մϴ�.";
              alert.alertOkMessage(out, v_msg, v_url , box);
            }else if(isOk == 1){
		      v_msg = "���� �Ⱓ �����Դϴ�.";
              alert.alertFailMessage(out, v_msg);
            }else if (isOk == 3){
		      v_msg = "���� �Ⱓ�� �Ϸ�Ǿ����ϴ�.";
              alert.alertFailMessage(out, v_msg);
            }else{
		      v_msg = "�̹� �ش� ������ �����ϼ̽��ϴ�.";
              alert.alertFailMessage(out, v_msg);			
			} 
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunCpResultInsert()\r\n" + ex.getMessage());
        }
    } 
    
    
    
}