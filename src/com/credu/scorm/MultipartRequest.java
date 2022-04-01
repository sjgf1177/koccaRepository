package com.credu.scorm;

import java.io.*;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package alexit.lib.util:
//            UploadedFile, MultipartInputStreamHandler

public class MultipartRequest extends HttpServlet
{

    public MultipartRequest(HttpServletRequest request, String saveDirectory)
        throws IOException
    {
        this(request, saveDirectory, 0x100000);
    }
    public MultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize)
        throws IOException
    {
        parameters = new Hashtable();
        files = new Hashtable();
        if(request == null)
            throw new IllegalArgumentException("request cannot be null");
        if(saveDirectory == null)
            throw new IllegalArgumentException("saveDirectory cannot be null");
        if(maxPostSize <= 0)
            throw new IllegalArgumentException("maxPostSize must be positive");
        req = request;
        dir = new File(saveDirectory);
        maxSize = maxPostSize;
        if(!dir.isDirectory())
            throw new IllegalArgumentException("Not a directory: " + saveDirectory);
        if(!dir.canWrite())
            throw new IllegalArgumentException("Not writable: " + saveDirectory);
        if(getFileSizeCheck().equals("SUCCESS"))
            readRequest();
    }

    public MultipartRequest(ServletRequest request, String saveDirectory)
        throws IOException
    {
        this((HttpServletRequest)request, saveDirectory);
    }

    public MultipartRequest(ServletRequest request, String saveDirectory, int maxPostSize)
        throws IOException
    {
        this((HttpServletRequest)request, saveDirectory, maxPostSize);
    }

    public Enumeration getParameterNames()
    {
        return parameters.keys();
    }

    public Enumeration getFileNames()
    {
        return files.keys();
    }

    public int getRequestSize()
    {
        int length = req.getContentLength();
        return length;
    }

    public int getFileNum()
    {
        return files.size();
    }

    public String getParameter(String name)
    {
        try
        {
            Vector values = (Vector)parameters.get(name);
            if(values == null || values.size() == 0)
            {
                return null;
            } else
            {
                String value = (String)values.elementAt(values.size() - 1);
                return value;
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String[] getParameterValues(String name)
    {
        try
        {
            Vector values = (Vector)parameters.get(name);
            if(values == null || values.size() == 0)
            {
                return null;
            } else
            {
                String valuesArray[] = new String[values.size()];
                values.copyInto(valuesArray);
                return valuesArray;
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String getFilesystemName(String name)
    {
        try
        {
            UploadedFile file = (UploadedFile)files.get(name);
            return file.getFilesystemName();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public Enumeration getFileRealNames()
    {
        try
        {
            Vector lm_oFileNames = new Vector();
            for(Enumeration e = files.keys(); e.hasMoreElements();)
            {
                UploadedFile file = (UploadedFile)files.get(e.nextElement());
                if(file.getFilesystemName() != null && !file.getFilesystemName().equals(""))
                    lm_oFileNames.add(file.getFilesystemName());
            }

            Enumeration lm_oFiles = lm_oFileNames.elements();
            return lm_oFiles;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String getContentType(String name)
    {
        try
        {
            UploadedFile file = (UploadedFile)files.get(name);
            return file.getContentType();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public File getFile(String name)
    {
        try
        {
            UploadedFile file = (UploadedFile)files.get(name);
            return file.getFile();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String getFileSizeCheck()
    {
        try
        {
            int lm_iLength = req.getContentLength();
            if(lm_iLength <= maxSize)
                return "SUCCESS";
            else
                return "FAIL";
        }
        catch(Exception e)
        {
            return null;
        }
    }

    protected void readRequest()
        throws IOException
    {
        int length = req.getContentLength();
        String type = null;
        String type1 = req.getHeader("Content-Type");
        String type2 = req.getContentType();
        if(type1 == null && type2 != null)
            type = type2;
        else
        if(type2 == null && type1 != null)
            type = type1;
        else
        if(type1 != null && type2 != null)
            type = type1.length() <= type2.length() ? type2 : type1;
        if(type == null || !type.toLowerCase().startsWith("multipart/form-data"))
            throw new IOException("Posted content type isn't multipart/form-data");
        String boundary = extractBoundary(type);
        if(boundary == null)
            throw new IOException("Separation boundary was not specified");
        MultipartInputStreamHandler in = new MultipartInputStreamHandler(req.getInputStream(), length);
        String line = in.readLine();
        if(line == null)
            throw new IOException("Corrupt form data: premature ending");
        if(!line.startsWith(boundary))
            throw new IOException("Corrupt form data: no leading boundary");
        for(boolean done = false; !done; done = readNextPart(in, boundary));
    }

    protected boolean readNextPart(MultipartInputStreamHandler in, String boundary)
        throws IOException
    {
        String line = in.readLine();
        if(line == null)
            return true;
        if(line.length() == 0)
            return true;
        String dispInfo[] = extractDispositionInfo(line);
        String disposition = dispInfo[0];
        String name = dispInfo[1];
        String filename = dispInfo[2];
        line = in.readLine();
        if(line == null)
            return true;
        String contentType = extractContentType(line);
        if(contentType != null)
        {
            line = in.readLine();
            if(line == null || line.length() > 0)
                throw new IOException("Malformed line after content type: " + line);
        } else
        {
            contentType = "application/octet-stream";
        }
        if(filename == null)
        {
            String value = readParameter(in, boundary);
            if(value.equals(""))
                value = null;
            Vector existingValues = (Vector)parameters.get(name);
            if(existingValues == null)
            {
                existingValues = new Vector();
                parameters.put(name, existingValues);
            }
            existingValues.addElement(value);
        } else
        {
            readAndSaveFile(in, boundary, filename, contentType, name);
            if(filename.equals("unknown"))
                files.put(name, new UploadedFile(null, null, null));
        }
        return false;
    }

    protected String readParameter(MultipartInputStreamHandler in, String boundary)
        throws IOException
    {
        StringBuffer sbuf = new StringBuffer();
        String line;
        while((line = in.readLine()) != null)
        {
            if(line.startsWith(boundary))
                break;
            sbuf.append(line + "\r\n");
        }
        if(sbuf.length() == 0)
        {
            return null;
        } else
        {
            sbuf.setLength(sbuf.length() - 2);
            return sbuf.toString();
        }
    }

    protected void readAndSaveFile(MultipartInputStreamHandler in, String boundary, String filename, String contentType, String name)
        throws IOException
    {
        OutputStream os = null;
        String sFilenameTemp = null;
        if(filename.equals("unknown"))
        {
            os = new ByteArrayOutputStream();
        } else
        {
            int lm_iIndex = -2;
            lm_iIndex = filename.lastIndexOf("\\");
            String sFilepath = filename.substring(0, lm_iIndex + 1);
            String sFilename = filename.substring(lm_iIndex + 1);
            File f = null;
            lm_iIndex = sFilename.lastIndexOf('.');
            String sFilenameNoExt;
            String sExt;
            if(lm_iIndex > -1)
            {
                sFilenameNoExt = sFilename.substring(0, lm_iIndex);
                sExt = sFilename.substring(lm_iIndex);
            } else
            {
                sFilenameNoExt = sFilename;
                sExt = "";
            }
            int countfilename = 0;
            sFilenameTemp = sFilename;
            boolean bExist = true;
            while(bExist)
            {
                f = new File(dir + File.separator + sFilenameTemp);
                if(f.exists())
                {
			countfilename++;
		//	sFilenameTemp = sFilenameNoExt + "$" + countfilename + sExt;
			sFilenameTemp = sFilenameNoExt +"("+ countfilename +")"+ sExt;
                } else
                {
                    bExist = false;
                }
            }
            File lm_oFile = null;
            lm_oFile = new File(dir + File.separator + sFilenameTemp);
            os = new FileOutputStream(lm_oFile);
            sFilenameTemp = sFilepath + sFilenameTemp;
        }
        BufferedOutputStream out = new BufferedOutputStream(os, 8192);
        byte bbuf[] = new byte[0x19000];
        boolean rnflag = false;
        int result;
        while((result = in.readLine(bbuf, 0, bbuf.length)) != -1)
        {
            if(result > 2 && bbuf[0] == 45 && bbuf[1] == 45)
            {
                String line = new String(bbuf, 0, result, "KSC5601");
                if(line.startsWith(boundary))
                    break;
            }
            if(rnflag)
            {
                out.write(13);
                out.write(10);
                rnflag = false;
            }
            if(result >= 2 && bbuf[result - 2] == 13 && bbuf[result - 1] == 10)
            {
                out.write(bbuf, 0, result - 2);
                rnflag = true;
            } else
            {
                out.write(bbuf, 0, result);
            }
        }
        out.flush();
        out.close();
        os.close();
        if(!filename.equals("unknown"))
            files.put(name, new UploadedFile(dir.toString(), sFilenameTemp, contentType));
    }

    private String extractBoundary(String line)
    {
        int index = line.lastIndexOf("boundary=");
        if(index == -1)
        {
            return null;
        } else
        {
            String boundary = line.substring(index + 9);
            boundary = "--" + boundary;
            return boundary;
        }
    }

    private String[] extractDispositionInfo(String line)
        throws IOException
    {
        String retval[] = new String[4];
        String origline = line;
        line = origline.toLowerCase();
        int start = line.indexOf("content-disposition: ");
        int end = line.indexOf(";");
        if(start == -1 || end == -1)
            throw new IOException("Content disposition corrupt: " + origline);
        String disposition = line.substring(start + 21, end);
        if(!disposition.equals("form-data"))
            throw new IOException("Invalid content disposition: " + disposition);
        start = line.indexOf("name=\"", end);
        end = line.indexOf("\"", start + 7);
        if(start == -1 || end == -1)
            throw new IOException("Content disposition corrupt: " + origline);
        String name = origline.substring(start + 6, end);
        String filename = null;
        start = line.indexOf("filename=\"", end + 2);
        end = line.indexOf("\"", start + 10);
        if(start != -1 && end != -1)
        {
            filename = origline.substring(start + 10, end);
            if(filename.equals(""))
                filename = "unknown";
        }
        retval[0] = disposition;
        retval[1] = name;
        retval[2] = filename;
        return retval;
    }

    private String extractContentType(String line)
        throws IOException
    {
        String contentType = null;
        String origline = line;
        line = origline.toLowerCase();
        if(line.startsWith("content-type"))
        {
            int start = line.indexOf(" ");
            if(start == -1)
                throw new IOException("Content type corrupt: " + origline);
            contentType = line.substring(start + 1);
        } else
        if(line.length() != 0)
            throw new IOException("Malformed line after disposition: " + origline);
        return contentType;
    }

    private static final int DEFAULT_MAX_POST_SIZE = 0x100000;
    private static final String NO_FILE = "unknown";
    private HttpServletRequest req;
    private File dir;
    private int maxSize;
    private Hashtable parameters;
    private Hashtable files;
}
