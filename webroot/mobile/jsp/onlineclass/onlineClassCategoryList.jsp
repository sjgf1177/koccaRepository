<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "java.util.ArrayList" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    // 정규과정 분야별 목록
    ArrayList categoryList = (ArrayList)request.getAttribute("categoryList");
    DataBox dbox = null;
    
    String pageTitle = "정규과정";

    String titleTag = "";

    if ( isApp ) {
        titleTag = "정규과정";
    } else if ( isWeb ) {
        titleTag = "분야별 목록 | 정규과정 | 한국콘텐츠아카데미";
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
<script src="/mobile/assets/js/onlineclass.js"></script>

<script>
var currentCategoryID = "";
/**
 * 목록 조회
 */
function fnSelectCategoryList( clsCd, step, categoryId, clsNm) {

    var isPannelBodyClosed = $(categoryId).is(':hidden');

    if ( isPannelBodyClosed || (!isPannelBodyClosed && step != "step1") ) {

        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassCategoryServlet"
            ,   dataType : "json"
            ,   data : "process=selectCategorySubjectList&clsCd=" + clsCd + "&step=" + step
            ,   success : function (data) {

                    currentCategoryID = categoryId;

                    var list = "";
                    var currListCnt = 0;
                    var totalListCnt = 0;
                    var targetObj = $(currentCategoryID).children("ul");

                    $.each( data.categorySubjectList, function(){

                        list = "<li style=\"display:block;\">\n";
                        list += "   <a href=\"javascript:fnViewOnlineClassDetail(2, '" + this.d_subj + "', '" + this.d_year + "', '" + this.d_subjseq + "');\">\n";
                        list += "       <span class=\"thumb\"><img src=\"" + this.d_subjfilenamenew + "\" alt=\"\"></span>\n";
                        list += "       <span class=\"body\">\n";

                        if ( this.d_mobile_use_yn == "Y" ) {
                            list += "       <span class=\"name\"><img src=\"/mobile/assets/img/mobile_icon.png\" style=\"width:13px; height:13px;\" alt=\"모바일학습가능\">" + this.d_subjnm + "</span>\n";
                        } else {
                            list += "       <span class=\"name\">" + this.d_subjnm + "</span>\n";
                        }
                        list += "       <span class=\"text\">" + this.d_intro.replace("\r\r\n", "<br/>") + "</span>\n";
                        list += "       </span>\n";
                        list += "   </a>\n";
                        list += "</li>\n";

                        targetObj.append(list);
                        totalListCnt = this.d_cnt;
                    });

                    if ( isPannelBodyClosed ) {
                        $(categoryId).show();
                    }

                    if ( totalListCnt > 3 && step == "step1") {
                        $(categoryId).append("<a href=\"javascript:fnSelectCategoryList('" + clsCd + "', 'step2', '" + categoryId + "', '" + clsNm + "');\" class=\"btn-more\">더보기 <i class=\"icon icon-cir-plus\"></i></a>");
                    } else if ( step == "step2") {
                        $(categoryId).append("<a href=\"javascript:fnSelectCategoryDetailList('" + clsCd + "', '" + clsNm + "');\" class=\"btn-all\">전체보기</a>");
                        $(categoryId).children(".btn-more").remove();
                    }
                }
            ,   complete : fnOnCompleteCategoryList
            ,   error :  fnOnErrorCategoryList
        });

    } else {
        $(categoryId).hide();
        $(categoryId).children(".thumb-list").empty();
        $(categoryId).children(".btn-more").remove();
        $(categoryId).children(".btn-all").remove();
    }
}


function fnOnErrorCategoryList( arg1, arg2) {
    // alert("error : " + arg1 + " || " + arg2);
}

function fnOnCompleteCategoryList( arg1, arg2 ) {
    // alert("complete : " + arg1 + " || " + arg2);
}

/**
 * 정규과정 분야별 상세 목록으로 이동
 */
function fnSelectCategoryDetailList( clsCd, clsNm ) {
    var url = "/servlet/controller.mobile.onlineclass.OnlineClassCategoryServlet?process=selectCategoryDetailList&clsCd=" + clsCd + "&clsNm=" + clsNm;

    fnMoveNavi( 2, url, "");
}

</script>
</head>
<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/header.jsp" %>
<!-- // header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<!-- tabs -->
<ul class="tabs tabs-auto-width tabs-fixed-top">
    <li class="active"><a href="#"><i class="icon icon-cir-ar-right"></i> 분야별</a></li>
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.onlineclass.OnlineClassJobServlet');"><i class="icon icon-cir-ar-right"></i> 직업별</a></li>
</ul>
<!-- // tabs -->

<div class="container" <%= containerStyle %>>
<%
    String categoryId = "";
	boolean view = true;
	String check_id = (String)session.getAttribute("userid");
    for (int i = 0 ; i < categoryList.size() ; i++ ) {
        dbox = (DataBox)categoryList.get(i);
        categoryId = "oCategoryID" + (i+1);
        view = true;
        if("1006".equals(dbox.getString("d_cls_cd")) && (!"kkj9699".equals(check_id) && !"jmh8263".equals(check_id) && !"lee1".equals(check_id) )){
        	view = false;
        }
        
        // 창조경제타운 모바일 오픈 안함
        if("1009".equals(dbox.getString("d_cls_cd"))){
        	view = false;
        }
        
        if(view){
%>
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnSelectCategoryList('<%= dbox.getString("d_cls_cd") %>', 'step1', '#<%= categoryId %>', '<%= dbox.getString("d_cls_nm") %>');"><%= dbox.getString("d_cls_nm") %> <i class="icon icon-cir-ar-down"></i></a>
        </div>
        <div class="panel-body" id="<%= categoryId %>">
            <ul class="thumb-list">
            </ul>
        </div>
    </div>
<%
        }
    }
%>

</div>
</body>
</html>
