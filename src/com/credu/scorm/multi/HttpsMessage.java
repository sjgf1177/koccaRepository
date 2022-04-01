// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpsMessage.java

package com.credu.scorm.multi;

import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;

// Referenced classes of package com.credu.scorm.multi:
//            HttpMessage

public class HttpsMessage extends HttpMessage
{

    public HttpsMessage(String s)
        throws Exception
    {
        super(null);
        if(!m_bStreamHandlerSet)
        {
            String s1 = System.getProperty("java.vendor");
            String s2 = System.getProperty("java.version");
            Double double1 = new Double(s2.substring(0, 3));
            if(-1 < s1.indexOf("Microsoft"))
                try
                {
                    Class class1 = Class.forName("com.ms.net.wininet.WininetStreamHandlerFactory");
                    if(null != class1)
                        URL.setURLStreamHandlerFactory((URLStreamHandlerFactory)class1.newInstance());
                }
                catch(ClassNotFoundException classnotfoundexception)
                {
                    throw new Exception("Unable to load the Microsoft SSL stream handler.  Check classpath." + classnotfoundexception.toString());
                }
                catch(Error error)
                {
                    m_bStreamHandlerSet = true;
                }
            else
            if(1.2D <= double1.doubleValue())
            {
                System.getProperties().put("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
                try
                {
                    Class class2 = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
                    if(null != class2 && null == Security.getProvider("SunJSSE"))
                        Security.addProvider((Provider)class2.newInstance());
                }
                catch(ClassNotFoundException classnotfoundexception1)
                {
                    throw new Exception("Unable to load the JSSE SSL stream handler.  Check classpath." + classnotfoundexception1.toString());
                }
            }
            m_bStreamHandlerSet = true;
        }
        servlet = new URL(s);
    }

    static boolean m_bStreamHandlerSet = false;

}
