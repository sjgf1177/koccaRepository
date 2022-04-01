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
    //String v_pwd  = (String)request.getAttribute("pwd");
    String v_tmp_pwd  =  box.getString("tmp_pwd");
    String v_email  = box.getString("v_toEmail");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post" action="">
	<input type="hidden"	name="p_process"	value="" />

    <div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_idpass.gif" alt="아이디 비밀번호 찾기"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>아이디/비밀번호 찾기</u></td>
			</tr>
		</table>
		<div class="idpass">
			<div class="idfinish">
			
				<% if (v_tmp_pwd.equals("")) {  %>
                      <h6>입력하신 정보와 일치하는 정보가 없습니다</h6>
                   <% } else {  %>
                       <h6><strong><%=v_name %></strong> 회원님의 임시 비밀번호는 <strong><%=v_tmp_pwd %></strong> 입니다.<br />
                           	안내해 드린 임시 비밀번호로 로그인 후 &quot;개인정보변경&quot; 접속후 변경하여 주시기 바랍니다.
                       </h6>
                   <% } %>
				<br>
				<button type="button" class="btn_nomal_white" onclick="history.go(-1)" title="이전 화면으로 돌아갑니다.">뒤로가기</button>
			</div>
		</div>
	</div>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
<!--// footer -->