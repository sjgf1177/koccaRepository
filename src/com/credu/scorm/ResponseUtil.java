package com.credu.scorm;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class ResponseUtil
{

    public ResponseUtil()
    {
    }

    public static void alert(String msg, PageContext PC)
    {
        try
        {
            JspWriter out = PC.getOut();
            String rtn_msg = "";
            rtn_msg = "\n <script language=\"javascript\">\n   alert('" + msg + "');" + "\n </script>";
            out.println(rtn_msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void alert(String msg, String url, PageContext PC)
    {
        try
        {
            JspWriter out = PC.getOut();
            String rtn_msg = "";
            rtn_msg = "\n <script language=\"javascript\">\n   alert('" + msg + "');" + "\n   location.href = '" + url + "';" + "\n </script>";
            out.println(rtn_msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void alertNback(String msg, PageContext PC)
    {
        try
        {
            JspWriter out = PC.getOut();
            String rtn_msg = "";
            rtn_msg = "\n <script language=\"javascript\">\n   alert('" + msg + "');" + "\n   history.back(-1);" + "\n </script>";
            out.println(rtn_msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void alertNgo(String msg, PageContext PC, int pageCnt)
    {
        try
        {
            JspWriter out = PC.getOut();
            String rtn_msg = "";
            rtn_msg = "\n <script language=\"javascript\">\n   alert('" + msg + "');" + "\n   history.go("+ pageCnt +");" + "\n </script>";
            out.println(rtn_msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void alertNclose(String msg, PageContext PC)
    {
        try
        {
            JspWriter out = PC.getOut();
            String rtn_msg = "";
            rtn_msg = "\n <script language=\"javascript\">\n   alert('" + msg + "');" + "\n   window.close();" + "\n </script>";
            out.println(rtn_msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
