<%
    String topmenu  = "0"; 
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<meta http-equiv="Pragma" content="no-cache" >
<TITLE> :::: 사이버 문화콘텐츠 아카데미 :::: </TITLE>
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
</HEAD>

<BODY bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" background="<%=tem_imgpath%><%=tem_mainbg%>" <%=onLoad%>>

<!----------------- 공통폼 / 메뉴이동스크립트 시작 ----------------->
<%@ include file="/learn/user/kocca/include/common.jsp"%>
<!----------------- 공통폼 / 메뉴이동스크립트 끝 ------------------>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
<!-- 테이블 탑부분 -->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="916">
            <table width="916" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td rowspan="3" width="203" height="80" valign="top"><img src="<%=tem_imgpath%><%=tem_toplogo%>" width="203" height="80"></td>
                <td colspan="2"> 
                </td>
              </tr>
              <tr>
                <td height="37" colspan="2" align="right" valign="bottom">
                  <!----------------- global menu 시작 ----------------->
                  <%@ include file="/learn/user/kocca/include/globalmenuA.jsp"%>
                  <!-----------------  global menu 끝 ------------------>
                </td>
              </tr>
              <tr> 
                <td height="42" colspan="2" valign="top">
                  <!----------------- menu flash 시작 ----------------->
                  <%@ include file="/learn/user/kocca/include/menuflashA.jsp"%>
                  <!----------------- menu flash 끝 ------------------>
                </td>
              </tr>
            </table>
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>
<!-- 테이블 센터부분-->     
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="916"> 
            <table width="916" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>
				<script language = "javascript" src = "/script/user_patch.js"></script>
				<script language='javascript'>
					setParam1('<%=tem_imgpath+""+tem_mainflash%>');
					setParam2('916');
					setParam3('222');
					//alert("1111");
					object_patch('leftmenuB');
					setParam1('');
					setParam2('');
					setParam3('');
				</script>
					<!--object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="916" height="222">
                    <param name="movie" value="<%//=tem_imgpath%><%//=tem_mainflash%>">
                    <param name="quality" value="high">
                    <embed src="<%//=tem_imgpath%><%//=tem_mainflash%>" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="916" height="222"></embed></object-->
				</td>
              </tr>
            </table>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td width="916"><table width="916" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="182" valign="top" background="/images/user/kocca/homepage/type1/bg_left_contents.gif">
