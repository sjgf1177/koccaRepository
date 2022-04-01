package controller.mobile.helpdesk;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.common.MenuMobileBean;
import com.credu.mobile.helpdesk.HelpDeskBean;

/**
 * ������Ʈ�� : kocca_java
 * ��Ű���� : controller.mobile.helpdesk
 * ���ϸ� : HelpdeskServlet.java
 * �ۼ���¥ : 2011. 9. 26.
 * ó������ : 
 * �������� : 
 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.    
 */
@WebServlet("/servlet/controller.mobile.helpdesk.HelpDeskServlet")
public class HelpDeskServlet extends javax.servlet.http.HttpServlet 
{
	/* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException 
    {
        this.doPost(request, response);
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException 
    {
    	PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        
        try 
        {
            request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");         // process
            
            if(ErrorManager.isErrorMessageView()) 
            {
                box.put("errorout", out);
            }
            
            if("".equals(v_process) || "noticeList".equals(v_process))
            {
            	this.performNoticeListPage(request, response, box, out);		//�������� ���
            }
            else if("noticeView".equals(v_process))
            {
            	
            	this.performNoticeViewPage(request, response, box, out);		//�������� ��������
            }
            else if("eventList".equals(v_process))
            {
            	this.performEventListPage(request, response, box, out);			//�̺�Ʈ ���
            }
            else if("eventPassView".equals(v_process))
            {
            	this.performEventPassViewPage(request, response, box, out);			//�̺�Ʈ ��û�� ��� ���
            }
            else if("eventView".equals(v_process))
            {
            	
            	this.performEventViewPage(request, response, box, out);			//�̺�Ʈ ��������
            }
            else if("helpIntroPage".equals(v_process))
            {
            	
            	this.performHelpIntroPage(request, response, box, out);			//�̿�ȳ� ������
            }
        }
        catch(Exception ex) 
        {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    } 
    
    /**
     * �������� ���
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performNoticeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      ��������� box ��ü�� �Ѱ��ش�
    		
    		DataBox menudbox = new MenuMobileBean().getMenuDistCodeData(box);
    		if(menudbox == null) menudbox = new DataBox("request");
    		box.put("p_tabseq", menudbox.getString("d_distcode"));
    		
    		
    		HelpDeskBean bean = new HelpDeskBean();
    		request.setAttribute("_LIST_", bean.getNoticeList( box ));
    		
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/helpdesk/zu_notice_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performNoticeListPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * �������� �� ������
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performNoticeViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      ��������� box ��ü�� �Ѱ��ش�
    		
    		DataBox menudbox = new MenuMobileBean().getMenuDistCodeData(box);
    		if(menudbox == null) menudbox = new DataBox("request");
    		box.put("p_tabseq", menudbox.getString("d_distcode"));
    		
    		HelpDeskBean bean = new HelpDeskBean();
    		request.setAttribute("_VIEW_", bean.getNoticeView( box ));
    		
    		//��ȸ�� ����
    		bean.updateCnt(box);
    		
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/helpdesk/zu_notice_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performNoticeViewPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * ����� �̺�Ʈ ���
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performEventListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      ��������� box ��ü�� �Ѱ��ش�
    		
    		HelpDeskBean bean = new HelpDeskBean();
    		request.setAttribute("_LIST_", bean.getEventList( box ));
    		
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/helpdesk/zu_event_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEventListPage()\r\n" + ex.getMessage() );
        }
    }
    
    
    /**
     * �̺�Ʈ ��÷�� ����
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performEventPassViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    {
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      ��������� box ��ü�� �Ѱ��ش�
    		
    		HelpDeskBean bean = new HelpDeskBean();
    		request.setAttribute("_VIEW_", bean.getEventData( box ));
    		
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/helpdesk/zu_eventPass_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEventPassViewPage()\r\n" + ex.getMessage() );
        }
    }
    
    
    /**
     * �̺�Ʈ �� ����
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performEventViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    {
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      ��������� box ��ü�� �Ѱ��ش�
    		
    		HelpDeskBean bean = new HelpDeskBean();
    		request.setAttribute("_VIEW_", bean.getEventData( box ));
    		
    		//��ȸ�� ����
    		bean.updateEventCnt(box);
    		
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/helpdesk/zu_event_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performEventViewPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * �̿�ȳ� ������
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performHelpIntroPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    {
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      ��������� box ��ü�� �Ѱ��ش�
    		
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/helpdesk/zu_hlepIntro_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performHelpIntroPage()\r\n" + ex.getMessage() );
        }
    }
}
