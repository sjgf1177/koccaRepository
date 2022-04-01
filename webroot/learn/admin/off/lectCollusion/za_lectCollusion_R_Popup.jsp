<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	DataBox ibox = (DataBox)request.getAttribute("resultbox");
	
	String v_name 		= ibox.getString("d_name");
	String v_reg7 		= ibox.getString("d_reg7");
	String v_compnm		= ibox.getString("d_compnm");
	String v_deptnm 	= ibox.getString("d_deptnm");
	String v_compaddr	= ibox.getString("d_compaddr");
	String v_comptel 	= ibox.getString("d_comptel");
	String v_handphone 	= ibox.getString("d_handphone");
	String v_email 		= ibox.getString("d_email");
	String v_worknbook	= ibox.getString("d_worknbook").replace("\r","<br/>");
	String v_etc 		= ibox.getString("d_etc").replace("\r","<br/>");
	String v_subjgubun	= ibox.getString("d_subjgubun");
	String v_lectgubun	= ibox.getString("d_lectgubun");
	String v_lectgubunnm = "";
	if(v_lectgubun.equals("C")){
		v_lectgubunnm = "전문강사";
	}else{
		v_lectgubunnm = "실습강사";
	}
	
	String[] file = new String[3];
	ArrayList fileList = (ArrayList)request.getAttribute("fileList");
	if(fileList.size() > 0 ){
		for(int i = 0; i < fileList.size(); i++) {
			DataBox dbox = (DataBox)fileList.get(i);
			if(dbox.getString("d_filegubun").equals("P")){
				file[i] = "<img src=\""+dbox.getString("d_savefile")+"\" width=150 height=200 />";
			}else{
				file[i] = "<a href=/servlet/controller.library.DownloadServlet?p_savefile=";
				file[i] += dbox.getString("d_savefile");
				file[i] += "&p_realfile=";
				file[i] += dbox.getString("d_realfile");
				file[i] += " target=_blank>";
				file[i] += dbox.getString("d_realfile");
				file[i] += "</a>";
			}
		}
	}
	ArrayList eduList = (ArrayList)request.getAttribute("eduList");
	ArrayList careerList = (ArrayList)request.getAttribute("careerList");
	ArrayList professorList = (ArrayList)request.getAttribute("professorList");
%>
<!DOCTYPE html>
<html>
<head>
<style type='text/css'>
<!--
body {font-size:14px;font-family: dotum;}
.title {font-weight: bold;text-align: center;background-color: #efefef;}
.contents {padding-left: 5px;}
.contents_c {text-align: center;}
-->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>

</head>

<body id="tutor" bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<br/>
<center><h1>강 사 이 력 카 드</h1></center>

<!------------------------------기본정보--------------------------------------->
<table border="1" cellpadding="0" cellspacing="0" width="970px" align="center">
	<tr height="50">
		<td rowspan="3" width="150" height="200"><%=file[0] %></td>
		<td width="120" height="50" class="title">성 명</td>
		<td class="contents"><%=v_name %></td>
		<td width="120" class="title">주민등록번호</td>
		<td class="contents"><%=v_reg7 %>******</td>
	</tr>
	<tr height="50">
		<td height="50" class="title">직장명</td>
		<td colspan="3" class="contents"><%=v_compnm %></td>
	</tr>
	<tr height="50">
		<td height="50" class="title">주소</td>
		<td colspan="3" class="contents"><%=v_compaddr %></td>
	</tr>
	
	<tr height="50">
		<td rowspan="2" class="title">연락처</td>
		<td height="50" class="title">전화</td>
		<td class="contents"><%=v_comptel %></td>
		<td class="title">핸드폰</td>
		<td class="contents"><%=v_handphone %></td>
	</tr>
	<tr height="50">
		<td height="50" class="title">E-mail</td>
		<td colspan="3" class="contents"><%=v_email %></td>
	</tr>
	<tr height="50">
		<td rowspan="3" class="title">지원정보</td>
		<td height="50" class="title">강사구분</td>
		<td class="contents" colspan="3"><%=v_lectgubunnm %></td>
	</tr>
	<tr height="50">
		<td height="50" class="title">지원분야</td>
		<td colspan="3" class="contents"><%=v_subjgubun %></td>
	</tr>
	<tr height="50">
		<td height="50" class="title">상세내용</td>
		<td colspan="3" class="contents"><%=v_etc %></td>
	</tr>
</table>

<table border="1" cellpadding="0" cellspacing="0" width="970px" align="center" style="border-top-style: none;margin-top: -2px;">
	<tr height="50">
		<td rowspan="4" width="150" class="title">학 력</td>
		<td class="title" width="200">학 교(기관)</td>
		<td class="title" width="200">기 간</td>
		<td class="title">내 용</td>
	</tr>
	<%
		if(eduList.size() > 0 ){
			for(int i = 0; i < eduList.size(); i++) {
				DataBox ebox = (DataBox)eduList.get(i);
	%>
	<tr height="50">
		<td class="contents_c"><%=ebox.getString("d_position") %></td>
		<td class="contents_c"><%=ebox.getDate("d_startdate",".") %> ~ <%=ebox.getDate("d_enddate",".") %></td>
		<td class="contents"><%=ebox.getString("d_contents") %></td>
	</tr>
	<%
			}
		}
	%>
	
	<tr height="50">
		<td rowspan="4" width="150" class="title">주요경력</td>
		<td class="title">근무처</td>
		<td class="title">기 간</td>
		<td class="title">직 무</td>
	</tr>
	<%
		if(careerList.size() > 0 ){
			for(int i = 0; i < careerList.size(); i++) {
				DataBox cbox = (DataBox)careerList.get(i);
	%>
	<tr height="50">
		<td class="contents_c"><%=cbox.getString("d_position") %></td>
		<td class="contents_c"><%=cbox.getDate("d_startdate",".") %> ~ <%=cbox.getDate("d_enddate",".") %></td>
		<td class="contents"><%=cbox.getString("d_contents") %></td>
	</tr>
	<%
			}
		}
	%>
	
	<tr height="50">
		<td rowspan="4" width="150" class="title">교수기록</td>
		<td class="title">기관명</td>
		<td class="title">기 간</td>
		<td class="title">교과내용</td>
	</tr>
	<%
		if(professorList.size() > 0 ){
			for(int i = 0; i < professorList.size(); i++) {
				DataBox pbox = (DataBox)professorList.get(i);
	%>
	<tr height="50">
		<td class="contents_c"><%=pbox.getString("d_position") %></td>
		<td class="contents_c"><%=pbox.getDate("d_startdate",".") %> ~ <%=pbox.getDate("d_enddate",".") %></td>
		<td class="contents"><%=pbox.getString("d_contents") %></td>
	</tr>
	<%
			}
		}
	%>
	
	<tr style="min-height: 50px;">
		<td class="title">주요작품 및<br/>저서</td>
		<td colspan="3" class="contents"><%=v_worknbook %></td>
	</tr>
</table>
<center><img src="/images/admin/common/koccalogo.png"/></center>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>