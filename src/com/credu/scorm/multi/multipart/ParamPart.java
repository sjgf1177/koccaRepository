// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParamPart.java

package com.credu.scorm.multi.multipart;

import java.io.*;
import javax.servlet.ServletInputStream;

// Referenced classes of package com.credu.scorm.multi.multipart:
//            Part, PartInputStream

public class ParamPart extends Part
{

    ParamPart(String s, ServletInputStream servletinputstream, String s1, String s2)
        throws IOException
    {
        super(s);
        encoding = s2;
        PartInputStream partinputstream = new PartInputStream(servletinputstream, s1);
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(512);
        byte abyte0[] = new byte[128];
        int i;
        while((i = partinputstream.read(abyte0)) != -1) 
            bytearrayoutputstream.write(abyte0, 0, i);
        partinputstream.close();
        bytearrayoutputstream.close();
        value = bytearrayoutputstream.toByteArray();
    }

    public byte[] getValue()
    {
        return value;
    }

    public String getStringValue()
        throws UnsupportedEncodingException
    {
        return getStringValue(encoding);
    }

    public String getStringValue(String s)
        throws UnsupportedEncodingException
    {
        return new String(value, s);
    }

    public boolean isParam()
    {
        return true;
    }

    private byte value[];
    private String encoding;
}
