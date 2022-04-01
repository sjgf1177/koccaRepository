<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.library.StringManager" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.regex.Pattern" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    try {
        DataBox subjectInfo = (DataBox)request.getAttribute("subjectInfo"); // 과정 기본정보

        ArrayList subjLessonList = (ArrayList)request.getAttribute("subjLessonList"); // 차시 목록
        ArrayList subjReviewList = (ArrayList)request.getAttribute("subjReviewList"); // 후기 목록
        ArrayList nextProposeSubjList = (ArrayList)request.getAttribute("nextProposeSubjList"); // 연관 강좌

        DataBox dbox = null;

        String pageTitle = "정규과정";
        String previousURL = "javascript:history.back(-1);";
        
        String v_creduURL = "/contents/credu/start.html";

        String year = box.getString("year");
        String subj = box.getString("subj");
        String subjseq = box.getString("subjseq");
        String subjNm = subjectInfo.getString("d_subjnm");
        String bizType = subjectInfo.getString("d_biz_type");
        String newYn = subjectInfo.getString("d_new_yn");
        String intro = subjectInfo.getString("d_intro");
        String introduceFileName = subjectInfo.getString("d_introducefilenamenew");
        String eduYn = subjectInfo.getString("d_edu_yn");
        String propYn = subjectInfo.getString("d_prop_yn");
        String favorYn = subjectInfo.getString("d_favor_yn");

        String mobileUseYn = subjectInfo.getString("d_mobile_use_yn");
        String mobilePreURL = subjectInfo.getString("d_mobile_preurl");
        String mobilePreURL2 = subjectInfo.getString("d_mobile_preurl2");

        mobilePreURL = (mobilePreURL.equals("") || mobilePreURL.equals("Y")) ? mobilePreURL2 : mobilePreURL;
        String param = "process=selectStudySubjectInfo&subj=" + subj + "&year=" + year + "&subjseq=" + subjseq + "&subjnm=" + subjNm;
        String titleTag = "";

        if ( isApp ) {
            titleTag = "정규과정";
        } else if ( isWeb ) {
            titleTag = subjNm + " | 정규과정 | 한국콘텐츠아카데미";
        }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta property="og:title" content="<%= subjNm %>">
<meta property="og:url" content="http://m.edu.kocca.kr/servlet/controller.mobile.onlineclass.OnlineClassDetailServlet?year=<%= year %>&subj=<%= subj %>&subjseq=<%= subjseq %>">
<meta property="og:description" content="<%= intro %>">
<meta property="og:image" content="http://m.edu.kocca.kr<%= introduceFileName %>">

<title><%= titleTag %></title>
<link href="/mobile/assets/css/style.css" rel="stylesheet">
<script src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script src="/mobile/assets/js/common.js"></script>
<script src="/mobile/assets/js/json2.js"></script>
<script src="/mobile/assets/js/onlineclass.js"></script>
<script>

    var isLogin = <%= isLogin %>;

    /**
     * 과정(정규과정/열린강좌) 찜하기
     *
     */
    function fnRegSubjFavor( classType, subj, jobType ) {

        var param = "classType=" + classType + "&subj=" + subj + "&jobType=" + jobType;
        var successMsg = "";

        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.mobile.subj.SubjFavorServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {

                    if ( data.isLogin == true ) {
                        if ( data.resultCnt > 0) {

                            if (jobType == "register" ) {
                                $(".mb20 > a").eq(1).attr("onclick", "javascript:fnRegSubjFavor( '01', '" + subj + "', 'cancel' );").text("찜취소");
                                successMsg = "찜한 강좌에 추가되었습니다.";
                            } else {
                                $(".mb20 > a").eq(1).attr("onclick", "javascript:fnRegSubjFavor( '01', '" + subj + "', 'register' );").text("찜하기");
                                successMsg = "찜한 강좌에서 삭제되었습니다.";
                            }

                            alert( successMsg );
                        } else {
                            alert("오류가 발생하였습니다.\n관리자에게 문의하세요.");
                            return;
                        }
                    } else {
                        alert("해당 메뉴를 사용하려면 로그인을 해야 합니다.");
                        fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet?process=loginPage", location.href);
                        return;
                    }
                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }

        });
    }


    $(document).ready(function() {
        $(".tabs-auto-width > li").bind("click", function() {

            $(".tabs-auto-width > li").removeClass("active");
            $(this).addClass("active");

            $("#oSubjectGoal").hide();
            $("#oSubjectIntro").hide();
            $("#oSubjectLesson").hide();
            $("#oSubjectPeriod").hide();
            $("#oReview").hide();
            $("#oNextSubj").hide();

            if( $(this).index() == 0 ) {
                $("#oSubjectGoal").show();
                $("#oSubjectIntro").show();
                $("#oSubjectLesson").show();
                $("#oSubjectPeriod").show();
            } else if( $(this).index() == 1 ) {
                $("#oReview").show();
            } else if( $(this).index() == 2 ) {
                $("#oNextSubj").show();
            }
        });


        $(".star-select > button").bind("click", function() {
            var point = $(this).index() + 1;

            if ( isLogin ) {
                $("#oPoint").val( point );
                $("#oReviewModal").modal({show:true});
                $("#oRegisterButton").show();
                $("#oModifyButton").hide();
                $(".modal-star-rating > .star > a").removeClass("icon-star-on").addClass("icon-star-off");
                $("#oContents").val("");

                if ( point == 5 ) {
                    $(".modal-star-rating > .star > a").removeClass("icon-star-off").addClass("icon-star-on");
                } else {
                    $(".modal-star-rating > .star > a").eq(point).prevAll().removeClass("icon-star-off").addClass("icon-star-on");
                }

                $("#oContents").focus();
            } else {
                alert("해당 메뉴를 사용하려면 로그인을 해야 합니다.");
                fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet?process=loginPage", location.href);
                return;
            }
            /*
            $.ajax({
                    type : "get"
                ,   url : "/mobile/jsp/common/checkLoginStatus.jsp"
                ,   dataType : "json"
                ,   success : function( data ) {
                        isLogin = data.isLogin;

                        if ( isLogin ) {
                            $("#oPoint").val( point );
                            $("#oReviewModal").modal({show:true});
                            $("#oRegisterButton").show();
                            $("#oModifyButton").hide();
                            $("#oContents").focus();
                            $(".modal-star-rating > .star > a").removeClass("icon-star-on").addClass("icon-star-off");
                            $("#oContents").val("");

                            if ( point == 5 ) {
                                $(".modal-star-rating > .star > a").removeClass("icon-star-off").addClass("icon-star-on");
                            } else {
                                $(".modal-star-rating > .star > a").eq(point).prevAll().removeClass("icon-star-off").addClass("icon-star-on");
                            }
                        } else {
                            alert("해당 메뉴를 사용하려면 로그인을 해야 합니다.");
                            fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet?process=loginPage", location.href);
                            return;
                        }
                }
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                }
            });
            */

        }) ;

        $(".modal-star-rating > .star > a").bind("click", function() {
            $("#oPoint").val( $(this).index() + 1);
            $(".modal-star-rating > .star > a").removeClass("icon-star-on").addClass("icon-star-off");
            $(this).removeClass("icon-star-off").addClass("icon-star-on");
            $(this).prevAll().removeClass("icon-star-off").addClass("icon-star-on");
        });

    });

    /**
     * 후기 수정 팝업 열기
     * @param num - 강좌 후기 번호
     * @param point - 강좌 별점
     */
    function fnOpenReviewModifyPopup( num, point ) {
        var cont = $("#oContents_" + num).val();
        $("#oNum").val(num);
        $("#oPoint").val(point);

        $("#oReviewModal").modal({show:true});
        $("#oContents").focus();
        $(".modal-star-rating > .star > a").removeClass("icon-star-on").addClass("icon-star-off");
        $("#oContents").val( cont );
        $("#oRegisterButton").hide();
        $("#oModifyButton").show();

        if ( point == 5 ) {
            $(".modal-star-rating > .star > a").removeClass("icon-star-off").addClass("icon-star-on");
        } else {
            $(".modal-star-rating > .star > a").eq(point).prevAll().removeClass("icon-star-off").addClass("icon-star-on");
        }
    }

    /**
     * 후기 등록
     */
    function fnRegisterReview() {
        if ($("#oPoint").val() == "") {
            alert("평가 별점을 선택해주세요.");
            return;
        }
        if ($("#oContents").val() == "") {
            alert("평가 내용을 입력해주세요.");
            $("#oContents").focus();
            return;
        }

        $("#oRegBtn").attr("disabled", true);
        $("#oProcess").val("registerReview");
        var param = $("#oReviewForm").serialize().replace(/%/g, '%25');

        $.ajax({
                type : "post"
            ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function( data ) {
                    $("#oReview > .panel-body > ul").empty();
                    $('body').removeClass('modal-open');
                    $('.overlay').hide();
                    $('.modal').hide();

                    alert("등록되었습니다.");

                    $("#oRegBtn").attr("disabled", false);
                    $("#oReview > .panel-header").hide();
                    fnSelectReviewList( "step1" );
            }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1 + " : " + arg2);
            }
        });

    }

    /**
     * 후기 수정
     */
    function fnModifyReview() {
        if ($("#oPoint").val() == "") {
            alert("평가 별점을 선택해주세요.");
            return;
        }
        if ($("#oContents").val() == "") {
            alert("평가 내용을 입력해주세요.");
            $("#oContents").focus();
            return;
        }

        if ( confirm("후기 및 별점을 수정하시겠습니까?") ) {
            $("#oProcess").val("modifyReview");
            var param = $("#oReviewForm").serialize().replace(/%/g, '%25');

            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function( data ) {
                        $("#oReview > .panel-body > ul").empty();
                        $('body').removeClass('modal-open');
                        $('.overlay').hide();
                        $('.modal').hide();

                        alert("수정되었습니다.");
                        fnSelectReviewList( "step1" );
                }
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1 + " : " + arg2);
                }
            });
        }
    }


    /**
     * 후기 삭제
     */
    function fnDeleteReview() {

        if ( confirm("후기를 삭제하시겠습니까?") ) {

            $("#oProcess").val("deleteReview");
            var param = $("#oReviewForm").serialize();
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function( data ) {
                        $("#oReview > .panel-body > ul").empty();
                        $('body').removeClass('modal-open');
                        $('.overlay').hide();
                        $('.modal').hide();

                        alert("삭제되었습니다.");
                        $("#oReview > .panel-header").show();
                        fnSelectReviewList( "step1" );
                }
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                }
            });
        }
    }

    /**
     * 후기 목록 가져오기
     */
    function fnSelectReviewList( step ) {
        $("#oProcess").val("selectReviewList");
        $("#oStep").val( step );
        var param = $("#oReviewForm").serialize();

        $.ajax({
                type : "post"
            ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function( data ) {
                    var reviewArea = $("#oReview > .panel-body > ul");
                    var reviewListStr = "";
                    var title = "";
                    var contents = "";
                    var maxLength = 0;
                    var currCnt = data.onlineClassReviewList.length;
                    var totCnt = 0;

                    if ( currCnt > 0 ) {
                        $.each( data.onlineClassReviewList, function(){
                            reviewListStr += "<li>\n";
                            reviewListStr += "<div class=\"author\">" + this.d_name + "\n";
                            if ( this.d_user_rnk == "1") {
                                reviewListStr += "<div class=\"offset-right\">\n";
                                reviewListStr += "  <a href=\"javascript:fnOpenReviewModifyPopup(" + this.d_num + ", " + this.d_point + ");\" class=\"btn-modify\">| 수정</a>\n";
                                reviewListStr += "</div>\n";
                            }
                            reviewListStr += "</div>\n";
                            reviewListStr += "<div class=\"star\">\n";

                            for( var i = 0 ; i < Number(this.d_point); i++ ) {
                                reviewListStr += "  <i class=\"icon icon-star-on-sm\"></i>\n";
                            }

                            for( var i = 0 ; i < (5 - Number(this.d_point)); i++ ) {
                                reviewListStr += "  <i class=\"icon icon-star-off-sm\"></i>\n";
                            }

                            reviewListStr += "  <span class=\"date\">" + this.d_reg_dt + "</span>\n";

                            contents = this.d_contents;
                            maxLength = (contents.length > 20) ? 20 : contents.length;
                            title = contents.substring(0, maxLength);
                            contents = ( maxLength <= 20 ) ? contents.substring(maxLength, contents.length) : "";

                            reviewListStr += "<div class=\"text\">\n";
                            reviewListStr += "  <strong>" + title + "</strong>" + contents +"\n";
                            reviewListStr += "  <input type=\"hidden\" name=\"contents_"+ this.d_num + "\" id=\"oContents_" + this.d_num + "\" value=\"" + title + contents + "\"/>\n";
                            reviewListStr += "</div>\n";
                            reviewListStr += "</li>\n";

                            totCnt = this.d_cnt;

                        });
                    } else {
                        reviewListStr = "<li class=\"no-data\">등록된 후기가 없습니다.</li>";
                    }

                    reviewArea.empty();
                    reviewArea.html( reviewListStr );

                    if( step == "step1" && totCnt > 3) {
                        // 더보기 버튼 보이기
                        $("#oReview > .panel-body > .btn-more").remove();
                        $("#oReview > .panel-body > .btn-all").remove();
                        $("#oReview > .panel-body").append("<a onclick=\"fnSelectReviewList('step2');\" class=\"btn-more\">더보기 <i class=\"icon icon-cir-plus\"></i></a>");
                    } else if ( step == "step2" ) {
                        if (totCnt > 5) {
                            // 전체보기 버튼 보이기
                            // 더보기 버튼 숨기기
                            $("#oReview > .panel-body > .btn-more").remove();
                            $("#oReview > .panel-body > .btn-all").remove();
                            $("#oReview > .panel-body").append("<a onclick=\"fnSelectReviewList('step3');\" class=\"btn-all\">전체후기 <i class=\"icon icon-cir-plus\"></i></a>");
                        } else {
                            // 더보기/전체 버튼 숨기기
                            $("#oReview > .panel-body > .btn-more").remove();
                            $("#oReview > .panel-body > .btn-all").remove();
                        }
                    } else if ( step == "step3" ) {
                        $("#oReview > .panel-body > .btn-more").remove();
                        $("#oReview > .panel-body > .btn-all").remove();
                    }
            }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg2);
            }
        });
    }

    /**
     * 수강신청하기
     */
    function fnApplyOnlineClass(subj, year, subjseq) {
        var msg = "";
        if ( !isLogin ) {
            alert("해당 메뉴를 사용하려면 로그인을 해야 합니다.");
            fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet?process=loginPage", location.href);
            return;
        }

        if( confirm("학습하시겠습니까?") ) {
            $.ajax({
                    type : "get"
                ,   url : "/servlet/controller.mobile.onlineclass.ApplySubjectServlet"
                ,   dataType : "json"
                ,   data : "process=applyOnllineClass&subj=" + subj + "&year=" + year + "&subjseq=" + subjseq
                ,   success : function (data) {

                        if ( data.resultMsg == "apply.ok" ) {
                            msg = "수강신청 되었습니다";
                            // 수강신청 버튼 텍스트 및 링크 변경 필요
                            $(".mb20 > a").eq(0).attr("onclick", "javascript:fnCancleApplyOnlineClass( '" + subj + "', '" + year + "', '" + subjseq + "');").text("수강취소");
                            $(".mb20 > a").eq(1).hide();
                        } else {
                            if ( data.resultMsg == "monthly.limit.over" ) {
                                msg = "현재 5개 과정 신청하셔서 추가 수강신청이 불가능합니다.\n(차수별 1인 최대 5개 과정까지 신청 가능)\n\n※ 다음 차수에 수강신청해주세요. (매월 1일, 15일 차수 오픈)";

                            } else if ( data.resultMsg == "already.apply.ok" ) {
                                msg = "이미 수강신청된 과정입니다.";

                            } else if ( data.resultMsg == "student.limit.full" ) {
                                msg = "수강신청 인원이 정원에 도달하였습니다.";

                            } else if ( data.resultMsg == "apply.error" ) {
                                msg = "관리자에게 문의하세요.";

                            } else if ( data.resultMsg == "apply.fail" ) {
                                msg = "수강신청에 실패하였습니다.\n관리자에게 문의하십시오.";

                            }
                        }

                        //alert(msg);
                        var subjnm = "<%= subjNm %>";
                        var param = "process=selectStudySubjectInfo&subj=" + subj + "&year=" + year + "&subjseq=" + subjseq + "&subjnm=" + subjnm;
                        
                        fnMoveNavi(2, "/servlet/controller.mobile.myclass.StudySubjectServlet?" + param, "");
                        
                    }
                ,   complete : function(arg1, arg2) {
                        // alert("register apply ajax result : " + arg2);
                    }
            });
        } else {
            return;
        }
    }

    /**
     * 수강취소
     */
    function fnCancleApplyOnlineClass(subj, year, subjseq) {
        if( confirm("수강신청을 취소하시겠습니까?") ) {

            $.ajax({
                    type : "get"
                ,   url : "/servlet/controller.mobile.onlineclass.ApplySubjectServlet"
                ,   dataType : "json"
                ,   data : "process=cancelApplyOnllineClass&subj=" + subj + "&year=" + year + "&subjseq=" + subjseq
                ,   success : function (data) {

                        if ( data.resultMsg == "cancel.apply.ok" ) {
                            msg = "수강신청이 취소되었습니다";
                            // 수강신청 버튼 텍스트 및 링크 변경 필요
                            $(".mb20 > a").eq(0).attr("onclick", "javascript:fnApplyOnlineClass( '" + subj + "', '" + year + "', '" + subjseq + "');").text("신청하기");
                            $(".mb20 > a").eq(1).attr("onclick", "javascript:fnRegSubjFavor( '01', '" + subj + "', 'register' );").text("찜하기");
                            $(".mb20 > a").eq(1).show();
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

    /**
     * SNS 공유하기
     */
    function fnShareOnSNS( snsName ) {

        var url = location.href;

        $("#oSNSShareModal").modal({show:false});
        $(".overlay").hide();

        fnSendSns( snsName, url, $("#oSubjText").val());
    }

    /**
     * 맛보기 강좌
     */
    function fnPreview(subj, year, subjseq, mobileUseYn, mobilePreURL) {

        if (mobileUseYn != "Y" ) {
            alert("모바일에서 지원되지 않는 과정입니다.");
            return;
        } else if ( mobilePreURL == "Y" ||  mobilePreURL == "") {
            alert("등록된 맛보기 강좌가 없습니다.");
            return;
        } else {

            // 콘텐츠 재생 시작
            // 아래는 NTC 뷰어로 동영상을 재생할 때 사용되는 모듈이다.

//             mobilePreURL = (mobilePreURL.indexOf("m.edu.kocca.kr") > -1 ) ? mobilePreURL : "http://m.edu.kocca.kr" + mobilePreURL;

           	mobilePreURL = (mobilePreURL.indexOf("/contents") == 0 ) ? "http://m.edu.kocca.kr" + mobilePreURL : mobilePreURL;
           	
        	if(mobilePreURL.indexOf(".ntc") > 0 ){
	            var preview = {	
	                "playNTC" : {
	                    "url": mobilePreURL,
	                    "page" : "1",
	                    "notifyURL" : "",
	                    "customInfo" : ""
	                }
	            };
	            document.location = "newin:@" + JSON.stringify(preview);
            }else if(mobilePreURL.indexOf("ksp.credu.com") > 0){
               	$.ajax({
           	            type : "post"
           	        ,   url : "/servlet/controller.contents.EduStart"
           	        ,   dataType : "json"
           	        ,   data : {
           	        	url : mobilePreURL,
           	        	p_process : "creduSubj"
           	        }
           	        ,   success : function(ajaxData) {
			            	location.href = ajaxData.videoURL;
           	        	}
           	        ,   error :  function(arg1, arg2) {
           	                alert("오류가 발생하여 플레이 할 수 없습니다.");
           	            }
           	    });
            }else{
            	location.href = mobilePreURL;
            }
            
        }
    }

    /**
     *
     */
    function fnGoToPCWeb(subj, year, subjseq) {
        var targetURL = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectPreviewPage&p_subj=" + subj + "&p_year=" + year + "&p_subjseq=" + subjseq;
        location.href = targetURL;
    }
</script>
</head>


<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/sub_header.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<div class="container">
    <div class="view-header-subject">
        <h2>
<%
    if ( mobileUseYn.equals("Y") ) {
%>
            <img src="/mobile/assets/img/mobile_icon.png" style="width:13px; height:13px;" alt="모바일학습가능" />
<%
    }
%>
            <%= subjNm %>
<%
    if ( newYn.equals("Y") ) {
%>
            <i class="icon icon-text-box icon-primary">신규</i>
<%
    }

    if ( bizType.equals("F01") ) {
%>
            <i class="icon icon-text-box icon-info">기본</i>
<%
    } if ( bizType.equals("F02") ) {
%>
            <i class="icon icon-text-box icon-info">실무</i>
<%
    }
%>
        </h2>
    </div>
    <div class="view-movie-frame">
        <a href="javascript:fnPreview('<%= subj %>', '<%= year %>', '<%= subjseq %>', '<%= mobileUseYn %>', '<%= mobilePreURL %>');">
            <img src="<%= introduceFileName %>" alt="">
            <i class="icon-play"><i></i></i>
        </a>
    </div>
    <div class="mb20">

        <!--<a onclick="javascript:fnApplyOnlineClass('<%= subj %>', '<%= year %>', '<%= subjseq %>');" class="btn btn-default">신청하기</a>
        <a onclick="javascript:fnCancleApplyOnlineClass('<%= subj %>', '<%= year %>', '<%= subjseq %>');" class="btn btn-default">수강취소</a>-->
        <%if(sUserId.equals("kkj9699")){%>
        <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
        <a id="kakao-link-btn" href="javascript:;">
			<img src="//dev.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"/>
		</a>
		<script type='text/javascript'>
		  //<![CDATA[
		    // // 사용할 앱의 JavaScript 키를 설정해 주세요.
		    Kakao.init('cece9b62660bfc6dd1a44078997ee746');
		    // // 카카오톡 링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.
		    Kakao.Link.createTalkLinkButton({
		      container: '#kakao-link-btn',
		      label: '카카오링크 샘플에 오신 것을 환영합니다.',
		      image: {
		        src: 'http://dn.api1.kage.kakao.co.kr/14/dn/btqaWmFftyx/tBbQPH764Maw2R6IBhXd6K/o.jpg',
		        width: '300',
		        height: '200'
		      },
		      webButton: {
		        text: '카카오 디벨로퍼스',
		        url: 'https://dev.kakao.com/docs/js' // 앱 설정의 웹 플랫폼에 등록한 도메인의 URL이어야 합니다.
		      }
		    });
		  //]]>
		</script>
        <%}%>
<%
    if ( isApp  ) {
        if ( propYn.equals("Y") ) {
%>
        <a onclick="javascript:fnMoveNavi(2, '/servlet/controller.mobile.myclass.StudySubjectServlet?<%= param %>', '');" class="btn btn-default">학습하기</a>
<%
        } else {
%>
        <a onclick="javascript:fnApplyOnlineClass('<%= subj %>', '<%= year %>', '<%= subjseq %>');" class="btn btn-default">학습하기</a>
<%
        }
    }

  

    if ( isApp && propYn.equals("N") ) {
        if ( favorYn.equals("Y") ) {
%>
        <a onclick="javascript:fnRegSubjFavor( '01', '<%= subj %>', 'cancel' );" class="btn btn-default">찜취소</a>
<%
        } else {
%>
        <a onclick="javascript:fnRegSubjFavor( '01', '<%= subj %>', 'register' );" class="btn btn-default">찜하기</a>
<%
        }
    }
%>
        <a onclick="javascript:fnOpenSharePopup();" class="btn btn-default">공유하기</a>
<%
    if ( isWeb && !isApp ) {
%>
        <a onclick="javascript:fnGoToPCWeb('<%= subj %>', '<%= year %>', '<%= subjseq %>');" class="btn btn-default">PC 화면</a>
<%
        if ( Pattern.matches("^[iphone/ipad/ipod]*$", deviceType) ) {
%>
        <a href="https://itunes.apple.com/us/app/kocca/id492151283?l=ko&ls=1&mt=8" target="_blank" class="btn btn-default">App 바로가기</a>
<%
        } else if ( Pattern.matches("^[android]*$", deviceType) ) {
%>
        <a href="https://play.google.com/store/apps/details?id=com.newin.kocca" target="_blank" class="btn btn-default">App 바로가기</a>
<%
        }
    }
%>
        <br>
        <div style="padding-top:12px; font-size:12px;">※정규과정의 &lt;신청&gt;, &lt;학습&gt;은 PC 또는 app에서 가능합니다.</div>
    </div>
    <ul class="tabs tabs-view tabs-auto-width">
        <li class="active"><a>소개</a></li>
        <li><a>후기</a></li>
        <li><a>연관과정</a></li>
    </ul>

    <div class="panel" id="oSubjectGoal">
        <div class="panel-header">
            <!-- <h3>교육목표</h3> //-->
            <a href="#" data-toggle="collapse">교육목표 <i class="icon icon-ar-up"></i></a>
        </div>
        <div class="panel-body panel-body-info">
            <%= subjectInfo.getString("d_intro") %>
        </div>
    </div>

    <div class="panel" id="oSubjectIntro">
        <div class="panel-header">
            <a href="#" data-toggle="collapse">교육내용 <i class="icon icon-ar-down"></i></a>
        </div>
        <div class="panel-body panel-body-collapse panel-body-info">
            <%= subjectInfo.getString("d_explain") %>
        </div>
    </div>

    <div class="panel" id="oSubjectLesson">
        <div class="panel-header">
            <a href="#" data-toggle="collapse">차시 <i class="icon icon-ar-down"></i></a>
        </div>
        <div class="panel-body panel-body-collapse panel-body-info">
            <ul class="step-list">
<%
        if ( subjLessonList != null && subjLessonList.size() > 0 ) {
            for( int i = 0 ;i < subjLessonList.size(); i++ ) {
            dbox = (DataBox)subjLessonList.get(i);
%>
                <li><strong><%= dbox.getInt("d_lesson") %>차시</strong><%= dbox.getString("d_sdesc") %></li>
<%
            }
        } else {
%>
                <li class="no-data"><strong>등록된 차시가 없습니다.</li>
<%
        }
%>
            </ul>
        </div>
    </div>

    <!--<div class="panel" id="oSubjectPeriod">
        <div class="panel-header">
            <h3>기간</h3>
        </div>
        <div class="panel-body panel-body-info">
            <strong>신청기간</strong> <%= subjectInfo.getString("d_propstart") %> ~ <%= subjectInfo.getString("d_propend") %><br>
            <strong>교육기간</strong> <%= subjectInfo.getString("d_edustart") %> ~ <%= subjectInfo.getString("d_eduend") %>
        </div>
    </div>
--><%
    int userRnk = 0;
    int totCnt = 0;
    if ( subjReviewList != null && subjReviewList.size() > 0 ) {
        dbox = (DataBox)subjReviewList.get(0);
        userRnk = dbox.getInt("d_user_rnk");
        totCnt = dbox.getInt("d_tot_cnt");
    }
%>
    <div class="panel panel-star-rating" style="display:none;" id="oReview">

<%
    //if ( userRnk == 0 && isApp) {
%>
        <div class="panel-header">
            <h3>평가</h3>
            <div class="star-select">
                <button class="icon icon-star-off"></button>
                <button class="icon icon-star-off"></button>
                <button class="icon icon-star-off"></button>
                <button class="icon icon-star-off"></button>
                <button class="icon icon-star-off"></button>
            </div>
        </div>
<%
    //}
%>
        <div class="panel-body">
            <ul>
<%
    int maxLength = 0;
    String title = "";
    String contents = "";
    if ( subjReviewList == null || subjReviewList.size() == 0 ) {
%>
                <li class="no-data">등록된 후기가 없습니다.</li>
<%
    } else {
        for( int i = 0; i < subjReviewList.size(); i++ ) {
            dbox = (DataBox)subjReviewList.get(i);
            contents = dbox.getString("d_contents");

            maxLength = (contents.length() > 20) ? 20 : contents.length();

            title = contents.substring(0, maxLength);
            contents = ( maxLength <= 20 ) ? contents.substring(maxLength, contents.length()) : "";

%>
                <li>
                    <div class="author">
                        <%= dbox.getString("d_name") %>
                        <div class="offset-right">
<%
            if ( sUserId.equals( dbox.getString("d_userid") ) ) {
%>
                            <a href="javascript:javascript:fnOpenReviewModifyPopup(<%= dbox.getInt("d_num") %>, <%= dbox.getInt("d_point") %>);" class="btn-modify">| 수정</a>
<%
            }
%>
                        </div>
                    </div>
                    <div class="star">
<%
            for( int j = 0; j < dbox.getInt("d_point") ; j++ ) {
%>
                        <i class="icon icon-star-on-sm"></i>
<%
            }

            for( int j = 0; j < (5 - dbox.getInt("d_point") ) ; j++ ) {

%>
                        <i class="icon icon-star-off-sm"></i>
<%
            }
%>
                        <span class="date"><%= dbox.getString("d_reg_dt") %></span>
                    </div>
                    <div class="text">
                        <strong><%= title %></strong> <%= contents.replaceAll("\r\n", "<br/>") %>
                        <input type="hidden" name="contents_<%= dbox.getInt("d_num") %>" id="oContents_<%= dbox.getInt("d_num") %>" value="<%= title + contents %>" />
                    </div>
                </li>
<%
        }
%>
            </ul>
<%

    }
%>
<%
    if ( totCnt > 5 ) {
%>
            <a href="#" class="btn-more">더보기 <i class="icon icon-cir-plus"></i></a>
<%
    }
%>
            <!-- <a href="#" class="btn-all">전체후기</a> //-->
        </div>
    </div>

    <div class="panel" style="display:none;" id="oNextSubj">
        <div class="panel-header">
            <h3>연관 과정</h3>
        </div>
        <div class="panel-body">
            <ul class="thumb-list thumb-list-lecture">
<%
    if ( nextProposeSubjList != null && nextProposeSubjList.size() > 0 ) {
        for ( int i = 0 ; i < nextProposeSubjList.size() ; i++ ){
            dbox = (DataBox)nextProposeSubjList.get(i);
%>
                <li>
                    <a href="javascript:fnViewOnlineClassDetail(2, '<%= dbox.getString("d_subj") %>', '<%= dbox.getString("d_year") %>', '<%= dbox.getString("d_subjseq") %>');">
                        <span class="thumb"><img src="<%= dbox.getString("d_introducefilenamenew") %>" alt=""></span>
                        <span class="body">
                            <span class="name"><%= dbox.getString("d_subjnm") %></span>
                            <span class="text">
                                신청기간 <%= dbox.getString("d_propstart") %>~<%= dbox.getString("d_propend") %><br>
                                교육기간 <%= dbox.getString("d_edustart") %>~<%= dbox.getString("d_eduend") %>
                            </span>
                        </span>
                    </a>
                </li>
<%
        }
    } else {
%>
                <li class="no-data">연관 과정 목록이 없습니다.</li>
<%
    }
%>
            </ul>
        </div>
    </div>


</div>

<%@ include file="/mobile/jsp/include/shareonsns.jsp" %>
<input type="hidden" id="oSubjText" name="subjText" value="<%= subjNm %>" />

<form id="oReviewForm" name="reviewForm">
    <input type="hidden" id="oProcess" name="process" value="" />
    <input type="hidden" id="oSubj" name="subj" value="<%= subj %>" />
    <input type="hidden" id="oNum" name="num" value="" />
    <input type="hidden" id="oPoint" name="point" value="" />
    <input type="hidden" id="oStep" name="step" value="" />
<div id="oReviewModal" class="modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <a href="#" class="modal-close">X</a>
            <div class="modal-header">
                <strong><%= box.getSession("name") %></strong>님의 평가
            </div>
            <div class="modal-star-rating">
                <div class="star">
                    <a href="#" class="icon icon-star-off"></a>
                    <a href="#" class="icon icon-star-off"></a>
                    <a href="#" class="icon icon-star-off"></a>
                    <a href="#" class="icon icon-star-off"></a>
                    <a href="#" class="icon icon-star-off"></a>
                </div>
                <div class="textarea">
                    <textarea id="oContents" name="contents" maxlength="150" style="z-index:1009;"></textarea>
                </div>
                <div class="row" id="oRegisterButton">
                    <button type="button" class="btn btn-block btn-primary btn-lg" id="oRegBtn" onclick="fnRegisterReview();">등록</button>
                </div>
                <div class="row" id="oModifyButton" style="display:none;">
                    <button type="button" class="btn btn-primary btn-lg pull-left" style="width:49%;" id="oDelBtn" onclick="fnDeleteReview();">삭제</button>
                    <button type="button" class="btn btn-primary btn-lg pull-right" style="width:49%;" id="oModBtn" onclick="fnModifyReview();">저장</button>
                </div>
            </div>
        </div>
    </div>
</div>
</form>
<%
    } catch (Exception e) {
        out.println(e.getMessage());
    }

%>
</body>
</html>