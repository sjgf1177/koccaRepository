<%
//**********************************************************
//  1. 제      목: 회원가입_본인확인
//  2. 프로그램명 : zu_MemberJoin.jsp
//  3. 개      요: 회원가입_본인확인
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>

<%@page import="com.credu.library.RequestBox" %>
<%@page import="com.credu.library.RequestManager" %>
<%@page import="NiceID.Check.CPClient" %>
<%@taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String prevProcess = box.getString("p_process");

    if (prevProcess == null || prevProcess.equals("") || !prevProcess.equals("CheckAgree") ) {
%>
<!DOCTYPE html>
<html>
<title>한국콘텐츠아카데미</title>
<script type="text/javascript">
    alert("잘못된 접근입니다.");
    location.href = "/";
</script>
<body>
</body>
</html>
<%
    } else {


        CPClient niceCheck = new CPClient();

        String sSiteCode = "G7889";             // NICE로부터 부여받은 사이트 코드
        String sSitePassword = "KCQJDMUIH9ZI";  // NICE로부터 부여받은 사이트 패스워드

        String sRequestNumber = "REQ0000000001";            // 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로
                                                            // 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
        sRequestNumber = niceCheck.getRequestNO(sSiteCode);
        session.setAttribute("REQ_SEQ" , sRequestNumber);   // 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.

        String sAuthType = "M";          // 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서

        String popgubun = "Y";      //Y : 취소버튼 있음 / N : 취소버튼 없음
        String customize = "";      //없으면 기본 웹페이지 / Mobile : 모바일페이지

        // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
        String sReturnUrl = "http://edu.kocca.or.kr/certi/checkplus/checkplus_success.jsp";      // 성공시 이동될 URL
        String sErrorUrl = "http://edu.kocca.or.kr/certi/checkplus/checkplus_fail.jsp";          // 실패시 이동될 URL

        // 입력될 plain 데이타를 만든다.
        String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                            "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                            "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                            "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                            "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                            "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                            "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;

        String sMessage = "";
        String sEncData = "";

        int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
        if( iReturn == 0 ) {
            sEncData = niceCheck.getCipherData();

        } else if( iReturn == -1) {
            sMessage = "암호화 시스템 에러입니다.";

        } else if( iReturn == -2) {
            sMessage = "암호화 처리오류입니다.";

        } else if( iReturn == -3) {
            sMessage = "암호화 데이터 오류입니다.";

        } else if( iReturn == -9) {
            sMessage = "입력 데이터 오류입니다.";

        } else {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        box.put("title_str", "본인확인 - 회원가입 - 이용안내 - 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<script type="text/javascript">

    window.name = "Parent_window";

    /**
     * 안심본인인증 팝업창 열기
     */
    function fnNiceCertiPopup(){
        window.open("", "popupChk", "width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no");

        document.certiForm.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
        document.certiForm.target = "popupChk";
        document.certiForm.submit();
    }

    /**
     * 이메일인증 팝업창 열기
     */
    function fnEmailCertiPopup() {
        window.open("", "popupChk", "width=636, height=306, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=no, location=no, scrollbar=no");
        document.emailCetiForm.action = "/certi/email/emailCerti.jsp";
        document.emailCetiForm.target = "popupChk";
        document.emailCetiForm.submit();
    }
</script>
<!-- container-new 시작 //-->
<div id="container-new">

    <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>이용안내</li>
                <li>회원가입 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <!-- <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" >이용안내</a></li> //-->
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin" class="active">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                <!--
                <li>이용안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
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
                    회원가입 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin" class="active">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>
                -->
                </li>
            </ul>
            <!-- 서브메인용 -->
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <h2 class="subTit">회원가입<!-- <img src="/images/2013/support/join_title.gif" alt="회원가입" />//--></h2>

            <div id="subCont">
                <div class="stepimgBox">
                    <img src="/images/2013/support/private_img2.jpg" alt="회원가입의 4단계 약관동의, 본인확인, 정보입력, 가입완료 중에서 현재 2단계 본인확인 단계입니다." />
                </div>
                <div class="joinBox">
                    <div class="cont">
                        <div class="btnArea wd2">
                            <button type="button" class="btnText" onclick="javascript:fnNiceCertiPopup();"><span>안심본인인증</span></button>
                            <button type="button" class="btnText" onclick="javascript:fnEmailCertiPopup();"><span>이메일인증</span></button>
                        </div>
                    </div>

                </div>
                <!--//실명확인 -->


            </div>
            <!-- //#subCont -->

        </div>

    <form name="certiForm" method="post">
        <input type="hidden" name="m" value="checkplusSerivce" /> <!-- 필수 데이타로, 누락하시면 안됩니다. -->
        <input type="hidden" name="EncodeData" value="<%= sEncData %>"/>    <!-- 위에서 업체정보를 암호화 한 데이타입니다. -->

        <!-- 업체에서 응답받기 원하는 데이타를 설정하기 위해 사용할 수 있으며, 인증결과 응답시 해당 값을 그대로 송신합니다. 해당 파라미터는 추가하실 수 없습니다. -->
        <input type="hidden" name="param_r1" value="regMember" />
        <input type="hidden" name="param_r2" value="" />
        <input type="hidden" name="param_r3" value="" />
    </form>

    <form name="emailCetiForm" method="post">
        <input type="hidden" name="param_r1" value="regMember" />
    </form>

<!-- container-new 시작 //-->
<div id="container-new">

<%@ include file="/learn/user/2013/portal/include/footer.html"%>

<%
    }
%>
