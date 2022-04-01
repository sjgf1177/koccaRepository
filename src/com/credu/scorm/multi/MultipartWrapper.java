// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultipartWrapper.java

package com.credu.scorm.multi;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// Referenced classes of package com.credu.scorm.multi:
//            MultipartRequest

public class MultipartWrapper extends HttpServletRequestWrapper
{

    public MultipartWrapper(HttpServletRequest httpservletrequest, String s)
        throws IOException
    {
        super(httpservletrequest);
        mreq = null;
        mreq = new MultipartRequest(httpservletrequest, s);
    }

    public Enumeration getParameterNames()
    {
        return mreq.getParameterNames();
    }

    public String getParameter(String s)
    {
        return mreq.getParameter(s);
    }

    public String[] getParameterValues(String s)
    {
        return mreq.getParameterValues(s);
    }

    public Map getParameterMap()
    {
        HashMap hashmap = new HashMap();
        String s;
        for(Enumeration enumeration = getParameterNames(); enumeration.hasMoreElements(); hashmap.put(s, mreq.getParameterValues(s)))
            s = (String)enumeration.nextElement();

        return hashmap;
    }

    public Enumeration getFileNames()
    {
        return mreq.getFileNames();
    }

    public String getFilesystemName(String s)
    {
        return mreq.getFilesystemName(s);
    }

    public String getContentType(String s)
    {
        return mreq.getContentType(s);
    }

    public File getFile(String s)
    {
        return mreq.getFile(s);
    }

    MultipartRequest mreq;
}
