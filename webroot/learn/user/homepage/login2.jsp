                  <!-- login  -->

                  <form name="frmLogin" method="post" action="">
                    <input type="hidden" name="p_process" value="" >

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
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
        frmLogin.p_process.value   = "losspwd";
        frmLogin.action            = "/servlet/controller.homepage.LoginServlet?leftmenu=2";
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
            <table width="315" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="34" align="center">
                 <table width="304" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="13"><img src="/images/user/homepage/id.gif"></td>
                      <td width="90"><input type="text" name="p_userid" onkeypress="password_enter(event)"  class="input_login" value="" size="12"></td>
                      <td width="23" align="center"><img src="/images/user/homepage/pw.gif" ></td>
                      <td width="90"><input type="password" name="p_pwd"  onkeypress="password_enter(event)"  class="input_login" value="" size="12"></td>
                      <td width="39" align="center"><a href="javascript:login()"><img src="/images/user/homepage/type2/btn_login.gif" border="0" ></a></td>
                      <td width="49" align="center"><a href="javascript:losspwd()"><img src="/images/user/homepage/type2/btn_idpw.gif" border="0" ></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td height="25" class="mcolor2">
                </td>
              </tr>
                </form>
            </table>


<% } else {         %>

<%
                        String ss_userid   = (String)box.getSession("userid");
                        String ss_username = (String)box.getSession("name");
                        String ss_gadmin   = (String)box.getSession("gadmin");
%>

            <table width="315" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="34" align="center">
                 <table width="304" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="152" class="font02">- <strong><%=ss_username%></strong>님 좋은 하루되세요!! </td>
                      <td width="152"><a href="javascript:goLoginout()"><img src="/images/user/homepage/type2/btn_logout.gif"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td height="25" class="mcolor2"> 
                  <%= LoginBean.getAuthSelect(ss_userid, "p_auth", ss_gadmin, "onChange = 'javascript:authChange()'")%>
                  <% if (!(ss_gadmin.equals("ZZ") || ss_gadmin.equals(""))) {                     %>
                  <a href='#' onClick='adminOpenPage()'><img src="/images/user/homepage/btn_go.gif"  border="0" align="absmiddle"></a>
                  <% }                                                                            %>
                </td>
              </tr>
                </form>
            </table>

<SCRIPT LANGUAGE="JavaScript">
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

//-->
</SCRIPT>

<% }                %>

