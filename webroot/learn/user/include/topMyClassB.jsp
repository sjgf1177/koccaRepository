<%
    String complogo        = box.getSession("complogo");
%>

<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style2.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

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

<body background="<%=tem_imgpath%><%=tem_subbg%>" leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" <%=onLoad%>>

<!----------------- 공통폼 / 공통스크립트 시작 ----------------->
<%@ include file="/learn/user/include/common.jsp"%>
<!----------------- 공통폼 / 공통스크립트 끝 ------------------>

<!-- top -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="<%=tem_imgpath%><%=tem_subtopbg%>">
  <tr>
    <td valign="top">
	  <table width="900" height="52" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="315">&nbsp;</td>
          <td align="right"></td>
        </tr>
        <tr> 
          <td width="250" align="center" valign="top"><a href="/">
          <% if(complogo.equals("")){%>
            <img src="<%=tem_imgpath%><%=tem_toplogo%>" width="160" height="30">
          <%}else{%>
            <img src="<%=complogo%>" width="160" height="30">
          <%}%>
		  </a></td>
          <td height="34" align="right" valign="middle">
            <!----------------- global menu 시작 ----------------->
            <%@ include file="/learn/user/include/globalmenuB.jsp"%>
            <!-----------------  global menu 끝 ------------------>
		  </td>
        </tr>
      </table>
	  <!-- navigation table -->
	  <table width="900"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="315" valign="top" class="mcolor1"></td>
          <td align="right" valign="top"> 

            <!----------------- top menu 시작 ----------------->
            <%@ include file="/learn/user/include/topmenuB.jsp"%>
            <!-----------------  top menu 끝 ------------------>
		  </td>
        </tr>
      </table>
	  <!-- subtopimg table -->
      <table width="900" border="0" cellspacing="0" cellpadding="0">
           <tr>
            
          <td><img src="<%=tem_imgpath%><%=tem_subtopimg%>"></td>
           </tr>
      </table>

	  </td>
  </tr>
</table>
<!-- center -->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td valign="top">
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="170" valign="top">
		   <table width="170" border="0" cellpadding="0" cellspacing="0"  >
              <tr> 
                <td colspan="2" ><img src="/images/user/homepage/type2/left_top.gif"></td>
              </tr>
              <tr> 
                <td width="18" valign="top" background="/images/user/homepage/type2/bg_left.gif" >
                </td>
            <!----------------- top left menu 시작 ----------------->
            <%@ include file="/learn/user/include/leftmenuMyClassB.jsp"%>
            <!-----------------  top left menu 끝 ------------------>
              </tr>
              <tr> 
                <td colspan="2"><img src="/images/user/homepage/type2/left_bottom.gif"></td>
              </tr>
              <tr> 
                <td colspan="2">&nbsp;</td>
              </tr>
            </table>
		   </td>
          <td valign="top" background="/images/user/homepage/type2/bg_subcenter.gif">