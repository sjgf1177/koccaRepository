package com.namo.active;

import java.io.*;
import java.util.Vector;

import javax.mail.internet.MimeUtility;
import javax.mail.MessagingException;

import com.credu.library.ConfigSet;
import com.namo.SmeNamoMime;

/**
 * ActiveSqaure �� �����Ǵ� MIMEValue�� ���ڵ��ϴ� Ŭ����.
 * ���ڵ� �����̸� ���ڵ��� �������� �ʴ´�. ���� �Ϲ����� MIME ���ڵ�
 * �����Ϳ� ȣȯ�� ���� �������� �ִ�.
 */
public class NamoMime
{
	/**
	 * MIME Data�� �� ��Ʈ
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
		�Ľ̿� ���Ǵ� ���°���
	*/
	final static class MIME
	{
		public static int BEGIN = 0;
		public static int HEADER = 1;
		public static int BODY = 2;
		public static int END = 3;

	};

	// multipart �ΰ�?
	private boolean multipart;
	// multipart �� ��� �ٿ������?
	private String boundary;
	// ���ڵ��� ��Ʈ��
	private Vector decodePart;
	// save Path
	private String savePath;
	// save URL
	private String saveURL;

	/**
	 * ÷�� ������ ������ ��ġ�� �����մϴ�.
	 */
	public void setSavePath(String path)
	{
		savePath = path;
	}

	/**
	 * ÷�� ������ �＼���Ҽ� �ִ� URL�� �����մϴ�.
	 */
	public void setSaveURL(String url)
	{
		saveURL = url;
	}

	/**
	 * MIME �������� ������ �˻��Ѵ�.
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
	 * �ϳ��� MIME Part�� ���, ���� ���� �и��Ѵ�.
	 */
	protected void splitSinglePart(String encodedString) throws IOException
	{
		System.out.println("splitSinglePart : begin");
		BufferedReader br = new BufferedReader(new StringReader(encodedString));		// ���پ� �б� ���ؼ� ��Ʈ���� �Ҵ�
		String line = null;																// ������
		String compare = null;
		String body = new String();
		MimePart part = new MimePart();
		int nowState = MIME.BEGIN;

		while(((line = br.readLine()) != null) && (nowState != MIME.END))							// ���پ� �о� ���δ�
		{
			line = line.trim();
			if(line.length() == 0 && nowState == MIME.BEGIN)										// ���ۺκп� �ִ� ���� ����
				continue;
			else
			{
				if(nowState == MIME.BEGIN)															// ���� ��Ȳ�� �����̾����� ������·�
					nowState = MIME.HEADER;

				compare = line.toLowerCase();														// ��� �ҹ��ڷ� ����
				if(nowState == MIME.HEADER)
				{
					System.out.println("Header");
					System.out.println(compare);
					if(compare.indexOf("mime-version") != -1)										// MIME Version�� �������� �ʴ´�
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
					else if(compare.length() == 0)													// ��� ���¿��� ������ ���� �������·� ��ȯ ^__^
					{
						nowState = MIME.BODY;
					}
				}
				else if(nowState == MIME.BODY)														// MIME ����
				{
	
					if(line.length() == 0 && multipart)												// ���� ��
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
		part.setBodypart(body);																		// MIME ���� ����
		decodePart.addElement(part);
	}
	// MultiPart �� ��� ������
	protected void splitMultiPart(String encodedString) throws IOException
	{
		String part = null;
		int start = 0;
		int pos = 0;
		
		while(true)
		{
			pos = encodedString.indexOf("--" + boundary, start);													// �ٿ������ ã�´�
			if(pos == -1)																			// ���� �ٿ������ ���� ��� �н�
				break;
			start = encodedString.indexOf("--" + boundary, pos + boundary.length() + 2);
			if(start == -1)
				break;
			part = encodedString.substring(pos + boundary.length() + 2, start);
			splitSinglePart(part);
		}
	}

	// MIME �� part ���� ������, ������ �м��Ѵ�
	protected void splitMimePart(String encodedString) throws IOException
	{
		if(this.multipart)						// ��Ƽ ��Ʈ�ΰ��
		{
			splitMultiPart(encodedString);
		}
		else									// �̱� ��Ʈ�ΰ��
		{
			splitSinglePart(encodedString);
		}
	}

	/**
	 * MIME�� ���ڵ��Ѵ�.
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
	 * ���� ���ڿ����� Ư�� ���ڿ��� ã�Ƽ� �� ���ڿ��� ġȯ�Ѵ�.
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
	 * ÷�ε� ������ ���� ��� CID ��ũ ������ ������ ���� �̸����� �����Ѵ�.
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
	 * HTML���� �Ǵ� TEXT �޽��� ������ ��ȯ�Ѵ�.
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

		if(part.getEncoding() == null)																	// ���ڵ� Ÿ���� �����Ƿ� ���ڵ� ���� ����
			return part.getBodypart();
		
		try
		{
			InputStream is = new ByteArrayInputStream(part.getBodypart().getBytes("iso-8859-1"));		// String �� ����Ʈ�� �ٲ㼭 �Է� ��Ʈ������ �ٲ۴�. �̶� ���ڵ� Ÿ���� iso-8859-1�� �����Ѵ�
			try
			{
				is = MimeUtility.decode(is, part.getEncoding());										// ���ڵ�
			}
			catch(MessagingException me)
			{
				throw new MimeDecodeException("Cannot Decoding");
			}

			decodeByte = new byte[is.available() + 1];													// ����Ʈ�迭�� �о� ���δ�
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
		}															// ��Ʈ������ �ٲ۴�
		
		if(multipart)
		{
			decodeText = changeCIDPath(decodeText);
		}
		return decodeText;
	}

	/**
	 * MIME�� ÷�ε� ������ �����Ѵ�. �ݵ�� decode()�޽�带 ���� ���ؾ� �Ѵ�.
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
			// Mime file �� �����´�
			try
			{
				is = new ByteArrayInputStream(part.getBodypart().getBytes("iso-8859-1"));				// String�� byte array�� �ٲ۴�
				is = MimeUtility.decode(is, part.getEncoding());										// MIME Decoding�� �Ѵ�
				fileContent = new byte [is.available() + 1];											// byte �迭�� ���ڵ� ������ �����´�
				is.read(fileContent);
				fileName = getWritableFileName(part.getName());
				part.setName(fileName);
				outFile = new File(getWritableFileName(savePath + part.getName()));									// ������ ���� �����Ѵ�
				os = new FileOutputStream(outFile);														// ���Ͽ� �����Ѵ�
				os.write(fileContent);
				os.close();																				// ��Ʈ���� �ݴ´�
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
	 * ������ ������ ���� �̸��� ���Ѵ�. 
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
	 * MIME Decoding �׽�Ʈ�� main �Լ�
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
			test.decode(strContent);	// saveFile�� �ϱ����� decode�� ���� �����ؾ� �Ѵ�.
			test.saveFile();
			System.out.println(test.getBodyContent());
		}
		catch(IOException ioe)
		{
			return ;
		}
	}
	
	/**
	 * MIME ������ �������� �������ݴϴ�. 
	 * @param v_content
	 * @return
	 */
	static public Object setNamoContent(String v_content){
		// ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
		ConfigSet conf 		= null; 
		NamoMime mime 		= new NamoMime();

		try{
			conf 			= new ConfigSet();
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
		
		SmeNamoMime namo 	= new SmeNamoMime(v_content); // ��ü����
		 
		boolean result 		= namo.parse(); // ���� �Ľ� ����
		
		
		if ( !result ) { // �Ľ� ���н�
			System.out.println(namo.getDebugMsg() ); // ����� �޽��� ���
			return result;
		}
		
		try {
			
			mime.decode(v_content);                          			// MIME ���ڵ�
			
			if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�

				String fPath 	= conf.getProperty("dir.namo");   				// ���� ���� ��� ����
				String v_server = conf.getProperty("kocca.url.value");		// ���� ��Ʈ ���
				String refUrl 	= conf.getProperty("url.namo"); 				// ������ ����� ������ �����ϱ� ���� ���
				
			    mime.setSaveURL(v_server+refUrl);
			    
			    mime.setSavePath(fPath);
			    
			    mime.saveFile();                            				// ������ ���� �����ϱ�
			
			}
	    	
	 	    //v_content 	= mime.getBodyContent().replace("'", "\\'"); 		// ���� ����Ʈ ���
	 	   v_content 	= mime.getBodyContent(); 		// ���� ����Ʈ ���
	 	    
	    } catch (Exception e) {
	    	System.out.println(e.toString());
	 	   return false;
	    }
		
		if ( !result ) { // �������� ���н�
			System.out.println(namo.getDebugMsg() ); // ����� �޽��� ���
			return v_content;
		}
		
		return v_content;
	}
}
