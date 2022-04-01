// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Part.java

package com.credu.scorm.multi.multipart;


public abstract class Part
{

    Part(String s)
    {
        name = s;
    }

    public String getName()
    {
        return name;
    }

    public boolean isFile()
    {
        return false;
    }

    public boolean isParam()
    {
        return false;
    }

    private String name;
}
