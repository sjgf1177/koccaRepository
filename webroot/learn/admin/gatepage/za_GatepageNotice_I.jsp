<%
//**********************************************************
//  1. 제      목: 게이트시스템 공지사항 관리 등록화면
//  2. 프로그램명: zu_GatepageNotice_i.jsp
//  3. 개      요: 게이트시스템 공지사항 관리 등록화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
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
 
String v_title = "", v_content = "", v_usernm = "", v_userid = "", v_regdt = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno");
String s_usernm = box.getSession("name");
String content = "";
String width = "650";
String height = "200";
String s_gubun = "";

s_gubun = box.getString("p_gubun");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>GateSystem공지사항</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
<!--
	   function cancel() {
                document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
				document.form1.p_process.value = "selectList";      
                document.form1.p_searchtext.value = "<%= v_searchtext %>";   
                document.form1.p_select.value = "<%= v_select %>";   
                document.form1.p_pageno.value = "<%= v_pageno %>";     
                document.form1.submit();	
            }                        
            function insert() { 

				form1.p_content.value = document.form1.Wec.Value;
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
				if (document.form1.Wec.TextValue.length < 3) {
					alert("내용을 입력하세요");
					return;
				}

	

                document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
				document.form1.p_process.value = "insert";              
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

<body topmargin=0 leftmargin=0>
<form name = "form1" enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
	<input type = "hidden" name = "p_gubun"       value = "<%=s_gubun%>">
    <input type = "hidden" name = "p_searchtext"  value = "">
    <input type = "hidden" name = "p_select"      value = "">
    <input type = "hidden" name = "p_pageno"      value = "">
    <input type = 'hidden' name = "p_content"     value = "">
	<input type = "hidden" name = "p_process">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/gatepage/cp_title06.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="1" cellpadding="0">
	  <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
		<tr>
          <td colspan="2" height=2></td>
        </tr>
        <tr> 
          <td width="113"  class="table_title">제목</td>
          <td width="857" class=b_textarea><input type = "text" style="padding:1; border:1 solid #D1D1D1;" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
										name = "p_title" size = "84" maxlength = "100" value = ""></td>
        </tr>
        <tr> 
          <td class="table_title">작성자</td>
          <td class=b_textarea><%= s_usernm %></td>
        </tr>
        <tr> 
          <td valign="top" class="table_title"style="padding-top:10px">내용</td>
          <td class=b_textarea> 				
                <!-- 나모 Editor  -->
                <p align="center">
							<script language = "javascript" src = "/script/user_patch.js"></script>
							<script language='javascript'>object_namopatch('684','500');</script>
                <!--OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
                   <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">
                </OBJECT>
                <OBJECT ID="Wec" WIDTH="684" HEIGHT="500"
                CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">
                <param name="InitFileURL" value="/namo/namowec.env">
                <param name="InitFileVer" value="1.0">
                </OBJECT-->
                </p>
                <!-- 나모 Editor  -->
 
            <br> </td>
        </tr>
        <tr> 
          <td height="21" class="table_title"style="padding-top:10px">파일첨부</td>
                    <td width="484" height="21" >&nbsp;&nbsp;
					<input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file">																																	
</td>
        </tr>
        <tr> 
          <td colspan="2" class=b_list_bottom></td>
        </tr>
        <tr> 
          <td height=8></td>
          <td height=8></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="95%" align="right"><a href = "javascript:insert();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
                <td width="1%">&nbsp;</td>
                <td width="4%" align="right"><a href = "javascript:cancel();"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</form>
</body>
</html>
