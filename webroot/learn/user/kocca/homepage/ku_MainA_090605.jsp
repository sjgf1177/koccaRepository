<%
//**********************************************************
//  1. 제      목 : 홈페이지 메인
//  2. 프로그램명  : ku_MainA.jsp
//  3. 개      요 : 홈페이지 메인
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 이나연 05.12.15
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%//@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.net.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.net.InetAddress" %>
<%@ page import = "java.net.InetSocketAddress" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.study.KRecommandBean"%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);


    String s_userid  = box.getSession("userid");

    //로그인 체크 변수
    boolean bLoginCheck = false;

    //세션에 사용자 정보가 있는지 검사
    if (session.getAttribute("userid") != null ) {
        bLoginCheck = true;
    }
    box.put("onLoad","onload=\"winonload();\"");

    LoginBean bean = new LoginBean();

    String v_m_gubun        = "";
    String v_m_menuid       = "";
    String v_m_menuname     = "";
    String v_m_menuurl      = "";
    ArrayList mainl_list = (ArrayList)request.getAttribute("mainl_list");
    ArrayList mainr_list = (ArrayList)request.getAttribute("mainr_list");
    ArrayList mainc_list = (ArrayList)request.getAttribute("mainc_list");

    int i = 0;

%>

<%//템플릿 정보%>
<%@ include file="/learn/user/kocca/include/site_info.jsp" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<meta http-equiv="Pragma" content="no-cache" >
<TITLE> :::: 사이버문화콘텐츠아카데미 ::::: </TITLE>
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>

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

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}

//-->
</script>
<!-- 웹폰트 적용시작 -->
<link rel="stylesheet" type="text/css" href="/webfont/webfont_style.css">
<script type="text/javascript" src="/webfont/cookie.js"></script>
<script type="text/javascript" src="/webfont/webfont.js"></script>
<!-- 웹폰트 적용끝-->
<script language="javascript">

function getCookie( name ){
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


function ShowPopup(){

//팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
if ( getCookie( "kocca_open" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
PopCookieWindow = window.open('/learn/user/kocca/pop/pop090604.html','kocca_open','scrollbars=no, left=0, top=0,width=220,height=280');
PopCookieWindow.opener = self;
}

//팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_event" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open//('/learn/user/kocca/pop/pop090604.html','kocca_event','scrollbars=no,// left=0, //top=0,width=220,height=280');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_invitation" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/open_invitation.jsp','kocca_invitation','scrollbars=no, left=0, top=0, //width=400,height=500');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_curri" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/open_curri.jsp','kocca_curri','scrollbars=no, left=845, top=0, width=400,height=500');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_work" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/open_work.jsp','kocca_work','scrollbars=no, left=410, top=0, width=425,height=645');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_special" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/pop20071124.jsp','kocca_special','scrollbars=no, left=410, //top=0, width=400,height=440');
//PopCookieWindow.opener = self;
//}

}
// -->
</SCRIPT>

<!-- 팝업창 띄우는 메소드 //-->
<script language="JavaScript" type="text/JavaScript">
	<!--
		function MM_openBrWindow(theURL,winName,features) { //v2.0
		window.open(theURL,winName,features);
	}
//-->
</script>

<SCRIPT LANGUAGE="JavaScript">
<!--
function menuForward(topmenu, leftmenu, orders){

    document.topdefaultForm.orders.value     = orders;
    document.topdefaultForm.topmenu.value    = topmenu;
    document.topdefaultForm.leftmenu.value   = leftmenu;
    document.topdefaultForm.p_process.value  = "writeLog";
    document.topdefaultForm.action = "/servlet/controller.system.MenuCountServlet";

    document.topdefaultForm.submit();
}
//-->

</SCRIPT>

<form name="topdefaultForm" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'  value=''>
  <input type='hidden' name='topmenu'    value='0'>
  <input type='hidden' name='leftmenu'   value=''>
  <input type='hidden' name='orders'     value=''>
</form>

</HEAD>

<BODY Onload="ShowPopup()" bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" background="/images/user/kocca/homepage/new_img/main_bg.jpg">

<!-- logo 및 메뉴 플래시 start -->
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top"><img src="/images/user/kocca/homepage/new_img/logo.jpg"></td>
		<td valign="top">
			<script language = "javascript" src = "/script/user_patch.js"></script>
			<script language='javascript'>
			setParam1('/images/user/kocca/homepage/type1/navi_final.swf');
			setParam2('736');
			setParam3('80');
			//alert("1111");
			object_patch('leftmenuB');
			setParam1('');
			setParam2('');
			setParam3('');
			</script>
			</td>
	</tr>
</table>
<!-- logo 및 메뉴 플래시 end -->

<!-- 메인 플래시 start -->
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><script language = "javascript" src = "/script/user_patch.js"></script>
			<script language='javascript'>
			setParam1('/images/user/kocca/homepage/type1/mainflash_081216.swf');
			setParam2('916');
			setParam3('220');
			//alert("1111");
			object_patch('leftmenuB');
			setParam1('');
			setParam2('');
			setParam3('');
			</script>
			<!--object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="916" height="222">
			<param name="movie" value="">
			<param name="quality" value="high">
			<embed src="" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="916" height="222"></embed></object-->
		</td>
	</tr>
</table>
<!-- 메인 플래시 end -->

<table border="0" cellspacing="0" cellpadding="0" width="916" background="/images/user/kocca/homepage/new_img/m_bg_080901.jpg" style="background-repeat:no-repeat;">
	<tr>
		<td width="10"><span style="width:10"></span></td>
		<td valign="top" width="190">

			<table width="170" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>

						<!--  로그인 table start -->
						<!-- login  -->

						<form name="frmLogin" method="post" action="">
						<input type="hidden" name="p_process" value="" >

<%
if (session.getAttribute("userid") == null) {
%>

	<SCRIPT LANGUAGE="JavaScript">
<!--

    /* 패스워드 엔터키 체크 */
    function password_enter(e) {
        if (e.keyCode =='13'){  login();  }
    }

    // 로그인
    function login() {

        if (input_chk(frmLogin)){
            frmLogin.p_process.value   = "login";
            frmLogin.action            = "/servlet/controller.homepage.KLoginServlet";
            frmLogin.submit();
        }
    }

    // 패스워드 찾기
    function losspwd() {
        //frmLogin.p_process.value   = "golosspwd";
        frmLogin.action            = "/servlet/controller.homepage.KLoginServlet?p_process=golosspwd";
        frmLogin.submit();
    }

	// 회원가입
    function member() {
        //frmLogin.p_process.value   = "golosspwd";
        frmLogin.action            = "/servlet/controller.homepage.KMemberJoinServlet?p_process=join";
        frmLogin.submit();
    }

    // 입력 체크
    function input_chk(frmLogin)  {
        var chk = ( isNull(frmLogin.elements["p_userid"],"아이디") && isNull(frmLogin.elements["p_pwd"],"비밀번호"));
        return (chk);
    }

/*
    if( getCookie( "userid" ) == "" ) {
      document.frmLogin.p_userid.focus();
    } else {
      document.frmLogin.p_userid.value = getCookie( "userid" );
      document.frmLogin.rememberId.checked = true;
      document.frmLogin.p_pwd.focus();
    }
*/

//-->
</SCRIPT>
						<!-- 로그인 전-->

						<table width="170" border="0" cellspacing="0" cellpadding="0">
							<tr height="120">
								<td background="/images/user/kocca/homepage/new_img/login_bg.jpg" style="background-repeat:no-repeat;padding:37 0 0 9" valign="top">

									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td><input name="p_userid" type="text" class="main_login" style="width:105;height:18" tabindex="1" value="" onkeypress="password_enter(event)"></td>
											<td rowspan="2" style="padding-left:4"><a href="javascript:login()"><img src="/images/user/kocca/homepage/new_img/btn_login.gif"></td>
										</tr>
										<tr>
											<td><input name="p_pwd" type="password" class="main_login" style="width:105;height:18" tabindex="2" value="" onkeypress="password_enter(event)" ></td>
										</tr>
										<tr>
											<td height="10"></td>
										</tr>
										<tr>
											<td colspan="2" align="center">
												<a href="javascript:member()"><img src="/images/user/kocca/homepage/new_img/btn_joinus.gif"></a><a href="javascript:losspwd()"><img src="/images/user/kocca/homepage/new_img/btn_idpw_search.gif"></a>
											</td>
										</tr>
									</table>

								</td>
							</tr>
						</table>
						<!-- 로그인 전-->
<%} else {

	String ss_userid   = (String)box.getSession("userid");
	String ss_username = (String)box.getSession("name");
	String ss_gadmin   = (String)box.getSession("gadmin");

	int v_totalmil = 0;
	v_totalmil = HomePageMileageBean.TotalMileage(box);
%>
<SCRIPT LANGUAGE="JavaScript">

	function gologout(){
		//frmLogin.p_process.value = "loginpage";
		frmLogin.action			 = "/servlet/controller.homepage.KLoginServlet?p_process=gologout";
		frmLogin.submit();
	}

<!--

    function adminOpenPage() {
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinAdmin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1024, height = 768, top=0, left=0");
        document.frmLogin.target = "openWinAdmin"
        document.frmLogin.action = "/learn/admin/system/mScreenFset.jsp";
        document.frmLogin.submit();

        farwindow.window.focus();
        document.frmLogin.target = window.self.name;
    }

    function authChange() {
        frmLogin.p_process.value   = "authChange";
        frmLogin.action            = "/servlet/controller.homepage.MainServlet";
        frmLogin.submit();
    }

	// 정보변경
	function mem_modify(){
		var url = "/servlet/controller.homepage.KMemberInfoServlet?p_process=memberUpdatePage";
		frmLogin.action            = url;
        frmLogin.submit();
	}

//-->
</SCRIPT>
						<!-- //로그인 후-->

						<table width="170" border="0" cellspacing="0" cellpadding="0">
							<tr height="150">
								<td background="/images/user/kocca/homepage/new_img/logout_bg.gif" style="background-repeat:no-repeat;padding:35 0 0 9" valign="top">

									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="105"><%=box.getSession("name")%> 님의<br>방문을 환영합니다.
									 		</td>
											<td style="padding-left:4"><a href="javascript:gologout()"><img src="/images/user/kocca/homepage/new_img/btn_logout.gif"></a></td>
										</tr>
										<tr>
											<td height="10"></td>
										</tr>
										<tr>
											<td height="10" colspan="2">

												<%= LoginBean.getAuthSelect(ss_userid, "p_auth", ss_gadmin, "onChange = 'javascript:authChange()'")%>
												<% if (!(ss_gadmin.equals("ZZ") || ss_gadmin.equals(""))) {                     %>
												 <a href='#' onClick='adminOpenPage()'><img src="/images/user/kocca/homepage/type1/btn_go.gif"  align="absmiddle"></a>
												 <% } %>

											</td>
										</tr>
										<tr>
											<td height="10"></td>
										</tr>
										<tr>
											<td colspan="2" align="center">
												<a href="/servlet/controller.homepage.KMemberInfoServlet?p_process=memberUpdatePage"><img src="/images/user/kocca/homepage/new_img/btn_change.gif"></a>
											</td>
										</tr>
									</table>

								</td>
							</tr>
						</table>
<% } %>
						</form>

					</td>
				</tr>
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>

						<!--  강좌검색 table start -->

						<SCRIPT LANGUAGE="JavaScript">
						<!--

						function search_enter(e)  {
						if (e.keyCode =='13'){  subjSearch();}
						}


						function subjSearch() {
						if (document.subjSearch.p_lsearchtext.value=="") {
						alert("검색어를 입력하세요");
						return;
						}

						document.subjSearch.action = "/servlet/controller.propose.KMainSubjSearchServlet";
						document.subjSearch.p_process.value = "SubjSearch";
						document.subjSearch.submit();
						}
						//-->
						</SCRIPT>

						<form name="subjSearch" method="post" action="javascript:return;">
						<input type='hidden' name='p_process'>
						<input type='hidden' name='p_lsearch' value="">

						<table width="170" border="0" cellspacing="0" cellpadding="0">
							<tr height="58">
								<td background="/images/user/kocca/homepage/new_img/search_bg.jpg" style="background-repeat:no-repeat;padding:8 0 0 32" valign="top">

									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td><input type="text" name="p_lsearchtext" class="search_input" value="" style="width:85;height:19" onkeypress="search_enter(event)"></td>
											<td style="padding-left:5"><a href="javascript:subjSearch()"><img src="/images/user/kocca/homepage/new_img/i_go.gif"></a></td>
										</tr>
									</table>

								</td>
							</tr>
						</table>

						</form>
						<!--  강좌검색 table end -->

					</td>
				</tr>
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>

						<!--  수강신청 table start -->
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><a href="/servlet/controller.propose.KProposeCourseServlet?p_process=SubjectList"><img src="/images/user/kocca/homepage/new_img/st_aplly_b.gif"></a></td>
							</tr>
							<tr>
								<td><a href="/servlet/controller.study.KMyClassServlet?p_process=ProposeCancelPage"><img src="/images/user/kocca/homepage/new_img/st_aplly_01.gif"></a></td>
							</tr>
							<tr>
								<td><a href="/servlet/controller.propose.KProposeCourseServlet?p_process=SubjectIntro"><img src="/images/user/kocca/homepage/new_img/st_aplly_02.gif"></a></td>
							</tr>
							<tr>
								<td><a href="/servlet/controller.propose.KProposeCourseServlet?p_process=OffLineSubjPage"><img src="/images/user/kocca/homepage/new_img/st_aplly_03.gif"></a></td>
							</tr>
						</table>
						<!--  수강신청 table end -->

					</td>
				</tr>
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>

						<!--  고객센터 table start -->
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><a href="/servlet/controller.homepage.KHome1vs1Servlet?p_process=movePage"><img src="/images/user/kocca/homepage/new_img/quick_menu_03.gif"></a><br></td>
							</tr>
						</table>
						<!--  고객센터 table end -->

					</td>
				</tr>
				<tr>
					<td height="5"></td>
				</tr>
				<tr>
					<td>

						<SCRIPT LANGUAGE="JavaScript">
						<!--
							// 커뮤니티 새창
							function CommunityOpenPage() {
								window.self.name = "winSelectViewCommunity";
								farwindow = window.open("", "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
								document.topdefaultForm.target = "openWinCommunity"
								document.topdefaultForm.action = "/servlet/controller.community.CommunityIndexServlet";
								document.topdefaultForm.submit();

								farwindow.window.focus();
								document.topdefaultForm.target = window.self.name;
							}
						//-->
						</SCRIPT>

						<!--  커뮤니티 table start -->
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><a href="#"  onClick='CommunityOpenPage()'><img src="/images/user/kocca/homepage/new_img/quick_menu_04.gif"></a><br></td>
							</tr>
						</table>
						<!--  커뮤니티 table end -->

					</td>
				</tr>
			</table>
		</td>

		<td width="490" valign="top">

			<!--  공지사항 table start -->

			<SCRIPT LANGUAGE="JavaScript">
			<!--

			function selectView(seq){
			var url = "/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq="+seq;
			document.form1.action = url;
			document.form1.submit();
			}

			//-->
			</SCRIPT>

			<script language="Javascript">

				function hide() {
					document.all.div1.style.display = 'none';
					document.all.div2.style.display = 'none';
				}

				function show(i) {
					switch (i) {
						case (1) : {
							document.all.div1.style.display = 'block';
						} break;
						case (2) : {
							document.all.div2.style.display = 'block';
						} break;
					}
				}

			</script>

			<!-- 공지사항 -->

			<form name="form1" method="post">
			<table border="0" cellpadding="0" cellspacing="0" id="div1" style="display:block">
				<tr>
					<td><img src="/images/user/kocca/homepage/new_img/st_notice.gif" border="0" usemap="#st_notice"></td>
				</tr>
				<tr>
					<td height="10"></td>
				</tr>
				<tr height="108">
					<td valign="top" style="padding-left:4">
						<%
							HomeNoticeBean beanNotice= new HomeNoticeBean();
							ArrayList noticeList = beanNotice.selectDirectList(box);
						%>
						<table width="466" border="0" cellspacing="0" cellpadding="0">
	<%
		int nlsize = (noticeList.size());
			for(i = 0; i < nlsize; i++) {
				DataBox dbox = (DataBox)noticeList.get(i);

	%>
							<tr height="19">
								<td width="80%"><img src="/images/user/kocca/homepage/new_img/icon.gif" align="absmiddle">&nbsp;<a href="javascript:selectView(<%=dbox.getInt("d_seq")%>)"><%=dbox.getString("d_adtitle")%></a></td>
								<td class="notice_day" align="left">[<%=FormatDate.getFormatDate(dbox.getString("d_addate"),"yyyy-MM-dd")%>]</td>
							</tr>
<%}		for(int j = 0; j < 4 - nlsize; j++){%>
							<tr height="19">
								<td colspan="2"></td>
							</tr>
<%		}%>
						</table>

					</td>
				</tr>
			</table>

			<map name="st_notice">
				<area shape="rect" coords="0,0,76,22" href="#" onclick='javascript:hide();show(1);' onMouseOver="MM_swapImage('tab01','','/images/user/kocca/homepage/new_img/st_notice.gif',1);javascript:hide();show(1);" onMouseOut="MM_swapImgRestore();" onFocus="this.blur()">
				<area shape="rect" coords="76,0,184,22" href="#" onclick='javascript:hide();show(2);' onMouseOver="MM_swapImage('tab02','','/images/user/kocca/homepage/new_img/st_offline_info.gif',1);javascript:hide();show(2);" onMouseOut="MM_swapImgRestore();" onFocus="this.blur()">
				<area shape="rect" coords="436,0,470,24" href="/servlet/controller.homepage.KHomeNoticeServlet?p_process=moreNotice" onFocus="this.blur()">
			</map>
			</form>

			<script language="javascript">
			//function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
			var  width  = 0;
			var  height = 0;
			var  left   = 0;
			var  top    = 0;



			function notice_getCookie( name ){
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

			</script>

			<!-- 공지사항 -->
			<!--  공지사항 table end -->

			<!--  오프라인강좌소식통 table start -->
			<%
				String v_dday	= "";	// 강의일
				String v_subjnm	= "";	// 강의명
				int v_seq		= 0;

				ArrayList list      = null;

				list = (ArrayList)KProposeCourseBean.OffLineSelectSubjectTopList(box);	// 최근 과목
			%>

<script>
	// 상세보기
	function applyOffLineView(seq)
	{
		var frm = document.frmOffLine;

		frm.p_seq.value = seq;
		frm.action = "/servlet/controller.propose.KProposeCourseServlet";
		frm.p_process.value = "OffLineView";
		frm.target = "_self";
		frm.submit();
	}
</script>

<form name="frmOffLine">
	<input type="hidden" name="p_seq">
	<input type="hidden" name="p_process">
</form>
			<table border="0" cellpadding="0" cellspacing="0" id="div2" style="display:none">
				<tr>
					<td><img src="/images/user/kocca/homepage/new_img/st_offline_info.gif" border="0" usemap="#st_offline_info"></td>
				</tr>
				<tr>
					<td height="10"></td>
				</tr>
				<tr height="108">
					<td valign="top" style="padding-left:4">

						<table width="466" border="0" cellspacing="0" cellpadding="0">
<%
	for(i = 0; i < list.size(); i++)
	{
		DataBox dbox = (DataBox)list.get(i);

		v_seq		= dbox.getInt("d_seq");
		v_dday		= dbox.getString("d_dday");
		v_subjnm	= dbox.getString("d_subjnm");

		/*
		if(v_subjnm.length() > 10)
		{
			v_subjnm = v_subjnm.substring(0,10) + "...";
		}
	*/

		//날짜 처리
		if(v_dday.equals("")){v_dday = "미정";}
		else{v_dday     = FormatDate.getFormatDate(v_dday,"yyyy-MM-dd");}

%>

							<tr height="19">
								<td width="80%"><img src="/images/user/kocca/homepage/new_img/icon.gif" align="absmiddle">&nbsp;<a href="javascript:applyOffLineView('<%=v_seq%>')"><%= v_subjnm %></a></td>
								<td class="notice_day" align="left">[<%=v_dday%>]</td>
							</tr>
<%		}

	 if (i == 0) { %>
			<tr>
			  <td height="19" colspan="2">이 달의 오프라인 강좌가 없습니다.</td>
			</tr>
<%	}	%>
						</table>

					</td>
				</tr>
			</table>

			<map name="st_offline_info">
				<area shape="rect" coords="0,0,76,22" href="#" onclick='javascript:hide();show(1);' onMouseOver="MM_swapImage('tab01','','/images/user/kocca/homepage/new_img/st_notice.gif',1);javascript:hide();show(1);" onMouseOut="MM_swapImgRestore();" onFocus="this.blur()">
				<area shape="rect" coords="76,0,184,22" href="#" onclick='javascript:hide();show(2);' onMouseOver="MM_swapImage('tab02','','/images/user/kocca/homepage/new_img/st_offline_info.gif',1);javascript:hide();show(2);" onMouseOut="MM_swapImgRestore();" onFocus="this.blur()">
				<area shape="rect" coords="436,0,470,24" href="/servlet/controller.propose.KProposeCourseServlet?p_process=OffLineSubjPage" onFocus="this.blur()">
			</map>
			<!--  오프라인강좌소식통 table end -->

			<!--  바로가기 LINK table start -->
			<table width="470" border="0" cellspacing="0" cellpadding="0">
				<tr height="63">
					<td background="/images/user/kocca/homepage/new_img/curri_bg.gif" style="background-repeat:no-repeat;padding:26 0 0 60" valign="top">

						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="74"class="view_sub_t" id="font_subtitle"><img src="/images/user/kocca/homepage/new_img/icon02.gif" align="absmiddle"> <a href="javascript:menuForward('1', '07', '3')" class="curri">입문과정</a></td>
								<td width="74"><img src="/images/user/kocca/homepage/new_img/icon02.gif" align="absmiddle"> <a href="javascript:menuForward('1', '02', '4')" class="curri">개별과정</a></td>
								<td width="80"><img src="/images/user/kocca/homepage/new_img/icon02.gif" align="absmiddle"> <a href="javascript:menuForward('1', '04', '5')" class="curri">전문가과정</a></td>
								<td width="80"><img src="/images/user/kocca/homepage/new_img/icon02.gif" align="absmiddle"> <a href="javascript:menuForward('1', '05', '6')" class="curri">워크숍과정</a></td>
								<td width="80"><img src="/images/user/kocca/homepage/new_img/icon02.gif" align="absmiddle"> <a href="javascript:menuForward('1', '06', '7')" class="curri">학점인증과정</a></td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
			</table>
			<!--  바로가기 LINK table end -->

			<!-- 추천강좌 start -->
			<%
				box.put("p_process","main");

				MainSubjSearchBean subjbean = new MainSubjSearchBean();
				ArrayList recolist = subjbean.selectSubjRecomList(box);

				//String v_subjnm		= "";
				String v_subj		= "";
				String v_intro		= "";
				String v_sphere		= "";
				String v_savefile   = "";
				String v_realfile   = "";
			%>

			<SCRIPT LANGUAGE="JavaScript">
			<!--

			// 과정 내용보기
			function whenSubjInfo(subj,sphere){
			document.subjform.p_subj.value     = subj;
			document.subjform.p_sphere.value   = sphere;
			document.subjform.p_process.value  = 'SubjectPreviewPage';
			document.subjform.p_rprocess.value = 'SubjectListAlway';
			document.subjform.action='/servlet/controller.course.KCourseIntroServlet';
			document.subjform.target = "_self";
			document.subjform.submit();
			}
			//-->
			</SCRIPT>


			<form name="subjform" method="post" >
			<input type="hidden" name="p_process">
			<input type="hidden" name="p_subj">
			<input type='hidden' name='p_sphere' >
			<input type='hidden' name='p_rprocess' value="">

			<table width="470" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top">

						<!-- 이달의 추천강좌 -->
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="/images/user/kocca/homepage/new_img/st_monthly_class.gif"></td>
							</tr>
							<tr>
								<td background="/images/user/kocca/homepage/new_img/st_monthly_bg.gif" align="center">

									<table width="213" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td><img src="/images/user/kocca/homepage/new_img/st_monthly_top.gif"></td>
										</tr>
										<tr bgcolor="#F1FBEB">
											<td align="center">

												<table width="199" border="0" cellpadding="0">
													<!-- SET START -->
													<tr>
														<td width="50">
														<img src="/upload/bulletin/Subject_introducefile_200903161613341_lee1.jpg" width="44" height="44">

														</td>
														<td width="149">


															<table border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td><strong><a href="javascript:whenSubjInfo('ash008','1')"><span class="new_main_contents_title_text">

																	  <font color="#41970C">문화특강 - 조선희 사진작가</font>
																	  </span></a></strong></td>
																</tr>
																<tr>
																	<td height="2"></td>
																</tr>
																<tr>
																	<td class="main_contents02" style="letter-spacing:-1px">

																	  우리시대 가장 주목받는 예술가와의 만남 ‘아트샤워 시즌2 ...</td>
																</tr>
															</table>

														</td>
													</tr>
													<tr>
														<td height="3"></td>
													</tr>
													<!-- SET END -->

													<!-- SET START -->
													<tr>
														<td width="50">
														<img src="/upload/bulletin/Subject_introducefile_200812231454381_lee1.jpg" width="44" height="44">

														</td>
														<td width="149">


															<table border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td><strong><a href="javascript:whenSubjInfo('001173','2')"><span class="new_main_contents_title_text">

																	  <font color="#41970C">new 기술로 본 모바일콘텐츠</font>
																	  </span></a></strong></td>
																</tr>
																<tr>
																	<td height="2"></td>
																</tr>
																<tr>
																	<td class="main_contents02" style="letter-spacing:-1px">

																	  모바일 콘텐츠를 생각한다면 알아야 할 기술 이야기</td>
																</tr>
															</table>

														</td>
													</tr>
													<tr>
														<td height="3"></td>
													</tr>
													<!-- SET END -->

												</table>

											</td>
										</tr>
										<tr>
											<td><img src="/images/user/kocca/homepage/new_img/st_monthly_bottom.gif"></td>
										</tr>
									</table>

								</td>
							</tr>
							<tr>
								<td><img src="/images/user/kocca/homepage/new_img/st_monthly_bottom02.gif"></td>
							</tr>
						</table>
						</form>
						<!-- 이달의 추천강좌 -->

					</td>
					<td valign="top" align="right">


						<!--  인기강좌 table start -->

						<SCRIPT LANGUAGE="JavaScript">
						<!--
						// 과정 내용보기
						function whenSubjInfo(subj,sphere){
						document.frm3.p_subj.value       = subj;
						document.frm3.p_sphere.value = sphere;
						document.frm3.p_process.value  = 'SubjectPreviewPage';
						document.frm3.p_rprocess.value = 'SubjectListAlway';
						document.frm3.action='/servlet/controller.course.KCourseIntroServlet';
						document.frm3.target = "_self";
						document.frm3.submit();
						}


						//-->
						</SCRIPT>

						<FORM  name="frm3" METHOD=POST ACTION="" >
						<input type="hidden" name="p_searchYn">
						<input type='hidden' name='p_sphere' >
						<input type='hidden' name='p_subj'    value="">
						<input type='hidden' name='p_rprocess' value="">
						<input type='hidden' name='p_process' value="">
						</FORM>

						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="/images/user/kocca/homepage/new_img/st_good_course.gif"></td>
							</tr>
							<tr>
								<td background="/images/user/kocca/homepage/new_img/st_monthly_bg.gif" align="center">

									<table width="213" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td><img src="/images/user/kocca/homepage/new_img/st_monthly_top002.gif"></td>
										</tr>
										<tr bgcolor="#FBF7ED">
											<td align="center">
												<%
												KRecommandBean krecommandBean = new KRecommandBean();
												ArrayList rclist = krecommandBean.selectMainRecommandSubjList(box);

												String v_newphoto = "";


											for(i = 0; i < rclist.size() && i < 2; i++) {
												DataBox dbox = (DataBox)rclist.get(i);

												if(!dbox.getString("d_newphoto").equals("")){
													v_newphoto = conf.getProperty("url.upload")+"bulletin/" + dbox.getString("d_newphoto");
												}else{
													v_newphoto = "/images/user/kocca/homepage/type1/blank_photo1.gif";
												}
												%>

												<table width="199" border="0" cellpadding="0">
												    <!-- SET START -->
													<tr>
														<td width="50"><img src="<%=v_newphoto%>" width="44" height="44"></td>
														<td width="149">


															<table border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td height=10><strong><a href="javascript:whenSubjInfo('<%=dbox.getString("d_subj")%>','<%=dbox.getString("d_sphere")%>')"><span class="new_main_contents_title_text02">
																	 <%v_subjnm = dbox.getString("d_subjnm");
																	 if(v_subjnm.length() > 10){
																		  	v_subjnm = v_subjnm.substring(0,10)+"...";
																		  }%>
																	  <font color="#B48616"><%=v_subjnm%></font>
																	</span></a></strong></td>
																</tr>
																<tr>
																	<td height="2"></td>
																</tr>
																<tr>
																	<td class="main_contents02" style="letter-spacing:-1px"><a href="javascript:whenSubjInfo('<%=dbox.getString("d_subj")%>','<%=dbox.getString("d_sphere")%>')"><font class="main_contents02"><%=dbox.getString("d_intro")%></a><font style="font-size:5pt">...</font></td>
																</tr>
															</table>

														</td>
													</tr>
													<tr>
														<td height="3"></td>
													</tr>
													<!-- SET END -->
												<%
													}
												%>

												<% if (rclist.size()==0) { %>
													<tr>
														<td height="10" colspan=2>검색된 과정이 없습니다.</td>
													</tr>
												<% } %>
												</table>

											</td>
										</tr>
										<tr>
											<td><img src="/images/user/kocca/homepage/new_img/st_monthly_bottom002.gif"></td>
										</tr>
									</table>

								</td>
							</tr>
							<tr>
								<td><img src="/images/user/kocca/homepage/new_img/st_monthly_bottom02.gif"></td>
							</tr>
						</table>

						<!-- 인기강좌 table start -->

					</td>
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
			</table>
			<!-- 추천강좌 end -->

			<!-- 파워인터뷰 table start -->

			<SCRIPT LANGUAGE="JavaScript">

			function view(seq) {
			pform1.action = "/servlet/controller.homepage.KOpenPowerServlet";
			pform1.p_seq.value = seq;
			pform1.p_process.value = "OpenPowerDetail";
			pform1.submit();
			}

			</SCRIPT>


			<form name="pform1" method="post">
			<input type="hidden" name="p_seq">
			<input type="hidden" name="p_process">

			<table width="223" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><a href="/servlet/controller.homepage.KOpenPowerServlet"><img src="/images/user/kocca/homepage/new_img/st_power_interview.gif"></a></td>
				</tr>
				<tr>
					<td background="/images/user/kocca/homepage/new_img/st_power_interview_bg.gif" align="center">

						<table width="450" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><a href="javascript:view(23)" width="95"><img src="http://contents.connect.or.kr/upload/bulletin/KOpenPowerAdmin_file1_200812121643571_lee1.jpg"></td>
								<td class="tbl_gleft_small" width="355" valign=top>

									<table border="0" cellpadding="0" cellspacing="0">
										<%	KOpenPowerBean kbean = new KOpenPowerBean();
											ArrayList pilist =  kbean.selectBoardList(box);

											for(i = 0; i < pilist.size() && i < 3; i++) {
												DataBox dbox	= (DataBox)pilist.get(i);
												int v_pseq   = dbox.getInt("d_seq");
												int v_pcnt			= dbox.getInt("d_cnt");
										        String v_psingleword	= dbox.getString("d_singleword");		// 제목
										        String v_ptitle			= dbox.getString("d_title");			// 튜터명
										%>
										<tr height="20">
											<td><img src="/images/user/kocca/homepage/new_img/icon03.gif" align="absmiddle">
											<%if(v_psingleword.length() < 30){
						  v_psingleword = v_psingleword;
				  }else{  v_psingleword = (v_psingleword).substring(0,30)+"...";
				  }%>
				<a href="javascript:view(<%=v_pseq%>)"><%=v_psingleword%></a>
				</td>
										</tr>
										<% } %>

										<% if (pilist.size() == 0 ) { %>
												<tr>
													<td>인터뷰 내용이 없습니다.</td>
												</tr>
										<% } %>

									</table>

								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td><img src="/images/user/kocca/homepage/new_img/st_power_interview_bottom.gif"></td>
				</tr>
			</table>
			<!-- 파워인터뷰 table end -->

		</td>
		<td width="226" valign="top" style="padding-top:19">

			<!-- 강좌 맛보기 -->
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="/servlet/controller.propose.KMainSubjSearchServlet?p_process=SubjSearch"><img src="/images/user/kocca/homepage/new_img/sample.gif" align="absmiddle" border=0></a></td>
				</tr>
				<tr>
					<td height="5"></td>
				</tr>
			</table>
<table border="0" cellpadding="0" cellspacing="0">			<!-- 강좌 맛보기 -->
<tr>
					<td><a href="/servlet/controller.homepage.KHome1vs1Servlet?p_process=movePage"><img src="/images/user/kocca/homepage/new_img/becc_bn227.gif"></a></td>
				</tr></table>
			<!-- 과정 로드맵 table start -->
			<SCRIPT LANGUAGE="JavaScript">
			<!--

				 function viewLoad() {

					window.self.name = "winSelectView";
					farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 762, height = 750, top=0, left=0");
					document.pform1.target = "openWin"

					document.pform1.p_process.value = "select";
					document.pform1.action = "/servlet/controller.course.SubjLoadMapServlet";
					document.pform1.submit();

					farwindow.window.focus();
					document.pform1.target = window.self.name;
				 }

			//-->
			</SCRIPT>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><img src="/images/user/kocca/homepage/new_img/quick_menu_01.gif" align="absmiddle"></td>
				</tr>
				<tr>
					<td height="20" bgcolor="#F6FBFB" style="padding-left:11"><img src="/images/user/kocca/homepage/new_img/icon04.gif" align="absmiddle"> <a href="#" onClick="MM_openBrWindow('/learn/user/kocca/homepage/Loadmap01.jsp','','scrollbars=no,width=900,height=700,left=0,top=0');return false" onFocus="this.blur();" class="curri" >일반인대상</td>
				</tr>
				<tr>
					<td height="1" background="/images/user/kocca/homepage/new_img/dot.gif"></td>
				</tr>
				<tr>
					<td height="20" bgcolor="#F6FBFB" style="padding-left:11"><img src="/images/user/kocca/homepage/new_img/icon04.gif" align="absmiddle"> <a href="#" onClick="MM_openBrWindow('/learn/user/kocca/homepage/Loadmap02.jsp','','scrollbars=no,width=900,height=700,left=0,top=0');return false" onFocus="this.blur();" class="curri" >학생 및 취업대상자</td>
				</tr>
				<tr>
					<td height="1" background="/images/user/kocca/homepage/new_img/dot.gif"></td>
				</tr>
				<tr>
					<td height="20" bgcolor="#F6FBFB" style="padding-left:11"><img src="/images/user/kocca/homepage/new_img/icon04.gif" align="absmiddle"> <a href="#" onClick="MM_openBrWindow('/learn/user/kocca/homepage/Loadmap03.jsp','','scrollbars=no,width=900,height=700,left=0,top=0');return false" onFocus="this.blur();" class="curri" >문화콘텐츠 산업 재직자</td>
				</tr>
				<tr>
					<td height="1" bgcolor="#BADBD3"></td>
				</tr>
				<tr>
					<td height="7"></td>
				</tr>
			</table>
			<!-- 과정 로드맵 table end -->

			<table border="0" cellpadding="0" cellspacing="0">
				

				 <tr>
					<td height="6"></td>
				</tr>
<tr>
					<td><a href="#" onClick="MM_openBrWindow('/learn/user/kocca/homepage/pop_curri.jsp','','scrollbars=yes,width=816,height=600');"><img src="/images/user/kocca/homepage/new_img/r_bn02.gif" align="absmiddle"></a></td>
				</tr>
				<tr>
					<td height="6"></td>
				</tr>
				
			    <tr>
					<td height="6"></td>
				</tr>

				<tr>
					<td><a href="/servlet/controller.homepage.KHome1vs1Servlet?p_process=movePage"><img src="/images/user/kocca/homepage/new_img/r_bn022.gif"></a></td>
				</tr>
				 <tr>
					<td height="6"></td>
				</tr>
				<tr>
					<td><a href="/servlet/controller.homepage.KOpenOfflineServlet?p_process=OpenOfflineList"><img src="/images/user/kocca/homepage/new_img/kocca_guide.gif"></a></td>
				</tr>
				<tr>
					<td height="6"></td>
				</tr>
				<tr>
					<td><a href="#" onClick="MM_openBrWindow('/learn/user/kocca/homepage/pop_map.jsp','','scrollbars=no,width=676,height=629');"><img src="/images/user/kocca/homepage/new_img/r_bn03.gif" align="absmiddle"></a></td>
				</tr>
			</table>

			<SCRIPT LANGUAGE="JavaScript">
			<!--

				 function viewLoad2(gubun) {

					if(gubun == "road1" ){
						document.pform1.p_process.value = "select";
				 }else{
						document.pform1.p_process.value = "select2";
				 }
					window.self.name = "winSelectView";
					farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 762, height = 750, top=0, left=0");
					document.pform1.target = "openWin"
					document.pform1.action = "/servlet/controller.course.SubjLoadMapServlet";
					document.pform1.submit();

					farwindow.window.focus();
					document.pform1.target = window.self.name;
				 }

			//-->
			</SCRIPT>

		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="20"></td>
	</tr>
	<tr>
		<td background="/images/user/kocca/homepage/new_img/copy_bg.gif">

			<table width="916" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><img src="/images/user/kocca/homepage/new_img/bottom_logo.gif"></td>
					<td><a href="http://www.mct.go.kr/" target="_blank"><img src="/images/user/kocca/homepage/new_img/bottom01.gif"></a></td>
					<td><a href="http://www.kocca.or.kr/index.jsp" target="_blank"><img src="/images/user/kocca/homepage/new_img/bottom02.gif"></a></td>
					<td><a href="http://www.gitiss.org" target="_blank"><img src="/images/user/kocca/homepage/new_img/bottom04.gif"></a></td>
					<td><a href="http://academy.kbi.re.kr/" target="_blank"><img src="/images/user/kocca/homepage/new_img/bottom05.gif"></a></td>
					<td><a href="http://www.gameacademy.or.kr/" target="_blank"><img src="/images/user/kocca/homepage/new_img/bottom06.gif"></a></td>
				</tr>
			</table>

		</td>
	</tr>
	<tr>
		<td bgcolor="#F2F2F2"><img src="/images/user/kocca/homepage/new_img/copy02.gif" align="absmiddle" usemap="#copy02"></td>
	</tr>
</table>

<map name="copy02">
	<area shape="rect" coords="18,0,55,28" href="/" onFocus="this.blur()">
	<area shape="rect" coords="55,0,121,28" href="mailto:cyber02@kocca.or.kr" onFocus="this.blur()">
	<area shape="rect" coords="121,0,170,28" href="/servlet/controller.homepage.KHomeSiteMapServlet" onFocus="this.blur()">
	<area shape="rect" coords="833,6,853,24" href="mailto:cyber02@kocca.or.kr" onFocus="this.blur()">
</map>
</BODY>
<HTML>
