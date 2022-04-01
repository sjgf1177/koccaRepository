// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScaleServlet.java

package controller.multiestimate;

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

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.multiestimate.ScaleBean;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.multiestimate.ScaleServlet")
public class ScaleServlet extends HttpServlet
    implements Serializable
{

    public ScaleServlet()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doPost(request, response);
    }

    @SuppressWarnings("unchecked")
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
            if(!AdminUtil.getInstance().checkRWRight("ScaleServlet", v_process, out, box))
                return;
            if(v_process.equals("ScaleListPage"))
                performScaleListPage(request, response, box, out);
            else
            if(v_process.equals("ScaleInsertPage"))
                performScaleInsertPage(request, response, box, out);
            else
            if(v_process.equals("ScaleUpdatePage"))
                performScaleUpdatePage(request, response, box, out);
            else
            if(v_process.equals("ScaleInsert"))
                performScaleInsert(request, response, box, out);
            else
            if(v_process.equals("ScaleUpdate"))
                performScaleUpdate(request, response, box, out);
            else
            if(v_process.equals("ScaleDelete"))
                performScaleDelete(request, response, box, out);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    @SuppressWarnings("unchecked")
	public void performScaleListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_Scale_L.jsp";
            ScaleBean bean = new ScaleBean();
            java.util.ArrayList list1 = bean.selectScaleList(box);
            request.setAttribute("ScaleList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleListPage()\r\n" + ex.getMessage());
        }
    }

    public void performScaleInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_Scale_I.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsertPage()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	public void performScaleUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_Scale_U.jsp";
            ScaleBean bean = new ScaleBean();
            java.util.ArrayList list = bean.selectScaleExample(box);
            request.setAttribute("ScaleExampleData", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleUpdatePage()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	public void performScaleInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.ScaleServlet";
            ScaleBean bean = new ScaleBean();
            int isOk = bean.insertScale(box);
            String v_msg = "";
            box.put("p_process", "ScaleInsertPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else
            {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsert()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	public void performScaleUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.ScaleServlet";
            ScaleBean bean = new ScaleBean();
            int isOk = bean.updateScale(box);
            String v_msg = "";
            box.put("p_process", "ScaleListPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else
            if(isOk == -2)
            {
                v_msg = "\uD574\uB2F9 \uCC99\uB3C4\uB294 \uC0AC\uC6A9\uC911\uC785\uB2C8\uB2E4.";
                alert.alertFailMessage(out, v_msg);
            } else
            {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsert()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	public void performScaleDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.ScaleServlet";
            ScaleBean bean = new ScaleBean();
            int isOk = bean.deleteScale(box);
            String v_msg = "";
            box.put("p_process", "ScaleListPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else
            if(isOk == -2)
            {
                v_msg = "\uD574\uB2F9 \uCC99\uB3C4\uB294 \uC0AC\uC6A9\uC911\uC785\uB2C8\uB2E4.";
                alert.alertFailMessage(out, v_msg);
            } else
            {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScaleInsert()\r\n" + ex.getMessage());
        }
    }
}
