// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   CacheHttpServlet.java

package com.credu.scorm.multi;

import java.io.*;
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

// Referenced classes of package com.credu.scorm.multi:
//            CacheServletOutputStream

public class CacheHttpServletResponse
    implements HttpServletResponse
{
   // @Override
    public int getStatus() {
        return 0;
    }

   // @Override
    public String getHeader(String s) {
        return null;
    }

   // @Override
    public Collection<String> getHeaders(String s) {
        return null;
    }

   // @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    CacheHttpServletResponse(HttpServletResponse httpservletresponse)
    {
        delegate = httpservletresponse;
        try
        {
            out = new CacheServletOutputStream(httpservletresponse.getOutputStream());
        }
        catch(IOException ioexception)
        {
            System.out.println("Got IOException constructing cached response: " + ioexception.getMessage());
        }
        internalReset();
    }

    private void internalReset()
    {
        status = 200;
        headers = new Hashtable();
        contentLength = -1;
        contentType = null;
        locale = null;
        cookies = new Vector();
        didError = false;
        didRedirect = false;
        gotStream = false;
        gotWriter = false;
        out.getBuffer().reset();
    }

    public boolean isValid()
    {
        return !didError && !didRedirect;
    }

    private void internalSetHeader(String s, Object obj)
    {
        Vector vector = new Vector();
        vector.addElement(obj);
        headers.put(s, vector);
    }

    private void internalAddHeader(String s, Object obj)
    {
        Vector vector = (Vector)headers.get(s);
        if(vector == null)
            vector = new Vector();
        vector.addElement(obj);
        headers.put(s, vector);
    }

    public void writeTo(HttpServletResponse httpservletresponse)
    {
        httpservletresponse.setStatus(status);
        if(contentType != null)
            httpservletresponse.setContentType(contentType);
        if(locale != null)
            httpservletresponse.setLocale(locale);
        Cookie cookie;
        for(Enumeration enumeration = cookies.elements(); enumeration.hasMoreElements(); httpservletresponse.addCookie(cookie))
            cookie = (Cookie)enumeration.nextElement();

        for(Enumeration enumeration1 = headers.keys(); enumeration1.hasMoreElements();)
        {
            String s = (String)enumeration1.nextElement();
            Vector vector = (Vector)headers.get(s);
            for(Enumeration enumeration2 = vector.elements(); enumeration2.hasMoreElements();)
            {
                Object obj = enumeration2.nextElement();
                if(obj instanceof String)
                    httpservletresponse.setHeader(s, (String)obj);
                if(obj instanceof Integer)
                    httpservletresponse.setIntHeader(s, ((Integer)obj).intValue());
                if(obj instanceof Long)
                    httpservletresponse.setDateHeader(s, ((Long)obj).longValue());
            }

        }

        httpservletresponse.setContentLength(out.getBuffer().size());
        try
        {
            out.getBuffer().writeTo(httpservletresponse.getOutputStream());
        }
        catch(IOException ioexception)
        {
            System.out.println("Got IOException writing cached response: " + ioexception.getMessage());
        }
    }

    public ServletOutputStream getOutputStream()
        throws IOException
    {
        if(gotWriter)
        {
            throw new IllegalStateException("Cannot get output stream after getting writer");
        } else
        {
            gotStream = true;
            return out;
        }
    }

    public PrintWriter getWriter()
        throws UnsupportedEncodingException
    {
        if(gotStream)
            throw new IllegalStateException("Cannot get writer after getting output stream");
        gotWriter = true;
        if(writer == null)
        {
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(out, getCharacterEncoding());
            writer = new PrintWriter(outputstreamwriter, true);
        }
        return writer;
    }

    public void setContentLength(int i)
    {
        delegate.setContentLength(i);
    }

    public void setContentType(String s)
    {
        delegate.setContentType(s);
        contentType = s;
    }

    public String getCharacterEncoding()
    {
        return delegate.getCharacterEncoding();
    }

    public void setBufferSize(int i)
        throws IllegalStateException
    {
        delegate.setBufferSize(i);
    }

    public int getBufferSize()
    {
        return delegate.getBufferSize();
    }

    public void reset()
        throws IllegalStateException
    {
        delegate.reset();
        internalReset();
    }

    public void resetBuffer()
        throws IllegalStateException
    {
        delegate.resetBuffer();
        contentLength = -1;
        out.getBuffer().reset();
    }

    public boolean isCommitted()
    {
        return delegate.isCommitted();
    }

    public void flushBuffer()
        throws IOException
    {
        delegate.flushBuffer();
    }

    public void setLocale(Locale locale1)
    {
        delegate.setLocale(locale1);
        locale = locale1;
    }

    public Locale getLocale()
    {
        return delegate.getLocale();
    }

    public void addCookie(Cookie cookie)
    {
        delegate.addCookie(cookie);
        cookies.addElement(cookie);
    }

    public boolean containsHeader(String s)
    {
        return delegate.containsHeader(s);
    }

    public void setStatus(int i, String s)
    {
        delegate.setStatus(i, s);
        status = i;
    }

    public void setStatus(int i)
    {
        delegate.setStatus(i);
        status = i;
    }

    public void setHeader(String s, String s1)
    {
        delegate.setHeader(s, s1);
        internalSetHeader(s, s1);
    }

    public void setIntHeader(String s, int i)
    {
        delegate.setIntHeader(s, i);
        internalSetHeader(s, new Integer(i));
    }

    public void setDateHeader(String s, long l)
    {
        delegate.setDateHeader(s, l);
        internalSetHeader(s, new Long(l));
    }

    public void sendError(int i, String s)
        throws IOException
    {
        delegate.sendError(i, s);
        didError = true;
    }

    public void sendError(int i)
        throws IOException
    {
        delegate.sendError(i);
        didError = true;
    }

    public void sendRedirect(String s)
        throws IOException
    {
        delegate.sendRedirect(s);
        didRedirect = true;
    }

    public String encodeURL(String s)
    {
        return delegate.encodeURL(s);
    }

    public String encodeRedirectURL(String s)
    {
        return delegate.encodeRedirectURL(s);
    }

    public void addHeader(String s, String s1)
    {
        internalAddHeader(s, s1);
    }

    public void addIntHeader(String s, int i)
    {
        internalAddHeader(s, new Integer(i));
    }

    public void addDateHeader(String s, long l)
    {
        internalAddHeader(s, new Long(l));
    }

    public String encodeUrl(String s)
    {
        return encodeURL(s);
    }

    public String encodeRedirectUrl(String s)
    {
        return encodeRedirectURL(s);
    }

    private int status;
    private Hashtable headers;
    private int contentLength;
    private String contentType;
    private Locale locale;
    private Vector cookies;
    private boolean didError;
    private boolean didRedirect;
    private boolean gotStream;
    private boolean gotWriter;
    private HttpServletResponse delegate;
    private CacheServletOutputStream out;
    private PrintWriter writer;

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub

	}
}
