<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "java.util.ArrayList" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    int seq = 0;
    int rnk = 0;
    String vodImg = "";
    String tutorNm = "";
    String lecNm = "";

    String pageTitle = "열린강좌";

    String titleTag = "";

    if ( isApp ) {
        titleTag = "열린강좌";
    } else if ( isWeb ) {
        titleTag = "분류별 | 열린강좌 | 한국콘텐츠아카데미";
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
<script src="/mobile/assets/js/json2.js"></script>
<script src="/mobile/assets/js/common.js"></script>
<script src="/mobile/assets/js/openclass.js"></script>

<script>
var currentCategoryID = "";
/**
 * url 이동
 * @param type : app / web 구분
 * @param url : 이동할 url
 * @param parameter : 파라미터 값
 */
function fnSelectCategoryList( lectureCls, step, oCategoryID, lectureClsNm) {

    var isPannelBodyClosed = $(oCategoryID).is(':hidden');

    if ( isPannelBodyClosed || (!isPannelBodyClosed && step != "step1") ) {

        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.mobile.openclass.OpenClassCategoryServlet"
            ,   dataType : "json"
            ,   data : "process=openClassCategoryList&lectureCls=" + lectureCls + "&step=" + step
            ,   success : function (data) {

                    currentCategoryID = oCategoryID;

                    var list = "";
                    var currListCnt = 0;
                    var totalListCnt = 0;
                    var targetObj = $(currentCategoryID).children("ul");

                    $.each( data.openClassCategoryList, function(){
                        list = "<li style=\"display:block;\">\n";
                        list += "<a href=\"javascript:fnOpenClassViewDetail(2, " + this.d_seq + ", 'category');\">\n";
                        list += "<span class=\"thumb\"><img src=\"" + this.d_vodimg + "\" alt=\"\"></span>\n";
                        list += "<span class=\"body\">\n";
                        list += "<span class=\"name\">" + this.d_lecnm + "</span>\n";
                        list += "<span class=\"text\" style=\"font-weight:bold; color:#808080\">" + this.d_tutornm + "</span>\n";
                        // list += "<span class=\"text\">" + this.d_intro.replace("\r\r\n", "<br/>") + "</span>\n";
                        list += "</span>\n";
                        list += "</a>\n";
                        list += "</li>\n";

                        targetObj.append(list);
                        totalListCnt = this.d_cnt;
                    });

                    if ( isPannelBodyClosed ) {
                        $(oCategoryID).show();
                    }

                    if ( totalListCnt > 3 && step == "step1") {
                        $(oCategoryID).append("<a href=\"javascript:fnSelectCategoryList('" + lectureCls + "', 'step2', '" + oCategoryID + "', '" + lectureClsNm + "');\" class=\"btn-more\">더보기 <i class=\"icon icon-cir-plus\"></i></a>");
                    } else if ( totalListCnt > 8 && step == "step2") {
                        $(oCategoryID).append("<a href=\"javascript:fnSelectCategoryDetailList('" + lectureCls + "', '" + lectureClsNm + "');\" class=\"btn-all\">전체보기</a>");
                        $(oCategoryID).children(".btn-more").remove();
                    } else if ( totalListCnt < 8 && step == "step2") {
                        $(oCategoryID).children(".btn-more").remove();
                    }
                }
            ,   complete : fnOnCompleteCategoryList
            ,   error :  fnOnErrorCategoryList
        });

    } else {
        $(oCategoryID).hide();
        $(oCategoryID).children(".thumb-list").empty();
        $(oCategoryID).children(".btn-more").remove();
        $(oCategoryID).children(".btn-all").remove();
    }
}


function fnOnErrorCategoryList( arg1, arg2) {
    // alert("error : " + arg1 + " || " + arg2);
}

function fnOnCompleteCategoryList( arg1, arg2 ) {
    // alert("complete : " + arg1 + " || " + arg2);
}

/**
 * 열린강좌 분류별 상세 목록으로 이동
 */
function fnSelectCategoryDetailList( lectureCls, lectureClsNm ) {
    var url = "/servlet/controller.mobile.openclass.OpenClassCategoryServlet?process=openClassCategoryDetailList&step=step3&lectureCls=" + lectureCls + "&lectureClsNm=" + lectureClsNm;

    fnMoveNavi( "<%= naviType %>", url, "");
}


</script>
</head>
<body>

<%@ include file="/mobile/jsp/include/header.jsp" %>

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<!-- tabs -->
<ul class="tabs tabs-auto-width tabs-fixed-top">
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.openclass.OpenClassPopularServlet');"><i class="icon icon-cir-ar-right"></i> 인기별</a></li>
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.openclass.OpenClassThemeServlet');""><i class="icon icon-cir-ar-right"></i> 테마별</a></li>
    <li class="active"><a href="#"><i class="icon icon-cir-ar-right"></i> 분류별</a></li>
</ul>
<!-- /tabs -->

<div class="container" <%= containerStyle %>>
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnSelectCategoryList('GC01', 'step1', '#oCategoryGC01', '방송영상');">방송영상 <i class="icon icon-cir-ar-down"></i></a>
        </div>
        <div class="panel-body" id="oCategoryGC01">
            <ul class="thumb-list">
            </ul>
        </div>
    </div>
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnSelectCategoryList('GC02', 'step1', '#oCategoryGC02', '게임');">게임 <i class="icon icon-cir-ar-down"></i></a>
        </div>
        <div class="panel-body" id="oCategoryGC02">
            <ul class="thumb-list">
            </ul>
        </div>
    </div>
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnSelectCategoryList('GC03', 'step1', '#oCategoryGC03', '만화애니캐릭터');">만화/애니/캐릭터 <i class="icon icon-cir-ar-down"></i></a>
        </div>
        <div class="panel-body" id="oCategoryGC03">
            <ul class="thumb-list">
            </ul>
        </div>
    </div>
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnSelectCategoryList('GC04', 'step1', '#oCategoryGC04', '음악/공연');">문화일반 <i class="icon icon-cir-ar-down"></i></a>
        </div>
        <div class="panel-body" id="oCategoryGC04">
            <ul class="thumb-list">
            </ul>
        </div>
    </div>
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnSelectCategoryList('GC05', 'step1', '#oCategoryGC05', '인문학강좌');">인문/경영/교양 <i class="icon icon-cir-ar-down"></i></a>
        </div>
        <div class="panel-body" id="oCategoryGC11">
            <ul class="thumb-list">
            </ul>
        </div>
    </div>
    <!-- <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnSelectCategoryList('GC05', 'step1', '#oCategoryGC05', '기타');">기타 <i class="icon icon-cir-ar-down"></i></a>
        </div>
        <div class="panel-body" id="oCategoryGC05">
            <ul class="thumb-list">
            </ul>
        </div>
    </div> -->

</div>
</body>
</html>
