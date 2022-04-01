// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileRenamePolicy.java

package com.credu.scorm.multi.multipart;

import java.io.File;

public interface FileRenamePolicy
{

    public abstract File rename(File file);
}
