<%
//**********************************************************
//  1. 제      목: Loadmap
//  2. 프로그램명 : pop_Loadmap.jsp
//  3. 개      요: Loadmap 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 06.01.16
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<html>
<head>
<title> ::: 과정로드맵 ::: </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
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
<SCRIPT LANGUAGE="JavaScript">
<!--

function move(tab){
	document.form1.p_process.value= "select2";
	document.form1.p_tab.value = tab;
	document.form1.action = "/servlet/controller.course.SubjLoadMapServlet";
	document.form1.submit();
}
2006-02-06
//-->
</SCRIPT>

</head>

<body  onLoad="MM_preloadImages('/images/user/kocca/course/tab2_02on.gif','/images/user/kocca/course/tab2_03on.gif','/images/user/kocca/course/tab2_04on.gif','/images/user/kocca/course/tab2_05on.gif','/images/user/kocca/course/tab2_06on.gif','/images/user/kocca/course/tab11_03on.gif','/images/user/kocca/course/tab11_02on.gif')" topmargin="0" leftmargin="0">
<form name = "form1" method="post">
	<input type="hidden" name ="p_process">
	<input type="hidden" name ="p_tab">
</form>
<table border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/user/kocca/homepage/new_img/road03.gif"></td>
  </tr>
 </table>
</body>
</html>
