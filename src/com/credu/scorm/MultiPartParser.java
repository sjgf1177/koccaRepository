package com.credu.scorm;

import java.io.*;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
//import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package alexit.lib.util:
//            FormatUtil

public class MultiPartParser
{
    public class MultiPartParameter
    {

        public void setParmName(String name)
        {
            parmName = name;
        }

        public void setParmValue(String value)
        {
            parmValue = value;
        }

        public void setSaveFile(File file)
        {
            saveFile = file;
        }

        public String getParmName()
        {
            return parmName;
        }

        public String getParmValue()
        {
            return parmValue;
        }

        public File getSaveFile()
        {
            return saveFile;
        }

        String parmName;
        String parmValue;
        File saveFile;

        public MultiPartParameter(String name)
        {
            parmName = name;
        }

    }


    public MultiPartParser()
    {
        parameters = new Vector();
        servletIn = null;
        fileOut = null;
        fileBuffer = new byte[1024];
        fileBufferLength = 0;
        endOfFile = false;
        leadBufferLength = 0;
        leadBuffer = new byte[100];
        delimiter = new byte[35];
        nameHeader = (new String("Content-Disposition: form-data; name=")).getBytes();
        oneByte = 0;
        targetDir = ".";
        inputFileName = "";
        FileNames = new Hashtable();
    }

    public MultiPartParser(HttpServletRequest req)
        throws Exception
    {
        parameters = new Vector();
        servletIn = null;
        fileOut = null;
        fileBuffer = new byte[1024];
        fileBufferLength = 0;
        endOfFile = false;
        leadBufferLength = 0;
        leadBuffer = new byte[100];
        delimiter = new byte[35];
        nameHeader = (new String("Content-Disposition: form-data; name=")).getBytes();
        oneByte = 0;
        targetDir = ".";
        inputFileName = "";
        FileNames = new Hashtable();
        getRequest(req);
    }

    public MultiPartParser(HttpServletRequest req, String dir)
        throws Exception
    {
        parameters = new Vector();
        servletIn = null;
        fileOut = null;
        fileBuffer = new byte[1024];
        fileBufferLength = 0;
        endOfFile = false;
        leadBufferLength = 0;
        leadBuffer = new byte[100];
        delimiter = new byte[35];
        nameHeader = (new String("Content-Disposition: form-data; name=")).getBytes();
        oneByte = 0;
        targetDir = ".";
        inputFileName = "";
        FileNames = new Hashtable();
        setTargetDir(dir);
        getRequest(req);
    }

    public void getRequest(HttpServletRequest req)
        throws Exception
    {
        servletIn = new DataInputStream(req.getInputStream());
        makeParameters();
    }

    private void makeParameters()
        throws Exception
    {
        if(servletIn.read(delimiter) != delimiter.length)
            return;
        while(!endOfFile)
        {
            parmName = null;
            parmValue = null;
            saveFile = null;
            try
            {
                while(servletIn.readByte() != 10) ;
                getParmName();
                if(oneByte == 10)
                {
                    while(servletIn.readByte() != 10) ;
                    getParmValue();
                } else
                if(oneByte == 59)
                {
                    servletIn.readByte();
                    if(getFileName() > 0)
                        FileNames.put(parmName, inputFileName);
                }
            }
            catch(EOFException e)
            {
                endOfFile = true;
            }
            addParameter();
        }
    }

    private void addParameter()
    {
        if(parmName == null)
        {
            return;
        } else
        {
            MultiPartParameter mp = new MultiPartParameter(parmName);
            mp.setParmValue(parmValue);
            mp.setSaveFile(saveFile);
            parameters.addElement(mp);
            return;
        }
    }

    private void getParmName()
        throws Exception
    {
        byte data[] = new byte[100];
        while(servletIn.readByte() != 61) ;
        int length = 0;
        do
        {
            oneByte = servletIn.readByte();
            if(oneByte != 10 && oneByte != 59)
            {
                data[length] = oneByte;
                length++;
            } else
            {
                parmName = trimParm(new String(data, 0, length));
                return;
            }
        } while(true);
    }

    private void getParmValue()
        throws Exception
    {
        byte data[] = new byte[1024];
        int length = 0;
        do
        {
            oneByte = servletIn.readByte();
            if(oneByte == 45)
            {
                if(!isDelimiter(oneByte))
                {
                    for(int i = 0; i < leadBufferLength; i++)
                    {
                        data[length] = leadBuffer[i];
                        length++;
                        data = checkBuffer(data, length);
                    }

                } else
                {
                    parmValue = trimParm(new String(data, 0, length, "euc-kr"));
                    return;
                }
            } else
            {
                data[length] = oneByte;
                length++;
                data = checkBuffer(data, length);
            }
        } while(true);
    }

    private byte[] checkBuffer(byte buff[], int length)
    {
        if(buff == null)
            return null;
        if(buff.length - 10 <= length)
        {
            byte newBuff[] = new byte[buff.length * 2];
            System.arraycopy(buff, 0, newBuff, 0, buff.length);
            return newBuff;
        } else
        {
            return buff;
        }
    }

    private Enumeration getMultiPartParameters()
    {
        return parameters.elements();
    }

    private int getFileName()
        throws Exception
    {
        byte data[] = new byte[500];
        int length = 0;
        while(servletIn.readByte() != 61) ;
        while((oneByte = servletIn.readByte()) != 10)
        {
            data[length] = oneByte;
            length++;
        }
        parmValue = trimParm(new String(data, 0, length));
        if(parmValue == null || parmValue.length() == 0)
        {
            do
                oneByte = servletIn.readByte();
            while(oneByte == 13 || oneByte == 10 || !isDelimiter(oneByte));
            return -1;
        }
        fileOpen();
        while((oneByte = servletIn.readByte()) != 10) ;
        servletIn.readByte();
        servletIn.readByte();
        length = 0;
        do
        {
            oneByte = servletIn.readByte();
            if(oneByte == 10)
            {
                data[length] = oneByte;
                length++;
                oneByte = servletIn.readByte();
                data[length] = oneByte;
                length++;
                if(oneByte == 45)
                {
                    if(isDelimiter(oneByte))
                        break;
                    if(length > 0)
                    {
                        fileWrite(data, 0, length);
                        length = 0;
                    }
                    fileWrite(leadBuffer, 1, leadBufferLength - 1);
                } else
                if(length > 0)
                {
                    fileWrite(data, 0, length);
                    length = 0;
                }
                continue;
            }
            if(oneByte == 45)
            {
                data[length] = oneByte;
                length++;
                if(isDelimiter(oneByte))
                    break;
                if(length > 0)
                {
                    fileWrite(data, 0, length);
                    length = 0;
                }
                fileWrite(leadBuffer, 1, leadBufferLength - 1);
            } else
            {
                if(length > 0)
                {
                    fileWrite(data, 0, length);
                    length = 0;
                }
                data[length] = oneByte;
                length++;
            }
        } while(true);
        length--;
        if(--length > 0)
        {
            if(data[length - 1] == 13)
                length--;
            fileWrite(data, 0, length);
        }
        fileClose();
        return 1;
    }

    private void fileOpen()
        throws Exception
    {
        saveFile = getTargetFile();
        fileOut = new FileOutputStream(saveFile);
        fileBufferLength = 0;
    }

    private void fileWrite(byte inData[], int startIndex, int inLength)
        throws Exception
    {
        for(int i = 0; i < inLength; i++)
        {
            if(fileBufferLength == 1024)
                fileBufferFlush();
            fileBuffer[fileBufferLength++] = inData[startIndex++];
        }

    }

    private void fileBufferFlush()
        throws Exception
    {
        fileOut.write(fileBuffer, 0, 64);
        fileBufferLength = 0;
        for(int i = 64; i < 1024; i++)
            fileBuffer[fileBufferLength++] = fileBuffer[i];

    }

    private void fileClose()
        throws Exception
    {
        if(fileBufferLength > 0)
        {
            if(fileBuffer[fileBufferLength - 1] == 13)
                fileBufferLength--;
            fileOut.write(fileBuffer, 0, fileBufferLength);
            fileBufferLength = 0;
        }
        fileOut.close();
    }

    private boolean isDelimiter(byte initByte)
        throws Exception
    {
        leadBufferLength = 0;
        leadBuffer[leadBufferLength] = initByte;
        leadBufferLength++;
        do
        {
            oneByte = servletIn.readByte();
            leadBuffer[leadBufferLength] = oneByte;
            leadBufferLength++;
            if(compareBytes(delimiter, leadBuffer, leadBufferLength))
            {
                if(leadBufferLength == delimiter.length)
                    return true;
            } else
            {
                return false;
            }
        } while(true);
    }

    private boolean compareBytes(byte source[], byte target[], int length)
        throws Exception
    {
        if(source == null || target == null)
            return false;
        if(source.length < length || target.length < length)
            return false;
        for(int i = 0; i < length; i++)
            if(source[i] != target[i])
                return false;

        return true;
    }

    private String trimParm(String s)
        throws Exception
    {
        if(s == null)
            return null;
        byte src[] = s.trim().getBytes();
        if(src.length == 0)
            return null;
        if(src[0] == 34)
            src[0] = 32;
        if(src[src.length - 1] == 34)
            src[src.length - 1] = 32;
        String str = (new String(src, "8859_1")).trim();
        if(str != null && str.length() == 0)
            str = null;
        return str;
    }

    public void setTargetDir(String dir)
        throws Exception
    {
        File directory = new File(dir);
        if(!directory.exists())
            directory.mkdirs();
        if(dir == null || !(new File(dir)).isDirectory())
        {
            throw new Exception("invalid Directory");
        } else
        {
            targetDir = dir;
            return;
        }
    }

    public String getTargetDir()
        throws Exception
    {
        return targetDir;
    }

    private File getTargetFile()
        throws Exception
    {
        int serial = 0;
        char otherSeparator = File.separatorChar != '/' ? '/' : '\\';
        String tmpName = parmValue;
        tmpName = tmpName.replace(otherSeparator, File.separatorChar);
        tmpName = new String(tmpName.getBytes("8859_1"));
        String fileName = (new File(tmpName)).getName();
        int pointIndex = fileName.lastIndexOf('.');
        String surfix;
        String prefix;
        if(pointIndex == -1 || pointIndex == fileName.length() - 1)
        {
            prefix = fileName;
            surfix = null;
        } else
        {
            prefix = fileName.substring(0, pointIndex);
            surfix = fileName.substring(pointIndex + 1);
        }
        fileName = surfix != null ? prefix + "." + surfix : prefix;
        inputFileName = fileName;
        Date currentTime = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssS");
        fileName = fmt.format(currentTime) + fileName;
        File file;
        for(file = new File(targetDir, fileName); file.exists();)
        {
            String serialSurfix = String.valueOf(serial);
            fileName = surfix != null ? prefix + serialSurfix + "." + surfix : prefix + serialSurfix;
            file = new File(targetDir, fileName);
            serial++;
        }

        return file;
    }

    public String getParameter(String name)
    {
        for(Enumeration enumParms = getMultiPartParameters(); enumParms.hasMoreElements();)
        {
            MultiPartParameter parm = (MultiPartParameter)enumParms.nextElement();
            if(name.equals(parm.getParmName()))
            {
                String pv = null;
                try
                {
                    pv = new String(parm.getParmValue().getBytes("8859_1"));
                }
                catch(Exception exception) { }
                return pv;
            }
        }

        return null;
    }

    public String getisnullParameter(String name, String target)
    {
        return FormatUtil.isnull2(getParameter(name), target);
    }

    public File getFile(String name)
    {
        for(Enumeration enumParms = getMultiPartParameters(); enumParms.hasMoreElements();)
        {
            MultiPartParameter parm = (MultiPartParameter)enumParms.nextElement();
            if(name.equals(parm.getParmName()))
                return parm.getSaveFile();
        }

        return null;
    }

    public String[] getParameterValues(String name)
    {
        Vector values = new Vector();
        for(Enumeration enumParms = getMultiPartParameters(); enumParms.hasMoreElements();)
        {
            MultiPartParameter parm = (MultiPartParameter)enumParms.nextElement();
            if(name.equals(parm.getParmName()))
                values.addElement(parm.getParmValue());
        }

        int length = values.size();
        String stringValues[] = new String[length];
        for(int i = 0; i < length; i++)
            stringValues[i] = (String)values.elementAt(i);

        return stringValues;
    }

    public Enumeration getParameterNames()
    {
        Vector names = new Vector();
        MultiPartParameter parm;
        for(Enumeration enumParms = getMultiPartParameters(); enumParms.hasMoreElements(); names.addElement(parm.getParmName()))
            parm = (MultiPartParameter)enumParms.nextElement();

        return names.elements();
    }

    private Vector parameters;
    private DataInputStream servletIn;
    private FileOutputStream fileOut;
    private final int fileBufferMax = 1024;
    private final int fileBufferMin = 64;
    private byte fileBuffer[];
    private int fileBufferLength;
    private boolean endOfFile;
    private int leadBufferLength;
    private byte leadBuffer[];
    private byte delimiter[];
    private byte nameHeader[];
    private byte oneByte;
    private final byte NEWLINE = 10;
    private final byte CR = 13;
    private final byte DASH = 45;
    private final byte EQUAL = 61;
    private final byte SPACE = 32;
    private final byte SEMICOLON = 59;
    private final byte DOUBLEQUOTE = 34;
    private String targetDir;
    private String parmName;
    private String parmValue;
    private File saveFile;
    public String inputFileName;
    public Hashtable FileNames;
}
