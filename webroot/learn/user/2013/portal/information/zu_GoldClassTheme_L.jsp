<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_subTit = "";

    String lectureCls = box.getStringDefault("p_lecture_cls","ALL");
    String lectureClsNm = "";

    if(lectureCls.equals("ALL")){
        lectureClsNm = "전체";
        v_subTit = lectureClsNm;
        
    } else if (lectureCls.equals("OT01")) {
        lectureClsNm = "명사특강";
        v_subTit = lectureClsNm;

    } else if (lectureCls.equals("OT02")) {
        lectureClsNm = "트렌드";
        v_subTit = lectureClsNm;

    } else if (lectureCls.equals("OT03")) {
        lectureClsNm = "스킬노하우";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("OT04")) {
        lectureClsNm = "창업열전";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("OT05")) {
        lectureClsNm = "직업탐구";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("OT06")) {
        lectureClsNm = "마스터클래스";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("OT07")) {
        lectureClsNm = "콘텐츠인사이트";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("OT08")) {
        lectureClsNm = "경영/전략";
        v_subTit = lectureClsNm;
    
    } else if(lectureCls.equals("OT09")) {
        lectureClsNm = "마케팅";
        v_subTit = lectureClsNm;
    
    } else if(lectureCls.equals("OT10")) {
        lectureClsNm = "비지니스 스킬";
        v_subTit = lectureClsNm;
    }

    int i = 0;
    int j = 0;

    int totalPage = 0;
    int totalRowCount = 0;
    int pageNo = box.getInt("pageNo");
    int pageSize = box.getInt("pageSize");

 	// 열린강좌 테마별 목록
    ArrayList openClassThemeList = (ArrayList)request.getAttribute("openClassThemeList");
    DataBox openClassThemeCnt = (DataBox)request.getAttribute("openClassThemeCnt");

    String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

    String s_userid = box.getSession("userid");

    box.put("title_str", lectureClsNm + ", 열린강좌");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<style type="text/css">
    .lecname a{ width:597px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; }
</style>

<!-- 스크립트영역시작 -->
<script type="text/javascript">

    /*
    $(function() {
        //강사 프로필 레이어
        var teacher = $(".movList2 .btn_teacher");
        teacher.each(function(){
            var btn = $(this)
            ,lyr = btn.attr("href")
            layerUI({
                btns       : btn //레이어 띄우기 버튼
                ,layer     : lyr //레이어
                ,closeBtn  : ".btn_layerClose" //레이어 안쪽 닫기 버튼
                ,outCloseBtn  : ".lyrClose" //레이어 바깥쪽 닫기 버튼
                ,evt       : "click" //이벤트
                ,autoPosition  : false //자동으로 중앙 위치 맞춤 true || false
                ,evtPosition  : false //마우스 위치로 맞춤 true || false
                ,winResize : false //윈도우 크기 바뀌면 위치 재설정 true || false
                ,blurClose : false //초첨 나갈때 닫히기 true || false
                ,toggle : false //버튼을 클릭하면 켜졌다꺼졌다로 true || false
                ,fn        : function(){
                    $(".movLayer:visible").hide();
                    $(".currentview").removeClass("currentview")
                    btn.addClass("currentview")
                } //이벤트 발생시 실행될 함수
                ,fn_close    : function(){
                    $(".currentview").removeClass("currentview")
                    .find("img").attr("src",function(){
                        return $(this).attr("src").replace("_on","_off");
                    });
                } //닫힐때 실행될 함수
            });

        });

    });
    */

    // 상세화면 페이지로 이동
    function fnViewContent(seq) {
        var url = "/servlet/controller.infomation.GoldClassHomePageServlet";
        var lectureCls = $("#oLectureCls").val();
        var param = "?p_process=selectView&p_seq=" + seq + "&p_lecture_cls=" + lectureCls;

        location.href = url + param;
    }

    /**
     * 페이지 이동
     */
    function goPage(pageNum) {
        var formObj = document.openClassForm;
        formObj.method = "get";
        formObj.pageNo.value = pageNum;
        formObj.target="_self";
        formObj.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        formObj.p_process.value = "themeMainPage";
        formObj.submit();
    }

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

<!-- Form 영역 시작 -->
<form name="openClassForm" method="post" action="">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="pageNo" value="<%= pageNo %>">
    <input type="hidden" id="oLectureCls" name="p_lecture_cls" value="<%= lectureCls %>" />

	<div id="mainMenu">
	<!-- 아카데미 소개 -->
        <ul class="location">
            <li>홈</li>
            <li>열린 강좌</li>
            <li>
                <%= lectureClsNm %> <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul style="z-index: 1;">
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="javascript:fnOpenClassThemeList('ALL');" <%= lectureCls.equals("ALL") ? "class=\"active\"" : "" %> >전체</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT01');" <%= lectureCls.equals("OT01") ? "class=\"active\"" : "" %>>명사특강</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT02');" <%= lectureCls.equals("OT02") ? "class=\"active\"" : "" %>>트렌드</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT03');" <%= lectureCls.equals("OT03") ? "class=\"active\"" : "" %>>스킬노하우</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT04');" <%= lectureCls.equals("OT04") ? "class=\"active\"" : "" %>>창업열전</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT05');" <%= lectureCls.equals("OT05") ? "class=\"active\"" : "" %>>직업탐구</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT06');" <%= lectureCls.equals("OT06") ? "class=\"active\"" : "" %>>마스터클래스</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT07');" <%= lectureCls.equals("OT07") ? "class=\"active\"" : "" %>>콘텐츠인사이트</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT08');" <%= lectureCls.equals("OT08") ? "class=\"active\"" : "" %>>경영/전략</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT09');" <%= lectureCls.equals("OT09") ? "class=\"active\"" : "" %>>마케팅</a></li>
                    <li><a href="javascript:fnOpenClassThemeList('OT10');" <%= lectureCls.equals("OT10") ? "class=\"active\"" : "" %>>비지니스 스킬</a></li>
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
        <div class="introCategory">
            <ul>
                <li><a href="javascript:fnOpenClassThemeList('ALL');"><%= lectureCls.equals("ALL") ? "<strong><u>전체</u></strong>" : "전체" %> (<%= openClassThemeCnt.getString("d_all_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT01');"><%= lectureCls.equals("OT01") ? "<strong><u>명사특강</u></strong>" : "명사특강" %> (<%= openClassThemeCnt.getString("d_ot01_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT02');"><%= lectureCls.equals("OT02") ? "<strong><u>트렌드</u></strong>" : "트렌드" %></a> (<%= openClassThemeCnt.getString("d_ot02_cnt") %>)</li>
                <li><a href="javascript:fnOpenClassThemeList('OT03');"><%= lectureCls.equals("OT03") ? "<strong><u>스킬노하우</u></strong>" : "스킬노하우" %> (<%= openClassThemeCnt.getString("d_ot03_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT04');"><%= lectureCls.equals("OT04") ? "<strong><u>칭업열전</u></strong>" : "창업열전" %> (<%= openClassThemeCnt.getString("d_ot04_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT05');"><%= lectureCls.equals("OT05") ? "<strong><u>직업탐구</u></strong>" : "직업탐구" %> (<%= openClassThemeCnt.getString("d_ot05_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT06');"><%= lectureCls.equals("OT06") ? "<strong><u>마스터클래스</u></strong>" : "마스터클래스" %> (<%= openClassThemeCnt.getString("d_ot06_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT07');"><%= lectureCls.equals("OT07") ? "<strong><u>콘텐츠인사이트</u></strong>" : "콘텐츠인사이트" %> (<%= openClassThemeCnt.getString("d_ot07_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT08');"><%= lectureCls.equals("OT08") ? "<strong><u>경영/전략</u></strong>" : "경영/전략" %> (<%= openClassThemeCnt.getString("d_ot08_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT09');"><%= lectureCls.equals("OT09") ? "<strong><u>마케팅</u></strong>" : "마케팅" %> (<%= openClassThemeCnt.getString("d_ot09_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT10');"><%= lectureCls.equals("OT10") ? "<strong><u>비지니스 스킬</u></strong>" : "비지니스 스킬" %> (<%= openClassThemeCnt.getString("d_ot10_cnt") %>)</a></li>
            </ul>
        </div>
        
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">

        <h2 class="subTit"><%= v_subTit %></h2>
<%
        if ( lectureCls.equals("GC06") || lectureCls.equals("GC07")) {
%>
        <!--
        <div class="noticBox nbox1">
            콘텐츠인사이트 / 마스터클래스 안내문구입니다.
        </div>
        //-->
<%
        }
%>
        <div id="subCont">
            <div class="movList2">
<%
        if(openClassThemeList.size() != 0) {

            for(i = 0; i < openClassThemeList.size(); i++) {
                DataBox dbox = (DataBox)openClassThemeList.get(i);

                int bWidth= 597;

                totalPage = dbox.getInt("d_totalPage");
                totalRowCount = dbox.getInt("d_totalRowCount");
                if ( dbox.getString("d_new_yn").equals("Y") ) { bWidth -= 35;}
                if ( dbox.getString("d_recom_yn").equals("Y") ) { bWidth -= 35;}
                if ( dbox.getString("d_hit_yn").equals("Y") ) { bWidth -= 35;}

%>
                        <!-- 리스트 영역 시작 -->
        		        <dl style="height:80px">
		                    <dt>
                		        <a href="javascript:fnViewContent('<%= dbox.getString("d_seq") %>');"><img src="<%=StringManager.replace(dbox.getString("d_vodimg"),"\\","/") %>" width="126" height="80" alt="<%=  dbox.getString("d_lecnm") %>" /></a>
        		            </dt>
		                    <dd class="title">
                		        <div class="lecname">
        		                    <a href="javascript:fnViewContent('<%= dbox.getString("d_seq") %>');" style="padding-bottom:8px; width:<%= bWidth %>px;" title="열린강좌의 상세 화면으로 이동합니다."><%=  dbox.getString("d_lecnm") %></a>
		                        </div>

                                <span style="float:right; margin-top:-6px;">
                                    <!--<img src="/images/2013/online_edu/detail_mbile.png" style="float:right; margin-right:5px;" alt="모바일" />-->
<%
                if( dbox.getString("d_hit_yn").equals("Y") ) {
%>
                                    <img src="/images/2013/online_edu/detail_popular.png" style="float:right; margin-right:5px;" alt="인기" />
<%
                }
                if( dbox.getString("d_recom_yn").equals("Y") ) {
%>
                                    <img src="/images/2013/online_edu/detail_recommend.png" style="float:right; margin-right:5px;" alt="추천" />
<%
                }
                if( dbox.getString("d_new_yn").equals("Y") ) {
%>
                                    <img src="/images/2013/online_edu/detail_new.png" style="float:right; margin-right:5px;" alt="신규" />
<%
                }
%>
                        		</span>
		                    </dd>
        		            <dd style="margin-top:10px;"><%=dbox.getString("d_tutornm")%></dd>
                		    <dd class="txt" style="white-space:auto; overflow:hidden; text-overflow:ellipsis;">- <%=dbox.getString("d_intro") %></br>
		                    </dd>

                		</dl>
                        <!-- 리스트 영역 끝 -->

<%
            }
%>


<%
        } else {
%>
                <dl><dd>조회된 강좌가 없습니다.</dd></dl>

<%
        }
%>
            </div>
            <div class="paging">
                <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 0, pageSize, totalRowCount) %>
            </div>
        </div>
        <!-- //#subCont -->


    </div>
    <!-- //#contents -->
    <!-- 컨텐츠 영역 끝 -->

</form>

</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
