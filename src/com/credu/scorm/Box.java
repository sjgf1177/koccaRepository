package com.credu.scorm;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

// Referenced classes of package alexit.lib.util:
//            FormatUtil, DateUtil, StringUtil

public class Box extends Hashtable
{

    public Box(String name)
    {
        this.name = null;
        this.name = name;
    }

    public Object clone()
    {
        Box newbox = new Box(name);
        Hashtable src = this;
        Hashtable target = newbox;
        String key;
        Object value;
        for(Enumeration e = src.keys(); e.hasMoreElements(); target.put(key, value))
        {
            key = (String)e.nextElement();
            value = src.get(key);
        }

        return newbox;
    }

    public void copyToEntity(Object entity)
    {
        if(entity == null)
            throw new NullPointerException("trying to copy from box to null entity class");
        Class c = entity.getClass();
        Field field[] = c.getFields();
        for(int i = 0; i < field.length; i++)
            try
            {
                String fieldtype = field[i].getType().getName();
                String fieldname = field[i].getName();
                if(containsKey(fieldname))
                    if(fieldtype.equals("java.lang.String"))
                        field[i].set(entity, getString(fieldname));
                    else
                    if(fieldtype.equals("int"))
                        field[i].setInt(entity, getInt(fieldname));
                    else
                    if(fieldtype.equals("double"))
                        field[i].setDouble(entity, getDouble(fieldname));
                    else
                    if(fieldtype.equals("long"))
                        field[i].setLong(entity, getLong(fieldname));
                    else
                    if(fieldtype.equals("float"))
                        field[i].set(entity, new Float(getDouble(fieldname)));
                    else
                    if(fieldtype.equals("boolean"))
                        field[i].setBoolean(entity, getBoolean(fieldname));
            }
            catch(Exception exception) { }

    }

    public String get(String key)
    {
        return getString(key);
    }

    public String getThdComma(String key)
    {
        return FormatUtil.insertComma(get(key));
    }

    public boolean getBoolean(String key)
    {
        String value = getString(key);
        boolean isTrue = false;
        try
        {
            isTrue = (new Boolean(value)).booleanValue();
        }
        catch(Exception exception) { }
        return isTrue;
    }

    public double getDouble(String key)
    {
        String value = removeComma(getString(key));
        if(value.equals(""))
            return 0.0D;
        double num = 0.0D;
        try
        {
            num = Double.valueOf(value).doubleValue();
        }
        catch(Exception e)
        {
            num = 0.0D;
        }
        return num;
    }

    public double getFloat(String key)
    {
        return (double)(float)getDouble(key);
    }

    public int getInt(String key)
    {
        double value = getDouble(key);
        return (int)value;
    }

    public long getLong(String key)
    {
        String value = removeComma(getString(key));
        if(value.equals(""))
            return 0L;
        long lvalue = 0L;
        try
        {
            lvalue = Long.valueOf(value).longValue();
        }
        catch(Exception e)
        {
            lvalue = 0L;
        }
        return lvalue;
    }

    public String getDateParams(String key)
    {
        return getString(key + "1") + getString(key + "2") + getString(key + "3");
    }

    public String getString(String key)
    {
        String value = "";
        try
        {
            Object o = super.get(key);
            Class c = o.getClass();
            if(o == null)
                value = "";
            else
            if(c.isArray())
            {
                int length = Array.getLength(o);
                if(length == 0)
                {
                    value = "";
                } else
                {
                    for(int i = 0; i < Array.getLength(o); i++)
                    {
                        Object item = Array.get(o, i);
                        if(item != null)
                            value = value + item.toString();
                    }

                }
            } else
            {
                value = o.toString();
            }
        }
        catch(Exception e)
        {
            value = "";
        }
        return value;
    }

    public Vector getVector(String key)
    {
        Vector vector = new Vector();
        try
        {
            Object o = super.get(key);
            Class c = o.getClass();
            if(o != null)
                if(c.isArray())
                {
                    int length = Array.getLength(o);
                    if(length != 0)
                    {
                        for(int i = 0; i < length; i++)
                        {
                            Object tiem = Array.get(o, i);
                            if(tiem == null)
                                vector.addElement("");
                            else
                                vector.addElement(tiem.toString());
                        }

                    }
                } else
                {
                    vector.addElement(o.toString());
                }
        }
        catch(Exception exception) { }
        return vector;
    }

    public void put(String key, String value)
    {
        super.put(key, value);
    }

    private static String removeComma(String s)
    {
        if(s == null)
            return null;
        if(s.indexOf(",") != -1)
        {
            StringBuffer buf = new StringBuffer();
            for(int i = 0; i < s.length(); i++)
            {
                char c = s.charAt(i);
                if(c != ',')
                    buf.append(c);
            }

            return buf.toString();
        } else
        {
            return s;
        }
    }

    public Object getObject(String key)
    {
        try
        {
            return super.get(key);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public synchronized String toString()
    {
        int max = size() - 1;
        StringBuffer buf = new StringBuffer();
        Enumeration keys = keys();
        Enumeration objects = elements();
        buf.append("{");
        for(int i = 0; i <= max; i++)
        {
            String key = keys.nextElement().toString();
            String value = null;
            Object o = objects.nextElement();
            if(o == null)
            {
                value = "";
            } else
            {
                Class c = o.getClass();
                if(c.isArray())
                {
                    int length = Array.getLength(o);
                    if(length == 0)
                        value = "";
                    else
                    if(length == 1)
                    {
                        Object item = Array.get(o, 0);
                        if(item == null)
                            value = "";
                        else
                            value = item.toString();
                    } else
                    {
                        StringBuffer valueBuf = new StringBuffer();
                        valueBuf.append("[");
                        for(int j = 0; j < length; j++)
                        {
                            Object item = Array.get(o, j);
                            if(item != null)
                                valueBuf.append(item.toString());
                            if(j < length - 1)
                                valueBuf.append(",");
                        }

                        valueBuf.append("]");
                        value = valueBuf.toString();
                    }
                } else
                {
                    value = o.toString();
                }
            }
            buf.append(key + "=" + value);
            if(i < max)
                buf.append(", ");
        }

        buf.append("}");
        return "Box[" + name + "]=" + buf.toString();
    }

    public String get(String key, String target)
    {
        String tmp = getString(key);
        return tmp.equals("") ? target : tmp;
    }

    public String get(String key, int fmt)
    {
        String tmp = "";
        switch(fmt)
        {
        case 1: // '\001'
            tmp = DateUtil.defFmtDate(get(key));
            break;

        case 2: // '\002'
            tmp = DateUtil.defFmtDateTime(get(key));
            break;

        case 3: // '\003'
            tmp = StringUtil.getComma(get(key));
            break;
        }
        return tmp;
    }

    public String get(String key, int fmt, String target)
    {
        String tmp = get(key, fmt);
        return tmp.equals("") ? target : tmp;
    }

    public static final int DEF_DATE_FMT = 1;
    public static final int DEF_DATETIME_FMT = 2;
    public static final int THOUSAND_COMMA = 3;
    protected String name;
}
