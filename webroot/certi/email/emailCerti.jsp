<%
//**********************************************************
//  1. 제  목: 회원가입_본인확인
//  2. 파일명: /certi/email/emailCerti.jsp
//  3. 개  요: 회원가입_본인확인
//  4. 환  경: JDK 1.5
//  5. 버  젼: 1.0
//  6. 작  성:
//  7. 수  정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    // 인증 화면을 호출한 구분값을 가지고 있다.
    // regForm: 회원가입 / findPwd: 비밀번호 찾기
    String sReserved1 = box.getStringDefault("param_r1", "");
    String resultPage = "";

    if (sReserved1.equals("regMember") ) {
        // resultPage = "/learn/user/2013/portal/member/zu_MemberInfo_I_new.jsp";
        resultPage = "/servlet/controller.member.MemberJoinRegServlet";
    } else if (sReserved1.equals("findPwd") ) {
        resultPage = "/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd";
    }
%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta name="subject" content="한국콘텐츠아카데미" />
<meta name="title" content="한국콘텐츠아카데미" />
<meta name="description" content="한국콘텐츠아카데미" />
<meta name="keywords" content="회원가입 이메일인증, kocca, 한국콘텐츠아카데미" />
<meta name="robots" content="index,follow" />

<title>이메일 인증 - 회원가입 - 이용안내 - 학습지원 | 한국콘텐츠아카데미</title>

<style type="text/css">
    * {margin:0; padding:0;}
    img {border:0; vertical-align:top;}
    ul {list-style:none;}
    body {font-family: '돋움', Dotum; font-size: 12px;}
    #wrap {width:630px; height:300px; margin:0; auto; border:solid; border-color:#8c8b8b;}
</style>

<script src="/js/jquery/1.8.3/jquery.js"></script>
<script type="text/javascript">
    $(function() {
        $("#oCertiNumCheckArea > a").attr("onclick", "");
    });

    /**
     * 입력한 이메일 주소로 인증번호를 발송한다.
     */
    function fnSendCertiNum() {
        if ( $("#oUserInputEmail").val() === "") {
            alert("이메일 주소를 입력해 주십시오.");
            $("#oUserInputEmail").focus();
            return;
        } else if ( fnCheckEmailValidation($("#oUserInputEmail").val()) == false ) {
            alert("EMail 주소를 올바르게 입력해 주십시오.");
            return false;
        } else {

            $("#oEmail").val( $("#oUserInputEmail").val() );
            var param = $("#oEmailCertiForm").serialize();

            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
                ,   url : "/certi/email/sendCertiEmailAjax.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        // 결과 코드 값
                        // S : 성공
                        // EE : 이메일 발송 오류
                        // EI : DB Insert 오류
                        if (data.result == "S") {
                            alert("입력하신 이메일로 인증번호가 발송되었습니다.");
                            $("#oUserInputCertiNum").attr("readonly", false);
                            $("#oBtnCertiCheck").attr("disabled", false);
                            $("#oCertiNumCheckArea > a").attr("onclick", "javascript:fnCheckCertiNum();").attr("style", "cursor:pointer");
                            $("#oSeq").val( data.seq );
                            $("#oCertiNum").val( data.certiNum );
                        } else if (data.result == "EE") {
                            alert("이메일 발송 도중 오류가 발생하였습니다.\n관리자에게 문의하시기 바랍니다.");
                            return;
                        } else if (data.result == "EI") {
                            alert("시스템 오류가 발생하였습니다.\n관리자에게 문의하시기 바랍니다.");
                            return;
                        } else {
                            alert("알 수 없는 오류가 발생하였습니다.\n관리자에게 문의하시기 바랍니다.");
                            return;
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
     * 이메일 및 이메일형태 ID검사
     */
    function fnCheckEmailValidation(strEmail) {
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

        if ( !regDoNot.test(strEmail) && regMust.test(strEmail) ) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * 입력한 인증 번호를 확인한다.
     */
    function fnCheckCertiNum() {
        if( $("#oUserInputCertiNum").val() == "" ) {
            alert("인증번호를 입력하세요.");
            $("#oUserInputCertiNum").focus();
            return;

        } else {
            var param = "seq=" + $("#oSeq").val() + "&user_certi_num=" + $("#oUserInputCertiNum").val();
            // 인증번호 확인
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/certi/email/checkCertiNumAjax.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {
                        // 결과 코드 값
                        if (data.result == "S") {
                            alert("인증이 완료되었습니다.");
                            if ( parent.opener.name == "Parent_window" ) {
                                $("#oEmailCertiForm").attr("action", "<%= resultPage %>");
                                $("#oEmailCertiForm").attr("method", "post");
                                $("#oEmailCertiForm").attr("target", "Parent_window").submit();

                                // parent.opener.location = "/servlet/controller.homepage.MainMemberJoinServlet?p_process=memberInfoInsesrt&certiType=EMAIL&email=" + $("#oEmail").val();
                                // parent.opener.location = "/learn/user/2013/portal/member/zu_MemberInfo_I_new.jsp?certiType=" + paramCertiType + "&email=" + paramEmail;
                            } else {
                                alert("부모 창이 변경되어 회원 가입을 진행할 수 없습니다.");
                            }
                            self.close();
                        } else if (data.result == "EN") {
                            alert("잘못된 인증 번호를 입력하였습니다.\n다시 입력해 주시기 바랍니다.");
                            $("#oUserInputCertiNum").focus();
                            return;
                        } else if (data.result == "ET") {
                            alert("제한된 인증 시간을 초과하였습니다.\n인증번호를 재발급 하시기 바랍니다.");
                            $("#oUserInputCertiNum").val("");
                            $("#oUserInputCertiNum").attr("readonly", "readonly");
                            $("#oCertiNumCheckArea > a").attr("onclick", "");
                            return;
                        } else {
                            alert("알 수 없는 오류가 발생하였습니다.\n관리자에게 문의하시기 바랍니다.");
                            return;
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
     * 인증 창을 닫는다.
     */
    function fnClosewin() {
        self.close();
    }

</script>

<body>

<form id="oEmailCertiForm" name="emailCertiForm">
    <div id="wrap">
        <div style="height:20px; background-color:#8c8b8b; padding:20px;">
            <span style="font-size:16px; color:#fff; font-weight:bold;">이메일 인증</span>
            <div style="position: absolute; left:580px; top: 20px;">
                <a href="javascript:fnClosewin();"><img src="/images/certi/close.gif" alt="이메일 인증 창 닫기"></a>
            </div>
        </div>
        
        <div style="padding:40px;">
            <div style="height:40px;">
                <span style="padding-right:16px; font-weight:bold;"><label for="oUserInputEmail">이메일주소</label></span>
                <input type="text" style="width:320px; height:26px; padding-rigt:" id="oUserInputEmail" name="userInputEmail" />
                <a href="#" onclick="fnSendCertiNum();"><img src="/images/certi/send.gif" style="padding:2px;" alt="인증번호받기" /></a>
            </div>
            <div style="height:40px;" id="oCertiNumCheckArea">
                <span style="padding-right:28px; font-weight:bold;"><label for="oUserInputCertiNum">인증번호</label></span>
                <input type="text" style="width:150px; height:26px;" id="oUserInputCertiNum" name="userInputCertiNum" maxlength="6" readonly />
                <a onclick="javascript:fnCheckCertiNum();"><img src="/images/certi/confirm.gif" style="padding:2px;"alt="확인" /></a>
            </div>
            <div style="padding-top:40px;">
                <span >※ 기재하신 이메일주소로 전송된 인증번호를 10분 이내로 입력하여 주시기 바랍니다.</span> 
            </div>
        </div>
         
    </div>

    <input type="hidden" id="oSeq" name="seq" value="" />
    <input type="hidden" id="oCertiNum" name="certiNum" value="" />
    <input type="hidden" id="oCertiType" name="certiType" value="EMAIL" />
    <input type="hidden" id="oEmail" name="email" value="" />
</form>

</body>
</html>
