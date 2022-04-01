// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParameterParser.java

package com.credu.scorm.multi;

import java.io.UnsupportedEncodingException;
import java.util.Vector;
import javax.servlet.ServletRequest;

// Referenced classes of package com.credu.scorm.multi:
//            ParameterNotFoundException

public class ParameterParser
{

    public ParameterParser(ServletRequest servletrequest)
    {
        req = servletrequest;
    }

    public void setCharacterEncoding(String s)
        throws UnsupportedEncodingException
    {
        new String("".getBytes("8859_1"), s);
        encoding = s;
    }

    public String getStringParameter(String s)
        throws ParameterNotFoundException
    {
        String as[] = req.getParameterValues(s);
        if(as == null)
            throw new ParameterNotFoundException(s + " not found");
        if(as[0].length() == 0)
            throw new ParameterNotFoundException(s + " was empty");
        if(encoding == null)
            return as[0];
        try
        {
            return new String(as[0].getBytes("8859_1"), encoding);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            return as[0];
        }
    }

    public String getStringParameter(String s, String s1)
    {
        try
        {
            return getStringParameter(s);
        }
        catch(Exception exception)
        {
            return s1;
        }
    }

    public boolean getBooleanParameter(String s)
        throws ParameterNotFoundException, NumberFormatException
    {
        String s1 = getStringParameter(s).toLowerCase();
        if(s1.equalsIgnoreCase("true") || s1.equalsIgnoreCase("on") || s1.equalsIgnoreCase("yes"))
            return true;
        if(s1.equalsIgnoreCase("false") || s1.equalsIgnoreCase("off") || s1.equalsIgnoreCase("no"))
            return false;
        else
            throw new NumberFormatException("Parameter " + s + " value " + s1 + " is not a boolean");
    }

    public boolean getBooleanParameter(String s, boolean flag)
    {
        try
        {
            return getBooleanParameter(s);
        }
        catch(Exception exception)
        {
            return flag;
        }
    }

    public byte getByteParameter(String s)
        throws ParameterNotFoundException, NumberFormatException
    {
        return Byte.parseByte(getStringParameter(s));
    }

    public byte getByteParameter(String s, byte byte0)
    {
        try
        {
            return getByteParameter(s);
        }
        catch(Exception exception)
        {
            return byte0;
        }
    }

    public char getCharParameter(String s)
        throws ParameterNotFoundException
    {
        String s1 = getStringParameter(s);
        if(s1.length() == 0)
            throw new ParameterNotFoundException(s + " is empty string");
        else
            return s1.charAt(0);
    }

    public char getCharParameter(String s, char c)
    {
        try
        {
            return getCharParameter(s);
        }
        catch(Exception exception)
        {
            return c;
        }
    }

    public double getDoubleParameter(String s)
        throws ParameterNotFoundException, NumberFormatException
    {
        return (new Double(getStringParameter(s))).doubleValue();
    }

    public double getDoubleParameter(String s, double d)
    {
        try
        {
            return getDoubleParameter(s);
        }
        catch(Exception exception)
        {
            return d;
        }
    }

    public float getFloatParameter(String s)
        throws ParameterNotFoundException, NumberFormatException
    {
        return (new Float(getStringParameter(s))).floatValue();
    }

    public float getFloatParameter(String s, float f)
    {
        try
        {
            return getFloatParameter(s);
        }
        catch(Exception exception)
        {
            return f;
        }
    }

    public int getIntParameter(String s)
        throws ParameterNotFoundException, NumberFormatException
    {
        return Integer.parseInt(getStringParameter(s));
    }

    public int getIntParameter(String s, int i)
    {
        try
        {
            return getIntParameter(s);
        }
        catch(Exception exception)
        {
            return i;
        }
    }

    public long getLongParameter(String s)
        throws ParameterNotFoundException, NumberFormatException
    {
        return Long.parseLong(getStringParameter(s));
    }

    public long getLongParameter(String s, long l)
    {
        try
        {
            return getLongParameter(s);
        }
        catch(Exception exception)
        {
            return l;
        }
    }

    public short getShortParameter(String s)
        throws ParameterNotFoundException, NumberFormatException
    {
        return Short.parseShort(getStringParameter(s));
    }

    public short getShortParameter(String s, short word0)
    {
        try
        {
            return getShortParameter(s);
        }
        catch(Exception exception)
        {
            return word0;
        }
    }

    public String[] getMissingParameters(String as[])
    {
        Vector vector = new Vector();
        for(int i = 0; i < as.length; i++)
        {
            String s = getStringParameter(as[i], null);
            if(s == null)
                vector.addElement(as[i]);
        }

        if(vector.size() == 0)
        {
            return null;
        } else
        {
            String as1[] = new String[vector.size()];
            vector.copyInto(as1);
            return as1;
        }
    }

    private ServletRequest req;
    private String encoding;
}
