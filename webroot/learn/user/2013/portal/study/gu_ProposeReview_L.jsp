<%
//**********************************************************
//  1. 제      목:
//  2. 프로그램명:
//  3. 개      요:
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList reviewList = null;
    reviewList = (ArrayList)request.getAttribute("reviewList");   //수강중인 과정 리스트

    String subj = "";
    String subjNm = "";
    String v_appdate = "";
    String contents = "";
    String areaNm = "";
    String reviewYn = "";
    int point = 0;
    int num = 0;

    int pageNo = ( box.getInt("p_pageno") == 0 ) ? 1 : box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int pageSize = ( box.getInt("p_pagesize") == 0 ) ? 10 : box.getInt("p_pagesize");
    int totalRowCount = 0;
    int totalPage = 0;
    int dispNum = 0;
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script type="text/javascript">

    $(function(){
        $('#sample').modal({show:false});
        //$('[data-dismiss="modal"]').trigger('click');
    });

    /**
     * 후기 목록에서 페이지 이동 처리를 한다.
     * ajax를 이용하여 새로운 목록을 조회하고 하단의 페이지 기능도 처리한다.
     */
    function goPage( pageNum ) {
        $("#oReviewManageForm input:hidden[name=page]").val(pageNum);
        var param = "p_process=selectReviewListForAjax&p_pageno=" + pageNum;

        var reviewListStr = "";
        var pagingStr = "";
        var idx = 0;
        var point = 0;

        var pageNo = 0;
        var pageSize = 3;
        var startPage = 0;
        var endPage = 0;
        var blockSize = 0;
        var pageBlock = 0;
        var blockCount = 0;

        // $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.study.ProposeReviewServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {

                    var currCnt = data.reviewList.length;
                    var k = 0;

                    // 후기 목록 표시
                    if ( currCnt > 0 ) {
                        $("tr[name=reviewContents]").remove();

                        $.each( data.reviewList, function(){

                            point = Number(this.d_point);

                            reviewListStr += "<tr id=\"oReviewContents" + idx + "\" name=\"reviewContents\">\n";
                            reviewListStr += "  <td class=\"first\">" + this.d_dispnum + "</td>\n";
                            reviewListStr += "  <td>" + this.d_codenm + "</td>\n";
                            reviewListStr += "  <td>" + this.d_subjnm + "</td>\n";
                            reviewListStr += "  <td>\n";

                            if ( this.d_review_yn == "N") {
                                reviewListStr += "      <span class=\"btnBlue\"><a href=\"javascript:fnManageReview('" + this.d_subj + "', " + this.d_num + ", " + this.d_point + ", 'R');\">등록</a></span>\n";

                            } else {
                                reviewListStr += "      <span class=\"btnPink\"><a href=\"javascript:fnManageReview('" + this.d_subj + "', " + this.d_num + ", " + this.d_point + ", 'M');\">수정</a></span>\n";
                                reviewListStr += "      <input type=\"hidden\" id=\"oContentsText_" + this.d_subj + "\" name=\"contentsText\" value=\"" + this.d_contents + "\" />\n";
                            }
                            reviewListStr += "  </td>\n";
                            reviewListStr += "</tr>\n";

                            $("#oReviewTable tbody").append(reviewListStr);
                            reviewListStr = "";
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

                        $("#oPagingArea").empty();
                        $("#oPagingArea").append(pagingStr);
                        $("#oPagingArea").css("display", "block");

                    } else {
                        reviewListStr = "";
                        reviewListStr += "<tr>\n";
                        reviewListStr += "    <td colspan=\"4\" style=\"border-left: none;\">수강신청한 이력이 없습니다.</td>\n";
                        reviewListStr += "</tr>\n";

                        $("tr[name=reviewTitle]").remove();
                        $("tr[name=reviewContents]").remove();
                        $("#oReviewTable tbody").append(reviewListStr);
                        $("#oPagingArea").css("display", "none");
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
     * 후기 등록/수정 팝업을 연다
     */
    function fnManageReview(subj, num, point, jobType) {
        $("#oReviewManageForm input:hidden[name=subj]").val(subj);
        $("#oReviewManageForm input:hidden[name=num]").val(num);
        $("#oReviewManageForm input:radio[name=point]").removeAttr("checked");
        $("#oReviewManageForm textarea[name=contents]").val("");

        if ( jobType == "R" ) {
            $(".modal-content .modal-btn").eq(0).text("등록").attr("onclick", "fnRegisterReview();");
            $(".modal-content .modal-btn").eq(1).text("취소").attr("onclick", "fnCloseRegisterPopup();");

        } else {
            $(".modal-content .modal-btn").eq(0).text("수정").attr("onclick", "fnModifyReview();");
            $(".modal-content .modal-btn").eq(1).text("삭제").attr("onclick", "fnDeleteReview();");
            $("#oReviewManageForm input:radio[name=point][value=" + point + "]").prop("checked", true);
            $("#oReviewManageForm textarea[name=contents]").val( $("#oContentsText_" + subj).val() );
        }

        $("[data-toggle=\"modal\"]").trigger("click");
    }

    /**
     * 후기를 등록한다.
     */
    function fnRegisterReview(idx) {

        if ( $("#oReviewManageForm input:radio[name=point]:checked").length == 0 ) {
            alert("평점을 선택해주세요.");
            $("#oReviewManageForm input:radio[name=point]").eq(0).focus();
            return;

        } else if( $("#oReviewManageForm textarea[name=contents]").val() == "") {
            alert("후기 내용을 입력하세요.");
            $("#oReviewManageForm textarea[name=contents]").focus();
            return;

        } else if (confirm("후기를 등록하시겠습니까?")) {

            $("#oReviewManageForm input[name=process]").val("registerReview");
            var param = $("#oReviewManageForm").serialize();

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $("[data-dismiss=\"modal\"]").trigger("click");

                        if ( data.resultCnt > 0 ) {
                            var pageNo = $("#oReviewManageForm input:hidden[name=page]").val();
                            alert("후기가 등록되었습니다.");
                            goPage(pageNo);
                            $("#oReviewManageForm textarea[name=contents]").val("");
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
     * 후기를 수정한다.
     */
    function fnModifyReview() {

        if ( $("#oReviewManageForm input:radio[name=point]:checked").length == 0 ) {
            alert("평점을 선택해주세요.");
            $("#oReviewManageForm input:radio[name=point]").eq(0).focus();
            return;

        } else if( $("#oReviewManageForm textarea[name=contents]").val() == "") {
            alert("후기 내용을 입력하세요.");
            $("#oReviewManageForm textarea[name=contents]").focus();
            return;

        } else if (confirm("후기를 수정하시겠습니까?")) {

            $("#oReviewManageForm input[name=process]").val("modifyReview");
            var param = $("#oReviewManageForm").serialize();

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/servlet/controller.mobile.onlineclass.OnlineClassReviewServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        $("[data-dismiss=\"modal\"]").trigger("click");

                        if ( data.resultCnt > 0 ) {
                            var pageNo = $("#oReviewManageForm input:hidden[name=page]").val();
                            alert("후기가 수정되었습니다.");
                            goPage(pageNo);
                            $("#oReviewManageForm textarea[name=contents]").val("");
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
     * 후기를 삭제한다.
     */
    function fnDeleteReview(idx) {
        $("#oReviewManageForm input:hidden[name=process]").val("deleteReview");

        var param = $("#oReviewManageForm").serialize();

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
                            var pageNo = $("#oReviewManageForm input:hidden[name=page]").val();
                            goPage(pageNo);
                            alert("후기가 삭제되었습니다.");
                            $("#oReviewManageForm textarea[name=contents]").val("");
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
     * 후기를 삭제한다.
     */
    function fnCloseRegisterPopup() {
        $("[data-dismiss=\"modal\"]").trigger("click");
    }

</script>
<!-- container-new 시작 //-->
<div id="container-new">
    <!-- 온라인 교육 -->
    <div id="mainMenu">
        <ul class="location">
            <li>개인메뉴</li>
            <li>온라인 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" class="active">온라인 강의실</a></li>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육 강의실</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" >내가작성한 글</a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                    <!-- 2015-01-09 개편 삭제
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                    <li><a href="#">커뮤니티</a></li>
                    //-->
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
        <div class="introCategory">
            <ul>
                <!-- <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><strong><u>나의 강의실</u></strong></a></li> //-->
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList" >찜목록</a></li>
                <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew" >설문</a></li>
                <li><a href="/servlet/controller.study.ProposeReviewServlet?p_process=selectReviewList" ><strong><u>과정후기</u></strong></a></li>
                <!-- 2015-01-09 개편 변경
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                //-->
            </ul>
        </div>
        <!-- //서브메인용 -->
        <!-- //온라인 교육 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">과정후기</h2>

        <div id="subCont" class="myClass">

            <div class="tableWrap2 noclose">
                <table id="oReviewTable" class="layoutTable3 space3" summary="이표는 나의 강의실 과정후기 표로 번호, 분류, 과정명, 후기 관리 버튼으로 구성되어 있습니다 ">
                    <caption>후기 등록 가능한 과정 목록</caption>
                    <colgroup>
                        <col width="80"/>
                        <col width="13%"/>
                        <col width="65%"/>
                        <col width="106"/>
                        <col width="106"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col">번호</th>
                        <th scope="col">분류</th>
                        <th scope="col">과정명</th>
                        <th scope="col">후기</th>
                    </tr>
                    </thead>
                    <tbody>

<%
        if(reviewList.size() != 0){
            DataBox dbox = null;
            for(int i = 0; i < reviewList.size(); i++) {
                dbox = (DataBox)reviewList.get(i);
                subj = dbox.getString("d_subj");
                subjNm = dbox.getString("d_subjnm");
                // v_appdate = dbox.getString("d_appdate");
                // v_appdate = FormatDate.getFormatDate(v_appdate,"yyyy.MM.dd");
                num = dbox.getInt("d_num");
                point = dbox.getInt("d_point");
                contents = dbox.getString("d_contents");
                areaNm = dbox.getString("d_codenm");
                reviewYn = dbox.getString("d_review_yn");

                dispNum = dbox.getInt("d_dispNum");
                totalRowCount = dbox.getInt("d_tot_cnt");
                totalPage = dbox.getInt("d_totalPage");
%>
                        <tr id="oReviewContents<%= i %>" name="reviewContents">
                            <td class="first"><%= dispNum %></td>
                            <td><%= areaNm %></td>
                            <td><%= subjNm %></td>
                            <td>
<%
        if ( reviewYn.equals("N") ) {
%>
                                <span class="btnBlue"><a href="javascript:fnManageReview('<%= subj %>', <%= num %>, <%= point %>, 'R');">등록</a></span>
<%
        } else {
%>
                                <span class="btnPink"><a href="javascript:fnManageReview('<%= subj %>', <%= num %>, <%= point %>, 'M');">수정</a></span>
                                <input type="hidden" id="oContentsText_<%= subj %>" name="contentsText" value="<%= contents %>" />
<%
        }
%>
                            </td>
                        </tr>
<%
            }
        } else {
%>
                        <tr>
                            <td colspan="4" style="border-left: none;">수강신청한 이력이 없습니다.</td>
                        </tr>
<%
        }
%>
                    </tbody>
                </table>
            </div>
            <!-- //tableWrap2 -->
            <div class="paging" id="oPagingArea">
                <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 5, pageSize, totalRowCount) %>
            </div>
            <!-- //paging -->
        </div>
        <!-- //#subCont -->
    </div>
</div>
<!--  //container-new 끝 //-->

<div data-toggle="modal" data-target="#sample" style="display:none;"></div>

<!--  // 모달 팝업 시작//-->
<form id="oReviewManageForm" name="reviewManageForm">
    <input type="hidden" name="process" value="" />
    <input type="hidden" name="subj" value="" />
    <input type="hidden" name="num" value="" />
    <input type="hidden" name="page" value="" />
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
                <button type="button" class="modal-btn modal-btn-default" onclick="">확인</button>
                <button type="button" class="modal-btn modal-btn-default" onclick="">취소</button>
            </div>
        </div>
    </div>
</div>
</form>
<!--  // 모달 팝업 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>
