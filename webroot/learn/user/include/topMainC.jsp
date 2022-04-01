<%
    String topmenu  = "0";
    String complogo        = box.getSession("complogo");
%>

<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style3.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
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

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}


    // 로그 아웃 처리
    function goLoginout() {
        if (confirm("로그아웃 하시겠습니까?")) {
            document.frmLogin.p_process.value   = "logout";
            document.frmLogin.action            = "/servlet/controller.homepage.LoginServlet";
            document.frmLogin.submit();
        } else {
            return;
        }
    }

    // 페이지 초기화
    function winonload() {
    <% if (!bLoginCheck) {%>
        document.frmLogin.p_userid.focus();
    <% } else { %>
        return;
    <% } %>
    }

// 관련 사이트 링크 배너
function goSiteBanner(grcode,seq,loginCheck) {
    if(loginCheck == "Y"){
    <% if(box.getSession("userid").equals("")) {%>
        alert("로그인 후 이용해주세요");
        return;
    <%}%>
    }
    window.open("/learn/user/homepage/zu_LinkSite_Action.jsp?p_grcode="+grcode+"&p_seq="+seq, "SiteLink", "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=700,height=480,left=80,top=100");
}

// 관련 사이트 링크 셀렉트박스
function goSiteSelect() {
    var link1 = document.linkform.link.value;
    var arr = link1.split(':');

    if(arr[2] == "Y"){
    <% if(box.getSession("userid").equals("")) {%>
        alert("로그인 후 이용해주세요");
        return;
    <%}%>
    }
    window.open("/learn/user/homepage/zu_LinkSite_Action.jsp?p_grcode="+arr[0]+"&p_seq="+arr[1], "SiteLink", "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=700,height=480,left=80,top=100");
}

//-->
</script>
</head>

<body background="<%=tem_imgpath%><%=tem_mainbg%>" leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" <%=onLoad%>>

<!----------------- 공통폼 / 공통스크립트 시작 ----------------->
<%@ include file="/learn/user/include/common.jsp"%>
<!----------------- 공통폼 / 공통스크립트 끝 ------------------>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <table width="870" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="170" align="center" valign="top">
            <table width="170" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top">
                  <table width="170" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center"><a href="/">
                      <% if(complogo.equals("")){%>
                        <img src="<%=tem_imgpath%><%=tem_toplogo%>" width="158" height="61">
                      <%}else{%>
                        <img src="<%=complogo%>" width="158" height="61">
                      <%}%>
                     </a></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="173" align="center" valign="bottom">
                <!-- 로그인 -->
                <!--  login table start -->
                <jsp:include page="/learn/user/homepage/login.jsp" flush="true" />
                <!--  login table end -->
                </td>
              </tr>
            </table>
          </td>
          <td valign="top">
          <table width="690" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="23" align="right" valign="bottom">

            <!-- global menu table -->
            <!----------------- global menu 시작 ----------------->
            <%@ include file="/learn/user/include/globalmenuC.jsp"%>
            <!-----------------  global menu 끝 ------------------>

                </td>
              </tr>
              <tr>
                <td>
                  <!--플래쉬 left 시작-->
				  <script language = "javascript" src = "/script/user_patch.js"></script>
<script language='javascript'>
			setParam1('<%=tem_imgpath+""+tem_mainflash%>');
			setParam2('690');
			setParam3('168');
			//alert("1111");
			object_patch('leftmenuB');
			setParam1('');
			setParam2('');
			setParam3('');
			</script>
                  <!--object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="690" height="168">
                  <param name="movie" value="<%//=tem_imgpath+""+tem_mainflash%>">
                  <param name="quality" value="high">
                  <embed src="<%//=tem_imgpath+""+tem_mainflash%>" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="690" height="168"></embed></object-->
                  <!--플래쉬 left 종료-->
                </td>
              </tr>
            </table>

            <!----------------- top menu 시작 ----------------->
            <%@ include file="/learn/user/include/topmenuC.jsp"%>
            <!-----------------  top menu 끝 ------------------>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>