<%
//**********************************************************
//  1. 제      목: 패스워드 찾기 결과
//  2. 프로그램명 : zu_FindPwd_R.jsp
//  3. 개      요: 패스워드 찾기 결과
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_name = box.getString("p_name_2");
    //String v_pwd  = (String)request.getAttribute("pwd");
    String v_tmp_pwd  =  box.getString("tmp_pwd");
    String v_email  = box.getString("v_toEmail");

    box.put("title_str", "아이디/패스워드찾기-이용안내, 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/top_sub.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post" action="">
    <input type="hidden"    name="p_process"    value="" />

    <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>학습지원</li>
                <li>
                    이용안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        <!--<li><a href="#">연간일정</a></li>-->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" class="active">이용안내</a></li>
                    </ul>
                </li>
                <li>
                    아이디/패스워드 찾기 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd" class="active">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>
            </ul>
            <!-- 서브메인용 -->
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
            <h2 class="subTit"><img src="/images/2013/support/idpw_title.gif" alt="아이디/패스워드 찾기" /></h2>

            <div id="subCont">
                <div class="searhIdpw_done">
                    <% if (v_tmp_pwd.equals("")) {  %>
                        입력하신 정보와 일치하는 정보가 없습니다.
                    <% } else {  %>
                        <%=v_name %> 회원님의 임시 비밀번호는 <strong><%=v_tmp_pwd %></strong> 입니다.<br/>
                        안내해 드린 임시 비밀번호로 로그인 후 &quot;개인정보변경&quot; 접속후 변경하여 주시기 바랍니다.
                    <% } %>
                    <a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd"><img src="/images/2013/btn/btn_back.gif" alt="뒤로가기" /></a>
                </div>
                <!-- //searhIdpw -->
            </div>
            <!-- //#subCont -->
        </div>
    </form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/2013/portal/include/footer.jsp"%>
<!--// footer -->