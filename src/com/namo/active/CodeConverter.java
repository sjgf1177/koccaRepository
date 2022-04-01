package com.namo.active;

import java.io.*;
import java.lang.String;
import javax.mail.internet.MimeUtility;
import javax.mail.MessagingException;

public class CodeConverter
{
	/**
	 * 원본 문자열을 지정한 char-set으로 인코딩합니다.
	 */
	static String convertString(String src, String enc)
	{
		String converted = null;

		try
		{
			converted = new String(src.getBytes(), enc);
		}
		catch(UnsupportedEncodingException uee)
		{
			return null;
		}

		return converted;
	}

	static String convertString(byte [] src, String enc)
	{
		String converted = null;
		try
		{
			converted = new String(src, enc);
		}
		catch(UnsupportedEncodingException uee)
		{
			return null;
		}

		return converted;
	}

	/**
	 * MIME의 name 프로퍼티에 사용되는 문자열을 시스템 인코딩에 맞게 디코딩 합니다.
	 * @param encoded	MIME Encode가 된 문자열
	 */
	static String getMIMEEncodedString(String encoded)
	{
		String charset = null;
		String enctype = null;
		String content = null;
		String converted = null;
		byte [] convertsrc;
		int beginindex = 0;
		int endindex = 0;

		// is MIME Encoded word?
		if(encoded.charAt(0) == '=' && encoded.charAt(1) == '?')
		{
			beginindex = encoded.indexOf("?", endindex);
			endindex = encoded.indexOf("?", beginindex + 1);
			if(beginindex == -1 || endindex == -1)
				return null;
			charset = new String(encoded.substring(beginindex + 1, endindex));

			beginindex = endindex;
			endindex = encoded.indexOf("?", beginindex + 1);
			if(beginindex == -1 || endindex == -1)
				return null;
			enctype = new String(encoded.substring(beginindex + 1, endindex));
			if(enctype.equals("b"))
				enctype = "base64";

			beginindex = endindex;
			endindex = encoded.indexOf("?", beginindex + 1);
			if(beginindex == -1 || endindex == -1)
				return null;
			content = new String(encoded.substring(beginindex + 1, endindex));
		}

		if(charset != null && charset.length() > 0)
		{
			try
			{
				InputStream is = new ByteArrayInputStream(content.getBytes("iso-8859-1"));
				try
				{
					is = MimeUtility.decode(is, enctype);
				}
				catch(MessagingException moe)
				{
					return null;
				}
 				convertsrc = new byte[is.available() + 1];
				is.read(convertsrc);
			}
			catch(IOException ioe)
			{
				return null;
			}
			converted = convertString(convertsrc, charset).trim();
		}

		return converted;
	}

	static void main(String[] argv)
	{
		// 아래와 같은 MIME Encoded word가 있을 경우 디코딩이 가능
		String test = new String("=?euc-kr?b?x9Gx2y5qcGc=?=");
		test = getMIMEEncodedString(test);
	}
};
