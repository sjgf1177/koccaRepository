package com.credu.scorm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.Enumeration;
//import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil
{

    public RequestUtil()
    {
    }

    public String getParameter(HttpServletRequest req, String name)
    {
        String value = req.getParameter(name);
        if(value != null)
            return value;
        else
            return "";
    }

    public static String getParam(HttpServletRequest req)
    {
        String param = "";
        String qstring = "";
        for(Enumeration enumeration = req.getParameterNames(); enumeration != null && enumeration.hasMoreElements();)
        {
            param = ((String)enumeration.nextElement()).trim();
            qstring = qstring + param + "=" + req.getParameter(param) + "&";
        }

        return qstring;
    }

    public String[] getParameterValues(HttpServletRequest request, String name)
    {
        String values[] = request.getParameterValues(name);
        if(values != null)
            return values;
        else
            return null;
    }

    public Object getAttribute(HttpServletRequest request, String name)
    {
        return request.getAttribute(name);
    }

    public String getHtmlHiddenTags(HttpServletRequest request, Object obj)
        throws Exception
    {
        Class c = obj.getClass();
        Field fields[] = c.getFields();
        StringBuffer strBuf = new StringBuffer();
        String name = "";
        String value = "";
        for(int i = 0; i < fields.length; i++)
        {
            Class fc = fields[i].getType();
            int mod = fc.getModifiers();
            if(Modifier.isPublic(mod) && fc.isAssignableFrom(java.lang.String.class) && (!fc.isArray() && !Modifier.isStatic(mod)))
            {
                name = fields[i].getName();
                value = request.getParameter(name);
                if(value == null)
                    value = "";
                strBuf.append("\n <input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\">");
            }
        }

        return strBuf.toString();
    }

    public String getURLParameter(Object obj)
        throws Exception
    {
        Class c = obj.getClass();
        Field fields[] = c.getFields();
        StringBuffer strBuf = new StringBuffer();
        String rtn = "";
        String name = "";
        String value = "";
        for(int i = 0; i < fields.length; i++)
        {
            Class fc = fields[i].getType();
            int mod = fc.getModifiers();
            if(Modifier.isPublic(mod) && fc.isAssignableFrom(java.lang.String.class) && (!fc.isArray() && !Modifier.isStatic(mod)))
            {
                name = fields[i].getName();
                value = fields[i].get(obj).toString();
                if(value == null)
                    value = "";
                strBuf.append(name + "=" + value + "&");
            }
        }

        rtn = strBuf.toString();
        if(rtn.length() > 0)
            rtn = rtn.substring(0, rtn.length() - 1);
        return rtn;
    }

    public String getURLEncodedParameter(Object obj)
        throws Exception
    {
        Class c = obj.getClass();
        Field fields[] = c.getFields();
        StringBuffer strBuf = new StringBuffer();
        String rtn = "";
        String name = "";
        String value = "";
        for(int i = 0; i < fields.length; i++)
        {
            Class fc = fields[i].getType();
            int mod = fc.getModifiers();
            if(Modifier.isPublic(mod) && fc.isAssignableFrom(java.lang.String.class) && (!fc.isArray() && !Modifier.isStatic(mod)))
            {
                name = fields[i].getName();
                value = fields[i].get(obj).toString();
                if(value == null)
                    value = "";
                strBuf.append(name + "=" + value + "&");
            }
        }

        rtn = strBuf.toString();
        if(rtn.length() > 0)
            rtn = rtn.substring(0, rtn.length() - 1);
        return URLEncoder.encode(rtn);
    }

    public String getURLEncodedString(String str)
        throws Exception
    {
        return URLEncoder.encode(str);
    }
}
