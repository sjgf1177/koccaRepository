package com.credu.scorm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class StringUtil
{
	public static final String TITLE_NAME = "NAME :";
	public static final String TITLE_ORGANIZATION = "ORGANIZATION :";
	public static final String TITLE_ADDRESS = "ADDRESS :";
	public static final String TITLE_EMAIL = "EMAIL :";

	public StringUtil()
	{
	}

	@SuppressWarnings("deprecation")
	public static String urlEncode(String str)
	{
		return replace(URLEncoder.encode(str), "+", "%20");
	}

	public static String replace(String str, String pattern, String replace)
	{
		int s = 0;
		int e = 0;
		if(pattern.equals(""))
			return str;
		StringBuffer result = new StringBuffer();
		while((e = str.indexOf(pattern, s)) >= 0)
		{
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static String replace(String str, int sPos, int ePos, String replace)
	{
		StringBuffer result = new StringBuffer(str.substring(0, sPos));
		result.append(replace);
		result.append(str.substring(ePos));
		return result.toString();
	}

	public static String[] getArrToken(String str, String delim)
	{
		StringTokenizer tk = new StringTokenizer(str, delim);
		String arr[] = new String[tk.countTokens()];
		for(int i = 0; i < arr.length; i++)
			arr[i] = tk.nextToken();

		return arr;
	}

	public static String[] getArrToken2(String str, String delim)
	{
		return split(str, delim, true);
	}

	public static String HrInsEnterX(String str, int length)
	{
		String tmp[] = getArrToken(str, " ");
		String rtnStr = "";
		int line_num = 1;
		for (String element : tmp) {
			if((rtnStr + element).length() > length * line_num)
			{
				rtnStr = rtnStr + "\n";
				line_num++;
			}
			rtnStr = rtnStr + " " + element;
		}

		return rtnStr;
	}

	public static String HrInsEnterX(String str, int length, String pad)
	{
		return replace(HrInsEnterX(str, length), "\n", "\n" + pad);
	}

	public static String HrInsEnterX2(String str, int length)
	{
		String tmp[] = getArrToken(str, " ");
		String rtnStr = "";
		String this_line = "";
		for (String element : tmp) {
			this_line = this_line + element;
			if(this_line.length() > length)
			{
				rtnStr = rtnStr + this_line + "\n";
				this_line = "";
			} else
				if(this_line.indexOf("\n") != -1)
				{
					rtnStr = rtnStr + this_line;
					this_line = "";
				}
		}

		rtnStr = rtnStr + this_line;
		return rtnStr;
	}

	public static String getIntString(String str)
	{
		String rtn = "";
		try
		{
			rtn = String.valueOf(Integer.parseInt(str));
		}
		catch(Exception e)
		{
			rtn = "";
		}
		return rtn;
	}

	public static String getNumber(String str)
	{
		if(str == null)
			return str;
		StringBuffer sb = new StringBuffer(str);
		StringBuffer newSb = new StringBuffer();
		int sbLen = sb.length();
		for(int i = 0; i < sbLen; i++)
		{
			char number = sb.charAt(i);
			if(number >= '0' && number <= '9')
				newSb.append(sb.charAt(i));
		}

		return newSb.toString();
	}

	public static String fixLength(String input)
	{
		return fixLength(input, 15, "...");
	}

	public static String fixLength(String input, int limit)
	{
		return fixLength(input, limit, "...");
	}

	public static String fixLength(String input, int limit, String postfix)
	{
		char charArray[] = input.toCharArray();
		if(limit >= charArray.length)
			return input;
		else
			return (new String(charArray, 0, limit)).concat(postfix);
	}

	public static String fixUnicodeLength(String input, int limitByte)
	{
		return fixLength(input, limitByte, "...");
	}

	public static String fixUnicodeLength(String input, int limitByte, String postfix)
	{
		byte outputBytes[] = input.getBytes();
		String output = outputBytes.length > limitByte ? ((new String(outputBytes, 0, limitByte)).length() != 0 ? new String(outputBytes, 0, limitByte) : (new String(outputBytes, 0, limitByte - 1)).concat(postfix)).concat(postfix) : input;
		return output;
	}

	public static String getEUC_KR(String text)
	{
		String rtn = "";
		if(text == null)
			return rtn;
		try
		{
			return new String(text.getBytes("8859_1"), "euc-kr");
		}
		catch(UnsupportedEncodingException UEE)
		{
			return rtn;
		}
	}

	public static String[] getEUC_KR(String arrText[])
	{
		String arrRtn[] = new String[arrText.length];
		for(int i = 0; i < arrText.length; i++)
			arrRtn[i] = getEUC_KR(arrText[i]);

		return arrRtn;
	}

	public static String get8859_1(String text)
	{
		String rtn = "";
		if(text == null)
			return rtn;
		try
		{
			return new String(text.getBytes("euc-kr"), "8859_1");
		}
		catch(UnsupportedEncodingException UEE)
		{
			return rtn;
		}
	}

	public static String getConvertCharset(String text, String fromEncode, String toEncode)
	{
		String rtn = "";
		if(text == null)
			return rtn;
		try
		{
			return new String(text.getBytes(fromEncode), toEncode);
		}
		catch(UnsupportedEncodingException UEE)
		{
			return rtn;
		}
	}

	public static String getSpecialCharacters(String str)
	{
		str = replace(str, "&", "&amp;");
		str = replace(str, "<", "&lt;");
		str = replace(str, ">", "&gt;");
		str = replace(str, "'", "&acute;");
		str = replace(str, "\"", "&quot;");
		str = replace(str, "|", "&brvbar;");
		str = replace(str, "\n", "<BR>");
		str = replace(str, "\r", "");
		return str;
	}

	public static String getRreplaceSpecialCharacters(String str)
	{
		str = replace(str, "<BR>", "\n");
		str = replace(str, "&amp;", "&");
		str = replace(str, "&lt;", "<");
		str = replace(str, "&gt;", ">");
		str = replace(str, "&acute;", "'");
		str = replace(str, "&quot;", "\"");
		str = replace(str, "&brvbar;", "|");
		return str;
	}

	public static String getComma(int str)
	{
		return getComma(Integer.toString(str), true);
	}
	public static String getComma(String str)
	{
		return getComma(str, true);
	}

	public static String getComma(String str, boolean isTruncated)
	{
		if(str == null)
			return "";
		if(str.trim().equals(""))
			return "";
		if(str.trim().equals("&nbsp;"))
			return "&nbsp;";
		int pos = str.indexOf(".");
		if(pos != -1)
		{
			if(!isTruncated)
			{
				DecimalFormat commaFormat = new DecimalFormat("#,##0.00");
				return commaFormat.format(Float.parseFloat(str.trim()));
			} else
			{
				DecimalFormat commaFormat = new DecimalFormat("#,##0");
				return commaFormat.format(Long.parseLong(str.trim().substring(0, pos)));
			}
		} else
		{
			DecimalFormat commaFormat = new DecimalFormat("#,##0");
			return commaFormat.format(Long.parseLong(str.trim()));
		}
	}

	public static String getComma(Long lstr)
	{
		DecimalFormat commaFormat = new DecimalFormat("#,##0");
		if(lstr == null)
			return "";
		else
			return commaFormat.format(lstr);
	}

	public static String getFormatedText(String text, String format)
	{
		int tCount = text.length();
		int fCount = format.length();
		String rtn = "";
		if(text.equals(""))
			return rtn;
		if(text.equals("&nbsp;"))
			return "&nbsp;";
		for(int i = 0; i < tCount; i++)
			if(!text.substring(i, i + 1).equals("-"))
				rtn = rtn + text.substring(i, i + 1);

		text = rtn;
		tCount = text.length();
		int len = 0;
		for(int j = 0; j < fCount; j++)
			if(format.substring(j, j + 1).equals("?"))
				len++;

		if(tCount < len)
		{
			for(int i = 0; i < len - tCount; i++)
				text = '0' + text;

			tCount = len;
		}
		rtn = "";
		int start = 0;
		for(int i = 0; i < tCount; i++)
		{
			for(int j = start; j < fCount; j++)
			{
				if(format.substring(j, j + 1).equals("?"))
				{
					rtn = rtn + text.substring(i, i + 1);
					start++;
					break;
				}
				rtn = rtn + format.substring(j, j + 1);
				start++;
			}

		}

		return rtn + format.substring(start);
	}

	public static String getRawText(String text, String format)
	{
		int tCount = text.length();
		int fCount = format.length();
		String rtn = "";
		if(text.equals(""))
			return rtn;
		if(text.equals("&nbsp;"))
			return "&nbsp;";
		int start = 0;
		for(int i = 0; i < tCount; i++)
		{
			for(int j = start; j < fCount;)
			{
				if(format.substring(j, j + 1).equals("?"))
				{
					rtn = rtn + text.substring(i, i + 1);
					start++;
				} else
				{
					start++;
				}
				break;
			}

		}

		return rtn;
	}

	public static String getZeroBaseString(int num, int size)
	{
		return getZeroBaseString(String.valueOf(num), size);
	}

	public static String getZeroBaseString(String num, int size)
	{
		String zeroBase = "";
		if(num.length() >= size)
			return num;
		for(int index = 0; index < size - num.length(); index++)
			zeroBase = zeroBase + "0";

		return zeroBase + num;
	}

	public static String getGetMethodFormat(String str)
	{
		String rtn = "";
		rtn = replace(str, "\n", " ");
		rtn = replace(rtn, " ", "%20");
		return rtn;
	}

	public static String lpad(String str, String f_char, int size)
	{
		if(str.length() >= size)
			return str;
		else
			return getFillChar("", f_char, size - str.length()) + str;
	}

	public static String rpad(String str, String f_char, int size)
	{
		if(str.length() >= size)
			return str;
		else
			return str + getFillChar("", f_char, size - str.length());
	}

	public static String getFillChar(String str, String f_char, int size)
	{
		String fillChar = "";
		if(str.length() >= size)
			return str;
		for(int index = 0; index < size - str.length(); index++)
			fillChar = fillChar + f_char;

		return str + fillChar;
	}

	public static String getFillCharByte(String str, String f_char, int size)
	{
		String fillChar = "";
		if(str.getBytes().length >= size)
			return str;
		for(int index = 0; index < size - str.getBytes().length; index++)
			fillChar = fillChar + f_char;

		return str + fillChar;
	}

	public static String[] split(String strTarget, String strDelim, boolean bContainNull)
	{
		int index = 0;
		String resultStrArray[] = new String[getStrCnt(strTarget, strDelim) + 1];
		for(String strCheck = new String(strTarget); strCheck.length() != 0;)
		{
			int begin = strCheck.indexOf(strDelim);
			if(begin == -1)
			{
				resultStrArray[index] = strCheck;
				break;
			}
			int end = begin + strDelim.length();
			if(bContainNull)
				resultStrArray[index++] = strCheck.substring(0, begin);
			strCheck = strCheck.substring(end);
			if(strCheck.length() == 0 && bContainNull)
			{
				resultStrArray[index] = strCheck;
				break;
			}
		}

		return resultStrArray;
	}

	public static int getStrCnt(String strTarget, String strSearch)
	{
		int result = 0;
		String strCheck = new String(strTarget);
		for(int i = 0; i < strTarget.length();)
		{
			int loc = strCheck.indexOf(strSearch);
			if(loc == -1)
				break;
			result++;
			i = loc + strSearch.length();
			strCheck = strCheck.substring(i);
		}

		return result;
	}

	public static String makeFUpper(String src)
	{
		if(src != null && src.length() > 0)
			return src.substring(0, 1).toUpperCase() + src.substring(1).toLowerCase();
		else
			return "";
	}

	public static boolean isExistInArray(String array[], String str, boolean ignorecase)
	{
		if(array == null)
			return false;
		for (String element : array)
			if(ignorecase)
			{
				if(element.toUpperCase().equals(str.toUpperCase()))
					return true;
			} else
				if(element.equals(str))
					return true;

		return false;
	}

	public static String ltrim(String str)
	{
		int len = str.length();
		int st = 0;
		int off = 0;
		for(char val[] = str.toCharArray(); st < len && val[off + st] <= ' '; st++);
		return st <= 0 ? str : str.substring(st);
	}

	public static String rtrim(String str)
	{
		int len = str.length();
		int st = 0;
		int off = 0;
		for(char val[] = str.toCharArray(); st < len && val[(off + len) - 1] <= ' '; len--);
		return len >= str.length() ? str : str.substring(0, len);
	}

	public static String addBackSlash(String pm_sString)
	{
		String lm_sRetString = "";
		for(int i = 0; i < pm_sString.length(); i++)
			if(pm_sString.charAt(i) == '\\')
				lm_sRetString = lm_sRetString + "\\\\";
			else
				if(pm_sString.charAt(i) == '\'')
					lm_sRetString = lm_sRetString + "\\'";
				else
					lm_sRetString = lm_sRetString + pm_sString.charAt(i);

		return lm_sRetString;
	}

	public static String encode(String inputStr)
	{
		String result = "";
		inputStr = inputStr.trim();
		for(int i = 0; i < inputStr.length(); i++)
		{
			int val = inputStr.charAt(i) + i * 29;
			result = result + lpad(String.valueOf(val), "0", 3);
		}

		return result;
	}

	public static String decode(String inputStr)
	{
		String result = "";
		inputStr = inputStr.trim();
		for(int i = 0; i < inputStr.length() / 3; i++)
		{
			int num = Integer.parseInt(inputStr.substring(i * 3, i * 3 + 3)) - i * 29;
			result = result + (char)num;
		}

		return result;
	}

	public static String nbsp(int count)
	{
		String result = "";
		for(int i = 0; i < count; i++)
			result = result + "&nbsp;";

		return result;
	}

	public static String centity(String str)
	{
		String ret = ",,,";

		try {
			if ( str != null ) {
				ret = str.substring(TITLE_NAME.length(),str.lastIndexOf(TITLE_ORGANIZATION)-1).trim() + ","
				+ str.substring(str.lastIndexOf(TITLE_ORGANIZATION) + TITLE_ORGANIZATION.length(), str.lastIndexOf(TITLE_ADDRESS)-1).trim() + ","
				+ str.substring(str.lastIndexOf(TITLE_ADDRESS) + TITLE_ADDRESS.length(), str.lastIndexOf(TITLE_EMAIL)-1).trim() + ","
				+ str.substring(str.lastIndexOf(TITLE_EMAIL)+TITLE_EMAIL.length()).trim();
			}
		} catch (Exception e) { }
		return ret;
	}

	public static int getBytesCount(String str) {
		int count = 0;
		byte[] b = null;

		if ( str != null ) {
			b = str.getBytes();
			count = b.length;
		}
		return count;
	}

	public static String juminno(String str)
	{
		String ret = "";

		try {
			if ( str != null && str.length() == 13 ) {
				ret = str.substring(0,6) + "-" + str.substring(6);
			}
		} catch (Exception e) { }
		return ret;
	}
}