package com.dunet.common.util;

import java.util.StringTokenizer;

public class StringUtil {
	/**
	 * �Ҽ��� 2�ڸ� ���� ���� & 3�ڸ����� ',' �߰�
	 * @param (int, long, double, String) inputValue
	 * @return
	 */
	public static String setString(int inputValue) {
		return setString(Integer.toString(inputValue));
	}
	public static String setString(long inputValue) {
		return setString(Long.toString(inputValue));
	}
	public static String setString(double inputValue) {
		return setString(Double.toString(inputValue));
	}
	public static String setString(String inputValue) {
		inputValue = inputValue.trim();
		int dot = inputValue.indexOf('.');
		int length = inputValue.indexOf('.') != -1 && inputValue.indexOf('.') + 2 < inputValue.length() ? inputValue.indexOf('.') + 2 : inputValue.length();
		if (dot < 0) dot = inputValue.length();
		StringBuffer result = new StringBuffer();
		StringBuffer temp = new StringBuffer(inputValue.substring(0,dot));
		temp.reverse();
		if (inputValue != null) {
			for (int i = 0; i < temp.length(); i++) {
				if (i != 0 && i%3 == 0 && i < temp.length()) result.append(',');
				result.append(temp.charAt(i));
			}
		}
		result.reverse();
		result.append(inputValue.substring(dot, length));
		return result.toString();
	}

	/**
	 * inputValue ���� from �� ã�� to �� �ٲ��ش�.
	 * @param String inputValue
	 * @param char(String) from
	 * @param char(String) to
	 * @return
	 */
	public static String replaceAll(String inputValue, char from, char to) {
		if (inputValue == null) return null;
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < inputValue.length(); i++) {
			if (inputValue.charAt(i) != from)
				result.append(inputValue.charAt(i));
			else result.append(to);
		}
		return result.toString();
	}
	/**
	 * inputValue ���� from �� ã�� to �� �ٲ��ش�.
	 * @param String inputValue
	 * @param char(String) from
	 * @param char(String) to
	 * @return
	 */
	public static String replaceAll(String inputValue, char from, String to) {
		if (inputValue == null) return null;
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < inputValue.length(); i++) {
			if (inputValue.charAt(i) != from)
				result.append(inputValue.charAt(i));
			else result.append(to);
		}
		return result.toString();
	}
	/**
	 * inputValue ���� from �� ã�� to �� �ٲ��ش�.
	 * @param String inputValue
	 * @param char(String) from
	 * @param char(String) to
	 * @return
	 */
	public static String replaceAll(String inputValue, String from, String to) {
		if (inputValue == null) return null;
		else if(inputValue.indexOf(from)==-1) return inputValue;
		StringBuffer result = new StringBuffer();
		StringTokenizer st = new StringTokenizer(inputValue, from);
		boolean isFirst = true;
		while (st.hasMoreTokens()) {
			result.append(st.nextToken());
			if(isFirst || st.hasMoreTokens()) {
				result.append(to);
				isFirst = false;
			}
		}
		//        System.out.println("inputValue : " + inputValue + "\nfrom : " + from + "\nto : " +  to + " \nresult : " + result );
		return result.toString();
	}
	/**
	 * inputValue ���� from �� ã�� to �� �ٲ��ش�.
	 * @param String inputValue
	 * @param char(String) from
	 * @param char(String) to
	 * @return
	 */
	public static String replaceAll(String inputValue, String from, char to) {
		if (inputValue == null) return null;
		StringBuffer result = new StringBuffer();
		StringTokenizer st = new StringTokenizer(inputValue, from);
		boolean	n = false;
		while (st.hasMoreTokens()) {
			result.append(st.nextToken());
			if (n) result.append(to);
			else n = true;
		}
		return result.toString();
	}

	public static String replaceQueryString(String inputValue, String from, String to) {
		return replaceAll(inputValue, from, "'" + to + "'");
	}

	/**
	���ڿ��� ������ ���ڿ��� ã�Ƽ� ���ο� ���ڿ��� �ٲٴ� �Լ�
	origianl	��� ���ڿ�
	oldstr		ã�� ���ڿ�
	newstr		�ٲ� ���ڿ�
	return		�ٲ� ���
	 */
	public static String replace(String original, String oldstr, String newstr)
	{
		String convert = new String();
		int pos = 0;
		int begin = 0;
		pos = original.indexOf(oldstr);

		if(pos == -1)
			return original;

		while(pos != -1)
		{
			convert = convert + original.substring(begin, pos) + newstr;
			begin = pos + oldstr.length();
			pos = original.indexOf(oldstr, begin);
		}
		convert = convert + original.substring(begin);

		return convert;
	}

	/**
		������ HTML ������ȣ�� ���ڸ� HTML Ư����ȣ �������� ��ȯ�մϴ�.
		htmlstr		�ٲ� ����� ���ڿ�
		return		�ٲ� ���
		PHP�� htmlspecialchars�� ������ ����� ��ȯ�մϴ�.
	 */
	public static String convertHtmlchars(String htmlstr)
	{
		String convert = new String();
		convert = replace(htmlstr, "<", "&lt;");
		convert = replace(convert, ">", "&gt;");
		convert = replace(convert, "\"", "&quot;");
		convert = replace(convert, "&nbsp;", "&amp;nbsp;");
		return convert;
	}

	// ���ڿ��� Byte �� ��ŭ �ڸ��� "..." �� �ٿ� �߸� ���ڿ����� ǥ��
	public static String getByteString(String i_sSource, int i_iLength) {
		String	result	= "";

		if ((null == i_sSource) || "".equals(i_sSource)) {
			return result;
		}

		if (i_iLength <= 0) {
			return i_sSource;
		}

		try {

			char 	cTmp;
			byte[]	bTmp;
			int		nowLength	= 0;
			int		strLemgth	= 0;

			for (int i = 0 ; i < i_sSource.length(); i++ )
			{
				cTmp	= i_sSource.charAt(i);

				bTmp		= ("" + cTmp).getBytes("UTF-8");
				strLemgth	= bTmp.length;

				if (strLemgth == 3)
					nowLength += 2;
				else
					nowLength += strLemgth;

				if (nowLength <= i_iLength)
				{
					result	+= cTmp;
				}
				else
				{
					break;
				}
			}

			if (i_sSource.length() > result.length())
				result += "...";

		} catch (Exception e) {
			result	= i_sSource;
		}

		return result;
	}

	public static String getStarPoint(float point) {

		StringBuffer str = new StringBuffer();

		int v_iPoint	= 0; 	// ����(�����κ�)
		float v_fPoint  = 0.0f; // ����(�Ҽ��κ�)
		int v_iNoPoint  = 0;	// ��������


		v_iPoint    = ((Float)point).intValue();
		v_fPoint  = (point - v_iPoint);

		if(v_fPoint > 0 ) {
			v_iNoPoint = 5 - v_iPoint - 1;
		} else {
			v_iNoPoint = 5 - v_iPoint;
		}

		if (v_iPoint > 0 ) {
			for(int j = 0 ; j < v_iPoint ; j ++) {
				str.append("<img src='/images/portal/ico/ico_star_on.gif'>");
			}
		}

		if(v_fPoint > 0 ) {
			str.append("<img src='/images/portal/ico/ico_star_half.gif'>");
		}

		if(v_iNoPoint > 0 ) {
			for(int j = 0 ; j < v_iNoPoint ; j ++) {
				str.append("<img src='/images/portal/ico/ico_star_off.gif'>");
			}
		}

		return str.toString();
	}

	public static String getString(String str){
		String tempStr = str;
		if(tempStr == null) {
			tempStr = "";
		}
		return tempStr;
	}
	
	/**
	 * HTML �±׸� �������ش�.
	 * @param s
	 * @return
	 */
    public static String removeTag(String s) {
        return s.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","");
    }
}
