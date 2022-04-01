// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultipartResponse.java

package com.credu.scorm.multi;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class MultipartResponse
{

    public MultipartResponse(HttpServletResponse httpservletresponse)
        throws IOException
    {
        endedLastResponse = true;
        res = httpservletresponse;
        out = res.getOutputStream();
        res.setContentType("multipart/x-mixed-replace;boundary=End");
        out.println();
        out.println("--End");
    }

    public void startResponse(String s)
        throws IOException
    {
        if(!endedLastResponse)
            endResponse();
        out.println("Content-type: " + s);
        out.println();
        endedLastResponse = false;
    }

    public void endResponse()
        throws IOException
    {
        out.println();
        out.println("--End");
        out.flush();
        endedLastResponse = true;
    }

    public void finish()
        throws IOException
    {
        out.println("--End--");
        out.flush();
    }

    HttpServletResponse res;
    ServletOutputStream out;
    boolean endedLastResponse;
}
