<%
//**********************************************************
//  1. 제	  목: 과목등록 화면
//  2. 프로그램명: za_off_grseqDetail_I.jsp
//  3. 개	  요: 과목등록 화면
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009.11.23
//  7. 수	  정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	
    DataBox data = (DataBox)request.getAttribute("resultData");
	String p_subj = data.getString("d_subj");
	String p_term = data.get("d_term", "1");
	//DEFINED in relation to select START
	String  ss_gyear	 = data.get("d_year", box.getStringDefault("s_gyear", FormatDate.getDate("yyyy")));			//년도
	String  ss_subjseq   = data.get("d_subjseq", box.getString("s_subjseq"));		  //과정 차수
	//DEFINED in relation to select END
%>
<html>
<head>
<title>과목등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>

<SCRIPT LANGUAGE="JavaScript">
<!--

//수정 시작
function updateSubject() {
	  var wec = document.Wec;
	  document.form1.p_explain.value = wec.MIMEValue;
	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'insert';
	document.form1.submit();
}
//수정 끝
function DeleteSubject() {
  if (confirm("과정정보를 삭제 하시겠습니까?")) {
    document.form1.p_process.value = 'delete';
    document.form1.submit();
  }
}

function goSubjectListPage() {
	document.form1.s_action.value = "go";
	document.form1.p_process.value  = 'listPage';
	document.form1.submit();
}

// 멤버 검색 후 처리
function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
  if (tmp1 == 'p_cuser') {
	document.form1.p_cuseridnm.value = name;
	document.form1.p_cuserid.value   = userid;
  } else if (tmp1 == 'p_muser') {
	document.form1.p_museridnm.value = name;
	document.form1.p_muserid.value   = userid;
	document.form1.p_musertel.value   = tmp3;
  }
}


//담당교수 검색
function searchMuser() {
  var p_gubun = 'name';
  var p_key1  = '';//document.form1.p_museridnm.value;
  var p_key2  = '1';
  var url = "/servlet/controller.library.SearchServlet?p_process=tutor&p_gubun="+ p_gubun +"&p_key1="+ p_key1 +"&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
// 담당교수 검색 후 처리
function receiveTutor(userid, name, resno, email, phone, tmp1, tmp2, tmp3, tmp4, tmp5){
	addMuser(userid, name);
//  document.form1.p_museridnm.value = name;
//  document.form1.p_muserid.value   = userid;
//  document.form1.p_musertel.value   = phone;
}

-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="">
<div id="existSubjSeqDiv"></div>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form1" enctype="multipart/form-data" method = "POST" action="/servlet/controller.off.OffSubjectAdminServlet">
	<input type="hidden" name="p_process"	 value="">
	<input type="hidden" name="s_action" value="<%= box.get("s_action") %>">
	<input type="hidden" name="s_year" value="<%= box.get("s_year") %>">
	<input type="hidden" name="s_upperclass"  value="<%= box.get("s_upperclass") %>">
	<input type="hidden" name="s_middleclass" value="<%= box.get("s_middleclass") %>">
	<input type="hidden" name="s_lowerclass" value="<%= box.get("s_lowerclass") %>">
	<input type="hidden" name="s_subjcode" value="<%= box.get("s_subjcode") %>">
	<input type="hidden" name="s_subjseq" value="<%= box.get("s_subjseq") %>">
	<input type="hidden" class="input" id="p_subj" name="p_subj" value="<%= data.get("d_subj") %>" readonly>
	<input type="hidden" class="input" id="p_lecture" name="p_lecture" value="<%= data.get("d_lecture") %>" readonly>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off03.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
</td></tr><tr><td align="center" valign="top">
	  <!----------------- title 끝 ----------------->
	  <%=box.getString("p_warnmsg")%><br>
	  <br>
	<table cellspacing="1" cellpadding="5"  border="0" ALIGN="LEFT">
	  <tr> 
		<td ALIGN="LEFT"><B>과정명 : <%=data.getString("d_subjnm")%></B></td>
	  </tr>
	 </table>
</td></tr><tr><td align="center" valign="top">
	  <!----------------- 과정 등록/수정/삭제/상세보기 (사이버) 시작 ----------------->
		<table cellspacing="1" cellpadding="5" class="table_out" border="0">
		  <tr> 
			<td colspan="5" class="table_top_line"></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>년도</b></td>
			<td class="table_02_2">
				<kocca_select:select name="p_year" sqlNum="off.year.02"  param="<%= p_subj %>"
				onChange="changep_subjseq($('#p_subj').val(), this.value)" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="none" />
			</td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>차수</b></td>
			<td class="table_02_2">
				<kocca_select:select name="p_subjseq" sqlNum="off.getSubjSeq.02"  param="<%= p_subj %>" param2="<%= ss_gyear %>"
				onChange="changep_term($('#p_subj').val(), $('#p_year').val(), this.value);" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="none" />
			</td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>학기</b></td>
			<td class="table_02_2">
				<kocca_select:select name="p_term" sqlNum="off.termlist"  param="<%= p_subj %>" param2="<%= ss_gyear %>" param3="<%= ss_subjseq %>"
				onChange=" " attr=" " selectedValue="<%= p_term %>" isLoad="true" all="none" />
			</td>
		  </tr>
		  <tr> 
			<td  class="table_01">과목명(국문)</td>
			<td class="table_02_2"><input type="text" class="input" name="p_lecturenm" size="38" maxlength="100" value="<%= data.get("d_lecturenm") %>" tabindex=""></td>
		  </tr>
		  <tr> 
			<td  class="table_01">과목명(영문)</td>
			<td class="table_02_2"><input type="text" class="input" name="p_lectureen" size="38" maxlength="100" value="<%= data.get("d_lectureen") %>" tabindex=""></td>
		  </tr>
		  <tr> 
			<td height="31" class="table_01">학점</td>
			<td class="table_02_2" ><input name="p_point" value="<%= data.getInt("d_point")%>" class="input"  size="2" maxlength="1" > 
			</td>
		  </tr>
		  <tr>
			<td  class="table_01">
				<!--운영담당-->담당교수<font color="red">(*)</font> &nbsp;
			</td>
			<td height="34" class="table_02_2" >
				<kocca_select:select name="p_tutorid" sqlNum="offmuserList.1"  param="<%= p_subj %>"
				onChange="" attr=" " selectedValue="" isLoad="true" all="none" />
			</td>
		  </tr>
		  <tr> 
			<td height="31" class="table_01">사용여부</td>
			<td class="table_02_2" > <select name="p_isuse"  class="inputpsearch" onChange="whenIsuse()" tabindex=26>
				<option value='Y'>Y</option>
				<option value='N' <%= data.get("d_isuse").equals("N") ? "selected" : ""  %>>N</option>
			  </select> 
			</td>
		  </tr>
		<tr>
		  <td class="table_title"><b>교육내용</b></td>
		  <td class="table_02_2">
                <!-- 나모 액티브 스퀘어  시작 -->
				<input type= "hidden" name= "p_explain"  id= "p_explain"  value= "<%=com.dunet.common.util.StringUtil.convertHtmlchars(data.getString("d_explain"))%>">
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted('p_explain');</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
			   <!-- 나모 Editor  -->
		  </td>
		</tr>
	  </table>
	  <!----------------- 과정맛보기 등록 끝 ----------------->
	  <br>
	  <!----------------- 등록, 리스트 버튼 시작 ----------------->
	  <table border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td align="center"><a href="javascript:updateSubject()" tabindex=68><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
		  <td width=8></td>
		  <td align="center"><a href="javascript:goSubjectListPage()" tabindex=69><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
            <td align="center" style="display=none">
                <a href="javascript:DeleteSubject()" tabindex=75><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
		</tr>
	  </table>
	  <!----------------- 등록, 리스트 버튼 끝 ----------------->
	  <br>
		</td>
	</tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
