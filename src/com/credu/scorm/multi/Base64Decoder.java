// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Base64Decoder.java

package com.credu.scorm.multi;

import java.io.*;

public class Base64Decoder extends FilterInputStream
{

    public Base64Decoder(InputStream inputstream)
    {
        super(inputstream);
    }

    public int read()
        throws IOException
    {
        int i;
        do
        {
            i = in.read();
            if(i == -1)
                return -1;
        } while(Character.isWhitespace((char)i));
        charCount++;
        if(i == 61)
            return -1;
        i = ints[i];
        int j = (charCount - 1) % 4;
        if(j == 0)
        {
            carryOver = i & 0x3f;
            return read();
        }
        if(j == 1)
        {
            int k = (carryOver << 2) + (i >> 4) & 0xff;
            carryOver = i & 0xf;
            return k;
        }
        if(j == 2)
        {
            int l = (carryOver << 4) + (i >> 2) & 0xff;
            carryOver = i & 0x3;
            return l;
        }
        if(j == 3)
        {
            int i1 = (carryOver << 6) + i & 0xff;
            return i1;
        } else
        {
            return -1;
        }
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k;
        for(k = 0; k < j; k++)
        {
            int l = read();
            if(l == -1 && k == 0)
                return -1;
            if(l == -1)
                break;
            abyte0[i + k] = (byte)l;
        }

        return k;
    }

    public static String decode(String s)
    {
        byte abyte0[] = null;
        try
        {
            abyte0 = s.getBytes("8859_1");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        Base64Decoder base64decoder = new Base64Decoder(new ByteArrayInputStream(abyte0));
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream((int)((double)abyte0.length * 0.67000000000000004D));
        try
        {
            byte abyte1[] = new byte[4096];
            int i;
            while((i = base64decoder.read(abyte1)) != -1) 
                bytearrayoutputstream.write(abyte1, 0, i);
            bytearrayoutputstream.close();
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
            System.err.println("Usage: java Base64Decoder fileToDecode");
        Base64Decoder base64decoder = null;
        try
        {
            base64decoder = new Base64Decoder(new BufferedInputStream(new FileInputStream(args[0])));
            byte abyte0[] = new byte[4096];
            int i;
            while((i = base64decoder.read(abyte0)) != -1) 
                System.out.write(abyte0, 0, i);
        }
        finally
        {
            if(base64decoder != null)
                base64decoder.close();
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
    private static final int ints[];
    private int charCount;
    private int carryOver;

    static 
    {
        ints = new int[128];
        for(int i = 0; i < 64; i++)
            ints[chars[i]] = i;

    }
}
