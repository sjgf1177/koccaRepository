
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
 * <p>제목: String 관리 라이브러리</p>
 * <p>설명: String 관리 라이브러리</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
 *@date 2003. 12
 *@version 1.0
 */
public class StringManager {

	/**
	 * Base64로 복호화
    @param str 전체 문자열
    @return Base64로 복호화된 문자열
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
	 * Base64로 암호화
    @param str 전체 문자열
    @return Base64로 암호화된 문자열
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
	 * null 체크
    @param str 전체 문자열
    @return str  null 인경우 "" 을, 아니면 원래의 문자열을 반환한다.
	 */
	public static String chkNull(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}

	/**
	내용중 HTML 툭수기호인 문자를 HTML 특수기호 형식으로 변환합니다.
	htmlstr		바꿀 대상인 문자열
	return		바뀐 결과
	PHP의 htmlspecialchars와 유사한 결과를 반환합니다.
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
	 * 제목을 보여줄때 제한된 길이를 초과하면 뒷부분을 짜르고 "..." 으로 대치한다.
    @param title(제목등의 문자열), max(최대길이)
    @return title(변경된 문자열)
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
	 * 제목을 보여줄때 제한된 길이를 초과하면 뒷부분을 짜르고 "..." 으로 대치한다.
    @param title(제목등의 문자열), max(최대길이)
    @return title(변경된 문자열)
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
	 * 문자열을 Byte 수 만큼 자르고 "..." 을 붙여 잘린 문자열임을 표시
    @param i_Source(제목등의 문자열), i_iLength(최대길이)
    @return result(변경된 문자열)
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
	 * SQL Query 문에서 value 값의 ' ' 를 만들어 주기 위한 메소드
    @param str   ' ' 안에 들어갈 변수 값
    @return   'str' 로 리턴됨
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
		int    v_levels      = 0;                        // 레벨
		int    v_seq         = 0;                        // seq
		String v_gubun       = "";                       // 구분 (대메뉴, 소메뉴, 모듈)
		String v_menu        = "";                       // 메뉴
		String v_menunm      = "";                       // 메뉴명
		String v_control_org = "";                       // 권한(읽기/쓰기)
		String v_rchecked    = "";                       // 읽기권한 체크
		String v_wchecked    = "";                       // 쓰기권한 체크

		String v_classcenter = "";                       // td class 지정(중앙정렬)
		//		String v_classleft   = "";                       // td class 지정(왼쪽정렬)
		String v_classright  = "";                       // td class 지정(오른쪽정렬)
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
		final String _MENU = "메뉴";
		final String _MODULE = "모듈";
		final String _BOLD = "<b>";
		final String _BOLD_CLOSE = "</b>";
		//메뉴 리스트
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

			// 권한 리스트
			if(v_gadmincnt > 0) {
				v_control_org = data2.getControl();
				StringTokenizer st = new StringTokenizer(v_control_org,"/");
				j = 0;

				// 어드민권한에 대한 각각의 읽기/쓰기권한값 배열에 저장
				while (st.hasMoreElements()) {
					v_control[j] = StringManager.chkNull(st.nextToken());
					j++;
				}

				// 위치 지정을 위한 구분값 (맨앞숫자 한자리) 제거
				for (j = 0;j<v_gadmincnt;j++){
					v_control[j] = rightstring(v_control[j], v_control[j].length()-1);
				}

				// 해당 권한 체크박스에 세팅
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
	 * 해당 문자열에서 older String 을 newer String 으로 교체한다.
    @param original 전체 String
    @param older 전체 String 중 교체 전 문자 String
    @param newer 전체 String 중 교체 후 문자 String
    @return result 교체된 문자열을 반환함
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
	 *java.lang.String 패키지의 substring() 메소드와 기능은 동일한데 오른쪽 문자끝부터 count 를 해서 자름
    @param str 전체 문자열
    @param count  오른쪽 문자끝(1) 부터 count 까지
    @return result  substring 된 문자열을 반환함
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
				result = str.substring(str.length()-count,str.length());  //  오른쪽 count 만큼 리턴
			}
		}
		catch (Exception ex) {
			throw new Exception("StringManager.rightstring(\""+str+"\","+count+")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 * java.lang.String 패키지의 substring() 메소드와 기능은 동일, null 체크만 함
    @param str 전체 문자열
    @param beginIndex
    @return result  substring 된 문자열을 반환함
	 */
	public static String substring(String str, int beginIndex) {
		String result = "";

		if(str != null) {
			result = str.substring(beginIndex);
		}

		return result;
	}

	/**
	 * java.lang.String 패키지의 substring() 메소드와 기능은 동일, null 체크만 함
    @param str 전체 문자열
    @param beginIndex
    @param endIndex
    @return result  substring 된 문자열을 반환함
	 */
	public static String substring(String str, int beginIndex, int endIndex) {
		String result = "";

		if(str != null) {
			result = str.substring(beginIndex, endIndex);
		}

		return result;
	}

	/**
	 * String 형을 int 형으로 변환, null 및 "" 체크
    @param str 전체 문자열
    @return null 및 "" 일 경우 0 반환
	 */
	public static int toInt(String str) {
		if (str == null || str.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	/**
	 * java.lang.String 패키지의 trim() 메소드와 기능은 동일, null 체크만 함
    @param str 전체 문자열
    @return result  trim 된 문자열을 반환함
	 */
	public static String trim(String str) throws Exception {
		String result = "";

		if(str != null) {
			result = str.trim();
		}

		return result;
	}
	/**
	 * URLEncoder 로 암호화
    @param str 전체 문자열
    @return URLEncoder로 복호화된 문자열
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

	// 문자열에서 <br>만 남기고 모든 HTML 태그 삭제
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
		i_sHTML	= replace(i_sHTML, "<BODY style=\"FONT-FAMILY: 굴림; FONT-SIZE: 10pt\">", "");
		i_sHTML	= replace(i_sHTML, "<BODY style='FONT-FAMILY: 굴림; FONT-SIZE: 10pt'>", "");
		i_sHTML	= replace(i_sHTML, "<P style='LINE-HEIGHT: 200%; MARGIN-TOP: 0px; MARGIN-BOTTOM: 0px' align=left>", "");
		i_sHTML	= replace(i_sHTML, "<html><head>", "");
		i_sHTML	= replace(i_sHTML, "<meta content='text/html; charset=euc-kr' http-equiv='Content-Type'>", "");
		i_sHTML	= replace(i_sHTML, "<meta name='GENERATOR' content='ActiveSquare'>", "");
		i_sHTML	= replace(i_sHTML, "</head><body>", "");
		i_sHTML	= replace(i_sHTML, "<title></title>", "");
		i_sHTML	= replace(i_sHTML, "</head><body style='font-family: 굴림; font-size: 10pt;'>", "");
		i_sHTML	= replace(i_sHTML, "</BODY></HTML>", "");
		i_sHTML	= replace(i_sHTML, "</body></html>", "");
		i_sHTML	= replace(i_sHTML, "<meta content=\"text/html; charset=euc-kr\" http-equiv=\"Content-Type\">", "");
		i_sHTML	= replace(i_sHTML, "<meta name=\"GENERATOR\" content=\"ActiveSquare\">", "");
		i_sHTML	= replace(i_sHTML, "</head><body style=\"font-family: 굴림; font-size: 10pt;\">", "");
		

		//i_sHTML	= replace(i_sHTML, "</BODY></HTML>", "");
		//result	= replace(i_sHTML, "P {margin-top:2px;margin-bottom:2px;}", "");
		//result	= replace(i_sHTML, "<HTML><HEAD><TITLE></TITLE> <META content='text/html; charset=euc-kr' http-equiv=Content-Type> <META name=GENERATOR content=ActiveSquare></HEAD> <BODY style='FONT-FAMILY: 굴림; FONT-SIZE: 10pt'>", "");
		
		//i_sHTML	= replace(i_sHTML, "<HTML><HEAD><TITLE></TITLE><META content=\"text/html; charset=euc-kr\" http-equiv=Content-Type><META name=GENERATOR content=ActiveSquare></HEAD><BODY style=\"FONT-FAMILY: 굴림; FONT-SIZE: 10pt\">", "");
		
		
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
	 * 내용중에서 html tag를 제거한다.
	 * @param content 원본 내용
	 * @return content tag가 제거된 내용
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
     * 문자열을 byte 단위로 자르는 기능을 수행한다.
     * 한글과 영문의 byte 단위가 다름으로 정확한 길의 문자열을 자르기 위함이다.  
     * @param str
     * @param byteLength
     * @return
     */
    public static String subStringBytes(String str, int byteLength) {
        // String 을 byte 길이 만큼 자르기.

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

