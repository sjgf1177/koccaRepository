<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.library.StringManager" %>
<%@ page import = "java.util.ArrayList" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>

<%
    ArrayList commonCodeList = (ArrayList)request.getAttribute("commonCodeList");
    DataBox dbox = null;

    String retUrl = box.getString("retUrl");

    String pageTitle = "열린강좌";

    String titleTag = "";
    if ( isApp ) {
        titleTag = "로그인";
    } else if ( isWeb ) {
        titleTag = "그룹회원 로그인 | 한국콘텐츠아카데미";
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
 * 분류별 그룹코드 목록 조회
 */
function fnSelectEduGrp( groupType ) {
    $("#oSelectGrcode").empty();
    $("#oSelectGrcode").append("<option value=''>선택</option>");

    if ( groupType != "") {
        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.mobile.common.CodeServlet"
            ,   dataType : "json"
            ,   data : "process=groupCodeList&groupType=" + groupType
            ,   success : function (data) {

                    $.each( data, function(){
                        $("#oSelectGrcode").append("<option value='" + this.grcode + "'>" + this.grcodenm + "</option>");
                    });

                }
            ,   complete : function(arg1, arg2) {
                }
            ,   error :  function(arg1, arg2) {
                }

        });
    }
}

/**
 * 사용자 로그인 체크
 */
function fnLogin() {
    var groupType = $("#oGroupType").val();
    var grcode = $("#oGrcode").val();
    var grPrefix = $("#oGrPrefix").val();
    var userId = $("#oUserid").val();
    var pwd = $("#oPwd").val();

    var param = "process=loginProc&grcode=" + grcode + "&userid=" + userId + "&pwd=" + pwd + "&grPrefix=" + grPrefix;

    if ( groupType == "" ) {
        alert("분류를 선택하세요.");
        $("#oGroupType").focus();
        return false;

    } else  if ( grcode == "" ) {
        alert("교육그룹을 선택하세요.");
        $("#oSelectGrcode").focus();
        return false;

    } else if ( userId == "" ) {
        alert("사용자 아이디를 입력하세요.");
        $("#oUserid").focus();
        return false;

    } else if ( pwd == "" ) {
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

                    $("#oLoginForm").attr("action", "/mobile/jsp/user/loginOk.jsp");
                    $("#oUserName").val(data.userName);
                    $("#oLoginForm").attr("onsubmit", "return true");
                    $("#oLoginForm").submit();

                } else { // 로그인 실패시
                    alert("아이디 또는 비밀번호가 잘못되었습니다.");
                    return false;
                }
            }
        ,   complete : function(arg1, arg2) {
                // alert("complete : " + arg1);
            }
        ,   error :  function(arg1, arg2) {
                // alert("error : " + arg1);
            }

        });

    }
}

/**
 * 그룹코드 선택상자(oSelectGrcode)의 값을 "|"로 분리하여 그룹코드(oGrcode)와 그룹별 접두어(oGrPrefix) 항목에 각각 설정한다.
 * @param selectedValue - 선택된 값
 */
function fnSetGrcode( selectedValue ) {
    var temp = "";
    if (selectedValue.indexOf("|") > -1) {
        temp = selectedValue.split("|");
        $("#oGrcode").val( temp[0] );
        $("#oGrPrefix").val( temp[1] );
    } else {
        $("#oGrcode").val(selectedValue);
        $("#oGrPrefix").val( "" );
    }
}

/**
 * 로그인 성공시 자동 로그인 체크값에 따라 각 입력 항목의 값을 쿠키에 저장한다.
 */
function fnSetValue( userName ) {

    if( $("input:checkbox[name=autologin]").is(":checked")) {
        fnSetCookie ('userid', $("#oUserid").val(), 365, true);
        fnSetCookie ('username', userName, 365, false);
		fnSetCookie ('pwd', $("#oPwd").val(), 365, true);
        fnSetCookie ('groupType', $("#oGroupType").val(), 365, false);
        fnSetCookie ('selectGrcode', $("#oSelectGrcode").val(), 365, false);
		fnSetCookie ('autologin', true, 365, true);
    } else {
        fnSetCookie ('userid', $("#oUserid").val(), 1, true);
        fnSetCookie ('username', userName, 1, false);
        fnSetCookie ('pwd', $("#oPwd").val(), 1, true);
        fnSetCookie ('groupType', $("#oGroupType").val(), 1, false);
        fnSetCookie ('selectGrcode', $("#oSelectGrcode").val(), 1, true);
		fnSetCookie ('autologin', false, 365, true);
    }
}

/**
 * 페이지 로딩시 쿠키 값을 체크한 후, 쿠키 값이 존재하면 해당 항목에 값을 설정한다.
 */
function fnGetValue() {

    var userid = fnGetCookie("userid", true);
	var pwd = fnGetCookie("pwd", true);
    var groupType = fnGetCookie("groupType", true);
	var selectGrcode = fnGetCookie("selectGrcode", true);
	var autologin = eval(fnGetCookie("autologin", true));

	if(typeof(userid) == "undefined") {
	    userid = "";
    }

    if(typeof(pwd) == "undefined") {
	    pwd = "";
    }

	if(typeof(grcode) == "undefined") {
	    grcode = "";
    }

    if(userid != null && userid != "") {
        $("#oUserid").val( userid );
    }

	if(pwd != null && pwd != "") {
        $("#oPwd").val( pwd );
    }

    if(groupType != null && groupType != "") {
        $("#oGroupType").val( groupType );
    }

    if(selectGrcode != null && selectGrcode != "") {
        $("#oSelectGrcode").val( selectGrcode );
    }

    if ( autologin ) {
        $("input:checkbox[name=autologin]").attr("checked", true);
    }

    if ( userid != "" && pwd != "" && autologin) {
        fnLogin();
    }
}

$(function() {
    // fnGetValue();
});
</script>
</head>
<body>
<form id="oLoginForm" name="loginForm" method="post" action="/servlet/controller.mobile.user.LoginServlet" onsubmit="return false;">
    <input type="hidden" id="oProcess" name="process" value="loginProc" />
    <input type="hidden" id="oRetUrl" name="retUrl" value="<%= retUrl %>" />
    <input type="text" id="oGrcode" name="grcode" value="" />
    <input type="text" id="oGrPrefix" name="grPrefix" value="" />
    <input type="hidden" id="oUserName" name="username" value="" />

<div class="login-form">
	<div class="body2">
		<ul class="category-field">
			<li>
				<label>분류</label>
				<select id="oGroupType" name="groupType" onchange="fnSelectEduGrp(this.value);">
					<option value=""> 선택 </option>
<%
    for ( int i = 0 ; i < commonCodeList.size() ; i++ ) {
        dbox = (DataBox)commonCodeList.get(i);
%>
					<option value="<%= dbox.getString("d_code") %>"><%= dbox.getString("d_codenm") %></option>
<%
    }
%>
				</select>
			</li>
			<li>
				<label>그룹</label>
				<select id="oSelectGrcode" name="selectGrcode" onchange="fnSetGrcode(this.value);">
					<option value="">선택</option>
				</select>
			</li>
		</ul>
		<div class="login-field">
			<ul>
				<li><input type="text" id="oUserid" name="userid" placeholder="아이디"></li>
				<li><input type="password" id="oPwd" name="pwd" placeholder="비밀번호"></li>
			</ul>
			<button type="submit" class="btn btn-primary" onclick="javascript:fnLogin();">로그인</button>
		</div>
	</div>
	<div class="footer">
		<p><i class="icon icon-cir-check"></i> 사이버 연수원 사이트와 동일한 아이디와 비밀번호로 로그인 하실 수 있습니다.</p>
		<p><i class="icon icon-cir-check"></i> 회원가입 및 아이디/비밀번호 찾기는 PC버전에서 이용하실 수 있습니다</p>
	</div>
<%
    if ( isWeb ) {
%>
	<a href="javascript:history.back(-1);" class="close">X</a>
<%
    }
%>

</div>
</body>
</html>