package com.credu.scorm;

import java.net.URLEncoder;
import java.util.*;
//import javax.servlet.ServletRequest;
import javax.servlet.http.*;

// Referenced classes of package alexit.lib.util:
//            Box, MultiPartParser, StringUtil, MultipartRequest, 
//            SessionBox

public class HttpUtility
{

    private HttpUtility()
    {
    }

    public static String decode(String s)
    {
        return URLEncoder.encode(s);
    }

    public static String encode(String s)
    {
        return URLEncoder.encode(s);
    }

    public static Box getBox(HttpServletRequest req)
    {
        Box box = new Box("RequestBox");
        String key;
        for(Enumeration e = req.getParameterNames(); e.hasMoreElements(); box.put(key, req.getParameterValues(key)))
            key = (String)e.nextElement();

        return box;
    }

    public static Box getBox(MultiPartParser mPas)
    {
        Box box = new Box("MultiPartBox");
        String key;
        for(Enumeration e = mPas.getParameterNames(); e.hasMoreElements(); box.put(key, StringUtil.getEUC_KR(mPas.getParameterValues(key))))
            key = StringUtil.getEUC_KR((String)e.nextElement());

        return box;
    }

    public static Box getBox(MultipartRequest mPas)
    {
        Box box = new Box("MultiPartBox");
        String key;
        for(Enumeration e = mPas.getParameterNames(); e.hasMoreElements(); box.put(key, mPas.getParameterValues(key)))
            key = (String)e.nextElement();

        return box;
    }

    public static Properties getProperty(HttpServletRequest req)
    {
        Properties pt = new Properties();
        String key;
        for(Enumeration e = req.getParameterNames(); e.hasMoreElements(); pt.setProperty(key, req.getParameter(key)))
            key = (String)e.nextElement();

        return pt;
    }

    public static Box getBoxFromCookie(HttpServletRequest req)
    {
        Box cookiebox = new Box("cookiebox");
        Cookie cookies[] = req.getCookies();
        if(cookies == null)
            return cookiebox;
        for(int i = 0; cookies != null && i < cookies.length; i++)
        {
            String key = cookies[i].getName();
            String value = cookies[i].getValue();
            if(value == null)
                value = "";
            String values[] = new String[1];
            values[0] = value;
            cookiebox.put(key, values);
        }

        return cookiebox;
    }

    public static SessionBox getNewSessionBox(HttpServletRequest req)
    {
        return getNewSessionBox(req, "shared session box");
    }

    public static SessionBox getNewSessionBox(HttpServletRequest req, String name)
    {
        HttpSession session = req.getSession(true);
        SessionBox sessionbox = null;
        if(!session.isNew())
        {
            Object o = session.getAttribute(name);
            if(o != null)
                session.removeAttribute(name);
        }
        if(sessionbox == null)
        {
            sessionbox = new SessionBox(session, name);
            session.setAttribute(name, sessionbox);
        }
        String key;
        for(Enumeration e = req.getParameterNames(); e.hasMoreElements(); sessionbox.put(key, req.getParameterValues(key)))
            key = (String)e.nextElement();

        return sessionbox;
    }

    public static SessionBox getSessionBox(HttpServletRequest req)
    {
        return getSessionBox(req, "shared session box");
    }

    public static SessionBox getSessionBox(HttpServletRequest req, String name)
    {
        HttpSession session = req.getSession(true);
        SessionBox sessionbox = null;
        Object o = session.getAttribute(name);
        if(o != null)
            if(o instanceof SessionBox)
                sessionbox = (SessionBox)o;
            else
                session.removeAttribute(name);
        if(sessionbox == null)
        {
            sessionbox = new SessionBox(session, name);
            session.setAttribute(name, sessionbox);
        }
        String key;
        for(Enumeration e = req.getParameterNames(); e.hasMoreElements(); sessionbox.put(key, req.getParameterValues(key)))
            key = (String)e.nextElement();

        return sessionbox;
    }

    public static boolean isOverIE50(HttpServletRequest req)
    {
        String user_agent = req.getHeader("user-agent");
        if(user_agent == null)
            return false;
        int index = user_agent.indexOf("MSIE");
        if(index == -1)
            return false;
        int version = 0;
        try
        {
            version = Integer.parseInt(user_agent.substring(index + 5, index + 5 + 1));
        }
        catch(Exception exception) { }
        return version >= 5;
    }
}
