package com.credu.scorm;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

// Referenced classes of package alexit.lib.util:
//            StringUtil, Box

public class EntityUtil
{

    public EntityUtil()
    {
    }

    public static synchronized void toEUC_KR(Object entity)
    {
        if(entity == null)
            throw new NullPointerException("trying to print from EntityUtil to null entity class");
        Class c = entity.getClass();
        Field field[] = c.getFields();
        for(int i = 0; i < field.length; i++)
            try
            {
                if(field[i].getType().getName().equals("java.lang.String") && field[i].get(entity) != null)
                    field[i].set(entity, StringUtil.getEUC_KR(field[i].get(entity).toString()));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

    }

    public static synchronized String toString(Object entity)
    {
        StringBuffer buf = new StringBuffer();
        if(entity == null)
        {
            buf.append("null object");
        } else
        {
            Class c = entity.getClass();
            String fullname = c.getName();
            String name = null;
            int index = fullname.lastIndexOf('.');
            if(index == -1)
                name = fullname;
            else
                name = fullname.substring(index + 1);
            buf.append(name + ":{");
            Field fields[] = c.getFields();
            for(int i = 0; i < fields.length; i++)
                try
                {
                    if(i != 0)
                        buf.append(',');
                    buf.append(fields[i].getName() + '=');
                    Object f = fields[i].get(entity);
                    Class fc = fields[i].getType();
                    if(fc.isArray())
                    {
                        buf.append('[');
                        int length = Array.getLength(f);
                        for(int j = 0; j < length; j++)
                        {
                            if(j != 0)
                                buf.append(',');
                            Object element = Array.get(f, j);
                            buf.append(element.toString());
                        }

                        buf.append(']');
                    } else
                    {
                        buf.append(f.toString());
                    }
                }
                catch(Exception exception) { }

            buf.append('}');
        }
        return buf.toString();
    }

    public static Object[] clone(Object objects[])
    {
        int length = objects.length;
        Class c = ((Object) (objects)).getClass().getComponentType();
        Object array = Array.newInstance(c, length);
        for(int i = 0; i < length; i++)
            Array.set(array, i, clone(objects[i]));

        return (Object[])array;
    }

    public static Object clone(Object object)
    {
        Class c = object.getClass();
        Object newObject = null;
        try
        {
            newObject = c.newInstance();
        }
        catch(Exception e)
        {
            return null;
        }
        Field field[] = c.getFields();
        for(int i = 0; i < field.length; i++)
            try
            {
                Object f = field[i].get(object);
                field[i].set(newObject, f);
            }
            catch(Exception exception) { }

        return newObject;
    }

    public static Vector clone(Vector objects)
    {
        Vector newObjects = new Vector();
        Object o;
        for(Enumeration e = objects.elements(); e.hasMoreElements(); newObjects.addElement(clone(o)))
            o = e.nextElement();

        return newObjects;
    }

    public static void fixNull(Object o)
    {
        if(o == null)
            return;
        Class c = o.getClass();
        if(c.isPrimitive())
            return;
        Field fields[] = c.getFields();
        for(int i = 0; i < fields.length; i++)
            try
            {
                Object f = fields[i].get(o);
                Class fc = fields[i].getType();
                if(fc.getName().equals("java.lang.String"))
                    if(f == null)
                        fields[i].set(o, "");
                    else
                        fields[i].set(o, f);
            }
            catch(Exception exception) { }

    }

    public static void fixNullAll(Object o)
    {
        if(o == null)
            return;
        Class c = o.getClass();
        if(c.isPrimitive())
            return;
        if(c.isArray())
        {
            int length = Array.getLength(o);
            for(int i = 0; i < length; i++)
            {
                Object element = Array.get(o, i);
                fixNullAll(element);
            }

        } else
        {
            Field fields[] = c.getFields();
            for(int i = 0; i < fields.length; i++)
                try
                {
                    Object f = fields[i].get(o);
                    Class fc = fields[i].getType();
                    if(!fc.isPrimitive())
                        if(fc.getName().equals("java.lang.String"))
                        {
                            if(f == null)
                                fields[i].set(o, "");
                            else
                                fields[i].set(o, f);
                        } else
                        if(f != null)
                            fixNullAll(f);
                }
                catch(Exception exception) { }

        }
    }

    public static void fixNullAndTrim(Object o)
    {
        if(o == null)
            return;
        Class c = o.getClass();
        if(c.isPrimitive())
            return;
        Field fields[] = c.getFields();
        for(int i = 0; i < fields.length; i++)
            try
            {
                Object f = fields[i].get(o);
                Class fc = fields[i].getType();
                if(fc.getName().equals("java.lang.String"))
                    if(f == null)
                    {
                        fields[i].set(o, "");
                    } else
                    {
                        String item = ((String)f).trim();
                        fields[i].set(o, item);
                    }
            }
            catch(Exception exception) { }

    }

    public static void fixNullAndTrimAll(Object o)
    {
        if(o == null)
            return;
        Class c = o.getClass();
        if(c.isPrimitive())
            return;
        if(c.isArray())
        {
            int length = Array.getLength(o);
            for(int i = 0; i < length; i++)
            {
                Object element = Array.get(o, i);
                fixNullAndTrimAll(element);
            }

        } else
        {
            Field fields[] = c.getFields();
            for(int i = 0; i < fields.length; i++)
                try
                {
                    Object f = fields[i].get(o);
                    Class fc = fields[i].getType();
                    if(!fc.isPrimitive())
                        if(fc.getName().equals("java.lang.String"))
                        {
                            if(f == null)
                            {
                                fields[i].set(o, "");
                            } else
                            {
                                String item = ((String)f).trim();
                                fields[i].set(o, item);
                            }
                        } else
                        if(f != null)
                            fixNullAndTrimAll(f);
                }
                catch(Exception exception) { }

        }
    }

    public static Properties getProperties(Object entity)
    {
        Properties prop = new Properties();
        Class c = entity.getClass();
        Field field[] = c.getFields();
        try
        {
            for(int i = 0; i < field.length; i++)
            {
                Class fc = field[i].getType();
                if(!fc.isArray())
                    prop.setProperty(field[i].getName(), field[i].get(entity).toString());
            }

        }
        catch(Exception exception) { }
        return prop;
    }

    public static synchronized Object entityParamCopy(Object fromEnt, Object toEnt)
    {
        if(fromEnt == null || toEnt == null)
            throw new NullPointerException(" null entity error");
        Box box = new Box("");
        Class c = fromEnt.getClass();
        Field field[] = c.getFields();
        for(int i = 0; i < field.length; i++)
            try
            {
                String fieldtype = field[i].getType().getName();
                String fieldname = field[i].getName();
                box.put(fieldname, c.getField(fieldname).get(fromEnt));
            }
            catch(Exception exception) { }

        box.copyToEntity(toEnt);
        return toEnt;
    }
}
