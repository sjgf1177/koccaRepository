<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<div class="lnb_wrap">
    <span class="lnb_header">회원/로그인</span>
    <ul class="lnb_con">
    	<c:choose>
			<c:when test="${sessionScope.userid eq null || sessionScope.userid == ''}">
		        <li <c:if test="${param.left_active eq '990' }" > class="on"</c:if>><a href="javascript:mainmenu('990');">로그인</a></li>
		        <li <c:if test="${param.left_active eq '1' }" > class="on"</c:if>><a href="javascript:mainmenu('1');">회원가입</a></li>
		        <li <c:if test="${param.left_active eq '2' }" > class="on"</c:if>><a href="javascript:mainmenu('2');">아이디/비밀번호 찾기</a></li>
			</c:when>
			<c:otherwise>
				<c:if test="${sessionScope.agreechk ne 'N'}">
					<li <c:if test="${param.left_active eq '4' }" > class="on"</c:if>><a href="javascript:mainmenu('4');">개인정보 변경</a></li>
		        </c:if>
				<c:if test="${sessionScope.agreechk eq 'N'}">
					<li class="on">단체가입 회원 최초 이용약관동의</li>
		        </c:if>		        
			</c:otherwise>
		</c:choose>
    </ul>
</div>