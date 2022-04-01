<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="com.credu.homepage.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_process = box.getString("p_process");
    String v_searchtxt = box.getString("p_searchtxt");
    String v_faqcategory = "";
    v_faqcategory = box.getString("faqcategory");

    int v_fnum = 0, v_totalpage = 0, v_rowcount = 1;
    int v_pageno = box.getInt("p_pageno");
    String v_title = "";
    String v_contents = "";
    String v_indate = "";
    String v_searchtext = box.getString("p_searchtxt");
    ArrayList list = (ArrayList)request.getAttribute("selectList");
    String faqcategory ="";

    String full_toggle = "off";
    String sub1_toggle = "off";
    String sub2_toggle = "off";
    String sub3_toggle = "off";
    String sub4_toggle = "off";
    String sub5_toggle = "off";

    String categoryName = "";

    if ("0025".equals(v_faqcategory)) {
        sub1_toggle = "on";
        categoryName = "회원가입 및 탈퇴";
    } else if ("0034".equals(v_faqcategory)) {
        sub2_toggle = "on";
        categoryName = "수강신청";
    } else if ("0035".equals(v_faqcategory)) {
        sub3_toggle = "on";
        categoryName = "학습관련";
    } else if ("0036".equals(v_faqcategory)) {
        sub4_toggle = "on";
        categoryName = "평가";
    } else if ("0072".equals(v_faqcategory)) {
        sub5_toggle = "on";
        categoryName = "시스템";
    } else {
        full_toggle = "on";
        categoryName = "전체";
    }


    box.put("title_str", categoryName + " - 자주하는 질문 - 학습지원 - 고객센터");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script type="text/javascript">
//카테고리에 맞는 리스트 검색
function changeCategory(aa) {
        document.form1.faqcategory.value = aa;
        $("#p_searchtxt").val("");
        document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
}

function search_enter(e)  {
    if (e.keyCode =='13') {
        searchTxt();
    }
}

/**
 * 검색내용에 맞는 리스트 페이지로 이동
 */
function searchTxt() {
    /*
    if(document.form1.p_searchtxt.value ==""){
        alert("내용을 입력하세요");
        return false;

    } else {
        document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
    */
    document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();

}

$(function(){
    var
    o = $(".FAQ")
    ,a = o.find("dt a")
    ,c = o.find("dd");
    a.each(function(i){
        $(this).click(function(e){
            e.preventDefault();
            var
            that = $(this);
            obj = c.eq(i);
            if(obj.is(":hidden")){
                obj.show();
                that.addClass("on");
            }else{
                obj.hide();
                that.removeClass("on");
            }
        });
    });
    o.find(".unfold").click(function(e){
        e.preventDefault();
        c.show();
        a.addClass("on");
    });
    o.find(".fold").click(function(e){
        e.preventDefault();
        a.removeClass("on");
        c.hide();
    });
});

</script>
<!-- container-new 시작 //-->
<div id="container-new">

    <form name="form1" method="post" action="" onsubmit="return searchTxt">
        <input type="hidden" name="p_process" value="<%= v_process %>" />
        <input type="hidden" name="p_pageno" value="" />
        <input type="hidden" name="p_seq" value="" />
        <input type="hidden" name="p_userid" value="" />
        <input type="hidden" name="faqcategory" value="<%= v_faqcategory %>" />

        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>고객센터</li>
                <li> 자주하는 질문 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습지원</a></li>
<!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
-->
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList" class="active">자주하는 질문</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
<!--
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
-->
                    </ul>
                </li>
            </ul>

            <!-- //학습지원 -->
<!--
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList"><strong><u>FAQ</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
                </ul>
            </div>

-->            <!-- //서브메인용 -->
        </div>
        <!-- //#mainMenu -->

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
        <h2 class="subTit">자주하는 질문<!--<img src="/images/2013/support/faq02_title.gif" alt="자주하는질문" />//--></h2>

            <div id="subCont" class="FAQ">

                <ul class="tabCus">
                    <li><a href="javascript:changeCategory('ALL')"><img src="/images/2013/support/tabfaq1_<%=full_toggle%>.gif" alt="전체" /></a></li>
                    <li><a href="javascript:changeCategory('0025')"><img src="/images/2013/support/tabfaq2_<%=sub1_toggle%>.gif" alt="회원가입 및 탈퇴" /></a></li>
                    <li><a href="javascript:changeCategory('0034')"><img src="/images/2013/support/tabfaq3_<%=sub2_toggle%>.gif" alt="수강신청" /></a></li>
                    <li><a href="javascript:changeCategory('0035')"><img src="/images/2013/support/tabfaq4_<%=sub3_toggle%>.gif" alt="학습관련" /></a></li>
                    <li><a href="javascript:changeCategory('0036')"><img src="/images/2013/support/tabfaq5_<%=sub4_toggle%>.gif" alt="평가" /></a></li>
                    <li><a href="javascript:changeCategory('0072')"><img src="/images/2013/support/tabfaq6_<%=sub5_toggle%>.gif" alt="시스템" /></a></li>
                </ul>
                <h3 class="blind">전체 - 현재위치</h3>
                <!--  페이지 내 검색 -->
                <div class="search">
                    <fieldset>
                        <legend>통합 검색</legend>
                        <input type="text" name="p_searchtxt" value="<%=v_searchtext %>" title="검색어" class="keyword" />
                        <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" onclick="javascript:searchTxt();" />
                    </fieldset>
                </div>
                <!--  //페이지 내 검색 -->
                <p class="btnR">
                    <a href="#" class="unfold"><img src="/images/2013/btn/btn_all_unfold.gif" alt="전체 펼치기" /></a>
                    <a href="#" class="fold"><img src="/images/2013/btn/btn_all_fold.gif" alt="전체 닫기" /></a>
                </p>

                <dl class="faqList">
<%
        if(list.size() != 0){

            int i =0;
            for( i = 0; i < list.size(); i++) {

                DataBox dbox = (DataBox)list.get(i);

                v_title = dbox.getString("d_title");
                v_contents = dbox.getString("d_contents");
                v_totalpage = dbox.getInt("d_totalpage");
                v_rowcount = dbox.getInt("d_rowcount");

                if (!v_searchtext.equals("")) {
                    v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                    v_contents = StringManager.replace(v_contents, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                }
%>
                        <dt><a href="#"><%= v_title %></a></dt>
                        <dd><%= StringManager.removeHTMLHead(v_contents) %></dd>
<%
            }
        } else {
%>
                    <dt>등록된 내용이 없습니다.</dt>
<%
        }
%>
                </dl>
            </div>
            <!-- //#subCont -->

    </div>
    </form>
    <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>