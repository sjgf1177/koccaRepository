package com.credu.library;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * <p>제목: 날짜관련 라이브러리</p>
 * <p>설명: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
 *@date 2003. 12
 *@version 1.0
 */
public class FormatDate {

	public static String alltrim(String s) {
		if (s == null) return null;

		String temp,result = "";

		for (int i=0; i<s.length(); i++) {
			temp = s.substring(i,i+1);
			if (temp.equals(" ")) {
				continue;
			}
			else {
				result += temp;
			}
		}
		return result;
	}


	/**
	 *월 차이의 달수 구함. 예) datediff("20010101", "20000501");  달의 차
	 *@param firstdate
	 *@param lastdate
	 *@return 더하거나 뺀 월을 리턴
	 */
	public static int datediff(String firstdate, String lastdate) throws Exception {
		int returnValue = 0;
		//		long temp = 0;
		int year=0,month=0,year1=0,month1=0;
		//		,day=0,day1=0;
		int year2 = 0, month2 = 0;

		if ( firstdate == null || firstdate.equals("") ) return returnValue;
		if ( lastdate == null || lastdate.equals("") ) return returnValue;

		try {
			year  = Integer.parseInt(firstdate.substring(0,4));
			month = Integer.parseInt(firstdate.substring(4,6));
			//			day   = Integer.parseInt(firstdate.substring(6,8));

			year1  = Integer.parseInt(lastdate.substring(0,4));
			month1 = Integer.parseInt(lastdate.substring(4,6));
			//			day1   = Integer.parseInt(lastdate.substring(6,8));

			year2  = (year - year1) * 12;
			month2 = month - month1;
			returnValue = year2 + month2 + 1;
		}
		catch (Exception ex) {
			throw new Exception("getFormatDate.datediff(\""+returnValue+"\")\r\n"+ex.getMessage());
		}
		return returnValue;
	}

	/**
	 *일짜 차이의 일수, 월 차이의 달수 구함. 예) datediff("d", "20000101", "20010501");  일의 차 - 작은 날이 앞에, datediff("20010101", "20000501");  달의 차-작은 날이 뒤에
	 *@param gubn  월, 일 중 하나를 세팅한다.(월 = "month", 일 = "date")
	 *@param firstdate
	 *@param lastdate
	 *@return 더하거나 뺀 월, 일을 리턴
	 */
	public static int datediff(String gubn, String firstdate, String lastdate) throws Exception {
		int returnValue = 0;
		long temp = 0;
		int year=0,month=0,day=0,year1=0,month1=0,day1=0;
		int year2 = 0, month2 = 0;

		if ( firstdate == null || firstdate.equals("") ) return returnValue;
		if ( lastdate == null || lastdate.equals("") ) return returnValue;

		try {
			year  = Integer.parseInt(firstdate.substring(0,4));
			month = Integer.parseInt(firstdate.substring(4,6));
			day   = Integer.parseInt(firstdate.substring(6,8));

			year1  = Integer.parseInt(lastdate.substring(0,4));
			month1 = Integer.parseInt(lastdate.substring(4,6));
			day1   = Integer.parseInt(lastdate.substring(6,8));

			if (gubn.equals("date")) {
				TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
				Calendar calendar=Calendar.getInstance(tz);

				calendar.set((year-1900),(month-1),day);

				Calendar cal2=Calendar.getInstance(tz);
				cal2.set((year1-1900),(month1-1),day1);

				java.util.Date temp1 = calendar.getTime();
				java.util.Date temp2 = cal2.getTime();

				temp = temp2.getTime() - temp1.getTime();

				if ( ( temp % 10 ) < 5 )
					temp = temp - ( temp % 10 );
				else
					temp = temp + ( 10 - ( temp % 10 ) );

				returnValue = (int)( temp / ( 1000 * 60 * 60 * 24 ) );

				if ( returnValue == 0 ) returnValue = 1;
			}
			else {
				year2  = (year - year1) * 12;
				month2 = month - month1;
				returnValue = year2 + month2;
			}
		}
		catch (Exception ex) {
			throw new Exception("getFormatDate.datediff(\""+returnValue+"\")\r\n"+ex.getMessage());
		}
		return returnValue;
	}

	/**
	 *날짜를 여러 타입으로 리턴한다.
	 *예) getDate("yyyyMMdd");
	 *      getDate("yyyyMMddHHmmss");
	 *      getDate("yyyyMMddHHmmssSSS");
	 *      getDate("yyyy/MM/dd HH:mm:ss");
	 *      getDate("yyyy/MM/dd");
	 *      getDate("HHmm");
	 *@param type 날짜타입
	 *@return result  변경된 날짜타입을 반환함
	 */
	public static String getDate(String type) throws Exception {
		if (type == null) return null;

		String s="";
		try {
			s = new SimpleDateFormat(type).format(new Date());
		}
		catch (Exception ex) {
			throw new Exception("getFormatDate.getDate(\""+type+"\")\r\n"+ex.getMessage());
		}
		return s;
	}

	/**
	 *String 형식의 YYYYMMDDHHMISS 를 Date 객체로 리턴한다.
	 *@param v_datestr  YYYYMMDDHHMISS
	 *@return  Date 객체 리턴
	 */
	public static Date getDate2(String v_datestr) {
		Date d = null;

		int v_year  = 0;
		int v_month = 1;
		int v_date  = 0;
		int v_hrs   = 0;
		int v_min   = 0;
		int v_sec   = 0;

		if(v_datestr.length() >= 4) {
			v_year  = Integer.parseInt(v_datestr.substring(0, 4));
		}

		if(v_datestr.length() >= 6) {
			v_month = Integer.parseInt(v_datestr.substring(4, 6));
		}

		if(v_datestr.length() >= 8) {
			v_date  = Integer.parseInt(v_datestr.substring(6, 8));
		}

		if(v_datestr.length() >= 10) {
			v_hrs   = Integer.parseInt(v_datestr.substring(8, 10));
		}

		if(v_datestr.length() >= 12) {
			v_min   = Integer.parseInt(v_datestr.substring(10,12));
		}

		if(v_datestr.length() >= 14) {
			v_sec   = Integer.parseInt(v_datestr.substring(12,14));
		}

		d = (new GregorianCalendar(v_year, v_month-1, v_date, v_hrs, v_min, v_sec)).getTime();

		return d;
	}

	public static String getDateAdd(String type, String gubn, int rec) throws Exception {
		String result = "";

		if (type == null) return null;

		try {
			Calendar calendar=Calendar.getInstance();
			if (gubn == "month") {
				calendar.add (Calendar.MONTH, rec);
			}
			if (gubn == "date") {
				calendar.add (Calendar.DATE,  rec);
			}
			if (gubn == "hour") {
				calendar.add (Calendar.HOUR_OF_DAY,  rec);
			}
			if (gubn == "minute") {
				calendar.add (Calendar.MINUTE,   rec);
			}
			if (gubn == "second") {
				calendar.add (Calendar.SECOND,   rec);
			}
			result = (new SimpleDateFormat(type)).format(calendar.getTime());
		}
		catch (Exception ex) {
			throw new Exception("FormatDate.getDateAdd(\""+type+"\")\r\n"+ex.getMessage());
		}
		return result;
	}

	public static String getDateAdd(String date, String type, String gubn, int rec) throws Exception {
		String result = "";
		int year=0,month=0,day=0,length=date.length();

		if (date == null) return null;
		if (type == null) return null;
		if (length != 8) return null;

		try {
			year = Integer.parseInt(date.substring(0,4));
			month= Integer.parseInt(date.substring(4,6))-1;
			day  = Integer.parseInt(date.substring(6,8));

			Calendar calendar=Calendar.getInstance();
			calendar.set(year,month,day);
			if (gubn == "year")     calendar.add (Calendar.YEAR, rec);
			if (gubn == "month")    calendar.add (Calendar.MONTH, rec);
			if (gubn == "date")     calendar.add (Calendar.DATE,  rec);
			if (gubn == "hour")     calendar.add (Calendar.HOUR_OF_DAY,  rec);
			if (gubn == "minute") 	calendar.add (Calendar.MINUTE,   rec);
			result = (new SimpleDateFormat(type)).format(calendar.getTime());
		}
		catch (Exception ex) {
			throw new Exception("FormatDate.getDateAdd(\""+type+"\")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 *년,월,일,시,분등과 관련된 HTML <option> 을 출력한다.
	 *@param start  시작시간
	 *@param end  종료시간
	 *@return getDateOptions(start,end,-1);
	 */
	public static String getDateOptions(int start, int end) {
		return getDateOptions(start,end,-1);
	}

	/**
	 *년,월,일,시,분등과 관련된 HTML <option> 을 출력한다.
	 *@param start  시작시간
	 *@param end  종료시간
	 *@param nDefault  default 값이 선택됨
	 *@return HTML <option> 을 출력
	 */
	public static String getDateOptions(int start, int end, int nDefault) {
		String result = "";

		for (int i=start ; i <= end ; i ++) {
			if (i < 100) {
				String temp = "";
				temp = String.valueOf(i + 100);
				temp = temp.substring(1);

				if (i == nDefault) {
					result += "<option value='"+temp+"' selected>"+temp;
				}
				else {
					result += "<option value='"+temp+"'>"+temp;
				}
			}
			else {
				if (i == nDefault) {
					result += "<option value='"+i+"' selected>"+i;
				}
				else {
					result += "<option value='"+i+"'>"+i;
				}
			}
		}
		return result;
	}

	/**
	 *년,월,일,시,분등과 관련된 HTML <option> 을 출력한다.
	 *@param start  시작시간
	 *@param end  종료시간
	 *@param nDefault  default 값이 선택됨
	 *@return HTML <option> 을 출력
	 */
	public static String getDateOptions(int start, int end, String nDefault) {
		return getDateOptions(start, end, Integer.parseInt(nDefault));
	}

	/**
	 *해당 날짜에 년, 월, 일, 시, 분을 더하거나 뺀 날짜를 리턴한다.
	 *@param date  YYYYMMDDHHMI
	 *@param type  type 패턴에 따라 날짜가 형식이 변환된다.
	 *@param gubn  년, 월, 일, 시, 분 중 하나를 세팅한다.
	 *@param rec  빼거나 더할 숫자를 넣는다.
	 *@return 더하거나 뺀 날짜를 리턴
	 */
	public static String getDayAdd(String date, String type, String gubn, int rec) throws Exception {
		String result = "";
		int year=0,month=0,day=0,hour=0,min=0;
		//		int length=date.length();

		if (date == null) return null;
		if (type == null) return null;
		//if (length != 12) return null;

		try {
			year = Integer.parseInt(date.substring(0,4));
			month= Integer.parseInt(date.substring(4,6))-1;
			day  = Integer.parseInt(date.substring(6,8));
			hour = Integer.parseInt(date.substring(8,10));
			min  = Integer.parseInt(date.substring(10,12));

			Calendar calendar=Calendar.getInstance();
			calendar.set(year,month,day,hour,min);
			if (gubn == "month")    calendar.add (Calendar.MONTH, rec);
			if (gubn == "date")     calendar.add (Calendar.DATE,  rec);
			if (gubn == "hour")     calendar.add (Calendar.HOUR_OF_DAY,  rec);
			if (gubn == "minute") 	calendar.add (Calendar.MINUTE,   rec);
			result = (new SimpleDateFormat(type)).format(calendar.getTime());
		}
		catch (Exception ex) {
			throw new Exception("FormatDate.getDayAdd(\""+type+"\")\r\n"+ex.getMessage());
		}

		return result;
	}

	/**
	 *오늘의 요일을 계산한다.
	 *@return 오늘의 요일을 반환함
	 */
	public static String getDayOfWeek() throws Exception {
		return getDayOfWeek(getDate("yyyyMMdd"));
	}

	/**
	 *해당날짜의 요일을 계산한다. (년월일(6자리)을 지정하는데 지정되지 않으면 default 값을 사용한다. 2000.2)
	 *예) getDayOfWeek("2000")     -> 토 (2000/1/1)
	 *      getDayOfWeek("200002")   -> 화 (2000/2/1)
	 *      getDayOfWeek("20000225") -> 금 (2000/2/25)
	 *@param type 날짜타입
	 *@return result  변경된 날짜타입을 반환함
	 */
	public static String getDayOfWeek(String date) {
		if (date==null) return null;

		int yyyy=0,MM=1,dd=1,day_of_week; // default

		String days[]={"일","월","화","수","목","금","토"};

		try {
			yyyy=Integer.parseInt(date.substring(0,4));
			MM=Integer.parseInt(date.substring(4,6));
			dd=Integer.parseInt(date.substring(6,8));
		}
		catch (Exception ex) {
			// do nothing
		}

		Calendar calendar=Calendar.getInstance();
		calendar.set(yyyy,MM-1,dd);
		day_of_week=calendar.get(Calendar.DAY_OF_WEEK); //1(일),2(월),3(화),4(수),5(목),6(금),7(토)

		return days[day_of_week-1];
	}
	/**
	 *날짜(+시간)을 스트링으로 받어서 type 형태로 리턴한다.
	 *예) getFormatDate("19991201","yyyy/MM/dd") -> "1999/12/01"
	 *      getFormatDate("19991201","yyyy-MM-dd") -> "1999-12-01"
	 *     getFormatDate("1999120112","yyyy-MM-dd HH") -> "1999-12-01 12"
	 *      getFormatDate("199912011200","yyyy-MM-dd HH:mm ss") -> "1999-12-01 12:00 00"
	 *      getFormatDate("19991231115959","yyyy-MM-dd-HH-mm-ss") -> "1999-12-31-11-59-59"
	 *@param date 날짜
	 *@param type 날짜타입
	 *@return result  변경된 날짜타입을 반환함
	 */
	public static String getFormatDate(String date, String type) throws Exception {
		if (date == null || type == null) return null;

		String result="";
		int year=0,month=0,day=0,hour=0,min=0,sec=0,length=date.length();

		try {
			if (length >= 8) {  // 날짜
				if (date.indexOf('-') != -1 || date.indexOf('.') != -1) {
					year = Integer.parseInt(date.substring(0,4));
					month= Integer.parseInt(date.substring(5,7)); // month 는 Calendar 에서 0 base 으로 작동하므로 1 을 빼준다.
					day  = Integer.parseInt(date.substring(8,10));

					if (length == 13) {     // 날짜+시간
						hour = Integer.parseInt(date.substring(11,13));
					}
					if (length == 19) {     // 날짜+시간
						hour = Integer.parseInt(date.substring(14,16));
						min  = Integer.parseInt(date.substring(17,19));
					}
					if (length == 22) {     // 날짜+시간
						hour = Integer.parseInt(date.substring(8,10));
						min  = Integer.parseInt(date.substring(10,12));
						sec  = Integer.parseInt(date.substring(12,14));
					}
				}
				else {
					year = Integer.parseInt(date.substring(0,4));
					month= Integer.parseInt(date.substring(4,6)); // month 는 Calendar 에서 0 base 으로 작동하므로 1 을 빼준다.
					day  = Integer.parseInt(date.substring(6,8));

					if (length == 10) {     // 날짜+시간
						hour = Integer.parseInt(date.substring(8,10));
					}
					if (length == 12) {     // 날짜+시간
						hour = Integer.parseInt(date.substring(8,10));
						min  = Integer.parseInt(date.substring(10,12));
					}
					if (length == 14) {     // 날짜+시간
						hour = Integer.parseInt(date.substring(8,10));
						min  = Integer.parseInt(date.substring(10,12));
						sec  = Integer.parseInt(date.substring(12,14));
					}
				}
				Calendar calendar=Calendar.getInstance();
				calendar.set(year,month-1,day,hour,min,sec);
				result = (new SimpleDateFormat(type)).format(calendar.getTime());
			}
		}
		catch (Exception ex) {
			throw new Exception("getFormatDate.getFormatDate(\""+date+"\",\""+type+"\")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 *시간을 스트링으로 받어서 type 형태로 리턴한다.
	 *예) getFormatTime("1200","HH:mm") -> "12:00"
	 *      getFormatTime("1200","HH:mm:ss") -> "12:00:00"
	 *      getFormatTime("120003","HH:mm") -> "12:00"
	 *     getFormatTime("120003","HH:mm ss") -> "12:00 03"
	 *@param time 시간
	 *@param type 시간타입
	 *@return result  변경된 시간타입을 반환함
	 */
	public static String getFormatTime(String time, String type) throws Exception {
		if (time == null || type == null) return null;

		String result="";
		int hour=0,min=0,sec=0;//,length=time.length();

		try {
			try {
				hour = Integer.parseInt(time.substring(0,2));
				min  = Integer.parseInt(time.substring(2,4));
				sec = Integer.parseInt(time.substring(4,6));
			}
			catch (IndexOutOfBoundsException ex) {
				// ignore
			}
			Calendar calendar=Calendar.getInstance();
			calendar.set(0,0,0,hour,min,sec);
			result = (new SimpleDateFormat(type)).format(calendar.getTime());
		}
		catch (Exception ex) {
			throw new Exception("getFormatDate.getFormatTime(\""+time+",\""+type+"\")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 *두 시간의 차이를 밀리초로 계산한다..  처음 파라메터가 작은 날짜인데 만약 더 큰날짜를 처음으로 주면 음수를리턴.
	 *@return 두시간의 차를 밀리초로 반환함
	 */
	public static int getMilliSecDifference(String s_start,String s_end) throws Exception {
		long l_gap = getTimeDifference(s_start, s_end);

		return (int)l_gap;
	}

	/**
	 *두 시간의 차이를 분으로 계산한다.  처음 파라메터가 작은 날짜인데 만약 더 큰날짜를 처음으로 주면 음수를리턴.
	 *예) getMinDifference("20000302","20000303") --> 3600
	 *      getMinDifference("2000030210","2000030211") --> 60
	 *      getMinDifference("200003021020","200003021021") --> 1
	 *      getMinDifference("20000302102000","20000302102130") --> 1
	 *@return 두시간의 차를 분으로 반환함
	 */
	public static int getMinDifference(String s_start,String s_end) throws Exception {
		long l_gap = getTimeDifference(s_start, s_end);

		return (int)(l_gap/(1000*60));
	}

	/**
		현재 System시간으로 부터 정해진 날짜를 구한다.

		@param tab 상대적으로 구할 날짜 (-3 : 3일전, 100 : 100일후)
		@return 날짜 8자리
	 */
	@SuppressWarnings("deprecation")
	public static String getRelativeDate(int tab) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, tab);
		Date targetDate = today.getTime();
		String sDate = (targetDate.getYear() + 1900) + "" + (targetDate.getMonth() + 1) + "";

		if(sDate.length() == 5) sDate = sDate.substring(0, 4) + "0" + sDate.substring(4);
		sDate += targetDate.getDate();
		if (sDate.length() == 7){
			sDate = sDate.substring(0, 6) + "0" + sDate.substring(6);
		}
		return sDate;
	}

	/**
	 *0 = Sunday, 1 = Monday, 2 =  Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday)
	 *특정일(yyyyMMdd) 에서 주어진 일자만큼 더한 날짜를 계산한다.
	 *@param date 특정일(yyyyMMdd)
	 *@param rday 주어진 일자
	 *@return result  더한 날짜를 계산하여 반환함
	 */
	public static String getRelativeDate(String date, int rday) throws Exception {
		if (date == null) return null;

		if (date.length() < 8 ) return ""; // 최소 8 자리

		String time = "";

		try {
			TimeZone kst = TimeZone.getTimeZone("JST");
			TimeZone.setDefault(kst);

			Calendar calendar = Calendar.getInstance(kst); // 현재

			int yyyy = Integer.parseInt(date.substring(0,4));
			int mm = Integer.parseInt(date.substring(4,6));
			int dd = Integer.parseInt(date.substring(6,8));

			calendar.set(yyyy,mm-1,dd);   // 카렌더를 주어진 date 로 세팅하고
			calendar.add (Calendar.DATE, rday); // 그 날자에서 주어진 rday 만큼 더한다.

			time = new SimpleDateFormat ("yyyyMMdd").format(calendar.getTime());
		}
		catch (Exception ex) {
			throw new Exception("getFormatDate.getRelativeDate(\""+date+"\","+rday+")\r\n"+ex.getMessage());
		}
		return time;
	}

	/**
	 *두 시간의 차이를 초로 계산한다..  처음 파라메터가 작은 날짜인데 만약 더 큰날짜를 처음으로 주면 음수를리턴.
	 *@return 두시간의 차를 초로 반환함
	 */
	public static int getSecDifference(String s_start,String s_end) throws Exception {
		long l_gap = getTimeDifference(s_start, s_end);

		return (int)(l_gap/(1000));
	}

	public static long getTimeDifference(String s_start,String s_end) throws Exception {
		long l_start,l_end,l_gap;

		int i_start_year=0,i_start_month=1,i_start_day=1,i_start_hour=0,i_start_min=0,i_start_sec=0,i_start_msec=0;
		int i_end_year=0,i_end_month=1,i_end_day=1,i_end_hour=0,i_end_min=0,i_end_sec=0,i_end_msec=0;

		try {
			try {
				i_start_year = Integer.parseInt(s_start.substring(0,4));
				i_start_month= Integer.parseInt(s_start.substring(4,6)); // month 는 Calendar에서 0 base 으로 작동하므로 1 을 빼준다.
				i_start_day  = Integer.parseInt(s_start.substring(6,8));
				i_start_hour = Integer.parseInt(s_start.substring(8,10));
				i_start_min  = Integer.parseInt(s_start.substring(10,12));
				i_start_sec  = Integer.parseInt(s_start.substring(12,14));
				i_start_msec  = Integer.parseInt(s_start.substring(14,17));
			}
			catch (IndexOutOfBoundsException ex) {
				// ignore
			}

			try {
				i_end_year = Integer.parseInt(s_end.substring(0,4));
				i_end_month= Integer.parseInt(s_end.substring(4,6)); // month 는 Calendar 에서0 base 으로 작동하므로 1 을 빼준다.
				i_end_day  = Integer.parseInt(s_end.substring(6,8));
				i_end_hour = Integer.parseInt(s_end.substring(8,10));
				i_end_min  = Integer.parseInt(s_end.substring(10,12));
				i_end_sec  = Integer.parseInt(s_end.substring(12,14));
				i_end_msec  = Integer.parseInt(s_end.substring(14,17));
			}
			catch (IndexOutOfBoundsException ex) {
				// ignore
			}
		}
		catch (Exception ex) {
			throw new Exception("getFormatDate.getTimeDifference("+s_start+","+s_end+")\r\n"+ex.getMessage());
		}

		Calendar calendar=Calendar.getInstance();

		calendar.set(i_start_year, i_start_month-1, i_start_day, i_start_hour, i_start_min, i_start_sec);
		calendar.set(Calendar.MILLISECOND, i_start_msec);

		l_start=calendar.getTime().getTime();

		calendar.set(i_end_year, i_end_month-1, i_end_day, i_end_hour, i_end_min, i_end_sec);
		calendar.set(Calendar.MILLISECOND, i_end_msec);
		l_end=calendar.getTime().getTime();

		l_gap=l_end-l_start;

		return l_gap;
	}

	/**
	 *주어진 초동안 아무일도 안한다.
	 *@param milisecond  1/1000 초
	 */
	public static void sleep(int milisecond) throws Exception {
		if (milisecond > 0) {
			long endTime = System.currentTimeMillis() + milisecond;

			while (System.currentTimeMillis() < endTime) ;    // 주어진 초동안 아무일도 안한다.
		}
	}

	/**
	 *해당 날짜의 요일을 출력한다.
	 *@param date  YYYYMMDD
	 *@return 요일 리턴
	 */
	public static int Weekday(String date) {
		if (date==null) return -1;

		int yyyy=0,MM=1,dd=1,day_of_week; // default

		try {
			yyyy=Integer.parseInt(date.substring(0,4));
			MM=Integer.parseInt(date.substring(4,6));
			dd=Integer.parseInt(date.substring(6,8));
		}
		catch (Exception ex) {
			// do nothing
		}

		Calendar calendar=Calendar.getInstance();
		calendar.set(yyyy,MM-1,dd);
		day_of_week=calendar.get(Calendar.DAY_OF_WEEK);

		return day_of_week;
	}
}