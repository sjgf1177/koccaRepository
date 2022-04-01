<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 상단 링크화면
//  2. 프로그램명: zu_Include.jsp.jsp
//  3. 개      요: 외주관리 시스템 상단 링크화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
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

function freeList() {
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

function companyList() {
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

function materiaList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else {
	
  }
<% } else { %>
	document.form1.p_process.value   = "selectList";
	document.form1.action            = "/servlet/controller.cp.CpMateriaServlet";
	document.form1.submit()
<% } %>
}

function formList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else {
	
  }
<% } else { %>
	document.form1.p_process.value   = "selectList";
	document.form1.action            = "/servlet/controller.cp.CpFormServlet";
	document.form1.submit()
<% } %>
}

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

function faqList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else {
	
  }
<% } else { %>
	document.form1.p_process.value   = "selectList";
	document.form1.action            = "/servlet/controller.cp.CpFaqServlet";
	document.form1.submit()
<% } %>
}

function indexList() {

	
	document.form1.action            = "/cp/index.jsp";
	document.form1.submit()
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



function MM_openBrWindow() { //v2.0
  window.open('/cp/user/zu_ContactUs.jsp','usppop','width=520,height=628');
}

//-->
</script>     



<body leftmargin=0 topmargin=0>





	
<table width="100%" border="0" cellspacing="0" cellpadding="0">



  <tr>
    <td class=O_top01><table width="950" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="424"><a href="javascript:indexList()" target="_self" onfocus=this.blur()><img src="/images/gate/O_logo.gif" width="350" height="51" border="0"></a></td>
          <td width="224">&nbsp;</td>
		 
          <td width="57" class=login_text  style=padding-top:30px>
		 
		   <%if (ss_username == null){%>
				 <a href="javascript:loginpage()" target="_self">[ 로그인 ]</a>
				<%}else{%>
				 <a href="javascript:goLoginout()" target="_self">[로그아웃]</a>
			  <%}%></a></td>
          <td width="50" valign="bottom"><a href="javascript:indexList()" target="_self" onfocus=this.blur()><img src="/images/gate/O_home.gif" width="50" height="21" border="0"></a></td>
          <td width="76" valign="bottom"><a href="javascript:faqList()" target="_self" onfocus=this.blur()><img src="/images/gate/O_help.gif" width="75" height="21" border="0"></a></td>
          <td width="66" valign="bottom"><a href="javascript:MM_openBrWindow()" onfocus=this.blur()><img src="/images/gate/O_contact.gif" width="65" height="21" border="0" ></a></td>
          <td width="25">&nbsp;</td>
          <td class=O_bgline></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td class=O_top02><table width="950" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width=30>&nbsp;</td>
          <td width="84"><a href="javascript:freeList()"><img src="/images/gate/O_M01.gif" width="84" height="18" border="0"></a></td>
          <td width="81"><a href="javascript:materiaList()" target="_self" onfocus=this.blur()><img src="/images/gate/O_M02.gif" width="81" height="18" border="0"></a></td>
          <td width="118"><a href="javascript:companyList()" target="_self" onfocus=this.blur()><img src="/images/gate/O_M03.gif" width="118" height="18" border="0"></a></td>
          <td width="103"><a href="javascript:formList()" target="_self" onfocus=this.blur()><img src="/images/gate/O_M04.gif" width="103" height="18" border="0"></a></td>
          <td width="84"><a href="javascript:swList()" target="_self" onfocus=this.blur()><img src="/images/gate/O_M05.gif" width="84" height="18" border="0"></a></td>
          <td>&nbsp;</td>
          <td class=O_bgline>&nbsp;</td>
        </tr>
      </table>	</td>
  </tr>
</table>

</body>
</html>
