// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoteHttpServlet.java

package com.credu.scorm.multi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class RemoteHttpServlet extends HttpServlet
    implements Remote
{

    public RemoteHttpServlet()
    {
    }

    public void init(ServletConfig servletconfig)
        throws ServletException
    {
        super.init(servletconfig);
        try
        {
            UnicastRemoteObject.exportObject(this);
            bind();
        }
        catch(RemoteException remoteexception)
        {
            log("Problem binding to RMI registry: " + remoteexception.getMessage());
        }
    }

    public void destroy()
    {
        unbind();
    }

    protected String getRegistryName()
    {
        String s = getInitParameter("registryName");
        if(s != null)
            return s;
        else
            return getClass().getName();
    }

    protected int getRegistryPort()
    {
        try
        {
            return Integer.parseInt(getInitParameter("registryPort"));
        }
        catch(NumberFormatException numberformatexception)
        {
            return 1099;
        }
    }

    protected void bind()
    {
        try
        {
            registry = LocateRegistry.getRegistry(getRegistryPort());
            registry.list();
        }
        catch(Exception exception)
        {
            registry = null;
        }
        if(registry == null)
            try
            {
                registry = LocateRegistry.createRegistry(getRegistryPort());
            }
            catch(Exception exception1)
            {
                log("Could not get or create RMI registry on port " + getRegistryPort() + ": " + exception1.getMessage());
                return;
            }
        try
        {
            registry.rebind(getRegistryName(), this);
        }
        catch(Exception exception2)
        {
            log("Could not bind to RMI registry: " + exception2.getMessage());
            return;
        }
    }

    protected void unbind()
    {
        try
        {
            if(registry != null)
                registry.unbind(getRegistryName());
        }
        catch(Exception exception)
        {
            log("Problem unbinding from RMI registry: " + exception.getMessage());
        }
    }

    protected Registry registry;
}
