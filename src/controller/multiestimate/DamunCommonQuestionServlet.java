// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunCommonQuestionServlet.java

package controller.multiestimate;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.multiestimate.DamunCommonQuestionBean;
import com.credu.multiestimate.ScaleBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.multiestimate.DamunCommonQuestionServlet")
public class DamunCommonQuestionServlet extends HttpServlet
    implements Serializable
{

    public DamunCommonQuestionServlet()
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
            v_process = box.getStringDefault("p_process", "DamunQuestionListPage");
            if(ErrorManager.isErrorMessageView())
                box.put("errorout", out);
            if(!AdminUtil.getInstance().checkRWRight("DamunCommonQuestionServlet", v_process, out, box))
                return;
            if(v_process.equals("DamunQuestionListPage"))
                performDamunQuestionListPage(request, response, box, out);
            else
            if(v_process.equals("DamunQuestionInsertPage"))
                performDamunQuestionInsertPage(request, response, box, out);
            else
            if(v_process.equals("DamunQuestionInsertS"))
                performDamunQuestionInsertS(request, response, box, out);
            else
            if(v_process.equals("DamunQuestionUpdatePage"))
                performDamunQuestionUpdatePage(request, response, box, out);
            else
            if(v_process.equals("DamunQuestionUpdateS"))
                performDamunQuestionUpdateS(request, response, box, out);
            else
            if(v_process.equals("DamunQuestionInsert"))
                performDamunQuestionInsert(request, response, box, out);
            else
            if(v_process.equals("DamunQuestionUpdate"))
                performDamunQuestionUpdate(request, response, box, out);
            else
            if(v_process.equals("DamunQuestionDelete"))
                performDamunQuestionDelete(request, response, box, out);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performDamunQuestionListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonQuestion_L.jsp";
            DamunCommonQuestionBean bean = new DamunCommonQuestionBean();
            ArrayList list1 = bean.selectQuestionList(box);
            request.setAttribute("DamunQuestionList", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunQuestionListPage()\r\n" + ex.getMessage());
        }
    }

    public void performDamunQuestionInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonQuestion_I.jsp";
            ScaleBean sbean = new ScaleBean();
            ArrayList list1 = sbean.selectScaleGubunExample(box);
            request.setAttribute("ScaleQuestionExampleData", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunQuestionInsertPage()\r\n" + ex.getMessage());
        }
    }

    public void performDamunQuestionInsertS(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonQuestion_I2.jsp";
            ScaleBean sbean = new ScaleBean();
            ArrayList list1 = sbean.selectScaleGubunExample(box);
            request.setAttribute("ScaleQuestionExampleData", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunQuestionInsertS()\r\n" + ex.getMessage());
        }
    }

    public void performDamunQuestionUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonQuestion_U.jsp";
            DamunCommonQuestionBean bean = new DamunCommonQuestionBean();
            ArrayList list = bean.selectQuestionExample(box);
            request.setAttribute("DamunQuestionExampleData", list);
            DataBox dbox0 = (DataBox)list.get(0);
            int v_damuntype = dbox0.getInt("d_damuntype");
            int v_fscalecode = dbox0.getInt("d_fscalecode");
            int v_sscalecode = dbox0.getInt("d_sscalecode");
            int v_scalecode = 0;
            if(v_damuntype == 5 || v_damuntype == 6)
                v_scalecode = v_fscalecode;
            box.put("p_damuntype", Integer.toString(v_damuntype));
            box.put("p_scalecode", Integer.toString(v_scalecode));
            box.put("p_fscalecode", Integer.toString(v_fscalecode));
            box.put("p_sscalecode", Integer.toString(v_sscalecode));
            ScaleBean sbean = new ScaleBean();
            ArrayList list1 = sbean.selectScaleGubunExample(box);
            request.setAttribute("ScaleQuestionExampleData", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunQuestionUpdatePage()\r\n" + ex.getMessage());
        }
    }

    public void performDamunQuestionUpdateS(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/multiestimate/za_DamunCommonQuestion_U2.jsp";
            DamunCommonQuestionBean bean = new DamunCommonQuestionBean();
            int isOk = bean.updateSQuestion(box);
            ArrayList list = bean.selectQuestionExample(box);
            request.setAttribute("DamunQuestionExampleData", list);
            DataBox dbox0 = (DataBox)list.get(0);
            int v_damuntype = dbox0.getInt("d_damuntype");
            int v_fscalecode = dbox0.getInt("d_fscalecode");
            int v_sscalecode = dbox0.getInt("d_sscalecode");
            int v_scalecode = 0;
            if(v_damuntype == 5 || v_damuntype == 6)
                v_scalecode = v_fscalecode;
            box.put("p_damuntype", Integer.toString(v_damuntype));
            box.put("p_scalecode", Integer.toString(v_scalecode));
            box.put("p_fscalecode", Integer.toString(v_fscalecode));
            box.put("p_sscalecode", Integer.toString(v_sscalecode));
            ScaleBean sbean = new ScaleBean();
            ArrayList list1 = sbean.selectScaleGubunExample(box);
            request.setAttribute("ScaleQuestionExampleData", list1);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDamunQuestionUpdateS()\r\n" + ex.getMessage());
        }
    }

    public void performDamunQuestionInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.DamunCommonQuestionServlet";
            DamunCommonQuestionBean bean = new DamunCommonQuestionBean();
            int isOk = bean.insertQuestion(box);
            String v_reloadlist = "";
            String v_msg = "";
            box.put("p_process", "DamunQuestionInsertPage");
            box.put("p_end", "0");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "insert.ok";
                v_reloadlist = "ok";
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
            throw new Exception("performDamunQuestionInsert()\r\n" + ex.getMessage());
        }
    }

    public void performDamunQuestionUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.DamunCommonQuestionServlet";
            DamunCommonQuestionBean bean = new DamunCommonQuestionBean();
            int isOk = bean.updateQuestion(box);
            String v_msg = "";
            box.put("p_process", "DamunQuestionUpdatePage");
            box.put("p_end", "0");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else
            if(isOk == -2)
            {
                v_msg = "\uD574\uB2F9 \uBB38\uC81C\uB294 \uC0AC\uC6A9\uC911\uC785\uB2C8\uB2E4.";
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
            throw new Exception("performDamunQuestionUpdate()\r\n" + ex.getMessage());
        }
    }

    public void performDamunQuestionDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            String v_url = "/servlet/controller.multiestimate.DamunCommonQuestionServlet";
            DamunCommonQuestionBean bean = new DamunCommonQuestionBean();
            int isOk = bean.deleteQuestion(box);
            String v_msg = "";
            box.put("p_process", "DamunQuestionInsertPage");
            box.put("p_end", "0");
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else
            if(isOk == -2)
            {
                v_msg = "\uD574\uB2F9 \uBB38\uC81C\uB294 \uC0AC\uC6A9\uC911\uC785\uB2C8\uB2E4.";
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
            throw new Exception("performDamunQuestionDelete()\r\n" + ex.getMessage());
        }
    }
}
