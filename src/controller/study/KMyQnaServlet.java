// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KMyQnaServlet.java

package controller.study;

import com.credu.library.*;
import com.credu.study.MyQnaBean;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/servlet/controller.study.KMyQnaServlet")
public class KMyQnaServlet extends HttpServlet
    implements Serializable
{

    public KMyQnaServlet()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        try
        {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            if(ErrorManager.isErrorMessageView())
                box.put("errorout", out);
            if(box.getSession("userid").equals(""))
            {
                request.setAttribute("tUrl", request.getRequestURI());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/controller.homepage.MainServlet");
                dispatcher.forward(request, response);
                return;
            }
            if(v_process.equals("MyQnaStudyListPage"))
                performMyQnaStudyListPage(request, response, box, out);
            else
            if(v_process.equals("MyQnaStudyViewPage"))
                performMyQnaStudyViewPage(request, response, box, out);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performMyQnaStudyListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/kocca/study/ku_MyQnaStudy_L.jsp";
            MyQnaBean bean = new MyQnaBean();
            java.util.ArrayList list1 = bean.SelectMyQnaStudyList(box);
            request.setAttribute("MyQnaStudyListPage", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            Log.info.println(this, box, v_url);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyQnaStudyListPage()\r\n" + ex.getMessage());
        }
    }

    public void performMyQnaSiteListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/kocca/study/ku_MyQnaSite_L.jsp";
            MyQnaBean bean = new MyQnaBean();
            java.util.ArrayList list2 = bean.SelectMyQnaSiteList(box);
            request.setAttribute("MyQnaSiteListPage", list2);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            Log.info.println(this, box, v_url);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyQnaSiteListPage()\r\n" + ex.getMessage());
        }
    }

    public void performMyQnaStudyViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            MyQnaBean bean = new MyQnaBean();
            com.credu.library.DataBox dbox = bean.selectMyQnaStudy(box);
            request.setAttribute("MyQnaStudyViewPage", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/study/ku_MyQnaStudy_R.jsp");
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    public void performMyQnaSiteViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            MyQnaBean bean = new MyQnaBean();
            com.credu.library.DataBox dbox = bean.selectMyQnaSite(box);
            request.setAttribute("MyQnaSiteViewPage", dbox);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/study/ku_MyQnaSite_R.jsp");
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }
}
