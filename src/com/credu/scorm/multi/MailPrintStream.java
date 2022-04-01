// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailMessage.java

package com.credu.scorm.multi;

import java.io.OutputStream;
import java.io.PrintStream;

class MailPrintStream extends PrintStream
{

    public MailPrintStream(OutputStream outputstream)
    {
        super(outputstream, true);
    }

    public void write(int i)
    {
        if(i == 10 && lastChar != 13)
        {
            rawWrite(13);
            rawWrite(i);
        } else
        if(i == 46 && lastChar == 10)
        {
            rawWrite(46);
            rawWrite(i);
        } else
        if(i != 10 && lastChar == 13)
        {
            rawWrite(10);
            rawWrite(i);
            if(i == 46)
                rawWrite(46);
        } else
        {
            rawWrite(i);
        }
        lastChar = i;
    }

    public void write(byte abyte0[], int i, int j)
    {
        for(int k = 0; k < j; k++)
            write(abyte0[i + k]);

    }

    void rawWrite(int i)
    {
        super.write(i);
    }

    void rawPrint(String s)
    {
        int i = s.length();
        for(int j = 0; j < i; j++)
            rawWrite(s.charAt(j));

    }

    int lastChar;
}
