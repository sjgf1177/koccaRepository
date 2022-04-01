<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<div class="lnb_wrap">
    <span class="lnb_header">학습지원센터</span>
    <ul class="lnb_con">
        <li <c:if test="${param.left_active eq '07' }" > class="on"</c:if>><a href="javascript:menuForward('4','07');">공지사항</a></li>
        <li <c:if test="${param.left_active eq '01' }" > class="on"</c:if>><a href="javascript:menuForward('4','01');">FAQ</a></li>
        <li <c:if test="${param.left_active eq '02' }" > class="on"</c:if>><a href="javascript:menuForward('4','02');">QNA</a></li>
        <li <c:if test="${param.left_active eq '14' }" > class="on"</c:if>><a href="javascript:menuForward('4','14');">원격지원서비스</a></li>
    </ul>
</div>