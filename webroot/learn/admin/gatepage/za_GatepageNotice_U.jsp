<%
//**********************************************************
//  1. 제      목: 게이트시스템 공지사항 관리 수정화면
//  2. 프로그램명: zu_GatepageNotice_U.jsp
//  3. 개      요: 게이트시스템 공지사항 관리 수정화면
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

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "", s_gubun="";

int v_seq = box.getInt("p_seq");
//int v_upfilecnt = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수 
String v_searchtext = box.getString("p_searchtext");
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";
String content = "";
String width = "650";
String height = "200";
String s_usernm = box.getSession("name");

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid   = dbox.getString("d_userid");
v_name   = dbox.getString("d_adname");
v_indate    = dbox.getString("d_addate");
v_title    = dbox.getString("d_adtitle");
v_seq      = dbox.getInt("d_seq");
content = StringManager.replace(dbox.getString("d_adcontent"),"&amp;","&");
				

v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");

//------------------------------------------------------------------------------------------------------

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
            function selectList() {
                document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
			    document.form1.p_process.value = "selectList";     
                document.form1.submit();	
            }            
            
            function cancel() {
                document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
				document.form1.p_process.value = "select";
                document.form1.submit();
            }
            
            function update() { 
				if(document.all.use_editor[0].checked) {        
		            form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
				}else {
					form1.content.value = document.all.txtDetail.value;
			    }
                if (blankCheck(document.form1.p_title.value)) {
                    alert("제목을 입력하세요!");
                    document.form1.p_title.focus();
                    return;
                }                
                if (realsize(document.form1.p_title.value) > 100) {
                    alert("제목은 한글기준 50자를 초과하지 못합니다.");
                    document.form1.p_title.focus();
                    return;
                }		              
                if (blankCheck(document.form1.content.value)) {
                    alert("내용을 입력하세요!");
                    document.form1.content.focus();
                    return;
                }	    
                document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
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

<body topmargin=0 leftmargin=0>
<form name = "form1" enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"  value = "">
    <input type = "hidden" name = "p_select"  value = "">
    <input type = "hidden" name = "p_pageno"  value = "">
	<input type = "hidden" name = "p_process">
	<input type = "hidden" name = "p_gubun" value = "<%=s_gubun%>">
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
          <td width="857" class=b_textarea><input type = "text" style="padding:1; border:1 solid #D1D1D1;" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background = '#ffffff'" 
	                                            name = "p_title" size = "84" maxlength = "100" value = "<%= v_title %>"></td>
        </tr>
        <tr> 
          <td class="table_title">작성자</td>
          <td class=b_textarea><%= s_usernm %></td>
        </tr>
        <tr> 
          <td valign="top" class="table_title" style="padding-top:10px">내용</td>
          <td class=b_textarea> 				
		  <!-- DHTML Editor  -->
					<%@ include file="/portal/include/DhtmlEditor.jsp" %>
				<!-- DHTML Editor  -->
 
            <br> </td>
        </tr>
        <tr> 
          <td height="13" class="table_title" style="padding-top:10px">파일여부</td>
                    <td width="484" height="28">&nbsp;&nbsp;
						                                        <%   for(int i = 0; i < v_realfileVector.size(); i++) {  
	                                                     String v_realfile = (String)v_realfileVector.elementAt(i);
                                                         String v_savefile = (String)v_savefileVector.elementAt(i);
	                                                    if(v_realfile != null) {  %>                                                                                   
                                                    &nbsp;<%= v_realfile%>
                                                    &nbsp;<input type = "checkbox"  onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_savefile" value = "<%= v_savefile%>"> (삭제시 체크)
                                                    <br>                                                    
                                                    <input type = "hidden" name = "p_realfile"  value = "<%= v_realfile%>">
                                                <%        }                                                                
                                                         }   %>
</td>
        </tr>
        <tr> 
                        <td height="13" class="table_title" style="padding-top:10px">                            <p>파일첨부</p>
</td>
                        <td width="484" height="28">                           &nbsp;&nbsp; <input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file">																																	

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
                <td width="95%" align="right"><a href = "javascript:update();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
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
