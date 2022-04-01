package com.namo.active;

import java.io.*;
import java.util.Vector;

import javax.mail.internet.MimeUtility;
import javax.mail.MessagingException;

import com.credu.library.ConfigSet;
import com.namo.SmeNamoMime;

/**
 * ActiveSqaure 서 생성되는 MIMEValue를 디코딩하는 클래스.
 * 디코딩 전용이며 인코딩은 지원하지 않는다. 또한 일반적인 MIME 인코딩
 * 데이터와 호환이 되지 않을수도 있다.
 */
public class NamoMime
{
	/**
	 * MIME Data의 한 파트
	 */
	class MimePart
	{
		private String bodypart;
		private String contentType;
		private String contentID;
		private String encoding;
		private String name;

		public void setName(String name)
		{
			String convstr = CodeConverter.getMIMEEncodedString(name);
			if(convstr != null)
				this.name = convstr;
			else
				this.name = name;	
		}

		public String getName()
		{
			return name;
		}

		public void setBodypart(String part)
		{
			bodypart = part;
		}

		public String getBodypart()
		{
			return bodypart;
		}

		public void setContentType(String contentType)
		{
			this.contentType = contentType;
		}

		public String getContentType()
		{
			return contentType;
		}

		public void setContentID(String contentID)
		{
			this.contentID = contentID;
		}

		public String getContentID()
		{
			return contentID;
		}

		public void setEncoding(String encoding)
		{
			this.encoding = encoding;
		}

		public String getEncoding()
		{
			return encoding;
		}
	}

	/**
		파싱에 사용되는 상태값들
	*/
	final static class MIME
	{
		public static int BEGIN = 0;
		public static int HEADER = 1;
		public static int BODY = 2;
		public static int END = 3;

	};

	// multipart 인가?
	private boolean multipart;
	// multipart 인 경우 바운더리는?
	private String boundary;
	// 디코드할 파트들
	private Vector decodePart;
	// save Path
	private String savePath;
	// save URL
	private String saveURL;

	/**
	 * 첨부 파일을 저장할 위치를 지정합니다.
	 */
	public void setSavePath(String path)
	{
		savePath = path;
	}

	/**
	 * 첨부 파일을 억세스할수 있는 URL을 지정합니다.
	 */
	public void setSaveURL(String url)
	{
		saveURL = url;
	}

	/**
	 * MIME 데이터의 형식을 검사한다.
	 */
	protected void checkMimeType(String encodedString) throws MimeDecodeException, IOException
	{
		String line, data;
		int pos = 0;
		BufferedReader br = new BufferedReader(new StringReader(encodedString));

		data = null;

		while(true)
		{
			line = br.readLine();
			line.trim();
			if(line == null || line.length() <= 0)
				break;
			data += line;
		}
		if(data.length() <= 0)
			throw new MimeDecodeException("Not found MIME Header");
		line = data.toLowerCase();
		pos = line.indexOf("content-type");							// find content-type
		if(pos == -1)
			throw new MimeDecodeException("Not found content-type");
		pos = line.indexOf("multipart", pos + 1);					// find multipart directive
		if(pos != -1)
			this.multipart = true;
		else
			this.multipart = false;
		pos = line.indexOf("boundary", pos + 1);					// find boundary
		if(pos == -1 && this.multipart == true)
			throw new MimeDecodeException("Not found boundary");
		pos = data.indexOf("\"", pos + 1);							// find boundary data
		if(pos == -1 && this.multipart == true)
			throw new MimeDecodeException("Not found boundary");
		if(this.multipart == true)
			this.boundary = data.substring(pos + 1, data.indexOf("\"", pos + 1));
	}

	/**
	 * 하나의 MIME Part를 헤더, 본문 으로 분리한다.
	 */
	protected void splitSinglePart(String encodedString) throws IOException
	{
		System.out.println("splitSinglePart : begin");
		BufferedReader br = new BufferedReader(new StringReader(encodedString));		// 한줄씩 읽기 위해서 스트림을 할당
		String line = null;																// 현재줄
		String compare = null;
		String body = new String();
		MimePart part = new MimePart();
		int nowState = MIME.BEGIN;

		while(((line = br.readLine()) != null) && (nowState != MIME.END))							// 한줄씩 읽어 들인다
		{
			line = line.trim();
			if(line.length() == 0 && nowState == MIME.BEGIN)										// 시작부분에 있는 공백 무시
				continue;
			else
			{
				if(nowState == MIME.BEGIN)															// 이전 상황에 시작이었으면 헤더상태로
					nowState = MIME.HEADER;

				compare = line.toLowerCase();														// 모두 소문자로 변경
				if(nowState == MIME.HEADER)
				{
					System.out.println("Header");
					System.out.println(compare);
					if(compare.indexOf("mime-version") != -1)										// MIME Version은 저장하지 않는다
					{
						continue;
					}
					else if(compare.indexOf("content-type") != -1)									// content type
					{
						part.setContentType(compare.substring(compare.indexOf(":") + 1).trim());
					}
					else if(compare.indexOf("content-transfer-encoding") != -1)						// encoding type
					{
						part.setEncoding(line.substring(line.indexOf(":") + 1).trim());
					}
					else if(compare.indexOf("content-id") != -1)									// CID
					{
						compare = line.substring(line.indexOf("<") + 1, line.indexOf(">"));
						part.setContentID(compare);
					}
					else if(compare.indexOf("name") != -1)											// File name
					{
						compare = line.substring(line.indexOf("\"") + 1, line.length() - 1);
						part.setName(compare);

					}
					else if(compare.length() == 0)													// 헤더 상태에서 공백이 오면 본문상태로 전환 ^__^
					{
						nowState = MIME.BODY;
					}
				}
				else if(nowState == MIME.BODY)														// MIME 본문
				{
	
					if(line.length() == 0 && multipart)												// 본문 끝
						nowState = MIME.END;
					else
					{
						body = body + line;
						break;
					}
				}
			}
		}
		if(nowState == MIME.BODY)
		{
			if(line != null)
			{
				body = encodedString.substring(encodedString.indexOf(line));
			}
		}
		part.setBodypart(body);																		// MIME 본문 셋팅
		decodePart.addElement(part);
	}
	// MultiPart 인 경우 나눈다
	protected void splitMultiPart(String encodedString) throws IOException
	{
		String part = null;
		int start = 0;
		int pos = 0;
		
		while(true)
		{
			pos = encodedString.indexOf("--" + boundary, start);													// 바운더리를 찾는다
			if(pos == -1)																			// 다음 바운더리가 없는 경우 패스
				break;
			start = encodedString.indexOf("--" + boundary, pos + boundary.length() + 2);
			if(start == -1)
				break;
			part = encodedString.substring(pos + boundary.length() + 2, start);
			splitSinglePart(part);
		}
	}

	// MIME 을 part 별로 나누고, 정보를 분석한다
	protected void splitMimePart(String encodedString) throws IOException
	{
		if(this.multipart)						// 멀티 파트인경우
		{
			splitMultiPart(encodedString);
		}
		else									// 싱글 파트인경우
		{
			splitSinglePart(encodedString);
		}
	}

	/**
	 * MIME을 디코딩한다.
	 */
	public boolean decode(String encodedString) throws MimeDecodeException, IOException
	{
		int i = 0;
		MimePart part;
		decodePart = null;
		decodePart = new Vector();
		checkMimeType(encodedString);
		splitMimePart(encodedString);

		return true;
	}

	/**
	 * 원본 문자열에서 특정 문자열을 찾아서 새 문자열로 치환한다.
	 */
	public String replace(String original, String oldstr, String newstr)
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
	 * 첨부된 파일이 있을 경우 CID 링크 내용을 저장한 파일 이름으로 변경한다.
	 */
	protected String changeCIDPath(String content)
	{
		int i = 0;
		String convert;
		MimePart part;
		convert = content;

		for( i = 1; i < decodePart.size(); i++)
		{
			part = (MimePart)decodePart.elementAt(i);
			if(part.getContentID() != null)
			{
				if(saveURL == null && saveURL.length() <= 0)
					convert = replace(convert, "cid:" + part.getContentID(), part.getName());
				else
					convert = replace(convert, "cid:" + part.getContentID(), saveURL + "/" + part.getName());
			}
		}

		return convert;
	}

	/**
	 * HTML본문 또는 TEXT 메시지 내용을 반환한다.
	 */
	public String getBodyContent() throws MimeDecodeException
	{
		MimePart part;
		byte [] decodeByte;
		String decodeText = null;

		if(decodePart.size() <= 0)
			return null;

		part = (MimePart)decodePart.elementAt(0);
		decodeText = part.getBodypart();

		if(part.getEncoding() == null)																	// 인코딩 타입이 없으므로 인코딩 하지 말것
			return part.getBodypart();
		
		try
		{
			InputStream is = new ByteArrayInputStream(part.getBodypart().getBytes("iso-8859-1"));		// String 을 바이트로 바꿔서 입력 스트림으로 바꾼다. 이때 인코딩 타입을 iso-8859-1로 셋팅한다
			try
			{
				is = MimeUtility.decode(is, part.getEncoding());										// 디코딩
			}
			catch(MessagingException me)
			{
				throw new MimeDecodeException("Cannot Decoding");
			}

			decodeByte = new byte[is.available() + 1];													// 바이트배열로 읽어 들인다
			is.read(decodeByte);
		}
		catch(IOException ioe)
		{
			throw new MimeDecodeException("Cannot create input stream");
		}
		
		try {
			decodeText = new String(decodeByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}															// 스트링으로 바꾼다
		
		if(multipart)
		{
			decodeText = changeCIDPath(decodeText);
		}
		return decodeText;
	}

	/**
	 * MIME에 첨부된 파일을 저장한다. 반드시 decode()메쏘드를 먼저 콜해야 한다.
	 */
	public void saveFile() throws MimeDecodeException
	{
		File outFile;
		OutputStream os;
		InputStream is;
		MimePart part;
		String fileName;
		byte [] fileContent;
		int i;

		for(i = 1; i < decodePart.size(); i++)
		{
			outFile = null;
			os = null;
			is = null;
			fileContent = null;


			part = (MimePart)decodePart.elementAt(i);

			// Test
			// Mime file 을 가져온다
			try
			{
				is = new ByteArrayInputStream(part.getBodypart().getBytes("iso-8859-1"));				// String을 byte array로 바꾼다
				is = MimeUtility.decode(is, part.getEncoding());										// MIME Decoding을 한다
				fileContent = new byte [is.available() + 1];											// byte 배열로 디코딩 내용을 가져온다
				is.read(fileContent);
				fileName = getWritableFileName(part.getName());
				part.setName(fileName);
				outFile = new File(getWritableFileName(savePath + part.getName()));									// 파일을 새로 생성한다
				os = new FileOutputStream(outFile);														// 파일에 저장한다
				os.write(fileContent);
				os.close();																				// 스트림을 닫는다
			}
			catch(FileNotFoundException fnfe)
			{
				System.out.println(fnfe.toString());
				throw new MimeDecodeException("Cannot create file");
			}
			catch(IOException ioe)
			{
				throw new MimeDecodeException("Cannot write file");
			}
			catch(MessagingException me)
			{
				throw new MimeDecodeException("Cannot decode file");
			}
		}
	}

	/**
	 * 저장이 가능한 파일 이름을 정한다. 
	 */
	public String getWritableFileName(String fileName)
	{
		String writableName = fileName;
		String name = null;
		String ext = null;
		File writeFile = new File(savePath + "/" + writableName);
		int i = 0;

		name = fileName.substring(0, fileName.lastIndexOf('.'));
		ext = fileName.substring(fileName.lastIndexOf('.'));

		System.out.println("getWritableFileName entered");
		System.out.println("name : " + name);
		System.out.println("ext : " + ext);

		while(writeFile.exists() == true)
		{
			writableName = name + "[" + Integer.toString(i) + "]" + ext;
			writeFile = null;
			writeFile = new File(savePath + "/" + writableName);
			System.out.println(writableName);
			i++;
		}
		return writableName;
	}

	/**
	 * Default Contructor
	 */
	public void NamoMime()
	{
		multipart = false;
		boundary = null;
	}

	/**
	 * MIME Decoding 테스트용 main 함수
	 */
	static public void main(String []argv) throws MimeDecodeException
	{
		NamoMime test = new NamoMime();
		byte [] content;
		String strContent = null;
		try
		{
			InputStream is = new FileInputStream("D:/test/mime.txt");
			content = new byte[is.available() + 1];
			is.read(content);
		}
		catch(FileNotFoundException fnfe)
		{
			return;
		}
		catch(IOException ioe)
		{
			return;
		}

		strContent = new String(content);
		try
		{
			test.setSaveURL("http://www.joins.com");
			test.setSavePath("d:/test");
			test.decode(strContent);	// saveFile을 하기전에 decode를 먼저 실행해야 한다.
			test.saveFile();
			System.out.println(test.getBodyContent());
		}
		catch(IOException ioe)
		{
			return ;
		}
	}
	
	/**
	 * MIME 형식의 컨텐츠를 변경해줍니다. 
	 * @param v_content
	 * @return
	 */
	static public Object setNamoContent(String v_content){
		// 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
		ConfigSet conf 		= null; 
		NamoMime mime 		= new NamoMime();

		try{
			conf 			= new ConfigSet();
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
		
		SmeNamoMime namo 	= new SmeNamoMime(v_content); // 객체생성
		 
		boolean result 		= namo.parse(); // 실제 파싱 수행
		
		
		if ( !result ) { // 파싱 실패시
			System.out.println(namo.getDebugMsg() ); // 디버깅 메시지 출력
			return result;
		}
		
		try {
			
			mime.decode(v_content);                          			// MIME 디코딩
			
			if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단

				String fPath 	= conf.getProperty("dir.namo");   				// 파일 저장 경로 지정
				String v_server = conf.getProperty("kocca.url.value");		// 서버 루트 경로
				String refUrl 	= conf.getProperty("url.namo"); 				// 웹에서 저장된 파일을 접근하기 위한 경로
				
			    mime.setSaveURL(v_server+refUrl);
			    
			    mime.setSavePath(fPath);
			    
			    mime.saveFile();                            				// 포함한 파일 저장하기
			
			}
	    	
	 	    //v_content 	= mime.getBodyContent().replace("'", "\\'"); 		// 최종 컨텐트 얻기
	 	   v_content 	= mime.getBodyContent(); 		// 최종 컨텐트 얻기
	 	    
	    } catch (Exception e) {
	    	System.out.println(e.toString());
	 	   return false;
	    }
		
		if ( !result ) { // 파일저장 실패시
			System.out.println(namo.getDebugMsg() ); // 디버깅 메시지 출력
			return v_content;
		}
		
		return v_content;
	}
}
