<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    response.setContentType("text/html;charset=euc-kr");
    String retUrl = box.getStringDefault("retUrl", "/servlet/controller.mobile.myclass.StudySubjectServlet");

    String titleTag = "";
    if ( isApp ) {
        titleTag = "로그인";
    } else if ( isWeb ) {
        titleTag = "로그인 | 한국콘텐츠아카데미";
    }
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
    var retUrl = "<%= retUrl %>";

    window.onload = function () {

        if ( retUrl != "" ) {
            var addParam = "username=" + encodeURIComponent("<%= box.getSession("name") %>");
            if ( retUrl.indexOf("?") > -1 ) {
                retUrl = retUrl + "&" + addParam;
            } else {
                retUrl = retUrl + "?" + addParam;
            }

            setTimeout("fnMoveNavi( 2, '" + retUrl + "', '')", 1000);
        }
    }
</script>

</head>
<body>

    <div class="body">
        <div class="login-header">
            <img src="/mobile/assets/img/login-header.png" alt="kocca 한국콘텐츠아카데미" width="165">
        </div>
    <div style="font-size:10pt; font-weight:bold; vertical-align:bottom; text-align:center; height:100%; padding: 250px 10px 10px 10px"><%= box.getSession("name")%>님 환영합니다.</div>
    </div>


</body>
</html>
