// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultipartRequest.java

package com.credu.scorm.multi;

import com.credu.scorm.multi.multipart.FilePart;
import com.credu.scorm.multi.multipart.FileRenamePolicy;
import com.credu.scorm.multi.multipart.MultipartParser;
import com.credu.scorm.multi.multipart.ParamPart;
import com.credu.scorm.multi.multipart.Part;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.credu.scorm.multi:
//            UploadedFile

public class MultipartRequest
{

    public MultipartRequest(HttpServletRequest httpservletrequest, String s)
        throws IOException
    {
        this(httpservletrequest, s, 0x100000);
    }

    public MultipartRequest(HttpServletRequest httpservletrequest, String s, int i)
        throws IOException
    {
        this(httpservletrequest, s, i, null, null);
    }

    public MultipartRequest(HttpServletRequest httpservletrequest, String s, String s1)
        throws IOException
    {
        this(httpservletrequest, s, 0x100000, s1, null);
    }

    public MultipartRequest(HttpServletRequest httpservletrequest, String s, int i, FileRenamePolicy filerenamepolicy)
        throws IOException
    {
        this(httpservletrequest, s, i, null, filerenamepolicy);
    }

    public MultipartRequest(HttpServletRequest httpservletrequest, String s, int i, String s1)
        throws IOException
    {
        this(httpservletrequest, s, i, s1, null);
    }

    public MultipartRequest(HttpServletRequest httpservletrequest, String s, int i, String s1, FileRenamePolicy filerenamepolicy)
        throws IOException
    {
        parameters = new Hashtable();
        files = new Hashtable();
        if(httpservletrequest == null)
            throw new IllegalArgumentException("request cannot be null");
        if(s == null)
            throw new IllegalArgumentException("saveDirectory cannot be null");
        if(i <= 0)
            throw new IllegalArgumentException("maxPostSize must be positive");
        File file = new File(s);
        if(!file.isDirectory())
            throw new IllegalArgumentException("Not a directory: " + s);
        if(!file.canWrite())
            throw new IllegalArgumentException("Not writable: " + s);
        MultipartParser multipartparser = new MultipartParser(httpservletrequest, i);
        if(s1 != null)
            multipartparser.setEncoding(s1);
        Part part;
        while((part = multipartparser.readNextPart()) != null) 
        {
            String s2 = part.getName();
            if(part.isParam())
            {
                ParamPart parampart = (ParamPart)part;
                String s3 = parampart.getStringValue();
                Vector vector = (Vector)parameters.get(s2);
                if(vector == null)
                {
                    vector = new Vector();
                    parameters.put(s2, vector);
                }
                vector.addElement(s3);
            } else
            if(part.isFile())
            {
                FilePart filepart = (FilePart)part;
                String s4 = filepart.getFileName();
                if(s4 != null)
                {
                    filepart.setRenamePolicy(filerenamepolicy);
                    filepart.writeTo(file);
                    files.put(s2, new UploadedFile(file.toString(), filepart.getFileName(), s4, filepart.getContentType()));
                } else
                {
                    files.put(s2, new UploadedFile(null, null, null, null));
                }
            }
        }
    }

    public MultipartRequest(ServletRequest servletrequest, String s)
        throws IOException
    {
        this((HttpServletRequest)servletrequest, s);
    }

    public MultipartRequest(ServletRequest servletrequest, String s, int i)
        throws IOException
    {
        this((HttpServletRequest)servletrequest, s, i);
    }

    public Enumeration getParameterNames()
    {
        return parameters.keys();
    }

    public Enumeration getFileNames()
    {
        return files.keys();
    }

    public String getParameter(String s)
    {
        try
        {
            Vector vector = (Vector)parameters.get(s);
            if(vector == null || vector.size() == 0)
            {
                return null;
            } else
            {
                String s1 = (String)vector.elementAt(vector.size() - 1);
                return s1;
            }
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public String[] getParameterValues(String s)
    {
        try
        {
            Vector vector = (Vector)parameters.get(s);
            if(vector == null || vector.size() == 0)
            {
                return null;
            } else
            {
                String as[] = new String[vector.size()];
                vector.copyInto(as);
                return as;
            }
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public String getFilesystemName(String s)
    {
        try
        {
            UploadedFile uploadedfile = (UploadedFile)files.get(s);
            return uploadedfile.getFilesystemName();
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public String getOriginalFileName(String s)
    {
        try
        {
            UploadedFile uploadedfile = (UploadedFile)files.get(s);
            return uploadedfile.getOriginalFileName();
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public String getContentType(String s)
    {
        try
        {
            UploadedFile uploadedfile = (UploadedFile)files.get(s);
            return uploadedfile.getContentType();
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public File getFile(String s)
    {
        try
        {
            UploadedFile uploadedfile = (UploadedFile)files.get(s);
            return uploadedfile.getFile();
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    private static final int DEFAULT_MAX_POST_SIZE = 0x100000;
    protected Hashtable parameters;
    protected Hashtable files;
}
