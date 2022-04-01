package com.credu.scorm;


public class NumericUtil
{

    public NumericUtil()
    {
    }

    public static boolean isEven(int no)
    {
        return (no / 2) * 2 == no;
    }

    public static boolean isOdd(int no)
    {
        return (no / 2) * 2 != no;
    }

    public static double parseDouble(String str)
    {
        double rtn = 0.0D;
        try
        {
            rtn = Double.parseDouble(str);
        }
        catch(Exception ignore)
        {
            rtn = 0.0D;
        }
        finally
        {
            return rtn;
        }
    }

    public static float parseFloat(String str)
    {
        float rtn = 0.0F;
        try
        {
            rtn = Float.parseFloat(str);
        }
        catch(Exception ignore)
        {
            rtn = 0.0F;
        }
        finally
        {
            return rtn;
        }
    }

    public static int parseInt(String str)
    {
        int rtn = 0;
        try
        {
            rtn = Integer.parseInt(str);
        }
        catch(Exception ignore)
        {
            rtn = 0;
        }
        finally
        {
            return rtn;
        }
    }

    public static long parseLong(String str)
    {
        long rtn = 0L;
        try
        {
            rtn = Long.parseLong(str);
        }
        catch(Exception ignore)
        {
            rtn = 0L;
        }
        finally
        {
            return rtn;
        }
    }

    public static boolean CheckNumber(String Value)
    {
        if(Value == null || Value.equals(""))
            return false;
        int ilen = Value.length();
        for(int i = 0; i < ilen; i++)
            if(!Character.isDigit(Value.charAt(i)))
                return false;

        return true;
    }

    public static float decimalPlace(float fValue, int cutPoint)
        throws Exception
    {
        String arg = Float.toString(fValue);
        try
        {
            float rtn;
            if(arg.indexOf("E") > 0)
            {
                rtn = Float.parseFloat("0.0");
            } else
            {
                arg = arg.substring(0, arg.indexOf(".") + cutPoint + 1);
                rtn = Float.parseFloat(arg);
            }
            return rtn;
        }
        catch(Exception e)
        {
            return fValue;
        }
    }
}
