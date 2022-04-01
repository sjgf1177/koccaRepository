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
        titleTag = "복습하기 지난과정 | 나의강의실 | 한국콘텐츠아카데미";
    }

    if ( !isLogin ) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta name="format-detection" content="telephone=no"/>
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
            fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet", location.href);
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

        ArrayList reviewSubjctLessonList = (ArrayList)request.getAttribute("reviewSubjctLessonList");
        DataBox reviewSubjctInfo = (DataBox)request.getAttribute("reviewSubjctInfo");
        DataBox dbox = null;

        String pageTitle = "나의 강의실";
        String previousURL = "javascript:history.back(-1);";

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String lesson = "";
        String mobileURL = "";

        try {
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
<script src="/mobile/assets/js/json2.js"></script>
<script>

    var isLogin = eval("<%= isLogin %>");
    var isApp = eval("<%= isApp %>");

    /**
     * 강좌를 재생한다.
     */
    function fnViewReviewContents( subj, year, subjseq, lesson, mobileURL ) {
        // 콘텐츠 유형을 구별하기 위한 변수
        // mobileURL의 확장자가 '.ntc'이면 NTools로 재생, 그렇지 않은 경우는 동영상으로 재생
        var isNTC = ( mobileURL.indexOf(".ntc") > -1 ) ? true : false;

        if ( mobileURL != null && mobileURL != "" ) {

            // 콘텐츠 재생 시작
            // 시작 페이지는 현재로서는 의미가 없다.
            // 콘텐츠 재생 종료시 호출될 진도처리 URL 정보와 파라미터 정보도 같이 보낸다.
            // 아래는 NTC 뷰어로 동영상을 재생할 때 사용되는 모듈이다.
            param = "process=updateProgressEnd&subj=" + subj +"&year=" + year + "&subjseq=" + subjseq + "&lesson=" + lesson;

            if ( isNTC ) {
                mobileURL = (mobileURL.indexOf("m.edu.kocca.kr") > -1 ) ? mobileURL : "http://m.edu.kocca.kr" + mobileURL;

                var progress = {
                    "playNTC" : {
                        "url": mobileURL,
                        "page" : "1",
                        "notifyURL" : "",
                        "customInfo" : ""
                    }
                };

            } else {
                var progress = {
                    "playVideo" : {
                        "url": mobileURL,
                        "startPlaybackTime" : "0",
                        "notifyURL" : "",
                        "customInfo" : ""
                    }
                };
            }

            document.location = "newin:@" + JSON.stringify(progress);

        } else {
            alert("등록된 강좌 정보가 없습니다.");
            return;
        }
    }

</script>
</head>
<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/sub_header.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu.jsp" %>

<div class="page-header">
	<h2><%= reviewSubjctInfo.getString("d_subjnm") %></h2>
	<div class="well well-sm mt10">
		복습기간 <%= reviewSubjctInfo.getString("d_review_fmt_date") %>
	</div>
</div>
<ol class="lecture-step-list">
<%
    for ( int i = 0 ; i < reviewSubjctLessonList.size() ; i++ ) {
        dbox = (DataBox)reviewSubjctLessonList.get(i);

        lesson = dbox.getString("d_lesson");
        mobileURL = dbox.getString("d_new_mobile_url");
%>
	<li <%= dbox.getString("d_study_yn").equals("Y") ? "class=\"visited\"" : ""%>>
		<a href="javascript:fnViewReviewContents('<%= subj %>', '<%= year %>', '<%= subjseq %>', '<%= lesson %>', '<%= mobileURL %>');">
			<strong><%= Integer.parseInt( lesson ) %>차시</strong>
			<%= dbox.getString("d_sdesc") %>
			<i class="icon-play"><i></i></i>
		</a>
	</li>
<%
    }

    } catch (Exception e) {
        System.out.println("Exception occured : " + e.getMessage());
    }
%>

</ol>
</body>
</html>
<%
    }
%>