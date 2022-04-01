// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MacBinaryDecoderOutputStream.java

package com.credu.scorm.multi.multipart;

import java.io.*;

public class MacBinaryDecoderOutputStream extends FilterOutputStream
{

    public MacBinaryDecoderOutputStream(OutputStream outputstream)
    {
        super(outputstream);
        bytesFiltered = 0;
        dataForkLength = 0;
    }

    public void write(int i)
        throws IOException
    {
        if(bytesFiltered <= 86 && bytesFiltered >= 83)
        {
            int j = (86 - bytesFiltered) * 8;
            dataForkLength = dataForkLength | (i & 0xff) << j;
        } else
        if(bytesFiltered < 128 + dataForkLength && bytesFiltered >= 128)
            out.write(i);
        bytesFiltered++;
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        if(bytesFiltered >= 128 + dataForkLength)
            bytesFiltered += j;
        else
        if(bytesFiltered >= 128 && bytesFiltered + j <= 128 + dataForkLength)
        {
            out.write(abyte0, i, j);
            bytesFiltered += j;
        } else
        {
            for(int k = 0; k < j; k++)
                write(abyte0[i + k]);

        }
    }

    private int bytesFiltered;
    private int dataForkLength;
}
