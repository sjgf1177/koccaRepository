<%
    String topmenu  = "0"; 
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<meta http-equiv="Pragma" content="no-cache" >
<TITLE> :::: 사이버 게임 아카데미 :::: </TITLE>
<link rel="stylesheet" type="text/css" href="/css/user_style2.css" >
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
</HEAD>

<BODY <%=onLoad%>>

<!----------------- 공통폼 / 메뉴이동스크립트 시작 ----------------->
<%@ include file="/learn/user/game/include/common.jsp"%>
<!----------------- 공통폼 / 메뉴이동스크립트 끝 ------------------>

<table width="100%" border="0" cellpadding="0" cellspacing="0" background="<%=tem_imgpath%><%=tem_topbg%>">
  <tr>
    <td>
	<!-- top -->
	  <table width="1000" height="108" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="194">&nbsp;</td>
          <td width="806" align="right">
            <!----------------- global menu 시작 ----------------->
            <%@ include file="/learn/user/game/include/globalmenuB.jsp"%>
            <!-----------------  global menu 끝 ------------------>
          </td>
        </tr>
        <tr> 
          <td valign="bottom"><a href="/indexGame.jsp"><img src="<%=tem_imgpath%><%=tem_toplogo%>" border="0"></a></td>
          <td valign="top">
            <!----------------- menu flash 시작 ----------------->
            <%@ include file="/learn/user/game/include/menuflashB.jsp"%>
            <!----------------- menu flash 끝 ------------------>
          </td>
        </tr>
        <tr>
          <td width="216" valign="bottom" bgcolor="#FFFFFF">&nbsp;</td>
          <td align="right" valign="top" bgcolor="#FFFFFF"><img src="/images/user/game/homepage/type2/con_top.gif"></td>
        </tr>
      </table></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>