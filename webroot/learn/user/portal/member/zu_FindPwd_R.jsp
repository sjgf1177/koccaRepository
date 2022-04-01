<%
//**********************************************************
//  1. 제      목: 패스워드 찾기 결과
//  2. 프로그램명 : zu_FindPwd_R.jsp
//  3. 개      요: 패스워드 찾기 결과
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
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

    String v_name = box.getString("p_name_2");
   // String v_pwd  = (String)request.getAttribute("pwd");
    String v_tmp_pwd  =  box.getString("tmp_pwd");
    String v_email  = box.getString("v_toEmail");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post" >
	<input type="hidden"	name="p_process"	value="" />

    <h2><img src="/images/portal/member/membership/h3_tit.gif" alt="아이디/패스워드 찾기" class="fl_l" /><p class="category">Home &gt; 이용안내 &gt; <strong>아이디/패스워드 찾기</strong></p></h2>

    <p><img src="/images/portal/member/membership/stit_m7.gif"  width="101" height="13" alt="패스워드 찾기 결과" /></p>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18 ">
            <tr>
             <td height="129" align="left" valign="top" background="/images/portal/member/membership/box_img_9.gif" style="padding-top:30px;padding-left:270px;background-repeat:no-repeat">
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <% if (v_tmp_pwd.equals("")) {  %>
                       <tr>
                         <td height="45" valign="top" class="lh_17"><strong>입력하신 정보와 일치하는 데이터가 없습니다.</strong><br />
                         </td>
                       </tr>
                   <% } else {  %>
                       <tr>
                         <td height="60" valign="top" class="lh_17"><strong><%=v_name %></strong> 회원님의 임시 비밀번호는 <strong><%=v_tmp_pwd %></strong> 입니다.<br />
                           안내해 드린 비밀번호를 &quot;개인정보변경&quot; 접속후 변경하여 주시기 바랍니다.</td>
                       </tr>
                   <% } %>
                       <tr>
                         <td align="left"><a href="javascript:mainmenu('990');"><img src="/images/portal/member/membership/btn_login2.gif" width="85" height="29" alt="로그인"/></a></td>
                      </tr>
             </table></td>
           </tr>
     </table>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->