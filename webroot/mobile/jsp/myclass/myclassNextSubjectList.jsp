<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "java.util.ArrayList" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    String titleTag = "";

    if ( isApp ) {
        titleTag = "나의강의실";
    } else if ( isWeb ) {
        titleTag = "다음과정 | 나의강의실 | 한국콘텐츠아카데미";
    }

    if ( !isLogin ) {
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
<script src="/mobile/assets/js/common.js"></script>
<script src="/mobile/assets/js/json2.js"></script>
<script>
    var isApp = eval("<%= isApp %>");

    $(document).ready( function() {
        alert("해당 메뉴를 사용하려면 로그인을 해야 합니다.");
        if ( isApp ) {
            fnMoveNavi(1, "/servlet/controller.mobile.user.LoginServlet", location.href);
        } else {
            fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet", location.href);
        }
    });
</script>
<body>
</body>
</html>
<%
    } else {

        ArrayList nextSubjectList = (ArrayList)request.getAttribute("nextSubjectList");
        DataBox dbox = null;

        String pageTitle = "나의 강의실";
        String mobileUseYn = "";
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
<script src="/mobile/assets/js/common.js"></script>
<script src="/mobile/assets/js/onlineclass.js"></script>
<script>

    /**
     * 과정별 상세 메뉴 팝업 열기
     */
    function fnOpenUtilMenu( subj, year, subjseq, idx ) {

        $("#oSubj").val( subj );
        $("#oYear").val( year );
        $("#oSubjseq").val( subjseq );

        var subjText = $('.btn-util-menu').eq(idx).parent().children("a").find(".name").text();
        $("#oSubjText").val( subjText );

        var utilMenu = $(".popover-util-menu");
        var utilMenuBtn = $(".btn-util-menu").eq(idx);
        var offset = utilMenuBtn.offset();

        if (utilMenuBtn.hasClass("toggle-in")) {
            $(".popover-util-menu").hide();
            $(".btn-util-menu").removeClass("toggle-in");

        } else {
            $(".btn-util-menu").removeClass("toggle-in");
            utilMenuBtn.addClass("toggle-in");
            $(".popover-util-menu").show();
        }

        $(".popover-util-menu").css({top:offset.top+20,left:offset.left-80});
        return false;
    }

    /**
     * 상세 화면으로 이동
     */
    function fnViewSubjectDetail() {
        var subj = $("#oSubj").val();
        var year = $("#oYear").val();
        var subjseq = $("#oSubjseq").val();

        fnViewOnlineClassDetail(2, subj, year, subjseq);

        $(".popover-util-menu").hide();
        $(".btn-util-menu").removeClass("toggle-in");
    }

    /**
     * SNS 공유 팝업 열기
     */
    function fnOpenSNSSharePopup() {
        $(".overlay").show();
        $("#oSNSShareModal").modal({show:true});

        var idx = $("#oIdx").val();
        var utilMenuBtn = $('.btn-util-menu').eq(idx);

        $(".popover-util-menu").hide();
        $(".btn-util-menu").removeClass("toggle-in");
    }

    /**
     * SNS 공유하기
     */
    function fnShareOnSNS( snsName ) {
        var subj = $("#oSubj").val();
        var year = $("#oYear").val();
        var subjseq = $("#oSubjseq").val();
        var url = location.href;

        url = url.substring(0, url.indexOf("/servlet/"));
        url += "/servlet/controller.mobile.onlineclass.OnlineClassDetailServlet?process=selectOnlineClassDetail&subj=" + subj + "&year=" + year + "&subjseq=" + subjseq;

        $("#oSNSShareModal").modal({show:false});
        $(".overlay").hide();

        fnSendSns( snsName, url, $("#oSubjText").val());
    }

    /**
     * 수강취소
     */
    function fnCancleApplyOnlineClass(subj, year, subjseq) {
        if( confirm("수강신청을 취소하시겠습니까?") ) {

            var subj = $("#oSubj").val();
            var year = $("#oYear").val();
            var subjseq = $("#oSubjseq").val();

            $.ajax({
                    type : "get"
                ,   url : "/servlet/controller.mobile.onlineclass.ApplySubjectServlet"
                ,   dataType : "json"
                ,   data : "process=cancelApplyOnllineClass&subj=" + subj + "&year=" + year + "&subjseq=" + subjseq
                ,   success : function (data) {

                        if ( data.resultMsg == "cancel.apply.ok" ) {
                            msg = "수강신청이 취소되었습니다";
                            $(".popover-util-menu").hide();

                            fnSelectNextSujectList();

                        } else {
                            msg = "수강신청 취소에 실패하였습니다.\n관리자에게 문의하십시오.";
                        }

                        alert(msg);
                    }
                ,   complete : function(arg1, arg2) {
                        // alert("delet apply ajax result : " + arg2);
                    }
            });

        } else {
            return;
        }
    }

    function fnSelectNextSujectList() {
        var listStr = "";
        var i = 0;
        $.ajax({
                type: "get"
            ,   url: "/servlet/controller.mobile.myclass.NextSubjectServlet?reqType=ajax"
            ,   dataType: "json"
            ,   success: function(data){

                    $(".thumb-list-lecture").empty();

                    $.each(data.nextSubjectList, function() {
                        listStr = "<li>\n";
                        listStr += "    <a href=\"#\">\n";
                        listStr += "        <span class=\"thumb\"><img src=" + this.d_introducefilenamenew + " alt=\"\"></span>\n";
                        listStr += "        <span class=\"body\">\n";
                        listStr += "            <span class=\"name\">" + this.d_subjnm + "</span>\n";
                        listStr += "            <span class=\"text\">\n";
                        listStr += "                학습기간 " + this.d_edustart + " ~ " + this.d_eduend + "\n";
                        listStr += "            </span>\n";
                        listStr += "        </span>\n";
                        listStr += "    </a>\n";
                        listStr += "    <a onclick=\"javascript:fnOpenUtilMenu('" + this.d_subj + "', '" + this.d_year + "', '" + this.d_subjseq + "', " + i + ");\" class=\"btn-util-menu\"><i class=\"icon icon-sq3\"></i></a>\n";
                        listStr += "</li>\n";

                        $(".thumb-list-lecture").append( listStr );
                        i++;

                    });
                }
            ,   complete: function(arg1, arg2) {
                }
            ,   error: function(arg1, arg2) {
                }
        });
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
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.myclass.LastSubjectServlet', '');"><i class="icon icon-cir-ar-right"></i> 지난과정</a></li>
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.myclass.StudySubjectServlet', '');"><i class="icon icon-cir-ar-right"></i> 학습중</a></li>
    <li class="active"><a href="#"><i class="icon icon-cir-ar-right"></i> 다음과정</a></li>
    <li><a href="javascript:fnMoveNavi(2, '/servlet/controller.mobile.myclass.FavorSubjectServlet', '');"><i class="icon icon-cir-ar-right"></i> 찜한과정</a></li>
</ul>
<!-- /tabs -->

<div class="container">

    <div class="panel">
        <ul class="thumb-list thumb-list-lecture">
<%
    try {
        if ( nextSubjectList == null || nextSubjectList.size() == 0 ) {
%>
            <li class="no-data">다음 강의 목록이 없습니다.</li>
<%
        } else {
            for( int i = 0 ; i < nextSubjectList.size() ; i++ ) {
                dbox = (DataBox) nextSubjectList.get(i);
                mobileUseYn = dbox.getString("d_mobile_use_yn");
%>
            <li>
                <a href="#">
                    <span class="thumb"><img src="<%= dbox.getString("d_introducefilenamenew") %>" alt=""></span>
                    <span class="body">
                        <span class="name" style="font-size:14px; font-weight:bold; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; width:94%;">
<%
                if ( mobileUseYn.equals("Y") ) {
%>
                            <img src="/mobile/assets/img/mobile_icon.png" style="width:13px; height:13px;" alt="모바일학습가능" />
<%
                }
%>
                            <%= dbox.getString("d_subjnm") %>
                        </span>
                        <span class="text">
                            학습기간 <%= dbox.getString("d_edustart") %> ~ <%= dbox.getString("d_eduend") %>
                        </span>
                    </span>
                </a>
                <a onclick="fnOpenUtilMenu('<%= dbox.getString("d_subj") %>', '<%= dbox.getString("d_year") %>', '<%= dbox.getString("d_subjseq") %>', <%= i %>)" class="btn-util-menu"><i class="icon icon-sq3"></i></a>
            </li>
<%
            }
        }
%>
        </ul>
        <!--
        <a href="#" class="btn-more">더보기 <i class="icon icon-cir-plus"></i></a>
        <a href="#" class="btn-all">전체보기</a>
        //-->
    </div>
</div>

<div class="popover bottom popover-util-menu">
    <div class="arrow"></div>
    <div class="popover-content">
        <ul>
            <li><a href="javascript:fnCancleApplyOnlineClass();"><i class="icon icon-u-cancel"></i> 신청취소</a></li>
            <li><a href="javascript:fnViewSubjectDetail();"><i class="icon icon-u-view"></i> 과정상세보기</a></li>
            <li><a href="javascript:fnOpenSNSSharePopup();"><i class="icon icon-u-share"></i> 공유하기</a></li>
        </ul>
    </div>
</div>

<div id="oSNSShareModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<a href="#" class="modal-close">X</a>
			<div class="modal-header">
				다음으로 공유
			</div>
			<ul class="modal-share">
				<li><a href="javascript:fnShareOnSNS('facebook');"><i class="icon icon-facebook"></i>페이스북</a></li>
				<li><a href="javascript:fnShareOnSNS('twitter');"><i class="icon icon-twitter"></i>트위터</a></li>
				<li><a href="javascript:fnShareOnSNS('kakaotalk');"><i class="icon icon-kakaotalk"></i>카카오톡</a></li>
			</ul>
		</div>
	</div>
</div>

<form id="oNextSubjectForm" name="nextSubjectForm">
    <input type="hidden" id="oSubj" name="subj" value="" />
    <input type="hidden" id="oYear" name="year" value="" />
    <input type="hidden" id="oSubjseq" name="subjseq" value="" />
    <input type="hidden" id="oIdx" name="idx" value="" />
    <input type="hidden" id="oSubjText" name="subjText" value="" />
</form>

<%
    } catch(Exception e) {
        out.println("오류가 발생하였습니다. 관리자에게 문의하세요.<br/>" + e.getMessage());
        System.out.println("Exception occured in /mobile/jsp/myclass/myclassNextSubjectList.jsp\n" + e.getMessage());
    }
%>
</body>
</html>
<%
    }
%>