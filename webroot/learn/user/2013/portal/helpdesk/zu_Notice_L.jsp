<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="com.credu.homepage.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.common.*" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list2 = null;

    int v_tabseq = box.getInt("p_tabseq");
    int v_seq = box.getInt("p_seq");
    String v_process  = box.getString("p_process");
    String v_onoff = box.getStringDefault("p_onoffgubun","ALL");
    String v_gubun_view = box.getString("p_gubun_view"); // 공지사항 구분

    // 검색
    String v_searchtext = box.getString("p_searchtext");
    String v_search = box.getString("p_search");

    int    v_cnt = 0;            // 조회수
    String v_gubun = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    String v_enddate = "";           // 팝업공지종료일
    String v_adtitle = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate = "";           // 등록일
    String v_adname = "";           // 등록자
    String v_luserid = "";
//    String v_gubun_view = "";
    String v_loginyn = "";           // 로그인유무
    String v_useyn = "";           // 사용유무
    String v_grcodecd = "";           // 대상 교육그룹
    String v_isall = "";           // 전체 공지 유무
    String v_onoffgubun = "";           // 온오프구분
    int v_popwidth = 0;               // 팝업가로
    int v_popheight = 0;               // 팝업세로
    int v_popxpos = 0;               // 팝업x위치
    int v_popypos = 0;               // 팝업y위치

    String v_popup = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe = "";               // 프레임사용

    String v_upfile ="";
    //String v_realfile  ="";

    int v_dispnum = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount = 0;
    int v_upfilecnt = 0;
    int v_pageno = box.getInt("p_pageno");
    int v_pagesize = box.getInt("p_pagesize");

    int  v_totalrowcount =  0;

    String s_userid = box.getSession("userid");
    String s_username = box.getSession("name");

    list2 = (ArrayList)request.getAttribute("selectNoticeList");

    String left_menu = "helpdesk";

    box.put("title_str", "공지사항 - 새소식");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<!-- 스크립트영역시작 -->
<SCRIPT type="text/javascript" LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    function pagesize(pageSize) {
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_pageno.value = 1;
        document.nform1.p_pagesize.value = pageSize;
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    function search_enter(e)  {
        if (e.keyCode =='13'){  select();  }
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

//-->
</SCRIPT>
<!-- container-new -->
<div id="container-new">

    <form name="nform1" method="post" action="/" onsubmit="javascript:select();">
    <input type="hidden" name="p_process" />
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>" />
    <input type="hidden" name="p_seq"    />
    <input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>" />

        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>새소식</li>
                <li>
                    공지사항 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List" class="active">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list" >아카데미 이야기</a></li>
                        <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" >CMU 스토리</a></li>
                    <!-- 2015-01-09 개편변경
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
                    -->
                    </ul>
                </li>
            </ul>

            <!-- //학습지원 -->
<!--2015-01-07 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List"><strong><u>공지사항</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
                </ul>
            </div>

-->
            <!-- //서브메인용 -->
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
        <h2 class="subTit">공지사항
            <!-- <img src="/images/2013/support/notice_title.gif" alt="공지사항" />
            //--></h2>

            <div id="subCont">
                <div class="noticBox">
                    신규개설과정, 오프라인 교육생 모집, 행사, 초청강연 등 다양한 서비스에 대한 정보를 신속하게 전해드립니다.
                </div>
                <!--  페이지 내 검색 -->
                <div class="search">
                    <fieldset>
                        <legend>통합 검색</legend>
                        <label for="oTotalSearch" style="display:none;">통합 검색</label>
                        <select id="oTotalSearch" name="p_search" title="검색 분류">
                            <option value="adtitle" <%=v_search.equals("adtitle")?"selected":"" %>>제목</option>
                            <option value="adcontent" <%=v_search.equals("adcontent")?"selected":"" %>>내용</option>
                        </select>
                        <input type="text" name="p_searchtext" value="<%=v_searchtext %>" title="검색어" class="keyword" onkeypress="search_enter(event);" />
                        <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" onclick="javascript:select();" />
                    </fieldset>
                </div>

                <div class="tableWrap2 space5">
                    <table class="board" summary="이표는 공지사항 표로 전체, 제목, 등록일, 조회 순으로 구성되어있습니다.">
                        <caption>공지사항 리스트</caption>
                        <colgroup>
                            <col width="6%"/>
                            <col width="10%"/>
                            <col width="*"/>
                            <col width="10%"/>
                            <col width="7%"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col"><label for="oOnOffGubun">번호</label></th>
                            <th scope="col">
                                <select class="" id="oGubun" name="p_gubun_view" onchange="javascript:select();">
                                    <option value="" <%if(v_gubun_view.equals("")){%>selected<%}%>>전체</option>
                                    <option value="N" <%if(v_gubun_view.equals("N")){%>selected<%}%>>공지</option>
                                    <option value="G" <%if(v_gubun_view.equals("G")){%>selected<%}%>>안내</option>
                                    <option value="I" <%if(v_gubun_view.equals("I")){%>selected<%}%>>정보</option>
                                <!-- 2015-01-21 개편 변경
                                <select class="" id="oOnOffGubun" name="p_onoffgubun" onchange="javascript:select();">
                                    <option value="ALL" <%if(v_onoff.equals("ALL")){%>selected<%}%>>전체</option>
                                    <option value="ON" <%if(v_onoff.equals("ON")){%>selected<%}%>>온라인</option>
                                    <option value="OFF" <%if(v_onoff.equals("OFF")){%>selected<%}%>>현장</option>
                                </select>
                                -->
                                </select>
                            </th>
                            <th scope="col">제목</th>
                            <th scope="col">등록일</th>
                            <th scope="col">조회</th>
                        </tr>
                        </thead>
                        <tbody>
<%
                        if(list2.size() != 0){
                            for( int i = 0; i < list2.size(); i++) {
                                DataBox dbox = (DataBox)list2.get(i);
                                v_seq      = dbox.getInt("d_seq");
                                v_cnt      = dbox.getInt("d_cnt");
                                v_adtitle  = dbox.getString("d_adtitle");
                                v_addate   = dbox.getString("d_addate");
                                v_adname   = dbox.getString("d_adname");
                                v_upfilecnt = dbox.getInt("d_filecnt");
                                v_dispnum   = dbox.getInt("d_dispnum");
                                v_totalpage = dbox.getInt("d_totalpage");
                                v_totalrowcount = dbox.getInt("d_totalrowcount");
                                v_rowcount  = dbox.getInt("d_rowcount");
                                v_isall     = dbox.getString("d_isall");
                                v_onoffgubun = dbox.getString("d_onoffgubun");
                                v_gubun =dbox.getString("d_gubun");
                                String txtGubun = "";

                                if( v_onoffgubun.equals("C") ) {
                                    v_onoffgubun = "<img src=\"/images/2013/btn/icon_online.gif\" alt=\"온라인\">";
                                } else {
                                    v_onoffgubun = "<img src=\"/images/2013/btn/icon_offline.gif\" alt=\"오프라인\">";
                                }
                                
                                if(v_gubun.equals("G")){
                                    txtGubun = "안내";
                                } else if(v_gubun.equals("I")){
                                    txtGubun = "정보";
                                } else {
                                    txtGubun = "공지";
                                }

                                v_addate = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");
%>
                        <tr>
                            <td class="first"><%=v_dispnum%></td>
                            <td class="bldnone"><%=txtGubun %></td>
                            <td class="bldnone left"><a href="javascript:selectView(<%=v_seq%>);"><%=StringManager.removeHTML(v_adtitle)%></a></td>
                            <td><%=v_addate%></td>
                            <td><%=v_cnt%></td>
                        </tr>
<% 
                            } 
                        } else {
%>
                        <tr>
                            <td colspan="5">등록된 게시물이 없습니다.</td>
                        </tr>
<% 
                        }
%>
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap2 -->

                <div class="paging">
                    <%= PageUtil.re2013_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
                </div>
                <!-- //paging -->
            </div>
            <!-- //#subCont -->
    </div>
    <!-- 컨텐츠 영역 끝 -->
    </form>
</div>
<!--  //container-new -->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>