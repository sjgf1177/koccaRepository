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

    DataBox dbox = (DataBox)request.getAttribute("selectOffExpert");

    DataBox openClassCnt = (DataBox)request.getAttribute("openClassCnt"); // 각강좌수

    ArrayList nextSubjList = (ArrayList)request.getAttribute("nextSubjList"); // 다음 강좌
    ArrayList<DataBox> reviewList = (ArrayList<DataBox>)request.getAttribute("reviewList"); // 강좌 후기

    if (box == null) box = RequestManager.getBox(request);

    String v_genre = box.getStringDefault("p_genre","ALL");
    String v_genrenm = "";
    String v_subTit = "";

    String lectureCls = box.getStringDefault("p_lecture_cls","ALL");
    String gubun = lectureCls.substring(0,2);
    String lectureClsNm = "";

    String dirFlag = box.getStringDefault("p_dir_flag", "N");
    String seq = box.getStringDefault("p_seq", "");
    
    String loginUserId = box.getSession("userid");
    
    boolean isMyReviewReg = false;
    int totalRowCount = 0;
    int totalPage = 0;
    int pageNo = ( box.getInt("p_pageno") == 0 ) ? 1 : box.getInt("p_pageno");
    int pageSize = ( box.getInt("p_pagesize") == 0 ) ? 5 : box.getInt("p_pagesize");


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
    }  else if (lectureCls.equals("OT01")) {
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


    box.put("title_str",  dbox.getString("d_lecnm") + ", " + lectureClsNm + ", 열린강좌" );

    box.put("og_title", dbox.getString("d_lecnm"));
    box.put("og_url", "http://edu.kocca.or.kr/servlet/controller.infomation.GoldClassHomePageServlet?p_process=" + box.get("p_process") + "&p_seq=" + box.get("p_seq") + "&p_lecture_cls=" + lectureCls);
    box.put("og_description", dbox.getString("d_intro"));
    box.put("og_image", "http://edu.kocca.or.kr" + dbox.getString("d_vodimg").replaceAll("\\\\","/") );

%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<style type="text/css">
    /* * {margin:0; padding:0;} */
    /* img {border:0; vertical-align:top;} */

    #divPopup       {position: fixed; left: 0px; top: 0px; width: 100%; height: 100%; margin:0; auto; vertical-align:middle; text-align: center; z-index:5003; display: none; }
    #vodArea        {position: absolute; width:920px; height:540px; border-style:solid; border:6px solid #fff; background-color:#efefef; z-index:5004; display: none; }
    .vodWinCloseBtn {position: absolute; width:25px; height:25px; color:white; text-align:center; padding-top:10px; cursor:pointer; z-index:5005; display: none; }
    .divPopupMask   {position: fixed; left:0px; top:0px; width:100%; height:100%; background-image: url(/images/common/mask.png); z-index:5002; display: none; }
    .lecname a{ width:600px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; }
    /* .divPopupMask   {position: fixed; top:0px; left:0px; width:100%; height:100%; background-image: url(/images/common/mask_transparency.png); z-index:5002; display: none; } */

</style>

<script language="JavaScript">

  $(function() {

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
                $(".vodWinCloseBtn").css( {"left": closeBtnPosX, "top": closeBtnPosY } );
            }
        });

        $("#oAnchorShareSns").on( {
            "click": function(e) {
                e.preventDefault();
                e.stopPropagation();
                var windowW = $(window).width();

                if ($("#oshareSns").is(":hidden")) {
                    $("#oshareSns").css("top", $("#oBtnList").position().top + 44);
                    $("#oshareSns").css("left", windowW / 2 + 366);
                    $("#oshareSns").show();
                } else {
                    $("#oshareSns").hide();
                }
            }
        });

        $(document).click(function(){
            $("#oshareSns").hide();
        });

        $("#oAnchorShareSns").click(function(e){
            e.stopPropagation();
        });

        $(window).resize(function () {
            var windowW = $(window).width();

            $("#oshareSns").css("left", windowW / 2 + 366);
        });

        $(".play_movie img").mouseover( function () {
            $(".play_movie img").attr("src", "/images/2013/btn/btn_playb.png");
        });
        $(".play_movie img").mouseout( function () {
            $(".play_movie img").attr("src", "/images/2013/btn/btn_playw.png");
        });


<%
    if ( dirFlag.equals("Y") ) {
%>
        fnViewOpenClassDirect(<%= seq %>);
<%
    }
%>
    });

    //과정 내용보기
    function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq){
        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "?p_process=SubjectPreviewPage&p_subj=" + subj + "&p_year=" + year + "&p_subjseq=" + subjseq;

        location.href = url + param;
    }

    // 새 상세화면 페이지로 이동
    function fnViewContent(seq) {
        var formObj = document.openClassForm;
        formObj.method = "get";
        formObj.p_prePage.value = "Main";
        formObj.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        formObj.target="_self";
        formObj.p_process.value = "selectView";
        formObj.p_seq.value = seq;
        formObj.seq.value = seq;
        formObj.submit();
    }


    /**
     * SNS 공유하기
     */
    function fnShareOnSNS( snsName ) {
        var url = location.href;

        if (url.indexOf("?") == -1 ) {
            url += "?p_process=<%=box.get("p_process")%>&year=<%=box.get("p_year")%>&subj=<%=box.get("seq")%>&p_seq=<%=box.get("p_seq")%>&p_lecture_cls=<%=box.getString("p_lecture_cls")%>";
          //url += "?p_process=<%=box.get("p_process")%>?year=<%=box.get("p_year")%>&subj=<%=box.get("p_subj")%>&subjseq=<%=box.get("p_seq")%>";
        }

        fnSendSns( snsName, url, "<%= dbox.getString("d_lecnm") %>");

        $("#oshareSns").hide();

    }

    /**
     * 과정(정규과정/열린강좌) 찜하기
     *20161006
     */
    var isLogin = "<%= box.getSession("userid") %>";

    function fnRegSubjFavor( classType, subj, jobType ) {
        if ( <%= box.getSession("userid").length() == 0 %>) {
            var frmURL = location.href;
            frmURL = frmURL.replace("http://", "");
            var idx = frmURL.indexOf("/");

            frmURL = frmURL.substr(idx, frmURL.length);

            alert("로그인 후  이용해주세요.");
            location.href = "/learn/user/2013/portal/homepage/zu_MainLogin.jsp?p_frmURL=" + encodeURIComponent( frmURL );

            return;
        } else {

            var param = "classType=" + classType + "&subj=" + subj + "&jobType=" + jobType;
            var successMsg = "";

            $.ajax({
                    type : "get"
                ,   url : "/servlet/controller.mobile.subj.SubjFavorServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {

                        if ( data.resultCnt > 0) {

                            if (jobType == "register" ) {
                                $("#oBtnList > a").eq(0).attr("onclick", "javascript:fnRegSubjFavor( '02', '" + subj + "', 'cancel' );").attr("title", "이 강좌를 찜한 강좌 목록에서 삭제합니다.");// javascript 함수 변경
                                $("#oBtnFavor").attr("src", "/images/2013/online_edu/btn_class_minex.gif");    // 버튼 이미지 변경

                                successMsg = "찜한 강좌에 추가되었습니다.";
                                $("input[name=p_favoryn]").val("Y");

                            } else {
                                $("#oBtnList > a").eq(0).attr("onclick", "javascript:fnRegSubjFavor( '02', '" + subj + "', 'register' );").attr("title", "이 강좌를 찜한 강좌 목록에 추가합니다.");// javascript 함수 변경
                                $("#oBtnFavor").attr("src", "/images/2013/online_edu/btn_class_mine.gif");    // 버튼 이미지 변경

                                successMsg = "찜한 강좌에서 삭제되었습니다.";
                                $("input[name=p_favoryn]").val("N");
                            }

                            alert( successMsg );
                        } else {
                            alert("오류가 발생하였습니다.\n관리자에게 문의하세요.");
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
    }

    function returnFLV(str){
        returnVal = document.getElementById("oFlashVar").value;
        return returnVal;
    }

    /**
     * 임베드 유형의 열린강좌 열기
     */
    function fnLoadSWF(file, width, height, trans, flashVars){
        // file = "http://edu.kocca.or.kr" + file;

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
        $(".divPopupMask, #divPopup, #vodArea, .vodWinCloseBtn").hide();
        $(".play_movie > a").focus();
    }

    /**
     * 임베드 유형의 열린강좌 조회수 갱신 (ajax)
     */
    function fnUpdateViewCount(seq) {
        $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.infomation.GoldClassHomePageServlet"
            ,   dataType : "text"
            ,   data : "p_process=updateViewCount&seq=" + seq
            ,   success : function(data) { }
            ,   complete : function(arg1, arg2) {}
            ,   error :  function(arg1, arg2) {}
        });
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

            if ( $("#vodArea").is(":hidden") ) {

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
                $(".vodWinCloseBtn").css( {"left": closeBtnPosX, "top": closeBtnPosY } );
                $(".divPopupMask, #divPopup, #vodArea, .vodWinCloseBtn").show();

                $(".vodWinCloseBtn > a").focus();

                $.get(
                    vodUrl,
                    function( data ) {
                        $("#oHiddenFlashArea").html( data.substr(data.indexOf("<body"), data.lastIndexOf("</body>") ) );

                        fnLoadSWF(vodPath, width, height, true, "vodURL=" + $("#oFlashVar").val());

                        fnUpdateViewCount(seq);
                    }
                );
            } else {
                return;
            }
        }
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

    function fnUpdateCountCallback(data) {
        alert("fnUpdateCountCallback : " + data);
    }

	function scriptAct(){
		window.open('/contents/openclass/<%= seq %>/script/script.html','','width=600,height=540');
	}

    /**
     * 후기 내용을 확인한다.
     */
    function fnViewReivewContents( idx ) {
        var contObj = $("#oReviewContents" + idx);
        var hiddenYn = contObj.is(":hidden");

        $("tr[name=reviewContents]").hide();

        if ( hiddenYn ) {
            contObj.show();
        } else {
            contObj.hide();
        }
    }

    /**
     * 후기 목록에서 페이지 이동 처리를 한다.
     * ajax를 이용하여 새로운 목록을 조회하고 하단의 페이지 기능도 처리한다.
     */
    function goPage(pageNum) {
        var param = "p_process=selectReviewListForAjax&p_seq=" + $("#oSeq").val() + "&p_pageno=" + pageNum;

        var reviewListStr = "";
        var pagingStr = "";
        var idx = 0;
        var starRate = "";
        var point = 0;
        var loginUserId = "<%= box.getSession("userid") %>";

        var pageNo = 0;
        var pageSize = 3;
        var startPage = 0;
        var endPage = 0;
        var blockSize = 0;
        var pageBlock = 0;
        var blockCount = 0;

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.infomation.GoldClassHomePageServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {

                    var currCnt = data.reviewList.length;
                    var k = 0;

                    // 후기 목록 표시
                    if ( currCnt > 0 ) {
                        $("tr[name=reviewTitle]").remove();
                        $("tr[name=reviewContents]").remove();

                        $.each( data.reviewList, function(){

                            point = Number(this.d_checkpoin);
                            for ( var i = 1; i <= point; i++ ) {
                                starRate += "★";
                            }
                            for ( var j = 1; j <= 5 - point; j++ ) {
                                starRate += "☆";
                            }

                            reviewListStr += "<tr id=\"oReviewTitle" + idx + "\" name=\"reviewTitle\"> \n";
                            reviewListStr += "    <th scope=\"row\">" + this.d_dispnum + "</th> \n";
                            reviewListStr += "    <td>  \n";
                            reviewListStr += "        <div style=\"width:420px; height:16px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis;\">    \n";
                            reviewListStr += "            <a href=\"javascript:fnViewReivewContents(" + idx + ");\" >" + this.d_cont + "</a>    \n";
                            reviewListStr += "        </div>    \n";
                            reviewListStr += "    </td> \n";
                            reviewListStr += "    <td>" + starRate + "</td> \n";
                            reviewListStr += "    <td style=\"text-align:center;\">" + this.d_name + "</td>  \n";
                            reviewListStr += "    <td>" + this.d_regdt + "</td>   \n";
                            reviewListStr += "</tr> \n";
                            reviewListStr += "<tr id=\"oReviewContents" + idx + "\" name=\"reviewContents\" style=\"display:none;\">  \n";
                            reviewListStr += "    <td colspan=\"5\" style=\"border-left:0; padding-left:131px;\"> \n";
                            reviewListStr += "        " + this.d_cont.replace(/\n/gi, "<br/>");  + " \n";
                            reviewListStr += "        <input type=\"hidden\" id=\"oContentsText" + idx + "\" name=\"contentsText" + idx + "\" value=\"" + this.d_cont + "\" /> \n";

                            if ( loginUserId != null && loginUserId != "" && loginUserId == this.d_regid ) {
                                reviewListStr += "        <div style=\"padding:10px 0 0 0; text-align:right;\">   \n";
                                reviewListStr += "            <a href=\"javascript:fnViewModifyPopup(" + this.d_num + ", " + this.d_checkpoin + ", " + idx + ");\"><img src=\"/images/2013/online_edu/btn_write_modify.gif\" alt=\"수정\" /></a> \n";
                                reviewListStr += "             <a href=\"javascript:fnDeleteReview(" + this.d_num + ", " + idx + ");\"><img src=\"/images/2013/online_edu/btn_delete.gif\" alt=\"삭제\" /></a>   \n";
                                reviewListStr += "        </div>  \n";
                            }
                            reviewListStr += "    </td> \n";
                            reviewListStr += "</tr> \n";

                            $("#oReviewTable tbody").append(reviewListStr);
                            reviewListStr = "";
                            starRate = "";
                            idx++;

                            pageNo = this.d_pageno;
                            startPage = this.d_startpage;
                            endPage = this.d_endpage;
                            pageBlock = this.d_pageblock;
                            blockSize = this.d_blocksize;
                            blockCount = this.d_blockcount;
                        });

                        if ( pageBlock == 1 ) {
                            pagingStr += "<a href=\"javascript:void(0);\"><img src=\"/images/2013/common/btn_pre.jpg\" alt=\"이전페이지\" /></a>";
                        } else {
                            pagingStr += "<a href=\"javascript:goPage(" + ( startPage - blockSize ) + ");\"><img src=\"/images/2013/common/btn_pre.jpg\" alt=\"이전페이지\" /></a>";
                        }
                        pagingStr += "<span>";

                        for( var i = startPage ; i <= endPage ; i++ ) {
                            if (i == pageNo) {
                                pagingStr += "<strong>" + i + "</strong>";
                            } else {
                                pagingStr += "<a href=\"javascript:goPage('" + i + "');\">" + i + "</a>";
                            }
                        }

                        pagingStr += "</span>";

                        if ( pageBlock == blockCount ) {
                            pagingStr += "<a href=\"javascript:void(0);\"><img src=\"/images/2013/common/btn_next.jpg\" alt=\"다음페이지\" /></a>";
                        } else {
                            pagingStr += "<a href=\"javascript:goPage(" + ( endPage + 1 ) + ");\"><img src=\"/images/2013/common/btn_next.jpg\" alt=\"다음페이지\" /></a>";
                        }

                        $("#oPagingArea").css("display", "block");
                        $("#oPagingArea").empty();
                        $("#oPagingArea").append(pagingStr);

                    } else {
                        reviewListStr = "";
                        reviewListStr += "<tr name=\"reviewTitle\"> \n";
                        reviewListStr += "    <td style=\"text-align:center; border-left:1px;\" colspan=\"5\">등록된 후기가 없습니다.</td>  \n";
                        reviewListStr += "</tr> \n";
                        $("tr[name=reviewTitle]").remove();
                        $("tr[name=reviewContents]").remove();
                        $("#oPagingArea").css("display", "none");
                        $("#oReviewTable tbody").append(reviewListStr);
                    }

                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(XMLHttpRequest, textStatus, errorThrown) {
                    // alert("error : " + errorThrown);
                    alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                }

        });

    }
    /**
     * 후기 내용을 등록한다.
     */
    function fnRegisterReview() {

        if ( $("input:radio[name=checkpoin]:checked").length == 0 ) {
            alert("평점을 선택해주세요.");
            $("input:radio[name=checkpoin]").eq(0).focus();
            return;

        } else if( $("#oReviewRegForm textarea[name=cont]").val() == "") {
            alert("후기 내용을 입력하세요.");
            $("#oReviewRegForm textarea[name=cont]").focus();
            return;

        } else if (confirm("후기를 등록하시겠습니까?")) {

            $("#oReviewRegForm input[name=process]").val("registerReview");
            var param = $("#oReviewRegForm").serialize();

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.openclass.OpenClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $('[data-dismiss="modal"]').trigger("click");

                        if ( data.resultCnt > 0 ) {
                            $("#oReviewButton").hide();
                            goPage(1);
                            alert("후기가 등록되었습니다.");
                            $("#oReviewRegForm textarea[name=cont]").val("");
                            $("#oReviewRegForm input:radio[name=checkpoin]").removeAttr("checked");
                        } else {
                            alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                        }
                    }
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(XMLHttpRequest, textStatus, errorThrown) {
                        // alert("error : " + errorThrown);
                        alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                    }
            });
        }
    }

    /**
     * 후기 내용 수정 팝업을 연다.
     */
    function fnViewModifyPopup(num, checkpoin, idx) {
        $("input:radio[name=checkpoin][value=\"" + checkpoin +"\"]").attr("checked", true);
        $("#oReviewRegForm textarea[name=cont]").val( $("#oContentsText" + idx).val() );
        $(".modal-content .modal-btn").eq(0).attr("onclick", "fnModifyReview(" + num + ");");
        $("[data-toggle=\"modal\"]").trigger("click");
    }

    /**
     * 후기 내용을 수정한다.
     */
    function fnModifyReview( num ) {

        if ( $("input:radio[name=checkpoin]:checked").length == 0 ) {
            alert("평점을 선택해주세요.");
            $("input:radio[name=checkpoin]").eq(0).focus();
            return;

        } else if( $("#oReviewRegForm textarea[name=cont]").val() == "") {
            alert("후기 내용을 입력하세요.");
            $("#oReviewRegForm textarea[name=cont]").focus();
            return;

        } else if (confirm("후기를 수정하시겠습니까?")) {

            $("#oReviewRegForm input[name=process]").val("modifyReview");
            $("#oReviewRegForm input[name=num]").val(num);
            var param = $("#oReviewRegForm").serialize();

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.openclass.OpenClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $('[data-dismiss="modal"]').trigger("click");

                        if ( data.resultCnt > 0 ) {
                            // $("#oReviewButton").hide();
                            goPage(1);
                            alert("후기가 수정되었습니다.");
                            $("#oReviewRegForm textarea[name=cont]").val("");
                            $("#oReviewRegForm input:radio[name=checkpoin]").removeAttr("checked");
                        } else {
                            alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                        }
                    }
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(XMLHttpRequest, textStatus, errorThrown) {
                        // alert("error : " + errorThrown);
                        alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                    }
            });
        }
    }

    /**
     * 후기 내용을 삭제한다.
     */
    function fnDeleteReview(num, idx) {
        $("#oReviewRegForm input[name=process]").val("deleteReview");
        $("#oReviewRegForm input[name=num]").val(num);
        var param = $("#oReviewRegForm").serialize();

        if (confirm("후기를 삭제하시겠습니까?")) {

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.openclass.OpenClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $('[data-dismiss="modal"]').trigger("click");
                        if ( data.resultCnt > 0 ) {
                            $("#oReviewTitle" + idx).remove();
                            $("#oReviewContents" + idx).remove();
                            goPage(1);
                            alert("후기가 삭제되었습니다.");
                            $("#oReviewRegForm textarea[name=cont]").val("");
                            $("#oReviewRegForm input:radio[name=checkpoin]").removeAttr("checked");
                            $(".modal-content .modal-btn").eq(0).attr("onclick", "fnRegisterReview();");
                            $("#oReviewButton").show();
                        } else {
                            alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                        }
                    }
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(XMLHttpRequest, textStatus, errorThrown) {
                        // alert("error : " + errorThrown);
                        alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                    }
            });
        }
    }
</script>
<!-- Common 영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

<form name="openClassForm" method="post" action="">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" id="oSeq" name="p_seq" value="<%= box.get("p_seq") %>" />
    <input type="hidden" name="seq" value="<%= box.get("p_seq") %>" />
    <input type="hidden" name="p_prePage" value="" />
    <input type="hidden" name="p_genre" value="" />
    <input type="hidden" name="p_lecture_cls" value="<%= lectureCls %>" />
    <input type="hidden" name="pageNo" value="">


    <div id="mainMenu">
        <% if(gubun.equals("GC")){ %>
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
		<% } else if (gubun.equals("OT")) { %>
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
                <li><a href="javascript:fnOpenClassThemeList('ALL');"><%= lectureCls.equals("ALL") ? "<strong><u>전체</u></strong>" : "전체" %> (<%= openClassCnt.getString("d_all_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT01');"><%= lectureCls.equals("OT01") ? "<strong><u>명사특강</u></strong>" : "명사특강" %> (<%= openClassCnt.getString("d_ot01_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT02');"><%= lectureCls.equals("OT02") ? "<strong><u>트렌드</u></strong>" : "트렌드" %></a> (<%= openClassCnt.getString("d_ot02_cnt") %>)</li>
                <li><a href="javascript:fnOpenClassThemeList('OT03');"><%= lectureCls.equals("OT03") ? "<strong><u>스킬노하우</u></strong>" : "스킬노하우" %> (<%= openClassCnt.getString("d_ot03_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT04');"><%= lectureCls.equals("OT04") ? "<strong><u>칭업열전</u></strong>" : "창업열전" %> (<%= openClassCnt.getString("d_ot04_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT05');"><%= lectureCls.equals("OT05") ? "<strong><u>직업탐구</u></strong>" : "직업탐구" %> (<%= openClassCnt.getString("d_ot05_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT06');"><%= lectureCls.equals("OT06") ? "<strong><u>마스터클래스</u></strong>" : "마스터클래스" %> (<%= openClassCnt.getString("d_ot06_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT07');"><%= lectureCls.equals("OT07") ? "<strong><u>콘텐츠인사이트</u></strong>" : "콘텐츠인사이트" %> (<%= openClassCnt.getString("d_ot07_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT08');"><%= lectureCls.equals("OT08") ? "<strong><u>경영/전략</u></strong>" : "경영/전략" %> (<%= openClassCnt.getString("d_ot08_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT09');"><%= lectureCls.equals("OT09") ? "<strong><u>마케팅</u></strong>" : "마케팅" %> (<%= openClassCnt.getString("d_ot09_cnt") %>)</a></li>
                <li><a href="javascript:fnOpenClassThemeList('OT10');"><%= lectureCls.equals("OT10") ? "<strong><u>비지니스 스킬</u></strong>" : "비지니스 스킬" %> (<%= openClassCnt.getString("d_ot10_cnt") %>)</a></li>
            </ul>
        </div>
        
        <!-- //서브메인용 -->
		<% } %>
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit"><%=v_subTit%></h2>
        <div id="subCont">


            <!-- online_box -->
            <div class="online_box">
                <div class="smart_device">
                    <div class="sdleft" style="width:938px">
                        <p>
                            <a href="javascript:fnViewOpenClass('<%= dbox.getString("d_seq") %>', '<%= dbox.getString("d_vod_path") %>', '<%=dbox.getString("d_width_s") %>','<%=dbox.getString("d_height_s") %>', '<%= dbox.getString("d_lecture_type") %>', '<%= dbox.getString("d_vodurl") %>');">
                            <img src="<%= dbox.getString("d_vodimg").replaceAll("\\\\","/") %>" style="width:938px; height:457px" alt="강좌보기" /></a>
                        </p>
                    </div>
                    <div class="play_movie">
                        <a href="javascript:fnViewOpenClass('<%= dbox.getString("d_seq") %>', '<%= dbox.getString("d_vod_path") %>', '<%=dbox.getString("d_width_s") %>','<%=dbox.getString("d_height_s") %>', '<%= dbox.getString("d_lecture_type") %>', '<%= dbox.getString("d_vodurl") %>');"><img src="/images/2013/btn/btn_playw.png" alt=""/></a>
                    </div>
                    <div style="width:938px">
<%
                        if("Y".equals(dbox.getString("d_hit_yn"))){
%>
                        <img src="/images/2013/online_edu/detail_popular.gif" style="margin-right:5px;" alt="인기" />
<%
                        }
                        if("Y".equals(dbox.getString("d_recom_yn"))){
%>
                        <img src="/images/2013/online_edu/detail_recommend.gif" style="margin-right:5px;" alt="추천" />
<%
                        }
                        if("Y".equals(dbox.getString("d_new_yn"))){
%>
                        <img src="/images/2013/online_edu/detail_new.gif" style="margin-right:5px;" alt="신규" />
<%
                        }
%>
                    </div>
					
                    <div id="divPopup">
                        <div id="vodArea"></div>
                        <div class="vodWinCloseBtn"><a href="javascript:fnCloseViewWin();"><img src="/images/common/close.png" alt="동영상창 닫기" /></a></div>
                    </div>
                    <div id="oHiddenFlashArea" style="display:none;"></div>

                    <div class="title" style="width:938px; margin-top:-40px;">
                        <%=dbox.getString("d_lecnm")%>
                    </div>
                    <div>
                        <div>
                            <img src="/images/2013/online_edu/dot2.gif" width= "100%" style="margin-top:10px" alt="구분선" />
                        </div>
                        <div id="oBtnList" style="float:right; margin-top:10px;" >
<%
                            if ("N".equals(dbox.getString("d_favor_yn"))){

%>
                            <a href="javascript:fnRegSubjFavor( '02', '<%= dbox.get("d_seq") %>', 'register' );" title="이 강좌를 찜한 강좌 목록에 추가합니다."><img id="oBtnFavor" src="/images/2013/online_edu/btn_class_mine.gif" style="margin-left:10px;" alt="찜하기" /></a>
<%
                            }else{
%>
                            <a href="javascript:fnRegSubjFavor( '02', '<%= dbox.get("d_seq") %>', 'cancel' );" title="이 강좌를 찜한 강좌 목록에서 삭제합니다."><img id="oBtnFavor" src="/images/2013/online_edu/btn_class_minex.gif" style="margin-left:10px;" alt="찜취소" /></a>
<%
                            }
%>
                            <a href="javascript:void(0);" id="oAnchorShareSns" title="이 강좌를 학습자의 SNS 계정으로 공유합니다."><img src="/images/2013/online_edu/btn_class_share.gif" style="margin-left:10px;" alt="공유하기" /></a>
                        </div>
                    </div>
                </div>

                <!-- 공유하기 -->
                <div class="share" id="oshareSns" style="position: absolute;top: 952px;left: 735px; width:100px; height:70px; border:solid 1px #d1d1d1; background-color:#ffffff; display:none;">
                    <dl style="margin:10px;">
                        <dt><a href="javascript:fnShareOnSNS('facebook');"><img src="/images/2013/online_edu/logo_facebook.png"  style="margin-right:5px; vertical-align:middle;" alt="페이스북" />페이스북</a></dt>
                    </dl>
                    <dl style="margin:10px;">
                        <dt><a href="javascript:fnShareOnSNS('twitter');"><img src="/images/2013/online_edu/logo_twitter.png" style="margin-right:5px; vertical-align:middle;" alt="트위터" />트위터</a></dt>
                    </dl>
                </div>

                <p class="tit1"><img src="/images/2013/online_edu/online_tit_8.gif" alt="이 강좌에 대하여" /></p>
                <ul class="list1">
                    <li><%=dbox.getString("d_intro")%></li>
                    <li></li>
                </ul>

                <p class="tit1"><img src="/images/2013/online_edu/online_tit_9.gif" alt="강사소개" /></p>
                <ul class="list1">
                    <li><%= dbox.getString("d_tutornm") %></li>
                    <li><%= dbox.getString("d_tutorcareer").replaceAll("\n", "<br/>") %></li>
                    <li><%= dbox.getString("d_tutorauthor").replaceAll("\r\n", "<br/>") %></li>
                </ul>

                <!--
                <p class="tit2"><img src="/images/2013/online_edu/online_tit_6.gif" alt="후기" /></p>
                <div class="tableWrap2 space5">
                    <table class="board" summary="">
                        <caption>후기 목록</caption>
                        <colgroup>
                            <col width="6%"/>
                            <col width="*"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="10%"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">번호</th>
                            <th scope="col">내용</th>
                            <th scope="col">평가</th>
                            <th scope="col">작성자</th>
                            <th scope="col">작성일</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="first">5</td>
                            <td class="bldnone left"><a href="notice_view.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                            <td class="bldnone">★★★★☆</td>
                            <td>한아미</td>
                            <td>2014.08.18</td>
                        </tr>
                        <tr>
                            <td class="first">4</td>
                            <td class="bldnone left"><a href="notice_view.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                            <td class="bldnone">★★★★☆</td>
                            <td>한아미</td>
                            <td>2014.08.18</td>
                        </tr>

                        <tr>
                            <td colspan="5" style="padding:15px 15px 15px 62px; border-color:#e0e0e0; border-left:0px;" >
                                <div style="text-align:left">
                                    지방이라 공부하기 힘들었는데, 관심있는 분야 강의들이 한국콘텐츠아카데미에 있어 덕분에 집에서 편안하게 학습할 수 있어서 너무 좋아요. 강의 내용도 알차고 최고에요!!
                                    지방이라 공부하기 힘들었는데, 관심있는 분야 강의들이 한국콘텐츠아카데미에 있어 덕분에 집에서 편안하게 학습할 수 있어서 너무 좋아요. 강의 내용도 알차고 최고에요!!
                                </div>
                                <div style="float:right; margin-top:15px;">
                                    <a href="#"><img src="/images/2013/online_edu/btn_write_modify.gif" alt="수정" /></a>
                                    <a href="#"><img src="/images/2013/online_edu/btn_write_close.gif" alt="닫기" /></a>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="first">3</td>
                            <td class="bldnone left"><a href="notice_view.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                            <td class="bldnone">★★★★☆</td>
                            <td>한아미</td>
                            <td>2014.08.18</td>
                        </tr>
                        <tr>
                            <td class="first">2</td>
                            <td class="bldnone left"><a href="notice_view.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                            <td class="bldnone">★★★★☆</td>
                            <td>한아미</td>
                            <td>2014.08.18</td>
                        </tr>
                        <tr>
                            <td class="first">1</td>
                            <td class="bldnone left"><a href="notice_view.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                            <td class="bldnone">★★★★☆</td>
                            <td>한아미</td>
                            <td>2014.08.18</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="paging">
                    <a href="#_"><img src="/images/2013/common/btn_pre.jpg" alt="이전페이지" /></a>
                    <span>
                        <strong>1</strong>
                        <a href="#_">2</a>
                        <a href="#_">3</a>
                        <a href="#_">4</a>
                        <a href="#_">5</a>
                        <a href="#_">6</a>
                        <a href="#_">7</a>
                        <a href="#_">8</a>
                        <a href="#_">9</a>
                        <a href="#_">10</a>
                    </span>
                    <a href="#_"><img src="/images/2013/common/btn_next.jpg" alt="다음페이지" /></a>

                    <a href="#"><img src="/images/2013/online_edu/btn_write.gif" style="float:right; margin-top:-10px;" alt="후기작성" /></a>
                </div>
                //-->
                <!-- //tableWrap2 -->

        <!-- 과정후기 시작 //-->
		<div class="body">

			<p class="tit1" style="margin-bottom:20px;"><img src="/images/2013/online_edu/online_tit_6.gif" alt="과정후기" /></p>
            <div class="tableWrap10 space2">
                <table id="oReviewTable" class="dataTable10" summary="이 표는 후기 표로 번호,내용,평가,작성자,작성일로 구성 되어 있습니다.">
                    <caption>후기 표</caption>
                    <colgroup>
                        <col style="width:110px;"/>
                        <col style="width:427px;"/>
                        <col style="width:110px;"/>
                        <col style="width:110px;"/>
                        <col style="width:110px;"/>
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">번호</th>
                            <th scope="col">내용</th>
                            <th scope="col">평가</th>
                            <th scope="col">작성자</th>
                            <th scope="col">작성일</th>
                        </tr>
                    </thead>
                    <tbody>

<%
        if ( reviewList.size() == 0 || reviewList == null ) {
%>
                        <tr name="reviewTitle">
                            <td style="text-align:center; border-left:1px;" colspan="5">등록된 후기가 없습니다.</td>
                        </tr>
<%
        } else {
            DataBox rdbox = null;
            String regUserId = "";
            int point = 0;
            String starRate = "";
            for ( int i = 0 ; i < reviewList.size(); i++ ) {
                rdbox = (DataBox)reviewList.get(i);
                regUserId = rdbox.getString("d_regid");

                point = rdbox.getInt("d_checkpoin");
                starRate = "";
                for ( int k = 1; k <= point; k++ ) {
                    starRate += "★";
                }
                for ( int j = 1; j <= 5 - point; j++ ) {
                    starRate += "☆";
                }
%>
                    <tr id="oReviewTitle<%= i %>" name="reviewTitle">
                        <th scope="row"><%= rdbox.getString("d_dispNum") %></th>
                        <td>
                            <div style="width:420px; height:16px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
                                <a href="javascript:fnViewReivewContents(<%= i %>);" ><%= rdbox.getString("d_cont") %></a>
                            </div>
                        </td>
                        <td><%= starRate %></td>
                        <td style="text-align:center;"><%= rdbox.getString("d_name") %></td>
                        <td><%= rdbox.getString("d_regdt") %></td>
                    </tr>
                    <tr id="oReviewContents<%= i %>" name="reviewContents" style="display:none;">
                        <td colspan="5" style="border-left:0; padding-left:131px;">
                            <%= rdbox.getString("d_cont").replaceAll("\\n", "<br/>") %>
                            <input type="hidden" id="oContentsText<%= i %>" name="contentsText<%= i %>" value="<%= rdbox.getString("d_cont") %>" />

<%
                if ( loginUserId != null && !loginUserId.equals("") && loginUserId.equals(regUserId) ) {
                    isMyReviewReg = true;
%>
                            <div style="padding:10px 0 0 0; text-align:right;">
                                <a href="javascript:fnViewModifyPopup(<%= rdbox.getInt("d_num") %>, <%= point %>, <%= i %>);" ><img src="/images/2013/online_edu/btn_write_modify.gif" alt="수정" /></a>
                                <a href="javascript:fnDeleteReview(<%= rdbox.getInt("d_num") %>, <%= i %>);"><img src="/images/2013/online_edu/btn_delete.gif" alt="삭제" /></a>
                            </div>
<%
                }
%>
                        </td>
                    </tr>
<%
                totalRowCount = rdbox.getInt("d_tot_cnt");
                totalPage = rdbox.getInt("d_totalPage");
            }
        }
%>
                    </tbody>
                </table>
            </div>

            <div id="oReviewButton" style="text-align:right; margin-top:15px; <%= (!isMyReviewReg && !loginUserId.equals("") ) ? "display:block;" : "display:none;"%>">
                <a href="#" data-toggle="modal" data-target="#sample"><img src="/images/2013/online_edu/btn_write.gif" alt="후기작성" /></a>
            </div>

            <div class="paging" id="oPagingArea">
<%
        if ( reviewList != null && reviewList.size() > 0 ) {
%>
                <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 5, pageSize, totalRowCount) %>
<%
        }

%>
                    <!-- <a href="#" data-toggle="modal" data-target="#sample"><img src="/images/2013/online_edu/btn_write.gif" alt="후기작성" /></a> //-->
<%
        // }
%>
            </div>
		</div>
		<!-- 과정후기 끝 //-->

                <!-- movList2 다음과정-->
                <p class="tit2"><img src="/images/2013/online_edu/online_tit_7.gif" style="margin-top:50px;" alt="다음과정" /></p>
                <div class="movList2">
<%
                            /*
                             * 다음과정.. 연관과정리스트
                             */
                            DataBox dnbox = null;
                            if(nextSubjList != null && nextSubjList.size() >0){
                                for (int i=0 ; i < nextSubjList.size() ; i++) {
                                    dnbox = (DataBox)nextSubjList.get(i);
                                    int bWidth=600;
                                    String mobileuseYn= dnbox.getString("d_mobile_use_yn");
                                    String imgurl = dnbox.getString("d_vodimg");
                                    imgurl = imgurl.replaceAll("\\\\", "/");

                                    if ( dnbox.getString("d_new_yn").equals("Y") ) { bWidth -= 35;}
                                    if ( dnbox.getString("d_recom_yn").equals("Y") ) { bWidth -= 35;}
                                    if ( dnbox.getString("d_hit_yn").equals("Y") ) { bWidth -= 35;}
%>

                    <dl style="height:80px">
                        <dt>
                            <a href="javascript:fnViewContent('<%= dnbox.getString("d_seq") %>');"><img src="<%=imgurl%>" width="126" height="80" alt="<%=  dnbox.getString("d_lecnm") %>" /></a>
<!--                            <span class="btn_threeWrap">-->
<!--                                <span class="btn_three1"><a href="#"> 과정 만족도 : 90</a></span>-->
<!--                            </span>-->
                        </dt>
                        <dd class="title">
                            <div class="lecname">
                            <a href="javascript:fnViewContent('<%= dnbox.getString("d_seq") %>');" style="padding-bottom:8px;width:<%=bWidth%>px;"><%=  dnbox.getString("d_lecnm") %></a>
                            </div>
                            <span class="img_mobile" style="float:right; margin-top:-6px;">
<%
                if( dnbox.getString("d_hit_yn").equals("Y") ) {
%>
                                <img src="/images/2013/online_edu/detail_popular.png" style="float:right; margin-right:5px;" alt="인기" />
<%
                }
                if( dnbox.getString("d_recom_yn").equals("Y") ) {
%>
                                <img src="/images/2013/online_edu/detail_recommend.png" style="float:right; margin-right:5px;" alt="추천" />
<%
                }
                if( dnbox.getString("d_new_yn").equals("Y") ) {
%>
                                <img src="/images/2013/online_edu/detail_new.png" style="float:right; margin-right:5px;" alt="신규" />
<%
                }
%>
                            </span>

                        </dd>
                        <dd style="margin-top:10px;"><%=dbox.getString("d_tutornm")%></dd>
                        <dd class="txt" style="white-space:auto; overflow:hidden; text-overflow:ellipsis;"><%=dnbox.getString("d_intro")%></dd>

                    </dl>
<%
                                }
                            }else{
%>
                    <dl><dd>다음 과정이 없습니다.</dd></dl>
<%
                            }
%>
                </div>
                <!--// movList2 -->
            </div>

        </div>
        <!-- //#subCont -->
    </div>
    <!-- 컨텐츠 영역 끝 -->
</form>

</div>
<!--  //container-new 끝 //-->

<div class="divPopupMask"></div>
<!-- 후기 등록 모달 팝업 시작 //-->
<form id="oReviewRegForm" name="reviewRegForm">
    <input type="hidden" name="process" value="" />
    <input type="hidden" name="seq" value="<%= box.get("p_seq") %>" />
    <input type="hidden" name="num" value="" />
<div id="sample" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<button type="button" data-dismiss="modal" class="modal-close">X</button>
			<div class="header">
				<h4>후기</h4>
			</div>
			<div class="modal-review-star">
				<strong>평점주기 :</strong>
				<label><input type="radio" id="oPoint5" name="checkpoin" value="5" /> <img src="/images/common/icon-star5.png" alt="평가점수 5점"></label>
				<label><input type="radio" id="oPoint4" name="checkpoin" value="4" /> <img src="/images/common/icon-star4.png" alt="평가점수 4점"></label>
				<label><input type="radio" id="oPoint3" name="checkpoin" value="3" /> <img src="/images/common/icon-star3.png" alt="평가점수 3점"></label>
				<label><input type="radio" id="oPoint2" name="checkpoin" value="2" /> <img src="/images/common/icon-star2.png" alt="평가점수 2점"></label>
				<label><input type="radio" id="oPoint1" name="checkpoin" value="1" /> <img src="/images/common/icon-star1.png" alt="평가점수 1점"></label>
			</div>
			<textarea class="modal-review-textarea" placeholder="최대 500byte까지 작성가능하며, 욕설이나 비방글은 삭제될 수 있습니다." maxlength="500" id="oContents" name="cont"></textarea>
			<div class="footer">
				<button type="button" class="modal-btn modal-btn-default" onclick="fnRegisterReview();">확인</button>
				<button type="button" class="modal-btn modal-btn-default" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>
</form>
<!-- 후기 등록 모달 팝업 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>