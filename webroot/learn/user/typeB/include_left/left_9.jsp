<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<div class="lnb_wrap">
    <ul class="lnb_con d-flex">
<%--        <li <c:if test="${param.left_active eq '01' }" > class="on"</c:if>><a href="javascript:menuForward('9', '01');">������û�ȳ�</a></li>--%>
<%--        <li <c:if test="${param.left_active eq '02' }" > class="on"</c:if>><a href="javascript:menuForward('9', '02');">������û</a></li>--%>
<%--        <li <c:if test="${param.left_active eq '03' }" > class="on"</c:if>><a href="javascript:menuForward('9', '03');">������ûȮ��/���</a></li>--%>

        <li <c:if test="${param.p_area eq '' || param.p_area eq null || param.p_area eq 'ALL'}"> class="on"</c:if>><a href="javascript:selectArea('');"><span>��ü</span></a></li>
        <li <c:if test="${param.p_area eq 'B0' }"> class="on"</c:if>><a href="javascript:selectArea('B0');"><span>��ۿ���</span></a></li>
        <li <c:if test="${param.p_area eq 'G0' }"> class="on"</c:if>><a href="javascript:selectArea('G0');"><span>����</span></a></li>
        <li <c:if test="${param.p_area eq 'K0' }"> class="on"</c:if>><a href="javascript:selectArea('K0');"><span>��ȭ</span></a></li>
    </ul>
</div>