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
v_okyn1      = dbox.getString("d_okyn1");
v_okuserid1  = dbox.getString("d_okuserid1");
v_okdate1    = dbox.getString("d_okdate1");
v_okyn2      = dbox.getString("d_okyn2");
v_okuserid2  = dbox.getString("d_okuserid2");
v_okdate2    = dbox.getString("d_okdate2");
v_realfileVector    = (Vector)dbox.getObject("d_realfile");
v_savefileVector    = (Vector)dbox.getObject("d_savefile");
v_cpnm = dbox.getString("d_cpnm");
String s_name = box.getSession("name");
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

    // 등록 저장
    function insert() {

        if (document.form1.p_title.value == "") {
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

		document.form1.contents.value = document.form1.Wec.MIMEValue;
		document.form1.contents.value.replace("&","&amp;");

        if (document.form1.Wec.TextValue.length < 3) {
            alert("내용을 입력하세요");
            return;
        }
        document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
        document.form1.p_process.value = "reply";
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
	 	<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
	 	<input type = "hidden" name = "p_type"        value = "AF">
	 	<input type = "hidden" name = "contents"      value = "">
	 	
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
		<td  class=sub_title>회사별게시판 답변</td>
		<td align = "right">
		  <a href="javascript:insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a>
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
     </table>
        <br>

		<!----------------- 답변내용 입력하기 ----------------->
    <table cellspacing="1" cellpadding="5" class="table_out">
      <tr> 
        <td width="13%" height="25" class="table_title"><b> 작성자 </b></td>
        <td class="table_02_2"><%=s_name%></td>
      </tr>
      <tr> 
        <td class="table_title" class="table_title">제목 </td>
        <td class="table_02_2"><input type="text"  size="70" name="p_title" class="input" value=""></td>
      </tr>
      <tr> 
        <td class="table_title" class="table_title">내용 </td>
        <td class="table_02_2" >
			<!-- 나모 Editor  -->
            <p align="left">
            <OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
               <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">
            </OBJECT>
            <OBJECT ID="Wec" WIDTH="684" HEIGHT="500"
            CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">
            <param name="InitFileURL" value="/namo/namowec.env">
            <param name="InitFileVer" value="1.0">
            </OBJECT>
            </p>
            <!-- 나모 Editor  -->          
					</td>
        </tr>
        <tr> 
          <td class="table_title" class="table_title">파일첨부 </td>
          <td class="table_02_2">
           <input type="FILE" name="p_file1" size="120" class="input"><br>
           <input type="FILE" name="p_file2" size="120" class="input"><br>
           <input type="FILE" name="p_file3" size="120" class="input"><br>
           <input type="FILE" name="p_file4" size="120" class="input"><br>
           <input type="FILE" name="p_file5" size="120" class="input"><br>
          </td>
        </tr>
      </table>
			<br>
			<table width="97%" border="0" cellspacing="0" cellpadding="0">
				<tr>
						<td align="right"><a href="javascript:insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a>
						<a href="javascript:listpage()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
						</td>
				</tr>
		 </table>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
