<html>
<head>
<title> :::: 사이버 문화콘텐츠 아카데미 :::: </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
<link href="/css/user_style_k2.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--



function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function clickHandler() {
  var targetId, srcElement, targetElement, srcCss;
  srcElement = window.event.srcElement;

  if (srcElement.className == "question") {
     targetId = srcElement.id + "details";
     targetElement = document.all(targetId);
     srcCss = document.all(srcElement.id);

     if (targetElement.style.display == "none") {
        targetElement.style.display = "";
        //srcCss.style.color = "#666666";
        //srcCss.style.font = "normal 9pt \"돋움\"";
     } else {
        targetElement.style.display = "none";
        //srcCss.style.color = "";
        //srcCss.style.font = "normal 9pt \"돋움\"";
     }
  }
}

document.onclick = clickHandler;
//-->
</script>
 <SCRIPT>
var old_menu = ''; var old_cell = '';
function comment( submenu, cellbar) {

    if( old_menu != submenu ) {

        if( old_menu !='' ) {
            old_menu.style.display = 'none';
        }
        submenu.style.display = 'block';
        old_menu = submenu;
        old_cell = cellbar;

    } else {
        submenu.style.display = 'none';
        old_menu = '';
        old_cell = '';
    }
}

</SCRIPT>
<style type="text/css">
<!--
.l-padding {
    padding-left: 12px;
}
-->
</style>
</head>

<body bgcolor="#FFFFFF" background="/images/user/kocca/member/<%=tem_subimgpath%>/subbg.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  <%=onLoad%>>

<!----------------- 공통폼 / 메뉴이동스크립트 시작 ----------------->
<%@ include file="/learn/user/kocca/include/common.jsp"%>
<!----------------- 공통폼 / 메뉴이동스크립트 끝 ------------------>


<!-- Left Menu --> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
<!-- 테이블 탑부분 -->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="916">
            <table width="916" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td rowspan="3"  valign="top"><a href="/"><img src="<%=tem_imgpath%><%=tem_toplogo%>" border="0"></a></td>
                <td colspan="2"> 
                </td>
              </tr>
              <tr>
                <td valign="top" align="right"colspan="2"> 
                  <!----------------- global menu 시작 ----------------->
                  <%@ include file="/learn/user/kocca/include/globalmenuA.jsp"%>
                  <!-----------------  global menu 끝 ------------------>
                </td>
              </tr>
              <tr> 
                <td valign="top" colspan="2">
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
                <td height="14" colspan="2" valign="top"></td>
              </tr>
              <tr> 
                <td width="195" valign="top">

                  <!-- mycourse -->
                        <%@ include file="/learn/user/kocca/include/mycourseB.jsp"%>
                  <!-- mycourse -->

                  <!-----------------  left menu 시작 ----------------->
                  <%@ include file="/learn/user/kocca/include/leftmenuCommonB.jsp"%>
                  <!-----------------  left menu 끝 ------------------>
                
                </td>
                <td width="721" valign="top">
                  <table width="721" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="bottom"><img src="/images/user/kocca/member/type2/top_center.gif" width="721" height="10"></td>
                    </tr>
                  </table>
                  <table width="721" border="0" cellpadding="0" cellspacing="0" background="/images/user/kocca/member/type2/bg_center.gif" bgcolor="#FFFFFF">
                    <tr> 
                      <td width="20">&nbsp;</td>
                      <td valign="top">