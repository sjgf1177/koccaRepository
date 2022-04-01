<%
/**
 * file name : zu_loginbulletin.jsp
 * date      : 2003/5/28
 * programmer: jinhee park jj1004@dreamwiz.com
 * function  : LOGIN form
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.sample.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<html>
<head>
<title>HHI Cyber Education</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<SCRIPT LANGUAGE="JavaScript">
<!--
    /* 패스워드 엔터키 체크 */
    function password_enter(e)
    {
        if (e.keyCode =='13'){	login();  }
    }
    function login()
    {
        if (input_chk(form1)){            
            form1.p_process.value   = "insertLogin";            
        	form1.action            = "/servlet/controller.sample.LoginBulletinServlet";		    					
        	form1.submit();
        }		
    }
    function input_chk(form)
    {
    	var chk = (
    		isNull(form.elements["p_user_id"],"아이디") &&		
    		checkLength(form.elements["p_user_pwd"],"비밀번호",4)  
    		);
    	
    	return (chk);
    }
    // null,자릿수 체크 ( 필드명,메세지,길이 ) /*완료
    function checkLength ( theField, s, Length)
    {
    	if (theField.value == "" || theField.value == null ) 
    	{	
    		theField.value="";  //폼창에 NULL값 리턴		
    		alert( s + "를 입력하십시요.");
    		return(false);
    	}	
    	
    	if (theField.value.length < Length)
    	{
    		theField.value="";  //폼창에 NULL값 리턴
    		alert( Length+"글자 이상 이어야 합니다.");
    		return(false);
    	}
    	
    	return(true);
    }
    // null 값 체크 /*완료
    function isNull ( theField, s ) 
    {
    	if (theField.value == "" || theField.value == null )
    	{
    		theField.value="";  //폼창에 NULL값 리턴
    		//theField.focus();
    		//theField.select();
    		alert( s + "을(를) 입력하십시요.");
    		return(false);
    	}
    	
    	return(true);
    }
    function login_focus() {
      form1.p_user_id.focus();
    }
//-->
</SCRIPT>

</head>
<body leftmargin="0" topmargin="0" onLoad='login_focus()'>
<form name = "form1" method = "post">
<input type = "hidden" name = "p_process">  
<table height="100%" border="0" cellpadding="0" cellspacing="0" align="center">
<tr>
    <td align="center">
    <table cellspacing=0 cellpadding=0 align=center border=0>
    <tr align="center">
        <td align="center">
        <table cellspacing=0 cellpadding=0 align=center border=1>
        <form name=form1 action="" method=post >
        <tr height=35>
            <td width=97><font color=#000000>아이디</font></td>
            <td><input class=input name="p_user_id" value=""></td>        
        </tr>
        <tr>
            <td width=97><font color=#000000>패스워드</font></td>
            <td><input class=input onkeypress=password_enter(event) type=password name="p_user_pwd"></td>
        </tr>
        <tr>
        <td colspan=2 align=center><input type=button value='로그인' onClick="javascript:login()"></td>
        </tr>
        </table>
        </td>
    </tr>
    </table>
    </td>
</tr>
</table>   
<%@ include file = "/learn/library/getJspName.jsp" %>	
</form> 
</body>
</html>
