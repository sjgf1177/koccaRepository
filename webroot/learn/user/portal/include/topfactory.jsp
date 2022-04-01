<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import = "com.credu.library.RequestBox" %>
<title>KOCCA 한국콘텐츠아카데미 - 지식팩토리</title>
<style type="text/css" media="screen">@import "/css/common.css";</style>
<style type="text/css" media="screen">@import "/css/factory.css";</style>
 
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script language="javascript" src="/script/mainscript.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

 
<script language="javascript"  src="/js/lib.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="Content-Style-Type" content="text/css">

<script type="text/javascript">

  var _gaq = _gaq || [];  
  _gaq.push(['_setAccount', 'UA-33104445-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body>

<%
	String topWarp_userid = box.getSession("userid");
	String topWarp_usernm = box.getSession("name");
	String topWarp_gadmin = box.getSession("gadmin");
%>
<%
   
    String gubun     = box.getString("gubun");  
	String menuid    = box.getString("menuid"); 
	String orders    = box.getString("orders"); 

	
	if(!gubun.equals("") && gubun != null)  box.setSession("s_gubun", gubun);
	if(!menuid.equals("") && menuid != null) box.setSession("s_menuid", menuid);

	String v_return_url = request.getRequestURI();
	String v_return_process = box.getString("p_process");

%>


<script language="JavaScript">
<!--//
	// 로그인
    function login() {
        	document.topForm1.p_process.value   = "login";
        	document.topForm1.p_topuserid.value = document.getElementById('p_userid').value;
        	document.topForm1.p_toppwd.value =  document.getElementById('p_pwd').value;

        	document.topForm1.action            = "/servlet/controller.homepage.LoginServlet";
        	document.topForm1.submit();        
    }   
	/* 패스워드 엔터키 체크 */
	function password_enter(e) {
	    if (e.keyCode =='13'){  login();  }
	}

	/* 검색 엔터키 체크 */
	function search_enter(e) {
	    if (e.keyCode =='13'){  totalSubjSearch();  }
	}
	 //전체 과정검색
    function totalSubjSearch() {
        
        var searchText = $("#topWarp_searchText").val();
        var searchClass = $("#mainSearch").attr("src");        
      
         if(searchClass.indexOf("online")>=0) {
             var gubun   = "1";
             var url     = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&s_subjnm="+searchText;
         } else {
             var gubun   = "2";
             var url     = "/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&s_subjnm="+searchText;
         }
         menuMainForward(gubun, url);
       

    }
    function onoffselect()
    {
        var imgFilename=$("#mainSearch").attr("src");
        var filename="/images/newmain_20100625/search_";
        var fileClick="/images/newmain_20100625/search_";
        var tmp="";

        if(imgFilename.indexOf("search_online.gif")>0)
            tmp="offline";
        else
            tmp="online";
        
        $("#mainSearch").attr("src",filename+tmp+".gif");
        $("#mainSearchClick").attr("src",fileClick+tmp+".gif");
        $("#topWarp_searchClass").val(tmp+"line");
    }
    function goSiteSelect() {
    	var link1 = document.getElementById("select2").value;
    	var arr = link1.split(':');

    	if(arr[2] == "Y"){
    	
    		alert("로그인 후 이용해주세요");
    		return;
    	
    	}
    	window.open("/learn/user/homepage/zu_LinkSite_Action.jsp?p_grcode="+arr[0]+"&p_seq="+arr[1], "SiteLink", "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=700,height=480,left=80,top=100");
    }
    
//-->
</script>
<form name="topForm1">
    <input type="hidden" name="p_topuserid"	value=""/>
    <input type="hidden" name="p_toppwd"	value=""/>
    <input type = "hidden" name = "p_process" value=""/>
</form>


<form name="topdefaultForm" method="post" action="javascript:return;" id="topdefaultForm">
  <input type='hidden' name='p_process'   value=''>
  <input type='hidden' name='gubun'       value='0'>
  <input type='hidden' name='menuid'      value=''>
  <input type='hidden' name='url'         value=''>
  <input type='hidden' name='p_cmuno'     value=''>
  <input type='hidden' name='searchClass' value=''>
  <input type='hidden' name='p_auth'      value=''>
  <input type='hidden' name='p_topuserid' value=''>
  <input type='hidden' name='p_toppwd'    value=''>
  <input type='hidden' name='p_area'    value=''>
</form>
<div id="warp">
	<!-- 상단 -->
	<div id="header">
		<table class="logo" cellpadding="0" cellspacing="0">
			<colgroup><col width="200"><col width="*"><col width="200"></colgroup>
			<tr>
				<td class="alignL">&nbsp;</td>
				<td class="alignC"><a href="/"><img src="/images/common/logo.gif" alt="로고"></a></td>
				<td class="alignR v-aB"><a href="http://twitter.com/edukocca" target="_blank"><img src="/images/common/icon_twitter.gif" class="twitter" alt="트위터"></td>
			</tr>
		</table>
		<div class="navi"><script>fl_view('/flash/topnavi.swf',995,41);</script></div>
		<div class="memoption">
			<table class="inoption" cellpadding="0" cellspacing="0">
				<colgroup><col width="30"><col width="126"><col width="126"><col width="62"><col width="79"><col width="*"><col width="68"><col width="80"><col width="126"><col width="54"></colgroup>
				<tr>
				 	<td width="30" height="35"><div align="right"><img src="/images/portal/homepage_renewal/common/icon_login.jpg" /></div></td>
			        <% if(topWarp_userid.equals("")) {%>     <%--로그인이 안되었는 경우--%>
			        <td width="99"><input name="p_userid" type="text" id="p_userid" width="91px" value="아이디" onfocus="javascript:this.value='';" /></td>
			        <td width="95"><input name="p_pwd" type="password" id="p_pwd" width="87px" value="비밀번호" onfocus="javascript:this.value='';" onkeypress="password_enter(event)"/></td>
			        <td width="54"><a href="javascript:login();"><img src="/images/portal/homepage_renewal/common/btn_login.jpg" alt="로그인" /></a></td>
			        <td width="71"><a href="javascript:menuForward('90', '02');"><img src="/images/portal/homepage_renewal/common/btn_find.jpg" alt="ID/PW찾기"  /></a></td>
			        <td width="254"><a href="javascript:menuForward('90', '01');"><img src="/images/portal/homepage_renewal/common/btn_join.jpg" alt="회원가입" /></a></td>
			        <td width="64"><img src="/images/portal/homepage_renewal/common/txt_search.jpg" alt="과정검색" /></td>
			        <td width="66"><img src="/images/newmain_20100625/search_online.gif" width="72" height="20" onclick='onoffselect()' style='CURSOR:POINTER;' id="mainSearch"/></td>
			        <td width="152"><input name="topWarp_searchText" type="text" id="topWarp_searchText" size="30" onkeypress="search_enter(event)"/></td>
			        <td width="49"><a href="javascript:totalSubjSearch();"><img src="/images/portal/homepage_renewal/common/btn_search.jpg" alt="검색" /></a></td>
			        <%}else{ %>
			        <td class="wt" style="font-family:'dotum','sans-self'; font-size:11px; color:#ffffff;"><strong><%=topWarp_usernm%></strong> 님 환영합니다.</td>
			        <td width="54"><a href="javascript:mainmenu('998');"><img src="/images/portal/homepage_renewal/common/btn_logout.jpg" alt="로그아웃" /></a></td>
			        <td width="71"><a href="javascript:menuForward('90', '03');"><img src="/images/portal/homepage_renewal/common/btn_edit.jpg" alt="회원정보수정"  /></a></td>
			        <td width="254">&nbsp;</td>
			        <td width="64"><img src="/images/portal/homepage_renewal/common/txt_search.jpg" alt="과정검색" /></td>
			        <td width="66"><img src="/images/newmain_20100625/search_online.gif" width="72" height="20" onclick='onoffselect()' style='CURSOR:POINTER;' id="mainSearch"/></td>
			        <td width="152"><input name="topWarp_searchText" type="text" id="topWarp_searchText" size="30" onkeypress="search_enter(event)"/></td>
			        <td width="49"><a href="javascript:totalSubjSearch();"><img src="/images/portal/homepage_renewal/common/btn_search.jpg" alt="검색" /></a></td>
			        <%} %>
			        
					<!-- 로그인 시작 
					<td class="alignC"><img src="/images/common/icon_login.gif" alt="로그인"></td>
					<td class="alignL"><input type="text" class="login" onfocus="this.style.background='#ffffff'" tabindex="1"></td>
					<td class="alignL"><input type="password" class="pass" onfocus="this.style.background='#ffffff'" tabindex="2"></td>
					<td class="alignL"><img src="/images/common/btn_login.gif" alt="로그인" tabindex="3"></td>
					<td class="alignL"><img src="/images/common/btn_find.gif" alt="아이디/비밀번호 찾기"></td>
					<td class="alignL"><img src="/images/common/btn_join.gif" alt="회원가입"></td>
					 로그인 끝 
					 과정검색 시작 
					<td><img src="/images/common/txt_search.gif" alt="과정검색"></td>
					<td class="alignR">
						<select tabindex="11">
							<option>온라인</option>
							<option>오프라인</option>
						</select>
					</td>
					<td class="alignL"><input type="text" tabindex="12" class="search"></td>
					<td class="alignL"><img src="/images/common/btn_search.gif" alt="검색" tabindex="13"></td>
					 과정검색 끝 
				--></tr>
			</table>
		</div>
	</div>