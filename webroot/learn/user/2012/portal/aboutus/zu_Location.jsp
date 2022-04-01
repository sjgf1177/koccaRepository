<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("topmenu","7");
	box.put("submenu","6");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > <u>오시는길</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/location.gif" alt="오시는길">
			</div>
		</div><br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="교육협력 공공기관으로 이동합니다">대중교동 이용안내</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="교육협력 기업으로 이동합니다">방송회관 층별안내</a></dd>
		</dl>
		<div class="locationbox">
			<iframe src="http://dna.daum.net/examples/maps/MissA/map_view.php?width=930&height=350&latitude=37.52688709196582&longitude=126.87156331424604&contents=%ED%95%9C%EA%B5%AD%EC%BD%98%ED%85%90%EC%B8%A0%EC%95%84%EC%B9%B4%EB%8D%B0%EB%AF%B8&zoom=4" width="930" height="350" scrolling="no" frameborder="0"></iframe>
		</div>
		<div class="locationtxt">
			<h5>158-715 서울시 종로구 연견동 대학로 57 홍익대 아트센터 건물 1층 (전화 02-2161-0077(온라인 교육), 02-2161-0072(오프라인 교육) / 팩스 02-2161-0078)</h5>
		</div>
		<div class="locationtip">
			<img src="/images/2012/sub/page7/subject_location01.gif" id="skip01">
			<img src="/images/2012/sub/page7/location_train.gif">
			<img src="/images/2012/sub/page7/location_bus.gif">
			<br><br><br>
			<img src="/images/2012/sub/page7/location_f.gif" id="skip02">
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>