package com.credu.library;

import java.text.DecimalFormat;

/**
* 프로젝트명 : imba_java
* 패키지명 : com.credu.library
* 파일명 : NumberManager.java
* 작성날짜 : 2011. 3. 22.
* 처리업무 : 숫자관련
* 수정내용 : 
 
* Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.    
*/

public class NumberManager 
{
	/**
	 * long형 포맷
	 * @param number
	 * @return
	 */
	public static String getIntFormat(int number)
	{
		return new DecimalFormat("#,##0").format(number);
	}
	
	/**
	 * long형 포맷
	 * @param number
	 * @return
	 */
	public static String getIntFormat(String number)
	{
		try
		{
			if(number == null || number.equals("")) return "0";
			return new DecimalFormat("#,##0").format(Integer.parseInt( number));
		}
		catch(NumberFormatException e)
		{
			return "0";
		}
	}
	
	/**
	 * long형 포맷
	 * @param number
	 * @return
	 */
	public static String getlongFormat(long number)
	{
		return new DecimalFormat("#,##0").format(number);
	}
	
	/**
	 * long형 포맷
	 * @param number
	 * @return
	 */
	public static String getlongFormat(String number)
	{
		try
		{
			if(number == null || number.equals("")) return "0";
			return new DecimalFormat("#,##0").format(Long.parseLong( number));
		}
		catch(NumberFormatException e)
		{
			return "0";
		}
	}
	
	/**
	 * double형 포맷
	 * @param number
	 * @param nLen
	 * @return
	 */
	public static String getDoubleFormat(double number, int nLen)
	{
		String strFormat = "#,##0";
		if(nLen > 0)
			strFormat += ".";
		for(int i = 0; i < nLen; i++)
		{
			strFormat += "0";
		}
		return new DecimalFormat(strFormat).format(number);
	}
	
	/**
	 * String int로 변환
	 * @param number
	 * @return
	 */
	public static int getInt(String number)
	{
		try
		{
			if(number == null || number.equals("")) return 0;
			else return Integer.parseInt(number);
			
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	}
	
	/**
	 * String long로 변환
	 * @param number
	 * @return
	 */
	public static long getLong(String number)
	{
		try
		{
			if(number == null || number.equals("")) return 0;
			else return Long.parseLong(number);
			
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	}
}
