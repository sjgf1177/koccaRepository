<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<script type="text/javascript">
	<c:if test="${sessionScope.tem_type eq '' || sessionScope.tem_type eq null}" >
 		alert("비정상적인 접근입니다.");
 		document.location = "/";
	</c:if>
</script>