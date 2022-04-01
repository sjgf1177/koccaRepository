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
    
    box.put("title_str", "이메일무단수집거부 - 이용안내");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>이용안내 <!--<a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>-->
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
<!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
-->
                        <!--<li><a href="#">연간일정</a></li>-->
<!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" class="active">이용안내</a></li>
                    </ul>
-->
                </li>
                <li>
                    이메일무단수집거부 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <!--<li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>-->
<%
    if ( !isUserLogin ) {
%>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
<%
    }
%>
                        <!--<li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>-->
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL" class="active">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>
            </ul>
            <!-- 서브메인용 -->
            <div class="introCategory">
                <ul>
                    <!--<li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>-->
<%
    if ( !isUserLogin ) {
%>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
<%
    }
%>
                    <!--<li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>-->
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL"><strong><u>이메일무단수집거부</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            </div>
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <h2 class="subTit">이메일무단수집거부<!--<img src="/images/2013/support/email_title.gif" alt="이메일무단수집거부" />//--></h2>

                <div id="subCont">

                <div class="emailx">
                    <p class="tx1">
                        본 홈페이지에 게시된 이메일 주소가 전자우편 수집 프로그램이나 그 밖의<br />
                        기술적 장치를 이용하여 무단으로 수집되는 것을 거부하며, 이를 위반시<br />
                        정보통신망법에 의해 형사처벌됨을 유념하시기 바랍니다.<br />
                    </p>

                    <p class="tx2">한국콘텐츠진흥원 아카데미는 이메일 주소 수집을 거부합니다.</p>
                    <p class="tx3">정보통신망법 제 50조의 2 (전자우편주소의 무단 수집행위 등 금지)</p>
                    <ul>
                        <li class="num1">누구든지 전자우편주소의 수집을 거부하는 의사가 명시된 인터넷 홈페이지에서
                        자동으로 전자우편주소를 수집하는 프로그램 그 밖의 기술적 장치를 이용하여
                        전자우편주소를 수집하여서는 안된다.</li>

                        <li class="num2">누구든지 제 1항의 규정을 위반하여 수집된 전자우편주소를 판매 유통하여서는
                        아니된다.</li>

                        <li class="num3">누구든지 제 1항 및 제 2항의 규정에 의하여 수집·판매 및 유통이금지된 전자우편
                        주소임을 알고 이를 정보전송에 이용하여서는 아니 된다. </li>

                        <li class="num4">만일, 위와 같은 기술적 장치를 사용한 이메일주소 무단수집 피해를 당하신 경우<br />
                        <strong>불법스팸대응센터 전용전화 (1336)나 홈페이지(www.spamcop.or.kr)</strong>의
                        신고창을 통하여 신고하여 주시기 바랍니다. </li>
                    </ul>
                </div>
                <!--//emailx -->

            </div>
            <!-- //#subCont -->


        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>