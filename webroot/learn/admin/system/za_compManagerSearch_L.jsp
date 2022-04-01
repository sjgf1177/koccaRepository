<%
/**
 * file name : za_compManagerSearch_L.jsp
 * date	  : 2009.12.01
 * programmer: swchoi
 * function  : 환경설정 | 업체관리 - 담당자 검색
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.propose.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = null;
box = (RequestBox)request.getAttribute("requestbox");
if (box == null) {
	box = RequestManager.getBox(request);
}

ArrayList list	  = null;

String v_userid	 = "";
String v_resno	  = "";
String v_name	   = "";
String v_email	  = "";
String v_membergubun= "";
String v_comptext= "";

int	 i		   =  0;

String p_param1 = box.get("p_param1");
String p_param2 = box.get("p_param2");
String  ss_userid	 = box.getString("p_userid");	 //ID
String  ss_name	   = box.getString("p_name");	   //이름
String  ss_action	 = box.getString("p_action");
String  ss_fix_gubun  = box.getStringDefault("s_fix_gubun","direct");

if (ss_action.equals("go")) {	//go button 선택시만 list 출력
		list = (ArrayList)request.getAttribute("resultList");
}

%>
<%
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
</head>
<script language="javascript">
<!--

	//검색
	function whenSelection(p_action) {
			ff = document.form1;
			document.form1.p_action.value = p_action;
			document.form1.target = "_self";
			document.form1.action='/servlet/controller.system.OutUserAdminServlet';
			document.form1.p_process.value = 'userList';
			document.form1.submit();
	}

	// 엔터키 처리
	function searchEnterKeyEvent(e){
	 if (e.keyCode =='13'){  whenSelection('go');  }
	 else return;
	}

//-->
</script>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<center>

<form name="form1" method="post" action="/servlet/controller.propose.ProposeWizardServlet">
<input type="hidden" name="p_process"	value="userList">
<input type="hidden" name="p_action"	value="<%= ss_action %>">

<table width="97%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td align="center" valign="top">
	  <!----------------- 교육차수 검색 title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
		<tr>
		  <td><img src="/images/admin/course/tit_memsearch.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!-------------------타이틀 끝-------------------------->
	  <br>
	  <!----------------- 검색조건 입력박스 ----------------->
	  <table cellspacing="0" cellpadding="1" class="form_table_out">
		<tr>
		  <td bgcolor="#C6C6C6" align="center">
			<table cellspacing="0" cellpadding="0" class="form_table_bg">
			  <tr>
				<td height="7" width="99%">
				</td>
			  </tr>
			  <tr>
				<td align="center" width="99%" valign="middle">
				  <table width="99%" border="0" cellspacing="0" cellpadding="0" class="form_table">
					<tr>
					  <td>
					  	<select name="p_param1"><option value="NM">이름</option><option value="ID" <%= p_param1.equals("ID") ? "selected" : "" %>>아이디</option></select>
					  </td>
					  <td>
						<input type="text" name="p_param2"   value="<%=p_param2%>" size="10" onClick="javascript:searchEnterKeyEvent(event)">
					  </td>
					  <td></td>
					  <td>
						&nbsp;&nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %>
					  </td>
					</tr>
				  </table>
				</td>
			  </tr>
			  <tr>
				<td height="7" width="99%"></td>
			  </tr>
			</table>
		  </td>
		</tr>
	  </table>
	  <!-----------------  검색조건 입력박스 끝 ----------------->
	  <br/>
	  <!----------------- 테이블 시작 ----------------->
	  <table cellspacing="1" cellpadding="5" class="table_out">
		<tr> <td colspan="11" class="table_top_line"></td></tr>
		<tr>
		  <td class="table_title" width="10%"><b>No</b></td>
		  <td class="table_title" width="20%"><b>회원구분</b></td>
		  <td class="table_title" width="20%"><b>ID</b></td>
		  <td class="table_title" width="20%"><b>성명</b></td>
		  <td class="table_title" width="20%"><b>소속</b></td>
		  <td class="table_title" width="10%"><b>전화번호</b></td>
		</tr>

		<%
		int v_total =0, v_index1=0, v_index2=0;
		String v_officStatus = "";
		i = 0;
	if(list != null) {
	//out.println("list="+list);
		v_total = list.size();
		for(i = 0; i < v_total; i++) {
			String v_telno1 = "", v_str_tmp = "", v_telno2 = "",v_telno3 = "", v_telno = "";
			String v_htelno1 = "", v_htelno2 = "",v_htelno3 = "", v_htelno = "";
			DataBox dbox = (DataBox)list.get(i);
			v_userid	 = dbox.getString("d_userid");
			v_resno	  = dbox.getString("d_resno");
			v_name	   = dbox.getString("d_name");
			v_email	  = dbox.getString("d_email");
			v_comptext	  = dbox.getString("d_comptext");
			v_membergubun= dbox.getString("d_membergubun");

			v_telno = dbox.getString("d_hometel");
			v_htelno = dbox.getString("d_handphone");
	        if(v_telno.length() > 0 && v_telno.indexOf('-') != -1){
	            v_index1   =   v_telno.indexOf('-');
	            v_telno1   =   v_telno.substring(0,v_index1);
	            v_str_tmp  =   v_telno.substring(v_index1+1);
	            v_index2   =   v_str_tmp.indexOf('-');
	            v_telno2   =   v_str_tmp.substring(0,v_index2);
	            v_telno3   =   v_str_tmp.substring(v_index2+1);
	        }
	        if(v_htelno.length() > 0 && v_htelno.indexOf('-') != -1){
	            v_index1   =   v_htelno.indexOf('-');
	            v_htelno1   =   v_htelno.substring(0,v_index1);
	            v_str_tmp  =   v_htelno.substring(v_index1+1);
	            v_index2   =   v_str_tmp.indexOf('-');
	            v_htelno2   =   v_str_tmp.substring(0,v_index2);
	            v_htelno3   =   v_str_tmp.substring(v_index2+1);
	        }
			//회원구분
			if(v_membergubun.equals("C")) {v_membergubun = "기업";}
			else if(v_membergubun.equals("U")) {v_membergubun = "대학";}
			else  {v_membergubun = "개인";}
		%>
<%	v_totalpage	 = dbox.getInt("d_totalpage");// for 루프 안에 삽입
		v_totalrowcount = dbox.getInt("d_totalrowcount");
		v_rowcount	  = dbox.getInt("d_rowcount");	%>	 
		<tr>
			<td class="table_01"><input type="radio" name="p_userid" value="<%=v_userid%>" 
				onclick="var userInfo={
					v_userid:'<%=v_userid%>',
					v_name : '<%=v_name%>',
					v_membergubun :'<%=v_membergubun%>',
					v_comptext : '<%=v_comptext%>',
					v_membergubun:'<%=v_membergubun%>',
					v_email:'<%=dbox.getString("d_email")%>',
					v_membergubun	: '<%= dbox.get("d_membergubun") %>',
					v_userid		: '<%= dbox.get("d_userid") %>',
					v_name			: '<%=v_name %>',
					v_comptext		: '<%= dbox.get("d_comptext") %>',
					v_hometel		: '<%= v_telno %>',
					v_hometel1		: '<%= v_telno1 %>',
					v_hometel2		: '<%= v_telno2 %>',
					v_hometel3		: '<%= v_telno3 %>',
					v_handphone1	: '<%= v_htelno1 %>',
					v_handphone2	: '<%= v_htelno2 %>',
					v_handphone3	: '<%= v_htelno3 %>',
					v_handphone	: '<%= v_htelno %>',
					v_resno			: '<%= dbox.get("d_resno") %>',
					v_post1			: '<%= dbox.get("d_post1") %>',
					v_post2			: '<%= dbox.get("d_post2") %>',
					v_email			: '<%= dbox.get("d_email") %>',
					v_addr			: '<%= dbox.get("d_addr") %>',
					v_addr2			: '<%= dbox.get("d_addr2") %>',
					v_pwd			: '<%= dbox.get("d_pwd") %>'
				};fnSelectUser(userInfo);"><%=i+1%></td>
			<td class="table_02_1"><%=v_membergubun%></td>
			<td class="table_02_1"><%=v_userid%></td>
			<td class="table_02_1"><%=v_name%></td>
			<td class="table_02_1"><%=v_comptext%></td>
			<td class="table_02_1"><%=v_membergubun%></td>
		  </tr>
<%
		}
	}
	if(i == 0) {
%>
		 <tr><td align="center" bgcolor="#F7F7F7" height="50" colspan="10">검색된 회원이 없습니다</td></tr>

<%
	}
%>
	  </table>
	  <!----------------- 테이블 끝 ----------------->
<br>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function fnSelectUser(userInfo) {
		opener.putUserInfo(userInfo);
		window.close();
	}
	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.submit();
	}
	function pagesize(pageSize) {
		document.form1.target = "_self";
		document.form1.p_pageno.value = 1;
		document.form1.p_pagesize.value = pageSize;
		document.form1.submit();
	}
//-->
</SCRIPT>
	<input type = "hidden" name = "p_pageno"	value = "<%=v_pageno%>">
	<input type = "hidden" name = "p_pagesize"  value = "<%=v_pagesize%>">




		<!----------------- total 시작 ----------------->
		<br>
		<table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			<td align="right" valign="absmiddle">
			  <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
			</td>
		  </tr>
		</table>
		<!----------------- total 끝 ----------------->

<br>
	  <!----------------- 닫기 버튼 시작 ----------------->
	  <table cellpadding="0" cellspacing="0" class="table1">
		<tr>
		  <td align="center" height="20"><a href="javascript:window.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
		</tr>
	  </table>
	  <!----------------- 닫기 버튼 끝 ----------------->
	  <br>
	</td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>

</center>
</body>
</html>
