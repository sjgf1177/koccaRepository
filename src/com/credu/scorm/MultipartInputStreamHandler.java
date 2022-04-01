package com.credu.scorm;

import java.io.IOException;
import javax.servlet.ServletInputStream;

class MultipartInputStreamHandler
{

    public MultipartInputStreamHandler(ServletInputStream in, int totalExpected)
    {
        totalRead = 0;
        buf = new byte[8192];
        this.in = in;
        this.totalExpected = totalExpected;
    }

    public String readLine()
        throws IOException
    {
        StringBuffer sbuf = new StringBuffer();
        int result;
        do
        {
            result = readLine(buf, 0, buf.length);
            if(result != -1)
                sbuf.append(new String(buf, 0, result, "KSC5601"));
        } while(result == buf.length);
        if(sbuf.length() == 0)
        {
            return null;
        } else
        {
            sbuf.setLength(sbuf.length() - 2);
            return sbuf.toString();
        }
    }

    public int readLine(byte b[], int off, int len)
        throws IOException
    {
        if(totalRead >= totalExpected)
            return -1;
        int result = in.readLine(b, off, len);
        if(result > 0)
            totalRead += result;
        return result;
    }

    ServletInputStream in;
    int totalExpected;
    int totalRead;
    byte buf[];
}
