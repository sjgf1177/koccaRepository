<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process    = box.getString("p_process");
    int     v_tabseq     = box.getInt   ("p_tabseq");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");


    int v_seq      = box.getInt("p_seq");

    String  v_date          = "";
    String  v_title         = "";
    String  v_content       = "";
    String  v_name          = "";
    String  v_flagYn        = "";
    String  v_position      = "";
    String  v_url           = "";
    int     v_cnt           =  0;
    DataBox dbox = (DataBox)request.getAttribute("selectView");

    if (dbox != null) {
        v_title     = dbox.getString("d_title");
        v_content   = dbox.getString("d_content");
        v_date      = FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy년 MM월 dd일");
        v_name      = dbox.getString("d_name");
        v_flagYn    = dbox.getString("d_flagyn");
        v_position  = dbox.getString("d_position");
        v_url       = dbox.getString("d_url");
        v_cnt       = dbox.getInt("d_cnt");
     }

    box.put("title_str", "보도자료, 아카데미소개");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">

    function listnotice(){
        document.nform2.p_process.value = "selectList";
        document.nform2.action = "/servlet/controller.infomation.BriefingHomePageServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->

<form name="nform2" method="post">
    <input type = "hidden" name = "p_process" >
    <input type = "hidden" name = "p_seq" value="<%=v_seq%>">
    <input type = "hidden" name = "p_tabseq" value="<%=v_tabseq%>">
    <input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_search" value = "<%=v_search %>">
</form>

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <div id="mainMenu">
                <!-- 아카데미 소개 -->
                <ul class="location">
                    <li>홈</li>
                    <li>아카데미소개</li>
                    <li>
                        보도자료 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                        <ul style="z-index: 1;">
                            <!-- 활성화된 메뉴에  class="active" 삽입 -->
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
<!-- 2015-01-08 개편삭제
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->

                        </ul>
                    </li>
                </ul>
                <div class="introCategory">
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList"><strong><u>보도자료</u></strong></a></li>
                    </ul>
                </div>
            </div>


            <h2 class="subTit"><img src="/images/2013/introduce/bodo_title.gif" alt="보도자료" /></h2>

            <div id="subCont">
                <div class="article">
                    <div class="info">
                        <p class="title"><%=v_title%></p>
                        <p class="date"><%=v_date %></p>
                    </div>

                    <div class="inner">
                        <%=StringManager.removeHTMLHead(v_content)%>
                        <%
                        Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                        Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                        String i_boardStyle        = "board_view";                                // 보드 스타일
                        %>
                        <%@ include file="/learn/user/include/zu_MultiAttach_2012_R.jsp" %>
                    </div>
                </div>
                <!-- //article -->

                <!--table class="bbsNavi">
                    <colgroup>
                        <col width="70">
                        <col>
                        <col width="70">
                        <col width="70">
                    </colgroup>
                    <tr>
                        <td class="pre">이전글</td>
                        <td><img src="/images/2013/common/ico_info.gif" alt="정보" /> <a href="my_class_2.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                        <td>작성자</td>
                        <td>2013.06.18</td>
                    </tr>
                    <tr>
                        <td class="next">다음글</td>
                        <td><img src="/images/2013/common/ico_question2.gif" alt="질문" /> <a href="my_class_2.html">안산교육형, ‘재미있는 수업이야기’ 초등교사들 수업역량 강화... ‘호응’</a></td>
                        <td>홍길동</td>
                        <td>2013.06.18</td>
                    </tr>
                </table-->

                <div class="btnArea right">
                    <a href="javascript:listnotice();"><img src="/images/2013/btn/btn_list.gif" alt="목록" /></a>
                </div>
            </div>
            <!-- //#subCont -->




        </div>
        <!-- 컨텐츠 영역 끝 -->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>