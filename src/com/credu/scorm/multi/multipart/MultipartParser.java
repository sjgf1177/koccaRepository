// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultipartParser.java

package com.credu.scorm.multi.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.credu.scorm.multi.multipart:
//            BufferedServletInputStream, LimitedServletInputStream, ParamPart, FilePart, 
//            Part

public class MultipartParser
{

    public MultipartParser(HttpServletRequest httpservletrequest, int i)
        throws IOException
    {
        this(httpservletrequest, i, true, true);
    }

    public MultipartParser(HttpServletRequest httpservletrequest, int i, boolean flag, boolean flag1)
        throws IOException
    {
        buf = new byte[8192];
        encoding = DEFAULT_ENCODING;
        String s = null;
        String s1 = httpservletrequest.getHeader("Content-Type");
        String s2 = httpservletrequest.getContentType();
        if(s1 == null && s2 != null)
            s = s2;
        else
        if(s2 == null && s1 != null)
            s = s1;
        else
        if(s1 != null && s2 != null)
            s = s1.length() <= s2.length() ? s2 : s1;
        if(s == null || !s.toLowerCase().startsWith("multipart/form-data"))
            throw new IOException("Posted content type isn't multipart/form-data");
        int j = httpservletrequest.getContentLength();
        if(j > i)
            throw new IOException("Posted content length of " + j + " exceeds limit of " + i);
        String s3 = extractBoundary(s);
        if(s3 == null)
            throw new IOException("Separation boundary was not specified");
        Object obj = httpservletrequest.getInputStream();
        if(flag)
            obj = new BufferedServletInputStream(((ServletInputStream) (obj)));
        if(flag1)
            obj = new LimitedServletInputStream(((ServletInputStream) (obj)), j);
        in = ((ServletInputStream) (obj));
        boundary = s3;
        String s4 = readLine();
        if(s4 == null)
            throw new IOException("Corrupt form data: premature ending");
        if(!s4.startsWith(s3))
            throw new IOException("Corrupt form data: no leading boundary: " + s4 + " != " + s3);
        else
            return;
    }

    public void setEncoding(String s)
    {
        encoding = s;
    }

    public Part readNextPart()
        throws IOException
    {
        if(lastFilePart != null)
        {
            lastFilePart.getInputStream().close();
            lastFilePart = null;
        }
        Vector vector = new Vector();
        String s = readLine();
        if(s == null)
            return null;
        if(s.length() == 0)
            return null;
        String s1;
        for(; s != null && s.length() > 0; s = s1)
        {
            s1 = null;
            boolean flag = true;
            while(flag) 
            {
                s1 = readLine();
                if(s1 != null && (s1.startsWith(" ") || s1.startsWith("\t")))
                    s = s + s1;
                else
                    flag = false;
            }
            vector.addElement(s);
        }

        if(s == null)
            return null;
        String s2 = null;
        String s3 = null;
        String s4 = null;
        String s5 = "text/plain";
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
        {
            String s6 = (String)enumeration.nextElement();
            if(s6.toLowerCase().startsWith("content-disposition:"))
            {
                String as[] = extractDispositionInfo(s6);
                s2 = as[1];
                s3 = as[2];
                s4 = as[3];
            } else
            if(s6.toLowerCase().startsWith("content-type:"))
            {
                String s7 = extractContentType(s6);
                if(s7 != null)
                    s5 = s7;
            }
        }

        if(s3 == null)
            return new ParamPart(s2, in, boundary, encoding);
        if(s3.equals(""))
            s3 = null;
        lastFilePart = new FilePart(s2, in, boundary, s5, s3, s4);
        return lastFilePart;
    }

    private String extractBoundary(String s)
    {
        int i = s.lastIndexOf("boundary=");
        if(i == -1)
            return null;
        String s1 = s.substring(i + 9);
        if(s1.charAt(0) == '"')
        {
            int j = s1.lastIndexOf(34);
            s1 = s1.substring(1, j);
        }
        s1 = "--" + s1;
        return s1;
    }

    private String[] extractDispositionInfo(String s)
        throws IOException
    {
        String as[] = new String[4];
        String s1 = s;
        s = s1.toLowerCase();
        int i = s.indexOf("content-disposition: ");
        int j = s.indexOf(";");
        if(i == -1 || j == -1)
            throw new IOException("Content disposition corrupt: " + s1);
        String s2 = s.substring(i + 21, j);
        if(!s2.equals("form-data"))
            throw new IOException("Invalid content disposition: " + s2);
        i = s.indexOf("name=\"", j);
        j = s.indexOf("\"", i + 7);
        if(i == -1 || j == -1)
            throw new IOException("Content disposition corrupt: " + s1);
        String s3 = s1.substring(i + 6, j);
        String s4 = null;
        String s5 = null;
        i = s.indexOf("filename=\"", j + 2);
        j = s.indexOf("\"", i + 10);
        if(i != -1 && j != -1)
        {
            s4 = s1.substring(i + 10, j);
            s5 = s4;
            int k = Math.max(s4.lastIndexOf(47), s4.lastIndexOf(92));
            if(k > -1)
                s4 = s4.substring(k + 1);
        }
        as[0] = s2;
        as[1] = s3;
        as[2] = s4;
        as[3] = s5;
        return as;
    }

    private String extractContentType(String s)
        throws IOException
    {
        String s1 = null;
        String s2 = s;
        s = s2.toLowerCase();
        if(s.startsWith("content-type"))
        {
            int i = s.indexOf(" ");
            if(i == -1)
                throw new IOException("Content type corrupt: " + s2);
            s1 = s.substring(i + 1);
        } else
        if(s.length() != 0)
            throw new IOException("Malformed line after disposition: " + s2);
        return s1;
    }

    private String readLine()
        throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i;
        do
        {
            i = in.readLine(buf, 0, buf.length);
            if(i != -1)
                stringbuffer.append(new String(buf, 0, i, encoding));
        } while(i == buf.length);
        if(stringbuffer.length() == 0)
            return null;
        int j = stringbuffer.length();
        if(j >= 2 && stringbuffer.charAt(j - 2) == '\r')
            stringbuffer.setLength(j - 2);
        else
        if(j >= 1 && stringbuffer.charAt(j - 1) == '\n')
            stringbuffer.setLength(j - 1);
        return stringbuffer.toString();
    }

    private ServletInputStream in;
    private String boundary;
    private FilePart lastFilePart;
    private byte buf[];
    private static String DEFAULT_ENCODING = "ISO-8859-1";
    private String encoding;

}
