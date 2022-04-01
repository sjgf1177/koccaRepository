<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<html lang="ko">
<head>
<title>한국콘텐츠아카데미 취업희망디딤돌 참여신청</title>
<style type="text/css" media="screen">@import "http://edu.kocca.or.kr/css/2012/common.css";</style>
<script type="text/javascript" src="/js/jquery/1.8.2/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Content-Language" content="euc-kr">
<meta http-equiv="imagetoolbar" content="false">
<meta name="robots" content="all">
<meta name="MSSmartTagsPreventParsing" content="true">
<meta name="description" content="">
<style type="text/css">
body { 
	background-color:#31a5be; 
}

table.doclayout {
	width:100%;
	height:100%;
}

table.doclayout td.layout {
	text-align:center;
	vertical-align:middle;
}

img.logo {
	margin-bottom:5px;
}

div.boxline {
	width:400px;
	border:5px dotted white;
	margin:0 auto;
	text-align:left;
}

table.request {
	margin:20px;
}

table.request td {
	font-family:"malgun gothic","gulim","dotum","sans-serif";
	font-size:14px;
	font-weight:bold;
	color:white;
	padding-top:6px;
	padding-bottom:6px;
	padding-left:10px;
}

table.request td input {
	font-family:"gulim","dotum","sans-serif";
	font-size:12px;
	color:black;
	padding:2px;
}

/*---*/
table.request td select {
	font-family:"gulim","dotum","sans-serif";
	font-size:12px;
	color:black;
	padding:1px;
}
/*---*/

.formsend {
	margin-top:10px;
	font-family:"malgun gothic","gulim","dotum","sans-serif";
	font-size:14px;
	font-weight:bold;
	color:#1b8bea;
	line-height:24px;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$('#requestinsert').submit(function(){
			var returnmsg = $.makeArray($(this).find('input:text').map(function() {
				return $(this);
			}));
			var intxtmax = $(returnmsg).length-1;
			for (i=0; i<=intxtmax; i++) {
				if($(returnmsg)[i].val() == "") {
					alert($(returnmsg)[i].attr('title'));
					$(returnmsg)[i].focus();
					return false;
				}
			}
		});
	});
</script>
</head>
<body>
<table class="doclayout" cellpadding="0" cellspacing="0">
	<tr>
		<td class="layout">
			<a href="http://edu.kocca.or.kr" tabindex="1" target="_blank" title="한국콘텐츠아카데미 홈페이지로 이동합니다"><img src="/images/2012/sub/logo.png" class="logo"></a>
			<form id="requestinsert" method="post" enctype="multipart/form-data" action="/servlet/controller.homepage.HomePageContactServlet">
   			<input type = "hidden" name="p_process"    value = "insertVocation">
			<div class="boxline">
				<table class="request" cellpadding="0" cellspacing="0">
					<colgroup><col width="100"><col width="*"></colgroup>
					<tr>
						<td>회사명</td>
						<td><input name="company" type="text" tabindex="1" style="width:220px;" title="소속하신 회사명을 입력해주세요"></td>
					</tr>
					<tr>
						<td>회사업종</td>
						<td>
							<select id="selecttype" name="category" tabindex="2" title="소속하신 회사의 업종(분야)를 선택해주세요">
								<option>분야 선택</option>
								<option value="방송">방송</option>
								<option value="게임">게임</option>
								<option value="문화">문화</option>
								<option value="기타">기타</option>
							</select>
							</td>
					</tr>
				</table>
				<table class="request" cellpadding="0" cellspacing="0">
					<colgroup><col width="100"><col width="*"></colgroup>
					<tr>
						<td>담당자명</td>
						<td><input type="text" name="user_name" tabindex="3" style="width:100px;" title="담당하시는 분의 실명을 입력해주세요"></td>
					</tr>
					<tr>
						<td>담당자 직책</td>
						<td><input type="text" name="job" tabindex="4" style="width:150px;" title="담당하시는 분의 직책을 입력해주세요"></td>
					</tr>
					<tr>
						<td>담당자 연락처</td>
						<td>
							<input type="text" name="tel1" maxlength="4" tabindex="5" style="width:35px; text-align:center;" title="지역번호 또는 이동번호를 입력해주세요"> -
							<input type="text" name="tel2" maxlength="4" tabindex="6" style="width:35px; text-align:center;" title="연락처의 중간번호 3~4자리를 입력해주세요"> -
							<input type="text"  name="tel3"maxlength="4" tabindex="7" style="width:35px; text-align:center;" title="연락처의 끝 4자리를 입력해주세요">
						</td>
					</tr>
				</table>
			</div>
			<input type="submit" tabindex="8" class="formsend" value="위 내용으로 신청합니다">
			</form>
		</td>
	</tr>
</table>
</body>
</html>