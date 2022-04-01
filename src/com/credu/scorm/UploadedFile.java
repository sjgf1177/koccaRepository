package com.credu.scorm;

import java.io.File;

class UploadedFile
{

    UploadedFile(String dir, String filename, String type)
    {
        this.dir = dir;
        this.filename = filename;
        this.type = type;
    }

    public String getContentType()
    {
        return type;
    }

    public String getFilesystemName()
    {
        return filename;
    }

    public File getFile()
    {
        if(dir == null || filename == null)
            return null;
        else
            return new File(dir + File.separator + filename);
    }

    private String dir;
    private String filename;
    private String type;
}
