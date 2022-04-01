package com.credu.scorm;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class HashVt
    implements Serializable
{
    public class HashIndex
        implements Serializable
    {

        public int idx;
        public Object key;

        public HashIndex(int idx, Object key)
        {
            this.idx = idx;
            this.key = key;
        }
    }


    public HashVt()
    {
        ht = new Hashtable();
        htkey = new Hashtable();
        index = 0;
    }

    public void put(Object key, Object value)
    {
        Vector vt = (Vector)ht.get(key);
        if(vt == null)
            vt = new Vector();
        htkey.put(String.valueOf(index), new HashIndex(vt.size(), key));
        index++;
        vt.addElement(value);
        ht.put(key, vt);
    }

    public Vector getVt(Object key)
    {
        return ht.get(key) != null ? (Vector)ht.get(key) : null;
    }

    public Object get(Object key)
    {
        try
        {
            return ((Vector)ht.get(key)).get(0);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public Object get(int i)
    {
        try
        {
            HashIndex hi = (HashIndex)htkey.get(String.valueOf(i));
            return ((Vector)ht.get(hi.key)).get(hi.idx);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public int getSize(Object key)
    {
        return ht.get(key) != null ? ((Vector)ht.get(key)).size() : 0;
    }

    public int getSize()
    {
        return index;
    }

    public Object getObjectAt(Object key, int i)
        throws Exception
    {
        return ht.get(key) != null ? ((Vector)ht.get(key)).get(i) : null;
    }

    private Hashtable ht;
    private Hashtable htkey;
    private int index;
}
