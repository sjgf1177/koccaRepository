package com.credu.scorm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import javax.servlet.http.*;

public class CookieUtil
{

    public CookieUtil()
    {
        cookieTable = new Hashtable();
    }

    public String getValue(HttpServletRequest request, String name)
    {
        String rtn = "";
        Cookie allCookies[] = request.getCookies();
        if(allCookies != null)
        {
            for(int inx = 0; inx < allCookies.length; inx++)
                cookieTable.put(allCookies[inx].getName(), allCookies[inx]);

            if(name != null && !name.equals(""))
            {
                Cookie newCookie = (Cookie)cookieTable.get(name);
                if(newCookie != null)
                    rtn = newCookie.getValue();
            }
        }
        return rtn;
    }

    public void getAllValues(HttpServletRequest request, Object obj)
        throws Exception
    {
        Class c = obj.getClass();
        Field fields[] = c.getFields();
        Cookie allCookies[] = request.getCookies();
        if(allCookies != null)
        {
            for(int inx = 0; inx < allCookies.length; inx++)
                cookieTable.put(allCookies[inx].getName(), allCookies[inx]);

            for(int i = 0; i < fields.length; i++)
            {
                Class fc = fields[i].getType();
                int mod = fc.getModifiers();
                if(Modifier.isPublic(mod) && fc.isAssignableFrom(java.lang.String.class) && (!fc.isArray() && !Modifier.isStatic(mod)))
                {
                    Cookie newCookie = (Cookie)cookieTable.get(fields[i].getName());
                    if(newCookie != null)
                        fields[i].set(obj, newCookie.getValue());
                }
            }

        }
    }

    public void setValue(HttpServletResponse response, String name, String value)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    public void setAllValues(HttpServletResponse response, Object obj)
        throws Exception
    {
        Class c = obj.getClass();
        Field fields[] = c.getFields();
        for(int i = 0; i < fields.length; i++)
        {
            Class fc = fields[i].getType();
            int mod = fc.getModifiers();
            if(Modifier.isPublic(mod) && fc.isAssignableFrom(java.lang.String.class) && (!fc.isArray() && !Modifier.isStatic(mod)))
            {
                Cookie cookie = new Cookie(fields[i].getName(), fields[i].get(obj).toString());
                cookie.setPath("/");
                cookie.setSecure(false);
                response.addCookie(cookie);
            }
        }

    }

    public void setValueDomain(HttpServletResponse response, String name, String value, String domain)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public void setAllValuesDomain(HttpServletResponse response, Object obj, String domain)
        throws Exception
    {
        Class c = obj.getClass();
        Field fields[] = c.getFields();
        for(int i = 0; i < fields.length; i++)
        {
            Class fc = fields[i].getType();
            int mod = fc.getModifiers();
            if(Modifier.isPublic(mod) && fc.isAssignableFrom(java.lang.String.class) && (!fc.isArray() && !Modifier.isStatic(mod)))
            {
                Cookie cookie = new Cookie(fields[i].getName(), fields[i].get(obj).toString());
                cookie.setPath("/");
                cookie.setSecure(false);
                cookie.setDomain(domain);
                response.addCookie(cookie);
            }
        }

    }

    public void removeAll(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie allCookies[] = request.getCookies();
        if(allCookies != null)
        {
            for(int inx = 0; inx < allCookies.length; inx++)
            {
                allCookies[inx].setMaxAge(0);
                response.addCookie(allCookies[inx]);
            }

        }
    }

    public void removeAllValues(HttpServletResponse response, Object obj)
        throws Exception
    {
        Class c = obj.getClass();
        Field fields[] = c.getFields();
        for(int i = 0; i < fields.length; i++)
        {
            Class fc = fields[i].getType();
            int mod = fc.getModifiers();
            if(Modifier.isPublic(mod) && fc.isAssignableFrom(java.lang.String.class) && (!fc.isArray() && !Modifier.isStatic(mod)))
            {
                Cookie cookie = new Cookie(fields[i].getName(), fields[i].get(obj).toString());
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

    }

    public void removeValue(HttpServletResponse response, String name, String value)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private Hashtable cookieTable;
}
