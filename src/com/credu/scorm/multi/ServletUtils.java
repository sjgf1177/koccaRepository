// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServletUtils.java

package com.credu.scorm.multi;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.*;

public class ServletUtils
{

    public ServletUtils()
    {
    }

    public static void returnFile(String s, OutputStream outputstream)
        throws FileNotFoundException, IOException
    {
        FileInputStream fileinputstream = null;
        try
        {
            fileinputstream = new FileInputStream(s);
            byte abyte0[] = new byte[4096];
            int i;
            while((i = fileinputstream.read(abyte0)) != -1) 
                outputstream.write(abyte0, 0, i);
        }
        finally
        {
            if(fileinputstream != null)
                fileinputstream.close();
        }
    }

    public static void returnURL(URL url, OutputStream outputstream)
        throws IOException
    {
        InputStream inputstream = url.openStream();
        byte abyte0[] = new byte[4096];
        int i;
        while((i = inputstream.read(abyte0)) != -1) 
            outputstream.write(abyte0, 0, i);
    }

    public static void returnURL(URL url, Writer writer)
        throws IOException
    {
        URLConnection urlconnection = url.openConnection();
        urlconnection.connect();
        String s = urlconnection.getContentEncoding();
        BufferedReader bufferedreader = null;
        if(s == null)
            bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
        else
            bufferedreader = new BufferedReader(new InputStreamReader(url.openStream(), s));
        char ac[] = new char[4096];
        int i;
        while((i = bufferedreader.read(ac)) != -1) 
            writer.write(ac, 0, i);
    }

    public static String getStackTraceAsString(Throwable throwable)
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        PrintWriter printwriter = new PrintWriter(bytearrayoutputstream, true);
        throwable.printStackTrace(printwriter);
        return bytearrayoutputstream.toString();
    }

    public static Servlet getServlet(String s, ServletRequest servletrequest, ServletContext servletcontext)
    {
        try
        {
            Servlet servlet = servletcontext.getServlet(s);
            if(servlet != null)
                return servlet;
            Socket socket = new Socket(servletrequest.getServerName(), servletrequest.getServerPort());
            socket.setSoTimeout(4000);
            PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
            printwriter.println("GET /servlet/" + s + " HTTP/1.0");
            printwriter.println();
            try
            {
                socket.getInputStream().read();
            }
            catch(InterruptedIOException interruptedioexception) { }
            printwriter.close();
            return servletcontext.getServlet(s);
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public static String[] split(String s, String s1)
    {
        Vector vector = new Vector();
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, s1); stringtokenizer.hasMoreTokens(); vector.addElement(stringtokenizer.nextToken()));
        String as[] = new String[vector.size()];
        for(int i = 0; i < as.length; i++)
            as[i] = (String)vector.elementAt(i);

        return as;
    }

    public static URL getResource(ServletContext servletcontext, String s)
        throws IOException
    {
        if(s == null)
            throw new FileNotFoundException("Requested resource was null (passed in null)");
        if(s.endsWith("/") || s.endsWith("\\") || s.endsWith("."))
            throw new MalformedURLException("Path may not end with a slash or dot");
        if(s.indexOf("..") != -1)
            throw new MalformedURLException("Path may not contain double dots");
        String s1 = s.toUpperCase();
        if(s1.startsWith("/WEB-INF") || s1.startsWith("/META-INF"))
            throw new MalformedURLException("Path may not begin with /WEB-INF or /META-INF");
        if(s1.endsWith(".JSP"))
            throw new MalformedURLException("Path may not end with .jsp");
        URL url = servletcontext.getResource(s);
        if(url == null)
            throw new FileNotFoundException("Requested resource was null (" + s + ")");
        else
            return url;
    }
}
