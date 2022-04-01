<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    String retUrl = box.getString("retUrl");

    String titleTag = "";
    if ( isApp ) {
        titleTag = "로그인";
    } else if ( isWeb ) {
        titleTag = "로그인 | 한국콘텐츠아카데미";
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= titleTag %></title>
<link href="/mobile/assets/css/style.css" rel="stylesheet">
<script src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script src="/mobile/assets/js/common.js"></script>

<script>
/**
 * 사용자 로그인 체크
 */
function fnLogin() {
    var userId = $("#oUserid").val();
    var pwd = $("#oPwd").val();
    var grcode = $("#oGrcode").val();

    var param = "process=loginProc&grcode=" + grcode + "&userid=" + userId + "&pwd=" + pwd

    if ( userId == "" ) {
        alert("사용자 아이디를 입력하세요.");
        $("#oUserid").focus();
        return false;
    } else  if ( pwd == "" ) {
        alert("사용자 비밀번호를 입력하세요.");
        $("#oPwd").focus();
        return false;
    } else {

        $.ajax({
            type : "post"
        ,   url : "/servlet/controller.mobile.user.LoginServlet"
        ,   dataType : "json"
        ,   data : param
        ,   success : function (data) {
                if ( data.loginResult || data.loginResult == "true" ) { // 로그인 성공

                    fnSetValue(data.userName);

                    $("#oLoginForm").attr("action", "/mobile/jsp/user/loginOk.jsp");
                    $("#oRetUrl").val("<%= retUrl %>");
                    $("#oUserName").val(data.userName);
                    $("#oLoginForm").attr("onsubmit", "return true");
                    $("#oLoginForm").submit();
                    return true;

                } else { // 로그인 실패시
                    alert("아이디 또는 비밀번호가 잘못되었습니다.");
                    return false;
                }
            }
        ,   complete : function(arg1, arg2) {
                // alert("complete : " + arg1 + " :: " + arg2);
            }
        ,   error :  function(arg1, arg2) {
                // alert("error : " + arg1);
            }

        });
    }
}


function fnSetValue( userName ) {

    if( $("input:checkbox[name=autologin]").is(":checked")) {
        fnSetCookie ('userid', $("#oUserid").val(), 365, true);
        fnSetCookie ('username', userName, 365, false);
		fnSetCookie ('pwd', $("#oPwd").val(), 365, true);
		fnSetCookie ('autologin', true, 365, true);
    } else {
        fnSetCookie ('userid', $("#oUserid").val(), 1, true);
        fnSetCookie ('username', userName, 1, false);
        fnSetCookie ('pwd', $("#oPwd").val(), 1, true);
		fnSetCookie ('autologin', false, 365, true);
    }
}


function fnGetValue() {

    var userid = fnGetCookie("userid", true);
	var pwd = fnGetCookie("pwd", true);
	var autologin = eval(fnGetCookie("autologin", true));

	if(typeof(userid) == "undefined") {
	    userid = "";
    }

    if(typeof(pwd) == "undefined") {
	    pwd = "";
    }

    if(userid != null && userid != "") {
        $("#oUserid").val( userid );
    }

	if(pwd != null && pwd != "") {
        $("#oPwd").val( pwd );
    } else {
        $("#oUserid").focus();
    }

    if ( autologin ) {
        $("input:checkbox[name=autologin]").attr("checked", true);
    }

    if ( userid != "" && pwd != "" && autologin) {
        fnLogin();
    }
}

function fnMoveToB2BLoginPage() {
    fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet?process=B2BLoginPage&retUrl=" + encodeURIComponent("<%= retUrl %>") );
}

$(document).ready(function() {
    fnGetValue();
}); 

</script>
</head>
<body>
<form id="oLoginForm" name="loginForm" method="post" action="/servlet/controller.mobile.user.LoginServlet" onsubmit="return false;">
    <input type="hidden" id="oProcess" name="process" value="loginProc" />
    <input type="hidden" id="oGrcode" name="grcode" value="N000001" />
    <input type="hidden" id="oRetUrl" name="retUrl" value="<%= retUrl %>" />
    <input type="hidden" id="oUserName" name="username" value="" />
<div class="login-form">
    <div class="body">
        <div class="login-header">
            <img src="/mobile/assets/img/login-header.png" alt="kocca 한국콘텐츠아카데미" width="165">
        </div>
        <div class="login-field">
            <ul>
                <li><input type="text" id="oUserid" name="userid" placeholder="아이디" tabindex="1"></li>
                <li><input type="password" id="oPwd" name="pwd" placeholder="비밀번호" tabindex="2"></li>
            </ul>
            <button type="submit" class="btn btn-primary" onclick="javascript:fnLogin();" tabindex="4">로그인</button>
        </div>
        <div class="login-auto-check">
            <label><input type="checkbox" id="oAutoLogin" name="autologin" tabindex="3"> 자동로그인</label>
        </div>
        <a href="javascript:fnMoveToB2BLoginPage();" class="btn btn-primary btn-block btn-xl"><i class="icon icon-cir-ar-right"></i>단체회원 로그인</a>
    </div>
    <div class="footer">
        <p><i class="icon icon-cir-check"></i> 한국콘텐츠아카데미 사이트와 동일한 아이디와 비밀번호로 로그인 하실 수 있습니다.</p>
        <p><i class="icon icon-cir-check"></i> 회원가입 및 아이디/비밀번호 찾기는 PC버전(edu.kocca.kr)에서 이용하실 수 있습니다</p>
    </div>
<%
    if ( isWeb ) {
%>
    <a href="javascript:history.back(-1);" class="close" tabindex="5">X</a>
<%
    }
%>
</div>
</form>
</body>
</html>
