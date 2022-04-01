package com.credu.scorm;

import java.text.*;
import java.util.*;
import java.lang.*;
//import java.io.*;
import java.net.*;

public class AttachFileUtil{
	public AttachFileUtil(){}

	public static String fileSize(String fsize){		// File Size(Byte)�� �Է¹޾� ����(MB/KB/Byte)���� ���ڿ� ����
		double filesize;
		String strSize = new String();

		if(fsize==null || fsize.equals("")){
			filesize = 0;
		}else{
			filesize = Double.parseDouble(fsize);
		}

		if(filesize > 1024000){
			strSize = Math.round((filesize/1024000.00)*1000)/1000.00 + "MB";
		}else if(filesize > 1024){
			strSize = Math.round((filesize/1024.00)*10)/10.00 + "KB";
		}else if(filesize > 0){
			strSize = Math.round(filesize) + "Byte";
		}else{
			strSize = filesize + "Byte";
		}

		return strSize;
	}

	public static String fileIcon(String fname){		// ���ϸ��� �޾� �� Ȯ���ڿ� �´� �����ܸ��� ���ڿ��� ����
		int dot_idx;
		int icon_idx = -1;
		String file_exe		= new String();
		String file_name		= new String();

		String chk_file_list[]	= new String[7];
		String icon_list[]		= new String[7];

		chk_file_list[0]		= "MID,EXE,HWP,DOC,COMP,ZIP,RAR,TXT,WAV,XLS,BMP,PPT,ALZ";
		chk_file_list[1]		= "RA,RAM";
		chk_file_list[2]		= "GIF";
		chk_file_list[3]		= "HTM,HTML";
		chk_file_list[4]		= "JPG,JPEG";
		chk_file_list[5]		= "MPG,AVI,MPEG";

		icon_list[0]			= "";
		icon_list[1]			= "ra.gif";
		icon_list[2]			= "gif.gif";
		icon_list[3]			= "htm.gif";
		icon_list[4]			= "jpg.gif";
		icon_list[5]			= "mpeg.gif";
		icon_list[6]			= "unknown.gif";

		if(fname==null || fname.equals("")){
			return "";
		}else{
			dot_idx	= fname.lastIndexOf(".");
			file_exe	= fname.substring(dot_idx+1,fname.length());
			file_name	= fname.substring(0,dot_idx);		// .���� ������ -> ���ϸ�
			for(int i=0; i<chk_file_list.length; i++){
				if(chk_file_list[i].indexOf(file_exe.toUpperCase()) > -1){
					icon_idx = i;
					break;
				}
				if (i == 5)
				{
					icon_idx = -1;
					break;
				}
			}
			if(icon_idx == -1){
				return icon_list[6];
			}else if(icon_idx == 0){
				return (file_exe + ".gif");
			}else{
				return icon_list[icon_idx];
			}
		}
	}

	public static String fileEXE(String FileName){
			int intFileIndex = FileName.lastIndexOf(".");
			String strFileEXE = FileName.substring(intFileIndex+1).toUpperCase();
				
			return strFileEXE;
	}


	public static String encode(String inputStr, int eSize){	// ���ϰ�� ��ȣȭ��
		String result = "";
		if(eSize < 3) eSize = 3;

		inputStr = inputStr.trim();
		for(int i = 0; i < inputStr.length(); i++){
			int val = inputStr.charAt(i) + i * 29;
			result = result + StringUtil.lpad(String.valueOf(val), "0", eSize);
		}

		return result;
	}

	public static String decode(String inputStr, int dSize){	// ���ϰ�� ��ȣȭ��
		String result = "";
		if(dSize < 3) dSize = 3;

		inputStr = inputStr.trim();
		for(int i = 0; i < inputStr.length() / dSize; i++){
			int num = Integer.parseInt(inputStr.substring(i * dSize, i * dSize + dSize)) - i * 29;
			result = result + (char)num;
		}

		return result;
	}

	public static String fileLinkURL(String fname, String fpath, String str_Down){	// ��ũ ���� ��� ����� (����/��ξ�ȣȭ)
		String str_url = new String();

		if(fname == null) fname = "";
		if(fpath == null) fpath = "";
	//	fname		= java.net.URLEncoder.encode(fname);
	//	fpath			= URLEncoder.encode(fpath);
	//	fname		= encode(fname, 4);
		fpath			= encode(fpath, 4);
//		str_url		= "http://ecamp.gcgc.ac.kr:8080/servlet/servlet.Download?DownType="+ str_Down +"&FileName="+ fname +"&FilePath="+ fpath;
		str_url		= "/servlet/servlet.Download?DownType="+ str_Down +"&FileName="+ fname +"&FilePath="+ fpath;
//		str_url		= "/common/com_download.jsp?DownType="+ str_Down +"&FileName="+ fname +"&FilePath="+ fpath;

		return str_url;
	}

	public static String getFileDownType(String fname){				// ������ Ȯ���ڿ� ���� �������� ���� �ִ��� ���� üũ1
		String strExeList		= "HTML,HTM,DOC,XLS,PPT,TXT,GIF,BMP,JPG,JPEG,MPG,MPEG,MPE,AVI,SWF";
		String return_type	= "0";	 // '0' : ������, '1' : �ٿ�ε�

		return_type			= getFileDownType(fname, strExeList);
		return return_type;
	}

	public static String getFileDownType(String fname, String strExeList){	// ������ Ȯ���ڿ� ���� �������� ���� �ִ��� ���� üũ2
		String strFileEXE		= fileEXE(fname);
		String return_type	= "0";	 // '0' : ������, '1' : �ٿ�ε�

		if(strExeList.indexOf(strFileEXE) > -1){	// �������� ����
			return_type		= "0";
		}else{	// �ٿ�ε� �޾ƾ� ��
			return_type		= "1";
		}

		return return_type;
	}

	public static String makFileLink(String fname, String linkpath, String linkname, String str_Down){	 // Ȯ���ں� �Ǵ� ������ �ٿ�ε�
		String str_link	= "";

		if(str_Down.equals("1")){	// ������ �ٿ�ε�
		//	str_link	= "<a href='#alexit' title='"+fname+"' onclick=\"window.open('"+ linkpath +"', 'DownWin', 'width=200,height=200,toolbars=no,scrollbars=yes,resizable=yes');\">"+ linkname +"</a>";
			str_link	= "<a href='"+ linkpath +"' title='"+fname+"'>"+ linkname +"</a>";
		}else{
			String strFileEXE		= fileEXE(fname);
			String strTMPType	= "";

			/* ��Ƽ��Ʃ��(arf, atl, apk, apx), ��Ű��(adf)... */
			if(strFileEXE.equals("ARF") || strFileEXE.equals("ATL") || strFileEXE.equals("APK") || strFileEXE.equals("APX")) { 
			//	str_link = "<a href='"+ linkpath +"' title='"+ fname +"' target='_blank'>"+ linkname +"</a>";
				str_link	= "<a href=\"#alexit\" onclick=\"window.open('"+ linkpath +"', 'DownWin', 'toolbars=no,scrollbars=yes,resizable=yes');\" title=\""+ fname +"\">"+ linkname +"</a>";
			}else{
				strTMPType	 = getFileDownType(fname);
				if(strTMPType.equals("0")){	// ��������
				//	str_link = "<a href='"+ linkpath +"' title='"+ fname +"' target='_blank'>"+ linkname +"</a>";
					str_link	= "<a href=\"#alexit\" onclick=\"window.open('"+ linkpath +"', 'DownWin', 'toolbars=no,scrollbars=yes,resizable=yes');\" title=\""+ fname +"\">"+ linkname +"</a>";
				}else if(strTMPType.equals("1")){	// �ٿ�ε�� (DownType���� ������ �ٿ�ε�'1'�� ������ �� ��ũ �ɱ�)
					linkpath	= linkpath.replaceAll("DownType=0", "DownType=1");

					str_link	= "<a href='"+ linkpath +"' title='"+fname+"'>"+ linkname +"</a>";
				}else{
				//	str_link = "<a href='"+ linkpath +"' title='"+ fname +"' target='_blank'>"+ linkname +"</a>";
					str_link	= "<a href=\"#alexit\" onclick=\"window.open('"+ linkpath +"', 'DownWin', 'toolbars=no,scrollbars=yes,resizable=yes');\" title=\""+ fname +"\">"+ linkname +"</a>";
				}
			}
		}

		return str_link;
	}




	/*
	���ϸ����� ������ ������ ���Ͽ� �����ܿ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	*/
	public static String iconFileLink(String fname, String fpath){
		String linkpath	= fileLinkURL(fname, fpath, "0");
		return iconFileLink(fname, fpath, linkpath, "0");
	}

	/*
	���ϸ����� ������ ������ ���Ͽ� �����ܿ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����2
	*/
	public static String iconFileLink(String fname, String fpath, String str_Down){
		String linkpath	= fileLinkURL(fname, fpath, str_Down);
		return iconFileLink(fname, fpath, linkpath, str_Down);
	}

	
	/*
	���ϸ����� ������ ������ ���Ͽ� �����ܿ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	������ �ٿ�ε� ������� ���� ('0' : Ȯ���ڿ� ����, '1' : ������ �ٿ�ε�)
	*/
	public static String iconFileLink(String fname, String fpath, String linkpath, String str_Down){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link	= "";

		try{
			if(fname==null || fname.equals("")){
			//	str_link		= "<img src='/images/fileicon/text.gif' alt='÷�ξ���' align='absmiddle'>";
				str_link		= "-";
			}else{
				String strIcon	= "disk.gif";	 // fileIcon(fname) --> Ȯ���ں� ������ �̹��� ���Ͽ� ����� ���� ����
				str_link		= makFileLink(fname, linkpath, "<img src=\'/images/fileicon/" + strIcon +"' border=0 align='absmiddle'>", str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}


	/*
	���ϸ����� ������ ������ ���Ͽ�, ������+���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	*/
	public static String fileLink(String fname, String fpath){
		String linkpath	= fileLinkURL(fname, fpath, "0");
		return fileLink(fname, fpath, linkpath, "0");
	}

	/*
	���ϸ����� ������ ������ ���Ͽ�, ������+���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����2
	*/
	public static String fileLink(String fname, String fpath, String str_Down){
		String linkpath	= fileLinkURL(fname, fpath, str_Down);
		return fileLink(fname, fpath, linkpath, str_Down);
	}

	/*
	���ϸ����� ������ ������ ���Ͽ�, ������+���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	������ �ٿ�ε� ������� ���� ('0' : Ȯ���ڿ� ����, '1' : ������ �ٿ�ε�)
	*/
	public static String fileLink(String fname, String fpath, String linkpath, String str_Down){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>���Ͼ���</font>";
			}else{
				String strIcon	= "disk.gif";	 // fileIcon(fname) --> Ȯ���ں� ������ �̹��� ���Ͽ� ����� ���� ����
				str_link		= makFileLink(fname, linkpath, "<img src=\'/images/fileicon/" + strIcon +"' border=0 align='absmiddle'>&nbsp;"+ fname, str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}

	/*
	���ϸ����� ������ ������ ���Ͽ�, ������+���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	������ �ٿ�ε� ������� ���� ('0' : Ȯ���ڿ� ����, '1' : ������ �ٿ�ε�)
	��ũ�� �ɸ� ���ϸ��� ���ڿ� ���� ���� ��� (0 �̻�)
	*/
	public static String fileLink(String fname, String fpath, String str_Down, int int_nameLen){
		String linkpath		= fileLinkURL(fname, fpath, str_Down);
		return fileLink(fname, fpath, linkpath, str_Down, int_nameLen);
	}

	/*
	���ϸ����� ������ ������ ���Ͽ�, ������+���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����2
	������ �ٿ�ε� ������� ���� ('0' : Ȯ���ڿ� ����, '1' : ������ �ٿ�ε�)
	��ũ�� �ɸ� ���ϸ��� ���ڿ� ���� ���� ��� (0 �̻�)
	*/
	public static String fileLink(String fname, String fpath, String linkpath, String str_Down, int int_nameLen){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link		= "";
		String strLinkName	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>���Ͼ���</font>";
			}else{
				String strIcon	= "disk.gif";	 // fileIcon(fname) --> Ȯ���ں� ������ �̹��� ���Ͽ� ����� ���� ����

				if(int_nameLen < 1 || int_nameLen > fname.length()){
					int_nameLen	= fname.length();
					strLinkName		= fname;
				}else{
					strLinkName		= fname.substring(0, int_nameLen)+"...";
				}

				str_link		= makFileLink(fname, linkpath, "<img src=\'/images/fileicon/" + strIcon +"' border=0 align='absmiddle'>&nbsp;"+ strLinkName, str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}

	/*
	(������ ������) ���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	*/
	public static String fileLinkNoIcon(String fname, String fpath){
		String linkpath	= fileLinkURL(fname, fpath, "0");
		return fileLinkNoIcon(fname, fpath, linkpath, "0");
	}

	/*
	(������ ������) ���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����2
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String str_Down){
		String linkpath	= fileLinkURL(fname, fpath, str_Down);
		return fileLinkNoIcon(fname, fpath, linkpath, str_Down);
	}

	/*
	(������ ������) ���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	������ �ٿ�ε� ������� ���� ('0' : Ȯ���ڿ� ����, '1' : ������ �ٿ�ε�)
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String linkpath, String str_Down){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>���Ͼ���</font>";
			}else{
				str_link		= makFileLink(fname, linkpath, fname, str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}

	/*
	(������ ������) ���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����
	������ �ٿ�ε� ������� ���� ('0' : Ȯ���ڿ� ����, '1' : ������ �ٿ�ε�)
	��ũ�� �ɸ� ���ϸ��� ���ڿ� ���� ���� ��� (0 �̻�)
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String str_Down, int int_nameLen){
		String linkpath		= fileLinkURL(fname, fpath, str_Down);
		return fileLink(fname, fpath, linkpath, str_Down, int_nameLen);
	}

	/*
	(������ ������) ���ϸ� ������ �ٿ�ε� ���� �� �ִ� ��ũ���ڿ��� ����� ����2
	������ �ٿ�ε� ������� ���� ('0' : Ȯ���ڿ� ����, '1' : ������ �ٿ�ε�)
	��ũ�� �ɸ� ���ϸ��� ���ڿ� ���� ���� ��� (0 �̻�)
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String linkpath, String str_Down, int int_nameLen){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link		= "";
		String strLinkName	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>���Ͼ���</font>";
			}else{
				if(int_nameLen < 1 || int_nameLen > fname.length()){
					int_nameLen	= fname.length();
					strLinkName		= fname;
				}else{
					strLinkName		= fname.substring(0, int_nameLen)+"...";
				}

				str_link		= makFileLink(fname, linkpath, strLinkName, str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}

	/*
	���� ÷��/���� ��(html)�� ���ڿ��� �����Ͽ� �����Ѵ� (���ÿɼ�, ����÷��, ÷������ �� 3��(tr)�� ǥ����)
	- ��������(old_filename)	: <input type='hidden' name='old_filename'> 
	- ���ÿɼ�(old_fileopt)		: �� ���Ϻ��� �� ����� �����ϻ���
	- ����÷��(attach_file)		: <input type='file' name='attach_file'>
	- ÷������(��ũ)			: ÷�������� ������� �ٿ�ε� ������ ��ũ
	*/
	public static String fileAttachForm(String str_Formname, String fname, String fpath){
		String linkpath		= fileLinkURL(fname, fpath, "0");
		return fileAttachForm(str_Formname, fname, fpath, linkpath, "0");
	}

	public static String fileAttachForm(String str_Formname, String fname, String fpath, String str_Down){
		String linkpath		= fileLinkURL(fname, fpath, str_Down);
		return fileAttachForm(str_Formname, fname, fpath, linkpath, str_Down);
	}

	public static String fileAttachForm(String str_Formname, String fname, String fpath, String linkpath, String str_Down){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);

		String str_link		= new String();
		String return_Form	= new String();

		if(fname == null || fname.equals("")){
			fname = "";
			str_link			= "";
		}else{
			str_link			= fileLink(fname, fpath, str_Down);
		}

		if(str_Formname.equals("")) str_Formname = "frm";

		return_Form		= "<script language='javascript'>\n"
								+ "function Change_FileOption(idx){\n"
								+ "	 	if(idx == 0 || idx == 2){\n"
								+ "			document."+str_Formname+".attach_file.disabled = true;\n"
								+ "		}else if(idx == 1){\n"
								+ "			document."+str_Formname+".attach_file.disabled = false;\n"
								+ "		}\n"
								+ "}\n"
								+ "</script>\n"
								+ "\n"
								+ "<table border=0 cellspacing=0 cellpadding=0 width='100%'>\n";

		if(fname.equals("")){
			return_Form	+= "<input type='hidden' name='old_filename' value=''>\n"
								+ "<input type='hidden' name='old_fileopt' value='2'>\n"
								+ "<tr height='20'><td><input type='file' name='attach_file' class='form' style='width:100%'></td></tr>\n"
								+ "</table>\n";
		}else{
			return_Form	+= "<input type='hidden' name='old_filename' value=\""+ fname +"\">\n"
								+ "<tr height='20'>\n"
								+ "	<td>\n"
								+ "		<input type='radio' name='old_fileopt' value='1' onclick='Change_FileOption(0)' checked> ���Ϻ���\n"
								+ "		<input type='radio' name='old_fileopt' value='2' onclick='Change_FileOption(1)'> �����\n"
								+ "		<input type='radio' name='old_fileopt' value='3' onclick='Change_FileOption(2)'> ���ϻ���\n"
								+ "	</td>\n"
								+ "</tr>\n"
								+ "<tr height='20'><td><input type='file' name='attach_file' class='form' style='width:100%' disabled></td></tr>\n"
								+ "<tr height='20'><td>"+ str_link + "</td></tr>\n"
								+ "</table>\n";
		}

		return return_Form;
	}

	/*
	���� ÷��/���� ��(html)�� ���ڿ��� �����Ͽ� �����Ѵ� (���ÿɼ� + ����÷��, ÷������ �� 2��(tr)�� ǥ����)
	- ��������(old_filename)	: <input type='hidden' name='old_filename'> 
	- ���ÿɼ�(old_fileopt)		: �� ���Ϻ��� �� ����� �����ϻ���
	- ����÷��(attach_file)		: <input type='file' name='attach_file'>
	- ÷������(��ũ)			: ÷�������� ������� �ٿ�ε� ������ ��ũ
	*/
	public static String fileAttachForm(String str_Formname, String fname, String fpath, String str_Down, int int_nameLen){
		String linkpath		= fileLinkURL(fname, fpath, str_Down);
		return fileAttachForm(str_Formname, fname, fpath, linkpath, str_Down, int_nameLen);
	}

	public static String fileAttachForm(String str_Formname, String fname, String fpath, String linkpath, String str_Down, int int_nameLen){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link			= new String();
		String return_Form	= new String();

		if(fname == null || fname.equals("")){
			fname = "";
			str_link			= "";
		}else{
			str_link			= fileLink(fname, fpath, str_Down, int_nameLen);
		}

		if(str_Formname.equals("")) str_Formname = "frm";

		return_Form		= "<script language='javascript'>\n"
								+ "function Change_FileOption(idx){\n"
								+ "	 	if(idx == 0 || idx == 2){\n"
								+ "			document."+str_Formname+".attach_file.disabled = true;\n"
								+ "		}else if(idx == 1){\n"
								+ "			document."+str_Formname+".attach_file.disabled = false;\n"
								+ "		}\n"
								+ "}\n"
								+ "</script>\n"
								+ "\n"
								+ "<table border=0 cellspacing=0 cellpadding=0 width='100%'>\n";

		if(fname.equals("")){
			return_Form	+= "<input type='hidden' name='old_filename' value=''>\n"
								+ "<input type='hidden' name='old_fileopt' value='2'>\n"
								+ "<tr height='20'><td><input type='file' name='attach_file' class='form' style='width:100%'></td></tr>\n"
								+ "</table>\n";
		}else{
			return_Form	+= "<input type='hidden' name='old_filename' value=\""+ fname +"\">\n"
								+ "<tr height='20'>\n"
								+ "	<td>\n"
								+ "		<input type='radio' name='old_fileopt' value='1' onclick='Change_FileOption(0)' checked> ���Ϻ���\n"
								+ "		<input type='radio' name='old_fileopt' value='2' onclick='Change_FileOption(1)'> �����\n"
								+ "		<input type='radio' name='old_fileopt' value='3' onclick='Change_FileOption(2)'> ���ϻ���\n"
								+ "		&nbsp;"+ str_link + ""
								+ "	</td>\n"
								+ "</tr>\n"
								+ "<tr height='20'><td><input type='file' name='attach_file' class='form' style='width:100%' disabled></td></tr>\n"
								+ "</table>\n";
		}

		return return_Form;
	}
}