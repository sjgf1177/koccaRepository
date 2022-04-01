// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CookieParser.java

package com.credu.scorm.multi;

import java.util.Hashtable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.credu.scorm.multi:
//            CookieNotFoundException

public class CookieParser
{

    public CookieParser(HttpServletRequest httpservletrequest)
    {
        cookieJar = new Hashtable();
        req = httpservletrequest;
        parseCookies();
    }

    void parseCookies()
    {
        Cookie acookie[] = req.getCookies();
        if(acookie != null)
        {
            for(int i = 0; i < acookie.length; i++)
            {
                String s = acookie[i].getName();
                String s1 = acookie[i].getValue();
                cookieJar.put(s, s1);
            }

        }
    }

    public String getStringCookie(String s)
        throws CookieNotFoundException
    {
        String s1 = (String)cookieJar.get(s);
        if(s1 == null)
            throw new CookieNotFoundException(s + " not found");
        else
            return s1;
    }

    public String getStringCookie(String s, String s1)
    {
        try
        {
            return getStringCookie(s);
        }
        catch(Exception exception)
        {
            return s1;
        }
    }

    public boolean getBooleanCookie(String s)
        throws CookieNotFoundException
    {
        return (new Boolean(getStringCookie(s))).booleanValue();
    }

    public boolean getBooleanCookie(String s, boolean flag)
    {
        try
        {
            return getBooleanCookie(s);
        }
        catch(Exception exception)
        {
            return flag;
        }
    }

    public byte getByteCookie(String s)
        throws CookieNotFoundException, NumberFormatException
    {
        return Byte.parseByte(getStringCookie(s));
    }

    public byte getByteCookie(String s, byte byte0)
    {
        try
        {
            return getByteCookie(s);
        }
        catch(Exception exception)
        {
            return byte0;
        }
    }

    public char getCharCookie(String s)
        throws CookieNotFoundException
    {
        String s1 = getStringCookie(s);
        if(s1.length() == 0)
            throw new CookieNotFoundException(s + " is empty string");
        else
            return s1.charAt(0);
    }

    public char getCharCookie(String s, char c)
    {
        try
        {
            return getCharCookie(s);
        }
        catch(Exception exception)
        {
            return c;
        }
    }

    public double getDoubleCookie(String s)
        throws CookieNotFoundException, NumberFormatException
    {
        return (new Double(getStringCookie(s))).doubleValue();
    }

    public double getDoubleCookie(String s, double d)
    {
        try
        {
            return getDoubleCookie(s);
        }
        catch(Exception exception)
        {
            return d;
        }
    }

    public float getFloatCookie(String s)
        throws CookieNotFoundException, NumberFormatException
    {
        return (new Float(getStringCookie(s))).floatValue();
    }

    public float getFloatCookie(String s, float f)
    {
        try
        {
            return getFloatCookie(s);
        }
        catch(Exception exception)
        {
            return f;
        }
    }

    public int getIntCookie(String s)
        throws CookieNotFoundException, NumberFormatException
    {
        return Integer.parseInt(getStringCookie(s));
    }

    public int getIntCookie(String s, int i)
    {
        try
        {
            return getIntCookie(s);
        }
        catch(Exception exception)
        {
            return i;
        }
    }

    public long getLongCookie(String s)
        throws CookieNotFoundException, NumberFormatException
    {
        return Long.parseLong(getStringCookie(s));
    }

    public long getLongCookie(String s, long l)
    {
        try
        {
            return getLongCookie(s);
        }
        catch(Exception exception)
        {
            return l;
        }
    }

    public short getShortCookie(String s)
        throws CookieNotFoundException, NumberFormatException
    {
        return Short.parseShort(getStringCookie(s));
    }

    public short getShortCookie(String s, short word0)
    {
        try
        {
            return getShortCookie(s);
        }
        catch(Exception exception)
        {
            return word0;
        }
    }

    private HttpServletRequest req;
    private Hashtable cookieJar;
}
