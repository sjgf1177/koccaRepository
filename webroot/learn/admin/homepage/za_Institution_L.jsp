<%
//**********************************************************
//  1. 제      목 : 시설관리
//  2. 프로그램명 : za_Institution_L.jsp
//  3. 개      요 : 시설관리 리스트
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 2005.08. 03
//  7. 수      정 :
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String  v_process     = box.getString("p_process");

	int    v_no        = 0;
	String v_code      = "";
	String v_insname   = "";
	String v_realfile  = "";
	String v_savefile  = "";
	String v_url       = "";
	String v_luserid   = "";
	String v_ldate     = "";
	String v_realpath  = "";
    int v_dispnum      = 0;           // 총게시물수
    int v_totalpage    = 0;           // 게시물총페이지수
	int v_pageno       = box.getInt("p_pageno"); 
    ArrayList list     = (ArrayList)request.getAttribute("select");
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<SCRIPT LANGUAGE="javascript">

	//입력페이지로 이동
	function insertPage() {
		document.form1.action = "/servlet/controller.homepage.InstitutionAdminServlet";
		document.form1.p_process.value = "insertPage";   
		document.form1.submit();
	}

	//수정페이지로 이동
	function updatePage(no,code) {
		document.form1.p_no.value=no;
		document.form1.p_code.value=code;
		document.form1.action = "/servlet/controller.homepage.InstitutionAdminServlet";
		document.form1.p_process.value = "updatePage";   
		document.form1.submit();
	}	

</SCRIPT>

</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post">
<input type = "hidden" name = "p_process" value = "">
<input type = "hidden" name = "p_no" value = "">
<input type = "hidden" name = "p_code" value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/homepage/h_title18.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   <br>     
      <table width="970"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="970" align="right" class=txt_input>
		    <a href='javascript:insertPage()'><img src="/images/admin/button/btn_add.gif" border="0"></a>
		  </td>
        </tr>
      </table>
      <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td colspan="10" class="table_top_line"></td>
        </tr>
        <tr> 
        	<td class="table_title" width="9%">NO</td>
            <td class="table_title" width="10%">코드</td>
            <td class="table_title" width="18%">시설명</td>
		  	<td class="table_title" width="39%">URL</td>
            <td class="table_title" width="10%"><b>작성자</b></td>
            <td class="table_title" width="14%" ><b>등록일</b></td>
        </tr>
<%
	if(list.size()>0){
		for(int i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);  
			v_dispnum 	 = dbox.getInt("d_dispnum");
			v_no         = dbox.getInt("d_no");
			v_code       = dbox.getString("d_code");
			v_insname    = dbox.getString("d_insname");
			v_realfile   = dbox.getString("d_realfile");
			v_savefile   = dbox.getString("d_savefile");
			v_url        = dbox.getString("d_url");
			v_luserid    = dbox.getString("d_luserid");
			v_ldate      = dbox.getString("d_ldate");	
			v_realpath   = conf.getProperty("dir.institutionrealpath") + v_code + "/" + v_url;
%>
        <tr> 
         	<td class="table_01"><%=v_dispnum%></td>
            <td class="table_02_1"><%=v_code%></td>
            <td class="table_02_2"><a href="javascript:updatePage(<%=v_no%>,'<%=v_code%>')"><%=v_insname%></a></td>
            <td class="table_02_2"><a href="<%=v_realpath%>" target="_new"><%=v_url%></a></td>
            <td class="table_02_2"><%=v_luserid%></td>
		  	<td class="table_02_1"><%=FormatDate.getFormatDate(v_ldate, "yyyy/MM/dd")%></td>
        </tr>
<%
        }
	}else{

%>
		<tr>
		  <td colspan='15' class="table_02_1">
				등록 된 내용이 없습니다.		
		  </td>
		</tr>
<%
        }
%>
      </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
