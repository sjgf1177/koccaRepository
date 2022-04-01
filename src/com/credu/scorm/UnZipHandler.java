// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnZipHandler.java

//package alexit.nlib.util;
package com.credu.scorm;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;
import java.text.*;

public class UnZipHandler
{
	private File mZipFile;
    private String mExtractToDir;
    private String sourcefileName;
    private String rootDir;

    public UnZipHandler(File archivefile, String ParentDir)
    {
        mZipFile = archivefile;
        sourcefileName = archivefile.getName();
        rootDir = ParentDir;
        start();
    }

    public void start()
    {
        String mExtractToDir = rootDir;
        String destFileName = new String();
        String fileName = new String();

        try
        {
	    mExtractToDir = new String(mExtractToDir.getBytes("8859_1"), "euc-kr");
        }
        catch(Exception exception) {
		System.out.println("[Error] mExtractToDir.getBytes('8859_1'), 'euc-kr'");
	}
        byte buffer[] = new byte[16384];
        try
        {
            ZipFile archive = new ZipFile(mZipFile);
            for(Enumeration e = archive.entries(); e.hasMoreElements();)
            {
                ZipEntry entry = (ZipEntry)e.nextElement();
                if(!entry.isDirectory())
                {
                    fileName = entry.getName();
                    fileName = fileName.replace('/', File.separatorChar);
                    try
                    {
System.out.println("[Before] fileName  :"+ fileName);
		    fileName	= new String(fileName.getBytes("8859_1"),"KSC5601"); 
System.out.println("[After] fileName  :"+ fileName);
                    destFileName = mExtractToDir + File.separatorChar + fileName;
                    }
                    catch(Exception exception1) {
System.out.println("[Error] destFileName.getBytes('8859_1'), 'KSC5601'");
		    }
                    File destFile = new File(destFileName);
                    String parent = destFile.getParent();
                    if(parent != null)
                    {
                        File parentFile = new File(parent);
                        if(!parentFile.exists())
                            parentFile.mkdirs();
                    }
                    InputStream in = archive.getInputStream(entry);
                    if(in == null){
                        System.out.println("null - korean");
					}else{
						OutputStream out = new FileOutputStream(destFileName);
						int count;
						while((count = in.read(buffer)) != -1) 
						out.write(buffer, 0, count);
						in.close();
						out.close();
								System.out.println("out - success");
					}
                }
            }

        }
        catch(ZipException ze)
        {
            ze.printStackTrace();
        }
        catch(NullPointerException npe)
        {
            npe.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch(SecurityException se)
        {
            se.printStackTrace();
        }
    }   
}
