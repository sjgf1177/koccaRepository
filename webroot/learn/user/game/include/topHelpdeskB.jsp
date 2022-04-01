

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="Pragma" content="no-cache" />
<TITLE> :::: 사이버 게임 아카데미 :::: </TITLE>
<link rel="stylesheet" type="text/css" href="/css/user_style2.css" />
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</SCRIPT>
</HEAD>

<BODY background="/images/user/game/helpdesk/<%=tem_subimgpath%>/<%=tem_subbg1%>" <%=onLoad%>>

<!----------------- 공통폼 / 메뉴이동스크립트 시작 ----------------->
<%@ include file="/learn/user/game/include/common.jsp"%>
<!----------------- 공통폼 / 메뉴이동스크립트 끝 ------------------>

<table width="100%" border="0" cellpadding="0" cellspacing="0" >
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
    <td valign="top">
    <!-- center -->
      <table width="1000" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="216" valign="top">
            <!-----------------  left menu 시작 ----------------->
            <%@ include file="/learn/user/game/include/leftmenuHelpdeskB.jsp"%>
            <!-----------------  left menu 끝 ------------------>
          </td>
          <td width="784" valign="top" background="/images/user/game/helpdesk/type2/conbg.gif">
          <table width="784" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  valign="top" class="submargin">
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/user/game/helpdesk/<%=tem_subimgpath%>/<%=tem_subtopimg1%>"></td>
                    </tr>
                  </table>
