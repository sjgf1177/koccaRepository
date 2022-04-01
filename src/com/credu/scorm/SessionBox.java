package com.credu.scorm;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.http.HttpSession;

// Referenced classes of package alexit.lib.util:
//            Box

public class SessionBox extends Box
{

    public SessionBox(HttpSession session, String name)
    {
        super(name);
        this.session = null;
        this.session = session;
    }

    public Object clone()
    {
        SessionBox sessionbox = new SessionBox(session, super.name);
        Hashtable src = this;
        Hashtable target = sessionbox;
        String key;
        Object value;
        for(Enumeration e = src.keys(); e.hasMoreElements(); target.put(key, value))
        {
            key = (String)e.nextElement();
            value = src.get(key);
        }

        return sessionbox;
    }

    public String getName()
    {
        return super.name;
    }

    public Object getObject(String key)
    {
        return super.get(key);
    }

    public void putObject(String key, Object object)
    {
        super.put(key, object);
    }

    public void release()
    {
        try
        {
            session.removeAttribute(super.name);
        }
        catch(Exception exception) { }
        clear();
    }

    public String toString()
    {
        return "Sessoin" + super.toString();
    }

    private HttpSession session;
}
