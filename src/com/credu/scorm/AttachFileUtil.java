package com.credu.scorm;

import java.text.*;
import java.util.*;
import java.lang.*;
//import java.io.*;
import java.net.*;

public class AttachFileUtil{
	public AttachFileUtil(){}

	public static String fileSize(String fsize){		// File Size(Byte)를 입력받아 단위(MB/KB/Byte)붙인 문자열 리턴
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

	public static String fileIcon(String fname){		// 파일명을 받아 각 확장자에 맞는 아이콘명을 문자열로 리턴
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
			file_name	= fname.substring(0,dot_idx);		// .문자 전까지 -> 파일명
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


	public static String encode(String inputStr, int eSize){	// 파일경로 암호화용
		String result = "";
		if(eSize < 3) eSize = 3;

		inputStr = inputStr.trim();
		for(int i = 0; i < inputStr.length(); i++){
			int val = inputStr.charAt(i) + i * 29;
			result = result + StringUtil.lpad(String.valueOf(val), "0", eSize);
		}

		return result;
	}

	public static String decode(String inputStr, int dSize){	// 파일경로 복호화용
		String result = "";
		if(dSize < 3) dSize = 3;

		inputStr = inputStr.trim();
		for(int i = 0; i < inputStr.length() / dSize; i++){
			int num = Integer.parseInt(inputStr.substring(i * dSize, i * dSize + dSize)) - i * 29;
			result = result + (char)num;
		}

		return result;
	}

	public static String fileLinkURL(String fname, String fpath, String str_Down){	// 링크 실제 경로 만들기 (서블릿/경로암호화)
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

	public static String getFileDownType(String fname){				// 파일의 확장자에 따라 브라우저로 열수 있는지 여부 체크1
		String strExeList		= "HTML,HTM,DOC,XLS,PPT,TXT,GIF,BMP,JPG,JPEG,MPG,MPEG,MPE,AVI,SWF";
		String return_type	= "0";	 // '0' : 브라우저, '1' : 다운로드

		return_type			= getFileDownType(fname, strExeList);
		return return_type;
	}

	public static String getFileDownType(String fname, String strExeList){	// 파일의 확장자에 따라 브라우저로 열수 있는지 여부 체크2
		String strFileEXE		= fileEXE(fname);
		String return_type	= "0";	 // '0' : 브라우저, '1' : 다운로드

		if(strExeList.indexOf(strFileEXE) > -1){	// 브라우저로 가능
			return_type		= "0";
		}else{	// 다운로드 받아야 함
			return_type		= "1";
		}

		return return_type;
	}

	public static String makFileLink(String fname, String linkpath, String linkname, String str_Down){	 // 확장자별 또는 무조건 다운로드
		String str_link	= "";

		if(str_Down.equals("1")){	// 무조건 다운로드
		//	str_link	= "<a href='#alexit' title='"+fname+"' onclick=\"window.open('"+ linkpath +"', 'DownWin', 'width=200,height=200,toolbars=no,scrollbars=yes,resizable=yes');\">"+ linkname +"</a>";
			str_link	= "<a href='"+ linkpath +"' title='"+fname+"'>"+ linkname +"</a>";
		}else{
			String strFileEXE		= fileEXE(fname);
			String strTMPType	= "";

			/* 액티브튜터(arf, atl, apk, apx), 알키온(adf)... */
			if(strFileEXE.equals("ARF") || strFileEXE.equals("ATL") || strFileEXE.equals("APK") || strFileEXE.equals("APX")) { 
			//	str_link = "<a href='"+ linkpath +"' title='"+ fname +"' target='_blank'>"+ linkname +"</a>";
				str_link	= "<a href=\"#alexit\" onclick=\"window.open('"+ linkpath +"', 'DownWin', 'toolbars=no,scrollbars=yes,resizable=yes');\" title=\""+ fname +"\">"+ linkname +"</a>";
			}else{
				strTMPType	 = getFileDownType(fname);
				if(strTMPType.equals("0")){	// 브라우저용
				//	str_link = "<a href='"+ linkpath +"' title='"+ fname +"' target='_blank'>"+ linkname +"</a>";
					str_link	= "<a href=\"#alexit\" onclick=\"window.open('"+ linkpath +"', 'DownWin', 'toolbars=no,scrollbars=yes,resizable=yes');\" title=\""+ fname +"\">"+ linkname +"</a>";
				}else if(strTMPType.equals("1")){	// 다운로드용 (DownType값을 무조건 다운로드'1'로 변경한 후 링크 걸기)
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
	파일명으로 아이콘 파일을 구하여 아이콘에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	*/
	public static String iconFileLink(String fname, String fpath){
		String linkpath	= fileLinkURL(fname, fpath, "0");
		return iconFileLink(fname, fpath, linkpath, "0");
	}

	/*
	파일명으로 아이콘 파일을 구하여 아이콘에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴2
	*/
	public static String iconFileLink(String fname, String fpath, String str_Down){
		String linkpath	= fileLinkURL(fname, fpath, str_Down);
		return iconFileLink(fname, fpath, linkpath, str_Down);
	}

	
	/*
	파일명으로 아이콘 파일을 구하여 아이콘에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	무조건 다운로드 설정기능 포함 ('0' : 확장자에 따라, '1' : 무조건 다운로드)
	*/
	public static String iconFileLink(String fname, String fpath, String linkpath, String str_Down){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link	= "";

		try{
			if(fname==null || fname.equals("")){
			//	str_link		= "<img src='/images/fileicon/text.gif' alt='첨부없음' align='absmiddle'>";
				str_link		= "-";
			}else{
				String strIcon	= "disk.gif";	 // fileIcon(fname) --> 확장자별 아이콘 이미지 구하여 사용할 수도 있음
				str_link		= makFileLink(fname, linkpath, "<img src=\'/images/fileicon/" + strIcon +"' border=0 align='absmiddle'>", str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}


	/*
	파일명으로 아이콘 파일을 구하여, 아이콘+파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	*/
	public static String fileLink(String fname, String fpath){
		String linkpath	= fileLinkURL(fname, fpath, "0");
		return fileLink(fname, fpath, linkpath, "0");
	}

	/*
	파일명으로 아이콘 파일을 구하여, 아이콘+파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴2
	*/
	public static String fileLink(String fname, String fpath, String str_Down){
		String linkpath	= fileLinkURL(fname, fpath, str_Down);
		return fileLink(fname, fpath, linkpath, str_Down);
	}

	/*
	파일명으로 아이콘 파일을 구하여, 아이콘+파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	무조건 다운로드 설정기능 포함 ('0' : 확장자에 따라, '1' : 무조건 다운로드)
	*/
	public static String fileLink(String fname, String fpath, String linkpath, String str_Down){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>파일없음</font>";
			}else{
				String strIcon	= "disk.gif";	 // fileIcon(fname) --> 확장자별 아이콘 이미지 구하여 사용할 수도 있음
				str_link		= makFileLink(fname, linkpath, "<img src=\'/images/fileicon/" + strIcon +"' border=0 align='absmiddle'>&nbsp;"+ fname, str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}

	/*
	파일명으로 아이콘 파일을 구하여, 아이콘+파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	무조건 다운로드 설정기능 포함 ('0' : 확장자에 따라, '1' : 무조건 다운로드)
	링크에 걸릴 파일명의 문자열 길이 제한 기능 (0 이상)
	*/
	public static String fileLink(String fname, String fpath, String str_Down, int int_nameLen){
		String linkpath		= fileLinkURL(fname, fpath, str_Down);
		return fileLink(fname, fpath, linkpath, str_Down, int_nameLen);
	}

	/*
	파일명으로 아이콘 파일을 구하여, 아이콘+파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴2
	무조건 다운로드 설정기능 포함 ('0' : 확장자에 따라, '1' : 무조건 다운로드)
	링크에 걸릴 파일명의 문자열 길이 제한 기능 (0 이상)
	*/
	public static String fileLink(String fname, String fpath, String linkpath, String str_Down, int int_nameLen){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link		= "";
		String strLinkName	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>파일없음</font>";
			}else{
				String strIcon	= "disk.gif";	 // fileIcon(fname) --> 확장자별 아이콘 이미지 구하여 사용할 수도 있음

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
	(아이콘 사용안함) 파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	*/
	public static String fileLinkNoIcon(String fname, String fpath){
		String linkpath	= fileLinkURL(fname, fpath, "0");
		return fileLinkNoIcon(fname, fpath, linkpath, "0");
	}

	/*
	(아이콘 사용안함) 파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴2
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String str_Down){
		String linkpath	= fileLinkURL(fname, fpath, str_Down);
		return fileLinkNoIcon(fname, fpath, linkpath, str_Down);
	}

	/*
	(아이콘 사용안함) 파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	무조건 다운로드 설정기능 포함 ('0' : 확장자에 따라, '1' : 무조건 다운로드)
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String linkpath, String str_Down){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>파일없음</font>";
			}else{
				str_link		= makFileLink(fname, linkpath, fname, str_Down);
			}
		}catch(Exception e){
			str_link = e.getMessage();
		}

		return str_link;
	}

	/*
	(아이콘 사용안함) 파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴
	무조건 다운로드 설정기능 포함 ('0' : 확장자에 따라, '1' : 무조건 다운로드)
	링크에 걸릴 파일명의 문자열 길이 제한 기능 (0 이상)
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String str_Down, int int_nameLen){
		String linkpath		= fileLinkURL(fname, fpath, str_Down);
		return fileLink(fname, fpath, linkpath, str_Down, int_nameLen);
	}

	/*
	(아이콘 사용안함) 파일명에 파일을 다운로드 받을 수 있는 링크문자열을 만들어 리턴2
	무조건 다운로드 설정기능 포함 ('0' : 확장자에 따라, '1' : 무조건 다운로드)
	링크에 걸릴 파일명의 문자열 길이 제한 기능 (0 이상)
	*/
	public static String fileLinkNoIcon(String fname, String fpath, String linkpath, String str_Down, int int_nameLen){
		if(linkpath == null || linkpath.equals("")) linkpath = fileLinkURL(fname, fpath, str_Down);
		String str_link		= "";
		String strLinkName	= "";

		try{
			if(fname==null || fname.equals("")){
				str_link = "<font color=gray>파일없음</font>";
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
	파일 첨부/수정 폼(html)을 문자열로 구성하여 리턴한다 (선택옵션, 파일첨부, 첨부파일 각 3줄(tr)로 표현됨)
	- 기존파일(old_filename)	: <input type='hidden' name='old_filename'> 
	- 선택옵션(old_fileopt)		: ⊙ 파일보존 ○ 덮어쓰기 ○파일삭제
	- 파일첨부(attach_file)		: <input type='file' name='attach_file'>
	- 첨부파일(링크)			: 첨부파일이 있을경우 다운로드 가능한 링크
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
								+ "		<input type='radio' name='old_fileopt' value='1' onclick='Change_FileOption(0)' checked> 파일보존\n"
								+ "		<input type='radio' name='old_fileopt' value='2' onclick='Change_FileOption(1)'> 덮어쓰기\n"
								+ "		<input type='radio' name='old_fileopt' value='3' onclick='Change_FileOption(2)'> 파일삭제\n"
								+ "	</td>\n"
								+ "</tr>\n"
								+ "<tr height='20'><td><input type='file' name='attach_file' class='form' style='width:100%' disabled></td></tr>\n"
								+ "<tr height='20'><td>"+ str_link + "</td></tr>\n"
								+ "</table>\n";
		}

		return return_Form;
	}

	/*
	파일 첨부/수정 폼(html)을 문자열로 구성하여 리턴한다 (선택옵션 + 파일첨부, 첨부파일 각 2줄(tr)로 표현됨)
	- 기존파일(old_filename)	: <input type='hidden' name='old_filename'> 
	- 선택옵션(old_fileopt)		: ⊙ 파일보존 ○ 덮어쓰기 ○파일삭제
	- 파일첨부(attach_file)		: <input type='file' name='attach_file'>
	- 첨부파일(링크)			: 첨부파일이 있을경우 다운로드 가능한 링크
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
								+ "		<input type='radio' name='old_fileopt' value='1' onclick='Change_FileOption(0)' checked> 파일보존\n"
								+ "		<input type='radio' name='old_fileopt' value='2' onclick='Change_FileOption(1)'> 덮어쓰기\n"
								+ "		<input type='radio' name='old_fileopt' value='3' onclick='Change_FileOption(2)'> 파일삭제\n"
								+ "		&nbsp;"+ str_link + ""
								+ "	</td>\n"
								+ "</tr>\n"
								+ "<tr height='20'><td><input type='file' name='attach_file' class='form' style='width:100%' disabled></td></tr>\n"
								+ "</table>\n";
		}

		return return_Form;
	}
}