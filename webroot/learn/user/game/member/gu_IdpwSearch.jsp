<%
//**********************************************************
//  1. 제      목: 패스워드 찾기
//  2. 프로그램명 : LossPwd.jsp
//  3. 개      요: 패스워드 찾기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.15
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

    String s_userid  = box.getSession("userid");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMember.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


 <SCRIPT LANGUAGE="JavaScript">
 <!--

// 패스워드 찾기
 function searchPwd() {

    if(document.form1.p_userid.value == ""){
        alert("아이디를 입력하세요!");
        form1.p_userid.focus();
        return;
    }

    if(document.form1.p_name_1.value == ""){
        alert("이름을 입력하세요!");
        form1.p_name_1.focus();
        return;
    }

    if (document.form1.p_resno_1.value=="") {
        alert("주민번호를 정확히 입력하세요.");
        form1.p_resno_1.focus();
        return;
    }

    if (document.form1.p_resno_2.value=="") {
        alert("주민번호를 정확히 입력하세요.");
        form1.p_resno_2.focus();
        return;
    }

    document.form1.p_process.value = "sendmail";
    document.form1.action = "/servlet/controller.homepage.LoginServlet";
    document.form1.submit();
}

// ID 찾기
function searchId() {

    if(document.form1.p_name.value == ""){
        alert("이름을 입력하세요!");
        form1.p_name.focus();
        return;
    }

    if (document.form1.p_resno1.value=="") {
        alert("주민번호를 정확히 입력하세요.");
        form1.p_resno1.focus();
        return;
    }

    if (document.form1.p_resno2.value=="") {
        alert("주민번호를 정확히 입력하세요.");
        form1.p_resno2.focus();
        return;
    }


    document.form1.p_process.value = "lossidresult";
    document.form1.action = "/servlet/controller.homepage.LoginServlet";
    document.form1.submit();
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

//-->
</SCRIPT>

 <form name="form1" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'>

    <!-- title -->
        <div style="width:720px;height:35px;margin-bottom:20px;background-image:url('/images/user/game/member/<%=tem_subimgpath%>/tit_idpw.gif');text-align:right;padding:19px 25px 0px 0px;font-size:11px;color:#000000;">
            <img src="/images/user/game/member/blt01.gif"> Home &gt; 아이디/비밀번호 찾기
        </div>
        <!-- //title -->
            <img src="/images/user/game/member/idpw_bn.gif"><br>
            <br>
            <table cellpadding="0" cellspacing="0" width="720" >
            <col width=50%><col width=7><col width=50%>
            <tr>
                <td><img src="/images/user/game/member/idpw_st01.gif" vspace="5"></td>
                <td></td>
                <td><img src="/images/user/game/member/idpw_st02.gif" vspace="5"></td>
            </tr>
            <tr>
                <td style="border-top:5px solid #dfd7d4;border-bottom:5px solid #dfd7d4;border-left:5px solid #dfd7d4;">
                  <table cellpadding="0" cellspacing="0" >
                    <tr>
                        <td><img src="/images/user/game/member/idpw_name.gif"></td>
                        <td><input name="p_name" type="text" class="input" style="width:175px" value=""></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><img src="/images/user/game/member/idpw_regno.gif"></td>
                        <td style="padding:5px 0px;">
                            <input name="p_resno1" type="text" class="input" style="width:75px" onkeyup="moveFocus(6,this,this.form.p_resno2);" OnKeypress="onlyNumber();">
                            <img src="/images/user/game/member/idpw_dash.gif" hspace=5 align="absmiddle">
                            <input name="p_resno2" type="password" class="input" style="width:75px" OnKeypress="onlyNumber();"></td>
                        <td style="padding-left:25"><a href="javascript:searchId()"><img src="/images/user/game/button/btn_confirm.gif"></a></td>
                    </tr>
                  </table>
                </td>
                <td style="border-top:5px solid #dfd7d4;border-bottom:5px solid #dfd7d4;"><img src="/images/user/game/member/idpw_hdot.gif"></td>
                <td style="border-top:5px solid #dfd7d4;border-bottom:5px solid #dfd7d4;border-right:5px solid #dfd7d4;">
                  <table cellpadding="0" cellspacing="0" >
                    <tr>
                        <td><img src="/images/user/game/member/idpw_id.gif"></td>
                        <td><input name="p_userid" type="text" class="input" style="width:175px"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><img src="/images/user/game/member/idpw_name.gif"></td>
                        <td><input name="p_name_1" type="text" class="input" style="width:175px" value=""></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><img src="/images/user/game/member/idpw_regno.gif"></td>
                        <td style="padding:5px 0px;">
                            <input name="p_resno_1" type="text" class="input" style="width:75px" onkeyup="moveFocus(6,this,this.form.p_resno_2);" OnKeypress="onlyNumber();">
                            <img src="/images/user/game/member/idpw_dash.gif" hspace="5" align="absmiddle">
                            <input name="p_resno_2" type="password" class="input" style="width:75px" OnKeypress="onlyNumber();"></td>
                        <td style="padding-left:25"><a href="javascript:searchPwd()"><img src="/images/user/game/button/btn_confirm.gif"></a></td>
                    </tr>
                  </table>
                </td>
            </tr>
            </table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
