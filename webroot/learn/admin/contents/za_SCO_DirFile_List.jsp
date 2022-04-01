<%
/**
 * file name : za_SCO_DirFile_List.jsp
 * date      : 2004/11/15
 * programmer: Park Mibok
 * function  : SCO 등록/수정 - 파일찾기모듈 화면
 */
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.util.*,java.io.File" %>
<%@ page import="com.credu.scorm.*" %>
<%-- @ include file = "/learn/admin/contents/com_Upload_Path.jsp" --%>
<%!
        public final static String str_COURSEDATA_PATH = "d:\\Gachun\\Upload\\Course_Data";
	public String getIconImage(String str_Type, String str_FileName){
		String str_Image	= "";
		String str_Exe	= FileUtil.getFileExt(str_FileName).toUpperCase();

		if(str_Type.equals("F")){
			str_Exe	= str_Exe.toUpperCase();
			if(str_Exe.equals("GIF") || str_Exe.equals("JPG") || str_Exe.equals("JPEG") || str_Exe.equals("BMP")){
				str_Image	= "/images/fileicon/img_file.gif";
			}else if(str_Exe.equals("HTM") || str_Exe.equals("HTML")){
				str_Image	= "/images/fileicon/html_file.gif";
			}else if(str_Exe.equals("TXT")){
				str_Image	= "/images/fileicon/text_file.gif";
			}else if(str_Exe.equals("DOC")){
				str_Image	= "/images/fileicon/word_file.gif";
			}else if(str_Exe.equals("XLS")){
				str_Image	= "/images/fileicon/xls_file.gif";
			}else if(str_Exe.equals("PPT")){
				str_Image	= "/images/fileicon/ppt_file.gif";
			}else if(str_Exe.equals("ZIP")){
				str_Image	= "/images/fileicon/zip_file.gif";
			}else{
				str_Image	= "/images/fileicon/unknown_file.gif";
			}
		}else{
			str_Image	= "/images/fileicon/folder.gif";
		}

		str_Image	= "<img src='"+ str_Image +"' border=0 align='absmiddle'>";

		return str_Image;
	}
%>
<%
	String str_TopPath		= str_COURSEDATA_PATH;
	String str_DefaultPath		= str_TopPath+"\\"+"0000000001";
	String str_SelectedFile		= "";
	StringBuffer sbf_DirFileList	= new StringBuffer();


	String str_ReturnName		= HttpUtil.getParameter("Return_FieldName", request, "CONTENT_URL1", "CONTENT_URL1");
	String str_RootPath		= HttpUtil.getParameter("ROOT_PATH", request, str_DefaultPath, str_DefaultPath);
	String str_CurrentPath		= HttpUtil.getParameter("CURR_PATH", request, str_RootPath, str_RootPath);
	String str_SortField		= HttpUtil.getParameter("SORT_FIELD", request, "DirFile_Name", "DirFile_Name");
	String str_SortType		= HttpUtil.getParameter("SORT_TYPE", request, "ASC", "ASC");
	String tmp_Root			= StringUtil.replace(str_RootPath, "\\", "/");
	String str_SortButton		= "";
	boolean bln_ASC		= true;

	str_RootPath		= StringUtil.replace(str_RootPath, "/", "\\");
	str_CurrentPath		= StringUtil.replace(str_CurrentPath, "/", "\\");
	if(str_SortType.equals("ASC")){
		bln_ASC		= true;
		str_SortButton	= "<span onclick=\"Change_Sort('DirFile_Name', 'DESC')\" style='color:#ACA899;cursor:hand'>▲</span>";
	}else{
		bln_ASC		= false;
		str_SortButton	= "<span onclick=\"Change_Sort('DirFile_Name', 'ASC')\" style='color:#ACA899;cursor:hand'>▼</span>";
	}

	int i_FolderCnt		= 0;
	int i_FileCnt			= 0;

	try{
		File file		= new File(str_CurrentPath);
		if(!file.exists()){
			sbf_DirFileList.append("<TR height='20'>\n");
			sbf_DirFileList.append("<TD colspan='2'>[FTP]로 파일을 먼저 업로드 하셔야합니다.</TD>\n");
			sbf_DirFileList.append("</TR>\n");
		}else{
			String[] arr_File	= file.list();
			String str_ParentPath	= file.getParent();
			Matrix mtx_DirList		= new Matrix("DirFile_Code, DirFile_Type, DirFile_Name, DirFile_Path, DirFile_TypeName");
			Matrix mtx_FileList	= new Matrix("DirFile_Code, DirFile_Type, DirFile_Name, DirFile_Path, DirFile_TypeName");

			if(!str_ParentPath.equals(str_TopPath)){	 // 현재 디렉토리가 최상위 디렉토리가 아닐경우 상위 디렉토리 표시
				List newRow	= new ArrayList();
				newRow.add("1");
				newRow.add("R");
				newRow.add("..");
				newRow.add(str_ParentPath);
				newRow.add("상위 폴더");
				mtx_DirList.addRow(newRow);
			}
			for(int i = 0; i < arr_File.length; i++){
				List newRow	= new ArrayList();
				File tmpFile		= new File(str_CurrentPath +"\\"+ arr_File[i]);
				if(tmpFile.isDirectory()){	// 디렉토리일 경우
					newRow.add("2");
					newRow.add("D");	
					newRow.add(arr_File[i]);	// 디렉토리명
					newRow.add(str_CurrentPath +"\\" + arr_File[i]);	// 디렉토리 경로
					newRow.add("파일 폴더");
					mtx_DirList.addRow(newRow);
				}else{	// 파일일 경우
					String str_Exe	= FileUtil.getFileExt(arr_File[i]).toUpperCase();
					newRow.add("3");
					newRow.add("F");
					newRow.add(arr_File[i]);	// 파일명
					newRow.add(str_CurrentPath);	// 파일의 경로
					newRow.add(str_Exe +" 파일");
					mtx_FileList.addRow(newRow);
				}
				
			}			

			String str_Type	= "";
			String str_Name	= "";
			String str_Path	= "";
			String str_Path2	= "";
			String str_TName	= "";
			String str_Image	= "";

			if((mtx_DirList == null || mtx_DirList.getRowSize() < 1) && (mtx_FileList == null || mtx_FileList.getRowSize() < 1)){
				sbf_DirFileList.append("<TR height='20'>\n");
				sbf_DirFileList.append("<TD colspan='2'>[FTP]로 파일을 먼저 업로드 하셔야합니다.</TD>\n");
				sbf_DirFileList.append("</TR>\n");
			}else{
				mtx_DirList.sort(str_SortField, bln_ASC);
				i_FolderCnt		= mtx_DirList.getRowSize();

				for(int idx = 0; idx < mtx_DirList.getRowSize(); idx++){
					str_Type	= mtx_DirList.getString(idx, "DirFile_Type", "R");
					str_Name	= mtx_DirList.getString(idx, "DirFile_Name", "");
					str_Path	= mtx_DirList.getString(idx, "DirFile_Path", "");
					str_Path	= StringUtil.replace(str_Path, "\\", "/");
					str_Path2	= StringUtil.replace(str_Path, tmp_Root , "")+"/";
					str_TName	= mtx_DirList.getString(idx, "DirFile_TypeName", "");
					str_Image	= getIconImage(str_Type, "");

					if(str_Type.equals("R")){
						sbf_DirFileList.append("<TR height='20'>\n");
						sbf_DirFileList.append("<TD width='300'>"+ str_Image +" <span id='Dir"+ idx +"' class='unselected' onclick=\"Item_Click('Dir"+ idx +"')\" ondblclick=\"Move_Directory('"+ str_Path +"')\" onmouseover=\"document.frm.win_status.value='"+ str_Path2 +"'\" onmouseout=\"document.frm.win_status.value='파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요'\">"+ str_Name +"</span></TD>\n");
						sbf_DirFileList.append("<TD width='70' align=center>"+ str_TName +"</TD>\n");
						sbf_DirFileList.append("</TR>\n");
					}else if(str_Type.equals("D")){
						sbf_DirFileList.append("<TR height='20'>\n");
						sbf_DirFileList.append("<TD width='300'>"+ str_Image +" <span id='Dir"+ idx +"' class='unselected' onclick=\"Item_Click('Dir"+ idx +"')\" ondblclick=\"Move_Directory('"+ str_Path +"')\" onmouseover=\"document.frm.win_status.value='"+ str_Path2 +"'\" onmouseout=\"document.frm.win_status.value='파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요'\">"+ str_Name +"</span></TD>\n");

						sbf_DirFileList.append("<TD width='70' align=center>"+ str_TName +"</TD>\n");
						sbf_DirFileList.append("</TR>\n");
					}else{
						sbf_DirFileList.append("<TR height='20'>\n");
						sbf_DirFileList.append("<TD width='300'>"+ str_Name +"</TD>\n");
						sbf_DirFileList.append("<TD width='70' align=center>"+ str_TName +"</TD>\n");
						sbf_DirFileList.append("</TR>\n");
					}
				}
			}

			if((mtx_DirList == null || mtx_DirList.getRowSize() < 1) && (mtx_FileList == null || mtx_FileList.getRowSize() < 1)){
			}else{
				mtx_FileList.sort(str_SortField, bln_ASC);
				i_FileCnt		= mtx_FileList.getRowSize();

				for(int idx = 0; idx < mtx_FileList.getRowSize(); idx++){
					str_Type	= mtx_FileList.getString(idx, "DirFile_Type", "R");
					str_Name	= mtx_FileList.getString(idx, "DirFile_Name", "");
					str_Path	= mtx_FileList.getString(idx, "DirFile_Path", "");
					str_Path	= StringUtil.replace(str_Path, "\\", "/");
					str_Path2	= StringUtil.replace(str_Path, tmp_Root , "");
					if(!str_Path2.equals("")){
						str_Path2	= StringUtil.replace(str_Path, tmp_Root+"/" , "");
					}

					str_TName	= mtx_FileList.getString(idx, "DirFile_TypeName", "");
					str_Image	= getIconImage(str_Type, str_Name);

					if(str_Type.equals("F")){
						sbf_DirFileList.append("<TR height='20'>\n");

						if(str_Path2.equals("")){
							sbf_DirFileList.append("<TD width='300'>"+ str_Image +" <span id='File"+ idx +"' class='unselected' onclick=\"Item_Click('File"+ idx +"')\" ondblclick=\"Select_File('"+ str_Path +"', '"+ str_Path2 +"', '"+ str_Name +"')\" onmouseover=\"document.frm.win_status.value='"+ str_Name +"'\" onmouseout=\"document.frm.win_status.value='파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요'\">"+ str_Name +"</span></TD>\n");
						}else{
							sbf_DirFileList.append("<TD width='300'>"+ str_Image +" <span id='File"+ idx +"' class='unselected' onclick=\"Item_Click('File"+ idx +"')\" ondblclick=\"Select_File('"+ str_Path +"', '"+ str_Path2 +"', '"+ str_Name +"')\" onmouseover=\"document.frm.win_status.value='"+ str_Path2 +"/"+ str_Name +"'\" onmouseout=\"document.frm.win_status.value='파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요'\">"+ str_Name +"</span></TD>\n");
						}
						sbf_DirFileList.append("<TD width='70' align=center>"+ str_TName +"</TD>\n");
						sbf_DirFileList.append("</TR>\n");
					}else{
						sbf_DirFileList.append("<TR height='20'>\n");
						sbf_DirFileList.append("<TD width='300'>"+ str_Name +"</TD>\n");
						sbf_DirFileList.append("<TD width='70' align=center>"+ str_TName +"</TD>\n");
						sbf_DirFileList.append("</TR>\n");
					}
				}
			}
		}
	}catch(Exception e){
		out.println(e.getMessage());
	}
%>
<html>
<head>
	<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
	<script language="javascript">
	var dir_cnt	= <%=i_FolderCnt%>;
	var file_cnt = <%=i_FileCnt%>;

	function Change_Sort(field, type){
		document.frm.SORT_FIELD.value = field;
		document.frm.SORT_TYPE.value = type;
		document.frm.action = "com_DirFile_List.jsp";
		document.frm.submit();
	}
	function Item_Click(item_name){
		var tmp_name = "";
		for(i = 0; i < dir_cnt; i++){
			tmp_name	= "Dir"+ i;
			var item1 = eval(document.all[tmp_name]);
			item1.className='unselected';

			if(tmp_name == item_name){
				var item2 = eval(document.all[item_name]);
				item2.className='selected';
			}
		}
		for(i = 0; i < file_cnt; i++){
			tmp_name	= "File"+ i;
			var item1 = eval(document.all[tmp_name]);
			item1.className='unselected';

			if(tmp_name == item_name){
				var item2 = eval(document.all[item_name]);
				item2.className='selected';
			}
		}
	}
	function Move_Directory(path){
		document.frm.CURR_PATH.value = path;
		document.frm.action = "com_DirFile_List.jsp";
		document.frm.submit();
	}
	function Select_File(path, path2, name){
		document.frm.CURR_PATH.value = path;
		if(path2 == "")
			document.frm.SelectedFile.value = name;
		else
			document.frm.SelectedFile.value = path2 +"/"+name;
	}
	function List_Reload(){
		document.frm.action = "com_DirFile_List.jsp";
		document.frm.submit();
	}
	function Win_Close(){
		parent.WinSearch_Close();
	}
	function Path_Use(){
		var value = document.frm.SelectedFile.value;
		if(value == ""){
			alert("초기파일로 시작할 파일을 선택(더블클릭)하신 후 다시 시도하십시오.");
			return;
		}else{
		        var item = eval("parent.document.form1."+ document.frm.Return_FieldName.value);
			item.value = value;
			Win_Close();
		}
	}
	</script>
	<style type="text/css">
	.textpath{border: 1px solid; border-color: #7F9DB9; color:#000000; width:285}
	.selected{
		color:#FFFFFF;
		background-color:#316AC5;
		cursor:hand;
	}
	.unselected{
		color:#000000;
		background-color:#F7F7F7;
		cursor:hand;
	}
	.rb {
		background:#EBEADB;
		border-style:outset;
		border-width:1;
	    }
	.onbut
	{
		border-color : #ffffff #808080 #808080 #ffffff;
		border-style : solid;
		border-width: 1px;
		cursor: hand;
	}
	.downbut
	{
		border-color: #808080 #ffffff #ffffff #F7F7F7;
		border-style : solid;
		border-width: 1px;
	}
	.dirtitle
	{
		color:#000000;
	}
	.offbut
	{
		border-color: #EBEADB;
		border-style : solid;
		border-width: 1px
	}
	</style>
</head>
<body class="bodystyle" bgcolor="#EBEADB">
<BODY oncontextmenu='return false' onselectstart='return false' ondragstart='return false'>
<table border=0 cellspacing=1 cellpadding=0 width=400 bgcolor='#FFFFFF'>
<form name="frm" method="post">
<input type="hidden" name="Return_FieldName" value="<%=str_ReturnName%>">
<input type="hidden" name="ROOT_PATH" value="<%=str_RootPath%>">
<input type="hidden" name="CURR_PATH" value="<%=str_CurrentPath%>">
<input type="hidden" name="SORT_FIELD" value="<%=str_SortField%>">
<input type="hidden" name="SORT_TYPE" value="<%=str_SortType%>">

<tr><td valign=top bgcolor=#FFFFFF>
	<table width="400" height="300" border="1" cellpadding="0" cellspacing="0" bordercolor="#D6D2C2" style=border-collapse:collapse>
	<tr><td height="131">
			<Table width="400" cellpadding=2 cellspacing=0 bgcolor="#EBEADB" style="border-color:#D6D2C2; border-style:solid; border-width:1;">
			<TR><TD height=20 bgcolor="#EBEADB">
				<Table class=rb border=0 width="100%" cellspacing=0 height="100%">
				<TR align=center><TD>
					<Table border=0 cellspacing=0 cellpadding=0 width=400>
					<TR align=left>
						<TD width=285><input type="text" name="SelectedFile" value="<%=str_SelectedFile%>" class="textpath" readonly></TD>
						<TD width="35" align=center class="offbut" onmouseover="this.className='onbut'; document.frm.win_status.value='사용하기'; return true;" onmouseout="this.className='offbut'; document.frm.win_status.value='파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요'; return true;" onmousedown="this.className='downbut'" onclick="Path_Use()"><img src="/images/fileicon/path_use.gif" align="absmiddle" alt="사용하기"></TD>
						<TD width=3 align=center><img src="/images/fileicon/bar.gif" width="3" height="20"></TD>
						<TD width=32 align=center class="offbut" onmouseover="this.className='onbut'; document.frm.win_status.value='새로고침'; return true;" onmouseout="this.className='offbut'; document.frm.win_status.value='파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요'; return true;" onmousedown="this.className='downbut'" onclick="List_Reload()"><img src="/images/fileicon/reload.gif" alt="새로고침"></TD>
						<TD width=3 align=center><img src="/images/fileicon/bar.gif" width="3" height="20"></TD>
						<TD width=32 align=center class="offbut" onmouseover="this.className='onbut'; document.frm.win_status.value='창닫기'; return true;" onmouseout="this.className='offbut'; document.frm.win_status.value='파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요'; return true;" onmousedown="this.className='downbut'" onclick="Win_Close()"><img src="/images/fileicon/win_close.gif" alt="창닫기"></TD>
					</TR>
					</Table>
				</TD></TR>
				</Table>
			</TD></TR>
			<TR><TD height=20 bgcolor="#EBEADB">
				<Table class=rb border=0 width="100%" cellspacing=0 height="100%">
				<TR align=center><TD>
					<Table border=0 cellspacing=0 cellpadding=0 width=400>
					<TR align=left>
						<TD width=297 class="dirtitle">&nbsp이름&nbsp;&nbsp;<%=str_SortButton%></TD>
						<TD width=3 align=center><img src="/images/fileicon/bar.gif" width="3" height="20"></TD>
						<TD width=100 class="dirtitle">&nbsp종류</TD>
					</TR>
					</Table>
				</TD></TR>
				</Table>
			</TD></TR>
			<TR><TD>
				<Table cellspacing=0  border=0 align=center width="100%" height="280" bgcolor="#F7F7F7" class="downbut">
				<TR><TD valign=top>
					<div style="width:400;height:300;overflow:auto">
						<Table cellspacing=0 border=0 cellpadding=0 width="370">
						<%=sbf_DirFileList.toString()%>
						</Table>
					</div>
				</TD></TR>
				</Table>
			</TD></TR>
			<TR><TD height=20 bgcolor="#EBEADB"><img src="/images/fileicon/html_file.gif" align="absmiddle">&nbsp;<input type="text" name="win_status" value="파일을 목록에서 더블클릭하신 후 [->] 버튼을 클릭하세요" style="width:90%;border:0;background-color:#EBE8D6;color:#000000" readonly></TD></TR>
			</Table>
	</td></tr>
	</table>


</td></tr>
</form>
</table>
</body>
</html>