<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 상단 링크화면
//  2. 프로그램명: zu_Include.jsp.jsp
//  3. 개      요: 외주관리 시스템 상단 링크화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 20
//  7. 수      정: 이연정 2005. 07. 20
//***********************************************************
%>
<%
	String ss_username = (String)session.getAttribute("name");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script language="JavaScript" type="text/JavaScript">
<!--

function loginpage() {
	

    document.form1.p_process.value   = "loginpage";
    document.form1.action            = "/servlet/controller.cp.CpLoginServlet";
    document.form1.submit();
}

function winonload() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    document.form1.p_userid.focus();
  } else {
    document.form1.p_userid.value = getCookie( "userid_cp" );
    document.form1.rememberId.checked = true;
    document.form1.p_pwd.focus();
  }
<% } else { %>
	return;
<% } %>
}


function getCookie( name ) {
    var nameOfCookie = name + "=";
    var x = 0;
    while ( x <= document.cookie.length )
    {
        var y = (x+nameOfCookie.length);
        if ( document.cookie.substring( x, y ) == nameOfCookie ) {
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring( y, endOfCookie ) );
        }
        x = document.cookie.indexOf( " ", x ) + 1;
        if ( x == 0 )
            break;
    }
    return "";
}

function goLoginout() {
    if (confirm("로그아웃 하시겠습니까?")) {
		form1.p_process.value   = "logout";
		form1.action            = "/servlet/controller.cp.CpLoginServlet";
		document.form1.submit();
    } else {
        return;
    }
	  
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

//자유게시판으로 이동
/*function freeList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else{
  
  }
<% } else { %>
	document.form1.p_process.value   = "";
	document.form1.action            = "/servlet/controller.cp.CpFreeServlet";
	document.form1.submit()
<% } %>
}
*/
//회사별 게시판으로 이동
/*function companyList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else {
	
  }
<% } else { %>
	document.form1.p_process.value   = "selectList";
	document.form1.action            = "/servlet/controller.cp.CpCompanyServlet";
	document.form1.submit()
<% } %>
}
*/
//자료실로 이동
//function materiaList() {
<%// if (session.getAttribute("userid")==null) {%>     
  //if( getCookie( "userid_cp" ) == "" ) {
   // alert("로그인을 하셔야 사용 가능합니다");
  //} else {
	
 // }
<%// } else { %>
	//document.form1.p_process.value   = "selectList";
	//document.form1.action            = "/servlet/controller.cp.CpMateriaServlet";
	//document.form1.submit()
<% //} %>
//}

//공지사항으로 이동
function formList() {
<% if (session.getAttribute("userid")==null) {%>     
	  if( getCookie( "userid_cp" ) == "" ) {
		alert("로그인을 하셔야 사용 가능합니다");
	  } else {
	
	  }
<% } else { %>
	document.form1.p_process.value   = "selectList";
	document.form1.action            = "/servlet/controller.cp.CpNoticeServlet";
	document.form1.submit()
<% } %>
}

//소프트웨어 자료실로 이동
function swList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else {
	
  }
<% } else { %>
	document.form1.p_process.value   = "";
	document.form1.action            = "/servlet/controller.cp.CpSwServlet";
	document.form1.submit()
<% } %>
}

//인덱스 화면으로 이동한다.
function indexList() {

	document.form1.p_process.value   = "index";
	document.form1.action            = "/servlet/controller.cp.CpNoticeServlet";
	document.form1.submit()
}

//사용자의 쿠키값을 가져온다.
function getCookie( name ) {
    var nameOfCookie = name + "=";
    var x = 0;
    while ( x <= document.cookie.length )
    {
        var y = (x+nameOfCookie.length);
        if ( document.cookie.substring( x, y ) == nameOfCookie ) {
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring( y, endOfCookie ) );
        }
        x = document.cookie.indexOf( " ", x ) + 1;
        if ( x == 0 )
            break;
    }
    return "";
}



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



<!-- top -->
<table width="100%" height="71" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_top.gif">
  <tr>
    <td valign="top">
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="12"><img src="/images/cp/stop_head.gif"></td>
          <td width="179">&nbsp;</td>
          <td width="709" align="right" valign="top">
		    <table width="400" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">

				<a href="javascript:indexList()" target="_self" onfocus=this.blur()><img src="/images/cp/sm_home.gif"  border="0"></a>
			<%if (ss_username == null){%>
				<a href="javascript:loginpage()" target="_self"><img src="/images/cp/sm_login.gif" border="0"></a>
				<%}else{%>
				<a href="javascript:goLoginout()" target="_self"><img src="/images/cp/sm_logout.gif" border="0"></a>
			<%}%>
				</td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td><img src="/images/cp/top_head.gif" ></td>
          <td valign="top"><a href= "javascript:indexList()"><img src="/images/cp/logo_auto.gif"></a></td>
          <td valign="top">
		  <!-- 대메뉴 table -->
		  <table width="709" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="33"><img src="/images/cp/tmenu_head.gif"></td>
                <td width="655" align="right" background="/images/cp/m_bg.gif"><a href="javascript:formList()"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','/images/cp/m_notice_on.gif',1)"><img src="/images/cp/m_notice.gif" name="Image10"  border="0"></a><a href="javascript:swList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image11','','/images/cp/m_swdata_on.gif',1)"><img src="/images/cp/m_swdata.gif" name="Image11" border="0"></a></td>
                <td width="21">&nbsp;</td>
              </tr>
            </table></td>
        </tr>
      </table> </td>
  </tr>
</table>
