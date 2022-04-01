<%
//**********************************************************
//  1. 제      목: 외주관리 공지사항 관리 리스트화면
//  2. 프로그램명: zu_GatepageNotice_L.jsp
//  3. 개      요: 외주관리 공지사항 관리 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08. 09
//  7. 수      정: 이연정 2004. 12. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.gatepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0;

String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select     = box.getString("p_select");
int    v_pageno     = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");
String s_usernm = box.getSession("name");
String s_gubun  = "";

s_gubun = box.getString("p_gubun");

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
<!--
//페이지이동
function go(index) {
     document.form1.p_pageno.value = index;
     document.form1.action = "/servlet/controller.cp.GatePageNoticeServlet";
	 document.form1.p_process.value = "";     
     document.form1.submit();
}
//페이지이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.cp.GatePageNoticeServlet";
	 document.form1.p_process.value = "";     
     document.form1.submit();
}
//리스트페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.cp.GatePageNoticeServlet";
	document.form1.p_process.value = "selectList";   
	document.form1.p_pageno.value = "1";       
	document.form1.submit();
}
//입력화면으로 이동
function insertPage() {
	document.form1.action = "/servlet/controller.cp.GatePageNoticeServlet";
	document.form1.p_process.value = "insertPage";  
	document.form1.p_pageno.value = "<%= v_pageno %>";    
	document.form1.submit();
}
//상세화면 보기
function select(num,upfilecnt) {
    document.form1.action = "/servlet/controller.cp.GatePageNoticeServlet";
    document.form1.p_process.value = "select";
    document.form1.p_seq.value = num;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.p_userid.value = "<%=v_userid%>";
    document.form1.p_pageno.value = "<%= v_pageno %>"; 
    document.form1.submit();

}

//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/cp/cp_title06.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>
		<form name = "form1" method = "post">
		<input type = "hidden" name = "p_process" value = "">
		<input type = "hidden" name = "p_pageno" value = "">
		<input type = "hidden" name = "p_seq" value = "">
		<input type = "hidden" name = "p_userid" value = "">
		<input type = "hidden" name = "p_upfilecnt" value = "">
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>외주관리 공지사항 리스트</td>
		  </tr>
          <tr>
            <td height="8"></td>
          </tr>
        </table>

      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="20%">

	  </td>
          <td width="70%" align="right" valign="middle">
		  <select name="p_select" >
                <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
          		<option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
           </select>
 
		  <input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                                                 name = "p_searchtext"  size = "15" maxlength = "15">
          </td>
          <td align="right" valign="middle"><a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/admin/button/search3_butt.gif" border="0" valign="middle">
		  <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
				<a href="javascript:insertPage()"><img src="/images/admin/button/btn_add.gif" border="0"></a>
		<%  }   %>
		</td>
        </tr>
        <tr>
          <td height=5 colspan="10"></td>
        </tr>
      </table>
        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
		  <tr>
			  <td colspan="7" class="table_top_line"></td>
			</tr>
          <tr>
            <td class="table_title" width="10%"><b>번호</b></td>
			<td class="table_title" width="60%"><b>제목</b></td>
            <td class="table_title" width="10%"><b>파일</b></td>
            <td class="table_title" width="20%"><b>날짜</b></td>
          </tr>
				  
<%if(list.size() != 0){%>				  
			<!--내용 시작하는 곳-->
			<%  for(i = 0; i < list.size(); i++) {
					   DataBox dbox = (DataBox)list.get(i);  
					   v_dispnum = dbox.getInt("d_dispnum");
					   //v_userid   = dbox.getString("d_userid");
					   v_name   = dbox.getString("d_adname");
					   v_indate    = dbox.getString("d_addate");
					   v_title    = dbox.getString("d_adtitle");
					   v_seq      = dbox.getInt("d_seq");
					   v_readcnt  = dbox.getInt("d_adreadcnt");
					   v_realmotion  = dbox.getString("d_realmotion");
					   v_savemotion  = dbox.getString("d_savemotion");
					   v_upfilecnt   = dbox.getInt("d_filecnt");
					   v_totalpage = dbox.getInt("d_totalpage");
					   v_rowcount = dbox.getInt("d_rowcount");  
																		
					   if (!v_searchtext.equals("")) {
					   v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					   // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
					   }%>
			<tr> 
                <td class="table_02_1"><%=v_dispnum%></td>
                <td class="table_02_2"><a href = "javascript:select('<%=v_seq%>', '<%=v_upfilecnt%>')"><%= v_title %></a></td>
			    <td class="table_02_1" style="padding-left:10">	  <%if (v_upfilecnt != 0){
						%><img src="/images/admin/common/icon_file.gif" width="13" height="12"><%
					}else{%>
						
			  <%}%></td>
                <td class="table_02_1"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td> 
			</tr>
			<!--내용 끝나는곳-->

			<%}%>	
	<%}else{%>

            <tr>
                <td class=b_list_W colspan="13" align=center >
					등록 된 공지사항이 없습니다.		
				</td>
			</tr>

<%}%>			
 
        </table>
		</form>				
        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle"> 
			<%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %>
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->

      </td>
  </tr>

</table>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
