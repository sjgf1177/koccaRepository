<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 메인화면
//  2. 프로그램명: zu_Index_L.jsp
//  3. 개      요: 외주관리 시스템 메인화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>       
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0;

String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   

int v_pageno = box.getInt("p_pageno"); 

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
<script language= "javascript" src="/script/cresys_lib.js"></script>
<script language="javascript" type="text/javascript">
<!--
function selectList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인 하셔야 사용 가능합니다.");
  } else {
	
  }
<% } else { %>
	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
				document.form1.p_process.value = "";   
            	document.form1.p_pageno.value = "1";       
            	document.form1.submit();
<% } %>
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

/* 패스워드 엔터키 체크 */
function password_enter(e) { 
    if (e.keyCode =='13'){  
      login();  
    }
}

function login() {
	if (document.form1.p_userid.value=="") {
		alert("사번을 입력하세요");
		document.form1.p_userid.focus();
		return;
	}

	if (document.form1.p_pwd.value=="") {
		alert("비밀번호를 입력하세요");
		document.form1.p_pwd.focus();
		return;
	}
	
	//alert("kkkkkk");

    document.form1.p_process.value   = "login";
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

function pwSearch() {
   document.form1.p_process.value   = "pass";
   document.form1.action            = "/servlet/controller.cp.CpPassServlet";
   document.form1.submit();
}
function adminOpenPage() {
    window.self.name = "winSelectView";
    farwindow = window.open("", "openGATEAdmin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1024, height = 768, top=0, left=0");
    document.form1.target = "openGATEAdmin"
    document.form1.p_systemgubun.value="2";
    document.form1.action = "/learn/admin/system/mScreenFset.jsp";
    document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}

function authChange() {
    document.form1.p_process.value   = "authChange";
    document.form1.action            = "/servlet/controller.cp.CpLoginServlet";
    document.form1.submit();
}

function select(num) {
            	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
				document.form1.p_process.value = "select";
            	document.form1.p_seq.value = num;
            	document.form1.p_userid.value = "<%=v_userid%>";
				document.form1.p_pageno.value = "<%= v_pageno %>"; 
            	document.form1.submit();
}
function selectList() {

	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
	document.form1.p_process.value = "";   
    document.form1.p_pageno.value = "1";       
    document.form1.submit();
}  
    

//-->
</script>

</head>

<body bgcolor="EBECE6" leftmargin=0 topmargin=0 onload=winonload();>

<form name = "form1" method = "post">
	<input type="hidden" name="p_systemgubun" value="">
	<input type='hidden' name='p_process'	value=''>
	<input type='hidden' name='p_frmURL'	value=''>
	
	<input type='hidden' name='p_pageno'	value=''>
	<input type='hidden' name='p_seq'	value=''>
    
  


	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><td><%@include file="/cp/user/zu_Include1.jsp"%></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class=O_bg><table width="949" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="510" height="45">&nbsp;</td>
          <td width="390" height="45">&nbsp;</td>
          <td width="50" height="45">&nbsp;</td>
        </tr>
        <tr> 
          <td>
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="510" height="381">
              <param name="movie" value="/images/gate/O_main.swf">
              <param name="quality" value="high">
              <embed src="/images/gate/O_main.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="510" height="381"></embed></object></td>
              <td width="390" valign="top"><table width="390" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="15"><img src="/images/gate/O_login_c01.gif" width="15" height="12"></td>
                <td width="110"  height="12" bgcolor="E7E2DC"></td>
                <td width="33" bgcolor="E7E2DC"></td>
                <td width="214" bgcolor="E7E2DC"></td>
                <td><img src="/images/gate/O_login_c02.gif" width="17" height="12"></td>
              </tr>
	          <input type='hidden' name='p_frmURL'	value=''>
              <tr>
                <td bgcolor="E7E2DC">&nbsp;</td>
                <td valign="top" bgcolor="E7E2DC"><img src="/images/gate/O_login_title.gif" width="110" height="23"></td>
                <td align="center" bgcolor="E7E2DC"><img src="/images/gate/O_login_line.gif" width="2" height="108"></td>
                <td bgcolor="E7E2DC">

	<% if (session.getAttribute("cpuserid")==null) {%>
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="18%"><img src="/images/gate/O_login_id.gif" width="35" height="12"></td>
                      <td width="56%"> <input name="p_userid" type="text" size="15" class=input tabindex="1"> 
                      </td>
					  <td width="24%" rowspan="3" align="right" height="48">
						<p align="center"><a href="javascript:login()" onfocus=this.blur()><img src="/images/gate/O_login.gif" width="46" height="47" border="0" tabindex="4"></a></p>
					  </td>
                    </tr>
                    <tr> 
                      <td height=3></td>
                      <td height=3></td>
                    </tr>
                    <tr> 
                      <td height="22"><img src="/images/gate/O_login_pw.gif" width="35" height="12"></td>
                      <td height="22"><input name="p_pwd" type="password" size="15" onkeypress="javascript:enterKey_Event('login()', event)" class=input tabindex="2"></td>
                    </tr>
                    <tr>
                      <td height=4></td>
                      <td height=4></td>
                      <td height=4></td>
                    </tr>
    <tr>
        <td colspan="3">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="17%"> <input type="checkbox" name="rememberId" value="1" tabindex="3"> 
                            </td>
                    <td width="56%" class=login_text>아이디 기억하기</td>
                    <td width="26%">
                        <p align="center"><a href="javascript:pwSearch()" target="_self" onfocus=this.blur()><img src="/images/gate/O_login_searchpw.gif" width="76" height="23" border="0"></a></p>
</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height=4></td>
                      <td height=4></td>
                      <td height=4></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                      <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="36%" height="14"></td>
                            <td width="6%">&nbsp;</td>
                            <td width="58%"></td>
                          </tr>
                        </table></td>
                    </tr>

                  </table>
	<!-- 로그인 전 끝 -->
	<%} else { %>
	<!-- 로그인 이후 시작 -->
	<%
		String ss_userid   = (String)session.getAttribute("userid");
		//String ss_username = (String)session.getAttribute("name");
		String ss_gadmin   = (String)session.getAttribute("gadmin");
		String today       = FormatDate.getDate("yyyy년 MM월 dd일 a hh:mm");
		today = StringManager.replace(today,"AM", "오전");
		today = StringManager.replace(today,"PM", "오후");
	
	%>
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
				 <input type='hidden' name='p_userid'	value=''>
                    <tr> 
                      <td><IMG src="/images/main/main_bullet_01.gif" border="0"><font color="1963A0"><b><%=ss_username%></b>님 좋은 하루되세요!!</font></td>
                    </tr>
                    <tr> 
                      <td height=13></td>
                    </tr>
                    <tr> 
                      <td height=4></td>
                    </tr>
                    <tr>
                      <td><%=CpLoginBean.getAuthSelect(ss_userid, "p_auth", ss_gadmin, "onChange = 'javascript:authChange()'")%>
                       <% if (!(ss_gadmin.equals("ZZ") || ss_gadmin.equals(""))) {                     %>
					  <A HREF="#" onClick='adminOpenPage()'><IMG src="/images/main/main_bt_go.gif" border="0"></A>
                      <%
                    	  }
                      %>
                      <table>
                        <tr><td height=5></td></tr>
                      </table>
                      <IMG src="/images/main/main_img30.gif" border="0"> <A HREF="#" onClick="goLoginout()">로그아웃</A></td>
                      
                    </tr>

                  </table>

	<% } %>
                  
                  
                </td>
                <td  width="18" bgcolor="E7E2DC"></td>
              </tr>
	
              <tr> 
                <td><img src="/images/gate/O_login_c03.gif" width="15" height="12"></td>
                <td bgcolor="E7E2DC"></td>
                <td bgcolor="E7E2DC"></td>
                <td bgcolor="E7E2DC"></td>
                <td><img src="/images/gate/O_login_c04.gif" width="17" height="12"></td>
              </tr>
            </table>
            <table width="390" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="14" height="12"></td>
                <td width="375" height="12"></td>
                <td width="15" height="12"></td>
              </tr>
              <tr> 
                <td colspan="3"><a href="javascript:"><img src="/images/gate/O_notice_t.gif" width="390" height="46" border="0" usemap="#Map"></td>
              </tr>
              <tr> 
                <td width="14" background="/images/gate/O_notice_leftbg.gif"></td>
                <td bgcolor="FFFFFF">
				<!--공지사항들어가는 곳-->
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <%if(list.size() != 0){%>
				<%  for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);  
                   
                  
                   v_dispnum = dbox.getInt("d_dispnum");
                   v_name   = dbox.getString("d_adname");
                   v_indate    = dbox.getString("d_addate");
                   v_title    = dbox.getString("d_adtitle");
                   v_seq      = dbox.getInt("d_seq");
				   %>
				<tr> 
					<td width="3%"><img src="/images/gate/O_icon.gif" width="12" height="11"></td>
					<td width="74%" class=notice_list><a href="javascript:select(<%=v_seq%>)" target="_self"><%= v_title %></a></td>
					<td width="23%" class=notice_list><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
				</tr>
				<tr> 
					<td colspan="2" class=dot_line02></td>
					<td colspan="2" class=dot_line02></td>
				</tr>
				<%}%>
<%}else{%>
           

                                                    <tr>
                                                        <td class=notice_list colspan="13" align=center >
															등록 된 내용이 없습니다.		
														</td>
													</tr>

<%}%>
			</table>
				<!--공시사항끝나는곳-->
				  </td></td>
                <td  width="15" background="/images/gate/O_notice_rightbg.gif"></td>
              </tr>
              <tr> 
                <td><img src="/images/gate/O_notice_c01.gif" width="14" height="15"></td>
                <td height="15" background="/images/gate/O_notice_bottombg.gif"></td>
                <td><img src="/images/gate/O_notice_c02.gif" width="15" height="15"></td>
              </tr>
            </table></td>
          <td width="50">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
<map name="Map">
  <area shape="rect" coords="337,15,381,37" href="javascript:selectList()" target="_self" onfocus=this.blur()>
</map>
</FORM>
</body>
</html>
