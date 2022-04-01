// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocaleNegotiator.java

package com.credu.scorm.multi;

import java.util.*;

// Referenced classes of package com.credu.scorm.multi:
//            LocaleToCharsetMap

public class LocaleNegotiator
{

    public LocaleNegotiator(String s, String s1, String s2)
    {
        Locale locale = new Locale("en", "US");
        String s3 = "ISO-8859-1";
        ResourceBundle resourcebundle = null;
        try
        {
            resourcebundle = ResourceBundle.getBundle(s, locale);
        }
        catch(MissingResourceException missingresourceexception) { }
        if(s1 == null)
        {
            chosenLocale = locale;
            chosenCharset = s3;
            chosenBundle = resourcebundle;
            return;
        }
        for(StringTokenizer stringtokenizer = new StringTokenizer(s1, ","); stringtokenizer.hasMoreTokens();)
        {
            String s4 = stringtokenizer.nextToken();
            Locale locale1 = getLocaleForLanguage(s4);
            ResourceBundle resourcebundle1 = getBundleNoFallback(s, locale1);
            if(resourcebundle1 != null)
            {
                String s5 = getCharsetForLocale(locale1, s2);
                if(s5 != null)
                {
                    chosenLocale = locale1;
                    chosenBundle = resourcebundle1;
                    chosenCharset = s5;
                    return;
                }
            }
        }

        chosenLocale = locale;
        chosenCharset = s3;
        chosenBundle = resourcebundle;
    }

    public ResourceBundle getBundle()
    {
        return chosenBundle;
    }

    public Locale getLocale()
    {
        return chosenLocale;
    }

    public String getCharset()
    {
        return chosenCharset;
    }

    private Locale getLocaleForLanguage(String s)
    {
        int i;
        if((i = s.indexOf(59)) != -1)
            s = s.substring(0, i);
        s = s.trim();
        Locale locale;
        int j;
        if((j = s.indexOf(45)) == -1)
            locale = new Locale(s, "");
        else
            locale = new Locale(s.substring(0, j), s.substring(j + 1));
        return locale;
    }

    private ResourceBundle getBundleNoFallback(String s, Locale locale)
    {
        ResourceBundle resourcebundle = null;
        try
        {
            resourcebundle = ResourceBundle.getBundle(s, new Locale("bogus", ""));
        }
        catch(MissingResourceException missingresourceexception) { }
        try
        {
            ResourceBundle resourcebundle1 = ResourceBundle.getBundle(s, locale);
            if(resourcebundle1 != resourcebundle)
                return resourcebundle1;
            if(resourcebundle1 == resourcebundle && locale.getLanguage().equals(Locale.getDefault().getLanguage()))
                return resourcebundle1;
        }
        catch(MissingResourceException missingresourceexception1) { }
        return null;
    }

    protected String getCharsetForLocale(Locale locale, String s)
    {
        return LocaleToCharsetMap.getCharset(locale);
    }

    private ResourceBundle chosenBundle;
    private Locale chosenLocale;
    private String chosenCharset;
}
