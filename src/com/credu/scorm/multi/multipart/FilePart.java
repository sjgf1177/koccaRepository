// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FilePart.java

package com.credu.scorm.multi.multipart;

import java.io.*;
import javax.servlet.ServletInputStream;

// Referenced classes of package com.credu.scorm.multi.multipart:
//            Part, PartInputStream, MacBinaryDecoderOutputStream, FileRenamePolicy

public class FilePart extends Part
{

    FilePart(String s, ServletInputStream servletinputstream, String s1, String s2, String s3, String s4)
        throws IOException
    {
        super(s);
        fileName = s3;
        filePath = s4;
        contentType = s2;
        partInput = new PartInputStream(servletinputstream, s1);
    }

    public void setRenamePolicy(FileRenamePolicy filerenamepolicy)
    {
        policy = filerenamepolicy;
    }

    public String getFileName()
    {
        return fileName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getContentType()
    {
        return contentType;
    }

    public InputStream getInputStream()
    {
        return partInput;
    }

    public long writeTo(File file)
        throws IOException
    {
        long l = 0L;
        BufferedOutputStream bufferedoutputstream = null;
        try
        {
            if(fileName != null)
            {
                File file1;
                if(file.isDirectory())
                    file1 = new File(file, fileName);
                else
                    file1 = file;
                if(policy != null)
                {
                    file1 = policy.rename(file1);
                    fileName = file1.getName();
                }
                bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file1));
                l = write(bufferedoutputstream);
            }
        }
        finally
        {
            if(bufferedoutputstream != null)
                bufferedoutputstream.close();
        }
        return l;
    }

    public long writeTo(OutputStream outputstream)
        throws IOException
    {
        long l = 0L;
        if(fileName != null)
            l = write(outputstream);
        return l;
    }

    long write(OutputStream outputstream)
        throws IOException
    {
        if(contentType.equals("application/x-macbinary"))
            outputstream = new MacBinaryDecoderOutputStream(outputstream);
        long l = 0L;
        byte abyte0[] = new byte[8192];
        int i;
        while((i = partInput.read(abyte0)) != -1) 
        {
            outputstream.write(abyte0, 0, i);
            l += i;
        }
        return l;
    }

    public boolean isFile()
    {
        return true;
    }

    private String fileName;
    private String filePath;
    private String contentType;
    private PartInputStream partInput;
    private FileRenamePolicy policy;
}
