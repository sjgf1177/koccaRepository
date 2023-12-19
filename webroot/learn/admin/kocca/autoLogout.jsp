<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%
    session.invalidate();

    String titleTag = "";
    titleTag = "로그아웃 | 한국콘텐츠아카데미";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= titleTag %></title>
<link href="/mobile/assets/css/style.css" rel="stylesheet">
<script src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script src="/mobile/assets/js/common.js"></script>

<script>

window.onload = function () {

    alert("일정시간(120분) 미사용으로 자동 로그아웃 되었습니다.");
    parent.document.location.href="/learn/admin/kocca/login.jsp";

}
</script>

</head>
<body>
</body>
</html>
