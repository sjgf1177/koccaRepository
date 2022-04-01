<%
//**********************************************************
//  1. 제      목: 회원가입 완료
//  2. 프로그램명 : zu_MemberJoinOk.jsp
//  3. 개      요: 회원가입 완료
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
    
    box.put("topmenu","1");
	box.put("submenu","2");
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
	<input type="hidden"	name="p_process"	value="" >
	<input type="hidden"	name="p_resno" value="" >
	
	<!--타이틀부분//-->
	<div class="content">
		<div class="joinfinish"><img src="/images/2012/sub/page0/join_finish.gif" alt="한국콘텐츠아카데미 회원가입이 완료되었습니다."></div>
	</div>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
<!--// footer -->