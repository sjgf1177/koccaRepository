// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContentCPServlet.java

package controller.beta;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.ContentCPBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.beta.ContentCPServlet")
public class ContentCPServlet extends HttpServlet
    implements Serializable
{

    public ContentCPServlet()
    {
    }

    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
        doPost(httpservletrequest, httpservletresponse);
    }

    public void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
        PrintWriter printwriter = null;
        Object obj = null;
        Object obj1 = null;
        String s = "";
        boolean flag = false;
        try
        {
            httpservletresponse.setContentType("text/html;charset=euc-kr");
            printwriter = httpservletresponse.getWriter();
            RequestBox requestbox = RequestManager.getBox(httpservletrequest);
            String s1 = requestbox.getString("p_process");
            if(ErrorManager.isErrorMessageView())
                requestbox.put("errorout", printwriter);
            System.out.println("v_process : " + s1);
            if(!AdminUtil.getInstance().checkRWRight("ContentCPServlet", s1, printwriter, requestbox))
                return;
            requestbox.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            if(s1.equals("select"))
                performSelectList(httpservletrequest, httpservletresponse, requestbox, printwriter);
            else
            if(s1.equals("insertPage"))
                performInsertPage(httpservletrequest, httpservletresponse, requestbox, printwriter);
            else
            if(s1.equals("insert"))
                performInsert(httpservletrequest, httpservletresponse, requestbox, printwriter);
            else
            if(s1.equals("updatePage"))
                performUpdatePage(httpservletrequest, httpservletresponse, requestbox, printwriter);
            else
            if(s1.equals("update"))
                performUpdate(httpservletrequest, httpservletresponse, requestbox, printwriter);
            else
            if(s1.equals("delete"))
                performDelete(httpservletrequest, httpservletresponse, requestbox, printwriter);
        }
        catch(Exception exception)
        {
            ErrorManager.getErrorStackTrace(exception, printwriter);
        }
    }

    public void performDelete(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, RequestBox requestbox, PrintWriter printwriter)
        throws Exception
    {
        try
        {
            ContentCPBean contentcpbean = new ContentCPBean();
            int i = contentcpbean.deleteManager(requestbox);
            String s = "";
            String s1 = "/servlet/controller.beta.ContentCPServlet";
            requestbox.put("p_process", "select");
            AlertManager alertmanager = new AlertManager();
            if(i > 0)
            {
                s = "delete.ok";
                alertmanager.alertOkMessage(printwriter, s, s1, requestbox);
            } else
            {
                s = "delete.fail";
                alertmanager.alertFailMessage(printwriter, s);
            }
            Log.info.println(this, requestbox, s + " on ContentCPServlet");
        }
        catch(Exception exception)
        {
            ErrorManager.getErrorStackTrace(exception, printwriter);
            throw new Exception("performDelete()\r\n" + exception.getMessage());
        }
    }

    public void performInsert(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, RequestBox requestbox, PrintWriter printwriter)
        throws Exception
    {
        try
        {
            ContentCPBean contentcpbean = new ContentCPBean();
            int i = contentcpbean.insertComp(requestbox);
            int j = contentcpbean.insertMember(requestbox);
            int k = contentcpbean.insertManager(requestbox);
            String s = "";
            String s1 = "/servlet/controller.beta.ContentCPServlet";
            requestbox.put("p_process", "select");
            AlertManager alertmanager = new AlertManager();
            if(i > 0 && (j > 0) & (k > 0))
            {
                s = "insert.ok";
                alertmanager.alertOkMessage(printwriter, s, s1, requestbox);
            } else
            {
                s = "insert.fail";
                alertmanager.alertFailMessage(printwriter, s);
            }
            Log.info.println(this, requestbox, s + " on ContentCPServlet");
        }
        catch(Exception exception)
        {
            ErrorManager.getErrorStackTrace(exception, printwriter);
            throw new Exception("performInsert()\r\n" + exception.getMessage());
        }
    }

    public void performInsertPage(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, RequestBox requestbox, PrintWriter printwriter)
        throws Exception
    {
        try
        {
            httpservletrequest.setAttribute("requestbox", requestbox);
            ServletContext servletcontext = getServletContext();
            RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher("/beta/admin/za_ContentCP_I.jsp");
            requestdispatcher.forward(httpservletrequest, httpservletresponse);
            Log.info.println(this, requestbox, "Dispatch to /beta/admin/za_ContentCP_I.jsp");
        }
        catch(Exception exception)
        {
            ErrorManager.getErrorStackTrace(exception, printwriter);
            throw new Exception("performInsertPage()\r\n" + exception.getMessage());
        }
    }

    public void performSelectList(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, RequestBox requestbox, PrintWriter printwriter)
        throws Exception
    {
        try
        {
            httpservletrequest.setAttribute("requestbox", requestbox);
            ContentCPBean contentcpbean = new ContentCPBean();
            java.util.ArrayList arraylist = contentcpbean.selectList(requestbox);
            httpservletrequest.setAttribute("selectList", arraylist);
            ServletContext servletcontext = getServletContext();
            RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher("/beta/admin/za_ContentCP_L.jsp");
            requestdispatcher.forward(httpservletrequest, httpservletresponse);
            Log.info.println(this, requestbox, "Dispatch to /beta/admin/za_ContentCP_L.jsp");
        }
        catch(Exception exception)
        {
            ErrorManager.getErrorStackTrace(exception, printwriter);
            throw new Exception("performSelectList()\r\n" + exception.getMessage());
        }
    }

    public void performUpdate(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, RequestBox requestbox, PrintWriter printwriter)
        throws Exception
    {
        try
        {
            ContentCPBean contentcpbean = new ContentCPBean();
            int i = contentcpbean.updateManager(requestbox);
            int j = contentcpbean.updateMember(requestbox);
            String s = "";
            String s1 = "/servlet/controller.beta.ContentCPServlet";
            requestbox.put("p_process", "select");
            AlertManager alertmanager = new AlertManager();
            if(i > 0 && j > 0)
            {
                s = "update.ok";
                alertmanager.alertOkMessage(printwriter, s, s1, requestbox);
            } else
            {
                s = "update.fail";
                alertmanager.alertFailMessage(printwriter, s);
            }
            Log.info.println(this, requestbox, s + " on ContentCPServlet");
        }
        catch(Exception exception)
        {
            ErrorManager.getErrorStackTrace(exception, printwriter);
            throw new Exception("performUpdate()\r\n" + exception.getMessage());
        }
    }

    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox requestbox, PrintWriter printwriter)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", requestbox);
            ContentCPBean contentcpbean = new ContentCPBean();
            DataBox databox = contentcpbean.selectViewComp(requestbox);
            request.setAttribute("selectManager", databox);

            ServletContext sc = getServletContext();
						RequestDispatcher rd = sc.getRequestDispatcher("/beta/admin/za_ContentCP_U.jsp"); 
						rd.forward(request, response);


            Log.info.println(this, requestbox, "Dispatch to /beta/admin/za_ContentCP_U.jsp");
        }
        catch(Exception exception)
        {
            ErrorManager.getErrorStackTrace(exception, printwriter);
            throw new Exception("performUpdatePage()\r\n" + exception.getMessage());
        }
    }
}