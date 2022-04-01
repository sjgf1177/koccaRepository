
package com.credu.library;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.credu.system.MenuAuthData;

/**
 * <p>����: String ���� ���̺귯��</p>
 * <p>����: String ���� ���̺귯��</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
 *@date 2003. 12
 *@version 1.0
 */
public class StringManager {

	/**
	 * Base64�� ��ȣȭ
    @param str ��ü ���ڿ�
    @return Base64�� ��ȣȭ�� ���ڿ�
	 */
	public static String BASE64Decode(String str) {
		String result = "";
		BASE64Decoder decoder;

		try {
			decoder = new BASE64Decoder();
			result = new String(decoder.decodeBuffer(str));
		}
		catch(Exception e) {}

		return result;
	}
	/**
	 * Base64�� ��ȣȭ
    @param str ��ü ���ڿ�
    @return Base64�� ��ȣȭ�� ���ڿ�
	 */
	public static String BASE64Encode(String str) {
		String result = "";
		BASE64Encoder encoder;

		try {
			encoder = new BASE64Encoder();
			result = encoder.encode(str.getBytes());
		}
		catch(Exception e) {}
		return result;
	}

	/**
	 * null üũ
    @param str ��ü ���ڿ�
    @return str  null �ΰ�� "" ��, �ƴϸ� ������ ���ڿ��� ��ȯ�Ѵ�.
	 */
	public static String chkNull(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
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
		convert = replace(convert, "\n", "<br/>");
		return convert;
	}

	/**
	 * ������ �����ٶ� ���ѵ� ���̸� �ʰ��ϸ� �޺κ��� ¥���� "..." ���� ��ġ�Ѵ�.
    @param title(������� ���ڿ�), max(�ִ����)
    @return title(����� ���ڿ�)
	 */
	public static String cutZero(String seq) {
		String result = "";

		try {
			result = Integer.parseInt(seq)+"";
		}
		catch(Exception e) {}
		return result;
	}

	public static String engEncode(String str) throws UnsupportedEncodingException {
		if(str == null) {
			return null;
		}
		return new String(str.getBytes("KSC5601"), "8859_1");
	}

	/**
	 * ������ �����ٶ� ���ѵ� ���̸� �ʰ��ϸ� �޺κ��� ¥���� "..." ���� ��ġ�Ѵ�.
    @param title(������� ���ڿ�), max(�ִ����)
    @return title(����� ���ڿ�)
	 */
	public static String formatTitle(String title, int max) {
		if (title==null) {
			return null;
		}

		if (title.length() <= max) {
			return title;
		} else {
			return title.substring(0,max-3) + "...";
		}
	}

	/**
	 * ���ڿ��� Byte �� ��ŭ �ڸ��� "..." �� �ٿ� �߸� ���ڿ����� ǥ��
    @param i_Source(������� ���ڿ�), i_iLength(�ִ����)
    @return result(����� ���ڿ�)
	 */
    public static String getByteString(String i_sSource, int i_iLength) {
    	String	result	= "";

    	if ((null == i_sSource) || "".equals(i_sSource)) {
    		return result;
    	}

    	if (i_iLength <= 0) {
    		return i_sSource;
    	}

    	try {
	    	byte[]	buffer	= i_sSource.getBytes("euc-kr");

	    	if (buffer.length <= i_iLength) {
	    		result	= i_sSource;
	    	} else {
	    		int	nCount	= 0;

	    		for (int i = 0; i < i_iLength; i++) {
	    			nCount	+= (((int)buffer[i] < 0) ? 1 : 0);
	    		}

	    		if ((nCount % 2) != 0) {
	    			i_iLength--;
	    		}

	    		result	= new String(buffer, 0, i_iLength, "euc-kr") + "...";
	    	}
    	} catch (Exception e) {
    		result	= i_sSource;
    	}

    	return result;
    }

	public static String korEncode(String str) throws UnsupportedEncodingException {
		if(str == null) {
			return null;
		}
		return new String(str.getBytes("8859_1"), "KSC5601");
	}

	/**
	 * SQL Query ������ value ���� ' ' �� ����� �ֱ� ���� �޼ҵ�
    @param str   ' ' �ȿ� �� ���� ��
    @return   'str' �� ���ϵ�
	 */
	public static String makeSQL(String str) {
		String result = "";
		if(str != null) {
			result = "'" + chkNull(replace(str.trim(), "'", "")) + "'";
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static String menuListFetch(ArrayList list2, int  v_gadmincnt, String[] v_gadmin,String[] v_control) throws Exception{
		StringBuffer sb = new StringBuffer();
		int    v_levels      = 0;                        // ����
		int    v_seq         = 0;                        // seq
		String v_gubun       = "";                       // ���� (��޴�, �Ҹ޴�, ���)
		String v_menu        = "";                       // �޴�
		String v_menunm      = "";                       // �޴���
		String v_control_org = "";                       // ����(�б�/����)
		String v_rchecked    = "";                       // �б���� üũ
		String v_wchecked    = "";                       // ������� üũ

		String v_classcenter = "";                       // td class ����(�߾�����)
		//		String v_classleft   = "";                       // td class ����(��������)
		String v_classright  = "";                       // td class ����(����������)
		int    v_menucount   = 0;
		int    j=0,k=0,v_width=1000;
		final String _02_1 = "table_02_1";
		//		final String _02_2 = "table_02_2";
		final String _02_4 = "table_02_4";
		final String _02_5 = "table_02_5";
		//		final String _02_6 = "table_02_6";
		final String _02_7 = "table_02_7";
		final String _02_8 = "table_02_8";
		//		final String _02_9 = "table_02_9";
		final String _02_10 = "table_02_10";
		final String _MENU = "�޴�";
		final String _MODULE = "���";
		final String _BOLD = "<b>";
		final String _BOLD_CLOSE = "</b>";
		//�޴� ����Ʈ
		for(int i = 0; i < list2.size(); i++) {

			MenuAuthData data2  = (MenuAuthData)list2.get(i);
			v_levels  = data2.getLevels();
			v_seq     = data2.getSeq();
			v_menu    = data2.getMenu();
			v_menunm  = data2.getMenunm();

			if (v_levels == 1 && v_seq == 0) {
				v_menucount++;
				v_gubun = _BOLD + String.valueOf(v_menucount) + _BOLD_CLOSE;

				v_classcenter = _02_8;
				//				v_classleft   = _02_9;
				v_classright  = _02_10;
			} else if (v_levels == 2 && v_seq == 0) {
				v_gubun = _MENU;
				v_classcenter = _02_5;
				//				v_classleft   = _02_6;
				v_classright  = _02_7;
			} else {
				v_gubun = _MODULE;
				v_classcenter = _02_1;
				//				v_classleft   = _02_2;
				v_classright  = _02_4;
			}

			sb.append("<td width=\"40\" height=\"25\" class=\""+v_classcenter+"\">"+v_gubun+"</td>");
			sb.append("<td width=\"160\" class=\""+v_classcenter+"\">"+v_menunm+"</td>");

			// ���� ����Ʈ
			if(v_gadmincnt > 0) {
				v_control_org = data2.getControl();
				StringTokenizer st = new StringTokenizer(v_control_org,"/");
				j = 0;

				// ���α��ѿ� ���� ������ �б�/������Ѱ� �迭�� ����
				while (st.hasMoreElements()) {
					v_control[j] = StringManager.chkNull(st.nextToken());
					j++;
				}

				// ��ġ ������ ���� ���а� (�Ǿռ��� ���ڸ�) ����
				for (j = 0;j<v_gadmincnt;j++){
					v_control[j] = rightstring(v_control[j], v_control[j].length()-1);
				}

				// �ش� ���� üũ�ڽ��� ����
				for (k=0; k < v_gadmincnt ; k++) {
					v_control[k] = StringManager.chkNull(v_control[k]);

					if (v_control[k].equals("r") || v_control[k].equals("rw") ) {
						v_rchecked = "checked";
					} else {
						v_rchecked = "";
					}
					if (v_control[k].equals("w") || v_control[k].equals("rw") ) {
						v_wchecked = "checked";
					} else {
						v_wchecked = "";
					}

					sb.append("<td width=\""+v_width/10+"\" class=\""+v_classcenter+"\">");
					sb.append("<input type=\"hidden\" name = \"p_menu"+v_gadmin[k]+"\" value=\""+v_menu+"\">");
					sb.append("<input type=\"hidden\" name = \"p_menusubseq"+v_gadmin[k]+"\" value=\""+v_seq+"\">");
					sb.append("<input type=\"hidden\" name = \"p_gadmin"+v_gadmin[k]+"\" value=\""+v_gadmin[k]+"\">");
					sb.append("<table width='100'>");
					if (v_seq == 0) {
						sb.append("<tr>");
						sb.append("<td class=\""+v_classright+"\"> View </td>");
						sb.append("<td class=\""+v_classright+"\">");
						sb.append("<input type=\"checkbox\" name=\"p_"+v_gadmin[k]+"R"+i+k+"\" value=\"r\" "+v_rchecked+">");
						sb.append("<input type=\"hidden\" name=\"p_"+v_gadmin[k]+"W"+i+k+"\" value=\"w\" "+v_wchecked+">");
						sb.append("</td>");
					} else {
						sb.append("<tr>");
						sb.append("<td class=\""+v_classcenter+"\"> R </td>");
						sb.append("<td class=\""+v_classright+"\"> ");
						sb.append("<input type=\"checkbox\" name=\"p_"+v_gadmin[k]+"R"+i+k+"\" value=\"r\" "+v_rchecked+">");
						sb.append("<td class=\""+v_classcenter+"\"> W </td>");
						sb.append("<td class=\""+v_classright+"\"> ");
						sb.append("<input type=\"checkbox\" name=\"p_"+v_gadmin[k]+"W"+i+k+"\" value=\"w\" "+v_wchecked+">");
						sb.append("</td>");
					}
					sb.append("</table>");
					sb.append("</td>");

				}
			}
			sb.append("</tr>");
		}
		return sb.toString();
	}

	/**
	 * �ش� ���ڿ����� older String �� newer String ���� ��ü�Ѵ�.
    @param original ��ü String
    @param older ��ü String �� ��ü �� ���� String
    @param newer ��ü String �� ��ü �� ���� String
    @return result ��ü�� ���ڿ��� ��ȯ��
	 */
	public static String replace(String original, String older, String newer) {
		String result = original;

		if(original != null) {
			int idx = result.indexOf(older);
			int newLength = newer.length();

			while(idx >= 0) {
				if (idx == 0) {
					result = newer + result.substring(older.length());
				}else {
					result = result.substring(0, idx) + newer + result.substring(idx + older.length());
				}
				idx = result.indexOf(older, idx + newLength);
			}
		}
		return result;
	}

	/**
	 *java.lang.String ��Ű���� substring() �޼ҵ�� ����� �����ѵ� ������ ���ڳ����� count �� �ؼ� �ڸ�
    @param str ��ü ���ڿ�
    @param count  ������ ���ڳ�(1) ���� count ����
    @return result  substring �� ���ڿ��� ��ȯ��
	 */
	public static String rightstring(String str, int count) throws Exception {
		if (str == null) {
			return null;
		}

		String result = null;
		try {
			if (count == 0) {
				result = "";
			} else if (count > str.length()) {
				result = str;
			} else {
				result = str.substring(str.length()-count,str.length());  //  ������ count ��ŭ ����
			}
		}
		catch (Exception ex) {
			throw new Exception("StringManager.rightstring(\""+str+"\","+count+")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 * java.lang.String ��Ű���� substring() �޼ҵ�� ����� ����, null üũ�� ��
    @param str ��ü ���ڿ�
    @param beginIndex
    @return result  substring �� ���ڿ��� ��ȯ��
	 */
	public static String substring(String str, int beginIndex) {
		String result = "";

		if(str != null) {
			result = str.substring(beginIndex);
		}

		return result;
	}

	/**
	 * java.lang.String ��Ű���� substring() �޼ҵ�� ����� ����, null üũ�� ��
    @param str ��ü ���ڿ�
    @param beginIndex
    @param endIndex
    @return result  substring �� ���ڿ��� ��ȯ��
	 */
	public static String substring(String str, int beginIndex, int endIndex) {
		String result = "";

		if(str != null) {
			result = str.substring(beginIndex, endIndex);
		}

		return result;
	}

	/**
	 * String ���� int ������ ��ȯ, null �� "" üũ
    @param str ��ü ���ڿ�
    @return null �� "" �� ��� 0 ��ȯ
	 */
	public static int toInt(String str) {
		if (str == null || str.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	/**
	 * java.lang.String ��Ű���� trim() �޼ҵ�� ����� ����, null üũ�� ��
    @param str ��ü ���ڿ�
    @return result  trim �� ���ڿ��� ��ȯ��
	 */
	public static String trim(String str) throws Exception {
		String result = "";

		if(str != null) {
			result = str.trim();
		}

		return result;
	}
	/**
	 * URLEncoder �� ��ȣȭ
    @param str ��ü ���ڿ�
    @return URLEncoder�� ��ȣȭ�� ���ڿ�
	 */
	@SuppressWarnings("deprecation")
	public static String URLEncode(String str) throws Exception {
		String result = "";
		try {
			if(str != null) {
				result = URLEncoder.encode(str);
			}
		}
		catch (Exception ex) {
			throw new Exception("StringManager.URLEncode(\""+str+"\")\r\n"+ex.getMessage());
		}
		return result;
	}

	// ���ڿ����� <br>�� ����� ��� HTML �±� ����
	public static String removeHTML(String i_sHTML) {
		String	result	= "";

		if ((null == i_sHTML) || "".equals(i_sHTML)) {
			return "";
		}

		i_sHTML	= replace(i_sHTML, "<br>", "@#$%^&*");
		i_sHTML	= replace(i_sHTML, "<BR>", "@#$%^&*");
		i_sHTML	= replace(i_sHTML, "<Br>", "@#$%^&*");
		i_sHTML	= replace(i_sHTML, "<bR>", "@#$%^&*");

		result	= i_sHTML.replaceAll("<.+?>", "");

		result	= replace(result, "@#$%^&*", "<br>");
		result	= replace(result, "\n", "<br>");

		result	= replace(result, "P {margin-top:2px;margin-bottom:2px;}", "");

		return result;
	}
	
	public static String removeHTMLHead(String i_sHTML) {
		// String	result	= "";

		if ((null == i_sHTML) || "".equals(i_sHTML)) {
			return "";
		}		
				
		i_sHTML	= replace(i_sHTML, "<HTML><HEAD><TITLE></TITLE>", "");
		i_sHTML	= replace(i_sHTML, "<META content=\"text/html; charset=euc-kr\" http-equiv=Content-Type>", "");
		i_sHTML	= replace(i_sHTML, "<META content='text/html; charset=euc-kr' http-equiv=Content-Type>", "");
		i_sHTML	= replace(i_sHTML, "<META name=GENERATOR content=ActiveSquare></HEAD>", "");
		i_sHTML	= replace(i_sHTML, "<BODY style=\"FONT-FAMILY: ����; FONT-SIZE: 10pt\">", "");
		i_sHTML	= replace(i_sHTML, "<BODY style='FONT-FAMILY: ����; FONT-SIZE: 10pt'>", "");
		i_sHTML	= replace(i_sHTML, "<P style='LINE-HEIGHT: 200%; MARGIN-TOP: 0px; MARGIN-BOTTOM: 0px' align=left>", "");
		i_sHTML	= replace(i_sHTML, "<html><head>", "");
		i_sHTML	= replace(i_sHTML, "<meta content='text/html; charset=euc-kr' http-equiv='Content-Type'>", "");
		i_sHTML	= replace(i_sHTML, "<meta name='GENERATOR' content='ActiveSquare'>", "");
		i_sHTML	= replace(i_sHTML, "</head><body>", "");
		i_sHTML	= replace(i_sHTML, "<title></title>", "");
		i_sHTML	= replace(i_sHTML, "</head><body style='font-family: ����; font-size: 10pt;'>", "");
		i_sHTML	= replace(i_sHTML, "</BODY></HTML>", "");
		i_sHTML	= replace(i_sHTML, "</body></html>", "");
		i_sHTML	= replace(i_sHTML, "<meta content=\"text/html; charset=euc-kr\" http-equiv=\"Content-Type\">", "");
		i_sHTML	= replace(i_sHTML, "<meta name=\"GENERATOR\" content=\"ActiveSquare\">", "");
		i_sHTML	= replace(i_sHTML, "</head><body style=\"font-family: ����; font-size: 10pt;\">", "");
		

		//i_sHTML	= replace(i_sHTML, "</BODY></HTML>", "");
		//result	= replace(i_sHTML, "P {margin-top:2px;margin-bottom:2px;}", "");
		//result	= replace(i_sHTML, "<HTML><HEAD><TITLE></TITLE> <META content='text/html; charset=euc-kr' http-equiv=Content-Type> <META name=GENERATOR content=ActiveSquare></HEAD> <BODY style='FONT-FAMILY: ����; FONT-SIZE: 10pt'>", "");
		
		//i_sHTML	= replace(i_sHTML, "<HTML><HEAD><TITLE></TITLE><META content=\"text/html; charset=euc-kr\" http-equiv=Content-Type><META name=GENERATOR content=ActiveSquare></HEAD><BODY style=\"FONT-FAMILY: ����; FONT-SIZE: 10pt\">", "");
		
		
		//result	= replace(i_sHTML,"</BODY></HTML>", "");
		//result	= replace(result, "P {margin-top:2px;margin-bottom:2px;}", "");
		
		


		return i_sHTML;
	}
	
	public static String cutText(String strTarget, int nLimit, boolean bDot)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
		{
			return strTarget;
		}
		
		String retValue = null;
		
		int nLen = strTarget.length();
		int nTotal = 0;
		int nHex = 0;
	
		
		String strDot = "";
		
		if ( bDot )
			strDot = "...";
		
		for (int i = 0 ; i < nLen ; i++)
		{
			nHex = (int)strTarget.charAt( i );
			nTotal += Integer.toHexString( nHex ).length() / 2;

			if ( nTotal > nLimit )
			{
				retValue = strTarget.substring( 0, i - 1 ) + strDot;
				break;
			}
			else if ( nTotal == nLimit )
			{
				if ( i == (nLen - 1) )
				{
					retValue = strTarget.substring( 0, i - 1 ) + strDot;
					break;
				}
				retValue = strTarget.substring( 0, i ) + strDot;
				break;
			}
			else
			{
				retValue = strTarget;
			}
		}
		return retValue;
	}

	/**
	 * �����߿��� html tag�� �����Ѵ�.
	 * @param content ���� ����
	 * @return content tag�� ���ŵ� ����
	 */
	public static String getContentsHTMLRemoved(String content) {
	    Pattern SCRIPTS = Pattern.compile("&lt;(no)?script[^&gt;]*&gt;.*?&lt;/(no)?script&gt;",Pattern.DOTALL);
	    Pattern STYLE = Pattern.compile("&lt;style[^&gt;]*&gt;.*&lt;/style&gt;",Pattern.DOTALL);
	    Pattern TAGS = Pattern.compile("&lt;(\"[^\"]*\"|\'[^\']*\'|[^\'\"&gt;])*&gt;");
	    // Pattern nTAGS = Pattern.compile("&lt;\\w+\\s+[^&lt;]*\\s*&gt;");
	    Pattern ENTITY_REFS = Pattern.compile("&amp;[^;]+;");
	    Pattern WHITESPACE = Pattern.compile("\\s\\s+");
	        
	    Matcher m;
	        
	    m = SCRIPTS.matcher(content);
	    content = m.replaceAll("");
	    m = STYLE.matcher(content);
	    content = m.replaceAll("");
	    m = TAGS.matcher(content);
	    content = m.replaceAll("");
	    m = ENTITY_REFS.matcher(content);
	    content = m.replaceAll("");
	    m = WHITESPACE.matcher(content);
	    content = m.replaceAll(" ");        

	    return content;
	}

    /**
     * ���ڿ��� byte ������ �ڸ��� ����� �����Ѵ�.
     * �ѱ۰� ������ byte ������ �ٸ����� ��Ȯ�� ���� ���ڿ��� �ڸ��� �����̴�.  
     * @param str
     * @param byteLength
     * @return
     */
    public static String subStringBytes(String str, int byteLength) {
        // String �� byte ���� ��ŭ �ڸ���.

        int retLength = 0;
        int tempSize = 0;
        int asc;

        if(str == null || "".equals(str) || "null".equals(str)){
            str = "";
        }

        int length = str.length();

        for (int i = 1; i <= length; i++) {
            asc = (int) str.charAt(i - 1);
            if (asc > 127) {
                if (byteLength >= tempSize + 2) {
                    tempSize += 2;
                    retLength++;
                } else {
                    return str.substring(0, retLength) + "...";
                }
            } else {
                if (byteLength > tempSize) {
                    tempSize++;
                    retLength++;
                }
            }
        }

        return str.substring(0, retLength);
    }
}

