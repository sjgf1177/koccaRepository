<%
//**********************************************************
//  1. 제      목: 회워탈퇴
//  2. 프로그램명 :zu_MemberWithdraw.jsp
//  3. 개      요: 회워탈퇴
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
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
   
	String v_userid   = box.getSession("userid");
	box.put("submenu","5");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

//탈퇴신청
function doWithdraw(){
    var fm=document.form1;

    if( (fm.p_userid.value) == ""){
        alert("아이디가 없습니다");        
    }
    if( (fm.p_pwd.value) == ""){
        alert("비밀번호를 입력하세요");
        fm.p_pwd.focus();return;
    }

    //document.form1.target="_self";
    document.form1.p_process.value="MemberWithdrawOk";
    document.form1.action="/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.submit()
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post" action="">
	<input type="hidden"	name="p_process"	value="" >
    <input type="hidden"	name="p_userid"	value="<%=v_userid %>" >
	<div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_out.gif" alt="회원탈퇴"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>회원탈퇴</u></td>
			</tr>
		</table>
		<div class="memberout">
			<ul class="memberoutlayout">
				<li class="leftspace">
					<img src="/images/2012/sub/page0/memberout_image.gif" alt="이미지"><br><br><br>
					<p><img src="/images/2012/sub/icon_dot.png" class="dot" align="absmiddle" alt="dot">회원탈퇴를 신청하시면 해당 아이디는 사용이 중지되므로 <u>해당 아이디로는 재가입이</u><br>&nbsp;<u>불가능</u>합니다.</p>
					<p><img src="/images/2012/sub/icon_dot.png" class="dot" align="absmiddle" alt="dot">회원탈퇴한 회원정보는 삭제되며, 기존 가입한 ID와 신규가입 ID간의 ID 중복방지와<br>&nbsp;게시글 관리를 위하여 성명과 ID는 남습니다.</p>
					<p><img src="/images/2012/sub/icon_dot.png" class="dot" align="absmiddle" alt="dot">회원 탈퇴 후 재가입 가능하며, 개인정보는 <u>회원탈퇴와 동시에 삭제 및 파기</u>됩니다.</p>
					<p><문의 : 한국콘텐츠아카데미 운영팀 TEL. 02-2161-0077></p>
				</li>
				<li class="rightspace">
					<div class="becouse" cellspacing="0" cellpadding="0" summary="회원탈퇴를 하기 위해 다음 항목을 작성해주세요">
						<table class="insert" cellspacing="0" cellpadding="0">
							<colgroup><col width="80"><col width="*"></colgroup>
							<tr>
								<th>아이디</th>
								<td><%=v_userid %></td>
							</tr>
							<tr>
								<th>비밀번호</th>
								<td><input type="password" tabindex="171" name="p_pwd" class="password" title="비밀번호를 입력해주세요."></td>
							</tr>
							<tr>
								<th>탈퇴사유</th>
								<td><textarea class="reason" tabindex="172" name="p_leave_reason" title="탈퇴하시는 사유를 적어주세요."></textarea></td>
							</tr>
						</table>
						<div class="insertfooter"><a href="javascript:doWithdraw();" tabindex="173" title="위의 입력하신 정보로 회원탈퇴를 신청합니다"><img src="/images/2012/sub/page0/btn_memberout.gif" alt="회원탈퇴를 신청합니다."></a></div>
					</div>
				</li>
			</ul>
			<div class="clearL"></div>
		</div>
	</div>
</form>	
<!-- footer -->
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
<!--// footer -->