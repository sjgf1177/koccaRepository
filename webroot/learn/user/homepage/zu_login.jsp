<%
//**********************************************************
//  1. 제      목: 로그인
//  2. 프로그램명 : zu_login.jsp
//  3. 개      요: 로그인
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");
	box.put("leftmenu","1");
	box.put("onLoad","onload=\"winonload();\"");
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topHome.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

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

    // 패스워드 찾기
    function losspwd() {
        form1.p_process.value   = "losspwd";
        form1.action            = "/servlet/controller.homepage.LoginServlet?leftmenu=2";
        form1.submit();
    }
    // 입력 체크
    function input_chk(form1)  {
        var chk = ( isNull(form1.elements["p_userid"],"아이디") && isNull(form1.elements["p_pwd"],"비밀번호"));
        return (chk);
    }

//-->
</SCRIPT>

 <form name="form1" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'>

          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; 로그인</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/common/<%=tem_subimgpath%>/tit_login.jpg" ></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="405" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <!-- 로그인 table -->
                  <table width="405" border="0" cellpadding="0" cellspacing="0" background="/images/user/common/log_bg.gif">
                    <tr> 
                      <td colspan="2" valign="top"><img src="/images/user/common/log_top.gif"></td>
                    </tr>
                    <tr> 
                      <td width="125"><img src="/images/user/common/log_img.jpg"></td>
                      <td width="280" valign="top"><table width="259" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="259" class="padd03">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td align="right"><img src="/images/user/common/log_line.gif" width="214" height="9"></td>
                          </tr>
                          <tr> 
                            <td align="right"><table width="230" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="18"><img src="/images/user/homepage/id.gif"></td>
                                  <td width="108"><input name="p_userid" onkeypress="password_enter(event)" type="password" class="input_login" value="" size="15" tabindex=1></td>
                                  <td width="50" rowspan="2"><a href="javascript:login()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','/images/user/button/btn_login-1_on.gif',1)"><img src="/images/user/button/btn_login-1.gif" name="Image23" width="47" height="40" border="0" tabindex=3></a></td>
                                  <td width="54" rowspan="2"><a href="javascript:losspwd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','/images/user/button/btn_pwsearch_on.gif',1)"><img src="/images/user/button/btn_pwsearch.gif" name="Image24" width="47" height="40" border="0" tabindex=4></a></td>
                                </tr>
                                <tr> 
                                  <td><img src="/images/user/homepage/pw.gif" ></td>
                                  <td><input type="text" name="p_pwd"  onkeypress="password_enter(event)"  class="input_login" value="" size="15" tabindex=2></td>
                                </tr>
                              </table> </td>
                          </tr>
                          <tr> 
                            <td height="8">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td colspan="2" valign="bottom"><img src="/images/user/common/log_bo.gif"></td>
                    </tr>
                  </table>
                  <table width="405" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="10"></td>
                    </tr>
                    <tr>
                      <td class="font_ex">- 비밀번호는 영문 대소문자를 구분합니다.<br>
                        - 최초 비밀번호는 주민번호 뒷자리 7자 입니다.</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
</form>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
