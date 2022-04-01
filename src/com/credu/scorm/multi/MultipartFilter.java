package com.credu.scorm.multi;

import java.io.File;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class MultipartFilter
    implements Filter
{
    public FilterConfig getFilterConfig() 
    {
    	return config;
    }
    
    public void setFilterConfig(FilterConfig arg0) 
    {
    }

    public MultipartFilter()
    {
        config = null;
        dir = null;
    }

    public void init(FilterConfig filterconfig)
        throws ServletException
    {
        config = filterconfig;
        dir = filterconfig.getInitParameter("uploadDir");
        if(dir == null)
        {
            File file = (File)filterconfig.getServletContext().getAttribute("javax.servlet.context.tempdir");
            if(file != null)
                dir = file.toString();
            else
                throw new ServletException("MultipartFilter: No upload directory found: set an uploadDir init parameter or ensure the javax.servlet.context.tempdir directory is valid");
        }
    }

    public void destroy()
    {
        config = null;
    }

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
        throws IOException, ServletException
    {
        HttpServletRequest httpservletrequest = (HttpServletRequest)servletrequest;
        String s = httpservletrequest.getHeader("Content-Type");
        if(s == null || !s.startsWith("multipart/form-data"))
        {
            filterchain.doFilter(servletrequest, servletresponse);
        } else
        {
            MultipartWrapper multipartwrapper = new MultipartWrapper(httpservletrequest, dir);
            filterchain.doFilter(multipartwrapper, servletresponse);
        }
    }

    private FilterConfig config;
    private String dir;
}
