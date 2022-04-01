<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>

<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.homepage.LoginBean" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String s_userid  = box.getSession("userid");
	box.put("onLoad","onload=\"winonload();\"");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script language="javascript" src="/script/mainscript.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
</head>

<%box.put("mainYn", "N"); %>
<%@ page import = "com.credu.homepage.LoginBean" %>

<%
    if(box.getSession("tem_grcode").equals("")){
        box.setSession("tem_grcode","N000001");
    }
    
    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");

    if (topWarp_gadmin.equals("")) {
        box.setSession("gadmin", "ZZ");
        topWarp_gadmin = box.getSession("gadmin");
    }
    String referer ="4124124"+ request.getHeader("REFERER");
    if(referer.indexOf("http://localhost")>0 || referer.indexOf("http://lms.kocca.or.kr")>0)
        referer="Y";
    else
        referer="";

    String mainYN=box.get("mainYn");
    String init_margin="";
    String init_margin1="";
    String init_margin2="";
    if(mainYN.equals(""))
    {
        
        init_margin="30";
        init_margin1="10";
        init_margin2="30";
    
    }
    else
    {
        init_margin="20";
        init_margin1="5";
        init_margin2="30";
    }
 %>

<script language="JavaScript" type="text/JavaScript">
<!--
<%
if (!topWarp_userid.equals("") && !topWarp_gadmin.equals("")){
    if(!topWarp_gadmin.equals("ZZ")){
%>
    window.onload = function () {
    document.location.href="/learn/admin/kocca/mScreenFset.jsp";

    }
<%
    }
}
%>
    // 페이지 초기화
    function winonload() {
        document.form1.p_userid.focus();
    }

    /* 패스워드 엔터키 체크 */
    function password_enter(e) {
        if (e.keyCode =='13'){  login();  }
    }

    // 로그인
    function login() {    	
        if (input_chk(form1)){

            form1.p_process.value   = "login";

            form1.action            = "/servlet/controller.homepage.LoginServlet";
            form1.submit();
        }
    }

    // 입력 체크
    function input_chk(form1)  {
        // var chk = ( isNull(form1.elements["p_userid"],"아이디") && isNull(form1.elements["p_pwd"],"비밀번호") && isNull(form1.elements["p_auth"],"권한") );
        // return (chk);
        if(document.form1.p_userid.value=="" || document.form1.p_userid.value==null){
            alert("아이디를 입력하세요.");
            document.form1.p_userid.focus();
            return(false);
        }else if(document.form1.p_pwd.value=="" || document.form1.p_pwd.value==null){
            alert("비밀번호를 입력하세요.");
            document.form1.p_pwd.focus();
            return(false);
        }else if(document.form1.p_auth.value=="" || document.form1.p_auth.value==null){
            alert("권한을 선택하세요.");
            document.form1.p_auth.focus();
            return(false);
        }else {
            return(true);
        }
        
    }
//-->
</script>

<body>
<!-- form 시작 -->
<form name="form1"  action="" method="post">
    <input type="hidden" name="p_process"    value="" />
    <input type="hidden" name="p_isAdmin"    value="Y" />

<table width="100%" border="0" cellspacing="0" cellpadding="0" id="login" style="margin-top: 100px;">

    <tr>
        <td valign="top" align="center">
            <img src="/images/portal/member/membership/loginlogo.png" width="160" height="100" alt="KOCCA 한국콘텐츠아카데미 로그인"/>
        </td>
    </tr>  
    <tr>
        <td align="center" valign="bottom">
            
            <table width="400" border="0" cellspacing="2" cellpadding="0" id="login_form">
                <tr>
                    <td align="left">
                        <img src="/images/portal/homepage_renewal/member/tit_loginbox01.gif" alt="아이디" /></td>
                    <td>
                        <input type="text" name="p_userid" id="p_userid" style="width:202px; height:24px;" onkeypress="password_enter(event)" tabindex="1"/>
                    </td>
                    <td rowspan="3">
                        <a href="javascript:login();"><img src="/images/portal/homepage_renewal/member/btn_login02.gif" alt="로그인" tabindex="4" style="width: 86px;height: 59px;"/></a>
                    </td>
                </tr>

                <tr>
                    <td align="left" width="87"><img src="/images/portal/homepage_renewal/member/tit_loginbox02.gif" alt="패스워드" /></td>
                    <td>
                        <input type="password" name="p_pwd" id="p_pwd" style="width:202px; height:24px;" onkeypress="password_enter(event)" tabindex="2"/>
                    </td>
                </tr>
                <tr>
                    <td width="87"> 권한 </td>
                    <td>
                        <%= LoginBean.getAuthSelect("p_auth") %>
                    </td>
                </tr>
            </table>
            
        </td>
    </tr>
    <tr>
        <td height="44" align="center"><img src="/images/portal/homepage_renewal/member/line_blue02.gif" /></td>
    </tr>
</table>

      	
</form>
<!-- form 끝 -->

</body>
</html>
