// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpMessage.java

package com.credu.scorm.multi;

import java.io.*;
import java.net.*;
import java.util.*;

// Referenced classes of package com.credu.scorm.multi:
//            Base64Encoder

public class HttpMessage
{

    public HttpMessage(URL url)
    {
        servlet = null;
        headers = null;
        servlet = url;
    }

    public InputStream sendGetMessage()
        throws IOException
    {
        return sendGetMessage(null);
    }

    public InputStream sendGetMessage(Properties properties)
        throws IOException
    {
        String s = "";
        if(properties != null)
            s = "?" + toEncodedString(properties);
        URL url = new URL(servlet.toExternalForm() + s);
        URLConnection urlconnection = url.openConnection();
        urlconnection.setUseCaches(false);
        sendHeaders(urlconnection);
        return urlconnection.getInputStream();
    }

    public InputStream sendPostMessage()
        throws IOException
    {
        return sendPostMessage(null);
    }

    public InputStream sendPostMessage(Properties properties)
        throws IOException
    {
        String s = "";
        if(properties != null)
            s = toEncodedString(properties);
        URLConnection urlconnection = servlet.openConnection();
        urlconnection.setDoInput(true);
        urlconnection.setDoOutput(true);
        urlconnection.setUseCaches(false);
        urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        sendHeaders(urlconnection);
        DataOutputStream dataoutputstream = new DataOutputStream(urlconnection.getOutputStream());
        dataoutputstream.writeBytes(s);
        dataoutputstream.flush();
        dataoutputstream.close();
        return urlconnection.getInputStream();
    }

    public InputStream sendPostMessage(Serializable serializable)
        throws IOException
    {
        URLConnection urlconnection = servlet.openConnection();
        urlconnection.setDoInput(true);
        urlconnection.setDoOutput(true);
        urlconnection.setUseCaches(false);
        urlconnection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
        sendHeaders(urlconnection);
        ObjectOutputStream objectoutputstream = new ObjectOutputStream(urlconnection.getOutputStream());
        objectoutputstream.writeObject(serializable);
        objectoutputstream.flush();
        objectoutputstream.close();
        return urlconnection.getInputStream();
    }

    public void setHeader(String s, String s1)
    {
        if(headers == null)
            headers = new Hashtable();
        headers.put(s, s1);
    }

    private void sendHeaders(URLConnection urlconnection)
    {
        if(headers != null)
        {
            String s;
            String s1;
            for(Enumeration enumeration = headers.keys(); enumeration.hasMoreElements(); urlconnection.setRequestProperty(s, s1))
            {
                s = (String)enumeration.nextElement();
                s1 = (String)headers.get(s);
            }

        }
    }

    public void setCookie(String s, String s1)
    {
        if(headers == null)
            headers = new Hashtable();
        String s2 = (String)headers.get("Cookie");
        if(s2 == null)
            setHeader("Cookie", s + "=" + s1);
        else
            setHeader("Cookie", s2 + "; " + s + "=" + s1);
    }

    public void setAuthorization(String s, String s1)
    {
        String s2 = Base64Encoder.encode(s + ":" + s1);
        setHeader("Authorization", "Basic " + s2);
    }

    private String toEncodedString(Properties properties)
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(Enumeration enumeration = properties.propertyNames(); enumeration.hasMoreElements();)
        {
            String s = (String)enumeration.nextElement();
            String s1 = properties.getProperty(s);
            stringbuffer.append(URLEncoder.encode(s) + "=" + URLEncoder.encode(s1));
            if(enumeration.hasMoreElements())
                stringbuffer.append("&");
        }

        return stringbuffer.toString();
    }

    URL servlet;
    Hashtable headers;
}
