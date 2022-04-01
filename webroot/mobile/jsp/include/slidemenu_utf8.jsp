<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>

<!-- sidenav -->
<div class="overlay" style="display:none;"></div>
<div id="sidenav">
    <ul>
<%
    if ( isApp ) {
        if ( isLogin ) {
%>
        <li style="color:#efefef;">
            <i class="icon icon-s-cs"></i><a href="javascript:fnLogout();"><%= box.getSession("name") %>님 환영합니다. 로그아웃</a>
        </li>

        <li>
            <a href="javascript:fnMoveMenu(2, '/servlet/controller.mobile.myclass.LastSubjectServlet', false);"><i class="icon icon-s-myclass"></i>나의 강의실</a>
        </li>
<%
        } else {
%>
        <li style="color:#efefef;">
            <i class="icon icon-s-cs"></i><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.user.LoginServlet', '');">로그인</a>
        </li>
<%
        }
%>
        <li>
            <a href="javascript:fnMoveMenu(2, '/servlet/controller.mobile.onlineclass.OnlineClassCategoryServlet', false);"><i class="icon icon-s-online"></i>정규과정</a>
        </li>
        <li>
            <a href="javascript:fnMoveMenu(2, '/servlet/controller.mobile.openclass.OpenClassFavorServlet', true); "><i class="icon icon-s-favor"></i>찜한강좌</a>
        </li>
<%
    }

    if ( isWeb || isApp ) {
%>
        <li>
            <a href="javascript:fnMoveMenu(2, '/servlet/controller.mobile.openclass.OpenClassPopularServlet', false);"><i class="icon icon-s-open"></i>열린강좌</a>
        </li>
        <li>
            <a href="javascript:fnMoveMenu(2, '/servlet/controller.mobile.information.FAQServlet', false);"><i class="icon icon-s-faq"></i>FAQ</a>
        </li>
        <li>
            <a href="javascript:fnMoveMenu(2, '/servlet/controller.mobile.information.NoticeServlet', false);"><i class="icon icon-s-notice"></i>공지사항</a>
        </li>
        <li>
            <a href="javascript:fnMoveMenu(2, '/servlet/controller.mobile.information.InformationServlet', false);""><i class="icon icon-s-intro"></i>아카데미소개</a>
        </li>
        <li>
            <a href="javascript:fnMoveMenu(2, '/mobile/jsp/information/information.jsp', false);""><i class="icon icon-s-cs"></i>고객센터</a>
        </li>
        <li>
            <a href="javascript:fnMoveMenu(2, 'http://edu.kocca.or.kr/servlet/controller.homepage.MainServlet', false);" style="padding-left:10px;">PC 화면</a>
        </li>
<%
    }
%>
    </ul>
</div>
<!-- /sidenav -->
