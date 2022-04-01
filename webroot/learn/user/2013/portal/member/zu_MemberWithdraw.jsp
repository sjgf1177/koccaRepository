<%
//**********************************************************
//  1. 제      목: 회워탈퇴
//  2. 프로그램명 :zu_MemberWithdraw.jsp
//  3. 개      요: 회워탈퇴
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_userid   = box.getSession("userid");

    box.put("title_str", "회원탈퇴 - 정보관리 - 개인메뉴");
%>


<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function doWithdraw(){
    var fm=document.form1;

    if( (fm.p_userid.value) == ""){
        alert("아이디가 없습니다");
    }
    if( (fm.p_pwd.value) == ""){
        alert("비밀번호를 입력하세요");
        fm.p_pwd.focus();return;
    }
    document.form1.p_process.value="MemberWithdrawOk";
    document.form1.action="/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.submit()
}
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

    <!-- form 시작 -->
    <form name="form1"  action="" method="post" action="">
        <input type="hidden"    name="p_process"    value="" >
        <input type="hidden"    name="p_userid" value="<%=v_userid %>" >
        <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>개인메뉴</li>
                <li>정보관리 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a></li>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육</a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE">내가작성한 글</a></li>
                        <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                        <li><a href="#" class="active">정보관리</a></li>
                    </ul>
                </li>
                <!-- 2015-01-09 개편삭제
                <li>홈</li>
                <li>학습지원</li>
                <li>
                    이용안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" class="active">이용안내</a></li>
                    </ul>
                </li>
                <li>
                    내 정보 관리 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate" class="active">내 정보 관리</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>
                //-->

            </ul>
            <!-- 서브메인용 -->
            <!-- 2015-01-09 개편삭제
                <div class="introCategory">
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate"><strong><u>내 정보 관리</u></strong></a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </div>

            //-->
        </div>
        <!-- //#mainMenu -->

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <h2 class="subTit">정보관리<!--<img src="/images/2013/support/myinform_title.gif" alt="내정보관리" />//--></h2>
                <div id="subCont">
                    <ul class="tabCus">
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate"><img src="/images/2013/support/tabinfo1_off.jpg" alt="기본정보수정" /></a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=ChangePwd"><img src="/images/2013/support/tabinfo2_off.jpg" alt="비밀번호변경" /></a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberWithdraw"><img src="/images/2013/support/tabinfo3_on.jpg" alt="회원탈퇴" /></a></li>
                    </ul>
                    <h3 class="blind">문의 및 제안 현재위치</h3>

                    <div class="formWrap">
                        <div class="txtLeft">
                            <p class="txt1">지금까지 한국콘텐츠진흥원 아카데미를 이용해 주셔서 감사드립니다.</p>
                            <ul>
                                <li> 회원탈퇴를 신청하시면 해당 아이디는 사용이 중지되므로 <strong>해당
                                  아이디로는 재가입이 불가능</strong>합니다.</li>
                                <li> 회원탈퇴한 회원정보는 삭제되며, 기존 가입한 ID와 신규가입 ID
                                  간의 ID 중복방지와 게시글 관리를 위하여 성명과 ID는 남습니다.</li>
                                <li> 회원 탈퇴 후 재가입 가능하며, 개인정보는 <strong>회원탈퇴와 동시에
                                  삭제 및 파기</strong>됩니다.</li>
                              </ul>
                             <p class="txt2">&lt;문의 : 한국콘텐츠아카데미 운영팀 TEL. 02-2161-0077 &gt; </p>
                        </div>
                        <!-- //txtLeft -->
                        <div class="txtRight">
                            <table class="dataTable3" summary="이 표는 내 정보관리 비밀번호변경 표료 현재 비밀번호 확인, 새 비밀번호, 새 비밀번호 확인 내용으로 구성 되어 있습니다.">
                        <caption>내 정보관리 비밀번호변경 표</caption>
                                <colgroup>
                                    <col width="20%"/>
                                    <col width="*"/>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th scope="row"><label for="identity">아이디</label></th>
                                    <td><%=v_userid %></td>
                                </tr>
                                <tr>
                                    <th scope="row"><label for="pw2">비밀번호</label></th>
                                    <td><input type="password" name="p_pwd" id="pw2" class="inputPw" size="30" /></td>
                                </tr>
                                <tr>
                                    <th scope="row"><label for="reason">탈퇴사유</label></th>
                                    <td><textarea id="reason" name="p_leave_reason" class="textArea" rows="7" cols="37" /></textarea></td>
                                </tr>
                                </tbody>
                            </table>
                            <a href="javascript:doWithdraw();"><img src="/images/2013/support/btn_out.gif" alt="회원탈퇴를 신청합니다" /></a>
                        </div>
                        <!-- //txtRight -->
                    </div>
                    <!-- //formWrap -->
                </div>
                <!-- //#subCont -->

        </div>
    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>