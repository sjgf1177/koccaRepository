<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "홈페이지 개선의견 - 이용안내");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//입력된 내용 저장
function insert() {
    var f = document.form1;
    var v_email = f.p_email1.value+'@'+f.p_email2.value;

    if(f.p_title.value == ""){alert("제목을 입력해주세요");f.p_title.focus();return;}

    if(f.p_smsyn.checked){
        if(is_mail(v_email) == false){
            alert("정확한 E-Mail을 입력하십시오.");
            f.p_email1.focus();
            return;
        }
        f.p_smsyn.value = "Y";
    }else{
        f.p_smsyn.value = "N";
    }

    if (realsize(f.p_contents.value) < 1) {alert("내용을 입력해주세요");f.p_contents.focus();return;}

    f.p_email.value = v_email;
    f.action = "/servlet/controller.homepage.HomePageContactServlet";
    f.p_process.value = "insertWebReport";

    if(confirm("등록하시겠습니까?")){f.submit();}
}

//글자수 체크
function realsize( value ) {
 var len = 0;
 if ( value == null ) return 0;
 for(var i=0;i<value.length;i++){
     var c = escape(value.charAt(i));
     if ( c.length == 1 ) len ++;
     else if ( c.indexOf("%u") != -1 ) len += 2;
     else if ( c.indexOf("%") != -1 ) len += c.length/3;
 }
 return len;
}


//-------------------------------------------------------
//이메일 및 이메일형태 ID검사
//-------------------------------------------------------
function is_mail(strEmail){
    /** 금지사항
     - @가 2개이상
     - .이 붙어서 나오는 경우
     -  @.나  .@이 존재하는 경우
     - 맨처음이.인 경우 **/
    var regDoNot = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;

    /** 필수사항
     - @이전에 하나이상의 문자가 있어야 함
     - @가 하나있어야 함
     - Domain명에 .이 하나 이상 있어야 함
     - Domain명의 마지막 문자는 영문자 2~4개이어야 함 **/
    var regMust = /^[a-zA-Z0-9\-\.\_]+\@[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4})$/;
    if ( !regDoNot.test(strEmail) && regMust.test(strEmail) ){
        return true;
    }else{
        return false;
    }
}
function emailChange() {
    var r=$("[name='p_email3']").val();
    $("[name='p_email2']").val(r);
    if(r == ""){
        $("[name='p_email2']").attr("readonly",false);
    }else{
        $("[name='p_email2']").attr("readonly",true);
    }
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

    <!-- Form 영역 시작 -->
    <form name="form1" method = "post" action="/servlet/controller.homepage.HomePageContactServlet">
    <input type="hidden" name="p_process" />
    <input type="hidden" name="p_email" />

        <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>이용안내 
                    <!--
                    <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                    -->
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" class="active">이용안내</a></li>
                    </ul>
                    -->
                </li>
                <li>
                    홈페이지 개선의견 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
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
                        <!-- <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li> //-->
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport" class="active">홈페이지 개선의견</a></li>
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
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport"><strong><u>홈페이지 개선의견</u></strong></a></li>
                </ul>
            </div>
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
            <h2 class="subTit">홈페이지 개선의견<!--<img src="../images/2013/support/webcare_title.gif" alt="홈페이지 개선의견" />//--></h2>

            <div id="subCont">
                <div class="webimgBox">
                    <h3 class="blind">한국콘텐츠아카데미  홈페이지 개선의견</h3>
                    한국콘텐츠아카데미 홈페이지에 방문해 주셔서 감사합니다.<br />
                    <strong>홈페이지 이용에 관련하여 개선의견 사항</strong>을 남겨주시면 검토 후 반영하겠습니다.<br />
                    이메일 주소를 함께 남겨주시면 처리 진행상황을 받아보실 수 있습니다.<br /><br />

                    기타 일반 이용관련 불편사항은 학습지원>교육문의를 이용해 주시기 바랍니다.
                </div>
                <input type="hidden" name="p_gubun" value="C1"/>
                <div class="tableWrap space2">
                    <table class="dataTable2" summary="이 표는 홈페이지 개선의견 접수 표로 제목, 연락처(무선), 불편신고내용으로 구성되어 있습니다.">
                        <caption>홈페이지 개선의견 접수 표</caption>
                        <colgroup>
                            <col width="18%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="title">제목</label></th>
                            <td><input type="text" name="p_title" id="title"  class="inputText" size="98" /></td>
                        </tr>
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="report">내용</label></th>
                            <td><textarea id="report" name="p_contents" class="textArea" rows="7" cols="13"></textarea></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="f_dEmail">이메일</label></th>
                            <td>
                                <input type="text" name="p_email1" id="f_dEmail" class="inputText" size="15" title="이메일 아이디 입력"/> @
                                <input type="text" name="p_email2" class="inputText" size="20" title="이메일 도메인 입력"/>
                                <select class="select" name="p_email3" title="이메일주소 선택" onchange="emailChange();">
                                    <option value="">직접입력</option>
                                    <option value="naver.com" >naver.com</option>
                                    <option value="chol.com" >chol.com</option>
                                    <option value="dreamwiz.com" >dreamwiz.com</option>
                                    <option value="empal.com" >empal.com</option>
                                    <option value="freechal.com" >freechal.com</option>
                                    <option value="gmail.com" >gmail.com</option>
                                    <option value="hanafos.com" >hanafos.com</option>
                                    <option value="hanmail.net" >hanmail.net</option>
                                    <option value="hanmir.com" >hanmir.com</option>
                                    <option value="hitel.net" >hitel.net</option>
                                    <option value="hotmail.com" >hotmail.com</option>
                                    <option value="korea.com" >korea.com</option>
                                    <option value="lycos.co.kr" >lycos.co.kr</option>
                                    <option value="nate.com" >nate.com</option>
                                    <option value="netian.com" >netian.com</option>
                                    <option value="paran.com" >paran.com</option>
                                    <option value="yahoo.com" >yahoo.com</option>
                                    <option value="yahoo.co.kr" >yahoo.co.kr</option>
                                </select>
                                <span class="rd0">&nbsp;&nbsp;<input type="checkbox" name="p_smsyn" id="sms" /> <label for="sms">처리사항을 E-mail로 받아봅니다</label></span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap -->

                <div class="btnArea wd3">
                    <a href="javascript:insert();" class="btnText"><span>등록합니다</span></a>
                </div>

            </div>
            <!-- //#subCont -->
        </div>
        <!-- 컨텐츠 영역 끝 -->

    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>