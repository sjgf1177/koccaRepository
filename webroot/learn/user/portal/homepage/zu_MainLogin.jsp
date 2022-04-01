
<%
//**********************************************************
//  1. 제      목: 로그인
//  2. 프로그램명 : zu_MainLogin.jsp
//  3. 개      요: 로그인
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");
	box.put("onLoad","onload=\"winonload();\"");

	String p_eventgubun = box.getString("p_eventgubun");   

	box.put("p_eduservicesul", box.getString("p_eduservicesul"));
	
	
	
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

<body>

<%@ include file="/learn/user/portal/include/site_info.jsp" %>
<%box.put("mainYn", "N"); %>
<%@ include file="/learn/user/portal/include/common.jsp" %>
<%@ page import = "com.credu.homepage.LoginBean" %>

	<%
	    String v_indexURLGLB = "";     
		String s_tem_grcodeGLB = box.getSession("tem_grcode");
        v_indexURLGLB = "/index" + s_tem_grcodeGLB + ".jsp";


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
            if(s_tem_grcodeGLB.equals("N000001"))
            {
                init_margin="8";
                init_margin1="0";
                init_margin2="34";
            }
            else
            {
                init_margin="30";
                init_margin1="10";
                init_margin2="30";
            }
        }
        else
        {
            init_margin="20";
            init_margin1="5";
            init_margin2="30";
        }
     %>
 <%if( box.getSession("tem_grcode").equals("N000001")) { %>
 	<link href="/css/portal/homepage_renewal/style.css" rel="stylesheet" type="text/css" />
 	<link href="/css/portal/homepage_renewal/member.css" rel="stylesheet" type="text/css" />
 <%} %>

<script language="JavaScript" type="text/JavaScript">
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

			//교육서비스 만족도 특정 인원만 응시..
<%      	if( box.getString("p_eduservicesul").equals("Y") ) {  %>
				form1.p_eduservicesul.value   = "Y";
<%          } %>
        	
            form1.p_process.value   = "login";
            form1.p_topuserid.value = form1.p_userid.value;
            form1.p_toppwd.value =  form1.p_pwd.value;

            form1.action            = "/servlet/controller.homepage.LoginServlet";
            form1.submit();
        }
    }

    // 입력 체크
    function input_chk(form1)  {
        var chk = ( isNull(form1.elements["p_userid"],"아이디") && isNull(form1.elements["p_pwd"],"비밀번호"));
        return (chk);
    }
//-->
</script>

<!-- form 시작 -->
<form name="form1"  action="" method="post">
    <input type="hidden" name="p_process"       values="" />
    <input type="hidden" name="p_topuserid"     values="" />
    <input type="hidden" name="p_toppwd"        values="" />
    <input type="hidden" name="p_eduservicesul" values="" />
	<input type="hidden" name="p_eventgubun"    value="<%=p_eventgubun %>" />
	
	  <%if( box.getSession("tem_grcode").equals("N000001")) { %>
	          
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
      <tr>
      	<td>
      		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 100px;">
      <tr>
      	<td align="center"><a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/member/membership/loginlogo.gif" width="246" height="81" alt="KOCCA 한국콘텐츠아카데미 로그인"/></a></td>
      </tr>      
       <tr>
         <td height="550" valign="top" align="center">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="login">
        <tr>
          <td align="right" valign="bottom"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="30" align="left"><table width="100%" border="0" cellspacing="2" cellpadding="0" id="login_form">
                <tr>
                  <td><img src="/images/portal/homepage_renewal/member/tit_loginbox01.gif" alt="아이디" /></td>
                  <td>
                    <input type="text" name="p_userid" id="p_userid" style="width:202px; height:26px;" onkeypress="password_enter(event)" tabindex="1"/>
                  </td>
                  <td rowspan="2"><a href="javascript:login();"><img src="/images/portal/homepage_renewal/member/btn_login02.gif" alt="로그인" tabindex="3" style="width: 86px;height: 59px;"/></a></td>
                </tr>

                <tr>
                  <td width="87"><img src="/images/portal/homepage_renewal/member/tit_loginbox02.gif" alt="패스워드" /></td>
                  <td>
                    <input type="password" name="p_pwd" id="p_pwd" style="width:202px; height:26px;" onkeypress="password_enter(event)" tabindex="2"/>
                  </td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td height="44" align="left"><img src="/images/portal/homepage_renewal/member/line_blue02.gif" /></td>
            </tr>
            <tr>
              <td height="30" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/images/portal/homepage_renewal/member/tit_loginbox03.gif" alt="회원가입을 하시면 더 많은 혜택이 있습니다." /></td>
                  <td height="30" align="right"><a href="javascript:mainmenu('991');"><img src="/images/portal/homepage_renewal/member/btn_join02.gif" alt="회원가입" /></a></td>
                </tr>
                <tr>
                  <td><img src="/images/portal/homepage_renewal/member/tit_loginbox04.gif" alt="회원아이디를 잊으셨나요?" /></td>
                  <td height="30" align="right"><a href="javascript:mainmenu('992');"><img src="/images/portal/homepage_renewal/member/btn_idfind02.gif" alt="아이디찾기" /></a></td>
                </tr>
                <tr>
                  <td><img src="/images/portal/homepage_renewal/member/tit_loginbox05.gif" alt="비밀번호가 기억이 안나세요?" /></td>
                  <td height="30" align="right"><a href="javascript:mainmenu('992');"><img src="/images/portal/homepage_renewal/member/btn_pwfind02.gif" alt="비밀번호 찾기" /></a></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="44" align="left"><img src="/images/portal/homepage_renewal/member/line_blue02.gif" /></td>
            </tr>
            <tr>
              <td height="30" align="left">&middot; 회원정보와 관련한 문의사항은 한국콘텐츠아카데미 운영팀으로<br />
&nbsp;&nbsp;문의해주시기 바랍니다.<br />
&middot; TEL. 02-2161-0077&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp          &middot; Email. academy@kocca.or.kr<br /></td>
            </tr>
          </table></td>
        </tr>
      </table>
         
         </td>
  </tr>
  
      		</table>
      	</td>
      </tr>
 
</table>
	      	
	      	<%}else{ %>

<table width="100%" border="0" cellspacing="0" cellpadding="0" >
       <tr>
         <td height="700" valign="top" align="center">
         <table width="576" border="0" cellpadding="0" cellspacing="0">
           <tr>
             <td height="72">&nbsp;</td>
           </tr>
           <tr>
             <td align="left"><a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/member/membership/loginlogo.gif" width="246" height="81" alt="KOCCA 한국콘텐츠아카데미 로그인"/></a></td>
           </tr>
           <tr>
             <td><img src="/images/portal/member/membership/logintitle.gif" alt="Login" width="576" height="95" /></td>
           </tr>
           <tr>
             <td height="370" valign="top" background="/images/portal/member/membership/login_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td height="28">&nbsp;</td>
               </tr>
               <tr>
                 <td><table border="0" align="left" cellpadding="0" cellspacing="0" summary="로그인정보입력">
                   <tr>
                     <td width="180">&nbsp;</td>
                      <td width="170">
                      <input name="p_userid" type="text" class="inp_txt_01" id="p_userid" onkeypress="password_enter(event)"  size="25" title="아이디"/></td>
                     <td rowspan="3"><a href="javascript:login();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image14','','/images/portal/member/membership/btn_login_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image14','','/images/portal/member/membership/btn_login_o.gif',1)">
                         <img src="/images/portal/member/membership/btn_login_a.gif" name="Image14" width="107" height="54" border="0" id="Image14" alt="로그인" /></a></td>
                   </tr>
                   <tr>
                    <td>&nbsp;</td>
                     <td>
                       <input name="p_pwd" type="password" class="inp_txt_01" id="p_pwd" onkeypress="password_enter(event)"  size="25" title="패스워드" />
                   </td>
                   </tr>
                 </table></td>
               </tr>
               <tr>
                 <td height="35">&nbsp;</td>
               </tr>
               <tr>
                 <td><table  border="0" align="left" cellpadding="0" cellspacing="0" summary="회원가입영역">
                   <tr>
                     <td width="330">&nbsp;</td>
                     <td height="32"><a href="javascript:mainmenu('991');"><img src="/images/portal/member/membership/btn_join.gif" width="127" height="31" alt="회원가입"/></a></td>
                   </tr>
                   <tr>
                     <td>&nbsp;</td>
                     <td height="32"><a href="javascript:mainmenu('992');"><img src="/images/portal/member/membership/btn_idsearch.gif" width="127" height="31"  alt="아이디 찾기"/></a></td>
                   </tr>
                   <tr>
                     <td>&nbsp;</td>
                     <td height="32"><a href="javascript:mainmenu('992');"><img src="/images/portal/member/membership/btn_pwsearch.gif" width="127" height="31"  alt="비밀번호 찾기"/></a></td>
                   </tr>
                 </table></td>
               </tr>
             </table></td>
           </tr>
         </table></td>
  </tr>
 
</table>

<%} %>

</form>
<!-- form 끝 -->

<!-- footer -->
    <!-- Footer Menu 시작 -->
    <%@ include file="/learn/user/portal/include/footer_include.jsp" %>
    <!-- Footer Menu 종료 -->

</body>
</html>
<!--// footer -->