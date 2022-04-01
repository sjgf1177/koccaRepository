<%
/**
 * file name : za_excel.jsp
 * date		:
 * programmer:
 * function	: 엑셀 저장화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page contentType="application/vnd.ms-excel; charset=MS949" %>
<%
    response.setHeader("Content-Disposition", "inline; filename=excelDown.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) {
		box = RequestManager.getBox(request);
	}

	final String TITLE_OPEN = "<td bgcolor='#00FF99'><b>";
	final String TITLE_CLOSE = "</b></td>";
	final String TABLE_ROW_OPEN = "<tr align=\"left\">";
	final String TABLE_ROW_CLOSE = "</tr>";
	final String TABLE_TD_OPEN = "<td class=\"accountnum\">";
	final String TABLE_TD_OPEN_BG = "<td bgcolor='#FFFFCC' class=\"accountnum\">";
	final String TABLE_TD_CLOSE = "</td>";
	String[]tname = box.get("tname").split("[|]");
	String[]tcode = box.get("tcode").split("[|]");
	String resultName = box.getStringDefault("resultListName", "resultList");
	String bgcolumn = box.get("bgcolumn");
	List<DataBox> resultList = (List<DataBox>)request.getAttribute(resultName);
	int rownum = 1;
%>
<html>
<head>
<contentType=text/html>
<contentType=application/vnd.ms-excel>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
	td.accountnum	{mso-number-format:\@}
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
	<tr>
		<td align="center" height="30"><font size="3"><b><%=box.get("title")%> 목록</b></font></td>
	</tr>
	<tr>
	<td align="center" valign="top">
		<table cellspacing="1" cellpadding="5" border="1">
			<tr>
				<td ><b>NO</b></td>
<%	for(String t : tname) out.println(TITLE_OPEN+t+TITLE_CLOSE);	%>
			</tr>
<%	if(resultList != null && resultList.size() > 0) {
			for(DataBox dbox : resultList) {
				out.println(TABLE_ROW_OPEN);
				out.println(TABLE_TD_OPEN + rownum++ + TABLE_TD_CLOSE);
				for(String entity : tcode) {
					if(bgcolumn.indexOf(entity)!=-1) {
						//색상추가
						out.println(TABLE_TD_OPEN_BG + dbox.getFromDBTypeString(entity) + TABLE_TD_CLOSE);
					}
					else {
						out.println(TABLE_TD_OPEN + dbox.getFromDBTypeString(entity) + TABLE_TD_CLOSE);
					}
				}
				out.println(TABLE_ROW_CLOSE);
			}
		}
		else {	%>
		<tr>
			<td align="center"	colspan="10">등록된 내용이 없습니다.</td>
		</tr>
<%	}	%>

		</table>
		</td>
	</tr>
</table>
</body>
</html>