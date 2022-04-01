<html>
<head>
<title> :::: 사이버 문화콘텐츠 아카데미 :::: </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
<script language="JavaScript" type="text/JavaScript">
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
</script>
</head>

<body bgcolor="#FFFFFF" background="/images/user/kocca/course/<%=tem_subimgpath%>/subbg.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" <%=onLoad%>>

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
                <td rowspan="3" width="203" height="80" valign="top"><a href="/indexKocca.jsp"><img src="<%=tem_imgpath%><%=tem_toplogo%>" border="0"></a></td>
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
                <td height="42" colspan="2">
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
          <td width="916" valign="top"><table width="916" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="195" valign="top"><table width="195" border="0" cellpadding="0" cellspacing="0">
                    <tr> 
                      <td height="28" colspan="3">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="12">&nbsp;</td>
                      <td width="182" valign="bottom">					  
					  <!-- mycourse -->
						<%@ include file="/learn/user/kocca/include/mycourse.jsp"%>
					  <!-- mycourse --></td>
                      <td width="1" valign="bottom">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table>


                  <!-----------------  left menu 시작 ----------------->
                  <%@ include file="/learn/user/kocca/include/leftmenuCommonA.jsp"%>
                  <!-----------------  left menu 끝 ------------------>

				</td>
                <td width="721" valign="top"><table width="721" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/kocca/common/<%=tem_subimgpath%>/subimg.jpg" ></td>
                    </tr>
                  </table>
                  <table width="721" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                    <tr> 
                      <td width="20">&nbsp;</td>
                      <td valign="top">
