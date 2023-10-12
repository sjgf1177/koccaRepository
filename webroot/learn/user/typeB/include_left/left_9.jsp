<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<div class="onedpas_lnb_wrap">
    <ul class="lnb_con d-flex">
<%--        <li <c:if test="${param.left_active eq '01' }" > class="on"</c:if>><a href="javascript:menuForward('9', '01');">수강신청안내</a></li>--%>
        <li <c:if test="${param.left_active eq '02' }" > class="on"</c:if>><a href="javascript:menuForward('9', '02');">수강신청</a></li>
        <li <c:if test="${param.left_active eq '03' }" > class="on"</c:if>><a href="javascript:menuForward('9', '03');">수강신청확인/취소</a></li>
    </ul>
</div>