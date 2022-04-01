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
    	var frm = document.frmLogin;
        if (input_chk()){
        	frm.p_process.value   = "login";
        	frm.action            = "/servlet/controller.homepage.LoginServlet";
        	frm.submit();
        }
    }

    // 패스워드 찾기
    function losspwd() {
    	var frm = document.frmLogin;
        //frmLogin.p_process.value   = "golosspwd";
        frm.action            = "/servlet/controller.homepage.LoginServlet?p_process=golosspwd";
        frm.submit();
    }

	// 회원가입
    function member() {
    	var frm = document.frmLogin;
        //frmLogin.p_process.value   = "golosspwd";
        frm.action            = "/servlet/controller.homepage.MemberJoinServlet?p_process=join";
        frm.submit();
    }

    // 입력 체크
    function input_chk()  {
        //var chk = ( isNull(frmLogin.elements["p_userid"],"아이디") && isNull(frmLogin.elements["p_pwd"],"비밀번호"));
        var frm = document.frmLogin;

        var userid = frm.p_userid;
        var passwd = frm.p_pwd;

        if(isNull(userid, "아이디") || isNull(passwd, "패스워드")) return true;
        
        return false;
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

<form name="frmLogin" method="post" action="">
                    <input type="hidden" name="p_process" value="" >

		<!-- 로그인 전-->
                <table width="536" height="37" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/homepage/type2/log_boxbg.gif">
                  <tr> 
                    <td valign="top"><img src="/images/user/game/homepage/type2/logbox_top.gif" width="536" height="9"></td>
                  </tr>
                  <tr> 
                    <td align="center" background="/images/user/game/homepage/type2/logbox_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                          <td width="112"><img src="/images/user/game/homepage/type2/log_text.gif"></td>
                          <td width="21" align="right"><img src="/images/user/game/homepage/type2/login_id.gif"></td>
                          <td> <input name="p_userid" id="p_userid" type="text" class="input5" size="12" onkeypress="password_enter(event)"> 
                          </td>
                          <td width="36"><img src="/images/user/game/homepage/type2/login_pw.gif"></td>
                          <td><input name="p_pwd" id="p_pwd" type="password" class="input5" size="12"onkeypress="password_enter(event)"></td>
                          <td><a href="javascript:login()"><img src="/images/user/game/homepage/type2/login_btn.gif" align="absmiddle"></a> 
                            <a href="javascript:losspwd()"><img src="/images/user/game/homepage/type2/login_idpw.gif" align="absmiddle"></a>
                            <a href="javascript:member()"><img src="/images/user/game/homepage/type2/login_join.gif" align="absmiddle"></a>
                          </td>
                        </tr>
                      </table></td>
                  </tr>
                  <tr> 
                    <td valign="bottom" background="/images/user/game/homepage/type2/logbox_bg.gif"><img src="/images/user/game/homepage/type2/logbox_bo.gif" width="536" height="9"></td>
                  </tr>
                  <tr bgcolor="#FFFFFF"> 
                    <td height="10"></td>
                  </tr>
                </table>
		<!-- //로그인 전-->
		<%} else {
		String ss_userid   = (String)box.getSession("userid");
		String ss_username = (String)box.getSession("name");
		String ss_gadmin   = (String)box.getSession("gadmin");
	
		int v_totalmil = 0;
		v_totalmil = HomePageMileageBean.TotalMileage(box);
%>
<SCRIPT LANGUAGE="JavaScript">

	function gologout(){
		var frm = document.frmLogin;
		//frmLogin.p_process.value = "loginpage";
		frm.action			 = "/servlet/controller.homepage.LoginServlet?p_process=gologout";
		frm.submit();
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
    	document.frmLogin.p_process.value   = "authChange";
    	document.frmLogin.action            = "/servlet/controller.homepage.MainServlet";
    	document.frmLogin.submit();
    }

		// 개인정보변경
	function mem_modify(){
		var url = "/servlet/controller.homepage.MemberInfoServlet?p_process=memberUpdatePageNew";
		document.frmLogin.action            = url;
		document.frmLogin.submit();
	}

//-->
</SCRIPT>

		
		<!-- 로그인 후-->
		<table width="536" height="37" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/homepage/type2/log_boxbg.gif">
                    <tr> 
                      <td valign="top"><img src="/images/user/game/homepage/type2/logbox_top.gif" width="536" height="9"></td>
                    </tr>
                    <tr> 
                      <td valign="bottom" background="/images/user/game/homepage/type2/logbox_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
			    <td><img src="/images/user/game/homepage/type2/log_texts.gif"></td>
                            <td height="19" class="font02"><strong><%=ss_username%></strong>회원님방문을 환영합니다. 
                            </td>
                            <td rowspan="2" align="center">
								<%= LoginBean.getAuthSelect(ss_userid, "p_auth", ss_gadmin, "onChange = 'javascript:authChange()'")%>
								<% if (!(ss_gadmin.equals("ZZ") || ss_gadmin.equals(""))) {                     %>
								<a href='#' onClick='adminOpenPage()'><img src="/images/user/game/homepage/type2/btn_go.gif"  align="absmiddle"></a>
								<% } %>  
                            </td>
                            <td width="141" rowspan="2"><a href="javascript:gologout()"><img src="/images/user/game/homepage/type2/btn_logout_on.gif"></a>
                              <a href="javascript:mem_modify()"><img src="/images/user/game/homepage/type2/btn_personal.gif"></a>
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
                      <td height="10"></td>
                    </tr>
                  </table> 

		<%}%>
		<!-- //로그인 후-->
	</form>