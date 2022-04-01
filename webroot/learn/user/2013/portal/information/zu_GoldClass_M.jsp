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

    String v_genre = box.getStringDefault("p_genre","ALL");
    String v_genrenm = "";
    String v_subTit = "";

    String lectureCls = box.getStringDefault("p_lecture_cls","ALL");
    String lectureClsNm = "";

    String dirFlag = box.getStringDefault("p_dir_flag", "N");
    String seq = box.getStringDefault("p_seq", "");

    /*
    if(v_genre.equals("ALL")){
        v_genrenm = "전체";
        v_subTit = "<img src=\"/images/2013/open_edu/openedu_title.gif\" alt=\"열린강좌\" />";

    } else if (v_genre.equals("G01")) {
        v_genrenm = "방송영상";
        v_subTit = "<img src=\"/images/2013/open_edu/broadcast_title.gif\" alt=\"방송영상\" />";

    } else if (v_genre.equals("G02")) {
        v_genrenm = "영화";
        v_subTit = "<img src=\"/images/2013/open_edu/movie_title.gif\" alt=\"영화\" />";

    } else if (v_genre.equals("G03")) {
        v_genrenm = "다큐멘터리";
        v_subTit = "<img src=\"/images/2013/open_edu/document_title.gif\" alt=\"다큐멘터리\" />";

    } else if(v_genre.equals("G04")) {
        v_genrenm = "문화예술";
        v_subTit = "<img src=\"/images/2013/open_edu/art_title.gif\" alt=\"문화예술\" />";

    } else if(v_genre.equals("G06")) {
        v_genrenm = "게임";
        v_subTit = "<img src=\"/images/2013/open_edu/game_title.gif\" alt=\"게임\" />";

    } else if(v_genre.equals("G05")) {
        v_genrenm = "기타";
        v_subTit = "<img src=\"/images/2013/open_edu/etc_title.gif\" alt=\"기타\" />";
    }
    */


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

    String v_subnavi = "";

    int[] v_seq = null;
    int i = 0;
    int j = 0;
    int v_viewcnt = 0;
    float v_checkpoin = 0;
    boolean flag = false;

    int totalPage = 0;
    int	totalRowCount = 0;
    int pageNo = box.getInt("pageNo"); 
    int pageSize = box.getInt("pageSize");

    String[] v_vodimg = new String[300];
    String[] v_lecnm = new String[300];
    String[] v_tutornm = new String[300];
    String[] v_lectime = new String[300];
    String[] v_creator = new String[300];
    String[] v_intro = new String[300];
    String[] v_width_s = new String[300];
    String[] v_height_s = new String[300];
    String[] v_vodurl = new String[300];
    String[] v_tutorimg = new String[300];
    String[] v_tutorcareer = new String[300];
    String[] v_tutorauthor = new String[300];
    String[] v_contents = new String[300];

    String v_creatyear = "";
    String v_openyn = "";

    String v_useyn = "";
    String v_img_save_file = "";
    String v_tutor_save_file = "";
    String v_bannerimg = "";

    ArrayList openClassList = (ArrayList)request.getAttribute("openClassList");
    DataBox openClassCnt = (DataBox)request.getAttribute("openClassCnt");

    String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

    String s_userid = box.getSession("userid");

    if (v_bannerimg.equals("")) {
        v_bannerimg = "/images/2012/sub/page5/castimage/sample.gif";
    } else {
        v_bannerimg = "http://edu.kocca.or.kr/upload/bulletin/"+v_bannerimg;
    }

    box.put("title_str", lectureClsNm + ", 열린강좌");
%>

<%@ include file="/learn/user/2013/portal/include/top_sub.jsp"%>
<!-- Common 영역종료 -->

<style type="text/css">
    /* * {margin:0; padding:0;} */
    /* img {border:0; vertical-align:top;} */

    #divPopup       {position: fixed; left: 0px; top: 0px; width: 100%; height: 100%; margin:0; auto; vertical-align:middle; text-align: center; z-index:5003; display: none; }
    #vodArea        {position: absolute; width:920px; height:540px; border-style:solid; border:6px solid #fff; background-color:#efefef; z-index:5004; display: none; }
    .closeBtn       {position: absolute; width:25px; height:25px; color:white; text-align:center; padding-top:10px; cursor:pointer; z-index:5005; display: none; }
    .divPopupMask   {position: fixed; left:0px; top:0px; width:100%; height:100%; background-image: url(/images/common/mask.png); z-index:5002; display: none; }
    /* .divPopupMask   {position: fixed; top:0px; left:0px; width:100%; height:100%; background-image: url(/images/common/mask_transparency.png); z-index:5002; display: none; } */

</style>

<!-- 스크립트영역시작 -->
<script type="text/javascript">

    $(function() {
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

<%
    if ( dirFlag.equals("Y") ) {
%>
        fnViewOpenClassDirect(<%= seq %>);
<%
    }
%>

        $(window).bind("resize", function () {
            var docHeight = $(document).height();
            var docWidth = $(document).width();
            var vodWidth = $("#vodArea").width();

            if ( $(".divPopupMask").css("display") == "block") {

                var vodAreaPosX = 0;
                var vodAreaPosY = 100;
                var closeBtnPosX = 0;
                var closeBtnPosY = vodAreaPosY - 28;

                vodAreaPosX = (docWidth / 2) - (vodWidth / 2) - 40;
                closeBtnPosX = vodAreaPosX + vodWidth;

                $("#vodArea").css( { "left": vodAreaPosX } );
                $(".divPopupMask").css( {"width": docWidth, "height:": docHeight} );
                $(".closeBtn").css( {"left": closeBtnPosX, "top": closeBtnPosY } );
            }
        });

    });


    //검색내용에 맞는 리스트 페이지로 이동
    function goPreGoldClass(){
        var formObj = document.openClassForm;
        formObj.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        formObj.target="_self";
        formObj.p_process.value = "selectPreopenClassList";
        formObj.submit();
    }

    //상세화면 페이지로 이동
    function viewContent(seq) {
        var formObj = document.openClassForm;
        formObj.p_prePage.value = "Main";
        formObj.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        formObj.target="_self";
        formObj.p_process.value = "selectView";
        formObj.p_seq.value = seq;
        formObj.submit();
    }

    /**
     * 열린강좌 보기
     * @param seq 열린강좌 일련번호
     * @param vodPath 열린강좌 실제 동영상 경로
     * @param width 열린강좌 동영상 넓이
     * @param height 열린강좌 동영상 높이
     * @param lectureType 강좌 유형 (P: 팝업(값이 없으면 popup), E: 임베드)
     */
    function fnViewOpenClass(seq, vodPath, width, height, lectureType, vodUrl) {
        lectureType = (lectureType == "") ? "P" : lectureType;

        if ( lectureType == "P" ) {
            fnViewOpenClassByPopup(seq, width, height);
        } else {

            var docHeight = $(document).height();
            var docWidth = $(document).width();

            width = Number(width)

            var vodAreaPosX = 0;
            var vodAreaPosY = 100;
            var closeBtnPosX = 0;
            var closeBtnPosY = vodAreaPosY - 28;

            vodAreaPosX = (docWidth / 2) - (width / 2) - 40;
            closeBtnPosX = vodAreaPosX + width;

            $(".divPopupMask").css( { "width": docWidth, "height": docHeight } );
            $("#vodArea").css( { "width": width, "height": height, "left": vodAreaPosX, "top": vodAreaPosY } );
            $(".closeBtn").css( {"left": closeBtnPosX, "top": closeBtnPosY } );

            $(".divPopupMask, #divPopup, #vodArea, .closeBtn").show();
            // $("#divPopup").css( {"top":0, "left":40 } );

            $.get(
                vodUrl,
                function( data ) {
                    $("#oHiddenFlashArea").html( data.substr(data.indexOf("<body"), data.lastIndexOf("</body>") ) );

                    fnLoadSWF(vodPath, width, height, true, "vodURL=" + $("#oFlashVar").val());

                    fnUpdateViewCount(seq);
                }
            );
        }
    }

    function returnFLV(str){
        returnVal = document.getElementById("oFlashVar").value;
        return returnVal;
    }

    /**
     * 임베드 유형의 열린강좌 열기
     */
    function fnLoadSWF(file, width, height, trans, flashVars){
        var tagObj = "";
        tagObj += "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='" + width + "' height='" + height + "' id='objOpenClass' align='middle'>\n";
        tagObj += "<param name='allowScriptAccess' value='always' />\n";
        tagObj += "<param name='movie' value='" + file + "' />\n";
        tagObj += "<param name='menu' value='false' />\n";
        tagObj += "<param name='flashvars' value='" + flashVars + "'>\n";
        if (trans == true){
            tagObj += "<param name='wmode' value='window' />\n";
        }
        tagObj += "<embed src='" + file + "' quality='high' bgcolor='#ffffff' width='" + width + "' height='" + height + "' name='embOpenClass' align='middle' allowScriptAccess='always' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />\n";
        tagObj += "</object>";

        $("#vodArea").html( tagObj );

    }

    /**
     * 임베드 유형의 열린강좌 닫기
     */
    function fnCloseViewWin() {
        $("#vodArea").html( "" );
        $(".divPopupMask, #divPopup, #vodArea, .closeBtn").hide();
    }

    /**
     * 임베드 유형의 열린강좌 조회수 갱신 (ajax)
     */
    function fnUpdateViewCount(seq) {
        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.infomation.GoldClassHomePageServlet"
            ,   dataType : "text"
            ,   data : "p_process=updateViewCount&seq=" + seq
            // ,   success : fnOnSuccess
            // ,   complete : fnOnComplete
            // ,   error :  fnOnError
        });
    }

    /**
     * 외부 링크를 타고 들어온 경우 해당 열린 강좌 정보를 조회하여 바로 보여줌.
     */
    function fnViewOpenClassDirect(seq) {

        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.infomation.GoldClassHomePageServlet"
            ,   dataType : "xml"
            ,   data : "p_process=getOpenClassInfo&seq=" + seq
            ,   success : function (xml) {
                    var xmlDoc = $(xml).find("ROOT");
                    var vodPath = $(xmlDoc).find("d_vod_path").text();
                    var vodUrl = $(xmlDoc).find("d_vodurl").text();
                    var width = $(xmlDoc).find("d_width_s").text();
                    var height = $(xmlDoc).find("d_height_s").text();
                    var lectureType = $(xmlDoc).find("d_lecture_type").text();

                    fnViewOpenClass(seq, vodPath, width, height, lectureType, vodUrl);

                }
            // ,   complete : fnOnComplete
            ,   error :  fnOnError
        });
    }

    /*
    function fnOnSuccess(data) {
    }
    function fnOnComplete(data) {
    }
    */
    function fnOnError(data) {
        alert("잘못된 접근입니다. 운영자에게 문의하세요.");
        location.href = "/";
        // alert("fnOnError : " + data);
    }


    /**
     * 팝업 유형의 열린강좌 보기.
     * 기존 방식을 이용하므로 별도의 조회수 갱신 함수를 호출할 필요가 없음.
     * @param seq 열린강좌 일련번호
     * @param w 콘텐츠 넓이
     * @param h 콘텐츠 높이
     */
    function fnViewOpenClassByPopup(seq, w, h) {
        if (w.length > 0) {
            window.open("", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");

        } else {
            window.open("", "ViewVod", "top=0, left=0, width=1008, height=570, status=no, resizable=no");
        }

        var formObj = document.openClassForm;
        formObj.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        formObj.p_process.value = "popUpVod";
        formObj.p_seq.value = seq;
        formObj.target = "ViewVod";
        formObj.submit();
        formObj.target = "_self";
    }


    /**
     * 페이지 이동
     */
    function goPage(pageNum) {
        var formObj = document.openClassForm;
        formObj.pageNo.value = pageNum;
        formObj.target="_self";
        formObj.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        formObj.p_process.value = "mainPage";
        formObj.submit();
    }

    /* 현재 미사용
    function goReply(seq){

        window.open("about:blank", "replyList", "top=0, left=0, width=637, height=530, status=no, resizable=no");

        document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.p_process.value = "insertReplyPage";
        document.form1.p_seq.value = seq;
        document.form1.target="replyList";
        document.form1.submit();

        document.form1.target="_self";
    }
    */

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->

<form name="openClassForm" method="post" action="">
    <input type="hidden" name="p_process"       value="" />
    <input type="hidden" name="p_seq"           value="" />
    <input type="hidden" name="p_prePage"       value="" />
    <input type="hidden" name="p_genre"         value="" />
    <input type="hidden" name="p_lecture_cls"   value="<%= lectureCls %>" />
    <input type="hidden" name="pageNo"          value="<%= pageNo %>">

    <div id="mainMenu">
        <!-- 아카데미 소개 -->
        <ul class="location">
            <li>홈</li>
            <li>열린 강좌</li>
            <li> 
                <%= lectureClsNm %> <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul style="z-index: 1;">
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="javascript:fnOpenClassList('ALL');" <%= lectureCls.equals("ALL") ? "class=\"active\"" : "" %> >전체</a></li>
                    <li><a href="javascript:fnOpenClassList('GC01');" <%= lectureCls.equals("GC01") ? "class=\"active\"" : "" %>>방송영상</a></li>
                    <li><a href="javascript:fnOpenClassList('GC02');" <%= lectureCls.equals("GC02") ? "class=\"active\"" : "" %>>게임</a></li>
                    <li><a href="javascript:fnOpenClassList('GC03');" <%= lectureCls.equals("GC03") ? "class=\"active\"" : "" %>>만화애니캐릭터</a></li>
                    <li><a href="javascript:fnOpenClassList('GC04');" <%= lectureCls.equals("GC04") ? "class=\"active\"" : "" %>>음악/공연</a></li>
                    <li><a href="javascript:fnOpenClassList('GC05');" <%= lectureCls.equals("GC05") ? "class=\"active\"" : "" %>>기타</a></li>
                    <li><a href="javascript:fnOpenClassList('GC06');" <%= lectureCls.equals("GC06") ? "class=\"active\"" : "" %>>콘텐츠인사이트</a></li>
                    <li><a href="javascript:fnOpenClassList('GC07');" <%= lectureCls.equals("GC07") ? "class=\"active\"" : "" %>>마스터클래스</a></li>
                    <li><a href="javascript:fnOpenClassList('GC08');" <%= lectureCls.equals("GC08") ? "class=\"active\"" : "" %>>휴넷열린강좌</a></li>
                </ul>
            </li>
            <!--
            <li> 
                <%=v_genrenm %> <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul style="z-index: 1;">
                    <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage" <%= v_genre.equals("ALL") ? "class=\"active\"" : "" %> >전체</a></li>
                    <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G01" <%= v_genre.equals("G01") ? "class=\"active\"" : "" %>>방송영상</a></li>
                    <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G02" <%= v_genre.equals("G02") ? "class=\"active\"" : "" %>>영화</a></li>
                    <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G03" <%= v_genre.equals("G03") ? "class=\"active\"" : "" %>>다큐멘터리</a></li>
                    <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G04" <%= v_genre.equals("G04") ? "class=\"active\"" : "" %>>문화예술</a></li>
                    <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G06" <%= v_genre.equals("G06") ? "class=\"active\"" : "" %>>게임</a></li>
                    <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G05" <%= v_genre.equals("G05") ? "class=\"active\"" : "" %>>기타</a></li>
                </ul>
            </li>
            //-->
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
        <!--
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage"><%= v_genre.equals("ALL") ? "<strong><u>전체</u></strong>" : "전체" %></a></li>
                <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G01"><%= v_genre.equals("G01") ? "<strong><u>방송영상</u></strong>" : "방송영상" %></a></li>
                <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G02"><%= v_genre.equals("G02") ? "<strong><u>영화</u></strong>" : "영화" %></a></li>
                <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G03"><%= v_genre.equals("G03") ? "<strong><u>다큐멘터리</u></strong>" : "다큐멘터리" %></a></li>
                <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G04"><%= v_genre.equals("G04") ? "<strong><u>문화예술</u></strong>" : "문화예술" %></a></li>
                <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G06"><%= v_genre.equals("G06") ? "<strong><u>게임</u></strong>" : "게임" %></a></li>
                <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G05"><%= v_genre.equals("G05") ? "<strong><u>기타</u></strong>" : "기타" %></a></li>
            </ul>
        </div>
        //-->
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
            <div class="movList">
<%
        if(openClassList.size() != 0) {
            v_seq = new int[ openClassList.size() ];
            for(i = 0; i < openClassList.size(); i++) {
                DataBox dbox = (DataBox)openClassList.get(i);

                v_seq[i] = dbox.getInt("d_seq");
                v_vodimg[i] = dbox.getString("d_vodimg");
                v_lecnm[i] = dbox.getString("d_lecnm");
                v_tutornm[i] = dbox.getString("d_tutornm");
                v_lectime[i] = dbox.getString("d_lectime");
                v_creator[i] = dbox.getString("d_creator");
                v_intro[i] = dbox.getString("d_intro");
                v_width_s[i] = dbox.getString("d_width_s");
                v_height_s[i] = dbox.getString("d_height_s");
                v_vodurl[i] = dbox.getString("d_vodurl");
                v_tutorimg[i] = ( dbox.getString("d_tutorimg").equals("") ) ? "/images/common/no_profile_img.png" : StringManager.replace(dbox.getString("d_tutorimg"),"\\","/");
                v_tutorcareer[i] = dbox.getString("d_tutorcareer");
                v_tutorauthor[i] = dbox.getString("d_tutorauthor");
                v_contents[i] = dbox.getString("d_contents");

                totalPage = dbox.getInt("d_totalPage");
                totalRowCount = dbox.getInt("d_totalRowCount");

                if((i+1)%3 == 1) {
%>
                <ul>
<%
                }
%>
                    <li>
                        <dl>
                            <dt><a href="javascript:fnViewOpenClass('<%= dbox.getString("d_seq") %>', '<%= dbox.getString("d_vod_path") %>', '<%=dbox.getString("d_width_s") %>','<%=dbox.getString("d_height_s") %>', '<%= dbox.getString("d_lecture_type") %>', '<%= dbox.getString("d_vodurl") %>');"><img src="<%=StringManager.replace(dbox.getString("d_vodimg"),"\\","/") %>" alt="" /></a>
                            <span class="movieTime"><%=dbox.getString("d_lectime") %></span></dt>
                            <dd>
<%
                String newYnImgStr = "";
                if ( dbox.getString("d_new_yn").equals("Y") ) {
                    newYnImgStr = "<img src=\"/images/common/open_new2.gif\" alt=\"\" style='vertical-align:middle;' />";
                }
%>
                                <a href="javascript:fnViewOpenClass('<%= dbox.getString("d_seq") %>', '<%= dbox.getString("d_vod_path") %>', '<%=dbox.getString("d_width_s") %>','<%=dbox.getString("d_height_s") %>', '<%= dbox.getString("d_lecture_type") %>', '<%= dbox.getString("d_vodurl") %>');"  style="padding-bottom:8px;"><%= newYnImgStr + " " + dbox.getString("d_lecnm") %></a>
                                <a href="#profile_<%=dbox.getInt("d_seq") %>" class="btn_teacher">강사소개</a>
                            </dd>
                        </dl>
                    </li>
<%
                if((i+1) % 3 == 0 || (i+1) == openClassList.size()) {
%>
                </ul>
<%
                    if(openClassList.size() < 3) {
                        j = 1;
                    } else {
                        j = (i-2);
                    }
                    for(; j < (i+1); j++){
%>
                <!-- 프로필 레이어 -->
                <div id="profile_<%= v_seq[j] %>" class="movLayer">
                    <a href="#" class="btn_layerClose"><img src="/images/2013/open_edu/btn_layer_close.gif" alt="닫기" /></a>
                    <p class="tit"><%= v_lecnm[j] %> <span style="font-size: 14px;font-weight: normal;"> - <%= v_tutornm[j] %></span></p>
                    <dl>
                        <dt><img src="<%= v_tutorimg[j] %>" alt="프로필사진" />
<!--                                        <a href="javascript:fnViewOpenClassByPopup(<%=v_seq[j] %>, '<%=v_width_s[j] %>','<%=v_height_s[j] %>')"><img src="/images/2013/open_edu/btn_play.gif" alt="강의보기" /></a>-->
                        </dt>
                        <dd>
                            <span class="tit_history"><img src="/images/2013/open_edu/tit_history.gif" alt="약력" /></span>
                            <ul>
                                <li>&nbsp;&nbsp;<%=v_tutorcareer[j].replaceAll("\r\n","<br/>") %></li>
                            </ul>
                        </dd>
                        <dd class="last">
                            <span class="tit_history"><img src="/images/2013/open_edu/tit_major.gif" alt="주요 수상 및 저서" /></span>
                            <ul>
                                <li>&nbsp;&nbsp;<%=v_tutorauthor[j].replaceAll("\r\n","<br/>") %></li>
                            </ul>
                        </dd>
                    </dl>
                </div>
                <!--// 프로필 레이어 -->
<%
                    }
                }
            }
%>
            <div class="paging pgSpace">
                <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 0, pageSize, totalRowCount) %>
            </div>

<%
        } else {
%>
                <ul>
                    <li>등록된 강좌가 없습니다.</li>
                </ul>
<%
        }
%>

            </div>
        </div>
        <!-- //#subCont -->


    </div>
    <!-- //#contents -->
    <!-- 컨텐츠 영역 끝 -->

</form>

    <div class="divPopupMask"></div>
    <div id="divPopup">
        <div id="vodArea"></div>
        <div class="closeBtn"><img src="/images/common/close.png" id="" onclick="fnCloseViewWin();" /></div>
    </div>
    <div id="oHiddenFlashArea" style="display:none;"></div>


<%@ include file="/learn/user/2013/portal/include/footer.jsp"%>
