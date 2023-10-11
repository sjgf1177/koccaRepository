<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<div class="lnb_wrap">
	<ul class="lnb_con d-flex">
		<li <c:if test="${param.left_active eq '11' }" > class="on"</c:if>><a href="javascript:menuForward('3','11');">수강중인과정</a></li>
		<li <c:if test="${param.left_active eq '02' }" > class="on"</c:if>><a href="javascript:menuForward('3','02');">나의교육이력</a></li>
		<li <c:if test="${param.left_active eq '12' }" > class="on"</c:if>><a href="javascript:menuForward('3','12');">나의상담내역</a></li>
	</ul>
</div>
