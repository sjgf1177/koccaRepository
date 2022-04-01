<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_gubun_view = box.getString("p_gubun_view");
    String  v_gubun_txt = "";

    int v_tabseq   = box.getInt("p_tabseq");
    int v_seq      = box.getInt("p_seq");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
    String v_luserid   = "";
//    String v_gubun_view = "";
    String v_loginyn    = "";           // 로그인유무
    String v_useyn      = "";           // 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
    String v_onoffgubun = "";           // 온라인/오프라인 구분
    String v_onoff      = "";
    int v_popwidth   = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치

    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용

    DataBox dbox = (DataBox)request.getAttribute("selectNotice");

    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");
        v_adtitle   = dbox.getString("d_adtitle");
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");
        v_adname    = dbox.getString("d_adname");
        v_cnt       = dbox.getInt("d_cnt");
        v_onoff     = box.getString("p_onoffgubun");

        if(dbox.getStringDefault("d_onoffgubun","C").equals("C")){
            v_onoffgubun = "<img src=\"/images/2013/btn/icon_online.gif\" alt=\"온라인\">";
        }else{
            v_onoffgubun = "<img src=\"/images/2013/btn/icon_offline.gif\" alt=\"오프라인\">";
        }

        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");

     }
     v_onoffgubun="";

/*  2015-01-21 개편 삭제 사용안함.
    if (v_gubun.equals("Y")) v_gubun_view = "전체";
    else if (v_gubun.equals("N")) v_gubun_view = "일반";
    else if (v_gubun.equals("P")) v_gubun_view = "팝업";

*/
    if (v_gubun_view.equals("N")){
        v_gubun_txt = "공지";
    } else if (v_gubun_view.equals("G")){
        v_gubun_txt = "안내";
    } else if (v_gubun_view.equals("I")){
        v_gubun_txt = "정보";
    } 

    if (v_uselist.equals("")) v_uselist = "N";
    if (v_useframe.equals("")) v_useframe = "N";

    if(v_loginyn.equals("Y")) v_loginyn = "로그인후";
    else if(v_loginyn.equals("N")) v_loginyn = "로그인전";
    else v_loginyn = "전체";

    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";

    box.put("title_str", "공지사항 - 새소식");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- 스크립트영역 -->
<SCRIPT type="text/javascript">
    $(function() {
        $(document.body).css("font-family", "돋움,Dotum,AppleGothic,sans-serif").css("font-size", "12px");
    });


    function listnotice(){
        document.nform2.p_process.value = "List";
        document.nform2.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- container-new -->
<div id="container-new">

        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>새소식</li>
                <li>공지사항 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List" class="active">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list" >아카데미 이야기</a></li>
                        <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" >CMU 스토리</a></li>
                        <!-- 2015-01-07 개편삭제
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
                        -->
                    </ul>
                </li>
            </ul>
            <!-- //학습지원 -->
<!-- 2015-01-07 개편삭제
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
        <!-- form 시작 -->
        <form name="nform2" method="post" action="/">
            <input type="hidden" name="p_process" >
            <input type="hidden" name="p_pageno" value="<%=v_pageno %>">
            <input type="hidden" name="p_searchtext" value="<%=v_searchtext %>">
            <input type="hidden" name="p_search" value="<%=v_search %>">
            <input type="hidden" name="p_onoffgubun" value="<%=v_onoff%>" />
            <input type="hidden" id="choicetype" value="subject">
            <input type="hidden" name="p_gubun_view" value="<%=v_gubun_view%>">

            <h2 class="subTit"> 공지사항
            <!-- <img src="/images/2013/support/notice_title.gif" alt="공지사항" />
            //--></h2>

            <div id="subCont">
                <div class="noticBox">
                    신규개설과정, 오프라인 교육생 모집, 행사, 초청강연 등 다양한 서비스에 대한 정보를 신속하게 전해드립니다.
                </div>

                <div class="article">
                    <div class="info">
                        <p class="title"><%=v_onoffgubun %> <%=StringManager.removeHTML(v_adtitle)%></p>
                        <p class="date"><%=v_addate%></p>
                    </div>
                    <div class="inner">
                        <%=StringManager.removeHTMLHead(v_adcontent)%>
                        <%
                            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                            String i_boardStyle        = "board_view";                                // 보드 스타일
                        %>
                        <%if(i_realfileVector.size() != 0){ %>
                        <br/>
                        <%@ include file="/learn/user/include/zu_MultiAttach_2012_R.jsp" %>
                        <%} %>
                    </div>
                </div>

                <!--table class="bbsNavi">
                    <colgroup>
                        <col width="60">
                        <col>
                        <col width="70">
                    </colgroup>
                    <tr>
                        <td class="pre">이전글</td>
                        <td><img src="/images/2013/btn/icon_online.gif" alt="온라인" /><a href="#">코멘트를 달아봅니다</a></td>
                        <td>2013.06.18</td>
                    </tr>
                    <tr>
                        <td class="next">다음글</td>
                        <td><img src="/images/2013/btn/icon_offline.gif" alt="오프라인" /><a href="#">코멘트를 달아봅니다 코멘트를 달아봅니다 코멘트를 달아봅니다 코멘트를 달아봅니다</a></td>
                        <td>2013.06.18</td>
                    </tr>
                </table-->

                <div class="btnArea right">
                    <a href="javascript:listnotice();"><img src="/images/2013/btn/btn_list.gif" alt="목록" /></a>
                </div>

            </div>
            <!-- //#subCont -->
        </form>
        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new  끝-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>