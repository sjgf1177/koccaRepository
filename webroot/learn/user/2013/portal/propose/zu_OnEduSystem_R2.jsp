<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "교육체계도 - 온라인교육");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script type="text/javascript">
//과정 내용보기
function SubjInfo(subj){
    document.form1.p_subj.value = subj;
    document.form1.p_process.value = "previewSubjDetailPage";
    document.form1.p_rprocess.value = "EduSystem";
    document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
    document.form1.submit();
}

$(function(){
    currGuideUI();
});
function show_content(tar){
    var b = 6;
    var g = 5;
    var a = 6;
    var m = 4;

    for(i=1; i<=b; i++){
        $("#b"+i).attr("class","");
        $("#view_b"+i).attr("style","display:none;");
    }

    for(i=1; i<=g; i++){
        $("#g"+i).attr("class","");
        $("#view_g"+i).attr("style","display:none;");
    }

    for(i=1; i<=a; i++){
        $("#a"+i).attr("class","");
        $("#view_a"+i).attr("style","display:none;");
    }

    for(i=1; i<=m; i++){
        $("#m"+i).attr("class","");
        $("#view_m"+i).attr("style","display:none;");
    }

    $("#"+tar).attr("class","active");
    $("#view_"+tar).attr("style","");
}
</script>
<!-- container-new 시작 //-->
<div id="container-new">

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>온라인 교육</li>
            <li>교육체계도 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" >분야별</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup" >직업별</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem" class="active">교육체계도</a></li>
<%
        if ( currDate < 20160301 ) {
%>

                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList" >휴넷인문학</a></li>
<%
        }
%>
<!-- 2015-01-08 개편 변경
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
-->
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
<!-- 2015-01-08 개편 삭제
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><strong><u>과정안내</u></strong></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
            </ul>
        </div>
-->
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
    <form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
        <input type="hidden" name="p_process"   value="<%=box.get("p_process")%>">
        <input type="hidden" name="p_rprocess"  value="">
        <input type="hidden" name="p_subj"  value="">
<!--        <h2 class="subTit"><img src="/images/2013/field_edu/courseguide_title.gif" alt="과정체계도" /></h2>-->
        <h2 class="subTit">교육체계도<!--<img src="/images/2013/field_edu/courseguide_title.gif" alt="교육체계도" />//--></h2>
        <div id="subCont">
<!-- 2015-01-08 개편 삭제
            <ul class="tabCus">
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><img src="/images/2013/online_edu/tabcurs4_off.jpg" alt="과정별 소개" /></a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup"><img src="/images/2013/online_edu/tabcurs3_off.jpg" alt="작업별 분류" /></a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikmu"><img src="/images/2013/online_edu/tabcurs2_off.jpg" alt="직무별 분류" /></a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem"><img src="/images/2013/online_edu/tabcurs1_on.jpg" alt="교육체계도" /></a></li>
<%
        if ( box.getSession("isLiteratureSubjYn").equals("Y") ) {
%>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList"><img src="/images/2013/online_edu/tabcurs5_off.jpg" alt="휴넷인문학" /></a></li>
<%
        }
%>
            </ul>
-->

            <div class="noticBox">
                - 온라인과정의 교육체계도 및 추천 Track을 확인 하실 수 있습니다.<br/>
                - <strong>원하는 Track을 클릭</strong>하시면 세부내용을 확인 하실 수 있습니다.
            </div>


            <h3 class="blind">교육체계도 현재위치</h3>

            <div class="guideWrap" style="border:1px solid #d1d1d1; padding:25px; background:#F7F7F7;">
                <ul class="step">
                    <li><img src="/images/2013/online_edu/cisys_tit_1.gif" alt="기획" /></li>
                    <li class="s02"><img src="/images/2013/online_edu/cisys_tit_2.gif" alt="제작" /></li>
                    <li><img src="/images/2013/online_edu/cisys_tit_3.gif" alt="비즈니스" /></li>
                    <li><img src="/images/2013/online_edu/cisys_tit_4.gif" alt="교양" /></li>
                </ul>
                <div class="guideright">
                    <!-- 방송영상 -->
                    <div class="boxWrap">
                        <p class="tit"><img src="/images/2013/online_edu/tabcisys_tit_1.gif" alt="방송영상분야 역량강화를 위한 추천 트랙" /></p>

                        <div class="box01 on">
                            <a href="javascript:show_content('b1');">
                                <span class="top"></span>
                                <span class="txt first">방송영상 기획 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--// line1 -->

                        <div class="box01 type2">
                            <a href="javascript:show_content('b2');">
                                <span class="top"></span>
                                <span class="txt first">방송영상 연출 Track</span>
                            </a>
                            <a href="javascript:show_content('b3');"><span class="txt">방송영상 촬영 Track</span></a>
                            <a href="javascript:show_content('b4');">
                                <span class="txt">방송영상 편집 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line2 -->


                        <div class="box01">
                            <a href="javascript:show_content('b5');">
                                <span class="top"></span>
                                <span class="txt first">방송영상 비즈 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line3 -->

                        <div class="box01">
                            <a href="javascript:show_content('b6');">
                                <span class="top"></span>
                                <span class="txt first">방송영상 교양</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line4 -->
                    </div>
                    <!--// 방송영상 -->

                    <!-- 게임 -->
                    <div class="boxWrap">
                        <p class="tit"><img src="/images/2013/online_edu/tabcisys_tit_2.gif" alt="게임분야 역량강화를 위한 추천 트랙" /></p>

                        <div class="box02">
                            <a href="javascript:show_content('g1');">
                                <span class="top"></span>
                                <span class="txt first">게임 기획 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line1 -->

                        <div class="box02 type2">
                            <a href="javascript:show_content('g2');">
                                <span class="top"></span>
                                <div class="txt first hg2">게임 연출/그래픽/사운드<br /> Track</div>
                            </a>
                            <a href="javascript:show_content('g3');">
                                <span class="txt hg2">게임 프로그래밍 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line2 -->

                        <div class="box02">
                            <a href="javascript:show_content('g4');">
                                <span class="top"></span>
                                <span class="txt first">게임 비즈 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line3 -->

                        <div class="box02">
                            <a href="javascript:show_content('g5');">
                                <span class="top"></span>
                                <span class="txt first">게임 교양</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line4 -->
                    </div>
                    <!--// 게임 -->

                    <!-- 만애캐 -->
                    <div class="boxWrap">
                        <p class="tit"><img src="/images/2013/online_edu/tabcisys_tit_3.gif" alt="만화애니캐릭터분야 역량강화를 위한 추천 트랙" /></p>

                        <div class="box03">
                            <a href="javascript:show_content('a1');">
                                <span class="top"></span>
                                <span class="txt first">만화애니캐릭터 기획 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line1 -->

                        <div class="box03 type2">
                            <a href="javascript:show_content('a2');">
                                <span class="top"></span>
                                <span class="txt first">만화 제작 Track</span>
                            </a>
                            <a href="javascript:show_content('a3');"><span class="txt">애니 제작 Track</span></a>
                            <a href="javascript:show_content('a4');">
                                <span class="txt">캐릭터 제작 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line2 -->

                        <div class="box03">
                            <a href="javascript:show_content('a5');">
                                <span class="top"></span>
                                <span class="txt first">만화애니캐릭터 비즈 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line3 -->

                        <div class="box03">
                            <a href="javascript:show_content('a6');">
                                <span class="top"></span>
                                <span class="txt first">만화애니캐릭터 교양</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line4 -->
                    </div>
                    <!--// 만애캐 -->


                    <!-- 음악공연 -->
                    <div class="boxWrap">
                        <p class="tit"><img src="/images/2013/online_edu/tabcisys_tit_4.gif" alt="음악공연분야 역량강화를 위한 추천 트랙" /></p>

                        <div class="box04">
                            <a href="javascript:show_content('m1');">
                                <span class="top"></span>
                                <span class="txt first">음악공연 기획 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line1 -->

                        <div class="box04 type2">
                            <a href="javascript:show_content('m2');">
                                <span class="top"></span>
                                <span class="txt first hg3">음악공연 제작 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line2 -->

                        <div class="box04">
                            <a href="javascript:show_content('m3');">
                                <span class="top"></span>
                                <span class="txt first">음악공연 비즈 Track</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line3 -->

                        <div class="box04">
                            <a href="javascript:show_content('m4');">
                                <span class="top"></span>
                                <span class="txt first">음악공연 교양</span>
                                <span class="btm"></span>
                            </a>
                        </div>
                        <!--//line4 -->
                    </div>
                    <!--// 음악공연 -->
                </div>
                <!--// guideright  -->
            </div>
            <!--// guideWrap  -->

            <div id="view_b1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b1.jsp"%></div>
            <div id="view_b2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b2.jsp"%></div>
            <div id="view_b3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b3.jsp"%></div>
            <div id="view_b4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b4.jsp"%></div>
            <div id="view_b5" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b5.jsp"%></div>
            <div id="view_b6" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b6.jsp"%></div>
            <div id="view_g1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g1.jsp"%></div>
            <div id="view_g2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g2.jsp"%></div>
            <div id="view_g3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g3.jsp"%></div>
            <div id="view_g4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g4.jsp"%></div>
            <div id="view_g5" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g5.jsp"%></div>
            <div id="view_a1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a1.jsp"%></div>
            <div id="view_a2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a2.jsp"%></div>
            <div id="view_a3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a3.jsp"%></div>
            <div id="view_a4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a4.jsp"%></div>
            <div id="view_a5" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a5.jsp"%></div>
            <div id="view_a6" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a6.jsp"%></div>
            <div id="view_m1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m1.jsp"%></div>
            <div id="view_m2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m2.jsp"%></div>
            <div id="view_m3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m3.jsp"%></div>
            <div id="view_m4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m4.jsp"%></div>

        </div>
        <!-- //#subCont -->
        </form>
    </div>
    <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>