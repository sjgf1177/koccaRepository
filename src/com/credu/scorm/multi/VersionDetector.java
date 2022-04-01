// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VersionDetector.java

package com.credu.scorm.multi;


public class VersionDetector
{

    public VersionDetector()
    {
    }

    public static String getServletVersion()
    {
        if(servletVersion != null)
            return servletVersion;
        String s = null;
        try
        {
            s = "1.0";
            Class.forName("javax.servlet.http.HttpSession");
            s = "2.0";
            Class.forName("javax.servlet.RequestDispatcher");
            s = "2.1";
            Class.forName("javax.servlet.http.HttpServletResponse").getDeclaredField("SC_EXPECTATION_FAILED");
            s = "2.2";
            Class.forName("javax.servlet.Filter");
            s = "2.3";
        }
        catch(Throwable throwable) { }
        servletVersion = s;
        return servletVersion;
    }

    public static String getJavaVersion()
    {
        if(javaVersion != null)
            return javaVersion;
        String s = null;
        try
        {
            s = "1.0";
            Class.forName("java.lang.Void");
            s = "1.1";
            Class.forName("java.lang.ThreadLocal");
            s = "1.2";
            Class.forName("java.lang.StrictMath");
            s = "1.3";
            Class.forName("java.net.URI");
            s = "1.4";
        }
        catch(Throwable throwable) { }
        javaVersion = s;
        return javaVersion;
    }

    static String servletVersion;
    static String javaVersion;
}
