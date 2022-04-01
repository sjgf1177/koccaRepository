<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>

<%
    String pageTitle = "고객센터";

    String titleTag = "";

    if ( isApp ) {
        titleTag = "고객센터";
    } else if ( isWeb ) {
        titleTag = "고객센터 | 한국콘텐츠아카데미";
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<title><%= titleTag %></title>
<link href="/mobile/assets/css/style.css" rel="stylesheet">
<script src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script src="/mobile/assets/js/json2.js"></script>
<script src="/mobile/assets/js/common.js"></script>
<script>
    var isApp = eval("<%= isApp %>");
    /**
     * 화면 초기화
     */
    $(document).ready(function() {
        $('.panel-category .panel-header a').click(function(){
            var panelBody = $(this).parent().parent().children('.panel-body');

            if (panelBody.length > 0) {
                if (panelBody.is(':hidden'))
                {
                    if ($(this).children('i').hasClass('icon-ar-down'))
                    {
                        $('.panel-category .panel-header a i').removeClass('icon-ar-up').addClass('icon-ar-down');
                        $(this).children('i').removeClass('icon-ar-down').addClass('icon-ar-up');
                    }
                    // $('.panel-body').hide();
                    panelBody.show();
                } else {
                    if ($(this).children('i').hasClass('icon-ar-down') || $(this).children('i').hasClass('icon-ar-up'))
                    {
                        $('.panel-category .panel-header a i').removeClass('icon-ar-up').addClass('icon-ar-down');
                    }
                    panelBody.hide();
                }
                return false;
            } else {
                return true;
            }
        });

    });

    /**
     * 1:1 문의 창 표시
     */
    function fnOpenContactUsPopup() {
        var isLogin = <%= isLogin %>;
        if ( isLogin ) {
            $("#oContactPopup").modal({show:true});

        } else {
            alert("해당 메뉴를 사용하려면 로그인을 해야 합니다.");
            fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet", location.href)
        }
    }

    /**
     * 1:1 문의 내용 등록
     */
    function fnRegisterContactUs() {
        var isPopupOpen = $("#oContactPopup").is(":visible");

        if (isPopupOpen) {
            if ($("#oTitle").val() == "") {
                alert("제목을 입력하세요.");
                $("#oTitle").focus();
                return;
            } else if ($("#oContents").val() == "") {
                alert("내용을 입력하세요.");
                $("#oContents").focus();
                return;
            } else {
                // 1:1 문의 등록
                var param = $("#oContactUsForm").serialize();
                $.ajax({
                        type : "post"
                    ,   url : "/servlet/controller.mobile.information.InformationServlet"
                    ,   dataType : "json"
                    ,   data : param
                    ,   success : function( data ) {
                            if ( data.result == 1 ) {
                                alert("등록되었습니다.");
                            } else {
                                alert("글을 등록하지 못했습니다.\n관리자에게 문의하세요.");
                            }
                            $("body").removeClass("modal-open");
                            $(".overlay").hide();
                            $(".modal").hide();
                            $("#oTitle").val("");
                            $("#oContents").val("");
                    }
                    ,   complete : function(arg1, arg2) {
                            // alert("complete : " + arg1 + " : " + arg2);
                    }
                });

            }
        } else {
            return;
        }
    }

</script>
</head>
<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/header.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<div class="container">
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="tel:02-6310-0770">문의전화 : 02-6310-0770</a>
        </div>
    </div>
<%
    if ( isApp ) {
%>
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="javascript:fnOpenContactUsPopup();">1:1 문의</a>
        </div>
    </div>
<%
    }
%>
</div>

<form id="oContactUsForm" name="contactUsForm">
    <input type="hidden" id="oProcess" name="process" value="registerContactUs">
<div id="oContactPopup" class="modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <a href="#" class="modal-close">X</a>
            <div class="modal-header">
                PC버전에서 온라인교육&gt;나의강의실&gt;상담내역에서 답변을 확인하실 수 있습니다.
            </div>
            <div class="mb10">
                <input type="text" style="width:93%;" placeholder="제목" id="oTitle" name="title">
            </div>
            <div class="mb10">
                <textarea style="width:98%; height:100px;" placeholder="내용" id="oContents" name="contents"></textarea>
            </div>
            <button type="button" class="btn btn-block btn-primary btn-lg" onclick="fnRegisterContactUs();">등록</button>
        </div>
    </div>
</div>
</form>
</body>
</html>