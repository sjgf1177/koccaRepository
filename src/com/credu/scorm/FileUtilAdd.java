// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileUtilAdd.java

package com.credu.scorm;

import java.io.File;
import java.io.IOException;

public class FileUtilAdd
{

    public FileUtilAdd()
    {
    }

    public void deleteDir(File directory)
        throws IOException
    {
        if(!directory.isDirectory())
            throw new IOException("This file is not directory.");
        File list[] = directory.listFiles();
        for(int i = 0; i < list.length; i++)
            if(list[i].isDirectory())
                deleteDir(list[i]);
            else
                list[i].delete();

        directory.delete();
    }

    public static String[] getfileNames(File directory)
        throws IOException
    {
        if(!directory.isDirectory())
            throw new IOException("This file is not directory.");
        File list[] = directory.listFiles();
        String names[] = new String[list.length];
        for(int i = 0; i < list.length; i++)
            names[i] = list[i].getName();

        return names;
    }

    public static String[] getfileNames(File files[])
    {
        String names[] = new String[files.length];
        for(int i = 0; i < names.length; i++)
            names[i] = files[i].getName();

        return names;
    }

    public static String getParent(String fileName)
        throws IOException
    {
        return getParent(new File(fileName));
    }

    public static String getParent(File file)
        throws IOException
    {
        String filePath = file.getCanonicalPath();
        return filePath.substring(0, filePath.length() - file.getName().length());
    }
}
