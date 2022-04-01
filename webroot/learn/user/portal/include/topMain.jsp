<%@ page import="java.net.InetAddress" %>
<%@ include file="/learn/user/portal/include/site_info.jsp" %>
<%
    InetAddress inet= InetAddress.getLocalHost();
    String ipSplit = inet.getHostAddress();
    String [] ipnum=ipSplit.replace(".","/").split("/");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미(<%=ipnum[3]%>)</title>
<%if( box.getSession("tem_grcode").equals("N000001")) { %>
<link href="/css/portal/homepage_renewal/style.css" rel="stylesheet" type="text/css" />	
<%}else{ %>
<link rel="stylesheet" href="/css/newmenu_20100625.css" type="text/css" />
<%} %>
<script language="javascript" src="/script/flash_TagScript.js"></script>
<script language="javascript" src="/script/jquery-1.3.2.min.js"></script>
<script language="javascript" src="/script/jquery.timers.js"></script>
<script language="javascript" src="/script/jquery.form.js"></script>
<script language="javascript" src="/script/mainscript.js"></script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  //_gaq.push(['_setAccount', 'UA-24782208-1']);
  _gaq.push(['_setAccount', 'UA-33104445-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body >

<%@ include file="/learn/user/portal/include/common.jsp" %>		
		

<div id="header" <%=!koccaYn ? "class=\"asp_main\"" : "" %>>
    <!-- Top Warp 시작 -->
	<%@ include file="/learn/user/portal/include/topWarp.jsp" %>
	<!-- Top Warp 종료 -->
	
	<!-- Top Menu 시작 -->
	<%@ include file="/learn/user/portal/include/topMenu.jsp" %>
    <!-- Top Menu 종료 -->	

    <!-- Top Visual 시작 -->
    <%@ include file="/learn/user/portal/include/topVisual.jsp" %>
    <!-- Top Visual 종료 -->
</div>
<%box.put("mainYn", "Y"); %>
<%--<%@ include file="/learn/user/portal/include/topMenu.jsp" %>--%>
<div>
