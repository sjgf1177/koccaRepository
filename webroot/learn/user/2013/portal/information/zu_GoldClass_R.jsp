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

    String v_genre      = box.getStringDefault("p_genre","ALL");
    String v_genrenm    = "";
    String v_subTit     = "";
    if(v_genre.equals("ALL")){
        v_genrenm = "전체";
        v_subTit = "<img src=\"/images/2013/open_edu/openedu_title.gif\" alt=\"열린강좌\" />";
    }else if(v_genre.equals("G01")){
        v_genrenm = "방송영상";
        v_subTit = "<img src=\"/images/2013/open_edu/broadcast_title.gif\" alt=\"방송영상\" />";
    }
    else if(v_genre.equals("G02")){
        v_genrenm = "영화";
        v_subTit = "<img src=\"/images/2013/open_edu/movie_title.gif\" alt=\"영화\" />";
    }
    else if(v_genre.equals("G03")){
        v_genrenm = "다큐멘터리";
        v_subTit = "<img src=\"/images/2013/open_edu/document_title.gif\" alt=\"다큐멘터리\" />";
    }
    else if(v_genre.equals("G04")){
        v_genrenm = "문화예술";
        v_subTit = "<img src=\"/images/2013/open_edu/art_title.gif\" alt=\"문화예술\" />";
    }
    else if(v_genre.equals("G06")){
        v_genrenm = "게임";
        v_subTit = "<img src=\"/images/2013/open_edu/game_title.gif\" alt=\"게임\" />";
    }
    else if(v_genre.equals("G05")){
        v_genrenm = "기타";
        v_subTit = "<img src=\"/images/2013/open_edu/etc_title.gif\" alt=\"기타\" />";
    }
    String v_subnavi = "";

    int[]   v_seq       = new int[200];
    int     i           = 0;
    int     j           = 0;
    int     v_viewcnt   = 0;
    float   v_checkpoin = 0;
    boolean flag = false;

    int v_seq2          = 0;
    String v_vodimg2    = "";
    String v_lecnm2     = "";
    String v_tutornm2   = "";
    String v_lectime2   = "";
    String v_creator2   = "";
    String v_intro2     = "";
    String v_width_s2   = "";
    String v_height_s2  = "";
    int v_viewcnt2      = 0;
    float v_checkpoin2  = 0;
    String v_vodurl2    = "";
    String v_tutorimg2  = "";

    String v_creatyear  = "";
    String v_openyn = "";

    String v_useyn  = "";
    String v_img_save_file  = "";
    String v_tutor_save_file    = "";
    String v_bannerimg = "";

    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

    String v_classname1      = "";
    String v_classname2      = "";

    String s_userid   = box.getSession("userid");

    if (v_bannerimg.equals("")) {
        v_bannerimg = "/images/2012/sub/page5/castimage/sample.gif";
    } else {
        v_bannerimg = "http://edu.kocca.or.kr/upload/bulletin/"+v_bannerimg;
    }

    box.put("title_str", v_genrenm + ", 열린강좌");
%>

<%@ include file="/learn/user/2013/portal/include/top_sub.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script type="text/javascript">
$(function(){
    //강사 프로필 레이어
    var teacher = $(".movList .btn_teacher");
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
</script>
<script language="JavaScript" type="text/JavaScript">
//검색내용에 맞는 리스트 페이지로 이동
function goPreGoldClass(){
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectPreGoldClassList";
    document.form1.submit();
    }

function viewContentNew(seq) {
    document.form1.p_prePage.value   = "Main";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value   = "mainPage";
    document.form1.p_seq.value       = seq;
    document.form1.submit();
}


function goGoldClassMain(){
    document.form1.target="_self";
    document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
    document.form1.p_process.value = "mainPage";
    document.form1.submit();
}


//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->

<form name="form1" method = "post" action="">
      <input type="hidden" name="p_process"     value="" />
      <input type="hidden" name="p_seq"         value="" />
      <input type="hidden" name="p_prePage"     value="" />

            <div id="mainMenu">
                <!-- 아카데미 소개 -->
                <ul class="location">
                    <li>홈</li>
                    <li>열린 강좌</li>
                    <li>
                        <%=v_genrenm %> <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                        <ul style="z-index: 1;">
                            <!-- 활성화된 메뉴에  class="active" 삽입 -->
                            <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage" <%=v_genre.equals("ALL") ? "class=\"active\"" : "" %> >전체</a></li>
                            <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G01" <%=v_genre.equals("G01") ? "class=\"active\"" : "" %>>방송영상</a></li>
                            <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G02" <%=v_genre.equals("G02") ? "class=\"active\"" : "" %>>영화</a></li>
                            <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G03" <%=v_genre.equals("G03") ? "class=\"active\"" : "" %>>다큐멘터리</a></li>
                            <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G04" <%=v_genre.equals("G04") ? "class=\"active\"" : "" %>>문화예술</a></li>
                            <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G06" <%=v_genre.equals("G06") ? "class=\"active\"" : "" %>>게임</a></li>
                            <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G05" <%=v_genre.equals("G05") ? "class=\"active\"" : "" %>>기타</a></li>
                        </ul>
                    </li>
                </ul>

                <!-- 서브메인용 -->
                <div class="introCategory">
                    <ul>
                        <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage"><%=v_genre.equals("ALL") ? "<strong><u>전체</u></strong>" : "전체" %></a></li>
                        <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G01"><%=v_genre.equals("G01") ? "<strong><u>방송영상</u></strong>" : "방송영상" %></a></li>
                        <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G02"><%=v_genre.equals("G02") ? "<strong><u>영화</u></strong>" : "영화" %></a></li>
                        <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G03"><%=v_genre.equals("G03") ? "<strong><u>다큐멘터리</u></strong>" : "다큐멘터리" %></a></li>
                        <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G04"><%=v_genre.equals("G04") ? "<strong><u>문화예술</u></strong>" : "문화예술" %></a></li>
                        <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G06"><%=v_genre.equals("G06") ? "<strong><u>게임</u></strong>" : "게임" %></a></li>
                        <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G05"><%=v_genre.equals("G05") ? "<strong><u>기타</u></strong>" : "기타" %></a></li>
                    </ul>
                </div>
                <!-- //서브메인용 -->
            </div>

      <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <h2 class="subTit"></h2>
            <div id="subCont">
                <div class="movList">
                    <ul>
                        <li>
                            <dl>
                                <dt>
                                    <span class="movieTime"><%=dbox.getString("d_lectime") %></span></dt>
                                <dd>
                                    <a href="javascript:openGoldClass(<%=dbox.getInt("d_seq") %>, '<%=dbox.getString("d_width_s") %>','<%=dbox.getString("d_height_s") %>');"><%=dbox.getString("d_lecnm") %></a>
                                    <a href="#profile" class="btn_teacher">강사소개</a>
                                </dd>
                            </dl>
                        </li>
                    </ul>
                    <!-- 프로필 레이어 -->
                    <div id="profile" class="movLayer">
                        <a href="#" class="btn_layerClose"><img src="/images/2013/open_edu/btn_layer_close.gif" alt="닫기" /></a>
                        <p class="tit"><%= v_lecnm %> <span style="font-size: 14px;font-weight: normal;"> - <%= v_tutornm %></span></p>
                        <dl>
                            <dt><img src="http://edu.kocca.or.kr<%=v_tutorimg %>" alt="프로필사진" /></dt>
                            <dd>
                                <span class="tit_history"><img src="/images/2013/open_edu/tit_history.gif" alt="약력" /></span>
                                <ul>
                                    <li>&nbsp;&nbsp;<%= v_tutorcareer.replaceAll("\r\n","<br/>") %></li>
                                </ul>
                            </dd>
                            <dd class="last">
                                <span class="tit_history"><img src="/images/2013/open_edu/tit_major.gif" alt="주요 수상 및 저서" /></span>
                                <ul>
                                    <li>&nbsp;&nbsp;<%= v_tutorauthor.replaceAll("\r\n","<br/>") %></li>
                                </ul>
                            </dd>
                        </dl>
                    </div>
                    <!--// 프로필 레이어 -->
                </div>
            </div>
            <!-- //#subCont -->
        </div>
        <!-- //#contents -->
        <!-- 컨텐츠 영역 끝 -->

</form>
<%@ include file="/learn/user/2013/portal/include/footer.jsp"%>