package com.credu.scorm;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

public class CalendarBean
{

    public CalendarBean()
    {
        calendar = Calendar.getInstance();
        calendar.set(5, 1);
    }

    private int getMonthLastDay(int year, int month)
    {
        switch(month)
        {
        case 2: // '\002'
            return (year % 4 != 0 || year % 100 == 0) && year % 400 != 0 ? 28 : 29;

        case 4: // '\004'
        case 6: // '\006'
        case 9: // '\t'
        case 11: // '\013'
            return 30;

        case 3: // '\003'
        case 5: // '\005'
        case 7: // '\007'
        case 8: // '\b'
        case 10: // '\n'
        default:
            return 31;
        }
    }

    public int getLastDay()
    {
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        return getMonthLastDay(year, month);
    }

    public int getLastMonthLastDay()
    {
        int year = calendar.get(1);
        int month = calendar.get(2);
        if(month == 0)
        {
            year--;
            month = 12;
        }
        return getMonthLastDay(year, month);
    }

    public void setYear(int value)
    {
        calendar.set(1, value);
    }

    public void setMonth(int value)
    {
        calendar.set(2, value - 1);
    }

    public int getYear()
    {
        return calendar.get(1);
    }

    public int getMonth()
    {
        return calendar.get(2) + 1;
    }

    public int getFirstOfMonth()
    {
        return calendar.get(7);
    }

    public boolean DateCheck(String dt)
    {
        boolean value = true;
        try
        {
            DateFormat df = java.text.DateFormat.getDateInstance(3);
            df.setLenient(false);
            Date dt2 = df.parse(dt);
        }
        catch(ParseException e)
        {
            value = false;
        }
        catch(IllegalArgumentException e)
        {
            value = false;
        }
        return value;
    }

    public String DateFormat(String d_fmt, Date d)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(d_fmt);
        String value = sdf.format(d);
        return value;
    }

    Calendar calendar;
}
