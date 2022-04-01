<%
//**********************************************************
//  1. 제      목: 로그인
//  2. 프로그램명 : Login.jsp
//  3. 개      요: 로그인
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.15
//  7. 수      정:
//***********************************************************
%>
                  <!-- login  -->

                  <form name="frmLogin" method="post" action="">
                    <input type="hidden" name="p_process" value="" >

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<% 
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    if (session.getAttribute("userid") == null) {
%>

<SCRIPT LANGUAGE="JavaScript">
<!--

    /* 패스워드 엔터키 체크 */
    function password_enter(e) {
        if (e.keyCode =='13'){  login();  }
    }

    // 로그인
    function login() {
        if (input_chk(frmLogin)){
            frmLogin.p_process.value   = "login";
            frmLogin.action            = "/servlet/controller.homepage.LoginServlet";
            frmLogin.submit();
        }
    }

    // 패스워드 찾기
    function losspwd() {
        //frmLogin.p_process.value   = "golosspwd";
        frmLogin.action            = "/servlet/controller.homepage.LoginServlet?p_process=golosspwd";
        frmLogin.submit();
    }

    // 회원가입
    function member() {
        //frmLogin.p_process.value   = "golosspwd";
        frmLogin.action            = "/servlet/controller.homepage.MemberJoinServlet?p_process=join";
        frmLogin.submit();
    }

    // 입력 체크
    function input_chk(frmLogin)  {
        var chk = ( isNull(frmLogin.elements["p_userid"],"아이디") && isNull(frmLogin.elements["p_pwd"],"비밀번호"));
        return (chk);
    }


/*
    if( getCookie( "userid" ) == "" ) {
      document.frmLogin.p_userid.focus();
    } else {
      document.frmLogin.p_userid.value = getCookie( "userid" );
      document.frmLogin.rememberId.checked = true;
      document.frmLogin.p_pwd.focus();
    }
*/

//-->
</SCRIPT>

                <!-- 로그인 전-->
                <table width="536" height="37" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/homepage/type2/log_boxbg.gif">
                    <tr>
                      <td width="15"><img src="/images/user/game/homepage/type2/log_boxhead.gif"></td>
                      <td width="506" valign="middle"><table width="506" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="112"><img src="/images/user/game/homepage/type2/log_text.gif" width="112" height="19"></td>
                            <td width="21" align="right"><img src="/images/user/game/homepage/type2/login_id.gif"></td>
                            <td width="91"> <input name="p_userid" id="p_userid" type="text" style="width:86px;height:18px;border:1px solid #dcd6d8;" tabindex="1" value="" onkeypress="password_enter(event)"> 
                            </td>
                            <td width="36"><img src="/images/user/game/homepage/type2/login_pw.gif"></td>
                            <td width="90"><input name="p_pwd" type="password" style="width:86px;height:18px;border:1px solid #dcd6d8;" tabindex="2" value="" onkeypress="password_enter(event)" ></td>
                            <td width="156"><a href="javascript:login()"><img src="/images/user/game/homepage/type2/login_btn.gif" align="absmiddle" tabindex="3"></a> 
                              <a href="javascript:losspwd()"><img src="/images/user/game/homepage/type2/login_idpw.gif" align="absmiddle"></a> 
                              <a href="javascript:member()"><img src="/images/user/game/homepage/type2/login_join.gif" align="absmiddle"></a> 
                            </td>
                          </tr>
                        </table></td>
                      <td align="right"><img src="/images/user/game/homepage/type2/log_boxtail.gif" width="15" height="37"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td height="20" colspan="3">&nbsp;</td>
                    </tr>
                  </table> 
                  <!-- //로그인 전-->
<%
    } else {
        String ss_userid   = (String)box.getSession("userid");
        String ss_username = (String)box.getSession("name");
        String ss_gadmin   = (String)box.getSession("gadmin");
    
        int v_totalmil = 0;
        v_totalmil = HomePageMileageBean.TotalMileage(box);
%>
<SCRIPT LANGUAGE="JavaScript">

    function gologout(){
        //frmLogin.p_process.value = "loginpage";
        frmLogin.action          = "/servlet/controller.homepage.LoginServlet?p_process=gologout";
        frmLogin.submit();
    }

<!--

    function adminOpenPage() {
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinAdmin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1024, height = 768, top=0, left=0");
        document.frmLogin.target = "openWinAdmin"
        document.frmLogin.action = "/learn/admin/system/mScreenFset.jsp";
        document.frmLogin.submit();

        farwindow.window.focus();
        document.frmLogin.target = window.self.name;
    }

    function authChange() {
        frmLogin.p_process.value   = "authChange";
        frmLogin.action            = "/servlet/controller.homepage.MainServlet";
        frmLogin.submit();
    }

        // 개인정보변경
    function mem_modify(){
        var url = "/servlet/controller.homepage.MemberInfoServlet?p_process=memberUpdatePageNew";
        frmLogin.action            = url;
        frmLogin.submit();
    }

//-->
</SCRIPT>

                <!-- 로그인 후-->
                  <table width="536" height="37" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/homepage/type2/log_boxbg.gif">
                    <tr> 
                      <td valign="top"><img src="/images/user/game/homepage/type2/logbox_top.gif" width="536" height="9"></td>
                    </tr>
                    <tr> 
                      <td valign="bottom" background="/images/user/game/homepage/type2/logbox_bg.gif"><table width="535" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="19" class="font02"><strong><%=ss_username%></strong>회원님방문을 환영합니다. 
                            </td>
                            <td width="162" rowspan="2" align="center">
                                <%= LoginBean.getAuthSelect(ss_userid, "p_auth", ss_gadmin, "onChange = 'javascript:authChange()'")%>
                                <% if (!(ss_gadmin.equals("ZZ") || ss_gadmin.equals(""))) {                     %>
                                <a href='#' onClick='adminOpenPage()'><img src="/images/user/game/homepage/type2/btn_go.gif" border="0" align="absmiddle" ></a><br>
                                <% }                                                                            %>
                            </td>
                            <td width="141" rowspan="2"><a href="javascript:gologout()"><img src="/images/user/game/homepage/type2/btn_logout_on.gif"></a> 
                              <a href="javascript:mem_modify()"><img src="/images/user/game/homepage/type2/btn_personal.gif" width="65" height="17"></a> 
                            </td>
                          </tr>
                          <!--tr> 
                            <td class="font02"><img src="/images/user/game/homepage/type2/login_milage.gif" align="absmiddle" vspace="5"><strong><%=v_totalmil%></strong>점</td>
                          </tr-->
                        </table></td>
                    </tr>
                    <tr>
                      <td valign="bottom" background="/images/user/game/homepage/type2/logbox_bg.gif"><img src="/images/user/game/homepage/type2/logbox_bo.gif" width="536" height="9"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF"> 
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table> 

                  <!-- //로그인 후-->
<%
    }
%>

    </form>