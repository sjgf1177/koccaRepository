// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LimitedServletInputStream.java

package com.credu.scorm.multi.multipart;

import java.io.IOException;
import javax.servlet.ServletInputStream;

public class LimitedServletInputStream extends ServletInputStream
{

    public LimitedServletInputStream(ServletInputStream servletinputstream, int i)
    {
        totalRead = 0;
        in = servletinputstream;
        totalExpected = i;
    }

    public int readLine(byte abyte0[], int i, int j)
        throws IOException
    {
        int l = totalExpected - totalRead;
        if(l <= 0)
            return -1;
        int k = in.readLine(abyte0, i, Math.min(l, j));
        if(k > 0)
            totalRead += k;
        return k;
    }

    public int read()
        throws IOException
    {
        if(totalRead >= totalExpected)
            return -1;
        int i = in.read();
        if(i != -1)
            totalRead++;
        return i;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int l = totalExpected - totalRead;
        if(l <= 0)
            return -1;
        int k = in.read(abyte0, i, Math.min(l, j));
        if(k > 0)
            totalRead += k;
        return k;
    }

    private ServletInputStream in;
    private int totalExpected;
    private int totalRead;
}
