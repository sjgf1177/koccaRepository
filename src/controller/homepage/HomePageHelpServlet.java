//**********************************************************
//  1. ��      ��: �н�ȯ�浵��̸� �����ϴ� ����
//  2. ���α׷��� : HomePageHelpServlet.java
//  3. ��      ��: �н�ȯ�浵��� �������� �����Ѵ�(HOMEPAGE)
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.6 �̿���
//  7. ��      ��: 
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.HtmlManageBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageHelpServlet")
public class HomePageHelpServlet extends javax.servlet.http.HttpServlet {
    
    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;
        
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);
            
            if (box.getSession("tem_grcode") == "") {        		
	             box.setSession("tem_grcode","N000001");
	     	}

            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

           if(v_process.equals("HelpHome")){									// �н����� Ȩ
        	   this.performHelpHome(request, response, box, out);
            }else if(v_process.equals("selectHelp")) {							// �н�ȯ�浵��� ( ���ͳ� )
                this.performSelectList(request, response, box, out);
            }else if(v_process.equals("selectHelpMove")){						// �н�ȯ�浵��� ( ������ )
                this.performSelectListMove(request, response, box, out);
			}else if(v_process.equals("selectHelpSWDown")){						// SW �ٿ�ε�
                this.performSelectListDown(request, response, box, out);
			}else if(v_process.equals("jobLink")){								// ���α��� ��ũ
                this.performSelectJobLink(request, response, box, out);
			}else if(v_process.equals("Help")){									// �¶��� �޴���
                this.performSelectHelp(request, response, box, out);
			}else if(v_process.equals("SiteMap")){								// ����Ʈ��
                this.performSiteMap(request, response, box, out);
			}else if(v_process.equals("Footer")){								// Ǫ�� �޴�
                this.performFooter(request, response, box, out);
			}else if(v_process.equals("RemoteService")){
				this.performRemoteService(request, response, box, out);			//�������� ����
			}else if(v_process.equals("SupportHome")){
				this.performSupportHome(request, response, box, out);			//�̿�ȳ� Ȩ
			}else if(v_process.equals("mobileApp")){
				this.performMobileApp(request, response, box, out);			//����� ��
			}
           
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    �н�ȯ�浵��� ( ���ͳ� )
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
        	
        	request.setAttribute("requestbox", box);      
        	
        	String v_url = "";
        	if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/helpdesk/zu_help_R.jsp";
            	v_url = "/learn/user/2013/portal/helpdesk/zu_help_R.jsp";
            } else {
            	v_url = "/learn/user/portal/helpdesk/zu_help_R.jsp";
            }
			
            String v_code = box.getStringDefault("p_code", "HELP_INTERNET");
            box.put("p_code", v_code); 
            
			HtmlManageBean bean = new HtmlManageBean();
			
			DataBox dbox = bean.selectView(box);
			
			request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_help_R.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    /**
    Ǫ�� �޴�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performFooter(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
        	
        	request.setAttribute("requestbox", box);            
			
        	String v_url = "";
            String v_code = box.getStringDefault("p_code", "FOOTER_SERVICE");
            box.put("p_code", v_code); 
            
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/helpdesk/zu_noemail.jsp";
            	v_url = "/learn/user/2013/portal/helpdesk/zu_noemail.jsp";
            } else {
            	v_url = "/learn/user/portal/include/footer_common.jsp";
            }
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/include/footer_common.jsp";
            }
            
            
			HtmlManageBean bean = new HtmlManageBean();			
			DataBox dbox = bean.selectView(box);			
			request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            //Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_help_R.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    �н�ȯ�浵��� ( ������ )
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectListMove(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
			
            String v_code = box.getStringDefault("p_code", "HELP_VIDEO");
            box.put("p_code", v_code); 
            
			HtmlManageBean bean = new HtmlManageBean();
			
			DataBox dbox = bean.selectView(box);
			
			request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/helpdesk/zu_help_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_help_R.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	
    /**
    SW �ٿ�ε�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectListDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
        	request.setAttribute("requestbox", box);   
        	
        	String v_url = "";
        	 if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
             	//v_url = "/learn/user/2012/portal/helpdesk/zu_swDown_R.jsp";
             	v_url = "/learn/user/2013/portal/helpdesk/zu_swDown_R.jsp";
             } else {
             	v_url = "/learn/user/portal/helpdesk/zu_swDown_R.jsp";
             }
			
            String v_code = box.getStringDefault("p_code", "SOFTWARE_DOWNLOAD");
            box.put("p_code", v_code); 
            
			HtmlManageBean bean = new HtmlManageBean();
			
			DataBox dbox = bean.selectView(box);
			
			request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_swDown_R.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	
    /**
    ���α��� ��ũ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectJobLink(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
		
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/service/gu_jobLink.jsp");
			
			rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_jobLink.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
	
    /**
    �¶��� �޴���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectHelp(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
			
            String v_code = box.getStringDefault("p_code", "ONLINE_MENUAL01");
            box.put("p_code", v_code); 
            
			HtmlManageBean bean = new HtmlManageBean();
			
			DataBox dbox = bean.selectView(box);
			
			request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/helpdesk/zu_menual_R.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_menual_R.jsp");
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    ����Ʈ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSiteMap(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            String v_url ="";
            String v_code = box.getStringDefault("p_code", "SITEMAP");
            box.put("p_code", v_code); 
            
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
            	//v_url = "/learn/user/2012/portal/helpdesk/zu_sitemap_R.jsp";
            	v_url = "/learn/user/2013/portal/helpdesk/zu_sitemap_R.jsp";
            } else {
            	v_url = "/learn/user/portal/helpdesk/zu_sitemap_R.jsp";
            }
            
			HtmlManageBean bean = new HtmlManageBean();
			
			DataBox dbox = bean.selectView(box);
			
			request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+v_url);
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    /**
	�н����� Ȩ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performHelpHome(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            String v_url ="";
            
        	v_url = "/learn/user/2013/portal/helpdesk/zu_helphome.jsp";
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+v_url);
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    /**
	�н����� Ȩ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performRemoteService(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            String v_url ="";
            
        	v_url = "/learn/user/2013/portal/helpdesk/zu_remoteSerivce_R.jsp";
        	if(box.getSession("tem_type").equals("B")){
        		v_url = "/learn/user/typeB/helpdesk/zu_remoteSerivce_R.jsp";
        	}
        	
        	request.setAttribute("requestbox", box);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+v_url);
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    /**
	�̿�ȳ� Ȩ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSupportHome(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            String v_url ="";
            
        	v_url = "/learn/user/2013/portal/helpdesk/zu_supportHome_R.jsp";
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+v_url);
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    /**
	����� ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMobileApp(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);            
            String v_url ="";
            
        	v_url = "/learn/user/2013/portal/helpdesk/zu_mobileApp_R.jsp";
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+v_url);
        }catch (Exception ex) {            
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
}

