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
	box.put("submenu","5");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > <u>교육협력기관 안내</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/corp.gif" alt="교육협력기관 안내">
			</div>
		</div><br>
		<table class="linecolor" cellspacing="0" cellpadding="0">
			<colgroup><col width="25%"><col width="25%"><col width="25%"><col width="*"></colgroup>
			<tr>
				<td class="color1"></td>
				<td class="color2"></td>
				<td class="color3"></td>
				<td class="color4"></td>
			</tr>
		</table>
		<div class="corpbox">
			<div class="partnershipbox">
				<img src="/images/2012/sub/page7/corp/a01.gif" alt="문화체육관광부" title="문화체육관광부">
				<img src="/images/2012/sub/page7/corp/a02.gif" alt="송파구" title="송파구">
				<img src="/images/2012/sub/page7/corp/a03.gif" alt="한국소리문화의전당" title="한국소리문화의전당">
				<img src="/images/2012/sub/page7/corp/a04.png" alt="중앙경찰학교" title="중앙경찰학교">
				<img src="/images/2012/sub/page7/corp/a05.png" alt="중앙선거관리위원회" title="중앙선거관리위원회">
				<img src="/images/2012/sub/page7/corp/b01.gif" alt="부산경남대표방송KNN" title="부산경남대표방송KNN">
				<img src="/images/2012/sub/page7/corp/b02.gif" alt="SBS" title="SBS">
				<img src="/images/2012/sub/page7/corp/b03.png" alt="홈앤쇼핑" title="홈앤쇼핑">
				<img src="/images/2012/sub/page7/corp/b04.png" alt="주식회사 호미" title="주식회사 호미">
				<img src="/images/2012/sub/page7/corp/b05.png" alt="한국HD방송주식회사" title="한국HD방송주식회사">
				<img src="/images/2012/sub/page7/corp/c01.gif" alt="세종대학교" title="세종대학교">
				<img src="/images/2012/sub/page7/corp/c02.gif" alt="중부대학교" title="중부대학교">
				<img src="/images/2012/sub/page7/corp/c03.gif" alt="서울호서전문학교" title="서울호서전문학교">
				<img src="/images/2012/sub/page7/corp/d01.gif" alt="경기모바일과학고등학교" title="경기모바일과학고등학교">
				<img src="/images/2012/sub/page7/corp/d02.gif" alt="상일미디어고등학교" title="상일미디어고등학교">
				<img src="/images/2012/sub/page7/corp/d03.gif" alt="서울디지텍고등학교" title="서울디지텍고등학교">
				<img src="/images/2012/sub/page7/corp/d04.png" alt="천안천일고등학교" title="천안천일고등학교">
				<img src="/images/2012/sub/page7/corp/d05.png" alt="한국게임과학고등학교" title="한국게임과학고등학교">
				<img src="/images/2012/sub/page7/corp/d06.png" alt="한세사이버보안고등학교" title="한세사이버보안고등학교">
			</div>
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>