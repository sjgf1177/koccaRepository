// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CacheHttpServlet.java

package com.credu.scorm.multi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

class CacheServletOutputStream extends ServletOutputStream
{

    CacheServletOutputStream(ServletOutputStream servletoutputstream)
    {
        delegate = servletoutputstream;
        cache = new ByteArrayOutputStream(4096);
    }

    public ByteArrayOutputStream getBuffer()
    {
        return cache;
    }

    public void write(int i)
        throws IOException
    {
        delegate.write(i);
        cache.write(i);
    }

    public void write(byte abyte0[])
        throws IOException
    {
        delegate.write(abyte0);
        cache.write(abyte0);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        delegate.write(abyte0, i, j);
        cache.write(abyte0, i, j);
    }

    ServletOutputStream delegate;
    ByteArrayOutputStream cache;
}
