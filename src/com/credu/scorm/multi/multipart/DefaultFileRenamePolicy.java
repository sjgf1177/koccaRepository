// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultFileRenamePolicy.java

package com.credu.scorm.multi.multipart;

import java.io.File;

// Referenced classes of package com.credu.scorm.multi.multipart:
//            FileRenamePolicy

public class DefaultFileRenamePolicy
    implements FileRenamePolicy
{

    public DefaultFileRenamePolicy()
    {
    }

    public File rename(File file)
    {
        if(!file.exists())
            return file;
        String s = file.getName();
        String s1 = null;
        String s2 = null;
        int i = s.lastIndexOf(".");
        if(i != -1)
        {
            s1 = s.substring(0, i);
            s2 = s.substring(i);
        } else
        {
            s1 = s;
            s2 = "";
        }
        int j = 0;
        String s3;
        for(; file.exists(); file = new File(file.getParent(), s3))
        {
            j++;
            s3 = s1 + j + s2;
        }

        return file;
    }
}
