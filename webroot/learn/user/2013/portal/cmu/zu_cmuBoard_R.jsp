<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.credu.research.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.homepage.*" %>
<%@ page import="com.dunet.common.util.*" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int seq = box.getInt("seq");
    int pageNo = box.getInt("p_pageno");
    int pageSize = box.getInt("p_pagesize");

    String searchType = box.getString("searchType");
    String searchKeyword = box.getString("searchKeyword");

    String userid = box.getSession("userid");

    DataBox cmuBoard = (DataBox)request.getAttribute("cmuBoard");

    int viewCount = 0;
    String title = "";
    String cont = "";
    String regId = "";
    String regNm = "";
    String regDt = "";
    String useYn = "";
    String fileNm = "";
    String saveFileNm = "";
    String fileSize = "";

    if (cmuBoard != null) {
        title = cmuBoard.getString("d_title");
        cont = cmuBoard.getString("d_cont");
        viewCount = cmuBoard.getInt("d_view_count");
        regId = cmuBoard.getString("d_reg_id");
        regNm = cmuBoard.getString("d_reg_nm");
        regDt = cmuBoard.getString("d_reg_fmt_dt");
        useYn = cmuBoard.getString("d_use_yn");
        fileNm = cmuBoard.getString("d_file_nm");
        saveFileNm = cmuBoard.getString("d_save_file_nm");
        fileSize = cmuBoard.getString("d_file_size");
    }

    box.put("title_str", "CMU 스토리 - 새소식");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<script type="text/javascript">
    $(function() {
        $(document.body).css("font-family", "돋움,Dotum,AppleGothic,sans-serif").css("font-size", "12px");
    });

    /**
     * 목록 화면으로 이동
     */
    function fnGoToList() {
        $("#oProcess").val("list");
        $("#oCMUBoardForm").attr("action", "/servlet/controller.homepage.CMUBoardServlet");
        $("#oCMUBoardForm").submit();
    }
</script>
<!-- container-new 시작 //-->
<div id="container-new">


<form id="oCMUBoardForm" name="cmuBoardForm" method="get" action="">
    <input type="hidden" id="oProcess" name="p_process" value="" />
    <input type="hidden" id="oPageNo" name="p_pageno" value="<%= pageNo %>" />
    <input type="hidden" id="oPageSize" name="p_pagesize" value="<%= pageSize %>" />
    <input type="hidden" id="oSearchType" name="searchType" value="<%= searchType %>" />
    <input type="hidden" id="oSearchKeyword" name="searchKeyword" value="<%= searchKeyword %>" />
    <input type="hidden" id="oSeq" name="seq" value="" />

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>새소식</li>
            <li>CMU 스토리 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List" >공지사항</a></li>
                    <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list" >아카데미 이야기</a></li>
                    <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" class="active">CMU 스토리</a></li>
                    <!-- 2015-01-07 개편삭제
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                    //-->
                </ul>
            </li>
        </ul>
        <!-- 서브메인용 -->
<!-- 2015-01-07 개편삭제
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><strong><u>커뮤니티</u></strong></a></li>
            </ul>
        </div>
-->
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">CMU 스토리<!--<img src="/images/2013/online_edu/CMU_title.gif" alt="CMU스토리" />//--></h2>
        
        <div id="subCont">
<!-- 2015-01-07 개편삭제
            <ul class="tabCus">
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/2013/online_edu/community_tab_1_off.gif" alt="전체" /></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=B0"><img src="/images/2013/online_edu/community_tab_2_off.gif" alt="방송영상" /></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=G0"><img src="/images/2013/online_edu/community_tab_3_off.gif" alt="게임개발" /></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=K0"><img src="/images/2013/online_edu/community_tab_4_off.gif" alt="문화콘텐츠" /></a></li>
                <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list"><img src="/images/2013/online_edu/community_tab_6_on.gif" alt="cmu 국비지원" /></a></li>
<%
        if ( !topWarp_userid.equals("") ) {
%>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE"><img src="/images/2013/online_edu/community_tab_5_off.gif" alt="내가 작성한 글" /></a></li>
<%
        }
%>

            </ul>
-->
            <h3 class="blind">전체 현재위치</h3>

            <div class="article">
                <div class="info">
                    <p class="title"><%= title %></p>
                    <p class="date"><%= regDt %></p>
                    <!-- <p class="curriculum"></p> //-->
                </div>
                <div class="info2">
                    <!-- <p class="name"><%= regNm %></p> //-->
<%
        if ( !fileNm.equals("") ) {
%>
                    <p class="file"><a href="/servlet/controller.library.DownloadServlet?p_savefile=<%= saveFileNm %>&p_realfile=<%= fileNm %>"><%= fileNm %></a><input type="hidden" name="p_savefile" value="<%= saveFileNm %>" /></p>
<%
        }
%>
                </div>

                <div class="inner">
                    <%= cont %>
                </div>

            </div>
            <!-- //article -->

            <div class="btnArea right">
                <a href="javascript:fnGoToList();"><img src="/images/2013/btn/btn_list.gif" alt="목록" /></a>
            </div>

        </div>
        <!-- //#subCont -->
    </div>
    <!-- //#contents -->


</form>

</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
