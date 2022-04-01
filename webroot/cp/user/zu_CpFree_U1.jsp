<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 자유게시판 수정화면
//  2. 프로그램명: zu_CpFree_U.jsp
//  3. 개      요: 외주관리 시스템 자유게시판 수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
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

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid   = dbox.getString("d_userid");
v_name   = dbox.getString("d_name");
v_indate    = dbox.getString("d_indate");
v_title    = dbox.getString("d_title");
v_seq      = dbox.getInt("d_seq");
content = StringManager.replace(dbox.getString("d_content"),"&amp;","&");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");

//------------------------------------------------------------------------------------------------------
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템자유게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
 <script language = "javascript">
            function selectList() {
                document.form1.action = "/servlet/controller.cp.CpFreeServlet";
                document.form1.p_process.value = "";     
                document.form1.submit();	
            }            
            
            function cancel() {
                document.form1.action = "/servlet/controller.cp.CpFreeServlet";
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
                document.form1.action = "/servlet/controller.cp.CpFreeServlet";
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
        </script>
</head>

<body leftmargin=0 topmargin=0>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<form name = "form1" enctype = "multipart/form-data" method = "POST">
	        <input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
	        <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
	        <input type = "hidden" name = "p_select"  value = "<%= v_select %>">
	        <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
	        <input type = "hidden" name = "p_process"  value = "">
	        <input type = "hidden" name = "p_userid" value = "<%= v_userid %>">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="907" bgcolor="EFEFEA">
	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="830" height="120">
              <param name="movie" value="/images/gate/O_sub01.swf">
			  <param name=wmode value=transparent>
              <param name="quality" value="high">
              <embed src="/images/gate/O_sub01.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="830" height="120"></embed></object>
			  </td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td><table width="949" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="25">&nbsp;</td>
          <td width="85" valign="top"><img src="/images/gate/O_title01.gif" width="85" height="80"></td>
          <td width="25">&nbsp;</td>
          <td width="775"><table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height=6  class=O_boardwrite_top ></td>
              </tr>
              <tr> 
                <td height=3></td>
              </tr>
            </table>
            <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="53" class=b_write><div align="justify">제목</div></td>

				<!-- 제목입력란-->
                <td colspan="2" class=b_textarea><input style="padding:1; border:1 solid #D1D1D1;" type = "text"  onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background = '#ffffff'" 
	                                            name = "p_title" size = "84" maxlength = "100" value = "<%= v_title %>"></td>
										
										
				
              </tr>
              <tr> 
		
                <td class=b_write> <div align="justify">작성자</div></td>
				<td class = "table-content" width = "12" align = "left">&nbsp; </td>
				<!--작성자이름값 얻어오기-->
                <td class = "table-content" width = "710" align = "left"><%= v_name %></td>
              </tr>
              <tr> 
                <td valign="top" class=b_write style="padding-top:10px"><div align="justify">내용</div></td>
				<!--내용입력란-->
                <td colspan="2" class=b_textarea>
				<!-- DHTML Editor  -->
					<%@ include file="/portal/include/DhtmlEditor.jsp" %>
				<!-- DHTML Editor  -->	
				</td>
				  
              </tr>
              <tr> 
                <td height=4></td>
                <td height=4 colspan="2"></td>
              </tr>
              <tr> 
                <td colspan="3" class=b_list_bottom ></td>
              </tr>
              <tr> 
                <td height=8></td>
                <td height=8 colspan="2"></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="83%" align="right"><a href = "javascript:update();"><img src="/images/gate/btn_save.gif" width="50" height="22" border="0"></a></td>
                      <td width="7%"><a href = "javascript:cancel();"><img src="/images/gate/btn_cancel.gif" width="50" height="22" border="0"></a></td>
                      <td width="10%" align="right"><a href = "javascript:selectList();"><img src="/images/gate/btn_list.gif" width="74" height="22" border="0"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td colspan="2">&nbsp;</td>
              </tr>
            </table></td>
          <td width="10">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
          <td width="25" height="50">&nbsp;</td>
          <td width="775" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3"><img src="/images/gate/O_copyright.gif" width="370" height="41"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td class=O_bgline></td>
  </tr>
</table>
</form>
</body>
</html>
