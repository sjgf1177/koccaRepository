// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Base64Encoder.java

package com.credu.scorm.multi;

import java.io.*;

public class Base64Encoder extends FilterOutputStream
{

    public Base64Encoder(OutputStream outputstream)
    {
        super(outputstream);
    }

    public void write(int i)
        throws IOException
    {
        if(i < 0)
            i += 256;
        if(charCount % 3 == 0)
        {
            int j = i >> 2;
            carryOver = i & 0x3;
            out.write(chars[j]);
        } else
        if(charCount % 3 == 1)
        {
            int k = (carryOver << 4) + (i >> 4) & 0x3f;
            carryOver = i & 0xf;
            out.write(chars[k]);
        } else
        if(charCount % 3 == 2)
        {
            int l = (carryOver << 2) + (i >> 6) & 0x3f;
            out.write(chars[l]);
            l = i & 0x3f;
            out.write(chars[l]);
            carryOver = 0;
        }
        charCount++;
        if(charCount % 57 == 0)
            out.write(10);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        for(int k = 0; k < j; k++)
            write(abyte0[i + k]);

    }

    public void close()
        throws IOException
    {
        if(charCount % 3 == 1)
        {
            int i = carryOver << 4 & 0x3f;
            out.write(chars[i]);
            out.write(61);
            out.write(61);
        } else
        if(charCount % 3 == 2)
        {
            int j = carryOver << 2 & 0x3f;
            out.write(chars[j]);
            out.write(61);
        }
        super.close();
    }

    public static String encode(String s)
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream((int)((double)s.length() * 1.3700000000000001D));
        Base64Encoder base64encoder = new Base64Encoder(bytearrayoutputstream);
        byte abyte0[] = null;
        try
        {
            abyte0 = s.getBytes("8859_1");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        try
        {
            base64encoder.write(abyte0);
            base64encoder.close();
            return bytearrayoutputstream.toString("8859_1");
        }
        catch(IOException ioexception)
        {
            return null;
        }
    }

    public static void main(String args[])
        throws Exception
    {
        if(args.length != 1)
            System.err.println("Usage: java com.credu.scorm.multi.Base64Encoder fileToEncode");
        Base64Encoder base64encoder = null;
        BufferedInputStream bufferedinputstream = null;
        try
        {
            base64encoder = new Base64Encoder(System.out);
            bufferedinputstream = new BufferedInputStream(new FileInputStream(args[0]));
            byte abyte0[] = new byte[4096];
            int i;
            while((i = bufferedinputstream.read(abyte0)) != -1) 
                base64encoder.write(abyte0, 0, i);
        }
        finally
        {
            if(bufferedinputstream != null)
                bufferedinputstream.close();
            if(base64encoder != null)
                base64encoder.close();
        }
    }

    private static final char chars[] = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
        'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
        '8', '9', '+', '/'
    };
    private int charCount;
    private int carryOver;

}
