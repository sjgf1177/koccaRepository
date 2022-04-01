package com.credu.scorm;

//import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// Referenced classes of package alexit.lib.util:
//            StringUtil

public class DateUtil
{

	public DateUtil()
	{
	}

	private static String fmDate(String xFormat)
	{
		Date currentTime = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat(xFormat);
		return fmt.format(currentTime);
	}

	@Override
	public String toString()
	{
		return fmDate("yyyy/MM/dd HH:mm:ss S");
	}

	public static String toString(String xFormat)
	{
		return fmDate(xFormat);
	}

	public static String getYear()
	{
		return fmDate("yyyy");
	}

	public static String getMonth()
	{
		return fmDate("MM");
	}

	public static String getDay()
	{
		return fmDate("dd");
	}

	public static String getHour()
	{
		return fmDate("hh");
	}

	public static String getMinute()
	{
		return fmDate("mm");
	}

	public static String getSecond()
	{
		return fmDate("ss");
	}

	public static String getSysDate()
	{
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(1));
		String month = String.valueOf(cal.get(2) + 1);
		String day = String.valueOf(cal.get(5));
		if(cal.get(2) + 1 < 10) {
			month = "0" + String.valueOf(cal.get(2) + 1);
		} else {
			month = String.valueOf(cal.get(2) + 1);
		}
		if(cal.get(5) < 10) {
			day = "0" + String.valueOf(cal.get(5));
		} else {
			day = String.valueOf(cal.get(5));
		}
		String hour;
		if(cal.get(11) < 10) {
			hour = "0" + String.valueOf(cal.get(11));
		} else {
			hour = String.valueOf(cal.get(11));
		}
		String minute;
		if(cal.get(12) < 10) {
			minute = "0" + String.valueOf(cal.get(12));
		} else {
			minute = String.valueOf(cal.get(12));
		}
		String second;
		if(cal.get(13) < 10) {
			second = "0" + String.valueOf(cal.get(13));
		} else {
			second = String.valueOf(cal.get(13));
		}
		return year + month + day + hour + minute + second;
	}

	public static int getDaysBetween(String from, String to, String format)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
		Date d1 = null;
		Date d2 = null;
		try
		{
			d1 = formatter.parse(from);
			d2 = formatter.parse(to);
		}
		catch(ParseException e)
		{
			return -999;
		}
		if(!formatter.format(d1).equals(from)) {
			return -999;
		}
		if(!formatter.format(d2).equals(to)) {
			return -999;
		}
		long duration = d2.getTime() - d1.getTime();
		if(duration < 0L) {
			return -999;
		} else {
			return (int)(duration / 0x5265c00L);
		}
	}

	public static int getDayCount(int year, int month)
	{
		int day[] = {
				31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
				30, 31
		};
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			day[1] = 29;
		}
		return day[month - 1];
	}

	public static boolean isLeafYear(int year)
	{
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}

	public static boolean isDate(String date)
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setLenient(false);
		return format.parse(date, new ParsePosition(0)) != null;
	}

	public static boolean isDate(String date, String xfmt)
	{
		SimpleDateFormat format = new SimpleDateFormat(xfmt);
		format.setLenient(false);
		return format.parse(date, new ParsePosition(0)) != null;
	}

	public static int getWeekDay(int year, int month, int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal.get(7) - 1;
	}

	public static String getWeekDayNm(int year, int month, int day)
	{
		String weekNm = "";
		switch(getWeekDay(year, month, day))
		{
		case 0: // '\0'
			weekNm = "일";
			break;

		case 1: // '\001'
			weekNm = "월";
			break;

		case 2: // '\002'
			weekNm = "화";
			break;

		case 3: // '\003'
			weekNm = "수";
			break;

		case 4: // '\004'
			weekNm = "목";
			break;

		case 5: // '\005'
			weekNm = "금";
			break;

		case 6: // '\006'
			weekNm = "토";
			break;
		}
		return weekNm;
	}

	public static String addDate(int year, int month, int day, int add, String fmt, String ymd)
	{
		Calendar calendar = Calendar.getInstance();
		Date _date = null;
		calendar.set(year, month - 1, day);
		if(ymd.equals("Y")) {
			calendar.add(1, add);
		} else
			if(ymd.equals("M")) {
				calendar.add(2, add);
			} else {
				calendar.add(5, add);
			}
		_date = calendar.getTime();
		SimpleDateFormat sfmt = new SimpleDateFormat(fmt);
		return sfmt.format(_date);
	}

	public static String addDate(String date, int add, String fmt, String ymd)
	{
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, 8));
		return addDate(year, month, day, add, fmt, ymd);
	}

	public static String strToDateStr(String date, String fmt)
	{
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, 8));
		Calendar calendar = Calendar.getInstance();
		Date _date = null;
		calendar.set(year, month - 1, day);
		_date = calendar.getTime();
		SimpleDateFormat sfmt = new SimpleDateFormat(fmt);
		return sfmt.format(_date);
	}

	public static Date strToDate(String date)
	{
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int min = 0;
		int sec = 0;
		if(date.length() > 14)
		{
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(4, 6));
			day = Integer.parseInt(date.substring(6, 8));
			hour = Integer.parseInt(date.substring(8, 10));
			min = Integer.parseInt(date.substring(10, 12));
			sec = Integer.parseInt(date.substring(12, 14));
		} else
			if(date.length() == 8)
			{
				year = Integer.parseInt(date.substring(0, 4));
				month = Integer.parseInt(date.substring(4, 6));
				day = Integer.parseInt(date.substring(6, 8));
			}
		Calendar calendar = Calendar.getInstance();
		Date _date = null;
		calendar.set(year, month - 1, day, hour, min, sec);
		_date = calendar.getTime();
		return _date;
	}

	public static String sqlDateToStr(java.sql.Date _date)
	{
		return _date.toString();
	}

	public static boolean isSunOffDay(String month, String day)
	{
		String month_day = month + day;
		return "0101,0301,0405,0505,0606,0717,0815,1003,1125".indexOf(month_day) >= 0;
	}

	public static boolean isSunOffDay(int month, int day)
	{
		//        StringUtil SC = new StringUtil();
		String sMonth = StringUtil.getZeroBaseString(month, 2);
		String sDay = StringUtil.getZeroBaseString(day, 2);
		return isSunOffDay(sMonth, sDay);
	}

	public static boolean isOffDay(int month, int day)
	{
		//        StringUtil SC = new StringUtil();
		String sMonth = StringUtil.getZeroBaseString(month, 2);
		String sDay = StringUtil.getZeroBaseString(day, 2);
		return isSunOffDay(sMonth, sDay);
	}

	public static boolean isOffDay(String month, String day)
	{
		if(month.length() == 1) {
			month = "0" + month;
		}
		if(day.length() == 1) {
			day = "0" + day;
		}
		return isSunOffDay(month, day);
	}

	public static int dayDiff(Date from, Date to)
	{
		long msPerDay = 0x5265c00L;
		long diff = from.getTime() / msPerDay - to.getTime() / msPerDay;
		Long convertLong = new Long(diff);
		return convertLong.intValue();
	}

	public static int dateDiff(String from, String to, String ymd)
	{
		if(ymd.equals("Y") && from.length() == 8 && to.length() == 8)
		{
			int f_year = Integer.parseInt(from.substring(0, 4));
			int t_year = Integer.parseInt(to.substring(0, 4));
			int f_md = Integer.parseInt(from.substring(4));
			int t_md = Integer.parseInt(to.substring(4));
			int diff = t_year - f_year;
			if(f_md > t_md) {
				diff--;
			}
			return diff;
		} else
		{
			return -1;
		}
	}

	public static final java.sql.Date toSQLDate(Date inDate)
	{
		return new java.sql.Date(inDate.getTime());
	}

	public static String defFmtDate(String inDate)
	{
		if(inDate.length() >= 8) {
			return inDate.substring(0, 4) + "-" + inDate.substring(4, 6) + "-" + inDate.substring(6, 8);
		}
		if(inDate.length() >= 6) {
			return inDate.substring(0, 4) + "-" + inDate.substring(4, 6);
		}
		if(inDate.length() >= 4) {
			return inDate.substring(0, 4);
		} else {
			return inDate;
		}
	}

	public static String defFmtDateTime(String inDate)
	{
		if(inDate.length() >= 14) {
			return inDate.substring(0, 4) + "-" + inDate.substring(4, 6) + "-" + inDate.substring(6, 8) + " " + inDate.substring(8, 10) + ":" + inDate.substring(10, 12) + ":" + inDate.substring(12, 14);
		} else {
			return inDate;
		}
	}

	public static String defFmtDate2(String inDate)
	{
		if(inDate.length() >= 8) {
			return inDate.substring(0, 4) + "년 " + inDate.substring(4, 6) + "월 " + inDate.substring(6, 8) + "일";
		}
		if(inDate.length() == 6) {
			return inDate.substring(0, 4) + "년 " + inDate.substring(4, 6) + "월";
		}
		if(inDate.length() == 4) {
			return inDate.substring(0, 4) + "년";
		} else {
			return inDate;
		}
	}
	/*
    public static String defFmtDateTime2(String inDate)
    {
        if(inDate.length() >= 14)
            return inDate.substring(0, 4) + "년 " + inDate.substring(4, 6) + "월 " + inDate.substring(6, 8) + "일 " + inDate.substring(8, 10) + "시 " + inDate.substring(10, 12) + "분 " + inDate.substring(12, 14) + "초";
        else
            return inDate;
    }
	 */
	//    private static final String sunOffDays = "0101,0301,0405,0505,0606,0717,0815,1003,1125";

	public static String defFmtDateTime2(String inDate)
	{
		if(inDate.length() >= 14) {
			return inDate.substring(0, 4) + "년 " + inDate.substring(4, 6) + "월 " + inDate.substring(6, 8) + "일 " + inDate.substring(8, 10) + "시 " + inDate.substring(10, 12) + "분 " + inDate.substring(12, 14) + "초";
		} else {
			return inDate;
		}
	}



}
