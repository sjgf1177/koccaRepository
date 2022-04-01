<%
//**********************************************************
//  1. 제목     : 비밀번호 변경
//  2. 파일명   : /learn/user/2013/portal/member/zu_PwdChange.jsp
//  3. 개요     : 회원의 비밀번호 변경 페이지이다.
//  4. 환경     : JDK 1.5
//  5. 버젼     : 1.0
//  6. 작성     : unknown
//  7. 갱신이력
//      - 2014-05-12: 비밀번호 관리 체계 변경에 따른 수정.
//                    보안등급 체크 자바스크립트 적용.
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
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

    String v_userid = box.getSession("userid");

    box.put("title_str", "비밀번호변경 - 내정보관리 - 이용안내 - 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<style type="text/css">
 .secure_info   {   display:none; padding:8px; word-break: keep-all; border:2px solid #3a96d7;
                    position:absolute; top:232px; left:560px; width:480px; height:268px; z-index:9999; overflow:auto; background:#ffffff;
                }
</style>

<!-- 스크립트영역 -->
<!-- <script type="text/javascript" src="/script/password.js"></script> //-->
<script type="text/javascript">

    /**
     * 비밀번호 보안 등급을 체크한다.
     */
    function fnCheckPaswordLevel(password) {
        var desc = new Array();
        desc[0] = "<label style=\"color:#ff0000;\">매우 낮음</label>"; // 매우부족
        desc[1] = "<label style=\"color:#ff0000;\">매우 낮음</label>"; // 조금부족
        desc[2] = "<label style=\"color:#808080;\">약함</label>"; // 보통수준
        desc[3] = "<label style=\"color:#0000ff;\">보통</label>"; // 양호수준
        desc[4] = "<label style=\"color:#808000;\">양호</label>"; // 좋습니다
        desc[5] = "<label style=\"color:#008000;\">매우 양호</label>"; // 매우좋음
        var score = 0;

        //if password length == 0, do nothing
        if (password.length == 0) return;
        //if password bigger than 8 give 1 point
        if (password.length > 7) score++;

        //if password has both lower and uppercase characters give 1 point
        if ( ( password.match(/[a-z]/) ) || ( password.match(/[A-Z]/) ) ) score++;
        //if password has at least one number give 1 point
        if (password.match(/\d+/)) score++;
        //if password has at least one special caracther give 1 point
        if ( password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/) ) score++;
        //if password bigger than 12 give another 1 point
        if (password.length > 12) score++;

        //document.getElementById("passwordDescription").innerHTML = desc[score];
        $("#oPwdLevelArea").html( desc[score] );
    }

    /**
     * 비밀번호와 비밀번호확인 값을 비교한다.
     */
    function fnCheckPasswordRe(rePassword) {
        var password = $("#oNewPwd").val();
        if ( rePassword != "" ) {
            if (rePassword.length < password.length ) {
                $("#oPwdReArea").html("<label style=\"color:#fca500;\">재입력된 비밀번호가 너무 짧음</label>");
            } else if ( password != rePassword ) {
                $("#oPwdReArea").html("<label style=\"color:#fca500;\">비밀번호가 맞지 않음</label>");
            } else {
                $("#oPwdReArea").html("<label style=\"color:#008000;\">비밀번호 확인</label>");
            }
        } else {
            $("#oPwdReArea").html("");
        }
    }

    /**
     * 비밀번호를 변경한다.
     */
    function fnUpdateUserPassword() {
        var docForm = document.passwordChangeForm;
        var oldPwd = docForm.p_pwd_old.value;
        var newPwd = docForm.p_pwd.value;
        var newPwdRe = docForm.p_pwd_re.value;

        var secureLevel = "";

        if (oldPwd.length == 0) {
            alert("현재 비밀번호를 입력해 주십시오.");
            docForm.p_pwd_old.focus();
            return;
        } else if (newPwd.length == 0) {
            alert("새 비밀번호를 입력해 주십시오.");
            docForm.p_pwd.focus();
            return;
        } else if (newPwdRe.length == 0) {
            alert("새 비밀번호확인을 입력해 주십시오.");
            docForm.p_pwd_re.focus();
            return;
        } else if (newPwd != newPwdRe) {
            alert("새로운 비밀번호가 다르게 입력되었습니다.");
            docForm.p_pwd.focus();
            return;
        } else if (newPwd.length < 8 ) {
            alert("비밀번호는 8자리 이상 입력해 주십시오.");
            docForm.p_pwd.focus();
            return;
        } else if ( oldPwd == newPwd ) {
            alert("새로운 비밀번호가 기존 비밀번호와 동일합니다.");
            docForm.p_pwd.focus();
            return;
        } else {
            docForm.target = "_self";
            docForm.action = https_host() + "/servlet/controller.homepage.MainMemberJoinServlet";
            docForm.p_process.value = "ChangePwdOk";
            docForm.submit();
        }
    }
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">


<!-- form 시작 -->
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
                -->
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
                <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=ChangePwd"><img src="/images/2013/support/tabinfo2_on.jpg" alt="비밀번호변경" /></a></li>
                <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberWithdraw"><img src="/images/2013/support/tabinfo3_off.jpg" alt="회원탈퇴" /></a></li>
            </ul>
            <h3 class="blind">문의 및 제안 현재위치</h3>

            <p class="sidetxt"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />는 필수 입력 항목입니다.</p>
            <div class="tableWrap space1">
                <form id="oPasswordChangeForm" name="passwordChangeForm" method="post" action="">
                    <input type="hidden" name="p_process" value="" />
                    <input type="hidden" name="p_userid" value="<%= v_userid %>" />
                <table class="dataTable2" summary="이 표는 내 정보관리 비밀번호변경 표료 현재 비밀번호 확인, 새 비밀번호, 새 비밀번호 확인 내용으로 구성 되어 있습니다.">
                    <caption>내 정보관리 비밀번호변경 표</caption>
                    <colgroup>
                        <col width="25%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="pw">현재비밀번호확인</label></th>
                        <td><input type="password" name="p_pwd_old" id="oOldPwd" class="inputPw" maxlength="30" title="현재비밀번호 입력란" style="width: 280px; font-size: 11px;" /></td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="pw2">새 비밀번호</label></th>
                        <td>
                            <input type="password" name="p_pwd" id="oNewPwd" class="inputPw" maxlength="30" title="새 비밀번호 입력란" style="width: 280px; font-size: 11px;" onkeyup="fnCheckPaswordLevel(this.value);" onblur="fnCheckPasswordRe(document.passwordChangeForm.p_pwd_re.value);"/>
                            <span id="oPwdLevelArea" style="width:200px;margin-left:10px; font-weight:bold;"></span>
                            <!-- <a href="javascript:void(0);" onmouseover="fnToggleDiv(true);" onmouseout="fnToggleDiv(false);">보안등급안내</a> //-->
                            <!-- <span id="oLevelMessage" style="padding-left:12px;"></span> //-->
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="pw3">새 비밀번호확인</label></th>
                        <td>
                            <input type="password" name="p_pwd_re" id="oNewPwdRe" class="inputPw" maxlength="30" title="새 비밀번호 확인 입력란" style="width: 280px; font-size: 11px;" onkeyup="fnCheckPasswordRe(this.value);" />
                            <span id="oPwdReArea" style="width:200px;margin-left:10px; font-weight:bold;"></span>
                        </td>
                    </tr>
                    </tbody>
                </table>
                </form>
            </div>
            <ul class="tips">
                <li>* 비밀번호는 8~30자의 영문 대소문자/숫자/특수문자 혼용 사용이 가능합니다.</li>
                <li>* 2가지 조합인 경우 10자리 이상, 3가지 조합인 경우 8자리 이상을 권장합니다.</li>
                <li>* 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 사용하시고, 생일번호, 주민등록번호 등 알기 쉬운 숫자는 피해주십시오.</li>
                <li>&nbsp;</li>
                <li>보안수준이 높은 암호는 다른 사람이 쉽게 추측하거나, 자동화 프로그램 등을 이용하여 알아내기 어려운 암호를 말합니다.</li>
                <li>개인정보보호를 위해 보안수준이 높은 암호를 만드는 것이 중요합니다.</li>
                <li>보다 안전한 개인정보보호를 위해 6개월마다 주기적인 변경을 권장합니다.</li>
            </ul>
            <div class="btnArea">
                <button type="button" onclick="javascript:fnUpdateUserPassword();" class="btnText"><span>비밀번호를 변경합니다.</span></button>
            </div>
        </div>
        <!-- //#subCont -->
    </div>

    <div class="secure_info">
        <ul class="tips">
            <li>&nbsp;</li>
            <li>* 비밀번호는 8~30자의 영문 대소문자/숫자/특수문자 혼용 사용이 가능합니다.</li>
            <li>* 2가지 조합인 경우 10자리 이상, 3가지 조합인 경우 8자리 이상을 권장합니다.</li>

            <li style="padding-left:12px; font-weight:normal; color:#d1006c;">- 소문자 (a-z)</li>
            <li style="padding-left:12px; font-weight:normal; color:#d1006c;">- 대문자 (A-Z)</li>
            <li style="padding-left:12px; font-weight:normal; color:#d1006c;">- 숫자 (0-9)</li>
            <li style="padding-left:12px; font-weight:normal; color:#d1006c;">- 특수문자 (`~!@#$%^&*()-_+ [] {}|;,.&lt;&gt;/)</li>

            <li>&nbsp;</li>
            <li>* 비밀번호에 사용할 수 없는 경우 및 준수사항</li>
            <li style="padding-left:12px; font-weight:normal;">- 공백 및 허용된 특수문자 이외의 문자(예 ☜♧☎ )</li>
            <li style="padding-left:12px; font-weight:normal;">- 4자 이상 연속된 문자 및 동일한 문자 3자 이상은 사용하실 수 없습니다.</li>
            <li style="padding-left:12px; font-weight:normal;">- 사전에 등록된 단어, 아이디, 개인정보와 관련된 이름, 생일, 전화번호 등의 조합은 사용을 삼가합니다.</li>
            <!--
            <li>* 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.</li>
            <li>* 연속된 숫자, 생일번호, 주민등록번호 등 알기 쉬운 숫자는 피해주세요.</li>
            //-->
        </ul>
    </div>


</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
