<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "java.util.ArrayList" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    // 열린강좌 인기별 목록
    ArrayList openClassThemeList = (ArrayList)request.getAttribute("openClassThemeList");

    // String deviceType = box.getString("device_type");

    int cnt = 0;
    String themeNm = "";
    String code = "";

    String pageTitle = "열린강좌";

    String titleTag = "";
    if ( isApp ) {
        titleTag = "열린강좌";
    } else if ( isWeb ) {
        titleTag = "테마별 | 열린강좌 | 한국콘텐츠아카데미";
    }

    String containerStyle = (isCheckApp) ? "style=\"padding-top:93px;\"" : "";
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
<script src="/mobile/assets/js/openclass.js"></script>
<script>
    /**
     * 열린강좌 테마별 목록 화면으로 이동한다.
     * @param type : app / web 구분
     * @param lectureTheme : 강좌 테마 코드
     * @param themeNm : 테마 제목
     */
    function fnOpenClassThemeDetailList ( type, lectureTheme,  themeNm, cnt) {
        if ( cnt == 0 ) {
            alert("등록된 강좌가 없습니다.");
            return;
        } else {
            var url = "/servlet/controller.mobile.openclass.OpenClassThemeServlet?process=openClassThemeDetailList&lectureTheme=" + lectureTheme + "&themeNm=" + themeNm;

            fnMoveNavi( type, url)
        }
    }

</script>
</head>
<body>
<!-- header -->
<%@ include file="/mobile/jsp/include/header.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<!-- tabs -->
<ul class="tabs tabs-auto-width tabs-fixed-top">
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.openclass.OpenClassPopularServlet');"><i class="icon icon-cir-ar-right"></i> 인기별</a></li>
    <li class="active"><a href="#"><i class="icon icon-cir-ar-right"></i> 테마별</a></li>
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.openclass.OpenClassCategoryServlet');"><i class="icon icon-cir-ar-right"></i> 분류별</a></li>
</ul>
<!-- /tabs -->

<div class="container" <%= containerStyle %>>
<%
    DataBox dbox = null;
    for ( int i = 0 ; i < openClassThemeList.size() ; i++ ) {
        dbox = (DataBox)openClassThemeList.get(i);
        themeNm = dbox.getString("d_codenm");
        code = dbox.getString("d_code");
        cnt = dbox.getInt("d_cnt");

        if ( cnt > 0 ) {
%>
    <div class="panel panel-theme">
        <div class="panel-header">
            <a href="javascript:fnOpenClassThemeDetailList(2, '<%= code %>', '<%= themeNm %>', <%= cnt %>);"><%= themeNm %> (<%= cnt %>) <i class="icon icon-ar-right"></i></a>
        </div>
    </div>
<%
        }
    }
%>

    <!--
    <div class="panel panel-theme">
        <div class="panel-header">
            <a href="#">휴넷열린강좌 (1027) <i class="icon icon-text-box icon-primary">제휴</i> <i class="icon icon-ar-right"></i></a>
        </div>
    </div>
    //-->

</div>
</body>
</html>