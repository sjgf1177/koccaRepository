<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 노동부자료실 수정화면
//  2. 프로그램명: zu_CpWork_U.jsp
//  3. 개      요: 외주관리 시스템 노동부자료실 수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08. 07
//  7. 수      정: 이연정 2005. 08. 07
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

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "", content = "", v_cpnm="";

int    v_seq        = box.getInt("p_seq");
int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
int    v_pageno     = box.getInt("p_pageno");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select     = box.getString("p_select"); 

Vector v_realfileVector = new Vector();
Vector v_savefileVector = new Vector();
Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid     = dbox.getString("d_userid");
v_name       = dbox.getString("d_name");
v_indate     = dbox.getString("d_indate");
v_title      = dbox.getString("d_title");
v_seq        = dbox.getInt("d_seq");
v_content    = dbox.getString("d_content");
content  = v_content;

v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");

v_cpnm = dbox.getString("d_cpnm");

content = StringManager.replace(content,"&nbsp;","&amp;nbsp;");
%>

<html>
<head>
<title>회사별게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
<!--
function initPage()
{
    document.form1.Wec.Value = document.form1.conText.value;
    //document.form1.Wec.Value = document.form1.conText.value;
	//alert(document.form1.conText.value);
}
function cancel() {
    document.form1.action = "/servlet/controller.cp.CpWorkServlet";
	document.form1.p_process.value = "selectList";      
    document.form1.p_searchtext.value = "<%= v_searchtext %>";   
    document.form1.p_select.value = "<%= v_select %>";   
    document.form1.p_pageno.value = "<%= v_pageno %>";     
    document.form1.submit();	
}                        
function update() { 
	document.form1.p_content.value = document.form1.Wec.MIMEValue;
    form1.p_content.value.replace("&","&amp;");
    if (blankCheck(document.all.p_title.value)) {
        alert("제목을 입력하세요!");
        document.all.p_title.focus();
        return;
    }                
    if (realsize(document.all.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        document.all.p_title.focus();
        return;
    }		              
	if (document.form1.Wec.Value.length < 3) {
		alert("내용을 입력하세요");
		return;
	}

    document.form1.action = "/servlet/controller.cp.CpWorkServlet";
	document.form1.p_process.value = "update";              
    document.form1.submit();
}
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}
function realsize( value ) {
    var len = 0;
    if ( value == null ) return 0;
    for(var i=0;i<value.length;i++){
        var c = escape(value.charAt(i));
        if ( c.length == 1 ) len ++;
        else if ( c.indexOf("%u") != -1 ) len += 2;
        else if ( c.indexOf("%") != -1 ) len += c.length/3;
    }
    return len;
}
//-->
	</script>
</head>

<body topmargin=0 leftmargin=0 onload="initPage()">
<form name = "form1" enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"  value = "">
    <input type = "hidden" name = "p_select"      value = "">
    <input type = "hidden" name = "p_pageno"      value = "">
    <input type = "hidden" name = "p_content"     value = "<%=v_content%>">
	<input type = "hidden" name = "p_upfilecnt"   value = "<%=v_upfilecnt %>">
    <input type = "hidden" name = "conText"       value = "<%=content%>">
	<input type = "hidden" name = "p_type"        value = "AD">
	<input type = "hidden" name = "p_process">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title10.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="1" cellpadding="0"  class="table_out">
	    <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="113"  class="table_title">제목</td>
          <td width="857" class=table_02_2>
		  <input type = "text" style="padding:1; border:1 solid #D1D1D1;" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
										name = "p_title" size = "100" maxlength = "100" value = "<%= v_title %>"></td>
        </tr>
        <tr> 
          <td class="table_title">회사명</td>
          <td class=table_02_2><%= v_cpnm %></td>
        </tr>
        <tr> 
          <td class="table_title">작성자</td>
          <td class=table_02_2><%= v_name %></td>
        </tr>
        <tr> 
          <td valign="top" class="table_title"style="padding-top:10px">내용</td>
          <td class=table_02_2> 				
                <!-- 나모 Editor  -->
                <p align="left">
                <OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
                   <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">
                </OBJECT>
                <OBJECT ID="Wec" WIDTH="800" HEIGHT="350"
                CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">
                <param name="InitFileURL" value="/namo/namowec.env">
                <param name="InitFileVer" value="1.0">
                </OBJECT>
                <!-- 나모 Editor  -->
            <br> </td>
        </tr>
        <% if( v_upfilecnt > 0 ) {%>
        <tr> 
          <td height="21" class="table_title"style="padding-top:10px">이전파일</td>

          <td width="484" height="21" class=table_02_2>
			<%		
				if( v_realfileVector != null ) {
					for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
					String v_realfile = (String)v_realfileVector.elementAt(i);
					String v_savefile = (String)v_savefileVector.elementAt(i);  
					String v_fileseq = (String)v_fileseqVector.elementAt(i);  	

					
					if(v_realfile != null && !v_realfile.equals("")) {  %>
						&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
					
                        <%= v_realfile%></a> &nbsp;
						<input type = "checkbox"  name = "p_fileseq<%=i%>" value = "<%= v_fileseq%>"> (삭제시 체크)<br>
						<input type = "hidden" name = "p_realfile"  value = <%=v_realfile%>>
						<input type = "hidden" name = "p_savefile<%=i%>"  value ="<%=v_savefile%>">
			<%		}   %>
		<%			
				}}
		%>
            </td>
        </tr>
         <%}%>

        <tr> 
          <td height="21" class="table_title"style="padding-top:10px">파일첨부</td>
                    <td width="484" height="21" class=table_02_1>
					  &nbsp;&nbsp;<input type="FILE" name="p_file1" size="122" class="input"><br>
					  <input type="FILE" name="p_file2" size="122" class="input"><br>
					  <input type="FILE" name="p_file3" size="122" class="input"><br>
					  <input type="FILE" name="p_file4" size="122" class="input"><br>
					  <input type="FILE" name="p_file5" size="122" class="input"><br>
		  </td>
        </tr>
        </table>
        <br>
        <table >
        <tr> 
          <td class=table_02_1 height="20">&nbsp;</td>
          <td ><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="95%" align="right"><a href = "javascript:update();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
                <td width="1%">&nbsp;</td>
                <td width="4%" align="right"><a href = "javascript:cancel();"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td class=table_02_1>&nbsp;</td>
          <td class=table_02_1>&nbsp;</td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
