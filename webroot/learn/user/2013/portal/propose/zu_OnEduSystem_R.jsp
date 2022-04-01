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
%>
<%@ include file="/learn/user/2013/portal/include/top_sub.jsp"%>
<script type="text/javascript">
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

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>온라인 교육</li>
            <li>
                과정안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><strong><u>과정안내</u></strong></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
            </ul>
        </div>
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">


        <h2 class="subTit"><img src="/images/2013/field_edu/courseguide_title.gif" alt="과정안내" /></h2>

        <div id="subCont">
            <ul class="tabCus">
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem"><img src="/images/2013/online_edu/tabcurs1_on.jpg" alt="교육체계도" /></a></li>
<!--                <li><a href="#"><img src="/images/2013/online_edu/tabcurs2_off.jpg" alt="직무별 분류" /></a></li>-->
<!--                <li><a href="#"><img src="/images/2013/online_edu/tabcurs3_off.jpg" alt="작업별 분류" /></a></li>-->
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><img src="/images/2013/online_edu/tabcurs4_off.jpg" alt="과정별 소개" /></a></li>
            </ul>
            <h3 class="blind">교육체계도 현재위치</h3>

            <div class="tableWrap8 space2">
                <table class="dataTable8" summary="이 표는 온라인교육 과정안내 표로 방송영상 게임 만화애니캐릭터 음악공연 내용으로 구성 되어 있습니다.">
                    <caption>과정안내 교육체계도 표</caption>
                    <colgroup>
                        <col width="15%"/>
                        <col width="18%"/>
                        <col width="25%"/>
                        <col width="22%"/>
                        <col width="*"/>
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col">방송영상</th>
                            <th scope="col">게임</th>
                            <th scope="col">만화캐릭터</th>
                            <th scope="col">음악공연</th>
                        </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>기획</th>
                        <td id="b1"><a href="javascript:show_content('b1');">방송영상 기획 Track</a></td>
                        <td id="g1"><a href="javascript:show_content('g1');">게임 기획 Track</a></td>
                        <td id="a1"><a href="javascript:show_content('a1');">만화애니캐릭터 기획 Track</a></td>
                        <td id="m1"><a href="javascript:show_content('m1');">음악공연 기획 Track</a></td>
                    </tr>
                    <tr>
                        <th scope="row" rowspan="4"><a href="#">제작</a></th>
                        <td id="b2"><a href="javascript:show_content('b2');">방송영상 연출 Track</a></td>
                        <td id="g2" rowspan="2"><a href="javascript:show_content('g2');">게임 연출/그래픽/사운드 Track</a></td>
                        <td id="a2"><a href="javascript:show_content('a2');">만화 제작 Track</a></td>
                        <td id="m2" rowspan="4"><a href="javascript:show_content('m2');">음악공연 제작 Track</a></td>
                    </tr>
                    <tr>
                      <td id="b3" rowspan="2"><a href="javascript:show_content('b3');">방송영상 촬영 Track</a></td>
                      <td id="a3" rowspan="2"><a href="javascript:show_content('a3');">애니 제작 Track</a></td>
                      </tr>
                    <tr>
                      <td id="g3" rowspan="2"><a href="javascript:show_content('g3');">게임 프로그래밍 Track</a></td>
                      </tr>
                    <tr>
                        <td id="b4"><a href="javascript:show_content('b4');">방송영상 편집 Track</a></td>
                        <td id="a4"><a href="javascript:show_content('a4');">캐릭터 제작 Track</a></td>
                    </tr>
                    <tr>
                      <th scope="row">비즈</th>
                      <td id="b5"><a href="javascript:show_content('b5');">방송영상 비즈 Track</a></td>
                      <td id="g4"><a href="javascript:show_content('g4');">게임 비즈 Track</a></td>
                      <td id="a5"><a href="javascript:show_content('a5');">만화애니캐릭터 비즈 Track</a></td>
                      <td id="m3"><a href="javascript:show_content('m3');">음악공연 비즈 Track</a></td>
                    </tr>
                    <tr>
                      <th scope="row">교양</th>
                      <td id="b6"><a href="javascript:show_content('b6');">방송영상 교양</a></td>
                      <td id="g5"><a href="javascript:show_content('g5');">게임교양</a></td>
                      <td id="a6"><a href="javascript:show_content('a6');">만화애니캐릭터 교양</a></td>
                      <td id="m4"><a href="javascript:show_content('m4');">음악공연 교양</a></td>
                      </tr>
                    </tbody>
                </table>
            </div>
            <!-- //tableWrap8 -->

            <div class="broadWrap" id="view_b1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b1.jsp"%></div>
            <div class="broadWrap" id="view_b2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b2.jsp"%></div>
            <div class="broadWrap" id="view_b3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b3.jsp"%></div>
            <div class="broadWrap" id="view_b4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b4.jsp"%></div>
            <div class="broadWrap" id="view_b5" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b5.jsp"%></div>
            <div class="broadWrap" id="view_b6" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/b6.jsp"%></div>
            <div class="broadWrap" id="view_g1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g1.jsp"%></div>
            <div class="broadWrap" id="view_g2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g2.jsp"%></div>
            <div class="broadWrap" id="view_g3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g3.jsp"%></div>
            <div class="broadWrap" id="view_g4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g4.jsp"%></div>
            <div class="broadWrap" id="view_g5" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/g5.jsp"%></div>
            <div class="broadWrap" id="view_a1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a1.jsp"%></div>
            <div class="broadWrap" id="view_a2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a2.jsp"%></div>
            <div class="broadWrap" id="view_a3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a3.jsp"%></div>
            <div class="broadWrap" id="view_a4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a4.jsp"%></div>
            <div class="broadWrap" id="view_a5" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a5.jsp"%></div>
            <div class="broadWrap" id="view_a6" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/a6.jsp"%></div>
            <div class="broadWrap" id="view_m1" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m1.jsp"%></div>
            <div class="broadWrap" id="view_m2" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m2.jsp"%></div>
            <div class="broadWrap" id="view_m3" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m3.jsp"%></div>
            <div class="broadWrap" id="view_m4" style="display: none;"><%@ include file="/learn/user/2013/portal/propose/onedusystem/m4.jsp"%></div>

            <!-- //broadWrap -->

        </div>
        <!-- //#subCont -->

    </div>
<%@ include file="/learn/user/2013/portal/include/footer.jsp"%>