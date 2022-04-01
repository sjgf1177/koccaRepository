<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.credu.propose.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<jsp:useBean id="getCodenm" class="com.credu.common.GetCodenm" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    DataBox subjectPreview = (DataBox) request.getAttribute("subjectPreview");
    List<DataBox> subjseqList = (List<DataBox>)request.getAttribute("subjseqList");
    List<DataBox> lessonList = (List<DataBox>)request.getAttribute("lessonList");
    ArrayList nextSubjList = (ArrayList)request.getAttribute("nextSubjList"); // 연관 강좌
    ArrayList<DataBox> reviewList = (ArrayList<DataBox>)request.getAttribute("reviewList");

    String sugang_YN = subjectPreview.getString("d_sugang_YN");
    String propose_yn = subjectPreview.getString("d_propose_yn");
    String biyong = subjectPreview.getString("d_biyong");
    String grcode = box.getSession("tem_grcode");
    String pRprocess = box.get("p_rprocess");

    String v_area = box.get("p_area");
    String p_specials = box.get("p_specials");
    String p_action = box.get("p_action");
    String v_upperclass = box.get("p_upperclass");

    String loginUserId = box.getSession("userid");

    if (box == null) box = RequestManager.getBox(request);
    int pageNo = ( box.getInt("p_pageno") == 0 ) ? 1 : box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int pageSize = ( box.getInt("p_pagesize") == 0 ) ? 5 : box.getInt("p_pagesize");
    int totalRowCount = 0;
    int totalPage = 0;

    boolean isSeq = (subjseqList != null && subjseqList.size() != 0);
    boolean isMyReviewReg = false;

    pRprocess = (pRprocess == null || pRprocess.equals("")) ? "SubjectList" : pRprocess;

    box.put("title_str",  subjectPreview.getString("d_subjnm") + ", 과정안내, 온라인 교육" );

    box.put("og_title", subjectPreview.getString("d_subjnm"));
    box.put("og_url", "http://edu.kocca.or.kr/servlet/controller.propose.ProposeCourseServlet?p_process=" + box.get("p_process") + "&p_rprocess=" + pRprocess + "&p_subj=" + box.get("p_subj") + "&p_year=" + box.get("p_year") + "&p_subjseq=" + box.get("p_subjseq") );
    box.put("og_description", subjectPreview.getString("d_intro"));
    box.put("og_image", "http://edu.kocca.or.kr" + subjectPreview.getString("d_introducefilenamenew").replaceAll("\\\\","/") );

    String imgurl = subjectPreview.getString("d_introducefilenamenew");
    imgurl = imgurl.replaceAll("\\\\", "/");

    if (imgurl.equals("")) {
        imgurl = "/images/2012/common/not_image.gif";
    }

    
    //학습창 호출 시작
             
    String eduUrl = "";
    int ieduurl = 0;
    String v_edulist_value= "";
    String isoutsourcing = "";
    String edulist_value= "";
    String subj = subjectPreview.getString("d_subj");
    String year = subjectPreview.getString("d_year");
    
    String subjSeq = subjectPreview.getString("d_subjseq");
    String cpsubj = subjectPreview.getString("d_cpsubj");
    String cpsubjseq = subjectPreview.getString("d_cpsubjseq");
    String company = subjectPreview.getString("d_company");
    String contenttype = subjectPreview.getString("d_contenttype");
 	
    // 학습url=외주인 경우..
    if(eduUrl.equals("")) { ieduurl = 0; }else{ ieduurl = 1; }

    // 학습현황
    edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ subj +"&p_year="+ year +"&p_subjseq="+ subjSeq+"&p_userid=" + loginUserId;

    if (isoutsourcing.equals("Y")) {                 //위탁과정일때

    	eduUrl = "/servlet/controller.contents.EduStart";
    	eduUrl += "?FIELD1=" + loginUserId + "&FIELD2=" + year + "&FIELD3=" + cpsubj + "&FIELD4=" + cpsubjseq + "&FIELD99=" + company+"&FIELD100=N"+ "&contenttype=" + contenttype+ "&p_subj=" + subj +"&p_year="+ year +"&p_subjseq="+subjSeq;
    } else {//외부과정이 아닌경우
         //직접 로직 흡수.. eduUrl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);
        if (eduUrl.equals("")){
        	eduUrl = "/servlet/controller.contents.EduStart?p_subj="+subj;
        }

        if (!year.equals(""))
        	eduUrl = eduUrl+"&p_year="+year+"&p_subjseq=";
        else
        	eduUrl = eduUrl+"&p_year=2000&p_subjseq=";

        if(!subjSeq.equals(""))
        	eduUrl = eduUrl+subjSeq;
        else eduUrl = eduUrl+"0001";
        eduUrl +=  "&contenttype=" + contenttype;
    }
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<style>
    .course-detail-tabs {border:4px solid #d1d1d1; border-top-width:3px;}
    .course-detail-tabs .header {height:31px; background:#d1d1d1;}
    .course-detail-tabs .header li {position:relative; float:left; line-height:18px; font-family:'나눔고딕','nanumgothic','맑은 고딕','malgun gothic',sans-serif;}
    .course-detail-tabs .header li a {display:block; height:27px; padding:4px 40px 0 40px; color:#3d3d3d; font-size:14px; font-weight:600;}
    .course-detail-tabs .header li i {display:none; position:absolute; left:50%; bottom:-10px; width:13px; height:8px; margin-left:-7px; background:url(/images/common/icon-tabs-ar.png) no-repeat;}
    .course-detail-tabs .header li.active a {color:#ce0000; font-weight:bold; background:#fff;}
    .course-detail-tabs .header li.active i {display:block;}
    .course-detail-tabs .body {display:none; padding:59px;}
</style>
<script>
    $(function(){

        $("#oAnchorShareSns").on( {
            "click": function(e) {
                e.preventDefault();
                e.stopPropagation();
                var windowW = $(window).width();

                if ($("#oshareSns").is(":hidden")) {
                    $("#oshareSns").css("top", $("#oBtnList").position().top + 44);
                    $("#oshareSns").css("left", windowW / 2 + 357);
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

            $("#oshareSns").css("left", windowW / 2 + 357);
        });

        $(".course-detail-tabs").each(function(){
            var index = $(this).find(".header .active").index();
            var tabContent = $(this).find(".body");
            tabContent.eq(index).show();
        });
        $(".course-detail-tabs .header a").click(function(){
            var index = $(this).parent().index();
            var tabContent = $(this).parent().parent().parent().parent().find(".body");
            $(this).parent().parent().find("li").removeClass("active");
            $(this).parent().addClass("active");
            tabContent.hide();
            tabContent.eq(index).show();
            return false;
        });
    });

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
                                $("#oBtnList > a").eq(2).attr("href", "javascript:fnRegSubjFavor( '01', '" + subj + "', 'cancel' );").attr("title", "이 과정을 찜한 과정 목록에서 삭제합니다.");// javascript 함수 변경
                                $("#oBtnFavor").attr("src", "/images/2013/online_edu/btn_class_minex.gif");    // 버튼 이미지 변경
                                successMsg = "찜한 강좌에 추가되었습니다.";
                                $("input[name=p_favoryn]").val("Y");

                            } else {
                                $("#oBtnList > a").eq(2).attr("href", "javascript:fnRegSubjFavor( '01', '" + subj + "', 'register' );").attr("title", "이 과정을 찜한 과정 목록에 추가합니다.");// javascript 함수 변경
                                $("#oBtnFavor").attr("src", "/images/2013/online_edu/btn_class_mine.gif");    // 버튼 이미지 변경
                                successMsg = "찜한 강좌에서 삭제되었습니다.";
                                $("input[name=p_favoryn]").val("N");
                            }

                            alert( successMsg );
                            return;
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

    /**
     * 수강신청 팝업창을 연다.
     */
    function fnProposeSubj(subj, year, subjseq, subjnm) {
        if(<%= box.getSession("userid").length()==0 %>) {
            var frmURL = location.href;
            frmURL = frmURL.replace("http://", "");
            var idx = frmURL.indexOf("/");

            frmURL = frmURL.substr(idx, frmURL.length);

            alert("로그인 후  이용해주세요.");
            location.href = "/learn/user/2013/portal/homepage/zu_MainLogin.jsp?p_frmURL=" + encodeURIComponent( frmURL );

            return;
        }

        var confirmMsg = subjnm + " 과정을 학습 하시겠습니까?"

        if ( subj == "CK15002" ) {
            confirmMsg = "※ 본 과정은 [한국양성평등교육진흥원] 콘텐츠를 지원받아 운영되는 과정으로 수료증이 발급되지 않습니다.\n\n 교육시간으로 인정되는 과정을 원하는 경우에는 [한국양성평등교육진흥원(egenedu.kigepe.or.kr/)]으로 신청하시기 바랍니다.\n\n" + confirmMsg;
        }

        if( !confirm(confirmMsg) ) {
            return;
        }

        // 수강 신청 로직 후 학습창으로 이동
        
        var new_Open = window.open("", "proposeWindow", "scrollbars=yes,width=10,height=10,resizable=no");
        document.form1.p_process.value = "SubjectEduPropose";
        document.form1.target = "proposeWindow";
        document.form1.p_subj.value = subj;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.submit();
        document.form1.target = "_self";
    }

    /**
     * 맛보기 강좌를 연다
     */
    function whenPreShow(url, subj, d_wj_classkey) {
        if (d_wj_classkey != '') {  //경영직무교육(웅진패스원)
            if (<%=grcode.equals("N000031") %>)  {
                prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
                open_window("openShow","http://samplezone.campus21.co.kr/classpreview.asp?classkey="+d_wj_classkey,"100","100","720","400","","","",true,true);
            }
            return;
         } else {
            prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
            open_window("openShow",url,"100","100","<%= subjectPreview.get("d_prewidth", "800") %>","<%= subjectPreview.get("d_preheight", "600") %>","","","",true,true);
         }
    }


    /**
     * SNS 공유하기
     */
    function fnShareOnSNS( snsName ) {

        var url = location.href;

        if (url.indexOf("?") == -1 ) {
            url += "?p_process=<%= box.get("p_process") %>?year=<%= box.get("p_year") %>&subj=<%= box.get("p_subj") %>&subjseq=<%= box.get("p_subjseq") %>";
        }

        fnSendSns( snsName, url, "<%= subjectPreview.getString("d_subjnm") %>");

        $("#oshareSns").hide();
    }

    /**
     * 과정 내용보기
     */
    function fnViewSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq){
        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "?p_process=SubjectPreviewPage&p_subj=" + subj + "&p_year=" + year + "&p_subjseq=" + subjseq;

        location.href = url + param;
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
     *
     */

    /**
     * 후기 목록에서 페이지 이동 처리를 한다.
     * ajax를 이용하여 새로운 목록을 조회하고 하단의 페이지 기능도 처리한다.
     */
    function goPage(pageNum) {
        var param = "p_process=selectReviewListForAjax&p_subj=" + $("#oSubj").val() + "&p_pageno=" + pageNum;

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
            ,   url : "/servlet/controller.propose.ProposeCourseServlet"
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

                            point = Number(this.d_point);
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
                            reviewListStr += "            <a href=\"javascript:fnViewReivewContents(" + idx + ");\" >" + this.d_contents + "</a>    \n";
                            reviewListStr += "        </div>    \n";
                            reviewListStr += "    </td> \n";
                            reviewListStr += "    <td>" + starRate + "</td> \n";
                            reviewListStr += "    <td style=\"text-align:center;\">" + this.d_name + "</td>  \n";
                            reviewListStr += "    <td>" + this.d_reg_dt + "</td>   \n";
                            reviewListStr += "</tr> \n";
                            reviewListStr += "<tr id=\"oReviewContents" + idx + "\" name=\"reviewContents\" style=\"display:none;\">  \n";
                            reviewListStr += "    <td colspan=\"5\" style=\"border-left:0; padding-left:90px;\"> \n";
                            reviewListStr += "        " + this.d_contents.replace(/\n/gi, "<br/>");  + " \n";
                            reviewListStr += "        <input type=\"hidden\" id=\"oContentsText" + idx + "\" name=\"contentsText" + idx + "\" value=\"" + this.d_contents + "\" /> \n";

                            if ( loginUserId != null && loginUserId != "" && loginUserId == this.d_userid ) {
                                reviewListStr += "        <div style=\"padding:10px 0 0 0; text-align:right;\">   \n";
                                reviewListStr += "            <a href=\"javascript:fnViewModifyPopup(" + this.d_num + ", " + this.d_point + ", " + idx + ");\"><img src=\"/images/2013/online_edu/btn_write_modify.gif\" alt=\"수정\" /></a> \n";
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

        if ( $("input:radio[name=point]:checked").length == 0 ) {
            alert("평점을 선택해주세요.");
            $("input:radio[name=point]").eq(0).focus();
            return;

        } else if( $("#oReviewRegForm textarea[name=contents]").val() == "") {
            alert("후기 내용을 입력하세요.");
            $("#oReviewRegForm textarea[name=contents]").focus();
            return;

        } else if (confirm("후기를 등록하시겠습니까?")) {

            $("#oReviewRegForm input[name=process]").val("registerReview");
            var param = $("#oReviewRegForm").serialize();

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $('[data-dismiss="modal"]').trigger("click");

                        if ( data.resultCnt > 0 ) {
                            $("#oReviewButton").hide();
                            goPage(1);
                            alert("후기가 등록되었습니다.");
                            $("#oReviewRegForm textarea[name=contents]").val("");
                            $("#oReviewRegForm input:radio[name=point]").removeAttr("checked");
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
    function fnViewModifyPopup(num, point, idx) {
        $("input:radio[name=point][value=\"" + point +"\"]").attr("checked", true);
        $("#oReviewRegForm textarea[name=contents]").val( $("#oContentsText" + idx).val() );
        $(".modal-content .modal-btn").eq(0).attr("onclick", "fnModifyReview(" + num + ");");
        $("[data-toggle=\"modal\"]").trigger("click");
    }

    /**
     * 후기 내용을 수정한다.
     */
    function fnModifyReview( num ) {

        if ( $("input:radio[name=point]:checked").length == 0 ) {
            alert("평점을 선택해주세요.");
            $("input:radio[name=point]").eq(0).focus();
            return;

        } else if( $("#oReviewRegForm textarea[name=contents]").val() == "") {
            alert("후기 내용을 입력하세요.");
            $("#oReviewRegForm textarea[name=contents]").focus();
            return;

        } else if (confirm("후기를 수정하시겠습니까?")) {

            $("#oReviewRegForm input[name=process]").val("modifyReview");
            $("#oReviewRegForm input[name=num]").val(num);
            var param = $("#oReviewRegForm").serialize();

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $('[data-dismiss="modal"]').trigger("click");

                        if ( data.resultCnt > 0 ) {
                            // $("#oReviewButton").hide();
                            goPage(1);
                            alert("후기가 수정되었습니다.");
                            $("#oReviewRegForm textarea[name=contents]").val("");
                            $("#oReviewRegForm input:radio[name=point]").removeAttr("checked");
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
                ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $('[data-dismiss="modal"]').trigger("click");
                        if ( data.resultCnt > 0 ) {
                            $("#oReviewTitle" + idx).remove();
                            $("#oReviewContents" + idx).remove();
                            goPage(1);
                            alert("후기가 삭제되었습니다.");
                            $("#oReviewRegForm textarea[name=contents]").val("");
                            $("#oReviewRegForm input:radio[name=point]").removeAttr("checked");
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

 	// 학습창 OPEN
    function studyOpen(url, iurl) {
        
        var f = document.form1;

        f.lessonReurl.value = url;
        f.lessonReiurl.value = iurl;
        var s_url = url+"&p_iurl="+iurl;
        
        if (url == '') {
            alert("정보가 없습니다");
        } else {
            if (iurl == 0){
                open_window('openApp',s_url,0,0,0,0,false,false,false,true,true);
            }else{
                open_window('openApp',s_url,0,0,1024,768,false,false,false,'yes','yes');
            }
        }
	}
    
</script>
<!-- container-new -->
<div id="container-new">
    <div id="mainMenu">
        <ul class="location">
            <li>홈</li>
            <li>온라인 교육</li>
            <li>
                분야별 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">분야별</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup" >직업별</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem" >교육체계도</a></li>
<%
        if ( currDate < 20160401 ) {
%>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList" >휴넷인문학</a></li>
                    <!-- 휴넷 인문학은 2015년도 위탁 업체 선정 여부에 따라 서비스가 될 수도, 되지 않을 수도 있음. 이에 따라 2015년 3월 1일부터 메뉴를 삭제해 놓은 상태임 //-->
<%
        }
%>

    <!--
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
    -->
                </ul>
            </li>
        </ul>
    </div>
    <!-- online_box -->
    <div class="online_box">
        <div class="smart_device">
            <div class="sdleft"  style="width:400px;">
                <div>
                    <p><img src="<%=imgurl%>" class="courseimage" alt="<%= subjectPreview.getString("d_subjnm") %>" style="width:400px; height:229px;" ></p>
                </div>
            </div>
            <div>
<%
                String mobileUseYn = subjectPreview.getString("d_mobile_use_yn");
                String isnewYn = subjectPreview.getString("d_isnew");
                String ishitYn = subjectPreview.getString("d_ishit");
                String isrecomYn= subjectPreview.getString("d_isrecom");

                if(mobileUseYn.equals("Y")) {
%>
                <img src="/images/2013/online_edu/detail_mbile.png" style="float:right; margin-right:5px;" alt="모바일" />
<%
                }
                if(ishitYn.equals("Y")) {
%>
                <img src="/images/2013/online_edu/detail_popular.png" style="float:right; margin-right:5px;" alt="인기" />
<%
                }
                if(isrecomYn.equals("Y")) {
%>
                <img src="/images/2013/online_edu/detail_recommend.png" style="float:right; margin-right:5px;" alt="추천" />
<%
                }
                if(isnewYn.equals("Y")) {
%>
                <img src="/images/2013/online_edu/detail_new.png" style="float:right; margin-right:5px;" alt="신규" />
<%
                        }
%>
            </div>

            <!--각 기능버튼 시작-->
            <div class="title" style="height:125px; width:450px; padding-left:16px; margin-top:-30px;">

                <%= subjectPreview.getString("d_subjnm") %>
                <ul class="sublist">
                    <li><%= subjectPreview.getHtmlString("d_edumans") %></li>
                </ul>
            </div>

            <div>
                <div>
                    <img src="/images/2013/online_edu/dot.gif" style="margin:16px 0px 0px 20px; width:508px; height:1px;" alt="" />
                </div>
                <div id="oBtnList" style="float:right; margin:10px;">
                    <!--<a class="first" href="javascript:whenPreShow('<%= subjectPreview.get("d_preurl") %>','<%= box.get("p_subj") %>', '<%= box.get("p_wj_classkey") %>' );" title="이 과정의 1차시를 체험할 수 있습니다."><img src="/images/2013/online_edu/btn_class_tasty.gif" alt="맛보기" /></a>-->
<%
                    if (isSeq && "Y".equals(sugang_YN) && "N".equals(propose_yn)) {
%>
                    <a href="javascript:fnProposeSubj('<%= box.get("p_subj") %>', '<%= box.get("p_year") %>', '<%= box.get("p_subjseq") %>', '<%= subjectPreview.getString("d_subjnm") %>');" title="이 과정에 대해 수강신청을 할 수 있습니다."><img src="/images/2013/online_edu/btn_class_study.gif" style="margin-left:10px;" alt="수강신청하기" /></a>
<%
                    } else {
%>
					<a href="javascript:studyOpen( '<%= eduUrl %>', '<%= ieduurl %>');" title="이 과정에 대해 학습을 진행 합니다."><img src="/images/2013/online_edu/btn_class_study.gif" style="margin-left:10px;" alt="학습하기" /></a>
<%                    	
                    }
%>

<%
                    if ("N".equals(propose_yn)){
                        if ("N".equals(subjectPreview.getString("d_favor_yn"))){

%>
                    <!--<a href="javascript:fnRegSubjFavor( '01', '<%= box.get("p_subj") %>', 'register' );" title="이 과정을 찜한 과정 목록에 추가합니다."><img id="oBtnFavor" src="/images/2013/online_edu/btn_class_mine.gif" style="margin-left:10px;" alt="찜하기" /></a>-->
<%
                        }else{
%>
                    <!-- <a href="javascript:fnRegSubjFavor( '01', '<%= box.get("p_subj") %>', 'cancel' );" title="이 과정을 찜한 과정 목록에서 삭제합니다."><img id="oBtnFavor" src="/images/2013/online_edu/btn_class_minex.gif" style="margin-left:10px;" alt="찜취소" /></a>-->
<%
                        }
                    }
%>
                    <!-- <a onclick="javascript:fnShareSns();"><img src="/images/2013/online_edu/btn_class_share.gif" style="margin-left:10px;" alt="공유하기" /></a> //-->
                    <a href="javascript:void(0);" id="oAnchorShareSns" title="이 과정을 학습자의 SNS 계정으로 공유합니다."><img src="/images/2013/online_edu/btn_class_share.gif" style="margin-left:10px;" alt="공유하기" /></a>
                </div>

                <!-- 공유하기 -->
                <div class="share" id="oshareSns" style="position: absolute;top: 666px; left: 1278px; width:100px; height:70px; border:solid 1px #d1d1d1; background-color:#ffffff; display:none;">
                    <dl style="margin:10px;">
                        <dt><a onclick="javascript:fnShareOnSNS('facebook');"><img src="/images/2013/online_edu/logo_facebook.png"  style="margin-right:5px; vertical-align:middle;" alt="페이스북" />페이스북</a></dt>
                    </dl>
                    <dl style="margin:10px;">
                        <dt><a onclick="javascript:fnShareOnSNS('twitter');"><img src="/images/2013/online_edu/logo_twitter.png" style="margin-right:5px; vertical-align:middle;" alt="트위터" />트위터</a></dt>
                    </dl>
                </div>

            </div>
            <!--각 기능버튼 끝-->
        </div>
    </div>

	<!-- 현재 사이트 내용 그대로 들어감 //-->

	<!-- course-detail-tabs -->
	<div class="course-detail-tabs">
		<!-- header -->
		<div class="header">
			<ul>
				<li class="active"><a href="#">과정정보<i></i></a></li>
				
				<li><a href="#">과정후기<i></i></a></li>
			</ul>
		</div>
		<!-- header //-->
		<!-- 과정정보 시작 //-->
		<div class="body">

			<p class="tit1"><img src="http://edu.kocca.or.kr/images/2013/online_edu/online_tit_5.gif" alt="수강안내" /></p><br/>
            <ul>
                <li>- 교육비    : 무료</li>
                <li>- 교육문의 : 온라인 교육 운영 담당자: 02-2161-0077 academy@kocca.kr</li>
                <li>- 수료정보 : 진도율 60% 이상</li>
            </ul>
            <br/>
            <p class="tit1"><img src="http://edu.kocca.or.kr/images/2013/online_edu/online_tit_1.gif" alt="교육목표" /></p><br/>
            <ul>
                <li><%= subjectPreview.getHtmlString("d_intro") %></li>
            </ul>
            <br/>
            <p class="tit1"><img src="http://edu.kocca.or.kr/images/2013/online_edu/online_tit_2.gif" alt="교육내용" /></p><br/>
            <ul>
                <li><%= subjectPreview.getHtmlString("d_explain") %></li>
            </ul>
            <br/>
            <p class="tit1"><img src="http://edu.kocca.or.kr/images/2013/online_edu/online_tit_3.gif" alt="강의참고" /></p><br/>
            <ul>
                <li><%= subjectPreview.getHtmlString("d_memo").equals("")? "없음":subjectPreview.getHtmlString("d_memo") %></li>
            </ul>
            <br/>
            <p class="tit2"><img src="http://edu.kocca.or.kr/images/2013/online_edu/online_tit_4.gif" alt="차시정보" /></p>
            <div class="tableWrap10 space2">
                <table class="dataTable10" summary="이 표는 차시정보 표로 차시,강의주제 내용으로 구성 되어 있습니다.">
                    <caption>차시정보 표</caption>
                    <colgroup>
                        <col width="12%"/>
                        <col width="*"/>
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">차시</th>
                            <th scope="col">강의주제</th>
                        </tr>
                    </thead>
                    <tbody>
<%
                            for (DataBox lessonListDataBox : lessonList) {
%>
                    <tr>
                        <th scope="row"><%= lessonListDataBox.get("d_lesson")  %></th>
                        <td><%= lessonListDataBox.get("d_sdesc")  %></td>
                    </tr>
<%
                            }
%>
                    </tbody>
                </table>
            </div>

            <!-- 다음과정 시작 //-->
            <p class="tit2"><img src="/images/2013/online_edu/online_tit_7.gif" style="margin-top:20px;" alt="다음과정" /></p>
<%
         // 다음과정 리스트
         DataBox dnbox = null;
         if(nextSubjList != null && nextSubjList.size() >0) {
%>
            <div class="movList2">
<%
            for (int i=0 ; i < nextSubjList.size() ; i++) {
                dnbox = (DataBox)nextSubjList.get(i);
                String mobileuseYn= dnbox.getString("d_mobile_use_yn");
                imgurl = dnbox.getString("d_introducefilenamenew");
                imgurl = imgurl.replaceAll("\\\\", "/");
%>
                <dl style="height:80px">
                    <dt>
                        <img src="<%= imgurl %>" width="126" height="80" alt="<%= dnbox.getString("d_subjnm") %>" />
                    </dt>
                    <dd class="title"><a href="javascript:fnViewSubjInfo('<%=dnbox.get("d_subj") %>', '<%=dnbox.get("d_subjnm") %>', '<%=dnbox.get("d_isonoff") %>', '<%=dnbox.get("d_scupperclass") %>', '<%=dnbox.get("d_uclassnm") %>', '<%=dnbox.get("d_year") %>', '<%=dnbox.get("d_subjseq") %>')"><%= dnbox.getString("d_subjnm") %></a>

                        <span class="img_mobile" style="float:right; margin-top:-6px;">
<%
                if(mobileuseYn.equals("Y")){
%>
                            <img src="/images/2013/online_edu/detail_mbile.png" style="float:right; margin-right:5px;" alt="모바일"/>
<%
                }
%>
                        </span>

                    </dd>

                    <dd class="txt"><%=dnbox.getString("d_intro") %></dd>
                        <div class="mvLayer" id="CK10071"></div>
                    </dd>
                </dl>
<%
            }
%>
            </div>
<%
        } else {
%>
            <div class="movList2" style="min-height:36px;padding: 20px 0 0 32px;">
                <span style="font-size: 14px; font-weight: bold;">다음 과정이 없습니다.</span>
            </div>
<%
        }
%>
		</div>
		<!-- 과정정보 끝 //-->


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
            DataBox dbox = null;
            String regUserId = "";
            int point = 0;
            String starRate = "";
            for ( int i = 0 ; i < reviewList.size(); i++ ) {
                dbox = (DataBox)reviewList.get(i);
                regUserId = dbox.getString("d_userid");

                point = dbox.getInt("d_point");
                starRate = "";
                for ( int k = 1; k <= point; k++ ) {
                    starRate += "★";
                }
                for ( int j = 1; j <= 5 - point; j++ ) {
                    starRate += "☆";
                }
%>
                    <tr id="oReviewTitle<%= i %>" name="reviewTitle">
                        <th scope="row"><%= dbox.getString("d_dispNum") %></th>
                        <td>
                            <div style="width:420px; height:16px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
                                <a href="javascript:fnViewReivewContents(<%= i %>);" ><%= dbox.getString("d_contents") %></a>
                            </div>
                        </td>
                        <td><%= starRate %></td>
                        <td style="text-align:center;"><%= dbox.getString("d_name") %></td>
                        <td><%= dbox.getString("d_reg_dt") %></td>
                    </tr>
                    <tr id="oReviewContents<%= i %>" name="reviewContents" style="display:none;">
                        <td colspan="5" style="border-left:0; padding-left:90px;">
                            <%= dbox.getString("d_contents").replaceAll("\\n", "<br/>") %>
                            <input type="hidden" id="oContentsText<%= i %>" name="contentsText<%= i %>" value="<%= dbox.getString("d_contents") %>" />

<%
                if ( loginUserId != null && !loginUserId.equals("") && loginUserId.equals(regUserId) ) {
                    isMyReviewReg = true;
%>
                            <div style="padding:10px 0 0 0; text-align:right;">
                                <a href="javascript:fnViewModifyPopup(<%= dbox.getInt("d_num") %>, <%= point %>, <%= i %>);" ><img src="/images/2013/online_edu/btn_write_modify.gif" alt="수정" /></a>
                                <a href="javascript:fnDeleteReview(<%= dbox.getInt("d_num") %>, <%= i %>);"><img src="/images/2013/online_edu/btn_delete.gif" alt="삭제" /></a>
                            </div>
<%
                }
%>
                        </td>
                    </tr>
<%
                totalRowCount = dbox.getInt("d_tot_cnt");
                totalPage = dbox.getInt("d_totalPage");
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

	</div>
	<!-- /course-detail-tabs -->
<!-- /container-new -->
</div>

<form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
    <!--input type="hidden" name="type" value="<%//=type%>"/-->
    <input type="hidden" name="p_process" value="<%= box.get("p_process") %>"/>
    <input type="hidden" name="p_rprocess" value="<%= box.getStringDefault("p_rprocess", box.get("p_process"))%>"/>
    <input type="hidden" name="p_actionurl" value="<%= box.get("p_actionurl") %>"/>
    <input type="hidden" name="p_year" value=""/>
    <input type="hidden" id="oSubj" name="p_subj" value="<%= box.get("p_subj") %>"/>
    <input type="hidden" name="subj" value="<%= box.get("p_subj") %>"/>
    <input type="hidden" name="p_subjseq" value="<%= box.get("p_subjseq") %>"/>
    <input type="hidden" name="p_subjnm" value="<%= box.get("p_subjnm") %>"/>
    <input type="hidden" name="p_upperclass" value="<%= box.get("p_upperclass") %>"/>
    <input type="hidden" name="p_upperclassnm" value="<%= box.get("p_upperclassnm") %>"/>
    <input type="hidden" id="s_mclassnm" name="s_mclassnm"  value="<%= box.get("s_mclassnm") %>"/>
    <input type="hidden" name="p_action" value="<%= p_action %>"/>
    <input type="hidden" name="p_pageno" value="<%= pageNo %>"/>
    <input type="hidden" name="p_pagesize" value="<%= pageSize %>"/>
    <input type="hidden" name="p_favoryn" value="<%= subjectPreview.getString("d_favor_yn") %>"/>
    <input type="text" name="p_biyong" value="<%= biyong %>"/>
    <input type="hidden" name="lessonReurl" value="" />
    <input type="hidden" name="lessonReiurl" value="" /> 
</form>

<!-- 후기 등록 모달 팝업 시작 //-->
<form id="oReviewRegForm" name="reviewRegForm">
    <input type="hidden" name="process" value="" />
    <input type="hidden" name="subj" value="<%= box.get("p_subj") %>" />
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
				<label><input type="radio" id="oPoint5" name="point" value="5" /> <img src="/images/common/icon-star5.png" alt="평가점수 5점"></label>
				<label><input type="radio" id="oPoint4" name="point" value="4" /> <img src="/images/common/icon-star4.png" alt="평가점수 4점"></label>
				<label><input type="radio" id="oPoint3" name="point" value="3" /> <img src="/images/common/icon-star3.png" alt="평가점수 3점"></label>
				<label><input type="radio" id="oPoint2" name="point" value="2" /> <img src="/images/common/icon-star2.png" alt="평가점수 2점"></label>
				<label><input type="radio" id="oPoint1" name="point" value="1" /> <img src="/images/common/icon-star1.png" alt="평가점수 1점"></label>
			</div>
			<textarea class="modal-review-textarea" placeholder="최대 500byte까지 작성가능하며, 욕설이나 비방글은 삭제될 수 있습니다." maxlength="500" id="oContents" name="contents"></textarea>
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