<%@ page contentType = "text/html;charset=MS949" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<title>COMMON UI</title>
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Content-Language" content="euc-kr">
<meta http-equiv="imagetoolbar" content="false">
<meta name="robots" content="all">
<meta name="MSSmartTagsPreventParsing" content="true">
<meta name="description" content="">
<style>
html,body { width:100%; height:100%; margin:0; background-color:#666666;text-align:left; }
a { color:#ffffff; }
img { border:0px; margin:3px 0px 0px 3px; }
</style>
<script type="text/javascript">
	$(function(){
		var returnmsg = $.makeArray($('frame', parent.ebody.document).map(function() {
			return $(this);
		}));
		var intxtmax = $(returnmsg).length-1;
		for (i=0; i<=intxtmax; i++) {
 			if(!$(returnmsg)[i].attr('id') && !$(returnmsg)[i].attr('name')){
 				$(returnmsg)[i].remove();
 				$('frameset', parent.ebody.document).removeAttr('rows');
 				$('frameset', parent.ebody.document).removeAttr('cols');
 			}
 			if($(returnmsg)[i].attr('id') || $(returnmsg)[i].attr('name')){
 				$(returnmsg)[i].attr('scrolling','no')
 			}
		}
	});
</script>
</head>
<body>
</body>
</html>