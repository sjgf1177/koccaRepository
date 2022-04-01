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
 * 프로젝트명 : kocca_java
 * 패키지명 : controller.mobile.helpdesk
 * 파일명 : HelpdeskServlet.java
 * 작성날짜 : 2011. 9. 26.
 * 처리업무 : 
 * 수정내용 : 
 
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
            	this.performNoticeListPage(request, response, box, out);		//공지사항 목록
            }
            else if("noticeView".equals(v_process))
            {
            	
            	this.performNoticeViewPage(request, response, box, out);		//공지사항 상세페이지
            }
            else if("eventList".equals(v_process))
            {
            	this.performEventListPage(request, response, box, out);			//이벤트 목록
            }
            else if("eventPassView".equals(v_process))
            {
            	this.performEventPassViewPage(request, response, box, out);			//이벤트 담청자 목록 목기
            }
            else if("eventView".equals(v_process))
            {
            	
            	this.performEventViewPage(request, response, box, out);			//이벤트 상세페이지
            }
            else if("helpIntroPage".equals(v_process))
            {
            	
            	this.performHelpIntroPage(request, response, box, out);			//이용안내 페이지
            }
        }
        catch(Exception ex) 
        {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    } 
    
    /**
     * 공지사항 목록
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
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
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
     * 공지사항 상세 페이지
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
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		DataBox menudbox = new MenuMobileBean().getMenuDistCodeData(box);
    		if(menudbox == null) menudbox = new DataBox("request");
    		box.put("p_tabseq", menudbox.getString("d_distcode"));
    		
    		HelpDeskBean bean = new HelpDeskBean();
    		request.setAttribute("_VIEW_", bean.getNoticeView( box ));
    		
    		//조회수 증가
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
     * 모바일 이벤트 목록
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
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
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
     * 이벤트 당첨자 보기
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
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
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
     * 이벤트 상세 내용
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
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		HelpDeskBean bean = new HelpDeskBean();
    		request.setAttribute("_VIEW_", bean.getEventData( box ));
    		
    		//조회수 증가
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
     * 이용안내 페이지
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
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
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
