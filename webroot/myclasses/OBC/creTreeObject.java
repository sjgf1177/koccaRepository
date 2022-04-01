// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   creTreeObject.java

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.StringTokenizer;

public class creTreeObject
{

    public creTreeObject(ImageObserver imageobserver, String s)
        throws Exception
    {
        oid = "";
        module = "";
        lesson = "";
        filetype = "";
        otype = "";
        oatt = "";
        omode = "";
        npage = 0;
        level = 0;
        eduStatus = "";
        eduEnded = "";
        starting = "";
        desc = "";
        openStatus = false;
        lineSize = 0;
        observ = imageobserver;
    }

    public creTreeObject(String s)
        throws Exception
    {
        oid = "";
        module = "";
        lesson = "";
        filetype = "";
        otype = "";
        oatt = "";
        omode = "";
        npage = 0;
        level = 0;
        eduStatus = "";
        eduEnded = "";
        starting = "";
        desc = "";
        openStatus = false;
        lineSize = 0;
        Object obj = null;
        String as[] = new String[15];
        int i = 0;
        try
        {
            if(s != null)
            {
                for(StringTokenizer stringtokenizer = new StringTokenizer(s, "||"); stringtokenizer.hasMoreTokens();)
                {
                    as[i] = stringtokenizer.nextToken();
                    i++;
                }

            }
            idx = parsenum(as[0], 10, 10);
            module = as[1];
            lesson = as[2];
            oid = as[3];
            otype = as[4];
            omode = as[5];
            filetype = as[6];
            npage = parsenum(as[7], 10, 10);
            oatt = as[8];
            eduStatus = as[9];
            eduEnded = as[9];
            starting = as[10];
            desc = as[11];
            if(as[12].equals("Y"))
                openStatus = true;
            select = false;
            pos_x = 0;
            pos_y = 0;
            partImage = null;
            if(otype.equals("MO"))
                level = 0;
            else
            if(otype.equals("LE"))
                level = 1;
            else
            if(otype.equals("SC"))
                level = 2;
            else
                level = 0;
        }
        catch(Exception exception)
        {
            throw new Exception("in setcreTreeObjectEtc" + exception.getMessage());
        }
    }

    public boolean checkIn(int i, int j)
    {
        if(partImage == null)
            return false;
        if(i >= pos_x && i <= pos_x + width)
            return j >= pos_y && j <= pos_y + height;
        else
            return false;
    }

    public void paint(Graphics g)
    {
        if(partImage != null)
        {
            g.drawImage(partImage, pos_x, pos_y, width, height, observ);
            if(select)
            {
                Color color = g.getColor();
                g.setColor(Color.yellow);
                g.setColor(color);
            }
        }
    }

    public void setEduStatus(Image image, String s)
    {
        partImage = image;
        eduStatus = s;
    }

    public void setProperty(Image image, String s)
    {
        width = image.getWidth(observ);
        height = image.getHeight(observ);
        select = false;
        partImage = image;
    }

    public int parsenum(String s, int i, int j)
    {
        if(s == null)
            return j;
        int k = j;
        try
        {
            k = Integer.parseInt(s, i);
        }
        catch(NumberFormatException numberformatexception)
        {
            k = j;
        }
        return k;
    }

    ImageObserver observ;
    public int pos_x;
    public int pos_y;
    public static final int iWIDTH = 255;
    public static final int iHEIGHT = 315;
    private int width;
    private int height;
    public Image partImage;
    public int idx;
    public boolean select;
    public String oid;
    public String module;
    public String lesson;
    public String filetype;
    public String otype;
    public String oatt;
    public String omode;
    public int npage;
    public int level;
    public String eduStatus;
    public String eduEnded;
    public String starting;
    public String desc;
    public boolean openStatus;
    public int lineSize;
}
