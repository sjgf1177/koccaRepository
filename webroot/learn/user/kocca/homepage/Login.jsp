<%
//**********************************************************
//  1. 제      목: 로그인
//  2. 프로그램명 : Login.jsp
//  3. 개      요: 로그인
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
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
            frmLogin.action            = "/servlet/controller.homepage.KLoginServlet";
            frmLogin.submit();
        }
    }

    // 패스워드 찾기
    function losspwd() {
        //frmLogin.p_process.value   = "golosspwd";
        frmLogin.action            = "/servlet/controller.homepage.KLoginServlet?p_process=golosspwd";
        frmLogin.submit();
    }

	// 회원가입
    function member() {
        //frmLogin.p_process.value   = "golosspwd";
        frmLogin.action            = "/servlet/controller.homepage.KMemberJoinServlet?p_process=join";
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
<table width="166" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td colspan="3"><img src="/images/user/kocca/homepage/type1/login_01.gif" width="166" height="20"></td>
	  </tr>
	  <tr> 
		<td width="6"><img src="/images/user/kocca/homepage/type1/login_bg_01.gif" width="6" height="72"></td>
		<td width="151" background="/images/user/kocca/homepage/type1/login_bg.gif"><table width="151" border="0" cellspacing="0" cellpadding="0">
			<tr> 
			  <td width="1" >&nbsp; </td>
			  <td width="35" height="22" ><img src="/images/user/kocca/homepage/type1/i_id.gif" width="9" height="7"></td>
			  <td width="72"><input name="p_userid" type="text" size="11" tabindex="1" value="" onkeypress="password_enter(event)"></td>
			  <td width="40" rowspan="2"><div align="center"><a href="javascript:login()"><img src="/images/user/kocca/homepage/type1/btn_login.gif" width="33" height="38" tabindex="3"></div></td>
			</tr>
			<tr> 
			  <td >&nbsp;</td>
			  <td height="23" ><img src="/images/user/kocca/homepage/type1/i_pass.gif" width="26" height="7"></td>
			  <td><input name="p_pwd" type="password" size="11" tabindex="2" value="" onkeypress="password_enter(event)" ></td>
			</tr>
			<tr> 
			  <td height="5" colspan="4"></td>
			</tr>
			<tr> 
			  <td height="22" colspan="4"><div align="right"><a href="javascript:member()"><img src="/images/user/kocca/homepage/type1/btn_joinus.gif" width="66" height="18" border="0"></a><img src="/images/user/kocca/homepage/type1/blank.gif" width="3" height="10"><a href="javascript:losspwd()"><img src="/images/user/kocca/homepage/type1/btn_idpw_search.gif" width="66" height="18" border="0"></a><img src="/images/user/kocca/homepage/type1/blank.gif" width="2" height="10"></div></td>
			</tr>
		  </table>
	</td>
		<td width="5"><img src="/images/user/kocca/homepage/type1/login_bg_02.gif" width="5" height="72"></td>
	  </tr>
	  <tr> 
		<td colspan="3"><img src="/images/user/kocca/homepage/type1/login_02.gif" width="166" height="10"></td>
	  </tr>
	  <tr> 
		<td height="8" colspan="3"></td>
	  </tr>
	</table>

			<!-- 로그인 전-->

<%} else {
	
	String ss_userid   = (String)box.getSession("userid");
	String ss_username = (String)box.getSession("name");
	String ss_gadmin   = (String)box.getSession("gadmin");

	int v_totalmil = 0;
	v_totalmil = HomePageMileageBean.TotalMileage(box);
%>
<SCRIPT LANGUAGE="JavaScript">

	function gologout(){
		//frmLogin.p_process.value = "loginpage";
		frmLogin.action			 = "/servlet/controller.homepage.KLoginServlet?p_process=gologout";
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

	// 정보변경
	function mem_modify(){
		var url = "/servlet/controller.homepage.KMemberInfoServlet?p_process=memberUpdatePage";
		frmLogin.action            = url;
        frmLogin.submit();
	}

//-->
</SCRIPT>

	<!-- 로그인 후-->
			<table width="166" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/homepage/type1/login_01.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top" background="/images/user/kocca/homepage/type1/login_bg.jpg"><table width="150" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="150" class="font02"><strong><%=ss_username%></strong>회원님<br>
                                    방문을 환영합니다. </td>
                                </tr>
                                <!--임시처리-->
                                <!--tr> 
                                  <td align="center" bgcolor="E8F1F5"><img src="/images/user/game/homepage/type1/login_milage.gif" align="absmiddle" vspace="5"><span class="font02"><strong><%=v_totalmil%></strong>점</span></td>
                                </tr>
                                <tr> 
                                  <td height="4"></td>
                                </tr-->
                                <tr> 
                                  <td align="center">
										<a href="javascript:gologout()"><img src="/images/user/kocca/homepage/type1/btn_logout.gif" width="66" height="18" border="0"></a><img src="/images/user/kocca/homepage/type1/blank.gif" width="3" height="10">
										<a href="javascript:mem_modify()"><img src="/images/user/kocca/homepage/type1/btn_pe.gif"></a></td>
                                </tr>
                                <tr> 
                                  <td height="4"></td>
                                </tr>
                                <tr>
								  <td align="center" > 
									 <%= LoginBean.getAuthSelect(ss_userid, "p_auth", ss_gadmin, "onChange = 'javascript:authChange()'")%>
									 <% if (!(ss_gadmin.equals("ZZ") || ss_gadmin.equals(""))) {                     %>
									 <a href='#' onClick='adminOpenPage()'><img src="/images/user/kocca/homepage/type1/btn_go.gif"  align="absmiddle"></a>
									 <% }                                                                            %>
								  </td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/kocca/homepage/type1/login_02.gif" width="166" height="10"></td>
                          </tr>
                          <tr> 
                            <td height="8"></td>
                          </tr>
                        </table>			
<%}%>
			<!-- //로그인 후-->
	</form>

