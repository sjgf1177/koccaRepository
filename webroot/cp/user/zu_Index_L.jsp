<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 메인화면
//  2. 프로그램명: zu_Index_L.jsp
//  3. 개      요: 외주관리 시스템 메인화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 18
//  7. 수      정: 이연정 2005. 07. 18
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
RequestBox box1 = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0;

String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
String v_name1 = "", v_indate1 = "",v_title1 = "";
int v_dispnum1 = 0, v_seq1 = 0;


ArrayList list  = (ArrayList)request.getAttribute("selectPdsList");
ArrayList list1 = (ArrayList)request.getAttribute("selectSwList");

int v_pageno = box.getInt("p_pageno"); 

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../../css/cp_style.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--

function selectList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인 하셔야 사용 가능합니다.");
  } else {
	
  }
<% } else { %>
	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
				document.form1.p_process.value = "index";   
            	document.form1.p_pageno.value = "1";       
            	document.form1.submit();
<% } %>
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



//입력이 제대로 되면 쿠키셋팅으로 넘어감
function login() {
	if (document.form1.p_userid.value=="") {
		alert("아이디를 입력하세요");
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

//패스워드 찾기 화면으로 넘어감
function pwSearch() {
   document.form1.p_process.value   = "pass";
   document.form1.action            = "/servlet/controller.cp.CpPassServlet";
   document.form1.submit();
}

// onload 할때 호출
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

//쿠키값 셋팅
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

//로그아웃 명령
function goLoginout() {
    if (confirm("로그아웃 하시겠습니까?")) {
		form1.p_process.value   = "logout";
		form1.action            = "/servlet/controller.cp.CpLoginServlet";
		document.form1.submit();
    } else {
        return;
    }
	  
}

//운영본부 화면 오픈
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

//권한 변경
function authChange() {
    document.form1.p_process.value   = "authChange";
    document.form1.action            = "/servlet/controller.cp.CpNoticeServlet";
    document.form1.submit();
}

//선택
function select(num) {
            	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
				document.form1.p_process.value = "select";
            	document.form1.p_seq.value = num;
            	document.form1.p_userid.value = "<%=v_userid%>";
				document.form1.p_pageno.value = "<%= v_pageno %>"; 
            	document.form1.submit();
}
//조회리스트
function selectList() {

	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
	document.form1.p_process.value = "index";   
    document.form1.p_pageno.value = "1";       
    document.form1.submit();
}  
    
//공지사항 리스트 페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
	document.form1.p_process.value = "selectList";     
	document.form1.submit();	
}            

//S/W 자료실 페이지로 이동
function selectSW() {
	document.form1.action = "/servlet/controller.cp.CpSwServlet";
	document.form1.p_process.value = "selectList";
	document.form1.submit();	
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
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/images/cp/m_board_on.gif','/images/cp/m_data_on.gif','/images/cp/m_company_on.gif','/images/cp/m_sdata_on.gif','/images/cp/m_swdata_on.gif','/images/user/button/btn_idpw_on.gif','/images/user/button/btn_login_on.gif')">
<form name = "form1" method = "post">
<input type="hidden" name="p_systemgubun" value="">
<input type='hidden' name='p_process'	value=''>
<input type='hidden' name='p_frmURL'	value=''>
<input type='hidden' name='p_pageno'	value=''>
<input type='hidden' name='p_seq'	value=''>
<%@include file="zu_Include.jsp"%>
<!-- center -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_total.gif">
  <tr> 
    <td>
	<!-- topimg -->
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="12"><img src="/images/cp/img_head.gif"></td>
          <td width="888" valign="top"><img src="/images/cp/mainimg.jpg"></td>
        </tr>
        <tr valign="top"> 
          <td colspan="2">

		

		<!-- 로그인 table -->

		
		  <table width="900" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="190" align="center" valign="top" bgcolor="F0FBFB">
				  <table width="190" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="10" valign="top"><img src="/images/cp/img_le.gif" width="10" height="130"></td>
                      <td width="169" valign="top">
					    <table width="158" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td>

	<!-- 로그인 하지 않았을때 -->						
	<% if (session.getAttribute("cpuserid")==null) {%>
							<table width="169" height="131"border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="center" valign="top" background="/images/cp/logbox_bg.gif" style="padding-top:40px"> 
									<table width="142" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="142" align="center"> 
										  <table width="132" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="21"><img src="/images/cp/id.gif"></td>
                                              <td width="111"><input name="p_userid" type="text" size="15"  class="input_login"  tabindex="1" maxlength="15">
											  </td>
                                            </tr>
                                            <tr> 
                                              <td><img src="/images/cp/pw.gif" ></td>
                                              <td>
											  <input name="p_pwd" type="password" size="15" maxlength="20" class="input_login" tabindex="2" onKeypress="javascript:enterKey_Event('login()', event)"></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr> 
                                        <td height="8"><img src="/images/user/homepage/log_line.gif" width="139" height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td align="center">
                                          	<a href="javascript:login()" onfocus=this.blur() onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','/images/cp/btn_login_on.gif',1)"><img src="/images/cp/btn_login.gif" name="Image32"border="0"></a> 
                                        	<a href="javascript:pwSearch()" target="_self" onfocus=this.blur() onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/cp/btn_idpw_on.gif',1)"><img src="/images/cp/btn_idpw.gif" name="Image31" border="0"></a>                                           	
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                    </table>
								  </td>
                                </tr>
							  </table>
							<!-- 로그인 전 끝 -->
							<%} else { %>
							<!-- 로그인 이후 시작 -->
							<%
								String ss_userid   = (String)session.getAttribute("userid");
								ss_username = (String)session.getAttribute("name");
								String ss_gadmin   = (String)session.getAttribute("gadmin");
								String today       = FormatDate.getDate("yyyy년 MM월 dd일 a hh:mm");
								today = StringManager.replace(today,"AM", "오전");
								today = StringManager.replace(today,"PM", "오후");
								
							
							%>
							<!-- 로그인 table -->
							<input type='hidden' name='p_userid'	value=''>
							<table width="169" height="131"border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="center" valign="top" background="/images/cp/logbox_bg.gif" style="padding-top:40px"> 
                                    <table width="142" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="142" align="center" class="font02"><strong><%=ss_username%></strong>님 
                                          <br>
                                          좋은 하루되세요!! </td>
                                      </tr>
                                      <tr> 
                                        <td height="8"><img src="/images/user/homepage/log_line.gif" width="139" height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td align="center"><a href="javascript:goLoginout()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','/images/cp/btn_logout_on.gif',1)"><img src="/images/cp/btn_logout.gif" name="Image34" border="0"></a> 
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td align="center">
										<%=CpLoginBean.getAuthSelect(ss_userid, "p_auth", ss_gadmin, "onChange = 'javascript:authChange()'")%>
										   <% if (!(ss_gadmin.equals("ZZ") || ss_gadmin.equals(""))) {                     %> 
										  <A HREF="#" onClick='adminOpenPage()'><img src="/images/user/homepage/btn_go.gif" border="0" align="absmiddle""></A>
										  <%
											  }
										  %>
										  </td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table>
							  
							<%
							  }
							%>

							<!-- 로그인 이후 끝 -->

							</td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table></td>
                      <td width="11" align="right" valign="top"><img src="/images/cp/img_ri.gif" width="11" height="130"></td>
                    </tr>
                  </table></td>
                <td valign="top" background="/images/cp/bg_center.gif">
				  <table width="710" border="0" cellspacing="0" cellpadding="0">
                    <tr valign="top"> 
                      <td height="35" colspan="2"><img src="/images/cp/center_top.gif"></td>
                    </tr>
                    <tr> 
                      <td width="445" align="center" valign="top"> 
                        <!-- notice table start-->
                        <table width="385" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td colspan="4">
							<a href = "javascript:selectList()"><img src="/images/cp/gong_tit.gif" border="0"  usemap="#Map"></a>
							</td>
                          </tr>
                          <tr>
                            <td height="18" colspan="4">&nbsp;</td>
                          </tr>



                          <tr> 
                            <td height="15" colspan="4">
							<table width="384" border="0" cellspacing="0" cellpadding="0">
						  <%if(list.size() != 0){%>
							<%  for(i = 0; i < list.size(); i++) {
								   DataBox dbox = (DataBox)list.get(i);  
								  
								   v_dispnum1 = dbox.getInt("d_dispnum");
								   v_name1    = dbox.getString("d_adname");
								   v_indate1  = dbox.getString("d_addate");
								   v_title1   = dbox.getString("d_adtitle");
								   v_seq1     = dbox.getInt("d_seq");

                                    if(v_title1.length() > 15)
					                	v_title1 = v_title1.substring(0,15)+"...";

								   %>
                                <tr> 
                                  <td width="29" align="center"><img src="/images/cp/bl.gif"></td>
                                  <td width="281" class="notice"><a href="javascript:select(<%=v_seq1%>)" target="_self"><%= v_title1 %></a></td>
                                  <td width="74" class="notice_day"><%= FormatDate.getFormatDate(v_indate1, "yyyy/MM/dd") %></td>
                                </tr>
							<%}%>
							<%}else{%>
                               <tr>
                                   <td class=notice colspan="13" align=center >
										등록 된 내용이 없습니다.		
									</td>
								</tr>

				<%}%>


                              </table></td>
                          </tr>
                          <tr> 
                            <td height="20" colspan="4">&nbsp;</td>
                          </tr>
                        </table>
					  </td>
                      <td align="center" valign="top">
					  <!-- 새소식 -->
					  <table width="245" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><a href = "javascript:selectSW()"><img src="/images/cp/swdata_tit.gif" border="0" usemap="#Map2"></a></td>
                          </tr>
                          <tr>
                            <td valign="top">
							  <table width="245" border="0" cellpadding="0" cellspacing="0" background="/images/cp/news_bg.gif">
                                <tr>
                                  <td><img src="/images/cp/news_top.gif"></td>
                                </tr>
                                <tr>
                                  <td align="center" valign="top">
								  <table width="230" border="0" cellspacing="0" cellpadding="0">

						  <%if(list1.size() != 0){%>
								<%  for(i = 0; i < list1.size(); i++) {
								   DataBox dbox1 = (DataBox)list1.get(i);  
								   v_dispnum    = dbox1.getInt("d_dispnum");
								   v_userid     = dbox1.getString("d_userid");
								   v_name       = dbox1.getString("d_name");
								   v_indate     = dbox1.getString("d_indate");
								   v_title      = dbox1.getString("d_title");
								   v_seq        = dbox1.getInt("d_seq");

                                    if(v_title.length() > 15)
					                	v_title = v_title.substring(0,15)+"...";

							%>

                                      <tr> 
                                        <td width="19" align="right"><img src="/images/cp/bl.gif"></td>
                                        <td width="140" class="notice"><a href="javascript:selectSW(<%=v_seq%>)" target="_self"><%= v_title %></a></td>
                                        <td width="71" class="notice_day"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                                      </tr>
							<%}%>
							<%}else{%>
                               <tr>
                                   <td class=notice colspan="13" align=center >
										등록 된 내용이 없습니다.		
									</td>
								</tr>

				<%}%>
									
									</table></td>
                                </tr>
                                <tr>
                                  <td><img src="/images/cp/news_bo.gif"></td>
                                </tr>
                              </table></td>
                          </tr>
						  <tr>
                            <td height="20">&nbsp;</td>
                          </tr>
                        </table>
					  </td>
                    </tr>
                  </table></td>
              </tr>
            </table>
			<map name="Map">
			  <area shape="rect" coords="350,3,385,18" href="javascript:selectList()" target="_self" onfocus=this.blur()>
			</map>
		  </td>
        </tr>
      </table></td>
  </tr>
</table>
</form>	
<%@ include file = "/include/cp_footer.jsp"%>


<%@ include file = "/learn/library/getJspName.jsp" %> 

</body>
</html>
