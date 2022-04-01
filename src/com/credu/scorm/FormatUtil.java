package com.credu.scorm;


public class FormatUtil
{

    public FormatUtil()
    {
    }

    public static String bizRegiNoFormat(String bizRegiNo)
    {
        String b = bizRegiNo;
        if(b.length() != 10)
            return b;
        else
            return b.substring(0, 3) + "-" + b.substring(3, 5) + "-" + b.substring(5, 10);
    }

    public static String NVL(String source, String target)
    {
        return source != null ? source : target;
    }

    public static String isnull2(String source, String target)
    {
        return source != null && !source.equals("") ? source : target;
    }

    public static String isnull3(String source, String target)
    {
        return source != null && !source.trim().equals("") ? source : target;
    }

    public static String isnullSubstr(String source, int from, int to, String target)
    {
        try
        {
            return source.substring(from, to);
        }
        catch(Exception e)
        {
            return target;
        }
    }

    public static String makeQuot(String source)
    {
        return "'" + isnull2(source, "") + "'";
    }

    public static String juminNo(String juminno)
    {
        int juminno_len = juminno.length();
        String retValue = "";
        if(juminno_len == 13)
            retValue = juminno.substring(0, 6) + "-" + juminno.substring(6, 13);
        else
            retValue = juminno;
        return retValue;
    }

    public static String[] dateArr(String datestr)
    {
        String tmp[] = {
            "", "", ""
        };
        if(datestr != null && datestr.length() == 8)
        {
            tmp[0] = datestr.substring(0, 4);
            tmp[1] = datestr.substring(4, 6);
            tmp[2] = datestr.substring(6, 8);
        }
        return tmp;
    }

    public static String[] zipArr(String zipstr)
    {
        String tmp[] = {
            "", ""
        };
        if(zipstr != null && zipstr.length() == 6)
        {
            tmp[0] = zipstr.substring(0, 3);
            tmp[1] = zipstr.substring(3, 6);
        }
        return tmp;
    }

    public static String replace(String sourceStr, String source, String target)
    {
        String result = sourceStr;
        int idx = sourceStr.indexOf(source);
        if(idx > -1)
        {
            result = sourceStr.substring(0, idx) + target + sourceStr.substring(idx + source.length(), sourceStr.length());
            result = replace(result, source, target);
        }
        return result;
    }

    public static Object pcsFormat(String pNumber)
    {
        String pcsNumber = replace(pNumber.trim(), "-", "");
        String retValue = "";
        int len = pcsNumber.length();
        if(len >= 10)
            retValue = pcsNumber.substring(0, 3) + "-" + pcsNumber.substring(3, len - 4) + "-" + pcsNumber.substring(len - 4);
        else
            retValue = pcsNumber;
        return retValue;
    }

    public static String insertComma(String inputStr)
    {
        String tmpStr = inputStr;
        String underComma = "";
        if(inputStr.indexOf(".") >= 0)
        {
            tmpStr = inputStr.substring(0, tmpStr.indexOf("."));
            underComma = "." + inputStr.substring(inputStr.indexOf(".") + 1);
        }
        int len = tmpStr.length();
        String resultValue = "";
        String sign = "";
        if(inputStr.startsWith("-"))
        {
            sign = "-";
            len--;
            tmpStr = tmpStr.substring(1);
        }
        for(int i = 0; i < len; i++)
        {
            if(i > 0 && i % 3 == 0)
                resultValue = "," + resultValue;
            resultValue = tmpStr.charAt(len - 1 - i) + resultValue;
        }

        return sign + resultValue + underComma;
    }

    public static String insertComma2(String inputStr)
    {
        String resultValue = insertComma(inputStr);
        resultValue = resultValue.equals("0") ? "" : resultValue;
        return resultValue;
    }

    public static String fillZero(String source, int length)
    {
        if(source == null)
            return "";
        if(source.length() >= length)
            return source;
        for(; source.length() < length; source = "0" + source);
        return source;
    }

    public static String zeroToEmpty(String value)
    {
        return value.equals("0") ? "" : value;
    }

    public static String emptyToZero(String value)
    {
        return value.equals("") ? "0" : value;
    }

    public static String iifEQ(String src1, String src2, String target1, String target2)
    {
        try
        {
            return src1.equals(src2) ? target1 : target2;
        }
        catch(Exception e)
        {
            return target2;
        }
    }
}
