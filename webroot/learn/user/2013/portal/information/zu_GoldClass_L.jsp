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

    } else if (lectureCls.equals("GC01")) {
        lectureClsNm = "방송영상";
        v_subTit = lectureClsNm;

    } else if (lectureCls.equals("GC02")) {
        lectureClsNm = "게임";
        v_subTit = lectureClsNm;

    } else if (lectureCls.equals("GC03")) {
        lectureClsNm = "만화애니캐릭터";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC04")) {
        lectureClsNm = "음악공연";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC05")) {
        lectureClsNm = "기타";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC06")) {
        lectureClsNm = "콘텐츠 인사이트";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC07")) {
        lectureClsNm = "마스터 클래스";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC08")) {
        lectureClsNm = "휴넷열린강좌";
        v_subTit = lectureClsNm;
    }
    
/* 2015-01-13 개편 수정
    if(lectureCls.equals("ALL")){
        lectureClsNm = "전체";
        v_subTit = "<img src=\"/images/open_class/title_total.png\" alt=\"전체\" />";

    } else if (lectureCls.equals("GC01")) {
        lectureClsNm = "방송영상";
        v_subTit = "<img src=\"/images/open_class/title_broad.png\" alt=\"방송영상\" />";

    } else if (lectureCls.equals("GC02")) {
        lectureClsNm = "게임";
        v_subTit = "<img src=\"/images/open_class/title_game.png\" alt=\"게임\" />";

    } else if (lectureCls.equals("GC03")) {
        lectureClsNm = "만애캐";
        v_subTit = "<img src=\"/images/open_class/title_ani.png\" alt=\"만화애니캐릭터\" />";

    } else if(lectureCls.equals("GC04")) {
        lectureClsNm = "음악공연";
        v_subTit = "<img src=\"/images/open_class/title_music.png\" alt=\"음악/공연\" />";

    } else if(lectureCls.equals("GC05")) {
        lectureClsNm = "기타";
        v_subTit = "<img src=\"/images/open_class/title_etc.png\" alt=\"기타\" />";

    } else if(lectureCls.equals("GC06")) {
        lectureClsNm = "콘텐츠 인사이트";
        v_subTit = "<img src=\"/images/open_class/title_con.png\" alt=\"콘텐츠 인사이트\" />";

    } else if(lectureCls.equals("GC07")) {
        lectureClsNm = "마스터 클래스";
        v_subTit = "<img src=\"/images/open_class/title_master.png\" alt=\"마스터 클래스\" />";

    } else if(lectureCls.equals("GC08")) {
        lectureClsNm = "휴넷열린강좌";
        v_subTit = "<img src=\"/images/open_class/title_coalition_hunet.png\" alt=\"휴넷열린강좌\" />";
    }

*/    
    int i = 0;
    int j = 0;

    int totalPage = 0;
    int totalRowCount = 0;
    int pageNo = box.getInt("pageNo");
    int pageSize = box.getInt("pageSize");

    ArrayList openClassList = (ArrayList)request.getAttribute("openClassList");
    DataBox openClassCnt = (DataBox)request.getAttribute("openClassCnt");

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
        formObj.p_process.value = "mainPage";
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
                    <li><a href="javascript:fnOpenClassList('ALL')" <%= lectureCls.equals("ALL") ? "class=\"active\"" : "" %> >전체</a></li>
                    <li><a href="javascript:fnOpenClassList('GC01);" <%= lectureCls.equals("GC01") ? "class=\"active\"" : "" %>>방송영상</a></li>
                    <li><a href="javascript:fnOpenClassList('GC02);" <%= lectureCls.equals("GC02") ? "class=\"active\"" : "" %>>게임</a></li>
                    <li><a href="javascript:fnOpenClassList('GC03);" <%= lectureCls.equals("GC03") ? "class=\"active\"" : "" %>>만화애니캐릭터</a></li>
                    <li><a href="javascript:fnOpenClassList('GC04);" <%= lectureCls.equals("GC04") ? "class=\"active\"" : "" %>>음악/공연</a></li>
                    <li><a href="javascript:fnOpenClassList('GC05);" <%= lectureCls.equals("GC05") ? "class=\"active\"" : "" %>>기타</a></li>
                    <li><a href="javascript:fnOpenClassList('GC06);" <%= lectureCls.equals("GC06") ? "class=\"active\"" : "" %>>콘텐츠인사이트</a></li>
                    <li><a href="javascript:fnOpenClassList('GC07);" <%= lectureCls.equals("GC07") ? "class=\"active\"" : "" %>>마스터클래스</a></li>
                    <li><a href="javascript:fnOpenClassList('GC08);" <%= lectureCls.equals("GC08") ? "class=\"active\"" : "" %>>휴넷열린강좌</a></li>
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
        <div class="introCategory">
            <ul>
                <li><a href="javascript:fnOpenClassList('ALL');"><%= lectureCls.equals("ALL") ? "<strong><u>전체</u></strong>" : "전체" %> (<%= openClassCnt.getString("d_all_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassList('GC01');"><%= lectureCls.equals("GC01") ? "<strong><u>방송영상</u></strong>" : "방송영상" %> (<%= openClassCnt.getString("d_gc01_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassList('GC02');"><%= lectureCls.equals("GC02") ? "<strong><u>게임</u></strong>" : "게임" %></a> (<%= openClassCnt.getString("d_gc02_cnt") %>)</li>
                <li><a href="javascript:fnOpenClassList('GC03');"><%= lectureCls.equals("GC03") ? "<strong><u>만화애니캐릭터</u></strong>" : "만화애니캐릭터" %> (<%= openClassCnt.getString("d_gc03_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassList('GC04');"><%= lectureCls.equals("GC04") ? "<strong><u>음악/공연</u></strong>" : "음악/공연" %> (<%= openClassCnt.getString("d_gc04_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassList('GC05');"><%= lectureCls.equals("GC05") ? "<strong><u>기타</u></strong>" : "기타" %> (<%= openClassCnt.getString("d_gc05_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassList('GC06');"><%= lectureCls.equals("GC06") ? "<strong><u>콘텐츠인사이트</u></strong>" : "콘텐츠인사이트" %> (<%= openClassCnt.getString("d_gc06_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassList('GC07');"><%= lectureCls.equals("GC07") ? "<strong><u>마스터클래스</u></strong>" : "마스터클래스" %> (<%= openClassCnt.getString("d_gc07_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassList('GC08');"><%= lectureCls.equals("GC08") ? "<strong><u>휴넷열린강좌</u></strong>" : "휴넷열린강좌" %> (<%= openClassCnt.getString("d_gc08_cnt") %>)</a></li>
                
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
        if(openClassList.size() != 0) {

            for(i = 0; i < openClassList.size(); i++) {
                DataBox dbox = (DataBox)openClassList.get(i);

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
