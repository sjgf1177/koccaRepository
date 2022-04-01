<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 회사별게시판 상세화면
//  2. 프로그램명: zu_CpCompany_R.jsp
//  3. 개      요: 외주관리 시스템 회사별게시판 상세화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08. 06
//  7. 수      정: 이연정 2005. 08. 06
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "";
String v_okyn1  = "", v_okuserid1 = "",v_okdate1="",v_okyn2 ="",v_okuserid2 = "",v_okdate2 ="", v_cpnm="";
String v_kind = "";

int    v_seq        = box.getInt("p_seq");
int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
int    v_pageno     = box.getInt("p_pageno");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select     = box.getString("p_select"); 


Vector v_realfileVector = new Vector();
Vector v_savefileVector = new Vector();

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid     = dbox.getString("d_userid");
v_name       = dbox.getString("d_name");
v_indate     = dbox.getString("d_indate");
v_title      = dbox.getString("d_title");
v_seq        = dbox.getInt("d_seq");
v_content    = dbox.getString("d_content");
v_kind       = dbox.getString("d_kind");
v_okyn1      = dbox.getString("d_okyn1");
v_okuserid1  = dbox.getString("d_okuserid1");
v_okdate1    = dbox.getString("d_okdate1");
v_okyn2      = dbox.getString("d_okyn2");
v_okuserid2  = dbox.getString("d_okuserid2");
v_okdate2    = dbox.getString("d_okdate2");
v_realfileVector    = (Vector)dbox.getObject("d_realfile");
v_savefileVector    = (Vector)dbox.getObject("d_savefile");
v_cpnm = dbox.getString("d_cpnm");

int    v_filecount  = v_realfileVector.size();
%>

<html>
<head>
<title>회사별 게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript">
//리스트화면으로 이동
function selectList() {
	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
	document.form1.p_process.value = "selectList";     
	document.form1.submit();	
}            

//수정화면으로 이동
function updatePage() {
	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}

//삭제하기
function deleteAction() {
    if (confirm("정말로 삭제하시겠습니까?")) {
        document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }
    else {
        return;
    }
}

//수정한 내용을 저장
function viewsave(okyn1) {
	if(okyn1=='N' && document.form1.p_okyn1.checked == false){
		alert("확인체크를 해주십시오");
		return;
	}
	if(okyn1 == 'Y' && document.form1.p_okyn2.checked == false){
		alert("확인체크를 해주십시오");
		return;
	}
	if(document.form1.p_okyn1.checked == true){
		document.form1.p_okyn1.value = "Y";
	}else{
		document.form1.p_okyn1.value = "N";
	}
	if(document.form1.p_okyn2.checked == true){
		document.form1.p_okyn2.value = "Y";
	}else{
		document.form1.p_okyn2.value = "N";
	}
	//alert(document.form1.p_okyn1.value);
	//alert(document.form1.p_okyn2.value);
	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
	document.form1.p_process.value = "viewsave";   	
	document.form1.submit();

}
//답변하기
function replyPage()
{
  document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
  //alert(document.form1.action);
  document.form1.p_process.value = "replyPage";   	
  //alert(document.form1.p_process.value);
  document.form1.submit();
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	 <form name = "form1" method = "post">
     	<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
     	<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
     	<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
     	<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
     	<input type = "hidden" name = "p_process"     value = "">
     	<input type = "hidden" name = "p_userid"      value = "<%= v_userid %>">
	 	<input type = "hidden" name = "p_approval1"   value= "<%=v_okyn1%>">
	 	<input type = "hidden" name = "p_approval2"   value= "<%=v_okyn2%>">
	 	<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
	 	<input type = "hidden" name = "p_type"        value = "AF">
	 	<input type = "hidden" name = "p_kind"      value = "<%=v_kind%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
     <!----------------- title 끝 ----------------->
     <br>
	 <!----------------- 내용보기 시작 ----------------->
	 <table width="97%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
		<td  class=sub_title>회사별리스트 상세화면보기</td>
		<td align = "right">
		<%if(!(("Y".equals(v_okyn1))&&("Y".equals(v_okyn2)))){%>
		  <a href="javascript:viewsave('<%=v_okyn1%>')"><img src="/images/admin/button/btn_save.gif" border="0"></a>
		<%}%>
		<%if(!v_okyn1.equals("Y")){%>
		  <a href="javascript:updatePage()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
		<%}%>
		  <a href="javascript:deleteAction()"><img src="/images/admin/button/btn_del.gif" border="0"></a>
		  <a href="javascript:replyPage()"><img src="/images/admin/button/btn_answer.gif" border="0"></a>
          <a href="javascript:selectList()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
		</td>
	  </tr>
	  <tr>
		<td height="3"></td>
	  </tr>
	</table>
	<table cellspacing="1" cellpadding="5" class="table_out">
       <tr> 
           <td width="13%" height="25" class="table_title">회사명</td>
           <td class="table_02_2"><%=v_cpnm%></td>
           <td width="13%" height="25" class="table_title" >작성자</td>
           <td class="table_02_2"><%=v_name%></td>
       </tr>
       <tr> 
           <td class="table_title" class="table_title">첨부파일 </td>
           <td class="table_02_2" colspan="3">
			<%		
				if( v_realfileVector != null ) { 
					for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

						String v_realfile = (String)v_realfileVector.elementAt(i);
						String v_savefile = (String)v_savefileVector.elementAt(i);  	

						if(v_realfile != null && !v_realfile.equals("")) {  %>
							&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
					
							<%= v_realfile%></a><br>
							<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
				<%		}   %>
		<%			}
				}
		%>
		   </td>
       </tr>
	   <tr> 
           <td class="table_title" class="table_title">제목 </td>
           <td class="table_02_2" colspan="3"><%=v_title%></td>

       </tr>
       <tr> 
           <td class="table_title" class="table_title">내용 </td>
           <td class="table_02_2" colspan="3"><%=v_content%></td>
       </tr>
	  <%
	  String okyn2str = "";
	  if(v_okyn1.equals("Y")){
		  if(v_okyn2.equals("Y")){
			okyn2str ="disabled checked";
		  }
	  } else {					
			okyn2str ="disabled";
	  }
	  %>
       <tr> 
           <td width="13%" height="25" class="table_title"><b> 확인1 </b>
			<input type = "checkbox" name="p_okyn1" <%=(v_okyn1.equals("Y") ? "disabled checked":"")%>>
		  </td>
           <td width="35%" class="table_02_2"><%=v_okuserid1 %></td>
           <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
           <td class="table_02_2"><%=FormatDate.getFormatDate(v_okdate1, "yyyy/MM/dd")%></td>
       </tr>
       <tr> 
           <td width="13%" height="25" class="table_title"><b> 확인2 </b>
			<input type = "checkbox" name="p_okyn2" <%=okyn2str%>>
		  </td>
           <td width="35%" class="table_02_2"><%=v_okuserid2 %></td>
           <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
           <td class="table_02_2"><%=FormatDate.getFormatDate(v_okdate2, "yyyy/MM/dd")%></td>
       </tr> 
     </table>
        <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
