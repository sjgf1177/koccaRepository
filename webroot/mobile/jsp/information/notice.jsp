<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>

<%
    // 공지사항 목록
    ArrayList noticeList = (ArrayList)request.getAttribute("noticeList");
    DataBox dbox = null;

    String pageTitle = "공지사항";

    int seq = 0;
    int totCnt = 0;
    String adTitle = "";
    String adContent = "";
    String onOffType = "";
    String addate = "";

    String titleTag = "";

    if ( isApp ) {
        titleTag = "공지사항";
    } else if ( isWeb ) {
        titleTag = "공지사항 | 한국콘텐츠아카데미";
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

var currListCnt = 0;

function fnViewMoreNoticeList() {
    currListCnt = $(".notice-list").children("li").length;

    var i = 1;
    $.ajax({
            type : "get"
        ,   url : "/servlet/controller.mobile.information.NoticeServlet"
        ,   dataType : "json"
        ,   data : "process=noticeList&reqType=ajax&currListCnt=" + currListCnt
        ,   success : function (data) {

                var list = "";
                var totalListCnt = 0;
                var targetObj = $(".notice-list");

                $.each( data.noticeList, function() {
                    list = "<li>\n";
                    list += "    <div class=\"header\" id=\"oHeader" + (currListCnt + i) + "\">\n";
                    list += "        <a href=\"javascript:fnViewContents(" + (currListCnt + i) + ");\">\n";
                    if( this.d_ntt_type == "1" ) {
                        list += "            <i class=\"icon-notice\">공지</i>\n";
                    } else {
                        list += "            <i class=\"icon-event\">일반</i>\n";
                    }
                    list += "            " + this.d_ntt_sj + "<br/>\n";
                    list += "            <span style=\"font-size:8pt; font-weight:normal;\">" + this.d_frst_register_pnttm + "</span>\n";
                    list += "            <i class=\"icon icon-ar-down\"></i>\n";
                    list += "        </a>\n";
                    list += "    </div>\n";
                    list += "    <div class=\"body\" id=\"oContent" + (currListCnt + i) + "\">\n";
                    list += "        " + this.d_ntt_cn + "\n";
                    list += "    </div>\n";
                    list += "</li>\n";

                    targetObj.append(list);
                    totalListCnt = this.d_totcnt;
                    i++;
                });

                currListCnt = targetObj.children("li").length;

                if ( currListCnt == totalListCnt ) {
                    $("#oBtnMore").hide();
                }

                // fnInitIconArrow();

            }
        ,   complete : function(arg1, arg2) {
                // alert("result : " + arg2);
            }
        ,   error :  function(arg1, arg2) {
            }
    });
}

function fnInitIconArrow() {
	$(".notice-list .header a").bind("click", function() {
		var body = $(this).parent().parent().children(".body");
		if (body.is(":hidden"))		{
			$(".notice-list .header a .icon-ar-up").removeClass("icon-ar-up").addClass("icon-ar-down");
			$(this).children(".icon-ar-down").removeClass("icon-ar-down").addClass("icon-ar-up");
			$(".notice-list .body").hide();
			body.show();
		} else {
			$(".notice-list .header a .icon-ar-up").removeClass("icon-ar-up").addClass("icon-ar-down");
			body.hide();
		}
		return false;
	});
}

function fnViewContents(listIdx, seq) {
    var headerObj = $("#oHeader" + listIdx);
    var contentObj = $("#oContent" + listIdx);

    if (contentObj.is(":hidden"))		{
        $(".notice-list li .body").hide();
        $(".notice-list .header a .icon-ar-up").removeClass("icon-ar-up").addClass("icon-ar-down");
        headerObj.children("a").children(".icon-ar-down").removeClass("icon-ar-down").addClass("icon-ar-up");
        contentObj.show();
    } else {
        contentObj.hide();
        $(".notice-list .header a .icon-ar-up").removeClass("icon-ar-up").addClass("icon-ar-down");
    }

    fnUpdateViewCount( seq );
}

function fnUpdateViewCount( seq ) {
}

$(document).ready(function() {
    // fnInitIconArrow();
});

</script>
</head>
<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/header.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<ul class="notice-list">
<%
    for ( int i = 0 ; i < noticeList.size(); i++ ) {
        dbox = (DataBox)noticeList.get(i);
//         seq = dbox.getInt("d_seq");
//         totCnt = dbox.getInt("d_totcnt");
//         adTitle = dbox.getString("d_adtitle");
//         adContent = dbox.getString("d_adcontent");
//         onOffType = dbox.getString("d_onoffgubun");
//         addate = dbox.getString("d_addate");
        
        
        seq = dbox.getInt("d_ntt_id");
        totCnt = dbox.getInt("d_totcnt");
        adTitle = dbox.getString("d_ntt_sj");
        adContent = dbox.getString("d_ntt_cn");
        onOffType = dbox.getString("d_ntt_type");
        addate = dbox.getString("d_frst_register_pnttm");
%>
    <li>
        <div class="header" id="oHeader<%= i %>">
            <a href="javascript:fnViewContents(<%= i %>, <%= seq %>);" data-value="N">
<%
        if ( onOffType.equals("1") ) {
%>
                <i class="icon-notice">공지</i>
<%
        } else {
%>
                <i class="icon-event">일반</i>
<%
        }
%>
                <%= adTitle %><br/>
                <span style="font-size:8pt; font-weight:normal;"><%= addate %></span>
                <i class="icon icon-ar-down"></i>
            </a>
        </div>
        <div class="body" id="oContent<%= i %>">
            <%= adContent %>
        </div>
    </li>
<%
    }
%>

<!--
    <li>
        <div class="header">
            <a href="#">
                <i class="icon-event">이벤트</i>
                제목 입니다 제목 입니다
                <i class="icon icon-ar-down"></i>
            </a>
        </div>
        <div class="body">
            내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다
        </div>
    </li>
//-->
</ul>
<a href="javascript:fnViewMoreNoticeList();" class="btn btn-link btn-lg btn-block" id="oBtnMore">더보기 <i class="icon icon-cir-plus"></i></a>
</body>
</html>