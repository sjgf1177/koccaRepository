<%
//**********************************************************
//  1. 제      목: 문의하기 화면
//  2. 프로그램명: zu_ContactUs.jsp
//  3. 개      요: 문의하기 화면
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

String userid = (String)session.getAttribute("userid");

String v_name = "", v_email = "", v_cono = "";
String v_hyuncono = "9563786";
String v_kiacono = "2657659"; 


if (userid != null){
	DataBox dbox = CpContactBean.selectMail(userid);


	if(dbox.getString("d_name") == ""){
		v_name = "";
	}else{
		v_name = dbox.getString("d_name");
	}

	if(dbox.getString("d_email") == ""){
		v_email = "";
	}else{
		v_email = dbox.getString("d_email");
	}

	if(dbox.getString("d_cono") == ""){
		v_cono = "";
	}else{
		v_cono = dbox.getString("d_cono");
	}

}




//------------------------------------------------------------------------------------------------------
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Contact Us</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
<script language="JavaScript" type="text/JavaScript">
<!--

function toSend() {
	if(form1.p_title.value == ""){
		alert("제목을 입력해 주세요!");
	}
	
	else if (form1.p_email.value == ""){
		alert("이메일을 입력해주세요!");
	}
	else if (form1.p_content.value == ""){
		alert("내용을 입력해주세요!");
	
	
	}else{
		document.form1.action = "/servlet/controller.cp.CpContactServlet";
		document.form1.p_process.value = "send";      
		document.form1.submit();
	}
}

//-->
</script>
</head>

<body  topmargin=0 leftmargin=0>
<FORM METHOD=POST name="form1">
<input type = "hidden" name = "p_process"  value = "">
<input type = "hidden" name = "p_cono"  value = "<%=v_cono%>">
<input type = "hidden" name = "p_hyuncono"  value = "<%=v_hyuncono%>">
<input type = "hidden" name = "p_kiacono"  value = "<%=v_kiacono%>">
<table width="520" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/gate/contactus_pop.gif" width="520" height="150"></td>
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
          <td width="54" height="34" bgcolor="FFEEEF" class=b_title>제목</td>
          <td width="6" bgcolor="FFEEEF"><img src="/images/gate/line.gif" width="1" height="9"></td>
          <td width="403" height="34" bgcolor="FFEEEF">&nbsp; <input style="padding:1; border:1 solid #D1D1D1;" size=50 name="p_title"></td>
        </tr>
        <tr bgcolor="F7F3EF"> 
          <td width="54" height="34" bgcolor="FFEEEF" class=b_title>작성자</td>
          <td width="6" bgcolor="FFEEEF"><img src="/images/gate/line.gif" width="1" height="9"></td>
          <td width="403" height="34" bgcolor="FFEEEF">&nbsp; <input  style="padding:1; border:1 solid #D1D1D1;" size=20 name="p_name" value="<%=v_name%>"></td>
        </tr>
        <tr bgcolor="F7F3EF"> 
          <td width="54" height="34" bgcolor="FFEEEF" class=b_title>이메일</td>
          <td width="6" bgcolor="FFEEEF"><img src="/images/gate/line.gif" width="1" height="9"></td>
          <td width="403" height="34" bgcolor="FFEEEF">&nbsp; <input  style="padding:1; border:1 solid #D1D1D1;" size=50 name="p_email" value="<%=v_email%>"></td>
        </tr>
        <tr> 
          <td colspan="3" class=dot_line></td>
        </tr>
        <tr> 
          <td colspan="3" class=dot_line></td>
        </tr>
        <tr bgcolor="F7F7F7"> 
          <td width="54" height="34" valign="top" class=b_title style=padding-top:10px>내용 
          </td>
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
                <td width="51"><a href="javascript:window.close()"><img src="/images/gate/btn_popclose.gif" width="50" height="22" border="0"></a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td width="28">&nbsp;</td>
  </tr>
  <tr> 
    <td height="20"></td>
    <td height="20"></td>
    <td height="20"></td>
  </tr>
  <tr bgcolor="D0D0D0"> 
    <td height="8" colspan="3"></td>
  </tr>
</table>
</FORM>
</body>
</html>
