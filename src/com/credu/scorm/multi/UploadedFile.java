// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultipartRequest.java

package com.credu.scorm.multi;

import java.io.File;

class UploadedFile
{

    UploadedFile(String s, String s1, String s2, String s3)
    {
        dir = s;
        filename = s1;
        original = s2;
        type = s3;
    }

    public String getContentType()
    {
        return type;
    }

    public String getFilesystemName()
    {
        return filename;
    }

    public String getOriginalFileName()
    {
        return original;
    }

    public File getFile()
    {
        if(dir == null || filename == null)
            return null;
        else
            return new File(dir + File.separator + filename);
    }

    private String dir;
    private String filename;
    private String original;
    private String type;
}
