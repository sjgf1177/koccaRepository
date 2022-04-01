<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import = "com.credu.library.*" %>
<%! 
public String makeKor(String s) throws java.io.UnsupportedEncodingException{ 
/*	
		String kor=""; 
		
		if (s==null) 
			kor= null; 
		else 
			kor=new String(s.getBytes("ISO-8859-1"),"EUC-KR"); 
		
		return kor; 
*/
		return s;
} 
%>
<%
String strKeyword = makeKor(request.getParameter("keyword"));
String strCourseCode = makeKor(request.getParameter("coursecode"));
String strCheck = null;
String strSQL = null;

DBConnectionManager connMgr = null;
ListSet rs = null;
String sql = "";

if(strKeyword != null && !strKeyword.equals("") && !strKeyword.equals("null")) {
	strSQL = "select a.title, a.url, a.coursecode ,b.content from tbSearchData as a left join tbContentData as b on a.idx = b.data_idx and b.serial = 1 where a.coursecode = '" + strCourseCode + "' and (title like '%" + strKeyword + "%' or a.idx in (select data_idx from tbContentData where content like '%" + strKeyword + "%' group by data_idx))";
} else {
	strSQL = "select * from ( select rownum rnum,  * from tbSearchData) where rnum < 101";
	strKeyword = "";
}

try {

	connMgr = new DBConnectionManager();
	rs = connMgr.executeQuery(strSQL);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<title>:::: 검색 결과 ::::</title>
<link href="../images/lib.css" rel="stylesheet" type="text/css">
<link href="images/lib.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript">
	function showContent(strURL) {
	
		window.open('/hdsearch/searchview.jsp?url=<%= strCourseCode %>/docs/contents/' + strURL,'viewwin','width=900, height=680, scrollbars=no');
	}
	
	function checkSubmit() {
	
		var objForm = document.getElementById("searchform");	
		
		if(objForm.keyword.value != "")
			objForm.submit();
		else
			alert("검색어를 입력해주세요");
	}
	
</script>	
<body topmargin=0 leftmargin=0>
<table width="725" height="0" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30" colspan="3">&nbsp;</td>
  </tr>
  <tr>
    <td width="10" rowspan="3">&nbsp;</td>
    <td>
    	<form action="search_i.jsp" method="post" id="searchform">
			<input type="text" size="20" name="keyword" value="<%= strKeyword %>">
			<input type="hidden" size="20" name="coursecode" value="<%= strCourseCode %>">
			<input type="button" value="검색" onClick="checkSubmit()">
		</form>
    	<img src="images/bullet.gif" align="absmiddle"> 검색어<strong> &quot;<%= strKeyword %>&quot; </strong>으로 검색된 결과 입니다.
        <ul>
<%
	while(rs.next()) {
		String strTitle = rs.getString("title");
		String strContent = rs.getString("content");
		String strUrl = rs.getString("url");

		strCheck = "ok";

		if(strContent != null && strTitle != null) {
			if(strContent.length() > strTitle.length()+205) {
				strContent = strContent.substring(strTitle.length(),strTitle.length()+205) + "...";
			}
		}
		%><li/><a href="javascript:showContent('<%= strUrl %>')"><%= strTitle %></a><BR><%= strContent %><br><br><%
	}

	rs.close();
%>
    </ul>
<%
	if(strCheck == null) {
		%><p align="center">검색된 결과가 없습니다.</p><%
	}
%>
    </td>
    <td width="10" rowspan="3">&nbsp;</td>
  </tr>
  <tr>
    <td height="5">
      <!--<div align="right">[이전] [ 1 | 2 | 3 | 4 | 5 | 6 ] [다음]</div></td>-->
  </tr>
  <tr>
    <td height="10">&nbsp;</td>
  </tr>
</table>
</body>
</html>

<%
} catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, null, sql);
	throw new Exception(ex.getMessage());
} finally {
	if (rs != null) { try {rs.close();} catch (Exception e) {}}
	if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
}
%>