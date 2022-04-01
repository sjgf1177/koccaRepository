// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailMessage.java

package com.credu.scorm.multi;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

// Referenced classes of package com.credu.scorm.multi:
//            MailPrintStream

public class MailMessage
{

    public MailMessage()
        throws IOException
    {
        this("localhost");
    }

    public MailMessage(String s)
        throws IOException
    {
        host = s;
        to = new Vector();
        cc = new Vector();
        headers = new Hashtable();
        setHeader("X-Mailer", "com.credu.scorm.multi.MailMessage (www.servlets.com)");
        connect();
        sendHelo();
    }

    public void from(String s)
        throws IOException
    {
        sendFrom(s);
        from = s;
    }

    public void to(String s)
        throws IOException
    {
        sendRcpt(s);
        to.addElement(s);
    }

    public void cc(String s)
        throws IOException
    {
        sendRcpt(s);
        cc.addElement(s);
    }

    public void bcc(String s)
        throws IOException
    {
        sendRcpt(s);
    }

    public void setSubject(String s)
    {
        headers.put("Subject", s);
    }

    public void setHeader(String s, String s1)
    {
        headers.put(s, s1);
    }

    public PrintStream getPrintStream()
        throws IOException
    {
        setFromHeader();
        setToHeader();
        setCcHeader();
        sendData();
        flushHeaders();
        return out;
    }

    void setFromHeader()
    {
        setHeader("From", from);
    }

    void setToHeader()
    {
        setHeader("To", vectorToList(to));
    }

    void setCcHeader()
    {
        setHeader("Cc", vectorToList(cc));
    }

    String vectorToList(Vector vector)
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
        {
            stringbuffer.append(enumeration.nextElement());
            if(enumeration.hasMoreElements())
                stringbuffer.append(", ");
        }

        return stringbuffer.toString();
    }

    void flushHeaders()
        throws IOException
    {
        String s;
        String s1;
        for(Enumeration enumeration = headers.keys(); enumeration.hasMoreElements(); out.println(s + ": " + s1))
        {
            s = (String)enumeration.nextElement();
            s1 = (String)headers.get(s);
        }

        out.println();
        out.flush();
    }

    public void sendAndClose()
        throws IOException
    {
        sendDot();
        disconnect();
    }

    static String sanitizeAddress(String s)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = s.length();
        for(int i1 = 0; i1 < l; i1++)
        {
            char c = s.charAt(i1);
            if(c == '(')
            {
                i++;
                if(j == 0)
                    k = i1;
            } else
            if(c == ')')
            {
                i--;
                if(k == 0)
                    j = i1 + 1;
            } else
            if(i == 0 && c == '<')
                j = i1 + 1;
            else
            if(i == 0 && c == '>')
                k = i1;
        }

        if(k == 0)
            k = l;
        return s.substring(j, k);
    }

    void connect()
        throws IOException
    {
        socket = new Socket(host, 25);
        out = new MailPrintStream(new BufferedOutputStream(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        getReady();
    }

    void getReady()
        throws IOException
    {
        String s = in.readLine();
        int ai[] = {
            220
        };
        if(!isResponseOK(s, ai))
            throw new IOException("Didn't get introduction from server: " + s);
        else
            return;
    }

    void sendHelo()
        throws IOException
    {
        String s = InetAddress.getLocalHost().getHostName();
        int ai[] = {
            250
        };
        send("HELO " + s, ai);
    }

    void sendFrom(String s)
        throws IOException
    {
        int ai[] = {
            250
        };
        send("MAIL FROM: <" + sanitizeAddress(s) + ">", ai);
    }

    void sendRcpt(String s)
        throws IOException
    {
        int ai[] = {
            250, 251
        };
        send("RCPT TO: <" + sanitizeAddress(s) + ">", ai);
    }

    void sendData()
        throws IOException
    {
        int ai[] = {
            354
        };
        send("DATA", ai);
    }

    void sendDot()
        throws IOException
    {
        int ai[] = {
            250
        };
        send("\r\n.", ai);
    }

    void sendQuit()
        throws IOException
    {
        int ai[] = {
            221
        };
        send("QUIT", ai);
    }

    void send(String s, int ai[])
        throws IOException
    {
        out.rawPrint(s + "\r\n");
        String s1 = in.readLine();
        if(!isResponseOK(s1, ai))
            throw new IOException("Unexpected reply to command: " + s + ": " + s1);
        else
            return;
    }

    boolean isResponseOK(String s, int ai[])
    {
        for(int i = 0; i < ai.length; i++)
            if(s.startsWith("" + ai[i]))
                return true;

        return false;
    }

    void disconnect()
        throws IOException
    {
        if(out != null)
            out.close();
        if(in != null)
            in.close();
        if(socket != null)
            socket.close();
    }

    String host;
    String from;
    Vector to;
    Vector cc;
    Hashtable headers;
    MailPrintStream out;
    BufferedReader in;
    Socket socket;
}
