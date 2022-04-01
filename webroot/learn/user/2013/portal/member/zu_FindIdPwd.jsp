<%
//**********************************************************
//  1. 제      목: 패스워드 찾기
//  2. 프로그램명 : zu_FindIdPwd.jsp
//  3. 개      요: 패스워드 찾기
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

    box.put("submenu","3");

    String s_userid  = box.getSession("userid");

    //===================================================================
    // 본인인증(휴대폰인증) 관련 항목
    //===================================================================
    String sUserid = box.getString("sUserid");
    String sJuminid1 = box.getString("sJuminid1");
    String sJuminid2 = box.getString("sJuminid2");
    String sName = box.getString("sName");
    String sEncData = box.getString("sEncData");
    String sMessage = box.getString("sMessage");
    String sIsOk = box.getString("sIsOk");

    box.setSession("sUserid", sUserid);
    box.setSession("sJuminid1", sJuminid1);
    box.setSession("sJuminid2", sJuminid2);
    box.setSession("sName", sName);

    box.put("title_str", "아이디/패스워드찾기-이용안내, 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<style type="text/css">
.defaultWordText { font-weight:bold; }
.defaultWordTextActive { color: #a1a1a1; font-weight:bold; }
</style>

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--

$(function() {
    $(".defaultWordText").focus(function(srcc) {
        if ($(this).val() == $(this)[0].title) {
            $(this).removeClass("defaultWordTextActive");
            $(this).val("");
        }
    });
        
    $(".defaultWordText").blur(function() {
        if ($(this).val() == "") {
            $(this).addClass("defaultWordTextActive");
            $(this).val($(this)[0].title);
        }
    });
        
    $(".defaultWordText").blur();

    $("input:radio[name=checkType]").bind("click", function(){
        if ($(this).val() == 1 ) {
            $("#oCertiType1").show();
            $("#oCertiMsg1").show();
            $("#oCertiType2").hide();
            $("#oCertiMsg2").hide();

        } else {
            $("#oCertiType1").hide();
            $("#oCertiMsg1").hide();
            $("#oCertiType2").show();
            $("#oCertiMsg2").show();
        }
    });
});


/**
 * ID 찾기
 */
function fnFindId() {
    if ( $("#oFindIdUserName").val() == "" || $("#oFindIdUserName").val() == $("#oFindIdUserName")[0].title ) {
        alert("이름을 입력하세요.");
        $("#oFindIdUserName").focus();
        return;
    } else if( $("#oFindIdCertiName").val() == ""  || $("#oFindIdCertiName").val() == $("#oFindIdCertiName")[0].title ) {
        alert("휴대폰번호 혹은 이메일 주소를 입력하세요.");
        $("#oFindIdCertiName").focus();
        return;
    } else {

        var param = $("#oFindIdPwdForm").serialize();

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "post"
            ,   url : "/learn/user/2013/portal/member/zu_FindIdAjax.jsp"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {
                    if ( data.result == "success" ) {
                        $("#oFindIdResultMsg").empty().show().append("입력하신 정보로 찾은 아이디 입니다.<br/>개인정보 보호를 위해 아이디의 일부는 *로 표기됩니다.");
                        $("#oFindIdResultList").empty().show();
                        $.each( data.userList, function(){
                            $("#oFindIdResultList").append(this.d_userid + "<br/>");
                        });
                    } else {
                        $("#oFindIdResultMsg").empty().show().append("입력하신 정보로 아이디를 찾을 수 없습니다.");
                        $("#oFindIdResultList").empty().hide();
                    }
                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                    alert("시스템 오류가 발생하였습니다.\n관리자에게 문의하시기 바랍니다.");
                }
        });
    }
}

/**
 * 패스워드 찾기
 */
function fnFindPassword() {

    if( $("#oFindPwdUserId").val() == ""  || $("#oFindPwdUserId").val() == $("#oFindPwdUserId")[0].title ) {
        alert("아이디를 입력하세요.");
        $("#oFindPwdUserId").focus();
        return;
    } else if ( $("#oFindPwdUserName").val() == "" || $("#oFindPwdUserName").val() == $("#oFindPwdUserName")[0].title ) {
        alert("이름을 입력하세요.");
        $("#oFindPwdUserName").focus();
        return;
    } else if( $("input:radio[name=checkType]:checked").val() == 1 && ( $("#oFindPwdUserMobile").val() == ""  || $("#oFindPwdUserMobile").val() == $("#oFindPwdUserMobile")[0].title )) {
        alert("휴대폰 번호를 입력하세요.");
        $("#oFindPwdUserMobile").focus();
        return;
    } else if( $("input:radio[name=checkType]:checked").val() == 2 && ( $("#oFindPwdUserEmail").val() == ""  || $("#oFindPwdUserEmail").val() == $("#oFindPwdUserEmail")[0].title )) {
        alert("이메일 주소를 입력하세요.");
        $("#oFindPwdUserEmail").focus();
        return;
    } else {

        var param = $("#oFindIdPwdForm").serialize();

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "post"
            ,   url : "/learn/user/2013/portal/member/zu_FindPasswordAjax.jsp"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {
                    if ( data.result == "success" ) {
                        if ( $("input:radio[name=checkType]:checked").val() == 1 ) {
                            alert("입력하신 휴대폰 번호로 임시 비밀번호를 전송하였습니다.");
                        } else {
                            alert("입력하신 이메일 주소로 임시 비밀번호를 발송하였습니다.");
                        }
                    } else {
                        alert("입력하신 내용으로 조회된 정보가 없습니다.");
                    }
                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                    alert("시스템 오류가 발생하였습니다.\n관리자에게 문의하시기 바랍니다.");
                }
        });
    }

}


// 메인페이지로
function indexList() {
    document.form1.p_process.value = "";
    document.form1.action = "/servlet/controller.homepage.MainServlet";
    document.form1.submit();
}

//자동이동
function moveFocus(num,fromform,toform)
    {
        var str = fromform.value.length;
        if(str == num)
       toform.focus();
    }

// 숫자만 들어가게 만든다.
function onlyNumber()
{
    if(((event.keyCode<48)||(event.keyCode>57)))
    event.returnValue=false;
}

   // SMS 전송
    function whenSMS() {

      ff =document.form1;

      ff.p_msubjnm.value=ff.s_subjcourse.options[ff.s_subjcourse.selectedIndex].text;
      ff.p_mseqgrnm.value=ff.s_subjseq.options[ff.s_subjseq.selectedIndex].text;
      ff.p_msubj.value=ff.s_subjcourse.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value=ff.s_subjseq.value;
      ff.p_touch.value = "09";

      window.self.name = "StudentMemberList";
      open_window("openSMS","","100","100","450","443");
      document.form1.target = "openSMS";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.p_process.value = 'SendSMS';
      document.form1.submit();
    }
//-->
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

<!-- form 시작 -->
    <form id="oFindIdPwdForm" name="form1" method="post" onsubmit="return false;">
    <!--
    <input type="hidden" name="p_process">
    <input type="hidden" name="gubun" value="1">
    //-->

            <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>이용안내</li>
                <li>
                    아이디/패스워드 찾기 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin" >회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd" class="active">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>

                <!-- 2015-01-16 개편 변경
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
                    아이디/패스워드 찾기 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
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
                //-->
            </ul>
            <!-- 서브메인용 -->
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd"><strong><u>아이디/패스워드 찾기</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            <!-- 2015-01-16 개편 변경
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd"><strong><u>아이디/패스워드 찾기</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            //-->
            </div>
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <h2 class="subTit">아이디/패스워드 찾기<!-- <img src="/images/2013/support/idpw_title.gif" alt="아이디/패스워드 찾기" />//--></h2>

            <div id="subCont">
                <div class="searhIdpw">
                    <div class="boxLeft">
                        <p class="tit1"><img src="/images/2013/support/idsearch_title.gif" alt="아이디 찾기" /></p>
                        <p class="tit2">가입시 입력한 성명과 휴대폰번호 혹은 이메일 주소로 아이디를 찾을 수 있습니다.</p>
                        <ul>
                            <li>내 정보에 등록된 휴대폰 번호/이메일로 찾기</li>
                        </ul>
                        <dl id="oFindIdArea" style="height:164px;">
                            <dt><label for="oFindIdUserName" style="display:none;">성명(한글)</label></dt>
                            <dd style="padding-left:8px;"><input type="text" name="p_findid_name" id="oFindIdUserName" class="inputText defaultWordText" title="이름을 입력하세요." style="width:320px; height:24px;" /></dd>
                            <dt><label for="oFindIdCertiName" style="display:none;">휴대폰번호 혹은 이메일 주소</label></dt>
                            <dd style="padding-left:8px;"><input type="text" name="p_findid_certi" id="oFindIdCertiName" class="inputText defaultWordText" title="휴대폰번호 혹은 이메일 주소를 입력하세요." style="width:320px; height:24px;" /></dd>

                            <dt id="oFindIdResultMsg" style="display:none;"></dt>
                            <dt id="oFindIdResultList" style="display:none; width:35%; text-align:center; color:#4b88f7; font-size:15px; font-weight:bold; padding-top:40px;"></dt>
                        </dl>

                        <a href="javascript:fnFindId();" class="btn_check"><img src="/images/2013/common/btn_check.gif" alt="확인" /></a>
                    </div>
                    <!-- //boxLeft -->

                    <div class="boxRight">
                        <p class="tit1"><img src="/images/2013/support/pw_title.gif" alt="비밀번호 찾기" /></p>
                        <p class="tit2">회원가입 시 등록한 정보를 아래에 입력해 주세요.<br/>&nbsp;</p>
                        <ul>
                            <li><input type="radio" name="checkType" id="oFindPwdMobile" value="1" checked /><label for="oFindPwdMobile">휴대폰으로 찾기</label> </li>
                            <li><input type="radio" name="checkType" id="oFindPwdEmail" value="2"/><label for="oFindPwdEmail">이메일로 찾기</label></li>
                        </ul>
                        <dl>
                            <dd style="padding-left:8px;"><input type="text" name="p_findpwd_userid" id="oFindPwdUserId" class="inputText defaultWordText" title="아이디를 입력하세요." style="width:320px; height:18px;" /></dd>
                            <dd style="padding-left:8px;"><input type="text" name="p_findpwd_name" id="oFindPwdUserName" class="inputText defaultWordText" title="이름을 입력하세요" style="width:320px; height:18px;" /></dd>
                            <div id="oCertiType1">
                                <dd style="padding-left:8px;"><input type="text" name ="p_findpwd_mobile" id="oFindPwdUserMobile" class="inputText defaultWordText" title="휴대폰 번호를 입력하세요" maxlength="13" style="width:320px; height:18px;" /></dd>
                            </div>
                            <div id="oCertiType2" style="display: none;">
                                <dd style="padding-left:8px;"><input type="text" name="p_findpwd_email" id="oFindPwdUserEmail" class="inputText defaultWordText" title="이메일주소 입력하세요" style="width:320px; height:18px;" /></dd>
                            </div>
                        </dl>
                        <p class="tx1">
                            <span id="oCertiMsg1" style="display:block;">입력하신 정보가 올바른 경우, 임시 비밀번호가 회원님의 핸드폰으로 문자가 발송됩니다.</span>
                            <span id="oCertiMsg2" style="display:none;">입력하신 정보가 올바른 경우, 임시 비밀번호가 회원님의 이메일로 발송됩니다.</span>
                        </p>
                        <a href="javascript:fnFindPassword();" class="btn_check"><img src="/images/2013/common/btn_check.gif" alt="확인" /></a>
                    </div>
                    <!-- //boxRight -->
                </div>
                <!-- //searhIdpw -->
            </div>
            <!-- //#subCont -->
        </div>
    </form>

</div>
<!--  //container-new 끝 //-->

<%
    if (!sMessage.equals("")) {
        out.println("<script language='javascript'> alert('"+ sMessage +"'); </script>");
    }

    if (sIsOk.equals("0")) {
       out.println("<script language='javascript'> window.open('http://check.namecheck.co.kr/checkplus_new_model/checkplus.cb?m=checkplusSerivce&EncodeData="+ sEncData + "','', 'width=425, height=535');</script>");
    }
%>

<%@ include file="/learn/user/2013/portal/include/footer.html"%>