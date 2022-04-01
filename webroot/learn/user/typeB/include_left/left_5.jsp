<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<div class="lnb_wrap">
	<span class="lnb_header">열린강좌</span>
	<ul class="lnb_con">
		<li <c:if test="${param.left_active eq '01' }" > class="on"</c:if>><a href="javascript:menuForward('5','01');">전체</a></li>
		<li <c:if test="${param.left_active eq '17' }" > class="on"</c:if>><a href="javascript:menuForward('5','17');">방송영상</a></li>
		<li <c:if test="${param.left_active eq '18' }" > class="on"</c:if>><a href="javascript:menuForward('5','18');">게임</a></li>
		<li <c:if test="${param.left_active eq '19' }" > class="on"</c:if>><a href="javascript:menuForward('5','19');">만화/애니/캐릭터</a></li>
		<li <c:if test="${param.left_active eq '20' }" > class="on"</c:if>><a href="javascript:menuForward('5','20');">문화일반</a></li>
		
		<%-- <li <c:if test="${param.left_active eq '22' }" > class="on"</c:if>><a href="javascript:menuForward('5','22');">콘텐츠인사이트</a></li>
		<li <c:if test="${param.left_active eq '23' }" > class="on"</c:if>><a href="javascript:menuForward('5','23');">마스터클래스</a></li> --%>
		
		<li <c:if test="${param.left_active eq '21' }" > class="on"</c:if>><a href="javascript:menuForward('5','21');">인문/경영/교양</a></li>
<%-- 		<li <c:if test="${param.left_active eq '26' }" > class="on"</c:if>><a href="javascript:menuForward('5','26');">인문학 강좌</a></li> --%>

	</ul>

</div>
