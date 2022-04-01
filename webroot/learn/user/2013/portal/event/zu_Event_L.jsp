<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("topmenu","5");
    box.put("submenu","7");

    ArrayList list1 = null;
    ArrayList list2 = null;

    String v_process = box.getString("p_process");

    // 검색
    String v_searchtext = box.getString("p_searchtext");
    String v_search = box.getString("p_search");

    String v_selSeminarGubun = box.getString("p_selSeminarGubun");
    String p_periodyn = box.getString("p_periodyn");

    int v_pageno = box.getInt("p_pageno");
    int v_dispnum = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount = 0;
    int v_pagesize = box.getInt("p_pagesize");

    int v_seq = 0;              // 일련번호
    int v_cnt = 0;              // 조회수

    String v_title = "";           // 제목
    String v_content = "";           // 내용
    String v_indate = "";           // 등록일
    String v_name = "";           // 등록자
    String v_startdate = "";           // 이벤트 시작일
    String v_enddate = "";           // 이벤트 시작일
    String v_winneryn = "";           // 담청자 여부
    String v_isall = "";
    String v_status = "";
    String v_bannerimg = "";
    String v_save_bannerimg = "";
    String v_periodyn = "";
    String v_periodtxt = "";

    String s_userid = box.getSession("userid");
    String s_username = box.getSession("name");

    list1 = (ArrayList)request.getAttribute("selectList");

    box.put("title_str", "이벤트 - 새소식");

%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

    function viewPassContent(seq) {

        window.open("about:blank", "popUpView", "top=0, left=0, width=400, height=330, status=no, resizable=no, scrollbars=auto");

        document.nform1.action="/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectViewPass";
        document.nform1.p_seq.value = seq;
        document.nform1.target="popUpView";
        document.nform1.submit();

        document.nform1.target="_self";
    }

    function fnPeriodList(){
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();

    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();" action="">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>">
    <input type = "hidden" name = "p_seq"    >

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>새소식</li>
            <li>이벤트 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List" >공지사항</a></li>
                    <li><a href="/servlet/controller.homepage.EventHomePageServlet?p_process=selectList" class="active">이벤트</a></li>
                    <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list" >아카데미 이야기</a></li>
                    <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" >CMU 스토리</a></li>
                    <!-- 2015-01-09 개편삭제
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                    -->
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
    <div class="content">
        <h2 class="subTit">이벤트</h2>
        <div class="subCont">
            <div class="noticBox">
                콘텐츠아카데미에서 진행되는 다양한 이벤트 소식을 전해드립니다.
            </div>
            <div style="margin-left : 15px ; width: 49px;">
            <select name="p_periodyn" onchange="javascript:fnPeriodList();">
                <option value="">전체</option>
                <option value="Y" <%= p_periodyn.equals("Y")? "selected" : "" %>>진행중</option>
                <option value="P" <%= p_periodyn.equals("P")? "selected" : "" %>>예정</option>
                <option value="N" <%= p_periodyn.equals("N")? "selected" : "" %>>종료</option>
            </select>
            </div>
            <table class="eventlist" cellpadding="0" cellspacing="0" summary="현재 진행중인 이벤트의 목록입니다">
                <colgroup><col width="438px"><col width="438px"></colgroup>
                <tr>
                <%
                    if(list1.size() != 0){
                        for( int i = 0; i < list1.size(); i++) {
                            DataBox dbox = (DataBox)list1.get(i);

                            v_seq = dbox.getInt("d_seq");
                            v_cnt = dbox.getInt("d_cnt");
                            v_title = dbox.getString("d_title");
                            v_startdate = dbox.getString("d_strdate");
                            v_enddate = dbox.getString("d_enddate");
                            v_indate = dbox.getString("d_indate");
                            v_name = dbox.getString("d_name");
                            v_winneryn = dbox.getString("d_winneryn");
                            v_isall = dbox.getString("d_isall");

                            v_dispnum = dbox.getInt("d_dispnum");
                            v_totalpage = dbox.getInt("d_totalpage");
                            v_rowcount = dbox.getInt("d_rowcount");
                            v_bannerimg = dbox.getString("d_bannerimg");
                            v_save_bannerimg = dbox.getString("d_save_bannerimg");
                            v_periodyn = dbox.getString("d_periodyn");

                            if (v_periodyn.equals("Y")){
                                v_periodtxt = "진행중";
                            } else {
                                v_periodtxt = "종료";
                            }

                            if(v_winneryn.equals("C")){
                                v_winneryn = "미발표";
                                v_status = "완료";
                            } else if(v_winneryn.equals("Y")){
                                v_winneryn = "<a href='javascript:viewPassContent("+v_seq+")'><img src='/images/portal/homepage_renewal/library/btn_dok.jpg' width='90' height='21' alt='당첨자확인' /></a>";
                                v_status = "완료";
                            } else if(v_winneryn.equals("N")){
                                v_winneryn = "미발표";
                                v_status = "진행";
                            } else if(v_winneryn.equals("A")){
                                v_winneryn = "미발표";
                                v_status = "진행";
                            }


                            //if (!v_searchtext.equals("")&&v_search.equals("title")) {
                                /*
                            if (!v_searchtext.equals("")) {
                                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                            }
                                */
%>
                    <td style="padding:15px;" >
                        <a href="javascript:selectView(<%=v_seq%>)" tabindex="<%=i+180%>" title="<%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%> <%=v_title%>에 대한 이벤트 상세내용을 확인합니다">
                        <div>

                            <div>
                                <img style="border-style:solid; border-color:#dedede; border-width:1px;" width = "435px" height="140px" src="<%=v_save_bannerimg%>" alt="<%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%> <%=v_title%>">
                            </div>
                            <div style="padding-top: 4px; line-height: 20px;">
                            <b>&#183 제목 :</b> <%=StringManager.formatTitle(v_title,45)%> <br>
                            <b>&#183 기간 :</b> <%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%>
                            <span><img src="/images/2013/common/<%= v_periodyn.equals("Y") ? "event_01.png" : (v_periodyn.equals("N") ? "event_02.png" : "event_03.png") %>" style="margin-bottom: -3px;"></span>
                            </div>
                        </div>
                        </a>

                    </td>
<%
                            if ((i+1)%2 == 0) {
%>
                </tr>
                <tr>
<%
                            }
%>

<%                      }
                    } else if (list1.size() == 0) {
%>
                    <td colspan="4" style="padding: 32px 0 32px 12px; text-align:left; font-family:돋움; font-weight:bold; font-size:18px; ">
                        현재 진행중인 이벤트가 없습니다.
                    </td>
<%
                    }
%>
<%
                if (list1.size() % 2 == 1) {
%>
                    <th></th><td>&nbsp;</td>
<%
                }
%>
                </tr>
            </table>

            <div class="paging pgSpace">
                <%= PageUtil.re2013_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
            </div>

        </div>
    </div>
</form>
</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>

