// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NamoMime.java

package com.namo;

import java.io.*;
import java.util.Vector;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

// Referenced classes of package com.namo:
//            MimeDecodeException

public class NamoMime
{
    static final class MIME
    {

        public static int BEGIN = 0;
        public static int HEADER = 1;
        public static int BODY = 2;
        public static int END = 3;


        MIME()
        {
        }
    }

    class MimePart
    {

        public void setName(String s)
        {
            name = s;
        }

        public String getName()
        {
            return name;
        }

        public void setBodypart(String s)
        {
            bodypart = s;
        }

        public String getBodypart()
        {
            return bodypart;
        }

        public void setContentType(String s)
        {
            contentType = s;
        }

        public String getContentType()
        {
            return contentType;
        }

        public void setContentID(String s)
        {
            contentID = s;
        }

        public String getContentID()
        {
            return contentID;
        }

        public void setEncoding(String s)
        {
            encoding = s;
        }

        public String getEncoding()
        {
            return encoding;
        }

        private String bodypart;
        private String contentType;
        private String contentID;
        private String encoding;
        private String name;

        MimePart()
        {
        }
    }


    public NamoMime()
    {
    }

    public void setSavePath(String s)
    {
        savePath = s;
    }

    public void setSaveURL(String s)
    {
        saveURL = s;
    }

    protected void checkMimeType(String s)
        throws MimeDecodeException, IOException
    {
        int i = 0;
        BufferedReader bufferedreader = new BufferedReader(new StringReader(s));
        String s1;

        do
        {
            s1 = bufferedreader.readLine();
System.out.println("namo" + s1);
            if(s1 == null) {
System.out.println("namo + end");
//break;
                throw new MimeDecodeException("Cannot find Content-Type:");
			}
            s1 = s1.toLowerCase();
            i = s1.indexOf("content-type");

        } while(i == -1);
System.out.println("namo11111");

        i = s1.indexOf("multipart");
        if(i != -1)
        {
            multipart = true;
            String s2;
            int j;
            do
            {
                s2 = bufferedreader.readLine();
                if(s2 == null)
                    throw new MimeDecodeException("Cannot find boundary");
                j = s2.indexOf("boundary");
            } while(j == -1);
            j = s2.indexOf("\"");
            if(j == -1)
                throw new MimeDecodeException("Connot find boudary");
            boundary = s2.substring(j + 1, s2.indexOf("\"", j + 1));
        }
    }

    protected void splitSinglePart(String s)
        throws IOException
    {
        BufferedReader bufferedreader = new BufferedReader(new StringReader(s));
        Object obj = null;
        Object obj1 = null;
        String s3 = new String();
        MimePart mimepart = new MimePart();
        String s1;
        for(int i = MIME.BEGIN; (s1 = bufferedreader.readLine()) != null && i != MIME.END;)
        {
            s1 = s1.trim();
            if(s1.length() != 0 || i != MIME.BEGIN)
            {
                if(i == MIME.BEGIN)
                    i = MIME.HEADER;
                String s2 = s1.toLowerCase();
                if(i == MIME.HEADER)
                {
                    if(s2.indexOf("mime-version") == -1)
                        if(s2.indexOf("content-type") != -1)
                            mimepart.setContentType(s2.substring(s2.indexOf(":") + 1).trim());
                        else
                        if(s2.indexOf("content-transfer-encoding") != -1)
                            mimepart.setEncoding(s1.substring(s1.indexOf(":") + 1).trim());
                        else
                        if(s2.indexOf("content-id") != -1)
                        {
                            s2 = s1.substring(s1.indexOf("<") + 1, s1.indexOf(">"));
                            mimepart.setContentID(s2);
                        } else
                        if(s2.indexOf("name") != -1)
                        {
                            s2 = s1.substring(s1.indexOf("\"") + 1, s1.length() - 1);
                            mimepart.setName(s2);
                        } else
                        if(s2.length() == 0)
                            i = MIME.BODY;
                } else
                if(i == MIME.BODY)
                    if(s1.length() == 0 && multipart)
                        i = MIME.END;
                    else
                        s3 = s3 + s1;
            }
        }

        mimepart.setBodypart(s3);
        decodePart.addElement(mimepart);
        System.out.println("\uC778\uCF54\uB529 : " + mimepart.getEncoding());
    }

    protected void splitMultiPart(String s)
        throws IOException
    {
        Object obj = null;
        int i = 0;
        boolean flag = false;
        do
        {
            int j = s.indexOf("--" + boundary, i);
            if(j == -1)
                break;
            i = s.indexOf("--" + boundary, j + boundary.length() + 2);
            if(i == -1)
                break;
            String s1 = s.substring(j + boundary.length() + 2, i);
            splitSinglePart(s1);
        } while(true);
    }

    protected void splitMimePart(String s)
        throws IOException
    {
        if(multipart)
            splitMultiPart(s);
        else
            splitSinglePart(s);
    }

    public boolean decode(String s)
        throws MimeDecodeException, IOException
    {
        boolean flag = false;
        decodePart = null;
        decodePart = new Vector();
        checkMimeType(s);
        splitMimePart(s);
        return true;
    }

    public String replace(String s, String s1, String s2)
    {
        String s3 = new String();
        int i = 0;
        int j = 0;
        i = s.indexOf(s1);
        if(i == -1)
            return s;
        for(; i != -1; i = s.indexOf(s1, j))
        {
            s3 = s3 + s.substring(j, i) + s2;
            j = i + s1.length();
        }

        s3 = s3 + s.substring(j);
        return s3;
    }

    protected String changeCIDPath(String s)
    {
        boolean flag = false;
        String s1 = s;
        for(int i = 1; i < decodePart.size(); i++)
        {
            MimePart mimepart = (MimePart)decodePart.elementAt(i);
            if(mimepart.getContentID() != null)
                if(saveURL == null && saveURL.length() <= 0)
                    s1 = replace(s1, "cid:" + mimepart.getContentID(), mimepart.getName());
                else
                    s1 = replace(s1, "cid:" + mimepart.getContentID(), saveURL + "/" + mimepart.getName());
        }

        return s1;
    }

    public String getBodyContent()
        throws MimeDecodeException
    {
        String s = null;
        if(decodePart.size() <= 0)
            return null;
        MimePart mimepart = (MimePart)decodePart.elementAt(0);
        s = mimepart.getBodypart();
        if(mimepart.getEncoding() == null)
            return mimepart.getBodypart();
        byte abyte0[];
        try
        {
            Object obj = new ByteArrayInputStream(mimepart.getBodypart().getBytes("iso-8859-1"));
            try
            {
                obj = MimeUtility.decode(((InputStream) (obj)), mimepart.getEncoding());
            }
            catch(MessagingException messagingexception)
            {
                throw new MimeDecodeException("Cannot Decoding");
            }
            abyte0 = new byte[((InputStream) (obj)).available() + 1];
            ((InputStream) (obj)).read(abyte0);
        }
        catch(IOException ioexception)
        {
            throw new MimeDecodeException("Cannot create input stream");
        }
        s = new String(abyte0);
        if(multipart)
            s = changeCIDPath(s);
        return s;
    }

    public void saveFile()
        throws MimeDecodeException
    {
        for(int i = 1; i < decodePart.size(); i++)
        {
            Object obj = null;
            Object obj1 = null;
            Object obj2 = null;
            Object obj4 = null;
            MimePart mimepart = (MimePart)decodePart.elementAt(i);
            try
            {
                Object obj3 = new ByteArrayInputStream(mimepart.getBodypart().getBytes("iso-8859-1"));
                obj3 = MimeUtility.decode(((InputStream) (obj3)), mimepart.getEncoding());
                byte abyte0[] = new byte[((InputStream) (obj3)).available() + 1];
                ((InputStream) (obj3)).read(abyte0);
                System.out.print(savePath + "/" + mimepart.getName());
                File file = new File(savePath + "/" + mimepart.getName());
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                fileoutputstream.write(abyte0);
                fileoutputstream.close();
            }
            catch(FileNotFoundException filenotfoundexception)
            {
                throw new MimeDecodeException("Cannot create file");
            }
            catch(IOException ioexception)
            {
                throw new MimeDecodeException("Cannot write file");
            }
            catch(MessagingException messagingexception)
            {
                throw new MimeDecodeException("Cannot decode file");
            }
        }

    }

    public void NamoMime()
    {
        multipart = false;
        boundary = null;
    }

    public static void main(String args[])
        throws MimeDecodeException
    {
        NamoMime namomime = new NamoMime();
        String s = null;
        byte abyte0[];
        try
        {
            FileInputStream fileinputstream = new FileInputStream("C:/tomcat-4.0.1/webapps/ROOT/mime2.txt");
            abyte0 = new byte[fileinputstream.available() + 1];
            fileinputstream.read(abyte0);
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            return;
        }
        catch(IOException ioexception1)
        {
            return;
        }
        s = new String(abyte0);
        try
        {
            namomime.setSaveURL("http://www.joins.com");
            namomime.setSavePath("c:/tomcat-4.0.1/webapps/ROOT");
            namomime.decode(s);
            System.out.println(namomime.getBodyContent());
            namomime.saveFile();
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    private boolean multipart;
    private String boundary;
    private Vector decodePart;
    private String savePath;
    private String saveURL;
}
