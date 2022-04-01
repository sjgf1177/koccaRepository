// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunCommonPaperServlet.java

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
import com.credu.multiestimate.DamunCommonPaperBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.multiestimate.DamunCommonPaperServlet")
public class DamunCommonPaperServlet extends HttpServlet
    implements Serializable
{

    public DamunCommonPaperServlet()
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
            v_process = box.getStringDefault("p_process", "DamunPaperListPage");
            if(ErrorManager.isErrorMessageView())
                box.put("errorout", out);
            if(!AdminUtil.getInstance().checkRWRight("DamunCommonPaperServlet", v_process, out, box))
                return;
            if(v_process.equals("DamunPaperListPage"))
                performDamunPaperListPage(request, response, box, out);
            else
            if(v_process.equals("DamunPaperInsertPage"))
                performDamunPaperInsertPage(request, response, box, out);
            else
            if(v_process.equals("DamunPaperUpdatePage"))
                performDamunPaperUpdatePage(request, response, box, out);
            else
            if(v_process.equals("DamunPaperInsert"))
                performDamunPaperInsert(request, response, box, out);
            else
            if(v_process.equals("DamunPaperUpdate"))
                performDamunPaperUpdate(request, response, box, out);
            else
            if(v_process.equals("DamunPaperDelete"))
                performDamunPaperDelete(request, response, box, out);
            else
            if(v_process.equals("DamunPaperPreviewPage"))
                performDamunPaperPreviewPage(request, response, box, out);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performDamunPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonPaper_L.jsp";
            DamunCommonPaperBean bean = new DamunCommonPaperBean();
            java.util.ArrayList list1 = bean.selectPaperList(box);
            request.setAttribute("DamunPaperListPage", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunPaperListPage()\r\n" + ex.getMessage());
        }
    }

    public void performDamunPaperInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonPaper_I.jsp";
            DamunCommonPaperBean bean = new DamunCommonPaperBean();
            java.util.ArrayList list1 = bean.selectQuestionList(box);
            request.setAttribute("DamunPaperInsertPage", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunPaperInsertPage()\r\n" + ex.getMessage());
        }
    }

    public void performDamunPaperUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonPaper_U.jsp";
            DamunCommonPaperBean bean = new DamunCommonPaperBean();
            java.util.ArrayList list1 = bean.selectQuestionList(box);
            request.setAttribute("DamunCommonQuestionList", list1);
            java.util.ArrayList list2 = bean.selectPaperQuestionList(box);
            request.setAttribute("DamunCommonPaperQuestionList", list2);
            com.credu.library.DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("DamunPaperData", dbox1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunPaperUpdatePage()\r\n" + ex.getMessage());
        }
    }

    public void performDamunPaperInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.DamunCommonPaperServlet";
            DamunCommonPaperBean bean = new DamunCommonPaperBean();
            int isOk = bean.insertPaper(box);
            String v_msg = "";
            box.put("p_process", "DamunPaperInsertPage");
            box.put("p_end", "0");
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
            throw new Exception("performDamunPaperInsert()\r\n" + ex.getMessage());
        }
    }

    public void performDamunPaperUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.DamunCommonPaperServlet";
            DamunCommonPaperBean bean = new DamunCommonPaperBean();
            int isOk = bean.updatePaper(box);
            String v_msg = "";
            box.put("p_process", "DamunPaperUpdatePage");
            box.put("p_end", "0");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else
            {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunPaperUpdate()\r\n" + ex.getMessage());
        }
    }

    public void performDamunPaperDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.DamunCommonPaperServlet";
            DamunCommonPaperBean bean = new DamunCommonPaperBean();
            int isOk = bean.deletePaper(box);
            String v_msg = "";
            box.put("p_process", "DamunPaperUpdatePage");
            box.put("p_end", "0");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else
            if(isOk == -2)
            {
                v_msg = "\uD574\uB2F9 \uBB38\uC81C\uC9C0\uB294 \uC0AC\uC6A9\uC911\uC785\uB2C8\uB2E4.";
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
            throw new Exception("performDamunPaperDelete()\r\n" + ex.getMessage());
        }
    }

    public void performDamunPaperPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonPaperPreview.jsp";
            DamunCommonPaperBean bean = new DamunCommonPaperBean();
            java.util.ArrayList list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);
            box.put("p_subjsel", box.getString("p_subj"));
            box.put("p_upperclass", "ALL");
            com.credu.library.DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("DamunPaperData", dbox1);
            box.remove("p_subjsel");
            box.remove("p_subjsel");
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunPaperPreviewPage()\r\n" + ex.getMessage());
        }
    }
}
