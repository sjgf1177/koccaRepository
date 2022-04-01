package com.credu.scorm;

import java.io.*;
import java.util.Properties;
import java.util.StringTokenizer;

// Referenced classes of package alexit.lib.util:
//            StringUtil

public class FileUtil
{

    public FileUtil()
    {
    }

    public static boolean doDeleteFile(String filePath)
        throws Exception
    {
        File file = new File(filePath);
        return !file.exists() || file.delete();
    }

    public static String getFileName(String str)
    {
        Properties p = new Properties();
        String token;
        for(StringTokenizer strTokens = new StringTokenizer(str, "/"); strTokens.hasMoreTokens(); p.setProperty("token", token))
            token = strTokens.nextToken();

        return p.getProperty("token");
    }

    public static String getFileExt(String filenm)
    {
        if(filenm.lastIndexOf(".") >= 0)
            return filenm.substring(filenm.lastIndexOf(".") + 1, filenm.length());
        else
            return "";
    }

    public static void writeToTextFile(String fileName, String content, boolean isAppend)
    {
        FileOutputStream outFile = null;
        try
        {
            outFile = new FileOutputStream(fileName, isAppend);
            String str = content;
            outFile.write(str.getBytes());
            if(outFile != null)
                outFile.close();
        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("FileNotFound:" + fileName);
        }
        catch(IOException ioe)
        {
            System.out.println("IOException:" + fileName);
        }
    }

    public static String fileSeparator()
    {
        return System.getProperty("file.separator", "/");
    }

    public static String getValidFileName(String fileName)
    {
        String validFileName = fileName;
        String fileExt = "";
        if(validFileName == null)
            return "";
        if(validFileName.equals(""))
        {
            return "";
        } else
        {
            validFileName = StringUtil.replace(validFileName, "%", "_");
            validFileName = StringUtil.replace(validFileName, "#", "_");
            validFileName = StringUtil.replace(validFileName, " ", "_");
            return validFileName;
        }
    }

    public static String getCanonicalPath(String fileName)
    {
        if(fileName == null)
            return null;
        File f = new File(fileName);
        try
        {
            return f.getCanonicalPath();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        return fileName;
    }

    public static String readFromTextFile(String fileName)
        throws Exception
    {
        FileInputStream fis = null;
        byte buf[] = null;
        try
        {
            int len = (int)(new File(fileName)).length();
            fis = new FileInputStream(fileName);
            buf = new byte[len];
            fis.read(buf);
            fis.close();
        }
        finally
        {
            try
            {
                if(fis != null)
                    fis.close();
            }
            catch(IOException ioexception) { }
            return new String(buf);
        }
    }

    public static byte[] readFromImgFile(String fileName)
    {
        Long fileLen = new Long(getFilelength(fileName));
        byte buffer[] = new byte[fileLen.intValue()];
        FileInputStream fln = null;
        try
        {
            fln = new FileInputStream(fileName);
            fln.read(buffer);
        }
        catch(Exception exception) { }
        finally
        {
            try
            {
                if(fln != null)
                    fln.close();
            }
            catch(Exception exception1) { }
            return buffer;
        }
    }

    public static long getFilelength(String fileName)
    {
        long rtn = 0L;
        try
        {
            File fp = new File(fileName);
            rtn = fp.length();
        }
        catch(Exception exception) { }
        finally
        {
            return rtn;
        }
    }

    public static boolean isExistFile(String fileName)
    {
        File file = new File(fileName);
        return file.exists();
    }
}
