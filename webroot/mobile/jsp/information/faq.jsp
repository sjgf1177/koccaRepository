<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>

<%
    // 정규과정 직업별 목록
    ArrayList faqList = (ArrayList)request.getAttribute("faqList");
    DataBox dbox = null;

    String pageTitle = "FAQ";

    String faqCategoryNm = "";
    String title = "";
    String contents = ""; 

    String currFaqCategory = "";
    String prevFaqCategory = "";

    String titleTag = "";

    if ( isApp ) {
        titleTag = "FAQ";
    } else if ( isWeb ) {
        titleTag = "FAQ | 한국콘텐츠아카데미";
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
var isWeb = <%= isWeb %>;
$(function(){
	$('.panel-category .panel-header a').click(function(){
		var panelBody = $(this).parent().parent().children('.panel-body');

		if (panelBody.length > 0) {
			if (panelBody.is(':hidden')) {
				if ($(this).children('i').hasClass('icon-ar-down')) {
					$('.panel-category .panel-header a i').removeClass('icon-ar-up').addClass('icon-ar-down');
					$(this).children('i').removeClass('icon-ar-down').addClass('icon-ar-up');
				}
				// $('.panel-body').hide();
				panelBody.show();
			} else {
				if ($(this).children('i').hasClass('icon-ar-down') || $(this).children('i').hasClass('icon-ar-up')) {
					$('.panel-category .panel-header a i').removeClass('icon-ar-up').addClass('icon-ar-down');
				}
				panelBody.hide();
			}
			return false;
		} else {
			return true;
		}
	});

    if ( isWeb ) {
        $('body').css('padding-top', 43);
    }
});
</script>
</head>
<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/header.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<div id="faq-list" style="position:absolute; top: 0px;">
<%
    for ( int i = 0 ; i < faqList.size(); i++ ) {
        dbox = (DataBox)faqList.get(i);
//         currFaqCategory = dbox.getString("d_faqcategory");
//         faqCategoryNm = dbox.getString("d_faqcategorynm");
        title = dbox.getString("d_ntt_sj");
        contents = dbox.getString("d_ntt_cn");

        //if ( !currFaqCategory.equals(prevFaqCategory) ) {
          //  if ( i > 0) {
                out.println("</div>");
            //}
%>
<%-- 
<h2 class="page-subject-bar" style="color:#f00;"><%= faqCategoryNm %></h2>
<div class="container">
 --%>
<%
        //}
%>

    <div class="panel panel-category">
        <div class="panel-header">
            <a><%= title %> <i class="icon icon-ar-down"></i></a>
        </div>
        <div class="panel-body panel-body-info">
            <%= contents %>
        </div>
    </div>

<%
        if( i == faqList.size() - 1 ) {
%>
</div>
<%
        }

        //prevFaqCategory = currFaqCategory;

    }
%>
</div>
</body>
</html>