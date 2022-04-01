<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>
<%
    String retUrl = box.getString("retUrl");

    String titleTag = "";
    if ( isApp ) {
        titleTag = "�α���";
    } else if ( isWeb ) {
        titleTag = "�α��� | �ѱ���������ī����";
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
 * ����� �α��� üũ
 */
function fnLogin() {
    var userId = $("#oUserid").val();
    var pwd = $("#oPwd").val();
    var grcode = $("#oGrcode").val();

    var param = "process=loginProc&grcode=" + grcode + "&userid=" + userId + "&pwd=" + pwd

    if ( userId == "" ) {
        alert("����� ���̵� �Է��ϼ���.");
        $("#oUserid").focus();
        return false;
    } else  if ( pwd == "" ) {
        alert("����� ��й�ȣ�� �Է��ϼ���.");
        $("#oPwd").focus();
        return false;
    } else {

        $.ajax({
            type : "post"
        ,   url : "/servlet/controller.mobile.user.LoginServlet"
        ,   dataType : "json"
        ,   data : param
        ,   success : function (data) {
                if ( data.loginResult || data.loginResult == "true" ) { // �α��� ����

                    fnSetValue(data.userName);

                    $("#oLoginForm").attr("action", "/mobile/jsp/user/loginOk.jsp");
                    $("#oRetUrl").val("<%= retUrl %>");
                    $("#oUserName").val(data.userName);
                    $("#oLoginForm").attr("onsubmit", "return true");
                    $("#oLoginForm").submit();
                    return true;

                } else { // �α��� ���н�
                    alert("���̵� �Ǵ� ��й�ȣ�� �߸��Ǿ����ϴ�.");
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
            <img src="/mobile/assets/img/login-header.png" alt="kocca �ѱ���������ī����" width="165">
        </div>
        <div class="login-field">
            <ul>
                <li><input type="text" id="oUserid" name="userid" placeholder="���̵�" tabindex="1"></li>
                <li><input type="password" id="oPwd" name="pwd" placeholder="��й�ȣ" tabindex="2"></li>
            </ul>
            <button type="submit" class="btn btn-primary" onclick="javascript:fnLogin();" tabindex="4">�α���</button>
        </div>
        <div class="login-auto-check">
            <label><input type="checkbox" id="oAutoLogin" name="autologin" tabindex="3"> �ڵ��α���</label>
        </div>
        <a href="javascript:fnMoveToB2BLoginPage();" class="btn btn-primary btn-block btn-xl"><i class="icon icon-cir-ar-right"></i>��üȸ�� �α���</a>
    </div>
    <div class="footer">
        <p><i class="icon icon-cir-check"></i> �ѱ���������ī���� ����Ʈ�� ������ ���̵�� ��й�ȣ�� �α��� �Ͻ� �� �ֽ��ϴ�.</p>
        <p><i class="icon icon-cir-check"></i> ȸ������ �� ���̵�/��й�ȣ ã��� PC����(edu.kocca.kr)���� �̿��Ͻ� �� �ֽ��ϴ�</p>
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