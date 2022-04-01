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
    String  searchType = box.getString("searchType");
    String  searchKeyword = box.getString("searchKeyword");

    ArrayList<DataBox> tongBoardList = (ArrayList<DataBox>)request.getAttribute("tongBoardList");
    DataBox dbox = null;

    int pageNo = box.getInt("p_pageno");
    int pageSize = box.getInt("p_pagesize");
    int totalPage = 0;
    int totalRowCount = 0;

    pageNo = pageNo == 0 ? 1 : pageNo;
    pageSize = pageSize == 0 ? 10 : pageSize;

    box.put("title_str", "아카데미 이야기, 새소식");

    int seq = 0;
    int dispNum = 0;
    int viewCount = 0;

    String title = "";
    String newYn = "";
    String regDt = "";
    String regNm = "";
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<script type="text/javascript">

    /**
     * 내용 보기 화면으로 이동
     */
    function fnViewArticle(seq) {
        $("#oSeq").val(seq);
        $("#oProcess").val("view");
        $("#oTongBoardForm").attr("action", "/servlet/controller.homepage.TongBoardServlet");
        $("#oTongBoardForm").submit();
    }

    /**
     * 엔터키를 입력하면 게시판 검색
     */
    function fnSearchByKey(e)  {
        if (e.keyCode == "13") {
            goPage(1);
        }
    }

    /**
     * 게시물 검색
     */
    function goPage(pageNum) {
        $("#oPageNo").val( pageNum );
        $("#oProcess").val("list");
        $("#oTongBoardForm").attr("action", "/servlet/controller.homepage.TongBoardServlet");
        $("#oTongBoardForm").submit();
    }

</script>

<!-- container-new 시작 //-->
<div id="container-new">

    <form id="oTongBoardForm" name="TongBoardForm" method="get" action="">
    <input type="hidden" id="oProcess" name="p_process" value="" />
    <input type="hidden" id="oPageNo" name="p_pageno" value="<%= pageNo %>" />
    <input type="hidden" id="oPageSize" name="p_pagesize" value="<%= pageSize %>" />
    <input type="hidden" id="oSeq" name="seq" value="" />

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>새소식</li>
            <li>
                아카데미 이야기 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List" >공지사항</a></li>
                    <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list" class="active">아카데미 이야기</a></li>
                    <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" >CMU 스토리</a></li>
                    <!-- 2015-01-09 개편 변경
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                    //-->                    
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
        <!--
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><strong><u>커뮤니티</u></strong></a></li>
            </ul>
        </div>
        //-->
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">아카데미 이야기<!--<img src="/images/2013/online_edu/academy_title.gif" alt="아카데미이야기" />//--></h2>

        <div id="subCont">
            <!-- 2015-01-09 개편삭제
            <ul class="tabCus">
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/2013/online_edu/community_tab_1_off.gif" alt="전체" /></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=B0"><img src="/images/2013/online_edu/community_tab_2_off.gif" alt="방송영상" /></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=G0"><img src="/images/2013/online_edu/community_tab_3_off.gif" alt="게임개발" /></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=K0"><img src="/images/2013/online_edu/community_tab_4_off.gif" alt="문화콘텐츠" /></a></li>
                <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list"><img src="/images/2013/online_edu/community_tab_6_off.gif" alt="cmu 국비지원" /></a></li>
<%
        if ( !topWarp_userid.equals("") ) {
%>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE"><img src="/images/2013/online_edu/community_tab_5_off.gif" alt="내가 작성한 글" /></a></li>
<%
        }
%>

            </ul>
            //-->
            <h3 class="blind">전체 현재위치</h3>
            <!--
            <ul class="bbsType">
                <li><a href="community_list_webzine.html"><img src="/images/2013/common/ico_webzine.gif" alt="" /> 웹진형</a></li>
                <li class="on"><a href="community_list_bbs.html"><img src="/images/2013/common/ico_bbs.gif" alt="" /> 게시판형</a></li>
            </ul>
            //-->

            <!--  페이지 내 검색 -->
            <div class="search">
                <fieldset>
                    <legend>통합 검색</legend>
                    <select id="oSearchType" name="searchType" title="검색 분류">
                        <option value="title" <%= searchType.equals("title") ? "selected='selected'" : "" %>>제목</option>
                        <option value="cont" <%= searchType.equals("cont") ? "selected='selected'" : "" %>>내용</option>
                    </select>
                    <input type="text" id="oSearchKeyword" name="searchKeyword" value="<%= searchKeyword %>" title="검색어" class="keyword" onkeypress="fnSearchByKey(event);" />
                    <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" onclick="javascript:selectList();" />
                </fieldset>
            </div>
            <!-- 페이지 내 검색 끝//-->

            <div class="board2">
                <table summary="글번호, 분류, 제목, 과정, 작성자, 등록일 조회로 구성되어있습니다.">
                    <colgroup>
                        <col width="8%"/>
                        <col width="*"/>
                        <!-- <col width="8%"/> //-->
                        <col width="14%"/>
                        <!-- <col width="8%"/> //-->
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">
                            <div class="divide">
                                <!--
                                <select class="category" title="분류">
                                    <option>후기</option>
                                    <option>자료</option>
                                    <option>정보</option>
                                    <option>질문</option>
                                    <option>토론</option>
                                </select>
                                //-->
                                제목
                            </div>
                        </th>

                        <!-- <th scope="col">작성자</th> //-->
                        <th scope="col">등록일</th>
                        <!-- <th scope="col">조회</th> //-->
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 아이콘
                    <img src="/images/factory/ico_review.gif" alt="후기" />
                    <img src="/images/2013/common/ico_pds.gif" alt="자료" />
                    <img src="/images/2013/common/ico_info.gif" alt="정보" />
                    <img src="/images/2013/common/ico_question.gif" alt="질문" />
                    <img src="/images/2013/common/ico_discussion.gif" alt="토론" />
                    -->
<%
        if ( tongBoardList == null || tongBoardList.size() == 0 ) {
%>
                    <tr>
                        <td class="left bldnone" colspan="3" style="text-align:center;"><b>등록된 게시물이 없습니다.</b></td>
                    </tr>
<%
        } else {
            for ( int i = 0 ; i < tongBoardList.size(); i++) {
                dbox = (DataBox)tongBoardList.get(i);
                seq = dbox.getInt("d_seq");
                dispNum = dbox.getInt("d_dispnum");
                title = dbox.getString("d_title").replaceAll("\'\'", "\'");
                newYn = dbox.getString("d_new_yn");
                regDt = dbox.getString("d_reg_date");
                regNm = dbox.getString("d_reg_nm");
                viewCount = dbox.getInt("d_view_count");

                totalPage = dbox.getInt("d_totalPage");
                totalRowCount = dbox.getInt("d_totalRowCount");

%>
                    <tr>
                        <td class="first"><%= dispNum %></td>
                        <td class="left bldnone">
                            <a href="javascript:fnViewArticle(<%= seq %>);"><%= title %></a>
<%
                if ( newYn.equals("Y") ) {
%>
                            <img src="/images/2013/common/ico_new3.gif" alt="새로 올라온 글 표시" />
<%
                }
%>
                        </td>
                        <!-- <td><%= regNm %></td> //-->
                        <td><%= regDt %></td>
                        <!-- <td><%-- viewCount --%></td> //-->
                    </tr>
<%
            }
        }
%>
                    </tbody>
                </table>
                <!--
                <div class="btnArea right">
                    <a href="community_write.html"><img src="/images/2013/btn/bnt_write.gif" alt="글작성" /></a>
                </div>
                -->
            </div>
            <!-- //tableWrap2 -->

            <div class="paging pgSpace">
                <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 0, pageSize, totalRowCount) %>
            </div>

            <!-- //paging -->

        </div>
        <!-- //#subCont -->
    </div>
    <!-- //#contents -->

    </form>
    
</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
