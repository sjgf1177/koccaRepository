// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BufferedServletInputStream.java

package com.credu.scorm.multi.multipart;

import java.io.IOException;
import javax.servlet.ServletInputStream;

public class BufferedServletInputStream extends ServletInputStream
{

    public BufferedServletInputStream(ServletInputStream servletinputstream)
    {
        buf = new byte[0x10000];
        in = servletinputstream;
    }

    private void fill()
        throws IOException
    {
        int i = in.read(buf, 0, buf.length);
        if(i > 0)
        {
            pos = 0;
            count = i;
        }
    }

    public int readLine(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = 0;
        if(j == 0)
            return 0;
        int l = count - pos;
        if(l <= 0)
        {
            fill();
            l = count - pos;
            if(l <= 0)
                return -1;
        }
        int j1 = Math.min(j, l);
        int k1 = findeol(buf, pos, j1);
        if(k1 != -1)
            j1 = k1;
        System.arraycopy(buf, pos, abyte0, i, j1);
        pos += j1;
        for(k += j1; k < j && k1 == -1; k += j1)
        {
            fill();
            int i1 = count - pos;
            if(i1 <= 0)
                return k;
            j1 = Math.min(j - k, i1);
            k1 = findeol(buf, pos, j1);
            if(k1 != -1)
                j1 = k1;
            System.arraycopy(buf, pos, abyte0, i + k, j1);
            pos += j1;
        }

        return k;
    }

    private static int findeol(byte abyte0[], int i, int j)
    {
        int k = i + j;
        for(int l = i; l < k;)
            if(abyte0[l++] == 10)
                return l - i;

        return -1;
    }

    public int read()
        throws IOException
    {
        if(count <= pos)
        {
            fill();
            if(count <= pos)
                return -1;
        }
        return buf[pos++] & 0xff;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k;
        int i1;
        for(k = 0; k < j; k += i1)
        {
            int l = count - pos;
            if(l <= 0)
            {
                fill();
                l = count - pos;
                if(l <= 0)
                    if(k > 0)
                        return k;
                    else
                        return -1;
            }
            i1 = Math.min(j - k, l);
            System.arraycopy(buf, pos, abyte0, i + k, i1);
            pos += i1;
        }

        return k;
    }

    private ServletInputStream in;
    private byte buf[];
    private int count;
    private int pos;
}
