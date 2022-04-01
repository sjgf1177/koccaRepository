<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 운영자에게 메일 보내는 화면
//  2. 프로그램명: zu_CpMail.jsp
//  3. 개      요: 외주관리 시스템 운영자에게 메일 보내는 화면
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
ArrayList list = (ArrayList)request.getAttribute("selectlmail");
String [] v_email ;
String [] v_name; 
String [] v_orga_ename ; 
String [] v_jikwinm ; 
String [] v_jikmunm ; 
String [] v_cono ;



v_email = new String[2];
v_name = new String[2]; 
v_orga_ename = new String[2]; 
v_jikwinm = new String[2]; 
v_jikmunm = new String[2]; 
v_cono = new String[2];

int v_pageno = box.getInt("p_pageno");



for(int i = 0; i < list.size(); i++) {
	DataBox dbox = (DataBox)list.get(i);  
	
	v_email[i] = dbox.getString("d_email");
	
	v_name[i] =  dbox.getString("d_name");
	
	v_orga_ename[i] =  dbox.getString("d_orga_ename");
	
	v_jikwinm[i] =  dbox.getString("d_jikwinm");
	
	v_jikmunm[i] =  dbox.getString("d_jikmnm");
	
	v_cono[i] =  dbox.getString("d_cono");
	
	

}

//------------------------------------------------------------------------------------------------------
if (v_email[1] == null){
	v_email[1] = v_email[0];
	
}
if (v_name[1] == null){
	v_name[1] = v_name[0];
	
}

if (v_orga_ename[1] == null){
	v_orga_ename[1] = v_orga_ename[0];
	
}

if (v_jikwinm[1] == null){
	v_jikwinm[1] = v_jikwinm[0];
	
}

if (v_jikmunm[1] == null){
	v_jikmunm[1] = v_jikmunm[0];
	
}

if (v_cono[1] == null){
	v_cono[1] = v_cono[0];
	
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리 시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
<script language="JavaScript" type="text/JavaScript">
<!--



function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}



function toSend() {
	if(form1.p_title.value == ""){
		alert("제목을 입력해 주세요!");
	}
	else if (form1.p_content.value == ""){
		alert("내용을 입력해주세요!");
	
	}else{
		document.form1.action = "/servlet/controller.cp.CpAdminServlet";
		document.form1.p_process.value = "send";      
		document.form1.submit();
	}
}
function toClose(){
document.form1.target = window.opener.name;
document.form1.action = "/servlet/controller.cp.CpAdminServlet";
document.form1.p_process.value = "adminiList";
document.form1.submit();
self.close();
}
//-->
</script>
</head>

<body  topmargin=0 leftmargin=0>
<FORM METHOD=POST name="form1">
<input type='hidden' name='p_process'	value=''>
<input type='hidden' name='p_toemail'	value='<%=v_email[0]%>'>
<input type='hidden' name='p_cono'	value='<%=v_cono[0]%>'>
<table width="520" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/gate/L_poptop.gif" width="520" height="150"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="520" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="28">&nbsp;</td>
    <td width="464"><table width="463" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="1" colspan="3" bgcolor=B6B6B6></td>
        </tr>
        <tr bgcolor="F7F3EF"> 
          <td width="54" height="34" bgcolor="FFEEEF" class=b_title>수신</td>
          <td width="6" bgcolor="FFEEEF"><img src="/images/gate/line.gif" width="1" height="9"></td>
          <td width="403" height="34" bgcolor="FFEEEF">&nbsp; <%=v_orga_ename[1]%> &nbsp; <%=v_name[1]%> &nbsp; <%=v_jikwinm[1]%> </td>

        </tr>
        <tr> 
          <td colspan="3" class=dot_line></td>
        </tr>
        <tr bgcolor="F7F7F7"> 
          <td width="54" height="34" class=b_title>제목</td>
          <td width="6" bgcolor="F7F7F7"><img src="/images/gate/line.gif" width="1" height="9"></td>
          <td height="34" bgcolor="F7F7F7">&nbsp; <input size=30 name="p_title"></td>
        </tr>
        <tr> 
          <td colspan="3" class=dot_line></td>
        </tr>
        <tr bgcolor="F7F7F7"> 
          <td width="54" height="34" valign="top" class=b_title style=padding-top:10px>내용 </td>
          <td width="6" bgcolor="F7F7F7" valign="top" style=padding-top:13px><img src="/images/gate/line.gif" width="1" height="9"></td>
          <td style=padding-top:10px>&nbsp; <textarea name="p_content" cols="50" rows="20"></textarea> 
            <br> <br> </td>
        </tr>
        <tr> 
          <td height="1" colspan="3" bgcolor=B6B6B6></td>
        </tr>
        <tr> 
          <td height="10" colspan="3"></td>
        </tr>
        <tr align="right"> 
          <td height="5" colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="403" align="right"><a href="javascript:toSend()"><img src="/images/gate/btn_popanswer.gif" width="74" height="22" border="0"></a></td>
                <td width="10">&nbsp;</td>
                <td width="51"><a href="javascript:toClose()"><img src="/images/gate/btn_popclose.gif" width="50" height="22" border="0"></a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td width="28">&nbsp;</td>
  </tr>
  <tr> 
    <td height="30">&nbsp;</td>
    <td height="30">&nbsp;</td>
    <td height="30">&nbsp;</td>
  </tr>
  <tr bgcolor="D0D0D0"> 
    <td height="8" colspan="3"></td>
  </tr>
</table>
</form>
</body>
</html>
