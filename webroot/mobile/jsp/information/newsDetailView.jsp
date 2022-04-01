<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import="com.credu.library.DataBox" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>

<%
    String pageTitle = "보도자료";
    String previousURL = "";
    previousURL = ( previousURL.equals("") ) ? "javascript:history.back(-1);" : previousURL;

    DataBox dbox = (DataBox)request.getAttribute("newsDetail");

    int seq = 0;
    int totCnt = 0;
    String adTitle = "";
    String adContent = "";
    String onOffType = "";

    String titleTag = "";

    if ( isApp ) {
        titleTag = "고객센터";
    } else if ( isWeb ) {
        titleTag = "보도자료 | 고객센터 | 한국콘텐츠아카데미";
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
/**
 * 보도자료 상세 화면 이동
 */
function fnViewNewsDetail( seq ) {
    fnMoveNavi("2", "/servlet/controller.mobile.information.NewsServlet?process=selectNewsDetail&seq=" + seq, "");
}
</script>
</head>
<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/sub_header.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<div class="article-view">
    <h2><%= dbox.getString("d_title") %></h2>
    <p class="date"><%= dbox.getString("d_ldate") %></p>
<%
    if ( !dbox.getString("d_savefile").equals("") ) {
%>
    <div class="img">
        <img src="<%= dbox.getString("d_savefile") %>" alt="">
    </div>
<%
    }
%>
    <div><%= dbox.getString("d_content") %></div>
</div>
<ul class="article-view-footer-list">
<%
    if ( dbox.getInt("d_prev_seq") != 0) {
%>
    <li>
        <a href="javascript:fnViewNewsDetail(<%= dbox.getInt("d_prev_seq")%>);">
            <i>이전글</i>
            <%= dbox.getString("d_prev_title") %>
        </a>
    </li>
<%
    }

    if ( dbox.getInt("d_next_seq") != 0) {
%>
    <li>
        <a href="javascript:fnViewNewsDetail(<%= dbox.getInt("d_next_seq")%>);">
            <i>다음글</i>
            <%= dbox.getString("d_next_title") %>
        </a>
    </li>
<%
    }
%>
</ul>
</body>
</html>