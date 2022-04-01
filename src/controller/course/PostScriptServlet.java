/*
 * @(#)PostScriptServlet.java
 *
 * Copyright(c) 2006, Jin-pil Chung
 * All rights reserved.
 */

package controller.course;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.PostScriptBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * Servlets implementation class for Servlet: ScormDistcodeServlet
 * 
 */
@WebServlet("/servlet/controller.course.PostScriptServlet")
public class PostScriptServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException,
            java.io.IOException
    {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException,
            java.io.IOException
    {
        response.setContentType("text/html;charset=euc-kr");
        request.setCharacterEncoding("euc-kr");
        
        PrintWriter out = response.getWriter();
        RequestBox box = RequestManager.getBox(request);
        
        String v_process = request.getParameter("p_process");

        
        // Default GRCODE를 게임아카데미로 설정
        box.put( "p_grcode", box.getStringDefault("p_grcode","N000002") );
        
        try
        {        
            if (ErrorManager.isErrorMessageView())
            {
                box.put("error.out", out);
            }
            
            // 권한 Check : 관리자만 접근 가능
            if ( !( v_process.equals("list") || v_process.equals("select") ) ) {
            	if ( !AdminUtil.getInstance().checkLoginPopup(out, box) ) { 
            		return;
            	}
            }
            
            if ( v_process.equals("list") )                         // 교육후기 목록
            {
                this.performList( request, response, box, out );
            }
            else if ( v_process.equals("select") )                  // 교육후기 읽기
            {
                this.performSelect( request, response, box, out );
            }
            else if ( v_process.equals("insertPage") )              // 교육후기 쓰기 페이지
            {
                this.performInsertPage( request, response, box, out );
            }
            else if ( v_process.equals("insert") )                  // 교육후기 쓰기
            {
            	this.performInsert( request, response, box, out );
            }
            else if ( v_process.equals("updatePage") )              // 교육후기 수정 페이지
            {
                this.performUpdatePage( request, response, box, out );
            }
            else if ( v_process.equals("update") )                  // 교육후기 수정
            {
            	this.performUpdate( request, response, box, out );
            }
            else if ( v_process.equals("delete") )                  // 교육후기 삭제
            {
                this.performDelete( request, response, box, out );
            }
            else if ( v_process.equals("allList") )                 // 교육후기 전체후기 목록  (2007-0076차수 후기 이벤트용)
            {
            	this.performAllList( request, response, box, out );
            }
            else if ( v_process.equals("myList") )                  // 교육후기 나의후기 목록 (2007-00076차수 후기 이벤트용)
            {
            	this.performMyList( request, response, box, out );
            }
        }
        catch (Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    private void performMyList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception
    {
        try
        {
            PostScriptBean psb = new PostScriptBean();
            
            List myList = psb.selectMyList( box );
            request.setAttribute( "myList", myList );
            
            request.setAttribute("requestbox", box);
            
            String forwardUrl = "/learn/user/game/course/postscript_mylist.jsp";
            forwardUrl(request, response, forwardUrl);
        }
        catch (Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyList()\r\n" + ex.getMessage());
        }               
    }

    private void performAllList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception
    {
        try
        {
            PostScriptBean psb = new PostScriptBean();
            
            List allList = psb.selectAllList( box );
            request.setAttribute( "allList", allList );
            
            request.setAttribute("requestbox", box);
            
            String forwardUrl = "/learn/user/game/course/postscript_alllist.jsp";
            forwardUrl(request, response, forwardUrl);
        }
        catch (Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }               
    }

    private void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception
    {
        try
        {
            PostScriptBean psb = new PostScriptBean();
            
            List list = psb.selectList( box );
            request.setAttribute( "list", list );
            
            boolean isPostscript = psb.isPostscript( box );
            request.setAttribute( "is_postscript", new Boolean(isPostscript) );
            
            request.setAttribute("requestbox", box);
            
            String forwardUrl = "/learn/user/game/course/postscript_list.jsp";
            forwardUrl(request, response, forwardUrl);
        }
        catch (Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }               
    }

    private void performSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception
    {
    	try
    	{
    		PostScriptBean psb = new PostScriptBean();
    		
    		DataBox dbox = psb.select( box );
    		request.setAttribute( "select", dbox );
    		
    		request.setAttribute("requestbox", box);
    		
    		String forwardUrl = "/learn/user/game/course/postscript_read.jsp";
    		forwardUrl(request, response, forwardUrl);
    	}
    	catch (Exception ex)
    	{
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performSelect()\r\n" + ex.getMessage());
    	}               
    }

    private void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box);
    		
    		String forwardUrl = "/learn/user/game/course/postscript_write.jsp";
    		forwardUrl(request, response, forwardUrl);
    	}
    	catch (Exception ex)
    	{
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performInsertPage()\r\n" + ex.getMessage());
    	}               
    }
    
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	PostScriptBean psb = new PostScriptBean();
			
            int isOk = psb.insert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.course.PostScriptServlet";

            box.put("p_process", box.getStringDefault("p_next_process", "list") );
            request.setAttribute("requestbox", box);

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }
    	catch (Exception ex)
    	{
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performInsert()\r\n" + ex.getMessage());
    	}   
    }    
    
    private void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

    		PostScriptBean psb = new PostScriptBean();
    		
    		box.put("idCheck", "Y");	// 본인글만 Select 가능
    		DataBox dbox = psb.select( box );
    		request.setAttribute( "select", dbox );
    		
    		request.setAttribute("requestbox", box);
    		
    		String forwardUrl = "/learn/user/game/course/postscript_write.jsp";
    		forwardUrl(request, response, forwardUrl);
    	}
    	catch (Exception ex)
    	{
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
    	}               
    }
    
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
        	PostScriptBean psb = new PostScriptBean();
			
            int isOk = psb.insert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.course.PostScriptServlet";

            box.put("p_process", box.getStringDefault("p_next_process", "list") );
            request.setAttribute("requestbox", box);

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
    	}
    	catch (Exception ex)
    	{
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdate()\r\n" + ex.getMessage());
    	}   
    }    
    
    private void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception
    {
        try
        {
            request.setAttribute( "requestbox", box );
            
            String v_msg = "";
            PostScriptBean psb = new PostScriptBean();
            
            int result = psb.delete( box );
            
            box.put("p_process", box.getStringDefault("p_next_process", "list") );
            String v_url = "/servlet/controller.course.PostScriptServlet";

            AlertManager alert = new AlertManager();

            if ( result > -1 ) 
            { 
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, false, false);
            }
            else 
            { 
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }
        catch (Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    private void forwardUrl(HttpServletRequest request, HttpServletResponse response, String forwardUrl)
            throws ServletException, IOException
    {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(forwardUrl);
        rd.forward(request, response);
    }
}