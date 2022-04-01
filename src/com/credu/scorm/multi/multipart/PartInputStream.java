// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PartInputStream.java

package com.credu.scorm.multi.multipart;

import java.io.*;
import javax.servlet.ServletInputStream;

public class PartInputStream extends FilterInputStream
{

    PartInputStream(ServletInputStream servletinputstream, String s)
        throws IOException
    {
        super(servletinputstream);
        buf = new byte[0x10000];
        boundary = s;
    }

    private void fill()
        throws IOException
    {
        if(eof)
            return;
        if(count > 0)
            if(count - pos == 2)
            {
                System.arraycopy(buf, pos, buf, 0, count - pos);
                count -= pos;
                pos = 0;
            } else
            {
                throw new IllegalStateException("fill() detected illegal buffer state");
            }
        boolean flag = false;
        int i;
        for(int j = buf.length - boundary.length(); count < j; count += i)
        {
            i = ((ServletInputStream)in).readLine(buf, count, buf.length - count);
            if(i == -1)
                throw new IOException("unexpected end of part");
            if(i < boundary.length())
                continue;
            eof = true;
            for(int k = 0; k < boundary.length(); k++)
            {
                if(boundary.charAt(k) == buf[count + k])
                    continue;
                eof = false;
                break;
            }

            if(eof)
                break;
        }

    }

    public int read()
        throws IOException
    {
        if(count - pos <= 2)
        {
            fill();
            if(count - pos <= 2)
                return -1;
        }
        return buf[pos++] & 0xff;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = 0;
        if(j == 0)
            return 0;
        int l = count - pos - 2;
        if(l <= 0)
        {
            fill();
            l = count - pos - 2;
            if(l <= 0)
                return -1;
        }
        int j1 = Math.min(j, l);
        System.arraycopy(buf, pos, abyte0, i, j1);
        pos += j1;
        for(k += j1; k < j; k += j1)
        {
            fill();
            int i1 = count - pos - 2;
            if(i1 <= 0)
                return k;
            j1 = Math.min(j - k, i1);
            System.arraycopy(buf, pos, abyte0, i + k, j1);
            pos += j1;
        }

        return k;
    }

    public int available()
        throws IOException
    {
        int i = (count - pos - 2) + in.available();
        return i >= 0 ? i : 0;
    }

    public void close()
        throws IOException
    {
        if(!eof)
            while(read(buf, 0, buf.length) != -1) ;
    }

    private String boundary;
    private byte buf[];
    private int count;
    private int pos;
    private boolean eof;
}
