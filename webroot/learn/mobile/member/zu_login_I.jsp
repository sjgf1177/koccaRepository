<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
%>
<%@ include file="/learn/mobile/include/common.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="euc-kr"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta name="format-detection" content="telephone=no"/>
<title><%=v_title.toString()%></title>
<link rel="stylesheet" type="text/css" href="/css/m_common.css" />
<script type="text/javascript" language="javascript" src="/js/common.js"></script>
<script type="text/javascript" language="javascript">
	//로그인 
	function MemberValidate()
	{
		var obj = document.writeForm;
		if(f_nul_chk(obj.p_topuserid, "[아이디]를")) return false;
		else if(f_nul_chk(obj.p_toppwd, "[비밀번호]를")) return false;
		setValue();
		obj.submit();
	}
	
	//이벤트 키다운 
	function KeyEvent(e)
	{
		var obj = document.writeForm;
		if(e.keyCode == 13)	MemberValidate(obj);
	}
	
	function setCookie( name, value, expiredays ) 
	{ 
	    var todayDate = new Date(); 
	    todayDate.setDate( todayDate.getDate() + expiredays ); 
	    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" 
	} 
	
	//쿠키값 얻어온다.
	function getCookie(eventName) 
	{ 
		var flag = document.cookie.indexOf(eventName + '='); 
	
		if (flag != -1) 
		{  
			flag    += eventName.length + 1;
			end =  document.cookie.indexOf(';', flag);
	
			if (end == -1) 
					end = document.cookie.length;
			
			return unescape(document.cookie.substring(flag, end));
		} 
	}
	
	
	function setValue()
	{
		var obj = document.writeForm;
		
		// 아이디 저장 설정
		if(obj.sID.checked)
		{	setCookie ('p_topuserid', obj.p_topuserid.value, 365);
			setCookie ('p_toppwd', obj.p_toppwd.value, 365);
		}
		else
		{
			setCookie ('p_topuserid', "", 1);
			setCookie ('p_toppwd', "", 1);
		}
	}
	
	function getValue()
	{
		var obj = document.writeForm;
		
	    var id = getCookie("p_topuserid");
	    var pwd = getCookie("p_toppwd");
		
	    if(typeof(id) == "undefined")
	    	id = "";
	
	    if(typeof(pwd) == "undefined")
	    	pwd = "";
	    
	    if(id != null && id != "")
		{
	    	obj.p_topuserid.value = id;
	    	obj.sID.checked = true;
	    }
	    
	    if(pwd != null && pwd != "")
	    {
	    	obj.p_toppwd.value = pwd;
<%
	if("".equals(box.getString("p_loginFail")))
	{
%>
			//setTimeout("MemberValidate()", "2000");
<%
	}
%>
	   	}
	    else
	    {
	    	obj.p_topuserid.focus();
		}   
	}
</script>
</head>
<body class="other" onload="javascript:getValue();">
<div id="header">
	
	<h1><a href="/"><img src="/images/mobile/sub_logo.gif" alt="로고" /></a></h1> 
	
</div>
<div class="contentWrap">
	<h1 class="login_bg"></h1>
</div>

<div id="koccaContent">
	<form name="writeForm" method="post" action="/servlet/controller.mobile.member.LoginServlet" onsubmit="return MemberValidate();">
	<input type="hidden" name="p_process" value="loginproc"/>
	<input type="hidden" name="p_frmURL" value="<%=box.getString("p_frmURL") %>"/>   
	<div id="login_area">
		
		<p class="uid">
			<label class="invisible" for="id">아이디 </label>
			<input name="p_topuserid" id="id" value="" tabindex="1" title="아이디 or 이메일 입력" />			
		</p>
		<p class="upw">
			<label class="invisible" for="inputPwd" >비밀번호</label>
			<input type="password" name="p_toppwd" id="inputPwd" value="" title="비밀번호 입력" tabindex="2" />
		</p>
		<p class="ubtn">				
			<input type="checkbox" id="sID" name="sID" onclick="javascript:setValue();"/> <label id=""  class=""  for="">자동로그인</label>
			<button id="loginBtn" type="submit"><span>로그인</span></button>
		</p>
	</div>
	</form>
	<div id="login_info01" class="">
		<p class="info_txt01">					
		일부 브라우저에서는 로그인 정보저장이 지원되지 않을	수 있습니다.
		</p>
	</div>
	<div id="login_info02" class="">
		<p class="info_txt02">					
		회원가입 및 아이디/비밀번호 찾기는 PC화면(웹)에서 한국
		콘텐츠진흥원 한국콘텐츠아카데미
		를 방문하시면 이용하실 수 있습니다.
		</p>
	</div>	
	


</div>

<div id="koccaFoot">
	<address>COPYRIGHT@2011 한국콘텐츠진흥원 All Right Reserverd</address>
</div>


</body>
</html>
