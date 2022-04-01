package com.credu.library;

import java.text.DecimalFormat;

/**
* ������Ʈ�� : imba_java
* ��Ű���� : com.credu.library
* ���ϸ� : NumberManager.java
* �ۼ���¥ : 2011. 3. 22.
* ó������ : ���ڰ���
* �������� : 
 
* Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.    
*/

public class NumberManager 
{
	/**
	 * long�� ����
	 * @param number
	 * @return
	 */
	public static String getIntFormat(int number)
	{
		return new DecimalFormat("#,##0").format(number);
	}
	
	/**
	 * long�� ����
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
	 * long�� ����
	 * @param number
	 * @return
	 */
	public static String getlongFormat(long number)
	{
		return new DecimalFormat("#,##0").format(number);
	}
	
	/**
	 * long�� ����
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
	 * double�� ����
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
	 * String int�� ��ȯ
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
	 * String long�� ��ȯ
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
